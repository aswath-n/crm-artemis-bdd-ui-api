package com.maersk.dms.steps;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.pages.crm.CRMApplicationSearchPage;
import com.maersk.crm.pages.crm.CRMCaseContactDetailsPage;
import com.maersk.crm.pages.crm.CRMConsumerSearchResultPage;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.World;
import com.maersk.dms.pages.CreateOutboundCorrespondencePage;
import com.maersk.dms.pages.ViewInboundCorrespondenceDetailsUIAPIPage;
import com.maersk.dms.step_definitions.InboundCorrespondenceNoteEventStepDefs;
import com.maersk.dms.step_definitions.InboundCorrespondenceSearchStepDef;
import com.maersk.dms.step_definitions.InboundDocumentTaskStepDefs;
import com.maersk.dms.step_definitions.ViewInboundCorrespondenceDetailsUIAPIStepDefs;
import com.maersk.dms.utilities.APIAutoUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.maersk.crm.utilities.BrowserUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class InboundCorrespondenceSearchSteps extends CRMUtilities implements ApiBase {
    private final ThreadLocal<InboundCorrespondenceSearchStepDef> inboundCorrespondenceSearchStepDef = ThreadLocal.withInitial(InboundCorrespondenceSearchStepDef::new);
    private InboundCorrespondenceNoteEventStepDefs inboundCorrespondenceNoteEventStepDefs = new InboundCorrespondenceNoteEventStepDefs();
    private final ThreadLocal<InboundDocumentTaskStepDefs> inboundDocumentTaskStepDefs = ThreadLocal.withInitial(InboundDocumentTaskStepDefs::new);
    private CRMCaseContactDetailsPage caseContactDetailsPage = new CRMCaseContactDetailsPage();
    private final ThreadLocal<ViewInboundCorrespondenceDetailsUIAPIStepDefs> viewInboundCorrespondenceDetailsUIAPIStepDefs = ThreadLocal.withInitial(ViewInboundCorrespondenceDetailsUIAPIStepDefs::new);
    ViewInboundCorrespondenceDetailsUIAPIPage viewInboundCorrespondenceDetailsUIAPIPage = new ViewInboundCorrespondenceDetailsUIAPIPage();
    private CRMConsumerSearchResultPage crmConsumerSearchResultPage = new CRMConsumerSearchResultPage();
    private CRMContactRecordUIPage crmContactRecordUIPage = new CRMContactRecordUIPage();
    private CRMApplicationSearchPage crmSearchApplicationPage = new CRMApplicationSearchPage();
    private final ThreadLocal<BrowserUtils> browserUtils = ThreadLocal.withInitial(BrowserUtils::new);

    @Then("I should see that all the inbound correspondence search results have the following values")
    public void verifySearchResults(Map<String, String> dataTable) {
        boolean isOnLastPage = false;
        do {
            inboundCorrespondenceSearchStepDef.get().verifyColumnValuesFromResults(dataTable);
            if (inboundCorrespondenceSearchStepDef.get().isOnLastPage()) {
                isOnLastPage = true;
            }
            inboundCorrespondenceSearchStepDef.get().goToNextPage();
        } while (!isOnLastPage);
        System.out.println("finish");
    }

    @Then("I should see that the column names for the search results have the following values")
    public void iShouldSeeThatTheColumnNamesForTheSearchResultsHaveTheFollowingValues(List<String> expectedHeaders) {
        inboundCorrespondenceSearchStepDef.get().verifyColumnNames(expectedHeaders);
    }

    @Then("I should see all the labels have following values")
    public void iShouldSeeAllTheLabelsHaveFollowingValues(List<String> expectedLabels) {
        inboundCorrespondenceSearchStepDef.get().verifyLabels(expectedLabels);
    }

    @And("I verify channel drop down value in the Inbound Correspondence Search")
    public void iVerifyChannelDropDownValueInTheInboundCorrespondenceSearch(List<String> dataTables) {
        dataTables = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundChannels();
        inboundCorrespondenceSearchStepDef.get().verifyChannelDropdown(dataTables);
    }

    @And("I verify the status drop down value in the Inbound Correspondence Search")
    public void iVerifyTheStatusDropDownValueInTheInboundCorrespondenceSearch(List<String> dataTables) {
        inboundCorrespondenceSearchStepDef.get().verifyStatusDropdown(dataTables);
    }

    @When("I click search button on Inbound Correspondence Search Page")
    public void iClickSearchButtonOnInboundCorrespondenceSearchPage() {
        inboundCorrespondenceSearchStepDef.get().clickOnSearchButton();
    }

    @Then("I should see an Error Alert Message")
    public void iShouldSeeAnErrorAlertMessage(String message) {
        inboundCorrespondenceSearchStepDef.get().verifyErrorMessage(message);
    }

    @And("I have a Inbound Document that was uploaded {string}")
    public void iHaveAInboundDocumentThatWasUploaded(String when) {
        inboundDocumentTaskStepDefs.get().uploadInboundDocument("maersk Case + Consumer");

    }

    @And("I fill in the following search fields in Inbound Search Page")
    public void iFillInTheFollowingSearchFieldsInInboundSearchPage(List<String> dataTable) {
        inboundCorrespondenceSearchStepDef.get().fillInRandomValuesNoSearch(dataTable);
    }

    @When("I click on Cancel button to clear out the the Inbound Search Criteria")
    public void iClickOnCancelButtonToClearOutTheTheInboundSearchCriteria() {
        inboundCorrespondenceSearchStepDef.get().clickOnCancelButton();
    }

    @When("I click on Cancel button to clear out the Application Search Criteria")
    public void iClickOnCancelButtonToClearOutTheApplicationSearchCriteria() {
        inboundCorrespondenceSearchStepDef.get().clickOnCancelButtonForApplicationSearch();
    }

    @Then("I should see all of the Inbound Search Criteria is cleared")
    public void iShouldSeeAllOfTheInboundSearchCriteriaIsCleared() {
        inboundCorrespondenceSearchStepDef.get().verifyAllFieldsCleared();
    }

    @And("the Inbound Search date received operator is {string}")
    public void theInboundSearchDateReceivedOperatorIs(String operator) {
        inboundCorrespondenceSearchStepDef.get().verifyDateReceivedOperatorDropdown();
    }

    @Then("all the CID elements are hyperlinks")
    public void allTheCIDElementsAreHyperlinks() {
        boolean isOnLastPage = false;
        do {
            inboundCorrespondenceSearchStepDef.get().verifyCIDhyperlink();
            if (inboundCorrespondenceSearchStepDef.get().isOnLastPage()) {
                isOnLastPage = true;
            }
            inboundCorrespondenceSearchStepDef.get().goToNextPage();
        } while (!isOnLastPage);
        System.out.println("finish");
    }

    @Then("I should be navigated to the Inbound Correspondence Details page for that Document")
    public void iShouldBeNavigatedToTheInboundCorrespondenceDetailsPageForThatDocument() {
        inboundCorrespondenceSearchStepDef.get().verifyClickedOnCIDPage();
    }

    @When("I select the Inbound Document CID link {string} on CID list")
    public void iSelectTheInboundDocumentCIDLinkOnCIDList(String cid) {
        if ("firstAvailable".equalsIgnoreCase(cid)) {
            inboundCorrespondenceSearchStepDef.get().getCIDFirstValue();
            inboundCorrespondenceNoteEventStepDefs.selectFirstInList();
            BrowserUtils.waitFor(2);
        } else if ("lastAvailable".equalsIgnoreCase(cid)) {
            inboundCorrespondenceSearchStepDef.get().getCIDlastValue();
            inboundCorrespondenceNoteEventStepDefs.selectlastInList();
        } else {
            inboundCorrespondenceNoteEventStepDefs.selectSpecificCidInList(cid);
        }
    }

    @And("I navigate back to the Inbound Correspondence Search results")
    public void iNavigateBackToTheInboundCorrespondenceSearchResults() {
        inboundCorrespondenceSearchStepDef.get().clickONInboundDetailHeader();
    }

    @Then("I should see that I am in the same place in the search results where I left off")
    public void iShouldSeeThatIAmInTheSamePlaceInTheSearchResultsWhereILeftOff() {
        boolean isOnLastPage = false;
        do {
            inboundCorrespondenceSearchStepDef.get().getExpectedCIDlist();
            if (inboundCorrespondenceSearchStepDef.get().isOnLastPage()) {
                isOnLastPage = true;
            }
            inboundCorrespondenceSearchStepDef.get().hoverPageButtonNextPage();
        } while (!isOnLastPage);
        System.out.println("finish");
        inboundCorrespondenceSearchStepDef.get().goBacktoFirstSearchPage();
        inboundCorrespondenceSearchStepDef.get().verifyActualExpectedCIDlist();
    }

    @And("I get a list of the Inbound Correspondence Search CIDs")
    public void iGetAListOfTheInboundCorrespondenceSearchCIDs() {
        boolean isOnLastPage = false;
        do {
            inboundCorrespondenceSearchStepDef.get().getListactualofCID();
            if (inboundCorrespondenceSearchStepDef.get().isOnLastPage()) {
                isOnLastPage = true;
            }
            inboundCorrespondenceSearchStepDef.get().goToNextPage();
        } while (!isOnLastPage);
        inboundCorrespondenceSearchStepDef.get().goBacktoFirstSearchPage();
        System.out.println("finish");
    }

    @Then("I should not see the {string} CId in the result list")
    public void iShouldNotSeeTheCIdInTheResultList(String cId) {
        inboundCorrespondenceSearchStepDef.get().checkCreatedCidNotInList(cId);
    }

    @And("I have a Inbound Document Type of {string} linked to a following case number {string}")
    public void iHaveAInboundDocumentTypeOfLinkedToAFollowingCaseNumber(String inboundType, String caseNum) {
        inboundDocumentTaskStepDefs.get().uploadInboundDocument(inboundType, caseNum);
    }

    @Then("I should not see the {string} CId in the Contact and Details Tab Inbound CId list")
    public void iShouldNotSeeTheCIdInTheContactAndDetailsTabInboundCIdList(String cId) {
        inboundCorrespondenceSearchStepDef.get().checkcIdnotInCaseContactDetailsInboList(cId);
    }

    @And("I get a list of the Inbound Correspondence Search CIDs in the Case Contact Details Tab")
    public void iGetAListOfTheInboundCorrespondenceSearchCIDsInTheCaseContactDetailsTab() {
        browserUtils.get().scrollToElement(caseContactDetailsPage.inbCorrHeader);
        boolean isOnLastPage = false;
        do {
            inboundCorrespondenceSearchStepDef.get().getListOfCaseContactDetailsCIDs();
            if (inboundCorrespondenceSearchStepDef.get().isOnLastPage()) {
                isOnLastPage = true;
            }
            inboundCorrespondenceSearchStepDef.get().goToNextPage();
        } while (!isOnLastPage);
        inboundCorrespondenceSearchStepDef.get().goBacktoFirstSearchPage();
        System.out.println("finish");

    }

    @And("I click on a view icon of {string} Inbound Search Results")
    public void iClickOnAViewIconOfInboundSearchResults(String inbDocument) {
        BrowserUtils.waitFor(2);
        inboundCorrespondenceSearchStepDef.get().clickViewInbDocument(inbDocument);
    }

    @And("I click on a CID of {string} Inbound Search Results")
    public void iClickOnACIDOfInboundSearchResults(String inbDocument) {
        inboundCorrespondenceSearchStepDef.get().navToInbDetails(inbDocument);

    }

    @And("I click on the {string} in the Inbound Correspondence Details page")
    public void iClickOnTheInTheInboundCorrespondenceDetailsPage(String inbDocument) {
        switch (inbDocument) {
            case "view icon":
                viewInboundCorrespondenceDetailsUIAPIStepDefs.get().clickViewIconInbDocumentGenerated();
                break;
        }
    }

    @Then("I should see that I have selected the following values for CORRESPONDENCETYPE")
    public void iShouldSeeThatIHaveSelectedTheFollowingValuesForCORRESPONDENCETYPE(Map<String, String> dataTable) {
        inboundCorrespondenceSearchStepDef.get().verifyTypeDropDown(dataTable);
    }

    @Then("I should see that I have selected the following values for STATUS")
    public void iShouldSeeThatIHaveSelectedTheFollowingValuesForSTATUS(Map<String, String> dataTable) {
        inboundCorrespondenceSearchStepDef.get().verifyStatusDropDownSelected(dataTable);
    }

    @And("the Inbound Document {string} is linked to caseId {string}")
    public void theInboundDocumentIsLinkedToCaseId(String cid, String caseId) {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().linkInbDocToCaseId(cid, caseId);

    }

    @Then("I should see that the following items are not visible for the CoverVA tenant on the Inbound Search Screen")
    public void iShouldSeeThatTheFollowingItemsAreNotVisibleForTheCoverVATenantOnTheInboundSearchScreen(List<String> elements) {
        inboundCorrespondenceSearchStepDef.get().verifyCoverVAHidingInbSearchOptions(elements);
    }

    @Then("I should see the Type drop down is in alphabetical order")
    public void iShouldSeeTheTypeDropDownIsInAlphabeticalOrder() {
        inboundCorrespondenceSearchStepDef.get().verifyTypeDropDown();
    }

    @When("I search for Inbound Correspondences by the following criteria")
    public void iSearchForInboundCorrespondencesByTheFollowingCriteria(Map<String, String> data) {
        JsonObject request = new JsonObject();
        JsonObject metaData;
        JsonArray md = new JsonArray();
        JsonArray channelArray = new JsonArray();
        for (String criteria : data.keySet()) {
            switch (criteria) {
                case "channel":
                    channelArray.add(data.get(criteria));
                    break;
                case "caseId":
                    if ("Random".equalsIgnoreCase(data.get(criteria))) {
                        request.addProperty(criteria, String.valueOf(World.generalSave.get().get("CaseID")));
                    } else if ("previouslyCreated".equalsIgnoreCase(data.get(criteria))) {
                        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                        String caseNumb = caseConsumerId.get("caseId");
                        request.addProperty(criteria, caseNumb);
                    } else {
                        request.addProperty(criteria, data.get(criteria));
                    }
                    break;
                case "consumerId":
                    if ("Random".equalsIgnoreCase(data.get(criteria))) {
                        request.addProperty(criteria, String.valueOf(World.generalSave.get().get("ConsumerID")));
                    } else if ("previouslyCreated".equalsIgnoreCase(data.get(criteria))) {
                        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                        String consumerNum = caseConsumerId.get("consumerId");
                        request.addProperty(criteria, consumerNum);
                    } else {
                        request.addProperty(criteria, data.get(criteria));
                    }
                    break;
                case "id":
                    if ("Random".equalsIgnoreCase(data.get(criteria))) {
                        request.addProperty(criteria, String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital")));
                    } else {
                        request.addProperty(criteria, data.get(criteria));
                    }
                    break;
                case "setId":
                    if ("Random".equalsIgnoreCase(data.get(criteria))) {
                        request.addProperty(criteria, String.valueOf(World.generalSave.get().get("setId")));
                    } else {
                        request.addProperty(criteria, data.get(criteria));
                    }
                    break;
                case "dateReceived":
                    if ("Random".equalsIgnoreCase(data.get(criteria))) {
                        request.addProperty("dateReceivedMax", LocalDate.now().toString());
                        request.addProperty("dateReceivedMin", LocalDate.now().toString());
                    }
                    break;
                default:
                    if (criteria.startsWith("METADATA:")) {
                        metaData = new JsonObject();
                        metaData.addProperty("name", criteria.replace("METADATA:", ""));
                        if ("Random".equalsIgnoreCase(data.get(criteria))) {
                            metaData.addProperty("value", String.valueOf(World.generalSave.get().get(criteria.replace("METADATA:", ""))));
                        } else {
                            metaData.addProperty("value", data.get(criteria));
                        }
                        md.add(metaData);
                    } else {
                        if ("Random".equalsIgnoreCase(data.get(criteria))) {
                            request.addProperty(criteria, String.valueOf(World.generalSave.get().get(criteria)));
                        } else {
                            request.addProperty(criteria, data.get(criteria));
                        }

                    }
            }
        }
        request.add("channel", channelArray);
        request.add("metaData", md);
        World.generalSave.get().put("searchResults", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().inboundSearch(String.valueOf(request)));
    }

    @Then("I should see the {string} Inbound Correspondence is in the search results")
    public void iShouldSeeTheInboundCorrespondenceIsInTheSearchResults(String cid) {
        if ("fromRequest".equalsIgnoreCase(cid) || "previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        } else if ("inboundDocumentId".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentId"));
        }
        int expected = Integer.parseInt(cid);
        JsonPath results = (JsonPath) World.generalSave.get().get("searchResults");
        List<Integer> ids = results.getList("correspondences.id");
        boolean found = false;
        for (Integer id : ids) {
            if (expected == id) {
                found = true;
            }
        }
        Assert.assertTrue(found, "results - \n" + results.prettyPrint());
    }

    @Then("I verify Case and Consumer option not available in the Create Links\\(s) dropdown")
    public void i_verify_Case_and_Consumer_option_not_available_in_the_Create_Links_s_dropdown() {
        browserUtils.get().waitForVisibility(viewInboundCorrespondenceDetailsUIAPIPage.createLinksDropdown, 3);
        viewInboundCorrespondenceDetailsUIAPIPage.createLinksDropdown.click();
        for (String value : browserUtils.get().getElementsText(viewInboundCorrespondenceDetailsUIAPIPage.createLinksDropdownValues)) {
            if (value.contains("CASE/CONSUMER")) {
                Assert.assertTrue(false, "Create links dropdown contains CASE/CONSUMER, verification failed");
            }
        }
    }

    @Then("I should see Create Link(s) button appears in Links component")
    public void iShouldSeeCreateLinksbuttonappearsinLinkscomponent() {
        Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.createLinksDropdown.isDisplayed());
    }

    @Then("I verify the link button is displayed")
    public void iVerifyTheLinkButtonIsDisplayed() {
        Assert.assertTrue(crmConsumerSearchResultPage.link.isDisplayed());
    }

    @Then("I click cancel button Request and Task Search")
    public void vIclickcancelbuttonRequestTaskSearch() {
        crmContactRecordUIPage.cancelButtonAfterCorres.click();
    }

    @And("I click the link button on task search page")
    public void iClickTheLinkButtonOnTaskSearchPage() {
        waitForVisibility(crmConsumerSearchResultPage.link, 5);
        crmConsumerSearchResultPage.link.click();
    }

    @And("I click the Unlink button on Correspondence")
    public void iClickTheUnLinkButtonOnCorres() {
        waitForVisibility(crmConsumerSearchResultPage.firstUnlinklButton, 5);
        crmConsumerSearchResultPage.firstUnlinklButton.click();
    }

    @Then("I select {string} option from Create Links\\(s) dropdown")
    public void i_select_option_in_the_Create_Links_s_dropdown(String option) {
        browserUtils.get().waitForVisibility(viewInboundCorrespondenceDetailsUIAPIPage.createLinksDropdown, 3);
        viewInboundCorrespondenceDetailsUIAPIPage.createLinksDropdown.click();
        switch(option.toUpperCase()){
            case "CASE/CONSUMER":
                viewInboundCorrespondenceDetailsUIAPIPage.caseconsumerlink.click();
                break;
            case "TASK/SERVICE REQUEST":
                viewInboundCorrespondenceDetailsUIAPIPage.taskservicerequestlink.click();
                break;
            case "APPLICATION":
                viewInboundCorrespondenceDetailsUIAPIPage.applicationlink.click();
                break;
            default:
                Assert.fail("No matching results found");

        }
    }

    @Then("I validate {string} is present in view inbound correspondence details page")
    public void i_validate_field_visible_in_view_ib_corr_page(String fieldname) {
        switch(fieldname.toUpperCase()){
            case "CASE & CONSUMER SEARCH":
                browserUtils.get().waitForVisibility(viewInboundCorrespondenceDetailsUIAPIPage.caseconsumersearchfieldlabel, 3);
               Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.caseconsumersearchfieldlabel.isDisplayed());
                break;
            case "VIEW INBOUND CORRESPONDENCE DETAILS":
                browserUtils.get().waitForVisibility(viewInboundCorrespondenceDetailsUIAPIPage.viewibcorrdeatilslabel, 3);
                Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.viewibcorrdeatilslabel.isDisplayed());
                break;
            default:
                Assert.fail("No matching results found");

        }
    }


    @Then("I validate the search results for case and consumer search")
    public void ivalidatesearchresults(){
        inboundCorrespondenceSearchStepDef.get().verifyInbCaseConsumerSearchvalues();
    }

    @Then("I validate Linking a {string} to the Inbound Correspondence")
    public void ivalidatelinkingcasetoibcorrespondence(String type){
        inboundCorrespondenceSearchStepDef.get().linkingCaseConsumertoibcorrespondence(type);
    }

    @Then("I validate viewing Case link to the Inbound Correspondence in cp")
    public void ivalidateviewingcaselinktoibcorrespondence(){
        inboundCorrespondenceSearchStepDef.get().viewingcaselinktoibcorrespondence();
    }

    @Then("I validate viewing Case and consumer link to the Inbound Correspondence with {string} values in cp")
    public void ivalidateviewingcaseandconsumerlinktoibcorrespondence(String type){
        inboundCorrespondenceSearchStepDef.get().viewingcaseandconsumerlinktoibcorrespondence(type);
    }

    @Then("I validate viewing Consumer link to the Inbound Correspondence in cp")
    public void ivalidateviewingconsumerlinktoibcorrespondence(){
        inboundCorrespondenceSearchStepDef.get().viewingconsumerlinktoibcorrespondence();
    }

    @And("I link application to inbound correspondence")
    public void iLinkApplicationToInboundCorrespondence() {
        inboundCorrespondenceSearchStepDef.get().linkingApplicationtoibcorrespondence();
    }

    @And("I view application linked to inbound correspondence")
    public void iViewApplicationLinkedToInboundCorrespondence() {
        inboundCorrespondenceSearchStepDef.get().viewingApplicationLinkedToInboundCorrespondence();
    }

    @And("I verify application ID doesn't function on search results")
    public void iVerifyApplicationIDDoesnTFunctionOnSearchResults() {
        inboundCorrespondenceSearchStepDef.get().clickApiCreatedApplicationID();
        Assert.assertTrue(crmSearchApplicationPage.searchTitle.isDisplayed(),"Search Title is not displayed");
        Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.inboundCorrDetHeader.isDisplayed(),"Application ID navigates away form Inbound Correspondence Details Page");
    }


    @And("I verify Link component displays on Inbound Correspondence Details Page")
    public void iVerifyLinkComponentDisplaysOnInboundCorrespondenceDetailsPage() {
        Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.linksHeader.isDisplayed(),"Links Header is not displayed");
    }
}