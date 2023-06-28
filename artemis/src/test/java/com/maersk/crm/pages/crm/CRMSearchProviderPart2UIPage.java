package com.maersk.crm.pages.crm;


        import com.maersk.crm.utilities.Driver;
        import org.openqa.selenium.By;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.FindAll;
        import org.openqa.selenium.support.FindBy;
        import org.openqa.selenium.support.PageFactory;

        import java.util.List;

public class CRMSearchProviderPart2UIPage {





    

    public CRMSearchProviderPart2UIPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }


    @FindBy(xpath = "//span[contains(text(), 'Provider Search')]")
    public WebElement searchProvider;

    @FindBy(xpath = "//button[contains(text(), 'SEARCH PROVIDER')]")
    public WebElement searchProviderbutton;

    @FindBy(xpath = "//span[contains(text(),'search')]/../..")
    public WebElement searchbutton;

    @FindBy(xpath = "//button//span[.='search']//../../following-sibling::button")
    public WebElement cancelbutton;

    @FindBy(xpath = "//tbody[1]/tr[1]/td[8]/span[1]/button[1]/i[1]")
    public WebElement checkmarkButton;

    @FindBy(xpath = "//h5[contains(text(), 'SELECTED CONSUMER(S)')]")
    public WebElement selectedConsumers;

    @FindBy(xpath = "//h5[contains(text(), 'SELECTED CONSUMER(S)')]")
    public WebElement selectedPlans;

    @FindBy(xpath = "//h5[contains(text(), 'SELECTED PROVIDER(S)')]")
    public WebElement selectedProviders;

    @FindBy(xpath = "(//tbody)[2]/tr[1]/td[2]")
    public WebElement providerName;

    @FindBy(xpath = "//tbody[2]/tr[1]/td[2]")
    public WebElement providerAddressResult;

    @FindBy(xpath = "(//tbody)[2]/tr[1]/td[3]/div/span/div/div")
    public WebElement providerFirstAddressResult;

    @FindBy(xpath = "//div[@id='contactSearch']//tr[@class='MuiTableRow-root'][1]/td[3]/div/span//i[@title='call']")
    public WebElement providerPhoneResult;

    @FindBy(xpath = "//tbody[1]/tr[1]/td[4]")
    public WebElement providerDistanceResult;

    @FindBy(xpath = "//div[@id='contactSearch']//tr[@class='MuiTableRow-root'][1]/td[5]/div/span")
    public WebElement providerLanguageResult;

    @FindBy(xpath = "//div[@id='contactSearch']//tr[@class='MuiTableRow-root'][1]/td[6]/div/span")
    public WebElement providerSpecialityResult;

    @FindBy(xpath = "//div[@id='contactSearch']//tr[@class='MuiTableRow-root'][1]/td[7]/div/span")
    public WebElement lastUpdatedResult;


    @FindBy(xpath = "//i[contains(text(),'link')]")
    public WebElement selectProvider;

    @FindBy(xpath = "//p[contains(text(),'PROVIDER NAME')]")
    public WebElement providerNameField;

    @FindBy(xpath = "//p[contains(text(),'PROVIDER NAME')]//following-sibling::p")
    public WebElement providerNameFieldValue;

    @FindBy(xpath = "//p[contains(text(),'NPI')]")
    public WebElement providerNPI;

    @FindBy(xpath = "//input[contains(@id, 'distance')]")
    public WebElement distancefield;
    @FindBy(xpath = "//input[@id='_CITY']")
    public WebElement cityfield;
    @FindBy(xpath = "//input[@id='_CITY']/..//button[1]")
    public WebElement cityFieldClearButton;
    @FindBy(xpath = "//input[contains(@id, '_ZIP')]")
    public WebElement zipfield;
    @FindBy(xpath = "//input[@id='_ZIP']/..//button[1]")
    public WebElement zipFieldClearButton;
    @FindBy(xpath = "//input[@id='distance']")
    public WebElement distanceField;
    @FindBy(xpath = "//input[@id='genderCd']")
    public WebElement genderField;

    public WebElement getParentFieldForHover(WebElement field) {
        return field.findElement(By.xpath("./../../.."));
    }

    @FindBy(xpath = "//*[contains(text(), 'Miles')]")
    public List <WebElement> milesColumn;

    @FindBy(xpath = "//*[contains(text(), '-- --')]")
    public List <WebElement> milesColumn2;

    @FindBy(xpath = "//span[contains(@id, 'distance-km')]")
    public WebElement distancetooltip;

    @FindBy(xpath = "//div[@class='MuiTooltip-tooltip jss17 MuiTooltip-tooltipPlacementTop MuiTooltip-tooltipArrow']")
    public WebElement distancetooltip1;

    //div[@role='tooltip']
    @FindBy(xpath = "//span[contains(text(), 'DISTANCE')]")
    public WebElement distancecolumn;

    @FindBy(xpath = "//input[contains(@id, 'addressLine1')]")
    public WebElement streetaddress;

    @FindBy(xpath = "//input[@id='addressLine1']")
    public WebElement streetaddressField;

    @FindBy(xpath = "//input[@id='addressLine1']")
    public WebElement addressLine;

//    @FindBy(xpath = "//input[@id='_SPECIALITY']")
    @FindBy(xpath = "//input[@id='_SPECIALTY']")
    public WebElement specialityField;

//    @FindBy(xpath = "//input[@id='_SPECIALITY']/..//button[1]")
    @FindBy(xpath = "//input[@id='_SPECIALTY']/..//button[1]")
    public WebElement specialityFieldClearButton;

    @FindBy(xpath = "//li[@id='_SPECIALTY-option-71']")
    public WebElement specialityOption;

    @FindBy(xpath = "//input[@id='_LANGUAGE']")
    public WebElement languageField;

    @FindBy(xpath = "//input[@id='_LANGUAGE']/..//button[1]")
    public WebElement languageFieldClearButton;

    @FindBy(xpath = "//li[@id='_LANGUAGE-option-6']")
    public WebElement languageOption;

    @FindBy(xpath = "//span[contains(text(), 'PROVIDER NAME')]")
    public WebElement providerNameResults;

    @FindBy(xpath = "//span[contains(text(), 'PROVIDER ADDRESS')]")
    public WebElement providerAddressResults;

    @FindBy(xpath = "//span[contains(text(), 'DISTANCE')]")
    public WebElement providerDistanceResults;

    @FindBy(xpath = "//span[contains(text(), 'LANGUAGE')]")
    public WebElement providerLanguageResults;

    @FindBy(xpath = "//span[contains(text(), 'SPECIALTY')]")
    public WebElement providerSpecialityResults;

    @FindBy(xpath = "//span[contains(text(), 'LAST UPDATED')]")
    public WebElement lastUpdated;


    @FindBy(xpath = "//div[@id='mui-component-select-genderCd']")
    public WebElement providerGenderField;

    @FindBy(xpath = "//li[@data-value='Male']")
    public WebElement providerGenderOption;

    @FindBy(xpath = "//input[@id='_AGE RANGE ']")
    public WebElement ageRangeField;

    @FindBy(xpath = "//input[@id='_AGE RANGE ']/..//button[1]")
    public WebElement ageRangeFieldClearButton;

    @FindBy(xpath = "//li[@id='_AGE RANGE -option-0']")
    public WebElement ageRangeOption;
    @FindBy(xpath = "//li[@id='_AGE RANGE -option-1']")
    public WebElement ageRangeOption1;
    @FindBy(xpath = "//li[@id='_AGE RANGE -option-2']")
    public WebElement ageRangeOption2;
    @FindBy(xpath = "//li[@id='_AGE RANGE -option-3']")
    public WebElement ageRangeOption3;

    @FindBy(xpath = "//i[contains(text(), 'chevron_right')]")
    public List<WebElement> expandArrow;

    @FindBy(xpath = "//i[contains(text(), 'keyboard_arrow_down')]")
    public WebElement minimizeArrow;

    public WebElement getDynamicElement(int index){
        return Driver.getDriver().findElement(By.xpath("(//i[contains(text(), 'chevron_right')])["+index+"]"));
    }

    public WebElement getDynamicGenderServedValues(int index){
        return Driver.getDriver().findElement(By.xpath("(//p[text()='GENDER SERVED']/following-sibling::p)["+index+"]"));
    }

    @FindBy(xpath = "//*[contains(text(),'Male')]")
    public List<WebElement> providerGenderSearchResult;

    @FindBy(xpath = "//body[1]//tbody[1]/tr[2]/td[1]/div[1]/div[1]/div[2]/p[2]")
    public WebElement ageRangeSearchResult;

    @FindBy(xpath = "//span[contains(text(),'ENGLISH')]")
    public List<WebElement> languageSearchResult;

    @FindBy(xpath = "//span[contains(text(),'Family Practice')]")
    public List<WebElement> specialitySearchResult;


    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT(S)')]")
    public WebElement searchResult;


    @FindBy(xpath = "(//span[contains(@class, 'MuiButton-label')])[3]")
    public WebElement advancesearch;

    @FindBy(xpath = "//div[contains(@class, 'row py-2 px-2')]")
    public WebElement additionalarea;

    @FindBy(xpath = "(//div[contains(@class, 'MuiAutocomplete-endAdornment')]/button[contains(@title, 'Clear')])[6]")
    public WebElement languageclearicon;

    @FindBy(xpath = " //input[contains(@id, '_LANGUAGE')]")
    public WebElement selectlanguage;


    @FindBy(xpath = "//thead/tr[1]/th[5]/button[1]/span[1]/span[2]")
    public WebElement languageFilter;

    @FindBy(xpath = "//thead/tr[1]/th[6]/button[1]/span[1]/span[2]")
    public WebElement specialityFilter;

    @FindBy(xpath = "//input[@value='English']")
    public WebElement languageFilterCheckbox;

    @FindBy(xpath = "//body/div[@id='']//label[1]//input[1]")
    public WebElement specialityFilterCheckbox;

    @FindBy(xpath = "//span[contains(text(),'FILTER')]")
    public WebElement filterButton;

    @FindBy(xpath = "//*[@name='providerPCPIndicator']/..")
    public WebElement pcpindicator;

    @FindBy(xpath = "//*[@name='providerOBIndicator']/..")
    public WebElement selectobindicator;

    @FindBy(xpath = "//*[@name='providerAcceptNewPatient']/..")
    public WebElement providerAcceptNewPatient;

    @FindBy(xpath = "//*[@name='providerGenderServed']/..")
    public WebElement selectproviderGenderServed;

    @FindBy(xpath = "//*[@name='providerGender']/..")
    public WebElement selectproviderGender;
    @FindBy(xpath = " //input[contains(@id, '_COUNTY')]")
    public WebElement selectcounty;

    @FindBy(xpath = " //input[contains(@id, '_STATE')]")
    public WebElement selectstate;

    @FindBy(xpath = " //input[contains(@id, 'providerPatientMinAge')]")
    public WebElement enterminage;
    @FindBy(xpath = " //input[contains(@id, 'providerPatientMaxAge')]")
    public WebElement entermaxage;
    @FindBy(xpath = "//input[contains(@id, 'providerNPI')]")
    public WebElement enternpi;

    @FindBy(xpath = "//div[contains(@id, 'mui-component-select-providerAcceptNewPatient')]")
    public WebElement defaultvalueofselctnewpatient;

    @FindBy(xpath = "//i[contains(text(), 'chevron_right')]")
    public WebElement expandproviderdetail;

    @FindBy(xpath = "//i[contains(text(), 'chevron_right')]")
    public List <WebElement> expandproviderdetailList;


    @FindBy(xpath = "//button[@class=\"mdl-button mdl-js-button mdl-button--icon mdl-button--colored ml-2\"]")
    public List <WebElement> expandproviderList;

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
    @FindBy(xpath = "//thead[contains(@class, 'MuiTableHead-root')]/tr/th[2]")
    public WebElement providername;

    @FindBy(xpath = "//p[contains(text(),'PROVIDER GENDER')]")
    public WebElement providerGender;

    @FindBy(xpath = "//p[contains(text(),'PCP')]")
    public WebElement pcp;

    @FindBy(xpath = "//p[contains(text(),'Yes')]")
    public WebElement pcpYes;

    @FindBy(xpath = "//p[contains(text(),'OB/GYN')]")
    public WebElement obgyn;

    @FindBy(xpath = " //p[contains(text(),'AGE RANGE')]")
    public WebElement ageRange;

    @FindBy(xpath = " //p[contains(text(),'NPI')]")
    public WebElement npiProvider;

    @FindBy(xpath = " //h5[contains(text(),'LANGUAGES')]")
    public WebElement providerLaguages;

    @FindBy(xpath = " //h5[contains(text(),'SPECIALITIES')]")
    public WebElement providerSpecialities;
    @FindBy(xpath = " //th[contains(text(),'PROVIDER ADDRESS')]")
    public WebElement providerAddress;
    @FindBy(xpath = " //th[contains(text(),'PHONE NUMBER')]")
    public WebElement providerPhone;
    @FindBy(xpath = "  //th[contains(text(),'FAX NUMBER')]")
    public WebElement providerFax;
    @FindBy(xpath = "  //th[contains(text(),'FAX NUMBER')]")
    public WebElement providerEmail;
    @FindBy(xpath = " //th[contains(text(),'NEW PATIENTS')]")
    public WebElement providerNewPatients;
    @FindBy(xpath = " //th[contains(text(),'HANDICAP')]")
    public WebElement handicap;
    @FindBy(xpath = " //th[contains(text(),'OFFICE HOURS')]")
    public WebElement officeHours;

    @FindBy(xpath = "//thead[contains(@class, 'MuiTableHead-root')]/tr/th[3]")
    public WebElement provideraddress;

    @FindBy(xpath = "//thead[contains(@class, 'MuiTableHead-root')]/tr/th[4]")
    public WebElement groupname;

    @FindBy(xpath = "//thead[contains(@class, 'MuiTableHead-root')]/tr/th[5]")
    public WebElement planname;

    @FindBy(xpath = "//p[contains(text(),'AMERIGROUP COMMUNITY CARE')]")
    public WebElement planName;

    @FindBy(xpath = "//span[contains(text(),'keyboard_backspace')]")
    public WebElement backButton;

    @FindBy(xpath = "//h5[contains(text(), 'PROVIDER SEARCH')]")
    public WebElement providerSearch;

@FindBy(xpath = "//input[@id='_PATIENT AGE RANGE']")
public WebElement patientAgeRangeInput;

@FindBy(xpath= "//input[@id='_COUNTY']")
public WebElement CountyNameOption;

@FindBy(xpath = "//div[@id='mui-component-select-sexLimitsCds']")
public WebElement genderServedInput;

@FindBy(xpath = "//div[@id='mui-component-select-providerTypeCd']")
public WebElement providerTypeDropdown;

@FindBy(xpath = "//p[text()='GENDER SERVED']/following-sibling::p")
public List<WebElement> getGenderServedfromrecords;

    @FindBy(xpath = "//div[@role='presentation']")
    public WebElement presentationPopUp;

    @FindBy(xpath = "//div[@role='presentation']//ul//li")
    public List<WebElement> dropdownOptions;

    @FindBy(xpath = "//ul[@id='_COUNTY-popup']//li")
    public List<WebElement> Countydropdownlist;

    @FindBy(xpath = "//ul[@role='listbox']//li")
    public List<WebElement> dropdownOptions2;

    @FindBy(xpath = "//div[@class='MuiPaper-root MuiMenu-paper MuiPopover-paper MuiPaper-elevation8 MuiPaper-rounded']//ul//li")
    public List<WebElement> genderServedDropdownList;

    @FindBy(xpath = "//div[./h5[contains(text(), 'PROVIDER SEARCH')]]")
    public WebElement providerSearchForm;

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


    @FindBy(xpath = "//input[contains(@name, 'providerPCPIndicator')]")
    public WebElement expandpcplist;

    @FindBy(xpath = "//input[contains(@id, 'firstName')]")
    public WebElement providerfirstname;

    @FindBy(xpath = "//input[contains(@id, 'lastName')]")
    public WebElement providerlastname;

    @FindBy(xpath = "//input[contains(@id, 'firstName')]")
    public WebElement providerFirstname;

    @FindBy(xpath = "//input[contains(@id, 'lastName')]")
    public WebElement providerLastname;

    @FindBy(xpath = "//input[@id='phoneNumber']")
    public WebElement providerphoneNumber;

    @FindBy(xpath = "//input[@id='searchOperator']")
    public WebElement searchOperatorToggle;

    @FindBy(xpath = "//input[@id='_PLAN NAME']")
    public WebElement providerplanName;

    @FindBy(xpath = "//button[@title='Open']")
    public WebElement providerplanNameDropDownButton;

    public WebElement getProviderPlanNameOptionByValue(String value) {
        return Driver.getDriver().findElement(By.xpath("//li[.='"+ value +"']"));
    }

//    @FindBy(xpath = "//tbody[contains(@class, 'MuiTableBody-root')][2]/descendant::tr[contains(@class, 'MuiTableRow-root')]/td[4]/span")
    @FindBy(xpath = "(//table)[2]//tbody[contains(@class, 'MuiTableBody-root')]//tr/td[4]")
    public WebElement providerPlanNameResult;

    @FindBy(xpath = "//div[@id='contactSearch']//tr[@class='MuiTableRow-root'][1]/td[3]/div/span")
    public WebElement provideraddressResult;

    @FindBy(xpath = "//div[@id='contactSearch']//tr[@class='MuiTableRow-root'][1]/td[3]/span/div")
    public List<WebElement> provideraddressResultList;


    @FindBy(xpath = "//div[@id='contactSearch']//tr[@class='MuiTableRow-root'][1]/td[2]/div/span")
    public WebElement providernameResult;

    @FindBy(xpath = "//div[@id='contactSearch']//tr[@class='MuiTableRow-root'][1]/td[2]/span")
    public List<WebElement> providernameResultList;

    @FindBy(xpath = "//div[@id='contactSearch']//tr[@class='MuiTableRow-root'][1]/td[4]/span")
    public List<WebElement> providergroupnameList;

    @FindBy(xpath = "//div[@id='contactSearch']//tr[@class='MuiTableRow-root'][1]/td[5]/span")
    public List<WebElement> providerplannameList;

    @FindBy(xpath = "//tr[@class='MuiTableRow-root']//td[2]")
    public List<WebElement> nameResultList;

    @FindBy(xpath = "//input[contains(@id, 'lastName')]")
    public WebElement provideNameResults;

    @FindBy(id = "groupName")
    public WebElement providergroupname;

    @FindBy(id = "phoneNumber")
    public WebElement ProviderPhoneno;

    @FindBy(xpath = "//button[contains(@title, 'Open')]")
    public WebElement OpenCombo;

    @FindBy(xpath = "//input[contains(@id, '_SPECIALITY')]")
    public WebElement selectspeciality;

    @FindBy(id = "mui-component-select-providerTypeCd")
    public WebElement providertype;

    public WebElement getProviderTypeOptionByName(String option) {
        return presentationPopUp.findElement(By.xpath("//li[@data-value='" +    option +   "']"));
    }

    @FindBy(xpath = "//*[@name='itemsPerPage']/..")
    public WebElement itemsPerPage;

    @FindBy(xpath = "//li[contains(@data-value, '40')]")
    public WebElement select40items;

    @FindBy(xpath = "//li[contains(@data-value, '60')]")
    public WebElement select60items;

    @FindBy(xpath = "//input[contains(@id, '_PLAN NAME')]")
    public WebElement SelectOption;

    @FindBy(xpath = "//span[text()='SEARCH']")
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

    @FindBy(xpath ="//button//span[text()='CANCEL']")
    public WebElement CancelButton;

@FindBy(xpath = "//tr[@class='MuiTableRow-root']//td[4]//div")
public WebElement getDistance;

    @FindBy(xpath ="(//td/span)[3]")
    public WebElement getFirstDistance;

    @FindBy(xpath ="//*[contains(text(), 'Adairsville')]")
    public List <WebElement> providerAddressColumn;

    @FindBy(xpath = "//h6[contains(text(), ' SEARCH RESULT(S)')]/..//tr/td[2]")
    public List<WebElement> providerNames;

    @FindBy(xpath = "//div[@id = 'contactSearch']")
    public WebElement providerSearchResultForm;

    @FindBy(xpath = "//div[@id = 'contactSearch']//thead//th[2]/span")
    public WebElement nameFieldTitle;

    public WebElement getButtonInGivenParentByName(WebElement parent, String name){
        return parent.findElement(By.xpath(".//button[contains(span, '" + name + "')]"));
    }

    public WebElement getFieldInGivenParent(WebElement parent, String fieldName){
        return parent.findElement(By.xpath(".//label[.='" + fieldName + "']"));
    }

    public WebElement getInputByFieldInGivenParent(WebElement parent, String fieldName){
        return parent.findElement(By.xpath(".//label[.='" + fieldName + "']/..//input"));
    }

    public WebElement getProviderSearchResultTableHeaderInGivenParentByName(WebElement parent, String name){
        return parent.findElement(By.xpath(".//th[contains(span, '" + name + "')]"));
    }

    public List<WebElement> getProviderAddressfieldsInGivenParent(WebElement parent){
        return parent.findElements(By.xpath(".//td//div[@name = 'overflow']"));
    }

    public WebElement getAcceptingNewPatiensIconInGivenParent(WebElement parent){
        return parent.findElement(By.xpath(".//i[@title = 'Yes Accepting New Patients']"));
    }

    public WebElement getAcceptingHandicapIconInGivenParent(WebElement parent){
        return parent.findElement(By.xpath(".//i[@title = 'Accepting Handicap']"));
    }

    public List<WebElement> getProviderDistanceFieldsInGivenParent(WebElement parent) {
        return parent.findElements(By.xpath(".//td[4]/span"));
    }

    public List<WebElement> getProviderNameFieldsInGivenParent(WebElement parent) {
        return parent.findElements(By.xpath(".//td[2]//span"));
    }

    @FindBy(xpath = "//div[@id = 'contactSearch']//tbody[1]")
    public WebElement firstFoundProvider;

    @FindBy(xpath = "//div[@id = 'contactSearch']//table//tbody//tr[1]//td[2]//span")
    public WebElement firstFoundProviderName;

    public WebElement getExpandButtonInGivenParent(WebElement parent){
        return parent.findElement(By.xpath(".//tr[1]/td[1]//button"));
    }

    public WebElement getfieldByGivenLabelInGivenParentInExpandedView(WebElement parent, String name) {
        return parent.findElement(By.xpath(".//p[. = '" + name + "']/following-sibling::p"));
    }

    @FindBy(xpath = "//p[contains(text(),'NPI')]/following-sibling::p")
    public List<WebElement> getListOfNPI;

    @FindBy(xpath = "//div[@class=\"col-2-5 py-3\"][7]/p[2]")
    public WebElement getListOfNPI1;
    //div[@class="col-2-5 py-3"][7]/p[2]

    @FindBy(xpath = "//h5[contains(text(), 'PROVIDER DETAILS')]/..//div[@class='row']")
    public WebElement providerDetailsFieldsParent;

    @FindBy(xpath = "//h5[contains(text(), 'LOCATION ASSOCIATED TO THE PROVIDER')]/..")
    public WebElement locationAssociatedtoTheProviderParent;

    @FindBy(xpath = "//h5[contains(text(), 'SPECIALITIES')]/..//span")
    public List<WebElement> providerSpecialties;

    @FindBy(xpath = "//h5[contains(text(), 'HOSPITAL AFFILIATION')]/..//span")
    public List<WebElement> providerHospitalAffiliations;

    @FindBy(xpath = "//h5[contains(text(), 'LANGUAGES')]/..//span")
    public List<WebElement> providerLanguages;

    public WebElement getFieldInsideProviderDetailsByName(String labelName) {
        return providerDetailsFieldsParent.findElement(By.xpath(
                ".//*[.='" + labelName + "']/following-sibling::*"
        ));
    }

    public WebElement getFieldInsideLocationsAssociatedToTheProviderByName(String labelName) {
        return locationAssociatedtoTheProviderParent.findElement(By.xpath(".//*[.='" + labelName + "']"));
    }

    @FindBy(xpath = "//div[@id = 'contactSearch']//table")
    public WebElement providerSearchResultsTable;

    @FindBy(xpath = "//div[@id = 'contactSearch']//th/span")
    public List<WebElement> providerSearchResultsTableHeads;

    public List<WebElement> getAllCellsByColumnIndex(int columnIndex) {
        return providerSearchResultsTable.findElements(By.xpath(".//td[" + columnIndex + "]"));
    }

    @FindBy(xpath = "//div[@id='mui-component-select-providerTypeCd']")
    public WebElement providerTypeInputBx;

    @FindBy(xpath = "//div[@id='mui-component-select-handicappedAccesibleInd']")
    public WebElement handicapAccessibleInputBx;

    @FindBy(xpath = "//div[@id='mui-component-select-acceptNewPatientsInd']")
    public WebElement acceptingNewPatientsDrop;

    @FindBy(xpath = "//i[@title='Not Accepting Handicap']")
    public List<WebElement> getNotHandicapAccessibleIcon;

    public WebElement getAdvanceSearchField(String fieldName)
    {
        return Driver.getDriver().findElement(By.xpath("//div[@class='mdl-color--grey-50 px-2 mb-4']//div//label[text()='"+fieldName+"']"));
    }

    @FindBy(xpath = "//i[@title='Accepting Handicap']")
    public List<WebElement> getHandicapAccessibleIcon;

    @FindBy(xpath = "(//span[@id='client-snackbar'])[2]")
    public WebElement warningMessageText;

    @FindBy(id = "mui-component-select-itemsPerPage")
    public WebElement displayNumberOfProviders;

    public WebElement getDropDownOptionByValue(String value) {
        return Driver.getDriver().findElement(By.xpath("//ul[@role='listbox']//li[@data-value='"+ value +"']"));
    }

    @FindBy(xpath = "//a/span[.='arrow_forward']")
    public WebElement forwardPaginator;

    @FindBy(xpath = "(//button[@class='mdl-button mdl-js-button mdl-button--icon mx-btn-secondary'])[1]")
    public WebElement firstCheckMarkButton;

    @FindBy(xpath = "//h5[contains(text(), 'SELECTED PROVIDER(S)')]/..//div/p[.=' PROVIDER NAME']")
    public WebElement providerNameUnderSelectedProviders;

    @FindBy(xpath = "//h5[contains(text(), 'SELECTED PROVIDER(S)')]/..//div/p[.='PROVIDER TYPE']")
    public WebElement providerTypeUnderSelectedProviders;

    @FindBy(xpath = "//h5[contains(text(), 'SELECTED PROVIDER(S)')]/..//div/p[.='NPI']")
    public WebElement NPIUnderSelectedProviders;

    public WebElement goToPageNumber(int page) {
        return Driver.getDriver().findElement(By.xpath("//div[@class='collapse show']//a[@aria-label='Go to page number "+  page +"']"));
    }
    @FindBy(xpath = "//h5[contains(text(), 'HOSPITAL AFFILIATION')]/..//span")
    public List<WebElement> providerhosliptalAffiliation;

    @FindBy(xpath = "//h6[text()=' SEARCH RESULT']//following-sibling::div//tr//td[2]//div//span")
    public List<WebElement> firstProviderName;

    @FindBy(xpath = "(//h5[contains(text(), 'SELECTED PROVIDER(S)')]/..//div/p[contains(.,'PROVIDER TYPE')]//following-sibling::p)")
    public List<WebElement> providerTypeDataUnderSelectedProviders;

    @FindBy(xpath = "(//h5[contains(text(), 'SELECTED PROVIDER(S)')]/..//div/p[contains(.,'PROVIDER NAME')]//following-sibling::p)")
    public List<WebElement> providerNameDataUnderSelectedProviders;

    @FindBy(xpath = "(//h5[contains(text(), 'SELECTED PROVIDER(S)')]/..//div/p[contains(.,'NPI')]//following-sibling::p)")
    public List<WebElement> providerNpiDataUnderSelectedProviders;

    @FindAll({
            @FindBy(xpath = "//tr//td[3]")
    })
    public List<WebElement> addressesInLocationAssociated;

    @FindBy(xpath = "//div[contains(@id,'button_')]")
    public WebElement expandAddressButton;

    @FindAll({
            @FindBy(xpath = "//div[@role='tooltip']//div//div//p[@class='mb-0']")

    } ) public List<WebElement> expandedAddress;

    @FindBy(xpath = "//tr//td[3]")
    public WebElement firstFoundProviderAddress;

    @FindBy(xpath = "//td//span[7]")
    public WebElement officeHoursSegment;

    @FindBy(xpath = "//td//span[6]")
    public WebElement officeHoursSegmentsat;

    @FindBy(xpath = "//h6[text()='Evening Hours']")
    public WebElement hovertextEveningHours;

    @FindBy(xpath = "//h6[text()=' SEARCH RESULT(S)']//following-sibling::div//tr//td[6]//div//span")
    public List<WebElement> specialtyProvider;

    @FindBy(xpath = "//tr//td[5]")
    public WebElement programName;



}
