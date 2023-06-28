package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMTaskEditHistoryPage {

    

    public CRMTaskEditHistoryPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//span[text()='EDIT HISTORY']")
    public WebElement editHistoryTab;

    @FindBy(xpath = "//*[text()='TASK EDIT HISTORY']")
    public WebElement taskEditHistory;

    @FindBy(xpath = "//tr//td[2]")
    public List<WebElement> listEditedOn;

    @FindBy(xpath = "//tr//td[3]")
    public List<WebElement> listEditedBy;

    @FindBy(xpath = "//a[contains(text(),'2')]")
    public WebElement scndPag;

    @FindBy(xpath = "//tr//td[4]")
    public List<WebElement> reasonForEditVlu;

    @FindBy(xpath = "//tr//td[5]")
    public List<WebElement> fieldEditHistory;

    @FindBy(xpath = "//tr//td[6]")
    public List<WebElement> previousEditHistory;

    @FindBy(xpath = "//tr//td[7]")
    public List<WebElement> updatedEditHistory;

    @FindBy(xpath = "//ul[@class='pagination']/li")
    public List<WebElement> pagination;

    @FindBy(xpath = "//span[text()='TASK DETAILS']")
    public WebElement taskDetailsTab;
}
