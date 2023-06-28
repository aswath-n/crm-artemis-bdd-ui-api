@CP-12498
Feature: Pass null instead of empty strings in Correspondence events

  @CP-12498-01 @API-ECMS @Prithika
  Scenario: Verify no empty strings in Outbound Correspondence events
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the request for an Outbound Correspondence With empty strings to the service and save traceID
    And I retrieve event by trace id and verify for empty strings
      | OUTBOUND_CORRESPONDENCE_SAVE_EVENT  |
      | NOTIFICATION_SAVE_EVENT             |
      | CORRESPONDENCE_RECIPIENT_SAVE_EVENT |
      | LETTER_DATA_SAVE_EVENT              |
    Then I update Outbound Correspondence Notification "0" with the following values
      | Error | Test Notification |
    And I retrieve event by trace id and verify for empty strings
      | NOTIFICATION_UPDATE_EVENT            |
      | OUTBOUND_CORRESPONDENCE_UPDATE_EVENT |

  @CP-12498-02 @API-ECMS @Prithika
  Scenario: Verify no empty strings in INBOUND_CORRESPONDENCE_SAVE_EVENT
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | FromName     | null                    |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I save the Digital Correspondence Id created to Inbound Correspondence Id
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE         | CREATEDDOCUMENT         |
      | PROCESSTYPE  | INBOUND CORRESPONDENCE  |
      | CHANNEL      | EMAIL                   |
      | documentType | maersk Case + Consumer |
      | STATUS       | COMPLETE                |
    When I send the String request to create the Inbound Correspondence Save Event
    And I retrieve response for "INBOUND_CORRESPONDENCE_SAVE_EVENT" and verify for empty strings

    # update_event  as per CP-29794 from API
  #@CP-12498-03 @API-ECMS @Prithika
  Scenario: Verify no empty strings in INBOUND_CORRESPONDENCE_UPDATE_EVENT
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on edit button on Inbound Settings Page
    Then I update the Inbound Correspondence "receiveddate" field with value "currentdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" event that is produced by trace id
    And I retrieve response for "INBOUND_CORRESPONDENCE_UPDATE_EVENT" and verify for empty strings

  @CP-12498-03 @API-ECMS @Prithika
  Scenario: Verify no empty strings in INBOUND_CORRESPONDENCE_UPDATE_EVENT
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I want to add a task rule to the Inbound Correspondence Type "maersk Case + Consumer" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12544 |
    And I send the request to update the Inbound Correspondence Type "maersk Case + Consumer" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with no Case ID in addition to no Consumer ID
    And I have an Inbound Correspondence Save event Request with the following values
      | TYPE        | PROCESSEDDOCUMENT |
      | ProcessType | RECEIVED          |
      | CHANNEL     | Mail              |
      | documentDate     | Today              |
    When I send the request to create the Inbound Correspondence Save Event
    And I retrieve response for "INBOUND_CORRESPONDENCE_INDEXED_EVENT" and verify for empty strings

  @CP-12498-04 @API-ECMS @Prithika
  Scenario: Verify no empty strings in INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    When I add a new note to the Inbound Correspondence
    And I retrieve response for "INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT" and verify for empty strings