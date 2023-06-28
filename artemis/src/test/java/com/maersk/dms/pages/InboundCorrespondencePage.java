package com.maersk.dms.pages;

import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static com.maersk.crm.utilities.World.generalSave;

public class InboundCorrespondencePage {

    
//    Actions actions;
//    WebElement pdfIcon;
//    WebDriverWait wait;
//    private BrowserUtils browserUtils = new BrowserUtils();


    public InboundCorrespondencePage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(id = "mui-component-select-contactType")
    public WebElement contactType;  // "Inbound"

    @FindBy(id = "mui-component-select-callQueueType")
    public WebElement inboundCallQueue;

    @FindBy(id = "mui-component-select-contactChannelType")
    public WebElement channelType;

    @FindBy(xpath = "(//input[@class='MuiInputBase-input MuiInput-input'])[6]")
    public WebElement channelInput;

    @FindBy(id = "mui-component-select-programTypes")
    public WebElement programABC;

    @FindBy(id = "mui-component-select-contactDispositions")
    public WebElement contactDisposition;

    @FindBy(id = "mui-component-select-outcomeOfContact")
    public WebElement contactOutcome;

    @FindBy(xpath = "//*[text()='Save ']")
    public WebElement contractDetailsSaveButton;

    @FindBy(xpath = "//*[text()=' END CONTACT']")
    public WebElement endContactButton;

    @FindBy(xpath = "//tr/td[6]")
    public List<WebElement> consumerTypeResult;

    @FindBy(xpath = "//*[text()='RECEIVED']/../../../tbody/tr/td[4]")
    public List<WebElement> dateFormatResult;

    @FindBy(xpath = "//tr/td[5]")
    public List<WebElement> consumerResult;

    @FindBy(xpath = "//div[text()='Show 5']")
    public WebElement showFiveResults;

    //case&ContactDetailsScreen
    @FindBy(xpath = "//i[@title='Manual']/parent::td")
    public WebElement contactHistoryCid;

    @FindBy(xpath = "//*[text()='Case & Contact Details']")
    public WebElement caseContactDetailsTab;

    @FindBy(xpath = "//*[contains(text(), 'INITIATE CONTACT')]")
    public WebElement initContact;

    @FindBy(xpath = "//*[@class='mx-second-header__ac-indicator  float-left mx-active']")
    public WebElement contactInProgressGreenSign;

    @FindBy(xpath = "(//tbody[@class='MuiTableBody-root'])[3]/tr/td[5]")
    public WebElement inboundMultipleConsumerResults;

    @FindBy(id = "consumerFirstName")
    public WebElement consumerFirstName;

    @FindBy(xpath = "//*[contains(@class, 'mx-btn-primary mdl-shadow--2dp')]")
    public WebElement searchBtn;

    @FindBy(xpath = "//tr/td/button/span")
    public WebElement firstContactBtn;

    @FindBy(xpath = "//tr/td/button/span")
    public WebElement contactDetailsHeader;

    @FindBy(name = "0_caConsumerFullName")
    public WebElement nameConsumer1;

    @FindBy(name = "0_caDob")
    public WebElement dobConsumer1;

    @FindBy(name = "0_caCrmConsumerId")
    public WebElement crmConsumerID1;

    @FindBy(xpath = "//*[text()='LINK RECORD']")
    public WebElement linkRecordBtn;

    @FindBy(xpath = "(//tbody[@class='MuiTableBody-root'])[2]/tr/td[2]")
    public WebElement onbaseCid;

    @FindBy(xpath = "(//tbody[@class='MuiTableBody-root'])[2]/tr/td[6]/a/i")
    public WebElement outboundMultipleChannels;

    @FindBy(xpath = "(//tbody[@class='MuiTableBody-root'])[2]/tr/td[6]")
    public WebElement outboundChannel;

    @FindBy(xpath = "(//*[text()='Fax'])/../td[6]/a/i")
    public WebElement multipleOutboundsFax;

    @FindBy(xpath = "(//*[text()='Email'])/../td[6]/a/i")
    public WebElement multipleOutboundsEmail;

    @FindBy(xpath = "(//*[text()='Mail'])/../td[6]/a/i")
    public WebElement multipleOutboundsMail;

    @FindBy(xpath = "((//*[text()='Email'])/../td[6])[last()]")
    public WebElement singleOutboundEmail;

    @FindBy(xpath = "((//*[text()='Text'])/../td[6])[last()]")
    public WebElement singleOutboundText;

    @FindBy(xpath = "((//*[text()='Mail'])/../td[6]/a/i)/../../../td[1]")
    public WebElement multipleOutboundMailViewIcon;


    @FindBy(xpath = "//tr/td[11]")
    public WebElement correspondenceFileIcons;

    @FindBy(xpath = "//div[@class='col-12 mx-border-6dp py-4']//div[@class='col-1 col-xxl-1']/p/button")
    public WebElement outboundCorrespondenceDetailIcons;

    public boolean elementIsDisplayed(WebElement e) {
        try {
            if (e.isDisplayed())
                return true;
            else
                return false;
        } catch (NoSuchElementException exp) {
            return false;
        }
    }

    @FindBy(xpath = "//*[text()='Demographic Info']")
    public WebElement demographicInfoTab;

    @FindBy(xpath = "(//*[text()='\" + nid + \"'])[1]/../../div[7]/p/button")
    public WebElement pdfIcon;

    @FindBy(xpath = "//tbody[@class='MuiTableBody-root']/tr[1]/td[4]")
    public WebElement firstNameFirstResult;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]")
    public WebElement searchResultHeader;

    @FindBy(xpath = "//*[contains(text(),'\"invalid_access_token\"')]")
    public WebElement pdfWindowFailMsg;

    @FindBy(xpath = "//div[@id='content']/embed")
    public WebElement pdfPlugin;

    @FindBy(xpath = "//button[@id='menu-list-button']")
    public WebElement menuListBtn;

    @FindBy(xpath = "//li[@name='createCorrespondence']")
    public WebElement createCorrespondenceBtn;

    @FindBy(xpath = "(//div[@class='MuiFormControl-root col-12'])[1]")
    public WebElement createOutboundTypeDropdwn;

    @FindBy(xpath = "(//div[@class='MuiFormControl-root col-12'])[2]")
    public WebElement createOutboundLanguageDropdwn;

    @FindBy(xpath = "//input[@id='sendTo']")
    public WebElement createOutboundSendToCheckBx;

    @FindBy(xpath = "//*[text()=' Save ']")
    private WebElement createOutboundSaveBtn;

    @FindBy(xpath = "//*[text()=' Cancel']")
    public WebElement createOutboundCancelBtn;

    @FindBy(xpath = "//*[text()=' WARNING MESSAGE']")
    public WebElement createOutboundCancelMsg;

    @FindBy(xpath = "//*[text()='Continue']")
    public WebElement createOutboundPopupContinueBtn;

    @FindBy(xpath = "//*[text()='Cancel']")
    public WebElement createOutboundPopupCancelBtn;

    @FindBy(xpath = "//*[text()='CID']/a")
    public WebElement correspondenceDetailsHeader;

    @FindBy(xpath = "//*[text()=' NOTES']/./i")
    public WebElement correspondenceDetailsNotesHeader;

    @FindBy(xpath = "//span[contains(text(),'Save')]/..")
    public WebElement correspondenceDetailsNotesSaveBtn;

    @FindBy(xpath = "//span[contains(text(),'Cancel')]/..")
    public WebElement correspondenceDetailsNotesCancelBtn;

    public void outboundCorrespondenceDetailsByCid(String cid, WebDriver driver) {
        String x = "//*[text()='" + cid + "']";

        driver.findElement(By.xpath(x)).click();
    }

    @FindBy(xpath = "//*[text()='OUTBOUND CORRESPONDENCES']")
    public WebElement outboundCorrespondencesHeader;

    @FindBy(xpath = "//*[@name='notesText']")
    public WebElement addNoteInput;

    @FindBy(xpath = "//*[text()='SUCCESS MESSAGE']")
    public WebElement correspondenceDetailsSaveNoteSuccessMsg;

    @FindBy(xpath = "(//div[@class='pt-5 mx-comment-border'])[last()]/p[@class='text-right']")
    public WebElement correspondenceDetailsLatestNoteAddedDate;

    //refactored after latest added went from bottom of list to top of list  @albert
    // @FindBy(xpath = "(//div[@class='pt-5 mx-comment-border'])[last()]/p[@class='ml-3 pl-4']")
    @FindBy(xpath = "(//div[@class='pt-5 mx-comment-border']/p[2])[1]")
    public WebElement correspondenceDetailsLatestNoteAddedText;

    @FindBy(xpath = "//h6[@class='mx-section-header float-left mt-2 mdl-color-text--grey-600']")
    public WebElement correspondenceDetailsNoteUserNames;

    @FindBy(xpath = "//div[@class='pt-5 mx-comment-border']/p[@class='text-right']")
    public WebElement correspondenceDetailsNoteDateTimeStamps;

    @FindBy(xpath = "//div[@class='pt-5 mx-comment-border']/p[@class='ml-3 pl-4']")
    public WebElement correspondenceDetailsNoteTexts;

    @FindBy(xpath = "(//div[@class='pt-5 mx-comment-border'])[last()]/h6")
    public WebElement correspondenceDetailsLatestNoteCreatedBy;

    @FindBy(xpath = "//*[text()='If you continue, all the captured information will be lost']")
    public WebElement correspondenceDetailsNavigationWarningMsg;

    @FindBy(xpath = "//*[text()='keyboard_backspace']")
    public WebElement outboundCorrespondencBackspaceArrow;

    @FindBy(xpath = "//*[text()='INBOUND CORRESOPNDENCES']")
    public WebElement caseAndContactDetailsInboundCorrespondencesHeader;

    @FindBy(xpath = "//*[contains(text(),'INBOUND COR')]/following::table[contains(@class,'table')]/tbody/tr/td[4]")
    public List<WebElement> inboundDateReceivedValues;

    @FindBy(xpath = "//*[contains(text(),'INBOUND COR')]/following::table[contains(@class,'table')]/tbody/tr/td[5]")
    public List<WebElement> inboundRecipientValues;

    @FindBy(xpath = "//*[contains(text(),'INBOUND COR')]/following::table[contains(@class,'table')]/tbody/tr/td[3]")
    public List<WebElement> inboundTypeValues;

    @FindBy(xpath = "//*[contains(text(),'INBOUND COR')]/following::table[contains(@class,'table')]/tbody/tr/td[2]")
    public List<WebElement> inboundCIDValues;

    @FindBy(xpath = "//div[5]/div[1]/p[2]")
    public WebElement firstNote;

    @FindBy(xpath = "//*[contains(text(),'NOTES')]/following-sibling::div/div/h6/following-sibling::p[contains(@class,'wordbreak')]")
    public List<WebElement> notesList;

    @FindBy(xpath = "//p[contains(@class,'px-3')]")
    public WebElement warningMessage;

    @FindBy(xpath = "//h6/a")
    public WebElement cid;

    @FindBy(xpath = "//a[contains(@class,'mdl-data-table__cell--non-numeric mx-link')]")
    public WebElement rescannedCid;

    @FindBy(xpath = "//h1/i")
    public WebElement linkComponentHeader;

    @FindBy(xpath = "//h1[contains(.,'LINKS')]")
    public WebElement linkComponentHeader2;

    @FindBy(xpath = "//h1/i[contains(text(),'link')]/../following-sibling::div/div/div/div/div/table/tbody/tr[1]/td")
    public List<WebElement> firstLink;

    @FindBy(xpath = "//h1/i[contains(text(),'link')]/../following-sibling::div/div/div/div/div/table/tbody/tr[3]/td")
    public List<WebElement> secondLink;

    @FindBy(xpath = "//h1/i[contains(text(),'link')]/../following-sibling::div/div/div/div/div/table/tbody/tr[5]/td")
    public List<WebElement> thirdLink;

    @FindBy(xpath = "//h1/i[contains(text(),'link')]/../following-sibling::div/div/div/div/div/table/tbody/tr[7]/td")
    public List<WebElement> fourthLink;

    @FindBy(xpath = "//button[contains(@class,'MuiButtonBase-root MuiButton-root MuiButton-text" +
            " mdl-button--primary mx-btn mx-btn-border mx-btn-cancel float-left ml-2')]")
    public WebElement warningMessageCancel;

    @FindBy(xpath = "//button[contains(@class,'MuiButtonBase-root MuiButton-root MuiButton-text" +
            " mx-btn  mx-btn-border mx-btn-primary float-left mb-4')]")
    public WebElement warningMessageContinue;

    @FindBy(xpath = "//*[text()='INBOUND CORRESPONDENCE SEARCH']")
    public WebElement inboundCorrespondenceSearchHeader;

    @FindBy(xpath = "//*[text()='INBOUND CORRESPONDENCE']")
    public WebElement inboundCorrespondenceHeaderTxt;

    @FindBy(xpath = "(//p[.='STATUS'])[1]/../h6")
    public WebElement outboundStatus;

    @FindBy(xpath = "(//p[.='STATUS REASON'])[1]/../h6")
    public WebElement outboundStatusReason;

    @FindBy(xpath = "//*[contains(text(),'edit')]/../..")
    public WebElement editButton;

    @FindBy(id = "_TYPE")
    public WebElement inboundTypeDropDown;

    @FindBy(xpath = "//span[contains(text(),'check')]/../..")
    public WebElement saveButton;

    @FindBy(xpath = "(//span[contains(text(),'check')]/../..)[1]")
    public WebElement editSaveButton;

    @FindBy(xpath = "//span[contains(text(),'link')]/../..")
    public WebElement getImageButton;

//    @FindBy(xpath = "//span[contains(text(),'clear')]/../..")
    @FindBy(xpath = "//span[contains(text(),'close')]/../..")
    public WebElement cancelButton;

    @FindBy(xpath = "(//span[contains(text(),'clear')]/../..)[1]")
    public WebElement editCancelButton;

    @FindBy(xpath = "//*[text()[contains(.,'DOCUMENT SET DETAILS')]]/../..")
    public WebElement documentSetDetailstab;

    @FindBy(xpath = "//span[contains(@title,'Correspondence')]")
    public WebElement documentSetDetailsIcon;

    @FindBy(xpath = "//*[contains(text(),'file_copy')]/..")
    public WebElement viewIcon;

    @FindBy(xpath = "//i[contains(text(),'scanner')]/..")
    public WebElement requestRescan;

    @FindBy(xpath = ".//*[text()[contains(.,'CANCEL RE-SCAN')]]")
    public WebElement cancelRescanButton;

    @FindBy(xpath = "//th[contains(text(),'CORRESPONDENCE TYPE')]")
    public WebElement documentSetDetailsTypeHeader;

    @FindBy(xpath = "//th[contains(text(),'CID')]")
    public WebElement documentSetDetailsCIDHeader;

    @FindBy(xpath = "//*[contains(text(),'No Records Found')]")
    public WebElement noRecordsFound;

    @FindBy(xpath = "//*[@title='More Submenu']")
    public WebElement multipleConsumersEllipsis;

    @FindBy(xpath = "//th[contains(text(),'CONSUMER NAME')]")
    public WebElement documentSetDetailsConsumerNameHeader;

    @FindBy(xpath = "//ul[@role='listbox']/li")
    public List<WebElement> typeDropDownOptions;

    @FindBy(xpath = "//*[contains(text(),'Your file couldn’t be accessed')]")
    public WebElement pageNotFound;

    @FindBy(xpath = "//*[contains(@class,'userid')]")
    public WebElement userId;

    @FindBy(xpath = "//*[text()[contains(.,'DOCUMENT SET ID')]]/following-sibling::div/table/tbody/tr/td[4]/button")
    public List<WebElement> documentSetViewIcons;

    public WebElement getTypePop(String message) {
        return Driver.getDriver().findElement(By.xpath("//span[@id='client-snackbar' and contains(text(),'" + message + "')]"));
    }

    public String outboundCorresRecipientByCid(String cid) {
        String x = "(//*[text()='" + cid + "'])/../td[5]";
        return Driver.getDriver().findElement(By.xpath(x)).getText();
    }

    public String viewVerifyOutboundStatus() {
        if (outboundStatus.isDisplayed()) {
            return outboundStatus.getText();
        } else return String.valueOf(false);
    }

    public String viewVerifyOutboundStatusReason() {
        if (outboundStatusReason.isDisplayed()) {
            return outboundStatusReason.getText();
        } else return String.valueOf(false);
    }

    @FindBy(xpath="//*[contains(text(),'INBOUND COR')]/following::table[contains(@class,'table')]/tbody/tr/td[3]")
    public List<WebElement> inboundLinkNames;

    @FindBy(xpath="//*[contains(text(),'INBOUND COR')]/following::table[contains(@class,'table')]/tbody/tr/td[4]")
    public List<WebElement> inboundLinkTypes;

    @FindBy(xpath="//*[contains(text(),'INBOUND COR')]/following::table[contains(@class,'table')]/tbody/tr/td[5]")
    public List<WebElement> inboundLinkStatusDates;

    @FindBy(xpath="//*[contains(text(),'INBOUND COR')]/following::table[contains(@class,'table')]/tbody/tr/td[6]")
    public List<WebElement> inboundLinkStatus;

    @FindBy(xpath = "//label[text()='RECEIVED DATE']/following-sibling::div/input")
    public WebElement receiveddate;

    @FindBy(xpath = "//button[@aria-label='change date']")
    public WebElement calendaricon;

    @FindBy(xpath = "(//button[@class='MuiButtonBase-root MuiIconButton-root MuiPickersCalendarHeader-iconButton'])[1]")
    public WebElement previousmontharrow;

    @FindBy(xpath = "(//button[@class='MuiButtonBase-root MuiIconButton-root MuiPickersCalendarHeader-iconButton'])[2]")
    public WebElement nextmontharrow;

    @FindBy(xpath = "//span[contains(text(),'OK')]")
    public WebElement calendarokbutton;

    @FindBy(xpath = "//span[contains(text(),'Clear')]")
    public WebElement calendarclearbutton;

    public WebElement  selectcalendardate(int d) {
        return Driver.getDriver().findElement(By.xpath("//button[contains(@class,'MuiButtonBase-root MuiIconButton-root MuiPickersDay-day')]//p[text()="+d+"]"));
    }

    @FindBy(xpath = "//p[text()='RECEIVED DATE']/following-sibling::h6")
    public WebElement receiveddatevalue;

    @FindBy(xpath = "//p[contains(.,'TYPE')]/../h6")
    public WebElement typeDropdownValue;

    @FindBy(xpath = "//p[contains(text(),'If you navigate away, your information will not be saved')]") // this is the expected message on the story
//    @FindBy(xpath = "//p[contains(text(),'If you continue, all the captured information will be lost')]")  // this is the actual message on the application
    public WebElement discardFailMessage;

    @FindBy(xpath = "//p[contains(text(),'If you continue, all the captured information will be lost')]//ancestor::div[7]//button//span[contains(text(),'clear')]")
    public WebElement warningMsgCancelButton;

    @FindBy(xpath = "//td[text()='Task']/../td[2]")
    public List<WebElement> listOfTaskIDs;

    @FindBy(xpath = "//tbody/tr/td[2]")
    public List<WebElement> iblinkids;

    @FindBy(xpath = "//tbody/tr/td[3]")
    public List<WebElement> iblinknames;

    @FindBy(xpath = "//tbody/tr/td[4]")
    public List<WebElement> iblinktypes;

}