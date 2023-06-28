package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class APIContactRecordHistoryController extends CRMUtilities implements ApiBase {


    private String contactRecordURI = ConfigurationReader.getProperty("apiContactRecordURI");
    private String createUnidentifiedContactRecord = "app/crm/contactrecord";
    private String getUnidentifiedContactRecordEditHistory = "/app/crm/contactrecordedithistory/{contactRecordId}";


    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> contactRecordId = ThreadLocal.withInitial(String::new);
   /* private APITMEventController apitmEventController = new APITMEventController();
    private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();*/

    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(() -> new HashMap<>(getNewTestData2()));
    private final ThreadLocal<String> phone = ThreadLocal.withInitial(() -> newConsumer.get().get("phone").toString());

    @Given("I create new unidentified contact record for Contact Record Edit using API")
    public void createNewUnidentifiedRecordAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(contactRecordURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createUnidentifiedContactRecord);
        //////apitmEventController.getAuthenticationToken("BLCRM","CRM");
        requestParams.set(generateCreateUnidentifiedContactRecord("Adding Additional Comment"));
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("status"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List contactRecordsList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("contactRecords");
        HashMap contactRecordMap = (HashMap) contactRecordsList.get(0);
        contactRecordId.set(contactRecordMap.get("contactRecordId").toString());
    }

    @When("I edit the unidentified contact Record using API")
    public void editUnidentifiedContactRecordUsingAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(contactRecordURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createUnidentifiedContactRecord);

        requestParams.set(generateCreateUnidentifiedContactRecord("Correcting Additional Comment"));
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("status"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Then("I validate the Edited By Field from edit history")
    public void validateEditHistoryFieldsUsingAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(contactRecordURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getUnidentifiedContactRecordEditHistory.replace("{contactRecordId}", contactRecordId.get()));

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("status"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List editHistoryContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("contactRecordEditHistory.content");

        for (int i = 0; i < editHistoryContent.size(); i++) {
            HashMap editHistoryContentMap = (HashMap) editHistoryContent.get(i);
            String contactRecordEditHistory = editHistoryContentMap.get("contactRecordId").toString();
            String editedByEditHistory = editHistoryContentMap.get("editedBy").toString();

            assertEquals(contactRecordId.get(), contactRecordEditHistory);
            assertEquals(requestParams.get().get("updatedBy").toString(), editedByEditHistory);
            assertEquals(editHistoryContentMap.get("fieldLabel"), "Reason For Edit");
            assertEquals(editHistoryContentMap.get("updatedValue"), "Correcting Additional Comment");
            assertTrue(verifyDateFormat(editHistoryContentMap.get("editedOn").toString()));
        }
    }

    private JsonObject generateCreateUnidentifiedContactRecord(String editReasonType) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/contactrecords/apiCreateUnidentifiedContactRecords.json");

        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
        System.out.println("current date offset" + offsetTime);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordStartTime", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordEndTime", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", RandomStringUtils.random(16, false, true));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_uiid());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordType", "Inbound");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactRecordId", contactRecordId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerType", "Anonymous");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactType", "Unidentified Contact");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactChannelType", "Phone");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerPhone", phone.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactReasonEditType", editReasonType);

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    private boolean verifyDateFormat(String dateField) {
        boolean dateFormatCheck = false;
        String dateFieldFormatted = dateField.substring(1, dateField.length() - 1);
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateFieldFormatted);
            if (date1 != null)
                dateFormatCheck = true;
        } catch (Exception e) {
            System.out.println("Date is not in the expected format");
        }

        return dateFormatCheck;
    }
}
