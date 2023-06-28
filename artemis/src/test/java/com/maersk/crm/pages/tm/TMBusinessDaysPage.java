package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class TMBusinessDaysPage {

   

   public TMBusinessDaysPage() {
      
      PageFactory.initElements(Driver.getDriver(), this);
   }

   @FindBy(xpath="//*[contains(text(),'If Business days are not present for a given day, ')]//parent::p")
   public WebElement warningMsg1;

   @FindBy(xpath="//p//strong")
   public WebElement businessDays;

   @FindBy(xpath="//i[text()='add']/following-sibling::span")
   public WebElement addButton;

   @FindBy(name = "businessDaysName")
   public WebElement businessDaysName;

   @FindBy(xpath="//label[text()='START DATE']/following-sibling::div//input")
   public WebElement startDate;

   @FindBy(xpath="//label[text()='END DATE']/following-sibling::div//input")
   public WebElement endDate;

   @FindBy(xpath="(//span[contains(text(),'EXCLUDE FOR TASK')])[1]")
   public WebElement isExcludeTask;

   @FindBy(xpath="(//span[contains(text(),'EXCLUDE FOR SERVICE REQUEST')])[1]")
   public WebElement isExcludeServiceRequest;

   @FindBy(xpath="//span[contains(text(),'SUN')]/parent::label//input")
   public WebElement sunCB;

   @FindBy(xpath="//span[contains(text(),'MON')]/parent::label//input")
   public WebElement monCB;

   @FindBy(xpath="//span[contains(text(),'TUE')]/parent::label//input")
   public WebElement tueCB;

   @FindBy(xpath="//span[contains(text(),'WED')]/parent::label//input")
   public WebElement wedCB;

   @FindBy(xpath="//span[contains(text(),'THU')]/parent::label//input")
   public WebElement thuCB;

   @FindBy(xpath="//span[contains(text(),'FRI')]/parent::label//input")
   public WebElement friCB;

   @FindBy(xpath="//span[contains(text(),'SAT')]/parent::label//input")
   public WebElement satCB;

   @FindBy(xpath="//*[contains(text(),'Save')]")
   public WebElement saveButton;

   @FindBy(xpath="//*[contains(text(),'Cancel')]")
   public WebElement cancelButton;

   @FindBy(xpath = "//span[contains(text(),'Business Days saved successfully')]")
   public WebElement successMessage;

   @FindBy(xpath = "//span[text()='Business Days saved successfully']")
   public WebElement businessDaysSuccessMsg;

   @FindBy(xpath="//p[text()='NAME']/following-sibling::p")
   public WebElement lblName;

   @FindBy(xpath="//p[text()='START DATE']/following-sibling::p")
   public WebElement lblStartDate;

   @FindBy(xpath="//label[text()='End Date']/following-sibling::div//input")
   public List<WebElement> endDateList;

   @FindBy(id = "mui-component-select-defaultYear")
   public WebElement selectYear;

   @FindBy(xpath = "//input[@name='defaultYear']")
   public WebElement defaultYear;

   @FindBy(xpath="//div[@class='row mb-3 mx-0 pt-3 mx-border-outline']")
   public List<WebElement> timeFrames;

   public static final By TIME_FRAME = By.xpath("//div[contains(@class,'mx-border-outline')]");

   @FindBy(xpath="//p[text()='NAME']/following-sibling::p")
   public List<WebElement> lblNames;

   @FindBy(xpath="//p[text()='START DATE']/following-sibling::p")
   public List<WebElement> lblStartDates;

   @FindBy(xpath="//div[@class='row mb-3 mx-0 pt-3 mx-border-outline']//div//label[text()='End Date']/following-sibling::div//input")
   public List<WebElement> EndDates;

   @FindBy(xpath="//div[@class='row mb-3 mx-0 pt-3 mx-border-outline']//div[5]/div[2]//span//input[@name='isExcludeTask']")
   public List<WebElement> isExcludeTaskFromCurrentTF;

   @FindBy(xpath="//div[@class='row mb-3 mx-0 pt-3 mx-border-outline']//div[5]/div[2]//span//input[@name='isExcludeServiceRequest']")
   public List<WebElement> isExcludeServiceRequestCurrentTF;

   @FindBy(xpath="//div[@class='row mb-3 mx-0 pt-3 mx-border-outline']//div[5]/div[1]/div[2]//label//input")
   public List<WebElement> sunListCurrentTF;

   @FindBy(xpath="//div[@class='row mb-3 mx-0 pt-3 mx-border-outline']//div[5]/div[1]/div[3]//label//input")
   public List<WebElement> monListCurrentTF;

   @FindBy(xpath="//div[@class='row mb-3 mx-0 pt-3 mx-border-outline']//div[5]/div[1]/div[4]//label//input")
   public List<WebElement> tueListCurrentTF;

   @FindBy(xpath="//div[@class='row mb-3 mx-0 pt-3 mx-border-outline']//div[5]/div[1]/div[5]//label//input")
   public List<WebElement> wedListCurrentTF;

   @FindBy(xpath="//div[@class='row mb-3 mx-0 pt-3 mx-border-outline']//div[5]/div[1]/div[6]//label//input")
   public List<WebElement> thuListCurrentTF;

   @FindBy(xpath="//div[@class='row mb-3 mx-0 pt-3 mx-border-outline']//div[5]/div[1]/div[7]//label//input")
   public List<WebElement> friListCurrentTF;

   @FindBy(xpath="//div[@class='row mb-3 mx-0 pt-3 mx-border-outline']//div[5]/div[1]/div[8]//label//input")
   public List<WebElement> satListCurrentTF;

   @FindBy(xpath="//div[@class='row my-3 mx-0 mx-border-outline']")
   public List<WebElement> editableTimeFrames;

   @FindBy(xpath="//input[@name='businessDaysName']")
   public List<WebElement> businessDaysNames;

   @FindBy(xpath="//label[text()='START DATE']/following-sibling::div//input")
   public List<WebElement> startDates;

   @FindBy(xpath="//div[@class='row my-3 mx-0 mx-border-outline']//div//label[text()='End Date']/following-sibling::div//input")
   public List<WebElement> editTimeFrameEndDates;

   @FindBy(xpath="//div[@class='row my-3 mx-0 mx-border-outline']//div[5]/div[2]//span//input[@name='isExcludeTask']")
   public List<WebElement> isExcludeTaskFromFutureTF;

   @FindBy(xpath="//div[@class='row my-3 mx-0 mx-border-outline']//div[5]/div[2]//span//input[@name='isExcludeServiceRequest']")
   public List<WebElement> isExcludeServiceRequestFutureTF;

   @FindBy(xpath="//div[@class='row my-3 mx-0 mx-border-outline']//div[5]/div[1]/div[2]//label//input")
   public List<WebElement> sunListFutureTF;

   @FindBy(xpath="//div[@class='row my-3 mx-0 mx-border-outline']//div[5]/div[1]/div[3]//label//input")
   public List<WebElement> monListFutureTF;

   @FindBy(xpath="//div[@class='row my-3 mx-0 mx-border-outline']//div[5]/div[1]/div[4]//label//input")
   public List<WebElement> tueListFutureTF;

   @FindBy(xpath="//div[@class='row my-3 mx-0 mx-border-outline']//div[5]/div[1]/div[5]//label//input")
   public List<WebElement> wedListFutureTF;

   @FindBy(xpath="//div[@class='row my-3 mx-0 mx-border-outline']//div[5]/div[1]/div[6]//label//input")
   public List<WebElement> thuListFutureTF;

   @FindBy(xpath="//div[@class='row my-3 mx-0 mx-border-outline']//div[5]/div[1]/div[7]//label//input")
   public List<WebElement> friListFutureTF;

   @FindBy(xpath="//div[@class='row my-3 mx-0 mx-border-outline']//div[5]/div[1]/div[8]//label//input")
   public List<WebElement> satListFutureTF;

   @FindBy(xpath="//p[contains(text(),'The End Date must be greater than the Start Date')]")
   public WebElement endDateErrorMsg;

   @FindBy(xpath="//p[contains(text(),'The Start Date cannot be in the past')]")
   public WebElement startDateCanNotBePastErrorMsg;

   @FindBy(xpath = "//*[text()='The Business Days Name is required and cannot be left blank.']")
   public WebElement nameRequiredErrorMsg;

   @FindBy(xpath="//p[contains(text(),'The Time Frame you are entering conflicts with an existing Time Frame.')]")
   public WebElement conflictsErrorMsg;

   @FindBy(xpath = "//*[text()='If you navigate away, your information will not be saved']")
   public WebElement navigationWarningMsg;

   @FindBy(xpath = "//*[text()='Cancel']/..")
   public WebElement warningMsgCancelBtn;

   @FindBy(xpath = "//*[text()='Continue']/..")
   public WebElement warningMsgContinueBtn;

   @FindBy(xpath = "//i[text()='calendar_today']/following-sibling::span")
   public List<WebElement> copyPreviousCalendarList;

   @FindBy(xpath = "//i[text()='calendar_today']/following-sibling::span")
   public WebElement copyPreviousCalendar;

   public int getDefaultYear() {
      String defaultYear = this.defaultYear.getAttribute("value");
      return Integer.parseInt(defaultYear);
   }

   public List<String> getYears(List<String> tempStartDate) {
      return tempStartDate.stream()
              .map(date -> StringUtils.substringAfterLast(date, "/"))
              .collect(Collectors.toList());
   }

}
