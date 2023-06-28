Feature: Digital Integration Authentication/Primary Contact Info/ Active Member

  @API-CP-8693 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Digital Authentication of all Role Consumers
    Given I will get the Authentication token for "" in "CRM"
    When I initiated Case Loader API for Create New Consumer for Digital Integration
    And I set up test data for a new "<status>" caseConsumer with "<role>" created by caseloader with "<primary>", "<contactType>", "<channel>"
    And I run the CaseLoader PUT API to Create New Consumer for Digital Integration
    When I get a case id and consumer id created from Consumer Search API
    And I initiate digital Authentication api call with parameters
    And I store Consumer details data for digital authentication api call "<dob>", "<4lastDigitsSSN>", "<FN>", "<LN>", "<externalId>", "<externalIdType>"
    And I run the Digital Authentication POST API
    Then I validate response according to request parameters "<dob>", "<4lastDigitsSSN>", "<FN>", "<LN>", "<externalId>", "<externalIdType>", "<status>"
    Examples:
      | dob     | 4lastDigitsSSN | FN      | LN      | externalId | externalIdType | status   | role | primary | contactType | channel |
#      |[blank]|                |[blank]|         |[blank]|                |[blank]| CM   |[blank]|             |[blank]|
#      |[blank]|                |[blank]|         |[blank]|                | Inactive | CM   |[blank]|             |[blank]|
#      | invalid |[blank]|         |[blank]|            |[blank]|          | CM   |[blank]|             |[blank]|
#      |[blank]| invalid        |[blank]|         |[blank]|                |[blank]| CM   |[blank]|             |[blank]|
#      |[blank]|                | invalid |[blank]|            |[blank]|          | CM   |[blank]|             |[blank]|
#      |[blank]|                |[blank]| invalid |[blank]|                |[blank]| CM   |[blank]|             |[blank]|
#      |[blank]|                |[blank]|         | invalid    |[blank]|          | CM   |[blank]|             |[blank]|
#      |[blank]|                |[blank]|         |[blank]| invalid        |[blank]| CM   |[blank]|             |[blank]|
#      |[blank]|                |[blank]|         |[blank]|                |[blank]| PI   |[blank]|             |[blank]|
      |[blank]|                |[blank]|         |[blank]|                | Inactive | PI   |[blank]|             |[blank]|
      | invalid |[blank]|         |[blank]|            |[blank]|          | PI   |[blank]|             |[blank]|
      |[blank]| invalid        |[blank]|         |[blank]|                |[blank]| PI   |[blank]|             |[blank]|
      |[blank]|                | invalid |[blank]|            |[blank]|          | PI   |[blank]|             |[blank]|
      |[blank]|                |[blank]| invalid |[blank]|                |[blank]| PI   |[blank]|             |[blank]|
      |[blank]|                |[blank]|         | invalid    |[blank]|          | PI   |[blank]|             |[blank]|
      |[blank]|                |[blank]|         |[blank]| invalid        |[blank]| PI   |[blank]|             |[blank]|
      |[blank]|                |[blank]|         |[blank]|                |[blank]| PI   |[blank]|             |[blank]|
#      |[blank]|                |[blank]|         |[blank]|                |[blank]| AR   |[blank]|             |[blank]|
#      |[blank]|                |[blank]|         |[blank]|                | Inactive | AR   |[blank]|             |[blank]|
#      | invalid |[blank]|         |[blank]|            |[blank]|          | AR   |[blank]|             |[blank]|
#      |[blank]| invalid        |[blank]|         |[blank]|                |[blank]| AR   |[blank]|             |[blank]|
#      |[blank]|                | invalid |[blank]|            |[blank]|          | AR   |[blank]|             |[blank]|
#      |[blank]|                |[blank]| invalid |[blank]|                |[blank]| AR   |[blank]|             |[blank]|
#      |[blank]|                |[blank]|         | invalid    |[blank]|          | AR   |[blank]|             |[blank]|
#      |[blank]|                |[blank]|         |[blank]| invalid        |[blank]| AR   |[blank]|             |[blank]|


  @API-CP-31912 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Updated Digital Authentication for null external id parameters
    Given I will get the Authentication token for "" in "CRM"
    When I initiated Case Loader API for Create New Consumer for Digital Integration
    And I set up test data for a new "<status>" caseConsumer with "<role>" created by caseloader with "<primary>", "<contactType>", "<channel>"
    And I run the CaseLoader PUT API to Create New Consumer for Digital Integration
    When I get a case id and consumer id created from Consumer Search API
    And I initiate digital Authentication api call with parameters
    And I store Consumer details data for digital authentication api call "<dob>", "<4lastDigitsSSN>", "<FN>", "<LN>", "<externalId>", "<externalIdType>"
    And I run the Digital Authentication POST API
    Then I validate response according to request parameters "<dob>", "<4lastDigitsSSN>", "<FN>", "<LN>", "<externalId>", "<externalIdType>", "<status>"
    Examples:
      | dob | 4lastDigitsSSN | FN | LN | externalId | externalIdType | status | role | primary | contactType | channel |
      |[blank]|                |[blank]|    | null       |[blank]|        | PI   |[blank]|             |[blank]|
      |[blank]|                |[blank]|    |[blank]| null           |[blank]| PI   |[blank]|             |[blank]|

  @API-CP-31912 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Updated Digital Authentication for missing field paramethers
    Given I will get the Authentication token for "" in "CRM"
    When I initiated Case Loader API for Create New Consumer for Digital Integration
    And I set up test data for a new "<status>" caseConsumer with "<role>" created by caseloader with "<primary>", "<contactType>", "<channel>"
    And I run the CaseLoader PUT API to Create New Consumer for Digital Integration
    When I get a case id and consumer id created from Consumer Search API
    And I initiate digital Authentication api call with parameters
    And I store Consumer details data for digital authentication api call "<dob>", "<4lastDigitsSSN>", "<FN>", "<LN>", "<externalId>", "<externalIdType>"
    And I run the Digital Authentication POST API
    Then I validate response null to request parameters "<dob>", "<4lastDigitsSSN>", "<FN>", "<LN>", "<externalId>", "<externalIdType>", "<status>"
    Examples:
      | dob  | 4lastDigitsSSN | FN   | LN   | externalId | externalIdType | status | role | primary | contactType | channel |
      | null |[blank]|      |[blank]| null       | null           |[blank]| PI   |[blank]|             |[blank]|
      |[blank]| null           |[blank]|      | null       | null           |[blank]| PI   |[blank]|             |[blank]|
      |[blank]|                | null |[blank]| null       | null           |[blank]| PI   |[blank]|             |[blank]|
      |[blank]|                |[blank]| null | null       | null           |[blank]| PI   |[blank]|             |[blank]|


  @API-CP-9025 @API-CP-9025-01 @API-CP-10436 @API-CP-10436-01 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Business API RETRIEVE case level contact information display error messages case is null or not found
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Case Loader API for Create New Consumer for Digital Integration
    And I initiate the Digital Primary Contact GET API with unexpected Case Id to trigger "<message>"
    And I run the Digital Primary Contact GET API
    Then I validate error code "<code>" with error message "<message>" in response according to request parameters
    Examples:
      | code    | message                        |
      | CASE001 | caseId cannot be null.         |
      | CASE002 | Case not found or is inactive. |


  @API-CP-9025 @API-CP-9025-02 @API-CP-10436 @API-CP-10436-02 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Business API RETRIEVE case level contact information display error messages when PI status is Inactive
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Case Loader API for Create New Consumer for Digital Integration
    And I set up test data for a new "<status>" caseConsumer with "<role>" created by caseloader with "<primary>", "<contactType>", "<channel>"
    And I run the CaseLoader PUT API to Create New Consumer for Digital Integration
    When I get a case id and consumer id created from Consumer Search API
    And I initiate Digital Primary Contact api call with parameters
    And I run the Digital Primary Contact GET API
    Then I validate error code "CASE003" with error message "No active consumers found under selected CaseId." in response according to request parameters
    Examples:
      | status   | role | primary | contactType | channel |
      | Inactive | PI   | true    | email       |[blank]|
      | Inactive | PI   | true    | address     |[blank]|
      | Inactive | PI   | true    | phone       | Home    |
      | Inactive | PI   | true    | phone       | Cell    |
      | Inactive | PI   | true    | phone       | Work    |
      | Inactive | PI   | true    | phone       | Fax     |


  @API-CP-9025 @API-CP-9025-03 @API-CP-10436 @API-CP-10436-03 @API-CC @API-CRM-Regression @muhabbat #1 scenario falls due to CP-14013 defect
  Scenario Outline: Business API to RETRIEVE case level contact information
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Case Loader API for Create New Consumer for Digital Integration
    And I set up test data for a new "<status>" caseConsumer with "<role>" created by caseloader with "<primary>", "<contactType>", "<channel>"
    And I run the CaseLoader PUT API to Create New Consumer for Digital Integration
    When I get a case id and consumer id created from Consumer Search API
    And I initiate Digital Primary Contact api call with parameters
    And I run the Digital Primary Contact GET API
    Then I validate response according to request parameters "<status>", "<primary>", "<contactType>", "<channel>", "<inResponse>"
    Examples:
      | status | role | primary | contactType | channel | inResponse |
      |[blank]| PI   | true    | email       |[blank]| true       |
      |[blank]| PI   | true    | address     |[blank]| true       |
      |[blank]| PI   | true    | phone       | Home    | true       |
      |[blank]| PI   | true    | phone       | Cell    | true       |
      |[blank]| PI   | true    | phone       | Work    | true       |
      |[blank]| PI   | true    | phone       | Fax     | false      |
      |[blank]| PI   | false   | email       |[blank]| false      |
      |[blank]| PI   | false   | phone       | Home    | false      |
      |[blank]| PI   | false   | phone       | Cell    | false      |
      |[blank]| PI   | false   | phone       | Work    | false      |
      |[blank]| PI   | false   | phone       | Fax     | false      |

  @API-CP-8692 @API-CP-8692-01 @API-CP-8692-02 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: BusinessI initiated Case Loader API for Create New Consumer for Digital Integration API RETRIEVE Case/Consumer Information information display error messages case is null or not found
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Case Loader API for Create New Consumer for Digital Integration
    And I initiate the CaseConsumer Information GET API with unexpected Case Id to trigger "<message>"
    And I run the Digital CaseConsumer Information GET API
    Then I validate error code "<code>" with error message "<message>" in response according to request parameters
    Examples:
      | code    | message                        |
      | CASE001 | caseId cannot be null.         |
      | CASE002 | Case not found or is inactive. |

  @API-CP-8692 @API-CP-8692-03 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Business API RETRIEVE Case/Consumer Information information display error messages when no active Consumer on the case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Case Loader API for Create New Consumer for Digital Integration
    And I set up test data for a new "<status>" caseConsumer with "<role>" created by caseloader with "", "", ""
    And I run the CaseLoader PUT API to Create New Consumer for Digital Integration
    When I get a case id and consumer id created from Consumer Search API
    And I initiate Digital Case Consumer Information api call with parameters
    And I store Consumer details data for digital authentication api call "", "", "", "", "", ""
    And I run the Digital CaseConsumer Information GET API
    Then I validate error code "CASE003" with error message "No active consumers found under selected CaseId." in response according to request parameters
    Examples:
      | status   | role |
      | Inactive | CM   |
      | Inactive | PI   |
      | Inactive | AR   |

  @API-CP-8692 @API-CP-8692-04 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Business API RETRIEVE Case/Consumer Information information on the case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Case Loader API for Create New Consumer for Digital Integration
    And I set up test data for a new "" caseConsumer with "<role>" created by caseloader with "", "", ""
    And I run the CaseLoader PUT API to Create New Consumer for Digital Integration
    When I get a case id and consumer id created from Consumer Search API
    And I initiate Digital Case Consumer Information api call with parameters
    And I store Consumer details data for digital authentication api call "", "", "", "", "", ""
    And I run the Digital CaseConsumer Information GET API
    Then I validate response according to CaseConsumer Information request parameters "<role>"
    Examples:
      | role |
      | CM   |
      | PI   |
      | AR   |

  @API-CP-9026 @API-CP-9026-01 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline:Verify Primary Active Address information updated using digital endpoint
    Given I will get the Authentication token for "" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].contacts.address.primaryIndicator                  | bool::true |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressCity                       | name::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressCounty                     | name::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressState                      | California |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressStreet1                    | name::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressStreet2                    | name::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressType                       | Mailing    |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressZip                        | 22222      |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressZipFour                    | 1111       |
      | caseLoaderRequest[0].case.consumers[0].correlationId                                      | npi::      |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::      |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | npi::      |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::     |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::     |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | npi::      |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I initiate the Update Primary Contact info API
    Then I send api call to Update Primary Address info
      | caseId                                 | c.object.result[0].cases.caseId |
      | caseContacts.address.addressStreet1    | <addressStreet1>                |
      | caseContacts.address.addressStreet2    | <addressStreet2>                |
      | caseContacts.address.addressState      | <addressState>                  |
      | caseContacts.address.addressCounty     | <addressCounty>                 |
      | caseContacts.address.addressCountyCode | <addressCountyCode>             |
      | caseContacts.address.addressCity       | <addressCity>                   |
      | caseContacts.address.addressZip        | <addressZip>                    |
      | caseContacts.address.addressZipFour    | <addressZip4>                   |
    Then Send api call with CaseId to Contact Primary API
    And Verify response primary Contact Address updated with "<callName>", "<addressStreet1>", "<addressStreet2>", "<addressState>", "<addressCounty>", "<addressCountyCode>", "<addressCity>", "<addressZip>", "<addressZip4>" all detail with "<addressType>" address type
    Examples:
      | addressType | callName | addressStreet1  | addressStreet2 | addressState | addressCounty  | addressCountyCode | addressCity  | addressZip | addressZip4 |
      | Mailing     | c19      | 123 Updated AS1 | Updated AS2    | AZ           | Updated County | 1234              | Updated City | 55555      | 4444        |

  #  @API-CP-9026 @API-CP-9026-02 @API-CC @API-CRM-Regression @muhabbat retired due to CP-13048 implementation
  Scenario Outline:Verify Primary Address update generated ADDRESS_SAVE_EVENT using digital endpoint
    Given I will get the Authentication token for "" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].contacts.address.primaryIndicator                  | bool::true |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressCity                       | name::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressCounty                     | name::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressState                      | California |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressStreet1                    | name::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressStreet2                    | name::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressType                       | Mailing    |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressZip                        | 33333      |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressZipFour                    | 2222       |
      | caseLoaderRequest[0].case.consumers[0].correlationId                                      | npi::      |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::      |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | npi::      |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::     |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::     |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | npi::      |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I initiate the Update Primary Contact info API
    Then I send api call to Update Primary Address info
      | caseId                                 | c.object.result[0].cases.caseId |
      | caseContacts.address.addressStreet1    | <addressStreet1>                |
      | caseContacts.address.addressStreet2    | <addressStreet2>                |
      | caseContacts.address.addressState      | <addressState>                  |
      | caseContacts.address.addressCounty     | <addressCounty>                 |
      | caseContacts.address.addressCountyCode | <addressCountyCode>             |
      | caseContacts.address.addressCity       | <addressCity>                   |
      | caseContacts.address.addressZip        | <addressZip>                    |
      | caseContacts.address.addressZipFour    | <addressZip4>                   |
    Then Send api call with CaseId to Contact Primary API
    And Verify response primary Contact Address updated with "<callName>", "<addressStreet1>", "<addressStreet2>", "<addressState>", "<addressCounty>", "<addressCountyCode>", "<addressCity>", "<addressZip>", "<addressZip4>" all detail with "<addressType>" address type
    And I initiate the Events APi
    Then User send Api call to get "ADDRESS_SAVE_EVENT"
    Then Verify "ADDRESS_SAVE_EVENT" response payload with "<addressType>" address Type "<addressStreet1>", "<addressStreet2>", "<addressState>", "<addressCounty>", "<addressCountyCode>", "<addressCity>", "<addressZip>", "<addressZip4>"
    Examples:
      | addressType | callName | addressStreet1  | addressStreet2 | addressState | addressCounty  | addressCountyCode | addressCity  | addressZip | addressZip4 |
      | Mailing     | c20      | 123 Updated AS1 | Updated AS2    | AZ           | Updated County | 1234              | Updated City | 55555      | 4444        |

  #  @API-CP-9026 @API-CP-9026-03 @API-CC @API-CRM-Regression @muhabbat retired due to CP-13048 implementation
  Scenario Outline:Verify Primary Address update generated ADDRESS_UPDATE_EVENT for previous Address with digital endpoint
    Given I will get the Authentication token for "" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].contacts.address.primaryIndicator                  | bool::true |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressCity                       | name::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressCounty                     | name::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressState                      | California |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressStreet1                    | name::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressStreet2                    | name::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressType                       | Mailing    |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressZip                        | 44444      |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressZipFour                    | 3333       |
      | caseLoaderRequest[0].case.consumers[0].correlationId                                      | npi::      |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::      |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | npi::      |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::     |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::     |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | npi::      |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then Send api call with CaseId to Contact Primary API to retrive Initial address info
    Then I initiate the Update Primary Contact info API
    Then I send api call to Update Primary Address info
      | caseId                                 | c.caseId            |
      | caseContacts.address.addressStreet1    | <addressStreet1>    |
      | caseContacts.address.addressStreet2    | <addressStreet2>    |
      | caseContacts.address.addressState      | <addressState>      |
      | caseContacts.address.addressCounty     | <addressCounty>     |
      | caseContacts.address.addressCountyCode | <addressCountyCode> |
      | caseContacts.address.addressCity       | <addressCity>       |
      | caseContacts.address.addressZip        | <addressZip>        |
      | caseContacts.address.addressZipFour    | <addressZip4>       |
    Then Send api call with CaseId to Contact Primary API
#    And Verify response primary Contact Address updated with "<callName>", "<addressStreet1>", "<addressStreet2>", "<addressState>", "<addressCounty>", "<addressCountyCode>", "<addressCity>", "<addressZip>", "<addressZip4>" all detail with "<addressType>" address type
    And I initiate the Events APi
    Then User send Api call to get "ADDRESS_UPDATE_EVENT"
    Then Verify "ADDRESS_UPDATE_EVENT" response payload with "<addressType>" address Type "", "", "", "", "", "", "", ""
    Examples:
      | addressType | callName | addressStreet1  | addressStreet2 | addressState | addressCounty  | addressCountyCode | addressCity  | addressZip | addressZip4 |
      | Mailing     | c200     | 123 Updated AS1 | Updated AS2    | AZ           | Updated County | 1234              | Updated City | 55555      | 4444        |

  @api-smoke-devops
  Scenario: Business API RETRIEVE Case/Consumer Information information on the case (for DevOps smoke)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Case Loader API for Create New Consumer for Digital Integration
    And I set up test data for a new "" caseConsumer with "CM" created by caseloader with "", "", ""
    And I run the CaseLoader PUT API to Create New Consumer for Digital Integration
    When I get a case id and consumer id created from Consumer Search API
    And I initiate Digital Case Consumer Information api call with parameters
    And I store Consumer details data for digital authentication api call "", "", "", "", "", ""
    And I run the Digital CaseConsumer Information GET API
    Then I validate response according to CaseConsumer Information request parameters "CM"


  @API-CP-13048 @API-CP-13048-01 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Digital API to RETRIEVE case level contact information
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Case Loader API for Create New Consumer for Digital Integration
    And I set up test data for a new "<status>" caseConsumer with "<role>" created by caseloader with "<primary>", "<contactType>", "<channel>"
    And I run the CaseLoader PUT API to Create New Consumer for Digital Integration
    When I get a case id and consumer id created from Consumer Search API
    And I initiate Digital Primary Contact api call with parameters
    And I run the Digital Primary Contact GET API
    Then I validate response according to request parameters "<status>", "<primary>", "<contactType>", "<channel>", "<inResponse>"
    Examples:
      | status | role | primary | contactType | channel | inResponse |
      |[blank]| PI   | false   | address     |[blank]| true       |


  @API-CP-13048 @API-CP-13048-02 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline:Verify Primary Indicator is not cuptured when Address updated using digital endpoint
    Given I will get the Authentication token for "" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].contacts.address.primaryIndicator                  | bool::true |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressCity                       | name::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressCounty                     | name::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressState                      | California |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressStreet1                    | name::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressStreet2                    | name::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressType                       | Mailing    |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressZip                        | 22222      |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressZipFour                    | 1111       |
      | caseLoaderRequest[0].case.consumers[0].correlationId                                      | npi::      |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::      |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | npi::      |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::     |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::     |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | npi::      |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I initiate the Update Primary Contact info API
    Then I send api call to Update Primary Address info
      | caseId                                 | c.object.result[0].cases.caseId |
      | caseContacts.address.addressStreet1    | <addressStreet1>                |
      | caseContacts.address.addressStreet2    | <addressStreet2>                |
      | caseContacts.address.addressState      | <addressState>                  |
      | caseContacts.address.addressCounty     | <addressCounty>                 |
      | caseContacts.address.addressCountyCode | <addressCountyCode>             |
      | caseContacts.address.addressCity       | <addressCity>                   |
      | caseContacts.address.addressZip        | <addressZip>                    |
      | caseContacts.address.addressZipFour    | <addressZip4>                   |
    Then Send api call with CaseId to Contact Primary API
    And Verify response primary Contact Address updated with "<callName>", "<addressStreet1>", "<addressStreet2>", "<addressState>", "<addressCounty>", "<addressCountyCode>", "<addressCity>", "<addressZip>", "<addressZip4>" all detail with "<addressType>" address type
    Examples:
      | addressType | callName | addressStreet1  | addressStreet2 | addressState | addressCounty  | addressCountyCode | addressCity  | addressZip | addressZip4 |
      | Mailing     | c22      | 321 Updated Str | Updated Str    | MD           | Updated County | 4321              | Updated City | 55555      | 4444        |