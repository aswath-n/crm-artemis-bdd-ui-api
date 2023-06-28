package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.LinkedList;
import java.util.List;

public class ViewInboundCorrespondenceDetailsUIAPIPage {

    

    public ViewInboundCorrespondenceDetailsUIAPIPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//h5[contains(text(),'INBOUND CORRESPONDENCES')]/following::div[2]/div/div[2]/div/div/div/div")
    public WebElement inboundCorrShow5Default;

    @FindBy(xpath = "//span[text()='CORPORATE HEADQUARTERS']")
    public WebElement footer;

    @FindBy(xpath = "//h5[contains(text(),'INBOUND CORRESPONDENCES')]/following::div[2]/div/div[2]/div/div/ul/li/a[contains(@aria-label,'Go to page number')]")
    public List<WebElement> corrPageNums;

    @FindBy(xpath = "//h5[contains(text(), 'CORRESPONDENCE DETAILS')]")
    public WebElement inboundCorrDetHeader;

    @FindBy(xpath = "//h6[@class = 'mt-0 float-right mx-color-text-primary mt-2']")
    public WebElement cID;

    @FindBy(xpath = "//p[text() = 'SOURCE']/parent::div/h6")
    public WebElement source;

    @FindBy(xpath = "//p[text() = 'CREATED BY']/parent::div/h6")
    public WebElement createdBy;

    @FindBy(xpath = "//p[text() = 'CREATED ON']/parent::div/h6")
    public WebElement createdOn;

    @FindBy(xpath = "//p[text() = 'UPDATED BY']/parent::div/h6")
    public WebElement updatedBy;

    @FindBy(xpath = "//p[text() = 'UPDATED ON']/parent::div/h6")
    public WebElement updatedOn;

    @FindBy(xpath = "//p[text() = 'TYPE']/parent::div/h6")
    public WebElement correspondenceType;

    @FindBy(xpath = "//p[text() = 'LANGUAGE']/parent::div/h6")
    public WebElement language;

    @FindBy(xpath = "//p[text() = 'RECEIVED DATE']/parent::div/h6")
    public WebElement receivedDate;

    @FindBy(xpath = "//p[text() = 'PAGE COUNT']/parent::div/h6")
    public WebElement pageCount;

    @FindBy(xpath = "//p[text() = 'STATUS']/parent::div/h6")
    public WebElement status;

    @FindBy(xpath = "//p[text() = 'STATUS DATE']/parent::div/h6")
    public WebElement statusDate;

    @FindBy(xpath = "//p[text() = 'SET ID']/parent::div/h6")
    public WebElement setId;

    @FindBy(xpath = "//p[text() = 'THIRD PARTY RECEIVED DATE']/parent::div/h6")
    public WebElement thirdPartyRD;

    @FindBy(xpath = "//p[text() = 'FORM VERSION']/parent::div/h6")
    public WebElement formVersion;

    @FindBy(xpath = "//p[text() = 'RETURNED NOTIFICATION ID']/parent::div/h6")
    public WebElement returnedNotificationId;

    @FindBy(xpath = "//p[text() = 'RESCANNED AS CID']/parent::div/h6")
    public WebElement replacementCorrespondenceId;

    @FindBy(xpath = "//p[text() = 'RESCAN OF CID']/parent::div/h6")
    public WebElement rescanOfCorrespondenceId;

    @FindBy(xpath = "//h4")
    public WebElement channel;

    @FindBy(xpath = "//p[text() = 'ORIGIN']/parent::div/h6")
    public WebElement origin;

    @FindBy(xpath = "//p[text() = 'ORIGIN ID']/parent::div/h6")
    public WebElement originItemId;

    @FindBy(xpath = "//p[text() = 'ORIGIN SET ID']/parent::div/h6")
    public WebElement originSetId;

    @FindBy(xpath = "//p[text() = 'SOURCE BATCH ID']/parent::div/h6")
    public WebElement batchNumber;

    @FindBy(xpath = "//p[text() = 'SCAN BATCH CLASS']/parent::div/h6")
    public WebElement scanBatchClass;

    @FindBy(xpath = "//p[text() = 'SCAN TIMESTAMP']/parent::div/h6")
    public WebElement scannedOn;

    @FindBy(xpath = "//p[text() = 'FROM PHONE NUMBER']/parent::div/h6")
    public WebElement fromPhoneNumber;

    @FindBy(xpath = "//p[text() = 'FROM EMAIL']/parent::div/h6")
    public WebElement fromEmailAddress;

    @FindBy(xpath = "//p[text() = 'FROM NAME']/parent::div/h6")
    public WebElement fromName;

    @FindBy(xpath = "//span[contains(text(),'Signed')]/parent::h6")
    public WebElement signed;

    @FindBy(xpath = "//span[contains(text(),'MAIL Attachment Count')]/parent::h6")
    public WebElement mailAttachmentCount;

    @FindBy(xpath = "//span[contains(text(),'MAIL Attachment Name')]/parent::h6")
    public WebElement mailAttachmentName;

    @FindBy(xpath = "//span[contains(text(),'MAIL Date Time Received')]/parent::h6")
    public WebElement mailDTReceived;

    @FindBy(xpath = "//span[contains(text(),'MAIL From')]/parent::h6")
    public WebElement mailFrom;

    @FindBy(xpath = "//span[contains(text(),'MAIL Subject')]/parent::h6")
    public WebElement mailSubject;

    @FindBy(xpath = "//span[contains(text(),'MAIL To')]/parent::h6")
    public WebElement mailTo;

    @FindBy(xpath = "//h5[contains(text(),'INBOUND CORRESPONDENCES')]/following::table/tbody/tr/td[2]")
    public List<WebElement> inboundCIDs;

    @FindBy(xpath = "//i[@title='Links']")
    public WebElement linksHeader;

    @FindBy(xpath = "//td[text()='Case']/parent::tr/td[6]")
    public WebElement linksCaseStatus;

    @FindBy(xpath = "//td[text()='Case']/parent::tr/td[2]")
    public WebElement linksCaseID;

    @FindBy(xpath = "//td[text()='Case']/parent::tr/td[4]")
    public WebElement linksCaseType;

    @FindBy(xpath = "//td[text()='Case']/parent::tr/td[5]")
    public WebElement linksCaseDate;

    @FindBy(xpath = "//td[text()='Consumer Profile']/parent::tr/td[6]")
    public WebElement linksConsumerProfileStatus;

    @FindBy(xpath = "//td[text()='Consumer Profile']/parent::tr/td[2]")
    public WebElement linksConsumerProfileID;

    @FindBy(xpath = "//td[text()='Consumer Profile']/parent::tr/td[4]")
    public WebElement linksConsumerProfileType;

    @FindBy(xpath = "//td[text()='Consumer Profile']/parent::tr/td[5]")
    public WebElement linksConsumerProfileDate;

    @FindBy(xpath = "//td[text()='Task']/parent::tr/td[2]")
    public List<WebElement> linkTaskIdColumn;

    @FindBy(xpath = "//td[text()='Service Request']/parent::tr/td[2]")
    public List<WebElement> linkServiceRequestIdColumn;

    @FindBy(xpath = "//td[text()='Service Request']/parent::tr/td[3]")
    public List<WebElement> linkServiceRequestNameColumn;

    @FindBy(xpath = "//td[text()='Service Request']/parent::tr/td[4]")
    public List<WebElement> linkServiceRequestTypeColumn;

    @FindBy(xpath = "//td[text()='Service Request']/parent::tr/td[5]")
    public List<WebElement> linkServiceRequestDateColumn;

    @FindBy(xpath = "//td[text()='Service Request']/parent::tr/td[6]")
    public List<WebElement> linkServiceRequestStatusColumn;

    @FindBy(xpath = "//td[text()='Task']/parent::tr/td[4]")
    public List<WebElement> linkTaskTypeCoumn;

    @FindBy(xpath = "//h6[@class = 'mt-0 float-right mx-color-text-primary mt-2']/a")
    public WebElement cIDValueNumber;

    @FindBy(xpath = "//*[.='keyboard_backspace']")
    public WebElement backArrow;

    @FindBy(xpath = "//*[contains(text(),'file_copy')]/..")
    public WebElement viewIcon;

    @FindBy(xpath = "//*[contains(text(),'SOURCE DETAILS')]/../..")
    public WebElement sourceDetailsTab;

    @FindBy(xpath = "//*[text()[contains(.,'DOCUMENT SET DETAILS')]]/../..")
    public WebElement documentSetDetailsTab;

    @FindBy(xpath = "//*[contains(text(),'METADATA')]/../..")
    public WebElement metadataTab;

    @FindBy(xpath = "//*[contains(text(),'HISTORY')]/../..")
    public WebElement statusHistoryTab;

    @FindBy(xpath = "//*[contains(text(),'STATUS HISTORY')]/../..")
    public WebElement oldStatusHistoryTab;

    @FindBy(xpath = "//p[contains(text(),'SOURCE BATCH ID')]/following-sibling::h6")
    public WebElement sourceBatchIdValue;

    @FindBy(xpath = "//p[contains(text(),'SCAN BATCH CLASS')]/following-sibling::h6")
    public WebElement scanBatchClassValue;

    @FindBy(xpath = "//p[contains(text(),'SCAN TIMESTAMP')]/following-sibling::h6")
    public WebElement scanTimestampValue;

    @FindBy(xpath = "//p[contains(text(),'ORIGIN')]/following-sibling::h6")
    public WebElement originValue;

    @FindBy(xpath = "//p[contains(text(),'ORIGIN ID')]/following-sibling::h6")
    public WebElement originIdValue;

    @FindBy(xpath = "//p[contains(text(),'ORIGIN SET ID')]/following-sibling::h6")
    public WebElement originSetIdValue;

    @FindBy(xpath = "//p[contains(text(),'THIRD PARTY RECEIVED DATE')]/following-sibling::h6")
    public WebElement thirdPartyReceivedDateValue;

    @FindBy(xpath = "//p[contains(text(),'FROM EMAIL')]/following-sibling::h6")
    public WebElement fromEmailAddressValue;

    @FindBy(xpath = "//p[contains(text(),'FROM PHONE NUMBER')]/following-sibling::h6")
    public WebElement fromPhoneNumberValue;

    @FindBy(xpath = "//p[contains(text(),'FROM NAME')]/following-sibling::h6")
    public WebElement fromNameValue;

    @FindBy(xpath = "//*[contains(text(),'SOURCE BATCH ID')]")
    public WebElement sourceBatchIdLabel;

    @FindBy(xpath = "//*[contains(text(),'SCAN BATCH CLASS')]")
    public WebElement scanBatchClassLabel;

    @FindBy(xpath = "//*[contains(text(),'SCAN TIMESTAMP')]")
    public WebElement scanTimestampLabel;

    @FindBy(xpath = "//*[contains(text(),'ORIGIN')]")
    public WebElement originLabel;

    @FindBy(xpath = "//*[contains(text(),'ORIGIN ID')]")
    public WebElement originIdLabel;

    @FindBy(xpath = "//p[contains(text(),'ORIGIN SET ID')]")
    public WebElement originSetIdLabel;

    @FindBy(xpath = "//p[contains(text(),'THIRD PARTY RECEIVED DATE')]")
    public WebElement thirdPartyReceivedDateLabel;

    @FindBy(xpath = "//p[contains(text(),'FROM EMAIL')]")
    public WebElement fromEmailAddressLabel;

    @FindBy(xpath = "//p[contains(text(),'FROM PHONE NUMBER')]")
    public WebElement fromPhoneNumberLabel;

    @FindBy(xpath = "//p[contains(text(),'FROM NAME')]")
    public WebElement fromNameLabel;

    @FindBy(xpath = "//*[contains(text(),'RE-SCAN')]")
    public WebElement rescanButton;

    @FindBy(xpath = "//*[contains(text(),'CREATED BY')]")
    public WebElement createdbyLabel;

    @FindBy(xpath = "//*[contains(text(),'CREATED BY')]/following-sibling::h6")
    public WebElement createdbyValue;

    @FindBy(xpath = "//*[contains(text(),'CREATED ON')]")
    public WebElement createdonlabel;

    @FindBy(xpath = "//*[contains(text(),'CREATED ON')]/following-sibling::h6")
    public WebElement createdonValue;

    @FindBy(xpath = "//*[contains(text(),'UPDATED BY')]")
    public WebElement updatedbyLabel;

    @FindBy(xpath = "//*[contains(text(),'UPDATED BY')]/following-sibling::h6")
    public WebElement updatedbyValue;

    @FindBy(xpath = "//*[contains(text(),'UPDATED ON')]")
    public WebElement updatedonLablel;

    @FindBy(xpath = "//*[contains(text(),'UPDATED ON')]/following-sibling::h6")
    public WebElement updatedonValue;

    @FindBy(xpath = "//*[contains(text(),'TYPE')]")
    public WebElement typeLabel;

    @FindBy(xpath = "//*[contains(text(),'TYPE')]/following-sibling::h6")
    public WebElement typeValue;

    @FindBy(xpath = "//*[contains(text(),'PAGE COUNT')]")
    public WebElement pageCountLabel;

    @FindBy(xpath = "//*[contains(text(),'PAGE COUNT')]/following-sibling::h6")
    public WebElement pageCountValue;

    @FindBy(xpath = "//*[contains(text(),'RECEIVED DATE')]")
    public WebElement receivedDateLabel;

    @FindBy(xpath = "//*[contains(text(),'RECEIVED DATE')]/following-sibling::h6")
    public WebElement receivedDateValue;

    @FindBy(xpath = "//*[contains(text(),'STATUS')]")
    public WebElement statusLabel;

    @FindBy(xpath = "//*[contains(text(),'STATUS')]/following-sibling::h6")
    public WebElement statusValue;

    @FindBy(xpath = "//*[contains(text(),'STATUS DATE')]")
    public WebElement statusdateLabel;

    @FindBy(xpath = "//*[contains(text(),'STATUS DATE')]/following-sibling::h6")
    public WebElement statusdateValue;

    @FindBy(xpath = "//*[contains(text(),'RESCANNED AS CID')]")
    public WebElement rescannedAsCidLabel;

    @FindBy(xpath = "//*[contains(text(),'RESCANNED AS CID')]/following-sibling::h6")
    public WebElement rescannedAsCidValue;

    @FindBy(xpath = "//*[contains(text(),'RESCAN OF CID')]")
    public WebElement rescannedOfCidLabel;

    @FindBy(xpath = "//*[contains(text(),'RESCAN OF CID')]/following-sibling::h6")
    public WebElement rescannedOfCidValue;

    @FindBy(xpath = "//*[contains(text(),'RETURNED NOTIFICATION ID')]")
    public WebElement returnedNidLabel;

    @FindBy(xpath = "//*[contains(text(),'RETURNED NOTIFICATION ID')]/following-sibling::h6")
    public WebElement returnedNidValue;

    @FindBy(xpath = "//*[contains(text(),'RETURNED REASON')]")
    public WebElement returnedReasonLabel;

    @FindBy(xpath = "//*[contains(text(),'RETURNED REASON')]/following-sibling::h6")
    public WebElement returnedReasonValue;

    @FindBy(xpath = "//*[contains(text(),'LANGUAGE')]")
    public WebElement languageLabel;

    @FindBy(xpath = "//*[contains(text(),'LANGUAGE')]/following-sibling::h6")
    public WebElement languageValue;

    @FindBy(xpath = "//*[contains(text(),'FORM VERSION')]")
    public WebElement formVersionLabel;

    @FindBy(xpath = "//*[contains(text(),'FORM VERSION')]/following-sibling::h6")
    public WebElement formVersionValue;

    @FindBy(xpath = "//span[text()=' CREATE LINK(S)']")
    public WebElement createLinksDropdown;

    @FindBy(xpath = "//h1[text()='LINKS']/../ul/li/span")
    public List<WebElement> createLinksDropdownValues;

    @FindBy(xpath = "//*[contains(text(),'EDITED BY')]/../following-sibling::tbody/tr/td[2]")
    public WebElement serviceCPUserChangedByColumn;

    @FindBy(xpath = "//*[contains(text(),'EDITED BY')]/../following-sibling::tbody/tr/td")
    public List<WebElement> serviceCPUserEDITEDBYColumn;

    @FindBy(xpath = "//*[contains(text(),'Service')]")
    public WebElement loggedInUserName;

    @FindBy(xpath = "//*[contains(text(),'Service')]/following-sibling::*")
    public WebElement loggedInUserId;

    @FindBy(xpath = "//*[contains(text(),'HISTORY')]/../..")
    public WebElement editHistoryTab;

    @FindBy(xpath = "//table[contains(@class,'mdl-data-table mdl-js-data-table mt-')]/tbody/tr/td[1]")
    public WebElement editedonvalue;

    @FindBy(xpath = "(//button/span/span[text()='check'])[1]")
    public WebElement editibcorrsavebutton;

    @FindBy(xpath = "//h1[text()='LINKS']/../..//table/tbody//i[@title='File Copy']")
    public WebElement viewIconInLinks;

    @FindBy(xpath = "//thead/th")
    public List<WebElement> edithistorycolumnnames;

    @FindBy(xpath = "(//*[contains(text(),'SUCCESS MESSAGE')])[1]")
    public WebElement savemsg;

    @FindBy(xpath = "(//button/span/span[text()='clear'])[1]")
    public WebElement editibcorrcancelbutton;

    @FindBy(xpath = "//span[contains(text(),'CASE/CONSUMER')]")
    public WebElement caseconsumerlink;

    @FindBy(xpath = "//span[contains(text(),'TASK/SERVICE REQUEST')]")
    public WebElement taskservicerequestlink;

    @FindBy(xpath = "//span[text()=' APPLICATION']")
    public WebElement applicationlink;

    @FindBy(xpath = "//span[text()='link']")
    public WebElement linkApplicationButton;

    @FindBy(xpath = "//input[@id='searchCaseType']")
    public WebElement casetypedropdown;

    @FindBy(xpath = "//input[@id='crmCaseID']")
    public WebElement caseidfield;

    @FindBy(xpath = "//h5[text()='CASE & CONSUMER SEARCH']")
    public WebElement caseconsumersearchfieldlabel;

    @FindBy(xpath = "//*[text()='EDITED ON']")
    public WebElement editedOnLabel;

    @FindBy(xpath = "//*[text()='EDITED BY']")
    public WebElement editedByLabel;

    @FindBy(xpath = "//*[text()='FIELD LABEL']")
    public WebElement fieldLabel;

    @FindBy(xpath = "//*[text()='PREVIOUS VALUE']")
    public WebElement previousValueLabel;

    @FindBy(xpath = "//*[text()='UPDATED VALUE']")
    public WebElement updatedValueLabel;

    @FindBy(xpath = "//h5[text()='VIEW INBOUND CORRESPONDENCE DETAILS']")
    public WebElement viewibcorrdeatilslabel;

    @FindBy(xpath = "//span[contains(text(),'chevron_right')]")
    public WebElement expandarrow;

    @FindBy(xpath = "//input[@name='linkCaseOnly']")
    public WebElement linkCaseOnly;

    @FindBy(xpath = "//input[@name='member']")
    public WebElement consumermember;

    @FindBy(xpath = "//span[text()='LINK RECORD']")
    public WebElement linkrecord;

    @FindBy(xpath = "//span[contains(text(),'REFRESH')]")
    public WebElement refreshbutton;

    @FindBy(xpath = "//tbody/tr[1]/td[2]")
    public WebElement caseid;

    @FindBy(xpath = "//tbody/tr[1]/td[2]")
    public WebElement applicationID;

    @FindBy(xpath = "//tbody/tr[1]/td[3]")
    public WebElement casename;

    @FindBy(xpath = "//tbody/tr[1]/td[3]")
    public WebElement applicationName;

    @FindBy(xpath = "//tbody/tr[3]/td[2]")
    public WebElement consumerid;

    @FindBy(xpath = "//tbody/tr[3]/td[3]")
    public WebElement consumername;

    @FindBy(xpath = "//a[@aria-label='Go to page number 1']")
    public WebElement page;

    public LinkedList<String> returnHistoryTabValuesByColumnOrder(int columnOrder){
      List<WebElement> allElements = Driver.getDriver().findElements(By.xpath("//td[@class = 'mdl-data-table__cell--non-numeric']"));
      LinkedList<String> returnElements = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
           returnElements.add(allElements.get((columnOrder - 1) * 5 + i).getText());
        }
       return returnElements;
    }

    @FindBy(xpath = "//input[@id='_TYPE']")
    public WebElement typedropdown;

    @FindBy(xpath = "//ul[@id='_TYPE-popup']/li")
    public List<WebElement> typedropdownvalues;

}