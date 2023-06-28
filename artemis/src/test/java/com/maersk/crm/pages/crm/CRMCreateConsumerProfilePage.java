package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.testng.Assert.assertTrue;

public class CRMCreateConsumerProfilePage extends CRMUtilities implements ApiBase {


    

    public CRMCreateConsumerProfilePage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//span[contains(text(), 'add')]")
    public WebElement addConsumerButton;

    @FindBy(xpath = "//span[(text()= 'ADD CONSUMER')]")
    public WebElement addConsumerButtonNew;

    @FindBy(name = "consumerFirstName")
    public WebElement consumerFN;

    @FindBy(name = "consumerMiddleName")
    public WebElement consumerMI;

    @FindBy(name = "consumerLastName")
    public WebElement consumerLN;

    //@FindBy(name = "consumerUniqueId")
    @FindBy(xpath = "//*[@name='consumerIdentificationNumber']")
    public WebElement consumerUniqueId;

    @FindBy(id = "consumerDateOfBirth")
    public WebElement consumerDB;

    @FindBy(id = "consumerSSN")
    public WebElement consumerSSN;

    @FindBy(id = "phone")
    public WebElement consumerPhoneNum;

    @FindBy(id = "email")
    public WebElement consumerEmail;

    @FindBy(xpath = "//*[@name='genderCode']/..")
    public WebElement consumerGender;

    @FindBy(xpath = "//*[@name='correspondencePreference']/..")
    public WebElement consumerCorrespondencePref;

    @FindBy(xpath = "//*[@name='spokenLanguage']/..")
    public WebElement consumerEditPreflang;

    @FindBy(xpath = "//*[@name='correspondencePreference']//..")
    public WebElement consumerEditCorrespondencePref;

    @FindBy(xpath = "//*[@name='correspondencePreference']/../*[1] ")
    public WebElement correspondencePrefSelect;

    @FindBy(xpath = "//ul/li[contains(text(), 'Paperless')]")
    public WebElement correspondencePaperlessValue;

    @FindBy(xpath = "//*[@data-value='CORRESPONDENCE_PAPERLESS']")
    public WebElement correspondencePrefNonSelect;

    @FindBy(xpath = "//div[@class='MuiPopover-root']")
    public WebElement correspondencePrefOut;

    @FindBy(xpath = "(//div[@class='MuiSelect-root MuiSelect-select MuiSelect-selectMenu MuiInputBase-input MuiInput-input'])[5]")
    public WebElement consumerEditCorrespondencePrefCaseMember;

    @FindBy(xpath = "//input[@value='Phone']/..")
    public WebElement commOptPhone;

    @FindBy(xpath = "//input[@value='Text']/..")
    public WebElement commOptText;

    @FindBy(xpath = "//input[@value='Email']/..")
    public WebElement commOptEmail;

    @FindBy(xpath = "//input[@value='Fax']/..")
    public WebElement commOptFax;

    @FindBy(xpath = "//input[@value='Mail']/..")
    public WebElement commOptMail;

    @FindBy(xpath = "//input[@name='addressType']/..")
    public WebElement consumerAddressType;

    @FindBy(name = "addressStreet1")
    public WebElement consumerAddress1;

    @FindBy(name = "addressStreet2")
    public WebElement consumerAddress2;

    @FindBy(name = "addressCity")
    public WebElement consumerCity;

    @FindBy(xpath = "(//*[@name ='addressCity'])/..")
    public WebElement consumerCityDropdown;

    @FindBy(xpath = "(//*[@name='addressState'])/..")
    public WebElement consumerState;

    @FindBy(xpath = "(//*[@name='addressState'])/..")
    public WebElement consumerStateField;

    @FindBy(id = "addressCounty")
    public WebElement consumerCounty;

    @FindBy(xpath = "//*[@name='addressCounty']/..")
    public WebElement consumerCountyField;

    @FindBy(id = "mui-component-select-addressState")
    public WebElement defaltConsumerStateValue;

    @FindBy(id = "addressZip")
    public WebElement consumerZipCode;

    @FindBy(xpath = "//*[contains(text(), 'close')]")
    public WebElement cancelConsumerButton;

    //    @FindBy(xpath = "(//button[contains(@class ,'mx-btn-border mx-btn-primary mdl-shadow--2dp mr-2')])[1]")
    @FindBy(xpath = "//span[contains(text(),'Create Consumer')]")
    public WebElement createConsumerButton;

    @FindBy(xpath = "(//button[contains(@class ,'mx-btn-border mx-btn-primary mdl-shadow--2dp mr-2')])[2]")
    public WebElement createCaseButton;

    @FindBy(xpath = "//span[contains(text(), 'Please fill in the required fields.')]")
    public WebElement enterValueMainWarning;
    //ref by shruti
    @FindBy(xpath = "(//input[@name='email']//parent::div//following-sibling::p)")
    public WebElement fillOutErrorEmail;
    // Mandatory field
    // By EmailMandatory = By.xpath("//input[@name='consumerEmail' and @required]");
    @FindBy(xpath = "(//input[@name='consumerEmail' and @required])")
    public WebElement mandatoryEmail;
    //Ref by Shruti
    //   @FindBy(xpath = "//p[contains(text(), 'Please enter a valid Zip Code of either 5 or 9 digits')]")
    @FindBy(xpath = "//p[contains(text(), 'ZIP CODE must be 5 or 9 characters')]")
    public WebElement fillOutErrorZipCode;

    @FindBy(xpath = "//p[.='PHONE must be 10 characters']")
    public WebElement invalidPhoneError;

    @FindBy(xpath = "//p[.='ZIP CODE must be 5 or 9 characters']")
    public WebElement invalidZipCodeError;

    @FindBy(xpath = "//span[contains(text(),'Please fill in the required fields.')]")
    public WebElement fillOutErrorHeaderMandatory;

    @FindBy(xpath = "//span[contains(text(),'Please fill in the required fields.')]")
    public WebElement fillOutErrorHeaderOptional;

    @FindBy(xpath = "//p[contains(text(),'Invalid date format')]")
    public WebElement invalidDobError;

    @FindBy(xpath = "//p[contains(text(),'SSN must be 9 characters')]")
    public WebElement invalidSsnError;

    @FindBy(xpath = "//p[contains(text(),'EMAIL is not in the correct format')]")
    public WebElement invalidEmailError;

    @FindBy(xpath = "//input[@name='contactReason']/following::p[contains(text(),'Fill out all Required Fields')]")
    public WebElement contactReasonError;

    @FindBy(xpath = "//input[@name='contactAction']/following::p[contains(text(),'Fill out all Required Fields')]")
    public WebElement contactActionError;

    @FindBy(xpath = "//textarea[@name='comments']/following::p[contains(text(),'Fill out the Required Field')]")
    public WebElement additionalCommentsError;

    //@FindBy(xpath = "//label[contains(text(), 'Consumer First Name')]")
    ////label[@id='consumerFirstName-label']
    @FindBy(xpath = "//label[@for='consumerFirstName']")
    public WebElement firstNameLabel;

    //@FindBy(xpath = "//label[contains(text(), 'Consumer Last Name')]")
    //id=consumerLastName-label
    @FindBy(xpath = "//label[@for='consumerLastName']")
    public WebElement lastNameLabel;

    @FindBy(xpath = "//label[contains(text(), 'Phone Number')]")
    public WebElement phoneNumberLabel;

    @FindBy(xpath = "//label[contains(text(), 'Zip Code')]")
    public WebElement zipCodeLabel;

    @FindBy(xpath = "//*[contains(text(),'Consumer already exists. Please associate existing Consumer to Case.')]")
    public WebElement duplicateConsumererrorMessage;

    @FindBy(xpath = "//h5[contains(text(), 'CREATE CONSUMER')]")
    public WebElement createConsumerHeader;

    @FindBy(xpath = "//p[text()='ADDRESS LINE 1 must contain both numeric and alphabetic characters to be valid']")
    public WebElement addressLineOneError;
    //Address Line 2 information must contain alpha-numeric characters to be valid.
    @FindBy(xpath = "//p[text()='ADDRESS LINE 2 must contain both numeric and alphabetic characters to be valid']")
    public WebElement addressLineTwoError;

    @FindBy(xpath = "//input[@name='consumerFirstName']")
    public WebElement firstNameSearch;

    @FindBy(xpath = "//input[@name='consumerLastName']")
    public WebElement lastNameSearch;

    @FindBy(xpath = "//span[text() = 'Continue']/parent::button")
    public WebElement warningMessageContinueButton;

    @FindBy(xpath = "//span[contains(text(), 'clear')]")
    public WebElement warningMessageCancelButton;

    @FindBy(xpath = "//*[@id= 'correspondencePreference']/..")
    public WebElement correspondencePreferenceDropDownText;

    @FindBy(xpath = "//*[@id='correspondencePreference']/parent::*/div")
    public WebElement correspondencePreferenceDropDown;

    @FindBy(xpath = "//*[contains(text(), 'Paperless')]")
    public WebElement corresPrefDropDownOptionPaperless;

    @FindBy(xpath = "//*[contains(text(), 'CASE / CONSUMER SEARCH')]")
    public WebElement caseConsumerSearch;

    @FindBy(xpath = "//p[contains(text(), 'If you')]")
    public WebElement cpModalWarningMessage;

    @FindBy(xpath = "//*[contains(text(), 'keyboard_backspace')]")
    public WebElement backArrow;

    @FindBy(xpath = "//h5[@class=' m-0 pt-2 mt-1 float-left']")
    public WebElement backArrowText;

    @FindBy(id = "mui-component-select-phoneType")
    public WebElement phoneType;

    @FindBy(xpath = "//*[contains(text(),'FIRST NAME is required and cannot be left blank')]")
    public WebElement fillOutErrorFName;

    @FindBy(xpath = "//*[contains(text(),'LAST NAME is required and cannot be left blank')]")
    public WebElement fillOutErrorLName;

    @FindBy(xpath = "//*[contains(text(),'TYPE is required and cannot be left blank')]")
    public WebElement selectErrorPhoneType;

    @FindBy(xpath = "//*[contains(text(),'PHONE is required and cannot be left blank')]")
    public WebElement fillOutErrorPhone2;

    @FindBy(xpath = "//*[contains(text(),'The Consumer Type is required and cannot be left blank.')]")
    public WebElement fillOutErrorConsumerType;

    @FindBy(xpath = "//*[contains(text(),'ZIP CODE is required and cannot be left blank')]")
    public WebElement fillOutErrorZipCode2;

    @FindBy(xpath = "//*[contains(text(),'ADDRESS LINE 1 is required and cannot be left blank')]")
    public WebElement fillOutErrorAddressLine1;

    @FindBy(xpath = "//*[contains(text(),'EMAIL is required and cannot be left blank')]")
    public WebElement fillOutErrorEmail2;

    @FindBy(xpath = "//*[contains(text(),'DOB  is required and cannot be left blank')]")
    public WebElement fillOutErrorDOB;

    @FindBy(xpath = "//*[@name='consumerType']/..")
    public WebElement consumerTypeOptions;

    @FindBy(xpath = "//*[@name='agencyName']/..")
    public WebElement externalIdType;

    @FindBy(xpath = "//*[contains(text(),'EXTERNAL ID(S)')]")
    public WebElement externalIdLabel;

    @FindBy(xpath = "//*[@name='consumerIdentificationNumber']")
    public WebElement externalId;

    @FindBy(xpath = "//*[@name='preferredLanguage']/..")
    public WebElement preferredLanguage;

    @FindBy(id = "mui-component-select-spokenLanguage")
    public WebElement spokenLanguage;

    @FindBy(id = "mui-component-select-writtenLanguage")
    public WebElement writtenLanguage;

    @FindBy(xpath = "//p[@class='px-3']")
    public WebElement optInWarningMessage;

    @FindBy(xpath = "(//span[contains(text(), 'check')])[4]/../..")
    public WebElement optInWarningContinueButton;

    @FindBy(xpath = "//span[contains(text(), 'clear')]")
    public WebElement optInWarningCancelButton;

    @FindBy(id = "select-phoneType")
    public WebElement phoneTypeClick;

    @FindBy(id = "phone_type2")
    public WebElement cell;

    @FindBy(id = "mui-component-select-addressType")
    public WebElement addressType;

    @FindBy(xpath = "(//*[text()='COMMENTS'])[1]")
    public WebElement contactReasonComments;

    @FindBy(xpath = "(//div[@class='col-12 text-right px-0 mt-3']//*[contains(text(),'check')])[1]")
    public WebElement contactReasonCommentSaveBtn;

    @FindBy(xpath = "(//*[text()='COMMENTS'])[2]")
    public WebElement additionalComments;

    @FindBy(xpath = "(//div[contains(@class,'col-12 text-right px-0')]/button/span[contains(text(),'check')])[2]")
    public WebElement additionalCommentsSaveBtn;

    @FindBy(xpath = "//*[@id='notes']")
    public WebElement reasonComment;

    @FindBy(xpath = "//*[text()='REASONS']")
    public WebElement expendReasonComment;

    @FindBy(xpath = "//*[@id='additionalComments']")
    public WebElement additionalReasonComment;

    @FindBy(id = "contactPhone")
    public WebElement phoneInput;

    @FindBy(id = "mui-component-select-contactType")
    public WebElement contactTypeContactDetails;

    @FindBy(id = "inbound-call-queue")
    public WebElement inboundCallQueueDropDown;

    @FindBy(id = "mui-component-select-callCampaignReference")
    public WebElement callCampaignDropDown;

    @FindBy(id = "mui-component-select-outcomeOfContact")
    public WebElement outcomeOfContactDropDown;

    @FindBy(id = "mui-component-select-callQueueType")
    public WebElement inboundCallQueueDropDownSelection;

    @FindBy(id = "condetcontPhone")
    public WebElement phoneContactDetails;

    @FindBy(id = "mui-component-select-programTypes")
    public WebElement programTypeContactDetails;

    @FindBy(id = "mui-component-select-contactDispositions")
    public WebElement dispositionContactDetails;

    @FindBy(xpath = "(//span[text()[contains(.,'Save')]])[2]/..")
    public WebElement saveButton;

    @FindBy(xpath = "//span[text()[contains(.,'Continue')]]")
    public WebElement continueAfterSaveButton;

    @FindBy(xpath = "//p[contains(text(),'PHONE is required and cannot be left blank')]")
    public WebElement fillOutPhoneNumberError;

    @FindBy(xpath = "//*[text()='EMAIL']")
    public WebElement optInEmailText;

    @FindBy(xpath = "//*[contains(text(),'FAX')]")
    public WebElement optInFaxText;

    @FindBy(xpath = "//*[text() ='MAIL']")
    public WebElement optInMailText;
    @FindBy(xpath = "//*[contains(text(),'PHONE')]")
    public WebElement optInPhoneText;

    @FindBy(xpath = "//*[contains(text(),'TEXT')]")
    public WebElement optInTextData;

    @FindBy(css = "input:checked[value='Mail']")
    public WebElement checkedOptMail;

    @FindBy(css = "input:checked[value='Phone']")
    public WebElement checkedOptPhone;

    @FindBy(css = "input:checked[value='Email']")
    public WebElement checkedOptEmail;

    @FindBy(css = "input:checked[value='Text']")
    public WebElement checkedOptText;

    @FindBy(css = "input:checked[value='Fax']")
    public WebElement checkedOptFax;

    @FindBy(xpath = "//label[@for='consumerDateOfBirth']")
    public WebElement consumerDOBLabel;

    @FindBy(xpath = "//label[@for='spokenLanguage']")
    public WebElement spokenLanguageLabel;

    @FindBy(xpath = "//label[@for='writtenLanguage']")
    public WebElement writtenLanguageLabel;

    @FindBy(xpath = "//label[@for='consumerType']")
    public WebElement consumerTypeLabel;

    @FindBy(xpath = "//div[@id='menu-spokenLanguage']//ul//li")
    public List<WebElement> spokenLanglist;

    @FindBy(xpath = "//div[@id='menu-writtenLanguage']//ul//li")
    public List<WebElement> writtenLanglist;

    @FindBy(css = "#menu-phoneType ul li")
    public List<WebElement> phoneTypeList;

    @FindBy(css = "#menu-addressType ul li")
    public List<WebElement> addressTypeist;

    @FindBy(css = "#menu-genderCode ul li")
    public List<WebElement> genderCodeist;

    @FindBy(css = "#menu-consumerType ul li")
    public List<WebElement> consumerTypeList;

    @FindBy(css = "#menu-correspondencePreference ul li")
    public List<WebElement> correspondencePreferenceList;

    @FindBy(id = "mui-component-select-genderCode")
    public WebElement genderType;

    @FindBy(id = "mui-component-select-consumerType")
    public WebElement consumerType;

    @FindBy(id = "mui-component-select-correspondencePreference")
    public WebElement correspondencePreferenceType;

    @FindBy(xpath = "//i[text()='add']//parent::button")
    public WebElement externalIdAdd;

    @FindBy(xpath = "//div[contains(@id,'mui-component-select-extAgn')]")
    public List<WebElement> externalIdProgram;

    @FindBy(xpath = "//div[contains(@id,'menu-extAgn')]//ul/li")
    public List<WebElement> externalIdProgramList;

    @FindBy(xpath = "//span[text()='EMAIL']//preceding-sibling::span")
    public WebElement va_commOptEmail;

    @FindBy(xpath = "//span[text()='MAIL']//preceding-sibling::span")
    public WebElement va_commOptMail;

    @FindBy(xpath = "//span[text()='MAIL']//preceding-sibling::span//input")
    public WebElement va_commOptMailSelect;

    @FindBy(xpath = "//span[text()='TEXT']//preceding-sibling::span")
    public WebElement va_commOptText;

    @FindBy(xpath = "//span[text()='PHONE']//preceding-sibling::span")
    public WebElement va_commOptPhone;

    @FindBy(xpath = "//span[text()='PHONE']//preceding-sibling::span//input")
    public WebElement va_commOptPhoneSelect;

    @FindBy(xpath = "//span[text()=' CONTINUE AND ADD NEW CONSUMER']")
    public WebElement continueAddConsumer;

    public Map<String, WebElement> getLocatorsCreateConsumerForm() {
        Map<String, WebElement> locators = new HashMap<>();
        locators.put("firstName", consumerFN);
        locators.put("middleInitial", consumerMI);
        locators.put("lastName", consumerLN);
        locators.put("dateOfBirth", consumerDB);
        locators.put("ssn", consumerSSN);
        locators.put("email", consumerEmail);
        locators.put("phoneNumber", consumerPhoneNum);
        locators.put("gender", consumerGender);
        locators.put("zipCode", consumerZipCode);
        locators.put("addressType", consumerAddressType);
        locators.put("addressLine1", consumerAddress1);
        locators.put("addressLine2", consumerAddress2);
        locators.put("city", consumerCity);
        locators.put("state", consumerStateField);
        locators.put("county", consumerCountyField);
        return locators;
    }

    public void checkMandatoryFieldsErrors(String option) {

        switch (option) {
            case "firstName":
                scrollUpUsingActions(2);
                assertTrue(fillOutErrorFName.isDisplayed(), "" + option + " error message is not displayed");
                break;
            case "lastName":
                scrollUpUsingActions(2);
                assertTrue(fillOutErrorLName.isDisplayed(), "" + option + " error message is not displayed");
                break;
            case "consumerType":
                assertTrue(fillOutErrorConsumerType.isDisplayed(), "" + option + " error message is not displayed");
                break;
            case "phoneNumber":
                assertTrue(fillOutErrorPhone2.isDisplayed(), "" + option + " error message is not displayed");
                break;
            case "phoneNumberType":
                assertTrue(selectErrorPhoneType.isDisplayed(), "" + option + " error message is not displayed");
                break;
            case "zipCode":
                assertTrue(fillOutErrorZipCode2.isDisplayed(), "" + option + " error message is not displayed");
                break;
            case "addressLine1":
                assertTrue(fillOutErrorAddressLine1.isDisplayed(), "" + option + " error message is not displayed");
                break;
            case "email":
                assertTrue(fillOutErrorEmail2.isDisplayed(), "" + option + " error message is not displayed");
                break;
            case "DOB":
                assertTrue(fillOutErrorDOB.isDisplayed(), "" + option + " error message is not displayed");
                // failing due to CP-13084
                break;
        }
    }

    public void populateOneRelatedField(String option) {
        switch (option) {
            case "phoneNumber":
                clearAndFillText(consumerPhoneNum, getRandomNumber(10));
                break;
            case "phoneNumberType":
                selectRandomDropDownOption(phoneType);
                break;
            case "externalIdType":
                selectRandomDropDownOption(externalIdType);
                break;
            case "externalId":
                clearAndFillText(externalId, getRandomNumber(5) + getRandomString(2));
                break;
            case "addressType":
                selectDropDown(consumerAddressType, "Mailing");
                break;
            case "addressLine1":
                clearAndFillText(consumerAddress1, getRandomNumber(2) + " " + getRandomString(6) + " Ct.");
                break;
            case "city":
                clearAndFillText(consumerCity, getRandomString(7) + " City");
                break;
            case "county":
                selectRandomDropDownOption(consumerCountyField);
                break;
            case "state":
                selectRandomDropDownOption(consumerState);
                break;
            case "zip":
                clearAndFillText(consumerZipCode, getRandomNumber(5));
                break;
            case "email":
                clearAndFillText(consumerEmail, randomEmailId());
                break;
        }
    }

    public void createConsumerWith(String option) {
        switch (option) {
            case "Address":
                populateOneRelatedField("addressType");
                populateOneRelatedField("addressLine1");
                populateOneRelatedField("city");
                populateOneRelatedField("county");
                populateOneRelatedField("state");
                populateOneRelatedField("zip");
                break;
            case "Phone":
                populateOneRelatedField("phoneNumber");
                populateOneRelatedField("phoneNumberType");
                break;
            case "Email":
                populateOneRelatedField("email");
                break;
        }
        click(createConsumerButton);
    }

    public boolean checkIfFieldIsDisplayed(String option) {
        boolean verified = false;
        switch (option) {
            case "PreferredLanguage":
                verified = preferredLanguage.isDisplayed();
                break;
            case "ConsumerIdType":
                verified = externalIdType.isDisplayed();
                break;
            case "ConsumerType":
                verified = consumerTypeOptions.isDisplayed();
                break;
            case "PhoneNumberType":
                verified = phoneType.isDisplayed();
                break;
            case "ExternalId":
                verified = externalIdLabel.isDisplayed();
                break;
            case "FirstName":
                verified = consumerFN.isDisplayed();
                break;
            case "MiddleInitial":
                verified = consumerMI.isDisplayed();
                break;
            case "LastName":
                verified = consumerLN.isDisplayed();
                break;
            case "SpokenLanguage":
                verified = spokenLanguage.isDisplayed();
                break;
            case "WrittenLanguage":
                verified = writtenLanguage.isDisplayed();
                break;
            case "CorrespondencePreference(s)":
                verified = correspondencePreferenceDropDown.isDisplayed();
                break;
        }
        return verified;
    }


    public boolean fieldHasDefaultValue(String field, String value) {
        boolean fieldHas = false;
        switch (field) {
            case "PreferredLanguage":
                fieldHas = preferredLanguage.getText().equals(value);
                break;
            case "ConsumerType":
                fieldHas = consumerTypeOptions.getText().equals(value);
                break;
        }
        return fieldHas;
    }

    public void selectOptInOutOption(String channel) {
        waitFor(1);
        commOptMail.click();
        waitFor(1);
        commOptPhone.click();
        waitFor(1);
        WebElement el = Driver.getDriver().findElement(By.xpath("//input[@value='" + channel + "']/.."));
        el.click();
    }

    public void verifyOptInOutWarning(String activeChannel) {
        String expectedWarningMessage = "Consumer does not have an Active " + activeChannel;
        System.out.println(expectedWarningMessage);
        waitForVisibility(optInWarningMessage, 10);
        System.out.println(optInWarningMessage.getText());
        assertTrue(optInWarningMessage.isDisplayed(), "Warning message is missing");
        assertTrue(optInWarningMessage.getText().equals(expectedWarningMessage), "Warning message is not related to " + activeChannel);
    }

    public WebElement selectOption(String option) {
        return Driver.getDriver().findElement(By.xpath("//li[text()='" + option + "']"));
    }

    @FindBy(xpath = "//input[contains(@id,'extCIN')]")
    public List<WebElement> externalId_ConsumerId;

    @FindBy(xpath = "//i[text()='delete']")
    public List<WebElement> external_id_Delete;

    @FindBy(xpath = "//*[text()='PROGRAM']")
    public WebElement lblProgram;

    @FindBy(xpath = "//*[text()='CONSUMER ID']")
    public WebElement lblConsumberID;

    @FindBy(id = "addressCounty")
    public WebElement addressCountyText;

    @FindBy(xpath = "//span[text()='EDIT']")
    public WebElement consumerEdit;

    @FindBy(id = "effectiveStartDate")
    public WebElement consumerStartDate;

    public WebElement getMandatoryFieldError(String field) {
        return Driver.getDriver().findElement(By.xpath("//p[text()='" + field + " is required and cannot be left blank']"));
    }

    @FindBy(xpath = "(//span[contains(text(),'check')])[1]")
    public WebElement SaveButton;

    @FindBy(xpath = "//*[contains(text(), 'Consumer Profile successfully updated')]")
    public WebElement consumerUpdateSuccessMessage;

    @FindBy(xpath = "//h1[text()='LINKS']//parent::div//following-sibling::div//table/tbody")
    public WebElement consumerLinkedActivecontact;

    @FindBy(xpath = "//button[@aria-label='Close']")
    public WebElement closeSuccessfulAlert;

    @FindBy(id = "effectiveEndDate")
    public WebElement consumerPhoneEndDate;
    @FindBy(id="effectiveEndDate")
    public WebElement consumerEndDate;

//    @FindBy(id="primaryIndicator")
//    public WebElement newPrimaryConsumerPhone;

    @FindBy(id = "phoneNumber")
    public WebElement newPrimaryConsumerphoneNumber;

    @FindBy(xpath = "//h6[contains(text(),'CONSUMER ID:')]/a")
    public WebElement consumerID;

    @FindBy(xpath = "//p[contains(text(),'This Consumer already has a Primary Phone. Clicking Continue will make this Phone Number the new Primary')]")
    public WebElement newPhoneNumWarningMessage;

    @FindBy(xpath="//p[contains(text(),'This Consumer already has a Primary Email. Clicking Continue will make this Email the new Primary')]")
    public WebElement newEmailWarningMessage;

    @FindBy(xpath="//span[contains(text(),'Potential matches found on External Consumer ID/SSN/First Name + Last Name + DOB')]")
    public WebElement potentialMatchWarningMessage;

    @FindBy(xpath = "//h5[text()='PHONE NUMBER']//following-sibling::div//table/tbody/tr[1]")
    public WebElement editPhoneNum;

    @FindBy(xpath = "//h5[text()='PHONE NUMBER']//following-sibling::div//table/tbody/tr[3]")
    public WebElement editFirstPhoneNum;

    @FindBy(xpath="//h5[text()='EMAIL']//following-sibling::div//table/tbody/tr[1]")
    public WebElement editEmail;

    @FindBy(xpath="//h5[text()='EMAIL']//following-sibling::div//table/tbody/tr[3]")
    public WebElement editFirstEmail;

    @FindBy(xpath = "//tbody/tr[1]/td[2]")
    public WebElement existingExternalId;

    @FindBy(xpath = "//label[@for='consumerSSN']")
    public WebElement ssnLabel;

    @FindBy(xpath="//span[text()='DO NOT CALL']")
    public WebElement doNotCall;

    @FindBy(id="doNotCall")
    public WebElement doNotCallCheckBox;

    @FindBy(xpath="//h6[text()='INACTIVE']")
    public WebElement InactiveStatus;

    @FindBy(xpath="(//*[@name ='addressSource'])/..")
    public WebElement addressSourceDropdown;

    @FindBy(xpath="(//*[@for ='addressSource'])/..")
    public WebElement addressSource;

    @FindBy(css =".MuiFormControlLabel-root.mx-checkbox-label.lead.py-4.pr-5  ")
    public List<WebElement> optInOptions;

    @FindBy(xpath="//*[@id ='addressSource']")
    public WebElement addressSourceOnCreateConsumer;

    @FindBy(xpath="//*[contains(text(), 'ETHNICITY')]")
    public WebElement ethnicityConsumerNotONaCase;

    @FindBy(id="mui-component-select-ethnicityCode")
    public WebElement ethnicityDropdown;

    @FindBy(xpath="//div[@class='MuiPaper-root MuiMenu-paper MuiPopover-paper MuiPaper-elevation8 MuiPaper-rounded']//ul//li")
    public List<WebElement> ethnicityList;

    @FindBy(xpath="//*[contains(text(), 'RACE')]")
    public WebElement raceConsumerNotONaCase;

    @FindBy(id="mui-component-select-raceCode")
    public WebElement raceDropdown;

    @FindBy(xpath="//div[@id='menu-raceCode']//ul//li")
    public List<WebElement> raceList;

    @FindBy(xpath="//*[contains(text(), 'CITIZENSHIP')]")
    public WebElement citizenshipConsumerNotONaCase;

    @FindBy(id="mui-component-select-citizenship")
    public WebElement citizenshipDropdown;

    @FindBy(xpath="//div[@id='menu-citizenship']//ul//li")
    public List<WebElement> citizenshipList;

    @FindBy(id="mui-component-select-usResidentStatusCode")
    public WebElement residencyDropdown;

    @FindBy(xpath="//*[@id=\"menu-usResidentStatusCode\"]/div[3]/ul/li")
    public List<WebElement> residencyList;

    @FindBy(xpath="//*[contains(text(), 'AMERICAN INDIAN or ALASKA NATIVE')]")
    public WebElement AmericanIndianorAlaskaNativeCheckBox;

    @FindBy(xpath="//td[text() = '']")
    public WebElement consumerTableText;

    public WebElement consumerTable(String text){
        WebElement consumerTableText = Driver.getDriver().findElement(By.xpath("//td[text() = '"+text+"']"));
        return consumerTableText;
    }
    @FindBy(xpath="//tbody[@class = 'MuiTableBody-root']/tr/td")
    public List <WebElement> consumerTableRow;

    @FindBy(xpath="//*[@data-value = 'MEDICAID']")
    public WebElement externalIdDropdownMedicAIDvalue;

    @FindBy(xpath="//*[text()='MEDICAID']")
    public WebElement externalIdDropdownMEDICAIDvalueTxt;

    @FindBy(xpath="//td[contains(@id, 'acid-td1')]")
    public List <WebElement> externalIdDropdown;

    @FindBy(xpath="//input[contains(@id, 'extC')]")
    public List <WebElement> externalIdInputField;

    @FindBy(xpath="//tr[contains(@id, 'acid')]")
    public WebElement externalIdInFirsRowInEditPage;

    @FindBy(xpath="//td[contains(@id, 'acid-td3')]")
    public WebElement littleTrashBucket;

    @FindBy(xpath="//*[@data-value = 'CHIP']")
    public WebElement externalIdDropdownCHIPvalue;

    @FindBy(xpath="//*[text()='CHIP']")
    public WebElement externalIdDropdownCHIPvalueTxt;

    @FindBy(xpath="//li[@data-value]")
    public List <WebElement> sateDropdownValueListINEB;

    @FindBy(xpath="//*[contains(text(), 'PHONE SOURCE')]")
    public WebElement phoneSource;

    @FindBy(xpath="//*[contains(text(), 'State Reported')]")
    public WebElement stateReportedSource;

    @FindBy(id="mui-component-select-phoneSource")
    public WebElement phoneSourceValue;

    @FindBy(xpath="//*[text()='CONSUMER DETAILS']")
    public WebElement consumerDetalsLableHeader;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[1]//tr[*]//td[6]")
    })
    public List<WebElement> phoneSourceType;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[1]//tr[*]//td[8]")
    })
    public List<WebElement> phoneSourceStatus;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mx-table table mt-4 mb-0 mdl-color--grey-50')])[1]//tr[*]//td[5]")
    })
    public List<WebElement> typePhone;

    @FindBy(xpath="(//span[@id='client-snackbar'])[2]")
    public WebElement popUperrorMessageInCreateConsumerPage;

    @FindBy(xpath="//thead[@class = 'MuiTableHead-root']/tr/th/span[@role ='button']")
    public List<WebElement> searchResultTableHeader;

    @FindBy(xpath="//table[@class = 'mdl-data-table mdl-js-data-table mt-2 table']/thead/tr/td")
    public List<WebElement> associatedCaseHeader;

    @FindBy(xpath="//button/i[text() = 'add']")
    public List<WebElement> useThisConsumerButton;

    @FindBy(xpath= "//label[@for='genderCode']")
    public WebElement genderLabel;

    @FindBy(xpath= "//button[@aria-label='Toggle Visibility'] ")
    public WebElement dobVisibYeyButton;

    @FindBy(xpath= "(//*[text()='Consumer'])[2]")
    public WebElement consumerTypeDropDValue;
   }