package com.maersk.dms.step_definitions;

import com.maersk.crm.utilities.ApiTestDataUtil;
import com.maersk.crm.utilities.BrowserUtils;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ObtainCorrespondenceItemsFromECMSRequestStepDefs {

    ApiTestDataUtil apiTestDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();

    public String getAccessTokenBLCRMDMS() throws IOException {
        String token = null;
        Response responseToken = given()
                .contentType("application/json")
                .contentType("application/x-www-form-urlencoded")
                .body("client_id=0GJtlsx4XTpNzAIRIFE82rwkVLqAqcS0" + "&client_secret=IlASqed8mbe7LT35")
                .post("https://api-qa-ext-non-prod.apigee.pcf-maersk.com" + "/mars-ecms-oauth/token" + "?grant_type=client_credentials");
        if (responseToken.getStatusCode() == 200) {
            token = responseToken.jsonPath().get("access_token");
        }
        return token;
    }

    public String defaultCorresOnBase(String accessToken) {
        String response = null;
        Response responseToken = given()
                .header("projectId", "6203")
                .header("Authorization", "Bearer " + accessToken)
                .header("x-b3-traceid", apiTestDataUtil.generate_random_trace_id())
                .header("x-b3-spanid", "2961869c6744d168")
                .contentType("multipart/form-data")
                .multiPart("correspondenceType", "maersk Case + Consumer")
                .multiPart("channel", "Email")
                .multiPart("file", new File("src/test/resources/testData/api/dms/MultiPageDocumenMail.pdf"))
                .put("https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/correspondence/inbound/document");
        if (responseToken.getStatusCode() == 200) {
            response = responseToken.jsonPath().get("documentId");
        }
        return response;
    }

    public String inboundCorrespondenceInOnbase(String accessToken, String key, String value) {
        String response = null;
        Response responseToken = given()
                .header("projectId", "6203")
                .header("Authorization", "Bearer " + accessToken)
                .header("x-b3-traceid", apiTestDataUtil.generate_random_trace_id())
                .header("x-b3-spanid", "2961869c6744d168")
                .contentType("multipart/form-data")
                .multiPart("correspondenceType", "maersk Case + Consumer")
                .multiPart("channel", "Email")
                .multiPart(key, value)
                .multiPart("file", new File("src/test/resources/testData/api/dms/MultiPageDocumenMail.pdf"))
                .put("https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/correspondence/inbound/document");
        if (responseToken.getStatusCode() == 200) {
            response = responseToken.jsonPath().get("documentId");
        }
        return response;
    }

    public String inboundCorrespondenceInOnbase(String accessToken, String key, String value, String key1, String value1) {
        String response = null;
        Response responseToken = given()
                .header("projectId", "6203")
                .header("Authorization", "Bearer " + accessToken)
                .header("x-b3-traceid", apiTestDataUtil.generate_random_trace_id())
                .header("x-b3-spanid", apiTestDataUtil.generate_random_trace_id().substring(16, 32))
                .contentType("multipart/form-data")
                .multiPart("correspondenceType", "maersk Case + Consumer")
                .multiPart("channel", "Email")
                .multiPart(key, value)
                .multiPart(key1, value1)
                .multiPart("file", new File("src/test/resources/testData/api/dms/MultiPageDocumenMail.pdf"))
                .put("https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/correspondence/inbound/document");
        if (responseToken.getStatusCode() == 200) {
            response = responseToken.jsonPath().get("documentId");
        }
        return response;
    }

    public String inboundCorrespondenceInOnbaseForMail(String correspondenceType) throws IOException {
        String response = null;
        Response responseToken = given()
                .header("projectId", "6203")
                .header("Authorization", "Bearer " + getAccessTokenBLCRMDMS())
                .header("x-b3-traceid", apiTestDataUtil.generate_random_trace_id())
                .header("x-b3-spanid", apiTestDataUtil.generate_random_trace_id().substring(16, 32))
                .contentType("multipart/form-data")
                .multiPart("correspondenceType", correspondenceType)
                .multiPart("channel", "Email")
                .multiPart("file", new File("src/test/resources/testData/api/dms/MultiPageDocumenMail.pdf"))
                .put("https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/correspondence/inbound/document");
        if (responseToken.getStatusCode() == 200) {
            response = responseToken.jsonPath().get("documentId");
        }
        return response;
    }

    public String outboundCorrespondenceInOnbase(String accessToken, String key, String value) {
        String response = null;
        Response responseToken = given()
                .header("projectId", "6203")
                .header("Authorization", "Bearer " + accessToken)
                .header("x-b3-traceid", apiTestDataUtil.generate_random_trace_id())
                .header("x-b3-spanid", apiTestDataUtil.generate_random_trace_id().substring(16, 32))
                .contentType("multipart/form-data")
                .multiPart("correspondenceType", "maersk Case + Consumer")
                .multiPart("channel", "Text")
                .multiPart(key, value)
                .multiPart("file", new File("src/test/resources/testData/api/dms/MultiPageDocumenMail.pdf"))
                .put("https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/correspondence/outbound/notification");
        if (responseToken.getStatusCode() == 200) {
            response = responseToken.jsonPath().get("documentId");
        }
        return response;
    }

    public String outboundCorrespondenceInOnbase(String accessToken, String key, String value, String key1, String value1) {
        String response = null;
        Response responseToken = given()
                .header("projectId", "6203")
                .header("Authorization", "Bearer " + accessToken)
                .header("x-b3-traceid", apiTestDataUtil.generate_random_trace_id())
                .header("x-b3-spanid", apiTestDataUtil.generate_random_trace_id().substring(16, 32))
                .contentType("multipart/form-data")
                .multiPart("correspondenceType", "maersk Case + Consumer")
                .multiPart("channel", "Text")
                .multiPart(key, value)
                .multiPart(key1, value1)
                .multiPart("file", new File("src/test/resources/testData/api/dms/MultiPageDocumenMail.pdf"))
                .put("https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/correspondence/outbound/notification");
        if (responseToken.getStatusCode() == 200) {
            response = responseToken.jsonPath().get("documentId");
        }
        return response;
    }

    public String searchCorrespondenceBFF(String accessToken, String key, String value) {
        String rawBody = "{\"" + key + "\": \"" + value + "\"}";
        String responseString = null;
        Response responseBody = given()
                .contentType("application/json")
                .auth().preemptive().oauth2(accessToken)
                .body(rawBody)
                .post("https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/document/search/correspondence");
        if (responseBody.getStatusCode() == 200) {
            responseString = responseBody.asString();
        }
        return responseString.toLowerCase();
    }

    public ArrayList<String> searchCorrespondenceBFFGetID(String accessToken, String key, String value) {
        String rawBody = "{\"" + key + "\": \"" + value + "\"}";
        ArrayList<String> responseString = new ArrayList<>();
        Response responseBody = given()
                .contentType("application/json")
                .header("Project-Id","6203")
                .header("Project-Name","BLCRM")
                .auth().preemptive().oauth2(accessToken)
                .body(rawBody)
                .post("https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/document/search/correspondence");
        if (responseBody.getStatusCode() == 200) {
            responseString.add(BrowserUtils.validNumberFilter(responseBody.jsonPath().get("correspondences.id[0]").toString()));
            responseString.add(responseBody.jsonPath().get("correspondences.receivedDate[0]").toString());
            responseString.add(responseBody.jsonPath().get("correspondences.type[0]").toString());

        }
        return responseString;
    }

    public String getCorrespondenceNID(String accessToken, String NID) {
        String responseString = null;
        Response responseBody = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/x-www-form-urlencoded")
                .accept("application/json")
                .get("https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/correspondence/inbound/document/" + NID);
        if (responseBody.getStatusCode() == 200) {
            responseString = responseBody.asString();
        }
        return responseString;
    }

    public String searchCorrespondenceBFF(String accessToken, String key, String value, String key1, String value1) {
        String rawBody = "{\"" + key + "\": \"" + value + "\"," +
                "\"" + key1 + "\": \"" + value1 + "\"" + "}";
        if ("type".equalsIgnoreCase(key) || "status".equalsIgnoreCase(key) || "channel".equalsIgnoreCase(key1)) {
            rawBody = "{\"" + key + "\": \"" + value + "\"," +
                    "\"" + key1 + "\": [\"" + value1 + "\"]" + "}";
        }
        if ("type".equalsIgnoreCase(key1) || "status".equalsIgnoreCase(key1) || "channel".equalsIgnoreCase(key1)) {
            rawBody = "{\"" + key + "\": \"" + value + "\"," +
                    "\"" + key1 + "\": [\"" + value1 + "\"]" + "}";
        }
        String responseString = null;
        Response responseBody = given()
                .contentType("application/json")
                .header("Project-Id","6203")
                .header("Project-Name","BLCRM")
                .auth().preemptive().oauth2(accessToken)
                .body(rawBody)
                .post("https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/document/search/correspondence");
        if (responseBody.getStatusCode() == 200) {
            responseString = responseBody.asString();
        }
        return responseString.toLowerCase();
    }

    public void updateInboundCorrespondenceInOnbase(String accessToken, String key, String value, String key1, String value1) {
        String rawBody = "{\n" +
                "  \"" + key + "\": " + value + ",\n" +
                "  \"" + key1 + "\": " + value1 + "\n" +
                "}";
        Response responseToken = given()
                .contentType("application/json")
                .header("Project-Id", "44")
                .header("Authorization", "Bearer " + accessToken)
                .header("Project-Name", "BLCRM")
                .header("userId", "James")
                .auth().preemptive().oauth2(accessToken)
                .body(rawBody)
                .post("https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/correspondence/inbound/document");
        Assert.assertTrue(responseToken.getStatusCode() == 200);
    }

    public String searchCorrespondenceBFF(String accessTokenBLCRMDMS, String Request) {
        String responseString = null;
        Response responseBody = given()
                .contentType("application/json")
                .auth().preemptive().oauth2(accessTokenBLCRMDMS)
                .body(Request)
                .post("https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/document/search/correspondence");
        if (responseBody.getStatusCode() == 200) {
            responseString = responseBody.asString();
        }
        return responseString.toLowerCase();
    }
}