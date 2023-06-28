package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APIConsumerPopulationDmnController;
import com.maersk.crm.pages.crm.CRMContactRecordDashboardPage;
import com.maersk.crm.pages.crm.CRMDemographicContactInfoPage;
import com.maersk.crm.pages.crm.CRMEnrollmentUpdatePage;
import com.maersk.crm.pages.crm.CRMProgramAndBenefitInfoPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class CRM_ProgramAndBenefitInfoStepDef extends CRMUtilities implements ApiBase {


    private CRMProgramAndBenefitInfoPage programBenefitInfoPage = new CRMProgramAndBenefitInfoPage();
    private CRMContactRecordDashboardPage contactRecordDashboard = new CRMContactRecordDashboardPage();
    private CRMEnrollmentUpdatePage enrollmentUpdatePage = new CRMEnrollmentUpdatePage();
    public final ThreadLocal<List<String>> countDownDays = ThreadLocal.withInitial(ArrayList::new);

    @Then("I verify each consumer section is expanded by default in program and benefit info page")
    public void verifyAllConsumerRecordsExpandedByDefault() {
        waitForVisibility(programBenefitInfoPage.programAndBenefitInfoHeader, 10);
        waitFor(2);
        for (WebElement header : programBenefitInfoPage.listOfConsumersBenefitInfoHeaders) {
            String text = header.findElement(By.xpath(".//i")).getText();
            assertTrue(text.endsWith("down"));
        }
    }

    @When("I click on carrot sign for each consumer section in program and benefit info page")
    public void clickCarronSIgnForEachConsumer() {
        waitFor(2);
        scrollUpUsingActions(5);
        for (WebElement header : programBenefitInfoPage.listOfConsumersBenefitInfoHeaders) {
            try {
                click(header.findElement(By.xpath("./button")));
            } catch (Exception e) {
                jsClick(header.findElement(By.xpath("./button")));
            }
            waitFor(1);
        }
    }

    @Then("I verify each consumer section is collapsed in program and benefit info page")
    public void verifyAllConsumerRecordsCollapsed() {
        waitFor(2);
        for (WebElement header : programBenefitInfoPage.listOfConsumersBenefitInfoHeaders) {
            String text = header.findElement(By.xpath(".//i")).getText();
            assertTrue(!text.endsWith("down"));
        }
    }

    @Then("I verify sort order consumer eligibility enrolled and not eligible")
    public void verifySOrtOrderOfEligibilityEnrolled() {
        waitFor(2);
        String prevRecordBenefitStatus = "";
        String presentRecordBenefitStatus = "";
        for (WebElement header : programBenefitInfoPage.listOfConsumersBenefitInfoHeaders) {
            String text = header.findElement(By.xpath("./div[2]//div[@title='Benefit Status']")).getText();
            presentRecordBenefitStatus = text.replaceAll("star", "");
            if (prevRecordBenefitStatus.isEmpty()) {
                prevRecordBenefitStatus = presentRecordBenefitStatus;
                continue;
            }

            if (presentRecordBenefitStatus.equalsIgnoreCase("ELIGIBLE")) {
                assertTrue(prevRecordBenefitStatus.equalsIgnoreCase("ELIGIBLE"));
            } else if (presentRecordBenefitStatus.equalsIgnoreCase("ENROLLED")) {
                assertTrue(prevRecordBenefitStatus.equalsIgnoreCase("ELIGIBLE") ||
                        prevRecordBenefitStatus.equalsIgnoreCase("ENROLLED"));
            } else if (presentRecordBenefitStatus.equalsIgnoreCase("NOT ELIGIBLE")) {
                assertTrue(prevRecordBenefitStatus.equalsIgnoreCase("ELIGIBLE") ||
                        prevRecordBenefitStatus.equalsIgnoreCase("ENROLLED") ||
                        prevRecordBenefitStatus.equalsIgnoreCase("NOT ELIGIBLE"));
            }
            prevRecordBenefitStatus = presentRecordBenefitStatus;
        }
    }

    @Then("I verify following data elements are displayed for consumer")
    public void verifyAllConsumerRecordsCollapsed(List<String> dataElementsToVerify) {
        waitFor(2);
        scrollUpUsingActions(5);
        for (String element : dataElementsToVerify) {
            System.out.println(element);
        }
        System.out.println();
        assertTrue(isElementDisplayed(programBenefitInfoPage.consumerExternalId));
        System.out.println("Consumer External Id Display verified");
        assertTrue(isElementDisplayed(programBenefitInfoPage.consumerFullName));
        System.out.println("Consumer Full Name Display verified");
        assertTrue(isElementDisplayed(programBenefitInfoPage.consumerAgeAndGender));
        System.out.println("Consumer Age and Gender Display verified");
        assertTrue(isElementDisplayed(programBenefitInfoPage.consumerBenefitStatus));
        System.out.println("Consumer Benefit Status Display verified");
        assertTrue(isElementDisplayed(programBenefitInfoPage.consumerEligibilityStatus));
        System.out.println("Consumer Eligibility Status Display verified");
        assertTrue(isElementDisplayed(programBenefitInfoPage.consumerBenefitCalendarIcon));
        System.out.println("Consumer Benefit Calendar Icon Display verified");
        assertTrue(isElementDisplayed(programBenefitInfoPage.consumerCountDownOfDaysLeft));
        System.out.println("Consumer Count Down of Days Left Display verified");
        assertTrue(isElementDisplayed(programBenefitInfoPage.consumerNotificationIcon));
        System.out.println("Consumer Notification Icon Display verified");
        Actions builder = new Actions(Driver.getDriver());
        builder.moveToElement(programBenefitInfoPage.consumerBenefitCalendarIcon).perform();
        assertTrue(isElementDisplayed(Driver.getDriver().findElement(By.xpath("//div[starts-with(@aria-describedby,'mui')]"))));
        System.out.println("Important Dates - Enroll Period Pop up Display verified with Hover Over");
        builder.moveToElement(programBenefitInfoPage.consumerNotificationIcon).perform();
        assertTrue(isElementDisplayed(Driver.getDriver().findElement(By.xpath("//i[starts-with(@aria-describedby,'mui')]"))));
        System.out.println("Calendar Days Explanation Pop up Display verified with Hover Over");
    }

    @Then("I verify tool tip text for each consumer by hovering the mouse pointer over the Calendar icon of Important Dates")
    public void mouseHoverOnCalendarIcon() {
        waitFor(2);
        scrollUpUsingActions(5);
        for (WebElement header : programBenefitInfoPage.listOfConsumersBenefitInfoHeaders) {
            try {
                click(header.findElement(By.xpath("./button")));
            } catch (Exception e) {
                jsClick(header.findElement(By.xpath("./button")));
            }
            waitFor(1);
            WebElement calendarIcon = header.findElement(By.xpath("./div[2]//div[@class='mx-benefit-calendar']"));
            hover(calendarIcon);
            waitForVisibility(programBenefitInfoPage.toolTip, 10);
            String toolTipText = programBenefitInfoPage.toolTip.getText();
            String[] toolTipData = toolTipText.split("\n");
            assertTrue(toolTipText.contains("IMPORTANT DATE"));
            assertTrue(toolTipText.contains("ANNIVERSARY DATE"));

        }
    }

    @Then("I verify tool tip text for each consumer by hovering the mouse pointer over the notification icon")
    public void mouseHoverOnNotificationIcon() {
        waitFor(2);
        scrollUpUsingActions(5);
        for (WebElement header : programBenefitInfoPage.listOfConsumersBenefitInfoHeaders) {
            try {
                click(header.findElement(By.xpath("./button")));
            } catch (Exception e) {
                jsClick(header.findElement(By.xpath("./button")));
            }
            waitFor(1);
            WebElement notificationIcon = header.findElement(By.xpath("./div[2]//div[contains(text(), 'DAYS')]/i"));
            hover(notificationIcon);
            waitForVisibility(programBenefitInfoPage.toolTip, 10);
            String toolTipText = programBenefitInfoPage.toolTip.getText();
            toolTipText = toolTipText.replaceAll("\n", " ");
            assertTrue(toolTipText.contains("Calendar days left to enroll in a plan"));
        }
    }

    @Then("I verify alternative text is displayed for benefit Status and Eligibility status for each consumer")
    public void alternateTextIsDisplayed() {
        waitFor(2);
        scrollUpUsingActions(5);
        for (WebElement header : programBenefitInfoPage.listOfConsumersBenefitInfoHeaders) {
            try {
                click(header.findElement(By.xpath("./button")));
            } catch (Exception e) {
                jsClick(header.findElement(By.xpath("./button")));
            }
            waitFor(1);
            assertTrue(isElementDisplayed(header.findElement(By.xpath("./div[2]//div[@title='Benefit Status']"))));
            assertTrue(isElementDisplayed(header.findElement(By.xpath("./div[2]//div[@title='Eligibility Status']"))));
        }
    }

    @Then("I verify user is navigate to Demographic info tab after clicking on external consumer id")
    public void verifyUserNavigateToDemographicInfoTab() {
        waitFor(2);
        scrollUpUsingActions(5);
        List<WebElement> headers = Driver.getDriver().findElements(By.xpath("//div[@class='mx-benefit-info__header']"));
        for (int i=0; i < headers.size(); i++) {
            clickCarronSIgnForEachConsumer();
            scrollUpUsingActions(5);
            waitFor(1);
            String externalId = headers.get(i).findElement(By.xpath("./div[1]/div/div/p")).getText();
            System.out.println(externalId.matches("[A-Za-z0-9]+"));
            assertTrue(externalId.matches("[A-Za-z0-9]+"));
            try {
                click(headers.get(i).findElement(By.xpath("./div[1]/div/div/p")));
            } catch (Exception e) {
                jsClick(headers.get(i).findElement(By.xpath("./div[1]/div/div/p")));
            }
            waitFor(2);
            assertTrue(Driver.getDriver().getTitle().contains("Demographic Info"));
            contactRecordDashboard.programBenefitInfoTab.click();
            waitFor(2);
            assertTrue(Driver.getDriver().getCurrentUrl().endsWith("benefits"));
            headers = Driver.getDriver().findElements(By.xpath("//div[@class='mx-benefit-info__header']"));
        }
    }

    @Then("I verify {string} is displayed for all cunsumers on Program & Benefit Info Page")
    public void iVerifyIsDisplayedForAllCunsumersOnProgramBenefitInfoPage(String section) {
        section = section.toUpperCase();
        switch (section) {
            case "ELIGIBILITY SECTION":
                if (programBenefitInfoPage.listOfEligibilitySection.size() > 0) {
                    for (int i = 0; i < programBenefitInfoPage.listOfEligibilitySection.size(); i++) {
                        assertTrue(programBenefitInfoPage.listOfEligibilitySection.get(i).isDisplayed(),
                                "The Eligibility Section is not displayed");
                    }
                }
                break;
            case "CARROT SYMBOL":
                if (programBenefitInfoPage.listOfCarrotBttnOnCurrentEligibilitySection.size() > 0) {
                    for (int i = 0; i < programBenefitInfoPage.listOfCarrotBttnOnCurrentEligibilitySection.size(); i++) {
                        assertTrue(programBenefitInfoPage.listOfCarrotBttnOnCurrentEligibilitySection.get(i).isDisplayed(),
                                "Carrot Symbol is not displayed");
                    }
                }
                break;
            case "FUTURE ELIGIBILITY":
                if (programBenefitInfoPage.listOfFutureEligibilitySetion.size() > 0) {
                    for (int i = 0; i < programBenefitInfoPage.listOfFutureEligibilitySetion.size(); i++) {
                        assertTrue(programBenefitInfoPage.listOfFutureEligibilitySetion.get(i).isDisplayed());
                    }
                }
                break;
            case "ORDER OF ELIGIBILITY":
                for (int i = 0; i < programBenefitInfoPage.currentEligibilityFieldList.size(); i++) {
                    assertTrue(programBenefitInfoPage.currentEligibilityFieldList.get(i).getLocation().getY() <
                            programBenefitInfoPage.futureEligibilityFieldList.get(i).getLocation().getY());
                }

                break;
            case "SPECIAL COVERAGE SECTION":
                for (int i = 0; i < programBenefitInfoPage.listOfSpecialCoverageBar.size(); i++) {
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageBar.get(i).isDisplayed(), "The Special Coverage Section is not displayed");
                }
                break;
            case "SPECIAL COVERAGE CARROT SYMBOL":
                for (int i = 0; i < programBenefitInfoPage.listOfSpecialCoverageExpandBttn.size(); i++) {
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageExpandBttn.get(i).isDisplayed(), "The Special Coverage Expand Button is not displayed");
                }
                break;
            case "ORDER OF SPECIAL COVERAGE":
                for (int i = 0; i < programBenefitInfoPage.currentEligibilityFieldList.size(); i++) {
                    assertTrue(programBenefitInfoPage.currentEligibilityFieldList.get(i).getLocation().getY() <
                            programBenefitInfoPage.futureEligibilityFieldList.get(i).getLocation().getY());
                    assertTrue(programBenefitInfoPage.futureEligibilityFieldList.get(i).getLocation().getY() <
                            programBenefitInfoPage.listOfSpecialCoverageBar.get(i).getLocation().getY(), "The sort Of Special Coverage is incorrect");
                }
                break;
            case "CARROT BUTTON ON CURRENT ENROLLMENT":
                for (int i = 0; i < programBenefitInfoPage.listOfCurrentEnrollmentExpandBttn.size(); i++) {
                    assertTrue(programBenefitInfoPage.listOfCurrentEnrollmentExpandBttn.get(i).isDisplayed(), "The " + section + " is not displayed");
                }
                break;
            case "ORDER OF ENROLLMENT":
                for (int i = 0; i < programBenefitInfoPage.listOfCurrentEnrollmen.size(); i++) {
                    assertTrue(programBenefitInfoPage.listOfCurrentEnrollmen.get(i).getLocation().getY() <
                            programBenefitInfoPage.listOfFutureEnrollmen.get(i).getLocation().getY(), "The " + section + " is not correct");
                }
                break;
            default:
                Assert.fail("The " + section + " is not declared in switch statement");
        }

    }

    @Then("I verify {string} is collapsed by default")
    public void iVerifyIsCollapsedByDefault(String section) {
        section = section.toUpperCase();
        switch (section) {
            case "ELIGIBILITY SECTION":
                for (int i = 0; i < programBenefitInfoPage.listOfColapseBttn.size(); i++) {
                    assertFalse(programBenefitInfoPage.listOfColapseBttn.get(i).getText().contains("arrow_down"),
                            "The Eligibility section is not collapsed by default");
                }
                break;
            case "SPECIAL COVERAGE SECTION":
                for (int i = 0; i < programBenefitInfoPage.listOfSpecialCoverageExpandBttn.size(); i++) {
                    assertFalse(programBenefitInfoPage.listOfSpecialCoverageExpandBttn.get(i).getText().contains("arrow_down"),
                            "The Special Coverage section is not collapsed by default");
                }
                break;
            case "CURRENT ENROLLMENT":
                for (int i = 0; i < programBenefitInfoPage.listOfCurrentEnrollmentExpandBttn.size(); i++) {
//                    assertFalse(programBenefitInfoPage.listOfSpecialCoverageExpandBttn.get(i).getText().contains("arrow_down"),
//                            "The " + section + " section is not collapsed by default");
                    assertFalse(programBenefitInfoPage.listOfCurrentEnrollmentExpandBttn.get(i).getText().contains("arrow_down"),
                            "The " + section + " section is not collapsed by default");
                }
                break;
            default:
                Assert.fail("The " + section + " is not declared in the switch statement");
        }
    }

    @Then("I verify {string} on the {string} section is in the past/feature")
    public void iVerifyOnTheSectionIsInThePast(String date, String section) {
        section = section.toUpperCase();
        String currentDate = getCurrentDate();
        switch (section) {
            case "CURRENT ELIGIBILITY":
                if (date.equalsIgnoreCase("Start date")) {
                    for (int i = 0; i < programBenefitInfoPage.listOfStartDatesOnCurrentEnrollmentSection.size(); i++) {
                        String startDate = programBenefitInfoPage.listOfStartDatesOnCurrentEnrollmentSection.get(i).getText();
                        assertTrue(convertStringToDate(currentDate).after(convertStringToDate(startDate)),
                                "The Start Date is not in the past");
                    }
                }
                break;
            case "CURRENT ENROLLMENT":
                if (date.equalsIgnoreCase("Start date")) {
                    for (int i = 0; i < programBenefitInfoPage.listOfCurrentEnrollmentStartDate.size(); i++) {
                        String startDate = programBenefitInfoPage.listOfCurrentEnrollmentStartDate.get(i).getText();
                        if (!startDate.equalsIgnoreCase("-- --")) {
                            assertTrue(convertStringToDate(currentDate).after(convertStringToDate(startDate)),
                                    "The Start Date is not in the past");
                        }
                    }
                } else if (date.equalsIgnoreCase("End date")) {
                    for (int i = 0; i < programBenefitInfoPage.listOfCurrentEnrollmentEndDate.size(); i++) {
                        String endDate = programBenefitInfoPage.listOfCurrentEnrollmentEndDate.get(i).getText();
                        if (!endDate.equalsIgnoreCase("-- --")) {
                            assertTrue((convertStringToDate(currentDate).before(convertStringToDate(endDate)) ||
                                            convertStringToDate(currentDate).compareTo(convertStringToDate(endDate)) == 0),
                                    "The End Date is not in the Future");
                        }
                    }

                }
                break;
            default:
                Assert.fail("The " + section + " is not declared in the switch statement");
        }
    }

    @Then("I verify {string} is not displayed on {string} section")
    public void iVerifyIsNotDisplayedOnSection(String field, String section) {
        section = section.toUpperCase();
        switch (section) {
            case "FUTURE ELIGIBILITY":
                if (field.equalsIgnoreCase("Expand option")) {
                    assertTrue(Driver.getDriver().findElements(By.xpath("//div[contains(@class,'future-eligibility')]/..//button/i")).isEmpty());
                }
                break;
            case "FUTURE ENROLLMENT":
                if (field.equalsIgnoreCase("Expand option")) {
                    assertTrue(Driver.getDriver().findElements(By.xpath("//div[.='FUTURE ELIGIBILITY']/..//button/i")).isEmpty());
                }
                break;
            default:
                Assert.fail("The " + section + " is not declared in switch case");
        }
    }

    @Then("I verify bellow fields are displayed on {string} section:")
    public void i_verify_bellow_fields_are_displayed_on_section(String section, List<String> fields) {
        waitFor(5);
        section = section.toUpperCase();
        waitFor(2);
        switch (section) {
            case "ELIGIBILITY":
                for (int i = 0; i < programBenefitInfoPage.consumerPopulationFieldList.size(); i++) {
                    assertTrue(programBenefitInfoPage.consumerPopulationFieldList.get(i).getText().equalsIgnoreCase(fields.get(0)), "The consumer population field is not displayed");
                    assertTrue(programBenefitInfoPage.serviceRegionFieldList.get(i).getText().equalsIgnoreCase(fields.get(1)), "The Service Region field is not displayed");
                    assertTrue(programBenefitInfoPage.listOfStartDateForEligibilitySection.get(i).getText().equalsIgnoreCase(fields.get(2)),
                            "The Start Date field on Eligibility section is not displayed");
                    assertTrue(programBenefitInfoPage.listOfEndDateForEligibilitySection.get(i).getText().equalsIgnoreCase(fields.get(3)),
                            "The End Date on The Eligibility Section is not displayed");
                }
                break;
            case "PRIOR ELIGIBILITY DETAILS":
                break;
            case "":
                for (int i = 0; i < programBenefitInfoPage.listOfCarrotBttnOnCurrentEligibilitySection.size(); i++) {
                    programBenefitInfoPage.listOfCarrotBttnOnCurrentEligibilitySection.get(i).click();
                    waitFor(1);
                }
                for (int i = 0; i < programBenefitInfoPage.listOfProgramNamePriopEligibilityField.size(); i++) {
                    assertTrue(programBenefitInfoPage.listOfProgramNamePriopEligibilityField.get(i).getText().equalsIgnoreCase(fields.get(0)),
                            "The Program Name on Prior Eligibility section is not displayed");
                    assertTrue(programBenefitInfoPage.listOfEligibilityStartDatePriorEligibtyField.get(i).getText().equalsIgnoreCase(fields.get(1)),
                            "The Eligibility Start Date is not displayed on Prior Eligibility Details field");
                    assertTrue(programBenefitInfoPage.listOfEligibilityEndDatePriorEligibtyField.get(i).getText().equalsIgnoreCase(fields.get(2)),
                            "The Eligibility End Date is not displayed on Prior Eligibility Details field");
                    assertTrue(programBenefitInfoPage.listOfEligibilityAndReasonPriorEligibtyField.get(i).getText().equalsIgnoreCase(fields.get(3)),
                            "The ELIGIBILITY END REASON is not displayed on Eligibility Details field");
                }
                break;
            case "PLAN DETAILS":
                assertTrue(enrollmentUpdatePage.planNameOnPlanDetailsPanel.getText().equalsIgnoreCase(fields.get(0)), "The Plan Name on Plan Detail section is not displayed");
                assertTrue(enrollmentUpdatePage.planTypeOnPlanDetailsPanel.getText().equalsIgnoreCase(fields.get(1)), "The Plan Type on Plan Detail section is not displayed");
                assertTrue(enrollmentUpdatePage.serviceRegionOnPlanDetailsPanel.getText().equalsIgnoreCase(fields.get(2)), "The Service Region on Plan Detail section is not displayed");
                assertTrue(enrollmentUpdatePage.startDateOnPlanDetailsPanel.getText().contains(fields.get(3)), "The Start Date on Plan Detail section is not displayed");
                assertTrue(enrollmentUpdatePage.endDateOnPlanDetailsPanel.getText().equalsIgnoreCase(fields.get(4)), "The End Date on Plan Detail section is not displayed");
                break;
            case "SPECIAL COVERAGE WAIVER":
                for (int i = 0; i < programBenefitInfoPage.listOfSpecialCoverageCodeWaiver.size(); i++) {
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageCodeWaiver.get(i).getText().equalsIgnoreCase(fields.get(0)),
                            "The CODE field is not displayed on SPECIAL COVERAGE WAIVER section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageCountyWaiver.get(i).getText().equalsIgnoreCase(fields.get(1)),
                            "The COUNTY field is not displayed on SPECIAL COVERAGE WAIVER section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageStartDateWaiver.get(i).getText().equalsIgnoreCase(fields.get(2)),
                            "The START DATE field is not displayed on SPECIAL COVERAGE WAIVER section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageEndDateWaiver.get(i).getText().equalsIgnoreCase(fields.get(3)),
                            "The END DATE field is not displayed on SPECIAL COVERAGE WAIVER section");
                }
                break;
            case "SPECIAL COVERAGE OTHER INSURANCE":
                for (int i = 0; i < programBenefitInfoPage.listOfSpecialCoverageSourceOtherInsurance.size(); i++) {
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageSourceOtherInsurance.get(i).getText().equalsIgnoreCase(fields.get(0)),
                            "The SOURCE field is not displayed on SPECIAL COVERAGE OTHER INSURANCE section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageCodeOtherInsurance.get(i).getText().equalsIgnoreCase(fields.get(1)),
                            "The CODE field is not displayed on SPECIAL COVERAGE OTHER INSURANCE section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageStartDateOtherInsurance.get(i).getText().equalsIgnoreCase(fields.get(2)),
                            "The START DATE field is not displayed on SPECIAL COVERAGE OTHER INSURANCE section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageEndDateOtherInsurance.get(i).getText().equalsIgnoreCase(fields.get(3)),
                            "The END DATE field is not displayed on SPECIAL COVERAGE OTHER INSURANCE section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageGroupNumberOtherInsurance.get(i).getText().equalsIgnoreCase(fields.get(4)),
                            "The GROUP NUMBER field is not displayed on SPECIAL COVERAGE OTHER INSURANCE section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageNameOtherInsurance.get(i).getText().equalsIgnoreCase(fields.get(5)),
                            "The NAME field is not displayed on SPECIAL COVERAGE OTHER INSURANCE section");
                }
                break;
            case "SPECIAL COVERAGE MEDICARE":
                for (int i = 0; i < programBenefitInfoPage.listOfSpecialCoveragePartAStartDateMedicare.size(); i++) {
                    assertTrue(programBenefitInfoPage.listOfSpecialCoveragePartAStartDateMedicare.get(i).getText().equalsIgnoreCase(fields.get(0)),
                            "The PART A START DATE field is not displayed on SPECIAL COVERAGE MEDICARE section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoveragePartAEndDateMedicare.get(i).getText().equalsIgnoreCase(fields.get(1)),
                            "ThePART A END DATE field is not displayed on SPECIAL COVERAGE MEDICARE section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoveragePartBStartDateMedicare.get(i).getText().equalsIgnoreCase(fields.get(2)),
                            "The PART B START DATE field is not displayed on SPECIAL COVERAGE MEDICARE section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoveragePartBEndDateMedicare.get(i).getText().equalsIgnoreCase(fields.get(3)),
                            "The PART B END DATE field is not displayed on SPECIAL COVERAGE MEDICARE section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoveragePartDStartDateMedicare.get(i).getText().equalsIgnoreCase(fields.get(4)),
                            "ThePART D START DATE field is not displayed on SPECIAL COVERAGE MEDICARE section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoveragePartDEndDateMedicare.get(i).getText().equalsIgnoreCase(fields.get(5)),
                            "The PART D END DATE field is not displayed on SPECIAL COVERAGE MEDICARE section");
                }
                break;
            case "SPECIAL COVERAGE LTC":
                for (int i = 0; i < programBenefitInfoPage.listOfSpecialCoverageCoverageCodeLTC.size(); i++) {
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageCoverageCodeLTC.get(i).getText().equalsIgnoreCase(fields.get(0)),
                            "The COVERAGE CODE field is not displayed on SPECIAL COVERAGE LTC section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageStartDateLTC.get(i).getText().equalsIgnoreCase(fields.get(1)),
                            "The START DATE field is not displayed on SPECIAL COVERAGE LTC section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageEndDateLTC.get(i).getText().equalsIgnoreCase(fields.get(2)),
                            "The END DATE field is not displayed on SPECIAL COVERAGE LTC section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageProviderNameLTC.get(i).getText().equalsIgnoreCase(fields.get(3)),
                            "The PROVIDER NAME field is not displayed on SPECIAL COVERAGE LTC section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageProviderIdLTC.get(i).getText().equalsIgnoreCase(fields.get(4)),
                            "The PROVIDER ID/NPI field is not displayed on SPECIAL COVERAGE LTC section");
                }
                break;
            case "SPECIAL COVERAGE HOSPICE":
                for (int i = 0; i < programBenefitInfoPage.listOfSpecialCoverageIndicatorHospice.size(); i++) {
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageIndicatorHospice.get(i).getText().equalsIgnoreCase(fields.get(0)),
                            "The INDICATOR field is not displayed on SPECIAL COVERAGE HOSPICE section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageStartDateHospice.get(i).getText().equalsIgnoreCase(fields.get(1)),
                            "The START DATE field is not displayed on SPECIAL COVERAGE HOSPICE section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageEndDateHospice.get(i).getText().equalsIgnoreCase(fields.get(2)),
                            "The END DATE field is not displayed on SPECIAL COVERAGE HOSPICE section");
                }
                break;
            case "SPECIAL COVERAGE FACILITY/PLACEMENT":
                for (int i = 0; i < programBenefitInfoPage.listOfSpecialCoverageCodeFacilityPlmnt.size(); i++) {
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageCodeFacilityPlmnt.get(i).getText().equalsIgnoreCase(fields.get(0)),
                            "The CODE  field is not displayed on SPECIAL COVERAGE FACILITY/PLACEMENT section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageCountryCodeFacilityPlmnt.get(i).getText().equalsIgnoreCase(fields.get(1)),
                            "The COUNTY CODE  field is not displayed on SPECIAL COVERAGE FACILITY/PLACEMENT section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageStartDateFacilityPlmnt.get(i).getText().equalsIgnoreCase(fields.get(2)),
                            "The START DATE  field is not displayed on SPECIAL COVERAGE FACILITY/PLACEMENT section");
                    assertTrue(programBenefitInfoPage.listOfSpecialCoverageEndDateFacilityPlmnt.get(i).getText().equalsIgnoreCase(fields.get(3)),
                            "The END DATE  field is not displayed on SPECIAL COVERAGE FACILITY/PLACEMENT section");
                }
                break;
            case "ENROLLMENT":
                for (int i = 0; i < programBenefitInfoPage.listOfPlanNameOnFutureAndCurrentEnrollment.size(); i++) {
                    assertTrue(programBenefitInfoPage.listOfPlanNameOnFutureAndCurrentEnrollment.get(i).getText().equalsIgnoreCase(fields.get(0)),
                            "The " + fields.get(0) + " is not displayed on Enrollment section");
                    assertTrue(programBenefitInfoPage.listOfSelectionStatusOnFutureAndCurrentEnrollment.get(i).getText().equalsIgnoreCase(fields.get(1)),
                            "The " + fields.get(1) + " is not displayed on Enrollment section");
                    assertTrue(programBenefitInfoPage.listOfChannelsOnFutureAndCurrentEnrollment.get(i).getText().equalsIgnoreCase(fields.get(2)),
                            "The " + fields.get(2) + " is not displayed on Enrollment section");
                    assertTrue(programBenefitInfoPage.listOfPCPNameOnFutureAndCurrentEnrollment.get(i).getText().equalsIgnoreCase(fields.get(3)),
                            "The " + fields.get(3) + " is not displayed on Enrollment section");
                    assertTrue(programBenefitInfoPage.listOfStartDateFutureAndCurrentEnrollment.get(i).getText().equalsIgnoreCase(fields.get(4)),
                            "The " + fields.get(4) + " is not displayed on Enrollment section");
                    assertTrue(programBenefitInfoPage.listOfEndDateFutureAndCurrentEnrollment.get(i).getText().equalsIgnoreCase(fields.get(5)),
                            "The " + fields.get(5) + " is not displayed on Enrollment section");
                }
                break;
            case "PRIOR ENROLLMENT DETAILS":
                for (int i = 0; i < programBenefitInfoPage.listOfCurrentEnrollmentExpandBttn.size(); i++) {
                    click(programBenefitInfoPage.listOfCurrentEnrollmentExpandBttn.get(i));
                    waitFor(1);
                }
                for (int i = 0; i < programBenefitInfoPage.listOfCurrentEnrollmentExpandBttn.size(); i++) {
                    assertTrue(programBenefitInfoPage.listOfPlanNamePriorEnrollment.get(i).getText().equalsIgnoreCase(fields.get(0)),
                            "The " + fields.get(0) + " is not displayed on Enrollment section");
                    assertTrue(programBenefitInfoPage.listOfEnrollmentStartDatePriorEnrollment.get(i).getText().equalsIgnoreCase(fields.get(1)),
                            "The " + fields.get(1) + " is not displayed on Enrollment section");
                    assertTrue(programBenefitInfoPage.listOfEnrollmentEndDatePriorEnrollment.get(i).getText().equalsIgnoreCase(fields.get(2)),
                            "The " + fields.get(2) + " is not displayed on Enrollment section");
                    assertTrue(programBenefitInfoPage.listOfEnrollmentEndReasonPriorEnrollment.get(i).getText().equalsIgnoreCase(fields.get(3)),
                            "The " + fields.get(3) + " is not displayed on Enrollment section");
                }
                break;
            case "DROP DOWN REASON":
                waitForVisibility(enrollmentUpdatePage.reasonDropDownChoices.get(0), 2);
                List<String> dropdownSelectionsList = getElementsText(enrollmentUpdatePage.reasonDropDownChoices);
                System.out.println("expected fields: " + fields.toString());
                System.out.println("actual fields: " + dropdownSelectionsList);
                try {
                    for (String field : fields) {
                        assertTrue(dropdownSelectionsList.contains(field),
                                "The " + field + " is not displayed on " + section + " section");
                    }
                    System.out.println("Reason dropdown selections are:\n" + dropdownSelectionsList);
                } catch (Exception e) {
                }
                break;
        }
    }

    @When("I expand {string} section for every consumer")
    public void i_expand_section_for_every_consumer(String section) {
        section = section.toUpperCase();
        switch (section) {
            case "SPECIAL COVERAGE":
                for (int i = 0; i < (programBenefitInfoPage.listOfSpecialCoverageExpandBttn.size()); i++) {
                    click((programBenefitInfoPage.listOfSpecialCoverageExpandBttn.get(i)));
                }
                break;
            case "PRIOR ELIGIBILITY":
                for (int i = 0; i < (programBenefitInfoPage.priorEligibilityExpandCarrotList.size()); i++) {
                    click((programBenefitInfoPage.priorEligibilityExpandCarrotList.get(i)));
                }
                break;
            case "PRIOR ENROLLMENT":
                for (int i = 0; i < (programBenefitInfoPage.priorEnrollmentExpandCarrotList.size()); i++) {
                    click((programBenefitInfoPage.priorEnrollmentExpandCarrotList.get(i)));
                }
                break;
            default:
                Assert.fail("The " + section + " is not displayed in the switch statement");
        }
    }

    @Then("I verify the combination of Start Date and End Date is correct")
    public void i_verify_the_combination_of_Start_Date_and_End_Date_is_correct() {
        List<String> startDate = getElementsText(programBenefitInfoPage.listOfALLSpecialCoverageStartDate);
        List<String> endDate = getElementsText(programBenefitInfoPage.listOfALLSpecialCoverageEndDate);
        for (int i = 0; i < startDate.size(); i++) {
            if (startDate.get(i).equalsIgnoreCase("-- --")) {
                assertTrue(endDate.get(i).equalsIgnoreCase("-- --"), "The End Date is not null");
            } else {
                for (int j = 0; j < startDate.size(); j++) {
                    assertTrue(endDate.get(i).equalsIgnoreCase("-- --") ||
                            convertStringToDate(endDate.get(i)).after(convertStringToDate(getCurrentDate())) ||
                            endDate.get(i).equalsIgnoreCase(getNextYearDate(startDate.get(i))), "The End Date is not in Future or Next year");
                }

            }
        }
    }

    @Then("I verify {string} are displayed/not-displayed")
    public void i_verify_are_dispalyed(String field) {
        field = field.toUpperCase();
        switch (field) {
            case "COUNT DOWN NUMBER OF DAYS":
                for (int i = 0; i < programBenefitInfoPage.listOfCountDownDays.size(); i++) {
                    assertTrue(programBenefitInfoPage.listOfCountDownDays.get(i).isDisplayed(), "The " + field + " is not displayed");
                }
                break;
            case "RED DOT ON THE CALENDAR ICON WITH PLAN CHANGE ACTION":
            case "RED DOT ON THE CALENDAR ICON WITH ENROLL ACTION":
                for (int i = 0; i < programBenefitInfoPage.listOfCountDownDays.size(); i++) {
                    if (!countDownDays.get().get(i).equalsIgnoreCase("-- --")) {
                        assertTrue(programBenefitInfoPage.listOfRedDotOnCalendar.get(i).isDisplayed(), "The " + field + " is not displayed");
                    }
                }
                break;
            case "RED DOT ON THE CALENDAR ICON WITH NO PLAN CHANGE ACTION":
            case "RED DOT ON THE CALENDAR ICON WITH NO ENROLL ACTION":
                for (int i = 0; i < programBenefitInfoPage.listOfCountDownDays.size(); i++) {
                    if (countDownDays.get().get(i).equalsIgnoreCase("-- --")) {
                        assertFalse(programBenefitInfoPage.listOfRedDotOnCalendar.get(i).isDisplayed(), "The " + field + " is displayed");
                    }
                }
                break;
            default:
                Assert.fail("The " + field + " is not declared in the switch statement");
        }
    }

    @Then("I verify {string} number is calculated correct")
    public void i_verify_number_is_calculated_correct(String field) {
        field = field.toUpperCase();
        switch (field) {
            case "COUNT DOWN DAYS":
                for (int i = 0; i < programBenefitInfoPage.calendarIcon.size(); i++) {
                    if (!countDownDays.get().get(i).equalsIgnoreCase("-- --")) {
                        hover(programBenefitInfoPage.calendarIcon.get(i));
                        waitFor(1);
                        assertEquals(daysDifference(getCurrentDate(), programBenefitInfoPage.datesFromImportantDatesHover.getText().substring(13)),
                                Integer.parseInt(programBenefitInfoPage.listOfCountDownDays.get(i).getText().replace(" DAYS", "")), "The days count doesn't match");
                    }
                }
                break;
            default:
                Assert.fail("The " + field + " is not declared in switch statement");
        }
    }

    @Then("I verify if the Count Days is {string} it's displayed in Red/Black")
    public void iVerifyIfTheCountDaysIsItSDisplayedInRed(String count) {
        count = count.toUpperCase();
        switch (count) {
            case "ZERO OR NEGATIVE":
                for (int i = 0; i < programBenefitInfoPage.listOfCountDownDays.size(); i++) {
                    if (!programBenefitInfoPage.listOfCountDownDays.get(i).getText().replace(" DAYS", "").equalsIgnoreCase("-- --") &&
                            Integer.parseInt(programBenefitInfoPage.listOfCountDownDays.get(i).getText().replace(" DAYS", "")) <= 0) {
                        verifyColorOfElement(programBenefitInfoPage.listOfCountDownDays.get(i), "#D32F2F");
                    }
                }
                break;
            case "POSITIVE":
                for (int i = 0; i < programBenefitInfoPage.listOfCountDownDays.size(); i++) {
                    if (!programBenefitInfoPage.listOfCountDownDays.get(i).getText().replace(" DAYS", "").equalsIgnoreCase("-- --") &&
                            Integer.parseInt(programBenefitInfoPage.listOfCountDownDays.get(i).getText().replace(" DAYS", "")) > 0) {
                        verifyColorOfElement(programBenefitInfoPage.listOfCountDownDays.get(i), "#212529");
                    }
                }
                break;
            case "NOT DISPLAYED":
                for (int i = 0; i < programBenefitInfoPage.listOfCountDownDays.size(); i++) {
                    assertTrue(programBenefitInfoPage.listOfCountDownDays.get(i).getText().replace(" DAYS", "").equalsIgnoreCase("-- --"));
                    verifyColorOfElement(programBenefitInfoPage.listOfCountDownDays.get(i), "#212529");
                }
                break;
            default:
                Assert.fail("The " + count + "  is not in switch statement");
        }
    }

    @And("I view the {string} for the consumer")
    public void iViewTheForTheConsumer(String action) {
        action = action.toUpperCase();
        switch (action) {
            case "COUNT DOWN DAYS ACTION":
                for (int i = 0; i < programBenefitInfoPage.listOfCountDownDays.size(); i++) {
                    countDownDays.get().add(programBenefitInfoPage.listOfCountDownDays.get(i).getText().replace(" DAYS", ""));
                }
                break;
            default:
                Assert.fail("The " + action + " is not declared in switch statement");
        }
    }

    @Then("Display the provider name in Enrollment Segment")
    public void display_the_provider_name_in_Enrollment_Segment() {
        Assert.assertEquals(programBenefitInfoPage.listOfPCPNameOnFutureAndCurrentEnrollment.get(0).getText(), "PCP NAME");
    }

    @Then("I verify previously accepted enrollment segment with an end date and selection status of “Disenroll Requested”")
    public void i_verify_previously_accepted_enrollment_segment_with_an_end_date_and_selection_status_of_Disenroll_Requested() {
        Assert.assertEquals(programBenefitInfoPage.currentEnrollmentSelectionStatusDisenrollRequested.getText(), "Disenroll Requested");
        Assert.assertNotNull(programBenefitInfoPage.endDate.get(1));
    }

    @Then("I verify previously accepted enrollment segment with an end date and selection status of “Disenroll Submitted”")
    public void i_verify_previously_accepted_enrollment_segment_with_an_end_date_and_selection_status_of_Disenroll_Submitted() {
        Assert.assertEquals(programBenefitInfoPage.currentEnrollmentSelectionStatusDisenrollSubmitted.getText(), "Disenroll Submitted");
        Assert.assertNotNull(programBenefitInfoPage.endDate.get(1));
    }

    @Then("I verify new Plan Change with anticipated start date and selection status of “Selection Made”")
    public void i_verify_new_Plan_Change_with_anticipated_start_date_and_selection_status_of_Selection_Made() {
        Assert.assertEquals(programBenefitInfoPage.currentEnrollmentSelectionStatus.getText(), "Selection Made");
        Assert.assertNotNull(programBenefitInfoPage.futureEnrollmentPlanStartDate.get(0));
    }

    @Then("I select a reason from drop down on Program and Benefit info page")
    public void i_select_a_reason_from_drop_down_on_Program_benefit_info_page() {
        waitFor(3);
        click(programBenefitInfoPage.dropDownDisenroll);
        waitFor(2);
        click(programBenefitInfoPage.dropDownDisenrollReason);
    }

    @Then("I verify current enrollment segment is in “DISENROLL REQUESTED” status")
    public void i_verify_current_enrollment_segment_is_in_DISENROLL_REQUESTED_status() {
        Assert.assertTrue(programBenefitInfoPage.iconDisenrollRequested.isDisplayed());
    }

    @Then("I will see an option to DISREGARD the action taken on the disenroll segment")
    public void i_will_see_an_option_to_DISREGARD_the_action_taken_on_the_disenroll_segment() {
        Assert.assertTrue(programBenefitInfoPage.disregardCurrentEnrollmentButton.isDisplayed());
    }

    @Then("I will not see an option to DISREGARD on the disenroll segment")
    public void i_will_not_see_an_option_to_DISREGARD_on_the_disenroll_segment() {
        try {
            Assert.assertFalse(programBenefitInfoPage.disregardCurrentEnrollmentButton.isDisplayed());
        } catch (Exception e) {
        }
    }

    @Then("I verify orange informational icon displayed next to the enrollment segment selection status")
    public void i_verify_orange_informational_icon_displayed_next_to_the_enrollment_segment_selection_status() {
        Assert.assertEquals("rgba(215, 96, 5, 1)", programBenefitInfoPage.iconDisenrollRequested.getCssValue("background-color"),
                "Orange color is missing");
        Assert.assertTrue(programBenefitInfoPage.infoIconDisenrollRequested.isDisplayed());
    }

    @Then("I verify Selection Status for the disenroll request displayed in an orange status label with an informational icon")
    public void i_verify_Selection_Status_for_the_disenroll_request_displayed_in_an_orange_status_label_with_an_informational_icon() {
        Assert.assertEquals("rgba(255, 99, 4, 1)", programBenefitInfoPage.infoIconSelectionStatus.getCssValue("color"),
                "Orange color is missing");
        Assert.assertTrue(programBenefitInfoPage.infoIconSelectionStatus.isDisplayed());
    }

    @When("I hover over orange informational icon")
    public void i_hover_over_orange_informational_icon() {
        hover(programBenefitInfoPage.infoIconSelectionStatus);
        waitFor(3);
    }

    @Then("I verify title of the hover bubble, request Date, effective date, reason are displayed")
    public void i_verify_title_of_the_hover_bubble_request_Date_effective_date_reason_are_displayed() {
        Assert.assertTrue(programBenefitInfoPage.titleDisenroll.isDisplayed());
        Assert.assertTrue(programBenefitInfoPage.requestDate.isDisplayed());
        Assert.assertTrue(programBenefitInfoPage.effectiveDate.isDisplayed());
        Assert.assertTrue(programBenefitInfoPage.reasonDisenroll.isDisplayed());
    }

    @When("I change effective date to {int} month later to day {int} on calendar")
    public void I_change_start_date_to_month_later_on_calendar(int month, int day) {
        waitForClickablility(programBenefitInfoPage.editCalendarDateBttn, 2).click();
        waitForVisibility(programBenefitInfoPage.editCalendarDateForm, 2);
        System.out.println("changing calendar date to " + month + " month(s) later");
        for (int i = 0; i < month; i++) {
            programBenefitInfoPage.calendarNextMonthButton.click();
            waitFor(1);
        }
        programBenefitInfoPage.calendarDayButton(day).click();
        programBenefitInfoPage.calendarActionButton("OK").click();
    }

    @When("I change effective date to {int} month earlier to day {int} on calendar")
    public void I_change_start_date_to_month_earlier_on_calendar(int month, int day) {
        waitForClickablility(programBenefitInfoPage.editCalendarDateBttn, 2).click();
        waitForVisibility(programBenefitInfoPage.editCalendarDateForm, 2);
        System.out.println("changing calendar date to " + month + " month(s) earlier");
        for (int i = 0; i < month; i++) {
            programBenefitInfoPage.calendarPreviousMonthButton.click();
            waitFor(1);
        }
        programBenefitInfoPage.calendarDayButton(day).click();
        programBenefitInfoPage.calendarActionButton("OK").click();
    }

    @Then("^I verify dropdown values available for reason dropdown")
    public void verifyProgramFieldDropDownValues(List<String> expectedOptions) {
        click(enrollmentUpdatePage.reasonDropDownBttn);
        List<String> actualOptions = getElementsText(enrollmentUpdatePage.editreasonDropDownChoices).stream().sorted().collect(Collectors.toList());
        expectedOptions = expectedOptions.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(actualOptions, expectedOptions, "dropdown options doesn't contain all expected options");
    }

}
