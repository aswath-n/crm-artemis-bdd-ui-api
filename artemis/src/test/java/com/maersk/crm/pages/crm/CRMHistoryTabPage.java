package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMHistoryTabPage extends CRMUtilities {



    public CRMHistoryTabPage() {

        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(id = "mui-component-select-consumerName")
    public WebElement consumerNameDropdown;

    @FindBy(id = "mui-component-select-businessEvents")
    public WebElement businessEventsDropdown;

    @FindBy(xpath = "//div[@aria-labelledby='processDateRange processDateRange']")
    public WebElement processDateRangeDropdown;

    @FindBy(id = "mui-component-select-channel")
    public WebElement channelDropdown;

    @FindBy(xpath = "(//h5[contains(@class,'mx-section-header float-left pl')])[1]")
    public WebElement eventName;

    @FindBy(xpath = "(//p[contains(text(),'APPLICATION CODE')]/following-sibling::p)[1]")
    public WebElement applicationCode;

    @FindBy(xpath = "(//p[contains(text(),'APPLICATION CONSUMER ID')]/following-sibling::p)[1]")
    public WebElement applicationConsumerId;

    @FindBy(xpath = "(//p[contains(text(),'APPLICATION DEADLINE DATE')]/following-sibling::p)[1]")
    public WebElement applicationDeadlineDate;

    @FindBy(xpath = "(//p[contains(text(),'APPLICATION ID')]/following-sibling::p)[1]")
    public WebElement applicationId;

    @FindBy(xpath = "(//p[contains(text(),'APPLICATION STATUS')]/following-sibling::p)[1]")
    public WebElement applicationStatus;

    @FindBy(xpath = "(//p[contains(text(),'APPLICATION TYPE / CYCLE')]/following-sibling::p)[1]")
    public WebElement applicationTypeCycle;

    @FindBy(xpath = "(//p[contains(text(),'CREATED BY')]/following-sibling::p)[1]")
    public WebElement createdBy;

    @FindBy(xpath = "(//p[contains(text(),'CREATED DATE')]/following-sibling::p)[1]")
    public WebElement createdDate;

    @FindBy(xpath = "(//p[contains(text(),'EXTERNAL APPLICATION NUMBER')]/following-sibling::p)[1]")
    public WebElement externalApplicationNumber;

    @FindBy(xpath = "(//p[contains(text(),'EXPIRED BY')]/following-sibling::p)[1]")
    public WebElement expiredBy;

    @FindBy(xpath = "(//p[contains(text(),'EXPIRED DATE')]/following-sibling::p)[1]")
    public WebElement expiredDate;

    @FindBy(xpath = "(//p[contains(text(),'PROGRAM')]/following-sibling::p)[1]")
    public WebElement program;

    @FindBy(xpath = "(//p[contains(text(),'PROGRAM ELIGIBILITY STATUS')]/following-sibling::p)[1]")
    public WebElement programEligibilityStatus;

    @FindBy(xpath = "(//p[contains(text(),'PRIMARY Y / N')]/following-sibling::p)[1]")
    public WebElement primaryYN;

    @FindBy(xpath = "(//p[contains(text(),'UPDATED BY')]/following-sibling::p)[1]")
    public WebElement updatedBy;

    @FindBy(xpath = "(//p[contains(text(),'UPDATED DATE')]/following-sibling::p)[1]")
    public WebElement updatedDate;

    @FindBy(xpath = "(//p[contains(text(),'MI CREATED BY')]/following-sibling::p)[1]")
    public WebElement miCreatedBy;

    @FindBy(xpath = "(//p[contains(text(),'MI CREATED DATE')]/following-sibling::p)[1]")
    public WebElement miCreatedDate;

    @FindBy(xpath = "(//p[contains(text(),'MISSING INFORMATION ID')]/following-sibling::p)[1]")
    public WebElement missingInformationId;

    @FindBy(xpath = "(//p[contains(text(),'MI CATEGORY / TYPE')]/following-sibling::p)[1]")
    public WebElement miCategoryType;

    @FindBy(xpath = "(//p[contains(text(),'MI DUE DATE')]/following-sibling::p)[1]")
    public WebElement miDueDate;

    @FindBy(xpath = "(//p[contains(text(),'MI STATUS')]/following-sibling::p)[1]")
    public WebElement miStatus;

    @FindBy(xpath = "//span[@class='text-center']")
    public WebElement textCenter;

    @FindBy(xpath = "//ul[@class='mx-history-timeline']/li/div[1]")
    public List<WebElement> channelIconList;

    @FindBy(xpath = "//ul[@class='mx-history-timeline']/li/div[2]/h5")
    public List<WebElement> eventHistoryHeaderList;


}
