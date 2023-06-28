package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.step_definitions.CRMOptInOutDemographicMember;
import com.maersk.crm.utilities.APIClassUtil;
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

import static org.testng.Assert.*;

public class APIConsumerRemoveDupeFieldsController extends CRMUtilities implements ApiBase {

    private String eventURI = ConfigurationReader.getProperty("apiEventsURI");
    private String eventsCorrelationIdEndPoint = "/app/crm/event/correlation/";

    private APIClassUtil apiEvent = APIClassUtil.getApiClassUtilThreadLocal();
    private APITMEventController apitmEventController = new APITMEventController();

    CRMOptInOutDemographicMember createCaseLoaderMember = new CRMOptInOutDemographicMember();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();

    private final ThreadLocal <String> correlationId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> firstName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> lastName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> firstNameMember = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> lastNameMember = ThreadLocal.withInitial(String::new);

    @When("I create new consumer using case loader for remove dupe fields check for {string}")
    public void createConsumerNoEmptyString(String consumerRole) {
        Map<String, String> consumerNames = createCaseLoaderMember.createACaseUsingCaseLoaderAPIForDemographicMember(consumerRole);
        firstName.set(consumerNames.get("firstName"));
        lastName.set(consumerNames.get("lastName"));
        correlationId.set(consumerNames.get("correlationId"));

        System.out.println("First Name of the Consumer from Case Loader: "+firstName.get());
        System.out.println("Last Name of the Consumer from Case Loader: "+lastName.get());
    }

    @When("I will take correlation Id for {string} for {string} for remove dupe fields check")
    public void i_will_take_trace_Id_for_Events(String event, String eventType) {
        eventsCorrelationIdEndPoint = "/app/crm/event/correlation/";
        if (event.contains("SAVE")) {
            correlationId.set(EventsUtilities.getLogs("traceid", eventType).get(1));
        } else if (event.contains("UPDATE")) {
            correlationId.set(EventsUtilities.getLogs("traceid", eventType).get(2));
        }
        System.out.println("Log gets correlation id:  " + correlationId.get());
        eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
    }

    @When("I search consumer with first name and last name for remove dupe fields check")
    public void searchConsumerCommPrefIdCheck() {
        contactRecord.firstName.sendKeys(firstName.get());
        contactRecord.lastName.sendKeys(lastName.get());
        waitFor(2);
        contactRecord.searchButton.click();
        waitFor(1);
        contactRecord.caseIdFirstRecord.click();
        waitFor(2);
    }

    @And("I add {string} for remove dupe fields check")
    public void addDemMeberRemoveDupeFieldsCheck(String type) {
        Map<String, String> memberNames = createCaseLoaderMember.addDemMeber(type, "");
        firstNameMember.set(memberNames.get("firstName"));
        lastNameMember.set(memberNames.get("lastName"));

        System.out.println("First Name of the Member added: "+firstNameMember.get());
        System.out.println("Last Name of the Member added: "+lastNameMember.get());
    }

    @Then("I will verify an {string} is created for remove dupe fields check in payload for {string} event")
    public void verifyDupeFieldsEvent(String eventName, String eventType) {
        if (eventType.contains("caseLoader"))
            eventsCorrelationIdEndPoint = eventsCorrelationIdEndPoint + correlationId.get();
        apiEvent.setbaseUri(eventURI);
        apiEvent.setEndPoint(eventsCorrelationIdEndPoint);
       // //apitmEventController.getAuthenticationToken("BLCRM", "CRM");

        apiEvent.getAPI();
        System.out.println("Response for Event: " + apiEvent.responseString);

        assertEquals(apiEvent.statusCode, 200);
        assertEquals(apiEvent.jsonPathEvaluator.get("status"), "success");

        List eventsContent = apiEvent.jsonPathEvaluator.getJsonObject("events");
        assertTrue(eventsContent.size() > 0);

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

            if (eventName.contains("SAVE"))
                assertEquals(payLoadData.get("action").getAsString(), "Create");
            else
                assertEquals(payLoadData.get("action").getAsString(), "Update");

            assertTrue(payLoadData.get("recordType").getAsString().equalsIgnoreCase("Consumer"));
            assertNotNull(payLoadData.get("dataObject"));
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

            if (eventType.contains("UI")) {
                assertEquals(dataObjectData.get("consumerFirstName").getAsString().toLowerCase(), firstNameMember.get().toLowerCase());
                assertEquals(dataObjectData.get("consumerLastName").getAsString().toLowerCase(), lastNameMember.get().toLowerCase());
            } else if (eventType.contains("caseLoader")) {
                assertEquals(dataObjectData.get("consumerFirstName").getAsString().toLowerCase(), firstName.get().toLowerCase());
                assertEquals(dataObjectData.get("consumerLastName").getAsString().toLowerCase(), lastName.get().toLowerCase());
            }
            assertNull(dataObjectData.get("caseId"));
            assertNull(dataObjectData.get("consumerRole"));
            assertNull(dataObjectData.get("pregnancyDueDate"));
            assertNull(dataObjectData.get("pregnancyInd"));

            JsonArray caseConsumer = dataObjectData.get("caseConsumer").getAsJsonArray();
            JsonObject caseConsumerData = caseConsumer.get(0).getAsJsonObject();

            assertNotNull(caseConsumerData.get("caseId"));
            assertNotNull(caseConsumerData.get("consumerRole"));
            assertNotNull(caseConsumerData.get("pregnancyDueDate"));
            assertNotNull(caseConsumerData.get("pregnancyInd"));

            if (eventsData.get("eventName").equals(eventName)) {
                assertEquals(eventsData.get("status"), "success");
                break;
            }
        }
    }
}
