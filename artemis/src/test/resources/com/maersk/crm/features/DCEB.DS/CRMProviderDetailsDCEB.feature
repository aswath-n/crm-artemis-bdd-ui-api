@viewProviderDCEB
Feature: View Provider DetailsDCEB

  @CP-38997 @CP-38997-01 @mital @ui-pp
  Scenario: 1.0 Verify viewing Provider Type (Global Provider Search)
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    Then I verify dropdown Provider Type on provider search page has options
      | Individual Provider                  |
      | Group Provider                       |
      | Hospital                             |
      | Community Health Centers and Clinics |
      | Pharmacy                             |
      | Dental                               |
      | Medical                              |
      | Vision                               |
      | Adult Foster Care                    |
      | Ambulatory Surgery Center            |
      | Birthing Center                      |
      | Inpatient Substance Abuse Facility   |
      | Day Activity/Health Center           |
      | Imaging Facility                     |
#      | Inpatient Mental Health Facility     |
      | Nursing Home                         |
      | Outpatient Mental Health Facility    |
      | Outpatient Substance Abuse Facility  |
      | Primary Care Group                   |
      | Radiology Facility                   |
      | Skilled Nursing Facility             |
      | Sleep Disorder Center                |
      | Urgent Care Center                   |
      | Wound Care Center                    |
      | Laboratory                           |
      | Hospice                              |
      | Residential Treatment Center         |
      | Durable Medical Equipment            |
      | Multi-Specialty Group                |
      | Behavioral Health Group              |
      | Trauma Center                        |

  @CP-38997 @CP-38997-02 @CP-38997-03 @mital @ui-pp
  Scenario: 2.0 Display Basic Provider Search Criteria
            3.0 Display Advanced Provider Search Criteria
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    Then I verify fields on provider search page with data
      | FIRST NAME     | is displayed |
      | LAST NAME      | is displayed |
      | GROUP NAME     | is displayed |
      | PHONE NUMBER   | is displayed |
      | PLAN NAME      | is displayed |
      | PROGRAM NAME   | is displayed |
      | PROVIDER TYPE  | is displayed |
      | SPECIALTY      | is displayed |
      | STREET ADDRESS | is displayed |
      | CITY           | is displayed |
      | ZIP            | is displayed |
      | DISTANCE       | is displayed |
    And I click on "Advanced Search" button on provider search page
    Then I verify fields on provider search page with data
      | LANGUAGES              | is displayed |
      | PCP INDICATOR          | is displayed |
      | OB INDICATOR           | hidden       |
      | ACCEPTING NEW PATIENTS | is displayed |
      | GENDER SERVED          | is displayed |
      | PROVIDER GENDER        | is displayed |
      | COUNTY                 | is displayed |
      | STATE                  | is displayed |
      | PATIENT AGE RANGE      | is displayed |

  @CP-38997 @CP-38997-04 @mital @ui-pp
  Scenario: 4.0 Display Provider Search Results (Starts with/Contains search)
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I set Search By operator to "Contains"
    When I fill out fields on provider search page with data
      | FIRST NAME | am  |
      | LAST NAME  | son |
    And I click on "SEARCH" button on provider search page
    Then I verify provider search results table fields At A Glance View with data
      | NAME             | is displayed                                                                                                 |
      | PROVIDER ADDRESS | validate STREET ADDRESS, CITY, STATE, ZIP, PHONE NUMBER, Accepting New Patient Icon, Accepting handicap icon |
      | PLAN NAME        | is displayed                                                                                                 |
      | PROGRAM NAME     | is displayed                                                                                                 |
      | PROVIDER TYPE    | is displayed                                                                                                 |
      | SPECIALTY        | is displayed                                                                                                 |
      | LAST UPDATED     | is displayed                                                                                                 |
    And I verify provider name values "Contains" search parameters "am" in First Name and "son" in Last Name
    And I click on "CANCEL" button on provider search page

    And I set Search By operator to "Start with"
    When I fill out fields on provider search page with data
      | FIRST NAME | in  |
      | LAST NAME  | roo |
    And I click on "SEARCH" button on provider search page
    And I verify provider name values "Start with" search parameters "in" in First Name and "roo" in Last Name


  @CP-38997 @CP-38997-05 @mital @ui-pp
  Scenario: 5.0 Display Selected Provider Details (for selected location) Global Provider - Verify office hours
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      | FIRST NAME | Jhon  |
      | LAST NAME  | Tom |
    And I click on "SEARCH" button on provider search page
    Then I click on first found provider and verify Provider Details with data
      | PROVIDER DETAILS.PROVIDER NAME                       | is displayed |
      | PROVIDER DETAILS.PROVIDER GENDER                     | is displayed |
      | PROVIDER DETAILS.GENDER SERVED                       | is displayed |
      | PROVIDER DETAILS.PCP INDICATOR                       | is displayed |
      | PROVIDER DETAILS.PATIENT AGE RANGE                   | is displayed |
      | PROVIDER DETAILS.NPI                                 | is displayed |
      | SPECIALTY                                            | 1 - 10       |
      | HOSPITAL AFFILIATION                                 | 1 - 5        |
      | LOCATION ASSOCIATED TO THE PROVIDER.PLAN NAME        | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.PROVIDER ADDRESS | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.PHONE NUMBER     | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.EMAIL ADDRESS    | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.NEW PATIENTS     | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.HANDICAP         | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.OFFICE HOURS     | is displayed |
    And I verify office hours segment getting correctly displayed with Sunday in Red Color
   # And I mouse over on the office hours to verify hovertext as Evening Hours
    And I mouse over on the office hours to verify hovertext as Evening Hours for day "5"
   # And I verify office hours segment getting correctly displayed with Saturday in blue Color

  @CP-42516 @CP-42516-01 @mital @ui-pp
  Scenario: verify multiple addresses are getting correctly displayed for a provider
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      | FIRST NAME | Priyal |
      | LAST NAME  | Garg   |
    And I click on "SEARCH" button on provider search page
    Then I click on plus sign to expand addresses for first provider and save addresses in a list
    And I verify multiple addresses are getting correctly displayed for a provider
    Then I click on first found provider and verify Provider Details with data
      | LOCATION ASSOCIATED TO THE PROVIDER.PROVIDER ADDRESS | is displayed |
    And I verify row for each provider address is displayed in Locations Associated

  @CP-40075 @CP-40075-01 @planProviderUI  @UI-PP-Regression @planProvider @deepa
  Scenario Outline: Verify - Provider & Location Attributes, Calculating EPSDT and Accept New Patient
    When I will get the Authentication token for "DC-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProviderDCEB" for creating provider with name "40075-01" with data
      | providers[0].firstName                          | random                |
      | providers[0].lastName                           | random                |
      | providers[0].npi                                | random                |
      | providers[0].planCode                           | 044733300             |
      | providers[0].acceptNewPatientsInd               | true                  |
      | providers[0].planName                           | MEDSTAR FAMILY CHOICE |
      | providers[0].subProgramTypeCd                   | DCHF                  |
      | providers[0].pcpFlag                            | Y                     |
      | providers[0].providerSpeciality[0].specialityCd | <SpecialityCode>      |
      | providers[0].providerType[0].providerTypeCd     | P                     |
    Then I verify the response status code "200" with creation status "success"
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      | FIRST NAME | 40075-01.providers[0].firstName |
      | LAST NAME  | 40075-01.providers[0].lastName   |
      #| SPECIALTY              | Pediatric |
     # | PCP INDICATOR          | yes       |
      #| ACCEPTING NEW PATIENTS | yes       |
    And I click on "SEARCH" button on provider search page
    Then I click on first found provider and verify Provider Details with data
      | EPSDT   | yes |
    Examples:
      | SpecialityCode |
      | 24A            |
      | 28F            |

  @CP-40075 @CP-40075-03 @planProviderUI  @UI-PP-Regression @planProvider @deepa
  Scenario: : Verify - Display Accepting New Patient Indicator in green colour
    When I will get the Authentication token for "DC-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProviderDCEB" for creating provider with name "40075-03" with data
      | providers[0].firstName                          | random                |
      | providers[0].lastName                           | random                |
      | providers[0].npi                                | random                |
      | providers[0].planCode                           | 044733300             |
      | providers[0].acceptNewPatientsInd               | true                  |
      | providers[0].planName                           | MEDSTAR FAMILY CHOICE |
      | providers[0].subProgramTypeCd                   | DCHF                  |
    Then I verify the response status code "200" with creation status "success"
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      | FIRST NAME | 40075-03.providers[0].firstName |
      | LAST NAME  | 40075-03.providers[0].lastName   |
    And I click on "SEARCH" button on provider search page
    When I have selected to view the provider detail screen
    When I view the icons for Accepting New Patients
    Then verify Hex color is green "#29A040" icon person accepting new patients

  @CP-40075 @CP-40075-04 @planProviderUI  @UI-PP-Regression @planProvider @deepa
  Scenario:  Verify - Display Accepting New Patient Indicator in red colour
    When I will get the Authentication token for "DC-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProviderDCEB" for creating provider with name "40075-04" with data
      | providers[0].firstName                          | random                |
      | providers[0].lastName                           | random                |
      | providers[0].npi                                | random                |
      | providers[0].planCode                           | 044733300             |
      | providers[0].acceptNewPatientsInd               | false                 |
      | providers[0].planName                           | MEDSTAR FAMILY CHOICE |
      | providers[0].subProgramTypeCd                   | DCHF                  |
    Then I verify the response status code "200" with creation status "success"
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      | FIRST NAME | 40075-04.providers[0].firstName |
      | LAST NAME  | 40075-04.providers[0].lastName   |
    When I fill out field Accepting new patients on provider search page with data "No"
    And I click on "SEARCH" button on provider search page
    When I have selected to view the provider detail screen
    When I view the icons for Accepting New Patients
    Then verify Hex color is red "#EF5350" icon person accepting patients

  @CP-40075 @CP-40075-05 @planProviderUI  @UI-PP-Regression @planProvider @deepa
  Scenario: 5.0 Display Selected Provider Details (for selected location) Global Provider - Verify Evening hours and Saturday in blue colour
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      | FIRST NAME | Jhon  |
      | LAST NAME  | Tom |
    And I click on "SEARCH" button on provider search page
    Then I click on first found provider and verify Provider Details with data
      | PROVIDER DETAILS.PROVIDER NAME                       | is displayed |
      | PROVIDER DETAILS.PROVIDER GENDER                     | is displayed |
      | PROVIDER DETAILS.GENDER SERVED                       | is displayed |
      | PROVIDER DETAILS.PCP INDICATOR                       | is displayed |
     # | PROVIDER DETAILS.PATIENT AGE RANGE                   | is displayed |
      | PROVIDER DETAILS.NPI                                 | is displayed |
     # | SPECIALTY                                            | 1 - 10       |
     # | HOSPITAL AFFILIATION                                 | 1 - 5        |
      | LOCATION ASSOCIATED TO THE PROVIDER.PLAN NAME        | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.PROVIDER ADDRESS | is displayed |
     # | LOCATION ASSOCIATED TO THE PROVIDER.PHONE NUMBER     | is displayed |
     # | LOCATION ASSOCIATED TO THE PROVIDER.EMAIL ADDRESS    | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.NEW PATIENTS     | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.HANDICAP         | is displayed |
      | LOCATION ASSOCIATED TO THE PROVIDER.OFFICE HOURS     | is displayed |
    And I mouse over on the office hours to verify hovertext as Evening Hours for day "5"
    And I verify office hours segment getting correctly displayed with Saturday in blue Color

  @CP-48005 @CP-48005-01 @planProviderUI  @UI-PP-Regression @planProvider @deepa
  Scenario Outline: :  Verify - Countyname search in the Provider Search UI
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I minimize Genesys popup if populates
    And I click on "Advanced Search" button on provider search page
    Then I verify dropdown County name on provider search page has options
      | Northeast |
      | Northwest |
      | Southeast |
      | Southwest |
    And   User select county "<COUNTY>"
    And I click on "SEARCH" button on provider search page
    Then I verify provider search results table fields At A Glance View with data
      | NAME | is displayed |
    Examples:
      | COUNTY    |
      | Northeast |

  @CP-48005 @CP-48005-02 @planProviderUI  @API-PP-Regression @planProvider @deepa
  Scenario: Digital search API by passing Countyname
    When I will get the Authentication token for "DC-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProviderDCEB" for creating provider with name "48005-02" with data
      | providers[0].firstName                       | random                |
      | providers[0].lastName                        | random                |
      | providers[0].npi                             | random                |
      | providers[0].planCode                        | 044733300             |
      | providers[0].acceptNewPatientsInd            | true                  |
      | providers[0].planName                        | MEDSTAR FAMILY CHOICE |
      | providers[0].subProgramTypeCd                | DCHF                  |
      | providers[0].providerAddress[0].addressLine1 | 3240 STANTON RD SE    |
      | providers[0].providerAddress[0].city         | Washington            |
      | providers[0].providerAddress[0].state        | DC                    |
      | providers[0].providerAddress[0].zipCode      | 20020                 |
      | providers[0].providerAddress[0].countyCd     | 01                    |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
      | warnings | null |
    When I initiate digital Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.firstName | 48005-02.providers[0].firstName |
      | providerSearch.lastName  | 48005-02.providers[0].lastName  |
      | providerSearch.countyName|  Northwest                      |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | firstName                                | 48005-02.providers[0].firstName |
      | lastName                                 | 48005-02.providers[0].lastName  |
      | providers[0].providerAddress[0].countyCd | 01                              |

  @CP-48755 @CP-48755-02  @planProviderUI  @UI-PP-Regression @planProvider @deepa
  Scenario:UPDATE UI to allow for 100 characters for Displaying Complete Plan Name in Drop-down
    Given I logged into CRM and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      | PLAN NAME |CAREFIRST COMMUNITY HEALTH PLAN DC |
    And I click on "SEARCH" button on provider search page
    And Wait for 5 seconds
    Then I verify provider search results table fields At A Glance View with data
      | NAME          | is displayed |
      | PLAN NAME     | is displayed |
      | PROGRAM NAME  | is displayed |
      | PROVIDER TYPE | is displayed |
      | SPECIALTY     | is displayed |
      | LAST UPDATED  | is displayed |