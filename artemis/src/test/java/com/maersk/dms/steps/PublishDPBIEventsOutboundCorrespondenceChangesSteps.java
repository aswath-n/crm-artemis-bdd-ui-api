package com.maersk.dms.steps;

import com.maersk.crm.step_definitions.CRM_ContactRecordUIStepDef;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.World;
import com.maersk.dms.step_definitions.ObtainCorrespondenceItemsFromECMSRequestStepDefs;
import com.maersk.dms.step_definitions.PublishDPBIEventsOutboundCorrespondenceChangesStepDefs;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.testng.Assert;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublishDPBIEventsOutboundCorrespondenceChangesSteps {

    final ThreadLocal<PublishDPBIEventsOutboundCorrespondenceChangesStepDefs> outCorr = ThreadLocal.withInitial(PublishDPBIEventsOutboundCorrespondenceChangesStepDefs::new);
    final ThreadLocal<ObtainCorrespondenceItemsFromECMSRequestStepDefs> oCIF = ThreadLocal.withInitial(ObtainCorrespondenceItemsFromECMSRequestStepDefs::new);
    final ThreadLocal<Map<String, String>> createdCorrUI = ThreadLocal.withInitial(HashMap::new);
    final ThreadLocal<Map<String, String>> givenInfo = ThreadLocal.withInitial(HashMap::new);
    final ThreadLocal<Map<String, String>> updateEventUI = ThreadLocal.withInitial(HashMap::new);
    final ThreadLocal<Map<String, String>> searchQAEventsDatesPayload = ThreadLocal.withInitial(HashMap::new);
    final ThreadLocal<List<String>> notificationInfoUI = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<String> searchQAEvent = ThreadLocal.withInitial(()->null);
    final ThreadLocal<String> networkLocationString = ThreadLocal.withInitial(()->null);
    final ThreadLocal<String> networkId = ThreadLocal.withInitial(()->null);
    public final ThreadLocal<String> accessTokenGetOAuthQA = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> accessToken = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<Map<String, String>> inboundInfo = ThreadLocal.withInitial(HashMap::new);

    @Given("that I have created an Outbound Correspondence through the Post Correspondence endpoint {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string}")
    public void that_I_have_created_an_Outbound_Correspondence_through_the_Post_Correspondence_endpoint(String caseId, String MMSCode, String language, String channelType, String createdBy, String firstName,
                                                                                                        String lastName, String role, String streetAddress, String streetAddionalLine1, String city,
                                                                                                        String state, String zipcode) throws IOException {
        accessTokenGetOAuthQA.set(outCorr.get().getOAuthQA());
        String rawBody = outCorr.get().rawBodyCreateCorrespondence(caseId, MMSCode, language, channelType, createdBy, firstName,
                lastName, role, streetAddress, streetAddionalLine1, city,
                state, zipcode);
        givenInfo.get().put("caseId", caseId);
        givenInfo.get().put("MMSCode", MMSCode);
        givenInfo.get().put("language", language);
        givenInfo.get().put("channelType", channelType);
        givenInfo.get().put("createdBy", createdBy);
        givenInfo.get().put("firstName", firstName);
        givenInfo.get().put("lastName", lastName);
        givenInfo.get().put("role", role);
        givenInfo.get().put("streetAddress", streetAddress);
        givenInfo.get().put("streetAddionalLine1", streetAddionalLine1);
        givenInfo.get().put("city", city);
        givenInfo.get().put("state", state);
        givenInfo.get().put("zipcode", zipcode);

        String createdAPICorr = outCorr.get().createCorrespondence(accessTokenGetOAuthQA.get(), rawBody);

        Assert.assertTrue(createdAPICorr.contains(caseId));
        Assert.assertTrue(createdAPICorr.contains(MMSCode));
        Assert.assertTrue(createdAPICorr.contains(language));
        Assert.assertTrue(createdAPICorr.contains(channelType));
        Assert.assertTrue(createdAPICorr.contains(createdBy));
        Assert.assertTrue(createdAPICorr.contains(firstName));
        Assert.assertTrue(createdAPICorr.contains(lastName));
        Assert.assertTrue(createdAPICorr.contains(role));
        Assert.assertTrue(createdAPICorr.contains(streetAddress));
        Assert.assertTrue(createdAPICorr.contains(streetAddionalLine1));
        Assert.assertTrue(createdAPICorr.contains(city));
        Assert.assertTrue(createdAPICorr.contains(state));
        Assert.assertTrue(createdAPICorr.contains(zipcode));
    }

    @When("I search {string} in the Get Events endpoint")
    public void i_search_in_the_Get_Events_endpoint(String eventName) throws IOException {
        accessToken.set( outCorr.get().getAccessToken());
        searchQAEventsDatesPayload.set(outCorr.get().searchQAEventsPayloadDates(accessToken.get(), eventName));
        if (eventName.compareTo("OUTBOUND_CORRESPONDENCE_SAVE_EVENT") == 0) {
            searchQAEvent.set(outCorr.get().searchQAEvents(accessToken.get(), eventName));
            Assert.assertTrue(searchQAEvent.get().contains(givenInfo.get().get("caseId")));
            Assert.assertTrue(searchQAEvent.get().contains(givenInfo.get().get("MMSCode")));
            Assert.assertTrue(searchQAEvent.get().contains(givenInfo.get().get("language")));
            Assert.assertTrue(searchQAEvent.get().contains(givenInfo.get().get("channelType")));
            Assert.assertTrue(searchQAEvent.get().contains(givenInfo.get().get("createdBy")));
            Assert.assertTrue(searchQAEvent.get().contains(givenInfo.get().get("firstName")));
            Assert.assertTrue(searchQAEvent.get().contains(givenInfo.get().get("lastName")));
            Assert.assertTrue(searchQAEvent.get().contains(givenInfo.get().get("role")));
            Assert.assertTrue(searchQAEvent.get().contains(givenInfo.get().get("streetAddress")));
            Assert.assertTrue(searchQAEvent.get().contains(givenInfo.get().get("streetAddionalLine1")));
            Assert.assertTrue(searchQAEvent.get().contains(givenInfo.get().get("city")));
            Assert.assertTrue(searchQAEvent.get().contains(givenInfo.get().get("state")));
            Assert.assertTrue(searchQAEvent.get().contains(givenInfo.get().get("zipcode")));
        } else if (eventName.compareTo("OUTBOUND_CORRESPONDENCE_UPDATE_EVENT") == 0) {
            searchQAEvent.set( outCorr.get().searchQAEvents(accessToken.get(), eventName));
            Assert.assertTrue(searchQAEvent.get().contains(updateEventUI.get().get("caseIDUI")));
            Assert.assertTrue(searchQAEvent.get().contains(updateEventUI.get().get("typeUI")));
            Assert.assertTrue(searchQAEvent.get().contains(updateEventUI.get().get("createdByIDUI")));
            Assert.assertTrue(searchQAEvent.get().contains(updateEventUI.get().get("languageUI")));
            Assert.assertTrue(searchQAEvent.get().contains(updateEventUI.get().get("statusUI")));
            Assert.assertTrue(searchQAEvent.get().contains(updateEventUI.get().get("statusReason")));
        } else if (eventName.compareTo("NOTIFICATION_SAVE_EVENT") == 0) {
            searchQAEvent.set( outCorr.get().searchQAEvents(accessToken.get(), eventName));
            for (int i = 0; i <= notificationInfoUI.get().size() - 1; i++) {
                Assert.assertTrue(searchQAEvent.get().contains(notificationInfoUI.get().get(i)));
            }
        }
    }

    @Given("that I have an Update Inbound Correspondence in Onbase with a key as {string} and value as {string} and {string} as {string}")
    public void that_I_have_an_Update_Inbound_Correspondence_in_Onbase_with_a_key_as_and_value_as_and_as(String key1, String value1, String key2, String value2) throws IOException {
        inboundInfo.get().put(key1, value1);
        inboundInfo.get().put(key2, value2);
        oCIF.get().updateInboundCorrespondenceInOnbase(oCIF.get().getAccessTokenBLCRMDMS(), key1, value1, key2, value2);
    }

    @When("I search {string} the Get Events")
    public void i_search_the_Get_Events(String string) throws IOException {
        accessToken.set(outCorr.get().getAccessToken());
        searchQAEvent.set(outCorr.get().searchQAEvents(accessToken.get(), string));
        searchQAEventsDatesPayload.set(outCorr.get().searchQAEventsPayloadDates(accessToken.get(), string));
    }

    @Then("Pass all datetime fields {string} {string} {string} and dataObject {string} and value as {string} for {string}")
    public void pass_all_datetime_fields_and_dataObject_and_value_as(String action, String recordType, String eventCreatedOn, String caseId, String value, String event) {
        if (!searchQAEvent.get().isEmpty()) {
            Assert.assertTrue(searchQAEvent.get().contains(action));
            Assert.assertTrue(searchQAEvent.get().contains(recordType));
            Assert.assertTrue(searchQAEvent.get().contains(eventCreatedOn));
            if (event.compareTo("INBOUND_CORRESPONDENCE_SAVE_EVENT") == 0) {
                if (!searchQAEventsDatesPayload.get().get("dataObject").isEmpty()) {
                    Assert.assertTrue(searchQAEvent.get().contains(caseId));
                    Assert.assertTrue(searchQAEvent.get().contains(value));
                    JSONObject payloadObject = new JSONObject(searchQAEventsDatesPayload.get().get("dataObject"));
                    Assert.assertTrue(payloadObject.get("createdOn").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                    Assert.assertTrue(searchQAEventsDatesPayload.get().get("createdOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                    Assert.assertTrue(searchQAEventsDatesPayload.get().get("eventCreatdOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                    Assert.assertTrue(searchQAEvent.get().contains("\"updatedOn\":null"));
                }
            } else if (event.compareTo("INBOUND_CORRESPONDENCE_UPDATE_EVENT") == 0) {
                if (!searchQAEventsDatesPayload.get().get("dataObject").isEmpty()) {
                    JSONObject payloadObject = new JSONObject(searchQAEventsDatesPayload.get().get("dataObject"));
                    Assert.assertTrue(payloadObject.get("createdOn").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                    Assert.assertTrue(payloadObject.get("updatedOn").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                    Assert.assertTrue(searchQAEventsDatesPayload.get().get("createdOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                    Assert.assertTrue(searchQAEventsDatesPayload.get().get("eventCreatdOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                    Assert.assertTrue(searchQAEvent.get().contains(inboundInfo.get().get("caseId")));
                    Assert.assertTrue(searchQAEvent.get().contains(inboundInfo.get().get("id")));
                }
            }
        }
    }

    @Then("Pass all datetime fields {string} {string} {string} and dataObject for {string}")
    public void pass_all_datetime_fields_and_dataObject(String action, String recordType, String eventCreatedOn, String event) throws IOException {
        searchQAEvent.set(outCorr.get().searchQAEvents(outCorr.get().getAccessToken(), event));
        searchQAEventsDatesPayload.set(outCorr.get().searchQAEventsPayloadDates(outCorr.get().getAccessToken(), event));
        if (!searchQAEvent.get().isEmpty()) {
            Assert.assertTrue(searchQAEvent.get().contains(action));
            Assert.assertTrue(searchQAEvent.get().contains(recordType));
            Assert.assertTrue(searchQAEvent.get().contains(eventCreatedOn));
            if (event.compareTo("OUTBOUND_CORRESPONDENCE_SAVE_EVENT") == 0 || event.compareTo("OUTBOUND_CORRESPONDENCE_UPDATE_EVENT") == 0) {
                JSONObject payloadObject = new JSONObject(searchQAEventsDatesPayload.get().get("dataObject"));
                Assert.assertTrue(payloadObject.get("createdDatetime").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(payloadObject.get("statusDatetime").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("createdOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("eventCreatedOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("updatedOn").replaceAll("[\\[?\\]]", "").contains("null"));

            } else if (event.compareTo("OUTBOUND_CORRESPONDENCE_DEFINITION_SAVE_EVENT") == 0) {
                JSONObject payloadObject = new JSONObject(searchQAEventsDatesPayload.get().get("dataObject"));
                Assert.assertTrue(payloadObject.get("createdDatetime").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("createdOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("eventCreatedOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("updatedOn").replaceAll("[\\[?\\]]", "").contains("null"));

            } else if (event.compareTo("OUTBOUND_CORRESPONDENCE_DEFINITION_UPDATE_EVENT") == 0) {
                JSONObject payloadObject = new JSONObject(searchQAEventsDatesPayload.get().get("dataObject"));
                Assert.assertTrue(payloadObject.get("createdDatetime").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(payloadObject.get("endDate").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(payloadObject.get("startDate").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(payloadObject.get("updatedDatetime").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("createdOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("eventCreatedOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("updatedOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));

            } else if (event.compareTo("OUTBOUND_CORRESPONDENCE_DEFINITION_CHANNEL_SAVE_EVENT") == 0) {
                JSONObject payloadObject = new JSONObject(searchQAEventsDatesPayload.get().get("dataObject"));
                Assert.assertTrue(payloadObject.get("startDate").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(payloadObject.get("createdOn").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(payloadObject.get("endDate").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("createdOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("eventCreatedOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("updatedOn").replaceAll("[\\[?\\]]", "").contains("null"));

            } else if (event.compareTo("OUTBOUND_CORRESPONDENCE_DEFINITION_CHANNEL_UPDATE_EVENT") == 0) {
                JSONObject payloadObject = new JSONObject(searchQAEventsDatesPayload.get().get("dataObject"));
                Assert.assertTrue(payloadObject.get("updatedDatetime").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(payloadObject.get("startDate").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(payloadObject.get("createdOn").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(payloadObject.get("endDate").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("createdOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("eventCreatedOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("updatedOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));

            } else if (event.compareTo("OUTBOUND_CORRESPONDENCE_SETTINGS_SAVE_EVENT") == 0) {
                JSONObject payloadObject = new JSONObject(searchQAEventsDatesPayload.get().get("dataObject"));
                Assert.assertTrue(payloadObject.get("createdDate").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("createdOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("eventCreatedOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("updatedOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));

            } else if (event.compareTo("OUTBOUND_CORRESPONDENCE_SETTINGS_UPDATE_EVENT") == 0) {
                JSONObject payloadObject = new JSONObject(searchQAEventsDatesPayload.get().get("dataObject"));
                Assert.assertTrue(payloadObject.get("createdDate").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(payloadObject.get("updatedDate").toString().replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("createdOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("eventCreatedOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
                Assert.assertTrue(searchQAEventsDatesPayload.get().get("updatedOn").replaceAll("[\\[?\\]]", "").matches("[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0]:[0][0]"));
            }
        }
    }


    @Then("I should see {string} Event was created")
    public void i_should_see_Event_was_created(String eventName) throws IOException {
        accessToken.set(outCorr.get().getAccessToken());
        String eventID = outCorr.get().searchQAEventsEventID(accessToken.get(), eventName);
        if (!eventID.replaceAll("[^a-zA-Z0-9_-]", "").isEmpty()) {
            String EventNameAPI = outCorr.get().getEventSubscriberRecordId(accessToken.get(), eventID.replaceAll("[^a-zA-Z0-9_-]", ""));
            Assert.assertEquals(EventNameAPI.replaceAll("[^a-zA-Z0-9_-]", ""), eventName);
        }
    }

    @Given("that I have created an Outbound Correspondence through the UI {string} {string} {string}")
    public void that_I_have_created_an_Outbound_Correspondence_through_the_UI(String streetAddress, String streetAdditionalLine1, String faxNum) {
        outCorr.get().clickMenuIconDropDown();
        outCorr.get().clickMenuIconCreateCorrOpt();
        outCorr.get().verifyOnCreateOutboundCorrespondenceSection();
        String type[] = outCorr.get().selectFirstOptionTypeStoreOpt().split(" - ");
        String language = outCorr.get().selectFirstOptionLanguageStoreOpt();
        outCorr.get().clickOnFirstIndividual();
        outCorr.get().linkFaxStoreNumber(faxNum);
        outCorr.get().linkEmailStoreNumber("test@test.com");
        createdCorrUI.set(outCorr.get().linkEmailAndAdditionalInfoStoreMap(streetAddress, streetAdditionalLine1));
        createdCorrUI.get().put("type", type[1]);
        createdCorrUI.get().put("language", language);
        createdCorrUI.get().put("faxNum", faxNum);
        outCorr.get().clickSaveButton();
        outCorr.get().verifySuccessMessageVisible();
    }

    @When("I search {string} the Get Events endpoint for UI")
    public void i_search_the_Get_Events_endpoint_for_UI(String string) throws IOException {
        accessToken.set(outCorr.get().getAccessToken());
        searchQAEvent.set(outCorr.get().searchQAEvents(accessToken.get(), string));
        System.out.println(searchQAEvent.get());
        System.out.println(createdCorrUI.get());
        Assert.assertTrue(searchQAEvent.get().contains(createdCorrUI.get().get("type")));
        Assert.assertTrue(searchQAEvent.get().contains(createdCorrUI.get().get("language")));
        try {
            if (!createdCorrUI.get().get("Address1").isEmpty()) {
                Assert.assertTrue(searchQAEvent.get().contains(createdCorrUI.get().get("Address1")));
                Assert.assertTrue(searchQAEvent.get().contains(createdCorrUI.get().get("address2")));
                Assert.assertTrue(searchQAEvent.get().contains(createdCorrUI.get().get("city")));
                Assert.assertTrue(searchQAEvent.get().contains(createdCorrUI.get().get("state")));
                Assert.assertTrue(searchQAEvent.get().contains(createdCorrUI.get().get("zipCode")));
                Assert.assertTrue(searchQAEvent.get().contains(createdCorrUI.get().get("role")));
                Assert.assertTrue(searchQAEvent.get().contains(createdCorrUI.get().get("caseID")));
                Assert.assertTrue(searchQAEvent.get().contains(createdCorrUI.get().get("firstName")));
                Assert.assertTrue(searchQAEvent.get().contains(createdCorrUI.get().get("lastName")));
            }
        } catch (java.lang.NullPointerException e) {
        }
    }

    @Given("that I have update an Outbound Correspondence through the UI {string}")
    public void that_I_have_update_an_Outbound_Correspondence_through_the_UI(String status) {
        updateEventUI.set(outCorr.get().updateCIDNotificationByStatus(status));
    }

    @Given("that I have an Outbound Correspondence marked as {string}")
    public void that_I_have_an_Outbound_Correspondence_marked_as(String status) {
        notificationInfoUI.set(outCorr.get().saveNotificationByStatus(status));
        System.out.println(notificationInfoUI.get());
    }

    @When("I search for that filepath network location with value as {string} to get credentials")
    public void iSearchForThatFilepathNetworkLocationWithValueAsToGetCredentials(String id) throws IOException {
        networkId.set(id);
        BrowserUtils.waitFor(60);
        if("fromRequest".equalsIgnoreCase(id)){
            networkId.set(World.generalSave.get().get("InboundDocumentId").toString());
        }
        networkLocationString.set(outCorr.get().getNetworkLocationByID("token", networkId.get()));
    }

    @Then("I will verify the document matches {string} and is successful")
    public void iWillVerifyTheDocumentMatchesNonprodQEECMSFilesAndIsSuccessfull(String path) {
        Assert.assertTrue(networkLocationString.get().contains("SUCCESS"));
        Assert.assertTrue(networkLocationString.get().contains("filePath"));
        Assert.assertTrue(networkLocationString.get().contains("UVAAPMMFSG01SBE\\njsbe-nonprod\\QE\\ECMSFiles"));
        Assert.assertTrue(networkLocationString.get().contains("pdf"));
        Assert.assertTrue(networkLocationString.get().contains(networkId.get()));
    }

    @Then("I will verify the document matches {string} and is unsuccessful")
    public void iWillVerifyTheDocumentMatchesNonprodQEECMSFilesAndIsUnsuccessful(String path) {
        Assert.assertTrue(networkLocationString.get().contains("Failed"));
        Assert.assertTrue(networkLocationString.get().contains("Unable to fetch the document for document ID " + networkId.get()));
        Assert.assertTrue(networkLocationString.get().contains("filePath"));
    }

    @Then("I will verify the document matches {string} and is Successful")
    public void iWillVerifyTheDocumentMatchesNonprodQEECMSFilesAndIsSuccessful(String path) {
        Assert.assertTrue(networkLocationString.get().contains("SUCCESS"));
        Assert.assertTrue(networkLocationString.get().contains("filePath"));
        Assert.assertTrue(networkLocationString.get().contains("\\\\UVAAPMMFSG01CVA\\vacv-nonprod\\QE\\ECMSFiles"));
        Assert.assertTrue(networkLocationString.get().contains("pdf"));
        Assert.assertTrue(networkLocationString.get().contains(networkId.get()));
    }

}
