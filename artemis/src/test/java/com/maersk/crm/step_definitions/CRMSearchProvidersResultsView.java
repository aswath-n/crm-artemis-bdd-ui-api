package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.CRMSearchProvidersResultViewPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class CRMSearchProvidersResultsView extends CRMUtilities implements ApiBase {

    //replaces the thread.sleep with wait for

    CRMSearchProvidersResultViewPage crmSearchProvidersResultViewPage = new CRMSearchProvidersResultViewPage();
    //we follow be convenetion for the page object
    CRMSearchProvidersResultViewPage searchProvidersResultViewPage = new CRMSearchProvidersResultViewPage();
    public static final ThreadLocal<String> recordCount = ThreadLocal.withInitial(String::new);

    @Given("I am on the search screen")
    public void I_am_on_the_search_provider_screen() throws InterruptedException {

        waitFor(12);
        jsClick(crmSearchProvidersResultViewPage.searchProvider);
        waitFor(5);

    }

    @Given("I search by {string} firstname")
    public void SearchbyFirstName(String name) throws InterruptedException {
        crmSearchProvidersResultViewPage.providerfirstname.sendKeys(name);
    }
    @Given("I search by {string} lastname")
    public void SearchbyLastName(String name) throws InterruptedException {
        crmSearchProvidersResultViewPage.providerlastname.sendKeys(name);
    }
    @Then("I click on Search button")
    public void ClickonSearchButton() throws InterruptedException {
        crmSearchProvidersResultViewPage.searchButton.click();
    }
    @Then("I will see these columns: {string}, {string}, {string}, {string}, {string}, {string} and {string}")
    public void i_will_see_these_columns_and(String providername, String provideraddress, String planname, String programname,String providertype, String speciality, String lastupdated) {

        waitFor(3);

        Assert.assertTrue(crmSearchProvidersResultViewPage.providername.getText().contains(providername));

        Assert.assertTrue(crmSearchProvidersResultViewPage.provideraddress.getText().contains(provideraddress));

//        Assert.assertTrue(crmSearchProvidersResultViewPage.groupname.getText().contains(groupname)); --------Implementation change

        Assert.assertTrue(crmSearchProvidersResultViewPage.planname.getText().contains(planname));

        Assert.assertTrue(crmSearchProvidersResultViewPage.programname.getText().contains(programname));

        Assert.assertTrue(crmSearchProvidersResultViewPage.providertypecol.getText().contains(providertype));

        Assert.assertTrue(crmSearchProvidersResultViewPage.speciality.getText().contains(speciality));

        Assert.assertTrue(crmSearchProvidersResultViewPage.lastupdate.getText().contains(lastupdated));

    }

    @When("I click the additional address icon")
    public void i_click_the_additional_address_icon() {
        // Write code here that turns the phrase above into concrete actions
    waitFor(2);
        scrollDown();
                crmSearchProvidersResultViewPage.additionaladdressicon.click();
        waitFor(3);
    }
    @Then("I will see an icon with the number of additional addresses that match the search criteria")
    public void i_will_see_an_icon_with_the_number_of_additional_addresses_that_match_the_search_criteria() {
        // Write code here that turns the phrase above into concrete actions
        waitFor(3);
        Assert.assertTrue(crmSearchProvidersResultViewPage.additionaladdressicon.isDisplayed());
    }

    @Then("I will see the list of locations")
    public void i_will_see_the_list_of_locations() {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertTrue(crmSearchProvidersResultViewPage.tooltipaddress.isDisplayed());
    }

    @Then("I will see the list of locations with address and phone no")
    public void i_will_see_the_list_of_locations_with_address_and_phone_no() {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertTrue(crmSearchProvidersResultViewPage.tooltipaddress.isDisplayed());
        Assert.assertTrue(crmSearchProvidersResultViewPage.tooltipaddressphone.isDisplayed());
    }

    @When("I select the carrot to the left of Provider Name")
    public void i_select_the_carrot_to_the_left_of_Provider_Name() throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions

        waitFor(10);
     //   crmSearchProvidersResultViewPage.expandproviderdetail.click();
        jsClick( crmSearchProvidersResultViewPage.expandproviderdetail);
        waitFor(5);
    }

    @Then("the results will expand or collapse")
    public void the_results_will_expand_or_collapse() {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertTrue(crmSearchProvidersResultViewPage.language.isDisplayed());
        waitFor(5);
        crmSearchProvidersResultViewPage.collapseproviderdetail.click();
        waitFor(5);
        Assert.assertFalse(crmSearchProvidersResultViewPage.language.isDisplayed());


    }
    @Then("I will see these elements: {string}, {string}, {string}, {string}, {string}, {string} and {string}")
    public void i_will_see_these_elements_and(String language, String pci_indicator, String ob_indicator, String gender_served, String provider_gender, String ageRange ,String npi) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertTrue(crmSearchProvidersResultViewPage.language.getText().equalsIgnoreCase(language));
        Assert.assertTrue(crmSearchProvidersResultViewPage.pciindicator.getText().equalsIgnoreCase(pci_indicator));

        Assert.assertTrue(crmSearchProvidersResultViewPage.obindicator.getText().equalsIgnoreCase(ob_indicator));

        Assert.assertTrue(crmSearchProvidersResultViewPage.genderserved.getText().equalsIgnoreCase(gender_served));

        Assert.assertTrue(crmSearchProvidersResultViewPage.providergender.getText().equalsIgnoreCase(provider_gender));

//        Assert.assertTrue(crmSearchProvidersResultViewPage.patientminage.getText().equalsIgnoreCase(patient_min_age));
//
//        Assert.assertTrue(crmSearchProvidersResultViewPage.patientmaxage.getText().equalsIgnoreCase(patient_max_age));

        Assert.assertTrue(crmSearchProvidersResultViewPage.ageRangeExpandedView.getText().equalsIgnoreCase(ageRange));

        Assert.assertTrue(crmSearchProvidersResultViewPage.npiExpandedView.getText().equalsIgnoreCase(npi));

    }
    @When("I entered Char {string} in FirstName")
    public void i_entered_in_FirstName(String str) {
        crmSearchProvidersResultViewPage.providerfirstname.sendKeys(str);
    }


    @When("I click the cancel button")
    public void i_select_the_cancel_button() {
        crmSearchProvidersResultViewPage.CancelButton.click();
    }

    @Then("the criteria entered will be cleared and I will remain on the same screen {string}")
    public void the_criteria_entered_will_be_cleared_and_I_will_remain_on_the_same_screen(String fn) throws InterruptedException {
        waitFor(5);
        waitForVisibility(crmSearchProvidersResultViewPage.providerfirstname,30);
        System.out.println("Prinitting name"+crmSearchProvidersResultViewPage.providerfirstname.getAttribute("value") +"  "+fn);
        Assert.assertNotEquals(crmSearchProvidersResultViewPage.providerfirstname.getAttribute("value"), fn);
    }
    @Then("I will see a message {string}")
    public void i_will_see_a_message(String string) throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions

        //waitFor(7);
        waitForVisibility(crmSearchProvidersResultViewPage.resultCount,30);
        String str = crmSearchProvidersResultViewPage.resultCount.getText();
        Assert.assertTrue(str.contains("Returning 100 of") && str.contains("providers found, use more search criteria for more precise results"));
    }

    @Then("I verify warning message shows correct number of Provider Records")
    public void i_verify_warning_message_shows_correct_number_of_provider_records() {
       recordCount.set(crmSearchProvidersResultViewPage.resultCount.getText().split(" of ")[1].split(" ")[0]);
        Assert.assertTrue(Integer.parseInt(recordCount.get())>100,"Wrong Error Message displayed");
    }

    @Then("I will see {int} provider records in the page of the {int} results returned")
    public void i_will_see_provider_records_in_the_page_of_the_results_returned(int expected, int int2) throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
       // Thread.sleep(5000);
        waitFor(6);
        int actual = crmSearchProvidersResultViewPage.rowcount.size();
        Assert.assertEquals(actual, expected);
    }


    @Then("I will have the option to select {int}, {int}, or {int} per page from the {int} results returned")
    public void i_will_have_the_option_to_select_or_per_page_from_the_results_returned(int page1, int page2, int page3, int int4) throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions

        waitFor(15);
        crmSearchProvidersResultViewPage.itemsPerPage.click();
        crmSearchProvidersResultViewPage.select40items.click();

        waitFor(10);
        int actual = crmSearchProvidersResultViewPage.rowcount.size();
        Assert.assertEquals(actual, page2);

        crmSearchProvidersResultViewPage.itemsPerPage.click();
        crmSearchProvidersResultViewPage.select60items.click();
        waitFor(10);
        waitFor(10);

        actual = crmSearchProvidersResultViewPage.rowcount.size();
        Assert.assertEquals(actual, page3);


    }

    @Then("I will verify pagination works correctly")
    public void i_will_verify_pagination_works_correctly()  {
        List<WebElement> pagination = Driver.getDriver().findElements(By.xpath("//ul[@class='pagination']//li"));
        int s = pagination.size();
        for(int i=1;i<=s;i++){
           Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']//li["+i+"]")).click();
           waitFor(2);
        }
    }

    @Then("I will be able to sort the search results by each column header in ascending or descending order")
    public void i_will_be_able_to_sort_the_search_results_by_each_column_header_in_ascending_or_descending_order() throws InterruptedException {

        waitFor(15);
        List<WebElement> spec1=crmSearchProvidersResultViewPage.getNameList;
        List<String > before= new ArrayList<>();
        List<String > after= new ArrayList<>();
        for(WebElement e: spec1)
        {
            before.add(e.getText());
        }
        crmSearchProvidersResultViewPage.sortbyname.click();
        for(WebElement e: spec1)
        {
            after.add(e.getText());
        }

        assertNotEquals(before,after);

        waitFor(2);

        List<WebElement> address=crmSearchProvidersResultViewPage.getAddressList;
        List<String > before2= new ArrayList<>();
        List<String > after2= new ArrayList<>();
        for(WebElement e: address)
        {
            before2.add(e.getText());
        }
        crmSearchProvidersResultViewPage.sortbyaddress.click();
        for(WebElement e: address)
        {
            after2.add(e.getText());
        }
        assertNotEquals(before2,after2);

        waitFor(2);

        List<WebElement> names=crmSearchProvidersResultViewPage.getSpecialityList;
        List<String > before1= new ArrayList<>();
        List<String > after1= new ArrayList<>();
        for(WebElement e: names)
        {
            before1.add(e.getText());
        }
        crmSearchProvidersResultViewPage.sortbyspecialaty.click();
        for(WebElement e: names)
        {
            after1.add(e.getText());
        }
        assertNotEquals(before1,after1);


    }


    @Then("I will verify the accept new patient icon on the provider result table {string}")
    public void verifyAceeptNewPatientsInProvieResultTable(String color)  {
        scrollDown();
        scrollDown();
        Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//*[contains(@title, 'Yes Accepting New Patients')]")).isDisplayed());
        verifyColorOfElement(Driver.getDriver().findElement(By.xpath("//*[contains(@title, 'Yes Accepting New Patients')]")), color);
    }


    @Then("I will verify the accept new patient icon on the provider additional address list {string}")
    public void verifyListAceeptNewPatientsInProvieResultTable(String color)  {
        for(int i=0;i<crmSearchProvidersResultViewPage.acceptingNewPatientsicon.size()-1;i++){
        Assert.assertTrue(crmSearchProvidersResultViewPage.acceptingNewPatientsicon.get(i).isDisplayed());
        verifyColorOfElement(crmSearchProvidersResultViewPage.acceptingNewPatientsicon.get(i), color);
        }}

        @Then("I will verify the No accept new patient icon on the provider additional address {string}")
        public void i_will_verify_the_no_new_accept_patient_icon_on_the_provider_result_table_something( String color) {
            Assert.assertTrue(crmSearchProvidersResultViewPage.noAceeptPatientIcon.isDisplayed());
            verifyColorOfElement(crmSearchProvidersResultViewPage.noAceeptPatientIcon, color);
        }

    @Then("I will verify the No accept new patient icon on the provider additional address list {string}")
    public void verifyListNoAceeptNewPatientsInProvieResultTable(String color)  {

        for(int i=0;i<crmSearchProvidersResultViewPage.noAcceptingNewPatientsicon.size()-1;i++){
            Assert.assertTrue(crmSearchProvidersResultViewPage.noAcceptingNewPatientsicon.get(i).isDisplayed());
            verifyColorOfElement(crmSearchProvidersResultViewPage.noAcceptingNewPatientsicon.get(i), color);
        }}

}

