package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.security.cert.X509Certificate;
import java.util.List;

public class CRMApplicationTrackingPage {

    

    public CRMApplicationTrackingPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//span[@title='Application View']")
    public WebElement applicationIcon;

    @FindBy(xpath = "(//div[@class='py-2 px-2 mx-layout-top-drawer-fields float-left']/p)[1]")
    public WebElement primaryIndividualName;

    @FindBy(xpath = "(//div[@class='py-2 px-2 mx-layout-top-drawer-fields float-left']/p)[2]")
    public WebElement applicationId;

    @FindBy(xpath = "//a[.='APPLICATION TRACKING']")
    public WebElement applicationTrackingTab;

    @FindBy(xpath = "//a[.='APPLICATION']")
    public WebElement applicationTab;

    @FindBy(xpath = "//h2[.='APPLICATION INFORMATION']")
    public WebElement applicationInformationPanel;

    @FindBy(xpath = "//h2[text()='APPLICATION INFORMATION']/following-sibling::div")
    public WebElement applicationStatus;

    @FindBy(xpath = "//span[text()='WITHDRAW']//parent::button")
    public WebElement withdrawLink;

    @FindBy(xpath = "//span[text()='WITHDRAW']//parent::button//preceding-sibling::button")
    public WebElement editButtonApplicationInformation;

    @FindBy(xpath = "//div[@class='row mb-3 px-3']//div/p")
    public List<WebElement> appInfoColumns;

    @FindBy(xpath = "//p[.='APPLICATION ID']")
    public WebElement applicationIdUnderApplicationTrackingTab;

    @FindBy(xpath = "//h2[contains(text(), 'PRIMARY INDIVIDUAL DETAILS')]/following-sibling::div/table//td")
    public List<WebElement> primaryIndividualDetails;

    @FindBy(xpath = "//div[@class='d-flex justify-content-between mb-3']//h6")
    public List<WebElement> applicationInformationPanelValues;

    @FindBy(xpath = "//h3[text()='CONTACT INFO']/following-sibling::div[@class='row px-0']/div/div/h6")
    public List<WebElement> contactInfoValues;

    @FindBy(xpath = "//span[@title='Keyboard Back Arrow']")
    public WebElement backArrow;

    @FindBy(xpath = "(//tr[@class='MuiTableRow-root pl-0 MuiTableRow-head'])[1]/th[@class='MuiTableCell-root MuiTableCell-head mdl-data-table__cell--non-numeric' and text() != '']")
    public List<WebElement> memberInfoColumns;

    @FindBy(xpath = "//i[text()='person']")
    public WebElement primaryIndividualIcon;

    @FindBy(xpath = "//h3[.='AUTHORIZED REPRESENTATIVE(S)']")
    public WebElement authorizedRepresentativePanelHeader;

    @FindBy(xpath = "//input[@id='withdrawSelectReason']/parent::div")
    public WebElement applicationWithdrawReasonDropdown;

    @FindBy(xpath = "//div[@id='menu-withdrawSelectReason']//li")
    public List<WebElement> withdrawReasonList;

    @FindBy(xpath = "//h5[.='APPLICATION WITHDRAW REASON']")
    public WebElement applicationWithdrawReasonText;

    @FindBy(xpath = "//span[text()='SAVE']")
    public WebElement withdrawSaveButton;

    @FindBy(xpath = "//span[text()='CANCEL']")
    public WebElement withdrawCancelButton;

    @FindBy(xpath = "(//div[@class='modal-content']//div)[10]//p")
    public WebElement warningMessageForWithdaw;

    @FindBy(xpath = "//span[text()='Continue']")
    public WebElement withdrawContinueButtonInsideWarning;

    @FindBy(xpath = "//span[text()='Cancel']")
    public WebElement withdrawCancelButtonInsideWarning;

    @FindBy(xpath = "//th[.='APPLICANT STATUS']/../../following-sibling::tbody//td[4]")
    public List<WebElement> applicantStatusValues;

    @FindBy(xpath = "//p[.='APPLICATION ID']/following-sibling::h6")
    public WebElement appPannelIdValue;

    @FindBy(xpath = "//p[.='SIGNATURE DATE']/following-sibling::h6")
    public WebElement signatureDateValue;

    @FindBy(xpath = "//p[.='RECEIVED DATE']/following-sibling::h6")
    public WebElement receivedDateValue;

    @FindBy(xpath = "//p[.='CHANNEL']/following-sibling::h6")
    public WebElement channelValue;

    @FindBy(xpath = "//p[.='CREATE DATE']/following-sibling::h6")
    public WebElement createDateValue;

    @FindBy(xpath = "//p[.='LAST UPDATED DATE']/following-sibling::h6")
    public WebElement lastUpdatedDateValue;

    @FindBy(xpath = "//p[.='PRIORITY']/following-sibling::h6")
    public WebElement priorityValue;

    @FindBy(xpath = "//p[.='DEADLINE DATE']/following-sibling::h6")
    public WebElement deadlineDateValue;

    @FindBy(xpath = "//input[@id='applicationDeadlineDate']")
    public WebElement deadlineDateInputBox;

    @FindBy(xpath = "//p[contains(text(),'DEADLINE DATE cannot be before RECEIVED DATE')]")
    public WebElement deadlineDateWarningMessage;

    @FindBy(xpath = "//i[text()='check']")
    public WebElement applicationInfoSaveButton;

    @FindBy(xpath = "//p[.='APPLICATION TYPE']/following-sibling::h6")
    public WebElement appTypeValue;

    @FindBy(xpath = "//p[.='CYCLE']/following-sibling::h6")
    public WebElement cycleValue;

    @FindBy(xpath = "//h3[contains(text(),'MEMBER(S) INFO')]/parent::div/child::button")
    public WebElement editButtonMembersInfoPanel;

    @FindBy(xpath = "//th[.='FULL NAME']")
    public WebElement memberInfoFullNameLabel;

    @FindBy(xpath = "//th[.='AGE / GENDER']")
    public WebElement memberInfoAgeGenderLabel;

    @FindBy(xpath = "//th[.='APPLICANT STATUS']")
    public WebElement memberInfoApplicantStatusLabel;

    @FindBy(xpath = "//th[.='PROGRAM(S) APPLIED FOR']")
    public WebElement memberInfoProgramAppliedLabel;

    @FindBy(xpath = "//th[.='ELIGIBILITY STATUS']")
    public WebElement memberInfoEligibilityStatusLabel;

    @FindBy(xpath = "//th[.='DENIAL REASON(S)']")
    public WebElement memberInfoDenialReasonsLabel;

    @FindBy(xpath = "//th[.='DETERMINATION DATE']")
    public WebElement memberInfoDeterminationDateLabel;

    @FindBy(xpath = "//th[.='START DATE']")
    public WebElement memberInfoStartDateLabel;

    @FindBy(xpath = "//th[.='END DATE']")
    public WebElement memberInfoEndDateLabel;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody/tr[1]/td[2]")
    public WebElement memberInfoFirstRowFullName;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody/tr[1]/td[3]")
    public WebElement memberInfoFirstRowAgeGender;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody/tr[1]/td[4]")
    public WebElement memberInfoFirstRowApplicantStatus;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody/tr[1]/td[5]")
    public WebElement memberInfoFirstRowPrograms;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody/tr[1]/td[6]")
    public WebElement memberInfoFirstRowEligiblityStatus;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody[1]/tr[1]/td[8]")
    public WebElement memberInfoFirstRowDenialReasons;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody/tr[2]/td[8]")
    public WebElement memberInfoSecondRowDenialReasons;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody[1]/tr[1]/td[9]")
    public WebElement memberInfoFirstRowDeterminationDate;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody/tr[2]/td[9]")
    public WebElement memberInfoSecondRowDeterminationDate;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody[1]/tr[1]/td[10]")
    public WebElement memberInfoFirstRowStartDate;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody/tr[2]/td[10]")
    public WebElement memberInfoSecondRowStartDate;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody[1]/tr[1]/td[11]")
    public WebElement memberInfoFirstRowEndDate;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody/tr[2]/td[11]")
    public WebElement memberInfoSecondRowEndDate;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody/tr/td[3]")
    public List<WebElement> memberInfoAgeGenderColumn;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody[1]/tr[1]/td[5]")
    public WebElement memberInfoFirstRowFirstProgram;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody[1]/tr[2]/td[1]")
    public WebElement memberInfoFirstRowSecondProgram;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody[1]/tr[3]/td[1]")
    public WebElement memberInfoFirstRowThirdProgram;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody[1]/tr[1]/td[5]")
    public WebElement hcbsFirstRowFirstProgram;

    @FindBy(xpath = "//h3[text()='CONTACT INFO']/following-sibling::div[@class='row px-0']/div/div/p")
    public List<WebElement> contactInfoLabels;

    @FindBy(xpath = "//h3[text()='CONTACT INFO']")
    public WebElement contactInfoPanelHeader;

    @FindBy(xpath = "//h3[.='AUTHORIZED REPRESENTATIVE(S)']/following::table/thead/tr[1]/th")
    public List<WebElement> authRepPanelLabels;

    @FindBy(xpath = "//h3[.='AUTHORIZED REPRESENTATIVE(S)']/following::table/tbody/tr[1]/td")
    public List<WebElement> authRepPanelFirstRowValues;

    @FindBy(xpath = "//h3[.='AUTHORIZED REPRESENTATIVE(S)']/following::table/tbody/tr[2]/td")
    public List<WebElement> authRepPanelSecondRowValues;

    @FindBy(xpath = "//h3[.='AUTHORIZED REPRESENTATIVE(S)']/following::table/tbody/tr[3]/td")
    public List<WebElement> authRepPanelThirdRowValues;

    @FindBy(xpath = "//h3[.='AUTHORIZED REPRESENTATIVE(S)']/following::table/tbody/tr[4]/td")
    public List<WebElement> authRepPanelFourthRowValues;

    @FindBy(xpath = "//h3[.='AUTHORIZED REPRESENTATIVE(S)']/following::table/tbody/tr/td[3]")
    public List<WebElement> authRepPanelAuthTypeColumn;

    @FindBy(xpath = "//h3[.='AUTHORIZED REPRESENTATIVE(S)']/following::table/tbody/tr/td[4]")
    public List<WebElement> authRepPanelAccessTypeColumn;

    @FindBy(xpath = "//p[.='PRIORITY']")
    public WebElement priorityLabel;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody/tr[2]/td[4]")
    public WebElement memberInfoSecondRowApplicantStatus;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody/tr[2]/td[4]")
    public WebElement memberInfothirdRowApplicantStatus;

    @FindBy(css = "#mui-component-select-eligibilityStatus")
    public List<WebElement> memberInfoEligibilityStatusDropDownList;

    @FindBy(css = "#determinationDate")
    public List<WebElement> memberInfoDeterminationDateList;

    @FindBy(css = "#eligibilityStartDate")
    public List<WebElement> memberInfoStartDateList;

    @FindBy(css = "#eligibilityEndDate")
    public List<WebElement> memberInfoEndDateList;

    @FindBy(css = "#mui-component-select-denialReasons")
    public List<WebElement> membersInfoEligibilityDenialReasonsDropdownList;

    @FindBy(css = "#mui-component-select-subProgramId")
    public List<WebElement> membersInfoEligibilitySubProgramList;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody/tr[*]/td[5]")
    public List<WebElement> membersInfoEligibilityProgramList;

    @FindBy(xpath = "//h3[contains(text(),'MEMBER(S) INFO')]/parent::div/div/div/div/button/span[text()='SAVE']")
    public WebElement saveButtonMembersInfoPanel;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody/tr/td[6]")
    public WebElement memberInfoFirstRowEligibilityStatus;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody/tr[2]/td[6]")
    public WebElement memberInfoSecondRowEligibilityStatus;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody/tr[3]/td[6]")
    public WebElement memberInfoThirdRowEligibilityStatus;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody/tr[2]/td[5]")
    public WebElement memberInfoSecondRowPrograms;

    @FindBy(xpath = "//p[.='EXTERNAL APP ID']/following-sibling::h6")
    public WebElement externalAppId;

    @FindBy(xpath = "//h5[contains(text(),'LINKS')]")
    public WebElement linkHeader;

    @FindBy(xpath = "//i[@class='material-icons ml-4 align-middle mdl-color-text--grey-600']")
    public List<WebElement> linkApplicationIconList;

    @FindBy(xpath = "//i[contains(text(),'chevron_right')]/parent::button/parent::td/following-sibling::td")
    public List<WebElement> linkTableData;

    @FindBy(id = "mui-component-select-atsCommonGrid")
    public WebElement paginationButton;

    @FindBy(xpath = "//ul[@class='MuiList-root MuiMenu-list MuiList-padding']")
    public WebElement paginationDropdown;

    @FindBy(xpath="//button[@title='Refresh']")
    public WebElement refreshButton;

    @FindBy(xpath = "//i[.='link']//following::table[1]/tbody[1]/tr")
    public List<WebElement> linkSize;

    @FindBy(xpath = "//i[contains(text(),'assignment')]")
    public WebElement taskIcon;

    @FindBy(xpath = "//i[contains(text(),'library_books')]")
    public WebElement correspondenceIcon;

    @FindBy(xpath = "//i[.='link']//following::table[1]/tbody[1]/tr/td[4]")
    public List<WebElement> linkNames;

    @FindBy(xpath = "//i[.='link']//following::table[1]/tbody[1]/tr/td[3]")
    public List<WebElement> linkIDs;

    @FindBy(xpath = "//i[.='link']//following::table[1]/tbody[1]/tr/td[5]")
    public List<WebElement> linkTypes;

    @FindBy(xpath = "//i[.='link']//following::table[1]/tbody[1]/tr/td[6]")
    public List<WebElement> linkStatusDates;

    @FindBy(xpath = "//i[.='link']//following::table[1]/tbody[1]/tr/td[7]")
    public List<WebElement> linkStatus;

    @FindBy(xpath = "//*[@title='Missing Info Indicator']")
    public List<WebElement> missingInfoFlag;

    @FindBy(xpath ="//*[@id='missing-info Notification']")
    public WebElement miTabFlag;

    @FindBy(xpath = "//h3[contains(text(),'MEMBER(S) INFO')]/following::tbody/tr[2]/td[1]//*[@title='Missing Info Indicator']")
    public WebElement appMemberMIFlag;

    @FindBy(xpath = "//h3[contains(text(),'MEMBER(S) INFO')]/following::tbody/tr[1]/td[1]//*[@title='Missing Info Indicator']")
    public WebElement primaryIndividualMIFlag;

    @FindBy(xpath = "//ul[@role='listbox']/li")
    public List<WebElement> eligibilityDropdownList;

    @FindBy(id = "mui-component-select-eligibilityStatus")
    public WebElement eliStatusDropdown;

    @FindBy(id = "mui-component-select-denialReasons")
    public WebElement eliDenialDropdown;

    @FindBy(id = "mui-component-select-subProgramId")
    public WebElement eliSubProgram;

    @FindBy(xpath = "(//*[@id='mui-component-select-subProgramId'])[2]")
    public WebElement eliSubProgramSecondRow;

    @FindBy(id = "determinationDate")
    public WebElement eligibilityDeterminationDate;

    @FindBy(id = "eligibilityStartDate")
    public WebElement eligibilityStartDate;

    @FindBy(id = "eligibilityEndDate")
    public WebElement eligibilityEndDate;

    @FindBy(xpath = "//p[.='Date cannot be in the future']")
    public WebElement futureWarningMsg;

    @FindBy(xpath = "//p[.='ELIGIBILITY START DATE is required and cannot be left blank']")
    public WebElement reqStartDateMsg;

    @FindBy(xpath = "//p[.='DENIAL REASON(S) is required and cannot be left blank']")
    public WebElement reqDenialMsg;

    @FindBy(xpath = "//a[contains(text(), 'MISSING INFO')]")
    public WebElement missingInfoTab;

    @FindBy(xpath = "//i[contains(text(), 'file_copy')]")
    public WebElement fileIcon;

    @FindBy(xpath = "//button[contains(text(), 'INITIATE')]")
    public WebElement initiateButton;

    @FindBy(xpath = "//p[.='RECEIVED LANGUAGE']")
    public WebElement savedReceivedLanguageLabel;

    @FindBy(xpath = "//p[.='RECEIVED LANGUAGE']/following-sibling::h6")
    public WebElement savedReceivedLanguage;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody/tr[2]//td[2]")
    public WebElement memberInformationSecondRowEligibilityStatus;

    @FindBy(xpath = "(//*[@id='determinationDate'])[2]")
    public WebElement eligibilitySecondDeterminationDate;

    @FindBy(xpath = "(//*[@id='eligibilityStartDate'])[2]")
    public WebElement eligibilitySecondStartDate;

    @FindBy(id = "(//*[@id='eligibilityEndDate'])[2]")
    public WebElement eligibilitySecondEndDate;

    @FindBy(id = "(//*[@id='mui-component-select-denialReasons'])[2]")
    public WebElement eliDenialSecondDropdown;

    @FindBy(xpath = "//*[contains(text(),'LINKS')]/../..//table/tbody//td[4][text()='Case']/..//td[3]")
    public List<WebElement> caseId;

    @FindBy(xpath = "//p[.='APPLICATION CODE']")
    public WebElement applicationCodeLabel;

    @FindBy(xpath = "//p[.='APPLICATION CODE']/following-sibling::h6")
    public WebElement applicationCodeValue;

    @FindBy(xpath = "//h3[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[1]/td[2]")
    public WebElement authRepName;

    @FindBy(xpath = "//h3[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[1]/td[3]")
    public WebElement authType;

    @FindBy(xpath = "//h3[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[1]/td[4]")
    public WebElement accessType;

    @FindBy(xpath = "//h3[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[1]/td[5]")
    public WebElement authRepAuthorizedValue;

    @FindBy(xpath = "//h3[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[1]/td[6]")
    public WebElement authRepStatusValue;

    @FindBy(xpath = "//h3[contains(text(),'AUTHORIZED REPRESENTATIVE')]")
    public WebElement authorizationRepresentativeSection;

    @FindBy(xpath = "(//h3[.='MEMBER(S) INFO']//following::table[1]/tbody/tr/td[6])[2]")
    public WebElement memberInfoFirstRowSecondProgramEligibilityStatus;

    @FindBy(xpath = "(//*[@id='mui-component-select-denialReasons'])[2]")
    public WebElement eliDenialDropdownForSecondProgram;

    @FindBy(xpath = "(//*[@id='mui-component-select-subProgramId'])[2]")
    public WebElement eliSubProgramForSecondProgram;

    @FindBy(xpath = "(//*[@id = 'determinationDate'])[2]")
    public WebElement eligibilityDeterminationDateForSecondProgram;

    @FindBy(xpath = "(//*[@id = 'eligibilityStartDate'])[2]")
    public WebElement eligibilityStartDateForSecondProgram;

    @FindBy(xpath = "//p[.='SUB PROGRAM is required and cannot be left blank']")
    public WebElement subProgramRequiredMsg;

    @FindBy(xpath = "//h3[.='MEMBER(S) INFO']//following::table[1]/tbody[1]/tr[1]/td[7]")
    public WebElement memberInfoFirstRowSubProgram;
}
