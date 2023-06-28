package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.security.PublicKey;
import java.time.Duration;
import java.util.List;


public class OutBoundCorrespondenceDestiPage {

    
    
    Actions actions;
    WebElement pdfIcon;
    WebDriverWait wait;

    public OutBoundCorrespondenceDestiPage() {

        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy( xpath = "//input[@name='crmCaseID']")
    public WebElement CaseID;

     @FindBy(xpath = "//*[contains(@class, 'mx-btn-primary mdl-shadow--2dp')]")
    public WebElement searchBtn;






// click chevron and populate the consumner

   // @FindBy(xpath="//*[@id='caseConsumerSearch']/div/div[1]/div[2]/div/table/tbody/tr[1]/td[1]/button/span")
    @FindBy(xpath = "//*[contains(text(), 'chevron_right')]" )
    public WebElement Expend;

    public void correspondenceChevronByCid(String cid) {
        String x = "(//*[text()='" + cid + "'])/../td[1]";
        Driver.getDriver().findElement(By.xpath(x)).click();
    }


    @FindBy(xpath ="//p[contains(text(), 'ADDRESS LINE 1 is required and cannot be left blank')]")
    public WebElement ErrorMessageForAddressOne;

    @FindBy(xpath = "//p[contains(text(), 'CITY is required and cannot be left blank')]")
    public WebElement ErrorMessageForcity;


    @FindBy(xpath= "//p[contains(text(), 'STATE is required and cannot be left blank')]")
    public  WebElement ErrorMessageForState;


    @FindBy(xpath = "//p[contains(text(), 'ZIP CODE is required and cannot be left blank')]")
    public WebElement ErrorMessageForZipCode;

    @FindBy(xpath = "//p[contains(text(), 'ZIP CODE must be 5 or 9 characters')]")
    public WebElement ErrorMessageForZipCodeInvalidformat;

    @FindBy(xpath = "//*[@id='emailAddress-helper-text']")
    public WebElement ErrorMessageForEmail;

    @FindBy(xpath ="//*[@id='emailAddress-helper-text']")
    public WebElement ErrorMessageForWrongFormat;

    @FindBy(xpath = "//*[@id='phoneNumber-helper-text']")
    public WebElement ErrorMessageForTextField;

    @FindBy(xpath = "//*[@id='faxNumber-helper-text']")
    public WebElement ErrorMessageForFaxNumber;


    @FindBy(xpath = "//*[contains(@class, 'MuiTableHead-root')]/following-sibling::tbody/tr[1]/td[2]")
    public WebElement MatchCaseId;


    @FindBy(xpath = "//*[contains(text(), 'chevron_right')]")
    public List<WebElement>  ExpandArrow;








    public void findRowValues( String text){

        List<WebElement> rows = Driver.getDriver().findElements(By.xpath("//input[@class='jss1618']"));
        for (WebElement row : rows) {
         if(row.getText().equalsIgnoreCase(text))
            {
             row.click();
            }
        }
    }



            // Iterate over rows to get each count

        // Do whatever you want with pCount

    @FindBy(xpath = "//p[contains(text(),'PRIMARY INDIVIDUAL')]")
    public WebElement primaryIndividualField;

    @FindBy(xpath = "//*[text()='LINK RECORD']")
    public WebElement linkRecordBtn;

// menu on the righthand
    @FindBy(xpath = "//button[@id='menu-list-button']")
    public WebElement menuListBtn;
    //createCorrespondence
    @FindBy(xpath = "//li[@name='createCorrespondence']")
    public WebElement createCorrespondenceBtn;

    public String  getPageTitle(){
        return Driver.getDriver().getTitle();
    }

   @FindBy(xpath= "//*[@id='_TYPE']")
   public WebElement selectCorrespondeType;

   @FindBy(xpath ="//*[@id='5162']")
    public WebElement checkConsumer;

   public WebElement getRecipient(String consumerId){
       return Driver.getDriver().findElement(By.xpath("//*[@id='"+consumerId+"']"));
   }

//
//    @FindBy(xpath="//*[@id='root']/div/main/div/div/div/div[1]/div/div[3]/div[9]/div[6]/div/div/div/div[4]/div/div/div[2]/label/span[1]")
//    public WebElement CheckText;

    @FindBy(xpath="//input[contains(@id, 'checkText')]")
    public List<WebElement> CheckText;
    @FindBy(xpath="//input[contains(@id, 'checkMail')]")
    public List<WebElement> CheckMail;

    @FindBy(xpath="//input[contains(@id, 'checkEmail')]")
    public List<WebElement> CheckEmail;

    @FindBy(xpath="//input[contains(@id, 'checkFax')]")
    public List<WebElement> CheckFax;

    @FindBy(xpath="//div[contains(@id, 'mui-component-select-textDestination')]")
    public List<WebElement> OpenTextList;

    @FindBy(xpath="//div[contains(@id, 'mui-component-select-mailDestination')]")
    public List<WebElement> OpenMailList;
    @FindBy(xpath="//div[contains(@id, 'mui-component-select-emailDestination')]")
    public List<WebElement> OpenEmailList;
    @FindBy(xpath="//div[contains(@id, 'mui-component-select-faxDestination')]")
    public List<WebElement> OpenFaxList;


    @FindBy(xpath="//li[contains(@data-value, 'Other')]")
    public WebElement SelectOther;




    @FindBy(xpath = "//*[@id='phoneNumber']")
    public WebElement phoneNumber;








    @FindBy(xpath="//*[@id='emailAddress']")
    public WebElement Emailfield;



@FindBy (xpath = "//input[contains(@id, 'faxNumber')]")
public WebElement FaxField;


//mailing address xpathes.



    @FindBy(xpath ="//*[@id='consumerAddress1']")
    public WebElement addressLineOne;

    @FindBy(xpath = "//*[@id='consumerAddress2']")
    public WebElement addressLineTwo;

    @FindBy(xpath = "//*[@id='consumer-city']")
  //  @FindBy(xpath = "//div[contains(@id, 'mui-component-select-consumerCity')]")
    public WebElement OpenCityList;

//    @FindBy(xpath = "//li[contains(@data-value, 'Reston')]")
//    public WebElement SelectCity;

    public void selectCity(String city) throws InterruptedException {
        Thread.sleep(3000);
        Driver.getDriver().findElement(By.xpath("//li[contains(@data-value, '"+city+"')]")).click();

    }

    @FindBy(xpath = "//div[contains(@id, 'mui-component-select-consumerState')]")
    public WebElement OpenStateList;
//    @FindBy(xpath = "//li[contains(@data-value, 'Virginia')]")
//    public WebElement SelectState;

    public void SelectState(String state) throws InterruptedException {
        Thread.sleep(3000);
        Driver.getDriver().findElement(By.xpath("//li[contains(@data-value, '"+state+"')]")).click();

    }
    @FindBy(xpath = "//*[@id='zipCode']")
  //  @FindBy(xpath = "//div[contains(@id, 'mui-component-select-zipCode')]")
    public WebElement OpenZipList;


    @FindBy(xpath = "//li[contains(@data-value, '20190')]")
    public WebElement SelectZip;

    public void selectZipCode(String zip) throws InterruptedException {

        Thread.sleep(5000);
        Driver.getDriver().findElement(By.xpath("//li[contains(@data-value, '"+zip+"')]")).click();

    }

    @FindBy(xpath = "//*[@id='consumer-city']")
   // @FindBy(xpath = "//*[@id='mui-component-select-consumerCity']")
    public WebElement City;

    @FindBy(xpath = "//*[@id='mui-component-select-consumerState']")
    public WebElement State;

    @FindBy(xpath = "//*[@id='zipCode']")
    //@FindBy(xpath= "//*[@id='mui-component-select-zipCode']")
    public WebElement ZipCode;

        //mailing address information above


    @FindBy(xpath="//button[contains(@class, 'MuiButtonBase-root MuiButton-root MuiButton-text mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mx-btn  mx-btn-border mx-btn-primary')]")
    public WebElement SaveButton;

    @FindBy (xpath="//*[@id='client-snackbar']")
    public WebElement SuccessMessage;

    public boolean  messagetext(String msg) {
        return Driver.getDriver().findElement(By.xpath("//span[contains(text(),'"+msg+"')]")).isDisplayed();
    }


    public void clickOnTextFromDropdownList(WebElement list, String textToSearchFor) throws Exception {
        Wait<WebDriver> tempWait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(2));
        try {
            tempWait.until(ExpectedConditions.elementToBeClickable(list)).click();
            list.sendKeys(textToSearchFor);
            list.sendKeys(Keys.ENTER);
            System.out.println("Successfully sent the following keys: " + textToSearchFor + ", to the following WebElement: " + "<" + list.toString() + ">");
        } catch (Exception e) {
            System.out.println("Unable to send the following keys: " + textToSearchFor + ", to the following WebElement: " + "<" + list.toString() + ">");
            Assert.fail("Unable to select the required text from the dropdown menu, Exception: " + e.getMessage());
        }
    }


}

