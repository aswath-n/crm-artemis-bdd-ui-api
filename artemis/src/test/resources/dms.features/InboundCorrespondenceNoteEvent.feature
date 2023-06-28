Feature: Publish event when note is added to inbound correspondence

  @CP-12019 @CP-12019-01 @ui-ecms2 @James
  Scenario: Verify note save event is produced when triggered from searched inbound document
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | CaseID       | PreviouslyCreated       |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    And I click on the Inbound Document CID link "firstAvailable"
    When I add a new note to the Inbound Correspondence
    And I retrieve the "INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT" event that is produced by trace id
    Then I should see the payload for the "INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT" is as expected
    And I should see that the event mapping for "INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT" exists
    And I should see that the "INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT" record is produced to DPBI


  @CP-12019 @CP-12019-02 @ui-ecms2 @James
  Scenario: Verify note save event is produced when triggered from active contact context
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | CaseID       | PreviouslyCreated       |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    When I navigate to the case and contact details tab
    And I click on given Inbound Correspondence hyperlink "firstAvailable"
    When I add a new note to the Inbound Correspondence
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve the "INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT" event that is produced by trace id
    Then I should see the payload for the "INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT" is as expected
    And I should see that the event mapping for "INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT" exists
    And I should see that the "INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT" record is produced to DPBI



