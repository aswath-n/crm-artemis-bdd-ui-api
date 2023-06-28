package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMDesignerPage;
import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.pages.tm.TMDesignerPage;
import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class TM_FieldDependencyRules extends BrowserUtils {
    TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();
    TMDesignerPage tmDesignerPage= new TMDesignerPage();

    @Then("I click on Add Rule button")
    public void iClickOnAddRuleButton() {
        Assert.assertTrue(tmDesignerPage.addRuleBtn.isDisplayed(),"ADD RULE BUTTON IS NOT DISPLAYED");
        tmDesignerPage.addRuleBtn.click();
    }

    @And("I provide details to add rule with source field {string} as {string} and source condition {string}")
    public void iProvideDetailsToAddRulenewAndSaveIt(String srcField, String fieldType,String srcCondition) {
        Assert.assertTrue(tmDesignerPage.listSourceFieldDropdown.get(tmDesignerPage.listSourceFieldDropdown.size()-1).isDisplayed());
        waitFor(2);
        selectDropDownWithText(tmDesignerPage.listSourceFieldDropdown.get(tmDesignerPage.listSourceFieldDropdown.size()-1),srcField);
        selectDropDownWithText(tmDesignerPage.ListSourceConditionDropdown.get(tmDesignerPage.ListSourceConditionDropdown.size()-1),srcCondition);
        if (fieldType.equals("dropdown") && srcCondition.equalsIgnoreCase("Not Null"))
        {
            selectRandomOptionFromMultiSelectDropDownWithoutEscape(tmDesignerPage.FieldValuesDropdown.get(tmDesignerPage.FieldValuesDropdown.size()-1));
        }
    }
    @And("I provide details to add rule with destination field {string} as {string} and destination condition {string}")
    public void iProvideDetailsToAddRulenewAndSaveItDestination(String dstField, String fieldType, String dstCondition) {
        Assert.assertTrue(tmDesignerPage.listDependentFieldDropdown.get(tmDesignerPage.listDependentFieldDropdown.size()-1).isDisplayed());
        waitFor(2);
        selectDropDownWithText(tmDesignerPage.listDependentFieldDropdown.get(tmDesignerPage.listDependentFieldDropdown.size()-1),dstField);
        selectDropDownWithText(tmDesignerPage.listDependentConditionDropdown.get(tmDesignerPage.listDependentConditionDropdown.size()-1),dstCondition);
        if (fieldType.equals("dropdown") && dstCondition.equalsIgnoreCase("Not Null"))
        {
            selectRandomOptionFromMultiSelectDropDownWithoutEscape(tmDesignerPage.FieldValuesDropdown.get(tmDesignerPage.FieldValuesDropdown.size()-1));

        }
    }
    @And("I verify the success message got displayed")
    public void iVerifyTheSuccessMessageGotDisplayed() {
        waitFor(3);
        Assert.assertTrue(tmDesignerPage.succesMessage.isDisplayed());
    }
    @Then("I click on save button to save rule")
    public void ICLickOnSaveButton()
    {
        tmDesignerPage.saveRuleButton.click();
    }
    @When("I click on Cancel button on configure rule Page")
    public void i_click_on_Cancel_button_on_Team_User_Page() {
        waitFor(2);
        tmDesignerPage.cancelButton.click();
    }

    @And("I click on cancel button and verify warning message is displayed on cofigure rule page")
    public void iClickOnCancelButtonAndVerifyWarningMessageOnCofigureRulePage() {
        waitFor(2);
        tmDesignerPage.cancelButton.click();
        waitForVisibility(tmDesignerPage.warningMsgonCancelBtn,5);
        Assert.assertTrue(tmDesignerPage.warningMsgonCancelBtn.isDisplayed());
    }
}
