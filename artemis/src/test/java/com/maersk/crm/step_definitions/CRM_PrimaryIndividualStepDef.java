package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.*;
import com.maersk.dms.step_definitions.PublishDPBIEventsOutboundCorrespondenceChangesStepDefs;
import com.maersk.dms.steps.OutboundCorrespondenceRequestSteps;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.*;

public class CRM_PrimaryIndividualStepDef extends CRMUtilities implements ApiBase {

    CRMDemographicMemberPage crmDemographicMemberPage = new CRMDemographicMemberPage();
    CRMAddPrimaryIndividualPage crmAddPrimaryIndividualPage = new CRMAddPrimaryIndividualPage();
    CRMAddAuthorizedRepresentativePage addARpage = new CRMAddAuthorizedRepresentativePage();
    CRM_ContactRecordUIStepDef crmContactRecordUIStepDef = new CRM_ContactRecordUIStepDef();
    CRMDemographicContactInfoPage demographicContactInfoPage = new CRMDemographicContactInfoPage();
    CRMAddAuthorizedRepresentativePage AuthorizedRep = new CRMAddAuthorizedRepresentativePage();

    private ThreadLocal<String> firstName = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> middleName = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> lastName = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> dob = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> age = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> startDate = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> endDate = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> ssn = ThreadLocal.withInitial(String::new);
    public static ThreadLocal<String> fullName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Integer> iteration = new ThreadLocal<>();
    final ThreadLocal<Map<String, Object>> randomdata = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    //    List<WebElement> start = new ArrayList<>();
//    List<WebElement> end = new ArrayList<>();
    final ThreadLocal<List<String>> ActiveStartDate = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> InactiveEndDate = ThreadLocal.withInitial(ArrayList::new);
    //    private List<String> firstrowData = new ArrayList<String>();
    private ThreadLocal<String> consumerIdPrimaryIndividual = ThreadLocal.withInitial(String::new);
    PublishDPBIEventsOutboundCorrespondenceChangesStepDefs corrSteps = new PublishDPBIEventsOutboundCorrespondenceChangesStepDefs();
    CRMAddContactInfoPage crmAddContactInfoPage = new CRMAddContactInfoPage();
    private final ThreadLocal<ApiTestDataUtil> apitdu = ThreadLocal.withInitial(ApiTestDataUtil::getApiTestDataUtilThreadLocal);

    @Then("I verify primary individual section displayed")
    public void i_verify_primary_individual_section_displayed() {
        waitFor(3);
        assertTrue(crmDemographicMemberPage.piHeader.isDisplayed(), "PI header is not displayed");
        assertTrue(crmDemographicMemberPage.consumerIdHeader.isDisplayed(), "Consumer Id column is not displayed");
        assertTrue(crmDemographicMemberPage.fullNameHeader.isDisplayed(), "Full name column is not displayed");
        assertTrue(crmDemographicMemberPage.dobHeader.isDisplayed(), "Date of birth column is not displayed");
        assertTrue(crmDemographicMemberPage.ssnHeader.isDisplayed(), "SSN column is not displayed");
        //assertTrue(crmDemographicMemberPage.languageHeader.isDisplayed(), "Language column is not displayed"); TODO: not displayed anymore a defect? version update?
        assertTrue(crmDemographicMemberPage.statusHeader.isDisplayed(), "Status column is not displayed");
    }

    @Then("I verify Authorized Representative section displayed")
    public void i_verify_Authorized_Representative_section_displayed() {
        waitFor(3);
        assertTrue(demographicContactInfoPage.authorizedRepLabel.isDisplayed(), "AR header is not displayed");
    }

    //Vinuta 05/13 - Refactored to scroll before hover
    @When("I hover over status of the first PI")
    public void i_hover_over_status_of_the_first_PI() {
        scrollUpRobot();
        hover(crmDemographicMemberPage.firstPIStatus);
    }

    @Then("I verify that the start and end dates are displayed for PI")
    public void i_verify_that_the_start_and_end_dates_are_displayed_for_PI() {
        waitFor(1);
        Assert.assertTrue(getHoverText().containsKey("START DATE"));
        waitFor(1);
        Assert.assertTrue(getHoverText().containsKey("END DATE"));
    }

    @Then("I should see the Add button displayed for primary individual")
    public void i_should_see_the_Add_button_displayed_for_primary_individual() {
        Assert.assertTrue(crmDemographicMemberPage.addPIButton.isDisplayed());
    }

    @When("I click on the Add button for primary individual")
    public void i_click_on_the_Add_button_for_primary_individual() {
        waitFor(2);
        click(crmDemographicMemberPage.addPIButton);
        waitForPageToLoad(5);
        waitFor(3);
    }

    @When("I verify that I am in the Add Primary Individual Page")
    public void i_verify_that_I_am_in_the_Add_Primary_Individual_Page() {
        waitFor(2);
        Assert.assertTrue(crmAddPrimaryIndividualPage.primaryIndividualLabel.isDisplayed());
    }

    @Then("I verify the fields displayed on Add Primary Individual Page")
    public void i_verify_the_fields_displayed_on_Add_Primary_Individual_Page() {
        waitFor(1);
        Assert.assertTrue(crmAddPrimaryIndividualPage.piAge.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piDOBField.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piEndDate.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piFirstNameField.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piGender.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piLanguage.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piLanguage.getText().equals("English"));
        Assert.assertTrue(crmAddPrimaryIndividualPage.piLastNameField.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piSSNField.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piMiddleName.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piStartDate.isDisplayed());
    }

    @Then("I verify pregnancy start and end dates are displayed on Add Primary Individual Page")
    public void i_verify_pregnancy_start_and_end_dates_are_displayed_on_Add_Primary_Individual_Page() {
        waitFor(1);
        Assert.assertTrue(crmAddPrimaryIndividualPage.piAge.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piDOBField.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piEndDate.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piFirstNameField.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piGender.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piLastNameField.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piSSNField.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piStartDate.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.pregnancyEndDate.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.pregnancyStartDate.isDisplayed());
    }

    @Then("Verify {string} dropDown has options")
    public void verify_dropDown_has_options(String dropdown, List<String> options) {
        for (String option : options) {
            selectOptionFromMultiSelectDropDown(crmAddPrimaryIndividualPage.memberWardDropdown, option);
        }
    }

    @Then("I verify the fields displayed on Add Authorized Representative Page")
    public void i_verify_the_fields_displayed_on_Add_Authorized_Representative_Page() {
        waitFor(1);
        Assert.assertTrue(crmAddPrimaryIndividualPage.piAge.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piDOBField.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piEndDate.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piFirstNameField.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piGender.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piLastNameField.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piSSNField.isDisplayed());
        Assert.assertTrue(crmAddPrimaryIndividualPage.piStartDate.isDisplayed());
    }

    @Then("I see error message populated below each field on Add Primary Individual Page")
    public void i_see_error_message_populated_below_each_field_on_Add_Primary_Individual_Page() {
        assertTrue(crmAddPrimaryIndividualPage.piFNBlankError.isDisplayed(), "First name blank error is not displayed");
        assertTrue(crmAddPrimaryIndividualPage.piLNBlankError.isDisplayed(), "Last name blank error is not displayed");
        assertTrue(crmAddPrimaryIndividualPage.piDOBBlankError.isDisplayed(), "DOB blank error is not displayed");
        assertTrue(crmAddPrimaryIndividualPage.piGenderBlankError.isDisplayed(), "Gender blank error is not displayed");
        assertTrue(crmAddPrimaryIndividualPage.piStartDateBlankError.isDisplayed(), "Start date blank error is not displayed");
        assertTrue(crmAddPrimaryIndividualPage.piSSNBlankError.isDisplayed(), "SSN blank error is not displayed");
//        assertTrue(crmAddPrimaryIndividualPage.piLanguage.getText().equalsIgnoreCase("English"), "Preferred Language is not English by default");
    }

    @Then("I see valid field error message populated below each field on Add Primary Individual Page")
    public void i_see_valid_field_error_message_populated_below_each_field_on_Add_Primary_Individual_Page() {
        switch (iteration.get()) {
            case 1:
                assertTrue(crmAddPrimaryIndividualPage.piFirstNameField.getText().isEmpty());
                assertTrue(crmAddPrimaryIndividualPage.piMiddleName.getText().isEmpty());
                assertTrue(crmAddPrimaryIndividualPage.piLastNameField.getText().isEmpty());
                assertTrue(crmAddPrimaryIndividualPage.piDOBField.getText().isEmpty());
                assertTrue(crmAddPrimaryIndividualPage.piStartDate.getText().isEmpty());
                assertTrue(crmAddPrimaryIndividualPage.piEndDate.getText().isEmpty());
                assertTrue(crmAddPrimaryIndividualPage.piSSNField.getText().isEmpty());
                System.out.println("case 1 done");
                break;
            case 2:
                assertTrue(crmAddPrimaryIndividualPage.piFirstNameField.getAttribute("value").length() == 15);
                assertTrue(crmAddPrimaryIndividualPage.piMiddleName.getAttribute("value").length() == 1);
                assertTrue(crmAddPrimaryIndividualPage.piLastNameField.getAttribute("value").length() == 15);
                assertTrue(crmAddPrimaryIndividualPage.piDOBGenericError.getText().equalsIgnoreCase("Date cannot be in the future"));
                assertTrue(crmAddPrimaryIndividualPage.piEndDateGenericError.getText().equalsIgnoreCase("end date cannot be lesser than start date"));
                assertTrue(hasMinLengthErrorMessage(crmAddPrimaryIndividualPage.piSSNGenericError, "SSN", 9));
                System.out.println("case 2 done");
                break;
            case 3:
                assertTrue(crmAddPrimaryIndividualPage.piDOBGenericError.getText().equalsIgnoreCase("The date entered does not exist. Please enter a valid date."));
                assertTrue(crmAddPrimaryIndividualPage.piStartDateGenericError.getText().equalsIgnoreCase("The date entered does not exist. Please enter a valid date."));
                assertTrue(crmAddPrimaryIndividualPage.piEndDateGenericError.getText().equalsIgnoreCase("The date entered does not exist. Please enter a valid date."));
                assertTrue(hasMinLengthErrorMessage(crmAddPrimaryIndividualPage.piSSNGenericError, "SSN", 9));
                System.out.println("case 3 done");
                break;
            case 4:
                assertTrue(hasInvalidDateErrorMessage(crmAddPrimaryIndividualPage.piDOBGenericError));
                assertTrue(hasInvalidDateErrorMessage(crmAddPrimaryIndividualPage.piStartDateGenericError));
                assertTrue(hasInvalidDateErrorMessage(crmAddPrimaryIndividualPage.piEndDateGenericError));
                System.out.println("case 4 done");
                break;
            case 5:
                assertTrue(crmAddPrimaryIndividualPage.piDOBGenericError.getText().equalsIgnoreCase("The date entered does not exist. Please enter a valid date."));
                assertTrue(crmAddPrimaryIndividualPage.piStartDateGenericError.getText().equalsIgnoreCase("The date entered does not exist. Please enter a valid date."));
                assertTrue(crmAddPrimaryIndividualPage.piEndDateGenericError.getText().equalsIgnoreCase("The date entered does not exist. Please enter a valid date."));
                System.out.println("case 5 done");
                break;
            case 6:
                assertTrue(Integer.parseInt(crmAddPrimaryIndividualPage.piAge.getAttribute("value")) == calculateAge(dob.get()));
                assertTrue(crmAddPrimaryIndividualPage.piEndDateGenericError.getText().equalsIgnoreCase("End date cannot be equal to start date"));
                System.out.println("case 6 done");
                break;
        }
    }

    @When("I provide invalid data for {int},{string},{string},{string},{string},{string},{string},{string},{string}")
    public void i_provide_invalid_data_for(int itr, String firstName, String middleName, String lastName, String dob, String age, String startDate, String endDate, String ssn) {
        synchronized (iteration) {
            iteration.set(itr);
        }
        firstName = firstName;
        middleName = middleName;
        lastName = lastName;
        dob = dob;
        age = age;
        startDate = startDate;
        endDate = endDate;
        ssn = ssn;
        fullName.set(firstName + " " + this.lastName.get());

        clearAndFillText(crmAddPrimaryIndividualPage.piFirstNameField, firstName);
        clearAndFillText(crmAddPrimaryIndividualPage.piLastNameField, lastName);
        clearAndFillText(crmAddPrimaryIndividualPage.piMiddleName, middleName);
        waitForVisibility(addARpage.unmaskDOB, 5);
        click(addARpage.unmaskDOB);
        clearAndFillText(crmAddPrimaryIndividualPage.piDOBField, dob);
        clearAndFillText(crmAddPrimaryIndividualPage.piStartDate, startDate);
        clearAndFillText(crmAddPrimaryIndividualPage.piEndDate, endDate);
        click(addARpage.unmaskSSN);
        clearAndFillText(crmAddPrimaryIndividualPage.piSSNField, ssn);
        //selectDropDown(crmAddPrimaryIndividualPage.piGender,"Female");
        crmAddPrimaryIndividualPage.saveButton.click();
    }

    @And("I populate all mandatory details of primary individual")
    public void i_populate_all_mandatory_details_of_primary_individual() {
        firstName.set(randomdata.get().get("name").toString());
        lastName.set(randomdata.get().get("surname").toString());
        fullName.set(firstName.get() + " " + lastName.get());
        scrollUpRobot();
        scrollToTop();
        waitFor(3);
        clearAndFillText(crmAddPrimaryIndividualPage.piFirstNameField, firstName.get());
        clearAndFillText(crmAddPrimaryIndividualPage.piLastNameField, lastName.get());
        clearAndFillText(crmAddPrimaryIndividualPage.piDOBField, "11/11/2000");
        crmAddPrimaryIndividualPage.piGender.click();
        waitFor(1);
        scrollToElement(crmAddPrimaryIndividualPage.genderDropdownLastItem);
        hover(crmAddPrimaryIndividualPage.genderDropdownLastItem);
        crmAddPrimaryIndividualPage.genderDropdownLastItem.click();
        waitFor(7);
        clearAndFillText(crmAddPrimaryIndividualPage.piStartDate, getCurrentDate());
        clearAndFillText(crmAddPrimaryIndividualPage.piSSNField, getRandomNumber(9));

    }

    @And("I click on Save button on add primary individual page")
    public void i_click_on_Save_button_on_add_primary_individual_page() {
        hover(crmAddPrimaryIndividualPage.saveButton);
        click(crmAddPrimaryIndividualPage.saveButton);
        waitFor(3);
    }

    @And("I click on Cancel button on add primary individual page")
    public void i_click_on_Cancel_button_on_add_primary_individual_page() {
        crmAddPrimaryIndividualPage.cancelButton.click();
    }

    @Then("New primary individual should be added to the PI list")
    public void new_primary_individual_should_be_added_to_the_PI_list() {
        boolean found = false;
        int i = 2;
        boolean nextButton = false;
        List<WebElement> pages = Driver.getDriver().findElements(By.xpath("(//ul[@class='pagination'])[1]/li/a"));
        WebElement next = Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[1]/li/a/span[text()='arrow_forward']"));
        while (!found) {
            List<WebElement> piName = Driver.getDriver().findElements(By.xpath("//td[3]"));
            for (WebElement name : piName) {
                //System.out.println(name.getText());
                if (fullName.get().equalsIgnoreCase(name.getText())) {
                    found = true;
                }
                break;
            }
            if (found) break;
            if (!found) {
                Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[1]/li/a[text()='" + i + "']")).click();
                i++;
            } else break;
        }
        assertTrue(found, "PI added is not found");
    }

    @And("I give start date and end date in the past and save")
    public void i_give_start_date_and_end_date_in_the_past_and_save() {
        clearAndFillText(crmAddPrimaryIndividualPage.piStartDate, getPriorDate(5));
        clearAndFillText(crmAddPrimaryIndividualPage.piEndDate, getPriorDate(3));
        crmAddPrimaryIndividualPage.saveButton.click();
    }

    @And("I give start date in the past and end date as current date and save")
    public void i_give_start_date_in_the_past_and_end_date_as_current_date_and_save() {
        clearAndFillText(crmAddPrimaryIndividualPage.piStartDate, getPriorDate(5));
        clearAndFillText(crmAddPrimaryIndividualPage.piEndDate, getCurrentDate());
        crmAddPrimaryIndividualPage.saveButton.click();
    }

    @And("I give start date as current date and end date in future and save")
    public void I_give_start_date_as_current_date_and_end_date_in_future_and_save() {
        clearAndFillText(crmAddPrimaryIndividualPage.piStartDate, getPriorDate(1));
        waitFor(2);
        clearAndFillText(crmAddPrimaryIndividualPage.piEndDate, getGreaterDate(2));
        crmAddPrimaryIndividualPage.saveButton.click();
    }

    @And("I give start date and end date in future and save")
    public void I_give_start_date_and_end_date_in_future_and_save() {
        clearAndFillText(crmAddPrimaryIndividualPage.piStartDate, getGreaterDate(1));
        clearAndFillText(crmAddPrimaryIndividualPage.piEndDate, getGreaterDate(2));
        crmAddPrimaryIndividualPage.saveButton.click();
    }

    @And("I give start date in past and save")
    public void I_give_start_date_in_past_and_save() {
        clearAndFillText(crmAddPrimaryIndividualPage.piStartDate, getPriorDate(1));
        crmAddPrimaryIndividualPage.saveButton.click();
    }

    @And("I give start date in future and save")
    public void I_give_start_date_in_future_and_save() {
        clearAndFillText(crmAddPrimaryIndividualPage.piStartDate, getGreaterDate(1));
        crmAddPrimaryIndividualPage.saveButton.click();
    }

    @And("I verify PI age is calculated on DOB")
    public void I_verify_PI_age_is_calculated_on_DOB() {
        assertTrue(crmAddPrimaryIndividualPage.piAge.getAttribute("value").equals("18"), "calulated age is wrong");
    }

    @Then("I verify status of primary individual as {string}")
    public void verifyPIStatus(String status) {
        WebElement emailStatus = Driver.getDriver().findElement(By.xpath("//td[.='" + fullName.get() + "']/following-sibling::td[5]"));
        Assert.assertEquals(emailStatus.getText(), status);
    }

    @Then("Age is displayed by calculating based on DOB on PI List Page")
    public void Age_is_displayed_by_calculating_based_on_DOB_on_PI_List_Page() {
        WebElement ageOfPi = Driver.getDriver().findElement(By.xpath("//td[contains(text(),'" + fullName.get() + "')]/parent::tr/td[contains(text(),'18')]"));
        assertTrue(ageOfPi.isDisplayed(), "Age is not displayed correctly");
    }

    @Then("Preferred language has value as {string}")
    public void verifyPreferredLanguage(String language) {
        WebElement lang = Driver.getDriver().findElement(By.xpath("//td[contains(text(),'" + fullName.get() + "')]/parent::tr/td[7]"));
        Assert.assertEquals(lang.getText(), language);
    }

    @And("I add three pi with active and inactive statuses")
    public void I_add_three_pi_with_active_and_inactive_statuses() {
        i_populate_all_mandatory_details_of_primary_individual();
        i_click_on_Save_button_on_add_primary_individual_page();
        crmContactRecordUIStepDef.i_click_on_continue_button_on_warning_message();
        i_click_on_the_Add_button_for_primary_individual();
        i_populate_all_mandatory_details_of_primary_individual();
        i_click_on_Save_button_on_add_primary_individual_page();
        crmContactRecordUIStepDef.i_click_on_continue_button_on_warning_message();
        i_click_on_the_Add_button_for_primary_individual();
        i_populate_all_mandatory_details_of_primary_individual();
        i_give_start_date_and_end_date_in_the_past_and_save();
        crmContactRecordUIStepDef.i_click_on_continue_button_on_warning_message();
    }

    @Then("I verify that two pis are display at first glance")
    public void I_verify_that_two_pis_are_display_at_first_glance() {
        assertTrue(crmDemographicMemberPage.listOfPI.size() == 2);
    }

    @Then("Active primary individuals are displayed on top followed by inactive")
    public void active_pi_followed_by_inactive() throws StaleElementReferenceException {
        int i = 2;
        String previous = "ACTIVE";
        List<WebElement> pages = Driver.getDriver().findElements(By.xpath("(//ul[@class='pagination'])[1]/li/a"));
        WebElement next = Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[1]/li/a/span[text()='arrow_forward']"));

        try {
            while (next.isDisplayed()) {

                for (WebElement pi : crmDemographicMemberPage.listOfPI) {
                    List<WebElement> listOfStatus = pi.findElements(By.xpath("//td[8]"));
                    for (WebElement e : listOfStatus) {
                        String status = e.getText();
                        //System.out.println(previous + "    " + status);
                        assertTrue(previous.equals("ACTIVE") && status.equals("ACTIVE") || previous.equals("ACTIVE") && status.equals("INACTIVE") || previous.equals("INACTIVE") && status.equals("INACTIVE"));
                        assertFalse(previous.equals("INACTIVE") && status.equals("ACTIVE"));
                        previous = status;
                    }
                }
                Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[1]/li/a[text()='" + i + "']")).click();
                i++;
            }
        } catch (Exception e) {
        }
    }

    public void getPrimaryIndividualDates() {
        WebElement next = Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[1]/li/a/span[text()='arrow_forward']"));
        int i = 2;
        try {
            while (next.isDisplayed()) {
                for (WebElement pi : crmDemographicMemberPage.listOfPI) {
                    List<WebElement> listOfStatus = pi.findElements(By.xpath("//td[8]"));
                    for (WebElement e : listOfStatus) {
                        hover(e);
                        String startDate = Driver.getDriver().findElement(By.xpath("(//div[@role='tooltip']/div/div/div)[1]/h6")).getText();
                        String endDate = Driver.getDriver().findElement(By.xpath("(//div[@role='tooltip']/div/div/div)[2]/h6")).getText();
                        if (e.getText().equals("ACTIVE"))
                            ActiveStartDate.get().add(startDate);
                        if (e.getText().equals("INACTIVE"))
                            InactiveEndDate.get().add(endDate);
                    }
                }
                Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[1]/li/a[text()='" + i + "']")).click();
                i++;
            }
        } catch (Exception e) {
        }
    }

    @Then("Active primary individuals are sorted by start date in ascending order")
    public void active_pi_sorted_start_date_ascending() {
        getPrimaryIndividualDates();
        assertTrue(ascendingOrderDatesString(ActiveStartDate.get()));
    }

    @And("Inactive primary individuals are sorted by end date in descending order")
    public void inactive_pi_end_date_descending() {
        //getPrimaryIndividualDates();
        assertTrue(descendingOrderDatesString(InactiveEndDate.get()));
    }

    List<String> getFirstRowData() {
        List<String> cellData = new ArrayList<String>();
        for (int temp = 0; temp < 8; temp++) {
            cellData.add(Driver.getDriver().findElements(crmAddPrimaryIndividualPage.PItablecells).get(temp).getText());
        }
        System.out.println(cellData);
        return cellData;
    }

    @When("I click on existing Primary Individual Record")
    public void i_click_on_existing_Primary_Individual_Record() {
        scrollUpUsingActions(3);
        waitFor(3);
//        List<String> rowdata = new ArrayList<String>();
//        firstrowData = getFirstRowData();
        consumerIdPrimaryIndividual.set(Driver.getDriver().findElements(crmAddPrimaryIndividualPage.PItableRows).get(0).findElement(By.xpath("/td[2]")).getText());
        Driver.getDriver().findElements(crmAddPrimaryIndividualPage.PItableRows).get(0).click();
        waitFor(5);
    }

    @And("I update primary individual's middle name")
    public void i_update_authorized_representatives_middle_name() {
        scrollUpUsingActions(2);
        clearAndFillText(crmAddPrimaryIndividualPage.piMiddleName, "Z");
        scrollDownUsingActions(3);

    }


    @Then("I will see information split into {int} sections")
    public void iWillSeeInformationSplitIntoSections(int arg0) {
        assertTrue(corrSteps.elementIsDisplayed(crmAddPrimaryIndividualPage.roleInformationHeader));
        assertTrue(corrSteps.elementIsDisplayed(crmAddPrimaryIndividualPage.profileInformationHeader));
    }


    @And("I verify consumer role {string}")
    public void iVerifyConsumerRole(String arg0) {
        if (arg0.equalsIgnoreCase("Primary Individual")) {
            assertTrue(corrSteps.elementIsDisplayed(crmAddPrimaryIndividualPage.consumerRoleFieldName));
            assertEquals(crmAddPrimaryIndividualPage.consumerRoleField.getAttribute("value"), arg0);
        } else if (arg0.equalsIgnoreCase("Case Member")) {
            assertTrue(corrSteps.elementIsDisplayed(crmAddPrimaryIndividualPage.consumerRoleFieldName));
            assertEquals(crmAddPrimaryIndividualPage.consumerRoleField.getAttribute("value"), arg0);
        } else if (arg0.equalsIgnoreCase("Authorized Representative")) {
            assertTrue(corrSteps.elementIsDisplayed(crmAddPrimaryIndividualPage.consumerRoleFieldName));
            assertTrue(crmAddPrimaryIndividualPage.AuthorizedRepresentativeDropdownValue.isDisplayed(), "AuthorizedRepresentativeDropdownValue no showing in UI");
        }
    }

    @When("I select Gender as {string} to verify pregnancy checkbox is displayed")
    public void iSelectGenderAsToVerifyPregnancyCheckboxIsDisplayed(String gender) {
        if (gender.equalsIgnoreCase("female")) {
            jsClick(crmAddPrimaryIndividualPage.profileInformationHeader);
            corrSteps.clickIfElementIsDisplayed(crmAddPrimaryIndividualPage.piGender);
            Driver.getDriver().findElement(By.xpath("//li[text()='" + gender + "']")).click();
            assertTrue(corrSteps.elementIsDisplayed(crmAddPrimaryIndividualPage.pregnancyIndCheckBox));
        }
    }

    @And("I click pregnancy checkbox")
    public void iClickPregnancyCheckbox() {
        assertTrue(corrSteps.elementIsDisplayed(crmAddPrimaryIndividualPage.pregnancyIndCheckBox));
        corrSteps.clickIfElementIsDisplayed(crmAddPrimaryIndividualPage.pregnancyIndCheckBox);
    }

    @Then("I verify Mandatory fields")
    public void iVerifyMandatoryFields(List<String> options) {
        WebElement el;
        for (String option : options) {
            switch (option) {
                case "relationShip":
                    el = crmAddPrimaryIndividualPage.labelRelationship;
                    break;
                case "Start Date":
                    el = crmAddPrimaryIndividualPage.lableStartDay;
                    break;
                case "Receive Correspondence":
                    el = crmAddPrimaryIndividualPage.labelReceiveCorrespondenceText;
                    break;
                case "First Name":
                    el = crmAddPrimaryIndividualPage.piFirstNameFieldName;
                    break;
                case "Last Name":
                    el = crmAddPrimaryIndividualPage.piLastNameFieldName;
                    break;
                case "DOB":
                    el = crmAddPrimaryIndividualPage.piDOBFieldName;
                    break;
                case "SSN":
                    el = crmAddPrimaryIndividualPage.piSSNFieldName;
                    break;
                case "Gender":
                    el = crmAddPrimaryIndividualPage.piGenderFieldName;
                    break;
                case "Spoken Language":
                    el = crmAddPrimaryIndividualPage.spokenLanguageFieldName;
                    break;
                case "Written Language":
                    el = crmAddPrimaryIndividualPage.writtenLanguageFieldName;
                    break;
                case "Pregnancy Due Date":
                    el = crmAddPrimaryIndividualPage.pregnancyDueDate;
                    break;
                case "Access Type":
                    el = Driver.getDriver().findElement(By.xpath("//label[@for='accessType']"));
                    break;
                case "Notification Reason":
                    el = Driver.getDriver().findElement(By.xpath("//label[text()='REASON']"));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + option);
            }
            System.out.println(option);
            System.out.println("text of " + option + "======================" + el.getText());
            Assert.assertTrue(markedMandatory(el));
        }

    }


    @Then("I verify {string} is required and cannot be left blank")
    public void iVerifyIsRequiredAndCannotBeLeftBlank(String arg0) {
        assertEquals(crmAddContactInfoPage.mandatoryFields(arg0).getText(), arg0.replace("  ", "") + " is required and cannot be left blank",
                "Mandatory field is Fail");
    }

    @And("I fill DOD with todays date")
    public void iFillDODWithTodaysDate() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date Date = new Date();
        String today = dateFormat.format(Date);
        clearAndFillText(crmAddPrimaryIndividualPage.piDOD, today);

    }

    @And("I fill DOD with past date")
    public void iFillDODWithPastDate() {
        String today = getPastDayWithCurrentdate(1);
        clearAndFillText(crmAddPrimaryIndividualPage.piDOD, today);

    }

    @Then("I verify the dropdown values for {string}")
    public void iVerifyTheDropdownValuesFor(String arg0, List<String> options) {
        WebElement el;
        switch (arg0) {
            case "Ethnicity":
                corrSteps.clickIfElementIsDisplayed(crmAddPrimaryIndividualPage.ethnicity);
                waitFor(1);
                break;
            case "relationShip":
//                corrSteps.clickIfElementIsDisplayed(Driver.getDriver().findElement(By.xpath("//div[@id='mui-component-select-relationShip']")));
                Driver.getDriver().findElement(By.xpath("//div[@id='mui-component-select-relationShip']")).click();
                waitFor(1);
                break;
            case "Gender":
                corrSteps.clickIfElementIsDisplayed(crmAddPrimaryIndividualPage.piGender);
                waitFor(1);
                break;
            case "Race":
                corrSteps.clickIfElementIsDisplayed(crmAddPrimaryIndividualPage.race);
                waitFor(1);
                break;
            case "Citizenship":
                corrSteps.clickIfElementIsDisplayed(crmAddPrimaryIndividualPage.citizenship);
                waitFor(1);
                break;
            case "Residency":
                corrSteps.clickIfElementIsDisplayed(crmAddPrimaryIndividualPage.residency);
                waitFor(1);
                break;
            case "receiveCorrespondence":
                corrSteps.clickIfElementIsDisplayed(crmAddPrimaryIndividualPage.receiveCorrespondence);
                waitFor(1);
                break;
            case "spokenLanguage":
                corrSteps.clickIfElementIsDisplayed(crmAddPrimaryIndividualPage.spokenLanguageField);
                waitFor(1);
                break;
            case "writtenLanguage":
                corrSteps.clickIfElementIsDisplayed(crmAddPrimaryIndividualPage.writtenLanguageField);
                waitFor(1);
                break;
            case "Correspondence":
                corrSteps.clickIfElementIsDisplayed(crmAddPrimaryIndividualPage.corrPreference);
                waitFor(1);
                break;
            case "DISPOSITION":
                Driver.getDriver().findElement(By.xpath("//div[@id='mui-component-select-contactRecordStatusType']")).click();
                waitFor(1);
                break;
            case "Access Type":
                crmDemographicMemberPage.arAccessType.click();
                waitFor(1);
                break;
            case "Reason For Edit":
                Driver.getDriver().findElement(By.xpath("//div[@id='mui-component-select-contactReasonEditType']")).click();
                waitFor(1);
                break;
            case "Notification Reason":
                Driver.getDriver().findElement(By.xpath("//div[@id='mui-component-select-notificationStatusReason']")).click();
                waitFor(1);
                break;
        }
        for (String option : options) {
            el = Driver.getDriver().findElement(By.xpath("//li[text()='" + option + "']"));
            waitFor(1);
            assertTrue(corrSteps.elementIsDisplayed(el));
        }
        if (arg0.equalsIgnoreCase("Notification Reason")) {
            OutboundCorrespondenceRequestSteps.notificationReason.set(Driver.getDriver().findElement(By.xpath("//li[text()='" + options.get(0) + "']")).getText());
        }
        corrSteps.clickIfElementIsDisplayed(Driver.getDriver().findElement(By.xpath("//li[text()='" + options.get(0) + "']")));
    }

    @And("I click on first consumerID for Primary Individual")
    public void iClickOnFirstConsumerIDForPrimaryIndividual() {
        scrollUp();
        waitFor(2);
        crmAddPrimaryIndividualPage.firstPIConsumer.click();
    }

    @And("I fill Start Date with todays date")
    public void iFillStartDateWithTodaysDate() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date Date = new Date();
        String today = dateFormat.format(Date);
        clearAndFillText(crmAddPrimaryIndividualPage.piStartDate, today);

    }


    @Then("I see the following checkboxes with the PI current opt-in and out selections")
    public void iSeeTheFollowingCheckboxesWithThePICurrentOptInAndOutSelections() {
        List<WebElement> checkboxes = Driver.getDriver().findElements(By.xpath("//h5[text()='COMMUNICATION OPT-IN INFORMATION']/following::div[2]/label/span/span/input"));
        for (WebElement e : checkboxes) {
            assertTrue(isElementDisplayed(e));
            assertTrue(e.getAttribute("value").equalsIgnoreCase("email") || e.getAttribute("value").equalsIgnoreCase("fax")
                    || e.getAttribute("value").equalsIgnoreCase("mail") || e.getAttribute("value").equalsIgnoreCase("phone")
                    || e.getAttribute("value").equalsIgnoreCase("text"));
        }
    }


    @Then("I am able to update each checkbox")
    public void iAmAbleToUpdateEachCheckbox() {
        List<WebElement> checkboxes = Driver.getDriver().findElements(By.xpath("//h5[text()='COMMUNICATION OPT-IN INFORMATION']/following::div[2]/label/span/span/input"));
        for (int i = 0; i <= checkboxes.size() - 1; i++) {
            assertTrue(isElementDisplayed(checkboxes.get(i)));
            String check = checkboxes.get(i).getAttribute("checked");
            if (check == null) {
                checkboxes.get(i).click();
                List<WebElement> updatedCheckbox = Driver.getDriver().findElements(By.xpath("//h5[text()='COMMUNICATION OPT-IN INFORMATION']/following::div[2]/label/span/span/input"));
                assertTrue(updatedCheckbox.get(i).getAttribute("checked").equals("true"));
            } else {
                checkboxes.get(i).click();
                List<WebElement> updatedCheckbox = Driver.getDriver().findElements(By.xpath("//h5[text()='COMMUNICATION OPT-IN INFORMATION']/following::div[2]/label/span/span/input"));
                String updatedCheckboxValue = updatedCheckbox.get(i).getAttribute("checked");
                assertTrue(updatedCheckboxValue == null);
            }
        }
    }

    @And("I get rawLogs for {string} verify updated on by")
    public void iGetRawLogsForVerifyUpdatedOnBy(String url) {
        List<String> caseMemberSave = EventsUtilities.getRawLogs(url);
        String firstLog = caseMemberSave.get(0);
        String updatedBy = Driver.getDriver().findElement(By.xpath("//p[text()='Service  AccountOne']/parent::div/p[2]")).getText().replace("ID ", "");
        String presentTimeStamp = apitdu.get().getCurrentDateAndTime("yyyy-MM-dd'T'");
        Assert.assertTrue(firstLog.contains("\\\"updatedBy\\\":\\\"" + updatedBy));
        Assert.assertTrue(firstLog.contains("\\\"updatedOn\\\":\\\"" + presentTimeStamp));
    }


    @Then("I see error message populated below each field on Add AR Page")
    public void i_see_error_message_populated_below_each_field_on_Add_AR_Page() {
        assertTrue(crmAddPrimaryIndividualPage.piFNBlankError.isDisplayed(), "First name blank error is not displayed");
        assertTrue(crmAddPrimaryIndividualPage.piLNBlankError.isDisplayed(), "Last name blank error is not displayed");
        assertTrue(crmAddPrimaryIndividualPage.piDOBBlankError.isDisplayed(), "DOB blank error is not displayed");
        assertTrue(crmAddPrimaryIndividualPage.piGenderBlankError.isDisplayed(), "Gender blank error is not displayed");
        assertTrue(crmAddPrimaryIndividualPage.piStartDateBlankError.isDisplayed(), "Start date blank error is not displayed");
    }

    @When("I verify delete icon next to the MedicaidRID is not present")
    public void i_verify_delete_icon_next_to_the_Medicaid_RID_is_not_present() {
        waitFor(2);
        Assert.assertTrue(!isElementDisplayed(crmAddPrimaryIndividualPage.DeleteIconMedId));
    }

    @When("I select Gender as {string} to verify pregnancy checkbox is displayed on create consumer page")
    public void iSelectGenderAsToVerifyPregnancyCheckboxIsDisplayedOncreateConsumerPage(String gender) {
        selectDropDown(crmAddPrimaryIndividualPage.piGender, gender);
        assertTrue(corrSteps.elementIsDisplayed(crmAddPrimaryIndividualPage.pregnancyIndCheckBox), "pregnancyDueDate field is not displayed");
    }

    @When("I select Gender as {string}")
    public void i_select_Gender_as(String gender) {
        selectDropDown(crmAddPrimaryIndividualPage.piGender, gender);
    }

    @Then("I should be able to see PREGNANCY DUE DATE Field Display")
    public void i_should_be_able_to_see_PREGNANCY_DUE_DATE_Field_Display() {
        assertTrue(crmAddPrimaryIndividualPage.pregnancyDueDate.isDisplayed(), "pregnancyDueDate field is not displayed");
    }

    @When("I select all the rest {string} except Female")
    public void i_select_all_the_rest_except_Female(String gender) {
        selectDropDown(crmAddPrimaryIndividualPage.piGender, gender);
    }

    @Then("I should NOT see PREGNANT checkbox")
    public void i_should_NOT_to_see_PREGNANT_checkbox() {
        assertTrue(!isElementDisplayed(crmAddPrimaryIndividualPage.pregnancyIndCheckBox), "pregnancyIndCheckBox is  displayed");
    }

    @When("I change consumer role to {string}")
    public void i_change_consumer_role_to(String role) {
        waitFor(2);
        selectDropDown(crmAddPrimaryIndividualPage.consumerRole, role);
    }

    @Then("I verify the consumer role is updated to {string}")
    public void i_verify_the_consumer_role_is_updated_to(String role) {
        waitFor(3);
        //  assertTrue(crmAddPrimaryIndividualPage.consumerRole.getAttribute("value").equalsIgnoreCase(role));
        assertTrue(crmAddPrimaryIndividualPage.consumerRole.getText().equalsIgnoreCase(role));
    }

    @Then("I choose {string} in Relationship dropdown")
    public void i_choose_in_Relationship_dropdown(String option) {
        waitFor(2);
        selectDropDown(crmAddPrimaryIndividualPage.relationshipToDropdown, option);
    }

    @Then("I verify consumer is end dated with the day before")
    public void i_verify_consumer_is_end_dated_with_the_day_before() {
        waitFor(2);
        System.out.println(AuthorizedRep.arEndDate.getAttribute("value"));
        assertTrue(AuthorizedRep.arEndDate.getAttribute("value").replace("/", "").equalsIgnoreCase(getPriorDate(1).replace("/", "")));
    }

    @Then("I verify Relationship to PI field becomes disabled")
    public boolean relationship_to_PI_field_becomes_disabled() {
        try {
            waitForClickablility(crmAddPrimaryIndividualPage.relationshipToDropdown, 5);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Then("I verify consumer role is {string} and is disabled")
    public void i_verify_consumer_role_is_and_is_disabled(String role) {
        assertTrue(crmAddPrimaryIndividualPage.consumerRoleDisabled.getAttribute("value").equalsIgnoreCase(role));
        assertTrue(!crmAddPrimaryIndividualPage.consumerRoleDisabled.isEnabled(), "Consumer role is clickable");
    }

    @Then("I am able to update the Consumer Role {string}")
    public void iAmAbleToUpdateTheConsumerRole(String option) {
        waitFor(2);
        selectDropDown(crmAddPrimaryIndividualPage.consumerRoleField, option);
    }

    @And("I validate relationship field {string}")
    public void iValidateRelationshipField(String condition) {
        boolean expected = false;
        switch (condition) {
            case "required":
                assertTrue(crmAddPrimaryIndividualPage.labelRelationship.isDisplayed(), "Relationship filed should be  " + condition);
                break;
            case "not displayed":
                try {
                    crmAddPrimaryIndividualPage.labelRelationship.isDisplayed();
                } catch (NoSuchElementException e) {
                    expected = true;

                }
                assertTrue(expected, "Relationship filed should be  " + condition);

        }
    }

    @Then("I see options for relationship field will display and is required")
    public void iSeeOptionsForFieldWillDisplayAndIsRequired(List<String> options) {
        for (String option : options) {
            selectDropDown(crmAddPrimaryIndividualPage.relationshipToDropdown, option);
            waitFor(1);
            assertTrue(markedMandatory(crmAddPrimaryIndividualPage.labelRelationship));

        }
    }

    @Then("I verify Authorized Ref Type has following options")
    public void i_verify_Authorized_Ref_Type_has_following_options(List<String> options) {
        assertTrue(crmAddPrimaryIndividualPage.authRepTypeTxt.isDisplayed());
        for (String option : options) {
            selectDropDown(crmAddPrimaryIndividualPage.authRefTypeValue, option);
        }
    }

    @Then("I verify Authorized Ref Type value for the case is {string}")
    public void i_verify_Authorized_Ref_Type_value_for_the_case_is(String value) {
        waitFor(2);
        assertEquals(crmAddPrimaryIndividualPage.authRefTypeValue.getText(), value);
    }
}
