Feature: Exception Handling for Post-Fulfillment Digital Notifications


  @CP-43378 @CP-43378-01 @API-ECMS @James
  Scenario Outline: Verify CONSUMER_NOTIFICATION_RETURNED event is published when Notification Status with a Consumer IDis updated to Returned with a Reason of Undeliverable
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | <Destination>   | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | textNumber                      | Twilio            |
      | recipients                      | random            |
      | channel                         | <Channel>         |
      | consumerId                      | previouslyCreated |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I update the Outbound Correspondence Notification with the following values
      | Returned | random |
    And I retrieve the "CONSUMER_NOTIFICATION_RETURNED" event that is produced by trace id
    Then I should see the payload for the "CONSUMER_NOTIFICATION_RETURNED" is as expected
    Examples:
      | Channel | Destination     |
      | Mail    | mailDestination |
#      | Email   | emailDestination      |
#      | Fax     | secondFaxDestination  |
#      | Text    | secondTextDestination |


  @CP-43378 @CP-43378-02 @API-ECMS @James
  Scenario Outline: Verify APPLICATION_CONSUMER_NOTIFICATION_RETURNED event is published when Notification Status with externalRefType of APPLICATION_CONSUMER is updated to Returned with a Reason of Undeliverable
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | <Destination>   | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase            |
      | caseId                          | previouslyCreated    |
      | textNumber                      | Twilio               |
      | recipients                      | random               |
      | channel                         | <Channel>            |
      | externalRefType                 | APPLICATION_CONSUMER |
      | externalRefId                   | random               |
      | consumerId                      | null                 |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I update the Outbound Correspondence Notification with the following values
      | Returned | random |
    And I retrieve the "APPLICATION_CONSUMER_NOTIFICATION_RETURNED" event that is produced by trace id
    Then I should see the payload for the "APPLICATION_CONSUMER_NOTIFICATION_RETURNED" is as expected
    Examples:
      | Channel | Destination           |
      | Mail    | mailDestination       |
      | Email   | emailDestination      |
      | Fax     | secondFaxDestination  |
      | Text    | secondTextDestination |

  @CP-43378 @CP-43378-03a @API-ECMS @James
  Scenario Outline: Verify no event when Notification Status does not have Consumer Id is updated to Returned with a Reason of Undeliverable
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | <Destination>   | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | textNumber                      | Twilio            |
      | recipients                      | random            |
      | channel                         | <Channel>         |
      | consumerId                      | null              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I update the Outbound Correspondence Notification with the following values
      | Returned | random |
    Then I should not see any of the following events that is produced by trace id
      | APPLICATION_CONSUMER_NOTIFICATION_RETURNED |
      | CONSUMER_NOTIFICATION_RETURNED             |
    Examples:
      | Channel | Destination           |
      | Mail    | mailDestination       |
      | Email   | emailDestination      |
      | Fax     | secondFaxDestination  |
      | Text    | secondTextDestination |

  @CP-43378 @CP-43378-03b @API-ECMS @James
  Scenario Outline: Verify no event when Notification Status does not have externalRefType of APPLICATION_CONSUMER is updated to Returned with a Reason of Undeliverable
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | <Destination>   | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | textNumber                      | Twilio            |
      | recipients                      | random            |
      | channel                         | <Channel>         |
      | externalRefType                 | null              |
      | externalRefId                   | null              |
      | consumerId                      | null              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I update the Outbound Correspondence Notification with the following values
      | Returned | random |
    Then I should not see any of the following events that is produced by trace id
      | APPLICATION_CONSUMER_NOTIFICATION_RETURNED |
      | CONSUMER_NOTIFICATION_RETURNED             |
    Examples:
      | Channel | Destination           |
      | Mail    | mailDestination       |
      | Email   | emailDestination      |
      | Fax     | secondFaxDestination  |
      | Text    | secondTextDestination |


  @CP-43378 @CP-43378-01DCEB @api-ecms-dceb @James
  Scenario Outline: Verify CONSUMER_NOTIFICATION_RETURNED event is published when Notification Status with a Consumer IDis updated to Returned with a Reason of Undeliverable for dceb
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "42258-09" with following payload
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
    When I initiate contacts V2 API
    When I add this contact optin "mail" in to the payload
    And I send request to consumer reported V2 API
    When I run reevaluatepi API for V_2
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | DCEBcaseid        |
      | recipients                      | random            |
      | channel                         | <Channel>         |
      | language                        | English           |
      | consumerId                      | previouslyCreated |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I update the Outbound Correspondence Notification with the following values
      | Returned | random |
    And I retrieve the "CONSUMER_NOTIFICATION_RETURNED" event that is produced by trace id
    Then I should see the payload for the "CONSUMER_NOTIFICATION_RETURNED" is as expected
    Examples:
      | Channel |
      | Mail    |
      | Email   |
      | Text    |


  @CP-43378 @CP-43378-02DCEB @api-ecms-dceb @James
  Scenario Outline: Verify APPLICATION_CONSUMER_NOTIFICATION_RETURNED event is published when Notification Status with externalRefType of APPLICATION_CONSUMER is updated to Returned with a Reason of Undeliverable for dceb
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
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
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase            |
      | caseId                          | DCEBcaseid           |
      | recipients                      | random               |
      | channel                         | <Channel>            |
      | language                        | English              |
      | externalRefType                 | APPLICATION_CONSUMER |
      | externalRefId                   | random               |
      | consumerId                      | null                 |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I update the Outbound Correspondence Notification with the following values
      | Returned | random |
    And I retrieve the "APPLICATION_CONSUMER_NOTIFICATION_RETURNED" event that is produced by trace id
    Then I should see the payload for the "APPLICATION_CONSUMER_NOTIFICATION_RETURNED" is as expected
    Examples:
      | Channel |
      | Mail    |
      | Email   |
      | Text    |

  @CP-43378 @CP-43378-03aDCEB @api-ecms-dceb @James
  Scenario Outline: Verify no event when Notification Status does not have externalRefType of APPLICATION_CONSUMER is updated to Returned with a Reason of Undeliverable for dceb
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
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
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase            |
      | caseId                          | DCEBcaseid           |
      | recipients                      | random               |
      | channel                         | <Channel>            |
      | language                        | English              |
      | externalRefType                 | APPLICATION_CONSUMER |
      | externalRefId                   | random               |
      | consumerId                      | null                 |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I update the Outbound Correspondence Notification with the following values
      | Returned | random |
    Then I should not see any of the following events that is produced by trace id
      | APPLICATION_CONSUMER_NOTIFICATION_RETURNED |
      | CONSUMER_NOTIFICATION_RETURNED             |
    Examples:
      | Channel |
      | Mail    |
      | Email   |
      | Text    |

  @CP-43378 @CP-43378-03bDCEB @api-ecms-dceb @James
  Scenario Outline: Verify no event when Notification Status does not have Consumer Id is updated to Returned with a Reason of Undeliverable for dceb
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
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
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | DCEBcaseid        |
      | recipients                      | random            |
      | channel                         | <Channel>         |
      | language                        | English           |
      | consumerId                      | previouslyCreated |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I update the Outbound Correspondence Notification with the following values
      | Returned | random |
    Then I should not see any of the following events that is produced by trace id
      | APPLICATION_CONSUMER_NOTIFICATION_RETURNED |
      | CONSUMER_NOTIFICATION_RETURNED             |
    Examples:
      | Channel |
      | Mail    |
      | Email   |
      | Text    |