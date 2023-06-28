package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APIProviderController;
import com.maersk.crm.api_step_definitions.APIProviderSearch;
import com.maersk.crm.pages.crm.CRMDashboardPage;
import com.maersk.crm.pages.crm.CRMProviderDetailsPage;
import com.maersk.crm.pages.crm.CRMProviderSearchPage;
import com.maersk.crm.pages.tm.TMListOfTeamPage;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;


/**
 * Created by KAMIL SHIKHSAFIYEV ON 4/6/2020
 */
public class CRM_View_Provider_Details_StepDef extends CRMUtilities implements ApiBase {


    public static final ThreadLocal<String> projectName = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("projectName"));

    CRMProviderSearchPage providerPage = new CRMProviderSearchPage();
    CRMProviderDetailsPage providerDetail = new CRMProviderDetailsPage();
    CRMDashboardPage dashboard = new CRMDashboardPage();
    APIProviderController apiProviderController = new APIProviderController();
    APIProviderSearch apiProviderSearch = new APIProviderSearch();
    final ThreadLocal<Api_CommonSteps> apiCommonSteps = ThreadLocal.withInitial(Api_CommonSteps::new);
    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    //public ApiTestDataUtil apiTestDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();

    public final ThreadLocal<String> userNameData = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("login"));
    public final ThreadLocal<String> password = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("password"));


    @Given("I navigate to Provider Search page")
    public void i_navigate_to_Provider_Search_page() {
        click(providerPage.providerSearch);
    }

    @And("I choosing {string} plan from Plan Name dropdown")
    public void iChoosingPlanName(String planName) throws InterruptedException {
        staticWait(3000);
        providerPage.selectDropDown(providerPage.planName, planName);
        providerPage.searchButton.click();
    }

    @When("I have selected to view the provider detail screen")
    public void iHaveSelectedToViewTheProviderDetailScreen() {
        staticWait(3000);
        staticWait(5000);
        waitFor(5);
        highLightElement(providerPage.providerTable);
        click(providerPage.providerTable);
        waitForPageToLoad(10);
        Assert.assertTrue(providerPage.firstNameProviderDetails.isDisplayed(), "Provider first name are not displayed");
    }

    @Then("I verify in page PROVIDER DETAILS fields are displayed")
    public void iVerifyInPagePROVIDERDETAILSFieldsAreDisplayed() {
        Assert.assertTrue(providerPage.groupName.isDisplayed());
        Assert.assertTrue(providerPage.providerGenderValue.isDisplayed());
        Assert.assertTrue(providerPage.genderServed.isDisplayed());
        Assert.assertTrue(providerPage.providerType.isDisplayed());
        Assert.assertTrue(providerPage.pcpIndicator.isDisplayed());
        Assert.assertTrue(providerPage.obIndicator.isDisplayed());
        // Assert.assertEquals(providerPage.patientMinimumAge.getText(), "0");
        //  Assert.assertEquals(providerPage.patientMaxAge.getText(), "18");
        Assert.assertTrue(providerPage.patientAgeRange.isDisplayed());
        Assert.assertTrue(providerPage.npi.isDisplayed());
        Assert.assertTrue(providerPage.count(providerPage.languages) <= 7);
        Assert.assertEquals(providerPage.primaryLang.getText(), "star");
        Assert.assertTrue(providerPage.count(providerPage.specialities) <= 10);
        Assert.assertEquals(providerPage.primarySpec.getText(), "star");
    }

    @Then("Verify languages section where all languages up to {int} are displayed")
    public void verifyLanguagesSectionWhereAllLanguagesUpToAreDisplayed(int size) {
        Assert.assertTrue(providerPage.count(providerPage.languages) <= size);
    }

    @And("Verify star for the provider’s primary language next to the primary language first option")
    public void verifyStarForTheProviderSPrimaryLanguageNextToThePrimaryLanguageLanguage() {
        Assert.assertEquals(providerPage.primaryLang.getText(), "star");
    }

    @And("Verify alternate text for disability accessibility indicating the primary language")
    public void verifyAlternateTextForDisabilityAccessibilityIndicatingThePrimaryLanguage() {
        hover(providerPage.primaryLangHover);
    }

    @Then("Verify section for Specialties where all specialties up to {int} are displayed")
    public void verifySectionForSpecialtiesWhereAllSpecialtiesUpToAreDisplayed(int size) {
        Assert.assertTrue(providerPage.count(providerPage.specialities) <= size);
    }

    @And("Verify a star for the provider’s primary specialty next to the primary specialty for first Specialty")
    public void verifyAStarForTheProviderSPrimarySpecialtyNextToThePrimarySpecialtyForFirstSpecialty() {
        Assert.assertEquals(providerPage.primarySpec.getText(), "star");
    }

    @Then("Verify alternate text for disability accessibility indicating the primary specialty")
    public void verifyAlternateTextForDisabilityAccessibilityIndicatingThePrimarySpecialty() {
        hover(providerPage.primarySpecHover);
    }

    @Then("Verify Location affiliated details are displayed in page")
    public void verifyLocationAffiliatedDetailsAreDisplayInPage() {
        Assert.assertTrue(providerDetail.planNameForProvider.isDisplayed());
        Assert.assertTrue(providerDetail.providerAddress.isDisplayed());
        Assert.assertTrue(providerDetail.phoneNumber.isDisplayed());
        Assert.assertTrue(providerDetail.emailAddress.isDisplayed());
        Assert.assertTrue(providerDetail.newPatients.isDisplayed());
        Assert.assertTrue(providerDetail.handicap.isDisplayed());
        Assert.assertTrue(providerDetail.officeHours.isDisplayed());
    }

    @And("find record for new patient by npi {string} number and event name as {string}")
    public void findRecordForNewPatientByNpiNumber(String npi, String eventName) {
        staticWait(3000);
        if (npi.isEmpty() || npi == null) {
            String asb = apiCommonSteps.get().asStr(eventName + ".providers[0].npi");
            System.out.println("Printing NPI" + asb);
            providerPage.providerNpi.sendKeys(asb);
        } else {
            providerPage.providerNpi.sendKeys(npi);
        }
        staticWait(3000);
        highLightElement(providerPage.searchButtonAfterAdvance);
        click(providerPage.searchButtonAfterAdvance);
    }


    @And("find record for new patient by npi {string} number_1")
    public void findRecordForNewPatientByNpiNumber_1(String npi) {
        staticWait(3000);
        if (npi.isEmpty() || npi == null) {
            String asb = apiCommonSteps.get().asStr("ps1.providers[0].npi");
            System.out.println("i m here new NPI" + asb);
            providerPage.providerNpi.sendKeys(asb);
            //providerPage.providerNpi.sendKeys(apiProviderSearch.getNPI);
            highLightElement(providerPage.searchButtonAfterAdvance);
            click(providerPage.searchButtonAfterAdvance);
            System.out.println("i m here1");
        } else {
            providerPage.providerNpi.sendKeys(npi);
        }
        staticWait(3000);
        highLightElement(providerPage.searchButtonAfterAdvance);
        click(providerPage.searchButtonAfterAdvance);
    }


    @When("I view the icons for Accepting New Patients")
    public void iViewTheIconsForAcceptingNewPatients() {
        Assert.assertTrue(providerDetail.newPatientIcon.isDisplayed());
    }

    @When("I view the icons for Handicap Accessible")
    public void iViewTheIconsForHandicapAccessible() {
        Assert.assertTrue(providerDetail.handicapIcon.isDisplayed());
    }

    @Then("Verify I view Office Hours column")
    public void verifyIViewOfficeHoursColumn() {
        Assert.assertTrue(providerDetail.mon.isDisplayed());
        Assert.assertTrue(providerDetail.tue.isDisplayed());
        Assert.assertTrue(providerDetail.wed.isDisplayed());
        Assert.assertTrue(providerDetail.thur.isDisplayed());
        Assert.assertTrue(providerDetail.fri.isDisplayed());
        Assert.assertTrue(providerDetail.sat.isDisplayed());
        Assert.assertTrue(providerDetail.sun.isDisplayed());
    }

    @When("I hover my mouse over each icon, the Office Hours will display for that day of the week")
    public void iHoverMyMouseOverEachIconTheOfficeHoursWillDisplayForThatDayOfTheWeek() {
        hover(providerDetail.mon);
        hover(providerDetail.tue);
        hover(providerDetail.wed);
        hover(providerDetail.thur);
        hover(providerDetail.fri);
        hover(providerDetail.sat);
        hover(providerDetail.sun);

    }

    @And("Verify Hex color {string} icon for each day of the week that does not have office hours")
    public void verifyRedIconForEachDayOfTheWeekThatDoesNotHaveOfficeHours(String color) {
        verifyColorOfElement(providerDetail.sat, color);
        verifyColorOfElement(providerDetail.sun, color);
    }

    @Then("verify Hex color is green {string} icon person accepting new patients")
    public void verifyHexColorIsGreenIconPersonAcceptingNewPatients(String color) {
        verifyColorOfElement(providerDetail.newPatientIcon, color);
    }

    @Then("verify Hex color is red {string} icon person accepting patients")
    public void verifyHexColorIsRedIconPersonAcceptingNewPatients(String color) {
        verifyColorOfElement(providerDetail.redIconForPatient, color);
    }

    @Then("verify Hex color is green {string} icon Handicap Accessible")
    public void verifyHexColorIsGreenIconHandicapAccessible(String color) {
        verifyColorOfElement(providerDetail.handicapIcon, color);
    }

    @Then("verify Hex color is red {string} icon Handicap Accessible")
    public void verifyHexColorIsRedIconHandicapAccessible(String color) {
        verifyColorOfElement(providerDetail.handicapIcon, color);
    }

    @When("Get the Authentication token for {string}")
    public void getTheAuthenticationTokenFor(String app) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getOauthToken(app, "");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName1("");
    }

    @Then("I will be navigated back to the search results screen")
    public void iWillBeNavigatedBackToTheSearchResultsScreen() {
        waitForVisibility(providerDetail.popUpContinue, 5);
        click(providerDetail.popUpContinue);
    }

    @When("I select the back arrow navigated back to the search results screen")
    public void iSelectTheBackArrowNavigatedBackToTheSearchResultsScreen() {
        waitFor(2);
        jsClick(new TMListOfTeamPage().backArrow);
    }

    @And("Verify no warning message will display and the search results will remain")
    public void verifyNoWarningMessageWillDisplayAndTheSearchResultsWillRemain() {
        Assert.assertTrue(providerPage.providerTable.isDisplayed());
    }

    @When("I select an icon in the left hand navigation")
    public void iSelectAnIconInTheLeftHandNavigation() {
        click(providerPage.contactRecordWidget);
    }

    @Then("I will be navigated away, no warning message will display, and the search results will be cleared")
    public void iWillBeNavigatedAwayNoWarningMessageWillDisplayAndTheSearchResultsWillBeCleared() {
        click(providerPage.providerSearch);
        Assert.assertEquals(providerDetail.inputName.getText(), "");
    }

    @When("I view the locations affiliated to the provider")
    public void iViewTheLocationsAffiliatedToTheProvider() {
        Assert.assertTrue(providerDetail.locationAssociated.isDisplayed());
    }

    @Then("Verify user will see {int} locations in the page and")
    public void verifyUserWillSeeLocationsInThePageAnd(int locations) {
        Assert.assertEquals(providerPage.count(providerDetail.locationProviderTable), locations);
    }

    @Then("Verify the option to page through the locations")
    public void verifyTheOptionToPageThroughTheLocations() {
        waitForVisibility(providerDetail.secondPageLocation, 5);
        Assert.assertTrue(providerDetail.secondPageLocation.isDisplayed());
        click(providerDetail.secondPageLocation);
    }

    @And("I choosing {string} from Accepting New Patient drop down")
    public void iChoosingFromAcceptingNewPatientDropDown(String accepNewPat) throws InterruptedException {
        staticWait(3000);
        click(providerPage.advancedSearch);
        providerPage.selectDropDown(providerPage.acceptingNewPatientsDrop, accepNewPat);
    }


    @Then("I logged into CRM and click on Provider Search page")
    public void iLoggedIntoCRMAndClickOnProviderSearchPage() {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        staticWait(3000);
        loginCRM(userNameData.get(), password.get(), projectName.get());
        staticWait(2000);
        waitForVisibility(providerPage.providerSearch, 40);
        providerPage.providerSearch.click();
    }
}
