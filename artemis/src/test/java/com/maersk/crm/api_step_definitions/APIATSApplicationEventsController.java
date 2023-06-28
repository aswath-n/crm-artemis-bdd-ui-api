package com.maersk.crm.api_step_definitions;

import com.maersk.crm.pages.crm.CRMApplicationTrackingPage;
import com.maersk.crm.step_definitions.*;
import com.maersk.crm.utilities.APIClassUtil;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.dms.utilities.EventsUtilities;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.*;


import static com.maersk.crm.api_step_definitions.APIATSApplicationController.*;
import static com.maersk.crm.api_step_definitions.APIATSSendEventAndCreateLinksController.*;
import static com.maersk.crm.step_definitions.CRM_CreateApplicationStepDef.extAppID;
import static com.maersk.crm.step_definitions.CRM_MissingInformationTabStepDef.*;
import static com.maersk.crm.step_definitions.CRM_SearchApplicationStepDef.apiApplicationId;
import static org.testng.Assert.*;

public class APIATSApplicationEventsController extends CRMUtilities implements ApiBase {
    APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
    ApiTestDataUtil apiTestDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    EventsUtilities eventsUtilities = new EventsUtilities();
    CRM_CreateApplicationStepDef crmCreateApplicationStepDef = new CRM_CreateApplicationStepDef();
    CRM_CreateApplicationMemberStepDef crmCreateApplicationMemberStepDef = new CRM_CreateApplicationMemberStepDef();
    CRM_ApplicationTrackingAuthorizedRepStepDef authorizedRepStepDef = new CRM_ApplicationTrackingAuthorizedRepStepDef();

    private final ThreadLocal <JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    public final ThreadLocal <String> correlationId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <Integer> eventId = ThreadLocal.withInitial(()->0);
    public final ThreadLocal <String> action = ThreadLocal.withInitial(String::new);
    public final ThreadLocal <String> currentUserId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal <String> recordType = ThreadLocal.withInitial(String::new);
    public final ThreadLocal <String> eventCreatedOn = ThreadLocal.withInitial(String::new);
    public final ThreadLocal <String> dataObject = ThreadLocal.withInitial(String::new);
    public final ThreadLocal <JSONObject> payloadObject = ThreadLocal.withInitial(JSONObject::new);
    public static final ThreadLocal <String> subscriberId = ThreadLocal.withInitial(()->"12700");
    public final ThreadLocal <JSONArray> subscriberList = ThreadLocal.withInitial(JSONArray::new);
    public final ThreadLocal <String> projectName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal <Integer> projectId = ThreadLocal.withInitial(()->0);
    public final ThreadLocal <String> createdOn = ThreadLocal.withInitial(String::new);
    public final ThreadLocal <String> createdBy = ThreadLocal.withInitial(String::new);
    public final ThreadLocal <String> eventNameResponse = ThreadLocal.withInitial(String::new);
    public final ThreadLocal <String> moduleName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal <String> updatedOn = ThreadLocal.withInitial(String::new);
    public final ThreadLocal <String> updatedBy = ThreadLocal.withInitial(String::new);
    public final ThreadLocal <List<String>> correlationIdList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<Map<String, Object>>> applicationEvents = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal <Integer> eventCount = ThreadLocal.withInitial(()->0);


    public String baseAPIUrl = ConfigurationReader.getProperty("apiEventsURI");
    public String subscribersEndPoint = "/app/crm/es/event/subscribers";
    public String subscribersRecordEndPoint = "/app/crm/es/subscriber/records/";
    public String eventsCorrelationIdEndPoint = "/app/crm/event/correlation/";

    CRMApplicationTrackingPage crmApplicationTrackingPage = new CRMApplicationTrackingPage();

    @When("I will take trace Id for Events and {string} and initiate Event GET API")
    public void i_will_take_trace_Id_for_Events_and_initiate_Event_GET_API(String eventType) {
        switch (eventType) {
            case "STATUS UPDATE":
                correlationId.set(EventsUtilities.getLogs("traceid", "newApplicationStatus", "createdBy", "applicationChangeReason").get(0));
                break;
            case "STATUS DUPLICATE":
                correlationId.set(EventsUtilities.getLogs("traceid", "Duplicate", "createdBy").get(0));
                break;
            case "application update":
                correlationId.set(EventsUtilities.getLogs("traceid", crmCreateApplicationStepDef.cycle, "createdBy", "updatedBy").get(0));
                break;
            case "PI UPDATE":
                correlationId.set(EventsUtilities.getLogs("traceid", "eventCreatedOn", "updatedBy").get(2));
                break;
            case "CONS UPDATE":
                correlationId.set(EventsUtilities.getLogs("traceid", "eventCreatedOn", "updatedBy").get(1));
                break;
            case "AR UPDATE":
                correlationId.set(EventsUtilities.getLogs("traceid", "Applicant Address", "updatedBy").get(4));
                break;
            case "STATUS UPDATE SUBMITTED":
                List<String> listTemp = EventsUtilities.getLogs("traceid", "application", "submittedInd", "true", "createdBy", "createdOn");
                correlationId.set(listTemp.get(2));
                break;
            case "consumer save am":
                correlationId.set(EventsUtilities.getLogs("traceid", "createdBy", "consumers", "applicationConsumers").get(1));
                break;
            case "consumer save ar":
                correlationId.set(EventsUtilities.getLogs("traceid", "consumers", "createdBy", "applicationConsumers", "consumerFirstName", "Authorized Rep").get(0));
                break;
            case "Missing Information Item":
                correlationId.set(EventsUtilities.getLogs("traceid", "comments").get(0));
                break;
            case "Missing Information Update":
                correlationId.set(EventsUtilities.getLogs("traceid", "comments", "completedBy").get(0));
                break;
            case "Eligibility update":
                correlationId.set(EventsUtilities.getLogs("traceid", "eligibilityStatus", "updatedBy").get(0));
                break;
            case "Outbound Correspondence Save":
                correlationId.set(EventsUtilities.getLogs("traceid", "createdBy", "updatedBy").get(0));
                break;
            case "Inbound Correspondence Save":
                correlationId.set(EventsUtilities.getLogs("traceid", "inboundCorrespondenceId", "applicationId").get(0));
                break;
            case "Case":
                correlationId.set(EventsUtilities.getLogs("traceid", "case", "createdBy", "createdOn").get(0));
                break;
            case "expired":
                correlationId.set(EventsUtilities.getLogs("traceid", "Expired").get(0));
                break;
            default:
                correlationId.set(EventsUtilities.getLogs("traceid", eventType, "createdBy", "createdOn").get(0));
                break;
        }
        System.out.println("Log gets correlation id:  " + correlationId);
        String endPoint = "";
        endPoint = eventsCorrelationIdEndPoint + correlationId;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseAPIUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(endPoint);
    }

    @When("I will run the Event GET API for application events {string} and {string} and {string}")
    public JSONObject i_will_run_the_Event_GET_API_for_application_events_and_and(String eventName, String module, String correlation) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Status code isnt verified");

        applicationEvents.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("events"));

        for (int i = 0; i < applicationEvents.get().size(); i++) {
            System.out.println("Events size is: " + applicationEvents.get().size());

            if (applicationEvents.get().get(i).get("eventName").toString().equals(eventName)) {
                System.out.println("Event name is validated");
                payloadObject.set(new JSONObject(applicationEvents.get().get(i).get("payload").toString()));

                eventId.set(Integer.parseInt(applicationEvents.get().get(i).get("eventId").toString()));
                System.out.println("Event id:   " + eventId);
                assertTrue((eventId + "").chars().allMatch(Character::isDigit), "EventId isnt valid");
                System.out.println("Event id is validated");

                moduleName.set(applicationEvents.get().get(i).get("module").toString());
                System.out.println("Module name in response is:  " + module);
                assertEquals(moduleName, module, "Module isnt verified");
                System.out.println("Module name is validated");
            }
        }

        return payloadObject.get();
    }

    @Then("I verify {string} details in the event payload")
    public void i_verify_details_in_the_event_payload(String eventName) {
        switch (eventName) {
            case "MISSING_INFORMATION_ITEM_SAVE_EVENT":
                assertEquals(payloadObject.get().getInt("projectId") + "", (ConfigurationReader.getProperty("projectName").split(" "))[0], "Project Id is not correct");
                assertEquals(payloadObject.get().getString("projectName"), (ConfigurationReader.getProperty("projectName").split(" "))[1], "Project Name is not a correct");
                assertEquals(payloadObject.get().getString("action"), "Create", "Action isnt verified for event");
                // assertEquals(payloadObject.get().getString("recordType"), "Missing Information Item", "Record type verified for event");
                // record type value is changed due to CP-37843
                assertEquals(payloadObject.get().getString("recordType"), "MissingInfoItemModel", "Record type verified for event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("missingInfoItemId").toString().chars().allMatch(Character::isDigit), "Missing Info Item ID is not all digits for event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("missingInfoItemId").toString().equalsIgnoreCase(savedMIValues.get().get(0)), "missingInfoItemId is not same as application");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("entityId").toString().chars().allMatch(Character::isDigit), "Entity ID is not all digits for event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").get("entityRecordType"), miExpectedMissingInfo.get("entityRecordType"), "Entity Record Type is not correct");
                if (miExpectedMissingInfo.get("attributeName").equalsIgnoreCase("Citizenship Verification"))
                    assertTrue(payloadObject.get().getJSONObject("dataObject").get("recordClass").equals("Verification"), "record class is not correct");
                else
                    assertTrue(payloadObject.get().getJSONObject("dataObject").get("recordClass").equals("Data"), "record class is not correct");
                JSONObject missingInfoDependentsObject;
                missingInfoDependentsObject = (JSONObject) payloadObject.get().getJSONObject("dataObject").getJSONArray("missingInformationDependents").get(0);
                if (loggedInUserId == null) {
                    assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), "2492", "CreatedBy isnt valid for " + eventName);
                    assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), "2492", "UpdatedBy isnt valid for " + eventName);
                    assertEquals(missingInfoDependentsObject.get("createdBy"), "2492", "CreatedBy isnt valid for event");
                    assertEquals(missingInfoDependentsObject.get("updatedBy"), "2492", "UpdatedBy isnt valid for event");
                } else {
                    assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), loggedInUserId, "CreatedBy isnt valid for " + eventName);
                    assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), loggedInUserId, "UpdatedBy isnt valid for " + eventName);
                    assertEquals(missingInfoDependentsObject.get("createdBy"), loggedInUserId, "CreatedBy isnt valid for event");
                    assertEquals(missingInfoDependentsObject.get("updatedBy"), loggedInUserId, "UpdatedBy isnt valid for event");
                }
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), loggedInUserId, "CreatedBy isnt valid for event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), loggedInUserId, "UpdatedBy isnt valid for event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdOn").substring(0, 20), payloadObject.get().getJSONObject("dataObject").getString("updatedOn").substring(0, 20), "createdOn & updatedOn are not same");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("attributeValue"), "Attribute Value is not null");
                assertEquals(payloadObject.get().getJSONObject("dataObject").get("status"), "PENDING", "Status is not Pending");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("tags").get("application").toString(), apiApplicationId.get(), "Application ID is not correct");
                assertEquals(payloadObject.get().getJSONObject("dataObject").get("comments"), miExpectedMissingInfo.get("comments"), "Comments are not correct");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("completedBy"), "completedBy is not null");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("completedOn"), "completedOn is not null");
                assertTrue(missingInfoDependentsObject.get("missingInfoDependentId").toString().chars().allMatch(Character::isDigit), "MissingInfo Dependent Id isn't verified");
                assertEquals(missingInfoDependentsObject.get("dependentType"), "Application Consumer", "Dependent Type is not Application Consumer");
                assertTrue(EventsUtilities.isValidDate(missingInfoDependentsObject.get("createdOn").toString()), "CreatedOn date time field IS NOT valid for event");
                assertTrue(EventsUtilities.isValidDate(missingInfoDependentsObject.get("updatedOn").toString()), "UpdatedOn date time field IS NOT valid for event");
                assertEquals(missingInfoDependentsObject.get("createdOn").toString().substring(0, 20), missingInfoDependentsObject.get("updatedOn").toString().substring(0, 20), "createdOn & updatedOn are not same");
                assertEquals(missingInfoDependentsObject.get("createdOn").toString().substring(0, 20), missingInfoDependentsObject.get("updatedOn").toString().substring(0, 20), "createdOn & updatedOn are not same");
                break;
            case "APPLICATION_SAVE_EVENT":
                assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project id isnt correct");
                assertEquals(payloadObject.get().getString("action"), "Create", "Action isnt verified for application save event");
                assertEquals(payloadObject.get().getString("recordType"), "ApplicationEventRequest", "Record type verified for application save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for application save event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "CreatedBy isnt valid for application save event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "UpdatedBy isnt valid for application save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for application save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for application save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("correlationId").equals(correlationId), "correlationId didnt match for application save event");

                System.out.println("ApplicationId from UI:" + crmCreateApplicationStepDef.applicationId);
                assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationId")).equals(crmCreateApplicationStepDef.applicationId), "ApplicationId isnt verified for application save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("applicationSignatureDate"), "App sign date isnt null for application save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("channelId").equals(crmCreateApplicationStepDef.channel), "Channel isnt verified for application save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationType").equals("Long Term Care"), "Application type isnt verified for application save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationCycle").equals("New"), "Application cycle isnt verified for application save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationName").equals("Long Term Care - New"), "Application name isnt verified for application save event");
                assertTrue(EventsUtilities.isOnlyValidDate(payloadObject.get().getJSONObject("dataObject").getString("applicationReceivedDate")), "Application Received Date isnt verified for application save event");
                assertFalse(payloadObject.get().getJSONObject("dataObject").getBoolean("applicationSignatureExistsInd"), "applicationSignatureExistsInd isnt false for application save event");
                if (extAppID != null) {
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("externalApplicationId"), extAppID, "External Application ID isnt verified for application save event");
                }
                System.out.println("Application save event is verified");
                break;

            case "APPLICATION_UPDATE_EVENT":
                assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project id isnt correct");
                assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project name isnt correct");
                assertEquals(payloadObject.get().getString("action"), "Update", "Action isnt verified for application update event");
                assertEquals(payloadObject.get().getString("recordType"), "ApplicationEventRequest", "Record type verified for application save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for application save event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "CreatedBy isnt valid for application save event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "UpdatedBy isnt valid for application save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for application save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for application save event");
                assertFalse(payloadObject.get().getJSONObject("dataObject").getString("createdOn").equals(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "CreatedOn & UpdatedOn are same in update event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("correlationId").equals(correlationId), "correlationId didnt match for application save event");

                System.out.println("ApplicationId from UI:" + crmCreateApplicationStepDef.applicationId);
                assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationId")).equals(crmCreateApplicationStepDef.applicationId), "ApplicationId isnt verified for application update event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("channelId").equals(crmCreateApplicationStepDef.channel), "Channel isnt verified for application save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationPriority").equals(crmCreateApplicationStepDef.priority), "Priority isnt verified for application update event");
                if (crmCreateApplicationStepDef.applicatonType == "Long Term Care")
                    assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationType").equals("Long Term Care"), "Application type isnt verified for application update event");
                if (crmCreateApplicationStepDef.applicatonType == "Medical Assistance")
                    assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationType").equals("Medical Assistance"), "Application type isnt verified for application update event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationCycle").equals(crmCreateApplicationStepDef.cycle), "Application cycle isnt verified for application update event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationName").equals(crmCreateApplicationStepDef.applicatonType + " - " + crmCreateApplicationStepDef.cycle), "Application name isnt verified for application save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").getString("applicationReceivedDate").equals(BrowserUtils.convertMMddyyyyToyyyyMMdd(crmCreateApplicationStepDef.receivedDate)), "Application Received Date isnt verified for application update event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").getString("applicationSignatureDate").equals(crmCreateApplicationStepDef.signatureDate), "Application Signature Date isnt verified for application update event");
                if (crmCreateApplicationStepDef.signature == "Yes")
                    assertTrue(payloadObject.get().getJSONObject("dataObject").getBoolean("applicationSignatureExistsInd"), "applicationSignatureExistsInd isnt true for application update event");
                if (crmCreateApplicationStepDef.signature == "No")
                    assertFalse(payloadObject.get().getJSONObject("dataObject").getBoolean("applicationSignatureExistsInd"), "applicationSignatureExistsInd isnt false for application update event");
                System.out.println("Application update event is verified");
                if (extAppID != null) {
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("externalApplicationId"), extAppID, "External Application ID isnt verified for application save event");
                }
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("applicationReceivedLanguage"),crmCreateApplicationStepDef.receivedLanguage,"Expected applicationReceivedLanguage did not match");
                break;

            case "APPLICATION_STATUS_SAVE_EVENT":
                assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project id isnt correct");
                assertEquals(payloadObject.get().getString("action"), "Create", "Action isnt verified for application status save event");
                assertEquals(payloadObject.get().getString("recordType"), "ApplicationStatus", "Record type verified for application save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for application save event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "CreatedBy isnt valid for application save event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "UpdatedBy isnt valid for application save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for application save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for application save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("correlationId").equals(correlationId), "correlationId didnt match for application save event");

                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationStatusId").toString().chars().allMatch(Character::isDigit), "app statusId isnt digit for application status save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("effectiveStartDate")), "effectiveStartDate field IS NOT valid for application status save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveEndDate"), "effectiveEndDate field IS NOT null for application status save event");
                assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationId")).equals(crmCreateApplicationStepDef.applicationId), "ApplicationId isnt verified for application status save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").getString("statusId").equals("Entering Data"), "Application status isnt verified for application status save event");
                System.out.println("Application status save event is verified");
                break;

            case "APPLICATION_STATUS_UPDATE_EVENT":
                assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project id isnt correct");
                assertEquals(payloadObject.get().getString("action"), "Update", "Action isnt verified for application status update event");
                assertEquals(payloadObject.get().getString("recordType"), "ApplicationStatus", "Record type verified for application update event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for application update event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "CreatedBy isnt valid for application update event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "UpdatedBy isnt valid for application update event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for application update event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for application update event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("correlationId").equals(correlationId), "correlationId didnt match for application update event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationStatusId").toString().chars().allMatch(Character::isDigit), "app statusId isnt digit for application status update event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("effectiveStartDate")), "effectiveStartDate field IS NOT valid for application status update event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveEndDate"), "effectiveEndDate field IS NOT null for application status update event");
                String status = payloadObject.get().getJSONObject("dataObject").getString("statusId");

                switch (status) {
                    case "Submitted":
                        assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationId")).equals(crmCreateApplicationStepDef.applicationId), "ApplicationId isnt verified for application status update event");
                        break;
                    case "Withdrawn":
                        assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationId")).equals(crmCreateApplicationStepDef.applicationId), "ApplicationId isnt verified for application status update event");
                        String actualStatusReason = String.valueOf(payloadObject.get().getJSONObject("dataObject").get("statusReason"));
                        if ("Already Receiving Services".equals(actualStatusReason)) {
                            assertTrue(true);
                        } else if ("Not Interested in Services".equals(actualStatusReason)) {
                            assertTrue(true);
                        } else {
                            fail("Status reason for withdrawl did not matched. Actual: " + actualStatusReason);
                        }
                        break;
                    case "Determining":
                        assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationId")).equals(crmCreateApplicationStepDef.applicationId), "ApplicationId isnt verified for application status update event");
                        break;
                    case "Duplicate":
                        assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationId")).equals(duplicateId), "ApplicationId isnt verified for application status update event");
                        break;
                    default:
                        fail("Application status isnt verified for application status update event");
                }
                System.out.println("Application status save event is verified");
                break;

            case "APPLICATION_CONSUMER_STATUS_SAVE_EVENT":
                assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project id isnt correct");
                assertEquals(payloadObject.get().getString("action"), "Create", "Action isn't verified for application consumer status save event");
                assertEquals(payloadObject.get().getString("recordType"), "ApplicationConsumerStatusEvent", "Record type isn't verified for application consumer status save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for application consumer status  save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerStatusId").toString().chars().allMatch(Character::isDigit), "app consumer statusId isnt digit for application consumer status  save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerId").toString().chars().allMatch(Character::isDigit), "ApplicationConsumerId isnt verified for application status save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").getString("statusId").equals("Received"), "Application status isnt verified for application consumer status save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("effectiveStartDate")), "effectiveStartDate field IS NOT valid for application consumer status save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveEndDate"), "effectiveEndDate field IS NOT null for application consumer status save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for  application consumer status  save event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "CreatedBy isnt valid for application consumer status  save event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "UpdatedBy isnt valid for application consumer status  save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for application consumer status  save event");
                System.out.println("Application consumer status save event is verified");
                break;

            case "APPLICANT_ADDRESS_SAVE_EVENT":
                assertEquals(payloadObject.get().getInt("projectId") + "",
                        (ConfigurationReader.getProperty("projectName").split(" "))[0],
                        "Project Id is not correct");
                assertEquals(payloadObject.get().getString("projectName"),
                        (ConfigurationReader.getProperty("projectName").split(" "))[1],
                        "Project Name is not a correct");
                assertEquals(payloadObject.get().getString("action"), "Create", "Action isnt verified for application update event");
                assertEquals(payloadObject.get().getString("recordType"), "ApplicationConsumerAddressEvent", "Record type verified for application save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for application save event");
                assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationId")).equals(crmCreateApplicationStepDef.applicationId), "ApplicationId isnt verified for application save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerId").toString().chars().allMatch(Character::isDigit), "ApplicationConsumerId isnt verified for save event");
                assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerType")).equals(crmCreateApplicationStepDef.applicantInformation.get("applicationConsumerType")), "Consumer Type isnt verified for save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("correlationId").equals(correlationId), "correlationId didnt match for save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressStreet1").equals(CRM_CreateApplicationStepDef.applicantInformation.get("addressStreet1")), "Address is not verified for address save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressStreet2").equals(CRM_CreateApplicationStepDef.applicantInformation.get("addressStreet2")), "Address is not verified for address save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressState").equals(CRM_CreateApplicationStepDef.applicantInformation.get("addressState")), "Address is not verified for address save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressCity").equals(CRM_CreateApplicationStepDef.applicantInformation.get("addressCity")), "Address is not verified for address save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressZip").equals(CRM_CreateApplicationStepDef.applicantInformation.get("addressZip")), "Address is not verified for address save event");
                if (crmCreateApplicationStepDef.applicantInformation.get("mailing").equalsIgnoreCase("true"))
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("addressType"), "Mailing", "Address is not verified for address save event");
                if (crmCreateApplicationStepDef.applicantInformation.get("residential").equalsIgnoreCase("true")) {
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("addressType"), "Residential", "Address is not verified for address save event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressCounty").equals(crmCreateApplicationStepDef.applicantInformation.get("addressCounty")), "Address is not verified for address save event");
                }
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "CreatedBy isnt valid for save event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "UpdatedBy isnt valid for save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for save event");
                assertNotNull(payloadObject.get().getJSONObject("dataObject").getInt("applicantAddressId"), "applicantAddressId is empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("addressAttn"), "addressAttn is not empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("addressHouseCode"), "addressHouseCode is not empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("addressZipFour"), "addressZipFour is not empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveStartDate"), "effectiveStartDate is not empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveEndDate"), "effectiveEndDate is not empty");
                break;
            case "APPLICANT_ADDRESS_UPDATE_EVENT":
                assertEquals(payloadObject.get().getInt("projectId") + "",
                        (ConfigurationReader.getProperty("projectName").split(" "))[0],
                        "Project Id is not correct");
                assertEquals(payloadObject.get().getString("projectName"),
                        (ConfigurationReader.getProperty("projectName").split(" "))[1],
                        "Project Name is not a correct");
                assertEquals(payloadObject.get().getString("action"), "Update", "Action isnt verified for application update event");
                assertEquals(payloadObject.get().getString("recordType"), "ApplicationConsumerAddressEvent", "Record type verified for application save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for application save event");
                assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationId")).equals(apiApplicationId.get()), "ApplicationId isnt verified for application save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerId").toString().chars().allMatch(Character::isDigit), "ApplicationConsumerId isnt verified for save event");
                assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerType")).equals(crmCreateApplicationStepDef.applicantInformation.get("applicationConsumerType")), "Consumer Type isnt verified for save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("correlationId").equals(correlationId), "correlationId didnt match for save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressStreet1").equals(crmCreateApplicationStepDef.applicantInformation.get("addressStreet1")), "Address is not verified for address save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressStreet2").equals(crmCreateApplicationStepDef.applicantInformation.get("addressStreet2")), "Address is not verified for address save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressState").equals(crmCreateApplicationStepDef.applicantInformation.get("addressState")), "Address is not verified for address save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressCity").equals(crmCreateApplicationStepDef.applicantInformation.get("addressCity")), "Address is not verified for address save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressZip").equals(crmCreateApplicationStepDef.applicantInformation.get("addressZip")), "Address is not verified for address save event");
                if (crmCreateApplicationStepDef.applicantInformation.get("mailing").equalsIgnoreCase("true"))
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("addressType"), "Mailing", "Address is not verified for address save event");
                if (crmCreateApplicationStepDef.applicantInformation.get("residential").equalsIgnoreCase("true")) {
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("addressType"), "Residential", "Address is not verified for address save event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").get("addressCounty").equals(crmCreateApplicationStepDef.applicantInformation.get("addressCounty")), "Address is not verified for address save event");
                }
                assertNotEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), payloadObject.get().getJSONObject("dataObject").getString("updatedBy"));
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "UpdatedBy isnt valid for save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for save event");
                assertNotEquals(payloadObject.get().getJSONObject("dataObject").getString("createdOn"), payloadObject.get().getJSONObject("dataObject").getString("updatedOn"));
                assertTrue(payloadObject.get().getJSONObject("dataObject").getString("updatedOn").contains(apiTestDataUtil.getCurrentDateAndTime("yyyy-MM-dd'T'")));
                assertNotNull(payloadObject.get().getJSONObject("dataObject").getInt("applicantAddressId"), "applicantAddressId is empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("addressAttn"), "addressAttn is not empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("addressHouseCode"), "addressHouseCode is not empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("addressZipFour"), "addressZipFour is not empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveStartDate"), "effectiveStartDate is not empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveEndDate"), "effectiveEndDate is not empty");
                break;

            case "APPLICANT_ADDRESS_DELETE_EVENT":
                assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project id isnt correct");
                assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project name isnt correct");
                assertEquals(payloadObject.get().getString("action"), "Delete", "Action isnt verified for application update event");
                assertEquals(payloadObject.get().getString("recordType"), "ApplicationConsumerAddressDeleteEvent", "Record type verified for application save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for application save event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "UpdatedBy isnt valid for application save event");
                assertNotNull(payloadObject.get().getJSONObject("dataObject").getInt("applicantAddressId"), "applicantAddressId is empty");
                break;

            case "APPLICANT_PHONE_SAVE_EVENT":
                assertEquals(payloadObject.get().getInt("projectId") + "",
                        (ConfigurationReader.getProperty("projectName").split(" "))[0],
                        "Project Id is not correct");
                assertEquals(payloadObject.get().getString("projectName"),
                        (ConfigurationReader.getProperty("projectName").split(" "))[1],
                        "Project Name is not a correct");
                assertEquals(payloadObject.get().getString("action"), "Create", "Action isnt verified for save event");
                assertEquals(payloadObject.get().getString("recordType"), "ApplicationConsumerPhoneEvent", "Record type verified for save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for save event");
                assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationId")).equals(crmCreateApplicationStepDef.applicationId), "ApplicationId isnt verified for save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerId").toString().chars().allMatch(Character::isDigit), "ApplicationConsumerId isnt verified for save event");
                assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerType")).equals(crmCreateApplicationStepDef.applicantInformation.get("applicationConsumerType")), "Consumer Type isnt verified for save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("correlationId").equals(correlationId), "correlationId didnt match for save event");
                assertNotNull(payloadObject.get().getJSONObject("dataObject").getInt("applicantPhoneId"), "applicantPhoneId is empty");
                assertNotNull(payloadObject.get().getJSONObject("dataObject").get("canReceiveTextInd"), "canReceiveTextInd is empty");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for save event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "CreatedBy isnt valid for save event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "UpdatedBy isnt valid for save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveStartDate"), "effectiveStartDate is not empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveEndDate"), "effectiveEndDate is not empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("phoneNumber").equals(crmCreateApplicationStepDef.applicantInformation.get("contactNumber")), "Contact Number is not verified for phone save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("phoneType").equals(crmCreateApplicationStepDef.applicantInformation.get("phoneType")), "Phone type is not verified for phone save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("primaryIndicator") instanceof Boolean, "primaryIndicator is not empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("smsEnabledInd") instanceof Boolean, "smsEnabledInd is not empty");
                break;
            case "APPLICANT_PHONE_UPDATE_EVENT":
                assertEquals(payloadObject.get().getInt("projectId") + "",
                        (ConfigurationReader.getProperty("projectName").split(" "))[0],
                        "Project Id is not correct");
                assertEquals(payloadObject.get().getString("projectName"),
                        (ConfigurationReader.getProperty("projectName").split(" "))[1],
                        "Project Name is not a correct");
                assertEquals(payloadObject.get().getString("action"), "Update", "Action isnt verified for save event");
                assertEquals(payloadObject.get().getString("recordType"), "ApplicationConsumerPhoneEvent", "Record type verified for save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for save event");
                assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationId")).equals(apiApplicationId.get()), "ApplicationId isnt verified for application update event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerId").toString().chars().allMatch(Character::isDigit), "ApplicationConsumerId isnt verified for save event");
                assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerType")).equals(crmCreateApplicationStepDef.applicantInformation.get("applicationConsumerType")), "Consumer Type isnt verified for save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("correlationId").equals(correlationId), "correlationId didnt match for save event");
                assertNotNull(payloadObject.get().getJSONObject("dataObject").getInt("applicantPhoneId"), "applicantPhoneId is empty");
                assertNotNull(payloadObject.get().getJSONObject("dataObject").get("canReceiveTextInd"), "canReceiveTextInd is empty");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for save event");
                assertNotEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), "CreatedBy, UpdatedBy is the same for Update event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for save event");
                assertNotEquals(payloadObject.get().getJSONObject("dataObject").getString("createdOn"), payloadObject.get().getJSONObject("dataObject").getString("updatedOn"));
                assertTrue(payloadObject.get().getJSONObject("dataObject").getString("updatedOn").contains(apiTestDataUtil.getCurrentDateAndTime("yyyy-MM-dd'T'")));
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveStartDate"), "effectiveStartDate is not empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveEndDate"), "effectiveEndDate is not empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("phoneNumber").equals(crmCreateApplicationStepDef.applicantInformation.get("contactNumber")), "Contact Number is not verified for phone save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("phoneType").equals(crmCreateApplicationStepDef.applicantInformation.get("phoneType")), "Phone type is not verified for phone save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("primaryIndicator") instanceof Boolean, "primaryIndicator is not empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("smsEnabledInd") instanceof Boolean, "smsEnabledInd is not empty");
                break;

            case "APPLICANT_PHONE_DELETE_EVENT":
                assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project id isnt correct");
                assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project name isnt correct");
                assertEquals(payloadObject.get().getString("action"), "Delete", "Action isnt verified for application update event");
                assertEquals(payloadObject.get().getString("recordType"), "ApplicationConsumerPhoneDeleteEvent", "Record type verified for application save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for application save event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "UpdatedBy isnt valid for application save event");
                assertNotNull(payloadObject.get().getJSONObject("dataObject").getInt("applicantPhoneId"), "applicantPhoneId is empty");
                break;

            case "APPLICANT_EMAIL_SAVE_EVENT":
                assertEquals(payloadObject.get().getInt("projectId") + "",
                        (ConfigurationReader.getProperty("projectName").split(" "))[0],
                        "Project Id is not correct");
                assertEquals(payloadObject.get().getString("projectName"),
                        (ConfigurationReader.getProperty("projectName").split(" "))[1],
                        "Project Name is not a correct");
                assertEquals(payloadObject.get().getString("action"), "Create", "Action isnt verified for save event");
                assertEquals(payloadObject.get().getString("recordType"), "ApplicationConsumerEmailEvent", "Record type verified for save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for save event");
                assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationId")).equals(crmCreateApplicationStepDef.applicationId), "ApplicationId isnt verified for save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerId").toString().chars().allMatch(Character::isDigit), "ApplicationConsumerId isnt verified for save event");
                assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerType")).equals(crmCreateApplicationStepDef.applicantInformation.get("applicationConsumerType")), "Consumer Type isnt verified for save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("correlationId").equals(correlationId), "correlationId didnt match for save event");
                assertNotNull(payloadObject.get().getJSONObject("dataObject").getInt("applicantEmailId"), "applicantEmailId is empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("emailType"), "emailType is not empty");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for save event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "CreatedBy isnt valid for save event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "UpdatedBy isnt valid for save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveStartDate"), "effectiveStartDate is not empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveEndDate"), "effectiveEndDate is not empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("emailAddress").equals(crmCreateApplicationStepDef.applicantInformation.get("emailAddress")), "Email is not verified for email save event");
                break;

            case "APPLICANT_EMAIL_UPDATE_EVENT":
                assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project id isnt correct");
                assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project name isnt correct");
                assertEquals(payloadObject.get().getString("action"), "Update", "Action isnt verified for update event");
                assertEquals(payloadObject.get().getString("recordType"), "ApplicationConsumerEmailEvent", "Record type verified for save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for save event");
                assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationId")).equals(apiApplicationId.get()), "ApplicationId isnt verified for application save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerId").toString().chars().allMatch(Character::isDigit), "ApplicationConsumerId isnt verified for save event");
                assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerType")).equals(crmCreateApplicationStepDef.applicantInformation.get("applicationConsumerType")), "Consumer Type isnt verified for save event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("correlationId").equals(correlationId), "correlationId didnt match for save event");
                assertNotNull(payloadObject.get().getJSONObject("dataObject").getInt("applicantEmailId"), "applicantEmailId is empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("emailType"), "emailType is not empty");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for save event");
                assertNotEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), "CreatedBy isnt valid for save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for save event");
                assertNotEquals(payloadObject.get().getJSONObject("dataObject").getString("createdOn"), payloadObject.get().getJSONObject("dataObject").getString("updatedOn"));
                assertTrue(payloadObject.get().getJSONObject("dataObject").getString("updatedOn").contains(apiTestDataUtil.getCurrentDateAndTime("yyyy-MM-dd'T'")));
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveStartDate"), "effectiveStartDate is not empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveEndDate"), "effectiveEndDate is not empty");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("emailAddress").equals(crmCreateApplicationStepDef.applicantInformation.get("emailAddress")), "Email is not verified for email update event");
                break;

            case "APPLICANT_EMAIL_DELETE_EVENT":
                assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project id isnt correct");
                assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project name isnt correct");
                assertEquals(payloadObject.get().getString("action"), "Delete", "Action isnt verified for application update event");
                assertEquals(payloadObject.get().getString("recordType"), "ApplicationConsumerEmailDeleteEvent", "Record type verified for application save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for application save event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "UpdatedBy isnt valid for application save event");
                assertNotNull(payloadObject.get().getJSONObject("dataObject").getInt("applicantEmailId"), "applicantPhoneId is empty");
                break;

            case "APPLICATION_CONSUMER_UPDATE_EVENT":
            case "APPLICATION_CONSUMER_SAVE_EVENT":
                if (eventName.equals("APPLICATION_CONSUMER_UPDATE_EVENT")) {
                    assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project id isnt correct");
                    assertEquals(payloadObject.get().getString("action"), "Update", "Action isnt verified for application consumer update event");
                    assertFalse(payloadObject.get().getJSONObject("dataObject").getString("createdBy").equals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy")), " createdBy and updatedBy values arent verified for consumer update event");
                    assertFalse(payloadObject.get().getJSONObject("dataObject").getString("createdOn").equals(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), " createdOn and updatedOn values arent verified for consumer update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), apiUserID, "CreatedBy isnt valid for application consumer update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), loggedInUserId, "UpdatedBy isnt valid for application consumer update event");
                    System.out.println("ApplicationId from UI when it is saved from API:" + applicationId);
                    assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationId")).equals(applicationId), "ApplicationId isnt verified for application consumer update event");
                } else if (eventName.equals("APPLICATION_CONSUMER_SAVE_EVENT")) {
                    assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project id isnt correct");
                    assertEquals(payloadObject.get().getString("action"), "Create", "Action isnt verified for application consumer save event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "CreatedBy isnt valid for application consumer save event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "UpdatedBy isnt valid for application consumer save event");
                    assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for application consumer save event");
                    assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for application consumer save event");
                    System.out.println("ApplicationId from UI:" + crmCreateApplicationStepDef.applicationId);
                    assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationId")).equals(crmCreateApplicationStepDef.applicationId), "ApplicationId isnt verified for application consumer save event");
                }
                assertEquals(payloadObject.get().getString("recordType"), "ApplicationConsumerEventRequest", "Record type verified for application save or update event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for application save or update event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("correlationId").equals(correlationId), "correlationId didnt match for application consumer save or update event");

                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerId").toString().chars().allMatch(Character::isDigit), "consumerId isnt digit for application consumer save or update event");

                if (payloadObject.get().getJSONObject("dataObject").get("consumerRoleType").toString().equals("Primary Individual")) {
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("consumerLastName").toString(), (crmCreateApplicationStepDef.piLastName), "Last name isnt verified for application consumer save or update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("consumerFirstName").toString(), (crmCreateApplicationStepDef.piFirstName), "First name isnt verified for application consumer save or update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("consumerMiddleName").toString(), (crmCreateApplicationStepDef.piMiddleInitial), "Middle name isnt verified for application consumer save or update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("consumerRoleType").toString(), ("Primary Individual"), "Consumer role type isnt verified for application consumer save or update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("consumerMiddleName").toString(), (crmCreateApplicationStepDef.piMiddleInitial), "Middle name isnt verified for application consumer save or update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("consumerSuffix").toString(), (crmCreateApplicationStepDef.piSuffix), "Suffix isnt verified for application consumer save or update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("dateOfBirth").toString(), BrowserUtils.convertMMddyyyyToyyyyMMdd(crmCreateApplicationStepDef.piDOB), "DOB isnt verified for application consumer save or update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("expectedBabies").toString(), (crmCreateApplicationStepDef.piBabiesExpected), "Expected babies isnt verified for application consumer save or update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("expectedDueDate").toString(), BrowserUtils.convertMMddyyyyToyyyyMMdd(crmCreateApplicationStepDef.piExpectedDueDate), "Expected due date isnt verified for application consumer save or update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("externalConsumerId").toString(), (crmCreateApplicationStepDef.piExtConId), "External consumer id isnt verified for application consumer save or update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("externalConsumerIdType").toString(), (crmCreateApplicationStepDef.piExtIdType), "External id type isnt verified for application consumer save or update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("genderCode").toString(), (crmCreateApplicationStepDef.piGender), "Gender isnt verified for application consumer save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").get("pregnancyInd").equals(true), "Pregnancy ind isnt verified for application consumer save or update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("spokenLanguage").toString(), (crmCreateApplicationStepDef.piSpokenLanguage), "Spoken language isnt verified for application consumer save or update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("writtenLanguage").toString(), (crmCreateApplicationStepDef.piWrittenLanguage), "Written language isnt verified for application consumer save or update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("consumerSsn").toString(), (crmCreateApplicationStepDef.piSSN), "SSN isnt verified for application consumer save or update event");

                    //Program array verification
                    assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getString("programId").equals("CHIP"), "First program isnt verified for application consumer save or update event");
                    assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getInt("applicationProgramId")).chars().allMatch(Character::isDigit), "Program id isnt verified for application consumer save or update event");
                    assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getInt("applicationConsumerId")).chars().allMatch(Character::isDigit), "Consumer id isnt verified for application consumer save or update event");
                    assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getString("effectiveStartDate")), "Effective start date for programs isnt verified for application consumer save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).isNull("effectiveEndDate"), "Effective end date for programs isnt verified for application consumer save or update event");
                    assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getString("createdOn")), "CreatedOn for program isnt verified for application consumer save or update event");
                    assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getString("updatedOn")), "Updated for program isnt verified for application consumer save or update event");
                    if ("APPLICATION_CONSUMER_SAVE_EVENT".equals(eventName)) {
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getString("createdBy"), loggedInUserId, "CreatedBy for program isnt valid for application consumer save or update event");
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getString("updatedBy"), loggedInUserId, "UpdatedBy for program isnt valid for application consumer save or update event");
                    }
                    if ("APPLICATION_CONSUMER_UPDATE_EVENT".equals(eventName)) {
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getString("createdBy"), "2492", "CreatedBy for program isnt valid for application consumer save or update event");
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getString("updatedBy"), loggedInUserId, "UpdatedBy for program isnt valid for application consumer save or update event");
                    }
                    //Consumer opt in details array
                    assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerOptInInformation").getJSONObject(0).getInt("applicationConsentId")).chars().allMatch(Character::isDigit), "applicationConsentId isnt verified for application consumer save or update event");
                    assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerOptInInformation").getJSONObject(0).getInt("applicationConsumerId")).chars().allMatch(Character::isDigit), "applicationConsumerId isnt verified for application consumer save or update event");
                    assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerOptInInformation").getJSONObject(0).getString("dateOfConsent")), "dateOfConsent isnt verified for application consumer save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerOptInInformation").getJSONObject(0).getString("applicationConsentType").equals("Mail"), "applicationConsentType isnt verified for application consumer save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerOptInInformation").getJSONObject(0).getBoolean("optIn"), "optIn isnt verified for application consumer save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerOptInInformation").getJSONObject(0).isNull("effectiveEndDate"), "Effective end date for optIn isnt verified for application consumer save or update event");
                    assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerOptInInformation").getJSONObject(0).getString("createdOn")), "CreatedOn for optIn isnt verified for application consumer save or update event");
                    assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerOptInInformation").getJSONObject(0).getString("updatedOn")), "Updated for optIn isnt verified for application consumer save or update event");
                    if ("APPLICATION_CONSUMER_SAVE_EVENT".equals(eventName)){
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerOptInInformation").getJSONObject(0).getString("createdBy"),loggedInUserId, "CreatedBy for optIn isnt valid for application consumer save or update event");
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerOptInInformation").getJSONObject(0).getString("updatedBy"), loggedInUserId, "UpdatedBy for optIn isnt valid for application consumer save or update event");
                    }
                    if ("APPLICATION_CONSUMER_UPDATE_EVENT".equals(eventName)){
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerOptInInformation").getJSONObject(0).getString("createdBy"),"2492", "CreatedBy for optIn isnt valid for application consumer save or update event");
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("consumerOptInInformation").getJSONObject(0).getString("updatedBy"), loggedInUserId, "UpdatedBy for optIn isnt valid for application consumer save or update event");
                    }
                    System.out.println("Primary individual is verified");

                } else if (payloadObject.get().getJSONObject("dataObject").get("consumerRoleType").toString().equals("Application Member")) {
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("consumerLastName").toString(), (crmCreateApplicationMemberStepDef.lastName.get()), "Last name isnt verified for application consumer save or update event for app member");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("consumerFirstName").toString(), (crmCreateApplicationMemberStepDef.firstName.get()), "First name isnt verified for application consumer save or update event for app member");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("consumerMiddleName").toString(), (crmCreateApplicationMemberStepDef.mi.get()), "Middle name isnt verified for application consumer save or update event for app member");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("consumerRoleType").toString(), ("Application Member"), "Consumer role type isnt verified for application consumer save or update event for app member");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("consumerSuffix").toString(), (crmCreateApplicationMemberStepDef.suffix.get()), "Suffix isnt verified for application consumer save or update event for app member");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("dateOfBirth").toString(), BrowserUtils.convertMMddyyyyToyyyyMMdd(crmCreateApplicationMemberStepDef.dob.get()), "DOB isnt verified for application consumer save or update event for app member");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("expectedBabies").toString(), (crmCreateApplicationMemberStepDef.numOfBabies.get()), "Expected babies isnt verified for application consumer save or update event for app member");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("expectedDueDate").toString(), BrowserUtils.convertMMddyyyyToyyyyMMdd(crmCreateApplicationMemberStepDef.dueDate.get()), "Expected due date isnt verified for application consumer save or update event for app member");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("externalConsumerId").toString(), (crmCreateApplicationMemberStepDef.consumerID.get()), "External consumer id isnt verified for application consumer save or update event for app member");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("externalConsumerIdType").toString(), (crmCreateApplicationMemberStepDef.consumerType.get()), "External id type isnt verified for application consumer save or update event for app member");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("genderCode").toString(), (crmCreateApplicationMemberStepDef.gender.get()), "Gender isnt verified for application consumer save or update event for app member");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").get("pregnancyInd").equals(true), "Pregnancy ind isnt verified for application consumer save or update event for app member");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("spokenLanguage"), "Spoken language isnt verified for application consumer save or update event for app member");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("writtenLanguage"), "Written language isnt verified for application consumer save or update event for app member");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("consumerSsn").toString(), (crmCreateApplicationMemberStepDef.ssn.get()), "SSN isnt verified for application consumer save or update event for app member");

                    //Program array verification
                    assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getString("programId").equals("Medicaid"), "First program isnt verified for application consumer save or update event for app member");
                    assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getInt("applicationProgramId")).chars().allMatch(Character::isDigit), "Program id isnt verified for application consumer save or update event for app member");
                    assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getInt("applicationConsumerId")).chars().allMatch(Character::isDigit), "Consumer id isnt verified for application consumer save or update event for app member");
                    assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getString("effectiveStartDate")), "Effective start date for programs isnt verified for application consumer save or update event for app member");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).isNull("effectiveEndDate"), "Effective end date for programs isnt verified for application consumer save or update event for app member");
                    assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getString("createdOn")), "CreatedOn for program isnt verified for application consumer save or update event for app member");
                    assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getString("updatedOn")), "Updated for program isnt verified for application consumer save or update event for app member");
                    if ("APPLICATION_CONSUMER_SAVE_EVENT".equals(eventName)) {
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getString("createdBy"), loggedInUserId, "CreatedBy for program isnt valid for application consumer save or update event for app member");
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getString("updatedBy"), loggedInUserId, "UpdatedBy for program isnt valid for application consumer save or update event for app member");
                    }
                    if ("APPLICATION_CONSUMER_UPDATE_EVENT".equals(eventName)) {
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getString("createdBy"), "2492", "CreatedBy for program isnt valid for application consumer save or update event for app member");
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONArray("programs").getJSONObject(0).getString("updatedBy"), loggedInUserId, "UpdatedBy for program isnt valid for application consumer save or update event for app member");
                    }

                    //Consumer opt in details array
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerOptInInformation"), "applicationConsentId isnt verified for application consumer save or update event for app member");
                    System.out.println("Application member is verified");
                } else if (payloadObject.get().getJSONObject("dataObject").get("consumerRoleType").toString().equals("Primary Individual") || payloadObject.get().getJSONObject("dataObject").get("consumerRoleType").toString().equals("Application Member")) {
                    //Null fields
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("authorizedRepAppEndDate"), "authorizedRepAppEndDate field IS NOT valid for application consumer save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("authorizedRepAppStartDate"), "authorizedRepAppStartDate field IS NOT valid for application consumer save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("authorizedRepExternalId"), "authorizedRepExternalId field IS NOT valid for application consumer save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("authorizedRepOrgName"), "authorizedRepOrgName field IS NOT valid for application consumer save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("authorizedRepSignatureDate"), "authorizedRepSignature date field IS NOT valid for application consumer save or update event");
                    assertFalse(payloadObject.get().getJSONObject("dataObject").getBoolean("authorizedRepSignature"), "authorizedRepSignature field IS NOT valid for application consumer save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("citizenship"), "citizenship field IS NOT valid for application consumer save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerStatus"), "consumer status field IS NOT valid for application consumer save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerType"), "consumer type field IS NOT valid for application consumer save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveStartDate"), "effectiveStartDate field IS NOT valid for application consumer save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveEndDate"), "effectiveEndDate field IS NOT null for application consumer save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("correspondence"), "correspondence field IS NOT valid for application consumer save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("accessType"), "access type field IS NOT valid for application consumer save or update event");
                    System.out.println("Common parts are verified for authorized representative and application member");
                } else if (payloadObject.get().getJSONObject("dataObject").get("consumerRoleType").toString().equals("Authorized Rep")) {
                    assertTrue(payloadObject.get().getJSONObject("dataObject").get("authorizedRepAppEndDate").toString().startsWith(BrowserUtils.convertMMddyyyyToyyyyMMdd(authorizedRepStepDef.endDate)), "Authorized rep end date isnt verified for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").get("authorizedRepAppStartDate").toString().startsWith(BrowserUtils.convertMMddyyyyToyyyyMMdd(authorizedRepStepDef.startDate)), "Authorized rep start date isnt verified for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").get("authorizedRepExternalId").toString().equals(authorizedRepStepDef.idNum), "External id isnt matched for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").get("authorizedRepOrgName").toString().equals(authorizedRepStepDef.orgName), "Org name isnt verified for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").getBoolean("authorizedRepSignature"), "Auth rep signature isnt verified for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").get("authorizedRepSignatureDate").toString().startsWith(BrowserUtils.convertMMddyyyyToyyyyMMdd(authorizedRepStepDef.authSignDate)), "Auth rep signature date isnt verified for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("citizenship"), "Consumer isnt verified for auth rep save or update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("consumerLastName").toString(), (authorizedRepStepDef.authRepLastName), "Last name isnt verified for auth rep save or update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("consumerFirstName").toString(), (authorizedRepStepDef.authRepFirstName), "First name isnt verified for auth rep save or update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("consumerMiddleName").toString(), (authorizedRepStepDef.authRepMi), "Middle name isnt verified for auth rep save or update event");
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("consumerRoleType").toString(), ("Authorized Rep"), "Consumer role type isnt verified for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerStatus"), "Status isnt verified for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerSuffix"), "Suffix isnt verified for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").get("consumerType").toString().equals(authorizedRepStepDef.authType), "Auth type isnt verified for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("dateOfBirth"), "DOB isnt verified for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveEndDate"), "Effective end date isnt verified for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveEndDate"), "Effective end date isnt verified for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveStartDate"), "Effective start date isnt verified for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("expectedBabies"), "Expected babies isnt verified for application consumer save or update event for app member");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("expectedDueDate"), "Expected due date isnt verified for application consumer save or update event for app member");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("externalConsumerId"), "External consumer id isnt verified for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("externalConsumerIdType"), "External id type isnt verified for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("genderCode"), "Gender isnt verified for auth rep save or update event");
                    assertFalse(payloadObject.get().getJSONObject("dataObject").getBoolean("pregnancyInd"), "Pregnancy ind isnt verified for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("spokenLanguage"), "Spoken language isnt verified for aauth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("writtenLanguage"), "Written language isnt verified for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerSsn"), "SSN isnt verified for aauth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").get("correspondence").toString().equals(authorizedRepStepDef.correspondence), "Correspondence isnt verified for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").get("accessType").toString().equals(authorizedRepStepDef.accessType), "Access type isnt verified for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("programs"), "programs IS NOT valid for auth rep save or update event");
                    assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerOptInInformation"), "consumerOptInInformation NOT valid for auth rep save or update event");
                    System.out.println("Authorized representative is verified");
                }

                System.out.println("Application consumer save OR update event is verified");

                break;
            case "APPLICATION_CONSUMER_ELIGIBILITY_UPDATE_EVENT":
                assertEquals(payloadObject.get().getInt("projectId") + "", (ConfigurationReader.getProperty("projectName").split(" "))[0], "Project Id is not correct");
                assertEquals(payloadObject.get().getString("projectName"), (ConfigurationReader.getProperty("projectName").split(" "))[1], "Project Name is not a correct");
                assertEquals(payloadObject.get().getString("action"), "Update", "Action isnt verified for event");
                assertEquals(payloadObject.get().getString("recordType"), "Application Consumer Eligibility", "Record type not verified for event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("applicationConsumerType"), "Primary Individual", "ApplicationConsumer Type type verified for event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), "3163", "Created By not verified for event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), loggedInUserId, "Updated By not verified for event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getLong("applicationId"), Long.parseLong(applicationIdAPI.get()), "Application Id not verified for event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("eligibilityStatus"), "Eligible", "Eligibility Status type verified for event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("startDate"), getCurrentDateInYearFormat(), "Start Date not verified for event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("determinationDate"), getCurrentDateInYearFormat(), "Determination Date not verified for event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for application consumer save event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for application consumer save event");
                break;
            case "APPLICATION_MISSING_INFORMATION":
                for (Map<String, Object> event : APIATSSendEventAndCreateLinksController.events.get()) {
                    payloadObject.set(new JSONObject(event.get("payload").toString()));
                    if (applicationIdAPI.equals("" + payloadObject.get().getJSONObject("dataObject").getJSONObject("applicationInfo").get("applicationId"))) {
                        eventCount.set(eventCount.get()+1);


                        assertEquals(payloadObject.get().getInt("projectId") + "", (ConfigurationReader.getProperty("projectName").split(" "))[0], "Project Id is not correct");
                        assertEquals(payloadObject.get().getString("projectName"), (ConfigurationReader.getProperty("projectName").split(" "))[1], "Project Name is not a correct");
                        assertEquals(payloadObject.get().getString("action"), "Create", "Action isnt verified for event");
                        assertEquals(payloadObject.get().getString("recordType"), "APPLICATION_STATUS_UPDATE_EVENT", "Record type not verified for event");
                        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for event");
                        assertTrue(payloadObject.get().getJSONObject("dataObject").get("missingInfoItemId").toString().chars().allMatch(Character::isDigit), "Missing Info Item ID is not all digits for event");
                        String fromName = payloadObject.get().getJSONObject("dataObject").get("missingInformationFrom").toString();
                        assertTrue(fromName.contains(piFirstName.get())||fromName.contains(amFirstName.get()), "MI From is not verified");
                        assertEquals(payloadObject.get().getJSONObject("dataObject").get("missingInformationStatus"), "PENDING", "MI status is not correct");
                        assertEquals(payloadObject.get().getJSONObject("dataObject").get("missingInformationDueDate"), getFutureDateFormatYYYYMMdd(45 - 7), "MI due date is not correct");
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("miCreatedBy"), apiUserID, "CreatedBy isnt valid for " + eventName);
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("miUpdatedBy"), apiUserID, "UpdatedBy isnt valid for " + eventName);
                        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("miCreatedOn")), "CreatedOn date time field IS NOT valid for event");
                        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("miUpdatedOn")), "UpdatedOn date time field IS NOT valid for event");
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("applicationInfo").get("applicationId").toString(), applicationIdAPI.get(), "Application ID is not correct");
                        String appConsumerId = payloadObject.get().getJSONObject("dataObject").getJSONObject("applicationInfo").get("applicationConsumerId").toString();
                        assertTrue(appConsumerId.contains(getKeyFromValue(createdConsumerDetails.get(), "Primary Individual"))||appConsumerId.contains(getKeyFromValue(createdConsumerDetails.get(), "Application Member")), "Application Consumer ID is not correct");
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("applicationInfo").getString("applicationStatus"), "Determining", "Application status is not correct");
                        assertNotNull(payloadObject.get().getJSONObject("dataObject").getJSONObject("applicationInfo").getString("channel"), "Application channel is null");
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("applicationInfo").getString("applicationDeadlineDate"), getFutureDateFormatYYYYMMdd(45), "Application deadline date is not correct");
                        assertNotNull(payloadObject.get().getJSONObject("dataObject").getJSONObject("applicationInfo").get("caseId"), "Case id is null");
                        assertNotNull(payloadObject.get().getJSONObject("dataObject").getJSONObject("applicationInfo").get("consumerId"), "Consumer id is null");
                        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("externalApplicationId"), "Ext App ID is not null");
                        assertNotNull(payloadObject.get().getJSONObject("dataObject").getJSONObject("applicationInfo").getString("correlationId"), "correlationId is null");
                    }
                }
                break;
            case "NEW_APPLICATION":
                assertEquals(payloadObject.get().getInt("projectId") + "", (ConfigurationReader.getProperty("projectName").split(" "))[0], "Project Id is not correct");
                assertEquals(payloadObject.get().getString("projectName"), (ConfigurationReader.getProperty("projectName").split(" "))[1], "Project Name isnt verified for new application event");
                assertEquals(payloadObject.get().getString("action"), "Create", "Action isnt verified for new application event");
                assertEquals(payloadObject.get().getString("recordType"), "Application", "Record type verified for new application event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for new application event");
                assertEquals("" + payloadObject.get().getJSONObject("dataObject").get("applicationId"), crmCreateApplicationStepDef.applicationId, "Application Id isnt valid for new application event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("applicationSignatureDate"), "App sign date isnt null for new application event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("bussinessEventName"), "New Application", "business event name isnt valid for new application event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("channelId").equals(crmCreateApplicationStepDef.channel), "Channel isnt verified for new application event");
                if (payloadObject.get().getJSONObject("dataObject").get("applicationType").equals("Medical Assistance")) {
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("applicationType"), "Medical Assistance", "Expected  : " + "Medical Assistance" + " didn't match with actual application Type: " + payloadObject.get().getJSONObject("dataObject").get("applicationType"));
                    assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationName").equals("Medical Assistance - New"), "Application name isnt verified for new application event");
                }else  if (payloadObject.get().getJSONObject("dataObject").get("applicationType").equals("Long Term Care")) {
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("applicationType"), "Long Term Care", "Expected  : " + "Long Term Care" + " didn't match with actual application Type: " + payloadObject.get().getJSONObject("dataObject").get("applicationType"));
                    assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationName").equals("Long Term Care - New"), "Application name isnt verified for new application event");
                }
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationCycle").equals("New"), "Application cycle isnt verified for new application event");
                assertTrue(EventsUtilities.isOnlyValidDate(payloadObject.get().getJSONObject("dataObject").getString("applicationReceivedDate")), "Application Received Date isnt verified for new application event");
                assertFalse(payloadObject.get().getJSONObject("dataObject").getBoolean("applicationSignatureExistsInd"), "application Signature Exists Ind isnt false for new application event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for new application event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "CreatedBy isnt valid for new application event");
                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for new application event");
                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId.toString(), "UpdatedBy isnt valid for new application event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("correlationId").equals(correlationId), "correlationId didnt match for new application event");

                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("applicationDeadlineDate"), authorizedRepStepDef.i_get_the_application_deadline_date_on_application_tracking_page(), "Application deadline isnt valid for new application event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationPriority").equals("Normal"), "Application priority isnt null for new application event");
                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationReceivedLanguage").equals("English"), "Application Received Language isnt null for new application event");

                System.out.println("ApplicationId from UI:" + crmCreateApplicationStepDef.applicationId);
                if (extAppID != null) {
                    assertEquals(payloadObject.get().getJSONObject("dataObject").get("externalApplicationId"), extAppID, "External Application ID isnt verified for new application event");
                }
                System.out.println("NEW APPLICATION event is verified");
                break;
            case "APPLICATION_EXPIRATION":
                for (Map<String, Object> event : APIATSSendEventAndCreateLinksController.events.get()) {
                    payloadObject.set(new JSONObject(event.get("payload").toString()));
                    if (applicationIdAPI.equals("" + payloadObject.get().getJSONObject("dataObject").get("applicationId"))) {
                        eventCount.set(eventCount.get()+1);

                        assertEquals(payloadObject.get().getInt("projectId") + "", (ConfigurationReader.getProperty("projectName").split(" "))[0], "Project Id is not correct");
                        assertEquals(payloadObject.get().getString("projectName"), (ConfigurationReader.getProperty("projectName").split(" "))[1], "Project Name isnt verified for application expired event");
                        assertEquals(payloadObject.get().getString("action"), "Update", "Action isnt verified for application expired event");
                        assertEquals(payloadObject.get().getString("recordType"), "APPLICATION_STATUS_UPDATE_EVENT", "Record type isnt verified for application expired event");
                        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for application expired event");
                        assertEquals("" + payloadObject.get().getJSONObject("dataObject").get("applicationId"), applicationIdAPI.get(), "Expected  : " + applicationIdAPI.get() + " didn't match with actual application Id: " + "" + payloadObject.get().getJSONObject("dataObject").get("applicationId"));
                        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("externalApplicationId"), "externalApplicationId isnt null for application expired event");
                        assertTrue(payloadObject.get().getJSONObject("dataObject").getString("applicationCode").matches("[a-zA-Z0-9]*"), "Application Code isnt contains alphanumeric");
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("applicationStatus"), "Expired", "Application status isnt expiration");
                        assertTrue(payloadObject.get().getJSONObject("dataObject").get("channel").equals("Web"), "Channel isnt verified application expiration event");
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("applicationExpirationCreatedBy"), "ATS Service", "application expiration CreatedBy isnt valid for application expiration event");
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("applicationExpirationUpdatedBy"), "ATS Service", "application expiration UpdatedBy isnt valid for application expiration event");
                        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("applicationExpirationCreatedOn")), "application expiration CreatedOn date time field IS NOT valid for application expiration event");
                        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("applicationExpirationUpdatedOn")), "application expiration UpdatedOn date time field IS NOT valid for application expiration event");
                        assertNotNull(payloadObject.get().getJSONObject("dataObject").getString("correlationId"), "Correlation is null");
                        assertTrue(payloadObject.get().getJSONObject("dataObject").has("consumerId"), "Consumer Id isnt null for application expiration event");
                        assertTrue(payloadObject.get().getJSONObject("dataObject").has("caseId"), "Case Id isnt null for application expiration event");
                    }
                }
                break;

            case "APPLICATION_WITHDRAWN":
                for (Map<String, Object> event : APIATSSendEventAndCreateLinksController.events.get()) {
                    payloadObject.set(new JSONObject(event.get("payload").toString()));
                    String eventNameFromResp = (String) event.get("eventName");
                    if (eventNameFromResp.equalsIgnoreCase(eventName)) {
                        assertEquals(payloadObject.get().getInt("projectId") + "", (ConfigurationReader.getProperty("projectName").split(" "))[0], "Project Id is not correct");
                        assertEquals(payloadObject.get().getString("projectName"), (ConfigurationReader.getProperty("projectName").split(" "))[1], "Project Name isnt verified for application withdrawn event");
                        assertEquals(payloadObject.get().getString("action"), "Update", "Action isnt verified for application withdrawn event");
                        assertEquals(payloadObject.get().getString("recordType"), "APPLICATION_STATUS_UPDATE_EVENT", "Record type isnt verified for application withdrawn event");
                        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for application withdrawn event");
                        if (crmCreateApplicationStepDef.applicationId == null) {
                            assertEquals("" + payloadObject.get().getJSONObject("dataObject").get("applicationId"), applicationIdAPI.get(), "Expected  : " + applicationIdAPI.get() + " didn't match with actual application Id: " + "" + payloadObject.get().getJSONObject("dataObject").get("applicationId"));
                            assertTrue(payloadObject.get().getJSONObject("dataObject").get("channelId").equals("Web"), "Channel isnt verified application withdrawn event");
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("applicationWithdrawalCreatedBy"), apiUserID, "application Withdrawal CreatedBy isnt valid for application withdrawn event");
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("applicationWithdrawalUpdatedBy"), apiUserID, "application Withdrawal UpdatedBy isnt valid for application withdrawn event");
                        } else {
                            assertEquals("" + payloadObject.get().getJSONObject("dataObject").get("applicationId"), crmCreateApplicationStepDef.applicationId, "Application Id isnt valid for application withdrawn event");
                            assertTrue(payloadObject.get().getJSONObject("dataObject").get("channelId").equals(crmCreateApplicationStepDef.channel), "Channel isnt verified for application withdrawn event");
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("applicationWithdrawalCreatedBy"), loggedInUserId, "application Withdrawal CreatedBy isnt valid for application withdrawn event");
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("applicationWithdrawalUpdatedBy"), loggedInUserId, "application Withdrawal UpdatedBy isnt valid for application withdrawn event");
                            for (int i = 0; i < authorizedRepStepDef.caseIdFromATP.size(); i++) {
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("caseId"), authorizedRepStepDef.caseIdFromATP.get(i), "case Id is null for application withdrawn event");
                            }
                        }
                        assertTrue(payloadObject.get().getJSONObject("dataObject").getString("applicationCode").matches("[a-zA-Z0-9]*"), "Application Code isnt contains alphanumeric");
                        for (int i = 0; i < APIATSSendEventAndCreateLinksController.consumerIdList.get().size(); i++) {
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("applicationConsumerId"), "" + APIATSSendEventAndCreateLinksController.consumerIdList.get().get(i), "Application Consumer Id is Null for application withdrawn event");
                        }
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("applicationStatus"), "Withdrawn", "Application status isnt withdrawn");
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("withdrawalReason"), CRM_WithdrawnApplicationConfiguringPermissionsSetpDef.alreadyReceivingServiceReason.get(), "Already Receiving Services Application withdrawal reason isnt matching");
                        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerId"), "Consumer Id isnt null for application withdrawn event");
                        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("caseId"), "Case Id isnt null for application withdrawn event");
                        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("applicationWithdrawalCreatedOn")), "application Withdrawal CreatedOn date time field IS NOT valid for application withdrawn event");
                        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("applicationWithdrawalUpdatedOn")), "application Withdrawal UpdatedOn date time field IS NOT valid for application withdrawn event");
                        if (extAppID != null) {
                            assertEquals(payloadObject.get().getJSONObject("dataObject").get("externalApplicationId"), extAppID, "External Application ID isnt verified for new application event");
                        }
                        break;
                    }
                }
            case "APPLICATION_PROGRAM_ELIGIBILITY_STATUS_UPDATE":
                for (Map<String, Object> event : APIATSSendEventAndCreateLinksController.events.get()) {
                    payloadObject.set(new JSONObject(event.get("payload").toString()));
                    String eventNameFromResp = (String) event.get("eventName");
                    if (eventNameFromResp.equalsIgnoreCase(eventName)) {
                        String applicationConsumerID = "" + payloadObject.get().getJSONObject("dataObject").get("applicationConsumerId");
                        assertEquals(payloadObject.get().getInt("projectId") + "", (ConfigurationReader.getProperty("projectName").split(" "))[0], "Project Id is not correct");
                        assertEquals(payloadObject.get().getString("projectName"), (ConfigurationReader.getProperty("projectName").split(" "))[1], "Project Name isnt verified for application cleared event");
                        assertEquals(payloadObject.get().getString("action"), "Application Consumer Eligibility", "Action isn't verified for " + eventName);
                        assertEquals(payloadObject.get().getString("recordType"), "ApplicationConsumerEligibilityEvent", "Record type verified for " + eventName);
                        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isn't verified for " + eventName);
                        if (!applicationIdAPI.get().equals("")) {
                            assertEquals(payloadObject.get().getJSONObject("dataObject").get("applicationId").toString(), applicationIdAPI.get(), "Application ID  isn't valid for " + eventName);
                            assertTrue(payloadObject.get().getJSONObject("dataObject").get("channelId").equals("Web"), "Channel isnt verified for application program eligibility status update");
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("programEligibilityStatusUpdatedBy"), apiUserID, "application Withdrawal UpdatedBy isnt valid for application withdrawn event");
                            if (payloadObject.get().getJSONObject("dataObject").getString(" applicationProgram").equalsIgnoreCase("Medicaid")) {
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString(" applicationProgram"), "Medicaid", "Application program isnt verified for application program eligibility status update");
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("eligibilityStatus"), "Eligible", "Application status isnt verified for application program eligibility status update");
                            } else if (payloadObject.get().getJSONObject("dataObject").getString(" applicationProgram").equalsIgnoreCase("CHIP")) {
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString(" applicationProgram"), "CHIP", "Application program isnt verified for application program eligibility status update");
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("eligibilityStatus"), "Not Eligible", "Application status isnt verified for application program eligibility status update");
                            } else if (payloadObject.get().getJSONObject("dataObject").getString(" applicationProgram").equalsIgnoreCase("HCBS")) {
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString(" applicationProgram"), "HCBS", "Application program isnt verified for application program eligibility status update");
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("eligibilityStatus"), "Eligible", "Application status isnt verified for application program eligibility status update");
                            }
                        } else {
                            assertEquals(payloadObject.get().getJSONObject("dataObject").get("applicationId").toString(), loggedInUserId, "Application ID  isn't valid for " + eventName);
                            assertTrue(payloadObject.get().getJSONObject("dataObject").get("channelId").equals(crmCreateApplicationStepDef.channel), "Channel isnt verified for application program eligibility status update");
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("programEligibilityStatusUpdatedBy"), loggedInUserId, "application Withdrawal UpdatedBy isnt valid for application withdrawn event");
                            if(crmCreateApplicationStepDef.applicatonType.equalsIgnoreCase("Medicaid")){
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString(" applicationProgram"), "Medicaid", "Application program isnt verified for application program eligibility status update");
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("eligibilityStatus"), "Eligible", "Application status isnt verified for application program eligibility status update");
                            }else if(crmCreateApplicationStepDef.applicatonType.equalsIgnoreCase("CHIP")){
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString(" applicationProgram"), "CHIP", "Application program isnt verified for application program eligibility status update");
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("eligibilityStatus"), "Not Eligible", "Application status isnt verified for application program eligibility status update");
                            }else if(crmCreateApplicationStepDef.applicatonType.equalsIgnoreCase("HCBS")){
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString(" applicationProgram"), "HCBS", "Application program isnt verified for application program eligibility status update");
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("eligibilityStatus"), "Not Eligible", "Application status isnt verified for application program eligibility status update");
                            }
                        }
                        assertTrue(payloadObject.get().getJSONObject("dataObject").getString("applicationCode").matches("[a-zA-Z0-9]*"), "Application Code isnt contains alphanumeric");
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("applicationStatus"), "Determining", "Application status isnt Determining");
                        assertEquals(payloadObject.get().getJSONObject("dataObject").getString("primaryMember"), "N", "Application primary isnt verified for application program eligibility status update");
                        assertTrue(!(payloadObject.get().getJSONObject("dataObject").isNull("applicationDeadlineDate")), "End Date isn't verified for " + eventName);
                        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerId"), "Consumer Id isnt null for application withdrawn event");
                        assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("caseId"), "Case Id isnt null for application withdrawn event");
                        assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("programEligibilityStatusUpdatedOn")), "Program Eligibility Status Updated isn't verified for " + eventName);
                        System.out.println("APPLICATION_PROGRAM_ELIGIBILITY_STATUS_UPDATE is verified");
                    }
                }
        }
    }

    @Then("I verify {string} {string} generated and verify details")
    public void i_verify_generated_and_verify_details(String eventSize, String eventType) {
        int eventTypeCount = 0;
        if (eventType.contains("APPLICATION_EXPIRATION")) {
            eventTypeCount = eventCount.get();
        } else if (eventType.contains("APPLICATION_MISSING_INFORMATION")) {
            eventTypeCount = eventCount.get();
        } else {
            for (int i = 0; i < applicationEvents.get().size(); i++) {
                if (eventType.contains("LINK_EVENT for Outbound Correspondence")) {
                    if (applicationEvents.get().get(i).get("eventName").toString().equals("LINK_EVENT")) {
                        eventTypeCount++;
                    }
                }
                if (eventType.contains("LINK_EVENT for Inbound Correspondence")) {
                    if (applicationEvents.get().get(i).get("eventName").toString().equals("LINK_EVENT")) {
                        eventTypeCount++;
                    }
                } else if (applicationEvents.get().get(i).get("eventName").toString().equals(eventType)) {
                    eventTypeCount++;
                }
            }
        }
            Assert.assertEquals("" + eventTypeCount, eventSize, "Expected event count isn't verified for " + eventType);
            switch (eventType) {
                case "APPLICATION_EXPIRATION":
                    break;
                case "APPLICATION_MISSING_INFORMATION":
                    break;

                case "LINK_EVENT":
                    for (int i = 0; i < applicationEvents.get().size(); i++) {
                        if (applicationEvents.get().get(i).get("eventName").toString().equals(eventType)) {
                            payloadObject.set(new JSONObject(applicationEvents.get().get(i).get("payload").toString()));
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("internalId").toString(), applicationIdAPI.get(), "Internal ID isn't verified for first Link Event");
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("internalRefType"), "APPLICATION", "Internal Reference Type isn't verified for first Link Event");
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("externalRefType"), "APPLICATION", "External Reference Type isn't verified for first Link Event");
                            System.out.println("First Link Event verified");
                            for (int j = i + 1; j < applicationEvents.get().size(); j++) {
                                if (applicationEvents.get().get(j).get("eventName").toString().equals(eventType)) {
                                    payloadObject.set(new JSONObject(applicationEvents.get().get(j).get("payload").toString()));
                                    assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("externalId").toString(), applicationIdAPI.get(), "External ID isn't verified for second Link Event");
                                    assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("internalRefType"), "APPLICATION", "Internal Reference Type isn't verified for second Link Event");
                                    assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("externalRefType"), "APPLICATION", "External Reference Type isn't verified for second Link Event");
                                    System.out.println("Second Link Event verified");
                                }
                            }
                        }
                    }
                    break;

                case "LINK_EVENT for Outbound Correspondence":
                    for (int i = 0; i < applicationEvents.get().size(); i++) {
                        if (applicationEvents.get().get(i).get("eventName").toString().equals("LINK_EVENT")) {
                            payloadObject.set(new JSONObject(applicationEvents.get().get(i).get("payload").toString()));
                            if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("externalRefType").equals("OUTBOUND_CORRESPONDENCE")) {
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("internalId").toString(), applicationIdAPI.get(), "Internal ID isn't verified for first Link Event");
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("internalRefType"), "APPLICATION", "Internal Reference Type isn't verified for first Link Event");
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("externalRefType"), "OUTBOUND_CORRESPONDENCE", "External Reference Type isn't verified for first Link Event");
                                System.out.println("First Link Event verified");
                            } else if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("externalRefType").equals("APPLICATION")) {
                                payloadObject.set(new JSONObject(applicationEvents.get().get(i).get("payload").toString()));
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("externalId").toString(), applicationIdAPI.get(), "External ID isn't verified for second Link Event");
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("internalRefType"), "OUTBOUND_CORRESPONDENCE", "Internal Reference Type isn't verified for second Link Event");
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("externalRefType"), "APPLICATION", "External Reference Type isn't verified for second Link Event");
                                System.out.println("Second Link Event verified");
                            }
                        }
                    }
                    break;

                case "LINK_EVENT for Inbound Correspondence":
                    for (int i = 0; i < applicationEvents.get().size(); i++) {
                        if (applicationEvents.get().get(i).get("eventName").toString().equals("LINK_EVENT")) {
                            payloadObject.set(new JSONObject(applicationEvents.get().get(i).get("payload").toString()));
                            if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("externalRefType").equals("INBOUND_CORRESPONDENCE")) {
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("internalId").toString(), applicationIdAPI.get(), "Internal ID isn't verified for first Link Event");
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("internalRefType"), "APPLICATION", "Internal Reference Type isn't verified for first Link Event");
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("externalRefType"), "INBOUND_CORRESPONDENCE", "External Reference Type isn't verified for first Link Event");
                                System.out.println("First Link Event verified");
                            } else if (payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("externalRefType").equals("APPLICATION")) {
                                payloadObject.set(new JSONObject(applicationEvents.get().get(i).get("payload").toString()));
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("externalId").toString(), applicationIdAPI.get(), "External ID isn't verified for second Link Event");
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("internalRefType"), "INBOUND_CORRESPONDENCE", "Internal Reference Type isn't verified for second Link Event");
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("externalLinkPayload").get("externalRefType"), "APPLICATION", "External Reference Type isn't verified for second Link Event");
                                System.out.println("Second Link Event verified");
                            }
                        }
                    }
                    break;

                case "MISSING_INFORMATION_ITEM_SAVE_EVENT":
                    for (int i = 0; i < applicationEvents.get().size(); i++) {
                        if (applicationEvents.get().get(i).get("eventName").toString().equals(eventType)) {
                            assertTrue(createdConsumerDetails.get().containsKey(payloadObject.get().getJSONObject("dataObject").get("entityId").toString()));
                            JSONObject missingInfoDependentsObject;
                            missingInfoDependentsObject = (JSONObject) payloadObject.get().getJSONObject("dataObject").getJSONArray("missingInformationDependents").get(0);
                            assertEquals(missingInfoDependentsObject.get("dependentRecordId").toString(), getKeyFromValue(createdConsumerDetails.get(), "Primary Individual"));
                            break;
                        }
                    }
                    break;
                case "APPLICATION_CONSUMER_ELIGIBILITY_SAVE_EVENT":
                    for (int i = 0; i < applicationEvents.get().size(); i++) {
                        if (applicationEvents.get().get(i).get("eventName").toString().equals(eventType)) {
                            payloadObject.set(new JSONObject(applicationEvents.get().get(i).get("payload").toString()));
                            String applicationConsumerID = "" + payloadObject.get().getJSONObject("dataObject").get("applicationConsumerId");
                            assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project id isn't correct" + eventType);
                            assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project name isn't correct for " + eventType);
                            assertEquals(payloadObject.get().getString("action"), "Create", "Action isn't verified for " + eventType);
                            assertEquals(payloadObject.get().getString("recordType"), "ApplicationConsumerEligibilityEvent", "Record type verified for " + eventType);

                            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isn't verified for " + eventType);
                            assertEquals(payloadObject.get().getJSONObject("dataObject").get("applicationId").toString(), applicationIdAPI.get(), "Application ID  isn't valid for " + eventType);
                            assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationStatusId").toString().chars().allMatch(Character::isDigit), "app statusId isn't digit for " + eventType);
                            assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerType")).equals(createdConsumerDetails.get().get(applicationConsumerID)), "Consumer Type isn't verified for " + eventType);
                            assertTrue(payloadObject.get().getJSONObject("dataObject").get("eligibilityId").toString().chars().allMatch(Character::isDigit), "EligibilityId isn't digit for " + eventType);
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("eligibilityStatus"), "Pending", "Eligibility Status isn't verified for " + eventType);
                            assertTrue(payloadObject.get().getJSONObject("dataObject").getJSONArray("denialReasonCode").isEmpty(), "Denial Reason Code isn't verified for " + eventType);
                            assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("startDate"), "Start Date isn't verified for " + eventType);
                            assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("endDate"), "End Date isn't verified for " + eventType);
                            assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("determinationDate"), "Determination Date isn't verified for " + eventType);
                            assertTrue(payloadObject.get().getJSONObject("dataObject").get("programId").toString().chars().allMatch(Character::isDigit), "Program Id isn't digit for " + eventType);
                            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isn't verified for " + eventType);
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), loggedInUserId, "CreatedBy isnt valid for " + eventType);
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), loggedInUserId, "UpdatedBy isnt valid for " + eventType);
                            System.out.println("APPLICATION_CONSUMER_ELIGIBILITY_SAVE_EVENT is verified");
                        }
                    }
                    break;
                case "MISSING_INFORMATION_ITEM_UPDATE_EVENT":
                    for (int i = 0; i < applicationEvents.get().size(); i++) {
                        if (applicationEvents.get().get(i).get("eventName").toString().equals(eventType)) {
                            payloadObject.set(new JSONObject(applicationEvents.get().get(i).get("payload").toString()));
                            assertEquals(payloadObject.get().getInt("projectId") + "", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project id isn't correct" + eventType);
                            assertEquals(payloadObject.get().getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal(), "Project name isn't correct for " + eventType);
                            assertEquals(payloadObject.get().getString("action"), "Update", "Action isn't verified for " + eventType);
                          //  assertEquals(payloadObject.get().getString("recordType"), "Missing Information Item", "Record type verified for " + eventType);
                          // record Type value is changed due to CP-37843
                            assertEquals(payloadObject.get().getString("recordType"), "MissingInfoItemModel", "Record type verified for " + eventType);
                            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for" + eventType);
                            assertTrue(payloadObject.get().getJSONObject("dataObject").get("missingInfoItemId").toString().chars().allMatch(Character::isDigit), "Missing Info Item ID is not all digits for " + eventType);
                            assertTrue(payloadObject.get().getJSONObject("dataObject").get("entityId").toString().chars().allMatch(Character::isDigit), "Entity ID is not all digits for" + eventType);
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getJSONObject("tags").get("application").toString(), applicationIdAPI.get(), "Application ID  isn't valid for " + eventType);
                            JSONObject missingInfoDependentsObjectUpdate;
                            missingInfoDependentsObjectUpdate = (JSONObject) payloadObject.get().getJSONObject("dataObject").getJSONArray("missingInformationDependents").get(0);
                            if (loggedInUserId == null) {
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), "2492", "CreatedBy isnt valid for " + eventType);
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), "2492", "UpdatedBy isnt valid for " + eventType);
                                assertEquals(missingInfoDependentsObjectUpdate.get("createdBy"), "2492", "CreatedBy isnt valid for " + eventType);
                                assertEquals(missingInfoDependentsObjectUpdate.get("updatedBy"), "2492", "UpdatedBy isnt valid for " + eventType);
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("completedBy"), "2492", "Completed By isnt valid for " + eventType);
                            } else {
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), loggedInUserId, "CreatedBy isnt valid for " + eventType);
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), loggedInUserId, "UpdatedBy isnt valid for " + eventType);
                                assertEquals(missingInfoDependentsObjectUpdate.get("createdBy"), loggedInUserId, "CreatedBy isnt valid for " + eventType);
                                assertEquals(missingInfoDependentsObjectUpdate.get("updatedBy"), loggedInUserId, "UpdatedBy isnt valid for " + eventType);
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("completedBy"), loggedInUserId, "Completed By isnt valid for " + eventType);
                            }
                            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "Event created On isn't verified for " + eventType);
                            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "Event created On isn't verified for " + eventType);
                            if (CRM_MissingInformationTabStepDef.miExpectedMissingInfo.get("attributeName").equalsIgnoreCase("Citizenship Verification"))
                                assertTrue(payloadObject.get().getJSONObject("dataObject").get("recordClass").equals("Verification"), "record class is not correct");
                            else
                                assertTrue(payloadObject.get().getJSONObject("dataObject").get("recordClass").equals("Data"), "record class is not correct");
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("entityRecordType"), "Application Consumer", "Entity Record Type isnt valid for " + eventType);
                            if (CRM_MissingInformationTabStepDef.miExpectedMissingInfo.get("attributeName").equalsIgnoreCase("Citizenship Verification"))
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("attributeName"), "Citizenship Verification", "Attribute Name isnt valid for " + eventType);
                            else
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("attributeName"), "SSN", "Attribute Name isnt valid for " + eventType);
                            assertEquals(payloadObject.get().getJSONObject("dataObject").get("status"), missingInformationStatus.get(), "MI Status is not verified for " + eventType);
                            // New expected created
                            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("completedOn")), "Event created On isn't verified for " + eventType);

                            assertTrue(missingInfoDependentsObjectUpdate.get("missingInfoDependentId").toString().chars().allMatch(Character::isDigit), "MissingInfo Dependent Id isn't verified for " + eventType);
                            assertEquals(missingInfoDependentsObjectUpdate.get("dependentType"), "Application Consumer", "Dependent Type is not Application Consumer for " + eventType);
                            assertTrue(missingInfoDependentsObjectUpdate.get("dependentRecordId").toString().chars().allMatch(Character::isDigit), "Dependent Record ID is not Application Consumer for " + eventType);
                            assertTrue(EventsUtilities.isValidDate(missingInfoDependentsObjectUpdate.get("updatedOn").toString()), "UpdatedOn date time field IS NOT valid for " + eventType);
                            assertTrue(EventsUtilities.isValidDate(missingInfoDependentsObjectUpdate.get("createdOn").toString()), "UpdatedOn date time field IS NOT valid for " + eventType);
                            System.out.println(eventType + "verified");
                        }
                    }
                    break;
                default:
                    System.out.println("Event Type didn't match");
                    fail();
            }
        }


    @Then("I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS")
    public void i_initiate_Subscribers_Record_GET_API_and_run_Subscribers_Record_GET_API_for_ATS() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseAPIUrl);
        String endPoint = "";
        endPoint = subscribersRecordEndPoint + subscriberId.get() + "/" + eventId.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(endPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Status code isnt 200 for Subscriber records");
    }

    @Then("I will verify response has event Id and {string} and subscriberId for ATS")
    public void i_will_verify_response_has_event_Id_and_and_subscriberId_for_ATS(String eventName) {
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("eventSubscriberRecords");
        JSONObject temp = new JSONObject(json.get(0).toString());
        assertEquals(temp.getString("eventName"), eventName, "Event name doesnt match for subscriber records");
        System.out.println("Even name on DPBI:  " + eventName);
        assertEquals(temp.getInt("eventId"), eventId.get(), "Event id doesnt match for subscriber records");
        System.out.println("Event id on DPBI:  " + eventId.get());
        assertEquals(temp.getInt("subscriberId") + "", subscriberId.get(), "Subscriber id doesnt match");
        System.out.println("Subscriber id on DPBI:   " + subscriberId.get());
        System.out.println("Event is subscribed to DPBI list");
    }

    //Vinuta - Refactored to accomodate all event types
    @Then("I verify {string} is not generated for {string}")
    public void iVerifyIsNotGenerated(String eventName, String member) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Status code isnt verified");
        List<Map<String, Object>> applicationEvents = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("events");

        for (int i = 0; i < applicationEvents.size(); i++) {
            System.out.println("Events size is: " + applicationEvents.size());
            if (applicationEvents.get(i).get("eventName").toString().equals(eventName)) {
                Assert.fail("The " + eventName + "generated");
            }
        }
    }

    @Then("I verify The {string} details for the {string}")
    public void iVerifyTheDetailsForThe(String eventType, String consumerType) {
        boolean eventVerified = false;
        assertTrue(APIATSSendEventAndCreateLinksController.createdConsumerDetails.get().containsValue(consumerType), consumerType + "Consumer Type didn't match with the Created Consumers");
        for (Map<String, Object> event : APIATSSendEventAndCreateLinksController.events.get()) {
            payloadObject.set(new JSONObject(event.get("payload").toString()));
            String eventName = (String) event.get("eventName");
            String applicationConsumerID = "" + payloadObject.get().getJSONObject("dataObject").get("applicationConsumerId");
            switch (eventType.toUpperCase()) {
                case "APPLICATION_EXPIRATION":
                    if (eventName.equalsIgnoreCase(eventType)) {
                        for (String consumerID : APIATSSendEventAndCreateLinksController.createdConsumerDetails.get().keySet()) {
                            boolean applicationConsumerMatch = applicationConsumerID.equals(consumerID) && APIATSSendEventAndCreateLinksController.createdConsumerDetails.get().get(consumerID).equalsIgnoreCase(consumerType);
                            if (applicationConsumerMatch) {
                                eventId.set((int) event.get("eventId"));
                                System.out.println("Event ID is " + eventId + " for " + consumerType);
                                assertTrue(true, "Application Consumer Status Save Event for " + consumerType + " " + consumerID + " didn't" + "found on events");
                                System.out.println("Application Consumer Status Save Event for " + consumerType + " " + consumerID + " " + "found on events");
                                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerId").toString().chars().allMatch(Character::isDigit), "ApplicationConsumerId isnt verified for application status save event");
                                assertFalse(payloadObject.get().getJSONObject("dataObject").isNull("consumerId"), "Consumer Id is null for application expiration event");
                                assertFalse(payloadObject.get().getJSONObject("dataObject").isNull("caseId"), "Case Id isnt is for application expiration event");
                            }
                        }
                    }
                    break;
                case "APPLICATION_CONSUMER_STATUS_SAVE_EVENT":
                    if (eventName.equalsIgnoreCase(eventType)) {
                        for (String consumerID : APIATSSendEventAndCreateLinksController.createdConsumerDetails.get().keySet()) {
                            boolean applicationConsumerMatch = applicationConsumerID.equals(consumerID) && APIATSSendEventAndCreateLinksController.createdConsumerDetails.get().get(consumerID).equalsIgnoreCase(consumerType);
                            if (applicationConsumerMatch) {
                                eventId.set((int) event.get("eventId"));
                                System.out.println("Event ID is " + eventId + " for " + consumerType);
                                assertTrue(true, "Application Consumer Status Save Event for " + consumerType + " " + consumerID + " didn't" + "found on events");
                                System.out.println("Application Consumer Status Save Event for " + consumerType + " " + consumerID + " " + "found on events");
                                assertEquals(payloadObject.get().getString("action"), "Create", "Action isn't verified for application consumer status save event");
                                assertEquals(payloadObject.get().getString("recordType"), "Application Consumer Status", "Record type isn't verified for application consumer status save event");
                                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for application consumer status  save event");
                                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerStatusId").toString().chars().allMatch(Character::isDigit), "app consumer statusId isnt digit for application consumer status  save event");
                                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerId").toString().chars().allMatch(Character::isDigit), "ApplicationConsumerId isnt verified for application status save event");
                                assertTrue(payloadObject.get().getJSONObject("dataObject").getString("statusId").equals("Received"), "Application status isnt verified for application consumer status save event");
                                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("effectiveStartDate")), "effectiveStartDate field IS NOT valid for application consumer status save event");
                                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveEndDate"), "effectiveEndDate field IS NOT null for application consumer status save event");
                                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for  application consumer status  save event");
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), apiUserID, "CreatedBy isnt valid for application consumer status  save event");
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), apiUserID, "UpdatedBy isnt valid for application consumer status  save event");
                                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for application consumer status  save event");
                                System.out.println("Application consumer status save event is verified for " + consumerType + " " + consumerID);
                                eventVerified = true;
                            }
                        }
                    }
                    break;
                case "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT":
                    if (eventName.equalsIgnoreCase(eventType)) {
                        for (String consumerID : APIATSSendEventAndCreateLinksController.createdConsumerDetails.get().keySet()) {
                            boolean applicationConsumerMatch = applicationConsumerID.equals(consumerID) && APIATSSendEventAndCreateLinksController.createdConsumerDetails.get().get(consumerID).equalsIgnoreCase(consumerType);
                            if (applicationConsumerMatch) {
                                eventId.set((int) event.get("eventId"));
                                System.out.println("Event ID is " + eventId + " for " + consumerType);
                                assertTrue(true, "Application Consumer Status Update Event for " + consumerType + " " + consumerID + " didn't" + "found on events");
                                System.out.println("Application Consumer Status Save Event for " + consumerType + " " + consumerID + " " + "found on events");
                                assertEquals(payloadObject.get().getString("action"), "Update", "Action isn't verified for application consumer status update event");
                                assertEquals(payloadObject.get().getString("recordType"), "ApplicationConsumerStatusEvent", "Record type isn't verified for application consumer status update event");
                                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isn't verified for application consumer status update event");
                                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerStatusId").toString().chars().allMatch(Character::isDigit), "app consumer statusId isn't digit for application consumer status  update event");
                                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationConsumerId").toString().chars().allMatch(Character::isDigit), "ApplicationConsumerId isn't verified for application status save event");
                                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("effectiveStartDate")), "effectiveStartDate field IS NOT valid for application consumer status save event");
                                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("effectiveEndDate"), "effectiveEndDate field IS NOT null for application consumer status save event");
                                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for  application consumer status  save event");
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), apiUserID, "CreatedBy isnt valid for application consumer status  save event");
                                if (loggedInUserId != null) {
                                    assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), loggedInUserId, "UpdatedBy isnt valid for application consumer status  save event");
                                } else
                                    assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), apiUserID, "UpdatedBy isnt valid for application consumer status  save event");
                                assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for application consumer status  save event");
                                System.out.println("Application consumer status update event is verified for " + consumerType + " " + consumerID);
                                eventVerified = true;
                            }
                        }
                    }
                    break;
            }
            if (eventVerified) break;
        }
    }

    @And("I verify The {string} is not  generated for {string}")
    public void iVerifyTheIsNotGeneratedFor(String eventType, String consumerType) {
        assertTrue(APIATSSendEventAndCreateLinksController.createdConsumerDetails.get().containsValue(consumerType), consumerType + "Consumer Type didn't match with the Created Consumers");
        for (Map<String, Object> event : APIATSSendEventAndCreateLinksController.events.get()) {
            payloadObject.set(new JSONObject(event.get("payload").toString()));
            String eventName = (String) event.get("eventName");
            String applicationConsumerID = "" + payloadObject.get().getJSONObject("dataObject").get("applicationConsumerId");
            if (eventName.equalsIgnoreCase(eventType)) {
                for (String consumerID : APIATSSendEventAndCreateLinksController.createdConsumerDetails.get().keySet()) {
                    boolean applicationConsumerMatch = applicationConsumerID.equals(consumerID) && APIATSSendEventAndCreateLinksController.createdConsumerDetails.get().get(consumerID).equalsIgnoreCase(consumerType);
                    if (applicationConsumerMatch) {
                        Assert.fail(eventName + "generated for the " + consumerType);
                    }
                }
            }
        }
    }

    @Then("I verify The {string} includes deadline date and set to end of the calculated date for {string}")
    public void iVerifyTheIncludesDeadlineDateAndSetToEndOfTheCalculatedDateFor(String eventType, String applicationType) {
        for (Map<String, Object> event : APIATSSendEventAndCreateLinksController.events.get()) {
            payloadObject.set(new JSONObject(event.get("payload").toString()));
            String eventName = (String) event.get("eventName");
            String applicationIDResponse = "" + payloadObject.get().getJSONObject("dataObject").get("applicationId");
            System.out.println("Application id response: "+applicationIDResponse);
            if (eventName.equalsIgnoreCase(eventType)) {
                boolean applicationMatch = applicationIDResponse.equals(applicationIdAPI.get());
                System.out.println("Application match status: "+applicationMatch);
                if (applicationMatch) {
                    eventId.set((int) event.get("eventId"));
                    System.out.println("Matched eventId: "+eventId.get());
                    switch (eventType) {
                        case "APPLICATION_SAVE_EVENT":
                            String expectedApplicationDeadlineDate = "";
                            if (applicationType.equalsIgnoreCase("MEDICAL ASSISTANCE")) {
                                expectedApplicationDeadlineDate = getFutureDateFormatYYYYMMdd(45);
                            } else if (applicationType.equalsIgnoreCase("LONG TERM CARE")) {
                                expectedApplicationDeadlineDate = getFutureDateFormatYYYYMMdd(90);
                            }
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("applicationDeadlineDate"), expectedApplicationDeadlineDate, "Expected Application Deadline date : " + expectedApplicationDeadlineDate + " didn't match with actual deadline date: " + payloadObject.get().getJSONObject("dataObject").getString("applicationDeadlineDate"));
                    }
                }
            }
        }
    }

    @Then("I verify {string} statusId is {string} and statusReason is {string} in the event payload")
    public void iVerifyStatusIdIsAndStatusReasonIsInTheEventPayload(String eventType, String statusID, String statusReason) {
        for (Map<String, Object> event : APIATSSendEventAndCreateLinksController.events.get()) {
            payloadObject.set(new JSONObject(event.get("payload").toString()));
            String eventName = (String) event.get("eventName");
            String applicationIDResponse = "" + payloadObject.get().getJSONObject("dataObject").get("applicationId");
            if (eventName.equalsIgnoreCase(eventType)) {
                boolean applicationMatch = applicationIDResponse.equals(applicationIdAPI.get());
                if (applicationMatch) {
                    switch (eventType) {
                        case "APPLICATION_STATUS_SAVE_EVENT":
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("statusId"), statusID, "Status ID isn't verified for application status update event");
                            if (statusReason.equalsIgnoreCase("null")) {
                                assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("statusReason"), "Status Reason isn't verified for application status update event");
                            } else {
                                assertEquals(payloadObject.get().getJSONObject("dataObject").getString("statusReason"), statusReason, "Status Reason isn't verified for application status update event");
                            }
                    }
                }
            }
        }
    }

    @And("I verify status of the application is {string} in the events payload")
    public void iVerifyStatusOfApplicationInEventPayloadIs(String expectedStatus) {
        assertTrue(String.valueOf(payloadObject.get().getJSONObject("dataObject").get("statusId")).equals(expectedStatus), "Application status is not as expected: " + expectedStatus);
    }

    //This method catches any extra events which are generated - addresses CP-21399, CP-22001 defects
    @And("I verify number of events generated is {string}")
    public void iVerifyNumberOfEventsGenerated(String expectedNumber) {
        List<Map<String, Object>> applicationEvents = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("events");
        assertTrue(applicationEvents.size() == Integer.parseInt(expectedNumber), "Number of events expected do not match");
    }

    @Then("I verify the {string} generated for the {string}")
    public void iVerifyTheGeneratedForThe(String eventType, String consumerType) {
        boolean isEventGenerated = false;
        assertTrue(APIATSSendEventAndCreateLinksController.createdConsumerDetails.get().containsValue(consumerType), consumerType + "Consumer Type didn't match with the Created Consumers");
        List<Map<String, Object>> applicationEvents = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("events");
        for (Map<String, Object> event : applicationEvents) {
            String eventName = (String) event.get("eventName");
            payloadObject.set(new JSONObject(event.get("payload").toString()));
            switch (eventName.toUpperCase()) {
                case "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT":
                    System.out.println(event);
                    if (eventName.equalsIgnoreCase(eventType)) {
                        String applicationConsumerID = "" + payloadObject.get().getJSONObject("dataObject").get("applicationConsumerId");
                        for (String consumerID : APIATSSendEventAndCreateLinksController.createdConsumerDetails.get().keySet()) {
                            boolean applicationConsumerMatch = applicationConsumerID.equals(consumerID) && APIATSSendEventAndCreateLinksController.createdConsumerDetails.get().get(consumerID).equalsIgnoreCase(consumerType);
                            if (applicationConsumerMatch) {
                                isEventGenerated = true;
                                System.out.println(eventType + "is  generated for " + consumerType);
                            }
                        }
                    }
            }
        }
        Assert.assertTrue(isEventGenerated, eventType + " is not  generated for " + consumerType);
    }

    @Then("I verify the {string} not generated for the {string}")
    public void iVerifyTheNotGeneratedForThe(String eventType, String consumerType) {
        assertTrue(APIATSSendEventAndCreateLinksController.createdConsumerDetails.get().containsValue(consumerType), consumerType + "Consumer Type didn't match with the Created Consumers");
        List<Map<String, Object>> applicationEvents = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("events");
        for (Map<String, Object> event : applicationEvents) {
            String eventName = (String) event.get("eventName");
            payloadObject.set(new JSONObject(event.get("payload").toString()));
            if (eventName.equalsIgnoreCase(eventType)) {
                Assert.fail(eventName + "generated for the " + consumerType);
            }
        }
    }

    @Then("I verify {string} has primaryIndicator set to {string}")
    public void verifyPrimaryIndicatorSet(String phoneType, String indicator) {
        for (int i = 0; i < applicationEvents.get().size(); i++) {
            payloadObject.set(new JSONObject(applicationEvents.get().get(i).get("payload").toString()));
            if (payloadObject.get().getJSONObject("dataObject").get("phoneType").equals(phoneType)) {
                assertEquals(payloadObject.get().getJSONObject("dataObject").get("primaryIndicator"), Boolean.parseBoolean(indicator));
            }
        }
    }

    @Then("I verify The {string} includes application received language for {string} application")
    public void iVerifyTheIncludesApplicationReceiveeLanguageFor(String eventType, String applicationType) {
        for (Map<String, Object> event : APIATSSendEventAndCreateLinksController.events.get()) {
            payloadObject.set(new JSONObject(event.get("payload").toString()));
            String eventName = (String) event.get("eventName");
            String applicationIDResponse = "" + payloadObject.get().getJSONObject("dataObject").get("applicationId");
            if (eventName.equalsIgnoreCase(eventType)) {
                boolean applicationMatch = applicationIDResponse.equals(applicationIdAPI.get());
                if (applicationMatch) {
                    eventId.set((int) event.get("eventId"));
                    switch (eventType) {
                        case "APPLICATION_SAVE_EVENT":
                            String expectedApplicationReceivedLanguage = "";
                            if (applicationType.equalsIgnoreCase("MEDICAL ASSISTANCE")) {
                                expectedApplicationReceivedLanguage = applicationReceivedLanguage.get() ;
                            } else if (applicationType.equalsIgnoreCase("LONG TERM CARE")) {
                                expectedApplicationReceivedLanguage = applicationReceivedLanguage.get() ;
                            }
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("applicationReceivedLanguage"), expectedApplicationReceivedLanguage, "Expected Application Deadline date : " + expectedApplicationReceivedLanguage + " didn't match with actual deadline date: " + payloadObject.get().getJSONObject("dataObject").getString("applicationReceivedLanguage"));
                    }
                }
            }
        }
    }

    @Then("I verify the application GUID from the response for {string} application")
    public void iVerifyTheResponseIncludesApplicationGUID(String applicationType) {
        HashMap appData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        int expectedApplicationGUIDSize = 8;
        int actualApplicationGUIDSize = 0;
        String applicationGuid = null;
        applicationReceivedLanguage.set(null);
        if (applicationType.equalsIgnoreCase("MEDICAL ASSISTANCE") || applicationType.equalsIgnoreCase("LONG TERM CARE")) {
            applicationGuid = (String) appData.get("applicationCode");
            actualApplicationGUIDSize=applicationGuid.toCharArray().length;
        }
        assertEquals(actualApplicationGUIDSize, expectedApplicationGUIDSize, "Expected length of application GUID : " +expectedApplicationGUIDSize+ " didn't match with actual Application GUID: " + actualApplicationGUIDSize );
        assertTrue(applicationGuid.matches("[a-zA-Z0-9]*"),"Expected application code does not contains only letters and numbers :"+applicationGuid);
        if(applicationId!=null){
            if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationStatus").equalsIgnoreCase("Submitted")){
                assertEquals(applicationCodeAPI.get(), applicationCodeAfterUpdateAPI.get(), "Expected : " +applicationCodeAPI+ " didn't match with actual Application code: " + applicationCodeAfterUpdateAPI );
            }else if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationStatus").equalsIgnoreCase("Withdrawn")){
                assertEquals(applicationCodeAPI.get(), applicationCodeAfterUpdateAPI.get(), "Expected : " +applicationCodeAPI+ " didn't match with actual Application code: " + applicationCodeAfterUpdateAPI );
            }else if(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationStatus").equalsIgnoreCase("Entering Data") && (applicationCodeAfterUpdateAPI!=null)){
                assertEquals(applicationCodeAPI.get(), applicationCodeAfterUpdateAPI.get(), "Expected : " +applicationCodeAPI+ " didn't match with actual Application code: " + applicationCodeAfterUpdateAPI );
            }
        }else{
            if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationStatus").equalsIgnoreCase("Withdrawn")){
                assertEquals(applicationCodeAPI.get(), applicationCodeAfterUpdateAPI.get(), "Expected : " +applicationCodeAPI+ " didn't match with actual Application code: " + applicationCodeAfterUpdateAPI );
            }else  if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationStatus").equalsIgnoreCase("Determining")){
                assertEquals(applicationCodeAPI.get(), applicationCodeAfterUpdateAPI.get(), "Expected : " +applicationCodeAPI+ " didn't match with actual Application code: " + applicationCodeAfterUpdateAPI );
            }else  if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.applicationStatus").equalsIgnoreCase("Duplicate")){
                assertEquals(applicationCodeAPI.get(), applicationCodeAfterUpdateAPI.get(), "Expected : " +applicationCodeAPI+ " didn't match with actual Application code: " + applicationCodeAfterUpdateAPI );
            }
        }
    }

    @Then("I verify the response includes {string} events for {string} appplication")
    public void iVerifyTheResponseIncludesNewApplicationEvents(String eventType, String applicationType) {
        for (Map<String, Object> event : APIATSSendEventAndCreateLinksController.events.get()) {
            payloadObject.set(new JSONObject(event.get("payload").toString()));
            String eventName = (String) event.get("eventName");
            String applicationIDResponse = "" + payloadObject.get().getJSONObject("dataObject").get("applicationId");
            String applicationTypeResponse = "" + payloadObject.get().getJSONObject("dataObject").get("applicationType");
            if (eventName.equalsIgnoreCase(eventType)) {
                boolean applicationMatch = applicationIDResponse.equals(applicationIdAPI.get());
                if (applicationMatch) {
                    eventId.set((int) event.get("eventId"));
                    switch (eventType) {
                        case "NEW_APPLICATION":
                            String expectedApplicationId = "";
                            assertEquals(payloadObject.get().getInt("projectId") + "", (ConfigurationReader.getProperty("projectName").split(" "))[0], "Project Id is not correct");
                            assertEquals(payloadObject.get().getString("projectName"), (ConfigurationReader.getProperty("projectName").split(" "))[1], "Project Name isnt verified for new application event");

                            if (applicationType.equalsIgnoreCase("MEDICAL ASSISTANCE")) {
                                expectedApplicationId = applicationIdAPI.get();
                                assertEquals(applicationTypeResponse, "Medical Assistance", "Expected  : " + applicationType + " didn't match with actual application Type: " + applicationTypeResponse);
                                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationName").equals("Medical Assistance - New"), "Application name isnt verified for new application event");
                            } else if (applicationType.equalsIgnoreCase("LONG TERM CARE")) {
                                expectedApplicationId = applicationIdAPI.get();
                                assertEquals(applicationTypeResponse, "Long Term Care", "Expected  : " + applicationType + " didn't match with actual application Type: " + applicationTypeResponse);
                                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationName").equals("Long Term Care - New"), "Application name isnt verified for new application event");
                            }
                            assertEquals(applicationIDResponse, expectedApplicationId, "Expected  : " + expectedApplicationId + " didn't match with actual application Id: " + applicationIDResponse);
                            assertEquals(payloadObject.get().getString("action"), "Create", "Action isnt verified for new application event");
                            assertEquals(payloadObject.get().getString("recordType"), "Application", "Record type verified for new application event");
                            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for new application event");
                            assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("applicationSignatureDate"), "App sign date isnt null for new application event");
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("bussinessEventName"), "New Application", "business event name isnt valid for new application event");
                            assertTrue(payloadObject.get().getJSONObject("dataObject").get("channelId").equals("Web"), "Channel isnt verified new for application event");
                            assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationCycle").equals("New"), "Application cycle isnt verified for new application event");
                            assertTrue(EventsUtilities.isOnlyValidDate(payloadObject.get().getJSONObject("dataObject").getString("applicationReceivedDate")), "Application Received Date isnt verified for new application event");
                            assertFalse(payloadObject.get().getJSONObject("dataObject").getBoolean("applicationSignatureExistsInd"), "application Signature Exists Ind isnt false for new application event");
                            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for new application event");
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), apiUserID, "CreatedBy isnt valid for new application event");
                            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for new application event");
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), apiUserID, "UpdatedBy isnt valid for new application event");
                            assertTrue(!(payloadObject.get().getJSONObject("dataObject").isNull("applicationDeadlineDate")), "Application deadline isnt null for new application event");
                            assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationPriority").equals("Normal"), "Application priority isnt null for new application event");
                            assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("applicationReceivedLanguage"), "Application Received Language isnt null for new application event");
                            break;
                        case "APPLICATION_CLEARED":
                            assertEquals(payloadObject.get().getInt("projectId") + "", (ConfigurationReader.getProperty("projectName").split(" "))[0], "Project Id is not correct");
                            assertEquals(payloadObject.get().getString("projectName"), (ConfigurationReader.getProperty("projectName").split(" "))[1], "Project Name isnt verified for application cleared event");
                            if (!applicationIdAPI.get().equals("")) {
                                assertEquals(applicationIDResponse, applicationIdAPI.get(), "Expected  : " + applicationIdAPI.get() + " didn't match with actual application Id: " + applicationIDResponse);
                                assertTrue(payloadObject.get().getJSONObject("dataObject").get("channelId").equals("Web"), "Channel isnt verified application cleared event");
                            } else {
                                assertEquals("" + payloadObject.get().getJSONObject("dataObject").get("applicationId"), crmCreateApplicationStepDef.applicationId, "Application Id isnt valid for application cleared event");
                                assertTrue(payloadObject.get().getJSONObject("dataObject").get("channelId").equals(crmCreateApplicationStepDef.channel), "Channel isnt verified for application cleared event");
                            }

                            assertEquals(payloadObject.get().getString("action"), "Update", "Action isnt verified for application cleared event");
                            assertEquals(payloadObject.get().getString("recordType"), "Application Cleared", "Record type isnt verified for application cleared event");
                            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getString("eventCreatedOn")), "Event created On isnt verified for application cleared event");
                            assertTrue(!(payloadObject.get().getJSONObject("dataObject").isNull(" applicationConsumerId")),"Application Consumer Id is Null for application cleared event");
                            assertTrue(payloadObject.get().getJSONObject("dataObject").getString(" applicationCode").matches("[a-zA-Z0-9]*"),"Application Code isnt contains alphanumeric");
                            assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("externalApplicationId"),"External Application Id isnt Null for application cleared event");
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("applicationStatus"),"Determining", "Application status isnt determining");
                            assertTrue(!(payloadObject.get().getJSONObject("dataObject").isNull(" applicationDeadlineDate")),"Application Dead line date is Null for application cleared event");

                            assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("consumerId"), "Consumer Id isnt null for application cleared event");
                            assertTrue(payloadObject.get().getJSONObject("dataObject").isNull("caseId"), "Case Id isnt null for application cleared event");
                            if (applicationType.equalsIgnoreCase("MEDICAL ASSISTANCE") ) {
                                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationTypeAndCycle").equals("Medical Assistance - New"), "Application type and cycle isnt verified for new application event");
                            }else  if ( applicationType.equalsIgnoreCase("LONG TERM CARE")) {
                                assertTrue(payloadObject.get().getJSONObject("dataObject").get("applicationTypeAndCycle").equals("Long Term Care - New"), "Application type and cycle isnt verified for new application event");
                            }
                            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("createdOn")), "CreatedOn date time field IS NOT valid for application cleared event");
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("createdBy"), "2492", "CreatedBy isnt valid for application cleared event");
                            assertTrue(EventsUtilities.isValidDate(payloadObject.get().getJSONObject("dataObject").getString("updatedOn")), "UpdatedOn date time field IS NOT valid for application cleared event");
                            assertEquals(payloadObject.get().getJSONObject("dataObject").getString("updatedBy"), "2492", "UpdatedBy isnt valid for application cleared event");
                            break;
                    }
                }
            }
        }
    }
}