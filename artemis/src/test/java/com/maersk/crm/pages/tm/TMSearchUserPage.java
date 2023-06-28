package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TMSearchUserPage {

    

    public TMSearchUserPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }
//todo: this element will be moved to TMViewProjectPage as first appears there
    //refactorBy:Vidya Date:02-03-20202
    @FindBy (xpath = "//h5[contains(text(),'User List')]")
    public WebElement projectName;

    //todo: this element will be moved to TMViewProjectPage as first appears there
    @FindBy (xpath = "//a[.='ballot']")
    public WebElement editProjectTab;

    //todo: this element will be moved to TMViewProjectPage as first appears there
    @FindBy (xpath = "//a[.='group']")
    public WebElement searchUserTab;

    @FindBy (name = "firstName")
    public WebElement searchFirstName;

    @FindBy (name = "lastName")
    public WebElement searchLastName;

    @FindBy (name = "maerskId")
    public WebElement searchMaxID;

    @FindBy (name = "email")
    public WebElement searchEmail;

   //todo please check for dropdown function
    @FindBy (xpath="//*[@id='accountType']/..")
    public WebElement searchAccountType;

//    @FindBy (id="accountType")
//    public WebElement searchAccountType;

    //todo please check for dropdown function
    // @FindBy (xpath="//*[@id='statusType']/..")
    // public WebElement searchStatusType;

    @FindBy(xpath = "//input[@id='statusType']/..") //(id="statusType")
    public WebElement searchStatusType;

    @FindBy (xpath = "//*[contains(text(), ' Search')]")
    public WebElement searchButton;


    @FindBy (xpath = "//*[contains(text(), ' Clear')]")
    public WebElement clearButton;

    @FindBy (xpath = "//*[contains(text(), ' delete ')]")
    public WebElement discardButton;

  // @FindBy (xpath = "//span[contains(text(), 'add')]/..")
   @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiFab-root mdl-js-button mdl-button--fab mdl-js-ripple-effect mx-btn-white-tm ml-2 mt-4 ']")
    public WebElement addUserButton;

   @FindBy (xpath = "//span[contains(text(), 'keyboard_backspace')]")
    public WebElement backToProjectListButton;

   @FindBy (name = "itemsPerPage")
   public WebElement itemsPerPage;

    //refactored 11/29 -- Vinuta
   @FindBy (xpath = "//*[@class='modal-content']")
   public WebElement warningSign;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'table-responsive')]//tr[*]//td[1]")
    })
    public List<WebElement> resultCheckBoxes;

    //refactored 11/29 --- Vinuta
    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'table-responsive')]//tr[*]//td[2]")
    })
    public List<WebElement> resultFirstNames;

    //refactored 11/29 -- Vinuta
    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'table-responsive')]//tr[*]//td[3]")
    })
    public List<WebElement> resultLastNames;

    //refactored 11/29 -- Vinuta
    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'table-responsive')]//tr[*]//td[4]")
    })
    public List<WebElement> resultMaxIDs;

    //refactored 11/29 -- Vinuta
    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'table-responsive')]//tr[*]//td[5]")
    })
    public List<WebElement> resultEmails;

    //refactored 11/29 -- Vinuta
    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'table-responsive')]//tr[*]//td[6]")
    })
    public List<WebElement> resultStartDays;

    //refactored 11/29 -- Vinuta
    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'table-responsive')]//tr[*]//td[7]")
    })
    public List<WebElement> resultEndDays;

    //refactored 11/29 -- Vinuta
    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'table-responsive')]//tr[*]//td[8]")
    })
    public List<WebElement> resultStatuses;

    @FindBy(xpath = "//th[contains(@class,'mdl-data-table__cell--non-numeric ')]//input")
    public WebElement selectAllUserCheckbox;

    //By Vinuta
    //This method returns the checkbox against any column value that is unique on User List page
    public WebElement getCheckboxBy(String value){
        return  Driver.getDriver().findElement(By.xpath("//td[text()='"+value+"']/parent::tr//preceding-sibling::td//input[@type='checkbox']"));
    }

    //By Vinuta
    //This method returns true if checkbox is checked for Inactive user,false if checkbox is not checked for Active user
    public boolean getCheckboxValue(String value){
        String xpath="//td[text()='" +value+ "']/parent::tr//preceding-sibling::td//input[@checked]";
        WebElement checkboxValue = Driver.getDriver().findElement(By.xpath(xpath));
        if(checkboxValue.isSelected()) return true;
        else return false;
    }

    @FindBy (xpath = "//h5[contains(text(),'No Records Available')]")
    public WebElement noRecardMsg;
}
