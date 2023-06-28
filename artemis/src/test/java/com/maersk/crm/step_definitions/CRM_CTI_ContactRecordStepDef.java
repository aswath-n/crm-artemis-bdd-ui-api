package com.maersk.crm.step_definitions;
import com.google.gson.JsonObject;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMDashboardPage;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

import java.util.Map;

public class CRM_CTI_ContactRecordStepDef extends CRMUtilities implements ApiBase {

    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    //private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMDashboardPage dashboardPage = new CRMDashboardPage();
    private final String baseCTICallURI = ConfigurationReader.getProperty("apiCTIURI");
    private final String callCTIEndpoint = "/call/pop";
    private final ThreadLocal<JsonObject> ctiCallPopParams = ThreadLocal.withInitial(JsonObject::new);
    private String randomPhoneNumber;

    @When("I initiated CTI API Call")
    public void i_initiated_CTI_API_Call() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseCTICallURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(callCTIEndpoint);
    }

    @When("I populate CTI Call Information with {string}, {string}, {string},{string}")
    public void i_populate_CTI_Call_Information_with(String contactType, String inboundCallQueue, String contactChannelType, String ani) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("cti/apiCTICall.json");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactType", contactType);
        if (contactType.equalsIgnoreCase("Inbound")){
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("inboundCallQueue", inboundCallQueue);
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("contactChannelType", contactChannelType);
        if (ani.equalsIgnoreCase("random")){
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10);
            randomPhoneNumber = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber;
            System.out.println("phone Number " + randomPhoneNumber);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("ani", randomPhoneNumber);
        } else{
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("ani", ani);
        }


        ctiCallPopParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(ctiCallPopParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(ctiCallPopParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @When("I verify sent above {string}, {string},{string} and {string} are displayed on UI")
    public void i_verify_sent_above_and_are_displayed_on_UI(String contactType, String inboundCallQueue, String contactChannelType, String phoneNumber) {
        Assert.assertEquals(contactRecord.contactType.getText(),contactType);
        if (!inboundCallQueue.isEmpty()){
            Assert.assertEquals(contactRecord.inboundCallQueueDropDownSelection.getText(),inboundCallQueue);
        }
        Assert.assertEquals(contactRecord.contactChannelType.getText(),contactChannelType);
        String randomPhoneNumberFormated = new StringBuilder(randomPhoneNumber).insert(3,'-').insert(7, '-').toString();
        System.out.println("randomPhoneNumber " + randomPhoneNumberFormated);
        System.out.println("ui phonenumber " + contactRecord.phoneNumber.getAttribute("value") );
        Assert.assertEquals(contactRecord.phoneNumber.getAttribute("value"), randomPhoneNumberFormated);
    }

    @When("I verify that Case\\/Consumer search is not performed")
    public void i_verify_that_Case_Consumer_search_is_not_performed() {
        contactRecord.btnAdvancedSearch.click();
       Assert.assertEquals(contactRecord.consumerPhoneNumber.getAttribute("value"), null);

    }

    @Then("I verify that telephony widget is displayed")
    public void verify_telephony_widget_displayed(){
        Assert.assertTrue(dashboardPage.telephonyWidget.isDisplayed(),"Telephony Widget is not displayed");
        Assert.assertTrue(dashboardPage.telephonyWidget.isEnabled(),"Telephony Widget is not enabled");
    }

    @Then("I verify that telephony widget is not displayed")
    public void verify_telephony_widget_not_displayed(){
        try {
            Assert.assertFalse(dashboardPage.telephonyWidget.isDisplayed(),"Telephony widget is displayed");
        } catch (NoSuchElementException e) {
            System.out.println("Telephony widget is not displayed");
        }
    }

    @When("I click on collapse on telephony widget")
    public void collapseCTIPopup(){
        Assert.assertTrue(dashboardPage.expandCollapseCTI.isDisplayed(),"Expand/Collapse telephony Widget button is not displayed");
        dashboardPage.expandCollapseCTI.click();
    }

    @Then("I verify telephony widget is {string}")
    public void verifyCTiExpandCollapse(String position){
        if(position.equals("expanded"))
            Assert.assertTrue(dashboardPage.telephonyWidget.getAttribute("class").contains("show"),"Telephony widget is not expanded");
        if(position.equals("collapsed"))
            Assert.assertTrue(dashboardPage.telephonyWidget.getAttribute("class").contains("fix-right"),"Telephony widget is not collapsed");
    }

    @When("I initiated CTI Call Pop API")
    public void i_initiated_Med_Chat_Create_Contact_Records_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseCTICallURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(callCTIEndpoint);
    }

    @And("User send Api call with name {string} payload {string} for CTI CALL Pop")
    public void userSendApiCallWithNamePayloadForMedChat(String name, String payload, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("cti/" + payload + ".json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        System.out.println("Printing json payload" + json);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutWithNameRequest(name, API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
    }
}