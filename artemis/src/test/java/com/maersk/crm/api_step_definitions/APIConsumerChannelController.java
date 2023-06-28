package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.HashMap;

import static org.testng.Assert.*;

public class APIConsumerChannelController  extends CRMUtilities implements ApiBase {

    private String caseCorrespondenceBaseURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String getCaseCorrespondenceListByUser = "app/crm/casecorrespondence";

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);

    @Given("I initiated Case Correspondence API for Consumer Channel")
    public void initiatedCaseCorrespondenceAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseCorrespondenceBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getCaseCorrespondenceListByUser);
    }

    @When("I run Case Correspondence API for Consumer Channel")
    public void runCaseCorrespondenceAPI() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiCaseCorrespondence.json");
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I verify the opt-in channels consumed in the response")
    public void verifyTheOptInChannelsConsumed() {
        ArrayList consumerContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("consumers");
        for(int i=0;i<consumerContent.size();i++) {
            HashMap consumerData = (HashMap) consumerContent.get(i);

            HashMap emailData = (HashMap) ((HashMap) consumerData.get("channels")).get("email");
            HashMap emailUsabilityInfo = (HashMap) emailData.get("usability");

            if (emailUsabilityInfo.get("usableFlag").equals(true))
                assertNull(emailUsabilityInfo.get("unusableReason"));

            HashMap faxData = (HashMap) ((HashMap) consumerData.get("channels")).get("fax");
            HashMap faxUsabilityInfo = (HashMap) faxData.get("usability");

            if (faxUsabilityInfo.get("usableFlag").equals(true))
                assertNull(faxUsabilityInfo.get("unusableReason"));

            HashMap textData = (HashMap) ((HashMap) consumerData.get("channels")).get("text");
            HashMap textUsabilityInfo = (HashMap) textData.get("usability");

            if (textUsabilityInfo.get("usableFlag").equals(true))
                assertNull(textUsabilityInfo.get("unusableReason"));
        }
    }

    @Then("I verify the opt-out channels consumed in the response")
    public void verifyTheOptOutChannelsConsumed() {
        ArrayList consumerContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("consumers");
        for(int i=0;i<consumerContent.size();i++) {
            HashMap consumerData = (HashMap) consumerContent.get(i);

            HashMap emailData = (HashMap) ((HashMap) consumerData.get("channels")).get("email");
            HashMap emailUsabilityInfo = (HashMap) emailData.get("usability");

            if (emailUsabilityInfo.get("usableFlag").equals(false))
                assertEquals(emailUsabilityInfo.get("unusableReason"), "Consumer opted out");

            HashMap faxData = (HashMap) ((HashMap) consumerData.get("channels")).get("fax");
            HashMap faxUsabilityInfo = (HashMap) faxData.get("usability");

            if (faxUsabilityInfo.get("usableFlag").equals(false))
                assertEquals(faxUsabilityInfo.get("unusableReason"), "Consumer opted out");

            HashMap textData = (HashMap) ((HashMap) consumerData.get("channels")).get("text");
            HashMap textUsabilityInfo = (HashMap) textData.get("usability");

            if (textUsabilityInfo.get("usableFlag").equals(false))
                assertEquals(textUsabilityInfo.get("unusableReason"), "Consumer opted out");
        }
    }

    @Then("I verify the channel contact details when channel is NOT usable")
    public void verifyChannelContactDetailsNotUsable() {
        ArrayList consumerContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("consumers");
        for(int i=0;i<consumerContent.size();i++) {
            HashMap consumerData = (HashMap) consumerContent.get(i);

            HashMap emailData = (HashMap) ((HashMap) consumerData.get("channels")).get("email");
            HashMap emailUsabilityInfo = (HashMap) emailData.get("usability");

            if (emailUsabilityInfo.get("usableFlag").equals(false))
                assertNull(emailData.get("emailAddresses"));

            HashMap faxData = (HashMap) ((HashMap) consumerData.get("channels")).get("fax");
            HashMap faxUsabilityInfo = (HashMap) faxData.get("usability");

            if (faxUsabilityInfo.get("usableFlag").equals(false))
                assertNull(faxData.get("phoneNumbers"));

            HashMap textData = (HashMap) ((HashMap) consumerData.get("channels")).get("text");
            HashMap textUsabilityInfo = (HashMap) textData.get("usability");

            if (textUsabilityInfo.get("usableFlag").equals(false))
                assertNull(textData.get("phoneNumbers"));
        }
    }
}
