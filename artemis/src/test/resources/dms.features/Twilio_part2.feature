Feature: Send SMS Request to Twilio DC-EB tenant

  @CP-37733 @CP-37733-2 @CP-39968 @api-ecms-dceb @Keerthi
  Scenario: Verify Process SMS Delivered Response from Twilio (DC-EB)
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
      | correspondenceDefinitionMMSCode | textOnly   |
      | caseId                          | DCEBcaseid |
      | emailAddress                    | sendGrid   |
      | recipients                      | random     |
      | channel                         | Text       |
      | language                        | English    |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And The Text Notification that was created is sent to TwilioEvent End Point
      | notificationId | previouslyCreated |
      | projectName    | DC-EB             |
      | MessageStatus  | delivered         |
      | ErrorCode      | 3000              |
      | ErrorMessage   | Test              |
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status | Sent |
    Then I verify the following values in the correspondence notification response
      | Status            | Sent                  |
      | Updated By        | ECMS Service          |
      | Updated Date      | current date and hour |
      | objectReceivedOn  | current date and hour |
      | statusChangedDate | current date and hour |
      | changedBy         | ECMS Service          |
    And I copied the following nids
      | notificationId | previouslyCreated |
    And I should see that following records are produced to DPBI
      | NOTIFICATION_UPDATE_EVENT |


  ################################### CP-37734 ###################################

  @CP-37734 @CP-37734-3 @CP-39968 @api-ecms-dceb @Keerthi
  Scenario Outline: Verify Process SMS Failed Response from Twilio for 'Failed' MessageStatus (DC-EB)
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
      | correspondenceDefinitionMMSCode | textOnly   |
      | caseId                          | DCEBcaseid |
      | emailAddress                    | sendGrid   |
      | recipients                      | random     |
      | channel                         | Text       |
      | language                        | English    |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And The Text Notification that was created is sent to TwilioEvent End Point
      | notificationId | previouslyCreated |
      | projectName    | <projectName>     |
      | MessageStatus  | <MessageStatus>   |
      | ErrorCode      | <ErrorCode>       |
      | ErrorMessage   | <ErrorMessage>    |
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status        | Returned                   |
      | Status Reason | <NotificationStatusReason> |
    Then I verify the following values in the correspondence notification response
      | Status            | Returned                   |
      | Status Reason     | <NotificationStatusReason> |
      | changedBy         | ECMS Service               |
      | statusChangedDate | current date and hour      |
      | Updated By        | ECMS Service               |
      | Updated Date      | current date and hour      |
      | objectReceivedOn  | null                       |
      | ErrorDetail       | <ErrorMessage>             |
    And I copied the following nids
      | notificationId | previouslyCreated |
    And I should see that following records are produced to DPBI
      | NOTIFICATION_UPDATE_EVENT |
    Examples:
      | projectName | MessageStatus | ErrorCode | NotificationStatusReason           | ErrorMessage |
      | DC-EB       | failed        | 30003     | Refused                            | 1234         |
      | DC-EB       | failed        | 30004     | Refused                            | test         |
      | DC-EB       | failed        | 30005     | Refused                            | !@#@$#$      |
      | DC-EB       | failed        | 30006     | Refused                            | 123RTid      |
      | DC-EB       | failed        | test300   | Destination Agent Rejected Message | test 123     |

  @CP-37734 @CP-37734-4 @CP-39968 @api-ecms-dceb @Keerthi
  Scenario Outline: Verify Process SMS Failed Response from Twilio for 'undelivered' MessageStatus (DC-EB)
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
      | correspondenceDefinitionMMSCode | textOnly   |
      | caseId                          | DCEBcaseid |
      | emailAddress                    | sendGrid   |
      | recipients                      | random     |
      | channel                         | Text       |
      | language                        | English    |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And The Text Notification that was created is sent to TwilioEvent End Point
      | notificationId | previouslyCreated |
      | projectName    | <projectName>     |
      | MessageStatus  | <MessageStatus>   |
      | ErrorCode      | <ErrorCode>       |
      | ErrorMessage   | <ErrorMessage>    |
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status        | Returned                   |
      | Status Reason | <NotificationStatusReason> |
    Then I verify the following values in the correspondence notification response
      | Status            | Returned                   |
      | Status Reason     | <NotificationStatusReason> |
      | changedBy         | ECMS Service               |
      | statusChangedDate | current date and hour      |
      | Updated By        | ECMS Service               |
      | Updated Date      | current date and hour      |
      | objectReceivedOn  | null                       |
      | ErrorDetail       | <ErrorMessage>             |
    And I copied the following nids
      | notificationId | previouslyCreated |
    And I should see that following records are produced to DPBI
      | NOTIFICATION_UPDATE_EVENT |
    Examples:
      | projectName | MessageStatus | ErrorCode | NotificationStatusReason           | ErrorMessage |
      | DC-EB       | undelivered   | 30003     | Refused                            | 1234         |
      | DC-EB       | undelivered   | 30004     | Refused                            | test         |
      | DC-EB       | undelivered   | 30005     | Refused                            | !@#@$#$      |
      | DC-EB       | undelivered   | 30006     | Refused                            | 123RTid      |
      | DC-EB       | undelivered   | test300   | Destination Agent Rejected Message | test 123     |

###################################### CP-39709 #############################################

  @CP-39709 @CP-39709.1  @API-ECMS @Keerthi
  Scenario: Verify Process SMS Unsubscribe Event from Twilio for START (BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | textDestination | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | textOnly          |
      | caseId                          | previouslyCreated |
      | textNumber                      | Twilio            |
      | recipients                      | random            |
      | channel                         | Text              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And The Text Notification that was created is sent to TwilioEvent End Point
      | Event_Type  | CONSUMER_SUBSCRIPTION_UPDATE |
      | projectName | BLCRM                        |
      | OptOutType  | START                        |
      | From        | +14017122661                 |
    And Wait for 10 seconds
    And I should see that following records are produced to DPBI
      | CONSUMER_SUBSCRIPTION_UPDATE | START,+14017122661 |

  @CP-39709 @CP-39709.2  @API-ECMS @Keerthi
  Scenario: Verify Process SMS Unsubscribe Event from Twilio for STOP (BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | textDestination | random active address |
      | writtenLanguage | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | textOnly          |
      | caseId                          | previouslyCreated |
      | textNumber                      | Twilio            |
      | recipients                      | random            |
      | channel                         | Text              |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And The Text Notification that was created is sent to TwilioEvent End Point
      | Event_Type  | CONSUMER_SUBSCRIPTION_UPDATE |
      | projectName | BLCRM                        |
      | OptOutType  | STOP                         |
      | From        | +14017122661                 |
    And Wait for 10 seconds
    And I should see that following records are produced to DPBI
      | CONSUMER_SUBSCRIPTION_UPDATE | STOP,+14017122661 |

  @CP-39709 @CP-39709.3  @API-ECMS @Keerthi
  Scenario: Verify Process SMS Unsubscribe Event from Twilio for START (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39709-01" with following payload
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
      | correspondenceDefinitionMMSCode | textOnly   |
      | caseId                          | DCEBcaseid |
      | recipients                      | random     |
      | channel                         | Text       |
      | textNumber                      | Twilio     |
      | language                        | English    |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And The Text Notification that was created is sent to TwilioEvent End Point
      | Event_Type  | CONSUMER_SUBSCRIPTION_UPDATE |
      | projectName | DC-EB                        |
      | OptOutType  | START                        |
      | From        | +14017122661                 |
    And Wait for 10 seconds
    And I should see that following records are produced to DPBI
      | CONSUMER_SUBSCRIPTION_UPDATE | START,+14017122661 |

  @CP-39709 @CP-39709.4  @API-ECMS @Keerthi
  Scenario: Verify Process SMS Unsubscribe Event from Twilio for STOP (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39709-01" with following payload
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
      | correspondenceDefinitionMMSCode | textOnly   |
      | caseId                          | DCEBcaseid |
      | recipients                      | random     |
      | channel                         | Text       |
      | textNumber                      | Twilio     |
      | language                        | English    |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And The Text Notification that was created is sent to TwilioEvent End Point
      | Event_Type  | CONSUMER_SUBSCRIPTION_UPDATE |
      | projectName | DC-EB                        |
      | OptOutType  | STOP                        |
      | From        | +14017122661                 |
    And Wait for 10 seconds
    And I should see that following records are produced to DPBI
      | CONSUMER_SUBSCRIPTION_UPDATE | STOP,+14017122661 |