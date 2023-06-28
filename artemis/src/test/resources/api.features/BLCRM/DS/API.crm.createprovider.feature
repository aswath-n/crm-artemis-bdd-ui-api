@apiCreateProvider
Feature: API-Tenant Manager: Create a new provider and establish provider data elements

  @planProviderAPI @API-CP-1006 @API-CP-1006-01 @API-CRM @API-PP-Regression @mohammad @plan-manag-ms-PP @API-CRM-SmokeTest @1 @events
  Scenario Outline: Create new provider details with appropriate data
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps1000601" payload "createProvider" for Provider Search
      | providers[0].firstName                           | Colin                     |
      | providers[0].lastName                            | Dave                      |
      | providers[0].npi                                 | npi::                     |
      | providers[0].effectiveStartDate                  | date::                  |
      | providers[0].acceptNewPatientsInd                | false                     |
    Then I verify the response status code "200" with creation status "success"
    Examples:
      |projectName|
      |[blank]|

  @planProviderAPI @API-CP-1006 @API-CP-1006-04 @API-CRM @API-PP-Regression @mohammad @plan-manag-ms-PP @API-CRM-SmokeTest @1 @events @pp-events
  Scenario Outline: Verify the payload to verify it has action, recordType, eventCreatedOn, and dataObject
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Plan Management for service provider API
    When I create a new provider with request payload with the appropriate data and post
    Then I verify the response status code "200" with creation status "success"
    When I initiate Event POST API Services
    And I create a new event with request payload with the appropriate data and trace-id and post
    Then I verify the response status code "200" with creation status "success"
    And I verify the payload has action, recordType, eventCreatedOn, and dataObject
    Examples:
      |projectName|
      |[blank]|

  @planProviderAPI @API-CP-1006 @API-CP-1006-05 @API-CRM @API-PP-Regression @mohammad @plan-manag-ms-PP @API-CRM-SmokeTest @events_smoke_level_two @events @pp-events
  Scenario Outline: Verify the provider_save_event is generated on provider insert
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Plan Management for service provider API
    When I create a new provider with request payload with the appropriate data and post
    Then I verify the response status code "200" with creation status "success"
    When I initiate Event POST API Services
    And I create a new event with request payload with the appropriate data and trace-id and post
    Then I verify the response status code "200" with creation status "success"
    When I initiate Subscribers Record GET API and run Subscribers Record GET API Services
    Then I verify the response status code "200" with creation status "success"
    And I will verify Subscribers response has event Id and "PROVIDER_SAVE_EVENT" and subscriberId
    Examples:
      |projectName|
      |[blank]|

  @planProviderAPI @API-CP-1006 @API-CP-1006-06 @API-CRM @API-PP-Regression @mohammad @plan-manag-ms-PP @API-CRM-SmokeTest @events @pp-events
  Scenario Outline: Verify the created date and created by
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Plan Management for service provider API
    When I create a new provider with request payload with the appropriate data and post
    Then I verify the response status code "200" with creation status "success"
    When I initiate Event POST API Services
    And I create a new event with request payload with the appropriate data and trace-id and post
    Then I verify the response status code "200" with creation status "success"
    When I initiate Subscribers Record GET API and run Subscribers Record GET API Services
    Then I verify the response status code "200" with creation status "success"
    And I will verify Subscribers response has event Id and "PROVIDER_SAVE_EVENT" and subscriberId
    And I verify created date and created by fields as appropriate
    Examples:
      |projectName|
      |[blank]|
#Same as on @API-CP-92 Ducplicate
#  @planProviderAPI @API-CP-5351 @API-CP-5351-01 @API-CRM @API-PP-Regression @mohammad @plan-manag-ms-PP @API-CRM-SmokeTest
  Scenario Outline: Verification provider Search API
    Given I initiated Plan Management for service provider API
    #When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I create a new provider with request payload with the appropriate data and post
    Then I verify the response status code "200" with creation status "success"
    #When I initiate Provider Search API
    #And I create a request payload with all the search field values and post
    #Then I verify the response status code "200" with creation status "success"
    Examples:
      |projectName|
      |[blank]|
#Same as on @API-CP-92 Ducplicate
#  @planProviderAPI @API-CP-5351 @API-CP-5351-02 @API-CRM @API-PP-Regression @mohammad @plan-manag-ms-PP @API-CRM-SmokeTest
  Scenario Outline: Verification provider Search AP response
    Given I initiated Plan Management for service provider API
    #When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I create a new provider with request payload with the appropriate data and post
    Then I verify the response status code "200" with creation status "success"
    When I initiate Provider Search API
    And I create a request payload with all the search field values and post
    Then I verify the response status code "200" with creation status "success"
    And I verify all the search result parameters with the new provider parameters values
    Examples:
      |projectName|
      |[blank]|
#Same as on @API-CP-92 Ducplicate
#  @planProviderAPI @API-CP-5351 @API-CP-5351-03 @API-CRM @API-PP-Regression @mohammad @plan-manag-ms-PP @API-CRM-SmokeTest
  Scenario Outline: Verification of has a doctor search criteria
    Given I initiated Plan Management for service provider API
    #When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I create a new provider with request payload with the appropriate data and post
    Then I verify the response status code "200" with creation status "success"
    When I initiate Provider Search API
    And I create a request payload with First Name, Last Name, Group Name, and Phone Number search field values and post
    Then I verify the response status code "200" with creation status "success"
    And I verify all the search result parameters with the new provider parameters values
    Examples:
      |projectName|
      |[blank]|
#Same as on @API-CP-92 Ducplicate
#  @planProviderAPI @API-CP-5351 @API-CP-5351-04 @API-CRM @API-PP-Regression @mohammad @plan-manag-ms-PP @API-CRM-SmokeTest
  Scenario Outline: Verification of the Pick a doctor search criteria
    Given I initiated Plan Management for service provider API
    #When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I create a new provider with request payload with the appropriate data and post
    Then I verify the response status code "200" with creation status "success"
    When I initiate Provider Search API
    And I create a request payload with Plan Name, Provider Type, Language, Gender Served, Provider Gender, and Specialty search field values and post
    Then I verify the response status code "200" with creation status "success"
    And I verify all the search result parameters with the new provider parameters values
    Examples:
      |projectName|
      |[blank]|
#Same as on @API-CP-92 Ducplicate
#  @planProviderAPI @API-CP-5351 @API-CP-5351-10 @API-CRM @API-PP-Regression @mohammad @plan-manag-ms-PP @API-CRM-SmokeTest
  Scenario Outline: Verification of the providers active
    Given I initiated Plan Management for service provider API
    #When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I create a new provider with request payload with the appropriate data and post
    Then I verify the response status code "200" with creation status "success"
    When I initiate Provider Search API
    And I create a request payload with First Name, Last Name, and Group Name search field values and post
    Then I verify the response status code "200" with creation status "success"
    And I verify all the search result parameters with the new provider parameters values
    Examples:
      |projectName|
      |[blank]|
#Same as on @API-CP-92 Ducplicate
#  @planProviderAPI @API-CP-5351 @API-CP-5351-05 @API-CP-5351-06 @API-CP-5351-07 @API-CRM @API-PP-Regression @mohammad @plan-manag-ms-PP @API-CRM-SmokeTest
  Scenario Outline: Verification of the providers active
    Given I initiated Plan Management for service provider API
    #When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I create a new provider with request payload with the appropriate data and post
    Then I verify the response status code "200" with creation status "success"
    When I initiate Provider Search API
    And I create a request payload with Plan Name, Provider Type, Language, Gender Served, Provider Gender, and Specialty search field values and post
    Then I verify the response status code "200" with creation status "success"
    And I verify all the search result parameters with the new provider parameters values
    Examples:
      |projectName|
      |[blank]|

  @planProviderAPI @API-CP-17362 @API-CP-17362-01 @API-CRM @mital @events @pp-events
  Scenario Outline: Verify the provider_save_event is generated on provider insert
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps17362" payload "createProvider" for Provider Search
      | providers[0].firstName          | Adams     |
      | providers[0].lastName           | Smith      |
      | providers[0].npi                | npi::     |
      | providers[0].groupName          | adamsmith |
      | providers[0].providerAddress[0].city         | Atlanta           |
    Then I verify the response status code "200" with creation status "success"
    And User send Api call with name "ps17362" payload "createProvider" for Provider Search
      | providers[0].firstName          | Adams     |
      | providers[0].lastName           | Smith      |
      | providers[0].npi                | ps17362.providers[0].npi      |
      | providers[0].groupName          | adamsmith  |
      | providers[0].providerAddress[0].city         | Jackson           |
    Then I verify the response status code "200" with creation status "success"
    When I initiate Event POST API Services
    And I create a new event with request payload with the appropriate data and trace-id and post
    Then I verify the response status code "200" with creation status "success"
    When I initiate Subscribers Record GET API and run Subscribers Record GET API Services
    Then I verify the response status code "200" with creation status "success"
    And I will verify Subscribers response has event Id and "PROVIDER_UPDATE_EVENT" and subscriberId
    Examples:
      |projectName|
      |[blank]|

  @planProviderAPI @API-CP-17362 @API-CP-17362-02 @API-CRM  @mital @events @pp-events
  Scenario Outline: Verify the provider_save_event is generated on provider insert
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate Provider Search API
    When I initiated Plan Management for service provider API
    And User send Api call with name "ps17362-02" payload "createProvider" for Provider Search
      | providers[0].firstName          | Jane     |
      | providers[0].lastName           | Smith    |
      | providers[0].npi                | npi::                     |
      | providers[0].groupName          | adamsmith  |
      | providers[0].effectiveEndDate   | 2022-12-10T09:39:04.000Z          |
    Then I verify the response status code "200" with creation status "success"
    When I initiate Event POST API Services
    And I create a new event with request payload with the appropriate data and trace-id and post
    Then I verify the response status code "200" with creation status "success"
    When I initiate Subscribers Record GET API and run Subscribers Record GET API Services
    Then I verify the response status code "200" with creation status "success"
    And I will verify Subscribers response has event Id and "PROVIDER_SAVE_EVENT" and subscriberId
    Examples:
      |projectName|
      |[blank]|


  @API-CP-30858 @API-CP-30858-1 @planProviderAPI @API-TM @API-PP-Regression @planProvider @mital
  Scenario: Update “Duplicate Provider ID” matching logic - Update logic- All fields matching - Provider ID, Plan Code, Group Name, Group ID
    When I will get the Authentication token for "IN-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "30858-1" with data
      | providers[0].firstName                       | Ashley                  |
      | providers[0].lastName                        | W                       |
      | providers[0].middleName                      | Smith                   |
      | providers[0].programTypeCd                   | MEDICAID                |
      | providers[0].stateProviderId                 | stateProviderId::       |
      | providers[0].npi                             | null               |
      | providers[0].planCode                        | 400752220               |
      | providers[0].planName                        | ANTHEM                  |
      | providers[0].sexLimitsCd                     | F                        |
      | providers[0].subProgramTypeCd                | HealthyIndianaPlan      |
      | providers[0].groupName                       | REID HEALTH GROUP       |
      | providers[0].groupId                         | 100268496M              |
      | providers[0].providerAddress[0].addressLine1 | 1625 EAST JEFFERSON BLVD|
      | providers[0].providerAddress[0].city         | MISHAWAKA               |
      | providers[0].providerAddress[0].state        | IN                      |
      | providers[0].providerAddress[0].zipCode      | 46545                   |
      | providers[0].providerAddress[0].countyCd     | 071                     |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
      | warnings | null |
    And User send Api call with payload "createProvider" for creating provider with name "" with data
      | providers[0].firstName                       | Ashley                  |
      | providers[0].lastName                        | W                       |
      | providers[0].middleName                      | Smith                   |
      | providers[0].stateProviderId                 | 30858-1.providers[0].stateProviderId       |
      | providers[0].planCode                        | 400752220               |
      | providers[0].planName                        | ANTHEM                  |
      | providers[0].npi                             | null               |
      | providers[0].subProgramTypeCd                | HealthyIndianaPlan      |
      | providers[0].groupName                       | REID HEALTH GROUP       |
      | providers[0].sexLimitsCd                     | B                        |
      | providers[0].groupId                         | 100268496M              |
      | providers[0].providerAddress[0].addressLine1 | 1625 EAST JEFFERSON BLVD|
      | providers[0].providerAddress[0].city         | MISHAWAKA               |
      | providers[0].providerAddress[0].state        | IN                      |
      | providers[0].providerAddress[0].zipCode      | 46545                   |
      | providers[0].providerAddress[0].countyCd     | 071                     |

    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after updating provider with data
      | errors   | null |

  @API-CP-30858 @API-CP-30858-2 @planProviderAPI @API-TM @API-PP-Regression @planProvider @mital
  Scenario: Update “Duplicate Provider ID” matching logic- Insert Logic- Unmatched Plan Code
    When I will get the Authentication token for "IN-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "30858-2" with data
      | providers[0].firstName                       | Jane                    |
      | providers[0].lastName                        | W                       |
      | providers[0].middleName                      | Smith                   |
      | providers[0].stateProviderId                 | stateProviderId::       |
      | providers[0].planCode                        | 700410350               |
      | providers[0].planName                        | CARESOURCE               |
      | providers[0].sexLimitsCd                     | F                        |
      | providers[0].subProgramTypeCd                | HealthyIndianaPlan      |
      | providers[0].groupName                       | REID HEALTH GROUP       |
      | providers[0].groupId                         | 100268496M              |
      | providers[0].npi                             | null               |
      | providers[0].providerAddress[0].addressLine1 | 1625 EAST JEFFERSON BLVD|
      | providers[0].providerAddress[0].city         | MISHAWAKA               |
      | providers[0].providerAddress[0].state        | IN                      |
      | providers[0].providerAddress[0].zipCode      | 46545                   |
      | providers[0].providerAddress[0].countyCd     | 071                     |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
      | warnings | null |
    And User send Api call with payload "createProvider" for creating provider with name "" with data
      | providers[0].firstName                       | Jane                    |
      | providers[0].lastName                        | W                       |
      | providers[0].middleName                      | Smith                   |
      | providers[0].stateProviderId                 | 30858-2.providers[0].stateProviderId       |
      | providers[0].planCode                        | 400752220               |
      | providers[0].planName                        | ANTHEM                  |
      | providers[0].subProgramTypeCd                | HealthyIndianaPlan      |
      | providers[0].groupName                       | REID HEALTH GROUP       |
      | providers[0].sexLimitsCd                     | F                        |
      | providers[0].groupId                         | 100268496M              |
      | providers[0].npi                             | null               |
      | providers[0].providerAddress[0].addressLine1 | 1625 EAST JEFFERSON BLVD|
      | providers[0].providerAddress[0].city         | MISHAWAKA               |
      | providers[0].providerAddress[0].state        | IN                      |
      | providers[0].providerAddress[0].zipCode      | 46545                   |
      | providers[0].providerAddress[0].countyCd     | 071                     |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
      | warnings | null |


  @API-CP-30858 @API-CP-30858-3 @planProviderAPI @API-TM @API-PP-Regression @planProvider @mital
  Scenario: Update “Duplicate Provider ID” matching logic- Insert Logic- Unmatched Group Name and Group ID
    When I will get the Authentication token for "IN-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "30858-3" with data
      | providers[0].firstName                       | Jane                    |
      | providers[0].lastName                        | W                       |
      | providers[0].middleName                      | Smith                   |
      | providers[0].stateProviderId                 | stateProviderId::       |
      | providers[0].planCode                        | 700410350               |
      | providers[0].planName                        | CARESOURCE               |
      | providers[0].sexLimitsCd                     | F                        |
      | providers[0].npi                             | null               |
      | providers[0].subProgramTypeCd                | HealthyIndianaPlan      |
      | providers[0].groupName                       | ST VINCENT MEDICAL GROUP INC  |
      | providers[0].groupId                         | 201060820S              |
      | providers[0].providerAddress[0].addressLine1 | 1625 EAST JEFFERSON BLVD|
      | providers[0].providerAddress[0].city         | MISHAWAKA               |
      | providers[0].providerAddress[0].state        | IN                      |
      | providers[0].providerAddress[0].zipCode      | 46545                   |
      | providers[0].providerAddress[0].countyCd     | 071                     |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
      | warnings | null |
    And User send Api call with payload "createProvider" for creating provider with name "" with data
      | providers[0].firstName                       | Jane                    |
      | providers[0].lastName                        | W                       |
      | providers[0].middleName                      | Smith                   |
      | providers[0].stateProviderId                 | 30858-3.providers[0].stateProviderId       |
      | providers[0].planCode                        | 700410350               |
      | providers[0].planName                        | CARESOURCE               |
      | providers[0].subProgramTypeCd                | HealthyIndianaPlan      |
      | providers[0].npi                             | null               |
      | providers[0].groupName                       | REID HEALTH GROUP       |
      | providers[0].sexLimitsCd                     | F                        |
      | providers[0].groupId                         | 100268496M              |
      | providers[0].providerAddress[0].addressLine1 | 1625 EAST JEFFERSON BLVD|
      | providers[0].providerAddress[0].city         | MISHAWAKA               |
      | providers[0].providerAddress[0].state        | IN                      |
      | providers[0].providerAddress[0].zipCode      | 46545                   |
      | providers[0].providerAddress[0].countyCd     | 071                     |

    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
      | warnings | null |


# Removed as an effect of Implementation Change due to CP-45622
#  @CP-30885 @CP-30885-3 @planProvidergression @planProvider @API-PP-Regression @mital
  Scenario: Provider Updates - Gender Served Search Criteria - Reconciliation Gender Served-F & B---->B
    When I will get the Authentication token for "IN-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "30885-3" with data
      | providers[0].firstName                       | Sam                  |
      | providers[0].lastName                        | Clare                       |
      | providers[0].middleName                      | s                 |
      | providers[0].programTypeCd                   | MEDICAID                |
      | providers[0].stateProviderId                 | stateProviderId::       |
      | providers[0].planCode                        | 400752220               |
      | providers[0].planName                        | ANTHEM                  |
      | providers[0].npi                             | null               |
      | providers[0].sexLimitsCd                     | F                        |
      | providers[0].subProgramTypeCd                | HealthyIndianaPlan      |
      | providers[0].groupName                       | REID HEALTH GROUP       |
      | providers[0].groupId                         | 100268496M              |
      | providers[0].providerAddress[0].addressLine1 | 1625 EAST JEFFERSON BLVD|
      | providers[0].providerAddress[0].city         | MISHAWAKA               |
      | providers[0].providerAddress[0].state        | IN                      |
      | providers[0].providerAddress[0].zipCode      | 46545                   |
      | providers[0].providerAddress[0].countyCd     | 071                     |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
      | warnings | null |
    And User send Api call with payload "createProvider" for creating provider with name "30885-3" with data
      | providers[0].firstName                       | Sam                  |
      | providers[0].lastName                        | Clare                       |
      | providers[0].middleName                      | s                 |
      | providers[0].programTypeCd                   | MEDICAID                |
      | providers[0].stateProviderId                 | 30885-3.providers[0].stateProviderId       |
      | providers[0].planCode                        | 400752220               |
      | providers[0].planName                        | ANTHEM                  |
      | providers[0].sexLimitsCd                     | B                        |
      | providers[0].subProgramTypeCd                | HealthyIndianaPlan      |
      | providers[0].groupName                       | REID HEALTH GROUP       |
      | providers[0].groupId                         | 100268496M              |
      | providers[0].providerAddress[0].addressLine1 | 1625 EAST JEFFERSON BLVD|
      | providers[0].npi                             | null               |
      | providers[0].providerAddress[0].city         | MISHAWAKA               |
      | providers[0].providerAddress[0].state        | IN                      |
      | providers[0].providerAddress[0].zipCode      | 46545                   |
      | providers[0].providerAddress[0].countyCd     | 071                     |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.firstName | Sam |
      | providerSearch.lastName | Clare |
      | providerSearch.stateProviderId | 30885-3.providers[0].stateProviderId  |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | sexLimitsCd | B |

# Removed as an effect of Implementation Change due to CP-45622
#  @CP-30885 @CP-30885-4 @planProvidergression @planProvider @API-PP-Regression @mital
  Scenario: Provider Updates - Gender Served Search Criteria - Reconciliation Gender Served-M & B---->B
    When I will get the Authentication token for "IN-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "30885-4" with data
      | providers[0].firstName                       | Sam                  |
      | providers[0].lastName                        | Clinton                       |
      | providers[0].middleName                      | s                 |
      | providers[0].programTypeCd                   | MEDICAID                |
      | providers[0].stateProviderId                 | stateProviderId::       |
      | providers[0].planCode                        | 400752220               |
      | providers[0].planName                        | ANTHEM                  |
      | providers[0].sexLimitsCd                     | M                        |
      | providers[0].subProgramTypeCd                | HealthyIndianaPlan      |
      | providers[0].groupName                       | REID HEALTH GROUP       |
      | providers[0].groupId                         | 100268496M              |
      | providers[0].providerAddress[0].addressLine1 | 1625 EAST JEFFERSON BLVD|
      | providers[0].providerAddress[0].city         | MISHAWAKA               |
      | providers[0].providerAddress[0].state        | IN                      |
      | providers[0].providerAddress[0].zipCode      | 46545                   |
      | providers[0].providerAddress[0].countyCd     | 071                     |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
      | warnings | null |
    And User send Api call with payload "createProvider" for creating provider with name "30885-4" with data
      | providers[0].firstName                       | Sam                  |
      | providers[0].lastName                        | Clinton                       |
      | providers[0].middleName                      | s                 |
      | providers[0].programTypeCd                   | MEDICAID                |
      | providers[0].stateProviderId                 | 30885-4.providers[0].stateProviderId       |
      | providers[0].planCode                        | 400752220               |
      | providers[0].planName                        | ANTHEM                  |
      | providers[0].sexLimitsCd                     | B                        |
      | providers[0].subProgramTypeCd                | HealthyIndianaPlan      |
      | providers[0].groupName                       | REID HEALTH GROUP       |
      | providers[0].groupId                         | 100268496M              |
      | providers[0].providerAddress[0].addressLine1 | 1625 EAST JEFFERSON BLVD|
      | providers[0].providerAddress[0].city         | MISHAWAKA               |
      | providers[0].providerAddress[0].state        | IN                      |
      | providers[0].providerAddress[0].zipCode      | 46545                   |
      | providers[0].providerAddress[0].countyCd     | 071                     |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.firstName | Sam |
      | providerSearch.lastName | Clinton |
      | providerSearch.stateProviderId | 30885-4.providers[0].stateProviderId  |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | sexLimitsCd | B |

  # Removed as an effect of Implementation Change due to CP-45622
#  @CP-30885 @CP-30885-5 @planProvidergression @planProvider @API-PP-Regression @mital
  Scenario: Provider Updates - Gender Served Search Criteria - Reconciliation Gender Served-M & F---->B
    When I will get the Authentication token for "IN-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "30885-5" with data
      | providers[0].firstName                       | Sam                  |
      | providers[0].lastName                        | Cloony                       |
      | providers[0].middleName                      | s                 |
      | providers[0].programTypeCd                   | MEDICAID                |
      | providers[0].stateProviderId                 | stateProviderId::       |
      | providers[0].planCode                        | 400752220               |
      | providers[0].planName                        | ANTHEM                  |
      | providers[0].sexLimitsCd                     | M                        |
      | providers[0].subProgramTypeCd                | HealthyIndianaPlan      |
      | providers[0].groupName                       | REID HEALTH GROUP       |
      | providers[0].groupId                         | 100268496M              |
      | providers[0].providerAddress[0].addressLine1 | 1625 EAST JEFFERSON BLVD|
      | providers[0].providerAddress[0].city         | MISHAWAKA               |
      | providers[0].providerAddress[0].state        | IN                      |
      | providers[0].providerAddress[0].zipCode      | 46545                   |
      | providers[0].providerAddress[0].countyCd     | 071                     |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
      | warnings | null |
    And User send Api call with payload "createProvider" for creating provider with name "30885-5" with data
      | providers[0].firstName                       | Sam                  |
      | providers[0].lastName                        | Cloony                       |
      | providers[0].middleName                      | s                 |
      | providers[0].programTypeCd                   | MEDICAID                |
      | providers[0].stateProviderId                 | 30885-5.providers[0].stateProviderId       |
      | providers[0].planCode                        | 400752220               |
      | providers[0].planName                        | ANTHEM                  |
      | providers[0].sexLimitsCd                     | F                        |
      | providers[0].subProgramTypeCd                | HealthyIndianaPlan      |
      | providers[0].groupName                       | REID HEALTH GROUP       |
      | providers[0].groupId                         | 100268496M              |
      | providers[0].providerAddress[0].addressLine1 | 1625 EAST JEFFERSON BLVD|
      | providers[0].providerAddress[0].city         | MISHAWAKA               |
      | providers[0].providerAddress[0].state        | IN                      |
      | providers[0].providerAddress[0].zipCode      | 46545                   |
      | providers[0].providerAddress[0].countyCd     | 071                     |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.firstName | Sam |
      | providerSearch.lastName | Cloony |
      | providerSearch.stateProviderId | 30885-5.providers[0].stateProviderId  |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | sexLimitsCd | B |

  @CP-28633 @CP-28633-01 @planProvidergression @planProvider @API-PP-Regression @mital
  Scenario: Update geo information - Verify RetryHeremapCall API and retryCount value
    When I will get the Authentication token for "IN-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "28633-01" with data
      | providers[0].firstName                       | Dan                  |
      | providers[0].lastName                        | Rodger                       |
      | providers[0].middleName                      | s                 |
      | providers[0].npi                             | null               |
      | providers[0].programTypeCd                   | MEDICAID                |
      | providers[0].stateProviderId                 | stateProviderId::       |
      | providers[0].planCode                        | 400752220               |
      | providers[0].planName                        | ANTHEM                  |
      | providers[0].sexLimitsCd                     | M                        |
      | providers[0].subProgramTypeCd                | HealthyIndianaPlan      |
      | providers[0].groupName                       | REID HEALTH GROUP       |
      | providers[0].groupId                         | 100268496M              |
      | providers[0].providerAddress[0].addressLine1 | rrrrrr|
      | providers[0].providerAddress[0].city         | xxxx               |
      | providers[0].providerAddress[0].state        | yy                      |
      | providers[0].providerAddress[0].zipCode      | 111                   |
      | providers[0].providerAddress[0].countyCd     | 222                     |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.firstName | Dan |
      | providerSearch.lastName | Rodger |
      | providerSearch.stateProviderId   | 28633-01.providers[0].stateProviderId       |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | lattitude | 0.0 |
      | longitude | 0.0 |
  Given I initiated Plan Management for retrying Heremap call API
    Then I send Put API call for retrying heremap call for "6" times
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.firstName | Dan |
      | providerSearch.lastName | Rodger |
      | providerSearch.stateProviderId   | 28633-01.providers[0].stateProviderId       |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      |retryCount  |  4   |

  @CP-45622 @CP-45622-1 @planProvidergression @planProvider @API-PP-Regression @deepa
  Scenario Outline: Provider Updates - Gender Served Search Criteria - cancelled Reconciliation Gender,pcpFlag,Acceptobt,Handicap,Classificationid served as last entry
    When I will get the Authentication token for "IN-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "45622-1" with data
      | providers[0].firstName                       | Sam                          |
      | providers[0].lastName                        | Cloony                       |
      | providers[0].npi                             | 1234578997                   |
      | providers[0].middleName                      | s                            |
      | providers[0].programTypeCd                   | MEDICAID                     |
      | providers[0].stateProviderId                 | stateProviderId::            |
      | providers[0].planCode                        | 400752220                    |
      | providers[0].planName                        | ANTHEM                       |
      | providers[0].sexLimitsCd                     | <name1>                      |
      | providers[0].subProgramTypeCd                | HealthyIndianaPlan           |
      | providers[0].groupName                       | REID HEALTH GROUP            |
      | providers[0].groupId                         | 100268496M                   |
      | providers[0].pcpFlag                         | <pcpFlagname1>               |
      | providers[0].classificationCd                | <Classification1>            |
      | providers[0].acceptObInd                     | <Acceptobind1>               |
      | providers[0].handicappedAccesibleInd         | <Handicappedaccessibleid1>   |
      | providers[0].providerAddress[0].addressLine1 | 1625 EAST JEFFERSON BLVD     |
      | providers[0].providerAddress[0].city         | MISHAWAKA                    |
      | providers[0].providerAddress[0].state        | IN                           |
      | providers[0].providerAddress[0].zipCode      | 46545                        |
      | providers[0].providerAddress[0].countyCd     | 071                          |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
      | warnings | null |
    And User send Api call with payload "createProvider" for creating provider with name "45622-1" with data
      | providers[0].firstName                       | Sam                                  |
      | providers[0].npi                             | 1234578997                           |
      | providers[0].lastName                        | Cloony                               |
      | providers[0].middleName                      | s                                    |
      | providers[0].programTypeCd                   | MEDICAID                             |
      | providers[0].stateProviderId                 | 45622-1.providers[0].stateProviderId |
      | providers[0].planCode                        | 400752220                            |
      | providers[0].planName                        | ANTHEM                               |
      | providers[0].sexLimitsCd                     | <name2>                              |
      | providers[0].subProgramTypeCd                | HealthyIndianaPlan                   |
      | providers[0].groupName                       | REID HEALTH GROUP                    |
      | providers[0].groupId                         | 100268496M                           |
      | providers[0].pcpFlag                         | <pcpFlagname2>                       |
      | providers[0].classificationCd                | <Classification2>                    |
      | providers[0].acceptObInd                     | <Acceptobind2>                       |
      | providers[0].handicappedAccesibleInd         | <Handicappedaccessibleid2>           |
      | providers[0].providerAddress[0].addressLine1 | 1625 EAST JEFFERSON BLVD             |
      | providers[0].providerAddress[0].city         | MISHAWAKA                            |
      | providers[0].providerAddress[0].state        | IN                                   |
      | providers[0].providerAddress[0].zipCode      | 46545                                |
      | providers[0].providerAddress[0].countyCd     | 071                                  |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.firstName       | Sam                                  |
      | providerSearch.lastName        | Cloony                               |
      | providerSearch.stateProviderId | 45622-1.providers[0].stateProviderId |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | sexLimitsCd                | <result>|
      | pcpFlag                    |<result1>|
      | classificationCd           |<result2>|
      | acceptObInd                |<result3>|
      | handicappedAccesibleInd    |<result4>|
    Examples:
      | name1 |  name2  | result | pcpFlagname1|pcpFlagname2  |  result1  | Classification1|Classification2|result2|Acceptobind1|Acceptobind2|result3|Handicappedaccessibleid1|Handicappedaccessibleid2|result4|
      | F     | M       | M      | Y           |   Y          |    Y      |        02      |    02         | 02    |   true     |    false   |false  | false                  |true                    |  true |
      | F     | F       | F      | N           |   Y          |    Y      |        01      |    02         | 02    |   false    |    false   |false  | true                   |false                   | false |
      | M     | M       | M      | Y           |   N          |    N      |        02      |    01         | 01    |   false    |    true    |true   | true                   |true                    | true  |
      | M     | F       | F      | N           |   N          |    N      |        01      |    01         | 01    |   true     |    true    |true   | false                  |false                   | false |

  @CP-45622 @CP-45622-2 @planProvidergression @planProvider @API-PP-Regression @deepa
  Scenario Outline:  Provider Updates - Gender Served Search Criteria - cancelled Reconciliation Gender,pcpFlag,Acceptobt,Handicap,Classificationid served as last entry
    When I will get the Authentication token for "BLCRM" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProvider" for creating provider with name "45622-2" with data
      | providers[0].firstName               | Sam                        |
      | providers[0].lastName                | Cloony                     |
      | providers[0].middleName              | s                          |
      | providers[0].sexLimitsCd             | <name1>                    |
      | providers[0].subProgramTypeCd        | HealthyIndianaPlan         |
      | providers[0].npi                     | random                     |
      | providers[0].pcpFlag                 | <pcpFlagname1>             |
      | providers[0].classificationCd        | <Classification1>          |
      | providers[0].acceptObInd             | <Acceptobind1>             |
      | providers[0].handicappedAccesibleInd | <Handicappedaccessibleid1> |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
      | warnings | null |
    And User send Api call with payload "createProvider" for creating provider with name "45622-2" with data
      | providers[0].firstName               | Sam                        |
      | providers[0].lastName                | Cloony                     |
      | providers[0].middleName              | s                          |
      | providers[0].sexLimitsCd             | <name2>                    |
      | providers[0].subProgramTypeCd        | HealthyIndianaPlan         |
      | providers[0].npi                     | random                     |
      | providers[0].pcpFlag                 | <pcpFlagname2>             |
      | providers[0].classificationCd        | <Classification2>          |
      | providers[0].acceptObInd             | <Acceptobind2>             |
      | providers[0].handicappedAccesibleInd | <Handicappedaccessibleid2> |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.firstName       | Sam                                  |
      | providerSearch.lastName        | Cloony                               |
      | providerSearch.stateProviderId | 45622-2.providers[0].stateProviderId |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | sexLimitsCd                | <result>|
      | pcpFlag                    |<result1>|
      | classificationCd           |<result2>|
      | acceptObInd                |<result3>|
      | handicappedAccesibleInd    |<result4>|

    Examples:
      | name1 |  name2  | result | pcpFlagname1|pcpFlagname2  |  result1  | Classification1|Classification2|result2|Acceptobind1|Acceptobind2|result3|Handicappedaccessibleid1|Handicappedaccessibleid2|result4|
      | F     | M       | M      | Y           |   Y          |    Y      |        02      |    02         | 02    |   true     |    false   |false  | false                  |true                    |  true |
      | F     | F       | F      | N           |   Y          |    Y      |        01      |    02         | 02    |   false    |    false   |false  | true                   |false                   | false |
      | M     | M       | M      | Y           |   N          |    N      |        02      |    01         | 01    |   false    |    true    |true   | true                   |true                    | true  |
      | M     | F       | F      | N           |   N          |    N      |        01      |    01         | 01    |   true     |    true    |true   | false                  |false                   | false |
  @CP-45622 @CP-45622-3 @planProvidergression @planProvider @API-PP-Regression @deepa
  Scenario Outline:  Provider Updates - Gender Served Search Criteria - cancelled Reconciliation Gender,pcpFlag,Acceptobt,Handicap,Classificationid served as last entry
    When I will get the Authentication token for "DC-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProviderDCEB" for creating provider with name "45622-3" with data
      | providers[0].firstName               | Sam                        |
      | providers[0].lastName                | Cloony                     |
      | providers[0].npi                     | random                     |
      | providers[0].planCode                | 044733300                  |
      | providers[0].acceptNewPatientsInd    | true                       |
      | providers[0].planName                | MEDSTAR FAMILY CHOICE      |
      | providers[0].subProgramTypeCd        | DCHF                       |
      | providers[0].sexLimitsCd             | <name1>                    |
      | providers[0].pcpFlag                 | <pcpFlagname1>             |
      | providers[0].classificationCd        | <Classification1>          |
      | providers[0].acceptObInd             | <Acceptobind1>             |
      | providers[0].handicappedAccesibleInd | <Handicappedaccessibleid1> |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
      | warnings | null |
    And User send Api call with payload "createProviderDCEB" for creating provider with name "45622-3" with data
      | providers[0].firstName               | Sam                        |
      | providers[0].lastName                | Cloony                     |
      | providers[0].npi                     | random                     |
      | providers[0].planCode                | 044733300                  |
      | providers[0].acceptNewPatientsInd    | true                       |
      | providers[0].planName                | MEDSTAR FAMILY CHOICE      |
      | providers[0].subProgramTypeCd        | DCHF                       |
      | providers[0].sexLimitsCd             | <name2>                    |
      | providers[0].pcpFlag                 | <pcpFlagname2>             |
      | providers[0].classificationCd        | <Classification2>          |
      | providers[0].acceptObInd             | <Acceptobind2>             |
      | providers[0].handicappedAccesibleInd | <Handicappedaccessibleid2> |
    Then I verify the response status code "200" with creation status "success"
    Then I verify errors and warnings in the response after creating provider with data
      | errors   | null |
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.firstName       | Sam                                  |
      | providerSearch.lastName        | Cloony                               |
      | providerSearch.stateProviderId | 45622-3.providers[0].stateProviderId |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | sexLimitsCd                | <result>|
      | pcpFlag                    |<result1>|
      | classificationCd           |<result2>|
      | acceptObInd                |<result3>|
      | handicappedAccesibleInd    |<result4>|

    Examples:
      | name1 |  name2  | result | pcpFlagname1|pcpFlagname2  |  result1  | Classification1|Classification2|result2|Acceptobind1|Acceptobind2|result3|Handicappedaccessibleid1|Handicappedaccessibleid2|result4|
      | F     | M       | M      | Y           |   Y          |    Y      |        02      |    02         | 02    |   true     |    false   |false  | false                  |true                    |  true |
      | F     | F       | F      | N           |   Y          |    Y      |        01      |    02         | 02    |   false    |    false   |false  | true                   |false                   | false |
      | M     | M       | M      | Y           |   N          |    N      |        02      |    01         | 01    |   false    |    true    |true   | true                   |true                    | true  |
      | M     | F       | F      | N           |   N          |    N      |        01      |    01         | 01    |   true     |    true    |true   | false                  |false                   | false |