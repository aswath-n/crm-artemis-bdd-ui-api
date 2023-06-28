package com.maersk.crm.api_step_definitions;

import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.restassured.response.ResponseBody;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class APICoreContactRecordDisplayMapping extends CRMUtilities implements ApiBase {

    private String baseMarsCR = ConfigurationReader.getProperty("apiMarContactRecord");
    private final ThreadLocal<String> getContactrecordDisplayFields = ThreadLocal.withInitial(() -> "/app/crm/contactrecord/contactrecordDisplayFields");
    private String apiEnumBase = ConfigurationReader.getProperty("apiEnum");
    private String getEnum = "/mars-cp-web/mars-contact-record-api/app/crm/lookup?tableName=";
    private final ThreadLocal<List<Map<String, Object>>> enumLists = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal<ResponseBody> matchApiResponse = new ThreadLocal<>();
    private final ThreadLocal<List<Map<String, Object>>> displaymapping = ThreadLocal.withInitial(ArrayList::new);


    @And("I initiate and send GET ENUM {string} API")
    public void iInitiateAndSendGETENUMAPI(String data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(apiEnumBase);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getEnum + data);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
    }

    @And("I validate the {string} scope and values have migrated to the contact record display fields mapping for {string}")
    public void iValidateTheScopeAndValuesHaveMigratedToTheContactRecordDisplayFieldsMappingFor(String data, String type) {
        matchApiResponse.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseBody);
        enumLists.set(matchApiResponse.get().jsonPath().getList(data));
        List<List<String>> expectedScopeValue = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : enumLists.get()) {
            List<String> pairedValues = new ArrayList<>();
            try {
                if (stringObjectMap.get("scope") != null && stringObjectMap.get("effectiveEndDate") == null) {
                    pairedValues.add(stringObjectMap.get("scope").toString());
                    if ("DCEB".equals(type)) {
                        pairedValues.add(stringObjectMap.get("value").toString());
                    } else {
                        pairedValues.add(stringObjectMap.get("reportLabel").toString());
                    }
                    pairedValues.add(stringObjectMap.get("value").toString());
                    expectedScopeValue.add(pairedValues);
                }
            } catch (NullPointerException ignored) {
            }
        }
        if (expectedScopeValue.size() == 0) {
            System.out.println("All scoped values were null");
        } else {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseMarsCR);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getContactrecordDisplayFields.get());
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
            displaymapping.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList(""));
            for (int i = 0; i < expectedScopeValue.size(); i++) {
                boolean foundInMaping = false;
                Assert.assertFalse(foundInMaping, "foundInMaping not set to false");
                for (int j = 0; j < displaymapping.get().size(); j++) {
                    try {
                        if (expectedScopeValue.get(i).get(0).equals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("contactRecordIndependentDisplay[" + j + "].selectionValue")) &&
                                expectedScopeValue.get(i).get(1).equals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("contactRecordConditionalDisplay[" + j + "].selectionValue"))
                                || API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("contactRecordConditionalDisplay[" + j + "].selectionValue").toString().contains(expectedScopeValue.get(i).get(1))
                        ) {
                            foundInMaping = true;
                        }
                    } catch (NullPointerException ignored) {
                    }
                }
                Assert.assertTrue(foundInMaping, "ENUM Scope: " + expectedScopeValue.get(i).get(0) + "\nENUM Value: " + expectedScopeValue.get(i).get(1) + " Not Found in Display mapping");
            }
        }
    }
}
