package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMTSRTabForSRPage {

    

    public CRMTSRTabForSRPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//*[contains(text(),'SERVICE REQUEST LIST')]/..//table/tbody/tr")
    public List<WebElement> srListRows;

    @FindBy(xpath = "//h5[text()='SERVICE REQUEST LIST']/..//table/tbody/tr//td[3]")
    public List<WebElement> srTypes;

    @FindBy(xpath = "//h5[text()='SERVICE REQUEST LIST']/..//table/tbody/tr//td[5]")
    public List<WebElement> srStatuses;

    @FindBy(xpath = "//h5[text()='SERVICE REQUEST LIST']/..//table/tbody/tr//td[9]")
    public List<WebElement> srInitiateButtonsText;

    @FindBy(xpath = "//*[text()='SR ID']")
    public WebElement columnSRId;

    @FindBy(xpath = "//h5[text()='SERVICE REQUEST LIST']/..//table/tbody/tr//td[2]")
    public List<WebElement> srIDs;

    @FindBy(xpath = "//span[text()='arrow_back']/parent::a")
    public WebElement lnkArrowBack;

    @FindBy(xpath = "(//span[text()='arrow_forward']/parent::a)[2]")
    public WebElement lnkArrowForward;

    @FindBy(xpath = "(//ul[@class='pagination'])[2]//li")
    public List<WebElement> lnkPageNations;

    @FindBy(xpath = "//h5[text()='SERVICE REQUEST LIST']/..//*[text()='TYPE']")
    public WebElement srtypeColumn;

    @FindBy(xpath = "//h5[text()='SERVICE REQUEST LIST']/..//*[text()='PRIORITY']")
    public WebElement srpriorityColumn;

    @FindBy(xpath = "//h5[text()='SERVICE REQUEST LIST']/..//*[text()='STATUS']")
    public WebElement srstatusColumn;

    @FindBy(xpath = "//h5[text()='SERVICE REQUEST LIST']/..//*[text()='STATUS DATE']")
    public WebElement srStatusDateColumn;

    @FindBy(xpath = "//h5[text()='SERVICE REQUEST LIST']/..//*[text()='CONSUMER NAME']")
    public WebElement srconsumerNameColumn;

    @FindBy(xpath = "//h5[text()='SERVICE REQUEST LIST']/..//table/tbody/tr/td[4]")
    public List<WebElement> srpriorities;

    @FindBy(xpath = "//h5[text()='SERVICE REQUEST LIST']/..//table/tbody/tr/td[6]")
    public List<WebElement> srStatusDate;

    @FindBy(xpath = "//h5[text()='SERVICE REQUEST LIST']/..//table/tbody/tr/td[7]")
    public List<WebElement> srconsumerNames;

    @FindBy(xpath = "//h5[text()='SERVICE REQUEST LIST']")
    public WebElement srListLabel;

    @FindBy(xpath = "//h5[text()='SERVICE REQUEST LIST']/..//table/tbody/tr[1]/td[1]")
    public List<WebElement> expandSRArrows;

    @FindBy(xpath = "//h5[contains(text(), 'SR ID')]")
    public WebElement lblSRId;

    @FindBy(xpath = "//span[text()='EDIT SERVICE REQUEST']")
    public WebElement btnEditSR;

    @FindBy(xpath = "//h5[text()='SERVICE REQUEST LIST']/..//p[text()='CREATED BY']/following-sibling::p")
    public List<WebElement> createdBySRValue;

    @FindBy(xpath = "//h5[text()='SERVICE REQUEST LIST']/..//p[text()='SR INFORMATION']/following-sibling::p")
    public List<WebElement> srInfoValue;

    @FindBy(xpath = "//h5[text()='SERVICE REQUEST LIST']/..//p[text()='CREATED ON']/following-sibling::p")
    public List<WebElement> createdOnSR;

    @FindBy(xpath = "//h5[text()='SERVICE REQUEST LIST']/..//p[text()='CASE ID']/following-sibling::p")
    public List<WebElement> caseIDValue;

    @FindBy(xpath = "//*[text()='keyboard_backspace']")
    public WebElement backarrow;
}
