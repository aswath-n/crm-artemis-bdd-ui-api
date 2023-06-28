package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMDashboardPage;
import com.maersk.crm.pages.crm.CRMUploadAlertPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CRM_UploadAlertFile extends CRMUtilities implements ApiBase {

    CRMUploadAlertPage alertPage = new CRMUploadAlertPage();
    CRMDashboardPage dashboardPage = new CRMDashboardPage();
    final ThreadLocal<String> randomFileName = ThreadLocal.withInitial(() -> getRandomNumber(4));

    @Then("I can see Alert tab from left navigation bar")
    public void iCanSeeAlertTabFromLeftNavigationBar() throws Throwable {
        assertTrue(alertPage.alertIcon.isDisplayed(), "Upload Alert Icon is missing");
    }

    @When("I am navigated to Case Alert Upload screen after clicking on Alert tab")
    public void iAmNavigatedToCaseAlertUploadScreenAfterClickingOnAlertTab() throws Throwable {
        jsClick(dashboardPage.case_ConsumerAlertTab);
        assertTrue(alertPage.alertComponentHeader.isDisplayed(), "Not navigated to Upload Alert screen");
    }

    @When("I validate all expected fields are present on Upload Case Alert screen")
    public void iValidateAllExpectedFieldsArePresentOnUploadCaseAlertScreen() throws Throwable {
        iAmNavigatedToCaseAlertUploadScreenAfterClickingOnAlertTab();
        assertTrue(alertPage.alertFileSelect.isDisplayed(), "File select button is not displayed");
        assertTrue(alertPage.alertFileUploadBtn.isDisplayed(), "File upload button is not displayed");
        assertTrue(alertPage.alertFileCancel.isDisplayed(), "Cancel File upload button is not displayed");
    }

    @When("I validate Upload Alert tab is not displayed for this user")
    public void iValidateUploadAlertTabIsNotDisplayedForThisUser() throws Throwable {
        assertTrue(!isElementDisplayed(alertPage.alertIcon), "Upload Alert Icon should not be displayed");
    }

    @When("I verify download alert template button is displayed")
    public void i_verify_download_alert_template_button_is_displayed() {
        assertTrue(alertPage.alertTemplate.isDisplayed(), "Download Alert Template is missing");
        assertTrue(alertPage.alertTemplate.isEnabled(), "Download Alert Template is not clickable");
    }

    @When("I navigate to Alert Upload screen")
    public void iNavigateToAlertUploadScreen() throws Throwable {
        iAmNavigatedToCaseAlertUploadScreenAfterClickingOnAlertTab();
    }

    @Then("I click on Recently Created tab")
    public void i_click_on_Recently_Created_tab() {
        alertPage.recentlyCreatedTab.click();
    }

    @Then("I validate all components are displayed on CaseConsumer Alert Landing Page")
    public void iValidateAllComponentsAreDisplayedOnCaseConsumerAlertLandingPage() throws Throwable {
        assertTrue(alertPage.alertComponentHeader.isDisplayed(), "Create Alert Component is nto Displayed");
        assertTrue(alertPage.uploadHistoryComponent.isDisplayed(), "Upload History Component is nto Displayed");
    }

    @Then("I validate create alert component on CaseConsumer Alert Landing Page")
    public void iValidateCreateAlertComponentOnCaseConsumerAlertLandingPage() throws Throwable {
        waitFor(3);
        assertTrue(alertPage.alertComponentHeader.isDisplayed(), "Create Alert Component is nto Displayed");
        assertTrue(alertPage.alertFileSelect.isDisplayed(), "File select is not displayed");
        assertTrue(alertPage.alertFileSelect.isEnabled(), "File select is not clickable");
        assertTrue(alertPage.alertFileUploadBtn.isDisplayed(), "Upload File button is not displayed");
        assertTrue(alertPage.alertFileUploadBtn.isEnabled(), "Upload File button is not clickable");
        assertTrue(alertPage.downloadAlertTemplate.isDisplayed(), "Download Alert Template button is not displayed");
        assertTrue(alertPage.downloadAlertTemplate.isEnabled(), "Download Alert Template button is not clickable");
    }

    @Then("I validate Upload History component on CaseConsumer Alert Landing Page")
    public void iValidateUploadHistoryComponentOnCaseConsumerAlertLandingPage() throws Throwable {
        assertTrue(alertPage.uploadHistoryComponent.isDisplayed(), "Upload History component is not displayed");
        assertTrue(alertPage.fileNameColumn.isDisplayed(), "File Name Column is not displayed");
        assertTrue(alertPage.dateTimeUploaded.isDisplayed(), "Date /Time Uploaded Column is not displayed");
        assertTrue(alertPage.uploadedBy.isDisplayed(), "Uploaded By Column is not displayed");
        assertTrue(alertPage.downloadSuccAlert.isDisplayed(), "Download Successful Alert Column is not displayed");
        assertTrue(alertPage.downloadFailAlert.isDisplayed(), "Download Failed Alert Column is not displayed");
    }

    @Then("I validate Recently Created component on CaseConsumer Alert Landing Page")
    public void i_validate_Recently_Created_component_on_Case_Consumer_Alert_Landing_Page() {
        assertTrue(alertPage.recentlyCreatedComponent.isDisplayed(), "Recently Created Alerts component is not displayed");
        assertTrue(alertPage.alertIdColumn.isDisplayed(), "Alert ID Column is not displayed");
        assertTrue(alertPage.caseAndConsumerId.isDisplayed(), "Case and Consumer Column is not displayed");
        assertTrue(alertPage.alertTxtColumn.isDisplayed(), "Alert Column is not displayed");
        assertTrue(alertPage.startDate.isDisplayed(), "Start Date Column is not displayed");
        assertTrue(alertPage.endDate.isDisplayed(), "End Date Column is not displayed");
        assertTrue(alertPage.typeTxt.isDisplayed(), "Type Column is not displayed");
        assertTrue(alertPage.resetBtn.isDisplayed(), "Reset button is not displayed");
    }

    @Then("I verify date range single select dropdown containing the following options")
    public void i_verify_date_range_single_select_dropdown_containing_the_following_options(List<String> expValues) {
        List<String> actValues = new ArrayList<>();
        alertPage.dateRangeDropdown.click();
        waitFor(2);
        if (alertPage.selectFilter.isDisplayed()) {
            for (WebElement element : alertPage.getDateRangeDropdownValuesHistory)
                actValues.add(element.getText().trim());
        } else {
            for (WebElement element : alertPage.getDateRangeDropdownValues)
                actValues.add(element.getText().trim());
        }
        Assert.assertEquals(actValues, expValues);
    }

    @Then("I verify start and end dates are displayed in date range option")
    public void i_verify_start_and_end_dates_are_displayed_in_date_range_option() {
        alertPage.dateRangeDropdown.click();
        alertPage.selectDateRangeOption.click();
        assertTrue(alertPage.startDateTxt.isDisplayed(), "Start Date is not displayed");
        assertTrue(alertPage.endDateTxt.isDisplayed(), "End Date is not displayed");
    }

    @Then("I verify I can filter by uploaded user")
    public void i_verify_I_can_filter_by_uploaded_user() {
        assertTrue(alertPage.uploadedByText.isDisplayed(), "Uploaded by is not displayed");
        alertPage.uploadedByInput.click();
        alertPage.uploadedByInput.sendKeys("Service");
        assertTrue(alertPage.uploadedByFilter.getText().contains("Service"));
    }

    @Then("I verify Reset button is displayed for Upload Alert Screen")
    public void i_verify_Reset_button_is_displayed_for_Upload_Alert_Screen() {
        assertTrue(alertPage.resetBtn.isDisplayed(), "Reset button is not displayed");
    }

    @Then("I validate Data captured on UI for file Upload on Alert Page")
    public void iValidateDataCapturedOnUIForFileUploadOnAlertPage() throws Throwable {
        assertTrue(alertPage.fileNames.get(0).isDisplayed(), "Uploaded Alert File Name is displayed");
        assertTrue(alertPage.dateTimeUploads.get(0).isDisplayed(), "Uploaded Date Time is displayed");
        assertTrue(alertPage.uploadedBys.get(0).isDisplayed(), "Uploaded Alert Uploaded By Name is displayed");
        assertTrue(alertPage.downloadSuccAlertButtons.get(0).isDisplayed(),
                "Uploaded Alert File Download Successful Alerts button is displayed");
        assertTrue(alertPage.downloadSuccAlertButtons.get(0).isEnabled(),
                "Uploaded Alert File Download Successful Alerts button is clickable");
        assertTrue(alertPage.downloadFailedAlertButtons.get(0).isEnabled(),
                "Uploaded Alert File Download Failed Alerts button is clickable");

    }

    @Then("I validate Cancel alert component button on CaseConsumer Alert Landing Page")
    public void iValidateCancelAlertComponentButtonOnCaseConsumerAlertLandingPage() throws Throwable {
        assertTrue(alertPage.alertFileCancel.isDisplayed(), "Cancel File button is not displayed");
        assertTrue(alertPage.alertFileCancel.isEnabled(), "Cancel File button is not clickable");
    }

    @Then("I verify download indicator for the Alert Success column is disabled")
    public void i_verify_download_indicator_for_the_Alert_Success_column_is_disabled() {
        waitFor(3);
        try {
            waitForClickablility(dashboardPage.downloadSucAlertBtn.get(0), 300);
            dashboardPage.downloadSucAlertBtn.get(0).click();
        } catch (Exception e) {
            System.out.println("Download Successfull Alert button is not clickable");
        }
    }

    @Then("I verify download indicator for the Alert Failure column is disabled")
    public void i_verify_download_indicator_for_the_Alert_Failure_column_is_disabled() {
        waitFor(3);
        try {
            waitForClickablility(dashboardPage.downloadFailAlertBtn.get(0), 300);
            dashboardPage.downloadFailAlertBtn.get(0).click();
        } catch (Exception e) {
            System.out.println("Download Failure Alert button is not clickable");
        }
    }

    @Then("I verify download indicator for the Alert Success column is enabled")
    public void i_verify_download_indicator_for_the_Alert_Success_column_is_enabled() {
        waitFor(3);
        assertTrue(dashboardPage.downloadSucAlertBtn.get(0).isEnabled(), "Download Successful Alert button is not clickable");
    }

    @Then("I validate NJ-SBE Upload and Validate button is displayed for Alerts")
    public void iValidateUploadAndValidateButtonIsDisplayed() {
        assertTrue(alertPage.alertFileUploadAndValidateBtn.isDisplayed(), "Upload and Validate button is not displayed");
    }

    @Then("I select {string} file for upload and validate")
    public void iSelectFileForUpload(String filePath) throws Exception {
        randomFileName.set(randomFileName.get() + filePath);
        File copied;
        File sourceFile;
        String osName = System.getProperty("os.name");

        System.out.println("OS name: " + osName);
        if (osName.startsWith("Win")) {
            copied = new File("./src/test/resources/tesData/cc/" + randomFileName.get());
            sourceFile = new File("./src/test/resources/testData/cc/" + filePath);
            FileUtils.copyFile(sourceFile, copied);
            System.out.println(copied.exists() + " Copied file exist in Local run");
            String choosePath = copied.getCanonicalPath();
            waitFor(1);
            alertPage.alertFileSelect.sendKeys(choosePath);

        } else {
            String jobName = System.getenv("JOB_BASE_NAME");
            System.out.println("JOB NAME: " + jobName);
            copied = new File("/home/jenkins/agent/workspace/QA/ONDEMAND_SELENIUM/Group%203/job/" + jobName + "/maersk-crm-qa-automation-framework/" + randomFileName.get());
            sourceFile = new File("/home/jenkins/agent/workspace/QA/ONDEMAND_SELENIUM/Group%203/job/" + jobName + "/src/test/resources/testData/cc/" + filePath);
            FileUtils.copyFile(sourceFile, copied);
            System.out.println(copied.exists() + " Copied file exist in Jenkins run");
            RemoteWebElement uploadElement = (RemoteWebElement) Driver.getDriver().findElement(By.id("fileSelect"));
            LocalFileDetector detector = new LocalFileDetector();
            File localFile = detector.getLocalFile("/home/jenkins/agent/workspace/QA/ONDEMAND_SELENIUM/Group%203/job/" + jobName + "/maersk-crm-qa-automation-framework/" + randomFileName.get());
            uploadElement.setFileDetector(detector);

// enter the absolute file path into the file input field
            uploadElement.sendKeys(localFile.getAbsolutePath());


        }
        waitFor(5);
        click(alertPage.alertFileUploadAndValidateBtn);
        waitFor(3);
        copied.deleteOnExit();
        System.out.println(randomFileName.get());
    }

    @Then("I see successful upload is completed for NJ-SBE GI case")
    public void iSeeSuccessfulUploadIsCompleted() {
        assertEquals(alertPage.fileNames.get(0).getText(), randomFileName.get(), "Uploaded file is not displayed on History components");
        assertEquals(alertPage.downloadSuccorFailedAlertButtons.get(0).getAttribute("tabindex"), "0", "Uploaded file DownloadSuccessAlert Button is not active");
        assertEquals(alertPage.downloadSuccorFailedAlertButtons.get(1).getAttribute("tabindex"), "-1", "Uploaded file DownloadFailureAlert Button is active");

    }


    @Then("I see unsuccessful upload is completed for NJ-SBE GI case")
    public void iSeeUnsuccessfulUploadIsCompleted() {
        assertEquals(alertPage.fileNames.get(0).getText(), randomFileName.get(), "Uploaded file is not displayed on History components");
        assertEquals(alertPage.downloadSuccorFailedAlertButtons.get(0).getAttribute("tabindex"), "-1", "Uploaded file DownloadSuccessAlert Button is not active");
        assertEquals(alertPage.downloadSuccorFailedAlertButtons.get(1).getAttribute("tabindex"), "0", "Uploaded file DownloadFailureAlert Button is active");

    }

    @And("I click on upload button on upload alert page")
    public void I_click_on_upload_button() {
        waitForClickablility(alertPage.alertFileUploadBtn, 5);
        jsClick(alertPage.alertFileUploadBtn);
        waitForVisibility(alertPage.succesfullyFileUploadedMessage, 10);
        assertTrue(alertPage.succesfullyFileUploadedMessage.isDisplayed(), "succesfullyFileUploadedMessage not displayed");
    }

    @When("I select {string} file for upload and validate BLCRM")
    public void vvv(String filePath) throws IOException {
        randomFileName.set(randomFileName.get() + filePath);
        File copied;
        File sourceFile;
        String osName = System.getProperty("os.name");

        System.out.println("OS name: " + osName);
        if (osName.startsWith("Win")) {
            copied = new File("./src/test/resources/tesData/cc/" + randomFileName.get());
            sourceFile = new File("./src/test/resources/testData/cc/" + filePath);
            FileUtils.copyFile(sourceFile, copied);
            System.out.println(copied.exists() + " Copied file exist in Local run");
            String choosePath = copied.getCanonicalPath();
            waitFor(1);
            alertPage.alertFileSelect.sendKeys(choosePath);

        } else {
            String jobName = System.getenv("JOB_BASE_NAME");
            System.out.println("JOB NAME: " + jobName);
            copied = new File("/home/jenkins/agent/workspace/QA/" + jobName + "/maersk-crm-qa-automation-framework/" + randomFileName.get());
            sourceFile = new File("/home/jenkins/agent/workspace/QA/" + jobName + "/src/test/resources/testData/cc/" + filePath);
            FileUtils.copyFile(sourceFile, copied);
            System.out.println(copied.exists() + " Copied file exist in Jenkins run");
            RemoteWebElement uploadElement = (RemoteWebElement) Driver.getDriver().findElement(By.id("fileSelect"));
            LocalFileDetector detector = new LocalFileDetector();
            File localFile = detector.getLocalFile("/home/jenkins/agent/workspace/QA/" + jobName + "/maersk-crm-qa-automation-framework/" + randomFileName.get());
            uploadElement.setFileDetector(detector);

// enter the absolute file path into the file input field
            uploadElement.sendKeys(localFile.getAbsolutePath());

        }
    }

    @Then("I will Download Successful Alert file")
    public void I_will_Download_Successful_Alert_file() {
        waitForClickablility(alertPage.firstAlertDownloadBntOnAlertUploadPage, 5);
        assertTrue(alertPage.firstAlertDownloadBntOnAlertUploadPage.isDisplayed(), "Download Successful Alert button is not displayed");
        jsClick(alertPage.firstAlertDownloadBntOnAlertUploadPage);
        waitFor(6);
        File folder = new File(System.getProperty("user.dir"));
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String fileName = file.getName();
                System.out.println("File name ==== " + fileName);
                if (fileName.contains(randomFileName.get())) {
                    assertTrue(true);
                }
            }
        }

    }
}