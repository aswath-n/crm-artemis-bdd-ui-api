package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMActiveContactPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.*;

public class CRM_CORE_ReasonsPanelStepDef extends BrowserUtils {

    CRMActiveContactPage activeContactPage = new CRMActiveContactPage();

    @And("I click on {string} Field in Active Contact")
    public void iClickOnFieldInActiveContact(String type) {
        waitFor(2);
        switch (type) {
            case "CONTACT REASONS":
                activeContactPage.contactRes.click();
                break;
            default:
                fail("Mismatch in provided case");
        }
        waitFor(1);
    }

    @Then("I validate the {string} select dropdown with the following select values in the REASONS panel")
    public void iValidateTheSelectDropdownWithTheFollowingSelectValuesInTheREASONSPanel(String dropdownType, List<String> data) {
        List<String> expectedList = new ArrayList<>(data);
        Collections.sort(expectedList);
        switch (dropdownType) {
            case "CONTACT REASONS":
                activeContactPage.contactRes.click();
                assertEquals(getActualDropdownValues(), expectedList, "Mismatch in Actual and Expected Dropdown list for : " + dropdownType);
                break;
            default:
                fail("Mismatch in provided case");
        }
    }

    public List<String> getActualDropdownValues() {
        waitFor(2);
        List<String> actualList = new ArrayList<>();
        List<WebElement> actualWebList = new ArrayList<>(Driver.getDriver().findElements(By.xpath("//ul[@role='listbox']/li")));
        actualWebList.forEach(each -> actualList.add(each.getText()));
        Collections.sort(actualList);
        waitFor(2);
        Driver.getDriver().findElement(By.xpath("//body")).click();
        return actualList;
    }
}
