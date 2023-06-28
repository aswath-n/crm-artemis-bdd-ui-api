package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import static org.testng.Assert.assertEquals;

public class APICTIRestController  extends CRMUtilities implements ApiBase {

    private String baseURI = ConfigurationReader.getProperty("apiCTIURI");
    private String screenPopResource = "mars/cti/call/pop?apikey=BvRmuodYbL23mAWIwfv4CESavHzOMbuW";

/*    private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();*/

    private final ThreadLocal <JsonObject> ctiScreenPopParams = ThreadLocal.withInitial(JsonObject::new);

    @Given("I initiated cti screen pop API")
    public void initiateCtiScreenPopApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(screenPopResource);
    }

    @When("I can provide cti screen pop information {string}, {string} and {string}")
    public void provideCtiScreenPopInfo(String ssn, String dob, String ciscoAgentId){
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("cti/apiScreenPop.json");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("callData").getAsJsonObject().addProperty("SSN", ssn);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("callData").getAsJsonObject().addProperty("DOB", dob);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("ciscoAgentId", ciscoAgentId);

        ctiScreenPopParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(ctiScreenPopParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(ctiScreenPopParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I verify the response status code and status message")
    public void verifyCtiScreenPopApiResponse(){
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
    }
}
