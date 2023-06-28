package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRM_CORE_CommunitySearchPage {


    private WebDriver driver;

    public CRM_CORE_CommunitySearchPage() {
        this.driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath ="//h5[.='event_availableSEARCH COMMUNITY OUTREACH SESSION']")
    public WebElement communitySearchPageHeader;

    @FindBy(xpath ="//h5[.='searchSEARCH RESULT(S)']")
    public WebElement communitySearchResultHeader;

    @FindBy(id = "communityOutreachId")
    public WebElement sessionIdSearchField;

    @FindBy(id = "communityOutreachId-label")
    public WebElement sessionIdSearchLabel;

    @FindBy(id = "auto-Session Type")
    public WebElement sessionTypeSearchField;

    @FindBy(id = "auto-Session Type-label")
    public WebElement sessionTypeSearchLabel;

    @FindBy(id = "siteName")
    public WebElement siteNameSearchField;

    @FindBy(id = "siteName-label")
    public WebElement siteNameSearchLabel;

    @FindBy(id = "auto-Recurrence Frequency")
    public WebElement recurrenceFreqSearchField;

    @FindBy(id = "auto-Recurrence Frequency-label")
    public WebElement recurrenceFreqSearchLabel;

    @FindBy(id = "auto-Presenter Name")
    public WebElement presenterNameSearchField;

    @FindBy(id = "auto-Presenter Name-label")
    public WebElement presenterNameSearchLabel;

    @FindBy(id = "auto-Session Region")
    public WebElement sessionRegionSearchField;

    @FindBy(id = "auto-Session Region-label")
    public WebElement sessionRegionSearchLabel;

    @FindBy(id = "auto-Session Status")
    public WebElement sessionStatusSearchField;

    @FindBy(id = "auto-Session Status-label")
    public WebElement sessionStatusSearchlabel;

    @FindBy(id = "auto-Zip")
    public WebElement zipSearchField;

    @FindBy(id = "auto-Zip-label")
    public WebElement zipSearchLabel;

    @FindBy(id = "auto-County")
    public WebElement countySearchField;

    @FindBy(id = "auto-County-label")
    public WebElement countySearchLabel;

    @FindBy(xpath = "//button[contains(text(),' SEARCH')]")
    public WebElement searchButton;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/thead/tr[1]/th")
    public List<WebElement> searchResultHeaders;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody[1]/tr[1]/td")
    public List<WebElement> searchResultRowOne;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody[2]/tr[1]/td")
    public List<WebElement> searchResultRowTwo;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody[3]/tr[1]/td")
    public List<WebElement> searchResultRowThree;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody[4]/tr[1]/td")
    public List<WebElement> searchResultRowFour;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody[5]/tr[1]/td")
    public List<WebElement> searchResultRowFive;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[2]")
    public List<WebElement> sessionIDColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[3]")
    public List<WebElement> sessionTypeColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[4]")
    public List<WebElement> sessionStatusColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[5]")
    public List<WebElement> sessionScheduleDateColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[6]")
    public List<WebElement> sessionRegionColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[7]")
    public List<WebElement> siteNameColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[8]")
    public List<WebElement> recurrenceFrequencyColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[9]")
    public List<WebElement> zipColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[10]")
    public List<WebElement> countyColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[11]")
    public List<WebElement> presenterNameColumn;

    @FindBy(xpath = "//li[contains(text(),'show 5')]")
    public WebElement itemsPerPage;

    @FindBy(xpath = "//li[contains(text(),'show 5')]")
    public WebElement resultShow5;

    @FindBy(xpath = "//li[contains(text(),'show 10')]")
    public WebElement resultShow10;

    @FindBy(xpath = "//li[contains(text(),'show 20')]")
    public WebElement resultShow20;

    @FindBy(id = "mui-component-select-itemsPerPage")
    public WebElement selectItemsPerPage;

    @FindBy(xpath = "//li/a[@href='#']")
    public List<WebElement> searchResultpaginationList;
}
