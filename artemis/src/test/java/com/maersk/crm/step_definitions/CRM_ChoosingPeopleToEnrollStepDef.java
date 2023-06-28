package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APIConsumerPopulationDmnController;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import com.maersk.crm.pages.crm.CRMEnrollmentUpdatePage;
import com.maersk.crm.pages.crm.CRMProgramAndBenefitInfoPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class CRM_ChoosingPeopleToEnrollStepDef extends CRMUtilities implements ApiBase {

    CRMProgramAndBenefitInfoPage programAndBenefitInfoPage=new CRMProgramAndBenefitInfoPage();
    CRMEnrollmentUpdatePage enrollmentUpdatePage=new CRMEnrollmentUpdatePage();
    private  List<String> consumerPlans;
    // Date Change: 03/03/2022 Below comments should be deleted by the end of Mar-2022 if no issues after running a regression
    ThreadLocal<List<String>> caseMemberNames = ThreadLocal.withInitial(ArrayList::new);//=getElementsText(enrollmentUpdatePage.listAddCaseMembers);
    ThreadLocal<List<String>> consumerNames = ThreadLocal.withInitial(ArrayList::new);//=getElementsText(programAndBenefitInfoPage.listOfConsmersNames);
    ThreadLocal<List<String>> consumerNamesEnrollmentPage = ThreadLocal.withInitial(ArrayList::new);//=getElementsText(enrollmentUpdatePage.listConsumersNamesEnrollmentPage);

    @When("I click Add Case Members Button")
    public void i_Click_Add_Case_Members_Button() {
        waitFor(2);
        BrowserUtils.scrollToElement(enrollmentUpdatePage.enrollmentUpdateAddCaseMembersButtonPlus, false);
        enrollmentUpdatePage.enrollmentUpdateAddCaseMembersButtonPlus.click();

    }

    @Then("I verify I see all other consumers in the dropdown list")
    public void i_verify_I_see_all_other_consumers_in_the_dropdown_list() {
        caseMemberNames.set(getElementsText(enrollmentUpdatePage.listAddCaseMembers));
        List<String> selectedConsumers = getElementsText(enrollmentUpdatePage.listConsumersNamesEnrollmentPage);
        for (int i = 0; i < consumerNames.get().size(); i++) {
            Assert.assertTrue(caseMemberNames.get().contains(consumerNames.get().get(i)) || selectedConsumers.contains(consumerNames.get().get(i)));
        }
    }

    @And("I click Add Case Members with First Name as {string} and Last Name as {string}")
    public void i_click_add_case_members_with_first_name_as_and_last_name_as(String firstName, String lastName) {
        String fullName = (firstName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerFirstName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(firstName).toString()) + " "
                + (lastName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerLastName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(lastName).toString());
        System.out.println("Adding consumer " + fullName + " from same case id to plan selection");
        waitForVisibility(enrollmentUpdatePage.selectAllOptionFromCaseMembersDropDown, 5);
        enrollmentUpdatePage.getConsumerSelectionByFullName(fullName).click();
    }

    @And("I click Remove Button for Case Member First Name as {string} and Last Name as {string}")
    public void I_click_Remove_Button_for_Case_Member_First_Name_as_and_Last_Name_as(String firstName, String lastName){
        String fullName = (firstName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerFirstName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(firstName).toString()) + " "
                + (lastName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerLastName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(lastName).toString());
        System.out.println("Removing consumer " + fullName + " from same case id from plan selection");
        waitForVisibility(enrollmentUpdatePage.consumerDetails, 2);
        click(enrollmentUpdatePage.getConsumersRemoveButtonByGivenFullName(fullName));
    }

    @And("I verivy consumer by First Name as {string} and Last Name as {string} is not in plan selection")
    public void I_verivy_consumer_by_First_Name_as_and_Last_Name_as_is_not_in_plan_selection(String firstName, String lastName){
        String fullName = (firstName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerFirstName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(firstName).toString()) + " "
                + (lastName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerLastName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(lastName).toString());
        System.out.println("Validating if consumer " + fullName + " is NOT in plan selection");
        waitForVisibility(enrollmentUpdatePage.consumerDetails, 2);
        try {
            assertFalse(enrollmentUpdatePage.getConsumerDetailsRowByFullName(fullName).isDisplayed(), "The consumer" + fullName + " is displayed");
        } catch (Exception e) {
            System.out.println("Consumer " + fullName + " is not displayed");
        }
    }

    @Then("I verify I only see next other consumers in the dropdown list")
    public void i_verify_i_only_see_next_other_consumers_in_the_dropdown_list(List<String> names) {
        waitForVisibility(enrollmentUpdatePage.selectAllOptionFromCaseMembersDropDown, 5);
        ArrayList<String> expectedFullNames = new ArrayList<>();
        for (String name : names) {
            String fullName = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(name + ".firstName").toString() + " "
                    + API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(name + ".lastName").toString();
            expectedFullNames.add(fullName);
        }
        List<String> actualFullNames = getElementsText(enrollmentUpdatePage.listAddCaseMembers);
        System.out.println("expectedFullNames = " + expectedFullNames);
        System.out.println("actualFullNames = " + actualFullNames);
        for (String actualFullName : actualFullNames) {
            if (actualFullName.equals("Select all")) continue;
            assertTrue(expectedFullNames.contains(actualFullName), actualFullName + " is unexpected consumer in add case members dropdown!");
        }
    }



    @When("Select case member")
    public void select_case_member() {
        enrollmentUpdatePage.listAddCaseMembers.get(1).click();
        waitFor(5);
    }

    @Then("I verify all clients listed in the {string} section are not present in the dropdown")
    public void i_verify_all_clients_listed_in_the_section_are_not_present_in_the_dropdown(String string) {
        caseMemberNames.set(getElementsText(enrollmentUpdatePage.listAddCaseMembers));
        consumerNamesEnrollmentPage.set(getElementsText(enrollmentUpdatePage.listConsumersNamesEnrollmentPage));
        for (int i = 1; i < consumerNamesEnrollmentPage.get().size(); i++) {
            Assert.assertFalse(caseMemberNames.get().equals(consumerNamesEnrollmentPage.get().get(i)));
        }
    }
    @When("no additional clients meet criteria for inclusion with the enrollment request")
    public void no_additional_clients_meet_criteria_for_inclusion_with_the_enrollment_request() {
        waitFor(1);
        assertTrue(getElementsText(enrollmentUpdatePage.listConsumersNamesEnrollmentPage).containsAll(consumerNames.get()));
    }

    @Then("I verify the UI must temporarily hide the “Add Case Member“ button.")
    public void i_verify_the_UI_must_temporarily_hide_the_Add_Case_Member_button() {
        assertFalse(isElementDisplayed(enrollmentUpdatePage.enrollmentUpdateAddCaseMembersButton));
    }

    @When("I get consumer names in Program and Benefit Info Page")
    public void I_get_consumer_names_in_PB_Page() {
        consumerNames.set(getElementsText(programAndBenefitInfoPage.listOfConsmersNames));
    }

}
