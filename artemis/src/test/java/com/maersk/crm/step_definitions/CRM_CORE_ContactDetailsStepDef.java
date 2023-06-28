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
import java.util.Map;

import static org.testng.Assert.*;

public class CRM_CORE_ContactDetailsStepDef extends BrowserUtils {

    CRMActiveContactPage activeContactPage = new CRMActiveContactPage();


    @And("I validate the following default values are displayed for CONTACT DETAILS")
    public void iValidateTheFollowingDefaultValuesAreDisplayedForCONTACTDETAILS(List<Map<String, String>> datatable) {
        waitFor(1);
        Map<String, String> verifyData = datatable.get(0);
        for (String eachVerifyValue : verifyData.keySet()) {
            switch (eachVerifyValue.toUpperCase()) {
                case "CONTACT TYPE":
                    assertEquals(activeContactPage.contactTypeDropdown.getText(), verifyData.get(eachVerifyValue));
                    break;
                case "CHANNEL":
                    assertEquals(activeContactPage.channelField.getText(), verifyData.get(eachVerifyValue));
                    break;
            }
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


    @Then("I validate the {string} select dropdown with the following select values in the Contact Details panel")
    public void iValidateTheSelectDropdownWithTheFollowingSelectValuesInTheContactDetailsPanel(String dropdownType, List<String> data) {
        List<String> expectedList = new ArrayList<>(data);
        Collections.sort(expectedList);
        switch (dropdownType) {
            case "CONTACT TYPE":
                activeContactPage.contactTypeDropdown.click();
                assertEquals(getActualDropdownValues(), expectedList, "Mismatch in Actual and Expected Dropdown list for : " + dropdownType);
                break;
            case "INBOUND CALL QUEUE":
                activeContactPage.inboundCallQueue.click();
                assertEquals(getActualDropdownValues(), expectedList, "Mismatch in Actual and Expected Dropdown list for : " + dropdownType);
                break;
            case "CHANNEL":
                activeContactPage.channelField.click();
                assertEquals(getActualDropdownValues(), expectedList, "Mismatch in Actual and Expected Dropdown list for : " + dropdownType);
                break;
            case "PROGRAM":
                activeContactPage.programs.click();
                assertEquals(getActualDropdownValues(), expectedList, "Mismatch in Actual and Expected Dropdown list for : " + dropdownType);
                break;
            case "TRANSLATION SERVICE":
                activeContactPage.translationServiceDropDown.click();
                assertEquals(getActualDropdownValues(), expectedList, "Mismatch in Actual and Expected Dropdown list for : " + dropdownType);
                break;
            case "OUTCOME OF CONTACT":
                activeContactPage.outcomeOfContact.click();
                assertEquals(getActualDropdownValues(), expectedList, "Mismatch in Actual and Expected Dropdown list for : " + dropdownType);
                break;
            case "CALL CAMPAIGN":
                activeContactPage.contactCallCampaign.click();
                assertEquals(getActualDropdownValues(), expectedList, "Mismatch in Actual and Expected Dropdown list for : " + dropdownType);
                break;
            default:
                fail("Mismatch in Case value");
        }
    }
}
