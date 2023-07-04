Feature: DC-EB Apply Phone Number Standard Consumer v2 Screens

  @CP-41261 @CP-41261-01 @ui-cc-dc @muhabbat
  Scenario: Phone Number Standard on searchResult Screen
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41261-01" with following payload
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
    When I search just created consumer by external consumer ID
    Then I validate Phone Standard on "searchResult" screen

  @CP-41261 @CP-41261-02 @ui-cc-dc @muhabbat
  Scenario: Phone Number Standard Consumer on viewConsumerProfile Screens
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41261-01" with following payload
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
    And I minimize Genesys popup if populates
    When I search just created consumer by external consumer ID
   # And I navigate to consumer profile page by clicking on stateId
    Then I validate Phone Standard on "viewConsumerProfile" screen

  @CP-41261 @CP-41261-03 @ui-cc-dc @muhabbat
  Scenario: Phone Number Standard on viewCaseInfo Screens
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41261-01" with following payload
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
    When I search just created consumer by external consumer ID
    And I navigate to case view page by clicking on DC caseId
    Then I validate Phone Standard on "viewCaseInfo" screen

