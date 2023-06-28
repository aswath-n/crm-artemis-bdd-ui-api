package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMCaseAndConsumerSearchPage {

    
    public CRMCaseAndConsumerSearchPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(id = "consumerFirstName")
    public WebElement consumerFirstName;

    @FindBy(xpath = "(//span[text()='search'])[2]")
    public WebElement searchBtn;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class,'mdl-data-table mdl-js-data-table mx-table table mt-4 mb-0 mdl-color--grey-50')]/tbody/tr/td[3]")
    })
    public List<WebElement> contactIDs;

    @FindBy(xpath = "//a[text()='Demographic Info']")
    public WebElement DemographicInfoTab;

    @FindBy(xpath = "//input[@id='preferredLanguage']/..")
    public WebElement preferredLanguageDropdown;

    @FindBy(xpath = "//a[text()='PROFILE CONTACT']")
    public WebElement caseConsumerSearchEditPageHeader;

    @FindBy(xpath = "//span[text()=' EDIT']/parent::*")
    public WebElement profileContactEditButton;

    @FindBy(id = "consumerLastName")
    public WebElement consumerLastName;

    @FindBy(id = "consumerMiddleName")
    public WebElement consumerMiddleName;

    @FindBy(id = "consumerSSN")
    public WebElement consumerSsn;

    @FindBy(id = "seaConDOB")
    public WebElement consumerDob;

//    @FindBy(id = "seacrmCaseID")
//    public WebElement internalCaseId;

//    @FindBy(id = "seacrmConsumerId")
//    public WebElement internalConsumerId;

    @FindBy(xpath = "//span[text()='Cancel']")
    public WebElement cancelBtn;

    @FindBy(xpath = "//div[@class='col-12 my-3']/button[2]")   //(is there a correct class for this?)
    public WebElement resetBtn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]")
    public WebElement searchResultHeader;

    @FindBy(xpath = "(//span[@class='material-icons MuiIcon-root mx-leftnav-icon'])[1]")
    public WebElement activeCallSideBar;

    @FindBy(xpath = "(//span[@class='material-icons MuiIcon-root mx-leftnav-icon'])[6]")
    public WebElement providerSearchSideBar;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody/tr/td[2]/p/b")
    public WebElement caseIdEnabled;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody/tr/td[2]/p")
    public WebElement caseIdDisbled;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody/tr/td[3]")
    public WebElement consumerIdEnabled;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody/tr/td[3]/p")
    public WebElement consumerIdDisabled;

    @FindBy(xpath = "//*[contains(text(), 'keyboard_backspace')]")
    public WebElement backButton;

    @FindBy(xpath = "//*[contains(text(), 'CONSUMER NAME')]/following-sibling::*")
    public WebElement consumerName;

    //CP-670
    @FindBy(xpath = "//*[contains(@class,'MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mx-link')]/p/b")
    public List<WebElement> searchCaseConsumerID;

    @FindBy(xpath = "//span[text()='add']")
    public List<WebElement> addButton;

    @FindBy(xpath="//*[text()='PRIMARY INDIVIDUAL']")
    public WebElement primaryIndividualLabel;

    @FindBy(xpath = "//input[@id='preferred_lang']/..")
    public WebElement UpdatePreferredLanguage;

    @FindBy(xpath = "//span[text()='add']")
    public WebElement addbutton;

    @FindBy(xpath = "(//*[text()='chevron_right'])[1]")
    public WebElement expandFistConsumer;

    //used class since no other locators
    @FindBy(xpath = "//h5[text()='CONSUMER AUTHENTICATION']/../..//following-sibling::div[not(contains(@class,'clearfix'))]//table//tr/th")
    public List<WebElement> authenticationTableColumn;


    @FindBy(xpath = "//span//input[@type='checkbox']/ancestor::div[1]/label")
    public List<WebElement> authenticationTableColumn1;

    @FindBy(xpath = "//input[@name='0_caConsumerFullName']")
    public WebElement firstConsumerNameRadioButton;

    @FindBy(xpath = "//tbody/tr[1]//input[@type='checkbox']")
    public List<WebElement> firstRecordCheckboxCount;

    @FindBy(xpath = "//p[contains(text(),'CONSUMER AUTHENTICATED')]")
    public WebElement consumerAuthenticateButton;

    @FindBy(xpath = "//tbody/tr[1]//input[@type='radio']")
    public WebElement consumerRadioButton;

    @FindBy(xpath = "//span[contains(text(),'LINK RECORD')]")
    public WebElement linkRecordButton;

    @FindBy(xpath = "//input[@name='0_caConsumerFullName']")
    public WebElement consumerRadio_Button;

    @FindBy(xpath = "//table//tr/th[text()='PHONE NUMBER']")
    public WebElement phField;

    @FindBy(xpath = "(//p[@class='mx-overflow-text mb-0'])[2]")
    public List<WebElement> consumerIDSearch;

// Umid adding input fields

    @FindBy(xpath = "//input[@id='caseId']")
    public WebElement caseIdField;

    @FindBy(xpath = "//input[@id='consumerId']")
    public WebElement consumerIdField;

    @FindBy(xpath = "//tbody//tr//td[2]")
    public WebElement crmCaseIdValue;

    @FindBy(xpath = "//tbody//tr//td[3]")
    public WebElement crmConsumerIdValue;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[3]")
    public WebElement frstPrimaryCon;

    @FindBy(xpath = "//div/table/tbody/tr/td[2]/p")
    public WebElement stateCaseIDValue;

    @FindBy(xpath = "//div[2]/div[1]/div/a[2]")
    public WebElement stateCaseIDValueWidged;

    @FindBy(xpath = "//*[@class=\"row mx-layout-margin-left pt-4  mt-4\"]/div/div[1]/div[1]/p")
    public WebElement alertBannerText;

    @FindBy(xpath = "//*[@class=\"row mx-layout-margin-left pt-4 \"]/div[1]/div/div[1]/p")
    public WebElement alertBannerTxtViewTask;

    @FindBy(xpath = "//*[@class='font-weight-bold']")
    public List<WebElement> alertBannerConsumerName;

    @FindBy(xpath = "//span[text()='VIEW ALL ALERTS']")
    public WebElement viewAllAlertsBtn;

    @FindBy(xpath = "//i[text()='clear']")
    public WebElement closeXBtn;

    @FindBy(xpath = "//h5[text()='ALERTS']")
    public WebElement AlertsText;

    @FindBy(xpath = "//*[@class=\"col-12 mx-alert-card-active mx-2 mt-4\"]/p[3]")
    public WebElement ActiveAlertMsg;

    @FindBy(xpath = "//*[@class=\"col-12 mx-alert-card-future mx-2 mt-4\"]/p[3]")
    public WebElement FutureAlertMsg;

    @FindBy(xpath = "//*[@class=\"col-12 mx-alert-card-past mx-2 mt-4\"]/p[3]")
    public WebElement PastAlertMsg;

    @FindBy(xpath = "//h5[text()='CREATE ALERT']")
    public WebElement createAlertTxt;

    @FindBy(id = "alertText")
    public WebElement alertText;

    @FindBy(id = "mui-component-select-type")
    public WebElement alertType;

    @FindBy(xpath = "//p[text()='ALERT TEXT  is required and cannot be left blank']")
    public WebElement alertTxtRequiredErrorMsg;

    @FindBy(xpath = "//p[text()='TYPE  is required and cannot be left blank']")
    public WebElement alertTypeRequiredErrorMsg;

    @FindBy(xpath = "//p[text()='START DATE is required and cannot be left blank']")
    public WebElement alertDateRequiredErrorMsg;

    @FindAll({
    @FindBy(css = "tr:nth-child(2) > td.MuiTableCell-root.MuiTableCell-body.mdl-data-table__cell--non-numeric.mx-link")
    })
            public List<WebElement> caseIdLinks;

    @FindBy(xpath = "//button[@ id= 'basic-button']")
    public WebElement createAlertLinkBnt;

    @FindBy(xpath = "//span[text() = 'CASE/ CONSUMER']")
    public WebElement caseConsumerBnt;

    @FindBy(xpath = "(//span[text() = 'search'])[2]")
    public WebElement searchBtnOnManualCreateAlertPage;

    @FindAll({
            @FindBy(xpath = "//input[@name='p']")
    })
    public List<WebElement> listOfCheckBoxFromExpandSearchResult;

    @FindBy(xpath = "(//*[text() = 'calendar_today'])[1]")
    public WebElement calendarIconAlertSlider;

    @FindBy(xpath = "(//h6[@class='m-0  float-left'])[1]")
    public WebElement toolTipDateOnAlertSlider;

    @FindBy(xpath = "//*[contains(@id,'menu-list-button-')]")
    public WebElement alertHamburger;

    @FindBy(xpath = "//li[text()='IN-ACTIVATE']")
    public WebElement inactivateTxt;

    @FindBy(xpath = "//li[text()='ACTIVATE']")
    public WebElement activateTxt;

    @FindBy(xpath = "//*[contains(@class,'material-icons mdl-color-text--grey-')]")
    public WebElement greyBorderAlert;

    @FindBy(xpath = "//*[contains(@class,'material-icons mdl-color-text--red')]")
    public WebElement redBorderAlert;

    @FindBy(xpath = "//*[contains(@class,'material-icons mdl-color-text--orange')]")
    public WebElement orangeBorderAlert;

    @FindBy(xpath = "//li[text()='EDIT']")
    public WebElement editTxt;

    @FindBy(xpath = "//h5[text()='ALERT DETAILS']")
    public WebElement alertDetails;

    @FindBy(xpath = "//*[@class=\"MuiTableBody-root\"]/tr[1]/td[2]/p")
    public WebElement firstAlertID;

    @FindBy(xpath = "//*[text()='IN-ACTIVATE']")
    public WebElement inactivateAlert;

    @FindBy(xpath = "//i[text()='link_off']")
    public WebElement hiddenUnlinkBtn;

    @FindBy(xpath = "//span[text()='You must link a Case or Consumer to save your Alert.']")
    public WebElement linkAlertErrorMsg;

    @FindBy(id = "consumerExternalId")
    public WebElement externalID;

    @FindBy(id = "consumerId")
    public WebElement consumerID;

    @FindBy(xpath = "//span[text()='SEARCH']")
    public WebElement searchButton;

    @FindBy(id = "name")
    public WebElement Name;

    @FindBy(id = "dateOfBirth")
    public WebElement ConsumerDOB;

    @FindBy(id = "consumerAddress")
    public WebElement consumerAddress;

    @FindBy(id = "consumerCityZIP")
    public WebElement consumerCityZip;

    @FindBy(id = "consumerPhoneNumber")
    public WebElement consumerPhoneNumber;

    @FindBy(id = "consumerEmail")
    public WebElement consumerEmail;

    @FindBy(xpath = "//*[@class=\"MuiTableBody-root\"]/tr[1]/td[2]/p")
    public WebElement StateIDResult;

    @FindBy(xpath = "//*[@class=\"MuiTableBody-root\"]/tr[1]/td[4]/p")
    public WebElement NameResult;

    @FindBy(xpath = "//*[@class=\"MuiTableBody-root\"]/tr[1]/td[7]/p")
    public WebElement SNNResult;

    @FindBy(xpath = "//*[@class=\"MuiTableBody-root\"]/tr[1]/td[5]/p")
    public WebElement DOBResult;

    @FindBy(xpath = "//*[@class=\"MuiTableBody-root\"]/tr[1]/td[8]/p")
    public WebElement AddressResult;

    @FindBy(xpath = "//*[@class=\"MuiTableBody-root\"]/tr[1]/td[9]/p")
    public WebElement PhoneResult;

    @FindBy(xpath = "//*[@class=\"MuiTableBody-root\"]/tr[1]/td[10]/p")
    public WebElement EmailResult;

    @FindBy(xpath = "//*[@class=\"MuiTableBody-root\"]/tr[1]/td[3]/p")
    public WebElement CaseIdResult;

    @FindBy(xpath = "//p[text()='SSN must be 9 characters']")
    public WebElement invalidSnnError;

    @FindBy(xpath = "//p[text()='Invalid date format']")
    public WebElement invalidDOBError;

    @FindBy(xpath = "//p[text()='DELIVERY ADDRESS must contain both numeric and alphabetic characters to be valid']")
    public WebElement invalidAddressError;

    @FindBy(xpath = "//p[text()='city must be less than 30 character']")
    public WebElement invalidCityError;

    @FindBy(xpath = "//p[text()='ZIP must be 5 character']")
    public WebElement invalidZipError;

    @FindBy(xpath = "//p[text()='PHONE must be 10 characters']")
    public WebElement invalidPhoneError;

    @FindBy(xpath = "//p[text()='EMAIL ADDRESS is not in the correct format']")
    public WebElement invalidEmailError;

    @FindAll({
            @FindBy(xpath = "//span[contains(text(),'visibility')]")
    })
    public List<WebElement> visibilityOff;

    @FindBy(id = "mui-component-select-searchBy")
    public WebElement searchByDropdown;

    @FindBy(xpath = "//li[text()='Internal Consumer ID']")
    public WebElement intConsumerID;

    @FindBy(xpath = "//li[text()='Medicaid Case ID']")
    public WebElement medCaseID;

    @FindBy(xpath = "//li[text()='Internal Case ID']")
    public WebElement intCaseID;

    @FindBy(xpath = "//span[@title='DOB Visiblity Off']")
    public WebElement DOBMaskBtnSearchParameter;

    @FindBy(xpath = "//span[@title='SSN Visiblity Off']")
    public WebElement SSNMaskBtnSearchParameter;

    @FindBy(xpath = "//p[text()='**/**/****']")
    public WebElement DOBMaskedResult;

    @FindBy(xpath = "//p[text()='***-**-****']")
    public WebElement SSNMaskedResult;

    @FindBy(xpath = "//span[text()='STATE ID']")
    public WebElement stateIDheader;

    @FindBy(xpath = "//th/span[text()='NAME']")
    public WebElement nameHeader;

    @FindBy(xpath = "//th/span[text()='DOB']")
    public WebElement dobHeader;

    @FindBy(xpath = "//th/span[text()='SOURCE']")
    public WebElement sourceHeader;

    @FindBy(xpath = "//th/span[text()='SSN']")
    public WebElement ssnHeader;

    @FindBy(xpath = "//th/span[text()='ADDRESS']")
    public WebElement addressHeader;

    @FindBy(xpath = "//th/span[text()='PHONE NUMBER']")
    public WebElement phoneNumHeader;

    @FindBy(xpath = "//th/span[text()='EMAIL']")
    public WebElement emailHeader;

    @FindBy(xpath = "(//p[@class='mx-overflow-text mb-0'])[1]")
    public List<WebElement> caseIDSearchResult;
}