package com.maersk.dms.steps;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.World;
import com.maersk.dms.step_definitions.InboundCorrespondenceNoteEventStepDefs;
import com.maersk.dms.step_definitions.PublishDPBIEventsOutboundCorrespondenceChangesStepDefs;
import com.maersk.dms.step_definitions.ViewInboundCorrespondenceDetailsUIAPIStepDefs;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ViewInboundCorrespondenceDetailsUIAPISteps {
    final ThreadLocal<Map<String, String>> infoUI = ThreadLocal.withInitial(HashMap::new);
    final ThreadLocal<ViewInboundCorrespondenceDetailsUIAPIStepDefs> viewInboundCorrespondenceDetailsUIAPIStepDefs = ThreadLocal.withInitial(ViewInboundCorrespondenceDetailsUIAPIStepDefs::new);
    private final ThreadLocal<InboundCorrespondenceNoteEventStepDefs> inboundCorrespondenceNoteEventStepDefs = ThreadLocal.withInitial(InboundCorrespondenceNoteEventStepDefs::new);

    @When("I click on given Inbound Correspondence hyperlink {string}")
    public void i_click_on_given_Inbound_Correspondence_hyperlink(String cid) {
        BrowserUtils.waitFor(7);
        if ("firstAvailable".equalsIgnoreCase(cid)) {
            viewInboundCorrespondenceDetailsUIAPIStepDefs.get().selectFirstInListFromInboundCorrespondenceComponent();
        } else if ("fromRequest".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
            viewInboundCorrespondenceDetailsUIAPIStepDefs.get().clickOnGivenID(cid);
        } else {
            viewInboundCorrespondenceDetailsUIAPIStepDefs.get().clickOnGivenID(cid);
        }
    }

    @Then("I store all the possible VIEW INBOUND CORRESPONDENCE DETAILS")
    public void i_store_all_the_possible_VIEW_INBOUND_CORRESPONDENCE_DETAILS() {
        infoUI.set(viewInboundCorrespondenceDetailsUIAPIStepDefs.get().getAllInfoViewInboundCorrDet());
    }

    @Then("I verify all the VIEW INBOUND CORRESPONDENCE DETAILS from BFF ECMS CORRESPONDENCE INBOUND DOCUMENT {string}")
    public void i_verify_all_the_VIEW_INBOUND_CORRESPONDENCE_DETAILS_from_BFF_ECMS_CORRESPONDENCE_INBOUND_DOCUMENT(String id) throws IOException {
        String searchResponse = viewInboundCorrespondenceDetailsUIAPIStepDefs.get().inboundDocumentDetailsDocId(PublishDPBIEventsOutboundCorrespondenceChangesStepDefs.getOAuthQA(), id);
        for (String eachValue : infoUI.get().values()) {
            if (!eachValue.isEmpty()) {
                Assert.assertTrue(searchResponse.contains(eachValue));
            }
        }

    }


    @Then("I should see the the following tabs in the Inbound Correspondence Details Screen")
    public void iShouldSeeTheTheFollowingTabsInTheInboundCorrespondenceDetailsScreen(Map<String, String> dataTable) {
        viewInboundCorrespondenceDetailsUIAPIStepDefs.get().verifyTabsNames();
    }

    @Then("I should see the the following labels on the {string} tab on the Inbound Correspondence Details Screen")
    public void iShouldSeeTheTheFollowingLabelsOnTheTabOnTheInboundCorrespondenceDetailsScreen(String tab, Map<String, String> dataTable) {
        switch (tab.toUpperCase()) {
            case "SOURCE DETAILS":
                viewInboundCorrespondenceDetailsUIAPIStepDefs.get().verifyLabelsSourceDetailsTabFromApi(dataTable);
                break;
            case "METADATA":
                viewInboundCorrespondenceDetailsUIAPIStepDefs.get().verifyLabelsMetadataTabFromApi(dataTable);
                break;
            case "HISTORY":
                viewInboundCorrespondenceDetailsUIAPIStepDefs.get().verifyLabelsHistoryTabFromApi(dataTable);
                break;
            case "UPPER SECTION OF INB DETAILS":
                viewInboundCorrespondenceDetailsUIAPIStepDefs.get().verifyLabelsUPPERSECTIONFromApi(dataTable);
                break;
            default:
                Assert.fail("the tab passed in from cucumber feature file is not matching any case, please add a case or change value from feature file | " + tab);
        }
    }

    @And("I should see the all the following values on the {string} tab on the Inbound Correspondence Details Screen are correct")
    public void iShouldSeeTheAllTheFollowingValuesOnTheTabOnTheInboundCorrespondenceDetailsScreenAreCorrect(String tab, Map<String, String> dataTable) {
        switch (tab.toUpperCase()) {
            case "SOURCE DETAILS":
                viewInboundCorrespondenceDetailsUIAPIStepDefs.get().verifyValuesSourceDetailsTabFromApi(dataTable);
                break;
            case "METADATA":
                viewInboundCorrespondenceDetailsUIAPIStepDefs.get().verifyValuesMetadataTabFromApi(dataTable);
                break;
            case "STATUS HISTORY":
                viewInboundCorrespondenceDetailsUIAPIStepDefs.get().verifyValuesStatusHistoryTabFromApi(dataTable);
                break;
            case "UPPER SECTION OF INB DETAILS":
                viewInboundCorrespondenceDetailsUIAPIStepDefs.get().verifyValuesUPPERSECTIONFromApi(dataTable);
                break;
            default:
                Assert.fail("the tab passed in from cucumber feature file is not matching any case, please add a case or change value from feature file | " + tab);
        }
    }

    @Then("I should see the the following labels ARE HIDDEN WHEN EMPTY on the {string} tab on the Inbound Correspondence Details Screen")
    public void iShouldSeeTheTheFollowingLabelsAREHIDDENWHENEMPTYOnTheTabOnTheInboundCorrespondenceDetailsScreen(String tab, Map<String, String> dataTable) {
        viewInboundCorrespondenceDetailsUIAPIStepDefs.get().verifyHiddenLabels(dataTable);
    }

    @And("I click MetaDataTab On Inbound Correspondence Detail Page")
    public void iVerifyInboundCorrespondencePageAndClickMetaDataTab(){
        viewInboundCorrespondenceDetailsUIAPIStepDefs.get().clickMetaDataTab();
    }

    @And("I retrieve MetaData Information")
    public void iRetrieveMetaDataInformation(){
        viewInboundCorrespondenceDetailsUIAPIStepDefs.get().getMetaDataInformation();
    }

    @And ("I retrieve MetaData Records")
    public void iRetrieveMetaDataRecords(){
        viewInboundCorrespondenceDetailsUIAPIStepDefs.get().getMetaDataRecords();
    }

    @Then("I should see the status history tab is no longer visible")
    public void iShouldSeeTheStatusHistoryTabIsNoLongerVisible() {
        viewInboundCorrespondenceDetailsUIAPIStepDefs.get().verifyStatusHistoryGone();
    }
}
