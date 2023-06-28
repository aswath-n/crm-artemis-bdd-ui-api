Feature: Contact Record Search Part 2


  @CP-143 @CP-143-01 @asad @ui-core @crm-regression
  Scenario: Selecting a Contact Record
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for init CONTACT with Contact Reasons
      | contactReasonActions[0].contactRecordActions[0].contactRecordActionType | Escalated-3                 |
      | contactReasonActions[0].contactRecordReasonType                         | Complaint - Account Access  |
      | contactReasonActions[0].notes                                           | Created from API automation |
    Given I logged into CRM
    When I click contact search tab for Contact Record
    Then I will be able to select the Contact Record ID for the specific Contact Record and view Contact Record Details for Contact Record

  @CP-143 @CP-143-02.0 @asad @ui-core @crm-regression
  Scenario: Navigating Back to Search Results
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for init CONTACT with Contact Reasons
      | contactReasonActions[0].contactRecordActions[0].contactRecordActionType | Escalated-3                 |
      | contactReasonActions[0].contactRecordReasonType                         | Complaint - Account Access  |
      | contactReasonActions[0].notes                                           | Created from API automation |
    Given I logged into CRM
    When I click contact search tab for Contact Record
    And I search and view results for Contact Record
    Then I will be able to navigate back to the Manual Contact Record Search Results for Contact Record

  @CP-143 @CP-143-02.1 @asad @ui-core @crm-regression
  Scenario: Navigate back to Manual Contact Record Search Results
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for init CONTACT with Contact Reasons
      | contactReasonActions[0].contactRecordActions[0].contactRecordActionType | Escalated-3                 |
      | contactReasonActions[0].contactRecordReasonType                         | Complaint - Account Access  |
      | contactReasonActions[0].notes                                           | Created from API automation |
    Given I logged into CRM
    When I click contact search tab for Contact Record
    And I search and view results for Contact Record
    Then I can edit the Contact Record by adding an Additional Comment and will be able to navigated back to Search Results for Contact Record

  @CP-15547 @CP-15547-02 @umid @ui-core @crm-regression
  Scenario: Invalid Omitted from Disposition Dropdown on Contact Record Search
    Given I logged into CRM
    When I navigate to manual contact record search page
    Then I verify the dropdown values for "DISPOSITION"
      | Cancelled           |
      | Complete            |
      | Dropped             |
      | Escalate            |
      | Incomplete          |
      | Outbound Incomplete |
      | Requested Call Back |
      | Transfer            |
    And I do NOT see "Invalid" as a dropdown option
