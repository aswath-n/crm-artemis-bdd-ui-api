package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMCaseContactDetailsPage {
    
    private BrowserUtils browserUtils = new BrowserUtils();

    public CRMCaseContactDetailsPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//span[contains(text(),'CONTACT ID')]")
    public WebElement contactId;

    @FindBy(id = "consumerFirstName")
    public WebElement consumerFirstName;

    @FindBy(id = "consumerLastName")
    public WebElement consumerLastName;

    @FindBy(id = "consumerSSN")
    public WebElement consumerSsn;

    @FindBy(id = "seaConDOB")
    public WebElement consumerDob;

    @FindBy(id = "seacrmCaseID")
    public WebElement caseId;

    @FindBy(id = "seacrmConsumerId")
    public WebElement consumerId;

    @FindBy(xpath = "//div[@class='col-12 mt-4 mb-4']/button[1]")
    public WebElement searchBtn;

    @FindBy(xpath = "//div[@class='col-12 mt-4 mb-4']/button[2]")
    public WebElement cancelBtn;

    @FindBy(xpath = "//div[@class='mx-menu-icon-wrapper']")
    public WebElement contactRecordSearchSidebarIcon;

    @FindBy(xpath = "//*[text()='Contact Record Search']")
    public WebElement contactRecordSearchTab;

    @FindBy(xpath = "//h5[contains(text(),'INBOUND CORRESPONDENCES')]")
    public WebElement inbCorrHeader;

    @FindBy(xpath = "//h5[contains(text(),'OUTBOUND CORRESPONDENCES')]/following::table/tbody/tr/td[2]")
    public List<WebElement> outboundCIdColumn;

    @FindBy(xpath = "//h5[contains(text(),'OUTBOUND CORRESPONDENCES')]")
    public List<WebElement> outboundCIdHeader;

    @FindBy(xpath = "//h5[contains(text(),'INBOUND CORRESPONDENCES')]/following::table/tbody/tr/td[2]")
    public List<WebElement> caseContactDeInboundcidColumn;

}
