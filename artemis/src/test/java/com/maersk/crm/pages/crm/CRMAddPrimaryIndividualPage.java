package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CRMAddPrimaryIndividualPage {
    

    public CRMAddPrimaryIndividualPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//*[text()='PRIMARY INDIVIDUALS']")
    public WebElement primaryIndividualLabel;

    @FindBy(xpath = "//label[@for='consumerFirstName']")
    public WebElement piFirstNameFieldName;

    @FindBy(id = "consumerFirstName")
    public WebElement piFirstNameField;

    @FindBy(name = "consumerMiddleName")
    public WebElement piMiddleName;

    @FindBy(xpath = "//label[@for='consumerLastName']")
    public WebElement piLastNameFieldName;

    @FindBy(id = "consumerLastName")
    public WebElement piLastNameField;

    @FindBy(xpath = "//label[@for='consumerDateOfBirth']")
    public WebElement piDOBFieldName;

    @FindBy(id = "consumerDateOfBirth")
    public WebElement piDOBField;

    @FindBy(name = "age")
    public WebElement piAge;

    @FindBy(xpath = "//label[@for='genderCode']")
    public WebElement piGenderFieldName;

    @FindBy(xpath = "//*[@id='genderCode']/..")
    public WebElement piGender;

    @FindBy(xpath = "//input[@id='effectiveStartDate']")
    public WebElement piStartDate;

    @FindBy(xpath = "//input[@id='effectiveEndDate']")
    public WebElement piEndDate;

    @FindBy(xpath = "//*[@name='preferredLanguage']/..")
    public WebElement piLanguage;

    @FindBy(xpath = "//label[@for='consumerSSN']")
    public WebElement piSSNFieldName;

    @FindBy(id = "consumerSSN")
    public WebElement piSSNField;

    @FindBy(xpath = "//span[text()='SAVE']")
    public WebElement saveButton;

    @FindBy(xpath = "//*[text()='CANCEL']/..")
    public WebElement cancelButton;

    @FindBy(xpath = "//ul[@role='listbox']/li[last()]")
    public WebElement genderDropdownLastItem;

    //errors
    @FindBy(xpath = "//p[text()='FIRST NAME is required and cannot be left blank']")
    public WebElement piFNBlankError;

    @FindBy(xpath = "//p[text()='LAST NAME is required and cannot be left blank']")
    public WebElement piLNBlankError;

    @FindBy(xpath = "//p[text()='DOB is required and cannot be left blank']")
    public WebElement piDOBBlankError;

    //Vinuta 05/13 - refactored text
    @FindBy(xpath = "//p[text()='GENDER is required and cannot be left blank']")
    public WebElement piGenderBlankError;

    @FindBy(xpath = "//p[text()='START DATE is required and cannot be left blank']")
    public WebElement piStartDateBlankError;

    @FindBy(xpath = "//p[text()='SSN is required and cannot be left blank']")
    public WebElement piSSNBlankError;

    @FindBy(xpath = "(//*[@placeholder='MM/DD/YYYY'])[1]/../following-sibling::p")
    public WebElement piDOBGenericError;

    @FindBy(xpath = "(//*[@placeholder='MM/DD/YYYY'])[2]/../following-sibling::p")
    public WebElement piStartDateGenericError;

    @FindBy(xpath = "(//*[@placeholder='MM/DD/YYYY'])[3]/../following-sibling::p")
    public WebElement piEndDateGenericError;

    @FindBy(xpath = "//input[@name='fieldValue']/../following-sibling::p")
    public WebElement piSSNGenericError;

    @FindBy(xpath = "//span[contains(text(),'INACTIVATE IMMEDIATELY')]")
    public WebElement checkBoxLabelInactive;

    @FindBy(xpath = "//input[@type='checkbox']")
    public WebElement checkboxInactive;

    public By PItablecells = By.xpath("//*[(text()='PRIMARY INDIVIDUALS')]//following-sibling::div[contains(@class,'table')]//tr//td");
    public By PItableRows = By.xpath("//*[(text()='PRIMARY INDIVIDUALS')]//following-sibling::div[contains(@class,'table')]//tr");

    @FindBy(xpath = "//h5[text()='ROLE INFORMATION']/span[text()='person']")
    public WebElement roleInformationHeader;

    @FindBy(xpath = "//h5[text()='PROFILE INFORMATION']/span[text()='ballot']")
    public WebElement profileInformationHeader;

    @FindBy(xpath = "//label[text()='CONSUMER ROLE']")
    public WebElement consumerRoleFieldName;

    @FindBy(xpath = "//input[@id='consumerRole']")
    public WebElement consumerRoleField;

    @FindBy(xpath = "//div[@id='mui-component-select-receiveCorrespondence']")
    public WebElement receiveCorrespondence;

    @FindBy(xpath = "//input[@id='dateOfDeath']")
    public WebElement piDOD;

    @FindBy(xpath = "//div[@id='mui-component-select-ethnicityCode']")
    public WebElement ethnicity;

    @FindBy(xpath = "//div[@id='mui-component-select-raceCode']")
    public WebElement race;

    @FindBy(xpath = "//div[@id='mui-component-select-citizenship']")
    public WebElement citizenship;

    @FindBy(xpath = "//div[@id='mui-component-select-usResidentStatusCode']")
    public WebElement residency;

    @FindBy(xpath = "//label[@for='spokenLanguage']")
    public WebElement spokenLanguageFieldName;

    @FindBy(xpath = "//div[@id='mui-component-select-spokenLanguage']")
    public WebElement spokenLanguageField;

    @FindBy(xpath = "//label[@for='writtenLanguage']")
    public WebElement writtenLanguageFieldName;

    @FindBy(xpath = "//div[@id='mui-component-select-writtenLanguage']")
    public WebElement writtenLanguageField;

    @FindBy(xpath = "//div[@id='mui-component-select-correspondencePreference']")
    public WebElement corrPreference;

    @FindBy(xpath = "//input[@id='pregnancyInd']/parent::span/parent::span")
    public WebElement pregnancyIndCheckBox;

    @FindBy(xpath = "//label[@for='pregnancyDueDate']")
    public WebElement pregnancyDueDate;

    @FindBy(xpath = "//label[@for='effectiveStartDate']")
    public WebElement lableStartDay;

    @FindBy(xpath = "//label[@for='relationShip']")
    public WebElement labelRelationship;

    @FindBy(id = "mui-component-select-relationShip")
    public WebElement relationshipToDropdown;

    @FindBy(id = "mui-component-select-receiveCorrespondence")
    public WebElement labelReceiveCorrespondence;

    @FindBy(xpath = "//td[text()='Medicaid/RID']")
    public WebElement MedicaidRIDtxt;

    @FindBy(xpath = "//i[text()='delete']")
    public WebElement DeleteIconMedId;

    @FindBy(id = "pregnancyDueDate")
    public WebElement pregnancyDueDateField;

    @FindBy(id = "dateOfDeathNotifiedDate")
    public WebElement dateOfDeathNotifiedDate;

    @FindBy(id = "dateOfDeathNotifiedBy")
    public WebElement dateOfDeathNotifiedBy;

    @FindBy(xpath = "(//*[text() = '***-**-****']/following-sibling::td)[1]")
    public WebElement pIstatus;

    @FindBy(xpath = "//*[@id=\"members\"]/div[1]/div[2]/div/table/tbody/tr[1]/td[2]")
    public WebElement firstPIConsumer;

    @FindBy(xpath = "//input[@id='pregnancyStartDate']")
    public WebElement pregnancyStartDate;

    @FindBy(xpath = "//input[@id='pregnancyEndDate']")
    public WebElement pregnancyEndDate;

    @FindBy(id = "mui-component-select-memberWardType")
    public WebElement memberWardDropdown;

    @FindBy(id = "mui-component-select-consumerRole")
    public WebElement consumerRole;

    @FindBy(id = "consumerRole")
    public WebElement consumerRoleDisabled;

    @FindBy(xpath = "//label[text()='AUTHORIZED REP TYPE']")
    public WebElement authRepTypeTxt;

    @FindBy(id = "mui-component-select-authorizedRepType")
    public WebElement authRefTypeValue;

    @FindBy(xpath = "//label[@for='receiveCorrespondence']")
    public WebElement labelReceiveCorrespondenceText;

    @FindBy(xpath = "//input[@value='Authorized Representative']")
    public WebElement AuthorizedRepresentativeDropdownValue;

}
