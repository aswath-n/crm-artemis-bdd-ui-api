Feature: Process Unsubscribe Event from SendGrid

   ######################################### CP-39710 ######################################################

  @CP-39710 @CP-39710.1 @api-ecms-dceb @Keerthi
  Scenario: Verify Process Unsubscribe Event from SendGrid with consumer (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I initiate consumers V2 API
    When I run consumers API name "39710-01" with following payload
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
      | event          | unsubscribe                  |
      | email          | workpcautomation@outlook.com |
      | notificationId | previouslyCreated            |
      | projectName    | DC-EB                        |
    And Wait for 10 seconds
    And I should see that following records are produced to DPBI
      | CONSUMER_SUBSCRIPTION_UPDATE | with consumer |


  @CP-39710 @CP-39710.2 @api-ecms-dceb @Keerthi
  Scenario: Verify Process Unsubscribe Event from SendGrid without consumer (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I initiate consumers V2 API
    When I run consumers API name "39710-01" with following payload
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
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | emailOnly                    |
      | channelType                     | Email                        |
      | caseId                          | DCEBcaseid                   |
      | firstName                       | test                         |
      | lastName                        | test                         |
      | emailAddress                    | workpcautomation@outlook.com |
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And The Email Notification that was created is sent to SendGridEvent End Point
      | event          | unsubscribe                  |
      | email          | workpcautomation@outlook.com |
      | notificationId | previouslyCreated            |
      | projectName    | DC-EB                        |
    And Wait for 10 seconds
    And I should see that following records are produced to DPBI
      | CONSUMER_SUBSCRIPTION_UPDATE | without consumer |

  @CP-39710 @CP-39710.3  @API-ECMS @Keerthi
  Scenario: Verify Process Unsubscribe Event from SendGrid with consumer (BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | emailOnly                    |
      | caseId                          | previouslyCreated            |
      | emailAddress                    | workpcautomation@outlook.com |
      | recipients                      | random                       |
      | channel                         | Email                        |
      | language                        | English                      |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And The Email Notification that was created is sent to SendGridEvent End Point
      | event          | unsubscribe                  |
      | email          | workpcautomation@outlook.com |
      | notificationId | previouslyCreated            |
      | projectName    | BLCRM                        |
    And Wait for 10 seconds
    And I should see that following records are produced to DPBI
      | CONSUMER_SUBSCRIPTION_UPDATE | with consumer |

  @CP-39710 @CP-39710.4  @API-ECMS @Keerthi
  Scenario: Verify Process Unsubscribe Event from SendGrid without consumer (BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I clear all old messages from "workpcautomation@outlook.com" inbox
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | emailOnly                    |
      | channelType                     | Email                        |
      | caseId                          | previouslyCreated            |
      | firstName                       | test                         |
      | lastName                        | test                         |
      | emailAddress                    | workpcautomation@outlook.com |
    And the Email Notification that was created is sent to Send Grid End Point
    Then I should see that Send Grid Response was successful
    And I should see that the Email Notification was updated to "Exported" Successfully
    And The Email Notification that was created is sent to SendGridEvent End Point
      | event          | unsubscribe                  |
      | email          | workpcautomation@outlook.com |
      | notificationId | previouslyCreated            |
      | projectName    | BLCRM                        |
    And Wait for 10 seconds
    And I should see that following records are produced to DPBI
      | CONSUMER_SUBSCRIPTION_UPDATE | without consumer |