package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMApplicationInfoPage extends CRMUtilities implements ApiBase {

    

    public CRMApplicationInfoPage() {

        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//div[contains(@class,'MuiPaper-elevation1')]//tbody//tr//td[count(//thead//tr/th[contains(.,'APPLICATION ID')]/preceding-sibling::th)+1]")
    public List<WebElement> applicationIdColumnValues;


    @FindBy(xpath = "//div[contains(@class,'MuiPaper-elevation1')]//tbody//tr//td[count(//thead//tr/th[contains(.,'APPLICATION ID')]/preceding-sibling::th)]/button")
    public List<WebElement> applicationIdColumnButton;

    @FindBy(xpath = "//div[contains(@class,'MuiPaper-elevation1')]//tbody//tr//td[count(//thead//tr/th[contains(.,'LAST NAME')]/preceding-sibling::th)+1]")
    public List<WebElement> lastNameColumnValues;

    @FindBy(xpath = "//div[contains(@class,'MuiPaper-elevation1')]//tbody//tr//td[count(//thead//tr/th[contains(.,'FIRST NAME')]/preceding-sibling::th)+1]")
    public List<WebElement> firstNameColumnValues;

    @FindBy(xpath = "//div[contains(@class,'MuiPaper-elevation1')]//tbody//tr//td[count(//thead//tr/th[contains(.,'APPLICANT DOB')]/preceding-sibling::th)+1]")
    public List<WebElement> applicantDobColumnValues;

    @FindBy(xpath = "//div[contains(@class,'MuiPaper-elevation1')]//tbody//tr//td[count(//thead//tr/th[contains(.,'STATUS')]/preceding-sibling::th)+1]")
    public List<WebElement> statusColumnValues;

    @FindBy(xpath = "//div[contains(@class,'MuiPaper-elevation1')]//tbody//tr//td[count(//thead//tr/th[contains(.,'APPLICATION CYCLE')]/preceding-sibling::th)+1]")
    public List<WebElement> appCycleColumnValues;

    @FindBy(xpath = "//div[contains(@class,'MuiPaper-elevation1')]//tbody//tr//td[count(//thead//tr/th[contains(.,'PROGRAM')]/preceding-sibling::th)+1]")
    public List<WebElement> programColumnValues;

    @FindBy(xpath = "//div[contains(@class,'MuiPaper-elevation1')]//tbody//tr//td[count(//thead//tr/th[contains(.,'CHANNEL')]/preceding-sibling::th)+1]")
    public List<WebElement> channelColumnValues;

    @FindBy(xpath = "//div[contains(@class,'MuiPaper-elevation1')]//tbody//tr//td[count(//thead//tr/th[contains(.,'EXTERNAL APP ID')]/preceding-sibling::th)+1]")
    public List<WebElement> extAppIdColumnValues;

    @FindBy(xpath = "//div[contains(@class,'MuiPaper-elevation1')]//thead/tr/th")
    public List<WebElement> headerValues;

}
