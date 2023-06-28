package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OutboundCorrespondenceDefinitionChannelPage {
    

    public OutboundCorrespondenceDefinitionChannelPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//*[contains(text(),'CORRESPONDENCE CHANNEL DETAILS')]")
    public WebElement channelHeader;

    @FindBy(xpath = "//label[contains(text(),'Start Date')]/following-sibling::div/input")
    public WebElement startDate;

    @FindBy(xpath = "//label[contains(text(),'End Date')]/following-sibling::div/input")
    public WebElement endDate;

    @FindBy(xpath = "//*[contains(text(),'Save')]/..")
    public WebElement saveButton;

    @FindBy(xpath = "//input[@name='endReason']")
    public WebElement endReason;

    @FindBy(xpath = "//input[@name='senderEmail']")
    public WebElement senderEmailId;

    @FindBy(id = "sendImmediately")
    public WebElement sendImmediately;

    @FindBy(id = "mandatory")
    public WebElement mandatory;

    @FindBy(xpath = "//p[.='Template File Name']/following-sibling::*")
    public WebElement templateFileName;

    @FindBy(xpath = "//label[@htmlforhtml='selectChannel']/..")
    public WebElement channelType;

    @FindBy(xpath = "//*[contains(text(),'chevron_right')]")
    public WebElement chevronRight;

    @FindBy(xpath = "//*[.='UPDATED BY']/following-sibling::h6")
    public WebElement updatedBy;

    @FindBy(xpath = "//*[.='UPDATED ON']/following-sibling::h6")
    public WebElement updatedOn;

    @FindBy(xpath = "//*[contains(text(),'clear')]")
    public WebElement cancel;

    @FindBy(xpath = "//label[contains(text(),'Start Date')]/following-sibling::div/div/button")
    public WebElement calendarButton;

    @FindBy(xpath = "//span[contains(text(),'OK')]")
    public WebElement calendarOk;

    @FindBy(xpath = "//label[contains(text(),'End Date')]/following-sibling::div/div/button")
    public WebElement calendarButtonEndDate;

    @FindBy(xpath = "//span[contains(text(),'OK')]")
    public WebElement calendarOkEndDate;

    @FindBy(xpath = "//div[@class='MuiPickersBasePicker-pickerView']/div/div/button[@tabindex='0']")
    public WebElement nextCalendarMonth;

    @FindBy(xpath = "//label[@htmlforhtml='notificationPurpose']/..")
    public WebElement notificationPurposeDropdown;

    public List<WebElement> getChannels() {
        return Driver.getDriver().findElements(By.xpath("//p[contains(text(),'CHANNEL')]"));
    }

    public List<WebElement> getChannelType() {
        return Driver.getDriver().findElements(By.xpath("//*[contains(text(),'CHANNELS FOR DEFINITION')]/../following-sibling::div[2]/a/div/div/div/following-sibling::div/p[2]"));
    }

    public WebElement getFailMessage(String message) {
        return Driver.getDriver().findElement(By.xpath("//*[contains(text(),'" + message + "')]"));
    }

    public List<WebElement> getAllFields() {
        return Driver.getDriver().findElements(By.xpath("//span[contains(text(),'ALL FIELDS')]/../following-sibling::div/form/div/div/div/label/span/span/input[@type='checkbox']"));
    }

    public WebElement getCalendarDate(String date) {
        return Driver.getDriver().findElement(By.xpath("//div[contains(@class,'MuiPickersSlideTransition-transitionContainer MuiPickersCalendar-transitionContainer')]/div/div/div/button/span/p[contains(text(),'" + date + "')]"));
    }

    public WebElement getDropdownSelectedValue(String id) {
        return Driver.getDriver().findElement(By.id("mui-component-select-" + id));
    }

    public WebElement getCheckBoxSelectedValue(String id) {
        return Driver.getDriver().findElement(By.id(id));
    }

    @FindBy(xpath = "//*[contains(text(),'CHANNELS FOR DEFINITION')]/../following-sibling::div/a/div/div/div/following-sibling::div")
    public List<WebElement> listOfChannels;

    @FindBy(xpath = "//*[contains(@class, 'text-right mx-header-userid')]")
    public WebElement headerID;

    @FindBy(xpath = "//p[text()='CREATED BY']/following-sibling::h6")
    public WebElement createdByValue;

    @FindBy(xpath = "//p[text()='CREATED ON']/following-sibling::h6")
    public WebElement createdOnValue;

    @FindBy(id = "mui-component-select-selectChannel")
    public WebElement selectChannel;

    @FindBy(xpath = "//*[contains(text(),'keyboard_backspace')]/../..")
    public WebElement backButton;


    @FindBy(xpath = "(//label[contains(text(),'Start Date')]/following-sibling::div/input)[last()]")
    public WebElement templatestartDate;

    @FindBy(xpath = "(//label[contains(text(),'End Date')]/following-sibling::div/input)[last()]")
    public WebElement templateendDate;

}
