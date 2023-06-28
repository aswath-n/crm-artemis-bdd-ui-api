@API-Provider_Search
Feature: API-CRM: Provider Search

  @API-CP-92 @API-CP-92-01 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search with one field-With First Name
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "92-1" with data
      | providers[0].firstName | London                    |
      | providers[0].lastName  | John                      |
      | providers[0].genderCd  | M                         |
      | providers[0].npi       | npi::                     |
      | providers[0].groupName | AndyJohn                  |
      | providers[0].planName  | AMERIGROUP COMMUNITY CARE |
    Then I verify the response status code "200" with creation status "success"
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.firstName | 92-1.providers[0].firstName |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | firstName | 92-1.providers[0].firstName |
    Examples:
      | projectName |
      |[blank]|


  @API-CP-92 @API-CP-92-02 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search with one field-With Last Name
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "92-2" with data
      | providers[0].firstName | Andy                      |
      | providers[0].lastName  | London                    |
      | providers[0].genderCd  | M                         |
      | providers[0].npi       | npi::                     |
      | providers[0].groupName | AndyJohn                  |
      | providers[0].planName  | AMERIGROUP COMMUNITY CARE |
    Then I verify the response status code "200" with creation status "success"
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.lastName | 92-2.providers[0].lastName |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | lastName | 92-2.providers[0].lastName |
    Examples:
      | projectName |
      |[blank]|


  @API-CP-92 @API-CP-92-03 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search with one field-With Group Name
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "92-3" with data
      | providers[0].firstName | Andy                      |
      | providers[0].lastName  | John                      |
      | providers[0].genderCd  | M                         |
      | providers[0].npi       | npi::                     |
      | providers[0].groupName | London                    |
      | providers[0].planName  | AMERIGROUP COMMUNITY CARE |
    Then I verify the response status code "200" with creation status "success"
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.groupName | 92-3.providers[0].groupName |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | groupName | 92-3.providers[0].groupName |
    Examples:
      | projectName |
      |[blank]|


  @API-CP-92 @API-CP-92-04 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search with one field-With Phone Number
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "92-4" with data
      | providers[0].firstName                                      | Andy                      |
      | providers[0].lastName                                       | John                      |
      | providers[0].genderCd                                       | M                         |
      | providers[0].npi                                            | npi::                     |
      | providers[0].providerAddress[0].phoneNumbers[0].phoneNumber | phone::                   |
      | providers[0].planName                                       | AMERIGROUP COMMUNITY CARE |
    Then I verify the response status code "200" with creation status "success"
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.phoneNumber | 92-4.providers[0].providerAddress[0].phoneNumbers[0].phoneNumber |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | providers[0].providerAddress[0].phoneNumbers[0].phoneNumber | 92-4.providers[0].providerAddress[0].phoneNumbers[0].phoneNumber |
    Examples:
      | projectName |
      |[blank]|


  @API-CP-92 @API-CP-92-05 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search with one field-With PLAN NAME
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "92-5" with data
      | providers[0].firstName | Andy                      |
      | providers[0].lastName  | John                      |
      | providers[0].genderCd  | M                         |
      | providers[0].npi       | npi::                     |
      | providers[0].groupName | London                    |
      | providers[0].planName  | AMERIGROUP COMMUNITY CARE |
    Then I verify the response status code "200" with creation status "success"
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.planName | 92-5.providers[0].planName |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | planName | 92-5.providers[0].planName |
    Examples:
      | projectName |
      |[blank]|


  @API-CP-92 @API-CP-92-06 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search with one field-With Provider Type
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "" with data
      | providers[0].firstName | Andy                      |
      | providers[0].lastName  | John                      |
      | providers[0].genderCd  | M                         |
      | providers[0].npi       | npi::                     |
      | providers[0].groupName | London                    |
      | providers[0].planName  | AMERIGROUP COMMUNITY CARE |
    Then I verify the response status code "200" with creation status "success"
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.providerTypeCd | Medical |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | providerTypeCd | Medical |
    Examples:
      | projectName |
      |[blank]|


  @API-CP-92 @API-CP-92-07 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search with one field-With Search by Specialty
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "92-6" with data
      | providers[0].firstName | Andy                      |
      | providers[0].lastName  | John                      |
      | providers[0].genderCd  | M                         |
      | providers[0].npi       | npi::                     |
      | providers[0].groupName | London                    |
      | providers[0].planName  | AMERIGROUP COMMUNITY CARE |
    Then I verify the response status code "200" with creation status "success"
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.specialityCd | 92-6.providers[0].providerSpeciality[0].specialityCd |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | specialityCd | 92-6.providers[0].providerSpeciality[0].specialityCd |
    Examples:
      | projectName |
      |[blank]|


  @API-CP-92 @API-CP-92-08 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search with Multi parameter Search Criterias
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "92-7" with data
      | providers[0].firstName | Paris                     |
      | providers[0].lastName  | London                    |
      | providers[0].genderCd  | M                         |
      | providers[0].npi       | npi::                     |
      | providers[0].groupName | London                    |
      | providers[0].planName  | AMERIGROUP COMMUNITY CARE |
    Then I verify the response status code "200" with creation status "success"
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.firstName    | 92-7.providers[0].firstName                          |
      | providerSearch.lastName     | 92-7.providers[0].lastName                           |
      | providerSearch.planName     | 92-7.providers[0].planName                           |
      | providerSearch.specialityCd | 92-7.providers[0].providerSpeciality[0].specialityCd |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | firstName    | 92-7.providers[0].firstName                          |
      | lastName     | 92-7.providers[0].lastName                           |
      | planName     | 92-7.providers[0].planName                           |
      | specialityCd | 92-7.providers[0].providerSpeciality[0].specialityCd |
    Examples:
      | projectName |
      |[blank]|


  @API-CP-92 @API-CP-92-09 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search results return the 100 records
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.planName | AMERIGROUP COMMUNITY CARE |
    Then I verify the response status code "200" with creation status "success"
    And Verify the 100 record results are returning in the response
    Examples:
      | projectName |
      |[blank]|


  @API-CP-92 @API-CP-92-10 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search with first letter of first name
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.firstName | A |
    Then I verify the response status code "200" with creation status "success"
    And Verify response First names start with letter "A"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-92 @API-CP-92-11 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search with Fist Name first letter parameter and PCP default
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.firstName | A |
      | providerSearch.pcpFlag   | Y |
    Then I verify the response status code "200" with creation status "success"
    And Verify provider details PCP is "Y"
    And Verify response First names start with letter "A"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-95 @API-CP-95-01 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search by Language (AutoComplete)
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for Search Provider by Language
    Then User send Api call with payload "empty" for search provider
      | languagetypeDescription | Eng |
    Then I verify the response status code "200" with creation status "success"
    And Verify response language type Description is "ENGLISH"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-95 @API-CP-95-02 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search by OB indicator (Drop down)
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.acceptObInd | false |
    Then I verify the response status code "200" with creation status "success"
    And  Verify that "p.providerSearch.acceptObInd == false"
    And  Verify that "c.providerSearchResponse.content[0].acceptObInd == false"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-95 @API-CP-95-03 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search by Accept New Patients
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.acceptNewPatientsInd | true |
    Then I verify the response status code "200" with creation status "success"
    And  Verify that "c.providerSearchResponse.content[0].acceptNewPatientsInd == true"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-95 @API-CP-95-04 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search by Gender Servered
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.genderCd | M |
    Then I verify the response status code "200" with creation status "success"
    And  Verify that "c.providerSearchResponse.content[0].genderCd == M"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-95 @API-CP-95-05 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search by State
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.state | GA |
    Then I verify the response status code "200" with creation status "success"
    And  Verify that "c.providerSearchResponse.content[0].providerAddress[0].state == GA"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-95 @API-CP-95-06 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search by Patient Min Age and Max Age
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.ageLowLimit  | int::0  |
      | providerSearch.ageHighLimit | int::16 |
    Then I verify the response status code "200" with creation status "success"
    And  Verify that "c.providerSearchResponse.content[0].ageLowLimit >= 0"
    And  Verify that "c.providerSearchResponse.content[0].ageHighLimit <= 16"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-95 @API-CP-95-07 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search by Specialty and Provider Type
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.providerTypeCd | Medical |
      | providerSearch.specialityCd   | 072     |
    Then I verify the response status code "200" with creation status "success"
    And I verify speciality "072" and provider type "Medical"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-93 @API-CP-93-01 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search with Street Address
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for search provider
      | providers[0].firstName                       | David              |
      | providers[0].lastName                        | Wonder             |
      | providers[0].npi                             | npi::              |
      | providers[0].providerAddress[0].addressLine1 | 800 n Michigan ave |
      | providers[0].providerAddress[0].city         | Chicago            |
      | providers[0].providerAddress[0].zipCode      | 60611              |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.addressLine1 | 800 n Michigan ave |
      | providerSearch.zipCode      | 60611              |
    Then I verify the response status code "200" with creation status "success"
    And  Verify that "c.providerSearchResponse.content[0].providerAddress[0].addressLine1 == 800 n Michigan ave"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-93 @API-CP-93-02 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search with Street Address, City and ZipCode
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for search provider
      | providers[0].firstName                       | David              |
      | providers[0].lastName                        | Wonder             |
      | providers[0].npi                             | npi::              |
      | providers[0].providerAddress[0].addressLine1 | 800 n Michigan ave |
      | providers[0].providerAddress[0].city         | Chicago            |
      | providers[0].providerAddress[0].zipCode      | 60611              |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.city         | Chicago            |
      | providerSearch.addressLine1 | 800 n Michigan ave |
      | providerSearch.zipCode      | 60611              |
    Then I verify the response status code "200" with creation status "success"
    And  Verify response for Search with "800 n Michigan ave", "Chicago" and "60611"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-93 @API-CP-93-03 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search with City
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.city | Atlanta |
    Then I verify the response status code "200" with creation status "success"
    And Verify that "c.providerSearchResponse.content[0].providerAddress[0].city == Atlanta"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-93 @API-CP-93-04 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search with City and Distance
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.state     | GA |
      | providerSearch.city     | Acworth |
      | providerSearch.distance | 10      |
    Then I verify the response status code "200" with creation status "success"
    And Verify that "c.providerSearchResponse.content[0].providerAddress[0].city == Acworth"
    And Verify provider city distance
    Examples:
      | projectName |
      |[blank]|

#Distance searching with zipCode returning null for the distance
  @API-CP-93 @API-CP-93-05 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Distance searching by single criteria - return providers location within the radius of miles
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.zipCode  | 30101 |
      | providerSearch.distance | 30    |
      | providerSearch.state    | GA    |
    Then I verify the response status code "200" with creation status "success"
    And Verify that "c.providerSearchResponse.content[0].providerAddress[0].normalizedPhysicalAddress.zipCode == 30101"
    And Verify provider city distance within 30 miles
    Examples:
      | projectName |
      |[blank]|


  @API-CP-93 @API-CP-93-06 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Distance searching multiple criteria - return providers location within the radius of miles
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.city     | Acworth |
      | providerSearch.zipCode  | 30101   |
      | providerSearch.distance | 10      |
      | providerSearch.state     | GA |
    Then I verify the response status code "200" with creation status "success"
    And Verify that "c.providerSearchResponse.content[0].providerAddress[0].normalizedPhysicalAddress.zipCode == 30101"
    And Verify that "c.providerSearchResponse.content[0].providerAddress[0].normalizedPhysicalAddress.city == Acworth"
    And Verify provider city distance within 10 miles
    Examples:
      | projectName |
      |[blank]|


  @API-CP-93 @API-CP-93-07 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Return only ZIPs mapped to the city entered
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.city | Acworth |
    Then I verify the response status code "200" with creation status "success"
    And Verify that "c.providerSearchResponse.content[0].providerAddress[0].normalizedPhysicalAddress.zipCode == 30101"
    And Verify that "c.providerSearchResponse.content[0].providerAddress[0].normalizedPhysicalAddress.city == Acworth"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-8684 @API-CP-8684-01 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search with FirstName,LastName,Street Address, City ,ZipCode,npi,gender,planName
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for search provider
      | providers[0].firstName                       | David                     |
      | providers[0].lastName                        | Wonder                    |
      | providers[0].npi                             | npi::                     |
      | providers[0].providerAddress[0].addressLine1 | 4791 S Main St            |
      | providers[0].providerAddress[0].city         | Acworth                   |
      | providers[0].providerAddress[0].state        | GA                        |
      | providers[0].providerAddress[0].zipCode      | 30101                     |
      | providers[0].planCode                        | 85                        |
      | providers[0].planName                        | AMERIGROUP COMMUNITY CARE |
      | providers[0].genderCd                        | M                         |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.firstName    | David                   |
      | providerSearch.lastName     | Wonder                  |
      | providerSearch.city         | Acworth                 |
      | providerSearch.addressLine1 | 4791 S Main St          |
      | providerSearch.zipCode      | 30101                   |
      | providerSearch.distance     | 10                      |
      | providerSearch.state        | GA                      |
      | providerSearch.npi          | p.providers[0].npi      |
      | providerSearch.genderCd     | p.providers[0].genderCd |
      | providerSearch.planName     | p.providers[0].planName |
    Then I verify the response status code "200" with creation status "success"
    And Verify provider city distance within 10 miles
    And Verify response for Provider Search with multi parameters
    Examples:
      | projectName |
      |[blank]|


  @API-CP-8684 @API-CP-8684-02 @API-PP-Regression @API-PP @kamil
  Scenario Outline: Search with FirstName,LastName,Full Address,npi
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for search provider
      | providers[0].firstName                           | Colin                     |
      | providers[0].lastName                            | Dave                      |
      | providers[0].npi                                 | npi::                     |
      | providers[0].providerAddress[0].addressLine1     | 4791 S Main St            |
      | providers[0].providerAddress[0].city             | Acworth                   |
      | providers[0].providerAddress[0].state            | GA                        |
      | providers[0].providerAddress[0].zipCode          | 30101                     |
      | providers[0].planCode                            | 85                        |
      | providers[0].planName                            | AMERIGROUP COMMUNITY CARE |
      | providers[0].genderCd                            | M                         |
      | providers[0].providerLanguages[0].languageTypeCd | TA                        |
      | providers[0].ageLowLimit                         | int::0                    |
      | providers[0].ageHighLimit                        | int::18                   |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.firstName    | Colin              |
      | providerSearch.lastName     | Dave               |
      | providerSearch.city         | Acworth            |
      | providerSearch.addressLine1 | 4791 S Main St     |
      | providerSearch.zipCode      | 30101              |
      | providerSearch.npi          | p.providers[0].npi |
      | providerSearch.ageLowLimit  | int::0             |
      | providerSearch.ageHighLimit | int::18            |
    Then I verify the response status code "200" with creation status "success"
    And  Verify that "c.providerSearchResponse.content[0].ageLowLimit == 0"
    And  Verify that "c.providerSearchResponse.content[0].ageHighLimit == 18"
    Then Verify response return provider details
    Examples:
      | projectName |
      |[blank]|


  @API-CP-5351 @API-CP-5351-02 @API-CRM @API-PP-Regression @API-PP @kamil @plan-manag-ms-PP @API-CRM-SmokeTest
  Scenario Outline: Verify all payload for Provider Search APi response
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    And User send Api call with name "ps" payload "createProvider" for Provider Search
      | providers[0].firstName                           | Colin                     |
      | providers[0].lastName                            | Dave                      |
      | providers[0].npi                                 | npi::                     |
      | providers[0].providerAddress[0].addressLine1     | 4791 S Main St            |
      | providers[0].providerAddress[0].city             | Acworth                   |
      | providers[0].providerAddress[0].state            | GA                        |
      | providers[0].providerAddress[0].zipCode          | 30101                     |
      | providers[0].planCode                            | 85                        |
      | providers[0].planName                            | AMERIGROUP COMMUNITY CARE |
      | providers[0].genderCd                            | M                         |
      | providers[0].providerLanguages[0].languageTypeCd | TA                        |
      | providers[0].ageLowLimit                         | int::0                    |
      | providers[0].ageHighLimit                        | int::18                   |
      | providers[0].effectiveStartDate                  | date::                    |
      | providers[0].ssn                                 | ssn::                     |
      | providers[0].fein                                | fein::                    |
      | providers[0].createdOn                           | dtt::                     |
      | providers[0].updatedOn                           | dtt::                     |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.firstName    | Colin               |
      | providerSearch.lastName     | Dave                |
      | providerSearch.city         | Acworth             |
      | providerSearch.addressLine1 | 4791 S Main St      |
      | providerSearch.zipCode      | 30101               |
      | providerSearch.npi          | ps.providers[0].npi |
      | providerSearch.ageLowLimit  | int::0              |
      | providerSearch.ageHighLimit | int::18             |
    Then I verify the response status code "200" with creation status "success"
    And  Verify that "c.providerSearchResponse.content[0].ageLowLimit == 0"
    And  Verify that "c.providerSearchResponse.content[0].ageHighLimit == 18"
    Then Verify response return provider details
    Examples:
      | projectName |
      |[blank]|


  @API-CP-9679 @API-CP-9679-01 @API-CP-1012 @API-CP-1012-02 @API-PP-Regression @API-PP @kamil @API-CRM-SmokeTest
  Scenario Outline: Verify Standardize and Geo Code Provider Address
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for search provider
      | providers[0].npi                             | npi::          |
      | providers[0].providerAddress[0].addressLine1 | 4791 S Main St |
      | providers[0].providerAddress[0].addressLine2 | 5555 S Main St |
      | providers[0].providerAddress[0].city         | Acworth        |
      | providers[0].providerAddress[0].state        | GA             |
      | providers[0].providerAddress[0].zipCode      | 30101          |
    Then I verify the response status code "200" with creation status "success"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.npi | p.providers[0].npi |
    Then I verify the response status code "200" with creation status "success"
    And Verify response Standardize and Geo Code for Provider Search
    Examples:
      | projectName |
      |[blank]|

 # @API-CP-1012 @API-CP-1012-01 @API-PP-Regression @API-PP @aswath            ---------------------->Implementation Changes
  Scenario Outline: Verify if location is not found Geo Code process Provider Address
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for search provider
      | providers[0].npi                             | npi::    |
      | providers[0].providerAddress[0].addressLine1 | southend |
      | providers[0].providerAddress[0].addressLine2 | 5 tre    |
      | providers[0].providerAddress[0].city         | Acw      |
      | providers[0].providerAddress[0].zipCode      | 3        |
    Then I verify the response status code "404" with creation status "fail"
    Examples:
      | projectName |
      |[blank]|


  @API-PP-Regression @API-PP @aswath @api-smoke-devops
  Scenario Outline: Search with one field-With First Name
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.firstName | Andy |
    Then I verify the response status code "200" with creation status "success"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-23897 @API-CP-23897-01 @mital @API-PP-Regression @API-PP @in-eb
  Scenario:   Search using “Contains” instead of “Starts with” logic
    When I will get the Authentication token for "IN-EB" in "CRM"
    Then I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.firstName | TIN |
    Then I verify the response status code "200" with creation status "success"
    Then Verify response return first name contains "TIN"
    And I create a request payload with data for search field values and post
      | providerSearch.lastName | ANN |
    Then I verify the response status code "200" with creation status "success"
    Then Verify response return last name contains "ANN"
    And I create a request payload with data for search field values and post
      | providerSearch.groupName | UNITY |
    Then I verify the response status code "200" with creation status "success"
    Then Verify response return group name contains "UNITY"

  @API-CP-23897 @API-CP-23897-02 @mital @API-PP-Regression @API-PP @in-eb
  Scenario:   Global Provider Search for IN-EB by Program
    When I will get the Authentication token for "IN-EB" in "CRM"
    Then I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.subProgramTypeCd | HoosierHealthwise |
    Then I verify the response status code "200" with creation status "success"
    And I create a request payload with data for search field values and post
      | providerSearch.subProgramTypeCd | HealthyIndianaPlan |
    Then I verify the response status code "200" with creation status "success"
    And I create a request payload with data for search field values and post
      | providerSearch.subProgramTypeCd | HoosierCareConnect |
    Then I verify the response status code "200" with creation status "success"

  @API-CP-23897 @API-CP-23897-03 @mital @API-PP-Regression @API-PP @in-eb
  Scenario:   Global Provider Search for IN-EB by Program
    When I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated enum get api for "ENUM_AGE_RANGE" and "plan"
    And I run enum get api
    Then I Verify response return age ranges
      |0 - 120|
      |0 - 19|
      |20 - 64|
      |65+|

  @API-CP-22230 @API-CP-22230-01 @ineb-tm-Regression @mital @API-PP-Regression @API-PP
  Scenario:  Verify Service Region Configuration file load
    When I will get the Authentication token for "IN-EB" in "Tenant Manager"
    Given I initiated Plan Management for service region API
    Given I get the jwt token
    When I upload the service regions file "Region_Configuration_INEB.xlsx" with fileStatus "success"
    Then I verify the file upload message "Upload Successful - Please Upload Plan File"
    Given I initiated Plan Management for Plan API
    When I upload the plan regions file "Plan Config INEB" with fileStatus "Upload Successful"
    Given I initiated get Service Delivery Areas via API
    When I run get Service Delivery Areas
    Then I can verify get Service Delivery Areas API response is not empty
    Given I initiated get counties via API
    When I run get county name
    Then I can verify get Counties API response is not empty

  @API-CP-22230 @API-CP-22230-02 @ineb-tm-Regression @mital @API-PP-Regression @API-PP
  Scenario:  Verify Plan Configuration file load
    When I will get the Authentication token for "IN-EB" in "Tenant Manager"
    Given I initiated Plan Management for service region API
  # Given I get the jwt token
    When I upload the service regions file "Region_Configuration_INEB.xlsx" with fileStatus "success"
    Then I verify the file upload message "Upload Successful - Please Upload Plan File"
    Given I initiated Plan Management for Plan API
    When I upload the plan regions file "Plan Config INEB" with fileStatus "Upload Successful"
    Given I initiated get plan name via API
    When I run get Plan Names
    Then I can verify get Plan Name API response is not empty
    Then I can verify plan code is not empty
    Given I initiated get Service Delivery Areas via API
    When I run get Service Delivery Areas
    Then I can verify get Service Delivery Areas API response is not empty
    Given I initiated get program type via API
    When I run get Program Type
    Then I can verify get Program Type API response is not empty
    Given I initiated sub get program type via API
    When I can provide details with following information to get sub program type
      | serviceRegion |  projectName |
      | programType   |[blank]|
    When I run post sub Program Type
    Then I can verify get Sub Program Type API response is not empty
    Given I initiated get counties via API
    When I run get county name
    Then I can verify get Counties API response is not empty

  @API-CP-22230 @API-CP-22230-03 @ineb-tm-Regression @mital @API-PP-Regression @API-PP
  Scenario: Verify Plan Configuration file load expectations (individual record)
    When I will get the Authentication token for "IN-EB" in "Tenant Manager"
    Given I initiated Plan Management for service region API
#   Given I get the jwt token
    When I upload the service regions file "Region_Configuration_INEB.xlsx" with fileStatus "success"
    Then I verify the file upload message "Upload Successful - Please Upload Plan File"
    Given I initiated Plan Management for Plan API
    When I upload the plan regions file "Plan Config INEB" with fileStatus "Upload Successful"
    Given I initiated Plan Management for Plan Search
    And User send Api call with payload "planSearch" for Plan search
      | planSearch.planName | ANTHEM |
    And I verify the response status code 200 and status "success"
    Given I initiated Plan Management for getting plan details
    And User send Api call with payload "getplandetails" for getting plan details
      | getplandetails.planName | ANTHEM |
    And I verify the response status code 200 and status "success"
    Then I verify the plan details response
      |planName                     | ANTHEM          |
      |planCode                     | 400752220       |
      |planShortName                |ANTHEM       |
      |serviceDeliveryAreas[0].sdaName  | Statewide      |
      |planTypeEntity.planType          | Medical          |
      |sdaPrograms[0].programTypeCd     | Medicaid         |
      |sdaPrograms[0].subProgramTypeCd  |HoosierHealthwise |
      |startDate                        |05/02/2019        |
      |endDate                          |[blank]|
      |enrollmentStartDate              |05/02/2019|
      |enrollmentEndDate                |[blank]|
      |planEnrollmentLimits[0].enrollmentCap      |[blank]|
      |planEnrollmentLimits[0].effectiveStartDate |[blank]|
      |planEnrollmentLimits[0].effectiveEndDate   |[blank]|
      |planContact.addressLine1                   |[blank]|
      |planContact.addressLine2                   |[blank]|
      |planContact.city                           |[blank]|
      |planContact.state                          |[blank]|
      |planContact.zipCode                        |[blank]|
      |planContact.memberServiceNum1              |866-408-6131    |
      |planContact.memberServiceNum2              |[blank]|
      |planContact.memberServiceNum3              |[blank]|
      |planContact.memberServiceNum4              |[blank]|
      |planContact.memberServiceNum5              |[blank]|
      |planContact.planContactFirstName           |[blank]|
      |planContact.planContactLastName            |[blank]|
      |planContact.memberServicesWebsite          | www.anthem.com   |
      |planContact.plancontactphonenum            |[blank]|
      |planContact.providerServicesNum1           |866-408-6132    |
      |planContact.providerServicesNum2           |[blank]|
      |planContact.providerServicesNum3           |[blank]|

  @API-CP-10580 @API-CP-10580-01 @API-PP-Regression @mital
  Scenario Outline: Verify distance value to be one digit after decimal point in address based provider search
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.city         | Acworth    |
      | providerSearch.state        | GA         |
      | providerSearch.country      | USA        |
      | providerSearch.distance     | 10         |
    Then I verify the response status code "200" with creation status "success"
    And I verify distance value in payload with one digit after decimal point
    And I verify in payload distance values are in ascending order
    Examples:
      | projectName |
      |[blank]|


  @API-CP-11073 @API-CP-11073-01 @API-PP-Regression @mital
  Scenario Outline: Verify provider search based on 1.Provider Type - Dental 2. Program Name 3.Handicap Accessible Value - Yes
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.providerTypeCd          | Dental     |
      | providerSearch.ageLowLimit             | 0          |
      | providerSearch.ageHighLimit            | 120        |
      | providerSearch.subProgramTypeCd        | MedicaidGF |
      | providerSearch.handicappedAccesibleInd | true       |
    Then I verify the response status code "200" with creation status "success"
    And I verify provider search response with values given below
      | ageLowLimit                    | 0          |
      | ageHighLimit                   | 120        |
      | providerType[0].providerTypeCd | DENTAL     |
      | handicappedAccesibleInd        | true       |
      | subProgramTypeCd               | MedicaidGF |

    Examples:
      | projectName |
      |[blank]|

  @API-CP-11073 @API-CP-11073-02 @API-PP-Regression @mital
  Scenario Outline: Verify provider search based on Handicap Accessible Value - No
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearch" for search provider
      | providerSearch.providerTypeCd          | Medical |
      | providerSearch.planName                 | AMERIGROUP COMMUNITY CARE        |
      | providerSearch.handicappedAccesibleInd | false   |
    Then I verify the response status code "200" with creation status "success"
    And I verify provider search response with values given below
      | providerType[0].providerTypeCd | Medical     |
      | handicappedAccesibleInd        | false       |
    Examples:
      | projectName |
      |[blank]|

  @API-CP-11181 @API-CP-11181-01 @API-PP-Regression @mital
  Scenario: Verify provider search for First and Last Name based on Starts with/Contains logic
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearchWithOperator" for search provider
      | providerSearch.firstName      | ANA                        |
      | providerSearch.lastName      | MON                        |
      | providerSearch.searchOperator | startsWith                  |
    Then I verify the response status code "200" with creation status "success"
  Then I verify response provider name values "startsWith" search parameters "ANA" in First Name and "MON" in Last Name
    And User send Api call with payload "providerSearchWithOperator" for search provider
      | providerSearch.firstName      | ANA                        |
      | providerSearch.lastName      | MON                        |
      | providerSearch.searchOperator | contains                  |
    Then I verify the response status code "200" with creation status "success"
    Then I verify response provider name values "contains" search parameters "ANA" in First Name and "MON" in Last Name

  @API-CP-11181 @API-CP-11181-02 @API-PP-Regression @mital
  Scenario: Verify provider search for Group Name based on Starts with/Contains logic
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Plan Management for Search Provider
    And User send Api call with payload "providerSearchWithOperator" for search provider
      | providerSearch.groupName      | HARRISON                        |
      | providerSearch.searchOperator | startsWith                  |
    Then I verify the response status code "200" with creation status "success"
    Then I verify response provider name values "startsWith" search parameters "HARRISON" in Group Name
    And User send Api call with payload "providerSearchWithOperator" for search provider
      | providerSearch.groupName      | PARKVIEW                        |
      | providerSearch.searchOperator | contains                  |
    Then I verify the response status code "200" with creation status "success"
    Then I verify response provider name values "contains" search parameters "PARKVIEW" in Group Name




