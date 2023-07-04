@CreateTask
Feature: Create Task Functionality Check

  @CP-10691 @CP-10691-03 @CP-32925 @CP-32925-02 @vidya @nj-regression @ui-wf-nj
  Scenario: Verification of all dynamic fields in Outbound Call task type
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    When I navigate to "Outbound Call" task page
    And I minimize Genesys popup if populates
    Then I verify Contact Reason drop down value
      | Announcements        |
      | Change of Address |
      | Complaint          |
      | Courtesy Call Back|
      |  DMI               |
      |Escalation          |
      |Inbound Document Inquiry|
      |Other                   |
      |Returned Mail           |
    Then I verify text box Date and Time field value and error message for following fields
      |PREFERRED CALL BACK DATE|
      |PREFERRED CALL BACK TIME|
      |PREFERRED PHONE         |
      |NAME                    |
    And Close the soft assertions

  @CP-10691 @CP-10691-04 @vidya @nj-regression @ui-wf-nj
  Scenario: Verification of mandatory field level error message
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    When I navigate to "Outbound Call" task page
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "Name"
    And I verify task mandatory fields error message "PREFERRED PHONE "
    And Close the soft assertions

  @CP-10691 @CP-10691-05 @CP-10685 @CP-10685-05 @vidya @nj-regression @ui-wf-nj
  Scenario: Verification of name auto complete drop down field if we link to any case/consumer
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I click on initiate a contact button
    And I minimize Genesys popup if populates
    When I searched customer have First Name as "John" and Last Name as "Smith"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    And I navigate to "Outbound Call" task page
    Then I verify Name drop down fields value
      | John Smith |
    And Close the soft assertions

  @CP-10685 @CP-10685-03 @CP-17645 @CP-17645-01 @vidya @nj-regression @ui-wf-nj
  Scenario: Verification of all dynamic fields in Review Complaint task type
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    When I navigate to "Review Complaint" task page
    Then I verify Complaint About drop down value
      | CAC (maersk) |
      | Carrier       |
      | Exchange      |
      | FFM           |
      | Medicaid      |
      | Other         |
    And I verify Reason drop down value
      | Customer Service         |
      | Customer Service - Agent |
      | Member Portal            |
      | Other                    |
      | Timeliness               |
    And Close the soft assertions

  @CP-10685 @CP-10685-04 @vidya @nj-regression @ui-wf-nj
  Scenario: Verification of mandatory field level error message for Review Complaint task type
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    When I navigate to "Review Complaint" task page
    And I click on save button in create task page
    Then I verify task mandatory fields error message "Complaint About"
    And I verify task mandatory fields error message "Name"
    And I verify task mandatory fields error message "Reason"
    And Close the soft assertions

  @CP-17645 @CP-17645-02 @CP-20286 @CP-20286-01 @vidya @nj-regression @ui-wf-nj
  Scenario: Verification Reason Drop-down value for GCNJ Escalation task page
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    When I navigate to "GCNJ Escalation" task page
    Then I verify "reason" single select drop down value
      | DOBI Escalation  |
      | Policy Question  |
      | Technical Defect |
    And I verify "actionTaken" multi select drop down value
      | Did not reach consumer/left message|
      | Outreach Consumer                  |
    And Close the soft assertions

  @CP-22951 @CP-22951-01 @CP-25982 @CP-25982-05 @vidya @ruslan @nj-regression @ui-wf-nj
  Scenario: Verification Reason Drop-down value for Enrollment Escalation task page
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "Enrollment Escalation" task page
    Then I verify "reason" single select drop down value
      | Change Coverage End Date   |
      | Change Coverage Start Date |
      | Enrollment Cancellation    |
      | Medicaid Denial            |
      | Updated Application        |
    And I will provide following information before creating task
      | reason   | Medicaid Denial    |
      | assignee | Service AccountOne |
    And I click on save button on task edit page
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | Cancelled           |
      | reasonForCancel | Created Incorrectly |
    And I click on save button on task edit page
    And Close the soft assertions

  @CP-22952 @CP-22952-01 @CP-37688 @CP-37688-04 @vidya @nj-regression @ui-wf-nj
  Scenario: Verification Reason Drop-down value for Subsidy Escalation task page
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "Subsidy Escalation" task page
    Then I verify "reason" single select drop down value
      | Medicaid Denial|
      | Updated Application|
    And I verify "ASSIGNEE BUSINESS UNIT" filed is not mandatory for the task
    And I select "Subsidy Escalation" option in task type drop down
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And Get the task type information of "Subsidy Escalation" for project "NJ-SBE"
    Given I initiated active business units and teams api
    When I provide taskTypeId for active business units and teams "singleVal"
    And I can run active bu and teams api
    Then I get business unit names from business units and teams response api
    Then I verify Assignee Business Unit dropdown displays only BU that are associated to the task type
    And Close the soft assertions

  @CP-16249 @CP-16249-01 @CP-16249-01.1 @CP-16249-02 @CP-16249-03 @CP-30301 @CP-30301-08 @CP-30081 @CP-30081-05 @nj-regression @ui-wf-nj @kamil @ruslan
  Scenario:  Validate Task Type is Available from Ellipsis Menu and View Task UI/Edit Task UI
    Given I logged into CRM with "Service Tester 4" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Process Appeals Supporting Documentation" task page
    Then Verify Create Task page for Process Appeals Supporting Documentation visible
    Then I verify dynamic fields are dispayed
      | EXTERNAL CASE ID |
      | ASSIGNEE         |
      | TASK TYPE        |
    And I will provide following information before creating task
      | assignee       ||
      | taskInfo       ||
      | status         | Complete         |
      | disposition    | Resolved         |
      | externalCaseId | External Case ID |
    And I click on save button in create task page
    #Then I verify Success message is displayed for task
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    And I will get "Complete" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    When I navigate to "Task Search" page
    Then I will search for newly created task on Task Search
    And I perform a Task Search by following fields
      | assignmentStatus | Assigned |
    And I click on search button on task search page
    Then I verify request payload "assignedFlag" contains assignedFlag "true" and serviceRequestInd "false"
    Then I verify No Records Available message is displayed
    And I perform a Task Search by following fields
      | assignmentStatus | Unassigned |
    And I click on search button on task search page
    And I initiated search records API
    Then I verify staffAssignedTo field returns "null"
    Then I verify request payload "assignedFlag" contains assignedFlag "false" and serviceRequestInd "false"
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And Verify required fields are available on Edit Task
    And Close the soft assertions

  @CP-14664 @CP-14664-05 @ui-wf-nj @nj-regression @ozgen
  Scenario: Display Get Insured Case and ConsumerId on Create Task Page
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "christina" and Last Name as "lake"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    Then I save GetInsured caseid and consumerId from Linked Consumer Details
    When I navigate to "Subsidy Escalation" task page
    And I verify GetInsured case and consumer ids in "Create Task"
    And Close the soft assertions

  @CP-5190 @CP-5190-02 @CP-13829 @CP-13829-02 @ui-wf-nj @nj-regression @vidya
  Scenario: Calculate Due Date for Calendar day and  Business Days
    When I will get the Authentication token for "BLCRM" in "Tenant Manager"
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "Outbound Call" task page
    Then I verify due date field in create task page for 3 "Business Day"
    And I select "Review OAL Appeal Decision" option in task type drop down
    Then I verify due date field in create task page for 10 "Calendar Day"
    And Close the soft assertions