@Re_Upload_File_Service_Region_file
Feature: View Service Region Details after Search on Tenant Manager UI

  @planProvider @planManagement @CP-941 @CP-941-01 @tm-smoke @ui-pp @mohammad
  Scenario Outline: Verification of Display of Service Region Information
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "RegressionBaseline"
    When I expand the project "RegressionBaseline" to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I select "<Selection>" in "<Field>" field
    And I click on Search Button on Search Service Region tab
    Then I verify the "<ExpectedResult>" search in results
    And I click on the Service region Name on search results
    Then I verify the screen is divided into "SERVICE REGION INFO" tab and "GEOGRAPHICAL INFO" tab
    Examples:
      |Field               |Selection       |ExpectedResult|
      |ServiceRegionName   |Central         |Central       |
      |CountyNameAndCode   |Dawson - 042   |North         |
      |ZipCode             |39870           |Southwest     |

  @planProvider @planManagement @CP-941 @CP-941-02 @ui-pp @mohammad
  Scenario Outline: Verification of Default Tab View
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "RegressionBaseline"
    When I expand the project "RegressionBaseline" to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I select "<Selection>" in "<Field>" field
    And I click on Search Button on Search Service Region tab
    Then I verify the "<ExpectedResult>" search in results
    And I click on the Service region Name on search results
    Then I verify the Default tab view is always "SERIVCE REGION INFORMATION" tab
    Examples:
      |Field               |Selection       |ExpectedResult|
      |ServiceRegionName   |Central         |Central       |
      |CountyNameAndCode   |Bartow - 008   |Atlanta       |
      |ZipCode             |30159           |Southwest     |

  @planProvider @planManagement @CP-941 @CP-941-03 @ui-pp @mohammad
  Scenario Outline: Verification of Service Region Info Tab details
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "RegressionBaseline"
    When I expand the project "RegressionBaseline" to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I select "<Selection>" in "<Field>" field
    And I click on Search Button on Search Service Region tab
    And I verify the "<ExpectedResult>" search in results
    And I click on the Service region Name on search results
    And I verify the Default tab view is always "SERIVCE REGION INFORMATION" tab
    Then I verify the Service Region Name field as "<ExpectedResult>"
    And I verify the Program Type field as "<ExpectedProgramType>"
    And I verify the Sub-Program Type field as "<ExpectedSubProgramType>"
    Examples:
      |Field               |Selection       |ExpectedResult|ExpectedProgramType|ExpectedSubProgramType                         |
      |ServiceRegionName   |Central         |Central       |Medicaid           |Fostercare:PeachcareGF:MedicaidGF:MedicaidMCHB |

  @planProvider @planManagement @CP-941 @CP-941-04 @ui-pp @mohammad
  Scenario Outline: Verification of Geographical Info Tab
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "RegressionBaseline"
    When I expand the project "RegressionBaseline" to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I select "<Selection>" in "<Field>" field
    And I click on Search Button on Search Service Region tab
    And I verify the "<ExpectedResult>" search in results
    And I click on the Service region Name on search results
    When I click on Geographical Info tab
    Then I verify the system displays all the fields on page
    Examples:
      |Field               |Selection       |ExpectedResult|
      |ServiceRegionName   |Central         |Central       |


  @planProvider @planManagement @CP-941 @CP-941-05 @ui-pp @mohammad
  Scenario Outline: Search for a specific County Name, County Code, Zip Code or City within results
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "RegressionBaseline"
    When I expand the project "RegressionBaseline" to view the details
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
      |ServiceRegionName   |Central         |Central       |CountyNameAndCode           |Bibb - 011           |
      |CountyNameAndCode   |Berrien - 010   |Southwest      |City                        |Macon                |
      |ZipCode             |39870           |Southwest     |ZipCode                     |31722                   |

  @planProvider @planManagement @CP-941 @CP-941-06 @ui-pp @mohammad
  Scenario Outline: Verification of Minimum of One Search Parameter Required
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "RegressionBaseline"
    When I expand the project "RegressionBaseline" to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I select "<Selection>" in "<Field>" field
    And I click on Search Button on Search Service Region tab
    And I verify the "<ExpectedResult>" search in results
    And I click on the Service region Name on search results
    When I click on Geographical Info tab
    And I click on Search Button on GEOGRAPHICAL INFO tab
    Then Then I verify the "Please enter the search parameters" error message
    Examples:
      |Field               |Selection       |ExpectedResult|
      |ServiceRegionName   |Central         |Central       |


  @planProvider @planManagement @CP-941 @CP-941-07 @ui-pp @mohammad
  Scenario Outline: Verification of the view of first page results and Pagination
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "RegressionBaseline"
    When I expand the project "RegressionBaseline" to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I select "<Selection>" in "<Field>" field
    And I click on Search Button on Search Service Region tab
    And I verify the "<ExpectedResult>" search in results
    And I click on the Service region Name on search results
    When I click on Geographical Info tab
    Then I view "10" records at first glance with the option to expand the number of results viewed at first glance
    And I verify the pagination to view additional pages of results
    Examples:
      |Field               |Selection       |ExpectedResult|
      |ServiceRegionName   |Central         |Central       |

  @planProvider @planManagement @CP-941 @CP-941-08 @ui-pp @mohammad
  Scenario Outline: Verification of Sort by Column Headers
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "RegressionBaseline"
    When I expand the project "RegressionBaseline" to view the details
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I select "<Selection>" in "<Field>" field
    And I click on Search Button on Search Service Region tab
    And I verify the "<ExpectedResult>" search in results
    And I click on the Service region Name on search results
    When I click on Geographical Info tab
    Then I will be able to sort the Results in ascending and descending order by the Column Headers
#    incomplete logic for sorting
    Examples:
      |Field               |Selection       |ExpectedResult|
      |ServiceRegionName   |Central         |Central       |
      |CountyNameAndCode   |Bibb - 011      |Central          |
      |ZipCode             |30295           |Central      |
