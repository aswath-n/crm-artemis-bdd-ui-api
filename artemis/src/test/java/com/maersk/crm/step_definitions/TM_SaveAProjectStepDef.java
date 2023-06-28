package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.LoginPage;
import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.pages.tm.TMListOfProjectsPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.TMUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TM_SaveAProjectStepDef extends TMUtilities {

//    private WebDriver driver= Driver.getDriver();

    //    LoginPage loginPage=new LoginPage();
    TMProjectDetailsPage TMProjectDetailsPage = new TMProjectDetailsPage();
    TMListOfProjectsPage tmListOfProjectsPage = new TMListOfProjectsPage();

    final ThreadLocal<TM_EditProjectInformationStepDef> tmProjectInformationPage = ThreadLocal.withInitial(TM_EditProjectInformationStepDef::new);
    public final ThreadLocal<String> userNamedatatExcel = ThreadLocal.withInitial(() -> getCellValueBySheetRowAndColoumn("Login", "ValidData", "UserName"));
    public final ThreadLocal<String> passExcel = ThreadLocal.withInitial(() -> getCellValueBySheetRowAndColoumn("Login", "ValidData", "password"));
    public final ThreadLocal<String> projectName = ThreadLocal.withInitial(() -> "Proj" + getRandomString(5));
    public final ThreadLocal<String> programName = ThreadLocal.withInitial(() -> "Medicaid");
    public final ThreadLocal<String> contractId = ThreadLocal.withInitial(() -> "ID" + getRandomNumber(4));
    public final ThreadLocal<String> clientName = ThreadLocal.withInitial(() -> getRandomString(5) + " Agency");
    public final ThreadLocal<String> startDate = ThreadLocal.withInitial(BrowserUtils::getCurrentDateWithFormat);
    public final ThreadLocal<String> projectName1 = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> programName1 = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> contractId1 = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> clientName1 = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> startDate1 = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> timeZone = ThreadLocal.withInitial(() -> "Chamorro");
    public final ThreadLocal<String> projNameValue = ThreadLocal.withInitial(String::new);


    @Then("the elements  should be displayed in the Project Contact Page")
    public void the_elements_should_be_displayed_in_the_project_contact_page() {
        staticWait(100);
        Assert.assertTrue(TMProjectDetailsPage.addProjectLogo.isDisplayed());
        Assert.assertTrue(TMProjectDetailsPage.projectName.isDisplayed());
        Assert.assertTrue(TMProjectDetailsPage.state.isDisplayed());
        getStateNames();
        staticWait(100);
        Assert.assertTrue(TMProjectDetailsPage.programName.isDisplayed());
        Assert.assertTrue(TMProjectDetailsPage.contractId.isDisplayed());
        Assert.assertTrue(TMProjectDetailsPage.stateAgencyName.isDisplayed());
        Assert.assertTrue(TMProjectDetailsPage.contractStartDate.isDisplayed());
        Assert.assertTrue(TMProjectDetailsPage.contractEndDate.isDisplayed());
        Assert.assertTrue(TMProjectDetailsPage.provisioningStatus.isDisplayed());
        TMProjectDetailsPage.provisioningStatus.click();
        hover(TMProjectDetailsPage.provisioningStatus);
        Assert.assertTrue(TMProjectDetailsPage.activeStatus.isDisplayed());
        Assert.assertTrue(TMProjectDetailsPage.inactiveStatus.isDisplayed());
        TMProjectDetailsPage.inactiveStatus.click();
        Assert.assertTrue(TMProjectDetailsPage.saveButton.isDisplayed());
    }

    //Refactored 1-31-2020 Vidya As per strory CP-1722
    @And("I enter valid Details and save the Project")
    public void i_enter_valid_details_and_save_the_project() {
        createAndSave(projectName.get(), programName.get(), contractId.get(), clientName.get(), timeZone.get());
    }

    //Refactored 1-31-2020 Vidya As per strory CP-1722
    @Then("I navigate back to Project List page")
    public void should_be_navigated_to_the_home_page() {
        waitForVisibility(TMProjectDetailsPage.backToProjectsButton, 10);
        TMProjectDetailsPage.backToProjectsButton.click();
        Assert.assertTrue(tmListOfProjectsPage.projectList.isDisplayed());
        highLightElement(tmListOfProjectsPage.projectList);
    }

    @Then("I should get a Error Message for duplicate project")
    public void i_should_get_a_error_message_for_duplicate_project() {
        waitForVisibility(TMProjectDetailsPage.errorSnackBarTitle, 15);
        Assert.assertTrue(TMProjectDetailsPage.errorSnackBarTitle.isDisplayed());
//        waitForVisibility ( TMProjectDetailsPage.duplicateProjError,15 );
        Assert.assertTrue(TMProjectDetailsPage.duplicateProjError.isDisplayed());
    }

    @And("I enter all the Details Contract Start Date and End Date as Same and not select Time Zone")
    public void i_enter_all_the_details_contract_start_date_and_end_date_as_same() {
        addContractDateAndSaveCurrentDate(projectName.get(), programName.get(), contractId.get(), clientName.get(), startDate.get());
    }

    @Then("I verify filed level error in End date and Time Zone fields")
    public void it_should_throw_an_error_message() {
        waitForVisibility(TMProjectDetailsPage.contractDateErrorMessage, 10);
        Assert.assertTrue(TMProjectDetailsPage.contractDateErrorMessage.isDisplayed());
        Assert.assertTrue(TMProjectDetailsPage.timeZoneFieldLevelError.isDisplayed());
    }

    //Refactored 12/19: Vinuta, this method is added newly as this testcase was not automated
    @And("I pass the duplicate values in Project Name and other Attributes")
    public void create_duplicate_project() {
        createAndSave(projectName.get(), programName.get(), contractId.get(), clientName.get(), timeZone.get());
    }


    /* Author -Paramita 11/12/2019 - Script for GO-LIVE-DATE when Go-Live Date is less than Contract Start date  */
    @And("I enter all the Details and enter Go-Live Date less than Contract start date")
    public void enterGoLiveDateLessThanContractStartDate() {
        tmProjectInformationPage.get().addGoLiveDateAndSaveLessThanContractStartDate(projectName.get(), programName.get(), contractId.get(), clientName.get(), startDate.get());
    }

    /* Author -Paramita 11/12/2019 - Script to Display error message when Go-Live Date is less than Contract Start date  */
    @Then("I should see system throw an Error message")
    public void errorMessageDisplayGoLiveDateLessThanContractStartDate() {
        String str = "Go-Live date cannot be before" + " " + getCurrentDate();
        Assert.assertTrue(TMProjectDetailsPage.goLiveDateErrorMessage.getText().contains(str), "Error message is not displayed");

    }

    /* Author -Paramita 11/12/2019 - Script for GO-LIVE-DATE when Go-Live Date is less than Contract End date */
    @And("I enter all the Details and enter Go-Live Date less than Contract end date")
    public void enterGoLiveDateLessThanContractEndDate() {
        tmProjectInformationPage.get().addGoLiveDateAndSaveLessThanContractEndDate(projectName.get(), programName.get(), contractId.get(), clientName.get(), startDate.get());
    }

    /* Author -Paramita 11/12/2019 - Script to display error message when Go-Live Date is less than Contract End date */
    @Then("I should see system throw an Error message when Go-Live Date less than Contract end date")
    public void errorMessageDisplayGoLiveDateLessThanContractEndDate() {
        String str1 = "Go-Live date cannot be after" + " " + getGreaterDateFormatMMddyyyy(10);
        Assert.assertTrue(TMProjectDetailsPage.goLiveDateErrorMessage.getText().contains(str1), "Error message is not displayed");
    }

    /* Author -Paramita 11/12/2019 - Script to create project sucessfully when Go-Live Date equal to Contract start date  */
    @And("I enter Go-Live Date equal to Contract start date project created sucessfully")
    public void addGoLIveDateAndSaveProjectSuccesfully() {
        tmProjectInformationPage.get().addGoLiveDateAndSaveProjectEqualContratStartDate(projectName.get(), programName.get(), contractId.get(), clientName.get(), startDate.get());
        Assert.assertTrue(TMProjectDetailsPage.projSuccessStatus.isDisplayed(), "Project Success status is not displayed ");
    }

    /* Author -Paramita 11/12/2019 - Script for GO-LIVE-DATE when Go-Live Date entered without Contract Start date */
    @And("I enter all the Details and enter Go-Live Date without contract start date")
    public void enterGoLiveDateWithoutContractStartDate() {
        tmProjectInformationPage.get().addGoLiveDateWithoutContractStartDate(projectName.get(), programName.get(), contractId.get(), clientName.get(), startDate.get());
    }

    /* Author -Paramita 11/12/2019 - Script to display error message when Go-Live Date entered without Contract Start date */
    @Then("I should see an Error message")
    public void errorMessageGoLiveDate() {
        waitForVisibility(TMProjectDetailsPage.goLiveContractDateErrorMessage, 10);
        Assert.assertTrue(TMProjectDetailsPage.goLiveContractDateErrorMessage.getText().contains("StartDate cannot be empty"), "Error Message is not displayed");
    }


    /*  CP-1070 -Code to get ProjectStatusId when project Update event is triggered
     Author : Paramita  Date : 14/01/2020
     */

    /* Code to create project sucessfully*/

    @And("I enter details and  created project sucessfully")
    public void projectCreatedSuccesfully() {
        addGoLIveDateAndSaveProjectSuccesfully();
    }

    // Author -Vidya 1-31-2020 As per story Cp-1722
    @And("I verify success message is displayed for project creation and is on same page")
    public void verifySuccessMessageIsDisplayedAndUserIsOnSamePage() {
        waitForVisibility(TMProjectDetailsPage.projSuccessStatus, 10);
        Assert.assertTrue(TMProjectDetailsPage.projSuccessStatus.isDisplayed(), "Project Success status is not displayed ");
        Assert.assertTrue(TMProjectDetailsPage.projectDetailsTitle.isDisplayed(), "User is not in same page");
    }

    // Author -Vidya 02-12-2020
    @And("I will enter {string},{string},{string},{string},{string},{string},{string},{string},{string},{string} fields values")
    public void enterValueInAllFields(String projectName, String state, String programName, String contractId,
                                      String clientName, String startDate, String endDate, String goliveDate,
                                      String provStatus, String timeZone) {
        scrollUpRobot();

        if (projectName.equals("{random}")) {
            this.projectName1.set("Prj" + getRandomString(8));
        } else if (!projectName.isEmpty()) {
            this.projectName1.set(projectName);
        }
        if (projectName.equals("duplicate"))
            this.projectName1.set(projNameValue.get());

        projNameValue.set(this.projectName1.get());
        if (!this.projectName1.get().equals(""))
            clearAndFillText(TMProjectDetailsPage.projectName, this.projectName1.get());

        if (!state.equals(""))
            selectDropDown(TMProjectDetailsPage.state, state);

        if (programName.equals("{random}")) {
            this.programName1.set("Prm" + getRandomString(5));
        } else
            this.programName1.set(programName);

        if (!this.programName1.get().equals(""))
            clearAndFillText(TMProjectDetailsPage.programName, this.programName1.get());

        if (contractId.equals("{random}")) {
            this.contractId1.set("CID" + getRandomNumber(3));
        } else
            this.contractId1.set(contractId);

        if (!this.contractId1.equals(""))
            clearAndFillText(TMProjectDetailsPage.contractId, this.contractId1.get());

        if (clientName.equals("{random}")) {
            this.clientName1.set("Clt" + getRandomString(8));
        } else
            this.clientName1.set(clientName);

        if (!this.clientName1.get().equals(""))
            clearAndFillText(TMProjectDetailsPage.stateAgencyName, this.clientName1.get());

        if (!provStatus.equals(""))
            selectDropDown(TMProjectDetailsPage.provisioningStatus, provStatus);

        this.startDate1.set(startDate);
        if (startDate.equals("today")) this.startDate1.set(getCurrentDate());
        else if (startDate.contains("-")) {
            this.startDate1.set(getPriorDate(Integer.parseInt(startDate.replace("-", ""))));
        } else if (startDate.contains("+")) {
            this.startDate1.set(getGreaterDate(Integer.parseInt(startDate.replace("+", ""))));
        }
        if (!this.startDate1.get().equals("")) {
            clearAndFillText(TMProjectDetailsPage.contractStartDate, this.startDate1.get());
        }

        if (endDate.equals("today")) endDate = getCurrentDate();
        else if (endDate.contains("-")) {
            endDate = getPriorDate(Integer.parseInt(endDate.replace("-", "")));
        } else if (endDate.contains("+")) {
            endDate = getGreaterDate(Integer.parseInt(endDate.replace("+", "")));
        }
        if (!endDate.equals("")) {
            clearAndFillText(TMProjectDetailsPage.contractEndDate, endDate);
        }

        if (goliveDate.equals("today")) goliveDate = getCurrentDate();
        else if (goliveDate.contains("-")) {
            goliveDate = getPriorDate(Integer.parseInt(goliveDate.replace("-", "")));
        } else if (goliveDate.contains("+")) {
            goliveDate = getGreaterDate(Integer.parseInt(goliveDate.replace("+", "")));
        }
        if (!goliveDate.equals("")) {
            clearAndFillText(TMProjectDetailsPage.goLiveDate, goliveDate);
        }

        if (!timeZone.equals("") && TMProjectDetailsPage.timezonefield.getText().equals(timeZone)) {
            timeZone = "Atlantic";
        }
        if (!timeZone.equals(""))
            selectDropDown(TMProjectDetailsPage.timezonefield, timeZone);
    }

    // Author -Vidya 02-12-2020
    @And("I click on save button on project details page")
    public void clickOnSaveButton() {
        TMProjectDetailsPage.saveButton.click();
    }

    @Then("I verify the error message is displayed for Invalid project name")
    public void i_verify_the_error_message_is_displayed_for_invalid_project_name() {
        waitFor(1);
        Assert.assertTrue(TMProjectDetailsPage.invalidProjectNameErrorMsg.isDisplayed());
    }


    // Author -Vidya 02-12-2020
    @And("I verify it should remain on the same page and information should not save in project details page")
    public void verifyUserRemainsOnSamePage() {
        Assert.assertTrue(TMProjectDetailsPage.projectDetailsTitle.isDisplayed(), "It is not remains on same page");
        Assert.assertNotEquals(TMProjectDetailsPage.projectName.getAttribute("value"), "", "Project Name field value is cleared");
        Assert.assertNotEquals(TMProjectDetailsPage.programName.getAttribute("value"), "", "Program Name field value is cleared");
        Assert.assertNotEquals(TMProjectDetailsPage.state.getAttribute("value"), "", "State field value is cleared");
        Assert.assertNotEquals(TMProjectDetailsPage.timezonefield.getAttribute("value"), "", "Time Zone field value is cleared");
    }

    // Author -Vidya 02-12-2020
    @And("I validate Time zone drop down values")
    public void verifyTimeZoneDropDownValue() {
        waitForVisibility(TMProjectDetailsPage.timezonefield, 10);
        click(TMProjectDetailsPage.timezonefield);
        waitFor(1);
        String[] value = {"Hawaii-Aleutian", "Alaska", "Central", "Mountain", "Pacific", "Eastern", "Arizona", "Atlantic", "Chamorro", "Hawaii", "Samoa"};
        ArrayList<String> actualValue = new ArrayList<String>();
        ArrayList<String> expectedValue = new ArrayList<>(Arrays.asList(value));
//        Collections.sort(expectedValue,String.CASE_INSENSITIVE_ORDER);
        for (int i = 1; i < TMProjectDetailsPage.timeZoneValue.size(); i++) {
            actualValue.add(TMProjectDetailsPage.timeZoneValue.get(i).getText());
        }
        Assert.assertEquals(actualValue, expectedValue, "Time Zone Drop down value is not correct");
    }
}
