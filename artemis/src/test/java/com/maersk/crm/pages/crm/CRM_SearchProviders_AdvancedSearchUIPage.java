package com.maersk.crm.pages.crm;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRM_SearchProviders_AdvancedSearchUIPage {
    //@sean thorson




    

    public CRM_SearchProviders_AdvancedSearchUIPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }


    @FindBy(xpath = "//span[contains(text(), 'Provider Search')]")
    public WebElement searchProvider;


    @FindBy(xpath = "(//span[contains(@class, 'MuiButton-label')])[3]")
    public WebElement advancesearch;

    @FindBy(xpath = "//div[contains(@class, 'row py-2 px-2')]")
    public WebElement additionalarea;

    @FindBy(xpath = "(//div[contains(@class, 'MuiAutocomplete-endAdornment')]/button[contains(@title, 'Clear')])[6]")
    public WebElement languageclearicon;

    @FindBy(xpath = " //input[contains(@id, '_LANGUAGE')]")
    public WebElement selectlanguage;




    @FindBy(xpath = "//*[@name='pcpFlag']/..")
    public WebElement pcpindicator;

    @FindBy(xpath = "//*[@name='acceptObInd']/..")
    public WebElement selectobindicator;

    @FindBy(xpath = "//*[@name='acceptNewPatientsInd']/..")
    public WebElement providerAcceptNewPatient;

    @FindBy(xpath = "//*[@name='sexLimitsCds']/..")
    public WebElement selectproviderGenderServed;

    @FindBy(xpath = "//*[@name='genderCd']/..")
    public WebElement selectproviderGender;
    @FindBy(xpath = "//input[contains(@id, '_COUNTY')]")
    public WebElement selectcounty;

    @FindBy(xpath = "//input[contains(@id, '_STATE')]")
    public WebElement selectstate;

    @FindBy(xpath = " //input[contains(@id, 'providerPatientMinAge')]")
    public WebElement enterminage;
    @FindBy(xpath = " //input[contains(@id, 'providerPatientMaxAge')]")
    public WebElement entermaxage;
    @FindBy(xpath = "//input[contains(@id, 'npi')]")
    public WebElement enternpi;

    @FindBy(xpath = "//div[contains(@id, 'mui-component-select-providerAcceptNewPatient')]")
    public WebElement defaultvalueofselctnewpatient;


    @FindBy(xpath = "//i[contains(text(), 'chevron_right')]")
    public WebElement expandproviderdetail;


    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][1]/p[2]")
    public WebElement getlanguage;

    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][2]/p[2]")
    public WebElement getpcpindicator;

    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][3]/p[2]")
    public WebElement getobindicator;

    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][4]/p[2]")
    public WebElement getgenderserved;


    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][5]/p[2]")
    public WebElement getprovidergender;

    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][6]/p[2]")
    public WebElement getPatientminage;

    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][7]/p[2]")
    public WebElement getPatientmaxage;

    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][8]/p[2]")
    public WebElement getNPI;

    @FindBy(xpath = "//div[@class='col-2-5 py-3'][7]//p[2]")
    public WebElement getNPI1;
    @FindBy(xpath = "//thead[contains(@class, 'MuiTableHead-root')]/tr/th[2]")
    public WebElement providername;

    @FindBy(xpath = "//thead[contains(@class, 'MuiTableHead-root')]/tr/th[3]")
    public WebElement provideraddress;

    @FindBy(xpath = "//thead[contains(@class, 'MuiTableHead-root')]/tr/th[4]")
    public WebElement groupname;

    @FindBy(xpath = "//thead[contains(@class, 'MuiTableHead-root')]/tr/th[5]")
    public WebElement planname;

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



    @FindBy(xpath = "//i[contains(text(), 'keyboard_arrow_down')]")
    public WebElement collapseproviderdetail;

    @FindBy(xpath = "//i[contains(text(), 'chevron_right')]")
    public List<WebElement > rowcount;



    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][3]/p")
    public WebElement obindicator;

    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][4]/p")
    public WebElement genderserved;



    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][6]/p")
    public WebElement patientminage;

    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][7]/p")
    public WebElement patientmaxage;

    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root mx-table-collapse')]/descendant::div[contains(@class, 'py-3')][8]/p")
    public WebElement npi;






    @FindBy(xpath = "//input[contains(@name, 'providerPCPIndicator')]")
    public WebElement expandpcplist;


//@sean thorson@

    @FindBy(xpath = "//input[contains(@id, 'providerFirstName')]")
    public WebElement providerfirstname;

    @FindBy(xpath = "//input[contains(@id, 'providerLastName')]")
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
    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root MuiTableRow-head')]/descendant::th[7]/span")
    public WebElement sortbyspecialaty;
    @FindBy(xpath = "//tbody[contains(@class, 'MuiTableBody-root')]/descendant::tr[contains(@class, 'MuiTableRow-root')]/td[2]/span")
    public WebElement getName;
    @FindBy(xpath = "//tbody[contains(@class, 'MuiTableBody-root')]/descendant::tr[contains(@class, 'MuiTableRow-root')]/td[3]/span")
    public WebElement getaddress;



    @FindBy(xpath = "//tbody[contains(@class, 'MuiTableBody-root')]/descendant::tr[contains(@class, 'MuiTableRow-root')]/td[5]/span")
    public WebElement getplanname;



    @FindBy(xpath = "//tbody[contains(@class, 'MuiTableBody-root')]/descendant::tr[contains(@class, 'MuiTableRow-root')]/td[6]/span")
    public WebElement getProviderType;

    @FindBy(xpath = "//tbody[contains(@class, 'MuiTableBody-root')]/descendant::tr[contains(@class, 'MuiTableRow-root')]/td[7]/span")
    public WebElement getSpeciality;


    @FindBy(xpath ="(//span[contains(@id, 'client-snackbar')])[2]")
    public WebElement resultCount;

    @FindBy(xpath ="(//button[contains(@class, 'primary mx-btn-cancel ml-2')])[2]/span")
    public WebElement CancelButton;


    @FindBy(xpath ="(//tr//td[3]//span//div[1])[2]")
    public WebElement getFirstProviderAddress;

    @FindBy(xpath ="(//*[@id=\"client-snackbar\"])[2]")
    public WebElement getErrorMsgPopup;

    @FindBy(xpath ="(//*[@id=\"client-snackbar\"])[1]")
    public WebElement getErrorMsg;
}
