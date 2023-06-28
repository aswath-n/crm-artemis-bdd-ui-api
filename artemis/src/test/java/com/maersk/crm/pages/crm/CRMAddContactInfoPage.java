package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMAddContactInfoPage  {

    

    public CRMAddContactInfoPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//*[contains(text(), 'keyboard_backspace')]")
    public WebElement backToContactInfo;

    @FindBy(xpath = "//div[contains(@class,'MuiTypography-root')]//div[contains(@class,'col-')][3]//table//tbody//tr[1]//i[text()='star']")
    public WebElement chkFirstEmailWithStar;

    @FindBy(xpath = "//*[@id = 'mui-component-select-consumer']/..")
    public WebElement addressConsumer;

    @FindBy(css = ".MuiTableCell-body.mdl-data-table__cell--non-numeric.mx-link.mx-disable-link-text.MuiTableCell-sizeSmall")
    public WebElement disabledAddress;

    @FindBy(id = "addressStreet1")
    public WebElement addressLineOne;

    @FindBy(id = "addressStreet2")
    public WebElement addressLineTwo;

    @FindBy(xpath = "//*[@name='addressCity']")
    public WebElement city;

    @FindBy(xpath = "//*[@name='addressCounty']")
    public WebElement county;

    @FindBy(xpath = "//*[@name='addressState']//..")
    public WebElement state;

    @FindBy(xpath = "//*[@name='addressState']//.")
    public WebElement stateDropdown;

    @FindBy(xpath = "//*[@id='addressZip']")
    public WebElement zip;

    @FindBy(xpath = "//*[@name='addressType']//..")
    public WebElement addressType;

    @FindBy(xpath = "//*[@name='addressType']//.")
    public WebElement addressTypeDropdown;

    @FindBy(id = "effectiveStartDate")
    public WebElement startDate;

    @FindBy(id = "effectiveEndDate")
    public WebElement endDate;

    //    @FindBy(xpath = "//*[contains(@class, 'mx-btn mx-btn-border mx-btn-primary mdl-shadow--2dp mr-2')]")
    @FindBy(xpath = "//span[contains(text(),'check')]")
    public WebElement saveButton;

    @FindBy(xpath = "//*[@id=\"root\"]//span[contains(@class,'MuiButtonBase-root')]")
    public WebElement primaryCasePhoneOrEmailCheckBox;

    @FindBy(id = "primaryIndicator")
    public WebElement primaryCaseEnabledCheckBox;

    @FindBy(xpath = "//button[contains(@class, 'mx-btn-cancel')]")
    public WebElement cancelButton;

    @FindBy(xpath = "//div[contains(@class,'modal-dialog')]//button[1]")
    public WebElement popupAlertContinueButton;

    @FindBy(xpath = "//div[contains(@class,'modal-dialog')]//button[1]")
    public List<WebElement> popupAlertContinueButton1;

    @FindBy(xpath = "//div[contains(@class,'modal-dialog')]//button[2]")
    public WebElement popupAlertCancelButton;

    @FindBy(xpath = "//*[contains(h1,'EMAIL')]")
    public WebElement emailAddressLabel;

    //@FindBy(xpath = "//label[contains(text(), 'Email Address')]/..")
    @FindBy(xpath = "//*[@name='emailAddress']")
    //@FindBy(name="emailAddress")
    //@FindBy(xpath ="//*[@id='DAEAddress']/..")
    public WebElement emailAddressField;

    @FindBy(xpath = "//*[@id=\"menu-consumer\"]/div[3]/ul/li[1]")
    public WebElement emailFirstConsumer;

    @FindBy(xpath = "//*[@name='consumer']/..")
    public WebElement associatedCaseMember;

    //    @FindBy(xpath = "//*[@id='formatted-phone-input']")
    @FindBy(xpath = "//*[@id='phoneNumber']")
    public WebElement phoneNumber;

    @FindAll({
            @FindBy(xpath = "(//th[contains(text(),'PHONE NUMBER')])[1]/parent::tr/parent::thead/parent::table/tbody/tr")
    })
    public List<WebElement> listOfPhoneNumbers;

    @FindBy(xpath = "//*[@name='phoneType']/..")
    public WebElement phoneType;

    @FindBy(xpath = "//*[@name='consumer']/..")
    public WebElement consumer;

    @FindBy(xpath = "//*[@name='comments']")
    public WebElement phoneComments;

    @FindBy(xpath = "//ul[@role='listbox']/li[last()]")
    public WebElement dropdownLastItem;

    @FindBy(xpath = "//*[contains(text(),'EMAIL is not in the correct format')]")
    public WebElement invalidEmailAddressError;

    public By paginationPhoneNumber = By.xpath("//div[contains(@class,'MuiTypography-root')]//div[contains(@class,'col-')][2]//ul/li/a");

    public By paginationEmailAddress = By.xpath("//div[contains(@class,'MuiTypography-root')]//div[contains(@class,'col-')][3]//ul/li/a");

    public By paginationAddress = By.xpath("//div[contains(@class,'MuiTypography-root')]//div[contains(@class,'col-') and contains(@class,'mt-')][1]//ul/li/a");

    public By paginationGetAddresses = By.xpath("//div[contains(@class,'MuiTypography-root')]//div[contains(@class,'col-') and contains(@class,'mt-')][1]//table//tr//td[contains(@class,'mx-link')]");

    @FindBy(xpath = "//div[contains(@class,'MuiTypography-root')]//div[contains(@class,'col-') and contains(@class,'mt-')][1]//table//td[contains(@class,'-status')]")
    public List<WebElement> addressStatuses;

    @FindBy(xpath = "//div[contains(@class,'MuiTypography-root')]//div[contains(@class,'col-') and contains(@class,'mt-')][2]//table//th[text()='LAST UPDATED']")
    public WebElement phoneNumLastUpdatedLabel;

    @FindBy(xpath = "//div[contains(@class,'MuiTypography-root')]//div[contains(@class,'col-') and contains(@class,'mt-')][2]//table//th[text()='CONSUMER']")
    public WebElement phoneNumConsumerLabel;

    @FindBy(xpath = "//div[contains(@class,'MuiTypography-root')]//div[contains(@class,'col-') and contains(@class,'mt-')][3]//table//th[text()='CONSUMER']")
    public WebElement emailConsumerLabel;

    @FindBy(xpath = "//div[contains(@class,'MuiTypography-root')]//div[contains(@class,'col-') and contains(@class,'mt-')][2]//table//i[text()='star']")
    public WebElement checkPrimaryPhoneNum;

    @FindBy(xpath = "//div[contains(@class,'MuiTypography-root')]//div[contains(@class,'col-')][2]//table//td[contains(@class,'-status')]")
    public List<WebElement> phoneNumberStatus;

    @FindBy(xpath = "//div[contains(@class,'MuiTypography-root')]//div[contains(@class,'col-')][3]//table//td[contains(@class,'-status')]")
    public List<WebElement> emailAddressStatus;

    @FindBy(xpath = "(//table[starts-with(@class,'jss')])[3]/tr/td[2]")
    public WebElement emailActiveStatus;

    @FindBy(xpath = "//th[text()='EMAIL ADDRESS']")
    public WebElement emailAddressHeader;

    @FindBy(xpath = "(//th[contains(text(),'EMAIL ADDRESS')]/parent::tr/parent::thead/parent::table/tbody/tr)[2]/td[1]")
    public WebElement firstEmailID;

    @FindBy(xpath = "(//th[contains(text(),'EMAIL ADDRESS')]/parent::tr/parent::thead/parent::table/tbody/tr)[2]/td[3]")
    public WebElement firstEmailIDStatus;

    @FindBy(id = "inactiveLabel")
    public WebElement inactiveImmediatelyCheckbox;

    @FindBy(xpath = "//label[text()='REASON FOR INACTIVATION']")
    public WebElement inactiveImmediatelyOption;

    @FindBy(xpath = "(//ul[@class='pagination'])[3]/li/a")
    public WebElement pagesOnEmailSection;

    @FindBy(xpath = "//*[contains(@class,'mdl-data-table__cell--non-numeric')]")
    public WebElement addressStatus;

    @FindAll({
            @FindBy(xpath = "((//table[@class='MuiTable-root mdl-data-table mdl-js-data-table mx-table table mt-4 mb-0 mdl-color--grey-50'])[1]//tr[*]/td[2])[1]/following-sibling::td[contains(@class,'status')]")
    })
    public List<WebElement> statusOfAddrss;


    @FindBy(xpath = "(//*[contains(@class,'mdl-data-table__cell--non-numeric')])[1]")
    public WebElement statusOfAddress;

    @FindBy(xpath = "//p[text()='ADDRESS LINE 1 is required and cannot be left blank']")
    public WebElement enterAddressOneError;

    @FindBy(xpath = "//p[text()='CITY is required and cannot be left blank']")
    public WebElement enterCityError;

    @FindBy(xpath = "//p[text()='COUNTY is required and cannot be left blank']")
    public WebElement enterCountyError;

    @FindBy(xpath = "//p[text()='STATE is required and cannot be left blank']")
    public WebElement enterStateError;

    @FindBy(xpath = "//p[text()='ZIP CODE is required and cannot be left blank']")
    public WebElement zipMandatoryError;

    @FindBy(id = "addressZip-helper-text")
    public WebElement zipError;

    @FindBy(xpath = "//p[text()='TYPE is required and cannot be left blank']")
    public WebElement selectAddressTypeError;

    @FindBy(xpath = "//p[text()='START DATE is required and cannot be left blank']")
    public WebElement enterStartDateError;

    @FindBy(xpath = "(//p[text()='Invalid date format'])[1]")
    public WebElement startDateFormatError;

    @FindBy(xpath = "(//p[text()='Invalid date format'])")
    public WebElement endDateFormatError;

    @FindBy(xpath = "//*[contains (text(), 'Please fill in the required fields.')]")
    public WebElement mainFillFieldMessage;

    @FindBy(xpath = "//h5[contains(text(),'PHONE NUMBER')]")
    public WebElement phoneNumberLabel;

    @FindBy(xpath = "//label[text()='START DATE']")
    public WebElement startDateLabel;

    @FindBy(xpath = "//label[text()='ADDRESS LINE 1']")
    public WebElement addressLineOneLabel;

    @FindBy(xpath = "//label[text()='CITY']")
    public WebElement cityLabel;

    @FindBy(xpath = "//label[text()='COUNTY']")
    public WebElement countyLabel;

    @FindBy(xpath = "//label[text()='STATE']")
    public WebElement stateLabel;

    @FindBy(xpath = "//label[text()='ZIP CODE']")
    public WebElement zipLabel;

    @FindBy(xpath = "//label[text()='TYPE']")
    public WebElement addressTypeLabel;

    @FindBy(xpath = "//label[text()='PHONE']")
    public WebElement phoneNumberFieldLabel;

    @FindBy(xpath = "//label[text()='TYPE']")
    public WebElement phoneTypeLabel;

    @FindBy(id = "addressStreet1")
    public WebElement addressLine1Input;

    @FindBy(id = "addressStreet2")
    public WebElement addressLine2Input;

    @FindBy(id = "addressCity")
    public WebElement addressCityInput;

    @FindBy(id = "addressCounty")
    public WebElement addressCountyInput;

    @FindBy(id = "mui-component-select-addressState")
    public WebElement addressStateDropdownButton;

    @FindBy(xpath = "//ul[@class='MuiList-root MuiMenu-list MuiList-padding']")
    public WebElement addressStateDropdownList;

    public WebElement getAddressStateSelectionBy(String stateName) {
        return addressStateDropdownList.findElement(By.xpath("//li[.='" + stateName + "']"));
    }

    @FindBy(id = "addressState")
    public WebElement addressStateInput;

    @FindBy(id = "addressZip")
    public WebElement addressZipInput;

    @FindBy(id = "addressType")
    public WebElement addressTypeInput;

    @FindBy(id = "effectiveStartDate")
    public WebElement effectiveStartDateInput;

    @FindBy(xpath = "//p[text()='PHONE must be 10 characters']")
    public WebElement phoneNumberErrorMessage;

    @FindBy(xpath = "//p[text()='ADDRESS LINE 1 must contain both numeric and alphabetic characters to be valid']")
    public WebElement addressLineOneFieldError;

    @FindBy(xpath = "//*[text()='ADDRESS LINE 1 must contain both numeric and alphabetic characters to be valid']")
    public WebElement addressLineTwoFieldError;

    @FindBy(xpath = "//p[text()='TYPE is required and cannot be left blank']")
    public WebElement selectPhoneTypeError;

    @FindBy(xpath = "//p[text()='PHONE is required and cannot be left blank']")
    public WebElement enterPhoneError;

    @FindBy(xpath = "//*[@id='assoc_case']/..")
    public WebElement consumerDropDown;

    public void getConsumerDropdownOption(int i) {
        associatedCaseMember.click();
        WebElement el = Driver.getDriver().findElement(By.xpath("//*[@id='menu-associatedCaseMember']//ul/li[" + i + "]"));
        new BrowserUtils().jsClick(el);
    }

    @FindBy(xpath = "//p[text()='ASSOCIATED CASE MEMBER is required and cannot be left blank']")
    public WebElement selectConsumerError;

    @FindBy(xpath = "//*[@name='inactivationReason']/..")
    public WebElement inactivateReasonType;

    @FindBy(xpath = "//*[@name='AddInactivationReasonType']")
    public WebElement errInactivateReasonType;

    @FindAll({
            @FindBy(xpath = "(//th[contains(text(),'FULL ADDRESS')])[1]/parent::tr/parent::thead/parent::table/tbody/tr")
    })
    public List<WebElement> listOfAddress;

    @FindBy(xpath = "//p[(text()='REASON FOR INACTIVATION is required and cannot be left blank')]")
    public WebElement reasonTypeError;

    @FindBy(xpath = "//span[contains(text(),'room')]")
    public WebElement clickOutside;

    @FindBy(xpath = "(//th[contains(text(),'PHONE NUMBER')])[1]/ancestor::table/tbody/tr[1]")
    public List<WebElement> phoneNumberRecordRows;

    public void clearEditedAddressMandatoryFields() {
        CRMUtilities crmUtilities = new CRMUtilities();
        city.click();
        crmUtilities.clearFiled(city);
        crmUtilities.clearFiled(county);
//        clearDropDown(state);
        crmUtilities.waitFor(2);
        crmUtilities.clearFiled(addressLineOne);
        for(int i=0; i<9; i++){
            crmUtilities.clearFiled(zip);}
//        clearDropDown(addressType);
        crmUtilities.waitFor(2);
        crmUtilities.clearFiled(startDate);
    }


    public boolean isPopupAlertContinueButtonExist() {
        boolean continueButtonExist = false;
        try {
            continueButtonExist = popupAlertContinueButton.isDisplayed();
            return continueButtonExist;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return continueButtonExist;
        }
    }

    @FindBy(xpath = "//li[@class='MuiButtonBase-root MuiListItem-root MuiMenuItem-root MuiMenuItem-gutters MuiListItem-gutters MuiListItem-button']/.")
    public WebElement caseMemberName;


    @FindAll({
            @FindBy(xpath = "//*[@id=\"contact_info\"]/div[3]/div[2]/div/table/tbody/tr[1]/td[2]")
    })
    public List<WebElement> emailAddressList;

    @FindBy(xpath = "(//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mx-link  MuiTableCell-sizeSmall'])[1]")
    public WebElement contactInfoAddressFirstRecord;

    @FindBy(xpath = "(//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mx-link  MuiTableCell-sizeSmall'])[2]")
    public WebElement contactInfoPhoneFirstRecord;

    @FindBy(xpath = "(//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mx-link  MuiTableCell-sizeSmall'])[3]")
    public WebElement contactInfoEmailFirstRecord;

    @FindBy(xpath = "(//thead)[2]//th[7]")
    public WebElement emailStatusHeader; // use when no address listed

    @FindBy(xpath = "(//table)[2]//td[2]")
    public WebElement firstEmailRecord; // use when no address listed

    @FindBy(xpath = "(//ul[@class='pagination'])[2]//a")
    public List<WebElement> emailPagination; // use when no address listed

    @FindBy(xpath = "(//*[@class='pagination'])[1]/li")
    public List<WebElement> addressPagination;

    @FindBy(xpath = "(//table)[2]//tbody")
    public List<WebElement> emailRecordDetails; // use when no address listed

    @FindBy(xpath = "(//table)[2]/tbody/tr/td[4]")
    public List<WebElement> emailRecordStatuses; // use when no address listed

    @FindBy(xpath = "//span[text() = 'The start Date of the Future Address cannot be earlier than the End Date of the Active Address.']")
    public WebElement errorMessageAddressCannot;

    public WebElement selectField(String field) {
        CRMUtilities crmUtilities = new CRMUtilities();
        crmUtilities.waitFor(1);
        return Driver.getDriver().findElement(By.xpath("//label[contains(text(),'" + field + "')]"));
    }

    public WebElement mandatoryFields(String field) {
        CRMUtilities crmUtilities = new CRMUtilities();
        crmUtilities.waitFor(1);
        return Driver.getDriver().findElement(By.xpath("//p[contains(text(),'" + field + " is required and cannot be left blank')]"));
    }

    @FindBy(xpath = "//*[text() = 'Are you sure you want to update the Primary Phone for this Case? This action will update the existing Primary Phone for the Case to no longer be the Primary']")
    public WebElement warningMessage;

    @FindBy(xpath = "(//*[@title='Primary Case Phone Number']/parent::td/following-sibling::td)[1]/span")
    public WebElement primaryCasePhoneNumber;

    @FindBy(xpath = "//*[text() = 'Are you sure you want to update the Primary Email for this Case? This action will update the existing Primary Email for the Case to no longer be the Primary']")
    public WebElement warningMessageEmail;

    @FindBy(xpath = "(//*[@title='Primary Case Email Address']/parent::td/following-sibling::td)[1]")
    public WebElement primaryCaseEmail;

    @FindBy(xpath = "//*[text() = 'Physical']")
    public List<WebElement> physicalAddressFromAddressTable;

    @FindBy(xpath = "//*[text() = 'Mailing']")
    public List<WebElement> mailingAddressFromAddressTable;

    @FindBy(xpath = "//span[text() = 'Address type is already active for selected consumer']")
    public WebElement errorMessageAlreadyExists;

    @FindBy(xpath = "//*[@id='client-snackbar']")
    public WebElement sameTypeErrorMsg;

    @FindBy(xpath = "//*[@id='mui-component-select-emailSource']")
    public WebElement emailSourceDropDown;

    @FindBy(xpath = "//*[@id='client-snackbar']")
    public List <WebElement> listOfErrorPopUpMessagesONAddAddressPage;

    @FindBy(xpath = "//*[@name='addressCity']/..")
    public WebElement cityINEB;

    @FindBy(xpath = "//span[contains(text(), 'INACTIVATE IMMEDIATELY')]")
    public WebElement addressINACTIVATEIMMEDIATELY;



}

