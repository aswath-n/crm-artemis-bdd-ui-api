package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OutboundCorrespondenceDefinitionPage {
    

    public OutboundCorrespondenceDefinitionPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//*[contains(text(),'OUTBOUND CORRESPONDENCE DEFINITION DETAILS')]")
    public WebElement outboundCorrespondenceHeader;

    @FindBy(xpath = "//*[contains(text(),'report_problem')]/..")
    public WebElement outboundCorrespondenceMessage;

    @FindBy(xpath = "//input[@name='cdfID']")
    public WebElement Id;

    @FindBy(xpath = "//input[@name='cdfName']")
    public WebElement name;

    @FindBy(xpath = "//input[@name='cdfDescription']")
    public WebElement description;

    @FindBy(xpath = "//label[contains(text(),'Start Date')]/following-sibling::div/input")
    public WebElement startDate;

   // @FindBy(xpath = "//label[contains(text(),'Start Date')]/following-sibling::div/input/following-sibling::div")
    @FindBy(xpath = "(//*[@class='MuiSvgIcon-root'])[1]")
    public WebElement startDateCalendar;

    @FindBy(xpath = "//span[contains(text(),'Clear')]")
    public WebElement startDateCalendarClear;

    @FindBy(xpath = "//label[contains(text(),'End Date')]/following-sibling::div/input")
    public WebElement endDate;

    @FindBy(xpath = "//label[contains(text(),'End Date')]/following-sibling::div/input/following-sibling::div")
    public WebElement endDateCalendar;

    @FindBy(xpath = "//span[contains(text(),'Clear')]")
    public WebElement endDateCalendarClear;

    @FindBy(xpath = "//label[contains(text(),'End Reason')]/following-sibling::div/input")
    public WebElement endReason;

    @FindBy(id = "userMayRequest")
    public WebElement userMayRequest;

    @FindBy(id = "approvalRequired")
    public WebElement approvalRequired;

    @FindBy(id = "userMayEnter")
    public WebElement userMayEnter;

    @FindBy(xpath = "//label[.='Required Keys']/..//div//div//div/div[@type='text']")
    public WebElement requiredKeys;

    @FindBy(xpath = "(//div[@class='css-16pqwjk-indicatorContainer'])[1]")
    public WebElement deleteAllRequiredKeys;

    @FindBy(xpath = "//i[@class='material-icons']")
    public List<WebElement> listOfDeleteIconBodyElements;

    @FindBy(xpath = "//span[contains(text(), ' ADD DATA ELEMENT')]")
    public WebElement addDataElement;

    @FindBy(id = "field-name")
    public WebElement dataElementFieldName;

    @FindBy(id = "field-label")
    public WebElement dataElementFieldLabel;

    @FindBy(id = "demo-simple-select-required-label")
    public WebElement dataElementFieldType;

    @FindBy(id = "required")
    public WebElement dataElementRequired;

    @FindBy(id = "allowMultiple")
    public WebElement dataElementAllowMultiple;

    @FindBy(xpath = "//input[@name='cdfStateID']")
    public WebElement stateId;

    @FindBy(xpath = "//input[@name='cdfBodyData']")
    public WebElement bodyDataSource;

    @FindBy(xpath = "//span[contains(text(),'Save')]")
    public WebElement saveButon;

    @FindBy(xpath = "//*[contains(text(),'ADD CHANNEL')]")
    public WebElement addChannel;

    @FindBy(xpath = "//*[contains(text(),'ERROR MESSAGE')]")
    public WebElement failMessage;

    @FindBy(xpath = "//*[contains(text(),'The ID is required and cannot be left blank.')]")
    public WebElement idFailMessage;

    @FindBy(xpath = "//p[contains(text(),'If you navigate away, your information will not be saved')]")
    public WebElement discardFailMessage;

    @FindBy(xpath = "//*[contains(text(),'The Name is required and cannot be left blank.')]")
    public WebElement nameFailMessage;

    @FindBy(xpath = "//*[contains(text(),'The Description is required and cannot be left blank.')]")
    public WebElement descriptionFailMessage;

    @FindBy(xpath = "//*[contains(text(),'START DATE is required and cannot be left blank.')]")
    public WebElement startDateFailMessage;

    @FindBy(xpath = "//*[contains(text(),'START DATE cannot be changed to a date before today.')]")
    public WebElement startDateMessage;

    @FindBy(xpath = "//*[contains(text(),'END DATE cannot be earlier than START DATE.')]")
    public WebElement endDateMessage;

    @FindBy(xpath = "//span[contains(text(),'keyboard_backspace')]/../..")
    public WebElement backButton;

    @FindBy(xpath = "//*[contains(text(),'SUCCESS MESSAGE')]")
    public WebElement savePopUp;

    @FindBy(xpath = "//*[contains(text(),'CREATED BY')]")
    public WebElement creationByHeader;

    @FindBy(xpath = "//*[contains(text(),'CREATED ON')]")
    public WebElement creationOnHeader;

    @FindBy(xpath = "//*[contains(text(),'UPDATED ON')]")
    public WebElement udpatedOnHeader;

    @FindBy(xpath = "//*[contains(text(),'UPDATED BY')]")
    public WebElement updatedByHeader;

    @FindBy(xpath = "//*[contains(text(),'CREATED BY')]/..")
    public WebElement creationByUser;

    @FindBy(xpath = "//*[contains(text(),'CREATED ON')]/..")
    public WebElement creationOnDate;

    @FindBy(xpath = "//*[contains(text(),'UPDATED BY')]/..")
    public WebElement updatedByUser;

    @FindBy(xpath = "//*[contains(text(),'UPDATED ON')]/..")
    public WebElement updatedOnDate;

    @FindBy(xpath = "//*[contains(text(),'END DATE cannot be earlier than START DATE.')]/..")
    public WebElement endDateCannotBePrior;

    @FindBy(xpath = "//*[contains(text(),'END REASON is required when END DATE is entered')]/..")
    public WebElement endReasonRequired;

    @FindBy(xpath = "//*[contains(text(),'No Records Available')]")
    public WebElement noChannels;

    @FindBy(xpath = "//div[@role='button']")
    public WebElement inboundCorrespondenceDropdown;

    public WebElement getDropdownOption(String text) {
        return Driver.getDriver().findElement(By.xpath("//li[text()='" + text + "']"));
    }

    @FindBy(xpath = "//*[contains(text(),'Save')]")
    public WebElement saveButton;

    @FindBy(xpath = "//*[contains(text(),'Inbound Correspondence Type cannot be left')]")
    public WebElement inboundCorrTypeWarningMessage;

    @FindBy(xpath = "//*[.='Mail']")
    public List<WebElement> mail;

    @FindBy(xpath = "//*[.='Mail']/../../div/h6")
    public WebElement status;

}
