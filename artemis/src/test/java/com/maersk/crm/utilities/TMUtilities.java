package com.maersk.crm.utilities;

import com.maersk.crm.pages.LoginPage;
import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.pages.tm.TMListOfProjectsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.Assertion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TMUtilities extends CRMUtilities implements ApiBase {


    LoginPage loginPage = new LoginPage();
    TMListOfProjectsPage tmListOfProjectsPage = new TMListOfProjectsPage();
    TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();
    public final ThreadLocal<String> updatedValue = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> priorDate = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> pastDate = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> statusValue = ThreadLocal.withInitial(String::new);

    // String excelLocation="C:\\crm-qa-automation-framework\\src\\test\\java\\com\\maersk\\crm\\utilities\\TestData.xlsx";

    final ThreadLocal<ExcelReader> excelReader2 = ThreadLocal.withInitial(()->new ExcelReader(ConfigurationReader.getProperty("excelLocation")));
    final ThreadLocal<ExcelReader> excelReader3 = ThreadLocal.withInitial(()->new ExcelReader("src/test/resources/testData/tm/planAndRegionConfig/Region_Configuration_BLCRM_AC_Program-Type.xlsx"));
    final ThreadLocal<ExcelReader> excelReader4 = ThreadLocal.withInitial(()->new ExcelReader("src/test/resources/testData/tm/planAndRegionConfig/Region_Configuration_BLCRM_AC_Sub-Program-Type.xlsx"));

    public void login(String userName, String password) {
        /*clearAndFillText(loginPage.userName, userName);
        clearAndFillText(loginPage.password, password);
        loginPage.tmLoginButton.click();*/
        tmLogin(userName, password);
    }

    public void tmLogin(String userName, String password) {
        waitFor(2);
        try {
//        fillTheFiled(loginPage.userNameCRM, userName);
//        fillTheFiled(loginPage.passwordCRM, password);
//        loginPage.submitButton.click();
            fillTheFiled(loginPage.loginUserNameOrEmail, userName);
            waitFor(1);
            loginPage.nextBtn.click();
            fillTheFiled(loginPage.passwordCRM, password);
            waitFor(1);
            loginPage.signInBtn.click();
            waitFor(5);//this wait need to discussed.
            waitForPageToLoad(10);
            click(loginPage.acceptAndContinueBtn);
            waitForVisibility(tmListOfProjectsPage.arrowClick, 20);
            waitFor(5);
        } catch (Exception e) {
            e.printStackTrace();
            click(loginPage.wrngMsgOkBtn);
            //added wait here to give sometime for UI to parse the response before making config api request.
            waitFor(3);
            waitForVisibility(loginPage.acceptAndContinueBtn, 10);
            click(loginPage.acceptAndContinueBtn);
            waitForVisibility(tmListOfProjectsPage.arrowClick, 10);
        }


    }

    /*
     * Author : Shilpa P
     * This method is used to create and save the project
     *
     * */

    //refactored 12/19 Vinuta
    //Refactored 1-31-2020 Vidya As per strory CP-1722
    public void createAndSave(String projectName, String programName, String contractId, String clientName, String timeZone) {
        clearAndFillText(tmProjectDetailsPage.projectName, projectName);
        tmProjectDetailsPage.state.click();
        hover(tmProjectDetailsPage.state);
        staticWait(1600);
        tmProjectDetailsPage.AR.click();
        waitFor(4);
        System.out.print("Element clicked ");
        clearAndFillText(tmProjectDetailsPage.programName, programName);
        clearAndFillText(tmProjectDetailsPage.contractId, contractId);
        clearAndFillText(tmProjectDetailsPage.stateAgencyName, clientName);
        tmProjectDetailsPage.provisioningStatus.click();
        hover(tmProjectDetailsPage.provisioningStatus);
        tmProjectDetailsPage.activeStatus.click();
        selectDropDown(tmProjectDetailsPage.timezonefield, timeZone);
        /*//highLightElement(tmProjectDetailsPage.saveButton);
        hover(tmProjectDetailsPage.saveButton);
        staticWait(7000);*/
        tmProjectDetailsPage.saveButton.click();
        Assert.assertTrue(tmProjectDetailsPage.projSuccessStatus.isDisplayed());
        //highLightElement(tmProjectDetailsPage.saveButton);
    }

    /*
     * Author: Shilpa P
     * This method is used to create a project with adding a contract  current
     * date and saving the project details
     *
     * */

    public void addContractDateAndSaveCurrentDate(String projectName, String programName, String contractId, String clientName, String startDate, String endDate) {
        clearAndFillText(tmProjectDetailsPage.projectName, projectName);
        tmProjectDetailsPage.state.click();
        hover(tmProjectDetailsPage.state);
        tmProjectDetailsPage.AR.click();
        waitFor(4);
        clearAndFillText(tmProjectDetailsPage.programName, programName);
        clearAndFillText(tmProjectDetailsPage.contractId, contractId);
        clearAndFillText(tmProjectDetailsPage.stateAgencyName, clientName);
        clearAndFillText(tmProjectDetailsPage.contractStartDate, startDate);
        clearAndFillText(tmProjectDetailsPage.contractEndDate, endDate);
        tmProjectDetailsPage.provisioningStatus.click();
        hover(tmProjectDetailsPage.provisioningStatus);
        tmProjectDetailsPage.activeStatus.click();
        System.out.print("Clicked");
        //staticWait(5000);
        hover(tmProjectDetailsPage.saveButton);
        highLightElement(tmProjectDetailsPage.saveButton);
        tmProjectDetailsPage.saveButton.click();
        highLightElement(tmProjectDetailsPage.saveButton);
    }

    /*
     * Author: Shilpa P
     * This method is used to create a project with adding a contract  current
     * date  and add the future date and saving the project details
     *
     * */
    public void addContractDateAndSaveCurrentDateAndEndDate(String projectName, String programName, String contractId, String clientName, String startDate, String endDate) {
        clearAndFillText(TMProjectDetailsPage.projectName, projectName);
        TMProjectDetailsPage.state.click();
        // hover(TMProjectDetailsPage.state);
        TMProjectDetailsPage.AR.click();
        clearAndFillText(TMProjectDetailsPage.programName, programName);
        clearAndFillText(TMProjectDetailsPage.contractId, contractId);
        clearAndFillText(TMProjectDetailsPage.stateAgencyName, clientName);
        clearAndFillText(TMProjectDetailsPage.contractStartDate, startDate);
        clearAndFillText(TMProjectDetailsPage.contractEndDate, endDate);
        TMProjectDetailsPage.provisioningStatus.click();
        hover(TMProjectDetailsPage.provisioningStatus);
        TMProjectDetailsPage.activeStatus.click();
        System.out.print("Clicked");
        //staticWait(5000);
        hover(TMProjectDetailsPage.saveButton);
        highLightElement(TMProjectDetailsPage.saveButton);
        TMProjectDetailsPage.saveButton.click();
        // highLightElement(TMProjectDetailsPage.saveButton);
    }


    /*
     * Author:Shilpa P
     * This method is used to search for the project Name
     * and Program Name
     * */

    public void search(String projectName, String programName) {
        //clearAndFillText(tenantManagerListOfProjectsPage.state,state);
        clearAndFillText(tmListOfProjectsPage.project, projectName);
        clearAndFillText(tmListOfProjectsPage.programName, programName);
        staticWait(1000);
        hover(tmListOfProjectsPage.search);
        tmListOfProjectsPage.search.click();
        staticWait(100);

    }
    /*
     * Author:Shilpa P
     * This method is used to select for the specific  project Name
     * */

    public void selectSearchResults() {
        highLightElement(tmListOfProjectsPage.elementSearchResults);
        //tenantManagerListOfProjectsPage.elementSearchResults.click();
        tmListOfProjectsPage.arrowClick.click();
    }

    /*
     * Author:Shilpa P
     * This method is used to get all the state names displayed in the state Name drop down
     * */

    public void getStateNames() {
        TMProjectDetailsPage.state.click();
        hover(TMProjectDetailsPage.state);
        List<WebElement> getStates = TMProjectDetailsPage.stateList;
        getStates.size();
        System.out.print("size is " + getStates.size());
        for (int i = 1; i < getStates.size(); i++) {
            System.out.println(getStates.get(i).getText());
        }
        TMProjectDetailsPage.AR.click();

    }

    /*
     * Author:Shilpa P
     * This method is used to create the project contact details and save
     * */


    public void createProjectContactAndSave(String firstName, String middleName, String lastName, String phoneNumber, String email) {
        highLightElement(TMProjectDetailsPage.role);
        hover(TMProjectDetailsPage.role);
        TMProjectDetailsPage.accountApprover.click();
        clearAndFillText(TMProjectDetailsPage.firstName, firstName);
        clearAndFillText(TMProjectDetailsPage.middleName, middleName);
        clearAndFillText(TMProjectDetailsPage.lastName, lastName);
        clearAndFillText(TMProjectDetailsPage.phoneNumber, phoneNumber);
        clearAndFillText(TMProjectDetailsPage.email, email);
        TMProjectDetailsPage.projectContactSave.click();

    }
    /*
     *  This method is used to get the cell value by sheet Name Row Name and coloumn Name
     * */

    public String getCellValueBySheetRowAndColoumn(String sheetName, String rowName, String coloumnName) {
        Map<String, String> recordByRowName = excelReader2.get().getExcelData(sheetName, rowName);
        String cellValue = recordByRowName.get(coloumnName);
        return cellValue;
    }

    public int getProgramTypeColumnRecords(String sheetName, String ColumnName) {
        List<String> columnData = excelReader3.get().getColoumnData(sheetName, ColumnName).stream().distinct().collect(Collectors.toList());
        return columnData.size();
    }

    public int getsubProgramTypeColumnRecords(String sheetName, String ColumnName) {
        List<String> columnData = excelReader4.get().getColoumnData(sheetName, ColumnName).stream().distinct().collect(Collectors.toList());
        return columnData.size();
    }


    public void swichMetodForEditProjectPage(String field, String value) {
        /* update project info on IU..... Method accepts data from feature file.
         */

       /* Since using this line of code showing validation error message and unable to save the edit project
        so commenting this code - PARAMITA Date - 21/01/2020
         if(!tmProjectDetailsPage.editEndDate.getText().replace("/", "").equals(getGreaterDate(1))){
            clearAndFillText(tmProjectDetailsPage.editEndDate, getGreaterDate(1));
            waitFor(2);
        }*/
        switch (field) {
            case "project_Name":
                /*if(value==null)
                {
                    value = getRandomString ( 6 );
                    clearAndFillText(tmProjectDetailsPage.editProjectName, value);
                }*/
                clearAndFillText(tmProjectDetailsPage.editProjectName, value);
                break;
            case "program_name":
                clearAndFillText(tmProjectDetailsPage.editProgramName, value);
                break;
            case "contract_id":
                clearAndFillText(tmProjectDetailsPage.editContractId, value);
                break;
            case "client_agency":
                clearAndFillText(tmProjectDetailsPage.editStateAgencyName, value);
                break;
            case "start_date":
                priorDate.set(getPriorDate(12));
                clearAndFillText(tmProjectDetailsPage.editStartDate, priorDate.get());
                waitFor(3);
                break;
            case "end_date":
                pastDate.set(getGreaterDate(1));
                clearAndFillText(tmProjectDetailsPage.editEndDate, pastDate.get());
                waitFor(3);
                break;
            case "state":
                if (value.equals("random"))
                    selectRandomDropDownOption(tmProjectDetailsPage.editState);
                else
                    selectDropDown(tmProjectDetailsPage.editState, value);
                updatedValue.set(tmProjectDetailsPage.editState.getText());
                break;
            case "pro_status":
//                if (tmProjectDetailsPage.editProvStatus.getText().equalsIgnoreCase(value)) {
//                    selectDropDown(tmProjectDetailsPage.editProvStatus, "Inactive");
//                }

                if (value.equals("random")) {
                    selectRandomDropDownOption(tmProjectDetailsPage.editProvStatus);
                } else {
                    selectDropDown(tmProjectDetailsPage.editProvStatus, value);
                }
                statusValue.set(tmProjectDetailsPage.editProvStatus.getText());
                break;
        }
    }

    public List<String> getContactInfo(WebElement element) {
        List<String> info = new ArrayList<>();
        info.add(element
                .findElement(By.cssSelector(tmProjectDetailsPage.contactRole)).getText());
        info.add(element
                .findElement(By.cssSelector(tmProjectDetailsPage.contactFirstName)).getText());
        info.add(element
                .findElement(By.cssSelector(tmProjectDetailsPage.contactMiddleName)).getText());
        info.add(element
                .findElement(By.cssSelector(tmProjectDetailsPage.contactLastName)).getText());
        info.add(element
                .findElement(By.cssSelector(tmProjectDetailsPage.contactPhoneNumber)).getText());
        info.add(element
                .findElement(By.cssSelector(tmProjectDetailsPage.contactEmail)).getText());
        return info;
    }

    public void updateContactInfo(WebElement element, List<String> newInfo) {
        selectDropDown(tmProjectDetailsPage.projectRoleDropdown, newInfo.get(0));
        clearAndFillText(tmProjectDetailsPage.createRoleFirstName, newInfo.get(1));
        clearAndFillText(tmProjectDetailsPage.createRoleMiddleName, newInfo.get(2));
        clearAndFillText(tmProjectDetailsPage.createRoleLastName, newInfo.get(3));
        clearAndFillText(tmProjectDetailsPage.createRolePhone, newInfo.get(4));
        clearAndFillText(tmProjectDetailsPage.createRoleEmail, newInfo.get(5));
    }
}
