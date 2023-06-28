package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMWOrkQueuePage {
    

    public CRMWOrkQueuePage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "(//h5[text()='TASK LIST']/..//table/tbody/tr[1])[1]//td[6]")
    public WebElement statusCreate;

    @FindBy(xpath = "//h5[contains(text(),'TASK LIST')]/..//table//thead//tr//th")
    public List<WebElement> workQueueColumns;

    @FindBy(xpath = "(//h5[text()='TASK LIST']/..//table/tbody/tr[1])[1]//td[1]")
    public WebElement expandArrow;

//    @FindBy(xpath = "//th[contains(text(),'TASK ID')]")
    @FindBy(xpath = "//*[text()='TASK ID']")
    public WebElement taskIDColumnHeader;

    @FindBy(xpath = "(//h5[text()='TASK LIST']/..//table/tbody/tr[1])[1]//td")
    public List<WebElement> workQueueColumnsValues;

    @FindBy(xpath = "(//h5[text()='TASK LIST']/..//table/tbody/tr[1])[1]/following-sibling::tr//p[1]")
    public List<WebElement> expandedHeader;

    @FindBy(xpath = "(//h5[text()='TASK LIST']/..//table/tbody/tr[1])[1]/following-sibling::tr//p[2]")
    public List<WebElement> expandedHeaderValue;

    @FindBy(xpath = "//span[text()='CLAIM NEXT']")
    public WebElement claimNext;

    @FindBy(xpath = "(//h5[text()='TASK LIST']/..//table/tbody/tr[1])[1]//td[2]")
    public WebElement taskID;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[2]")
    public List<WebElement> allRowTaskID;

    @FindBy(xpath = "//i[text()='check']")
    public WebElement saveBtn;

    @FindBy(xpath = "//ul[@class='pagination']//li")
    public List<WebElement> pageNumbers;

    @FindBy(xpath = "//span[text()='arrow_forward']/parent::a")
    public WebElement lnkArrowForward;

    @FindBy(xpath = "//h6[text()='TASK DETAILS']")
    public WebElement taskDetailsSlider;

    @FindBy(xpath = "//span[text()='SUCCESS MESSAGE']")
    public WebElement successMessage;

    @FindBy(xpath = "//span[text()='Task Successfully Saved']")
    public WebElement successMessageTxt;

    @FindBy(xpath = "//h5[text()='No Records Available']")
    public WebElement noRecord;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[6]")
    public List<WebElement> allRowStatus;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[3]")
    public List<WebElement> allRowType;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[4]")
    public List<WebElement> allRowPriority;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[5]")
    public List<WebElement> allRowStatusDate;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[7]")
    public List<WebElement> allRowDueDate;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[8]")
    public List<WebElement> allRowDueIn;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[9]")
    public List<WebElement> allRowCaseId;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[10]")
    public List<WebElement> allRowConsumerId;

    @FindBy(xpath = "//h5[text()='TASK LIST']/..//table/tbody/tr//td[1]//button//i")
    public List<WebElement> allRowExpandArrow;

    @FindBy(xpath = "//button[text()='INITIATE']")
    public List<WebElement> allRowInitiateBtn;

    @FindBy(xpath = "//button[text()='INITIATE']")
    public WebElement firstRowInitiateBtn;

    @FindBy(xpath = "//input[@id='taskStatus']/..")
    public WebElement taskSliderStatus;

    @FindBy(xpath = "//p[text()='SOURCE']/following-sibling::P")
    public List<WebElement> sourceVlu;

    @FindBy(xpath = "//p[text()='CREATED BY']/following-sibling::p")
    public List<WebElement> createdByVlu;

    @FindBy(xpath = "//p[text()='CREATED ON']/following-sibling::p")
    public List<WebElement> createdOnVlu;

    @FindBy(xpath = "//p[text()='NAME']/following-sibling::p")
    public List<WebElement> nameVlu;

    @FindBy(xpath = "//p[text()='TASK INFORMATION']/following-sibling::p")
    public List<WebElement> taskInfoVlu;

    @FindBy(xpath = "//p[text()='TASK NOTES']/following-sibling::p")
    public List<WebElement> taskNoteVlu;

    @FindBy(xpath = "//p[text()='ASSIGNEE']/following-sibling::p")
    public List<WebElement> assigneeVlu;

    @FindBy(xpath = "//div[@id='menu-taskStatus']//ul/li")
    public List<WebElement> taskSliderStatusDrDw;

    @FindBy(xpath = "//input[@id='disposition']/..")
    public WebElement taskSliderDisposition;

    @FindBy(xpath = "//div[@id='menu-disposition']//ul/li")
    public List<WebElement> taskSliderDispositionDrDw;

    @FindBy(xpath = "//span[text()='The DISPOSITION is required and cannot be left blank.']")
    public WebElement mandatoryErrorMsgFrDisposition;

    @FindBy(xpath = "//tbody[1]/tr[2]/td[1]/div[1]/div[1]/div[2]/div[1]/p[2]")
    public WebElement taskinfovalue;

    @FindBy(xpath = "//tbody[1]/tr[2]/td[1]/div[1]/div[1]/div[1]/div[2]/p[2]")
    public WebElement createdByValue;

    @FindBy(xpath = "//tbody[1]/tr[1]/td[2]/p[1]/b[1]")
    public WebElement getTaskID;

    @FindBy(xpath = "//h5[@class='text-center mdl-color--grey-50 p-5']")
    public WebElement noRecordsAvailable;

    @FindBy(xpath = "//span[contains(text(),'MY TASKS')]")
    public WebElement mytaskTab;

    @FindBy(xpath = "//tr//td[3]//p[1]")
    public List<WebElement> taskTypes;

    @FindBy(xpath = "//tr[1]//td[2]")
    public WebElement firstTaskInTable;

    @FindBy(xpath = "//h6[contains(text(),'Created')]")
    public WebElement createdStatus;

    @FindBy(xpath = "//div[@id='mui-component-select-taskStatus']")
    public WebElement taskStatusTaskDetPage;

    @FindBy(xpath = "//h6[contains(text(),'Escalated')]")
    public WebElement escStatus;

}
