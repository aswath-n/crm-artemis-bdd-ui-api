package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TMBusinessUnitListPage {
    

    public TMBusinessUnitListPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//h5[contains(text(),'BUSINESS UNIT')]/../../following-sibling::main//th")
    public List<WebElement> columnsHeader;

    @FindBy(xpath = "//span[contains(text(),'add')]")
    public WebElement addButton;

    @FindBy(xpath = "//span[text()='keyboard_backspace']")
    public WebElement backArrow;

    @FindBy(xpath = "//h5[contains(text(),'BUSINESS UNIT')]")
    public WebElement businessUnitListPage;

    @FindBy(xpath = "//h5[contains(text(),'BUSINESS UNIT')]/../../following-sibling::main//tbody//td[1]//a")
    public List<WebElement> businessUnitNameClumVlu;

    @FindBy(xpath = "//h5[contains(text(),'BUSINESS UNIT')]/../../following-sibling::main//tbody//td[2]")
    public List<WebElement> descriptionClumVlu;

    @FindBy(xpath = "//h5[contains(text(),'BUSINESS UNIT')]/../../following-sibling::main//tbody//td[3]")
    public List<WebElement> startDateClumVlu;

    @FindBy(xpath = "//h5[contains(text(),'BUSINESS UNIT')]/../../following-sibling::main//tbody//td[4]")
    public List<WebElement> endDateClumVlu;

    @FindBy(xpath = "//h5[contains(text(),'BUSINESS UNIT')]/../../following-sibling::main//tbody//td[5]")
    public List<WebElement> statusClumVlu;

    @FindBy(xpath = "//h5[contains(text(),'BUSINESS UNIT')]/../../following-sibling::main//tbody//td")
    public List<WebElement> allColumnsValue;

    @FindBy(xpath = "//h5[contains(text(),'ASSOCIATED TEAM(S)')]")
    public WebElement businessUnitAssociatedTeam;

    @FindBy(xpath = "//h5[contains(text(),'BUSINESS UNIT')]/../../following-sibling::main//tbody//td[1]//a")
    public WebElement businessUnitNameVlu;

}
