package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CRMSearchProviderPage {

    

    public CRMSearchProviderPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);

    }


    @FindBy(xpath = "//span[contains(text(), 'Provider Search')]")
    public WebElement searchProvider;


    @FindBy(xpath = "(//span[contains(@class, 'MuiButton-label')])[3]")
    public WebElement advancesearch;


    @FindBy(xpath = "//input[contains(@name, 'providerPCPIndicator')]")
    public WebElement expandpcplist;


    @FindBy(xpath = "//input[contains(@id, 'firstName')]")
    public WebElement providerfirstname;

    @FindBy(xpath = "//input[contains(@id, 'lastName')]")
    public WebElement providerlastname;


    @FindBy(xpath = "//input[contains(@id, 'groupName')]")
    public WebElement providergroupname;

    @FindBy(xpath = "//input[contains(@id, 'phoneNumber')]")
    public WebElement ProviderPhoneno;

    @FindBy(xpath = "//button[contains(@title, 'Open')]")
    public WebElement OpenCombo;

    @FindBy(xpath = "//input[contains(@id, '_SPECIALTY')]")
    public WebElement selectspeciality;
    @FindBy(xpath = "//*[@name='providerTypeCd']/..")
    public WebElement  providertype;

    @FindBy(xpath = "//*[@name='pcpFlag']/..")
    public WebElement pcpindicator;

    @FindBy(xpath = "//input[contains(@id, '_PLAN NAME')]")
    public WebElement SelectOption;

    @FindBy(xpath = "(//span[contains(text(),'search')])[2]")
    public WebElement searchButton;

    @FindBy(xpath = "//tr[@class='MuiTableRow-root']//td[2]//div")
    public WebElement getName;

    @FindBy(xpath = "//tr[@class='MuiTableRow-root']//td[3]//div")
    public WebElement getAddress;

    @FindBy(xpath = "//tbody[contains(@class, 'MuiTableBody-root')]/descendant::tr[contains(@class, 'MuiTableRow-root')]/td[4]/span")
    public WebElement getgroupname;

    @FindBy(xpath = "//tr[@class='MuiTableRow-root']//td[4]//div")
    public WebElement getplanname;

    @FindBy(xpath = "//tr[@class='MuiTableRow-root']//td[6]//div")
    public WebElement getProviderType;

    @FindBy(xpath = "//tr[@class='MuiTableRow-root']//td[7]//div")
    public WebElement getSpeciality;


    @FindBy(xpath ="(//span[contains(@id, 'client-snackbar')])[2]")
    public WebElement resultCount;

    @FindBy(xpath ="(//button[contains(@class, 'primary mx-btn-cancel ml-2')])[2]/span")
    public WebElement CancelButton;

    //groupname needs to be updated.. asa



}
