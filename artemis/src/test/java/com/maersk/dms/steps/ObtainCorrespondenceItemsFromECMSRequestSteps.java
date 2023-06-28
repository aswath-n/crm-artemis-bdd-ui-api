package com.maersk.dms.steps;

import com.google.gson.JsonObject;
import com.maersk.dms.step_definitions.ObtainCorrespondenceItemsFromECMSRequestStepDefs;
import com.maersk.dms.step_definitions.PublishDPBIEventsOutboundCorrespondenceChangesStepDefs;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ObtainCorrespondenceItemsFromECMSRequestSteps {
    final ThreadLocal<ObtainCorrespondenceItemsFromECMSRequestStepDefs> oCIF = ThreadLocal.withInitial(ObtainCorrespondenceItemsFromECMSRequestStepDefs::new);
    final ThreadLocal<PublishDPBIEventsOutboundCorrespondenceChangesStepDefs> pDBPIE = ThreadLocal.withInitial(PublishDPBIEventsOutboundCorrespondenceChangesStepDefs::new);
    private final ThreadLocal<String> accessTokenBLCRMDMS = ThreadLocal.withInitial(()->null);
    private final ThreadLocal<String> searchResponse = ThreadLocal.withInitial(()->null);
    private final ThreadLocal<String> outboundDocumentId = ThreadLocal.withInitial(()->null);
    private final ThreadLocal<String> inboundDocumentId = ThreadLocal.withInitial(()->null);
    private final ThreadLocal<String> defaultDocumentId = ThreadLocal.withInitial(()->null);
    public final ThreadLocal<Map<String, String>> outboundInfo = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal<Boolean> bool = ThreadLocal.withInitial(()->false);
    private final ThreadLocal<Boolean> bool1 = ThreadLocal.withInitial(()->false);
    private final ThreadLocal<ArrayList<String>> credentials = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal<String> getCorrNIDCred = ThreadLocal.withInitial(()->null);


    @Given("I will get the Authentication token for BLCRM in DMS")
    public void i_will_get_the_Authentication_token_for_BLCRM_in_DMS() throws IOException {
        accessTokenBLCRMDMS.set(oCIF.get().getAccessTokenBLCRMDMS());
    }

    @Given("that I have a Correspondence in Onbase")
    public void that_I_have_a_Correspondence_in_Onbase() {
        if (bool.get() == false) {
            defaultDocumentId.set(oCIF.get().defaultCorresOnBase(accessTokenBLCRMDMS.get()));
        }
    }

    @Given("that I have an Inbound Correspondence in Onbase with a key as {string} and value as {string}")
    public void that_I_have_a_Correspondence_in_Onbase_with_a_key_as_and_value_as(String key, String value) {
        if (bool.get() == false) {
            outboundInfo.get().put(key, value.toLowerCase());
            inboundDocumentId.set(oCIF.get().inboundCorrespondenceInOnbase(accessTokenBLCRMDMS.get(), key, value));
        }
    }

    @When("I search for that document with a key as {string} and value as {string} to get credentials")
    public void i_search_for_that_document_with_a_key_as_and_value_as_to_get_credentials(String key, String value) {
        credentials.set(oCIF.get().searchCorrespondenceBFFGetID(accessTokenBLCRMDMS.get(), key, value));
    }

    @When("I search Inbound Correspondence by document Id")
    public void i_search_Inbound_Correspondence_by_document_Id() throws IOException {
        if (!credentials.get().isEmpty()) {
            String[] ids = credentials.get().get(0).split(",");
            String id = ids[0].replace("[", "").replace("]", "");
            int last = ids.length;
            for (int i = 0; i <= ids.length; i++) {
                if (i != last - 1) {
                    Assert.assertFalse(ids[i].trim().replace("[", "").replace("]", "").equals(ids[i + 1].trim().replace("[", "").replace("]", "")));
                } else {
                    break;
                }
            }
            getCorrNIDCred.set(oCIF.get().getCorrespondenceNID(pDBPIE.get().getOAuthQA(), id));
            if (getCorrNIDCred != null) bool.set(true);
        }
    }


    @Given("that I have an Inbound Correspondence in Onbase with a key as {string} and value as {string} 2")
    public void that_I_have_a_Correspondence_in_Onbase_with_a_key_as_and_value_as_2(String key, String value) {
        if (bool1.get() == false) {
            outboundInfo.get().put(key, value.toLowerCase());
            inboundDocumentId.set(oCIF.get().inboundCorrespondenceInOnbase(accessTokenBLCRMDMS.get(), key, value));
        }
    }

    @When("I search for that document with a key as {string} and value as {string}")
    public void i_search_for_that_document_with_a_key_as_and_value_as(String key, String value) {
        if (bool.get() == false) {
            searchResponse.set(oCIF.get().searchCorrespondenceBFF(accessTokenBLCRMDMS.get(), key, value));
            if (searchResponse != null) bool.set(true);
        }
    }

    @Then("I should see {string} and value as {string} have been retrieved")
    public void i_should_see_and_value_as_have_been_retrieved(String key, String value) {
        if (!credentials.get().isEmpty()) {
            String[] ids = credentials.get().get(0).split(",");
            String[] receivedDates = credentials.get().get(1).split(",");
            String[] correspondenceTypes = credentials.get().get(2).split(",");
            String id = ids[0].replace("[", "").replace("]", "");
            String receivedDate = receivedDates[0].replace("[", "").replace("]", "");
            String correspondenceType = correspondenceTypes[0].replace("[", "").replace("]", "");
            Assert.assertTrue(getCorrNIDCred.get().contains("\"id\":" + id));
            Assert.assertTrue(getCorrNIDCred.get().contains("\"receivedDate\":\"" + receivedDate));
            Assert.assertTrue(getCorrNIDCred.get().contains("\"correspondenceType\":\"" + correspondenceType));
            Assert.assertTrue(getCorrNIDCred.get().contains(key));
            Assert.assertTrue(getCorrNIDCred.get().contains(value.toUpperCase()));
        }
    }

    @Given("that I have an Outbound Correspondence in Onbase with a key as {string} and value as {string}")
    public void that_I_have_an_Outbound_Correspondence_in_Onbase_with_a_key_as_and_value_as(String key, String value) {
        if (bool.get() == false) {
            outboundInfo.get().put(key, value.toLowerCase());
            outboundDocumentId.set(oCIF.get().outboundCorrespondenceInOnbase(accessTokenBLCRMDMS.get(), key, value));
        }
    }

    @Given("that I have an Outbound Correspondence in Onbase with a key as {string} and value as {string} 2")
    public void that_I_have_an_Outbound_Correspondence_in_Onbase_with_a_key_as_and_value_as_2(String key, String value) {
        if (bool1.get() == false) {
            outboundInfo.get().put(key, value.toLowerCase());
            outboundDocumentId.set(oCIF.get().outboundCorrespondenceInOnbase(accessTokenBLCRMDMS.get(), key, value));
        }
    }

    @When("I search for that document by {string} and value as {string}")
    public void i_search_for_that_document_by_and_value_as(String key, String value) {
        outboundInfo.get().put(key, value.toLowerCase());
        if (bool.get() == false) {
            searchResponse.set(oCIF.get().searchCorrespondenceBFF(accessTokenBLCRMDMS.get(), key, value));
            if (searchResponse != null) bool.set(true);
        }
    }

    @Then("I should see the documentId and value should match from Outbound Correspondence response")
    public void i_should_see_the_documentId_and_value_should_match_from_Outbound_Correspondence_response() {
        Assert.assertTrue(searchResponse.get().contains("id"));
        Assert.assertTrue(searchResponse.get().contains("receiveddate"));
        Assert.assertTrue(searchResponse.get().contains("type"));
    }

    @Then("I should see the documentId and value should match from Inbound Correspondence response")
    public void i_should_see_the_documentId_and_value_should_match_from_Inbound_Correspondence_response() {
        Assert.assertTrue(searchResponse.get().contains("id"));
        Assert.assertTrue(searchResponse.get().contains("receiveddate"));
        Assert.assertTrue(searchResponse.get().contains("type"));
    }

    @Then("I should see the documentId and value should match from default Inbound Correspondence response")
    public void i_should_see_the_documentId_and_value_should_match_from_default_Inbound_Correspondence_response() {
        Assert.assertTrue(searchResponse.get().contains(defaultDocumentId.get()));
    }

    @Then("I should see the {string} and {string} from default Inbound Correspondence response")
    public void i_should_see_the_and_from_default_Inbound_Correspondence_response(String date1, String date2) {
        Assert.assertTrue(searchResponse.get().contains(date1));
        Assert.assertTrue(searchResponse.get().contains(date2));
    }

    @When("I search for that document by both {string} as {string} and {string} as {string}")
    public void i_search_for_that_document_by_both_as_and_as(String key1, String value1, String key2, String value2) {
        outboundInfo.get().put(key1, value1.toLowerCase());
        outboundInfo.get().put(key2, value2.toLowerCase());
        if (bool.get() == false && bool1.get() == false) {
            searchResponse.set(oCIF.get().searchCorrespondenceBFF(accessTokenBLCRMDMS.get(), key1, value1, key2, value2));
            if (searchResponse != null) {
                bool.set( true);
                bool1.set(true);
            }
        }
    }

    @Then("I should see that documentId and {string} Outbound documents retrieved")
    public void i_should_see_that_documentId_and_Outbound_documents_retrieved(String key) {
        Assert.assertTrue(searchResponse.get().contains("id"));
        Assert.assertTrue(searchResponse.get().contains("receiveddate"));
        Assert.assertTrue(searchResponse.get().contains("type"));
        Assert.assertTrue(searchResponse.get().contains(outboundInfo.get().get(key).toLowerCase()));
    }

    @Then("I should see that documentId and {string} Inbound documents retrieved")
    public void i_should_see_that_documentId_and_Inbound_documents_retrieved(String key) {
        Assert.assertTrue(searchResponse.get().contains("id"));
        Assert.assertTrue(searchResponse.get().contains("receiveddate"));
        Assert.assertTrue(searchResponse.get().contains("type"));
        Assert.assertTrue(searchResponse.get().contains(outboundInfo.get().get(key).toLowerCase()));
    }

    @Then("I should see that {string} and {string} Inbound Correspondence have retrieved")
    public void i_should_see_that_and_Inbound_Correspondence_have_retrieved(String key, String key1) {
        Assert.assertTrue(searchResponse.get().contains(outboundInfo.get().get(key).toLowerCase()));
        Assert.assertTrue(searchResponse.get().contains(outboundInfo.get().get(key1).toLowerCase()));
    }

    @Given("I have an Outbound Correspondence in Onbase with a {string} and a {string}")
    public void i_have_an_Outbound_Correspondence_in_Onbase_with_a_and_a(String key1, String key2) {
        if (bool.get() == false && bool1.get() == false) {
            outboundDocumentId.set(oCIF.get().outboundCorrespondenceInOnbase(accessTokenBLCRMDMS.get(), key1, outboundInfo.get().get(key1), key2, outboundInfo.get().get(key2)));
        }
    }

    @Given("I have an Inbound Correspondence in Onbase with a {string} and a {string}")
    public void i_have_an_Inbound_Correspondence_in_Onbase_with_a_and_a(String key1, String key2) {
        if (bool.get() == false && bool1.get() == false) {
            inboundDocumentId.set(oCIF.get().inboundCorrespondenceInOnbase(accessTokenBLCRMDMS.get(), key1, outboundInfo.get().get(key1), key2, outboundInfo.get().get(key2)));
        }
    }

    @When("I have a over {int} Correspondence in Onbase")
    public void i_have_a_over_Correspondence_in_Onbase(Integer int1) {
        String[] ids = searchResponse.get().split("id");
        Assert.assertTrue(ids.length >= int1);
    }


    @Then("I should see api will return a {string}")
    public void i_should_see_api_will_return_a(String message) {
        Assert.assertTrue(searchResponse.get().contains(message.toLowerCase()), "Response was - " + searchResponse);
    }

    @When("I search for an Inbound Document in the API with the following values")
    public void iSearchForAnInboundDocumentInTheAPIWithTheFollowingValues(Map<String, String> dataTable) {
        JsonObject jsonObject = new JsonObject();
        for (String s : dataTable.keySet()) {
            jsonObject.addProperty(s, dataTable.get(s));
        }
        if (bool.get() == false && bool1.get() == false) {
            searchResponse.set(oCIF.get().searchCorrespondenceBFF(accessTokenBLCRMDMS.get(), jsonObject.toString()));
            if (searchResponse != null) {
                bool.set(true);
                bool1.set(true);
            }
        }
    }

    @When("I search for an Inbound Document in the API with the search criteria values;")
    public void iSearchForAnInboundDocumentInTheAPIWithTheSearchCriteriaValues(Map<String, String> dataTable) {
        JsonObject jsonObject = new JsonObject();
        for (String s : dataTable.keySet()) {
            jsonObject.addProperty(s, dataTable.get(s));
        }
        if (bool.get() == false && bool1.get() == false) {
            searchResponse.set(oCIF.get().searchCorrespondenceBFF(accessTokenBLCRMDMS.get(), jsonObject.toString()));
            if (searchResponse != null) {
                bool.set(true);
                bool1.set(true);
            }
        }
    }
}
