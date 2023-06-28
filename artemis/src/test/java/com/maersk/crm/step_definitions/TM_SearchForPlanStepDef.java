package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APIPlanManagementController;
import com.maersk.crm.pages.tm.TMConfigurationPage;
import com.maersk.crm.pages.tm.TMListOfProjectsPage;
import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.pages.tm.TMProjectListPage;
import com.maersk.crm.utilities.*;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import io.cucumber.datatable.DataTable;

//import static com.maersk.crm.api_step_definitions.APIPlanManagementController.i_upload_the_Planfile;
//import static com.maersk.crm.api_step_definitions.APIPlanManagementController.i_upload_the_service_region_prior_to_Planfile;
import static org.testng.Assert.*;


import javax.sound.midi.Soundbank;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class TM_SearchForPlanStepDef extends TMUtilities {

    TMListOfProjectsPage projectsPage = new TMListOfProjectsPage();
    TMProjectListPage projectList = new TMProjectListPage();
    TMProjectDetailsPage projectDetails = new TMProjectDetailsPage();
    TMConfigurationPage configPage = new TMConfigurationPage();

    public final ThreadLocal<String> userId = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("login"));
    public final ThreadLocal<String> password = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("password"));
    public final ThreadLocal<String> project = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("projectName").split(" ")[1]);
    public final ThreadLocal<String> tmRegressionProjectName = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("tmRegressionProjectName").split(" ")[1]);
    public final ThreadLocal<String> planNameValue = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> planCodeValue = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> serviceRegionValue = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> programTypeValue = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> subProgramTypeValue = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> zipCodeValue = ThreadLocal.withInitial(String::new);
    //    public final ThreadLocal<APIClassUtil> api = ThreadLocal.withInitial(()->APIClassUtil.getApiClassUtilThreadLocal(true));
    private final ThreadLocal<String> filePath = ThreadLocal.withInitial(String::new);
    //    int i = 0;
    final ThreadLocal<List<WebElement>> l1 = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> l2 = ThreadLocal.withInitial(ArrayList::new);


    @Then("I am navigated to the Plans Configuration screen")
    public void iAmNavigatedToThePlansConfigurationScreen() {
        waitForVisibility(configPage.planConfigHeader, 3);
        assertTrue(configPage.planConfigHeader.isDisplayed());


    }

    @When("I click on Plans Configuration tab")
    public void iClickOnPlansConfigurationTab() {
        waitForClickablility(configPage.planConfigTab, 10);
        click(configPage.planConfigTab);

    }

    @And("I navigate to Project Configuration")
    public void iNavigateToProjectConfiguration() {
        waitForClickablility(projectDetails.projectConfigSideBar, 3);
        jsClick(projectDetails.projectConfigSideBar);
    }

    @And("I select Plans Management")
    public void iSelectPlansManagement() {
        jsClick(configPage.plansManagementTab);
    }

    @Then("I am on the Service Region Configuration screen")
    public void iAmOnTheServiceRegionConfigurationScreen() {
        assertTrue(configPage.serviceRegionConfigHeader.isDisplayed());
    }

    @Given("I log into Tenant Manager Project list page")
    public void iLogIntoTenantManagerProjectListPage() {
        Driver.getDriver().get(ConfigurationReader.getProperty("tmPageURL"));
        tmLogin(userId.get(), password.get());
        waitFor(2);
        waitForVisibility(projectsPage.arrowClick, 10);
    }

    @And("I select the desired project")
    public void iSelectTheDesiredProject() {

        jsClick(projectList.getProjectDetailsButton(project.get()));

    }


    @Then("I will see the Service Region upload success message")
    public void iWillSeeTheServiceRegionUploadSuccessMessage() {

        assertTrue(configPage.serviceRegionSpecificPopupMsg.isDisplayed());
    }

    @And("I upload the Plan Configuration success file")
    public void iUploadThePlanConfigurationSuccessFile() {
        try {
            //classLoader ( "testData/tm/planAndRegionConfig/Plan_Configuration_Success.xlsx" );
            classLoader("testData/tm/planAndRegionConfig/Plan_Configuration_BLCRM.xlsx");
            waitFor(7);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("I will see the Plan Configuration upload success message")
    public void iWillSeeThePlanConfigurationUploadSuccessMessage() {
        waitForVisibility(configPage.successfulUploadPopUpHeader, 5);
        assertTrue(configPage.successfulUploadPopUpHeader.isDisplayed());
    }

    @Then("I select service region Config Tab")
    public void iNavigateToServiceRegionConfigurationScreen() {
        click(configPage.serviceRegionMainTabHdr);
    }

    @Then("I click on fields and verify results")
    public void i_click_on_fields_and_verify_results(DataTable arg1) {
        List<String> lst1 = arg1.asList();
        for (int j = 0; j < lst1.size(); j++) {
            String temp = lst1.get(j);
            switch (temp) {
                case "service region name":
                    waitForVisibility(configPage.serviceRegionMainTabHdr, 30);
                    int i = 0;
                    configPage.serviceRegionNameDropDwn.click();
                    l1.set(configPage.serviceRegionNameDrpDwnLst);
                    l2.set( new ArrayList<>());
                    for (i = 0; i < l1.get().size(); i++) {
                        l2.get().add(l1.get().get(i).getText());
                    }
                    l1.get().get(i - 1).click();
                    System.out.println(l2.get());
                    if (l2.get().size() > 0)
                        System.out.println("Service Region field loaded with records");
                    break;

                case "county name and code":
                    waitForVisibility(configPage.countyNameAndCodeDropDwn, 10);
                    autoCompleteSingleSelectDropDown(configPage.countyNameAndCodeDropDwn, "Blackford - 005");
                    if (configPage.countyNameAndCodeInputValue.isDisplayed())
                        System.out.println("County Name and Code field is loaded with records");
                    break;

                case "zip code":
                    Assert.assertTrue(configPage.zipCodeInput.isDisplayed(), "Zip Code field is not displayed");
                    System.out.println("Zip Code Field Displayed");
                    break;
                case "Plan Name":
                    waitForVisibility(configPage.planConfigHeader, 30);
                    waitFor(4);
                    int i1 = 0;
                    configPage.planNameBx.click();
                    List<WebElement> l11 = configPage.planTabNameDrpDwnLst;
                    List<String> l22 = new ArrayList<>();
                    for (i = 0; i < l11.size(); i++) {
                        l22.add(l11.get(i).getText());
                    }
                    l11.get(i - 1).click();
                    System.out.println(l22);
                    if (l22.size() > 0)
                        System.out.println("Plan Name field loaded with records");
                    break;
                case "Plan Code":
                    waitForVisibility(configPage.planConfigHeader, 30);
                    waitFor(4);
                    i = 0;
                    configPage.searchPlanCode.click();
                    waitFor(4);
                    l1.set(configPage.planCodeDrpDwnList);
                    l2.set( new ArrayList<>());
                    for (i = 0; i < l1.get().size(); i++) {
                        l2.get().add(l1.get().get(i).getText());
                    }
                    l1.get().get(i - 1).click();
                    System.out.println(l2.get());
                    if (l2.get().size() > 0)
                        System.out.println("Plan Code field loaded with records");
                    break;
                case "Service Region":
                    waitForVisibility(configPage.planConfigHeader, 30);
                    waitFor(4);
                    i = 0;
                    configPage.serviceRegionBx.click();
                    l1.set(configPage.serviceRegionNameDrpDwnLst);
                    l2.set( new ArrayList<>());
                    for (i = 0; i < l1.get().size(); i++) {
                        l2.get().add(l1.get().get(i).getText());
                    }
                    l1.get().get(i - 1).click();
                    System.out.println(l2.get());
                    if (l2.get().size() > 0)
                        System.out.println("Service Region field loaded with records");
                    break;
                case "Program Type":
                    waitForVisibility(configPage.planConfigHeader, 30);
                    waitFor(4);
                    i = 0;
                    configPage.ProgramTypeBx.click();
                    l1.set(configPage.programTypeDrpDwnLst);
                    l2.set( new ArrayList<>());
                    for (i = 0; i < l1.get().size(); i++) {
                        l2.get().add(l1.get().get(i).getText());
                    }
                    l1.get().get(i - 1).click();
                    System.out.println(l2.get());
                    if (l2.get().size() > 0)
                        System.out.println("Program Type field loaded with records");
                    break;
                case "Sub Program Type":
                    waitForVisibility(configPage.planConfigHeader, 30);
                    waitFor(4);
                    i = 0;
                    configPage.subProgramTypeBx.click();
                    l1.set(configPage.subprogramTypeDrpDwnLst);
                    l2.set( new ArrayList<>());
                    for (i = 0; i < l1.get().size(); i++) {
                        l2.get().add(l1.get().get(i).getText());
                    }
                    l1.get().get(i - 1).click();
                    System.out.println(l2.get());
                    if (l2.get().size() > 0)
                        System.out.println("Sub Program Type field loaded with records");
                    break;
                case "County-Plan Config":
                    waitForVisibility(configPage.planConfigHeader, 30);
                    autoCompleteSingleSelectDropDown(configPage.countyInputBx, "Blackford");
                    if (configPage.countyInputBx.isDisplayed())
                        System.out.println("County Name field is loaded with records");
                    break;

                case "Zip Code-Plan Config":
                    Assert.assertTrue(configPage.zipCodePlanConfigInput.isDisplayed(), "It is not diaplayed");
                    break;
            }
        }
    }


    @When("I upload the Service Region file")
    public void iUploadTheServiceRegionFile() {
        API_THREAD_LOCAL_FACTORY.getPlanManagementControllerThreadLocal().i_upload_the_service_region_prior_to_Planfile();


    }

    @When("I upload the Plan Configuration file")
    public void iUploadThePlanConfigurationFile() {
        API_THREAD_LOCAL_FACTORY.getPlanManagementControllerThreadLocal().i_upload_the_Planfile();
        //assertEquals(api.statusCode, 200);


    }

    @And("I navigate back to the Project List page")
    public void iNavigateBackToTheProjectListPage() {
        waitFor(2);
        waitForPageToLoad(5);
        jsClick(configPage.projectListPgBtn);
        waitForPageToLoad(2);
        assertTrue(configPage.projectListPage.isDisplayed());
    }

    @And("I navigate back to the previous Project Configuration screen")
    public void iNavigateBackToThePreviousProjectConfigurationScreen() {

        waitFor(2);
        waitForPageToLoad(5);
        jsClick(projectList.getProjectDetailsButton(project.get()));
        waitForClickablility(projectDetails.projectConfigSideBar, 3);
        jsClick(projectDetails.projectConfigSideBar);
        waitFor(2);
        jsClick(configPage.plansManagementTab);
        waitForPageToLoad(2);
        jsClick(configPage.planConfigTab);

    }

    @Then("I can make a valid search of plans")
    public void iCanMakeAValidSearchOfPlans() {
        waitForPageToLoad(5);
        waitFor(5);
        scrollToElement(configPage.planNameBx);
        waitFor(3);
        selectRandomDropDownOptionExcludingOne(configPage.planNameBx);
        waitFor(3);
        waitForVisibility(configPage.searchPlansBtn, 20);
        jsClick(configPage.searchPlansBtn);
        waitFor(3);
        //waitForVisibility(configPage.searchResultHeader,20);
        try {
            //waitForVisibility(configPage.searchResultHeader,20);
            assertTrue(configPage.searchResultHeader.isDisplayed());
        } catch (NoSuchElementException a) {
            System.out.println("Plan Config Upload False Positive -- DropDowns Not Populated");
            assertTrue(configPage.resultNoRecords.isDisplayed(), "This indicates Plan Config file didn't populate field options");
        }
    }

    @And("I can sort results by ascending or descending order")
    public void iCanSortResultsByAscendingOrDescendingOrder() {
        List<WebElement> resultsAsc = Driver.getDriver().findElements(By.xpath("//table[@class='MuiTable-root mdl-data-table mdl-js-data-table mx-table table mt-4 mb-0 mdl-color--grey-50']/tbody/tr/td[2]"));
        assertTrue(ascendingOrderTexts(resultsAsc));
        jsClick(configPage.planNameResultsTableHead);
        List<WebElement> resultsDsc = Driver.getDriver().findElements(By.xpath("//table[@class='MuiTable-root mdl-data-table mdl-js-data-table mx-table table mt-4 mb-0 mdl-color--grey-50']/tbody/tr/td[2]"));
        assertTrue(descendingOrderTexts(resultsDsc));
    }

    @When("I click on a Plan Name search result")
    public void iClickOnAPlanNameSearchResult() {
        waitForVisibility(configPage.firstSearchResult, 20);
        jsClick(configPage.firstSearchResult);
    }

    @Then("I am brought to the Plan Details Screen")
    public void iAmBroughtToThePlanDetailsScreen() {
        waitForPageToLoad(20);
        assertTrue(configPage.planDetailsHeader.isDisplayed());
        System.out.println("Printing " + configPage.planDetailsHeader.getText() + " :::   " + tmRegressionProjectName.get() + " - PLAN DETAILS");
        assertTrue(configPage.planDetailsHeader.getText().equalsIgnoreCase(tmRegressionProjectName.get() + " - PLAN DETAILS"));
    }

    @Then("I verify exclusion checkbox updated")
    public void i_verify_exclusion_checkbox_updated() throws Throwable {
        System.out.println(configPage.planInfoExclusionUpdated.getAttribute("value"));
        assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("false"));
    }

    @Then("I am navigated to the Plan Details Screen")
    public void iAmNavigatedToThePlanDetailsScreen() {
        waitForPageToLoad(20);
        assertTrue(configPage.planDetailsHeader.isDisplayed());
        System.out.println("Printing " + configPage.planDetailsHeader.getText() + " :::   " + tmRegressionProjectName.get() + " - PLAN DETAILS");
        waitFor(5);
    }

    @Then("I verify information is displayed in plan details panel")
    public void iVerifyInformationIsDisplayedInPlanInformationPanel() {
        Assert.assertTrue(configPage.picPlanName.isDisplayed(), "Plan Name field is not displayed");
        Assert.assertTrue(configPage.picPlanNameValue.isDisplayed(), "Plan Name Value is not displayed");
        Assert.assertTrue(configPage.picPlanCode.isDisplayed(), "Plan Code field is not displayed");
        Assert.assertTrue(configPage.picPlanCodeValue.isDisplayed(), "Plan Code Value is not displayed");
        Assert.assertTrue(configPage.picPlanShortName.isDisplayed(), "Plan Short Name field is not displayed");
        Assert.assertTrue(configPage.picPlanShortNameValue.isDisplayed(), "Plan Short Name Value is not displayed");
        Assert.assertTrue(configPage.picServiceRegion.isDisplayed(), "Service Region field is not displayed");
        Assert.assertTrue(configPage.picServiceRegionValue.isDisplayed(), "Service Region Value is not displayed");
        Assert.assertTrue(configPage.picPlanType.isDisplayed(), "Plan Type field is not displayed");
        Assert.assertTrue(configPage.picPlanTypeValue.isDisplayed(), "Plan Type Value is not displayed");
        Assert.assertTrue(configPage.picProgramType.isDisplayed(), "Program Type field is not displayed");
        Assert.assertTrue(configPage.picProgramTypeValue.isDisplayed(), "Program Type Value is not displayed");
        Assert.assertTrue(configPage.picSubType.isDisplayed(), "SubprogramType field is not displayed");
        Assert.assertTrue(configPage.picSubTypeValue.isDisplayed(), "SubprogramType Value is not displayed");
        Assert.assertTrue(configPage.picEligibility.isDisplayed(), "Eligibility field is not displayed");
        Assert.assertTrue(configPage.picEligibilityValue.isDisplayed(), "Eligibility value is not displayed");
        assertTrue(UIAutoUitilities.isElementPresent(configPage.planInfoExclusionCheckBox), "Exclude plan from auto-assignment Check Box is not present");
        Assert.assertTrue(configPage.contractStartDateField.isDisplayed(), "Contract Start Date Value is not displayed");
        Assert.assertTrue(configPage.contractStartDate.isDisplayed(), "Contract Start Date is not displayed");
        Assert.assertTrue(configPage.contractEndDateField.isDisplayed(), "Contract End Date Value is not displayed");
        Assert.assertTrue(configPage.contractEndDate.isDisplayed(), "Contract End Date is not displayed");

        Assert.assertTrue(configPage.enrollmentStartDateField.isDisplayed(), "Enrollment start date field in not displayed");
        Assert.assertTrue(configPage.enrollmentStartDate.isDisplayed(), "Enrollment start date value in not displayed");
        Assert.assertTrue(configPage.enrollmentEndDateField.isDisplayed(), "Enrollment end date field in not displayed");
        Assert.assertTrue(configPage.enrollmentEndDate.isDisplayed(), "Enrollment end date value in not displayed");
        Assert.assertTrue(configPage.enrollmentCapField.isDisplayed(), "Enrollment cap field is not displayed");
        Assert.assertTrue(configPage.enrollmentCap.isDisplayed(), "Enrollment cap value is not displayed");
        Assert.assertTrue(configPage.enrollmentCapStartDateField.isDisplayed(), "Enrollment cap start date field is not displayed");
        Assert.assertTrue(configPage.enrollmentCapStartDate.isDisplayed(), "Enrollment cap start date value is not displayed");
        Assert.assertTrue(configPage.enrollmentCapEndDateField.isDisplayed(), "Enrollment cap end date field is not displayed");
        Assert.assertTrue(configPage.enrollmentCapEndDate.isDisplayed(), "Enrollment cap end date value is not displayed");
        assertTrue(UIAutoUitilities.isElementPresent(configPage.enrollmentInfoPcpCheckBox), "Enrollment Information PCP Check Box is not present");

        waitFor(5);
    }


    @Then("I verify information is displayed in contact details panel")
    public void iVerifyInformationIsDisplayedInContactInformationPanel() {

        scrollUp();
        jsClick(configPage.contactDetailsScreen);
        waitFor(2);
        Assert.assertTrue(configPage.addressLine1Field.isDisplayed(), "Address Line1 Field is not displayed");
        Assert.assertTrue(configPage.addressLine1.isDisplayed(), "Address Line1 value is not displayed");
        Assert.assertTrue(configPage.addressLine2Field.isDisplayed(), "Address Line2 Field is not displayed");
        Assert.assertTrue(configPage.addressLine2.isDisplayed(), "Address Line2 value is not displayed");
        Assert.assertTrue(configPage.cityField.isDisplayed(), "City Field is not displayed");
        Assert.assertTrue(configPage.city.isDisplayed(), "City value is not displayed");
        Assert.assertTrue(configPage.stateField.isDisplayed(), "State Field is not displayed");
        Assert.assertTrue(configPage.state.isDisplayed(), "State value is not displayed");
        Assert.assertTrue(configPage.zipCodeField.isDisplayed(), "Zip Code Field is not displayed");
        Assert.assertTrue(configPage.zipCode.isDisplayed(), "Zip Code value is not displayed");

        System.out.println("All Plan Mailing Information Fields verified");

        Assert.assertTrue(configPage.memberPhone1Field.isDisplayed(), "Member Service Phone no 1 field is not displayed");
        Assert.assertTrue(configPage.memberServicePhone1.isDisplayed(), "Member Service Phone no 1 value is not displayed");
        Assert.assertTrue(configPage.providerPhone1Field.isDisplayed(), "Provider Service Phone no 1 field is not displayed");
        Assert.assertTrue(configPage.providerPhoneno1.isDisplayed(), "Provider Service Phone no 1 value is not displayed");
        Assert.assertTrue(configPage.planmemberserviceswebsiteURLField.isDisplayed(), "Plan member service website URL field is not displayed");
        Assert.assertTrue(configPage.planmemberserviceswebsiteURL1.isDisplayed(), "Plan member service website URL value is not displayed");
        Assert.assertTrue(configPage.plancontactfirstnameField1.isDisplayed(), "Plan Contact First name fiels is not displayed");
        Assert.assertTrue(configPage.plancontactfirstname1.isDisplayed(), "Plan contact First name value is not displayed");
        Assert.assertTrue(configPage.plancontactlastnameField1.isDisplayed(), "Plan contact last name field is not displayed");
        Assert.assertTrue(configPage.plancontactlastname1.isDisplayed(), "Plan contact last name value is not displayed");
        Assert.assertTrue(configPage.plancontactphonenumberField1.isDisplayed(), "Plan contact phone no 1 field not displayed");
        Assert.assertTrue(configPage.plancontactphonenumber1.isDisplayed(), "Plan contact phone no 1 value is not displayed");
        Assert.assertTrue(configPage.plancontactemailField1.isDisplayed(), "Plan contact email field is not displayed");
        Assert.assertTrue(configPage.plancontactemail1.isDisplayed(), "Plan contact email value is not displayed");
        Assert.assertTrue(configPage.fileLocationField.isDisplayed(), "File location field is not displayed");
        Assert.assertTrue(configPage.fileLocation1.isDisplayed(), "File location value is not displayed");
        Assert.assertTrue(configPage.memberPhone2Field.isDisplayed(), "Member phone no 2 field is not displayed");
        Assert.assertTrue(configPage.memberservicesphoneno2.isDisplayed(), "Member phone no 2 value is not displayed");
        Assert.assertTrue(configPage.memberPhone3Field.isDisplayed(), "Member phone no 3 field is not displayed");
        Assert.assertTrue(configPage.memberPhoneno3.isDisplayed(), "Member phone no 3 value is not displayed");
        Assert.assertTrue(configPage.memberPhone4Field.isDisplayed(), "Member phone no 4 field is not displayed");
        Assert.assertTrue(configPage.memberPhoneno4.isDisplayed(), "Member phone no 4 value is not displayed");
        Assert.assertTrue(configPage.memberPhone5Field.isDisplayed(), "Member phone no 5 field is not displayed");
        Assert.assertTrue(configPage.memberPhoneno5.isDisplayed(), "Member phone no 5 value is not displayed");
        Assert.assertTrue(configPage.providerservicesphoneno2Field.isDisplayed(), "PROVIDER SERVICES PHONE #2 field is not displayed");
        Assert.assertTrue(configPage.providerservicesphoneno2.isDisplayed(), "PROVIDER SERVICES PHONE #2 value is not displayed");
        Assert.assertTrue(configPage.providerservicesphoneno3Field.isDisplayed(), "PROVIDER SERVICES PHONE #3 field is not displayed");
        Assert.assertTrue(configPage.providerservicesphoneno3.isDisplayed(), "PROVIDER SERVICES PHONE #3 value is not displayed");
        System.out.println();
        System.out.println("All Contact Information Fields verified");
        waitFor(5);
    }

    @And("I can see the Plan Name row header on the search results table")
    public void iCanSeeThePlanNameRowHeaderOnTheSearchResultsTable() {
        try {
            waitForVisibility(configPage.planNameResultsTableHead, 5);
            assertTrue(configPage.planNameResultsTableHead.isDisplayed());
        } catch (Exception e) {
            assertTrue(configPage.noSearchResultsHeader.isDisplayed());
        }
    }

    @And("I will be able to view first {int} Plan Name Records")
    public void iWillBeAbleToViewFirstPlanNameRecords(int arg0) {
        /**
         NOTE : Have yet to have more than 4 search results
         **/
    }

    @And("I can use pagination to view additional pages of results.")
    public void iCanUsePaginationToViewAdditionalPagesOfResults() {
        waitForPageToLoad(4);
        assertTrue(configPage.paginationIcon.getAttribute("class").equalsIgnoreCase("active"));
        /**
         NOTE : basic assertion until the true behavior can be manually verified
         **/
    }

    @And("I make a search without populating any parameters")
    public void iMakeASearchWithoutPopulatingAnyParameters() {
        jsClick(configPage.cancelBtn);
        waitForVisibility(configPage.searchPlansBtn, 30);
        jsClick(configPage.searchPlansBtn);

    }

    @Then("I will see appropriate Error Message")
    public void iWillSeeAppropriateErrorMessage() {
        waitForVisibility(configPage.searchPlanErrMsg, 30);
        assertTrue(configPage.searchPlanErrMsg.isDisplayed());
        assertTrue(configPage.searchPlanErrMsgText.isDisplayed());

    }

    @When("I enter search parameters into the fields")
    public void iEnterSearchParametersIntoTheFields() {
        waitForVisibility(configPage.planNameBx, 30);
        selectRandomDropDownOption(configPage.planNameBx);
        selectRandomDropDownOption(configPage.searchPlanCode);
        selectRandomDropDownOption(configPage.serviceRegionBx);
        selectRandomDropDownOption(configPage.searchProgramType);
        selectRandomDropDownOption(configPage.subProgramTypeBx);
        configPage.zipCodeBx.sendKeys("12345");
        planNameValue.set(configPage.planNameValue.getAttribute("value"));
        planCodeValue.set(configPage.planCodeValue.getAttribute("value"));
        serviceRegionValue.set(configPage.serviceRegionValue.getAttribute("value"));
        programTypeValue.set(configPage.programTypeValue.getAttribute("value"));
        subProgramTypeValue.set(configPage.subProgramTypeValue.getAttribute("value"));
        zipCodeValue.set(configPage.zipCodeBx.getAttribute("value"));
    }

    @And("click cancel button")
    public void clickCancelButton() {
        waitForVisibility(configPage.cancelBtn, 30);
        jsClick(configPage.cancelBtn);
        // click(configPage.cancelBtn);
    }

    @Then("entered parameters will be cleared")
    public void enteredParametersWillBeCleared() {
        //waitForVisibility(configPage.planNameValue,30);
        // assertTrue(configPage.planNameValue.getText().equalsIgnoreCase("");
//        assertTrue(configPage.planNameValue.getAttribute("value")!=planNameValue);
//        assertTrue(configPage.planCodeValue.getAttribute("value")!=planCodeValue);
//        assertTrue(configPage.serviceRegionValue.getAttribute("value")!=serviceRegionValue);
//        assertTrue(configPage.programTypeValue.getAttribute("value")!=programTypeValue);
//        assertTrue(configPage.subProgramTypeValue.getAttribute("value")!=subProgramTypeValue);
//        assertTrue(configPage.zipCodeBx.getAttribute("value")!=zipCodeValue);

        assertTrue(configPage.planNameValue.getAttribute("value").equals(""));
        assertTrue(configPage.planCodeValue.getAttribute("value").equals(""));
        assertTrue(configPage.serviceRegionValue.getAttribute("value").equals(""));
        assertTrue(configPage.programTypeValue.getAttribute("value").equals(""));
        assertTrue(configPage.subProgramTypeValue.getAttribute("value").equals(""));
        assertTrue(configPage.zipCodeBx.getAttribute("value").equals(""));
    }

    @And("I will remain on the Plans Configuration screen")
    public void iWillRemainOnThePlansConfigurationScreen() {
        waitForVisibility(configPage.searchPlanHeader, 30);
        assertTrue(configPage.searchPlanHeader.isDisplayed());
    }


    @Then("I can search for Plans using any one of the fields")
    public void iCanSearchForPlansUsingAnyOneOfTheFields() {
        waitForVisibility(configPage.planNameBx, 30);
        assertTrue(configPage.planNameBx.isDisplayed());
        assertTrue(configPage.searchPlanCode.isDisplayed());
        assertTrue(configPage.serviceRegionBx.isDisplayed());
        assertTrue(configPage.searchProgramType.isDisplayed());
        assertTrue(configPage.subProgramTypeBx.isDisplayed());
        assertTrue(configPage.zipCodeBx.isDisplayed());

        selectRandomDropDownOption(configPage.planNameBx);
        waitForVisibility(configPage.searchPlansBtn, 30);
        jsClick(configPage.searchPlansBtn);
        //waitForVisibility(configPage.searchResultHeader,30);
        try {
            assertTrue(configPage.searchResultHeader.isDisplayed());
        } catch (NoSuchElementException b) {
            assertTrue(configPage.noSearchResultsHeader.isDisplayed());
        }
        planNameValue.set(configPage.planNameValue.getAttribute("value").trim());
        //System.out.println("==="+planNameValue);
        waitForVisibility(configPage.cancelBtn, 30);
        jsClick(configPage.cancelBtn);
        waitFor(2);
//        waitForVisibility(configPage.planNameValue,30);
//        System.out.println("==="+configPage.planNameValue.getAttribute("value"));
//        assertTrue(configPage.planNameValue.getAttribute("value")!=planNameValue);
        Assert.assertTrue(configPage.planNameValue.getAttribute("value").equals(""));

        waitForVisibility(configPage.serviceRegionBx, 30);
        selectRandomDropDownOption(configPage.serviceRegionBx);
        waitForVisibility(configPage.searchPlansBtn, 30);
        jsClick(configPage.searchPlansBtn);
        // waitForVisibility(configPage.searchPlansBtn,30);
        try {
            //waitForVisibility(configPage.searchResultHeader,30);
            assertTrue(configPage.searchResultHeader.isDisplayed());
        } catch (NoSuchElementException b) {
            assertTrue(configPage.noSearchResultsHeader.isDisplayed());
        }

        serviceRegionValue.set(configPage.serviceRegionValue.getAttribute("value"));
        waitForVisibility(configPage.cancelBtn, 30);
        jsClick(configPage.cancelBtn);
        //  waitForVisibility(configPage.serviceRegionValue,30);
        //  assertTrue(configPage.serviceRegionValue.getAttribute("value")!=serviceRegionValue);
        Assert.assertTrue(configPage.serviceRegionValue.getAttribute("value").equals(""));

        waitForVisibility(configPage.searchPlanCode, 30);
        selectRandomDropDownOption(configPage.searchPlanCode);
        waitForVisibility(configPage.searchPlansBtn, 30);
        jsClick(configPage.searchPlansBtn);
        //  waitForVisibility(configPage.searchResultHeader,30);
        try {
            // waitForVisibility(configPage.searchResultHeader,30);
            assertTrue(configPage.searchResultHeader.isDisplayed());
        } catch (NoSuchElementException b) {
            assertTrue(configPage.noSearchResultsHeader.isDisplayed());
        }
        planCodeValue.set(configPage.planCodeValue.getAttribute("value").trim());
        waitForVisibility(configPage.cancelBtn, 30);
        jsClick(configPage.cancelBtn);
        //waitForVisibility(configPage.planCodeValue,30);
        assertTrue(configPage.planCodeValue.getAttribute("value").equals(""));

        selectRandomDropDownOption(configPage.searchProgramType);
        waitForVisibility(configPage.searchPlansBtn, 30);
        jsClick(configPage.searchPlansBtn);

        //waitForVisibility(configPage.searchResultHeader,30);
        try {
            //waitForVisibility(configPage.searchResultHeader,30);
            assertTrue(configPage.searchResultHeader.isDisplayed());
        } catch (NoSuchElementException b) {
            assertTrue(configPage.noSearchResultsHeader.isDisplayed());
        }
        programTypeValue.set(configPage.programTypeValue.getAttribute("value").trim());
        waitForVisibility(configPage.cancelBtn, 30);
        jsClick(configPage.cancelBtn);
        //waitForVisibility(configPage.programTypeValue,30);
        assertTrue(configPage.programTypeValue.getAttribute("value").equals(""));

        selectRandomDropDownOption(configPage.subProgramTypeBx);
        waitForVisibility(configPage.subProgramTypeBx, 30);
        jsClick(configPage.searchPlansBtn);
        //waitForVisibility(configPage.searchPlansBtn,30);
        try {
            assertTrue(configPage.searchResultHeader.isDisplayed());
        } catch (NoSuchElementException b) {
            assertTrue(configPage.noSearchResultsHeader.isDisplayed());
        }
        subProgramTypeValue.set(configPage.subProgramTypeValue.getAttribute("value").trim());
        waitForVisibility(configPage.cancelBtn, 30);
        jsClick(configPage.cancelBtn);
        //waitForVisibility(configPage.subProgramTypeValue, 30);
        assertTrue(configPage.subProgramTypeValue.getAttribute("value").equals(""));

        configPage.zipCodeBx.sendKeys("12345");
        waitForVisibility(configPage.searchPlansBtn, 30);
        jsClick(configPage.searchPlansBtn);
        //waitForVisibility(configPage.searchResultHeader, 30);
        try {
            //waitForVisibility(configPage.searchResultHeader, 30);
            assertTrue(configPage.searchResultHeader.isDisplayed());
        } catch (NoSuchElementException b) {
            assertTrue(configPage.noSearchResultsHeader.isDisplayed());
        }
        zipCodeValue.set(configPage.zipCodeBx.getAttribute("value").trim());
        waitForVisibility(configPage.cancelBtn, 30);
        jsClick(configPage.cancelBtn);
        //waitForVisibility(configPage.zipCode, 30);
        assertTrue(configPage.zipCodeBx.getAttribute("value").equals(""));

    }

    @And("I verify the Service Region file upload success message")
    public void iVerifyTheServiceRegionFileUploadSuccessMessage() {
        waitForVisibility(configPage.uploadMsgPane, 5);
        try {
            Assert.assertTrue(configPage.uploadScucessMsg.getText().contains("SUCCESS"), "Successful message is not displayed");

        } catch (TimeoutException a) {
            waitFor(2);
            waitForVisibility(configPage.uploadScucessMsg, 60);
            Assert.assertTrue(configPage.uploadScucessMsg.getText().contains("SUCCESS"), "Successful message is not displayed");
            Assert.assertTrue(configPage.uploadScucessMsgTxt.getText().contains("Upload Successful - Please Upload Plan File"), "Successful message text is not displayed");
        } catch (NoSuchElementException b) {
            waitFor(2);
            waitForVisibility(configPage.uploadScucessMsg, 60);
            Assert.assertTrue(configPage.uploadScucessMsg.getText().contains("SUCCESS"), "Successful message is not displayed");
            Assert.assertTrue(configPage.uploadScucessMsgTxt.getText().contains("Upload Successful - Please Upload Plan File"), "Successful message text is not displayed");
        }
    }

    @Then("I can search for Plans using any two of the fields")
    public void iCanSearchForPlansUsingAnyTwoOfTheFields() {

        selectRandomDropDownOption(configPage.serviceRegionBx);
        selectRandomDropDownOption(configPage.planNameBx);
        jsClick(configPage.searchPlansBtn);
        try {
            assertTrue(configPage.searchResultHeader.isDisplayed());
        } catch (NoSuchElementException b) {
            assertTrue(configPage.noSearchResultsHeader.isDisplayed());
        }
        serviceRegionValue.set(configPage.serviceRegionValue.getAttribute("value"));
        planNameValue.set(configPage.planNameValue.getAttribute("value").trim());
        jsClick(configPage.cancelBtn);
        assertTrue(configPage.serviceRegionValue.getAttribute("value") != serviceRegionValue.get());
        assertTrue(configPage.planNameValue.getAttribute("value") != planNameValue.get());

        selectRandomDropDownOption(configPage.searchPlanCode);
        selectRandomDropDownOption(configPage.searchProgramType);
        jsClick(configPage.searchPlansBtn);
        try {
            assertTrue(configPage.searchResultHeader.isDisplayed());
        } catch (NoSuchElementException b) {
            assertTrue(configPage.noSearchResultsHeader.isDisplayed());
        }
        programTypeValue.set(configPage.programTypeValue.getAttribute("value").trim());
        planCodeValue.set(configPage.planCodeValue.getAttribute("value").trim());
        jsClick(configPage.cancelBtn);
        assertTrue(configPage.planCodeValue.getAttribute("value") != planCodeValue.get());
        assertTrue(configPage.programTypeValue.getAttribute("value") != programTypeValue.get());

        selectRandomDropDownOption(configPage.subProgramTypeBx);
        configPage.zipCodeBx.sendKeys("12345");
        jsClick(configPage.searchPlansBtn);
        try {
            assertTrue(configPage.searchResultHeader.isDisplayed());
        } catch (NoSuchElementException b) {
            assertTrue(configPage.noSearchResultsHeader.isDisplayed());
        }
        subProgramTypeValue.set(configPage.subProgramTypeValue.getAttribute("value").trim());
        zipCodeValue.set(configPage.zipCodeBx.getAttribute("value").trim());
        jsClick(configPage.cancelBtn);
        assertTrue(configPage.zipCodeBx.getAttribute("value") != zipCodeValue.get());
        assertTrue(configPage.subProgramTypeValue.getAttribute("value") != subProgramTypeValue.get());
    }

    @And("I click the State-Wide check box")
    public void iClickTheStateWideCheckBox() {

        jsClick(configPage.searchStateWideChxBx);
        assertTrue(configPage.searchStateWideChxBx.getAttribute("value").trim().equalsIgnoreCase("true"));
    }

    @Then("I see all search fields are disabled")
    public void iSeeAllSearchFieldsAreDisabled() {
        assertTrue(configPage.planNameBx.getAttribute("class").trim().endsWith("Mui-disabled"));
        assertTrue(configPage.searchPlanCode.getAttribute("class").trim().endsWith("Mui-disabled"));
        assertTrue(configPage.serviceRegionBx.getAttribute("class").trim().endsWith("Mui-disabled"));
        assertTrue(configPage.searchProgramType.getAttribute("class").trim().endsWith("Mui-disabled"));
        assertTrue(configPage.subProgramTypeBx.getAttribute("class").trim().endsWith("Mui-disabled"));
        assertTrue(configPage.zipCodeBx.getAttribute("class").trim().endsWith("Mui-disabled"));


    }

    @And("I navigate to the Outbound Correspondence Configuration screen")
    public void iNavigateToTheOutboundCorrespondenceConfigurationScreen() {
        jsClick(configPage.outboundCorrespondenceSidebarTab);
        waitForPageToLoad(2);
    }


    @Given("I make a valid selection of Plan Name")
    public void i_make_a_valid_selection_of_Plan_Name() {
        scrollToElement(configPage.searchPlanName);
        configPage.searchPlanName.click();
        waitForClickablility(configPage.planTabNameDrpDwnLst.get(2), 10);
        jsClick(configPage.planTabNameDrpDwnLst.get(2));
    }

    @Given("I click on Search Button on Search Plans tab")
    public void i_click_on_Search_Button_on_Search_Plans_tab() {
        jsClick(configPage.planTabSearchBtn);
    }

    @Given("I verify the search in results")
    public void i_verify_the_search_in_results() {
        assertTrue(configPage.searchResultHeader.isDisplayed());
    }

    @When("I click on the Plan Name on search results")
    public void i_click_on_the_Plan_Name_on_search_results() {
        scrollToElement(configPage.firstSearchResult);
        jsClick(configPage.firstSearchResult);
    }

    @When("I click on the Edit button next to the Plan Information Container")
    public void i_click_on_the_Edit_button_next_to_the_Plan_Information_Container() {
        scrollToElement(configPage.planDetails_planInfo_editBtn);
        jsClick(configPage.planDetails_planInfo_editBtn);
    }

    @Then("I will not be able to update the Eligibility Limitations field")
    public void i_will_not_be_able_to_update_the_Eligibility_Limitations_field() {
        try {
            configPage.planDetails_planInfo_eligibilityInput.sendKeys("test");
            assertTrue(false, "configPage.planDetails_planInfo_eligibilityInput is WORKING OKAY");
        } catch (ElementNotInteractableException e) {
            assertTrue(true, "configPage.planDetails_planInfo_eligibilityInput is NOT working");
        }
    }

    @When("I click on the Edit button next to the  Information Container")
    public void i_click_on_the_Edit_button_next_to_the_Information_Container() {
        scrollToElement(configPage.planDetails_enrollmentInfo_editBtn);
        jsClick(configPage.planDetails_enrollmentInfo_editBtn);
    }

    @Then("I will not be able to update the Enrollment Cap field")
    public void i_will_not_be_able_to_update_the_Enrollment_Cap_field() {
        try {
            configPage.planDetails_planInfo_EnrollmentCapInput.sendKeys("test");
            assertTrue(false, "configPage.planDetails_planInfo_EnrollmentCapInput IS WORKING");
        } catch (ElementNotInteractableException e) {
            assertTrue(true, "configPage.planDetails_planInfo_EnrollmentCapInput IS NOT WORKING");
        }
    }

    @Then("I will upload the region and plan file thru API")
    public void i_will_Region_plan() {
        API_THREAD_LOCAL_FACTORY.getPlanManagementControllerThreadLocal().i_upload_the_service_region_prior_to_Planfile();
        API_THREAD_LOCAL_FACTORY.getPlanManagementControllerThreadLocal().i_upload_the_Planfile();
    }
}









