package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMProgramAndBenefitInfoPage {
    

    public CRMProgramAndBenefitInfoPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//h5[text()='CASE BENEFICIARY INFORMATION']")
    public WebElement programAndBenefitInfoHeader;

    @FindBy(xpath = "//div[@class='mx-benefit-info__header px-2 py-1']")
    public List<WebElement> listOfConsumersBenefitInfoHeaders;

    @FindBy(xpath = "//div[@class='mx-benefit-info__header px-2 py-1']/button")
    public List<WebElement> listOfConsumerExpandButtons;//keyboard_arrow_down

    @FindBy(xpath = "//div[@role='tooltip']")
    public WebElement toolTip;

    @FindBy(xpath = "//div[@class='mx-benefit-info__header px-2 py-1']/div[1]/div/div[1]/p")
    public WebElement consumerExternalId;

    @FindBy(xpath = "//div[@class='mx-benefit-info__header px-2 py-1']/div[1]/div/div[2]/p")
    public WebElement consumerFullName;

    @FindBy(xpath = "//div[@class='mx-benefit-info__header px-2 py-1']/div[1]/div/div[3]/p")
    public WebElement consumerAgeAndGender;

    @FindBy(xpath = "//div[@title='Benefit Status']")
    public WebElement consumerBenefitStatus;

    @FindBy(xpath = "//div[@title='Eligibility Status']")
    public WebElement consumerEligibilityStatus;

    @FindBy(xpath = "//div[starts-with(@class,'mx-benefit-calendar')]")
    public WebElement consumerBenefitCalendarIcon;

    @FindBy(xpath = "//div[@class='mx-benefit-imp-dates']/span")
    public WebElement consumerCountDownOfDaysLeft;

    @FindBy(xpath = "//div[@class='mx-benefit-imp-dates']/i")
    public WebElement consumerNotificationIcon;

    @FindBy(xpath = "//div[@class='mx-benefit-section__buttons float-right']/button[.='ENROLL']")
    public WebElement currentEligibilityEnrollBttn;

    @FindBy(xpath = "//*[contains(text(), 'CURRENT ENROLLMENT')]/../..//button[./span[.='PLAN CHANGE']]")
    public WebElement currentEnrollmentPlanChangeBttn;

    @FindBy(xpath = "//*[contains(text(), 'CURRENT ENROLLMENT')]/../..//button[./span[.='EDIT']]")
    public WebElement currentEnrollmentEditBttn;

    @FindBy(xpath = "//*[contains(text(), 'CURRENT ENROLLMENT')]/../..//button[./span[.='DISREGARD']]")
    public WebElement currentEnrollmentDisregardBttn;

    @FindBy(xpath = "//div[./p[contains(text(), 'CURRENT ENROLLMENT')]]//following-sibling::div/p")
    public WebElement currentEnrollmentStatus;

    @FindBy(xpath = "//div[./p[contains(text(), 'CURRENT ELIGIBILITY')]]//following-sibling::div/p")
    public WebElement currentEligibilityStatus;

    public WebElement getCheckCoreIndicator(String fullName) {
        return programAndBenefitInfoHeader.findElement(By.xpath("//*[.='" + fullName + "']/../following-sibling::div[2]"));
    }

    @FindBy(xpath = "//p[.='Check Core']")
    public WebElement checkCoreText;

    @FindBy(xpath = "//p[@class='m-0 mx-color-text-secondary mx-text-1-2em text-left']")
    public WebElement checkCoreMMISText;

    public WebElement getGivenConsumerBoxByFullName(String fullName) {
        return programAndBenefitInfoHeader.findElement(By.xpath("//div[@class='accordion pt-4' and .//p[.='" + fullName + "']]"));
    }


    public WebElement getSegmentInsideGivenParent(WebElement parent, String segment) {
        return parent.findElement(By.xpath(".//div[contains(@class, 'row mx-4') and .//*[.='" + segment + "'] ]"));
    }

    public WebElement getButtonInsideGivenParentGivenEnrollmentGivenButtonText(WebElement parent, String enrollment, String button) {
        return parent.findElement(By.xpath(".//*[contains(text(), '" + enrollment + "')]/../..//button[./span[.='" + button + "']]"));
    }

    public WebElement getFieldInsideGivenParentGivenEnrollmentGivenLabelText(WebElement parent, String enrollment, String label) {
        return parent.findElement(By.xpath(".//div[contains(@class, 'row mx-4') and .//*[.='" + enrollment + "'] ]//p[.='" + label + "']/following-sibling::p"));
    }

    public WebElement getFieldInsideGivenParentGivenPriorEnrollmentGivenLabelText(WebElement parent, String label) {
        return parent.findElement(By.xpath("//p[contains(text(),'PRIOR ENROLLMENT DETAILS')]//parent::div/following-sibling::div[2]//p[.='" + label +"']/following-sibling::p"));
    }

    public WebElement getFieldInsideGivenParentGivenPriorEligibilityGivenLabelText(WebElement parent, String label) {
        return parent.findElement(By.xpath("//div[contains(@class,'row mx-0 mt-2 pb-2')]/following-sibling::div//p[.='" +label + "']/following-sibling::p"));
    }

    public WebElement getFieldInsideGivenParentGivenSpecialCoverageSingleGivenLabelText(WebElement parent, String specialCoverage, String label) {
        return parent.findElement(By.xpath(".//div[contains(@class, 'row mx-2') and .//*[.='" + specialCoverage + "'] ]//p[.='" + label + "']/following-sibling::p"));
    }

    public WebElement getFieldInsideGivenParentGivenSpecialCoverageDoubleGivenLabelText(WebElement parent, String specialCoverage, String label) {
        return parent.findElement(By.xpath(".//div[contains(@class, 'col-6') and .//*[.='" + specialCoverage + "'] ]//p[.='" + label + "']/following-sibling::p"));
    }

    public WebElement getSpecialCoverageSingleInsideGivenParent(WebElement parent, String specialCoverage) {
        return parent.findElement(By.xpath(".//div[contains(@class, 'row mx-2') and .//*[.='" + specialCoverage + "'] ]"));
    }

    public WebElement getSpecialCoverageDoubleInsideGivenParent(WebElement parent, String specialCoverage) {
        return parent.findElement(By.xpath(".//div[contains(@class, 'col-6') and .//*[.='" + specialCoverage + "'] ]"));
    }

    public List<WebElement> getListOfCodesOfAddedAidCategoryInsideGivenConsumerParentElement(WebElement parent) {
        return parent.findElements(By.xpath(".//div[contains(@class, 'row') and ./*[.='ADDED AID CATEGORY'] ]//div[@class = 'row']/div[1]"));
    }

    public List<WebElement> getListOfStartDatesOfAddedAidCategoryInsideGivenConsumerParentElement(WebElement parent) {
        return parent.findElements(By.xpath(".//div[contains(@class, 'row') and ./*[.='ADDED AID CATEGORY'] ]//div[@class = 'row']/div[2]"));
    }

    public List<WebElement> getListOfEndDatesOfAddedAidCategoryInsideGivenConsumerParentElement(WebElement parent) {
        return parent.findElements(By.xpath(".//div[contains(@class, 'row') and ./*[.='ADDED AID CATEGORY'] ]//div[@class = 'row']/div[3]"));
    }

    public List<WebElement> getListOfCodesOfOtherEligibilityInsideGivenConsumerParentElement(WebElement parent) {
        return parent.findElements(By.xpath(".//div[contains(@class, 'row') and ./*[.='OTHER ELIGIBILITY'] ]//div[@class = 'row']/div[1]"));
    }

    public List<WebElement> getListOfReasonCodesOfOtherEligibilityInsideGivenConsumerParentElement(WebElement parent) {
        return parent.findElements(By.xpath(".//div[contains(@class, 'row') and ./*[.='OTHER ELIGIBILITY'] ]//div[@class = 'row']/div[2]"));
    }

    public List<WebElement> getListOfStartDatesOtherEligibilityInsideGivenConsumerParentElement(WebElement parent) {
        return parent.findElements(By.xpath(".//div[contains(@class, 'row') and ./*[.='OTHER ELIGIBILITY'] ]//div[@class = 'row']/div[3]"));
    }

    public List<WebElement> getListOfEndDatesOtherEligibilityInsideGivenConsumerParentElement(WebElement parent) {
        return parent.findElements(By.xpath(".//div[contains(@class, 'row') and ./*[.='OTHER ELIGIBILITY'] ]//div[@class = 'row']/div[4]"));
    }

    public List<WebElement> getListOfCodesOfFacilityPlacementInsideGivenConsumerParentElement(WebElement parent) {
        return parent.findElements(By.xpath(".//div[contains(@class, 'row') and ./*[.='FACILITY/PLACEMENT'] ]//div[@class = 'row']/div[1]"));
    }

    public List<WebElement> getListOfCountyCodesOfFacilityPlacementInsideGivenConsumerParentElement(WebElement parent) {
        return parent.findElements(By.xpath(".//div[contains(@class, 'row') and ./*[.='FACILITY/PLACEMENT'] ]//div[@class = 'row']/div[2]"));
    }

    public List<WebElement> getListOfStartDatesOfFacilityPlacementInsideGivenConsumerParentElement(WebElement parent) {
        return parent.findElements(By.xpath(".//div[contains(@class, 'row') and ./*[.='FACILITY/PLACEMENT'] ]//div[@class = 'row']/div[3]"));
    }

    public List<WebElement> getListOfEndDatesOfFacilityPlacementInsideGivenConsumerParentElement(WebElement parent) {
        return parent.findElements(By.xpath(".//div[contains(@class, 'row') and ./*[.='FACILITY/PLACEMENT'] ]//div[@class = 'row']/div[4]"));
    }

    public List<WebElement> getListOfCodesOfHospiceInsideGivenConsumerParentElement(WebElement parent) {
        return parent.findElements(By.xpath(".//div[contains(@class, 'row') and ./*[.='HOSPICE'] ]//div[@class = 'row']/div[1]"));
    }

    public List<WebElement> getListOfStartDatesOfHospiceInsideGivenConsumerParentElement(WebElement parent) {
        return parent.findElements(By.xpath(".//div[contains(@class, 'row') and ./*[.='HOSPICE'] ]//div[@class = 'row']/div[2]"));
    }

    public List<WebElement> getListOfEndDatesOfHospiceInsideGivenConsumerParentElement(WebElement parent) {
        return parent.findElements(By.xpath(".//div[contains(@class, 'row') and ./*[.='HOSPICE'] ]//div[@class = 'row']/div[3]"));
    }

    public List<WebElement> getListOfCodesOfWaiverInsideGivenConsumerParentElement(WebElement parent) {
        return parent.findElements(By.xpath(".//div[contains(@class, 'row') and ./*[.='WAIVER'] ]//div[@class = 'row']/div[1]"));
    }

    public List<WebElement> getListOfCountiesOfWaiverInsideGivenConsumerParentElement(WebElement parent) {
        return parent.findElements(By.xpath(".//div[contains(@class, 'row') and ./*[.='WAIVER'] ]//div[@class = 'row']/div[2]"));
    }

    public List<WebElement> getListOfStartDatesOfWaiverInsideGivenConsumerParentElement(WebElement parent) {
        return parent.findElements(By.xpath(".//div[contains(@class, 'row') and ./*[.='WAIVER'] ]//div[@class = 'row']/div[3]"));
    }

    public List<WebElement> getListOfEndDatesOfWaiverInsideGivenConsumerParentElement(WebElement parent) {
        return parent.findElements(By.xpath(".//div[contains(@class, 'row') and ./*[.='WAIVER'] ]//div[@class = 'row']/div[4]"));
    }

    public WebElement getIconInsideGivenParentByIconText(WebElement parent, String iconInnerText) {
        return parent.findElement(By.xpath(".//i[.='" + iconInnerText + "']"));
    }

    public WebElement getEligibilityStatusInsideGivenParent(WebElement parent, String eligibility) {
        return parent.findElement(By.xpath(".//*[.='" + eligibility + "']/../..//div[@title='Benefit Status']/p"));
    }

    public WebElement getDaysLeftSpanInsideGivenParent(WebElement parent) {
        return parent.findElement(By.xpath(".//div[@class = 'mx-benefit-imp-dates']//span"));
    }

    public WebElement getInfoBoxInsideGivenParentByEnrolmentName(WebElement parent, String enrollment) {
        return parent.findElement(By.xpath(".//div[contains(@class, 'row mx-4') and .//*[.='" + enrollment + "'] ]"));
    }

    @FindBy(xpath = "//div[@class='float-left']/div/*[last()-1]")
    public List<WebElement> BeneficiaryName;

    @FindBy(xpath = "//div[@class='float-left']/div/*[last()]/p")
    public List<WebElement> listOfYearsAndGenderOfBeneficiary;

    @FindBy(xpath = "//ul[@class='nav']/li[last()-1]/a")
    public WebElement headTabProgramAndBenefit;

    @FindBy(xpath = "//*[contains(text(), 'FUTURE ENROLLMENT')]/../..//button[./span[.='PLAN CHANGE']]")
    public WebElement futureEnrollmentPlanChangeBttn;

    @FindBy(xpath = "//*[contains(text(), 'FUTURE ENROLLMENT')]/../..//button[./span[.='EDIT']]")
    public WebElement futureEnrollmentEditBttn;

    @FindBy(xpath = "//*[.='FUTURE ENROLLMENT']/../..//button[./span[.='DISREGARD']]")
    public WebElement futureEnrollmentDisregardBttn;

    @FindBy(xpath = "//*[.='FUTURE ENROLLMENT']/ancestor::div[contains(@class,'mx-benefit-info_body')]//p[.='SELECTION STATUS']/following-sibling::p")
    public WebElement futureEnrollmentStatus;

    @FindBy(xpath = "//*[.='FUTURE ELIGIBILITY']")
    public List<WebElement> futureEligibilityFieldList;

    @FindBy(xpath = "//*[.='FUTURE ELIGIBILITY']/following-sibling::*[1][name()='div']")
    public List<WebElement> futureEligibilityBenefitStatusFieldList;

    @FindBy(xpath = "//div[@class='row px-0 mx-4']//div[@title='Eligibility Status']")
    public List<WebElement> futureEligibilityEligibilityStatusFieldList;

    @FindBy(xpath = "//*[contains(text(),'ELIGIBILITY')]/../..")
    public List<WebElement> listOfEligibilitySection;

    @FindBy(xpath = "//*[.='CURRENT ELIGIBILITY']/../../..//button/i")
    public List<WebElement> listOfCarrotBttnOnCurrentEligibilitySection;

    @FindBy(xpath = "//div[contains(@class,'future-eligibility')]/..")
    public List<WebElement> listOfFutureEligibilitySetion;

    @FindBy(xpath = "//div[@class='col mx-table-icon']/button")
    public List<WebElement> listOfColapseBttn;

    @FindBy(xpath = "//*[.='CURRENT ELIGIBILITY']/ancestor::div[contains(@class,'mx-benefit-info_body')]//p[.='START DATE']/following-sibling::p")
    public List<WebElement> listOfStartDatesOnCurrentEnrollmentSection;

    @FindBy(xpath = "//*[contains(text(),'CURRENT ELIGIBILITY')]")
    public List<WebElement> currentEligibilityFieldList;

    @FindBy(xpath = "//*[contains(text(),'CONSUMER POPULATION')]")
    public List<WebElement> consumerPopulationFieldList;

    @FindBy(xpath = "//p[contains(text(),'SERVICE REGION')]")
    public List<WebElement> serviceRegionFieldList;

//    @FindBy(xpath = "//*[contains(text(),'ELIGIBILITY')]/../../..//p[.='START DATE']/following-sibling::p")
    @FindBy(xpath = "//*[contains(text(),'ELIGIBILITY')]/../../..//p[.='START DATE']")
    public List<WebElement> listOfStartDateForEligibilitySection;

//    @FindBy(xpath = "//*[contains(text(),'ELIGIBILITY')]/../../..//p[.='END DATE']/following-sibling::p")
    @FindBy(xpath = "//*[contains(text(),'ELIGIBILITY')]/../../..//p[.='END DATE']")
    public List<WebElement> listOfEndDateForEligibilitySection;

    @FindBy(xpath = "//*[contains(text(),'PROGRAM NAME')]")
    public List<WebElement> listOfProgramNamePriopEligibilityField;

    @FindBy(xpath = "//*[contains(text(),'ELIGIBILITY START DATE')]")
    public List<WebElement> listOfEligibilityStartDatePriorEligibtyField;

    @FindBy(xpath = "//*[contains(text(),'ELIGIBILITY END DATE')]")
    public List<WebElement> listOfEligibilityEndDatePriorEligibtyField;

    @FindBy(xpath = "//*[contains(text(),'ELIGIBILITY END REASON')]")
    public List<WebElement> listOfEligibilityAndReasonPriorEligibtyField;

    @FindBy(xpath = "//*[contains(text(), 'CONSUMER POPULATION')]//parent::div//preceding-sibling::div//button")
    public List<WebElement> priorEligibilityExpandCarrotList;

    @FindBy(xpath = "//*[contains(text(), 'PLAN NAME')]//parent::div//preceding-sibling::div//button")
    public List<WebElement> priorEnrollmentExpandCarrotList;

    @FindBy(xpath = "//*[.='SPECIAL COVERAGE']/..//div[contains(@class,'mx-special-subtitles')]")
    public List<WebElement> listOfSpecialCoverageFieldsSegment;

    @FindBy(xpath = "//*[.='SPECIAL COVERAGE']/..//button/i")
    public List<WebElement> listOfSpecialCoverageExpandBttn;

    @FindBy(xpath = "//*[.='SPECIAL COVERAGE']/..//div[starts-with(@class,'row mx-2 mb')]/div/p[2]")
    public List<WebElement> listOfSpecialCoverageElements;

    @FindBy(xpath = "//*[.='SPECIAL COVERAGE']/..//div[contains(@class,'mx-special-subtitles')]/p")
    public List<WebElement> listOfSpecialCoverageElements_txt;

    @FindBy(xpath = "//p[.='FACILITY/PLACEMENT']/../..//div/p[2]")
    public List<WebElement> listOfSpecialCoverageFieldsFacilityPlacement;

    @FindBy(xpath = "//p[.='LONG TERM CARE']/../..//div/p[2]")
    public List<WebElement> listOfSpecialCoverageFieldsLongTermCare;

    @FindBy(xpath = "//p[.='MEDICARE']/../..//div/p[2]")
    public List<WebElement> listOfSpecialCoverageFieldsMedicare;

    @FindBy(xpath = "//p[.='OTHER INSURANCE - 3RD PARTY']/../..//div/p[2]")
    public List<WebElement> listOfSpecialCoverageFieldsOtherInsurance;

    @FindBy(xpath = "//p[.='HOSPICE']/../../..//div[3]/div/p[2]")
    public List<WebElement> listOfSpecialCoverageFieldsHospice;

    @FindBy(xpath = "//p[.='WAIVER']/../../..//div[3]/div/p[2]")
    public List<WebElement> listOfSpecialCoverageFieldsWaiver;

    @FindBy(xpath = "//*[.='CURRENT ELIGIBILITY']/..//div[5]/div//*[.='SERVICE REGION']//following-sibling::p")
    public List<WebElement> listOfServiceRegionForCurrentEligibility;

    @FindBy(xpath = "//*[contains(text(),'SERVICE REGION')]/following-sibling::p")
    public List<WebElement> listOfServiceRegionForCurrentEligibility2;

    @FindBy(xpath = "//p[@class='m-0 mx-color-text-secondary lead']")
    public List<WebElement> listOfConsumerFullNames;

    @FindBy(xpath = "//*[.='CURRENT ELIGIBILITY']/..//div[5]/div//*[.='CONSUMER POPULATION']//following-sibling::p")
    public List<WebElement> listOfConsumerPopulationCurrentEligibility;

    @FindBy(xpath = "//p[contains(text(),'CONSUMER POPULATION')]/following-sibling::p")
    public List<WebElement> listOfConsumerPopulationCurrentEligibility2;

    @FindBy(xpath = "//*[.='CURRENT ELIGIBILITY']/ancestor::div//button[.='ENROLL']")
    public List<WebElement> listOfEnrollBttnForCurrentEligibility;

    @FindBy(xpath = "//div[@class='mx-benefit-imp-dates']/span")
    public List<WebElement> listOfActionDays;

    @FindBy(xpath = "//*[.='FUTURE ELIGIBILITY']/..//div[5]/div//*[.='CONSUMER POPULATION']//following-sibling::p")
    public List<WebElement> listOfConsumerPopulationFutureEligibility;

    @FindBy(xpath = "//*[.='FUTURE ELIGIBILITY']/..//div[5]/div//*[.='SERVICE REGION']//following-sibling::p")
    public List<WebElement> listOfServiceRegionForFutureEligibility;

    @FindBy(xpath = "//*[.='FUTURE ELIGIBILITY']/..//button[.='ENROLL']")
    public List<WebElement> listOfEnrollBttnForFutureEligibility;

    @FindBy(xpath = "//*[.='SPECIAL COVERAGE']/..")
    public List<WebElement> listOfSpecialCoverageBar;

    @FindBy(xpath = "//p[.='WAIVER']/../../..//*[.='CODE']")
    public List<WebElement> listOfSpecialCoverageCodeWaiver;

    @FindBy(xpath = "//p[.='WAIVER']/../../..//*[.='COUNTY']")
    public List<WebElement> listOfSpecialCoverageCountyWaiver;

    @FindBy(xpath = "//p[.='WAIVER']/../../..//*[.='START DATE']")
    public List<WebElement> listOfSpecialCoverageStartDateWaiver;

    @FindBy(xpath = "//p[.='WAIVER']/../../..//*[.='END DATE']")
    public List<WebElement> listOfSpecialCoverageEndDateWaiver;

    @FindBy(xpath = "//p[.='OTHER INSURANCE - 3RD PARTY']/../..//*[.='SOURCE']")
    public List<WebElement> listOfSpecialCoverageSourceOtherInsurance;

    @FindBy(xpath = "//p[.='OTHER INSURANCE - 3RD PARTY']/../..//*[.='CODE']")
    public List<WebElement> listOfSpecialCoverageCodeOtherInsurance;

    @FindBy(xpath = "//p[.='OTHER INSURANCE - 3RD PARTY']/../..//*[.='START DATE']")
    public List<WebElement> listOfSpecialCoverageStartDateOtherInsurance;

    @FindBy(xpath = "//p[.='OTHER INSURANCE - 3RD PARTY']/../..//*[.='END DATE']")
    public List<WebElement> listOfSpecialCoverageEndDateOtherInsurance;

    @FindBy(xpath = "//p[.='OTHER INSURANCE - 3RD PARTY']/../..//*[.='GROUP NUMBER']")
    public List<WebElement> listOfSpecialCoverageGroupNumberOtherInsurance;

    @FindBy(xpath = "//p[.='OTHER INSURANCE - 3RD PARTY']/../..//*[.='NAME']")
    public List<WebElement> listOfSpecialCoverageNameOtherInsurance;

    @FindBy(xpath = "//p[.='MEDICARE']/../..//*[.='PART A START DATE']")
    public List<WebElement> listOfSpecialCoveragePartAStartDateMedicare;

    @FindBy(xpath = "//p[.='MEDICARE']/../..//*[.='PART A END DATE']")
    public List<WebElement> listOfSpecialCoveragePartAEndDateMedicare;

    @FindBy(xpath = "//p[.='MEDICARE']/../..//*[.='PART B START DATE']")
    public List<WebElement> listOfSpecialCoveragePartBStartDateMedicare;

    @FindBy(xpath = "//p[.='MEDICARE']/../..//*[.='PART B END DATE']")
    public List<WebElement> listOfSpecialCoveragePartBEndDateMedicare;

    @FindBy(xpath = "//p[.='MEDICARE']/../..//*[.='PART D START DATE']")
    public List<WebElement> listOfSpecialCoveragePartDStartDateMedicare;

    @FindBy(xpath = "//p[.='MEDICARE']/../..//*[.='PART D END DATE']")
    public List<WebElement> listOfSpecialCoveragePartDEndDateMedicare;

    @FindBy(xpath = "//p[.='LONG TERM CARE']/../..//*[.='COVERAGE CODE']")
    public List<WebElement> listOfSpecialCoverageCoverageCodeLTC;

    @FindBy(xpath = "//p[.='LONG TERM CARE']/../..//*[.='START DATE']")
    public List<WebElement> listOfSpecialCoverageStartDateLTC;

    @FindBy(xpath = "//p[.='LONG TERM CARE']/../..//*[.='END DATE']")
    public List<WebElement> listOfSpecialCoverageEndDateLTC;

    @FindBy(xpath = "//p[.='LONG TERM CARE']/../..//*[.='PROVIDER NAME']")
    public List<WebElement> listOfSpecialCoverageProviderNameLTC;

    @FindBy(xpath = "//p[.='LONG TERM CARE']/../..//*[.='PROVIDER ID/NPI']")
    public List<WebElement> listOfSpecialCoverageProviderIdLTC;

    @FindBy(xpath = "//p[.='HOSPICE']/../../..//*[.='INDICATOR']")
    public List<WebElement> listOfSpecialCoverageIndicatorHospice;

    @FindBy(xpath = "//p[.='HOSPICE']/../../..//*[.='START DATE']")
    public List<WebElement> listOfSpecialCoverageStartDateHospice;

    @FindBy(xpath = "//p[.='HOSPICE']/../../..//*[.='END DATE']")
    public List<WebElement> listOfSpecialCoverageEndDateHospice;

    @FindBy(xpath = "//p[.='FACILITY/PLACEMENT']/../..//*[.='CODE']")
    public List<WebElement> listOfSpecialCoverageCodeFacilityPlmnt;

    @FindBy(xpath = "//p[.='FACILITY/PLACEMENT']/../..//*[.='COUNTY CODE']")
    public List<WebElement> listOfSpecialCoverageCountryCodeFacilityPlmnt;

    @FindBy(xpath = "//p[.='FACILITY/PLACEMENT']/../..//*[.='START DATE']")
    public List<WebElement> listOfSpecialCoverageStartDateFacilityPlmnt;

    @FindBy(xpath = "//p[.='FACILITY/PLACEMENT']/../..//*[.='END DATE']")
    public List<WebElement> listOfSpecialCoverageEndDateFacilityPlmnt;

    @FindBy(xpath = "//*[.='SPECIAL COVERAGE']/..//*[.='START DATE']//following-sibling::p")
    public List<WebElement> listOfALLSpecialCoverageStartDate;

    @FindBy(xpath = "//*[.='SPECIAL COVERAGE']/..//*[.='END DATE']//following-sibling::p")
    public List<WebElement> listOfALLSpecialCoverageEndDate;

    @FindBy(xpath = "//div[@title='Benefit Status']")
    public List<WebElement> listOfBenefitStatusForConsumer;

    @FindBy(xpath = "//*[.='CURRENT ELIGIBILITY']/following-sibling::*[1][name()='div']/p")
    public List<WebElement> listOfCurrentBenefitStatusForConsumer;

    @FindBy(xpath = "//*[.='FUTURE ELIGIBILITY']/following-sibling::*[1][name()='div']/p")
    public List<WebElement> listOfFutureBenefitStatusForConsumer;

    @FindBy(xpath = "//*[contains(@id,'accordion')]/div/div/div[2]/p")
    public List<WebElement> listOfConsmersNames;

    @FindBy(xpath = "//div[.='CURRENT ENROLLMENT']/..")
    public List<WebElement> listOfCurrentEnrollmentSection;

    @FindBy(xpath = "//*[.='CURRENT ENROLLMENT']/ancestor::div[contains(@class,'mx-benefit-info_body')]//button/i")
    public List<WebElement> listOfCurrentEnrollmentExpandBttn;

    @FindBy(xpath = "//*[.='CURRENT ENROLLMENT']/ancestor::div[contains(@class,'mx-benefit-info_body')]//p[.='START DATE']/following-sibling::p")
    public List<WebElement> listOfCurrentEnrollmentStartDate;

    @FindBy(xpath = "//*[.='CURRENT ENROLLMENT']/ancestor::div[contains(@class,'mx-benefit-info_body')]//p[.='END DATE']/following-sibling::p")
    public List<WebElement> listOfCurrentEnrollmentEndDate;

    @FindBy(xpath = "//div[.='FUTURE ENROLLMENT']/..")
    public List<WebElement> listOfFutureEnrollmentSection;

    @FindBy(xpath = "//h6[.='CURRENT ENROLLMENT']")
    public List<WebElement> listOfCurrentEnrollmen;

    @FindBy(xpath = "//h6[.='FUTURE ENROLLMENT']")
    public List<WebElement> listOfFutureEnrollmen;

    @FindBy(xpath = "//*[contains(text(),'ENROLLMENT')]/ancestor::div[contains(@class,'mx-benefit-info_body')]//*[.='PLAN NAME']/following-sibling::p")
    public List<WebElement> listOfPlanNameOnFutureAndCurrentEnrollment;

    @FindBy(xpath = "//*[contains(text(),'ENROLLMENT')]/ancestor::div[contains(@class,'mx-benefit-info_body')]//*[.='SELECTION STATUS']/following-sibling::p")
    public List<WebElement> listOfSelectionStatusOnFutureAndCurrentEnrollment;

    @FindBy(xpath = "//*[contains(text(),'ENROLLMENT')]/ancestor::div[contains(@class,'mx-benefit-info_body')]//*[.='CHANNEL']/following-sibling::p")
    public List<WebElement> listOfChannelsOnFutureAndCurrentEnrollment;

    @FindBy(xpath = "//*[contains(text(),'ENROLLMENT')]/ancestor::div[contains(@class,'mx-benefit-info_body')]//*[.='PCP NAME']/following-sibling::p")
    public List<WebElement> listOfPCPNameOnFutureAndCurrentEnrollment;

    @FindBy(xpath = "//*[contains(text(),'ENROLLMENT')]/ancestor::div[contains(@class,'mx-benefit-info_body')]//*[.='START DATE']/following-sibling::p")
    public List<WebElement> listOfStartDateFutureAndCurrentEnrollment;

    @FindBy(xpath = "//*[contains(text(),'ENROLLMENT')]/ancestor::div[contains(@class,'mx-benefit-info_body')]//*[.='END DATE']/following-sibling::p")
    public List<WebElement> listOfEndDateFutureAndCurrentEnrollment;

    @FindBy(xpath = "//div[.='PRIOR ENROLLMENT DETAILS']/..//*[.='PLAN NAME']")
    public List<WebElement> listOfPlanNamePriorEnrollment;

    @FindBy(xpath = "//div[.='PRIOR ENROLLMENT DETAILS']/..//*[.='ENROLLMENT START DATE']")
    public List<WebElement> listOfEnrollmentStartDatePriorEnrollment;

    @FindBy(xpath = "//div[.='PRIOR ENROLLMENT DETAILS']/..//*[.='ENROLLMENT END DATE']")
    public List<WebElement> listOfEnrollmentEndDatePriorEnrollment;

    @FindBy(xpath = "//div[.='PRIOR ENROLLMENT DETAILS']/..//*[.='ENROLLMENT END REASON']")
    public List<WebElement> listOfEnrollmentEndReasonPriorEnrollment;

    @FindBy(xpath = "//div[@class='mx-benefit-imp-dates']/span")
    public List<WebElement> listOfCountDownDays;

    @FindBy(xpath = "//i[.='info_outline']")
    public List<WebElement> infoIcon;

    @FindBy(xpath = "//p[@class='m-0 mx-color-text-secondary mx-text-1-2em text-left']")
    public WebElement hoverElementIconInfo;

    @FindBy(xpath = "//p[.='IMPORTANT DATES']/../..//div[3]/p[2]")
    public WebElement datesFromImportantDatesHover;

    @FindBy(xpath = "//p[.='IMPORTANT DATES']/../..//div[3]/p")
    public WebElement emptyDatesFromImportantDatesHover;

    @FindBy(xpath = "//span[.='calendar_today']")
    public List<WebElement> calendarIcon;

    @FindBy(xpath = "//p[.='IMPORTANT DATES']")
    public WebElement calendarHoverIcon;

    @FindBy(xpath = "//div[@role = 'tooltip']//div[@class = 'col-12 mt-2']")
    public WebElement emptyAction;

    @FindBy(xpath = "//div[@role = 'tooltip']//p[@class = 'm-0 mx-color-text-secondary mx-text-1-2em']")
    public List<WebElement> actionNames;

    @FindBy(xpath = "//div[@role = 'tooltip']//p[@class = 'm-0 mx-color-text-secondary']")
    public List<WebElement> actionDates;

    @FindBy(xpath = "//i[.='calendar_today']//following-sibling::span")
    public List<WebElement> listOfRedDotOnCalendar;

    @FindBy(xpath = "//p[.='PRE-LOCKIN - WINDOW']")
    public WebElement calendarHoverPreLokin;

    @FindBy(xpath = "//p[.='PRE-LOCKIN - WINDOW']//following-sibling::p")
    public WebElement calendarHoverPreLokinDates;

    @FindBy(xpath = "//p[.='ANNIVERSARY - WINDOW']")
    public WebElement calendarHoverAnniversary;

    @FindBy(xpath = "//p[.='ANNIVERSARY - WINDOW']//following-sibling::p")
    public WebElement calendarHoverAnniversaryDates;

    @FindBy(xpath = "//p[.='ANNIVERSARY - WINDOW']//following-sibling::p")
    public List<WebElement> listOfAnniversaryDates;

    @FindBy(xpath = "//*[.='FUTURE ELIGIBILITY']/ancestor::div//button[.='ENROLL']")
    public WebElement futureEligibilityEnrollBttn;

    @FindBy(xpath = "//p[.='IMPORTANT DATES']/../..//div[3]/p[1]")
    public WebElement calendarHoverTextLine1;

    @FindBy(xpath = "//p[.='IMPORTANT DATES']/../..//div[3]/p[3]")
    public WebElement calendarHoverTextLine2;

    @FindBy(xpath = "//p[.='IMPORTANT DATES']/../..//div[3]/p[5]")
    public WebElement calendarHoverTextLine3;

    @FindBy(xpath = "//*[.='CURRENT ENROLLMENT']/ancestor::div[contains(@class,'mx-benefit-info_body')]//p[.='PLAN NAME']/following-sibling::p")
    public WebElement currentEnrollmentPlanName;

    @FindBy(xpath = "//*[.='CURRENT ENROLLMENT']/ancestor::div[contains(@class,'mx-benefit-info_body')]//p[.='SELECTION STATUS']/following-sibling::p")
    public WebElement currentEnrollmentSelectionStatus;

    @FindBy(xpath = "//*[.='CURRENT ENROLLMENT']/ancestor::div[contains(@class,'mx-benefit-info_body')]//p[.='CHANNEL']/following-sibling::p")
    public WebElement currentEnrollmentChannel;

    @FindBy(xpath = "//*[.='CURRENT ENROLLMENT']/ancestor::div[contains(@class,'mx-benefit-info_body')]//p[.='START DATE']/following-sibling::p")
    public WebElement currentEnrollmentPlanStartDate;

    @FindBy(xpath = "//*[.='CURRENT ENROLLMENT']/ancestor::div[contains(@class,'mx-benefit-info_body')]//p[.='END DATE']/following-sibling::p")
    public WebElement currentEnrollmentPlanEndDate;

    @FindBy(xpath = "//div[contains(@class, 'accordion pt-4')]")
    public List<WebElement> accountsList;

    @FindBy(xpath = "//button[contains(span, 'ADD CASE MEMBERS(S)')]")
    public WebElement addCaseMembersButton;

    @FindBy(xpath = "//div[@id='simple-menu']//li[1]")
    public WebElement selectAllDropdownSelection;

    public WebElement getEligibilityStartDateFromAccount(WebElement caseMember) {
        return caseMember.findElement(By.xpath(".//*[contains(text(),'ELIGIBILITY')]/ancestor::div[contains(@class,'mx-benefit-info_body')]//p[.='START DATE']/following-sibling::p"));
    }

    public WebElement getEnrollmentStartDateFromAccount(WebElement caseMember) {
        List<WebElement> enrollments = caseMember.findElements(By.xpath(".//*[contains(text(),'ENROLLMENT')]/ancestor::div[contains(@class,'mx-benefit-info_body')]//p[.='START DATE']/following-sibling::p"));
        // this way we are getting future/last enrolment
        return enrollments.get(enrollments.size() - 1);
    }

    @FindBy(xpath = "//*[.='FUTURE ENROLLMENT']/ancestor::div[contains(@class,'mx-benefit-info_body')]//p[.='PLAN NAME']/following-sibling::p")
    public WebElement futureEnrollmentPlanName;

    @FindBy(xpath = "//*[.='FUTURE ENROLLMENT']/ancestor::div[contains(@class,'mx-benefit-info_body')]//p[.='SELECTION STATUS']/following-sibling::p")
    public WebElement futureEnrollmentSelectionStatus;

    @FindBy(xpath = "//*[.='FUTURE ENROLLMENT']/ancestor::div[contains(@class,'mx-benefit-info_body')]//p[.='CHANNEL']/following-sibling::p")
    public WebElement futureEnrollmentChannel;

    @FindBy(xpath = "//*[.='FUTURE ENROLLMENT']/ancestor::div[contains(@class,'mx-benefit-info_body')]//p[.='START DATE']/following-sibling::p")
    public List<WebElement> futureEnrollmentPlanStartDate;

    @FindBy(xpath = "//*[.='FUTURE ENROLLMENT']/ancestor::div[contains(@class,'mx-benefit-info_body')]//p[.='END DATE']/following-sibling::p")
    public WebElement futureEnrollmentPlanEndDate;

    @FindBy(xpath = "//*[contains(text(), 'CURRENT ENROLLMENT')]/../..//button[./span[.='EDIT']]")
    public WebElement editCurrentEnrollmentButton;

    @FindBy(xpath = "//*[contains(text(), 'CURRENT ENROLLMENT')]/../..//button[./span[.='DISREGARD']]")
    public WebElement disregardCurrentEnrollmentButton;

    @FindBy(xpath = "//*[contains(text(), 'CURRENT ENROLLMENT')]/../..//button[./span[.='DISENROLL']]")
    public WebElement disenrollCurrentEnrollmentButton;

    @FindBy(xpath = "//p[contains(text(),'Disenroll Requested')]")
    public WebElement currentEnrollmentSelectionStatusDisenrollRequested;

    @FindBy(xpath = "//p[contains(text(),'Disenroll Submitted')]")
    public WebElement currentEnrollmentSelectionStatusDisenrollSubmitted;

    @FindBy(xpath = "//span[contains(text(),'arrow_forward')]")
    public WebElement frontArrow;

    @FindBy(xpath = "//div[1]/ul[1]/li/a[1]")
    public List<WebElement> listOfPag;

    @FindBy(xpath = "//tbody[1]/tr/td[2]")
    public List<WebElement> consumerIds;

    @FindBy(xpath = "//h6[.='FUTURE ELIGIBILITY']/../../..//p[.='START DATE']/../p[2]")
    public WebElement futureEligibilityPlanStartDate;

    @FindBy(xpath = "//*[.='CURRENT ELIGIBILITY']/../../..//p[.='START DATE']/../p[2]")
    public List<WebElement> currentEligibilityPlanStartDate;

    @FindBy(xpath = "//*[.='END DATE']")
    public List<WebElement> endDate;

    @FindBy(id = "_DISENOLL REASON")
    public WebElement dropDownDisenroll;

    @FindBy(id = "_DISENOLL REASON-option-0")
    public WebElement dropDownDisenrollReason;

    @FindBy(id = "disenroll-submit")
    public WebElement disenrollSubmit;

    @FindBy(xpath = "//div[@class='mx-eligible-status mx-status--disenroll']")
    public WebElement iconDisenrollRequested;

    @FindBy(xpath = "//i[.='info']")
    public WebElement infoIconDisenrollRequested;

    @FindBy(id = "disenrollstatus-recepnt")
    public WebElement infoIconSelectionStatus;

    @FindBy(xpath = "//p[.='REQUEST DATE']")
    public WebElement requestDate;

    @FindBy(xpath = "//p[.='EFFECTIVE DATE']")
    public WebElement effectiveDate;

    @FindBy(xpath = "//p[.=' REASON']")
    public WebElement reasonDisenroll;

    @FindBy(xpath = "//h5[.='DISENROLL REQUEST ']")
    public WebElement titleDisenroll;

    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiIconButton-root mx-0 px-0' and @aria-label='Open/Close DatePicker']")
    public WebElement editCalendarDateBttn;

    @FindBy(xpath = "//div[@class='MuiPaper-root MuiDialog-paper MuiPickersModal-dialogRoot MuiDialog-paperScrollPaper MuiDialog-paperWidthSm MuiPaper-elevation24 MuiPaper-rounded']")
    public WebElement editCalendarDateForm;

    public WebElement calendarDayButton(int key) {
        for (WebElement day : editCalendarDateForm.findElements(By.xpath(".//p[.='" + key + "']"))) {
            if (day.isDisplayed()) return day;
        }
        return null;
    }

    public WebElement calendarActionButton(String key) {
        return editCalendarDateForm.findElement(By.xpath(".//span[.='" + key + "']"));
    }

    @FindBy(xpath = "//div[@class='MuiPickersCalendarHeader-switchHeader']//button[2]")
    public WebElement calendarNextMonthButton;

    @FindBy(xpath = "//div[@class='MuiPickersCalendarHeader-switchHeader']//button[1]")
    public WebElement calendarPreviousMonthButton;

    @FindBy(xpath = "//*[contains(text(),' CAHMI')]//parent::button")
    public WebElement cahmiSurveyButton;

    @FindBy(xpath = "//*[contains(text(),' HRA')]//parent::button")
    public WebElement hraSurveyButton;

    @FindBy(xpath = "//h5[text()='NO RECORDS AVAILABLE']")
    public WebElement noRecordsAvailableTxt;

    @FindBy(xpath = "//*[contains(text(),'NEW SURVEY')]//parent::button")
    public WebElement newSurveyButton;

    @FindBy(xpath = "//ul[@role='menu']//li//input")
    public List<WebElement> listConsumers;

    @FindBy(xpath = "//li[text()='START SURVEY']")
    public WebElement startSurveyButton;

    @FindBy(xpath = "//*[contains(text(),'clear')]")
    public WebElement closeSurveyButton;

    @FindBy(xpath = "//p[contains(text(),'15')]//parent::div//preceding-sibling::div[1]//p")
    public List<WebElement> eligibleConsumers;

    @FindBy(xpath = "//p[contains(text(),'32')]//parent::div//preceding-sibling::div[1]//p")
    public List<WebElement> hraEligibleConsumers;
    @FindBy(xpath = "//div[1]/label/span[1]/span[1]/input[1]")
    public List<WebElement> qusOneCahmi;

    @FindBy(xpath = "//input[@placeholder='PLEASE DESCRIBE ANY HEALTH PROBLEMS OR MEDICAL TREATMENTS.']")
    public WebElement qusOneYes;

    @FindBy(xpath = "//input[@placeholder=\"DOCTOR'S NAME\"]")
    public WebElement doctorName;

    @FindBy(xpath = "//input[@placeholder='DELIVERY DATE']")
    public WebElement deliveryDate;

    @FindBy(xpath = "//input[@placeholder='MEDICATION']")
    public WebElement medication;

    @FindBy(xpath = "//p[contains(text(),'Does your child need or use these medications beca')]")
    public WebElement medicationStr;

    @FindBy(xpath = "//span[contains(text(),'NEXT')]")
    public WebElement nextButton;

    public WebElement findSurveyQuestion(String question) {
        return programAndBenefitInfoHeader.findElement(By.xpath("//p[contains(text(),'" + question + "')]"));
    }

    @FindBy(xpath = "//input[@placeholder='DESCRIBE THE MEDICAL PROCEDURES']")
    public WebElement mediProce;

    @FindBy(xpath = "//input[@placeholder='APPOINTMENT DATE']")
    public WebElement appointmentDate;

    @FindBy(xpath = "//input[@placeholder='DATE MEDICATION RUNS OUT']")
    public WebElement dateMedicineRunsOut;

    @FindBy(xpath = "//input[@placeholder='TELL US ABOUT THE CARE']")
    public WebElement tellUsAboutTheCare;

    @FindBy(xpath = "//input[@placeholder='DATE BABY IS DUE']")
    public WebElement dateBabyIsOut;

    @FindBy(xpath = "//input[@placeholder='DENTIST NAME']")
    public WebElement dentistName;

    @FindBy(xpath = "//input[@placeholder='DESCRIBE THE HEALTH PROBLEM OR TREATMENT PLAN']")
    public WebElement healthProblemOrTreatmentPlan;

    @FindBy(xpath = "//input[@placeholder=\"DOCTOR’S NAME\"]")
    public WebElement doctorNameHRA;

    @FindBy(xpath = "//input[@placeholder='PLEASE DESCRIBE ANY HEALTH PROBLEMS OR MEDICAL TREATMENTS.']")
    public WebElement pleaseDescribeAnyHealthProblemsOrMedicalTreathments;

    @FindBy(xpath = "//span[text()='SAVE']")
    public WebElement saveButton;

    @FindBy(xpath = "//span[contains(text(),'BACK')]//parent::button")
    public WebElement backButton;

    @FindBy(xpath = "//span[text()='View Response']")
    public WebElement viewResponse;
}
