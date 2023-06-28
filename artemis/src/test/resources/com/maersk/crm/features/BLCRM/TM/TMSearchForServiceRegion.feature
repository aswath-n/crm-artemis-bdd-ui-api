@Re_Upload_File1
Feature: Search for a existing Service Regions on Tenant Manager UI

  @planProvider @planManagement @CP-805 @CP-805-01 @tm-regression @aswath @ui-pp
  Scenario: Verification of Navigating to the Search Service Regions configuration screen
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Then I verify the Search Service Regions configuration screen page fields

  @planProvider @planManagement @CP-805 @CP-805-02 @tm-regression @aswath @ui-pp
  Scenario Outline: Verification of Basic Search Parameters
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
#    And I verify data on the Plans Management
#    And I Click on the Service Region header
    And I select "<Selection>" in "<Field>" field
    And I click on Search Button on Search Service Region tab
    Then I verify the "<ExpectedResult>" search in results
    Examples:
      |Field               |Selection       |ExpectedResult|
      |ServiceRegionName   |Atlanta         |Atlanta       |
      |CountyNameAndCode   |Bartow - 008    |Atlanta       |
      |ZipCode             |30111           |Atlanta     |
#      |ServiceRegionName   |Atlanta         |Atlanta       |
#      |CountyNameAndCode   |GLASCOCK - 1293 |East          |
#      |ZipCode             |30117           |Southeast     |
#      |ServiceRegionName   |North           |North         |
#      |CountyNameAndCode   |HENRY - 1249    |Atlanta       |
#      |ZipCode             |30037           |Central       |

  @planProvider @planManagement @CP-805 @CP-805-04 @tm-regression @aswath @ui-pp
  Scenario: Verification of Searching for a State-Wide Service Area and "State- Wide" checkbox will disabled all others fields
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
    When I click on the checkbox next to State-Wide
    Then all other search parameters will be unavailable to the user

  @planProvider @planManagement @CP-805 @CP-805-05 @tm-smoke @tm-regression @aswath @ui-pp
  Scenario Outline:Verification of Cancel button and perform new search
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I select "<Selection1>" in "<Field1>" field
    And I click on Search Button on Search Service Region tab
    Then I verify the "<ExpectedResult1>" search in results
    And  I Click on Search Service Region tab Cancel button
    And I select "<Selection2>" in "<Field2>" field
    And I click on Search Button on Search Service Region tab
    Then I verify the "<ExpectedResult2>" search in results
    Examples:
      |Field1               |Selection1       |ExpectedResult1|Field2               |Selection2       |ExpectedResult2|
      |ServiceRegionName    |Central          |Central  |ZipCode              |30011            |Atlanta|
      |CountyNameAndCode    |Dawson - 042    |North          |ServiceRegionName    |North            |North          |
      |ZipCode              |39870            |Southwest      |CountyNameAndCode    |Glascock - 062  |East           |

  @planProvider @planManagement @CP-805 @CP-805-06 @tm-regression @aswath @ui-pp
  Scenario: Verification of Minimum of One Search Parameter required
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
    When I click on Search Button on Search Service Region tab
    Then I verify the "Please enter the search parameters" error message

  @planProvider @planManagement @CP-805 @CP-805-07 @tm-regression @aswath @ui-pp
  Scenario Outline: Verification of View Search Results header
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I select "<Selection>" in "<Field>" field
    And I click on Search Button on Search Service Region tab
    Then I verify Search Result header as "SERVICE REGION NAME"
    And I verify the "<ExpectedResult>" search in results

    Examples:
      |Field               |Selection       |ExpectedResult|
      |ServiceRegionName   |Atlanta         |Atlanta       |
      |CountyNameAndCode   |Glascock - 062  |East          |
      |ZipCode             |39870          |Southwest|

  @planProvider @planManagement @CP-805 @CP-805-10 @tm-regression @aswath @ui-pp
  Scenario Outline: Verification of Click on Service Region Name on Search Result
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I select "<Selection>" in "<Field>" field
    And I click on Search Button on Search Service Region tab
    Then I verify the "<ExpectedResult>" search in results
    And I click on the Service region Name on search results
    Then I verify that I have navigated to the Service Region Detail page
    Examples:
      |Field               |Selection       |ExpectedResult|
      |ServiceRegionName   |Atlanta         |Atlanta       |
      |CountyNameAndCode   |Dawson - 042   |North         |
      |ZipCode             |39870           |Southwest     |
