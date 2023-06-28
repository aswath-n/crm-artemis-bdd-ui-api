package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APIPlanManagementController;
import com.maersk.crm.pages.tm.TMConfigurationPage;
import com.maersk.crm.pages.tm.TMProjectDetailsPage;
//import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.pages.tm.TMProjectHolidaysPage;
import com.maersk.crm.pages.tm.TMUploadProjectPage;
import com.maersk.crm.utilities.*;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;


import java.awt.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TM_ProjectConfigStepDef extends BrowserUtils {
    com.maersk.crm.pages.tm.TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();
    TMConfigurationPage tmConfigurationPage = new TMConfigurationPage();
    TMUploadProjectPage tmUploadProjectPage = new TMUploadProjectPage();
    final ThreadLocal<APIPlanManagementController> planAPI = ThreadLocal.withInitial(APIPlanManagementController::new);
    TMProjectHolidaysPage holidayPage = new TMProjectHolidaysPage();

    private final ThreadLocal<String> filePath = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> goLiveDate = ThreadLocal.withInitial(String::new);
    final ThreadLocal<List<String>> l2 = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<TM_EditProjectInformationStepDef> tmEditProjectInformationStepDef = ThreadLocal.withInitial(TM_EditProjectInformationStepDef::new);
    final ThreadLocal<TM_SearchProjectStepDefs> tmSearchProjectStepDefs = ThreadLocal.withInitial(TM_SearchProjectStepDefs::new);
    final ThreadLocal<TM_CreateProjectStepDef> tmCreateProjectStepDef = ThreadLocal.withInitial(TM_CreateProjectStepDef::new);
    final ThreadLocal<String> enumValue = ThreadLocal.withInitial(String::new);

    @When("I click on the config icon for the project")
    public void navigateToProjectConfig() {
        Assert.assertTrue(tmProjectDetailsPage.projConfig.isDisplayed(), "Project configuration icon is  not displayed");
        tmProjectDetailsPage.projConfig.click();
        Assert.assertTrue(tmProjectDetailsPage.taskTemplateNavigation.isDisplayed(), "Task template icon is  not displayed");

    }

    @Then("I verify configuration options displayed on the left nav menu")
    public void validateConfigurations() {
        // Assert.assertTrue(tmProjectDetailsPage.holidaysNavigation.isDisplayed(),"Holidays configuration is  not displayed");
        Assert.assertTrue(tmProjectDetailsPage.taskTemplateNavigation.isDisplayed(), "Task template configuration is  not displayed");
        //Assert.assertTrue(tmProjectDetailsPage.timeZoneWorkingHoursNavigation.isDisplayed(),"Time Zone and Work Hours configuration is  not displayed");
        Assert.assertTrue(tmProjectDetailsPage.taskTypeNavigation.isDisplayed(), "Task Type configuration is  not displayed");
        Assert.assertTrue(tmProjectDetailsPage.uploadProjectNavigation.isDisplayed(), "Upload Project configuration is  not displayed");
        //  Assert.assertTrue(tmProjectDetailsPage.projectIdNavigation.isDisplayed(),"Project Id configuration is  not displayed");

    }

    /**
     * Veridy the user lands on Task Template Configuration when the wrench icon is clicked
     */

    @Then("I verify user lands on Task Template configuration screen by default")
    public void verifyTaskTemplateConfigurationSelectedByDefault() {
        // Assert.assertTrue(tmProjectDetailsPage.holidaysNavigation.getAttribute("class").contains("active"),"User is not landed on Task Template configuration");
        Assert.assertTrue(tmProjectDetailsPage.taskTemplateNavigation.getAttribute("class").contains("active"), "User is not landed on Task Template configuration");
        Assert.assertEquals(tmProjectDetailsPage.lblSelectedConfigurationHeader.getText().trim(), "TASK TEMPLATE");

    }

    @Then("I choose {string} to upload project")
    public void i_choose_file_to_upload(String filetype) throws AWTException {
        switch (filetype) {
            case "File with Duplicate field key":
                try {
                    classLoader("testData/TMConfigfailurefiles/duplicateField.json");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "File with Duplicate grid key":
                try {
                    classLoader("testData/TMConfigfailurefiles/duplicateGrid.json");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "File with Duplicate grid column key":
                try {
                    classLoader("testData/TMConfigfailurefiles/duplicateGridColumn.json");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "File with Duplicate page key":
                try {
                    classLoader("testData/TMConfigfailurefiles/duplicatePage.json");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "File with Duplicate section key":
                try {
                    classLoader("testData/TMConfigfailurefiles/duplicateSection.json");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "File with Duplicate label key":
                try {
                    classLoader("testData/TMConfigfailurefiles/duplicateLabel.json");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /*This method to find the file path to upload the file
    Author -Vidya
     */

    @Then("I upload the plan file for service regions {string}")
    public void i_upload_the_plan_file_for_service_regions_something(String filetype) throws AWTException {
        switch (filetype) {
            case "File Failure":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Region_Failure.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "File Success":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Region_success.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "File Extension":
                try {
                    classLoader("testData/tm/planAndRegionConfig/DocumentType.doc");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "File Subprogram":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Region_success_SubProgram.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GA Region File Success":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Region_GA_Success.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "No File":
                filePath.set("No File");
                break;
            case "File missing Program Type":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Region_Configuration_BLCRM_missing_Program-Type.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "File missing Sub Program Type":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Region_Configuration_BLCRM_missing_Sub-Program-Type.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "File missing Service Delivery Area":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Region_Configuration_BLCRM_missing_Service-Delivery-Area.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "File Duplicate Rows":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Region_Configuration_BLCRM_duplicate-rows.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "File Normal":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Region_Configuration_BLCRM.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Service Region SubProgram Type different characters BLCRM":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Region_Configuration_BLCRM_AC_Sub-Program-Type.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Service Region Program Type different characters BLCRM":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Region_Configuration_BLCRM_AC_Program-Type.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Service Region SubProgram Type with Special Characters BLCRM":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Region_Configuration_BLCRMwithSpecialCharacters.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "INEB Service Config":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Region_Configuration_INEB.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /*This method attch the file in service region tab
    Author -Vidya
     */

    @And("I attach file to Service regions tab")
    public void iAttachedTheFileToServiceRegionTab() {
        if (filePath.get().equalsIgnoreCase("") && !filePath.get().equalsIgnoreCase("No File")) {
            waitForVisibility(tmConfigurationPage.serviceAttachFile, 10);
            if (Driver.getDriver() instanceof RemoteWebDriver && !(Driver.getDriver() instanceof ChromeDriver)) {
                ((RemoteWebDriver) Driver.getDriver()).setFileDetector(new LocalFileDetector());
            }
            tmConfigurationPage.getServiceAttachFile.sendKeys(replaceTrimURL.get());
        }
    }

    @And("I attach file to choose file tab")
    public void i_attach_file_to_choose_file_tab() throws Throwable {
        if (filePath.get().equalsIgnoreCase("") && !filePath.get().equalsIgnoreCase("No File")) {
            waitForVisibility(tmUploadProjectPage.attchFiletab, 10);
            if (Driver.getDriver() instanceof RemoteWebDriver && !(Driver.getDriver() instanceof ChromeDriver)) {
                ((RemoteWebDriver) Driver.getDriver()).setFileDetector(new LocalFileDetector());
            }
            tmUploadProjectPage.getAttachedFile.sendKeys(replaceTrimURL.get());
        }
    }

   /*This method to click on Upload button on Service regions tab to upload the file
    Author -Aswath
     */

    @Then("I click on the upload button on service config")
    public void i_click_on_the_upload_on_service_config() {
        click(tmConfigurationPage.serviceTabUpload);
    }

    @Then("I click on Upload button on Upload Project config")
    public void i_click_on_upload_button_on_upload_project_config() {
        click(tmUploadProjectPage.uploadBtn);
    }

    @Then("I verify the file uplaod failure {string} message")
    public void i_verify_the_file_uplaod_failure_something_message(String messagetype) {
        switch (messagetype) {
            case "Duplicate field failure message":
                waitForVisibility(tmUploadProjectPage.uploadErrorMsg1, 10);
                Assert.assertTrue(tmUploadProjectPage.uploadErrorMsg1.isDisplayed(), "Duplicate field failure message is not displayed");
                break;
            case "Duplicate grid failure message":
                waitForVisibility(tmUploadProjectPage.uploadErrorMsg2, 10);
                Assert.assertTrue(tmUploadProjectPage.uploadErrorMsg2.isDisplayed(), "Duplicate grid failure message is not displayed");
                break;
            case "Duplicate grid column failure message":
                waitForVisibility(tmUploadProjectPage.uploadErrorMsg3, 10);
                Assert.assertTrue(tmUploadProjectPage.uploadErrorMsg3.isDisplayed(), "Duplicate grid column failure message is not displayed");
                break;
            case "Duplicate page failure message":
                waitForVisibility(tmUploadProjectPage.uploadErrorMsg4, 10);
                Assert.assertTrue(tmUploadProjectPage.uploadErrorMsg4.isDisplayed(), "Duplicate page failure message is not displayed");
                break;
            case "Duplicate section failure message":
                waitForVisibility(tmUploadProjectPage.uploadErrorMsg5, 10);
                Assert.assertTrue(tmUploadProjectPage.uploadErrorMsg5.isDisplayed(), "Duplicate section failure message is not displayed");
                break;
            case "Duplicate label failure message":
                waitForVisibility(tmUploadProjectPage.uploadErrorMsg6, 10);
                Assert.assertTrue(tmUploadProjectPage.uploadErrorMsg6.isDisplayed(), "Duplicate label failure message is not displayed");
                break;
        }
    }

    /*This method to check PopUp message which is displayed once we upload the file
    Author -Aswath&Vidya
     */

    @Then("I verify the file upload {string} message")
    public void i_verify_the_file_upload_something_message(String MessageType) {
        switch (MessageType) {
            case "Upload Failed":
                waitForVisibility(tmConfigurationPage.uploadErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.uploadErrorMsg.getText().contains("ERROR MESSAGE"), "Error message is not displayed");
                //Assert.assertTrue (tmConfigurationPage.uploadErrorMsgTxt.getText().contains("Upload Failed-Missing mandatory fields"), "Error message text is not displayed" );
                break;
            case "Upload Success":
                //waitFor ( 5 );
                waitForVisibility(tmConfigurationPage.uploadScucessMsg, 300);
                Assert.assertTrue(tmConfigurationPage.uploadScucessMsg.getText().toUpperCase().contains("SUCCESS"), "Successful message is not displayed");
                Assert.assertTrue(tmConfigurationPage.uploadScucessMsgTxt.getText().contains("Upload Successful - Please Upload Plan File"), "Successful message text is not displayed");
                break;

            case "Upload Plan Success":
                waitForVisibility(tmConfigurationPage.uploadScucessMsgTxt, 20);
                Assert.assertTrue(tmConfigurationPage.uploadScucessMsgTxt.getText().toUpperCase().contains("SUCCESS"), "Successful message is not displayed");
                Assert.assertTrue(tmConfigurationPage.uploadScucessMsgTxt.getText().contains("Upload Successful"), "Successful message text is not displayed");
                break;
            case "File Extension":
                waitForVisibility(tmConfigurationPage.uploadExtnMsg, 30);
                Assert.assertTrue(tmConfigurationPage.uploadExtnMsg.getText().contains("ERROR"), "Error message is not displayed");
                Assert.assertTrue(tmConfigurationPage.uploadExtnMsgTxt.getText().contains("File Type is not .xlsx"), "Error message text is notdisplayed");
                break;
            case "No File":
                waitForVisibility(tmConfigurationPage.uploadExtnMsg, 10);
                Assert.assertTrue(tmConfigurationPage.uploadExtnMsg.getText().contains("ERROR"), "Error message is not displayed");
                Assert.assertTrue(tmConfigurationPage.uploadExtnMsgTxtForNoFile.getText().contains("Please select file to upload"), "Error message text is not displayed");
                break;
            case "Warning":
                waitForVisibility(tmConfigurationPage.warningPopUpMsg, 10);
                Assert.assertTrue(tmConfigurationPage.warningPopUpMsg.getText().contains("WARNING MESSAGE"), "Error message is not displayed");
                Assert.assertTrue(tmConfigurationPage.waringPopUpMsgTxt.getText().toLowerCase().contains("your file will not be uploaded"), "Error message text is not displayed");
                break;
            case "File Size":
                waitForVisibility(tmConfigurationPage.uploadExtnMsg, 30);
                Assert.assertTrue(tmConfigurationPage.uploadExtnMsg.getText().contains("ERROR"), "Error message is not displayed");
                Assert.assertTrue(tmConfigurationPage.uploadExtnMsgTxt.getText().contains("File is too large, it is more than 1MB"), "Error message text is not displayed");
                break;
            case "File Name":
                waitForVisibility(tmConfigurationPage.uploadExtnMsg, 10);
                Assert.assertTrue(tmConfigurationPage.uploadExtnMsg.getText().contains("ERROR"), "Error message is not displayed");
                Assert.assertTrue(tmConfigurationPage.uploadExtnMsgTxt.getText().contains("File name must begin with the word Plan_"), "Error message text is not displayed");
                break;
            case "File Sheet Name":
                waitForVisibility(tmConfigurationPage.uploadExtnMsg, 10);
                Assert.assertTrue(tmConfigurationPage.uploadExtnMsg.getText().contains("ERROR"), "Error message is not displayed");
                Assert.assertTrue(tmConfigurationPage.uploadExtnMsgTxt.getText().contains("File sheets are not named correctly"), "Error message text is not displayed");
                break;
            case "File Sheet order":
                waitForVisibility(tmConfigurationPage.uploadExtnMsg, 10);
                Assert.assertTrue(tmConfigurationPage.uploadExtnMsg.getText().contains("ERROR"), "Error message is not displayed");
                Assert.assertTrue(tmConfigurationPage.uploadExtnMsgTxt.getText().contains("File sheets are not in the correct order"), "Error message text is not displayed");
                break;

            case "Column Missing":
                waitForVisibility(tmConfigurationPage.uploadErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.uploadErrorMsgTxt.getText().contains("The [ProviderServicesNum1] is not in the correct format/length."), "Error message text is not displayed");
                Assert.assertTrue(tmConfigurationPage.uploadErrorMsg.getText().contains("ERROR MESSAGE"), "Error message is not displayed");
                break;

            case "EL plan code Missing":
                waitForVisibility(tmConfigurationPage.uploadErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.uploadErrorMsgTxt.getText().contains("The Value Added Services sheet is missing required information"), "Error message text is not displayed");
                Assert.assertTrue(tmConfigurationPage.uploadErrorMsg.getText().contains("ERROR MESSAGE"), "Error message is not displayed");
                break;

            case "Mandatory Field Missing":
                waitForVisibility(tmConfigurationPage.uploadErrorMsg, 10);
                //Assert.assertTrue (tmConfigurationPage.uploadErrorMsgTxt.getText().contains("The [ServiceDeliveryArea] is required and cannot be left blank."), "Error message text is not displayed" );
                Assert.assertTrue(tmConfigurationPage.uploadErrorMsg.getText().contains("ERROR MESSAGE"), "Error message is not displayed");
                break;

            case "File not uploaded":
                waitForVisibility(tmConfigurationPage.uploadErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.uploadErrorMsgTxt.getText().contains("File Upload is Not Allowed"), "Error message text is not displayed");
                Assert.assertTrue(tmConfigurationPage.uploadErrorMsg.getText().contains("ERROR MESSAGE"), "Error message is not displayed");
                break;

            case "Uploaded Service Region":
//                waitForVisibility(tmConfigurationPage.uploadErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.uploadErrorMsgTxt.getText().contains("Please upload Service Region File First"), "Error message text is not displayed");
                Assert.assertTrue(tmConfigurationPage.uploadErrorMsg.getText().contains("ERROR MESSAGE"), "Error message is not displayed");
                break;

            case "Eligiblity limit Missing":
//                waitForVisibility(tmConfigurationPage.uploadErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.uploadErrorMsgTxt.getText().contains("The Eligibility Limitations sheet is missing required information"), "Error message text is not displayed");
                Assert.assertTrue(tmConfigurationPage.uploadErrorMsg.getText().contains("ERROR MESSAGE"), "Error message is not displayed");
                break;
            case "Plan absent Failed":
//                waitForVisibility(tmConfigurationPage.uploadErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.uploadErrorMsg.getText().contains("ERROR MESSAGE"), "Error message is not displayed");
                Assert.assertTrue(tmConfigurationPage.uploadErrorMsgTxt.getText().contains("Upload Failed-Missing mandatory fields"), "Error message text is not displayed");
                break;
            case "Plan Code Req Error":
//                waitForVisibility(tmConfigurationPage.fieldsErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.fieldsErrorMsg.isDisplayed(), "Required Fields Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.fieldsErrorMsg.getText().trim().equalsIgnoreCase("The [PlanCode] is required and cannot be left blank."), "Required Field Error Message Incorrect");
                break;
            case "Plan Name Req Error":
//                waitForVisibility(tmConfigurationPage.fieldsErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.fieldsErrorMsg.isDisplayed(), "Required Fields Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.fieldsErrorMsg.getText().trim().equalsIgnoreCase("The [PlanName] is required and cannot be left blank."), "Required Field Error Message Incorrect");
                break;
            case "Program Type Req Error":
//                waitForVisibility(tmConfigurationPage.fieldsErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.fieldsErrorMsg.isDisplayed(), "Required Fields Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.fieldsErrorMsg.getText().trim().equalsIgnoreCase("The [Program Type] is required and cannot be left blank."), "Required Field Error Message Incorrect");
                break;
            case "Contract Start Date Req Error":
//                waitForVisibility(tmConfigurationPage.fieldsErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.fieldsErrorMsg.isDisplayed(), "Required Fields Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.fieldsErrorMsg.getText().trim().equalsIgnoreCase("The [ContractStartDate] is required and cannot be left blank."), "Required Field Error Message Incorrect");
                break;
            case "All Req Fields Error":
//                waitForVisibility(tmConfigurationPage.fieldsErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.fieldsErrorMsg.isDisplayed(), "Required Fields Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.fieldsErrorMsg.getText().trim().equalsIgnoreCase("The [PlanName, PlanCode, ProgramType, ContractStartDate] is required and cannot be left blank."), "Required Field Error Message Incorrect");
                break;
            case "Plan Name Format Error":
//                waitForVisibility(tmConfigurationPage.formatErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.formatErrorMsg.isDisplayed(), "Field Formatting Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.formatErrorMsg.getText().trim().equalsIgnoreCase("The [PlanName] is not in the correct format/length."), "Field Formatting Error Message Incorrect");
                break;
            case "Plan Code Format Error":
//                waitForVisibility(tmConfigurationPage.formatErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.formatErrorMsg.isDisplayed(), "Field Formatting Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.formatErrorMsg.getText().trim().equalsIgnoreCase("The [PlanCode] is not in the correct format/length."), "Field Formatting Error Message Incorrect");
                break;
            case "Program Type Format Error":
//                waitForVisibility(tmConfigurationPage.formatErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.formatErrorMsg.isDisplayed(), "Field Formatting Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.formatErrorMsg.getText().trim().equalsIgnoreCase("The [ProgramType] is not in the correct format/length."), "Field Formatting Error Message Incorrect");
                break;
            case "Contract Start Format Error":
//                waitForVisibility(tmConfigurationPage.fieldsErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.fieldsErrorMsg.isDisplayed(), "Field Formatting Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.fieldsErrorMsg.getText().trim().equalsIgnoreCase("The [PlanName, PlanCode, ProgramType, ContractStartDate] is required and cannot be left blank."), "Field Formatting Error Message Incorrect");
                break;
            case "Plan Short Name Format Error":
//                waitForVisibility(tmConfigurationPage.formatErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.formatErrorMsg.isDisplayed(), "Field Formatting Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.formatErrorMsg.getText().trim().equalsIgnoreCase("The [PlanShortName] is not in the correct format/length."), "Field Formatting Error Message Incorrect");
                break;
            case "SubProgram Type Format Error":
//                waitForVisibility(tmConfigurationPage.formatErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.formatErrorMsg.isDisplayed(), "Field Formatting Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.formatErrorMsg.getText().trim().equalsIgnoreCase("The [SubProgramType] is not in the correct format/length."), "Field Formatting Error Message Incorrect");
                break;
            case "Contract End Format Error":
//                waitForVisibility(tmConfigurationPage.formatErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.formatErrorMsg.isDisplayed(), "Field Formatting Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.formatErrorMsg.getText().trim().equalsIgnoreCase("The [ContractEndDate] is not in the correct format/length."), "Field Formatting Error Message Incorrect");
                break;
            case "Enrollment Start Format Error":
//                waitForVisibility(tmConfigurationPage.formatErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.formatErrorMsg.isDisplayed(), "Field Formatting Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.formatErrorMsg.getText().trim().equalsIgnoreCase("The [EnrollmentStartDate] is not in the correct format/length."), "Field Formatting Error Message Incorrect");
                break;
            case "Eligibility Format Error":
//                waitForVisibility(tmConfigurationPage.formatErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.formatErrorMsg.isDisplayed(), "Field Formatting Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.formatErrorMsg.getText().trim().equalsIgnoreCase("The [EligibilityLimitation] is not in the correct format/length."), "Field Formatting Error Message Incorrect");
                break;
            case "Plan Code Duplicate Row Error":
//                waitForVisibility(tmConfigurationPage.duplicationErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.duplicationErrorMsg.isDisplayed(), "Duplicate Fields Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.duplicationErrorMsg.getText().trim().contains("More than one record found with the same plan code"), "Duplicate Field Error Message Incorrect");
                break;
            case "Program DNE Error":
//                waitForVisibility(tmConfigurationPage.unknownProgramErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.unknownProgramErrorMsg.isDisplayed(), "NonExisting Program Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.unknownProgramErrorMsg.getText().trim().contains("Program was not found in the system"), "NonExisting Program Error Message Incorrect");
                break;
            case "SubProgram DNE Error":
//                waitForVisibility(tmConfigurationPage.unknownSubProgramErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.unknownSubProgramErrorMsg.isDisplayed(), "NonExisting SubProgram Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.unknownSubProgramErrorMsg.getText().trim().contains("Sub-Program was not found in the system"), "NonExisting SubProgram Error Message Incorrect");
                break;
            case "Service Region DNE Error":
//                waitForVisibility(tmConfigurationPage.unknownServiceRegionErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.unknownServiceRegionErrorMsg.isDisplayed(), "NonExisting Service Region Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.unknownServiceRegionErrorMsg.getText().trim().contains("Service Region was not found in the system"), "NonExisting Service Region Error Message Incorrect");
                break;
            case "County DNE Error":
//                waitForVisibility(tmConfigurationPage.unknownCountyErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.unknownCountyErrorMsg.isDisplayed(), "NonExisting County Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.unknownCountyErrorMsg.getText().trim().contains("County Name was not found in the system"), "NonExisting Counties Error Message Incorrect");
                break;
            case "Zip Code DNE Error":
//                waitForVisibility(tmConfigurationPage.unknownZipCodeErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.unknownZipCodeErrorMsg.isDisplayed(), "NonExisting Zip Code Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.unknownZipCodeErrorMsg.getText().trim().contains("Zip Code was not found in the system"), "NonExisting Zip Code Error Message Incorrect");
                break;
            case "City DNE Error":
//                waitForVisibility(tmConfigurationPage.unknownCityErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.unknownCityErrorMsg.isDisplayed(), "NonExisting City Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.unknownCityErrorMsg.getText().trim().contains("City was not found in the system"), "NonExisting City Error Message Incorrect");
                break;
            case "Contract End Date Precedes Contract Start Date Error":
//                waitForVisibility(tmConfigurationPage.precedingContractEndDateErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.precedingContractEndDateErrorMsg.isDisplayed(), "Contract End Date Preceding Contract Start Date Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.precedingContractEndDateErrorMsg.getText().trim().contains("contract end date is less than the contract start date"), "Contract End Date Preceeding Contract Start Date Error Message Incorrect");
                break;
            case "Enrollment Start Date Precedes Contract Start Date Error":
                waitForVisibility(tmConfigurationPage.precedingContractStartErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.precedingContractStartErrorMsg.isDisplayed(), "Start Date Preceding Contract Start Date Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.precedingContractStartErrorMsg.getText().trim().equalsIgnoreCase("The [EnrollmentStartDate] is not equal to or greater than the contract start date"), "Enrollment Start Date Preceding Contract Start Date Error Message Incorrect");
                break;
            case "Enrollment End Date Precedes Enrollment Start Date Error":
//                waitForVisibility(tmConfigurationPage.enrollmentEndPrecedesEnrollmentStartErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.enrollmentEndPrecedesEnrollmentStartErrorMsg.isDisplayed(), "Enrollment End Date Preceding Enrollment Start Date Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.enrollmentEndPrecedesEnrollmentStartErrorMsg.getText().trim().contains(" enrollment end date is less than the enrollment start date"), "Enrollment End Date Preceding Enrollment Start Date Error Message Incorrect");
                break;
            case "Value Added Services Error":
//                waitForVisibility(tmConfigurationPage.valueAddedSrvcsErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.valueAddedSrvcsErrorMsg.isDisplayed(), "Value Added Services Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.valueAddedSrvcsErrorMsg.getText().trim().equalsIgnoreCase("The Value Added Services sheet is missing required information"));
                break;
            case "Plan Configuration File Name Error":
//                waitForVisibility(tmConfigurationPage.fileNameErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.fileNameErrorMsg.isDisplayed(), "Plan Configuration File Name Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.fileNameErrorMsg.getText().trim().contains("File name must begin with the word Plan_"));
                break;
            case "Plan File Sheet Name Error":
//                waitForVisibility(tmConfigurationPage.sheetNameErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.sheetNameErrorMsg.isDisplayed(), "Plan File Sheet Name Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.sheetNameErrorMsg.getText().trim().contains("File sheets are not named correctly"));
                break;
            case "Plan File Sheet Order Error":
//                waitForVisibility(tmConfigurationPage.sheetOrderErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.sheetOrderErrorMsg.isDisplayed(), "Plan File Sheet Order Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.sheetOrderErrorMsg.getText().trim().contains("File sheets are not in the correct order"));
                break;
            case "Service Delivery Area Req Error":
//                waitForVisibility(tmConfigurationPage.fieldsErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.fieldsErrorMsg.isDisplayed(), "The Service Delivery Area is required Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.fieldsErrorMsg.getText().trim().contains("The Service Delivery Area is required and cannot be left blank."));
                break;
            case "Duplicate rows Req Error":
//                waitForVisibility(tmConfigurationPage.duplicateErrorMsg, 10);
                Assert.assertTrue(tmConfigurationPage.duplicateErrorMsg.isDisplayed(), "The Service Delivery Area is required Error Message Not Displayed");
                Assert.assertTrue(tmConfigurationPage.duplicateErrorMsg.getText().trim().contains("Duplicate record found in the file, uploading only the unique set of records into the system"));
                waitFor(5);
//                waitForVisibility(tmConfigurationPage.uploadScucessMsg, 250);
                Assert.assertTrue(tmConfigurationPage.uploadScucessMsg.getText().toUpperCase().contains("SUCCESS"), "Successful message is not displayed");
                Assert.assertTrue(tmConfigurationPage.uploadScucessMsgTxt.getText().contains("Upload Successful - Please Upload Plan File"), "Successful message text is not displayed");
                break;
            case "Upload Plan":
//                waitForVisibility(tmConfigurationPage.uploadScucessMsg, 400);
                Assert.assertTrue(tmConfigurationPage.uploadScucessMsg.getText().toUpperCase().contains("SUCCESS"), "Successful message is not displayed");
                Assert.assertTrue(tmConfigurationPage.uploadScucessMsgTxt.getText().contains("Upload Successful - Please Upload Plan File"), "Successful message text is not displayed");
                waitFor(10);
                clickOnPlanConfigTab();
                try {
                    iChooseFileToUploadInPlanConfigTab("BLCRM Plan config");
                } catch (AWTException e) {
                    e.printStackTrace();
                }
                attachedTheFileToPlanConfigTab();
                clickOnPlanUploadButton();
                i_verify_the_file_upload_something_message("Upload Plan Success");
                break;
        }
    }

    /*This method to check Service region config tab and Plan config tab page and there elements are displayed.
    Author -Aswath&Vidya
     */

    @Then("I verify the {string} page fields")
    public void i_verify_page_fields(String plantype) {

        switch (plantype) {
            case "Service":
                String currURL = Driver.getDriver().getCurrentUrl();
                Assert.assertTrue(currURL.contains("configuration/plan-management"), "Plan Config. page is not displayed");
                Assert.assertTrue(tmConfigurationPage.serivceTabHdr.isDisplayed(), "Service Config Header is not displayed");
                Assert.assertTrue(tmConfigurationPage.serviceTabUpload.isDisplayed(), "Upload button is not displayed");
                Assert.assertTrue(tmConfigurationPage.serviceAttachFile.isDisplayed(), "Attach file button is not displayed");
                Assert.assertTrue(tmConfigurationPage.serivceTabCancelBtn.isDisplayed(), "Cancel button is not displayed");
                Assert.assertTrue(tmConfigurationPage.backArrow.isDisplayed(), " Back Arrow is not displayed");
                break;
            case "Plan":
                click(tmConfigurationPage.planConfigTab);
                currURL = Driver.getDriver().getCurrentUrl();
                Assert.assertTrue(currURL.contains("configuration/plan-management"), "Plan Config. page is not displayed");
                Assert.assertTrue(tmConfigurationPage.plansTabHdr.isDisplayed(), "Plans Config Header is not displayed");
                Assert.assertTrue(tmConfigurationPage.plansTabUpload.isDisplayed(), "Upload button is not displayed");
                Assert.assertTrue(tmConfigurationPage.planAttachFile.isDisplayed(), "Attach file button is not displayed");
                Assert.assertTrue(tmConfigurationPage.plansTabCancelBtn.isDisplayed(), "Cancel button is not displayed");
                Assert.assertTrue(tmConfigurationPage.backArrow.isDisplayed(), " Back Arrow is not displayed");
                break;
        }
    }

    /*This method to click on Back Arrow present on both service config and plan config tabs
    Author -Vidya
     */

    @Then("I click on the back arrow button")
    public void i_click_on_the_back_arrow_button() {
        waitForVisibility(tmConfigurationPage.backArrow, 10);
        click(tmConfigurationPage.backArrow);
    }

    /*This method to check Tanent manager project page is displayed or not
    Author -Aswath
     */

    @Then("I Verify the tenant manager home page")
    public void tenantManagerHomePage() {
        String currURL = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(currURL.contains("/tenant/project"), "Tenant Manager home page is not displayed");
    }

    /*This method to click on cancel button present on service region config tab.
    Author -Aswath
     */

    @Then("I Click on Service regions tab Cancel button")
    public void i_click_on_the_cancel_on_service_config() {
        waitForVisibility(tmConfigurationPage.serivceTabCancelBtn, 10);
        click(tmConfigurationPage.serivceTabCancelBtn);
    }

    /*This method to click on configuration icon present on left hand side.
    Author -Vidya
     */

    @And("I click on the configuration icon for the project")
    public void clickOnConfigIcon() {
        waitForVisibility(tmProjectDetailsPage.projConfig, 10);
        click(tmProjectDetailsPage.projConfig);
    }

    @And("I navigate to User role list screen")
    public void i_navigate_to_user_role_list_screen() {
        click(tmProjectDetailsPage.viewRoleList);
    }

    /*This method to click on Plans Management Link
    Author -Vidya
     */

    @And("I click on the Plans Management")
    public void clickOnPlansManagement() {
        waitForVisibility(tmConfigurationPage.planManagement, 10);
        click(tmConfigurationPage.planManagement);
    }

    @And("I click on the Upload Project tab")
    public void clickOnUploadprojecttab() {
        waitForVisibility(tmConfigurationPage.uploadProject, 10);
        click(tmConfigurationPage.uploadProject);
    }

    @Then("I verify Upload project page is displayed")
    public void i_verify_upload_project_page_is_displayed() throws Throwable {
        Assert.assertTrue(tmUploadProjectPage.UpladoPrjcthdr.isDisplayed());
    }

    /*This method to click on Plans Config Tab
    Author -Vidya
     */

    @And("I click on PLANS CONFIG Tab")
    public void clickOnPlanConfigTab() {
        waitForVisibility(tmConfigurationPage.planConfigTab, 10);
        jsClick(tmConfigurationPage.planConfigTab);
        waitFor(5);
    }

    /*This method to click on Upload button present on Plans Config tab
    Author -Vidya
     */

    @And("I Click on Plan Config tab Upload button")
    public void clickOnPlanUploadButton() {
        waitFor(3);//File uplopading isprogress, dont remove this wait
        waitForVisibility(tmConfigurationPage.plansTabUpload, 10);
        click(tmConfigurationPage.plansTabUpload);
        waitFor(2);
    }

    /*This method to attach the file to Plan Config Tab
    Author -Vidya
     */

    @And("I attach file to Plan Config tab")
    public void attachedTheFileToPlanConfigTab() {
        if (filePath.get().equalsIgnoreCase("") && !filePath.get().equalsIgnoreCase("No File")) {
            waitForVisibility(tmConfigurationPage.planAttachFile, 10);
            if (Driver.getDriver() instanceof RemoteWebDriver && !(Driver.getDriver() instanceof ChromeDriver)) {
                ((RemoteWebDriver) Driver.getDriver()).setFileDetector(new LocalFileDetector());
            }
            tmConfigurationPage.getPlanAttachFile.sendKeys(replaceTrimURL.get());
        }
    }

    /*This method to find the file path to upload the file in Plan Config tab
    Author -Vidya
     */

    @Then("I choose a file to upload in Plan Config tab {string}")
    public void iChooseFileToUploadInPlanConfigTab(String filetype) throws AWTException {
        switch (filetype) {
            case "File Failure Column Missing":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Plan_FailureTest_ColumnMissing.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "File Failure EL-PlanCode":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Plan_FailureTest_EL-PlanCode.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan Success":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Plan_Configuration_Success.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case "File Failure Mandatory Fields":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Plan_FailureTest_MandatoryFields.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "File Failure VAS-Plan Code":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Plan_FailureTest_VAS-PlanCode.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "File Success":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Plan_Configuration_Success.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "File Extension":
                try {
                    classLoader("testData/tm/planAndRegionConfig/DocumentType.doc");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GA Plan config":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Plan_GA_Configuration.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "INEB Plan Config":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Plan_Configuration_INEB.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "BLCRM Plan config":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Plan_Configuration_BLCRM.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "No File":
                filePath.set("No File");
                break;

            case "File Sheet order":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Plan_Configuration_SheetOrder.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "File Sheet Name":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Plan_Configuration_SheetName.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "File Name":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Region_success.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "File Size":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Plan_Configuration_Size.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "File Failure EL Type":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Plan_FailureTest_EL.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan Name Absent":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_planNameAbsent.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan Code Absent":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_planCodeAbsent.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Program Type Absent":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_programTypeAbsent.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Contract Start Date Absent":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_contractStartDateAbsent.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "All Required Fields Absent":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_all4RequiredFieldsAbsent.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan Name Invalid Format":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_planName_formatLength.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan Code Invalid Format":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_planCode_formatLength.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Program Type Invalid Format":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_programType_formatLength.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Contract Start Invalid Format":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_contractStartDate_formatLength.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan Short Name Invalid Format":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_planShortName_formatLength.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "SubProgram Type Invalid Format":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_subPrgmType_formatLength.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan SubProgram Type different characters BLCRM":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_BLCRM_TestFile_FAIL_subPrgmType_formatLength.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan SubProgram Type with Special Characters BLCRM":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Plan_Configuration_BLCRMwithSpecialCharacters.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan Program Type different characters BLCRM":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_BLCRM_TestFile_FAIL_PrgmType_formatLength.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Contract End Invalid Format":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_contractEndDate_formatLength.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Enrollment Start Invalid Format":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_enrollmentStartDate_formatLength.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Eligibility Invalid Format":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_eligLimitations_formatLength.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan Code Duplicate Row":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_planCode_duplicates.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Program DNE":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_nonExistingPrograms.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "SubProgram DNE":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_nonExistingSubPrograms.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Service Region DNE":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_nonExistingServiceRegion.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "County DNE":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_nonExistingCounties.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Zip Code DNE":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_nonExistingZipCodes.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "City DNE":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_nonExistingCities.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Contract End Date Precedes Contract Start Date":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_endDatePrecedesStartDate.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Enrollment Start Date Precedes Contract Start Date":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_enrollmentDatePrecedesStartDate.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Enrollment End Date Precedes Enrollment Start Date":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_enrollmentEndPrecedesEnrollmentStartDate.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Value Added Services Missing":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_valueAddedSrvcsMissing.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Eligibility Limitations Missing":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_eligibilityLimitationsMissing.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GA Plan Config":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Plan_Configuration_GA_TestFile_V3.0.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan Configuration File name must start with the word Plan":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/FailFileName_Configuration_GA_FAIL_SheetName_ZipDNE1.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
//            case "Plan File Extension Error":
//                try {
//                    classLoader ( "ZZ" );
//                } catch (Exception e) {
//                    e.printStackTrace ();
//                }
//                break;
            case "Plan File Sheet Names":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_FAIL_Extension_SheetName_SheetOrder_ZipDNE.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan File Sheet Order":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_FAIL_SheetOrder_ReqInformation.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
//            case "Plan File Size Error":
//                try {
//                    classLoader ( "ZZ" );
//                } catch (Exception e) {
//                    e.printStackTrace ();
//                }
//                break;
            case "Plan Required Information Error":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_FAIL_ReqInformation_FieldValidation.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
//            case "Plan Field Validation Error":
//                try {
//                    classLoader ( "ZZ" );
//                } catch (Exception e) {
//                    e.printStackTrace ();
//                }
//                break;
//            case "One unique plan code per file":
//                try {
//                    classLoader ( "ZZ" );
//                } catch (Exception e) {
//                    e.printStackTrace ();
//                }
//                break;
            case "Plan Program comparison to Database":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_nonExistingPrograms_nonExistingSubPrograms.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan Sub-Program comparison to Database":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_subProgramDNE_serviceDelivAreaDNE.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan Service Delivery Area Comparison to Database":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_ServiceRegionDNE_CitiesDNE.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan County Name Comparison to Database":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_countyDNE_zipCodeDNE.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan Zip Code Comparison to Database":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAI_zipCodeDNE_cityDNE.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan City Comparison to Database":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_cityDNE_incorectCityName.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan Contract End Date Validation - Contract Start Date Check":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_contractEndDate_enrollmentStartdate.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan Enrollment Start Date Validation - Contract Start Date Check":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_enrollmentStartdate_enrollmentEndDate.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan Enrollment End Date Validation":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_enrollmentEndDate_valueAddedSrvcs.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Value Added Services Sheet - Plan Code Provided":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_valueAddedSrvcs_eligibility.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Eligibility Limitations Sheet - Plan Code Provided":
                try {
                    classLoader("testData/tm/planAndRegionConfig/planConfigFailures/Plan_Configuration_GA_TestFile_FAIL_eligibilityLimitationsMissing.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan Configuation file missing optional columns":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Plan_Configuration_BLCRM_missing_optional_rows.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan Configuation file missing Value Added Services tab":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Plan_Configuration_BLCRM_missing_Value_Added_Services.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Plan Configuation file missing Eligibility Limitations tab":
                try {
                    classLoader("testData/tm/planAndRegionConfig/Plan_Configuration_BLCRM_missing_Eligibility_Limitations.xlsx");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }
    /*This method to click on Cancel button present on Plans Config tab
    Author -Vidya
     */

    @And("I Click on Plan Config tab Cancel button")
    public void clickOnPlanConfigCancelButton() {
        waitForVisibility(tmConfigurationPage.plansTabCancelBtn, 10);
        click(tmConfigurationPage.plansTabCancelBtn);
    }

    /*This method to click on Continue button present on Warning Popup
    Author -Vidya
     */

    @And("I click on the continue button on warning pop up")
    public void clickOnContinueButtonOnWarningPopup() {
        waitForVisibility(tmConfigurationPage.continuebtnWarningPopUp, 10);
        click(tmConfigurationPage.continuebtnWarningPopUp);
    }

    /*This method to verify remain on the Plan configuration Page and the previously chosen file
          Should be cleared once we click on Continue button on warning popup
    Author -Vidya
     */

    @Then("I verify remain on the Plan configuration Page and the previously chosen file Should be cleared")
    public void verifyContinueButtonFunctionality() {
        Assert.assertTrue(tmConfigurationPage.plansTabHdr.isDisplayed(), "User is not on the Plan configuration Page");
        try {
            Assert.assertFalse(tmConfigurationPage.fileAttachedInplanTab.isDisplayed(), "Previously chosen file is not cleared");
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    /*This method to click on Cancel button present on Warning Popup
    Author -Vidya
     */

    @And("I click on the cancel button on warning pop up")
    public void clickOnCancelButtonOnWarningPopup() {
        waitForVisibility(tmConfigurationPage.cancelbtnWarningPopUp, 10);
        click(tmConfigurationPage.cancelbtnWarningPopUp);
    }

    /*This method to verify remain on the Plan configuration Page and the previously chosen file
          Should not be cleared once we click on Cancel button on warning popup
    Author -Vidya
     */

    @Then("I verify remain on the Plan configuration Page and the previously chosen file Should not be cleared")
    public void verifyCancelButtonFunctionality() {
        Assert.assertTrue(tmConfigurationPage.plansTabHdr.isDisplayed(), "User is not on the Plan configuration Page");
        Assert.assertTrue(tmConfigurationPage.fileAttachedInplanTab.isDisplayed(), "Previously chosen file is cleared");
    }

    /*This method to check Project List page is displayed
      Author -Vidya
    */

    @Then("I verify Project List Page is displayed")
    public void verifyProjectListpageisDisplayed() {
        Assert.assertTrue(tmConfigurationPage.projectListPage.isDisplayed(), "Project List Page is not displayed");
    }

    /*This method to click on Task Type Link
      Author -Vidya
    */

    @And("I click on the task type link")
    public void clickonTaskTypeLink() {
        click(tmConfigurationPage.taskTypeLink);
    }

    /*This method to check whether I should navigate to Task Type page
      Author -Vidya
    */

    @Then("I verify should I navigate to page for which I selected")
    public void verifyInavigatedTOTaskTypePage() {
        Assert.assertTrue(tmConfigurationPage.taskTypePage.isDisplayed(), "It should not navigate to page which I Selwcted");
    }

    /*This method to verify remain on the Service region configuration Page and the previously chosen file
          Should be cleared once we click on Continue button on warning popup
    Author -Vidya
     */

    @Then("I verify remain on the Service region configuration Page and the previously chosen file Should be cleared")
    public void verifyContinueButtonFunctionalityofService() {
        Assert.assertTrue(tmConfigurationPage.serivceTabHdr.isDisplayed(), "User is not on the Service region configuration Page");
        try {
            Assert.assertFalse(tmConfigurationPage.fileAttachedInServiceTab.isDisplayed(), "Previously chosen file is not cleared");
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    /*This method to verify remain on the Service region configuration Page and the previously chosen file
          Should not be cleared once we click on Cancel button on warning popup
    Author -Vidya
     */

    @Then("I verify remain on the Service region configuration Page and the previously chosen file Should not be cleared")
    public void verifyCancelButtonFunctionalityofService() {
        Assert.assertTrue(tmConfigurationPage.serivceTabHdr.isDisplayed(), "User is not on the Service Region configuration Page");
        Assert.assertTrue(tmConfigurationPage.fileAttachedInServiceTab.isDisplayed(), "Previously chosen file is cleared");
    }

    /*
     * Adds new project with current date and different year
     * */
    public void addGoLiveDate() {
        clearAndFillText(tmProjectDetailsPage.contractEndDate, getFutureYearWithCurrentdate(6));
        clearAndFillText(tmConfigurationPage.projectGoLiveDate, getFutureYearWithCurrentdate(4));
        goLiveDate.set(tmConfigurationPage.projectGoLiveDate.getAttribute("value"));
        System.out.println("Go Live Date after update : " + goLiveDate.get());
        tmProjectDetailsPage.saveButton.click();
        waitForVisibility(tmProjectDetailsPage.projSuccessStatus, 10);
        //waitFor ( 3 );
        //tmSearchProjectStepDefs.i_search_for_a_project_by_value("project", "SelectFromConfig");
        //tmEditProjectInformationStepDef.i_expend_a_Project_to_view_the_details();
    }

    @Then("I update change the Go live date")
    public void updateChangeGoLiveDate() {
        clearAndFillText(tmProjectDetailsPage.contractStartDate, getCurrentDate());
        clearAndFillText(tmProjectDetailsPage.contractEndDate, getFutureYearWithCurrentdate(6));
        clearAndFillText(tmConfigurationPage.projectGoLiveDate, getFutureYearWithCurrentdate(4));
        goLiveDate.set(tmConfigurationPage.projectGoLiveDate.getAttribute("value"));
        System.out.println("Go Live Date after update : " + goLiveDate.get());
        click(tmProjectDetailsPage.saveButton);
        waitFor(2);
        //tmSearchProjectStepDefs.i_search_for_a_project_by_value("project", "SelectFromConfig");
        //tmEditProjectInformationStepDef.i_expend_a_Project_to_view_the_details();

    }

    /*To verify project Go live date, if go live date is the null or less than the current date, then update new go live date
    Author -Aswath
     */


    @Then("I verify the Go live date")
    public void verifyGoLiveDate() throws ParseException {
        goLiveDate.set(tmConfigurationPage.projectGoLiveDate.getAttribute("value"));
        System.out.println("Go Live Date: " + goLiveDate.get());
        addGoLiveDate();
    }


    @Then("I upload the service region file before to plan file")
    public void uploadServiceFileBeforePlanFile() throws AWTException {
        //planAPI.i_upload_the_service_region_prior_to_Planfile();
        i_upload_the_plan_file_for_service_regions_something("File Success");
        iAttachedTheFileToServiceRegionTab();
        i_click_on_the_upload_on_service_config();
        waitFor(2);
        i_verify_the_file_upload_something_message("Upload Success");


    }


    /*This method to verify user is landed on holiday screen by default when we
    click on configuration icon
    Author -Vidya
    Added reason:- to refactor the code for CRM-1411. Date:-23/10/2019
     */

    @Then("I verify user lands on Holiday screen by default")
    public void verifyUserLandsOnHolidaySceenByDefault() {
        waitForVisibility(holidayPage.holidayHeader, 10);
        Assert.assertTrue(holidayPage.holidayHeader.isDisplayed(), "User is not landed on Holiday page");
    }

    @Then("I will upload plan config the without service region")
    public void uploadPlanConfigSuccessFile() throws AWTException {
        waitFor(3);
        iChooseFileToUploadInPlanConfigTab("File Success");
        attachedTheFileToPlanConfigTab();
        waitFor(3);
        clickOnPlanUploadButton();
    }

    @Then("I upload the GA service region file before to plan file")
    public void uploadGAServiceFileBeforePlanFile() throws AWTException {
        i_upload_the_plan_file_for_service_regions_something("GA Region File Success");
        iAttachedTheFileToServiceRegionTab();
        i_click_on_the_upload_on_service_config();
        waitFor(4);
        i_verify_the_file_upload_something_message("Upload Success");
        waitFor(5);
    }


    //author TM ConfigurationLookUp @Sean Thorson

    @And("Navigate to lookup configuration page")
    public void clickOnLooUpConfigIcon() {
        waitForVisibility(tmProjectDetailsPage.projConfig, 10);
        click(tmProjectDetailsPage.lookupconfiguration);

    }

    @Then("I verify the following options displayed for Enum field")
    public void i_verify_the_following_options_displayed_for_Enum_field(java.util.List<String> expEnums) {
        // List<String> expEnums
        java.util.List<String> actCities = new ArrayList<String>();
        int count = 0;
        waitFor(2);
        tmProjectDetailsPage.selectedEnumTable.click();
        int size = tmProjectDetailsPage.selectenums.size();
        for (WebElement city : tmProjectDetailsPage.selectenums) {
            actCities.add(city.getText());
        }
        tmProjectDetailsPage.selectenums.get(0).click();
        System.out.println(actCities);
        System.out.println(expEnums);
        Assert.assertEquals(actCities, expEnums);
    }


    @Then("Validate the all fields on the ENUM by selecting the ‘Add Look up’ button")
    public void validate_the_all_fields_on_the_ENUM_by_selecting_the_Add_Look_up_button() {
        String value_length_fail = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnop";
        clearAndFillText(tmProjectDetailsPage.value, value_length_fail);
        waitFor(2);
        String description_length_fail = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnop1234";
        clearAndFillText(tmProjectDetailsPage.description, description_length_fail);
        waitFor(2);
        String reportlabel_length_fail = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnop1234";
        clearAndFillText(tmProjectDetailsPage.reportlabel, reportlabel_length_fail);
        waitFor(2);
        String defaultorder_length_fail = "12345678912345";
        clearAndFillText(tmProjectDetailsPage.orderbydefault, defaultorder_length_fail);
        waitFor(2);
        jsClick(tmProjectDetailsPage.addlookuprecordbutton);
        waitFor(2);
        Assert.assertTrue(tmProjectDetailsPage.valueValidation.getText().equalsIgnoreCase("Value should not exceed 64 characters"));
        Assert.assertTrue(tmProjectDetailsPage.descriptionvalidation.getText().equalsIgnoreCase("Description must be alphanumeric and cannot exceed 256 characters"));
        Assert.assertTrue(tmProjectDetailsPage.reportlabelvalidation.getText().equalsIgnoreCase("Report Label must be alphanumeric and cannot exceed 64 characters"));
        Assert.assertTrue(tmProjectDetailsPage.orderbydefaultvalidation.getText().equalsIgnoreCase("Order By Default should only contain numeric values and cannot exceed 10 characters"));
        jsClick(tmProjectDetailsPage.clearbutton);
        jsClick(tmProjectDetailsPage.addlookupbutton);
        String value_space_fail = "ABCD XYZ";
        clearAndFillText(tmProjectDetailsPage.value, value_space_fail);
        waitFor(2);
        jsClick(tmProjectDetailsPage.addlookuprecordbutton);
        Assert.assertTrue(tmProjectDetailsPage.valueValidation.getText().equalsIgnoreCase("Value must only contain uppercase letters and underscores"));
        jsClick(tmProjectDetailsPage.clearbutton);
        jsClick(tmProjectDetailsPage.addlookupbutton);
        String value_chnage_to_caps_pass = "abcdefgh";
        clearAndFillText(tmProjectDetailsPage.value, value_chnage_to_caps_pass);
        waitFor(2);
        Assert.assertTrue(tmProjectDetailsPage.value.getAttribute("value").equalsIgnoreCase(value_chnage_to_caps_pass));
        String value_alphanumwithunderscore_pass = "abcd_123";
        clearAndFillText(tmProjectDetailsPage.value, value_alphanumwithunderscore_pass);
        waitFor(2);
        jsClick(tmProjectDetailsPage.addlookuprecordbutton);
        Assert.assertTrue(tmProjectDetailsPage.valueValidation.getText().equalsIgnoreCase("Description is required and cannot be left blank."));
        String description_alphanum_pass = "abcd_123";
        clearAndFillText(tmProjectDetailsPage.description, description_alphanum_pass);
        waitFor(2);
        jsClick(tmProjectDetailsPage.addlookuprecordbutton);
        Assert.assertTrue(tmProjectDetailsPage.valueValidation.getText().equalsIgnoreCase("Description must be alphanumeric and cannot exceed 256 characters"));
        jsClick(tmProjectDetailsPage.clearbutton);
        jsClick(tmProjectDetailsPage.addlookupbutton);
        clearAndFillText(tmProjectDetailsPage.value, value_alphanumwithunderscore_pass);
        clearAndFillText(tmProjectDetailsPage.description, "abcd");
        String reportlabel_alphanum_pass = "abcd_123";
        clearAndFillText(tmProjectDetailsPage.reportlabel, reportlabel_alphanum_pass);
        waitFor(2);
        jsClick(tmProjectDetailsPage.addlookuprecordbutton);
        Assert.assertTrue(tmProjectDetailsPage.valueValidation.getText().equalsIgnoreCase("Report Label must be alphanumeric and cannot exceed 64 characters"));
        jsClick(tmProjectDetailsPage.clearbutton);
        jsClick(tmProjectDetailsPage.addlookupbutton);
        clearAndFillText(tmProjectDetailsPage.value, value_alphanumwithunderscore_pass);
        clearAndFillText(tmProjectDetailsPage.description, "abcd");
        clearAndFillText(tmProjectDetailsPage.reportlabel, "abcd");
        String defaultorder_alpha_fail = "abcd";
        clearAndFillText(tmProjectDetailsPage.orderbydefault, defaultorder_alpha_fail);
        waitFor(2);
        jsClick(tmProjectDetailsPage.addlookuprecordbutton);
        Assert.assertTrue(tmProjectDetailsPage.valueValidation.getText().equalsIgnoreCase("Order By Default should only contain numeric values and cannot exceed 10 characters"));
        jsClick(tmProjectDetailsPage.clearbutton);
    }

    @When("Click at add lookup button")
    public void add_lookup_button() {
        // Write code here that turns the phrase above into concrete actions
        waitForVisibility(tmProjectDetailsPage.addlookupbutton, 30);
        jsClick(tmProjectDetailsPage.addlookupbutton);
    }

    @When("Click at add lookup record button")
    public void add_lookup_record_button() {
        // Write code here that turns the phrase above into concrete actions
        waitForVisibility(tmProjectDetailsPage.addlookuprecordbutton, 30);
        jsClick(tmProjectDetailsPage.addlookuprecordbutton);
//        waitFor(5);

    }

    @When("Click at search button")
    public void search_button() {
        // Write code here that turns the phrase above into concrete actions
        waitFor(2);
        jsClick(tmProjectDetailsPage.searchlookupbutton);

    }

    @Then("Verify that usr is able to select the {string} database")
    public void verify_that_usr_is_able_to_select_the_d(String string) {
        waitForVisibility(tmProjectDetailsPage.databaselist, 15);
        click(tmProjectDetailsPage.databaselist);
        hover(tmProjectDetailsPage.databaselist);
        WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + string + "')]"));
        scrollToElement(single);
        hover(single);
        single.click();
        Assert.assertTrue(tmProjectDetailsPage.databaselist.getText().contains(string), "Selector " + string + " - wasn't selected");

    }

    @Then("Verify that usr is able to select the {string} database in look up")
    public void verify_that_usr_is_able_to_select_the_d_inLookUp(String string) {
        click(tmProjectDetailsPage.selectDatabaseDropdown);
        waitFor(4);
        WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + string + "')]"));
        scrollToElement(single);
        hover(single);
        click(single);
        Assert.assertTrue(tmProjectDetailsPage.databaselist.getText().contains(string), "Selector " + string + " - wasn't selected");
    }

    @When("I select any ENUM {string}  table")
    public void i_select_any_ENUM_table(String tableName) {
        // WebElement element = Driver.getDriver().findElement(By.id("navigationPageButton"));
        // Actions actions = new Actions(Driver.getDriver());
        // actions.moveToElement(tmProjectDetailsPage.selectedEnumTable).click().build().perform();
        waitFor(1);
        tmProjectDetailsPage.selectedEnumTable.click();
        waitFor(3);
        int size = tmProjectDetailsPage.selectenums.size();
        int rand = radomNumber(size);
        tmProjectDetailsPage.selectenums.get(rand).click();
    }

    @When("I select given ENUM {string}  table")
    public void i_select_given_ENUM_table(String tableName) {
        tmProjectDetailsPage.selectedEnumTable.click();
        waitFor(3);
        tmProjectDetailsPage.getEnumTableWithValue(tableName).click();
    }

    @Then("the ‘Add Look up’ button becomes disabled while I am performing data entry")
    public void the_Add_Look_up_button_becomes_disabled_while_I_am_performing_data_entry() {
        waitFor(5);
        Assert.assertFalse(tmProjectDetailsPage.addlookupbutton.isEnabled());
    }

    @Then("Then the {string} Success Message is displayed")
    public void then_the_Success_Message_is_displayed(String mesg) {
        waitForVisibility(tmProjectDetailsPage.getSuccessMessage(mesg), 5);
        Assert.assertTrue(tmProjectDetailsPage.getSuccessMessage(mesg).isDisplayed());
    }

    @And("I click on edit button to edit look up entry")
    public void i_click_on_edit_button_to_edit_look_up_entry() {
        tmProjectDetailsPage.getEditBtnforLookUpRecord(enumValue.get()).click();
    }

    @And("I verify the description field is editable")
    public void i_Verify_Description_field_ediatble() {
        Assert.assertTrue(tmProjectDetailsPage.description.isEnabled());
    }

    @Then("I enter {string}, {string},{string} and {string}")
    public void i_enter_and(String value, String description, String reportlabel, String startdate) {
        // Write code here that turns the phrase above into concrete actions
        tmProjectDetailsPage.value.sendKeys(getRandomString(20));
        tmProjectDetailsPage.description.sendKeys(getRandomString(60));
        tmProjectDetailsPage.reportlabel.sendKeys(getRandomString(8));
        clearAndFillText(tmProjectDetailsPage.selectedStartDate, getCurrentDate());

    }

    @Then("I enter values for {string}, {string},{string},{string},{string} and {string}")
    public void i_enter_values_for(String value, String description, String reportlabel, String scope, String order, String startdate) {
        enumValue.set(value + getRandomNumber(2));
        tmProjectDetailsPage.value.sendKeys(enumValue.get());
        tmProjectDetailsPage.description.sendKeys(description);
        tmProjectDetailsPage.reportlabel.sendKeys(reportlabel);
        tmProjectDetailsPage.scope.sendKeys(scope);
        tmProjectDetailsPage.orderByDefault.sendKeys(order);
        tmProjectDetailsPage.calenderBtn.click();
        tmProjectDetailsPage.calendarokbutton.click();

    }

    @Then("I enter {string},{string},{string},{string} to update enum record")
    public void i_enter_somethingsomethingsomething_to_update_enum_record(String reportlabelupdate, String scopeupdate, String orderbydefaultupdate, String DescriptionUpdate) {
        clearAndFillText(tmProjectDetailsPage.reportlabel, reportlabelupdate);
        clearAndFillText(tmProjectDetailsPage.scope, scopeupdate);
        clearAndFillText(tmProjectDetailsPage.orderByDefault, orderbydefaultupdate);
        clearAndFillText(tmProjectDetailsPage.description, orderbydefaultupdate);
    }

    @When("Click to close dialog box")
    public void close_dialog() {
        // Write code here that turns the phrase above into concrete actions
        waitForVisibility(tmProjectDetailsPage.okbutton, 30);
        jsClick(tmProjectDetailsPage.okbutton);
    }

    @Then("Verify that lookup button is enabled again")
    public void verify_that_lookup_button_is_enabled_again() {
        Assert.assertTrue(tmProjectDetailsPage.addlookupbutton.isEnabled());
    }

    @Then("the following required fields error messages should appear")
    public void the_following_required_fields_error_messages_should_appear(java.util.List<String> geterror) {

        waitFor(5);
        java.util.List<String> errormessage = new ArrayList<>();
        for (WebElement value : tmProjectDetailsPage.errormesg) {
            errormessage.add(value.getText());

        }
    }

    @When("Click at clear button")
    public void clear() {
        jsClick(tmProjectDetailsPage.clearbutton);
        waitFor(5);
    }

    @When("Click at Back arrow")
    public void back() {
        jsClick(tmProjectDetailsPage.backArrow);
        waitFor(5);
    }

    @When("Verify that user is navigated to project detail page")
    public void verify_that_user_is_navigated_to_project_detail_page() {
        Assert.assertTrue(tmProjectDetailsPage.projectDetailsTitle.isDisplayed());
    }

    @When("I choose to Navigate away and I do not have unsaved selections")
    public void i_choose_to_Navigate_away_and_I_do_not_have_unsaved_selections() {
        tmProjectDetailsPage.navigationlink.click();
        waitFor(10);
    }

    @Then("I am navigated wherever I chose to Navigate and am not presented a warning message")
    public void i_am_navigated_wherever_I_chose_to_Navigate_and_am_not_presented_a_warning_message() {
        Assert.assertTrue(tmProjectDetailsPage.naviigatedpage.isEnabled());
    }

    @Then("the existing records for that ENUM are displayed in a grid with the following fields populated:")
    public void the_existing_records_for_that_ENUM_are_displayed_in_a_grid_with_the_following_fields_populated(java.util.List<String> getvalue) {
        java.util.List<String> colvalues = new ArrayList<>();
        int count = 0;
        waitFor(5);
        int size = tmProjectDetailsPage.colvalues.size();
        for (WebElement value : tmProjectDetailsPage.colvalues) {
            colvalues.add(value.getText());
        }
        Assert.assertEquals(colvalues, getvalue);
    }

    @When("I click on Subprogram Type")
    public void i_click_on_Subprogram_Type() throws InterruptedException {
        waitForVisibility(tmConfigurationPage.subProgramTypeBx, 10);
        Actions action = new Actions(Driver.getDriver());
        action.sendKeys(Keys.ESCAPE).build().perform();
        waitFor(2);
        tmConfigurationPage.subProgramTypeBx.click();
        // List<WebElement> l1= Driver.getDriver().findElements(By.xpath("//*[@id=\"menu-subProgramType\"]/div[3]/ul/li"));
        List<WebElement> l1 = tmConfigurationPage.subprogramTypeDrpDwnLst;

        for (int i = 0; i < l1.size(); i++) {
            l2.get().add(l1.get(i).getText());
        }
        System.out.println(l2.get());
    }

    @Then("I view Subprogram Types in specific format")
    public void i_view_Subprogram_Types_in_specific_format() {
        System.out.println();
        for (int i = 0; i < l2.get().size(); i++) {
            String temp = l2.get().get(i);
            String regex = "^[A-Za-z0-9-_&/]+$";
            Pattern mp = Pattern.compile(regex);
            Matcher mm = mp.matcher(temp);
            Boolean flag = mm.matches();
            Assert.assertTrue(flag);
        }

    }


    @When("I click on Program Type")
    public void i_click_on_Program_Type() {
        waitForVisibility(tmConfigurationPage.ProgramTypeBx, 10);
        tmConfigurationPage.ProgramTypeBx.click();
        // List<WebElement> l1= Driver.getDriver().findElements(By.xpath("//*[@id=\"menu-subProgramType\"]/div[3]/ul/li"));

        List<WebElement> l1 = tmConfigurationPage.programTypeDrpDwnLst;

        for (int i = 0; i < l1.size(); i++) {
            l2.get().add(l1.get(i).getText());
        }
        System.out.println(l2.get());
    }

    @Then("I view Program Types in specific format")
    public void i_view_Program_Types_in_specific_format() {
        System.out.println();
        for (int i = 0; i < l2.get().size(); i++) {
            String temp = l2.get().get(i);
            String regex = "^[A-Za-z0-9-_&/]+$";
            Pattern mp = Pattern.compile(regex);
            Matcher mm = mp.matcher(temp);
            Boolean flag = mm.matches();
            Assert.assertTrue(flag);
        }

    }
}
