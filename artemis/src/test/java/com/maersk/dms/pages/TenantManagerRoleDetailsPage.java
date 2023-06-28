package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TenantManagerRoleDetailsPage {

    

    public TenantManagerRoleDetailsPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//span[contains(@class, 'material-icons MuiIcon-root')]")
    public WebElement addRoleButton;

    @FindBy(xpath = "//h5[text() = 'ROLE DETAILS']")
    public WebElement roleDetailsHeader;

    @FindBy(xpath = "//input[@name='projectName']")
    public WebElement roleProjectName;

    @FindBy(xpath = "//input[@name='projectId']")
    public WebElement roleProjectId;

    @FindBy(xpath = "//input[@name='roleName']")
    public WebElement roleName;

    @FindBy(xpath = "//input[@name='roleDesc']")
    public WebElement roleDesc;

    @FindBy(xpath = "//h5[contains(text(),'Role List')]")
    public WebElement roleListHeader;

}
