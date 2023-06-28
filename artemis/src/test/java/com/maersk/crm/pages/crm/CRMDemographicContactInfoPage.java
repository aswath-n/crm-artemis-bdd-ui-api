package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class
CRMDemographicContactInfoPage {

    

    public CRMDemographicContactInfoPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//*[text()='ERROR MESSAGE']/../div/span")
    public WebElement lblErrorMessage;

    @FindBy(xpath = "//a[contains(text(), 'Demographic Info')]")
    public WebElement demographicInfoTab;

    @FindBy(xpath = "(//*[contains(text(), 'Demographic Info')])[2]")
    public WebElement demographicInfoTabFromSearch;

    @FindBy(xpath = "//*[contains(text(), 'Application Info')]/../..")
    public WebElement applicationInfoTab;

    @FindBy(xpath = "//a[contains(text(), 'Application Info')]")
    public WebElement applicationInfoTabLink;

    @FindBy(xpath = "//li/a[contains(text(), 'Task & Service Request')]/../following-sibling::li/a[contains(text(), 'Application Info')]/../following-sibling::li/a[contains(text(), 'Program & Benefit Info')]")
    public WebElement applicationInfoTabLocation;

    @FindBy(xpath = "//*[contains(text(), 'CONTACT INFO')]/../..")
    public WebElement contactInfoTab;
    //FindBy(xpath="//*[contains(text(), 'CONTACT INFO')]/parent::*/..")
    //public WebElement contactInfoTab;

    @FindBy(xpath = "//*[contains(h5,'PROFILE CONTACT')]")
    public WebElement profileContactLabel;

    @FindBy(xpath = "//*[contains(h5,'PROFILE DETAILS')]")
    public WebElement profileDetailsLabel;

    //Refactored Xpath --aswath 04/04/2019
    @FindBy(xpath = "//*[contains(h5,'ADDRESS')]")
    public WebElement addressLabel;

    @FindBy(xpath = "//*[contains(h5,'PRIMARY INDIVIDUALS')]")
    public WebElement primaryIndividualsLabel;

    @FindBy(xpath = "//*[contains(h5,'CASE MEMBERS')]")
    public WebElement caseMembersLabel;

    @FindBy(xpath = "//*[contains(h5,'AUTHORIZED REPRESENTATIVE')]")
    public WebElement authorizedRepLabel;

    @FindBy(xpath = "//*[contains(h5,'EMAIL')]")
    public WebElement emailLabel;

    @FindBy(xpath = "//h5[contains(text(), 'ADDRESS')]/../button")
    public WebElement addAddressButton;

    @FindBy(xpath = "(//*[contains(span,'ADD')])[2]")
    public WebElement addPhoneButton;

    @FindBy(xpath = "(//*[contains(span,'ADD')])[3]")
    public WebElement addEmailButton;

    @FindBy(xpath = "(//p[@class='mb-0 mx-color-text-primary'])[1]")
    public WebElement consumerName;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[1]//tr[*]//td[2]")
    })
    public List<WebElement> fullAddresses;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[1]//tr[*]//td[4]")
    })
    public List<WebElement> addressCounties;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[1]//tr[*]//td[5]")
    })
    public List<WebElement> addressTypes;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[1]//tr[*]//td[6]")
    })
    public List<WebElement> addressSources;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')]//tr//td[7]")
    })
    public List<WebElement> addressesStatuses;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[1]//tr[*]//td[6]")
    })
    public List<WebElement> addressesStatusesNotOnCase;

    @FindAll({
            @FindBy(xpath = "(//table[@class='MuiTable-root mdl-data-table mdl-js-data-table mx-table table mt-4 mb-0 mdl-color--grey-50'])[1]//tr[*]/td[2]")
    })
    public List<WebElement> addresses;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[2]//tr[*]//td[6]")
    })
    public List<WebElement> phoneSources;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[2]//tr[*]//td[3]")
    })
    public List<WebElement> phoneNumbers;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[2]//tr[*]//td[3]")
    })
    public List<WebElement> phoneConsumers;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[2]//tr[*]//td[4]")
    })
    public List<WebElement> phoneTypes;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[2]//tr[*]//td[6]")
    })
    public List<WebElement> phoneComments;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])//td[5]")
    })  //(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[2]//tr[*]//td[6]
    public List<WebElement> phoneStatuses;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mdl-data-table mdl-js-data-table mx-table table mt-4')])[2]//tr[*]//td[7]")
    })
    public List<WebElement> phoneStatuses2;

    @FindBy(xpath = "(//*[contains(@class , 'mdl-data-table mdl-js-data-table mx-table table mt-4')])[2]//tr[*]//td[7]")
    public WebElement statusOfNumberConsumerProfile;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[3]//tr[*]//td[2]")
    })
    public List<WebElement> emails;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[3]//tr[*]//td[3]")
    })
    public List<WebElement> emailCaseMembers;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[1]//tr[*]//td[3]")
    })
    public List<WebElement> emailStatuses;

    //Refactored Xpath --aswath 04/04/2019
    @FindBy(xpath = "(//div[@role='tooltip']/div/div/div)[1]/h6")
    //(//h6[@class='m-0  float-left'])[1]
    public WebElement statusStartDate;
    @FindBy(xpath = "(//div[@role='tooltip']/div/div/div)[2]/h6")
    //(//h6[@class='m-0  float-left'])[1],
    public WebElement phonestatusStartDate;
    @FindBy(xpath = "(//div[@role='tooltip']/div/div/div)[1]/h6")
    //(//h6[@class='m-0  float-left'])[1]
    public WebElement emailStatusStartDate;

    //Refactored Xpath --aswath 04/04/2019
    @FindBy(xpath = "(//div[@role='tooltip']/div/div/div)[2]/h6")
    //(//h6[@class='m-0  float-left'])[2]
    public WebElement statusEndDate;

    @FindBy(xpath = "//*[text()='EMAIL']/..//table[contains(@class, 'mt-4')]/tbody")
    public WebElement emailTable;

    @FindAll({
            @FindBy(xpath = "(//*[@class='pagination'])[1]/li")
    })
    public List<WebElement> pagination;

    @FindBy(xpath = "(//*[@class='mx-section-header float-left mt-4'])[1]")
    public WebElement primaryIndividualsIcon;


    @FindBy(xpath = "//*[contains(h5,'PHONE NUMBER')]")
    public WebElement phoneNumberSection;

    @FindBy(xpath = "//span[text()='Continue']")
    public WebElement continueBtnOnWrnMsg;

    @FindBy(xpath = "(//p[@class='mb-0 mx-color-text-primary'])[2]")
    public WebElement dateOfBirth;

    @FindBy(xpath = "(//p[@class='mb-0 mx-color-text-primary'])[3]")
    public WebElement consumerAge;

    @FindBy(xpath = "(//p[@class='mb-0 mx-color-text-primary'])[4]")
    public WebElement consumerGender;

    @FindBy(xpath = "(//p[@class='mb-0 mx-color-text-primary'])[5]")
    public WebElement startDate;

    @FindBy(xpath = "(//p[@class='mb-0 mx-color-text-primary'])[6]")
    public WebElement endDate;

    @FindBy(xpath = "(//p[@class='mb-0 mx-color-text-primary'])[7]")
    public WebElement spokenLanguage;

    @FindBy(xpath = "(//p[@class='mb-0 mx-color-text-primary'])[8]")
    public WebElement writtenLanguage;

    @FindBy(xpath = "(//p[@class='mb-0 mx-color-text-primary'])[9]")
    public WebElement consumerSSN;

    @FindBy(xpath = "(//p[@class='mb-0 mx-color-text-primary'])[10]")
    public WebElement correspondencePreferences;

    @FindBy(xpath = "//table//tr/th[text()='PHONE NUMBER']")
    public WebElement phoneNumColumnHeader;

    @FindBy(xpath = "//table//tr/th[text()='TYPE']")
    public WebElement typeColumnHeader;

    @FindBy(xpath = "//table//tr/th[text()='COMMENTS']")
    public WebElement commentsColumnHeader;

    @FindBy(xpath = "//table//tr/th[text()='STATUS']")
    public WebElement statusColumnHeader;

    @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[1]//tr[*]//td[2]") // if no addresses listed
    public List<WebElement> phnNumbers2;

    @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[1]//tr[*]//td[4]") // if no addresses listed
    public List<WebElement> phnNumberTypes2;

    @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[1]//tr[*]//td[5]") // if no addresses listed
    public List<WebElement> phnNumberComments2;

    @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[1]//tr[*]//td[6]") // if no addresses listed
    public List<WebElement> phnNumberStatus3;

    @FindBy(xpath = "//tbody//td[3]")
    public List<WebElement> listOfPrimaryIndvsAndCaseMembersNames;

    @FindBy(name = "addressCity")
    public WebElement consumerCity;

    @FindBy(id = "addressCounty")
    public WebElement consumerCounty;

    @FindBy(id = "addressZip")
    public WebElement consumerZipCode;

    @FindBy(xpath = "//table//tr/th[text()='ADDRESS SOURCE']")
    public WebElement source;

    @FindBy(id = "emailAddress")
    public WebElement consumerEmailAddress;

    @FindBy(xpath = "//td[contains(text(), 'Service AccountOne')]/following-sibling::td[text() = 'ACTIVE']")
    public WebElement activeAddresFirstLine;

    @FindBy(xpath = "//*[text()='The start Date of the Future Address cannot be earlier than the End Date of the Active Address.']")
    public WebElement errorMessageFutureAddress;

    @FindBy(xpath = "//*[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mx-link  MuiTableCell-sizeSmall']")
    public List<WebElement> listOfConsumerID;



    public boolean checkIfFieldIsDisplayed(String option) {
        boolean verified = false;
        switch (option) {
            case "consumerName":
                verified = consumerName.isDisplayed();
                break;
            case "DOB":
                verified = dateOfBirth.isDisplayed();
                break;
            case "age":
                verified = consumerAge.isDisplayed();
                break;
            case "ssn":
                verified = consumerSSN.isDisplayed();
                break;
            case "gender":
                verified = consumerGender.isDisplayed();
                break;
            case "consumerType": //consumer type not displayed on demographic info tab
                verified = true;
                break;
            case "spokenLanguage":
                verified = spokenLanguage.isDisplayed();
                break;
            case "writtenLanguage":
                verified = writtenLanguage.isDisplayed();
                break;
            case "startDate":
                verified = startDate.isDisplayed();
                break;
            case "endDate":
                verified = endDate.isDisplayed();
                break;
            case "correspondencePreference(s)":
                verified = correspondencePreferences.isDisplayed();
                break;
        }
        return verified;
    }

    @FindBy(xpath = "//div[@id='mui-component-select-relatesTo']/..")  //input[@id='relatesTo']/..
    public WebElement relatesToDropdown;

    @FindBy(xpath = "//*[text()='Save']")
    public WebElement saveNotesButton;

    @FindBy(xpath = "//*[text()='Cancel']")
    public WebElement cancelNotesButton;

    @FindBy(xpath = "//*[@name='notesText']")
    public WebElement notesTextInputField;

    @FindBy(xpath = "//*[text()='CASE NOTES']")
    public WebElement caseNotesLabel;

    @FindBy(xpath = "//*[text()='Notes Saved Succesfully']")
    public WebElement NotesSavedSuccesfullyMessage;

    @FindBy(xpath = "//div/ul[@class='MuiList-root MuiMenu-list MuiList-padding']/li")
    public List<WebElement> relatesToDropdownOptions;

    @FindBy(xpath = "((//div[@class='col-12 pl-0 mt-3'])[2]//p)[3]")
    public WebElement firstNotesInNotesList;

    @FindBy(xpath = "//*[text()='Please indicate who this note relates to.']")
    public WebElement messagePleaseIndicateWhoThisNoteRelatesTo;

    @FindBy(xpath = "//*[text()='Please provide Note text.']")
    public WebElement messagePleaseProvideNoteText;

    @FindBy(xpath = "//*[text()='CONSUMER NOTES']")
    public WebElement consumerNotesLabel;

    @FindBy(xpath = "(((//tbody[@class='MuiTableBody-root'])[1]/tr)[1]/td)[6]")
    public WebElement firstAgeInPiTableInCase;

    @FindBy(xpath = "(((//tbody[@class='MuiTableBody-root'])[1]/tr)[3]/td)[6]")
    public WebElement secondAgeInPiTableInCase;;

    @FindBy(xpath = "((//*[(text()='PRIMARY INDIVIDUALS')]//following-sibling::div[contains(@class,'table')]//tr)[2]/td)[8]")
    public WebElement fistRowPIstatus;

    @FindBy(xpath = "(//*[contains(text(), 'PRIMARY INDIVIDUALS')]/following-sibling::div)[2]//*[text() = 'ACTIVE']")
    public List <WebElement> numberOfActivePI;

    @FindBy(xpath = "//input[@type='checkbox' and @value= 'Mail']")
    public WebElement mailChexboxEdidPiPage;

    @FindBy(xpath = "//input[@type='checkbox' and @value= 'Phone']")
    public WebElement phoneChexboxEdidPiPage;

    @FindBy(xpath = "//input[@type='checkbox' and @value= 'Email']")
    public WebElement emailChexboxEdidPiPage;

    @FindBy(xpath = "//input[@type='checkbox' and @value= 'Text']")
    public WebElement textChexboxEdidPiPage;

    @FindBy(xpath = "//input[@type='checkbox' and @value= 'Fax']")
    public WebElement faxChexboxEdidPiPage;

    @FindBy(xpath = "//td[text() = 'Consumer Reported']")
    public WebElement sourceTypeConsumerReported;

    @FindBy(xpath = "//*[text() = 'If you continue, all the captured information will be lost']")
    public WebElement warningNoteMessage;


    @FindBy(xpath = "((//tbody[@class='MuiTableBody-root'])[2]/tr/td)[5]")
    public WebElement firsMemberAge;
    }
