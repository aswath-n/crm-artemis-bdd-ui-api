package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMManualContactRecordSearchPage {
    
    public CRMManualContactRecordSearchPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }


    @FindBy(xpath = "//*[contains(text(),'ADVANCED SEARCH')]")
    public WebElement advancedSearchButton;

    @FindBy(id ="consumerFirstName")
    public WebElement manualSearchFirstName;

    @FindBy(id ="consumerLastName")
    public WebElement manualSearchLastName;

    @FindBy(id ="contactRecordId")
    public WebElement manualSearchContactRecordId;

    @FindBy(xpath="//i[@title='Manual User Captured']/parent::*")
    public WebElement contactRecordIdText;

    @FindBy(id ="createdOn")
    public WebElement manualSearchDate;

    @FindBy(id ="mui-component-select-contactRecordType")
    public WebElement manualSearchContactType;

    @FindBy(id ="mui-component-select-contactChannelType")
    public WebElement manualSearchChannel;

    @FindBy(id ="mui-component-select-contactRecordStatusType")
    public WebElement manualSearchDisposition;

    @FindBy(id ="caseId")
    public WebElement manualSearchInternalCaseId;

    @FindBy(id ="consumerId")
    public WebElement manualSearchInternalConsumerId;

    @FindBy(id = "phoneNumber")
    public WebElement phoneNumberTextBox;

    @FindBy(id = "emailAddress")
    public WebElement emailTextBox;

    //@FindBy(xpath = "//button[contains(@class, 'mx-btn-primary')]")
//    @FindBy(xpath = "(//button[contains(@class, 'mx-btn-primary')])[2]")
    @FindBy(xpath = "(//span[contains(text(), 'search')]/..)[2]")
    public WebElement searchButton; // changed due to call management

    @FindBy(xpath = "//h6[contains(text(),'SEARCH RESULT')]/..//table/tbody/tr[not(contains(@class, 'collapse'))]")
    public List<WebElement> searchResultRows;

    @FindBy(xpath = "//span[contains(text(),'Search results in excess')]")
    public WebElement searchIsInExcessError;

    @FindBy(xpath = "//*[contains(text(),'Contact Record Search')]")  //*[@id="root"]/div/div[2]/ul/li[3]/a
    public WebElement manualCRSearchMenu;

    @FindBy(xpath = "//h5[text()='CONTACT RECORD']")
    public WebElement labelManualCRSearch;

    @FindBy(id = "mui-component-select-contactType")
    public WebElement consumerTypeDropdown;

    @FindBy(id = "mui-component-select-contactRecordType")
    public WebElement contactTypeDropdown;

  //  @FindBy(xpath = "(//span[text()='search']/../..)[2]")
  //  public WebElement searchButton;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class,'mdl-data-table mdl-js-data-table mx-table table mt-4 mb-0 mdl-color--grey-50')]/tbody/tr/td[3]")
    })
    public List<WebElement> contactIDs;

    @FindBy(xpath = "//span[text()='CONTACT DETAILS']")
    public WebElement contactDetailsTab;

    @FindBy(xpath = "//h1[text()='UNIDENTIFIED - CONSUMER IN CONTACT']")
    public WebElement labelUnidentifiedContact;

    @FindBy(xpath = "//h1[text()='CONTACT DETAILS']")
    public WebElement labelContactDetails2;

    @FindBy(xpath = "//h1[text()='CONTACT DETAILS']")
    public WebElement labelContactDetailsNew;

    @FindBy(xpath = "//i[text()='edit']")
    public WebElement editButton;

    @FindBy(xpath = "//h5[text()='CONTACT RECORD']")
    public WebElement contactRecordLabel;

    @FindBy(xpath = "//h6[text()='CONTACT DETAILS']")
    public WebElement contactDetailsLabel;

    @FindBy(xpath = "//h6[text()='CONTACT EDIT DETAILS']")
    public WebElement contactEditDetailsLabel;

    @FindBy(xpath = "(//h5[text()='CONTACT RECORD'])[2]/following-sibling::div//p")
    public List<WebElement> contactDetailsHeaders;

    @FindBy(xpath = "(//h5[text()='CONTACT RECORD'])[2]/following-sibling::div//p/following-sibling::h6")
    public List<WebElement> contactDetailsAllValues;

    @FindBy(xpath = "//div[@id='contactDetails']//th")
    public List<WebElement> contactEditDetailsHeaders;

    @FindBy(xpath = "//div[@id='contactDetails']//td")
    public List<WebElement> contactEditDetailsAllValues;

    @FindBy(name = "contactRecordId")
    public WebElement contactRecordNo;

    @FindBy(xpath = "//p[text()='Contact Reason']")
    public WebElement contactReasonHeader;

    @FindBy(xpath = "//p[text()='Materials Request']")
    public WebElement contactReasonValue;

    @FindBy(xpath = "//p[text()='Additional Comments']")
    public WebElement additionalCommentsHeader;

    @FindBy(xpath = "//p[text()='This is a valid Test Comments']")
    public WebElement additionalCommentsValue;

    @FindBy(xpath = "(//th[contains(text(),'CONTACT ID')])[1]/parent::tr/parent::thead/parent::table/tbody/tr[1]/td[3]/i")
    public WebElement contactIcon;

    @FindBy(xpath = "//p[text()='Contact Id']/..//i")
    public WebElement contactDetailsTabIcon;

    @FindBy(xpath = "//p[text()='Contact Type']/..//h6")
    public WebElement contactDetailsTabContactType;

    @FindBy(xpath = "//input[@id='contactDispositions']/..")
    public WebElement contactDispositions;

    @FindBy(xpath = "//div/button[@id='addminiwidget']//i")
    public WebElement widgetPhoneIcon;

    @FindBy(xpath = "//span[contains(text(),'WARNING MESSAGE')]")
    public WebElement warningMsg;

    @FindBy(xpath = "//p[text()='If you continue, all the captured information will be lost']")
    public WebElement warningMsgTxt;

    @FindBy(xpath = "//p[contains(text(), 'If you navigate away, your information will not be saved')]")
    public WebElement warningMsgNavigate;

    @FindBy(id="auto-maerskId")
    public WebElement advanceSearchEmployeeID;

    @FindBy(id="mui-component-select-inboundCallQueue")
    public WebElement inboundCallQueueDropDown;

    @FindBy(id="mui-component-select-contactCampaignType")
    public WebElement callCampaignDropDown;

    @FindBy(id="mui-component-select-contactOutcome")
    public WebElement callOutcomeDropDown;

    @FindBy(id="auto-createdBy")
    public  WebElement createdByAdvancedSearch;

    @FindBy(xpath="//label[text()[contains(.,'Phone Number')]]")
    public  WebElement phoneNumberAdvancedSearch;

    @FindBy(id="emailAddress")
    public  WebElement emailAddressAdvancedSearch;

    @FindBy(id="organizationName")
    public  WebElement _3rdPartyOrganizationNameAdvancedSearch;

    @FindBy(xpath = "//p[text()='Your updates to the Primary Individual will not be saved']")
    public WebElement UpdatewarningMsgTxt;

    @FindBy(xpath = "//h5[contains(text(),'No Records Available')]")
    public WebElement noResultsFound;

    @FindBy(xpath = "//input[@placeholder='MM/DD/YYYY']")
    public WebElement CreatedOn;

    @FindBy(id="consumerFirstName")
    public WebElement firstNameSearch;

    @FindBy(xpath = "//tbody/tr/td[7]")
    public WebElement consumerNameSearchRes;

    @FindBy(id="consumerLastName")
    public WebElement lasttNameSearch;

    @FindBy(xpath = "//input[@id='caseId']")
    public WebElement caseIdSearch;

    @FindBy(id="consumerId")
    public WebElement consumerIDField;

    @FindBy(id="consumerIdentificationNo")
    public WebElement medAidconsumerIDField;

    @FindBy(id="caseIdentificationNo")
    public WebElement StateCaseIdSeach;

    @FindBy(id="caseIdentificationNumber")
    public WebElement StateCaseIdSearch;

    @FindBy(xpath = "//div[text()='State']")
    public WebElement State;

    @FindBy(xpath = "//span[@title='Sort by CRM CASE ID']")
    public WebElement CRMCaseID;

    @FindBy(xpath = "//span[@title='Sort by State CASE ID']")
    public WebElement StateCaseID;

    @FindBy(xpath = "//span[text()='State CASE ID']")
    public WebElement StateCaseIDTable;

    @FindBy(xpath = "//span[@title='Sort by MEDICAID_RID CONSUMER ID']")
    public WebElement MedicAidConsumerID;

    @FindBy(xpath = "//span[@title='Sort by CRM CONSUMER ID']")
    public WebElement CRMConsumerID;

    @FindBy(xpath = "//div[@id='mui-component-select-searchConsumerType']")
    public WebElement searchConsumerType;

    @FindBy(xpath = "//div[@id='mui-component-select-consumerType']")
    public WebElement searchConsumerTypeCaseConsumer;

    @FindBy(xpath = "//div[@id='mui-component-select-consumerType']")
    public WebElement searchConsumerDropdown;

    @FindBy(xpath = "//div[@id='mui-component-select-searchConsumer']")
    public WebElement searchConsumerTaskSearch;

    @FindBy(xpath = "//span[contains(text(),'Offender CONSUMER ID')]")
    public WebElement OffenderColumn;

    @FindBy(css = ".mt-4>div>div>table>tbody>tr>td:nth-child(5)")
    public WebElement OffenderIDValue;

    @FindBy(xpath = "//div[@id='mui-component-select-agencyName']")
    public WebElement consumerIdType;

    @FindBy(id="consumerIdentificationNumber")
    public WebElement consumerExternalId;

    @FindBy(id="consumerId")
    public WebElement consumerIdCaseConsumer;

    @FindBy(xpath ="//*[@id=\"consumerIdentificationNo\"]")
    public WebElement caseConsumerID;

    @FindBy(xpath = "//tbody/tr/td[6]")
    public WebElement internalConsumerId;

    @FindBy(id ="mui-component-select-searchCaseType")
    public WebElement searchCase;

    @FindBy(id="mui-component-select-contactRecordType")
    public WebElement contactRecordTypeSearch;

    @FindBy(xpath = "//span[contains(text(),'GENERAL CONTACT')]")
    public WebElement generalCon;

    @FindBy(xpath = "//span[contains(text(),'THIRD PARTY CONTACT')]")
    public WebElement thirdPartCon;

    @FindBy(xpath = "//span[contains(text(),'UNIDENTIFIED CONTACT')]")
    public WebElement unidentifiedCon;

    @FindBy(xpath = "//div[@id='mui-component-select-contactChannelType']")
    public WebElement channelSearch;

    @FindBy(xpath = "//div[@id='mui-component-select-contactRecordStatusType']")
    public WebElement dispositionSearch;

    @FindBy(xpath = "//span[contains(text(),'Cancel')]")
    public WebElement cancelButSearch;

    @FindBy(xpath = "//li[contains(text(),'Complete')]")
    public WebElement disComplete;

    @FindBy(xpath = "//li[contains(text(),'Dropped')]")
    public WebElement disDropped;

    @FindBy(xpath = "//li[contains(text(),'Escalate')]")
    public WebElement disEscalate;

    @FindBy(xpath = "//li[contains(text(),'Incomplete')]")
    public WebElement disIncomplete;

    @FindBy(xpath = "//li[contains(text(),'Outbound Incomplete')]")
    public WebElement disOutboundIncomplete;

    @FindBy(xpath = "//li[contains(text(),'Requested Call Back')]")
    public WebElement disRequestedCallBack;

    @FindBy(xpath = "//li[contains(text(),'Transfer')]")
    public WebElement disTransfer;

    @FindBy(xpath = "//div[@id='mui-component-select-contactChannelType']")
    public WebElement channelType;

    @FindBy(xpath = "//div[@id='mui-component-select-contactCampaignType']")
    public WebElement callCamp;

    @FindBy(xpath = "//li[contains(text(),'Payment Reminder')]")
    public WebElement paymentRem;

    @FindBy(xpath = "//li[contains(text(),'Program Information')]")
    public WebElement progInfo;

    @FindBy(xpath = "//div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/button[1]")
    public WebElement advancedSearch;

    @FindBy(xpath = "//div[@id='mui-component-select-contactOutcome']")
    public WebElement outComeCon;

    @FindBy(xpath = "//div[@id='menu-contactOutcome']//li[2]")
    public WebElement noVoicmail;

    @FindBy(xpath = "//li[contains(text(),'Invalid Phone Number')]")
    public WebElement invalidPhone;

    @FindBy(xpath = "//li[contains(text(),'Reached Successfully')]")
    public WebElement reachedSuccess;

    @FindBy(xpath = "//input[@id='auto-createdBy']")
    public WebElement createdBy;

    @FindBy(xpath = "//input[@id='auto-maerskId']")
    public WebElement userId;

    @FindBy(xpath = "//input[@id='phoneNumber']")
    public WebElement phoneSearch;

    @FindBy(xpath = "//input[@id='emailAddress']")
    public WebElement emailSearch;

    @FindBy(xpath = "//input[@id='organizationName']")
    public WebElement thirdOrgName;

    @FindBy(xpath = "//tbody/tr/td[3]")
    public WebElement contactiD;

    @FindBy(xpath = "//tbody/tr/td[4]")
    public WebElement createdOnContactSearch;

    @FindBy(xpath = "//div[@id='mui-component-select-createdDateSearch']")
    public WebElement createdDataSymbol;

    @FindBy(xpath = "//span[contains(text(),'EDIT CONTACT')]")
    public WebElement editContactButton;

    @FindBy(id="thirdPartyConsumerFirstName")
    public  WebElement _3rdPartyFistNameAdvancedSearch;

    @FindBy(id="thirdPartyConsumerLastName")
    public  WebElement _3rdPartyLastNameAdvancedSearch;

    @FindBy(id = "thirdPartyConsumerFirstName")
    public WebElement thirdPtyFirstName;

    @FindBy(id = "thirdPartyConsumerLastName")
    public WebElement thirdPtyLastName;

    @FindBy(xpath = "//span[contains(text(),'chevron_right')]")
    public WebElement expandFieldButton;

    @FindBy(xpath = "//p[contains(text(),'THIRD PARTY NAME')]/following-sibling::h6")
    public WebElement thirdPtyNameValue;

    @FindBy(xpath = "//p[contains(text(),'3RD PARTY ORGANIZATION')]/following-sibling::h6")
    public WebElement thirdPtyOrgNameValue;

    @FindBy(xpath = "//tr//p")
    public List<WebElement> expandedViewSearchParam;

    @FindBy(xpath = "//input[@id='caseIdentificationNumber']")
    public WebElement inputGIcaseId;

    @FindBy(xpath = "(//tbody[@class='MuiTableBody-root']/tr/td)[5]")
    public WebElement manConRecSearchgGetInsuredCaseId;

    @FindBy(xpath = "(//tbody[@class='MuiTableBody-root']/tr/td)[6]")
    public WebElement manConRecSearchgGetInsuredConsumerId;

    @FindBy(xpath = "//h5[contains(text(),'No Records Available')]")
    public WebElement noRecordsAvailable;

    @FindBy(xpath = "//p[text()[contains(.,'EMAIL')]]")
    public WebElement emailExpandedView;

    @FindBy(id = "mui-component-select-contactReasonEditType")
    public WebElement reasonForEdit;

    @FindBy(id="mui-component-select-contactReason")
    public WebElement contactReasonEditPage;

    @FindBy(id="mui-component-select-contactAction")
    public WebElement contactActionEditPage;

    @FindBy(xpath = "//textarea[@id='comments']")
    public WebElement comments;

    @FindBy(xpath = "//div[1]/div[2]/div[4]/button[1]")
    public WebElement saveReason;

    @FindBy(xpath = "//textarea[@id='additionalComments']")
    public WebElement getAdditionalComment;

    @FindBy(xpath = "//div[@class='row mx-0']//div[2]//button[1]")
    public WebElement additionalCommSave;

    @FindBy(xpath = "(//*[text()='textsms'])[2]//ancestor::div[@tabindex='0']//following-sibling::div[contains(@class,'Mui-expanded MuiIconButton-edgeEnd')]")
    public WebElement frstaddedCommentsign;

    @FindBy(xpath = "//*[contains(text(),'edit')]")
    public WebElement editbuttonComment;

    @FindBy(xpath = "//textarea[@id='additionalComment-356']")
    public WebElement frstaddedComment;

    @FindBy(xpath = "//div[6]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/button[1]")
    public WebElement frstaddedCommentSave;

    @FindBy(id = "consumerFirstName")
    public WebElement thirdPartyFrstName;

    @FindBy(id = "consumerLastName")
    public WebElement thirdPartyLstName;

    @FindBy(id = "organizationName")
    public WebElement thirdPartyOrgName;

   @FindBy(id="mui-component-select-consumerType")
    public WebElement consumerTypeDrpDwn;

    @FindBy(id="mui-component-select-preferredLanguageCode")
    public WebElement prepeferedLanDrpDwn;

    @FindBy(xpath = "//div[2]/div[3]/div[1]/div[1]/div[2]")
    public WebElement frstaddedContactReason;

    @FindBy(xpath = "//div[3]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[4]/button[1]")
    public WebElement editButtonAddedConcatReason;

    @FindBy(xpath = "//div[3]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[4]/button[2]")
    public WebElement editButtonAddedConcatReasonNew;

    @FindBy(xpath = "//div[@id='mui-component-select-contactReason-1016']")
    public WebElement addedContactReasonDrpDwn;

    @FindBy(xpath = "//div[@id='mui-component-select-contactAction-1016']")
    public WebElement addedContactActionDrpDwn;

    @FindBy(xpath = "//input[@id='comments-1016']")
    public WebElement addedCommentContactReason;

    @FindBy(xpath = "//div[3]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[4]/button[1]")
    public WebElement frstaddedCRSavebutton;

    @FindBy(xpath = "//button[contains(@class,'mx-btn-border mx-btn-cancel float-right')]")
    public WebElement unlinkContactRecord;

    @FindBy(name="p")
    public WebElement primaryIndividualRadioButton;

    @FindBy(xpath = "//span[contains(text(),'VALIDATE & LINK')]")
    public WebElement validateandlink;

    @FindBy(xpath = "//span[text()='VALIDATE & LINK']")
    public WebElement validateandlinked;

    @FindBy(xpath = "//tr[1]//td[1]//button[1]")
    public WebElement expandFrstRecord;

    @FindBy(xpath = "//div[@id='mui-component-select-contactRecordStatusType']")
    public WebElement dispositionDrpDwn;

    @FindBy(xpath = "//span[contains(text(),'Correcting Case/Consumer Link is not a valid reason')]")
    public WebElement errorMsgCaseLink;

    @FindBy(xpath = "//span[contains(text(),'Correcting Third Party Information is not a valid ')]")
    public WebElement errorMsgThirdParty;

    @FindBy(xpath = "(//p[@class='my-0'])[1]")
    public WebElement preContactReason;

    @FindBy(xpath = "(//p[@class='my-0'])[2]")
    public WebElement preContactAction;

    @FindBy(xpath = "(//p[@class='my-0'])[2]")
    public WebElement updatedContactReason;

    @FindBy(xpath = "//div[@class='col-12']/p[2]")
    public WebElement updatedAddComment;

    @FindBy(xpath = "//span[contains(text(),'LINK CASE/CONSUMER')]")
    public WebElement linkCaseAndConsumer;

    @FindBy(xpath = "//div[1]/div[1]/ul[1]/li[3]/a[1]")
    public WebElement frontArrow;

    @FindBy(xpath = "//div[1]/ul[1]/li/a[1]")
    public List<WebElement> listOfPag;

    @FindBy(xpath = "//div[3]/div[3]/h6[1]")
    public WebElement contactDetailsUserId;

    @FindBy(xpath = "//tbody[1]/tr[1]/td[3]/p[1]")
    public WebElement newLinkConId;

    @FindBy(xpath = "//div[3]/div[1]/div[1]/div[1]/input[1]")
    public WebElement afterthirdPartyFrstName;

    @FindBy(xpath = "//div[3]/div[3]/div[1]/div[1]/input[1]")
    public WebElement afterthirdPartyLstName;

    @FindBy(xpath = "//button[text()='INTERACTION MANAGEMENT']")
    public WebElement callManagmentTab;

    @FindBy(xpath = "//div[@class='MuiAutocomplete-popper']")
    public WebElement createdByDrop;

    @FindBy(xpath = "//div[1]/div[3]/h6[1]")
    public WebElement conDetEmail;

    @FindBy(xpath="//span[contains(text(),'VIEW CHAT TRANSCRIPT')]")
    public WebElement viewChatTranscriptButton;


    @FindBy(xpath = "//thead/tr[1]/th[5]/span[1]")
    public WebElement extConsumerId;

    @FindBy(xpath = "//thead/tr[1]/th[6]/span[1]")
    public WebElement extCaseConsumerId;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[6]")
    public WebElement manualSearchConsumerExtId;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[5]")
    public WebElement manualSearchConsumerExtIdNew;

    @FindBy(xpath = "//span[text()='VaCMS ID']")
    public WebElement VaCMSIdCol;

    @FindBy(xpath = "//input[@id='contactPhone']")
    public WebElement contactPhone;

    @FindBy(xpath = "//tbody/tr[1]/td[1]")
    public WebElement frstConRecord;

    @FindBy(xpath = "//span[1]/span[1]/input[1]")
    public WebElement elSigCapAfExpand;
    @FindAll({
            @FindBy(xpath = "//tbody/tr/td[3]")
    })
    public List<WebElement> contactRecordIDs;

    @FindBy(id = "mui-component-select-businessUnit")
    public WebElement businessUnitDropDown;

    @FindBy(xpath = "//p[contains(text(),'BUSINESS UNIT')]")
    public WebElement businessUnitExpandedSearch;

    @FindBy(xpath = "//h6[contains(text(),'Anonymous')]")
    public WebElement callerTypeVal;

    @FindBy(id="mui-component-select-contactTranslationService")
    public WebElement translationLanDrpDwn;

    public WebElement contactRecordSearchResultColumn (String columnName){
        return Driver.getDriver().findElement(By.xpath("//span[contains(text(),'" + columnName + "')]"));
    }

    @FindBy(xpath = "//input[@id='caseIdentificationNo']")
    public WebElement externalCaseIdSearch;

    @FindBy(xpath = "//input[@id='consumerIdentificationNo']")
    public WebElement externalConsumerIdSearch;

    @FindBy(xpath = "//span[text()='Continue']")
    public WebElement continueWarning;

    @FindBy(id = "programTypes")
    public WebElement chosenProgram;

    @FindBy(xpath = "//div[@id='mui-component-select-programTypes']")
    public WebElement programs;

    @FindBy(xpath = "//div[@id='menu-contactReasonEditType']/div/ul/li")
    public List<WebElement> reasonsForEditList;

    @FindBy(xpath = "//ul[@role='listbox']/li")
    public List<WebElement> nonMultiDropList;

    @FindBy(xpath = "//div[@id='menu-programTypes']/div/ul/li")
    public List<WebElement> contactDetailsProgramsList;

    @FindBy(xpath = "//button[@title=' Edit Comments']")
    public WebElement callSummaryEditButton;

    @FindBy(xpath = "//*[text()='First call summary']")
    public WebElement callSummaryEditedText;

    @FindBy(xpath = "//button[@title=' Save Edit Comments']")
    public WebElement firstCallSummarySavedButton;

    @FindBy(xpath = "//p[text()='CALL SUMMARY']/..//h6")
    public WebElement readTextCallSummary;
}

