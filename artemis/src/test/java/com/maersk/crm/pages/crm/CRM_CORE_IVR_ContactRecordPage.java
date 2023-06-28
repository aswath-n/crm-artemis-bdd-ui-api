package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CRM_CORE_IVR_ContactRecordPage {

    

    public CRM_CORE_IVR_ContactRecordPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//h6[contains(text(), 'Self Service')]")
    public WebElement selfServiceContactType;

    @FindBy(xpath = "//h6[contains(text(), 'IVR')]")
    public WebElement channelIVR;

    @FindBy(xpath = "//h6[contains(text(), 'Pending IVR Self Service Data')]")
    public WebElement IVRdisposition;

    public WebElement ivr_phone (String phonenumber) {
        return Driver.getDriver().findElement(By.xpath("//h6[contains(text(),'"+phonenumber+"')]"));
    }

    @FindBy(xpath = "//h6[contains(text(), 'a2adf64-a325ad45-asd45asf')]")
    public WebElement interactionID;

    public WebElement consumerName (String consumerName) {
        return Driver.getDriver().findElement(By.xpath("//td[contains(text(),'"+consumerName+"')]"));
    }

    @FindBy(xpath = "//p[contains(text(), 'IVR Authentication')]")
    public WebElement contactReason;

    @FindBy(xpath = "//p[contains(text(), 'Application Id: T12344455, VaCMS Consumer ID: 43251235123521, VaCMS Case ID: 43251235123521')]")
    public WebElement additionalComments;

    @FindBy(xpath = "//h6[contains(text(), '09/03/2021 | 07:50 AM')]")
    public WebElement contactStartTime;

    @FindBy(id = "mui-component-select-businessUnitSelect")
    public WebElement businessUnit;

    @FindBy(xpath = "(//div//table//tbody//tr//td[@class='mdl-data-table__cell--non-numeric  mx-link'])[1]/span")
    public WebElement VaCMSconsumerID;

    @FindBy(xpath = "//td[contains(text(),'VaCMS')]/..//td[2]")
    public WebElement vacmsID;

    @FindBy(xpath = "//td[contains(text(),'MMIS')]/..//td[2]")
    public WebElement mmisID;

    public WebElement consumerNameDemogPage (String consumerName) {
        return Driver.getDriver().findElement(By.xpath("//p[contains(text(),'"+consumerName+"')]"));
    }

    @FindBy(xpath = "//i[@class = 'material-icons text-center mdl-color-text--green-600 mx-icon mx-ivr']")
    public WebElement ivrContactRecordIcon;

    @FindBy(xpath = "//p[contains(text(), 'CREATED BY USER NAME')]")
    public WebElement createdByUserName;

    @FindBy(xpath = "//p[contains(text(), 'CREATED BY USER ID')]")
    public WebElement createdByUserId;

    @FindBy(xpath = "//p[contains(text(), 'RECEIVED BY USER NAME')]")
    public WebElement receivedByUserName;

    @FindBy(xpath = "//p[contains(text(), 'RECEIVED BY USER ID')]")
    public WebElement receivedByUserId;

    @FindBy(xpath = "//h6[contains(text(), 'System')]")
    public WebElement createdByUserNameSystem;

    @FindBy(xpath = "(//h6[@class='m-0 '])[1]")
    public WebElement createdByUserNameValue;

    @FindBy(xpath = "(//h6[@class='m-0 '])[2]")
    public WebElement createdByUserIdValue;

    @FindBy(xpath = "(//h6[@class='m-0 '])[3]")
    public WebElement receivedByUserNameValue;

    @FindBy(xpath = "(//h6[@class='m-0 '])[4]")
    public WebElement receivedByUserIdValue;


}
