package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class InboundCorrespondenceDefinitionPage {
    

    public InboundCorrespondenceDefinitionPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//*[text()='INBOUND CORRESPONDENCE DEFINITIONS DETAILS']") //remove S when fixed
    public WebElement inboundCorrespondenceHeader;

    @FindBy(xpath = "//*[contains(text(),'INBOUND CORRESPONDENCE DEFINITIONS')]")
    public WebElement inboundCorrespondencedefinitionheader;

    @FindBy(css = "[name=name]")
    public WebElement name;

    @FindBy(css = "[name=description]")
    public WebElement description;

    @FindBy(css = "[name=barCodeNumber]")
    public WebElement barcode;

    @FindBy(xpath = "//i[contains(text(),'check')]")
    public WebElement saveButton;

    @FindBy(xpath = "//span[contains(text(),'Inbound Correspondence definition successfully created')]")
    public WebElement createdsuccessmsg;

    @FindBy(xpath = "//span[contains(text(),'Inbound Correspondence definition successfully updated')]")
    public WebElement updatesuccessmsg;

    @FindBy(xpath = "//i[contains(text(),'clear')]")
    public WebElement cancelButton;

    @FindBy(xpath = "//i[contains(text(),'add')]")
    public WebElement addRuleButton;

    @FindBy(xpath = "//span[contains(text(),'keyboard_backspace')]")
    public WebElement backButton;

    @FindBy(xpath = "//span[contains(text(),'Inbound Correspondence definition successfully created.')]")
    public WebElement savePopUp;

    @FindBy(xpath = "//span[contains(text(),'Name value must exactly match name in OnBase')]")
    public WebElement nameValueMustMatchInOnBaseWarningMessage;

    @FindBy(xpath = "//*[@id='mui-component-select-rank']")
   // @FindBy(xpath = "(//*[@id='mui-component-select-rank'])[last()]")
    public WebElement rank;

    @FindBy(xpath = "//*[@id='mui-component-select-taskTypeId']")
   // @FindBy(xpath = "(//*[@id='mui-component-select-taskTypeId'])[last()]")
    public WebElement tasktype;

    @FindBy(xpath = "//*[@id='userMayRequest']")
    //@FindBy(xpath = "(//*[@id='userMayRequest'])[last()]")
    public WebElement taskperset;

    @FindBy(xpath = "(//*[contains(@id,'react-select-')])[1]")
    //@FindBy(xpath = "(//*[contains(@id,'react-select-')])[last()-1]")
    public WebElement reqkeys;

    public WebElement selectreqkeysrecord(String reqkey) {
        return Driver.getDriver().findElement(By.xpath("//*[contains(@id,'react-select') and text()='"+reqkey+"']"));
    }

    @FindBy(xpath = "(//*[contains(@id,'react-select-')])[2]")
   // @FindBy(xpath = "(//*[contains(@id,'react-select-')])[last()]")
    public WebElement documentset;

    public WebElement selectdocumentset(String docset) {
        return Driver.getDriver().findElement(By.xpath("//div[contains(@id,'react-select') and text()='"+docset+"']"));
    }

    public List<WebElement> rankorders() {
        return Driver.getDriver().findElements(By.xpath("//*[@class='col-1']/h6"));
    }

    @FindBy(xpath = "//*[contains(text(),'The Rank is required and cannot be left blank')]")
    public WebElement rankreqmsg;

    @FindBy(xpath = "//*[contains(text(),'The Task Type is required and cannot be left blank')]")
    public WebElement tasktypereqmsg;

    public List<WebElement> listvalues() {
        return Driver.getDriver().findElements(By.xpath("//ul[@role='listbox']/li"));
    }

    public List<WebElement> listreqkeys() {
        return Driver.getDriver().findElements(By.xpath("//div[contains(@id,'react-select')]"));
    }

    public List<WebElement> listsiblingtypes() {
        return Driver.getDriver().findElements(By.xpath("//div[contains(@id,'react-select')]"));
    }

    public WebElement taskrule_values_display(String reqkey) {
        return Driver.getDriver().findElement(By.xpath("(//span[text()='"+reqkey+"'])[last()]"));
    }

    @FindBy(xpath = "//i[contains(text(),'edit')]")
    public WebElement editDefinitionButton;
}