Feature: DC-EB: Update consumer service with opt-in data

  @API-CP-41439 @API-CP-41439-01 @Muhabbat @API-DC-CC
  Scenario: Consumer Contacts Sync for DC EB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41439-01" with following payload
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
    When I initiate and run Sync Contacts API
    Then I initiate the Case Correspondence API
    Then I send API call for DC Correspondence
    And I verify channel "mail" is usable
    And I verify channel "email" is not usable
    And I verify channel "text" is not usable


  @API-CP-41439 @API-CP-41439-02 @Muhabbat @API-DC-CC
  Scenario: Add optIn/out Address for CaseCorrespondence when pass true
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41439-02" with following payload
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
    When I sent with request "optAddress" is "true"
    And  I initiate consumers V2 API
    When I search consumer profile using v2 search API
    Then I should see in search response "optInAddress" is set to true
    Then I initiate the Case Correspondence API
    Then I send API call for DC Correspondence
    And I verify channel "mail" is usable

  @API-CP-41439 @API-CP-41439-03 @Muhabbat @API-DC-CC
  Scenario: Add optIn/out Address for CaseCorrespondence when pass false
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41439-03" with following payload
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

  @API-CP-41439 @API-CP-41439-04 @Muhabbat @API-DC-CC
  Scenario: Add optIn/out Phone for CaseCorrespondence when pass true
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41439-04" with following payload
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
    When I sent with request "optPhone" is "true"
    And  I initiate consumers V2 API
    When I search consumer profile using v2 search API
    Then I should see in search response "optInPhone" is set to true

  @API-CP-41439 @API-CP-41439-05 @Muhabbat @API-DC-CC
  Scenario: Add optIn/out Phone for CaseCorrespondence when pass false
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41439-05" with following payload
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
    When I sent with request "optPhone" is "false"
    And  I initiate consumers V2 API
    When I search consumer profile using v2 search API
    Then I should see in search response "optInPhone" is null

  @API-CP-41439 @API-CP-41439-06 @Muhabbat @API-DC-CC
  Scenario: Add optIn/out Email for CaseCorrespondence when pass true
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41439-06" with following payload
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
    When I sent with request "optEmail" is "true"
    And  I initiate consumers V2 API
    When I search consumer profile using v2 search API
    Then I should see in search response "optInEmail" is set to true

  @API-CP-41439 @API-CP-41439-07 @Muhabbat @API-DC-CC
  Scenario: Add optIn/out Email for CaseCorrespondence when pass false
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41439-07" with following payload
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
    When I sent with request "optEmail" is "false"
    And  I initiate consumers V2 API
    When I search consumer profile using v2 search API
    Then I should see in search response "optInEmail" is null

  @API-CP-41550 @API-CP-41550-01 @Beka @API-DC-CC
  Scenario: Include Middle Name and Suffix
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41550" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | Morgan   |
      | consumerRequests[0].suffix                     | Jr       |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I initiate and run Sync Contacts API
    Then I initiate the Case Correspondence API
    Then I send API call for DC Correspondence
    And I verify response contains middle "Morgan" name and suffix "Jr"

  @API-CP-39356 @API-CP-39356-01 @Muhabbat @API-DC-CC
  Scenario: Case Correspondence endpoint return External Case ID
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39356-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | random   |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I initiate and run Sync Contacts API
    Then I initiate the Case Correspondence API
    Then I send API call for DC Correspondence
    And I verify case correspondence response returns external Case Id for "39356-01"

  @API-CP-39356 @API-CP-39356-02 @Muhabbat @API-DC-CC
  Scenario: Case Correspondence endpoint return External Consumer ID
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39356-02" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | random   |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I initiate and run Sync Contacts API
    Then I initiate the Case Correspondence API
    Then I send API call for DC Correspondence
    And I verify case correspondence response returns external Consumer Id for "39356-02"

  @API-CP-39357 @API-CP-39357-01 @Muhabbat @API-DC-CC
  Scenario: Case Correspondence prefix name with Parent Guardian when <18
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39357-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | random   |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | <18      |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I initiate and run Sync Contacts API
    Then I initiate the Case Correspondence API
    Then I send API call for DC Correspondence
    And I verify case correspondence response returns name with Parent Guardian prefix "39357-01"

  @API-CP-39357 @API-CP-39357-02 @Muhabbat @API-DC-CC
  Scenario: Case Correspondence prefix name with Parent Guardian when 18-1
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39357-02" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | random   |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | 18-1     |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I initiate and run Sync Contacts API
    Then I initiate the Case Correspondence API
    Then I send API call for DC Correspondence
    And I verify case correspondence response returns name with Parent Guardian prefix "39357-02"


  @API-CP-39357 @API-CP-39357-03 @Muhabbat @API-DC-CC
  Scenario: Case Correspondence endpoint don't prefix name with Parent Guardian when 18
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39357-03" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | random   |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | 18       |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I initiate and run Sync Contacts API
    Then I initiate the Case Correspondence API
    Then I send API call for DC Correspondence
    And I verify case correspondence does not return name with Parent Guardian prefix "39357-03"

  @API-CP-39357 @API-CP-39357-04 @Muhabbat @API-DC-CC
  Scenario: Case Correspondence endpoint don't prefix name with Parent Guardian when >18
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39357-04" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | random   |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | >18      |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I initiate and run Sync Contacts API
    Then I initiate the Case Correspondence API
    Then I send API call for DC Correspondence
    And I verify case correspondence does not return name with Parent Guardian prefix "39357-04"

  @API-CP-47280 @API-CP-47280-01 @beka @API-DC-CC
  Scenario: Handling an Unsubscribe event without a Consumer ID
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "47280-1" with following payload
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
    When I add this contact optin "phoneNumber" in to the payload
    And I send request to consumer reported V2 API
    And  I initiate consumers V2 API
    When I search consumer profile using v2 search API
    Then I should see in search response "optInPhone" is set to true
    When I send request to stop a "phoneNumber" using CONSUMER_SUBSCRIPTION_UPDATE API
    And  I initiate consumers V2 API
    When I search consumer profile using v2 search API
    Then I should see in search response "optInPhone" is null
    When I initiate Event POST API to get only 1 page
    And I will provide payload with event name "CONSUMER_SUBSCRIPTION_UPDATE" and createdBy "AUTOSCRIPT"
    Then I will verify CONSUMER_SUBSCRIPTION_UPDATE event is published with blocked phone number

  @API-CP-47279 @API-CP-47279-01 @chopa @API-DC-CC
  Scenario: Handling an Unsubscribe event without a Consumer ID Email
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "47779-1" with following payload
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
    When I add this contact optin "test@gmail.com" in to the payload
    And I send request to consumer reported V2 API
    And  I initiate consumers V2 API
    When I search consumer profile using v2 search API
    Then I should see in search response "optInEmail" is set to true
    When I send request to stop a "test@gmail.com" using CONSUMER_SUBSCRIPTION_UPDATE API
    And  I initiate consumers V2 API
    When I search consumer profile using v2 search API
    Then I should see in search response "optInEmail" is null

  @API-CP-47070 @API-CP-47070-01 @beka @API-DC-CC
  Scenario: Verify a user can never opt out of both default mail and digital channel at a given time
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41439-06" with following payload
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
    When generate response for consumer reported API with following data
      | optInAddress | false |
      | optInPhone   | false |
      | optInEmail   | true  |
    And I send request to consumer reported V2 API
    When I initiate the Case Correspondence API
    When I send API call for DC Correspondence
    #AC1.0 User opts out of default Mail channel by selecting a digital channel
    Then I verify casecorrespondence response data
      | c.consumers[0].channels.mail.usability.usableFlag     | false                |
      | c.consumers[0].channels.mail.usability.unusableReason | Paperless Preference |
    When I initiate contacts V2 API
  #AC3.0 User opts into Both Mail and Paperless(digital) Channel
    When generate response for consumer reported API with following data
      | optInAddress | false |
      | optInPhone   | true  |
      | optInEmail   | true  |
    And I send request to consumer reported V2 API
    When I initiate the Case Correspondence API
    When I send API call for DC Correspondence
    Then I verify casecorrespondence response data
      | c.consumers[0].channels.mail.usability.usableFlag     | false                |
      | c.consumers[0].channels.mail.usability.unusableReason | Paperless Preference |
    When I initiate contacts V2 API
    When generate response for consumer reported API with following data
      | optInAddress | true |
      | optInPhone   | true |
      | optInEmail   | true |
    And I send request to consumer reported V2 API
    When I initiate the Case Correspondence API
    When I send API call for DC Correspondence
      #AC2.0 User opts out of Paperless(Digital) then communication preference should default to Mail (State Reported?)
    Then I verify casecorrespondence response data
      | c.consumers[0].channels.mail.usability.usableFlag     | true |
      | c.consumers[0].channels.mail.usability.unusableReason | null |
    When I initiate contacts V2 API
    When generate response for consumer reported API with following data
      | optInAddress | false |
      | optInPhone   | false |
      | optInEmail   | false |
    And I send request to consumer reported V2 API
    When I initiate the Case Correspondence API
    When I send API call for DC Correspondence
    #AC4.0 User opts out of all the 3 Mail, Text, Email then the communication preference should default to Mail (state reported mailing address)
    Then I verify casecorrespondence response data
      | c.consumers[0].channels.mail.usability.usableFlag        | true                          |
      | c.consumers[0].channels.mail.usability.unusableReason    | null                          |
      | c.consumers[0].channels.mail.addresses[0].addressLine1   | default State Mailing Address |
