package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import org.apache.xmlbeans.impl.xb.xsdschema.FieldDocument;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class TMSearchRegionInfoPage {
    
    public TMSearchRegionInfoPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    //Below elements are present on Service region information page
    @FindBy(xpath="(//div[@role=\"tablist\"]/button/span/span)[1]")
    public WebElement serviceRegionInfoTab;

    @FindBy(xpath="(//div[@role=\"tablist\"]/button/span/span)[2]")
    public WebElement geographicalInfoTab;

    //serviceRegionInfoTab
    @FindBy(xpath="//div[@id=\"service-region-info\"]/div/h5[1]")
    public WebElement serviceRegionInfoPageHdr;

    @FindBy(xpath="//div[@id=\"service-region-info\"]/div/h5[2]")
    public WebElement serviceRegionName;

    @FindBy(xpath="//div[@class=\"card\"]/div[1]")
    public WebElement programType;

    @FindBy(xpath="//div[@class=\"card-body\"]/span/span")
    public List<WebElement> subProgramTypes;

    //geographicalInfoTab
    @FindBy(xpath = "(//p[text()='Select County'])[1]")
    public WebElement countyNameAndCodeDropDwn;

    @FindBy(xpath = "//input[@id='_County Name and Code']")
    public WebElement countyNameAndCodeDropDwn1;


    @FindBy(xpath="//p[text()='Select County']/..")
    //@FindBy(xpath="//input[contains(@id,'react-select-11-input')]")
    public WebElement countyNameAndCodeInput;

    @FindBy(xpath="//input[@id='_County Name and Code']")
    public WebElement countyNameAndCodeInput1;

    @FindBy(xpath = "(//p[text()='Select City'])[1]")
    public WebElement cityDropDwn;

    @FindBy(xpath = "//input[@id='_City']")
    public WebElement cityDropDwn1;

    @FindBy(xpath = "//*[contains(text(),'Select City')]/..")
    public WebElement cityInput;

    @FindBy(xpath = "//input[@id='_City']")
    public WebElement cityInput1;


    @FindBy(name = "zipCode")
    public WebElement zipCodeInput;

    @FindBy(xpath="(//span[text()='search'])[1]/../..")
    public WebElement geographicalInfoSearchBtn;

    @FindBy(xpath="(//span[text()=' CANCEL '])[1]/..")
    public WebElement geographicalInfoCancelBtn;

    @FindBy(xpath="//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text mdl-button mdl-js-button mdl-button--primary mx-btn mx-btn-border mx-btn-cancel']")
    public WebElement geographicalInfoCancelBtn1;

    @FindBy(xpath="//h5[contains(text(),'SEARCH RESULT(S)')]")
    public WebElement geographicalInfo_searchResultMainHdr;

    @FindBy(xpath="//table/thead/tr/th[2]/span")
    public WebElement searchResultList_countyNameHdr;
    @FindBy(xpath="//table/thead/tr/th[3]/span")
    public WebElement searchResultList_countyCodeHdr;
    @FindBy(xpath="//table/thead/tr/th[4]/span")
    public WebElement searchResultList_zipCodeHdr;
    @FindBy(xpath="//table/thead/tr/th[5]/span")
    public WebElement searchResultList_citiesHdr;

    @FindBy(xpath="(//table/tbody/tr[1]/td[2])")
    public List<WebElement> searchResultList_countyName;
    @FindBy(xpath="(//table/tbody/tr[1]/td[3])")
    public List<WebElement> searchResultList_countyCode;
    @FindBy(xpath="(//table/tbody/tr[1]/td[4])")
    public List<WebElement> searchResultList_zipCode;
    @FindBy(xpath="(//table/tbody/tr[1]/td[5])")
    public List<WebElement> searchResultList_cities;

    //itemsPerPage
    @FindBy(id="mui-component-select-itemsPerPage")
    public WebElement geographicalInfo_default_ItemsPerPage;
    @FindBy(id="mui-component-select-itemsPerPage")
    public WebElement geographicalInfo_ItemsPerPageList;

    //all pages list
    @FindBy(xpath="//ul[@class=\"pagination\"]//a")
    public List<WebElement> searchResultList_pagesList;

    @FindBy(xpath="(//span[@id=\"client-snackbar\"])[2]")
    public WebElement searchResultList_InvalidSearchErrorMsg;

}

