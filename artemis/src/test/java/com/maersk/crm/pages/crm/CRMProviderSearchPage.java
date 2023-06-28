package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import javax.xml.xpath.XPath;
import java.util.List;

/**
 * Created by KAMIL SHIKHSAFIYEV ON 4/6/2020
 */
public class CRMProviderSearchPage {
    

    public CRMProviderSearchPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }


    @FindBy(xpath = "//span[text()='Provider Search']/parent::a")
    public WebElement providerSearch;

    @FindBy(xpath = "//*[contains(text(), 'INITIATE CONTACT')]")
    public WebElement initContact;

    @FindBy(xpath = "//input[@id='_PLAN NAME']/.")
    public WebElement planName;

    @FindBy(xpath = "(//button//span//span[text()='search'])[2]")
    public WebElement searchButton;

    @FindBy(xpath = "//tr[@class='MuiTableRow-root']//td[2]//div//span")
    public WebElement firstName;

    @FindBy(xpath = "//div[@class='row']//div//p[2]")
    public WebElement firstNameProviderDetails;

    @FindBy(xpath = "//input[@id='lastName']")
    public WebElement lastName;

    @FindBy(xpath = "//tbody[1]//tr[1]//td[2]//span[1]")
    public WebElement providerTable;

    @FindBy(xpath = "//div[2]/div[2]/p[2]")
    public WebElement groupName;

    @FindBy(xpath = "//*[@id='genderCd']")
    public WebElement providerGender;

    @FindBy(xpath = " //div[2]/div[3]/p[2]")
    public WebElement providerGenderValue;


    @FindBy(xpath = "//div[2]/div[4]/p[2]")
    public WebElement genderServed;


    @FindBy(xpath = "//div[2]/div[5]/p[2]")
    public WebElement providerType;

    @FindBy(xpath = "//div[2]/div[6]/p[2]")
    public WebElement pcpIndicator;

    @FindBy(xpath = "//div[2]/div[7]/p[2]")
    public WebElement obIndicator;

    @FindBy(xpath = "//div[3]/div[3]/p[2]")
    public WebElement patientMinimumAge;

    @FindBy(xpath = "//div[3]/div[4]/p[2]")
    public WebElement patientMaxAge;

    @FindBy(xpath = "//div[2]/div[8]/p[2]")
    public WebElement patientAgeRange;


    @FindBy(xpath = "//div[2]/div[9]/p[2]")
    public WebElement npi;

    @FindBy(xpath = "//div[@class='col-5 py-1']/span")
    public List<WebElement> languages;

    @FindBy(xpath = "//div[1]/span[1]/i[1]")
    public WebElement primaryLang;

    @FindBy(xpath = "//div[@class='col-6 py-1 px-0']/span")
    public List<WebElement> specialities;

    @FindBy(xpath = "//div[2]/span[1]/i[1]")
    public WebElement primarySpec;

    @FindBy(xpath = "//div[3]/div[1]/span")
    public WebElement primaryLangHover;

    @FindBy(xpath = "//div[3]/div[2]/span")
    public WebElement primarySpecHover;

    @FindBy(xpath = "//div[@class='col-12 px-0 pb-2']//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text mx-btn mx-btn-border mx-color-text-primary mx-btn-cancel ml-2']")
    public WebElement advancedSearch;

    @FindBy(xpath = "//input[@id='npi']")
    public WebElement providerNpi;

    @FindBy(xpath = "//span[text()='Advanced Search']")
    public WebElement searchForAdvance;

    @FindBy(xpath = "//div[6]/div[1]/div[1]/div[2]/div[1]/table[1]/tbody[1]/tr")
    public List<WebElement> rowsInProviderSearch;

    @FindBy(xpath = "//ul[1]/li[2]/a[1]/div[1]/span[1]")
    public WebElement contactRecordWidget;

    @FindBy(xpath = "//input[@id='firstName']")
    public WebElement getFirstName;

    @FindBy(xpath = "//div[@id='mui-component-select-acceptNewPatientsInd']")
    public WebElement acceptingNewPatientsDrop;

    @FindBy(xpath = "//input[@id='groupName']")
    public WebElement inputGroupName;

    @FindBy(xpath = "(//button//span//span[text()='search'])[2]")
    public WebElement searchButtonAfterAdvance;





    public int count(List<WebElement> ls) {
        int count = 0;
        for (int i = 0; i < ls.size(); i++) {
            count++;
        }
        return count;
    }

    public void selectDropDown(WebElement element, String selector) throws InterruptedException {
        Thread.sleep(1000);
        element.click();
        WebElement el = Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + selector + "')]"));
        el.click();
    }

}
