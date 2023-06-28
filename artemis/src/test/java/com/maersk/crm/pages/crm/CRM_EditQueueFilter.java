package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRM_EditQueueFilter {

    

    public CRM_EditQueueFilter() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(name="queueName")
    public WebElement name;

    @FindBy(id="mui-component-select-taskTypes")
    public WebElement taskType;

    @FindBy(xpath="//input[contains(@id,'escription')]")
    public WebElement taskDescription;

    @FindBy(id="mui-component-select-scopeType")
    public WebElement applyFilterFor;

    @FindBy(xpath = "//div[@id='menu-scopeType']//ul/li")
    public List<WebElement> taskApplyFilterForDrDw;

    @FindBy(xpath = "//span[text()='Task Queue Filter was successfully saved']")
    public WebElement successMessageText;

    @FindBy(id = "status")
    public WebElement activateFilter;

    @FindBy(xpath="//*[text()='EDIT FILTER']")
    public List<WebElement> editPageHeader;

    @FindBy(xpath = "//div[@id='menu-taskTypes']//ul/li")
    public List<WebElement> taskTypeIdDrDw;
}
