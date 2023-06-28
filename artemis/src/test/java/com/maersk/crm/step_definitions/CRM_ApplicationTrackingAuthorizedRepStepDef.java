package com.maersk.crm.step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.api_step_definitions.APIATSApplicationController;
import com.maersk.crm.pages.crm.ATSAddAuthorizedRepresentativePage;
import com.maersk.crm.pages.crm.CRMApplicationTrackingPage;
import com.maersk.crm.pages.crm.CRMCaseAndConsumerSearchPage;
import com.maersk.crm.pages.crm.CRMCreateApplicationPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import com.maersk.crm.pages.crm.CRMDemographicMemberPage;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertFalse;

public class CRM_ApplicationTrackingAuthorizedRepStepDef extends CRMUtilities implements ApiBase {

    CRMCreateApplicationPage createApplication = new CRMCreateApplicationPage();
    CRMCaseAndConsumerSearchPage crmCaseAndConsumerSearchPage = new CRMCaseAndConsumerSearchPage();
    ATSAddAuthorizedRepresentativePage atsAddAuthorizedRepresentativePage = new ATSAddAuthorizedRepresentativePage();
    CRMDemographicMemberPage crmDemographicMemberPage = new CRMDemographicMemberPage();
    Map<String, Object> newConsumer = new HashMap<>(getNewTestData2());

    private List<String> actualLabels = new ArrayList<>();
    private List<String> expectedLabels = new ArrayList<>();
    private List<WebElement> unused = new ArrayList<>();
    private String firstName = newConsumer.get("name").toString();
    private String lastName = newConsumer.get("surname").toString();
    private String middleName = newConsumer.get("name").toString().substring(0, 1);
    public static String accessType;
    public static String startDate;
    public static String endDate;
    public static String startDateFuture;
    public static String endDateFuture;
    public static String orgName;
    public static String idNum;
    public static String addressLine1;
    public static String city;
    public static String state;
    public static String zipCode;
    private String authorizedRepDate;
    private String fullName;
    private JsonObject requestParams;
    private JsonObject appRequestPayload;
    public static String authorized;
    public static String authStatus;
    public static String authType;
    public static String correspondence;
    public static String addressLine2;
    public static String authSignDate;
    private String nullValue = "-- --";
    private String authRepHoverOverDateValue;
    public static String authRepFirstName;
    public static String authRepLastName;
    public static String authRepMi;
    public static HashMap<String, String> authRepInfo = new HashMap<>();
    public static String applicationDeadlineDate;
    CRMApplicationTrackingPage crmApplicationTrackingPage = new CRMApplicationTrackingPage();
    public static List<String> caseIdFromATP = new ArrayList<>();

    @When("I click Add Authorized Representative button")
    public void clickAddAuthorizedRepButton() {
        waitFor(3);
        waitForClickablility(createApplication.addAuthorizedRepBtn, 37);
        createApplication.addAuthorizedRepBtn.click();
        waitForVisibility(createApplication.authorizedRepPage, 10);
    }

    @And("I click save button to save Authorized Representative")
    public void clickSaveButtonToSaveAuthorizedRep() {
        atsAddAuthorizedRepresentativePage.arSaveButton.click();
        waitForVisibility(createApplication.addAuthorizedRepBtn, 10);
    }
    @Then("I click save button to save Authorized Representative \\(negative)")
    public void iClickSaveButtonToSaveAuthorizedRepresentativeNegative() {
        atsAddAuthorizedRepresentativePage.arSaveButton.click();
    }

    @When("I enter information to add {string} Authorized Representative")
    public void addAuthorizedRepType(String type) {
        clearAndFillText(atsAddAuthorizedRepresentativePage.arMiddleName, middleName);
        if (type.equals("ACTIVE")) {
            authStatus = "ACTIVE";
            clearAndFillText(atsAddAuthorizedRepresentativePage.arFirstName, firstName);
            clearAndFillText(atsAddAuthorizedRepresentativePage.arLastName, lastName);
            startDate = getPriorDateFormatMMddyyyy(1);
            clearAndFillText2(atsAddAuthorizedRepresentativePage.startDate, startDate);
            waitFor(2);
            atsAddAuthorizedRepresentativePage.arEndDateBtn.click();
            atsAddAuthorizedRepresentativePage.arDateSelectOkBtn.click();
            waitFor(2);
            atsAddAuthorizedRepresentativePage.arAuthorizedDateBtn.click();
            atsAddAuthorizedRepresentativePage.arDateSelectOkBtn.click();
        } else if (type.equals("INACTIVE")) {
            authStatus = "INACTIVE";
            clearAndFillText(atsAddAuthorizedRepresentativePage.arFirstName, firstName + "In");
            clearAndFillText(atsAddAuthorizedRepresentativePage.arLastName, lastName + "In");
            int randomNum = Integer.parseInt(getRandomNumber(2));
            startDate = getPriorDateFormatMMddyyyy(randomNum + 5);
            endDate = getPriorDateFormatMMddyyyy(randomNum);
            clearAndFillText2(atsAddAuthorizedRepresentativePage.startDate, startDate);
            clearAndFillText2(atsAddAuthorizedRepresentativePage.endDate, endDate);
            System.out.println("INACTIVE Start Date: " + startDate);
            System.out.println("INACTIVE End Date: " + endDate);
        } else if (type.equals("FUTURE")) {
            authStatus = "FUTURE";
            clearAndFillText(atsAddAuthorizedRepresentativePage.arFirstName, firstName + "Fut");
            clearAndFillText(atsAddAuthorizedRepresentativePage.arLastName, lastName + "Fut");
            int random = Integer.parseInt(getRandomNumber(2));
            startDateFuture = getGreaterDateFormatMMddyyyy(random);
            clearAndFillText2(atsAddAuthorizedRepresentativePage.startDate, startDateFuture);
            System.out.println("FUTURE Start Date: " + startDateFuture);
        }
    }

    @And("I click on Added Authorized Representative")
    public void clickAuthorizedRepAndEdit() {
        waitFor(2);
        jsClick(createApplication.authRepHyperlink);
     //   createApplication.authRepHyperlink.click();
        Assert.assertTrue(atsAddAuthorizedRepresentativePage.addAuthRepHeader.getText().contains("AUTHORIZED REPRESENTATIVE(S)"), "Didnt navigate to AUTHORIZED REPRESENTATIVE(S) Page");
    }

    @And("I click on Edit button for Authorized Representative")
    public void clickEditButtonForAuthorizedRep() {
        waitForVisibility(atsAddAuthorizedRepresentativePage.arEditBtn, 10);
        atsAddAuthorizedRepresentativePage.arEditBtn.click();
    }

    @And("I edit Authorized Representative and save it")
    public void editARAndSave() {
        clearAndFillText(atsAddAuthorizedRepresentativePage.arFirstName, "editTest" + firstName);

        atsAddAuthorizedRepresentativePage.arSaveButton.click();
        waitForVisibility(createApplication.addAuthorizedRepBtn, 10);
    }

    @And("I click back button")
    public void clickBackButtonBeforeSaveAR() {
        waitFor(2);
        jsClick(atsAddAuthorizedRepresentativePage.arBackBtn);
      //  atsAddAuthorizedRepresentativePage.arBackBtn.click();
    }

    @Then("I check that Add Authorized Representative button is enabled")
    public void verifyAddAuthorizedRepButtonEnabled() {
        assertTrue(createApplication.addAuthorizedRepBtn.isDisplayed());
    }

    @Then("I verify the Authorized Representative have following labels")
    public void verifyARLabels(List<String> dataTable) {
        expectedLabels.addAll(dataTable);
        waitFor(2);
        actualLabels.add(atsAddAuthorizedRepresentativePage.authTypeLabel.getText());
        actualLabels.add(atsAddAuthorizedRepresentativePage.firstNameLabel.getText());
        actualLabels.add(atsAddAuthorizedRepresentativePage.middleInitialLabel.getText());
        actualLabels.add(atsAddAuthorizedRepresentativePage.lastNameLabel.getText());
        actualLabels.add(atsAddAuthorizedRepresentativePage.organizationNameLabel.getText());
        actualLabels.add(atsAddAuthorizedRepresentativePage.idNumberLabel.getText());
        actualLabels.add(atsAddAuthorizedRepresentativePage.startDateLabel.getText());
        actualLabels.add(atsAddAuthorizedRepresentativePage.endDateLabel.getText());
        actualLabels.add(atsAddAuthorizedRepresentativePage.accessTypeLabel.getText());
        actualLabels.add(atsAddAuthorizedRepresentativePage.correspondenceLabel.getText());
        actualLabels.add(atsAddAuthorizedRepresentativePage.addressLine1Label.getText());
        actualLabels.add(atsAddAuthorizedRepresentativePage.addressLine2Label.getText());
        actualLabels.add(atsAddAuthorizedRepresentativePage.cityLabel.getText());
        actualLabels.add(atsAddAuthorizedRepresentativePage.stateLabel.getText());
        actualLabels.add(atsAddAuthorizedRepresentativePage.zipCodeLabel.getText());
        actualLabels.add(atsAddAuthorizedRepresentativePage.authorizedLabel.getText());
        actualLabels.add(atsAddAuthorizedRepresentativePage.authorizationSignatureDateLabel.getText());
        Collections.sort(expectedLabels);
        Collections.sort(actualLabels);
        for (int i = 0; i < expectedLabels.size(); i++) {
            Assert.assertTrue(BrowserUtils.filterTextFor(actualLabels.get(i)).equals(BrowserUtils.filterTextFor(expectedLabels.get(i))),
                    "Actual label: " + actualLabels.get(i) + " does not match Expected label: " + expectedLabels.get(i));
        }
    }

    @Then("I verify data can be entered into fields on Authorized Representative Page for {string}")
    public void verifyDataEnteringInAuthorizedRepPage(String type) {
        if (!type.equals("Edit")) {
            atsAddAuthorizedRepresentativePage.arSaveButton.click();
            assertEquals(atsAddAuthorizedRepresentativePage.firstNameRequiredMsg.getText(), "FIRST NAME is required and cannot be left blank");
            assertEquals(atsAddAuthorizedRepresentativePage.lastNameRequiredMsg.getText(), "LAST NAME is required and cannot be left blank");
        }
        clearAndFillText(atsAddAuthorizedRepresentativePage.arFirstName, firstName);
        clearAndFillText(atsAddAuthorizedRepresentativePage.arMiddleName, "M");
        clearAndFillText(atsAddAuthorizedRepresentativePage.arLastName, lastName);
        selectDropDown(atsAddAuthorizedRepresentativePage.arAccessType, "Full Access");
        clearAndFillText(atsAddAuthorizedRepresentativePage.startDate, getCurrentDate());
        clearAndFillText(atsAddAuthorizedRepresentativePage.endDate, getGreaterDate(3));
        //atsAddAuthorizedRepresentativePage.startDate.sendKeys("11/01/2020");
        //atsAddAuthorizedRepresentativePage.endDate.sendKeys("12/01/2020");
        clearAndFillText(atsAddAuthorizedRepresentativePage.arOrganizationName, "orgName1");
        clearAndFillText(atsAddAuthorizedRepresentativePage.arIdNumber, "324234524");
        clearAndFillText(atsAddAuthorizedRepresentativePage.arAddressLine1, "1234 test ln");
        clearAndFillText(atsAddAuthorizedRepresentativePage.arAddressLine2, "1234 test ln");
        clearAndFillText(atsAddAuthorizedRepresentativePage.arAddressCity, "Denver");
        clearAndFillText(atsAddAuthorizedRepresentativePage.arAddressState, "Colorado");
        atsAddAuthorizedRepresentativePage.arAddressZip.click();
     //   selectDropDown(atsAddAuthorizedRepresentativePage.arAddressZip, "80212");
        clearAndFillText(atsAddAuthorizedRepresentativePage.arAddressZip, "80212");
        clearAndFillText(atsAddAuthorizedRepresentativePage.arAddressSignatureDate, getCurrentDate());

        assertTrue(atsAddAuthorizedRepresentativePage.arFirstName.getAttribute("value").length() > 1);
        assertEquals(atsAddAuthorizedRepresentativePage.arMiddleName.getAttribute("value").length(), 1);
        assertEquals(atsAddAuthorizedRepresentativePage.arAccessType.getText(), "Full Access");
        assertTrue(atsAddAuthorizedRepresentativePage.startDate.getAttribute("value").length() > 1);
        assertTrue(atsAddAuthorizedRepresentativePage.endDate.getAttribute("value").length() > 1);
        assertTrue(atsAddAuthorizedRepresentativePage.arOrganizationName.getAttribute("value").length() > 1);
        assertTrue(atsAddAuthorizedRepresentativePage.arAddressLine1.getAttribute("value").length() > 1);
        assertTrue(atsAddAuthorizedRepresentativePage.arAddressLine2.getAttribute("value").length() > 1);
        assertTrue(atsAddAuthorizedRepresentativePage.arAddressCity.getAttribute("value").length() > 1);
        assertTrue(atsAddAuthorizedRepresentativePage.arAddressState.getAttribute("value").length() > 1);
        assertTrue(atsAddAuthorizedRepresentativePage.arAddressZip.getAttribute("value").length() > 1);
        assertTrue(atsAddAuthorizedRepresentativePage.arAddressSignatureDate.getAttribute("value").length() > 1);
    }

    @Then("I verify Start date is same as Authorization Signature Date on Authorized Representative Page")
    public void verifyStartDate() {
        waitFor(1);
        createApplication.authorizedRepCollapseBtn.click();
        scrollToElement(createApplication.authRepStartDateValue);
        waitFor(1);
        assertEquals(createApplication.authRepStartDateValue.getText(), createApplication.authRepSignDateValue.getText(), "Auth Rep START DATE and SIGNATURE DATE in panel does not match: ");
    }

    @Then("I verify entered information from the {string} view in Create Application Page")
    public void verifyEnteredInfoCollapsedView(String type) {
        if (type.equals("collapsed")) {
            fullName = firstName + " " + middleName + " " + lastName;
            assertEquals(createApplication.nameCollapseView.getText(), fullName, "Actual Auth Rep Full Name in panel does not match the expected Full Name:");
            assertEquals(createApplication.authTypeCollapseView.getText(), authType, "Actual Auth Rep Auth Type in panel does not match the expected Auth Type:");
            assertEquals(createApplication.accessTypeCollapseView.getText(), accessType, "Actual Auth Rep ACCESS TYPE in panel does not match the expected ACCESS TYPE:");
            assertEquals(createApplication.authRepCorrexpondenceValue.getText(), correspondence, "Actual Auth Rep CORRESPONDENCE in panel does not match the expected CORRESPONDENCE:");
            assertEquals(createApplication.authRepAuthorizedValue.getText(), authorized, "Actual Auth Rep AUTHORIZED in panel does not match the expected AUTHORIZED:");
            assertEquals(createApplication.authRepStatusValue.getText(), authStatus, "Actual Auth Rep STATUS in panel does not match the expected STATUS:");
        } else if (type.equals("expanded")) {
            createApplication.authorizedRepCollapseBtn.click();
            waitFor(1);
            assertEquals(createApplication.authRepOrgNameValue.getText(), orgName, "Actual Auth Rep ORGANIZATION NAME in panel does not match the expected ORGANIZATION NAME:");
            assertEquals(createApplication.authRepIdNumValue.getText(), idNum, "Actual Auth Rep ID NUMBER in panel does not match the expected ID NUMBER:");
            assertEquals(createApplication.authRepStartDateValue.getText(), startDate, "Actual Auth Rep START DATE in panel does not match the expected START DATE:");
            assertEquals(createApplication.authRepEndDateValue.getText(), endDate, "Actual Auth Rep END DATE in panel does not match the expected END DATE:");
            assertEquals(createApplication.authRepSignDateValue.getText(), authSignDate, "Actual Auth Rep AUTHORIZATION SIGNATURE DATE in panel does not match the expected AUTHORIZATION SIGNATURE DATE:");
            assertEquals(createApplication.authRepAddress1value.getText(), addressLine1, "Actual Auth Rep ADDRESS LINE 1 in panel does not match the expected ADDRESS LINE 1:");
            assertEquals(createApplication.authRepAddress2value.getText(), addressLine2, "Actual Auth Rep ADDRESS LINE 2 in panel does not match the expected ADDRESS LINE 2:");
            assertEquals(createApplication.authRepCityValue.getText(), city, "Actual Auth Rep CITY in panel does not match the expected CITY:");
            assertEquals(createApplication.authRepStateValue.getText(), state, "Actual Auth Rep STATE in panel does not match the expected STATE:");
            assertEquals(createApplication.authRepZipValue.getText(), zipCode, "Actual Auth Rep ZIP in panel does not match the expected ZIP:");
        }
    }

    @Then("I verify Authorized Representatives are in order")
    public void verifyAuthorizedRepOrder() {
        scrollToElement(createApplication.authRepPanelHeader);
        waitFor(3);
        assertEquals(createApplication.authRepStatusValue.getText(), "ACTIVE", "Expected created Auth Rep with ACTIVE STATUS but : ");
        assertTrue(createApplication.authRepStatusValue.getAttribute("class").contains("green"), "Expected ACTIVE STATUS value in green color :");
        assertEquals(createApplication.secondAuthRepStatusValue.getText(), "FUTURE", "Expected created Auth Rep with FUTURE STATUS but : ");
        assertTrue(createApplication.secondAuthRepStatusValue.getAttribute("class").contains("orange"), "Expected FUTURE STATUS value in orange color :");
        assertEquals(createApplication.thirdAuthRepStatusValue.getText(), "INACTIVE", "Expected created Auth Rep with INACTIVE STATUS but : ");
        assertTrue(createApplication.thirdAuthRepStatusValue.getAttribute("class").contains("red"), "Expected INACTIVE STATUS value in red color :");
    }

    @Then("I verify status if Authorized Representative is {string}")
    public void verifyAuthorizedRepStatus(String type) {
        if (type.equals("ACTIVE")) {
            assertEquals(createApplication.authRepStatusValue.getText(), authStatus, "Actual Auth Rep STATUS in panel does not match the expected STATUS:");
        } else if (type.equals("INACTIVE")) {
            assertEquals(createApplication.authRepStatusValue.getText(), authStatus, "Actual Auth Rep STATUS in panel does not match the expected STATUS:");
        } else if (type.equals("FUTURE")) {
            assertEquals(createApplication.authRepStatusValue.getText(), authStatus, "Actual Auth Rep STATUS in panel does not match the expected STATUS:");
        }
    }

    @Then("I verify minimum fields need to be entered in Authorized Representative Page")
    public void verifyMinimumFieldsAuthorizedRep() {
        atsAddAuthorizedRepresentativePage.arSaveButton.click();
        assertEquals(atsAddAuthorizedRepresentativePage.firstNameRequiredMsg.getText(), "FIRST NAME is required and cannot be left blank");
        assertEquals(atsAddAuthorizedRepresentativePage.lastNameRequiredMsg.getText(), "LAST NAME is required and cannot be left blank");
    }

    @Then("I verify that it is present on the create application page")
    public void clickContinueAuthorizedRepPage() {
        assertTrue(createApplication.addAuthorizedRepBtn.isDisplayed());
    }

    @Then("I Verify that user is back to Create Application page")
    public void verifyUserIsOnCreateApplicationPage() {
        assertTrue(createApplication.addAuthorizedRepBtn.isDisplayed());
    }

    @Then("I click Cancel Button to stay on the Create Application Page")
    public void clickCancelButton() {
        waitForVisibility(crmCaseAndConsumerSearchPage.cancelBtn, 10);
        crmCaseAndConsumerSearchPage.cancelBtn.click();
        assertTrue(createApplication.addAuthorizedRepBtn.isDisplayed());
    }

    @Then("I verify that user is away from the Create Application Page")
    public void clickContinueButton() {
        boolean elementFound = true;
        try {
            assertFalse(createApplication.addAuthorizedRepBtn.isDisplayed());
        } catch (NoSuchElementException e) {
            elementFound = false;
        }
        assertFalse(elementFound);
    }

    @Then("I verify dropdown values available for {string}")
    public void verifyAuthTypeDropDownValues(String type, List<String> options) {
        waitFor(2);
        if (type.equals("Auth Type")) {
            atsAddAuthorizedRepresentativePage.authType.click();
        } else if (type.equals("Access Type")) {
            atsAddAuthorizedRepresentativePage.authTypeSelection.click();
            waitFor(1);
            atsAddAuthorizedRepresentativePage.arAccessType.click();
        } else if (type.equals("Correspondence")) {
            atsAddAuthorizedRepresentativePage.accessTypeSelection.click();
            waitFor(1);
            atsAddAuthorizedRepresentativePage.correspondenceType.click();
        }

        ArrayList<String> actualValues = new ArrayList<String>();
        ArrayList<String> expValues = new ArrayList<String>();
        for (String option : options)
            expValues.add(option);
        for (WebElement program : atsAddAuthorizedRepresentativePage.authorizedRepFieldDropdownValues) {
            actualValues.add(program.getText());
        }

        Collections.sort(actualValues);
        Collections.sort(expValues);
        assertEquals(actualValues, expValues);
    }

    /**
     * @param data Auth Signature date sets Start date. If both are used, Auth signature needs to be set first.
     */
    @Then("I fill in the following Authorized Representative values")
    public void iFillInTheAuthorizedRepValues(Map<String, String> data) {
        waitFor(2);
        CRM_CreateApplicationStepDef.applicantInformation.put("applicationConsumerType","Authorized Rep");
        //Initialiazing addressType to false to prevent NullPointerException
        CRM_CreateApplicationStepDef.applicantInformation.put("mailing","false");
        CRM_CreateApplicationStepDef.applicantInformation.put("residential","false");
        for (String eachValue : data.keySet()) {
            switch (eachValue.toUpperCase()) {
                case "FIRST NAME":
                    if ("RANDOM 30".equals(data.get("FIRST NAME"))) {
                        firstName = getRandomString(30);
                        authRepFirstName = firstName;
                        clearAndFillText(atsAddAuthorizedRepresentativePage.arFirstName, firstName);
                    } else if ("Random 5".equals(data.get("FIRST NAME"))) {
                        firstName = getRandomString(5);
                        authRepFirstName = firstName;
                        clearAndFillText(atsAddAuthorizedRepresentativePage.arFirstName, firstName);
                    } else if(data.get("FIRST NAME").isEmpty()){
                        clearTextField(atsAddAuthorizedRepresentativePage.arFirstName);
                        authRepFirstName = firstName;
                    }else {
                        firstName = data.get("FIRST NAME");
                        clearAndFillText(atsAddAuthorizedRepresentativePage.arFirstName, firstName);
                    }
                    break;
                case "MI":
                    if ("RANDOM 1".equals(data.get("MI"))) {
                        middleName = getRandomString(1);
                        authRepMi = middleName;
                        clearAndFillText(atsAddAuthorizedRepresentativePage.arMiddleName, middleName);
                    } else {
                        middleName = data.get("MI");
                        clearAndFillText2(atsAddAuthorizedRepresentativePage.arMiddleName, middleName);
                    }
                    break;
                case "LAST NAME":
                    if ("RANDOM 30".equals(data.get("LAST NAME"))) {
                        lastName = getRandomString(30);
                        authRepLastName = lastName;
                        clearAndFillText(atsAddAuthorizedRepresentativePage.arLastName, lastName);
                    } else if ("Random 5".equals(data.get("LAST NAME"))) {
                        lastName = getRandomString(5);
                        authRepLastName = lastName;
                        clearAndFillText(atsAddAuthorizedRepresentativePage.arLastName, lastName);
                    } else {
                        lastName = data.get("LAST NAME");
                        clearAndFillText(atsAddAuthorizedRepresentativePage.arLastName, lastName);
                    }
                    break;
                case "ORGANIZATION NAME":
                    if ("RANDOM 5".equals(data.get("ORGANIZATION NAME"))) {
                        orgName = getRandomString(5);
                        clearAndFillText(atsAddAuthorizedRepresentativePage.arOrganizationName, orgName);
                    } else {
                        orgName = data.get("ORGANIZATION NAME");
                        clearAndFillText(atsAddAuthorizedRepresentativePage.arOrganizationName, orgName);
                    }
                    break;
                case "ID NUMBER":
                    if ("Numeric 5".equals(data.get("ID NUMBER"))) {
                        idNum = getRandomNumber(5);
                        clearAndFillText2(atsAddAuthorizedRepresentativePage.arIdNumber, idNum);
                    } else {
                        idNum = data.get("ID NUMBER");
                        clearAndFillText2(atsAddAuthorizedRepresentativePage.arIdNumber, idNum);
                    }
                    break;
                case "START DATE":
                    if ("RANDOM PAST".equals(data.get("START DATE"))) {
                        int randomNum = Integer.parseInt(getRandomNumber(2));
                        startDate = getPriorDateFormatMMddyyyy(randomNum);
                        clearAndFillText2(atsAddAuthorizedRepresentativePage.startDate, startDate);
                    } else if ("RANDOM FUTURE".equals(data.get("START DATE"))) {
                        int random = Integer.parseInt(getRandomNumber(1));
                        startDate = getGreaterDateFormatMMddyyyy(random);
                        clearAndFillText2(atsAddAuthorizedRepresentativePage.startDate, startDate);
                    } else if ("CURRENT".equals(data.get("START DATE"))) {
                        startDate = getCurrentDate();
                        clearAndFillText2(atsAddAuthorizedRepresentativePage.startDate, startDate);
                    } else {
                        startDate = data.get("START DATE");
                        clearAndFillText2(atsAddAuthorizedRepresentativePage.startDate, startDate);
                    }
                    break;
                case "END DATE":
                    if ("RANDOM PAST".equals(data.get("END DATE"))) {
                        int randomNum = Integer.parseInt(getRandomNumber(1));
                        endDate = getPriorDateFormatMMddyyyy(randomNum);
                        clearAndFillText2(atsAddAuthorizedRepresentativePage.endDate, endDate);
                        randomNum=0;
                    } else if ("RANDOM FUTURE".equals(data.get("END DATE"))) {
                        int random = Integer.parseInt(getRandomNumber(3));
                        endDate = getGreaterDateFormatMMddyyyy(random);
                        clearAndFillText2(atsAddAuthorizedRepresentativePage.endDate, endDate);
                    } else if ("CURRENT".equals(data.get("END DATE"))) {
                        endDate = getCurrentDate();
                        clearAndFillText2(atsAddAuthorizedRepresentativePage.endDate, endDate);
                    } else {
                        endDate = data.get("END DATE");
                        clearAndFillText2(atsAddAuthorizedRepresentativePage.endDate, endDate);
                    }
                    break;
                case "ADDRESS LINE 1":
                    if ("ALPHA NUMERIC".equals(data.get("ADDRESS LINE 1"))) {
                        addressLine1 = getRandomNumber(3) + " " + getRandomString(5);
                        clearAndFillText(atsAddAuthorizedRepresentativePage.arAddressLine1, addressLine1);
                    } else if (data.get("ADDRESS LINE 1").isEmpty()) {
                        clearTextField(atsAddAuthorizedRepresentativePage.arAddressLine1);
                    } else {
                        addressLine1 = data.get("ADDRESS LINE 1");
                        clearAndFillText(atsAddAuthorizedRepresentativePage.arAddressLine1, addressLine1);
                    }
                    CRM_CreateApplicationStepDef.applicantInformation.put("addressStreet1",addressLine1);
                    CRM_CreateApplicationStepDef.applicantInformation.put("mailing","true");
                    break;
                case "ADDRESS LINE 2":
                    if ("ALPHA NUMERIC".equals(data.get("ADDRESS LINE 2"))) {
                        addressLine2 = getRandomNumber(3) + " " + getRandomString(5);
                        clearAndFillText(atsAddAuthorizedRepresentativePage.arAddressLine2, addressLine2);
                    } else if (data.get("ADDRESS LINE 2").isEmpty()) {
                        clearTextField(atsAddAuthorizedRepresentativePage.arAddressLine2);
                    } else {
                        addressLine2 = data.get("ADDRESS LINE 2");
                        clearAndFillText(atsAddAuthorizedRepresentativePage.arAddressLine2, addressLine2);
                    }
                    CRM_CreateApplicationStepDef.applicantInformation.put("addressStreet2",addressLine2);
                    break;
                case "CITY":
                    if ("RANDOM".equals(data.get("CITY"))) {
                        selectRandomDropDownOption(atsAddAuthorizedRepresentativePage.arAddressCity);
                        city = atsAddAuthorizedRepresentativePage.arAddressCity.getAttribute("value").toString();
                    } else {
                        city = data.get("CITY");
                        clearAndFillText2(atsAddAuthorizedRepresentativePage.arAddressCity, city);
                    }
                    CRM_CreateApplicationStepDef.applicantInformation.put("addressCity",city);
                    break;
                case "STATE":
                    if ("RANDOM".equals(data.get("STATE"))) {

                        selectDropDownByIndex(atsAddAuthorizedRepresentativePage.arAddressState, unused, 0);
                        //          selectRandomDropDownOption(atsAddAuthorizedRepresentativePage.arAddressState);
                        state = atsAddAuthorizedRepresentativePage.arAddressState.getAttribute("value").toString();
                    } else {
                        state = data.get("STATE");
                        clearAndFillText2(atsAddAuthorizedRepresentativePage.arAddressState, state);
                    }
                    CRM_CreateApplicationStepDef.applicantInformation.put("addressState",state);
                    break;
                case "ZIP CODE":
                    if ("RANDOM".equals(data.get("ZIP CODE"))) {
                        selectDropDownByIndex(atsAddAuthorizedRepresentativePage.arAddressZip, unused, 0);
                        //      selectRandomDropDownOption(atsAddAuthorizedRepresentativePage.arAddressZip);
                        zipCode = atsAddAuthorizedRepresentativePage.arAddressZip.getAttribute("value").toString();
                    } else {
                        zipCode = data.get("ZIP CODE");
                        clearAndFillText2(atsAddAuthorizedRepresentativePage.arAddressZip, zipCode);
                    }
                    CRM_CreateApplicationStepDef.applicantInformation.put("addressZip",zipCode);
                    break;
                case "AUTHORIZATION SIGNATURE DATE":
                    if ("RANDOM PAST".equals(data.get("AUTHORIZATION SIGNATURE DATE"))) {
                        int randomNum = Integer.parseInt(getRandomNumber(2));
                        authSignDate = getPriorDateFormatMMddyyyy(randomNum);
                        startDate = authSignDate;
                        clearAndFillText2(atsAddAuthorizedRepresentativePage.authorizationSignatureDate, authSignDate);
                    } else if ("RANDOM FUTURE".equals(data.get("AUTHORIZATION SIGNATURE DATE"))) {
                        int random = Integer.parseInt(getRandomNumber(1));
                        authSignDate = getGreaterDateFormatMMddyyyy(random);
                        clearAndFillText2(atsAddAuthorizedRepresentativePage.authorizationSignatureDate, authSignDate);
                        startDate = authSignDate;
                    } else if ("CURRENT".equals(data.get("AUTHORIZATION SIGNATURE DATE"))) {
                        authSignDate = getCurrentDate();
                        clearAndFillText2(atsAddAuthorizedRepresentativePage.authorizationSignatureDate, authSignDate);
                        startDate = authSignDate;
                    }else if(data.get("AUTHORIZATION SIGNATURE DATE").isEmpty()){
                        authSignDate="";
                        startDate=authSignDate;
                    }else {
                        authSignDate = data.get("AUTHORIZATION SIGNATURE DATE");
                        startDate = authSignDate;
                    }
                    break;
                case "AUTH TYPE":
                    if ("RANDOM".equals(data.get("AUTH TYPE"))) {
                        selectRandomDropDownOption(atsAddAuthorizedRepresentativePage.authType);
                        authType = atsAddAuthorizedRepresentativePage.authType.getText();
                    } else {
                        selectDropDown(atsAddAuthorizedRepresentativePage.authType, data.get("AUTH TYPE"));
                        authType = atsAddAuthorizedRepresentativePage.authType.getText();
                    }
                    break;
                case "ACCESS TYPE":
                    if ("RANDOM".equals(data.get("ACCESS TYPE"))) {
                        selectRandomDropDownOption(atsAddAuthorizedRepresentativePage.arAccessType);
                        accessType = atsAddAuthorizedRepresentativePage.arAccessType.getText();
                    } else {
                        selectDropDown(atsAddAuthorizedRepresentativePage.arAccessType, data.get("ACCESS TYPE"));
                    }
                    break;
                case "CORRESPONDENCE":
                    if ("RANDOM".equals(data.get("CORRESPONDENCE"))) {
                        selectRandomDropDownOption(atsAddAuthorizedRepresentativePage.correspondenceType);
                        correspondence = atsAddAuthorizedRepresentativePage.correspondenceType.getText();
                    } else {
                        selectDropDown(atsAddAuthorizedRepresentativePage.correspondenceType, data.get("CORRESPONDENCE"));
                    }
                    break;
                case "AUTHORIZED":
                    waitFor(1);
                    authorized = data.get("AUTHORIZED");
                    if ("Yes".equalsIgnoreCase(authorized)) {
                        atsAddAuthorizedRepresentativePage.authorizedYes.click();
                    } else if ("No".equalsIgnoreCase(authorized)) {
                        atsAddAuthorizedRepresentativePage.authorizedNo.click();
                    } else {
                        Assert.fail("Selected Option value is not yes or no for Auth Rep Authorized");
                    }
                    break;
                case "AUTH STATUS":
                    if (startDate.equals(null)) {
                        fail("START DATE IS NOT SET");
                    } else {
                        authStatus = calculateAuthStatus(startDate, endDate);
                    }
                    break;
                default:
                    fail("Provided Key does not match expected Key");
            }
        }
    }

    @And("I set Authorized representative Values from the sent application request payload to be checked")
    public void iSetAuthorizedRepresentativeValuesFromTheSentApplicationRequestPayloadToBeChecked() {
        requestParams = APIATSApplicationController.appPayload.get();
        firstName = requestParams.getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("consumerFirstName").getAsString();
        middleName = requestParams.getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("consumerMiddleName").getAsString();
        lastName = requestParams.getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("consumerLastName").getAsString();
        authType = requestParams.getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("consumerType").getAsString();
        accessType = requestParams.getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("accessType").getAsString();
        correspondence = requestParams.getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("correspondence").getAsString();
        if (requestParams.getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("authorizedRepSignature").getAsBoolean()) {
            authorized = "Yes";
        } else {
            authorized = "No";
        }
        orgName = requestParams.getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("authorizedRepOrgName").getAsString();
        idNum = requestParams.getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("authorizedRepExternalId").getAsString();
        startDate = convertyyyyMMddToMMddyyyy(requestParams.getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("authorizedRepAppStartDate").getAsString().substring(0, 10));
        endDate = convertyyyyMMddToMMddyyyy(requestParams.getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("authorizedRepAppEndDate").getAsString().substring(0, 10));
        addressLine1 = requestParams.getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().getAsJsonArray("applicationConsumerAddress").get(0).getAsJsonObject().get("addressStreet1").getAsString();
        addressLine2 = requestParams.getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().getAsJsonArray("applicationConsumerAddress").get(0).getAsJsonObject().get("addressStreet2").getAsString();
        city = requestParams.getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().getAsJsonArray("applicationConsumerAddress").get(0).getAsJsonObject().get("addressCity").getAsString();
        state = requestParams.getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().getAsJsonArray("applicationConsumerAddress").get(0).getAsJsonObject().get("addressState").getAsString();
        zipCode = requestParams.getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().getAsJsonArray("applicationConsumerAddress").get(0).getAsJsonObject().get("addressZip").getAsString();
        authStatus = calculateAuthStatus(startDate, endDate);
        authSignDate = convertyyyyMMddToMMddyyyy(requestParams.getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("authorizedRepSignatureDate").getAsString().substring(0, 10));
    }

    public String calculateAuthStatus(String startDate, String endDate) {
        String current = getCurrentDate();
        Long start = daysDifference(current, startDate);
        Long end = daysDifference(current, endDate);
        if (start <= 0 && end >= 0) {
            authStatus = "ACTIVE";
        } else if (start >= 1) {
            authStatus = "FUTURE";
        } else if (end < 0) {
            authStatus = "INACTIVE";
        }
        return authStatus;
    }

    @Then("I verify double dash is represented for NUll values for Authorized Representative panel info")
    public void iVerifyDoubleDashIsRepresentedForNUllValuesForAuthorizedRepresentativePanelInfo() {
        waitFor(2);
        scrollToElement(createApplication.authRepPanelHeader);
        assertEquals(createApplication.accessTypeCollapseView.getText(), nullValue, "Actual Auth Rep ACCESS TYPE in panel does not match the expected ACCESS TYPE:");
        assertEquals(createApplication.authRepCorrexpondenceValue.getText(), nullValue, "Actual Auth Rep CORRESPONDENCE in panel does not match the expected CORRESPONDENCE:");
        createApplication.authorizedRepCollapseBtn.click();
        waitFor(2);
        assertEquals(createApplication.authRepOrgNameValue.getText(), nullValue, "Actual Auth Rep ORGANIZATION NAME in panel does not match the expected ORGANIZATION NAME:");
        assertEquals(createApplication.authRepIdNumValue.getText(), nullValue, "Actual Auth Rep ID NUMBER in panel does not match the expected ID NUMBER:");
        assertEquals(createApplication.authRepEndDateValue.getText(), nullValue, "Actual Auth Rep END DATE in panel does not match the expected END DATE:");
        assertEquals(createApplication.authRepSignDateValue.getText(), nullValue, "Actual Auth Rep AUTHORIZATION SIGNATURE DATE in panel does not match the expected AUTHORIZATION SIGNATURE DATE:");
        assertEquals(createApplication.authRepAddress1value.getText(), nullValue, "Actual Auth Rep ADDRESS LINE 1 in panel does not match the expected ADDRESS LINE 1:");
        assertEquals(createApplication.authRepAddress2value.getText(), nullValue, "Actual Auth Rep ADDRESS LINE 2 in panel does not match the expected ADDRESS LINE 2:");
        assertEquals(createApplication.authRepCityValue.getText(), nullValue, "Actual Auth Rep CITY in panel does not match the expected CITY:");
        assertEquals(createApplication.authRepStateValue.getText(), nullValue, "Actual Auth Rep STATE in panel does not match the expected STATE:");
        assertEquals(createApplication.authRepZipValue.getText(), nullValue, "Actual Auth Rep ZIP in panel does not match the expected ZIP:");
    }

    @Then("I verify start and end date on Authorized Rep status date hover pop up")
    public void iVerifyStartAndEndDateOnAuthorizedRepStatusDateHoverPopUp() {
        waitFor(1);
        scrollToElement(createApplication.authRepPanelHeader);
        waitFor(1);
        hover(createApplication.authRepStatusValue);
        waitFor(2);
        authRepHoverOverDateValue = createApplication.authRepStartEndDatePopUp.getText();
        assertTrue(authRepHoverOverDateValue.contains(startDate) && authRepHoverOverDateValue.contains(endDate), "Expected start and end date value: " + startDate + " " + endDate + "  Actual value in pop up: " + authRepHoverOverDateValue);
    }

    @Then("I verify END DATE cannot be before START DATE error message is displayed and Auth rep is not saved")
    public void iVerifyENDDATECannotBeBeforeSTARTDATEErrorMessageIsDisplayedAndAuthRepIsNotSaved() {
        assertEquals(atsAddAuthorizedRepresentativePage.endDateErrorMessage.getText(), "END DATE cannot be before START DATE", "END DATE cannot be before START DATE message did not appear when entering End date is prior to Start date ");
        waitFor(1);
        assertTrue(atsAddAuthorizedRepresentativePage.addAuthRepHeader.isDisplayed(), "Auth Rep was saved when entering End date is prior to Start date ");
    }

    @And("I click on the created Auth Rep name hyperlink to go to Auth Rep Details page")
    public void iClickOnTheCreatedAuthRepNameHyperlinkToGoToAuthRepDetailsPage() {
        waitFor(2);
        createApplication.nameCollapseView.click();
        waitForVisibility(atsAddAuthorizedRepresentativePage.addAuthRepHeader, 10);
        assertTrue(atsAddAuthorizedRepresentativePage.addAuthRepHeader.toString().contains("AUTHORIZED REPRESENTATIVE(S)"), "Clicking on Auth Rep Hyperlink did not navigate to Auth Rep Details page");
    }

    @And("I verify the Authorized Representative details page have following labels")
    public void iVerifyTheAuthorizedRepresentativeDetailsPageHaveFollowingLabels() {
        assertEquals(atsAddAuthorizedRepresentativePage.savedAuthTypeLabel.getText(), "AUTH TYPE", "No AUTH TYPE label found on AUTHORIZED REPRESENTATIVE(S) details page");
        assertTrue(atsAddAuthorizedRepresentativePage.savedFirstNameLabel.toString().contains("FIRST NAME"), "No FIRST NAME label found on AUTHORIZED REPRESENTATIVE(S) details page");
        assertTrue(atsAddAuthorizedRepresentativePage.savedMiLabel.toString().contains("MI"), "No MI label found on AUTHORIZED REPRESENTATIVE(S) details page");
        assertTrue(atsAddAuthorizedRepresentativePage.savedLastNameLabel.toString().contains("LAST NAME"), "No LAST NAME label found on AUTHORIZED REPRESENTATIVE(S) details page");
        assertEquals(atsAddAuthorizedRepresentativePage.savedOrgNameLabel.getText(), "ORGANIZATION NAME", "No ORGANIZATION NAME label found on AUTHORIZED REPRESENTATIVE(S) details page");
        assertEquals(atsAddAuthorizedRepresentativePage.savedIdNumberLabel.getText(), "ID NUMBER", "No ID NUMBER label found on AUTHORIZED REPRESENTATIVE(S) details page");
        assertEquals(atsAddAuthorizedRepresentativePage.savedStartDateLabel.getText(), "START DATE", "No START DATE label found on AUTHORIZED REPRESENTATIVE(S) details page");
        assertEquals(atsAddAuthorizedRepresentativePage.savedEndDateLabel.getText(), "END DATE", "No END DATE label found on AUTHORIZED REPRESENTATIVE(S) details page");
        assertEquals(atsAddAuthorizedRepresentativePage.savedAccessTypeLabel.getText(), "ACCESS TYPE", "No ACCESS TYPE label found on AUTHORIZED REPRESENTATIVE(S) details page");
        assertEquals(atsAddAuthorizedRepresentativePage.savedCorrespondenceLabel.getText(), "CORRESPONDENCE", "No CORRESPONDENCE label found on AUTHORIZED REPRESENTATIVE(S) details page");
        assertEquals(atsAddAuthorizedRepresentativePage.savedAddressOneLabel.getText(), "ADDRESS LINE 1", "No ADDRESS LINE 1 label found on AUTHORIZED REPRESENTATIVE(S) details page");
        assertEquals(atsAddAuthorizedRepresentativePage.savedAddressTwoLabel.getText(), "ADDRESS LINE 2", "No ADDRESS LINE 2 label found on AUTHORIZED REPRESENTATIVE(S) details page");
        assertEquals(atsAddAuthorizedRepresentativePage.savedCityLabel.getText(), "CITY", "No CITY label found on AUTHORIZED REPRESENTATIVE(S) details page");
        assertEquals(atsAddAuthorizedRepresentativePage.savedStateLabel.getText(), "STATE", "No STATE label found on AUTHORIZED REPRESENTATIVE(S) details page");
        assertEquals(atsAddAuthorizedRepresentativePage.savedZipLabel.getText(), "ZIP CODE", "No ZIP CODE label found on AUTHORIZED REPRESENTATIVE(S) details page");
        assertEquals(atsAddAuthorizedRepresentativePage.savedAuthLabel.getText(), "AUTHORIZED", "AUTHORIZED label found on AUTHORIZED REPRESENTATIVE(S) details page");
        assertEquals(atsAddAuthorizedRepresentativePage.savedAuthSignatureLabel.getText(), "AUTHORIZATION SIGNATURE DATE", "No AUTHORIZATION SIGNATURE DATE label found on AUTHORIZED REPRESENTATIVE(S) details page");
    }

    @And("I verify the Authorized Representative Values in the Auth Rep details page")
    public void iVerifyTheAuthorizedRepresentativeValuesInTheAuthRepDetailsPage() {
        assertEquals(atsAddAuthorizedRepresentativePage.savedAuthType.getText(), authType, "Selected AUTH TYPE does not match AUTH TYPE value");
        assertEquals(atsAddAuthorizedRepresentativePage.savedFirstName.getText(), firstName, "Entered FIRST NAME does not match AUTH REP FIRST NAME detail page value: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedMi.getText(), middleName, "Entered Middle Initial does not match AUTH REP Middle Initial detail page value: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedLastName.getText(), lastName, "Entered Last NAME does not match AUTH REP Last NAME detail page value: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedOrgName.getText(), orgName, "Entered ORGANIZATION NAME does not match AUTH REP ORGANIZATION NAME detail page value: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedIdNumber.getText(), idNum, "Entered ID NUMBER does not match AUTH REP ID NUMBER detail page value: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedStartDate.getText(), startDate, "Entered START DATE does not match AUTH REP START DATE detail page value: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedEndDate.getText(), endDate, "Entered END DATE does not match AUTH REP END DATE detail page value: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedAccessType.getText(), accessType, "Entered ACCESS TYPE does not match AUTH REP ACCESS TYPE detail page value: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedCorrespondence.getText(), correspondence, "Entered CORRESPONDENCE does not match AUTH REP CORRESPONDENCE detail page value: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedAddressOne.getText(), addressLine1, "Entered ADDRESS LINE 1 does not match AUTH REP ADDRESS LINE 1 detail page value: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedAddressTwo.getText(), addressLine2, "Entered ADDRESS LINE 2 does not match AUTH REP ADDRESS LINE 2 detail page value: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedCity.getText(), city, "Entered CITY does not match AUTH REP CITY detail page value: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedState.getText(), state, "Entered STATE does not match AUTH REP STATE detail page value: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedZip.getText(), zipCode, "Entered ZIPCODE  does not match AUTH REP ZIPCODE detail page value: ");
        assertTrue(atsAddAuthorizedRepresentativePage.savedAuthYesbutton.getAttribute("class").contains("Mui-checked"), "Selected AUTHORIZED YES does not match AUTH REP AUTHORIZED YES detail page value: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedAuthSignature.getText(), authSignDate, "Entered AUTHORIZATION SIGNATURE DATE does not match AUTH REP AUTHORIZATION SIGNATURE DATE detail page value: ");
    }

    @Then("I validate double dashes are used for null values in Authorized Representative detail page")
    public void iValidateDoubleDashesAreUsedForNullValuesInAuthorizedRepresentativeDetailPage() {
        assertEquals(atsAddAuthorizedRepresentativePage.savedMi.getText(), nullValue, "Expected \"-- --\" for AUTH REP MI null value but found: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedOrgName.getText(), nullValue, "Expected \"-- --\" for AUTH REP ORGANIZATION NAME null value but found: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedIdNumber.getText(), nullValue, "Expected \"-- --\" for AUTH REP ID NUMBER null value but found: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedEndDate.getText(), nullValue, "Expected \"-- --\" for AUTH REP END DATE null value but found: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedAccessType.getText(), nullValue, "Expected \"-- --\" for AUTH REP ACCESS TYPE null value but found: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedCorrespondence.getText(), nullValue, "Expected \"-- --\" for AUTH REP CORRESPONDENCE null value but found: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedAddressOne.getText(), nullValue, "Expected \"-- --\" for AUTH REP ADDRESS LINE 1 null value but found: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedAddressTwo.getText(), nullValue, "Expected \"-- --\" for AUTH REP ADDRESS LINE 2 null value but found: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedCity.getText(), nullValue, "Expected \"-- --\" for AUTH REP CITY null value but found: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedState.getText(), nullValue, "Expected \"-- --\" for AUTH REP STATE null value but found: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedZip.getText(), nullValue, "Expected \"-- --\" for AUTH REP ZIP CODE null value but found: ");
        assertEquals(atsAddAuthorizedRepresentativePage.savedAuthSignature.getText(), nullValue, "Expected \"-- --\" for AUTH REP AUTHORIZATION SIGNATURE DATE null value but found: ");

    }

    @And("I verify I am on Authorized Rep Create page")
    public void iVerifyIAmOnAuthorizedRepCreatePage() {
        Assert.assertTrue(atsAddAuthorizedRepresentativePage.addAuthRepHeader.getText().contains("AUTHORIZED REPRESENTATIVE(S)"), "Unable to find AUTHORIZED REPRESENTATIVE(S) header on Add Auth Rep Page");
    }

    @Then("I get the application deadline date from application tracking page")
    public String i_get_the_application_deadline_date_on_application_tracking_page() {
        applicationDeadlineDate = crmApplicationTrackingPage.deadlineDateValue.getText();
        String[] value = applicationDeadlineDate.split("/");
        return  value[2]+"-"+value[0]+"-"+value[1];
    }

    @When("I get the case id from the application tracking page")
    public void iGetCseIdFromTheApplicationTrackingPage() {
        for (int i = 0; i < crmApplicationTrackingPage.caseId.size(); i++) {
            String caseId = crmApplicationTrackingPage.caseId.get(i).getText();
            caseIdFromATP.add(caseId);
        }
    }

    @Then("I verify entered Auth Rep information in view {string} Page")
    public void iVerifyEnteredAuthRepInfomationInViewPage(String page) throws Exception{
        if (page.equals("Application Tracking")) {
            if (crmApplicationTrackingPage.authRepName.isDisplayed()) {
                fullName = firstName + " " + middleName + " " + lastName;
                assertEquals(crmApplicationTrackingPage.authRepName.getText(), fullName, "Actual Auth Rep Full Name in application tracking page does not match the expected Full Name:");
                assertEquals(crmApplicationTrackingPage.authType.getText(), authType, "Actual Auth Rep Auth Type in application tracking page does not match the expected Auth Type:");
                assertEquals(crmApplicationTrackingPage.accessType.getText(), accessType, "Actual Auth Rep ACCESS TYPE in application tracking page does not match the expected ACCESS TYPE:");
                assertEquals(crmApplicationTrackingPage.authRepAuthorizedValue.getText(), authorized, "Actual Auth Rep AUTHORIZED in application tracking page does not match the expected AUTHORIZED:");
                assertEquals(crmApplicationTrackingPage.authRepStatusValue.getText(), authStatus, "Actual Auth Rep STATUS in application tracking page does not match the expected STATUS:");
            }
        } else if (page.equals("Demographic")) {
            scrollToTop();
            if (crmDemographicMemberPage.authRepConsumerId.isDisplayed()) {
                fullName = firstName + " " + middleName + " " + lastName;
                if (!(crmDemographicMemberPage.authRepConsumerId.getText().isEmpty())) {
                    assertTrue(crmDemographicMemberPage.authRepConsumerId.getText().matches("[0-9]*"), "Actual Auth Rep Id in Demographic page is null:");
                    idNum = crmDemographicMemberPage.authRepConsumerId.getText();
                }
                assertEquals(crmDemographicMemberPage.authRepName.getText(), fullName, "Actual Auth Rep Full Name in Demographic page does not match the expected Full Name:");
                assertEquals(crmDemographicMemberPage.authRepDOB.getText(), "", "Actual Auth Rep Date Of Birth in Demographic page does not match the expected dob:");
                assertEquals(crmDemographicMemberPage.authRepAge.getText(), nullValue, "Actual Auth Rep Age in Demographic page does not match the expected Age:");
                assertEquals(crmDemographicMemberPage.authRepSSNvalue.getText(), "", "Actual Auth Rep SSN in Demographic page does not match the expected SSN:");
                assertEquals(crmDemographicMemberPage.AuthRepStatusValue.getText(), authStatus, "Actual Auth Rep STATUS in Demographic page does not match the expected STATUS:");
            }
        } else if (page.equals("Authorized Representative")) {
            scrollToTop();
            System.out.println(atsAddAuthorizedRepresentativePage.consumerId.getText());
            assertEquals(atsAddAuthorizedRepresentativePage.consumerId.getText(), "CONSUMER ID:" + idNum, "Actual Auth Rep ID NUMBER in panel does not match the expected ID NUMBER:");
        }
    }

    @Then("I verify entered Auth Rep information is not displayed in Application Tracking Page")
    public void iVerifyEnteredAuthRepInfomationNotDisplayedInApplicationTrackingPage() {
        assertTrue(!(isElementDisplayed(crmApplicationTrackingPage.authorizationRepresentativeSection)), "Authorization Representative section found in Application Tracking page:");
    }

    @Then("I verify entered Auth Rep information is not displayed in Demographic member view Page")
    public void iVerifyEnteredAuthRepInfomationNotDisplayedInDemographicMemberViewPage() {
        scrollToTop();
        if (crmDemographicMemberPage.AuthRepNoRecordAvailable.isDisplayed()) {
            System.out.println("Text present in demographic page for AUTHORIZED REPRESENTATIVE section: " + crmDemographicMemberPage.AuthRepNoRecordAvailable.getText());
            assertEquals(crmDemographicMemberPage.AuthRepNoRecordAvailable.getText(), "No Records Available", "Auth Rep Records found in Demographic page:");
        }
    }
    @Then("I verify field {string} is set to value {string} on Auth Rep Page")
    public void verifyAuthRepValues(String field, String value){
        switch (field){
            case "Authorized":
                if(value.equalsIgnoreCase("No"))
                    assertTrue(atsAddAuthorizedRepresentativePage.authorizedNo.isSelected());
                 else if(value.equalsIgnoreCase("Yes"))
                    assertTrue(atsAddAuthorizedRepresentativePage.authorizedYes.isSelected());
                break;
            case "Authorized Signature Date":
                if(value.equalsIgnoreCase("RANDOM PAST"))
                    assertTrue(atsAddAuthorizedRepresentativePage.arAddressSignatureDate.getAttribute("value").equals(startDate));
                else if(value.equalsIgnoreCase(""))
                    assertTrue(atsAddAuthorizedRepresentativePage.arAddressSignatureDate.getAttribute("value").isEmpty());
                break;
            case "CORRESPONDENCE":
                if(value.equalsIgnoreCase("Receive in Addition To"))
                    assertTrue(atsAddAuthorizedRepresentativePage.correspondenceType.getText().equals("Receive in Addition To"));
                else if (value.equalsIgnoreCase("Receive in Place Of"))
                    assertTrue(atsAddAuthorizedRepresentativePage.correspondenceType.getText().equals("Receive in Place Of"));
                else if (value.equalsIgnoreCase("Do Not Receive"))
                    assertTrue(atsAddAuthorizedRepresentativePage.correspondenceType.getText().equals("Do Not Receive"));
                break;
            case "Address Line 1":
                if(value.equalsIgnoreCase("ALPHA NUMERIC")) {
                    System.out.println("correspondence " + addressLine1);
                    assertTrue(atsAddAuthorizedRepresentativePage.arAddressLine1.getAttribute("value").equals(addressLine1));
                } else if(value.equalsIgnoreCase(""))
                    assertTrue(atsAddAuthorizedRepresentativePage.arAddressLine1.getAttribute("value").isEmpty());
                break;
        }
    }

}
