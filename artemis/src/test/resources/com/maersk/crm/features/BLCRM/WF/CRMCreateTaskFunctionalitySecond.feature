@CreateTask
Feature: Create Task Functionality Check Second

  @CP-5190 @CP-5190-01 @CP-13829 @CP-13829-01 @crm-regression @ui-wf @vidya
  Scenario: Calculate Due Date for Calendar day and  Business Days
    When I will get the Authentication token for "BLCRM" in "Tenant Manager"
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    Then I verify due date field in create task page for 5 "Business Day"
    And I select "Review Appeal Form" option in task type drop down
    Then I verify due date field in create task page for 7 "Calendar Day"
    And Close the soft assertions

  @CP-16247 @CP-16247-01 @CP-16063 @CP-16063-01 @@vidya @crm-regression @ui-wf
  Scenario: Verification of all drop down and mandatory fileds on the Create Review appeal Form Task Page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Review Appeal Form" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status | Complete |
    Then I verify the optional fields in Review Appeal Form create task page
    And I verify Action Taken drop down value
      | Acknowledgement Letter Generated |
      | IDR Resolved Letter Generated    |
      | No Action Taken                  |
      | Outbound Call Successful         |
      | Outbound Call Unsuccessful       |
    And I verify "disposition" single select drop down value
      | Resolved   |
      | Unresolved |
    And I click on save button in create task page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DISPOSITION"
    And I will provide following information before creating task
      | status | Escalated |
    And I will check action taken is not mandatory when task status is not complete
    And Close the soft assertions

  @CP-16344 @CP-16344-01 @CP-16063 @CP-16063-02 @vidya @crm-regression @ui-wf
  Scenario: Verification of all drop down and mandatory fileds on the Create Follow-up on Appeal Task Page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Review Appeal Form" task page
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button in create task page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DISPOSITION"
    And I select "Follow-up on Appeal" option in task type drop down
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status | Complete |
    Then I verify the optional fields in Review Appeal Form create task page
    And I click on save button in create task page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DISPOSITION"
    And Close the soft assertions

  @CP-16247 @CP-16247-01 @vidya @crm-regression @ui-wf
  Scenario: Verification of all drop down on the Create Review appeal Form Task Page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Review Appeal Form" task page
    And I will provide following information before creating task
      | status | Complete |
    Then I verify the optional fields in Review Appeal Form create task page
    And I verify Action Taken drop down value
      | Acknowledgement Letter Generated |
      | IDR Resolved Letter Generated    |
      | No Action Taken                  |
      | Outbound Call Successful         |
      | Outbound Call Unsuccessful       |
    And I verify "disposition" single select drop down value
      | Resolved   |
      | Unresolved |
    And Close the soft assertions

  @CP-16344 @CP-16344-02 @vidya @crm-regression @ui-wf
  Scenario: Verification of all drop down on the Create Follow-up on Appeal Task Page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Follow-up on Appeal" task page
    And I will provide following information before creating task
      | status | Complete |
    Then I verify the optional fields in Review Appeal Form create task page
    And I verify Action Taken drop down value
      | IDR Resolved Letter Generated   |
      | IDR Unresolved Letter Generated |
      | No Action Taken                 |
    And I verify "disposition" single select drop down value
      | IDR Successful   |
      | IDR Unsuccessful |
    And Close the soft assertions

  @CP-17646 @CP-17646-01 @vidya @crm-regression @ui-wf
  Scenario: Verification of Reason drop down values in Dropdown One and Dropdown Two create task page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Dropdowns One" task page
    And I verify Reason drop down value
      | Appeal/IDR |
      | Complaint  |
      | Enrollment |
    And I select "Dropdowns Two" option in task type drop down
    And I verify Reason drop down value
      | Customer Service         |
      | Customer Service - Agent |
      | Member Portal            |
      | Other                    |
      | Timeliness               |
    And Close the soft assertions

  @CP-12321 @CP-12321-02 @vidya @crm-regression @ui-wf
  Scenario: Verification of fields error message for new old and provider address line 1 if we enter only alphabetic
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Address" task page
    And I will provide following information before creating task
      | newAddressLine1      | New Address Line      |
      | oldAddressLine1      | Old Address Line      |
      | providerAddressLine1 | Provider Address Line |
    And I click on save button on task edit page
    Then I verify field level error message for address line 1
      | New Address Line 1      |
      | Old Address Line 1      |
      | Provider Address Line 1 |
    And Close the soft assertions

  @CP-12321 @CP-12321-03 @vidya @crm-regression @ui-wf
  Scenario: Verification of fields error message for new old and provider address line 1 if we enter only numbers
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Address" task page
    And I will provide following information before creating task
      | newAddressLine1      | 123456 |
      | oldAddressLine1      | 123456 |
      | providerAddressLine1 | 123456 |
    And I click on save button on task edit page
    Then I verify field level error message for address line 1
      | New Address Line 1      |
      | Old Address Line 1      |
      | Provider Address Line 1 |
    And Close the soft assertions

  @CP-28998 @CP-28998-01 @priyal @crm-regression @ui-wf
  Scenario: Verify Systematically Closed Indicator on Task Type Configuration on create page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Member Matching" task page
    Then I verify "taskStatus" single select drop down value
      | Created   |
      | Escalated |
    And I select "General" option in task type drop down
    Then I verify "taskStatus" single select drop down value
      | Complete  |
      | Created   |
      | Escalated |
    And I select "Member Matching Research" option in task type drop down
    Then I verify "taskStatus" single select drop down value
      | Created   |
      | Escalated |
    And I select "General" option in task type drop down
    Then I verify "taskStatus" single select drop down value
      | Complete  |
      | Created   |
      | Escalated |
    And I select "Review Incomplete Application" option in task type drop down
    Then I verify "taskStatus" single select drop down value
      | Created   |
      | Escalated |
    And I select "General" option in task type drop down
    Then I verify "taskStatus" single select drop down value
      | Complete  |
      | Created   |
      | Escalated |
    And Close the soft assertions
