package com.maersk.dms.steps;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.maersk.crm.api_step_definitions.APIATSApplicationController;
import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.World;
import com.maersk.dms.utilities.APIAutoUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.Map;

public class BarcodeSteps extends CRMUtilities implements ApiBase {
    private final ThreadLocal<JsonPath> response = new ThreadLocal<>();
    private final ThreadLocal<JsonObject> request = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> barcode = ThreadLocal.withInitial(()->"");

    @And("I have a barcode with {string} value")
    public void iHaveABarcodeWithDigits(String barcode) {
        if ("0".equalsIgnoreCase(barcode)) {
            request.get().add("barcode", JsonNull.INSTANCE);
        } else {
            request.get().addProperty("barcode", barcode);
        }
    }

    @When("I send the barcode request to the service")
    public void iSendTheBarcodeRequestToTheService() {
        synchronized (response){
            response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().barcodeLookup(request.get()));
        }
    }

    @Then("I should see an {string} response from barcode look up")
    public void iShouldSeeAnResponseFromBarcodeLookUp(String errorMessage) {
        Assert.assertEquals("ERROR", response.get().getString("status"));
        Assert.assertEquals(errorMessage, response.get().getString("errors[0].errorCode"));
    }

    @Then("I should see barcode response has the case Id")
    public void iShouldSeeBarcodeResponseHasTheCaseId() {
        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
        String caseId = caseConsumerId.get("caseId");
        Assert.assertEquals(caseId, response.get().getString("data.caseId"));
    }

    @And("I have a barcode value with the {string} notification")
    public void iHaveABarcodeValueWithTheNotification(String nid) {
        if ("previouslyCreated".equalsIgnoreCase(nid)) {
            String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()));
            JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
            JsonPath inboundType = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().searchInboundType("maersk Case + Consumer");
             barcode.set(inboundType.getString("inboundCorrespondence.barcode"));
            nid = response.getString("recipients[0].notifications[0].notificationId");
        }
        request.get().addProperty("barcode", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().barcodeGenerator(nid, barcode.get()));
    }

    @Then("I should see barcode response has the case Id and consumer Id")
    public void iShouldSeeBarcodeResponseHasTheCaseIdAndConsumerId() {
        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
        String caseId = caseConsumerId.get("caseId");
        String consumerId = caseConsumerId.get("consumerId");
        Assert.assertEquals(caseId, response.get().getString("data.caseId"));
        Assert.assertEquals(consumerId, response.get().getString("data.consumerIds[0]"));
    }

    @When("I send the onbase barcode request to the service")
    public void iSendTheOnbaseBarcodeRequestToTheService() {
        synchronized (response){
            response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().onbaseBarcodeLookup(barcode.get()));
        }
    }

    @And("I have a onbase barcode with {string} value")
    public void iHaveAOnbaseBarcodeWithValue(String req) {
        barcode.set(req);
    }

    @Then("I should see an {string} response from onbase barcode look up")
    public void iShouldSeeAnResponseFromOnbaseBarcodeLookUp(String errorMessage) {
        Assert.assertEquals("FAILED", response.get().getString("status"));
        Assert.assertEquals(errorMessage, response.get().getString("message"));
    }

    @Then("I should see onbase barcode response has the case Id")
    public void iShouldSeeOnbaseBarcodeResponseHasTheCaseId() {
        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
        String caseId = caseConsumerId.get("caseId");
        Assert.assertEquals(caseId, response.get().getString("Document.Keywords[0].Value"));
    }

    @And("I have a onbase barcode value with the {string} notification")
    public void iHaveAOnbaseBarcodeValueWithTheNotification(String nid) {
        if ("previouslyCreated".equalsIgnoreCase(nid)) {
            String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()));
            nid = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid).getString("recipients[0].notifications[0].notificationId");
        }
//        barcode =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().barcodeGenerator(nid, "0004");
        JsonPath inboundType = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().searchInboundType("maersk Case + Consumer");
        barcode.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().barcodeGenerator(nid, inboundType.getString("inboundCorrespondence.barcode")));
    }

    @Then("I should see onbase barcode response has the case Id and consumer Id")
    public void iShouldSeeOnbaseBarcodeResponseHasTheCaseIdAndConsumerId() {
        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
        String caseId = caseConsumerId.get("caseId");
        String consumerId = caseConsumerId.get("consumerId");
        Assert.assertEquals(caseId, response.get().getString("Document.Keywords[0].Value"));
        Assert.assertEquals(consumerId, response.get().getString("Document.Keywords[1].Value"));
    }

    @Then("I should see barcode response has the {string} application Id")
    public void iShouldSeeBarcodeResponseHasTheApplicationId(String appId) {
        if(appId.equalsIgnoreCase("previouslyCreatedATS"))
            appId=APIATSApplicationController.applicationIdAPI.get();
        Assert.assertEquals(appId, response.get().getString("data.applicationId"));
    }

    @Then("I should see onbase barcode response has the {string} application Id")
    public void iShouldSeeOnbaseBarcodeResponseHasTheApplicationId(String appId) {
        if(appId.equalsIgnoreCase("previouslyCreatedATS"))
            appId=APIATSApplicationController.applicationIdAPI.get();
        Assert.assertEquals(appId, response.get().getString("Document.Keywords[0].Value"));
    }
}
