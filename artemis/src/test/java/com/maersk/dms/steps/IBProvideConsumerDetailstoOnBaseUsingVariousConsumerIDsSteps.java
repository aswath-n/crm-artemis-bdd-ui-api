package com.maersk.dms.steps;


import com.maersk.crm.utilities.*;
import com.maersk.crm.utilities.data_Module.crm_core_data_module.APIUtilitiesForUIScenarios;
import com.maersk.dms.utilities.APIAutoUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.maersk.crm.utilities.World.generalSave;


public class IBProvideConsumerDetailstoOnBaseUsingVariousConsumerIDsSteps {

    ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private final APIAutoUtilities apiautoutilities =APIAutoUtilities.API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities();
    public static ThreadLocal<String> consumerFullName = ThreadLocal.withInitial(String::new);
    public static ThreadLocal<String> consumerID = ThreadLocal.withInitial(String::new);

    @And("I have a consumer not on a case with {string} as externalConsumerIdType and {string} as externalConsumerIdValue")
    public void ihaveconsumernotoncasewithtypeandvalue(String type, String value) {

        Response response = apiautoutilities.createAConsumerwithExternalType(type, value);
        consumerFullName.set(World.generalSave.get().get("ConsumerFirstName").toString() + " " + World.generalSave.get().get("consumerMiddleName").toString() + " " + World.generalSave.get().get("ConsumerLastName").toString());
        consumerID.set(World.generalSave.get().get("ConsumerId").toString());
        System.out.println("consumer id : " + consumerID.get());
        APIUtilitiesForUIScenarios.consumerFirstName.set(String.valueOf(World.generalSave.get().get("ConsumerFirstName")));
        APIUtilitiesForUIScenarios.consumerLastName.set(String.valueOf(World.generalSave.get().get("ConsumerLastName")));
    }


    @Then("I initiate post request with {string} as type and {string} as value")
    public void ihaverequestwithtypeandvalue(String type, String extconsumerid, Map<String, String> dataTable) {

        if (extconsumerid.equalsIgnoreCase("PREVIOUSLY_CREATED"))
            extconsumerid = World.generalSave.get().get("ExternalConsumerIdValue").toString();

        Response response = apiautoutilities.lookupexternalconsumerid(type, extconsumerid);

       /* String consumerid = response.jsonPath().get("Document.Keywords[0].Value").toString();
        String consumername = response.jsonPath().get("Document.Keywords[1].Value").toString();
        Assert.assertEquals(consumerID, consumerid);
        Assert.assertEquals(consumerFullName, consumername);*/

        HashMap<String, String> map = new HashMap<>();
        List<Object> a = response.jsonPath().getList("Document.Keywords");
        for (int i = 0; i < a.size(); i++) {
            String key = response.jsonPath().get("Document.Keywords[" + i + "].Name").toString();
            String value = response.jsonPath().get("Document.Keywords[" + i + "].Value").toString();
            map.put(key, value);
            System.out.println(response.jsonPath().get("Document.Keywords[" + i + "].Name").toString());
            System.out.println(response.jsonPath().get("Document.Keywords[" + i + "].Value").toString());
        }

        for (String keyword : dataTable.keySet()) {
            switch (keyword) {
                case "CaseID":
                    String caseId;
                    if (dataTable.get("CaseID").equalsIgnoreCase("PREVIOUSLY_CREATED")) {
                        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                        caseId = caseConsumerId.get("caseId");
                    } else
                        caseId = dataTable.get("CaseID");
                    Assert.assertEquals(map.get(keyword), caseId);
                    break;
                case "ConsumerID":
                    String consumerId;
                    if (dataTable.get("ConsumerID").equalsIgnoreCase("PREVIOUSLY_CREATED")) {
                        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                        consumerId = caseConsumerId.get("consumerId");
                    } else
                        consumerId = dataTable.get("ConsumerID");
                    Assert.assertEquals(map.get(keyword), consumerId);
                    break;
                case "ConsumerOnly":
                    Assert.assertEquals(map.get("ConsumerID"), consumerID.get());
                    break;
                case "ConsumerName":
                    if (dataTable.get("ConsumerName").equalsIgnoreCase("PREVIOUSLY_CREATED")) {
                        consumerFullName.set(World.generalSave.get().get("ConsumerFirstName").toString() + " " + World.generalSave.get().get("consumerMiddleName").toString() + " " + World.generalSave.get().get("ConsumerLastName").toString());
                    } else
                        consumerFullName.set(dataTable.get("ConsumerName"));
                    Assert.assertEquals(map.get(keyword), consumerFullName.get());
                    break;
                default:
                    Assert.fail("no matching keyword");
            }
        }

    }


    @Then("I initiate invalid post request with {string} as type, {string} as value and {string} as failure message")
    public void ihaveinvalidrequestwithtypeandvalue(String type, String value, String message) {

        Response response = apiautoutilities.lookupexternalconsumerid(type, value);
        World.generalSave.get().put("FailMessage", response.jsonPath().get("message").toString());
        Assert.assertEquals(World.generalSave.get().get("FailMessage").toString(), message);

    }

    @And("I have a consumer on a case with {string} as externalConsumerIdType and {string} as externalConsumerIdValue")
    public void i_have_a_consumer_on_a_case_with_as_externalConsumerIdType_and_as_externalConsumerIdValue(String type, String value) {
        JsonPath response = apiautoutilities.createAConsumerOnACaseWithExternalType(type, value);
        Map<String, String> caseConsumerId = new HashMap<>();
        caseConsumerId.put("caseId", response.getString("object.result[0].cases.caseId"));
        caseConsumerId.put("consumerId", response.getString("object.result[0].consumers[0].consumerId"));
        World.generalSave.get().put("caseConsumerId", caseConsumerId);
        APIUtilitiesForUIScenarios.consumerFirstName.set(String.valueOf(World.generalSave.get().get("ConsumerFirstName")));
        APIUtilitiesForUIScenarios.consumerLastName.set(String.valueOf(World.generalSave.get().get("ConsumerLastName")));
    }


}
