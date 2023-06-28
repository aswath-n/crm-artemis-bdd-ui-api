package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.TMUtilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TMListOfTeamPage extends TMUtilities {

    

    public TMListOfTeamPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    //By Vidya
    @FindBy(xpath = "//span[contains(text(),'add')]")
    public WebElement addButton;

    //refactorBy:Shruti Date:03-25-2020
    //@FindBy(xpath = "//h5[contains(text(),'ENROLLMENT BROKER - TEAM')]")
    @FindBy(xpath = "//header/..//h5")
    public WebElement teamHeader;

    @FindBy(xpath = "//span[contains(text(),'keyboard_backspace')]")
    public WebElement backArrow;

    //refactorBy:Vidya Date:03-02-2020
    @FindBy(xpath = "//h5[contains(text(),'TEAM')]/../../following-sibling::main//th")
    public List <WebElement> columnheaders;

    //refactorBy:Vidya Date:03-02-2020
    @FindBy(xpath = "//h5[contains(text(),'TEAM')]/../../following-sibling::main//td")
    public List <WebElement> allColumnsValue;

    //refactorBy:Vidya Date:03-02-2020
    @FindBy(xpath = "//h5[contains(text(),'TEAM')]/../../following-sibling::main//tbody//td[1]//a")
    public List<WebElement> teamNameClumVlu;

    //refactorBy:Vidya Date:03-02-2020
    @FindBy(xpath = "//h5[contains(text(),'TEAM')]/../../following-sibling::main//tbody//td[3]")
    public List<WebElement> descriptionClumVlu;

    //refactorBy:Vidya Date:03-02-2020
    @FindBy(xpath = "//h5[contains(text(),'TEAM')]/../../following-sibling::main//tbody//td[2]")
    public List<WebElement> bUClumVlu;

    //refactorBy:Vidya Date:03-02-2020
    @FindBy(xpath = "//h5[contains(text(),'TEAM')]/../../following-sibling::main//tbody//td[4]")
    public List<WebElement> startDateClumVlu;

    //refactorBy:Vidya Date:03-02-2020
    @FindBy(xpath = "//h5[contains(text(),'TEAM')]/../../following-sibling::main//tbody//td[5]")
    public List<WebElement> endDateClumVlu;

    //refactorBy:Vidya Date:03-02-2020
    @FindBy(xpath = "//h5[contains(text(),'TEAM')]/../../following-sibling::main//tbody//td[6]")
    public List<WebElement> statusClumVlu;

    // Added by Paramita //refactorBy:Vidya Date:03-02-2020
    @FindBy(xpath = "//h5[contains(text(),'TEAM')]/../../following-sibling::main//tbody//tr")
    public List<WebElement> teamrowVlu;

    // Added by Paramita //refactorBy:Vidya Date:03-02-2020
    @FindBy(xpath = "//h5[contains(text(),'TEAM')]/../../following-sibling::main//tbody//td[1]/a")
    public List<WebElement> teamNameClumVluText;


}
