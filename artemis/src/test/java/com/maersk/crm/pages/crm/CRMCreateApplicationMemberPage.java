package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMCreateApplicationMemberPage {

    

    public CRMCreateApplicationMemberPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//h2[contains(text(),'APPLICATION MEMBER')]")
    public WebElement applicationMemberHeader;

    @FindBy(xpath = "//label[@for='consumerFirstName']")
    public WebElement firstNameLabel;

    @FindBy(id = "consumerFirstName")
    public WebElement firstName;

    @FindBy(xpath = "//label[@for='consumerMiddleName']")
    public WebElement middleInitialLabel;

    @FindBy(id = "consumerMiddleName")
    public WebElement middleInitial;

    @FindBy(xpath = "//label[@for='consumerLastName']")
    public WebElement lastNameLabel;

    @FindBy(id = "consumerLastName")
    public WebElement lastName;

    @FindBy(xpath = "//label[@for='consumerSuffix']")
    public WebElement suffixLabel;

    @FindBy(id = "consumerSuffix")
    public WebElement suffix;

    @FindBy(xpath = "//label[@for='dateOfBirth']")
    public WebElement dobLabel;

    @FindBy(id = "dateOfBirth")
    public WebElement dob;

    @FindBy(xpath = "//label[@for='age']")
    public WebElement ageLabel;

    @FindBy(xpath = "//label[@for='genderCode']")
    public WebElement genderLabel;

    @FindBy(id = "mui-component-select-genderCode")
    public WebElement genderDropdown;

    @FindBy(xpath = "//label[@for='ssn']")
    public WebElement ssnLabel;

    @FindBy(id = "ssn")
    public WebElement ssn;

    @FindBy(xpath = "//label[@for='externalConsumerId']")
    public WebElement externalConsumerIDLabel;

    @FindBy(id = "externalConsumerId")
    public WebElement externalConsumerId;

    @FindBy(xpath = "//label[@for='externalConsumerIdType']")
    public WebElement externalIDTypeLabel;

    @FindBy(id = "externalConsumerIdType")
    public WebElement externalIdType;

    @FindBy(xpath = "//span[.='SET PRIMARY INDIVIDUAL']")
    public WebElement setPrimaryLabel;

    @FindBy(id = "setPrimaryIndividual")
    public WebElement setPrimaryIndividualCheckbox;

    @FindBy(xpath = "//span[contains(text(),'MEDICAID')]")
    public WebElement medicaidLabel;

    @FindBy(xpath = "//span[contains(text(),'CHIP')]")
    public WebElement chipLabel;

    @FindBy(xpath = "//span[contains(text(),'PREGNANCY ASSISTANCE')]")
    public WebElement pregnancyAssistanceLabel;

    @FindBy(xpath = "//span[contains(text(),'HCBS')]")
    public WebElement hcbsLabel;

    @FindBy(id = "list-item-0")
    public WebElement medicaidCheckbox;

    @FindBy(id = "list-item-1")
    public WebElement chipCheckbox;

    @FindBy(id = "list-item-2")
    public WebElement pregnancyAssistanceCheckbox;

    @FindBy(id = "list-item-0")
    public WebElement hcbsCheckbox;

    @FindBy(id = "selectNotApplying")
    public WebElement NOTAPPLYINGCheckBox;

    @FindBy(xpath = "//span[contains(text(),'check')]")
    public WebElement saveButton;

    @FindBy(xpath = "//span[contains(text(),'close')]")
    public WebElement cancelButton;

    @FindBy(xpath = "//legend[.='ARE YOU PREGNANT']")
    public WebElement areYouPregnantLabel;

    @FindBy(xpath = "//label[@for='expectedBabies']")
    public WebElement numExpectedBabiesLabel;

    @FindBy(id = "expectedDueDate")
    public WebElement expectedDueDate;

    @FindBy(xpath = "//input[@value='true' and @type='radio']")
    public WebElement yesAreYouPregnant;

    @FindBy(xpath = "//input[@value='false' and @type='radio']")
    public WebElement noAreYouPregnant;

    @FindBy(id = "mui-component-select-expectedBabies")
    public WebElement numBabiesDropdown;

    @FindBy(id = "mui-component-select-externalConsumerIdType")
    public WebElement getExternalIdType;

    @FindBy(xpath = "//span[contains(text(),'Application successfully updated!')]")
    //span[@id='client-snackbar'])[2]
    public WebElement appSuccesfullyUpdatedMessage;

    @FindBy(xpath = "//p[contains(text(),'FIRST NAME is required')]")
    public WebElement firstNameRequiredMsg;

    @FindBy(xpath = "//p[contains(text(),'LAST NAME is required')]")
    public WebElement lastNameRequiredMsg;

    @FindBy(xpath = "//p[.='Are you sure you want to change the Primary Individual?']")
    public WebElement piSetWarningMsg;

    @FindBy(xpath = "(//span[.='check'])[2]")
    public WebElement piSetWarningContinue;

    @FindBy(xpath = "//span[.='clear']")
    public WebElement piSetWarningCancel;

    @FindBy(xpath = "//i[.='edit']//parent::span//parent::button")
    public WebElement appMemEditButton;

    @FindBy(xpath = "//p[.='If you continue, all the captured information will be lost']")
    public WebElement navigateAwayWarningMsg;

    @FindBy(xpath = "//span[.='keyboard_backspace']")
    public WebElement backArrow;

    @FindBy(xpath = "(//span[.='check'])[2]")
    public WebElement navigatAwayWarningContinue;

    @FindBy(id = "genderCode")
    public WebElement getGenderValue;

    @FindBy(xpath = "//input[@value='Medicaid']/../..")
    public WebElement getMedacaidCheckbox;

    @FindBy(xpath = "//input[@value='CHIP']/../..")
    public WebElement getChipCheckbox;

    @FindBy(xpath = "//input[@value='Pregnancy Assistance']/../..")
    public WebElement getPregnancyAssistanceCheckboxCheckbox;

    @FindBy(xpath = "//input[@value='HCBS']/../..")
    public WebElement getHCBSCheckbox;

    @FindBy(xpath = "//input[@id='setPrimaryIndividual']/../..")
    public WebElement getSetPICheckbox;

    @FindBy(xpath = "//span[contains(text(),'REMOVE')]")
    public WebElement removeButton;

    @FindBy(xpath = "//span[.='check']")
    public WebElement removeMemberMsgContinue;

    @FindBy(xpath = "//i[.='close']//parent::button")
    public WebElement removePrimaryIndividualButtonInAppMemberPanel;

    @FindBy(xpath = "//i[.='how_to_reg']//parent::button")
    public WebElement setAsPrimaryIndividualBtnInAppMemberPanel;

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

    @FindBy(xpath = "//p[contains(text(),'SUFFIX')]")
    public WebElement savedSuffixLabel;

    @FindBy(xpath = "//p[contains(text(),'SUFFIX')]/following-sibling::h6")
    public WebElement savedSuffix;

    @FindBy(xpath = "//p[contains(text(),'DATE OF BIRTH')]")
    public WebElement savedDOBLabel;

    @FindBy(xpath = "//p[contains(text(),'DATE OF BIRTH')]/following-sibling::h6")
    public WebElement savedDOB;

    @FindBy(xpath = "//p[contains(text(),'AGE')]")
    public WebElement savedAgeLabel;

    @FindBy(xpath = "//p[contains(text(),'AGE')]/following-sibling::h6")
    public WebElement savedAge;

    @FindBy(xpath = "//p[contains(text(),'GENDER')]")
    public WebElement savedGenderLabel;

    @FindBy(xpath = "//p[contains(text(),'GENDER')]/following-sibling::h6")
    public WebElement savedGender;

    @FindBy(xpath = "//legend[contains(text(),'ARE YOU PREGNANT')]")
    public WebElement savedAreYouPregnantLabel;

    @FindBy(xpath = "//span[contains(text(),'Yes')]")
    public WebElement savedAreYouPregnantYesLabel;

    @FindBy(xpath = "//span[contains(text(),'No')]")
    public WebElement savedAreYouPregnantNoLabel;

    @FindBy(xpath = "//span[contains(text(),'Yes')]/../span")
    public WebElement savedAreYouPregnantYes;

    @FindBy(xpath = "//span[contains(text(),'No')]/../span")
    public WebElement savedAreYouPregnantNo;

    @FindBy(xpath = "//p[contains(text(),'NO. OF BABIES EXPECTED')]")
    public WebElement savedNoOfBabiesLabel;

    @FindBy(xpath = "//p[contains(text(),'NO. OF BABIES EXPECTED')]/following-sibling::h6")
    public WebElement savedNoOfBabies;

    @FindBy(xpath = "//p[contains(text(),'EXPECTED DUE DATE')]")
    public WebElement savedExpectedDueDateLabel;

    @FindBy(xpath = "//p[contains(text(),'EXPECTED DUE DATE')]/following-sibling::h6")
    public WebElement savedExpectedDueDate;

    @FindBy(xpath = "//p[contains(text(),'SSN')]")
    public WebElement savedSSNLabel;

    @FindBy(xpath = "//p[contains(text(),'SSN')]/following-sibling::h6")
    public WebElement savedSSN;

    @FindBy(xpath = "//p[contains(text(),'EXTERNAL CONSUMER ID')]")
    public WebElement savedExConIdLabel;

    @FindBy(xpath = "//p[contains(text(),'EXTERNAL CONSUMER ID')]/following-sibling::h6")
    public WebElement savedExConId;

    @FindBy(xpath = "//p[contains(text(),'EXTERNAL ID TYPE')]")
    public WebElement savedExIdTypeLabel;

    @FindBy(xpath = "//p[contains(text(),'EXTERNAL ID TYPE')]/following-sibling::h6")
    public WebElement savedExIdType;

    @FindBy(xpath = "//span[contains(text(),'SET PRIMARY INDIVIDUAL')]")
    public WebElement savedSetPrimaryIndLabel;

    @FindBy(xpath = "//h2[contains(text(),'PROGRAM(S) APPLIED FOR')]")
    public WebElement savedProgramsAppliedLabel;

    @FindBy(xpath = "//span[contains(text(),'CHIP')]")
    public WebElement savedCHIPLabel;

    @FindBy(xpath = "//span[contains(text(),'CHIP')]/../span")
    public WebElement savedCHIPselectBox;

    @FindBy(xpath = "//span[contains(text(),'MEDICAID')]")
    public WebElement savedMedicaidLabel;

    @FindBy(xpath = "//span[contains(text(),'MEDICAID')]/../span")
    public WebElement savedMedicaidselectBox;

    @FindBy(xpath = "//span[contains(text(),'PREGNANCY ASSISTANCE')]")
    public WebElement savedPregAssistLabel;

    @FindBy(xpath = "//span[contains(text(),'PREGNANCY ASSISTANCE')]/../span")
    public WebElement savedPregAssistCheckBox;

    @FindBy(xpath = "//span[contains(text(),'HCBS')]")
    public WebElement savedHCBSLabel;

    @FindBy(xpath = "//span[contains(text(),'HCBS')]/../span")
    public WebElement savedHCBSCheckBox;

    @FindBy(xpath = "//*[@aria-label='Toggle SSN visibility']")
    public WebElement ssnRevealIcon;
}
