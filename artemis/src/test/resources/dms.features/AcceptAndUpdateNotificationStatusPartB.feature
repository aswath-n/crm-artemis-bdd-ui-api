@CP-3246
Feature: Accept and Store Update to Notification Status Part B

@CP-3246-09 @API-ECMS @Prithika
Scenario: Verify Update to notification status as Exported when No Others are in Returned Or Error Or Sent
Given I will get the Authentication token for "<projectName>" in "CRM"
And I have a consumer on a case that wants to send an Outbound Correspondence
And I have an Outbound Correspondence with a notification for "Mail,Email"
Then I update Outbound Correspondence Notification "0" with the following values
| Exported | To New address |
And I retrieve Status and Reason from correspondence response before Notification update
Then I update Outbound Correspondence Notification "1" with the following values
| Exported | New Telephone Line |
And I retrieve Status and Reason from correspondence response after notification update
Then I verify Notification Status and Reason in correspondence response as updated above
Then I verify Correspondence Status and Reason
| Exported | New Telephone Line |
And I retrieve the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" event that is produced by trace id
Then I should see the payload for the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" is as expected
And I should see that the event mapping for "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" exists
And I should see that the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" record is produced to DPBI

@CP-3246-10 @API-ECMS @Prithika
Scenario: Verify Update to notification status as Assembled when Others are in Returned Or Error Or Sent Or Exported
Given I will get the Authentication token for "<projectName>" in "CRM"
And I have a consumer on a case that wants to send an Outbound Correspondence
And I have an Outbound Correspondence with a notification for "Mail,Email"
Then I update Outbound Correspondence Notification "0" with the following values
| Returned | To New address |
And I retrieve Status and Reason from correspondence response before Notification update
Then I update Outbound Correspondence Notification "1" with the following values
| Assembled | Mail Gathered |
And I retrieve Status and Reason from correspondence response after notification update
Then I verify Notification Status and Reason in correspondence response as updated above
Then I verify that there is no update on Correspondence Status and Reason

@CP-3246-11 @API-ECMS @Prithika
Scenario: Verify Update to notification status as Assembled when No Others are in Returned Or Error Or Sent
Given I will get the Authentication token for "<projectName>" in "CRM"
And I have a consumer on a case that wants to send an Outbound Correspondence
And I have an Outbound Correspondence with a notification for "Mail,Email"
Then I update Outbound Correspondence Notification "0" with the following values
| Requested | To New address |
Then I update Outbound Correspondence Notification "1" with the following values
| Assembled | Mail Gathered |
And I retrieve Status and Reason from correspondence response after notification update
Then I verify Notification Status and Reason in correspondence response as updated above
Then I verify Correspondence Status and Reason
| Assembled | Mail Gathered |
And I retrieve the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" event that is produced by trace id
Then I should see the payload for the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" is as expected
And I should see that the event mapping for "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" exists
And I should see that the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" record is produced to DPBI

@CP-3246-12 @API-ECMS @Prithika
Scenario: Verify Update to notification status as Requested when Others are in Canceled Or Precluded Or Requested
Given I will get the Authentication token for "<projectName>" in "CRM"
And I have a consumer on a case that wants to send an Outbound Correspondence
And I have an Outbound Correspondence with a notification for "Mail,Email"
Then I update Outbound Correspondence Notification "0" with the following values
| Canceled | Change address |
Then I update Outbound Correspondence Notification "1" with the following values
| Requested | Mail Gathered |
And I retrieve Status and Reason from correspondence response after notification update
Then I verify Notification Status and Reason in correspondence response as updated above
Then I verify Correspondence Status and Reason
| Requested |empty|
And I retrieve the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" event that is produced by trace id
Then I should see the payload for the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" is as expected
And I should see that the event mapping for "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" exists
And I should see that the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" record is produced to DPBI

@CP-3246-13 @API-ECMS @Prithika
Scenario: Verify Update to notification status as Requested when No Others are in Canceled Or Precluded Or Requested
Given I will get the Authentication token for "<projectName>" in "CRM"
And I have a consumer on a case that wants to send an Outbound Correspondence
And I have an Outbound Correspondence with a notification for "Mail,Email"
Then I update Outbound Correspondence Notification "0" with the following values
| Returned | To New address |
And I retrieve Status and Reason from correspondence response before Notification update
Then I update Outbound Correspondence Notification "1" with the following values
| Requested | Information Needed |
And I retrieve Status and Reason from correspondence response after notification update
Then I verify Notification Status and Reason in correspondence response as updated above
Then I verify that there is no update on Correspondence Status and Reason

@CP-3246-14 @API-ECMS @Prithika
Scenario: Verify Update to notification status as Canceled when All Others are in Precluded
Given I will get the Authentication token for "<projectName>" in "CRM"
And I have a consumer on a case that wants to send an Outbound Correspondence
And I have an Outbound Correspondence with a notification for "Mail,Email"
Then I update Outbound Correspondence Notification "0" with the following values
| Precluded | To New address |
Then I update Outbound Correspondence Notification "1" with the following values
| Canceled | Information Needed |
And I retrieve Status and Reason from correspondence response after notification update
Then I verify Notification Status and Reason in correspondence response as updated above
Then I verify Correspondence Status and Reason
| Canceled | Information Needed |
And I retrieve the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" event that is produced by trace id
Then I should see the payload for the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" is as expected
And I should see that the event mapping for "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" exists
And I should see that the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" record is produced to DPBI

@CP-3246-15 @API-ECMS @Prithika
Scenario: Verify Update to notification status as Canceled when All Others are in Canceled Or Precluded
Given I will get the Authentication token for "<projectName>" in "CRM"
And I have a consumer on a case that wants to send an Outbound Correspondence
And I have an Outbound Correspondence with a notification for "Mail,Email"
Then I update Outbound Correspondence Notification "0" with the following values
| Canceled | To New address |
Then I update Outbound Correspondence Notification "1" with the following values
| Canceled | Information Needed |
And I retrieve Status and Reason from correspondence response after notification update
Then I verify Notification Status and Reason in correspondence response as updated above
Then I verify Correspondence Status and Reason
| Canceled | Multiple - See Notifications |
And I retrieve the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" event that is produced by trace id
Then I should see the payload for the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API" is as expected
And I should see that the event mapping for "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" exists
And I should see that the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" record is produced to DPBI

@CP-3246-16 @API-ECMS @Prithika
Scenario: Verify Update to notification status as Canceled when All Others are not in Canceled Or Precluded
Given I will get the Authentication token for "<projectName>" in "CRM"
And I have a consumer on a case that wants to send an Outbound Correspondence
And I have an Outbound Correspondence with a notification for "Mail,Email"
Then I update Outbound Correspondence Notification "0" with the following values
| Returned | To New address |
And I retrieve Status and Reason from correspondence response before Notification update
Then I update Outbound Correspondence Notification "1" with the following values
| Canceled | Information Needed |
And I retrieve Status and Reason from correspondence response after notification update
Then I verify Notification Status and Reason in correspondence response as updated above
Then I verify that there is no update on Correspondence Status and Reason

@CP-3246-17 @API-ECMS @Prithika
Scenario: Verify Update to notification status as Precluded
Given I will get the Authentication token for "<projectName>" in "CRM"
And I have a consumer on a case that wants to send an Outbound Correspondence
And I have an Outbound Correspondence with a notification for "Mail,Email"
Then I update Outbound Correspondence Notification "0" with the following values
| Returned | To New address |
And I retrieve Status and Reason from correspondence response before Notification update
Then I update Outbound Correspondence Notification "1" with the following values
| Precluded | Information Needed |
And I retrieve Status and Reason from correspondence response after notification update
Then I verify Notification Status and Reason in correspondence response as updated above
Then I verify that there is no update on Correspondence Status and Reason