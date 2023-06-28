package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMAddAuthorizedRepresentativePage;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMDemographicMemberPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.maersk.crm.utilities.BrowserUtils.*;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CRM_AuthorizedRepresentativeStepDef extends CRMUtilities implements ApiBase {

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMAddAuthorizedRepresentativePage AuthorizedRep = new CRMAddAuthorizedRepresentativePage();
    CRMDemographicMemberPage memberPage = new CRMDemographicMemberPage();
    CRM_ContactRecordUIStepDef contactRecordUIStepDef = new CRM_ContactRecordUIStepDef();

    private String apiconsumerFirstName = "fnar" + RandomStringUtils.random(5, true, false);
    private String apiconsumerLastName = "lnar" + RandomStringUtils.random(5, true, false);
    private String apiConsumerSSN = RandomStringUtils.random(9, false, true);
    private List<String> firstrowData = new ArrayList<String>();
    private String consumerIdAuthorizedRepresentative = "";
    private String consumerARname = "";
    ThreadLocal<Map<String, Object>> newAR = ThreadLocal.withInitial(() -> getNewTestData2());
    public ThreadLocal<String> ARfirstName = ThreadLocal.withInitial(String::new);
    public ThreadLocal<String> ARlastName = ThreadLocal.withInitial(String::new);
    public ThreadLocal<String> fullName = ThreadLocal.withInitial(String::new);
    public ThreadLocal<String> ARmiddleName = ThreadLocal.withInitial(String::new);

    @When("I am a user who has access to add authorized representative")
    public void userWhoHasAccessToAddAuthorizedRepresentative() {
        contactRecord.firstName.sendKeys("Gala");
        contactRecord.searchButton.click();
        waitForVisibility(contactRecord.consumerSearchFirstRecord, 10);
        contactRecord.consumerSearchFirstRecord.click();
        waitFor(5);
        AuthorizedRep.arAddButton.click();
        waitFor(3);
    }

    @Then("I am able to add the following Authorized Representative Information fields")
    public void addAuthorizedRepresentativeInformation() {
        waitFor(5);
        try {
            AuthorizedRep.arAddButton.click();
        }catch(Exception e){
            System.out.println("Already clicked");
        }
       // scrollUpUsingActions(2);
        waitFor(2);
        selectDropDown(AuthorizedRep.arAccessType, "Full Access");
        AuthorizedRep.arStartDate.sendKeys("11/01/2019");
        AuthorizedRep.arEndDate.sendKeys("12/01/2019");
        selectDropDown(AuthorizedRep.arReceiveCorrespondence, "Yes");
        AuthorizedRep.arFirstName.sendKeys(apiconsumerFirstName);
        AuthorizedRep.arMiddleName.sendKeys("H");
        AuthorizedRep.arLastName.sendKeys(apiconsumerLastName);
        AuthorizedRep.arDOB.sendKeys("03/23/1979");
        selectDropDown(AuthorizedRep.arGender, "Male");
        AuthorizedRep.arSSN.sendKeys(apiConsumerSSN);
        waitFor(4);
        AuthorizedRep.arSaveButton.click();
        waitFor(1);
        contactRecord.cancelWarningContinueButton.click();
        waitForVisibility(AuthorizedRep.arModalSuccessMessage, 2);
        String successMessage = AuthorizedRep.arModalSuccessMessage.getText();
        System.out.println("Success Message: " + successMessage);
        assertEquals(successMessage, "SUCCESS MESSAGE");
    }

    @Then("I am able to validate the required Authorized Representative Information fields")
    public void validateTheRequiredAuthorizedRepresentativeFields() {
        scrollUpUsingActions(2);
        waitFor(2);
        //   AuthorizedRep.arPreferredLang.click();
        waitFor(2);
        //   AuthorizedRep.arPreferredLangEmpty.click();
        waitFor(2);
        AuthorizedRep.arSaveButton.click();
        waitFor(2);
        isReqErrorMessage(AuthorizedRep.arAccessTypeError, "ACCESS TYPE");
        isReqErrorMessage(AuthorizedRep.arStartDateError, "START DATE");
        isReqErrorMessage(AuthorizedRep.arFirstNameError, "FIRST NAME");
        isReqErrorMessage(AuthorizedRep.arLastNameError, "LAST NAME");
        isReqErrorMessage(AuthorizedRep.arDOBError, "DOB");
        markedMandatory(AuthorizedRep.arWrittenLanguage);
        markedMandatory(AuthorizedRep.arSpokenLanguage);
        waitFor(2);


    }

    @Then("I am able to validate the format of Authorized Representative Information fields")
    public void validateTheFormatOfAuthorizedRepresentativeFields() {
        String actual = "";
        int fieldLength = 15;
        String sentText = createTextString(fieldLength * 4);
        clearAndFillText(AuthorizedRep.arFirstName, sentText);
        waitFor(1);
        actual = AuthorizedRep.arFirstName.getAttribute("value");
        System.out.println(actual.length());
        assertTrue(actual.length() <= fieldLength);
    }

    @Then("I select the Cancel Button and I will get the following Warning Message {string}")
    public void selectTheCancelButtonForAuthorizedRepresentative(String message) {
        selectDropDown(AuthorizedRep.arAccessType, "Full Access");
        AuthorizedRep.arFirstName.sendKeys(apiconsumerFirstName);
        AuthorizedRep.arCancelButton.click();
        waitFor(2);
        assertTrue(AuthorizedRep.arModalWarningMessage.getText().equals(message), "Actual message is = " +AuthorizedRep.arModalWarningMessage.getText());
        System.out.println("will click on continue");
        waitFor(2);
        AuthorizedRep.arModalContButton.click();
        waitFor(2);
        scrollToElement(AuthorizedRep.arAddButton);
        System.out.println("will click on add");
        jsClick(AuthorizedRep.arAddButton);
        waitFor(1);
        System.out.println("clicked on add");
        AuthorizedRep.arCancelButton.click();
        assertTrue(AuthorizedRep.arAddButton.isDisplayed(), "Not navigated back to Demographic Info screen");
    }

    @Then("I see {string} field accept only {int} characters in authorized representative")
    public void fieldAcceptOnlyCharactersForAuthorizedRepresentative(String field, int stringLength) {
        scrollUpUsingActions(2);
        waitFor(2);
        String actual = "";
        String sentText = createTextString(stringLength * 4);
        switch (field) {
            case "firstName":
                clearAndFillText(AuthorizedRep.arFirstName, sentText);
                waitFor(1);
                actual = AuthorizedRep.arFirstName.getAttribute("value");
                break;
            case "middleName":
                clearAndFillText(AuthorizedRep.arMiddleName, sentText);
                waitFor(1);
                actual = AuthorizedRep.arMiddleName.getAttribute("value");
                break;
            case "lastName":
                clearAndFillText(AuthorizedRep.arLastName, sentText);
                waitFor(1);
                actual = AuthorizedRep.arLastName.getAttribute("value");
                break;
        }
        assertTrue(actual.length() <= stringLength);
    }

    @Then("I see {string} field accept only {string} format")
    public void fieldAcceptOnlyFormatForAuthorizedRepresentative(String field, String fieldFormat) {
        String actual = "";
        String actualFormat = "";
        String sentText = "110120192019";
        switch (field) {
            case "startDate":
                clearAndFillText(AuthorizedRep.arStartDate, sentText);
                waitFor(1);
                actual = AuthorizedRep.arStartDate.getAttribute("value");
                actualFormat = actual.replaceAll("[0-9]", "*"); //refactored
                break;
            case "endDate":
                clearAndFillText(AuthorizedRep.arEndDate, sentText);
                waitFor(1);
                actual = AuthorizedRep.arEndDate.getAttribute("value");
                actualFormat = actual.replaceAll("[0-9]", "*"); //refactored
                break;
            case "dob":
                clearAndFillText(AuthorizedRep.arDOB, sentText);
                waitFor(1);
                actual = AuthorizedRep.arDOB.getAttribute("value");
                actualFormat = actual.replaceAll("[0-9]", "*"); //refactored
                break;
        }
        assertTrue(actualFormat.startsWith(fieldFormat));
    }

    @Then("I see SSN {int} digits value")
    public void seeSSNDigitsValueOfAuthorizedRepresentative(int ssnLength) {
        AuthorizedRep.arSSN.sendKeys("573845738999");
        String ssn = AuthorizedRep.arSSN.getAttribute("value");
        ssn = ssn.replaceAll("-", ""); //refactored
        assertTrue(ssn.matches("[0-9]*"));
        assertTrue(ssn.length() == ssnLength);
    }

    @And("I search and navigate to consumer profile for Authorized Representative")
    public void searchAndNavigateToConsumerProfile() {
        waitFor(3);
        contactRecord.lastName.click();
        contactRecord.firstName.sendKeys("Martin");
        waitFor(3);
        contactRecord.searchButton.click();
        waitForVisibility(contactRecord.consumerSearchFirstRecord, 20);
        contactRecord.consumerSearchFirstRecord.click();
        waitFor(2);
        scrollDownUsingActions(4);
        waitFor(2);
        AuthorizedRep.arAddButton.click();
        waitFor(5);
    }

    @Then("I verify the AR Status in UI based on {string} and {string}")
    public void verifyTheAuthorizedRepresentativeStatus(String startDate, String endDate) throws ParseException {
        if (startDate.equals("Future"))
            startDate = getDateFuture("startDate");
        if (endDate.equals("Future"))
            endDate = getDateFuture("endDate");

        addARWithStartEndDate(startDate, endDate);
        validateStatus();
    }

    @When("I am a user who has access to view authorized representative")
    public void userWhoHasAccessToViewAuthorizedRepresentative() {
        contactRecord.firstName.sendKeys("Gala");
        contactRecord.searchButton.click();
        waitForVisibility(contactRecord.consumerSearchFirstRecord, 10);
        contactRecord.consumerSearchFirstRecord.click();
        waitFor(3);
        hover(AuthorizedRep.arView);
        waitFor(2);
        AuthorizedRep.arView.click();
        waitFor(5);
    }

    @Then("I verify the age of Authorized Representative")
    public void verifyTheAgeOfAuthorizedRepresentative() {
        AuthorizedRep.unmaskDOB.click();
        String arDOB = AuthorizedRep.arDOB.getAttribute("value");
        int arAge = Integer.parseInt(AuthorizedRep.arAge.getAttribute("value"));
        int actualAge = calculateAge(arDOB);
        assertEquals(arAge, actualAge);
    }

    @Then("I capture the updated information of Authorized Representative")
    public void captureTheUpdatedInformationOfAuthorizedRepresentative() {
        String source = AuthorizedRep.arSource.getText();
        String createdBy = AuthorizedRep.arCreatedBy.getText();
        String createdOn = AuthorizedRep.arCreatedOn.getText();
        System.out.println(source);
        System.out.println(createdBy);
        System.out.println(createdOn);
    }

    private void addARWithStartEndDate(String startDate, String endDate) {
        scrollUpUsingActions(2);
        waitFor(2);
        selectDropDown(AuthorizedRep.arAccessType, "Full Access");
        AuthorizedRep.arStartDate.sendKeys(startDate);
        AuthorizedRep.arEndDate.sendKeys(endDate);
        AuthorizedRep.arFirstName.sendKeys(apiconsumerFirstName);
        AuthorizedRep.arMiddleName.sendKeys("H");
        AuthorizedRep.arLastName.sendKeys(apiconsumerLastName);
        AuthorizedRep.arDOB.sendKeys("03/23/1979");
        selectDropDown(AuthorizedRep.arGender, "Male");
        AuthorizedRep.arSSN.sendKeys(apiConsumerSSN);
        selectDropDown(AuthorizedRep.arReceiveCorrespondence,"Yes");
        waitFor(2);

        AuthorizedRep.arSaveButton.click();
        waitFor(2);
        //contactRecordUIStepDef.i_click_on_continue_button_on_warning_message();
        AuthorizedRep.arModalContButton.click();
        waitForVisibility(AuthorizedRep.arModalSuccessMessage, 2);
        String successMessage = AuthorizedRep.arModalSuccessMessage.getText();
        System.out.println("Success Message: " + successMessage);
        assertEquals(successMessage, "SUCCESS MESSAGE");
    }

    private void validateStatus() throws ParseException {
        waitFor(3);
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String curDate = sdf.format(currentDate);
        Date curDateFormatted = sdf.parse(curDate);
        scrollDownUsingActions(2);
        waitFor(4);
        hover(AuthorizedRep.consumerARStatus);
        waitFor(1);
        String startDate = getHoverText().get("START DATE");
        System.out.println("start Date from UI:" + startDate);
        Date startDateFormatted = sdf.parse(startDate);
        String endDate = getHoverText().get("END DATE");
        System.out.println("end Date from UI:" + endDate);
        String status = AuthorizedRep.consumerARStatus.getText();
        System.out.println("status:" + status);

        if (!endDate.isEmpty()) {
            Date endDateFormatted = sdf.parse(endDate);

            if (startDateFormatted.compareTo(curDateFormatted) > 0) {
                assertEquals(status, "FUTURE");
            } else if (startDateFormatted.compareTo(curDateFormatted) < 0) {
                assertEquals(status, "ACTIVE");
            } else if (endDateFormatted.compareTo(curDateFormatted) > 0) {
                assertEquals(status, "INACTIVE");
            }
        } else {
            assertEquals(status, "ACTIVE");
        }
    }

    private String getDateFuture(String dateType) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        if (dateType.equals("startDate"))
            cal.add(Calendar.DATE, 2);
        else if (dateType.equals("endDate"))
            cal.add(Calendar.DATE, 10);
        String dateFuture = dateFormat.format(cal.getTime());
        System.out.println(dateType + " Future: " + dateFuture);

        return dateFuture;
    }

    List<String> getFirstRowData() {
        List<String> cellData = new ArrayList<String>();
        for (int temp = 0; temp < 8; temp++) {
            cellData.add(Driver.getDriver().findElements(AuthorizedRep.ARtablecells).get(temp).getText());
        }
        System.out.println(cellData);
        return cellData;
    }

    @When("I click on existing Authorized Representative Record")
    public void i_click_on_existing_Authorized_Representative_Record() {
        memberPage.authorizedRepresLabel.isDisplayed();
//        List<String> rowdata = new ArrayList<String>();
        firstrowData = getFirstRowData();
        consumerIdAuthorizedRepresentative = Driver.getDriver().findElements(AuthorizedRep.ARtableRows).get(1).findElement(By.xpath("./td[2]")).getText();
        Driver.getDriver().findElements(AuthorizedRep.ARtableRows).get(1).click();
        waitFor(5);
    }

    @Then("I verify consumer's correspondence preference based on {string}")
    public void i_verify_consumer_s_correspondence_preference_based_on(String preference) {
        waitFor(3);
        Assert.assertTrue(AuthorizedRep.correspondencePreferenceDropdownOptions.isDisplayed(), "Correspondence preference dropdown isnt displayed");
        if (preference.equals("nonselected")) {
            Assert.assertFalse(AuthorizedRep.correspondencePreferenceDropdownOptions.getText().contains("Paperless"), "Correspondence Preference isnt selected");
        } else if (preference.equals("selected")) {
            Assert.assertTrue(AuthorizedRep.correspondencePreferenceDropdownOptions.getText().contains("Paperless"), "Correspondence Preference isnt selected");
        }

    }

    @When("I populate consumer's correspondence preference as {string} from multi select")
    public void i_populate_consumer_s_correspondence_preference_as_from_multi_select(String preference) {
        System.out.println("AR first name in UI: " + ARfirstName.get());
        System.out.println("AR last name in UI: " + ARlastName.get());
        System.out.println("value" + AuthorizedRep.correspondencePreferenceDropdownOptions.getText());
//        scrollDownUsingActions(2);
        waitFor(5);
        //     waitForVisibility(AuthorizedRep.correspondencePreferenceDropdownOptions,5);
        //   try {
        if (AuthorizedRep.correspondencePreferenceDropdownOptions.getText().contains("Paperless")) {
            i_deselect_consumer_s_correspondence_preference();
            selectDropDown(AuthorizedRep.correspondencePreferenceDropdownOptions, preference);
//            AuthorizedRep.paperlessOption.sendKeys(Keys.ALT);
        } else {
            waitFor(3);
            selectDropDown(AuthorizedRep.correspondencePreferenceDropdownOptions, preference);
//            AuthorizedRep.paperlessOption.sendKeys(Keys.ALT);
        }
        //     }catch(Exception exception){
        //       AuthorizedRep.paperlessOption.sendKeys(Keys.ESCAPE); waitFor(2);
        //       selectDropDown(AuthorizedRep.correspondencePreferenceDropdownOptions, preference);
        //      AuthorizedRep.paperlessOption.sendKeys(Keys.ALT);
        //     }
        waitFor(2);
        assertTrue(AuthorizedRep.correspondencePreferenceDropdownOptions.isDisplayed(), "Consumer's Correspondence Preference dropdown paperless isnt selected");
    }

    @Then("I verify consumer's updated correspondence preference")
    public void i_verify_consumer_s_updated_correspondence_preference() {
        waitFor(3);
        Assert.assertTrue(AuthorizedRep.correspondencePreferenceDropdownOptions.isDisplayed(), "Correspondence preference dropdown isnt displayed");
        Assert.assertTrue(AuthorizedRep.correspondencePreferenceDropdownOptions.getText().contains("Paperless"), "Correspondence Preference hasnt a value");
    }

    @Then("I verify that correspondence preference isnt marked mandatory")
    public void i_verify_that_correspondence_preference_isnt_marked_mandatory() {
        Assert.assertFalse(markedMandatory(AuthorizedRep.correspondencePreferenceDropdownOptions));
    }

    @Then("I verify correspondence preference options for baseline")
    public void i_verify_correspondence_preference_options_for_baseline(List<String> options) {
        List<String> actualValues = new ArrayList<String>();
        ArrayList<String> expValues = new ArrayList<String>();
        String[] items;
        for (String option : options) {
           // selectDropDown(AuthorizedRep.correspondencePreferenceDropdownOptions, option);
            selectFromMultiSelectDropDown(AuthorizedRep.correspondencePreferenceDropdownOptions, option);
            expValues.add(option);
            waitFor(5);
        }
        for (WebElement preference : AuthorizedRep.correspondencePreferenceDropdown) {
            items = preference.getText().split(",");
            actualValues = Arrays.asList(items);
        }

        Collections.sort(actualValues);
        Collections.sort(expValues);
        System.out.println("Expected values: " + expValues);
        System.out.println("Actual values: " + actualValues);
        assertEquals(actualValues, expValues, "Correspondence preference options didnt match");
    }

    @Then("I click on Save button for Authorized Representative")
    public void i_click_on_Save_button_for_Authorized_Representative() {
        jsClick(AuthorizedRep.arSaveButton);
        waitFor(3);
        jsClick(AuthorizedRep.arModalContButton);
        waitFor(2);
    }

    @When("I choose {string} from access Type dropdown")
    public void i_choose_from_access_Type_dropdown(String type) {
        scrollUpUsingActions(3);
        waitFor(5);
    }

    @When("I deselect consumer's correspondence preference")
    public void i_deselect_consumer_s_correspondence_preference() {
        AuthorizedRep.correspondencePreferenceDropdownOptions.click();
        waitFor(2);
        AuthorizedRep.paperlessOption.click();
//        waitFor(2);
//        AuthorizedRep.paperlessOption.sendKeys(Keys.ESCAPE);
//        waitFor(2);
        //    AuthorizedRep.paperlessOption.sendKeys(Keys.ALT);
        waitFor(2);
    }

    @Then("I populate Authorized Representative field for new record")
    public void i_populate_Authorized_Representative_field_for_new_record() {
        scrollUpUsingActions(2);
        waitFor(2);
        selectDropDown(AuthorizedRep.arAccessType, "Full Access");
        clearAndFillText(AuthorizedRep.arStartDate, ("01/01/2019"));
        clearAndFillText(AuthorizedRep.arEndDate, ("02/01/2020"));
        System.out.println(newAR.get().get("name").toString() + " " + newAR.get().get("surname").toString());

        clearAndFillText(AuthorizedRep.arFirstName, (newAR.get().get("name").toString()));
        ARfirstName.set(newAR.get().get("name").toString());
        System.out.println("AR first name in UI: " + ARfirstName.get());

        clearAndFillText(AuthorizedRep.arLastName, (newAR.get().get("surname").toString()));
        ARlastName.set(newAR.get().get("surname").toString());
        System.out.println("AR last name in UI: " + ARlastName.get());

        clearAndFillText(AuthorizedRep.arMiddleName, (newAR.get().get("name").toString().substring(1)));
        ARmiddleName.set(newAR.get().get("name").toString().substring(1).toUpperCase());

        fullName.set(ARfirstName.get() + " " + ARmiddleName.get() + " " + ARlastName.get());

        clearAndFillText(AuthorizedRep.arDOB, (newAR.get().get("birthday").toString()));
        selectRandomDropDownOption(AuthorizedRep.arGender);
        clearAndFillText(AuthorizedRep.arSSN, apiConsumerSSN);
        selectDropDown(AuthorizedRep.arReceiveCorrespondence,"Yes");

        waitFor(3);

    }

    @And("I update authorized representative's middle name")
    public void i_update_authorized_representatives_middle_name() {
        scrollUpUsingActions(2);
        clearAndFillText(AuthorizedRep.arMiddleName, getRandomString(1));
        scrollDownUsingActions(3);

    }

    @Then("New Authorized Representative should be added to the PI list")
    public void newAuthorizedRepresentativeShouldBeAddedToThePIList() {
        waitFor(3);
        Assert.assertTrue(CRM_PrimaryIndividualStepDef.fullName.get().contains("ADASDIOAJdsSJDK DASDIOAasHISJDA"));
    }

    @Then("I am able to see AR information In View Only Format")
    public void iAmAbleToSeeARInformationInViewOnlyFormat() {
        if (isElementDisplayed(Driver.getDriver().findElement(By.xpath("(//h5)[3]/parent::div/div[2]/div/table/tbody/tr/td[2]")))) {
            String consumerID = Driver.getDriver().findElement(By.xpath("(//h5)[3]/parent::div/div[2]/div/table/tbody/tr/td[2]")).getText();
            for (char c : consumerID.toCharArray()) {
                if (!Character.isDigit(c)) {
                    Assert.assertTrue(false, "ConsumerId is not numeric");
                }
            }
            String fullName = Driver.getDriver().findElement(By.xpath("(//h5)[3]/parent::div/div[2]/div/table/tbody/tr/td[3]")).getText();
            String firstLast[] = fullName.split(" ");
            for (int i = 0; i <= firstLast.length - 1; i++) {
                Assert.assertTrue(firstLast[i].length() <= 15);
            }
            click(Driver.getDriver().findElement(By.xpath("(//h5)[3]/parent::div/div[2]/div/table/thead/tr/th[4]/span")));
            waitFor(2);
            String dob = Driver.getDriver().findElement(By.xpath("(//h5)[3]/parent::div/div[2]/div/table/tbody/tr/td[4]")).getText();
            assertTrue(dob.matches("\\d{2}\\/\\d{2}\\/\\d{4}"));
            String ageGender[] = Driver.getDriver().findElement(By.xpath("(//h5)[3]/parent::div/div[2]/div/table/tbody/tr/td[5]")).getText().split("/");
            for (char c : ageGender[0].trim().toCharArray()) {
                if (!Character.isDigit(c)) {
                    Assert.assertTrue(false, "Age is not numeric");
                }
            }
            assertTrue(ageGender[1].trim().matches("[M]|[N]|[F]|[O]|[U]"));
            assertTrue(Driver.getDriver().findElement(By.xpath("(//h5)[3]/parent::div/div[2]/div/table/tbody/tr/td[6]")).getText().matches("...-..-...."));
            click(Driver.getDriver().findElement(By.xpath("(//h5)[3]/parent::div/div[2]/div/table/thead/tr/th[6]/span")));
            String ssn = Driver.getDriver().findElement(By.xpath("((//*[contains(text(), 'AUTHORIZED REPRESENTATIVE')]/following-sibling::div[@class='table-responsive']/div/table/tbody/tr)[1]/td)[6]")).getText().replaceAll("-", "");
            for (char c : ssn.toCharArray()) {
                if (!Character.isDigit(c)) {
                    Assert.assertTrue(false, "SSN is not numeric");
                }
            }
            String status = Driver.getDriver().findElement(By.xpath("//*[contains(text(),'AUTHORIZED REPRESENTATIVE')]/following::tbody/tr[1]/td[7]")).getText();
            assertTrue(status.equals("ACTIVE") || status.equals("FUTURE") || status.equals("INACTIVE"));
        }
    }

    @When("I hover my mouse over the Status of the {string} view the Start and End Date")
    public void iHoverMyMouseOverTheStatusOfThe(String status) {
        if (status.equalsIgnoreCase("ar")) {
            hover(AuthorizedRep.consumerARStatus);
//            WebElement statusForDate = Driver.getDriver().findElement(By.xpath("(//h5)[3]/parent::div/div[2]/div/table/tbody/tr/td[8]"));
//            hover(statusForDate);
            WebElement startDate = Driver.getDriver().findElement(By.xpath("//p[contains(text(),'START DATE')]/parent::div/h6"));
            WebElement endDate = Driver.getDriver().findElement(By.xpath("//p[contains(text(),'END DATE')]/parent::div/h6"));
            assertTrue(startDate.getText().matches("[0-1][0-9]\\/[0-3][0-9]\\/2020|\\/2021"));
            assertTrue(endDate.getText().matches("[0-1][0-9]\\/[0-3][0-9]\\/2020|\\/2021|\\B"));
        }
    }

    @Then("I will see the Age for the AR that is derived from the DOB of the AR")
    public void iWillSeeTheAgeForTheARThatIsDerivedFromTheDOBOfTheAR() {
        SimpleDateFormat dateForm = new SimpleDateFormat("MM/dd/yyyy");
        Date todayDate = new Date();
        String[] currentDate = dateForm.format(todayDate).split("/");
        jsClick(Driver.getDriver().findElement(By.xpath("(//span[contains(text(), 'visibility')])[3]")));
        waitFor(2);
        String[] dob = Driver.getDriver().findElement(By.xpath("(//div[@id='members']/div[3]//td[4])")).getText().split("/");
        int expectedAge = Integer.parseInt(currentDate[2]) - Integer.parseInt(dob[2]);
        String[] age = Driver.getDriver().findElement(By.xpath("(//div[@id='members']/div[3]//td[5])")).getText().split(" / ");
        int num = Integer.parseInt(age[0]);
        assertEquals(expectedAge, num);

    }

    @Then("I will see Authorized Representative displays {int} records per page")
    public void iWillSeeAuthorizedRepresentativeDisplaysRecordsPerPage(int arg0) throws ParseException {
        List<WebElement> pageNumsCount = Driver.getDriver().findElements(By.xpath("(//h5)[3]/parent::div/div[2]/div/div/div/ul/li/a"));
        int pageNums = pageNumsCount.size() - 1;
        for (int i = 0; i <= pageNums; i++) {
            List<WebElement> rows = Driver.getDriver().findElements(By.xpath("(//h5)[3]/parent::div/div[2]/div/table/tbody/tr"));
            assertTrue(rows.size() == 2 || rows.size() == 4);
            List<WebElement> statusForDates = Driver.getDriver().findElements(By.xpath("(//h5)[3]/parent::div/div[2]/div/table/tbody/tr/td[8]"));
            for (int y = 0; y <= statusForDates.size() - 1; y++) {
                if (statusForDates.size() == 2) {
                    String status1 = statusForDates.get(y).getText();
                    hover(statusForDates.get(y));
                    String startDate1 = Driver.getDriver().findElement(By.xpath("//p[contains(text(),'START DATE')]/parent::div/h6")).getText();
                    Date dateStart1 = new SimpleDateFormat("MM/dd/yyyy").parse(startDate1);
                    String status2 = statusForDates.get(y + 1).getText();
                    hover(statusForDates.get(y + 1));
                    String startDate2 = Driver.getDriver().findElement(By.xpath("//p[contains(text(),'START DATE')]/parent::div/h6")).getText();
                    Date dateStart2 = new SimpleDateFormat("MM/dd/yyyy").parse(startDate2);
                    assertTrue(dateStart1.after(dateStart2));
                    assertTrue(dateStart1.after(dateStart2));
                    if (status1.equalsIgnoreCase("active")) {
                        assertTrue(status2.equalsIgnoreCase("active") || status2.equalsIgnoreCase("future") || status2.equalsIgnoreCase("inactive"));
                    } else if (status1.equalsIgnoreCase("future")) {
                        assertTrue(status2.equalsIgnoreCase("future") || status2.equalsIgnoreCase("inactive"));
                    } else {
                        assertTrue(status2.equalsIgnoreCase("inactive"));
                    }
                    break;
                } else {
                    String status1 = statusForDates.get(y).getText();
                    assertTrue(status1.equalsIgnoreCase("active") || status1.equalsIgnoreCase("future") || status1.equalsIgnoreCase("inactive"));
                    break;
                }
            }
        }
    }

    @Then("I verify the Authorized Representative have following status")
    public void iVerifyTheAuthorizedRepresentativeHaveFollowingStatus(List<Map<String, String>> data) {
        waitFor(5);
        assertTrue(AuthorizedRep.arCheckStatus(data.get(0).get("Status")).isDisplayed());
    }

    @And("I save without filling following fields")
    public void iSaveWithoutFillingFollowingFields(List<Map<String, String>> data_table) {

        List<WebElement> hiddenEyeElements = AuthorizedRep.getHiddenEyeElements();

        for (WebElement hidWebElement : hiddenEyeElements) {
            waitFor(1);
            hidWebElement.click();
        }
        for (Map<String, String> data : data_table) {
            waitFor(1);
            clearFieldCharByChar(AuthorizedRep.arStartDate);
            AuthorizedRep.arFirstName.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            AuthorizedRep.arFirstName.sendKeys(Keys.DELETE);
            AuthorizedRep.arLastName.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            AuthorizedRep.arLastName.sendKeys(Keys.DELETE);
            clearFieldCharByChar(AuthorizedRep.arDOB);
        }

        waitFor(2);
        AuthorizedRep.arSaveButton.click();
        waitFor(2);
    }

    @Then("I should see every following field is required")
    public void iShouldSeeEveryFollowingFieldIsRequired(List<Map<String, String>> data_table) {


        for (Map<String, String> data : data_table) {
            assertTrue(isElementDisplayed(AuthorizedRep.getReuiredFieldValidation(data.get("StartDate"))));
            assertTrue(isElementDisplayed(AuthorizedRep.getReuiredFieldValidation(data.get("Firstname"))));
            assertTrue(isElementDisplayed(AuthorizedRep.getReuiredFieldValidation(data.get("Lastname"))));
            assertTrue(isElementDisplayed(AuthorizedRep.getReuiredFieldValidation(data.get("DOB"))));
        }

    }

    @When("I navigate to active Authorized Representative record edit page")
    public void clearFiledfollowingiNavigateToActiveAuthorizedRepresentativeRecordEditPage() {
        waitFor(2);
        click(AuthorizedRep.selectAuthorizedRepresentativeRecord);
    }

    @And("I edit the AR details to check cancel operations")
    public void iEditTheARDetailsToCheckCancelOperations() {
        waitFor(2);
        clearText(AuthorizedRep.arEndDate);
    }

    @And("I click on keyboard backspace button")
    public void iClickOnKeyBoardSpaceButton() {
        waitFor(2);
        click(AuthorizedRep.arKeyboardBackSapce);
        waitFor(2);
    }

    @And("I click on cancel button on Authorized Representative page")
    public void iClickOnCancelButton() {
        waitFor(2);
        click(AuthorizedRep.arCancelButton);
        waitFor(5);
    }

    @Then("I verify the  warning message on dialog box")
    public void iVerifyTheWarningMessageOnDialogBox() {
        waitFor(2);
        waitForVisibility(AuthorizedRep.arModalWarningMessage, 2);
        assertTrue(isElementDisplayed(AuthorizedRep.arModalWarningMessage));
        if (AuthorizedRep.arModalWarningMessage.isDisplayed()) {
            AuthorizedRep.arModalCancelButton.click();
        }

    }

    @When("I try to navigate to different page without saving changes")
    public void iTryToNavigateToDifferentPageWithoutSavingChanges() {
        waitFor(2);
        hover(AuthorizedRep.arCallButton);
        waitFor(2);
        click(AuthorizedRep.arCallButton);
        waitFor(2);
    }


    @And("I click on inactivate button")
    public void iClickOnInactivateButton() {
        waitFor(5);
        AuthorizedRep.arInactive.click();
        click(AuthorizedRep.arSaveButton);
        waitFor(2);
        iShouldSeeSuuccessfullyUpdatedMessage();
    }

    @And("I modify the AR details to check update event")
    public void iModifyARDeatils() {
        waitFor(2);
        selectDropDown(AuthorizedRep.arGender, "Female");
        waitFor(5);
        AuthorizedRep.arSaveButton.click();
        waitFor(2);
        iShouldSeeSuuccessfullyUpdatedMessage();
        waitFor(5);
    }

    @And("I modify the Authorized Representative record edit page")
    public void iModifyTheAuthorizedRepresentativeRecordEditPage(List<Map<String, String>> data_table) {
        waitFor(2);
        clearFieldCharByChar(AuthorizedRep.arStartDate);
        clearFieldCharByChar(AuthorizedRep.arEndDate);
        for (Map<String, String> record : data_table) {
            if (record.get("startDate") != null && !record.get("startDate").isEmpty()) {
                String sDate = record.get("startDate");
                sDate = sDate.contains("P") ? getPriorDate(Integer.parseInt(sDate.substring(1))) : getGreaterDate(Integer.parseInt(sDate.substring(1)));
                clearAndFillText(AuthorizedRep.arStartDate, sDate);
            }

            if (record.get("endDate") != null && !record.get("endDate").isEmpty()) {
                String sDate = record.get("endDate");
                sDate = sDate.contains("P") ? getPriorDate(Integer.parseInt(sDate.substring(1))) : getGreaterDate(Integer.parseInt(sDate.substring(1)));
                clearAndFillText(AuthorizedRep.arEndDate, sDate);
            }
        }
        waitFor(5);
        AuthorizedRep.arSaveButton.click();
        waitFor(2);
        iShouldSeeSuuccessfullyUpdatedMessage();
        waitFor(5);

    }

    @Then("I verify age field value is according to Date Of Birth")
    public void iverifyAgeFieldValueIsAccordingToDateOfBirth() {
        List<WebElement> hiddenEyeElements = AuthorizedRep.getHiddenEyeElements();

        for (WebElement hidWebElement : hiddenEyeElements) {
            waitFor(1);
            hidWebElement.click();
        }
        String dob = AuthorizedRep.arDOB.getAttribute("value");

        String[] dobd = dob.split("/");

        String age = AuthorizedRep.arAge.getAttribute("value");

        long eage = Math.abs(YEARS.between(LocalDate.now(ZoneId.systemDefault()), LocalDate.of(Integer.parseInt(dobd[2]), Integer.parseInt(dobd[0]), Integer.parseInt(dobd[1]))));

        Assert.assertEquals(eage, Long.parseLong(age));

    }

    @Then("I should see every following field data invalid  format")
    public void iShouldSeeEveryFollowingFieldDataInvalidFormat(List<Map<String, String>> data_table) {

        for (Map<String, String> data : data_table) {

            assertTrue(isElementDisplayed(AuthorizedRep.invalidDOB));
//            assertTrue(isElementDisplayed(AuthorizedRep.invalidSSN));
            assertTrue(isElementDisplayed(AuthorizedRep.invalidStartDate));

        }
    }

    @And("I am able to edit following fields with invalid data")
    public void iAmAbleToEditFollowingFieldsWithInvalidData(List<Map<String, String>> data_table) {

        List<WebElement> hiddenEyeElements = AuthorizedRep.getHiddenEyeElements();

        for (WebElement hidWebElement : hiddenEyeElements) {
            waitFor(1);
            hidWebElement.click();
        }
        for (Map<String, String> data : data_table) {
            AuthorizedRep.arFirstName.clear();
            waitFor(2);
            AuthorizedRep.arLastName.clear();
            waitFor(2);
            clearFieldCharByChar(AuthorizedRep.arStartDate);
            clearAndFillText(AuthorizedRep.arStartDate, data.get("StartDate"));
            waitFor(2);
            clearFieldCharByChar(AuthorizedRep.arDOB);
            clearAndFillText(AuthorizedRep.arDOB, data.get("DOB"));
            clearAndFillText(AuthorizedRep.arSSN, data.get("SSN"));

        }

        waitFor(5);

        AuthorizedRep.arSaveButton.click();
        waitFor(2);
    }

    @And("I save all details")
    public void iSaveAllDetails() {
        waitFor(2);
        AuthorizedRep.arSaveButton.click();
    }


    @And("I am able to edit following fields")
    public void iAmAbleToEditFollowingFields(List<Map<String, String>> data_table) {
        List<WebElement> hiddenEyeElements = AuthorizedRep.getHiddenEyeElements();

        for (WebElement hidWebElement : hiddenEyeElements) {
            waitFor(1);
            hidWebElement.click();
        }
        for (Map<String, String> data : data_table) {
            scrollUpUsingActions(2);
            waitFor(1);
            selectDropDown(AuthorizedRep.arAccessType, data.get("AuthorizationType"));
            waitFor(1);
            selectDropDown(AuthorizedRep.arspokenLanguage, data.get("SpokenLanguage").trim());
        }

    }

    @Then("I should see suuccessfully updated message")
    public void iShouldSeeSuuccessfullyUpdatedMessage() {
        waitFor(5);
        if (AuthorizedRep.arContinueButton.isDisplayed()) {
            AuthorizedRep.arContinueButton.click();
        }
        waitForVisibility(AuthorizedRep.arModalSuccessMessage, 2);
        assertTrue(isElementDisplayed(AuthorizedRep.arModalUpdateSuccessMessage));
        waitFor(10);
    }

    @Then("I click on the Auth Rep consumer Id in demographic page")
    public void iClickOnTheAuthRepConsumerId() {
        jsClick(memberPage.authRepConsumerId);
        waitFor(3);
    }
}