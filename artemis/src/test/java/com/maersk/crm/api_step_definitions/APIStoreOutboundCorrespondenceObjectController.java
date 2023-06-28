package com.maersk.crm.api_step_definitions;

import com.maersk.crm.utilities.APIClassUtil;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.dms.utilities.APIAutoUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.testng.Assert.*;

public class APIStoreOutboundCorrespondenceObjectController {

    private String apiBffCorrespondence = ConfigurationReader.getProperty("apiBffCorrespondence");
    private String createCorrespondence = "/ecms/correspondence/outbound/notification";
    private String documentIdCheck = "/ecms/correspondence/inbound/document/";

    private final ThreadLocal<APIAutoUtilities> apiAutoUtilities = ThreadLocal.withInitial(APIAutoUtilities::new);

    final ThreadLocal<String> createOutboundCorrespondenceEndPoint = ThreadLocal.withInitial(() -> null);
    final ThreadLocal<String> documentIdCheckEndPoint = ThreadLocal.withInitial(() -> null);
    APIClassUtil createCorrespondenceResponse = null;
    final ThreadLocal<Integer> documentId = ThreadLocal.withInitial(() -> 0);
    APIClassUtil documentIdCheckeResponse = null;

    private final ThreadLocal<String> correspondenceType = ThreadLocal.withInitial(() -> "maersk Outbound Correspondence");

    @Given("I initiated outbound Correspondence save API")
    public void initiateOutboundCorrespondenceSaveAPI() {
        createOutboundCorrespondenceEndPoint.set(apiBffCorrespondence + createCorrespondence);
    }

    @And("I create outbound correspondence save request with {string} request details and run it")
    public void createOutboundCorrespondenceSaveReq(String type) {
        if (type.equals("correct")) {
            createCorrespondenceResponse = apiAutoUtilities.get().saveOutboundCorrespondence("1215", createOutboundCorrespondenceEndPoint.get());
        } else if (type.equals("incorrect")) {
            createCorrespondenceResponse = apiAutoUtilities.get().saveOutboundCorrespondenceNoInputFile("1215", createOutboundCorrespondenceEndPoint.get());
        }
        System.out.println(createCorrespondenceResponse.responseString);
    }

    @Then("I validate response that outbound correspondence is successfully saved")
    public void validateOutboundCorrespondenceResponse() {
        System.out.println(createCorrespondenceResponse.responseString);
        assertTrue(createCorrespondenceResponse.responseString.contains("success"));
        assertEquals(createCorrespondenceResponse.jsonPathEvaluator.get("status"), "Success");
        assertEquals(createCorrespondenceResponse.jsonPathEvaluator.get("message"), "Document Saved successfully");
        assertNotNull(createCorrespondenceResponse.jsonPathEvaluator.get("documentId"));

        documentId.set(Integer.parseInt(createCorrespondenceResponse.jsonPathEvaluator.get("documentId")));
        System.out.println("Document Id: " + documentId.get());
    }

    @Then("I validate response that outbound correspondence is successfully saved and documentId is returned")
    public void validateOutboundCorrespondenceResponseAndDocumentId() {
        assertTrue(createCorrespondenceResponse.responseString.contains("success"));
        assertEquals(createCorrespondenceResponse.jsonPathEvaluator.get("status"), "Success");

        documentId.set(Integer.parseInt(createCorrespondenceResponse.jsonPathEvaluator.get("documentId")));
        System.out.println("Document Id: " + documentId.get());
    }

    @Then("I also validate the outbound correspondence created details successfully")
    public void validateOutboundCorrespondenceCreatedDetails() {
        documentIdCheck = documentIdCheck + documentId.get();
        documentIdCheckEndPoint.set(apiBffCorrespondence + documentIdCheck);

        documentIdCheckeResponse = apiAutoUtilities.get().getCorrespondenceDocumetDetails(documentIdCheckEndPoint.get());

        System.out.println(documentIdCheckeResponse.responseString);
        assertTrue(documentIdCheckeResponse.responseString.contains("SUCCESS"));
        assertEquals(documentIdCheckeResponse.jsonPathEvaluator.get("status"), "SUCCESS");
        System.out.println("Document Id: " + documentIdCheckeResponse.jsonPathEvaluator.get("inboundCorrespondence.id"));
        int actualDocId = documentIdCheckeResponse.jsonPathEvaluator.get("inboundCorrespondence.id");
        assertEquals(actualDocId, documentId.get());
        assertEquals(documentIdCheckeResponse.jsonPathEvaluator.get("inboundCorrespondence.correspondenceType"), correspondenceType.get());
    }

    @Then("I validate that 400 response is received for outbound correspondence")
    public void validateBadResponse() {
        assertEquals(createCorrespondenceResponse.statusCode, 400);
        assertTrue(createCorrespondenceResponse.responseString.contains("Failed"));
        assertEquals(createCorrespondenceResponse.jsonPathEvaluator.get("status"), "Failed");
        assertEquals(createCorrespondenceResponse.jsonPathEvaluator.get("errorMessage"), "Please upload a file as part of the request");
    }
}
