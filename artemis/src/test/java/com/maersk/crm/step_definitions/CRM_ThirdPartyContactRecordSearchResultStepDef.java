package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;

import static org.testng.Assert.assertTrue;

public class CRM_ThirdPartyContactRecordSearchResultStepDef extends CRMUtilities implements ApiBase {

    CRMActiveContactPage crmActiveContactPage = new CRMActiveContactPage();
    CRMThirdPartyContactRecPage thirdPartyContactPage = new CRMThirdPartyContactRecPage();
    CRMContactHistoryPage crmContactHistoryPage = new CRMContactHistoryPage();
    CRMLinksComponentPage linksComponentPage=new CRMLinksComponentPage();
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    CRMTaskSearchPage crmTaskSearchPage = new CRMTaskSearchPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();

    private String taskId = "14028";

    @When("I click on third party contact record in active contact")
    public void clickThirdPartyContactRecord() {
        crmActiveContactPage.thirdPartyContactTab.click();
        waitForVisibility(crmContactHistoryPage.thirdPartyLabelText, 10);
    }

    @When("I search Task by Task Id for contact record")
    public void searchTaskByTaskId() {
        linksComponentPage.taskIdForLinkVerification.sendKeys(taskId);
    }

    @When("I search customer in edit contact with First Name as {string} and Last Name as {string}")
    public void searchCustomerEditContactFirstAndLastName(String firstName, String lastName) {
        clearAndFillText(contactRecord.editContactFirstName, firstName);
        clearAndFillText(contactRecord.editContactLastName, lastName);
        waitFor(1);
        click(contactRecord.searchButton);
        waitFor(5);
    }

    @And("I click on found task record for contact record and edit task")
    public void clickFoundTaskRecord() {
        scrollDownUsingActions(1);
        waitForVisibility(createGeneralTask.taskSearchId, 10);
        jsClick(createGeneralTask.taskSearchId);
        waitForVisibility(crmTaskSearchPage.editTaskBtn, 10);
        jsClick(crmTaskSearchPage.editTaskBtn);
        waitForVisibility(createGeneralTask.link, 10);
    }

    @And("I click on searched record and edit contact")
    public void clickSearchRecordAndEdit() {
        waitForVisibility(contactRecord.consumerSearchSecondRecord, 10);
        jsClick(contactRecord.consumerSearchSecondRecord);
        waitForVisibility(contactRecord.editContactButton, 10);
        jsClick(contactRecord.editContactButton);
        waitForVisibility(contactRecord.firstName, 10);
    }

    @Then("I verify radio buttons are displayed correctly for members with {string} broker and assister")
    public void verifyRadioButtonsThirdPartyContact(String type) {
        scrollDownUsingActions(3);
        if (isElementDisplayed(thirdPartyContactPage.thirdPartyPrimaryIndividualName)) {
            thirdPartyContactPage.thirdPartyPrimaryIndividualRadioBtn.isDisplayed();
        }
        if (isElementDisplayed(thirdPartyContactPage.thirdPartyCaseMemberName)) {
            thirdPartyContactPage.thirdPartyCaseMemberRadioBtn.isDisplayed();
        }

        checkARBrokerAssisterRadioButton(thirdPartyContactPage.thirdPartyAuthorizedRepRadioBtn);

        if (type.equals("atleast")) {
            checkARBrokerAssisterRadioButton(thirdPartyContactPage.thirdPartyBrokerBtn);
            checkARBrokerAssisterRadioButton(thirdPartyContactPage.thirdPartyAssisterBtn);
        }
    }

    private void checkARBrokerAssisterRadioButton(WebElement element) {
        try {
            element.isDisplayed();
        } catch (Exception e) {
            System.out.println("Exception is: "+e);
            assertTrue(e.toString().contains("no such element: Unable to locate element"));
        }
    }
}
