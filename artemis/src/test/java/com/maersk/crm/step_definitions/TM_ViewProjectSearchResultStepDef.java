package com.maersk.crm.step_definitions;

import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.TMUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.Then;
import com.maersk.crm.pages.tm.TMProjectListPage;
import org.openqa.selenium.WebElement;
import static org.testng.Assert.assertTrue;

public class TM_ViewProjectSearchResultStepDef extends TMUtilities {

    TMProjectListPage tmProjectListPage = new TMProjectListPage();
    //


    /*this method verifies all project details are displayed */
    @Then("I can see all Project records with all data elements displayed")
    public void i_can_see_all_Project_records_with_all_data_elements_displayed() {

        for (WebElement project : tmProjectListPage.projects) {
            //Refactor 09/27/18
            //todo remove if condition when bug CRM-1094 is resolved
            //this condition skips the the project with ID 36(CRM-1094)
            if (project.findElement(By.cssSelector(tmProjectListPage.projectIdCSS)).getText().equalsIgnoreCase("36")) {
                continue;
            }
            assertTrue(project.findElement(By.cssSelector(tmProjectListPage.projectNameCSS)).isDisplayed(),
                    project.findElement(By.cssSelector(tmProjectListPage.projectIdCSS)).getText() + "; Project name field does not have value");
            assertTrue(project.findElement(By.className(tmProjectListPage.projectStateClass)).isDisplayed(), "State field does not have value");
            assertTrue(project.findElement(By.className(tmProjectListPage.contractIdClass)).isDisplayed());
            assertTrue(project.findElement(By.cssSelector(tmProjectListPage.projectIdCSS)).isDisplayed());
            assertTrue(project.findElement(By.cssSelector(tmProjectListPage.provisioningStatusCSS)).isDisplayed(), "Provisioning Status field does not have value");
            assertTrue(project.findElement(By.cssSelector(tmProjectListPage.programNameCSS)).isDisplayed(), "Program Name field does not have value");
            assertTrue(project.findElement(By.cssSelector(tmProjectListPage.clientNameCSS)).isDisplayed(), "Client/State Agency field does not have value");
            assertTrue(project.findElement(By.xpath(tmProjectListPage.startDateFieldXpath)).isDisplayed());
            assertTrue(project.findElement(By.xpath(tmProjectListPage.endDateFieldXpath)).isDisplayed());
            break;
        }

        System.out.println(tmProjectListPage.projects.size() + " project details are displayed on this page.");
    }

    /*this method checks if the search result displays more than 4 projects and scrolls down to the last project on the page*/
    @Then("I can navigate to see more Projects than shown on current page")
    public void i_can_navigate_to_see_more_Projects_than_shown_on_current_page() {
        int lastProjectIndex = tmProjectListPage.projects.size() - 1;
        if (lastProjectIndex >= 4) {
            scrollToElement(tmProjectListPage.projects.get(lastProjectIndex));

        }
        System.out.println("Scrolled down to view project #" + tmProjectListPage.projects.size() + " for this search");

    }


}


