Feature: DC-EB IVR APIs

  @contact-record-api-CRMC @CP-48168 @CP-48168-01 @API-CORE @API-CRM-Regression @araz
    # CP-48168(AC 1.0)
  Scenario: Verify that updating consumerPhone to a optional field "removed consumerPhone from payload"
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate IVR calldata api with the following data and send POST for DC_EB
      | tenant | consumerFirstName | consumerLastName | consumerDateOfBirth | interactionId | consumerSSN | applicationId |
      | DC-EB  | John              | DaLast           | 03171988            | 245279826     | 375646234   | 34567         |
    Then I verify the response for DC_EB ivr calldata

  @contact-record-api-CRMC @CP-48168 @CP-48168-02 @API-CORE @API-CRM-Regression @araz
    # CP-48168(AC 2.0)
  Scenario: Verify that consumerDateOfBirth should be optional "removed consumerDateOfBirth from payload"
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate IVR calldata api with the following data and send POST for DC_EB
      | tenant | consumerFirstName | consumerLastName | consumerPhone | interactionId | consumerSSN | applicationId |
      | DC-EB  | John              | DaLast           | 7191521396    | 245279826     | 375646234   | 34567         |
    Then I verify the response for DC_EB ivr calldata