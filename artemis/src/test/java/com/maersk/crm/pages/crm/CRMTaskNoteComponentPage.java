package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class CRMTaskNoteComponentPage {
    

    public CRMTaskNoteComponentPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//h5[text()='NOTES']")
    public WebElement noteComLabel;

    @FindBy(xpath = "//h5[text()='NOTES']/..//span[text()='SAVE']")
    public WebElement noteSaveButton;

    @FindBy(xpath = "//h5[text()='NOTES']/..//span[text()='CANCEL']")
    public WebElement noteCancelButton;

    @FindBy(name = "notes")
    public WebElement notesFieldTxtBox;

    @FindBy(id = "client-snackbar")
    public WebElement noteSuccessMessage;

    @FindBy(xpath = "//h5[text()='NOTES']/following-sibling::div//p[2]")
    public List<WebElement>  noteTxtValue;

    @FindBy(xpath = "//span[text()='arrow_forward']")
    public WebElement lblForwarArrow;

    @FindBy(xpath = "//span[text()='last_page']")
    public WebElement lastPage;

    @FindBy(xpath = "//h5[text()='NOTES']/..//ul//li//a")
    public List<WebElement> notePagination;

    @FindBy(xpath = "//span[text()='arrow_back']")
    public WebElement lblBackArrow;

    @FindBy(xpath = "//span[text()='first_page']")
    public WebElement firstPage;

    @FindBy(xpath = "//h5[text()='NOTES']/following-sibling::div//p[1]")
    public List<WebElement> noteDateTime;

    @FindBy(xpath = "//h5[text()='NOTES']/following-sibling::div//h6")
    public List<WebElement> noteCreatedBy;

    @FindBy(xpath = "//div[@title='ConnectionPoint Logo']/../..//div[contains(@class,'datetime')]")
    public WebElement projectDateAtHeader;

    @FindBy(xpath = "//span[text()='last_page']/parent::a")
    public WebElement noteCompArrowForward;

    @FindBy(xpath = "//span[text()='first_page']/parent::a")
    public WebElement noteCompArrowBack;
}
