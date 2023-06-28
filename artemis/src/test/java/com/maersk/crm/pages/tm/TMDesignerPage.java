package com.maersk.crm.pages.tm;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TMDesignerPage{
    

    public TMDesignerPage()
    {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//div//h5[contains(text(),'PAGES')]")
    public WebElement pagesTitle;

    @FindBy(xpath = "(//div[contains(@class,'pr-4')]//button)[1]")
    public WebElement addPageButton;

    @FindBy(xpath = "//tr//td//a")
    public List<WebElement> pageKeys;

    public WebElement getPageKeyWithvalue(String value)
    {
        return Driver.getDriver().findElement(By.xpath("//tr//td//a[text()='"+value+"']"));
    }

    @FindBy(xpath = "(//input)[1]")
    public WebElement pageKeyInput;

    @FindBy(xpath = "(//input)[2]")
    public WebElement pageNameInput;

    @FindBy(xpath = "(//span//i)[1]")
    public WebElement savePageButton;

    @FindBy(xpath = "//div[contains(@class,'float-right')]//button")
    public WebElement addTemplateButton;

    @FindBy(xpath = "//tr//td//a")
    public List<WebElement> TemplateKeys;

    @FindBy(xpath = "//tr//th[text()='TEMPLATE KEY']")
    public WebElement templateKeyHeader;
    @FindBy(xpath = "//tr//th[text()='TEMPLATE NAME']")
    public WebElement templateNameHeader;
    @FindBy(xpath = "//tr//th[text()='TEMPLATE TYPE']")
    public WebElement templateTypeHeader;
    @FindBy(xpath = "//tr//th[text()='Updated ON']")
    public WebElement updatedOnHeader;
    @FindBy(xpath = "//tr//th[text()='Updated BY']")
    public WebElement updatedByHeader;

    public WebElement pageNameLabelwithValue(String value)
    {
        return Driver.getDriver().findElement(By.xpath("//div//h6[text()='"+value+"']"));
    }

    public WebElement templateLabelwithValue(String value)
    {
        return Driver.getDriver().findElement(By.xpath("//table//tr//td//a[text()='"+value+"']"));
    }

    @FindBy(xpath = "//input[@class='MuiInputBase-input MuiInput-input']")
    public WebElement templateNameInputBox;

    @FindBy(xpath = "//a//i[text()='edit']")
    public WebElement templateEditButton;

    @FindBy(xpath = "(//input[@class='MuiInputBase-input MuiInput-input'])[2]")
    public WebElement templateKeyInputBox;

    @FindBy(id = "mui-component-select-srCategory")
    public WebElement templateTypeDropDown;

    @FindBy(xpath = "(//span//i)[1]")
    public WebElement savetemplateBtn;

    @FindBy(xpath = "//div[@class='mt-0']/..//i")
    public WebElement templateSuccessMsg;

    @FindBy(xpath = "//div//h5[text()='No Records Available ']")
    public WebElement noRecordsLabel;

    public WebElement getTemplateFromList(String val)
    {
        return Driver.getDriver().findElement(By.xpath("//tr//td//a[text()='"+val+"']"));
    }

    @FindBy(xpath = "//span//span[text()='check']")
    public WebElement okButton;

    public WebElement getTemplateNameFromList(String templateKey)
    {
        return Driver.getDriver().findElement(By.xpath("//tr//td//a[text()='"+templateKey+"']/../following-sibling::td"));
    }

    @FindBy(xpath = "//a//i[text()='add']")
    public WebElement addFieldButton;

    @FindBy(xpath = "//div[@id='mui-component-select-fieldType']")
    public WebElement fieldTypeDropdown;

    @FindBy(xpath = "//label[text()='FIELD KEY']/following-sibling::div//input")
    public WebElement fieldKeyInputBox;

    @FindBy(xpath = "//label[text()='FIELD NAME']/following-sibling::div//input")
    public WebElement fieldNameInputBox;

    @FindBy(xpath = "//label[text()='FIELD ORDER']/following-sibling::div//input")
    public WebElement fieldOrderInputBox;

    @FindBy(xpath = "//label[text()='FIELD DISPLAY LABEL']/following-sibling::div//input")
    public WebElement fieldDisplayLabelInputBox;

    @FindBy(xpath = "//label[text()='REQUIRED']")
    public WebElement labelRequired;

    @FindBy(xpath = "//label[text()='AUTO OK']")
    public WebElement labelAutoOk;

    @FindBy(xpath = "//label[text()='CLEARABLE']")
    public WebElement labelClearable;

    @FindBy(xpath = "//label[text()='MULTILINE']")
    public WebElement labelMultiline;

    @FindBy(xpath = "//label[text()='MASK VALUE']")
    public WebElement labelMaskValue;

    @FindBy(xpath = "//label[text()='MULTISELECT']")
    public WebElement labelMultiselect;

    @FindBy(xpath = "//label[text()='ERROR MSG']/following-sibling::div//input")
    public WebElement errorMessageInputBox;

    @FindBy(xpath = "//label[text()='COLOR']/following-sibling::div//input")
    public WebElement colorInputBox;

    @FindBy(xpath = "//label[text()='CLASS NAME PROPS']/following-sibling::div//input")
    public  WebElement classNamePropsInputBox;

    @FindBy(xpath = "//a[text()=' SAVE ']")
    public WebElement saveFieldButton;

    @FindBy(xpath = "//input[@name='required']")
    public WebElement radioButtonNO;

    @FindBy(xpath = "(//input[@name='required'])[2]")
    public WebElement requiredRadioButtonYES;

    @FindBy(xpath = "(//input[@name='autoOk'])[2]")
    public WebElement autoOkRadioButtonYES;

    @FindBy(xpath = "(//span[@class='MuiIconButton-label']//input[@name='required'])[4]")
    public WebElement multiselectRadioButtonYES;

    @FindBy(xpath = "//input[@value='TEXTFIELD']")
    public WebElement displayStyleTextfieldRadioButtonYES;

    @FindBy(xpath = "(//input[@name='clearable'])[2]")
    public WebElement clearableRadioButtonYES;

    public WebElement getFieldTicket(String fieldKey)
    {
        return Driver.getDriver().findElement(By.xpath("//h6[text()='"+fieldKey+"']"));
    }

    public WebElement getFieldTicketType(String fieldKey, String fieldType)
    {
        return Driver.getDriver().findElement(By.xpath("//h6[text()='"+fieldKey+"']/following-sibling::p[text()='"+fieldType+"']"));
    }

    @FindBy(xpath = "//label[text()='ROW']")
    public WebElement headerRow;

    @FindBy(xpath = "//label[text()='ROW']/following-sibling::div[@role='radiogroup']")
    public WebElement rowRadioGroup;

    @FindBy(xpath = "//label[text()='DEFAULT VALUE']/following-sibling::div//input")
    public WebElement defaultValueInputBox;

    @FindBy(xpath = "//label[text()='OPTIONS']/following-sibling::div//input")
    public WebElement optionsInputBox;

    @FindBy(xpath = "//label[text()='AUTO OK']/following-sibling::div")
    public WebElement autoOkRadioBtnGrp;

    @FindBy(xpath = "//label[text()='MULTILINE']/following-sibling::div")
    public WebElement multilineRadioBtnGrp;

    @FindBy(xpath = "//label[text()='MASK VALUE']/following-sibling::div")
    public WebElement maskValueRadioBtnGrp;

    @FindBy(xpath = "//label[text()='MULTISELECT']/following-sibling::div")
    public WebElement multiselectRadioBtnGrp;

    @FindBy(xpath = "//label[text()='REQUIRED']/following-sibling::div")
    public WebElement requiredRadioBtnGrp;

    @FindBy(xpath = "//label[text()='CLEARABLE']/following-sibling::div")
    public WebElement clearableRadioBtnGrp;

    @FindBy(xpath = "//label[text()='MINIMUM DATE ERROR MSG']/following-sibling::div//input")
    public WebElement minDateErrorMsgInputBox;

    @FindBy(xpath = "//label[text()='MAXIMUM DATE ERROR MSG']/following-sibling::div//input")
    public WebElement maxDateErrorMsgInputBox;

    @FindBy(xpath = "//label[text()='MIN LENGTH ERROR MSG']/following-sibling::div//input")
    public WebElement minLengthErrorMsgInputBox;

    @FindBy(xpath = "//label[text()='MAX LENGTH ERROR MSG']/following-sibling::div//input")
    public WebElement maxLengthErrorMsgInputBox;

    @FindBy(xpath = "//label[text()='MIN LENGTH']/following-sibling::div//input")
    public WebElement minLengthInputBox;

    @FindBy(xpath = "//label[text()='MAX LENGTH']/following-sibling::div//input")
    public WebElement maxLengthInputBox;

    @FindBy(xpath = "//label[text()='MIN LENGTH']")
    public WebElement labelMinLength;

    @FindBy(xpath = "//label[text()='MAX LENGTH']")
    public WebElement labelmaxLength;

    @FindBy(xpath = "//label[text()='INVALID DATE MSG']/following-sibling::div//input")
    public WebElement invalidErrorMsgInputBox;

    @FindBy(xpath = "//label[text()='FORMAT ERROR MSG']/following-sibling::div//input")
    public WebElement formatErrorMsgInputBox;

    @FindBy(xpath = "//div[@id='mui-component-select-dataType']")
    public WebElement fieldDataTypeDropdownBox;

    @FindBy(xpath = "//label[text()='ICON']/following-sibling::div//input")
    public WebElement iconInputBox;

    @FindBy(xpath = "//div[@id='mui-component-select-service']")
    public WebElement serviceDropdownBox;

    @FindBy(xpath = "//div[@id='mui-component-select-table']")
    public WebElement tableDropdownBox;

    @FindBy(xpath = "//h5[text()='GRID DETAILS']")
    public WebElement gridDetailsHeader;

    @FindBy(xpath = "//label[text()='GRID KEY']/following-sibling::div/input")
    public WebElement gridKeyInputBox;

    @FindBy(xpath = "//label[text()='GRID NAME']/following-sibling::div/input")
    public WebElement gridNameInputBox;

    @FindBy(xpath = "//label[text()='GRID LABEL']/following-sibling::div/input")
    public WebElement gridLabelInputBox;

    @FindBy(xpath = "//label[text()='PAGE SIZE']/following-sibling::div/input")
    public WebElement pageSizeInputBox;

    @FindBy(xpath = "//p[text()='valid page size format 1,5,50']")
    public WebElement pageSizeDescMsg;

    @FindBy(xpath = "//label[text()='FILTER']/following-sibling::div/input")
    public WebElement filterInputBox;

    @FindBy(xpath = "//label[text()='GRID KEY']")
    public WebElement gridKeyLabel;

    @FindBy(xpath = "//label[text()='GRID NAME']")
    public WebElement gridNameLabel;

    @FindBy(xpath = "//label[text()='GRID LABEL']")
    public WebElement gridLabelHeader;

    @FindBy(xpath = "//div//label[text()='PAGINATION']/parent::div//span//span//input[@value='false']")
    public WebElement paginationNORadioBtn;

    @FindBy(xpath = "//div//label[text()='PAGINATION']/parent::div//span//span//input[@value='true']")
    public WebElement paginationYESRadioBtn;

    @FindBy(xpath = "//label[text()='PAGE SIZE']")
    public WebElement pageSizeHeader;

    @FindBy(xpath = "//div//label[text()='SORT ORDER']/parent::div//span//span//input[@value='ASC']")
    public WebElement sortOrderASCRadioBtn;

    @FindBy(xpath = "//div//label[text()='SORT ORDER']/parent::div//span//span//input[@value='DESC']")
    public WebElement sortOrderDESCRadioBtn;

    @FindBy(xpath = "//div//a[text()=' SAVE ']//i")
    public WebElement saveGridBtn;

    @FindBy(xpath = "//div//a[text()=' EDIT GRID ']//i")
    public WebElement editGridBtn;

    @FindBy(xpath = "//div//a[text()='ADD COLUMN ']//i")
    public WebElement addColumnBtn;

    @FindBy(xpath = "//h5[text()='GRID COLUMN LIST']")
    public WebElement gridColumnListHeader;

    @FindBy(xpath = "//label[text()='GRID COLUMN KEY']/following-sibling::div/input")
    public WebElement gridColumnKeyInputBox;

    @FindBy(xpath = "//label[text()='GRID COLUMN KEY']")
    public WebElement gridColumnKeyLabel;

    @FindBy(xpath = "//label[text()='GRID COLUMN LABEL']/following-sibling::div/input")
    public WebElement gridColumnLabelInputBox;

    @FindBy(xpath = "//label[text()='GRID COLUMN LABEL']")
    public WebElement gridColumnLabel;

    @FindBy(xpath = "//label[text()='POSITION']/following-sibling::div/input")
    public WebElement positionInputBox;
    
    @FindBy(xpath = "//div//label[text()='CLICKABLE']/parent::div//span//span//input[@value='false']")
    public WebElement clickableNORadioBtn;

    @FindBy(xpath = "//div//label[text()='CLICKABLE']/parent::div//span//span//input[@value='true']")
    public WebElement clickableYESRadioBtn;

    @FindBy(xpath = "//label[text()='SEPARATOR']")
    public WebElement seperatorLabel;

    @FindBy(xpath = "//label[text()='SEPARATOR']/following-sibling::div/input")
    public WebElement seperatorInputBox;

    @FindBy(xpath = "//label[text()='COLUMN PROPERTY']/following-sibling::div/input")
    public WebElement propertyInputBox;

    @FindBy(xpath = "//button//i[text()='add']")
    public WebElement addPropertyIcon;

    public WebElement propertyInputBoxWithIndex(int i)
    {
        return Driver.getDriver().findElement(By.xpath("(//label[text()='COLUMN PROPERTY']/following-sibling::div/input)["+i+"]"));
    }

    @FindBy(xpath = "//a[contains(text(),'SAVE')]//i")
    public WebElement saveGridColumn;

    public WebElement getGridColumPanelLabel(String label)
    {
        return Driver.getDriver().findElement(By.xpath("//a//div//div//h6[text()='"+label+"']"));
    }

    public WebElement getGridColumPanelPosition(String label,String position)
    {
        return Driver.getDriver().findElement(By.xpath("//a//div//div//h6[text()='"+label+"']//following-sibling::p[text()='"+position+"']"));
    }

    @FindAll({
            @FindBy(xpath = "//label[text()='COLUMN PROPERTY']/following-sibling::div/input")
    })
    public List<WebElement> columnPropertiesList;

    @FindBy(xpath = "//label[text()='DISPLAY STYLE']")
    public WebElement labelMisplayStyle;

    @FindBy(xpath = "(//span[@class='MuiIconButton-label']//input[@name='required'])[6]")
    public WebElement displayStyleRadioButtonTextField;

    @FindAll({
            @FindBy(xpath = "//ul[@role='listbox']//li")
    })
    public List<WebElement> TextfieldDataTypes;

    @FindBy(xpath = "//ul[@role='listbox']//li[text()='alphaNumeric']")
    public WebElement dataTypeValue;
    @FindBy(xpath = "//button//span[contains(text(),'CONFIGURE RULES')]")
    public WebElement configureRulesSection;

    @FindBy(xpath = "//a[contains(text(),'ADD RULE')]")
    public WebElement addRuleBtn;

    @FindBy(xpath = "//div[@id='mui-component-select-sourceField']")
    public List<WebElement> listSourceFieldDropdown;

    @FindBy(xpath = "//div[@id='mui-component-select-sourceCondition']")
    public List<WebElement> ListSourceConditionDropdown;

    @FindBy(xpath = "//div[@id='mui-component-select-dependentField']")
    public List<WebElement> listDependentFieldDropdown;

    @FindBy(xpath = "//div[@id='mui-component-select-dependentCondition']")
    public List<WebElement> listDependentConditionDropdown;

    @FindBy(xpath = "(//div[@id='mui-component-select-sourceCondition'])[2]")
    public WebElement anotherSourceConditionDropdown;

    @FindBy(xpath = "//a[contains(text(),'Save')]")
    public WebElement saveRuleButton;

    @FindBy(xpath = "//p[contains(text(),'Field rules saved successfully')]")
    public WebElement succesMessage;

    @FindBy(xpath = "//input[@id='_Select Value(s)']")
    public List<WebElement> FieldValuesDropdown;

    @FindBy(xpath = "//a[contains(text(),' Cancel') ]")
    public WebElement cancelButton;

    @FindBy(xpath = "//span[contains(text(),'WARNING MESSAGE')]")
    public WebElement warningMsgonCancelBtn;
}
