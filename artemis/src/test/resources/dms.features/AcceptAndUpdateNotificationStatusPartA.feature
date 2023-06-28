@CP-3246
Feature: Accept and Store Update to Notification Status Part A

  @CP-3246-01 @API-ECMS @Prithika
  Scenario: Verify Update to notification status as Error when No Others are in Error
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Then I update Outbound Correspondence Notification "1" with the following values
      | Error | This is going to be fifty characters and verified!|
    And I retrieve Status and Reason from correspondence response after notification update
    Then I verify Notification Status and Reason in correspondence response as updated above
    Then I verify Correspondence Status and Reason
      | Error | This is going to be fifty characters and verified!|
    And I retrieve the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" event that is produced by trace id
    Then I should see the payload for the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" is as expected
    And I should see that the event mapping for "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" exists
    And I should see that the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" record is produced to DPBI


  @CP-3246-02 @API-ECMS @Prithika
  Scenario: Verify Update to notification status as Error when Others are in Error
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Then I update Outbound Correspondence Notification "1" with the following values
      | Error | This is going to be fifty characters and verified!|
    Then I update Outbound Correspondence Notification "2" with the following values
      | Error | Updated Error |
    And I retrieve Status and Reason from correspondence response after notification update
    Then I verify Notification Status and Reason in correspondence response as updated above
    Then I verify Correspondence Status and Reason
      | Error | Multiple - See Notifications |
    And I retrieve the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" event that is produced by trace id
    Then I should see the payload for the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" is as expected
    And I should see that the event mapping for "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" exists
    And I should see that the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" record is produced to DPBI

  @CP-3246-03 @API-ECMS @Prithika
  Scenario: Verify Update to notification status as Returned when Others are in Error
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Then I update Outbound Correspondence Notification "1" with the following values
      | Error | This is going to be fifty characters and verified!|
    And I retrieve Status and Reason from correspondence response before Notification update
    Then I update Outbound Correspondence Notification "2" with the following values
      | Returned | Invalid Address |
    And I retrieve Status and Reason from correspondence response after notification update
    Then I verify Notification Status and Reason in correspondence response as updated above
    Then I verify that there is no update on Correspondence Status and Reason

  @CP-3246-04 @API-ECMS @Prithika
  Scenario: Verify Update to notification status as Returned when Others are in Returned
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Then I update Outbound Correspondence Notification "1" with the following values
      | Returned | Invalid Zip|
    Then I update Outbound Correspondence Notification "2" with the following values
      | Returned | Invalid Address |
    And I retrieve Status and Reason from correspondence response after notification update
    Then I verify Notification Status and Reason in correspondence response as updated above
    Then I verify Correspondence Status and Reason
      | Returned | Multiple - See Notifications |
    And I retrieve the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" event that is produced by trace id
    Then I should see the payload for the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" is as expected
    And I should see that the event mapping for "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" exists
    And I should see that the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" record is produced to DPBI


  @CP-3246-05 @API-ECMS @Prithika
  Scenario: Verify Update to notification status as Returned when No Others are in Returned
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Then I update Outbound Correspondence Notification "2" with the following values
      | Sent | This is going to be fifty characters and verified!|
    Then I update Outbound Correspondence Notification "0" with the following values
      | Exported | New Telephone Line |
    Then I update Outbound Correspondence Notification "1" with the following values
      | Returned | Invalid Address |
    And I retrieve Status and Reason from correspondence response after notification update
    Then I verify Notification Status and Reason in correspondence response as updated above
    Then I verify Correspondence Status and Reason
      | Returned | Invalid Address |
    And I retrieve the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" event that is produced by trace id
    Then I should see the payload for the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" is as expected
    And I should see that the event mapping for "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" exists
    And I should see that the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" record is produced to DPBI

  @CP-3246-06 @API-ECMS @Prithika
  Scenario: Verify Update to notification status as Sent when Others are in Returned Or Error
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Then I update Outbound Correspondence Notification "2" with the following values
      | Error | This is going to be fifty characters and verified!|
    Then I update Outbound Correspondence Notification "1" with the following values
      | Returned | Invalid Address |
    And I retrieve Status and Reason from correspondence response before Notification update
    Then I update Outbound Correspondence Notification "0" with the following values
      | Sent | Message sent |
    And I retrieve Status and Reason from correspondence response after notification update
    Then I verify Notification Status and Reason in correspondence response as updated above
    Then I verify that there is no update on Correspondence Status and Reason

  @CP-3246-07 @API-ECMS @Prithika
  Scenario: Verify Update to notification status as Sent when No Others are in Returned Or Error
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Then I update Outbound Correspondence Notification "1" with the following values
      | Exported | New Telephone Line |
    Then I update Outbound Correspondence Notification "2" with the following values
      | Exported | New Vendor |
    And I retrieve Status and Reason from correspondence response before Notification update
    Then I update Outbound Correspondence Notification "0" with the following values
      | Sent | Message sent |
    And I retrieve Status and Reason from correspondence response after notification update
    Then I verify Notification Status and Reason in correspondence response as updated above
    Then I verify Correspondence Status and Reason
      | Sent | Message sent |
    And I retrieve the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" event that is produced by trace id
    Then I should see the payload for the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" is as expected
    And I should see that the event mapping for "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" exists
    And I should see that the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" record is produced to DPBI

  @CP-3246-08 @API-ECMS @Prithika
  Scenario: Verify Update to notification status as Exported when Others are in Returned Or Error Or Sent
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email"
    Then I update Outbound Correspondence Notification "0" with the following values
      | Sent | Message sent |
    And I retrieve Status and Reason from correspondence response before Notification update
    Then I update Outbound Correspondence Notification "1" with the following values
      | Exported | New Telephone Line |
    And I retrieve Status and Reason from correspondence response after notification update
    Then I verify Notification Status and Reason in correspondence response as updated above
    Then I verify that there is no update on Correspondence Status and Reason




