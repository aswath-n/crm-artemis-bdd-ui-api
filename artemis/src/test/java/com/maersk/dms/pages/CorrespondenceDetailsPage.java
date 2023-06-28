package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CorrespondenceDetailsPage {
    

    public CorrespondenceDetailsPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//h5[contains(text(),'CORRESPONDENCE DETAILS')]")
    public WebElement correspondenceDetailsHeader;

    @FindBy(xpath = "//h5[contains(text(),'OUTBOUND')]")
    public WebElement correspondenceHeader;

    @FindBy(xpath = "//p[text()='CREATED BY']/parent::div/h6")
    public WebElement createdByID;

    @FindBy(xpath = "//p[text()='CORRESPONDENCE TYPE']/parent::div/h6")
    public WebElement correspondenceType;

    @FindBy(xpath = "//p[text()='LANGUAGE']/parent::div/h6")
    public WebElement correspondenceLanguage;

    @FindBy(xpath = "//p[text()='STATUS']/parent::div/h6")
    public WebElement correspondenceStatus;

    @FindBy(xpath = "//p[text()='CASE ID']/parent::div/h6")
    public WebElement correspondenceCaseID;

    @FindBy(xpath = "//span[text()='edit']")
    public WebElement correspondenceEditButton;

    @FindBy(xpath = "//input[@name='outboundCorrespondenceStatus']/parent::div/div")
    public WebElement outboundCorrespondenceStatus;

    @FindBy(xpath = " //input[@name='correspondenceStatusReason']/parent::div/div")
    public WebElement outboundCorrespondenceStatusReason;

    @FindBy(xpath = "//span[text()='Yes']")
    public WebElement yesPopUpMessage;

    @FindBy(xpath = "//span[text()='Continue']")
    public WebElement continuePopUpMessage;

    @FindBy(xpath = "//p[text()='CHANNEL']/parent::div/parent::div/parent::div/parent::div/parent::div/parent::div/following::div")
    public WebElement correspondenceNotificationsScreen;

    @FindBy(xpath = "//p[text()='CHANNEL']/parent::div/h6")
    public WebElement correspondenceNotificationsChannel;

    @FindBy(xpath = "//p[text()='DESTINATION']/parent::div/h6")
    public WebElement correspondenceNotificationsDestination;

    @FindBy(xpath = "//h5[text()='NOTIFICATIONS']/following::div/div/div/div/p[text()='STATUS']/parent::div/h6")
    public WebElement correspondenceNotificationsStatus;

    @FindBy(xpath = "//p[text()='REASON']/parent::div/h6")
    public WebElement correspondenceNotificationsStatusReason;

    @FindBy(xpath = "//button[@class='mdl-button mdl-js-button mdl-button--icon mx-btn-cancel']")
    public WebElement correspondenceNotificationsCancelButton;

    @FindBy(xpath = "//span[text()='CANCEL']")
    public WebElement correspondenceNotificationsCancelOpt;

    @FindBy(xpath = "//div[@id='mui-component-select-notificationStatusReason']")
    public WebElement correspondenceNotificationsStatusReasonEdit;

    @FindBy(xpath = "//h5[text()='RESEND']")
    public WebElement correspondenceNotificationsStatusResendHeader;

    @FindBy(xpath = "//h5[text()='RESEND']/parent::div/div/div/label")
    public WebElement correspondenceNotificationsResendDestinationField;

    @FindBy(xpath = "//h5[text()='RESEND']/parent::div/div/div/div/label")
    public WebElement correspondenceNotificationsResendChannelField;

    @FindBy(xpath = "//*[text()='CASE ID']/ancestor::div[2]//table/tbody/tr/td[1]/span")
    public WebElement caseIdFirstRecord;

    @FindBy(xpath = "//h6[contains(text(), 'CID')]")
    public WebElement corIdLabel;

    @FindBy(xpath = "//h6[contains(text(), 'CID')]/a")
    public WebElement corIdValue;

    @FindBy(xpath = "(//span[contains(text(), 'Save')])[1]")
    public WebElement topPortionSaveButton;

    @FindBy(xpath = "(//span[contains(text(), 'Cancel')])[1]")
    public WebElement topPortionCancelButton;

    @FindBy(xpath = "//p[text()='CREATED BY']")
    public WebElement createdByLabel;

    @FindBy(xpath = "//p[text()='CREATED BY']/../..//p[contains(text(), 'CREATED ON')]")
    public WebElement createdOnLabelTopPortion;

    @FindBy(xpath = "//p[text()='CREATED BY']/../..//p[contains(text(), 'CREATED ON')]/following-sibling::*")
    public WebElement createdOnValueTopPortion;

    @FindBy(xpath = "//p[text()='UPDATED BY']")
    public WebElement updatedByLabel;

    @FindBy(xpath = "//p[text()='UPDATED BY']/../..//p[contains(text(), 'UPDATED BY')]/following-sibling::*")
    public WebElement updatedByValue;

    @FindBy(xpath = "//p[text()='UPDATED ON']/../..//p[contains(text(), 'UPDATED ON')]")
    public WebElement updatedOnLabel;

    @FindBy(xpath = "//p[text()='UPDATED ON']/../..//p[contains(text(), 'UPDATED ON')]/following-sibling::*")
    public WebElement updatedOnValue;

    @FindBy(xpath = "(//p[text()='STATUS DATE']/../..//p[contains(text(), 'STATUS DATE')])[1]")
    public WebElement statusDateLabelTopPortion;

    @FindBy(xpath = "(//p[text()='STATUS DATE']/../..//p[contains(text(), 'STATUS DATE')]/following-sibling::*)[1]")
    public WebElement statusDateValueTopPortion;

    @FindBy(xpath = "//p[text()='RESPONSE DUE DATE']/../..//p[contains(text(), 'RESPONSE DUE DATE')]")
    public WebElement responseDueDateLabel;

    @FindBy(xpath = "//p[text()='RESPONSE DUE DATE']/../..//p[contains(text(), 'RESPONSE DUE DATE')]/following-sibling::*")
    public WebElement responseDueDateValue;

    @FindBy(xpath = "//p[text()='CORRESPONDENCE TYPE']/../..//p[contains(text(), 'CORRESPONDENCE TYPE')]")
    public WebElement corTypeLabel;

    @FindBy(xpath = "//p[text()='CORRESPONDENCE TYPE']/../..//p[contains(text(), 'CORRESPONDENCE TYPE')]/following-sibling::*")
    public WebElement corTypeValue;

    @FindBy(xpath = "(//p[text()='LANGUAGE']/../..//p[contains(text(), 'LANGUAGE')])[1]")
    public WebElement languageLabelTopPortion;

    @FindBy(xpath = "(//p[text()='LANGUAGE']/../..//p[contains(text(), 'LANGUAGE')]/following-sibling::*)[1]")
    public WebElement languageValueTopPortion;

    @FindBy(xpath = "//p[.='STATUS']/../..//p[(text()='STATUS')]")
    public List<WebElement> listOfStatusLabels;

    @FindBy(xpath = "//p[.='STATUS']/../..//p[(text()='STATUS')]/following-sibling::*")
    public List<WebElement> listOfStatusValues;

    @FindBy(xpath = "//*[@id='menu-outboundCorrespondenceStatus']//ul/li")
    public List<WebElement> statusDropDownValues;

    @FindBy(xpath = "//*[@id='menu-correspondenceStatusReason']//ul/li")
    public List<WebElement> statusReasonDropDownValues;

    @FindBy(xpath = "//p[contains(text(), 'Reason is required')]")
    public WebElement statusReasonRequiredMessage;

    @FindBy(xpath = "//p[text()='STATUS REASON']/../..//p[contains(text(), 'STATUS REASON')]")
    public WebElement statusReasonLabel;

    @FindBy(xpath = "//p[text()='STATUS REASON']/../..//p[contains(text(), 'STATUS REASON')]/following-sibling::*")
    public WebElement statusReasonValue;

    @FindBy(xpath = "//h5[contains(text(), 'REGARDING')]")
    public WebElement regardingLabel;

    @FindBy(xpath = "//p[text()='CASE ID']/../..//p[contains(text(), 'CASE ID')]")
    public WebElement caseIdLabel;

    @FindBy(xpath = "//p[text()='CASE ID']/../..//p[contains(text(), 'CASE ID')]/following-sibling::*")
    public WebElement caseIdValue;

    @FindBy(xpath = "//p[text()='CONSUMER(S)']/../..//p[contains(text(), 'CONSUMER(S)')]")
    public WebElement consumersLabel;

    @FindBy(xpath = "//p[text()='CONSUMER(S)']/../..//p[contains(text(), 'CONSUMER(S)')]/following-sibling::*")
    public List<WebElement> listOfConsumer;

    //notification part
    @FindBy(xpath = "(//h5[text()='NOTIFICATIONS']/../div[@class='col-12 mdl-color--grey-100 mx-color-white-border py-2 mt-2']/ul/li)[1]")
    public List<WebElement> listOfConsumerIdsNotifications;

    @FindBy(xpath = "(//h5[text()='NOTIFICATIONS']/../div[@class='col-12 mdl-color--grey-100 mx-color-white-border py-2 mt-2']/ul/li)[2]")
    public List<WebElement> listOfConsumerNamesNotifications;

    @FindBy(xpath = "(//h5[text()='NOTIFICATIONS']/../div[@class='col-12 mdl-color--grey-100 mx-color-white-border py-2 mt-2']/ul/li)[3]")
    public List<WebElement> listOfConsumerRolesNotifications;

    @FindBy(xpath = "//p[text()='CHANNEL']/following-sibling::*")
    public List<WebElement> listOfChannelsNotifications;

    @FindBy(xpath = "//p[text()='DESTINATION']/following-sibling::*")
    public List<WebElement> listOfDestinationsNotifications;

    @FindBy(xpath = "//p[text()='NID']/following-sibling::*")
    public List<WebElement> listOfNIDNotifications;

    @FindBy(xpath = "//p[text()='NID']/../..//p[text()='LANGUAGE']/following-sibling::*")
    public List<WebElement> listOfLanguagesNotifications;

    @FindBy(xpath = "//p[text()='TEMPLATE']/following-sibling::*")
    public List<WebElement> listOfTemplatesNotifications;

    @FindBy(xpath = "//p[text()='VERSION']/following-sibling::*")
    public List<WebElement> listOfVersionsNotifications;

    @FindBy(xpath = "//p[text()='VERSION']/../..//p[text()='CREATED ON']/following-sibling::*")
    public List<WebElement> listOfCreatedOnNotifications;

    @FindBy(xpath = "//p[text()='VERSION']/../..//p[text()='STATUS DATE']/following-sibling::*")
    public List<WebElement> listOfStatusDateNotifications;

    @FindBy(xpath = "//p[text()='VERSION']/../..//p[text()='STATUS']/following-sibling::*")
    public List<WebElement> listOfStatusNotifications;

    @FindBy(xpath = "//p[text()='VERSION']/../..//button/span[text()='STATUS HISTORY']")
    public List<WebElement> listOfStatusHistories;

    @FindBy(xpath = "//p[text()='VERSION']/../..//button/i[text()='more_vert']")
    public List<WebElement> listOfBurgerOptions;

    @FindBy(xpath = "//div[@id = 'menu-list-grow']//li/div/span")
    public List<WebElement> listOfOptionsInBurgerMenu;

    @FindBy(id = "mui-component-select-notificationStatusReason")
    public WebElement reasonDropdownNotifications;

    @FindBy(xpath = "//ul[@role='listbox']/li")
    public List<WebElement> reasonDropdownValuesNotifications;

    @FindBy(xpath = "//input[@placeholder='MM/DD/YYYY']")
    public WebElement returnDatePickerNotifications;

    @FindBy(xpath = "//h5[text()='RESEND']")
    public WebElement resendLabel;

    @FindBy(id = "mui-component-select-channelType")
    public WebElement resendChannelDropdown;

    @FindBy(id = "mui-component-select-destination")
    public WebElement resendDestinationDropdown;

    @FindBy(xpath = "//input[@placeholder='MM/DD/YYYY']")
    public WebElement sentStatusDatePickerNotifications;

    @FindBy(xpath = "//p[text()='VERSION']/../following-sibling::div/button/span[text()=' Save']")
    public WebElement saveButtonNotifications;

    @FindBy(xpath = "//p[text()='VERSION']/../following-sibling::div/button/span[text()=' Cancel']")
    public WebElement cancelButtonNotifications;

    @FindBy(xpath = "//p[text()='VERSION']/../..//p[text()='RETURN DATE']/following-sibling::*")
    public WebElement returnDateNotifications;

    @FindBy(xpath = "//p[text()='VERSION']/../..//p[text()='REASON']/following-sibling::*")
    public WebElement reasonNotifications;

    @FindBy(xpath = "//i[text()='reply']")
    public WebElement resendFlag;

    @FindBy(xpath = "(//span[text()=' Save'])[1]")
    public WebElement saveButtonForResend;

    @FindBy(xpath = "//span[text()='Yes']")
    public WebElement warningWindowYes;

    @FindBy(xpath = "(//div[@class=' mx-header__userinfo']//p)[1]")
    public WebElement navBarUsername;
}
