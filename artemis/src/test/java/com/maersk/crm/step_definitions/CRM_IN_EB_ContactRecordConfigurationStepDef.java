package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMActiveContactPage;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.core.exception.CucumberException;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class CRM_IN_EB_ContactRecordConfigurationStepDef extends BrowserUtils {

//
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMActiveContactPage activeContact = new CRMActiveContactPage();

    @Then("Verify consumer Type field for IN_EB with the options")
    public void verify_consumer_Type_field_for_IN_EB_with_the_options(List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("Plan")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Assister")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("One-Time Authorized")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Representative")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Media")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Navigator")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Provider")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Other")) {
                selectDropDown(activeContact.consumerType, opt);
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }
}
