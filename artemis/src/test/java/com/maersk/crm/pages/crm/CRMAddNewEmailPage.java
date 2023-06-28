package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class CRMAddNewEmailPage {

    

    public CRMAddNewEmailPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    CRMAddContactInfoPage crmAddContactInfoPage = new CRMAddContactInfoPage();

    @FindBy(xpath = "//p[.='EMAIL ADDRESS is required and cannot be left blank']")
    public WebElement emptyEmailAddressError;

    @FindBy(xpath = "//p[.='CONSUMER is required and cannot be left blank']")
    public WebElement emptyConsumerError;

    @FindBy(xpath = "//p[.='START DATE is required and cannot be left blank']")
    public WebElement emptyStartDateError;

    @FindBy(xpath = "//p[.='EMAIL ADDRESS is not in the correct format']")
    public WebElement invalidEmailFieldError;

    @FindBy(xpath = "//p[.='The date entered does not exist. Please enter a valid date.']")
    public WebElement incorrectDateError;

    @FindBy(xpath = "//p[.='Invalid date format']")
    public WebElement invalidDateError;


    @FindAll({
            @FindBy(xpath = "(//th[contains(text(),'EMAIL')])[1]/parent::tr/parent::thead/parent::table/tbody/tr[1]")
    })
    public List<WebElement> listOfEMail;

    public boolean isPopupAlertContinueButtonExist() {
        boolean continueButtonExist = false;
        try {
            continueButtonExist = crmAddContactInfoPage.popupAlertContinueButton.isDisplayed();
            return continueButtonExist;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return continueButtonExist;
        }
    }

    public boolean isPopupAlertCancelButtonExist() {
        boolean continueButtonExist = false;
        try {
            continueButtonExist = crmAddContactInfoPage.popupAlertCancelButton.isDisplayed();
            return continueButtonExist;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return continueButtonExist;
        }
    }


}
