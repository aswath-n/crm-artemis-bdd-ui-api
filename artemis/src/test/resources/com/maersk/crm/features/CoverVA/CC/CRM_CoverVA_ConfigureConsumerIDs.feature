Feature: CoverVA Configure Consumer IDs

  @CP-16850 @CP-16850-01 @muhabbat @ui-cc-va @crm-regression
  Scenario:  Active Contact Consumer Search with a MMIS Consumer ID
    Given I logged into CRM and select a project "COVER-VA2"
    And I click on initiate a contact button
    And I searched consumer with "MMIS" Consumer Id "351351351324"
    Then I validate consumer search result according to "MMIS" Consumer Id "351351351324"

  @CP-16850 @CP-16850-02 @muhabbat @ui-cc-va @crm-regression
  Scenario:  Manual Consumer Search with a MMIS Consumer ID
    Given I logged into CRM and select a project "COVER-VA2"
    When I navigate to Manual Consumer search page
    And I searched consumer with "MMIS" Consumer Id "351351351324"
    Then I validate consumer search result according to "MMIS" Consumer Id "351351351324"

  @CP-16850 @CP-16850-03 @muhabbat @ui-cc-va @crm-regression
  Scenario: Manual Contact record search with a MMIS Consumer ID
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I navigate to Contact Record search
    And I searched with "MMIS" Consumer Id "351351351324"
    Then Verify "MMIS" and Consumer Id "351351351324" is visible

  @CP-16850 @CP-16850-04 @muhabbat @ui-cc-va @crm-regression
  Scenario: Task record search with a MMIS Consumer ID
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I navigate to "Task Search" page
    And I searched task with "MMIS" Consumer Id "351351351324"
    Then I validate task search result displayed expected record

