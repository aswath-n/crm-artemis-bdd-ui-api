package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by KAMIL SHIKHSAFIYEV ON 4/13/2020
 */
public class CRMFilterProviderResultsPage {
    

    public CRMFilterProviderResultsPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//tbody[1]/tr[1]/td[2]/div/span[1]")
    public WebElement firstNameFromTable;

    @FindBy(xpath = "//button[@id='filter_providerName']")
    public WebElement filterFirstNameIcon;

    @FindBy(xpath = "//input[@placeholder='Search']")
    public WebElement filterSearch;

//    @FindBy(xpath = "//span[@class='MuiIconButton-label']//input[@class='jss29']")
    @FindBy(xpath = "//div[@class='mx-width-220 pb-0']//div[2]//input[@type='checkbox']")
    public WebElement filterCheckBox;

    @FindBy(xpath = "//div[@class='mx-width-220 pb-0']")
    public WebElement filterBox;

    @FindBy(xpath = "//button//span[text()='FILTER']")
    public WebElement filterButton;

    @FindBy(xpath = "//button[@id='filter_groupName']")
    public WebElement groupNameFilter;

    @FindBy(xpath = "//button[@id='filter_planName']")
    public WebElement planNameFilter;

    @FindBy(xpath = "//button[@id='filter_providerSpeciality']")
    public WebElement specialityFilter;

    @FindBy(xpath = "//tbody[@class='MuiTableBody-root']//tr/td[2]//span")
    public WebElement firstGroupNameFromTable;

    @FindBy(xpath = "//table[1]/tbody/tr/td[4]/span")
    public List<WebElement> groupNamesFromTable;

    @FindBy(xpath = "//tbody[@class='MuiTableBody-root']//tr/td[2]//span")
    public List<WebElement> providerNamesFromTable;

    @FindBy(xpath = "//button[@id='filter_firstName']")
    public WebElement colorIconProvider;

    @FindBy(xpath = "(//button//span//span[text()='search'])[2]")
    public WebElement searchButton;

    @FindBy(xpath = "//div[1]/div[1]/div[2]/div[1]/p[2]")
    public WebElement providerNameFromProDet;

    @FindBy(xpath = "//tbody[@class='MuiTableBody-root'][1]//tr/td[4]//span")
    public WebElement firstPlanName;

    @FindBy(xpath = "//tbody[@class='MuiTableBody-root']//tr/td[4]//span")
    public List<WebElement> planNamesFromTable;



    public static boolean allTextInTableIsExist(List<WebElement> list, String name) {
        boolean exist = false;
        for (WebElement element : list) {
            if (element.getText().equalsIgnoreCase(name)) {
                exist=true;
            }
            else {
                exist=false;
            }
        }
        return exist;
    }
}
