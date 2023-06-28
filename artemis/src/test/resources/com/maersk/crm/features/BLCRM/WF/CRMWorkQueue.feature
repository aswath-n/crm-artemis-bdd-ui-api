Feature: Validation of Work Queue Page

#  @CP-403 @CP-403-01 @CP-349 @CP-349-1 @vidya @crm-regression @ui-wf
  Scenario: Verify newly created task status is Created on Work Queue page
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "Work Queue" page
    And I will check whether it contains at least one "Inbound Task" task if not I will create task
    And I navigate to newly created task by clicking on TaskID column header
    Then The newly created task status should be Created
    And Close the soft assertions

 # @CP-403 @CP-403-02 @CP-735 @CP-735-03 @vidya @crm-regression @ui-wf
  Scenario: Verification of all columns values are displayed on Work Queue page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    And I will provide following information before creating task
      | taskInfo                 |Task Info CP-403|
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I verify all columns are displayed on Work Queue page
    Then I verify all columns values are displayed is valid
    And Close the soft assertions

#  @CP-403 @CP-403-04 @CP-735 @CP-735-04 @vidya @crm-regression @ui-wf
  Scenario: Verification of all header are displayed when I Expanded Task on Work Queue page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Work Queue" page
    And I will check whether it contains at least one "General" task if not I will create task
    Then I verify all headers are displayed when I expand the task
    And Close the soft assertions

  @CP-403 @CP-403-05 @CP-735 @CP-735-05 @vidya @crm-regression @ui-wf
  Scenario: Verification of all values are displayed when I Expanded Task on Work Queue page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "Luis" and Last Name as "Stanton"
    And I link the contact to an existing Case or Consumer Profile
#    When I Create the "General" Task on Create task page
    When I navigate to "General" task page
    And I will provide following information before creating task
      | status | Created |
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I verify linked Case ID is displayed
    Then I verify all columns are displayed on Work Queue page
    Then I verify all columns values are displayed is valid
    And I click on Expand Arrow link
    Then I verify all headers are displayed when I expand the task
    Then I verify all header values are displayed on newly created task and Consumer Id is displayed
    And Close the soft assertions

 # @CP-403 @CP-403-06 @CP-735 @CP-735-06 @vidya @crm-regression @ui-wf
  Scenario: Verification of Case ID is displayed when I link the Case
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "John" and Last Name as "Thomos"
    And I link the contact to an existing Case or Consumer Profile
    When I Create the "General" Task on Create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I verify linked Case ID is displayed
    And Close the soft assertions

 # @CP-403 @CP-403-07 @CP-735 @CP-735-07 @vidya @crm-regression @ui-wf
  Scenario: Verification 5 records are displayed per page and pagination
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Work Queue" page
    And I will check whether it contains at least six "General" task if not I will create task
    Then I verify pagination is available when more than five records are added
    And Close the soft assertions

 # @CP-403 @CP-403-09 @CP-735 @CP-735-10 @vidya @crm-regression @ui-wf
  Scenario: Verification of Sort Order of the Tasks
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Work Queue" page
 #   And I will check whether it contains at least one "General" task if not I will create task
    Then I verify sorting order of the tasks By Due Date
    And Close the soft assertions

  #Below scenarios for Validation of Work Queue Supervisor View
#  @CP-735 @CP-735-01 @CP-138 @CP-138-10 @CP-349 @CP-349-3 @vidya @crm-regression @ui-wf
  Scenario: Verify newly created task status is Created on Work Queue page for General Task
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Work Queue" page
    And I will check whether it contains at least one "General" task if not I will create task
    And I navigate to newly created task by clicking on TaskID column header
    Then I verify all task status is Created or Escalated
    And Close the soft assertions

  @CP-2978 @CP-2978-03 @CP-11837 @CP-11837-01 @CP-3313 @CP-3313-04 @CP-38837 @CP-38837-01 @vidya @ruslan @crm-regression @ui-wf
  Scenario: Verification of status drop down value when we are editing the task with Escalated status
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    And I will provide following information before creating task
      | status | Escalated |
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    Then I verify the status drop down value of task with "Escalated" status
      | Cancelled |
      | Complete  |
      | Escalated |
      | OnHold    |
    And Close the soft assertions

 #refactrorBy:Vidya Date:19-04-2020
  #Verify warning is Display when User attempts to return to Create Task screen with unsaved data entry
  @CP-670 @CP-670-06 @paramita @regression @crm @crm-regression @ui-wf
  Scenario: Verify warning message is Display when User attempts to Create Task with unsaved data entry in Work QUEUE screen
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Work Queue" page
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I update the task status in task slider as "OnHold"
    When I click to navigate "General" task page
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Task Details |
    When I click to navigate "General" task page
    And I click on continue button on warning message
    Then I should return back to Create Task screen
    And Close the soft assertions

  @CP-10852 @CP-10852-06 @ui-wf @crm-regression @vidya
  Scenario: Validate Disposition field value when select from edit page for BaseLine project
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General Two" task page
    And I will provide following information before creating task
      | taskInfo | Test CP-10852 |
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I ensure My task page has at least one task with type "General Two" and I navigate to view task
    Then verify disposition field is not displayed
    And I click on edit button on view task page
    And I changed status on edit page to "Complete"
    And I update the dispostion field in task slider as "User closed"
    And I select value in "REASON FOR EDIT" drop down
    And I click on save button on task edit page
    Then I verify view page has disposition value
    And Close the soft assertions

 # @CP-349 @CP-349-2 @crm-regression @ui-wf @kamil
  Scenario: Verify I Cannot View Task in Work Queue if NOT logged in under correct role
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "Work Queue" page
    And Verify I will NOT see General Tasks with non CSR Role
    And Close the soft assertions

  @CP-4407 @CP-4407-2 @crm-regression @ui-wf @vidya
  Scenario: Verification all fields and mandatory fields on create task page for SA8
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I navigate to "General" task page
    And I will provide following information before creating task
      | status   | Escalated                            |
      | taskInfo | Test Escalated Task is in work queue |
    And I click on save button in create task page
 #   Then I verify Success message is displayed for task
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I verify all field are displayed on My Task or Work Queue page
    Then I verify all columns values are displayed is valid
    When I expand the first row in task list
    Then I verify the task details are displayed in work queue when expanded
  #  Then I verify the task details are displayed in my task when expanded
    And I expend first Task from the list by clicking in Task ID
    Then I am Navigated to Task details page
    Then I verify the below task details are displayed in my task
    And I verify task id and edit task button are displayed
    Then I am able to navigate back to My Task or Work Queue page
    And Close the soft assertions

 # @CP-4407 @CP-4407-4 @crm-regression @ui-wf @vidya
  Scenario: Verification all fields and mandatory fields on create task page
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "Inbound Task" task page
    And I will provide following information before creating task
      | status      |Complete|
      | disposition |User closed|
      | taskInfo    |Test Completed Task is not in work queue|
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When I navigate to "Work Queue" page
    Then I verify Complete or Cancelled task is NOT in My Tasks queue
    And Close the soft assertions

  @CP-13625 @CP-13625-02 @crm-regression @ui-wf @vidya
  Scenario: Verification channel drop down value in edit task page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General/CRM/EB" task page
    And I will provide following information before creating task
      | taskInfo        |Test Channel drop down value in edit task page|
      | contactReason   |Information Request,Materials Request         |
      | preferredPhone  |1234567890                                    |
      | channel         |Mobile App                                    |
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    Then I am Navigated to Task details page
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    Then I verify channel drop down value
      |Email        |
      |Fax          |
      |IVR          |
      |Mail         |
      |Mobile App   |
      |Phone        |
      |SMS Text     |
      |System Integration|
      |Web          |
      |Web Chat     |
    And I will update the following information in edit task page
      | status              |Cancelled|
      |reasonForCancel      |Created Incorrectly|
    And I click on save button on task edit page
 #   Then I verify Success message is displayed for task update
    And I verify should I navigated to view task page
    And I verify the below task details are displayed in my task
    And Close the soft assertions

  @CP-16149 @CP-16149-03 @CP-16063 @CP-16063-04 @CP-16148 @CP-16148-01 @vidya @crm-regression @ui-wf
  Scenario: Validate action taken field in create, view and Edit task details page navigating from work queue
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Follow-up on Appeal" task page
    And I will provide following information before creating task
      | status | Escalated |
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will check action taken is not mandatory when task status is not complete
    And I changed status on edit page to "Complete"
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify "actionTaken" multi select drop down value
      | IDR Resolved Letter Generated    |
      | IDR Unresolved Letter Generated  |
      | No Action Taken                  |
    And I verify "disposition" single select drop down value
      | IDR Successful   |
      | IDR Unsuccessful |
    And I will update the following information in edit task page
      | status         |Complete|
      | priority       |2|
      | taskInfo       |Test edit action taken|
      | disposition    |IDR Unsuccessful|
      | actionTaken    |IDR Resolved Letter Generated,IDR Unresolved Letter Generated,No Action Taken|
      | reasonForEdit  |Corrected Data Entry|
    Then I verify +# link is displayed on screen
    And I mouse over to +# to verify all selected values are displayed
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    Then I verify the below task details are displayed in my task
    And Close the soft assertions





