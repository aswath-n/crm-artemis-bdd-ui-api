package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CRMTaskQueueFilterViewPage {
    

    public CRMTaskQueueFilterViewPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//p[text()='CREATED BY']/following-sibling::h6")
    public WebElement lblCreatedBy;

    @FindBy(xpath = "//p[text()='CREATED ON']/following-sibling::h6")
    public WebElement lblCreatedOn;

    @FindBy(xpath = "//p[text()='NAME']/following-sibling::h6")
    public WebElement lblName;

    @FindBy(xpath = "//p[text()='TASK TYPE']/following-sibling::h6")
    public WebElement lblTaskType;

    @FindBy(xpath = "//p[text()='DESCRIPTION']/following-sibling::h6")
    public WebElement lblDescription;

    @FindBy(xpath = "//p[text()='APPLY FILTER FOR']/following-sibling::h6")
    public WebElement lblApplyFilterFor;

    @FindBy(xpath = "//p[text()='TEAM(s)']/following-sibling::h6")
    public WebElement lblTeams;

    @FindBy(xpath = "//p[text()='USER(s)']/following-sibling::h6")
    public WebElement lblUsers;

    @FindBy(xpath = "//p[text()='BUSINESS UNIT(s)']/following-sibling::h6")
    public WebElement lblBusinessUnits;

    @FindBy(xpath = "//h5[contains(text(), 'ACTIVE')]")
    public WebElement status;

    @FindBy(xpath = "//span[contains(text(), ' EDIT FILTER')]/parent::button")
    public WebElement editBtn;


}
