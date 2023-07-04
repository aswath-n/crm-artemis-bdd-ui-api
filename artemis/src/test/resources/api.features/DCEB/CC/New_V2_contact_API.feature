Feature: DC-EB: Saving and Updating Consumer Reported Contacts API

@API-CP-40323 @API-CP-40323-01 @Beka @API-DC-CC
Scenario: Create a consumer reported profile
  Given I will get the Authentication token for "DC-EB" in "CRM"
  And I initiate consumers V2 API
  When I run consumers API name "40323-01" with following payload
    | consumerRequests[0].uuid                       | random   |
    | consumerRequests[0].dataSource                 | MMIS     |
    | consumerRequests[0].firstName                  | random   |
    | consumerRequests[0].lastName                   | random   |
    | consumerRequests[0].middleName                 | random   |
    | consumerRequests[0].suffix                     | DDS      |
    | consumerRequests[0].ssn                        | random   |
    | consumerRequests[0].dateOfBirth                | random   |
    | consumerRequests[0].consumerProfile.type       | MEDICAID |
    | consumerRequests[0].consumerProfile.externalId | random   |
    | consumerRequests[0].case.type                  | MEDICAID |
    | consumerRequests[0].case.externalId            | random   |
  When I initiate contacts V2 API
  And I run contacts V2 API with following data
  |requestBy| profileId|
  Then I verify new profile created
  And I initiate consumers V2 API
  When I search consumer profile using v2 search API
  Then I verify search response has following data
  |profileId |just created|
  |type      |Consumer Reported|

  @API-CP-40323 @API-CP-40323-02 @Beka @API-DC-CC
  Scenario: Create and update profile with using externalID
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "40323-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I initiate contacts V2 API
    And I run contacts V2 API with following data
      |requestBy| externalId|
    Then I verify new profile created

  @API-CP-40323 @API-CP-40323-03 @Beka @API-DC-CC
    Scenario:  Verify  payload has all the 3 parameters populated- externalId,
               type and profileId then the search logic shall be give priority to profileId
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "40323-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I initiate contacts V2 API
    And  I run contacts V2 API with following data
      |requestBy| profileId and notExtistingExternalID|
    Then I verify new profile created
    And  I initiate consumers V2 API
    When I search consumer profile using v2 search API
    Then I verify search response has following data
      |profileId |just created|
      |type      |Consumer Reported|

  @API-CP-40323 @API-CP-40323-04 @Beka @API-DC-CC
  Scenario:  Validation for the following required fields, the error codes and error messages
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "40323-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I initiate contacts V2 API
    When I add new incorrect "address" for created profile to check error code
    Then I verify response contains following eror code "ERRADDRESS01" "Required fields are missing or not in correct format"
    When I add new incorrect "phone" for created profile to check error code
    Then I verify response contains following eror code "ERRPHONE01" "Required fields are missing or not in correct format"
    When I add new incorrect "email" for created profile to check error code
    Then I verify response contains following eror code "ERREMAIL01" "Required fields are missing or not in correct format"

  @API-CP-40323 @API-CP-40323-05 @Beka @API-DC-CC
  Scenario: Verify response error codes when pass wrong address/phone/email ENUM type
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "40323-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I initiate contacts V2 API
    When I add new incorrect "addressType" for created profile to check error code
    Then I verify response contains following eror code "ERRENUMVALUE01" "Unexpected enum value for the field address"
    When I add new incorrect "phoneType" for created profile to check error code
    Then I verify response contains following eror code "ERRENUMVALUE01" "Unexpected enum value for the field phone"
    When I add new incorrect "emailType" for created profile to check error code
    Then I verify response contains following eror code "ERRENUMVALUE01" "Unexpected enum value for the field email"

  @API-CP-40323 @API-CP-40323-06 @Beka @API-DC-CC
  Scenario: Verify contact optIn/out when pass true or false
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "40323-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I initiate contacts V2 API
    When I sent with request "optAddress" is "false"
    And  I initiate consumers V2 API
    When I search consumer profile using v2 search API
    Then I should see in search response "optInAddress" is null
    When I initiate contacts V2 API
    When I sent with request "optPhone" is "false"
    And  I initiate consumers V2 API
    When I search consumer profile using v2 search API
    Then I should see in search response "optInPhone" is null
    When I initiate contacts V2 API
    When I sent with request "optEmail" is "false"
    And  I initiate consumers V2 API
    When I search consumer profile using v2 search API
    Then I should see in search response "optInEmail" is null

  @API-CP-40323 @API-CP-40323-07 @Beka @API-DC-CC
  Scenario: Delete contacts by making the inactiveInd true
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "40323-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I initiate contacts V2 API
    And I run contacts V2 API with following data
      |requestBy| profileId|
    And I send request with inactiveInd true to validate deleting contacts
    And  I initiate consumers V2 API
    When I search consumer profile using v2 search API
    Then I verify search response contains empty contact object