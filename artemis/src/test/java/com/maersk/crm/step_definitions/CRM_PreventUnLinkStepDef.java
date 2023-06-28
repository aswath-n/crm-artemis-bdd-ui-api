package com.maersk.crm.step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.api_step_definitions.APICaseLoaderEligibilityEnrollmentController;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMCreateConsumerProfilePage;
import com.maersk.crm.pages.crm.CRMEditConsumerProfilePage;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class CRM_PreventUnLinkStepDef extends CRMUtilities implements ApiBase {

//    private WebDriver driver;

    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    public static final ThreadLocal<String> consumerName = ThreadLocal.withInitial(String::new);
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    CRMEditConsumerProfilePage editconsumer = new CRMEditConsumerProfilePage();

    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(() -> newConsumer.get().get("surname").toString());
    private final ThreadLocal<String> dob = ThreadLocal.withInitial(() -> "12/01/1991");
    private String consumerId;
//    private String firstNameUpdated = newConsumer.get("name").toString();
//    private String lastNameUpdated = newConsumer.get("surname").toString();
//    private String dobUpdated = "12/09/1993";
//    private String genderUpdated = "Female";
//    private String startDateUpdated = "09/15/2019";
//    private String endDateUpdated = "04/09/2020";

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();

    private String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private String createCaseConsumerForEvents = "app/crm/caseloader";

    private JsonObject requestParams;

    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private APICaseLoaderEligibilityEnrollmentController caseLoaderRequest = new APICaseLoaderEligibilityEnrollmentController();

    private String firstNameCaseLoader;
    private String lastNameCaseLoader;

//    @When("I create a consumer for case consumer profile search")
//    public void editconsumerForConsumerSearch() {
//        createNewConsumerForConsumerSearch();
//    }

    @When("I create a case for consumer using Case Loader API")
    public void createACaseUsingCaseLoaderAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseConsumerForEvents);
        requestParams = caseLoaderRequest.generateCaseLoaderRequest();
        System.out.println("Request Params: "+requestParams);

        JsonObject caseLoaderValues = requestParams.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject();
        JsonArray consumerValues = caseLoaderValues.getAsJsonObject("case").getAsJsonArray("consumers");
        firstNameCaseLoader = consumerValues.get(0).getAsJsonObject().get("consumerFirstName").getAsString();
        lastNameCaseLoader = consumerValues.get(0).getAsJsonObject().get("consumerLastName").getAsString();

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams);
        System.out.println("resp78: "+ API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        waitFor(3);
        contactRecord.initContact.click();
    }

    @And("I searched for consumer with first name and last name")
    public void SearchedForCustomerWithFirstNameAndLastName() {
        clearAndFillText(contactRecord.firstName, firstNameCaseLoader);
        clearAndFillText(contactRecord.lastName, lastNameCaseLoader);
        contactRecord.searchButton.click();
        waitFor(5);
    }

    @And("I link the contact to an Consumer Profile")
    public void linkTheContactToAnConsumerProfile() {
        scrollDownUsingActions(1);
        waitFor(2);
        contactRecord.contactShowMoreInfo.click();
        waitFor(1);
        contactRecord.contactMoreInfoName.click();
        contactRecord.contactMoreInfoDOB.click();
        contactRecord.contactMoreInfoSSN.click();
        waitFor(2);
        waitForVisibility(contactRecord.contactMoreInfoLinkRecord, 5);
        contactRecord.contactMoreInfoLinkRecord.click();
    }

    @And("I create a general task for consumer")
    public void createAGeneralTaskForConsumer() {
        waitFor(2);
        contactRecord.secondaryGlobNav.click();
        waitFor(2);
        hover(contactRecord.createTaskButton);
        waitFor(1);
        contactRecord.generalTaskButton.click();
        clearAndFillText(contactRecord.generalTaskInfo, "New Task");
        editconsumer.saveConsumerUpdate.click();
        waitFor(2);
        contactRecord.endContactButton.click();
    }

    @And("I create a Inbound document for consumer")
    public void createInboundDocumentForConsumer() {
        // Commented since the functionality does not have UI
//        waitFor(2);
//        contactRecord.secondaryGlobNav.click();
//        waitFor(2);
//        hover(contactRecord.createTaskButton);
//        waitFor(1);
//        contactRecord.inboundTaskButton.click();
//        waitFor(2);
//        String userName = contactRecord.userName.getText();
//        selectDropDown(contactRecord.inboundTaskAssignee, userName);
//        waitFor(1);
//        contactRecord.saveConsumerUpdate.click();
//        waitFor(2);
//        contactRecord.endContactButton.click();
    }

    @And("I create a outbound correspondence for consumer")
    public void createOutboundCorrespondenceForConsumer() {
        // Commented since the functionality is not working
//        waitFor(2);
//        contactRecord.secondaryGlobNav.click();
//        waitFor(2);
//        contactRecord.createCorrespondenceButton.click();
//        waitFor(1);
//        selectRandomDropDownOption(contactRecord.correspondenceType);
//        waitFor(1);
//        contactRecord.memberCorrespondence.click();
//        waitForVisibility(contactRecord.memberTextCorrespondence, 10);
//        contactRecord.memberTextCorrespondence.click();
//        selectRandomDropDownOption(contactRecord.textMemberCorrespondence);
//        clearAndFillText(contactRecord.phoneMemberCorrespondence, getRandomNumber(10));
//        waitFor(1);
//        contactRecord.saveConsumerUpdate.click();
//        waitFor(2);
//        contactRecord.endContactButton.click();
    }

    @And("I create a service request for consumer")
    public void createServiceRequestForConsumer() {
        //TO-DO: After Service Request functionality is completed
    }

    @Then("I will no longer see the Unlink button available")
    public void willNoLongerSeeTheUnlinkButtonAvailable() {
        assertTrue(Driver.getDriver().findElements(By.className("MuiButtonBase-root MuiButton-root MuiButton-text mdl-button mdl-js-button mdl-button--primary"))
                .isEmpty());
    }

    @And("I link the contact to an Consumer Profile with CSR Role")
    public void iLinkTheContactToAnConsumerProfileWithCSRRole() {
        scrollDown();
        waitFor(4);
        contactRecord.contactShowMoreInfo.click();
        waitFor(2);
        contactRecord.CSRcontactMoreInfoName.click();
        contactRecord.csrcontactMoreInfoDOB.click();
        contactRecord.csrcontactMoreInfoSSN.click();
        waitFor(2);
        waitForVisibility(contactRecord.CSRcontactMoreInfoLinkRecord, 5);
        contactRecord.CSRcontactMoreInfoLinkRecord.click();
        waitFor(5);
    }

    @Then("I wont see the Unlink button")
    public void i_wont_see_unlink_button() {
        try{
            assertFalse(contactRecord.unLink.isDisplayed());
        }catch (NoSuchElementException e){
            System.out.println("Unlink button is not displayed as expected");
        }
    }
}
