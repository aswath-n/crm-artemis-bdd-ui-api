package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.security.cert.X509Certificate;
import java.util.List;

public class CRMTaskServiceRequiestTabPage {
    

    public CRMTaskServiceRequiestTabPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[1]")
    public List<WebElement> expandTaskArrows;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[2]")
    public List<WebElement> taskIDs;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[3]")
    public List<WebElement> taskTypes;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[4]")
    public List<WebElement> priorities;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[5]")
    public List<WebElement> taskStatuses;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[6]")
    public List<WebElement> dueDates;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[7]")
    public List<WebElement> dueIns;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[8]")
    public List<WebElement> consumerNames;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[9]//button")
    public List<WebElement> taskInitiateButtons;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[9]")
    public List<WebElement> taskInitiateButtonsText;

    @FindBy(xpath = "//p[text()='SOURCE']/following-sibling::p")
    public List<WebElement> sources;

    @FindBy(xpath = "//p[contains(text(),'CASE ID')]/following-sibling::p")
    public List<WebElement> caseIds;

    @FindBy(xpath = "//p[text()='ASSIGNEE']/following-sibling::p")
    public List<WebElement> assignees;

    @FindBy(xpath = "//p[text()='CREATED BY']/following-sibling::p")
    public List<WebElement> createdBys;

    @FindBy(xpath = "//p[text()='TASK INFORMATION']/following-sibling::p")
    public List<WebElement> taskInfos;

    @FindBy(xpath = "//p[text()='CREATED ON']/following-sibling::p")
    public List<WebElement> createdOns;

    @FindBy(xpath = "//button[text()='INITIATE']")
    public WebElement initiateButton;

    @FindBy(xpath = "//p[text()='STATUS DATE']/following-sibling::p")
    public List<WebElement> statusdates;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[8]")
    public List<WebElement> taskDueIns;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[9]")
    public List<WebElement> taskCaseIDs;

    @FindBy(xpath = "(//div//p[1])[4]")
    public WebElement consumerFullNameFromTopLeftScreen;

    @FindBy(xpath = "//p[text()='CASE ID']/following-sibling::p")
    public WebElement caseId;

    @FindBy(xpath = "//*[contains(text(),'TASK LIST')]/..//table/tbody/tr")
    public List<WebElement> taskListRows;

    @FindBy(xpath = "(//div//p[2])[3]")
    public WebElement caseIDFromTopLeftScreen;

    @FindBy(xpath = "//*[text()='TASK ID']")
    public WebElement columnTaskId;

    @FindBy(xpath = "//*[text()='TYPE']")
    public WebElement typeColumn;

    @FindBy(xpath = "//*[text()='PRIORITY']")
    public WebElement priorityColumn;

    @FindBy(xpath = "//*[text()='STATUS']")
    public WebElement statusColumn;

    @FindBy(xpath = "//*[text()='DUE DATE']")
    public WebElement dueDateColumn;

    @FindBy(xpath = "//*[text()='DUE IN']")
    public WebElement dueInColumn;

    @FindBy(xpath = "//*[text()='CONSUMER NAME']")
    public WebElement consumerNameColumn;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//*[@class=\"pagination\"]//li")
    public List<WebElement> lnkPageNations;

    @FindBy(xpath = "//th[contains(text(),'ID')]")
    public WebElement idField;

    @FindBy(xpath = "//th[contains(text(),'NAME')]")
    public WebElement nameField;

    @FindBy(xpath = "//th[contains(text(),'TYPE')]")
    public WebElement typeField;

    @FindBy(xpath = "//th[contains(text(),'STATUS DATE')]")
    public WebElement statusDateField;

    @FindBy(xpath = "//thead/tr[1]/th[7]")
    public WebElement statusField;

    @FindBy(xpath = "//tbody/tr[1]/td[3]")
    public WebElement frstidField;

    @FindBy(xpath = "//tbody/tr[1]/td[4]")
    public WebElement frstnameField;

    @FindBy(xpath = "//tbody/tr[1]/td[5]")
    public WebElement frsttypeField;

    @FindBy(xpath = "//tbody/tr[1]/td[6]")
    public WebElement frststatusDateField;

    @FindBy(xpath = "//thead/tr[1]/th[7]")
    public WebElement frststatusField;

    @FindBy(xpath = "//span[contains(text(),'Continue')]")
    public WebElement continueButtonOnTask;

    @FindBy(xpath = "//tbody[1]/tr[1]/td[2]")
    public WebElement frstTaskId;

   @FindBy(xpath = "//tbody[1]/tr[1]/td[3]")
   public WebElement serReqId;

    @FindBy(xpath = "//*[text()='SERVICE REQUEST DETAILS']")
    public WebElement serviceReqDet;

    @FindBy(xpath = "//*[contains(text(),'TASK LIST')]/..//table/tbody/tr/td[2]")
    public List<WebElement> taskIds;

    @FindBy(xpath = "//*[text()='GetInsured CASE ID']/../p/following-sibling::p")
    public WebElement getInsuredCaseIdTSRTab;

    @FindBy(xpath = "//*[text()='Case']")
    public List<WebElement> CasesInTask;
}
