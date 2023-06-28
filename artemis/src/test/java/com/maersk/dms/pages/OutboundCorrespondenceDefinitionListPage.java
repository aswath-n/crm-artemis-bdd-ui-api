package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OutboundCorrespondenceDefinitionListPage {
    

    public OutboundCorrespondenceDefinitionListPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//span[contains(text(),'add')]")
    public WebElement addCorrespondenceDefinition;

    @FindBy(xpath = "//*[contains(text(),'OUTBOUND CORRESPONDENCE DEFINITIONS')]")
    public WebElement header;

    @FindBy(xpath = "(//i[contains(text(),'arrow_right_alt')])[1]")
    public WebElement blcrmProj;

    @FindBy(xpath = "//span[contains(text(),'arrow_forward')]")
    public WebElement arrowForward;

    @FindBy(xpath = "//span[contains(text(),'arrow_back')]")
    public WebElement arrowBack;

    @FindBy(xpath = "//span[@role='button' and contains(text(),'ID')]")
    public WebElement sortById;

    @FindBy(xpath = "//span[@role='button' and contains(text(),'NAME')]")
    public WebElement sortByName;

    @FindBy(xpath = "//span[@role='button' and contains(text(),'STATUS')]")
    public WebElement sortByStatus;

    @FindBy(xpath = "//*[contains(text(),'CORPORATE HEADQUARTERS')]")
    public WebElement bottomHeader;

    @FindBy(xpath = "//span[contains(text(),'check')]/..")
    public WebElement discardPopUp;

    @FindBy(xpath = "//*[contains(text(),'If you navigate away, your information will not be saved')]")
    public WebElement discardPopUpMessage;

    @FindBy(xpath = "//*[contains(text(),'Your Correspondence Channel will not be saved')]")
    public WebElement cancelPopUpMessage;

    @FindBy(xpath = "//*[contains(text(),'No Records Available')]")
    public WebElement noRecordsMessage;

    public WebElement selectName(String name) {
        return Driver.getDriver().findElement(By.xpath("//*[contains(text(),'" + name + "')]"));
    }

    public List<WebElement> idColumn() {
        return Driver.getDriver().findElements(By.xpath("//tbody//td/a"));
    }

    public List<WebElement> nameColumn() {
        return Driver.getDriver().findElements(By.xpath("//tbody//td[2]"));
    }

    public List<WebElement> statusColumn() {
        return Driver.getDriver().findElements(By.xpath("//tbody//td[3]"));
    }


    public WebElement pageNumber(String page) {
        return Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[contains(text(),'" + page + "')]"));
    }

    @FindBy(xpath = "//ul[@class='pagination']/li")
    public List<WebElement> listOfPages;

    @FindBy(xpath = "//label[text()='Inbound Correspondence Type']/span[text()='*']")
    public WebElement inboundcorrtypenonmandatory;

    @FindBy(xpath = "//div[@id='mui-component-select-inboundCorresType']")
    public WebElement typedropdown;

    @FindBy(xpath = "//ul[@class='MuiList-root MuiMenu-list MuiList-padding']/li")
    public List<WebElement> typedropdownvalues;

    @FindBy(xpath = "//div[@id='mui-component-select-inCorrespondenceType']")
    public WebElement ibtypedropdown;

    @FindBy(xpath = "//ul[@class='MuiList-root MuiMenu-list MuiList-padding']/li")
    public List<WebElement> ibtypedropdownvalues;

    @FindBy(xpath = "//div[@id='mui-component-select-ecmsDynamicLinks']")
    public WebElement itemsperpage;

    @FindBy(xpath = "//span[contains(text(),'ADD TEMPLATE')]")
    public WebElement templateButton;

    @FindBy(xpath = "//ul[@class='dropdown-menu show']/li/label")
    public List<WebElement> addtemplatedownvalues;

    @FindBy(xpath = "//input[@id='file-attach']")
    public WebElement templateFileSelect;

    @FindBy(xpath = "(//p[contains(text(),'Template File Name')]/following-sibling::h6)[last()]")
    public WebElement templateFileName;

    @FindBy(xpath = "(//p[contains(text(),'System')]/following-sibling::p)[last()]")
    public WebElement templateSystem;

    @FindBy(xpath = "(//p[contains(text(),'External Id')]/following-sibling::p)[last()]")
    public WebElement templateExternalId;

    @FindBy(xpath = "(//p[contains(text(),'Version')]/following-sibling::p)[last()]")
    public WebElement templateVersion;

    @FindBy(xpath = "(//div[@id='mui-component-select-language'])[last()]")
    public WebElement templateLanguage;

    @FindBy(xpath = "(//*[contains(text(),'download')])[last()]")
    public WebElement templatedownload;

    @FindBy(xpath = "(//*[contains(text(),'delete')])[last()]")
    public WebElement templatedelete;

    @FindBy(xpath = "//div[@id='demo-select-small']")
    public WebElement externalidtemplateSystem;

    @FindBy(xpath = "//input[@id='outlined-basic']")
    public WebElement externalid;

    @FindBy(xpath = "(//i[contains(text(),'add')])[2]")
    public WebElement externalidadd;

    @FindBy(xpath = "(//i[contains(text(),'clear')])[1]")
    public WebElement externalidcancel;

    @FindBy(xpath = "//p[contains(text(),'The System is required and cannot be left blank.')]")
    public WebElement externalidSystemReqmsg;

    @FindBy(xpath = "//p[contains(text(),'The External Template Id is required and cannot be')]")
    public WebElement externaltemplateidReqmsg;

}
