package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import java.util.Map;

import static org.testng.Assert.*;

public class APICaseCorrespondencePrimaryIndividualController extends CRMUtilities implements ApiBase {

    private String caseConsumerURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String caseCorrespondenceSearchAPI = "app/crm/casecorrespondence";

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private String consumerSearchBaseURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private String eventsBase = ConfigurationReader.getProperty("apiMarsEvent");
    private String getConsumerSearchListByUser = "app/crm/caseloader/consumers";
    private String createCaseWithConsumers = "app/crm/caseloader";
    private String searchByCaseConsumer = "app/crm/case/consumers";
    private String updatePrimaryContact = "app/crm/cases/contacts/primary";
    private String getEvents = "?size=100&page=0&sort=eventId,desc";

    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();

    @Given("I initiated Case Correspondence API for Primary Individual on case")
    public void initiatedCaseCorrespondenceForPrimaryIndividualAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(caseCorrespondenceSearchAPI);
    }

    @When("I run Case Correspondence API for Primary Individual on case")
    public void runCaseCorrespondenceForPrimaryIndividualAPI() {
        requestParams.set(generateCreateCorrespondenceWithCaseIdRequest());
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I will get Primary individual case and consumer level contact information")
    public void checkPrimaryIndividualData() {
        List consumersList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("consumers");

        for (int i = 0; i < consumersList.size(); i++) {
            HashMap consumersData = (HashMap) consumersList.get(i);

            if (!consumersData.get("consumerRole").equals("Primary Individual")) {
                Object channels = consumersData.get("channels");
                System.out.println(consumersData.get("consumerId"));
                System.out.println(channels);

                assertNotNull(((HashMap) channels).get("mail"));
                assertNotNull(((HashMap) channels).get("email"));
                assertNotNull(((HashMap) channels).get("fax"));
                assertNotNull(((HashMap) channels).get("text"));
            }
        }
    }

    public JsonObject generateCreateCorrespondenceWithCaseIdRequest() {
        JsonObject request = new JsonObject();
        JsonArray channelsListArray = new JsonArray();
        String channelsList[] = {"mail", "fax", "email", "text", "phone"};
        Stream.of(channelsList).forEach(channelsListArray::add);

        request.addProperty("caseId", "1");
        request.add("channels", channelsListArray);
        request.addProperty("includeNonDefaultRecipients", true);

        return request;
    }

    public JsonObject generateCreateCorrespondenceWithCaseIdRequest(String caseId) {
        JsonObject request = new JsonObject();
        JsonArray channelsListArray = new JsonArray();
        String channelsList[] = {"mail", "fax", "email", "text", "phone"};
        Stream.of(channelsList).forEach(channelsListArray::add);

        request.addProperty("caseId", caseId);
        request.add("channels", channelsListArray);
        request.addProperty("includeNonDefaultRecipients", true);

        return request;
    }

    public JsonObject generateCreateCorrespondenceWithConsumerIdRequest(String ConsumerId) {
        JsonObject request = new JsonObject();
        JsonArray channelsListArray = new JsonArray();
        String channelsList[] = {"mail", "fax", "email", "text", "phone"};
        Stream.of(channelsList).forEach(channelsListArray::add);

        request.addProperty("consumerId", ConsumerId);
        request.add("channels", channelsListArray);
        request.addProperty("includeNonDefaultRecipients", true);

        return request;
    }

    @Given("I send request for case correspondence API with just created ID")
    public void Isend_request_for_case_correspondence_API(Map<String, String> data) {
        waitFor(5);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(consumerSearchBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchByCaseConsumer);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/Provider/empty.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        System.out.println("Printing json payload" + json);

        for (int i = 0; i < 30; i++) {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithRequestBody(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
            if (API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().asStr("c.status").equalsIgnoreCase("success")) {
                System.out.println("System get call from " + (i + 1) + " attempts");
                break;
            }
        }
        System.out.println("Case ID ================= " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("object.result[0].cases.caseId"));
        String caseId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("object.result[0].cases.caseId").toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(caseCorrespondenceSearchAPI);

        requestParams.set(generateCreateCorrespondenceWithCaseIdRequest(caseId));
        System.out.println(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I validate response channels object contains following data for")
    public void i_validate_response_value_is_and_is(Map<String, String> data) {
        String expectedEmailUnusableReason = data.get("emailUnusableReason");
        String expectedTextUnusableReason = data.get("textUnusableReason");
        String expectedMailUnusableReason = data.get("mailUnusableReason");
        if (data.get("emailUnusableReason").equals("null")) {
            expectedEmailUnusableReason = null;
        }
        if (data.get("textUnusableReason").equals("null")) {
            expectedTextUnusableReason = null;
        }
        if (data.get("mailUnusableReason").equals("null")) {
            expectedMailUnusableReason = null;
        }

        boolean actualMailUsableFlag = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].channels.mail.usability.usableFlag");
        String actualMailUnusableReason = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].channels.mail.usability.unusableReason");
        boolean actualEmailUsableFlag = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].channels.email.usability.usableFlag");
        String actualEmailUnusableReason = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].channels.email.usability.unusableReason");
        boolean actualFaxUsableFlag = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].channels.fax.usability.usableFlag");
        String actualFaxUnusableReason = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].channels.fax.usability.unusableReason");
        boolean actualTextUsableFlag = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].channels.text.usability.usableFlag");
        String actualTextUnusableReason = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].channels.text.usability.unusableReason");

        if (data.get("mailUsableFlag").equals("false")) {
            assertFalse(actualMailUsableFlag, "actualMailUsableFlag is not false, actual actualMailUsableFlag is = " + actualMailUsableFlag);
            assertEquals(actualMailUnusableReason, data.get("mailUnusableReason"), "Unexpected MailUnusableReason. actualMailUnusableReason = " + actualMailUnusableReason);
        } else {
            assertTrue(actualMailUsableFlag, "actualMailUsableFlag is not True, actual actualMailUsableFlag is = " + actualMailUsableFlag);
            assertEquals(actualMailUnusableReason, expectedMailUnusableReason, "Unexpected MailUnusableReason. actualMailUnusableReason = " + actualMailUnusableReason);
        }
        if (data.get("emailUsableFlag").equals("true")) {
            assertTrue(actualEmailUsableFlag, "actualEmailUsableFlag is not true, actual actualEmailUsableFlag is = " + actualEmailUsableFlag);
            assertEquals(actualEmailUnusableReason, expectedEmailUnusableReason, "Unexpected EmailUnusableReason. ActualEmailUnusableReason = " + actualEmailUnusableReason);
        } else {
            assertFalse(actualEmailUsableFlag, "actualEmailUsableFlag is not false, actual actualEmailUsableFlag is = " + actualEmailUsableFlag);
            assertEquals(actualEmailUnusableReason, expectedEmailUnusableReason, "Unexpected EmailUnusableReason. ActualEmailUnusableReason = " + actualEmailUnusableReason);
        }
        assertFalse(actualFaxUsableFlag, "actualFaxUsableFlag is not false, actualMailUsableFlag is = " + actualFaxUsableFlag);
        assertEquals(actualFaxUnusableReason, data.get("faxUnusableReason"), "Unexpected actualFaxUnusableReason. actualFaxUnusableReason = " + actualFaxUnusableReason);

        if (data.get("textUsableFlag").equals("true")) {
            assertTrue(actualTextUsableFlag, "actualTextUsableFlag is not true, actualTextUsableFlag is = " + actualTextUsableFlag);
            assertEquals(actualTextUnusableReason, expectedTextUnusableReason, "Unexpected actualTextUnusableReason. actualFaxUsableFlag = " + actualTextUnusableReason);
        } else {
            assertFalse(actualTextUsableFlag, "actualTextUsableFlag is not false, actualTextUsableFlag is = " + actualTextUsableFlag);
            assertEquals(actualTextUnusableReason, expectedTextUnusableReason, "Unexpected actualTextUnusableReason. actualFaxUsableFlag = " + actualTextUnusableReason);
        }
    }

    @Given("I send a requast to caseCorespondens API with ConsumerID {string}")
    public void i_send_a_requast_to_caseCorespondens_API_with_ConsumerID(String caseId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(caseCorrespondenceSearchAPI);

        requestParams.set(generateCreateCorrespondenceWithConsumerIdRequest(caseId));
        System.out.println(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println("RESPONSE ============== " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.toString());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("Verify response contains following info")
    public void verify_response_contains_following_info(Map<String, String> data) {
        boolean defaultFlag = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].defaultFlag");
        String consumerId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].consumerId");
        String consumerFirstName = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].firstName");
        String consumerLastName = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].lastName");
        String preferredWrittenLanguage = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].preferredWrittenLanguage");
        boolean actualMailUsableFlag = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].channels.mail.usability.usableFlag");
        String actualMailUnusableReason = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].channels.mail.usability.unusableReason");
        assertTrue(defaultFlag, "defaultFlag Not matches");
        assertEquals(consumerId, data.get("consumerId"), "ConsumerId Not matches");
        assertEquals(consumerFirstName, data.get("firstName"), "FirstName Not matches");
        assertEquals(consumerLastName, data.get("lastName"), "consumerLastName Not matches");
        assertEquals(preferredWrittenLanguage, data.get("preferredWrittenLanguage"), "preferredWrittenLanguage Not matches");
        assertFalse(actualMailUsableFlag, "actualMailUsableFlag Not matches");
        assertEquals(actualMailUnusableReason, data.get("mailUnusableReason"), "actualMailUnusableReason Not matches");
    }

    @Then("I get {string} response from server")
    public void Iget404responsfromserver(String code) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("errorResponse.errorCode"), code, "Error code is wrong ");
    }

    @Then("Verify response contains following info when consumer not have mailing address")
    public void response_contains_following_info(Map<String, String> data) {
        boolean actualMailUsableFlag = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].channels.mail.usability.usableFlag");
        String actualMailUnusableReason = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].channels.mail.usability.unusableReason");
        String actualConsumerRole = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].consumerRole");
        if (actualConsumerRole == "null") {
            assertEquals(actualConsumerRole, data.get("consumerRole"), "actualConsumerRole Not matches");
        }
        assertFalse(actualMailUsableFlag, "actualMailUsableFlag Not matches");
        assertEquals(actualMailUnusableReason, data.get("mailUnusableReason"), "actualMailUnusableReason Not matches");

    }

    private JsonObject generateEventRequestForADDRESS_UPDATE_EVENT() {
        JsonObject request = new JsonObject();
        request.addProperty("eventName", "ADDRESS_UPDATE_EVENT");
        return request;
    }

    @And("I will initiate event post API")
    public void initiateEventPostAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventsBase);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEvents);
        requestParams.set(generateEventRequestForADDRESS_UPDATE_EVENT());
        System.out.println(requestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I validate response channels object contains writtenLanguage as {string}")
    public void verify_writtenLanguageAs(String language) {
        String actualWrittenLanguage = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].preferredWrittenLanguage");
        assertEquals(actualWrittenLanguage, language, "language Not matches");
    }

    @Then("I validate response channels object contains following data for if Consumer opted out")
    public void I_validate_response_channels(Map<String, String> data) {
          for(String key : data.keySet()) {
              if(key.endsWith("Reason")) {
                  String actualUnusableReason = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].channels." + key.substring(0, key.indexOf('U')) + ".usability.unusableReason");
                  assertEquals(data.get(key), actualUnusableReason, "actualUnusableReason not match");
              }
          }
            Boolean isAddressesNotNull = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].channels.mail.addresses") != null;
            Boolean isEmailAddressesNotNull = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].channels.email.emailAddresses") != null;
            Boolean isPhoneNumbersNotNull = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody.path("consumers[0].channels.text.phoneNumbers") != null;

            assertTrue(isAddressesNotNull, "addresses is null");
            assertTrue(isEmailAddressesNotNull, "EmailAddresses is null");
            assertTrue(isPhoneNumbersNotNull, "phoneNumbers is null");

    }

}