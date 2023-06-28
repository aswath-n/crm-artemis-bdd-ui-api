package com.maersk.dms.steps;

import com.maersk.crm.pages.crm.CRMCaseAndConsumerSearchPage;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.tm.TMListOfProjectsPage;
import com.maersk.crm.step_definitions.CRMBusinessEvents;
import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.World;
import com.maersk.dms.step_definitions.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewOutboundCorrespondenceFullDetailsLinksSteps extends BrowserUtils{
    TMListOfProjectsPage tmListOfProjectsPage = new TMListOfProjectsPage();
    final ThreadLocal<PublishDPBIEventsOutboundCorrespondenceChangesStepDefs> outCorr = ThreadLocal.withInitial(PublishDPBIEventsOutboundCorrespondenceChangesStepDefs::new);
    CRMCaseAndConsumerSearchPage crmCaseAndConsumerSearchPage = new CRMCaseAndConsumerSearchPage();
    final ThreadLocal<ShowOutboundCorrespondenceDetailsStepDefs> showOutboundCorrespondenceDetailsStepDefs = ThreadLocal.withInitial(ShowOutboundCorrespondenceDetailsStepDefs::new);
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    final ThreadLocal<DPBIOutboundCorrespondenceConfigEventsSteps> dpbiOutboundCorrespondenceConfigEventsSteps = ThreadLocal.withInitial(DPBIOutboundCorrespondenceConfigEventsSteps::new);
    final ThreadLocal<String> endpointID = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> rawBody = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> caseId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> consumerId = ThreadLocal.withInitial(String::new);

    public ViewOutboundCorrespondenceFullDetailsLinksSteps() throws IOException {
    }

    @Given("I have requested to Create Outbound Correspondence request with a {string} of value {string}")
    public void iHaveRequestedToCreateOutboundCorrespondenceRequestWithAOfValue(String key, String value) throws IOException {
//        String oauthQAToken = outCorr.get().getOAuthQA();
        switch (key) {
            case "CaseId":
                rawBody.set(outCorr.get().rawBodyCreateCorrespondenceWithCaseId(value));
                endpointID.set(String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get())));
                System.out.println("Created Correspondence ID: " + endpointID);
                break;
        }
        caseId.set(value);
    }

    @Given("I have requested to Create Outbound Correspondence request with a {string} of value {string} and {string} of value {string}")
    public void iHaveRequestedToCreateOutboundCorrespondenceRequestWithAOfValueAndOfValue(String key, String value, String key1, String value1) throws IOException {
        String oauthQAToken = outCorr.get().getOAuthQA();
        switch (key1) {
            case "ConsumerId":
                rawBody.set(outCorr.get().rawBodyCreateCorrespondenceWithCIDConsumerId(value, value1));
                endpointID.set(outCorr.get().createCorrespondenceRecipientsID(oauthQAToken, rawBody.get()));
                System.out.println("Created Correspondence ID: " + endpointID);
                break;
        }
        caseId.set(value);
        consumerId.set(value1);
    }

    @And("I search for the {string} with value {string}")
    public void iSearchForTheWithValue(String key, String value) {
        Map<String, String> caseConsumerIds;
        switch (key) {
            case "CaseId":
                BrowserUtils.sendKeyToTextField(crmCaseAndConsumerSearchPage.caseIdField, value);
                //  outCorr.get().sendKeysToAnElement(crmCaseAndConsumerSearchPage.caseIdField, value);
                break;
            case "RandomCaseId":
                caseConsumerIds = (Map<String, String>) World.generalSave.get().get("caseConsumerIds");
                value = caseConsumerIds.get("caseId");
                outCorr.get().sendKeysToAnElement(crmCaseAndConsumerSearchPage.caseIdField, value);
                break;
            case "ConsumerId":
                outCorr.get().sendKeysToAnElement(crmCaseAndConsumerSearchPage.consumerIdField, value);
                break;
            case "RandomConsumerId":
                caseConsumerIds = (Map<String, String>) World.generalSave.get().get("caseConsumerIds");
                value = caseConsumerIds.get("consumerId");
                outCorr.get().sendKeysToAnElement(crmCaseAndConsumerSearchPage.consumerIdField, value);
                break;


        }
        contactRecord.searchButton.click();
    }

    @And("I search for the Outbound Correspondence {string}")
    public void iSearchForTheOutboundCorrespondence(String value) {
        if ("previouslyCreated".equalsIgnoreCase(value)) {
            Map<String, String> caseConsumerIds = (Map<String, String>) World.generalSave.get().get("caseConsumerIds");
            value = caseConsumerIds.get("caseId");
        }
        WebElement e = Driver.getDriver().findElement(By.xpath("//*[contains(text(),'" + value + "')]"));
        outCorr.get().clickIfElementIsDisplayed(e);
    }

    @When("I click on the correspondence id of the Outbound Correspondence {string}")
    public void iClickOnTheCorrespondenceIdOfTheOutboundCorrespondence(String args0) {
        endpointID.set(String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get())));
        WebElement e = Driver.getDriver().findElement(By.xpath("//td[text()='" + endpointID + "']"));
        if (outCorr.get().elementIsDisplayed(e)) {
            outCorr.get().clickIfElementIsDisplayed(e);
        } else {
            outCorr.get().clickIfElementIsDisplayed(Driver.getDriver().findElement(By.xpath("(//a[@aria-label='Go to page number 2'])[2]")));
            BrowserUtils.waitFor(3);
            if (outCorr.get().elementIsDisplayed(e)) {
                outCorr.get().clickIfElementIsDisplayed(e);
            } else {
                outCorr.get().clickIfElementIsDisplayed(Driver.getDriver().findElement(By.xpath("(//a[@aria-label='Go to page number 3'])[2]")));
                BrowserUtils.waitFor(3);
                if (outCorr.get().elementIsDisplayed(e)) {
                    outCorr.get().clickIfElementIsDisplayed(e);
                } else {
                    Assert.fail("CID with " + endpointID + " not found.");
                }
            }
        }
    }

    @Then("I should see the Link to the case has the proper values for {string} Case")
    public void iShouldSeeTheLinkToTheCaseHasTheProperValues(String caseId) {
        showOutboundCorrespondenceDetailsStepDefs.get().verifyLinksCaseRowInfo("Null", caseId, "Null", "--/--/----");
        BrowserUtils.waitFor(2);
        if (consumerId == null) {
            return;
        }
        showOutboundCorrespondenceDetailsStepDefs.get().verifyLinksConsumerProfileRowInfo("Active", consumerId.get(), "Consumer", dpbiOutboundCorrespondenceConfigEventsSteps.get().getCurrentDate());
    }

    @Then("I should see the Link to the consumer has the proper values for {string} Consumer")
    public void iShouldSeeTheLinkToTheConsumerHasTheProperValuesForConsumerId(String consumerId) {
        String date = "";
        BrowserUtils.waitFor(2);
        if(consumerId.equalsIgnoreCase("2564")){
            date="01/04/2023";
        }
        else{
            date= dpbiOutboundCorrespondenceConfigEventsSteps.get().getCurrentDate();
        }
        showOutboundCorrespondenceDetailsStepDefs.get().verifyLinksConsumerProfileRowInfo("Active", consumerId, "Consumer",  date);
    }

    @Then("I should see the Link to the service request has the proper values")
    public void iShouldSeeTheLinkToThServiceRequestHasTheProperValues(Map<String, String> dataTable) {
        showOutboundCorrespondenceDetailsStepDefs.get().verifyLinksServiceRequestRowInfo(dataTable.get("serviceRequestType"));
        BrowserUtils.waitFor(2);
    }

    @And("I have requested to Create Outbound Correspondence request with First Name {string} and CaseId as {string}")
    public void iHaveRequestedToCreateOutboundCorrespondenceRequestWithFirstNameAndCaseIdAs(String frstName, String caseId) throws IOException {
        String oauthQAToken = outCorr.get().getOAuthQA();
        rawBody.set(outCorr.get().rawBodyCreateCorrespondenceWithFirstNameAndCaseId(frstName, caseId));
        endpointID.set(outCorr.get().createCorrespondenceRecipientsID(oauthQAToken, rawBody.get()));
        System.out.println("Created Correspondence ID: " + endpointID);
    }

    @And("I have requested to Create Outbound Correspondence request with First Name {string} and ConsumerId as {string}")
    public void iHaveRequestedToCreateOutboundCorrespondenceRequestWithFirstNameAndConsumerIdAs(String frstName, String consumerId) throws IOException {
        String oauthQAToken = outCorr.get().getOAuthQA();
        rawBody.set(outCorr.get().rawBodyCreateCorrespondenceWithFirstNameAndConsumerId(frstName, consumerId));
        endpointID.set(outCorr.get().createCorrespondenceRecipientsID(oauthQAToken, rawBody.get()));
        System.out.println("Created Correspondence ID: " + endpointID);
    }

    @And("I should see the Outbound Correspondence {string} link to caseId {string} in the links section")
    public void iShouldSeeTheOutboundCorrespondenceLinkToCaseIdInTheLinksSection(String cid, String caseId) {
        showOutboundCorrespondenceDetailsStepDefs.get().verifyOutboundLinkedToCaseUI(cid, caseId);
    }

    @And("I should see the Outbound Correspondence {string} link to Inbound Correspondence {string} in the links section")
    public void iShouldSeeTheOutboundCorrespondenceLinkToInboundCorrespondenceInTheLinksSection(String outCid, String inbCid) {
        showOutboundCorrespondenceDetailsStepDefs.get().verifyOutboundLinkedToInboundDocumentUI(outCid, inbCid);
    }


    @Then("I should see the Outbound Correspondence {string} link to Application {string} in the links section")
    public void iShouldSeeTheOutboundCorrespondenceLinkToApplicationInTheLinksSection(String outCid, String applicationId) {
        showOutboundCorrespondenceDetailsStepDefs.get().verifyOutboundLinkedToApplicationUI(outCid, applicationId);
    }

    @Then("I should see the Outbound Correspondence {string} link to Missing Information {string} in the links section")
    public void iShouldSeeTheOutboundCorrespondenceLinkToMissingInformationInTheLinksSection(String outCid, String missingInformationId) {
        showOutboundCorrespondenceDetailsStepDefs.get().verifyOutboundLinkedToMissingInformation(outCid, missingInformationId);
    }

    @Then("I should see the Outbound Correspondence {string} link to Contact Record {string} in the links section")
    public void iShouldSeeTheOutboundCorrespondenceLinkToContactRecordInTheLinksSection(String outCid, String contactRecordId) {
        showOutboundCorrespondenceDetailsStepDefs.get().verifyOutboundLinkedToContactRecordUI(outCid, contactRecordId);
    }

    @And("Verify On Case And Contact Details Screen")
    public void iShouldSeeTheOutboundCorrespondenceLinkToInboundCorrespondenceInTheLinksSection() {
        Assert.assertTrue(showOutboundCorrespondenceDetailsStepDefs.get().verifyOnCaseAndContactScreen());
    }

    @Then("Verify Case Id {string} for Regarding Case Id in Outbound Correspondence Details Page")
    public void verifyCaseIdForRegardingCaseIdInOutboundCorrespondenceDetailsPage(String caseId) {
        showOutboundCorrespondenceDetailsStepDefs.get().verifyRegardingCaseIdUI(caseId);
    }

    @Then("Verify Case Id {string} for Regarding Consumer Id in Outbound Correspondence Details Page")
    public void verifyCaseIdForRegardingConsumerIdInOutboundCorrespondenceDetailsPage(String consumerId) {
        showOutboundCorrespondenceDetailsStepDefs.get().verifyRegardingConsumerIdUI(consumerId);
    }

    @Then("I validate navigation from OB links to {string} page")
    public void validateNavigationFromOBtorepectivepage(String oblinkid) {
        showOutboundCorrespondenceDetailsStepDefs.get().navigatefromOBtoLinkedId(oblinkid, "click");
    }

    @Then("I validate navigation from OB to Task details page for {string}")
    public void validateNavigationFromOBtoTaskdetaislpage(String taskid) {
        showOutboundCorrespondenceDetailsStepDefs.get().navigatefromOBtoTask(taskid, "");
    }

    @Then("I validate navigation back to {string} in OB details page")
    public void validateNavigationtoOB(String oblinkid) {
        showOutboundCorrespondenceDetailsStepDefs.get().navigatefromOBtoLinkedId(oblinkid, "validate");
        ;
    }

    @Then("I validate navigation to {string} Consumer Profile Demographic Info page")
    public void i_navigation_Consumer_Profile_Demographicpage(String consumerid) {
        showOutboundCorrespondenceDetailsStepDefs.get().navigation_ConsumerProfile_Demographicpage(consumerid);
    }

    @Then("I validate {string} for OB external links with internal_externalId value as {string}")
    public void i_validate_linkevent_for_ob_externallinks(String eventname, String internal_externalId) {
        if (internal_externalId.equalsIgnoreCase("random"))
            internal_externalId = World.generalSave.get().get("externalRefId").toString();
        new DPBIEventsStepDefs().searchEventByEventName(eventname);
        JsonPath response = (JsonPath) World.generalSave.get().get("response");

        List<String> list = response.getList("eventsList.content.payload");
        int count = 0;
        for (String load : list) {
            if (load.contains(internal_externalId)) {
                ++count;
            }
            if (count > 2)
                break;
        }
        Assert.assertTrue(count == 2);
/*
        System.out.println("response :" + response.getString("eventsList.content[0].payload"));
        Assert.assertTrue(response.getString("eventsList.content[0].payload").contains("\\\"internalRefType\\\":\\\"SERVICE_REQUEST\\\",\\\"externalRefType\\\":\\\"OUTBOUND_CORRESPONDENCE\\\""));
        Assert.assertTrue(response.getString("eventsList.content[1].payload").contains("\\\"internalRefType\\\":\\\"OUTBOUND_CORRESPONDENCE\\\",\\\"externalRefType\\\":\\\"SERVICE_REQUEST\\\""));
   */
    }



}
