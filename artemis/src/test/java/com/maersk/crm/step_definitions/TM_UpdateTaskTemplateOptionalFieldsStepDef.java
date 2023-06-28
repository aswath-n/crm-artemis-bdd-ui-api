package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMTaskTemplatePage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.maersk.crm.utilities.BrowserUtils.waitFor;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class TM_UpdateTaskTemplateOptionalFieldsStepDef extends BrowserUtils{
    final ThreadLocal<TM_AddBusinessUnitStepDef> tm_addBusinessUnitStepDef = ThreadLocal.withInitial(TM_AddBusinessUnitStepDef::new);
    TMTaskTemplatePage tmTaskTemplatePage = new TMTaskTemplatePage();
    final ThreadLocal<String> templateName = ThreadLocal.withInitial(String::new);
    final ThreadLocal<WebDriverWait> wait = ThreadLocal.withInitial(()->new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20)));
//    private static WebDriver driver;

    @Given("that I am a User that is Creating a Task Template")
    public void that_I_am_a_User_that_is_Creating_a_Task_Template() {
        wait.set(new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20)));
        tm_addBusinessUnitStepDef.get().navigateToBusinessUnitDetailsPage("project", "SelectFromConfig");
        tmTaskTemplatePage.buildIcon.click();
        tmTaskTemplatePage.taskTemplateSideDrawer.click();


    }

    @When("I view the list of Configurable Fields")
    public void i_view_the_list_of_Configurable_Fields() {
        waitFor(5);
        tmTaskTemplatePage.createNewTaskTemplate.click();

    }

    @Then("I will see the following {string}")
    public void i_will_see_the_following(String field) {
        switch (field) {
            case "Disposition":
                tmTaskTemplatePage.disposition.isDisplayed();
                break;

            case "Site Type":
                tmTaskTemplatePage.siteType.isDisplayed();
                break;
            case "Site Name":
                tmTaskTemplatePage.siteName.isDisplayed();
                break;

            case "Site  Address Line 1":
                tmTaskTemplatePage.siteAddressLine1.isDisplayed();
                break;
            case "Site Address Line 2":
                tmTaskTemplatePage.siteAddressLine2.isDisplayed();
                break;

            case "Site Address City":
                tmTaskTemplatePage.siteAddressCity.isDisplayed();
                break;
            case "Site Address State":
                tmTaskTemplatePage.siteAddressState.isDisplayed();
                break;

            case "Site Address Zip Code":
                tmTaskTemplatePage.siteAddressZipCode.isDisplayed();
                break;
            case "Site County":
                tmTaskTemplatePage.siteCounty.isDisplayed();
                break;

            case "Session Region":
                tmTaskTemplatePage.sessionRegion.isDisplayed();
                break;
            case "Site Contact First Name":
                tmTaskTemplatePage.siteContactFirstName.isDisplayed();
                break;

            case "Site Contact Last Name":
                tmTaskTemplatePage.siteContactLastName.isDisplayed();
                break;
            case "Site Contact Phone":
                tmTaskTemplatePage.siteContactPhone.isDisplayed();
                break;

            case "Site Contact Fax":
                tmTaskTemplatePage.siteContactFax.isDisplayed();
                break;
            case "Site Contact Email":
                tmTaskTemplatePage.siteContactEmail.isDisplayed();
                break;

            case "Community Outreach Session Type":
                tmTaskTemplatePage.communityOutreachSessionType.isDisplayed();
                break;
            case "Complaint":
                tmTaskTemplatePage.complaint.isDisplayed();
                break;

            case "Message Subject":
                tmTaskTemplatePage.messageSubject.isDisplayed();
                break;
            case "Message":
                tmTaskTemplatePage.message.isDisplayed();
                break;
            case "CP8931":
                waitFor(4);
                scrollToElement(tmTaskTemplatePage.disposition);
                tmTaskTemplatePage.disposition.isDisplayed();
                tmTaskTemplatePage.siteType.isDisplayed();
                tmTaskTemplatePage.siteName.isDisplayed();
                tmTaskTemplatePage.siteAddressLine1.isDisplayed();
                tmTaskTemplatePage.siteAddressLine2.isDisplayed();
                tmTaskTemplatePage.siteAddressCity.isDisplayed();
                tmTaskTemplatePage.siteAddressState.isDisplayed();
                tmTaskTemplatePage.siteAddressZipCode.isDisplayed();
                tmTaskTemplatePage.siteCounty.isDisplayed();
                tmTaskTemplatePage.sessionRegion.isDisplayed();
                tmTaskTemplatePage.siteContactFirstName.isDisplayed();
                tmTaskTemplatePage.siteContactLastName.isDisplayed();
                tmTaskTemplatePage.siteContactPhone.isDisplayed();
                tmTaskTemplatePage.siteContactFax.isDisplayed();
                tmTaskTemplatePage.siteContactEmail.isDisplayed();
                tmTaskTemplatePage.communityOutreachSessionType.isDisplayed();
                tmTaskTemplatePage.complaint.isDisplayed();
                tmTaskTemplatePage.messageSubject.isDisplayed();
                scrollToElement(tmTaskTemplatePage.message);
                tmTaskTemplatePage.message.isDisplayed();

                break;


        }
    }


    @And("I select to include a field in a defined {string}")
    public void iSelectToIncludeFieldInDefined(String group) {

        switch (group) {
            case "Site":
                waitFor(2);
                scrollToElement(tmTaskTemplatePage.siteType);
                jsClick(tmTaskTemplatePage.siteType);
                break;

            case "Site Contact":
                waitFor(2);
                scrollToElement(tmTaskTemplatePage.siteContactFirstName);
                jsClick(tmTaskTemplatePage.siteContactFirstName);
                break;

        }
    }

    @Then("I see the other fields in the {string} are systematically selected")
    public void iSeeTheOtherFieldsInGroupSystematicallySelected(String group) {
        switch (group) {
            case "Site":
                waitFor(2);
                assertTrue(tmTaskTemplatePage.siteType.isSelected());
                assertTrue(tmTaskTemplatePage.siteName.isSelected());
                assertTrue(tmTaskTemplatePage.siteAddressCity.isSelected());
                assertTrue(tmTaskTemplatePage.siteAddressLine1.isSelected());
                assertTrue(tmTaskTemplatePage.siteAddressLine2.isSelected());
                assertTrue(tmTaskTemplatePage.siteAddressState.isSelected());
                assertTrue(tmTaskTemplatePage.siteAddressZipCode.isSelected());
                assertTrue(tmTaskTemplatePage.siteCounty.isSelected());
                break;

            case "Site Contact":
                waitFor(2);
                assertTrue(tmTaskTemplatePage.siteContactFirstName.isSelected());
                assertTrue(tmTaskTemplatePage.siteContactPhone.isSelected());
                assertTrue(tmTaskTemplatePage.siteContactEmail.isSelected());
                assertTrue(tmTaskTemplatePage.siteContactLastName.isSelected());
                assertTrue(tmTaskTemplatePage.siteContactFax.isSelected());
                break;

        }
    }

    @And("I add {string}")
    public void iAddFields(String field) {
        switch (field) {
            case "Disposition":
                tmTaskTemplatePage.disposition.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;

            case "Site Type":
                tmTaskTemplatePage.siteType.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;
            case "Site Name":
                tmTaskTemplatePage.siteName.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;

            case "Site Address Line 1":
                tmTaskTemplatePage.siteAddressLine1.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;
            case "Site Address Line 2":
                tmTaskTemplatePage.siteAddressLine2.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;

            case "Site Address City":
                tmTaskTemplatePage.siteAddressCity.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;
            case "Site Address State":
                tmTaskTemplatePage.siteAddressState.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;

            case "Site Address Zip Code":
                tmTaskTemplatePage.siteAddressZipCode.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;
            case "Site County":
                tmTaskTemplatePage.siteCounty.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;

            case "Session Region":
                tmTaskTemplatePage.sessionRegion.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;
            case "Site Contact First Name":
                tmTaskTemplatePage.siteContactFirstName.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;

            case "Site Contact Last Name":
                tmTaskTemplatePage.siteContactLastName.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;
            case "Site Contact Phone":
                tmTaskTemplatePage.siteContactPhone.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;

            case "Site Contact Fax":
                tmTaskTemplatePage.siteContactFax.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;
            case "Site Contact Email":
                tmTaskTemplatePage.siteContactEmail.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;

            case "Community Outreach Session Type":
                tmTaskTemplatePage.communityOutreachSessionType.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;
            case "Complaint":
                tmTaskTemplatePage.complaint.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;

            case "Message Subject":
                tmTaskTemplatePage.messageSubject.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;
            case "Message":
                tmTaskTemplatePage.message.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;
            case "CP8931":
                waitFor(2);
                tmTaskTemplatePage.disposition.click();
                tmTaskTemplatePage.siteType.click();
                tmTaskTemplatePage.sessionRegion.click();
                tmTaskTemplatePage.siteContactFirstName.click();
                tmTaskTemplatePage.communityOutreachSessionType.click();
                tmTaskTemplatePage.complaint.click();
                tmTaskTemplatePage.messageSubject.click();
                tmTaskTemplatePage.message.click();
                tmTaskTemplatePage.addFieldsBtn.click();
                break;

        }
    }

    @And("I populate template name")
    public void iPopulateTemplateName() {
        templateName.set(RandomStringUtils.random(5, true, false));
        tmTaskTemplatePage.templateName.sendKeys(templateName.get());


    }

    @Then("I see the success message for saving the Template")
    public void iSeeTheSuccessMessageForSavingTheTemplate() {
        wait.set(new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20)));
        wait.get().until(ExpectedConditions.visibilityOf(tmTaskTemplatePage.templateCreatedMessage));
        assertTrue(tmTaskTemplatePage.templateCreatedMessage.isDisplayed());
    }

    @And("I see the {string} are successfully saved as part of the Task Template")
    public void iSeeSuccessfullySavedTaskTemplate(String field) {
        wait.set(new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20)));
     
        String x = "//*[text()='" + templateName.get() + "']";
        wait.get().until(ExpectedConditions.elementToBeClickable(By.xpath(x)));
        assertFalse(tmTaskTemplatePage.templateCreatedMessage.isDisplayed(), "Success message intercepting task template click");
        Driver.getDriver().findElement(By.xpath(x)).click();
        savedFields(field);

    }


    public void savedFields(String field) {
        
        switch (field) {
            case "Disposition":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Disposition']")).isDisplayed());

                break;

            case "Site Type":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Type']")).isDisplayed());

            case "Site Name":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Name']")).isDisplayed());


            case "Site Address Line 1":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Address Line 1']")).isDisplayed());

            case "Site Address Line 2":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Address Line 2']")).isDisplayed());


            case "Site Address City":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Address City']")).isDisplayed());

            case "Site Address State":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Address State']")).isDisplayed());


            case "Site Address Zip Code":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Address Zip Code']")).isDisplayed());

            case "Site County":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site County']")).isDisplayed());
                break;

            case "Session Region":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Session Region']")).isDisplayed());
                break;
            case "Site Contact First Name":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Contact First Name']")).isDisplayed());


            case "Site Contact Last Name":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Contact Last  Name']")).isDisplayed());

            case "Site Contact Phone":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Contact Phone']")).isDisplayed());


            case "Site Contact Fax":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Contact Fax']")).isDisplayed());

            case "Site Contact Email":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Contact Email']")).isDisplayed());
                break;

            case "Community Outreach Session Type":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Community Outreach Session Type']")).isDisplayed());
                break;
            case "Complaint":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Complaint']")).isDisplayed());
                break;

            case "Message Subject":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Message Subject']")).isDisplayed());

                break;
            case "Message":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Message']")).isDisplayed());
                break;
            case "CP8931":
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Message']")).isDisplayed());
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Message Subject']")).isDisplayed());
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Complaint']")).isDisplayed());
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Community Outreach Session Type']")).isDisplayed());
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Contact Email']")).isDisplayed());
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Contact Fax']")).isDisplayed());
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Contact Phone']")).isDisplayed());
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Contact Last  Name']")).isDisplayed());
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Contact First Name']")).isDisplayed());
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Session Region']")).isDisplayed());
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site County']")).isDisplayed());
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Address Zip Code']")).isDisplayed());
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Address State']")).isDisplayed());
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Address City']")).isDisplayed());
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Address Line 2']")).isDisplayed());
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Address Line 1']")).isDisplayed());
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Name']")).isDisplayed());
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Site Type']")).isDisplayed());
                assertTrue(Driver.getDriver().findElement(By.xpath("(//h6[text()='SELECTED FIELDS']/..)//*[text()='Disposition']")).isDisplayed());
                break;

        }
    }
}
