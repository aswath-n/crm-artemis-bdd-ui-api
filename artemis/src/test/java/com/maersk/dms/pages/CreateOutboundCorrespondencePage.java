package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class CreateOutboundCorrespondencePage {

    

    public CreateOutboundCorrespondencePage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//span[@title='Menu Icon']")
    public WebElement menuIconDropDown;

    @FindBy(xpath = "//span[text()='CREATE CORRESPONDENCE']")
    public WebElement menuIconCreateCorrOpt;

    @FindBy(xpath = "//h5[contains(text(),'CREATE')]")
    public WebElement createCorrHeader;

    @FindBy(xpath = "//div[@id='mui-component-select-type']")
    public WebElement type;

    @FindBy(id = "_TYPE")
    public WebElement typeDropdown;

    @FindBy(xpath = "//div[@role='presentation']/div/ul/li")
    public WebElement presentationOpt;

    @FindBy(xpath = "//div[@id='mui-component-select-language']")
    public WebElement language;

    @FindBy(xpath = "//li[text()='English']")
    public WebElement languageEnglishOpt;

    @FindBy(xpath = "//ul[@class='mx-send-checkbox px-1 my-2']/li/label/span")
    public WebElement firstIndividual;

    @FindBy(xpath = "//ul[@class='mx-send-checkbox px-1 my-2']/li/label/span/span/input/parent::span")
    public List<WebElement> listIndividuals;

    @FindBy(xpath = "//input[@id='checkFax']/parent::span")
    public WebElement checkBoxFax;

    @FindBy(xpath = "//div[@id='mui-component-select-faxDestination']")
    public WebElement faxDestination;

    @FindBy(xpath = "//li[text()='Other']")
    public WebElement otherOpt;

    @FindBy(xpath = "//input[@id='faxNumber']")
    public WebElement faxNumber;

    @FindBy(xpath = "//input[@id='checkMail']/parent::span")
    public WebElement checkBoxMail;

    @FindBy(xpath = "//div[@id='mui-component-select-mailDestination']")
    public WebElement mailDestination;

    @FindBy(xpath = "//ul[@role='listbox']/li")
    public WebElement mailDestinationValue;

    @FindBy(xpath = "//input[@name='consumerAddress1']")
    public WebElement consumerAddress1;

    @FindBy(xpath = "//input[@name='consumerAddress2']")
    public WebElement consumerAddress2;

    @FindBy(xpath = "//div[@id='mui-component-select-consumerCity']")
    public WebElement consumerCity;

    @FindBy(xpath = "//div[@id='mui-component-select-consumerState']")
    public WebElement consumerState;

    @FindBy(xpath = "//div[@id='mui-component-select-zipCode']")
    public WebElement zipCode;

    @FindBy(xpath = "//span[contains(text(),'Save')]")
    public WebElement saveButton;

    @FindBy(xpath = "//span[@id='client-snackbar' and text() = 'The Correspondence Record is saved successfully.']")
    public WebElement successMessage;

    @FindBy(xpath = "//span[@id='client-snackbar' and text() = 'Correspondence Definition successfully updated.']")
    public WebElement updateSuccessMessage;

    @FindBy(xpath = "//p[text()='Case Id']/parent::div/h6")
    public WebElement caseID;

    @FindBy(xpath = "//p[text()='Consumer Id']/parent::div/h6")
    public WebElement consumerID;

    @FindBy(xpath = "//*[@class='css-19bqh2r']")
    public WebElement dropdownArrowDown;

    @FindBy(xpath = "(//*[contains(text(), 'CONSUMER')]/../div/div)[2]//div/div")
    public List<WebElement> dropdownListOfConsumers;

    @FindBy(xpath = "//ul[@class='mx-send-checkbox px-1 my-2']/li[2]")
    public WebElement fullName;

    @FindBy(xpath = "//ul[@class='mx-send-checkbox px-1 my-2']/li[3]")
    public WebElement role;

    @FindBy(xpath = "//input[@id='checkEmail']/parent::span")
    public WebElement checkBoxEmail;

    @FindBy(xpath = "//div[@id='mui-component-select-emailDestination']")
    public WebElement emailDestination;

    @FindBy(xpath = "//input[@id='emailAddress']")
    public WebElement emailAddress;

    @FindBy(id = "_TYPE-label")
    public WebElement typeLabel;

    @FindBy(id = "_LANGUAGE-label")
    public WebElement languageLabel;

    @FindBy(xpath = "//*[contains(text(),'REGARDING')]")
    public WebElement regardingLabel;

    @FindBy(xpath = "//*[contains(text(),'CREATE OUTBOUND CORRESPONDENCE REQUEST')]")
    public WebElement headerLabel;

    @FindBy(xpath = "//*[contains(text(),'USE DEFAULT 'SEND TO' SETTING DETERMINED AT FULFILLMENT TIME')]")
    public WebElement sendToLabel;

    @FindBy(xpath = "//span[@title='Inactive Consumer']")
    public WebElement inactiveConsumer;

    @FindBy(xpath = "(//*[contains(text(),'SEND TO')]/../following-sibling::div/div/following-sibling::div/div/ul/li/label/span/span/input)[1]")
    public WebElement firstRecipient;

    @FindBy(xpath = "(//*[@id='checkMail'])[1]")
    public WebElement firstChannel;

    @FindBy(xpath = "(//*[contains(text(),'DESTINATION')]/following-sibling::div)[1]")
    public WebElement firstDestination;

    @FindBy(xpath = "(//*[contains(text(),'DESTINATION')]/../following-sibling::div/div/div/input)[1]")
    public WebElement firstAddressLine1;

    @FindBy(xpath = "(//*[@id='mui-component-select-consumerCity'])[1]")
    public WebElement firstCity;

    @FindBy(xpath = "(//*[@id='mui-component-select-consumerState'])[1]")
    public WebElement firstState;

    @FindBy(xpath = "(//*[@id='(//*[@id='mui-component-select-zipCode'])[1]")
    public WebElement firstzip;

    // @Keerthi  @Body data elements locators
    @FindBy(xpath = "(//input[@class='MuiInputBase-input MuiInput-input MuiAutocomplete-input MuiAutocomplete-inputFocused MuiInputBase-inputAdornedEnd'])[1]")
    public WebElement type_textbox;

    @FindBy(xpath = "//*[@class='mx-color-text-primary mx-heading-ul pb-2 float-left']")
    public WebElement bodydataelemntsheading;

    @FindBy(xpath = "//div[@class='row mb-4']")
    public WebElement bodydatatable;

    @FindBy(xpath = "//div[@class='row mb-4']/div[1]//input")
    public WebElement reqshorttextfield;

    @FindBy(xpath = "//div[@class='row mb-4']/div[2]//input")
    public WebElement reqnumfield;

    @FindBy(xpath = "//div[@class='row mb-4']/div[3]//input")
    public WebElement reqdatefield;

    @FindBy(xpath = "//div[@class='row mb-4']/div[4]//input")
    public WebElement reqcbfield;

    @FindBy(xpath = "//div[@class='row mb-4']/div[5]//input")
    public WebElement reqlongtextfield;

    @FindBy(xpath = "//button/i[@class='material-icons']")
    public WebElement mutiplieinstanceicons;

    @FindBy(xpath = "//span[contains(text(),'Cancel')]")
    public WebElement CancelButton;

    @FindBy(xpath = "(//span[contains(text(),'check')])[2]")
    public WebElement warningMessage_Continue;

    // @Keerthi  @sendTo elements locators

    @FindBy(xpath = "//span[text()='THIRD PARTY CONTACT']")
    public WebElement thirdpartycontact;

    @FindBy(xpath = "(//input[@id='consumerFirstName'])[1]")
    public WebElement thirdpartycontact_firstname;

    @FindBy(xpath = "(//input[@id='consumerLastName'])[1]")
    public WebElement thirdpartycontact_lastname;

    @FindBy(xpath = "//h5[contains(text(),'SEND TO')]")
    public WebElement sendtolabel;

    @FindBy(xpath = "//input[@id='firstName']")
    public WebElement sendto_firstname;

    @FindBy(xpath = "//input[@id='lastName']")
    public WebElement sendto_lastname;

    @FindBy(xpath = "//input[@id='checkMail']")
    public WebElement sendto_checkmail;

    @FindBy(xpath = "//input[@id='consumerAddress1']")
    public WebElement sendto_addressline1;

    @FindBy(xpath = "//input[@id='consumerAddress2']")
    public WebElement sendto_addressline2;

    @FindBy(xpath = "//input[@id='consumer-city']")
    public WebElement sendto_city_dropdown;

    @FindBy(xpath = "//div[@id='mui-component-select-consumerState']")
    public WebElement sendto_state_dropdown;

    @FindBy(xpath = "//input[@id='zipCode']")
    public WebElement sendto_zipcode_dropdown;

    @FindBy(xpath = "//p[@id='consumerAddress1-helper-text']")
    public WebElement addressline1_errormessage;

    @FindBy(xpath = "//p[contains(text(),'CITY is required and cannot be left blank')]")
    public WebElement city_errormessage;

    @FindBy(xpath = "//p[contains(text(),'STATE is required and cannot be left blank')]")
    public WebElement state_errormessage;

    @FindBy(xpath = "//p[contains(text(),'ZIP CODE is required and cannot be left blank')]")
    public WebElement zipcode_errormessage;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/following-sibling::div[2]//tbody/tr/td[2]")
    public WebElement firstResultFromConsumerSearch;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/following-sibling::div[2]//tbody/tr/td[3]")
    public WebElement firstResultFromConsumerOnlySearch;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/following-sibling::div[2]//tbody/tr/td[2]")
    public WebElement caseIdPreviouslyCreated;

    @FindBy(xpath = "//*[contains(text(),'OUTBOUND CORRESPONDENCES')]/../following-sibling::div//tbody/tr/td[2]")
    public WebElement firstOutbCidCaseContactDetailsTab;

    @FindBy(xpath = "//h6[@class='m-0 mx-wordbreak']")
    public WebElement consumer_reg;

    @FindBy(xpath = "//span[text()=' WARNING MESSAGE']")
    public WebElement warning_msg;

    @FindBy(xpath = "//span[text()='Continue']")
    public WebElement continue_button;

    @FindBy(xpath = "//h5[text()='PROFILE DETAILS']")
    public WebElement demographicinfo_profiledetails;

    @FindBy(xpath = "//div[contains(@class, 'col-12 mt-3')]")
    public List<WebElement> recipient_list;

    @FindBy(xpath = "//div[contains(@class,'mx-color-white-border')]")
    public List<WebElement> destinationtype_list;

    @FindBy(xpath = "//span[contains(text(),'MAIL')]")
    public WebElement firstmailChannel;

    @FindBy(xpath = "//div[@id='mui-component-select-mailDestination']")
    public WebElement prepopulatemailingaddress;

    @FindBy(xpath = "//li[contains(@class,'MuiButtonBase-root MuiListItem-root MuiMenuItem-root Mui')]")
    public List<WebElement> maildestinationvalue_list;

    @FindBy(xpath = "//h5[contains(@class,'mx-section-header float-left pl-1')]")
    public WebElement notificationheader;

    @FindBy(xpath = "(//td[contains(text(),'Contact Record')]/preceding-sibling::td)[2]")
    public WebElement contactRecordLinkID;

    @FindBy(xpath = "//td[contains(text(),'Contact Record')]")
    public WebElement contactRecordLinkName;

    @FindBy(xpath = "//input[@id='sendTo']")
    public WebElement sendToCheckBox;

    @FindBy(xpath = "(//tbody[@class='MuiTableBody-root']/tr/td)[2]")
    public WebElement firstCaseIDFromSearch;

    @FindBy(xpath = "//ul[@id='_TYPE-popup']/li")
    public List<WebElement> typeDropDownValues;

    @FindBy(xpath = "//p[contains(text(), 'TYPE is required')]")
    public WebElement typeWarningMessage;

    @FindBy(id = "_LANGUAGE")
    public WebElement languageDropdown;

    @FindBy(xpath = "//ul[@id='_LANGUAGE-popup']/li")
    public List<WebElement> languageDropdownValues;

    @FindBy(xpath = "//span[contains(text(), 'At least one Consumer is required')]")
    public WebElement consumerIsRequiredWarningMessage;

    @FindBy(xpath = "//h5[text()='SEND TO']/../..//div/ul/li//span/input/following-sibling::*")
    public List<WebElement> listOfRecipientsCheckboxes;

    @FindBy(xpath = "//h5[text()='SEND TO']/../..//div/ul/li//span[contains(@class, 'MuiTypography-root MuiFormControlLabel-label')]")
    public List<WebElement> listOfRecipientsIDs;

    @FindBy(xpath = "//h5[text()='SEND TO']/../..//div/ul/li//span[contains(@class, 'MuiTypography-root MuiFormControlLabel-label')]/../../following-sibling::*[1]")
    public List<WebElement> listOfRecipientsNames;

    @FindBy(xpath = "//h5[text()='SEND TO']/../..//div/ul/li//span[contains(@class, 'MuiTypography-root MuiFormControlLabel-label')]/../../following-sibling::*[2]")
    public List<WebElement> listOfRecipientsRoles;

    @FindBy(xpath = "//span[contains(text(), 'At least one Recipient')]")
    public WebElement atLeastOneRecipientWarningMessage;

    @FindBy(xpath = "//h5[text()='SEND TO']/../..//div/div[@class='collapse show']/div//span[contains(@class, 'MuiButtonBase-root MuiIconButton-root')]")
    public List<WebElement> listOfChannelsCheckBoxes;

    @FindBy(xpath = "//h5[text()='SEND TO']/../..//div/div[@class='collapse show']/div//span[contains(@class, 'MuiTypography-root MuiFormControlLabel-label')]")
    public List<WebElement> listOfChannels;

    @FindBy(xpath = "//*[text()='DESTINATION']")
    public List<WebElement> listOfChannelDestinations;

    @FindBy(xpath = "//span[contains(text(), 'At least one Channel')]")
    public WebElement atLeastOneChannelWarningMessage;

    @FindBy(xpath = "//p[contains(text(), 'DESTINATION is required')]")
    public WebElement destinationIsRequiredWarningMessage;

    @FindBy(xpath = "(//div[@class=' mx-header__userinfo']//p)[2]")
    public WebElement headerUserID;

    @FindBy(xpath = "//span[text()='keyboard_backspace']")
    public WebElement backArrow;

    @FindBy(xpath = "(//span[contains(text(),'clear')])[2]")
    public WebElement warningMessageCancel;

    @FindBy(xpath = "//*[text()='CONSUMER(S)']")
    public WebElement consumerDropdown;

    @FindBy(xpath = "//span[@class='MuiChip-label']")
    public List<WebElement> listOfSelectedConsumers;

    @FindBy(xpath = "//span[@class='css-bgvzuu-indicatorSeparator']/../div/following-sibling::div")
    public WebElement consumerDropdownArrowDown;

    @FindBy(xpath = "//*[@class='MuiCircularProgress-svg']/..")
    public WebElement loadingIndicator;

    @FindBy(xpath = "//h5[text()='ACTIVE CONTACT']")
    public WebElement activeContactScreen;

    @FindBy(xpath = "//li[contains(text(),'Priority')]")
    public WebElement priorityDashboard;

    @FindBy(xpath = "(//span[contains(text(),'check')])[1]")
    public WebElement saveInboundCorres;

    @FindBy(xpath = "//h5[text()='LINKS']")
    public WebElement linksLabel;

    @FindBy(xpath = "//*[contains(text(),'APPLICATION ID')]")
    public WebElement applicationIDText;

    @FindBy(xpath = "//p[contains(text(),'APPLICATION ID')]//following-sibling::h6")
    public WebElement applicationIDValue;

    @FindBy(xpath = "//li[contains(text(),'Primary Individual')]//preceding-sibling::li[2]//span//input")
    public WebElement primaryIndividualCheckBox;

    @FindBy(xpath = "//li[contains(text(),'Authorized Rep')]//preceding-sibling::li[1]")
    public WebElement authorizedRepName;

    @FindBy(xpath = "//li[contains(text(),'Application Member')]//preceding-sibling::li[1]")
    public WebElement appMemberRepName;

    @FindBy(xpath = "//li[contains(text(),'Primary Individual')]//preceding-sibling::li[1]")
    public WebElement primaryIndividualName;

    @FindBy(xpath = "//*[contains(text(),'Application ID is required and cannot be left blank')]")
    public WebElement appIdRequiredMessage;

    @FindBy(xpath = "//*[contains(text(),' check')]")
    public WebElement validateButton;

    @FindBy(xpath = "(//span[contains(text(),'Application ID does not exist, please enter a valid Application ID')])[1]")
    public WebElement appIdInvalidMessage;

    @FindBy(id="applicationId")
    public WebElement applicationIdField;

    @FindBy(xpath="(//*[contains(text(),'CODE')])[1]")
    public WebElement codeField;

    @FindBy(xpath = "//li[contains(text(),'Primary Individual')]//preceding-sibling::li[2]")
    public WebElement primaryIndividualID;

    @FindBy(xpath = "//li[contains(text(),'Authorized Rep')]//preceding-sibling::li[2]")
    public WebElement authorizedRepID;

    @FindBy(xpath = "//li[contains(text(),'Application Member')]//preceding-sibling::li[2]")
    public WebElement applicationMemberID;

    @FindBy(xpath = "//li[contains(text(),'Authorized Rep')]//preceding-sibling::li[2]//span//input")
    public WebElement authorizedRepCheckBox;

    @FindBy(xpath = "//li[contains(text(),'Application Member')]//preceding-sibling::li[2]//span//input")
    public WebElement applicationMemberCheckBox;

    @FindBy(xpath = "//li[contains(text(),'Primary Individual')]/parent::*/parent::*//div[@id='mui-component-select-emailDestination']")
    public WebElement EmailDestinationForPrimaryInd;

    @FindBy(xpath = "//li[contains(text(),'Primary Individual')]/parent::*/parent::*//div[@id='mui-component-select-mailDestination']")
    public WebElement MailDestinationForPrimaryInd;

    @FindBy(xpath = "//li[contains(text(),'Primary Individual')]/parent::*/parent::*//div[@id='mui-component-select-faxDestination']")
    public WebElement FaxDestinationForPrimaryInd;

    @FindBy(xpath = "//li[contains(text(),'Application Member')]/parent::*/parent::*//div[@id='mui-component-select-emailDestination']")
    public WebElement EmailDestinationForAppMember;

    @FindBy(xpath = "//li[contains(text(),'Application Member')]/parent::*/parent::*//div[@id='mui-component-select-mailDestination']")
    public WebElement MailDestinationForAppMember;

    @FindBy(xpath = "//li[contains(text(),'Application Member')]/parent::*/parent::*//div[@id='mui-component-select-faxDestination']")
    public WebElement FaxDestinationForAppMember;

    @FindBy(xpath = "//li[contains(text(),'Authorized Rep')]/parent::*/parent::*//div[@id='mui-component-select-emailDestination']")
    public WebElement EmailDestinationForAuthorizedRep;

    @FindBy(xpath = "//li[contains(text(),'Authorized Rep')]/parent::*/parent::*//div[@id='mui-component-select-mailDestination']")
    public WebElement MailDestinationForAuthorizedRep;

    @FindBy(xpath = "//li[contains(text(),'Authorized Rep')]/parent::*/parent::*//div[@id='mui-component-select-faxDestination']")
    public WebElement FaxDestinationForAuthorizedRep;

    @FindBy(xpath = "//li[contains(text(),'Primary Individual')]/parent::*/parent::*//input[@id='checkEmail']/parent::span")
    public WebElement EmailCheckBoxForPrimaryInd;

    @FindBy(xpath = "//li[contains(text(),'Primary Individual')]/parent::*/parent::*//input[@id='checkMail']/parent::span")
    public WebElement MailCheckBoxForPrimaryInd;

    @FindBy(xpath = "//li[contains(text(),'Primary Individual')]/parent::*/parent::*//input[@id='checkFax']/parent::span")
    public WebElement FaxCheckBoxForPrimaryInd;

    @FindBy(xpath = "//li[contains(text(),'Application Member')]/parent::*/parent::*//input[@id='checkEmail']/parent::span")
    public WebElement EmailCheckBoxForAppMember;

    @FindBy(xpath = "//li[contains(text(),'Application Member')]/parent::*/parent::*//input[@id='checkMail']/parent::span")
    public WebElement MailCheckBoxForAppMember;

    @FindBy(xpath = "//li[contains(text(),'Application Member')]/parent::*/parent::*//input[@id='checkFax']/parent::span")
    public WebElement FaxCheckBoxForAppMember;

    @FindBy(xpath = "//li[contains(text(),'Authorized Rep')]/parent::*/parent::*//input[@id='checkEmail']/parent::span")
    public WebElement EmailCheckBoxForAuthRep;

    @FindBy(xpath = "//li[contains(text(),'Authorized Rep')]/parent::*/parent::*//input[@id='checkMail']/parent::span")
    public WebElement MailCheckBoxForAuthRep;

    @FindBy(xpath = "//li[contains(text(),'Authorized Rep')]/parent::*/parent::*//input[@id='checkFax']/parent::span")
    public WebElement FaxCheckBoxForAuthRep;

    @FindBy(xpath = "//span[contains(text(),'edit')]")
    public WebElement appIDeditButton;

    @FindBy(id = "applicationId")
    public WebElement applicationIDField;

    @FindBy(xpath = "//li[contains(text(),'Primary Individual')]/parent::*/parent::*//input[@id='checkEmail']/parent::span/..")
    public WebElement pIemailCheckBoxInputStatus;

    @FindBy(xpath = "//li[contains(text(),'Primary Individual')]/parent::*/parent::*//input[@id='checkMail']/parent::span/..")
    public WebElement pImailCheckBoxInputStatus;

    @FindBy(xpath = "//li[contains(text(),'Primary Individual')]/parent::*/parent::*//input[@id='checkFax']/parent::span/..")
    public WebElement pIfaxCheckBoxInputStatus;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/following-sibling::div[2]//tbody/tr/td[2]")
    public List<WebElement> listOfResultFromConsumerSearch;

    /**
        Use to return web element for consumer Id and Channel
     **/

    public WebElement findCheckboxElementWithConsumerIdChannelType(String consumerId , String channel){
        WebElement checkbox = Driver.getDriver().findElement(By.xpath("//span[contains(text(),'" +consumerId +"')]//ancestor::ul//following-sibling::div//input[@id='check" +channel +"']"));
        return checkbox;
    }

    public WebElement bodydataelementheading(String element){
        WebElement checkbox = Driver.getDriver().findElement(By.xpath("//*[contains(text(),'"+element+"')]"));
        return checkbox;
    }
}