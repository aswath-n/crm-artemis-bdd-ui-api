Feature: Outbound Correspondence Requests part1

  @CP-3039-01 @CP-3039 @ui-ecms1 @Sang
  Scenario: Verify status changes to Returned and have access to Reason and Returned date dropdown when I click on Returned in Notification Actions
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I select on the Outbound Correspondence by "previouslyCreated" CId in the case and contacts details tab
    And I click on notification actions dropdown to select "Returned"
    Then I verify status has changed to "Returned"
    And I have access to reason dropdown and return date dropdown

  @CP-3039-02 @CP-3039 @ui-ecms1 @Sang
  Scenario: Verify reasons in the Reasons dropdown for returned notifications
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I select on the Outbound Correspondence by "previouslyCreated" CId in the case and contacts details tab
    And I click on notification actions dropdown to select "Returned"
    And I click on the notification returned reason dropdown
    Then I verify the reason values contained are following
      | Undeliverable                      |
      | Refused                            |
      | Mailbox full                       |
      | Destination agent unresponsive     |
      | Destination agent rejected message |
      | Destination invalid                |
      | Change of Address                  |

  @CP-3039-03 @CP-3039 @ui-ecms1 @Sang
  Scenario: Verify the field is left blank error appears when saving without filling in Reasons and Return date fields
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I select on the Outbound Correspondence by "previouslyCreated" CId in the case and contacts details tab
    And I click on notification actions dropdown to select "Returned"
    And I click on save button in notifications details
    Then I verify the following error messages appears when Reason field is left blank
      | Status Reason is required and cannot be left blank |
    Then I verify the following error messages appears when RETURN DATE fields is left blank
      | RETURNED DATE is required when STATUS is Returned. |

  @CP-3039-04 @CP-3039 @ui-ecms1 @Sang
  Scenario: When I confirm my requests the system updates the Notification
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I saved logged in user ID
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I select on the Outbound Correspondence by "previouslyCreated" CId in the case and contacts details tab
    And I click on notification actions dropdown to select "Returned"
    And I select "Undeliverable" and "today's date" for reasons and returned date dropdowns
    And I click on save button in notifications details
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status        | Returned              |
      | Status Reason | Undeliverable         |
      | returned date | current date          |
      | Updated By    | UI User               |
      | Updated Date  | current date and hour |

  @CP-3039-05 @CP-3039 @ui-ecms1 @Sang
  Scenario: When I confirm my request the system cascade to correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I select on the Outbound Correspondence by "previouslyCreated" CId in the case and contacts details tab
    And I click on notification actions dropdown to select "Returned"
    And I select "Undeliverable" and "today's date" for reasons and returned date dropdowns
    And I click on save button in notifications details
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status        | Returned              |
      | Status Reason | Undeliverable         |
      | Updated Date  | current date and hour |
      | Updated By    | UI User               |

  @CP-3039-06 @CP-3039 @ui-ecms1 @Sang
  Scenario: When I confirm my request and the correspondence status is in error the system does not cascade to correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I update Outbound Correspondence CId "previouslyCreated" to status "Error"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I select on the Outbound Correspondence by "previouslyCreated" CId in the case and contacts details tab
    And I click on notification actions dropdown to select "Returned"
    And I select "Undeliverable" and "today's date" for reasons and returned date dropdowns
    And I click on save button in notifications details
    Then I verify the correspondence status is "Error"

  @CP-3039-07 @CP-3039 @ui-ecms1 @Sang
  Scenario: When I select the cancel button the system will revert the status back to sent
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I update Outbound Notification Id "previouslyCreatedlistofnid" to status "Sent"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I select on the Outbound Correspondence by "previouslyCreated" CId in the case and contacts details tab
    And I click on notification actions dropdown to select "Returned"
    And I select "Undeliverable" and "current date" for reasons and returned date dropdowns
    And I click on Cancel button on the notification
    Then I verify status has changed to "Sent"

  @CP-3039-08 @CP-3039 @ui-ecms1 @Sang
  Scenario: When I attempt to navigate away the system give me a warning message
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I update Outbound Notification Id "previouslyCreatedlistofnid" to status "Sent"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I select on the Outbound Correspondence by "previouslyCreated" CId in the case and contacts details tab
    And I click on notification actions dropdown to select "Returned"
    And I select "Undeliverable" and "today's date" for reasons and returned date dropdowns
    And I click on the back arrow button on Outbound Correspondence details
    Then I should see a warning message when navigating away "If you continue, all the captured information will be lost"

  @CP-3039-09 @CP-3039 @ui-ecms1 @Sang
  Scenario Outline: RETURNED option is not available if the Notification is in On Hold or Canceled Status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I update Outbound Notification Id "previouslyCreatedlistofnid" to status "<notificationStatus>"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I select on the Outbound Correspondence by "previouslyCreated" CId in the case and contacts details tab
    Then I verify Notification action dropdown does not contain Returned
    Examples:
      | notificationStatus |
      | On Hold            |
      | Canceled           |

  @CP-3039-10 @CP-3039 @ui-ecms1 @Sang
  Scenario Outline: RETURNED option is not available if the Notification is in On Hold or Canceled Status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I update Outbound Notification Id "previouslyCreatedlistofnid" to status "<notificationStatus>"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I select on the Outbound Correspondence by "previouslyCreated" CId in the case and contacts details tab
    Then I verify Notification action dropdown contains Returned
    Examples:
      | notificationStatus |
      | Requested          |
      | Error              |
      | Assembled          |
      | Exported           |
      | Sent               |

  @CP-3039-11 @CP-3039 @ui-ecms1 @Sang
  Scenario: When I confirm my request then it is visible in the Status History details for the notification
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I select on the Outbound Correspondence by "previouslyCreated" CId in the case and contacts details tab
    And I click on notification actions dropdown to select "Returned"
    And I select "Undeliverable" and "today's date" for reasons and returned date dropdowns
    And I click on save button in notifications details
    When I click on the Notifications detail page status history button
    Then I verify the Returned status is visible in status history

  @CP-12907-01 @CP-12907 @ui-ecms1 @Sang
  Scenario: When I cancel an outbound correspondence an event is published
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate post outbound correspondence with four recipient
    And I logged into CRM
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I search for the Outbound Correspondence with the new created CId
    And I click on the correspondence id of the search result
    And I click on edit button on view Outbound Correspondence details
    And I select "Canceled" for Status and "Requested in error" for status reason in Outbound Correspondence edit
    And I click on Save button in outbound correspondence edit
    And I click yes when given the option to confirm cancel or not cancel
    Then I verify Outbound correspondence is published for the canceled Outbound Correspondence
