package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ATSMissingInformationPage {

    

    public ATSMissingInformationPage() {

        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//span[@title='Application View']")
    public WebElement applicationIcon;

    @FindBy(xpath = "//span[@title='Keyboard Back Arrow']")
    public WebElement backArrow;

    @FindBy(xpath = "//a[contains(text(),'MISSING INFO')]")
    public WebElement missingInfoTabLink;

    @FindBy(xpath = "//h2[contains(text(),' MISSING INFO DETAILS')]")
    public WebElement missingInfoDetailsHeader;

    @FindBy(xpath = "//p[contains(text(),'APPLICATION ID')]/following-sibling::h6")
    public WebElement applicationIDValue;

    @FindBy(xpath = "//div[@class='row mb-3']/child::div/p")
    public List<WebElement> applicationInfoLabels;

    @FindBy(xpath = "//p[contains(text(),'EXTERNAL APP ID')]/following-sibling::h6")
    public WebElement externalAppIDValue;

    @FindBy(xpath = "//p[contains(text(),'DEADLINE DATE')]/following-sibling::h6")
    public WebElement deadlineDateValue;

    @FindBy(xpath = "//p[contains(text(),'PRIORITY')]/following-sibling::h6")
    public WebElement priorityValue;

    @FindBy(xpath = "//p[contains(text(),'SIGNATURE DATE')]/following-sibling::h6")
    public WebElement signatureDateValue;

    @FindBy(xpath = "//p[contains(text(),'APPLICATION TYPE')]/following-sibling::h6")
    public WebElement applicationTypeValue;

    @FindBy(xpath = "//p[contains(text(),'CYCLE')]/following-sibling::h6")
    public WebElement cycleValue;

    @FindBy(xpath = "//p[contains(text(),'RECEIVED DATE')]/following-sibling::h6")
    public WebElement receivedDateValue;

    @FindBy(xpath = "//p[contains(text(),'CHANNEL')]/following-sibling::h6")
    public WebElement channelValue;

    @FindBy(xpath = "//p[contains(text(),'CREATE DATE')]/following-sibling::h6")
    public WebElement createDateValue;

    @FindBy(xpath = "//p[contains(text(),'LAST UPDATED DATE')]/following-sibling::h6")
    public WebElement lastUpdatedDateValue;

    @FindBy(xpath = "//h2[contains(text(),' MISSING INFO DETAILS')]/following-sibling::button")
    public WebElement addMissingInfoButton;

    @FindBy(xpath = "//span[contains(text(), ' ADD MISSING INFO')]/..")
    public WebElement addMissingInfoButtonFunctionality;

    @FindBy(xpath = "//h2[contains(text(), ' MISSING INFO DETAILS')]/following::table/thead/tr/th")
    public List<WebElement> missingInfoLabels;

    @FindBy(id = "mui-component-select-miAddCategory")
    public WebElement miAddCategory;

    @FindBy(id = "mui-component-select-miAddType")
    public WebElement miAddType;

    @FindBy(id = "mui-component-select-miAddFrom")
    public WebElement miAddFrom;

    @FindBy(id = "mui-component-select-miAddNeedFor")
    public WebElement miAddNeedFor;

    @FindBy(id = "miAddComments")
    public WebElement miAddComments;

    @FindBy(xpath = "//h2[contains(text(), ' MISSING INFO DETAILS')]/following::table/tbody/tr[1]/td[7]")
    public WebElement rowOneStatus;

    @FindBy(xpath = "//h2[contains(text(), ' MISSING INFO DETAILS')]/following::table/tbody/tr[1]/td[8]/button[.='check']")
    public WebElement miSaveButton;

    @FindBy(xpath = "//h2[contains(text(), ' MISSING INFO DETAILS')]/following::table/tbody/tr[1]/td[8]/button[.='close']")
    public WebElement miCloseButton;

    @FindBy(xpath = "//h2[contains(text(), ' MISSING INFO DETAILS')]/following::table/tbody/tr[1]/td")
    public List<WebElement> savedMissingInfoListRowOne;

    @FindBy(xpath = "//h2[contains(text(), ' MISSING INFO DETAILS')]/following::table/tbody/tr[4]/td")
    public List<WebElement> savedMissingInfoListRowTwo;

    @FindBy(xpath = "//h2[contains(text(), ' MISSING INFO DETAILS')]/following::table/tbody/tr[5]/td")
    public List<WebElement> savedMissingInfoListRowThree;

    @FindBy(xpath = "//h2[contains(text(), ' MISSING INFO DETAILS')]/following::table/tbody/tr[7]/td")
    public List<WebElement> savedMissingInfoListRowFour;

    @FindBy(xpath = "//i[contains(text(),'chevron_right')]")
    public List<WebElement> listOfChevronRightButtons;

    @FindBy(xpath = "//i[contains(text(),'keyboard_arrow_down')]")
    public List<WebElement> listOfArrowDown;

    @FindBy(xpath = "//i[contains(text(),'more_horiz')]")
    public WebElement viewAllCommentbutton;

    @FindBy(xpath = "//div[@class='MuiTooltip-popper MuiTooltip-popperArrow']/div/p")
    public WebElement viewMIcommentPopUp;

    @FindBy(xpath = "//p[@class='MuiFormHelperText-root Mui-error Mui-required' and contains(text(),'is required')]")
    public List<WebElement> requiredWarningList;

    @FindBy(xpath = "//h2[contains(text(), ' MISSING INFO DETAILS')]/following::table/tbody/tr/td[2]")
    public List<WebElement> savedMICategoryColumn;

    @FindBy(xpath = "//h2[contains(text(), ' MISSING INFO DETAILS')]/following::table/tbody/tr/td[3]")
    public List<WebElement> savedMITypeColumn;

    @FindBy(xpath = "//h2[contains(text(), ' MISSING INFO DETAILS')]/following::table/tbody/tr/td[4]")
    public List<WebElement> savedMIFromColumn;

    @FindBy(xpath = "//h2[contains(text(), ' MISSING INFO DETAILS')]/following::table/tbody/tr/td[5]")
    public List<WebElement> savedMINeedForColumn;

    @FindBy(xpath = "//h2[contains(text(), ' MISSING INFO DETAILS')]/following::table/tbody/tr/td[6]")
    public List<WebElement> savedMICommentColumn;

    @FindBy(xpath = "//h2[contains(text(), ' MISSING INFO DETAILS')]/following::table/tbody/tr/td[6]//p[1]")
    public WebElement firstAppendedComment;

    @FindBy(xpath = "//h2[contains(text(), ' MISSING INFO DETAILS')]/following::table/tbody/tr/td[6]//p[2]")
    public WebElement secondAppendedComment;

    @FindBy(xpath = "//h2[contains(text(), ' MISSING INFO DETAILS')]/following::table/tbody/tr/td[7]")
    public List<WebElement> savedMIStatusColumn;

    @FindBy(xpath = "//h2[contains(text(), ' MISSING INFO DETAILS')]/following::table/tbody/tr[1]/td[5]/p")
    public WebElement rowOneNeedForIcon;

    @FindBy(xpath = "//p[.='NEED FOR']/following-sibling::ul/li")
    public List<WebElement> needForHoverConsumerList;

    @FindBy(xpath = "//i[.='more_vert']")
    public WebElement userIcon;

    @FindBy(xpath = "//li[@role='menuitem']")
    public List<WebElement> userIconSelectMenuList;

    @FindBy(xpath = "//p[.='MI ID#']")
    public WebElement iDLabel;

    @FindBy(xpath = "//p[.='MI ID#']/following-sibling::p")
    public WebElement iDValue;

    @FindBy(xpath = "//p[.='CREATED BY']")
    public WebElement createdByLabel;

    @FindBy(xpath = "//p[.='CREATED BY']/following-sibling::p")
    public WebElement createdByValue;

    @FindBy(xpath = "//p[.='CREATED ON']")
    public WebElement createdOnLabel;

    @FindBy(xpath = "//p[.='CREATED ON']/following-sibling::p")
    public WebElement createdOnValue;

    @FindBy(xpath = "//p[.='UPDATED BY']")
    public WebElement updatedByLabel;

    @FindBy(xpath = "//p[.='UPDATED BY']/following-sibling::p")
    public WebElement updatedByValue;

    @FindBy(xpath = "//p[.='UPDATED ON']")
    public WebElement updatedOnLabel;

    @FindBy(xpath = "//p[.='UPDATED ON']/following-sibling::p")
    public WebElement updatedOnValue;

    @FindBy(xpath = "//p[.='COMPLETED BY']")
    public WebElement completedByLabel;

    @FindBy(xpath = "//p[.='COMPLETED BY']/following-sibling::p")
    public WebElement completedByValue;

    @FindBy(xpath = "//p[.='COMPLETED ON']")
    public WebElement completedOnLabel;

    @FindBy(xpath = "//p[.='COMPLETED ON']/following-sibling::p")
    public WebElement completedOnValue;

    @FindBy(xpath = "//span[.='arrow_forward']")
    public WebElement nextPageArrowKeyButton;

    @FindBy(id = "miAddOrEditComments")
    public WebElement editCommentTextBox;

    @FindBy(xpath = "//button[@type='button']/span/span[.='check']")
    public WebElement editCommentTextBoxSave;

    @FindBy(xpath = "//button[@type='button']/span/span[.='clear']")
    public WebElement editCommentTextBoxClear;

    @FindBy(xpath = "//li[contains(text(),'EDIT COMMENT')]")
    public WebElement editCommentSelect;

    @FindBy(xpath = "//li[contains(text(),'SATISFY')]")
    public WebElement satisfySelect;

    @FindBy(xpath = "//li[contains(text(),'DISREGARD')]")
    public WebElement disregardSelect;

    @FindBy(xpath = "//*[text()='SUCCESS MESSAGE']")
    public WebElement miAddSuccessMsg;

    @FindBy(xpath = "//p[contains(text(),'Comment Section is required')]")
    public WebElement commentSectionWarningMessage;

    @FindBy(xpath = "//p[contains(text(),'If you continue, all the captured information will be lost')]")
    public WebElement warningMessageInformationWillLost;

    @FindBy(xpath = "//span[text()='Continue']")
    public WebElement continueButtonInsideWarning;

    @FindBy(xpath = "//span[text()='Cancel']")
    public WebElement cancelButtonInsideWarning;

    @FindBy(xpath = "//span[@title='Filter Column']")
    public WebElement filterColumnButton;

    @FindBy(xpath = "//span[contains(text(),'PENDING')]")
    public WebElement filterColumnPendingButton;

    @FindBy(xpath = "//span[contains(text(),'SATISFIED')]")
    public WebElement filterColumnSatisfiedButton;

    @FindBy(xpath = "//span[contains(text(),'DISREGARDED')]")
    public WebElement filterColumnDisregardedButton;

    @FindBy(xpath = "//span[contains(text(),'CLEAR')]")
    public WebElement filterColumnClearButton;

    @FindBy(xpath = "//span[contains(text(),'CLOSE')]")
    public WebElement filterColumnCloseButton;

    @FindBy(xpath = "//small[contains(text(),'DUE DATE :')]/..")
    public WebElement miDueDate;

    @FindBy(xpath ="(//span[contains(@id, 'client-snackbar')])[2]")
    public WebElement errorMessage;

    @FindBy(xpath ="//tbody[@class='MuiTableBody-root']//tr[@class='MuiTableRow-root']")
    public List<WebElement> missingInfoDetailRows;

    @FindBy(xpath = "//h2[text()='APPLICATION INFORMATION']/following-sibling::div")
    public WebElement applicationStatus;

    @FindBy(xpath = "//a[@class='nav-link' and (text()='APPLICATION')]")
    public WebElement applicationTab;

    @FindBy(xpath = "//p[.='APPLICATION CODE']")
    public WebElement applicationCodeLabel;

    @FindBy(xpath = "//p[.='APPLICATION CODE']/following-sibling::h6")
    public WebElement applicationCodeValue;

    @FindBy(xpath = "//td[@class='MuiTableCell-root MuiTableCell-body mdl-color-text--red-800']")
    public WebElement miDocumentStatus;
}