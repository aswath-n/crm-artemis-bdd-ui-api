package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMEnrollmentUpdatePage;
import com.maersk.crm.pages.crm.CRMProgramAndBenefitInfoPage;
import com.maersk.crm.utilities.BrowserUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CRM_AddCaseMemberDuringEnrollStepDef extends BrowserUtils {
  private CRMProgramAndBenefitInfoPage programAndBenefitInfoPage= new CRMProgramAndBenefitInfoPage();
  private CRMEnrollmentUpdatePage enrollmentUpdatePage=new CRMEnrollmentUpdatePage();
    List<String> population=getElementsText(programAndBenefitInfoPage.listOfConsumerPopulationCurrentEligibility2);
    List<String> region=getElementsText(programAndBenefitInfoPage.listOfServiceRegionForCurrentEligibility2);
    List<String> consumers=getElementsText(programAndBenefitInfoPage.listOfConsumerFullNames);
    List<String> actionsDays=new ArrayList<>();
    List<String> populationFuture=getElementsText(programAndBenefitInfoPage.listOfConsumerPopulationFutureEligibility);
    List<String> regionFuture=getElementsText(programAndBenefitInfoPage.listOfServiceRegionForFutureEligibility);
    List<String> anniversaryDates=getElementsText(programAndBenefitInfoPage.listOfAnniversaryDates);
    List<String>filter=new ArrayList<>();


    @When("I view {string} section for consumer number {int}")
    public void i_view_section_for_consumer_number(String section, Integer idx) {
        section=section.toUpperCase();
        for (int i = 0; i <getElementsText(programAndBenefitInfoPage.listOfActionDays).size() ; i++) {
            actionsDays.add(getElementsText(programAndBenefitInfoPage.listOfActionDays).get(i).replace(" DAYS",""));
            if(actionsDays.get(i).equalsIgnoreCase("-- --")){
                actionsDays.remove(i);
                consumers.remove(i);
                population.remove(i);
                region.remove(i);
            }
        }
        switch (section){
            case"CURRENT ELIGIBILITY DATA":
                waitFor(3);
                scrollUpUsingActions(4);
                for (int i = 0; i <consumers.size() ; i++) {
                    if (region.get(i).equalsIgnoreCase(region.get(idx - 1))&& population.get(i).equalsIgnoreCase(population.get(idx-1))&&Integer.parseInt(actionsDays.get(i))>0) {
                        filter.add(consumers.get(i));
                    }
                 }
                break;
            case"CURRENT ENROLLMENT DATA":
                waitFor(3);
                scrollUpUsingActions(4);
                for (int i = 0; i <consumers.size() ; i++) {
                    if (region.get(i).equalsIgnoreCase(region.get(idx - 1))&& population.get(i).equalsIgnoreCase(population.get(idx-1))&&Integer.parseInt(actionsDays.get(i))>0) {
                        filter.add(consumers.get(i));
                    }
                }
                break;
            case"FUTURE ELIGIBILITY DATA":
                for (int i = 0; i <consumers.size() ; i++) {
                    if (regionFuture.get(i).equalsIgnoreCase(regionFuture.get(idx - 1))&&populationFuture.get(i).equalsIgnoreCase(populationFuture.get(idx-1))&&Integer.parseInt(actionsDays.get(i))>0) {
                        filter.add(consumers.get(i));
                    }
                }
                break;
            default:
                Assert.fail("The "+section+" is not declared in the switch statement");
        }
    }

    @Then("I verify I see the consumers with the same {string} in the list")
    public void i_verify_I_see_the_consumers_with_the_same_in_the_list(String type) {
      type=type.toUpperCase();
      switch (type){
          case"POPULATION, SERVICE AREA AND PROGRAM":
          case"POPULATION, SERVICE AREA, PROGRAM AND ANNIVERSARY WINDOW":
              assertTrue(getElementsText(enrollmentUpdatePage.listConsumersNamesEnrollmentPage).containsAll(filter),
                      "The consumers list with same population doesn't match");
              break;
          case"ELIGIBILITY TIMEFRAME":
              for (int i = 0; i < programAndBenefitInfoPage.currentEligibilityPlanStartDate.size(); i++) {
                  assertEquals(programAndBenefitInfoPage.currentEligibilityPlanStartDate.get(i).getText(), programAndBenefitInfoPage.currentEligibilityPlanStartDate.get(0).getText());
              }
              break;
          case"ENROLLMENT TIMEFRAME":
              for (int i = 0; i < programAndBenefitInfoPage.futureEnrollmentPlanStartDate.size(); i++) {
                  assertEquals(programAndBenefitInfoPage.futureEnrollmentPlanStartDate.get(i).getText(), programAndBenefitInfoPage.futureEnrollmentPlanStartDate.get(0).getText());
              }
              break;
          case"ENROLL ACTION AVAILABLE":
          case"PLAN CHANGE ACTION AVAILABLE":
              waitFor(3);
              assertTrue(filter.containsAll(getElementsText(enrollmentUpdatePage.listConsumersNamesEnrollmentPage)),
                      "The consumers list with same action available doesn't match");
              break;
          default:
              Assert.fail("The "+type+" is not declared in switch statement");
      }
    }

    // Find all the steps that use the implementation below and change those steps to use the step
    // @And("I click {string} Button in {string} segment for a consumer first name {string} and last name {string}")
    @When("I click {string} Button on {string} section for a consumer number {int}")
    public void i_click_Button_on_section_for_a_consumer_number(String button, String section, Integer idx) {
       button=button.toUpperCase();
       section=section.toUpperCase();
       if(button.equalsIgnoreCase("ENROLL")){
           switch (section){
               case"CURRENT ELIGIBILITY":
                   if(programAndBenefitInfoPage.listOfEnrollBttnForCurrentEligibility.size()>1) {
                       click(programAndBenefitInfoPage.listOfEnrollBttnForCurrentEligibility.get(idx - 1));
                   }else{
                       idx=1;
                       click(programAndBenefitInfoPage.listOfEnrollBttnForCurrentEligibility.get(idx - 1));
                   }
                   break;
               case"FUTURE ELIGIBILITY":
                   if(programAndBenefitInfoPage.listOfEnrollBttnForFutureEligibility.size()>1) {
                       click(programAndBenefitInfoPage.listOfEnrollBttnForFutureEligibility.get(idx - 1));
                   }else{
                       idx=1;
                       click(programAndBenefitInfoPage.listOfEnrollBttnForFutureEligibility.get(idx - 1));
                   }
                   break;
               default:
                   Assert.fail("The "+section+" is don defined in switch statement" );
           }
       }
    }

}
