package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APIPlanManagementController;
import com.maersk.crm.pages.tm.TMConfigurationPage;
import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.pages.tm.TMProjectHolidaysPage;
import com.maersk.crm.pages.tm.TMSearchRegionInfoPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TMUploadPlanConfigurationFileStepDef extends BrowserUtils {
    TMConfigurationPage tmConfigurationPage = new TMConfigurationPage();


    @Then("I see an option to upload a file to configure the different Plans for the project")
    public void i_see_an_option_to_upload_a_file_to_configure_the_different_Plans_for_the_project() {
        click ( tmConfigurationPage.planConfigTab );
        Assert.assertTrue ( "Plans Config Header is not displayed", tmConfigurationPage.plansTabHdr.isDisplayed ());
        Assert.assertTrue ("Upload button is not displayed",tmConfigurationPage.plansTabUpload.isDisplayed () );
        Assert.assertTrue ( "Attach file button is not displayed", tmConfigurationPage.planAttachFile.isDisplayed ());
        Assert.assertTrue ( "Cancel button is not displayed",tmConfigurationPage.plansTabCancelBtn.isDisplayed ());
        Assert.assertTrue( " Back Arrow is not displayed", tmConfigurationPage.backArrow.isDisplayed());
    }

    @Then("I have chosen a file to upload")
    public void i_have_chosen_a_file_to_upload() {
        String filePath = System.getProperty("user.dir")+"\\src\\test\\resources\\testData\\tm\\planAndRegionConfig\\Plan_Configuration_Success.xlsx";
        tmConfigurationPage.getPlanAttachFile.sendKeys(filePath);
        waitFor(2);
    }

    @When("I select Cancel button after choosing the file to upload")
    public void i_select_Cancel_button_after_choosing_the_file_to_upload() {
        waitForVisibility(tmConfigurationPage.plansTabCancelBtn,10);
        click(tmConfigurationPage.plansTabCancelBtn);
        waitFor(2);
    }

    @Then("I will receive this Warning Message: {string}")
    public void i_will_receive_this_Warning_Message(String expectedMsg) {
        waitForVisibility(tmConfigurationPage.planTab_warningMsg,10);
        String actualMsg = tmConfigurationPage.planTab_warningMsg.getText();
        Assert.assertEquals("INVALID Warning message",expectedMsg,actualMsg);
    }

    @When("I select the Back Arrow button after choosing the file to upload")
    public void i_select_the_Back_Arrow_button_after_choosing_the_file_to_upload() {
        waitForClickablility(tmConfigurationPage.backArrow,10);
        click(tmConfigurationPage.backArrow);
        waitFor(2);
    }

    @When("I select to navigate away from the plan config screen")
    public void i_select_to_navigate_away_from_the_plan_config_screen() {
        waitForClickablility(tmConfigurationPage.planManagement,10);
        tmConfigurationPage.planManagement.click();
        waitFor(2);
    }

}