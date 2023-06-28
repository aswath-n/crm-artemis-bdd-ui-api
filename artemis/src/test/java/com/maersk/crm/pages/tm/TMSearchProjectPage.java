package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;

public class TMSearchProjectPage {

    

    public TMSearchProjectPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }
    @FindBy (xpath = "(//div[@class='clearfix'])[2]")
    public WebElement errorMessage;

    @FindBy (xpath="//span[contains(text(), ' Search ')]")
    public WebElement searchButton;

    @FindBy (xpath="//span[contains(text(), ' Clear ')]")
    public WebElement clearButton;

    public void search(){
        searchButton.click();
    }

    public void clear(){
        clearButton.click();
    }


}
