package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class CRMThirdPartyContactRecPage {
    
    private BrowserUtils browserUtils = new BrowserUtils();

    public CRMThirdPartyContactRecPage() {

        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath="//h6[text()='THIRD PARTY DETAILS']")
    public WebElement lblThirdPartyDetailsHeader;

    @FindBy(id="thirdPartyFirstName")
    public WebElement txtThirdPartyFirstName;

    @FindBy(id="consumerFirstName")
    public WebElement thirdPartyFirstNameNew;

    @FindBy(id="thirdPartyLastName")
    public WebElement txtThirdPartyLastName;

    @FindBy(id="consumerLastName")
    public WebElement thirdPartyLastNameNew;

    @FindBy(id="organizationName")
    public WebElement txtThirdPartyOrganizationName;

    @FindBy(xpath="//*[@id='consumerType']/..")
    public WebElement lstThirdPartyConsumerType;

    @FindBy(xpath="//*[@id='preferredLanguageCode']/..")
    public WebElement lstThirdPartyPreferredLanguage;

   @FindBy(xpath = "//div[@id='menu-consumerType']//ul/li")
    public List<WebElement> lstConsumerTypeValues;

    @FindBy(xpath = "//div[@id='menu-preferredLanguageCode']//ul/li")
    public List<WebElement> lstPreferredLanguageValues;

    @FindBy(xpath = "//*[@id='contactRecordStatusType']/..")
    public WebElement lstContactDisposition;

    @FindBy(xpath = "//*[@id='contactReasonEditType']/..")
    public WebElement lstReasonForEdit;

    @FindBy(xpath = "//a[contains(text(), 'SAVE')]")
    public WebElement btnSave;

    @FindBy(xpath = "//*[text()='THIRD PARTY - CONSUMER IN CONTACT']")
    public WebElement lblThirdPartyConsumerInContact;

    @FindBy(xpath = "//*[text()='THIRD PARTY - CONSUMER CONTACTED ABOUT']")
    public WebElement lblThirdPartyConsumerContactedAbout;

    @FindBy(xpath = "//*[text()='CONTACT DETAILS']")
    public WebElement lblThirdPartyContactDetails;

    @FindBy(xpath = "//span[@title='Wrap-Up and Close']")
    public WebElement lblThirdPartyWrapUpClose;

    @FindBy(xpath = "//span[@title='CASE / CONSUMER SEARCH']")
    public WebElement lblThirdPartyConsumerSearch;

    @FindBy(xpath = "//*[contains(text(),'THIRD PARTY CONTACT')]/..")
    public WebElement thirdPartyRecordTypeRadio;

    @FindBy(xpath = "//*[@id='menu-inboundCallQueue']//ul//li")  //deleted /li      //div[@id='menu-callQueueType']//ul
    public List<WebElement> callQueueTypeListValues;

    @FindBy(xpath = "//input[@name='inboundCallQueue']/ancestor::div[1]")
    public WebElement lstInboundCallQueue;

   @FindBy(xpath="//*[text()='LINKS']")
    public WebElement thirdPartyLinksHeader;

   @FindBy(xpath = "//*[text()='link']")
    public WebElement thirdPartyLinksIcon;

   @FindBy(xpath = "//*[text()[contains(.,'CASE / CONSUMER SEARCH')]]")
    public WebElement thirdPartyCaseConsumerHeader;

   @FindBy(xpath = "//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mx-link']")
    public WebElement thirdPartyLinkedCaseId;

   @FindBy(xpath = "//div[@class='row mx-0']")
    public WebElement thirdPartyAllPage;

   @FindBy(xpath = "//*[@class='mx-section-header float-left mt-1']")
    public WebElement thirdPartyConsumerAboutHeader;

   @FindBy(xpath = "//span[contains(text(),'UNLINK CONTACT RECORD')]")
    public WebElement thirdPartyUnlinkButton;

   @FindBy(xpath = "//span[contains(text(),'chevron_right')]")
    public WebElement thirdPartySearchCreatedConsumerArrow;

    @FindBy(xpath = "//span[contains(text(),'LINK RECORD')]")
    public WebElement thirdPartyLinkRecordButton;

   @FindBy(xpath = "//*[contains(text(),'GENERAL CONTACT')]")
    public WebElement thirdPartyGeneralContactHeader;

   @FindBy(xpath = "//*[contains(text(),'THIRD PARTY CONTACT')]")
   public WebElement thirdPartyThirdPartyHeader;

   @FindBy(xpath = "//*[contains(text(),'UNIDENTIFIED CONTACT')]")
    public WebElement thirdPartyUnidentifiedContactHeader;

   @FindBy(xpath = "//th[text()='ID']")
    public WebElement thirdPartyLinkID;

   @FindBy(xpath = "//th[text()='NAME']")
    public WebElement thirdPartyLinkName;

   @FindBy(xpath = "//th[text()='TYPE']")
    public WebElement thirdPartyLinkType;

   @FindBy(xpath = "//th[text()='STATUS DATE']")
    public WebElement thirdPartyLinkStatusDate;

   @FindBy(xpath = "//th[text()='STATUS']")
    public WebElement thirdPartyLinkStatus;

   @FindBy(xpath = "//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric']")
    public WebElement thirdPartyLinksTable;

   @FindBy(xpath = "(//td[starts-with(@class, 'MuiTableCell')])[3]")
    public WebElement thirdPartyConsumerProfile;

   @FindBy(xpath = "(//td[starts-with(@class, 'MuiTableCell')])[4]")
    public WebElement thirdPartyLinkConsumer;

   @FindBy(xpath = "(//td[starts-with(@class, 'MuiTableCell')])[5]")
    public WebElement thirdPartyLinkDateTime;

    @FindBy(xpath = "(//td[starts-with(@class, 'MuiTableCell')])[6]")
    public WebElement thirdPartyLinkStatusField;

    @FindBy(xpath = "//*[contains(text(),'CASE / CONSUMER SEARCH')]")
    public WebElement thirdPartyCaseConsumer;

    @FindBy(xpath = "//*[contains(text(),'GENERAL CONTACT')]")
    public WebElement generalContactTab;

    @FindBy(xpath = "(//td[starts-with(@class, 'MuiTableCell')])[2]")
    public WebElement thirdPartyLinkCaseId;

    @FindBy(xpath = "//*[p='FIRST NAME']/h6")
    public WebElement thirdPartyFN;

    @FindBy(xpath = "//*[p='LAST NAME']/h6")
    public WebElement thirdPartyLN;

    @FindBy(xpath = "//*[p='THIRD PARTY ORGANIZATION']/h6")
    public WebElement thirdPartyOrg;

    @FindBy(xpath = "//*[p='CONSUMER TYPE']/h6")
    public WebElement thirdPartyType;

    @FindBy(xpath = "//*[p='PREFERRED LANGUAGE']/h6")
    public WebElement thirdPartyPrefLang;

    @FindBy(xpath = "//input[@name='programTypes']/..")
    public WebElement contactProgramTypes;

    @FindBy(xpath = "//div[@id='menu-programTypes']")
    public WebElement contactMenuProgramTypes;

    @FindBy(xpath = "//input[@name='contactReason']/..")
    public WebElement trContactReason;

    @FindBy(xpath = "//input[@name='contactAction']/..")
    public WebElement trContactAction;

    @FindBy(xpath = "//*[@id='contactAction']/..")
    public WebElement contactMenuContactAction;

    @FindBy(xpath = "(//*[@name='notes'])[1]")
    public WebElement contactReasonComments;

    @FindBy(xpath = "(//*[@name='comments'])[2]")
    public WebElement contactReasonAddComments;

    @FindBy(xpath = "//span[contains(text(),' END CONTACT')]")
    public WebElement endContact;

    @FindBy(xpath = "(//*[contains(text(),'check')])[1]")
    public WebElement thirdPartySaveContact;

    @FindBy(id ="consumerFirstName" )
    public WebElement editThirdPartyFirstName;

    @FindBy(id ="consumerLastName" )
    public WebElement editThirdPartyLastName;

    @FindBy(xpath = "//div[@aria-label='Primary Individual']//span[@class='MuiTypography-root MuiFormControlLabel-label MuiTypography-body1']")
    public WebElement thirdPartyPrimaryIndividualName;

    @FindBy(xpath = "//div[@aria-label='Case Member']//span[@class='MuiTypography-root MuiFormControlLabel-label MuiTypography-body1']")
    public WebElement thirdPartyCaseMemberName;

    @FindBy(xpath = "//div[@aria-label='Primary Individual']//input[@type='radio']")
    public WebElement thirdPartyPrimaryIndividualRadioBtn;

    @FindBy(xpath = "//div[@aria-label='Case Member']//input[@type='radio']")
    public WebElement thirdPartyCaseMemberRadioBtn;

    @FindBy(xpath = "//div[@aria-label='Authorized Representative']//input[@type='radio']")
    public WebElement thirdPartyAuthorizedRepRadioBtn;

    @FindBy(xpath = "//div[@aria-label='Broker']//input[@type='radio']")
    public WebElement thirdPartyBrokerBtn;

    @FindBy(xpath = "//div[@aria-label='Assister']//input[@type='radio']")
    public WebElement thirdPartyAssisterBtn;

    @FindBy(xpath = "//span[contains(text(),'LINK CONSUMER')]")
    public WebElement thirdPartyLinkConsumerdButton;

    @FindBy(xpath = "(//span[contains(text(),'Save')])[1]")
    public WebElement savebtnEditPage;

    @FindBy(xpath = "//*[text()='CASE / CONSUMER SEARCH']/..//input[@id='consumerFirstName']")
    public WebElement ccSearchThirdPartyFirstName;

    @FindBy(xpath = "//*[text()='CASE / CONSUMER SEARCH']/..//input[@id='consumerLastName']")
    public WebElement ccSearchThirdPartyLastName;

    @FindBy(xpath = "//*[text()='THIRD PARTY - CONSUMER IN CONTACT']/../..//input[@id='consumerFirstName']")
    public WebElement thirdPartyFirstName;

    @FindBy(xpath = "//*[text()='THIRD PARTY - CONSUMER IN CONTACT']/../..//input[@id='consumerLastName']")
    public WebElement thirdPartyLastName;

    @FindBy(xpath = "//*[text()='THIRD PARTY - CONSUMER IN CONTACT']/../..//input[@id='consumerType']/..")
    public WebElement thirdPartyConsumerType;

    @FindBy(id = "mui-component-select-preferredLanguageCode")
    public WebElement cicPreferedLanguage;

    @FindBy(xpath = "//input[@name='c']")
    public WebElement thirdPartyConsumerRadioButton;

    @FindBy(id = "mui-component-select-contactReasonEditType")
    public WebElement reasonForEditDropdown;

    @FindBy(xpath = "//H5[.='person CONTACT EDIT REASONS']/following::table[1]/tbody/tr/td[3]")
    public List<WebElement> reasonForEditDataColumn;

    @FindBy(xpath = "//H5[.='person CONTACT EDIT REASONS']/following::table[1]/tbody/tr/td[2]")
    public List<WebElement> editedByDataColumn;

    @FindBy(xpath = "//H5[.='person CONTACT EDIT REASONS']/following::table[1]/tbody/tr/td[1]")
    public List<WebElement> editedOnDataColumn;
}
