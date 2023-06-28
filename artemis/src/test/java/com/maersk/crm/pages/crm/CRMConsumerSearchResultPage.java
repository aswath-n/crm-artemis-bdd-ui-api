package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
//import com.sun.org.glassfish.gmbal.DescriptorFields;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMConsumerSearchResultPage {

    
    public CRMConsumerSearchResultPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }


    @FindBy(xpath = "//*[text()='chevron_right']")
    public WebElement expandConsumerSearchResult;

    @FindAll({
            @FindBy(xpath = "//table[contains(@class, 'mt-4')]/tbody/tr[*]/td[1]//button")
    })
    public List<WebElement> ExpandSearchResults;


    @FindAll({
            @FindBy(xpath = "//*[contains(@class, 'mt-4 mx-table table mdl-color--grey-50 mb-0')]//tr[*]//td[7]")
    })
    public List<WebElement> SSNs;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class, 'mt-4 mx-table table mdl-color--grey-50 mb-0')]//tr[*]//td[2]")
    })
    public List<WebElement> CaseIDs;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class, 'mt-4 mx-table table mdl-color--grey-50 mb-0')]//tr[*]//td[3]")
    })
    public List<WebElement> ConsumerIDs;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class, 'mt-4 mx-table table mdl-color--grey-50 mb-0')]//tr[*]//td[4]")
    })
    public List<WebElement> FirstNames;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class, 'mt-4 mx-table table mdl-color--grey-50 mb-0')]//tr[*]//td[6]")
    })
    public List<WebElement> LastNames;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class, 'mt-4 mx-table table mdl-color--grey-50 mb-0')]//tr[*]//td[7]")
    })
    public List<WebElement> DOBs;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'mx-table table mt-4 mb-0 mdl-color--grey-50')]//tr[*]//td[10]")
    })
    public List<WebElement> LinkButtons;

    @FindBy(xpath = "(//span[contains(text(), 'visibility')])[2]")
    public WebElement ssnEyeUnMaskButton;

    @FindBy(xpath = "//span[contains(text(), 'visibility_off')]")
    public WebElement ssnEyeMaskButton;

    @FindBy(xpath = "//span[contains(text(),'chevron')]")
    public WebElement expandArrow;

    @FindBy(xpath = "//input[@value='caUnableToAuthenticate']")
    public WebElement unableToAuthenticateCheckBox;

    @FindBy(xpath = "//span[contains(text(),'link')]")
    public WebElement link;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class, 'mdl-data-table mdl-js-data-table mt-2')]//tr[*]//td[1]")
    })
    public List<WebElement> relatedConsumerNames;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class, 'mdl-data-table mdl-js-data-table mt-2')]//tr[*]//td[2]")
    })
    public List<WebElement> relatedConsumerRoles;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class, 'mdl-data-table mdl-js-data-table mt-2')]//tr[*]//td[3]/*[1]")
    })
    public List<WebElement> relatedConsumerDOBs;


    @FindAll({
            @FindBy(xpath = "//*[contains(@class, 'mdl-data-table mdl-js-data-table mt-2')]//tr[*]//td[4]/*[1]")
    })
    public List<WebElement> relatedConsumerSSNs;

    @FindBy(xpath = "//span[contains(text(), 'visibility')]")
    public WebElement unmaskDOB;

    @FindBy(xpath = "//span[contains(text(), 'visibility_off')]")
    public WebElement maskingDOB;

    @FindBy(xpath = "//*[@name='0_caConsumerFullName']")
    public WebElement consumerNameRadioButton;

    @FindAll({
            @FindBy(xpath = "//input[contains(@name, 'consumerCRMCaseID')]")
    })
    public List<WebElement> relatedConsumerIDs;

    @FindAll({
            @FindBy(xpath = "//input[contains(@name, 'consumerIdentificationNo')]")
    })
    public List<WebElement> relatedPrgConsumerIDs;


    @FindBy(xpath = "//*[text()='CONSUMER AUTHENTICATED']")
    public WebElement consumerAuthenticated;

    @FindBy(xpath = "//*[text()='ERROR MESSAGE']")
    public WebElement errorSnackBar;

    @FindBy(xpath = "//button[@aria-label='Next Page']")
    public WebElement btnNext;

    @FindBy(xpath = "//*[contains(@class ,'MuiTableRow-root pl-0 MuiTableRow-head')]//th[2]")
    public WebElement caseIdColumnHeader;

    @FindBy(xpath = "//*[contains(@class ,'MuiTableRow-root pl-0 MuiTableRow-head')]//th[3]")
    public WebElement consumerIdColumnHeader;

    @FindBy(xpath = "//button[@aria-label='Toggle SSN visibility']")
    public WebElement ssnMaskButtonSearchParameter;

    @FindBy(xpath = "//button[@aria-label='Toggle Visibility']//span[1]")
    public WebElement ssnMaskDateOfBirth;

    @FindAll({
            @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/..//table[contains(@class, 'mt-4')]/tbody/tr[not(contains(@class,'collapse'))]/td[6]")
    })
    public List<WebElement> DOBsNJ;

    @FindBy(xpath = "//button[@aria-label='Toggle Visibility']")
    public WebElement dobMaskButtonSearchParameter;

    @FindBy(xpath = "//*[@title='AuthDOB Visibiility']")
    public WebElement authGridDOBunmask;

    @FindBy(xpath = "//*[@title='AuthSSN Visibiility']")
    public WebElement authGridSSNunmask;

    @FindBy(xpath = "//tbody[1]/tr[1]/td[7]/button")
    public WebElement firstUnlinklButton;

    @FindBy(xpath = "//td[3]")
    public List<WebElement> listOfConsumerIdsFromManualSearchResult;

    @FindBy(xpath = "//td[2]")
    public List<WebElement> listOfCaseIdsFromManualSearchResult;



}
