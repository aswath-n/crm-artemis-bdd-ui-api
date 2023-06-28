Feature: Validation of Manage Queue Filter Page

  @CP-659 @CP-659-01 @CP-665 @CP-665-01 @CP-655 @CP-655-01 @CP-662 @CP-662-01 @CP-21672 @CP-21672-01 @vidya @Basha @crm-regression @ui-wf
  Scenario: Verify create, List, view and Edit details of Active Q Filter record
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Manage Queue Filter" page
    And I navigate to create filter page
    And I will create task queue filter with the below information
      | name              | dummy                |
      | description       | dummy                |
      | taskType          | General,Inbound Task |
      | applyFilterFor    | TEAM                 |
      | applyFilterSubTab | TestTeam             |
      | status            | ACTIVE               |
#    Then I verify task queue filter save success message
    And I verify Task queue filter list displayed
    Then I will verify record in List page
    And I will get the index of newly created record and click on first name for that
    And I will click on back arrow on view task page
    And I click on cancel button on create task warning message
    Then I verify Task Queue Filter view details
    And I will click on edit button in q filter view page
    And I will edit task queue filter with the below information
      | name              | dummy                       |
      | description       | vasya                       |
      | deselectTaskType  | General,Inbound Task        |
      | taskType          | Inbound Task,Correspondence |
      | applyFilterFor    | USER                        |
      | applyFilterSubTab | Nancy Percy                 |
      | status            | ACTIVE                      |
#    Then I verify task queue filter edit success message
    And I verify Task queue filter list displayed
    Then I will verify record in List page
    And I will get the index of newly created record and click on first name for that
    Then I verify Task Queue Filter view details
    And Close the soft assertions

  @CP-659 @CP-659-02 @CP-665 @CP-665-02 @CP-655 @CP-655-02 @CP-662 @CP-662-02 @CP-21672 @CP-21672-02 @vidya @Basha @crm-regression @ui-wf
  Scenario: Verify create, List, view and Edit details of InActive Q Filter record
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Manage Queue Filter" page
    And I navigate to create filter page
    And I will create task queue filter with the below information
      | name              | random               |
      | description       | random               |
      | taskType          | General,Inbound Task |
      | applyFilterFor    | BUSINESS UNIT        |
      | applyFilterSubTab | Csr team             |
      | status            ||
#    Then I verify task queue filter save success message
    And I verify Task queue filter list displayed
    And I will get the index of newly created record and click on first name for that
    And I will click on edit button in q filter view page
    And I will edit task queue filter with the below information
      | name              | dummy                |
      | description       | dummy                |
      | deselectTaskType  | General,Inbound Task |
      | taskType          | Plan/Provider        |
      | applyFilterFor    | TEAM                 |
      | applyFilterSubTab | TestTeam             |
      | status            | ACTIVE               |
#    Then I verify task queue filter edit success message
    And I verify Task queue filter list displayed
    Then I will verify record in List page
    And I will get the index of newly created record and click on first name for that
    Then I verify Task Queue Filter view details
    And Close the soft assertions

  @CP-659 @CP-659-03 @CP-665 @CP-665-03 @CP-655 @CP-655-03 @CP-662 @CP-662-03 @vidya @Basha @crm-regression @ui-wf
  Scenario: Verify Description field is optional in create, List, view and Edit page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Manage Queue Filter" page
    And I navigate to create filter page
    And I will create task queue filter with the below information
      | name              | ABCDEF        |
      | description       ||
      | taskType          | General       |
      | applyFilterFor    | USER          |
      | applyFilterSubTab | Antony Pelton |
      | status            | ACTIVE        |
#    Then I verify task queue filter save success message
    And I verify Task queue filter list displayed
    And I will get the index of newly created record and click on first name for that
    And I will click on edit button in q filter view page
    And I will edit task queue filter with the below information
      | name              | random                      |
      | description       | random                      |
      | deselectTaskType  | General                     |
      | taskType          | Inbound Task,General/CRM/EB |
      | applyFilterFor    | BUSINESS UNIT               |
      | applyFilterSubTab | Csr team                    |
      | status            ||
#    Then I verify task queue filter edit success message
    And I verify Task queue filter list displayed
    And Close the soft assertions

  @CP-659 @CP-659-04 @CP-21672 @CP-21672-03 @crm-regression @ui-wf @Basha
  Scenario: Verification of Task Queue Filter List sorting order
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Manage Queue Filter" page
    Then I verify task queue filter records are in sorted order
    And Close the soft assertions

  @CP-659 @CP-659-05 @CP-21672 @CP-21672-05 @crm-regression @ui-wf @Basha
  Scenario: Verification 5 records are displayed per page and pagination in Task Queue Filter
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Manage Queue Filter" page
    Then I verify task queue filter has five records with pagination
    And Close the soft assertions

  @CP-655 @CP-655-04 @vidya @crm-regression @ui-wf
  Scenario: Disable ‘Apply Filter For’ if Task type dropdown is not populated ad Mandatory error message on create Q filter page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Manage Queue Filter" page
    And I navigate to create filter page
    Then I verify all the fields are displayed on create queue screen
    And I verify Apply Filter drop down is disabled
    When I select "General" value in Task Type drop down
    Then I verify Apply Filter drop down is enable
    And I verify Apply Filter drop down values
      |BUSINESS UNIT|
      |TEAM|
      |USER|
    And I select "BUSINESS UNIT" in Apply Filter For drop down
    And I verify the "Business Unit" drop down has value "Csr team"
    Then I select "Correspondence" value in Task Type drop down
    Then I select "General" value in Task Type drop down
    And I verify the "Business Unit" drop down has value "Back Offer Room"
    Then I select "Correspondence" value in Task Type drop down
    Then I verify Apply Filter drop down is disabled
    And I verify "BUSINESS UNIT" drop down is disapper from screen
    When I select save button
    Then I verify required field error message "Name"
    And I verify required field error message "Task Type"
    And I verify required field error message "Apply Filter For"
    And I will scroll up to application
    And I select "General" value in Task Type drop down
    And I select "BUSINESS UNIT" in Apply Filter For drop down
    When I select save button
    Then I verify required field error message "Business Units"
    And I select "TEAM" in Apply Filter For drop down
    When I select save button
    Then I verify required field error message "Teams"
    And I verify the "Teams" drop down has value "TestTeam"
    And I select "USER" in Apply Filter For drop down
    When I select save button
    Then I verify required field error message "Users"
    And I verify the "User" drop down has value "Service AccountOne"
    And Close the soft assertions

  @CP-12580 @CP-12580-01 @CP-662 @CP-662-04 @vidya @nj-regression @ui-wf-nj
  Scenario: verify Hide Business Unit and Users from Queue Filter Dropdown Options in NJ-SBE project
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "Manage Queue Filter" page
    And I navigate to create filter page
    Then I verify all the fields are displayed on create queue screen
    And I verify Apply Filter drop down is disabled
    When I select "Review Complaint" value in Task Type drop down
    Then I verify Apply Filter drop down is enable
    And I verify Apply Filter drop down values
      |TEAM|
    Then I select "Review Complaint" value in Task Type drop down
    And I will create task queue filter with the below information
      | name                 |dummy|
      | description          |dummy|
      | taskType             |Review Complaint,Returned Mail|
      | applyFilterFor       |TEAM|
      | applyFilterSubTab    |CSR Hampton,Back Office Remote|
      | status               |ACTIVE|
    Then I verify task queue filter save success message
    And I verify Task queue filter list displayed
    Then I will verify record in List page
    And I will get the index of newly created record and click on first name for that
    Then I verify Task Queue Filter view details
    And I will click on edit button in q filter view page
    And I verify Apply Filter drop down values in edit page
      |TEAM|
    And I will edit task queue filter with the below information
      | name                 |dummy|
      | description          |dummy|
      | deselectTaskType     |Review Complaint,Returned Mail|
      | taskType             |Outbound Call,Process Inbound Document|
      | applyFilterFor       |TEAM|
      | applyFilterSubTab    |CSR Remote,Health Plan Liaison|
      | status               ||
    Then I verify task queue filter edit success message
    And I verify Task queue filter list displayed
    Then I will verify record in List page
    And I will get the index of newly created record and click on first name for that
    Then I verify Task Queue Filter view details
    And Close the soft assertions

  @CP-662 @CP-662-05 @vidya @crm-regression @ui-wf
  Scenario: Verify Cancel button funtionality in edit Q filter page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Manage Queue Filter" page
    And I verify Task queue filter list displayed
    And I select first record and navigate to view q filter page
    And I will click on edit button in q filter view page
    And I will edit task queue filter with the below information
      | name                 ||
      | description          ||
    And I deselect all task types in edit page
    When I select save button
    And I verify Apply Filter drop down is disabled in edit q filter page
    Then I verify required field error message "Name"
    And I verify required field error message "Task Type"
    And I verify required field error message "Apply Filter For"
    And I click on cancel button on create task type screen
    Then I verify warning message is displayed for "Cancel" function in Edit Q Filter page
    And I click on cancel button on create task warning message
    Then I verify it should remain on the edit q filter page and information should not save
    And I click on cancel button on create task type screen
    Then I verify warning message is displayed for "Cancel" function in Edit Q Filter page
    And I click on continue button on create task warning message
    And Close the soft assertions

  @CP-662 @CP-662-06 @vidya @crm-regression @ui-wf
  Scenario: Verify Back arrow button functionality in Edit Q filter page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Manage Queue Filter" page
    And I verify Task queue filter list displayed
    And I select first record and navigate to view q filter page
    And I will click on edit button in q filter view page
    And I deselect all task types in edit page
    And I will edit task queue filter with the below information
      | name              ||
      | description       | Test Edit                   |
      | taskType          | Inbound Task,General/CRM/EB |
      | applyFilterFor    | BUSINESS UNIT               |
      | applyFilterSubTab | Csr team                    |
      | status            ||
    And I will click on back arrow on view task page
    Then I verify warning message is displayed for "Back Arrow" function in Edit Q Filter page
    And I click on cancel button on create task warning message
    And I will click on back arrow on view task page
    Then I verify warning message is displayed for "Back Arrow" function in Edit Q Filter page
    And I click on continue button on create task warning message
    And Close the soft assertions

  @CP-662 @CP-662-07 @vidya @crm-regression @ui-wf
  Scenario: Verify Navigate Away funtionality in Edit Q filter page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Manage Queue Filter" page
    And I verify Task queue filter list displayed
    And I select first record and navigate to view q filter page
    And I will click on edit button in q filter view page
    And I deselect all task types in edit page
    And I will edit task queue filter with the below information
      | name                 ||
      | description          |Test Edit|
      | taskType             |Inbound Task,General/CRM/EB|
      | applyFilterFor       |BUSINESS UNIT|
    When I select save button
    Then I verify required field error message "Business Units"
    When I click contact search tab for Contact Record
    Then I verify warning message is displayed for "Navigate Away" function in Edit Q Filter page
    And I click on cancel button on create task warning message
    Then I verify it should remain on the edit q filter page and information should not save
    When I click contact search tab for Contact Record
    Then I verify warning message is displayed for "Navigate Away" function in Edit Q Filter page
    And I click on continue button on create task warning message
    Then I navigate to manual contact record search page
    And Close the soft assertions

  @CP-15548 @CP-15548-01 @CP-655 @CP-655-05 @CP-20585 @CP-20585-03 @crm-regression @ui-wf @vidya
  Scenario: Verify task type drop down has Active task type in create q filter page and listed in alphabetical order
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I will get the all service request which are present in BLCRM
    When I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I navigate to "Manage Queue Filter" page
    And I navigate to create filter page
    Then I verify service requests are not present in task type dropdown on create queue filter
    And I will get the all active task type which are present in BLCRM
    And I verify task type drop down has only active task types in create queue filter
    And Close the soft assertions

  @CP-15548 @CP-15548-02 @CP-662 @CP-662-08 @CP-20585 @CP-20585-04 @crm-regression @ui-wf @vidya
  Scenario: Verify task type drop down has Active task type in edit q filter page and listed in alphabetical order
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I will get the all service request which are present in BLCRM
    When I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I navigate to "Manage Queue Filter" page
    And I verify Task queue filter list displayed
    And I select first record and navigate to view q filter page
    And I will click on edit button in q filter view page
    Then I verify service requests are not present in task type dropdown on edit queue filter
    And I will get the all active task type which are present in BLCRM
    And I verify task type drop down has only active task types in edit queue filter
    And Close the soft assertions

  @CP-662 @CP-662-09 @vidya @crm-regression @ui-wf
  Scenario: Disable ‘Apply Filter For’ if Task type dropdown is not populated ad Mandatory error message on edit Q filter page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Manage Queue Filter" page
    And I verify Task queue filter list displayed
    And I select first record and navigate to view q filter page
    And I will click on edit button in q filter view page
    And I will edit task queue filter with the below information
      | applyFilterFor | BUSINESS UNIT |
    And I verify the "Business Unit" drop down has value "Csr team" on edit page
    Then I select "Correspondence" value in Task Type drop down
    Then I select "General" value in Task Type drop down
    And I verify the "Business Unit" drop down has value "Back Offer Room" on edit page
    And I deselect all task types in edit page of manager queue filter
    Then I verify Apply Filter drop down is disabled
    And I verify "BUSINESS UNIT" drop down is disapper from screen
    And I will edit task queue filter with the below information
      | name ||
    When I select save button
    Then I verify required field error message "Name"
    And I verify required field error message "Task Type"
    And I verify required field error message "Apply Filter For"
    And I will scroll up to application
    And I select "General" value in Task Type drop down
    And I will edit task queue filter with the below information
      | applyFilterFor | BUSINESS UNIT |
    When I select save button
    Then I verify required field error message "Business Units"
    And I will edit task queue filter with the below information
      | applyFilterFor | TEAM |
    When I select save button
    Then I verify required field error message "Teams"
    And I verify the "Teams" drop down has value "TestTeam"
    And I will edit task queue filter with the below information
      | applyFilterFor | USER |
    When I select save button
    Then I verify required field error message "Users"
    And I verify the "User" drop down has value "Service AccountOne" on edit page
    And Close the soft assertions

  @CP-13414 @CP-13414-01 @CP-21672 @CP-21672-06 @crm-regression  @ui-wf-nj @vidya
  Scenario Outline: Verify Manage Queue Filter is hide for user who don't have permission in TM roles
    When I logged into CRM with "Service Account 2" and select a project "<projectName>"
    Then I will verify Manage Queue Filter is hide for user
    And Close the soft assertions
    Examples:
      | projectName |
      | NJ-SBE      |
      | BLCRM       |