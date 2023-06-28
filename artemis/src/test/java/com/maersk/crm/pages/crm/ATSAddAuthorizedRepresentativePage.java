package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ATSAddAuthorizedRepresentativePage {

    

    public ATSAddAuthorizedRepresentativePage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(id = "consumerFirstName")
    public WebElement arFirstName;

    @FindBy(id = "consumerMiddleName")
    public WebElement arMiddleName;

    @FindBy(id = "consumerLastName")
    public WebElement arLastName;

    @FindBy(xpath = "(//div[@class='MuiSelect-root MuiSelect-select MuiSelect-selectMenu MuiInputBase-input MuiInput-input'])[2]")
    public WebElement arAccessType;

    @FindBy(xpath = "(//div[@class='MuiSelect-root MuiSelect-select MuiSelect-selectMenu MuiInputBase-input MuiInput-input'])[3]")
    public WebElement correspondenceType;

    @FindBy(xpath = "//span[contains(text(),'check')]")
    public WebElement arSaveButton;

    @FindBy(id = "startDate")
    public WebElement startDate;

    @FindBy(id = "endDate")
    public WebElement endDate;

    @FindBy(id = "authorizationSignatureDate")
    public WebElement authorizationSignatureDate;


    @FindBy(id = "mui-component-select-authType")
    public WebElement authType;

    @FindBy(xpath = "//p[contains(text(),'FIRST NAME is required and cannot be left blank')]")
    public WebElement firstNameRequiredMsg;

    @FindBy(xpath = "//p[contains(text(),'LAST NAME is required and cannot be left blank')]")
    public WebElement lastNameRequiredMsg;

    @FindBy(id = "organizationName")
    public WebElement arOrganizationName;

    @FindBy(id = "idNumber")
    public WebElement arIdNumber;

    @FindBy(id = "authorizedAddressStreet1")
    public WebElement arAddressLine1;

    @FindBy(id = "authorizedAddressStreet2")
    public WebElement arAddressLine2;

    @FindBy(id = "auto-authorizedAddressCity")
    public WebElement arAddressCity;

    @FindBy(id = "auto-authorizedAddressState")
    public WebElement arAddressState;

    @FindBy(id = "auto-authorizedAddressZip")
    public WebElement arAddressZip;

    @FindBy(id = "authorizationSignatureDate")
    public WebElement arAddressSignatureDate;

    @FindBy(xpath = "(//button[@class='MuiButtonBase-root MuiIconButton-root mx-0 px-0'])[1]")
    public WebElement arStartDateBtn;

    @FindBy(xpath = "(//button[@class='MuiButtonBase-root MuiIconButton-root mx-0 px-0'])[2]")
    public WebElement arEndDateBtn;

    @FindBy(xpath = "(//button[@class='MuiButtonBase-root MuiIconButton-root mx-0 px-0'])[3]")
    public WebElement arAuthorizedDateBtn;

    @FindBy(xpath = "(//span[@class='MuiButton-label'])[8]")
    public WebElement arDateSelectOkBtn;

    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiIconButton-root MuiPickersDay-day MuiPickersDay-dayDisabled']")
    public List<WebElement> dateSelectedDisabledBtns;

    @FindBy(xpath = "//i[.='edit']//parent::span//parent::button")
    public WebElement arEditBtn;

    @FindBy(xpath = "(//span[@class='material-icons MuiIcon-root'])[1]")
    public WebElement arBackBtn;

    @FindBy(xpath = "//label[contains(text(),'AUTH TYPE')]")
    public WebElement authTypeLabel;

    @FindBy(xpath = "//label[contains(text(),'FIRST NAME')]")
    public WebElement firstNameLabel;

    @FindBy(xpath = "//label[contains(text(),'MI')]")
    public WebElement middleInitialLabel;

    @FindBy(xpath = "//label[contains(text(),'LAST NAME')]")
    public WebElement lastNameLabel;

    @FindBy(xpath = "//label[contains(text(),'ORGANIZATION NAME')]")
    public WebElement organizationNameLabel;

    @FindBy(xpath = "//label[contains(text(),'ID NUMBER')]")
    public WebElement idNumberLabel;

    @FindBy(xpath = "//label[contains(text(),'START DATE')]")
    public WebElement startDateLabel;

    @FindBy(xpath = "//label[contains(text(),'END DATE')]")
    public WebElement endDateLabel;

    @FindBy(xpath = "//label[contains(text(),'ACCESS TYPE')]")
    public WebElement accessTypeLabel;

    @FindBy(xpath = "//label[contains(text(),'CORRESPONDENCE')]")
    public WebElement correspondenceLabel;

    @FindBy(xpath = "//label[contains(text(), 'ADDRESS LINE 1')]")
    public WebElement addressLine1Label;

    @FindBy(xpath = "//label[contains(text(),'ADDRESS LINE 2')]")
    public WebElement addressLine2Label;

    @FindBy(xpath = "//label[contains(text(),'CITY')]")
    public WebElement cityLabel;

    @FindBy(xpath = "//label[contains(text(),'STATE')]")
    public WebElement stateLabel;

    @FindBy(xpath = "//label[contains(text(),'ZIP CODE')]")
    public WebElement zipCodeLabel;

    @FindBy(xpath = "//legend[contains(text(),'AUTHORIZED')]")
    public WebElement authorizedLabel;

    @FindBy(xpath = "//label[contains(text(),'AUTHORIZATION SIGNATURE DATE')]")
    public WebElement authorizationSignatureDateLabel;

    @FindBy(xpath = "(//button[@class='MuiButtonBase-root MuiIconButton-root MuiPickersCalendarHeader-iconButton'])[1]")
    public WebElement prevMonthSelction;

    @FindBy(xpath = "(//button[@class='MuiButtonBase-root MuiIconButton-root MuiPickersDay-day'])[1]")
    public WebElement dateSelectBtn;

    @FindBy(xpath = "(//button[@class='MuiButtonBase-root MuiIconButton-root MuiPickersDay-day'])[1]")
    public WebElement secondDateSelectBtn;

    @FindBy(xpath = "(//h6[@class='m-0'])[2]")
    public WebElement startDateViewAr;

    @FindBy(xpath = "(//h6[@class='m-0'])[3]")
    public WebElement endDateViewAr;

    @FindBy(xpath = "(//h6[@class='m-0'])[9]")
    public WebElement authorizedDateViewAr;

    @FindBy(xpath = "//div[@class='MuiPaper-root MuiMenu-paper MuiPopover-paper MuiPaper-elevation8 MuiPaper-rounded']//ul//li")
    public List<WebElement> authorizedRepFieldDropdownValues;

    @FindBy(xpath = "//li[@data-value='Broker']")
    public WebElement authTypeSelection;

    @FindBy(xpath = "//li[@data-value='Full Access']")
    public WebElement accessTypeSelection;

    @FindBy(css = "input[value='true']")
    public WebElement authorizedYes;

    @FindBy(css = "input[value='false']")
    public WebElement authorizedNo;

    @FindBy(xpath = "//h2[contains(text(),'AUTHORIZED REPRESENTATIVE(S)')]")
    public WebElement addAuthRepHeader;

    @FindBy(xpath = "//p[contains(text(),'END DATE cannot be before START DATE')]")
    public WebElement endDateErrorMessage;

    @FindBy(xpath = "//p[contains(text(),'AUTH TYPE')]")
    public WebElement savedAuthTypeLabel;

    @FindBy(xpath = "//p[contains(text(),'AUTH TYPE')]/following-sibling::h6")
    public WebElement savedAuthType;

    @FindBy(xpath = "//p[contains(text(),'FIRST NAME')]")
    public WebElement savedFirstNameLabel;

    @FindBy(xpath = "//p[contains(text(),'FIRST NAME')]/following-sibling::h6")
    public WebElement savedFirstName;

    @FindBy(xpath = "//p[contains(text(),'MI')]")
    public WebElement savedMiLabel;

    @FindBy(xpath = "//p[contains(text(),'MI')]/following-sibling::h6")
    public WebElement savedMi;

    @FindBy(xpath = "//p[contains(text(),'LAST NAME')]")
    public WebElement savedLastNameLabel;

    @FindBy(xpath = "//p[contains(text(),'LAST NAME')]/following-sibling::h6")
    public WebElement savedLastName;

    @FindBy(xpath = "//p[contains(text(),'ORGANIZATION NAME')]")
    public WebElement savedOrgNameLabel;

    @FindBy(xpath = "//p[contains(text(),'ORGANIZATION NAME')]/following-sibling::h6")
    public WebElement savedOrgName;

    @FindBy(xpath = "//p[contains(text(),'ID NUMBER')]")
    public WebElement savedIdNumberLabel;

    @FindBy(xpath = "//p[contains(text(),'ID NUMBER')]/following-sibling::h6")
    public WebElement savedIdNumber;

    @FindBy(xpath = "//p[contains(text(),'START DATE')]")
    public WebElement savedStartDateLabel;

    @FindBy(xpath = "//p[contains(text(),'START DATE')]/following-sibling::h6")
    public WebElement savedStartDate;

    @FindBy(xpath = "//p[contains(text(),'END DATE')]")
    public WebElement savedEndDateLabel;

    @FindBy(xpath = "//p[contains(text(),'END DATE')]/following-sibling::h6")
    public WebElement savedEndDate;

    @FindBy(xpath = "//p[contains(text(),'ACCESS TYPE')]")
    public WebElement savedAccessTypeLabel;

    @FindBy(xpath = "//p[contains(text(),'ACCESS TYPE')]/following-sibling::h6")
    public WebElement savedAccessType;

    @FindBy(xpath = "//p[contains(text(),'CORRESPONDENCE')]")
    public WebElement savedCorrespondenceLabel;

    @FindBy(xpath = "//p[contains(text(),'CORRESPONDENCE')]/following-sibling::h6")
    public WebElement savedCorrespondence;

    @FindBy(xpath = "//p[contains(text(),'ADDRESS LINE 1')]")
    public WebElement savedAddressOneLabel;

    @FindBy(xpath = "//p[contains(text(),'ADDRESS LINE 1')]/following-sibling::h6")
    public WebElement savedAddressOne;

    @FindBy(xpath = "//p[contains(text(),'ADDRESS LINE 2')]")
    public WebElement savedAddressTwoLabel;

    @FindBy(xpath = "//p[contains(text(),'ADDRESS LINE 2')]/following-sibling::h6")
    public WebElement savedAddressTwo;

    @FindBy(xpath = "//p[contains(text(),'CITY')]")
    public WebElement savedCityLabel;

    @FindBy(xpath = "//p[contains(text(),'CITY')]/following-sibling::h6")
    public WebElement savedCity;

    @FindBy(xpath = "//p[contains(text(),'STATE')]")
    public WebElement savedStateLabel;

    @FindBy(xpath = "//p[contains(text(),'STATE')]/following-sibling::h6")
    public WebElement savedState;

    @FindBy(xpath = "//p[contains(text(),'ZIP CODE')]")
    public WebElement savedZipLabel;

    @FindBy(xpath = "//p[contains(text(),'ZIP CODE')]/following-sibling::h6")
    public WebElement savedZip;

    @FindBy(xpath = "//legend[contains(text(),'AUTHORIZED')]")
    public WebElement savedAuthLabel;

    @FindBy(xpath = "//span[contains(text(),'Yes')]")
    public WebElement savedAuthYesLabel;

    @FindBy(xpath = "//input[@value='true']/../..")
    public WebElement savedAuthYesbutton;

    @FindBy(xpath = "//span[contains(text(),'No')]")
    public WebElement savedAuthNoLabel;

    @FindBy(xpath = "//input[@value='false']/../..")
    public WebElement savedAuthNo;

    @FindBy(xpath = "//p[contains(text(),'AUTHORIZATION SIGNATURE DATE')]")
    public WebElement savedAuthSignatureLabel;

    @FindBy(xpath = "//p[contains(text(),'AUTHORIZATION SIGNATURE DATE')]/following-sibling::h6")
    public WebElement savedAuthSignature;

    @FindBy(xpath = "//strong[contains(.,'CONSUMER ID')]")
    public WebElement consumerId;

    @FindBy(xpath = "//*[contains(text(),'AUTHORIZED REPRESENTATIVE')]/following::div[3]//*[@name='consumerFirstName']")
    public  WebElement consumerFirstName;

    @FindBy(xpath = "//*[contains(text(),'AUTHORIZED REPRESENTATIVE')]/following::div[3]//*[@name='consumerMiddleName']")
    public  WebElement consumerMiddleName;

    @FindBy(xpath = "//*[contains(text(),'AUTHORIZED REPRESENTATIVE')]/following::div[3]//*[@name='consumerLastName']")
    public  WebElement consumerLastName;

    @FindBy(xpath = "//*[contains(text(),'AUTHORIZED REPRESENTATIVE')]/following::div[3]//*[@name='effectiveStartDate']")
    public WebElement effectiveStartDate;

    @FindBy(xpath = "//*[contains(text(),'AUTHORIZED REPRESENTATIVE')]/following::div[3]//*[@name='effectiveEndDate']")
    public WebElement effectiveEndDate;
}
