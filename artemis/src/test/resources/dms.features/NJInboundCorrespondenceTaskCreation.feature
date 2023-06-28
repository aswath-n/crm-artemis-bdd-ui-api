Feature: Tasks Created By Receiving NJ Inbound Documents

  @CP-NJ2605 @CP-NJ2605-01 @ui-ecms-nj @James
  Scenario: Verify a task is created when a rule has no required elements and there is not Case id or Consumer id in the event request (NJ)
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "NJ SBE General"
    And I want to add a task rule to the Inbound Correspondence Type "NJ SBE General" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12841 |
    And I send the request to update the Inbound Correspondence Type "NJ SBE General" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE General"
    And I have an Inbound Correspondence Save event Request with the following values
      | Channel     | Email                  |
      | Language    | Tagalog                |
      | ProcessType | INBOUND CORRESPONDENCE |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should that a Task has been created containing all the values sent in the request


  @CP-NJ2605 @CP-NJ2605-02 @ui-ecms-nj @James
  Scenario: Verify that is not created if it is ALREADY WORKED (NJ)
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "NJ SBE General"
    And I want to add a task rule to the Inbound Correspondence Type "NJ SBE General" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12841 |
    And I send the request to update the Inbound Correspondence Type "NJ SBE General" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE General"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | ALREADY WORKED |
      | Channel     | Email          |
    When I send the request to create the Inbound Correspondence Save Event
    Then I should that a Task has NOT been created

  @CP-3251 @CP-NJ3251 @CP-NJ3251-01 @api-ecms-nj @Sang
  Scenario: Verify Inbound Correspondence save event is created with correct values for post connection send event (NJ)
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "NJ SBE General"
    And I want to add a task rule to the Inbound Correspondence Type "NJ SBE General" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12841 |
    And I send the request to update the Inbound Correspondence Type "NJ SBE General" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE General"
    And I have an Inbound Correspondence Save event Request with the following values
      | Channel     | Email                  |
      | SetId       | random                 |
      | Language    | Tagalog                |
      | ProcessType | INBOUND CORRESPONDENCE |
    When I send the request to create the Inbound Correspondence Save Event
    And I initiate post search "INBOUND_CORRESPONDENCE_UPDATE_EVENT" list to find the Event ID created
    And I initiate get inbound correspondence event save event by the eventID found in list of Inbound Correspondence Save Event
    Then I verify the values contained in the payload of the save events

  @CP-3251 @CP-NJ3251 @CP-NJ3251-02 @api-ecms-nj @Sang
  Scenario: Verify Inbound Correspondence save event is not created when sending invalid specified JSON (NJ)
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "NJ SBE General"
    And I want to add a task rule to the Inbound Correspondence Type "NJ SBE General" with the following properties;
      | rank                 | 1     |
      | requiredDataElements | null  |
      | taskTypeID           | 12841 |
    And I send the request to update the Inbound Correspondence Type "NJ SBE General" - if not already setup as intended in the request
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE General"
    And I have an Inbound Correspondence Save Event with "Invalid" as value for the type
    Then I send the request to create the Inbound Correspondence Save Event then I should see status value is "FAILED"