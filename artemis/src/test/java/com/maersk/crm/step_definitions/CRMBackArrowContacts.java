package com.maersk.crm.step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.api_step_definitions.APITMEventController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.ElementClickInterceptedException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class CRMBackArrowContacts extends CRMUtilities implements ApiBase {

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMDemographicContactInfoPage demographicContactInfo = new CRMDemographicContactInfoPage();
    CRMAddContactInfoPage crmAddContactInfoPage = new CRMAddContactInfoPage();
    CRMActiveContactPage crmActiveContactPage = new CRMActiveContactPage();
    CRMDemographicMemberPage demographicMemberPage = new CRMDemographicMemberPage();

    private final String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private final String createCaseConsumer = "app/crm/caseloader";


    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);

    private final ThreadLocal<APITMEventController> apitmEventController = ThreadLocal.withInitial(APITMEventController::new);

    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    //private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();

    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);

    private final ThreadLocal<String> firstNameCaseLoader = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> lastNameCaseLoader = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerFirstName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerLastName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerSSN = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerDob = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> associatedCaseMember = ThreadLocal.withInitial(String::new);

    private final ThreadLocal<String> phoneNum = ThreadLocal.withInitial(() -> newConsumer.get().get("phone").toString());
    private final ThreadLocal<String> phoneType = ThreadLocal.withInitial(() -> "Home");
    private final ThreadLocal<String> emailAddress = ThreadLocal.withInitial(() -> getRandomString(5) + "@" + getRandomString(4) + ".com");
    private final ThreadLocal<String> addressLine1 = ThreadLocal.withInitial(() -> getRandomNumber(3) + " " + getRandomString(5) + " Ct.");
    private final ThreadLocal<String> addressCity = ThreadLocal.withInitial(() -> getRandomString(6) + " City");
    private final ThreadLocal<String> addressCounty = ThreadLocal.withInitial(() -> getRandomString(6) + " County");
    private final ThreadLocal<String> addressZip = ThreadLocal.withInitial(() -> newConsumer.get().get("zip").toString());
    private final ThreadLocal<String> addressType = ThreadLocal.withInitial(() -> "Mailing");

    @When("I create a case for consumer of type {string} using Case Loader API for contacts")
    public void createACaseUsingCaseLoaderAPIForDemographicContact(String consumerRole) {
        consumerFirstName.set("fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerLastName.set("ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        consumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        consumerDob.set(dateFormat.format(todayDate));

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseConsumer);
        ////apitmEventController.getAuthenticationToken("BLCRM","CRM");
        requestParams.set(generateCaseLoaderRequestWithSpecifiedParameters("", consumerFirstName.get(), consumerLastName.get(), consumerSSN.get(), consumerDob.get(), "", "", consumerRole));
        System.out.println("Request Params: " + requestParams.get());

        JsonObject caseLoaderValues = requestParams.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject();
        JsonArray consumerValues = caseLoaderValues.getAsJsonObject("case").getAsJsonArray("consumers");
        firstNameCaseLoader.set(consumerValues.get(0).getAsJsonObject().get("consumerFirstName").getAsString());
        lastNameCaseLoader.set(consumerValues.get(0).getAsJsonObject().get("consumerLastName").getAsString());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println("Status Code: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println("Response String: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        waitFor(2);
    }

    @And("I search consumer with first name and last name for contacts")
    public void searchedForCustomerWithFirstNameAndLastNameForMember() {
        waitFor(3);
        contactRecord.initContact.click();
        System.out.println("First Name of Case Loader:" + firstNameCaseLoader.get());
        System.out.println("Last Name of Case Loader:" + lastNameCaseLoader.get());
        clearAndFillText(contactRecord.firstName, firstNameCaseLoader.get());
        clearAndFillText(contactRecord.lastName, lastNameCaseLoader.get());
        try {
            contactRecord.searchButton.click();
        } catch (ElementClickInterceptedException e) {
            jsClick(contactRecord.searchButton);
        }
        waitFor(5);
    }

    @And("I search consumer from consumer search with first name and last name for contacts")
    public void searchedFromConsumerSearchWithFirstNameAndLastNameForDemographicContact() {
        waitForVisibility(contactRecord.firstName, 10);
        System.out.println("First Name of Case Loader:" + firstNameCaseLoader.get());
        System.out.println("Last Name of Case Loader:" + lastNameCaseLoader.get());
        clearAndFillText(contactRecord.firstName, firstNameCaseLoader.get());
        clearAndFillText(contactRecord.lastName, lastNameCaseLoader.get());
        contactRecord.searchButton.click();
        waitFor(5);
        waitForVisibility(contactRecord.consumerSearchFirstRecord, 10);
        contactRecord.consumerSearchFirstRecord.click();
    }

    @And("I click on demographic tab for contact and click on add for {string}")
    public void clickDemographicContactTab(String type) {
        waitForVisibility(demographicContactInfo.demographicInfoTab, 20);
        demographicContactInfo.demographicInfoTab.click();
        waitForVisibility(demographicContactInfo.contactInfoTab, 20);
        scrollUpUsingActions(6);
        waitFor(1);
        demographicContactInfo.contactInfoTab.click();
        if (type.equals("address")) {
            waitForVisibility(demographicContactInfo.addAddressButton, 10);
            //scrollUpUsingActions(4);
            demographicContactInfo.addAddressButton.click();
        } else if (type.equals("phone")) {
            waitForVisibility(demographicContactInfo.addPhoneButton, 10);
//            scrollToElement(demographicContactInfo.addPhoneButton);
            demographicContactInfo.addPhoneButton.click();
        } else if (type.equals("email")) {
            waitForVisibility(demographicContactInfo.addEmailButton, 10);
            scrollToElement(demographicContactInfo.addEmailButton);
            waitFor(2);
            demographicContactInfo.addEmailButton.click();
        }
    }

    @And("I click on demographic tab and add the {string} for the contacts from {string}")
    public void addAddressForContact(String type, String searchType) {
        String consumerId = null;
        if (searchType.equals("initiate"))
            consumerId = contactRecord.consumerIdInitiateContact.getText();
        else if (searchType.equals("search"))
            consumerId = contactRecord.consumerIdSearch.getText();
        associatedCaseMember.set(firstNameCaseLoader.get().concat("  ").concat(lastNameCaseLoader.get()).concat(" (").concat(contactRecord.consumerIdFirstRecord.getText().concat(")")));
        System.out.println("Associated Case Member: " + associatedCaseMember.get());
        waitForVisibility(demographicContactInfo.demographicInfoTab, 20);
        demographicContactInfo.demographicInfoTab.click();
        waitForVisibility(demographicContactInfo.contactInfoTab, 20);
        scrollUpUsingActions(6);
        waitFor(1);
        demographicContactInfo.contactInfoTab.click();
        if (type.equals("address")) {
            waitForVisibility(demographicContactInfo.addAddressButton, 10);
//            scrollUpUsingActions(4);
            demographicContactInfo.addAddressButton.click();
            waitForVisibility(crmAddContactInfoPage.addressLineOne, 10);
            System.out.println("New address city added: " + addressCity.get());
            selectRandomDropDownOption(crmAddContactInfoPage.addressConsumer);
            crmAddContactInfoPage.addressLineOne.click();
            clearAndFillText(crmAddContactInfoPage.addressLineOne, addressLine1.get());
            clearAndFillText(crmAddContactInfoPage.city, addressCity.get());
            clearAndFillText(crmAddContactInfoPage.county, addressCounty.get());
            selectRandomDropDownOption(crmAddContactInfoPage.state);
            clearAndFillText(crmAddContactInfoPage.zip, addressZip.get());
            selectDropDown(crmAddContactInfoPage.addressType, "Physical");
            clearAndFillText(crmAddContactInfoPage.startDate, getCurrentDate());
            crmAddContactInfoPage.saveButton.click();
            waitFor(2);
            waitForVisibility(demographicContactInfo.addAddressButton, 10);
        } else if (type.equals("phone")) {
            waitForVisibility(demographicContactInfo.addPhoneButton, 10);
//            scrollDownUsingActions(4);
            waitFor(1);
            demographicContactInfo.addPhoneButton.click();
            waitForVisibility(crmAddContactInfoPage.phoneNumber, 10);

            System.out.println("New phone number added: " + phoneNum.get());
            clearAndFillText(crmAddContactInfoPage.phoneNumber, phoneNum.get());
            selectDropDown(crmAddContactInfoPage.phoneType, phoneType.get());
            selectRandomDropDownOption(crmAddContactInfoPage.associatedCaseMember);
            clearAndFillText(crmAddContactInfoPage.startDate, getCurrentDate());
            crmAddContactInfoPage.saveButton.click();
            waitForVisibility(demographicContactInfo.addPhoneButton, 10);
        } else if (type.equals("email")) {
            waitForVisibility(demographicContactInfo.addEmailButton, 10);
            demographicContactInfo.addEmailButton.click();
            waitForVisibility(crmAddContactInfoPage.emailAddressField, 10);

            System.out.println("New email address added: " + emailAddress.get());
            clearAndFillText(crmAddContactInfoPage.emailAddressField, emailAddress.get());
            selectRandomDropDownOption(crmAddContactInfoPage.associatedCaseMember);
            clearAndFillText(crmAddContactInfoPage.startDate, getCurrentDate());
            crmAddContactInfoPage.saveButton.click();
            waitForVisibility(demographicContactInfo.addEmailButton, 10);
        }
    }

    @And("I edit the contact that was added for the contacts")
    public void editContactAddedForContacts() {
        waitFor(2);
        contactRecord.consumerSearchFirstRecord.click();
    }

    @Then("I verify that I see back arrow beneath the consumer profile icon for contact")
    public void verifyBackArrow() {
        waitFor(2);
        assertTrue(demographicMemberPage.memberBackBtn.isDisplayed());
        demographicMemberPage.memberBackBtn.click();
        waitForVisibility(demographicContactInfo.demographicInfoTab, 10);
    }

    private JsonObject generateCaseLoaderRequestWithSpecifiedParameters(String consumerType, String consumerFirstName, String consumerLastName,
                                                                        String consumerSSN, String consumerDateOfBirth, String GenderCode, String relationShip, String consumerRole) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader.json");

        String caseIdentificationNo = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber;
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
                        "consumerRole", consumerRole);
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
//                        "effectiveEndDate", getGreaterDate(3));
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
                        "consumerIdentificationNumber").get(0).getAsJsonObject().addProperty(
                        "externalConsumerId", externalConsumerId);

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }
}
