Feature:View Medicaid Case Details 2

  @CP-41784 @CP-41784-01 @ui-cc-dc @muhabbat
  Scenario: View Consumer Reported Communication Panel for PI on the Medicaid Case
    Given I logged into CRM and select a project "DC-EB"
    Given I will get the Authentication token for "DC-EB" in "CRM"
    When I navigate to manual case and consumer search page
    And I initiate consumers V2 API
    When I run consumers API name "41784-01" with following payload
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
    And I run reevaluatepi API for V_2
    When I search just created consumer by external consumer ID
    When I navigate to case view page by clicking on DC caseId
    Then I see Consumer-Reported Communication panel for PI


  @CP-41784 @CP-41784-02 @ui-cc-dc @muhabbat
  Scenario: View Add Consumer Reported Communication Panel for PI on the Medicaid Case
    Given I logged into CRM and select a project "DC-EB"
    Given I will get the Authentication token for "DC-EB" in "CRM"
    When I navigate to manual case and consumer search page
    And I initiate consumers V2 API
    When I run consumers API name "41784-02" with following payload
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
    And I run reevaluatepi API for V_2
    When I search just created consumer by external consumer ID
    When I navigate to case view page by clicking on DC caseId
    Then I see Add button for Consumer-Reported Communication panel for PI

  @CP-41784 @CP-41784-03 @ui-cc-dc @muhabbat
  Scenario: View Edit Consumer Reported Communication Panel for PI on the Medicaid Case
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I search for consumer by "StateID" with value "33528349test"
    When I navigate to case view page by clicking on DC caseId
    Then I see Edit button for Consumer-Reported Communication panel for PI

  @CP-41784 @CP-41784-04 @ui-cc-dc @muhabbat
  Scenario: Navigation through Tabs from View Medicaid Case Details
    Given I logged into CRM and select a project "DC-EB"
    Given I will get the Authentication token for "DC-EB" in "CRM"
    When I navigate to manual case and consumer search page
    And I initiate consumers V2 API
    When I run consumers API name "41784-04" with following payload
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
    When I navigate to case view page by clicking on DC caseId
    Then I will verify navigation following tabs
      | CASE DETAILS           |
      | CONTACT DETAILS        |
      | TASK & SERVICE REQUEST |
      | Program & Benefit Info |
      | History                |

  @CP-41784 @CP-41784-05 @ui-cc-dc @muhabbat
  Scenario: Navigation back to General Consumer Search from View Medicaid Case Details screen
    Given I logged into CRM and select a project "DC-EB"
    Given I will get the Authentication token for "DC-EB" in "CRM"
    When I navigate to manual case and consumer search page
    And I initiate consumers V2 API
    When I run consumers API name "41784-05" with following payload
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
    When I navigate to case view page by clicking on DC caseId
    And I click on Back Arrow on DC view case Consumer details Page
    Then I validate the search result remain on search screen

