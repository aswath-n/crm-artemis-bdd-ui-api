package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TMProjectHolidaysPage {

    

    public TMProjectHolidaysPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath="//span[contains(text(),'Save')]")
    public WebElement holidaysSaveButton;

    @FindBy(xpath="//span[contains(text(),'Cancel')]")
    public WebElement holidaysCancelButton;

    @FindBy(xpath = "//input[@name='holidayName']")
    public WebElement holidaysName;

    @FindBy(xpath = "//input[@id='holidayDate']")
    public WebElement selectHolidayDate;

    @FindBy(xpath = "//p[@class='holidayDate-helper-text']")
    public WebElement selectDateWarningMessage;

    //Add this for refactoring reason---Vidya 23/10/2019
    @FindBy(xpath = "//h5[text()='HOLIDAY ']")
    public WebElement holidayHeader;

    @FindBy(xpath = "//input[contains(@id,'holidayDate')]")
    public List<WebElement> selectDate;

    @FindBy(xpath = "//input[contains(@id,'holidayName')]")
    public List<WebElement> holidayName;

    @FindBy(xpath = "//input[contains(@id,'excludeForTaskInd')]")
    public List<WebElement> excludeForTaskCkBx;

    @FindBy(xpath = "//input[contains(@id,'excludeForSrInd')]")
    public List<WebElement> excludeForSrCkBx;

    @FindBy(xpath = "//button[@class='mdl-button mdl-js-button mdl-button--icon mdl-button--colored mx-btn mx-btn-border mx-btn-fabmini--white mt-4 mr-3 float-right']")
    public WebElement addHolidayBtn;

    @FindBy(xpath = "//button[@class=' mdl-button mdl-js-button mdl-button--icon mdl-button--colored mx-btn mx-btn-border mx-btn-fabmini--white mt-4 mr-3 float-right']")
    public List<WebElement> removeHolidayBtn;

    @FindBy(xpath = "//p[text()='This Holiday is on a weekend.']")
    public WebElement holidayWeekendError;
}
