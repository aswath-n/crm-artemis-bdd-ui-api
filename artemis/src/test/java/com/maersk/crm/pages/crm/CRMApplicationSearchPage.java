package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMApplicationSearchPage {
    

    public CRMApplicationSearchPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//h2[@class='h4 mx-section-header float-left mb-3']")
    public WebElement searchTitle;

    @FindBy(id = "applicationID")
    public WebElement applicationID;

    @FindBy(id = "firstName")
    public WebElement firstName;

    @FindBy(id = "lastName")
    public WebElement lastName;

    @FindBy(id = "applicantDateOfBirth")
    public WebElement applicantDOB;

    @FindBy(id = "mui-component-select-status")
    public WebElement statusDropDown;

    @FindBy(id = "status")
    public WebElement statusDropDownInput;

    @FindBy(id = "mui-component-select-applicationCycle")
    public WebElement applicationCycle;

    @FindBy(id = "applicationCycle")
    public WebElement applicationCycleInput;

    @FindBy(id = "mui-component-select-program")
    public WebElement program;

    @FindBy(id = "program")
    public WebElement programInput;

    @FindBy(id = "mui-component-select-channel")
    public WebElement channel;

    @FindBy(id = "channel")
    public WebElement channelInput;

    @FindBy(id = "externalAppId")
    public WebElement externalAppIdInput;

    @FindBy(xpath = "//span[text() = 'search']")
    public WebElement searchBtn;

    @FindBy(xpath = "//span[contains(text(),'Cancel')]")
    public WebElement cancelBtn;

    @FindBy(xpath = "//table/tbody/tr")
    public List<WebElement> resultTableRowsTr;

    @FindBy(xpath = "//table/thead/tr/th/span")
    public List<WebElement> resultTableHeaders;

    @FindBy(xpath = "//h5[text()='No Search Results Found']")
    public WebElement noSrchResMsg;

    @FindBy(xpath = "//span[text()='arrow_forward']")
    public WebElement forwardArrow;

    @FindBy(id = "mui-component-select-itemsCountPerPage")
    public WebElement itemsCountPerPage;

    @FindBy(className = "pagination")
    public WebElement pagination;

    @FindBy(id = "mui-component-select-atsCommonGrid")
    public WebElement itemCountDD;

    @FindBy(css = "ul[role=listbox] > li")
    public List<WebElement> itemCountList;

    @FindBy(xpath = "//a[@class='nav-link' and contains(text(),'APPLICATION')]")
    public WebElement applicationTab;

    @FindBy(xpath = "//div//table/tbody/tr/td[2]")
    public List<WebElement> applicationIdColumn;

    @FindBy(xpath = "//p[contains(text(),'Invalid Format Text')]")
    public WebElement warningExternalAppId;

    @FindBy(xpath = "//table/tbody/tr/td[13]")
    public WebElement resultFirstExternalAppID;

    @FindBy(xpath = "//table/tbody/tr/td[3]")
    public WebElement resultFirstApplicationCode;

    @FindBy(xpath = "//label[.='CASE ID']")
    public WebElement caseIdLabel;

    @FindBy(id = "caseId")
    public WebElement caseIdInput;

    @FindBy(id = "applicationCode")
    public WebElement applicationCodeInput;

    @FindBy(xpath = "//label[.='CASE TYPE']")
    public WebElement caseTypeLabel;

    @FindBy(id = "caseType")
    public WebElement caseTypeInput;

    @FindBy(xpath = "//td[@class='MuiTableCell-root MuiTableCell-body mx-link'][1]")
    public WebElement resultFirstApplicationID;

    @FindBy(xpath = "//span[text()='Search results in excess of 200 results, please enter additional search criteria to limit search results']")
    public WebElement warningMsg;

    @FindBy(name = "applicationDateFrom")
    public WebElement applicationReceivedDateFrom;

    @FindBy(name = "applicationDateTo")
    public WebElement applicationReceivedDateTo;

    @FindBy(xpath = "//table/tbody/tr/td[9]")
    public WebElement resultFirstApplicationReceivedDate;

    @FindBy(xpath = "//table/tbody/tr/td[10]")
    public WebElement resultFirstApplicationDeadlineDate;

    @FindBy(xpath = "//*[text()='Date cannot be after applicationReceivedDateTo date']")
    public WebElement errorForAppReceivedDateFrom;

    @FindBy(xpath = "//*[text()='Date cannot be before applicationReceivedDateFrom date']")
    public WebElement errorForAppReceivedDateTo;

    @FindBy(xpath = "(//td[@class='MuiTableCell-root MuiTableCell-body mx-link'])[1]/..")
    public WebElement resultFirstRow;

    @FindBy(xpath = "//input[@name='dateRangeType']/following-sibling::*[@class='MuiSvgIcon-root MuiSelect-icon']")
    public WebElement dateRangeDropdown;

    //input[@id='dateRangeType']/parent::div

}
