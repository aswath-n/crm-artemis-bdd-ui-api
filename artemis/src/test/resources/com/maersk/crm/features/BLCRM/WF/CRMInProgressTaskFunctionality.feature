@inprogressTask
Feature: Validate In-Progress Task Functionality

  @CP-13189 @CP-13189-01 @CP-11517 @CP-11517-01 @crm-regression @ui-wf @vidya
  Scenario: Make one task as in-progress task in SA2
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    And I click on initiate a contact button
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I searched customer have First Name as "FnZkiUL" and Last Name as "LnkjUlW"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Inbound Task" task page
    And I will provide following information before creating task
      | taskInfo                 | Test CP11517|
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    And I navigate to newly created task in Task & Service Request Tab
    And I click on initiate randomly
    Then Verify task slider is displayed
    And Close the soft assertions


#  @CP-13189 @CP-13189-02 @CP-11517 @CP-11517-02 @crm-regression @ui-wf @vidya
  Scenario: Verify if task is in in-progress status and not assign to me are not editable from my account
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "FnZkiUL" and Last Name as "LnkjUlW"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created task in Task & Service Request Tab
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    Then I verify error message is displayed for task in progress and not assign to me
    And I click on Active Contact widget
    And I click on task id in link component
    And I click on edit button on view task page
    Then I verify error message is displayed for task in progress and not assign to me
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    Then I verify error message is displayed for task in progress and not assign to me
    And Close the soft assertions

#  @CP-13189 @CP-13189-03 @CP-11517 @CP-11517-03 @crm-regression @ui-wf @vidya
  Scenario: Verify there is no in-progress task in SA2
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "My Task" page
    And I will ensure there is no task in progress status
    And Close the soft assertions

  @CP-11517 @CP-11517-04 @crm-regression @ui-wf @Basha
  Scenario Outline:Verify in view task page extra field is not displayed even if we pass that in request body
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "Correspondence" for project ""
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      |External Case ID|
      |Test 1234       |
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    Then Wait for 3 seconds
    Then Verify task slider is displayed
    And Close the soft assertions
    Examples:
      |projectName|caseId|consumerId|inboundId|taskInfo|triggerDate|
      ||      ||17486    |CP 13133|currentDate|

  @CP-13249 @CP-13249-01 @crm-regression @ui-wf @Basha
  Scenario: Edit Tasks In Progress & Assigned to Me
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to "General" task page
    And I will provide following information before creating task
      | taskInfo                 |Task Info 123|
      | assignee                 |Service AccountOne|
    And I click on save button in create task page
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    And I click on the priority in dashboard
    And I click on edit button on view task page
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "FnJCwLk" and Last Name as "LnkFNVK"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    Then I see radio button is associated with a Case or Consumer
    When I select Consumer radio button
    Then I see Link Record Consumer button get displayed
    When I click on Link Record Consumer button
    Then I verify task is linked with ConsumerID and CaseID
    And I will update the following information in edit task page
      | status   | Escalated       |
      | priority | 3               |
      | taskInfo | Task Info 13249 |
      | assignee ||
    And I click on save button on task edit page
#    Then I verify task updated Successfully
    And I verify should I navigated to view task page
    Then I verify task is linked with ConsumerID and CaseID
    And I verify the updated information in view task details page
    And I click on the priority in dashboard
    Then I verify the task information in task slider page
    And Close the soft assertions

#  @CP-13249 @CP-13249-02 @CP-18683 @CP-18683-01 @crm-regression @ui-wf @vidya @Basha
  Scenario: Display Error Message
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I minimize Genesys popup if populates
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I click on edit button on view task page
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I click on save in Task Slider
    Then I will verify an Error Message You must save your changes to the Task before saving on the Task Slider
    And I click on the priority in dashboard
    And I will update the following information in edit task page
      | status         |Complete|
      | priority       |1|
      | taskInfo       |Task Info 13249|
      | assignee       ||
      | reasonForEdit  |Corrected Data Entry|
      | disposition    | User closed        |
    And I click on save button on task edit page
#    Then I verify task updated Successfully
    Then I verify task slider is closed
    Then I verify navigate back to My Task or Work Queue page
    Then I verify Complete or Cancelled task is NOT in My Tasks queue
    And Close the soft assertions

  @CP-13249 @CP-13249-03 @CP-13249-04 @crm-regression @ui-wf @Basha
  Scenario: Edits Reflected in Task Slider and view task details
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I minimize Genesys popup if populates
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | OnHold               |
      | priority        | 3                    |
      | taskInfo        | Task Info 13249      |
      | assignee        | Service AccountEight |
      | reasonForEdit   | Corrected Data Entry |
      | reasonForOnHold | Application On Hold  |
    And I click on save button on task edit page
#    Then I verify task updated Successfully
    And I click on the priority in dashboard
    Then I verify the task information in task slider page
    And Close the soft assertions

  @CP-13249 @CP-13249-05 @crm-regression @ui-wf @Basha
  Scenario: Cancel the changes verify edits not Reflected in View Task Slider
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I minimize Genesys popup if populates
    When I navigate to "My Task" page
    And I create "General" task with priority "" assignee as "Service AccountEight" and task info as "Task Info" and status as ""
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I click on edit button on view task page
    And I changed status on edit page to "Cancelled"
    And I click on cancel button on create task type screen
    And I click on continue button on create task warning message
    And I click on the priority in dashboard
    And I verify the task status in task slider is in progress
    And I click on save in Task Slider
    Then I verify navigate back to My Task or Work Queue page
    And Close the soft assertions

  @CP-2978 @CP-2978-05 @CP-3313 @CP-3313-05 @CP-18683 @CP-18683-02 @vidya  @crm-regression @ui-wf
  Scenario: Verification of status drop down value when we are editing the task with In-Progress status
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "My Task" page
    And I will ensure assignee contains at least one "General" task if not I will create task
    And I will get the index of "General" task and click on initiate button for that
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I click on edit button on view task page for In-progress status
    Then I verify the status drop down value of task with "In-Progress" status
      | Cancelled   |
      | Complete    |
      | Escalated   |
      | In-Progress |
      | OnHold      |
    And I will update the following information in edit task page
      | status              |Cancelled|
      | reasonForCancel     |Created Incorrectly|
    And I click on save button on task edit page
    Then I verify task slider is closed
    And I verify navigate back to My Task or Work Queue page
    And I verify Complete or Cancelled task is NOT in My Tasks queue
    And Close the soft assertions
