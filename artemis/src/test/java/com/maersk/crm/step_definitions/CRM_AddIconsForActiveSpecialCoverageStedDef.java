package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMProgramAndBenefitInfoPage;
import com.maersk.crm.utilities.BrowserUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.List;
import static org.testng.Assert.assertTrue;

public class CRM_AddIconsForActiveSpecialCoverageStedDef extends BrowserUtils {
    List<WebElement>elements=new ArrayList<>();
    List<List<String>>list=new ArrayList<>();
    List<List<String>>facility=new ArrayList<>();
    List<List<String>>longTermCare=new ArrayList<>();
    List<List<String>>hospice=new ArrayList<>();
    List<List<String>>waiver=new ArrayList<>();
    List<List<String>>medicare=new ArrayList<>();
    List<List<String>>otherInsurance=new ArrayList<>();
    CRMProgramAndBenefitInfoPage programAndBenefitInfoPage=new CRMProgramAndBenefitInfoPage();

    @When("I verify if the consumer {string} Special Coverage segment to be displayed")
    public void i_verify_if_the_consumer_any_Special_Coverage_segment_to_be_displayed(String action) {
    action=action.toUpperCase();
        for (int i = 0; i <programAndBenefitInfoPage.listOfSpecialCoverageExpandBttn.size() ; i++) {
            programAndBenefitInfoPage.listOfSpecialCoverageExpandBttn.get(i).click(); }
        for (int i = 0; i <programAndBenefitInfoPage.listOfSpecialCoverageElements.size() ; i++) {
            if(!programAndBenefitInfoPage.listOfSpecialCoverageElements.get(i).getText().equalsIgnoreCase("-- --")){
                elements.add(programAndBenefitInfoPage.listOfSpecialCoverageElements.get(i)); } }
    switch (action){
        case"HAS":
             assertTrue(elements.size()>0,"The Special Coverage field doesn't have special coverage");
            break;
        case "DOESN'T HAVE":
             assertTrue(elements.size()==0,"The consumer has special coverage displayed");
            break;
        default:
            Assert.fail("The "+action+" is not declared in the switch statement"); }
    }

    @Then("I verify green outlined white box around each Special Coverage type is {string}")
    public void i_verify_green_outlined_white_box_around_each_Special_Coverage_type_is(String action) {
        action=action.toUpperCase();
        switch (action){
            case "DISPLAYED":
                if(programAndBenefitInfoPage.listOfSpecialCoverageFieldsSegment.size()>0){
                    for (int i = 0; i <programAndBenefitInfoPage.listOfSpecialCoverageFieldsSegment.size() ; i++) {
                        assertTrue(getBorderColorCode(programAndBenefitInfoPage.listOfSpecialCoverageFieldsSegment.get(i)).equalsIgnoreCase("#9cc487"),
                                "The color border is not displayed green"); }
                }
                break;
            case"NOT DISPLAYED":
               assertTrue(programAndBenefitInfoPage.listOfSpecialCoverageFieldsSegment.size()==0);
                break;
            default:
                Assert.fail("The "+action+" is not declared in the switch statement");
        }
    }
    @Then("I collapse and re-expand the {string} section")
    public void i_collapse_and_re_expand_the_section(String section) {
        section=section.toUpperCase();
        switch (section){
            case"SPECIAL COVERAGE":
                for (int i = 0; i <programAndBenefitInfoPage.listOfSpecialCoverageExpandBttn.size() ; i++) {
                    click( programAndBenefitInfoPage.listOfSpecialCoverageExpandBttn.get(i));
                    waitFor(1);
                    click( programAndBenefitInfoPage.listOfSpecialCoverageExpandBttn.get(i));
                }
                break;
            case"CONSUMER":
                for (int i = 0; i <programAndBenefitInfoPage.listOfConsumerExpandButtons.size() ; i++) {
                    click( programAndBenefitInfoPage.listOfConsumerExpandButtons.get(i));
                    waitFor(1);
                    click( programAndBenefitInfoPage.listOfConsumerExpandButtons.get(i));
                }
                break;
            default:
                Assert.fail("The "+section+" is not declared in the switch statement");
        }
    }

    @When("I verify just the special coverage segment is shown which has data")
    public void i_verify_just_the_special_coverage_segment_is_shown_which_has_data() {
        for (int i = 0; i <list.size() ; i++) {
            for (int j = 0; j <list.get(i).size() ; j++) {
                if(list.get(i).get(j).contains("Facility/Placement")){
                    assertTrue(facility.get(i).size()>0,"The facility list is empty");
                }
                if(list.get(i).get(j).contains("Long Term Care")){
                    assertTrue(facility.get(i).size()>0,"The long term list is empty");
                }
                if(list.get(i).get(j).contains("Hospice")){
                    assertTrue(facility.get(i).size()>0,"The hospice list is empty");
                }
                if(list.get(i).get(j).contains("Waiver")){
                    assertTrue(facility.get(i).size()>0,"The waiver list is empty");
                }
                if(list.get(i).get(j).contains("Medicare")){
                    assertTrue(facility.get(i).size()>0,"The Medicare list is empty");
                }
                if(list.get(i).get(j).contains("Other Insurance - 3rd Party")){
                    assertTrue(facility.get(i).size()>0,"The Other Insurance - 3rd Party is empty");
                }
            }
        }
    }
    @When("I view a special coverage section")
    public void i_view_a_special_coverage_section() {
        for (int i = 0; i < programAndBenefitInfoPage.listOfConsumerExpandButtons.size(); i++) {
            click(programAndBenefitInfoPage.listOfConsumerExpandButtons.get(i));
        }
        for (int i = 0; i < programAndBenefitInfoPage.listOfConsumerExpandButtons.size(); i++) {
            click(programAndBenefitInfoPage.listOfConsumerExpandButtons.get(i));
            list.add(getElementsText(programAndBenefitInfoPage.listOfSpecialCoverageElements_txt));
            click(programAndBenefitInfoPage.listOfSpecialCoverageExpandBttn.get(0));
            facility.add(getElementsText(programAndBenefitInfoPage.listOfSpecialCoverageFieldsFacilityPlacement));
            longTermCare.add(getElementsText(programAndBenefitInfoPage.listOfSpecialCoverageFieldsLongTermCare));
            medicare.add(getElementsText(programAndBenefitInfoPage.listOfSpecialCoverageFieldsMedicare));
            otherInsurance.add(getElementsText(programAndBenefitInfoPage.listOfSpecialCoverageFieldsOtherInsurance));
            hospice.add(getElementsText(programAndBenefitInfoPage.listOfSpecialCoverageFieldsHospice));
            waiver.add(getElementsText(programAndBenefitInfoPage.listOfSpecialCoverageFieldsWaiver));
            click(programAndBenefitInfoPage.listOfSpecialCoverageExpandBttn.get(0));
            click(programAndBenefitInfoPage.listOfConsumerExpandButtons.get(i));
        }
        removeEmptyFields(facility);
        removeEmptyFields(longTermCare);
        removeEmptyFields(facility);
        removeEmptyFields(longTermCare);
        removeEmptyFields(hospice);
        removeEmptyFields(waiver);
        removeEmptyFields(medicare);
        removeEmptyFields(otherInsurance);
        }
    }
