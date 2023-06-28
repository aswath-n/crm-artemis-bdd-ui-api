Feature: Validation of Claim Next/Initiate Functionality

  @CP-4345 @CP-4345-12 @crm-regression @ui-wf @Basha
  Scenario: verify initiate button is not displayed in Task service request tab when status is Complete
    Given I logged into CRM with "Service Tester 5" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I click on initiate a contact button
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I minimize Genesys popup if populates
    When I navigate to "Inbound Task" task page
    And I will provide following information before creating task
      | taskInfo                 |Test CP 4345|
    And I click on save button in create task page
    #Then I verify Success message is displayed for task
    When If any In Progress task present then update that to Cancelled
    And I navigate to newly created task in Task & Service Request Tab
    And I click on Initiate button
    And Verify task slider is displayed
    And I will update the following information in task slider
      |status           |Complete|
      | disposition     |User closed|
    And I click on save in Task Slider
    #Then I verify Success message is displayed for task update
    And I navigate to newly created task in Task & Service Request Tab
    Then I Verify "Inbound Task" task with "Complete" status does not have Initiate button Task SR tab
    And Close the soft assertions

  @CP-4345 @CP-4345-13 @CP-18090 @CP-18090-04 @crm-regression @ui-wf @vidya @Basha
  Scenario: verify initiate button is not displayed in Task service request tab when status is Cancelled
    Given I logged into CRM with "Service Tester 5" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I click on initiate a contact button
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I minimize Genesys popup if populates
    When I navigate to "Follow-up on Appeal" task page
    And I will provide following information before creating task
      | taskInfo                 |Test CP 4345|
    And I click on save button in create task page
    #Then I verify Success message is displayed for task
    And I navigate to newly created task in Task & Service Request Tab
    When If any In Progress task present then update that to Cancelled
    And I click on Initiate button
    And Verify task slider is displayed
    And I will update the following information in task slider
      |status           |Cancel|
      |reasonForCancel  |Duplicate Task|
    And I click on save in Task Slider
    #Then I verify Success message is displayed for task update
    And I navigate to newly created task in Task & Service Request Tab
    Then I Verify "Follow-up on Appeal" task with "Cancelled" status does not have Initiate button Task SR tab
    And I expend first Task from the list by clicking in Task ID
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-403 @CP-403-08 @CP-350 @CP-350-01 @CP-10931 @CP-10931-07 @CP-138 @CP-138-11 @CP-4345 @CP-4345-03 @CP-18090 @CP-18090-03 @CP-38837 @CP-38837-02 @vidya @ruslan @crm-regression @crm-smoke @ui-wf
  Scenario: Verification of Claim Next functionality
    Given I logged into CRM with "Service Tester 5" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Work Queue" page
    And I minimize Genesys popup if populates
    Then I verify claim next button is present on screen
    When I click on "TaskId" column on My Tasks Page
    #Then I verify task records are displayed in ascending order of their "TaskId"
    And I will check whether it contains at least one "Inbound Task" task if not I will create task
    And I click on Claim Next button
    Then Verify task slider is displayed
    And I update the task status in task slider as "Cancel"
    And I will select reason for cancel drop down value as "Duplicate Task"
    And I click on save in Task Slider
    Then I verify user is redirected to work queue page
    And Close the soft assertions