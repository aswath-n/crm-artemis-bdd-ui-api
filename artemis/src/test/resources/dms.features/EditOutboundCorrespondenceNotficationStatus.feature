@CP-2935
Feature: Edit Notification Status of Outbound Correspondence

  @CP-2935 @CP-2935-01 @ui-ecms2 @Keerthi
  Scenario: Verify Actions dropdown values for Requested notification
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | externallink | externalRefType,random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I validated for "Requested" notification status
    Then I validate the "action notification" dropdown values in outbound correspondence
      | HOLD     |
      | RETURNED |
      | SENT     |
      | CANCEL   |
      | RESEND   |

  @CP-2935 @CP-2935-02 @ui-ecms2 @Keerthi
  Scenario: Verify Sent notification status, mandatory Reason with values and mandatory Status date with default current date
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | externallink | externalRefType,random |
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
    Then I verify notification status reason is in disable mode
    And verify the Status date field for sent status
    And I cleared the date from calendar
    And I click save for notification
    Then I verify error message for notification status date in Notification grid


  @CP-2935 @CP-2935-03 @ui-ecms2 @Keerthi
  Scenario: Update notification status to On Hold and Verify Actions dropdown values, mandatory Reason with values
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | externallink | externalRefType,random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on notification actions dropdown to select "HOLD"
    Then I validated for "On Hold" notification status
    And I click save for notification
    And I verify error message for notification status reason in Notification grid
    Then I verify the dropdown values for "Notification Reason"
      | State requested    |
      | User requested     |
      | Volume control     |
      | Channel issues     |
      | Destination issues |
      | Template issues    |
      | Vendor issues      |
    And I click save for notification
    Then I validate the "action notification" dropdown values in outbound correspondence
      | RESUME |
      | CANCEL |
    And I click on notification actions dropdown to select "RESUME"
    Then I validated for "Requested" notification status

  @CP-2935 @CP-2935-04 @ui-ecms2 @Keerthi
  Scenario: Update notification status to Returned and Verify Actions dropdown values, mandatory Reason with values and Return date
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | externallink | externalRefType,random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on notification actions dropdown to select "RETURNED"
    Then I validated for "Returned" notification status
    And I click save for notification
    And I verify error message for notification Return date in Notification grid
    And I verify error message for notification status reason in Notification grid
    And I Update the Return date field with "currentdate" for Returned status
    Then I verify the dropdown values for "Notification Reason"
      | Undeliverable                      |
      | Mailbox full                       |
      | Destination agent unresponsive     |
      | Destination agent rejected message |
      | Destination invalid                |
      | Refused                            |
      | Change of Address                  |
    And I click save for notification
    Then I validate the "action notification" dropdown values in outbound correspondence
      | RESEND |


  @CP-2935 @CP-2935-05 @ui-ecms2 @Keerthi
  Scenario: Verify Actions dropdown values for Exported notification
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | externallink | externalRefType,random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I update Outbound Notification Id "previouslyCreated" to status "Sent"
    And I update Outbound Notification Id "previouslyCreated" to status "Exported"
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I validated for "Exported" notification status
    Then I validate the "action notification" dropdown values in outbound correspondence
      | RETURNED |
      | SENT     |
      | CANCEL   |
      | RESEND   |

  @CP-2935 @CP-2935-06 @ui-ecms2 @Keerthi
  Scenario: Verify Actions dropdown values for Assembled notification
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | externallink | externalRefType,random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I update Outbound Notification Id "previouslyCreated" to status "Sent"
    And I update Outbound Notification Id "previouslyCreated" to status "Assembled"
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I validated for "Assembled" notification status
    Then I validate the "action notification" dropdown values in outbound correspondence
      | RETURNED |
      | SENT     |
      | CANCEL   |
      | RESEND   |


  @CP-2935 @CP-2935-07 @ui-ecms2 @Keerthi
  Scenario: Verify Actions dropdown values for Error notification
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | externallink | externalRefType,random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I update Outbound Notification Id "previouslyCreated" to status "Error"
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I validated for "Error" notification status
    Then I validate the "action notification" dropdown values in outbound correspondence
      | RETURNED |
      | RETRY    |
      | CANCEL   |
      | RESEND   |
    And I click on notification actions dropdown to select "RETRY"
    Then I validated for "Requested" notification status


  @CP-2935 @CP-2935-08 @ui-ecms2 @Keerthi
  Scenario: Verify notification update event is produced while updating notification status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | externallink | externalRefType,random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on notification actions dropdown to select "SENT"
    And I click save for notification
    And I retrieve the "NOTIFICATION_UPDATE_EVENT" event that is produced by trace id
    Then I should see the payload for the "NOTIFICATION_UPDATE_EVENT" is as expected
    And I should see that the event mapping for "NOTIFICATION_UPDATE_EVENT" exists
    And I should see that the "NOTIFICATION_UPDATE_EVENT" record is produced to DPBI


  @CP-2935 @CP-2935-09 @ui-ecms2 @Keerthi
  Scenario: Verify Status,Status Reason,Updated By,Updated Date,Sent Date for updated notification
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CCONLY            |
      | caseId                          | previouslyCreated |
      | REGARDINGCONSUMERID             | previouslyCreated |
      | firstName                       | test              |
      | lastName                        | test              |
      | channelType                     | Mail              |
      | streetAddress                   | test lane 1       |
      | city                            | cedar park        |
      | state                           | TX                |
      | zipcode                         | 78613             |
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on notification actions dropdown to select "SENT"
    Then I verify notification status reason is in disable mode
    And I click save for notification
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status       | Sent                  |
      | Updated By   | current user          |
      | Updated Date | current date and hour |
      | sent date    | current date and hour |

  @CP-28109 @CP-28109-01 @ui-ecms2 @James
  Scenario: Verify updated by on correspondence level is updated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | externallink | externalRefType,random |
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
    Then I verify notification status reason is in disable mode
    And verify the Status date field for sent status
    And I click save for notification
    Then I should see the Updated By value on the Correspondence level displays the user name of the Connection Point User


