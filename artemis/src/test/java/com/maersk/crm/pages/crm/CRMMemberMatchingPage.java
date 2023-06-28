package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMMemberMatchingPage {

    

    public CRMMemberMatchingPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }
    @FindBy(xpath = "//div[@class='py-2 px-2 mx-layout-top-drawer-fields float-left']/../following-sibling::p")
    public WebElement memberMatchingPageHeader;

    @FindBy(xpath = "//*[@title='Keyboard Back Arrow']")
    public WebElement memberMatchingBackArrow;

    @FindBy(xpath = "//p[.='APPLICATION ID']/ancestor::td/following-sibling::td/div/p")
    public WebElement applicationIDText;

    @FindBy(xpath = "//h6[@class='mx-pm-status']") // class name mx-pm-status
    public WebElement applicationStatusText;

    @FindBy(xpath = "//*[@class='mx-app-address align-items-center']/child::p[1]")
    public WebElement addressLineOneTwo;

    @FindBy(xpath = "//*[@class='mx-app-address align-items-center']/child::p[2]")
    public WebElement cityStateZipText;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[3]/child::td[2]/div/p")
    public List<WebElement> firstConsumerDetails;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[4]/child::td[2]/div/p")
    public List<WebElement> secondConsumerDetails;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[5]/child::td[2]/div/p")
    public List<WebElement> thirdConsumerDetails;

    @FindBy(xpath = "//i[contains(text(),'person')]")
    public WebElement piIcon;

   public By firstConsumerDetailsBy = By.xpath("//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[3]/child::td[2]/div/p");
   public By secondConsumerDetailsBy  = By.xpath("//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[4]/child::td[2]/div/p");

    @FindBy(xpath = "//button[.='NEW']")
    public WebElement newButton;

    @FindBy(xpath = "//input[@type='checkbox']")
    public List<WebElement> matchingSelectBox;

    @FindBy(xpath = "//span[.='DUPLICATE']/..")
    public WebElement duplicateButton;

    @FindBy(xpath = "//span[.='check']")
    public WebElement duplicateButtonContinue;

    @FindBy(xpath = "//span[.='clear']")
    public WebElement duplicateButtonCancel;

    @FindBy(xpath = "//p[.='Do you want to mark the INBOUND application as a DUPLICATE and stop processing?']")
    public WebElement duplicateApplicationWarningMessage;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[2]/child::td/div/label/span[2]")
    public List<WebElement> potentialListOfMatchingAppId;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[2]/td/div/span/p[1]")
    public List<WebElement> listAddressLineOne;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[2]/td/div/span/p[2]")
    public List<WebElement> listAddressLineTwo;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[3]/child::td/div/p[1]")
    public List<WebElement> firstRowApplicantIdList;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[4]/child::td/div/p[1]")
    public List<WebElement> secondRowApplicantIdList;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[5]/child::td/div/p[1]")
    public List<WebElement> thirdRowApplicantIdList;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[6]/child::td/div/p[1]")
    public List<WebElement> fourthRowApplicantIdList;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[3]/child::td/div/p[2]")
    public List<WebElement> firstRowFullNameList;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[3]/child::td/div/p[3]")
    public List<WebElement> firstRowSSNlist;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[3]/child::td/div/p[4]")
    public List<WebElement> firstRowDOBlist;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[3]/child::td/div/p[5]")
    public List<WebElement> firstRowProgramsList;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[2]/td/div/h6")
    public List<WebElement> firstRowAppStatusList;

    @FindBy(xpath = "//h5[.='No Matches Found']")
    public WebElement noMatchesFoundMsg;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr/child::td[4]/div/p")
    public List<WebElement> potentialMatchAppColumnOne;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr/child::td[2]/div/p")
    public List<WebElement> inbApplicationColumnList;

    @FindBy(xpath = "//button[.='RESEARCH']")
    public WebElement researchButton;

    @FindBy(xpath = "//h5[text()='POTENTIAL MATCHES']//ancestor::tr//following-sibling::tr//td//input")
    public WebElement caseIdCheckBox;

    @FindBy(xpath = "//span[text()='LINK']//ancestor::button")
    public WebElement linkButton;

    @FindBy(xpath = "//span[text()='NEW CASE']//ancestor::button")
    public WebElement newCaseButton;

    @FindBy(xpath = "//span[text()='Link Incoming Application']")
    public WebElement linkIncomingApplication;

    @FindBy(xpath = "//span[text()='Link Incoming Application']//following-sibling::span[1]//a")
    public WebElement linkIncomingApplicationApplicationID;

    @FindBy(xpath = "//span[text()='Create New Consumer Profile for']")
    public WebElement createNewConsumerProfileText;

    @FindBy(xpath = "//span[text()='Create New Consumer Profile for']//following-sibling::span[1]/a")
    public List<WebElement> createNewConsumerProfileIDList;

    @FindBy(xpath = "//span[text()='Link Consumer']//following-sibling::span[1]/a")
    public List<WebElement> linkConsumerIDList;

    @FindBy(xpath = "//span[text()='to Case ']//following-sibling::span[1]/a")
    public List<WebElement> toCaseIDlist;

    @FindBy(xpath = "//span[text()=' and Link To Case ']//following-sibling::span[1]/a")
    public List<WebElement> linkToCaseIDList;

    @FindBy(xpath = "//span[contains(text(),'New Case')]")
    public List<WebElement> newCaseTextList;

    @FindBy(xpath = "//span[contains(text(),'Existing Case')]")
    public List<WebElement> existingTextList;

    @FindBy(xpath = "(//div[@class='row mx-card-01 mx-0'])[2]")
    public WebElement newCaseIDValue;

    @FindBy(xpath = "//i[contains(text(),'person')]//ancestor::p//following-sibling::div//button[@class='MuiButtonBase-root MuiToggleButton-root mx-btn-add mx-border-right']")
    public WebElement addPIButton;

    @FindBy(xpath = "//h5[@class='pb-4 mdl-color-text--grey-500 text-center justify-center']")
    public WebElement matchedCaseSummarySection;

    @FindBy(xpath = "//span[.='check']")
    public WebElement actionsSaveButton;

    @FindBy(id = "mui-component-select-reasons")
    public WebElement researchReasons;

    @FindBy(id = "notes")
    public WebElement sendToResearchNotes;

    @FindBy(xpath = "//span[.='check']")
    public WebElement sendToResearchSaveButton;

    @FindBy(xpath = "//span[.='check']//following::button")
    public WebElement sendToResearchCancelButton;

    @FindBy(xpath = "//p[.='If you continue, all the captured information will be lost']")
    public WebElement backArrowWarningMessage;

    @FindBy(xpath = "//span[text()='Continue']")
    public WebElement warningMessageContinueButton;

    @FindBy(xpath = "//span[text()='Cancel']")
    public WebElement warningMessageCancelButton;

    @FindBy(xpath = "//p[text()='REASON  is required and cannot be left blank']")
    public WebElement reasonRequiredMessage;

    @FindBy(xpath = "//tr[@class='col-12 px-0 d-inline-flex h-auto mb-2'][2]//h5")
    public WebElement matchedMemberPINoSelection;

    @FindBy(xpath = "//tr[@class='col-12 px-0 d-inline-flex h-auto mb-2'][2]//td[3]//p")
    public List<WebElement> matchedMemberPIDetails;

    @FindBy(xpath = "//tr[@class='col-12 px-0 d-inline-flex h-auto mb-2'][3]//h5")
    public WebElement matchedMemberAMNoSelection;

    @FindBy(xpath = "//tr[@class='col-12 px-0 d-inline-flex h-auto mb-2'][2]//td[2]//p")
    public List<WebElement> matchedMemberAMDetails;

    @FindBy(xpath = "//span[text()='person_add_alt']/../..")
    public List<WebElement> matchingConsumerId;

    @FindBy(xpath = "//tr[@class='col-12 px-0 d-inline-flex h-auto mb-2'][4]//td[3]//p")
    public List<WebElement> secondMatchedMemberAMDetails;

    @FindBy(xpath = "(//*[@class='row mx-0 mb-2'])[4]")
    public WebElement firstAMProgramDetails;

    @FindBy(xpath = "(//*[@class='mx-table-fixcol2 '])[3]//p[1]")
    public WebElement firstAMMemberID;

    @FindBy(xpath = "(//*[@class='mx-col-match1'])[3]//p[1]")
    public WebElement onlyCaseMemberID;

    @FindBy(xpath = "//p[text()='DEADLINE DATE cannot be before RECEIVED DATE']")
    public WebElement deadlineDateWarningMessage;

    @FindBy(xpath = "(//i[contains(text(), 'edit')])[1]")
    public WebElement editIconForApplication;

    @FindBy(xpath = "(//i[contains(text(), 'edit')])[2]")
    public WebElement editIconForPrimaryIndividual;

    @FindBy(xpath = "(//i[contains(text(), 'edit')])[3]")
    public WebElement editIconFor1stApplicationMember;

    @FindBy(xpath = "//i[text()='folder_shared']/../..//i")
    public WebElement matchedCaseID;

    @FindBy(xpath = "(//i[text()='person']/../..)[1]")
    public WebElement matchedConsumerID;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[5]/child::td/div/p[1]")
    public List<WebElement> matchedConsumerIdOnMatchingData;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[5]/child::td/div/p[2]")
    public List<WebElement> matchedConsumerNameOnMatchingData;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[5]/child::td/div/p[3]")
    public List<WebElement> matchedConsumerSSNonMatchingData;

    @FindBy(xpath = "//*[@class='table table-borderless mx-table-fixedcolumn']/child::tbody/child::tr[5]/child::td/div/p[4]")
    public List<WebElement> matchedConsumerDOBonMatchingData;

    @FindBy(xpath = "//span[contains(text(),'+')]")
    public WebElement existingAppCount ;
    @FindBy(xpath = "//span[@class='mdl-color-text--red-800']")
    public WebElement programType;

    @FindBy(xpath = "//span[@class='mb-0 mx-eligible-status-sm mx-eligible-status--eligible ml-2']")
    public WebElement programStatusEligible;

    @FindBy(xpath = "//span[@class='mb-0 mx-eligible-status-sm mx-eligible-status--partially-enrolled ml-2']")
    public WebElement programStatusPartiallyEnrolled;

    @FindBy(xpath = "//span[@class='mb-0 mx-eligible-status-sm mx-eligible-status--enrolled ml-2']")
    public WebElement programStatusEnrolled;

}
