Feature: Initiate Functionality oF My Task Third


  @CP-18060 @CP-18060-01 @crm-regression @ui-wf @crm-regression @ruslan
  Scenario: Verify Associated Screen - View Consumer Profile - Linked to Consumer
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When I navigate to "Plan/Provider" task page
    And I will provide following information before creating task
      | assignee | Service AccountEight |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "FnEIXno" and Last Name as "LnLaKwh"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    Then I see radio button is associated with a Case or Consumer
    When I select Consumer radio button
    Then I see Link Record Consumer button get displayed
    When I click on Link Record Consumer button
    And I click on save button in create task page
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    Then I am navigated to the View Consumer Profile UI and it is populated with the linked Consumer ID
    And I will update the following information in task slider
      | status | Complete |
    And I click on save in Task Slider
    And Close the soft assertions

  @CP-18060 @CP-18060-02 @crm-regression @ui-wf @crm-regression @ruslan
  Scenario: Verify Associated Screen - View Consumer Profile - Not Linked to Consumer
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When I navigate to "Plan/Provider" task page
    And I will provide following information before creating task
      | assignee | Service AccountEight |
    And I click on save button in create task page
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    Then I verify the field values on View Task details
    And I will update the following information in task slider
      | status | Complete |
    And I click on save in Task Slider
    And Close the soft assertions

  @CP-16059 @CP-16059-03 @crm-regression @ui-wf @crm-regression @ruslan
  Scenario: Validate navigation Option to Task Slider to Allow a User to Navigate directly to View Task Details in BLCRM
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When I navigate to "Plan/Provider" task page
    And I will provide following information before creating task
      | assignee | Service AccountEight |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "FnEIXno" and Last Name as "LnLaKwh"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    Then I see radio button is associated with a Case or Consumer
    When I select Consumer radio button
    Then I see Link Record Consumer button get displayed
    When I click on Link Record Consumer button
    And I click on save button in create task page
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    And I click on task id on the task slider
    Then I verify should I navigated to view task page
    And I Verify Task slider is collasped
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I will update the following information in task slider
      | status          | Cancel         |
      | reasonForCancel | Duplicate Task |
    And I click on save in Task Slider
    And Close the soft assertions


  @CP-24420 @CP-24420-01 @crm-regression @ui-wf @ruslan
  Scenario Outline: Validate error message displayed if task is not link to Correspondence
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When I navigate to "<taskType>" task page
    When I update status to "Complete" on task
    Then I validate the system displays an Error Message: "<errorMessage>"
    And I will provide following information before creating task
      | assignee | Service AccountEight |
    Then I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    When I update status to "Complete" on task
    Then I validate the system displays an Error Message: "<errorMessage>"
    And I click on save button on task edit page
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status      | Complete|
      | disposition | Resolved|
    And I click on save in Task Slider
    Then I validate the system displays an Error Message: "<errorMessage>"
    And Close the soft assertions
    Examples:
      | taskType       | errorMessage                                                              |
      | Correspondence | A link to a Correspondence is required before this task can be completed. |


  @CP-24420 @CP-24420-02 @crm-regression @ui-wf @ruslan
  Scenario Outline: Validate user is able to complete task from edit page if Correspondence is linked to the task
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Other"
    And Get the task type information of "Correspondence" for project ""
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      |  |
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | User closed          |
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And Close the soft assertions
    Examples:
      | projectName | caseId | consumerId | inboundId   | taskInfo      | triggerDate |
      |             | 9190   | 21204      | takeFromAPI | link to cores | currentDate |


  @CP-24420 @CP-24420-03 @crm-regression @ui-wf @ruslan
  Scenario Outline: Validate user is able to complete task on the task slider if Correspondence is linked to the task
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Other"
    And Get the task type information of "Correspondence" for project ""
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      |  |
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify save task search section is displayed
    And I click on initiate button in task search page
    Then Verify task slider is displayed
    And I will update the following information in task slider
      | status      | Complete    |
      | disposition | User closed |
    And I click on save in Task Slider
    Then I verify user is navigate back to task search page
    And Close the soft assertions
    Examples:
      | projectName | caseId | consumerId | inboundId   | taskInfo      | triggerDate |
      |             | 9190   | 21204      | takeFromAPI | link to cores | currentDate |