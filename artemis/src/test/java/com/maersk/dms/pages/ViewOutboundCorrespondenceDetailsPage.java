package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ViewOutboundCorrespondenceDetailsPage {

    

    public ViewOutboundCorrespondenceDetailsPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//span[text()='edit']")
    public WebElement correspondenceEditButton;

    @FindBy(id = "mui-component-select-outboundCorrespondenceStatus")
    public WebElement statusDropdown;

    @FindBy(id = "mui-component-select-correspondenceStatusReason")
    public WebElement statusReasonDropdown;

    @FindBy(xpath = "//h5[contains(text(),'CORRESPONDENCE DETAILS')]")
    public WebElement viewOutboundCorrespondenceDetailsHeader;

    @FindBy(xpath = "//li[.='On Hold']")
    public WebElement statusDropdownOnHold;

    @FindBy(xpath = "//li[.='Requested']")
    public WebElement statusDropdownRequested;

    @FindBy(xpath = "//li[.='Sent']")
    public WebElement statusDropdownSent;

    @FindBy(xpath = "//li[.='Canceled']")
    public WebElement statusDropdownCanceled;

    @FindBy(xpath = "//li[.='No longer appropriate']")
    public WebElement statusReasonDropdownNoLongerAppropriate;

    @FindBy(xpath = "//li[.='Requested in error']")
    public WebElement statusReasonDropdownRequestedInError;

    @FindBy(xpath = "//li[.='Unresolvable error']")
    public WebElement statusReasonDropdownUnresolvableError;

    @FindBy(xpath = "(//span[text()='check'])[1]")
    public WebElement saveOutboundButton;

    public WebElement getOutboundCorrCID(String CID) {
        return Driver.getDriver().findElement(By.xpath("//h5[contains(text(),'OUTBOUND')]/following::td[contains(text(),'" + CID + "')]"));
    }

    @FindBy(xpath = "//span[.='edit']")
    public WebElement editButton;

    @FindBy(xpath = "(//*[contains(text(),'file_copy')])[1]")
    public WebElement viewIconCorrespondenceLevel;

    @FindBy(xpath = "//p[.='Status Reason is required and cannot be left blank']")
    public WebElement statusReasonErrorMsg;

    @FindBy(xpath = "(//h6)[1]/a")
    public WebElement CIDByEditButton;

    @FindBy(xpath = "//div/div[1]/div/div/div/p/div/button[1]/span[1]/span")
    public WebElement warningMsgCheckYesbttn;

    @FindBy(xpath = "//p[contains(text(), 'Are you sure, you wish to cancel this correspondence?')]")
    public WebElement cancelingWarningMsg;

    @FindBy(xpath = "//i[.='more_vert']")
    public WebElement notificationActionDropdown;

    @FindBy(xpath = "//span[.='RETURNED']")
    public WebElement notificationDropdownReturned;

    @FindBy(xpath = "//span[.='CANCEL']")
    public WebElement notificationDropdownCancel;

    @FindBy(id = "mui-component-select-notificationStatusReason")
    public WebElement notificationReasonDropdown;

    @FindBy(xpath = "//input[@placeholder='MM/DD/YYYY']")
    public WebElement notificationReturnDateDropdown;

    @FindBy(xpath = "//p[contains(.,'Status Reason is')]")
    public WebElement notificationReasonBlankErrorMsg;

    @FindBy(xpath = "//p[.='RETURNED DATE is required when STATUS is Returned.']")
    public WebElement notificationReturnDateBlankErrorMsg;

    @FindBy(xpath = "(//p[.='STATUS'])[1]/following-sibling::h6")
    public WebElement correspondenceStatusValue;
    //p[contains(text(),'STATUS')][1]/following-sibling::h6

    @FindBy(xpath = "(//p[.='LANGUAGE'])[1]/following-sibling::h6")
    public WebElement correspondenceLanguageValue;

    @FindBy(xpath = "(//p[.='STATUS'])[2]/following-sibling::h6")
    public WebElement notificationStatusValue;

    @FindBy(xpath = "//li[@class='MuiButtonBase-root MuiListItem-root MuiMenuItem-root MuiMenuItem-gutters MuiListItem-gutters MuiListItem-button']" )
    public List<WebElement> notificationReasonsValues;

    @FindBy(xpath = "//li[contains(text(),'Undeliverable')]" )
    public WebElement notiValUndeliverable;

    @FindBy(xpath = "//li[contains(text(),'Refused')]" )
    public WebElement notiValRefused;

    @FindBy(xpath = "//li[contains(text(),'Mailbox full')]" )
    public WebElement notiValMailbox;

    @FindBy(xpath = "//li[contains(text(),'Destination agent unresponsive')]" )
    public WebElement notiValagentUnresponsive;

    @FindBy(xpath = "//li[contains(text(),'Destination agent rejected message')]" )
    public WebElement notiValrejectedMessage;

    @FindBy(xpath = "//li[contains(text(),'Destination invalid')]" )
    public WebElement notiValInvalid;

    @FindBy(xpath = "//button[contains(@aria-label,'change date')]")
    public WebElement calendar;

    @FindBy(xpath = "(//span[contains(text(),'Cancel')])[1]")
    public WebElement firstNotificationCancelBttn;

    @FindBy(xpath = "//span[contains(text(),'MESSAGE')]/../../../div[3]")
    public WebElement navigateAwayWarningMsg;

    @FindBy(xpath = "//*[contains(text(),'keyboard_backspace')]")
    public WebElement outboundDetailsBackArrow;

    @FindBy(xpath = "//span[contains(text(),'STATUS HISTORY')]")
    public WebElement statusHistory;

    @FindBy(xpath = "//div[@class='MuiListItemText-root']/*")
    public List<WebElement> notiActionDropValues;

    @FindBy(xpath = "//table[@class='mdl-data-table mdl-js-data-table mt-2 mx-table table']/tbody[1]/tr/td[1]")
    public List<WebElement> statusHistoryStatus;

    @FindBy(xpath = "(//p[.='STATUS'])[2]/following-sibling::h6")
    public WebElement firstNotificationStatusValue;

    @FindBy(xpath = "(//p[.='STATUS'])[3]/following-sibling::h6")
    public WebElement secondNotificationStatusValue;

    @FindBy(xpath = "(//p[.='STATUS'])[4]/following-sibling::h6")
    public WebElement thirdNotificationStatusValue;

    @FindBy(xpath = "(//p[.='STATUS'])[5]/following-sibling::h6")
    public WebElement fourthNotificationStatusValue;

    @FindBy(xpath = "//h1[contains(text(),'LINKS')]/following-sibling::div/div/div/div/table/tbody/tr/td[2]")
    public List<WebElement> firstColumnLinks;

    @FindBy(xpath = "//h1[contains(text(),'LINKS')]/following-sibling::div/div/div/div/table/tbody/tr/td[3]")
    public List<WebElement> secondColumnLinks;

    @FindBy(xpath = "//h1[contains(text(),'LINKS')]/following-sibling::div/div/div/div/table/tbody/tr/td[4]")
    public List<WebElement> thirdColumnLinks;

    @FindBy(xpath = "//h1[contains(text(),'LINKS')]/following-sibling::div/div/div/div/table/tbody/tr/td[5]")
    public List<WebElement> fourthColumnLinks;

    @FindBy(xpath = "//h1[contains(text(),'LINKS')]/following-sibling::div/div/div/div/table/tbody/tr/td[6]")
    public List<WebElement> fifthColumnLinks;

    @FindBy(xpath = "//li[.='CANCEL']")
    public WebElement cancelOpt;

    @FindBy(xpath = "//span[text()='check']")
    public WebElement saveBtn;

    @FindBy(xpath = "//*[contains(text(),'CASE ID')]/following-sibling::h6")
    public WebElement regardingCaseId;

    @FindBy(xpath = "//*[contains(text(),'CONSUMER')]/following-sibling::h6")
    public WebElement regardingConsumerId;

    //keerthi
    @FindBy(xpath = "//li[.='SENT']")
    public WebElement notificationDropdownSent;

    @FindBy(xpath = "//li[.='HOLD']")
    public WebElement notificationDropdownHold;

    @FindBy(xpath = "//li[.='RETRY']")
    public WebElement notificationDropdownRetry;

    @FindBy(xpath = "//li[.='RESEND']")
    public WebElement notificationDropdownResend;

    @FindBy(xpath = "//li[.='RESUME']")
    public WebElement notificationDropdownResume;

    @FindBy(xpath = "//input[@class='MuiInputBase-input MuiInput-input MuiInputBase-inputAdornedEnd']")
    public WebElement statusdate;

    @FindBy(xpath = "//input[@class='MuiInputBase-input MuiInput-input MuiInputBase-inputAdornedEnd']")
    public WebElement returndate;

    @FindBy(xpath = "//p[contains(.,'Sent Date is required')]")
    public WebElement notificationsentdate;

    @FindBy(xpath = "//p[contains(.,'RETURNED DATE is required')]")
    public WebElement notificationreturneddate;

    @FindBy(xpath = "(//p[.='REASON'])/../h6")
    public WebElement notificationreasonvalue;

    @FindBy(xpath = "//div[@id='mui-component-select-notificationStatusReason']")
    public WebElement notificationreason;

    @FindBy(xpath = "//*[@class='MuiSvgIcon-root']")
    public WebElement calendaricon;

    @FindBy(xpath = "//span[contains(text(),'Clear')]")
    public WebElement clearcalendaricon;

    @FindBy(xpath = "//*[contains(text(),'UPDATED BY')]/following-sibling::h6")
    public WebElement updatedByValue;


    @FindBy(xpath = "//ul[@role='listbox']/li")
    public WebElement dropdownlist;

    @FindBy(xpath = "(//span[text()='clear'])[3]")
    public WebElement warningMsgChecknobttn;

    @FindBy(xpath = "//*[contains(text(),'APPLICATION ID')]/following-sibling::h6")
    public WebElement applicationIDvalue;

    @FindBy(xpath = "//*[contains(text(),'APPLICATION ID')]")
    public WebElement applicationdIDfield;

    @FindBy(xpath = "(//div[@class='col-12 mdl-color--grey-100 mx-color-white-border py-2 mt-2'])[1]/ul/li[1]")
    //((//*[contains(text(),'NOTIFICATIONS')]/ancestor::div/div/ul)[2])/li[1]
    //(//*[contains(text(),'NOTIFICATIONS')]/ancestor::div/div/ul)[2]/li[1]
    public WebElement primaryIndConsumerId;

    @FindBy(xpath = "(//div[@class='col-12 mdl-color--grey-100 mx-color-white-border py-2 mt-2'])[1]/ul/li[2]")
    public WebElement primaryIndConsumerName;

    @FindBy(xpath = "(//div[@class='col-12 mdl-color--grey-100 mx-color-white-border py-2 mt-2'])[1]/ul/li[3]")
    public WebElement primaryIndConsumerRole;

    @FindBy(xpath = "(//div[@class='col-12 mdl-color--grey-100 mx-color-white-border py-2 mt-2'])[2]/ul/li[1]")
    public WebElement applicationMemberConsumerId;

    @FindBy(xpath = "(//div[@class='col-12 mdl-color--grey-100 mx-color-white-border py-2 mt-2'])[2]/ul/li[2]")
    public WebElement applicationMemberConsumerName;

    @FindBy(xpath = "(//div[@class='col-12 mdl-color--grey-100 mx-color-white-border py-2 mt-2'])[2]/ul/li[3]")
    public WebElement applicationMemberConsumerRole;

    @FindBy(xpath = "(//div[@class='col-12 mdl-color--grey-100 mx-color-white-border py-2 mt-2'])[3]/ul/li[1]")
    public WebElement authorizedRepConsumerId;

    @FindBy(xpath = "(//div[@class='col-12 mdl-color--grey-100 mx-color-white-border py-2 mt-2'])[3]/ul/li[2]")
    public WebElement authorizedRepConsumerName;

    @FindBy(xpath = "(//div[@class='col-12 mdl-color--grey-100 mx-color-white-border py-2 mt-2'])[3]/ul/li[3]")
    public WebElement authorizedRepConsumerRole;

    @FindBy(xpath = "(//td[text()='Contact Record']/preceding-sibling::td)[2]")
    public WebElement contactRecordLinkID;

    @FindBy(xpath = "(//p[.='STATUS REASON'])[1]/following-sibling::h6")
    public WebElement correspondenceStatusReasonValue;

    @FindBy(xpath = "(//p[.='REASON'])[1]/following-sibling::h6")
    public WebElement notificationStatusreasonValue;

    @FindBy(xpath = "(//td[text()='Application']/../td)[2]")
    public WebElement applicationID;

    @FindBy(xpath = "(//td[text()='Application']/../td)[3]")
    public WebElement applicationName;

    @FindBy(xpath = "(//td[text()='Application']/../td)[4]")
    public WebElement applicationType;

    @FindBy(xpath = "(//td[text()='Application']/../td)[5]")
    public WebElement applicationStatusDate;

    @FindBy(xpath = "(//td[text()='Application']/../td)[6]")
    public WebElement applicationStatus;

    @FindBy(xpath = "//tbody/tr/td[2]")
    public List<WebElement> oblinkids;

    @FindBy(xpath = "//h5[contains(@class,'mx-section-header mx-border')]")
    public WebElement taskid;

    @FindBy(xpath = "(//h5[contains(@class,'mx-section-header')])[1]")
    public WebElement taskpageheader;

    public WebElement getOutboundCorrNIDValues(String nid,String value) {
        return Driver.getDriver().findElement(By.xpath("//*/h6[text()='"+nid+"']/../../*/h6[text()='"+value+"']"));
    }

    @FindBy(id = "mui-component-select-channelType")
    public WebElement notificationchannelDropdown;

    @FindBy(id = "mui-component-select-language")
    public WebElement notificationlanguageDropdown;

    @FindBy(id = "mui-component-select-destination")
    public WebElement notificationdestinationDropdown;

    @FindBy(xpath = "//ul[@class='MuiList-root MuiMenu-list MuiList-padding']/li")
    public List<WebElement> typedropdownvalues;

    @FindBy(xpath = "//*[contains(text(),'ADD NOTIFICATION')]")
    public WebElement addNotificationButton;

    @FindBy(xpath = "//*[contains(text(),'ADD NOTIFICATION')]")
    public List<WebElement> addNotificationButtoncount;

    public WebElement requiredlabel(String value) {
        return Driver.getDriver().findElement(By.xpath("//p[contains(text(),'"+value+"')]"));
    }

    @FindBy(xpath = "//input[@id='phoneNumber']")
    public WebElement phonefield;

    @FindBy(xpath = "//input[@id='faxNumber']")
    public WebElement faxfield;

    @FindBy(xpath = "//input[@id='emailAddress']")
    public WebElement emailfield;

    @FindBy(xpath = "//*[@title='Outbound Links']")
    public WebElement linksHeader;
}
