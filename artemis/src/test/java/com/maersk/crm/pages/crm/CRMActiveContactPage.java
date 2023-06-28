package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMActiveContactPage {

    

    public CRMActiveContactPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//h1[contains(text(), 'CASE / CONSUMER SEARCH')]")
    public WebElement contactRecordSign;

    @FindBy(xpath = "//*[contains(text(), 'GENERAL CONTACT')]")
    public WebElement generalConsumerInContactSighn;

    @FindBy(xpath = "(//*[contains(@class , 'mdl-data-table mdl-js-data-table mx-border-6dp table')])//tr[1]//td[1]")
    public WebElement caseID;
    //refactoring 11/02/18
    @FindBy(xpath = "(//*[contains(@class , 'mdl-data-table mdl-js-data-table mx-border-6dp table')])//tr[1]//td[2]")
    public WebElement consumerID;

    @FindBy(xpath = "//h6[contains(text(), 'LINKED CONTACT')]")
    public WebElement linkedContactSign;

    @FindBy(xpath = "//span[contains(text(), 'UNLINK CONTACT RECORD')]/parent::button")
    public WebElement unlinkContactRecord;

    @FindBy(xpath = "//th[contains(text(), 'DOB')]/../../following-sibling::tbody/tr[1]/td[1]")
    public WebElement fullName;

    @FindBy(xpath = "//th[contains(text(), 'DOB')]/../../following-sibling::tbody/tr[1]/td[5]")
    public WebElement ssn;

    @FindBy(xpath = "//th[contains(text(), 'DOB')]/../../following-sibling::tbody/tr[1]/td[4]")
    public WebElement dob;

    @FindBy(xpath = "(//h6[@class='m-0'])[3]")
    public WebElement uniqueID;

    @FindBy(xpath = "(//h6)[1]")
    public WebElement headerConsumerName;

    @FindBy(xpath = "(//p)[5]")
    public WebElement headerConsumerID;

    @FindBy(xpath = "//th[contains(text(), 'CONSUMER ROLE')]")
    public WebElement defaultConsumerRole;

    @FindBy(xpath = "//th[contains(text(), 'PREFERRED LANGUAGE')]")
    public WebElement defaultPreferredLang;

    @FindBy(xpath = "(//h6[@class='m-0'])[4]")
    public WebElement defaultMember;

    //    @FindBy(xpath = "(//h6[@class='m-0'])[5]")
    @FindBy(xpath = "(//*[contains(@class , 'mdl-data-table mdl-js-data-table mx-border-6dp table')])//tr[1]//td[7]")
    public WebElement defaultEnglish;

    //    @FindBy (id = "formatted-phone-input")
    @FindBy(id = "contactPhone")
    public WebElement contactPhoneNumber;

    @FindBy(xpath = "//p[contains(text(),'Date cannot be in the future')]")
    public WebElement futureDOBError;

    @FindBy(xpath = "//p[contains(text(),'Invalid date format')]")
    public WebElement notExistDateError;

    @FindBy(name = "contactEmail")
    public WebElement contactEmail;

    @FindBy(xpath = "//*[@name='programTypes']/..")
    public WebElement programTypes;

    @FindBy(xpath = "//*[@title='DOB Visiblity Off']")
    public WebElement unmaskDOB;

    @FindBy(xpath = "//*[@title='DOB Visiblity off']")
    public WebElement unmaskDOBSearchResult;

    @FindBy(xpath = "(//*[contains(text(),'visibility_off')])")
    public WebElement maskingDOB;

    //
    @FindBy(id = "consumerLastName")
    public WebElement consumerLastName;

    @FindBy(id = "consumerFirstName")
    public WebElement consumerFirstName;

    @FindBy(id = "consumerMiddleName")
    public WebElement consumerMiddleName;

    @FindBy(id = "consumerSSN")
    public WebElement consumerSsn;

    @FindBy(id = "consumerDateOfBirth")
    public WebElement consumerDob;

    @FindBy(id = "seacrmCaseID")
    public WebElement internalCaseId;

    @FindBy(id = "seacrmConsumerId")
    public WebElement internalConsumerId;

    @FindBy(xpath = "//*[contains(@class, 'mx-btn-primary mdl-shadow--2dp')]")
    ////div[@class='col-12 my-3']/button[1]
    public WebElement searchBtn;

    @FindBy(xpath = "//*[contains(@class, 'mx-btn-border mdl-js-button mdl-button--primary mx-btn mx-btn-cancel')]")
    public WebElement resetBtn;
    // @FindBy(xpath = "//div[@class='col-12 my-3']/button[2]/span[2]"), //div[@class='col-12 my-3']/button[2]
    // public WebElement resetBtn;

    @FindBy(xpath = "//span[text()='Cancel']")
    public WebElement cancelBtn;

    @FindBy(xpath = "//h1[contains(text(),'SEARCH RESULT')]")
    public WebElement searchResultHeader;

    @FindBy(xpath = "//*[contains(text(),'THIRD PARTY CONTACT')]")
    public WebElement thirdPartyContactTab;

    @FindBy(xpath = "//a[text()='GENERAL CONTACT']")
    public WebElement generalConactTab;

    @FindBy(xpath = "//span[text()='ADD CONSUMER']")
    public WebElement addConsumerBtn;

    @FindBy(xpath = "//div[@class='col-12 text-left px-0 mt-4 pt-2 pb-2']/button[2]")
    public WebElement addConsumerCancelBtn;

    @FindBy(xpath = "(//td[@class='MuiTableCell51588 MuiTableCell51590 MuiTableCell51593 body']/button)[1]")
    public WebElement firstResultArrow;

    @FindBy(name = "0_consumer")
    public WebElement firstContactRadio;

    @FindBy(xpath = "//*[text()='LINK RECORD']")
    public WebElement linkRecordBtn;

    @FindBy(name = "0-consumerDateOfBirth")
    public WebElement firstContactDobCheck;

    @FindBy(name = "0-consumerSSN")
    public WebElement firstContactSsnCheck;

    @FindBy(name = "0-consumerCRMCaseID")
    public WebElement firstContactCaseIdCheck;

    @FindBy(id = "sample5")
    public WebElement reasonsComment;

    @FindBy(id = "sample6")
    public WebElement additionalComment;

    @FindBy(xpath = "//tr/td[2]")
    public WebElement consumerInContactId;

    @FindBy(xpath = "//tr/td[3]")
    public WebElement consumerIdResult;

    @FindBy(name = "0_29381")
    public WebElement resultDropdown;

    @FindBy(name = "0_caConsumerFullName")
    public WebElement nameConsumer1;

    @FindBy(name = "0_caDob")
    public WebElement dobConsumer1;

    @FindBy(name = "0_caSsn")
    public WebElement ssnConsumer1;

    @FindBy(xpath = "//tr/td/button/span")
    public WebElement firstContactBtn;

    @FindBy(name = "0_caCrmConsumerId")
    public WebElement crmConsumerID1;

    @FindBy(xpath = "//span[contains(text(),'UNIDENTIFIED CONTACT')]")
    public WebElement unidentfied;

    @FindBy(xpath = "//span[contains(text(),'GENERAL CONTACT')]")
    public WebElement genCon;

    @FindBy(id = "mui-component-select-inboundCallQueue")
    public WebElement inboundCallQueue;

    @FindBy(id = "mui-component-select-programTypes")
    public WebElement programs;

    @FindBy(id = "mui-component-select-contactCampaignType")
    public WebElement contactCallCampaign;

    @FindBy(xpath = "//div[@id='mui-component-select-contactRecordStatusType']")
    public WebElement disposition;

    @FindBy(xpath = "//div[@id='mui-component-select-contactReason']")
    public WebElement contactRes;

    @FindBy(xpath = "//div[contains(@id,'mui-component-select-contactReason-')]")
    public WebElement editContactRes;

    @FindBy(xpath = "//div[@id='mui-component-select-contactAction']")
    public WebElement contactAct;

    @FindBy(xpath = "(//div[@id='mui-component-select-contactAction'])[2]")
    public WebElement contactActionEdit;

    @FindBy(xpath = "//li[contains(text(),'IDR Unsuccessful, Appeal Form Unresolved')]")
    public WebElement IDRUnsuccessfulAppealFormUnresolved;

    @FindBy(xpath = "//li[contains(text(),'IDR Successful, Appeal Form Resolved')]")
    public WebElement IDRSuccessfulAppealFormResolved;

    @FindBy(xpath = "//li[contains(text(),'Unable to Resolve Issue')]")
    public WebElement unableToResolvedIssue;

    @FindBy(xpath = "//li[contains(text(),'New Application - Complete')]")
    public WebElement newAppComplete;

    @FindBy(xpath = "//li[contains(text(),'Transferred to CCC Plus Helpline')]")
    public WebElement transferredCCCPlusHelp;

    @FindBy(xpath = "//li[contains(text(),'Transferred to Enterprise Call Center')]")
    public WebElement transferredtoEnterCallCenter;

    @FindBy(xpath = "//li[contains(text(),'Transferred to MCO')]")
    public WebElement transferredtoMCO;

    @FindBy(xpath = "//li[contains(text(),'Transferred to MCO Helpline')]")
    public WebElement transferredtoMCOHelp;

    @FindBy(xpath = "//li[contains(text(),'Transferred to Medicare')]")
    public WebElement transferredtoMedicare;

    @FindBy(xpath = "//li[contains(text(),'Transferred to Provider Helpline')]")
    public WebElement transferredtoProviderHelp;

    @FindBy(xpath = "//li[contains(text(),'Transferred to Recipient Helpline')]")
    public WebElement transferredtoRecipientHelp;

    @FindBy(xpath = "//li[contains(text(),'Update Application - Incomplete')]")
    public WebElement updateAppIncomplete;

    @FindBy(xpath = "//li[contains(text(),'Update Application - Complete')]")
    public WebElement updateAppComplete;

    @FindBy(xpath = "//li[contains(text(),'Consumer Failed Identity Proofing')]")
    public WebElement consumerFailedIdenitityProofing;

    @FindBy(xpath = "//li[contains(text(),'Advised of OEP/SEP timeframe')]")
    public WebElement advisedOEPSEPtimeframe;

    @FindBy(xpath = "//li[contains(text(),'Training Question')]")
    public WebElement trainingQuestion;

    @FindBy(xpath = "//li[contains(text(),'Contact Information Update')]")
    public WebElement contactInformationUpdate;

    @FindBy(xpath = "//li[contains(text(),'Referred to GCNJ email')]")
    public WebElement referredToGCNJemail;

    @FindBy(xpath = "//li[contains(text(),'Referred to LMS Support')]")
    public WebElement referredToLMSSupport;

    @FindBy(xpath = "//li[contains(text(),'Updated Address')]")
    public WebElement UpdatedAdInGetIns;

    @FindBy(xpath = "//li[contains(text(),'Escalated to Supervisor')]")
    public WebElement escalatedToSuper;

    @FindBy(xpath = "//li[contains(text(),'Created Review Complaint Task')]")
    public WebElement createdRCTask;

    @FindBy(xpath = "//li[contains(text(),'Resolved')]")
    public WebElement resolved;

    @FindBy(xpath = "//li[contains(text(),'Verification Document Invalid')]")
    public WebElement verificationDocumentInvalid;

    @FindBy(xpath = "//li[contains(text(),'Referred to Carrier')]")
    public WebElement refToCarrier;

    @FindBy(xpath = "//li[contains(text(),'Referred to Exchange Org')]")
    public WebElement refToExchanOrg;

    @FindBy(xpath = "//li[contains(text(),'Referred to FFM')]")
    public WebElement refToFFM;

    @FindBy(xpath = "//li[contains(text(),'Referred to Medicaid')]")
    public WebElement refToMedC;

    @FindBy(xpath = "//li[contains(text(),'Referred to Treasury')]")
    public WebElement refToTreas;

    @FindBy(xpath = "//li[contains(text(),'Other')]")
    public WebElement miscOther;

    @FindBy(xpath = "//li[contains(text(),'Direct Transfer to NJFC SBE Unit')]")
    public WebElement directTransferToNJFCSBEUnit;

    @FindBy(xpath = "//li[contains(text(),'Sent Password Reset Link')]")
    public WebElement sentPassResetLink;

    @FindBy(xpath = "//li[contains(text(),'Sent Activation Link')]")
    public WebElement sentActivationLink;

    @FindBy(xpath = "//li[contains(text(),'Logged NJ Support Ticket')]")
    public WebElement loggedNJSupportTicket;

    @FindBy(xpath = "//li[contains(text(),'Unresolved Technical Assistance')]")
    public WebElement unresolvedTechAssistance;

    @FindBy(xpath = "//div[@id='mui-component-select-consumerType']")
    public WebElement consumerType;

    @FindBy(xpath = "//h5[@class='mx-section-header float-left']")
    public WebElement links;

    @FindBy(id = "mui-component-select-contactOutcome")
    public WebElement outcomeOfContact;

    @FindBy(xpath = "//div[@id='mui-component-select-contactChannelType']")
    public WebElement channelField;

    @FindBy(xpath = "//a[contains(text(),'Case & Contact Details')]")
    public WebElement caseContact;

    @FindBy(xpath = "//div[1]/table[1]/tbody[1]/tr[1]/td[3]")
    public WebElement contactIdContactHis;

    @FindBy(xpath = "//button[contains(@class,'MuiButtonBase-root MuiButton-root MuiButton-text mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mx-btn mx-btn-border mx-btn-primary')]")
    public WebElement editContactContactDetails;

    @FindBy(xpath = "//div[@id='mui-component-select-contactReasonEditType']")
    public WebElement reasonForEditContactHis;

    @FindBy(id = "mui-component-select-preferredLanguageCode")
    public WebElement preferredLanguageCode;

    @FindBy(xpath = "//tr[@class='MuiTableRow-root mx-comment-border']")
    public List<WebElement> consumerMembers;

    @FindBy(xpath = "//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric  mx-rightborder mx-text-capitalize mx-table-td-wrap ']")
    public List<WebElement> consumerRoles;

    @FindBy (xpath = "//div[@class='mx-layout-profile-tab mb-4']/descendant::a")
    public List<WebElement> activeContactTabs;

    @FindBy (xpath = "//*[@role='tab']")
    public List<WebElement> contactRecInfoTabs;

    @FindBy (xpath = "(//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mx-link'])[2]")
    public WebElement taskLinkCaseID;

    @FindBy (xpath = "(//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mx-link'])")
    public List<WebElement> firstTaskLinkCaseID;

    @FindBy (xpath = "//h5[text()= 'CONTACT RECORD']")
    public WebElement contactRecordHeader;

    @FindBy (id = "mui-component-select-contactTranslationService")
    public WebElement translationServiceDropDown;

    @FindBy(xpath = "//li[contains(text(),'Referred to External Entity')]")
    public WebElement referredToExternalEntity;

    @FindBy(xpath = "//li[contains(text(),'Submitted for Processing')]")
    public WebElement submittedForProcessing;

    @FindBy(xpath = "//li[contains(text(),'Withdrawn')]")
    public WebElement withdrawn;

    @FindBy(xpath = "//li[contains(text(),'Generated Requested Form(s)')]")
    public WebElement generatedRequestedForms;

    @FindBy(xpath = "//li[contains(text(),'Referred to CoverVa.org for Self Print')]")
    public WebElement referredToCovervaorgForSelfPrint;

    @FindBy(xpath = "//li[contains(text(),'Provided LDSS Office Info')]")
    public WebElement providedLDSSOfficeInfo;

    @FindBy(xpath = "//li[contains(text(),'Provided Program Info')]")
    public WebElement providedProgramInfo;

    @FindBy(xpath = "//li[contains(text(),'No Response - Disconnected')]")
    public WebElement noResponseDisconnected;

    @FindBy(xpath = "//*[@id=\"agent-slider\"]/div/button/span")
    public WebElement collapseExpandWidget;

    @FindBy(xpath = "//li[contains(text(),'Created Pre-Hearing Conference Task')]")
    public WebElement createdPreHearingConferenceTask;

    @FindBy(xpath = "//li[contains(text(),'Created Appeals Service Request')]")
    public WebElement createdAppealsServiceRequest;

    @FindBy(xpath = "//li[contains(text(),'Provided Appeals Status')]")
    public WebElement providedAppealsStatus;

    @FindBy(xpath = "//li[contains(text(),'Withdrew Appeal')]")
    public WebElement withdrewAppeal;

    @FindBy(xpath = "//li[contains(text(),'Appeals Summary')]")
    public WebElement appealsSummary;

    @FindBy(xpath = "//li[contains(text(),'Escalated to External Entity')]")
    public WebElement escalatedToExternalEntity;

    @FindBy(xpath = "//li[contains(text(),'Escalated to Locality')]")
    public WebElement escalatedToLocality;

    @FindBy(xpath = "//li[contains(text(),'No Action Taken - Unable to Authenticate')]")
    public WebElement noActionTakenUnableToAuthenticate;

    @FindBy(xpath = "//li[contains(text(),'Provided Application Status')]")
    public WebElement providedApplicationStatus;

    @FindBy(xpath = "//li[contains(text(),'Provided Renewal Status')]")
    public WebElement providedRenewalStatus;

    @FindBy(xpath = "//li[contains(text(),'System Issues - Read VaCMS Down Script')]")
    public WebElement systemIssuesReadVaCMSDownScript;

    @FindBy(xpath = "//li[contains(text(),'Escalated to CVIU Eligibility')]")
    public WebElement escalatedToCVIUEligibility;

    @FindBy(xpath = "//li[contains(text(),'Report My Change')]")
    public WebElement reportMyChange;

    @FindBy(xpath = "//li[contains(text(),'Updated Authorized Representative')]")
    public WebElement updatedAuthorizedRepresentative;

    @FindBy(xpath = "//li[contains(text(),'Updated Contact Information')]")
    public WebElement updatedContactInformation;

    @FindBy(xpath = "//li[contains(text(),'Updated Demographic Information')]")
    public WebElement updatedDemographicInformation;

    @FindBy(xpath = "//li[contains(text(),'Updated Household Information')]")
    public WebElement updatedHouseholdInformation;

    @FindBy(xpath = "//li[contains(text(),'Updated Income Information')]")
    public WebElement updatedIncomeInformation;

    @FindBy(xpath = "//li[contains(text(),'Withdrew Complaint')]")
    public WebElement withdrewComplaint;

    @FindBy(xpath = "//li[contains(text(),'FAMIS Select Inquiry - Referred to CoverVA Website')]")
    public WebElement FAMISSelectInquiryReferredtoCoverVAWebsite;

    @FindBy(xpath = "//li[contains(text(),'FAMIS Select Inquiry - Referred to DMAS Unit')]")
    public WebElement FAMISSelectInquiryReferredtoDMASUnit;

    @FindBy(xpath = "//li[contains(text(),'Provided Benefit Status')]")
    public WebElement providedBenefitStatus;

    @FindBy(xpath = "//li[contains(text(),'Provided Coverage Information')]")
    public WebElement providedCoverageInformation;

    @FindBy(xpath = "//li[contains(text(),'Provided Enrollment Status')]")
    public WebElement providedEnrollmentStatus;

    @FindBy(xpath = "//li[contains(text(),'Provided Instructions on How to Cancel FAMIS Coverage')]")
    public WebElement providedInstructionsonHowtoCancelFAMISCoverage;

    @FindBy(xpath = "//li[contains(text(),'Provided Participating Providers')]")
    public WebElement providedParticipatingProviders;

    @FindBy(xpath = "//li[contains(text(),'Referred to MCO')]")
    public WebElement referredToMCO;

    @FindBy(xpath = "//li[contains(text(),'Referred to Recipient Help Line')]")
    public WebElement referredtoRecipientHelpLine;

    @FindBy(xpath = "//li[contains(text(),'Generated New Medicaid ID Card in MMIS')]")
    public WebElement generatedNewMedicaidIDCardinMMIS;

    @FindBy(xpath = "//li[contains(text(),'Provided Instructions on How to Cancel Medicaid Coverage')]")
    public WebElement providedInstructionsonHowtoCancelMedicaidCoverage;

    @FindBy(xpath = "//li[contains(text(),'Completed Telephonic Application')]")
    public WebElement completedTelephonicApplication;

    @FindBy(xpath = "//li[contains(text(),'Created Process Application Service Request')]")
    public WebElement createdProcessApplicationServiceRequest;

    @FindBy(xpath = "//li[contains(text(),'Escalated to CPU')]")
    public WebElement escalatedtoCPU;

    @FindBy(xpath = "//li[contains(text(),'Referred to Enterprise Call Center')]")
    public WebElement referredtoEnterpriseCallCenter;

    @FindBy(xpath = "//li[contains(text(),'Updated Application')]")
    public WebElement updatedApplication;

    @FindBy(xpath = "//li[contains(text(),'Withdrew Application')]")
    public WebElement withdrewApplication;

    @FindBy(xpath = "//li[contains(text(),'Escalated to CVIU Eligibility')]")
    public WebElement escalatedtoCVIUEligibility;

    @FindBy(xpath = "//li[contains(text(),'Re-Entry')]")
    public WebElement reEntry;

    @FindBy(xpath = "//li[contains(text(),'Renewal Incomplete')]")
    public WebElement renewalIncomplete;

    @FindBy(xpath="//button[contains(text(),'INTERACTION MANAGEMENT')]")
    public WebElement callManagment;

    @FindBy(xpath="//li[contains(text(),'No Action - Did Not Reach Consumer')]")
    public WebElement noActionDidNotReachConsumer;

    @FindBy(xpath="//li[contains(text(),'Resolved Inquiry')]")
    public WebElement resolvedInquiry;

    @FindBy(xpath="//li[contains(text(),'Unable to Resolve Inquiry')]")
    public WebElement unabletoResolveInquiry;

    @FindBy(xpath="//li[contains(text(),'Extended Pend Letter')]")
    public WebElement extendedPendLetter;

    @FindBy(xpath="//li[contains(text(),'LDSS Communication Form')]")
    public WebElement LDSSCommunicationForm;

    @FindBy(xpath="//li[contains(text(),'Re-Generated VCL')]")
    public WebElement ReGeneratedVCL;

    @FindBy(xpath="//li[contains(text(),'Re-Generated NOA')]")
    public WebElement ReGeneratedNOA;

    @FindBy(xpath="//li[contains(text(),'Transferred to External Entity')]")
    public WebElement transferredtoExternalEntity;

    @FindBy(xpath="//li[contains(text(),'Received Required Information')]")
    public WebElement receivedRequiredInformation;

    @FindBy(xpath="//li[contains(text(),'Completed PHC - Referred to DMAS Appeals ')]")
    public WebElement CompletedPHCReferredtoDMASAppeals;

    @FindBy(xpath="//li[contains(text(),'Unable to Reach - Scheduled Outbound Call')]")
    public WebElement UnabletoReachScheduledOutboundCall;

    @FindBy(xpath="//li[contains(text(),'Schedule Callback')]")
    public WebElement scheduledCallback;

    @FindBy(id = "mui-component-select-consumerType")
    public WebElement callerType;

    @FindBy(xpath = "//li[contains(text(),'Provided Benefits Status')]")
    public WebElement providedBenefitsStatus;

    @FindBy(xpath = "//li[contains(text(),'Provided 1095-B Information')]")
    public WebElement provided1095BInfo;

    @FindBy(xpath = "//li[contains(text(),'Provided Dental Information')]")
    public WebElement providedDentalInfo;

    @FindBy(xpath = "//li[contains(text(),'Regenerated 1095-B Form')]")
    public WebElement regenerated1095BInfo;

    @FindBy(xpath="//li[contains(text(),'Scheduled Callback')]")
    public WebElement scheduledCallBack;

    @FindBy(xpath="//*[contains(text(),'CONSUMER ID')]//..//../following::tbody/tr/td[2]")
    public WebElement searchResultsFirstRecord;

    @FindBy(id = "consumerCity")
    public WebElement consumerCity;

    @FindBy(id = "consumerCounty")
    public WebElement consumerCounty;

    @FindBy(id = "zipCode")
    public WebElement consumerZipCode;

    public WebElement action (String act){
        return Driver.getDriver().findElement(By.xpath("//li[contains(text(),'" + act + "')]"));
    }

    @FindBy(xpath="//*[contains(text(),'Case')]")
    public WebElement caseId;

    @FindBy(xpath="//*[contains(text(),'Consumer')]")
    public WebElement consumerRole;

    @FindBy(xpath = "//*[contains(text(),'PHONE is required and cannot be left blank')]")
    public WebElement phoneRequired;

    @FindBy(xpath = "//*[contains(text(),'PROGRAM is required and cannot be left blank')]")
    public WebElement programRequired;

    @FindBy(xpath = "//*[contains(text(),'DISPOSITION is required and cannot be left blank')]")
    public WebElement dispositionRequired;

    @FindBy(xpath = "//*[contains(text(),'CONSUMER TYPE is required and cannot be left blank')]")
    public WebElement consumerTypeRequired;

    @FindBy(xpath="//div[@id='mui-component-select-businessUnitSelect']")
    public WebElement businessUnitDropdwn;

    @FindBy(xpath = "//div[3]/div[3]/ul[1]/li")
    public List<WebElement> contactReasons;

    @FindBy(xpath = "//label[text()='CONTACT REASON']")
    public WebElement contactReasonsNew;

    public WebElement tabs (String tab) {
        return Driver.getDriver().findElement(By.xpath("//span[contains(text(),'"+tab+"')]"));
    }

    @FindBy(xpath = "//a[contains(text(), 'Demographic Info')]")
    public WebElement demographicInfoTab;

    @FindBy(xpath = "//a[contains(text(), 'Case & Contact Details')]")
    public WebElement caseContactDetailsTab;

    @FindBy(xpath = "//a[contains(text(), 'Task & Service Request')]")
    public WebElement taskServiceRequest;

    @FindBy(xpath = "//*[contains(text(),'GetInsured CONSUMER ID')]//..//../following::tbody/tr/td[2]")
    public WebElement searchResultConsumerId;

    @FindBy(xpath="//div[text()='CVCC']")
    public WebElement businessUnitCVCCvalue;

    @FindBy(xpath="//li[contains(text(),'CVIU')]")
    public WebElement businessUnitCVIUvalue;

    @FindBy(xpath="//li[contains(@class, 'MuiButtonBase-root')]")
    public WebElement businessUnitDropdwns;

    @FindBy(xpath="//*[contains(text(),'PHONE NUMBER')]")
    public WebElement phoneNumberAutGrid;

    @FindBy (xpath= "//li[contains(text(),'Web Chat')]")
    public WebElement businessUnitWebChat;

    @FindBy (xpath= "//div[@id='mui-component-select-facilityName']")
    public WebElement facilityName;

    @FindBy (xpath= "//input[@id='facilityName']")
    public WebElement facilityNameTextFree;

    @FindBy (xpath= "//*[contains(text(),'FACILITY TYPE')]")
    public WebElement facilityType;

    @FindBy (xpath= "//div[@id='mui-component-select-facilityType']")
    public WebElement facilityTypesNew;

    @FindBy(xpath = "//div[contains(@class,'MuiPaper-root MuiMenu-paper MuiPopover-paper MuiPaper-elevation8 MuiPaper-rounded')]//ul/li")
    public List<WebElement> actualFacilityTypes;

    @FindBy (xpath= "//div//h6[contains(text(),'Local Jail')]")
    public WebElement facilityTypeValueLocal;

    @FindBy (xpath= "(//h6[@class='m-0 mx-wordbreak'])[1]")
    public WebElement facilityTypeValueAll;

    @FindBy(xpath = "//div//p[text()='FACILITY NAME  is required and cannot be left blank']")
    public WebElement facilityNameRequiredMessage;

    @FindBy(xpath = "//li[@data-value = 'Education - Health Plan2']")
    public WebElement contactReasonEducationHealthPlan;

    @FindBy(xpath = "//span[contains(text(),'delete')]")
    public WebElement deleteContactReasonOnEditPage;

    @FindBy(xpath = "//span[@class='MuiButton-label' and text()='Continue']")
    public WebElement continueAfterDeletingContactReason;

    @FindBy(xpath = "//p[text()='FACILITY TYPE cannot exceed 50 characters']")
    public WebElement facilityTypeMaxCharsDisplayed;

    @FindBy(xpath = "//p[text()='Invalid Format Text']")
    public WebElement facilityTypeInvalidFormatText;

    @FindBy (xpath= "//input[@id='facilityType']")
    public WebElement facilityTypeNew;

    @FindBy (xpath= "//input[@name='facilityType']")
    public WebElement facilityTypeField;

    @FindBy(xpath = "//li[contains(text(),'Address Change')]")
    public WebElement adressChange;

    @FindBy(xpath = "//li[contains(text(),'Repeat Callers')]")
    public WebElement repeatCallers;

    @FindBy(xpath = "//li[contains(text(),'Follow Up')]")
    public WebElement followUp;

    @FindBy(xpath = "//li[contains(text(),'Backdating Enrollment Request')]")
    public WebElement backdatingEnrollmentRequest;

    @FindBy(xpath = "//li[contains(text(),'Backdating Subsidies Request')]")
    public WebElement backdatingSubsidiesRequest;

    @FindBy(xpath = "//li[contains(text(),'Reinstatement Request')]")
    public WebElement reinstatementRequest;

    @FindBy(xpath = "//li[contains(text(),'Retro Disenrollment Request')]")
    public WebElement retrodisenrollmentRequest;

    @FindBy(xpath = "//li[contains(text(),'Referred to Other State/Federal Agency')]")
    public WebElement referredtoOtherStateFederalAgency;

    @FindBy(xpath = "//li[contains(text(),'Report QLE')]")
    public WebElement reportQLE;

    @FindBy(xpath = "//li[contains(text(),'RIDP')]")
    public WebElement RIDP;

    @FindBy(xpath = "//*[@class='MuiCircularProgress-svg']")
    public WebElement MuiCircularOnSearchPage;

    @FindBy (xpath= "//span[contains(text(),'You have selected a Disposition of')]")
    public WebElement cancelledErrorMessage;

    @FindBy (xpath= "(//*[@class='MuiSvgIcon-root MuiSelect-icon Mui-disabled'])[1]")
    public WebElement dispositionFieldDisabled;

    @FindBy(id = "contactReason")
    public WebElement contactReasonInputValue;

    @FindBy(id = "contactAction")
    public WebElement contactActionInputValue;

    @FindBy(xpath = "(//div[@id='mui-component-select-contactAction'])[2]")
    public WebElement firstSavedEditContactActionDropdown;

    @FindBy(xpath = "(//div[@id='mui-component-select-contactReason'])[2]")
    public WebElement firstSavedEditContactReasonDropdown;

    @FindBy(xpath = "//button[@title=' Edit Reason']")
    public List<WebElement> reasonActionPanelEditButton;

    @FindBy(xpath = "//div[@class='MuiButtonBase-root MuiIconButton-root MuiAccordionSummary-expandIcon Mui-expanded MuiIconButton-edgeEnd']")
    public List<WebElement> reasonActionPanelOpenButtonList;

    @FindBy(xpath = "//p[.='CONTACT REASON']/following-sibling::h6")
    public WebElement contactReasonSavedValue;

    @FindBy(xpath = "//p[.='CONTACT ACTION']/following-sibling::h6")
    public WebElement contactActionSavedValue;

    @FindBy(xpath = "//div[@class='MuiAccordionSummary-content']")
    public WebElement contactResClick;

    @FindBy(xpath = "//div[@id='menu-contactAction']/div/following-sibling::div/ul/li")
    public List<WebElement> contactActionValuesList;

    @FindBy(xpath = "//div[@id='menu-contactReason']/div/following-sibling::div/ul/li")
    public List<WebElement> contactReasonValuesList;

    @FindBy(xpath = "//div[@id='menu-businessUnitSelect']/div/following-sibling::div/ul/li")
    public List<WebElement> businessUnitDropdownList;

    @FindBy(xpath = "//div[@id='menu-contactAction']/div/following-sibling::div/ul/li")
    public List<WebElement> contactActionDropdownList;

    @FindBy(xpath = "//div[@id='menu-contactReason']/div/following-sibling::div/ul/li")
    public List<WebElement> contactReasonDropdownList;

    @FindBy(xpath = "//div[@id='menu-facilityName']/div/following-sibling::div/ul/li")
    public List<WebElement> facilityNameDropdownList;

    @FindBy(xpath = "//div[@id='menu-inboundCallQueue']/div/following-sibling::div/ul/li")
    public List<WebElement> inboundCallQueueDropdownList;

    @FindBy(xpath = "(//th[contains(text(),'ID')])[3]")
    public WebElement linksIDColumn;

    @FindBy(xpath = "(//th[contains(text(),'NAME')])[2]")
    public WebElement linksNameColumn;

    @FindBy(xpath = "//th[contains(text(),'TYPE')]")
    public WebElement linksTypeColumn;

    @FindBy(xpath = "//th[contains(text(),'STATUS DATE')]")
    public WebElement linksStatusDateColumn;

    @FindBy(xpath = "(//th[contains(text(),'STATUS')])[2]")
    public WebElement linksStatusColumn;

    @FindBy(xpath = "//label[@for='contactRecordType']")
    public WebElement contactTypeLabel;

    @FindBy(id = "mui-component-select-contactRecordType")
    public WebElement contactTypeDropdown;

    @FindBy(xpath = "//label[@for='inboundCallQueue']")
    public WebElement inboundCallQueueLabel;

    @FindBy(xpath = "//label[@for='programTypes']")
    public WebElement programTypesLabel;

    @FindBy(xpath = "//label[@for='contactTranslationService']")
    public WebElement contactTranslationServiceLabel;

    @FindBy(xpath = "//label[@for='contactCampaignType']")
    public WebElement contactCampaignTypeLabel;

    @FindBy(xpath = "//label[@for='contactOutcome']")
    public WebElement contactOutcomeLabel;

}
