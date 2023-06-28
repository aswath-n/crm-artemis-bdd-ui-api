package com.maersk.dms.steps;

import com.maersk.dms.pages.*;
import com.maersk.dms.step_definitions.ChanelDefinitionWithinEffectiveDatesStepsDefs;
import com.maersk.dms.step_definitions.DPBIOutboundCorrespondenceConfigEventsStepDefs;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ChanelDefinitionWithinEffectiveDatesSteps {

    final ThreadLocal<ChanelDefinitionWithinEffectiveDatesStepsDefs> chanelDefinitionWithinEffectiveDatesStepsDefs = ThreadLocal.withInitial(ChanelDefinitionWithinEffectiveDatesStepsDefs::new);
    final ThreadLocal<DPBIOutboundCorrespondenceConfigEventsStepDefs> dpbiOutboundCorrespondenceConfigEventsStepDefs = ThreadLocal.withInitial(DPBIOutboundCorrespondenceConfigEventsStepDefs::new);

    final ThreadLocal<List<String>> startEndDates = ThreadLocal.withInitial(ArrayList::new);

    @And("Click at Add Channel button")
    public void AddChanel() {
        dpbiOutboundCorrespondenceConfigEventsStepDefs.get().clickAddChannelButton();
    }

    @When("I store Correspondence definition Start and End Dates")
    public void i_store_Correspondence_definition_Start_and_End_Dates() {
        startEndDates.set(chanelDefinitionWithinEffectiveDatesStepsDefs.get().storeCorrDefStartEndDAtes());
    }

    @When("I try entering in start date before Start Date of parent Definition")
    public void i_try_entering_in_start_date_before_Start_Date_of_parent_Definition() throws ParseException {
        chanelDefinitionWithinEffectiveDatesStepsDefs.get().enterStartDateBeforeParentStartDate(startEndDates.get().get(0));
    }

    @Then("the system does not save, and issues an error {string}")
    public void the_system_does_not_save_and_issues_an_error(String message) {
        chanelDefinitionWithinEffectiveDatesStepsDefs.get().verifyWarningMessage(message);
    }

    @Then("I try entering in end date after End Date of parent Definition")
    public void i_try_entering_in_end_date_after_End_Date_of_parent_Definition() throws ParseException {
        chanelDefinitionWithinEffectiveDatesStepsDefs.get().enterEndDateAfterParentEndDate(startEndDates.get().get(1));
    }

    @Then("I try entering in end date before Start Date of parent Definition")
    public void i_try_entering_in_end_date_before_Start_Date_of_parent_Definition() throws ParseException {
        chanelDefinitionWithinEffectiveDatesStepsDefs.get().enterEndDateBeforeParentStartDate(startEndDates.get().get(0));
    }

    @When("I Successfully update Correspondence Definition End Date")
    public void i_Successfully_update_Correspondence_Definition_End_Date() {
        chanelDefinitionWithinEffectiveDatesStepsDefs.get().updateCorrespondenceDefinitionEndDate();
    }

    @Then("system updates any child Channel Definitions whose End Date is blank or later")
    public void system_updates_any_child_Channel_Definitions_whose_End_Date_is_blank_or_later() {
        chanelDefinitionWithinEffectiveDatesStepsDefs.get().verifyAnyChildChannelDefUpdated();
    }

}
