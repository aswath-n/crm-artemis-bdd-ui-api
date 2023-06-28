package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.*;
import com.maersk.dms.utilities.APIAutoUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.response.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.maersk.crm.api_step_definitions.APIATSApplicationController.*;
import static org.testng.Assert.*;

public class APIATSCorrespondenceController extends CRMUtilities implements ApiBase {

    private final String baseApplicationDataUrl = ConfigurationReader.getProperty("apiApplicationDataServices");
    private final String baseNonProdATSUrl = ConfigurationReader.getProperty("apiATSApplicationData");
    private final String baseECMSUrl = ConfigurationReader.getProperty("apiECMSLetterData");
    private final String miOutboundCorrespondence = "/outboundcorrespondence";
    private final String recipients = "/recipients";
    private JsonObject recipientsRequestPayload = getCreateApplicationRequestFromJSONFile("crm/ats/recipients.json");
    private final String outboundCorrespondence="/correspondences";
    public JsonObject outboundCorrespondenceRequestPayload = getCreateApplicationRequestFromJSONFile("crm/ats/OutboundCorrespondence.json");
    public JsonObject miOutboundCorrespondenceRequestPayload = getCreateApplicationRequestFromJSONFile("crm/ats/miOutboundCorrespondence.json");
    private ResponseBody outboundCorrespondenceResponse;

    APIATSApplicationController applicationController = new APIATSApplicationController();
  //  APIAutoUtilities apiAutoUtilities = new APIAutoUtilities();
    final ThreadLocal <APIAutoUtilities> apiAutoUtilities = ThreadLocal.withInitial(APIAutoUtilities::new);
    APIATSSendEventAndCreateLinksController LinksController=new APIATSSendEventAndCreateLinksController();
    BrowserUtils browserUtils=new BrowserUtils();

    private final ThreadLocal <String> appId = applicationIdAPI;
    public static final ThreadLocal <String> createdOutboundCorrespondenceID = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <ResponseBody> response = new ThreadLocal<>();
    private final ThreadLocal <List<Map<String, Object>>> applicationConsumers = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <Map<String, Object>> piApplicationConsumer = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <Map<String, Object>> amApplicationConsumers = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <Map<String, Object>> arApplicationConsumers = ThreadLocal.withInitial(HashMap::new);

    private final ThreadLocal <List<Map<String, Object>>> applicationConsumerAddress = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<Map<String, Object>>> applicationConsumerEmail = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<Map<String, Object>>> applicationConsumerFax = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<Map<String, Object>>> recipientAddress = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<Map<String, Object>>> recipientEmail = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <List<Map<String, Object>>> recipientFax = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <Map<String, Object>> recipientAttributes = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal <ArrayList<ResponseBody>> responseClear = ThreadLocal.withInitial(ArrayList::new);


    private JsonObject getCreateApplicationRequestFromJSONFile(String filePath) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile(filePath);
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    @And("I initiate POST Application recipients with {string} following list of channels and {string} for including non default recipients")
    public void iInitiatePOSTApplicationRecipientsWithFollowingListOfChannelsAndForIncludingNonDefaultRecipients(String appIdType, String includeNon, List<String> channel) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/applications/"+applicationIdAPI.get()+recipients);
        if ("created save API appID".equals(appIdType)) {
            recipientsRequestPayload.addProperty("applicationId", appId.get());
        } else if ("no appId".equals(appIdType)) {
            return;
        } else {
            recipientsRequestPayload.addProperty("applicationId", appIdType);
        }
        recipientsRequestPayload.addProperty("includeNonDefaultRecipients", includeNon);
        for (String each : channel) {
            if ("null".equals(each)) {
                return;
            } else {
                recipientsRequestPayload.getAsJsonArray("channels").add(each);
            }
        }
        System.out.println(recipientsRequestPayload.toString());
    }

    @And("I send POST application data services recipients")
    public void iSendPOSTApplicationDataServicesRecipients() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(recipientsRequestPayload);
    }

    @Then("I verify the {string} status of the POST application recipients api")
    public void iVerifyTheStatusOfThePOSTApplicationRecipientsApi(String statusType) {
        switch (statusType) {
            case "SUCCESS":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Status code is not correct for Save Application API Call");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success", "Wrong status is displayed for recipients API Call");
                break;
            case "No Channel invalid":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Status code is not correct for Save Application API Call");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "invalid", "Wrong status is displayed for recipients API Call");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("errors[0].message"), "Atleast one channel should be passed in the request.", "Wrong error message is displayed for recipients API Call");
                break;
            case "No AppId invalid":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Status code is not correct for Save Application API Call");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "invalid", "Wrong status is displayed for recipients API Call");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("errors[0].message"), "Application id should be present", "Wrong error message is displayed for recipients API Call");
                break;
            case "Non Existing AppId invalid":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Status code is not correct for Save Application API Call");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "invalid", "Wrong status is displayed for recipients API Call");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("errors[0].message"), "Application id is invalid", "Wrong error message is displayed for recipients API Call");
                break;
            case "Unrecognized invalid":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Status code is not correct for Save Application API Call");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "invalid", "Wrong status is displayed for recipients API Call");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("errors[0].message"), "Incoming channels have invalid values. Accepted values are mail,fax,email", "Wrong error message is displayed for recipients API Call");
                break;
            case "Only PI":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Status code is not correct for Save Application API Call");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[0].role"), "Primary Individual", "Mismatch in consumerRoleType");
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.recipients").size()==1,"Mismatch with recipient count");
        }
    }

    @And("I verify {string} received data for recipients api with the following channel data")
    public void iVerifyReceivedDataForRecipientsApiWithTheFollowingChannelData(String consumerType, List<String> channelType) {
        response.set(applicationController.getMemberMatchingResponse().get(0));
        applicationConsumers.set(response.get().jsonPath().getList("data.applicationConsumers"));
        for(int i=0;i<applicationConsumers.get().size();i++){
           String role = applicationConsumers.get().get(i).get("consumerRoleType").toString();
           if(role.equalsIgnoreCase("Primary Individual")) piApplicationConsumer.set(applicationConsumers.get().get(i));
           if(role.equalsIgnoreCase("Application Member")) amApplicationConsumers.set(applicationConsumers.get().get(i));
           if(role.equalsIgnoreCase("Authorized Rep")) arApplicationConsumers.set(applicationConsumers.get().get(i));
        }

        switch (consumerType) {
            case "Primary Individual":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[0].firstName"), piApplicationConsumer.get().get("consumerFirstName"), "Mismatch in expected Primary First Name");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[0].lastName"), piApplicationConsumer.get().get("consumerLastName"), "Mismatch in expected Primary Last Name");
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getBoolean("data.recipients[0].isDefault"), "Expected isDefault value to be true");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[0].role"), piApplicationConsumer.get().get("consumerRoleType"), "Mismatch in consumerRoleType");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.recipients[0].externalRefId"), piApplicationConsumer.get().get("applicationConsumerId"), "Mismatch in applicationConsumerId");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[0].preferredWrittenLanguage"), piApplicationConsumer.get().get("writtenLanguage"), "Mismatch in writtenLanguage");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[0].externalRefType"), "Application Consumer", "Mismatch in externalRefType");

                for (String each : channelType) {
                    switch (each) {
                        case "mail":
                            applicationConsumerAddress.set(response.get().jsonPath().getList("data.applicationConsumers.applicationConsumerAddress"));
                            recipientAddress.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.recipients[0].channels.mail.addresses"));
                            recipientAttributes.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getMap("data.recipients[0].channels.mail.addresses[0].attributes"));
                            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getBoolean("data.recipients[0].channels.mail.isUsable"), "Expected isUsable value to be true");
                            assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[0].channels.mail.unusableReason"), "Expected unusableReason value to be null");
                            assertEquals(recipientAddress.get().get(0).get("addressLine1"), ((HashMap)((ArrayList) piApplicationConsumer.get().get("applicationConsumerAddress")).get(0)).get("addressStreet1"), "Mismatch in mail channel value addressStreet1");
                            assertEquals(recipientAddress.get().get(0).get("addressLine2"), ((HashMap)((ArrayList) piApplicationConsumer.get().get("applicationConsumerAddress")).get(0)).get("addressStreet2"), "Mismatch in mail channel value addressStreet2");
                            assertEquals(recipientAddress.get().get(0).get("addressCity"), ((HashMap)((ArrayList) piApplicationConsumer.get().get("applicationConsumerAddress")).get(0)).get("addressCity"), "Mismatch in mail channel value addressCity");
                            assertEquals(recipientAddress.get().get(0).get("addressStateAbbr"), ((HashMap)((ArrayList) piApplicationConsumer.get().get("applicationConsumerAddress")).get(0)).get("addressState"), "Mismatch in mail channel value addressState");
                            assertEquals(recipientAddress.get().get(0).get("addressZip"), ((HashMap)((ArrayList) piApplicationConsumer.get().get("applicationConsumerAddress")).get(0)).get("addressZip"), "Mismatch in mail channel value addressZip");
                            assertEquals(recipientAddress.get().get(0).get("addressType"), ((HashMap)((ArrayList) piApplicationConsumer.get().get("applicationConsumerAddress")).get(0)).get("addressType"), "Mismatch in mail channel value addressType");
                            assertEquals(recipientAttributes.get().get("ownerType"), "consumer", "Mismatch in mail channel value consumer");
                            assertEquals(recipientAttributes.get().get("isDefault"), true, "Expected isDefault value to be true for mail channel attribute");
                            assertEquals(recipientAttributes.get().get("isPrimary"), true, "Expected isPrimary value to be true for mail channel attribute");
                            break;
                        case "email":
                            applicationConsumerEmail.set(response.get().jsonPath().getList("data.applicationConsumers.applicationConsumerEmail"));
                            recipientEmail.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.recipients[0].channels.email.emailAddresses"));
                            recipientAttributes.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getMap("data.recipients[0].channels.email.emailAddresses[0].attributes"));
                            assertEquals(recipientEmail.get().get(0).get("emailAddress"), ((HashMap)((ArrayList) piApplicationConsumer.get().get("applicationConsumerEmail")).get(0)).get("emailAddress"));
                            assertEquals(recipientAttributes.get().get("ownerType"), "consumer", "Mismatch in email channel value consumer");
                            assertEquals(recipientAttributes.get().get("isDefault"), true, "Expected isDefault value to be true for email channel attribute");
                            assertEquals(recipientAttributes.get().get("isPrimary"), true, "Expected isPrimary value to be true for email channel attribute");
                            break;
                        case "fax":
                            applicationConsumerFax.set(response.get().jsonPath().getList("data.applicationConsumers.applicationConsumerPhone"));
                            recipientFax.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.recipients[0].channels.fax.faxNumbers"));
                            recipientAttributes.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getMap("data.recipients[0].channels.fax.faxNumbers[0].attributes"));
                            assertEquals(recipientFax.get().get(0).get("phoneNumber"), ((HashMap) ((ArrayList) piApplicationConsumer.get().get("applicationConsumerPhone")).get(0)).get("phoneNumber"));
                            assertEquals(recipientAttributes.get().get("ownerType"), "consumer");
                            assertEquals(recipientAttributes.get().get("isDefault"), true);
                            assertEquals(recipientAttributes.get().get("isPrimary"), true);
                            break;
                        default:
                            fail("Unexpected Channel Value");
                    }
                }
                break;
            case "Application Member":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[1].firstName"), amApplicationConsumers.get().get("consumerFirstName"), "Mismatch in expected AM First Name");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[1].lastName"), amApplicationConsumers.get().get("consumerLastName"), "Mismatch in expected AM Last Name");
                assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getBoolean("data.recipients[1].isDefault"), "Expected isDefault value to be false");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[1].role"), amApplicationConsumers.get().get("consumerRoleType"), "Mismatch in consumerRoleType");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.recipients[1].externalRefId"), amApplicationConsumers.get().get("applicationConsumerId"), "Mismatch in applicationConsumerId");
                assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[1].preferredWrittenLanguage"), "Expected null for writtenLanguage");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[1].externalRefType"), "Application Consumer", "Mismatch in externalRefType");

                for (String each : channelType) {
                    switch (each) {
                        case "mail":
                            recipientAddress.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.recipients[1].channels.mail.addresses"));
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[1].channels.mail.unusableReason"), "Consumer opted out","Expected unusable reason mismatch");
                            assertTrue(recipientAddress.get().size()==0, "Address is not null");
                            assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getBoolean("data.recipients[1].channels.mail.isUsable"), "Expected isUsable value to be false");
                            break;
                        case "email":
                            recipientEmail.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.recipients[1].channels.email.emailAddresses"));
                            assertTrue(recipientEmail.get().size()==0,"Email is not null");
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[1].channels.mail.unusableReason"), "Consumer opted out","Expected unusable reason mismatch");
                            assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getBoolean("data.recipients[1].channels.email.isUsable"), "Expected isUsable value to be false");
                            break;
                        case "fax":
                            recipientFax.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.recipients[1].channels.fax.faxNumbers"));
                            assertTrue(recipientFax.get().size()==0,"Fax is not null");
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[1].channels.mail.unusableReason"), "Consumer opted out","Expected unusable reason mismatch");
                            assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getBoolean("data.recipients[1].channels.fax.isUsable"), "Expected isUsable value to be false");
                            break;
                        default:
                            fail("Unexpected Channel Value");
                    }
                }
                break;
            case "Authorized Representative":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[2].firstName"), arApplicationConsumers.get().get("consumerFirstName"), "Mismatch in expected AM First Name");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[2].lastName"), arApplicationConsumers.get().get("consumerLastName"), "Mismatch in expected AM Last Name");
                assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getBoolean("data.recipients[2].isDefault"), "Expected isDefault value to be false");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[2].role"), arApplicationConsumers.get().get("consumerRoleType"), "Mismatch in consumerRoleType");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.recipients[2].externalRefId"), arApplicationConsumers.get().get("applicationConsumerId"), "Mismatch in applicationConsumerId");
                assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[2].preferredWrittenLanguage"), "Expected null for writtenLanguage");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[2].externalRefType"), "Application Consumer", "Mismatch in externalRefType");

                for (String each : channelType) {
                    switch (each) {
                        case "mail":
                            applicationConsumerAddress.set(response.get().jsonPath().getList("data.applicationConsumers.applicationConsumerAddress"));
                            recipientAddress.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.recipients[2].channels.mail.addresses"));
                            recipientAttributes.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getMap("data.recipients[2].channels.mail.addresses[0].attributes"));
                            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getBoolean("data.recipients[2].channels.mail.isUsable"), "Expected isUsable value to be true");
                            assertNull(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[2].channels.mail.unusableReason"), "Expected unusableReason value to be null");
                            assertEquals(recipientAddress.get().get(0).get("addressLine1"), ((HashMap) ((ArrayList) arApplicationConsumers.get().get("applicationConsumerAddress")).get(0)).get("addressStreet1"), "Mismatch in mail channel value addressStreet1");
                            assertEquals(recipientAddress.get().get(0).get("addressLine2"), ((HashMap) ((ArrayList) arApplicationConsumers.get().get("applicationConsumerAddress")).get(0)).get("addressStreet2"), "Mismatch in mail channel value addressStreet2");
                            assertEquals(recipientAddress.get().get(0).get("addressCity"), ((HashMap) ((ArrayList) arApplicationConsumers.get().get("applicationConsumerAddress")).get(0)).get("addressCity"), "Mismatch in mail channel value addressCity");
                            assertEquals(recipientAddress.get().get(0).get("addressStateAbbr"), ((HashMap) ((ArrayList) arApplicationConsumers.get().get("applicationConsumerAddress")).get(0)).get("addressState"), "Mismatch in mail channel value addressState");
                            assertEquals(recipientAddress.get().get(0).get("addressZip"), ((HashMap) ((ArrayList) arApplicationConsumers.get().get("applicationConsumerAddress")).get(0)).get("addressZip"), "Mismatch in mail channel value addressZip");
                            assertEquals(recipientAddress.get().get(0).get("addressType"), ((HashMap) ((ArrayList) arApplicationConsumers.get().get("applicationConsumerAddress")).get(0)).get("addressType"), "Mismatch in mail channel value addressType");
                            assertEquals(recipientAttributes.get().get("ownerType"), "consumer", "Mismatch in mail channel value consumer");
                            assertEquals(recipientAttributes.get().get("isDefault"), true, "Expected isDefault value to be true for mail channel attribute");
                            assertEquals(recipientAttributes.get().get("isPrimary"), true, "Expected isPrimary value to be true for mail channel attribute");
                            break;
                        case "email":
                            recipientEmail.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.recipients[2].channels.email.emailAddresses"));
                            assertTrue(recipientEmail.get().size()==0,"Email is not null");
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[2].channels.email.unusableReason"), "Consumer opted out","Expected unusable reason mismatch");
                            assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getBoolean("data.recipients[2].channels.email.isUsable"), "Expected isUsable value to be false");
                            break;
                        case "fax":
                            recipientFax.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.recipients[2].channels.fax.faxNumbers"));
                            assertTrue(recipientFax.get().size()==0,"Fax is not null");
                            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[2].channels.fax.unusableReason"), "Consumer opted out","Expected unusable reason mismatch");
                            assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getBoolean("data.recipients[2].channels.fax.isUsable"), "Expected isUsable value to be false");
                            break;
                        default:
                            fail("Unexpected Channel Value");
                    }
                }

                break;


            default:
                fail("Unexpected Consumer Value");
        }
    }

    @Then("I verify new Outbound Correspondence is created and stored id")
    public void i_verify_new_Outbound_Correspondence_is_created_and_stored_id() {
        createdOutboundCorrespondenceID.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.id"));
        System.out.println("Created outbound correspondence id: " + createdOutboundCorrespondenceID);
    }

    @Then("I verify newly created Outbound Correspondence is linked to application")
    public void i_verify_newly_created_Outbound_Correspondence_is_linked_to_application() {
        List<Map<String, Object>> linkData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links");
        for (int i = 0; i < linkData.size(); i++) {
            if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links.name").get(i).toString().equalsIgnoreCase("Outbound Correspondence")) {
                System.out.println("External link call response to capture outbound correspondence id: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links.id").get(i).toString());
                assertEquals(createdOutboundCorrespondenceID, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links.id").get(i).toString(), "Created outbound correspondence doesnt match with external link call response");
            }
        }
    }

    @And("I initiated create outbound correspondence api for ATS")
    public void iInitiatedCreateOutboundCorrespondenceApiForAts() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiECMSLetterData"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(outboundCorrespondence);
        }

    @And("I provide application details for POST Create Outbound Correspondence api")
    public void iPOSTATSSaveApplicationApi() {
        outboundCorrespondenceRequestPayload.getAsJsonObject().get("anchor").getAsJsonObject().addProperty("applicationId", APIATSApplicationController.applicationIdAPI.get());
        outboundCorrespondenceRequestPayload.getAsJsonObject().get("anchor").getAsJsonObject().addProperty("projectId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiprojectId);
    }

    @And("I provide application and case details for POST Create Outbound Correspondence api")
    public void iPOSTATSSaveApplicationAndCaseApi() {
        outboundCorrespondenceRequestPayload.getAsJsonObject().get("anchor").getAsJsonObject().addProperty("applicationId", APIATSApplicationController.applicationIdAPI.get());
        outboundCorrespondenceRequestPayload.getAsJsonObject().get("anchor").getAsJsonObject().addProperty("caseId", APIContactRecordController.caseID.get());
        outboundCorrespondenceRequestPayload.getAsJsonObject().remove("correspondenceDefinitionMMSCode");
        outboundCorrespondenceRequestPayload.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode","0");
    }

    @Then("I run POST Outbound Correspondence Call API")
    public ResponseBody I_run_POST_Outbound_Correspondence_Call_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(outboundCorrespondenceRequestPayload,"BLCRM");
        outboundCorrespondenceResponse = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody;
        System.out.println("Request payload for outbound correspondence: " + outboundCorrespondenceRequestPayload);
        System.out.println("Outbound correspondence response: " + outboundCorrespondenceResponse.prettyPrint());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode,201,"Status code isnt verified for create outbound correspondence");
        World.generalSave.get().put("OutboundCorrespondenceResponse",outboundCorrespondenceResponse.jsonPath());
        return outboundCorrespondenceResponse;
    }

    @Then("I provide application consumer details as Recipients for Create Outbound Correspondence api")
    public void i_provide_application_consumer_details_as_Recipients_for_Create_Outbound_Correspondence_api() {
        int consNumber = outboundCorrespondenceRequestPayload.getAsJsonArray("recipients").size();
        if (consNumber == 1) {
            outboundCorrespondenceRequestPayload.getAsJsonArray("recipients").get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("firstName", applicationController.piFirstName.get());
            outboundCorrespondenceRequestPayload.getAsJsonArray("recipients").get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("lastName", applicationController.piLastName.get());
            outboundCorrespondenceRequestPayload.getAsJsonArray("recipients").get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("role", "Primary Individual");
            outboundCorrespondenceRequestPayload.getAsJsonArray("recipients").get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("externalRefId", browserUtils.getKeyFromValue(LinksController.createdConsumerDetails.get(), "Primary Individual"));
        }
        else if(consNumber == 3) {
            outboundCorrespondenceRequestPayload.getAsJsonArray("recipients").get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("firstName", applicationController.piFirstName.get());
            outboundCorrespondenceRequestPayload.getAsJsonArray("recipients").get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("lastName", applicationController.piLastName.get());
            outboundCorrespondenceRequestPayload.getAsJsonArray("recipients").get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("role", "Primary Individual");
            outboundCorrespondenceRequestPayload.getAsJsonArray("recipients").get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("externalRefId", browserUtils.getKeyFromValue(LinksController.createdConsumerDetails.get(), "Primary Individual"));

            outboundCorrespondenceRequestPayload.getAsJsonArray("recipients").get(1).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("firstName", applicationController.amFirstName.get());
            outboundCorrespondenceRequestPayload.getAsJsonArray("recipients").get(1).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("lastName", applicationController.amLastName.get());
            outboundCorrespondenceRequestPayload.getAsJsonArray("recipients").get(1).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("role", "Application Member");
            outboundCorrespondenceRequestPayload.getAsJsonArray("recipients").get(1).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("externalRefId", browserUtils.getKeyFromValue(LinksController.createdConsumerDetails.get(), "Application Member"));

            outboundCorrespondenceRequestPayload.getAsJsonArray("recipients").get(2).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("firstName", applicationController.authRepFirstName.get());
            outboundCorrespondenceRequestPayload.getAsJsonArray("recipients").get(2).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("lastName", applicationController.authRepLastName.get());
            outboundCorrespondenceRequestPayload.getAsJsonArray("recipients").get(2).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("role", "Authorized Rep");
            outboundCorrespondenceRequestPayload.getAsJsonArray("recipients").get(2).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("externalRefId", browserUtils.getKeyFromValue(LinksController.createdConsumerDetails.get(), "Authorized Rep"));
        }
    }

    @And("I initiated create outbound correspondence api for Missing Information")
    public void iInitiatedCreateOutboundCorrespondenceApiForMissingInformation() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseNonProdATSUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(miOutboundCorrespondence);
    }

    @And("I provide application details for POST Create Outbound Correspondence api for Missing Information")
    public void iPOSTATSSaveApplicationApiForMissingInformation() {
        miOutboundCorrespondenceRequestPayload.getAsJsonObject().addProperty("applicationId", APIATSApplicationController.applicationIdAPI.get());
    }

    @Then("I run POST Outbound Correspondence Call API for Missing Information")
    public void iRun_POSTOutboundCorrespondenceCallAPIForMissingInformation() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(miOutboundCorrespondenceRequestPayload);
        System.out.println("Request payload for outbound correspondence: " + miOutboundCorrespondenceRequestPayload);
        System.out.println("Outbound correspondence response: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.prettyPrint());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode,200,"Status code isnt verified for create outbound correspondence");
    }

    @Then("I verify outbound correspondence cannot be created when no missing information on application")
    public void iVerifyOutboundCorrespondenceCannotBeCreatedWhenNoMissingInformationOnApplication() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("errors[0].message"), "There is no Missing Information for the given Application Id with Pending state");
    }

    @And("I initiated GET outbound correspondence api")
    public void iInitiatedGETOutboundCorrespondenceApi(){
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseECMSUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(outboundCorrespondence+"/"+createdOutboundCorrespondenceID);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "wrong status code");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.prettyPrint();
    }

    @Then("I verify outbound correspondence response has expected missing information details")
    public void iVerifyOutboundCorrespondenceResponseHasExpectedMissingInformationDetails(List<Map<String, String>> data){
        Map<String, String> dependentKeysToOverride = data.get(0);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"),"Requested","Status is mismatch");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("typeName"),"BL MI","Type is mismatch");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("anchor").get("applicationId").toString().replace("\"","") +"-"+applicationIdAPI.get());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("anchor").get("applicationId").toString().replace("\"",""),applicationIdAPI.get(),"Application ID is mismatch");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("requester").get("requesterId"), miOutboundCorrespondenceRequestPayload.getAsJsonObject().get("createdBy"),"Requester ID is mismatch");
        String expectedMIDueDate = "";
        if (dependentKeysToOverride.get("applicationType").equalsIgnoreCase("MEDICAL ASSISTANCE")) {
            expectedMIDueDate = getFutureDateFormatYYYYMMdd(38);
        } else if (dependentKeysToOverride.get("applicationType").equalsIgnoreCase("LONG TERM CARE")) {
            expectedMIDueDate = getFutureDateFormatYYYYMMdd(86);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("bodyData").get("dueDate").toString().replace("\"",""),expectedMIDueDate,"Application Due Date is mismatch");
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("bodyData").getAsJsonArray("missingInfos").get(0).getAsJsonObject().get("type").toString().replace("\"",""),dependentKeysToOverride.get("attributeName"),"Type is mismatch");
        if(dependentKeysToOverride.get("dependentType").equalsIgnoreCase("PRIMARY INDIVIDUAL"))
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("bodyData").getAsJsonArray("missingInfos").get(0).getAsJsonObject().get("fromName").toString().replace("\"",""),piFirstName + " " + piLastName,"From Name is mismatch for Application MI");
        else
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("bodyData").getAsJsonArray("missingInfos").get(0).getAsJsonObject().get("fromName").toString().replace("\"",""),amFirstName + " " + amLastName,"From Name is mismatch for Application Consumer MI");
    }

    @Then("I verify that there is no outbound correspondence created")
    public void i_verify_that_there_is_no_outbound_correspondence_created() {
        List<Map<String, Object>> linkData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links");
        if (linkData.size() != 0) {
            for (int i = 0; i < linkData.size(); i++) {
                assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("links.name").get(i).toString().equalsIgnoreCase("Outbound Correspondence"), "Outbound Correspondence is created");
            }
        } else {
            assertTrue(linkData.size() == 0);
        }

    }

    @Then("I verify {string} has following values in the recipients api response for {string} channel")
    public void iVerifyPIHasFollowingValuesInTheRecipientsApiResponseForChannel(String consumerType,String channel, List<Map<String, String>> data) {
        response.set(applicationController.getMemberMatchingResponse().get(0));
        applicationConsumers.set(response.get().jsonPath().getList("data.applicationConsumers"));
        for (int i = 0; i < applicationConsumers.get().size(); i++) {
            String role = applicationConsumers.get().get(i).get("consumerRoleType").toString();
            if (role.equalsIgnoreCase("Primary Individual")) piApplicationConsumer.set(applicationConsumers.get().get(i));
        }
        Map<String,String> attribute = data.get(0);
        switch (consumerType) {
            case "Primary Individual":
                switch (channel) {
                    case "mail":
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[0].channels.mail.isUsable").toString(), attribute.get("isUsable"));
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[0].channels.mail.unusableReason"), attribute.get("unusableReason"));
                        recipientAddress.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.recipients[0].channels.mail.addresses"));
                        if (attribute.get("addresses").isEmpty())
                            assertTrue(recipientAddress.get().size() == 0, "Address is not null");
                        else {
                            assertEquals(recipientAddress.get().get(0).get("addressLine1"), ((HashMap) ((ArrayList) piApplicationConsumer.get().get("applicationConsumerAddress")).get(0)).get("addressStreet1"), "Mismatch in mail channel value addressStreet1");
                            assertEquals(recipientAddress.get().get(0).get("addressLine2"), ((HashMap) ((ArrayList) piApplicationConsumer.get().get("applicationConsumerAddress")).get(0)).get("addressStreet2"), "Mismatch in mail channel value addressStreet2");
                            assertEquals(recipientAddress.get().get(0).get("addressCity"), ((HashMap) ((ArrayList) piApplicationConsumer.get().get("applicationConsumerAddress")).get(0)).get("addressCity"), "Mismatch in mail channel value addressCity");
                            assertEquals(recipientAddress.get().get(0).get("addressStateAbbr"), ((HashMap) ((ArrayList) piApplicationConsumer.get().get("applicationConsumerAddress")).get(0)).get("addressState"), "Mismatch in mail channel value addressState");
                            assertEquals(recipientAddress.get().get(0).get("addressZip"), ((HashMap) ((ArrayList) piApplicationConsumer.get().get("applicationConsumerAddress")).get(0)).get("addressZip"), "Mismatch in mail channel value addressZip");
                            assertEquals(recipientAddress.get().get(0).get("addressType"), ((HashMap) ((ArrayList) piApplicationConsumer.get().get("applicationConsumerAddress")).get(0)).get("addressType"), "Mismatch in mail channel value addressType");
                            recipientAttributes.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getMap("data.recipients[0].channels.mail.addresses[0].attributes"));
                            assertEquals(recipientAttributes.get().get("isDefault").toString(), attribute.get("isDefault"), "Expected isDefault value to be true for mail channel attribute");
                            assertEquals(recipientAttributes.get().get("isPrimary").toString(), attribute.get("isPrimary"), "Expected isPrimary value to be true for mail channel attribute");
                        }
                        break;
                    case "fax":
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[0].channels.fax.isUsable").toString(), attribute.get("isUsable"));
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[0].channels.fax.unusableReason"), attribute.get("unusableReason"));
                        recipientFax.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.recipients[0].channels.fax.faxNumbers"));
                        if (attribute.get("faxNumbers").isEmpty())
                            assertTrue(recipientAddress.get().size() == 0, "Address is not null");
                        else{
                            recipientAttributes.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getMap("data.recipients[0].channels.fax.faxNumbers[0].attributes"));
                            assertEquals(recipientFax.get().get(0).get("phoneNumber"), ((HashMap) ((ArrayList) piApplicationConsumer.get().get("applicationConsumerPhone")).get(0)).get("phoneNumber"));
                            assertEquals(recipientAttributes.get().get("isDefault").toString(), attribute.get("isDefault"));
                            assertEquals(recipientAttributes.get().get("isPrimary").toString(), attribute.get("isPrimary"));
                        }
                        break;
                    case "email":
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[0].channels.email.isUsable").toString(), attribute.get("isUsable"));
                        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[0].channels.email.unusableReason"), attribute.get("unusableReason"));
                        recipientEmail.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.recipients[0].channels.email.emailAddresses"));
                        if (attribute.get("emailAddresses").isEmpty())
                            assertTrue(recipientAddress.get().size() == 0, "Address is not null");
                        else {
                            recipientAttributes.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getMap("data.recipients[0].channels.email.emailAddresses[0].attributes"));
                            assertEquals(recipientEmail.get().get(0).get("emailAddress"), ((HashMap) ((ArrayList) piApplicationConsumer.get().get("applicationConsumerEmail")).get(0)).get("emailAddress"));
                            assertEquals(recipientAttributes.get().get("isDefault").toString(), attribute.get("isDefault"), "Expected isDefault value to be true for email channel attribute");
                            assertEquals(recipientAttributes.get().get("isPrimary").toString(), attribute.get("isPrimary"), "Expected isPrimary value to be true for email channel attribute");
                        }
                        break;
                    default:
                        fail("Channel not found");
                }
                break;
            case "Authorized Representative":
                assertFalse(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getBoolean("data.recipients[1].isDefault"), "Expected isDefault value to be false");
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[1].channels.mail.isUsable").toString(), attribute.get("isUsable"));
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data.recipients[1].channels.mail.unusableReason"), attribute.get("unusableReason"));
                recipientAddress.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data.recipients[1].channels.mail.addresses"));
                if (attribute.get("addresses").isEmpty())
                    assertTrue(recipientAddress.get().size() == 0, "Address is not null");
                break;
            default:
                fail("Consumer Type not found");
        }
    }


}
