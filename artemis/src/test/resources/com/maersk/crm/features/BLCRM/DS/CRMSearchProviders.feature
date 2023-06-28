@basicProviderSearch1
Feature: Basic and Advance Search For Providers

  @CP-92 @CP-92-01 @crm-regression @Sean @ui-pp
  Scenario Outline: Filter the search by Provider Name
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps9201" payload "createProvider" for Provider Search
      | providers[0].firstName                           | Andy                     |
      | providers[0].lastName                            | John                      |
      | providers[0].npi                                  | npi::                     |
      | providers[0].effectiveStartDate                  | date::                  |
      | providers[0].groupName                           | AndyJohn                  |
    Then I verify the response status code "200" with creation status "success"
    Then I logged into CRM and click on Provider Search page
    And I searching with first name "Andy" and last name "John"
    And I will be able to filter the results by Provider Name
    Then I have selected to view the provider detail screen
    Examples:
      | projectName |
      |[blank]|

  @CP-92-02  @CP-92   @crm-regression  @Sean @ui-pp
  Scenario Outline: : Search with first name Length Validation
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When  I search by "<FirstName>" parameter
    When I fill out fields on provider search page with data
      | PLAN NAME | AMERIGROUP COMMUNITY CARE |
    Then  I click on the Search button
    And   I verify that results are fetched for the "<FirstName>"
    Examples:
      | FirstName |
      | Andy      |

  @CP-92-03 @CP-92  @crm-regression  @Sean @ui-pp
  Scenario Outline: Serach by Last Name (Length Validation)
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When I search by "<lastName>"
    When I fill out fields on provider search page with data
      | PLAN NAME | AMERIGROUP COMMUNITY CARE |
    Then I click on the Search button
    And Verify that results are fetched for the "<lastName>"
    Examples:
      | lastName |
      | John     |

#  @CP-92-04  @CP-92   @crm-regression  @Sean @ui-pp   #Implementation change
  Scenario Outline: Search by Group Name (Length Validation)
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When I search with groupname "<GroupName>" parameter
    Then I click on the Search button
    And I verify that results are fetched for the group name "<GroupName>"
    Examples:
      | GroupName |
      | AndyJohn     |



  @CP-92-05 @CP-92-06 @CP-92  @crm-regression  @Sean @ui-pp
  Scenario Outline: Search by Phone number (Length Validation) and Search by Plan Name
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When I search by "<PhoneNumber>" Phone
    When I fill out fields on provider search page with data
      | PLAN NAME | AMERIGROUP COMMUNITY CARE |
    Then I click on the Search button
    And I verify results are fetched for the "<PhoneNumber>"
    Examples:
      |  PhoneNumber |
      |  706 625 4410 |

#  @CP-92-06  @CP-92 @crm-regression  @Sean @ui-pp  //Minimun 2 search parameter //Combined with CP-92-05
  Scenario Outline: Search by Plan Name
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When I search by "<PlanName>" PName
    Then I click on the Search button
    And I verify that results are fetched for the "<PlanName>" PName

    Examples:
      |     PlanName|
      |       AMERIGROUP COMMUNITY CARE  |

  @CP-92-07 @CP-92-09  @CP-92  @crm-regression  @Sean @ui-pp # //Combined with CP-92-05
  Scenario Outline: 3.0 Multiple Search Criteria will use ‘AND’ clause
                    7.0 Name fields - Return providers with first name that starts with the text entered
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When  I entered "<FirstName>" and "<LastName>"
    Then  I click on the Search button
    Then I Verify results for "<FirstName>" and "<LastName>"
    Then I will return providers with a name that starts with the text entered in the search "<FirstName>"
    Examples:
      | FirstName | LastName |
      | Andy   | John      |

  @CP-92-08   @CP-92  @crm-regression  @Sean @ui-pp
  Scenario: 6.0 Return only the first 100 provider results found
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When  I entered "An" in FirstName
    When I fill out fields on provider search page with data
      | PLAN NAME | AMERIGROUP COMMUNITY CARE |
    Then  I click on the Search button
    Then I will return only the first 100 provider results found

#  @CP-92-09   @CP-92 @crm-regression  @Sean @ui-pp
  Scenario Outline: 7.0 Name fields - Return providers with first name that starts with the text entered
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When  I entered "<FirstName>" and "<LastName>"
    Then  I click on the Search button
    Then I will return providers with a name that starts with the text entered in the search "<FirstName>"
    Examples:
      | FirstName | LastName |
      | Andy   | John |

  @CP-92-10   @CP-92   @crm-regression  @Sean @ui-pp
  Scenario Outline: 8.0 Plan Name, Provider Type, Specialty, Phone Number - Return providers with the exact match to the criteria entered
    Given I logged into the CRM Application
    Given I am on the search provider screen
    Given I’ve entered data into the "<PlanName>", "<ProviderType>", "<Specialty>", and "<Phone>" field
    Then  I click on the Search button
    Then I will return providers with the exact match to the criteria entered "<PlanName>", "<ProviderType>", "<Specialty>", and "<Phone>"
    Examples:
      | PlanName                    | ProviderType              | Specialty                  | Phone |
      | AMERIGROUP COMMUNITY CARE   | Medical                   | Pediatric Allergy           |   478 746 6662|


  @CP-92-11  @CP-92  @crm-regression  @Sean @ui-pp
  Scenario Outline: 10.0 Search Criteria Entered - Cancel Button Selected
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When  I search by "<FirstName>" parameter
    Then Click at cancel button
    Then the criteria entered will be cleared and I will remain on the same screen "<FirstName>"
    Examples:
      | FirstName |
      |Andy       |

  @CP-92-12    @CP-92 @crm-regression  @Sean @ui-pp
  Scenario Outline: 11.0 Return providers with PCP Indicator = Y
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When Click at Advance Search button
    When  I search by "<FirstName>" parameter
    When  I search by "<PCPIndicator>" pcp
    Then I click on the Search button
    Then I will return providers who have a "<PCPIndicator>" of Y
    Examples:
      | FirstName | PCPIndicator |
      | Andy   |    Yes         |

  @CP-92-13   @CP-92  @crm-regression  @Sean
  Scenario Outline: Search for provider type
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When  I search by "<FirstName>" parameter
    When  I search by providertype "<ProviderType>" parameter
    Then I click on the Search button
    And I verify that results are fetched for the Providertype
    Examples:
      | FirstName | ProviderType |
      | Andy   |    Medical         |

  @CP-92-14   @CP-92   @crm-regression  @Sean @ui-pp
  Scenario Outline: Search for Speciality
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When I fill out fields on provider search page with data
      | PLAN NAME | AMERIGROUP COMMUNITY CARE |
    When I search by speciality "<Specialty>"
    Then I click on the Search button
    And I verify that results are fetched for the speciality "<Specialty>"
    Examples:
      |     Specialty     |
      |  Internal Medicine |

  @CP-93-01  @crm-regression @Sean @CP-93 @ui-pp
  Scenario: 1.0 Navigation
    Given I logged into the CRM Application
    Given I am on the search provider screen
    Then  Verify search screen

  @CP-93-02  @crm-regression @Sean @CP-93 @ui-pp
  Scenario: 2.0 Basic Search Element
    Given I logged into the CRM Application
    Given I am on the search provider screen
    Then  Verify search screen
    Then I am able to enter one or more of the following fields in addition

  @CP-93-03 @CP-93-04 @crm-regression @Sean @CP-93 @crm-smoke @ui-pp
  Scenario Outline: 3.0 Street Address, City, ZIP - return providers with a location association that starts with the text entered
    Given I logged into the CRM Application
    Given I am on the search provider screen
    Given I have entered data into the "<Street Address>","<City>"
    When I return provider search results
    Then Verify Provider address displayed in the search results will match the "<Street Address>" and City or ZIP entered
    Examples:
      | Street Address     | City |
      |   1595 Cleveland Ave       | Atlanta  |

 # @CP-93-04  @crm-regression @Sean @CP-93 @ui-pp
  Scenario Outline: 3.0 Street Address, City, ZIP - return providers with a location association that starts with the text entered
    Given I logged into the CRM Application
    Given I am on the search provider screen
    Given I have entered data into the "<Street Address>","<City>"
    When I return provider search results
    Then Verify Provider address displayed in the search results will match the "<Street Address>" and City or ZIP entered
    Examples:
      | Street Address     | City |
      |   12 7th St       | Auburn  |

  @CP-93-05  @crm-regression @Sean @CP-93 @ui-pp
  Scenario Outline:  4.0 Enable Distance when City or ZIP is entered
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When Verify that distance field should be disable
    Then Enter city "<City>"
    Then Verify that distance field should be enable
    Then Click at cancel button
    When Verify that distance field should be disable
    Then Enter zip "<Zip>"
    Then Verify that distance field should be enable
    Examples:
      | City      |   Zip |
      | Auburn    | 30011 |

  @CP-93-06  @crm-regression @Sean @CP-93 @ui-pp
  Scenario:  5.0 Distance popover text
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When I hover my mouse over the orange icon in the Distance field
    Then I will see the following text: "Use ZIP or City or Street Address with Distance"

  @CP-93-07  @crm-regression @Sean @CP-93 @ui-pp
  Scenario Outline:7.0 Distance searching by single criteria - return providers with location association within the radius of miles entered in the single criteria
    Given I logged into the CRM Application
    Given I am on the search provider screen
    Then Enter city "<City>"
    Then Enter distance "<Distance>"
    When I return provider search results
    Then I will return providers who have an active location association within the radius of miles entered of the City entered "<Distance>"
    Examples:
      | City      |   Distance |
      | Acworth    | 10         |

  #@CP-93-08  @crm-regression @Sean @CP-93--Can be removed
  Scenario Outline:8.0 Distance searching by single criteria - return providers with location association within the radius of miles entered in the single criteria
    Given I logged into the CRM Application
    Given I am on the search provider screen
    Then Enter zip "<Zip>"
    Then Enter distance "<Distance>"
    When I return provider search results
    Then I will return providers who have an active location association within the radius of miles entered of the ZIP entered
    Examples:
      | NPI          |
      | 1104099274  |

  @CP-93-09 @crm-regression @Sean @CP-93 @ui-pp #need fix this script after test issue is fixed from krishna
  Scenario Outline:9.0 Distance searching multiple criteria - return providers with location association within the radius of miles entered from all location criteria
    Given I logged into the CRM Application
    Given I am on the search provider screen
    Then Enter city "<City>"
    Then Enter distance "<Distance>"
    Given Select Zip
    When I return provider search results
    Then I will return providers who have an active location association within the radius of miles entered of the City entered "<Distance>"
    Examples:
      | City      |   Distance |
      | Acworth    | 10         |

  @CP-93-10  @crm-regression @Sean @CP-93 @ui-pp
  Scenario Outline: 10.0 Return only ZIPs mapped to the city entered
    Given I logged into the CRM Application
    Given I am on the search provider screen
    Given Enter city "<City>"
    Given Select Zip
    When I return provider search results
    Then Return "<Zip>" mapped to the city entered
    Examples:
      | City     | Zip    |
      | Auburn   | 30011  |

  @CP-93-11  @crm-regression @Sean @CP-93 @ui-pp
  Scenario Outline: 9.0 Search Criteria Entered - Cancel Button Selected
    Given I logged into the CRM Application
    Given I am on the search provider screen
    Given I have entered street address "<Street Address>"
    Then Click at cancel button
    Then  "<Street Address>" entered will be cleared and I will remain on the same screen
    Examples:
      | Street Address          |
      | 1500 Oglethorpe Ave  |

  @CP-93-12  @crm-regression @Sean @CP-93 @ui-pp
  Scenario Outline: 12.0 Display distance column when distance is used in search
    Given I logged into the CRM Application
    Given I am on the search provider screen
    Then Enter city "<City>"
    Then Enter distance "<Distance>"
    When I return provider search results
    Then I will see Distance as a column
    Examples:
      | City        | Distance |
      | Acworth  | 10       |

  @CP-95-01  @crm-regression @CP-95  @Sean  @ui-pp
  Scenario: 1.0 Navigation
    Given I logged into the CRM Application
    Given I am on the search provider screen
    Given Click at advance search
    Then  Verify additional search area

  @CP-95-02  @crm-regression @Sean @CP-95 @ui-pp
  Scenario: 2.0 Additional search area element
    Given I logged into the CRM Application
    Given I am on the search provider screen
    Given Click at advance search
    Then  Verify additional search area element

  @CP-95-03  @crm-regression @Sean @CP-95 @ui-pp
  Scenario Outline: 3.0 Search by Language
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When I fill out fields on provider search page with data
      | PLAN NAME | AMERIGROUP COMMUNITY CARE |
    Given Click at advance search
    Then  User select language "<Language>"
    Then  Click on search button
    Then  Click on arrow to expand the result
    Then Verify that results are retrieved with respect to language "<Language>"
    Examples:
      | Language |
      | ENGLISH  |

  @CP-95-04  @crm-regression @Sean @CP-95 @ui-pp
  Scenario Outline: 4.0 Search by PCI indicator
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When I fill out fields on provider search page with data
      | PLAN NAME | AMERIGROUP COMMUNITY CARE |
    Given Click at advance search
    And   User select PCI Indicator "<PCI Indicator>"
    Then  Click on search button
    Then  Click on arrow to expand the result
    Then Verify that results are retrieved with respect to pcpindicator "<PCI Indicator>"
    Examples:
      | PCI Indicator |
      |      Yes      |

  @CP-95-05  @crm-regression @Sean @CP-95 @ui-pp
  Scenario Outline: 5.0 Search by OB Indicator
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When I fill out fields on provider search page with data
      | PLAN NAME | AMERIGROUP COMMUNITY CARE |
    Given Click at advance search
    And   User select OB Indicator "<OB Indicator>"
    Then  Click on search button
    Then  Click on arrow to expand the result
    Then Verify that results are retrieved with respect to obindicator "<OB Indicator>"
    Examples:
      | OB Indicator |
      |      No      |

  @CP-95-06 @crm-regression @Sean @CP-95 @ui-pp
  Scenario Outline: 6.0 Return only providers with Accept New Patient
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When I fill out fields on provider search page with data
      | PLAN NAME | AMERIGROUP COMMUNITY CARE |
    Given Click at advance search
#    Then  Verify Default Value of accepting new patient
    And   User select Accept new patient "<Accepting New Patients>"
    Then  Click on search button
    Then  Click on arrow to expand the result
    Then Verify that results are retrieved with respect to accept new patient "<Accepting New Patients>"
    Examples:
      | Accepting New Patients |
      |       Yes               |

  @CP-95-07 @crm-regression @Sean @CP-95  @ui-pp
  Scenario Outline: 7.0 Return result with respect to Gender Served
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When I fill out fields on provider search page with data
      | PLAN NAME | AMERIGROUP COMMUNITY CARE |
    Given Click at advance search
    And   User select gender served "<Gender Served>"
    Then  Click on search button
    Then  Click on arrow to expand the result
    Then Verify that results are retrieved with respect to gender served "<Gender Served>"
    Examples:
      | Gender Served |
      |     Both      |

  @CP-95-08  @crm-regression @Sean @CP-95 @ui-pp
  Scenario Outline: 8.0 Return only providers with respect to Provider Gender
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When I fill out fields on provider search page with data
      | PLAN NAME | AMERIGROUP COMMUNITY CARE |
    Given Click at advance search
    And   User select provider gender "<Provider Gender>"
    Then  Click on search button
    Then  Click on arrow to expand the result
    Then Verify that results are retrieved with respect to provider gender "<Provider Gender>"
    Examples:
      | Provider Gender |
      |     Male        |

#  @CP-95-09  @crm-regression @Sean @CP-95 @ui-pp                       -----------Implementation change-- Provider address doesn't contain County Name anymore
  Scenario Outline: 9.0 Return only providers for Searched County
    Given I logged into the CRM Application
    Given I am on the search provider screen
    Given Click at advance search
    And   User select county "<COUNTY>"
    Then  Click on search button
    Then  Click on arrow to expand the result
    Then Verify that results are retrieved with respect to county "<COUNTY>"
    Examples:
      | COUNTY |
      | UNION  |

  @CP-95-10 @crm-regression @Sean @CP-95  @ui-pp
  Scenario Outline: 10.0 Return only providers for Searched County
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When I fill out fields on provider search page with data
      | PLAN NAME | AMERIGROUP COMMUNITY CARE |
    Given Click at advance search
    And   User select STATE "<STATE>"
    Then  Click on search button
    Then Verify that results are retrieved with respect to state "<STATE>"
    Examples:
      | STATE |
      |   GA  |

#  @CP-95-11 @crm-regression @Sean @CP-95 @ui-pp                --->Implementation change
#  Scenario Outline: 11.0 Return only providers for the min age
#    Given I logged into the CRM Application
#    Given I am on the search provider screen
#    Given Click at advance search
#    And   User enter min age "<MinAge>"
#    Then  Click on search button
#    Then  Click on arrow to expand the result
#    Then Verify that results are retrieved with respect to min age "<MinAge>"
#
#    Examples:
#      | MinAge |
#      | 20     |
#
##  @CP-95-12  @crm-regression @Sean @CP-95 @ui-pp                 --->Implementation change
#  Scenario Outline: 4.0 Return only providers with max age
#    Given I logged into the CRM Application
#    Given I am on the search provider screen
#    Given Click at advance search
#    And   User enter max age "<MaxAge>"
#    Then  Click on search button
#    Then  Click on arrow to expand the result
#    Then Verify that results are retrieved with respect to max age "<MaxAge>"
#
#    Examples:
#      | MaxAge |
#      | 20  |

  @CP-95-13  @crm-regression @Sean @CP-95 @ui-pp
  Scenario Outline: 13.0 Return only providers with NPI searched
    Given I logged into the CRM Application
    Given I am on the search provider screen
    When I fill out fields on provider search page with data
      | PLAN NAME | AMERIGROUP COMMUNITY CARE |
    Given Click at advance search
    And   User enter npi "<NPI>"
    Then  Click on search button
    Then  Click on arrow to expand the result
    Then Verify that results are retrieved with respect to npi "<NPI>"
    Examples:
      | NPI          |
      | 1861435349  |

  @CP-95-14  @crm-regression @Sean @CP-95 @ui-pp
  Scenario Outline: 14.0 Minimum of 2 search criteria required
    Given I logged into the CRM Application
    Given I am on the search provider screen
    Given Click at advance search
    Then  User select language "<Language>"
    And   User select PCI Indicator "<PCI Indicator>"
    Then  User select provider gender "<Provider Gender>"
    Then  Click on search button
    Then  Click on arrow to expand the result
    Then Verify that results are retrieved with respect to "<Language>", "<PCI Indicator>", "<Provider Gender>"
    Examples:
      | Language | PCI Indicator | Provider Gender |
      | ENGLISH  |      Yes      |    Male         |

  @CP-95-15  @crm-regression @Sean @CP-95 @ui-pp
  Scenario Outline: 16.0 Cancel Button
    Given I logged into the CRM Application
    Given I am on the search provider screen
    Given Click at advance search
    And   User enter npi "<NPI>"
    Then Click at cancel button
    Then Verify that search is clear "<NPI>"
    Examples:
      | NPI          |
      | 1104099274   |

  @CP-95-16  @crm-regression @Sean @CP-95 @ui-pp
  Scenario Outline: 15.0 Minimum of 2 search criteria required
    Given I logged into the CRM Application
    Given I am on the search provider screen
    Given Click at advance search
    And   User enter npi "<NPI>"
    Then  Click on search button
    Then Verify that warning pop up with minimum 2 search parameters required
    Examples:
      | NPI          |
      | 1104099274   |

  @CP-10580 @CP-10580-01 @crm-regression @ui-pp @mital
  Scenario Outline: Verify distance value to be one digit after decimal point in address based provider search
    Given I logged into the CRM Application
    Given I am on the search provider screen
    Then Enter city "<City>"
    Then Enter distance "<Distance>"
    When I return provider search results
    Then I will see Distance as a column
    Then I verify provider search results table fields At A Glance View with data
      | DISTANCE         | ascending order   |
    Then I verify value in Distance column rounded to one digit after decimal point
    Examples:
      | City        | Distance |
      | Acworth  | 10       |

  @CP-11194 @CP-11194-01 @crm-regression @ui-pp @planProvider @mital
  Scenario: Verify Provider Search Results Warning Message Contains Correct Number of Displayed Providers
    Given I logged into the CRM Application
    Given I am on the search screen
    When  I entered Char "an" in FirstName
    When I fill out fields on provider search page with data
      | PLAN NAME | AMERIGROUP COMMUNITY CARE |
    Then  I click on Search button
    Then I will see a message "Returning 100 of X providers found, use more search criteria for more precise results."
    And I verify warning message shows correct number of Provider Records
    When I will get the Authentication token for "BLCRM" in "CRM"
    Then I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.firstName | an |
    Then I verify the response status code "200" with creation status "success"
    Then I verify same number of records from Warning Message got displayed

  @CP-11181 @CP-11181-01 @crm-regression @ui-pp @planProvider @mital
  Scenario: Verify change Name searches to default the search using "Starts with" logic - First Name and Last Name
    Given I logged into the CRM Application
    Given I am on the search screen
    And I click on "Advanced Search" button on provider search page
    Then I verify fields on provider search page with data
      | FIRST NAME          |[blank]|
      | LAST NAME           |[blank]|
      | GROUP NAME          |[blank]|
      | PHONE NUMBER        |[blank]|
      | PLAN NAME           |[blank]|
      | PROVIDER TYPE       |[blank]|
      | HANDICAP ACCESSIBLE |[blank]|
    Then I verify provider type dropdown has below options
      | Medical |
      | Dental  |
    And I select "Medical" option from provider type dropdown
    And I set Search By operator to "Contains"
    When I fill out fields on provider search page with data
      | FIRST NAME | AN |
      | LAST NAME | IN |
    And I click on "SEARCH" button on provider search page
    Then I verify provider search results table fields At A Glance View with data
      | NAME             | is displayed                                                                        |
      | PROVIDER ADDRESS | validate STREET ADDRESS, CITY, STATE, ZIP, PHONE NUMBER, Accepting New Patient Icon |
      | PLAN NAME        | is displayed                                                                        |
      | PROGRAM NAME     | is displayed                                                                        |
      | PROVIDER TYPE    | is displayed                                                                        |
      | SPECIALTY       | is displayed                                                                        |
      | LAST UPDATED     | is displayed                                                                        |
    And I verify below are the options to display more providers in a page
      | show 20 |
      | show 40 |
      | show 60 |
    Then I select "60" as the number of returned providers to be displayed on each page
    And I verify provider name values "Contains" search parameters "AN" in First Name and "IN" in Last Name
    And I click on "CANCEL" button on provider search page

    And I set Search By operator to "Start with"
    When I fill out fields on provider search page with data
      | FIRST NAME | SA |
      | LAST NAME | BA |
    And I click on "SEARCH" button on provider search page
    And I verify provider name values "Start with" search parameters "SA" in First Name and "BA" in Last Name

  @CP-11181 @CP-11181-02 @crm-regression @ui-pp @planProvider @mital
  Scenario: Verify change Name searches to default the search using "Starts with" logic - Group Name
    Given I logged into the CRM Application
    Given I am on the search screen
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      | PLAN NAME | AMERIGROUP COMMUNITY CARE |
    And I set Search By operator to "Contains"
    When I fill out fields on provider search page with data
      | GROUP NAME | HOSPITAL |
    And I click on "SEARCH" button on provider search page
    Then I verify provider search results table fields At A Glance View with data
      | NAME             | is displayed                                                                        |
      | PLAN NAME        | is displayed                                                                        |
      | PROGRAM NAME     | is displayed                                                                        |
      | PROVIDER TYPE    | is displayed                                                                        |
      | SPECIALTY       | is displayed                                                                        |
      | LAST UPDATED     | is displayed                                                                        |
    And I verify below are the options to display more providers in a page
      | show 20 |
      | show 40 |
      | show 60 |
    Then I select "60" as the number of returned providers to be displayed on each page
    And I verify Provider Name i.e. Group name field "Contains" value "HOSPITAL"
    And I click on "CANCEL" button on provider search page

    And I set Search By operator to "Start with"
    When I fill out fields on provider search page with data
      | PLAN NAME | AMERIGROUP COMMUNITY CARE |
    When I fill out fields on provider search page with data
      | GROUP NAME | Parkview |
    And I click on "SEARCH" button on provider search page
    And I verify Provider Name i.e. Group name field "Start with" value "Parkview"





