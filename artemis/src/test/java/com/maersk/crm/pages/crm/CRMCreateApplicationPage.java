package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMCreateApplicationPage {

    

    public CRMCreateApplicationPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//*[contains(text(), 'CREATE APPLICATION')]")
    public WebElement createApplicationButton;

    @FindBy(xpath = " //li[@id='createApplicationForm']/div/span")
    public WebElement crateApplicationMenu;

    @FindBy(xpath = "//div[@id='sub-menu-list']//li")
    public List<WebElement> createApplicationSubMenuList;

    @FindBy(xpath = "//*[contains(text(), 'LONG TERM CARE')]")
    public WebElement longTermCare;

    @FindBy(xpath = "//*[contains(text(), 'MEDICAL ASSISTANCE')]")
    public WebElement medicalAssistance;

    @FindBy(xpath = "//h2[@class='m-0 h3 mx-fontweight-status float-left']")
    public WebElement applicationPageHeader;

    @FindBy(xpath = "//*[@title='Keyboard Back Arrow']")
    public WebElement backArrowCreateApp;

    @FindBy(xpath = "//*[text()='keyboard_backspace']/ancestor::div[1]/div/p[contains(text(),'CREATE APPLICATION')]")
    public WebElement backArrowName;

    @FindBy(id = "firstName")
    public WebElement firstName;

    @FindBy(id = "middleInitial")
    public WebElement middleInitial;

    @FindBy(id = "lastName")
    public WebElement lastName;

    @FindBy(id = "suffix")
    public WebElement suffix;

    @FindBy(id = "dateOfBirth")
    public WebElement DOB;

    @FindBy(xpath = "//*[@id='genderCode']/..")
    public WebElement genderDropdown;

    @FindBy(id = "SSN")
    public WebElement SSN;

    @FindBy(xpath = "//*[@id='spokenLanguage']/..")
    public WebElement spokenLanguageDropdown;

    @FindBy(xpath = "//*[@id='writtenLanguage']/..")
    public WebElement writtenLanguageDropdown;

    @FindBy(id = "externalConsumerID")
    public WebElement externalConsumerID;

    @FindBy(xpath = "//*[@id='externalIDType']/..")
    public WebElement externalIdTypeDropdown;

    @FindBy(id = "cellPhoneNumber")
    public WebElement cellPhoneNumber;

    @FindBy(id = "homePhoneNumber")
    public WebElement homePhoneNumber;

    @FindBy(id = "workPhoneNumber")
    public WebElement workPhoneNumber;

    @FindBy(id = "faxNumber")
    public WebElement faxNumber;

    @FindBy(id = "emailAddress")
    public WebElement emailAddress;

    @FindBy(xpath = "//input[@value='Medicaid']")
    public WebElement medicaidCheckbox;

    @FindBy(xpath = "//input[@value='CHIP']")
    public WebElement CHIPCheckbox;

    @FindBy(xpath = "//input[@value='Pregnancy Assistance']")
    public WebElement pregnancyAssistanceCheckbox;

    @FindBy(xpath = "//input[@value='HCBS']")
    public WebElement HCBSCheckbox;

    @FindBy(id = "selectNotApplying")
    public WebElement NOTAPPLYINGCheckbox;

    @FindBy(xpath = "//*[@id='pregnancyNoOfBabies']/..")
    public WebElement numberOfBabiesDropdown;

    @FindBy(id = "pregnancyDueDate")
    public WebElement expectedDueDate;

    @FindBy(xpath = "//*[contains(text(),'ARE YOU PREGNANT')]")
    public WebElement pregnancyIndicatorHeader;

    @FindBy(xpath = "//*[contains(text(),'ARE YOU PREGNANT')]/following-sibling::div//input[@value='true']")
    public WebElement pregnancyIndicatorYESCheckbox;

    @FindBy(xpath = "//*[contains(text(),'ARE YOU PREGNANT')]/following-sibling::div//input[@value='false']")
    public WebElement pregnancyIndicatorNOCheckbox;

    @FindBy(xpath = "//input[@id='channel']//..")
    public WebElement channelDropdown;

    @FindBy(xpath = "(//span[contains(text(),'check')])[1]")
    public WebElement saveButton;

    @FindBy(xpath = "//*[contains(text(),' Submit')]")
    public WebElement submitBtn;

    @FindBy(xpath = "//div[@class='float-right mx-form-status']")
    public WebElement applicationStatus;

    @FindBy(xpath = "(//span[contains(text(),'check')])[3]")
    public WebElement continueBtn;

    @FindBy(xpath = "(//span[contains(text(), 'ADD AUTHORIZED REPRESENTATIVE')]//parent::button)")
    public WebElement addAuthorizedRepBtn;

    @FindBy(xpath = "//p[contains(text(), 'ADD APPLICATION MEMBER')]")
    public WebElement applicationMemberPage;

    @FindBy(xpath = "//p[contains(text(), 'ADD AUTHORIZED REPRESENTATIVE')]")
    public WebElement authorizedRepPage;

    @FindBy(id = "consumerFirstName")
    public WebElement memberFirstName;

    @FindBy(id = "consumerLastName")
    public WebElement memberLastName;

    @FindBy(xpath = "//*[text()='DATE OF BIRTH  is required and cannot be left blank']")
    public WebElement DOBErrorMessage;

    @FindBy(xpath = "//*[text()='SSN  is required and cannot be left blank']")
    public WebElement SSNErrorMessage;

    @FindBy(xpath = "//*[text()='GENDER  is required and cannot be left blank']")
    public WebElement GenderErrorMessage;

    @FindBy(xpath = "//*[text()='NO. OF BABIES EXPECTED  is required and cannot be left blank']")
    public WebElement noOfBabiesErrorMessage;

    @FindBy(xpath = "//*[text()='EXPECTED DUE DATE  is required and cannot be left blank']")
    public WebElement expectedDueDateErrorMessage;

    @FindBy(xpath = "//div[@class='py-2 px-2 mx-layout-top-drawer-fields float-left']")
    public WebElement applicationId;

    @FindBy(xpath = "//div[@class='py-2 px-2 mx-layout-top-drawer-fields float-left']/p[2]")
    public WebElement applicationIdText;

    @FindBy(xpath = "//*[@id='applicationCycle']/..")
    public WebElement applicationCycleDropdown;

    @FindBy(xpath = "//label[contains(text(),'FIRST NAME')]")
    public WebElement firstNameLabel;

    @FindBy(xpath = "//label[contains(text(),'MI')]")
    public WebElement middleInitialLabel;

    @FindBy(xpath = "//label[contains(text(),'LAST NAME')]")
    public WebElement lastNameLabel;

    @FindBy(xpath = "//label[contains(text(),'SUFFIX')]")
    public WebElement suffixLabel;

    @FindBy(xpath = "//label[contains(text(),'DATE OF BIRTH')]")
    public WebElement dateOfBirthLabel;

    @FindBy(xpath = "//label[contains(text(),'AGE') and @for='age']")
    public WebElement ageLabel;

    @FindBy(xpath = "//label[contains(text(),'GENDER')]")
    public WebElement genderLabel;

    @FindBy(xpath = "//label[contains(text(),'SSN')]")
    public WebElement ssnLabel;

    @FindBy(xpath = "//label[contains(text(),'SPOKEN LANGUAGE')]")
    public WebElement spokenLanguageLabel;

    @FindBy(xpath = "//label[contains(text(),'WRITTEN LANGUAGE')]")
    public WebElement writtenLanguageLabel;

    @FindBy(xpath = "//label[contains(text(),'EXTERNAL CONSUMER ID')]")
    public WebElement externalConsumerIdLabel;

    @FindBy(xpath = "//label[contains(text(),'EXTERNAL ID TYPE')]")
    public WebElement externaleIdTypeLabel;

    @FindBy(id = "cellPhoneNumber-label")
    public WebElement cellPhoneNumberLabel;

    @FindBy(id = "workPhoneNumber-label")
    public WebElement workPhoneNumberLabel;

    @FindBy(id = "homePhoneNumber-label")
    public WebElement homePhoneNumberLabel;

    @FindBy(id = "faxNumber-label")
    public WebElement faxNumberLabel;

    @FindBy(xpath = "//label[contains(text(),'EMAIL')]")
    public WebElement emailLabel;

    @FindBy(xpath = "//label[contains(text(),'ADDRESS LINE 1')]")
    public WebElement residenceAddressOneLabel;

    @FindBy(xpath = "//label[contains(text(),'ADDRESS LINE 2')]")
    public WebElement residenceAddressTwoLabel;

    @FindBy(id = "addressCity-label")
    public WebElement residenceCityLabel;

    @FindBy(id = "addressState-label")
    public WebElement residenceStateLabel;

    @FindBy(id = "addressZip-label")
    public WebElement residencZipLabel;

    @FindBy(id = "addressCounty-label")
    public WebElement residenceCountyLabel;

    @FindBy(xpath = "//label[@for='MaddressStreet1']")
    public WebElement mailAddressStreetOneLabel;

    @FindBy(xpath = "//label[@for='MaddressStreet2']")
    public WebElement mailAddressStreetTwoLabel;

    @FindBy(id = "MaddressCity-label")
    public WebElement mailCityLabel;

    @FindBy(id = "MaddressState-label")
    public WebElement mailStateLabel;

    @FindBy(id = "MaddressZip-label")
    public WebElement mailZipLabel;

    @FindBy(xpath = "//span[contains(text(),'MEDICAID')]")
    public WebElement medicaidLabel;

    @FindBy(xpath = "//span[contains(text(),'CHIP')]")
    public WebElement chipLabel;

    @FindBy(xpath = "//span[contains(text(),'PREGNANCY ASSISTANCE')]")
    public WebElement pregnanyAssistLabel;

    @FindBy(xpath = "//span[contains(text(),'HCBS')]")
    public WebElement hcbsLabel;

    @FindBy(xpath = "//span[contains(text(),'EMAIL')]")
    public WebElement communicationEmailLabel;

    @FindBy(xpath = "//span[contains(text(),'FAX')]")
    public WebElement faxLabel;

    @FindBy(xpath = "//span[.='MAIL']")
    public WebElement mailLabel;

    @FindBy(xpath = "//span[contains(text(),'PHONE')]")
    public WebElement phoneLabel;

    @FindBy(xpath = "//span[contains(text(),'TEXT')]")
    public WebElement textLabel;

    @FindBy(xpath = "//label[contains(text(),'APPLICATION CYCLE')]")
    public WebElement appCycleLabel;

    @FindBy(xpath = "//label[contains(text(),'RECEIVED DATE')]")
    public WebElement appReceivedDateleLabel;

    @FindBy(xpath = "//label[contains(text(),'CHANNEL')]")
    public WebElement channelLabel;

    @FindBy(xpath = "//legend[contains(text(),'APPLICATION SIGNATURE')]")
    public WebElement appSignatureLabel;

    @FindBy(xpath = "//label[contains(text(),'SIGNATURE DATE')]")
    public WebElement signatureDateLabel;

    @FindBy(xpath = "//button[contains(@aria-label,'change date')]")
    public WebElement calendar;

    @FindBy(id = "addressStreet1")
    public WebElement addressLineOne;

    @FindBy(id = "addressStreet2")
    public WebElement addressLineTwo;

    @FindBy(id = "auto-addressCity")
    public WebElement residenceCity;

    @FindBy(id = "auto-addressState")
    public WebElement residenceState;

    @FindBy(id = "auto-addressZip")
    public WebElement residenceZipcode;

    @FindBy(id = "auto-addressCounty")
    public WebElement residenceCounty;

    @FindBy(id = "MaddressStreet1")
    public WebElement mAddressLineOne;

    @FindBy(id = "MaddressStreet2")
    public WebElement mAddressLineTwo;

    @FindBy(id = "auto-MaddressCity")
    public WebElement mCity;

    @FindBy(id = "auto-MaddressState")
    public WebElement mState;

    @FindBy(id = "auto-MaddressZip")
    public WebElement mZipcode;

    @FindBy(id = "sameAsResAddr")
    public WebElement sameAsResidenceCheckBox;

    @FindBy(xpath = "//input[@id='list-item-0' and @value='Email']")
    public WebElement communicationEmailCheckbox;

    @FindBy(xpath = "//input[@id='list-item-1' and @value='Fax']")
    public WebElement communicationFaxCheckbox;

    @FindBy(xpath = "//input[@id='list-item-2' and @value='Mail']")
    public WebElement communicationMailCheckbox;

    @FindBy(xpath = "//input[@id='list-item-3' and @value='Phone']")
    public WebElement communicationPhoneCheckbox;

    @FindBy(xpath = "//input[@id='list-item-4' and @value='Text']")
    public WebElement communicationTextCheckbox;

    @FindBy(id = "mui-component-select-applicationCycle")
    public WebElement cycleDropdown;

    @FindBy(id = "applicationReceivedDate")
    public WebElement appReceivedDate;

    @FindBy(xpath = "//legend[.='APPLICATION SIGNATURE']/..//input[@type='radio' and @value='true']")
    public WebElement appSignatureYes;

    @FindBy(xpath = "//legend[.='APPLICATION SIGNATURE']/..//input[@type='radio' and @value='false']")
    public WebElement appSignatureNo;

    @FindBy(id = "signatureDate")
    public WebElement signatureDate;

    @FindBy(xpath = "(//h2[contains(text(),'PRIMARY INDIVIDUAL DETAILS')]/following::tbody/tr[1]/td[2])[1]")
    public WebElement savedFullName;

    @FindBy(xpath = "//h2[contains(text(),'PRIMARY INDIVIDUAL DETAILS')]/following::tbody//td[2]")
    public List<WebElement> savedListApplicantFullName;

    @FindBy(xpath = "//h2[contains(text(),'PRIMARY INDIVIDUAL DETAILS')]/following::tbody/tr[1]/td[3]")
    public WebElement savedDOB;

    @FindBy(xpath = "//h2[contains(text(),'PRIMARY INDIVIDUAL DETAILS')]/following::tbody/tr[1]/td[4]")
    public WebElement saveSSN;

    @FindBy(xpath = "//h2[contains(text(),'PRIMARY INDIVIDUAL DETAILS')]/following::tbody/tr[1]/td[5]")
    public WebElement SavedAgeGender;

    @FindBy(xpath = "//h2[contains(text(),'PRIMARY INDIVIDUAL DETAILS')]/following::tbody/tr[1]/td[6]")
    public WebElement savedSpokenLanguage;

    @FindBy(xpath = "//h2[contains(text(),'PRIMARY INDIVIDUAL DETAILS')]/following::tbody/tr[1]/td[7]")
    public WebElement savedWrittenLanguage;

    @FindBy(xpath = "//h2[contains(text(),'PRIMARY INDIVIDUAL DETAILS')]/following::tbody/tr[1]/td[8]")
    public WebElement savedPrograms;

    @FindBy(xpath = "//h2[contains(text(),'PRIMARY INDIVIDUAL DETAILS')]/following::tbody/tr[1]/td[9]")
    public WebElement savedOptIns;

    @FindBy(xpath = "//p[contains(text(),'ARE YOU PREGNANT')]/following-sibling::p")
    public WebElement savedAreYouPregnant;

    @FindBy(xpath = "//p[contains(text(),'NO. OF BABIES EXPECTED')]/following-sibling::p")
    public WebElement savedExpectedBabies;

    @FindBy(xpath = "//p[contains(text(),'EXPECTED DUE DATE')]/following-sibling::p")
    public WebElement savedExpectedDueDate;

    @FindBy(xpath = "//p[contains(text(),'EXTERNAL CONSUMER ID')]/following-sibling::p")
    public WebElement savedExternalId;

    @FindBy(xpath = "//p[contains(text(),'EXTERNAL ID TYPE')]/following-sibling::p")
    public WebElement savedExternalType;

    @FindBy(xpath = "//p[.='CELL PHONE NUMBER']/following-sibling::h6")
    public WebElement savedCellPhone;

    @FindBy(xpath = "//p[.='HOME PHONE NUMBER']/following-sibling::h6")
    public WebElement savedHomePhone;

    @FindBy(xpath = "//p[.='WORK PHONE NUMBER']/following-sibling::h6")
    public WebElement savedWorkPhone;

    @FindBy(xpath = "//p[.='FAX NUMBER']/following-sibling::h6")
    public WebElement savedFaxNumber;

    @FindBy(xpath = "//p[.='EMAIL']/following-sibling::h6")
    public WebElement savedEmail;

    @FindBy(xpath = "(//p[.='ADDRESS LINE 1'])[1]/following-sibling::h6")
    public WebElement savedResidAddOne;

    @FindBy(xpath = "(//p[.='ADDRESS LINE 1'])[2]/following-sibling::h6")
    public WebElement savedMailAddOne;

    @FindBy(xpath = "(//p[.='ADDRESS LINE 2'])[1]/following-sibling::h6")
    public WebElement savedResidAddtwo;

    @FindBy(xpath = "(//p[.='ADDRESS LINE 2'])[2]/following-sibling::h6")
    public WebElement savedMailAddTwo;

    @FindBy(xpath = "(//p[.='CITY'])[1]/following-sibling::h6")
    public WebElement savedResidCity;

    @FindBy(xpath = "(//p[.='CITY'])[2]/following-sibling::h6")
    public WebElement savedMailCity;

    @FindBy(xpath = "(//p[.='STATE'])[1]/following-sibling::h6")
    public WebElement saveResidState;

    @FindBy(xpath = "(//p[.='STATE'])[2]/following-sibling::h6")
    public WebElement savedMailState;

    @FindBy(xpath = "(//p[.='ZIP CODE'])[1]/following-sibling::h6")
    public WebElement savedResidZip;

    @FindBy(xpath = "(//p[.='ZIP CODE'])[2]/following-sibling::h6")
    public WebElement savedMailZip;

    @FindBy(xpath = "(//p[.='COUNTY'])[1]/following-sibling::h6")
    public WebElement savedResidCounty;

    @FindBy(xpath = "//p[.='APPLICATION CYCLE']/following-sibling::h6")
    public WebElement savedAppCycle;

    @FindBy(xpath = "//p[.='RECEIVED DATE']/following-sibling::h6")
    public WebElement savedReceivedDate;

    @FindBy(xpath = "//p[.='CHANNEL']/following-sibling::h6")
    public WebElement savedChannel;

    @FindBy(xpath = "//p[.='SIGNATURE DATE']/following-sibling::h6")
    public WebElement savedSignatureDate;

    @FindBy(xpath = "(//span[.='Yes']/../span)[1]")
    public WebElement yesSignatureValue;

    @FindBy(xpath = "(//span[.='No']/../span)[1]")
    public WebElement noSignatureValue;

    @FindBy(xpath = "//i[contains(text(),'chevron_right')]")
    public WebElement pIChevronRight;

    @FindBy(xpath = "//span[.='visibility']")
    public WebElement ssnVisible;

    @FindBy(xpath = "//span[@id='client-snackbar' and contains(text(), 'Application successfully created!')]")
    public WebElement applicationSuccessfulyCreatedMsg;

    @FindBy(xpath = "//div[@class = 'py-2 px-2 mx-layout-top-drawer-fields float-left']/p[2]")
    public WebElement createdAppId;

    @FindBy(xpath = "//li[.='New']")
    public WebElement appCycleValueNew;

    @FindBy(xpath = "//li[.='Renewal']")
    public WebElement appCycleValueRenewal;

    @FindBy(xpath = "//ul[@class='MuiList-root MuiMenu-list MuiList-padding']/li")
    public List<WebElement> appCycleDropdownValues;

    @FindBy(xpath = "//span[contains(text(),'Cancel')]")
    public WebElement cancelButton;

    @FindBy(id = "externalIDType")
    public WebElement externalIdType;

    @FindBy(id = "genderCode")
    public WebElement getGender;

    @FindBy(id = "channel")
    public WebElement getChannel;

    @FindBy(xpath = "//input[@value='Medicaid']/../..")
    public WebElement getMedacaidCheckbox;

    @FindBy(xpath = "//input[@value='CHIP']/../..")
    public WebElement getChipCheckbox;

    @FindBy(xpath = "//input[@value='Pregnancy Assistance']/../..")
    public WebElement getPregnancyAssistanceCheckboxCheckbox;

    @FindBy(xpath = "//input[@value='Email']/../..")
    public WebElement getEmailCheckboxCheckbox;

    @FindBy(xpath = "//input[@value='Fax']/../..")
    public WebElement getFaxCheckboxCheckbox;

    @FindBy(xpath = "//input[@value='Mail']/../..")
    public WebElement getMailCheckboxCheckbox;

    @FindBy(xpath = "//input[@value='Phone']/../..")
    public WebElement getPhoneCheckboxCheckbox;

    @FindBy(xpath = "//input[@value='Text']/../..")
    public WebElement getTextCheckboxCheckbox;

    @FindBy(xpath = "//legend[.='APPLICATION SIGNATURE']/following-sibling::div/label/span")
    public WebElement getAppSignatureYes;

    @FindBy(xpath = "//legend[.='APPLICATION SIGNATURE']/following-sibling::div/label/span")
    public WebElement getAppSignatureNo;

    @FindBy(id = "mui-component-select-spokenLanguage")
    public WebElement getSpokenLanguage;

    @FindBy(id = "mui-component-select-writtenLanguage")
    public WebElement getWrittenLanguage;

    @FindBy(xpath = "//input[@value='HCBS']/../..")
    public WebElement getHCBSCheckbox;

    @FindBy(xpath = "//span[contains(text(),'ADD APPLICATION MEMBER')]//parent::button")
    public WebElement addApplicationMemberButton;

    @FindBy(xpath = "//h2[contains(text(),'APPLICATION MEMBER(S)')]/following::th[2]")
    public WebElement appMemFullNameLabel;

    @FindBy(xpath = "//h2[contains(text(),'APPLICATION MEMBER(S)')]/following::th[3]")
    public WebElement appMemDOBLabel;

    @FindBy(xpath = "//h2[contains(text(),'APPLICATION MEMBER(S)')]/following::th[4]")
    public WebElement appMemSSNLabel;

    @FindBy(xpath = "//h2[contains(text(),'APPLICATION MEMBER(S)')]/following::th[4]/div/span[.='visibility']")
    public WebElement appMemSSNRevealIcon;

    @FindBy(xpath = "//h2[contains(text(),'APPLICATION MEMBER(S)')]/following::th[5]")
    public WebElement appMemAgeGenderLabel;

    @FindBy(xpath = "//h2[contains(text(),'APPLICATION MEMBER(S)')]/following::th[6]")
    public WebElement appMemProgramsLabel;

    @FindBy(id = "setasPI")
    public WebElement appMemSetAsPIButton;

    @FindBy(xpath = "//h2[contains(text(),'APPLICATION MEMBER(S)')]/following::td[8]/i/button[.='close']")
    public WebElement appMemDeleteIcon;

    @FindBy(xpath = "//h2[contains(text(),'APPLICATION MEMBER(S)')]/following::td[1]/button/i[.='chevron_right']")
    public WebElement appMemberPanelCarrot;

    @FindBy(xpath = "(//div[@class='col-2-5 mb-3'])[1]/p[@class='mb-0 mx-field-label mx-inputlabel-read']")
    public WebElement appMemberAreYouPregnantLabel;

    @FindBy(xpath = "(//div[@class='col-2-5 mb-3'])[1]/p[@class='m-0 mx-font-1-2em mx-inputvalue-read']")
    public WebElement appMemberAreYouPregnantValue;

    @FindBy(xpath = "(//div[@class='col-2-5 mb-3'])[2]/p[@class='mb-0 mx-field-label mx-inputlabel-read']")
    public WebElement appMemberNumBaibiesExpectedLabel;

    @FindBy(xpath = "(//div[@class='col-2-5 mb-3'])[2]/p[@class='m-0 mx-font-1-2em mx-inputvalue-read']")
    public WebElement appMemberNumBaibiesExpectedValue;

    @FindBy(xpath = "(//div[@class='col-2-5 mb-3'])[3]/p[@class='mb-0 mx-field-label mx-inputlabel-read']")
    public WebElement appMemberExpectedDueDateLabel;

    @FindBy(xpath = "(//div[@class='col-2-5 mb-3'])[3]/p[@class='m-0 mx-font-1-2em mx-inputvalue-read']")
    public WebElement appMemberExpectedDueDateValue;

    @FindBy(xpath = "(//div[@class='col-2-5 mb-3'])[4]/p[@class='mb-0 mx-field-label mx-inputlabel-read']")
    public WebElement appMemberExConIDLabel;

    @FindBy(xpath = "(//div[@class='col-2-5 mb-3'])[4]/p[@class='m-0 mx-font-1-2em mx-inputvalue-read']")
    public WebElement appMemberExConIDValue;

    @FindBy(xpath = "(//div[@class='col-2-5 mb-3'])[5]/p[@class='mb-0 mx-field-label mx-inputlabel-read']")
    public WebElement appMemberExConTypeLabel;

    @FindBy(xpath = "(//div[@class='col-2-5 mb-3'])[5]/p[@class='m-0 mx-font-1-2em mx-inputvalue-read']")
    public WebElement appMemberExConTypeValue;

    @FindBy(xpath = "//h2[contains(text(),'APPLICATION MEMBER(S)')]/following::tbody/tr[1]/td[2]")
    public WebElement appMemGetFullName;

    @FindBy(xpath = "//h2[contains(text(),'APPLICATION MEMBER(S)')]/following::tbody/tr[1]/td[3]")
    public WebElement appMemGetDoB;

    @FindBy(xpath = "//h2[contains(text(),'APPLICATION MEMBER(S)')]/following::tbody/tr[1]/td[4]")
    public WebElement appMemGetSSN;

    @FindBy(xpath = "//h2[contains(text(),'APPLICATION MEMBER(S)')]/following::tbody/tr[1]/td[5]")
    public WebElement appMemGetAgeGender;

    @FindBy(xpath = "//h2[contains(text(),'APPLICATION MEMBER(S)')]/following::tbody/tr[1]/td[6]")
    public WebElement appMemGetPrograms;

    @FindBy(xpath = "//a[.='APPLICATION']")
    public WebElement applicationTab;

    @FindBy(xpath = "(//h5[.='No Records Available'])[1]")
    public WebElement applicationPanelNoRecordsMessage;

    @FindBy(xpath = "//i[.='close']")
    public WebElement applicationPanelDeleteButton;

    @FindBy(xpath = "//i[contains(text(),'edit')]//parent :: button")
    public WebElement primaryIndividualEditButton;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::td[1]/button/i[.='chevron_right']")
    public WebElement authorizedRepCollapseBtn;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[1]/td[2]")
    public WebElement nameCollapseView;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[1]/td[3]")
    public WebElement authTypeCollapseView;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[1]/td[4]")
    public WebElement accessTypeCollapseView;

    @FindBy(xpath = "//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mdl-color-text--red-600']")
    public WebElement noAuthorized;

    @FindBy(xpath = "//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mdl-color-text--green-600']")
    public WebElement authorized;

    @FindBy(xpath = "//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mdl-color-text--green-600']")
    public WebElement activeStatus;

    @FindBy(xpath = "//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mdl-color-text--red-600']")
    public WebElement inactiveStatus;

    @FindBy(xpath = "//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mdl-color-text--orange-600']")
    public WebElement futureStatus;

    @FindBy(xpath = "(//p[@class='m-0 mx-font-1-2em mx-inputvalue-read'])[3]")
    public WebElement startDateeExpandedView;

    @FindBy(xpath = "(//p[@class='m-0 mx-font-1-2em mx-inputvalue-read'])[4]")
    public WebElement endDateExpandedView;

    @FindBy(xpath = "(//p[@class='m-0 mx-font-1-2em mx-inputvalue-read'])[5]")
    public WebElement authorizedSignDateExpandedView;

    @FindBy(xpath = "(//tr[@class='MuiTableRow-root'])[2]//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mdl-color-text--green-600']")
    public WebElement arListActiveStatus;

    @FindBy(xpath = "(//tr[@class='MuiTableRow-root'])[3]//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mdl-color-text--orange-600']")
    public WebElement arListInactiveStatus;

    @FindBy(xpath = "((//tr[@class='MuiTableRow-root'])[4]//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mdl-color-text--red-600'])[2]")
    public WebElement arListFutureStatus;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[1]/td[6]")
    public WebElement authRepAuthorizedValue;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[1]/td[7]")
    public WebElement authRepStatusValue;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[1]/td[5]")
    public WebElement authRepCorrexpondenceValue;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[2]/p[1]")
    public WebElement authRepOrgNameLabel;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[2]/p[2]")
    public WebElement authRepOrgNameValue;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[3]/p[1]")
    public WebElement authRepIdNumLabel;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[3]/p[2]")
    public WebElement authRepIdNumValue;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[4]/p[1]")
    public WebElement authRepStartDateLabel;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[4]/p[2]")
    public WebElement authRepStartDateValue;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[5]/p[1]")
    public WebElement authRepEndDateLabel;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[5]/p[2]")
    public WebElement authRepEndDateValue;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[6]/p[1]")
    public WebElement authRepSignDateLabel;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[6]/p[2]")
    public WebElement authRepSignDateValue;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[7]/p[1]")
    public WebElement authRepAddress1Label;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[7]/p[2]")
    public WebElement authRepAddress1value;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[8]/p[1]")
    public WebElement authRepAddress2Label;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[8]/p[2]")
    public WebElement authRepAddress2value;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[9]/p[1]")
    public WebElement authRepCityLabel;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[9]/p[2]")
    public WebElement authRepCityValue;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[10]/p[1]")
    public WebElement authRepStateLabel;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[10]/p[2]")
    public WebElement authRepStateValue;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[11]/p[1]")
    public WebElement authRepZipLabel;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[2]//div[3]/../div[11]/p[2]")
    public WebElement authRepZipValue;

    @FindBy(xpath = "(//h3[.='APPLICATION INFO']/..//button[@aria-label='Open/Close DatePicker'])[1]")
    public WebElement appReceivedDateCalendar;

    @FindBy(xpath = "//div[@class='MuiPickersBasePicker-pickerView']/div/div/button[@tabindex='0']")
    public WebElement nextCalendarMonth;

    @FindBy(xpath = "//span[contains(text(),'OK')]")
    public WebElement calendarOkEndDate;

    @FindBy(xpath = "//p[@class='m-0 mx-color-text-secondary float-left']")
    public WebElement authRepStartEndDatePopUp;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]")
    public WebElement authRepPanelHeader;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[3]/td[7]")
    public WebElement secondAuthRepStatusValue;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[5]/td[7]")
    public WebElement thirdAuthRepStatusValue;

    @FindBy(xpath = "//p[contains(text(),'FIRST NAME is required and cannot be left blank')]")
    public WebElement firstNameRequiredMsg;

    @FindBy(xpath = "//p[contains(text(),'LAST NAME is required and cannot be left blank')]")
    public WebElement lastNameRequiredMsg;

    @FindBy(xpath = "//p[contains(text(),'CELLPHONE NUMBER is required and cannot be left blank')]")
    public WebElement cellPhoneRequiredMsg;

    @FindBy(xpath = "//p[contains(text(),'HOME PHONE NUMBER is required and cannot be left blank')]")
    public WebElement homePhoneRequiredMsg;

    @FindBy(xpath = "//p[contains(text(),'WORK PHONE NUMBER is required and cannot be left blank')]")
    public WebElement workPhoneRequiredMsg;

    @FindBy(xpath = "//p[contains(text(),'FAX NUMBER is required and cannot be left blank')]")
    public WebElement faxNumberRequiredMsg;

    @FindBy(xpath = "//*[@id='addressStreet1']/../following-sibling::p")
    public WebElement residenceAddLine1RequiredMsg;

    @FindBy(xpath = "//*[@id='MaddressStreet1']/../following-sibling::p")
    public WebElement mailAddLine1RequiredMsg;

    @FindBy(xpath = "//p[contains(text(),'CHANNEL  is required and cannot be left blank')]")
    public WebElement channelRequiredMsg;

    @FindBy(xpath = "//h2[contains(text(),'APPLICATION MEMBER(S)')]/following::tbody/tr[3]/td[2]")
    public WebElement secondAppMemNameInPanel;

    @FindBy(xpath = "//h3[contains(text(),'MEMBER(S) INFO')]/following::tbody/tr[1]/td[4]")
    public WebElement firstMemberApplicantStatus;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]/following::tbody/tr[1]/td[2]")
    public WebElement authRepHyperlink;

    @FindBy(xpath = "//p[.='Must have at least one Application Member applying for coverage.']")
    public WebElement noApplicationMemberApplyingforCoverageMsg;

    @FindBy(xpath = "//div[@class='modal-dialog modal-lg mt-3']")
    public WebElement noProgramSelectedWarningMsg;

    @FindBy(xpath = "//span[.='Application missing critical information, please update the required fields and submit again']")
    public WebElement missingCriticalInformationWarningMessage;

    @FindBy(xpath = "//p[text()='Must have at least one Application Member applying for coverage.']")
    public WebElement submitButtonProgramWarningMessage;

    @FindBy(xpath = "//h2[contains(text(), 'APPLICATION MEMBER(S)')]/following-sibling::div/table//td")
    public List<WebElement> applicationMemberSinApplicationTab;

    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiIconButton-root mx-snackbar-close-btn']//parent :: span")
    public WebElement successMsgCloseBtn;

    @FindBy(xpath = "//a[.='APPLICATION TRACKING']")
    public WebElement appTrackingTab;

    @FindBy(xpath = "//a[contains(.,'MISSING INFO')]")
    public WebElement missingInfoTab;

    @FindBy(xpath = "//h2[contains(text(),'PRIMARY INDIVIDUAL DETAILS')]")
    public WebElement piDetailsHeader;

    @FindBy(xpath = "//button[.='file_copy']")
    public List<WebElement> inbCorrPDFIconList;

    @FindBy(xpath = "//button[.='file_copy']")
    public WebElement inbCorrPDFIconListFalseCondition;

    @FindBy(xpath = "//p[.='Service  AccountOne']/following-sibling::p")
    public WebElement accountUserId;

    @FindBy(xpath = "//label[contains(text(), 'PRIORITY')]")
    public WebElement priorityLabel;

    @FindBy(id = "mui-component-select-applicationPriority")
    public WebElement priorityDropdown;

    @FindBy(xpath = "//ul[@class='MuiList-root MuiMenu-list MuiList-padding']/li")
    public List<WebElement> priorityDropdownList;

    @FindBy(xpath = "//p[contains(text(),'PRIORITY')]/following-sibling::h6")
    public WebElement savedPriorityValue;

    @FindBy(xpath = "//div[@id='menu-genderCode']/div[3]/ul/li")
    public List<WebElement> genderList;

    @FindBy(xpath = "//div[@id='menu-pregnancyNoOfBabies']/div[3]/ul/li")
    public List<WebElement> noOfBabiesExpectedList;

    @FindBy(xpath = "//div[@id='menu-spokenLanguage']/div[3]/ul/li")
    public List<WebElement> spokenLanguageList;

    @FindBy(xpath = "//div[@id='menu-writtenLanguage']/div[3]/ul/li")
    public List<WebElement> writtenLanguageList;

    @FindBy(xpath = "//div[@id='menu-externalIDType']/div[3]/ul/li")
    public List<WebElement> exIdTypeList;

    @FindBy(xpath = "//div[@id='menu-channel']/div[3]/ul/li")
    public List<WebElement> channelList;

    @FindBy(xpath = "//span[.=' error_outline ']")
    public WebElement contactInfoIcon;

    @FindBy(xpath = "//div[.='must have at least one of the following: phone/address/email']")
    public WebElement contactinfoIconHoverOverMsg;

    @FindBy(xpath = "//p[.='ADDRESS LINE 1']/following-sibling::h6")
    public WebElement sameAsResidenceMailAddressOne;

    @FindBy(xpath = "//p[.='ADDRESS LINE 2']/following-sibling::h6")
    public WebElement sameAsResidenceMailAddressTwo;

    @FindBy(xpath = "//p[.='CITY']/following-sibling::h6")
    public WebElement sameAsResidenceMailCity;

    @FindBy(xpath = "//p[.='STATE']/following-sibling::h6")
    public WebElement sameAsResidenceMailState;

    @FindBy(xpath = "//p[.='ZIP CODE']/following-sibling::h6")
    public WebElement sameAsResidenceMailZipCode;

    @FindBy(xpath = "//input[@id='cellPhoneNumber']/following-sibling::div/i")
    public WebElement cellPhoneStarIcon;

    @FindBy(xpath = "//input[@id='homePhoneNumber']/following-sibling::div/i")
    public WebElement homePhoneStarIcon;

    @FindBy(xpath = "//input[@id='workPhoneNumber']/following-sibling::div/i")
    public WebElement workPhoneStarIcon;

    @FindBy(xpath = "//h2[contains(text(),'APPLICATION FOR')]/following-sibling::div")
    public WebElement appStatusTextInGold;

    @FindBy(xpath = "//span[contains(text(),'Submit')]/..")
    public WebElement submitbuttonValue;

    @FindBy(xpath = "//input")
    public List<WebElement> inputFields;

    @FindBy(id="externalAppId")
    public WebElement externalAppId;

    @FindBy(xpath = "//span[@id='client-snackbar' and contains(text(), 'Application successfully updated!')]")
    public WebElement applicationSuccessfullyUpdatedMsg;

    @FindBy(xpath = "//p[.='EXTERNAL APP ID']/following-sibling::h6")
    public WebElement savedExternalAppId;

    @FindBy(xpath = "//div[@class='table-responsive mt-2']/div/table/tbody/tr/td[6]")
    public WebElement prgramsAppliedForAM;

    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text mx-btn  mx-btn-border mx-btn-primary float-left mb-4']")
    public WebElement changeWillNotBeSavedWarningContinueButton;

    @FindBy(xpath = "//label[contains(text(),'RECEIVED LANGUAGE')]")
    public WebElement receivedLanguageLabel;

    @FindBy(id = "mui-component-select-applicationReceivedLanguage")
    public WebElement receivedLanguageDropdown;

    @FindBy(xpath = "//div[@id='menu-applicationReceivedLanguage']/div[3]/ul/li")
    public List<WebElement> receivedLanguageList;

    @FindBy(xpath = "//p[.='RECEIVED LANGUAGE']")
    public WebElement savedReceivedLanguageLabel;

    @FindBy(xpath = "//p[.='RECEIVED LANGUAGE']/following-sibling::h6")
    public WebElement savedReceivedLanguage;

    @FindBy(xpath = "//p[.='APPLICATION CODE']")
    public WebElement applicationCodeLabel;

    @FindBy(xpath = "//p[.='APPLICATION CODE']/following-sibling::h6")
    public WebElement savedApplicationCode;

    @FindBy(xpath = "//span[text()=' GO TO APPLICATION TAB']")
    public WebElement goToApplicationTabBtn;

    @FindBy(xpath = "//span[text()='GO TO MEMBER MATCHING TAB']")
    public WebElement goToMemberMatchingTabBtn;

    @FindBy(xpath = "//h6[text()='Application Status Fetched Successfully!']")
    public WebElement statusFetchedMessage;

    @FindBy(xpath = "//h5[text()=' STATUS']/span[text()='Targets Unidentified']")
    public WebElement targetsUnidentifiedStatusOnWarningMessage;

    @FindBy(xpath = "//h5[text()=' STATUS']/span[text()='Determining']")
    public WebElement determiningStatusOnWarningMessage;

    @FindBy(xpath = "//*[contains(text(),'APPLICATION NOTES')]")
    public WebElement applicationNotes;

    @FindBy(id="mui-component-select-relatesTo")
    public WebElement relatesToDropdown;

    @FindBy(id="notesText")
    public WebElement notesText;

    @FindBy(xpath = "//span[contains(text(),'SAVE')]")
    public WebElement saveNotes;

    @FindBy(xpath = "//span[contains(text(),'CANCEL')]")
    public WebElement cancelNotes;

    @FindBy(xpath = "//span[contains(text(),'Note Successfully Created')]")
    public WebElement noteSuccessMessage;

    @FindBy(xpath = "//ul[@class='MuiList-root MuiMenu-list MuiList-padding']/li")
    public List<WebElement> relatesToDropdownValues;

    @FindBy(xpath = "//i[contains(text(),'account_circle')]/following-sibling::span[1]")
    public WebElement createdByNotes;

    @FindBy(xpath = "//i[contains(text(),'account_circle')]/following-sibling::span[2]")
    public WebElement relatesToNotes;

    @FindBy(xpath = "//p[@class='text-right pt-2']")
    public WebElement createdOnNotes;

    @FindBy(xpath = "//p[@class='ml-3 pl-4']")
    public WebElement existingNotesText;

    @FindBy(xpath = "//h5[text()='APPLICATION NOTES']/following-sibling::div//p[2]")
    public List<WebElement> applicationNotesDateTimeList;

    @FindBy(xpath = "//h5[text()='APPLICATION NOTES']/following-sibling::div//p[3]")
    public List<WebElement> notesTextList;

    @FindBy(xpath = "//h5[text()='APPLICATION NOTES']/following-sibling::div//p[1]")
    public List<WebElement> createdByList;


}
