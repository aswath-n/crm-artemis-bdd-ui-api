package com.maersk.crm.step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.dms.step_definitions.PublishDPBIEventsOutboundCorrespondenceChangesStepDefs;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertEquals;

public class CRM_UpdateConsumersNJValidate extends CRMUtilities implements ApiBase {

    private final String eventURI = ConfigurationReader.getProperty("apiEventsURI");
    private final String getConsumerSaveEvents = "app/crm/events?size=1000&page=0&sort=eventId,desc";

    PublishDPBIEventsOutboundCorrespondenceChangesStepDefs pubCorr = new PublishDPBIEventsOutboundCorrespondenceChangesStepDefs();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();

    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();

    private JsonObject requestParams;
    private String firstName = "Willi";
    private String userId;

    @And("I click validate&link button for {string}")
    public void clickValidateAndLinkBtn(String type) {
        userId = contactRecord.userId.getText();
        userId = userId.substring(3);
        System.out.println("User Id for logged in user: " + userId);

        if (type.equals("createTask")) {
            Assert.assertTrue(pubCorr.elementIsDisplayed(contactRecord.validate));
            waitFor(2);
            contactRecord.validate.click();
            waitForVisibility(contactRecord.linkCaseConsumer, 10);
            contactRecord.linkCaseConsumer.click();
            waitFor(2);
        } else if (type.equals("taskSearch")) {
            Assert.assertTrue(pubCorr.elementIsDisplayed(contactRecord.validate));
            waitFor(2);
            contactRecord.validate.click();
            waitForVisibility(contactRecord.linkCaseConsumer, 10);
            contactRecord.linkCaseConsumer.click();
            waitFor(2);
        } else {
            Assert.assertTrue(pubCorr.elementIsDisplayed(contactRecord.validateAndLink));
            waitFor(2);
            click(contactRecord.validateAndLink);
            waitFor(3);
        }
    }

    @Then("I verify fields from save event with the {string} generated from clicking validate button")
    public void verifyFieldsConsumerUpdateEventFromValidateButtonClick(String eventName) {
//        waitFor(2);
//        JsonObject saveEventData = getSaveEventDetails();
//        System.out.println("Save Events Data: " + saveEventData);
//        assertNotNull(saveEventData);

        requestParams = generateEventRequest(eventName);
        System.out.println(requestParams);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getConsumerSaveEvents);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameterForProject(requestParams, "NJ-SBE");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().response.toString()+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("SUCCESS"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");
        boolean recordFound = false;

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

            assertEquals(payLoadData.get("projectName").getAsString(), "NJ-SBE");
            assertEquals(payLoadData.get("action").getAsString(), "Update");
            assertEquals(payLoadData.get("recordType").getAsString(), "ConsumerEventRequest");

            if (payLoadData.get("dataObject").getAsJsonObject().get("consumerFirstName").getAsString().equals(firstName)) {
                String correlationId = new JsonParser().parse(eventsData.get("correlationId").toString()).getAsString();
                String uiid = new JsonParser().parse(eventsData.get("uiid").toString()).getAsString();

                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date todayDate = new Date();
                String today = dateFormat.format(todayDate);
                System.out.println("Correlation Id from update event: " + correlationId);
                System.out.println("Uiid from update event: " + uiid);
                System.out.println("Today Date: " + today);

                JsonObject dataObject = payLoadData.get("dataObject").getAsJsonObject();

//                assertEquals(saveEventData.get("consumerId").getAsInt(), dataObject.get("consumerId").getAsInt());
//                assertEquals(saveEventData.get("consumerFirstName").getAsString(), dataObject.get("consumerFirstName").getAsString());
//                assertEquals(saveEventData.get("consumerLastName").getAsString(), dataObject.get("consumerLastName").getAsString());
//                assertEquals(saveEventData.get("consumerDateOfBirth").getAsString(), dataObject.get("consumerDateOfBirth").getAsString());
//                assertEquals(saveEventData.get("consumerStatus").getAsString(), dataObject.get("consumerStatus").getAsString());
//                assertEquals(saveEventData.get("consumerSSN").getAsString(), dataObject.get("consumerSSN").getAsString());
//                assertEquals(saveEventData.get("consumerType").getAsString(), dataObject.get("consumerType").getAsString());
//                assertEquals(saveEventData.get("createdOn").getAsString(), dataObject.get("createdOn").getAsString());
//                assertEquals(saveEventData.get("createdBy").getAsString(), dataObject.get("createdBy").getAsString());
//                assertEquals(saveEventData.get("effectiveStartDate").getAsString(), dataObject.get("effectiveStartDate").getAsString());
                assertEquals(correlationId, dataObject.get("correlationId").getAsString());
                assertEquals(uiid, dataObject.get("uiid").getAsString());
                assertEquals(userId, dataObject.get("updatedBy").getAsString());
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDateForISOFormat(dataObject.get("updatedOn").getAsString()), today);

//                JsonArray saveEventcaseConsumersArray = saveEventData.get("caseConsumer").getAsJsonArray();
//                assertEquals(saveEventcaseConsumersArray.size(), 1);

                JsonArray dataObjectcaseConsumersArray = dataObject.get("caseConsumer").getAsJsonArray();
                assertEquals(dataObjectcaseConsumersArray.size(), 1);

//                assertEquals(saveEventcaseConsumersArray.get(0).getAsJsonObject().get("caseConsumerId").getAsInt(),
//                        dataObjectcaseConsumersArray.get(0).getAsJsonObject().get("caseConsumerId").getAsInt());
//                assertEquals(saveEventcaseConsumersArray.get(0).getAsJsonObject().get("caseId").getAsInt(),
//                        dataObjectcaseConsumersArray.get(0).getAsJsonObject().get("caseId").getAsInt());
//                assertEquals(saveEventcaseConsumersArray.get(0).getAsJsonObject().get("consumerId").getAsInt(),
//                        dataObjectcaseConsumersArray.get(0).getAsJsonObject().get("consumerId").getAsInt());
//                assertEquals(saveEventcaseConsumersArray.get(0).getAsJsonObject().get("consumerRole").getAsString(),
//                        dataObjectcaseConsumersArray.get(0).getAsJsonObject().get("consumerRole").getAsString());
//                assertEquals(saveEventcaseConsumersArray.get(0).getAsJsonObject().get("effectiveStartDate").getAsString(),
//                        dataObjectcaseConsumersArray.get(0).getAsJsonObject().get("effectiveStartDate").getAsString());
//                assertEquals(saveEventcaseConsumersArray.get(0).getAsJsonObject().get("createdOn").getAsString(),
//                        dataObjectcaseConsumersArray.get(0).getAsJsonObject().get("createdOn").getAsString());
//                assertEquals(saveEventcaseConsumersArray.get(0).getAsJsonObject().get("createdBy").getAsString(),
//                        dataObjectcaseConsumersArray.get(0).getAsJsonObject().get("createdBy").getAsString());
//                assertEquals(saveEventcaseConsumersArray.get(0).getAsJsonObject().get("updatedOn").getAsString(),
//                        dataObjectcaseConsumersArray.get(0).getAsJsonObject().get("updatedOn").getAsString());
//                assertEquals(saveEventcaseConsumersArray.get(0).getAsJsonObject().get("updatedBy").getAsString(),
//                        dataObjectcaseConsumersArray.get(0).getAsJsonObject().get("updatedBy").getAsString());
//
//
//                JsonArray saveEventConsumerIdentificationArray = saveEventData.get("consumerIdentificationNumber").getAsJsonArray();
//                assertEquals(saveEventConsumerIdentificationArray.size(), 1);

                JsonArray dataObjectConsumerIdentificationArray = dataObject.get("consumerIdentificationNumber").getAsJsonArray();
                assertEquals(dataObjectConsumerIdentificationArray.size(), 1);

//                assertEquals(saveEventConsumerIdentificationArray.get(0).getAsJsonObject().get("consumerIdentificationNumberId").getAsInt(),
//                        dataObjectConsumerIdentificationArray.get(0).getAsJsonObject().get("consumerIdentificationNumberId").getAsInt());
//                assertEquals(saveEventConsumerIdentificationArray.get(0).getAsJsonObject().get("consumerId").getAsInt(),
//                        dataObjectConsumerIdentificationArray.get(0).getAsJsonObject().get("consumerId").getAsInt());
//                assertEquals(saveEventConsumerIdentificationArray.get(0).getAsJsonObject().get("externalConsumerId").getAsString(),
//                        dataObjectConsumerIdentificationArray.get(0).getAsJsonObject().get("externalConsumerId").getAsString());
//                assertEquals(saveEventConsumerIdentificationArray.get(0).getAsJsonObject().get("identificationNumberType").getAsString(),
//                        dataObjectConsumerIdentificationArray.get(0).getAsJsonObject().get("identificationNumberType").getAsString());
//                assertEquals(saveEventConsumerIdentificationArray.get(0).getAsJsonObject().get("effectiveStartDate").getAsString(),
//                        dataObjectConsumerIdentificationArray.get(0).getAsJsonObject().get("effectiveStartDate").getAsString());
//                assertEquals(saveEventConsumerIdentificationArray.get(0).getAsJsonObject().get("createdOn").getAsString(),
//                        dataObjectConsumerIdentificationArray.get(0).getAsJsonObject().get("createdOn").getAsString());
//                assertEquals(saveEventConsumerIdentificationArray.get(0).getAsJsonObject().get("createdBy").getAsString(),
//                        dataObjectConsumerIdentificationArray.get(0).getAsJsonObject().get("createdBy").getAsString());
//                assertEquals(saveEventConsumerIdentificationArray.get(0).getAsJsonObject().get("updatedOn").getAsString(),
//                        dataObjectConsumerIdentificationArray.get(0).getAsJsonObject().get("updatedOn").getAsString());
//                assertEquals(saveEventConsumerIdentificationArray.get(0).getAsJsonObject().get("updatedBy").getAsString(),
//                        dataObjectConsumerIdentificationArray.get(0).getAsJsonObject().get("updatedBy").getAsString());

                if (eventsData.get("eventName").equals(eventName)) {
                    assertEquals(eventsData.get("status"), "success");

                    recordFound = true;
                    break;
                }
            }
        }
        assertTrue(recordFound);
    }

    private JsonObject generateEventRequest(String eventName) {
        JsonObject request = new JsonObject();

        request.addProperty("eventId", "");
        request.addProperty("eventName", eventName);
        request.addProperty("module", "");
        request.addProperty("payload", "");
        request.addProperty("status", "");
        request.addProperty("errorStack", "");
        //request.addProperty("correlationId", "1176586223339058");
        request.addProperty("correlationId", "");
        request.addProperty("uiid", "");
        request.addProperty("createdOn", "");
        request.addProperty("updatedOn", "");
        request.addProperty("createdBy", "");
        request.addProperty("updatedBy", "");
        request.addProperty("message", "");

        return request;
    }

    private JsonObject getSaveEventDetails() {
        requestParams = generateEventRequest("CONSUMER_SAVE_EVENT");
        System.out.println(requestParams);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getConsumerSaveEvents);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameterForProject(requestParams, "NJ-SBE");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();

            if (payLoadData.get("dataObject").getAsJsonObject().get("consumerFirstName").getAsString().equals(firstName)) {
                return payLoadData.get("dataObject").getAsJsonObject();
            }
        }

        return null;
    }

    @Then("I verify updatedOn and updatedBy matches the createdon and createdBy values for {string} event for NJ")
    public void iVerifyUpdatedOnAndUpdatedByMatchesTheCreatedonAndCreatedByValuesForEventForNJ(String eventName) {
        JsonObject saveEventData = getSaveEventDetails();
        System.out.println("Save Events Data: " + saveEventData);
        assertNotNull(saveEventData);
        requestParams = generateEventRequest(eventName);
        System.out.println(requestParams);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getConsumerSaveEvents);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameterForProject(requestParams, "NJ-SBE");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("eventsList.content");

        if (eventName.equals("CONSUMER_UPDATE_EVENT")) {
            HashMap eventsData = (HashMap) eventsContent.get(0);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

            assertNotEquals(dataObjectData.get("createdBy").getAsString(), (dataObjectData.get("updatedBy").getAsString()));
            assertNotEquals(dataObjectData.get("updatedOn").getAsString().substring(0,15), (dataObjectData.get("eventCreatedOn").getAsString().substring(0,15)));

            JsonArray consumerIdentificationNumber = dataObjectData.get("consumerIdentificationNumber").getAsJsonArray();

            assertEquals(getCurrentDateInYearFormat(), dataObjectData.get("updatedOn").getAsString().substring(0, 10));
            assertEquals(consumerIdentificationNumber.get(0).getAsJsonObject().get("createdBy").getAsString(),
                    consumerIdentificationNumber.get(0).getAsJsonObject().get("updatedBy").getAsString());

            assertTrue(consumerIdentificationNumber.get(0).getAsJsonObject().get("updatedOn").getAsString().
                    equalsIgnoreCase(consumerIdentificationNumber.get(0).getAsJsonObject().get("createdOn").getAsString()));
            if (dataObjectData.get("consumerConsent").isJsonNull()) {
                System.out.println("consumerConsent field is null");
            } else {
                JsonArray consumerConsent = dataObjectData.get("consumerConsent").getAsJsonArray();

                Assert.assertEquals(consumerConsent.get(0).getAsJsonObject().get("createdBy").getAsString(),
                        consumerConsent.get(0).getAsJsonObject().get("updatedBy").getAsString());
                assertTrue(consumerConsent.get(0).getAsJsonObject().get("updatedOn").getAsString().
                        equalsIgnoreCase(consumerConsent.get(0).getAsJsonObject().get("createdOn").getAsString()));
            }
            if (dataObjectData.get("caseConsumer").isJsonNull()) {
                System.out.println("CaseConsumer field is null");
            } else {
                JsonArray caseConsumer = dataObjectData.get("caseConsumer").getAsJsonArray();

                Assert.assertNotEquals(caseConsumer.get(0).getAsJsonObject().get("createdBy").getAsString(),
                        caseConsumer.get(0).getAsJsonObject().get("updatedBy").getAsString());

                assertEquals(getCurrentDateInYearFormat(), caseConsumer.get(0).getAsJsonObject().get("updatedOn").getAsString().substring(0, 10));
                assertNotEquals(caseConsumer.get(0).getAsJsonObject().get("updatedOn").getAsString().substring(0, 15),
                        (caseConsumer.get(0).getAsJsonObject().get("createdOn").getAsString().substring(0, 15)));
            }
        }
    }


}
