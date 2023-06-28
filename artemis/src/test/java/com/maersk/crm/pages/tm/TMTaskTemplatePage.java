package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TMTaskTemplatePage {

    

    public TMTaskTemplatePage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    /*This section has locators for Task Template List
    Author - Vinuta on Feb 20, 2019
     */
    //refactorBy:Vidya Date:-1-28-2020 Reason: Locator change
    @FindBy(xpath = "//span[contains(text(),'add')]")
    public WebElement addTaskTemplateBtn;

    @FindBy(xpath = "//h5[text()='TASK TEMPLATE']")
    public WebElement templateListLabel;

    @FindBy(xpath = "//table[contains(@class,'mx-table')]/tbody/tr")
    public List<WebElement> templateRows;

    @FindBy(xpath = "//span[text()='Task Field successfully created']")
    public WebElement FieldCreatedSuccessMsg;


    /*This section has locators for Task Template Details
    Author - Vinuta on Feb 20, 2019
     */
    @FindBy(xpath = "//h5[text()='CREATE TASK TEMPLATE ']")
    public WebElement templateDetailsLabel;

    @FindBy(xpath = "//div[@class='mx-box-btn']")
    public WebElement addFieldButton;

    @FindBy(name = "templateName")
    public WebElement templateName;

    //refactorBy: Vidya Date:1-28-2020
    @FindBy(name = "templateDescription")
    public WebElement templateDescription;

    @FindBy(xpath = "//span[contains(text(),'Save')]/..")
    public WebElement saveTaskTemplateBtn;

    @FindBy(xpath = "//*[contains(text(),'Cancel')]/parent::button")
    public WebElement cancelTaskTemplateBtn;

    @FindBy(xpath = "//span[contains(text(),'keyboard_backspace')]")
    public WebElement taskTemplateBackArrow;

    @FindBy(xpath = "//p[text()='The Template Name is required and cannot be left blank.']")
    public WebElement templateNameMandatoryError;

    //refactorBy:Vidya Date:-1-28-2020 Reason: Locator change
    @FindBy(xpath = "//span[text()='SELECTED FIELDS']")
    public WebElement selectedFieldsLabel;

    //refactorBy:Vidya Date:-1-28-2020 Reason: Locator change
    @FindBy(xpath = "//span[text()='ALL FIELDS']")
    public WebElement allFieldsLabel;

    @FindBy(xpath = "//*[text()='SUCCESS MESSAGE']")
    public WebElement successSnackbar;

    @FindBy(xpath = "//*[text()='Task Template successfully created']")
    public WebElement templateCreatedMessage;

    @FindBy(xpath = "//span[text()='Continue']/ancestor::button")
    public WebElement cancelWarningPopUp;

    @FindBy(xpath = "//*[text()='Cancel']/parent::button")
    public WebElement WarningPopupCancelBtn;

    @FindBy(xpath = "//*[text()='Continue']/parent::button")
    public WebElement WarningPopupContinueBtn;

    @FindBy(xpath = "//i[text()='chevron_right']")
    public WebElement addFieldsBtn;

    @FindBy(xpath = "//div[@class='row px-2 py-4']")
    public WebElement editFieldPanel;

    public WebElement TocreateTaskOption(String value)
    {
        return Driver.getDriver().findElement(By.xpath("//span[@class='MuiIconButton-label']//input[@value='"+value+"']"));
    }
    public WebElement ToCompleteTaskOption(String value)
    {
        return Driver.getDriver().findElement(By.xpath("//span[@class='MuiIconButton-label']//input[@value='"+value+"']"));
    }

    @FindBy(xpath = "//i[text()='chevron_left']")
    public WebElement removeFieldsBtn;

    //refactorBy:Vidya Date:-1-28-2020 Reason: Locator change
    //@FindBy(xpath = "//p[text()='CREATED BY']/../h6/span[text()='Service AccountOne']")
    @FindBy(xpath = "//p[text()='CREATED BY']/../h6/span")
    public WebElement createdBy;

    @FindBy(xpath = "//p[text()='CREATED ON']/../h6")
    public WebElement createdOn;

    @FindBy(xpath = "//p[text()='TEMPLATE NAME']/../h6")
    public WebElement viewTemplateName;

    @FindBy(xpath = "//p[text()='DESCRIPTION']/../h6")
    public WebElement viewTemplateDesc;

    @FindAll({
            @FindBy(xpath = "//label[@class='mdl-js-ripple-effect mx-checkbox-label']/span")
    })
    public List<WebElement> viewAllFields;

    @FindAll({
            @FindBy(xpath = "//label[@class='jss513']/p")
    })
    public List<WebElement> viewSelectedFields;

    @FindBy(xpath = "//a[text()=' CLONE TEMPLATE']")
    public WebElement cloneTemplate;

    @FindBy(xpath = "//div[@class='col-3 ml-5']/p")
    public List<WebElement> associatedTaskTypes;

    @FindBy(xpath = "//div[@class='col-2']/p")
    public List<WebElement> dates;

    @FindBy(xpath = "//p[contains(@class,'mx-warning-text mx-section-header')]")
    public WebElement warningMessage;

    @FindBy(xpath = "//span[text()='ALL FIELDS']/../following-sibling::div//label/span[1]")
    public List<WebElement> allFieldValues;

    @FindBy(xpath = "//p[text()='Template Description must be alphanumeric and cannot exceed 150 characters']")
    public WebElement desFieldLevelErrorMsg;

    @FindBy(xpath = "//span[text()='SELECTED FIELDS']/../following-sibling::div//p")
    public List<WebElement> defaultFieldValues;

    @FindBy(xpath = "//h6[contains(text(),'ALL FIELDS')]")
    public WebElement allFldsLblInViewPage;

    @FindBy(xpath = "//*[contains(text(),'ALL FIELDS')]/..//span")
    public List<WebElement> allFieldValuesInViewPage;

    @FindBy(xpath = "//*[text()='SELECTED FIELDS']/..//p")
    public List<WebElement> defaultFieldValuesInViewPage;

    @FindBy(xpath = "//a[text()='General']/../../following-sibling::tr//p[text()='ASSOCIATED TASK TYPES']")
    public WebElement taskTypeHeader;

    @FindBy(xpath = "//h6[text()='General ']")
    public WebElement generalTaskType;


    /* This section has locators to develop CP-964
       Author -Paramita  Date - 27/01/2020
     */



    @FindBy(xpath = "//input[@type='checkbox']")
    public List<WebElement> groupedFieldsChkbox;

    @FindBy(xpath = "//span[contains(text(),'Case Worker First Name')]")
    public WebElement caseworkFirstName;


    /*
  CP-8931 @albert
   */
    @FindBy(id = "select-complaint")
    public WebElement complaint;

    @FindBy(id = "select-message")
    public WebElement message;

    @FindBy(id = "select-siteType")
    public WebElement siteType;

    @FindBy(id = "select-siteName")
    public WebElement siteName;

    @FindBy(id = "select-siteAddressCity")
    public WebElement siteAddressCity;

    @FindBy(id = "select-siteAddressZipCode")
    public WebElement siteAddressZipCode;

    @FindBy(id = "select-siteContactFirstName")
    public WebElement siteContactFirstName;

    @FindBy(id = "select-siteContactPhone")
    public WebElement siteContactPhone;

    @FindBy(id = "select-siteContactEmail")
    public WebElement siteContactEmail;

    @FindBy(id = "select-communityOutreachSessionType")
    public WebElement communityOutreachSessionType;

    @FindBy(id = "select-contactReason")
    public WebElement contactReason;

    @FindBy(id = "select-disposition")
    public WebElement disposition;

    @FindBy(id = "select-messageSubject")
    public WebElement messageSubject;

    @FindBy(id = "select-sessionRegion")
    public WebElement sessionRegion;

    @FindBy(id = "select-siteAddressLine1")
    public WebElement siteAddressLine1;

    @FindBy(id = "select-siteAddressLine2")
    public WebElement siteAddressLine2;

    @FindBy(id = "select-siteAddressState")
    public WebElement siteAddressState;

    @FindBy(id = "select-siteCounty")
    public WebElement siteCounty;

    @FindBy(id = "select-siteContactLastName")
    public WebElement siteContactLastName;

    @FindBy(id = "select-siteContactFax")
    public WebElement siteContactFax;

    @FindBy(xpath = "//*[text()=' Save ']/..")
    public WebElement saveBtn;

    @FindBy(xpath = "//*[text()='build']")
    public WebElement buildIcon;

    @FindBy(xpath = "//*[text()='Task Template']")
    public WebElement taskTemplateSideDrawer;

    @FindBy(xpath = "//*[text()=' add ']")
    public WebElement createNewTaskTemplate;

    @FindBy(xpath = "//*[text()='edit']/parent::a")
    public WebElement editTaskTemplateBtn;

    @FindBy(xpath = "//*[text()='add']/parent::a")
    public WebElement addFieldBtn;

    @FindBy(xpath = "//div[contains(@id, 'select-fieldType')]")
    public WebElement fieldTypeDropDown;

    @FindBy(xpath = "//div[contains(@id, 'menu-fieldType')]//ul/li")
    public List<WebElement> fieldTypeDropDownValues;

    @FindBy(name = "minCharacters")
    public WebElement minCharactersInput;

    @FindBy(name = "maxCharacters")
    public WebElement maxCharactersInput;

    @FindBy(xpath = "//div[contains(@id, 'select-service')]")
    public WebElement serviceDropDown;

    @FindBy(xpath = "//div[contains(@id, 'menu-service')]//ul/li")
    public List<WebElement> serviceDropDownValues;

    @FindBy(xpath = "//div[contains(@id, 'select-table')]")
    public WebElement tableDropDown;

    @FindBy(xpath = "//div[contains(@id, 'menu-table')]//ul/li")
    public List<WebElement> tableDropDownValues;

    @FindBy(name = "displayName")
    public WebElement displayNameInput;

    @FindBy(name = "fieldKey")
    public WebElement fieldKeyInput;

    @FindBy(xpath = "//*[text()='check']/../parent::button")
    public WebElement addFieldSaveBtn;

    @FindBy(xpath = "//input[@name='minCharacters']/../following-sibling::*")
    public WebElement minCharactersErrorMessage;

    @FindBy(xpath = "//input[@name='maxCharacters']/../following-sibling::*")
    public WebElement maxCharactersErrorMessage;

    @FindBy(xpath = "//input[@name='displayName']/../following-sibling::*")
    public WebElement displayNameErrorMessage;

    @FindBy(xpath = "//input[@name='fieldKey']/../following-sibling::*")
    public WebElement fieldKeyErrorMessage;

    @FindBy(xpath = "//div[contains(@id, 'select-service')]/../following-sibling::*")
    public WebElement serviceErrorMessage;

    @FindBy(xpath = "//div[contains(@id, 'select-table')]/../following-sibling::*")
    public WebElement tableErrorMessage;

    @FindBy(xpath="//div[@data-rbd-droppable-id='selected-task-fields']//button")
    public WebElement selectedTaskFieldeditButton;

    @FindBy(xpath = "//input[@name='displayOnTaskBar']")
    public WebElement displayOnTaskbarChkBox;

    @FindBy(xpath = "//div[@class='row px-2 py-4']//button[text()=' Save']")
    public WebElement editFieldSaveButton;
}
