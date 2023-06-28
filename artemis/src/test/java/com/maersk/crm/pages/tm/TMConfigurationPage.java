package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;


public class TMConfigurationPage {

    

    public TMConfigurationPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    //Below elements are present on Service region config Tab
    @FindBy(xpath = "(//i[(contains(text(),'attach_file'))])[1]")
    public WebElement serviceAttachFile;

    @FindBy(xpath = "//input[@id='file-attach-service']")
    public WebElement getServiceAttachFile;

    @FindBy(xpath = "(//i[(contains(text(),'attach_file'))])[1]/../following-sibling::p")
    public WebElement fileAttachedInServiceTab;

    @FindBy(xpath = "//span[contains(text(),'SERVICE REGION CONFIG.')]")
    public WebElement serviceRegionMainTabHdr;

    @FindBy(xpath = "//h5[contains(text(),' SEARCH SERVICE REGION')]")
    public WebElement searchServiceRegionHdr;

    @FindBy(xpath = "(//span[contains(text(),' Upload')])[1]")
    public WebElement serviceTabUpload;

    @FindBy(xpath = "(//span[contains(text(),'Cancel')])[1]")
    public WebElement serivceTabCancelBtn;

    @FindBy(xpath = "//h5[contains(text(),'SERVICE')]")
    public WebElement serivceTabHdr;

    //Below elements are common for both service region config and plan config tab
    @FindBy(xpath = "//span[contains(text(),'ERROR')]")
    public WebElement uploadErrorMsg;

    @FindBy(xpath = "//span[contains(text(),'ERROR')]/following-sibling::div")
    public WebElement uploadErrorMsgTxt;

    @FindBy(xpath = "//span[contains(text(),'SUCCESS MESSAGE')]")
    public WebElement uploadScucessMsg;

    @FindBy(xpath = " //span[contains(text(),'Upload')]")
    public WebElement uploadScucessMsgTxt;

    @FindBy(xpath = "//span[contains(text(),'ERROR')]")
    public WebElement uploadExtnMsg;

    @FindBy(xpath = "//span[contains(text(),'File')]")
    public WebElement uploadExtnMsgTxt;

    @FindBy(xpath = "//span[contains(text(),' WARNING MESSAGE')]")
    public WebElement warningPopUpMsg;

    @FindBy(xpath = "//span[contains(text(),' WARNING MESSAGE')]/../../..//p")
    public WebElement waringPopUpMsgTxt;

    @FindBy(xpath = "//span[text()='Continue']")
    public WebElement continuebtnWarningPopUp;

    @FindBy(xpath = "//span[contains(text(),'check')]/../../following-sibling::button//span[contains(text(),'clear')]")
    public WebElement cancelbtnWarningPopUp;

    @FindBy(xpath = "//span[contains(text(),'keyboard_backspace')]")
    public WebElement backArrow;

    //Below elements are present in plan config tab and related to Plan config tab test scripts 
    @FindBy(xpath = "(//span[contains(text(),' Upload')])[2]")
    public WebElement plansTabUpload;

    @FindBy(xpath = "(//span[contains(text(),'Cancel')])[2]")
    public WebElement plansTabCancelBtn;

    @FindBy(xpath = "//h5[text()='PLAN CONFIGURATION UPLOAD']")
    public WebElement plansTabHdr;

    @FindBy(xpath = "//h5[text()='Project List']")
    public WebElement projectListPage;

    @FindBy(xpath = "//h5[contains(text(),'TASK TYPE')]")
    public WebElement taskTypePage;

    @FindBy(xpath = "//a[contains(text(),'Task Type')]")
    public WebElement taskTypeLink;

    @FindBy(xpath = "//a[contains(text(),'Plans Management')]")
    public WebElement planManagement;

    @FindBy(xpath = "//a[contains(text(),'Upload Project')]")
    public WebElement uploadProject;

    @FindBy(xpath = "//span[contains(text(),'PLANS CONFIG.')]")
    public WebElement planConfigTab;

    @FindBy(xpath = "(//label[contains(text(),'CHOOSE FILE')])[2]")
    public WebElement planAttachFile;

    @FindBy(xpath = "//input[@id='file-attach-plans']")
    public WebElement getPlanAttachFile;

    @FindBy(xpath = "(//i[(contains(text(),'attach_file'))])[2]/../following-sibling::p")
    public WebElement fileAttachedInplanTab;

    @FindBy(xpath = "//span[contains(text(),'Please')]")
    public WebElement uploadExtnMsgTxtForNoFile;

    @FindBy(id = "mui-component-select-serviceRegionName")
    public WebElement serviceRegionNameDropDwn;

    @FindBy(xpath = "(//ul)[3]/li")
    public List<WebElement> serviceRegionNameDrpDwnLst;

    @FindBy(xpath = "//*[@id=\"_County Name and Code\"]")
    public WebElement countyNameAndCodeDropDwn;

    @FindBy(id = "react-select-2-input")
    public WebElement countyNameAndCodeInput;

    @FindBy(xpath = "//*[@id=\"_County\"]")
    public WebElement countyInputBx;

    @FindBy(xpath = "//div[@class=\"MuiFormControl-root MuiTextField-root mt-0 MuiFormControl-marginNormal MuiFormControl-fullWidth\"]")
    public WebElement countyNameAndCodeInputValue;

    @FindBy(name = "zipCode")
    public WebElement zipCodeInput;

    @FindBy(xpath = "//*[text()=' SEARCH PLANS']/../following-sibling::div[8]//input")
    public WebElement zipCodePlanConfigInput;


    @FindBy(xpath = "(//span[text()='search'])[1]/../..")
    public WebElement searchBtn;

    @FindBy(xpath = "//div[@id=\"plains config\"]/descendant::span[text()='search']")
    public WebElement planTabSearchBtn;

    @FindBy(xpath = "(//span[text()=' CANCEL'])[1]/..")
    public WebElement searchServiceRegionCancelBtn;

    @FindBy(xpath = "//table/thead/tr/th[2]/span")
    public WebElement searchResultHeader;

    @FindBy(xpath = "//table/tbody/tr[1]/td")
    public List<WebElement> searchResultList;

    @FindBy(xpath = "//span[text()=\"CORPORATE HEADQUARTERS\"]")
    public WebElement configEndOfPage;

    @FindBy(xpath = "(//span[@id=\"client-snackbar\"])[2]")
    public WebElement searchServiceRegionInvalidSearchErrorMsg;

    @FindBy(xpath = "(//span[text()=\"STATE-WIDE\"])[1]")
    public WebElement stateWideCheckBx;

    @FindBy(xpath = "(//ul)[3]/li")
    public List<WebElement> planTabNameDrpDwnLst;

    @FindBy(xpath= "//*[@id=\"menu-subProgramType\"]/div[3]/ul/li")
    public List<WebElement> subprogramTypeDrpDwnLst;

    @FindBy(xpath= "//*[@id=\"menu-programType\"]/div[3]/ul/li")
    public List<WebElement> programTypeDrpDwnLst;

    //Change below locator to dif page
    @FindBy(xpath = "(//*[@placeholder='MM/DD/YYYY'])[3]")
    public WebElement projectGoLiveDate;

    @FindBy(xpath = "(//div[@class=\"modal-content\"]//div)[9]/p")
    public WebElement planTab_warningMsg;

    @FindBy(xpath = "//a[text()='Plans Management']")
    public WebElement plansManagementTab;

    @FindBy(xpath = "//h5[text()='SERVICE REGION CONFIGURATION']")
    public WebElement serviceRgnConfigHeader;

    @FindBy(xpath = "//h5[text()='PLAN CONFIGURATION UPLOAD']")
    public WebElement planConfigHeader;

    @FindBy(id = "mui-component-select-planName")
    public WebElement searchPlanName;

    @FindBy(id = "mui-component-select-programType")
    public WebElement searchProgramType;

    @FindBy(xpath = "(//input[@type='checkbox'])[2]")
    public WebElement searchStateWideChxBx;

    @FindBy(xpath = "//*[text()='SUCCESS MESSAGE']")
    public WebElement successfulUploadPopUpHeader;

    @FindBy(xpath = "//*[text()='Upload Successful - Please Upload Plan File']")
    public WebElement serviceRegionSpecificPopupMsg;


    @FindBy(id = "mui-component-select-planName")
    public WebElement planNameBx;

    @FindBy(id = "mui-component-select-planCode")
    public WebElement searchPlanCode;

    @FindBy(id = "mui-component-select-serviceRegion")
    public WebElement serviceRegionBx;

    @FindBy(id = "mui-component-select-subProgramType")
    public WebElement subProgramTypeBx;

    @FindBy(xpath = "//*[@id=\"mui-component-select-programType\"]")
    public WebElement ProgramTypeBx;

    @FindBy(xpath = "(//input[@name='zipCode'])[2]")
    public WebElement zipCodeBx;



    @FindBy(xpath = "//*[@id=\"plains config\"]/div[3]/div/button[2]")
    public WebElement cancelBtn;

    @FindBy(xpath = "//*[text()='No Records Available']")
    public WebElement noSearchResultsHeader;

    @FindBy(xpath = "//div[@class='float-right pr-5']/button")
    public WebElement projectListPgBtn;

    @FindBy(xpath = "//h5[contains(text(),'- PLAN DETAILS')]")
    public WebElement planDetailsHeader;

    @FindBy(xpath = "(//table[@class='MuiTable-root mdl-data-table mdl-js-data-table mx-table table mt-4 mb-0 mdl-color--grey-50']/tbody/tr/td[2])[1]")
    public WebElement firstSearchResult;

    @FindBy(xpath = "//span[text()='PLAN NAME']")
    public WebElement planNameResultsTableHead;

    @FindBy(xpath = "//ul[@class='pagination']/li")
    public WebElement paginationIcon;

    @FindBy(xpath = "//*[text()='ERROR MESSAGE']")
    public WebElement searchPlanErrMsg;

    @FindBy(xpath = "//*[text()='Please enter the search parameters']")
    public WebElement searchPlanErrMsgText;

    @FindBy(xpath = "//input[@id='plan-name']")
    public WebElement planNameValue;

    @FindBy(xpath = "//input[@id='plan-code']")
    public WebElement planCodeValue;

    @FindBy(xpath = "//*[@id=\"menu-planCode\"]/div[3]/ul/li")
    public List<WebElement> planCodeDrpDwnList;

    @FindBy(xpath = "(//span[text()='edit']/../..)[1]")
    public WebElement planDetails_planInfo_editBtn;

    @FindBy(xpath = "//p[text()=\"ELIGIBILITY  LIMITATION\"]/../h6")
    public WebElement planDetails_planInfo_eligibilityInput;

    @FindBy(xpath = "(//span[text()='edit']/../..)[3]")
    public WebElement planDetails_enrollmentInfo_editBtn;

    @FindBy(xpath = "//p[text()=\"ENROLLMENT CAP\"]/../h6")
    public WebElement planDetails_planInfo_EnrollmentCapInput;

    @FindBy(xpath = "//input[@id='service-region']")
    public WebElement serviceRegionValue;

    @FindBy(xpath = "//input[@id='program-type']")
    public WebElement programTypeValue;

    @FindBy(xpath = "//input[@id='sub-program-type']")
    public WebElement subProgramTypeValue;

    @FindBy(xpath = "//h5[text()=' SEARCH PLANS']")
    public WebElement searchPlanHeader;

    @FindBy(xpath = "//h5[text()='SERVICE REGION CONFIGURATION']")
    public WebElement serviceRegionConfigHeader;

    @FindBy(xpath = "//div[@class='MuiSnackbarContent-message']")
    public WebElement uploadMsgPane;

    @FindBy(xpath = "//h5[text()='No Records Available']")
    public WebElement resultNoRecords;

    @FindBy(xpath = "//*[text()='ZIP CODE']")
    public WebElement zipCodeField;

    @FindBy(xpath = "//*[text()='ZIP CODE']/following-sibling::h6")
    public WebElement zipCode;

    @FindBy(xpath = "//*[text()='STATE']")
    public WebElement stateField;

    @FindBy(xpath = "//*[text()='STATE']/following-sibling::h6")
    public WebElement state;

    @FindBy(xpath = "//*[text()='CITY']")
    public WebElement cityField;

    @FindBy(xpath = "//*[text()='CITY']/following-sibling::h6")
    public WebElement city;

    @FindBy(xpath = "//*[text()='ADDRESS LINE 2']/following-sibling::h6")
    public WebElement addressLine2;

    @FindBy(xpath = "//input[@name='addressLine2']")
    public WebElement addressLine2Input;

    @FindBy(xpath = "//*[text()='ADDRESS LINE 2']")
    public WebElement addressLine2Field;

    @FindBy(xpath = "//*[text()='Correspondence Outbound Configuration']")
    public WebElement outboundCorrespondenceSidebarTab;

    @FindBy(xpath = "//*[contains(text(),'is required and cannot be left blank.')]")
    public WebElement fieldsErrorMsg;

    @FindBy(xpath = "//*[contains(text(),'Duplicate record found in the file')]")
    public WebElement duplicateErrorMsg;

    @FindBy(xpath = "//*[contains(text(),'is not in the correct format/length.')]")
    public WebElement formatErrorMsg;

    @FindBy(xpath = "//*[contains(text(),'More than one record found with the same plan code')]")
    public WebElement duplicationErrorMsg;

    @FindBy(xpath = "//*[contains(text(),'Program was not found in the system')]")
    public WebElement unknownProgramErrorMsg;

    @FindBy(xpath = "//*[contains(text(),'Sub-Program was not found in the system')]")
    public WebElement unknownSubProgramErrorMsg;

    @FindBy(xpath = "//*[contains(text(),'Service Region was not found in the system')]")
    public WebElement unknownServiceRegionErrorMsg;

    @FindBy(xpath = "//*[contains(text(),'County Name was not found in the system')]")
    public WebElement unknownCountyErrorMsg;

    @FindBy(xpath = "//*[contains(text(),'Zip Code was not found in the system')]")
    public WebElement unknownZipCodeErrorMsg;

    @FindBy(xpath = "//*[contains(text(),'City was not found in the system')]")
    public WebElement unknownCityErrorMsg;

    @FindBy(xpath = "//*[contains(text(),'contract end date is less than the contract start date')]")
    public WebElement precedingContractEndDateErrorMsg;

    @FindBy(xpath = "//*[contains(text(),'is not equal to or greater than the contract start date')]")
    public WebElement precedingContractStartErrorMsg;

    @FindBy(xpath = "//*[contains(text(),'enrollment end date is less than the enrollment start date')]")
    public WebElement enrollmentEndPrecedesEnrollmentStartErrorMsg;

    @FindBy(xpath = "//*[contains(text(),'The Value Added Services sheet is missing required information')]")
    public WebElement valueAddedSrvcsErrorMsg;

    @FindBy(xpath = "//*[text()='File name must begin with the word Plan_']")
    public WebElement fileNameErrorMsg;

    @FindBy(xpath = "//*[text()='File sheets are not named correctly']")
    public WebElement sheetNameErrorMsg;

    @FindBy(xpath = "//*[text()='File sheets are not in the correct order']")
    public WebElement sheetOrderErrorMsg;

    @FindBy(xpath = "(//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mx-btn mx-btn-border mx-btn-primary mr-3'])[2]")
    public WebElement searchPlansBtn;

    @FindBy(xpath = "//table/thead/tr/th[2]/span")
    public WebElement searchResultPlansHeader;

    @FindBy(xpath = "//span[text()='PLAN DETAILS']")
    public WebElement planDetailsTab;

    @FindBy(xpath = "//span[text()='CONTACT DETAILS']")
    public WebElement contactDetailsTab;

    @FindBy(xpath = "//span[text()='VALUE ADDED SERVICES']")
    public WebElement valueAddedServicesTab;

    @FindBy(xpath = "//span[text()='PLAN DETAILS']/../..")
    public WebElement planDetailsButton;

    @FindBy(xpath = "//*[text()='PLAN NAME']")
    public WebElement picPlanName;

    @FindBy(xpath = "//*[text()='PLAN CODE']")
    public WebElement picPlanCode;

    @FindBy(xpath = "//*[text()='PLAN SHORT NAME']")
    public WebElement picPlanShortName;

    @FindBy(xpath = "//*[text()='SERVICE REGION']")
    public WebElement picServiceRegion;

    @FindBy(xpath = "//*[text()='PLAN TYPE']")
    public WebElement picPlanType;

    @FindBy(xpath = "//*[text()='PROGRAM TYPE']")
    public WebElement picProgramType;

    @FindBy(xpath = "//*[text()='SUB PROGRAM TYPE']")
    public WebElement picSubType;

    @FindBy(xpath = "//*[text()='ELIGIBILITY  LIMITATION']")
    public WebElement picEligibility;


    @FindBy(xpath = "//*[text()='PLAN NAME']/following-sibling::h6")
    public WebElement picPlanNameValue;

    @FindBy(xpath = "//*[text()='PLAN CODE']/following-sibling::h6")
    public WebElement picPlanCodeValue;

    @FindBy(xpath = "//*[text()='PLAN SHORT NAME']/following-sibling::h6")
    public WebElement picPlanShortNameValue;

    @FindBy(xpath = "//*[text()='SERVICE REGION']/following-sibling::h6")
    public WebElement picServiceRegionValue;

    @FindBy(xpath = "//*[text()='PLAN TYPE']/following-sibling::h6")
    public WebElement picPlanTypeValue;

    @FindBy(xpath = "//*[text()='PROGRAM TYPE']/following-sibling::h6")
    public WebElement picProgramTypeValue;

    @FindBy(xpath = "//*[text()='SUB PROGRAM TYPE']/following-sibling::h6")
    public WebElement picSubTypeValue;

    @FindBy(xpath = "//*[text()='ELIGIBILITY  LIMITATION']/following-sibling::h6")
    public WebElement picEligibilityValue;

    @FindBy(xpath = "//*[text()='EXCLUDE PLAN FROM AUTO-ASSIGNMENT']/../span")
    public WebElement picExcludeAutoCheckBx;

    @FindBy(xpath = "//*[text()='CONTRACT START DATE']")
    public WebElement contractStartDateField;

    @FindBy(xpath = "//*[text()='CONTRACT START DATE']/following-sibling::h6")
    public WebElement contractStartDate;

    @FindBy(xpath = "//*[text()='CONTRACT END DATE']")
    public WebElement contractEndDateField;

    @FindBy(xpath = "//*[text()='CONTRACT END DATE']/following-sibling::h6")
    public WebElement contractEndDate;

    @FindBy(xpath = "//*[text()='ENROLLMENT START DATE']")
    public WebElement enrollmentStartDateField;

    @FindBy(xpath = "//*[text()='ENROLLMENT START DATE']/following-sibling::h6")
    public WebElement enrollmentStartDate;

    @FindBy(xpath = "//*[text()='ENROLLMENT END DATE']")
    public WebElement enrollmentEndDateField;

    @FindBy(xpath = "//*[text()='ENROLLMENT END DATE']/following-sibling::h6")
    public WebElement enrollmentEndDate;

    @FindBy(xpath = "//*[text()='ENROLLMENT CAP']/following-sibling::h6")
    public WebElement enrollmentCap;

    @FindBy(xpath = "//*[text()='ENROLLMENT CAP']")
    public WebElement enrollmentCapField;

    @FindBy(xpath = "//*[text()='ENROLLMENT CAP START DATE']")
    public WebElement enrollmentCapStartDateField;

    @FindBy(xpath = "//*[text()='ENROLLMENT CAP START DATE']/following-sibling::h6")
    public WebElement enrollmentCapStartDate;

    @FindBy(xpath = "//*[text()='ENROLLMENT CAP END DATE']")
    public WebElement enrollmentCapEndDateField;

    @FindBy(xpath = "//*[text()='ENROLLMENT CAP END DATE']/following-sibling::h6")
    public WebElement enrollmentCapEndDate;

    @FindBy(xpath = "//*[text()='PCP REQUIRED']/../span")
    public WebElement pcpRequiredCheckBx;

    @FindBy(xpath = "//*[text()='CONTACT DETAILS']/../..")
    public WebElement contactDetailsScreen;

    @FindBy(xpath = "//*[text()='CONTACT INFORMATION']")
    public WebElement contactInfoContainerHeader;

    @FindBy(xpath = "//*[text()='PROVIDER SERVICES PHONE #3']")
    public WebElement providerPhone3Field;

    @FindBy(xpath = "//div[@class='col-3 col-xxl-2-5 my-2']//label[text()='Provider Services Phone #3']/../div/input")
    public WebElement providerPhone3;

    @FindBy(xpath = "(//*[text()='EDIT']/..)[1]")
    public WebElement cdMailingAddressEditBtn;

    @FindBy(xpath = "(//*[text()='EDIT']/..)[2]")
    public WebElement cdContactInformationEditBtn;

    @FindBy(xpath = "//div[@class='col-3 col-xxl-2-5 my-2']//label[text()='Provider Services Phone #2']/../div/input")
    public WebElement providerPhone2;

    @FindBy(xpath = "//*[text()='PROVIDER SERVICES PHONE #2']")
    public WebElement providerPhone2Field;

    @FindBy(xpath = "//div[@class='col-3 col-xxl-2-5 my-2']//label[text()='Member Services Phone #5']/../div/input")
    public WebElement memberPhone5;

    @FindBy(xpath = "//*[text()='MEMBER SERVICES PHONE #5']")
    public WebElement memberPhone5Field;

    @FindBy(xpath = "//div[@class='col-3 col-xxl-2-5 my-2']//label[text()='Member Services Phone #4']/../div/input")
    public WebElement memberPhone4;

    @FindBy(xpath = "//*[text()='MEMBER SERVICES PHONE #4']")
    public WebElement memberPhone4Field;

    @FindBy(xpath = "//div[@class='col-3 col-xxl-2-5 my-2']//label[text()='Member Services Phone #3']/../div/input")
    public WebElement memberPhone3;

    @FindBy(xpath = "//*[text()='MEMBER SERVICES PHONE #3']")
    public WebElement memberPhone3Field;
    @FindBy(xpath = "//*[text()='MEMBER SERVICES PHONE #3']/following-sibling::h6")
    public WebElement memberPhoneno3;

    @FindBy(xpath = "//*[text()='MEMBER SERVICES PHONE #4']/following-sibling::h6")
    public WebElement memberPhoneno4;

    @FindBy(xpath = "//*[text()='MEMBER SERVICES PHONE #5']/following-sibling::h6")
    public WebElement memberPhoneno5;

    @FindBy(xpath = "//div[@class='col-3 col-xxl-2-5 my-2']//label[text()='Member Services Phone #2']/../div/input")
    public WebElement memberPhone2;

    @FindBy(xpath = "//*[text()='MEMBER SERVICES PHONE #2']")
    public WebElement memberPhone2Field;

    @FindBy(xpath = "//input[@name='fileExchangeLocation']")
    public WebElement fileLocation;

    @FindBy(xpath = "//*[text()='FILE EXCHANGE LOCATION']/following-sibling::h6")
    public WebElement fileLocation1;

    @FindBy(xpath = "//*[text()='FILE EXCHANGE LOCATION']")
    public WebElement fileLocationField;

    @FindBy(xpath = "//input[@name='planContactEmail']")
    public WebElement contactEmail;

    @FindBy(xpath = "//*[text()='PLAN CONTACT EMAIL']")
    public WebElement contactEmailField;

    @FindBy(xpath = "//*[text()='PLAN CONTACT PHONE NUMBER']")
    public WebElement contactPhoneField;

    @FindBy(xpath = "//input[@name='planContactLastName']")
    public WebElement contactLastName;

    @FindBy(xpath = "//*[text()='PLAN CONTACT LAST NAME']")
    public WebElement contactLastNameField;

    @FindBy(xpath = "//input[@name='planContactFirstName']")
    public WebElement contactFirstName;

    @FindBy(xpath = "//*[text()='PLAN CONTACT FIRST NAME']")
    public WebElement contactFirstNameField;

    @FindBy(xpath = "//div[@class='col-3 col-xxl-2-5 my-2']//label[text()='Plan Member Services Website URL']/../div/input")
    public WebElement memberServiceURL;

    @FindBy(xpath = "//*[text()='PLAN MEMBER SERVICES WEBSITE URL']")
    public WebElement memberServiceURLField;

    @FindBy(xpath = "//div[@class='col-3 col-xxl-2-5 my-2']//label[text()='Provider Services Phone #1']/../div/input")
    public WebElement providerPhone1;

    @FindBy(xpath = "//*[text()='PROVIDER SERVICES PHONE #1']/following-sibling::h6")
    public WebElement providerPhoneno1;

    @FindBy(xpath = "//*[text()='PROVIDER SERVICES PHONE #1']")
    public WebElement providerPhone1Field;

    @FindBy(xpath = "(//input[@name='fieldValue'])[1]")
    public WebElement memberPhone1;

    @FindBy(xpath = "//*[text()='MEMBER SERVICES PHONE #1']")
    public WebElement memberPhone1Field;

    @FindBy(xpath = "//*[text()='ADDRESS LINE 1']/following-sibling::h6")
    public WebElement addressLine1;

    @FindBy(xpath = "//*[text()='MEMBER SERVICES PHONE #1']/following-sibling::h6")
    public WebElement memberServicePhone1;

    @FindBy(xpath = "//input[@name='addressLine2']")
    public WebElement addressLine2InputDEUX;

    @FindBy(xpath = "//input[@name='addressLine1']")
    public WebElement addressLine1Input;

    @FindBy(xpath = "//*[text()='ADDRESS LINE 1']")
    public WebElement addressLine1Field;

    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiIconButton-root mx-snackbar-close-btn']")
    public WebElement closeContactDetailsErroMsg;

    @FindBy(xpath = "//*[text()='PLAN INFORMATION']/../button")
    public WebElement planInfoEditBtn;

    @FindBy(xpath = "//*[text()='CONTRACT INFORMATION']/../button")
    public WebElement contractInfoEditBtn;

    @FindBy(xpath = "//*[text()='ENROLLMENT INFORMATION']/../button")
    public WebElement enrollmentInfoEditBtn;

    @FindBy(xpath = "//*[contains(text(), 'SAVE')]")
    public WebElement detailsContainerSaveBtn;

    @FindBy(xpath = "//*[text()='CANCEL']")
    public WebElement detailsContainerCancelBtn;

    @FindBy(xpath = "//label[@class='MuiFormControlLabel-root mx-disabled-label mr-0 Mui-disabled']/span")
    public WebElement planInfoExclusionUpdated;

    @FindBy(xpath = "//*[text()='EXCLUDE PLAN FROM AUTO-ASSIGNMENT']/../span/span/input")
    public WebElement planInfoExclusionCheckBox;

    @FindBy(xpath = "//*[text()='SUCCESS MESSAGE']")
    public WebElement successfulUpdateMsg;

    @FindBy(xpath = "//*[text()='Contract End Date']/../div/input")
    public WebElement contractInfoEndDate;

    @FindBy(xpath = "//*[text()='CONTRACT START DATE']/../h6")
    public WebElement contractInfoStartDate;

    @FindBy(xpath = "//*[text()='Contract end date is not greater than the system date']")
    public WebElement contractEndDateErrorMsg;

    @FindBy(xpath = "//*[text()='PCP REQUIRED']/../span/span/input")
    public WebElement enrollmentInfoPcpCheckBox;

    @FindBy(xpath = "//*[text()='Enrollment Start Date']/../div/input")
    public WebElement enrollmentInfoStartDate;

    @FindBy(xpath = "//*[text()='Enrollment End Date']/../div/input")
    public WebElement enrollmentInfoEndDate;

    @FindBy(xpath = "//*[text()='ENROLLMENT CAP']/../h6")
    public WebElement enrollmentInfoCap;

    @FindBy(xpath = "//*[text()='ENROLLMENT CAP START DATE']/../h6")
    public WebElement enrollmentInfoCapStartDate;

    @FindBy(xpath = "//*[text()='ENROLLMENT CAP END DATE']/../h6")
    public WebElement enrollmentInfoCapEndDate;

    @FindBy(xpath = "//*[text()='The start date cannot be in the past']")
    public WebElement enrollmentInfoStartDateErrorMsg;

    @FindBy(xpath = "//*[text()='Enrollment end date is not greater than the Enrollment start date']")
    public WebElement enrollmentInfoEndDateErrorMsg;

    @FindBy(xpath = "//*[text()='Your changes will not be saved']")
    public WebElement cancelEditWarningMsg;

    @FindBy(xpath = "//*[text()='Continue']/..")
    public WebElement warningMsgContinueBtn;

    @FindBy(xpath = "//*[text()='Cancel']/..")
    public WebElement warningMsgCancelBtn;

    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiFab-root mdl-js-button mdl-button--fab mdl-js-ripple-effect mx-btn-white-tm ml-2']")
    public WebElement pdBackArrow;

    @FindBy(xpath = "//*[text()='If you navigate away, your information will not be saved']")
    public WebElement navigationWarningMsg;

    @FindBy(xpath = "//*[text()='group']")
    public WebElement userListSideBarTab;

    @FindBy(xpath = "//*[contains(text(),'- User List')]")
    public WebElement userListHeader;

    @FindBy(xpath = "//*[text()='The Enrollment end date is not in the correct format']")
    public WebElement invalidFormatErrorMsg;

    //

    @FindBy(xpath = "//label[contains(text(), 'File Exchange Location')]/parent::div/descendant::input")
    public WebElement plancontactlocation;

    @FindBy(xpath = "//label[contains(text(), 'Member Services Phone #1')]/parent::div/descendant::input")


    public WebElement memberservicesphone1;


    @FindBy(xpath = "//label[contains(text(), 'Provider Services Phone #1')]/parent::div/descendant::input")
    public WebElement providerservicesphone1;

    @FindBy(xpath = "//p[contains(text(), \"PROVIDER SERVICES PHONE #2\")]")
    public WebElement providerservicesphoneno2Field;

    @FindBy(xpath = "//p[contains(text(), \"PROVIDER SERVICES PHONE #2\")]/following-sibling::h6")
    public WebElement providerservicesphoneno2;

    @FindBy(xpath = "//p[contains(text(), \"PROVIDER SERVICES PHONE #3\")]")
    public WebElement providerservicesphoneno3Field;

    @FindBy(xpath = "//p[contains(text(), \"PROVIDER SERVICES PHONE #3\")]/following-sibling::h6")
    public WebElement providerservicesphoneno3;

    @FindBy(xpath = "//p[contains(text(), \"PLAN MEMBER SERVICES WEBSITE URL\")]")
    public WebElement planmemberserviceswebsiteURLField;

    @FindBy(xpath = "//p[contains(text(), \"PLAN MEMBER SERVICES WEBSITE URL\")]/following-sibling::h6")
   public WebElement planmemberserviceswebsiteURL1;


    @FindBy(xpath = "//label[contains(text(), 'Plan Member Services Website URL')]/parent::div/descendant::input")
    public WebElement planmemberserviceswebsiteURL;

    @FindBy(xpath = "//label[contains(text(), 'Plan Contact First Name')]/parent::div/descendant::input")
    public WebElement plancontactfirstname;

    @FindBy(xpath = "//p[contains(text(), \"PLAN CONTACT FIRST NAME\")]")
    public WebElement plancontactfirstnameField1;

    @FindBy(xpath = "//p[contains(text(), \"PLAN CONTACT FIRST NAME\")]/following-sibling::h6")
    public WebElement plancontactfirstname1;

    @FindBy(xpath = "//p[contains(text(), \"PLAN CONTACT LAST NAME\")]")
    public WebElement plancontactlastnameField1;

    @FindBy(xpath = "//p[contains(text(), \"PLAN CONTACT LAST NAME\")]/following-sibling::h6")
    public WebElement plancontactlastname1;

    @FindBy(xpath = "//p[contains(text(), \"PLAN CONTACT PHONE NUMBER\")]")
    public WebElement plancontactphonenumberField1;

    @FindBy(xpath = "//p[contains(text(), \"PLAN CONTACT PHONE NUMBER\")]/following-sibling::h6")
    public WebElement plancontactphonenumber1;

    @FindBy(xpath = "//p[contains(text(), \"PLAN CONTACT EMAIL\")]")
    public WebElement plancontactemailField1;

    @FindBy(xpath = "//p[contains(text(), \"PLAN CONTACT EMAIL\")]/following-sibling::h6")
    public WebElement plancontactemail1;

    @FindBy(xpath = "//label[contains(text(), 'Plan Contact Last Name')]/parent::div/descendant::input")
    public WebElement plancontactlastname;

    @FindBy(xpath = "//label[contains(text(), 'Plan Contact Phone Number')]/parent::div/descendant::input")
    public WebElement plancontactphonenumber;


    @FindBy(xpath = "//label[contains(text(), 'Plan Contact Email')]/parent::div/descendant::input")


    public WebElement plancontactemail;




    @FindBy(xpath = "//label[contains(text(), 'Member Services Phone #2')]/parent::div/descendant::input")
    public WebElement memberservicesphone2;

    @FindBy(xpath = "//p[contains(text(), 'MEMBER SERVICES PHONE #2')]/following-sibling::h6")
    public WebElement memberservicesphoneno2;



    @FindBy(xpath = "//label[contains(text(), 'Member Services Phone #3')]/parent::div/descendant::input")
    public WebElement memberservicesphone3;
    @FindBy(xpath = "//label[contains(text(), 'Member Services Phone #4')]/parent::div/descendant::input")
    public WebElement memberservicesphone4;
    @FindBy(xpath = "//label[contains(text(), 'Member Services Phone #5')]/parent::div/descendant::input")
    public WebElement memberservicesphone5;


    @FindBy(xpath = "//label[contains(text(), 'Provider Services Phone #2')]/parent::div/descendant::input")
    public WebElement providerservicesphone2;
    

    @FindBy(xpath = "//label[contains(text(), 'Provider Services Phone #3')]/parent::div/descendant::input")
    public WebElement providerservicesphone3;

    @FindBy(xpath = "//a[contains(text(), 'Holidays')]")
    public WebElement navigationlink;


    @FindBy(xpath = "//h5[contains(text(), 'HOLIDAY')]")
    public WebElement holiday;

    @FindBy(xpath="//p[contains(text(),'PROVIDER SERVICES PHONE #1')]")
    public WebElement viewProviderSerPhone1;

    @FindBy(xpath="//p[contains(text(),'PROVIDER SERVICES PHONE #2')]")
    public WebElement viewProviderSerPhone2;

    @FindBy(xpath="//p[contains(text(),'PROVIDER SERVICES PHONE #3')]")
    public WebElement viewProviderSerPhone3;


    @FindBy(xpath="//p[contains(text(),'MEMBER SERVICES PHONE #2')]")
    public WebElement viewMemeberSerPhone2;

    @FindBy(xpath="//p[contains(text(),'MEMBER SERVICES PHONE #3')]")
    public WebElement viewMemeberSerPhone3;

    @FindBy(xpath="//p[contains(text(),'MEMBER SERVICES PHONE #4')]")
    public WebElement viewMemeberSerPhone4;

    @FindBy(xpath="//p[contains(text(),'MEMBER SERVICES PHONE #5')]")
    public WebElement viewMemeberSerPhone5;



}
