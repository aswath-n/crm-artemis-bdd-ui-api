package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMUnidentifiedContactPage {

    

    public CRMUnidentifiedContactPage() {

        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//h5[contains(text(),'CONTACT RECORD')]")
    public WebElement contactRecordHeader;

//    @FindBy(xpath = "//label[contains(text(),'Contact Record Type')]//following-sibling::div//div[@role]")
    @FindBy(xpath = "//*[@class='MuiButtonBase-root MuiButton-root MuiButton-text btn active  mr-3']")
    public WebElement contactRecordTypeButton;

    @FindBy(xpath = "//*[@name='contactRecordType']/..")
    public WebElement activeContactRecordType;

    @FindBy(xpath = "//*[@data-value='Unidentified Contact']")
    public WebElement contactRecordTypeVauleUnidentifiedContact;
//*[@data-value='Unidentified Contact']
//*[@data-value='Third Party']
//*[@data-value='General']

    //@FindBy(xpath = "//label[contains(text(),'Preferred Language')]//following-sibling::div//div[@role]")
    @FindBy(id = "mui-component-select-preferredLanguageCode")
    public WebElement preferredLanguageDropDown;
    //*[@data-value='Russian']
    //*[@data-value='Other']
    //*[@data-value='English']
    //*[@data-value='Spanish']
    //*[@data-value='Vietnamese']

    @FindBy(id = "mui-component-select-contactChannelType")
    public WebElement contactChannelTypeDropDown;
    ////label[contains(text(),'Contact Channel Type')]//following-sibling::div//div[@role]
    //*[@data-value='Phone']
    //*[@data-value='Email']
    //*[@data-value='SMS Text']
    //*[@data-value='Web Chat']

    @FindBy(id = "mui-component-select-inboundCallQueue")
    public WebElement inboundCallQueueDropDown;
    ////label[contains(text(),'Inbound Call Queue')]//following-sibling::div//div[@role]
    //*[@data-value='Eligibility']
    //*[@data-value='English']
    //*[@data-value='Enrollment']
    //*[@data-value='Spanish']

    @FindBy(xpath = "//input[@name='consumerFirstName']")
    public WebElement consumerFirstNameInput;
    @FindBy(xpath = "//input[@name='consumerMiddleName']")
    public WebElement consumerMiddleNameInput;
    @FindBy(xpath = "//input[@name='consumerLastName']")
    public WebElement cconsumerLastNameInput;
    @FindBy(xpath = "//input[@name='fieldValue']")
    public WebElement ssnVauleInput;
    @FindBy(xpath = "//input[@name='crmCaseID']")
    public WebElement fcrmCaseIDInput;
    @FindBy(xpath = "//input[@name='crmConsumerId']")
    public WebElement crmConsumerIdInput;
    @FindBy(xpath = "//input[@placeholder='MM/DD/YYYY']")
    public WebElement dobInput;

    @FindBy(xpath = "//h6[text()=' WRAP-UP AND CLOSE']")
    public WebElement wrapupAndCloseHeader;

    @FindBy(xpath = "//div[text()='Inbound' and @role]")
    public WebElement conatactTypeVauleInbound;
    @FindBy(xpath = "//div[text()='Complete' and @role]")
    public WebElement contactDispositionVauleComplete;
    @FindBy(xpath = "//*[contains(text(),'Anonymous')]")
    public WebElement callerTypeVauleAnonymous;

    //input[@value='Anonymous']
    //a[text()=' Close ']
    //button//span[text()='CANCEL ']
//button//span[contains(text(),'search')] //rework

    @FindBy (xpath="//*[@class='row mdl-color--grey-50 mt-4 mx-0 mx-shadow--1dp']")
    public WebElement unidentifiedContactSection;

    @FindBy(linkText = "THIRD PARTY CONTACT")
    public WebElement lnkThirdPartyContactRecordType;

    @FindBy(xpath = "//input[@name='contactType']/ancestor::div[1]")
    public WebElement contactTypeDropDown;

    @FindBy(id="contactPhone")
    public WebElement phoneNumber;

    @FindBy(xpath = "//input[@id='contactDispositions']/ancestor::div[1]")
    public WebElement contactDispositions;

    @FindBy(xpath = "//h1[text()='CONTACT DETAILS']/../..//button[1]")
    public WebElement saveButton;

    @FindBy(id="contactEmail")
    public WebElement email;

    @FindBy(xpath = "//span[contains(text(),'EDIT CONTACT')]")
    public List<WebElement> editBtn;

    @FindBy(xpath = "//input[@id='reasonForEdit']/..")
    public WebElement reasonForEditDropDown;

    @FindBy(xpath = "//span[contains(text(),'SAVE')]")
    public WebElement saveBtn;

    @FindBy(xpath = "//h5[text()='UNIDENTIFIED - CONSUMER IN CONTACT']")
    public WebElement unidentifiedHeader;

    @FindBy(xpath = "//h5[text()='CONTACT DETAILS']")
    public WebElement contactDetailsHeader;

    @FindBy(xpath = "//h5[text()='WRAP-UP AND CLOSE']")
    public WebElement wrapUpAndCloseHeader;

    @FindBy(xpath = "//h5[text()='CONTACT EDIT REASONS']")
    public WebElement contactEditReasonHeader;

    @FindBy(xpath = "//span[text()='CONTACT DETAILS']")
    public WebElement contactDetailsTab;

    @FindBy(xpath = "//span[text()='EDIT HISTORY']")
    public WebElement editHistoryTab;

}
