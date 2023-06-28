package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class CRMTaskQueueFilterPage {
    

    public CRMTaskQueueFilterPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//span[text()='CREATE FILTER']")
    public WebElement createFilterBt;

    @FindBy(xpath = "//span[text()='arrow_forward']/parent::a")
    public WebElement lnkArrowForward;

    @FindBy(xpath = "//ul[@class='pagination']/li")
    public List<WebElement> lnkPageNations;

    @FindBy(xpath = "//span[text()='arrow_back']/parent::a")
    public WebElement lnkArrowBack;

    @FindBy(id="taskFilterName")
    public WebElement name;

    @FindBy(id="mui-component-select-taskTypeId")
    public WebElement taskType;

    @FindBy(id="taskDescription")
    public WebElement taskDescription;

    @FindBy(id="mui-component-select-applyFilterFor")
    public WebElement applyFilterFor;

    @FindBy(id="businessUnits")
    public WebElement businessUnits;

    @FindBy(id="teams")
    public WebElement teams;

    @FindBy(id="_Users")
    public WebElement users;

    @FindBy(id = "activateFilter")
    public WebElement activateFilter;

    @FindBy(xpath = "//span[text()='Save']")
    public WebElement save;

    @FindBy(xpath = "//span[text()='Cancel']")
    public WebElement cancel;

    @FindBy(xpath = "//div[7]//div[1]//div[1]/div")
    public WebElement applyFilterSubTab;

    @FindBy(xpath = "//div[@id='menu-taskTypeId']//ul/li")
    public List<WebElement> taskTypeIdDrDw;

    @FindBy(xpath = "//div[@id='menu-applyFilterFor']//ul/li")
    public List<WebElement> taskApplyFilterForDrDw;

    @FindBy(xpath = "//h5[text()='TASK QUEUE FILTER']/../..//table/tbody/tr//td[8]")
    public List<WebElement> statusClumVlu;

    @FindBy(xpath = "//*[contains(text(),'TASK QUEUE FILTER')]/../..//table/tbody//tr//td[2]/..")
    public List<WebElement> taskQueueFilterRows;

    @FindBy(xpath = "//h5[text()='TASK QUEUE FILTER']/../..//table/tbody/tr//td[2]")
    public List<WebElement> firstNameList;

    @FindBy(xpath = "//h5[text()='TASK QUEUE FILTER']/../..//table/tbody/tr//td[3]")
    public List<WebElement> descriptionList;

    @FindBy(xpath = "//h5[text()='TASK QUEUE FILTER']/../..//table/tbody/tr//td[4]")
    public List<WebElement> taskTypeList;

    @FindBy(xpath = "//h5[text()='TASK QUEUE FILTER']/../..//table/tbody/tr//td[5]")
    public List<WebElement> scopeList;

    @FindBy(xpath = "//h5[text()='TASK QUEUE FILTER']/../..//table/tbody/tr//td[6]")
    public List<WebElement> createdOnList;

    @FindBy(xpath = "//h5[text()='TASK QUEUE FILTER']/../..//table/tbody/tr//td[7]")
    public List<WebElement> createdByList;

    @FindBy(xpath = "//h5[text()='TASK QUEUE FILTER']/../..//table/thead/tr//th")
    public List<WebElement> taskQueueFilterColumns;

    @FindBy(xpath = "//h5[text()='TASK QUEUE FILTER']")
    public WebElement taskQueueFilterHeader;

    @FindBy(xpath = "//span[text()='SUCCESS MESSAGE']")
    public WebElement successMessage;

    @FindBy(xpath = "//span[text()='Successfully Created Queue Filter']")
    public WebElement successMessageText;

    @FindBy(xpath = "//div[8]//div[1]//div[1]/div")
    public WebElement applyFilterSubTabBusinessUnit;

    @FindBy(xpath = "//p[contains(text(),'cannot exceed 50 characters')]")
    public WebElement errorMessageForNameInManagerQueueFilter;

    @FindBy(xpath = "//p[contains(text(),'cannot exceed 150 characters')]")
    public WebElement errorMessageForDescriptionInManagerQueueFilter;
}

