@planEditContact

Feature: Edit plan - Contact Details Tab via Tenant Manager UI

  @CP-1970-01 @CP-1970 @sean @tm-regression @ui-pp
  Scenario: Edit Buttons Viewable on Plan Detail Tab
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on Plans Configuration tab
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    And I am brought to the Plan Details Screen
    Then I will see an edit button within each of the Plan Details containers

  @CP-1970-02 @CP-1970 @sean @tm-regression @tm-smoke @ui-pp
  Scenario: User Restricted to Edit Field in only one Container at a time
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on Plans Configuration tab
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    And I am brought to the Plan Details Screen
    Then I will see an edit button within each of the Plan Details containers
    And I can edit fields in only one container at a time

  @CP-1970-03 @CP-1970 @sean @tm-regression @ui-pp
  Scenario: Updating fields in the Plan Mailing Address Container
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on Plans Configuration tab
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    And I am brought to the Plan Details Screen
    Given that I have navigated to the Contact Details Tab
    When I click on the Edit button next to the Plan Mailing Address Container
    Then I will be able to update the following fields:

  @CP-1970-04 @CP-1970 @sean @tm-regression @ui-pp
  Scenario: Updating fields in the Contact Information Container
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on Plans Configuration tab
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    And I am brought to the Plan Details Screen
    Given that I have navigated to the Contact Details Tab
    When I click on the Edit button next to the Contact Information Container
    Then I will be able to update the given fields

  @CP-1970-05 @CP-1970 @sean @tm-regression @ui-pp
  Scenario: Save & Success Message
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on Plans Configuration tab
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    And I am brought to the Plan Details Screen
    Given that I have navigated to the Contact Details Tab
    When I click on the Edit button next to the Contact Information Container
    Then I will add contact information
    And I will Save the information
    And I am returned to the Contact Detail read-only screen

  @CP-1970-06 @CP-1970 @sean @tm-regression @ui-pp
  Scenario: Save and Success Message - Plan Information
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
#    When I will upload the region and plan file thru API
    And I click on Plans Configuration tab
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    And I am brought to the Plan Details Screen
    When I click on the Plan Information container edit button
    And I input and save changes to the given Plan Information fields
    Then I see the Plan Information changes were saved
    And I am returned to the Plan Detail read-only screen

  @CP-1970-07 @CP-1970 @sean @tm-regression @ui-pp
  Scenario: Cancel & Warning Message -Cancel warining message
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on Plans Configuration tab
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    When I click on the Plan Information container edit button
    And I input and cancel changes to the given Plan Information fields

  @CP-1970-08 @CP-1970 @sean @tm-regression @ui-pp
  Scenario: Cancel & Warning Message -Cancel continue warning message
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on Plans Configuration tab
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    When I click on the Plan Information container edit button
    And I input and cancel Continue changes to the given Plan Information fields

  @CP-1970-09 @CP-1970 @sean @tm-regression @ui-pp
  Scenario: Back Arrow & Warning Message -Cancel button
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on Plans Configuration tab
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    When I click on the Plan Information container edit button
    And I input and press back arrow Cancel changes to the given Plan Information fields

  @CP-1970-10 @CP-1970 @sean @ui-pp
  Scenario: Back Arrow & Warning Message -Continue button
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on Plans Configuration tab
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    When I click on the Plan Information container edit button
    And I input and press back arrow Continue changes to the given Plan Information fields
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    Then I verify exclusion checkbox updated


  @CP-1970-11 @CP-1970 @sean @tm-regression @ui-pp
  Scenario: Navigate away & Warning Message -Cancel button
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on Plans Configuration tab
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    When I click on the Plan Information container edit button
    And I input and press Navigation Link from the given Plan Information fields

  @CP-1970-12 @CP-1970 @sean @tm-regression @ui-pp
  Scenario: Navigate away & Warning Message -Continue button
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on Plans Configuration tab
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    When I click on the Plan Information container edit button
    And I input and navigate to Continue changes to the given Plan Information fields