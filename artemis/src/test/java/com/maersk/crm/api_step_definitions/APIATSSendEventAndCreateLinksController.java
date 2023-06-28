package com.maersk.crm.api_step_definitions;

import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.maersk.crm.pages.crm.CRMDemographicContactInfoPage;
import com.maersk.crm.step_definitions.CRM_GeneralTaskStepDef;
import com.maersk.crm.step_definitions.CRM_TaskManagementStepDef;
import com.maersk.crm.utilities.*;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

import static com.maersk.crm.api_step_definitions.APIATSApplicationController.applicationIdAPI;
import static com.maersk.crm.api_step_definitions.APIATSApplicationController.piSSN;
import static com.maersk.crm.step_definitions.CRM_ATS_ATSTaskLinksStepDef.caseIdUI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class APIATSSendEventAndCreateLinksController extends CRMUtilities implements ApiBase {
    private static final String EVENT_END_POINT = "/ecms/connectionpoint/sendevent";
    private static final String SEARCH_EVENT = "/app/crm/events";
    private static final String APPLICATIONS = "/mars/applicationdata/applications";
    private static final String DPBI_BASE = "https://mars-event-api-qa.apps.non-prod.pcf-maersk.com";
    private static final String DPBI_QA_DEV_BASE = "https://mars-event-api-qa-dev.apps.non-prod.pcf-maersk.com";
    private static final String DPBI_ENDPOINT = "/app/crm/es/subscriber/records/12700";
    private APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
    private ApiTestDataUtil testDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private final ThreadLocal <JsonObject> sendEventRequest = ThreadLocal.withInitial(JsonObject::new);
    public static final ThreadLocal <List<Map<String, Object>>> events = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <Map<String, String>> transactionalData = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <Map<String, String>> piConsumerData = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <Map<String, String>> piAddressInfo = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <Map<String, String>> appMemOneData = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <List<String>> programsList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<String>> optInList = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal <List<String>> appconsumerIdList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <Map<String, Object>> authRepData = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <Map<String, Object>> authRepAddressData = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <Map<String, Object>> authRepTwoData = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <Map<String, String>> piPhoneInformation = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <Map<String, String>> piEmailInformation = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <Map<String, String>> piOptInSelection = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <Map<String, String>> actualPI = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <Map<String, String>> actualAMOne = ThreadLocal.withInitial(HashMap::new);
    public static final ThreadLocal <String> documentId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> documentId2 = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> documentId1 = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> inbCreatedAppId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> arFirstName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> arLastName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> arMi = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> arOrgName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> arIdNumber = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> arAddress = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> arCity = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> arState = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> arZip = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> arSignDate = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> pIGender = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> aMGender = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <Integer> expectedBabies = ThreadLocal.withInitial(()->0);
    private final ThreadLocal <Boolean> arAuthIndicator = ThreadLocal.withInitial(()->false);
    private final ThreadLocal <Boolean> aMpregnancyInd = ThreadLocal.withInitial(()->false);
    private final ThreadLocal <Boolean> arAuthTwoIndicator = ThreadLocal.withInitial(()->false);
    public static final ThreadLocal <Map<String, String>> createdConsumerDetails = ThreadLocal.withInitial(HashMap::new);
    public static final ThreadLocal <Map<String, String>> idFullNameList = ThreadLocal.withInitial(HashMap::new);;
    private final ThreadLocal <JSONObject> payloadObject = ThreadLocal.withInitial(JSONObject::new);
    public static final ThreadLocal <String> apiUserID = ThreadLocal.withInitial(String::new);
    protected final ThreadLocal <JsonObject> inbRequest = ThreadLocal.withInitial(JsonObject::new);
    public static final ThreadLocal <String> inbCreatedTaskId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> randomSetId = ThreadLocal.withInitial(()->RandomStringUtils.random(15, true, false));
    private static final ThreadLocal <String> duplicateSetId = ThreadLocal.withInitial(String::new);
    APIAutoUtilities apiAutoUtilities = new APIAutoUtilities();
    public static final ThreadLocal <String> applicationCodeAPI = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> applicationCodeAfterUpdateAPI = ThreadLocal.withInitial(String::new);

    public static final ThreadLocal <String> consumerNotOnCaseFN = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> consumerNotOnCaseLN = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> consumerNotOnCaseDOB = ThreadLocal.withInitial(String::new);

    public static final ThreadLocal <String> apiApplicationID = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> apiApplicationType = ThreadLocal.withInitial(String::new);

    APIATSConsumersController consumersController = new APIATSConsumersController();
    CRMDemographicContactInfoPage crmDemographicContactInfoPage = new CRMDemographicContactInfoPage();
    APICreateCaseController createCaseController=new APICreateCaseController();
    CRM_GeneralTaskStepDef generalTaskStepDef = new CRM_GeneralTaskStepDef();
    private final ThreadLocal <Integer> eventCountATS = ThreadLocal.withInitial(()->0);
    private final ThreadLocal <Integer> eventCountECMS = ThreadLocal.withInitial(()->0);
    public static final ThreadLocal <List<String>> consumerRoleList = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal <List<Integer>> consumerIdList = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal <String> apiApplicationCode = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> apiProgramType = ThreadLocal.withInitial(String::new);

    @Given("I initiated mars search events api")
    public void initiateSearchEvents() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiMarsEvent"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint = "";
        //api.setEndPoint(SEARCH_EVENT);
    }

    @Given("I have prepared default sendevent request data for ATS from file {string}")
    public void i_have_prepared_default_sendevent_request_data_for_ATS_from_file(String filePath) {
        documentId.set(World.generalSave.get().get("InboundDocumentIdDigital").toString());
        System.out.println("Document id: "+documentId);
        System.out.println("File: " + filePath);
        initRequestData(filePath);
    }

    @And("I have prepared default sendevent request data for ATS from file {string} for second time")
    public void iHavePreparedDefaultSendeventRequestDataForATSFromFileForSecondTime(String filePath) {
        documentId2.set(World.generalSave.get().get("InboundDocumentIdDigital").toString());
        System.out.println("File: " + filePath);
        initRequestData(filePath);
    }

    @Given("I have prepared main properties\\(override if exist) for ATS sendevent endpoint as")
    public void i_have_prepared_main_properties_override_if_exist_for_ATS_sendevent_endpoint_as(List<Map<String, String>> data) {
        documentId.set(World.generalSave.get().get("InboundDocumentIdDigital").toString());
        if (sendEventRequest == null) {
            initDefaultRequestData();
        }
        Map<String, String> inputMainProperties = new HashMap<>();
        // all other dynamic fields should be handled here
        for (String key : data.get(0).keySet()) {
            String value = data.get(0).get(key);
            if (value.equalsIgnoreCase("documentId") && key.equalsIgnoreCase("documentHandle")) {
                System.out.println(documentId);
                value = documentId.get();
                documentId1.set(documentId.get());
            } else if (value.equalsIgnoreCase("documentId2") && key.equalsIgnoreCase("documentHandle")) {
                value = documentId2.get();
            } else if (value.contains("today:")) {
                String format = value.substring(value.indexOf(":") + 1).trim();
                if (value.contains("T")) {
                    format = format.replace("T", ""); // Use T if the format needs to be added this way
                    value = testDataUtil.getCurrentDate(format) + "T02:52:11.000000+00:00";
                } else {
                    value = testDataUtil.getCurrentDate(format);
                }
            } else if (value.equals("null")) {
                value = null;
            }
            inputMainProperties.put(key, value);
            // add to transactional variable
            transactionalData.get().put(key, value);
        }
        prepareMainProperties(inputMainProperties);
    }

    @Given("I have prepared keywordFields\\(override if exist) for ATS sendevent endpoint as")
    public void i_have_prepared_keywordFields_override_if_exist_for_ATS_sendevent_endpoint_as(List<Map<String, String>> data) {
        documentId.set(World.generalSave.get().get("InboundDocumentIdDigital").toString());
        if (sendEventRequest == null) {
            initDefaultRequestData();
        }
        Map<String, String> inputKeywordFields = new HashMap<>();
        // all dynamic fields should be handled here
        for (String key : data.get(0).keySet()) {
            String value = data.get(0).get(key);
            if (value.equalsIgnoreCase("documentId") && key.equalsIgnoreCase("documentHandle")) {
                System.out.println(documentId);
                value = documentId.get();
                documentId1.set(documentId.get());
            } else if (value.contains("today:")) {
                String format = value.substring(value.indexOf(":") + 1).trim();
                if (value.contains("T")) {
                    format = format.replace("T", ""); // Use T on the dataTable if the format needs to be added this way
                    value = testDataUtil.getCurrentDate(format) + "T02:52:11.000000+00:00";
                } else {
                    value = testDataUtil.getCurrentDate(format);
                }
            } else if (value.equals("null")) {
                value = null;
            } else if (value.contains("checkbox:")) {
                String checkBox = value.substring(value.indexOf(":") + 1).trim();
                value = checkBox;
            } else if (value.equals("documentId one")) {
                value = String.valueOf(World.generalSave.get().get("setId"));
            } else if (value.equals("API application ID")) {
                value = String.valueOf(applicationIdAPI.get());
            } else if (value.equals("IN RID")) {
                value = String.valueOf(generalTaskStepDef.externalCaseID.get());
            }
            inputKeywordFields.put(key, value);
            // add to transactional variable
            transactionalData.get().put(key, value);
        }
        addKeywordFieldsToRequest(inputKeywordFields);
        if (!applicationIdAPI.get().equals("")) {
            addKeywordFieldsToRequest(addressDynamicDataAdd());
            addKeywordFieldsToRequest(phoneDynamicDataAdd());
            addKeywordFieldsToRequest(emailDynamicDataAdd());
            addKeywordFieldsToRequest(optInDataAdd());
            programsList.get().addAll(Arrays.asList("Medicaid", "CHIP", "Pregnancy Assistance"));
        }
    }

    @Then("I submit ATS sendevent request")
    public void i_submit_ATS_sendevent_request() {
        if (sendEventRequest == null) {
            initDefaultRequestData();
        }
        sendEvent();
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Expected status code: 200");
        Map<String, String> res = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.as(HashMap.class);
        Assert.assertEquals(res.get("status"), "SUCCESS", "Expected SUCCESS");
        // typo in expected msg
        if (!applicationIdAPI.get().equals("")) {
            //Commented the message Assertion , It is different on qa-dev and qa-rls
            //  Assert.assertEquals(res.get("message"), " No Valid Task rules to apply for given Request: Task creation is aborted", "Expected different msg");
        } else {
            Assert.assertEquals(res.get("message"), "maersk Application event request processed successfully", "Expected different msg");
        }
        // need to wait because of search api
        waitFor(10);
    }

    @Then("I received {string} events from search event API")
    public void i_received_events_from_search_event_API(String numStr) {
        int expectedNum = Integer.parseInt(numStr);
        Map<String, Object> content = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList");
        events.set((ArrayList) content.get("content"));
        Assert.assertEquals(events.get().size(), expectedNum, "Expected " + numStr + " events");
    }

    @Then("there is event in search event results for {string} with values")
    public void there_is_event_in_search_event_results_for_with_values(String appKey, List<Map<String, String>> data) {
        try {
            documentId.set(World.generalSave.get().get("InboundDocumentIdDigital").toString());
        } catch (NullPointerException exception) {
            System.out.println("InboundDocumentIdDigital is null");
        }
        Map<String, String> expectedData = new HashMap<>();
        // handle documentId
        for (String key : data.get(0).keySet()) {
            String value = data.get(0).get(key);
            switch (value) {
                case "documentId":
                    value = documentId.get();
                    break;
                case "documentId2":
                    value = documentId2.get();
                    break;
                case "documentId1":
                    value = documentId1.get();
                    break;
                case "applicationId":
                    value = applicationIdAPI.get();
                    break;
                case "caseId":
                    value = String.valueOf(APIContactRecordController.caseID);
                    break;
                case "caseIdUI":
                    value = caseIdUI;
                    break;
                case "applicationConsumerIdPI":
                    value = consumersController.getAppConsumerIDByRole("PRIMARY INDIVIDUAL");
                    break;
                case "applicationConsumerIdAM":
                    value = consumersController.getAppConsumerIDByRole("APPLICATION MEMBER");
                    break;
                case "consumerIdPI":
                    value = String.valueOf(consumersController.applicationConsumerIdList.get().get(0));
                    break;
                case "consumerIdAM":
                    value = String.valueOf(consumersController.applicationConsumerIdList.get().get(1));
                    break;
                case "consumerIdPIUI":
                    value = String.valueOf(crmDemographicContactInfoPage.listOfConsumerID.get(0).getText());
                    break;
                case "consumerIdAMUI":
                    value = String.valueOf(crmDemographicContactInfoPage.listOfConsumerID.get(1).getText());
                    break;
                case "CASEID":
                    System.out.println("Case id from loop: "+CRM_GeneralTaskStepDef.taskValues.get().get("caseID"));
                    value = CRM_GeneralTaskStepDef.taskValues.get().get("caseID");
                    break;
                case "TASKID":
                    System.out.println("Task id from loop: "+CRM_TaskManagementStepDef.taskId.get());
                    value = CRM_TaskManagementStepDef.taskId.get();
                    break;
                case "CONSUMERID":
                    System.out.println("Consumer id from loop: "+CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"));
                    value = CRM_GeneralTaskStepDef.taskValues.get().get("consumerID");
                    break;

            }
            expectedData.put(key, value);
        }
        // loop over the events
        System.out.println("START:");
        for (Map<String, Object> event : events.get()) {
            Map<String, Object> payload = new Gson().fromJson(event.get("payload").toString(), HashMap.class);
            Map<String, Object> dataObject = (LinkedTreeMap) payload.get("dataObject");
            Map<String, Object> externalLinkPayload = (LinkedTreeMap) dataObject.get("externalLinkPayload");
            int eventId = (int) event.get("eventId");
            int internalId = (int) Math.round((Double) externalLinkPayload.get("internalId"));
            int externalId = (int) Math.round((Double) externalLinkPayload.get("externalId"));
            String internalRefType = externalLinkPayload.get("internalRefType").toString();
            String externalRefType = externalLinkPayload.get("externalRefType").toString();
            System.out.println("External ref type: "+externalRefType);
            if (appKey.equalsIgnoreCase("ATS") || appKey.equalsIgnoreCase("WF")) {
                boolean isATSEventFound = internalRefType.equals(expectedData.get("internalRefType"))
                        && externalRefType.equals(expectedData.get("externalRefType"))
                        && externalId == Integer.parseInt(expectedData.get("externalId"));
                if (isATSEventFound) {
                    eventCountATS.set(eventCountATS.get()+1);

                    System.out.println("All good, ATS event is there");
                    System.out.println("-----------");
                    System.out.println(event.get("payload"));
                    System.out.println("eventId: " + eventId);
                    System.out.println("internalId: " + internalId);
                    System.out.println("externalId: " + externalId);
                    System.out.println("internalRefType: " + internalRefType);
                    System.out.println("externalRefType: " + externalRefType);
                    System.out.println("-----------");
                    // save data for ATS
                    transactionalData.get().put("eventId", String.valueOf(eventId));
                    transactionalData.get().put("internalId", String.valueOf(internalId));
                    transactionalData.get().put("externalId", String.valueOf(externalId));
                    transactionalData.get().put("internalRefType", internalRefType);
                    transactionalData.get().put("externalRefType", externalRefType);
                    return;
                }
            } else if (appKey.equalsIgnoreCase("ECMS")) {
                boolean isECMSEventFound = internalRefType.equals(expectedData.get("internalRefType"))
                        && externalRefType.equals(expectedData.get("externalRefType"))
                        && internalId == Integer.parseInt(expectedData.get("internalId"));
                if (isECMSEventFound) {
                    eventCountECMS.set(eventCountECMS.get()+1);

                    System.out.println("All good, ECMS event is there");
                    System.out.println("-----------");
                    System.out.println(event.get("payload"));
                    System.out.println("eventId: " + eventId);
                    System.out.println("internalId: " + internalId);
                    System.out.println("externalId: " + externalId);
                    System.out.println("internalRefType: " + internalRefType);
                    System.out.println("externalRefType: " + externalRefType);
                    System.out.println("-----------");
                    // save data for ECMS
                    transactionalData.get().put("ecms_eventId", String.valueOf(eventId));
                    return;
                }
            }
        }
        Assert.fail(appKey + " event is not found.");
    }

    @When("I should see that the {string} record is produced to DPBI for {string} event")
    public void i_should_see_that_the_record_is_produced_to_DPBI_for_event(String eventName, String appName) {
        String eventId;
        // for future use to handle other conditions as well
        switch (appName.toUpperCase()) {
            case "ATS":
                eventId = transactionalData.get().get("eventId");
                break;
            case "ECMS":
                eventId = transactionalData.get().get("ecms_eventId");
                break;
            default:
                throw new RuntimeException("Application name is not defined - " + appName);
        }
        if ("qa-dev".equals(System.getProperty("env"))) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(DPBI_QA_DEV_BASE);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(DPBI_ENDPOINT + "/" + eventId);
        } else  {
            System.out.println("Url for qa: "+DPBI_BASE+DPBI_ENDPOINT+"/"+eventId);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(DPBI_BASE);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(DPBI_ENDPOINT + "/" + eventId);
        }

        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI().jsonPathEvaluator;
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Expected 200");
        Assert.assertEquals(response.get("status").toString(), "success", "Expected success");
        Assert.assertEquals(response.getList("eventSubscriberRecords").size(), 1);
        Assert.assertEquals(response.get("eventSubscriberRecords[0].eventName"), eventName, "Expected: " + eventName);
    }

    @When("I initiated application api for ats with url id {string}")
    public void i_initiated_application_api_for_ats_with_url_id(String id) {
        // for ats it's internalId for event data
        id = id.equalsIgnoreCase("base") ? transactionalData.get().get("internalId") : id;
        System.out.println(id);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiApplicationData"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(APPLICATIONS + "/" + id);
    }

    @When("I send request to application api for ats")
    public void i_send_request_to_application_api_for_ats() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
    }

    @Then("I verify application data response from ats application api")
    public void i_verify_application_data_response_from_ats_application_api(List<Map<String, String>> data) {
        Map<String, String> expectedData = data.get(0);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        Map<String, Object> res = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.as(HashMap.class);
        Assert.assertEquals(res.get("status"), "success", "Expected success status");
        Map<String, String> resData = (HashMap) res.get("data");
        for (String expectedKey : expectedData.keySet()) {
            String actualValue = String.valueOf(resData.get(expectedKey));
            String expectedValue = expectedData.get(expectedKey);
            if (expectedValue.contains("base:")) {
                String trKey = expectedValue.substring(expectedValue.indexOf(":") + 1).trim();
                expectedValue = transactionalData.get().get(trKey);
                // special cases
                if (expectedKey.equalsIgnoreCase("applicationSignatureExistsInd")) {
                    expectedValue = expectedValue.equalsIgnoreCase("YES") ? "true" : "false";
                }
            }
            Assert.assertEquals(actualValue, expectedValue, "Mismatch in response");
        }
    }

    public void initDefaultRequestData() {
        sendEventRequest.set(testDataUtil.getJsonFromFile("events/ATSSendEvent.json").jsonElement.getAsJsonObject());
    }

    public void initRequestData(String filePath) {
        sendEventRequest.set(testDataUtil.getJsonFromFile(filePath).jsonElement.getAsJsonObject());
    }

    /**
     * This method to set main properties for send request object
     * Note, it will override existing properties from file
     *
     * @param data
     */
    public void prepareMainProperties(Map<String, String> data) {
        for (String key : data.keySet()) {
            sendEventRequest.get().get("document").getAsJsonObject().get("properties").getAsJsonObject().addProperty(key, data.get(key));
        }
    }

    /**
     * This method will add object to keywordFields array
     * Note, it will override if keyword is already exist(keywordTypeName should match)
     *
     * @param keywordTypeName
     * @param value
     */
    public void addKeywordFieldToRequest(String keywordTypeName, String value) {
        JsonArray jsonArray = sendEventRequest.get().get("document").getAsJsonObject().get("keywordFields").getAsJsonArray();
        for (JsonElement field : jsonArray) {
            String key = field.getAsJsonObject().get("keywordTypeName").getAsString();
            if (key.equalsIgnoreCase(keywordTypeName)) {
                field.getAsJsonObject().addProperty("value", value);
                return;
            }
        }
        JsonObject newKeyword = new JsonObject();
        newKeyword.addProperty("keywordTypeName", keywordTypeName);
        newKeyword.addProperty("value", value);
        jsonArray.add(newKeyword);
    }

    /**
     * Balk add objects to keywordFields array
     * Note, it will override if keyword is already exist(keywordTypeName should match)
     *
     * @param data K - keywordTypeName, V - value
     */
    public void addKeywordFieldsToRequest(Map<String, String> data) {
        for (String key : data.keySet()) {
            addKeywordFieldToRequest(key, data.get(key));
        }
    }

    public void addKeywordRecordToRequest(String name, String keywordTypeName, String value, int index) {
        JsonArray nameJsonArray = new JsonArray();
        JsonArray jsonArray = sendEventRequest.get().get("document").getAsJsonObject().get("keywordRecords").getAsJsonArray();
        for (JsonElement element : jsonArray) {
            if (element.getAsJsonObject().get("name").getAsString().equalsIgnoreCase(name)) {
                nameJsonArray.add(element);
            }
        }
        JsonElement applicationMemberToChange = nameJsonArray.get(index);
        JsonArray keywords = applicationMemberToChange.getAsJsonObject().getAsJsonArray("keywords");
        for (JsonElement keyword : keywords) {
            String key = keyword.getAsJsonObject().get("keywordTypeName").getAsString();
            if (key.equalsIgnoreCase(keywordTypeName)) {
                keyword.getAsJsonObject().addProperty("value", value);
                return;
            }
        }
    }

    public void addKeywordRecordsToRequest(String name, Map<String, String> inputKeywords, int index) {
        for (String keywordTypeName : inputKeywords.keySet()) {
            addKeywordRecordToRequest(name, keywordTypeName, inputKeywords.get(keywordTypeName), index);
        }
    }

    public void sendEvent() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(EVENT_END_POINT);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(sendEventRequest.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    public static void main(String[] args) {
        APIATSSendEventAndCreateLinksController obj = new APIATSSendEventAndCreateLinksController();
        Map<String, String> mainProp = new HashMap<>();
        mainProp.put("createdBy", "1234");
        obj.prepareMainProperties(mainProp);
        obj.addKeywordFieldToRequest("Channel", "Email");
        obj.addKeywordRecordToRequest("Member", "First Name", "Alex", 0);
        obj.addKeywordRecordToRequest("Authorized Representative", "AR First Name", "Bob", 0);
        obj.addKeywordRecordToRequest("Member", "First Name", "Beknazar", 1);
        obj.addKeywordRecordToRequest("Authorized Representative", "AR First Name", "Munavvar", 1);
        System.out.println(obj.sendEventRequest.toString());
        obj.sendEvent();
    }

    @And("I store inbound correspondence Application Id found through link event with id {string}")
    public void iStoreInboundCorrespondenceApplicationIdFoundThroughLinkEventWithId(String id) {
        // for ats it's internalId for event data
        id = id.equalsIgnoreCase("base") ? transactionalData.get().get("internalId") : id;
        inbCreatedAppId.set(id);
        System.out.println("Application Id created through Inbound Correspondence: " + id);
    }


    @And("I have prepared Authorized Representative keywords for ATS sendevent")
    public void iHavePreparedAuthorizedRepresentativeKeywordsForATSSendevent(List<Map<String, String>> data) {
        documentId.set(World.generalSave.get().get("InboundDocumentIdDigital").toString());
        if (sendEventRequest == null) {
            initDefaultRequestData();
        }
        Map<String, String> inputMainProperties = new HashMap<>();
        // all other dynamic fields should be handled here
        for (String key : data.get(0).keySet()) {
            String value = data.get(0).get(key);
            if (value.equalsIgnoreCase("documentId") && key.equalsIgnoreCase("documentHandle")) {
                System.out.println(documentId);
                value = documentId.get();
            } else if (value.contains("today:")) {
                String format = value.substring(value.indexOf(":") + 1).trim();
                value = testDataUtil.getCurrentDate(format);
                arSignDate.set(value);
                arAuthIndicator.set(true);
            } else if (value.equals("null")) {
                value = null;
            } else if ("Random First".equals(value)) {
                arFirstName.set(testDataUtil.genRandomName());
                value = arFirstName.get();
            } else if ("Random Last".equals(value)) {
                arLastName.set(testDataUtil.genRandomName());
                value = arLastName.get();
            } else if ("Random Mi".equals(value)) {
                synchronized (arMi){
                    arMi.set(RandomStringUtils.random(1, true, false));
                }
                value = arMi.get();
            } else if ("Random Org name".equals(value)) {
                synchronized (arOrgName){
                    arOrgName.set(RandomStringUtils.random(5, true, false));
                }
                value = arOrgName.get();
            } else if ("Random Id".equals(value)) {
                synchronized (arIdNumber){
                    arIdNumber.set(RandomStringUtils.random(5, false, true));
                }
                value = arIdNumber.get();
            } else if ("Random Address".equals(value)) {
                synchronized (arAddress){
                    arAddress.set(testDataUtil.genRandomAddress());
                }
                value = arAddress.get();
            } else if ("Random City".equals(value)) {
                synchronized (arCity){
                    arCity.set(RandomStringUtils.random(5, true, false));
                }
                value = arCity.get();
            } else if ("Random State".equals(value)) {
                synchronized (arState){
                    arState.set(RandomStringUtils.random(5, true, false));
                }
                value = arState.get();
            } else if ("Random Zip".equals(value)) {
                synchronized (arZip){
                    arZip.set(RandomStringUtils.random(5, false, true));
                }
                value = arZip.get();
            }
            inputMainProperties.put(key, value);
            // add to transactional variable
            transactionalData.get().put(key, value);
        }
        addAuthRepDataToRequest(inputMainProperties);
    }

    public void addAuthRepDataToRequest(Map<String, String> data) {
        for (String key : data.keySet()) {
            addKeywordAuthDataoRequest(key, data.get(key));
        }
    }

    public void addKeywordAuthDataoRequest(String keywordTypeName, String value) {
        JsonArray jsonArray = sendEventRequest.get().get("document").getAsJsonObject().get("keywordRecords").getAsJsonArray().get(2).getAsJsonObject().get("keywords").getAsJsonArray();
        for (JsonElement field : jsonArray) {
            String key = field.getAsJsonObject().get("keywordTypeName").getAsString();
            if (key.equalsIgnoreCase(keywordTypeName)) {
                field.getAsJsonObject().addProperty("value", value);
                return;
            }
        }
        JsonObject newKeyword = new JsonObject();
        newKeyword.addProperty("keywordTypeName", keywordTypeName);
        newKeyword.addProperty("value", value);
        jsonArray.add(newKeyword);
    }

    public void addPiConsumerDataToRequest(Map<String, String> data) {
        for (String key : data.keySet()) {
            addKeywordPiConsumerDataToRequest(key, data.get(key));
        }
    }

    public void addAppMemConsumerDataToRequest(Map<String, String> data) {
        for (String key : data.keySet()) {
            addKeywordAppMemOneDataToRequest(key, data.get(key));
        }
    }

    public void addKeywordPiConsumerDataToRequest(String keywordTypeName, String value) {
        JsonArray jsonArray = sendEventRequest.get().get("document").getAsJsonObject().get("keywordRecords").getAsJsonArray().get(0).getAsJsonObject().get("keywords").getAsJsonArray();
        for (JsonElement field : jsonArray) {
            String key = field.getAsJsonObject().get("keywordTypeName").getAsString();
            if (key.equalsIgnoreCase(keywordTypeName)) {
                field.getAsJsonObject().addProperty("value", value);
                return;
            }
        }
        JsonObject newKeyword = new JsonObject();
        newKeyword.addProperty("keywordTypeName", keywordTypeName);
        newKeyword.addProperty("value", value);
        jsonArray.add(newKeyword);
    }

    public void addKeywordAppMemOneDataToRequest(String keywordTypeName, String value) {
        JsonArray jsonArray = sendEventRequest.get().get("document").getAsJsonObject().get("keywordRecords").getAsJsonArray().get(1).getAsJsonObject().get("keywords").getAsJsonArray();
        for (JsonElement field : jsonArray) {
            String key = field.getAsJsonObject().get("keywordTypeName").getAsString();
            if (key.equalsIgnoreCase(keywordTypeName)) {
                field.getAsJsonObject().addProperty("value", value);
                return;
            }
        }
        JsonObject newKeyword = new JsonObject();
        newKeyword.addProperty("keywordTypeName", keywordTypeName);
        newKeyword.addProperty("value", value);
        jsonArray.add(newKeyword);
    }

    @Then("I verify Authorized Representative data response from ats application api")
    public void iVerifyAuthorizedRepresentativeDataResponseFromAtsApplicationApi() {
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        Map<String, Object> res = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.as(HashMap.class);
        Assert.assertEquals(res.get("status"), "success", "Expected success status");
        authRepData.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().getJsonObject("data.applicationConsumers[2]"));
        authRepAddressData.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().getJsonObject("data.applicationConsumers[2].applicationConsumerAddress[0]"));
        authRepTwoData.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().getJsonObject("data.applicationConsumers[3]"));
        Assert.assertEquals(authRepData.get().get("consumerRoleType"), "Authorized Rep", "Mismatch in Authorized Representative consumerRoleType value created from ECMS: ");
        Assert.assertEquals(authRepData.get().get("consumerFirstName"), arFirstName, "Mismatch in Authorized Representative consumerFirstName value created from ECMS: ");
        Assert.assertEquals(authRepData.get().get("consumerMiddleName").toString(), arMi, "Mismatch in Authorized Representative consumerMiddleName value created from ECMS: ");
        Assert.assertEquals(authRepData.get().get("consumerLastName").toString(), arLastName, "Mismatch in Authorized Representative consumerLastName value created from ECMS: ");
        Assert.assertEquals(authRepData.get().get("authorizedRepOrgName").toString(), arOrgName, "Mismatch in Authorized Representative authorizedRepOrgName value created from ECMS: ");
        Assert.assertEquals(authRepData.get().get("authorizedRepExternalId").toString(), arIdNumber, "Mismatch in Authorized Representative authorizedRepExternalId value created from ECMS: ");
        Assert.assertEquals(authRepAddressData.get().get("addressStreet1").toString(), arAddress, "Mismatch in Authorized Representative addressStreet1 value created from ECMS: ");
        Assert.assertEquals(authRepAddressData.get().get("addressCity").toString(), arCity, "Mismatch in Authorized Representative addressCity value created from ECMS: ");
        Assert.assertEquals(authRepAddressData.get().get("addressState").toString(), arState, "Mismatch in Authorized Representative addressState value created from ECMS: ");
        Assert.assertEquals(authRepAddressData.get().get("addressZip").toString(), arZip, "Mismatch in Authorized Representative addressZip value created from ECMS: ");
        Assert.assertEquals(authRepData.get().get("authorizedRepSignature"), arAuthIndicator, "Mismatch in Authorized Representative authorizedRepSignature value created from ECMS: ");
        Assert.assertEquals(authRepData.get().get("authorizedRepSignatureDate").toString().substring(0, 10), arSignDate, "Mismatch in Authorized Representative authorizedRepSignatureDate value created from ECMS: ");
        // below is to assert check that null AR signature date equals a false value for authorizedRepSignature
        Assert.assertEquals(authRepTwoData.get().get("consumerRoleType"), "Authorized Rep", "Mismatch in Authorized Representative consumerRoleType value created from ECMS: ");
        Assert.assertEquals(authRepTwoData.get().get("authorizedRepSignature"), arAuthTwoIndicator, "Mismatch in Authorized Representative authorizedRepSignature value created from ECMS: ");
    }

    public Map<String, String> addressDynamicDataAdd() {
        piAddressInfo.get().put("Residence Address Line 1", testDataUtil.genRandomAddress());
        piAddressInfo.get().put("Residence Address Line 2", testDataUtil.genRandomAddress());
        piAddressInfo.get().put("Residence Address City", RandomStringUtils.random(5, true, false));
        piAddressInfo.get().put("Residence Address State", RandomStringUtils.random(5, true, false));
        piAddressInfo.get().put("Residence Address Zip", RandomStringUtils.random(5, false, true));
        piAddressInfo.get().put("Residence Address Zip+4", RandomStringUtils.random(4, false, true));
        piAddressInfo.get().put("Residence Address County", RandomStringUtils.random(5, true, false));
        piAddressInfo.get().put("Mailing Address Line 1", testDataUtil.genRandomAddress());
        piAddressInfo.get().put("Mailing Address Line 2", testDataUtil.genRandomAddress());
        piAddressInfo.get().put("Mailing Address City", RandomStringUtils.random(5, true, false));
        piAddressInfo.get().put("Mailing Address State", RandomStringUtils.random(5, true, false));
        piAddressInfo.get().put("Mailing Address Zip", RandomStringUtils.random(5, false, true));
        piAddressInfo.get().put("Mailing Address Zip+4", RandomStringUtils.random(4, false, true));
        return piAddressInfo.get();
    }

    public Map<String, String> phoneDynamicDataAdd() {
        piPhoneInformation.get().put("Cell Phone Number", testDataUtil.genRandomPhoneNumber());
        piPhoneInformation.get().put("Home Phone Number", testDataUtil.genRandomPhoneNumber());
        piPhoneInformation.get().put("Work Phone Number", testDataUtil.genRandomPhoneNumber());
        piPhoneInformation.get().put("Fax Phone Number", testDataUtil.genRandomPhoneNumber());
        piPhoneInformation.get().put("Primary Phone Type", "HOME");
        return piPhoneInformation.get();
    }

    public Map<String, String> emailDynamicDataAdd() {
        piEmailInformation.get().put("Email Address", testDataUtil.genRandomEmail());
        return piEmailInformation.get();
    }

    public Map<String, String> optInDataAdd() {
        piOptInSelection.get().put("SMS Text Opt-In", "YES");
        piOptInSelection.get().put("Email Opt-In", "YES");
        piOptInSelection.get().put("Mail Opt-In", "YES");
        piOptInSelection.get().put("Fax Opt-In", "YES");
        piOptInSelection.get().put("Phone Opt-In", "YES");
        optInList.get().addAll(Arrays.asList("Fax", "Email", "Mail", "Phone", "Text"));
        return piOptInSelection.get();
    }

    public Map<String, String> primaryDynamicDataAdd(String gender) {
        piConsumerData.get().put("First Name", RandomStringUtils.random(5, true, false));
        piConsumerData.get().put("Middle Initial", RandomStringUtils.random(1, true, false));
        piConsumerData.get().put("Last Name", RandomStringUtils.random(5, true, false));
        piConsumerData.get().put("Name Suffix", RandomStringUtils.random(2, true, false));
        piConsumerData.get().put("DOB", testDataUtil.getCurrentDate("yyyy-MM-dd"));
        piConsumerData.get().put("SSN", testDataUtil.genRandomSSN());
        piConsumerData.get().put("External Consumer ID", RandomStringUtils.random(5, true, true));
        piConsumerData.get().put("Is Primary", "YES");
        piConsumerData.get().put("External Consumer ID Type", "MEDICAID");
        if ("M".equals(gender)) {
            piConsumerData.get().put("Gender", "M");
            pIGender.set("Male");
        } else if ("F".equals(gender)) {
            pIGender.set("Female");
            piConsumerData.get().put("Gender", "F");
            piConsumerData.get().put("Pregnant", "YES");
            piConsumerData.get().put("Babies Expected", "7");
            piConsumerData.get().put("Pregnancy Due Date", BrowserUtils.getFutureDateFormatYYYYMMdd(10));
        } else if ("U".equals(gender)) {
            piConsumerData.get().put("Gender", "U");
            pIGender.set("Unknown");
        } else if ("Case".equalsIgnoreCase(gender)){
            pIGender.set("Female");
            piSSN.set(createCaseController.apiSearchConsumerSSN.get());
            piConsumerData.get().put("Gender","F");
            piConsumerData.get().put("SSN",piSSN.get());
            System.out.println("piSSN from case loader:"+piSSN.get());
        }
        return piConsumerData.get();
    }

    public Map<String, String> appMemOneDynamicDataAdd(String gender) {
        appMemOneData.get().put("First Name", RandomStringUtils.random(5, true, false));
        appMemOneData.get().put("Middle Initial", RandomStringUtils.random(1, true, false));
        appMemOneData.get().put("Last Name", RandomStringUtils.random(5, true, false));
        appMemOneData.get().put("Name Suffix", RandomStringUtils.random(3, true, false));
        appMemOneData.get().put("DOB", testDataUtil.getCurrentDate("yyyy-MM-dd"));
        appMemOneData.get().put("SSN", testDataUtil.genRandomSSN());
        appMemOneData.get().put("External Consumer ID", RandomStringUtils.random(5, true, true));
        appMemOneData.get().put("Is Primary", "NO");
        appMemOneData.get().put("External Consumer ID Type", "MEDICAID");
        if ("M".equals(gender)) {
            appMemOneData.get().put("Gender", "M");
            aMGender.set("Male");
        } else if ("F".equals(gender)) {
            aMGender.set("Female");
            aMpregnancyInd.set(true);
            expectedBabies.set(7);
            appMemOneData.get().put("Gender", "F");
            appMemOneData.get().put("Pregnant", "YES");
            appMemOneData.get().put("Babies Expected", "7");
            appMemOneData.get().put("Pregnancy Due Date", BrowserUtils.getFutureDateFormatYYYYMMdd(10));
        } else if ("U".equals(gender)) {
            appMemOneData.get().put("Gender", "U");
            aMGender.set("Unknown");
        }
        return appMemOneData.get();
    }

    @And("I verify Primary Individual Applicant Consumer values incoming from Inbound Correspondence")
    public void iVerifyPrimaryIndividualApplicantConsumerValuesIncomingFromInboundCorrespondence() {
        actualPI.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().getJsonObject("data.applicationConsumers[0]"));
        Assert.assertEquals(actualPI.get().get("consumerRoleType"), "Primary Individual", "Mismatch in Primary Individual consumerRoleType value created from ECMS: ");
        Assert.assertEquals(actualPI.get().get("consumerFirstName"), piConsumerData.get().get("First Name"), "Mismatch in Primary Individual consumerFirstName value created from ECMS: ");
        Assert.assertEquals(actualPI.get().get("consumerMiddleName"), piConsumerData.get().get("Middle Initial"), "Mismatch in Primary Individual consumerMiddleName value created from ECMS: ");
        Assert.assertEquals(actualPI.get().get("consumerLastName"), piConsumerData.get().get("Last Name"), "Mismatch in Primary Individual consumerLastName value created from ECMS: ");
        Assert.assertEquals(actualPI.get().get("consumerSuffix"), piConsumerData.get().get("Name Suffix"), "Mismatch in Primary Individual consumerSuffix value created from ECMS: ");
        Assert.assertEquals(actualPI.get().get("dateOfBirth"), piConsumerData.get().get("DOB"), "Mismatch in Primary Individual dateOfBirth value created from ECMS: ");
        Assert.assertEquals(actualPI.get().get("genderCode"), pIGender, "Mismatch in Primary Individual genderCode value created from ECMS: ");
        Assert.assertEquals(actualPI.get().get("ssn"), piConsumerData.get().get("SSN"), "Mismatch in Primary Individual ssn value created from ECMS: ");
        Assert.assertEquals(actualPI.get().get("externalConsumerId"), piConsumerData.get().get("External Consumer ID"), "Mismatch in Primary Individual externalConsumerId value created from ECMS: ");
        Assert.assertEquals(actualPI.get().get("externalConsumerIdType"), piConsumerData.get().get("External Consumer ID Type"), "Mismatch in Primary Individual externalConsumerIdType value created from ECMS: ");
        List<String> actualOptInList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers[0].consumerOptInInformation");
        Collections.sort(actualOptInList);
        Collections.sort(optInList.get());
        Assert.assertEquals(actualOptInList, optInList, "Mismatch in Primary Individual consumerOptInInformation value created from ECMS: ");
        List<String> actualProgramsList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers[0].programs.programType");
        Collections.sort(programsList.get());
        Collections.sort(actualProgramsList);
        Assert.assertEquals(actualProgramsList, programsList, "Mismatch in Primary Individual programs value created from ECMS: ");
    }

    @And("I verify Application Member one Applicant Consumer values incoming from Inbound Correspondence")
    public void iVerifyApplicationMemberOneApplicantConsumerValuesIncomingFromInboundCorrespondence() {
        actualAMOne.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().getJsonObject("data.applicationConsumers[1]"));
        Assert.assertEquals(actualAMOne.get().get("consumerRoleType"), "Application Member", "Mismatch in Application Member consumerRoleType value created from ECMS: ");
        Assert.assertEquals(actualAMOne.get().get("consumerFirstName"), appMemOneData.get().get("First Name"), "Mismatch in Application Member consumerFirstName value created from ECMS: ");
        Assert.assertEquals(actualAMOne.get().get("consumerMiddleName"), appMemOneData.get().get("Middle Initial"), "Mismatch in Application Member consumerMiddleName value created from ECMS: ");
        Assert.assertEquals(actualAMOne.get().get("consumerLastName"), appMemOneData.get().get("Last Name"), "Mismatch in Application Member consumerLastName value created from ECMS: ");
        Assert.assertEquals(actualAMOne.get().get("consumerSuffix"), appMemOneData.get().get("Name Suffix"), "Mismatch in Application Member consumerSuffix value created from ECMS: ");
        Assert.assertEquals(actualAMOne.get().get("dateOfBirth"), appMemOneData.get().get("DOB"), "Mismatch in Application Member dateOfBirth value created from ECMS: ");
        Assert.assertEquals(actualAMOne.get().get("genderCode"), aMGender, "Mismatch in Application Member genderCode value created from ECMS: ");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getBoolean("data.applicationConsumers[1].pregnancyInd"), aMpregnancyInd, "Mismatch in Application Member pregnancyInd value created from ECMS: ");
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.applicationConsumers[1].expectedBabies"), expectedBabies, "Mismatch in Application Member expectedBabies value created from ECMS: ");
        Assert.assertEquals(actualAMOne.get().get("ssn"), appMemOneData.get().get("SSN"), "Mismatch in Application Member ssn value created from ECMS: ");
        Assert.assertEquals(actualAMOne.get().get("externalConsumerId"), appMemOneData.get().get("External Consumer ID"), "Mismatch in Application Member externalConsumerId value created from ECMS: ");
        Assert.assertEquals(actualAMOne.get().get("externalConsumerIdType"), appMemOneData.get().get("External Consumer ID Type"), "Mismatch in Application Member externalConsumerIdType value created from ECMS: ");
    }

    @And("I verify Application Contact Information values incoming from Inbound Correspondence")
    public void iVerifyApplicationContactInformationValuesIncomingFromInboundCorrespondence() {
        List<Map<String, Object>> applicationConsumerPhone = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers[0].applicationConsumerPhone");
        // Phone verification for Inbound Correspondence application
        for (String eachVerifyValue : piPhoneInformation.get().keySet()) {
            switch (eachVerifyValue) {
                case "Cell Phone Number":
                    for (Map<String, Object> eachPhone : applicationConsumerPhone) {
                        if ("cellPhoneNumber".equals(eachPhone.get("phoneType"))) {
                            assertEquals(eachPhone.get("phoneNumber"), piPhoneInformation.get().get("Cell Phone Number"), "Actual Application Cell Phone Number does not match the expected CELL PHONE");
                        }
                    }
                    break;
                case "Home Phone Number":
                    for (Map<String, Object> eachPhone : applicationConsumerPhone) {
                        if ("homePhoneNumber".equals(eachPhone.get("phoneType"))) {
                            assertEquals(eachPhone.get("phoneNumber"), piPhoneInformation.get().get("Home Phone Number"), "Actual Application HOME Phone Number does not match the expected HOME PHONE");
                        }
                    }
                    break;
                case "Work Phone Number":
                    for (Map<String, Object> eachPhone : applicationConsumerPhone) {
                        if ("workPhoneNumber".equals(eachPhone.get("phoneType"))) {
                            assertEquals(eachPhone.get("phoneNumber"), piPhoneInformation.get().get("Work Phone Number"), "Actual Application WORK Phone Number does not match the expected WORK PHONE");
                        }
                    }
                    break;
                case "Fax Phone Number":
                    for (Map<String, Object> eachPhone : applicationConsumerPhone) {
                        if ("faxnumber".equals(eachPhone.get("phoneType"))) {
                            assertEquals(eachPhone.get("phoneNumber"), piPhoneInformation.get().get("Fax Phone Number"), "Actual Application FAX Number does not match the expected FAX");
                        }
                    }
                    break;
                case "Primary Phone Type":
                    if ("CELL".equals(piPhoneInformation.get().get("Primary Phone Type"))) {
                        for (Map<String, Object> eachPhone : applicationConsumerPhone) {
                            if ("cellPhoneNumber".equals(eachPhone.get("phoneType"))) {
                                assertEquals(eachPhone.get("primaryContactTypeInd"), true, "Expected Cell Phone to be Primary Phone but found false");
                            }
                        }
                    } else if ("WORK".equals(piPhoneInformation.get().get("Primary Phone Type"))) {
                        for (Map<String, Object> eachPhone : applicationConsumerPhone) {
                            if ("workPhoneNumber".equals(eachPhone.get("phoneType"))) {
                                assertEquals(eachPhone.get("primaryContactTypeInd"), true, "Expected WORK Phone to be Primary Phone but found false");
                            }
                        }
                    } else if ("HOME".equals(piPhoneInformation.get().get("Primary Phone Type"))) {
                        for (Map<String, Object> eachPhone : applicationConsumerPhone) {
                            if ("homePhoneNumber".equals(eachPhone.get("phoneType"))) {
                                assertEquals(eachPhone.get("primaryContactTypeInd"), true, "Expected HOME Phone to be Primary Phone but found false");
                            }
                        }
                    } else if ("FAX".equals(piPhoneInformation.get().get("Primary Phone Type"))) {
                        for (Map<String, Object> eachPhone : applicationConsumerPhone) {
                            if ("faxnumber".equals(eachPhone.get("phoneType"))) {
                                assertEquals(eachPhone.get("primaryContactTypeInd"), true, "Expected FAX number to be Primary Phone but found false");
                            }
                        }
                    } else {
                        fail("Entered Primary Phone Indicator did not match CELL, WORK, HOME, FAX");
                    }
                    break;
                default:
                    Assert.fail("Entered Key did not match existing keys");
            }
        }
        // Email verification for Inbound Correspondence application
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.jsonPath().getJsonObject("data.applicationConsumers[0].applicationConsumerEmail[0].emailAddress").toString(), piEmailInformation.get().get("Email Address"));
        // Address verification for Inbound Correspondence application
        List<Map<String, Object>> applicationConsumerAddress = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers[0].applicationConsumerAddress");
        for (String eachVerifyValue : piAddressInfo.get().keySet()) {
            switch (eachVerifyValue) {
                case "Residence Address Line 1":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Residential".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressStreet1"), piAddressInfo.get().get("Residence Address Line 1"), "Actual Application RESIDENCE ADDRESS 1 does not match the expected RESIDENCE ADDRESS 1:");
                        }
                    }
                    break;
                case "Residence Address Line 2":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Residential".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressStreet2"), piAddressInfo.get().get("Residence Address Line 2"), "Actual Application RESIDENCE ADDRESS 2 does not match the expected RESIDENCE ADDRESS 2:");
                        }
                    }
                    break;
                case "Residence Address City":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Residential".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressCity"), piAddressInfo.get().get("Residence Address City"), "Actual Application RESIDENCE CITY does not match the expected RESIDENCE CITY:");
                        }
                    }
                    break;
                case "Residence Address State":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Residential".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressState"), piAddressInfo.get().get("Residence Address State"), "Actual Application RESIDENCE STATE does not match the expected RESIDENCE STATE:");
                        }
                    }
                    break;
                case "Residence Address Zip":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Residential".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressZip"), piAddressInfo.get().get("Residence Address Zip"), "Actual Application RESIDENCE ZIP does not match the expected RESIDENCE ZIP:");
                        }
                    }
                    break;
                case "Residence Address Zip+4":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Residential".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressZipFour"), piAddressInfo.get().get("Residence Address Zip+4"), "Actual Application RESIDENCE ZIP+4 does not match the expected RESIDENCE ZIP:");
                        }
                    }
                    break;
                case "Residence Address County":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Residential".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressCounty"), piAddressInfo.get().get("Residence Address County"), "Actual Application RESIDENCE COUNTY does not match the expected RESIDENCE COUNTY:");
                        }
                    }
                    break;
                case "Mailing Address Line 1":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Mailing".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressStreet1"), piAddressInfo.get().get("Mailing Address Line 1"), "Actual Application MAIL ADDRESS 1 does not match the expected MAIL ADDRESS 1:");
                        }
                    }
                    break;
                case "Mailing Address Line 2":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Mailing".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressStreet2"), piAddressInfo.get().get("Mailing Address Line 2"), "Actual Application MAIL ADDRESS 2 does not match the expected MAIL ADDRESS 2:");
                        }
                    }
                    break;
                case "Mailing Address City":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Mailing".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressCity"), piAddressInfo.get().get("Mailing Address City"), "Actual Application MAIL CITY does not match the expected MAIL CITY:");
                        }
                    }
                    break;
                case "Mailing Address State":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Mailing".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressState"), piAddressInfo.get().get("Mailing Address State"), "Actual Application MAIL STATE does not match the expected MAIL STATE:");
                        }
                    }
                    break;
                case "Mailing Address Zip":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Mailing".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressZip"), piAddressInfo.get().get("Mailing Address Zip"), "Actual Application MAIL ZIP does not match the expected MAIL ZIP:");
                        }
                    }
                    break;
                case "Mailing Address Zip+4":
                    for (Map<String, Object> eachAddress : applicationConsumerAddress) {
                        if ("Mailing".equals(eachAddress.get("addressType"))) {
                            assertEquals(eachAddress.get("addressZipFour"), piAddressInfo.get().get("Mailing Address Zip+4"), "Actual Application MAIL ZIP+4 does not match the expected MAIL ZIP:");
                        }
                    }
                    break;
                default:
                    fail("Key did not match Mailing or Residential address values");
            }
        }
    }

    @And("I have prepared keywordRecord Primary Individual member dynamic data values with {string}")
    public void iHavePreparedKeywordRecordPrimaryIndividualMemberDynamicDataValuesWith(String gender) {
        addPiConsumerDataToRequest(primaryDynamicDataAdd(gender));
    }

    @And("I have prepared keywordRecord Application member one dynamic data values with {string}")
    public void iHavePreparedKeywordRecordApplicationMemberOneDynamicDataValuesWith(String gender) {
        addAppMemConsumerDataToRequest(appMemOneDynamicDataAdd(gender));
    }

    @And("I verify Primary Mailing and Residence Contact Information is the same values from Inbound Correspondence application")
    public void iVerifyPrimaryMailingAndResidenceContactInformationIsTheSameValuesFromInboundCorrespondenceApplication() {
        List<Map<String, Object>> applicationConsumerAddress = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers[0].applicationConsumerAddress");
        Assert.assertEquals(applicationConsumerAddress.get(0).get("addressStreet1"), applicationConsumerAddress.get(1).get("addressStreet1"), "Expected same Mailing and Residence value for Address line 1 for Same as Residence address marked YES: ");
        Assert.assertEquals(applicationConsumerAddress.get(0).get("addressStreet2"), applicationConsumerAddress.get(1).get("addressStreet2"), "Expected same Mailing and Residence value for Address line 2 for Same as Residence address marked YES: ");
        Assert.assertEquals(applicationConsumerAddress.get(0).get("addressCity"), applicationConsumerAddress.get(1).get("addressCity"), "Expected same Mailing and Residence value for City for Same as Residence address marked YES: ");
        Assert.assertEquals(applicationConsumerAddress.get(0).get("addressState"), applicationConsumerAddress.get(1).get("addressState"), "Expected same Mailing and Residence value for State for Same as Residence address marked YES: ");
        Assert.assertEquals(applicationConsumerAddress.get(0).get("addressZip"), applicationConsumerAddress.get(1).get("addressZip"), "Expected same Mailing and Residence value for Zip for Same as Residence address marked YES: ");
        Assert.assertEquals(applicationConsumerAddress.get(0).get("addressZipFour"), applicationConsumerAddress.get(1).get("addressZipFour"), "Expected same Mailing and Residence value for Zip+4 for Same as Residence address marked YES: ");
    }

    @And("I get the Application Consumer ID ,Consumer Role Type and User ID from API response")
    public void iGetTheApplicationConsumerIDConsumerRoleTypeAndUserIDFromAPIResponse() {
        apiUserID.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationConsumers[0].createdBy"));
        consumerRoleList.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers.consumerRoleType"));
        consumerIdList.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers.applicationConsumerId"));

        createdConsumerDetails.set(new HashMap<>());
        for (int i = 0; i < consumerIdList.get().size(); i++) {
            createdConsumerDetails.get().put("" + consumerIdList.get().get(i), consumerRoleList.get().get(i));
        }

        // Enhanced to add fullName to MAP idFullNameList (id:fullName)

        idFullNameList.get().clear();
        List<String> consumerFNameList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers.consumerFirstName");
        List<String> consumerLNameList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers.consumerLastName");
        List<String> consumerMNameList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers.consumerMiddleName");
        List<String> consumerSuffixList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers.consumerSuffix");
        for (int i = 0; i < consumerIdList.get().size(); i++) {
            String fullName = "";
            if (consumerFNameList.get(i) == null) {
                fullName += "";
            } else {
                fullName += consumerFNameList.get(i);
            }
            if (consumerMNameList.get(i) == null) {
                fullName += "";
            } else {
                fullName += " " + consumerMNameList.get(i);
            }
            if (consumerLNameList.get(i) == null) {
                fullName += "";
            } else {
                fullName += " " + consumerLNameList.get(i);
            }
            if (consumerSuffixList.get(i) == null) {
                fullName += "";
            } else {
                fullName += " " + consumerSuffixList.get(i);
            }
            idFullNameList.get().put("" + consumerIdList.get().get(i), fullName.trim());
        }
        // Enhanced to add Id's to a static List appconsumerIdList
        appconsumerIdList.get().clear();
        consumerIdList.get().forEach(each -> appconsumerIdList.get().add(String.valueOf(each)));

    }

    @And("I initiate request of documentId from Put ECMS documentencoded services with following values")
    public void iInitiateRequestOfDocumentIdFromPutECMSDocumentencodedServicesWithFollowingValues(Map<String, String> dataTable) {
        inbRequest.set((JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/ecmsPutRequest.json").jsonElement);
        for (String keyword : dataTable.keySet()) {
            switch (keyword) {
                case "documentType":
                    inbRequest.get().addProperty(keyword, dataTable.get(keyword));
                    break;
                case "fileType":
                    inbRequest.get().addProperty(keyword, dataTable.get(keyword));
                    break;
                case "setId":
                    String set = dataTable.get(keyword);
                    if ("random".equalsIgnoreCase(dataTable.get(keyword))) {
                        set = randomSetId.get();
                        duplicateSetId.set(set);
                        World.generalSave.get().put("setId", set);
                    }
                    else if ("duplicate".equalsIgnoreCase(dataTable.get(keyword))){
                        set = duplicateSetId.get();
                        World.generalSave.get().put("setId",set);
                    }
                    JsonObject setMetadata = new JsonObject();
                    setMetadata.addProperty("name", keyword);
                    setMetadata.addProperty("value", set);
                    inbRequest.get().getAsJsonArray("metaData").add(setMetadata);
                    break;
                case "StatusSetOn":
                    String setOn = dataTable.get(keyword);
                    if ("current".equals(dataTable.get(keyword))) {
                        setOn = getCurrentDateInYearFormat() + "T11:11:30.967000+00:00";
                        World.generalSave.get().put("StatusSetOn", setOn);
                    }
                    JsonObject setOnMetadata = new JsonObject();
                    setOnMetadata.addProperty("name", keyword);
                    setOnMetadata.addProperty("value", setOn);
                    inbRequest.get().getAsJsonArray("metaData").add(setOnMetadata);
                    break;
                case "FromEmailAddress":
                    String fromEmailAddress = dataTable.get(keyword);

                    JsonObject fromEmailAddressMetadata = new JsonObject();
                    fromEmailAddressMetadata.addProperty("name", keyword);
                    fromEmailAddressMetadata.addProperty("value", fromEmailAddress);
                    inbRequest.get().getAsJsonArray("metaData").add(fromEmailAddressMetadata);
                    break;
                case "FromPhoneNumber":
                    String fromPhoneNumber = dataTable.get(keyword);

                    JsonObject fromPhoneNumberMetadata = new JsonObject();
                    fromPhoneNumberMetadata.addProperty("name", keyword);
                    fromPhoneNumberMetadata.addProperty("value", fromPhoneNumber);
                    inbRequest.get().getAsJsonArray("metaData").add(fromPhoneNumberMetadata);
                    break;
                case "External Consumer ID Type":
                    String externalConsumerIdType = dataTable.get(keyword);

                    JsonObject externalConsumerIdTypeMetadata = new JsonObject();
                    externalConsumerIdTypeMetadata.addProperty("name", keyword);
                    externalConsumerIdTypeMetadata.addProperty("value", externalConsumerIdType);
                    inbRequest.get().getAsJsonArray("metaData").add(externalConsumerIdTypeMetadata);
                    break;
                case "IN RID":
                    String inRID = dataTable.get(keyword);
                    if("externalId".equalsIgnoreCase(dataTable.get(keyword))){
                        inRID = generalTaskStepDef.externalConsumerID.get();
                    }
                    JsonObject inRIDMetadata = new JsonObject();
                    inRIDMetadata.addProperty("name", keyword);
                    inRIDMetadata.addProperty("value", inRID);
                    inbRequest.get().getAsJsonArray("metaData").add(inRIDMetadata);
                    break;

                default:
                    String value = dataTable.get(keyword);
                    if ("Random".equalsIgnoreCase(dataTable.get(keyword))) {
                        value = RandomStringUtils.random(5, false, true);
                    }
                    JsonObject metaDataJson = new JsonObject();
                    metaDataJson.addProperty("name", keyword);
                    metaDataJson.addProperty("value", value);
                    inbRequest.get().getAsJsonArray("metaData").add(metaDataJson);
            }
        }
    }

    @When("I send PUT ECMS documentencoded services and store documentId {string} in a list")
    public void iSendPUTECMSDocumentencodedServicesAndStoreDocumentIdInAList(String documentIdNum) {
        switch (documentIdNum) {
            case "one":
                World.generalSave.get().put("InboundDocumentIdDigital", Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createInboundDocumentDigital(inbRequest.get())));
                System.out.println("Inbound document id digital: "+World.generalSave.get().get("InboundDocumentIdDigital").toString());
                System.out.println("Document id for one: "+Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createInboundDocumentDigital(inbRequest.get())));
                break;
            case "two":
                World.generalSave.get().put("InboundDocumentIdDigitalTwo", Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createInboundDocumentDigital(inbRequest.get())));
                System.out.println("Document id for two: "+Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createInboundDocumentDigital(inbRequest.get())));
                break;
            case "three":
                World.generalSave.get().put("InboundDocumentIdDigitalThree", Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createInboundDocumentDigital(inbRequest.get())));
                System.out.println("Document id for three: "+Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createInboundDocumentDigital(inbRequest.get())));
                break;
        }
    }

    @Then("I check to see Inbound Document Id {string} received is viewable in pdf")
    public void iCheckToSeeInboundDocumentIdReceivedIsViewableInPdf(String documentIdNum) throws IOException {
        Assert.assertTrue(verifyNotificationViewed(documentIdNum));
    }

    public boolean verifyNotificationViewed(String type) throws IOException {
        Boolean bool = false;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/document/contents/" + World.generalSave.get().get("InboundDocumentIdDigital") + "?format=pdf&type=Inbound%20Correspondence");
        switch (type) {
            case "one": {
                byte[] res = getCallPDFINBDOCUMENT(World.generalSave.get().get("InboundDocumentIdDigital").toString());
                PDDocument document = PDDocument.load(new ByteArrayInputStream(res));
                String pdfStripper = new PDFTextStripper().getText(document);
                Assert.assertTrue(pdfStripper.contains("Multi page document"));
                Assert.assertTrue(pdfStripper.contains("1"));
                Assert.assertTrue(pdfStripper.contains("2"));
                Assert.assertTrue(pdfStripper.contains("3"));
                bool = true;
            }
            return bool;
            case "two":
                if (EventsUtilities.getRawLogs(ConfigurationReader.getProperty("apiBffCorrespondence") + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint).size() > 0) {
                    byte[] res = getCallPDFINBDOCUMENT(World.generalSave.get().get("InboundDocumentIdDigitalTwo").toString());
                    PDDocument document = PDDocument.load(new ByteArrayInputStream(res));
                    String pdfStripper = new PDFTextStripper().getText(document);
                    Assert.assertTrue(pdfStripper.contains("Multi page document"));
                    Assert.assertTrue(pdfStripper.contains("1"));
                    Assert.assertTrue(pdfStripper.contains("2"));
                    Assert.assertTrue(pdfStripper.contains("3"));
                    bool = true;
                }
                return bool;
            default:
                return bool;
        }
    }

    private byte[] getCallPDFINBDOCUMENT(String id) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/document/contents/" + id + "?type=Inbound+Correspondence&format=pdf");
        Response pdfInb = apiAutoUtilities.getAPI(ConfigurationReader.getProperty("apiBffCorrespondence"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint);
        return pdfInb.asByteArray();
    }

    @And("I initiate and run Get Inbound Correspondence Links Call")
    public JsonPath inboundCorrespondenceLinks() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiLinksLink"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/links/inbound_correspondence/" + documentId + "?size=5&page=0");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Linked Applications/Tasks to inb correspondence Status Code from Get Inbound Correspondence Links Call: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "wrong status code");
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    @Then("I will get applicationId and taskId from the response")
    public void getApplicationIdTaskIdFromResponse() {
        List<Map<String, Object>> linkData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("externalLinkDetails.content");
        for (Map<String, Object> linkId : linkData) {
            if (linkId.get("name").toString().equalsIgnoreCase("Application")) {
                inbCreatedAppId.set(linkId.get("id").toString());
                System.out.println("Application id from links: " + inbCreatedAppId);
            } else if (linkId.get("name").toString().equalsIgnoreCase("Task")) {
                inbCreatedTaskId.set(linkId.get("id").toString());
                System.out.println("Task id from links: " + inbCreatedTaskId);
            }
        }
    }

    @And("I get the application GUID from API response")
    public void iGetTheApplicationGUIDFromAPIResponse() {
        applicationCodeAPI.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationCode"));
    }

    @And("I get the application GUID from API response after updating status")
    public void iGetTheApplicationGUIDFromAPIResponseAfterUpdate() {
        applicationCodeAfterUpdateAPI.set(null);
        applicationCodeAfterUpdateAPI.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationCode"));
    }

    @And("I get the application id and application type from API response")
    public void iGetTheApplicationIdApplicationTypeFromAPIResponse() {
        apiApplicationID.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationId"));
        apiApplicationType.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationType"));
    }

    @And("I verify {int} events found for {string} with above values")
    public void iVerifyEventsFoundForWithAboveValues(int eventCount, String appKey ) {
        if(appKey.equalsIgnoreCase("ATS")){
            Assert.assertTrue(eventCount==eventCountATS.get(),"Expected event count mismatched.ExpectedCount: " + eventCount + " But found: " + eventCountATS);
        }
        else if(appKey.equalsIgnoreCase("ECMS")){
            Assert.assertTrue(eventCount==eventCountECMS.get(),"Expected event count mismatched.ExpectedCount: " + eventCount + " But found: " + eventCountECMS);
        }
    }

    @And("I get the applicaion code from the API response")
    public void iGetTheApplicationCodeFromAPIResponse() {
        apiApplicationCode.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationCode"));
    }
    
    @And("I get the program type from the API response")
    public void iGetTheProgramTypeFromAPIResponse() {
        apiProgramType.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationConsumers[0].programs[0].programType"));
    }

    @And("I get second application member details to create consumer not on a case")
    public void iGetSecondApplicationMemberDetailsToCreateConsumerNotOnAcase() {
        List<Integer> consumerIdList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers.applicationConsumerId");

        for (int i = 0; i < consumerIdList.size(); i++) {
            if(null== API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers.ssn").get(i) & API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers.consumerRoleType").get(i).equals("Application Member")) {
                consumerNotOnCaseFN.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers.consumerFirstName").get(i).toString());
                consumerNotOnCaseLN.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers.consumerLastName").get(i).toString());
                consumerNotOnCaseDOB.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.applicationConsumers.dateOfBirth").get(i).toString());
            }
        }
        System.out.println("Consumer not on a cse first name: "+consumerNotOnCaseFN);
        System.out.println("Consumer not on a case last name: "+consumerNotOnCaseLN);
        System.out.println("Consumer not on a case DOB: "+consumerNotOnCaseDOB);

        appconsumerIdList.get().clear();
    }
}
