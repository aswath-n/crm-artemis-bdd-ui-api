Feature: Filter Provider Search Result - Group Name, Provider Name

  @CP-106 @CP-106-01 @crm-regression @kamil @ui-pp
  Scenario Outline: Filter the search by Provider Name
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps10601" payload "createProvider" for Provider Search
      | providers[0].firstName          | Colin     |
      | providers[0].lastName           | Dave      |
      | providers[0].npi                | npi::     |
      | providers[0].effectiveStartDate | date::    |
      | providers[0].groupName          | ColinDave |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.npi | ps10601.providers[0].npi |
    Then Verify search result response return provider details "ps10601"
    Then I logged into CRM and click on Provider Search page
    And I searching with Group Name "ColinDave"
    And I will be able to filter the results by Provider Name
    Then I have selected to view the provider detail screen
    Examples:
      | projectName |
      |[blank]|

#  @CP-106 @CP-106-02 @crm-regression @kamil @ui-pp      ----// Implementation change according to CP-11181 AC 2.0
  Scenario Outline: Verify more than one filter option can be selected
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps10602" payload "createProvider" for Provider Search
      | providers[0].firstName          | Andy     |
      | providers[0].lastName           | John     |
      | providers[0].npi                | npi::    |
      | providers[0].effectiveStartDate | date::   |
      | providers[0].groupName          | AndyJohn |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.npi | ps10602.providers[0].npi |
    Then Verify search result response return provider details "ps10602"
    Then I logged into CRM and click on Provider Search page
    And I searching with Group Name "AndyJohn"
    Then I am able to filter with "Andy B John" PROVIDER NAME and "AndyJohn" GROUP NAME
    And Verify PROVIDER NAME "Andy B John" AND GROUP NAME "AndyJohn" are displayed
    Examples:
      | projectName |
      |[blank]|

#  @CP-106 @CP-106-03 @crm-regression @kamil @ui-pp ----// Implementation change according to CP-11181 AC 2.0
  Scenario Outline: Verify Single selection with Multiple column
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps10603" payload "createProvider" for Provider Search
      | providers[0].firstName          | Andy      |
      | providers[0].lastName           | John      |
      | providers[0].npi                | npi::     |
      | providers[0].effectiveStartDate | date::    |
      | providers[0].groupName          | AndyJohn1 |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.npi | ps10603.providers[0].npi |
    Then Verify search result response return provider details "ps10603"
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps10603" payload "createProvider" for Provider Search
      | providers[0].firstName          | Andy      |
      | providers[0].lastName           | John      |
      | providers[0].npi                | npi::     |
      | providers[0].effectiveStartDate | date::    |
      | providers[0].groupName          | AndyJohn2 |
    Then I verify the response status code "200" with creation status "success"
    Then I logged into CRM and click on Provider Search page
    And I searching first name "Andy" and last name "John" with Plan Name "AMERIGROUP COMMUNITY CARE"
    Then I am able to filter with "Andy B John" PROVIDER NAME with GROUP NAMES "AndyJohn1" and "AndyJohn2"
    And Verify after multi filtering for GROUP NAME "AndyJohn1" and "AndyJohn2" results are displayed
    Examples:
      | projectName |
      |[blank]|

#  @CP-106 @CP-106-04 @crm-regression @kamil @ui-pp   ----// Implementation change according to CP-11181 AC 2.0
  Scenario Outline: Verify Auto Complete in Search field
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps10604" payload "createProvider" for Provider Search
      | providers[0].firstName          | Mo      |
      | providers[0].lastName           | Salah   |
      | providers[0].npi                | npi::   |
      | providers[0].effectiveStartDate | date::  |
      | providers[0].groupName          | MoSalah |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.npi | ps10604.providers[0].npi |
    Then Verify search result response return provider details "ps10604"
    Then I logged into CRM and click on Provider Search page
    And I searching with first name "Mo" and last name "Salah"
    Then I am able to filter with "Mo B Salah" PROVIDER NAME where GROUP NAME contains "MoSal"
    And Verify PROVIDER NAME "Mo B Salah" AND GROUP NAME "MoSalah" are displayed
    Examples:
      | projectName |
      |[blank]|


#  @CP-106 @CP-106-05 @crm-regression @kamil @ui-pp  ----// Implementation change according to CP-11181 AC 2.0
  Scenario Outline: Verify Filter the search by Provider Name - results list
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps10605" payload "createProvider" for Provider Search
      | providers[0].firstName          | Mo      |
      | providers[0].lastName           | Salah   |
      | providers[0].npi                | npi::   |
      | providers[0].effectiveStartDate | date::  |
      | providers[0].groupName          | MoSalah |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.npi | ps10605.providers[0].npi |
    Then Verify search result response return provider details "ps10605"
    Then I logged into CRM and click on Provider Search page
    And I searching with Group Name "MoSalah"
    Then I filter by Provider Name is "Mo B Salah"
    And Verify Provider Name "Mo B Salah" results list are displayed
    Examples:
      | projectName |
      |[blank]|


  @CP-106 @CP-106-06 @CP-8462 @CP-8462-06 @crm-regression @kamil @ui-pp
  Scenario: Verify Filter the search by Provider Name -  Number options selected for filter
    Then I logged into CRM and click on Provider Search page
    And I choosing "AMERIGROUP COMMUNITY CARE" plan from Plan Name dropdown
    When I fill out fields on provider search page with data
      | PROVIDER TYPE | MEDICAL |
    Then Verify user by default should see 20 selected options are displayed


  @CP-106 @CP-106-07 @crm-regression @kamil @ui-pp
  Scenario Outline: Verify Filter the search by Provider Name -  Number options selected for filter with results
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
#    Then I create a "createProvider" request payload with the appropriate data and post with data
#      | firstName | Steven                    |
#      | lastName  | Filter                    |
#      | genderCd  | M                         |
#      | groupName | StevenFilter              |
#      | planName  | AMERIGROUP COMMUNITY CARE |
#    Then I verify the response status code "200" with creation status "success"
#    Given I initiated Plan Management for service provider API
#    When I will get the Authentication token for "<projectName>" in "CRM"
#    Then I create a "createProvider" request payload with the appropriate data and post with data
#      | firstName | Steven                    |
#      | lastName  | Filter                    |
#      | genderCd  | M                         |
#      | groupName | StevenFilter2             |
#      | planName  | AMERIGROUP COMMUNITY CARE |
#    Then I verify the response status code "200" with creation status "success"
    Then I logged into CRM and click on Provider Search page
    And I searching for first name "Steven"
    Then I filtered Provider name by "Steven B Gerrard" and "Steven B Filter"
    Then Verify after multi filtering for PROVIDER NAME "Steven B Gerrard" and "Steven B Filter" results are displayed
    Examples:
      | projectName |
      |[blank]|

  @CP-106 @CP-106-08 @crm-regression @kamil @ui-pp
  Scenario Outline: Verify Filter the search by Provider Name - Number options selected for filter with results is applied for Column
    Given I will get the Authentication token for "<projectName>" in "CRM"
#    When  I initiated Plan Management for service provider API
#    Then I create a "createProvider" request payload with the appropriate data and post with data
#      | firstName | Steven                    |
#      | lastName  | Filter                    |
#      | genderCd  | M                         |
#      | groupName | StevenFilter              |
#      | planName  | AMERIGROUP COMMUNITY CARE |
#    Then I verify the response status code "200" with creation status "success"
#    Given I initiated Plan Management for service provider API
#    When I will get the Authentication token for "<projectName>" in "CRM"
#    Then I create a "createProvider" request payload with the appropriate data and post with data
#      | firstName | Steven                    |
#      | lastName  | Gerrard                   |
#      | genderCd  | M                         |
#      | groupName | StevenGerrard             |
#      | planName  | AMERIGROUP COMMUNITY CARE |
#    Then I verify the response status code "200" with creation status "success"
    Then I logged into CRM and click on Provider Search page
    And I searching for first name "Steven"
    Then I filtered Provider name by "Steven B Gerrard" and "Steven B Filter"
#    And Verify user should be able to view filtered is enabled in the applied column
    And Verify after multi filtering for PROVIDER NAME "Steven B Gerrard" and "Steven B Filter" results are displayed
    Examples:
      | projectName |
      |[blank]|


  @CP-8462 @CP-8462-01 @crm-regression @kamil @ui-pp
  Scenario Outline: Filter the search by Group Name
    Given I will get the Authentication token for "<projectName>" in "CRM"
#    When  I initiated Plan Management for service provider API
##    Then I create a "createProvider" request payload with the appropriate data and post with data
##      | firstName | Andy                      |
##      | lastName  | John                      |
##      | genderCd  | M                         |
##      | groupName | AndyJohn                  |
##      | planName  | AMERIGROUP COMMUNITY CARE |
#    Then I verify the response status code "200" with creation status "success"
    Then I logged into CRM and click on Provider Search page
    And I searching with Group Name "AndyJohn"
    Then Verify I will be able to filter the results by Group Name "AndyJohn"
    Examples:
      | projectName |
      |[blank]|


#  @CP-8462 @CP-8462-02 @crm-regression @kamil @ui-pp ----// Implementation change according to CP-11181 AC 2.0
  Scenario Outline: Verify more than one filter option can be selected for Group Name
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When  I initiated Plan Management for service provider API
    Then I create a "createProvider" request payload with the appropriate data and post with data
      | firstName | Andy                      |
      | lastName  | John                      |
      | genderCd  | M                         |
      | groupName | AndyJohn                  |
      | planName  | AMERIGROUP COMMUNITY CARE |
    Then I verify the response status code "200" with creation status "success"
    Then I logged into CRM and click on Provider Search page
    And I searching with Group Name "AndyJohn"
    And Verify filter options are displayed
    Then I am able to filter with "Andy B John" PROVIDER NAME and "AndyJohn" GROUP NAME
    And Verify PROVIDER NAME "Andy B John" AND GROUP NAME "AndyJohn" are displayed
    Examples:
      | projectName |
      |[blank]|


#  @CP-8462 @CP-8462-03 @crm-regression @kamil @ui-pp  ----// Implementation change according to CP-11181 AC 2.0
  Scenario Outline: Verify Single selection with Multiple column for GROUP NAME
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When  I initiated Plan Management for service provider API
    Then I create a "createProvider" request payload with the appropriate data and post with data
      | firstName | Andy                      |
      | lastName  | John                      |
      | genderCd  | M                         |
      | groupName | AndyJohn                  |
      | planName  | AMERIGROUP COMMUNITY CARE |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for service provider API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I create a "createProvider" request payload with the appropriate data and post with data
      | firstName | Jake                      |
      | lastName  | Black                     |
      | genderCd  | M                         |
      | groupName | AndyJohn                  |
      | planName  | AMERIGROUP COMMUNITY CARE |
    Then I verify the response status code "200" with creation status "success"
    Then I logged into CRM and click on Provider Search page
    And I searching with Group Name "AndyJohn"
    Then I am able to filter with "AndyJohn" GROUP NAME WITH PROVIDER NAMES "Andy B John" and "Jake B Black"
    And Verify after multi filtering for PROVIDER NAME "Andy B John" and "Jake B Black" results are displayed
    Examples:
      | projectName |
      |[blank]|

#  @CP-8462 @CP-8462-04 @crm-regression @kamil @ui-pp ----// Implementation change according to CP-11181 AC 2.0
  Scenario Outline: Verify Auto Complete in Search field for GROUP NAME
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When  I initiated Plan Management for service provider API
    Then I create a "createProvider" request payload with the appropriate data and post with data
      | firstName | Jake                      |
      | lastName  | Black                     |
      | genderCd  | M                         |
      | groupName | JakeBlack                 |
      | planName  | AMERIGROUP COMMUNITY CARE |
    Then I verify the response status code "200" with creation status "success"
    Then I logged into CRM and click on Provider Search page
    And I searching with Group Name "JakeBlack"
    Then I am able to filter with "Jake B Black" PROVIDER NAME where GROUP NAME contains "JakeBl"
    And Verify PROVIDER NAME "Jake B Black" AND GROUP NAME "JakeBlack" are displayed
    Examples:
      | projectName |
      |[blank]|

#  @CP-8462 @CP-8462-05 @crm-regression @kamil @ui-pp                      ----Implementation change
  Scenario Outline: Verify Filter the search by GROUP NAME - results list
#    Given I will get the Authentication token for "<projectName>" in "CRM"
#    When  I initiated Plan Management for service provider API
#    Then I create a "createProvider" request payload with the appropriate data and post with data
#      | firstName | Jake                      |
#      | lastName  | Black                     |
#      | genderCd  | M                         |
#      | groupName | JakeBlack                 |
#      | planName  | AMERIGROUP COMMUNITY CARE |
#    Then I verify the response status code "200" with creation status "success"
    Then I logged into CRM and click on Provider Search page
    And I searching with Group Name "JakeBlack"
    Then I filter by Group Name is "JakeBlack"
    Then Verify I will be able to filter the results by Group Name "JakeBlack"
    Examples:
      | projectName |
      |[blank]|

#  @CP-8462 @CP-8462-06 @crm-regression @kamil @ui-pp
  Scenario: Verify Filter the search by Provider Name -  Number options selected for filter
    Then I logged into CRM and click on Provider Search page
    And I choosing "AMERIGROUP COMMUNITY CARE" plan from Plan Name dropdown
    Then Verify user by default should see 20 selected options are displayed

#  @CP-8462 @CP-8462-07 @crm-regression @kamil @ui-pp   ----// Implementation change according to CP-11181 AC 2.0
  Scenario Outline: Verify Filter the search by Group Name -  Number options selected for filter with results
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When  I initiated Plan Management for service provider API
    Then I create a "createProvider" request payload with the appropriate data and post with data
      | firstName | Steven                    |
      | lastName  | Gerrard                   |
      | genderCd  | M                         |
      | groupName | StevenFilter1             |
      | planName  | AMERIGROUP COMMUNITY CARE |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for service provider API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I create a "createProvider" request payload with the appropriate data and post with data
      | firstName | Steven                    |
      | lastName  | Gerrard                   |
      | genderCd  | M                         |
      | groupName | StevenFilter2             |
      | planName  | AMERIGROUP COMMUNITY CARE |
    Then I verify the response status code "200" with creation status "success"
    Then I logged into CRM and click on Provider Search page
    And I searching first name "Steven" and last name "Gerrard" with Plan Name "AMERIGROUP COMMUNITY CARE"
    Then I filtered by Group name by "StevenFilter1" and "StevenFilter2"
    Then Verify after multi filtering for GROUP NAME "StevenFilter1" and "StevenFilter2" results are displayed
    Examples:
      | projectName |
      |[blank]|

#  @CP-8462 @CP-8462-08 @crm-regression @kamil @ui-pp     ----// Implementation change according to CP-11181 AC 2.0
  Scenario Outline: Verify Filter the search by Group Name - Number options selected for filter with results is applied for Column
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When  I initiated Plan Management for service provider API
    Then I create a "createProvider" request payload with the appropriate data and post with data
      | firstName | Steven                    |
      | lastName  | Gerrard                   |
      | genderCd  | M                         |
      | groupName | StevenFilter1             |
      | planName  | AMERIGROUP COMMUNITY CARE |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for service provider API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I create a "createProvider" request payload with the appropriate data and post with data
      | firstName | Steven                    |
      | lastName  | Gerrard                   |
      | genderCd  | M                         |
      | groupName | StevenFilter2             |
      | planName  | AMERIGROUP COMMUNITY CARE |
    Then I verify the response status code "200" with creation status "success"
    Then I logged into CRM and click on Provider Search page
    And I searching first name "Steven" and last name "Gerrard" with Plan Name "AMERIGROUP COMMUNITY CARE"
    Then I filtered by Group name by "StevenFilter1" and "StevenFilter2"
    And Verify user should be able to view filtered GROUP NAME is enabled in applied column
    Examples:
      | projectName |
      |[blank]|

  @CP-8463 @CP-8463-01 @crm-regression @kamil @ui-pp
  Scenario Outline: Filter the search by PLAN NAME
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I logged into CRM and click on Provider Search page
    And I searching with Group Name "AndyJohn"
    Then Verify I will be able to filter the results by PLAN NAME "AMERIGROUP COMMUNITY CARE"
    Examples:
      | projectName |
      |[blank]|

  @CP-8463 @CP-8463-02 @crm-regression @kamil @ui-pp        # ----// Implementation change according to CP-11181 AC 2.0
  Scenario Outline: Verify more than one filter option can be selected for PLAN NAME
    Given I will get the Authentication token for "<projectName>" in "CRM"
#    When  I initiated Plan Management for service provider API
#    Then I create a "createProvider" request payload with the appropriate data and post with data
#      | firstName | Andy                      |
#      | lastName  | John                      |
#      | genderCd  | M                         |
#      | groupName | AndyJohn                  |
#      | planName  | AMERIGROUP COMMUNITY CARE |
#    Then I verify the response status code "200" with creation status "success"
    Then I logged into CRM and click on Provider Search page
    And I searching for first name "Andy"
    And Verify filter options are displayed
    Then I am able to filter with "Andy B John" PROVIDER NAME and "AMERIGROUP COMMUNITY CARE" PLAN NAME
    And Verify PROVIDER NAME "Andy B John" AND PLAN NAME "AMERIGROUP COMMUNITY CARE" are displayed
    Examples:
      | projectName |
      |[blank]|

#  @CP-8463 @CP-8463-03 @crm-regression @kamil @ui-pp                  # ----// Implementation change according to CP-11181 AC 2.0
  Scenario Outline: Verify Single selection with Multiple column for PLAN NAME
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When  I initiated Plan Management for service provider API
    Then I create a "createProvider" request payload with the appropriate data and post with data
      | firstName | Andy                      |
      | lastName  | John                      |
      | genderCd  | M                         |
      | groupName | AndyJohn                  |
      | planName  | AMERIGROUP COMMUNITY CARE |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for service provider API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I create a "createProvider" request payload with the appropriate data and post with data
      | firstName | Jake                      |
      | lastName  | Black                     |
      | genderCd  | M                         |
      | groupName | AndyJohn                  |
      | planName  | AMERIGROUP COMMUNITY CARE |
    Then I verify the response status code "200" with creation status "success"
    Then I logged into CRM and click on Provider Search page
    And I searching for first name "Andy"
    Then I am able to filter with "AMERIGROUP COMMUNITY CARE" PLAN NAME WITH PROVIDER NAMES "Andy B John" and "Jake B Black"
    And Verify after multi filtering for PROVIDER NAME "Andy B John" and "Jake B Black" results are displayed
    Examples:
      | projectName |
      |[blank]|

  @CP-8463 @CP-8463-04 @crm-regression @kamil @ui-pp
  Scenario Outline: Verify Auto Complete in Search field for PLAN NAME
    Given I will get the Authentication token for "<projectName>" in "CRM"
#    When  I initiated Plan Management for service provider API
#    Then I create a "createProvider" request payload with the appropriate data and post with data
#      | firstName | Jake                      |
#      | lastName  | Black                     |
#      | genderCd  | M                         |
#      | groupName | JakeBlack                 |
#      | planName  | AMERIGROUP COMMUNITY CARE |
#    Then I verify the response status code "200" with creation status "success"
    Then I logged into CRM and click on Provider Search page
    And I searching for first name "Jake"
    Then I am able to filter with "Jake B Black" PROVIDER NAME where PLAN NAME contains "AMERIGROUP COMMUNITY"
    And Verify PROVIDER NAME "Jake B Black" AND PLAN NAME "AMERIGROUP COMMUNITY CARE" are displayed
    Examples:
      | projectName |
      |[blank]|

  @CP-8463 @CP-8463-05 @crm-regression @kamil @ui-pp
  Scenario Outline: Verify Filter the search by PLAN NAME - results list
    Given I will get the Authentication token for "<projectName>" in "CRM"
#    When  I initiated Plan Management for service provider API
#    Then I create a "createProvider" request payload with the appropriate data and post with data
#      | firstName | Jake                      |
#      | lastName  | Black                     |
#      | genderCd  | M                         |
#      | groupName | JakeBlack                 |
#      | planName  | AMERIGROUP COMMUNITY CARE |
#    Then I verify the response status code "200" with creation status "success"
    Then I logged into CRM and click on Provider Search page
    And I searching for first name "Jake"
    Then Verify I will be able to filter the results by PLAN NAME "AMERIGROUP COMMUNITY CARE"
    And Verify PLAN Name "AMERIGROUP COMMUNITY CARE" results list are displayed
    Examples:
      | projectName |
      |[blank]|

  @CP-8463 @CP-8463-06 @crm-regression @kamil @ui-pp
  Scenario Outline: Verify Filter the search by PLAN Name -  Number options selected for filter with results
    Given I will get the Authentication token for "<projectName>" in "CRM"
#    When  I initiated Plan Management for service provider API
#    Then I create a "createProvider" request payload with the appropriate data and post with data
#      | firstName | Steven                    |
#      | lastName  | Gerrard                   |
#      | genderCd  | M                         |
#      | groupName | StevenGerrard             |
#      | planName  | AMERIGROUP COMMUNITY CARE |
#    Then I verify the response status code "200" with creation status "success"
#    Given I initiated Plan Management for service provider API
#    When I will get the Authentication token for "<projectName>" in "CRM"
#    Then I create a "createProvider" request payload with the appropriate data and post with data
#      | firstName | Steven        |
#      | lastName  | Gerrard       |
#      | genderCd  | M             |
#      | groupName | StevenGerrard |
#      | planName  | Peachcare     |
#    Then I verify the response status code "200" with creation status "success"
    Then I logged into CRM and click on Provider Search page
    And I searching for first name "Steven"
    Then I filtered by PLAN Name by "AMERIGROUP COMMUNITY CARE" and "Peachcare"
    Then Verify after multi filtering for PLAN NAME "AMERIGROUP COMMUNITY CARE" and "Peachcare" results are displayed
    Examples:
      | projectName |
      |[blank]|

  @CP-8463 @CP-8463-07 @crm-regression @kamil @ui-pp
  Scenario Outline: Verify Filter the search by PLAN Name - Number options selected for filter with results is applied for Column
    Given I will get the Authentication token for "<projectName>" in "CRM"
#    When  I initiated Plan Management for service provider API
#    Then I create a "createProvider" request payload with the appropriate data and post with data
#      | firstName | Steven                    |
#      | lastName  | Gerrard                   |
#      | genderCd  | M                         |
#      | groupName | StevenGerrard             |
#      | planName  | AMERIGROUP COMMUNITY CARE |
#    Then I verify the response status code "200" with creation status "success"
#    Given I initiated Plan Management for service provider API
#    When I will get the Authentication token for "<projectName>" in "CRM"
#    Then I create a "createProvider" request payload with the appropriate data and post with data
#      | firstName | Steven        |
#      | lastName  | Gerrard       |
#      | genderCd  | M             |
#      | groupName | StevenGerrard |
#      | planName  | Peachcare     |
#    Then I verify the response status code "200" with creation status "success"
    Then I logged into CRM and click on Provider Search page
    And I searching for first name "Steven"
    Then I filtered by PLAN Name by "AMERIGROUP COMMUNITY CARE" and "Peachcare"
    And Verify user should be able to view filtered PLAN NAME is enabled in applied column
    Examples:
      | projectName |
      |[blank]|

  @CP-24348 @CP-24348-01 @CP-24348-02 @CP-24348-03 @crm-regression @ui-pp @sobir
  Scenario: Baseline General Provider Search Results
    Given I logged into CRM and click on Provider Search page
    And I minimize Genesys popup if populates
    And I click on "Advanced Search" button on provider search page
    # 1.0. Global Provider Search Results - Distance Default
    When I fill out fields on provider search page with data
      | STREET ADDRESS | 6000 Joe Frank Harris Pkwy NW |
      | CITY           | Adairsville                   |
    Then I verify fields on provider search page with data
      | DISTANCE (mi) | 15 |
    And I click on "CANCEL" button on provider search page
    When I fill out fields on provider search page with data
      | STREET ADDRESS | 6000 Joe Frank Harris Pkwy NW |
      | ZIP            | 30103                         |
    Then I verify fields on provider search page with data
      | DISTANCE (mi) | 15 |
    And I click on "CANCEL" button on provider search page
    When I fill out fields on provider search page with data
      | CITY | Adairsville |
      | ZIP  | 30103       |
    Then I verify fields on provider search page with data
      | DISTANCE (mi) | 15 |
    And I click on "SEARCH" button on provider search page
    #2.0. Global Provider Search Results - Display (only) Fields - (At -A- Glance View)
    Then I verify provider search results table fields At A Glance View with data
      | PROVIDER NAME    | is displayed                                                                        |
      | PLAN NAME        | is displayed                                                                        |
      | PROGRAM NAME     | is displayed                                                                        |
      | PROVIDER ADDRESS | validate STREET ADDRESS, CITY, STATE, ZIP, PHONE NUMBER, Accepting New Patient Icon |
      | GROUP NAME       | is displayed                                                                        |
      | DISTANCE         | ascending order                                                                     |
      | SPECIALITY       | is displayed                                                                        |
    # 3.0.  Global Provider Search Results - Display (only) Fields - (Expanded View)
    Then I verify provider serch results table fields At Expanded View with data
      | LANGUAGE          | is displayed |
      | PCP INDICATOR     | is displayed |
      | OB INDICATOR      | is displayed |
      | PROVIDER GENDER   | is displayed |
      | GENDER SERVED     | is displayed |
      | PATIENT AGE RANGE | is displayed |
      | NPI               | is displayed |

  @CP-27755 @crm-regression @ui-pp @sobir
  Scenario: IN-EB Global Provider Search - Age Range Search
    Given I logged into CRM and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    Then I verify dropdown Age Range on provider search page has options
      | 0 - 2    |
      | 0 - 12   |
      | 0 - 17   |
      | 0 - 20   |
      | 0 - 999  |
      | 3 - 999  |
      | 13 - 17  |
      | 13 - 20  |
      | 13 - 999 |
      | 17 - 999 |
      | 21 - 999 |
      | 65 - 999 |

  @CP-24349 @crm-regression @ui-pp @sobir
  Scenario: IN-EB General Provider Search Detail Screen
    Given I logged into CRM and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      | PROVIDER TYPE | MEDICAL |
    When I fill out fields on provider search page with data
      | ZIP | 46208 |
    And I click on "SEARCH" button on provider search page
    # 1.0. Provider Detail Displays Sub-Program
    Then I verivy provider search results at "PROGRAM NAME" column contains cells with values
      | HealthyIndianaPlan |
      | HoosierCareConnect |
      | HoosierHealthwise  |
    # 2.0. (Global Search) Provider Detail Displays Plan
    Then I click on first found provider and verify Provider Details with data
      | PROVIDER DETAILS.PROVIDER NAME                       | is displayed |
      | PROVIDER DETAILS.PROVIDER GENDER                     | is displayed |
      | PROVIDER DETAILS.GENDER SERVED                       | is displayed |
      | PROVIDER DETAILS.PCP INDICATOR                       | is displayed |
      | PROVIDER DETAILS.OB INDICATOR                        | is displayed |
      | PROVIDER DETAILS.PATIENT AGE RANGE                   | is displayed |
      | PROVIDER DETAILS.NPI                                 | is displayed |
      | SPECIALITY                                           | 1 - 10       |
      | LOCATION ASSOCIATED TO THE PROVIDER.PLAN NAME        | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.PROVIDER ADDRESS | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.PHONE NUMBER     | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.FAX NUMBER       | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.EMAIL ADDRESS    | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.HANDICAP         | is displayed |

  @CP-24814 @CP-29680 @crm-regression @ui-pp @mital @IN-EB
  Scenario: CP-24814 IN-EB General Provider Search - Hide Provider Type, Office Hours, Gender Served & Languages
  CP-29680 IN-EB General Provider Search - REVERSING Hide Gender Served
    Given I logged into CRM and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    Then I verify fields on provider search page with data
      # 1.0. Hide Provider Type Search Criterion
      | PROVIDER TYPE | hidden       |
      # 2.0. Hide Gender Served
      | GENDER SERVED | is displayed |
      # 4.0. Hide Languages
      | LANGUAGES     | hidden       |
    When I fill out fields on provider search page with data
      | PLAN NAME | ANTHEM |
    And I click on "SEARCH" button on provider search page
    Then I verify provider serch results table fields At Expanded View with data
      | GENDER SERVED | is displayed |
      | LANGUAGES     | hidden       |
    # 3.0. Hide Office Hours
    Then I click on first found provider and verify Provider Details with data
      | PROVIDER DETAILS.PROVIDER TYPE                   | hidden       |
      | PROVIDER DETAILS.GENDER SERVED                   | is displayed |
      | PROVIDER DETAILS.LANGUAGES                       | hidden       |
      | LOCATION ASSOCIATED TO THE PROVIDER.OFFICE HOURS | hidden       |

  @CP-29262 @crm-regression @ui-pp @mital
  Scenario: Baseline General Provider Search Detail Screen
    Given I logged into CRM and select a project "BLCRM"
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      | PLAN NAME  | AMERIGROUP COMMUNITY CARE |
      | SPECIALTY | Family Practice           |
    And I click on "SEARCH" button on provider search page
    Then I click on first found provider and verify Provider Details with data
      | PROVIDER DETAILS.PROVIDER NAME                       | is displayed |
      | PROVIDER DETAILS.GROUP NAME                          | is displayed |
      | PROVIDER DETAILS.PROVIDER GENDER                     | is displayed |
      | PROVIDER DETAILS.PROVIDER TYPE                       | is displayed |
      | PROVIDER DETAILS.PCP INDICATOR                       | is displayed |
      | PROVIDER DETAILS.OB INDICATOR                        | is displayed |
      | PROVIDER DETAILS.PATIENT AGE RANGE                   | is displayed |
      | PROVIDER DETAILS.NPI                                 | is displayed |
      | LANGUAGES                                            | 1 - 10       |
      | SPECIALITY                                           | 1 - 10       |
      | LOCATION ASSOCIATED TO THE PROVIDER.PLAN NAME        | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.PROVIDER ADDRESS | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.PHONE NUMBER     | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.FAX NUMBER       | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.EMAIL ADDRESS    | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.HANDICAP         | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.OFFICE HOURS     | is displayed |


  @CP-23897 @CP-23897-01 @crm-regression @ui-pp @in-eb @mital
  Scenario: Search using “Contains” instead of “Starts with” logic
    Given I logged into CRM and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      | FIRST NAME | tin |
    And I click on "SEARCH" button on provider search page
    And I verify first name search results contains value and not just starts with "tin"
    And I click on "CANCEL" button on provider search page
    When I fill out fields on provider search page with data
      | LAST NAME | ANN |
    And I click on "SEARCH" button on provider search page
    And I verify Last Name search results contains value and not just starts with "ANN"
    And I click on "CANCEL" button on provider search page
    When I fill out fields on provider search page with data
      | GROUP NAME | UNITY |
    And I click on "SEARCH" button on provider search page
    And I verify Group Name search results contains value and not just starts with "UNITY"


  @CP-23897 @CP-23897-02 @crm-regression @ui-pp @in-eb @mital
  Scenario: Global Provider Search for IN-EB by Program
    Given I logged into CRM and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      | PROGRAM NAME | HealthyIndianaPlan |
    And I click on "SEARCH" button on provider search page
    When I fill out fields on provider search page with data
      | PROGRAM NAME | HoosierCareConnect |
    And I click on "SEARCH" button on provider search page
    When I fill out fields on provider search page with data
      | PROGRAM NAME | HoosierHealthwise |
    And I click on "SEARCH" button on provider search page

  @CP-23897 @CP-23897-03 @crm-regression @ui-pp @in-eb @mital
  Scenario: Global Provider Search by PATIENT AGE RANGE
    Given I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    Then I verify dropdown Age Range on provider search page has options
      | 0 - 120 |
      | 0 - 19  |
      | 20 - 64 |
      | 65+     |


    @CP-11871 @CP-11871-01 @crm-regression @ui-pp @mital
      Scenario:Modification to Provider Search Logic - Plan only searching
      Given I logged into CRM and select a project "BLCRM"
      And I minimize Genesys popup if populates
      And I navigate to Provider Search page
      And I click on "Advanced Search" button on provider search page
      When I fill out fields on provider search page with data
        | PLAN NAME  | AMERIGROUP COMMUNITY CARE |
      And I click on "SEARCH" button on provider search page
      Then I verify provider search results table fields At A Glance View with data
        | PROVIDER ADDRESS | validate STREET ADDRESS, CITY, STATE, ZIP, PHONE NUMBER, Accepting New Patient Icon |


  @CP-11871 @CP-11871-02 @crm-regression @ui-pp @mital
  Scenario:Modification to Provider Search Logic - Plan and Doctor and Location searching
    Given I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      | PLAN NAME  | AMERIGROUP COMMUNITY CARE |
      |SPECIALTY  |Family Practice            |
      | CITY | Marietta           |
      | ZIP   |30060              |
      |DISTANCE (mi)| 5                |
    And I click on "SEARCH" button on provider search page
    Then I verify provider search results table fields At A Glance View with data
      | PROVIDER ADDRESS | validate STREET ADDRESS, CITY, STATE, ZIP, PHONE NUMBER, Accepting New Patient Icon |
      | DISTANCE         | ascending order                                                                     |

  @CP-11871 @CP-11871-03 @crm-regression @ui-pp @mital
  Scenario:Modification to Provider Search Logic - Plan and Location searching
    Given I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      | PLAN NAME  | AMERIGROUP COMMUNITY CARE |
      | CITY | Marietta           |
      | ZIP   |30060              |
      |DISTANCE (mi)| 5                |
    And I click on "SEARCH" button on provider search page
    Then I verify provider search results table fields At A Glance View with data
      | PROVIDER ADDRESS | validate STREET ADDRESS, CITY, STATE, ZIP, PHONE NUMBER, Accepting New Patient Icon |
      | DISTANCE         | ascending order                                                                     |

  @CP-11871 @CP-11871-04 @crm-regression @ui-pp @mital
  Scenario:Modification to Provider Search Logic - Doctor only searching
    Given I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
    |FIRST NAME         | AMY|
    |LAST NAME          | BARFIELD|
    | SPECIALTY        |Family Practice            |
    |LANGUAGES          |ENGLISH                    |
    | NPI               | 1003808619 |
    And I click on "SEARCH" button on provider search page
    Then I verify provider search results table fields At A Glance View with data
      | PROVIDER ADDRESS | validate STREET ADDRESS, CITY, STATE, ZIP, PHONE NUMBER, Accepting New Patient Icon |


  @CP-11871 @CP-11871-05 @crm-regression @ui-pp @mital
  Scenario:Modification to Provider Search Logic - Doctor and Location searching
    Given I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      |FIRST NAME         | FREDDY|
      |LAST NAME          | GATON|
      | SPECIALTY         |Family Practice            |
      |LANGUAGES          |ENGLISH                    |
      | NPI               | 1003896416 |
      | CITY | Roberta           |
      | ZIP   |31078              |
      |DISTANCE (mi)| 5                |
    And I click on "SEARCH" button on provider search page
    Then I verify provider search results table fields At A Glance View with data
      | PROVIDER ADDRESS | validate STREET ADDRESS, CITY, STATE, ZIP, PHONE NUMBER, Accepting New Patient Icon |
      | DISTANCE         | ascending order                                                                     |

  @CP-11871 @CP-11871-06 @crm-regression @ui-pp @mital
  Scenario:Modification to Provider Search Logic - Location only searching
    Given I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      | STREET ADDRESS | 1120 15th St |
      | CITY           | Augusta                   |
      | ZIP            | 30912                         |
      |DISTANCE (mi)   | 5                |
    And I click on "SEARCH" button on provider search page
    Then I verify provider search results table fields At A Glance View with data
      | PROVIDER ADDRESS | validate STREET ADDRESS, CITY, STATE, ZIP, PHONE NUMBER, Accepting New Patient Icon |
      | DISTANCE         | ascending order                                                                     |

  @CP-11871 @CP-11871-07 @crm-regression @ui-pp @mital
  Scenario: Modification to Provider Search Logic-Group provider records by Doctor and Location
    Given I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      |FIRST NAME         | Amy|
      |LAST NAME          | Geer|
    And I click on "SEARCH" button on provider search page
    Then I verify provider search result contains same NPI "1528141553"
    Then I verify provider search result contains different plans for address "951 S Broad"

  @CP-48755 @CP-48755-01 @crm-regression @ui-pp @deepa
  Scenario:UPDATE UI to allow for 100 characters for Displaying Complete Plan Name in Drop-down
    Given I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      | PLAN NAME |CareFirst Community Health Plan DC |
    And I click on "SEARCH" button on provider search page
    Then I verify provider search results table fields At A Glance View with data
      | NAME          | is displayed |
      | PLAN NAME     | is displayed |
      | PROGRAM NAME  | is displayed |
      | PROVIDER TYPE | is displayed |
      | SPECIALTY     | is displayed |
      | LAST UPDATED  | is displayed |







