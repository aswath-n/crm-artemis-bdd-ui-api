package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TMEditTaskTypePage {
    

    public TMEditTaskTypePage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    //@FindBy(xpath = "//span[contains(text(),'edit')]")
    @FindBy(xpath = "//span[contains(text(),'edit')]//parent::button")
    public WebElement taskTypeEDITButton;

    @FindBy(xpath = "//h5[contains(text(),'TASK TYPE DETAILS')]/../..//div/p[1]/..")
    public List<WebElement> editTaskReadOnlyfield;

    @FindBy(xpath = "//p[text()='TASK TYPE ID']//following-sibling::h6")
    public WebElement taskTypeIdEditPage;

    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO WORK']")
    public WebElement taskAssociateScreen;

    @FindBy(xpath = "//button[@type='button']/..//i")
    public List<WebElement> taskTypeSAVECancelButton;

    @FindBy(xpath = "//h5[contains(text(),'TASK TYPE DETAILS')]/../..//div//h6")
    public List<WebElement> readOnlyTasktypefieldsVal;

    @FindBy(xpath = "//p[text()='DESCRIPTION']/..//p[2]")
    public WebElement descriptionValue;

    @FindBy(xpath = "//h5[text()='ACTIVE']")
    public WebElement taskTypeStatus;

    @FindBy(xpath = "//p[contains(text(),'END DATE')]/../..//div//h6")
    public WebElement templateEndDateValue;

    @FindBy(xpath = "//h5[contains(text(),'ASSOCIATE TEMPLATE')]")
    public WebElement associateTemplate;

    @FindBy(xpath = "//span[contains(text(),'Save')]/ancestor::button")
    public WebElement editSaveButton;

    @FindBy(xpath = "//button[@type='button']/..//i[text()='clear']")
    public WebElement editCancelButton;


  // @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO WORK']/..//following-sibling::div//div[@role='button']//*[name()='svg']")
   // public List<WebElement> roleDeleteIconIstPerm;

    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO WORK']/..//following-sibling::div//button[@title='Clear']//*[name()='svg']")
    public WebElement roleDeleteIconPerm;

    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO WORK - ESCALATED']/..//following-sibling::div//button[@title='Clear']//*[name()='svg']")
    public WebElement roleDeleteIcon2ndPerm;

    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO CREATE/EDIT']/..//following-sibling::div//div[@role='button']//*[name()='svg']")
    public List<WebElement> roleDeleteIcon3rdPerm;

    @FindBy(xpath = "//p[contains(text(),'The Permission Group Name is required and cannot be left blank.')]")
    public WebElement errorMesg1;

    @FindBy(xpath = "//p[contains(text(),'The Permission Group Escalated Name is required and cannot be left blank.')]")
    public WebElement errorMesg2;

    @FindBy(xpath = "//span[contains(text(),'SUCCESS MESSAGE')]")
    public WebElement successMessage;

//h5[text()='ASSOCIATE PERMISSION GROUP TO WORK']/..//following-sibling::div//button[@title='Clear']//*[name()='svg']

    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO CREATE']/ancestor::div[@class='row']/div[2]/div/div/div")
    public WebElement lstPermisionGroupToCreate;

    @FindBy(xpath = "//h5[text()='ASSOCIATE PERMISSION GROUP TO EDIT']/ancestor::div[@class='row']/div[2]/div/div/div")
    public WebElement lstPermisionGroupToEdit;


    @FindBy(xpath = "//span[text()='keyboard_backspace']")
    public WebElement backArrow;

    @FindBy(xpath = "//span[text()='clear']")
    public WebElement warningMsgCancelBtn;

    @FindBy(xpath = "//h5[contains(text(),'TASK TYPE DETAILS ')]")
    public WebElement taskTypeDetails;

    @FindBy(xpath = "//span[text()='check']")
    public WebElement warningMsgContinueBtn;

    @FindBy(xpath = "//span[text()='Continue']/ancestor::button")
    public WebElement btnContinueWarningWindow;

    @FindBy(xpath = "//span[text()='Cancel']/ancestor::button")
    public WebElement btnCancelWarningWindow;

    @FindBy(xpath = "//h5[contains(text(), 'TASK TYPE')]")
    public WebElement lblTaskType;

    @FindBy(xpath = "//span[text()='group']")
    public WebElement UserIcon;

    @FindBy(xpath = "//h5[contains(text(),'User List')]")
    public WebElement userListScreen;

    @FindBy(xpath = "//span[contains(text(),'Task Type successfully updated')]")
    public WebElement editSuccessMesg;

    @FindBy(name="taskType")
    public WebElement txtTaskType;

    @FindBy(xpath = "//input[@name='taskPriority']/ancestor::div[1]")
    public WebElement lstTaskPriority;

    @FindBy(xpath = "//div[@id='menu-taskPriority']//ul/li")
    public List<WebElement> taskPriorityValues;


    @FindBy(xpath ="//input[@name='dueInDate']")
    public WebElement txtDueInDays;

    @FindBy(xpath = "//h5[text()=' ASSOCIATE SCREEN']/../../div//input")
    public WebElement lstAssociateScreen;

    @FindBy(xpath = "//input[@name='taskDescription']")
    public WebElement txtTaskDescription;

    @FindBy(xpath = "(//button[@title='Open'])[1]")
    public WebElement permToWorkArrow;

     @FindBy(xpath = "(//button[@title='Open'])[2]")
    public WebElement permToWorkEscalatedArrow;

    @FindBy(xpath = "//h5[text()='ASSOCIATE TEMPLATE']/../button")
    public WebElement addAssocTemplateButton;

    @FindBy(xpath = "//input[@name='selectedTemplate']/parent::div/div")
    public WebElement selectTemplate;

    @FindBy(xpath = "(//label[contains(text(),'Start Date')]/following-sibling::div/input)[1]")
    public WebElement startDate;

    @FindBy(xpath = "(//label[contains(text(),'End Date')]/following-sibling::div/input)[1]")
    public WebElement endDate;


    @FindBy(xpath = "//span[contains(text(),'ADD TEMPLATE ')]")
    public WebElement templateButton;

    @FindBy(xpath = "//span[contains(text(), 'add')]/ancestor::button")
    public WebElement btnAddTaskType;

    @FindBy(xpath = "//h5[contains(text(),'TASK TYPE')]/../..//table//tbody/tr")
    public List<WebElement> taskTypeCount;

    @FindBy(xpath = "//p[text()='SELECT PERMISSION GROUP(S)']/..//p[2]")
    public WebElement optionalFieldValue;













}
