@apiCreateProviderDCEB
Feature: DC-EB: Deactivate provider and provider data elements

  @CP-40063 @CP-40063-01 @planProviderAPI @API-TM @API-PP-Regression @planProvider @mital
  Scenario: Verify - Do Not Return DEACTIVATED Providers on Provider Search
    When I will get the Authentication token for "DC-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProviderDCEB" for creating provider with name "40063-1" with data
      | providers[0].firstName            | random                |
      | providers[0].lastName             | random                |
      | providers[0].npi                  | random                |
      | providers[0].planCode             | 044733300             |
      | providers[0].acceptNewPatientsInd | true                  |
      | providers[0].planName             | MEDSTAR FAMILY CHOICE |
      | providers[0].subProgramTypeCd     | DCHF                  |
    Then I verify the response status code "200" with creation status "success"
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data created via API
    And I click on "SEARCH" button on provider search page
    And I click on first found provider from provider search result
    And I verify the expected provider is fetched
    When I will get the Authentication token for "DC-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProviderDCEB" for updating provider with name "40063-1" with data
      | providers[0].effectiveEndDate | 2023-01-07T09:24:19.000Z |
    Then I verify the response status code "200" with creation status "success"
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data created via API
    And I click on "SEARCH" button on provider search page
    Then I should see "No records" message

  @CP-40063 @CP-40063-02 @planProviderAPI @API-TM @API-PP-Regression @planProvider @mital
  Scenario Outline: Verify - Do Not Return DEACTIVATED Providers Laguages, Specialties and Hospital Affiliations on Provider Search
    When I will get the Authentication token for "DC-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProviderDCEB" for creating provider with name "40063-1" with data
      | providers[0].firstName                                      | random                   |
      | providers[0].lastName                                       | random                   |
      | providers[0].npi                                            | random                   |
      | providers[0].planCode                                       | 044733300                |
      | providers[0].acceptNewPatientsInd                           | true                     |
      | providers[0].planName                                       | MEDSTAR FAMILY CHOICE    |
      | providers[0].subProgramTypeCd                               | DCHF                     |
      | providers[0].providerLanguages[0].languageTypeCd            | <languagesCd>            |
      | providers[0].providerSpeciality[0].specialityCd             | <specialtiesCd>          |
      | providers[0].providerHospitalAffilation[0].hospitalName     | <hospital affiliations>  |
      | providers[0].providerLanguages[0].effectiveEndDate          | 2023-01-01T09:24:19.000Z |
      | providers[0].providerSpeciality[0].effectiveEndDate         | 2023-01-01T09:24:19.000Z |
      | providers[0].providerHospitalAffilation[0].effectiveEndDate | 2023-01-01T09:24:19.000Z |
    Then I verify the response status code "200" with creation status "success"
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data created via API
    And I click on "SEARCH" button on provider search page
    And I click on first found provider from provider search result
    And I verify the expected provider is fetched
    And I verify "<languages>", "<specialties>" and "<hospital affiliations>" are not fetched
    Examples:
      | languagesCd | languages | specialtiesCd | specialties     | hospital affiliations |
      | HIN         | HINDI     | 060           | Family Medicine | Columbia Hospital     |

  @CP-42421 @CP-42421-01 @planProviderAPI @API-TM @API-PP-Regression @planProvider @mital
  Scenario: Verify insert and update provider hospital affiliations in DCEB
    When I will get the Authentication token for "DC-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProviderDCEB" for creating provider with name "42421-1" with data
      | providers[0].firstName                                  | random                |
      | providers[0].lastName                                   | random                |
      | providers[0].npi                                        | random                |
      | providers[0].planCode                                   | 044733300             |
      | providers[0].acceptNewPatientsInd                       | true                  |
      | providers[0].planName                                   | MEDSTAR FAMILY CHOICE |
      | providers[0].subProgramTypeCd                           | DCHF                  |
      | providers[0].providerHospitalAffilation[0].hospitalName | Test Hospital 1       |
      | providers[0].providerHospitalAffilation[1].hospitalName | Test Hospital 2       |
    Then I verify the response status code "200" with creation status "success"
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.firstName | 42421-1.providers[0].firstName |
      | providerSearch.lastName  | 42421-1.providers[0].lastName  |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | firstName                                               | 42421-1.providers[0].firstName       |
      | lastName                                                | 42421-1.providers[0].lastName        |
      | stateProviderId                                         | 42421-1.providers[0].stateProviderId |
      | providers[0].providerHospitalAffilation[0].hospitalName | Test Hospital 1                      |
      | providers[0].providerHospitalAffilation[1].hospitalName | Test Hospital 2                      |

  @CP-42421 @CP-42421-02 @planProviderAPI @API-TM @API-PP-Regression @planProvider @mital
  Scenario: Verify insert and update provider hospital affiliations in DCEB
    When I will get the Authentication token for "DC-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createProviderDCEB" for creating provider with name "42421-1" with data
      | providers[0].firstName                                  | random                |
      | providers[0].lastName                                   | random                |
      | providers[0].npi                                        | random                |
      | providers[0].planCode                                   | 044733300             |
      | providers[0].acceptNewPatientsInd                       | true                  |
      | providers[0].planName                                   | MEDSTAR FAMILY CHOICE |
      | providers[0].subProgramTypeCd                           | DCHF                  |
      | providers[0].providerHospitalAffilation[0].hospitalName | Test Hospital 1       |
    Then I verify the response status code "200" with creation status "success"
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.firstName | 42421-1.providers[0].firstName |
      | providerSearch.lastName  | 42421-1.providers[0].lastName  |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | firstName                                               | 42421-1.providers[0].firstName       |
      | lastName                                                | 42421-1.providers[0].lastName        |
      | providers[0].providerHospitalAffilation[0].hospitalName | Test Hospital 1                      |
      | providers[0].providerHospitalAffilation[1].hospitalName | null                                 |

  @CP-42422 @CP-42422-01 @CP-42402 @CP-42401-02 @CP-42402-01 @planProviderAPI @API-TM @API-PP-Regression @planProvider @mital
  Scenario: Verify insert and update provider subprogram types in DCEB
            Verify SubprogramType using Provider Search API and Provider Digital Search API
            Verify provider results for those affiliated with multiple subprograms
    When I will get the Authentication token for "DC-EB" in "CRM"
    Given I initiated Plan Management for service provider API with new version
    And User send Api call with payload "createProviderDCEBMultipleSubprogramTypes" for creating provider with name "42422-1" with data
      | providers[0].firstName                                  | random                |
      | providers[0].lastName                                   | random                |
      | providers[0].npi                                        | random                |
      | providers[0].planCode                                   | 044733300             |
      | providers[0].acceptNewPatientsInd                       | true                  |
      | providers[0].planName                                   | MEDSTAR FAMILY CHOICE |
      | providers[0].subProgramTypeCd                           | DCHF                  |
      | providers[0].providerSubprogramType[0].subprogramTypeCd | Alliance              |
      | providers[0].providerSubprogramType[1].subprogramTypeCd | ImmigrantChildren    |
    Then I verify the response status code "200" with creation status "success"
    When I initiate Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.firstName | 42422-1.providers[0].firstName |
      | providerSearch.lastName  | 42422-1.providers[0].lastName  |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | firstName                                               | 42422-1.providers[0].firstName |
      | lastName                                                | 42422-1.providers[0].lastName  |
      | providers[0].providerSubprogramType[0].subprogramTypeCd | Alliance                       |
      | providers[0].providerSubprogramType[1].subprogramTypeCd | ImmigrantChildren             |
    When I initiate digital Provider Search API
    And I create a request payload with data for search field values and post
      | providerSearch.firstName | 42422-1.providers[0].firstName |
      | providerSearch.lastName  | 42422-1.providers[0].lastName  |
    Then I verify the response status code "200" with creation status "success"
    Then I verify first found provider search result from api with data
      | firstName                                               | 42422-1.providers[0].firstName |
      | lastName                                                | 42422-1.providers[0].lastName  |
      | providers[0].providerSubprogramType[0].subprogramTypeCd | Alliance                       |
      | providers[0].providerSubprogramType[1].subprogramTypeCd | ImmigrantChildren             |
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      | FIRST NAME | 42422-1.providers[0].firstName |
      | LAST NAME  | 42422-1.providers[0].lastName   |
    And I click on "SEARCH" button on provider search page
    Then I verify provider search results table fields At A Glance View with data
      | PROGRAM NAME     | is displayed              |
    And I verify program name values displayed as
      | Alliance          |
      | ImmigrantChildren |

  @CP-43099 @CP-43099-01 @planProviderAPI @API-TM @API-PP-Regression @planProvider @mital
  Scenario: Verify provider Hospital Affilation names are displayed correctly in provider search result by creating provider with Hospital Codes instead of Names
    When I will get the Authentication token for "DC-EB" in "Tenant Manager"
    Given I initiated Plan Management for service provider API
    And User send Api call with payload "createdProviderWithHospitalAffiliationCodes" for creating provider with name "43099-1" with data
      | providers[0].firstName                                  | random                |
      | providers[0].lastName                                   | random                |
      | providers[0].npi                                        | random                |
      | providers[0].planCode                                   | 044733300             |
      | providers[0].acceptNewPatientsInd                       | true                  |
      | providers[0].planName                                   | MEDSTAR FAMILY CHOICE |
      | providers[0].subProgramTypeCd                           | DCHF                  |
      | providers[0].providerHospitalAffilation[0].hospitalName | 100001       |
      | providers[0].providerHospitalAffilation[1].hospitalName | 101240       |
      | providers[0].providerHospitalAffilation[2].hospitalName | 104320       |
    Then I verify the response status code "200" with creation status "success"
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Provider Search page
    And I click on "Advanced Search" button on provider search page
    When I fill out fields on provider search page with data
      | FIRST NAME | 43099-1.providers[0].firstName |
      | LAST NAME  | 43099-1.providers[0].lastName   |
    And I click on "SEARCH" button on provider search page
    And I click on first found provider from provider search result
    And I verify the expected provider is fetched
    And I verify the provider is affiliated to following hospitals
      | Children`s National Medical Center |
      | Hadley Hospital                    |
      | George Washington Hospital         |
