package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMTaskSearchPage {

    

    public CRMTaskSearchPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//input[@id='taskStatus']/..")
    public WebElement taskStatusDropdown;

    @FindBy(xpath = "//input[@id='taskTypeId']/..")
    public WebElement taskTypeDropdown;

    @FindBy(xpath = "//input[@id='statusDate']")
    public WebElement statusDate;

    @FindBy(xpath = "//input[@id='taskId']")
    public WebElement taskId;

    @FindBy(xpath = "//input[@id='defaultPriority']/..")
    public WebElement priorityDropdown;

    @FindBy(xpath = "//input[@id='defaultDueDate']")
    public WebElement dueDate;

    @FindBy(xpath = "//input[@id='searchCase']/..")
    public WebElement searchCaseDropdown;

    @FindBy(xpath = "//input[@id='caseId']")
    public WebElement caseId;

    @FindBy(xpath = "//input[@id='searchConsumer']/..")
    public WebElement searchConsumerDropdown;

    @FindBy(xpath = "//input[@id='consumerId']")
    public WebElement consumerId;

    @FindBy(xpath = "(//span[text()='search']/..)[2]")
    public WebElement searchBtn;

    @FindBy(xpath = "//span[text()='Cancel']")
    public WebElement cancelBtn;

    @FindBy(xpath = "//h5[text()='SAVE TASK SEARCH']")
    public WebElement saveTaskSearchLabel;

    @FindBy(xpath = "//input[@id='searchName']")
    public WebElement searchName;

    @FindBy(xpath = "//span[contains(text(),'SAVE')]")
    public WebElement saveBtn;

    @FindBy(xpath = "//span[contains(text(),'CANCEL')]")
    public WebElement saveTaskSearchCancelBtn;

    @FindBy(xpath = "//span[text()=' WARNING MESSAGE']")
    public WebElement warningMsg;

    @FindBy(xpath = "//p[text()='Task Search will not be saved']")
    public WebElement warningMsgTxt;

    @FindBy(xpath = "//span[text()='Continue']")
    public WebElement warningMsgContinueBtn;

    @FindBy(xpath = "//span[text()='clear']")
    public WebElement warningMsgCancelBtn;

    @FindBy(xpath = "//span[text()='SUCCESS MESSAGE']")
    public WebElement successMsg;

    @FindBy(xpath = "//span[text()='Saved Task Search successfully created']")
    public WebElement successMsgTxt;

    @FindBy(xpath = "//span[text()=' Advanced Search']")
    public WebElement advanceSearch;

    @FindBy(id = "consumerFirstName")
    public WebElement consumerFirstName;

    @FindBy(id = "consumerLastName")
    public WebElement consumerLastName;

    @FindBy(xpath = "//input[@id='source']/..")
    public WebElement sourceDropdown;

    //refactorBy:vidya Date:03-08-2020
    @FindBy(xpath = "//label[text()='ASSIGNEE']/..//input")
    public WebElement assignee;

    //refactorBy:vidya Date:03-08-2020
    @FindBy(xpath = "//label[text()='CREATED BY']/..//input")
    public WebElement createdBy;

    //refactorBy:vidya Date:03-08-2020
    @FindBy(xpath = "//label[text()='CREATED ON']/..//input")
    public WebElement createdOn;

    @FindBy(xpath = "//*[text()='person']")
    public WebElement contactRecorcSideBarIcon;

    @FindBy(xpath = "//a[@class='mx-left-drawer-menu active']")
    public WebElement contactRecordTab;

    @FindBy(id = "consumerId")
    public WebElement taskConsumerId;

    @FindBy(id = "client-snackbar")
    public WebElement badRequestMsg;

    @FindBy(id = "mui-component-select-taskSearchName")
    public WebElement savedSearchArrow;

    @FindBy(xpath = "//*[text()='SrchbPtkea']")
    public WebElement randonSavedSearch;

    @FindBy(xpath = "//input[@id='taskSearchName']/..")
    public WebElement taskSearchNameDropDown;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/..//table/tbody/tr//td[3]")
    public List<WebElement> taskIDs;

    @FindBy(xpath = "//h5[text()='TASK/SR SEARCH']")
    public WebElement taskSearchHeader;

    @FindBy(xpath = "//h5[contains(text(),'TASK/SR SEARCH')]")
    public WebElement taskSearchResultHeader;

    @FindBy(id="escalated")
    public WebElement escalated;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/..//table/tbody/tr//td[6]")
    public List<WebElement> taskStatuses;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT(S)')]/..//table/tbody/tr//td//p/../..")
    public List<WebElement> taskListRows;

    @FindBy(xpath = "//th[contains(text(),'ID')]")
    public WebElement taskIDColumnHeader;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/..//table/tbody/tr//td[4]//../p")
    public List<WebElement> taskTypes;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/..//table/tbody/tr//td[5]")
    public List<WebElement> taskPriority;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/..//table/tbody/tr//td[7]")
    public List<WebElement> taskStatusDate;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/..//table/tbody/tr//td[8]")
    public List<WebElement> taskDueDateDate;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/..//table/tbody/tr//td[9]")
    public List<WebElement> taskDueIns;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/..//table/tbody/tr//td[10]")
    public List<WebElement> taskCaseID;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/..//table/tbody/tr//td[11]")
    public List<WebElement> taskConsumerID;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/..//table/tbody/tr//td[12]")
    public List<WebElement> initiateTasks;

    @FindBy(xpath = "//span[text()='arrow_forward']/parent::a")
    public WebElement lnkArrowForward;

    @FindBy(xpath = "//ul[@class='pagination']/li")
    public List<WebElement> lnkPageNations;

    @FindBy(xpath = "//span[text()='arrow_back']/parent::a")
    public WebElement lnkArrowBack;

    @FindBy(xpath = "//*[text()='Edit Task']/..")
    public WebElement editTaskBtn;

    @FindBy(xpath = "//*[contains(text(),'chevron_right')]")
    public List <WebElement> expandBtns;

    @FindBy(xpath = "//p[contains(text(),'ASSIGNEE')]/following-sibling::h6")
    public List<WebElement> assigneeExpandedView;

    @FindBy(xpath = "//div[@id='menu-taskTypeId']//ul/li")
    public List<WebElement> taskTypeDDVlu;

    @FindBy(xpath = "(//span[@id='client-snackbar'])[2]")
    public WebElement errorMsgTxt;

    @FindBy(xpath = "//i[contains(text(),'keyboard_arrow_down')]")
    public WebElement arrowDownKey;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/..//table/tbody/tr//td[13]")
    public List<WebElement> initiateTasksForNJ;

    @FindBy(xpath = "//input[@id='dateOfContact']")
    public WebElement dateOfContact;

    @FindBy(xpath = "//input[@id='dateOfContact']/../..//label")
    public WebElement dateOfContactLabel;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/..//table//th")
    public List<WebElement> columnNames;

    @FindBy(name = "applicationId")
    public WebElement applicationId;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/..//table/tbody/tr//td[2]//button")
    public List<WebElement> expandSearchResultArrows;

    @FindBy(xpath = "//p[text()='CREATED BY']/following-sibling::p")
    public WebElement createdByVluSearchResult;

    @FindBy(xpath = "//p[text()='SOURCE']/following-sibling::h6")
    public WebElement lblSourceSearchResult;

    @FindBy(xpath = "//p[text()='CREATED ON']/following-sibling::h6")
    public WebElement lblCreatedOnSearchResult;

    @FindBy(xpath = "//p[text()='ASSIGNEE']/following-sibling::h6")
    public WebElement lblAssigneeSearchResult;

    @FindBy(xpath = "//p[text()='TASK NOTES']/following-sibling::h6")
    public WebElement lblTaskNoteSearchResult;

    @FindBy(xpath = "//p[text()='NAME']/following-sibling::h6")
    public WebElement lblNameSearchResult;

    @FindBy(xpath = "//p[text()='TASK INFORMATION']/following-sibling::h6")
    public WebElement lblTaskInfoSearchresult;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/..//table/tbody/tr//td[6]//p")
    public List<WebElement> consumerNameSearchResult;

    @FindBy(xpath = "//span[text()='link']")
    public List<WebElement> linksBtn;

    @FindBy(xpath = "//table//span[text()='ESCALATED']/..//input")
    public List<WebElement> escalatedCheckBox;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/..//table/tbody/tr//td[11]")
    public List<WebElement> taskGetInsuredCaseID;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/..//table/tbody/tr//td[12]")
    public List<WebElement> taskGetInsuredConsumerID;

    @FindBy(xpath = "//th[text()='DUE DATE']/../../following-sibling::tbody//td[9]//p")
    public List<WebElement> dueDateValuesForNj;

    @FindBy(id = "mui-component-select-dueDateSearch")
    public WebElement comparisonSignButton;

    @FindBy(xpath = "//li[@class='active']//a")
    public WebElement currentlyOnPaginationButton;

    @FindBy(xpath = "//div//input[@aria-label='Go To Page']")
    public WebElement goToPageSection;

    @FindBy(xpath = "//div//i[text()='send']")
    public WebElement sendIconForGoToPage;

    @FindBy(xpath = "//li//a[@class='undefined']")
    public WebElement selectedPagefromGoToPage;

    @FindBy(xpath = "//div/p[text()='The page you selected does not exist. Please enter a valid page']")
    public WebElement errorMessageForInvalidGoToPageNumber;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/..//table/tbody/tr//td[4]//p")
    public List<WebElement> typeName;

    @FindBy(name = "expedited")
    public WebElement expedited;

    @FindBy(xpath = "//table//span[text()='EXPEDITED']/..//input")
    public List<WebElement> expeditedCheckBox;

    @FindBy(id = "mui-component-select-itemsPerPage")
    public WebElement pageNationDD;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/..//table/tbody/tr//td[1]//input")
    public List<WebElement> checkbox;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/..//table/thead/tr//th/..//input/..")
    public WebElement mainCheckbox;

    @FindBy(id = "mui-component-select-assignmentStatus")
    public WebElement assignmentStatusDD;

    @FindBy(xpath = "//p[text()='Search results in excess. The first 500 are returned below. Enter additional search criteria to limit search results']")
    public WebElement errorMsg;

    @FindBy(xpath = "//label[text()='SEARCH CASE']/..//div[@id='mui-component-select-searchCase']")
    public WebElement searchCase;

    @FindBy(xpath = "//label[text()='SEARCH CONSUMER']/..//div[@id='mui-component-select-searchConsumer']")
    public WebElement searchConsumer;

    @FindBy(xpath="//th[text()='DATE OF CONTACT']")
    public WebElement dateOfContactColumn;

    @FindBy(xpath = "//th[text()='DUE DATE']/../../following-sibling::tbody//td[8]//p")
    public List<WebElement> dueDateValues;

    @FindBy(xpath = "//th[text()='DUE DATE']/../../following-sibling::tbody//td[10]//p")
    public List<WebElement> dueInValues;

    @FindBy(xpath = "//label[text()='ASSIGNEE TEAM']/..//input")
    public WebElement assigneeTeamLabel;

    @FindBy(xpath = "//label[text()='ASSIGNEE BUSINESS UNIT']/..//input")
    public WebElement assigneeBusinessUnitLabel;

    @FindBy(xpath = "//*[text()='APPLICATION ID']")
    public WebElement applicationID;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/..//table/tbody/tr//td[7]")
    public List<WebElement> taskStatusforNJ;

    @FindBy(xpath = "//input[@id='srTypeId']/..")
    public WebElement srTypeDropDown;

    @FindBy(xpath = "//*[contains(text(),'TASK/SR SEARCH')]")
    public WebElement taskSearchPageHearder;

    @FindBy(id = "mui-component-select-srTypeId")
    public WebElement srTypes;

    @FindBy(xpath = "//div[@id='menu-srTypeId']//ul/li")
    public List<WebElement> srTypeListValues;

    @FindBy(xpath = "//span[@title='Task/SR Icon']//span")
    public List<WebElement> listIconsOnTaskSearchResult;

    @FindBy(xpath = "//input[@id='assigneeBusinessUnit']/..")
    public WebElement assigneeBusinessUnit;

    @FindBy(xpath = "//input[@id='assigneeTeam']/..")
    public WebElement assigneeTeam;

    @FindBy(xpath = "//h5[contains(text(), 'SEARCH RESULT')]/..//table/tbody/tr//td[2]")
    public List<WebElement> taskIDsOnSearchPage;

    @FindBy(xpath = "//h5[contains(text(), 'SEARCH RESULT')]")
    public WebElement searchResultsHeader;

    @FindBy(id = "mui-component-select-contactReason")
    public WebElement contactReason;

    @FindBy(xpath = "//tbody/tr/td[12]")
    public List<WebElement> contactReasonResults;

    @FindBy(xpath = "//td[contains(@class,'non-numeric mx-link')][1]/..")
    public List<WebElement> taskResultsForHighlight;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT(S)')]/..//table/tbody/tr//td[11]")
    public List<WebElement> taskConsumerIdCoverVA;
}
