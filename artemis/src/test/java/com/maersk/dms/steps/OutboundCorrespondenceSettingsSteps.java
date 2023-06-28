package com.maersk.dms.steps;

import com.maersk.crm.pages.crm.CRMDashboardPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.World;
import com.maersk.dms.pages.InboundCorrespondenceSettingsPage;
import com.maersk.dms.pages.OutboundCorrespondenceSettingsPage;
import com.maersk.dms.pages.TenantManagerDMSProjectListPage;
import com.maersk.dms.step_definitions.OutboundCorrespondenceSettingsStepDefs;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.maersk.crm.utilities.BrowserUtils.*;
//import static com.sun.deploy.util.SessionState.save;

public class OutboundCorrespondenceSettingsSteps {
    private OutboundCorrespondenceSettingsPage outboundCorrespondenceSettingsPage = new OutboundCorrespondenceSettingsPage();
    private InboundCorrespondenceSettingsPage inboundCorrespondenceSettingsPage = new InboundCorrespondenceSettingsPage();
    private final ThreadLocal<OutboundCorrespondenceSettingsStepDefs> outboundCorrespondenceSettingsStepDefs = ThreadLocal.withInitial(OutboundCorrespondenceSettingsStepDefs::new);
    private TenantManagerDMSProjectListPage tmListOfProjectsPage = new TenantManagerDMSProjectListPage();
    public final ThreadLocal<Map<String, String>> save = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities= ThreadLocal.withInitial(UIAutoUitilities::new);
    private final APIAutoUtilities apiAutoUtilities =APIAutoUtilities.API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities();
    private String userName = ConfigurationReader.getProperty("login");
    private String password = ConfigurationReader.getProperty("password");
    private CRMDashboardPage dashBoard = new CRMDashboardPage();
    private final ThreadLocal<BrowserUtils> browserUtils = ThreadLocal.withInitial(BrowserUtils::new);

    @And("I navigate to the Outbound Correspondence Settings Screen of Project:{string}")
    public void iNavigateToTheOutboundCorrespondenceSettingsScreenOfProject(String project) {
//        if ("current".equalsIgnoreCase(project)) {
//            project = ConfigurationReader.getProperty("projectName").split(" ")[1];
//        }
//        waitForVisibility(tmListOfProjectsPage.projectInput, 7);
//        staticWait(2000);
//        uiAutoUitilities.clearFillWithActions(tmListOfProjectsPage.projectInput, project);
//        staticWait(2000);
////        browserUtils.get().waitForClickablility(tmListOfProjectsPage.getProjectList().get(0),7);
//        tmListOfProjectsPage.searchButton.click();
//        staticWait(1000);
//        browserUtils.get().waitForClickablility(tmListOfProjectsPage.getProject(project), 7);
//        tmListOfProjectsPage.getProject(project).click();
////        waitForVisibility(tmListOfProjectsPage.leftDrawer,7);
//        staticWait(3000);
////        Actions actions = new Actions(Driver.getDriver());
////
////        actions.click(tmListOfProjectsPage.leftDrawer);
//        actions.perform();
        staticWait(4000);

        tmListOfProjectsPage.leftDrawer.click();
        staticWait(1000);
//        waitForVisibility(tmListOfProjectsPage.outBoundCorrespondenceLink,7);
        tmListOfProjectsPage.outBoundSettingsLink.click();
        staticWait(2000);
        outboundCorrespondenceSettingsPage.languagesHeader = Driver.getDriver().findElement(By.xpath("//*[contains(text(),'LANGUAGES')]"));
        browserUtils.get().waitForVisibility(outboundCorrespondenceSettingsPage.languagesHeader, 7);
    }

    @When("I save Outbound Correspondence Settings with {string} {string}")
    public void iSaveOutboundCorrespondenceSettingsWith(String field, String value) {
        outboundCorrespondenceSettingsStepDefs.get().createRandomValues();
        outboundCorrespondenceSettingsStepDefs.get().changeValue(field, value);
        waitFor(2);
        outboundCorrespondenceSettingsStepDefs.get().ifEditButtonThenClick();
        if (World.configSettingsPresent.get()) {
            outboundCorrespondenceSettingsStepDefs.get().fillOutUIFields(field, value);
        } else {
            outboundCorrespondenceSettingsStepDefs.get().fillOutUIFields();
        }

    }

    @Then("Outbound Correspondence Settings save will fail with message {string}")
    public void outboundCorrespondenceSettingsSaveWillFailWithMessage(String message) {
        outboundCorrespondenceSettingsStepDefs.get().save();
        waitFor(2);
        outboundCorrespondenceSettingsStepDefs.get().assertFailMessage(message);
    }

    @And("{string} Channel is selected in Outbound Correspondence Settings")
    public void channelIsSelectedInOutboundCorrespondenceSettings(String channel) {
        outboundCorrespondenceSettingsStepDefs.get().ifEditButtonThenClick();
        outboundCorrespondenceSettingsStepDefs.get().selectChannel(channel);
    }

    @When("I save Outbound Correspondence Settings with the following fields blank; {string}")
    public void iSaveOutboundCorrespondenceSettingsWithTheFollowingFieldsBlank(String field) {
        outboundCorrespondenceSettingsStepDefs.get().createRandomValues();
        outboundCorrespondenceSettingsStepDefs.get().changeValue(field, "null");
        outboundCorrespondenceSettingsStepDefs.get().ifEditButtonThenClick();
        if (World.configSettingsPresent.get()) {
            outboundCorrespondenceSettingsStepDefs.get().fillOutUIFields(field, "null");
        } else {
            outboundCorrespondenceSettingsStepDefs.get().fillOutUIFields();
        }
    }

    @When("I save Outbound Correspondence Settings with {string} characters to following fields")
    public void iSaveOutboundCorrespondenceSettingsWithTheFollowingFieldsWithCharacters(String type, List<String> data) {
        outboundCorrespondenceSettingsStepDefs.get().createRandomValues();
        outboundCorrespondenceSettingsStepDefs.get().ifEditButtonThenClick();
        if (World.configSettingsPresent.get()) {
            //nothing as intended
        } else {
            outboundCorrespondenceSettingsStepDefs.get().fillOutUIFields();
        }
        for (String field : data) {
            outboundCorrespondenceSettingsStepDefs.get().fillOutUIFields(field, "null");
        }
        for (String field : data) {
            outboundCorrespondenceSettingsStepDefs.get().fillOutUIFieldsVerifyData(field);
        }
    }

    @Then("Outbound Correspondence Settings save the values successfully")
    public void outboundCorrespondenceSettingsSaveTheValuesSuccessfully() {
        outboundCorrespondenceSettingsStepDefs.get().save();
        outboundCorrespondenceSettingsStepDefs.get().verifySaveSuccessfulPopup();
    }

    @When("I select Outbound Print Field Format Dropdown")
    public void iSelectOutboundPrintFieldFormatDropdown() {
        outboundCorrespondenceSettingsStepDefs.get().ifEditButtonThenClick();
        waitFor(2);
        outboundCorrespondenceSettingsPage.outboundFileFormat.click();
    }

    @Then("I should see the following values as options;")
    public void iShouldSeeTheFollowingValuesAsOptions(List<String> data) {
        List<WebElement> list = outboundCorrespondenceSettingsPage.getOutboundfileFormats();
        if (data.size() != list.size()) {
            Assert.fail("Actual list is different than expected list");
        }
        for (int count = 0; count < list.size(); count++) {
            Assert.assertTrue(list.get(count).getAttribute("data-value").equalsIgnoreCase(data.get(count)));
        }
    }

    @Then("I should see the save successful pop up message appear")
    public void iShouldSeeTheSaveSuccessfulPopUpMessageAppear() {
        outboundCorrespondenceSettingsStepDefs.get().save();
        outboundCorrespondenceSettingsStepDefs.get().verifySaveSuccessfulPopup();
    }

    @When("I save Outbound Correspondence Settings with random valid values")
    public void iSaveOutboundCorrespondenceSettingsWithRandomValidValues() {
        outboundCorrespondenceSettingsStepDefs.get().createRandomValues();
        waitFor(2);
        outboundCorrespondenceSettingsStepDefs.get().ifEditButtonThenClick();
        waitFor(2);
        outboundCorrespondenceSettingsStepDefs.get().fillOutUIFields();
    }

    @Then("Outbound Correspondence Settings the system capture; Updated Date, Updated By and display as Updated On and Updated By")
    public void outboundCorrespondenceSettingsTheSystemCaptureUpdatedDateUpdatedByAndDisplayAsUpdatedOnAndUpdatedBy() {
        outboundCorrespondenceSettingsStepDefs.get().save();
        outboundCorrespondenceSettingsStepDefs.get().verifySaveSuccessfulPopup();
        BrowserUtils.waitFor(10);
        browserUtils.get().scrollToElement(outboundCorrespondenceSettingsPage.updatedBy);
        browserUtils.get().scrollUpRobot();
        Assert.assertTrue(outboundCorrespondenceSettingsPage.updatedBy.getText().contains("System"));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();
        Assert.assertTrue(outboundCorrespondenceSettingsPage.updatedOn.getText().contains(dtf.format(now)), "Actual - " + outboundCorrespondenceSettingsPage.updatedOn.getText() + "\n" + "Expected - " + dtf.format(now));
    }

    @When("I attempt to navigate away from the page")
    public void iAttemptToNavigateAwayFromThePage() {
        outboundCorrespondenceSettingsPage.backButton.click();
    }

    @Then("Outbound Correspondence Settings the system warn be before discarding changes")
    public void outboundCorrespondenceSettingsTheSystemWarnBeBeforeDiscardingChanges() {
        BrowserUtils.waitFor(1);
        outboundCorrespondenceSettingsStepDefs.get().assertFailMessage("If you navigate away, your information will not be saved");
    }

    @Then("I should see the {string} has a {string} value in Outbound Correspondence Settings")
    public void iShouldSeeTheHasAValueInOutboundCorrespondenceSettings(String field, String value) {
        outboundCorrespondenceSettingsStepDefs.get().verifyFieldData(field, value);
    }

    @When("I edit Outbound Correspondence Settings with {string} to be {string}")
    public void iEditOutboundCorrespondenceSettingsWithToBe(String field, String value) {
        outboundCorrespondenceSettingsStepDefs.get().ifEditButtonThenClick();
        if (World.configSettingsPresent.get()) {
            outboundCorrespondenceSettingsStepDefs.get().fillOutUIFields(field, value);
        } else {
            outboundCorrespondenceSettingsStepDefs.get().fillOutUIFields();
        }
    }

    @And("I remove {string} from Other Language dropdown in OB Correspondence Configuration")
    public void iRemoveFromOtherLanguageDropdownInOBCorrespondenceConfiguration(String value) {
        outboundCorrespondenceSettingsStepDefs.get().removeValueFromOtherLanguageBox(value);
    }

    @Then("I should see the {string} is displayed in Outbound Correspondence Settings")
    public void iShouldSeeTheIsDisplayedInOutboundCorrespondenceSettings(String webElement) {
        outboundCorrespondenceSettingsStepDefs.get().verifyWebelementIsDisplayed(webElement);
    }

    @Then("I should see the Outbound Correspondence Settings fail to save with error message {string}")
    public void iShouldSeeTheOutboundCorrespondenceSettingsFailToSaveWithErrorMessage(String message) {
        browserUtils.get().scrollToElement(outboundCorrespondenceSettingsPage.saveButton);
        waitFor(2);
        outboundCorrespondenceSettingsPage.saveButton.click();
        browserUtils.get().scrollUpRobot();
        uiAutoUitilities.get().verifyErrorMessageDisplayed(message);
    }

    @Then("I should not see the {string} is displayed in otherLanguage dropdown box")
    public void iShouldNotSeeTheIsDisplayedInDropdownBox(String value) {
        outboundCorrespondenceSettingsStepDefs.get().verifyNotInDropDown(value);
    }

    @When("I clicked on the global navigation icon")
    public void iClickedOnTheGlobalNavigationIcon() {
        browserUtils.get().click(dashBoard.btnMenuList);
        System.out.println("2");
    }

    @Then("I should Create Correspondence as an option")
    public void iShouldCreateCorrespondenceAsAnOption() {
        Assert.assertEquals("CREATE CORRESPONDENCE", dashBoard.createCorrespondence.getText());
    }

    @Then("I should see that correspondence channels are all present \\(Mail,Email,Text,Fax,Web Portal,Mobile App)")
    public void iShouldSeeThatCorrespondenceChannelsAreAllPresentMailEmailTextFaxWebPortalMobileApp() {
        outboundCorrespondenceSettingsStepDefs.get().verifyChannels();
    }

    @Then("I should see all the correspondence languages properly configured")
    public void iShouldSeeAllTheCorrespondenceLanguagesProperlyConfigured() {
        outboundCorrespondenceSettingsStepDefs.get().ifEditButtonThenClick();
        waitFor(3);
        outboundCorrespondenceSettingsStepDefs.get().verifyLanguages();
    }

    @And("I navigate to the Inbound Correspondence Settings Screen of Project:{string}")
    public void iNavigateToTheInboundCorrespondenceSettingsScreenOfProject(String project) {
        //        if ("current".equalsIgnoreCase(project)) {
//            project = ConfigurationReader.getProperty("projectName").split(" ")[1];
//        }
//        waitForVisibility(tmListOfProjectsPage.projectInput, 7);
//        staticWait(2000);
//        uiAutoUitilities.clearFillWithActions(tmListOfProjectsPage.projectInput, project);
//        staticWait(2000);
////        browserUtils.get().waitForClickablility(tmListOfProjectsPage.getProjectList().get(0),7);
//        tmListOfProjectsPage.searchButton.click();
//        staticWait(1000);
//        browserUtils.get().waitForClickablility(tmListOfProjectsPage.getProject(project), 7);
//        tmListOfProjectsPage.getProject(project).click();
////        waitForVisibility(tmListOfProjectsPage.leftDrawer,7);
//        staticWait(3000);
////        Actions actions = new Actions(Driver.getDriver());
////
////        actions.click(tmListOfProjectsPage.leftDrawer);
//        actions.perform();
        staticWait(4000);

        tmListOfProjectsPage.leftDrawer.click();
        staticWait(1000);
//        waitForVisibility(tmListOfProjectsPage.outBoundCorrespondenceLink,7);
        tmListOfProjectsPage.inBoundSettingsLink.click();
        staticWait(2000);
        outboundCorrespondenceSettingsPage.inboundSettingsHeader = Driver.getDriver().findElement(By.xpath("//*[contains(text(),'INBOUND CORRESPONDENCE CONFIGURATION')]"));
        browserUtils.get().waitForVisibility(outboundCorrespondenceSettingsPage.inboundSettingsHeader, 7);
    }

    @Then("I should see {string} is an option on Inbound Correspondence Settings Page")
    public void iShouldSeeIsAnOptionOnInboundCorrespondenceSettingsPage(String element) {
        waitFor(7);
        Assert.assertTrue(outboundCorrespondenceSettingsPage.getInboundChannels(element).isDisplayed());
    }

    @And("I click on edit button on Inbound Settings Page")
    public void iClickOnEditButtonOnInboundSettingsPage() {
        if (!uiAutoUitilities.get().quickIsDisplayed(inboundCorrespondenceSettingsPage.inboundSettingsHeader)) {
            inboundCorrespondenceSettingsPage.sideMenuInboundSettingsLink.click();
            browserUtils.get().waitForPageToLoad(10);
            waitFor(2);
        }
        waitFor(3);
        waitFor(5);
        outboundCorrespondenceSettingsPage.editInboundSettings.click();
        waitFor(2);
    }

    @When("I click to {string} the {string} channel option in Inbound Settings Page")
    public void iClickToTheChannelOptionInInboundSettingsPage(String action, String channel) {
        if ("enable".equalsIgnoreCase(action)) {
            outboundCorrespondenceSettingsStepDefs.get().enableInboundChannel(channel);
        } else if ("disable".equalsIgnoreCase(action)) {
            outboundCorrespondenceSettingsStepDefs.get().disableInboundChannel(channel);
        }
    }

    @And("I click the Inbound Settings Page Save button")
    public void iClickTheInboundSettingsPageSaveButton() {
        waitFor(7);
        outboundCorrespondenceSettingsPage.saveButton.click();
        waitFor(3);
    }

    @Then("I should see the {string} channel option is {string}")
    public void iShouldSeeTheChannelOptionIs(String channel, String action) {
        waitFor(5);
        if ("enabled".equalsIgnoreCase(action)) {
            outboundCorrespondenceSettingsStepDefs.get().verifyEnableInboundChannel(channel);
        } else if ("disabled".equalsIgnoreCase(action)) {
            outboundCorrespondenceSettingsStepDefs.get().verifyDisableInboundChannel(channel);
        }
    }

    @Then("I should see {string} is an option on Outbound Correspondence Settings Page")
    public void iShouldSeeIsAnOptionOnOutboundCorrespondenceSettingsPage(String channel) {
        waitFor(7);
        Assert.assertTrue(outboundCorrespondenceSettingsPage.getInboundChannels(channel).isDisplayed());
    }

    @When("I click to {string} the {string} channel option in Outbound Settings Page")
    public void iClickToTheChannelOptionInOutboundSettingsPage(String action, String channel) {
        if ("enable".equalsIgnoreCase(action)) {
            outboundCorrespondenceSettingsStepDefs.get().enableInboundChannel(channel);
        } else if ("disable".equalsIgnoreCase(action)) {
            outboundCorrespondenceSettingsStepDefs.get().disableInboundChannel(channel);
        }
    }

    @And("I click the Outbound Settings Page Save button")
    public void iClickTheOutboundSettingsPageSaveButton() {
        browserUtils.get().scrollDownUsingActions(10);
        outboundCorrespondenceSettingsPage.saveButton.click();
        browserUtils.get().scrollUpUsingActions(10);
        waitFor(5);
    }

    @Then("I should see I have navigated to Inbound Inbound Correspondence Settings Screen")
    public void iShouldSeeIHaveNavigatedToInboundInboundCorrespondenceSettingsScreen() {
        Assert.assertTrue(outboundCorrespondenceSettingsPage.inboundSettingsHeader.isDisplayed());
    }

    @And("I click the cancel button on the Inbound Settings Page")
    public void iClickTheCancelButtonOnTheInboundSettingsPage() {
        outboundCorrespondenceSettingsPage.cancelButton.click();
    }

    @Then("I verify the labels in the Inbound Correspondence Settings Screen")
    public void iVerifyTheLabelsInTheInboundCorrespondenceSettingsScreen() {
        Assert.assertTrue(outboundCorrespondenceSettingsPage.inboundChannelHeader.isDisplayed());
        Assert.assertTrue(outboundCorrespondenceSettingsPage.inboundDefaultTypeHeader.isDisplayed());
    }

    @And("I should not be able to invoke edit button on Inbound Settings Page")
    public void iCannotInvokeEditButtonOnInboundSettingsPage() {
        waitFor(5);
        Assert.assertTrue(outboundCorrespondenceSettingsPage.editInboundSettings.isDisplayed());
        Assert.assertFalse(outboundCorrespondenceSettingsPage.editInboundSettings.isEnabled(), "Edit button is not disabled");
    }

    @When("I validate following IB types available in Inbound configuration page")
    public void ivalidateinboundcorresppondencetypevalues(List<String> dataTable) {
        outboundCorrespondenceSettingsStepDefs.get().validateibfieldvaluesIBConfigurations(dataTable);
    }

}
