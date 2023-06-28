package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRM_SearchProviders_AdvancedSearchUIPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class CRM_SearchProviders_AdvancedSearchUIStepDef extends CRMUtilities  {




    CRM_SearchProviders_AdvancedSearchUIPage crmSearchProvidersAdvancedSearchUIPage = new CRM_SearchProviders_AdvancedSearchUIPage();


    @Given("Click at advance search")
    public void Click_at_advance_search_button() throws InterruptedException {
        crmSearchProvidersAdvancedSearchUIPage.advancesearch.click();

        waitFor(5);

    }
    @Given("Verify additional search area")
    public void Verify_additional_search_area() throws InterruptedException {
        waitFor(5);
        Assert.assertTrue(crmSearchProvidersAdvancedSearchUIPage.additionalarea.isDisplayed());
    }
    @Given("Verify additional search area element")
    public void Verify_additional_search_area_element() throws InterruptedException {
        waitFor(5);

        crmSearchProvidersAdvancedSearchUIPage.selectlanguage.sendKeys("ENG");
        crmSearchProvidersAdvancedSearchUIPage.selectlanguage.sendKeys(Keys.DOWN);
        crmSearchProvidersAdvancedSearchUIPage.selectlanguage.sendKeys(Keys.ENTER);

        Assert.assertTrue(crmSearchProvidersAdvancedSearchUIPage.pcpindicator.isDisplayed());
        waitFor(2);

        selectDropDown(crmSearchProvidersAdvancedSearchUIPage.pcpindicator, "Yes");
        selectDropDown(crmSearchProvidersAdvancedSearchUIPage.pcpindicator, "No");

        selectDropDown(crmSearchProvidersAdvancedSearchUIPage.selectobindicator, "Yes");
        selectDropDown(crmSearchProvidersAdvancedSearchUIPage.selectobindicator, "No");

        selectDropDown(crmSearchProvidersAdvancedSearchUIPage.providerAcceptNewPatient, "Yes");
        selectDropDown(crmSearchProvidersAdvancedSearchUIPage.providerAcceptNewPatient, "No");

        selectDropDown(crmSearchProvidersAdvancedSearchUIPage.selectproviderGenderServed, "MALE");
        selectDropDown(crmSearchProvidersAdvancedSearchUIPage.selectproviderGenderServed, "FEMALE");
        selectDropDown(crmSearchProvidersAdvancedSearchUIPage.selectproviderGenderServed, "BOTH");
        selectDropDown(crmSearchProvidersAdvancedSearchUIPage.selectproviderGender, "Male");
        selectDropDown(crmSearchProvidersAdvancedSearchUIPage.selectproviderGender, "Female");

        crmSearchProvidersAdvancedSearchUIPage.selectcounty.sendKeys("UNION");
        crmSearchProvidersAdvancedSearchUIPage.selectcounty.sendKeys(Keys.DOWN);
        crmSearchProvidersAdvancedSearchUIPage.selectcounty.sendKeys(Keys.ENTER);
        crmSearchProvidersAdvancedSearchUIPage.selectstate.sendKeys("GA");
        crmSearchProvidersAdvancedSearchUIPage.selectstate.sendKeys(Keys.DOWN);
        crmSearchProvidersAdvancedSearchUIPage.selectstate.sendKeys(Keys.ENTER);

        //crmSearchProvidersAdvancedSearchUIPage.enterminage.sendKeys("40");
        //crmSearchProvidersAdvancedSearchUIPage.entermaxage.sendKeys("75");
        crmSearchProvidersAdvancedSearchUIPage.enternpi.sendKeys("1124424536");



    }

    @Then("User select language {string}")
    public void user_select_language(String lang) {
        // Write code here that turns the phrase above into concrete actions
        crmSearchProvidersAdvancedSearchUIPage.selectlanguage.sendKeys(lang);
        crmSearchProvidersAdvancedSearchUIPage.selectlanguage.sendKeys(Keys.DOWN);
        crmSearchProvidersAdvancedSearchUIPage.selectlanguage.sendKeys(Keys.ENTER);    }

    @Then("User select PCI Indicator {string}")
    public void user_select_PCI_Indicator(String pci) {
        // Write code here that turns the phrase above into concrete actions
        selectDropDown(crmSearchProvidersAdvancedSearchUIPage.pcpindicator, pci);

    }

    @Then("User select OB Indicator {string}")
    public void user_select_OB_Indicator(String obind) {
        // Write code here that turns the phrase above into concrete actions
        selectDropDown(crmSearchProvidersAdvancedSearchUIPage.selectobindicator, "No");

    }
    @Then("Verify Default Value of accepting new patient")
    public void Default_value( ) {
        // Write code here that turns the phrase above into concrete actions
        waitForVisibility(crmSearchProvidersAdvancedSearchUIPage.providerAcceptNewPatient, 30);
        Assert.assertTrue(crmSearchProvidersAdvancedSearchUIPage.providerAcceptNewPatient.getText().equalsIgnoreCase("Yes"));
    }
    @Then("User select Accept new patient {string}")
    public void user_select_Accept_new_patient(String acceptpatient) {
        // Write code here that turns the phrase above into concrete actions
        selectDropDown(crmSearchProvidersAdvancedSearchUIPage.providerAcceptNewPatient, acceptpatient);

    }
    @Then("User select gender served {string}")
    public void user_select_gender_served(String genderserved) {
        // Write code here that turns the phrase above into concrete actions
        selectDropDown(crmSearchProvidersAdvancedSearchUIPage.selectproviderGenderServed, genderserved);

    }
    @Then("User select provider gender {string}")
    public void user_select_provider_gender(String providergender) {
        // Write code here that turns the phrase above into concrete actions
        selectDropDown(crmSearchProvidersAdvancedSearchUIPage.selectproviderGender, providergender);

    }
    @Then("User select county {string}")
    public void user_select_county(String county) {
        // Write code here that turns the phrase above into concrete actions
        crmSearchProvidersAdvancedSearchUIPage.selectcounty.sendKeys(county);
        crmSearchProvidersAdvancedSearchUIPage.selectcounty.sendKeys(Keys.DOWN);
        crmSearchProvidersAdvancedSearchUIPage.selectcounty.sendKeys(Keys.ENTER);

    }
    @Then("User select STATE {string}")
    public void user_select_STATE(String state) {
        // Write code here that turns the phrase above into concrete actions
        crmSearchProvidersAdvancedSearchUIPage.selectstate.sendKeys(state);
        crmSearchProvidersAdvancedSearchUIPage.selectstate.sendKeys(Keys.DOWN);
        crmSearchProvidersAdvancedSearchUIPage.selectstate.sendKeys(Keys.ENTER);

    }

    @Then("User select Provider gender {string}")
    public void user_select_Provider_gender(String gender) {
        // Write code here that turns the phrase above into concrete actions
        selectDropDown(crmSearchProvidersAdvancedSearchUIPage.selectproviderGender, gender);
    }

    @Then("User enter the patient min age {string}")
    public void user_enter_the_patient_min_age(String age) {
        // Write code here that turns the phrase above into concrete actions
        crmSearchProvidersAdvancedSearchUIPage.enterminage.sendKeys(age);
    }

    @Then("Click on search button")
    public void click_on_search_button() {
        // Write code here that turns the phrase above into concrete actions
        crmSearchProvidersAdvancedSearchUIPage.searchButton.click();
    }

    @Then("Click on arrow to expand the result")
    public void click_on_arrow_to_expand_the_result() {
        // Write code here that turns the phrase above into concrete actions
        waitForClickablility(crmSearchProvidersAdvancedSearchUIPage.expandproviderdetail, 45);
        crmSearchProvidersAdvancedSearchUIPage.expandproviderdetail.click();

    }
    @Then("Verify that results are retrieved with respect to language {string}")
    public void verify_that_results_are_retrieved_with_respect_to_language(String lang) {
        // Write code here that turns the phrase above into concrete actions
        waitForVisibility(crmSearchProvidersAdvancedSearchUIPage.getlanguage, 30);
        Assert.assertTrue(crmSearchProvidersAdvancedSearchUIPage.getlanguage.getText().equalsIgnoreCase(lang));

    }
    @Then("Verify that results are retrieved with respect to pcpindicator {string}")
    public void verify_that_results_are_retrieved_with_respect_to_pcpindicator(String pcpindicator) {
        // Write code here that turns the phrase above into concrete actions
        waitForVisibility(crmSearchProvidersAdvancedSearchUIPage.pcpindicator, 30);
        Assert.assertTrue(crmSearchProvidersAdvancedSearchUIPage.getpcpindicator.getText().equalsIgnoreCase(pcpindicator));

    }

    @Then("Verify that results are retrieved with respect to obindicator {string}")
    public void verify_that_results_are_retrieved_with_respect_to_obindicator(String obindicator) {
        // Write code here that turns the phrase above into concrete actions
        waitForVisibility(crmSearchProvidersAdvancedSearchUIPage.selectobindicator, 30);
        Assert.assertTrue(crmSearchProvidersAdvancedSearchUIPage.selectobindicator.getText().equalsIgnoreCase(obindicator));

    }
    @Then("Verify that results are retrieved with respect to accept new patient {string}")
    public void verify_that_results_are_retrieved_with_respect_to_accept_new_patient(String patient) {
        // Write code here that turns the phrase above into concrete actions
        waitForVisibility(crmSearchProvidersAdvancedSearchUIPage.providerAcceptNewPatient, 30);
        Assert.assertTrue(crmSearchProvidersAdvancedSearchUIPage.providerAcceptNewPatient.getText().equalsIgnoreCase(patient));

    }
    @Then("Verify that results are retrieved with respect to gender served {string}")
    public void verify_that_results_are_retrieved_with_respect_togender_served(String genderserved) {
        // Write code here that turns the phrase above into concrete actions
        waitForVisibility(crmSearchProvidersAdvancedSearchUIPage.getgenderserved, 30);
        if(genderserved.equals("Both")){
            Assert.assertTrue(crmSearchProvidersAdvancedSearchUIPage.getgenderserved.getText().equalsIgnoreCase("Male") | crmSearchProvidersAdvancedSearchUIPage.getgenderserved.getText().equalsIgnoreCase("Female") | crmSearchProvidersAdvancedSearchUIPage.getgenderserved.getText().equalsIgnoreCase("-- --"));
        }
        else{
            Assert.assertTrue(crmSearchProvidersAdvancedSearchUIPage.getgenderserved.getText().equalsIgnoreCase(genderserved));

        }

    }
    @Then("Verify that results are retrieved with respect to provider gender {string}")
    public void verify_that_results_are_retrieved_with_respect_to_provider_gender(String providergender) {
        // Write code here that turns the phrase above into concrete actions
        waitForVisibility(crmSearchProvidersAdvancedSearchUIPage.getprovidergender, 30);

        Assert.assertTrue(crmSearchProvidersAdvancedSearchUIPage.getprovidergender.getText().equalsIgnoreCase(providergender));


    }
    @Then("Verify that results are retrieved with respect to county {string}")
    public void verify_that_results_are_retrieved_with_respect_to_county(String county) {
        // Write code here that turns the phrase above into concrete actions
        waitForVisibility(crmSearchProvidersAdvancedSearchUIPage.getprovidergender, 30);

            Assert.assertTrue(crmSearchProvidersAdvancedSearchUIPage.getaddress.getText().contains(county));


    }
    @Then("Verify that results are retrieved with respect to state {string}")
    public void verify_that_results_are_retrieved_with_respect_to_state(String state) {
        // Write code here that turns the phrase above into concrete actions
        waitForVisibility(crmSearchProvidersAdvancedSearchUIPage.getaddress, 30);
        Assert.assertTrue(crmSearchProvidersAdvancedSearchUIPage.getaddress.getText().contains(state));


    }
    @Given("Verify min age should not accept more then three number")
    public void VerifyminageLength( ) throws InterruptedException {
        crmSearchProvidersAdvancedSearchUIPage.enterminage.sendKeys("1234");

        waitFor(5);
        String str=crmSearchProvidersAdvancedSearchUIPage.enterminage.getAttribute("value");
        waitFor(5);

        Assert.assertTrue(str.length()==3);
        crmSearchProvidersAdvancedSearchUIPage.enterminage.clear();

    }
    @Given("User enter min age {string}")
    public void user_min_age(String age) {

        waitFor(5);
        crmSearchProvidersAdvancedSearchUIPage.enterminage.sendKeys(age);

    }

    @Then("Verify that results are retrieved with respect to min age {string}")
    public void verify_that_results_are_retrieved_with_respect_to_min_age(String age) {
        // Write code here that turns the phrase above into concrete actions
        waitForVisibility(crmSearchProvidersAdvancedSearchUIPage.getPatientminage, 30);
        String getage=crmSearchProvidersAdvancedSearchUIPage.getPatientminage.getText();
        int resultage = Integer.parseInt(getage);
        int searchedage = Integer.parseInt(age);
        Assert.assertTrue(resultage>=searchedage);


    }
    @Given("User enter max age {string}")
    public void user_max_age(String age) {


        waitFor(5);
        crmSearchProvidersAdvancedSearchUIPage.entermaxage.sendKeys(age);

    }

    @Then("Verify that results are retrieved with respect to max age {string}")
    public void verify_that_results_are_retrieved_with_respect_to_max_age(String age) {
        // Write code here that turns the phrase above into concrete actions
        waitForVisibility(crmSearchProvidersAdvancedSearchUIPage.getPatientmaxage, 30);
        String getage=crmSearchProvidersAdvancedSearchUIPage.getPatientmaxage.getText();
        int resultage = Integer.parseInt(getage);
        int searchedage = Integer.parseInt(age);
        Assert.assertTrue(resultage<=searchedage);

    }

    @Given("User enter npi {string}")
    public void user_pi(String npi) {

        waitFor(5);
        crmSearchProvidersAdvancedSearchUIPage.enternpi.sendKeys(npi);

    }
    @Then("Click on cancel button")
    public void cancel_button( ) {

        waitFor(5);
        crmSearchProvidersAdvancedSearchUIPage.CancelButton.click();

    }

    @Then("Verify that results are retrieved with respect to npi {string}")
    public void verify_that_results_are_retrieved_with_respect_to_npi(String npi) {
        // Write code here that turns the phrase above into concrete actions
        waitForVisibility(crmSearchProvidersAdvancedSearchUIPage.getNPI1, 30);
        Assert.assertTrue(crmSearchProvidersAdvancedSearchUIPage.getNPI1.getText().contains(npi));

    }
    @Then("Verify that search is clear {string}")
    public void ClearSearch(String npi ) throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
       waitFor(5);
        String getvalue= crmSearchProvidersAdvancedSearchUIPage.enternpi.getAttribute("value");
        Assert.assertNotEquals(getvalue, npi);

    }

    @Then("Verify that results are retrieved with respect to {string}, {string}, {string}")
    public void verify_that_results_are_retrieved_with_respect_to_and(String lang, String pcpindicator, String gender) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertTrue(crmSearchProvidersAdvancedSearchUIPage.getlanguage.getText().equalsIgnoreCase(lang));
        Assert.assertTrue(crmSearchProvidersAdvancedSearchUIPage.getpcpindicator.getText().equalsIgnoreCase(pcpindicator));
        Assert.assertTrue(crmSearchProvidersAdvancedSearchUIPage.getprovidergender.getText().equalsIgnoreCase(gender));

    }

    @Then("Verify that warning pop up with minimum 2 search parameters required")
    public void verify_that_warning_pop() {
        // Write code here that turns the phrase above into concrete actions
      String warningMsg ="Please enter minimum 2 search criteria";
        Assert.assertTrue(crmSearchProvidersAdvancedSearchUIPage.getErrorMsgPopup.getText().equalsIgnoreCase(warningMsg));
        Assert.assertTrue(crmSearchProvidersAdvancedSearchUIPage.getErrorMsg.getText().equalsIgnoreCase("Error Message"));

    }





}

