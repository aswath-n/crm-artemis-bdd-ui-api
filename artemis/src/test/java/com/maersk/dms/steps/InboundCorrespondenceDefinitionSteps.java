package com.maersk.dms.steps;

import com.google.gson.JsonArray;
import com.maersk.crm.utilities.*;
import com.maersk.dms.pages.*;
import com.maersk.dms.step_definitions.InboundCorrespondenceDefinitionStepDefs;
import com.maersk.dms.step_definitions.InboundCorrespondenceSearchStepDef;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;

public class InboundCorrespondenceDefinitionSteps extends CRMUtilities implements ApiBase {

    private InboundCorrespondenceDefinitionStepDefs inboundCorrespondenceDefinitionStepDefs = new InboundCorrespondenceDefinitionStepDefs();
    private TenantManagerDMSProjectListPage tmListOfProjectsPage = new TenantManagerDMSProjectListPage();
    private InboundCorrespondenceDefinitionListPage inboundCorrespondenceDefinitionListPage = new InboundCorrespondenceDefinitionListPage();
    private InboundCorrespondenceDefinitionPage inboundCorrespondenceDefinitionPage = new InboundCorrespondenceDefinitionPage();
    private final APIAutoUtilities apiAutoUtilities =APIAutoUtilities.API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities();
    private InboundCorrespondenceSearchStepDef inboundCorrespondenceSearchStepDef = new InboundCorrespondenceSearchStepDef();
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities= ThreadLocal.withInitial(UIAutoUitilities::new);
    Actions action = new Actions(Driver.getDriver());
    private final ThreadLocal<BrowserUtils> browserUtils = ThreadLocal.withInitial(BrowserUtils::new);


    @When("I navigate to the Inbound Correspondence Definitions Screen of Project:{string}")
    public void iNavigateToTheInboundCorrespondenceDefinitionsScreenOfProject(String project) {

        staticWait(4000);
        tmListOfProjectsPage.leftDrawer.click();
        staticWait(3000);
        tmListOfProjectsPage.inBoundCorrespondenceLink.click();
        browserUtils.get().waitForVisibility(inboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition, 7);

    }

    @Then("I should see the add an Inbound Correspondence Definition button")
    public void iShouldSeeTheAddAnInboundCorrespondenceDefinitionButton() {
        Assert.assertTrue(inboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.isDisplayed());
    }

    @And("I have at least {int} Inbound Correspondence Definition with {int} characters for Name")
    public void iHaveAtLeastInboundCorrespondenceDefinitionWithCharactersForName(int definitions, int characters) {
        String value = RandomStringUtils.randomAscii(characters);
        Map<String, String> customFields = new HashMap<>();
        customFields.put("Name", value);
        inboundCorrespondenceDefinitionStepDefs.createInbCorrDefUI(definitions, customFields);
        inboundCorrespondenceDefinitionStepDefs.navigateBackToTheList();
    }

    @And("I have at least {int} Inbound Correspondence Definition with {int} characters for Barcode")
    public void iHaveAtLeastInboundCorrespondenceDefinitionWithCharactersForBarcode(int definitions, int characters) {
        String value = RandomStringUtils.random(characters, false, true);
        Map<String, String> customFields = new HashMap<>();
        customFields.put("Barcode", value);
        inboundCorrespondenceDefinitionStepDefs.createInbCorrDefUI(definitions, customFields);
        inboundCorrespondenceDefinitionStepDefs.navigateBackToTheList();
    }

    @When("I add a new Inbound Correspondence Definition without a {string}")
    public void iAddANewInboundCorrespondenceDefinitionWithoutA(String field) {
        inboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        browserUtils.get().waitForVisibility(inboundCorrespondenceDefinitionPage.inboundCorrespondenceHeader, 2);
        inboundCorrespondenceDefinitionStepDefs.fillRandomInboundCorrespondenceDefUI(field);
    }


    @When("I add a new Inbound Correspondence Definition without a {string} for the {string} project")
    public void iAddANewInboundCorrespondenceDefinitionWithoutAForTheProject(String field, String projectName) {
        inboundCorrespondenceDefinitionStepDefs.createRandomInboundCorrespondenceDef();
        inboundCorrespondenceDefinitionStepDefs.skipFieldsInboundCorrespondence(field);
        apiAutoUtilities.postInboundCorrespondenceDefinitionToServer(projectName);
    }

    @And("I have at least {int} Inbound Correspondence Definition")
    public void verifyAtLeastThreeInbCorDefinitionsExistStep(int definitions) {
        if (inboundCorrespondenceDefinitionListPage.nameColumn().size() < 3) {
            Map<String, String> customFields = new HashMap<>();
            inboundCorrespondenceDefinitionStepDefs.createInbCorrDefUI(definitions, customFields);
            inboundCorrespondenceDefinitionStepDefs.navigateBackToTheList();
        }
    }

    @When("I add a {string} to the Inbound Correspondence Definition containing {string} free text characters")
    public void iAddAToTheInboundCorrespondenceDefinitionContainingFreeTextCharacters(String Field, String characters) {
        inboundCorrespondenceDefinitionStepDefs.createRandomInboundCorrespondenceDef();
        inboundCorrespondenceDefinitionStepDefs.changeValueInboundCorrespondence(Field, RandomStringUtils.randomAscii(Integer.valueOf(characters)));
    }

    @Then("I should see the Inbound Correspondence Definition details displayed")
    public void iShouldSeeTheInboundCorrespondenceDefinitionDetailsDisplayed() {
        Assert.assertTrue(inboundCorrespondenceDefinitionPage.inboundCorrespondenceHeader.isDisplayed());
    }

    @Then("I should see the {int} Inbound Correspondence Definition details displayed")
    public void iShouldSeeTheInboundCorrespondenceDefinitionDetailsDisplayed(int count) {
        Assert.assertEquals(inboundCorrespondenceDefinitionListPage.nameColumn().size(), count);
    }

    @Then("I should see Inbound Correspondence Name is {int} characters displayed")
    public void iShouldSeeInboundCorrespondenceNameIsCharactersDisplayed(int length) {
        inboundCorrespondenceDefinitionStepDefs.assertLength(length, "NAME");
    }

    @Then("I should see Inbound Correspondence Barcode is {string} characters displayed")
    public void iShouldSeeInboundCorrespondenceBarcodeIsCharactersDisplayed(String length) {
        inboundCorrespondenceDefinitionStepDefs.assertLength(Integer.valueOf(length), "BARCODE");
    }

    @And("I click add button on Inbound Correspondence Definition")
    public void iClickAddButtonOnInboundCorrespondenceDefinition() {
        waitFor(3);
        inboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
    }

    @And("I add a new Inbound Correspondence Definition with Random Values")
    public void iAddANewInboundCorrespondenceDefinitionWithRandomValues() {
        inboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        inboundCorrespondenceDefinitionStepDefs.createRandomInboundCorrespondenceDef();
        inboundCorrespondenceDefinitionStepDefs.fillRandomInboundCorrespondenceDefUI();
        BrowserUtils.waitFor(2);
        browserUtils.get().waitForClickablility(inboundCorrespondenceDefinitionPage.saveButton, 6);
        new Actions(Driver.getDriver()).click(inboundCorrespondenceDefinitionPage.saveButton).perform();
        BrowserUtils.waitFor(6);
    }

    @Then("I verify I navigate to Inbound Correspondence Definition Screen")
    public void iVerifyINavigateToInboundCorrespondenceDefinitionScreen() {
        waitFor(2);
        inboundCorrespondenceDefinitionPage.inboundCorrespondencedefinitionheader.isDisplayed();
    }

    @Then("I verify I navigate to Inbound Correspondence Definition Details Screen")
    public void iVerifyINavigateToInboundCorrespondenceDefinitionDetailsScreen() {
        waitFor(2);
        inboundCorrespondenceDefinitionPage.inboundCorrespondenceHeader.isDisplayed();
    }


    @Then("I should see Inbound Correspondence Barcode left padded with zeroes")
    public void iShouldSeeInboundCorrespondenceBarcodeLeftPaddedWithZeroes() {
        inboundCorrespondenceDefinitionStepDefs.assertBarcode();
    }

    @When("I add a Barcode to the Inbound Correspondence Definition containing {string} free {string} characters")
    public void iAddABarcodeToTheInboundCorrespondenceDefinitionContainingFreeCharacters(String length, String format) {
        inboundCorrespondenceDefinitionStepDefs.createRandomInboundCorrespondenceDef();
        if (format.equalsIgnoreCase("NUMERIC")) {
            inboundCorrespondenceDefinitionStepDefs.changeValueInboundCorrespondence("Barcode", RandomStringUtils.random(Integer.valueOf(length), false, true));
        } else if (format.equalsIgnoreCase("ALPHANUMERIC")) {
            inboundCorrespondenceDefinitionStepDefs.changeValueInboundCorrespondence("Barcode", RandomStringUtils.random(Integer.valueOf(length), true, false));
        }
    }

    @And("I click on the back arrow button on Inbound Correspondence details")
    public void iClickOnTheBackArrowButtonOnInboundCorrespondenceDetails() {
        inboundCorrespondenceDefinitionStepDefs.navigateBackToTheList();
    }

    @And("I fill fields in create Inbound Correspondence screen")
    public void fillFieldsCreateInboundCorrespondenceDefinition() {
        inboundCorrespondenceDefinitionStepDefs.fillFieldsInboundCorrespondence();
    }

    @And("I click save button on create Inbound Correspondence screen")
    public void clickSaveButtonOnCreateInboundCorrespondenceDefinition() {
        inboundCorrespondenceDefinitionPage.saveButton.click();
    }

    @And("I post Inbound Correspondence Definition Details for the {string} project")
    public void iPostInboundCorrespondenceDefinitionDetailsForTheProject(String projectName) {
        apiAutoUtilities.postInboundCorrespondenceDefinitionToServer(projectName);
    }

    @Then("I verify {string} field displays as {string} on the Inbound Correspondence Definition List Page")
    public void iVerifyFieldDisplaysAsOnTheInboundCorrespondenceDefinitionListPage(String field, String expectedValue) {
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByField(field, expectedValue);
    }

    @And("I find the Inbound Correspondence with Definition Name value of {string}")
    public void iFindTheInboundCorrespondenceWithDefinitionNameValueOf(String name) {
        World.getWorld().inboundCorrespondenceDefinition.get().name = name;
    }

    @And("I verify the Task Types Field displays as the following text.")
    public void iVerifyTheTaskTypesFieldDisplaysAsTheFollowingText(String text) {
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByField("Task Types", text);
    }

    @And("I verify the Inbound Correspondence Definition Fields")
    public void iVerifyTheInboundCorrespondenceDefinitionFields() {
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByField("createdBy");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByField("createdDatetime");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByField("updatedBy");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByField("updatedDatetime");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByField("name");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByField("barcode");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByField("description");
    }

    @And("I get the Inbound Correspondence Definitions for the {string} project")
    public void iGetTheInboundCorrespondenceDefinitionsForTheProject(String ID) {
        inboundCorrespondenceDefinitionStepDefs.storeInboundCorrespondenceDefinitionIntoTheList(ID);
    }

    @Then("I should be able to all {string} characters accommodated in the {string} field for Inbound Correspondence Definition")
    public void iShouldBeAbleToAllCharactersAccommodatedInTheFieldForInboundCorrespondenceDefinition(String characters, String field) {
        switch (field) {
            case "Description":
                inboundCorrespondenceDefinitionStepDefs.assertDescriptionStringCount(Integer.valueOf(characters));
                break;
            case "Name":
                inboundCorrespondenceDefinitionStepDefs.assertNameStringCount(Integer.valueOf(characters));
                break;
        }
    }

    @And("I want to create a random Inbound Correspondence Definition for the {string} project")
    public void iWantToCreateARandomInboundCorrespondenceDefinitionForTheProject(String projectName) {
        inboundCorrespondenceDefinitionStepDefs.createRandomInboundCorrespondenceDef();
        apiAutoUtilities.postInboundCorrespondenceDefinitionToServer(projectName);
    }

    @And("I retrieve the previously created Inbound Correspondence Definition by {string}")
    public void iRetrieveThePreviouslyCreatedInboundCorrespondenceDefinitionBy(String field) {
        apiAutoUtilities.retrieveInboundCorrespondenceDefinition(field);
    }

    @And("I verify the Inbound Correspondence Definition Fields which retrieved by post")
    public void iVerifyTheInboundCorrespondenceDefinitionFieldsWhichRetrievedByPost() {
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByFieldRetrievedByPost("createdBy");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByFieldRetrievedByPost("createdDatetime");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByFieldRetrievedByPost("updatedBy");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByFieldRetrievedByPost("updatedDatetime");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByFieldRetrievedByPost("name");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByFieldRetrievedByPost("barcode");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByFieldRetrievedByPost("description");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByFieldRetrievedByPost("inboundCorrespondenceTaskRuleId");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByFieldRetrievedByPost("inboundCorrespondenceDefinitionId");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByFieldRetrievedByPost("requiredDataElements");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByFieldRetrievedByPost("createdByTaskRule");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByFieldRetrievedByPost("createdOnTaskRule");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByFieldRetrievedByPost("updatedByTaskRule");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByFieldRetrievedByPost("updatedOnTaskRule");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByFieldRetrievedByPost("taskTypeId");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByFieldRetrievedByPost("linkSameSetTaskFlag");
        inboundCorrespondenceDefinitionStepDefs.assertInboundCorrespondenceDefinitionByFieldRetrievedByPost("rank");
    }

    @Then("I validate I get 400 Bad Request for Inbound Correspondence Definition")
    public void iValidateIGetBadRequestForInboundCorrespondenceDefinition() {
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 400, "Status Code mismatched");
    }

    @And("I verify the Success message as Inbound Correspondence Definition created")
    public void iVerifyTheSuccessMessageAsInboundCorrespondenceDefinitionCreated() {
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Status Code mismatched");
        Assert.assertNotNull(World.getWorld().jsonPath.get().get("inboundCorrespondence.createdDatetime"), "Inbound Correspondence ID is Null");
    }

    @And("I verify the Name value must exactly match name in OnBase static message displays")
    public void iVerifyTheNameValueMustExactlyMatchNameInOnBaseStaticMessageDisplays() {
        browserUtils.get().waitForVisibility(inboundCorrespondenceDefinitionPage.nameValueMustMatchInOnBaseWarningMessage, 3);
        Assert.assertEquals(inboundCorrespondenceDefinitionPage.nameValueMustMatchInOnBaseWarningMessage.getText(), "Name value must exactly match name in OnBase");
    }

    @And("I validate and click Add Rule button on Inbound Correspondence Definition")
    public void iValidateAddRuleButton() {
        Assert.assertTrue(inboundCorrespondenceDefinitionListPage.addTaskRuleCorrespondenceDefinition.isDisplayed(), "ADD RUlE button is missing");
        Assert.assertTrue(inboundCorrespondenceDefinitionListPage.addTaskRuleCorrespondenceDefinition.isEnabled(), "ADD RUlE button is in disabled mode");
        inboundCorrespondenceDefinitionListPage.addTaskRuleCorrespondenceDefinition.click();
    }

    @And("I validate Edit Definition button removed from Inbound Correspondence Definition details screen")
    public void iValidateEditDefinitionButton() {
        Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(inboundCorrespondenceDefinitionListPage.editIBDefinition), "Edit Definition button is available");
    }


    @And("I view {string} IB Correspondence Definition type")
    public void iValidateAddRuleButton(String IBdeftype) {
        boolean status = false;
        int noofpages = inboundCorrespondenceDefinitionListPage.pagenumbers().size();

        for (int i = 1; i <= noofpages; i++) {
            status = inboundCorrespondenceDefinitionStepDefs.viewIBCorrespondneceDef(IBdeftype);
            if (status || noofpages == 1)
                break;
            inboundCorrespondenceDefinitionStepDefs.goToNextPage(i + 1);
        }

        Assert.assertTrue(status, IBdeftype + ": IB Correspondence definition not found");

    }

    @And("I view {string} IB Correspondence Definition typed")
    public void iValidateAddRuleButtond(String IBdeftype) {
        boolean Ibdefstatus = false;
        boolean isOnLastPage = false;
        do {
            Ibdefstatus = inboundCorrespondenceDefinitionStepDefs.viewIBCorrespondneceDef(IBdeftype);
            if (inboundCorrespondenceSearchStepDef.isOnLastPage()) {
                isOnLastPage = true;
            }
            inboundCorrespondenceSearchStepDef.goToNextPage();
        } while (!isOnLastPage);

        Assert.assertTrue(Ibdefstatus, ": IB Correspondence definition not found");
        System.out.println("finish");


    }

    @And("I validate new empty row for Add Task Behavior")
    public void iValidateEmptyRowforAddTaskRule() {
        Assert.assertTrue(inboundCorrespondenceDefinitionListPage.emptytaskrow.isDisplayed(), "Empty Task row is missing");
    }

    @And("I validate Delete Task Type Rule")
    public void iValidateDeleteTaskRule() {
        Assert.assertTrue(inboundCorrespondenceDefinitionListPage.deleteTaskRuleCorrespondenceDefinition.isDisplayed(), "Delete Task Rule button is missing");
        inboundCorrespondenceDefinitionListPage.deleteTaskRuleCorrespondenceDefinition.click();
        new Actions(Driver.getDriver()).click(inboundCorrespondenceDefinitionPage.saveButton).perform();
        waitFor(2);
        Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(inboundCorrespondenceDefinitionListPage.emptytaskrow), "Unable to Delete Task Rule ");
    }

    @And("I validate Delete all Task Type Rules")
    public void iValidateDeleteMultipleTaskRules() {
        if (uiAutoUitilities.get().quickIsDisplayed(inboundCorrespondenceDefinitionListPage.deleteTaskRuleCorrespondenceDefinition)) {
            List<WebElement> deltaskrules = inboundCorrespondenceDefinitionListPage.deletemultipletask();
            for (int i = 0; i < deltaskrules.size(); i++) {
                inboundCorrespondenceDefinitionListPage.deleteTaskRuleCorrespondenceDefinition.click();
                waitFor(2);
            }
            new Actions(Driver.getDriver()).click(inboundCorrespondenceDefinitionPage.saveButton).perform();
        }
        Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(inboundCorrespondenceDefinitionListPage.deleteTaskRuleCorrespondenceDefinition), "Unable to Delete Task Rule ");
    }

    @And("I try to exit without Save Task Type Rule")
    public void iValidateCancelwithoutsaveexit() {
        inboundCorrespondenceDefinitionListPage.createonlyonetaskperset.click();
        inboundCorrespondenceDefinitionListPage.arKeyboardBackSapce.click();
    }

    @And("I click {string} when given the option to confirm cancel or continue in warning popup")
    public void iClickWhenGivenTheOptionToConfirmCancelOrContinue(String button) {
        if (button.equalsIgnoreCase("cancel"))
            inboundCorrespondenceDefinitionListPage.warningbuttoncancel.click();
        else if (button.equalsIgnoreCase("continue"))
            inboundCorrespondenceDefinitionListPage.warningbuttoncontinue.click();
    }

    @And("I add a new Inbound Correspondence Definition Task Rules with given Values")
    public void iAddANewInboundCorrespondenceDefinitionTaskRulesWithRandomValues(Map<String, String> dataTable) {
        inboundCorrespondenceDefinitionListPage.addTaskRuleCorrespondenceDefinition.click();
        inboundCorrespondenceDefinitionStepDefs.fillRandomInboundCorrespondenceDefTaskRuleUI(dataTable);
        BrowserUtils.waitFor(2);
        browserUtils.get().waitForClickablility(inboundCorrespondenceDefinitionPage.saveButton, 6);
        new Actions(Driver.getDriver()).click(inboundCorrespondenceDefinitionPage.saveButton).perform();

    }

    @And("I validate Inbound Correspondence Definition Task Rules displayed in UI")
    public void ivalidateInboundCorrespondenceDefinitionTaskRulesValuesDispalyed(Map<String, String> dataTable) {
        BrowserUtils.waitFor(2);
        inboundCorrespondenceDefinitionStepDefs.validateInboundCorrespondenceDefTaskRuleDispalyedinUI(dataTable);
    }

    @Then("I validate the Inbound Correspondence Definition created success message")
    public void iShouldTheCorrespondenceDefinitionWasSuccessfullyAdded() {
        browserUtils.get().waitForClickablility(inboundCorrespondenceDefinitionPage.createdsuccessmsg, 5);
        Assert.assertTrue(inboundCorrespondenceDefinitionPage.createdsuccessmsg.isDisplayed());
    }

    @Then("I validate the Inbound Correspondence Definition update success message")
    public void iShouldTheCorrespondenceDefinitionWasSuccessfullyUpdated() {
        browserUtils.get().waitForClickablility(inboundCorrespondenceDefinitionPage.updatesuccessmsg, 5);
        Assert.assertTrue(inboundCorrespondenceDefinitionPage.updatesuccessmsg.isDisplayed());
    }

    @Then("I validate the Task Rank in Ascending Order")
    public void ivalidateTaskRankInAscendingOrder() {
        waitFor(5);
        List<WebElement> rankorders = inboundCorrespondenceDefinitionPage.rankorders();
        List<Integer> ranks = new ArrayList<Integer>();

        for (WebElement rank : rankorders) {
            ranks.add(Integer.parseInt(rank.getText()));
        }
        Assert.assertTrue(ranks.stream().sorted().collect(Collectors.toList()).equals(ranks), "Task Rank order not in ascending order");

    }

    @And("I verify there is no edit button on Inbound Correspondence Definition")
    public void iVerifyThereIsNoEditButtonOnInboundCorrespondenceDefinition() {
        browserUtils.get().waitForVisibility(inboundCorrespondenceDefinitionPage.inboundCorrespondenceHeader, 3);
        inboundCorrespondenceDefinitionStepDefs.assertElementIsNotDisplayed(inboundCorrespondenceDefinitionPage.editDefinitionButton);
    }

    @And("I validate Task Row Content")
    public void iValidateTaskRowContent() {
        browserUtils.get().waitForClickablility(inboundCorrespondenceDefinitionPage.saveButton, 6);
        new Actions(Driver.getDriver()).click(inboundCorrespondenceDefinitionPage.saveButton).perform();
        Assert.assertTrue(inboundCorrespondenceDefinitionPage.rankreqmsg.isDisplayed(), "Rank Required Warning message missing");
        Assert.assertTrue(inboundCorrespondenceDefinitionPage.tasktypereqmsg.isDisplayed(), "Task Type Required Warning message missing");
        String status = inboundCorrespondenceDefinitionPage.taskperset.getAttribute("value");
        Assert.assertTrue(status.equals("false"), "CREATE ONLY ONE TASK PER SET is checked by default");

        // validating Rank dropdown values 1 to 99
        ArrayList<String> expectedrankvalues = new ArrayList<String>();
        ArrayList<String> actualrankvalues = new ArrayList<String>();

        for (int i = 1; i < 100; i++)
            expectedrankvalues.add(String.valueOf(i));

        inboundCorrespondenceDefinitionPage.rank.click();
        List<WebElement> rankvalues = inboundCorrespondenceDefinitionPage.listvalues();
        for (WebElement i : rankvalues)
            actualrankvalues.add(i.getText());
        actualrankvalues.remove(0);

        Assert.assertTrue(actualrankvalues.equals(expectedrankvalues), "Task Rank order has missing values");
        action.sendKeys(Keys.ESCAPE).build().perform();

        // validating task types values
        ArrayList expectedtaskTypes = new ArrayList<String>();
        ArrayList actualtaskTypes = new ArrayList<String>();
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("taskTypeList");

        for (int i = 0; i < json.size(); i++) {
            JSONObject temp = new JSONObject(json.get(i).toString());
            if (temp.getString("status").equalsIgnoreCase("Active")) {
                expectedtaskTypes.add(temp.getString("taskTypeName"));
            }
        }

        inboundCorrespondenceDefinitionPage.tasktype.click();
        List<WebElement> tasktypevalues = inboundCorrespondenceDefinitionPage.listvalues();
        for (WebElement i : tasktypevalues)
            actualtaskTypes.add(i.getText());

        actualtaskTypes.remove(0);
        System.out.println("expectedtaskTypes : " + expectedtaskTypes.toString());
        System.out.println("actualtaskTypes   : " + actualtaskTypes.toString());
        Assert.assertTrue(actualtaskTypes.equals(expectedtaskTypes), "Task Types has missing values");
        action.sendKeys(Keys.ESCAPE).build().perform();

        //validating REQUIRED DATA ELEMENTS dropdown
        ArrayList actualreqkeys = new ArrayList<String>();
        ArrayList expectedreqkeys = new ArrayList<String>();
        expectedreqkeys.add("Case ID");
        expectedreqkeys.add("Consumer ID");
        expectedreqkeys.add("Application ID");

        inboundCorrespondenceDefinitionPage.reqkeys.click();
        for (WebElement i : inboundCorrespondenceDefinitionPage.listreqkeys())
            actualreqkeys.add(i.getText());
        Assert.assertTrue(actualreqkeys.equals(expectedreqkeys), "Required Keys has missing values");
        action.sendKeys(Keys.ESCAPE).build().perform();


        //validating Sibiling IB Correspondence Types  dropdown

        ArrayList expectedsibilingtypes = new ArrayList<String>();
        ArrayList actualsibilingtypes = new ArrayList<String>();
        List ibdef =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundTypes().getJsonObject("content");
        for (int i = 0; i < ibdef.size(); i++) {
            HashMap ib = (HashMap) ibdef.get(i);
            expectedsibilingtypes.add(ib.get("name"));
        }

        inboundCorrespondenceDefinitionPage.documentset.click();
        for (WebElement i : inboundCorrespondenceDefinitionPage.listsiblingtypes())
            actualsibilingtypes.add(i.getText());

        System.out.println("expectedsibilingtypes : " + expectedsibilingtypes.toString());
        System.out.println("actualsibilingtypes   : " + actualsibilingtypes.toString());
        Assert.assertTrue(actualsibilingtypes.equals(expectedsibilingtypes), "Sibiling IB Correspondences has missing values");
        action.sendKeys(Keys.ESCAPE).build().perform();

    }
}//end of class
