package com.maersk.crm.step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.api_step_definitions.APITMEventController;
import com.maersk.crm.pages.crm.CRMActiveContactPage;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMCreateConsumerProfilePage;
import com.maersk.crm.pages.crm.CRMDemographicContactInfoPage;
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
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CRM_ActiveContactStepDef extends CRMUtilities implements ApiBase {

    private String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private String createCaseConsumer = "app/crm/caseloader";

    private APITMEventController apitmEventController = new APITMEventController();

    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    //private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private ThreadLocal<String> firstNameCaseLoader = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> lastNameCaseLoader = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> consumerFirstName = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> consumerLastName = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> consumerSSN = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> consumerDob = ThreadLocal.withInitial(String::new);
    private String associatedCaseMember;

    Map<String, Object> newConsumer = new HashMap<>(getNewTestData2());
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    CRMDemographicContactInfoPage demographicContactInfoPage = new CRMDemographicContactInfoPage();
    CRMActiveContactPage activeContactPage = new CRMActiveContactPage();

    @When("I create a case for consumer to check profile details")
    public void createACaseUsingCaseLoaderAPIForDemographicContact() {
        consumerFirstName.set("fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerLastName.set("ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        consumerDob.set(dateFormat.format(todayDate));

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseConsumer);
        ////apitmEventController.getAuthenticationToken("BLCRM", "CRM");
        requestParams.set(generateCaseLoaderRequestWithSpecifiedParameters("", consumerFirstName.get(), consumerLastName.get(), consumerSSN.get(),
                consumerDob.get(), "", ""));
        System.out.println("Request Params: " + requestParams);

        JsonObject caseLoaderValues = requestParams.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject();
        JsonArray consumerValues = caseLoaderValues.getAsJsonObject("case").getAsJsonArray("consumers");
        firstNameCaseLoader.set(consumerValues.get(0).getAsJsonObject().get("consumerFirstName").getAsString());
        lastNameCaseLoader.set(consumerValues.get(0).getAsJsonObject().get("consumerLastName").getAsString());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(requestParams);
        System.out.println("Status Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println("Response String: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        waitFor(2);
    }

    @When("I search for consumer to check profile details")
    public void searchForConsumerToCheckProfile() {
        clearAndFillText(contactRecord.firstName, firstNameCaseLoader.get());
        waitFor(2);
        contactRecord.searchButton.click();
        waitForClickablility(contactRecord.expandButtonforLink, 5);
        scrollDown();
        jsClick(contactRecord.expandButtonforLink);
        waitFor(2);
        jsClick(contactRecord.fullNameRadioButton);
        jsClick(contactRecord.DOBradioButton);
        jsClick(contactRecord.consumerIDcheckButton);
        waitFor(3);
        contactRecord.linkRecordButtonOnContactRecordUI.click();
    }

    @Then("I am navigated to the Active Contact screen which has null consumer role")
    public void navigatedToTheActiveContactScreen() {
        waitForVisibility(contactRecord.consumerRole, 10);
        Assert.assertEquals(contactRecord.consumerRole.getText(), "");
    }

    @And("I select the Demographic Info tab on the Consumer profile")
    public void selectTheDemographicInfoTabOnTheConsumerProfile() {
        waitFor(2);
        waitForVisibility(demographicContactInfoPage.demographicInfoTab, 20);
        demographicContactInfoPage.demographicInfoTab.click();
    }

    @Then("I am navigated to the Consumer Profile view of the Demographic Info tab")
    public void navigatedToTheDemographicInfoTab() {
        assertTrue(demographicContactInfoPage.profileDetailsLabel.isDisplayed(), "Profile Label is not displayed");
        assertTrue(demographicContactInfoPage.addressLabel.isDisplayed(), "Address Label is not displayed");
        assertTrue(demographicContactInfoPage.emailLabel.isDisplayed(), "Email Label is not displayed");
        assertTrue(demographicContactInfoPage.phoneNumberSection.isDisplayed(), "Phone Number Label is not displayed");
        waitFor(2);
    }

    @Then("I am navigated to the Consumer Profile view with the list of case members")
    public void navigatedToTheConsumerProfileViewWithTheListOfCaseMembers() {
        assertTrue(demographicContactInfoPage.primaryIndividualsLabel.isDisplayed(), "Primary Individual Label is not displayed");
        assertTrue(demographicContactInfoPage.caseMembersLabel.isDisplayed(), "Case Members Label is not displayed");
        assertTrue(demographicContactInfoPage.authorizedRepLabel.isDisplayed(), "Authorized Representative Label is not displayed");
        scrollUpUsingActions(4);
        waitFor(2);
        demographicContactInfoPage.contactInfoTab.click();
        waitFor(2);
        assertTrue(demographicContactInfoPage.addressLabel.isDisplayed(), "Address Label is not displayed");
        assertTrue(demographicContactInfoPage.emailLabel.isDisplayed(), "Email Label is not displayed");
        assertTrue(demographicContactInfoPage.phoneNumberSection.isDisplayed(), "Phone Number Label is not displayed");
        waitFor(2);
    }

    private JsonObject generateCaseLoaderRequestWithSpecifiedParameters(String consumerType, String consumerFirstName, String consumerLastName,
                                                                        String consumerSSN, String consumerDateOfBirth, String GenderCode, String relationShip) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader.json");

        String caseIdentificationNo = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12).randomNumber;
        System.out.println("caseIdentificationNo " + caseIdentificationNo);
        String correlationId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber;
        String externalConsumerId = "x".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber);

        String currentDate = OffsetDateTime.now(ZoneOffset.UTC).toString();

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("caseIdentificationNumber", caseIdentificationNo);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty(
                        "consumerFirstName", consumerFirstName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty(
                        "consumerLastName", consumerLastName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty(
                        "consumerSSN", consumerSSN);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty(
                        "consumerDateOfBirth", consumerDateOfBirth);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty(
                        "correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty(
                        "createdOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty(
                        "dateOfSsnValidation", currentDate);
//        apitdu.jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
//                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty(
//                "effectiveEndDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty(
                        "effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty(
                        "updatedOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty(
                        "effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray(
                        "consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", externalConsumerId);

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    @Then("I verify the following {string} are visible")
    public void iVerifyTheFollowingAreVisible(String arg0, List<String> tabs) {
        waitForPageToLoad(10);
        for (int i = 0; i < tabs.size(); i++) {
            if (tabs.get(i).equals("CONTACT DETAILS") || tabs.get(i).equals("EDIT HISTORY")) {
                assertEquals(activeContactPage.contactRecInfoTabs.get(i).getText(), tabs.get(i));
            } else {
                assertEquals(activeContactPage.activeContactTabs.get(i).getText(), tabs.get(i));
            }
        }
    }

    @And("I click the Case ID under Links")
    public void iClickTheCaseIDUnderLinks() {
        waitFor(2);
        scrollToElement(activeContactPage.taskLinkCaseID);
        click(activeContactPage.taskLinkCaseID);
    }

    @When("I click the first Case ID under Links")
    public void i_click_the_first_Case_ID_under_Links() {
        waitFor(2);
        scrollToElement(activeContactPage.firstTaskLinkCaseID.get(0));
        waitForPageToLoad(2);
        activeContactPage.firstTaskLinkCaseID.get(1).click();
    }

    @And("I click on case id link under General")
    public void iClickUnderGeneral() {
        waitForPageToLoad(5);
        activeContactPage.caseID.click();
    }

    @And("I see a Translation Service on field displayed as described below")
    public void iSeeATranslationServiceOnFieldDisplayedAsDescribedBelow(List<String> lans) {
        for (String lan : lans) {
            selectDropDown(activeContactPage.translationServiceDropDown, lan);
        }
    }

    @Then("I navigate to {string} tab")
    public void InavigateTab(String tab) {
        activeContactPage.tabs(tab).click();
    }

    @Then("Verify I am able to edit information captured in the Translation Service")
    public void verifyIAmAbleToEditInformationCapturedInTheTranslationService() {
        waitFor(3);
        jsClick(contactRecord.editContactButton);
        selectDropDown(activeContactPage.translationServiceDropDown, "Other");
    }
}
