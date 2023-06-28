package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OutboundCorrespondenceSettingsPage {
    

    public OutboundCorrespondenceSettingsPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//input[@name='selectDefaultLang']/preceding-sibling::div")
    public WebElement defaultLanguage;

    @FindBy(xpath = "//input[@name='selectDefaultLang']")
    public WebElement defaultLanguageInputValue;

    @FindBy(xpath = "//*[contains(text(),'LANGUAGES')]")
    public WebElement languagesHeader;

    @FindBy(id = "_Select Other Language(s)")
    public WebElement otherLanguages;

    @FindBy(xpath = "//*[text()='Select Other Language(s)']/../div//div/span")
    public List<WebElement> otherLanguagesSelectedValues;

    @FindBy(xpath = "//div[@type='text']/div/div/div/input")
    public WebElement otherLanguagesInput;

    @FindBy(xpath = "//*[text()='Select Other Language(s)']/../div//div//button")
    public WebElement otherLanguageDelete;

    @FindBy(xpath = "//div[@role='menuitem' and text()='Spanish']")
    public WebElement otherLanguageSpanish;

    @FindBy(xpath = "//div[@role='menuitem' and text()='English']")
    public WebElement otherLanguageEnglish;

    @FindBy(xpath = "//input[@name='vendorCompanyName']")
    public WebElement vendorCompanyName;

    @FindBy(xpath = "//input[@name='vendorContactName']")
    public WebElement vendorContactName;

    @FindBy(xpath = "//input[@name='vendorPhone']")
    public WebElement vendorPhone;

    @FindBy(xpath = "//input[@name='vendorEmail']")
    public WebElement vendorEmail;

    @FindBy(xpath = "//input[@name='outboundCorrespondence']")
    public WebElement outboundCorrespondence;

    @FindBy(xpath = "//input[@name='outboundCorrespondenceTemplate']")
    public WebElement outboundCorrespondenceTemplate;

    @FindBy(xpath = "//input[@name='fileFormat']/preceding-sibling::div")
    public WebElement outboundFileFormat;

    @FindBy(xpath = "//h5[contains(text(),'OUTBOUND PRINT FILE FTP DETAILS')]/../following-sibling::div//input[@name='host']")
    public WebElement ftpHost;

    @FindBy(xpath = "//h5[contains(text(),'OUTBOUND PRINT FILE FTP DETAILS')]/../following-sibling::div//input[@name='user']")
    public WebElement ftpUser;

    @FindBy(xpath = "//h5[contains(text(),'OUTBOUND PRINT FILE FTP DETAILS')]/../following-sibling::div//input[@name='password']")
    public WebElement ftpPassword;

    @FindBy(xpath = "//h5[contains(text(),'OUTBOUND PRINT FILE FTP DETAILS')]/../following-sibling::div//input[@name='port']")
    public WebElement ftpPort;

    @FindBy(xpath = "//h5[contains(text(),'OUTBOUND PRINT FILE FTP DETAILS')]/../following-sibling::div//input[@name='ftpFolder']")
    public WebElement ftpFolder;

    @FindBy(xpath = "//h5[contains(text(),'INBOUND FILE PATH')]/../following-sibling::div//input[@name='inboundResFilePath']")
    public WebElement inboundFilePathResponse;

    @FindBy(xpath = "//h5[contains(text(),'INBOUND FILE PATH')]/../following-sibling::div//input[@name='inboundPdfPath']")
    public WebElement inboundFilePathPDF;

    @FindBy(xpath = "//h5[contains(text(),'OUTBOUND EMAIL SMTP DETAILS')]/../following-sibling::div//input[@name='host']")
    public WebElement smtpHost;

    @FindBy(xpath = "//h5[contains(text(),'OUTBOUND EMAIL SMTP DETAILS')]/../following-sibling::div//input[@name='user']")
    public WebElement smtpUser;

    @FindBy(xpath = "//h5[contains(text(),'OUTBOUND EMAIL SMTP DETAILS')]/../following-sibling::div//input[@name='password']")
    public WebElement smtpPassword;

    @FindBy(xpath = "//h5[contains(text(),'OUTBOUND EMAIL SMTP DETAILS')]/../following-sibling::div//input[@name='port']")
    public WebElement smtpPort;

    @FindBy(xpath = "//h5[contains(text(),'OUTBOUND EMAIL SMTP DETAILS')]/../following-sibling::div//input[@name='senderEmailAddress']")
    public WebElement smtpSenderEmail;

    @FindBy(xpath = "//h5[contains(text(),'OUTBOUND EMAIL SMTP DETAILS')]/../following-sibling::div//input[@name='senderEmailName']")
    public WebElement smtpSenderEmailName;

    @FindBy(xpath = "//button/span/i[.='check']")
    public WebElement saveButton;

    @FindBy(xpath = "//button/span/i[.='clear']")
    public WebElement cancelButton;

    @FindBy(xpath = "//input[@type='checkbox' and @id='Email']")
    public WebElement emailChannel;

    @FindBy(xpath = "//input[@type='checkbox' and @id='Fax']")
    public WebElement faxChannel;

    @FindBy(xpath = "//input[@type='checkbox' and @id='Text']")
    public WebElement textChannel;

    @FindBy(xpath = "//input[@type='checkbox' and @id='Mail']")
    public WebElement mailChannel;

    @FindBy(xpath = "//input[@type='checkbox' and @id='Web Portal']")
    public WebElement webPortalChannel;

    @FindBy(xpath = "//input[@type='checkbox' and @id='Mobile App']")
    public WebElement mobileAppChannel;

    @FindBy(xpath = "//*[.='Updated By']/following-sibling::h6")
    public WebElement updatedBy;

    @FindBy(xpath = "//*[.='Updated On']/following-sibling::h6")
    public WebElement updatedOn;

    @FindBy(xpath = "//span[text()='keyboard_backspace']")
    public WebElement backButton;

    @FindBy(xpath = "(//div[@role='menuitem'])[1]/..")
    public WebElement autoComplete;

    @FindBy(xpath = "//div[@role='presentation']")
    public WebElement presentationPopUp;

    @FindBy(xpath = "//span[contains(text(),'Edit Settings')]")
    public WebElement editSettingsButton;

    @FindBy(xpath = "//*[contains(text(),'INBOUND CORRESPONDENCE CONFIGURATION')]")
    public WebElement inboundSettingsHeader;

    @FindBy(xpath = "//*[contains(text(),'INBOUND CHANNELS')]")
    public WebElement inboundChannelHeader;

    @FindBy(xpath = "//*[contains(text(),'INBOUND DOCUMENT TYPES')]")
    public WebElement inboundDefaultTypeHeader;

    @FindBy(xpath = "//*[contains(text(),'edit')]/ancestor::button")
    public WebElement editInboundSettings;

    @FindBy(id = "Web Chat")
    public WebElement inboundWebChatCheckbox;

    @FindBy(id = "Mail")
    public WebElement inboundMailCheckbox;

    @FindBy(id = "Email")
    public WebElement inboundEmailCheckbox;

    @FindBy(id = "Text")
    public WebElement inboundTextCheckbox;

    @FindBy(id = "Fax")
    public WebElement inboundFaxCheckbox;

    @FindBy(id = "Mobile App")
    public WebElement inboundMobileAppCheckbox;

    @FindBy(id = "Web Portal")
    public WebElement inboundWebPortalCheckbox;

    public WebElement getInboundChannels(String channel) {
        return Driver.getDriver().findElement(By.xpath("//*[contains(text(),'" + channel + "')]"));
    }

    public List<WebElement> getChannelCheckboxes() {
        return Driver.getDriver().findElements(By.xpath("//input[@type='checkbox']"));
    }

    public WebElement getFailMessage(String message) {
        return Driver.getDriver().findElement(By.xpath("//*[contains(text(),'" + message + "')]"));
    }

    public List<WebElement> getOutboundfileFormats() {
        return Driver.getDriver().findElements(By.xpath("//ul[@role='listbox']/li"));
    }

    public List<WebElement> getDefaultLanguages() {
        return Driver.getDriver().findElements(By.xpath("//ul[@role='listbox']/li"));
    }

    public WebElement getChannelLabel(String label) {
        return Driver.getDriver().findElement(By.xpath("//*[contains(text(),'" + label + "')]"));
    }
}
