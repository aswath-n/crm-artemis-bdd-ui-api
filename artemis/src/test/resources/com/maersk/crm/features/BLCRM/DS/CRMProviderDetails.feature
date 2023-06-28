@viewProvider
Feature: View Provider Details

  @CP-96 @CP-96-01 @crm-regression @kamil @ui-pp
  Scenario: Navigation to provider detail screen
    Then I logged into CRM and click on Provider Search page
    And I choosing "Peachcare" plan from Plan Name dropdown
    Given Click at advance search
    And   User select PCI Indicator "Yes"
    Then  Click on search button
    When I have selected to view the provider detail screen

  @CP-96 @CP-96-02 @crm-regression @kamil @ui-pp @aswath
  Scenario Outline: Verify the PROVIDER DETAILS fields are displayed
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps5" payload "createProvider" for Provider Search
      | providers[0].firstName                           | Colin                     |
      | providers[0].lastName                            | Dave                      |
      | providers[0].npi                                 | npi::                     |
      | providers[0].effectiveStartDate                  | date::                  |
      | providers[0].acceptNewPatientsInd                | false                     |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.npi          | ps5.providers[0].npi |
    Then Verify search result response return provider details "ps5"
    Then I logged into CRM and click on Provider Search page
    And I choosing "No" from Accepting New Patient drop down
    And   User select PCI Indicator "Yes"
    And find record for new patient by npi "" number and event name as "ps5"
    When I have selected to view the provider detail screen
    Then  I verify in page PROVIDER DETAILS fields are displayed
    Examples:
      |projectName|
      |[blank]|

  @CP-96 @CP-96-03 @crm-regression @kamil @ui-pp
  Scenario: Verify Languages details of the Provider Search
    Then I logged into CRM and click on Provider Search page
    And I choosing "Peachcare" plan from Plan Name dropdown
    Given Click at advance search
    And   User select PCI Indicator "Yes"
    When I have selected to view the provider detail screen
    Then Verify languages section where all languages up to 7 are displayed
    And Verify star for the provider’s primary language next to the primary language first option
    And Verify alternate text for disability accessibility indicating the primary language

  @CP-96 @CP-96-04 @crm-regression @kamil @ui-pp
  Scenario: Verify Specialties of the Provider details
    Then I logged into CRM and click on Provider Search page
    And I choosing "Peachcare" plan from Plan Name dropdown
    Given Click at advance search
    And   User select PCI Indicator "Yes"
    When I have selected to view the provider detail screen
    Then Verify section for Specialties where all specialties up to 10 are displayed
    And Verify a star for the provider’s primary specialty next to the primary specialty for first Specialty
    Then Verify alternate text for disability accessibility indicating the primary specialty

  @CP-96 @CP-96-05 @crm-regression @kamil @ui-pp
  Scenario: Verify Locations affiliated of the Provider details
    Then I logged into CRM and click on Provider Search page
    And I choosing "Peachcare" plan from Plan Name dropdown
    Given Click at advance search
    And   User select PCI Indicator "Yes"
    When I have selected to view the provider detail screen
    Then Verify Location affiliated details are displayed in page


  @CP-96 @CP-96-06.0 @crm-regression @kamil @ui-pp
  Scenario Outline: Verify the green icon for New Patients column of the Provider details
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps7" payload "createProvider" for Provider Search
      | providers[0].firstName                           | Colin                     |
      | providers[0].lastName                            | Dave                      |
      | providers[0].npi                                 | npi::                     |
      | providers[0].effectiveStartDate                  | date::                  |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.npi          | ps7.providers[0].npi |
    Then Verify search result response return provider details "ps7"
    Then I logged into CRM and click on Provider Search page
    And I choosing "Yes" from Accepting New Patient drop down
    And find record for new patient by npi "" number and event name as "ps7"
    When I have selected to view the provider detail screen
    When I view the icons for Accepting New Patients
    Then verify Hex color is green "#29A040" icon person accepting new patients
    Examples:
      |projectName|
      |[blank]|


  @CP-96 @CP-96-06.1 @crm-regression @kamil @ui-pp
  Scenario Outline: Verify the red icon existing Patients column of the Provider details
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps8" payload "createProvider" for Provider Search
      | providers[0].firstName                           | Colin                     |
      | providers[0].lastName                            | Dave                      |
      | providers[0].npi                                 | npi::                     |
      | providers[0].effectiveStartDate                  | date::                  |
      | providers[0].acceptNewPatientsInd                | false                     |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.npi          | ps8.providers[0].npi |
    Then Verify search result response return provider details "ps8"
    Then I logged into CRM and click on Provider Search page
    And I choosing "No" from Accepting New Patient drop down
    And find record for new patient by npi "" number and event name as "ps8"
    When I have selected to view the provider detail screen
    Then verify Hex color is red "#EF5350" icon person accepting patients
    Examples:
      |projectName|
      |[blank]|

  @CP-96 @CP-96-07.0 @crm-regression @kamil @ui-pp
  Scenario Outline: Verify the green icon Handicap Accessible column of the Provider details
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps9" payload "createProvider" for Provider Search
      | providers[0].firstName                           | Colin                     |
      | providers[0].lastName                            | Dave                      |
      | providers[0].npi                                 | npi::                     |
      | providers[0].effectiveStartDate                  | date::                  |
      | providers[0].handicappedAccesibleInd              | true                     |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.npi          | ps9.providers[0].npi |
    Then Verify search result response return provider details "ps9"
    Then I logged into CRM and click on Provider Search page
    And I choosing "Yes" from Accepting New Patient drop down
    And find record for new patient by npi "" number and event name as "ps9"
    When I have selected to view the provider detail screen
    When I view the icons for Handicap Accessible
    Then verify Hex color is green "#29A040" icon Handicap Accessible
    Examples:
      |projectName|
      |[blank]|

  @CP-96 @CP-96-07.1 @crm-regression @kamil @ui-pp
  Scenario Outline: Verify the red icon Handicap Accessible column of the Provider details
    Given I will get the Authentication token for "<projectName>" in "CRM"
#    When I initiated Plan Management for service provider API
#    And User send Api call with name "ps10" payload "createProvider" for Provider Search
#      | providers[0].firstName                           | Colin                     |
#      | providers[0].lastName                            | Dave                      |
#      | providers[0].npi                                 | npi::                     |
#      | providers[0].effectiveStartDate                  | date::                  |
#      | providers[0].acceptNewPatientsInd                | false                     |
#      | providers[0].handicappedAccesibleInd              | false                     |
#    Then I verify the response status code "200" with creation status "success"
#    Given I initiated Plan Management for Search Provider
#    And User send Api call with payload "providerSearch" for search provider
#      | providerSearch.firstName          | Colin |
#    Then Verify search result response return provider details "ps10"
    Then I logged into CRM and click on Provider Search page
    And I choosing "No" from Accepting New Patient drop down
    And I searching with first name "Colin" and last name "Dave"
#    And find record for new patient by npi "" number and event name as "ps10"
    When I have selected to view the provider detail screen
    When I view the icons for Handicap Accessible
    Then verify Hex color is red "#29A040" icon Handicap Accessible
    Examples:
      |projectName|
      |[blank]|

  @CP-96 @CP-96-08 @crm-regression @kamil @ui-pp
  Scenario Outline: Verify Office Hours column of the Provider details
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps11" payload "createProvider" for Provider Search
      | providers[0].firstName                           | Colin                     |
      | providers[0].lastName                            | Dave                      |
      | providers[0].npi                                 | npi::                     |
      | providers[0].effectiveStartDate                  | date::                  |
      | providers[0].handicappedAccesibleInd              | true                     |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.npi          | ps11.providers[0].npi |
    Then Verify search result response return provider details "ps11"
    Then I logged into CRM and click on Provider Search page
    And I choosing "Yes" from Accepting New Patient drop down
    And find record for new patient by npi "" number and event name as "ps11"
    When I have selected to view the provider detail screen
    Then Verify I view Office Hours column
    When I hover my mouse over each icon, the Office Hours will display for that day of the week
    And Verify Hex color "#D32F2F" icon for each day of the week that does not have office hours
    Examples:
      |projectName|
      |[blank]|

#  @CP-96 @CP-96-09 @crm-regression @kamil @ui-pp          ------------>Implementation change
  Scenario Outline: Verify Paginate locations affiliated to the Provider
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps12" payload "createProvider10Address" for Provider Search
      | providers[0].firstName                           | Colin                     |
      | providers[0].lastName                            | Dave                      |
      | providers[0].npi                                 | npi::                     |
      | providers[0].effectiveStartDate                  | date::                  |
      | providers[0].handicappedAccesibleInd              | true                     |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.npi          | ps12.providers[0].npi |
    Then Verify search result response return provider details "ps12"
    Then I logged into CRM and click on Provider Search page
    And I choosing "Yes" from Accepting New Patient drop down
    And find record for new patient by npi "" number and event name as "ps12"
    When I have selected to view the provider detail screen
    When I view the locations affiliated to the provider
    Then Verify user will see 5 locations in the page and
    Then Verify the option to page through the locations
    Examples:
      |projectName|
      |[blank]|

#  @CP-96 @CP-96-10 @crm-regression @kamil @ui-pp       ---------------->Implementation change
  Scenario: Verify Navigate Back to Search Results
    Then I logged into CRM and click on Provider Search page
    And I choosing "Peachcare" plan from Plan Name dropdown
    Given I have selected to view the provider detail screen
    When I select the back arrow navigated back to the search results screen
    And Verify no warning message will display and the search results will remain

  @CP-96 @CP-96-11 @crm-regression @kamil @ui-pp
  Scenario: Verify Navigate Away - Left Hand Navigation
    Then I logged into CRM and click on Provider Search page
    And I choosing "Peachcare" plan from Plan Name dropdown
    Given Click at advance search
    And   User select PCI Indicator "Yes"
    Given I have selected to view the provider detail screen
    Then  I verify in page PROVIDER DETAILS fields are displayed
    When I select an icon in the left hand navigation
    Then I will be navigated away, no warning message will display, and the search results will be cleared

  @CP-8022 @CP-8022-01 @crm-regression @aswath
  Scenario Outline: Verify the green icon New Patients column of the Provider details
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps" payload "createProvider" for Provider Search
      | providers[0].firstName                           | Colin                     |
      | providers[0].lastName                            | Dave                      |
      | providers[0].npi                                 | npi::                     |
      | providers[0].effectiveStartDate                  | date::                  |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.npi          | ps.providers[0].npi |
    Then Verify search result response return provider details "ps"
    #Then Verify search result response return provider details
    Then I logged into CRM and click on Provider Search page
    And I choosing "Yes" from Accepting New Patient drop down
   And find record for new patient by npi "" number and event name as "ps"
    Then I will verify the accept new patient icon on the provider result table "#29A040"
    Examples:
      |projectName|
      |[blank]|

  @CP-8022 @CP-8022-02 @crm-regression @aswath @crm-smoke
  Scenario Outline: Verify the green accepting New Patients column of the Provider address list
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps1" payload "createProvider10Address" for Provider Search
      | providers[0].firstName                           | Colin                     |
      | providers[0].lastName                            | Dave                      |
      | providers[0].npi                                 | npi::                     |
      | providers[0].effectiveStartDate                  | 2020-08-27                  |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.npi          | ps1.providers[0].npi |
    Then Verify search result response return provider details "ps1"
    Then I logged into CRM and click on Provider Search page
    And I choosing "Yes" from Accepting New Patient drop down
    And find record for new patient by npi "" number and event name as "ps1"
    Then I will verify the accept new patient icon on the provider result table "#29A040"
    When I click the additional address icon
    Then I will see the list of locations with address and phone no
    Then I will verify the accept new patient icon on the provider additional address list "#29A040"
    Examples:
      |projectName|
      |[blank]|

  @CP-8022 @CP-8022-03 @crm-regression @aswath
  Scenario Outline: Verify the green icon New Patients column of the Provider details
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps2" payload "createProvider" for Provider Search
      | providers[0].firstName                           | Colin                     |
      | providers[0].lastName                            | Dave                      |
      | providers[0].npi                                 | npi::                     |
      | providers[0].effectiveStartDate                  | date::                  |
      | providers[0].acceptNewPatientsInd                | false                     |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.npi          | ps2.providers[0].npi |
    Then Verify search result response return provider details "ps2"
    Then I logged into CRM and click on Provider Search page
    And I choosing "No" from Accepting New Patient drop down
    And find record for new patient by npi "" number and event name as "ps2"
    Then I will verify the No accept new patient icon on the provider additional address "#EF5350"
    Examples:
      |projectName|
      |[blank]|

  @CP-8022 @CP-8022-04 @crm-regression @aswath @crm-smoke
  Scenario Outline: Verify the green accepting New Patients column of the Provider address list
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps3" payload "createProvider10Address" for Provider Search
      | providers[0].firstName                           | Colin                     |
      | providers[0].lastName                            | Dave                      |
      | providers[0].npi                                 | npi::                     |
      | providers[0].effectiveStartDate                  | date::                  |
      | providers[0].acceptNewPatientsInd                | false                     |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.npi          | ps3.providers[0].npi |
    Then Verify search result response return provider details "ps3"
    Then I logged into CRM and click on Provider Search page
    And I choosing "No" from Accepting New Patient drop down
    And find record for new patient by npi "" number and event name as "ps3"
    Then I will verify the No accept new patient icon on the provider additional address "#EF5350"
    When I click the additional address icon
    Then I will see the list of locations with address and phone no
    Then I will verify the accept new patient icon on the provider additional address list "#EF5350"
    Examples:
      |projectName|
      |[blank]|


  @CP-30885 @CP-30885-1 @CP-30885-2 @planProvidergression @planProvider @ui-pp @mital
  Scenario: Provider Updates - Gender Served Search Criteria - Verify Gender Served Drop-down values
  Provider Updates - Gender Served Search Criteria - Hide Provider Gender
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    Then I verify fields on provider search page with data
      | GENDER SERVED | is displayed |
      | PROVIDER GENDER | hidden       |
    Then I verify dropdown Gender Served on provider search page has options
      |All|
      |Female|
      |Male|

  @CP-30885 @CP-30885-1.1 @planProvidergression @planProvider @ui-pp @mital
  Scenario: Provider Updates - Gender Served Search Criteria - Verify Gender Served Drop-down values
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page

    When I select gender served "Male" value from dropdown
    And I click on "SEARCH" button on provider search page
    Then I verify records fetched contain Gender Served value as "Male"

    When I select gender served "Female" value from dropdown
    And I click on "SEARCH" button on provider search page
    Then I verify records fetched contain Gender Served value as "Female"

    When I select gender served "All" value from dropdown
    And I click on "SEARCH" button on provider search page
    Then I verify records fetched contain all Gender Served values

  @CP-11073 @CP-11073-1 @planProvidergression @planProvider @ui-pp @mital
  Scenario: Global provider search with Program Name and Program Type- Dental
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      | PROGRAM NAME | MedicaidGF |
    And I fill out Provider Type field on provider search page with value "Dental"
    When I fill out fields on provider search page with data
      | PATIENT AGE RANGE | 0 - 120 |
    And I fill out field Handicap Accessible on provider search page with data "Yes"
    And I click on "SEARCH" button on provider search page
    Then I verivy provider search results at "PROGRAM NAME" column contains cells with values
      | MedicaidGF |
    Then I verivy provider search results at "PROVIDER TYPE" column contains cells with values
      | DENTAL |
    Then I verify provider's Handicap Accessible value "Yes" in provider search result

  @CP-37257 @CP-37257-1 @planProvidergression @planProvider @ui-pp @mital
  Scenario: Global provider search with Program Name and Program Type- Dental
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    Then I verify visuals should match the following list in this order on provider search page
      | LANGUAGES              |
      | PCP INDICATOR          |
      | OB INDICATOR           |
      | ACCEPTING NEW PATIENTS |
      | GENDER SERVED          |
      | PROVIDER GENDER        |
      | COUNTY                 |
      | STATE                  |
      | PATIENT AGE RANGE      |
      | HANDICAP ACCESSIBLE    |
      | NPI                    |


