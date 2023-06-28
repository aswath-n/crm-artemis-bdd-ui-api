package com.maersk.crm.step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.api_step_definitions.APITMEventController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;

public class CRM_CommPrefIdEventsCheckStepDef extends CRMUtilities implements ApiBase {

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    Map<String, Object> newConsumer = new HashMap<>(getNewTestData2());
    public static String consumerName = "";
    CRMDemographicContactInfoPage demographicContactInfo = new CRMDemographicContactInfoPage();
    CRMEditConsumerProfilePage editConsumerProfilePage=new CRMEditConsumerProfilePage();

    private String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private String createCaseConsumerForEvents = "app/crm/caseloader";
    private String eventURI = ConfigurationReader.getProperty("apiEventsURI");
    private String eventsCorrelationIdEndPoint="/app/crm/event/correlation/";

    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private APITMEventController apitmEventController = new APITMEventController();
    CRMCreateConsumerProfilePage updateConsumer = new CRMCreateConsumerProfilePage();
    CRMUnidentifiedContactPage crmUnidentifiedContactPage = new CRMUnidentifiedContactPage();
    CRM_ContactRecordConsumerSummaryStepDef createConsumerForUpdate = new CRM_ContactRecordConsumerSummaryStepDef();
    CRMOptInOutDemographicMember createCaseLoader = new CRMOptInOutDemographicMember();

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);;
    private final ThreadLocal<String> correlationId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> updateDOB =ThreadLocal.withInitial(() -> "01/01/1995");

    @When("I create new consumer using case loader for communication PreferencesID check with {string}")
    public void createConsumerCommPrefIdCheck(String consumerRoleInput) {
        Map<String, String> consumerNames = createCaseLoader.createACaseUsingCaseLoaderAPIForDemographicMember(consumerRoleInput);
        firstName.set(consumerNames.get("firstName"));
        lastName.set(consumerNames.get("lastName"));
        correlationId.set(consumerNames.get("correlationId"));
        requestParams.set(new JsonParser().parse(consumerNames.get("requestParams")).getAsJsonObject());
        System.out.println("Create consumer First Name: " + firstName.get() + " and Last Name: " + lastName.get());
        System.out.println("Request Params for creating case from case loader: "+requestParams.get());
    }

    @When("I update new consumer using case loader for communication PreferencesID check")
    public void UpdateConsumerNoDummyFieldCheckCaseLoader() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseConsumerForEvents);

        System.out.println("Request Params: " + requestParams.get());
        requestParams.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerMiddleName", "F");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        correlationId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTraceId());
        System.out.println("Status Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println("Response String: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @When("I create a consumer for communication PreferencesID check")
    public void createCaseMemberNoDummyFieldCheck() {
        Map<String, String> consumerNames = createConsumerForUpdate.createNewConsumerForContactRecord();
        firstName.set(consumerNames.get("firstName"));
        lastName.set(consumerNames.get("lastName"));
        System.out.println("Create consumer First Name: " + firstName.get() + " and Last Name: " + lastName.get());
    }

    @When("I will take {string} for {string} for {string} for Communication Preference Id check")
    public void i_will_take_trace_Id_for_Events(String idType, String event, String eventType) {
        eventsCorrelationIdEndPoint="/app/crm/event/correlation/";
        if (event.contains("SAVE")) {
            correlationId.set(EventsUtilities.getLogs(idType, eventType).get(1));
        }
        else {
            correlationId.set(EventsUtilities.getLogs(idType, eventType).get(2));
        }
        System.out.println("Log gets correlation id:  "+correlationId.get());
        eventsCorrelationIdEndPoint=eventsCorrelationIdEndPoint+correlationId.get();
    }

    @When("I search consumer with first name and last name for Communication preference from consumer profile for Id check for {string}")
    public void searchConsumerCommPrefIdCheck(String creationType) {
        contactRecord.firstName.sendKeys(firstName.get());
        contactRecord.lastName.sendKeys(lastName.get());
        waitFor(2);
        contactRecord.searchButton.click();
        waitFor(1);
        if (creationType.equals("UI")) {
            contactRecord.consumerIdFirstRecord.click();
            waitForVisibility(editConsumerProfilePage.consumerEditButton, 10);
        }
        else {
            contactRecord.caseIdFirstRecord.click();
            waitForVisibility(demographicContactInfo.demographicInfoTabFromSearch, 10);
        }
    }

    @And("I add {string} for communication PreferencesID check")
    public void addAuthorizedRepCommPrefIdCheck(String type) {
        Map<String, String> memberNames = createCaseLoader.addDemMeber(type, "");
        firstName.set(memberNames.get("firstName"));
        lastName.set(memberNames.get("lastName"));
        System.out.println("Member Created. First Name: "+firstName.get()+" Last Name: "+lastName.get());
    }

    @And("I edit the consumer profile for communication PreferencesID check")
    public void updateConsumerNoDummyFieldsCheck() {
        editConsumerProfilePage.consumerEditButton.click();
        waitForVisibility(updateConsumer.consumerEditCorrespondencePref, 10);
        updateConsumer.consumerEditCorrespondencePref.click();
        waitForVisibility(updateConsumer.consumerDB, 10);
        scrollUpUsingActions(2);
        waitFor(1);
        clearAndFillText(updateConsumer.consumerDB, updateDOB.get());
        waitFor(1);
        selectDropDown(updateConsumer.consumerEditPreflang, "English");
        waitFor(1);
        crmUnidentifiedContactPage.saveBtn.click();
        waitForVisibility(updateConsumer.continueAfterSaveButton, 10);
        updateConsumer.continueAfterSaveButton.click();
        waitForVisibility(editConsumerProfilePage.consumerEditButton, 10);
    }

    @Then("I verify that Consumers Communication Preference Id is present in the {string} from {string}")
    public void verifyCommunicationPrefIdEvents(String event, String eventType) {
        if (eventType.contains("Case"))
            eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsCorrelationIdEndPoint);
        ////apitmEventController.getAuthenticationToken("BLCRM","CRM");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Response for Event: "+ API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
        assertTrue(eventsContent.size() > 0);
        boolean recordFound = false;

        for(int i=0;i<eventsContent.size();i++) {
            HashMap eventsData = (HashMap)eventsContent.get(i);
            System.out.println(eventsData.get("payload"));

            if(!eventsData.get("eventName").equals(event))
                continue;

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

            if (event.contains("CONSUMER_SAVE"))
                assertEquals(payLoadData.get("action").getAsString(), "Create");
            else if (event.contains("CONSUMER_UPDATE"))
                assertEquals(payLoadData.get("action").getAsString(), "Update");

            assertEquals(dataObjectData.get("consumerFirstName").getAsString().toLowerCase(), firstName.get().toLowerCase());
            assertEquals(dataObjectData.get("consumerLastName").getAsString().toLowerCase(), lastName.get().toLowerCase());
            assertEquals(payLoadData.get("recordType").getAsString(), "Consumer");
            JsonArray commPrefs = dataObjectData.get("communicationPreferences").getAsJsonArray();

            if (commPrefs == null)
                System.out.println("No Communication Preferences found");

            JsonObject commpref = commPrefs.get(0).getAsJsonObject();
            assertFalse(commpref.get("communicationPreferencesId").isJsonNull());
            assertNull(commpref.get("preferencesId"));

            if(eventsData.get("eventName").equals(event)) {
                assertEquals(eventsData.get("status"), "success");
                recordFound = true;
                break;
            }
        }

        assertTrue(recordFound);
    }
}
