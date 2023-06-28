
package com.maersk.dms.steps;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.dms.step_definitions.ShowOutboundCorrespondenceDetailsStepDefs;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.text.ParseException;

public class ShowOutboundCorrespondenceDetailsSteps {
    ShowOutboundCorrespondenceDetailsStepDefs showOutboundCorrespondenceDetailsStepDefs = new ShowOutboundCorrespondenceDetailsStepDefs();
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities= ThreadLocal.withInitial(UIAutoUitilities::new);
    @When("I select the show details caret for an Outbound Correspondence by {string}")
    public void iSelectTheShowDetailsCaretForCorrespondence(String cid) {
        BrowserUtils.waitFor(9);
        uiAutoUitilities.get().findInOutboundCorrespondenceListByCID(cid);
        showOutboundCorrespondenceDetailsStepDefs.correspondenceDetailsCaratCID(cid);
    }

    @Then("I will see the {string}{string} is present")
    public void iWillSeeFieldPresent(String field, String cid) throws ParseException {
        showOutboundCorrespondenceDetailsStepDefs.requiredCorrespondenceDetailsFields(field, cid);
    }

    @Then("I will see the {string} , {string} is present")
    public void iWillSeeTheIsPresent(String field, String cid) throws ParseException{
        showOutboundCorrespondenceDetailsStepDefs.requiredCorrespondenceDetailsFields(field, cid);
    }
}

