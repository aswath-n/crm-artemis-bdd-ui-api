package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TenantManagerDMSProjectListPage {
    

    public TenantManagerDMSProjectListPage() {

        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "(//i[contains(text(),'arrow_right_alt')])[1]")
    public WebElement arrowClick;

    @FindBy(xpath = "//*[contains(text(),'Correspondence Outbound Definitions')]")
    public WebElement outBoundCorrespondenceLink;

    @FindBy(xpath = "//*[contains(text(),'Correspondence Inbound Definitions')]")
    public WebElement inBoundCorrespondenceLink;

    @FindBy(xpath = "//*[contains(text(),'Correspondence Outbound Configuration')]")
    public WebElement outBoundSettingsLink;

    @FindBy(xpath = "//span[contains(text(),'build')]/..")
    public WebElement leftDrawer;

    @FindBy(xpath = "//span[contains(text(),'check')]/..")
    public WebElement discardPopUp;

    @FindBy(xpath="//*[contains(text(),'CORRESPONDENCE DEFINITIONS')]")
    public WebElement outboundCorrespondenceListHeader;

    @FindBy(xpath="//label[contains(text(),'Project')]/following-sibling::div/input")
    public WebElement projectInput;

    @FindBy(xpath="//span[contains(text(),'search')]/ancestor::button")
    public WebElement searchButton;

    @FindBy(xpath = "//*[contains(text(),'Correspondence Inbound Configuration')]")
    public WebElement inBoundSettingsLink;

    public WebElement getProject(String project){
        return Driver.getDriver().findElement(By.xpath("//*[contains(text(),'"+project+"')]/../button"));
    }

    public List<WebElement> getProjectList(){
        return Driver.getDriver().findElements(By.xpath("//i[contains(text(),'arrow_right_alt')]"));
    }


}
