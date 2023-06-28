package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMCreateGeneralTaskPage {

    

    public CRMCreateGeneralTaskPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//p[text()='SOURCE']/../h6")
    public WebElement lblSource;

    @FindBy(xpath = "//p[text()='STATUS DATE']/../h6")
    public WebElement lblStatusDate;

    @FindBy(xpath = "//p[text()='DUE DATE']/../h6")
    public WebElement lblDueDate;

    @FindBy(xpath = "//p[text()='DUE IN']/../h6")
    public WebElement lblDueIn;

    @FindBy(xpath = "//p[contains(text(),'CASE ID')]/../h6")
    public WebElement lblCaseId;

    @FindBy(xpath = "//p[contains(text(),'CONSUMER ID')]/../h6")
    public WebElement lblConsumerId;

    @FindBy(xpath = "//input[@id='taskTypeId']/ancestor::div[1]")
    public WebElement lstTaskType;

    @FindBy(xpath = "//input[@name='taskPriority']/ancestor::div[1]")
    public WebElement lstTaskPriority;

    @FindBy(xpath = "//input[@name='taskStatus']/..")
    public WebElement txtTaskStatus;

    @FindBy(id = "mui-component-select-taskStatus")
    public WebElement taskStatus;

    @FindBy(xpath = "//input[@id='auto-staffAssignedTo']")
    public WebElement lstAssignee;

    @FindBy(xpath = "//div[@id='mui-component-select-businessUnitAssignedTo']")
    public WebElement lstAssigneeBusinessUnit;

    @FindBy(xpath = "//div[@id='menu-businessUnitAssignedTo']//ul/li")
    public List<WebElement> assigneeBusinessUnitListValues;

    @FindBy(xpath = "//textarea[@name='taskInfo']")
    public WebElement txtTaskInfo;

    @FindBy(xpath = "//textarea[@name='taskInfo']/../following-sibling::p")
    public WebElement lblTaskInfoErrorMessage;

    @FindBy(xpath = "//span[text()='check']/ancestor::button")
    public WebElement btnSave;

    @FindBy(xpath = "(//span[text()='clear']/ancestor::button)[1]")
    public WebElement btnCancel;

    @FindBy(xpath = "//span[text()='ERROR MESSAGE']/..//div//span")
    public WebElement lblPopUpErrorMessage;

    @FindBy(xpath = "//div[@id='menu-taskTypeId']//ul/li")
    public List<WebElement> taskTypeListValues;

    @FindBy(xpath = "//div[@id='menu-taskPriority']//ul/li")
    public List<WebElement> taskPriorityListValues;

    @FindBy(xpath = "//input[@id='taskTypeId']/../div")
    public WebElement lblSelectedTaskType;

    //refactorBy:Vidya Date:03-23-2020
    @FindBy(xpath = "//*[contains(text(), 'LINKS')]/../..//tbody/tr[1]")
    public List<WebElement> linkRecordRows;

    @FindBy(xpath = "//*[contains(text(), 'WARNING MESSAGE')]/ancestor::div[@role='dialog']//button[1]")
    public WebElement btnContinueWarningPopUp;

    @FindBy(xpath = "//*[contains(text(), 'WARNING MESSAGE')]/ancestor::div[@role='dialog']//p")
    public WebElement lblWarningMessage;

    //refactorBy:Vidya Date: 01-24-2020 Reason: change the xpath
    @FindBy(xpath = "//span[text()='SUCCESS MESSAGE']")
    public WebElement lblSuccessMessage;

    @FindBy(xpath = "//h5[contains(text(), 'SUCCESS MESSAGE')]/ancestor::div[contains(@class, 'dialog')]//button")
    public WebElement btnOkSuccessDialog;

    @FindBy(xpath = "//h5[contains(text(),'CREATE TASK')]")
    public WebElement createTaskPageHeader;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mx-table table')])//tr[*]//td[3]")
    })
    public List<WebElement> linkIds;

    @FindAll({
            @FindBy(xpath = "(//*[contains(@class , 'mx-table table')])//tr[*]//td[4]")
    })
    public List<WebElement> linkNames;

    @FindBy(xpath = "//p[contains(text(),'Service')]")
    public WebElement userAccountName;

    @FindBy(xpath = "//*[contains(text(), 'WARNING MESSAGE')]/ancestor::div[@role='dialog']//button[2]")
    public WebElement btnCancelWarningPopUp;

    @FindBy(xpath = "//label[contains(text(),'TASK TYPE')]")
    public WebElement taskTypeValue;

    @FindBy(xpath = "//input[@id='taskTypeId']/ancestor::div[1]/div[@role='button']")
    public WebElement taskTypevalue;

    @FindBy(xpath = "//input[@id='staffAssignedTo']/../div[@role='button']")
    public WebElement lstAssigneeValue;

    @FindBy(xpath = "//label[text()='PLAN CHANGE REASON']/../../..//label")
    public List<WebElement> planProviderAdditionalFlds;

    @FindBy(xpath = "//input[@id='requestedNewPlan']")
    public WebElement requestedNewPlanChkBx;

    @FindBy(xpath = "//input[@id='requestedNewProvider']")
    public WebElement requestedNewProviderChkBx;

    @FindBy(xpath = "//input[@id='urgentAccessToCare']")
    public WebElement urgentAccessToCareChkBx;

    @FindBy(name = "providerFirstName")
    public WebElement providerFirstNameTxtBox;

    @FindBy(name = "providerLastName")
    public WebElement providerLastNameTxtBox;

    @FindBy(name = "providerNpi")
    public WebElement providerNpiTxtBox;

    @FindBy(name = "providerStateId")
    public WebElement providerStateIdTxtBox;

    @FindBy(xpath = "//label[text()='PROVIDER PHONE']/..//input")
    public WebElement providerPhoneTxtBox;

    @FindBy(xpath = "//div[@id='menu-planId']//ul/li")
    public List<WebElement> planIdDrDw;

    @FindBy(xpath = "//input[@id='planId']/ancestor::div[1]")
    public WebElement planId;

    @FindBy(xpath = "//div[@id='menu-planName']//ul/li")
    public List<WebElement> planNameDrDw;

    @FindBy(xpath = "//input[@id='planName']/ancestor::div[1]")
    public WebElement planName;

    @FindBy(xpath = "//div[@id='menu-planChangeReason']//ul/li")
    public List<WebElement> planChangeReasonDrDw;

    @FindBy(xpath = "//input[@id='planChangeReason']/ancestor::div[1]")
    public WebElement planChangeReason;

    @FindBy(xpath = "//div[@id='menu-providerRecordIssue']//ul/li")
    public List<WebElement> providerRecordIssueDrDw;

    @FindBy(xpath = "//input[@id='providerRecordIssue']/ancestor::div[1]")
    public WebElement providerRecordIssue;

    @FindBy(xpath = "//label[text()='PLAN EFFECTIVE DATE']/..//input")
    public WebElement planEffectiveDate;

    @FindBy(xpath = "//p[text()='Invalid Date Format']")
    public WebElement invalidDateFormat;

    @FindBy(xpath = "//p[text()='Invalid Time']")
    public WebElement invalidTimeFormat;

    @FindBy(xpath = "//label[text()='PLAN START DATE']/..//input")
    public WebElement planStartDate;

    @FindBy(xpath = "//label[text()='PROVIDER ADDRESS LINE 1']/../../../..//label")
    public List<WebElement> providerAddressLineFields;

    @FindBy(xpath = "//label[text()='NEW ADDRESS LINE 1']/../../../..//label")
    public List<WebElement> newAddressLineFields;

    @FindBy(xpath = "//label[text()='OLD ADDRESS LINE 1']/../../../..//label")
    public List<WebElement> oldAddressLineFields;

    @FindBy(name = "providerAddressLine1")
    public WebElement providerAddressLine1TxtBox;

    @FindBy(name = "providerAddressLine2")
    public WebElement providerAddressLine2TxtBox;

    @FindBy(name = "newAddressLine1")
    public WebElement newAddressLine1TxtBox;

    @FindBy(name = "newAddressLine2")
    public WebElement newAddressLine2TxtBox;

    @FindBy(name = "oldAddressLine1")
    public WebElement oldAddressLine1TxtBox;

    @FindBy(name = "oldAddressLine2")
    public WebElement oldAddressLine2TxtBox;

    @FindBy(id = "mui-component-select-providerAddressCounty")
    public WebElement providerAddressCounty;

    @FindBy(name = "providerAddressCity")
    public WebElement providerAddressCity;

    @FindBy(id = "auto-providerAddressState")
    public WebElement providerAddressState;

    @FindBy(name = "providerAddressZipCode")
    public WebElement providerAddressSZipCode;

    @FindBy(name = "newAddressCity")
    public WebElement newAddressCity;

    @FindBy(id = "auto-newAddressState")
    public WebElement newAddressState;

    @FindBy(name = "newAddressZip")
    public WebElement newAddressZipCode;

    @FindBy(name = "oldAddressCity")
    public WebElement oldAddressCity;

    @FindBy(id = "auto-oldAddressState")
    public WebElement oldAddressState;

    @FindBy(name = "oldAddressZipCode")
    public WebElement oldAddressZipCode;

    @FindBy(xpath = "//input[@id='contactReason']/ancestor::div[1]")
    public WebElement cantactReason;

    @FindBy(xpath = "//div[@id='menu-contactReason']//ul/li")
    public List<WebElement> contactReasonDrDw;

    @FindBy(xpath = "//input[@id='outreachLocation']/ancestor::div[1]")
    public WebElement outreachLocation;

    @FindBy(xpath = "//div[@id='outreach-location-edit']")
    public WebElement multiselectValueLink;

    @FindBy(xpath = "//div[@role='tooltip']//ul//li")
    public List<WebElement> selectedToolTipValue;

    @FindBy(xpath = "//label[text()='PREFERRED CALL BACK TIME']/..//input")
    public WebElement preferredCallBackTime;

    @FindBy(xpath = "//body/div[@id='root']/div[1]/main[1]//div[1]/div[8]//label")
    public List<WebElement> generalCRMEBAdditionalFlds;

    @FindBy(name = "caseWorkerFirstName")
    public WebElement caseWorkerFirstNameTxtBox;

    @FindBy(name = "caseWorkerLastName")
    public WebElement caseWorkerLastNameTxtBox;

    @FindBy(xpath = "//label[text()='PREFERRED PHONE']/..//input")
    public WebElement preferredPhoneTxtBox;

    @FindBy(xpath = "//input[@id='channel']/ancestor::div[1]")
    public WebElement channel;

    @FindBy(xpath = "//div[@id='menu-channel']//ul/li")
    public List<WebElement> channelDrDw;

    @FindBy(xpath = "//input[@id='contactReason']/ancestor::div[1]")
    public WebElement contactReason;

    @FindBy(xpath = "//div[@id='menu-outreachLocation']//ul/li")
    public List<WebElement> outreachLocationDrDw;

    @FindBy(xpath = "//input[@id='program']/..")
    public WebElement program;

    @FindBy(xpath = "//div[@id='menu-program']//ul/li")
    public List<WebElement> programDrDw;

    @FindBy(xpath = "//label[text()='APPOINTMENT DATE']/..//input")
    public WebElement appointmentDate;

    @FindBy(xpath = "//label[text()='DATE OF BIRTH']/..//input")
    public WebElement dateOfBirth;

    @FindBy(xpath = "//label[text()='PREFERRED CALL BACK DATE']/..//input")
    public WebElement preferredCallBackDate;

    @FindBy(xpath = "//label[text()='APPOINTMENT TIME']/..//input")
    public WebElement appointmentTime;

    @FindBy(xpath = "//h5[contains(text(),'LINKS')]")
    public WebElement link;

    @FindBy(xpath = "//h1[text()='CASE / CONSUMER SEARCH']")
    public WebElement caseConsumerSection;

    @FindBy(id = "consumerFirstName")
    public WebElement consumerFirstName;

    @FindBy(id = "consumerLastName")
    public WebElement consumerLastName;

    @FindBy(id = "consumerId")
    public WebElement internalconsumerId;

    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text mx-btn  mx-btn-border mx-btn-primary mdl-shadow--2dp mr-2']")
    public WebElement consumerSearchBtn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/../..//table//tbody//tr")
    public List<WebElement> search_recordCount;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/../..//table//tbody[1]/tr/td[1]/button")
    public WebElement search_Firstrecord;

    @FindBy(xpath = "//h5[contains(text(),' CONSUMER DETAILS')]")
    public List<WebElement> consumer_Details;

    @FindBy(xpath = "//input[@id='linkCaseOnly']")
    public WebElement linkCaseOnly_Chkbox1;

    @FindBy(xpath = "//input[@id='linkCaseOnly']/../..")
    public WebElement linkCaseOnly_Chkbox;

    @FindBy(xpath = "//span[text()='LINK CASE/CONSUMER']/..")
    public WebElement linkRecord_Consumerbutton;

    @FindBy(xpath = "//input[@type='radio']/../..")
    public List<WebElement> consumer_radioItems;

    @FindBy(xpath = "//span[text()='LINK CASE']/..")
    public WebElement linkRecord_Casebutton;

    @FindBy(xpath = "//ul[@class='pagination']/li/a")
    public List<WebElement> lnkPageNations;

    @FindBy(xpath = "//tr/td[2]")
    public List<WebElement> caseIDValue;

    @FindBy(xpath = "//tr/td[1]/button")
    public List<WebElement> click_searchRecord;

    @FindBy(xpath = "//tr/td[3]")
    public List<WebElement> consumerIDValue;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr[1]//td[2]")
    public List<WebElement> taskIDs;

    @FindBy(xpath = "//button[@title='Next page']/span//*[name()='svg']")
    public WebElement nextPage;

    @FindBy(xpath = "//table/tbody//tr/td[4]")
    public List<WebElement> linkrowsize;

    @FindBy(xpath = "//input[@id='actionTaken']/..")
    public WebElement actionTaken;

    @FindBy(xpath = "//label[text()='APPLICATION SOURCE']/..//div/div")
    public WebElement applicationSource;

    @FindBy(xpath = "//label[text()='FROM PHONE']/..//input")
    public WebElement fromPhone;

    @FindBy(xpath = "//label[text()='FROM EMAIL']/..//input")
    public WebElement fromEmail;

    @FindBy(xpath = "//label[text()='FROM NAME']/..//input")
    public WebElement fromName;

    @FindBy(xpath = "//div[@id='mui-component-select-inboundCorrespondenceType']")
    public WebElement inboundCorrespondenceType;

    @FindBy(name = "inboundCorrespondenceId")
    public WebElement inboundCorrespondenceId;

    @FindBy(xpath = "//span[contains(text(),'INBOUND CORRESPONDENCE WORKABLE FLAG')]")
    public WebElement inboundCorrespondenceWorkableFlag;

    @FindBy(xpath = "//input[@id='informationType']/..")
    public WebElement informationType;

    @FindBy(id = "mui-component-select-invalidAddressReason")
    public WebElement invalidAddressReason;

    @FindBy(name = "notificationId")
    public WebElement notificationId;

    @FindBy(id = "mui-component-select-outboundCorrespondenceType")
    public WebElement outboundCorrespondenceType;

    @FindBy(name = "outboundCorrespondenceId")
    public WebElement outboundCorrespondenceId;

    @FindBy(xpath = "//input[@id='preferredLanguage']/..")
    public WebElement preferredLanguage;

    @FindBy(id = "mui-component-select-returnedMailReason")
    public WebElement returnedMailReason;

    @FindBy(xpath = "//input[@id='complaintAbout']/..")
    public WebElement complaintAboutDrDn;

    @FindBy(id = "mui-component-select-reason")
    public WebElement reasonDrDn;

    @FindBy(name = "externalApplicationId")
    public WebElement externalApplicationId;

    @FindBy(name = "externalCaseId")
    public WebElement externalCaseId;

    @FindBy(id = "auto-name")
    public WebElement name;

    @FindBy(xpath = "//label[text()='COMPLAINT ABOUT']/../../..//label")
    public List<WebElement> njTaskAdditionalFields;

    @FindBy(xpath = "//a[text()='Task & Service Request']")
    public WebElement taskAndService;

    @FindBy(xpath = "//tbody[1]/tr[1]/td[10]/button")
    public WebElement initiate;

    @FindBy(xpath = "//button[text()='SAVE']")
    public WebElement taskDetsave;

    @FindBy(xpath = "//a[contains(text(),'Active Contact')]")
    public WebElement activeCon;

    @FindBy(xpath = "//div[3]/div[1]/div[2]/div[1]/table[1]/tbody[1]/tr[1]/td[2]")
    public WebElement firstIdLink;

    @FindBy(xpath = "//div[1]/div[2]/div[1]/table[1]/tbody[1]/tr[2]/td[2]")
    public WebElement secondIdLink;

    @FindBy(xpath = "//div[3]/div[1]/div[2]/div[1]/table[1]/tbody[1]/tr[1]/td[3]")
    public WebElement firstNameLink;

    @FindBy(xpath = "//div[3]/div[1]/div[2]/div[1]/table[1]/tbody[1]/tr[2]/td[3]")
    public WebElement secondNameLink;

    @FindBy(xpath = "//div[3]/div[1]/div[2]/div[1]/table[1]/tbody[1]/tr[1]/td[4]")
    public WebElement firstTypeLink;

    @FindBy(xpath = "//td[text() ='General']")
    public WebElement scndTypeLink;

    @FindBy(xpath = "//div[3]/div[1]/div[2]/div[1]/table[1]/tbody[1]/tr[2]/td[5]")
    public WebElement scndstatusDateLink;

    @FindBy(xpath = "//div[1]/div[2]/div[1]/table[1]/tbody[1]/tr[2]/td[6]")
    public WebElement scndStatusLink;

    @FindBy(xpath = "//div[@id='mui-component-select-contactAction']")
    public WebElement contactAction;

    @FindBy(xpath = "//body[1]/div[2]/div[3]/ul[1]/li[2]")
    public WebElement contactActOpt;

    @FindBy(xpath = "//div[1]/div[2]/div[4]/button[2]/span[1]")
    public WebElement reasonCheck;

    @FindBy(xpath = "//table/tbody/tr[1]/td[3]/div/p/b[1]")
    public WebElement taskSearchId;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[3]/p[1]")
    public WebElement taskSearhType;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[5]/p[1]")
    public WebElement taskSearchStatus;

    @FindBy(xpath = "//tbody[1]/tr[1]/td[6]/p[1]")
    public WebElement taskSearchStatusDate;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[10]")
    public WebElement taskConsumerId;

    @FindBy(xpath = "//span[text()='Search']")
    public WebElement taskSearchbt;

    @FindBy(xpath = "//table/tbody/tr[3]/td[3]/div/p")
    public WebElement firstTaskIdBt;

    @FindBy(xpath = "//table/tbody/tr[1]/td[3]")
    public WebElement firstTaskIdBtNew;

    @FindBy(xpath = "//td[@class='mdl-data-table__cell--non-numeric  mx-link']")
    public WebElement acTconsumerId;

    @FindBy(xpath = "//h5[text()='LINKS']/..//tbody//td[3]")
    public WebElement lblContactRecordId;

    @FindBy(xpath = "//input[@id='taskTypeId']/..//div")
    public WebElement lblTaskType;

    @FindBy(xpath = "//i[text()='keyboard_backspace']")
    public WebElement lblBackArrow;

    @FindBy(xpath = "//h6[contains(text(), 'PROVIDER ADDRESS DETAILS')]/../..//label")
    public List<WebElement> providerAddressDetailsLabels;

    @FindBy(xpath = "//h6[contains(text(), 'OLD ADDRESS DETAILS')]/../..//label")
    public List<WebElement> oldAddressDetailsLabels;

    @FindBy(xpath = "//h6[contains(text(), 'NEW ADDRESS DETAILS')]/../..//label")
    public List<WebElement> newAddressDetailsLabels;

    @FindBy(xpath = "//h6[contains(text(), 'ADDRESS DETAILS')]")
    public List<WebElement> addressesOrder;

    @FindBy(xpath = "//*[contains(text(), 'NEW ADDRESS DETAILS')]/../following-sibling::div//p")
    public List<WebElement> newAddressDetailsLabelsOnViewTask;

    @FindBy(xpath = "//*[contains(text(), 'PROVIDER ADDRESS DETAILS')]/../following-sibling::div//p")
    public List<WebElement> providerAddressDetailsLabelsOnViewTask;

    @FindBy(xpath = "//*[contains(text(), 'OLD ADDRESS DETAILS')]/../following-sibling::div//p")
    public List<WebElement> oldAddressDetailsLabelsOnViewTask;

    @FindBy(xpath = "//*[contains(text(), ' ADDRESS DETAILS')]")
    public List<WebElement> addressesOrderInViewTask;

    @FindBy(xpath = "//i[contains(text(),'phone')]/../..//td")
    public List<WebElement> contactRecordInfo;

    @FindBy(xpath = "//i[text()='person']/../following-sibling::td")
    public List<WebElement> consumerInfo;

    @FindBy(xpath = "//i[text()='folder_shared']/../..//td")
    public List<WebElement> caseInfo;

    @FindBy(xpath = "//*[text()='LINKS']/../..//table/tbody//tr/td[3]")
    public List<WebElement> contactLinkrowsize;

    @FindBy(xpath = "//th[text()='NAME']//following::tr//td[3]")
    public List<WebElement> contactLinkNames;

    @FindBy(xpath = "//i[text()='assignment']/../following-sibling::td")
    public List<WebElement> taskLinkInfo;

    @FindBy(xpath = "//div[@role='presentation']//ul//li")
    public List<WebElement> nameDrDn;

    @FindBy(xpath = "//input[@id='disposition']/..")
    public WebElement disposition;

    @FindBy(xpath = "(//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mx-link'])[2]")
    public WebElement editTaskCaseId;

    @FindBy(xpath = "(//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mx-link'])[4]")
    public WebElement editTaskConsumerId;

    @FindBy(id = "mui-component-select-taskStatus")
    public WebElement createTaskStatus;

    @FindBy(id = "mui-component-select-taskDisposition")
    public WebElement dispositionTask;

    @FindBy(xpath = "//tbody/tr/td[12]/button")
    public WebElement firstInitiate;

    @FindBy(xpath = "//*[contains(text(),'DISPOSITION is required and cannot be left blank')]")
    public WebElement errorMessageTask;

    @FindBy(xpath = "//div[@id='mui-component-select-disposition']")
    public WebElement dispos;

    @FindBy(xpath = "//div[@id='mui-component-select-taskTypeId']")
    public WebElement taskType;

    @FindBy(id = "mui-component-select-issueType")
    public WebElement taskIssueType;

    @FindBy(id = "mui-component-select-requestType")
    public WebElement taskRequestType;

    @FindBy(id = "mui-component-select-locality")
    public WebElement taskLocality;

    @FindBy(id = "mui-component-select-complaintType")
    public WebElement taskComplaintType;

    @FindBy(id = "mui-component-select-businessUnit")
    public WebElement taskBusinessUnit;

    @FindBy(xpath = "//span[text()='LINK CONSUMER']/..")
    public WebElement linkConsumerbutton;

    @FindBy(xpath = "//p[text()='FROM EMAIL is not in the correct format']")
    public WebElement emailError;

    @FindBy(xpath = "//div[@class='MuiPaper-root MuiMenu-paper MuiPopover-paper MuiPaper-elevation8 MuiPaper-rounded']/ul/li")
    public List<WebElement> outboundCorresTypeDrDw;

    @FindBy(xpath = "//label[text()='APPLICATION SOURCE']/../../..//label")
    public List<WebElement> listofTaskTemplateFields;

    @FindBy(xpath = "//p[text()='From Name must contain characters only']")
    public WebElement fromNameError;

    @FindBy(xpath = "//div[@id='menu-complaintAbout']//ul/li")
    public List<WebElement> complaintAboutDrDnValues;

    @FindBy(xpath = "//*[@id='menu-reason']//ul/li")
    public List<WebElement> reasonDrDnValues;

    @FindBy(xpath = "//span[contains(text(),'Unlink Case/Consumer')]")
    public WebElement unlinkCaseOrConsumer;

    @FindBy(xpath = "//div[@id='menu-taskStatus']//ul/li")
    public List<WebElement> taskStatusListValues;

    @FindBy(xpath = "(//table/tbody/tr/td[2]/p)[1]")
    public WebElement secondTaskId;

    @FindBy(id = "correspondenceId")
    public WebElement correspondenceIdSearch;

    @FindBy(xpath = "//tbody[@class='MuiTableBody-root']/tr[5]/td[2]")
    public WebElement taskIdInboundCorrespondence;

    @FindBy(xpath = "//tbody[@class='MuiTableBody-root']/tr[5]/td[3]")
    public WebElement taskNameInboundCorrespondence;

    @FindBy(xpath = "//tbody[@class='MuiTableBody-root']/tr[5]/td[4]")
    public WebElement taskTypeInboundCorrespondence;

    @FindBy(xpath = "//tbody[@class='MuiTableBody-root']/tr[5]/td[5]")
    public WebElement taskStatusDateInboundCorrespondence;

    @FindBy(xpath = "//tbody[@class='MuiTableBody-root']/tr[5]/td[6]")
    public WebElement taskStatusInboundCorrespondence;

    @FindBy(xpath = "(//h6[@class='m-0 mx-text-1-2em mx-color-text-secondary mx-wordbreak'])[7]")
    public WebElement taskTypeTaskDetail;

    @FindBy(xpath = "(//h6[@class='m-0 mx-text-1-2em mx-color-text-secondary mx-wordbreak'])[9]")
    public WebElement taskStatusTaskDetail;

    @FindBy(xpath = "(//h6[@class='m-0 mx-text-1-2em mx-color-text-secondary mx-wordbreak'])[18]")
    public WebElement inboundCorrespondenceTypeTaskDetail;

    @FindBy(xpath = "(//table[@class='MuiTable-root mdl-data-table mdl-js-data-table mx-table table mt-4 mb-0 mdl-color--grey-50']/tbody/tr/td[3])[1]")
    public WebElement correspondenceLinkIdTaskDetail;

    @FindBy(xpath = "(//table[@class='MuiTable-root mdl-data-table mdl-js-data-table mx-table table mt-4 mb-0 mdl-color--grey-50']/tbody/tr/td[4])[1]")
    public WebElement correspondenceLinkNameTaskDetail;

    @FindBy(xpath = "(//table[@class='MuiTable-root mdl-data-table mdl-js-data-table mx-table table mt-4 mb-0 mdl-color--grey-50']/tbody/tr/td[5])[1]")
    public WebElement correspondenceLinkTypeTaskDetail;

    @FindBy(xpath = "(//table[@class='MuiTable-root mdl-data-table mdl-js-data-table mx-table table mt-4 mb-0 mdl-color--grey-50']/tbody/tr/td[6])[1]")
    public WebElement correspondenceLinkStatusDateTaskDetail;

    @FindBy(xpath = "(//table[@class='MuiTable-root mdl-data-table mdl-js-data-table mx-table table mt-4 mb-0 mdl-color--grey-50']/tbody/tr/td[7])[1]")
    public WebElement correspondenceLinkStatusDetail;

    @FindBy(xpath = "//h6[@class='mt-0 float-right mx-color-text-primary mt-2']")
    public WebElement inboundCorrepondenceId;

    @FindBy(xpath = "//*[contains(text(),'TYPE')]/following-sibling::h6")
    public WebElement TypeInboundCorrespondence;

    @FindBy(xpath = "(//div[@class='col-3 col-xxl-2-5 my-2 ']/h6)[3]")
    public WebElement inboundCorrespondenceStatus;

    @FindBy(xpath = "//i[text()='description']/../following-sibling::td")   ////i[text()='description']/../..//td
    public List<WebElement> linkedInboundInfo;

    @FindBy(id = "mui-component-select-complaintAbout")
    public WebElement complaintAboutDropdown;

    @FindBy(id = "mui-component-select-reason")
    public WebElement selectReasonDropdown;

    @FindBy(xpath = "//span[contains(text(),'LINK CASE')]")
    public WebElement linkCaseButton;

    @FindBy(xpath = "//span[contains(text(),'VALIDATE')]")
    public WebElement validateTaskbutton;

    @FindBy(xpath = "//span[contains(text(),'LINK CASE/CONSUMER')]")
    public WebElement linkCaseConsumerButtonAfterValidation;

    @FindBy(xpath = "//button[@class='mdl-button mdl-js-button mdl-button--icon mdl-button--colored mx-grid-accord-arrow']")
    public WebElement expandFistConsumer;

    @FindBy(id = "mui-component-select-reasonForEdit")
    public WebElement reasonForEditDrpDn;

    @FindBy(xpath = "//table[1]/tbody[1]/tr/td[4]")
    public List<WebElement> linkedNames;

    @FindBy(xpath = "//table[1]/tbody[1]/tr/td[3]")
    public List<WebElement> linkedIds;

    @FindBy(xpath = "//button[@class='mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mx-btn-border mx-btn-primary']")
    public WebElement butSaveAfterLink;

    @FindBy(xpath = "//span[contains(text(),'GENERAL CONTACT')]")
    public WebElement generalContactTab;

    @FindBy(xpath = "//span[contains(text(),'SAVE')]")
    public WebElement saveEditTask;

    @FindBy(xpath = "//div[2]/div[5]/h6[1]")
    public WebElement getCaseIdTask;

    @FindBy(xpath = "//div[@id='sub-menu-list']//ul/li")
    public List<WebElement> taskTypeList;

    @FindBy(xpath = "//input[@id='actionTaken']/../../../..//label")
    public List<WebElement> reviewAppealAdditionalFlds;

    @FindBy(xpath = "//div[@id='menu-actionTaken']//ul/li")
    public List<WebElement> actionTakenDrDnValues;

    @FindBy(xpath = "//p[text()='ACTION TAKEN  is required and cannot be left blank']")
    public WebElement atMandatoryErrMsg;

    @FindBy(xpath = "//div[@title='ConnectionPoint Logo']/../..//div[contains(@class,'datetime')]//p")
    public WebElement projectDateAtHeader;

    @FindBy(xpath = "//p[text()='ADDRESS LINE 1 must contain both numeric and alphabetic characters to be valid']")
    public List<WebElement> addressLine1ErrMsg;

    @FindBy(xpath = "//label[text()='APPLICATION SOURCE']/../../..//label")
    public List<WebElement> correspondenceAdditionalFlds;

    @FindBy(xpath = "//input[@id='applicationType']/..")
    public WebElement applicationType;

    @FindBy(xpath = "//label[text()='DATE TRANSLATION ESCALATED']/..//input")
    public WebElement dateTranslationEscalated;

    @FindBy(xpath = "//label[text()='DATE TRANSLATION RECEIVED']/..//input")
    public WebElement dateTranslationReceived;

    @FindBy(xpath = "//label[text()='DATE TRANSLATION MAILED']/..//input")
    public WebElement dateTranslationMailed;

    @FindBy(name = "externalConsumerID")
    public WebElement externalConsumerID;

    @FindBy(xpath = "//label[text()='DUE DATE']/..//input")
    public WebElement dueDate;

    @FindBy(name = "connectionPointContactRecordId")
    public WebElement cpCRId;

    @FindBy(name = "connectionPointSRId")
    public WebElement cpSRId;

    @FindBy(name = "connectionPointTaskId")
    public WebElement cpTaskId;

    @FindBy(xpath = "//p[text()='case worker first name can not be a number']")
    public WebElement cwFirstNameErrMsg;

    @FindBy(xpath = "//p[text()='case worker last name cannot contain numbers']")
    public WebElement cwLastNameErrMsg;

    @FindBy(xpath = "//p[text()='CONNECTIONPOINT CONTACT RECORD ID must be numeric values only']")
    public WebElement cpCRErrMsg;

    @FindBy(xpath = "//p[text()='CONNECTIONPOINT SR ID must be numeric values only']")
    public WebElement cpSRErrMsg;

    @FindBy(xpath = "//p[text()='CONNECTIONPOINT TASK ID must be numeric values only']")
    public WebElement cpTaskErrMsg;

    @FindBy(xpath = "//input[@id='actionTakenSingle']/..")
    public WebElement actionTakenSingle;

    @FindBy(xpath = "//input[@id='outcome']/..")
    public WebElement outcome;

    @FindBy(xpath = "//div[@id='menu-inboundCorrespondenceType']//ul/li")
    public List<WebElement> inbDocTyDrDnValues;

    @FindBy(xpath = "//label[contains(text(),'RECEIVED DATE')]/..//input")
    public WebElement receivedDate;

    @FindBy(xpath = "//label[text()='DATE RESENT']/..//input")
    public WebElement dateResent;

    @FindBy(xpath = "//label[text()='REMAND DUE DATE']/..//input")
    public WebElement remandDueDate;

    @FindBy(xpath = "//label[text()='REMAND COMPLETION DATE']/..//input")
    public WebElement remandCompletionDate;

    @FindBy(xpath = "//input[@id='remandReason']/..")
    public WebElement remandReason;

    @FindBy(xpath = "//input[@id='eligibilityDecision']/..")
    public WebElement eligibilityDecision;

    @FindBy(xpath = "//*[@id='mui-component-select-reasonForCancel']")
    public WebElement cancelReason;

    @FindBy(xpath = "//p[text()='provider first name cannot have numbers']")
    public WebElement providerFirstNameErrMsg;

    @FindBy(xpath = "//p[text()='provider last name cannot have numbers']")
    public WebElement providerLastNameErrMsg;

    @FindBy(xpath = "//p[text()='PROVIDER EMAIL is not in the correct format']")
    public WebElement providerEmailError;

    @FindBy(name = "providerEmail")
    public WebElement providerEmail;

    @FindBy(xpath = "//input[@id='accessibilityNeeded']/..")
    public WebElement accessibilityNeeded;

    @FindBy(id = "mui-component-select-facilityType")
    public WebElement facilityType;

    @FindBy(id = "mui-component-select-facilityName")
    public WebElement facilityName;

    @FindBy(xpath = "//h5[text()='LINKS']/../..//table/tbody//tr/td[5]")
    public List<WebElement> linkType;

    @FindBy(id = "addressZipCode")
    public WebElement taskZipCode;

    @FindBy(xpath = "//i[text()='assignment']/../..//td[3]")
    public WebElement linkedSrId;

    @FindBy(xpath = "//p[text()='AR EMAIL is not in the correct format']")
    public WebElement AREmailError;

    @FindBy(name = "email")
    public WebElement AREmail;

    @FindBy(xpath = "//label[text()='AR PHONE']/..//input")
    public WebElement ARPhone;

    @FindBy(xpath = "//p[text()='AR FIRST NAME must contain alphabetical values only']")
    public WebElement ARFirstNameError;

    @FindBy(xpath = "//p[text()='AR LAST NAME must contain alphabetical values only']")
    public WebElement ARLastNameError;

    @FindBy(name = "firstName")
    public WebElement ARFirstNameTxtBox;

    @FindBy(name = "lastName")
    public WebElement ARLastNameTxtBox;

    @FindBy(name = "addressLine1")
    public WebElement arAddressLine1TxtBox;

    @FindBy(name = "addressLine2")
    public WebElement arAddressLine2TxtBox;

    @FindBy(name = "addressCity")
    public WebElement arCityTxtBox;

    @FindBy(name = "addressState")
    public WebElement arStateTxtBox;

    @FindBy(name = "addressZipCode")
    public WebElement arZipCodeTxtBox;

    @FindBy(xpath = "//p[text()='AR ADDRESS LINE 1 must contain both numeric and alphabetic characters to be valid']")
    public WebElement arAddressLine1ErrorMsg;

    @FindBy(xpath = "//p[text()='AR ZIP CODE is required and cannot be left blank']")
    public WebElement arZipCodeErrorMsg;

    @FindBy(xpath = "//p[text()='AR STATE must contain characters only']")
    public WebElement arStateErrorMsg;

    @FindBy(name = "organization")
    public WebElement organization;

    @FindBy(xpath = "//p[text()='CASE WORKER FIRST NAME must contain alphabetical values only']")
    public WebElement cwFirstNameErrMsg1;

    @FindBy(xpath = "//p[text()='CASE WORKER LAST NAME must contain alphabetical values only']")
    public WebElement cwLastNameErrMsg1;

    @FindBy(xpath = "//label[text()='MY WORKSPACE DATE']/..//input")
    public WebElement myWorkSpaceDate;

    @FindBy(xpath = "//label[contains(text(),'RETURNED MAIL RECEIVED DATE')]/..//input")
    public WebElement returnedMailReceivedDate;

    @FindBy(xpath = "//p[text()='SR CATEGORY']/../h6")
    public WebElement srCategory;

    @FindBy(xpath = "//label[text()='VCL DUE DATE']/..//input")
    public WebElement vclDueDate;

    @FindBy(xpath = "//input[@id='missingInfoRequired']/..")
    public WebElement missingInfoRequired;

    @FindBy(name = "noOfApplicantsInHousehold")
    public WebElement noOfApplicantsInHousehold;

    @FindBy(xpath = "//label[text()='APPLICATION RECEIVED DATE']/..//input")
    public WebElement appReceivedDate;

    @FindBy(xpath = "//input[@id='applicationStatus']/..")
    public WebElement appStatus;

    @FindBy(xpath = "//label[text()='VCL SENT DATE']/..//input")
    public WebElement vclSentDate;

    @FindBy(xpath = "//input[@id='pre-HearingReason']/..")
    public WebElement preHearingReason;

    @FindBy(xpath = "//input[@id='caseStatus']/..")
    public WebElement caseStatus;

    @FindBy(xpath = "//div[@id='menu-pre-HearingReason']//ul/li")
    public List<WebElement> preHearingReasonDrpDow;

    @FindBy(xpath = "//input[@id='pre-HearingOutcome']/..")
    public WebElement preHearingOutcome;

    @FindBy(xpath = "//div[@id='menu-pre-HearingOutcome']//ul/li")
    public List<WebElement> preHearingOutcomeDrpDow;

    @FindBy(id = "mui-component-select-actionTakenSingle")
    public WebElement actionTakenSlider;

    @FindBy(xpath = "//label[text()='APPLICATION UPDATE DATE']/..//input")
    public WebElement applicationUpdateDate;

    @FindBy(xpath = "//label[text()='LDSS RECEIVED DATE']/..//input")
    public WebElement ldssReceivedDate;

    @FindBy(xpath = "//label[text()='APPLICATION SIGNATURE DATE']/..//input")
    public WebElement applicatiSignatureDate;

    @FindBy(name = "noOfApplicantsInHousehold")
    public WebElement numberOfApplicantsInHousehold;

    @FindBy(name = "noOfApprovedApplicants")
    public WebElement numberOfApprovedApplicants;

    @FindBy(xpath = "//label[text()='MI RECEIVED DATE']/..//input")
    public WebElement miReceivedDate;

    //@FindBy(xpath = "//input[@id='decisionSource']/..")
    @FindBy(id = "mui-component-select-decisionSource")
    public WebElement decisionSource;

    @FindBy(id = "mui-component-select-denialReason")
    public WebElement denialReason;

    @FindBy(xpath = "//p[text()='FIRST NAME must be at least 2 characters']")
    public WebElement firstNameErrorMsg;

    @FindBy(xpath = "//p[text()='LAST NAME must be at least 2 characters']")
    public WebElement lastNameErrorMsg;

    @FindBy(name = "grievance")
    public WebElement grievance;

    @FindBy(name = "rid")
    public WebElement rid;

    @FindBy(name = "fieldValue")
    public WebElement contactPhone;

    @FindBy(xpath = "//input[@id='requestedNewPlan']/ancestor::div[1]")
    public WebElement desiredPlan;

    @FindBy(xpath = "//label[text()='DATE FOLLOW-UP EMAIL SENT']/..//input")
    public WebElement dateFollowUpEmailSent;

    @FindBy(xpath = "//label[text()='DATE RECEIVED GRIEVANCE']/..//input")
    public WebElement dateReceivedGrievance;

    @FindBy(xpath = "//label[text()='DATE HEALTH PLAN CONTACTED']/..//input")
    public WebElement dateHealthPlanContacted;

    @FindBy(xpath = "//label[text()='DATE STATE NOTIFIED']/..//input")
    public WebElement dateSateNotified;

    @FindBy(xpath = "//input[@id='decision']/..")
    public WebElement decision;

    @FindBy(xpath = "//input[@id='consumerSatisfied']/..")
    public WebElement consumerSatisfied;

    @FindBy(xpath = "//input[@id='finalDecision']/..")
    public WebElement finalDecision;

    @FindBy(xpath = "//label[text()='DATE DECISION LETTER SENT']/..//input")
    public WebElement dateDecisionLetterSent;

    @FindBy(xpath = "//label[text()='DMAS RECEIVED DATE']/..//input")
    public WebElement dmasReceivedDate;

    @FindBy(xpath = "//label[text()='CoverVA RECEIVED DATE']/..//input")
    public WebElement coverVAReceivedDate;

    @FindBy(id = "appealReason")
    public WebElement appealReason;

    @FindBy(xpath = "//label[text()='APPEALS CASE SUMMARY DUE DATE']/..//input")
    public WebElement appealCaseSummaryDueDate;

    @FindBy(name = "appealsCaseSummaryHyperlink")
    public WebElement appealsCaseSummaryHyperlink;

    @FindBy(id = "mui-component-select-appealCaseSummaryStatus")
    public WebElement appealCaseSummaryStatus;

    @FindBy(id = "mui-component-select-reviewOutcome")
    public WebElement reviewOutcome;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/div/div/div/div[2]/div/div/div[2]/div[1]/div[2]/div/table/tbody/tr[3]/td[5]")
    public WebElement contactRecordType;

    @FindBy(xpath = "(//div//table//tbody//tr[3]//td[5])[text()='General']")
    public WebElement contactRecordTypeGeneral;

    @FindBy(xpath = "(//div//table//tbody//tr[3]//td[5])[text()='Third Party']")
    public WebElement contactRecordTypeThirdParty;

    @FindBy(xpath = "(//div//table//tbody//tr[1]//td[5])[text()='Unidentified Contact']")
    public WebElement contactRecordTypeUnidentified;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/div/div/div/div[2]/div/div/div[2]/div[1]/div[2]/div/table/tbody/tr[1]/td[5]")
    public WebElement unidentifiedCRType;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/div/div/div/div[2]/div/div/div[2]/div[1]/div[2]/div/table/tbody/tr[1]/td[3]")
    public WebElement consumerIDfromTaskPage;

    @FindBy(xpath = "//input[@id='currentPlan']/..")
    public WebElement currentPlan;

    @FindBy(name = "other")
    public WebElement other;

    @FindBy(name = "name")
    public WebElement memberName;

    @FindBy(xpath = "//label[text()='CONTACT NAME']/..//input")
    public WebElement contactName;

    @FindBy(id = "mui-component-select-disenrollmentReason")
    public WebElement disenrollmentReason;

    @FindBy(xpath = "//label[text()='SOURCE']/..")
    public WebElement source;

    @FindBy(xpath = "//input[@id='desiredPlan']/ancestor::div[1]")
    public WebElement desiredPlantxt;

    @FindBy(xpath = "//td[text()='Created']//parent::tr//td[3]")
    public WebElement taskIdByCreatedStatus;

    @FindBy(xpath = "//input[@id='invalidAddressReason']/..")
    public WebElement interimStatus;

    @FindBy(xpath = "//label[text()='DISENROLLMENT DATE']/..//input")
    public WebElement disenrollmentDate;

    @FindBy(xpath = "//label[text()='INCIDENT DATE']/..//input")
    public WebElement incidentDate;

    @FindBy(xpath = "//div[@role='presentation']")
    public WebElement assigNDrop;

    @FindBy(xpath = "//span[contains(text(),' CREATE LINK(S)')]")
    public WebElement contactLinks;

    @FindBy(xpath = "//span[contains(text(),'CASE/ CONSUMER')]")
    public WebElement caseConsumerBtn;

    @FindBy(xpath = "//h6[contains(text(),'Resolved')]")
    public WebElement viewdispoResolved;

    @FindBy(xpath = "//*[text()='GetInsured CASE ID']/../p/following-sibling::h6")
    public WebElement getInsuredCaseIDonCreateTask;

    @FindBy(xpath = "//*[text()='GetInsured CONSUMER ID']/../p/following-sibling::h6")
    public WebElement getInsuredConsumerIDonCreateTask;

    @FindBy(id = "mui-component-select-escalationReason")
    public WebElement escalationReason;

    @FindBy(id = "escalated")
    public WebElement escalated;

    @FindBy(id = "mui-component-select-organization")
    public WebElement organizationDD;

    @FindBy(xpath = "//label[contains(text(),'COVERAGE START DATE')]")
    public WebElement coverageStartDate;

    @FindBy(xpath = "//label[contains(text(),'COVERAGE END DATE')]")
    public WebElement coverageEndtDate;

    @FindBy(xpath = "//label[text()='CSR NAME']/..//input")
    public WebElement csrName;

    public WebElement viewFields(String fields) {
        return Driver.getDriver().findElement(By.xpath("//p[text()='" + fields + "']"));
    }

    public WebElement dateOfContact(String fields) {
        return Driver.getDriver().findElement(By.xpath("//h6[text()='" + fields + "']"));
    }

    @FindBy(id = "mui-component-select-invalidReason")
    public WebElement invalidReasonDD;

    @FindBy(xpath = "//label[text()='VCCC RESPONSE DATE']/..//input")
    public WebElement vcccResponseDate;

    @FindBy(xpath = "//label[text()='DATE SENT TO VCCC']/..//input")
    public WebElement dateSenttoVccc;

    @FindBy(xpath = "//input[@id='reasonExplanationDropdown']/..")
    public List<WebElement> reasonExplanation;

    @FindBy(name = "explanation")
    public List<WebElement> explanation;

    @FindBy(xpath = "//input[@id='programRequired']/..")
    public WebElement programRequired;

    @FindBy(xpath = "//table[1]/tbody[1]/tr/td[5]")
    public List<WebElement> linkedTypes;

    @FindBy(xpath = "//table[1]/tbody[1]/tr/td[7]")
    public List<WebElement> linkedStatues;

    @FindBy(xpath = "//tr[@style='display: none;']")
    public List<WebElement> consumerLinks;

    @FindBy(xpath = "//label[text()='NEW PLAN START DATE']/..//input")
    public WebElement newPlanStartDate;

    @FindBy(xpath = "//p[text()='PHONE must be 10 characters']")
    public WebElement phoneErrorMsg;

    @FindBy(xpath = "//input[@id='auto-csrName']")
    public WebElement csrNameAutoComp;

    @FindBy(xpath = "//label[text()='SENT NOA DATE']/..//input")
    public WebElement sentNOADate;

    @FindBy(xpath = "//h5[text()='TASK DETAILS']/..//p[text()='CASE ID']/../h6")
    public WebElement editLblCaseId;

    @FindBy(xpath = "//*[text()='Close']")
    public WebElement closeButton;

    @FindBy(id = "requestedDate")
    public WebElement requestedDate;

    @FindBy(xpath = "//input[@id='usedTobaccoInTheLast6Months']/ancestor::div[1]")
    public WebElement usedTobaccoInTheLastSixMonths;

    @FindBy(xpath = "//p[contains(text(),'must contain characters only')]")
    public WebElement contactNameErrorMsg;

    @FindBy(id = "dateOfVoicemail")
    public WebElement dateOfVoicemail;

    @FindBy(xpath = "//label[text()='TIME OF VOICEMAIL']/..//input")
    public WebElement timeOfVoicemail;

    @FindBy(xpath = "//p[text()='# of Approved Applicants must be greater than 0']")
    public WebElement getNumberOfApprovedApplicantsErrorMsg;

    @FindBy(xpath = "//p[contains(text(),'Invalid Format Text')]")
    public WebElement warningExternalAppId;

    @FindBy(xpath = "//p[contains(text(),'ADDRESS LINE 1 must contain both numeric and alpha')]")
    public WebElement addressLn1;

    @FindBy(id = "mui-component-select-addressType")
    public WebElement addressType;

    @FindBy(id = "mui-component-select-addressSource")
    public WebElement addressSource;

    @FindBy(id = "addressZipCode")
    public WebElement addressZipCode;

    @FindBy(xpath = "//p[text()='DUE DATE']/../h6")
    public List<WebElement> dueDatesList;

    @FindBy(xpath = "//label[text()='CONTACT EMAIL']/..//input")
    public WebElement contactEmail;

    @FindBy(name = "reasonText")
    public WebElement reasonField;

    @FindBy(xpath = "//p[text()='CONTACT EMAIL is not in the correct format']")
    public WebElement contactEmailError;

    @FindBy(xpath = "//p[text()='RID # must contain only numeric characters to be valid']")
    public WebElement ridErrorMessage;

    @FindBy(xpath = "//p[text()='RID # must be 12 characters']")
    public WebElement ridErrorMessageForLength;

    @FindBy(name = "applicationId")
    public WebElement applicationId;

    @FindBy(xpath = "//ul[@id='auto-csrName-popup']//li")
    public List<WebElement> csrNameAutopopulateVlus;

    @FindBy(xpath = "//div[2]/div[1]/table[1]/tbody[1]/tr/td[3]")
    public List<WebElement> activecontactLinkrowsize;

    @FindBy(xpath = "//*[text()='Contact Record']//preceding-sibling::td[1]")
    public WebElement contactId;

    @FindBy(xpath = "//label[text()='START DATE']/..//input")
    public WebElement startDate;

    @FindBy(xpath = "//label[text()='END DATE']/..//input")
    public WebElement endDate;

    @FindBy(xpath = "//input[@id='reason']/..")
    public WebElement transferReason;

    @FindBy(xpath = "//p[text()='# of Approved Applicants In Household must be greater than 0']")
    public WebElement getNumberOfApprovedApplicantsHouseholdErrorMsg;

    @FindBy(xpath = "//p[text()='ORGANIZATION Must Contain Alphabetical Values Only']")
    public WebElement organizationErrorMessage;

    @FindBy(id = "businessUnitAssignedTo")
    ////input[@id='businessUnitAssignedTo']/..
    public WebElement businessUnitAssignedTo;

    @FindBy(xpath = "//h1[text()='LINKS']/../..//table/tbody//tr/td[3]")
    public List<WebElement> activeContactList;

    @FindBy(xpath = "//p[contains(text(),'# of Approved Applicants In Household can accept only numeric values')]")
    public WebElement errorMessageOnlyCanAcceptNumericValuesForHouseHold;

    @FindBy(xpath = "//p[contains(text(),'# of Approved Applicants can accept only numeric values')]")
    public WebElement errorMessageOnlyCanAcceptNumericValuesForApprovedApplicants;

    @FindBy(name = "addressDetailsLine1")
    public WebElement addressLineOne;

    @FindBy(name = "addressDetailsLine2")
    public WebElement addressLineTwo;

    @FindBy(name = "addressDetailsCity")
    public WebElement addressDetailsCity;

    @FindBy(name = "addressDetailsState")
    public WebElement addressDetailsState;

    @FindBy(name = "addressDetailsZip")
    public WebElement addressDetailsZip;

    @FindBy(xpath = "//td[text()='Consumer']")
    public WebElement linkConsumerId;

    @FindBy(xpath = "//td[text()='Outbound Correspondence']")
    public WebElement outboundCorrName;

    @FindBy(xpath = "//div//tbody/tr[5]/td[5]")
    public WebElement outboundCorrType;

    @FindBy(xpath = "//div//tbody/tr[7]/td[5]")
    public WebElement outboundCorr2Type;

    @FindBy(xpath = "//div//tbody/tr[5]/td[6]")
    public WebElement outboundCorrStatusDate;

    @FindBy(xpath = "//td[text()='Requested']")
    public WebElement outboundCorrStatusValue;

    @FindBy(xpath = "//span[text()='CANCEL SR']")
    public WebElement cancelSRcheckbox;

    @FindBy(xpath = "//span[text()='UPDATE ALL']")
    public WebElement updateALLcheckbox;

    @FindBy(xpath = "(//div[@id='mui-component-select-requestReason'])[1]")
    public WebElement requestReasonDropdown;

    @FindBy(xpath = "(//input[@name='memberName'])[1]")
    public WebElement memberNameForRequestReason;

    @FindBy(xpath = "(//input[@name='notes'])[1]")
    public WebElement notesForRequestReason;

    @FindBy(xpath = "(//div[@id='mui-component-select-requestedPlan'])[1]")
    public WebElement requestedPlan;

    @FindBy(xpath = "(//input[@id='requestedEffectiveDate'])[1]")
    public WebElement requestedEffectiveDate;

    @FindBy(xpath = "(//div[@id='mui-component-select-arcIndicator'])[1]")
    public WebElement arcIndicatorDropdown;

    @FindBy(xpath = "(//div[@id='mui-component-select-districtDisposition'])[1]")
    public WebElement districtDispositionDropdown;

    @FindBy(xpath = "//span[text()='ADD ']")
    public WebElement addButtonForMemberRequest;

    @FindBy(xpath = "(//div[@id='mui-component-select-requestReason'])[2]")
    public WebElement requestReasonDropdownSecond;

    @FindBy(xpath = "(//input[@name='memberName'])[2]")
    public WebElement memberNameForRequestReasonSecond;

    @FindBy(xpath = "(//input[@name='notes'])[2]")
    public WebElement notesForRequestReasonSecond;

    @FindBy(xpath = "(//div[@id='mui-component-select-requestedPlan'])[2]")
    public WebElement requestedPlanSecond;

    @FindBy(xpath = "(//input[@id='requestedEffectiveDate'])[2]")
    public WebElement requestedEffectiveDateSecond;

    @FindBy(xpath = "(//div[@id='mui-component-select-arcIndicator'])[2]")
    public WebElement arcIndicatorDropdownSecond;

    @FindBy(xpath = "(//div[@id='mui-component-select-districtDisposition'])[2]")
    public WebElement districtDispositionDropdownSecond;

    @FindBy(xpath = "//p[text()='AR ZIP CODE must be 5 characters']")
    public WebElement arZipCodeMustContainFiveCharErrorMessage;

    @FindBy(xpath = "(//p[contains(text(),'A link to a Case is required before this SR can be')]")
    public WebElement errorFCNoCaseLink;

    public WebElement fcFieldsinput(String fields) {
        return Driver.getDriver().findElement(By.xpath("//span[text()='" + fields + "']/../span/span/input"));
    }

    @FindBy(xpath = "//input[@id='lockedInPolicyExplained']/..")
    public WebElement lockedInPolicyExplained;

    @FindBy(id = "completedDate")
    public WebElement completedDate;

    @FindBy(id = "mui-component-select-approvalReason")
    public WebElement approvalReason;

    @FindBy(name = "renewalDueDate")
    public WebElement renewalProcessingDueDate;

    @FindBy(name = "callerName")
    public WebElement callerName;

    @FindBy(name = "memberName")
    public WebElement memberNameFc;

    @FindBy(id = "renewalDate")
    public WebElement renewalDate;

    @FindBy(id = "renewalCutoffDate")
    public WebElement renewalCutoffDate;

    @FindBy(xpath = "//label[text()='INBOUND CORRESPONDENCE TYPE']/..//input")
    public WebElement inboundCorrespondenceTypeInput;

    @FindBy(xpath = "//li[@tabindex='-1'][1]")
    public WebElement reasonForEditCorrectedDataEntry;

    @FindBy(xpath = "//input[@id='memberNameDropdown']//..")
    public WebElement memberNameDrpdown;

    @FindBy(xpath= "//input[@id='referredBy']//..")
    public WebElement referredBy;

    @FindBy(id= "mui-component-select-originatingEscalationType")
    public WebElement originatingEscalationType;

    @FindBy(id= "currentEscalationType")
    public WebElement currentEscalationType;

    @FindBy(xpath="//*[text()='LINKS']")
    public WebElement LinksText;

    @FindBy(xpath = "//p[text()='Application ID must follow the T12345678 format. Please update to match this format']")
    public WebElement applicationIdFormatError;

}
