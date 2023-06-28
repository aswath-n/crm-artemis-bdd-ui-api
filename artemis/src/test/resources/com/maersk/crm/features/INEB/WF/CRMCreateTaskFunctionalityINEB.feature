@CreateTask
Feature: Create Task Functionality Check

  @CP-5190 @CP-5190-04 @CP-13829 @CP-13829-04 @crm-regression @ui-wf-ineb @vidya
  Scenario: Calculate Due Date for Calendar day and  Business Days
    When I will get the Authentication token for "IN-EB" in "Tenant Manager"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Just Cause" service request page
    Then I verify due date field in create task page for 30 "Business Day"
    And I select "HCC Outbound Call SR" option in task type drop down
    Then I verify due date field in create task page for 60 "Calendar Day"
    And Close the soft assertions

  @CP-30407 @CP-30407-01 @CP-30301 @CP-30301-06 @CP-30081 @CP-30081-03 @crm-regression @ui-wf-ineb @kamil @ruslan
  Scenario: Verification all fields match requirements on Create Task
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Member Address Change Request" task page
    And I verify "addressType" single select drop down value
      | Mailing  |
      | Physical |
    And I verify "addressSource" single select drop down value
      | Consumer Reported  |
      | Forwarding Address |
      | Plan Reported      |
    Then I verify text box Date and Time field value and error message for following fields
      | AddressLine1 |
      | AddressLine2 |
      | City         |
      | State        |
    And I will provide following information before creating task
      | addressType    | Mailing           |
      | addressSource  | Consumer Reported |
      | addressZipCode | 80145             |
      | AddressLine1   | 1 N Madison       |
      | AddressLine2   | 23 S State        |
      | City           | Denver            |
      | State          | Colorado          |
      | taskInfo       | Test CP-30407     |
      | status         | Created           |
      | assignee       | Service TesterTwo |
    And I click on save button in create task page
    When I will get the Authentication token for "IN-EB" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I will get "Created" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    When I navigate to "Task Search" page
    Then I will search for newly created task on Task Search
    And I perform a Task Search by following fields
      | assignmentStatus | Unassigned |
    And I click on search button on task search page
    Then I verify request payload "assignedFlag" contains assignedFlag "false" and serviceRequestInd "false"
    Then I verify No Records Available message is displayed
    And I perform a Task Search by following fields
      | assignmentStatus | Assigned |
    And I click on search button on task search page
    And I initiated search records API
    Then I verify staffAssignedTo field returns "staffAssignedTo"
    Then I verify request payload "assignedFlag" contains assignedFlag "true" and serviceRequestInd "false"
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And Close the soft assertions

  @CP-30293 @CP-30293-04 @crm-regression @ui-wf-ineb @priyal
  Scenario: Verify only Active SR in create SR page and navigation bar for IN-EB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with specific role "Service Tester 5" and select a project "IN-EB" and select a role "Supervisor"
    And I will get the all active service request which has "Supervisor" permission to create and edit for project "IN-EB"
    Then I verify only active service request is displayed in create SR link
    And I verify only active service request is displayed in service request drop down in create SR page
    Then I logOut while Working a Task
    Given I logged into CRM after logged out with "Service Tester 5" and select a project "IN-EB" and select a role "Superuser"
    And I will get the all active service request which has "Superuser" permission to create and edit for project "IN-EB"
    Then I verify only active service request is displayed in create SR link
    And I verify only active service request is displayed in service request drop down in create SR page
    And Close the soft assertions