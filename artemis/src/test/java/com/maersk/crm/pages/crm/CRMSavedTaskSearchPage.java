package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMSavedTaskSearchPage {

    

    public CRMSavedTaskSearchPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//tbody//tr//td[2]//p")
    public List<WebElement> savedTaskSrchName;

    @FindBy(xpath = "//tbody//tr//td[3]//p")
    public List<WebElement> createdOnList;

    @FindBy(xpath = "//i[text()='chevron_right']")
    public List<WebElement> expandArrow;

    @FindBy(xpath = "//tr//td//div//p")
    public List<WebElement> srchParameterHeader;

    @FindBy(xpath = "//tr//td//div//p")
    public List<WebElement> srchParameterValue;

    @FindBy(xpath = "//h5[text()='No Records Available']")
    public WebElement noRecordsAvailable;

    @FindBy(xpath = "//h5[text()='SAVED TASK SEARCH']")
    public WebElement savedTaskSrchHeader;

    //Below elements added for scripting CP-832 By: Vidya
    @FindBy(xpath = "//span[text()='delete']")
    public List<WebElement> deleteBtn;

    @FindBy(xpath = "//span[text()=' WARNING MESSAGE']")
    public WebElement warningMsg;

    @FindBy(xpath = "//p[contains(text(),'If you continue, all the captured information will be lost')]")
    public WebElement warningMsgTxt;

    @FindBy(xpath = "//span[text()='check']")
    public WebElement warningMsgContBtn;

    @FindBy(xpath = "//span[text()='clear']")
    public WebElement warningMsgCancelBtn;

    @FindBy(xpath = "//span[text()='arrow_back']/parent::a")
    public WebElement lnkArrowBack;

    @FindBy(xpath = "//span[text()='arrow_forward']/parent::a")
    public WebElement lnkArrowForward;

    @FindBy(xpath = "//ul[@class='pagination']/li")
    public List<WebElement> lnkPageNations;

    @FindBy(id = "mui-component-select-itemsPerPage")
    public WebElement pageNationDD;

    @FindBy(xpath = "//p[contains(text(),\"There is an update to the existing search filter. Do you wish to update the Saved Task Search with the updated parameters?\")]")
    public WebElement updateSavedTaskSearchMessage;
}
