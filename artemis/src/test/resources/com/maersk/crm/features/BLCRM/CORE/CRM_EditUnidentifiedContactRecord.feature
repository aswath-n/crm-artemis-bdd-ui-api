Feature: Edit Unidentified Contact Record

  @CP-469 @CP-469-01 @asad @ui-core @crm-regression
  Scenario: Edit Button on Contact Record UI
    Given I logged into CRM
    When I create Unidentified Contact Record for Contact Record Edit
    And I click contact search tab for Contact Record Edit
    Then I am able to see edit button displayed

  @CP-469 @CP-469-01.1 @asad @ui-core @crm-regression
  Scenario: Editable Fields
    Given I logged into CRM
    When I create Unidentified Contact Record for Contact Record Edit
    And I click contact search tab for Contact Record Edit
    Then I select the edit button and will be able to edit

  @CP-469 @CP-469-02 @asad @ui-core @crm-regression
  Scenario: Reason for Edit Drop-Down
    Given I logged into CRM
    When I create Unidentified Contact Record for Contact Record Edit
    And I click contact search tab for Contact Record Edit
    Then I select to edit and will be required to select a single Reason for Edit

  @CP-469 @CP-469-03 @asad @ui-core @crm-regression
  Scenario: Capture of User Edits
    Given I logged into CRM
    When I create Unidentified Contact Record for Contact Record Edit
    And I click contact search tab for Contact Record Edit
    And I select to open and edit the contact record
    Then I will be able to view the most recent edit made to the Contact Record

  @CP-15973 @CP-15973-01 @aikanysh @ui-core @crm-regression
  Scenario: Edit Unidentified Contact Record Disposition
    Given I logged into CRM
    When I create Unidentified Contact Record for Contact Record Edit
    And I click contact search tab for Contact Record Edit
    And I select above contact record to edit
    Then Verify disposition field values are exist
      | Cancelled           |
      | Complete            |
      | Dropped             |
      | Escalate            |
      | Requested Call Back |
      | Transfer            |
    And I edit disposition for above contact record with "Correcting Disposition" Reason and verify edit displayed on Edit History

