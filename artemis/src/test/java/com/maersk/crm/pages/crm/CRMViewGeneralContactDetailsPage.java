package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMViewGeneralContactDetailsPage {

    

    public CRMViewGeneralContactDetailsPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//p[contains(text(),'DISPOSITION')]/..//h6")
    public WebElement lblDisposition;

    @FindBy(xpath = "//p[contains(text(),'PHONE')]/following-sibling::h6")
    public WebElement lblPhone;

    ////div//h5/i[text()='link']/../../..//div[3]//table//tbody//tr
    @FindBy(xpath = "//div[3]//table//tbody//tr")
    public List<WebElement> linksTableList;

    @FindBy(xpath = "//div[3]//table//tbody//tr//td[2]")
    public List<WebElement> linkIds;

    @FindBy(xpath = "//div[3]//table//tbody//tr//td[3]")
    public List<WebElement> linkNames;

    @FindBy(xpath = "//div[3]//table//tbody//tr//td[4]")
    public List<WebElement> linkTypes;

    @FindBy(xpath = "//div[3]//table//tbody//tr//td[4]")
    public List<WebElement> linkStatuses;

}
