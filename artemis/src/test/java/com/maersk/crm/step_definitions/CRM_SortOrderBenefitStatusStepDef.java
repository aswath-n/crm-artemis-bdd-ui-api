package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMDemographicContactInfoPage;
import com.maersk.crm.pages.crm.CRMProgramAndBenefitInfoPage;
import com.maersk.crm.utilities.BrowserUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.List;
import static org.testng.Assert.assertTrue;

public class CRM_SortOrderBenefitStatusStepDef extends BrowserUtils {
    private CRMProgramAndBenefitInfoPage programBenefitInfoPage = new CRMProgramAndBenefitInfoPage();
    private CRMDemographicContactInfoPage demographicContactInfoPage=new CRMDemographicContactInfoPage();
    final ThreadLocal<ArrayList<String>> actualOrder = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> consumerNames = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> listOfPIAndCaseMembers= ThreadLocal.withInitial(ArrayList::new);

    @When("I verify the sort of the consumers {string} by benefit status is:")
    public void i_verify_the_sort_of_the_consumers_by_benefit_status_is(String benefitStatus, List<String> order) {
        benefitStatus = benefitStatus.toUpperCase();
        actualOrder.set(new ArrayList<>());
        switch (benefitStatus) {
            case "WITH ONLY CURRENT BENEFIT STATUS":
                for (int i = 0; i < programBenefitInfoPage.listOfCurrentBenefitStatusForConsumer.size(); i++)
                    actualOrder.get().add(programBenefitInfoPage.listOfCurrentBenefitStatusForConsumer.get(i).getText().replace("star", ""));
                break;
            case "WITH ONLY FUTURE BENEFIT STATUS":
                for (int i = 0; i < programBenefitInfoPage.listOfFutureBenefitStatusForConsumer.size(); i++)
                    actualOrder.get().add(programBenefitInfoPage.listOfFutureBenefitStatusForConsumer.get(i).getText().replace("star", ""));
                break;
            case "WITH BOTH FUTURE AND CURRENT STATUS":
                for (int i = 0; i < programBenefitInfoPage.listOfBenefitStatusForConsumer.size(); i++)
                    actualOrder.get().add(programBenefitInfoPage.listOfBenefitStatusForConsumer.get(i).getText().replace("star", ""));
                break;
            default:
                Assert.fail("The " + benefitStatus + " is not declared in switch statement");
        }
        order.stream().map(status -> status.toUpperCase());
        System.out.println("expected order : " + order.toString());
        System.out.println("actual   order : " + actualOrder.get().toString());
        Assert.assertTrue(order.equals(actualOrder.get()), " Benefit Status Order does not match!");

        }
    @When("I view the sort of the consumers by {string}")
    public void i_view_the_sort_of_the_consumers_by(String field) {
       field=field.toUpperCase();
       switch (field){
           case "NAME":
          consumerNames.set(getElementsText(programBenefitInfoPage.listOfConsmersNames));
               System.out.println(consumerNames.get());
               break;
           case "PRIMARY INDIVIDUALS AND CASE MEMBERS":
               listOfPIAndCaseMembers.set(getElementsText(demographicContactInfoPage.listOfPrimaryIndvsAndCaseMembersNames));
               break;
           case"AGE":
               for (int i = 0; i < programBenefitInfoPage.listOfYearsAndGenderOfBeneficiary.size(); i++) {
                   String text = programBenefitInfoPage.listOfYearsAndGenderOfBeneficiary.get(i).getText();
                   listOfPIAndCaseMembers.get().add(text.substring(0,text.indexOf("Y")));
               }
               break;
           default:
               Assert.fail("The "+field+" is not defined in switch statement");
       }
    }

    @Then("I verify {string} displays first")
    public void i_verify_displays_first(String field) {
      field=field.toUpperCase();
      switch (field){
          case "PRIMARY INDIVIDUAL":
              assertTrue(consumerNames.get().get(0).equalsIgnoreCase(listOfPIAndCaseMembers.get().get(0)));
              break;
          case"OLDEST":
              listOfPIAndCaseMembers.get().clear();
              for (int i = 0; i < programBenefitInfoPage.listOfYearsAndGenderOfBeneficiary.size(); i++) {
                  String text = programBenefitInfoPage.listOfYearsAndGenderOfBeneficiary.get(i).getText();
                  listOfPIAndCaseMembers.get().add(text.substring(0,text.indexOf("Y")));
              }
              for (int i = 0; i < listOfPIAndCaseMembers.get().size()-1; i++) {
                  assertTrue(Integer.parseInt(listOfPIAndCaseMembers.get().get(i))>Integer.parseInt(listOfPIAndCaseMembers.get().get(i+1)));
              }

              break;
          default:
              Assert.fail("The "+field+" is not defined in switch statement");
      }
    }

}


