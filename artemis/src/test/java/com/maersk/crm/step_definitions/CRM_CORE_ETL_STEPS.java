package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMApplicationTrackingPage;
import com.maersk.crm.pages.crm.CRMContactHistoryPage;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMManualContactRecordSearchPage;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

//import static com.maersk.crm.utilities.ApiBaseClass.contactRecord;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;


public class CRM_CORE_ETL_STEPS extends CRMUtilities implements ApiBase {
    private CRM_CORE_ETL_STEP_DEF coreETLStepDefs = new CRM_CORE_ETL_STEP_DEF();
    private final ThreadLocal<ApiTestDataUtil> testDataUtil = ThreadLocal.withInitial(ApiTestDataUtil::getApiTestDataUtilThreadLocal);
    final ThreadLocal<String> etlPhoneNumber = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> formatedPhoneNumber =ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> fileName = ThreadLocal.withInitial(String::new);
    CRMManualContactRecordSearchPage manualContactRecordSearchPage = new CRMManualContactRecordSearchPage();
    CRMContactHistoryPage contactHistory = new CRMContactHistoryPage();
    public static final ThreadLocal<List<String>> data = new ThreadLocal<>();
    public static final ThreadLocal<List<String>> outcome= new ThreadLocal<>();
    CRMApplicationTrackingPage crmApplicationTrackingPage = new CRMApplicationTrackingPage();
    CRMContactRecordUIPage contactRecordUIPage = new CRMContactRecordUIPage();




    @Given("I have prepared {string} csv file by changing phone number")
    public void i_have_prepared_csv_file_by_changing_phone_number(String string) throws IOException {

        Path source = Paths.get("src/test/resources/testData/CORE_ETL/MAXNJSBEOCM_Response.csv");
        Date dNow = new Date( );
        SimpleDateFormat timeStamp = new SimpleDateFormat ("yyyyMMdd");

       fileName.set("MAXNJSBEOCM_Response_" + timeStamp.format(dNow) +".csv");
       Files.move(source, source.resolveSibling(fileName.get()));

        String fileToUpdate = "src/test/resources/testData/CORE_ETL/" + fileName.get();

        etlPhoneNumber.set(testDataUtil.get().genRandomPhoneNumber());
        formatedPhoneNumber.set(etlPhoneNumber.get().substring(0, 3) + "-" + etlPhoneNumber.get().substring(3, 6) + "-" + etlPhoneNumber.get().substring(6));
        System.out.printf("new phone number " + formatedPhoneNumber.get());
        int row = 1;
        int col = 5;
        CSVUtil.updateCSV(fileToUpdate, formatedPhoneNumber.get(), row, col);
    }

    @Given("I upload the ETL NJ_SBE {string} file to aws bucket {string} folder {string}")
    public void i_upload_the_ETL_NJ_SBE_file_to_aws_bucket_folder(String fileType, String bucketName, String S3folder)throws IOException {

        coreETLStepDefs.uploadFile(fileType, bucketName, S3folder, "src/test/resources/testData/CORE_ETL/" + fileName.get());
        cleanupFileNameForNextExecution();
    }

    @When("I run the {string} in project {string} for CRM Core")
    public void i_run_the_in_project_for_CRM_Core(String jobName, String projectName) {
        coreETLStepDefs.executeJob(jobName, projectName);
    }

    @When("I search for an ETL created contact record by PhoneNumber under Advanced Search")
    public void i_search_for_an_ETL_created_contact_record_by_PhoneNumber_under_Advanced_Search() {
        System.out.println("phoneNum " + etlPhoneNumber.get());
        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.manualSearchChannel, "Phone");
        manualContactRecordSearchPage.phoneNumberTextBox.sendKeys(etlPhoneNumber.get());
        System.out.println("after phone number input");
        manualContactRecordSearchPage.searchButton.click();
        System.out.println("search button clicked");
    }

    @When("I verify phone number is displayed as per ETL file")
    public void i_verify_phone_number_is_displayed_as_per_ETL_file() {
        String displayedPhoneNumberFormat = "("+ formatedPhoneNumber.get().substring(0,3) + ") "+ formatedPhoneNumber.get().substring(4);
        System.out.println("formated phone " + displayedPhoneNumberFormat);
        assertEquals(contactHistory.etlPhoneNumberFromFile.getText(), displayedPhoneNumberFormat);
    }

    public void cleanupFileNameForNextExecution () throws IOException {
        Path source = Paths.get("src/test/resources/testData/CORE_ETL/" +fileName.get());
        String clenupfileName = "MAXNJSBEOCM_Response"+".csv";
        Files.move(source, source.resolveSibling(clenupfileName));
    }

    @Given("I have prepared callCampaignJob csv file for IN-EB")
    public void i_have_prepared_csv_file_for_in_eb() throws IOException {

        Path source = Paths.get("src/test/resources/testData/CORE_ETL/MAXINEBOCM_Response.csv");
        Date dNow = new Date( );
        SimpleDateFormat timeStamp = new SimpleDateFormat ("yyyyMMdd");

        fileName.set("MAXINEBOCM_Response_" + timeStamp.format(dNow) +".csv");
        Files.move(source, source.resolveSibling(fileName.get()));

        String fileToUpdate = "src/test/resources/testData/CORE_ETL/" + fileName.get();

        synchronized (data){
            data.set(new ArrayList<>());
        }
        synchronized (outcome){
            outcome.set(Arrays.asList("Did Not Reach/No Voicemail", "Invalid Phone Number",
                    "Did Not Reach/No Voicemail", "DNC List", "Invalid"));
        }

        for (int i = 0; i < 5; i++) {
            etlPhoneNumber.set(testDataUtil.get().genRandomPhoneNumber());
            data.get().add(etlPhoneNumber.get());
            System.out.printf("phone number " + etlPhoneNumber);
            int row = i + 1;
            int col = 2;
            CSVUtil.updateCSV(fileToUpdate, etlPhoneNumber.get(), row, col);
        }
    }

    @Given("I upload the ETL IN-EB {string} file to aws bucket {string} folder {string}")
    public void i_upload_the_ETL_IN_EB_file_to_aws_bucket_folder(String fileType, String bucketName, String S3folder) throws IOException {
        coreETLStepDefs.uploadFile(fileType, bucketName, S3folder, fileName.get());
        cleanupFileNameForNextExecutionInEb();
    }

    public void cleanupFileNameForNextExecutionInEb () throws IOException {
        Path source = Paths.get("src/test/resources/testData/CORE_ETL/" +fileName.get());
        String clenupfileName = "MAXINEBOCM_Response"+".csv";
        Files.move(source, source.resolveSibling(clenupfileName));
    }

    @When("I search for an ETL created contact record by PhoneNumber under Advanced Search and verify valid data processed")
    public void i_search_for_an_ETL_IN_EB_created_contact_record_by_PhoneNumber_under_Advanced_Search_and_verify() {
        for (int i = 0; i < data.get().size(); i++) {
            System.out.println("phoneNum " + data.get().get(i));
            manualContactRecordSearchPage.phoneNumberTextBox.clear();
            manualContactRecordSearchPage.phoneNumberTextBox.sendKeys(data.get().get(i));
            manualContactRecordSearchPage.searchButton.click();
            waitFor(8);
            if(outcome.get().get(i).equals("Invalid")){
                assertTrue(contactRecordUIPage.noRecordsAvailableMessage.isDisplayed(), "Records found for invalid phone #");
                return;
            }
            try {
                Driver.getDriver().findElement(By.xpath("(//td[3])[1]")).click();
                waitFor(7);
                Driver.getDriver().findElement(By.xpath("//h5[text()='CONTACT RECORD INFORMATION']")).isDisplayed();
            } catch (NoSuchElementException e) {
                contactRecordUIPage.firstContactID.click();
            }
            waitFor(3);
            assertTrue(contactRecordUIPage.getOutcomeOfContactValue.getText().equals(outcome.get().get(i)), "Outcome of contact value is incorrect");
            crmApplicationTrackingPage.backArrow.click();
            waitFor(3);
        }
    }
}
