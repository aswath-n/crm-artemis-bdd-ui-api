package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CRM_Edit_ContactRecord_Page extends BrowserUtils {

    


    public CRM_Edit_ContactRecord_Page() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(id = "mui-component-select-contactReasonEditType")
    public WebElement reasonForEdit;

    @FindBy(xpath = "//div[2]/div[3]/div[1]/div[1]/div[2]")
    public WebElement frstaddedContactReason;

    @FindBy(xpath = "//div[3]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[4]/button[1]")
    public WebElement editButtonAddedConcatReason;

    @FindBy(xpath = "(//p[@class='my-0'])[1]")
    public WebElement preContactReason;

    @FindBy(id = "mui-component-select-contactAction-19004")
    public WebElement addNewAction;

    @FindBy(id = "mui-component-select-contactReason-19004")
    public WebElement chooseReason;

    @FindBy(xpath = "//div[3]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[4]/button[1]")
    public WebElement frstaddedCRSavebutton;

    @FindBy(xpath = "//tr[1]//td[4]")
    public WebElement reasonForEditInHistory;

    @FindBy(xpath = "//tr[1]//td[6]")
    public WebElement previousReasonAction;

    @FindBy(xpath = "//tr[1]//td[7]")
    public WebElement updatedReasonAction;

    @FindBy(xpath = "//span[contains(text(),'CONTACT DETAILS')]")
    public WebElement contactDetails;

    @FindBy(xpath = "//i[text()='edit']")
    public WebElement editButton;

    @FindBy(xpath = "//span[contains(text(),'Save')]")
    public WebElement editSave;

    @FindBy(xpath = "//p[contains(text(),'Change Request')]")
    public WebElement medChatReason;

    @FindBy(xpath = "//p[contains(text(),'Escalated to CPU')]")
    public WebElement medChatReasonAction;

    @FindBy(xpath = "//p[contains(text(),'testing commenting')]")
    public WebElement medChatAdditionalComment;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/div/div/div[2]/div/div/div[2]/div[1]/div/div[1]/div[1]")
    public WebElement expandReasonAction;

    @FindBy(id = "mui-component-select-electronicSignature")
    public WebElement elecSignCheckBox;

    @FindBy(xpath = "//p[contains(text(),'ELECTRONIC SIGNATURE CAPTURED')]")
    public WebElement elecSignCapturedTxt;

    @FindBy(xpath = "//p[contains(text(),'Electronic Signature is Required')]")
    public WebElement elecSignErrorTxt;

    @FindBy(name = "consumerId")
    public WebElement consumerIdSearch;

    @FindBy(xpath = "//tbody[1]/tr[1]/td[1]/span")
    public WebElement consumerId;

    @FindBy(xpath = "//tbody/tr[1]/td[3]/a[1]")
    public WebElement frstRecord;

    @FindBy(xpath = "//tbody/tr[1]/td[7]")
    public WebElement consumerName;

    @FindBy(name = "electronicSignature")
    public WebElement elSigcheckboxEditPage;

    @FindBy(xpath = "//p[contains(text(),'CLAIM ID')]")
    public WebElement claimIDField;

    @FindBy(xpath = "//li[contains(text(),'Yes')]")
    public WebElement elecSignYes;

    @FindBy(xpath = "//span[contains(text(), 'ADVANCED SEARCH')]")
    public WebElement advancedSearch;

    @FindBy(id="phoneNumber")
    public WebElement phoneNumber;

    @FindBy(xpath = "//div[1]/div[8]/h6[1]")
    public WebElement viewElSignConRecord;

    @FindBy(xpath = "//div[1]/div[9]/h6[1]")
    public WebElement viewElSignConRecordNew;

    @FindBy(xpath = "//div[1]/div[9]/h6[1]")
    public WebElement electronicSignCaptured;

    @FindBy(xpath = "//p[contains(text(),'Electronic Signature Captured')]/following-sibling::*")
    public WebElement viewElSignOption;

    @FindBy(id="mui-component-select-contactTranslationService")
    public WebElement tanslationService;

    @FindBy(xpath = "//th[contains(text(),'REASON FOR EDIT')]")
    public WebElement contactEditReasonEdit;

    public WebElement errorMessage (String message) {
        waitFor(1);
        return Driver.getDriver().findElement(By.xpath("//span[contains(text(),'"+message+"')]"));
    }

    @FindBy(xpath = "//div[3]/div[1]/div[1]/div[1]/div[1]/span[1]")
    public WebElement errorMessagePop;

    @FindBy(xpath = "//td[contains(text(),'Application ID')]")
    public WebElement applicationIDField;

    @FindBy(xpath = "//button[@title=' Edit Reason']")
    public WebElement editReason;

    @FindBy(xpath = "//button[@title=' Save Edit Reason']")
    public WebElement saveEditReason;

    @FindBy(xpath = "(//div[@id='mui-component-select-contactAction'])[2]")
    public WebElement contactEditReasonDd;

    public WebElement updatedAppIdField (String appId) {
        waitFor(1);
        return Driver.getDriver().findElement(By.xpath("//td[contains(text(),'"+appId+"')]"));
    }

    public WebElement reasonForEditField (String reason) {
        waitFor(1);
        return Driver.getDriver().findElement(By.xpath("//*[contains(text(),'"+reason+"')]"));
    }
}
