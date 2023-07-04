Feature: DC-EB: Reevaluate PI API

  @API-CP-40239 @API-CP-40239-01 @Beka @API-DC-CC
  Scenario: Verify when reevaluate PI API call complete then response returns process status for DC-EB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "40239-01" with following payload
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
      | consumerRequests[0].case                       | null     |
    When I run reevaluatepi API for V_2
    Then I sholud get following response structure
      | tenant      | DC-EB      |
      | error       | null       |
      | status      | INPROGRESS |
      | txnId       | not null   |
      | recordCount | >0         |
    When I will call transaction-status GET API by TXN_ID
    Then I will verify TXN COMPLETED and has following response
      | tenant      | DC-EB      |
      | error       | null       |
      | status      | COMPLETED  |
      | txnId       | not null   |

