package com.maersk.crm.step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.api_step_definitions.APICaseLoaderEligibilityEnrollmentController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class CRM_ConsumerSearchResult extends CRMUtilities implements ApiBase {

    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    public static final ThreadLocal<String> consumerName = ThreadLocal.withInitial(String::new);
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    CRMDemographicMemberPage demographicMemberPage = new CRMDemographicMemberPage();
    CRMManualCaseConsumerSearchPage manualCaseConsumerSearchPage=new CRMManualCaseConsumerSearchPage();
    //private APITMEventController apitmEventController = new APITMEventController();
    private String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private String createCaseConsumerForEvents = "app/crm/caseloader";

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);

    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private final ThreadLocal<ApiTestDataUtil> apitdu =ThreadLocal.withInitial(ApiTestDataUtil::getApiTestDataUtilThreadLocal);
    private final ThreadLocal<APICaseLoaderEligibilityEnrollmentController> caseLoaderRequest =ThreadLocal.withInitial(APICaseLoaderEligibilityEnrollmentController::new);

    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(() -> newConsumer.get().get("surname").toString());
    private final ThreadLocal<String> firstNameCaseLoader = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> lastNameCaseLoader = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerIdSearched = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> contactType = ThreadLocal.withInitial(() -> "Inbound");
    private final ThreadLocal<String> contactChannelType = ThreadLocal.withInitial(() -> "Phone");
    private final ThreadLocal<String> contactDispositionsValue = ThreadLocal.withInitial(() -> "Complete");
    private final ThreadLocal<String> contactSearchRecordType =ThreadLocal.withInitial(() -> "General");

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMCaseAndConsumerSearchPage consumerRecord = new CRMCaseAndConsumerSearchPage();

    String consumerNameSearch;
    String consumerNameDetailPage;

    @When("I create a case using Case Loader API")
    public void createACaseUsingCaseLoaderAPI() {
        String consumerFirstName = "fn".concat(apitdu.get().getRandomString(5).randomString);
        String consumerLastName = "ln".concat(apitdu.get().getRandomString(5).randomString);
        String consumerSSN = apitdu.get().getRandomNumber(9).randomNumber;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        String consumerDob = dateFormat.format(todayDate);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseConsumerForEvents);
        ////apitmEventController.getAuthenticationToken("BLCRM","CRM");
        requestParams.set(generateCaseLoaderRequestWithSpecifiedParameters("", consumerFirstName, consumerLastName, consumerSSN,
                consumerDob, "", ""));//caseLoaderRequest.generateCaseLoaderRequest();
        System.out.println("Request Params: "+requestParams);

        JsonObject caseLoaderValues = requestParams.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject();
        JsonArray consumerValues = caseLoaderValues.getAsJsonObject("case").getAsJsonArray("consumers");
        firstNameCaseLoader.set(consumerValues.get(0).getAsJsonObject().get("consumerFirstName").getAsString());
        lastNameCaseLoader.set(consumerValues.get(0).getAsJsonObject().get("consumerLastName").getAsString());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @When("I create a consumer for case consumer link check")
    public void createConsumerForConsumerSearch() {
        createNewConsumerForConsumerSearch();
    }

    @And("I search for consumer associated to a Case")
    public void searchForConsumerAssociatedToACase() {
        contactRecord.firstName.sendKeys(firstNameCaseLoader.get());
        contactRecord.lastName.sendKeys(lastNameCaseLoader.get());
        contactRecord.searchButton.click();
    }

    @Then("I see Case ID link is enabled and the Consumer ID link is disabled")
    public void seeCaseIDLinkAndConsumerIDLink() {
        String caseId = consumerRecord.caseIdEnabled.getText();
        String consumerId = consumerRecord.consumerIdDisabled.getText();
        assertNotNull(caseId);
        assertNotNull(consumerId);
        assertTrue(consumerRecord.caseIdEnabled.isDisplayed());
        consumerRecord.caseIdEnabled.click();
        assertFalse(consumerRecord.consumerIdDisabled.isEnabled());


    }

    @And("I search and select consumer associated to a Case")
    public void searchAndSelectConsumerAssociatedToACase() {
        waitFor(2);
        contactRecord.firstName.sendKeys(firstNameCaseLoader.get());
        contactRecord.lastName.sendKeys(lastNameCaseLoader.get());
        contactRecord.searchButton.click();
        consumerNameSearch = contactRecord.searchResultFirstRowFirstName.getText() + ' ' +contactRecord.searchResultMI.getText()+' '+
                contactRecord.searchResultFirstRowLastName.getText();
        System.out.println(consumerNameSearch);
        waitFor(10);
        contactRecord.caseIdFirstRecord.click();
        waitFor(1);
    }

    @And("I search , save and select consumer associated to a Case")
    public void searchSaveAndSelectConsumerAssociatedToACase() {
        waitFor(5);
        contactRecord.caseConsumerCancelButton.click();
        contactRecord.firstName.sendKeys(firstNameCaseLoader.get());
        contactRecord.lastName.sendKeys(lastNameCaseLoader.get());
        contactRecord.searchButton.click();
        consumerNameSearch = contactRecord.searchResultFirstRowFirstName.getText() + ' ' +contactRecord.searchResultMI.getText()+' '+
                contactRecord.searchResultFirstRowLastName.getText();
        System.out.println(consumerNameSearch);
        waitFor(10);
        String[] caseConsumer = new String[2];
        waitFor(10);
        System.out.println(contactRecord.caseIdFirstRowLink.getText());
        System.out.println(contactRecord.consumerIdLnk.getText());
        caseConsumer[0] =contactRecord.caseIdFirstRowLink.getText();
        caseConsumer[1] =contactRecord.consumerIdLnk.getText();
        World.generalSave.get().put("caseConsumerCaseId",caseConsumer[0]);
        World.generalSave.get().put("caseConsumerConsumerId",caseConsumer[1]);
        contactRecord.caseIdFirstRecord.click();
        waitFor(1);
    }

    @Then("I verify consumer and case data for Consumer search")
    public void verifyConsumerAndCaseData() {
        System.out.println(consumerNameSearch);
        consumerNameDetailPage = consumerRecord.consumerName.getText();
        System.out.println(consumerNameDetailPage);
        String[] consumerFirstName = consumerNameDetailPage.split(" ");
        Assert.assertEquals(consumerFirstName[0].trim().toLowerCase(), consumerNameSearch.trim().toLowerCase());
    }

    @Then("I select the Back Arrow on the top left and navigated back to Consumer Search for {string}")
    public void selectTheBackArrowAndNavigateToConsumerSearch(String createType) {
        consumerRecord.backButton.click();
        if (createType.equals("create consumer")) {
            waitForVisibility(demographicMemberPage.continueMemberBtn, 10);
            demographicMemberPage.continueMemberBtn.click();
        }
        assertTrue(contactRecord.searchResultSign.isDisplayed());
        if (createType.equalsIgnoreCase("case loader"))
            assertEquals(contactRecord.searchResultFirstRowFirstName.getText().toLowerCase(), firstNameCaseLoader.get().toLowerCase());
        else if (createType.equalsIgnoreCase("create consumer"))
            assertEquals(contactRecord.searchResultFirstRowFirstName.getText(), firstName.get());
    }

    @And("I search for consumer not associated to a Case")
    public void searchForConsumerNotAssociatedToCase() {
        manualCaseConsumerSearchPage.firstName.sendKeys(firstName.get());
        manualCaseConsumerSearchPage.lastName.sendKeys(lastName.get());
        manualCaseConsumerSearchPage.searchButton.click();
        waitFor(1);
        manualCaseConsumerSearchPage.consumerCancelBtn.click();
        waitFor(1);
        manualCaseConsumerSearchPage.firstName.sendKeys(firstName.get());
        manualCaseConsumerSearchPage.lastName.sendKeys(lastName.get());
        manualCaseConsumerSearchPage.searchButton.click();
        consumerIdSearched.set(contactRecord.consumerIdFirstRecord.getText());
    }

    @Then("I see the Consumer ID link is enabled and NO Case ID link")
    public void seeTheConsumerIDLinkAndNOCaseIDLink() {
        String caseId = consumerRecord.caseIdDisbled.getText();
        String consumerId = consumerRecord.consumerIdEnabled.getText();
        assertEquals(caseId, "--");
        assertFalse(consumerId.isEmpty());
        assertEquals(consumerIdSearched, consumerId);
    }

    @And("I search and select consumer not associated to a Case")
    public void searchAndSelectConsumerNotAssociatedToACase() {
        manualCaseConsumerSearchPage.firstName.sendKeys(firstName.get());
        manualCaseConsumerSearchPage.lastName.sendKeys(lastName.get());
        waitFor(1);
        manualCaseConsumerSearchPage.searchButton.click();
        waitFor(2);
        consumerNameSearch = contactRecord.searchResultFirstRowFirstName.getText() + ' '
                + contactRecord.searchResultMI.getText()+' '+ contactRecord.searchResultFirstRowLastName.getText();
        System.out.println(consumerNameSearch);
        contactRecord.consumerIdFirstRecord.click();
        waitFor(1);
    }

    @Then("I verify consumer for Consumer search")
    public void verifyConsumerForConsumerSearch() {
        System.out.println(consumerNameSearch);
        consumerNameDetailPage = consumerRecord.consumerName.getText();
        System.out.println(consumerNameDetailPage);
        assertTrue(consumerNameDetailPage.equals(consumerNameSearch));
    }

    private void createNewConsumerForConsumerSearch() {
        waitFor(3);
        contactRecord.initContact.click();
        assertTrue(contactRecord.contactInProgressGreenSign.isDisplayed());

        clearAndFillText(contactRecord.firstName, firstName.get());
        clearAndFillText(contactRecord.lastName, lastName.get());
        consumerName.set(firstName.get() + " " + lastName.get());
        System.out.println(firstName.get() + " " + lastName.get());

        waitFor(2);
        assertTrue(contactRecord.searchButton.isDisplayed());
        contactRecord.searchButton.click();
        waitFor(2);

        waitForVisibility(createConsumer.addConsumerButton, 5);
        assertTrue(createConsumer.addConsumerButton.isDisplayed(), "Add Consumer button is not displayed");
        createConsumer.addConsumerButton.click();
        waitFor(3);
        assertTrue(createConsumer.createConsumerButton.isDisplayed(), "Not navigated to Create Consumer page");

        clearAndFillText(createConsumer.consumerFN, firstName.get());
        clearAndFillText(createConsumer.consumerLN, lastName.get());
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        clearAndFillText(createConsumer.consumerDB, getPriorDate(1000));
        selectRandomDropDownOption(createConsumer.consumerGender);
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));

        staticWait(300);
        createConsumer.createConsumerButton.click();
        waitFor(1);
//        waitForVisibility(contactRecord.lblCrmConsumerId, 10);
//        tdm.put("CrmConsumerId", contactRecord.lblCrmConsumerId.getText());
//        consumerId = contactRecord.lblCrmConsumerId.getText();
    }

    private JsonObject generateCaseLoaderRequestWithSpecifiedParameters(String consumerType, String consumerFirstName, String consumerLastName,
                                                                        String consumerSSN, String consumerDateOfBirth, String GenderCode, String relationShip) {
        apitdu.get().getJsonFromFile("crm/caseloader/apiCaseLoader.json");

        String caseIdentificationNo = apitdu.get().getRandomNumber(8).randomNumber;
        System.out.println("caseIdentificationNo " +caseIdentificationNo);
        String correlationId = apitdu.get().getRandomNumber(10).randomNumber;
        String externalConsumerId = "x".concat(apitdu.get().getRandomNumber(6).randomNumber);

        String currentDate = OffsetDateTime.now(ZoneOffset.UTC).toString();

        apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("caseIdentificationNumber", caseIdentificationNo);
        apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("correlationId", correlationId);
        apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerFirstName", consumerFirstName);
        apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerLastName", consumerLastName);
        apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSSN", consumerSSN);
        apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerDateOfBirth", consumerDateOfBirth);
        apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("correlationId", correlationId);
        apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("createdOn", currentDate);
        apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("dateOfSsnValidation", currentDate);
        apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveEndDate", currentDate);
        apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("updatedOn", currentDate);
        apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        apitdu.get().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", externalConsumerId);

        return apitdu.get().jsonElement.getAsJsonObject();
    }
}
