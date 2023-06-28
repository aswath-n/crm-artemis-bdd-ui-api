package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APIConsumerPopulationDmnController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.ApiTestDataUtil;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import java.util.stream.Collectors;


//import static com.maersk.crm.utilities.ApiBaseClass.api_commonThreadLocal;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.*;

public class CRMEnrollmentUpdateStepDef extends CRMUtilities implements ApiBase {

    final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    final DateTimeFormatter dfUIver = DateTimeFormatter.ofPattern("MM/dd/yyyy");


    CRMProgramAndBenefitInfoPage programAndBenefitInfoPage = new CRMProgramAndBenefitInfoPage();
    CRMEnrollmentUpdatePage enrollmentUpdatePage = new CRMEnrollmentUpdatePage();
    private CRMContactRecordUIPage contactRecordUIPage = new CRMContactRecordUIPage();
    final ThreadLocal<ApiTestDataUtil> apiTestDataUtil = ThreadLocal.withInitial(ApiTestDataUtil::getApiTestDataUtilThreadLocal);
    private final ThreadLocal<List<String>> consumerPlans = ThreadLocal.withInitial(ArrayList::new);
    // Date Change: 03/03/2022 Below comment should be deleted by the end of Mar-2022 if no issues after running a regression
    final ThreadLocal<List<String>> consumerNames = ThreadLocal.withInitial(ArrayList::new);// = getElementsText(programAndBenefitInfoPage.listOfConsmersNames);
    private final ThreadLocal<List<String>> members = ThreadLocal.withInitial(ArrayList::new);

    public static final ThreadLocal<String> selectedPlanName = ThreadLocal.withInitial(String::new);

    public static final ThreadLocal<Map<String, String>> assignedPlanNames = ThreadLocal.withInitial(HashMap::new);
    public static final ThreadLocal<String> selectedPlanStartDate = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> selectedPlanEndDate = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> frstMember = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> warningMessage = ThreadLocal.withInitial(String::new);
    CRMSearchProviderPart2UIPage crmSearchProviderPart2UIPage = new CRMSearchProviderPart2UIPage();
    final ThreadLocal<CRMSearchtProviderPart2UIStepDef> CRMSearchtProviderPart2UIStepDef = ThreadLocal.withInitial(CRMSearchtProviderPart2UIStepDef::new);

    @When("I Add Case Members Button and Select all case members")
    public void i_Add_Case_Members_Button_and_Select_all_case_members() {
        scrollUpUsingActions(2);
        waitForVisibility(enrollmentUpdatePage.enrollmentUpdateAddCaseMembersButton, 5);
        enrollmentUpdatePage.enrollmentUpdateAddCaseMembersButton.click();
        waitForVisibility(enrollmentUpdatePage.selectAllOptionFromCaseMembersDropDown, 5);
        enrollmentUpdatePage.selectAllOptionFromCaseMembersDropDown.click();
        waitFor(2);

    }

    @When("I select A plan from Available Plans")
    public void i_select_A_plan_from_Available_Plans() {
        waitForVisibility(enrollmentUpdatePage.selectPlanBttn.get(0), 5);
        Random rand = new Random();
        List<WebElement> availablePlans = enrollmentUpdatePage.selectPlanBttn.stream().filter(WebElement::isEnabled).collect(Collectors.toList());
        availablePlans.get(rand.nextInt(availablePlans.size())).click();
        waitFor(5);
        selectedPlanName.set(enrollmentUpdatePage.selectedPlanName.get(0).getText().trim());
        try {
            selectedPlanStartDate.set(enrollmentUpdatePage.selectedPlanStartDate.getAttribute("value"));
        } catch (Exception e) {
            selectedPlanStartDate.set(enrollmentUpdatePage.selectedPlanStartDateH6.getText());
        }
        selectedPlanEndDate.set(enrollmentUpdatePage.selectedPlanEndDate.getText().trim());

    }

    @Then("I verify  the Add Case Members button is disable")
    public void i_verify_the_Add_Case_Members_button_is_disable() {
        assertTrue(enrollmentUpdatePage.enrollmentUpdateAddCaseMembersButton.getAttribute("class").endsWith("disabled"), "The Add Case Members Button is not disabled");
    }

    @Then("I verify Delete button for Case members selected is disabled")
    public void i_verify_Delete_button_for_Case_members_selected_is_disabled() {
        for (int i = 0; i < enrollmentUpdatePage.deleteBttnForCaseMembers.size(); i++) {
            assertTrue(enrollmentUpdatePage.deleteBttnForCaseMembers.get(i).getAttribute("class").endsWith("disabled"));
        }
    }

    @When("I click on Remove Plan Option")
    public void i_click_on_Remove_Plan_Option() {
        waitFor(3);
        enrollmentUpdatePage.removePlanOtionBttn.click();
        waitFor(3);
    }

    @Then("I verify Add Case Members button is disabled & Delete button is enabled for consumer")
    public void iVerifyAddCaseMembersButtonIsDisabledDeleteButtonIsEnabledForConsumer() {
        assertTrue(enrollmentUpdatePage.enrollmentUpdateAddCaseMembersButton.getAttribute("class").endsWith("disabled"), "The Add Case Members Button is not enabled");
        for (int i = 0; i < enrollmentUpdatePage.deleteBttnForCaseMembers.size(); i++) {
            assertFalse(enrollmentUpdatePage.deleteBttnForCaseMembers.get(i).getAttribute("class").endsWith("disabled"), "The delete button is not enabled");
        }

    }

    @Then("I verify Add Case Members button & Delete button for consumer are enabled")
    public void iVerifyAddCaseMembersButtonDeleteButtonForConsumerAreEnabled() {
        assertFalse(enrollmentUpdatePage.enrollmentUpdateAddCaseMembersButton.getAttribute("class").endsWith("disabled"), "The Add Case Members Button is not enabled");
        for (int i = 0; i < enrollmentUpdatePage.deleteBttnForCaseMembers.size(); i++) {
            assertFalse(enrollmentUpdatePage.deleteBttnForCaseMembers.get(i).getAttribute("class").endsWith("disabled"), "The delete button is not enabled");
        }

    }

    // Find all the steps that use the implementation below and change those steps to use the step
    // @And("I click {string} Button in {string} segment for a consumer first name {string} and last name {string}")
    @And("I click {string} Button for a consumer first name {string} and last name {string}")
    public void I_click_Button_for_a_consumer_full_name(String button, String firstName, String lastName) {
        String fullName = (firstName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerFirstName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(firstName).toString()) + " "
                + (lastName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerLastName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(lastName).toString());
        WebElement givenConsumerBox = programAndBenefitInfoPage.getGivenConsumerBoxByFullName(fullName);
        scrollToElement(givenConsumerBox);
        System.out.println("Clicking " + button + " for " + fullName);
        switch (button.toUpperCase()) {
            case "ENROLL":
                waitForClickablility(
                        programAndBenefitInfoPage.getButtonInsideGivenParentGivenEnrollmentGivenButtonText(
                                givenConsumerBox, "CURRENT ELIGIBILITY", "ENROLL"), 2).click();
                break;
            case "PLAN CHANGE":
                waitForClickablility(
                        programAndBenefitInfoPage.getButtonInsideGivenParentGivenEnrollmentGivenButtonText(
                                givenConsumerBox, "CURRENT ENROLLMENT", "PLAN CHANGE"), 2).click();
                break;
            case "DISREGARD":
                waitForClickablility(
                        programAndBenefitInfoPage.getButtonInsideGivenParentGivenEnrollmentGivenButtonText(
                                givenConsumerBox, "FUTURE ENROLLMENT", "DISREGARD"), 2).click();
                break;
            case "EDIT":
                waitForClickablility(
                        programAndBenefitInfoPage.getButtonInsideGivenParentGivenEnrollmentGivenButtonText(
                                givenConsumerBox, "FUTURE ENROLLMENT", "EDIT"), 2).click();
                break;
        }
    }

    // Find all the steps that use the implementation below and change those steps to use the step
    // @And("I click {string} Button in {string} segment for a consumer first name {string} and last name {string}")
    @And("I click future enrollment {string} Button for a consumer first name {string} and last name {string}")
    public void I_click_future_enrollment_Button_for_a_consumer_full_name(String button, String firstName, String lastName) {
        String fullName = (firstName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerFirstName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(firstName).toString()) + " "
                + (lastName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerLastName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(lastName).toString());
        WebElement givenConsumerBox = programAndBenefitInfoPage.getGivenConsumerBoxByFullName(fullName);
        scrollToElement(givenConsumerBox);
        switch (button.toUpperCase()) {
            case "PLAN CHANGE":
                waitForClickablility(
                        programAndBenefitInfoPage.getButtonInsideGivenParentGivenEnrollmentGivenButtonText(
                                givenConsumerBox, "FUTURE ENROLLMENT", "PLAN CHANGE"), 2).click();
                break;
            case "DISREGARD":
                waitForClickablility(
                        programAndBenefitInfoPage.getButtonInsideGivenParentGivenEnrollmentGivenButtonText(
                                givenConsumerBox, "FUTURE ENROLLMENT", "DISREGARD"), 2).click();
                break;
            case "EDIT":
                waitForClickablility(
                        programAndBenefitInfoPage.getButtonInsideGivenParentGivenEnrollmentGivenButtonText(
                                givenConsumerBox, "FUTURE ENROLLMENT", "EDIT"), 2).click();
                break;
        }
    }

    // Find all the steps that use the implementation below and change those steps to use the step
    // @And("I click {string} Button in {string} segment for a consumer first name {string} and last name {string}")
    @And("I click current enrollment {string} Button for a consumer first name {string} and last name {string}")
    public void I_click_current_enrollment_Button_for_a_consumer_full_name(String button, String firstName, String lastName) {
        String fullName = (firstName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerFirstName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(firstName).toString()) + " "
                + (lastName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerLastName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(lastName).toString());
        WebElement givenConsumerBox = programAndBenefitInfoPage.getGivenConsumerBoxByFullName(fullName);
        scrollToElement(givenConsumerBox);
        switch (button.toUpperCase()) {
            case "PLAN CHANGE":
                waitForClickablility(
                        programAndBenefitInfoPage.getButtonInsideGivenParentGivenEnrollmentGivenButtonText(
                                givenConsumerBox, "CURRENT ENROLLMENT", "PLAN CHANGE"), 2).click();
                break;
            case "DISREGARD":
                waitForClickablility(
                        programAndBenefitInfoPage.getButtonInsideGivenParentGivenEnrollmentGivenButtonText(
                                givenConsumerBox, "CURRENT ENROLLMENT", "DISREGARD"), 2).click();
                break;
            case "EDIT":
                waitForClickablility(
                        programAndBenefitInfoPage.getButtonInsideGivenParentGivenEnrollmentGivenButtonText(
                                givenConsumerBox, "CURRENT ENROLLMENT", "EDIT"), 2).click();
                break;
        }
    }

    @And("I click {string} Button in {string} segment for a consumer first name {string} and last name {string}")
    public void I_click_Button_in_segment_for_a_consumer_full_name(String button, String segment, String firstName, String lastName) {
        String fullName = (firstName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerFirstName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(firstName).toString()) + " "
                + (lastName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerLastName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(lastName).toString());
        WebElement givenConsumerBox = programAndBenefitInfoPage.getGivenConsumerBoxByFullName(fullName);
        scrollToElement(givenConsumerBox);
        System.out.println("Clicking " + button + " in " + segment + " segment for " + fullName);
        switch (button.toUpperCase()) {
            case "PLAN CHANGE":
                waitForClickablility(
                        programAndBenefitInfoPage.getButtonInsideGivenParentGivenEnrollmentGivenButtonText(
                                givenConsumerBox, segment, "PLAN CHANGE"), 2).click();
                break;
            case "DISREGARD":
                waitForClickablility(
                        programAndBenefitInfoPage.getButtonInsideGivenParentGivenEnrollmentGivenButtonText(
                                givenConsumerBox, segment, "DISREGARD"), 2).click();
                break;
            case "EDIT":
                waitForClickablility(
                        programAndBenefitInfoPage.getButtonInsideGivenParentGivenEnrollmentGivenButtonText(
                                givenConsumerBox, segment, "EDIT"), 2).click();
                break;
            case "ENROLL":
                waitForClickablility(
                        programAndBenefitInfoPage.getButtonInsideGivenParentGivenEnrollmentGivenButtonText(
                                givenConsumerBox, segment, "ENROLL"), 2).click();
                break;
            default:
                System.out.println(button + "button does not exist!");
        }
    }

    @And("I click {string} Button for a consumer")
    public void iClickButtonForAConsumer(String button) {
        waitFor(5);
        consumerNames.set(getElementsText(programAndBenefitInfoPage.listOfConsmersNames));
        switch (button.toLowerCase()) {
            case "enroll":
                scrollUpUsingActions(2);
                waitFor(3);
                for (int i = 0; i < consumerNames.get().size(); i++) {
                    consumerNames.get().get(i);
                }
                programAndBenefitInfoPage.currentEligibilityEnrollBttn.click();
                break;
            case "plan change":
                waitFor(2);
                waitForClickablility(programAndBenefitInfoPage.currentEnrollmentPlanChangeBttn, 2).click();
                break;
            case "crm case id":
                click(contactRecordUIPage.CRMCaseIdFirst);
                break;
//            case "submit":
//                click(enrollmentUpdatePage.enrollmentUpdateSubmitBttn);
//                break;
            case "submit":
                click(enrollmentUpdatePage.submitBttn);
                break;
            case "reason":
                waitFor(3);
                click(enrollmentUpdatePage.reasonDropDownBttn);
                waitFor(2);
                break;
            case "pcp select":
                waitFor(2);
                click(enrollmentUpdatePage.pcpSelectBttn);
                break;
            case "disenroll":
                waitFor(2);
                click(programAndBenefitInfoPage.disenrollCurrentEnrollmentButton);
                break;
            case "disenroll submit":
                waitFor(2);
                click(programAndBenefitInfoPage.disenrollSubmit);
                break;
            case "pcp pdp select":
                waitFor(2);
                click(enrollmentUpdatePage.pcpPdpSearch);
                break;
            default:
                Assert.fail("Button is not declared");
        }
    }

    @Then("I verify {string} Pannel/Section is displayed")
    public void iVerifyPannelIsDisplayed(String panel) {
        panel = panel.toUpperCase();
        switch (panel) {
            case "PLANS AVAILABLE":
                assertTrue(enrollmentUpdatePage.plansAvailablePanel.isDisplayed(), "The Plans Available Panel is not displayed");
                break;
            case "PROVIDER SEARCH":
                waitFor(2);
                assertEquals(enrollmentUpdatePage.searchProviderPanel.getText(), "Use the button to the right to select a provider");
                assertTrue(enrollmentUpdatePage.searchProviderBttn.isDisplayed());
                break;
            case "CURRENT ENROLLMENT":
                for (int i = 0; i < programAndBenefitInfoPage.listOfCurrentEnrollmentSection.size(); i++) {
                    assertTrue(programAndBenefitInfoPage.listOfCurrentEnrollmentSection.get(i).isDisplayed(), "The " + panel + " is not displayed");
                }
                break;
            case "FUTURE ENROLLMENT":
                for (int i = 0; i < programAndBenefitInfoPage.listOfFutureEnrollmentSection.size(); i++) {
                    assertTrue(programAndBenefitInfoPage.listOfFutureEnrollmentSection.get(i).isDisplayed(), "The " + panel + " is not displayed");
                }
                break;
            default:
                Assert.fail("The field " + panel + " is not defined in switch case");
        }

    }

    @Then("I verify {string} on the Plan Available Panel is displayed")
    public void iVerifyOnThePlanAvailablePanelIsDisplayed(String field) {
        field = field.toUpperCase();
        boolean result;
        switch (field) {
            case "PLAN NAME":
                waitFor(2);
                if (enrollmentUpdatePage.listOfPlanNamesOnPlansAvailableSection.size() > 0) {
                    result = enrollmentUpdatePage.listOfPlanNamesOnPlansAvailableSection.get(0).getText().equalsIgnoreCase("AMERIGROUP COMMUNITY CARE")
                            && enrollmentUpdatePage.listOfPlanNamesOnPlansAvailableSection.get(1).getText().equalsIgnoreCase("CARESOURCE GEORGIA")
                            && enrollmentUpdatePage.listOfPlanNamesOnPlansAvailableSection.get(2).getText().equalsIgnoreCase("PEACH STATE")
                            && enrollmentUpdatePage.listOfPlanNamesOnPlansAvailableSection.get(3).getText().equalsIgnoreCase("WELLCARE");
                    assertTrue(result);
                }
                break;
            case "PLAN TYPE":
                waitFor(5);
                if (enrollmentUpdatePage.listOfPlanTypeOnPlansAvailableSection.size() > 0) {
                    for (int i = 0; i < enrollmentUpdatePage.listOfPlanTypeOnPlansAvailableSection.size(); i++) {
                        assertTrue(enrollmentUpdatePage.listOfPlanTypeOnPlansAvailableSection.get(i).getText().equalsIgnoreCase("Medical"));
                    }
                }
                break;
            case "DATE":
                if (enrollmentUpdatePage.listOfStartDateOnPlansAvailableSection.size() > 0) {
                    for (int i = 0; i < enrollmentUpdatePage.listOfStartDateOnPlansAvailableSection.size(); i++) {
                        assertTrue(isMMddYYYYformat(enrollmentUpdatePage.listOfStartDateOnPlansAvailableSection.get(i).getText()));
                    }
                }
                break;
            case "PAGINATION":
                waitForVisibility(enrollmentUpdatePage.paginationElement, 5);
                assertTrue(enrollmentUpdatePage.paginationElement.isDisplayed());
                break;
            case "PLAN NAME FIELD":
                waitFor(2);
                if (enrollmentUpdatePage.listOfPlanNamesOnPlansAvailableSection.size() > 0) {
                    for (int i = 0; i < enrollmentUpdatePage.listOfPlanNamesOnPlansAvailableSection.size(); i++) {
                        assertTrue(enrollmentUpdatePage.listOfPlanNamesOnPlansAvailableSection.get(i).isDisplayed());
                    }
                }
                break;
            default:
                Assert.fail("The field is not defind");
        }
    }

    @Then("I verify {string} button is displayed for each plan")
    public void iVerifyButtonIsDisplayedForEachPlan(String button) {
        button = button.toUpperCase();
        switch (button) {
            case "SELECT":
                if (enrollmentUpdatePage.selectPlanBttn.size() > 0) {
                    for (int i = 0; i < enrollmentUpdatePage.selectPlanBttn.size(); i++) {
                        assertTrue(enrollmentUpdatePage.selectPlanBttn.get(i).isDisplayed(), "The Select Button is not displayed");
                    }
                }
                break;
            default:
                Assert.fail("The button is not defined");
        }

    }

    @Then("I verify {string} button is not clickable")
    public void I_verify_button_is_not_clickable(String button) {
        button = button.toUpperCase();
        switch (button) {
            case "SUBMIT":
                assertFalse(enrollmentUpdatePage.submitButton.isEnabled());
                break;
            default:
                Assert.fail("The button is not defined");
        }
    }

    @Then("I verify {string} button is displayed")
    public void iVerifyButtonIsDisplayed(String button) {
        button = button.toUpperCase();
        switch (button) {
            case "REMOVE PLAN":
                assertTrue(enrollmentUpdatePage.removePlanOtionBttn.isDisplayed());
                break;
            case "UPDATE ENROLLMENT":
                assertTrue(enrollmentUpdatePage.updateEnrollmentInfoBttn.isDisplayed(), "Update Enrollment Info Button is not displayed");
                break;
            case "PLAN CHANGE":
                assertTrue(programAndBenefitInfoPage.currentEnrollmentPlanChangeBttn.isDisplayed(), "The " + button + " is not displayed");
                break;
            case "PCP SELECT":
                assertTrue(enrollmentUpdatePage.pcpSelectBttn.isDisplayed(), "The " + button + " is not displayed");
                break;
            case "ENROLL":
                assertTrue(programAndBenefitInfoPage.currentEligibilityEnrollBttn.isDisplayed(), "The " + button + " is not displayed");
                break;
            case "ADD CASE MEMBERS":
                assertTrue(enrollmentUpdatePage.enrollmentUpdateAddCaseMembersButton.isDisplayed(), "The " + button + " is displayed");
                break;
            default:
                Assert.fail("The button " + button + " is not defined in switch statement");
        }
    }

    @Then("I verify {string} is not displayed")
    public void iVerifyIsNotDisplayed(String field) {
        field = field.toUpperCase();
        try {
            switch (field) {
                case "PLAN REASON DROPDOWN":
                    assertFalse(enrollmentUpdatePage.reasonPlanDropdown.isDisplayed(), "The " + field + " is displayed");
                    break;
                case "PLAN CHANGE BUTTON":
                    assertFalse(programAndBenefitInfoPage.currentEnrollmentPlanChangeBttn.isDisplayed(), "The " + field + " is displayed");
                    break;
                case "DISREGARD BUTTON":
                    assertFalse(programAndBenefitInfoPage.currentEnrollmentDisregardBttn.isDisplayed(), "The " + field + " is displayed");
                    break;
                case "EDIT BUTTON":
                    assertFalse(programAndBenefitInfoPage.currentEnrollmentEditBttn.isDisplayed(), "The " + field + " is displayed");
                    break;
                case "PCP SELECT BUTTON":
                    assertFalse(enrollmentUpdatePage.pcpSelectBttn.isDisplayed(), "The " + field + " is displayed");
                    break;
                case "ADD CASE MEMBERS BUTTON":
                    assertFalse(enrollmentUpdatePage.enrollmentUpdateAddCaseMembersButton.isDisplayed(), "The " + field + " is displayed");
                    break;
                case "ENROLL BUTTON":
                    assertFalse(programAndBenefitInfoPage.currentEligibilityEnrollBttn.isDisplayed(), "The " + field + " is displayed");
                    break;
                case "PRIOR ELIGIBILITY CARROT":
                    assertTrue(programAndBenefitInfoPage.priorEligibilityExpandCarrotList.size() == 0, "The " + field + " is displayed");
                    break;
                case "PRIOR ENROLLMENT CARROT":
                    assertTrue(programAndBenefitInfoPage.priorEnrollmentExpandCarrotList.size() == 0, "The " + field + " is displayed");
                    break;
                default:
                    Assert.fail("The field " + field + " is not defined in switch statement");
            }
        } catch (Exception e) {
            System.out.println(field + " is not displayed");
        }
    }

    @Then("I verify future enrollment {string} is not displayed")
    public void I_verify_future_enrollment_is_not_displayed(String field) {
        field = field.toUpperCase();
        try {
            switch (field) {
                case "PLAN CHANGE BUTTON":
                    assertFalse(programAndBenefitInfoPage.futureEnrollmentPlanChangeBttn.isDisplayed(), "The " + field + " is displayed");
                    break;
                case "DISREGARD BUTTON":
                    assertFalse(programAndBenefitInfoPage.futureEnrollmentDisregardBttn.isDisplayed(), "The " + field + " is displayed");
                    break;
                case "EDIT BUTTON":
                    assertFalse(programAndBenefitInfoPage.futureEnrollmentEditBttn.isDisplayed(), "The " + field + " is displayed");
                    break;
                default:
                    Assert.fail("The field " + field + " is not defined in switch statement");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Then("I verify current enrollment status is {string}")
    public void I_verify_current_enrollment_status_is(String status) {
        assertTrue(programAndBenefitInfoPage.currentEnrollmentStatus.getText().contains(status));
    }

    @Then("I verify current eligibilty status is {string}")
    public void I_verify_current_eligibility_status_is(String status) {
        assertTrue(programAndBenefitInfoPage.currentEligibilityStatus.getText().contains(status));
    }

    @And("I click submit button on enrollment update")
    public void clickSubmit() {
        scrollDownUsingActions(1);
        click(waitForClickablility(enrollmentUpdatePage.submitButton, 5));
    }

    @When("I delete all members with different plan on Consumer Details field")
    public void i_delete_all_members_with_different_plan_on_Consumer_Details_field() {
        consumerPlans.set(getElementsText(enrollmentUpdatePage.listOfCurrentPlansOnConsumerDetailsPanel));
        for (int i = consumerPlans.get().size() - 1; i >= 1; i--) {
            if (!consumerPlans.get().get(0).equalsIgnoreCase(consumerPlans.get().get(i))) {
                click(enrollmentUpdatePage.listOfDeleteBttnOnConsumerDetailsPanel.get(i - 1));
                waitFor(1);
            }
        }

    }

    @When("I click Select button for the plan consumer is enrolled")
    public void i_click_Select_button_for_the_plan_consumer_is_enrolled() {
        int idx = 0;
        for (int i = 0; i < enrollmentUpdatePage.listOfPlanNamesOnPlansAvailableSection.size(); i++) {
            if (enrollmentUpdatePage.listOfPlanNamesOnPlansAvailableSection.get(i).getText().equalsIgnoreCase(consumerPlans.get().get(0))) {
                idx = getElementsText(enrollmentUpdatePage.listOfPlanNamesOnPlansAvailableSection).indexOf(consumerPlans.get().get(0));
            }
        }
        click(enrollmentUpdatePage.selectPlanBttn.get(idx));
        waitFor(1);
    }

    @Then("I verify {string} is displayed on Plan/Consumer Details Panel")
    public void i_verify_is_displayed_on_Plan_Details_Panel(String expected) {
        assertEquals(expected, enrollmentUpdatePage.noRecordsFieldOnConsumerDetailsPanel.getText(), "No Record is not displayed");
    }

    @Then("I select a reason from drop down on Enrollment Update page")
    public void i_select_a_reason_from_drop_down_on_Enrollment_Update_page() {
        click(waitForClickablility(enrollmentUpdatePage.enrolmentUpdateReasonbttn, 2));
        // delete below commented out line after 07/15/2022 if following 3 lines work without an issue
//        click(waitForClickablility(enrollmentUpdatePage.dropDownReason, 2));
        int numberOfReasons = enrollmentUpdatePage.dropDownReasons.size();
        System.out.println("There are " + numberOfReasons + " number of Reasons");
        click(waitForClickablility(enrollmentUpdatePage.dropDownReasons.get(numberOfReasons - 1), 2));
        try {
            if (enrollmentUpdatePage.enrolmentOverrideReasonbttn.isDisplayed()) {
                click(waitForClickablility(enrollmentUpdatePage.enrolmentOverrideReasonbttn, 2));
                click(enrollmentUpdatePage.overrideDropDownFirstReason);
            }
        } catch (Exception e) {
        }
    }

    @Then("I verify dropdown {string} is not displayed")
    public void I_verify_dropdown_is_not_displayed(String dropdownName) {
        try {
            switch (dropdownName.toUpperCase()) {
                case "REASON":
                    Assert.assertFalse(enrollmentUpdatePage.dropDownReason.isDisplayed(),
                            "Dropdown " + dropdownName.toUpperCase() + " is displayed! - FAIL!");
                    System.out.println("Dropdown REASON is not displayed! - PASS!");
                    break;
                case "OVERRIDE REASON":
                    Assert.assertFalse(enrollmentUpdatePage.enrolmentOverrideReasonbttn.isDisplayed(),
                            "Dropdown " + dropdownName.toUpperCase() + " is displayed! - FAIL!");
                    System.out.println("Dropdown OVERRIDE REASON is not displayed! - PASS!");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Dropdown " + dropdownName.toUpperCase() + " is not displayed! - PASS!");
        }
    }

    @Then("I select a New Enrollment Override reason from drop down on Enrollment Edit page")
    public void I_select_select_a_New_Enrollment_Override_reason_from_drop_down_on_Enrollment_Edit_page() {
        try {
            if (enrollmentUpdatePage.enrolmentOverrideReasonbttn.isDisplayed()) {
                click(waitForClickablility(enrollmentUpdatePage.enrolmentOverrideReasonbttn, 2));
                click(enrollmentUpdatePage.overrideDropDownFirstReason);
            }
        } catch (Exception e) {
        }
    }

    @Then("I see {string} displayed")
    public void i_see_displayed(String field) {
        field = field.toUpperCase();
        switch (field) {
            case "WARNING MESSAGE":
                assertTrue(enrollmentUpdatePage.enrolmentUpdateWarningMessage.isDisplayed());
                break;
            default:
                Assert.fail("The " + field + " is not declared");
        }
    }

    @Then("I verify Edit & Disregard buttons are displayed on the new enrollment segment Selection Status = Selection Made")
    public void i_verify_Edit_Disregard_buttons_are_displayed_on_the_new_enrollment_segment_Selection_Status_Selection_Made() {
        BrowserUtils.waitFor(5);
        Assert.assertTrue(isElementDisplayed(programAndBenefitInfoPage.editCurrentEnrollmentButton));
        Assert.assertTrue(isElementDisplayed(programAndBenefitInfoPage.disregardCurrentEnrollmentButton));
        Assert.assertEquals(getText(programAndBenefitInfoPage.currentEnrollmentSelectionStatus), "Selection Made");
    }

    @Then("I verify Edit & Disregard buttons are displayed on the new enrollment segment Selection Status = Disenroll Requested")
    public void i_verify_Edit_Disregard_buttons_are_displayed_on_the_new_enrollment_segment_Selection_Status_Disenroll_Requested() {
        BrowserUtils.waitFor(5);
        Assert.assertTrue(isElementDisplayed(programAndBenefitInfoPage.editCurrentEnrollmentButton));
        Assert.assertTrue(isElementDisplayed(programAndBenefitInfoPage.disregardCurrentEnrollmentButton));
        Assert.assertEquals(getText(programAndBenefitInfoPage.currentEnrollmentSelectionStatus), "Disenroll Requested");
    }

    @Then("I verify Disenroll button is displayed on the enrollment segment Selection Status = Accepted")
    public void i_verify_Disenroll_button_is_displayed_on_the_enrollment_segment_Selection_Status_Accepted() {
        BrowserUtils.waitFor(3);
        Assert.assertTrue(isElementDisplayed(programAndBenefitInfoPage.disenrollCurrentEnrollmentButton));
        Assert.assertEquals(getText(programAndBenefitInfoPage.currentEnrollmentSelectionStatus), "Accepted");
    }

    @Then("I verify consumer actions are not available")
    public void i_verify_consumer_actions_are_not_available() {
        scrollUpUsingActions(1);
        assertEquals(enrollmentUpdatePage.calendarDays.getText(), "-- -- DAYS");
        assertFalse(isElementDisplayed(programAndBenefitInfoPage.hoverElementIconInfo));
        assertFalse(isElementDisplayed(programAndBenefitInfoPage.calendarHoverTextLine1));
    }

    @Then("I will click submit button")
    public void iWillClickSubmitButton() {
        waitForVisibility(enrollmentUpdatePage.submitButton, 10);
        enrollmentUpdatePage.submitButton.click();
    }

    @And("I click on Disregard button on program & benefits page")
    public void iClickOnDisregardButtonOnProgramBenefitsPage() {
        waitFor(2);
        waitForVisibility(enrollmentUpdatePage.disregardBtn, 10);
        enrollmentUpdatePage.disregardBtn.click();
    }

    @Then("I verify Warning message is displayed {string}")
    public void iVerifyWarningMessageIsDisplayed(String message) {
        waitForVisibility(enrollmentUpdatePage.enrolmentUpdatePageWarningMessage, 10);
        assertEquals(message, enrollmentUpdatePage.warningMeassage.getText());
        System.out.println("Warning message is : " + message + " - PASS!");
    }

    @Then("I verify enrollment selection dates are not valid message is displayed")
    public void iVerifyWarningMessageIsDisplayed() {
        waitForVisibility(enrollmentUpdatePage.enrollmentSelectionDatesAreNotValidMessage, 3);
        warningMessage.set(enrollmentUpdatePage.enrollmentSelectionDatesAreNotValidMessage.getText());
        assertTrue(enrollmentUpdatePage.enrollmentSelectionDatesAreNotValidMessage.getText().contains("Enrollment selection dates are not valid"));
    }

    @Then("I verify enrollment selection dates are not valid warning message includes consumer {string}")
    public void iVerifyWarningMessageIncludesConsumer(String consumerInfo) {
//        waitForVisibility(enrollmentUpdatePage.enrollmentSelectionDatesAreNotValidMessage, 3);
        assertTrue(warningMessage.get().contains(String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerInfo + ".externalConsumerId"))));
    }

    @And("I click on continue for Disregard Enrollment")
    public void iClickOnContinueForDisregardEnrollment() {
        waitForVisibility(enrollmentUpdatePage.continuebtn, 10);
        System.out.println("Clicking on continue for Disregard Enrollment");
        enrollmentUpdatePage.continuebtn.click();
    }

    @And("New enrollment selection is not displayed on the program & benefits info screen")
    public void newEnrollmentSelectionIsNotDisplayedOnTheProgramBenefitsInfoScreen() {
        waitFor(3);
        assertFalse(isElementDisplayed(enrollmentUpdatePage.futureEnrollment));
    }

    @And("Edit and Disregard button are not displayed on the Enrollment section")
    public void editAndDisregardButtonAreNotDisplayedOnTheEnrollmentSection() {
        waitFor(3);
        assertFalse(isElementDisplayed(enrollmentUpdatePage.disregardBtn));
        assertFalse(isElementDisplayed(enrollmentUpdatePage.editBtn));
    }

    @Then("I verify the consumer actions are available")
    public void iVerifyTheConsumerActionsAreAvailable() {
        assertTrue(isElementDisplayed(enrollmentUpdatePage.calendarDays));
        assertTrue(isElementDisplayed(enrollmentUpdatePage.calendarRedDot));
    }


    @Then("Verify Consumers with same Case")
    public void VerifyConsumerswithsameCase() {
        assertTrue(isElementDisplayed(programAndBenefitInfoPage.frontArrow));

        for (int i = 1; i < programAndBenefitInfoPage.listOfPag.size() - 1; i++) {
            waitFor(2);
            for (int k = 0; k < programAndBenefitInfoPage.consumerIds.size(); k++) {
                waitFor(2);
                assertTrue(isElementDisplayed(programAndBenefitInfoPage.consumerIds.get(k)));
                waitFor(2);
            }
            if (programAndBenefitInfoPage.frontArrow.isDisplayed()) {
                programAndBenefitInfoPage.frontArrow.click();
            }
        }
    }

    @Then("I verify Consumer has the same Service Regions")
    public void iVerifyConsumerHasTheSameServiceRegions() {
        String serviceReg = enrollmentUpdatePage.serviceRegions.get(0).getText();
        for (int i = 0; i < enrollmentUpdatePage.serviceRegions.size(); i++) {
            assertTrue(enrollmentUpdatePage.serviceRegions.get(i).getText().equalsIgnoreCase(serviceReg), "Service Regions are not equal");
        }
    }

    @Then("I verify Consumer has the same Programs")
    public void iVerifyConsumerHasTheSamePrograms() {
        String program = enrollmentUpdatePage.ConsumerPrograms.get(0).getText();
        for (int i = 0; i < enrollmentUpdatePage.ConsumerPrograms.size(); i++) {
            waitFor(2);
            assertTrue(enrollmentUpdatePage.ConsumerPrograms.get(i).getText().equalsIgnoreCase(program), "Programs are not equal");
        }
    }

    @Then("I click on Plan Change button")
    public void iClickOnPlanChangeButton() {
        waitFor(1);
        enrollmentUpdatePage.frstPlanChangeButton.click();
    }

    @And("I verify clients available for Plan Change in the dropdown, who have not been previously added")
    public void iVerifyClientsAvailableForPlanChangeInTheDropdownWhoHaveNotBeenPreviouslyAdded() {
        waitFor(3);
        enrollmentUpdatePage.addCMButton.click();
        for (int i = 1; i < enrollmentUpdatePage.addedmembers.size(); i++) {
            waitFor(1);
            assertTrue(members.get().contains(enrollmentUpdatePage.addedmembers.get(i).getText()), "Member is not available");
        }
    }

    @Then("Save List of members")
    public void saveListOfMembers() {
        for (int i = 0; i < enrollmentUpdatePage.listMembers.size(); i++) {
            members.get().add(enrollmentUpdatePage.listMembers.get(i).getText());
        }
    }

    @When("I clicking add case member btn and choosing the first member")
    public void iClickingAddCaseMemberBtnAndChoosingTheFirstMember() {
        waitFor(3);
        enrollmentUpdatePage.addCMButton.click();
        frstMember.set(enrollmentUpdatePage.addedmembers.get(1).getText());
        enrollmentUpdatePage.addedmembers.get(1).click();
    }

    @And("I verify clients section must not be present in the dropdown")
    public void iVerifyClientsSectionMustNotBePresentInTheDropdown() {
        for (int i = 1; i < enrollmentUpdatePage.addedmembers.size(); i++) {
            assertNotEquals(members.get().contains(enrollmentUpdatePage.addedmembers.get(i).getText()),
                    frstMember.get(), "Member available in the dropdown");
        }
    }

    @When("I verify Add Case Member button not visible")
    public void iVerifyAddCaseMemberButtonNotVisible() {
        assertFalse(isElementDisplayed(enrollmentUpdatePage.addCMButton), "Add Member button is DISPLAYED");
    }

    @When("I click Edit button")
    public void i_click_Edit_button() {
        programAndBenefitInfoPage.editCurrentEnrollmentButton.click();
    }

    @Then("I will be navigated to the “Edit Enrollment” screen")
    public void i_will_be_navigated_to_the_Edit_Enrollment_screen() {
        Assert.assertTrue(enrollmentUpdatePage.enrollmentEdit.isDisplayed());
    }

    @Then("I will see the consumer details, the provider panel, and the plan details panel")
    public void i_will_see_the_consumer_details_the_provider_panel_and_the_plan_details_panel() {
        Assert.assertEquals(enrollmentUpdatePage.searchProviderPanel.getText(), "Use the button to the right to select a provider");
        Assert.assertTrue(enrollmentUpdatePage.consumerDetails.isDisplayed());
        Assert.assertTrue(enrollmentUpdatePage.selectedPlanDetails.isDisplayed());
    }

    @Then("I will see the “Remove Plan” button")
    public void i_will_see_the_Remove_Plan_button() {
        Assert.assertTrue(enrollmentUpdatePage.removePlanBtn.isDisplayed());
    }

    @Then("I verify “+ Add Case Member\\(s)” button is grayed out and not available")
    public void i_verify_Add_Case_Member_s_button_is_grayed_out_and_not_available() {
        if (isElementDisplayed(enrollmentUpdatePage.enrollmentUpdateAddCaseMembersButton)) {
            Assert.assertFalse(enrollmentUpdatePage.enrollmentUpdateAddCaseMembersButton.isEnabled());
        } else
            Assert.assertFalse(isElementDisplayed(enrollmentUpdatePage.enrollmentUpdateAddCaseMembersButton));
    }

    @Then("verify I will only be able to “Edit” plan selection for one consumer at a time")
    public void verify_I_will_only_be_able_to_Edit_plan_selection_for_one_consumer_at_a_time() {
        Assert.assertEquals(1, enrollmentUpdatePage.listConsumersNamesEnrollmentPage.size());
    }

    @Then("verify I will only be able to “Disregard” plan selection for one consumer at a time")
    public void verify_I_will_only_be_able_to_Disregard_plan_selection_for_one_consumer_at_a_time() {
        Assert.assertEquals(0, enrollmentUpdatePage.listConsumersNamesEnrollmentPage.size());
    }

    @Then("I verify the plan selection will be removed")
    public void i_verify_the_plan_selection_will_be_removed() {
        Assert.assertTrue(enrollmentUpdatePage.selectPlanBttn.get(0).isDisplayed());
    }

    @Then("I will see “Select” button is available for each plan")
    public void i_will_see_Select_button_is_available_for_each_plan() {
        for (WebElement element : enrollmentUpdatePage.selectPlanBttn) {
            Assert.assertTrue(element.isDisplayed());
        }
    }

    @Then("I verify “Select” button grayed out and not available for consumer current enrollment")
    public void i_verify_Select_button_grayed_out_and_not_available_for_consumer_current_enrollment() {
        waitFor(3);
        Assert.assertFalse(enrollmentUpdatePage.selectPlanBttn1.isEnabled());
    }

    @Then("I verify Submit button activated")
    public void i_verify_Submit_button_activated() {
        Assert.assertTrue(enrollmentUpdatePage.submitButton.isDisplayed());
    }

    @Then("I will not be able to change the enrollment dates")
    public void i_will_not_be_able_to_change_the_enrollment_dates() {
        try {
            enrollmentUpdatePage.selectedPlanStartDate.click();
        } catch (Exception WebDriverException) {
        }
    }

    @Then("I will be required to enter a “Reason” for the change")
    public void i_will_be_required_to_enter_a_Reason_for_the_change() {
        enrollmentUpdatePage.enrolmentUpdateReasonbttn.isDisplayed();
    }

    @Then("I verify the enrollment dates for the consumer’s future enrollment selection will be the same")
    public void i_verify_the_enrollment_dates_for_the_consumer_s_future_enrollment_selection_will_be_the_same() {
        Assert.assertEquals(apiTestDataUtil.get().getStartDate("firstDayofPresntMonth"), getText(programAndBenefitInfoPage.currentEnrollmentPlanStartDate));
    }

    @Then("I will click on cancel button")
    public void i_will_click_on_cancel_button() {
        enrollmentUpdatePage.cancelBtn.click();
    }

    @When("I will click on cancel button in the warning message")
    public void i_will_click_on_cancel_button_in_the_warning_message() {
        enrollmentUpdatePage.cancelBtnWarningMessage.click();
    }

    @Then("I will click on continue button")
    public void i_will_click_on_continue_button() {
        enrollmentUpdatePage.continueBtn.click();
    }

    @Then("verify I will be navigated to the “Program & Benefits Info” screen with no change to the consumer’s enrollment")
    public void verify_I_will_be_navigated_to_the_Program_Benefits_Info_screen_with_no_change_to_the_consumer_s_enrollment() {
        Assert.assertTrue(programAndBenefitInfoPage.currentEligibilityEnrollBttn.isDisplayed());
    }

    @When("I select A second plan from Available Plans")
    public void i_select_A_second_plan_from_Available_Plans() {
        waitFor(7);
        enrollmentUpdatePage.selectPlanBttn.get(2).click();
        waitFor(2);
        selectedPlanName.set(enrollmentUpdatePage.selectedPlanName.get(0).getText().trim());
        try {
            selectedPlanStartDate.set(enrollmentUpdatePage.selectedPlanStartDate.getAttribute("value"));
        } catch (Exception e) {
            selectedPlanStartDate.set(enrollmentUpdatePage.selectedPlanStartDateH6.getText());
        }
        selectedPlanEndDate.set(enrollmentUpdatePage.selectedPlanEndDate.getText().trim());
    }

    @When("I select A first plan from Available Plans")
    public void i_select_A_first_plan_from_Available_Plans() {
        waitFor(7);
        enrollmentUpdatePage.selectPlanBttn.get(0).click();
    }

    @When("I select {string} from Available Plans")
    public void i_select_from_Available_Plans(String planName) {
        waitFor(3);
        enrollmentUpdatePage.getPlanSelectButtonByGivenPlanName(planName).click();
        waitFor(2);
        selectedPlanName.set(enrollmentUpdatePage.selectedPlanName.get(0).getText().trim());
        try {
            selectedPlanStartDate.set(enrollmentUpdatePage.selectedPlanStartDate.getAttribute("value"));
        } catch (Exception e) {
            selectedPlanStartDate.set(enrollmentUpdatePage.selectedPlanStartDateH6.getText());
        }
        selectedPlanEndDate.set(enrollmentUpdatePage.selectedPlanEndDate.getText().trim());
    }

    @When("I verify I can't click submit button and update plan without choosing the reason")
    public void i_verify_I_can_t_click_submit_button_and_update_plan_without_choosing_the_reason() {
        assertFalse(enrollmentUpdatePage.submitBttn.isEnabled(), "The button is enabled");
    }

    @Then("I select a reason for plan change")
    public void i_select_a_reason_for_plan_change() {
        enrollmentUpdatePage.reasonDropDownChoices.get(1).click();
        waitFor(2);
    }

    @Then("Plan Change Reasons and Plan Change Override Reasons should display in separate drop down selections")
    public void plan_Change_Reasons_and_Plan_Change_Override_Reasons_should_display_in_separate_drop_down_selections() {
        Assert.assertTrue(enrollmentUpdatePage.enrolmentUpdateReasonbttn.isDisplayed());
        Assert.assertTrue(enrollmentUpdatePage.enrolmentOverrideReasonbttn.isDisplayed());
    }

    @Then("I change start date to {int} on calendar")
    public void I_change_start_date_to_on_calendar(int day) {
        waitForClickablility(enrollmentUpdatePage.editCalendarDateBttn, 2).click();
        waitForVisibility(enrollmentUpdatePage.editCalendarDateForm, 2);
        System.out.println("changing calendar date to " + day);
        enrollmentUpdatePage.calendarDayButton(day).click();
        enrollmentUpdatePage.calendarActionButton("OK").click();
        selectedPlanStartDate.set(enrollmentUpdatePage.selectedPlanStartDate.getAttribute("value"));
    }

    @When("I change start date to {int} month earlier to day {int} on calendar")
    public void I_change_start_date_to_month_earlier_on_calendar(int month, int day) {
        waitForClickablility(enrollmentUpdatePage.editCalendarDateBttn, 2).click();
        waitForVisibility(enrollmentUpdatePage.editCalendarDateForm, 2);
        System.out.println("changing calendar date to " + month + " month(s) earlier");
        for (int i = 0; i < month; i++) {
            enrollmentUpdatePage.calendarPreviousMonthButton.click();
            waitFor(1);
        }
        enrollmentUpdatePage.calendarDayButton(day).click();
        enrollmentUpdatePage.calendarActionButton("OK").click();
        selectedPlanStartDate.set(enrollmentUpdatePage.selectedPlanStartDate.getAttribute("value"));
    }

    @When("I change start date to {int} month later to day {int} on calendar")
    public void I_change_start_date_to_month_later_on_calendar(int month, int day) {
        waitForClickablility(enrollmentUpdatePage.editCalendarDateBttn, 2).click();
        waitForVisibility(enrollmentUpdatePage.editCalendarDateForm, 2);
        System.out.println("changing calendar date to " + month + " month(s) later");
        for (int i = 0; i < month; i++) {
            enrollmentUpdatePage.calendarNextMonthButton.click();
            waitFor(1);
        }
        enrollmentUpdatePage.calendarDayButton(day).click();
        enrollmentUpdatePage.calendarActionButton("OK").click();
        selectedPlanStartDate.set(enrollmentUpdatePage.selectedPlanStartDate.getAttribute("value"));
    }

    @Then("I add {int} days from selected day on calendar")
    public void I_add_days_from_selected_day_on_calendar(int day) {
        waitForClickablility(enrollmentUpdatePage.editCalendarDateBttn, 2).click();
        waitForVisibility(enrollmentUpdatePage.editCalendarDateForm, 2);
        int selectedDay = Integer.parseInt(enrollmentUpdatePage.selectedCalendarDay.findElement(By.xpath(".//p")).getText());
        day = selectedDay + day < 29 ? selectedDay + day : selectedDay - day;
        System.out.println("changing calendar date to " + day);
        enrollmentUpdatePage.calendarDayButton(day).click();
        enrollmentUpdatePage.calendarActionButton("OK").click();
        waitForVisibility(enrollmentUpdatePage.selectedPlanStartDate, 2);
        selectedPlanStartDate.set(enrollmentUpdatePage.selectedPlanStartDate.getAttribute("value"));
    }

    @Then("I verify below details on newly created current enrollment segment on Enrollment Update Page")
    public void I_verify_below_details_on_newly_created_current_enrollment_segment_on_Enrollment_Update_Page(Map<String, String> data) {
        waitFor(5);
        String expValue = "1";
        String actValue = "1";
        for (String field : data.keySet()) {
            switch (field) {
                case "PLAN NAME":
                    expValue = CRMEnrollmentUpdateStepDef.selectedPlanName.get().toLowerCase();
                    actValue = enrollmentUpdatePage.planNameValue.getText();
                    System.out.println("Plan name is " + actValue);
                    break;
                case "PLAN TYPE":
                    expValue = data.get(field);
                    actValue = enrollmentUpdatePage.planTypeValue.getText();
                    System.out.println("Plan type is " + actValue);
                    break;
                case "SERVICE REGION":
                    if (data.get(field).equals("hidden")) {
                        try {
                            assertFalse(enrollmentUpdatePage.serviceRegionOnPlanDetailsPanel.isDisplayed(), "SERVICE REGION is displayed!");
                        } catch (Exception e) {
                            System.out.println("SERVICE REGION is not displayed");
                        }
                        continue;
                    } else {
                        expValue = data.get(field);
                        actValue = enrollmentUpdatePage.serviceRegionValue.getText();
                    }
                    break;
                case "START DATE":
                    if (data.get(field).equals("-- --") || data.get(field).equals("null") || data.get(field).isEmpty()) {
                        expValue = "-- --";
                    } else {
                        expValue = apiTestDataUtil.get().getStartDate(data.get(field));
                    }
                    try {
                        actValue = enrollmentUpdatePage.selectedPlanStartDate.getAttribute("value");
                    } catch (Exception e) {
                        actValue = enrollmentUpdatePage.selectedPlanStartDateH6.getText();
                    }
                    System.out.println("Start date is " + actValue);
                    break;
                case "END DATE":
                    expValue = data.get(field).equals("-- --")
                            ? "-- --"
                            : apiTestDataUtil.get().getEndDate(data.get(field));
                    actValue = enrollmentUpdatePage.endDateValue.getText();
                    System.out.println("End date is " + actValue);
                    break;
                case "SELECTED CONSUMER NAMES":
                    List<String> expectedConsumerFullNames = new ArrayList<>();
                    for (String each : data.get(field).split(",")) {
                        String fullName = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(each + ".firstName").toString() + " "
                                + API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(each + ".lastName").toString();
                        expectedConsumerFullNames.add(fullName);
                    }
                    List<String> actualConsumersFullNames = getElementsText(enrollmentUpdatePage.selectedConsumersNames);
                    System.out.println("expectedConsumerFullNames.toString() = " + expectedConsumerFullNames);
                    System.out.println("actualConsumersFullNames = " + actualConsumersFullNames);
                    assertTrue(actualConsumersFullNames.containsAll(expectedConsumerFullNames),
                            "List of selected consumers mismatch");
                    break;
                case "AGE/GENDER":
                    assertEquals(enrollmentUpdatePage.enrolmentUpdatePageAgeGenderField.getText(),
                            data.get(field), field + " mismatch!");
                    break;
                case "POPULATION":
                    assertEquals(enrollmentUpdatePage.enrolmentUpdatePagePopulationField.getText(),
                            data.get(field), field + " mismatch!");
                    break;
                case "RESIDENTIAL ADDRESS":
                    assertEquals(enrollmentUpdatePage.enrolmentUpdatePageAddressField.getText(),
                            data.get(field), field + " mismatch!");
                    break;
                case "CURRENT PLAN":
                    assertEquals(enrollmentUpdatePage.enrolmentUpdatePageCurrentPlanField.getText(),
                            data.get(field), field + " mismatch!");
                    break;
                case "CURRENT PROVIDER":
                    assertEquals(enrollmentUpdatePage.enrolmentUpdatePageCurrentProviderField.getText(),
                            data.get(field), field + " mismatch!");
                    break;
                case "ELIGIBILITY DETAILS":
                    assertEquals(enrollmentUpdatePage.enrolmentUpdatePageEligibilityDetailsField.getText(),
                            apiTestDataUtil.get().getStartDate(data.get(field).split(" - ")[0]) + " - "
                                    + apiTestDataUtil.get().getEndDate(data.get(field).split(" - ")[1]), field + " mismatch!");
                    break;
                default:
                    assertFalse(false, "Field validation is not implemented for: " + field);
            }
            assertEquals(actValue.toLowerCase(), expValue.toLowerCase(), "assertion failed for the field validation: " + field);
        }
    }

    @Then("I verify list of all available plans on Enrollment Update Page with data")
    public void i_verify_list_of_all_available_plans_on_enrollment_update_pagewith_data(List<String> data) {
        List<String> expectedPlans = data.stream().sorted().collect(Collectors.toList());
        List<String> actualPlans = getElementsText(enrollmentUpdatePage.listOfPlanNamesOnPlansAvailableSection)
                .stream().sorted().collect(Collectors.toList());
        assertEquals(actualPlans, expectedPlans, "actual Plans list don't match expected Plans list!");
    }

    @Then("I verify below details on Plans Available segment on Enrollment Update Page")
    public void i_verify_below_details_on_plans_available_segment_on_enrollment_update_page(Map<String, String> data) {
        for (String field : data.keySet()) {
            switch (field) {
                case "START DATE":
                    String expectedStartDate = apiTestDataUtil.get().getStartDate(data.get(field));
                    for (String actualStartDate : getElementsText(enrollmentUpdatePage.listOfStartDateOnPlansAvailableSection)) {
                        assertEquals(actualStartDate, expectedStartDate, "actual Start date is " + actualStartDate);
                    }
                    break;
                case "END DATE":
                    String expectedEndDate = (data.get(field).equals("-- --")
                            || data.get(field).equals("null")
                            || data.get(field).isEmpty()) ? "-- --" : apiTestDataUtil.get().getEndDate(data.get(field));
                    for (String actualEndDate : getElementsText(enrollmentUpdatePage.listOfEndDateOnPlansAvailableSection)) {
                        assertEquals(actualEndDate, expectedEndDate, "actual End date is " + actualEndDate);
                    }
                    break;
                case "PLAN TYPE":
                    for (String actualPlanType : getElementsText(enrollmentUpdatePage.listOfPlanTypeOnPlansAvailableSection)) {
                        assertEquals(actualPlanType, data.get(field), "actual Start date is " + actualPlanType);
                    }
                    break;
            }
        }
    }

    @Then("I verify program & benefit info page for consumer first name {string} and last name {string} with data")
    public void I_verify_program_benefit_info_page_for_consumer_first_name_and_last_name_with_data(String firstName, String lastName, Map<String, String> data) {
        waitFor(5);
        String fullName = (firstName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerFirstName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(firstName).toString()) + " "
                + (lastName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerLastName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(lastName).toString());
        WebElement givenConsumerBox = programAndBenefitInfoPage.getGivenConsumerBoxByFullName(fullName);
        scrollToElement(givenConsumerBox);
        scrollUpUsingActions(1);
        waitFor(2);
        try {
            click(programAndBenefitInfoPage.getIconInsideGivenParentByIconText(
                    programAndBenefitInfoPage.getInfoBoxInsideGivenParentByEnrolmentName(givenConsumerBox, "FUTURE ENROLLMENT"),
                    "chevron_right"));
            System.out.println("Clicking carrot for future enrollment discontinued enrollment info if present");
        } catch (Exception e) {
            System.out.println("Carrot for future enrollment discontinued enrollment info is NOT present. Continue ...");
        }
        try {
            click(programAndBenefitInfoPage.getIconInsideGivenParentByIconText(
                    programAndBenefitInfoPage.getInfoBoxInsideGivenParentByEnrolmentName(givenConsumerBox, "SPECIAL COVERAGE"),
                    "chevron_right"));
            System.out.println("Clicking carrot for SPECIAL COVERAGE info if present");
        } catch (Exception e) {
            System.out.println("Carrot for SPECIAL COVERAGE info is NOT present. Continue ...");
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        waitFor(3);
        hover(programAndBenefitInfoPage.getIconInsideGivenParentByIconText(givenConsumerBox, "calendar_today"));
        for (String field : data.keySet()) {
            String expVal = "1";
            String actVal = "1";
            switch (field.split("\\.")[1]) {
                case "ACTION TEXT":
                    try{
                        waitForVisibility(programAndBenefitInfoPage.calendarHoverIcon, 10);
                    }catch(TimeoutException te){
                        hover(programAndBenefitInfoPage.getIconInsideGivenParentByIconText(givenConsumerBox, "calendar_today"));
                        waitForVisibility(programAndBenefitInfoPage.calendarHoverIcon, 10);
                    }
                    if (data.get(field).isEmpty() || data.get(field).equals("null")) {
                        actVal = programAndBenefitInfoPage.emptyAction.getText();
                        expVal = "";
                    } else if (data.get(field).equals("-- --")) {
                        actVal = programAndBenefitInfoPage.emptyAction.getText();
                        expVal = "-- --";
                    } else {
                        actVal = getElementsText(programAndBenefitInfoPage.actionNames).toString().replaceAll("[\\[\\]]", "");
                        expVal = data.get(field);
                    }
                    System.out.println("Consumer action " + actVal);
                    break;
                case "RED DOT":
                    boolean shouldBeDisplayed = true;
                    try {
                        if (data.get(field).contains("according to action timeframe")) {
                            String dates = data.get(field).split(": ")[1];
                            if (LocalDate.now().isBefore(LocalDate.parse(apiTestDataUtil.get().getStartDate(dates.split(" - ")[0]), df))) {
                                shouldBeDisplayed = false;
                                assertFalse(givenConsumerBox.findElement(By.xpath(".//span[contains(@class, 'MuiBadge-colorError MuiBadge-dot')]")).isDisplayed(),
                                        "RED DOT is displayed! - FAIL!");
                                System.out.println("RED DOT is not displayed! - PASS!");
                            } else {
                                assertTrue(givenConsumerBox.findElement(By.xpath(".//span[contains(@class, 'MuiBadge-colorError MuiBadge-dot')]")).isDisplayed(),
                                        "RED DOT is NOT displayed! - FAIL!");
                                System.out.println("RED DOT is displayed! - PASS!");
                            }
                        } else {
                            if (data.get(field).equals("hidden")) {
                                shouldBeDisplayed = false;
                                assertFalse(givenConsumerBox.findElement(By.xpath(".//span[contains(@class, 'MuiBadge-colorError MuiBadge-dot')]")).isDisplayed(),
                                        "RED DOT is displayed! - FAIL!");
                                System.out.println("RED DOT is not displayed! - PASS!");
                            } else {
                                assertTrue(givenConsumerBox.findElement(By.xpath(".//span[contains(@class, 'MuiBadge-colorError MuiBadge-dot')]")).isDisplayed(),
                                        "RED DOT is NOT displayed! - FAIL!");
                                System.out.println("RED DOT is displayed! - PASS!");
                            }
                        }
                    } catch (Exception e) {
                        if (shouldBeDisplayed) fail("RED DOT is not displayed! - FAIL!");
                    }
                    break;
                case "IMPORTANT DATES":
                    waitForVisibility(programAndBenefitInfoPage.calendarHoverIcon, 10);
                    if (data.get(field).isEmpty() || data.get(field).equals("null")) {
                        actVal = programAndBenefitInfoPage.emptyAction.getText();
                        expVal = "";
                    } else if (data.get(field).equals("-- --")) {
                        actVal = programAndBenefitInfoPage.emptyAction.getText();
                        expVal = "-- --";
                    } else {
                        actVal = getElementsText(programAndBenefitInfoPage.actionDates).toString().replaceAll("[\\[\\]]", "");
                        expVal = "";
                        int i = 0;
                        for (String part : data.get(field).split(", ")) {
                            expVal = expVal + (i == 0 ? "" : ", ") + apiTestDataUtil.get().getStartDate(part.split(" - ")[0])
                                    + " - " + apiTestDataUtil.get().getEndDate(part.split(" - ")[1]);
                            i++;
                        }
                    }
                    System.out.println("expVal = " + expVal);
                    System.out.println("Action important days " + actVal);
                    break;
                case "NUMBER OF DAYS UNTIL":
                    actVal = programAndBenefitInfoPage.getDaysLeftSpanInsideGivenParent(givenConsumerBox).getText().split("\\s+")[0];
                    if (data.get(field).contains("according to action timeframe")) {
                        String dates = data.get(field).split(": ")[1];
                        if (data.get(field).contains("(with -)")) {
                            if (LocalDate.now().isBefore(LocalDate.parse(apiTestDataUtil.get().getStartDate(dates.split(" - ")[0]), df))) {
                                expVal = "--";
                            } else if (LocalDate.now().isAfter(LocalDate.parse(apiTestDataUtil.get().getEndDate(dates.split(" - ")[1]), df))) {
                                expVal = Long.toString(ChronoUnit.DAYS.between(
                                        LocalDate.parse(apiTestDataUtil.get().getEndDate(dates.split(" - ")[1]), df)
                                                .plusDays(1)
                                        , LocalDate.now()
                                ));
                            } else {
                                expVal = Long.toString(ChronoUnit.DAYS.between(
                                        LocalDate.now(), LocalDate.parse(apiTestDataUtil.get().getEndDate(dates.split(" - ")[1]), df)
                                                .plusDays(1)
                                ));
                            }
                        } else {
                            if (LocalDate.now().isBefore(LocalDate.parse(apiTestDataUtil.get().getStartDate(dates.split(" - ")[0]), df))
                                    || LocalDate.now().isAfter(LocalDate.parse(apiTestDataUtil.get().getEndDate(dates.split(" - ")[1]), df))) {
                                expVal = "--";
                            } else {
                                expVal = Long.toString(ChronoUnit.DAYS.between(
                                        LocalDate.now(), LocalDate.parse(apiTestDataUtil.get().getEndDate(dates.split(" - ")[1]), df)
                                                .plusDays(1)
                                ));
                            }
                        }
                    } else if (data.get(field).isEmpty() || data.get(field).equals("null") || data.get(field).equals("-- --")) {
                        expVal = "--";
                    } else {
                        expVal = Long.toString(ChronoUnit.DAYS.between(
                                LocalDate.now(), LocalDate.parse(apiTestDataUtil.get().getEndDate(data.get(field)), df)
                                        .plusDays(1)
                        ));
                    }
                    System.out.println("Count down numbers " + actVal);
                    break;
                case "ICON HOVER":
                    hover(programAndBenefitInfoPage.getIconInsideGivenParentByIconText(givenConsumerBox, "info_outline"));
                    if (data.get(field).isEmpty() || data.get(field).equals("null") || data.get(field).equals("-- --")) {
                        try {
                            assertFalse(programAndBenefitInfoPage.hoverElementIconInfo.isDisplayed(), "ICON HOVER message is displayed!");
                        } catch (Exception e) {
                            System.out.println("ICON HOVER message is hidden, TEST PASS!");
                        }
                    } else {
                        actVal = waitForVisibility(programAndBenefitInfoPage.hoverElementIconInfo, 2).getText().replace("\n", " ");
                        expVal = data.get(field);
                        System.out.println("Count down icon hover text: " + actVal);
                    }
                    break;
                case "END DATE":
                    expVal = data.get(field).equals("-- --")
                            ? "-- --"
                            : data.get(field).equals("DayBeforeSelectedPlanStartDate")
                            ? LocalDate.parse(CRMEnrollmentUpdateStepDef.selectedPlanStartDate.get(), dfUIver).minusDays(1).format(dfUIver)
                            : apiTestDataUtil.get().getEndDate(data.get(field));
                    actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenEnrollmentGivenLabelText(
                            givenConsumerBox, field.split("\\.")[0], field.split("\\.")[1]
                    ).getText();

                    System.out.println(field + " value is " + actVal);
                    break;
                case "START DATE":
                    expVal = data.get(field).contains("/")
                            ? data.get(field)
                            : data.get(field).contains("selectedPlanStartDate")
                            ? CRMEnrollmentUpdateStepDef.selectedPlanStartDate.get()
                            : apiTestDataUtil.get().getStartDate(data.get(field));
                    actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenEnrollmentGivenLabelText(
                            givenConsumerBox, field.split("\\.")[0], field.split("\\.")[1]
                    ).getText();

                    System.out.println(field + " value is " + actVal);
                    break;
                case "ENROLL BUTTON":
                case "PCP SELECT BUTTON":
                case "DISENROLL BUTTON":
                case "PLAN CHANGE BUTTON":
                    try {
                        if (data.get(field).equals("HIDDEN"))
                            assertFalse(programAndBenefitInfoPage.getFieldInsideGivenParentGivenPriorEnrollmentGivenLabelText(givenConsumerBox, field.split("\\.")[1]).isDisplayed(), field.split("\\.")[0] + "." + field.split("\\.")[1] + " is displayed! - FAIL!");
                    } catch (Exception e) {
                        System.out.println(field.split("\\.")[0] + "." + field.split("\\.")[1] + " is NOT displayed! - PASS!");
                    }
                    break;
                case "EDIT BUTTON":
                    try {
                        if (data.get(field).equals("HIDDEN"))
                            assertFalse(programAndBenefitInfoPage.getFieldInsideGivenParentGivenPriorEnrollmentGivenLabelText(givenConsumerBox, field.split("\\.")[1]).isDisplayed(), field.split("\\.")[0] + "." + field.split("\\.")[1] + " is displayed! - FAIL!");
                    } catch (Exception e) {
                        System.out.println(field.split("\\.")[0] + "." + field.split("\\.")[1] + " is NOT displayed! - PASS!");
                    }
                    break;
                case "DISREGARD BUTTON":
                    try {
                        if (data.get(field).contains("according to action timeframe")) {
                            String dates = data.get(field).split(": ")[1];
                            if (LocalDate.now().isBefore(LocalDate.parse(apiTestDataUtil.get().getStartDate(dates.split(" - ")[0]), df))
                                    || LocalDate.now().isAfter(LocalDate.parse(apiTestDataUtil.get().getEndDate(dates.split(" - ")[1]), df))) {
                                assertFalse(programAndBenefitInfoPage.getButtonInsideGivenParentGivenEnrollmentGivenButtonText(
                                        givenConsumerBox, field.split("\\.")[0], field.split("\\.")[1].replace(" BUTTON", "")
                                ).isDisplayed(), field.split("\\.")[1] + " is displayed - FAIL!");
                            } else {
                                assertTrue(programAndBenefitInfoPage.getButtonInsideGivenParentGivenEnrollmentGivenButtonText(
                                        givenConsumerBox, field.split("\\.")[0], field.split("\\.")[1].replace(" BUTTON", "")
                                ).isDisplayed(), field.split("\\.")[1] + " is not displayed - FAIL!");
                                System.out.println(field + " is displayed - PASS!");
                            }
                        } else if (data.get(field).equals("hidden")) {
                            assertFalse(programAndBenefitInfoPage.getButtonInsideGivenParentGivenEnrollmentGivenButtonText(
                                    givenConsumerBox, field.split("\\.")[0], field.split("\\.")[1].replace(" BUTTON", "")
                            ).isDisplayed(), field.split("\\.")[1] + " is displayed - FAIL!");
                        } else {
                            assertTrue(programAndBenefitInfoPage.getButtonInsideGivenParentGivenEnrollmentGivenButtonText(
                                    givenConsumerBox, field.split("\\.")[0], field.split("\\.")[1].replace(" BUTTON", "")
                            ).isDisplayed(), field.split("\\.")[1] + " is not displayed - FAIL!");
                            System.out.println(field + " is displayed - PASS!");
                        }
                    } catch (Exception e) {
                        System.out.println(field + " is not displayed - PASS!");
                    }
                    break;
                case "PLAN NAME":
                    if (data.get(field).equals("selectedPlanName")) {
                        expVal = CRMEnrollmentUpdateStepDef.selectedPlanName.get().toUpperCase();
                    } else if (data.get(field).startsWith("assignedPlanName")) {
                        String consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(field).substring(21) + ".consumerId").toString().replace(".0", "");
                        expVal = CRMEnrollmentUpdateStepDef.assignedPlanNames.get().get(consumerId);
                    } else {
                        expVal = data.get(field).toUpperCase();
                    }
                    if (field.split("\\.")[0].equals("PRIOR ENROLLMENT DETAILS")) {
                        actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenPriorEnrollmentGivenLabelText(givenConsumerBox, field.split("\\.")[1]).getText().toUpperCase();
                        break;
                    } else {
                        actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenEnrollmentGivenLabelText(
                                givenConsumerBox, field.split("\\.")[0], field.split("\\.")[1]
                        ).getText().toUpperCase();
                    }
                    System.out.println(field + " value is " + actVal);
                    break;
                case "ELIGIBILITY STATUS":
                    if (data.get(field).equals("hidden")) {
                        try {
                            assertFalse(programAndBenefitInfoPage.getFieldInsideGivenParentGivenEnrollmentGivenLabelText(
                                    givenConsumerBox, field.split("\\.")[0], field.split("\\.")[1]
                            ).isDisplayed(), field.split("\\.")[1] + " is displayed - FAIL!");
                        } catch (Exception e) {
                            System.out.println(field + " is not displayed - PASS!");
                        }
                    } else {
                        assertTrue(programAndBenefitInfoPage.getEligibilityStatusInsideGivenParent(givenConsumerBox, field.split("\\.")[0]).getText().contains(data.get(field)), field + "mismatch! - FAIL!");
                    }
                    break;
                case "DiscontinuedEnrollment":
                    switch (field.split("\\.")[2]) {
                        case "END DATE":
                            expVal = data.get(field).equals("-- --") ? "-- --" : apiTestDataUtil.get().getEndDate(data.get(field));
                            actVal = programAndBenefitInfoPage.getInfoBoxInsideGivenParentByEnrolmentName(givenConsumerBox, field.split("\\.")[0])
                                    .findElement(By.xpath(".//div[@class = 'col-12 px-0 py-2']/div[2]/div/div[7]/p")).getText();
                            System.out.println(field + " value is " + actVal);
                            break;
                        case "START DATE":
                            expVal = data.get(field).contains("/")
                                    ? data.get(field)
                                    : apiTestDataUtil.get().getStartDate(data.get(field));
                            actVal = programAndBenefitInfoPage.getInfoBoxInsideGivenParentByEnrolmentName(givenConsumerBox, field.split("\\.")[0])
                                    .findElement(By.xpath(".//div[@class = 'col-12 px-0 py-2']/div[2]/div/div[6]/p")).getText();
                            System.out.println(field + " value is " + actVal);
                            break;
                        case "PLAN NAME":
                            if (data.get(field).equals("selectedPlanName")) {
                                expVal = CRMEnrollmentUpdateStepDef.selectedPlanName.get().toUpperCase();
                            } else {
                                expVal = data.get(field).toUpperCase();
                            }
                            actVal = programAndBenefitInfoPage.getInfoBoxInsideGivenParentByEnrolmentName(givenConsumerBox, field.split("\\.")[0])
                                    .findElement(By.xpath(".//div[@class = 'col-12 px-0 py-2']/div[2]/div/div[2]/p")).getText();
                            System.out.println(field + " value is " + actVal);
                            break;
                        case "SELECTION STATUS":
                            expVal = data.get(field);
                            actVal = programAndBenefitInfoPage.getInfoBoxInsideGivenParentByEnrolmentName(givenConsumerBox, field.split("\\.")[0])
                                    .findElement(By.xpath(".//div[@class = 'col-12 px-0 py-2']/div[2]/div/div[3]/p")).getText().replace("\n", "");
                            System.out.println(field + " value is " + actVal);
                            break;
                        case "CHANNEL":
                            expVal = data.get(field);
                            actVal = programAndBenefitInfoPage.getInfoBoxInsideGivenParentByEnrolmentName(givenConsumerBox, field.split("\\.")[0])
                                    .findElement(By.xpath(".//div[@class = 'col-12 px-0 py-2']/div[2]/div/div[4]/p")).getText().replace("\n", "");
                            System.out.println(field + " value is " + actVal);
                            break;
                        case "PCP NAME":
                            expVal = data.get(field);
                            actVal = programAndBenefitInfoPage.getInfoBoxInsideGivenParentByEnrolmentName(givenConsumerBox, field.split("\\.")[0])
                                    .findElement(By.xpath(".//div[@class = 'col-12 px-0 py-2']/div[2]/div/div[5]/p")).getText().replace("\n", "");
                            System.out.println(field + " value is " + actVal);
                            break;
                        case "PDP NAME":
                            expVal = data.get(field);
                            actVal = programAndBenefitInfoPage.getInfoBoxInsideGivenParentByEnrolmentName(givenConsumerBox, field.split("\\.")[0])
                                    .findElement(By.xpath(".//div[@class = 'col-12 px-0 py-2']/div[2]/div/div[6]/p")).getText().replace("\n", "");
                            System.out.println(field + " value is " + actVal);
                            break;
                        case "HIDDEN":
                            try {
                                assertTrue(programAndBenefitInfoPage.getInfoBoxInsideGivenParentByEnrolmentName(givenConsumerBox, field.split("\\.")[0])
                                        .findElement(By.xpath(".//div[@class = 'col-12 px-0 py-2']/div[2]"))
                                        .isDisplayed(), field.split("\\.")[0] + " is displayed! - FAIL!");
                            } catch (Exception e) {
                                System.out.println(field.split("\\.")[0] + " is NOT displayed! - PASS!");
                            }
                            break;
                    }
                    break;
                case "HIDDEN":
                    try {
                        assertFalse(programAndBenefitInfoPage.getInfoBoxInsideGivenParentByEnrolmentName(givenConsumerBox, field.split("\\.")[0])
                                .isDisplayed(), field.split("\\.")[0] + " is displayed! - FAIL!");
                    } catch (Exception e) {
                        System.out.println(field.split("\\.")[0] + " is NOT displayed! - PASS!");
                    }
                    break;
                case "CHECK CORE":
                    if (field.split("\\.")[0].equals("INDICATOR")) {
                        if (data.get(field).equals("hidden")) {
                            try {
                                assertFalse(programAndBenefitInfoPage.getCheckCoreIndicator(fullName).isDisplayed(), field.split("\\.")[1] + " is displayed - FAIL!");
                            } catch (Exception e) {
                                System.out.println(field.split("\\.")[1] + " is NOT displayed! - PASS!");
                            }
                        } else if (data.get(field).equals("is displayed")) {
                            assertTrue(programAndBenefitInfoPage.getCheckCoreIndicator(fullName).isDisplayed(), field.split("\\.")[1] + " is not displayed - FAIL!");
                            System.out.println(field + " is displayed - PASS!");
                        } else {
                            fail(field + " has to be either \"hidden\" or \"is displayed\"");
                        }
                    } else if (field.split("\\.")[0].equals("TEXT")) {
                        hover(programAndBenefitInfoPage.getCheckCoreIndicator(fullName));
                        assertTrue(programAndBenefitInfoPage.checkCoreText.getText().equalsIgnoreCase(data.get(field)), field.split("\\.")[1] + " text is not displayed correctly and found "+programAndBenefitInfoPage.checkCoreText.getText()+"- FAIL!");
                        hover(programAndBenefitInfoPage.getIconInsideGivenParentByIconText(givenConsumerBox, "calendar_today"));
                    } else if (field.split("\\.")[0].equals("MMIS")) {
                        hover(programAndBenefitInfoPage.getCheckCoreIndicator(fullName));
                        assertTrue(programAndBenefitInfoPage.checkCoreMMISText.getText().equalsIgnoreCase(data.get(field)), field.split("\\.")[1] + " text is not displayed correctly- FAIL!");
                        hover(programAndBenefitInfoPage.getIconInsideGivenParentByIconText(givenConsumerBox, "calendar_today"));
                    }
                    break;
                case "SPECIAL COVERAGE":
                    switch (field.split("\\.")[2]) {
                        case "CODE":
                            try {
                                switch (field.split("\\.")[0]) {
                                    case "HOSPICE":
                                        if (data.get(field).equals("hidden")) {
                                            assertFalse(programAndBenefitInfoPage.getFieldInsideGivenParentGivenSpecialCoverageDoubleGivenLabelText(
                                                    givenConsumerBox, field.split("\\.")[0], field.split("\\.")[2]
                                            ).isDisplayed(), field + " is displayed - FAIL!");
                                        } else if (data.get(field).equals("is displayed")) {
                                            assertTrue(programAndBenefitInfoPage.getFieldInsideGivenParentGivenSpecialCoverageDoubleGivenLabelText(
                                                    givenConsumerBox, field.split("\\.")[0], field.split("\\.")[2]
                                            ).isDisplayed(), field + " is not displayed - FAIL!");
                                            System.out.println(field + " is displayed - PASS!");
                                        } else {
                                            expVal = data.get(field);
                                            actVal = getElementsText(programAndBenefitInfoPage.getListOfCodesOfHospiceInsideGivenConsumerParentElement(givenConsumerBox))
                                                    .get(Integer.parseInt(field.split("\\.")[3]));
                                            System.out.println(field + " value is " + actVal);
                                        }
                                        break;
                                    case "WAIVER":
                                        if (data.get(field).equals("hidden")) {
                                            assertFalse(programAndBenefitInfoPage.getFieldInsideGivenParentGivenSpecialCoverageDoubleGivenLabelText(
                                                    givenConsumerBox, field.split("\\.")[0], field.split("\\.")[2]
                                            ).isDisplayed(), field + " is displayed - FAIL!");
                                        } else if (data.get(field).equals("is displayed")) {
                                            assertTrue(programAndBenefitInfoPage.getFieldInsideGivenParentGivenSpecialCoverageDoubleGivenLabelText(
                                                    givenConsumerBox, field.split("\\.")[0], field.split("\\.")[2]
                                            ).isDisplayed(), field + " is not displayed - FAIL!");
                                            System.out.println(field + " is displayed - PASS!");
                                        } else {
                                            expVal = data.get(field);
                                            actVal = getElementsText(programAndBenefitInfoPage.getListOfCodesOfWaiverInsideGivenConsumerParentElement(givenConsumerBox))
                                                    .get(Integer.parseInt(field.split("\\.")[3]));
//                                            actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenSpecialCoverageDoubleGivenLabelText(
//                                                    givenConsumerBox, field.split("\\.")[0], field.split("\\.")[2]
//                                            ).getText();
                                            System.out.println(field + " value is " + actVal);
                                        }
                                        break;
                                    case "FACILITY/PLACEMENT":
                                        if (data.get(field).equals("hidden")) {
                                            assertFalse(programAndBenefitInfoPage.getFieldInsideGivenParentGivenSpecialCoverageSingleGivenLabelText(
                                                    givenConsumerBox, field.split("\\.")[0], field.split("\\.")[2]
                                            ).isDisplayed(), field + " is displayed - FAIL!");
                                        } else if (data.get(field).equals("is displayed")) {
                                            assertTrue(programAndBenefitInfoPage.getFieldInsideGivenParentGivenSpecialCoverageSingleGivenLabelText(
                                                    givenConsumerBox, field.split("\\.")[0], field.split("\\.")[2]
                                            ).isDisplayed(), field + " is not displayed - FAIL!");
                                            System.out.println(field + " is displayed - PASS!");
                                        } else {
                                            expVal = data.get(field);
                                            actVal = getElementsText(programAndBenefitInfoPage.getListOfCodesOfFacilityPlacementInsideGivenConsumerParentElement(givenConsumerBox))
                                                    .get(Integer.parseInt(field.split("\\.")[3]));
//                                            actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenSpecialCoverageSingleGivenLabelText(
//                                                    givenConsumerBox, field.split("\\.")[0], field.split("\\.")[2]
//                                            ).getText();
                                            System.out.println(field + " value is " + actVal);
                                        }
                                        break;
                                    case "ADDED AID CATEGORY":
                                        expVal = data.get(field);
                                        actVal = getElementsText(programAndBenefitInfoPage.getListOfCodesOfAddedAidCategoryInsideGivenConsumerParentElement(givenConsumerBox))
                                                .get(Integer.parseInt(field.split("\\.")[3]));
                                        System.out.println(field + " value is " + actVal);
                                        break;
                                    case "OTHER ELIGIBILITY":
                                        expVal = data.get(field);
                                        actVal = getElementsText(programAndBenefitInfoPage.getListOfCodesOfOtherEligibilityInsideGivenConsumerParentElement(givenConsumerBox))
                                                .get(Integer.parseInt(field.split("\\.")[3]));
                                        System.out.println(field + " value is " + actVal);
                                        break;
                                }
                            } catch (Exception e) {
                                System.out.println(field + " is not displayed - PASS!");
                            }
                            break;
                        case "START DATE":
                            expVal = data.get(field).contains("/")
                                    ? data.get(field)
                                    : data.get(field).contains("selectedPlanStartDate")
                                    ? CRMEnrollmentUpdateStepDef.selectedPlanStartDate.get()
                                    : apiTestDataUtil.get().getStartDate(data.get(field));
                            switch (field.split("\\.")[0]) {
                                case "HOSPICE":
                                    actVal = getElementsText(programAndBenefitInfoPage.getListOfStartDatesOfHospiceInsideGivenConsumerParentElement(givenConsumerBox))
                                            .get(Integer.parseInt(field.split("\\.")[3]));
                                    System.out.println(field + " value is " + actVal);
                                    break;
                                case "WAIVER":
                                    actVal = getElementsText(programAndBenefitInfoPage.getListOfStartDatesOfWaiverInsideGivenConsumerParentElement(givenConsumerBox))
                                            .get(Integer.parseInt(field.split("\\.")[3]));
                                    System.out.println(field + " value is " + actVal);
//                                    actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenSpecialCoverageDoubleGivenLabelText(
//                                            givenConsumerBox, field.split("\\.")[0], field.split("\\.")[2]
//                                    ).getText();
                                    break;
                                case "FACILITY/PLACEMENT":
                                    actVal = getElementsText(programAndBenefitInfoPage.getListOfStartDatesOfFacilityPlacementInsideGivenConsumerParentElement(givenConsumerBox))
                                            .get(Integer.parseInt(field.split("\\.")[3]));
                                    System.out.println(field + " value is " + actVal);
//                                    actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenSpecialCoverageSingleGivenLabelText(
//                                            givenConsumerBox, field.split("\\.")[0], field.split("\\.")[2]
//                                    ).getText();
                                    break;
                                case "ADDED AID CATEGORY":
                                    actVal = getElementsText(programAndBenefitInfoPage.getListOfStartDatesOfAddedAidCategoryInsideGivenConsumerParentElement(givenConsumerBox))
                                            .get(Integer.parseInt(field.split("\\.")[3]));
                                    System.out.println(field + " value is " + actVal);
                                    break;
                                case "OTHER ELIGIBILITY":
                                    actVal = getElementsText(programAndBenefitInfoPage.getListOfStartDatesOtherEligibilityInsideGivenConsumerParentElement(givenConsumerBox))
                                            .get(Integer.parseInt(field.split("\\.")[3]));
                                    System.out.println(field + " value is " + actVal);
                                    break;
                            }
                            break;
                        case "REASON CODE":
                            switch (field.split("\\.")[0]) {
                                case "OTHER ELIGIBILITY":
                                    expVal = data.get(field);
                                    actVal = getElementsText(programAndBenefitInfoPage.getListOfReasonCodesOfOtherEligibilityInsideGivenConsumerParentElement(givenConsumerBox))
                                            .get(Integer.parseInt(field.split("\\.")[3]));
                                    System.out.println(field + " value is " + actVal);
                                    break;
                            }
                            break;
                        case "END DATE":
                            expVal = data.get(field).equals("-- --")
                                    ? "-- --"
                                    : data.get(field).equals("DayBeforeSelectedPlanStartDate")
                                    ? LocalDate.parse(CRMEnrollmentUpdateStepDef.selectedPlanStartDate.get(), dfUIver).minusDays(1).format(dfUIver)
                                    : apiTestDataUtil.get().getEndDate(data.get(field));
                            switch (field.split("\\.")[0]) {
                                case "HOSPICE":
                                    actVal = getElementsText(programAndBenefitInfoPage.getListOfEndDatesOfHospiceInsideGivenConsumerParentElement(givenConsumerBox))
                                            .get(Integer.parseInt(field.split("\\.")[3]));
                                    System.out.println(field + " value is " + actVal);
                                    break;
                                case "WAIVER":
                                    actVal = getElementsText(programAndBenefitInfoPage.getListOfEndDatesOfWaiverInsideGivenConsumerParentElement(givenConsumerBox))
                                            .get(Integer.parseInt(field.split("\\.")[3]));
                                    System.out.println(field + " value is " + actVal);
//                                    actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenSpecialCoverageDoubleGivenLabelText(
//                                            givenConsumerBox, field.split("\\.")[0], field.split("\\.")[2]
//                                    ).getText();
                                    break;
                                case "FACILITY/PLACEMENT":
                                    actVal = getElementsText(programAndBenefitInfoPage.getListOfEndDatesOfFacilityPlacementInsideGivenConsumerParentElement(givenConsumerBox))
                                            .get(Integer.parseInt(field.split("\\.")[3]));
                                    System.out.println(field + " value is " + actVal);
//                                    actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenSpecialCoverageSingleGivenLabelText(
//                                            givenConsumerBox, field.split("\\.")[0], field.split("\\.")[2]
//                                    ).getText();
                                    break;
                                case "ADDED AID CATEGORY":
                                    actVal = getElementsText(programAndBenefitInfoPage.getListOfEndDatesOfAddedAidCategoryInsideGivenConsumerParentElement(givenConsumerBox))
                                            .get(Integer.parseInt(field.split("\\.")[3]));
                                    System.out.println(field + " value is " + actVal);
                                    break;
                                case "OTHER ELIGIBILITY":
                                    actVal = getElementsText(programAndBenefitInfoPage.getListOfEndDatesOtherEligibilityInsideGivenConsumerParentElement(givenConsumerBox))
                                            .get(Integer.parseInt(field.split("\\.")[3]));
                                    System.out.println(field + " value is " + actVal);
                                    break;
                            }
                            break;
                        case "COUNTY CODE":
                            expVal = data.get(field);
                            switch (field.split("\\.")[0]) {
                                case "FACILITY/PLACEMENT":
                                    actVal = getElementsText(programAndBenefitInfoPage.getListOfCountyCodesOfFacilityPlacementInsideGivenConsumerParentElement(givenConsumerBox))
                                            .get(Integer.parseInt(field.split("\\.")[3]));
                                    System.out.println(field + " value is " + actVal);
                                    break;
                            }
                            break;
                        case "COUNTY":
                            expVal = data.get(field);
                            switch (field.split("\\.")[0]) {
                                case "WAIVER":
                                    actVal = getElementsText(programAndBenefitInfoPage.getListOfCountiesOfWaiverInsideGivenConsumerParentElement(givenConsumerBox))
                                            .get(Integer.parseInt(field.split("\\.")[3]));
                                    System.out.println(field + " value is " + actVal);
                                    break;
                            }
                            break;

                        case "INDICATOR":
                            expVal = data.get(field);
                            switch (field.split("\\.")[0]) {
                                case "HOSPICE":
                                    actVal = getElementsText(programAndBenefitInfoPage.getListOfCodesOfHospiceInsideGivenConsumerParentElement(givenConsumerBox))
                                            .get(Integer.parseInt(field.split("\\.")[3]));
                                    System.out.println(field + " value is " + actVal);
                                    break;
                            }
                            break;
                        case "HIDDEN":
                            try {
                                switch (field.split("\\.")[0]) {
                                    case "HOSPICE":
                                    case "WAIVER":
                                        assertFalse(programAndBenefitInfoPage.getSpecialCoverageDoubleInsideGivenParent(
                                                givenConsumerBox, field.split("\\.")[0]).isDisplayed(), field.split("\\.")[0] + " is displayed! - FAIL!");
                                        break;
                                    case "FACILITY/PLACEMENT":
                                    case "ADDED AID CATEGORY":
                                        assertFalse(programAndBenefitInfoPage.getSpecialCoverageSingleInsideGivenParent(
                                                givenConsumerBox, field.split("\\.")[0]).isDisplayed(), field.split("\\.")[0] + " is displayed! - FAIL!");
                                        break;
                                }
                            } catch (Exception e) {
                                System.out.println(field.split("\\.")[0] + " is NOT displayed! - PASS!");
                            }
                            break;
                    }
                    break;
                case "SEGMENT":
                    try {
                        if (data.get(field).equals("hidden")) {
                            assertFalse(programAndBenefitInfoPage.getSegmentInsideGivenParent(
                                    givenConsumerBox, field.split("\\.")[0]
                            ).isDisplayed(), field.split("\\.")[1] + " is displayed - FAIL!");
                        } else if (data.get(field).equals("is displayed")) {
                            assertTrue(programAndBenefitInfoPage.getSegmentInsideGivenParent(
                                    givenConsumerBox, field.split("\\.")[0]
                            ).isDisplayed(), field.split("\\.")[1] + " is not displayed - FAIL!");
                            System.out.println(field + " is displayed - PASS!");
                        } else {
                            expVal = data.get(field);
                            actVal = programAndBenefitInfoPage.getSegmentInsideGivenParent(
                                    givenConsumerBox, field.split("\\.")[0]
                            ).getText();
                            System.out.println(field + " value is " + actVal);
                        }
                    } catch (Exception e) {
                        System.out.println(field + " is not displayed - PASS!");
                    }
                    break;
                case "ENROLLMENT END REASON":
                    expVal = data.get(field).toUpperCase();
                    if (field.split("\\.")[0].equals("PRIOR ENROLLMENT DETAILS")) {
                        actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenPriorEnrollmentGivenLabelText(givenConsumerBox, field.split("\\.")[1]).getText().toUpperCase();
                    }
                    System.out.println(field + " value is " + actVal);
                    break;

                case "ELIGIBILITY END REASON":
                    expVal = data.get(field).toUpperCase();
                    if (field.split("\\.")[0].equals("PRIOR ELIGIBILITY DETAILS")) {
                        actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenPriorEligibilityGivenLabelText(givenConsumerBox, field.split("\\.")[1]).getText().toUpperCase();
                        break;
                    }
                case "ELIGIBILITY START DATE":
                    expVal = data.get(field).toUpperCase();
                    if (field.split("\\.")[0].equals("PRIOR ELIGIBILITY DETAILS")) {
                        actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenPriorEligibilityGivenLabelText(givenConsumerBox, field.split("\\.")[1]).getText().toUpperCase();
                        break;
                    }
                case "ELIGIBILITY END DATE":
                    expVal = data.get(field).toUpperCase();
                    if (field.split("\\.")[0].equals("PRIOR ELIGIBILITY DETAILS")) {
                        actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenPriorEligibilityGivenLabelText(givenConsumerBox, field.split("\\.")[1]).getText().toUpperCase();
                        break;
                    }
                case "CONSUMER POPULATION":
                    if (data.get(field).equals("HIDDEN")){
                        try {
                            assertFalse(programAndBenefitInfoPage.getFieldInsideGivenParentGivenPriorEligibilityGivenLabelText(givenConsumerBox, field.split("\\.")[1]).isDisplayed(), field.split("\\.")[0] + "."+ field.split("\\.")[1] + " is displayed! - FAIL!");
                        }catch (Exception e)
                        {
                            System.out.println(field.split("\\.")[0]+"."+field.split("\\.")[1] + " is NOT displayed! - PASS!");
                        }
                    }else{
                        if (field.split("\\.")[0].equals("PRIOR ELIGIBILITY DETAILS")) {
                            expVal = data.get(field).toUpperCase();
                            actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenPriorEligibilityGivenLabelText(givenConsumerBox, field.split("\\.")[1]).getText().toUpperCase();

                        } else {
                            expVal = data.get(field);
                            actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenEnrollmentGivenLabelText(
                                    givenConsumerBox, field.split("\\.")[0], field.split("\\.")[1]
                            ).getText();
                            System.out.println(field + " value is " + actVal);
                        }
                    }

                    break;
                case "ENROLLMENT START DATE":
                    expVal = apiTestDataUtil.get().getStartDate(data.get(field));
                    //actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenPriorEnrollmentGivenLabelText(givenConsumerBox, field.split("\\.")[1]).getText().toUpperCase();
                    if (field.split("\\.")[0].equals("PRIOR ENROLLMENT DETAILS")) {
                        expVal = data.get(field).toUpperCase();
                        actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenPriorEnrollmentGivenLabelText(givenConsumerBox, field.split("\\.")[1]).getText().toUpperCase();
                    }
                    System.out.println(field + " value is " + actVal);
                    break;
                case "ENROLLMENT END DATE":
                    if (data.get(field).equals("yesterday"))
                        expVal = apiTestDataUtil.get().getStartDate("yesterdayUIver");
                   // actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenPriorEnrollmentGivenLabelText(givenConsumerBox, field.split("\\.")[1]).getText().toUpperCase();
                    if (field.split("\\.")[0].equals("PRIOR ENROLLMENT DETAILS")) {
                        expVal = data.get(field).toUpperCase();
                        actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenPriorEnrollmentGivenLabelText(givenConsumerBox, field.split("\\.")[1]).getText().toUpperCase();
                    }
                    System.out.println(field + " value is " + actVal);
                    break;
                case "PCP NAME":
                    if(data.get(field).equals("Not Null")){
                        expVal = CRMSearchtProviderPart2UIStepDef.get().providerData.get().get("PCPName");
                        actVal = programAndBenefitInfoPage.getInfoBoxInsideGivenParentByEnrolmentName(givenConsumerBox, field.split("\\.")[0])
                                .findElement(By.xpath(".//div[@class = 'col-12 px-0 py-2']//div[5]//p[2]")).getText();
                        System.out.println(field + " value is " + actVal);
                    } else {
                        expVal = data.get(field);
                        actVal = programAndBenefitInfoPage.getInfoBoxInsideGivenParentByEnrolmentName(givenConsumerBox, field.split("\\.")[0])
                                .findElement(By.xpath(".//div[@class = 'col-12 px-0 py-2']//div[5]//p[2]")).getText().replace("\n", "");
                        System.out.println(field + " value is " + actVal);
                    }
                    break;
                case "PDP NAME":
                    if (data.get(field).equals("Not Null")) {
                        expVal = CRMSearchtProviderPart2UIStepDef.get().providerData.get().get("PDPName");
                        actVal = programAndBenefitInfoPage.getInfoBoxInsideGivenParentByEnrolmentName(givenConsumerBox, field.split("\\.")[0])
                                .findElement(By.xpath(".//div[@class = 'col-12 px-0 py-2']//div[6]//p[2]")).getText();
                        System.out.println(field + " value is " + actVal);
                    } else {
                        expVal = data.get(field);
                        actVal = programAndBenefitInfoPage.getInfoBoxInsideGivenParentByEnrolmentName(givenConsumerBox, field.split("\\.")[0])
                                .findElement(By.xpath(".//div[@class = 'col-12 px-0 py-2']//div[6]//p[2]")).getText().replace("\n", "");
                        System.out.println(field + " value is " + actVal);
                    }
                    break;
                default:
                    // this step applies for all fields validation
                    try {
                        if (data.get(field).equals("hidden")) {
                            assertFalse(programAndBenefitInfoPage.getFieldInsideGivenParentGivenEnrollmentGivenLabelText(
                                    givenConsumerBox, field.split("\\.")[0], field.split("\\.")[1]
                            ).isDisplayed(), field.split("\\.")[1] + " is displayed - FAIL!");
                        } else if (data.get(field).equals("is displayed")) {
                            assertTrue(programAndBenefitInfoPage.getFieldInsideGivenParentGivenEnrollmentGivenLabelText(
                                    givenConsumerBox, field.split("\\.")[0], field.split("\\.")[1]
                            ).isDisplayed(), field.split("\\.")[1] + " is not displayed - FAIL!");
                            System.out.println(field + " is displayed - PASS!");
                        } else {
                            expVal = data.get(field);
                            actVal = programAndBenefitInfoPage.getFieldInsideGivenParentGivenEnrollmentGivenLabelText(
                                    givenConsumerBox, field.split("\\.")[0], field.split("\\.")[1]
                            ).getText();
                            System.out.println(field + " value is " + actVal);
                        }

                    } catch (Exception e) {
                        System.out.println(field + " is not displayed - PASS!");
                    }
                    break;
            }
            assertEquals(actVal, expVal, field + " mismatch!");
        }
    }


    @When("I select {string} from Channel Dropdown")
    public void i_select_from_Channel_Dropdown(String channelOption) {
        waitFor(1);
        enrollmentUpdatePage.getChannelOption(channelOption).click();
    }

    @When("I select {string} from reason Dropdown")
    public void i_select_from_reason_Dropdown(String reasonOption) {
        waitFor(1);
        enrollmentUpdatePage.getreasonOption(reasonOption).click();
    }

    @When("I validate {string} from Channel Dropdown not displayed")
    public void i_validate_from_Channel_Dropdown_not_displayed(String channelOption) {
        waitFor(2);
        try {
            assertFalse(enrollmentUpdatePage.getChannelOption(channelOption).isDisplayed(), "The " + channelOption + " is displayed");
        }
        catch (Exception e) {
            System.out.println(channelOption + " is not displayed");
        }
    }

    @When("I click Channel Dropdown button to display options")
    public void i_click_Channel_Dropdown_button_to_display_options() {
        waitFor(1);
        enrollmentUpdatePage.channelDropdownButton.click();
    }


    @Then("I verify {string} is the displayed Channel Dropdown option")
    public void i_verify_is_the_displayed_Channel_Dropdown_option(String option) {
        waitFor(1);
        System.out.println("Displayed channel value is : " + enrollmentUpdatePage.channelDropdown.getAttribute("value"));
        switch (option.toLowerCase()) {
            case "channel":
                Assert.assertEquals("", enrollmentUpdatePage.channelDropdown.getAttribute("value"), "Channel Dropdown does not display expected option!");
                break;
            default:
                Assert.assertEquals(option, enrollmentUpdatePage.channelDropdown.getAttribute("value"), "Channel Dropdown does not display expected value!");
        }
    }



    @And("I verify details of consumer first name {string} and last name {string} on Selected Consumers Segment on Enrollment Update Page")
    public void I_verify_details_of_consumer_first_name_and_last_name_on_Selected_Consumers_Segment(String firstName, String lastName, Map<String, String> data) {
        String fullName = (firstName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerFirstName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(firstName).toString()) + " "
                + (lastName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerLastName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(lastName).toString());

        for (String field : data.keySet()) {
            String actVal = "1";
            String expVal = data.get(field);
            switch (field) {
                case "AGE/GENDER":
                    actVal = enrollmentUpdatePage.getSelectedConsumerAgeGenderOnEnrollmentUpdatePageAgeByFullName(fullName).getText();
                    break;
                case "POPULATION":
                    actVal = enrollmentUpdatePage.getSelectedConsumerPopulationOnEnrollmentUpdatePageAgeByFullName(fullName).getText();
                    break;
                case "RESIDENTIAL ADDRESS":
                    actVal = enrollmentUpdatePage.getSelectedConsumerResidentialAddressOnEnrollmentUpdatePageAgeByFullName(fullName).getText();
                    break;
                case "CURRENT PLAN":
                    actVal = enrollmentUpdatePage.getSelectedConsumerCurrentPlanOnEnrollmentUpdatePageAgeByFullName(fullName).getText();
                    break;
                case "CURRENT PROVIDER":
                    actVal = enrollmentUpdatePage.getSelectedConsumerCurrentProviderOnEnrollmentUpdatePageAgeByFullName(fullName).getText();
                    break;
                case "ELIGIBILITY DETAILS":
                    actVal = enrollmentUpdatePage.getSelectedConsumerEligibilityDetailsOnEnrollmentUpdatePageAgeByFullName(fullName).getText();
                    expVal = apiTestDataUtil.get().getStartDate(data.get(field).split(" - ")[0]) + " - " + apiTestDataUtil.get().getEndDate(data.get(field).split(" - ")[1]);
                    break;
                default:
                    System.out.println(field + " field does not exist!");
            }
            System.out.println(field + ": " + actVal);
            assertEquals(actVal, expVal, field + " mismatch!");
        }
    }


    @Then("I verify no other channels are displayed in channel dropdown")
    public void i_verify_no_option_is_available_for_csr() {
        waitFor(1);
        Assert.assertTrue(enrollmentUpdatePage.channelDropdownOptions.size() == 0, "Other channels available");
    }

    @Then("I Verify Reason Field Is not Required Or Not Displaying")
    public void i_VerifyReason_Field_Is_Note_Displaying() {
        Assert.assertFalse(isElementDisplayed(enrollmentUpdatePage.enrolmentUpdateReasonbttn), "Reason Field is displaying");
    }

    @Then("I will not be able to change the enrollment Start dates")
    public void i_will_not_be_able_to_change_the_enrollment_Start_dates() {
        try {
            enrollmentUpdatePage.EEStartDate.click();
        } catch (Exception WebDriverException) {
        }
    }

    @Then("verify I will be navigated to the “Program & Benefits Info” screen with no change to the consumer’s enrollment for DC after PlanChange")
    public void verify_I_will_be_navigated_to_the_Program_Benefits_Info_screen_with_no_change_to_the_consumer_s_enrollment_After_Plan_Change() {
        Assert.assertTrue(programAndBenefitInfoPage.futureEnrollmentEditBttn.isDisplayed());
    }

    @When("I click on Search PCP and PDP button on Plan Option")
    public void i_click_on_Search_PCP_PDP_Plan_Option() {
        waitFor(2);
        enrollmentUpdatePage.searchPCPPDP.click();
        waitFor(3);
    }
    @Then("I will see a red stop indicator on the consumer row")
    public void I_will_see_a_red_stop_indicator_on_the_consumer_row(){
        Assert.assertTrue(enrollmentUpdatePage.mmisindicatoricon.isDisplayed());
    }

    @Then("verify Hex color is red \"(.*)\" icon mmis stop indicator")
    public void verify_Hex_color_is_red_icon_mmis_stop_indicator(String color){
        verifyColorOfElement(enrollmentUpdatePage.mmisindicatoricon,color);
    }
    @When("I hover my mouse over the red icon in the program and benifit page")
    public void i_hover_my_mouse_over_the_red_icon_in_the_program_and_benifit_page(){
        hover(enrollmentUpdatePage.mmisindicatoricon);
    }

    @Then("I will see the following {string} text on mmis indicator")
    public void i_will_see_the_following_text_on_mmis_indicator(String tooltip) {
        // Write code here that turns the phrase above into concrete actions
        String title = enrollmentUpdatePage.mmistooltipmsg.getText();
        System.out.println(title);
        Assert.assertEquals(title, tooltip);
    }
}



