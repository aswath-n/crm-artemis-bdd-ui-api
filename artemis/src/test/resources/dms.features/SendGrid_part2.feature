Feature: Send Email Request to SendGrid part2

   ######################################### CP-36201 ######################################################

  @CP-36201 @CP-36201-1 @CP-39968 @API-ECMS @Keerthi
  Scenario: Verify Process Email Success Response from SendGrid and sender email is taken from Ob Settings (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly         |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And The Email Notification that was created is sent to SendGridEvent End Point
      | event          | delivered          |
      | reason         | notificationreason |
      | type           | notificationtype   |
      | notificationId | previouslyCreated  |
      | projectName    | BLCRM              |
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

  @CP-36201 @CP-36201-2 @CP-39968 @API-ECMS @Keerthi
  Scenario:Verify Process Email Success Response from SendGrid and sender email is taken from channel definition (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly2        |
      | caseId                          | previouslyCreated |
      | emailAddress                    | sendGrid          |
      | recipients                      | random            |
      | channel                         | Email             |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And The Email Notification that was created is sent to SendGridEvent End Point
      | event          | delivered          |
      | reason         | notificationreason |
      | type           | notificationtype   |
      | notificationId | previouslyCreated  |
      | projectName    | BLCRM              |
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

  @CP-36201 @CP-36201-3 @CP-39968 @api-ecms-dceb @Keerthi
  Scenario: Verify Process Email Success Response from SendGrid and sender email is taken from Ob Settings (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
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
      | correspondenceDefinitionMMSCode | emailOnly                    |
      | caseId                          | DCEBcaseid                   |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | Email                        |
      | language                        | English                      |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And The Email Notification that was created is sent to SendGridEvent End Point
      | event          | delivered          |
      | reason         | notificationreason |
      | type           | notificationtype   |
      | notificationId | previouslyCreated  |
      | projectName    | DC-EB              |
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

    ######################################### CP-36202 ######################################################

  @CP-36202 @CP-36202-1.1 @CP-39968 @API-ECMS @Keerthi
  Scenario Outline: Verify Process Email Failed Response from SendGrid for dropped event type (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly                    |
      | caseId                          | previouslyCreated            |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | Email                        |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And The Email Notification that was created is sent to SendGridEvent End Point
      | event          | <Event>           |
      | reason         | <Reason>          |
      | type           | <Type>            |
      | notificationId | previouslyCreated |
      | projectName    | BLCRM             |
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status        | Returned       |
      | Status Reason | <StatusReason> |
    Then I verify the following values in the correspondence notification response
      | Status            | Returned              |
      | Status Reason     | <StatusReason>        |
      | ErrorDetail       | <ErrorDetail>         |
      | Updated By        | ECMS Service          |
      | Updated Date      | current date and hour |
      | objectReceivedOn  | null                  |
      | statusChangedDate | current date and hour |
      | changedBy         | ECMS Service          |
    And I copied the following nids
      | notificationId | previouslyCreated |
    And I should see that following records are produced to DPBI
      | NOTIFICATION_UPDATE_EVENT |
    Examples:
      | Event   | Reason                            | Type     | StatusReason                       | ErrorDetail                       |
      | dropped | Invalid SMTPAPI header            | testtype | Destination Agent Rejected Message | Invalid SMTPAPI header            |
      | dropped | Spam Content                      | testtype | Destination Agent Rejected Message | Spam Content                      |
      | dropped | Unsubscribed Address              | testtype | Refused                            | Unsubscribed Address              |
      | dropped | Bounced Address                   | testtype | Destination Agent Rejected Message | Bounced Address                   |
      | dropped | Spam Reporting Address            | testtype | Destination Agent Rejected Message | Spam Reporting Address            |
      | dropped | Invalid                           | testtype | Destination Agent Rejected Message | Invalid                           |
      | dropped | Recipient List over Package Quota | testtype | Destination Agent Rejected Message | Recipient List over Package Quota |
      | dropped | TestREASON!123@#%^&%^             | testtype | Destination Agent Rejected Message | TestREASON!123@#%^&%^             |

  @CP-36202 @CP-36202-1.2 @CP-39968 @API-ECMS @Keerthi
  Scenario Outline: Verify Process Email Failed Response from SendGrid for bounce event type (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual    |
      | emailDestination | random active address |
      | writtenLanguage  | English               |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly                    |
      | caseId                          | previouslyCreated            |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | Email                        |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And The Email Notification that was created is sent to SendGridEvent End Point
      | event          | <Event>           |
      | reason         | <Reason>          |
      | type           | <Type>            |
      | notificationId | previouslyCreated |
      | projectName    | BLCRM             |
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status        | Returned       |
      | Status Reason | <StatusReason> |
    Then I verify the following values in the correspondence notification response
      | Status            | Returned              |
      | Status Reason     | <StatusReason>        |
      | ErrorDetail       | <ErrorDetail>         |
      | Updated By        | ECMS Service          |
      | Updated Date      | current date and hour |
      | objectReceivedOn  | null                  |
      | statusChangedDate | current date and hour |
      | changedBy         | ECMS Service          |
    And I copied the following nids
      | notificationId | previouslyCreated |
    And I should see that following records are produced to DPBI
      | NOTIFICATION_UPDATE_EVENT |
    Examples:
      | Event  | Reason     | Type                  | StatusReason        | ErrorDetail           |
      | bounce | testreason | bounce                | Destination Invalid | bounce                |
      | bounce | testreason | blocked               | Destination Invalid | blocked               |
      | bounce | testreason | TestREASON!123@#%^&%^ | Destination Invalid | TestREASON!123@#%^&%^ |

  @CP-36202 @CP-36202-1.1dceb @CP-39968 @api-ecms-dceb @Keerthi
  Scenario Outline: Verify Process Email Failed Response from SendGrid for dropped event type (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
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
      | correspondenceDefinitionMMSCode | emailOnly                    |
      | caseId                          | DCEBcaseid                   |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | Email                        |
      | language                        | English                      |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And The Email Notification that was created is sent to SendGridEvent End Point
      | event          | <Event>           |
      | reason         | <Reason>          |
      | type           | <Type>            |
      | notificationId | previouslyCreated |
      | projectName    | DC-EB             |
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status        | Returned       |
      | Status Reason | <StatusReason> |
    Then I verify the following values in the correspondence notification response
      | Status            | Returned              |
      | Status Reason     | <StatusReason>        |
      | ErrorDetail       | <ErrorDetail>         |
      | Updated By        | ECMS Service          |
      | Updated Date      | current date and hour |
      | objectReceivedOn  | null                  |
      | statusChangedDate | current date and hour |
      | changedBy         | ECMS Service          |
    And I copied the following nids
      | notificationId | previouslyCreated |
    And I should see that following records are produced to DPBI
      | NOTIFICATION_UPDATE_EVENT |
    Examples:
      | Event   | Reason                            | Type     | StatusReason                       | ErrorDetail                       |
      | dropped | Invalid SMTPAPI header            | testtype | Destination Agent Rejected Message | Invalid SMTPAPI header            |
      | dropped | Spam Content                      | testtype | Destination Agent Rejected Message | Spam Content                      |
      | dropped | Unsubscribed Address              | testtype | Refused                            | Unsubscribed Address              |
      | dropped | Bounced Address                   | testtype | Destination Agent Rejected Message | Bounced Address                   |
      | dropped | Spam Reporting Address            | testtype | Destination Agent Rejected Message | Spam Reporting Address            |
      | dropped | Invalid                           | testtype | Destination Agent Rejected Message | Invalid                           |
      | dropped | Recipient List over Package Quota | testtype | Destination Agent Rejected Message | Recipient List over Package Quota |
      | dropped | TestREASON!123@#%^&%^             | testtype | Destination Agent Rejected Message | TestREASON!123@#%^&%^             |


  @CP-36202 @CP-36202-1.2dceb @CP-39968  @api-ecms-dceb @Keerthi
  Scenario Outline: Verify Process Email Failed Response from SendGrid for bounce event type (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
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
      | correspondenceDefinitionMMSCode | emailOnly                    |
      | caseId                          | DCEBcaseid                   |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | Email                        |
      | language                        | English                      |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And The Email Notification that was created is sent to SendGridEvent End Point
      | event          | <Event>           |
      | reason         | <Reason>          |
      | type           | <Type>            |
      | notificationId | previouslyCreated |
      | projectName    | DC-EB             |
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status        | Returned       |
      | Status Reason | <StatusReason> |
    Then I verify the following values in the correspondence notification response
      | Status            | Returned              |
      | Status Reason     | <StatusReason>        |
      | ErrorDetail       | <ErrorDetail>         |
      | Updated By        | ECMS Service          |
      | Updated Date      | current date and hour |
      | objectReceivedOn  | null                  |
      | statusChangedDate | current date and hour |
      | changedBy         | ECMS Service          |
    And I copied the following nids
      | notificationId | previouslyCreated |
    And I should see that following records are produced to DPBI
      | NOTIFICATION_UPDATE_EVENT |
    Examples:
      | Event  | Reason     | Type                  | StatusReason        | ErrorDetail           |
      | bounce | testreason | bounce                | Destination Invalid | bounce                |
      | bounce | testreason | blocked               | Destination Invalid | blocked               |
      | bounce | testreason | TestREASON!123@#%^&%^ | Destination Invalid | TestREASON!123@#%^&%^ |

