Feature: DC-EB View Case Profile

  @CP-40423 @CP-40423-01 @ui-cc-dc @beka
  Scenario: View Medicaid Case Details validation
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "40423-01" with following payload
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
    When I select a case to view from the list of consumers by clicking on the Case ID, if there is one for the consumer
    Then I will be navigated to Medicaid Case Details screen
    Then I will verify following tabs displayed
      | CASE DETAILS           |
      | Case & Contact Details |
      | TASK & SERVICE REQUEST |
      | Program & Benefit Info |
      | History                |
    Then I verify all case members headers and labels
      | STATE ID       |
      | FULL NAME      |
      | DATE OF BIRTH  |
      | AGE / GENDER   |
      | AUTHORIZED REP |
    Then I verify all following consumer data
      | STATE ID value       |
      | FULL NAME    value   |
      | DATE OF BIRTH value  |
      | AGE / GENDER   value |
      | AUTHORIZED REP value |