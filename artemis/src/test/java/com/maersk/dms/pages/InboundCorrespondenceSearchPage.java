package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class InboundCorrespondenceSearchPage {

    
    Actions actions;
    WebElement pdfIcon;
    WebDriverWait wait;


    public InboundCorrespondenceSearchPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//span[contains(text(),'INBOUND SEARCH')]")
    public WebElement inboundSearchPageMenuItem;

    @FindBy(xpath = "//span[contains(text(),'search')]")
    public WebElement searchButton;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[2]")
    public List<WebElement> cidList;

    public WebElement getCid(String cid){
        return Driver.getDriver().findElement(By.xpath("//*[contains(text(),'+cid+')]"));
    }

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[2]")
    public List<WebElement> cidColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[3]")
    public List<WebElement> setIdColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[7]")
    public List<WebElement> caseIDColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[10]")
    public List<WebElement> fromNameColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[8]")
    public List<WebElement> consumerIDColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[4]")
    public List<WebElement> dateReceivedColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[5]")
    public List<WebElement> typeColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[6]")
    public List<WebElement> statusColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/thead/tr/th")
    public List<WebElement> resultcolumnNames;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[11]")
    public List<WebElement> viewIcondColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[9]")
    public List<WebElement> channelColumn;

    @FindBy(xpath = "//div[@id='mui-component-select-channel']")
    public WebElement channelDropdown;

    @FindBy(xpath = "//div[@id='mui-component-select-status']")
    public WebElement statusDropdown;

    @FindBy(xpath = "//button[contains(@aria-label,'change date')]")
    public WebElement calendar;

    @FindBy(xpath = "//span[.='arrow_forward']")
    public WebElement nextPageArrowKeyButton;

    @FindBy(id = "correspondenceId-label")
    public WebElement cidLabel;

    @FindBy(id = "correspondenceSetID-label")
    public WebElement cSetIDLabel;

    @FindBy(xpath = "//label[@class='MuiFormLabel-root MuiInputLabel-root MuiInputLabel-formControl MuiInputLabel-animated']")
    public List<WebElement> labelNames;

    @FindBy(xpath = "//ul[@role='listbox']/li")
    public List<WebElement> channelDropdownValues;

    @FindBy(xpath = "//ul[@role='listbox']/li")
    public List<WebElement> statusDropdownValues;

    @FindBy(xpath = "//input[@id='dateReceived']")
    public WebElement dateReceived;

    @FindBy(xpath = "//*[@id='mui-component-select-condtionType']")
    public WebElement equalSymbols;

    @FindBy(xpath = "//li[@data-value='lessThan']")
    public WebElement lessThan;

    @FindBy(xpath = "//*[contains(text(),'No search results found')]")
    public WebElement nothingFound;

    @FindBy(xpath = "//span[@id='client-snackbar' and contains(text(),'User must enter at least one search criteria field')]")
    public WebElement errorMessageText;

    @FindBy(xpath = "//span[@id='client-snackbar' and contains(text(),'Search results in excess, enter additional search criteria to limit search results')]")
    public WebElement excessMessageText;

    @FindBy(xpath = "//*[@id='mui-component-select-correspondenceType']")
    public WebElement typeDropDown;

    @FindBy(xpath = "//input[@name='correspondenceType']")
    public WebElement typeDropDownValue;

    @FindBy(xpath = "//ul[@role='listbox']/li")
    public List<WebElement> typeDropDownOptions;

    @FindBy(xpath = "//*[@id='mui-component-select-status']")
    public WebElement statusDropDown;

    @FindBy(xpath = "//input[@name='status']")
    public WebElement statusDropDownValue;

    @FindBy(xpath = "//input[@name='channel']")
    public WebElement channelDropdownValue;

    @FindBy(xpath = "//span[contains(text(),'close')]/ancestor::button")
    public WebElement cancelButton;

    @FindBy(xpath = "//i[contains(text(),'file_copy')]")
    public WebElement fileCopy;

    /**
     * search criteria with ids
     */
    public WebElement correspondenceId;
    public WebElement correspondenceSetID;
    public WebElement addDemoPhoneNumber;
    public WebElement fromEmailAddress;
    public WebElement fromName;
    public WebElement caseID;
    public WebElement sourceCorrespondenceID;
    public WebElement sourceCorrespondenceSetID;
    public WebElement consumerID;


    //end of id section

    @FindBy(xpath = "//div[1]/div[5]/div[1]/h6[1]")
    public WebElement firstCreatedby;

    @FindBy(xpath = "//div[1]/div[5]/div[1]/p[1]")
    public WebElement firstCreatedOn;

    @FindBy(xpath = "//span[contains(text(),'Get Image Location')]")
    public WebElement getImageLocation;

    @FindBy(xpath = "//div[@class='col py-2 px-3 ml-3']//span[@id='client-snackbar']")
    public WebElement clipPopUp;

    @FindBy(xpath = "//h5[contains(text(), 'INBOUND CORRESPONDENCE SEARCH')]")
    public WebElement inboundSearchHeader;

    @FindBy(xpath = "//span[.='first_page']")
    public WebElement backToFirstPageArrow;

    @FindBy(xpath = "//*[contains(text(),'LINKS')]/following::table/tbody/tr/td[2]")
    public List<WebElement> linksIdColumn;

    @FindBy(xpath = "//*[contains(text(),'LINKS')]/following::table/tbody/tr/td[3]")
    public List<WebElement> linksNameColumn;

    @FindBy(xpath = "//span[@id='client-snackbar' and contains(text(),'Invalid Document Id')]")
    public WebElement noCIdDocumentErrorMsg;

    @FindBy(xpath = "//table/thead/tr/th[contains(text(),'CASE ID')]")
    public WebElement caseIdResultsColumnLabel;

    @FindBy(xpath = "//table/thead/tr/th[contains(text(),'CONSUMER ID')]")
    public WebElement consumerIdResultsColumnLabel;

}