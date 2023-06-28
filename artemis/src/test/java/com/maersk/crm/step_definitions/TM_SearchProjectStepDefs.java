package com.maersk.crm.step_definitions;

import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.TMUtilities;
import com.maersk.crm.utilities.ConfigurationReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.maersk.crm.pages.tm.TMProjectListPage;
import com.maersk.crm.pages.tm.TMSearchProjectPage;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class TM_SearchProjectStepDefs extends TMUtilities {

//    private final ThreadLocal<String> errorMessage;

    private TMProjectListPage tmProjectListPage = new TMProjectListPage();
    private TMSearchProjectPage tmSearchProjectPage = new TMSearchProjectPage();
    public static final ThreadLocal<String> projectname = ThreadLocal.withInitial(String::new);
//    TMViewProjectPage tmViewProjectPage = new TMViewProjectPage();


    /*this method distinguishes which search field should be used*/
    @When("I search for a project by {string} value {string}")
    public void i_search_for_a_project_by_value(String field, String value) {
//        waitForVisibility(tmProjectListPage.searchByState, 10);
        //--- To select a project from config file value must be "SelectFromConfig" ---
        waitFor(5);
        switch (value) {
            case "SelectFromConfig":
                value = ConfigurationReader.getProperty("tmRegressionProjectName").split(" ")[1];
                System.out.println("Project Name=" + value);
                projectname.set(value);
                break;

            case "SelectGAProject":
                value = ConfigurationReader.getProperty("gaProjectName").split(" ")[1];
                System.out.println("Project Name=" + value);
                projectname.set(value);
                break;
            case "SelectBLCRMConfig":
                value = ConfigurationReader.getProperty("projectName").split(" ")[1];
                System.out.println("Project Name=" + value);
                projectname.set(value);
                break;
            case "SelectNJSBEConfig":
                value = ConfigurationReader.getProperty("njProjectName").split(" ")[1];
                System.out.println("Project Name=" + value);
                projectname.set(value);
                break;
            case "SelectCOVERVAConfig":
                value = ConfigurationReader.getProperty("coverVAProjectName").split(" ")[1];
                System.out.println("Project Name=" + value);
                projectname.set(value);
                break;
            case "SelectINEBConfig":
                value = ConfigurationReader.getProperty("INEBProjectName").split(" ")[1];
                System.out.println("Project Name=" + value);
                projectname.set(value);
                break;
            case "TESTREGRESSION2":
                value = ConfigurationReader.getProperty("TESTREGRESSION2Name").split(" ")[1];
                System.out.println("Project Name=" + value);
                projectname.set(value);
                break;
            case "SelectRegressionBaseline":
                value = ConfigurationReader.getProperty("tmRegressionProjectName").split(" ")[1];
                System.out.println("Project Name=" + value);
                projectname.set(value);
                break;
        }
        // this explicit wait allows the project details to be loaded
        waitForVisibility(tmProjectListPage.projects.get(0), 4000);
        switch (field) {
            case "state":
                tmProjectListPage.searchByState.sendKeys(value);
                waitFor(1);
                break;
            case "project":
                tmProjectListPage.searchProject.sendKeys(value);
                waitFor(1);
                break;
            case "program":
                tmProjectListPage.searchByProgram.sendKeys(value);
                waitFor(1);
                break;
            case "client":
                tmProjectListPage.searchByClientStateAgency.sendKeys(value);
                waitFor(1);
                break;

        }
        waitFor(3);
        tmProjectListPage.search.click();
        waitFor(2);

        // System.out.println("Search button clicked");
    }


    /*this method asserts the error message*/
    @Then("I should see {string} message")
    public void i_should_see_message(String error) {
        System.out.println(error);
        assertTrue(error.contains(tmSearchProjectPage.errorMessage.getText()));

    }

    /*this method should display all projects according to the search criteria*/
    //Refactoring 09/27/18
    @Then("I should see all projects with {string} value {string}")
    public void i_should_see_all_projects_with_value(String field, String value) {
        int i = 0;
        for (i = 0; i <= tmProjectListPage.projects.size() - 1; i++) {
            // this explicit wait allows the project details to be loaded
            waitForVisibility(tmProjectListPage.projects.get(i), 3000);
            waitFor(2);
            switch (field) {
                case "state":
                    assertTrue(value.equalsIgnoreCase((tmProjectListPage.projects.get(i).
                            findElement(By.className(tmProjectListPage.projectStateClass)).getText().substring(0, value.length()))));
                    break;
                case "project":
                    assertTrue(value.equalsIgnoreCase((tmProjectListPage.projects.get(i).
                            findElement(By.cssSelector(tmProjectListPage.projectNameCSS)).getText().substring(0, value.length()))));
                    break;
                case "program":
                    assertTrue(value.equalsIgnoreCase((tmProjectListPage.projects.get(i).
                            findElement(By.cssSelector(tmProjectListPage.programNameCSS)).getText().substring(0, value.length()))));
                    break;
                case "client":
                    assertTrue(value.equalsIgnoreCase((tmProjectListPage.projects.get(i).
                            findElement(By.cssSelector(tmProjectListPage.clientNameCSS)).getText().substring(0, value.length()))));
                    break;
            }
        }/*printing the number of found projects*/
        System.out.println(i + " Projects found for " + field + " " + value);
    }

    /*this method pulls each project information and checks if the project information is displayed according to the wildcard search*/
    @Then("I should see the projects according to {string} {string} wild card")
    public void i_should_see_the_projects_according_to_wild_card(String field, String value) {
        int i = 0;
        waitFor(3);
        for (i = 0; i <= tmProjectListPage.projects.size() - 1; i++) {
            switch (field) {
                case "state":
                    assertTrue(value.equalsIgnoreCase((tmProjectListPage.projects.get(i).
                                    findElement(By.className(tmProjectListPage.projectStateClass)).getText().substring(0, value.length()))),
                            "Failed on wildcard search by State" + i);
                    break;
                case "project":
                    assertTrue(value.equalsIgnoreCase((tmProjectListPage.projects.get(i).
                                    findElement(By.cssSelector(tmProjectListPage.projectNameCSS)).getText().substring(0, value.length()))),
                            "Failed on wildcard search by Project name" + i);
                    break;
                case "program":
                    assertTrue(value.equalsIgnoreCase((tmProjectListPage.projects.get(i).
                                    findElement(By.cssSelector(tmProjectListPage.programNameCSS)).getText().substring(0, value.length()))),
                            "Failed on wildcard searching by Program name" + i);
                    break;
                case "client":
                    assertTrue(value.equalsIgnoreCase((tmProjectListPage.projects.get(i).
                                    findElement(By.cssSelector(tmProjectListPage.clientNameCSS)).getText().substring(0, value.length()))),
                            "Failed on wildcard searching by Client/State Agemcy" + i);
                    break;
            }

        }
        System.out.println(i + " Projects found for " + field + " " + value);
    }

    /*this method searches for a project with no value in fields*/
    @When("I search for a project with empty search fields")
    public void i_search_for_a_project_with_empty_search_fields() {
        tmProjectListPage.search.click();
    }


    /*this method invokes the dropdown option to autocomplete search
     */
    @Then("I should see potential {string} with {string} autocomplete")
    public void i_should_see_potential_with_autocomplete(String field, String value) {
        List<WebElement> potentialValues = new ArrayList<>();
        switch (field) {
            case "state":
                potentialValues = tmProjectListPage.searchByState.findElements(By.xpath(tmProjectListPage.searchDropDownList));
                break;
            case "project":
                potentialValues = tmProjectListPage.searchProject.findElements(By.xpath(tmProjectListPage.searchDropDownList));
                break;
            case "program":
                potentialValues = tmProjectListPage.searchByProgram.findElements(By.xpath(tmProjectListPage.searchDropDownList));
                break;
            case "client":
                potentialValues = tmProjectListPage.searchByClientStateAgency.findElements(By.xpath(tmProjectListPage.searchDropDownList));
                break;

        }
        /* collects the dropdown options in a List and checks the options being according to search criteria */
        if (potentialValues.size() > 0) {
            for (WebElement el : potentialValues) {
                System.out.println(el.getText());
                assertTrue(value.equalsIgnoreCase(el.getText().substring(0, value.length())),
                        "Failed on autocomplete searching by " + field + " and " + value);
            }
        } else {
            System.out.println(tmSearchProjectPage.errorMessage.getText());
        }
    }

    /*this method verifies no project record is displayed */
    @Then("I should see no projects displayed")
    public void i_should_see_no_projects_displayed() {
        assertTrue(tmProjectListPage.projects.isEmpty());
    }

}


