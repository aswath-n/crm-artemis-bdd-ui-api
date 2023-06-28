package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.APIClassUtil;
import com.maersk.crm.utilities.ApiTestDataUtil;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.dms.utilities.APIAutoUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.ResponseBody;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class APIOutboundCorrespondenceUpdatesController extends CRMUtilities implements ApiBase {

    private String correspondenceURI = ConfigurationReader.getProperty("apiDMSCorrespondence");
    private final ThreadLocal<String> postCorrespondences = ThreadLocal.withInitial(() -> "correspondences");
    private final ThreadLocal<String> endpointCorrespondence = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<APIAutoUtilities> apiAutoUtilities = ThreadLocal.withInitial(APIAutoUtilities::new);
    private final ThreadLocal<ApiTestDataUtil> apitdu = ThreadLocal.withInitial(()->ApiTestDataUtil.getApiTestDataUtilThreadLocal(true));
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal <APIClassUtil> response = new ThreadLocal<>();

    final ThreadLocal<String> empty = ThreadLocal.withInitial(() -> null);

    @Given("I initiated outbound Correspondence API")
    public void initiatedEligibilityCreateAPI() {
        endpointCorrespondence.set(correspondenceURI + "/" + postCorrespondences.get());
    }

    @And("I create outbound correspondence request {string}")
    public void createOutboundCorrespondenceRequest(String type) {
        apitdu.get().getJsonFromFile("dms/apiCreateOutboundCorrespondence.json");

        if (type.contains("without correspondence")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "");
        } else if (type.contains("without active")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "noactive");
        } else if (type.contains("with Inactive")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "TEMP");
        } else if (type.contains("The CorrespondenceDefinitionMMSCode is set for FUTURE")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "Future01");
        } else if (type.contains("without Regarding values")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("anchor", empty.get());
        } else if (type.contains("without Case Id")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "0011c");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonObject("anchor").addProperty("caseId", empty.get());
        } else if (type.contains("without Consumer Id") || type.contains("without Consumer Ids")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "001a");
        } else if (type.contains("with 2 consumer ids")) {
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonObject("anchor").get("regardingConsumerId").getAsJsonArray().add(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomConsumerId().get("consumerId"));
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonObject("anchor").get("regardingConsumerId").getAsJsonArray().add(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomConsumerId().get("consumerId"));
        } else if (type.contains("without distinct Consumer Ids")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "001b");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonObject("anchor").get("regardingConsumerId").getAsJsonArray().add("286");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonObject("anchor").get("regardingConsumerId").getAsJsonArray().add("286");
        } else if (type.contains("with optional preferred Language")) {
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonObject("anchor").get("regardingConsumerId").getAsJsonArray().add(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomConsumerId().get("consumerId"));
            apitdu.get().jsonElement.getAsJsonObject().addProperty("language", empty.get());
        } else if (type.contains("without valid language")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("language", "invalid");
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "CAONLY");
        } else if (type.contains("with jointly addressed consumer ids")) {
            apitdu.get().jsonElement.getAsJsonObject().get("anchor").getAsJsonObject().addProperty("caseId", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId().get("caseId"));
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonObject("anchor").get("regardingConsumerId").getAsJsonArray().add("286");
        } else if (type.contains("without regarding values for no recipient Case or Consumer ID")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonObject("anchor").addProperty("caseId", empty.get());
        } else if (type.contains("without channel for provided consumers ids")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().addProperty("notifications", empty.get());
        } else if (type.contains("which does not have channel mail")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonObject("anchor").get("regardingConsumerId").getAsJsonArray().add(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomConsumerId().get("consumerId"));
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").get(0).getAsJsonObject().addProperty("channelType", "Web Chat");
        } else if (type.contains("with future Channel for Correspondence Type")) {
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonObject("anchor").get("regardingConsumerId").getAsJsonArray().add(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomConsumerId().get("consumerId"));
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").get(0).getAsJsonObject().addProperty("channelType", "Text");
        } else if (type.contains("with more than one instance of the same Channel provided")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");

            JsonObject channel = new JsonObject();
            channel.addProperty("channelType", "Email");

            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").add(channel);
        } else if (type.contains("with destination for the Channel")) {
            apitdu.get().jsonElement.getAsJsonObject().get("anchor").getAsJsonObject().addProperty("caseId", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId().get("caseId"));
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonObject("anchor").get("regardingConsumerId").getAsJsonArray().add(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomConsumerId().get("consumerId"));
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
        } else if (type.contains("without delivery line for Mail Channel")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").get(0).getAsJsonObject().addProperty("channelType", "Mail");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo").addProperty("streetAddress", empty.get());
        } else if (type.contains("without city for Mail Channel")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").get(0).getAsJsonObject().addProperty("channelType", "Mail");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo").addProperty("city", empty.get());
        } else if (type.contains("without state for Mail Channel")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").get(0).getAsJsonObject().addProperty("channelType", "Mail");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo").addProperty("state", empty.get());
        } else if (type.contains("without zip code for Mail Channel")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").get(0).getAsJsonObject().addProperty("channelType", "Mail");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo").addProperty("zipcode", empty.get());
        } else if (type.contains("without valid 2 character postal state")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").get(0).getAsJsonObject().addProperty("channelType", "Mail");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo").addProperty("state", "NYC");
        } else if (type.contains("without valid 5 digit zip code")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonObject("anchor").get("regardingConsumerId").getAsJsonArray().add(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomConsumerId().get("consumerId"));
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").get(0).getAsJsonObject().addProperty("channelType", "Mail");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo").addProperty("zipcode", "201");
        } else if (type.contains("without email address for email channel")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo").addProperty("emailAddress", empty.get());
        } else if (type.contains("without correct email address format")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo").addProperty("emailAddress", "wrong@test");
        } else if (type.contains("without text phone number for text channel")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").get(0).getAsJsonObject().addProperty("channelType", "Text");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo").addProperty("textNumber", empty.get());
        } else if (type.contains("without text phone number is 10 digit number that begins with 2 thru 9")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").get(0).getAsJsonObject().addProperty("channelType", "Text");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo").addProperty("textNumber", "235445");
        } else if (type.contains("without fax phone number for fax channel")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").get(0).getAsJsonObject().addProperty("channelType", "Fax");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo").addProperty("faxNumber", empty.get());
        } else if (type.contains("without fax phone number is 10 digit number that begins with 2 thru 9")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").get(0).getAsJsonObject().addProperty("channelType", "Fax");
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo").addProperty("faxNumber", "235445");
        } else if (type.contains("without Body data")) {
            apitdu.get().jsonElement.getAsJsonObject().get("anchor").getAsJsonObject().addProperty("caseId", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId().get("caseId"));
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonObject("anchor").get("regardingConsumerId").getAsJsonArray().add(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomConsumerId().get("consumerId"));
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
            apitdu.get().jsonElement.getAsJsonObject().addProperty("bodyData", empty.get());
        } else if (type.contains("with structured body data")) {
            apitdu.get().jsonElement.getAsJsonObject().get("anchor").getAsJsonObject().addProperty("caseId", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId().get("caseId"));
            apitdu.get().jsonElement.getAsJsonObject().getAsJsonObject("anchor").get("regardingConsumerId").getAsJsonArray().add(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomConsumerId().get("consumerId"));
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
        } else if (type.contains("without user ID")) {
            apitdu.get().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "00testa1");
            apitdu.get().jsonElement.getAsJsonObject().addProperty("requester", empty.get());
        }

        requestParams.set(apitdu.get().jsonElement.getAsJsonObject());
    }

    @And("I run the outbound Correspondence API")
    public void runOutboundCorrespondenceAPI() {
        System.out.println(requestParams.get());
        response.set(apiAutoUtilities.get().PostCorrespondence(requestParams.get(), endpointCorrespondence.get()));
        System.out.println(response.get().responseString);
    }

    @Then("I validate that response contains error message of {string} is required")
    public void validateOutboundCorrespondenceResponse(String type) {
        List<Map<String, String>> errors = response.get().jsonPathEvaluator.get("errors");

        if (type.contains("empty correspondence")) {
            assertTrue(checkMessage(errors, "CorrespondenceDefinitionMMSCode is required"));
        } else if (type.contains("no active correspondence")) {
            assertTrue(checkMessage(errors, "Unable to fetch the Active CorrespondenceDefinition for the given Project Id and CorrespondenceDefinitionMMSCode"));
        } else if (type.equals("active correspondence type")) {
            assertTrue(checkMessage(errors, "Email Channel is not active for the given Correspondence Definition"));
        } else if (type.contains("The CorrespondenceDefinitionMMSCode is set for FUTURE")) {
            assertTrue(checkMessage(errors, "The CorrespondenceDefinitionMMSCode is set for FUTURE"));
        } else if (type.contains("Regarding value")) {
            assertTrue(checkMessage(errors, "Case ID or Consumer ID is required"));
        } else if (type.contains("Case Id")) {
            assertTrue(checkMessage(errors, "Case ID is required"));
        } else if (type.contains("Consumer Id")) {
            assertTrue(checkMessage(errors, "Consumer ID is required"));
        } else if (type.contains("no Consumer Ids")) {
            assertTrue(checkMessage(errors, "Consumer ID is required"));
        } else if (type.contains("distinct Consumer-Ids")) {
            assertTrue(checkMessage(errors, "Duplicate Consumer IDs are not allowed"));
        } else if (type.contains("valid languag")) {
            assertTrue(checkMessage(errors, "The Language must be a valid language for the project"));
        } else if (type.contains("channel details")) {
            assertTrue(checkMessage(errors, "Recipient and Notification both should be included"));
        } else if (type.contains("Channel association with Correspondence Typ")) {
            assertTrue(checkMessage(errors, "Web Chat Channel is not active for the given Correspondence Definition"));
        } else if (type.contains("Channel active for Correspondence Type")) {
            assertTrue(checkMessage(errors, "Text Channel is not active for the given Correspondence Definition"));
        } else if (type.contains("more than one instance of the same Channel")) {
            assertTrue(checkMessage(errors, "Email: There cannot more than one instance of the same Channel provided"));
        } else if (type.contains("delivery line for Mail Channel") || type.contains("city for Mail Channel") || type.contains("state for Mail Channel") || type.contains("zip code for Mail Channel")) {
            assertTrue(checkMessage(errors, "If the Channel is for the Mail Channel, a delivery line (street, PO Box, etc.), a city, a state, and a zip code must all be provided"));
        } else if (type.contains("valid 2 character postal state")) {
            assertTrue(checkMessage(errors, "If a state is provided it must be a valid 2 digit postal state or territory abbreviation"));
        } else if (type.contains("valid 5 digit zip code")) {
            assertTrue(checkMessage(errors, "If zipcode is provided then this should be in one of the following format: xxxxx, xxxxxxxxx or xxxxx-xxxx"));
        } else if (type.contains("email address for email channel")) {
            assertTrue(checkMessage(errors, "If the Channel is for the Email Channel, an email address must be provided"));
        } else if (type.contains("correct email address format")) {
            assertTrue(checkMessage(errors, "Invalid Email Format"));
        } else if (type.contains("text phone number for text channel")) {
            assertTrue(checkMessage(errors, "If the Channel is for the Text Channel, a text phone number must be provided"));
        } else if (type.contains("text phone number is 10 digit number that begins with 2 thru 9")) {
            assertTrue(checkMessage(errors, "Invalid Text Number"));
        } else if (type.contains("fax phone number for fax channel")) {
            assertTrue(checkMessage(errors, "If the Channel is for the Fax Channel, a fax phone number must be provided"));
        } else if (type.contains("fax phone number is 10 digit number that begins with 2 thru 9")) {
            assertTrue(checkMessage(errors, "Invalid Fax Number"));
        } else if (type.contains("no user ID")) {
            assertTrue(checkMessage(errors, "The user ID submitting the request must be provided"));
        }
    }

    @Then("I validate that response contains success message")
    public void validateOutboundCorrespondenceSuccessMsg() {
        assertTrue(response.get().statusCode == 201 || response.get().statusCode == 200);
        assertEquals(response.get().jsonPathEvaluator.get("data.correspondenceDefinitionMMSCode").toString(), requestParams.get().get("correspondenceDefinitionMMSCode").getAsString());
    }

    private boolean checkMessage(List<Map<String, String>> errors, String message) {
        for (Map<String, String> error : errors) {
            System.out.println("Message outside: " + error.get("message"));
            if (message.equals(error.get("message"))) {
                System.out.println("Message inside: " + error.get("message"));
                return true;
            }
        }

        return false;
    }
}
