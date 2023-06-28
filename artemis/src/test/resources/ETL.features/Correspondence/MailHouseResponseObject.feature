Feature: Mail House Response Object Job for BLCRM and INEB

  Background:
    Given I have cleaned up the target folder from previous "mailhouseRespObjectJob" runs

  @CP-2956 @CP-2956-01 @CORRESPONDENCE_ETL_REGRESSION @James
  Scenario: Verify Notification Record is updated when Mail House Response Object Job runs
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a pdf named after the "previouslyCreated" notification that is contained in a zip file
    And I upload the "mailhouseRespObjectJob" file to aws bucket "max-etl-baseline-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseRespObjectJob" in project "BLCRM"
    Then I should see the notification has been updated properly as expected from running the "mailhouseRespObjectJob"

  @CP-2956 @CP-2956-02 @CORRESPONDENCE_ETL_REGRESSION @James
  Scenario: Verify dip file after Mail House Response Object Job runs
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a pdf named after the "previouslyCreated" notification that is contained in a zip file
    And I upload the "mailhouseRespObjectJob" file to aws bucket "max-etl-baseline-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseRespObjectJob" in project "BLCRM"
    Then I verify the dip file that was been created in the aws bucket "max-etl-baseline-non-prod" folder "ECMS/Onbase/"


  @CP-24345 @CP-39472 @CP-24345-01 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify Notification Record is updated when Mail House Response Object Job runs for INEB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have specified the following values in the request for an Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | default           |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I have a pdf named after the "previouslyCreated" notification that is contained in a zip file
    And I upload the "mailhouseRespObjectJob" file to aws bucket "max-etl-ineb-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseRespObjectJob" in project "INEB"
    Then I should see the notification has been updated properly as expected from running the "mailhouseRespObjectJob"

  @CP-24345 @CP-24345-02 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify dip file after Mail House Response Object Job runs for INEB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have specified the following values in the request for an Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | default           |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I have a pdf named after the "previouslyCreated" notification that is contained in a zip file
    And I upload the "mailhouseRespObjectJob" file to aws bucket "max-etl-ineb-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseRespObjectJob" in project "INEB"
    Then I verify the dip file that was been created in the aws bucket "max-etl-ineb-non-prod" folder "ECMS/Onbase/"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | objectReceivedOn   | current           |
      | Updated Date       | current           |
      | objectParentFileId | previouslyCreated |


  @CP-3226 @CP-3226-01 @CORRESPONDENCE_ETL_REGRESSION @RuslanL
  Scenario: Verify the System creates print file and notification records are updated to Exported after Mail House Extract Job runs
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | default         |
      | language                        | English         |
      | caseId                          | Random          |
      | regardingConsumerId             | Random          |
      | requesterId                     | 2425            |
      | requesterType                   | ConnectionPoint |
      | channelType                     | Mail            |
      | address                         | random          |
    When I send the request for an Outbound Correspondence to the service
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I retrieve the Outbound Correspondence by Correspondence ID
    And I retrieve and store the Unprocessed Notifications for channel "Mail"
    And I retrieve and store letter data for each Unprocessed Notifications
    When I run the "mailhouseExtractJob" in project "BLCRM"
    Then I verify the zip file that was been created in the aws bucket "max-etl-baseline-non-prod" folder "ECMS/from_Max/Daily/"
    And I verify the process updates the Status of each Notification successfully to "Exported"
    And I should see that following records are produced to DPBI
      | OUTBOUND_CORRESPONDENCE_UPDATE_EVENT |
      | NOTIFICATION_UPDATE_EVENT            |

  @CP-3226 @CP-3226-02 @CORRESPONDENCE_ETL_REGRESSION @RuslanL
  Scenario: Verify the System creates print file and notification records are updated to Error after Mail House Extract Job runs
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | ETL3226         |
      | language                        | English         |
      | caseId                          | Random          |
      | requesterId                     | 2425            |
      | requesterType                   | ConnectionPoint |
      | channelType                     | Mail            |
      | address                         | random          |
    When I send the request for an Outbound Correspondence to the service
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I retrieve the Outbound Correspondence by Correspondence ID
    And I retrieve and store the Unprocessed Notifications for channel "Mail"
    And I retrieve and store letter data for each Unprocessed Notifications
    When I run the "mailhouseExtractJob" in project "BLCRM"
    And I verify the process updates the Status of each Notification successfully to "Error"
    And I should see that following records are produced to DPBI
      | OUTBOUND_CORRESPONDENCE_UPDATE_EVENT |
      | NOTIFICATION_UPDATE_EVENT            |



