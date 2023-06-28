Feature: DC-EB: Create and update consumer using API

  @API-CP-38721 @API-CP-38721-01 @API-CP-37456 @API-CP-36575 @CP-37457 @Beka @API-DC-CC
  Scenario Outline: When a new consumer comes up on the source file for DC-EB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "1001" with following payload
      | consumerRequests[0].uuid                       | <uuid>        |
      | consumerRequests[0].dataSource                 | <dataSource>  |
      | consumerRequests[0].firstName                  | <firstName>   |
      | consumerRequests[0].lastName                   | <lastName>    |
      | consumerRequests[0].middleName                 | <middleName>  |
      | consumerRequests[0].suffix                     | <suffix>      |
      | consumerRequests[0].ssn                        | <ssn>         |
      | consumerRequests[0].dateOfBirth                | <dateOfBirth> |
      | consumerRequests[0].consumerProfile.type       | <type>        |
      | consumerRequests[0].consumerProfile.externalId | <externalId>  |
      | consumerRequests[0].case                       | null          |
    Then I validate response have following data
      | status       | success            |
      | profileId    | new                |
      | errorMessage | null               |
      | passedRules  | null               |
    Examples:
      | uuid   | dataSource | firstName | lastName | ssn    | dateOfBirth | externalId | type     | middleName | suffix  |
      | random | MMIS       | random    | random   | random | random      | null       | MEDICAID | random     | DDS     |
      | random | MMIS       | random    | random   | random | random      | null       | MCO      | random     | ESQ     |
      | random | MCO        | random    | random   | random | random      | null       | MCO      | random     | MD      |
      | random | MCO        | random    | random   | random | random      | null       | MEDICAID | random     | PHD     |
      | random | MMIS       | random    | random   | random | random      | random     | MEDICAID | random     | RN      |
      | random | MMIS       | random    | random   | random | random      | random     | MCO      | random     | Sr      |
      | random | MCO        | random    | random   | random | random      | random     | MCO      | random     | Unknown |
      | random | MCO        | random    | random   | random | random      | random     | MEDICAID | random     | V       |

  @API-CP-38721 @API-CP-38721-03 @API-CP-37456 @API-CP-36575 @CP-37457 @Beka @API-DC-CC
  Scenario Outline: Update existing consumer profile by matching consumer CONSUMER_PROFILE without external ID for DC-EB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "1002" with following payload
      | consumerRequests[0].uuid                       | <uuid>        |
      | consumerRequests[0].dataSource                 | <dataSource>  |
      | consumerRequests[0].firstName                  | <firstName>   |
      | consumerRequests[0].lastName                   | <lastName>    |
      | consumerRequests[0].middleName                 | <middleName>  |
      | consumerRequests[0].suffix                     | <suffix>      |
      | consumerRequests[0].ssn                        | <ssn>         |
      | consumerRequests[0].dateOfBirth                | <dateOfBirth> |
      | consumerRequests[0].consumerProfile.type       | <type>        |
      | consumerRequests[0].consumerProfile.externalId | <externalId>  |
      | consumerRequests[0].case                       | null          |
    When I update consumers API name "1002" with following payload
      | consumerRequests[0].uuid                       | same |
      | consumerRequests[0].dataSource                 | same |
      | consumerRequests[0].firstName                  | same |
      | consumerRequests[0].lastName                   | same |
      | consumerRequests[0].middleName                 | same |
      | consumerRequests[0].suffix                     | same |
      | consumerRequests[0].ssn                        | same |
      | consumerRequests[0].dateOfBirth                | same |
      | consumerRequests[0].consumerProfile.type       | same |
      | consumerRequests[0].consumerProfile.externalId | same |
    Then I validate response have following data
      | status       | success                       |
      | uuid         | uuid-20220926-0001            |
      | consumerId   | same                          |
      | profileId    | Not a same                    |
      | errorMessage | ::null                        |
      | passedRules  | CONSUMER_NAME_DOB_SEX_ADDRESS |
    Examples:
      | uuid   | dataSource | firstName | lastName | ssn    | dateOfBirth | externalId | type     | middleName | suffix |
      | random | MMIS       | random    | random   | random | random      | null       | MEDICAID | random     | DDS    |
      | random | MMIS       | random    | random   | random | random      | null       | MCO      | random     | ESQ    |
      | random | MCO        | random    | random   | random | random      | null       | MCO      | random     | MD     |

  @API-CP-39039 @API-CP-39039-01 @chopa @API-DC-CC
  Scenario Outline: Search consumer profile for DC-EB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    Then I search for created consumer using search api by "<key>"
    Then I verify consumer details are available based on "<key>" search parameters
    Examples:
      | key         |
      | consumerSSN |
      | Medicaid    |

  @API-CP-48521 @API-CP-48521-01 @chopa @API-DC-CC
  Scenario: Verify mergedProfileId field is displayed for search consumer call
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    Then I search for created consumer using search api by "Medicaid"
    Then I verify mergedProfileId field is displayed


  @API-CP-39354 @API-CP-39354-01 @muhabbat @API-DC-CC
  Scenario: Search response sorting order for DC-EB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    Then I search for created consumer using search api by "profileId"
    Then I verify consumer profiles details are in expected order
      | MEDICAID          |
      | MCO               |
      | Consumer Reported |