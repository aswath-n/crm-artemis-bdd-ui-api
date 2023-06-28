package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMSearchProvidersResultViewPage {





    

    public CRMSearchProvidersResultViewPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }


    @FindBy(xpath = "//span[contains(text(), 'Provider Search')]")
    public WebElement searchProvider;


    @FindBy(xpath = "//thead[contains(@class, 'MuiTableHead-root')]/tr/th[2]")
    public WebElement providername;

    @FindBy(xpath = "//thead[contains(@class, 'MuiTableHead-root')]/tr/th[3]")
    public WebElement provideraddress;

    @FindBy(xpath = "//thead[contains(@class, 'MuiTableHead-root')]/tr/th[4]")
    public WebElement groupname;

    @FindBy(xpath = "//thead[contains(@class, 'MuiTableHead-root')]/tr/th[4]")
    public WebElement planname;

    @FindBy(xpath = "//thead[contains(@class, 'MuiTableHead-root')]/tr/th[5]")
    public WebElement programname;

    @FindBy(xpath = "//thead[contains(@class, 'MuiTableHead-root')]/tr/th[6]")
    public WebElement providertypecol;

    @FindBy(xpath = "//thead[contains(@class, 'MuiTableHead-root')]/tr/th[7]")
    public WebElement speciality;


    @FindBy(xpath = "//thead[contains(@class, 'MuiTableHead-root')]/tr/th[8]")
    public WebElement lastupdate;


    @FindBy(xpath = "//tbody[contains(@class, 'MuiTableBody-root')]/descendant::tr[contains(@class, 'MuiTableRow-root')]/td[3]/div")
    public WebElement additionaladdressicon;

    @FindBy(xpath = "//div[contains(@role, 'tooltip')]/descendant::p[1]")
    public WebElement tooltipaddress;
    @FindBy(xpath = "//div[contains(@role, 'tooltip')]/descendant::p[2]")
    public WebElement tooltipaddressphone;

    @FindBy(xpath = "//i[contains(text(), 'chevron_right')]")
    public WebElement expandproviderdetail;

    @FindBy(xpath = "//i[contains(text(), 'keyboard_arrow_down')]")
    public WebElement collapseproviderdetail;

    @FindBy(xpath = "//i[contains(text(), 'chevron_right')]")
    public List<WebElement > rowcount;


    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][1]/p")
    public WebElement language;

    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][2]/p")
    public WebElement pciindicator;

    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][3]/p")
    public WebElement obindicator;

    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][4]/p")
    public WebElement genderserved;

    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][5]/p")
    public WebElement providergender;

    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][6]/p")
    public WebElement patientminage;

    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][7]/p")
    public WebElement patientmaxage;

    @FindBy(xpath = "//div[@class='col-2-5 py-4']//p[text()='PATIENT AGE RANGE']")
    public WebElement ageRange;

    @FindBy(xpath = "//div[@class='col-2-5 py-3']//p[text()='PATIENT AGE RANGE']")
    public WebElement ageRangeExpandedView;


    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][8]/p")
    public WebElement npi;

    @FindBy(xpath = "//div[@class='col-2-5 py-3']//p[text()='NPI']")
    public WebElement npiExpandedView;


    @FindBy(xpath = "(//span[contains(@class, 'MuiButton-label')])[3]")
    public WebElement advancesearch;


    @FindBy(xpath = "//input[contains(@name, 'providerPCPIndicator')]")
    public WebElement expandpcplist;


//

    @FindBy(xpath = "//input[contains(@id, 'firstName')]")
    public WebElement providerfirstname;

    @FindBy(xpath = "//input[contains(@id, 'lastName')]")
    public WebElement providerlastname;


    @FindBy(xpath = "//input[contains(@id, 'providerGroupName')]")
    public WebElement providergroupname;

    @FindBy(xpath = "//input[contains(@id, 'providerPhoneNumber')]")
    public WebElement ProviderPhoneno;

    @FindBy(xpath = "//button[contains(@title, 'Open')]")
    public WebElement OpenCombo;

    @FindBy(xpath = "//input[contains(@id, '_SPECIALITY')]")
    public WebElement selectspeciality;
    @FindBy(xpath = "//*[@name='providerType']/..")
    public WebElement providertype;

    @FindBy(xpath = "//*[@name='providerPCPIndicator']/..")
    public WebElement pcpindicator;

    @FindBy(xpath = "//*[@name='itemsPerPage']/..")
    public WebElement itemsPerPage;

    @FindBy(xpath = "//li[contains(@data-value, '40')]")
    public WebElement select40items;

    @FindBy(xpath = "//li[contains(@data-value, '60')]")
    public WebElement select60items;

    @FindBy(xpath = "//input[contains(@id, '_PLAN NAME')]")
    public WebElement SelectOption;

    @FindBy(xpath = "(//span[contains(text(),'search')])[2]")
    public WebElement searchButton;


    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root MuiTableRow-head')]/descendant::th[2]/span")
    public WebElement sortbyname;


    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root MuiTableRow-head')]/descendant::th[3]/span")
    public WebElement sortbyaddress;
    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root MuiTableRow-head')]/descendant::th[8]/span")
    public WebElement sortbyspecialaty;

    @FindBy(xpath = "//tbody[contains(@class, 'MuiTableBody-root')]/descendant::tr[contains(@class, 'MuiTableRow-root')]/td[2]/span")
    public WebElement getName;
    @FindBy(xpath = "//tbody[contains(@class, 'MuiTableBody-root')]/descendant::tr[contains(@class, 'MuiTableRow-root')]/td[3]/span")
    public WebElement getaddress;



    @FindBy(xpath = "//tbody[contains(@class, 'MuiTableBody-root')]/descendant::tr[contains(@class, 'MuiTableRow-root')]/td[5]/span")
    public WebElement getplanname;



    @FindBy(xpath = "//tbody[contains(@class, 'MuiTableBody-root')]/descendant::tr[contains(@class, 'MuiTableRow-root')]/td[7]/span")
    public WebElement getProviderType;

    @FindBy(xpath = "//tbody[contains(@class, 'MuiTableBody-root')]/descendant::tr[contains(@class, 'MuiTableRow-root')]/td[8]/span")
    public WebElement getSpeciality;

    @FindBy(xpath = "//tbody[contains(@class, 'MuiTableBody-root')]//td[2]//div")
    public List<WebElement> getNameList;

    @FindBy(xpath = "//tbody[contains(@class, 'MuiTableBody-root')]//td[3]//div")
    public List<WebElement> getAddressList;



    @FindBy(xpath = "//tbody[contains(@class, 'MuiTableBody-root')]//td[7]//div")
    public List<WebElement> getSpecialityList;


    @FindBy(xpath ="(//span[contains(@id, 'client-snackbar')])[2]")
    public WebElement resultCount;

    @FindBy(xpath ="(//button[contains(@class, 'primary mx-btn-cancel ml-2')])[2]/span")
    public WebElement CancelButton;

    @FindBy(xpath ="//*[contains(@title, 'Yes Accepting New Patients')]")
    public WebElement aceeptPatientIcon;

    @FindBy(xpath ="//*[contains(@title, 'Not Accepting New Patients')]")
    public WebElement noAceeptPatientIcon;


    @FindAll({
            @FindBy(xpath = "//div[contains(@role, 'tooltip')]/descendant::p/i[2]")
    })
    public List<WebElement> acceptingNewPatientsicon;

    @FindAll({
            @FindBy(xpath = "//div[contains(@role, 'tooltip')]/descendant::p/span")
    })
    public List<WebElement> noAcceptingNewPatientsicon;

}
