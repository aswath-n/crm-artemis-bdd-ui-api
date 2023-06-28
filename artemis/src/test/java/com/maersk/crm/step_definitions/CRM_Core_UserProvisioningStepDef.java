package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMActiveContactPage;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

public class CRM_Core_UserProvisioningStepDef extends CRMUtilities implements ApiBase {

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMActiveContactPage activeContactPage = new CRMActiveContactPage();


    @Given("I click on configure button for user provisioning")
    public void i_click_on_configure_button_for_user_provisioning() {
        contactRecord.logOutArrow.click();
        contactRecord.configureUserProvisioning.click();
    }

    @Given("I verify user provisioning screen is displayed")
    public void i_verify_user_provisioning_screen_is_displayed() {
        contactRecord.userListHeader.isDisplayed();
        contactRecord.maerskIDSearch.isDisplayed();

    }

    @Given("I click on configure button and select Look Up")
    public void i_click_on_configure_button_and_select_LookUp() {
        contactRecord.logOutArrow.click();
        contactRecord.configureUserProvisioning.click();
        waitFor(2);
        Actions a =new Actions(Driver.getDriver());
        a.sendKeys(Keys.ESCAPE).perform();
        waitFor(2);
        jsClick(contactRecord.lookUpOption);
    }
    @And("I click on background screen")
    public void i_click_on_background_screen() {
        contactRecord.logOutArrow.click();
        waitFor(5);
        contactRecord.lookUpOption.click();
        waitFor(2);

    }

}
