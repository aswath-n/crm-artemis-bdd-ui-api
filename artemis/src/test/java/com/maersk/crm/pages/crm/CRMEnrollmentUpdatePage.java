package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMEnrollmentUpdatePage {
    

    public CRMEnrollmentUpdatePage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//span[contains(text(),'ADD CASE MEMBERS(S)')]")
    public WebElement enrollmentUpdateAddCaseMembersButtonPlus;

    @FindBy(xpath = "//button[@id='add-case-member'][@aria-controls='add_case_member']")
    public WebElement enrollmentUpdateAddCaseMembersButton;

    public WebElement getButtonByText(String buttonText) {
        return Driver.getDriver().findElement(By.xpath("//button[contains(span, '" + buttonText + "')]"));
    }

    @FindBy(xpath = "//ul[@role='menu']/li")
    public WebElement selectAllOptionFromCaseMembersDropDown;

    public WebElement getConsumerSelectionByFullName(String fullName) {
        return selectAllOptionFromCaseMembersDropDown.findElement(By.xpath("./../li[text() = '" + fullName + "']"));
    }

    public WebElement getConsumersRemoveButtonByGivenFullName(String fullName){
//        return Driver.getDriver().findElement(By.xpath("//td[contains(span, '" + fullName + "')]/../td[9]//button"));
        return Driver.getDriver().findElement(By.xpath("//td//span[.='" + fullName + "']/../../..//td[9]//button"));
    }

    public WebElement getConsumerDetailsRowByFullName(String fullName){
        return Driver.getDriver().findElement(By.xpath("//td[contains(span, '" + fullName + "')]/../"));
    }

    @FindBy(xpath = "//button[contains(text(), 'SELECT')]")
    public List<WebElement> selectPlanBttn;

    @FindBy(xpath = "(//tbody[1])[2]/tr[1]/td[6]//button")
    public WebElement selectPlanBttn1;

    @FindBy(xpath = "//span/button[@role='button']")
    public List<WebElement> deleteBttnForCaseMembers;

    @FindBy(xpath = "//i[.='link_off']//ancestor::button")
    public WebElement removePlanOtionBttn;

    @FindBy(xpath = "//p[contains(text(),'ENROLLMENT UPDATE')]")
    public WebElement enrollmentUpdatePageHead;

    @FindBy(xpath = "//p[contains(text(),'ENROLLMENT OVERRIDE')]")
    public WebElement enrollmentOverridePageHead;

    @FindBy(xpath = "//p[contains(text(),'ENROLLMENT EDIT')]")
    public WebElement enrollmentEditPageHead;

    @FindBy(xpath = "(//tbody)[1]/tr/td/span")
    public List<WebElement> consumerPanelFieldsList;

    @FindBy(xpath = "(//tr[@class='MuiTableRow-root'])[1]/td[2]")
    public WebElement enrollmentUpdatePageBeneficiaryName;

    @FindBy(xpath = "(//tr[@class='MuiTableRow-root'])[1]/td[3]/div/span")
    public WebElement enrolmentUpdatePageAgeGenderField;

    @FindBy(xpath = "(//tr[@class='MuiTableRow-root'])[1]/td[4]/div/span")
    public WebElement enrolmentUpdatePagePopulationField;

    @FindBy(xpath = "(//tr[@class='MuiTableRow-root'])[1]/td[5]")
    public WebElement enrolmentUpdatePageAddressField;

    @FindBy(xpath = "(//tr[@class='MuiTableRow-root'])[1]/td[6]")
    public WebElement enrolmentUpdatePageCurrentPlanField;

    @FindBy(xpath = "(//tr[@class='MuiTableRow-root'])[1]/td[7]")
    public WebElement enrolmentUpdatePageCurrentProviderField;

    @FindBy(xpath = "(//tr[@class='MuiTableRow-root'])[1]/td[8]")
    public WebElement enrolmentUpdatePageEligibilityDetailsField;

    public WebElement getSelectedConsumerAgeGenderOnEnrollmentUpdatePageAgeByFullName(String fullname) {
        return Driver.getDriver().findElement(By.xpath("//span[.='" + fullname + "']/ancestor::tr[@class='MuiTableRow-root']/td[3]"));
    }

    public WebElement getSelectedConsumerPopulationOnEnrollmentUpdatePageAgeByFullName(String fullname) {
        return Driver.getDriver().findElement(By.xpath("//span[.='" + fullname + "']/ancestor::tr[@class='MuiTableRow-root']/td[4]"));
    }

    public WebElement getSelectedConsumerResidentialAddressOnEnrollmentUpdatePageAgeByFullName(String fullname) {
        return Driver.getDriver().findElement(By.xpath("//span[.='" + fullname + "']/ancestor::tr[@class='MuiTableRow-root']/td[5]"));
    }

    public WebElement getSelectedConsumerCurrentPlanOnEnrollmentUpdatePageAgeByFullName(String fullname) {
        return Driver.getDriver().findElement(By.xpath("//span[.='" + fullname + "']/ancestor::tr[@class='MuiTableRow-root']/td[6]"));
    }

    public WebElement getSelectedConsumerCurrentProviderOnEnrollmentUpdatePageAgeByFullName(String fullname) {
        return Driver.getDriver().findElement(By.xpath("//span[.='" + fullname + "']/ancestor::tr[@class='MuiTableRow-root']/td[7]"));
    }

    public WebElement getSelectedConsumerEligibilityDetailsOnEnrollmentUpdatePageAgeByFullName(String fullname) {
        return Driver.getDriver().findElement(By.xpath("//span[.='" + fullname + "']/ancestor::tr[@class='MuiTableRow-root']/td[8]"));
    }

    @FindBy(xpath = "//span[.='keyboard_backspace']")
    public WebElement enrolmentUpdatePageArrowNavigateBack;

    @FindBy(xpath = "//span[.=' WARNING MESSAGE']")
    public WebElement enrolmentUpdatePageWarningMessage;

    @FindBy(xpath = "//span[@id='client-snackbar'][contains(text(),'Enrollment selection dates are not valid')]")
    public WebElement enrollmentSelectionDatesAreNotValidMessage;

    @FindBy(xpath = "//div[@class='col-12 px-5']/button")
    public List<WebElement> listOfButtonsWarningMsg;

    @FindBy(xpath = "//ul[@class='mx-sidenavmenu-items ']/li")
    public List<WebElement> listSideBarMenu;

//    @FindBy( xpath = "//div[@class='row px-2']//table//td/span")
//     public List<WebElement>listOfPlanDetails;

    @FindBy(xpath = "//a[@class='undefined']")
    public WebElement paginationElement;

    @FindBy(xpath = "//h6[@class='pt-2 m-0']")
    public WebElement searchProviderPanel;

    @FindBy(xpath = "//i[.='search']")
    public WebElement searchProviderBttn;

    @FindBy(xpath = "//h5[contains(text(),'PLANS AVAILABLE')]")
    public WebElement plansAvailablePanel;

//    @FindBy(xpath = "//div[@class='row px-2']//table//tbody/tr[1]/td[2]/span")
    @FindBy(xpath = "//div[@class='row px-2']//table//tbody//tr//td[2]//span")
    public List<WebElement> listOfPlanNamesOnPlansAvailableSection;

    @FindBy(xpath = "//div[@class='row px-2']//table//tbody/tr[1]/td[3]/span")
    public List<WebElement> listOfPlanTypeOnPlansAvailableSection;

    @FindBy(xpath = "//div[@class='row px-2']//table//tbody/tr[1]/td[4]//span")
    public List<WebElement> listOfStartDateOnPlansAvailableSection;

    @FindBy(xpath = "//div[@class='row px-2']//table//tbody/tr[1]/td[5]/span")
    public List<WebElement> listOfEndDateOnPlansAvailableSection;

    @FindBy(xpath = "//input[@name='enrollmentUpdateReason']")
    public WebElement reasonPlanDropdown;

    @FindBy(xpath = "//button[@id='update-enrollment']")
    public WebElement updateEnrollmentInfoBttn;

    @FindBy(xpath = "//*[.='PLAN NAME']")
    public WebElement planNameOnPlanDetailsPanel;

    @FindBy(xpath = "//*[.='PLAN NAME']/following-sibling::h6")
    public WebElement planNameValue;

    @FindBy(xpath = "//*[.='SERVICE REGION']")
    public WebElement serviceRegionOnPlanDetailsPanel;

    @FindBy(xpath = "//*[.='SERVICE REGION']/following-sibling::h6")
    public WebElement serviceRegionValue;

    @FindBy( xpath = "//label[contains(text(),'START DATE')]")
    public WebElement startDateOnPlanDetailsPanel;

    @FindBy( xpath = "//label[contains(text(),'START DATE')]/following-sibling::div/input")
    public WebElement startDateInputValue;

    @FindBy(xpath = "//*[.='END DATE']")
    public WebElement endDateOnPlanDetailsPanel;

    @FindBy(xpath = "//*[.='END DATE']/following-sibling::h6")
    public WebElement endDateValue;

    @FindBy(xpath = "//*[.='PLAN TYPE']")
    public WebElement planTypeOnPlanDetailsPanel;

    @FindBy(xpath = "//*[.='PLAN TYPE']/following-sibling::h6")
    public WebElement planTypeValue;

    @FindBy(xpath = "//button[@id='update-enrollment']")
    public WebElement submitButton;

    @FindBy(xpath = "//h6[@class='m-0']")
    public List<WebElement> selectedPlanName;

    @FindBy(xpath = "//input[@id='startDate']")
    public WebElement selectedPlanStartDate;

    @FindBy(xpath = "//*[.='END DATE']/../h6")
    public WebElement selectedPlanEndDate;

    @FindBy(xpath = "//*[.='START DATE']/../h6")
    public WebElement selectedPlanStartDateH6;

    @FindBy(xpath = "(//table)[1]//td[6]//span")
    public List<WebElement> listOfCurrentPlansOnConsumerDetailsPanel;

    @FindBy(xpath = "//span/button[@role='button']")
    public List<WebElement> listOfDeleteBttnOnConsumerDetailsPanel;

    @FindBy(xpath = "//h5[.='No Records']")
    public WebElement noRecordsFieldOnConsumerDetailsPanel;

    @FindBy(xpath = "//ul[@role='listbox']//li[1]")
    public WebElement dropDownReason;

    @FindBy(xpath = "//ul[@role='listbox']//li")
    public List<WebElement> dropDownReasons;

    @FindBy(id = "update-enrollment")
    public WebElement enrollmentUpdateSubmitBttn;

    @FindBy(xpath="//label[.='REASON']/../div")
    public WebElement enrolmentUpdateReasonbttn;

    @FindBy(id = "_REASON-option-1")
    public WebElement reasonDropDownChoice;

    @FindBy(xpath = "//label[.='OVER RIDE REASON']/../div")
    public WebElement enrolmentOverrideReasonbttn;

    @FindBy(xpath = "//*[.='WARNING MESSAGE']/..//div")
    public WebElement enrolmentUpdateWarningMessage;

    @FindBy(xpath = "//div[@id='menu-overrideReason']//li[1]")
    public WebElement overrideDropDownFirstReason;

    @FindBy(xpath = "(//table)[1]//td[2]//span")
    public List<WebElement> listConsumersNamesEnrollmentPage;

    @FindBy(xpath = "//ul[@role='menu']/li")
    public List<WebElement> listAddCaseMembers;

    @FindBy(xpath = "//div[1]/div[2]/div[1]/div[3]/span[1]")
    public WebElement calendarDays;

    @FindBy(xpath = "//span[@class='MuiBadge-badge MuiBadge-anchorOriginTopRightRectangle MuiBadge-colorError MuiBadge-dot']")
    public WebElement calendarRedDot;

    @FindBy(xpath = "//span[contains(text(),'DISREGARD')]")
    public WebElement disregardBtn;

    @FindBy(xpath = "//span[contains(text(),'DISREGARD')]")
    public List<WebElement> disregardButtons;

    @FindBy(xpath = "//p[@class='px-3']")
    public WebElement warningMeassage;

    @FindBy(xpath = "//span[text()='Continue']")
    public WebElement continuebtn;

    @FindBy(xpath = "//h6[contains(text(),'FUTURE ENROLLMENT')]")
    public WebElement futureEnrollment;

    @FindBy(xpath = "//button/span[contains(text(),'EDIT')]")
    public WebElement editBtn;

    @FindBy(xpath = "//*[.='SERVICE REGION']/following-sibling::p")
    public List<WebElement> serviceRegions;

    @FindBy(xpath = "//p[.='PLAN NAME']/following-sibling::p")
    public List<WebElement> ConsumerPrograms;

    @FindBy(xpath = "//*[.='CURRENT ENROLLMENT']/../..//button[./span[.='PLAN CHANGE']]")
    public WebElement frstPlanChangeButton;

    @FindBy(xpath = "//button[@id='add-case-member']")
    public WebElement addCMButton;

    @FindBy(xpath = "//div[@id='simple-menu']/div[3]/ul[1]/li")
    public List<WebElement> addedmembers;

    @FindBy(xpath = "//div[@class='d-flex flex-row ']/div[2]/p")
    public List<WebElement> listMembers;

    @FindBy(xpath = "//p[contains(text(),'ENROLLMENT EDIT')]")
    public WebElement enrollmentEdit;

    @FindBy(xpath = "//h5[contains(text(),'SELECTED CONSUMER(S)')]")
    public WebElement consumerDetails;

    @FindBy(xpath = "//h5[contains(text(),'SELECTED CONSUMER(S)')]/..//td[2]")
    public List<WebElement> selectedConsumersNames;

    @FindBy(xpath = "//h5[contains(text(),'SELECTED PLAN(S)')]")
    public WebElement selectedPlanDetails;

    @FindBy(xpath = "//span[contains(text(),'REMOVE PLAN')]")
    public WebElement removePlanBtn;

    @FindBy(xpath = "//span[text()=\"Cancel\"]/..")
    public WebElement cancelBtn;

    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text mdl-button--primary mx-btn mx-btn-border mx-btn-cancel float-left ml-2']")
    public WebElement cancelBtnWarningMessage;

    @FindBy(xpath = "//span[text()=\"Continue\"]/.")
    public WebElement continueBtn;

    @FindBy(id = "update-enrollment")
    public WebElement submitBttn;

    @FindBy(xpath = "//*[.='REASON']/..//div")
    public WebElement reasonDropDownBttn;

    @FindBy(xpath = "//div[@class='MuiAutocomplete-popper']//li")
    public List<WebElement> reasonDropDownChoices;

    @FindBy(xpath = "//div[@id='menu-planSelectionReasons']//ul/li[@tabindex='-1']")
    public List<WebElement> editreasonDropDownChoices;

    @FindBy(xpath = "//span[contains(text(),'PCP SELECT')]")
    public WebElement pcpSelectBttn;

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

    @FindBy(xpath = "//button[contains(@class, 'MuiPickersDay-daySelected')]")
    public WebElement selectedCalendarDay;

    public WebElement calendarActionButton(String key) {
        return editCalendarDateForm.findElement(By.xpath(".//span[.='" + key + "']"));
    }

    @FindBy(xpath = "//div[@class='MuiPickersCalendarHeader-switchHeader']//button[1]")
    public WebElement calendarPreviousMonthButton;

    @FindBy(xpath = "//div[@class='MuiPickersCalendarHeader-switchHeader']//button[2]")
    public WebElement calendarNextMonthButton;

    public WebElement getPlanSelectButtonByGivenPlanName(String planName){
        return Driver.getDriver().findElement(By.xpath("//tr//td/div//span[.='"+planName+"']/../../..//td[6]//button"));
    }

    @FindBy(id = "_CHANNEL")
    public WebElement channelDropdown;

    public WebElement getChannelOption(String channelOption){
        return Driver.getDriver().findElement(By.xpath("//ul[@id='_CHANNEL-popup']//li[.='"+channelOption+"']"));
    }

    public WebElement getreasonOption(String reasonOption){
        return Driver.getDriver().findElement(By.xpath("//div[@id='menu-planSelectionReasons']//ul/li[.='"+reasonOption+"']"));
    }

    @FindBy(xpath = "//div[@class='MuiAutocomplete-endAdornment']//button[@title='Open']")
    public WebElement channelDropdownButton;

    @FindBy(xpath = "//ul[@id='_CHANNEL-popup']//li")
    public List<WebElement> channelDropdownOptions;

    @FindBy(xpath="//p[text()='Start Date']/..//h6")
    public WebElement EEStartDate;

    @FindBy(xpath = "//span[contains(text(),'PCP/PDP SELECT')]")
    public WebElement pcpPdpSearch;

    @FindBy(xpath = "//button[contains(text(),'SEARCH PCP/PDP')]//i")
    public WebElement searchPCPPDP;

    @FindBy(xpath = "//div[@class='p-2']//i[contains(@id,'core-indicator')]")
    public WebElement mmisindicatoricon;

    @FindBy(xpath = "//div[contains(@Class,'MuiTooltip')]//p" )
    public WebElement mmistooltipmsg;


}
