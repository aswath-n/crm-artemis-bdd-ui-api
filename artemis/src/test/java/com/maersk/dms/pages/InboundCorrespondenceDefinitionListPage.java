package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class InboundCorrespondenceDefinitionListPage {
    

    public InboundCorrespondenceDefinitionListPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//span[contains(text(),'add')]")
    public WebElement addCorrespondenceDefinition;

    @FindBy(xpath = "//*[text()='INBOUND CORRESPONDENCE DEFINITIONS ']")
    public WebElement header;

    @FindBy(xpath = "//span[contains(text(),'arrow_back')]")
    public WebElement arrowBack;

    public WebElement selectName(String name) {
        return Driver.getDriver().findElement(By.xpath("//*[contains(text(),'" + name + "')]"));
    }

    public List<WebElement> nameColumn() {
        return Driver.getDriver().findElements(By.xpath("//tbody//td/a"));
    }

    public List<WebElement> barcodeColumn() {
        return Driver.getDriver().findElements(By.xpath("//tbody//td[2]"));
    }

    public List<WebElement> taskTypes() {
        return Driver.getDriver().findElements(By.xpath("//tbody//td[3]"));
    }

    public WebElement pageNumber(String page) {
        return Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[contains(text(),'" + page + "')]"));
    }

    @FindBy(xpath = "//ul[@class='pagination']/li")
    public List<WebElement> listOfPages;

    @FindBy(xpath = "//*[contains(text(),'ADD RULE')]")
    public WebElement addTaskRuleCorrespondenceDefinition;

    @FindBy(xpath = "//*[contains(text(),'EDIT DEFINITION')]")
    public WebElement editIBDefinition;

    public List<WebElement> pagenumbers() {
        return Driver.getDriver().findElements(By.xpath("//li/a"));
    }

    public WebElement selectpage(int pagenumber) {
        return Driver.getDriver().findElement(By.xpath("//a[@aria-label='Go to page number "+pagenumber+"']"));
    }

    @FindBy(xpath = "//div[@class='row']")
    public WebElement emptytaskrow;

    public List<WebElement> deletemultipletask() {
        return Driver.getDriver().findElements(By.xpath("//*[contains(text(),'close')]"));
    }

    @FindBy(xpath = "(//*[contains(text(),'close')])[last()]")
    public WebElement deleteTaskRuleCorrespondenceDefinition;

    @FindBy(xpath = "//input[@id='userMayRequest']")
    public WebElement createonlyonetaskperset;

    @FindBy(xpath = "//span[contains(text(),'keyboard_backspace')]")
    public WebElement arKeyboardBackSapce;

    @FindBy(xpath = "//span[contains(text(),'check')]")
    public WebElement warningbuttoncontinue;

    @FindBy(xpath = "//span[contains(text(),'clear')]")
    public WebElement warningbuttoncancel;

}