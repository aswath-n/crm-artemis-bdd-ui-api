package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.LoginPage;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.tm.TMListOfProjectsPage;
import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.EventBaseClass;
import com.maersk.crm.utilities.TMUtilities;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;

import java.util.HashMap;

public class LoginStepDef extends TMUtilities {
//    private WebDriver driver= Driver.getDriver();

    LoginPage loginPage = new LoginPage();
    TMProjectDetailsPage TMProjectDetailsPage = new TMProjectDetailsPage();
    TMListOfProjectsPage tmListOfProjectsPage = new TMListOfProjectsPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();

    public final ThreadLocal<String> userNamedata = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("login"));
    public final ThreadLocal<String> password = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("password"));
    public final ThreadLocal<String> selectProjectName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> CSRlogin = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("CSRLogin"));
    public final ThreadLocal<String> CallCenterSupervisor_Mars_Tester3_login = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("svcTester3"));
    public final ThreadLocal<String> Superuser_Mars_Tester3_login = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("svcTester3"));
    public final ThreadLocal<String> userNameData1 = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("additionalLogin"));
    public final ThreadLocal<String> userNameData2 = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("login2"));
    public final ThreadLocal<String> userNameData5 = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("login5"));
    public final ThreadLocal<String> userNameData6 = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("login6"));
    public final ThreadLocal<String> userNameData7 = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("login7"));
    public final ThreadLocal<String> userNameData8 = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("login8"));
    public final ThreadLocal<String> userNameData9 = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("login9"));
    public final ThreadLocal<String> userNameData10 = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("login10"));
    public final ThreadLocal<String> svcTester3 = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("svcTester3"));
    public final ThreadLocal<String> svcTester1 = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("svcTester1"));
    public final ThreadLocal<String> svcTester2 = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("svcTester2"));
    public final ThreadLocal<String> svcTester6 = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("svcTester6"));
    public final ThreadLocal<String> svcTester5 = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("svcTester5"));
    public final ThreadLocal<String> svcTester7 = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("svcTester7"));
    public final ThreadLocal<String> svcTester4 = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("svcTester4"));
    public static final ThreadLocal<String> projectName1 = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> loginUserName = ThreadLocal.withInitial(String::new);


    public final ThreadLocal<String> invalidUserName = ThreadLocal.withInitial(() -> getCellValueBySheetRowAndColoumn("Login", "InValidData", "UserName"));
    public final ThreadLocal<String> invalidPassword = ThreadLocal.withInitial(() -> getCellValueBySheetRowAndColoumn("Login", "InValidData", "password"));

    public final ThreadLocal<String> projectName = ThreadLocal.withInitial(() -> getCellValueBySheetRowAndColoumn("Search", "Valid ", "Project"));
    public final ThreadLocal<String> programName = ThreadLocal.withInitial(() -> getCellValueBySheetRowAndColoumn("Search", "Valid", "Program"));

    @Given("I  navigate to the Tenant Manager Url")
    public void i_navigate_to_the_tenant_manager_url() {
        Driver.getDriver().get(ConfigurationReader.getProperty("tmPageURL"));
    }

    @When("I  navigate it should redirect to the login Page")
    public void i_navigate_it_should_redirect_to_the_login_page() {
        String actualTitle = "https://mars-tenant-manager-webapp-dev.cfapps.maxng-dev.maersk.com/login";
        // Assert.assertEquals(Driver.getDriver().getTitle(),actualTitle);
    }

    @Then("I should see the login fields present in the login Page")
    public void i_should_see_the_login_fields_present_in_the_login_page() {
        Assert.assertTrue(loginPage.userName.isDisplayed());
        Assert.assertTrue(loginPage.password.isDisplayed());
        Assert.assertTrue(loginPage.tmLoginButton.isDisplayed());
        //login(userNamedata.get(),password);

    }

    @When("I navigate it should redirect to the login Page and provide valid login credentials")
    public void i_navigate_it_should_redirect_to_the_login_page_and_provide_valid_data() {
        //   i_navigate_it_should_redirect_to_the_login_page();
        tmLogin(userNamedata.get(), password.get());
    }

    @Then("the system should allow me to see the Home page")
    public void the_system_should_allow_me_to_see_the_home_page() {
        highLightElement(tmListOfProjectsPage.projectList);
    }

    @When("I  navigate it should redirect to the login Page  and I provide invalid data in the login page")
    public void i_navigate_it_should_redirect_to_the_login_page_and_i_provide_invalid_data_in_the_login_page() {
        login(invalidUserName.get(), invalidPassword.get());

    }

    @Then("the system should throw an error message of invalid username and invalid password")
    public void the_system_should_throw_an_error_message_of_invalid_username_and_invalid_password() {
        Assert.assertTrue(loginPage.invalidUserNameError.isDisplayed());
        highLightElement(loginPage.invalidUserNameError);

    }

    @Then("the system should allow me to see the Home page and search for the project")
    public void the_system_should_allow_me_to_see_the_Home_page_and_search_for_the_project() {
        highLightElement(tmListOfProjectsPage.projectList);
        search(projectName.get(), programName.get());
    }

    @Given("I logged into CRM and select a project {string}")
    public void i_logged_into_CRM_choosing_project(String projectName) {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        switch (projectName) {
            case "BLCRM":
                selectProjectName.set(ConfigurationReader.getProperty("projectName"));
                break;
            case "NJ-SBE":
                selectProjectName.set(ConfigurationReader.getProperty("njProjectName"));
                break;
            case "GFCRM":
                selectProjectName.set(ConfigurationReader.getProperty("gaProjectName"));
                userNamedata.set(ConfigurationReader.getProperty("login2"));
                break;
            case "COVER-VA":
                selectProjectName.set(ConfigurationReader.getProperty("coverVAProjectName"));
                userNamedata.set(ConfigurationReader.getProperty("svcTester3"));
                break;
            case "COVER-VA2":
                selectProjectName.set(ConfigurationReader.getProperty("coverVAProjectName"));
                userNamedata.set(ConfigurationReader.getProperty("svcTester2"));
                break;
            case "IN-EB":
                selectProjectName.set(ConfigurationReader.getProperty("INEBProjectName"));
                break;
            case "DC-EB":
                selectProjectName.set(ConfigurationReader.getProperty("DCEBProjectName"));
                break;
            default:
                selectProjectName.set(ConfigurationReader.getProperty("projectName"));
                break;
        }
        loginCRM(userNamedata.get(), password.get(), selectProjectName.get());
    }

    @Given("I logged into CRM with CSR account and select a project {string}")
    public void iLoggedIntoCRMWithCSRAccountAndSelectAProject(String projectName) {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        switch (projectName) {
            case "BLCRM":
                selectProjectName.set(ConfigurationReader.getProperty("projectName"));
                break;
            case "NJ-SBE":
                selectProjectName.set(ConfigurationReader.getProperty("njProjectName"));
                break;
            case "GFCRM":
                selectProjectName.set(ConfigurationReader.getProperty("gaProjectName"));
                break;
            default:
                selectProjectName.set(ConfigurationReader.getProperty("projectName"));
                break;
        }
        loginCRM(CSRlogin.get(), password.get(), selectProjectName.get());
    }

    @Given("I logged into CRM with Call Center Supervisor account and select a project {string}")
    public void i_logged_into_CRM_with_Call_Center_Supervisor_account_and_select_a_project(String projectName) {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        switch (projectName) {
            case "BLCRM":
                selectProjectName.set(ConfigurationReader.getProperty("projectName"));
                break;
            case "NJ-SBE":
                selectProjectName.set(ConfigurationReader.getProperty("njProjectName"));
                break;
            case "GFCRM":
                selectProjectName.set(ConfigurationReader.getProperty("gaProjectName"));
                break;
            case "COVER-VA":
                selectProjectName.set(ConfigurationReader.getProperty("coverVAProjectName"));
                break;
            default:
                selectProjectName.set(ConfigurationReader.getProperty("projectName"));
                break;
        }
        loginCRM(CallCenterSupervisor_Mars_Tester3_login.get(), password.get(), selectProjectName.get());
    }

    @Given("I logged into CRM with Superuser account and select a project {string}")
    public void i_logged_into_CRM_with_Superuser_account_and_select_a_project(String projectName) {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        switch (projectName) {
            case "BLCRM":
                selectProjectName.set(ConfigurationReader.getProperty("projectName"));
                break;
            case "NJ-SBE":
                selectProjectName.set(ConfigurationReader.getProperty("njProjectName"));
                break;
            case "GFCRM":
                selectProjectName.set(ConfigurationReader.getProperty("gaProjectName"));
                break;
            case "COVER-VA":
                selectProjectName.set(ConfigurationReader.getProperty("coverVAProjectName"));
                break;
            case "IN-EB":
                selectProjectName.set(ConfigurationReader.getProperty("INEBProjectName"));
                break;
            default:
                selectProjectName.set(ConfigurationReader.getProperty("projectName"));
                break;
        }
        loginCRM(Superuser_Mars_Tester3_login.get(), password.get(), selectProjectName.get());
    }


    @Given("I logged into CRM with {string} and select a project {string}")
    public void iLoggedIntoCPForSpecificProjectWithSpecificRole(String user, String projectName) {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        synchronized (CRM_GeneralTaskStepDef.taskValues) {
            CRM_GeneralTaskStepDef.taskValues.set(new HashMap<>());
        }
        synchronized (CRM_EditTaskStepDef.editHistory) {
            CRM_EditTaskStepDef.editHistory.set(new HashMap<>());
        }
        CRM_TaskManagementStepDef.taskId.set("");
        CRM_WorkQueueStepDef.position.set(0);
        EventBaseClass.correlationId = "";
        projectName1.set(projectName);
        sa.set(new SoftAssertions());
        switch (projectName) {
            case "BLCRM":
                selectProjectName.set(ConfigurationReader.getProperty("projectName"));
                break;
            case "FHK":
                selectProjectName.set(ConfigurationReader.getProperty("FHKProjectName"));
                break;
            case "NJ-SBE":
                selectProjectName.set(ConfigurationReader.getProperty("njProjectName"));
                break;
            case "GFCRM":
                selectProjectName.set(ConfigurationReader.getProperty("gaProjectName"));
                break;
            case "CoverVA":
                selectProjectName.set(ConfigurationReader.getProperty("coverVAProjectName"));
                break;
            case "IN-EB":
                selectProjectName.set(ConfigurationReader.getProperty("INEBProjectName"));
                break;
            case "BLATS2":
                selectProjectName.set(ConfigurationReader.getProperty("BLATS2ProjectName"));
                break;
            case "DC-EB":
                selectProjectName.set(ConfigurationReader.getProperty("DCEBProjectName"));
                break;
            default:
                selectProjectName.set(ConfigurationReader.getProperty("projectName"));
                break;
        }
        switch (user) {
            case "Service Account 1":
                loginUserName.set(userNamedata.get());
                loginCRM(userNamedata.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 2":
                loginUserName.set(userNameData2.get());
                loginCRM(userNameData2.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 5":
                loginUserName.set(userNameData5.get());
                loginCRM(userNameData5.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 6":
                loginUserName.set(userNameData6.get());
                loginCRM(userNameData6.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 7":
                loginUserName.set(userNameData7.get());
                loginCRM(userNameData7.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 8":
                loginUserName.set(userNameData8.get());
                loginCRM(userNameData8.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 9":
                loginUserName.set(userNameData9.get());
                loginCRM(userNameData9.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 10":
                loginUserName.set(userNameData10.get());
                loginCRM(userNameData10.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 3":
                loginUserName.set(svcTester3.get());
                loginCRM(svcTester3.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 1":
                loginUserName.set(svcTester1.get());
                loginCRM(svcTester1.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 2":
                loginUserName.set(svcTester2.get());
                loginCRM(svcTester2.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 6":
                loginUserName.set(svcTester6.get());
                loginCRM(svcTester6.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 5":
                loginUserName.set(svcTester5.get());
                loginCRM(svcTester5.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 7":
                loginUserName.set(svcTester7.get());
                loginCRM(svcTester7.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 4":
                loginUserName.set(svcTester4.get());
                loginCRM(svcTester4.get(), password.get(), selectProjectName.get());
                break;
            default:
                System.out.println("Default user");
                loginUserName.set(userNamedata.get());
                loginCRM(userNamedata.get(), password.get(), selectProjectName.get());

        }
        waitFor(3);
        waitForVisibility(contactRecord.initContact, 2);
    }


    @Then("I logged into CRM after logged out with {string} and select a project {string}")
    public void iLoggedIntoCRMAfterLoggedOutWithAndSelectAProject(String user, String projectName) {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        synchronized (CRM_GeneralTaskStepDef.taskValues) {
            CRM_GeneralTaskStepDef.taskValues.set(new HashMap<>());
        }
        CRM_WorkQueueStepDef.position.set(0);
        projectName1.set(projectName);
        switch (projectName) {
            case "BLCRM":
                selectProjectName.set(ConfigurationReader.getProperty("projectName"));
                break;
            case "NJ-SBE":
                selectProjectName.set(ConfigurationReader.getProperty("njProjectName"));
                break;
            case "GFCRM":
                selectProjectName.set(ConfigurationReader.getProperty("gaProjectName"));
                break;
            case "CoverVA":
                selectProjectName.set(ConfigurationReader.getProperty("coverVAProjectName"));
                break;
            case "IN-EB":
                selectProjectName.set(ConfigurationReader.getProperty("INEBProjectName"));
                break;
            default:
                selectProjectName.set(ConfigurationReader.getProperty("projectName"));
                break;
        }
        switch (user) {
            case "Service Account 1":
                loginUserName.set(userNamedata.get());
                loginCRMafterLoggOut(userNamedata.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 2":
                loginUserName.set(userNameData2.get());
                loginCRMafterLoggOut(userNameData2.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 5":
                loginUserName.set(userNameData5.get());
                loginCRMafterLoggOut(userNameData5.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 6":
                loginUserName.set(userNameData6.get());
                loginCRMafterLoggOut(userNameData6.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 7":
                loginUserName.set(userNameData7.get());
                loginCRMafterLoggOut(userNameData7.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 8":
                loginUserName.set(userNameData8.get());
                loginCRMafterLoggOut(userNameData8.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 9":
                loginUserName.set(userNameData9.get());
                loginCRMafterLoggOut(userNameData9.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 10":
                loginUserName.set(userNameData10.get());
                loginCRMafterLoggOut(userNameData10.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 3":
                loginUserName.set(svcTester3.get());
                loginCRMafterLoggOut(svcTester3.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 1":
                loginUserName.set(svcTester1.get());
                loginCRMafterLoggOut(svcTester1.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 2":
                loginUserName.set(svcTester2.get());
                loginCRMafterLoggOut(svcTester2.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 6":
                loginUserName.set(svcTester6.get());
                loginCRMafterLoggOut(svcTester6.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 5":
                loginUserName.set(svcTester5.get());
                loginCRMafterLoggOut(svcTester5.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 7":
                loginUserName.set(svcTester7.get());
                loginCRMafterLoggOut(svcTester7.get(), password.get(), selectProjectName.get());
                break;
            default:
                System.out.println("Default user");
                loginUserName.set(userNamedata.get());
                loginCRM(userNamedata.get(), password.get(), selectProjectName.get());

        }
        waitFor(3);
        waitForVisibility(contactRecord.initContact, 2);
    }

    @Then("I logged into CRM with specific role {string} and select a project {string} and select a role {string}")
    public void iLoggedIntoCRMChoosingProjectAndRole(String user, String projectName, String role) {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        synchronized (CRM_GeneralTaskStepDef.taskValues) {
            CRM_GeneralTaskStepDef.taskValues.set(new HashMap<>());
        }
        CRM_WorkQueueStepDef.position.set(0);
        projectName1.set(projectName);
        sa.set(new SoftAssertions());
        switch (projectName) {
            case "BLCRM":
                selectProjectName.set(ConfigurationReader.getProperty("projectName"));
                break;
            case "NJ-SBE":
                selectProjectName.set(ConfigurationReader.getProperty("njProjectName"));
                break;
            case "GFCRM":
                selectProjectName.set(ConfigurationReader.getProperty("gaProjectName"));
                break;
            case "CoverVA":
                selectProjectName.set(ConfigurationReader.getProperty("coverVAProjectName"));
                break;
            case "IN-EB":
                selectProjectName.set(ConfigurationReader.getProperty("INEBProjectName"));
                break;
            case "BLATS2":
                selectProjectName.set(ConfigurationReader.getProperty("BLATS2ProjectName"));
            case "DC-EB":
                selectProjectName.set(ConfigurationReader.getProperty("DCEBProjectName"));
                break;
            default:
                selectProjectName.set(ConfigurationReader.getProperty("projectName"));
                break;
        }
        switch (user) {
            case "Service Account 1":
                loginUserName.set(userNamedata.get());
                loginCRMafter(userNamedata.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 2":
                loginUserName.set(userNameData2.get());
                loginCRMafter(userNameData2.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 5":
                loginUserName.set(userNameData5.get());
                loginCRMafter(userNameData5.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 6":
                loginUserName.set(userNameData6.get());
                loginCRMafter(userNameData6.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 7":
                loginUserName.set(userNameData7.get());
                loginCRMafter(userNameData7.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 8":
                loginUserName.set(userNameData8.get());
                loginCRMafter(userNameData8.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 9":
                loginUserName.set(userNameData9.get());
                loginCRMafter(userNameData9.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 10":
                loginUserName.set(userNameData10.get());
                loginCRMafter(userNameData10.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 3":
                loginUserName.set(svcTester3.get());
                loginCRMafter(svcTester3.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 1":
                loginUserName.set(svcTester1.get());
                loginCRMafter(svcTester1.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 2":
                loginUserName.set(svcTester2.get());
                loginCRMafter(svcTester2.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 6":
                loginUserName.set(svcTester6.get());
                loginCRMafter(svcTester6.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 5":
                loginUserName.set(svcTester5.get());
                loginCRMafter(svcTester5.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 7":
                loginUserName.set(svcTester7.get());
                loginCRMafter(svcTester7.get(), password.get(), selectProjectName.get());
                break;
            default:
                System.out.println("Default user");
                loginUserName.set(userNamedata.get());
                loginCRMafter(userNamedata.get(), password.get(), selectProjectName.get());
                break;
        }
        switch (role) {
            case "Mailroom Supervisor":
                loginCRMWithRoleSelction(role);
                break;
            case "Mailroom Specialist":
                loginCRMWithRoleSelction(role);
                break;
            case "Supervisor":
                loginCRMWithRoleSelction(role);
                break;
            case "Research Unit Csr":
                loginCRMWithRoleSelction(role);
                break;
            case "Csr":
                loginCRMWithRoleSelction(role);
                break;
            case "Superuser":
                loginCRMWithRoleSelction(role);
            case "Outreach Specialist":
                loginCRMWithRoleSelction(role);
                break;
            default:
                loginCRMWithRoleSelction(role);
                break;
        }
        waitFor(3);
        waitForVisibility(contactRecord.initContact, 2);
    }

    @Then("I logged into CRM after logged out with {string} and select a project {string} and select a role {string}")
    public void iLoggedIntoCRMAfterLoggedOutWithAndSelectAProjectAndProject(String user, String projectName, String role) {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        synchronized (CRM_GeneralTaskStepDef.taskValues) {
            CRM_GeneralTaskStepDef.taskValues.set(new HashMap<>());
        }
        CRM_WorkQueueStepDef.position.set(0);
        projectName1.set(projectName);
        switch (projectName) {
            case "BLCRM":
                selectProjectName.set(ConfigurationReader.getProperty("projectName"));
                break;
            case "NJ-SBE":
                selectProjectName.set(ConfigurationReader.getProperty("njProjectName"));
                break;
            case "GFCRM":
                selectProjectName.set(ConfigurationReader.getProperty("gaProjectName"));
                break;
            case "CoverVA":
                selectProjectName.set(ConfigurationReader.getProperty("coverVAProjectName"));
                break;
            case "IN-EB":
                selectProjectName.set(ConfigurationReader.getProperty("INEBProjectName"));
                break;
            default:
                selectProjectName.set(ConfigurationReader.getProperty("projectName"));
                break;
        }
        switch (user) {
            case "Service Account 1":
                loginUserName.set(userNamedata.get());
                loginCRMafterLoggOutonCP(userNamedata.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 2":
                loginUserName.set(userNameData2.get());
                loginCRMafterLoggOutonCP(userNameData2.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 5":
                loginUserName.set(userNameData5.get());
                loginCRMafterLoggOutonCP(userNameData5.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 6":
                loginUserName.set(userNameData6.get());
                loginCRMafterLoggOutonCP(userNameData6.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 7":
                loginUserName.set(userNameData7.get());
                loginCRMafterLoggOutonCP(userNameData7.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 8":
                loginUserName.set(userNameData8.get());
                loginCRMafterLoggOutonCP(userNameData8.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 9":
                loginUserName.set(userNameData9.get());
                loginCRMafterLoggOutonCP(userNameData9.get(), password.get(), selectProjectName.get());
                break;
            case "Service Account 10":
                loginUserName.set(userNameData10.get());
                loginCRMafterLoggOutonCP(userNameData10.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 3":
                loginUserName.set(svcTester3.get());
                loginCRMafterLoggOutonCP(svcTester3.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 1":
                loginUserName.set(svcTester1.get());
                loginCRMafterLoggOutonCP(svcTester1.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 2":
                loginUserName.set(svcTester2.get());
                loginCRMafterLoggOutonCP(svcTester2.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 6":
                loginUserName.set(svcTester6.get());
                loginCRMafterLoggOutonCP(svcTester6.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 5":
                loginUserName.set(svcTester5.get());
                loginCRMafterLoggOutonCP(svcTester5.get(), password.get(), selectProjectName.get());
                break;
            case "Service Tester 7":
                loginUserName.set(svcTester7.get());
                loginCRMafterLoggOutonCP(svcTester7.get(), password.get(), selectProjectName.get());
                break;
            default:
                System.out.println("Default user");
                loginUserName.set(userNamedata.get());
                loginCRMafterLoggOutonCP(userNamedata.get(), password.get(), selectProjectName.get());
        }
        switch (role) {
            case "Mailroom Supervisor":
                loginCRMWithRoleSelction(role);
                break;
            case "Mailroom Specialist":
                loginCRMWithRoleSelction(role);
                break;
            case "Supervisor":
                loginCRMWithRoleSelction(role);
                break;
            case "Research Unit Csr":
                loginCRMWithRoleSelction(role);
                break;
            case "Csr":
                loginCRMWithRoleSelction(role);
                break;
            case "Superuser":
                loginCRMWithRoleSelction(role);
                break;
            default:
                loginCRMWithRoleSelction(role);
                break;
        }
        waitFor(3);
        waitForVisibility(contactRecord.initContact, 2);
    }

    @Then("Close the soft assertions")
    public void closeSoftassert2() {
        sa.get().assertAll();
    }

    @When("I initiate soft assertion")
    public void initiateSoftAssertion() {
        sa.set(new SoftAssertions());
    }



    @Given("I logged into application")
    public void test1() {


        loginCRM1();
        System.out.println("COmpleted");


    }
}
