package com.maersk.crm.pages.crm;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class CRMLinksComponentPage {
    

    public CRMLinksComponentPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//th[text()='TYPE']/../../../tbody//td[3]")
    public WebElement foundTaskRecord;

    @FindBy(xpath = "//input[@id='taskId']")
    public WebElement taskIdForLinkVerification;

    @FindBy(xpath = "//*[contains(text(),'Demographic Info')]")
    public WebElement demographicInfoTab;

    @FindBy(xpath = "(//*[contains(text(),'Active Contact')])[2]")
    public WebElement activeContactScreen;

    @FindBy(xpath = "//th[text()='TYPE']/../../../tbody//td[2]")
    public WebElement idField;

    @FindBy(xpath = "//td[text()='Case']/../td[2]")
    public WebElement idFieldForConsumerInCase;

    @FindBy(xpath = "(//*[@class ='MuiPaper-root MuiPaper-elevation1 MuiPaper-rounded']/table/tbody/tr[1]/td)[2]")
    public WebElement taskIdField;

    @FindBy(xpath = "//th[text()='TYPE']/../../../tbody/tr[3]//td[3]")
    public WebElement contactRecordIdOnTaskPage;

    @FindBy(xpath = "//th[text()='TYPE']/../../../tbody/tr[3]//td[4]")
    public WebElement contactRecordNameOnTaskPage;

    @FindBy(xpath = "//th[text()='TYPE']/../../../tbody/tr[3]//td[5]")
    public WebElement contactRecordTypeOnTaskPage;

    @FindBy(xpath = "//th[text()='TYPE']/../../../tbody/tr[3]//td[6]")
    public WebElement contactRecordDateOnTaskPage;

    @FindBy(xpath = "//th[text()='TYPE']/../../../tbody/tr[3]//td[7]")
    public WebElement contactRecordStatusOnTaskPage;


    @FindBy(xpath = "(//*[@class ='MuiPaper-root MuiPaper-elevation1 MuiPaper-rounded']/table/tbody/tr[1]/td)[3]")
    public WebElement taskNameField;

    @FindBy(xpath = "(//*[@class ='MuiPaper-root MuiPaper-elevation1 MuiPaper-rounded']/table/tbody/tr[1]/td)[4]")
    public WebElement taskTypeField;

    @FindBy(xpath = "(//*[@class ='MuiPaper-root MuiPaper-elevation1 MuiPaper-rounded']/table/tbody/tr[1]/td)[5]")
    public WebElement taskStatusDateField;

    @FindBy(xpath = "(//*[@class ='MuiPaper-root MuiPaper-elevation1 MuiPaper-rounded']/table/tbody/tr[1]/td)[6]")
    public WebElement taskStatusField;

    @FindBy(xpath = "//td[text()='Case']")
    public WebElement nameFieldForCase;

    @FindBy(xpath = "//td[text()='Consumer Profile']")
    public WebElement nameFieldForConsumer;

    @FindBy(xpath = "//td[text()='Case']/following-sibling::td[1]")
    public WebElement typeFieldForCase;

    @FindBy(xpath = "//td[text()='Consumer Profile']/following-sibling::td[1]")
    public WebElement typeFieldForConsumer;

    @FindBy(xpath = "//td[text()='Case']/following-sibling::td[2]")
    public WebElement statusDateFieldForCase;

    @FindBy(xpath = "//td[text()='Case']/following-sibling::td[3]")
    public WebElement statusFieldForCase;

    @FindBy(xpath="//tr[@class='MuiTableRow-root']")
    public List<WebElement> consumerLinkedToTaskList;

    @FindBy(xpath="//td[text()[contains(.,'Contact Record')]]")
    public WebElement consumerLinkedToGeneralTaskContactRecordName;

    @FindBy(xpath="//td[text()='Consumer']/../td[4]")
    public WebElement consumerLinkedToGeneralTaskConsumerProfileName;

    @FindBy(xpath="//td[text()[contains(.,'Case')]]")
    public WebElement consumerLinkedToGeneralTaskCaseName;

    @FindBy(xpath="//td[text()='Consumer']")
    public WebElement consumerLinkedToGeneralTaskConsumerType;

    @FindBy(xpath="//tbody/tr[3]/td[5]")
    public WebElement consumerLinkedToGeneralTaskNullType;

    @FindBy(xpath="//td[text()[contains(.,'Active')]]")
    public WebElement consumerLinkedToGeneralTaskConsumerProfileStatus;

    @FindBy(xpath="//tbody/tr[3]/td[7]")
    public WebElement consumerLinkedToGeneralTaskCaseStatus;

    @FindBy(xpath = "//td/i[text()='person']/../following-sibling::td[1]")
    public WebElement consumerIdField;

    @FindBy(xpath = "//td/i[text()='person']/../following-sibling::td[2]")
    public WebElement consumerNameField;

    @FindBy(xpath = "//td/i[text()='person']/../following-sibling::td[4]")
    public WebElement consumerStatusDateField;

    @FindBy(xpath = "//td/i[text()='person']/../following-sibling::td[3]")
    public WebElement consumerTypeField;

    @FindBy(xpath = "//td/i[text()='person']/../following-sibling::td[5]")
    public WebElement consumerStatusField;
}




