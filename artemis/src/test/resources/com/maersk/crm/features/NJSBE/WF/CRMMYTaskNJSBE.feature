Feature: My Task View Validation

  @CP-10684 @CP-10684-01 @ui-wf-nj @nj-regression @Basha
  Scenario: Verify initiate button for the Review complaint in MY Task
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Review Complaint" task page
    And I will provide following information before creating task
      | taskInfo          |Test CP-10685|
      | assignee          |Service AccountOne|
      | complaintAbout    |CAC (maersk)|
      | name              |Vidya Mithun|
      | reason            |Customer Service|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    Then I Verify "Review Complaint" task with "Created" status does have Initiate button
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I changed status on edit page to "Escalated"
    And I click on save button on task edit page
    And I will click on back arrow on view task page
    And I click task id to get the results in descending order
    Then I Verify "Review Complaint" task with "Escalated" status does have Initiate button
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I changed status on edit page to "OnHold"
    And I select value in "REASON FOR HOLD" drop down
    And I click on save button on task edit page
    And I will click on back arrow on view task page
    And I click task id to get the results in descending order
    Then I Verify "Review Complaint" task with "OnHold" status does have Initiate button
    And Close the soft assertions

  @CP-13257 @CP-13257-02 @CP-25316 @CP-25316-05 @CP-25316-09 @CP-25316-14 @vidya @nj-regression @ui-wf-nj
  Scenario: Validate Create and Edit Task in NJ-SBE for Incomplete Contact Record task with all data
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "Incomplete contact Record" task page
    Then I verify create task fields displayed on screen
    And I will provide following information before creating task
      | taskInfo                 | Test CP-13257|
      | assignee                 | Service AccountOne|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I verify all columns values are displayed is valid
    When I expand the first row in task list
    And I verify Task Notes field is not displayed on Task List expanded view page
    Then I verify the task details are displayed in my task when expanded
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I verify Task Notes field is not displayed on Edit Task page
    And I will update the following information in edit task page
      | priority                 | 4            |
      | status                   | Escalated    |
      | assignee                 ||
      | taskInfo                 | Test CP-13257 Task Info|
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    Then I Verify Task Notes field is not displayed on View Task page
    And I verify the below task details are displayed in my task
    When I navigate to "Task Search" page
    And I will search with taskId
    Then I verify Task Notes field is not displayed in Search Results page
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And Close the soft assertions

  @CP-15914 @CP-15914-01 @basha @nj-regression @ui-wf-nj
  Scenario: Verify action taken drop down values are cleared after deselecting
    Given I logged into CRM with "Service Account 7" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Review Appeals Form" task page
    And I will provide following information before creating task
      | taskInfo          |Test CP-15914 |
      | assignee          |Service AccountSeven|
      | actionTaken       |No Action Taken,Outbound Call Successful,Outbound Call Unsuccessful|
    Then I verify +# link is displayed on screen
    And I mouse over to +# to verify all selected values are displayed
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I Verify "Review Appeals Form" task with "Created" status does have Initiate button
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                    |Escalated  |
      | deselectActionTaken       |No Action Taken,Outbound Call Successful,Outbound Call Unsuccessful|
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    Then I verify the below task details are displayed in my task
    When I navigate to "Task Search" page
    And I will search with taskId
    Then I verify tasks are does not have initiate button since searched parameter does not have permission to work
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And Close the soft assertions

  @CP-15913 @CP-15913-02 @vidya @nj-regression @ui-wf-nj
  Scenario: Verification of Review OAL Appeal Decision task type with Dispossion value in create, edit, view taask details page
    Given I logged into CRM with "Service Tester 4" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Review OAL Appeal Decision" task page
    And I will provide following information before creating task
      | taskInfo     | Test CP 15913|
      | assignee     | Service TesterFour|
      | actionTaken  |-- --|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I Verify "Review OAL Appeal Decision" task with "Created" status does have Initiate button
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         |Complete|
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify Action Taken drop down value
      | Final Agency Decision Letter Generated |
      | No Action Taken                  |
    And I will update the following information in edit task page
      | actionTaken    |Final Agency Decision Letter Generated|
      | reasonForEdit  |Corrected Data Entry|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And Close the soft assertions

  @CP-14664 @CP-14664-06 @CP-14664-01 @ui-wf-nj @nj-regression @ozgen
  Scenario: Display Get Insured Case and ConsumerId on My Task Page and Task Slider
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I click on initiate contact record
    When I searched customer have First Name as "Bruce" and Last Name as "Banner"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    When I navigate to "Returned Mail" task page
    And I will provide following information before creating task
      | taskInfo    | Task Info CP-14664-02-03|
      | assignee    | Service AccountOne      |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I verify all field are displayed on My Task or Work Queue page
    And I verify GetInsured case and consumer ids in "My Task"
    When If any In Progress task present then update that to Cancelled
    Then I click on initiate randomly
    Then Verify task slider is displayed
    And I verify the below details are displayed in task slider
    And I verify GetInsured case and consumer ids in "Task Slider"
    And I will update the following information in task slider
      | status       | Complete |
      | disposition  | Resolved |
    And I click on save in Task Slider
    And Close the soft assertions


  @CP-21297 @CP-21297-01 @kamil @nj-regression @ui-wf-nj
  Scenario: Validate Convert Tasks without Task Type ID to Incomplete Contact Record
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "","Incomplete contact Record","","","","2","","","","","","","","","","","","",""
    And I click on search button on task search page
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Cancelled |
    And I select value in "REASON FOR CANCEL" drop down
    And I click on save button on task edit page
    And Close the soft assertions