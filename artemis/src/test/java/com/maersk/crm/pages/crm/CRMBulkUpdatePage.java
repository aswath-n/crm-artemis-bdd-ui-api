package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMBulkUpdatePage {
    

    public CRMBulkUpdatePage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }
    @FindBy(xpath = "//span[text()='BULK UPDATES']/..")
    public WebElement bulkUpdateBtn;

    @FindBy(xpath = "//h5[contains(text(),'TASK LIST')]/..//table/tbody/tr//td[3]")
    public List<WebElement> taskIdBulkUpdate;

    @FindBy(xpath = "//p[text()='BULK UPDATES']")
    public WebElement bulUpdateHeader;

    @FindBy(xpath = "//span[text()='NEXT']")
    public WebElement nextBtn;

    @FindBy(xpath = "//h6[text()='PRIORITY']/../..//input/..")
    public WebElement priorityDD;

    @FindBy(xpath = "//h6[text()='ASSIGNEE']/../..//input/..")
    public WebElement assigneeDD;

    @FindBy(id = "notes")
    public WebElement noteTxt;

    @FindBy(xpath = "//a[contains(text(),'CANCEL')]")
    public WebElement cancelBtn;

    @FindBy(xpath = "//h5[contains(text(),'TASK LIST')]/..//table/tbody/tr//td[4]")
    public List<WebElement> taskTypes;

    @FindBy(xpath = "//h5[contains(text(),'TASK LIST')]/..//table/tbody/tr//td[5]")
    public List<WebElement> priorities;

    @FindBy(xpath = "//h5[contains(text(),'TASK LIST')]/..//table/tbody/tr//td[6]")
    public List<WebElement> statuses;

    @FindBy(xpath = "//h5[contains(text(),'TASK LIST')]/..//table/tbody/tr//td[7]")
    public List<WebElement> statusDates;

    @FindBy(xpath = "//h5[contains(text(),'TASK LIST')]/..//table/tbody/tr//td[8]")
    public List<WebElement> dueDates;

    @FindBy(xpath = "//h5[contains(text(),'TASK LIST')]/..//table/tbody/tr//td[9]")
    public List<WebElement> myWorkSpaceDates;

    @FindBy(xpath = "//h5[contains(text(),'TASK LIST')]/..//table/tbody/tr//td[10]")
    public List<WebElement> dueIns;

    @FindBy(xpath = "//h5[contains(text(),'TASK LIST')]/..//table/tbody/tr//td[11]")
    public List<WebElement> caseIds;

    @FindBy(xpath = "//h5[contains(text(),'TASK LIST')]/..//table/tbody/tr//td[12]")
    public List<WebElement> consumerIds;

    @FindBy(xpath = "//h5[contains(text(),'TASK LIST')]/..//table/tbody/tr//td[13]")
    public List<WebElement> applicationIds;

    @FindBy(xpath = "//span[text()='All Tasks Successfully Updated']")
    public WebElement successMsg;

    @FindBy(xpath = "//h3[text()='Bulk Update Successful']/..//p")
    public WebElement bulkUpdateSuccessMsg;

    @FindBy(xpath = "//*[contains(text(),'BACK TO TASK SEARCH')]")
    public WebElement backToTaskSearchBtn;

    @FindBy(xpath = "//*[contains(text(),'CONFIRM BULK UPDATES')]")
    public WebElement confirmBulkUpdatesBtn;
}
