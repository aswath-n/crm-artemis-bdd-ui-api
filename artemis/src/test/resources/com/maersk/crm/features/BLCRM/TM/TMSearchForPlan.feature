@Re_Upload_File_Plan_File
Feature: Search for a Plan via Tenant Manager UI


  @CP-803 @CP-803-01 @planProvider @aswath @tm-regression @ui-pp
  Scenario: Navigating to the Plan configuration screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Project Configuration
    And I select Plans Management
    When I click on Plans Configuration tab
    Then I am navigated to the Plans Configuration screen


  @CP-803 @CP-803-02 @planProvider @aswath @tm-regression @ui-pp
  Scenario: Basic Search Parameters
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
   # Given I get the jwt token TM
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
   # When I upload the Service Region file
    When I upload the service region file before to plan file
    Then I click on Plans Configuration tab
    And I upload the Plan Configuration success file
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I will see the Plan Configuration upload success message
    Then I can search for Plans using any one of the fields



  @CP-803 @CP-803-03 @planProvider @aswath @tm-smoke @tm-regression @ui-pp
  Scenario: Basic Search Parameters - Multiple Parameters
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
    When I upload the service region file before to plan file
    Then I click on Plans Configuration tab
    And I upload the Plan Configuration success file
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I will see the Plan Configuration upload success message
    Then I can search for Plans using any two of the fields


  @CP-803 @CP-803-04 @planProvider @aswath @tm-regression @ui-pp
  Scenario: Searching for a State-Wide Service Area (x2)
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
    When I upload the service region file before to plan file
    Then I click on Plans Configuration tab
    And I upload the Plan Configuration success file
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I will see the Plan Configuration upload success message
    Then I can search for Plans using any two of the fields


  @CP-803 @CP-803-05 @planProvider @aswath @tm-regression @ui-pp
  Scenario: Select Cancel
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Project Configuration
    And I select Plans Management
    When I click on Plans Configuration tab
    Then I am navigated to the Plans Configuration screen
    When I enter search parameters into the fields
    And click cancel button
    Then entered parameters will be cleared
    And I will remain on the Plans Configuration screen


  @CP-803 @CP-803-06 @planProvider @aswath @tm-regression @ui-pp
  Scenario: Minimum of One Search Parameter Required
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
    When I upload the service region file before to plan file
    Then I click on Plans Configuration tab
    And I upload the Plan Configuration success file
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I will see the Plan Configuration upload success message
    And I make a search without populating any parameters
    Then I will see appropriate Error Message


  @CP-803 @CP-803-07 @planProvider @aswath @tm-regression @ui-pp
  Scenario: View Search Results
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
    When I upload the service region file before to plan file
    Then I click on Plans Configuration tab
    And I upload the Plan Configuration success file
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I will see the Plan Configuration upload success message
    Then I can make a valid search of plans
    And I can see the Plan Name row header on the search results table


  @CP-803 @CP-803-08 @planProvider @aswath @tm-regression @ui-pp
  Scenario: Pagination
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
    When I upload the service region file before to plan file
    Then I click on Plans Configuration tab
    And I upload the Plan Configuration success file
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I will see the Plan Configuration upload success message
    Then I can make a valid search of plans
    And  I will be able to view first 10 Plan Name Records
    And I can use pagination to view additional pages of results.


  @CP-803 @CP-803-09 @planProvider @aswath @tm-regression @ui-pp
  Scenario: Sort Plans search results
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
    When I upload the service region file before to plan file
    Then I click on Plans Configuration tab
    And I upload the Plan Configuration success file
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I will see the Plan Configuration upload success message
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order


  @CP-803 @CP-803-10 @planProvider @aswath @tm-regression @ui-pp
  Scenario: Click on Plan Name Search Result
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
    When I upload the service region file before to plan file
    Then I click on Plans Configuration tab
    And I upload the Plan Configuration success file
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I will see the Plan Configuration upload success message
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen

  @planProvider @planManagement @CP-1905 @CP-1905-01 @tm-regression @mohammad @ui-pp
  Scenario: Navigating to Plan Configuration Screen
    Given I logged into Tenant Manager Project list page
    And I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
    When I click on Plans Configuration tab
    Then I am navigated to the Plans Configuration screen

  @planProvider @planManagement @CP-1905 @CP-1905-02 @tm-regression @mohammad @ui-pp
  Scenario: Navigating to Plan Configuration Screen
    Given I logged into Tenant Manager Project list page
    And I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
    When I click on Plans Configuration tab
    Then I am navigated to the Plans Configuration screen
    And I see an option to upload a file to configure the different Plans for the project

  @planProvider @planManagement @CP-1905 @CP-1905-03 @tm-regression @mohammad @ui-pp
  Scenario: Cancel & Warning Message
    Given I logged into Tenant Manager Project list page
    And I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
    When I click on Plans Configuration tab
    Then I am navigated to the Plans Configuration screen
    And I have chosen a file to upload
    When I select Cancel button after choosing the file to upload
    Then I will receive this Warning Message: "Your file will not be uploaded"

  @planProvider @planManagement @CP-1905 @CP-1905-04 @tm-regression @mohammad @ui-pp
  Scenario: Back Arrow & Warning Message
    Given I logged into Tenant Manager Project list page
    And I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
    When I click on Plans Configuration tab
    Then I am navigated to the Plans Configuration screen
    And I have chosen a file to upload
    When I select the Back Arrow button after choosing the file to upload
    Then I will receive this Warning Message: "Your file will not be uploaded"

  @planProvider @planManagement @CP-1905 @CP-1905-04 @tm-regression @mohammad @ui-pp
  Scenario: Navigate away & Warning Message
    Given I logged into Tenant Manager Project list page
    And I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
    When I click on Plans Configuration tab
    Then I am navigated to the Plans Configuration screen
    And I have chosen a file to upload
    When I select to navigate away from the plan config screen
    Then I will receive this Warning Message: "Your file will not be uploaded"

  #Manual its working as expected and, Front Dev are working the UI upgradation
  @planProvider @planManagement @CP-1905 @CP-1905-05 @tm-regression @mohammad @ui-pp
  Scenario Outline: Search for a specific County Name, County Code, Zip Code or City within results
    Given I logged into Tenant Manager Project list page
    And I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I select "<Selection>" in "<Field>" field
    And I click on Search Button on Search Service Region tab
    And I verify the "<ExpectedResult>" search in results
    And I click on the Service region Name on search results
    When I click on Geographical Info tab
    And I select "<SelectionInResult>" in "<FieldInResult>" field on GEOGRAPHICAL INFO tab
    And I click on Search Button on GEOGRAPHICAL INFO tab
    Then I verify the "<SelectionInResult>" search in "<FieldInResult>" results
    Examples:
      |Field               |Selection       |ExpectedResult|FieldInResult               |SelectionInResult       |
#      |ServiceRegionName   |Central         |Central       |CountyNameAndCode           |Dodge - 045           |
#      |CountyNameAndCode   |Hall - 069    |East          |City                        |Augusta                  |
      |ZipCode             |39870           |Southwest     |ZipCode                     |31722                  |

     @planProvider @planManagement @CP-4116 @CP-4116-01 @tm-smoke @tm-regression @mohammad @ui-pp
  Scenario: Eligibility Limitations Field in the Plan Information Container
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Project Configuration
    And I select Plans Management
    And I click on Plans Configuration tab
    And I make a valid selection of Plan Name
    And I click on Search Button on Search Plans tab
    And I verify the search in results
    When I click on the Plan Name on search results
    Then I am brought to the Plan Details Screen
    When I click on the Edit button next to the Plan Information Container
    Then I will not be able to update the Eligibility Limitations field

  @planProvider @planManagement @CP-4116 @CP-4116-02 @tm-regression @mohammad @ui-pp
  Scenario: Enrollment Cap Fields in the Enrollment Information Container
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Project Configuration
    And I select Plans Management
    And I click on Plans Configuration tab
    And I make a valid selection of Plan Name
    And I click on Search Button on Search Plans tab
    And I verify the search in results
    When I click on the Plan Name on search results
    Then I am brought to the Plan Details Screen
    When I click on the Edit button next to the  Information Container
    Then I will not be able to update the Enrollment Cap field
