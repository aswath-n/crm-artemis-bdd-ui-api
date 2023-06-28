package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMUploadAlertPage {

    

    public CRMUploadAlertPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//*[@data-route='/mars/crm/case-consumer']")
    public WebElement alertIcon;

    @FindBy(xpath = "//*[text()='CREATE ALERT']")
    public WebElement alertComponentHeader;

    @FindBy(xpath = "//input[@id='fileSelect']")
    public WebElement alertFileSelect;

    @FindBy(xpath = "//*[text()='Upload']")
    public WebElement alertFileUploadBtn;

    @FindBy(xpath = "//*[text()='Upload and Validate']")
    public WebElement alertFileUploadAndValidateBtn;

    @FindBy(xpath = "//*[text()=' CANCEL ']")
    public WebElement alertFileCancel;

    @FindBy(xpath = "//*[text()='DOWNLOAD ALERT template']")
    public WebElement alertTemplate;

    @FindBy(xpath = "//*[text()=' RECENTLY CREATED']")
    public WebElement recentlyCreatedTab;

    @FindBy(xpath = "//*[text()='UPLOAD HISTORY']")
    public WebElement uploadHistoryComponent;

    @FindBy(xpath = "//*[text()='RECENTLY CREATED ALERT(S)']")
    public WebElement recentlyCreatedComponent;

    @FindBy(xpath = "//*[text()='DOWNLOAD ALERT template']")
    public WebElement downloadAlertTemplate;

    @FindBy(xpath = "//*[text()='FILE NAME']")
    public WebElement fileNameColumn;

    @FindBy(xpath = "//*[text()='ALERT ID']")
    public WebElement alertIdColumn;

    @FindBy(xpath = "//*[text()='DATE/TIME UPLOADED']")
    public WebElement dateTimeUploaded;

    @FindBy(xpath = "//*[text()='CASE/ CONSUMER ID']")
    public WebElement caseAndConsumerId;

    @FindBy(xpath = "//*[text()='UPLOADED BY']")
    public WebElement uploadedBy;

    @FindBy(xpath = "//*[text()='ALERT']")
    public WebElement alertTxtColumn;

    @FindBy(xpath = "//*[text()='DOWNLOAD SUCCESSFUL ALERTS']")
    public WebElement downloadSuccAlert;

    @FindBy(xpath = "//*[text()='START DATE']")
    public WebElement startDate;

    @FindBy(xpath = "//*[text()='DOWNLOAD FAILURE ALERTS']")
    public WebElement downloadFailAlert;

    @FindBy(xpath = "//*[text()='END DATE']")
    public WebElement endDate;

    @FindBy(xpath = "//*[text()='TYPE']")
    public WebElement typeTxt;

    @FindBy(xpath = "//*[text()='Reset']")
    public WebElement resetBtn;

    @FindBy(xpath = "//*[text()='Select Filter']")
    public WebElement selectFilter;

    @FindBy(xpath = "//*[contains(@id ,'processDateRange')]")
    public WebElement dateRangeDropdown;

    @FindBy(xpath = "//*[@id=\"menu-\"]/div[3]/ul/li[7]")
    public WebElement selectDateRangeOption;

    @FindBy(xpath = "//*[text()='Start Date']")
    public WebElement startDateTxt;

    @FindBy(xpath = "//*[text()='End Date']")
    public WebElement endDateTxt;

    @FindBy(xpath = "//*[text()='UPLOADED BY']")
    public WebElement uploadedByText;

    @FindBy(xpath = "//div[2]/div/div/input")
    public WebElement uploadedByInput;

    @FindBy(xpath = "//table/tbody/tr[1]/td[3]")
    public WebElement uploadedByFilter;

    @FindBy(xpath = "//div[@id='menu-dateRangeDropdown']//ul/li")
    public List<WebElement> getDateRangeDropdownValues;

    @FindBy(xpath = "//div[@id='menu-']//ul/li")
    public List<WebElement> getDateRangeDropdownValuesHistory;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'MuiTableBody-root')]//tr[*]/td[1]")
    })
    public List<WebElement> fileNames;
    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'MuiTableBody-root')]//tr[*]/td[2]")
    })
    public List<WebElement> dateTimeUploads;
    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'MuiTableBody-root')]//tr[*]/td[3]")
    })
    public List<WebElement> uploadedBys;
    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'MuiTableBody-root')]//tr[*]/td[4]")
    })
    public List<WebElement> downloadSuccAlertButtons;
    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'MuiTableBody-root')]//tr[*]/td[5]")
    })
    public List<WebElement> downloadFailedAlertButtons;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'mdl-button mdl-js-button mdl-button--icon mx-btn-secondary')]")
    })
    public List<WebElement> downloadSuccorFailedAlertButtons;

    @FindBy(xpath = "//*[text()='FileUploaded succesfully']")
    public WebElement succesfullyFileUploadedMessage;

    @FindBy(xpath = "(//*[contains(@class ,'MuiTableBody-root')]//tr[*]/td[4])[1]/button")
    public WebElement firstAlertDownloadBntOnAlertUploadPage;



}
