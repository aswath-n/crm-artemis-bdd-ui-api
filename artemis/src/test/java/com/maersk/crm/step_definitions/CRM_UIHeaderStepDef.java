package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.tm.TMListOfProjectsPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class CRM_UIHeaderStepDef  extends CRMUtilities implements ApiBase {
    CRMContactRecordUIPage contactRecord=new CRMContactRecordUIPage();
    TMListOfProjectsPage tmListOfProjectsPage=new TMListOfProjectsPage();

    @When("I click on Hamburger Menu I should see the create Task button and I should be able to click ")
    public void i_click_on_creating_a_task_i_should_see_the_hamburger_menu_and_options(){
        navigateToHamBurgerMenu("CREATEATASK");
    }

    @When("I click on the Hamburger Menu ")
    public void i_click_on_the_hamburger_menu(){
        contactRecord.hamBurgerMenu.click();

    }
//TODO Remove the following method as those are duplicated and do mon compile
//    @Then("I should see all the options present")
//    public void i_should_see_all_the_options_present(){
//        waitForVisibility(contactRecord.createATask,2);
//        Assert.assertTrue(contactRecord.createATask.isDisplayed());
//        waitForVisibility(contactRecord.moreOption,2);
//        Assert.assertTrue(contactRecord.moreOption.isDisplayed());
//
//    }

//    @And("I should see the Username should be present")
//    public void i_should_see_the_username_should_be_present(){
//        Assert.assertTrue(contactRecord.headerUsername.isDisplayed());
//
//    }

//    @And("I should see the Role present in the header")
//    public void i_should_see_the_role_present_in_the_header(){
//        Assert.assertTrue(contactRecord.headerRole.isDisplayed());
//
//    }

//    @And("I should see the Current Date and Time Displayed")
//    public void i_should_see_the_current_date_time_displayed(){
//        String actualcontDate=contactRecord.headerDate.getText();
//        String actaulTime=contactRecord.headerTime.getText();
//        String expectedDate= getCurrentDate();
//        String expectedTime=timeNow();
//        Assert.assertTrue(contactRecord.headerID.isDisplayed());
//        Assert.assertTrue(contactRecord.headerTime.isDisplayed());
//        Assert.assertEquals(actaulTime,expectedTime);
//        Assert.assertTrue(contactRecord.headerDate.isDisplayed());
//        Assert.assertEquals(actualcontDate,expectedDate);
//
//    }

    @And(" I should see the office Address displayed in the bottom")
    public void i_should_see_the_office_address_displayed_in_the_bottom(){
        Assert.assertTrue(tmListOfProjectsPage.addressLine1.isDisplayed());
        highLightElement(tmListOfProjectsPage.addressLine1);
        Assert.assertTrue(tmListOfProjectsPage.getAddressLine2.isDisplayed());
        highLightElement(tmListOfProjectsPage.getAddressLine2);
    }

}
