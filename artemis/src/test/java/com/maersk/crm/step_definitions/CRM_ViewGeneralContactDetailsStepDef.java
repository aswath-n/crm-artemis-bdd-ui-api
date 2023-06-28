package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import static org.testng.Assert.*;

public class CRM_ViewGeneralContactDetailsStepDef extends CRMUtilities implements ApiBase {

    CRMViewGeneralContactDetailsPage viewGeneralContactDetailsPage = new CRMViewGeneralContactDetailsPage();


    @Then("I verify disposition value {string} in wrap up and close section")
    public void verifyDispositionValueInContactDetailsPage(String dispositionText) {
        Assert.assertEquals(viewGeneralContactDetailsPage.lblDisposition.getText(), dispositionText);
    }

    @Then("I verify phone value {string} in contact details section")
    public void verifyPhoneValueInContactDetailsSection(String expectedPhoneNum) {
        String actualPhoneNum = viewGeneralContactDetailsPage.lblPhone.getText();
        actualPhoneNum = actualPhoneNum.replaceAll("\\D+","");
        Assert.assertEquals(actualPhoneNum, expectedPhoneNum);
    }

    @Then("I verify Task Name {string} in Links section")
    public void verifyTaskNameInLinksSection(String taskName) {
        waitFor(8);
        for (int i=1; i<viewGeneralContactDetailsPage.linksTableList.size(); i++)
        {
            if(viewGeneralContactDetailsPage.linkNames.get(i).getText().equalsIgnoreCase("Task"))
            {
                String jj = viewGeneralContactDetailsPage.linkTypes.get(i).getText();
                assertEquals(viewGeneralContactDetailsPage.linkTypes.get(i).getText(),taskName, "Task Name not matches in Links section");
            }
        }
    }

    @Then("I click Task Id in Links section")
    public void iClickTaskIdInLinksSection() {
        waitFor(3);
        boolean elementPresent = false;
        for (int i=1; i<viewGeneralContactDetailsPage.linksTableList.size(); i++)
        {
            if(viewGeneralContactDetailsPage.linkNames.get(i).getText().equalsIgnoreCase("Task"))
            {
                viewGeneralContactDetailsPage.linkIds.get(i).click();
                elementPresent = true;
            }
        }
        if(!elementPresent)
            fail("Task ID not present");
    }

    @Then("I verify Links section is empty")
    public void verifyLinksSectionIsEmpty() {
        waitFor(8);
        assertEquals(viewGeneralContactDetailsPage.linksTableList.size(), 0, "Task Name not matches in Links section");
    }

    @And("I scroll up the page")
    public void scroll_up_the_page() {
        waitFor(1);
        scrollUpUsingActions(2);
    }

    @And("I scroll down the page")
    public void scroll_down_the_page() {
        waitFor(1);
        scrollDownUsingActions(2);
    }
}
