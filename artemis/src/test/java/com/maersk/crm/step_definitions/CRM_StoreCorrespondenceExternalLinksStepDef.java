package com.maersk.crm.step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.pages.crm.CRMCreateGeneralTaskPage;
import com.maersk.crm.pages.tm.TMProjectListPage;
import com.maersk.crm.utilities.*;
import com.maersk.crm.utilities.data_Module.crm_core_data_module.APIUtilitiesForUIScenarios;
import com.maersk.dms.pages.CaseAndContactDetailsPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class CRM_StoreCorrespondenceExternalLinksStepDef extends CRMUtilities implements ApiBase {

    private final String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private final String caseConsumerBaseURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private final String createCaseWithConsumers = "app/crm/caseloader";
    private final String getCase = "app/crm/case/consumers";
    private final String correspondenceURI = ConfigurationReader.getProperty("apiDMSCorrespondence");
    private final String postCorrespondences = "correspondences";
    private final ThreadLocal<String> endpointCorrespondence = ThreadLocal.withInitial(String::new);
    private final String consumerLinkURI = ConfigurationReader.getProperty("apiConsumerLink");
    private final String caseLinkURI = ConfigurationReader.getProperty("apiCaseLink");
    private String caseUrl2 = "?size=100&page=0&sort=effectiveStartDate,desc";
    private final String correpondenceURI = ConfigurationReader.getProperty("apiCorresLink");
    private String correspondenceUrl = "?size=5&page=0";
    private final String eventURI = ConfigurationReader.getProperty("apiEventsURI");
    private final ThreadLocal<String> eventsCorrelationIdEndPoint = ThreadLocal.withInitial(() -> "/app/crm/event/correlation/");

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> caseId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> internalId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> correlationId = ThreadLocal.withInitial(String::new);
    final ThreadLocal<APIClassUtil> response = ThreadLocal.withInitial(() -> APIClassUtil.getApiClassUtilThreadLocal(true));

    private final ThreadLocal<ApiTestDataUtil> apitdu = ThreadLocal.withInitial(() -> ApiTestDataUtil.getApiTestDataUtilThreadLocal(true));
    CRMCreateGeneralTaskPage crmCreateGeneralTaskPage = new CRMCreateGeneralTaskPage();
    CaseAndContactDetailsPage correspondence = new CaseAndContactDetailsPage();
    TMProjectListPage tmProjectListPage = new TMProjectListPage();

    @When("I create a case to use for Outbound correspondence request")
    public void createCaseForOutboundCorrespondence() {
        World.generalSave.get().put("caseConsumerId", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId());
        APIUtilitiesForUIScenarios.consumerFirstName.set(String.valueOf(World.generalSave.get().get("firstName")));
        APIUtilitiesForUIScenarios.consumerLastName.set(String.valueOf(World.generalSave.get().get("lastName")));
    }

    @And("I get case id and consumer id of the created case")
    public void getCaseConsumerId() {
//        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerBaseURI);
//        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getCase);
//
//        requestParams.set(new JsonObject());
//        requestParams.get().addProperty("consumerFirstName", firstName.get());
//
//        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
//        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
//        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
//
//        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
//        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");


//        ArrayList caseResults = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result");
//        HashMap caseResult = (HashMap) caseResults.get(0);
//        HashMap cases = (HashMap) caseResult.get("cases");
        caseId.set(caseConsumerId.get("caseId"));
        consumerId.set(caseConsumerId.get("consumerId"));

        System.out.println("Case Id: " + caseId.get() + " Consumer Id: " + consumerId.get());
    }

    @When("I have Outbound Correspondence Request with anchor")
    public void createOutboundCorrespondenceRequestForLinks() {
        endpointCorrespondence.set(correspondenceURI + "/" + postCorrespondences);

        apitdu.set(apitdu.get().getJsonFromFile("dms/apiCreateOutboundCorrespondence.json"));
        apitdu.get().jsonElement.getAsJsonObject().getAsJsonObject("anchor").addProperty("caseId", caseId.get());
        apitdu.get().jsonElement.getAsJsonObject().getAsJsonObject("anchor").get("regardingConsumerId").getAsJsonArray().add(consumerId.get());

        requestParams.set(apitdu.get().jsonElement.getAsJsonObject());
    }

    @When("I send the request for the Outbound Correspondence for external links")
    public void sendOutboundCorrespondenceForLinks() {
        System.out.println(requestParams.get());
        response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().PostCorrespondence(requestParams.get(), endpointCorrespondence.get()));
        System.out.println(response.get().responseString);
    }

    @Then("I should see the anchor values are saved")
    public void validateResponseOutboundCorrespondenceForLinks() {
        assertEquals(response.get().statusCode, 201);
        internalId.set(response.get().jsonPathEvaluator.get("data.id"));
        assertEquals(response.get().jsonPathEvaluator.get("data.correspondenceDefinitionMMSCode").toString(), requestParams.get().get("correspondenceDefinitionMMSCode").getAsString());
        assertEquals(response.get().jsonPathEvaluator.get("data.anchor.caseId").toString(), caseId.get());
        List consumerIdList = response.get().jsonPathEvaluator.get("data.anchor.regardingConsumerId");
        assertEquals(consumerIdList.get(0).toString(), consumerId.get());
    }

    @Then("I should see a link in caseConsumer service with the values of {string}")
    public void seeLinkCaseConsumerService(String idType) {
        String endPoint = null;
        if (idType.contains("case")) {
            endPoint = caseLinkURI + "/" + caseId.get() + caseUrl2;
        } else if (idType.contains("consumer")) {
            endPoint = consumerLinkURI + "/" + consumerId.get() + caseUrl2;
        }
        BrowserUtils.waitFor(15);
        APIClassUtil caseLinksResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondenceDocumetDetails(endPoint);

        System.out.println(caseLinksResponse.responseString);
        assertEquals(caseLinksResponse.jsonPathEvaluator.get("status"), "success");

        ArrayList links = caseLinksResponse.jsonPathEvaluator.get("externalLinkDetails.content");
        HashMap linksData = (HashMap) links.get(0);

        if (idType.contains("case")) {
            assertEquals(linksData.get("internalId"), Integer.parseInt(caseId.get()));
        } else if (idType.contains("consumer")) {
            assertEquals(linksData.get("internalId"), Integer.parseInt(consumerId.get()));
        }
        assertEquals(linksData.get("id"), Integer.parseInt(internalId.get()));
        assertNotNull(linksData.get("externalLinkId"));
        assertTrue(verifyDatetimeFormat(linksData.get("effectiveStartDate").toString()));
        assertTrue(verifyDatetimeFormat(linksData.get("createdOn").toString()));
        correlationId.set(linksData.get("correlationId").toString());
    }

    @Then("I should see a link in correspondence service with the values of {string}")
    public void seeLinkCorrespondenceService(String idType) {
        String endPoint = consumerId.get() + correspondenceUrl;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(correpondenceURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(endPoint);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        ArrayList links = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("externalLinkDetails.content");

        for (int i = 0; i < links.size(); i++) {
            HashMap linksData = (HashMap) links.get(i);

            if (idType.contains("case")) {
                assertEquals(linksData.get("name"), "Case");
                assertEquals(linksData.get("id"), caseId.get());
            } else if (idType.contains("consumer")) {
                assertEquals(linksData.get("name"), "Consumer Profile");
                assertEquals(linksData.get("id"), consumerId.get());
            }
            assertEquals(linksData.get("internalId"), internalId.get());
            assertNotNull(linksData.get("externalLinkId"));
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDatetimeFormat((JsonElement) linksData.get("effectiveStartDate")));
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().verifyDatetimeFormat((JsonElement) linksData.get("createdOn")));
        }
    }

    @And("I send a get request for the Outbound Correspondence")
    public void GetOutboundCorrespondence() {
        String endPoint = postCorrespondences + "/" + internalId.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(correspondenceURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(endPoint);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I should see the anchor values are retrieved")
    public void verifyAnchorValuesOutboundCorrespondence() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("correspondenceDefinitionMMSCode").toString(), requestParams.get().get("correspondenceDefinitionMMSCode").getAsString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("anchor.caseId").toString(), caseId.get());
        List consumerIdList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("anchor.regardingConsumerId");
        assertEquals(consumerIdList.get(0).toString(), consumerId.get());
    }

    @Then("I should see a Link to the Task in the Links Section for {string}")
    public void verifyLinksForOutboundCorrespondence(String idType) {
        if (idType.equals("case")) {
            assertNotNull(correspondence.caseCorrespondenceLink);
            assertEquals(correspondence.caseCorrespondenceLink.getText(), caseId.get());
        } else if (idType.equals("consumer")) {
            assertNotNull(correspondence.consumerCorrespondenceLink);
            assertEquals(correspondence.consumerCorrespondenceLink.getText(), consumerId.get());
        }
    }

    @And("I search for the {string} for external links")
    public void searchEventForLinks(String event) {
        eventsCorrelationIdEndPoint.set(eventsCorrelationIdEndPoint.get() + correlationId.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsCorrelationIdEndPoint.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Response for Event: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @And("I search for Outbound Correspondence with the one created for links")
    public void searchInboundCorrespondenceForLinks() {
        waitFor(5);
        crmCreateGeneralTaskPage.notificationId.click();
        waitFor(2);
        clearAndFillText(crmCreateGeneralTaskPage.correspondenceIdSearch, internalId.get());
        tmProjectListPage.search.click();
    }

    @And("I click on the Outbound Correspondence Id result")
    public void clickOutboundCorrespondenceResult() {
        waitForVisibility(correspondence.correspondenceID, 10);
        correspondence.correspondenceID.click();
        waitFor(2);
        scrollDownUsingActions(2);
        waitFor(2);
    }

    @Then("I should see {string} in anchor values in the {string}")
    public void verifyIdEventForLinks(String idType, String event) {
        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
        assertTrue(eventsContent.size() > 0);
        boolean recordFound = false;

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            if (event.equals(eventsData.get("eventName"))) {
                System.out.println(eventsData.get("payload"));

                JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
                JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();
                String anchor = dataObjectData.get("anchor").getAsString();
                JsonObject anchorElem = getJsonFromString(anchor);

                if (idType.contains("case")) {
                    assertEquals(anchorElem.getAsJsonObject().get("caseId").getAsString(), caseId.get());
                } else if (idType.contains("consumer")) {
                    JsonArray consumerIdList = anchorElem.getAsJsonObject().get("regardingConsumerId").getAsJsonArray();
                    assertEquals(consumerIdList.get(0).getAsString(), consumerId.get());
                }
                recordFound = true;
            }
        }
        assertTrue(recordFound);
    }

    private boolean verifyDatetimeFormat(String datetimeField) {
        boolean datetimeFormatCheck = false;
        if (datetimeField.equals("null"))
            return true;

        DateTimeFormatter dtf = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        try {
            LocalDateTime parsedDate = LocalDateTime.parse(datetimeField, dtf);
            if (parsedDate != null)
                datetimeFormatCheck = true;
        } catch (Exception e) {
            System.out.println("Date is not in the expected format");
        }

        return datetimeFormatCheck;
    }

    private JsonObject getJsonFromString(String elem) {
        JsonParser parser = new JsonParser();
        return parser.parse(elem).getAsJsonObject();
    }
}
