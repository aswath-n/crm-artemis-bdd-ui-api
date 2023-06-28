@umid
@CP-3034

Feature: Cancel Notification

  @CP-3034-01 @ui-ecms1
  Scenario: Verify dropdown notification, pop-up and save functionality
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case ID and consumer search result
    When I navigate to the case and contact details tab
    And I click on the previous created CID "previouslyCreated"
    And I click on notification actions dropdown to select cancel
    And I click save for notification
    And I verify error message for notification reason
    Then I verify the dropdown values for "Notification Reason"
      | Consumer Request           |
      | Superseded by Events       |
      | No Valid Destination       |
      | Recipient No Longer Active |
      | Requested in Error         |
    And I click save for notification
    Then I should see a warning message when navigating away "Are you sure, you wish to cancel this notification?"
    And I click No when given the option to confirm cancel or not cancel
    And I click save for notification
    Then I should see a warning message when navigating away "Are you sure, you wish to cancel this notification?"
    And I click yes when given the option to confirm cancel or not cancel
    Then I verify notification status and reason is updated to Cancel

  @CP-3034-02 @ui-ecms1
  Scenario: Verify dropdown notification, pop-up and navigation functionality
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I click on the previous created CID "previouslyCreated"
    And I click on notification actions dropdown to select cancel
    Then I verify the dropdown values for "Notification Reason"
      | Consumer Request           |
      | Superseded by Events       |
      | No Valid Destination       |
      | Recipient No Longer Active |
      | Requested in Error         |
    When I navigate away by clicking the back arrow without saving changes on this page
    Then I should see a warning message when navigating away "If you continue, all the captured information will be lost"
    And I click on the Cancel Button
    And I click on edit button on view Outbound Correspondence details
    When I navigate away by clicking the back arrow without saving changes on this page
    Then I should see a warning message when navigating away "If you continue, all the captured information will be lost"
    And I click on the continue button on warning pop up
    And Verify On Case And Contact Details Screen

#  @CP-3034-03 @ui-ecms1
  Scenario: Verify user is not allowed to cancel notification from case consumer search
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    And I update previous notification
      | Sent     |
      | Returned |
      | Canceled |
    And I Verify Notification Statuses have been updated
    And I logged into CRM
    And I click case consumer search tab
    And I search for the "CaseId" with value "7347"
    And I search for the Outbound Correspondence "7347"
    When I navigate to the case and contact details tab
    And I click on the previous created CID "previouslyCreated"
    And I verify user not allowed to cancel notifications
    And I verify Status is changed for notification on UI

#  @CP-3034-04 @ui-ecms1
  Scenario: Verify user is not allowed to cancel notification Outbound Correspondence Search
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    And I update previous notification
      | Sent     |
      | Returned |
      | Canceled |
    And I Verify Notification Statuses have been updated
    And I logged into CRM
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I verify user not allowed to cancel notifications
    And I verify Status is changed for notification on UI

  @CP-3034-05 @ui-ecms1
  Scenario: Verify cascade, Status History, notification update
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    And I update Outbound Notification Id "previouslyCreatedlistofnid" to status "Canceled"
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    And I click on the previous created CID "previouslyCreated"
    And I verify Cascade Correspondence "Canceled" "Multiple - See Notifications"
    And I verify Status History "Requested" "Service AccountOne"

  @CP-10513 @CP-10513-01 @ui-ecms2 @RuslanL
  Scenario: Verify Sent option available if the Notification is in Requested
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | NOTIFICATIONSTATUS | Requested |
    When I click on the "first available" Outbound Correspondence
    And I verify notification actions dropdown contains the following values for notification status "Requested"
      | SENT |

  @CP-10513 @CP-10513-02 @ui-ecms2 @RuslanL
  Scenario: Verify Sent option available if the Notification is in Exported
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | NOTIFICATIONSTATUS | Exported |
    When I click on the "first available" Outbound Correspondence
    And I verify notification actions dropdown contains the following values for notification status "Exported"
      | SENT |

  @CP-10513 @CP-10513-03 @ui-ecms2 @RuslanL
  Scenario: Verify Sent option available if the Notification is in Assembled
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | NOTIFICATIONSTATUS | Assembled |
    When I click on the "first available" Outbound Correspondence
    And I verify notification actions dropdown contains the following values for notification status "Assembled"
      | SENT |

  @CP-10513 @CP-10513-04 @ui-ecms2 @RuslanL
  Scenario: Verify notification status, Status date and Status date error message for notification status Sent
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Random |
      | ConsumerId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on notification actions dropdown to select "SENT"
    Then I validated for "Sent" notification status
    And verify the Status date field for sent status
    And I cleared the date from calendar
    And I click save for notification
    Then I verify error message for notification status date in Notification grid

  @CP-10513 @CP-10513-05 @CP-3190-04 @ui-ecms2 @RuslanL
  Scenario: Verify system updates the Notification and cascade to Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole          | Primary Individual    |
      | mailDestination       | random active address |
      | emailDestination      | random active address |
      | secondTextDestination | random active address |
      | secondFaxDestination  | random active address |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | allChCase         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    When I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on notification actions dropdown to select "SENT"
    And I click save for notification
    And I verify the system updates the Notification for Status "Sent" and cascade to Correspondence
    And I verify Status History "Requested" "Service AccountOne"

  @CP-10513 @CP-10513-06 @ui-ecms2 @RuslanL
  Scenario: Verify system warn before discarding data while updating Notification to Sent
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole          | Primary Individual    |
      | mailDestination       | random active address |
      | emailDestination      | random active address |
      | secondTextDestination | random active address |
      | secondFaxDestination  | random active address |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | allChCase         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    When I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on notification actions dropdown to select "SENT"
    And I update and store notification status date
    When I navigate away by clicking the back arrow without saving changes on this page
    Then I should see a warning message when navigating away "If you continue, all the captured information will be lost"
    When I click cancel in Warning Message popup
    Then The system ignores my navigation request and keeps my data
    When I navigate away by clicking the back arrow without saving changes on this page
    Then I should see a warning message when navigating away "If you continue, all the captured information will be lost"
    And I click discard my changes
    Then I should be navigated to the Outbound Correspondence Global Search Page

  @CP-10513 @CP-10513-07 @ui-ecms2 @RuslanL
  Scenario: Verify Cancel button functionality when updating notifications status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole          | Primary Individual    |
      | mailDestination       | random active address |
      | emailDestination      | random active address |
      | secondTextDestination | random active address |
      | secondFaxDestination  | random active address |
    And I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | allChCase         |
      | language                        | English           |
      | caseId                          | previouslyCreated |
    When I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I store notification status and date
    And I click on notification actions dropdown to select "SENT"
    When I click on Cancel button on the notification
    Then The system will revert the Status and Status Date values on the screen to the values they were previously

        ################################################################################# @CP-42086 ####################################################################################################################

  @CP-42086 @API-CP-42086-01 @API-ECMS @burak
  Scenario: Verify Get Notification Image API in Correspondence Order service returns response in base64 format for OnBase template
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | CP42086           |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I see that a Document has been uploaded to Onbase for the "Mail" Outbound Correspondence Notification
    And I should see the Outbound Correspondence Notification is created with the following values
      | status | Requested |
    And I get the notification "PREVIOUSLY_CREATED" using order service
    And I verify response is Base64 encoded

  @CP-42086 @API-CP-42086-02 @API-ECMS @burak
  Scenario: Verify Get Notification Image API in Correspondence Order service returns response in base64 format for Twilio template
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | CP42086           |
      | channelType                     | Text              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
    When I send the request for an Outbound Correspondence to the service
    And I should see the Outbound Correspondence Notification is created with the following values
      | status | Requested |
    And I get the notification "PREVIOUSLY_CREATED" using order service
    And I verify response is Base64 encoded
    And I verify response is Base64 encoded as "this is a test twilio template.á, é, í, ó, ú, ü, ñ, ¿, ¡!@#$%^&*()?><:~|}{"

  @CP-42086 @API-CP-42086-03 @API-ECMS @burak
  Scenario: Verify Get Notification Image API in Correspondence Order service returns response in base64 format for SendGrid template
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | CP42086           |
      | channelType                     | Email             |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
    When I send the request for an Outbound Correspondence to the service
    And I should see the Outbound Correspondence Notification is created with the following values
      | status | Requested |
    And I get the notification "PREVIOUSLY_CREATED" using order service
    And I verify response is Base64 encoded

  @CP-42086 @API-CP-42086-04 @API-ECMS @burak
  Scenario: Verify Get Notification Image API error messages when image is not available for Twilio
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | badTextTem        |
      | channelType                     | Text              |
      | caseId                          | previouslyCreated |
      | language                        | English           |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
    When I send the request for an Outbound Correspondence to the service
    And I should see the Outbound Correspondence Notification is created with the following values
      | status | Requested |
    And I get the notification "PREVIOUSLY_CREATED" using order service
    And I verify error message as "document_does_not_exist_for_notification_id" for get notification API in Order Service

  @CP-42086 @API-CP-42086-045@API-ECMS @burak
  Scenario: Verify Get Notification Image API error messages when image is not available for SendGrid
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | badTemplat        |
      | channelType                     | Email             |
      | caseId                          | previouslyCreated |
      | language                        | English           |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
    When I send the request for an Outbound Correspondence to the service
    And I should see the Outbound Correspondence Notification is created with the following values
      | status | Requested |
    And I get the notification "PREVIOUSLY_CREATED" using order service
    And I verify error message as "document_does_not_exist_for_notification_id" for get notification API in Order Service

  @CP-42086 @API-CP-42086-06 @API-ECMS @burak
  Scenario Outline: Verify Get Notification Image API error messages for invalid Notification IDs (BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I get the notification "<Notification Id>" using order service
    And I verify error message as "<Error Message>" for get notification API in Order Service
    Examples:
      | Notification Id        | Error Message            |
      | LOWER_THAN_0           | Notification Id required |
      | INVALID_GREATER_THAN_0 | Notification not found   |

  @CP-42086 @API-CP-42086-07 @api-ecms-dceb @burak
  Scenario: Verify Get Notification Image API in Correspondence Order service returns response in base64 format for OnBase template (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | nowAvail1       |
      | channelType                     | Text            |
      | language                        | English         |
      | caseId                          | Random             |
      | requesterId                     | 2425            |
      | requesterType                   | ConnectionPoint |
    When I send the request for an Outbound Correspondence to the service
    And I see that a Document has been uploaded to Onbase for the "Text" Outbound Correspondence Notification
    And I should see the Outbound Correspondence Notification is created with the following values
      | status | Requested |
    And I get the notification "PREVIOUSLY_CREATED" using order service
    And I verify response is Base64 encoded

  @CP-42086 @API-CP-42086-08 @api-ecms-dceb @burak
  Scenario: Verify Get Notification Image API in Correspondence Order service returns response in base64 format for SendGrid template (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | nowAvail1       |
      | channelType                     | Email           |
      | language                        | English         |
      | caseId                          | Random             |
      | requesterId                     | 2425            |
      | requesterType                   | ConnectionPoint |
    When I send the request for an Outbound Correspondence to the service
    And I should see the Outbound Correspondence Notification is created with the following values
      | status | Requested |
    And I get the notification "PREVIOUSLY_CREATED" using order service
    And I verify response is Base64 encoded

  @CP-42086 @API-CP-42086-09 @api-ecms-dceb @burak
  Scenario: Verify Get Notification Image API in Correspondence Order service returns response in base64 format for Twilio template (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | nowAvail1       |
      | channelType                     | Text            |
      | language                        | English         |
      | caseId                          | Random             |
      | requesterId                     | 2425            |
      | requesterType                   | ConnectionPoint |
    When I send the request for an Outbound Correspondence to the service
    And I should see the Outbound Correspondence Notification is created with the following values
      | status | Requested |
    And I get the notification "PREVIOUSLY_CREATED" using order service
    And I verify response is Base64 encoded
    And I verify response is Base64 encoded as "TwilioTemplateWithSpecialCharacters"

  @CP-42086 @API-CP-42086-10 @api-ecms-dceb @burak
  Scenario: Verify Get Notification Image API error messages when image is not available (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | badCP42086      |
      | channelType                     | Text            |
      | language                        | English         |
      | caseId                          | Random             |
      | requesterId                     | 2425            |
      | requesterType                   | ConnectionPoint |
    When I send the request for an Outbound Correspondence to the service
    And I should see the Outbound Correspondence Notification is created with the following values
      | status | Requested |
    And I get the notification "PREVIOUSLY_CREATED" using order service
    And I verify error message as "document_does_not_exist_for_notification_id" for get notification API in Order Service

  @CP-42086 @API-CP-42086-11 @api-ecms-dceb @burak
  Scenario Outline: Verify Get Notification Image API error messages for invalid Notification IDs (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I get the notification "<Notification Id>" using order service
    And I verify error message as "<Error Message>" for get notification API in Order Service
    Examples:
      | Notification Id        | Error Message            |
      | LOWER_THAN_0           | Notification Id required |
      | INVALID_GREATER_THAN_0 | Notification not found   |