package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMSearchProviderPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.testng.Assert;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class CRMSearchProvidersUIStepDef extends CRMUtilities implements ApiBase {


    CRMSearchProviderPage crmSearchProviderPage = new CRMSearchProviderPage();


    @Given("I am on the search provider screen")
    public void I_am_on_the_search_provider_screen() throws InterruptedException {
        waitFor(10);
        jsClick(crmSearchProviderPage.searchProvider);
        waitFor(5);

    }

    @Given("I search by {string} parameter")
    public void SearchbyFirstName(String name) throws InterruptedException {
        crmSearchProviderPage.providerfirstname.sendKeys(name);
    }
    @Given("I search by providertype {string} parameter")
    public void SearchbyProviderType(String provider) throws InterruptedException {
        selectDropDown(crmSearchProviderPage.providertype, provider);
    }


    @Given("I search by {string}")
    public void SearchbyLtName(String name) throws InterruptedException {
        crmSearchProviderPage.providerlastname.sendKeys(name);
    }
    @Given("I search by speciality {string}")
    public void SearchbySpeciality(String speciality) throws InterruptedException {
        crmSearchProviderPage.selectspeciality.click();
        waitFor(5);
        crmSearchProviderPage.selectspeciality.sendKeys(speciality);
        waitFor(2);

        waitFor(5);
        crmSearchProviderPage.selectspeciality.sendKeys(Keys.ENTER);
    }

    @When("I entered {string} and {string}")
    public void SearchWithMultipleParameter(String fname, String lname) throws InterruptedException {
        crmSearchProviderPage.providerfirstname.sendKeys(fname);
        crmSearchProviderPage.providerlastname.sendKeys(lname);

    }

    @Given("I search with groupname {string} parameter")
    public void SearchwithGroup(String gname) throws InterruptedException {
        crmSearchProviderPage.providergroupname.sendKeys(gname);
    }

    @Given("I search by {string} Phone")
    public void SearchByPhone(String phone) throws InterruptedException {
        crmSearchProviderPage.ProviderPhoneno.sendKeys(phone);
    }

    @Given("I search by {string} PName")
    public void SearchByPlanName(String PName) throws InterruptedException {
        crmSearchProviderPage.OpenCombo.click();
        waitFor(5);
        crmSearchProviderPage.SelectOption.sendKeys(PName);
        waitFor(2);

        waitFor(5);
        crmSearchProviderPage.SelectOption.sendKeys(Keys.ENTER);

    }


    @Given("I search by {string} Providertype")
    public void SearchbyProvidertype(String ptype) throws InterruptedException {
        //  tmSeachPage.providerfirstname.sendKeys(ptype);
    }

    @Then("I click on the Search button")
    public void ClickonSearchButton() throws InterruptedException {
        crmSearchProviderPage.searchButton.click();
    }

    @And("I verify that results are fetched for the {string}")
    public void VerifyResult(String firstname) throws InterruptedException {
        System.out.println(firstname);
        waitFor(15);
        String name = crmSearchProviderPage.getName.getText();
        String[] arr = name.split(" ");
        String fname = arr[0];
        //Assert.assertTrue(fname.equalsIgnoreCase(firstname) && fname.matches("^[a-zA-Z]*$") && fname.length() < 15);
        Assert.assertTrue(fname.toLowerCase().contains(firstname.toLowerCase()) && fname.matches("^[a-zA-Z]*$") && fname.length()<15 );

    }


    @And("I verify that results are fetched for the speciality {string}")
    public void VerifySpecialityResult(String speciality) throws InterruptedException {
        waitForVisibility(crmSearchProviderPage.getSpeciality, 45);
        String specialityText = crmSearchProviderPage.getSpeciality.getText();
        Assert.assertTrue(specialityText.equalsIgnoreCase(speciality));


    }

    @And("Verify that results are fetched for the {string}")
    public void VerifyLastNameResult(String lname) throws InterruptedException {
        waitFor(15);
        String Fullname = crmSearchProviderPage.getName.getText().toLowerCase();
        String lastWord = Fullname.substring(Fullname.lastIndexOf(" ") + 1);
        System.out.println("Printng  "+ Fullname +" "+ lastWord+"  "+lname);
        assertTrue(lastWord.contains(lname.toLowerCase()),"Doest match last name");
        //Assert.assertTrue(lastWord.contains(lname) && lastWord.contains("^[a-zA-Z]*$") && lastWord.length() < 35);
        Assert.assertTrue(lastWord.toLowerCase().contains(lname.toLowerCase()) && lastWord.matches("^[a-zA-Z]*$") && lastWord.length()<35 );


    }

    @Given("I verify that results are fetched for the group name {string}")
    public void i_verify_that_results_are_fetched_for_the_group_name(String groupname) {
        // Write code here that turns the phrase above into concrete actions
        waitFor(15);
        String GroupName = crmSearchProviderPage.getgroupname.getText();
//        Assert.assertTrue(GroupName.contains(groupname) && GroupName.matches("^[a-zA-Z]*$") && GroupName.length() < 50);
        Assert.assertTrue(GroupName.toLowerCase().contains(groupname.toLowerCase()) && GroupName.matches("^[a-zA-Z]*$") && GroupName.length()<50 );

    }

    @Then("I Verify results for {string} and {string}")
    public void i_Verify_result_for_first_and_last_name(String FirstName, String lname) throws InterruptedException {
        waitFor(15);
        String name = crmSearchProviderPage.getName.getText();
        String[] arr = name.split(" ");
        String fname = arr[0];
        Assert.assertTrue(fname.equalsIgnoreCase(FirstName) && fname.matches("^[a-zA-Z]*$") && fname.length() < 15);
        String Fullname = crmSearchProviderPage.getName.getText();
        String lastWord = Fullname.substring(Fullname.lastIndexOf(" ") + 1);
        Assert.assertTrue(lastWord.equalsIgnoreCase(lname) && lastWord.matches("^[a-zA-Z]*$") && lastWord.length() < 35);

    }

    @And("I verify that results are fetched for the {string} PName")
    public void VerifyResultPlanName(String planname) throws InterruptedException {
        waitForVisibility(crmSearchProviderPage.getplanname, 30);
        String name = crmSearchProviderPage.getplanname.getText();
        Assert.assertTrue(name.contains(planname));

    }

    @And("I verify that results are fetched for the Providertype")
    public void VerifyResultProvidertype() throws InterruptedException {
     String  provider = "Medical";
        waitFor(15);
        String pname = crmSearchProviderPage.getProviderType.getText();
        System.out.println(pname);
        Assert.assertTrue(pname.equalsIgnoreCase(provider));

    }


    @And("I verify results are fetched for the {string}")
    public void VerifyPhoneNo(String phone) throws InterruptedException {

        String Address = crmSearchProviderPage.getAddress.getText();

        Address = Address.replace(" ", "");
        phone = phone.replace(" ", "");

        Assert.assertTrue(Address.contains(phone));

    }

    @When("I entered {string} in FirstName")
    public void i_entered_in_FirstName(String str) {
        crmSearchProviderPage.providerfirstname.sendKeys(str);
    }

    @Then("I will return only the first {int} provider results found")
    public void i_will_return_only_the_first_provider_results_found(Integer no) throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        waitForVisibility(crmSearchProviderPage.resultCount, 15);
        String str = crmSearchProviderPage.resultCount.getText();
        Assert.assertTrue(str.contains("Returning 100 of"));
    }

    @Then("I will return providers with a name that starts with the text entered in the search {string}")
    public void i_will_return_providers_with_a_name_that_starts_with_the_text_entered_in_the_search(String fn) {
        String name = crmSearchProviderPage.getName.getText();

        Assert.assertTrue(name.charAt(0) == fn.charAt(0));
    }

    @Given("I’ve entered data into the {string}, {string}, {string}, and {string} field")
    public void i_ve_entered_data_into_the_and_field(String planename, String provider, String speciality, String phone) throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        crmSearchProviderPage.OpenCombo.click();

        waitFor(5);

        crmSearchProviderPage.SelectOption.sendKeys(planename);

        waitFor(5);
        crmSearchProviderPage.SelectOption.sendKeys(Keys.ENTER);
        waitFor(5);

        selectDropDown(crmSearchProviderPage.providertype, provider);

        crmSearchProviderPage.ProviderPhoneno.sendKeys(phone);

        jsClick(Driver.getDriver().findElement(By.xpath("(//button[contains(@aria-label, 'Clear')])[2]")));
        waitFor(5);

        crmSearchProviderPage.selectspeciality.sendKeys(speciality);
        waitFor(5);

        crmSearchProviderPage.selectspeciality.sendKeys(Keys.DOWN);
        waitFor(5);
        crmSearchProviderPage.selectspeciality.sendKeys(Keys.ENTER);
        waitFor(5);

    }

    @Then("I will return providers with the exact match to the criteria entered {string}, {string}, {string}, and {string}")
    public void i_will_return_providers_with_the_exact_match_to_the_criteria_entered(String planname, String provider, String speciality, String phone) throws InterruptedException {

        waitFor(15);
        String plannametext = crmSearchProviderPage.getplanname.getText();
        Assert.assertTrue(plannametext.contains(planname));

        String providername = crmSearchProviderPage.getProviderType.getText();
        Assert.assertTrue(providername.equalsIgnoreCase(provider));

        String specialityText = crmSearchProviderPage.getSpeciality.getText();
        Assert.assertTrue(specialityText.equalsIgnoreCase(speciality));

        String Address = crmSearchProviderPage.getAddress.getText();

        Address = Address.replace(" ", "");
        phone = phone.replace(" ", "");

        Assert.assertTrue(Address.contains(phone));


    }


    @When("Click at Advance Search button")
    public void click_at_Advance_Search_button() throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        waitFor(5);
        jsClick(crmSearchProviderPage.advancesearch);

    }


    @When("I search by {string} pcp")
    public void i_search_by_pcp(String status) throws InterruptedException {
        waitFor(5);
        selectDropDown(crmSearchProviderPage.pcpindicator, status);

    }

    @Then("I will return providers who have a {string} of Y")
    public void i_will_return_providers_who_have_a_of_Y(String str) {

        waitFor(30);
        Driver.getDriver().findElement(By.xpath("//i[contains(text(), 'chevron_right')]")).click();
        Driver.getDriver().findElement(By.xpath("//p[contains(text(), 'PCP INDICATOR')]/following-sibling::p")).getText().contains(str);
    }

}

