package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class CRMTaskManageMyTasksPage {
    

    public CRMTaskManageMyTasksPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//*[contains(text(),'MY TASKS')]")
    public WebElement myTasksTab;

    @FindBy(xpath = "//*[contains(text(),'WORK QUEUE')]")
    public WebElement workQueueTab;

    //refactorBy:Vidya Date:24-02-2020
    @FindBy(xpath = "//h5[text()='TASK LIST']")
    public WebElement taskList;

    @FindBy(xpath = "//*[contains(@class, 'mx-btn-border mx-btn-primary mdl-shadow--2dp mt-2 float-right')]")
    public WebElement addTaskButton;

    @FindBy(xpath = "//*[text()='TASK ID']")
    public WebElement taskIdColumn;

    @FindBy(xpath = "//*[text()='TYPE']")
    public WebElement typeColumn;

    @FindBy(xpath = "//*[text()='PRIORITY']")
    public WebElement priorityColumn;

    @FindBy(xpath = "//*[text()='STATUS DATE']")
    public WebElement statusDateColumn;

    @FindBy(xpath = "//*[text()='STATUS']")
    public WebElement statusColumn;

    @FindBy(xpath = "//*[text()='DUE DATE']")
    public WebElement dueDateColumn;

    @FindBy(xpath = "//*[text()='DUE IN']")
    public WebElement dueInColumn;

    @FindBy(xpath = "//*[text()='CASE ID']")
    public WebElement caseIdColumn;

    @FindBy(xpath = "//*[text()='CONSUMER ID']")
    public WebElement consumerIdColumn;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[2]/preceding-sibling::td")
    public List<WebElement> expandTaskArrows;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[2]")
    public List<WebElement> taskIDs;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[3]")
    public List<WebElement> taskTypes;

    @FindBy(xpath = "//th[text()='DUE DATE']/../../following-sibling::tbody//td[6]//p")
    public List<WebElement> taskStatusDates;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[6]")
    public List<WebElement> taskStatuses;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[7]")
    public List<WebElement> taskDueDates;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[8]")
    public List<WebElement> taskDueIns;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[9]")
    public List<WebElement> taskCaseIDs;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[10]")
    public List<WebElement> taskConsumerIDs;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[12]/button")
    public List<WebElement> taskInitiateButtons;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[12]")
    public List<WebElement> taskInitiateButtonsText;

    @FindBy(xpath = "//button[text()='INITIATE']")
    public WebElement initiateLink;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[4]")
    public List<WebElement> priorities;

    @FindBy(xpath = "//*[contains(text(),'VIEW TASK')]")
    public WebElement viewTaskHeader;

    @FindBy(xpath = "(//h5[@class='mx-section-header float-left mt-2'])[1]")
    public WebElement taskDetailsSection;

    @FindBy(xpath = "//h5[text()='TASK DETAILS']")
    public WebElement taskDetailsHeader;

    @FindBy(xpath = "(//h5[@class='mx-section-header float-left mt-2'])[2]")
    public WebElement linksSection;

    @FindBy(xpath = "//button[contains(@class, 'mx-btn-border mx-btn')]")
    public WebElement editTaskButton;

    @FindBy(xpath = "//h5[text()='TASK DETAILS']/following-sibling::h5")
    public WebElement taskID;

    @FindBy(xpath = "//h5/..//p[text()='SOURCE']/..//h6")
    public WebElement source;

    @FindBy(xpath = "//h5/..//p[text()='CREATED BY']/..//h6")
    public WebElement taskCreatedBy;

    @FindBy(xpath = "//h5/..//p[text()='CREATED ON']/..//h6")
    public WebElement taskCreatedOn;

    @FindBy(xpath = "//h5/..//p[text()='STATUS DATE']/..//h6")
    public WebElement statusDate;

    @FindBy(xpath = "//h5/..//p[text()='DUE DATE']/..//h6")
    public WebElement dueDate;

    @FindBy(xpath = "//h5/..//p[text()='DUE IN']/..//h6")
    public WebElement dueIn;

    @FindBy(xpath = "//h5/..//p[text()='TASK TYPE']/..//h6")
    public WebElement taskType;

    @FindBy(xpath = "//h5/..//p[text()='PRIORITY']/..//h6")
    public WebElement priority;

    @FindBy(xpath = "//h5/..//p[text()='STATUS']/..//h6")
    public WebElement status;

    @FindBy(xpath = "//h5/..//p[text()='ASSIGNEE']/..//h6")
    public WebElement assignee;

    @FindBy(xpath = "//h5/..//p[text()='TASK INFORMATION']/..//h6")
    public WebElement taskInformation;

    @FindBy(xpath = "//h5/..//p[text()='TASK NOTES']/..//h6")
    public WebElement taskNotes;

    @FindBy(xpath = "//textarea[@name='taskNotes']")
    public WebElement editTaskNotes;

    @FindBy(xpath = "(//h6)[1]")
    public WebElement sourceValue;

    @FindBy(xpath = "(//h6)[2]")
    public WebElement createdByValue;

    @FindBy(xpath = "(//h6)[3]")
    public WebElement createdOnValue;

    @FindBy(xpath = "(//h6)[4]")
    public WebElement statusDateValue;

    @FindBy(xpath = "(//h6)[5]")
    public WebElement dueDateValue;

    @FindBy(xpath = "(//h6)[6]")
    public WebElement dueInValue;

    @FindBy(xpath = "(//h6)[7]")
    public WebElement taskTypeValue;

    @FindBy(xpath = "(//h6)[8]")
    public WebElement priorityValue;

    @FindBy(xpath = "(//h6)[9]")
    public WebElement statusValue;

    @FindBy(xpath = "(//h6)[10]")
    public WebElement assigneeValue;

    @FindBy(xpath = "(//h6)[11]")
    public WebElement taskInformationValue;

    @FindBy(xpath = "(//h6)[12]")
    public WebElement taskNotesValue;

    @FindBy(xpath = "//*[contains(text(),'TASK LIST')]/..//table/tbody/tr//td//p/../..")
    public List<WebElement> taskListRows;

    @FindBy(xpath = "//p[text()='SOURCE']/following-sibling::h6")
    public WebElement lblSource;

    @FindBy(xpath = "//p[text()='CREATED BY']/following-sibling::h6")
    public WebElement lblCreatedBy;

    @FindBy(xpath = "//p[text()='CREATED ON']/following-sibling::h6")
    public WebElement lblCreatedOn;

    @FindBy(xpath = "//p[text()='STATUS DATE']/following-sibling::h6")
    public WebElement lblStatusDate;

    @FindBy(xpath = "//p[text()='DUE DATE']/following-sibling::h6")
    public WebElement lblDueDate;

    @FindBy(xpath = "//p[text()='DUE IN']/following-sibling::h6")
    public WebElement lblDueIn;

    @FindBy(xpath = "//p[text()='TASK TYPE']/following-sibling::h6")
    public WebElement lblTaskType;

    @FindBy(xpath = "//p[text()='PRIORITY']/following-sibling::h6")
    public WebElement lblPriority;

    @FindBy(xpath = "//p[text()='STATUS']/following-sibling::h6")
    public WebElement lblStatus;

    @FindBy(xpath = "//p[text()='ASSIGNEE']/following-sibling::h6")
    public WebElement lblAssignee;

    @FindBy(xpath = "//h5[contains(text(), 'LINKS')]/..//table//tbody/tr[1]")
    public List<WebElement> linkTableRows;

    @FindBy(xpath = "//span[text()='Edit Task']/parent::button")
    public WebElement btnEditTask;

    @FindBy(xpath = "//h5[contains(text(), 'TASK ID')]")
    public WebElement lblTaskId;

    @FindBy(xpath = "//*[contains(text(),'TASK LIST')]/..//table/tbody/tr[2]")
    public List<WebElement> taskListRowDetails;

    @FindBy(xpath = "//span[text()='arrow_back']/parent::a")
    public WebElement lnkArrowBack;

    @FindBy(xpath = "//span[text()='arrow_forward']/parent::a")
    public WebElement lnkArrowForward;

    @FindBy(xpath = "//ul[@class='pagination']/li")
    public List<WebElement> lnkPageNations;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/thead/tr/th")
    public List<WebElement> taskListTableHeads;

    @FindBy(xpath = "//h5[text()='TASK LIST']")
    public WebElement taskListLabel;

    @FindBy(xpath = "(//*[contains(@class ,'mdl-data-table__cell--non-numeric pl-3')])[7]")
    public WebElement consumerNameColumn;

    @FindBy(xpath = "//p[text()='DUE DATE']/../h6")
    public WebElement getDueDate;

    @FindBy(xpath = "//p[text()='DUE IN']/../h6")
    public WebElement getDueIn;

    @FindBy(xpath = "(//th[text()='STATUS']/../../following-sibling::tbody//p)[4]")
    public WebElement getStatusValue;

    @FindBy(xpath = "//p[text()='ASSIGNEE']/following-sibling::p")
    public WebElement getAssigneeValue;

    @FindBy(xpath = "//th[text()='TASK ID']")
    public WebElement columnTaskId;

    @FindBy(name = "taskNotes")
    public WebElement taskInfo;

    @FindBy(xpath = "//i[text()='check']")
    public WebElement saveButton;

    @FindBy(xpath = "//input[@id='taskStatus']/..")
    public WebElement statusDropDown;

    @FindBy(xpath = "//th[text()='STATUS']/../../following-sibling::tbody//td[6]//p")
    public List<WebElement> getStatusValues;

    @FindBy(xpath = "//span[contains(text(),'EDIT TASK')]")
    public WebElement editBtn;

    @FindBy(xpath = "//h5[contains(text(),'No Records')]")
    public WebElement noRecordsMsg;

    @FindBy(xpath = "//input[@id='task-status']/..")
    public WebElement editPageStatusDropDown;

    @FindBy(xpath = "//input[@id='task-reasonForOnHold']/..")
    public WebElement reasonForHoldDropDown;

    @FindBy(xpath = "//span[text()='check']")
    public WebElement saveBtn;

    @FindBy(xpath = "//input[@name='reasonForCancel']/..")
    public WebElement reasonForCancelDropDown;

    @FindBy(xpath = "//ul[@role='listbox']//li")
    public List<WebElement> reasonDropDownVlu;

    @FindBy(xpath = "//input[@id='auto-staffAssignedTo']")
    public WebElement assigneeDropDown;

    @FindBy(xpath = "//*[text()='keyboard_backspace']")
    public WebElement backArrowInViewPage;

    @FindBy(xpath = "//span[text()='check']")
    public WebElement continueBtnOnWrnMsg;

    @FindBy(xpath = "//span[text()='SUCCESS MESSAGE']")
    public WebElement successMessage;

    @FindBy(xpath = "//span[text()='Task Successfully Saved']")
    public WebElement successMessageTxt;

    @FindBy(xpath = "(//th[text()='DUE DATE']/../../following-sibling::tbody//p)[7]")
    public WebElement getDueInValue;

    @FindBy(xpath = "(//th[text()='DUE DATE']/../../following-sibling::tbody//p)[5]")
    public WebElement getStatusColumnValue;

    @FindBy(xpath = "(//i[text()='info']")
    public WebElement errorinfoIcon;

    @FindBy(xpath = "//*[contains(text(),'TASK LIST')]/..//table/tbody/tr")
    public List<WebElement> taskListAllRows;

    //Aswath
    //aswath
    @FindBy(xpath = "//li[contains(text(),'Priority')]")
    public WebElement priorityDashboard;

    //@FindBy(xpath = "//*[contains(text(), 'STATUS')]/following-sibling::div/div")
    @FindBy(id = "mui-component-select-taskStatus")
    public WebElement statusDropdown;

    @FindBy(xpath = "//button[text()='SAVE']")
    public WebElement taskSilderSave;

    @FindBy(xpath = "//i[contains(text(),'clear')]")
    public WebElement taskSilderCancel;

    @FindBy(xpath = "//span[contains(text(),'check')]")
    public WebElement cancelWarningContinue;

    @FindBy(xpath = "//span[contains(text(),' WARNING MESSAGE')]")
    public WebElement cancelWarningPopup;

    @FindBy(xpath = "//span[contains(text(),'clear')]")
    public WebElement cancelWarningCancel;

    @FindBy(xpath = "//h5[contains(text(),'TASK LIST')]")
    public WebElement taskListPage;

    @FindBy(xpath = "//label[contains(text(), 'PRIORITY')]/following-sibling::div/div/div")
    public WebElement taskPriorityDD;

    @FindBy(xpath = "//p[contains(text(),'ASSIGNEE')]/following-sibling::h6")
    public WebElement taskSilderAssignee;

    @FindBy(xpath = "//textarea[@name='taskNotes']")
    public WebElement taskSilderTaskNotes;

    @FindBy(xpath = "//p[contains(text(),'SOURCE')]/following-sibling::h6")
    public WebElement taskSilderSource;

    @FindBy(xpath = "//p[contains(text(),'CREATED BY')]/following-sibling::h6")
    public WebElement taskSilderCreatedBy;

    @FindBy(xpath = "//p[contains(text(),'CREATED ON')]/following-sibling::h6")
    public WebElement taskSilderCreatedOn;

    @FindBy(xpath = "//p[contains(text(),'STATUS DATE')]/following-sibling::h6")
    public WebElement taskSilderStatusOn;

    @FindBy(xpath = "//p[contains(text(),'DUE DATE')]/following-sibling::h6")
    public WebElement taskSilderDueDate;

    @FindBy(xpath = "//p[contains(text(),'DUE IN')]/following-sibling::h6")
    public WebElement taskSilderDueIn;

    @FindBy(xpath = "//p[text()='CASE ID']/..//h6")
    public WebElement taskSilderCaseId;

    @FindBy(xpath = "//p[contains(text(),'CONSUMER ID')]/..//h6")
    public WebElement taskSilderConsumerID;

    @FindBy(xpath = "//p[contains(text(),'TASK TYPE')]/following-sibling::h6")
    public WebElement taskSilderTaskType;

    @FindBy(xpath = "//p[contains(text(),'PRIORITY')]/following-sibling::h6")
    public WebElement taskSilderPriority;

    //    @FindBy(xpath = "//p[contains(text(),'STATUS')]/following-sibling::h6")
    @FindBy(xpath = "(//p[contains(text(),'STATUS')]/following-sibling::h6)[2]")
    public WebElement taskSilderStatus;

    @FindBy(xpath = "//p[contains(text(),'TASK INFORMATION')]/following-sibling::h6")
    public WebElement taskSilderTaskInformation;

    @FindBy(xpath = "///span[contains(text(),'You can only Work 1 Task at a Time.')]")
    public WebElement initsecondTaskError;

    @FindBy(xpath = "//span[contains(text(),'You cannot have more than one task in progress at a time')]")
    public WebElement initsecondTaskErrorMsg;

    @FindBy(xpath = "//h6/i[contains(text(),'assignment')]")
    public WebElement taskSliderdetailView;

    @FindBy(name = "taskNotes")
    public WebElement txtTaskNotes;

    @FindBy(xpath = "//input[@name='taskStatus']/ancestor::div[1]")
    public WebElement lstTaskStatus;

    @FindBy(xpath = "//button[text()='Save']")
    public WebElement btnSaveTask;

    @FindBy(xpath = "//button[text()='Cancel']")
    public WebElement btnCancelTask;

    @FindBy(xpath = "//div[@class='modal-content']//button[1]")
    public WebElement btnContinueTaskWarning;

    @FindBy(xpath = "//div[@class='modal-content']//button[2]")
    public WebElement btnCancelTaskWarning;

    @FindBy(xpath = "//li[@class='active']/a")
    public WebElement activePageOnGrid;

    @FindBy(xpath = "//ul[@class='pagination']")
    public WebElement lnkPageNationParentElement;

    @FindBy(xpath = "//li/i[contains(text(),'assignment')]/following-sibling::strong")
    public WebElement dashboardTaskID;

    @FindBy(xpath = "//li[contains(text(),'Priority ')]/strong")
    public WebElement dashboardPriority;

    @FindBy(xpath = "//li[contains(text(),'Due In ')]/strong")
    public WebElement dashboardDueIn;

    @FindBy(xpath = "//p[text()='TASK NOTES']/following-sibling::p")
    public List<WebElement> taskNoteValues;

    @FindBy(xpath = "//p[text()='ASSIGNEE']/following-sibling::p")
    public List<WebElement> assigneeValues;

    @FindBy(xpath = "//a[text()='Task & Service Request']")
    public WebElement taskAndServiceRequestTab;

    @FindBy(xpath = "//h5[contains(text(),'TASK LIST')]")
    public WebElement taskListHeader;

    @FindBy(xpath = "//p[contains(text(),'Service')]")
    public WebElement userAccountName;

    @FindBy(xpath = "//div[@id='menu-taskStatus']//ul//li")
    public List<WebElement> taskStatusVlu;

    @FindBy(xpath = "//th[text()='STATUS']/../../following-sibling::tbody//td[5]//p")
    public List<WebElement> getStatusValuesForTSRTab;

    @FindBy(xpath = "//input[@id='reasonForEdit']")
    public WebElement reasonForEditDropDown;

    @FindBy(xpath = "//th[text()='STATUS']/../../following-sibling::tbody//td[5]//p")
    public List<WebElement> statusVluTaskTab;

    @FindBy(xpath = "//p[text()='TASK INFORMATION']/../h6")
    public WebElement lblTaskInfo;

    @FindBy(xpath = "//p[text()='TASK NOTES']/../h6")
    public WebElement lblTaskNote;

    @FindBy(xpath = "//p[text()='COMPLAINT ABOUT']/../h6")
    public WebElement cmplAbtValue;

    @FindBy(xpath = "//p[text()='COMPLAINT ABOUT']")
    public WebElement cmplAbtDrDn;

    @FindBy(xpath = "//p[text()='EXTERNAL APPLICATION ID']/../h6")
    public WebElement exAppIdValue;

    @FindBy(xpath = "//p[text()='APPLICATION ID']/../h6")
    public WebElement appIdValue;

    @FindBy(xpath = "//p[text()='EXTERNAL APPLICATION ID']")
    public WebElement exAppIdTxBx;

    @FindBy(xpath = "//p[text()='EXTERNAL CASE ID']/../h6")
    public WebElement exCaseIdValue;

    @FindBy(xpath = "//p[text()='EXTERNAL CASE ID']")
    public WebElement exCaseIdTxBx;

    @FindBy(xpath = "//p[text()='VACMS CASE ID']/../h6")
    public WebElement vacmsCaseIdValue;

    @FindBy(xpath = "//p[text()='MMIS MEMBER ID']/../h6")
    public WebElement mmisMemberIdValue;

    @FindBy(xpath = "//p[text()='NAME']/../h6")
    public WebElement nameValue;

    @FindBy(xpath = "//p[text()='NAME']")
    public WebElement nameTxBx;

    @FindBy(xpath = "//p[text()='REASON']/../h6")
    public WebElement reasonValue;

    @FindBy(xpath = "//p[text()='REASON']")
    public WebElement reasonDrDn;

    @FindBy(xpath = "//p[text()='COMPLAINT ABOUT']/../..//p")
    public List<WebElement> njViewAdditionalFields;

    @FindBy(id="mui-component-select-itemsPerPage")
    public WebElement geographicalInfo_default_ItemsPerPage;

    @FindBy(xpath = "//*[contains(text(),'TASK LIST')]/..//table/tbody")
    public List<WebElement> taskListTabletbodies;

    @FindBy(xpath = "//p[text()='CONTACT REASON']/../h6")
    public WebElement contactReasonVlu;

    @FindBy(xpath = "//p[text()='PREFERRED CALL BACK DATE']/../h6")
    public WebElement preferredCallBackDateVlu;

    @FindBy(xpath = "//p[text()='PREFERRED CALL BACK TIME']/../h6")
    public WebElement preferredCallBackTimeVlu;

    @FindBy(xpath = "//p[text()='PREFERRED PHONE']/../h6")
    public WebElement preferredPhoneVlu;

    @FindBy(xpath = "//p[text()='COMPLAINT ABOUT']/../h6")
    public WebElement lblComplaintAbout;

    @FindBy(xpath = "//p[text()='NAME']/../h6")
    public WebElement lblName;

    @FindBy(xpath = "//p[text()='REASON']/../h6")
    public WebElement lblReason;

    @FindBy(xpath = "//p[text()='DISPOSITION']/../h6")
    public WebElement dispositionVlu;

    @FindBy(xpath = "//p[text()='DISPOSITION']")
    public WebElement lblDisposition;

    @FindBy(xpath = "//div[@id='menu-taskStatus']//ul/li")
    public List<WebElement> statusDrDw;

    @FindBy(xpath = "//div[@id='menu-disposition']//ul/li")
    public List<WebElement> dispositionDrDw;

    @FindBy(xpath = "//p[text()='Max 300 characters']")
    public WebElement maxLengthError;

    @FindBy(xpath = "//i[text()='remove']")
    public WebElement remove;

    @FindBy(xpath = "//label[contains(text(),'STATUS')]/parent::div/div/div")
    public WebElement sliderStatus;

    @FindBy(xpath = "//*[@id=\"disposition\"]/parent::div")
    public WebElement taskSilderDisposition;

    @FindBy(xpath = "//th[text()='STATUS']/../../following-sibling::tbody//td[3]//p")
    public List<WebElement> getTaskTypesValues;

    @FindBy(xpath = "//span[text()='You cannot have more than one task in progress at a time']")
    public WebElement errorMessageForInitiate;

    @FindBy(xpath = "//ul/li[contains(text(), 'OnHold')]")
    public WebElement onHoldStatus;

    @FindBy(xpath = "//span[text()='Please complete Contact Record']")
    public WebElement contactRecordErMsg;

    @FindBy(xpath = "//span[text()='Please complete the task']")
    public WebElement taskErMsg;

    @FindBy(xpath = "//span[text()='This task is currently being worked by another user and cannot be edited']")
    public WebElement inProgressErrorMsg;

    @FindBy(xpath = "//span[text()='Task Successfully Updated']")
    public WebElement taskUpdatedTxt;

    @FindBy(xpath = "//span[text()='You must save your changes to the Task before saving on the Task Slider']")
    public WebElement taskErrorMsgYouMustSaveYourChangesBeforeSaving;

    @FindBy(xpath = "//p[text()='A link to a Case is required before this SR can be created.']")
    public WebElement caseIsRequiredForSRErrBeforeSaving;

    @FindBy(xpath = "//span[text()='Error saving task']")
    public WebElement errorSavingTaskMsg;

    @FindBy(xpath = "//button[text()='INTERACTION MANAGEMENT']//span")
    public List<WebElement> callManagement;

    @FindBy(xpath = "//p[contains(text(),'ACTION TAKEN')]/../h6")
    public WebElement txtActionTaken;

    @FindBy(xpath = "//p[contains(text(),'DOCUMENT TYPE')]/../h6")
    public WebElement txtDocType;

    @FindBy(xpath = "//p[contains(text(),'PROGRAM')]/../h6")
    public WebElement txtProgram;

    @FindBy(xpath = "//p[text()='CHANNEL']/../h6")
    public WebElement channelValue;

    @FindBy(xpath = "//li[@aria-selected='true']")
    public List<WebElement> multiSelectedVlus;

    @FindBy(xpath = "//label[text()='ACTION TAKEN']//span[text()='*']")
    public WebElement actionTakenMandatory;

    @FindBy(xpath = "//p[text()='REASON FOR CANCEL']/../h6")
    public WebElement reasonForCancel;

    @FindBy(xpath = "//button[@title='Clear']")
    public WebElement clearText;

    @FindBy(xpath = "(//p[text()='DUE DATE']/..//h6)[2]")
    public WebElement documentDueDate;

    @FindBy(xpath = "//p[text()='CONNECTIONPOINT CONTACT RECORD ID']/../h6")
    public WebElement cpCRId;

    @FindBy(xpath = "//p[text()='CONNECTIONPOINT SR ID']/../h6")
    public WebElement cpSRId;

    @FindBy(xpath = "//p[text()='CONNECTIONPOINT TASK ID']/../h6")
    public WebElement cpTaskId;

    @FindBy(xpath = "//p[text()='REASON FOR EDIT']/../h6")
    public WebElement reasonForEdit;

    @FindBy(xpath = "//p[text()='SR INFORMATION']/../h6")
    public WebElement lblSRInfo;

    @FindBy(xpath = "//p[text()='SR NOTES']/../h6")
    public WebElement lblSRNote;

    @FindBy(xpath = "//p[text()='APPLICATION TYPE']/../h6")
    public WebElement applicationType;

    @FindBy(xpath = "//p[text()='DECISION SOURCE']/../h6")
    public WebElement decisionSource;

    @FindBy(xpath = "//p[text()='SR CATEGORY']/../h6")
    public WebElement srCategory;

    @FindBy(xpath = "//p[text()='SR TYPE']/../h6")
    public WebElement srType;

    @FindBy(xpath = "//p[text()='DATE RESENT']/../h6")
    public WebElement dateResent;

    @FindBy(xpath = "//p[text()='RETURNED MAIL REASON']/../h6")
    public WebElement returnedMailReason;

    @FindBy(xpath = "//p[text()='RECEIVED DATE']/../h6")
    public WebElement receivedDate;

    @FindBy(xpath = "//p[text()='BUSINESS UNIT']/../h6")
    public WebElement businessUnit;

    @FindBy(id = "expedited")
    public WebElement expeditedCheckbox;

    @FindBy(xpath = "//p[text()='AR FIRST NAME']/../h6")
    public WebElement ARFirstNameValue;

    @FindBy(xpath = "//p[text()='AR LAST NAME']/../h6")
    public WebElement ARLastNameValue;

    @FindBy(xpath = "//p[text()='AR PHONE']/../h6")
    public WebElement ARPhoneValue;

    @FindBy(xpath = "//p[text()='AR EMAIL']/../h6")
    public WebElement AREmailValue;

    @FindBy(xpath = "//p[text()='ORGANIZATION']/../h6")
    public WebElement OrganizationValue;

    @FindBy(xpath = "//p[text()='APPLICATION ID']/../h6")
    public WebElement applicationID;

    @FindBy(xpath = "//p[text()='MY WORKSPACE DATE']/../h6")
    public WebElement myWorkspaceDate;

    @FindBy(xpath = "//p[text()='LDSS RECEIVED DATE']/../h6")
    public WebElement lDSSReceivedDate;

    @FindBy(xpath = "//p[text()='APPLICATION RECEIVED DATE']/../h6")
    public WebElement applicationReceivedDate;

    @FindBy(xpath = "//p[text()='APPLICATION SIGNATURE DATE']/../h6")
    public WebElement applicationSignatureDate;

    @FindBy(xpath = "//p[text()='APPLICATION UPDATE DATE']/../h6")
    public WebElement applicationUpdateDate;

    @FindBy(xpath = "//p[text()='FACILITY NAME']/../h6")
    public WebElement facilityName;

    @FindBy(xpath = "//p[text()='FACILITY TYPE']/../h6")
    public WebElement facilityType;

    @FindBy(xpath = "//input[@id='hpe']")
    public WebElement hpeCheckBox;

    @FindBy(xpath = "//input[@id='closedRenewal']")
    public WebElement closedRenewalCheckBox;

    @FindBy(xpath = "//p[text()='# OF APPLICANTS IN HOUSEHOLD']/../h6")
    public WebElement noOFApplicantsInHousehold;

    @FindBy(xpath = "//p[text()='MISSING INFO REQUIRED?']/../h6")
    public WebElement missingInfoRequired;

    @FindBy(xpath = "//p[text()='APPLICATION SUB-STATUS']/../h6")
    public WebElement applicationSubStatus;

    @FindBy(xpath = "//p[text()='# OF APPROVED APPLICANTS']/../h6")
    public WebElement noOFApprovedApplicants;

    @FindBy(xpath = "//p[text()='DENIAL REASON']/../h6")
    public WebElement denialReason;

    @FindBy(xpath = "//p[text()='RETURNED MAIL RECEIVED DATE']/following-sibling::h6")
    public WebElement returnedMailReceivedDate;

    @FindBy(xpath = "//p[text()='REASON FOR HOLD']/../h6")
    public WebElement reasonForHold;

    @FindBy(xpath = "//p[text()='PLAN CHANGE REASON']/../..//h6/..//p | //p[text()='PLAN CHANGE REASON']/../../..//label")
    public List<WebElement> planProviderViewFieldlbl;

    @FindBy(xpath = "//p[text()='CASE ID']/../h6")
    public WebElement lblCaseId;

    @FindBy(xpath = "//p[text()='Max 1000 characters']")
    public WebElement maxLengthError1;

    @FindBy(xpath = "//p[text()='LOCALITY']/../h6")
    public WebElement localityValue;

    @FindBy(xpath = "//p[text()='REQUEST TYPE']/../h6")
    public WebElement requestTypeValue;

    @FindBy(xpath = "//p[text()='ISSUE TYPE']/../h6")
    public WebElement issueTypeValue;

    @FindBy(xpath = "//p[text()='CASE WORKER FIRST NAME']/../h6")
    public WebElement cwFirstNameValue;

    @FindBy(xpath = "//p[text()='CASE WORKER LAST NAME']/../h6")
    public WebElement cwLastNameValue;

    @FindBy(xpath = "//p[text()='OUTCOME']/../h6")
    public WebElement outComeValue;

    @FindBy(xpath = "//p[text()='LANGUAGE']/../h6")
    public WebElement languageValue;

    @FindBy(xpath = "//p[text()='INFORMATION TYPE']/../h6")
    public WebElement informationTypeValue;

    @FindBy(xpath = "//p[text()='DATE TRANSLATION ESCALATED']/../h6")
    public WebElement dateTranslationEscalatedValue;

    @FindBy(xpath = "//p[text()='DATE TRANSLATION RECEIVED']/../h6")
    public WebElement dateTranslationReceivedValue;

    @FindBy(xpath = "//p[text()='DATE TRANSLATION MAILED']/../h6")
    public WebElement dateTranslationMailedValue;

    @FindBy(xpath = "//p[text()='AR ADDRESS LINE 1']/../h6")
    public WebElement arAddressLine1Value;

    @FindBy(xpath = "//p[text()='AR ADDRESS LINE 2']/../h6")
    public WebElement arAddressLine2Value;

    @FindBy(xpath = "//p[text()='AR CITY']/../h6")
    public WebElement arCityValue;

    @FindBy(xpath = "//p[text()='AR STATE']/../h6")
    public WebElement arStateValue;

    @FindBy(xpath = "//p[text()='AR ZIP CODE']/../h6")
    public WebElement arZipCodeValue;

    @FindBy(xpath = "//p[text()='REMAND REASON']/../h6")
    public WebElement remandReasonValue;

    @FindBy(xpath = "//p[text()='ELIGIBILITY DECISION']/../h6")
    public WebElement eligibilityDecisionValue;

    @FindBy(xpath = "//p[text()='REMAND COMPLETION DATE']/../h6")
    public WebElement remandCompletionDateValue;

    @FindBy(xpath = "//p[text()='REMAND DUE DATE']/../h6")
    public WebElement remandDueDateValue;

    @FindBy(xpath = "//p[text()='VCL DUE DATE']/../h6")
    public WebElement vclDueDateValue;

    @FindBy(xpath = "//p[text()='VCL SENT DATE']/../h6")
    public WebElement vclSentDateValue;

    @FindBy(xpath = "//p[text()='APPOINTMENT DATE']/../h6")
    public WebElement appointmentDateVlu;

    @FindBy(xpath = "//p[text()='APPOINTMENT TIME']/../h6")
    public WebElement appointmentTimeVlu;

    @FindBy(xpath = "//p[text()='CASE STATUS']/../h6")
    public WebElement caseStatusVlu;

    @FindBy(xpath = "//p[text()='PRE-HEARING OUTCOME']/../h6")
    public WebElement preHearingOutcomeVlu;

    @FindBy(xpath = "//p[text()='PRE-HEARING REASON']/../h6")
    public WebElement preHearingReasonVlu;

    @FindBy(xpath = "//h6[text()='TASK ID: ']//span")
    public WebElement taskIdOnTaskSlider;

    @FindBy(xpath = "//p[text()='COMPLAINT TYPE']/../h6")
    public WebElement complaintTypeValue;

    @FindBy(xpath = "//p[text()='DMAS RECEIVED DATE']/../h6")
    public WebElement dmasReceivedDate;

    @FindBy(xpath = "//p[text()='CoverVA RECEIVED DATE']/../h6")
    public WebElement coverVAReceivedDate;

    @FindBy(xpath = "//p[text()='APPEAL REASON']/../h6")
    public WebElement appealReason;

    @FindBy(xpath = "//p[text()='APPEALS CASE SUMMARY DUE DATE']/../h6")
    public WebElement appealCaseSummaryDueDate;

    @FindBy(xpath = "//p[text()='APPEALS CASE SUMMARY HYPERLINK']/../h6")
    public WebElement appealsCaseSummaryHyperlink;

    @FindBy(xpath = "//p[text()='APPEAL CASE SUMMARY STATUS']/../h6")
    public WebElement appealCaseSummaryStatus;

    @FindBy(xpath = "//p[text()='REVIEW OUTCOME']/../h6")
    public WebElement reviewOutcome;

    @FindBy(xpath = "//p[text()='PROVIDER FIRST NAME']/../h6")
    public WebElement providerFirstName;

    @FindBy(xpath = "//p[text()='PROVIDER LAST NAME']/../h6")
    public WebElement providerLastName;

    @FindBy(xpath = "//p[text()='PROVIDER PHONE']/../h6")
    public WebElement providerPhone;

    @FindBy(xpath = "//p[text()='PROVIDER EMAIL']/../h6")
    public WebElement providerEmail;

    @FindBy(id = "requestedNewPlan")
    public WebElement requestedNewPlanCheckBox;

    @FindBy(id = "invalid")
    public WebElement invalidCheckBox;

    @FindBy(xpath = "//span[contains(text(),'CREATE LINK(S)')]")
    public WebElement createLinkBtn;

    @FindBy(xpath = "//span[contains(text(),'TASK/SERVICE REQUEST')]")
    public WebElement taskOrSRLinkBtn;

    @FindBy(xpath = "//span[text()='REFRESH']")
    public WebElement refreshBtn;

    @FindBy(xpath = "//p[text()='OTHER']/../h6")
    public WebElement other;

    @FindBy(xpath = "//p[text()='DECISION']/../h6")
    public WebElement decisionValue;

    @FindBy(xpath = "//input[@id='externalCaseId']")
    public WebElement viewExtCaseId;

    @FindBy(xpath = "//*[text()='GetInsured CASE ID']")
    public WebElement getInsuredCaseIdColumn;

    @FindBy(xpath = "//*[text()='GetInsured CONSUMER ID']")
    public WebElement getInsuredConsumerIdColumn;

    @FindBy(xpath = "//p[text()='GetInsured CASE ID']/../h6")
    public WebElement getInsuredCaseIdValueTaskSlider;

    @FindBy(xpath = "//p[text()='GetInsured CONSUMER ID']/../h6")
    public WebElement getInsuredConsumerIdValueTaskSlider;

    @FindBy(xpath = "//p[text()='MEMBER NAME']/../h6")
    public WebElement memberName;

    @FindBy(xpath = "//p[text()='CONTACT NAME']/../h6")
    public WebElement contactName;

    @FindBy(xpath = "//p[text()='INCIDENT DATE']/../h6")
    public  WebElement incidentDateVlu;

    @FindBy(xpath = "//p[text()='CSR NAME']/../h6")
    public WebElement csrNameVlu;

    @FindBy(id = "escalated")
    public WebElement escalatedVlu;

    @FindBy(xpath = "//p[text()='ESCALATION REASON']/../h6")
    public WebElement escalationReasonVlu;

    @FindBy(xpath = "//p[text()='OTHER ENTITY']/../h6")
    public WebElement otherEntityVlu;

    @FindBy(xpath = "//p[text()='INVALID REASON']/../h6")
    public WebElement invalidReason;

    @FindBy(xpath = "//p[text()='INBOUND CORRESPONDENCE TYPE']/../h6")
    public WebElement inboundCorrespondenceTypeVlu;

    @FindBy(xpath = "//span[text()='You cannot link a task that has been created by a Service Request']")
    public WebElement errorMessageForTask;

    @FindBy(xpath = "//p[text()='CURRENT PLAN']/../h6")
    public WebElement currentPlan;

    @FindBy(xpath = "//p[text()='DISENROLLMENT DATE']/../h6")
    public WebElement disenrollmentDate;

    @FindBy(xpath = "//p[text()='Medicaid ID/RID']/../h6")
    public WebElement medicaidIDRidVlu;

    @FindBy(xpath = "(//p[text()='SOURCE']/../h6)[2]")
    public WebElement appSource;

    @FindBy(xpath = "//p[text()='DISENROLLMENT REASON']/../h6")
    public WebElement disenrollmentReason;

    @FindBy(xpath = "//p[text()='PHONE']/../h6")
    public WebElement phoneVlu;

    @FindBy(xpath = "//span[text()='add']//ancestor::button")
    public WebElement addReasonButton;

    @FindBy(xpath = "//i[text()='close']")
    public List<WebElement> listRemoveIcon;

    @FindBy(xpath = "//p[text()='SENT NOA DATE']/../h6")
    public  WebElement sentNoaDateVlu;

    @FindBy(xpath = "//h5[text()='TASK DETAILS']/..//input[@id='actionTaken']/..")
    public WebElement editActionTakenDD;

    @FindBy(xpath = "//p[text()='REQUESTED DATE']/../h6")
    public WebElement requestedDate;

    @FindBy(xpath = "//p[text()='CHANGE REASON']/../h6")
    public WebElement planChangeReason;

    @FindBy(xpath = "//p[text()='DESIRED PLAN']/../h6")
    public WebElement desiredPlan;

    @FindBy(xpath = "//p[text()='PLAN START DATE']/../h6")
    public WebElement planStartDate;

    @FindBy(xpath = "//p[text()='PLAN NAME']/../h6")
    public WebElement planName;

    @FindBy(xpath = "//p[text()='USED TOBACCO IN THE LAST 6 MONTHS?']/../h6")
    public WebElement usedTobaccoInTheLastSixMonths;

    @FindBy(xpath = "//p[text()='DATE OF VOICEMAIL']/../h6")
    public WebElement dateOfVoicemail;

    @FindBy(xpath = "//p[text()='TIME OF VOICEMAIL']/../h6")
    public WebElement timeOfVoicemail;

    @FindBy(xpath = "//p[text()='CONTACT PHONE']/../h6")
    public WebElement contactPhone;

    @FindBy(xpath = "//input[@id='actionTakenSingle']/..")
    public WebElement editActionTakenSingleDD;

    @FindBy(xpath = "//p[text()='ADDRESS LINE 1']/../h6")
    public WebElement AddressLine1Value;

    @FindBy(xpath = "//p[text()='ADDRESS LINE 2']/../h6")
    public WebElement AddressLine2Value;

    @FindBy(xpath = "//p[text()='CITY']/../h6")
    public WebElement CityValue;

    @FindBy(xpath = "//p[text()='STATE']/../h6")
    public WebElement StateValue;

    @FindBy(xpath = "//p[text()='ZIP CODE']/../h6")
    public WebElement zipCode;

    @FindBy(xpath = "//p[text()='ADDRESS TYPE']/../h6")
    public WebElement addressType;

    @FindBy(xpath = "//p[text()='ADDRESS SOURCE']/../h6")
    public WebElement addressSource;

    @FindBy(xpath = "//span[contains(text(),'edit')]")
    public WebElement editButtonToTaskOrSr;

    @FindBy(xpath = "//th[text()='DUE DATE']/../../following-sibling::tbody//td[7]//p")
    public List<WebElement> DueDatesmyTasks;

    @FindBy(xpath = "//th[text()='DUE DATE']/../../following-sibling::tbody//td[8]//p")
    public List<WebElement> dueInMyTasks;

    @FindBy(xpath = "//th[text()='DUE DATE']/../../following-sibling::tbody//td[5]//p")
    public List<WebElement> statusDateMyTasks;

    @FindBy(xpath = "//span[text()='Service Request Successfully Updated']")
    public WebElement srUpdatedTxt;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/..//table/thead/tr//th[7]")
    public WebElement statusDateSearchPage;

    @FindBy(xpath = "//p[text()='TRANSFER REASON']/../h6")
    public  WebElement transferReason;

//pagination DD on My task/Work queue pages
    @FindBy(id = "mui-component-select-gridComponentStaticforTM")
    public WebElement paginationDD;

    @FindBy(xpath = "//p[text()='ASSIGNEE BUSINESS UNIT']/following-sibling::h6")
    public WebElement lblBusinessUnitAssigneeTo;

    @FindBy(xpath = "//p[text()='DATE STATE NOTIFIED']/../h6")
    public WebElement dateStateNotified;

    @FindBy(xpath ="//span[text()='Close']")
    public WebElement closeErrorPopup;

    @FindBy(xpath = "//p[text()='MISSING INFO REQUIRED']/../h6")
    public WebElement missingInfoRequiredTxt;

    @FindBy(xpath = "//p[text()='RENEWAL PROCESSING DUE DATE']/following-sibling::h6")
    public WebElement renewalProcessingDueDateValue;

    @FindBy(xpath = "//p[text()='RENEWAL DATE']/following-sibling::h6")
    public WebElement renewalDateValue;

    @FindBy(xpath = "//p[text()='RENEWAL CUTOFF DATE']/following-sibling::h6")
    public WebElement renewalCutoffDateValue;

    @FindBy(xpath = "//p[text()='MI RECEIVED DATE']/following-sibling::h6")
    public WebElement miReceivedDateValue;

    @FindBy(xpath = "//p[text()='DISTRICT DISPOSITION']/following-sibling::h6")
    public WebElement districtDisposition;

    @FindBy(xpath = "//p[text()='CURRENT ESCALATION TYPE']/../h6")
    public WebElement currentEscalationType;
}
