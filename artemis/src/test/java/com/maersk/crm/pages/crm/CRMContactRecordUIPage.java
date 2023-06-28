package com.maersk.crm.pages.crm;

import com.gargoylesoftware.htmlunit.WebWindow;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import javax.xml.xpath.XPath;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.maersk.crm.utilities.BrowserUtils.waitFor;

public class CRMContactRecordUIPage {




    public CRMContactRecordUIPage() {

        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "(//*[contains(@class,'material-icons MuiIcon-root mr')])")
    public WebElement contactRecordAdvanceSearch;

    @FindBy(xpath = "(//div[contains(@id,'contactAction')])[2]")
    public WebElement expendcontactActionDropDown;

    @FindBy(xpath = "//a[contains(text(), 'Case & Contact Details')]")
    public WebElement caseContactDetailsTab;

    @FindBy(xpath = "//*[@id='consumerSSN']")
    public WebElement ssnSearch;

    @FindBy(xpath = "//*[@placeholder='MM/DD/YYYY']")
    public WebElement dobSearch;

    @FindBy(id = "caseId")
    public WebElement uniqueIDSearch;

    @FindBy(name = "consumerMiddleName")
    public WebElement middleInitialSearch;


    //    @FindBy(xpath = "//*[@id='client-snackbar']/..")
    @FindBy(xpath = "(//div[@class='col py-2 px-3 ml-3']//span[@id='client-snackbar'])")
    public WebElement enterSearchParametersWarning;

    @FindBy(xpath = "//*[contains(text(), 'INITIATE CONTACT')]")
    public WebElement initContact;

    @FindBy(xpath = "//*[@class=' mx-letter-space']")
    public WebElement headerDate;

    @FindBy(xpath = "//*[@class=' mx-letter-space']/following-sibling::* ")
    public WebElement headerTime;

    public WebElement getHeaderDate(String date) {
        return Driver.getDriver().findElement(By.xpath("//*[contains(p,'" + date + "')]/*"));
    }

    @FindBy(xpath = "//p[.='QA']")
    public WebElement headerRole;

    @FindBy(xpath = "//*[contains(@class, 'text-right mx-header-username ')]")
    public WebElement headerUsername;

    @FindBy(xpath = "//*[contains(@class, 'text-right mx-header-userid')]")
    public WebElement headerID;

    @FindBy(xpath = "//*[@class='mx-second-header__ac-indicator  float-left mx-active']")
    public WebElement contactInProgressGreenSign;

    @FindBy(xpath = "//*[contains(@class, 'mx-second-header__ac-indicator float-left mx-inactive')]")
    public WebElement noContactInProgressGraySign;

    @FindBy(xpath = "(//*[contains(text(), 'CONTACT RECORD')])[1]")
    public WebElement contactRecordSign;

    @FindBy(xpath = "//i[contains(text(),'schedule')]/parent::p")
    public WebElement contactStart;

    @FindBy(xpath = "//p[contains(text(),'DURATION')]")
    public WebElement contactDuration;

    @FindBy(xpath = "//i[contains(text(),'av_timer')]/..")
    public WebElement contactDurationValue;
    //refactored 11/02/18
    @FindBy(xpath = "//span[.='call_end']")
    public WebElement stopContact;

    @FindBy(xpath = "(//span[contains(text(),'search')])[2]")
    public WebElement searchButton;

    @FindBy(xpath = "//span[contains(text(),'search')]")
    public WebElement searchButtonIniContact;

    @FindBy(xpath = "(//td[2])[1]")
    public WebElement firstContactID;

    @FindBy(xpath = "(//i[contains(text(),'person')]/parent::a/span)[2]")
    public WebElement secondContactID;

    @FindBy(xpath = "(//span[contains(text(),'search')]/parent::span/parent::button)[2]")
    public WebElement consumerSearchBtn;

    @FindBy(xpath = "//*[contains(text(),'edit')]") //i[text()='edit']/..
    public WebElement editIcon;

    @FindBy(xpath = "//*[contains(text(),'Cancel')]")
    public WebElement resetButton;

    @FindBy(xpath = "//h5[contains(text(), 'No Records Available')]")
    public WebElement noRecordsAvailableMessage;

    @FindBy(xpath = "(//span[contains(text(),'close')])[2]")
    public WebElement cancelButton;

    @FindBy(xpath = "//div[@class='modal-content']//button[1]")
    public WebElement cancelWarningContinueButton;

    @FindBy(xpath = "//span[contains(text(), 'check')]/..")
    public WebElement closeButton;

    @FindBy(xpath = "//*[contains(text(), 'SEARCH RESULT')]")
    public WebElement searchResultSign;

    @FindBy(xpath = "//*[@class='jss88 jss92 jss89']")
    public WebElement searchResultTable;

    @FindBy(xpath = "(//td[4])[1]")
    public WebElement searchResultFirstRowFirstName;

    @FindBy(xpath = "(//td[6])[1]")
    public WebElement searchResultFirstRowLastName;

    @FindBy(xpath = "(//*[contains(text(), 'LINK RECORD')]/..)")
    public WebElement linkRecordButton;

    @FindBy(xpath = "//*[contains(@class,'mx-layout-top-drawer-fields')]")
    public WebElement linkedConsumerInContact;

    @FindBy(xpath = "//*[@title='Expand Arrow']")
    public WebElement expandFistConsumer;

    @FindBy(xpath = "(//input[@type='radio']/..)[1]")
    public WebElement fistConsumerRadioBtn;

    @FindBy(xpath = "(//input[@type='radio']/..)[2]")
    public WebElement secondConsumerRadioBtn;

    @FindBy(xpath = "//th[text()='ID']/parent::tr/parent::thead/following::tbody/tr/td[3]")
    public WebElement individualLinkID;

    @FindBy(xpath = "//*[@name='caUnableToAuthenticate']/..")
    public WebElement unableToAuthenticate;

    @FindBy(xpath = "//*[.='link_off']/../..")
    public WebElement unLink;

    @FindBy(xpath = "//button[contains(.,'CANCEL')]")
    public WebElement caseConsumerCancelButton;

    @FindBy(id = "consumerFirstName")
    public WebElement firstName;

    @FindBy(xpath = "//*[@id=\"consumerFirstName\"]")
    public List<WebElement> firstNameThirdParty;

    @FindBy(name = "consumerLastName")
    public WebElement lastName;

    @FindBy(xpath = "//*[@id=\"consumerLastName\"]")
    public List<WebElement> lastNameThirdParty;

    @FindBy(name = "consumerMiddleName")
    public WebElement middleName;

    @FindBy(xpath = "//input[@id='consumerSSN']")
    public WebElement ssnTextbox;

    @FindBy(id = "consumerDateOfBirth")
    public WebElement dobTextbox;

    @FindBy(xpath = "//input[@id='caseId']")
    public WebElement internalCaseId;

    @FindBy(name = "caseId")
    public WebElement uniqueIDTextbox;

    @FindBy(name = "consumerId")
    public WebElement consumerIDTextbox;

    @FindBy(name = "consumerAddress")
    public WebElement address;

    @FindBy(id = "consumerAddress1")
    public WebElement address1;

    @FindBy(name = "consumerAddress2")
    public WebElement address2;

    @FindBy(xpath = "//*[@name='consumerCity']//..")
    public WebElement city;

    @FindBy(xpath = "//*[@name='consumerState']//..")
    public WebElement state;

    @FindBy(xpath = "//*[@name='consumerCounty']//..")
    public WebElement county;

    @FindBy(id = "consumerPhoneNumber")
    public WebElement consumerPhoneNumber;

    @FindBy(name = "consumerEmail")
    public WebElement consumerEmail;

    @FindBy(xpath = "//h5[@class='mx-section-header float-left']//following-sibling::h5")
    public WebElement noResultsFound;

    @FindBy(xpath = "//p[@class='mx-overflow-text mb-0  mx-text-capitalize']")
    public WebElement searchResultFirstNameFound;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/../div/following-sibling::div/div/table/tbody/tr[1]/td[2]")
    public WebElement caseIdFirstRecordValue;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody[2]/tr/td[2]/p/b")
    public WebElement caseIdSecondRecordValue;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody[3]/tr/td[2]/p/b")
    public WebElement caseIdThirdRecordValue;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody[4]/tr/td[2]/p/b")
    public WebElement caseIdFourthRecordValue;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody[5]/tr/td[2]/p/b")
    public WebElement caseIdFifthRecordValue;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody/tr/td[3]/p")
    public WebElement consumerIdFirstRecordValue;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody[2]/tr/td[3]/p/b")
    public WebElement consumerIdSecondRecordValue;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody[3]/tr/td[3]/p/b")
    public WebElement consumerIdThirdRecordValue;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody[4]/tr/td[3]/p/b")
    public WebElement consumerIdFourthRecordValue;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody[5]/tr/td[3]/p/b")
    public WebElement consumerIdFifthRecordValue;

    @FindBy(xpath = "//*[contains(text(), 'Please resolve the following:')]")
    public WebElement ErrorTextMsg;

    @FindBy(xpath = "//div[@class='col py-2 px-3 ml-3']/span[@id='client-snackbar']")
    public WebElement noparamsText;

    @FindBy(xpath = "//div[@class='col-12 px-5 mt-2']/p[@class='px-3']")
    public WebElement warningText;

    @FindBy(xpath = "//*[@name='zipCode']//..")
    public WebElement zipCode;

    @FindBy(xpath = "//*[@id='contact-reason']/..") //input[@name='contactReason']
    public By contactReasons;

    @FindBy(id = "mui-component-select-contactReason")
    public WebElement contactReason;

    @FindBy(id = "mui-component-select-inboundCallQueue")
    public WebElement inboundCallQueueDropDownSelection;

    @FindBy(xpath = "//*[@id='contactCampaignType']/..")
    public WebElement callCampaignDropDown;

    @FindBy(xpath = "//*[@id='contactOutcome']/..")
    public WebElement outcomeOfContactDropDown;

    @FindBy(id = "mui-component-select-contactAction")
    public WebElement contactAction;

    @FindBy(xpath = "//div[contains(@id,'contactAction-')]")
    public WebElement editContactaction;

    @FindBy(id = "comments")
    public WebElement otherReasonComments;

    @FindBy(id = "notes")
    public WebElement reasonsComments;

    @FindBy(id = "comments")
    public WebElement reasonsCommentsOnEditPage;

    @FindBy(xpath = "(//*[@name='notes'])[2]")
    public WebElement editReasonsComments;

    @FindBy(xpath = "(//*[text()='chat'])[2]/ancestor::div[1]/following-sibling::div")
    public WebElement expandreasonsComments;

    @FindBy(xpath = "(//*[text()='textsms'])[2]//ancestor::div[@tabindex='0']//following-sibling::div[contains(@class,'Mui-expanded MuiIconButton-edgeEnd')]")
    public WebElement expandAdditionalComments;

    @FindBy(xpath = "(//*[@id='comments'])[3]")
    public WebElement additionalCommentsEditTextBox;

    //@FindBy(xpath = "//h6[contains(text(),'CST')]//span")
    @FindBy(xpath = "(//i[text()='textsms']/ancestor::div[1]/following-sibling::div)[2]")
    public WebElement additionalCommentsbox;

    @FindBy(xpath = "//*[contains(text(), 'Information Request')]")
    public WebElement viewActionTextInformationRequest;

    //@FindBy(css = ".mx-comments-reason:nth-of-type(1)")
    @FindBy(xpath = "//*[contains(text(), 'REASONS')]/../..")
    public WebElement expendReasonButton;

    @FindBy(xpath = "//*[contains(@class, 'MuiAccordionSummary-content Mui-expanded')]//i[contains(@title, 'Reasons')]")
    public WebElement expandEditReasonButton;

    @FindBy(xpath = "//*[contains(text(), 'edit')]")
    public WebElement editCreatedCommentBtn;

    @FindBy(xpath = "//*[contains(@id,'notes-')]")
    public WebElement editExistingComment;

    @FindBy(xpath = "(//*[contains(@id,'notes')])[2]")
    public WebElement editExistingCommentNew;

    @FindBy(xpath = "//*[contains(@title, ' Edit Reason')]")
    public WebElement saveEditReasonButton;

    // @FindBy(xpath= "//h6[contains(text(),'CST')]//i//ancestor::div[@tabindex='0']//following-sibling::div//input[@name='contactReason']//parent::div//div")
    @FindBy(xpath = "(//*[contains(@id,'contactReason')]/..)[2]")
    public WebElement expendcontactReasonDropDown;

    @FindBy(xpath = "//li[text()='Information Request']")
    public WebElement expendcontactReasonVauleInformationRequest;

    @FindBy(xpath = "//p[contains(text(), 'Comments')]")
    public WebElement savedCommentsText;

    @FindBy(xpath = "//p[contains(text(), 'Additional Comments')]")
    public WebElement savedAdditionalComments;

    @FindBy(xpath = "//h6[contains(text(), 'ADDITIONAL COMMENTS')]")
    public WebElement expendAdditionalCommentsButton;

    // @FindBy(xpath = "//h6[text()='ADDITIONAL COMMENTS']/../../following-sibling::div//textarea[@id='comments']")
    @FindBy(id = "additionalComments")
    public WebElement additionalCommentsTextBox;

    //@FindBy(xpath = "(//*[@class='material-icons'])[4]")
    @FindBy(xpath = "(//*[contains(text(), 'check')])[3]")
    public WebElement saveAdditionalComments;

    public WebElement getElementsContactReason(int i) {
        return Driver.getDriver().findElement(By.xpath("//*[@id='menu-contactReason']/div[2]/ul/li[" + i + "]"));
    }

    public WebElement getElementsContactAction(int i) {
//        return Driver.getDriver().findElement(By.xpath("//*[@id='menu-contactAction']/div[2]/ul/li[" + i + "]"));
        return Driver.getDriver().findElement(By.xpath("//p[text()='CONTACT ACTION']//following-sibling::h6"));
    }

    public WebElement getElementsConsumerType(int i) {
        return Driver.getDriver().findElement(By.xpath("//*[@id='menu-consumerType']/div[2]/ul/li[" + i + "]"));
    }

    public WebElement getElementsContactType(int i) {
        return Driver.getDriver().findElement(By.xpath("(//*[@id=\"menu-inboundCallQueue\"]/div[3]/ul/li)[" + i + "]"));
    }

    public WebElement getElementsInBoundCall(int i) {
        return Driver.getDriver().findElement(By.xpath("//*[@id='menu-callQueueType']/div[2]/ul/li[" + i + "]"));
    }

    public WebElement getElementContactChannelType(int i) {
        return Driver.getDriver().findElement(By.xpath("//*[@id='menu-contactChannelType']/div[2]/ul/li[" + i + "]"));
    }

    public WebElement getElementsPrefferedLangauge(int i) {
        return Driver.getDriver().findElement(By.xpath("//*[@id='mui-component-select-preferredLanguageCode']/div[2]/ul/li[" + i + "]"));
    }

    public WebElement getElementsContactDispostions(int i) {
        return Driver.getDriver().findElement(By.xpath("//*[@id='menu-contactDispositions']/div[2]/ul/li[" + i + "]"));
    }

    //      @FindBy(xpath = "(//*[@class='mx-section-header float-left'])[7]")
    @FindBy(xpath = "(//div[@class='mx-header__datetime mx-rightborder']/p)[2]")
    public WebElement timeStamp;
    //12/14 -locator not working , created new locator readTextContactReason  & readTextAdditionalComments
    @FindBy(xpath = "//*[@class='mt-3 mb-0 mx-color-text-primary text-capitalize']")
    public WebElement reasonsDisplayed;

    //    @FindBy(xpath = "//p[contains(text(), 'If you continue, all the captured information will be lost')]")
    @FindBy(xpath = "//*[contains(text(),'Your Contact Reasons, Actions, Comments and/or Additional Comments will not be saved.')]")
    public WebElement informationLostMessage;

    @FindBy(xpath = "//a[contains(text(), 'Continue')]")
    public WebElement continueButtonReasons;

    @FindBy(xpath = "//*[contains(text(), 'CANCEL')]")
    public WebElement cancelButtonReasons;

    @FindBy(xpath = "(//*[@class='jss131'])[7]")
    public WebElement expandSavedReason;

    @FindBy(xpath = "//p[contains(text(), 'Comments')]")
    public WebElement commentsDisplayedBottom;

    //@FindBy(xpath = "(//*[@class='material-icons'])[3]")
    //@FindBy(xpath = "//h6[text()='REASONS']//ancestor::div[@tabindex='0']//following-sibling::div//button[2]/i")
    //Refactored by Paramita - CP_1155  Date -08/01/2020
    @FindBy(xpath = "(//span[text()='check'])[2]")
    public WebElement saveReasonButton;

    @FindBy(xpath = "//h6[text()='ADDITIONAL COMMENTS']//ancestor::div[@tabindex='0']//following-sibling::div//button//span[contains(text(),'check')]")
    public WebElement saveAditionalCommentsButton;

    @FindBy(xpath = "(//*[contains(text(),'chat')])[2]/ancestor::div[@tabindex='0']/following-sibling::div//button/span[contains(text(),'check')]")
    public WebElement expandreasonSaveButton;

    @FindBy(xpath = "(//span[text()='check'])[4]")
    public WebElement expandcommentSaveButton;

    //12-14 updated the locator ; not working ealier
    @FindBy(xpath = "//h6[text()='REASONS']//ancestor::div[@tabindex='0']//following-sibling::div//button//span[contains(text(),'close')]")
    public WebElement cancelButtonReason;

    @FindBy(xpath = "//p[text()='CONTACT REASON']/../h6")
    public WebElement readTextContactReason;

    @FindBy(xpath = "//p[text()='CONTACT ACTION']/../h6")
    public WebElement readTextContactAction;

    @FindBy(xpath = "//p[text()='COMMENTS']/..//h6")
    public WebElement readTextComments;

    @FindBy(xpath = "//p[text()='COMMENTS']//following-sibling::h6")
    public WebElement readTextAdditionalComments;

    @FindBy(xpath = "//input[@id='consumerType']/..")
    public WebElement consumerType;

    @FindBy(xpath = "//*[@id='mui-component-select-contactRecordType']")
    public WebElement contactType;

    @FindBy(xpath = "//*[@id='mui-component-select-inboundCallQueue']")
    public WebElement callQueueType;

    @FindBy(xpath = "//*[@id='mui-component-select-contactChannelType']")
    public WebElement contactChannelType;

    @FindBy(id = "mui-component-select-preferredLanguageCode")
    public WebElement preferredLanguage;

    @FindBy(name = "consumerAuthenticated")
    public WebElement consumerAuthenticate;

    @FindBy(xpath = "//*[contains(text(),'CONSUMER AUTHENTICATED')]")
    public WebElement consumerAuthenticatedGreenButton;

    @FindBy(id = "mui-component-select-contactRecordStatusType")
    public WebElement contactDispositions;

    @FindBy(id = "mui-component-select-contactCampaignType")
    public WebElement callCampaignReference;

    @FindBy(id = "mui-component-select-contactOutcome")
    public WebElement outcomeOfContact;

    //Error message locators
    @FindBy(xpath = "//span[contains(text(), 'Please enter the search parameters')]")
    public WebElement blankSearchError;

    @FindBy(xpath = "//span[contains(text(),'Please resolve the following:')]")
    public WebElement headerError;

    @FindBy(xpath = "//*[contains(text(),'SSN must be 9 characters')]")
    //@FindBy(xpath = "//label[contains(text(),'Social Security Number')]/following::p[contains(text(),'SSN should be 9 char')]")
    public WebElement ssnError;

    @FindBy(xpath = "//p[contains(text(),'Invalid date format')]")
    public WebElement dobError;

    //Refactored by Shruti 01/02 changed text
    //@FindBy(xpath = "//input[@name='contactReason']/following::p[contains(text(),'Fill out all Required Fields')]")
    @FindBy(xpath = "//*[contains(text(),'CONTACT REASON is required and cannot be left blank')]")
    public WebElement contactReasonError;

    //@FindBy(xpath = "//input[@name='contactAction']/following::p[contains(text(),'Fill out all Required Fields')]")
    @FindBy(xpath = "//*[contains(text(),'CONTACT ACTION is required and cannot be left blank')]")
    public WebElement contactActionError;

    @FindBy(xpath = "//*[contains(text(),'COMMENTS are required and cannot be left blank')]")
    public WebElement additionalCommentsError;

    //    @FindBy(xpath="//span[@class='jss29']//span[@class='material-icons jss71']")
    //    public WebElement hamBurgerMenu;
//    @FindBy(xpath = "//span[contains(text(), 'menu')]")
    @FindBy(xpath = "//span[contains(text(), 'more_vertz')]")
    public WebElement hamBurgerMenu;

    @FindBy(xpath = "//span[contains(text(), 'CREATE TASK')]")
    public WebElement createATask;

    @FindBy(xpath = "//span[contains(text(), 'MORE OPTION')]")
    public WebElement moreOption;

    @FindBy(xpath = "//h6[contains(text(), 'CONSUMER ID')]")
    public WebElement consumerID;

    @FindBy(xpath = "//p[@class='my-0 mb-0 mx-color-text-primary']/following-sibling::*[1]")
    public WebElement Linked;

    public WebElement getFirstNameLinkedContactRecord(String name) {
        return Driver.getDriver().findElement(By.xpath("//h6[contains(text(),'" + name + "')]"));
    }

    @FindBy(xpath = "//p[text()='CONTACT REASON']/../h6")
    public WebElement savedReason;

    @FindBy(xpath = "(//span[@class='MuiIconButton-label']/parent::div)[3]")
    public WebElement expandSavedReasonAction;

    @FindBy(xpath = "//p[text()='CONTACT ACTION']/../h6")
    public WebElement savedAction;

    @FindBy(xpath = "(//*[contains(@*, 'mx-color-text-primary text-capitalize')])[2]")
    public WebElement savedAddComments;

    @FindBy(xpath = "//div[contains(text(),'Phone')]")
    public WebElement phoneText;

    @FindBy(xpath = "//*[contains(text(),'Inbound')]")
    public WebElement InboundText;

    //refactoring/smoke adjustment 11/27/18
    @FindBy(xpath = "//*[@class='jss201 mx-accord-arrow mt-1']")
    public WebElement expandSavedComments;

    @FindBy(xpath = "//label[text()='CONTACT REASON']")
    public WebElement contactReasonLabel;

    @FindBy(xpath = "//label[text()='CONTACT ACTION']")
    public WebElement contactActionLabel;

    @FindBy(xpath = "(//label[text()='COMMENTS'])[2]")
    public WebElement commentsLabel;

    @FindBy(xpath = "//label[text()='CHANNEL']")
    public WebElement contactChannelTypeLabel;

    @FindBy(xpath = "//label[text()='DISPOSITION']")
    public WebElement contactDispositionsLabel;

    @FindBy(xpath = "//label[text()='CONTACT TYPE']")
    public WebElement contactTypeLabel;

    @FindBy(xpath = "//label[text()='PHONE']")
    public WebElement contactPhoneLabel;

    //    @FindBy(id="formatted-phone-input")
    @FindBy(id = "contactPhone")
    public WebElement phoneNumber;

    @FindBy(name = "contactEmail")
    public WebElement emailID;

    @FindAll({
            @FindBy(xpath = "(//tr/td[3]")
    })
    public List<WebElement> allConsumerID;

    @FindBy(xpath = "(//img[@class='mx-dashboard-logo'])[2]")
    public WebElement bottomCrmLogo;

    @FindBy(xpath = "//h5[@class='mx-section-header float-left']")
    public WebElement topContactRecordLogo;

    @FindBy(xpath = "//*[contains(text(),'WRAP-UP AND CLOSE')]/../..")
    public WebElement expandWrapUpAndClose;

    @FindBy(xpath = "//table/tbody/tr[1]/td[2]")
    public WebElement contactRecordFromContactDetails;

    @FindBy(xpath = "(//span[contains(text(),'visibility_off')])[1]")
    public WebElement ssnVisibilityOff;

    @FindBy(xpath = "(//span[contains(text(),'visibility_off')])[2]")
    public WebElement dobVisibilityOff;

    @FindBy(xpath = "(//div[contains(@class,'comments-reason')]/div)[5]")
    public WebElement contactReason1Expand;

    @FindBy(xpath = "(//div[contains(@class,'comments-reason')]/div)[7]")
    public WebElement contactReason2Expand;

    @FindBy(xpath = "(//div[contains(@class,'comments-reason')]/div)[9]")
    public WebElement contactReason3Expand;

    public WebElement getContactRecordText(String text) {
        return Driver.getDriver().findElement(By.xpath("//*[contains(text(),'" + text + "')]/following-sibling::*"));
    }

    public WebElement getVisiblityOffButton(String ssnordob) {
        return Driver.getDriver().findElement(By.xpath("//*[contains(text(),'" + ssnordob + "')]/following-sibling::*/button"));
    }

    public WebElement getElementContainingText(String text) {
        return Driver.getDriver().findElement(By.xpath("//*[contains(text(),'" + text + "')]"));
    }

    public WebElement getElementContainingTextOAllNodesInNodeSet(String text) {
        return Driver.getDriver().findElement(By.xpath("//*[text()[contains(.,'" + text + "')]]"));
    }

    public WebElement getContactReasonElementContainingText(int count) {
        return Driver.getDriver().findElement(By.xpath("(//p[text()[contains(.,'Contact Reason')]]/following-sibling::p)[" + count + "]"));
    }

    public WebElement getContactReasonActionElementContainingText(int count) {
        //return Driver.getDriver().findElement(By.xpath("(//span[@class='jss453'])["+count+"]"));
        return Driver.getDriver().findElement(By.xpath("(//div[contains(@class,'grey') and @role='button']/span)[" + count + "]"));
    }

    public WebElement getContactReasonExpandElement(int count) {
        return Driver.getDriver().findElement(By.xpath("(//*[text()[contains(.,'Contact Reason')]]/ancestor::div/attribute::aria-expanded/..)[" + count + "]"));
    }

    @FindBy(xpath = "//p[contains(text(),'Contact Type')]/following-sibling::*")
    public WebElement inOrOutBoundContactType;

    @FindBy(xpath = "//p[contains(text(),'Campaign Reference')]/following-sibling::*")
    public WebElement campaignRefernce;

    @FindBy(xpath = "//*[text()='SUCCESS MESSAGE']")
    public WebElement successSnackBar;


    public Map<String, WebElement> getLocatorsContactDetails() {
        Map<String, WebElement> locators = new HashMap<>();
        locators.put("contactType", contactType);
        locators.put("inboundCallQueue", callQueueType);
        locators.put("contactChannel", contactChannelType);
        locators.put("phoneNumber", phoneNumber);
        locators.put("contactDispositions", contactDispositions);
        locators.put("contactCampaignType", callCampaignReference);
        locators.put("selectProgram", lstSelectProgram);
        locators.put("successSnackBar", successSnackBar);
        return locators;
    }

    public Map<String, WebElement> getLocatorsReasons() {
        Map<String, WebElement> locators = new HashMap<>();
        locators.put("contactReason", contactReason);
        locators.put("contactAction", contactAction);
        locators.put("reasonComments", reasonsComments);
        return locators;
    }

    //New locators @1288

    @FindBy(id = "mui-component-select-programTypes")
    public WebElement lstSelectProgram;


    @FindBy(xpath = "//div[@id='menu-programTypes']//ul/li")
    public List<WebElement> lstSelectProgramValues;


    @FindBy(xpath = "//*[contains(text(),'PROGRAM is required and cannot be left blank')]")
    public WebElement lblSelectProgramMessage;

    @FindBy(xpath = "//span[contains(@class, 'drawer-recordicon')]/span")
    public WebElement icnRecordingIcon;

    @FindBy(xpath = "//span[text()='sms']")
    public List<WebElement> icnSms;

    @FindBy(xpath = "//div[contains(@id,'programTypes')]/div/div/span")
    public List<WebElement> lblSelectedPrograms;

    //By Vinuta, locators for CRM-1088
    @FindBy(xpath = "//p[text()='PHONE is required and cannot be left blank']")
    public WebElement phoneNumberBlankError;

    @FindBy(xpath = "//p[text()='PHONE must be 10 characters']")
    public WebElement phoneNumberIncorrectError;

    @FindBy(xpath = "//p[text()='EMAIL is required and cannot be left blank']")
    public WebElement emailBlankError;

    @FindBy(xpath = "//p[text()='Invalid Format Text']")
    public WebElement emailIncorrectError;

    @FindBy(xpath = "(//input[@type='radio'])[1]")
    public WebElement rdoMember;

    @FindBy(xpath = "(//span[contains(text(),'Joan Riley')]/parent::*/span)[1]")
    public WebElement rdoJoanRiley;

    @FindBy(name = "0_caDob")
    //  //*[@id='caseConsumerSearch']/div/div[1]/div[2]/div/table/tbody/tr[2]/td/div/div[8]/div/div/table/tbody/tr[1]/td[3]/label/span[1]/span[1]/input
    public WebElement DOBcheckBox;

    @FindBy(name = "0_caConsumerFullName")
    public WebElement ConsumerFullNameCheckBox;

    @FindBy(name = "0_caCrmConsumerId")
    public WebElement ConsumerIdCheckBox;

    @FindBy(xpath = "//span[contains(text(),'State CASE ID')]/../span/span/input")
    public WebElement CaseIdCheckBox;

    @FindBy(name = "caHomeAddress")
    public WebElement HomeAddressCheckBox;

    @FindBy(xpath = "//input[@name='0_caDob']")
    public WebElement coverVADOBCheckBoxNew;

    @FindBy(xpath = "(//input[@name='0_caDob'])[1]")
    public WebElement coverVAHomeAddressCheckBox;

    @FindBy(xpath = "(//input[@name='0_caDob'])[2]")
    public WebElement coverVADOBCheckBox;

    @FindBy(name = "0_caPhonenumber")
    public WebElement PhoneNumberCheckBox;

    @FindBy(name = "0_caSsn")
    //*[@id=\"caseConsumerSearch\"]/div/div[1]/div[2]/div/table/tbody/tr[2]/td/div/div[8]/div/div/table/tbody/tr[1]/td[4]/label/span[1]/span[1]/input")
    public WebElement SSNcheckBox;

    @FindBy(xpath = "//input[@name='0_caSsn']")
    public WebElement coverVASSNcheckBox;

    @FindBy(xpath = "(//span[@class='MuiTypography-root MuiFormControlLabel-label MuiTypography-body1'])[1]")
    public WebElement HomeAddressVal;

    @FindBy(xpath = "(//span[@class='MuiTypography-root MuiFormControlLabel-label MuiTypography-body1'])[2]")
    public WebElement ExternalCaseIdVal;

    @FindBy(xpath = "(//span[@class='MuiTypography-root MuiFormControlLabel-label MuiTypography-body1'])[2]")
    public WebElement ConsumerFullNameVal;

    @FindBy(xpath = "(//span[@class='MuiTypography-root MuiFormControlLabel-label MuiTypography-body1'])[3]")
    public WebElement ConsumerDOBVal;

    @FindBy(xpath = "(//span[@class='MuiTypography-root MuiFormControlLabel-label MuiTypography-body1'])[4]")
    public WebElement ConsumerSSNVal;

    @FindBy(xpath = "(//span[@class='MuiTypography-root MuiFormControlLabel-label MuiTypography-body1'])[5]")
    public WebElement ConsumerPhoneNumberVal;

    @FindBy(xpath = "(//span[@class='material-icons MuiIcon-root float-right mt-3'])[3]")
    public WebElement ConsumerDOBShowBtn;

    @FindBy(xpath = "(//span[@class='material-icons MuiIcon-root float-right mt-3'])[4]")
    public WebElement ConsumerSSNShowBtn;

    @FindBy(xpath = "//p[@class='mx-link']")
    public WebElement caseId;

    @FindBy(xpath = "(//p[@class='mx-overflow-text mb-0'])[1]")
    public WebElement caseIdFirstRowLink;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/../div/following-sibling::div/div/table/tbody/tr[1]/td[2]")
    public WebElement caseIdFirstRecord;

    // newly added on 01/18 15.0
    @FindBy(xpath = "//div[@id='menu-callQueueType']//ul/li")
    public List<WebElement> callQueueTypeDropDownValues;

    @FindBy(xpath = "//div[@id='menu-contactCampaignType']//ul/li")
    public List<WebElement> callCampaignReferenceDropDownValues;

    @FindBy(xpath = "//div[@id='menu-contactOutcome']//ul/li")
    public List<WebElement> outcomeOfContactDropDownValues;

    @FindBy(xpath = "//input[@name='contactEmail']")
    public WebElement email;

    //    @FindBy(xpath = "//p[text()='CRM Consumer Id']/following-sibling::h6")
    @FindBy(xpath = "(//*[contains(@class , 'mdl-data-table mdl-js-data-table mx-border-6dp table')])//tr[1]//td[2]")
    public WebElement lblCrmConsumerId;

    @FindBy(xpath = "//div[contains(@class,'mx-layout-top-drawer-fields float-left')]/*[1]")
    public WebElement linkedConsumerNameOnHeader;

    @FindBy(xpath = "//div[contains(text(),'General')]/..")
    public WebElement contactRecordType;

    @FindBy(xpath = "//*[contains(text(), 'UNIDENTIFIED CONTACT')]")
    public WebElement selectUnidentifiedContactRecordType;

    @FindBy(xpath = "//*[@id=\"menu-contactRecordType\"]/div[2]/ul/li[4]")
    public WebElement getSelectedContactRecordType;

    @FindBy(xpath = "//*[contains(text(), 'CALLER TYPE')]")
    public WebElement callerTypeLabel;

    @FindBy(xpath = "//p[text()='CALLER TYPE']/following-sibling::h6")
    public WebElement callerTypeValue;

    @FindBy(xpath = "//span[contains(text(),'assignment')]")
    public WebElement taskManagementInsilderBar;

    @FindBy(xpath = "//span[contains(text(),'call')]")
    public WebElement activeContactInsilderBar;

    @FindBy(xpath = "//span[contains(text(),'group')]")
    public WebElement caseConsumerInsilderBar;

    @FindBy(xpath = "//span[contains(text(),'person')]")
    public WebElement contactRecordInsilderBar;

    //Advance Search elements
//    @FindBy(xpath = "(//span[contains(text(), 'search')]/ancestor::button)[1]")
    @FindBy(xpath = "(//span[contains(text(), 'search')]/..)[1]/..")
    public WebElement btnAdvancedSearch;

    @FindBy(id = "mui-component-select-consumerCity")
    public WebElement lstConsumerCity;

    @FindBy(id = "mui-component-select-consumerState")
    public WebElement lstConsumerState;

    @FindBy(id = "mui-component-select-consumerCounty")
    public WebElement lstConsumerCounty;

    @FindBy(id = "mui-component-select-zipCode")
    public WebElement lstZipCode;

    @FindBy(xpath = "//*[@id=\"menu-consumerState\"]/div[3]/ul/li")
    public List<WebElement> lstConsumerStateValues;

    @FindBy(xpath = "//div[@id='menu-consumerCity']//ul/li")
    public List<WebElement> lstConsumerCityValues;

    @FindBy(xpath = "//div[@id='menu-consumerCounty']//ul/li")
    public List<WebElement> lstConsumerCountyValues;

    @FindBy(xpath = "//div[@id='menu-zipCode']//ul/li")
    public List<WebElement> lstZipCodeValues;

    @FindBy(xpath = "//input[@type='radio']/..")
    public WebElement lstFirstConsumerNameRadioButton;

    @FindBy(xpath = "//div[10]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]")
    //div[@class='jss31 jss3198 jss32 jss25']")
    public WebElement searchResultViewDropdown;

    @FindAll({
            @FindBy(xpath = "//th/span[contains(text(),'CASE ID')]/parent::th/parent::tr/parent::thead/parent::table/tbody/tr")
    })
    public List<WebElement> listOfCaseID;

    @FindAll({
            @FindBy(xpath = "//div[1]/parent::div/table/thead/tr/th")
    })
    public List<WebElement> serachHdr;


    @FindBy(xpath = "//span[contains(text(),'chevron_right')]")
    public WebElement expandSearchResult;


    @FindAll({
            @FindBy(xpath = "//th[contains(text(),'FULL NAME')]/parent::tr/parent::thead/parent::table/tbody/tr")
    })
    public List<WebElement> listOfSearchhDtlTbl;

    @FindBy(xpath = "//*[@id='contact-record-type']/..")
    public WebElement contactRecordTypeDropDown;


    @FindBy(xpath = "//*[@id='menu-list-button']")
    public WebElement secondaryGlobNav;

    @FindBy(xpath = "//*[contains(text(), 'ACTIVE CONTACT')]")
    public WebElement activeContactWidzard;

    @FindBy(xpath = "//span[contains(text(),'call')]")
    public WebElement leftMenuCallIcon;

    public boolean activeContactWidzardVisible() {
        try {
            activeContactWidzard.isDisplayed();
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    @FindBy(xpath = "(//div[@id='acwidget']/following-sibling::div//p)[1]")
    public WebElement activeContactWidzardStartTime;

    @FindBy(xpath = "(//div[@id='acwidget']/following-sibling::div//p)[2]")
    public WebElement activeContactWidzardTimer;

    @FindBy(xpath = "//*[contains(@class, 'mdl-button mdl-js-button mdl-button--icon float-right collapsed mr-2')]")
    public WebElement activeContactWidzardMinimize;

    @FindBy(xpath = "(//div[@id='acwidget']//a)[1]")
    public WebElement activeContactWidzardCaseConsumerName;

    @FindBy(xpath = "(//div[@id='acwidget']//a)[2]")
    public WebElement activeContactWidzardCaseConsumerId;

    @FindBy(xpath = "//div[@class='my-1 p-2 mx-layout-top-drawer-fields float-left']/p[1]")
    ////div[@class='py-2 px-3 mx-layout-top-drawer-fields float-left']/p[1]
    //div[@class='my-2 pt-1 px-2 mx-layout-top-drawer-fields float-left']/p[1]
    public WebElement activeContactCaseConsumerNameHeader;

    @FindBy(xpath = "//div[@class='my-2 pt-1 px-2 mx-layout-top-drawer-fields float-left']/p[1]")
    public WebElement activeContactCaseConsumerNameHeaderNew;

    @FindBy(xpath = "//div[@class='my-1 p-2 mx-layout-top-drawer-fields float-left']/p[2]")
    //div[@class='py-2 px-3 mx-layout-top-drawer-fields float-left']/p[2]
    //div[@class='my-2 pt-1 px-2 mx-layout-top-drawer-fields float-left']/p[2]
    public WebElement activeContactCaseConsumerIdHeader;

    @FindBy(xpath = "//div[@class='my-2 pt-1 px-2 mx-layout-top-drawer-fields float-left']/p[2]")
    public WebElement activeContactCaseConsumerIdHeaderNew;

    //Aswath
    @FindBy(xpath = "//div/img[@id='mx-logout']")
    public WebElement userIcon;

    @FindBy(xpath = "//span[contains(text(),('arrow_drop_down'))]")
    public WebElement logOutArrow;

    @FindBy(xpath = "//div[@id='logout-menu']/div[2]")
    public WebElement logOutDropDown;

    @FindBy(xpath = "(//div/div/ul/li/i)[2]")
    public WebElement logOutOption;

    @FindBy(xpath = "//span[contains(@class, 'mx-top-drawer-recordicon')]")
    public WebElement recordIcon;

    @FindBy(xpath = "//*[@id='searchCaseType']/..")
    public WebElement searchCaseOptions;

    @FindBy(xpath = "//*[@id='mui-component-select-consumerType']/..")
    public WebElement searchConsumerOptions;

    @FindBy(id = "caseType")
    public WebElement searchCaseTypeValue;

    @FindBy(id = "consumerType")
    public WebElement searchConsumerTypeValue;

    @FindBy(id = "caseId")
    public WebElement searchCaseValue;

    @FindBy(id = "consumerId")
    public WebElement searchConsumerValue;


    @FindBy(xpath = "//*[@id='crmCaseID']")
    public WebElement searchCaseID;

    @FindBy(xpath = "//*[@id='crmConsumerId']")
    public WebElement searchConsumerID;

    @FindBy(xpath = "//*[contains(@for, 'caseId')]")
    public WebElement caseIdValue;

    @FindBy(xpath = "//*[contains(@for, 'consumerId')]")
    public WebElement consumerIdValue;

    @FindBy(xpath = "//span[contains(text(), 'chevron_right')]")
    public WebElement expandButtonforLink;

    @FindBy(xpath = "//input[@name='0_caConsumerFullName']")
    public WebElement fullNameRadioButton;

    @FindBy(xpath = "//input[@name='0_caDob']")
    public WebElement DOBradioButton;

    @FindBy(xpath = "//input[@name='0_caSsn']")
    public WebElement SSNradioButton;

    @FindBy(xpath = "//input[@name='0_caCrmConsumerId']")
    public WebElement consumerIDcheckButton;

    @FindBy(xpath = "//span[@title='Link Consumer']")
    public WebElement linkRecordButtonOnContactRecordUI;

    @FindBy(xpath = "//*[@id='contactReason']/..")  //mui-component-select-contactReason
    public WebElement reasonList;

    @FindBy(xpath = "//li[@data-value='Enrollment - Pediatric Dental']")
    public WebElement contactReasonButton;

    @FindBy(id = "mui-component-select-contactAction")
    public WebElement actionList;

    @FindBy(id = "notes")
    public WebElement commentArea;

    @FindBy(xpath = "//label[text()='COMMENTS']/parent::div/div/input")
    public WebElement editCommentArea;

    @FindBy(xpath = "(//span[contains(text(),'check')]/parent::button)[1]")
    public WebElement reasonSaveButton;

    @FindBy(xpath = "(//span[contains(text(),'check')])[4]")
    public WebElement editReasonSaveButton;

    @FindBy(xpath = "//div//button[@title=' Save Edit Reason']")
    public WebElement editReasonSaveButtonNew;

    @FindBy(xpath = "//p[contains(text(),'Contact Reason')]")
    public WebElement getEditAreaButton;

    @FindBy(xpath = "//span[contains(text(),'edit')]/parent::button")
    public WebElement editReasonActionButton;

    @FindBy(xpath = "//ul[@class='MuiList-root MuiMenu-list MuiList-padding']")
    public WebElement editReasonList;

    @FindBy(id = "sample6")
    public WebElement editTextArea;

    @FindBy(xpath = "(//span[contains(text(),'check')])[3]")
    public WebElement saveAdditionalCommentsButton;

    @FindBy(xpath = "//*[contains(text(), 'edit')]")
    public WebElement editSavedAdditonalCommentsButton;

    @FindBy(xpath = "//*[@class='mb-0 py-0 mx-color-text-primary text-capitalize']")
    public WebElement additionalCommentsTextDrop;

    @FindBy(xpath = "(//*[@class='material-icons'])[5]")
    public WebElement additionalCommentsTextBoxEdit;

    @FindBy(xpath = "(//*[@name='comments'])[3]")
    public WebElement additionalCommentsTextBox2;

    @FindBy(xpath = "//span[@name='0_24209']")
    public WebElement expandConsumerArrow;

    ////span[contains(text(),'edit')]/parent::button
    @FindBy(xpath = "(//i[contains(text(), 'person')])[2]")
    public WebElement latestContactRecord;

    @FindBy(xpath = "//*[@title='Edit Contact']")
    public WebElement editContactButton;

    @FindBy(id = "mui-component-select-contactReasonEditType")
    public WebElement editReasonListContactRecord;

    @FindBy(xpath = "//*[contains(text(),'Save')]")
    public WebElement saveContactRecordEditButton;

    @FindBy(xpath = "(//span[contains(text(),'check')])")
    public WebElement saveConsumerUpdate;

    @FindBy(xpath = "(//span[contains(text(),'clear')])")
    public WebElement cancelConsumerUpdate;

    @FindBy(xpath = "(//span[contains(text(),'check')])[4]")
    public WebElement saveAdditionalEditCommentsButton;

    //Contact Record Search
    @FindBy(name = "crmContactRecordNo")
    public WebElement contactRecordNumber;

    @FindBy(id = "consumerFirstName")
    public WebElement contactFN;

    @FindBy(id = "consumerLastName")
    public WebElement contactLN;

    @FindBy(id = "seaConDOB")
    public WebElement contactDOB;

    @FindBy(xpath = "//*[@id='consumerId']")
    public WebElement crmContactID;

    @FindBy(xpath = "//*[@id='menu-contactType']")
    public WebElement contactTypeMenu;

    @FindBy(xpath = "//*[@id='menu-contactChannelType']")
    public WebElement contactChannelMenu;

    @FindBy(xpath = "//*[@id='menu-contactDispositions']")
    public WebElement contactDispositionsMenu;

    @FindBy(xpath = "//input[@name='contactRecordType']/..")
    public WebElement contactSearchRecordType;

    @FindBy(xpath = "//input[@name='inboundCallQueue']/..")
    public WebElement contactInboundCall;

    @FindBy(xpath = "//*[text()='SEARCH RESULTS']/ancestor::div[1]//table/tbody/tr/td[3]")
    public WebElement contactIdEditFirstRecord;

    @FindBy(xpath = "//span[contains(text(),'EDIT HISTORY')]")
    public WebElement editContactHistoryButton;

    @FindBy(xpath = "//h5[text()='EDIT HISTORY']/following-sibling::div[2]/div/div/table/tbody/tr[1]/td[3]")
    public WebElement contactRecordEditedByNameFirstRow;

    @FindBy(xpath = "//h5[text()='EDIT HISTORY']/following-sibling::div[2]/div/div/table//th[4]")
    public WebElement contactRecordEditedByFieldLabel;

    @FindBy(xpath = "(//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric'])[2]")
    public WebElement contactRecordEditedByReasonForEdit;

    @FindBy(xpath = "//h5[text()='EDIT HISTORY']/following-sibling::div[2]/div/div/table/tbody/tr[3]/td[3]")
    public WebElement contactRecordEditedByNameSecondRow;

    @FindBy(xpath = "(//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric'])[10]")
    public WebElement contactRecordEditedByFieldLabelSecondRow;

    @FindBy(xpath = "(//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric'])[9]")
    public WebElement contactRecordEditedByReasonForEditSecondRow;

    @FindBy(id = "consumerFN")
    public WebElement contactEditFN;

//    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/div/div/div[2]/div[1]/div/div/div[1]/div[8]/div[9]/div[2]/div[1]/div/div")
//    public WebElement contactDispositionButton;

    @FindBy(id = "mui-component-select-contactRecordStatusType")
    public WebElement contactDispositionButton;

    @FindBy(id = "mui-component-select-contactReason")
    public WebElement editContactReason;

    @FindBy(id = "mui-component-select-contactAction")
    public WebElement editContactAction;

    @FindBy(xpath = "(//span[text()='check'])[2]")
    public WebElement saveEditComments;

    @FindBy(xpath = "//span[contains(text(),'edit')]")
    public WebElement editAdditionalComments;

    @FindBy(xpath = "(//span[text()='check'])[3]")
    public WebElement saveEditAdditionalComments;

    @FindBy(xpath = "(//i[text()='person' and @title='Contact Record Link']//parent::td/following-sibling::td)[1]")
    public WebElement consumerIdLink;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div[2]/p")
    public WebElement consumerIdText;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/a/i")
    public WebElement backToContactRecordButton;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/p[1]")
    public WebElement contactRecordNameText;

    @FindBy(xpath = "//*[@id='addConPhone']")
    public WebElement contactPhoneNumber;

    @FindBy(xpath = "//*[@id='addConPhone-helper-text']")
    public WebElement contactPhoneError;

    @FindBy(xpath = "//input[@name='crmContactRecordNo']/..//following-sibling::p")
    public WebElement contactRecordNumberError;

//    @FindBy(xpath = "//*[text()='SEARCH RESULTS']/ancestor::div[1]//table/tbody/tr/td[3]")
//    public WebElement contactIdFirstRecord;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[3]")
    public WebElement contactIdFirstRecord;

    @FindBy(xpath = "//*[text()='SEARCH RESULTS']/ancestor::div[1]//table/tbody/tr[3]/td[3]")
    public WebElement contactIdSecondRecord;

    @FindBy(xpath = "//*[text()='SEARCH RESULTS']/ancestor::div[1]//table/tbody/tr[1]/td[6]")
    public WebElement contactConsumerIdFirstRecord;

    @FindBy(xpath = "//*[text()='SEARCH RESULTS']/ancestor::div[1]//table/tbody/tr[3]/td[6]")
    public WebElement contactConsumerIdSecondRecord;

    @FindBy(xpath = "//*[text()='SEARCH RESULTS']/ancestor::div[1]//table/tbody/tr/td[4]")
    public WebElement contactCreatedOnValue;

    @FindBy(xpath = "//*[text()='SEARCH RESULTS']/ancestor::div[1]//table/tbody/tr/td[5]")
    public WebElement contactCaseIdValue;

    @FindBy(xpath = "//*[text()='SEARCH RESULTS']/ancestor::div[1]//table/tbody/tr/td[6]")
    public WebElement contactConsumerIdValue;

    @FindBy(xpath = "//*[text()='SEARCH RESULTS']/ancestor::div[1]//table/tbody/tr/td[9]")
    public WebElement contactDispositionsValue;

    @FindBy(xpath = "//*[text()='SEARCH RESULTS']/ancestor::div[1]//table/tbody/tr[3]/td[9]")
    public WebElement contactDispositions2ndValue;

    @FindBy(xpath = "//*[text()='SEARCH RESULTS']/ancestor::div[1]//table/tbody/tr/td[8]")
    public WebElement contactRecordTypeValue;

    @FindBy(xpath = "//*[text()='SEARCH RESULTS']/ancestor::div[1]//table/tbody/tr/td[7]")
    public WebElement contactNameValue;

    @FindBy(xpath = "//*[text()='SEARCH RESULTS']/ancestor::div[1]//table/tbody/tr[3]/td[7]")
    public WebElement contactName2ndValue;

    @FindBy(id = "caseId")
    public WebElement contactCaseId;

    @FindBy(xpath = "//button[@class='mdl-button mdl-js-button mdl-button--icon mdl-button--colored mx-grid-accord-arrow']")
    public WebElement contactShowMoreInfo;

    @FindBy(xpath = "//p[contains(text(),'CREATED BY')]/following-sibling::*")
    public WebElement contactCreatedByValue;

    @FindBy(xpath = "//p[contains(text(),'PHONE NUMBER')]/following-sibling::*")
    public WebElement contactPhoneNumberValue;

    @FindBy(xpath = "//p[contains(text(),'INBOUND CALL QUEUE')]/following-sibling::*")
    public WebElement contactInboundCallValue;

    @FindBy(xpath = "//*[text()='SEARCH RESULTS']/ancestor::div[1]//table/tbody/tr[2]/td/div/div/div/div[5]/h6")
    public WebElement contactCallOutcomeValue;

    @FindBy(xpath = "//p[contains(text(),'USER ID')]/following-sibling::*")
    public WebElement contactUserIdValue;

    @FindBy(xpath = "//*[text()='SEARCH RESULTS']/ancestor::div[1]//table/tbody/tr[2]/td/div/div/div/div[7]/h6")
    public WebElement contactEmailValue;

    @FindBy(xpath = "//*[text()='SEARCH RESULTS']/ancestor::div[1]//table/tbody/tr[2]/td/div/div/div/div[8]/p")
    public WebElement contactCallCampaignValue;

    @FindBy(xpath = "//p[contains(text(),'CONTACT ID')]//following-sibling::h6")
    public WebElement contactIdDetailsPage;

    @FindBy(xpath = "//span[contains(text(),'keyboard_backspace')]")
    public WebElement backButton;

    @FindBy(xpath = "//textarea[@name='notes']")
    public WebElement contactAddComments;

    @FindBy(xpath = "//textarea[@name='notes']/../../../../following-sibling::div/button")
    public WebElement contactAddCommentsSave;

    @FindBy(xpath = "//div[@id='mui-component-select-contactReasonEditType']")
    public WebElement contactReasonForEdit;

    @FindBy(xpath = "//div[@id='mui-component-select-reasonForEdit']/../../../following-sibling::div/button")
    public WebElement contactSave;

    @FindBy(xpath = "//span[contains(text(),'EDIT HISTORY')]")
    public WebElement editHistoryButton;

    @FindBy(xpath = "//h5[text()='EDIT HISTORY']/following-sibling::div[2]/div/div/table/tbody/tr[1]/td[3]")
    public WebElement contactEditedByName;

    @FindBy(xpath = "//span[contains(text(),'edit')]")
    public WebElement editButtonForReason;

    @FindBy(xpath = "//div[@class='col-12 my-2']//h1[@class='mx-section-header float-left mt-2']")
    public WebElement unidentifiedContactRecordPageTitle;

    @FindBy(id = "contactPhone")
    public WebElement unidentifiedContactRecordPhoneInput;

    @FindBy(id = "mui-component-select-contactReason")
    public WebElement unidentifiedContactRecordReason;

    @FindBy(id = "mui-component-select-contactAction")
    public WebElement unidentifiedContactRecordAction;

    @FindBy(xpath = "//*[contains(text(), 'Save')]")
    public WebElement saveEditContactRecordBtn;

    @FindBy(id = "phoneNumber")
    public WebElement contactPhoneField;

    @FindBy(xpath = "//p[text()='PHONE']/following-sibling::h6")
    public WebElement unidentifiedContactSearchPhoneField;

    @FindBy(xpath = "//div[@class='col-12 px-0 mt-2']//div[@class='MuiFormControl-root mdl-textfield__input']")
    public WebElement contactRecordEditReason;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/div/div/div[2]/div[1]/div/div/div[2]/div[1]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div")
    public WebElement contactRecordEditAction;

    @FindBy(xpath = "//div[@class='col-12 text-right px-0 pt-3']//button[@class='mdl-button mdl-js-button mdl-button--icon mdl-button--colored mx-btn mx-btn-secondary  mr-2 float-left mx-shadow--1dp btn btn-secondary']")
    public WebElement contactRecordEditReasonSaveBtn;

    @FindBy(xpath = "(//h6[contains(@class, 'mx-section-header mx-border-none float-left pb-0')])[3]")
    public WebElement timeStampforAdditionalComments;

    @FindBy(xpath = "//h5[text()='EDIT HISTORY']/following-sibling::div[2]/div/div/table/thead/tr/th[2]")
    public WebElement contactEditedOn;

    @FindBy(xpath = "//h5[text()='EDIT HISTORY']/following-sibling::div[2]/div/div/table/tbody/tr[1]/td[2]")
    public WebElement contactEditedOnValue;

    @FindBy(xpath = "//h5[text()='EDIT HISTORY']/following-sibling::div[2]/div/div/table/tbody/tr[1]/td[4]")
    public WebElement contactEditedReason;

    @FindBy(xpath = "//h5[text()='EDIT HISTORY']/following-sibling::div[2]/div/div/table/tbody/tr[1]/td[5]")
    public WebElement contactEditedField;

    @FindBy(xpath = "//h5[text()='EDIT HISTORY']/following-sibling::div[2]/div/div/table/tbody/tr[1]/td[6]")
    public WebElement contactEditedPrevValue;

    @FindBy(xpath = "//h5[text()='EDIT HISTORY']/following-sibling::div[2]/div/div/table/tbody/tr[1]/td[7]")
    public WebElement contactEditedCurValue;

    @FindBy(xpath = "//h5[text()='CONTACT EDIT REASONS']/following-sibling::div[2]/table/thead/tr/th[1]")
    public WebElement contactEditReason;

    @FindBy(xpath = "//ul[@class='pagination']/li[2]")
    public WebElement nextPageRecords;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/div/div[1]/div[2]/div[10]/div[2]/div[2]/button[2]/span[1]")
    public WebElement cancelConsumerLook;

    @FindBy(xpath = "/html/body/div[3]/div/div[1]/div/div/div/p/div/button[1]/span[1]")
    public WebElement continueCancelConsumerLook;

    @FindBy(xpath = "//*[@id='contactReasonEditType']//..")
    public WebElement contactRecordReasonForEdit;

    @FindBy(xpath = "(//*[@class='my-1 p-2 mx-layout-top-drawer-fields float-left'])//p[2]")
    public WebElement viewRecordConsumerId;

    @FindBy(xpath = "//*[contains(text(), 'person')]")
    public WebElement viewRecordConsumerIcon;

    @FindBy(xpath = "(//th[contains(text(), 'EDITED BY')])[1]")
    public WebElement contactRecordEditedByField;

    @FindBy(xpath = "//tbody/tr/td[6]")
    public WebElement contactRecordTypeField;

    @FindBy(xpath = "(//table[contains(@class, '--grey-50 mx-border-6dp ')])[2]//td[2]")
    public WebElement contactRecordEditedName;

    public void getEditedByContactRecordType(String contactRecordType) {
        switch (contactRecordType) {
            case "General":
                Assert.assertTrue(contactRecordType.equals("General"));
                break;
            case "Third Party":
                Assert.assertTrue(contactRecordType.equals("Third Party"));
                break;
            case "Unidentified Contact":
                Assert.assertTrue(contactRecordType.equals("Unidentified Contact"));
                break;
        }
    }

    @FindBy(xpath = "//input[@name='Email']")
    public WebElement consumerOptInEmail;

    @FindBy(xpath = "//input[@name='Fax']")
    public WebElement consumerOptInFax;

    @FindBy(xpath = "//input[@name='Mail']")
    public WebElement consumerOptInMail;

    @FindBy(xpath = "//input[@name='Phone']")
    public WebElement consumerOptInPhone;

    @FindBy(xpath = "//input[@name='Text']")
    public WebElement consumerOptInText;

    @FindBy(xpath = "(//div[@class='col-3 col-xxl-2-5 my-4']//h6)[1]")
    public WebElement consumerName;

    @FindBy(xpath = "(//div[@class='col-3 col-xxl-2-5 my-4']//h6)[3]")
    public WebElement consumerAge;

    @FindBy(xpath = "(//div[@class='col-3 col-xxl-2-5 my-4']//h6)[5]")
    public WebElement consumerStartDate;

    @FindBy(xpath = "(//div[@class='col-3 col-xxl-2-5 my-4']//h6)[6]")
    public WebElement consumerEndDate;

    @FindBy(xpath = "//h6[contains(@class,'mdl-data-table__cell--non-numeric mdl-color-text--')]")
    public WebElement consumerStatus; // (//table[contains(@class,'mx-table-treeview table')])[3]

    @FindBy(id = "contactRecordId")
    public WebElement contactRecordId;

    @FindBy(xpath = "//*[p='CONTACT START']/h6")
    public WebElement contactRecordStartDate;

    @FindBy(xpath = "//*[p='CONTACT END']/h6")
    public WebElement contactRecordEndDate;

    @FindBy(xpath = "(//div[@class='row mdl-color--grey-50 mx-border-6dp mdl-shadow--2dp mx-0 py-4 px-2 mt-4 mb-4']//h6[@class='m-0'])[3]")
    public WebElement contactRecordUserId;

    @FindBy(xpath = "(//div[@class='row mdl-color--grey-50 mx-border-6dp mdl-shadow--2dp mx-0 py-4 px-2 mt-4 mb-4']//h6[@class='m-0'])[2]")
    public WebElement contactRecordUserName;

    @FindBy(xpath = "//span[@class='mdl-data-table__cell--non-numeric mx-link mx-width-50']")
    public WebElement contactRecordConsumerId;

    @FindBy(xpath = "//*[p='CONTACT TYPE']/h6")
    public WebElement contactRecordContactType;

    @FindBy(xpath = "(//td[@class='mdl-data-table__cell--non-numeric'])[1]")
    public WebElement contactRecordConsumerName;

    @FindBy(xpath = "//*[p='CHANNEL']/h6")
    public WebElement contactRecordContactChannel;

    @FindBy(xpath = "//*[p='PHONE']/h6")
    public WebElement contactRecordPhone;

    @FindBy(xpath = "(//td[@class='mdl-data-table__cell--non-numeric'])[5]")
    public WebElement contactRecordPrefLanguage;

    @FindBy(xpath = "//*[p='INBOUND CALL QUEUE']/h6")
    public WebElement contactRecordCallQueue;

    @FindBy(xpath = "(//td[contains(@class, 'MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric')])[1]")
    public WebElement consumerSearchFirstRecord;

    @FindBy(xpath = "(//td[contains(@class, 'MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mx-link')])[2]")
    public WebElement consumerSearchSecondRecord;

    @FindBy(xpath = "(//span[contains(text(),'edit')])")
    public WebElement consumerEditButton;

    @FindBy(xpath = "(//span[@class = 'MuiIconButton-label'])[6]")
    public WebElement consumerInactiveImmediateBtn;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')] /ancestor::div[2]//table/tbody/tr[1]/td[4]")
    public WebElement searchResFirstConFN;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[2]//table/tbody/tr[1]/td[5]")
    public WebElement searchResFirstConLN;

    public String getExpectedName() {
        String name = searchResFirstConFN.getText() + " " + searchResFirstConLN.getText();
        return name;
    }

    @FindBy(xpath = "//p[@class='text-right mx-header-userid mdl-color-text--grey-600 lead']")
    public WebElement userId;

    @FindBy(xpath = "//*[@name='0_caConsumerFullName']")
    public WebElement contactMoreInfoName;

    @FindBy(xpath = "//*[@name='0_caDob']")
    public WebElement contactMoreInfoDOB;

    @FindBy(xpath = "//*[@name='0_caSsn']")
    public WebElement contactMoreInfoSSN;

    @FindBy(xpath = "//span[contains(text(), 'LINK RECORD')]")
    public WebElement contactMoreInfoLinkRecord;

    @FindBy(xpath = "//*[@id='menu-list-grow']/ul/li[1]")
    public WebElement createTaskButton;

    @FindBy(xpath = "//div[@id='sub-menu-list']/div/ul/li[1]")
    public WebElement generalTaskButton;

    @FindBy(name = "taskInfo")
    public WebElement generalTaskInfo;

    @FindBy(xpath = "(//span[contains(text(),'UNLINK CONTACT RECORD')])")
    public WebElement unlinkButton;

    @FindBy(xpath = "//p[@class='text-right mx-header-username mx-color-text-secondary lead']")
    public WebElement userName;

    @FindBy(xpath = "(//span[contains(text(),' END CONTACT')])")
    public WebElement endContactButton;

    @FindBy(xpath = "//input[@name='p']/../../following-sibling::span")
    public WebElement lstFirstConsumerName;

    @FindBy(xpath = "//div[@id='mui-component-select-itemsPerPage']")
    public WebElement showItemsDropdown;

    @FindBy(xpath = "//h1[text()='CONTACT DETAILS']")
    public WebElement contactDetailsHeader;

    @FindBy(xpath = "//span[contains(text(), 'call')]")
    public WebElement activeContactHeader;

    @FindBy(xpath = "(//*[contains(@class , 'MuiTableBody-root')])//tr[1]//td[2]")
    public WebElement consumerIdSearch;

    @FindBy(xpath = "(//*[contains(@class , 'MuiTableBody-root')])//tr[1]//td[2]")
    public WebElement consumerIdInitiateContact;

    @FindBy(xpath = "(//*[contains(@class , 'MuiTableBody-root')])//tr[1]//td[3]")
    public WebElement consumerFullNameSearch;

    @FindBy(xpath = "//*[text()='GENERAL - CONSUMER IN CONTACT']/ancestor::div[1]//table/tbody/tr/td[6]")
    public WebElement consumerRole;

    @FindBy(xpath = "//input[@name='list-item-0']")
    public WebElement conEmail;

    @FindBy(xpath = "//input[@name='list-item-1']")
    public WebElement conFax;

    @FindBy(xpath = "//input[@name='list-item-2']")
    public WebElement conMail;

    @FindBy(xpath = "//input[@name='list-item-3']")
    public WebElement conPhone;

    @FindBy(xpath = "//div[@id='mui-component-select-contactCampaignType']")
    public WebElement callCamp;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[2]//table/tbody/tr/td[3]")
    public WebElement conId;

    @FindBy(xpath = "//input[@name='0_caConsumerFullName']")
    public WebElement fullNameCheck;

    @FindBy(name = "0_caCrmConsumerId")
    public WebElement conIdCheckBox;

    @FindBy(xpath = "//button[@class ='mdl-button mdl-js-button mdl-button--icon mdl-button--colored mx-grid-accord-arrow']")
    public WebElement buttonForLink;

    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text mx-btn mx-btn-border mx-color-text-primary mx-btn-cancel ml-3']")
    public WebElement cancelConLook;

    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text mx-btn  mx-btn-border mx-btn-primary float-left mb-4']")
    public WebElement continueCancelConLook;

    @FindBy(xpath = "//*[@id='additionalComments']")
    public WebElement additionalComments;

    @FindBy(id = "comments")
    public WebElement editReasComments;

    @FindBy(xpath = "//th[text()='EDITED ON']")
    public WebElement editedOn;

    @FindBy(xpath = "//button[@class='mdl-button mdl-js-button mdl-button--icon mdl-button--colored mx-grid-accord-arrow']")
    public WebElement contactShowMoreInfoBtn;

    @FindBy(xpath = "//h3[@class='mx-color-text-primary mt-4 pt-3 mb-2']")
    public WebElement sessionExpired;

    @FindBy(xpath = "//*[@id=\"1000000439\"]/span[1]/span[1]/input")
    public WebElement thirdPartyConsumerRadioButton;

    @FindBy(xpath = "//span[text()[contains(.,'VALIDATE')]]")
    public WebElement validateLinkButton;

    @FindBy(xpath = "(//input[@class='MuiInputBase-input MuiInput-input'])[4]")
    public WebElement editContactFirstName;

    @FindBy(xpath = "(//input[@class='MuiInputBase-input MuiInput-input'])[5]")
    public WebElement editContactLastName;

    @FindBy(xpath = "//tr[1]/td[3]")
    public WebElement firstContactRecordTable;

    @FindBy(xpath = "//*[contains(text(), 'VALIDATE')]")
    public WebElement validate;

    @FindBy(xpath = "//span[text()='VALIDATE & LINK']")
    public WebElement validateAndLink;

    @FindBy(xpath = "//*[contains(text(), 'LINK TO CASE ONLY')]")
    public WebElement linkTaskToCaseOnly;

    @FindBy(xpath = "//span[text()='LINK CASE/CONSUMER']")
    public WebElement linkCaseConsumer;

    @FindBy(xpath = "//*[contains(text(),'CONSUMER AUTHENTICATED')]")
    public WebElement consumerAuth;

    @FindBy(xpath = "//input[@name='0_caConsumerFullName']")
    public WebElement CSRcontactMoreInfoName;

    @FindBy(xpath = "//input[@name='0_caDob']")
    public WebElement csrcontactMoreInfoDOB;

    @FindBy(xpath = "//input[@name='0_caSsn']")
    public WebElement csrcontactMoreInfoSSN;

    @FindBy(xpath = "//span[contains(text(),'VALIDATE & LINK')]")
    public WebElement CSRcontactMoreInfoLinkRecord;

    @FindBy(xpath = "//a[contains(text(),'Case & Contact Details')]")
    public WebElement CSRcaseAndContactDetails;

    @FindBy(xpath = "//th[text()='PREFERRED LANGUAGE']")
    public WebElement preferredLanguageTH;

    @FindBy(xpath = "//h5/span")
    public WebElement pageHeader;

    @FindBy(xpath = "(//td[3])[1]")
    public WebElement consumerIdFirstRecord;

    @FindBy(xpath = "(//label[text()='CONTACT REASON']/parent::div/div)[2]")
    public WebElement expandContactReasonDropDownUpdate;

    @FindBy(xpath = "(//label[text()='CONTACT ACTION']/parent::div/div)[2]")
    public WebElement expandContactActionDropDownUpdate;

    @FindBy(xpath = "//th[text() = 'GetInsured CASE ID']/parent::tr/parent::thead/following::tbody/tr/td[1]/span")
    public WebElement getInsuredCaseId;

    @FindBy(xpath = "//th[text() = 'GetInsured CASE ID']/parent::tr/parent::thead/following::tbody/tr/td[2]/span")
    public WebElement getInsuredConsumerId;

    @FindBy(xpath = "//p[@class='mx-link']")
    public WebElement caseIdAfterLink;

    @FindBy(xpath = "//th[text()='PHONE NUMBER']/parent::tr/parent::thead/following::tbody/tr/td[5]/label")
    public WebElement labelPhoneNum;

    @FindBy(xpath = "//div[4]/div[1]/div[1]/div[2]/div[2]/button[2]")
    public WebElement cancelButtonActiveContact;

    @FindBy(xpath = "//div[3]/div[1]/table[1]/tbody[1]/tr[1]/td[3]")
    public WebElement nameAfterLink;
    @FindBy(xpath = "//p[contains(text(),'If you continue, the Contact Record will not be saved.')]")
    public WebElement cancelButtonWarningMessage;

    @FindBy(xpath = "//span[text()[contains(.,'Continue')]]")
    public WebElement cancelButtonContinue;

    @FindBy(xpath = "(//textarea[@class='MuiInputBase-input MuiInput-input MuiInputBase-inputMultiline MuiInput-inputMultiline'])[3]")
    public WebElement editAdditionalCommentText;

    @FindBy(xpath = "//div[contains(@class,'col-3 mt-4')]//div[4]//button[1]")
    public WebElement reasonSvBttn;

    @FindBy(xpath = "//span[text() = 'GetInsured CASE ID']/parent::th/parent::tr/parent::thead//following::tbody/tr/td[2]/p")
    public WebElement getInsuredCaseIdCCSearch;

    @FindBy(xpath = "//span[text() = 'GetInsured CASE ID']/parent::th/parent::tr/parent::thead//following::tbody/tr/td[3]/p")
    public WebElement getInsuredConsumerIdCCSearch;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')/..//tr[1]/td[2]")
    public WebElement CRMCaseIdFirst;

    @FindBy(xpath = "//th[text()='SPOKEN LANGUAGE']")
    public WebElement spokenLanguage;

    @FindBy(xpath = "//th[text()='WRITTEN LANGUAGE']")
    public WebElement writtenLanguage;

    public WebElement individualName(String name) {
        String[] sep = name.split(" ");
        waitFor(1);
        return Driver.getDriver().findElement(By.xpath("//p[contains(text(),'" + sep[1] + "')]"));
    }

    public WebElement headerCaseId(String id) {
        waitFor(1);
        return Driver.getDriver().findElement(By.xpath("//p[contains(text(),'" + id + "')]"));
    }

    public WebElement generalCaseId(String id) {
        waitFor(1);
        return Driver.getDriver().findElement(By.xpath("//span[contains(text(),'" + id + "')]"));
    }

    @FindBy(id = "createdOn")
    public WebElement createdOn;

    @FindBy(xpath = "//table//*[text()='Active']")
    public WebElement selectCreatedConsumer;

    @FindBy(id = "tt3")
    public WebElement profileCreatedBy;

    @FindBy(xpath = "(//td[5])[1]")
    public WebElement searchResultMI;

    @FindBy(xpath = "//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mx-link']")
    public WebElement linkedConsumerId;

    @FindBy(xpath = "//h5[text()='PROFILE DETAILS']")
    public WebElement profileDetailsHeaders;

    @FindBy(xpath = "//h5[text()='COMMUNICATION OPT-IN INFORMATION']")
    public WebElement comOptInInfoHeader;

    @FindBy(xpath = "//h5[text()='ADDRESS']")
    public WebElement addressHeader;

    @FindBy(xpath = "//h5[text()='PHONE NUMBER']")
    public WebElement phoneNumberHeader;

    @FindBy(xpath = "//h5[text()='EMAIL']")
    public WebElement emailHeader;

    @FindBy(xpath = "//h5[text()='PRIMARY INDIVIDUALS']")
    public WebElement primaryIndividualsHeader;

    @FindBy(xpath = "//h5[text()='CASE MEMBERS']")
    public WebElement caseMembersHeader;

    @FindBy(xpath = "//h5[text()='AUTHORIZED REPRESENTATIVE']")
    public WebElement authorizedRepresentativeHeader;

    @FindBy(xpath = "//table/tbody/tr/td[4]")
    public WebElement consumerFirstName;

    @FindBy(xpath = "//table/tbody/tr/td[6]")
    public WebElement consumerLastName;

    @FindBy(xpath = "(//span[contains(@class,'leftnav') and text()='group'])")
    public WebElement hoverLeftNabar;

    @FindBy(xpath = "//a/span[text()='Case/Consumer Search']")
    public WebElement caseConsumerSearch;

    @FindBy(xpath = "//p[text()='COMMENTS are required and cannot be left blank']")
    public WebElement commentRequireError;

    @FindBy(id = "applicationId")
    public WebElement applicationId;

    @FindBy(xpath = "//*[@class='MuiTableBody-root']/tr[1]/td[5]")
    public WebElement consumerId;

    @FindBy(xpath = "//span[text() = 'SEARCH']")
    public WebElement SearchButton;

    public WebElement getAppIdFromExpandetContactRecord(String appId) {
        return Driver.getDriver().findElement(By.xpath("//h6[contains(text(),'" + appId + "')]"));
    }

    @FindBy(xpath = "//tbody/tr[2]/td[2]")
    public WebElement secondConId;

    @FindBy(xpath = "//*[@class='MuiTableBody-root']/tr[1]/td[3]")
    public WebElement consumerIdLnk;

    @FindBy(xpath = "//*[contains(text(), 'LINK CASE/CONSUMER')]/..")
    public WebElement linkButtonAfterUnlinking;

    @FindBy(xpath = "//*[contains(text(), 'CANCEL')]")
    public WebElement cancelButtonAfterSavingReason;

    @FindBy(xpath = "//p[contains(text(),'APPLICATION ID is Required')]")
    public WebElement appIdIsRequired;

    @FindBy(xpath = "//p[contains(text(),'APPLICATION ID must be 9 characters')]")
    public WebElement appIdMustBEnineChars;

    @FindBy(xpath = "//span[contains(text(), 'Mailing Address')]")
    public WebElement authGridMailingCheckbox;

    @FindBy(xpath = "//input[@name='0_caDob']")
    public WebElement inebDOBCheckBox;

    @FindBy(xpath = "//th[contains(text(),'CONSUMER ID')][2]")
    public WebElement programConsumerID;

    @FindBy(xpath = "//*[contains(text(), 'Save')]")
    public WebElement saveContactRecordButton;

    @FindBy(xpath = "//div[@id='mui-component-select-businessUnitSelect']")
    public WebElement businessUnitDropDwn;

    @FindBy(xpath = "//div[2]/table[1]/tbody[1]/tr[1]/td[2]")
    public WebElement editedByNameContact;

    public WebElement TranslationServicelanguage(String lan) {
        return Driver.getDriver().findElement(By.xpath("//h6[contains(text(),'" + lan + "')]"));
    }

    @FindBy(xpath = "//p[contains(text(),'PROGRAM is required and cannot be left blank')]")
    public WebElement programError;

    @FindBy(xpath = "//button[@title = ' Delete Reason']")
    public WebElement deleteReasonAction;

    @FindBy(xpath = "//span[contains(text(), 'Continue')]")
    public WebElement deleteReasonActionContinue;

    @FindBy(xpath = "//span[(text()= 'Continue')]")
    public WebElement deleteReasonActionContinueNew;

    public WebElement savedReasonAction(String reasonAction) {
        waitFor(1);
        return Driver.getDriver().findElement(By.xpath("//h6[contains(text(),'" + reasonAction + "')]"));
    }

    @FindBy(xpath = "//span[contains(text(), 'clear')]")
    public WebElement deleteReasonActionCancel;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[*]//table/thead[*]/tr/th[2]")
    public WebElement searchResultHeaderCaseId;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[*]//table/thead[*]/tr/th[3]")
    public WebElement searchResultHeaderConsumerId;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[*]//table/thead[*]/tr/th[4]")
    public WebElement searchResultHeaderFN;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[*]//table/thead[*]/tr/th[5]")
    public WebElement searchResultHeaderLN;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[*]//table/thead[*]/tr/th[7]")
    public WebElement searchResultHeaderDOB;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[*]//table/thead[*]/tr/th[8]")
    public WebElement searchResultHeaderSSN;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[*]//table/thead[*]/tr/th[8]")
    public WebElement searchResultHeaderPhone;


    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[*]//table/thead[*]/tr/th[*]")
    public List<WebElement> listOfSearchResultHeaders;

    @FindBy(xpath = "//span[contains(text(),'State CASE ID -')]")
    public WebElement caseIDAuthCheckBoxLabel;

    @FindBy(xpath = "//span[contains(text(),'State CASE ID  -')]/../span/span/input")
    public WebElement caseIDAuthCheckBox;

    @FindBy(xpath = "//input[@name='0_caMedicaidRid']")
    public WebElement medicaidRIDAuthCheckBox;

    @FindBy(xpath = "//input[@name='0_caPhonenumber']")
    public WebElement phoneNumberAuthCheckBox;

    @FindBy(id = "mui-component-select-caseType")
    public WebElement searchCaseDropdown;

    @FindBy(id = "mui-component-select-searchCaseType")
    public WebElement searchCaseDropdownNew;

    @FindBy(id = "mui-component-select-searchConsumerType")
    public WebElement searchConsumerDropdown;

    @FindBy(xpath = "//p[@id='contactPhone-helper-text']")
    public WebElement phoneError;

    @FindBy(xpath = "//*[contains(text(),'Business Unit must be selected before Contact Reason')]")
    public WebElement businessUnitMustBeSelectedWarningMessage;


    @FindBy(xpath = "//*[contains(text(),'All Contact Reasons must be deleted before you can choose a different Business Unit')]")
    public WebElement allContactReasonsMustBeDeletedMessage;

    @FindBy(xpath = "//div[@aria-disabled='true']")
    public WebElement ariaDisabled;

    @FindBy(xpath = "//span[text()='delete']")
    public WebElement DeleteContactReason;

    @FindBy(xpath = "//*[text()='Emergency Services - CVCC']")
    public WebElement EmergencyServicesCVCC;

    @FindBy(xpath = "//i[@title='Edit Contact']")
    public WebElement editContactIcon;

    @FindBy(xpath = "(//*[contains(@id,'contactReason')]/..)[3]")
    public WebElement expandEditContactReasonDropdown;


    @FindBy(xpath = "//div[@class='row mt-3']//input[contains(@id,'comment')]")
    public WebElement editReasonActionComments;

    @FindBy(xpath = "//div[@class='row mt-3']//span[text()='check']")
    public WebElement editReasonActionSaveButton;

    @FindBy(xpath = "//h1[text()='WRAP-UP AND CLOSE']/..//span[text()='Save']")
    public WebElement saveReasonForEditButton;

    @FindAll({
            @FindBy(xpath = "//button[@class='mdl-button mdl-js-button mdl-button--icon mdl-button--colored mx-grid-accord-arrow']")
    })
    public List<WebElement> defaultResults;

    @FindBy(id = "client-snackbar")
    public WebElement maxRecordsWarningMsg;

    @FindBy(id = "mui-component-select-resultsGrid")
    public WebElement resultsOptions;

    @FindBy(xpath = "//*[@id=\"menu-resultsGrid\"]/div[3]/ul/li[4]")
    public WebElement optionFifty;

    @FindBy(xpath = "//button[@title=' Save Comments']//span[@class='material-icons MuiIcon-root']")
    public WebElement saveCallSummaryButton;

    @FindBy(xpath = "//*[contains(text(),'CALL SUMMARY must be at least 10 characters')]")
    public WebElement callSummaryErrorMsg;

    @FindBy(xpath = "(//*[text()='ERROR MESSAGE']/..//span)[2]")
    public WebElement missingCallSummaryErrorMsg;

    @FindBy(xpath = "//*[text()='CALL SUMMARY']/../..//span[@class='MuiIconButton-label']")
    public WebElement expandCallSummaryButton;

    @FindBy(xpath = "//p[.='CALL SUMMARY']/following-sibling::h6")
    public List<WebElement> savedCallSummaries;

    @FindBy(xpath = "//span[contains(text(), 'Physical Address')]")
    public WebElement inebPhysicalAddressCheckBox;

    @FindBy(xpath = "//*[text()='QA']")
    public WebElement userRoleTextQA;

    @FindBy(xpath = "(//span[contains(text(),'close')])[1]")
    public WebElement cancelButtonAfterCorres;

    @FindBy(xpath = "//div[@id='mui-component-select-programTypes']//span")
    public List<WebElement> programSelectedValues;

    @FindBy(xpath = "//tr[1]//td[2]//span")
    public WebElement firstContactId;

    @FindBy(xpath = "//div[text()='20']")
    public WebElement show20DropDown;

    @FindBy(xpath = "//ul[@class='MuiList-root MuiMenu-list MuiList-padding']")
    public List<WebElement> showAllDropdown;

    @FindBy(xpath = "//div[@id='mui-component-select-contactSearchResultsList']")
    public WebElement showDropdown;

    @FindBy(xpath = "//li[@data-value='10']")
    public WebElement show10Dropdown;

    @FindBy(xpath = "//li[@data-value='50']")
    public WebElement show50Dropdown;

    @FindBy(xpath = "//li[@data-value='5']")
    public WebElement show5Dropdown;

    @FindBy(xpath = "//*[@class='MuiTableBody-root']/tr[1]/td[2]")
    public WebElement linkSectionID;

    @FindBy(xpath = "//div[@class='MuiButtonBase-root MuiAccordionSummary-root px-0 ']")
    public WebElement reasonsButton;

    @FindBy(xpath = "//i[@title='Reasons']")
    public WebElement reasonArrowOnEditPage;

    @FindBy(xpath = "//*[text()= 'EDIT CONTACT']")
    public WebElement editContact;

    @FindBy(xpath = "//span[text()='Save']")
    public WebElement saveButton;

    @FindBy(xpath = "//span[text()='Continue']")
    public WebElement continueButton;

    @FindBy(xpath = "(//span[@class='material-icons MuiIcon-root mr-2'])[1]")
    public WebElement saveButtonUnidetified;

    @FindBy(xpath = "(//td[2])[1]")
    public WebElement stateCaseIdFirstRecordBusEvents;

    @FindBy(id = "linkCaseOnly")
    public WebElement linkCaseOnlyCheckBox;

    @FindBy(xpath = "//i[@title='Cell Phone']")
    public WebElement cellPhoneImage;

    @FindBy(xpath = "//i[@title='Home Phone']")
    public WebElement homePhoneImage;

    @FindBy(xpath = "//i[@title='Work Phone']")
    public WebElement workPhoneImage;
    //Mailing Address - 999 Main Ave  Albion, AZ 22222 - 2222

    @FindBy(xpath = "//*[contains(text(), 'Mailing Address')]")
    public WebElement addressForAuthentication;

    @FindBy(xpath = "//input[@id='programTypes']")
    public WebElement programTypes;

    @FindBy(xpath = "//input[@id = 'consumerFirstName']")
    public List<WebElement> thirdPartyFirstName;

    @FindBy(xpath = "//input[@id = 'consumerLastName']")
    public List<WebElement> thirdPartyLastName;

    @FindBy(xpath = "(//td[contains(@class, 'MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric')])[2]")
    public WebElement consumerSearchFirstRecordPI;

    @FindBy(xpath = "//p[contains(text(),'OUTCOME OF CONTACT')]/..//h6")
    public WebElement getOutcomeOfContactValue;

    @FindBy(xpath = "//span[@title='General Contact']/..")
    public WebElement generalContact;

    @FindBy(xpath = "//span[@title='Third Party Contact']/..")
    public WebElement thirdPartyContact;

    @FindBy(xpath = "//span[@title='Unidendifed Contact']/..")
    public WebElement unidentifiedContact;

    @FindBy(xpath = "(//*[text()='ERROR MESSAGE']/..//span)[2]")
    public WebElement reasonSelectWithoutProgramErrorMsg;

    @FindBy(xpath = "//input[@name='contactReason']/..")
    public WebElement reasonDdValue;

    @FindBy(xpath = "//input[@name='contactAction']/..")
    public WebElement actionDdValue;

    @FindBy(xpath = "//*[@id='contactReason']/../..//button")
    public WebElement disabledContactReason;

    @FindBy(xpath = "//*[contains(text(),'CALL SUMMARY cannot exceed 500 characters')]")
    public WebElement callSummaryLengthErrorMsg;

    @FindBy(xpath = "(//div/div/ul/li/i)[1]")
    public WebElement configureUserProvisioning;

    @FindBy(xpath = "//*[contains(text(),'USER LIST')]")
    public WebElement userListHeader;

    @FindBy(name = "maerskId")
    public WebElement maerskIDSearch;

    @FindBy(xpath = "//*[text()='No Records Available']//parent::div/h5[text()='PRIMARY INDIVIDUALS']")
    public WebElement noRecordsAvailableTextOnPITable;

    @FindBy(xpath = "//*[@class='MuiFormHelperText-root Mui-error MuiFormHelperText-filled']")
    public WebElement genericErrorMessage;

    @FindBy(xpath = "//span[contains(text(),'Provided Appeal Information')]")
    public WebElement existingAction;

    @FindBy(xpath = "//li[contains(text(),'Provided Case Status/Information')]")
    public WebElement addedAction;

    @FindBy(xpath = "//button[@title =' Save Edit Reason']")
    public WebElement saveEditedReason;

    @FindBy(xpath = "//*[contains(text(),'edit')]")
    public WebElement editAdditionalCommentsIcon;

    @FindBy(xpath = "(//*[contains(text(),'edit')])[2]")
    public WebElement editAdditionalCommentsIconSecond;

    @FindBy(xpath = "//input[@name='list-item-4']")
    public WebElement conText;

    @FindBy(xpath = "//span[contains(text(),'Address')]/..")
    public WebElement consumerAddressLabel;

    @FindBy(xpath = "//span[contains(text(),'Address')]/../span/span/input")
    public WebElement consumerAddressCheckbox;

    @FindBy(xpath = "(//span[contains(text(),'chevron_right')])[1]")
    public WebElement firstSearchResultChevronButton;

    @FindBy(xpath = "(//span[contains(text(),'chevron_right')])[2]")
    public WebElement secondSearchResultChevronButton;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[2]//table/tbody/tr/td[2]")
    public List<WebElement> stateCaseIdColumResult;

    @FindBy(xpath = "(//span[.=' -- -- '])[1]")
    public WebElement consumerNullAddressLabel;

    @FindBy(xpath = "//span[contains(text(),'CREATE OUTREACH SESSION')]")
    public WebElement createOutReachSessionOption;

    @FindBy(xpath = "//h6[text()=\"ADDITIONAL COMMENTS\"]//i")
    public WebElement expend;

    @FindBy(xpath = "//input[@name='contactAction']")
    public WebElement contactActionInput;

    @FindBy(xpath = "//li[text()='Education - Health Plan']")
    public WebElement contactActionEducationHealthPlan;

    @FindBy(xpath = "//li[text()='Education - Choice Period']")
    public WebElement contactActionEducationChoicePeriod;

    @FindBy(xpath = "//li[text()='Education - General Program Information']")
    public WebElement contactActionEducationGeneralProgramInformation;

    @FindBy(xpath = "//li[text()='Education - Guardianship/AR']")
    public WebElement contactActionEducationGuardianshipAr;

    @FindBy(xpath = "//li[text()='Referral - Health Plan']")
    public WebElement contactActionReferralHealthPlan;

    @FindBy(xpath = "//thead//th[text()='GetInsured CASE ID']")
    public WebElement getInsuredCASEID;

    @FindBy(xpath = "//div[@class='col-2 px-3']")
    public WebElement backgroundDiv;

    @FindBy(xpath = "//li//a[text()='LOOKUP']")
    public WebElement lookUpOption;

    @FindBy(xpath = "//li//i[text()='tune']")
    public WebElement configureOption;

    @FindBy(xpath = "//div[@id='mui-component-select-contactAction']/div/div/span")
    public List<WebElement> selectedContactActionValueList;

    @FindBy(xpath = "//span[text() = 'LINK CONSUMER']/..")
    public WebElement linkConsumerButtonAfterUnlinking;

    @FindBy(xpath = "//p[text()='CALL SUMMARY is required and cannot be left blank']")
    public WebElement callSummaryRqdErrorMsg;

    @FindBy(xpath = "//i[@title='Additonal comments']")
    public WebElement additionCommentBox;

    @FindBy(xpath = "//p[text() = 'REASON FOR EDIT is required and cannot be left blank']")
    public WebElement reasonForEditRegErrorMessage;

    @FindBy(xpath = "(//h5[contains(text(),'CONTACT HISTORY')]/following::tbody)[1]/tr/td[2]")
    public List<WebElement> contactRecordIdList;

    @FindBy(xpath = "(//h5[contains(text(),'TASK LIST')]/following::tbody)[1]/tr/td[2]")
    public List<WebElement> taskIdList;

    @FindBy(id = "CONTACT RECORD")
    public List<WebElement> contactRecordLinkObject;

    @FindBy(id = "TASK")
    public List<WebElement> taskIdLinkObject;

    @FindBy(xpath = "//p[.='STATUS']")
    public WebElement taskStatusLabel;

    @FindBy(xpath = "//p[.='STATUS']/following-sibling::p")
    public WebElement taskStatusValue;

    @FindBy(xpath = "//p[.='STATUS DATE']")
    public WebElement taskStatusDateLabel;

    @FindBy(xpath = "//p[.='STATUS DATE']/following-sibling::p")
    public WebElement taskStatusDateValue;

    @FindBy(xpath = "//p[.='TYPE']")
    public WebElement taskTypelabel;

    @FindBy(xpath = "//p[.='TYPE']/following-sibling::p")
    public WebElement taskTypeValue;

    @FindBy(xpath = "//p[.='CONTACT START']")
    public WebElement conRecConStartLabel;

    @FindBy(xpath = "//p[.='CONTACT START']/following-sibling::p")
    public WebElement conRecConStartValue;

    @FindBy(xpath = "//p[.='WRAP UP TIME']")
    public WebElement conRecWrapUpLabel;

    @FindBy(xpath = "//p[.='WRAP UP TIME']/following-sibling::p")
    public WebElement conRecWrapUpValue;

    @FindBy(xpath = "//p[.='NAME OF CONTACT']")
    public WebElement conRecNameLabel;

    @FindBy(xpath = "//p[.='NAME OF CONTACT']/following-sibling::p")
    public WebElement conRecNameValue;

    @FindBy(xpath = "//button[.='VIEW']")
    public WebElement viewLinkButton;

    @FindBy(xpath = "(//h5[contains(text(),'CONTACT HISTORY')]/following::tbody)[1]/tr/td[4]")
    public List<WebElement> contactRecordConsumerList;

    @FindBy(xpath = "(//h5[contains(text(),'PRIMARY INDIVIDUALS')]/following::tbody)[1]/tr/td[3]")
    public List<WebElement> demoPagePIList;

    @FindBy(xpath = "(//h5[contains(text(),'CASE MEMBERS')]/following::tbody)[1]/tr/td[3]")
    public List<WebElement> demoPageMembersList;

    @FindBy(xpath = "//label[@for='consumerName']")
    public WebElement conNameDropLabel;

    @FindBy(id = "mui-component-select-consumerName")
    public WebElement conNameDropdown;

    @FindBy(xpath = "//ul[@role='listbox']/li")
    public List<WebElement> namesInHistoryTabConsumerDropList;

    @FindBy(xpath = "//label[@for='businessEvents']")
    public WebElement businessEventDropLabel;

    @FindBy(xpath = "//label[@id='processDateRange']")
    public WebElement processDateRangeDropLabel;

    @FindBy(xpath = "//label[@for='channel']")
    public WebElement channelDropLabel;

    @FindBy(id = "mui-component-select-channel")
    public WebElement channelDropdown;

    @FindBy(xpath = "//ul[@role='listbox']/li")
    public List<WebElement> channelValueDropList;

    @FindBy(xpath = "//div[2]/div[1]/div[1]/span[1]/h5[1]")
    public WebElement noRecordsAvailable;

    @FindBy(xpath = "//li/div[2]")
    public List<WebElement> listOfBusinessEvents;

    @FindBy(xpath = "(//*[@id=\"consumerFirstName\"])[2]")
    public WebElement thirdPartyConsumerFistNameSearch;

    @FindBy(xpath = "(//*[@id='consumerLastName'])[2]")
    public WebElement thirdPartyConsumerLastNameSearch;

    @FindBy(xpath = "//span[.='chevron_right']")
    public List<WebElement> listOfChevronRight;

    @FindBy(xpath = "//input[@name='p' and @type='radio']")
    public List<WebElement> primaryIndividualRadioButton;

    @FindBy(xpath = "//span[@title='LINK CASE/CONSUMER']")
    public WebElement linkCaseConsumerButton;

    @FindBy(xpath = "(//table)[1]/tbody/tr/td")
    public List<WebElement> consumerContactedAboutListData;

    @FindBy(xpath = "//li[@role='option']")
    public List<WebElement> dropdownselectvalues;

    @FindBy(id = "contactEmail")
    public WebElement contactEmailInput;

    @FindBy(xpath = "//span[contains(text(),'Mailing Address')]/..//span/span[1]")
    public WebElement mailingAddressCheckBox;

    @FindBy(xpath = "//span[contains(text(),'Physical Address')]/../span/span[1]")
    public WebElement physicalAddressCheckBox;

    @FindBy(id = "contactActionOther")
    public WebElement contactActionTextForOtherReason;

    @FindBy(xpath = "//p[text()='CONTACT ACTION']//following-sibling::h6")
    public WebElement savedContactActionText;

    @FindBy(xpath = "//tbody//tr//td[4]")
    public WebElement contactRecordEditHistoryReasonForEdit;

    @FindBy(xpath = "//tbody//tr[3]//td[4]")
    public WebElement contactRecordEditHistoryReasonForEditSecondRow;

    public WebElement authGridConsumerName(String consumerName) {
       return Driver.getDriver().findElement(By.xpath("//span[contains(text(), '"+consumerName+"')]"));
    }

    public WebElement consumerNameOnTopAfterLinkingActiveScreen(String consumerName) {
        return Driver.getDriver().findElement(By.xpath("//p[contains(text(), '"+consumerName+"')]"));
    }

    @FindBy(xpath = "//p[contains(text(),'CLAIM ID')]")
    public WebElement claimIDField;

    @FindBy(xpath = "//*[text()='CONTACT HISTORY']")
    public WebElement CONTACTHISTORYlable;

    @FindBy(xpath = "//*[text()='First additional comment']")
    public WebElement additionalCommentsEditedText;

    @FindBy(xpath = "//button[@title=' Save Edit Comments']")
    public WebElement firstAdditionalCommentsSavedButton;

    public WebElement additionalCommentsAndCallSummaryReadText(String Comment) {
        return Driver.getDriver().findElement(By.xpath("//h6[(text()='"+Comment+"')]"));
    }

    @FindBy(xpath = "//button[@title=' Edit Comments']")
    public WebElement additionalCommentAndCallSummaryEditButton;

    @FindBy(id = "applicationId")
    public WebElement applicationIdField;
}
