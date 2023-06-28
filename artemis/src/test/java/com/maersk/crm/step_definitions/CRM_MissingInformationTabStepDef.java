package com.maersk.crm.step_definitions;

import com.github.javafaker.Lorem;
import com.maersk.crm.api_step_definitions.APIATSApplicationController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.World;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Wait;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.maersk.crm.api_step_definitions.APIATSSendEventAndCreateLinksController.applicationCodeAPI;
import static com.maersk.crm.step_definitions.CRM_CreateApplicationStepDef.*;
import static org.testng.Assert.*;

public class CRM_MissingInformationTabStepDef extends BrowserUtils {
    CRMApplicationTrackingPage applicationTrackingPage = new CRMApplicationTrackingPage();
    CRMApplicationSearchPage applicationSearchPage = new CRMApplicationSearchPage();
    CRMCreateApplicationPage createApplicationPage = new CRMCreateApplicationPage();
    CRMCreateApplicationMemberPage createApplicationMemberPage = new CRMCreateApplicationMemberPage();
    ATSAddAuthorizedRepresentativePage addAuthorizedRepresentativePage = new ATSAddAuthorizedRepresentativePage();
    CRM_CreateApplicationStepDef appStep = new CRM_CreateApplicationStepDef();
    CRM_ApplicationTrackingAuthorizedRepStepDef authRep = new CRM_ApplicationTrackingAuthorizedRepStepDef();
    ATSMissingInformationPage missingInformationPage = new ATSMissingInformationPage();
    final ThreadLocal<List<String>> expectedMIdetailsHeader = ThreadLocal.withInitial(() -> new ArrayList<>(Arrays.asList("CATEGORY", "TYPE", "FROM", "NEED FOR", "COMMENTS", "STATUS")));

    final ThreadLocal<List<String>> actualMIdetailsHeader = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> applicantList = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> strOptions = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal<List<String>> savedMIValues = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal<String> additionalMiComment = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> missingInformationStatus = ThreadLocal.withInitial(String::new);
    public static HashMap<String, String> miExpectedMissingInfo = new HashMap<>();
    public static final ThreadLocal<String> comment = ThreadLocal.withInitial(String::new);
    Actions action = new Actions(Driver.getDriver());


    @Then("I see columns displayed in this order in the Application Information panel for Missing Information Tab")
    public void i_see_values_displayed_in_this_order_in_the_Application_Information_panel_for_Missing_Information_Tab(List<List<String>> data) {
        List<String> expectedColumns = data.get(0);
        List<WebElement> actualColumns = missingInformationPage.applicationInfoLabels;

        Assert.assertEquals(actualColumns.size(), expectedColumns.size(), "Column Size didn't match for Application Information panel for Missing Information Tab");

        for (int i = 0; i < expectedColumns.size(); i++) {
            Assert.assertEquals(actualColumns.get(i).getText().toLowerCase(), expectedColumns.get(i).toLowerCase(), "Expected Application " + expectedColumns.get(i) + " Missing Information Tab Info not found");
        }
    }

    @Then("I verify the Application panel values for {string} in the Missing Information tab")
    public void iVerifyTheApplicationPanelValuesForInTheMissingInformationTab(String appType) {
        Assert.assertEquals(missingInformationPage.applicationIDValue.getText(), applicationId, "Created app Id does not match the app Id value shown in APP INFO panel: ");
        Assert.assertEquals(missingInformationPage.externalAppIDValue.getText(), extAppID, "External Application Id does not match the External Application Id value shown in APP INFO panel: ");
        Assert.assertEquals(missingInformationPage.priorityValue.getText(), priority, "Incorrect Priority value found in App Info Panel inside Missing Information Tab");
        Assert.assertEquals(missingInformationPage.signatureDateValue.getText(), signatureDate, "Incorrect Signature date found in App Info Panel inside Missing Information Tab");
        Assert.assertEquals(missingInformationPage.receivedDateValue.getText(), receivedDate, "Incorrect Received date found in App Info Panel inside Missing Information Tab");
        Assert.assertEquals(missingInformationPage.channelValue.getText(), channel, "Incorrect Channel found in App Info Panel inside Missing Information Tab");
        Assert.assertEquals(missingInformationPage.createDateValue.getText(), getCurrentDate(), "Incorrect Create date found in App Info Panel inside Missing Information Tab");
        Assert.assertEquals(missingInformationPage.lastUpdatedDateValue.getText(), getCurrentDate(), "Incorrect Last Updated date found in App Info Panel inside Missing Information Tab");
        if ("MEDICAL ASSISTANCE".equals(appType)) {
            Assert.assertEquals(missingInformationPage.applicationTypeValue.getText(), "Medical Assistance", "Expected Medical Assistance for Application Type in App Info Panel inside Missing Information Tab");
            Assert.assertEquals(missingInformationPage.cycleValue.getText(), cycle, "Incorrect Application Cycle found in App Info Panel inside Missing Information Tab");
            Assert.assertEquals(missingInformationPage.deadlineDateValue.getText(), getGreaterDateFormatMMddyyyy(45), "Incorrect Deadline Date found in App Info Panel inside Missing Information Tab");
        } else if ("LONG TERM CARE".equals(appType)) {
            Assert.assertEquals(missingInformationPage.applicationTypeValue.getText(), "Long Term Care", "Expected Long Term Care for Application Type in App Info Panel inside Missing Information Tab");
            Assert.assertEquals(missingInformationPage.cycleValue.getText(), cycle, "Incorrect Application Cycle found in App Info Panel inside Missing Information Tab");
            Assert.assertEquals(missingInformationPage.deadlineDateValue.getText(), getGreaterDateFormatMMddyyyy(90), "Incorrect Deadline Date found in App Info Panel inside Missing Information Tab");
        } else {
            Assert.fail("Provided incorrect Application type");
        }
    }

    @And("I click on the back arrow in header row next to the icon Primary Individual name and application id on Missing Information Panel")
    public void iClickOnTheBackArrowInHeaderRowNextToTheIconPrimaryIndividualNameAndApplicationIdOnMissingInformationPanel() {
        missingInformationPage.backArrow.click();
    }

    @Then("I verify application is cleared from view for Missing Information Panel")
    public void iVerifyApplicationIsClearedFromViewForMissingInformationPanel() {
        Assert.assertFalse(quickIsDisplayed(missingInformationPage.applicationIcon), "Application is still in view after clicking on back arrow in Application tracking Tab");
    }

    @Then("I verify the labels in the Missing information Details panel labels and button")
    public void iVerifyTheLabelsInTheMissingInformationDetailsPanelLabelsAndButton() {
        for (int i = 1; i < 7; i++) {
            actualMIdetailsHeader.get().add(missingInformationPage.missingInfoLabels.get(i).getText());
        }
        Collections.sort(actualMIdetailsHeader.get());
        Collections.sort(expectedMIdetailsHeader.get());
        Assert.assertEquals(expectedMIdetailsHeader.get(), actualMIdetailsHeader.get(), "Mismatch in Missing Info Details panel Header List: ");
        missingInformationPage.addMissingInfoButton.click();
        waitFor(2);
        Assert.assertTrue(missingInformationPage.miSaveButton.isEnabled(), "Missing Save button in Missing Info Details Panel");
        Assert.assertTrue(missingInformationPage.miCloseButton.isEnabled(), "Missing Close/Cancel button in Missing Info Details Panel");
    }

    @And("I click on ADD MISSING INFO Button in Application Missing Info Tab")
    public void iClickOnADDMISSINGINFOButtonInApplicationMissingInfoTab() {
        waitForClickablility(missingInformationPage.addMissingInfoButton, 10);
        missingInformationPage.addMissingInfoButton.click();
        waitFor(1);
    }

    @And("I select {string} dropdown in MISSING INFO DETAILS panel to select")
    public List<String> iSelectDropdownInMISSINGINFODETAILSPanelToSelect(String dropdownType, List<String> data) {
        switch (dropdownType) {
            case "CATEGORY":
                strOptions.get().clear();
                if ("CLICK".equals(data.get(0))) {
                    missingInformationPage.miAddCategory.click();
                    waitFor(2);
                    strOptions.set(convertToStrList(Driver.getDriver().findElements(By.cssSelector("li[data-value]"))));
                } else {
                    waitForClickablility(missingInformationPage.miAddCategory, 2);
                    selectDropDownNoCheck(missingInformationPage.miAddCategory, data.get(0));
                }
                miExpectedMissingInfo.put("entityRecordType", data.get(0));
                waitFor(3);
                return strOptions.get();
            case "TYPE":
                strOptions.get().clear();
                if ("CLICK".equals(data.get(0))) {
                    missingInformationPage.miAddType.click();
                    waitFor(2);
                    strOptions.set(convertToStrList(Driver.getDriver().findElements(By.cssSelector("li[data-value]"))));
                } else {
                    missingInfoMultiDropdownSelector(missingInformationPage.miAddType, data);
                }
                waitFor(2);
                action.moveToElement(createApplicationPage.missingInfoTab).doubleClick(createApplicationPage.missingInfoTab).build().perform();
                miExpectedMissingInfo.put("attributeName", data.get(0));
                waitFor(3);
                return strOptions.get();
            case "FROM":
                strOptions.get().clear();
                if ("CLICK".equals(data.get(0))) {
                    missingInformationPage.miAddFrom.click();
                    waitFor(2);
                    strOptions.set(convertToStrList(Driver.getDriver().findElements(By.cssSelector("li[data-value]"))));
                } else {
                    List<String> fromApplicantToSelect = new ArrayList<>();
                    for (String eachAM : data) {
                        switch (eachAM) {
                            case "PRIMARY":
                                fromApplicantToSelect.add(applicantList.get().get(0));
                                break;
                            case "APP MEM ONE":
                                fromApplicantToSelect.add(applicantList.get().get(1));
                                break;
                            case "APP MEM TWO":
                                fromApplicantToSelect.add(applicantList.get().get(2));
                                break;
                            default:
                                fail("Provided value to check did not match");
                        }
                    }
                    missingInfoMultiDropdownSelector(missingInformationPage.miAddFrom, fromApplicantToSelect);
                    waitFor(2);
                    action.moveToElement(createApplicationPage.missingInfoTab).doubleClick(createApplicationPage.missingInfoTab).build().perform();
                }
                waitFor(3);
                return strOptions.get();
            case "NEED FOR":
                strOptions.get().clear();
                if ("CLICK".equals(data.get(0))) {
                    missingInformationPage.miAddNeedFor.click();
                    waitFor(2);
                    strOptions.set(convertToStrList(Driver.getDriver().findElements(By.cssSelector("li[data-value]"))));
                } else {
                    List<String> needForApplicantToSelect = new ArrayList<>();
                    for (String eachAM : data) {
                        switch (eachAM) {
                            case "PRIMARY":
                                needForApplicantToSelect.add(applicantList.get().get(0));
                                break;
                            case "APP MEM ONE":
                                needForApplicantToSelect.add(applicantList.get().get(1));
                                break;
                            case "APP MEM TWO":
                                needForApplicantToSelect.add(applicantList.get().get(2));
                                break;
                        }
                    }
                    missingInfoMultiDropdownSelector(missingInformationPage.miAddNeedFor, needForApplicantToSelect);
                    waitFor(2);
                    action.moveToElement(createApplicationPage.missingInfoTab).doubleClick(createApplicationPage.missingInfoTab).build().perform();
                }
                waitFor(3);
                return strOptions.get();
            default:
                throw new RuntimeException("Error. Invalid dropdown field name");
        }
    }

    private List<String> convertToStrList(List<WebElement> options) {
        List<String> strOptions = new ArrayList<>();
        options.forEach(option -> {
            strOptions.add(option.getText().trim());
        });
        return strOptions;
    }

    public List<String> missingInfoMultiDropdownSelector(WebElement element, List<String> selector) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
        waitFor(1);
        strOptions.set(convertToStrList(Driver.getDriver().findElements(By.cssSelector("li[data-value]"))));
        hover(element);
        for (String each : selector) {
            WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + each + "')]"));
            scrollToElement(single);
            hover(single);
            single.click();
            waitFor(1);
        }
        return strOptions.get();
    }

    @And("I gather application consumer full name data information for Missing Info services")
    public List<String> iGatherApplicationConsumerFullNameDataInformationForMissingInfoServices() {
        waitFor(2);
        waitForVisibility(createApplicationPage.savedListApplicantFullName.get(0), 5);
        for (WebElement webElement : createApplicationPage.savedListApplicantFullName) {
            applicantList.get().add(webElement.getText());
        }
        return applicantList.get();
    }

    @Then("I verify the following values for {string} dropdown values for Missing Info add")
    public void iVerifyTheFollowingValuesForDropdownValuesForMissingInfoAdd(String dropdownType, List<String> data) {
        switch (dropdownType) {
            case "CATEGORY":
                List<String> expectedCategory = new ArrayList<>(data);
                List<String> actualCategory = new ArrayList<>(strOptions.get());
                Collections.sort(expectedCategory);
                Collections.sort(actualCategory);
                Assert.assertEquals(actualCategory, expectedCategory);
                break;
            case "TYPE":
                List<String> expectedType = new ArrayList<>(data);
                List<String> actualType = new ArrayList<>(strOptions.get());
                Collections.sort(expectedType);
                Collections.sort(actualType);
                Assert.assertEquals(actualType, expectedType);
                break;
            case "FROM":
                List<String> expectedFrom = new ArrayList<>();
                expectedFrom.add("");
                List<String> actualFrom = new ArrayList<>(strOptions.get());
                for (String eachAM : data) {
                    switch (eachAM) {
                        case "PRIMARY":
                            expectedFrom.add(applicantList.get().get(0));
                            break;
                        case "APP MEM ONE":
                            expectedFrom.add(applicantList.get().get(1));
                            break;
                        case "APP MEM TWO":
                            expectedFrom.add(applicantList.get().get(2));
                            break;
                    }
                }
                Collections.sort(expectedFrom);
                Collections.sort(actualFrom);
                Assert.assertEquals(actualFrom, expectedFrom);
                break;
            case "NEED FOR":
                List<String> expectedFor = new ArrayList<>();
                expectedFor.add("");
                List<String> actualFor = new ArrayList<>(strOptions.get());
                for (String eachAM : data) {
                    switch (eachAM) {
                        case "PRIMARY":
                            expectedFor.add(applicantList.get().get(0));
                            break;
                        case "APP MEM ONE":
                            expectedFor.add(applicantList.get().get(1));
                            break;
                        case "APP MEM TWO":
                            expectedFor.add(applicantList.get().get(2));
                            break;
                    }
                }
                Collections.sort(expectedFor);
                Collections.sort(actualFor);
                Assert.assertEquals(actualFor, expectedFor);
                break;
            case "USER ICON":
                List<String> expectedUserIcon = new ArrayList<>(data);
                List<String> actualUserIcon = new ArrayList<>(iterateMissingInfoRowValue(missingInformationPage.userIconSelectMenuList));
                Collections.sort(expectedUserIcon);
                Collections.sort(actualUserIcon);
                Assert.assertEquals(actualUserIcon, expectedUserIcon);
                break;
            default:
                Assert.fail("Error. Invalid dropdown field name");
        }
    }

    @And("I click on {string} button for Missing Info in Application Missing Info tab")
    public void iClickOnButtonForMissingInfoInApplicationMissingInfoTab(String buttonType) {
        switch (buttonType) {
            case "SAVE":
                waitForClickablility(missingInformationPage.miSaveButton, 5);
                missingInformationPage.miSaveButton.click();
                waitForElementToDisappear(missingInformationPage.miAddSuccessMsg, 7);
                missingInformationStatus.set("PENDING");
                break;
            case "CANCEL":
                waitForClickablility(missingInformationPage.miCloseButton, 5);
                missingInformationPage.miCloseButton.click();
                break;
            case "USER ICON":
                waitForClickablility(missingInformationPage.userIcon, 5);
                missingInformationPage.userIcon.click();
                break;
            case "ROW ONE CARROT":
                waitForClickablility(missingInformationPage.listOfChevronRightButtons.get(0), 5);
                missingInformationPage.listOfChevronRightButtons.get(0).click();
                break;
            case "ROW TWO CARROT":
                missingInformationPage.listOfArrowDown.get(0).click();
                waitFor(1);
                waitForClickablility(missingInformationPage.listOfChevronRightButtons.get(1), 5);
                missingInformationPage.listOfChevronRightButtons.get(1).click();
                break;
            case "ROW THREE CARROT":
                missingInformationPage.listOfArrowDown.get(0).click();
                waitFor(1);
                waitForClickablility(missingInformationPage.listOfChevronRightButtons.get(2), 5);
                missingInformationPage.listOfChevronRightButtons.get(2).click();
                break;
            case "SAVE ONLY":
                waitForClickablility(missingInformationPage.miSaveButton, 5);
                missingInformationPage.miSaveButton.click();
                waitFor(1);
                break;
            default:
                Assert.fail("Entered button type did not match");
        }
    }

    @And("I click on Missing Info comment field to enter {string}")
    public String iClickOnMissingInfoCommentFieldToEnter(String commentValue) {
        comment.set("");
        waitForClickablility(missingInformationPage.miAddComments, 5);
        switch (commentValue.toUpperCase()) {
            case "MAX 500 CHAR COUNT":
                for (int i = 0; i < 25; i++) {
                    comment.set(comment.get() + "Lorem ipsum dolor s ");
                }
                missingInformationPage.miAddComments.click();
                waitFor(1);
                missingInformationPage.miAddComments.sendKeys(comment.get());
                break;
            case "200 CHAR COUNT":
                for (int i = 0; i < 10; i++) {
                    comment.set(comment.get() + "Lorem ipsum dolor s ");
                }
                missingInformationPage.miAddComments.click();
                waitFor(1);
                missingInformationPage.miAddComments.sendKeys(comment.get());
                break;
            case "RANDOM TEN":
                comment.set(RandomStringUtils.randomAlphabetic(10));
                missingInformationPage.miAddComments.click();
                waitFor(1);
                missingInformationPage.miAddComments.sendKeys(comment.get());
                break;
            case "RANDOM 30":
                if ("Random 30".equalsIgnoreCase(commentValue)) {
                    comment.set(getRandomString(30));
                }
                missingInformationPage.miAddComments.click();
                waitFor(1);
                missingInformationPage.miAddComments.sendKeys(comment.get());
                miExpectedMissingInfo.put("comments", comment.get());
                break;
            default:
                comment.set(commentValue);
                missingInformationPage.miAddComments.click();
                waitFor(1);
                missingInformationPage.miAddComments.sendKeys(comment.get());
                break;
        }
        waitFor(2);
        action.moveToElement(createApplicationPage.missingInfoTab).doubleClick(createApplicationPage.missingInfoTab).build().perform();
        return comment.get();
    }

    @Then("I verify {string} comment box for successful Missing Info entity save")
    public void iVerifyCommentBoxForSuccessfulMissingInfoEntitySave(String verifyType) {
        waitFor(5);
        switch (verifyType.toUpperCase()) {
            case "EMPTY":
                comment.set("");
                String actualComment = missingInformationPage.savedMissingInfoListRowOne.get(5).getText();
                Assert.assertEquals(actualComment, comment, "Expected no comments but found: " + actualComment);
                break;
            case "MAX 500 CHAR COUNT":
                String actualShownMaxComment = missingInformationPage.savedMissingInfoListRowOne.get(5).getText().replace("more_horiz", "").trim();
                Assert.assertTrue(actualShownMaxComment.length() == 250, "Expected 250 Characters but found: " + actualShownMaxComment.length());
                Assert.assertEquals(actualShownMaxComment, comment.get().substring(0, 250));
                Assert.assertTrue(filterForLettersOnly(comment.get().substring(0, 250)).equals(filterForLettersOnly(actualShownMaxComment)), "Expected Max allotted comments but found: " + actualShownMaxComment);
                hover(missingInformationPage.viewAllCommentbutton);
                waitFor(2);
                String actualShownMaxCommentInPopUP = missingInformationPage.viewMIcommentPopUp.getText();
                Assert.assertEquals(actualShownMaxCommentInPopUP.trim(), comment.get().trim(), "Expected Max allotted comments but found: " + actualShownMaxComment);
                break;
            case "RANDOM TEN":
                String actualRandomTen = missingInformationPage.savedMissingInfoListRowOne.get(5).getText().replace("more_horiz", "").trim();
                Assert.assertEquals(actualRandomTen, comment.get());
                break;
            case "RANDOM TEN EDIT AGAIN":
                String actualRandomTenEditAgain = missingInformationPage.savedMissingInfoListRowOne.get(5).getText().replace("more_horiz", "").trim();
                Assert.assertEquals(actualRandomTenEditAgain, comment.get());
                break;
            default:
                Assert.fail("Entered verify type did not match");
        }
    }

    @And("I Verify Missing Information Save {string} for following data")
    public void iVerifyMissingInformationSaveForFollowingData(String row, List<Map<String, String>> data) {
        List<String> actualValueList = new ArrayList<>();
        switch (row) {
            case "ROW ONE":
                waitFor(2);
                waitForVisibility(missingInformationPage.savedMissingInfoListRowOne.get(1), 10);
                actualValueList.addAll(iterateMissingInfoRowValue(missingInformationPage.savedMissingInfoListRowOne));
                break;
            case "ROW TWO":
                waitForVisibility(missingInformationPage.savedMissingInfoListRowTwo.get(1), 10);
                actualValueList.addAll(iterateMissingInfoRowValue(missingInformationPage.savedMissingInfoListRowTwo));
                break;
        }
        String actualFromAppConsumer = actualValueList.get(3);
        String actualNeedForAppConsumer = actualValueList.get(4);
        Map<String, String> expectedData = data.get(0);

        for (String expected : expectedData.keySet()) {
            switch (expected) {
                case "CATEGORY":
                    Assert.assertEquals(actualValueList.get(1), expectedData.get(expected));
                    break;
                case "TYPE":
                    Assert.assertEquals(actualValueList.get(2), expectedData.get(expected));
                    break;
                case "FROM":
                    switch (expectedData.get(expected)) {
                        case "PRIMARY":
                            Assert.assertEquals(actualFromAppConsumer, applicantList.get().get(0));
                            break;
                        case "APP MEM ONE":
                            Assert.assertEquals(actualFromAppConsumer, applicantList.get().get(1));
                            break;
                        default:
                            Assert.fail("Provided data does not match expected value");
                    }
                    break;
                case "NEED FOR":
                    switch (expectedData.get(expected)) {
                        case "PRIMARY":
                            Assert.assertEquals(actualNeedForAppConsumer, applicantList.get().get(0));
                            break;
                        case "APP MEM ONE":
                            Assert.assertEquals(actualNeedForAppConsumer, applicantList.get().get(1));
                            break;
                        default:
                            Assert.fail("Provided data does not match expected value");
                    }
                    break;
                case "STATUS":
                    highLightElement(missingInformationPage.rowOneStatus);
                    Assert.assertEquals(actualValueList.get(6).toLowerCase(), expectedData.get(expected).toLowerCase());
                    if ("ROW ONE".equals(row) && "PENDING".equalsIgnoreCase(expectedData.get(expected))) {
                        Assert.assertTrue(missingInformationPage.savedMIStatusColumn.get(0).getAttribute("class").contains("red"));
                    } else if ("ROW TWO".equals(row) && "PENDING".equals(expectedData.get(expected))) {
                        Assert.assertTrue(missingInformationPage.savedMIStatusColumn.get(1).getAttribute("class").contains("red"));
                    } else if ("ROW ONE".equals(row) && "SATISFIED".equals(expectedData.get(expected))) {
                        Assert.assertTrue(missingInformationPage.savedMIStatusColumn.get(0).getAttribute("class").contains("green"));
                    } else if ("ROW ONE".equals(row) && "DISREGARDED".equals(expectedData.get(expected))) {
                        BrowserUtils.verifyColorOfElement(missingInformationPage.rowOneStatus, "#000000");
                    }

                    break;
            }
        }
    }

    public List<String> iterateMissingInfoRowValue(List<WebElement> missingInfoRow) {
        List<String> actual = new ArrayList<>();
        for (WebElement webElement : missingInfoRow) {
            actual.add(webElement.getText());
        }
        return actual;
    }

    @Then("I verify Missing Info save panel disappears and values have been cleared")
    public void iVerifyMissingInfoSavePanelDisappearsAndValuesHaveBeenCleared() {
        waitFor(2);
        Assert.assertFalse(quickIsDisplayed(missingInformationPage.miAddCategory));
        Assert.assertFalse(quickIsDisplayed(missingInformationPage.miAddType));
        Assert.assertFalse(quickIsDisplayed(missingInformationPage.miAddFrom));
        Assert.assertFalse(quickIsDisplayed(missingInformationPage.miAddNeedFor));
        Assert.assertFalse(quickIsDisplayed(missingInformationPage.miAddComments));
    }

    @Override
    public boolean quickIsDisplayed(WebElement element) {
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        try {
            Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            if (element.isDisplayed()) return true;
        } catch (Exception exception) {
            Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            return false;
        }
        return false;
    }

    @Then("I verify the required error message for dropdown values for failed Missing Info Save")
    public void iVerifyTheRequiredErrorMessageForDropdownValuesForFailedMissingInfoSave() {
        waitForVisibility(missingInformationPage.requiredWarningList.get(1), 10);
        List<String> actualReqMsgList = new ArrayList<>(iterateMissingInfoRowValue(missingInformationPage.requiredWarningList));
        Assert.assertEquals(actualReqMsgList.get(0), "CATEGORY is required and cannot be left blank", "Mismatch in Expected Required message");
        Assert.assertEquals(actualReqMsgList.get(1), "TYPE is required and cannot be left blank", "Mismatch in Expected Required message");
        Assert.assertEquals(actualReqMsgList.get(2), "FROM is required and cannot be left blank", "Mismatch in Expected Required message");
        Assert.assertEquals(actualReqMsgList.get(3), "NEED FOR is required and cannot be left blank", "Mismatch in Expected Required message");
    }

    @And("I verify saved Missing Info NEED FOR consumer applicants by hovering over by the icon next to NEED FOR value")
    public void iVerifySavedMissingInfoNEEDFORConsumerApplicantsByHoveringOverByTheIconNextToNEEDFORValue() {
        waitForVisibility(missingInformationPage.savedMINeedForColumn.get(0), 10);
        hover(missingInformationPage.rowOneNeedForIcon);
        waitFor(2);
        List<String> actualSavedNeedForList = new ArrayList<>(iterateMissingInfoRowValue(missingInformationPage.needForHoverConsumerList));
        actualSavedNeedForList.add(missingInformationPage.savedMINeedForColumn.get(0).getText().replace("+2", "").trim());
        Collections.sort(actualSavedNeedForList);
        Collections.sort(applicantList.get());
        Assert.assertEquals(actualSavedNeedForList, applicantList.get());
        Assert.assertTrue(missingInformationPage.rowOneNeedForIcon.getText().contains("+2"), "Expected additonal consumer number +2 but found: " + missingInformationPage.rowOneNeedForIcon.getText());
    }

    @And("I save the following Missing Information data value for verification")
    public void iSaveTheFollowingMissingInformationDataValueForVerification(List<String> mISaveValues) {
        waitFor(2);
        for (String mISaveValue : mISaveValues) {
            switch (mISaveValue) {
                case "MI ID#":
                    waitForVisibility(missingInformationPage.iDValue, 10);
                    savedMIValues.get().add(missingInformationPage.iDValue.getText());
                    break;
            }
        }
    }

    @And("I verify the {string} in expanded Missing Information save")
    public void iVerifyTheInExpandedMissingInformationSave(String verifyType, List<String> expectedList) {
        waitForVisibility(missingInformationPage.iDLabel, 10);
        switch (verifyType) {
            case "LABELS":
                Assert.assertEquals(missingInformationPage.iDLabel.getText(), expectedList.get(0));
                Assert.assertEquals(missingInformationPage.createdByLabel.getText(), expectedList.get(1));
                Assert.assertEquals(missingInformationPage.createdOnLabel.getText(), expectedList.get(2));
                Assert.assertEquals(missingInformationPage.updatedByLabel.getText(), expectedList.get(3));
                Assert.assertEquals(missingInformationPage.updatedOnLabel.getText(), expectedList.get(4));
                break;
            case "SATISFY LABELS":
                Assert.assertEquals(missingInformationPage.iDLabel.getText(), expectedList.get(0));
                Assert.assertEquals(missingInformationPage.createdByLabel.getText(), expectedList.get(1));
                Assert.assertEquals(missingInformationPage.createdOnLabel.getText(), expectedList.get(2));
                Assert.assertEquals(missingInformationPage.completedByLabel.getText(), expectedList.get(3));
                Assert.assertEquals(missingInformationPage.completedOnLabel.getText(), expectedList.get(4));
                break;
            case "DISREGARDED LABELS":
                Assert.assertEquals(missingInformationPage.iDLabel.getText(), expectedList.get(0));
                Assert.assertEquals(missingInformationPage.createdByLabel.getText(), expectedList.get(1));
                Assert.assertEquals(missingInformationPage.createdOnLabel.getText(), expectedList.get(2));
                Assert.assertEquals(missingInformationPage.completedByLabel.getText(), expectedList.get(3));
                Assert.assertEquals(missingInformationPage.completedOnLabel.getText(), expectedList.get(4));
                break;
            case "VALUES":
                for (String each : expectedList) {
                    switch (each) {
                        case "CREATED BY":
                            Assert.assertEquals(missingInformationPage.createdByValue.getText(), APIATSApplicationController.loggedInUserId);
                            break;
                        case "CREATED ON":
                            Assert.assertEquals(missingInformationPage.createdOnValue.getText(), getCurrentDate());
                            break;
                        case "UPDATED BY":
                            Assert.assertEquals(missingInformationPage.updatedByValue.getText(), APIATSApplicationController.loggedInUserId);
                            break;
                        case "UPDATED ON":
                            Assert.assertEquals(missingInformationPage.updatedOnValue.getText(), getCurrentDate());
                            break;
                        case "COMPLETED BY":
                            Assert.assertEquals(missingInformationPage.completedByValue.getText(), APIATSApplicationController.loggedInUserId, "Expected API  user ID " + APIATSApplicationController.loggedInUserId + " but found unexpected.");
                            break;
                        case "COMPLETED ON":
                            Assert.assertEquals(missingInformationPage.completedOnValue.getText(), getCurrentDate());
                            break;
                        case "COMPLETED BY ATS":
                            Assert.assertEquals(missingInformationPage.completedByValue.getText(), "ATS Service", "Expected API  user ID is ATS Service but found unexpected.");
                            break;
                    }
                }
                break;
            case "UPDATE ON AND BY":
                waitFor(2);
                for (String each : expectedList) {
                    switch (each) {
                        case "CREATED BY":
                            Assert.assertEquals(missingInformationPage.createdByValue.getText(), "2492", "Expected API Created user ID 2492 but found unexpected.");
                            break;
                        case "CREATED ON":
                            Assert.assertEquals(missingInformationPage.createdOnValue.getText(), getCurrentDate());
                            break;
                        case "UPDATED BY":
                            Assert.assertEquals(missingInformationPage.updatedByValue.getText(), APIATSApplicationController.loggedInUserId, "Expected user log in ID: " + APIATSApplicationController.loggedInUserId + " but found unexpected");
                            break;
                        case "UPDATED ON":
                            Assert.assertEquals(missingInformationPage.updatedOnValue.getText(), getCurrentDate());
                            break;
                    }
                }
                break;
            default:
                fail("Provided value to check did not match");
        }
    }

    @Then("I verify Missing Information Details panel Pagination with Multiple saved data values")
    public void iVerifyMissingInformationDetailsPanelPaginationWithMultipleSavedDataValues() {
        List<String> actualCategory = new ArrayList<>();
        List<String> actualType = new ArrayList<>();
        List<String> actualFrom = new ArrayList<>();
        List<String> actualNeedFor = new ArrayList<>();
        List<String> actualComments = new ArrayList<>();
        List<String> actualStatus = new ArrayList<>();

        boolean isOnLastPage = false;
        do {
            waitFor(1);
            actualCategory.addAll(iterateMissingInfoRowValue(missingInformationPage.savedMICategoryColumn));
            actualType.addAll(iterateMissingInfoRowValue(missingInformationPage.savedMITypeColumn));
            actualFrom.addAll(iterateMissingInfoRowValue(missingInformationPage.savedMIFromColumn));
            actualNeedFor.addAll(iterateMissingInfoRowValue(missingInformationPage.savedMINeedForColumn));
            actualComments.addAll(iterateMissingInfoRowValue(missingInformationPage.savedMICommentColumn));
            actualStatus.addAll(iterateMissingInfoRowValue(missingInformationPage.savedMIStatusColumn));
            if (isOnLastPage()) {
                isOnLastPage = true;
            }
            goToNextPage();
        } while (!isOnLastPage);

        assertEquals(actualCategory.size(), 12, "Expected twelve saved Category Missing Information but found: " + actualCategory.size());
        assertEquals(actualType.size(), 12, "Expected twelve saved Type Missing Information but found: " + actualCategory.size());
        assertEquals(actualFrom.size(), 12, "Expected twelve saved From Missing Information but found: " + actualCategory.size());
        assertEquals(actualNeedFor.size(), 12, "Expected twelve saved Need For Missing Information but found: " + actualCategory.size());
        assertEquals(actualComments.size(), 12, "Expected twelve saved Comments Missing Information but found: " + actualCategory.size());
        assertEquals(actualStatus.size(), 12, "Expected twelve saved Missing Information but found: " + actualCategory.size());

        actualCategory.forEach((each) -> assertEquals(each, "Application Consumer", "Mismatch in Category list. Expected: Application Consumer but found " + each));
        actualType.forEach((each) -> assertTrue("SSN".equals(each) || "Gender".equals(each) || "Last Name".equals(each) || "First Name".equals(each) || "DOB".equals(each) || "Citizenship Verification".equals(each), "MisType found in Type List"));
        actualFrom.forEach((each) -> assertTrue(applicantList.get().get(0).equals(each) || applicantList.get().get(1).equals(each), "Incorrect FROM consumer name found in FROM List"));
        actualNeedFor.forEach((each) -> assertEquals(each, applicantList.get().get(0), "Incorrect NEED FOR consumer name found in NEED FOR List"));
        actualComments.forEach((each) -> assertEquals(each, comment.get(), "Incorrect Comment in list. Expected: ***" + comment.get() + "*** but found " + each));
        actualStatus.forEach((each) -> assertEquals(each, "PENDING", "Incorrect Status in list. Expected: PENDING but found " + each));
    }

    public boolean isOnLastPage() {
        return !quickIsDisplayed(missingInformationPage.nextPageArrowKeyButton);
    }

    public void goToNextPage() {
        if (!isOnLastPage()) {
            missingInformationPage.nextPageArrowKeyButton.click();
            waitFor(3);
        }
    }

    @And("I save the following Missing Information data value from {string} for verification")
    public void iSaveTheFollowingMissingInformationDataValueFromForVerification(String rowNum, List<String> mISaveValues) {

        for (String mISaveValue : mISaveValues) {
            switch (mISaveValue) {
                case "MI ID#":
                    waitForVisibility(missingInformationPage.iDValue, 10);
                    savedMIValues.get().add(missingInformationPage.iDValue.getText());
                    break;
            }
        }
    }

    @And("I click on the {string} from the User Icon menu for Missing Info Save")
    public void iClickOnTheFromTheUserIconMenuForMissingInfoSave(String userIconDropSelect) {
        switch (userIconDropSelect) {
            case "EDIT COMMENT":
                jsClick(missingInformationPage.editCommentSelect);
                break;
            case "SATISFY":
                jsClick(missingInformationPage.satisfySelect);
                missingInformationStatus.set("SATISFIED");
                break;
            case "DISREGARD":
                jsClick(missingInformationPage.disregardSelect);
                missingInformationStatus.set("DISREGARDED");
                break;
            default:
                fail("Entered User Icon Select Type mismatch");
        }

    }

    @And("I enter {string} character comments in to the edit comment box in Missing Info Panel")
    public void iEnterCharacterCommentsInToTheEditCommentBoxInMissingInfoPanel(String commentType) {
        waitForClickablility(missingInformationPage.editCommentTextBox, 5);
        switch (commentType) {
            case "MAX 500 CHAR COUNT":
                for (int i = 0; i < 25; i++) {
                    comment.set(comment.get()+ "Lorem ipsum dolor s ");
                }
                action.moveToElement(missingInformationPage.editCommentTextBox, 18, 0).click().build();
                missingInformationPage.editCommentTextBox.sendKeys(comment.get());
                break;
            case "Additional 300 characters":
                String addComment = "";
                for (int i = 0; i < 15; i++) {
                    addComment += "Lorem ipsum dolor s ";
                }
                action.moveToElement(missingInformationPage.editCommentTextBox, 18, 0).click().build();
                missingInformationPage.editCommentTextBox.sendKeys(addComment);
                comment.set(comment.get() + addComment);
                break;
            case "RANDOM TEN":
                additionalMiComment.set(RandomStringUtils.randomAlphabetic(10));
                action.moveToElement(missingInformationPage.editCommentTextBox, 18, 0).click().build();
                waitFor(1);
                missingInformationPage.editCommentTextBox.sendKeys(additionalMiComment.get());
                break;
            case "MORE THAN MAX 500 CHAR":
                for (int i = 0; i < 25; i++) {
                    comment.set(comment.get() + "Lorem ipsum dolor s ");
                }
                action.moveToElement(missingInformationPage.editCommentTextBox, 18, 0).click().build();
                missingInformationPage.editCommentTextBox.sendKeys(comment.get() + "12345678910");
                break;
            case "RANDOM TEN EDIT AGAIN":
                String additionComment = RandomStringUtils.randomAlphanumeric(10);
                action.moveToElement(missingInformationPage.editCommentTextBox, 18, 0).click().build();
                waitFor(1);
                missingInformationPage.editCommentTextBox.sendKeys(additionComment);
                comment.set(comment.get() + additionComment);
                break;
            case "RANDOM 489 AGAIN":
                for (int i = 0; i < 24; i++) {
                    comment.set(comment.get() + "Lorem ipsum dolor s ");
                }
                comment.set(comment.get() + "Lorem ips");
                action.moveToElement(missingInformationPage.editCommentTextBox, 18, 0).click().build();
                waitFor(1);
                missingInformationPage.editCommentTextBox.sendKeys(comment.get());
                break;
            default:
                fail("Entered Comment Type mismatch");
        }
    }

    @And("I Click on {string} for Missing Info Details panel EDIT COMMENT")
    public void iClickOnForMissingInfoDetailsPanelEDITCOMMENT(String editCommentBtnType) {
        switch (editCommentBtnType) {
            case "SAVE":
                missingInformationPage.editCommentTextBoxSave.click();
                break;
            case "CANCEL":
                missingInformationPage.editCommentTextBoxClear.click();
                break;
            default:
                fail("Entered Comment Type mismatch");
        }
    }

    @And("I verify Missing Info Panel EDIT COMMENT section has collapsed")
    public void iVerifyMissingInfoPanelEDITCOMMENTSectionHasCollapsed() {
        waitFor(3);
        assertFalse(quickIsDisplayed(missingInformationPage.editCommentTextBox));
    }

    @Then("I verify missing information flag is not displayed")
    public void iVerifyMIFlagNotDisplayed() {
        assertTrue(applicationTrackingPage.missingInfoFlag.isEmpty());
        boolean notDisplayed = false;
        try {
            assertFalse(applicationTrackingPage.miTabFlag.isDisplayed());
        } catch (Exception e) {
            notDisplayed = true;
        }
        assertTrue(notDisplayed, "Mi Tab Icon should not be displayed");
    }

    @Then("I verify the missing information icon displayed")
    public void iVerifyMissingInfoIconFor(List<String> data) {
        WebElement flag;
        for (String eachVerifyValue : data) {
            waitFor(1);
            assertTrue(data.size() == applicationTrackingPage.missingInfoFlag.size());
            switch (eachVerifyValue.toUpperCase()) {
                case "APPLICATION":
                    assertTrue(applicationTrackingPage.missingInfoFlag.get(0).isDisplayed());
                    break;
                case "PRIMARY INDIVIDUAL":
                    //  flag = Driver.getDriver().findElement(By.xpath("//*[text()='"+applicantList.get().get(0)+"']/preceding-sibling::td//*[@title='Missing Info Indicator']"));
                    assertTrue(applicationTrackingPage.primaryIndividualMIFlag.isDisplayed());
                    break;
                case "APPLICATION MEMBER":
                    assertTrue(applicationTrackingPage.appMemberMIFlag.isDisplayed());
                    break;
            }
        }
    }

    @Then("I verify Missing Information tab is highlighted with MI icon")
    public void iVerifyMissingInformationTabIsHighlightedWithMIIcon() {
        assertTrue(applicationTrackingPage.miTabFlag.isDisplayed());
    }

    @Then("I verify the Missing Info user icon is disabled")
    public void iVerifyTheMissingInfoUserIconIsDisabled() {
        waitFor(3);
        missingInformationPage.userIcon.click();
        assertFalse(quickIsDisplayed(missingInformationPage.satisfySelect), "Expected User Icon disabled but found Satisfy option found");
    }

    @Then("I verify Satisfy-Disregarded missing Info Comment is appended above the existing comment")
    public void iVerifySatisfyDisregardedMissingInfoCommentIsAppendedAboveTheExistingComment() {
        Assert.assertEquals(missingInformationPage.firstAppendedComment.getText(), additionalMiComment.get(), "Additional comment is not displayed before the existing comment");
        Assert.assertEquals(missingInformationPage.secondAppendedComment.getText() + " ", comment.get(), "Existing comment is not displayed after the new comment");
    }

    @Then("I verify user icon is disabled and Disregarded color is black for Application Missing Info tab")
    public void iVerifyUserIconIsDisabledAndDisregardedColorIsBlackForApplicationMissingInfoTab() {
        waitFor(2);
        missingInformationPage.userIcon.click();
        Assert.assertEquals(missingInformationPage.rowOneStatus.getText(), "DISREGARDED", "Missing Info Status is not disregarded");
        Assert.assertFalse(missingInformationPage.disregardSelect.isDisplayed(), "User Icon is not disable for Application Missing Information Tab");
        BrowserUtils.verifyColorOfElement(missingInformationPage.rowOneStatus, "#000000");
    }

    @And("I verify user stays on Application Missing Info tab")
    public void iVerifyUserStaysOnApplicationMissingInfoTab() {
        Assert.assertTrue(missingInformationPage.missingInfoDetailsHeader.isDisplayed(), "User is not on the Missing Info Tab , Missing Info Details Header isn't displayed");
    }

    @And("I verify {string} warning message in Application Missing Info tab")
    public void iVerifyWarningMessageInApplicationMissingInfoTab(String warningMessage) {
        waitFor(2);
        switch (warningMessage.toUpperCase()) {
            case "COMMENT SECTION IS REQUIRED AND CANNOT BE LEFT BLANK":
                waitForVisibility(missingInformationPage.commentSectionWarningMessage, 2);
                Assert.assertTrue(missingInformationPage.commentSectionWarningMessage.getText().equalsIgnoreCase("Comment Section is required and cannot be left blank"), "Comment Box warning message isn't display/matched");
                break;
            case "IF YOU CONTINUE, ALL THE CAPTURED INFORMATION WILL BE LOST":
                Assert.assertTrue(missingInformationPage.warningMessageInformationWillLost.getText().equalsIgnoreCase("IF YOU CONTINUE, ALL THE CAPTURED INFORMATION WILL BE LOST"), "Warning Message isn't display/matched");
                break;
            default:
                fail("Entered warning message didn't match");
        }
    }

    @Then("I verify the order for the created MI entities on Missing Information Tab")
    public void iVerifyTheOrderForTheCreatedMIEntitiesOnMissingInformationTab() {
        waitFor(2);
        Assert.assertEquals(missingInformationPage.rowOneStatus.getText(), "PENDING", "Pending Status isn't in order for Missing Information Tab");
        Assert.assertEquals(missingInformationPage.savedMissingInfoListRowTwo.get(6).getText(), "SATISFIED", "Satisfied Status isn't in order for Missing Information Tab");
        Assert.assertEquals(missingInformationPage.savedMissingInfoListRowThree.get(6).getText(), "DISREGARDED", "Disregarded Status isn't in order for Missing Information Tab");
    }

    @And("I click {string} on warning message for Application Missing Information Tab")
    public void iClickOnWarningMessageForApplicationMissingInformationTab(String actionType) {
        switch (actionType.toUpperCase()) {
            case "CONTINUE":
                missingInformationPage.continueButtonInsideWarning.click();
                break;
            case "CANCEL":
                missingInformationPage.cancelButtonInsideWarning.click();
                break;
            default:
                fail("ACTION didn't match");
        }
    }

    @And("I click Missing Info Filter Column")
    public void iClickMissingInfoFilterColumn() {
        missingInformationPage.filterColumnButton.click();
    }

    @When("I click {string} button on Missing Info Filter Column")
    public void iClickButtonOnMissingInfoFilterColumn(String statusType) {
        switch (statusType.toUpperCase()) {
            case "PENDING":
                missingInformationPage.filterColumnPendingButton.click();
                break;
            case "SATISFIED":
                missingInformationPage.filterColumnSatisfiedButton.click();
                break;
            case "DISREGARDED":
                missingInformationPage.filterColumnDisregardedButton.click();
                break;
            case "CLEAR":
                missingInformationPage.filterColumnClearButton.click();
                break;
            case "CLOSE":
                missingInformationPage.filterColumnCloseButton.click();
                break;
            default:
                fail("Status Type didn't match for Filter Column Dropdown");
        }
    }

    @Then("I should see following Missing Information Status Entities only")
    public void iShouldSeeFollowingMissingInformationStatusEntitiesOnly(List<String> expectedStatusList) {
        LinkedHashSet<String> actualStatusSet = new LinkedHashSet<>();
        for (int i = 0; i < missingInformationPage.savedMIStatusColumn.size(); i++) {
            actualStatusSet.add(missingInformationPage.savedMIStatusColumn.get(i).getText());
        }
        int i = 0;
        for (String actualStatusString : actualStatusSet) {
            Assert.assertEquals(actualStatusString, (expectedStatusList.get(i)), "Missing Information Expected Status mismatched");
            i++;
        }
    }

    @Then("I verify MI entities order as reverse chronological using {string}")
    public void iVerifyMIEntitiesOrderAsReverseChronologicalUsing(String verifyType) {
        switch (verifyType.toUpperCase()) {
            case "COMMENTS":
                for (int i = 0; i < missingInformationPage.savedMICommentColumn.size() - 1; i++) {
                    Assert.assertTrue(Integer.parseInt(missingInformationPage.savedMICommentColumn.get(i).getText())
                            < Integer.parseInt(missingInformationPage.savedMICommentColumn.get(i + 1).getText()), "MI Tab Entities not in Reverse Chronological order");
                }
                break;
            default:
                fail("Verify Type Didn't match");
        }
    }

    @Then("I verify I see following text {string} when I hover over the Filter Column Button")
    public void iVerifyISeeFollowingTextWhenIHoverOverTheFilterColumnButton(String expectedText) {
        waitFor(2);
        hover(missingInformationPage.filterColumnButton);
        Assert.assertEquals(missingInformationPage.filterColumnButton.getAttribute("title"), expectedText, "Expected and Actual Filter Column Text Mismatched");
    }

    @And("I verify All the checkboxes selected is removed on Filter Column")
    public void iVerifyAllTheCheckboxesSelectedIsRemovedOnFilterColumn() {
        Assert.assertTrue(!missingInformationPage.filterColumnPendingButton.isSelected(), "Pending Button is still selected on Filter Column");
        Assert.assertTrue(!missingInformationPage.filterColumnSatisfiedButton.isSelected(), "Satisfy Button is still selected on Filter Column");
        Assert.assertTrue(!missingInformationPage.filterColumnDisregardedButton.isSelected(), "Disregarded Button is still selected on Filter Column");
    }

    @Then("I verify Missing Info due date is {string}")
    public void iVerifyMissingInfoDueDateIs(String miDueDateDisplay) {
        waitFor(3);
        switch (miDueDateDisplay) {
            case "NO DISPLAY":
                assertFalse(quickIsDisplayed(missingInformationPage.miDueDate), "Expected No Display of MI DUE DATE but found DUE DATE Displayed");
                break;
            case "MEDICAL ASSISTANCE":
                assertEquals(getGreaterDateFormatMMddyyyy(45 - 7), missingInformationPage.miDueDate.getText().substring(11), "Expected Medical Assistance Due date did not match");
                break;
            case "LONG TERM CARE":
                assertEquals(getGreaterDateFormatMMddyyyy(90 - 4), missingInformationPage.miDueDate.getText().substring(11), "Expected Long Term Care Due date did not match");
                break;
            default:
                fail("MI Due date Type Didn't match");
        }
    }

    @Then("I verify Add missing info item button is disabled on Missing info page")
    public void iVerifyAddMissingInfoItemButtonIsDisabledOnMissingInfoPage() {
        assertTrue(missingInformationPage.addMissingInfoButton.getAttribute("class").contains("Mui-disabled"), "Expected ADD MISSING INFO button to be disabled but found enabled");
    }

    @And("I verify the following option are removed from the User Icon menu for Missing Info Save")
    public void iVerifyTheFollowingOptionAreRemovedFromTheUserIconMenuForMissingInfoSave(List<String> data) {
        List<String> actualUserIconSelection = new ArrayList<>();
        for (WebElement webElement : missingInformationPage.userIconSelectMenuList) {
            actualUserIconSelection.add(webElement.getText());
        }
        for (String each : data) {
            assertFalse(actualUserIconSelection.contains(each), "Expected " + each + " not pouplated in the user icon menu but found");
        }
    }

    @Then("I verify ADD MISSING INFO Button is displayed")
    public void iVerifyAddMIButtonDisplayed() {
        assertTrue(missingInformationPage.addMissingInfoButton.isDisplayed(), "ADD MISSING INFO Button is not displayed");
    }

    @Then("I verify ADD MISSING INFO Button is not displayed")
    public void iVerifyAddMIButtonNotDisplayed() {
        boolean notDisplayed = false;
        try {
            assertFalse(missingInformationPage.addMissingInfoButton.isDisplayed());
        } catch (Exception e) {
            notDisplayed = true;
        }
        assertTrue(notDisplayed, "ADD MISSING INFO Button is displayed");
    }

    @Then("I verify Missing Info duplicate record error is displayed")
    public void iVerifyMissingInfoDuplicateRecordErrorDisplayed() {
        System.out.println(missingInformationPage.errorMessage.getText());
        Assert.assertTrue(missingInformationPage.errorMessage.getText().trim().equalsIgnoreCase("The missing information record for the application member already exists, duplicate record cannot be created"));
    }

    @And("I verify the number of missing info Rows are {string}")
    public void iVerifyMissingInfoRecordsNumber(String expectedRows) {
        waitFor(2);
        Assert.assertEquals(missingInformationPage.missingInfoDetailRows.size(), Integer.parseInt(expectedRows));
    }

    @Then("I see application Status as {string} in the missing information page")
    public void i_see_application_Status_as_in_the_missing_information(String expectedStatus) {
        waitFor(5);
        String actualStatus = missingInformationPage.applicationStatus.getText();
        assertEquals(actualStatus.toLowerCase(), expectedStatus.toLowerCase(), "Expected Application status: " + expectedStatus + " But found: " + actualStatus);
    }

    @And("I navigate to application tab page from missing info tab")
    public void iNavigateToApplicationTabPage() {
        waitFor(2);
        missingInformationPage.applicationTab.click();
        waitFor(2);
    }

    @Then("I verify {string} in the Application Information panel of the Missing Info tab")
    public void iVerifyInTheApplicationInformationPanelOfTheMissingInfoTab(String data) {
        switch (data) {
            case "APPLICATION CODE UI":
                waitForVisibility(missingInformationPage.applicationCodeValue, 5);
                assertEquals(missingInformationPage.applicationCodeValue.getText(), applicationCode, "Mismatch in expected Saved Application Code");
                assertFalse(missingInformationPage.applicationCodeValue.getAttribute("class").contains("input"), "Expected non editable field value but able to input");
                break;
            case "APPLICATION CODE API":
                waitForVisibility(missingInformationPage.applicationCodeValue, 5);
                assertEquals(missingInformationPage.applicationCodeValue.getText(), applicationCodeAPI, "Mismatch in expected Saved Application Code");
                assertFalse(missingInformationPage.applicationCodeValue.getAttribute("class").contains("input"), "Expected non editable field value but able to input");
                break;
            default:
                fail("Provided key did not match data");
        }
    }

    @And("I verify the missing information details status as {string}")
    public void iVerifyMissingInformationDetailsStatusFromMIPage(String status) {
        waitFor(2);
        assertTrue(missingInformationPage.savedMIStatusColumn.get(0).getText().equalsIgnoreCase(status), "Status is not matching");
    }

    @Then("I verify that Review status is getting displayed as Red")
    public void i_verify_that_Review_status_is_getting_displayed_as_Red() {
        assertTrue(missingInformationPage.miDocumentStatus.isDisplayed(), "Red font isnt displayed");
        assertTrue(missingInformationPage.miDocumentStatus.getText().equalsIgnoreCase("Review"), "MI status is not correct");
    }
}