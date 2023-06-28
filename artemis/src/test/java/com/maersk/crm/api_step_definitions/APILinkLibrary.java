package com.maersk.crm.api_step_definitions;

import com.google.gson.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class APILinkLibrary extends CRMUtilities implements ApiBase {
    /*private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private APIContactRecordController contactRecordController = new APIContactRecordController();*/
    private final ThreadLocal<String>  baseURI = ThreadLocal.withInitial(()->ConfigurationReader.getProperty("apiContactRecordURI"));
    private final ThreadLocal<String> linkEndpoint = ThreadLocal.withInitial(()->"mars/linkmanagement/links/");//+{contactrecordId}
    private final ThreadLocal<String> crID = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> getLinkEndPoint = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);;


    @And("I initate linked contact record API")
    public void i_initate_linked_contact_record_api() throws InterruptedException {
       Thread.sleep(5);
        crID.set(API_THREAD_LOCAL_FACTORY.getContactRecordControllerThreadLocal().apicontactRecordId.toString());
        getLinkEndPoint.set(linkEndpoint.get() +  crID.get() +"?size=10&page=0");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getLinkEndPoint.get());
    }

    @And("I can run linked contact record API")
    public void i_can_run_linked_contact_record_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("API Response:");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I verify the linked contact record API")
    public void i_verify_the_linked_contact_record_api() {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

}