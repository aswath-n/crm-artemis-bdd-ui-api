package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TMTaskTypePage {

    

    public TMTaskTypePage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath="//*[contains(text(), 'CREATE TASK TYPE')]")
    public WebElement lblCreateTaskTypeHeader;

    @FindBy(name="taskType")
    public WebElement txtTaskType;

    @FindBy(xpath = "//input[@name='taskTypeId']")
    public WebElement taskTypeIdInputBox;

    @FindBy(xpath = "//p[text()='TASK TYPE ID']//following-sibling::h6")
    public WebElement taskTypeIdInView;

    @FindBy(xpath="(//div/h6)[3]")
    public WebElement readTaskType;

    @FindBy(xpath = "//input[@name='taskPriority']/ancestor::div[1]")
    public WebElement lstTaskPriority;

    @FindBy(xpath = "//div[@id='menu-taskPriority']//ul/li")
    public List<WebElement> taskPriorityValues;

    //@FindBy(name="dueInDate")--Not working
    @FindBy(xpath ="//input[@name='dueInDate']")
    public WebElement txtDueInDays;

    //@FindBy(name="dueInDate")--Not working
    @FindBy(xpath ="(//div/h6)[5]")
    public WebElement readDueInDays;

    @FindBy(xpath = "//input[@name='typeOfDays']/ancestor::div[1]")
    public WebElement lstTypeOfDays;

    @FindBy(xpath = "//input[@name='typeOfDays']/..")
    public WebElement txtTypeOfDays;

    //@FindBy(name="taskDescription")--Not working
    @FindBy(xpath = "//input[@name='taskDescription']")
    public WebElement txtTaskDescription;

    //@FindBy(xpath = "//p[@class='mb-0 mx-color-text-primary mdl-color-text--grey-400']")
    @FindBy(xpath = "//p[text()='DESCRIPTION']/following-sibling::p")
    public WebElement readTaskDescription;

    @FindBy(xpath = "//label[@for='case-link']")
    public WebElement caseRadioBtnInViewMode;

    @FindBy(xpath = "//label[@for='consumer-link']")
    public WebElement consumerRadioBtnInViewMode;

    @FindBy(xpath = "//label[@for='case-consumer-link']")
    public WebElement caseNconsumerRadioBtnInViewMode;

    @FindBy(xpath = "//label[@for='corresspondence-link']")
    public WebElement correspandanceChkBxInViewMode;

    @FindBy(xpath = "//h5[text()=' REQUIRED LINKS']")
    public WebElement requiredLinksHeader;

    @FindBy(xpath = "//span[text()='keyboard_backspace']/ancestor::button")
    public WebElement btnBack;

    @FindBy(xpath = "//span[text()='Continue']/ancestor::button")
    public WebElement btnContinueWarningWindow;

    @FindBy(xpath = "//span[text()='Cancel']/ancestor::button")
    public WebElement btnCancelWarningWindow;

    @FindBy(xpath = "//span[contains(text(), 'add')]/ancestor::button")
    public WebElement btnAddTaskType;

    @FindBy(xpath = "//h5[contains(text(), 'CREATE TASK TYPE')]")
    public WebElement lblCreateTaskType;

    @FindBy(xpath = "//h5[contains(text(), 'TASK TYPE')]")
    public WebElement lblTaskType;

    @FindBy(xpath = "//span[contains(text(), 'Save')]/parent::button")
    public WebElement btnSaveTaskType;

    @FindBy(xpath = "//span[contains(text(), 'Cancel')]/parent::button")
    public WebElement btnCancelTaskType;

    @FindBy(xpath = "//input[@name='taskType']/../following-sibling::p")
    public WebElement lblTaskTypeFieldMessage;

    @FindBy(xpath = "//input[@name='taskPriority']/../following-sibling::p")
    public WebElement lblTaskPriorityFieldMessage;

    //input[@name='taskPriority']--Not working
    @FindBy(xpath ="/html[1]/body[1]/div[1]/div[1]/main[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[5]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]")
    public WebElement priortyVal;

    @FindBy(xpath ="(//div/h6)[4]")
    public WebElement readPriorityVal;

    @FindBy(xpath = "//input[@name='dueInDate']/../following-sibling::p")
    public WebElement lblDueInDateFieldMessage;

    @FindBy(xpath = "//label[text()='Description']")
    public WebElement lblDescription;

       @FindBy(xpath = "//*[text()='SUCCESS MESSAGE']")
    public WebElement successSnackbar;

    @FindBy(xpath = "//*[text()='Task Type successfully created']")
    public WebElement taskTypeCreatedMessage;

    @FindBy(xpath = "//p[text()='Select Permission Group(s)']/parent::div")
    public WebElement lstPermisionGroup;

    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO WORK']/ancestor::div[@class='row']/div[2]/div/div/div")
    public WebElement lstPermisionGroupToWork;


    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO WORK']/ancestor::div[@class='row']/div[2]/div/div/div/..")
    public WebElement secondPermisionGroupToWork;

    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO WORK - ESCALATED']/ancestor::div[@class='row']/div[2]/div/div/div")
    public WebElement lstPermisionGroupToWorkEscalated;

    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO CREATE']/ancestor::div[@class='row']/div[2]/div/div/div")
    public WebElement lstPermisionGroupToCreate;

    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO EDIT']/ancestor::div[@class='row']/div[2]/div/div/div")
    public WebElement lstPermisionGroupToEdit;

    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO WORK']/ancestor::div[@class='row']/div[2]//div[@tabindex='-1']//span")
    public List<WebElement> permisionGroupToWorkSeletedValues;

    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO WORK - ESCALATED']/ancestor::div[@class='row']/div[2]//div[@tabindex='-1']//span")
    public List<WebElement> permisionGroupToWorkEscalatedSeletedValues;

    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO CREATE']/ancestor::div[@class='row']/div[2]//div[@tabindex='-1']//span")
    public List<WebElement> permisionGroupToWorkCreatedSeletedValues;

    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO EDIT']/ancestor::div[@class='row']/div[2]//div[@tabindex='-1']//span")
    public List<WebElement> permisionGroupToWorkEditSeletedValues;

    @FindBy(xpath = "//div[contains(@id, 'tasktype')]//table/tbody/tr")
    public List<WebElement> tasktypeRecords;

    @FindBy(xpath = "//h5[text()='ASSOCIATE SCREEN']/ancestor::div[@class='row']/div[2]//input")
    public WebElement lstAssociateScreen;

    @FindBy(xpath = "//*[@id='_undefined']")
    public WebElement lstAssociateScreenDD;

    @FindBy(xpath = "//ul[@role='listbox']//li")
    public WebElement lstAutoList;

    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO WORK']/ancestor::div[@class='row']/div[2]//input")
    public WebElement txtPermisionGroupToWork;

    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO WORK - ESCALATED']/ancestor::div[@class='row']/div[2]//input")
    public WebElement txtPermisionGroupToWorkEscalated;

    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO CREATE']/ancestor::div[@class='row']/div[2]//input")
    public WebElement txtPermisionGroupToCreate;

    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO EDIT']/ancestor::div[@class='row']/div[2]//input")
    public WebElement txtPermisionGroupToEdit;

    /* Paramita To automate : CP-2002*/

    @FindBy(xpath = "//div[contains(@id, 'tasktype')]//table/tbody/tr/td[2]/a")
    public List<WebElement> tasktypeName;

    @FindBy(xpath = "//div[contains(@id, 'tasktype')]//table/tbody/tr/td[6]")
    public List<WebElement> tasktypeStatus;

    @FindBy(xpath = "//div[contains(@id, 'tasktype')]//table/tbody")
    public List<WebElement> tasktypeRecordcount;

    @FindBy(xpath = "//h5[contains(text(),'TASK TYPE DETAILS')]/../..//div/p[1]")
    public List<WebElement> taskHeader;

    @FindBy(xpath = "//h5[contains(text(),'TASK TYPE DETAILS')]/../..//div//h6")
    public List<WebElement> tasktypefieldsVal;

    @FindBy(xpath = " //p[contains(text(),'SELECT PERMISSION GROUP(S)')]/..//span")
    public List<WebElement> groupPermssionFieldValue;

    @FindBy(xpath = "//h5[text()='ACTIVE']")
    public WebElement taskTypeStatus;

    @FindBy(xpath = "//p[text()='SELECT SCREEN NAME']/..//p[2]")
    public WebElement screenNameValue;

    @FindBy(xpath = "//p[text()='DESCRIPTION']/..//p[2]")
    public WebElement descriptionValue;

    @FindBy(xpath = "//button[@type='button']/..//i")
    public List<WebElement> taskTypeSAVECancelButton;


    @FindBy(xpath = "//span[contains(text(),'edit')]")
    public WebElement taskTypeEDITButton;

    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO WORK - ESCALATED']")
    public WebElement taskTypelastComponent;

    @FindBy(xpath = "//h5[text()='ASSOCIATE TEMPLATE']")
    public WebElement assocaiteTemplate;

    @FindBy(xpath = "//span[text()='add']")
    public WebElement templateAddBtn1;

    @FindBy(xpath = "//div[contains(@class,'mx-bracket')]")
    public WebElement associatedTemplatecolor;

    /*  CP-1075 */

    @FindBy(xpath = "//span[contains(text(),'ADD TEMPLATE')]")
    public WebElement templateButton;

    @FindBy(xpath = "//h5[contains(text(),'No Records Added')]")
    public WebElement noRecordText;

    @FindBy(xpath = "//div[contains(@class,'row py-0 mx-0')]")
    public List<WebElement> blankRecordCount;

    @FindBy(xpath = "//h5[text()='ASSOCIATE TEMPLATE']")
    public WebElement assocTemplateHeader;

    @FindBy(xpath = "//input[@name='selectedTemplate'][@value='']/..")
    public WebElement templateDropdown;

    @FindBy(xpath = "//input[@name='selectedTemplate']/../div")
    public WebElement templateDropdownValue;

    @FindBy(xpath = "//input[@name='versionId']/..")
    public WebElement versionId;

    @FindBy(xpath = "//input[@name='versionId']")
    public WebElement versionIdValue;

    @FindBy(xpath = "//label[text()='Start Date']/../div/input[@value='']")
    public WebElement templateStartDate;

    @FindBy(xpath = "//label[text()='End Date']/../div/input")
    public WebElement templateEndDate;

    @FindBy(xpath = "//span[text()='add']/..")
    public WebElement templateAddBtn;

    @FindBy(xpath = "//span[text()='refresh']/..")
    public WebElement templateRefreshBtn;

    @FindBy(xpath = "//ul[@role='listbox']/li")
    public List<WebElement> templateValues;

    @FindBy(xpath = "//span[text()='delete']/..")
    public List<WebElement> templateDelete;

    @FindBy(xpath = "//p[text()='The Start date is required and cannot be left blank.']")
    public WebElement startDateMandatoryError;

    @FindBy(xpath = "//p[text()='Select Template is required and cannot be left blank.']")
    public WebElement templateMandatoryError;

    @FindBy(xpath = "//p[text()='The versionId is required and cannot be left blank.']")
    public WebElement versionMandatoryError;

    @FindBy(xpath = "//*[contains(text(), 'Start Date cannot be in the past')]")
    public WebElement priorStartDateError;

    @FindBy(xpath = "//*[contains(text(), 'End Date cannot be in the past')]")
    public WebElement priorEndDateError;

    @FindBy(xpath = "(//h6)[1]")
    public WebElement createdBy;

    @FindBy(xpath = "(//h6)[2]")
    public WebElement createdDate;

    @FindBy(xpath = "(//h5)[3]")
    public WebElement statusTaskType;

    @FindBy(xpath = "//input[@name='selectedTemplate']/parent::div/div")
    public WebElement selectTemplate;

    @FindBy(xpath = "//input[@name='versionId']/parent::div/div")
    public WebElement selectVersion;

    @FindBy(xpath = "(//label[contains(text(),'Start Date')]/following-sibling::div/input)[1]")
    public WebElement startDate;

    @FindBy(xpath = "(//label[contains(text(),'End Date')]/following-sibling::div/input)[1]")
    public WebElement endDate;

    @FindBy(xpath = "(//label[contains(text(),'Start Date')]/following-sibling::div/input)[2]")
    public WebElement startDate2;

    @FindBy(xpath = "(//label[contains(text(),'End Date')]/following-sibling::div/input)[2]")
    public WebElement endDate2;

    @FindBy(xpath = "(//label[contains(text(),'Start Date')]/following-sibling::div/input)[3]")
    public WebElement startDate3;

    @FindBy(xpath = "(//label[contains(text(),'End Date')]/following-sibling::div/input)[3]")
    public WebElement endDate3;

    //@FindBy(xpath = "//span[contains(text(),'add')]")
    @FindBy(xpath = "//*[contains(text(),'ASSOCIATE TEMPLATE')]/../button")
    public WebElement addBtnAsscoiateTemplate;

    @FindAll({
            @FindBy(xpath = "(//th[contains(text(),'TASK TYPE')])[1]/parent::tr/parent::thead/parent::table/tbody/tr")
    })
    public List<WebElement> listOfTaskType;

    @FindBy(xpath = "//input[@name='selectedTemplate']")
    public WebElement selectTemplateValue;

//added by shruti , commenting due to merge issue
    //@FindBy(xpath = "(//div[contains(@class,'indicatorContainer')])[1]")
    //@FindBy(xpath = "//*[text()='ASSOCIATE PERMISSION GROUP TO WORK']/ancestor::div[@class='row']//button[2]")
    //public WebElement permToWorkArrow;


    @FindBy(xpath = "//span[text()='SERVICE REQUEST']/..//input")
    public WebElement serviceRequestCheckBox;

    @FindBy(xpath = "//div[@id='menu-selectedTemplate']//ul/li")
    public List<WebElement> listOfTemplates;

    @FindBy(xpath = "//input[@id='srCategory']/parent::div")
    public WebElement srCategory;

    @FindBy(xpath = "//div[@id='menu-srCategory']//ul/li")
    public List<WebElement> listOfSrCategories;

    @FindBy(xpath = "//input[@id='srCategory']/../following-sibling::p")
    public WebElement srCategoryFieldErrorMessage;

    @FindBy(xpath = "//*[text()='SR CATEGORY']/following-sibling::*")
    public WebElement lblSrCategory;

    @FindBy(xpath = "//input[@id='srCategory']/../div")
    public List<WebElement> listOfSelectedSrCategories;

    //Added by Shruti 03/27 /2020 TM REF
    //@FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO WORK - ESCALATED']/../following-sibling::div//*[name()='svg']")
   // @FindBy(xpath = "//*[text()='ASSOCIATE PERMISSION GROUP TO WORK - ESCALATED']/ancestor::div[@class='row']//button[2]")
   // public WebElement permToWorkEscalatedArrow;

    @FindBy(xpath = "//h5[text()='ASSOCIATE TEMPLATE']/../button")
    public WebElement addAssocTemplateButton;

    @FindBy(xpath="//p[text()='TASK TYPE']/following-sibling::*")
    public WebElement lblTaskTypeName;

    @FindBy(xpath = "//ul[@role='listbox']//li")
    public List<WebElement> lstAutoDropDownValues;

    //Refactored CP-1075 - Paramita 27th March 2020
    // @FindBy(xpath = "(//div[contains(@class,'indicatorContainer')])[1]")
    @FindBy(xpath = "(//button[@title='Open'])[1]")
    public WebElement permToWorkArrow;

  // Refactored - CP-1075 Paramita 27th March 2020
    //@FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO WORK - ESCALATED']/../following-sibling::div//*[name()='svg']")
   @FindBy(xpath = "(//button[@title='Open'])[2]")
    public WebElement permToWorkEscalatedArrow;

    @FindBy(xpath = "//h5[contains(text(),'ASSOCIATE SCREEN')]/ancestor::div[@class='row']/div[1]//input")
    public WebElement screenName;

    @FindBy(xpath = "(//span[text()='delete'])[1]/..")
    public WebElement deleteTemplate;

    @FindBy(xpath = "//h5[contains(text(),'ASSOCIATE SCREEN')]")
    public WebElement associateScreen;

    @FindBy(xpath = "//ul[@role='listbox']/li")
    public List<WebElement> actualScreenvalue;

    @FindBy(xpath = "//div[@role='button']")
    public WebElement savedScreenValue;

    @FindBy(xpath = "(//p[text()='END DATE']/../h6)[1]")
    public WebElement firstTemplateEndDate;

    @FindBy(xpath = "//p[text()='SELECT TEMPLATE']/following-sibling::h6")
    public WebElement readSelectTemplate;

    @FindBy(xpath = "//h5[text()=' ASSOCIATE SCREEN']/../../div//input")
    public WebElement associateScreenDD;

    @FindBy(xpath = "//span[text()='SYSTEMATICALLY CLOSE']/..//input")
    public WebElement systematicallyCloseCheckBox;

    @FindBy(xpath = "//span[text()='SYSTEMATICALLY CLOSE']/..//input/./ancestor::span")
    public WebElement systematicallyCloseCheckBoxDC;

    @FindBy(xpath = "//input[@name='restrictDuplicate']")
    public WebElement restrictDuplicateCheckbox;

    public WebElement gettaskTypewithValue(String taskType)
    {
       return Driver.getDriver().findElement(By.xpath("//td[2]/a[text()='"+taskType+"']"));
    }
    public WebElement getTaskTypewithName(String name)
    {
        return Driver.getDriver().findElement(By.xpath(".//td[2]//a[text()='"+name+"']"));
    }
}

