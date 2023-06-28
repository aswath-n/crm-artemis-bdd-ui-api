package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMActiveContactPage;
import com.maersk.crm.pages.crm.CRMCreateConsumerProfilePage;
import com.maersk.crm.pages.crm.CRMLinksComponentPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CRM_GeneralContactRecordSearchResultStepDef extends CRMUtilities implements ApiBase {

    CRMLinksComponentPage linksComponentPage=new CRMLinksComponentPage();
    CRMActiveContactPage activeContactPage = new CRMActiveContactPage();
    CRMCreateConsumerProfilePage createConsumerProfilePage = new CRMCreateConsumerProfilePage();
    @Then("I verify only primary individual and case member is displayed in the search result")
    public void verifyGeneralContactSearchResult() {
        List<String> consumerRoles = new ArrayList<>();
        consumerRoles.add("Primary Individual");
        consumerRoles.add("Member");

        assertEquals(linksComponentPage.consumerLinkedToTaskList.size(), 1);
        assertEquals(activeContactPage.consumerMembers.size(), 2);
        for (WebElement elem: activeContactPage.consumerRoles) {
            System.out.println("Consumer Role: "+elem.getText());
            assertTrue(consumerRoles.contains(elem.getText()));
        }
    }

    @Then("I will verify {string} hyperlink is disabled and not clickable")
    public void i_will_verify_hyhyperlink_is_disabled_and_not_clickable(String string) {
        waitFor(3);
        assertTrue(isElementDisplayed(createConsumerProfilePage.caseConsumerSearch), "caseConsumerSearch lable is not displayed");
    }
}
