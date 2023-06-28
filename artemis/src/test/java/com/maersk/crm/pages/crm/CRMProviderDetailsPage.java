package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by KAMIL SHIKHSAFIYEV ON 4/8/2020
 */
public class CRMProviderDetailsPage {
    

    public CRMProviderDetailsPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//th[contains(text(),'PLAN NAME')]")
    public WebElement planNameForProvider;

    @FindBy(xpath = "//th[contains(text(),'PROVIDER ADDRESS')]")
    public WebElement providerAddress;

    @FindBy(xpath = "//th[contains(text(),'PHONE NUMBER')]")
    public WebElement phoneNumber;

    @FindBy(xpath = "//th[contains(text(),'FAX NUMBER')]")
    public WebElement faxNumber;

    @FindBy(xpath = "//th[contains(text(),'EMAIL ADDRESS')]")
    public WebElement emailAddress;

    @FindBy(xpath = "//th[contains(text(),'NEW PATIENTS')]")
    public WebElement newPatients;

    @FindBy(xpath = "//th[contains(text(),'HANDICAP')]")
    public WebElement handicap;

    @FindBy(xpath = "//th[contains(text(),'OFFICE HOURS')]")
    public WebElement officeHours;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[7]")
    public WebElement newPatientIcon;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[8]/i[1]")
    public WebElement handicapIcon;

    @FindBy(xpath = "//tbody[1]/tr[1]/td[7]/span[1]")
    public WebElement redIconForPatient;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[9]/span[1]")
    public WebElement mon;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[9]/span[2]")
    public WebElement tue;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[9]/span[3]")
    public WebElement wed;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[9]/span[4]")
    public WebElement thur;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[9]/span[5]")
    public WebElement fri;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[9]/span[6]")
    public WebElement sat;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[9]/span[7]")
    public WebElement sun;

    @FindBy(xpath = "//a[@class='float-left pl-3']")
    public WebElement backArrow;

    @FindBy(xpath = "//div[1]/div[1]/p[1]/div[1]/button[1]")
    public WebElement popUpContinue;

    @FindBy(xpath = "//input[@id='firstName']")
    public WebElement inputName;

    @FindBy(xpath = "//div[1]/div[5]/div[1]/h5[1]")
    public WebElement locationAssociated;

    @FindBy(xpath = "//div[5]/div[1]/div[1]/div[1]/table[1]/tbody")
    public List<WebElement> locationProviderTable;

    @FindBy(xpath = "//div[1]/ul[1]/li[2]/a[1]")
    public WebElement secondPageLocation;



}
