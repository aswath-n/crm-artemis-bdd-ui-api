package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CRMContactRecordDashboardPage {


    

    public CRMContactRecordDashboardPage() {

        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//a[text()='Active Contact']")
    public WebElement activeContactTab;

    @FindBy(xpath = "//a[text()='Demographic Info']")
    public WebElement demographicInfoTab;

    @FindBy (xpath ="//a[contains(text(),'Case & Contact Details')]")
    public WebElement caseContactDetailsTab;

    @FindBy (xpath ="//a[contains(text(),'CONTACT DETAILS')]")
    public WebElement contactDetailsTab;

    @FindBy (xpath ="//a[text()='Task & Service Request']/..")
    public WebElement taskServiceRequestTab;

    @FindBy(xpath = "//a[text()='Profile Info']")
    public WebElement profileInfoTab;

    @FindBy(xpath = "//a[text()='History']")
    public WebElement historyTab;

    @FindBy(xpath = "//a[text()='Program & Benefit Info']")
    public WebElement programBenefitInfoTab;

    @FindBy (xpath ="//*[@class='nav-link active']")
    public WebElement activeTab;

    @FindBy(xpath="//*[text()='Contact Record Id']")
    public WebElement contactRecordId;

    @FindBy(xpath="//*[text()='Consumer First Name']")
    public WebElement consumerFirstName;

    @FindBy(xpath="//*[text()='Consumer Last Name']")
    public WebElement consumerLastName;

    @FindBy(xpath="//*[text()='Date']")
    public WebElement date;

    @FindBy(xpath="//*[text()='Internal Case Id']")
    public WebElement caseId;

    @FindBy(xpath="//*[text()='Internal Consumer Id']")
    public WebElement consumerId;

    @FindBy(xpath="//div[@class='col-12 mt-4 mb-4']/button[1]")
    public WebElement searchBtn;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]")
    public WebElement resultsHeader;

    @FindBy(xpath="//div[@class='col-12 mt-4 mb-4']/button[2]")
    public WebElement cancelBtn;

    @FindBy (xpath ="//h5[contains(text(),'CONTACT DETAILS')]/..//following-sibling::div//h5[text()='WRAP-UP AND CLOSE']")
    public WebElement wrapUpClose;

	@FindBy(xpath="//*[text()='Case/Consumer Search']")
    public WebElement caseConsumerSearchTab;

    @FindBy(id = "consumerLN")
    public WebElement consumerLN;

    @FindBy(id = "consumerFN")
    public WebElement consumerFN;

    @FindBy(name = "crmContactRecordNo")
    public WebElement recordId;

    @FindBy(id = "seaConDOB")
    public WebElement recordDate;

    @FindBy(id = "seacaseID")
    public WebElement recordCaseId;

    @FindBy(id = "crmConsumerID")
    public WebElement recordConsumerId;

    @FindBy(xpath = "//div[@class='mx-menu-icon-wrapper'][1]")
    public WebElement caseConsumerSidebarIcon;

	@FindBy (xpath ="//h5[text()='CONTACT HISTORY']")
    public WebElement contactHistory;

    @FindBy (xpath ="//h1[contains(text(),'CONTACT DETAILS')]/../../following-sibling::div/div/div/h1[.='publishWRAP-UP AND CLOSE']")
    public WebElement wrapUpCloseSec;

    @FindBy (xpath ="//a[text()='History']/..")
    public WebElement historyScreen;

    @FindBy(id = "consumerName")
    public WebElement conDrop;

    @FindBy(id = "businessEvents")
    public WebElement conBUSeVENTDrop;

    @FindBy(id = "processDateRange")
    public WebElement processDateRange;

    @FindBy(id = "channel")
    public WebElement channeldrop;

    @FindBy(xpath = "//div[2]/div[1]/div[1]/span[1]/h5[1]")
    public WebElement noRecordsAva;

    @FindBy(xpath = "//body/div[@id='menu-']/div[3]/ul[1]/li")
    public List<WebElement> consumerNameDrop;

    @FindBy(xpath = "//body/div[@id='root']/div[1]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]")
    public WebElement consumerDropHistoryTab;

    @FindBy(xpath = "//body/div[@id='root']/div[1]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[4]/div[1]/div[1]")
    public WebElement channelDropHistoryTab;

    @FindBy(xpath = "//body/div[@id='menu-']/div[3]/ul[1]/li")
    public List<WebElement> lstChannelDrop;

    @FindBy(xpath = "//div[2]/h5[1]")
    public WebElement busEventName;

    @FindBy(xpath = "//ul[1]/li[1]/div[1]")
    public WebElement channelName;

    @FindBy(xpath = "//ul[1]/li[1]/p[1]")
    public WebElement processDatehis;

    @FindBy(xpath = "//p[contains(text(),'CREATED BY')]")
    public WebElement createdByHis;

    @FindBy(xpath = "//p[contains(text(),'ELIGIBILITY START DATE')]")
    public WebElement elstartDatehis;

    @FindBy(xpath = "//p[contains(text(),'ELIGIBILITY END DATE')]")
    public WebElement elEndDatehis;

    @FindBy(xpath = "//div[2]/div[4]/p[1]")
    public WebElement elProgramCode;

    @FindBy(xpath = "//div[2]/div[2]/div[5]/p[1]")
    public WebElement elsubProgramCode;

    @FindBy(xpath = "//p[contains(text(),'LINKS -')]")
    public WebElement linksHis;

    @FindBy(xpath = "//div[@id='mui-component-select-businessEvents']")
    public WebElement busDrop;

    @FindBy(xpath = "//li[@role='option']")
    public List<WebElement> businessEventsdrop;

    @FindBy(xpath = "//body/div[@id='menu-']/div[1]")
    public WebElement bodyClick;

    @FindBy(xpath = "//p[contains(text(),'ENROLLMENT START DATE')]")
    public WebElement enstartDatehis;

    @FindBy(xpath = "//p[contains(text(),'ENROLLMENT END DATE')]")
    public WebElement enEndDatehis;

    @FindBy(xpath = "//li/div[2]")
    public List<WebElement> listOfEvents;

    @FindBy(xpath = "(//h5[contains(text(),'Reconciliation')]/parent::div/div)[4]/p/button")
    public WebElement expandingCaratForEligibilityTransactionDetails;

    @FindBy(xpath = "//td[contains(text(),'BENEFIT STATUS')]")
    public WebElement eligibilityBenefitStatus;

    @FindBy(xpath = "//td[contains(text(),'CATEGORY CODE')]")
    public WebElement eligibilityCategoryCode;

    @FindBy(xpath = "//td[contains(text(),'COVERAGE CODE')]")
    public WebElement eligibilityCoverageCode;

    @FindBy(xpath = "//td[contains(text(),'END DATE')]")
    public WebElement eligibilityEndDate;

    @FindBy(xpath = "//td[contains(text(),'END REASON')]")
    public WebElement eligibilityEndReason;

    @FindBy(xpath = "//td[contains(text(),'EXEMPTION CODE')]")
    public WebElement eligibilityExemptionCode;

    @FindBy(xpath = "//td[contains(text(),'PROGRAM CODE')]")
    public WebElement eligibilityProgramCode;

    @FindBy(xpath = "//td[contains(text(),'START DATE')]")
    public WebElement eligibilityStartDate;

    @FindBy(xpath = "//td[contains(text(),'SUB-PROGRAM CODE')]")
    public WebElement eligibilitySubProgramCode;

    @FindBy(xpath = "(//h5[contains(text(),'Outbound Plan Change')]/parent::div/div)[4]/p/button")
    public WebElement expandingCaratForEnrollementTransactionDetails;

    @FindBy(xpath = "//td[contains(text(),'ENROLLMENT END DATE')]")
    public WebElement enrollementEndDate;

    @FindBy(xpath = "//td[contains(text(),'ENROLLMENT START DATE')]")
    public WebElement enrollementStartDate;

    @FindBy(xpath = "//td[contains(text(),'ENROLLMENT TYPE')]")
    public WebElement enrollementType;

    @FindBy(xpath = "//td[contains(text(),'PLAN CODE')]")
    public WebElement planCode;

    @FindBy(xpath = "//td[contains(text(),'REJECT REASON')]")
    public WebElement rejectReason;

    @FindBy(xpath = "//td[contains(text(),'SELECTION STATUS')]")
    public WebElement selectionStatus;

    @FindBy(xpath = "//td[contains(text(),'SELECTION STATUS')]")
    public WebElement enrollementOldValue;

    @FindBy(xpath = "//td[contains(text(),'SELECTION STATUS')]")
    public WebElement enrollementUpdatedValue;

    @FindBy(xpath = "//div[@id='consumerName']")
    public WebElement historyScreenConsumerNameDropDown;

    @FindBy(xpath = "//div[@id='businessEvents']")
    public WebElement historyScreenBusinessEventsDropDown;

    @FindBy(xpath = "//div[@id='processDateRange']")
    public WebElement historyScreenProcessDateRangeDropDown;

    @FindBy(xpath = "//ul[@aria-labelledby='processDateRange']//li")
    public List<WebElement> processDateRangeDropDownValues;

    @FindBy(xpath = "//label[@id='channel']")
    public WebElement historyScreenChanneleDropDown;

    @FindBy(xpath = "//div[@id='channel']")
    public WebElement channelDropDown;

    @FindBy(xpath = "//button[contains(text(),' RESET FILTER')]")
    public WebElement resetFilterButton;
}
