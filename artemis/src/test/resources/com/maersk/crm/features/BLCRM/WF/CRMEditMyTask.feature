@editMyTask
Feature: Edit My Task

  @aswath @taskComplete
  Scenario Outline: updateTaskStatus2Complete
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "My Task" page
    And I will ensure there is no task in progress status
    When I navigate to "<Page>" page
    And I click task id to get the results in descending order
    And I verify the my task pagination and update status to complete
    Examples:
      |Page|
      |Work Queue|
      |My Task|

  @CP-1230 @CP-1230-01 @vidya @crm-regression @ui-wf
  Scenario Outline: Verification of On-Hold status functionality in Edit task page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "My Task" page
    And I ensure My task page has at least one task with status other than "<status>" and I navigate to view task
    And I click on edit button on view task page
    And I changed status on edit page to "<status1>"
    Then I verify "<field>" drop down is displayed as mandatory field
    And I verify "<field>" dropdown has values
    And I select value in "<field>" drop down
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    Then I verify status is changed as "<status1>" and status date is updated
    And Close the soft assertions
    Examples:
      | status    | status1   | field             |
      | Onhold    | OnHold    | REASON FOR HOLD   |

 @CP-1250 @CP-1250-01 @CP-968 @CP-968-01 @CP-22077 @CP-22077-01 @vidya @crm-regression @ui-wf
  Scenario Outline: Verification of Cancelled status functionality in Edit task page
   Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
   When I navigate to "My Task" page
   And I ensure My task page has at least one task with status other than "<status>" and I navigate to view task
   And I click on edit button on view task page
   And I changed status on edit page to "<status1>"
   Then I verify "<field>" drop down is displayed as mandatory field
   And I verify "<field>" dropdown has values
   And I select value in "<field>" drop down
   And I click on save button on task edit page
#   Then I verify Success message is displayed for task update
   Then I verify should I navigated to view task page
   Then I verify status is changed as "<status1>" and status date is updated
   Then I verify Due In field become blank
   And I click on edit button on view task page
   And I verify "reasonForEdit" multi select drop down value
     |Corrected Case/Consumer Link |
     |Corrected Data Entry|
     |Corrected Error(s)|
     |Entered Additional Info|
   And I will update the following information in edit task page
     | reasonForEdit           |Corrected Data Entry|
   And I click on save button on task edit page
   Then I am able to navigate back to My Task or Work Queue page
   And Close the soft assertions
    Examples:
      | status    | status1   | field             |
      | Cancelled | Cancelled | REASON FOR CANCEL |

  @CP-2978 @CP-2978-01 @CP-968 @CP-968-02 @CP-3313 @CP-3313-01 @vidya @crm-regression @ui-wf
  Scenario Outline: Verification of status drop down value when we are editing the task with Create status
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click taskId column in the task search result
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    Then I verify the status drop down value of task with "Created" status
      | Cancelled |
      | Complete  |
      | Created   |
      | Escalated |
      | OnHold    |
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || General  || Created ||          ||            ||                || true          ||            ||          || today     ||

  @CP-2978 @CP-2978-02 @CP-968 @CP-968-03 @CP-3313 @CP-3313-02 @CP-28998 @CP-28998-02 @vidya  @crm-regression @ui-wf
  Scenario Outline: Verification of status drop down value when we are editing the task with Open status
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    Then I verify the status drop down value of task with "Open" status
      | Cancelled |
      | Complete  |
      | Escalated |
      | OnHold    |
      | Open      |
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || General  || Open   ||          ||            ||                ||               ||            ||          ||           ||

  @CP-2978 @CP-2978-04 @CP-968 @CP-968-04 @CP-3313 @CP-3313-03 @vidya  @crm-regression @ui-wf
  Scenario Outline: Verification of status drop down value when we are editing the task with Onhold status
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    Then I verify the status drop down value of task with "Onhold" status
      | Cancelled |
      | Complete  |
      | Escalated |
      | OnHold    |
    And Close the soft assertions
    Examples:
      | taskId | taskType      |srType| status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || Plan/Provider || OnHold ||          ||            ||                ||               ||            ||          ||           ||

  #failed - Defect raised : CP-9561
  @CP-670 @CP-670-07 @CP-37692 @CP-37692-03 @kamil @paramita @regression @crm @crm-regression @ui-wf
  Scenario: Verify warning message is Display when User attempts to Create Task with unsaved data entry in MY TASK screen
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    And I will provide following information before creating task
      | assignee                 |Service AccountOne|
      | taskInfo                 |Task Info|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    And I click on edit button on view task page
    And I update the task status in task slider as "OnHold"
    When I click to navigate "General" task page
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Task Details |
    When I click to navigate "General" task page
    Then I verify warning popup is displayed with message
    And I click on continue button on warning message
    Then I should return back to Create Task screen
    And Close the soft assertions

  @CP-10852 @CP-10852-07 @ui-wf-nj @nj-regression @vidya
  Scenario Outline: Validate Disposition field is not displayed when task status is other then complete for NJ-SBE project
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then verify disposition field is not displayed
    And I click on edit button on view task page
    And I changed status on edit page to "Escalated"
    And I click on save button on task edit page
    Then verify disposition field is not displayed
    And I click on edit button on view task page
    And I changed status on edit page to "OnHold"
    And I select value in "REASON FOR HOLD" drop down
    And I click on save button on task edit page
    Then verify disposition field is not displayed
    And I changed status on edit page to "Cancelled"
    And I select value in "REASON FOR CANCEL" drop down
    And I click on save button on task edit page
    Then verify disposition field is not displayed
    And Close the soft assertions
    Examples:
      | taskId | taskType                 | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      || Process Inbound Document || Created ||          ||            ||                || false         ||            ||          ||           ||

  @CP-10852 @CP-10852-08 @ui-wf @crm-regression @vidya
  Scenario: Validate Disposition field is not displayed when task status is other then complete for BLCRM project
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General Two" task page
    And I will provide following information before creating task
      | taskInfo | Test CP-10852      |
      | assignee | Service AccountOne |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    Then verify disposition field is not displayed
    And I changed status on edit page to "Escalated"
    And I click on save button on task edit page
    Then verify disposition field is not displayed
    And I click on edit button on view task page
    And I changed status on edit page to "OnHold"
    And I select value in "REASON FOR HOLD" drop down
    And I click on save button on task edit page
    Then verify disposition field is not displayed
    And I click on edit button on view task page
    And I changed status on edit page to "Cancelled"
    And I select value in "REASON FOR CANCEL" drop down
    And I click on save button on task edit page
    Then verify disposition field is not displayed
    And Close the soft assertions

  @CP-10687 @CP-10687-02 @CP-13908 @CP-13908-04 @CP-22077 @CP-22077-02 @CP-15323 @CP-15323-06 @CP-13177 @CP-13177-01 @ui-wf-nj @nj-regression @vidya
  Scenario: Verify mandatory and optional filelds in Edit task page for Inbound Application Data Entry task type
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Inbound Application Data Entry" task page
    And I will provide following information before creating task
      | taskInfo              | Test CP-10686      |
      | assignee              | Service AccountTwo |
      | externalApplicationId | Test 123           |
      | externalCaseId        | caseId 123         |
    And I click on save button on task edit page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority              |        |
      | status                | OnHold |
      | assignee              |        |
      | taskInfo              | ABC    |
      | externalApplicationId |        |
      | externalCaseId        |        |
      | reasonForOnHold       |        |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE"
    And I verify task mandatory fields error message "REASON FOR HOLD"
    And I verify task mandatory fields error message "PRIORITY"
    And I verify minimum lenght error message "TASK INFORMATION"
    And I will update the following information in edit task page
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify "disposition" single select drop down value
      | Completed     |
      | Not Completed |
    And I verify "reasonForEdit" multi select drop down value
      | Corrected Case/Consumer Link |
      | Corrected Data Entry         |
      | Corrected Error(s)           |
      | Entered Additional Info      |
    And I will update the following information in edit task page
      | status        | Complete                                   |
      | assignee      | Service AccountTwo                         |
      | priority      | 2                                          |
      | taskInfo      | Test-CP-10687 $ allow Special Character &% |
      | disposition   | Not Completed                              |
      | reasonForEdit | Corrected Data Entry                       |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-10687 @CP-10687-03 @CP-39600 @CP-39600-02 @ui-wf-nj @nj-regression @vidya
  Scenario: Verify field length in Edit task page for Inbound Application Data Entry task type
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
 #   When I navigate to "My Task" page
 #   And I make sure assignee contains at least one "Inbound Application Data Entry" task if not I will create task
 #   And I click task id to get the results in descending order
 #   And I ensure My task page has at least one task with type "Inbound Application Data Entry" and I navigate to view task
    When I navigate to "Inbound Application Data Entry" task page
    And I will provide following information before creating task
      | assignee  | Service AccountTwo |
      | taskInfo  | Task Info |
    And I click on save button in create task page
    When I navigate to "My Task" page
 #   And I navigate to newly created task by clicking on TaskID column header
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    And Wait for 3 seconds
  #  And I will click on newly created task id
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority                 |4|
      | status                   |OnHold|
      | reasonForOnHold          |Missing Information|
      | assignee                 |Service AccountTwo|
      | taskInfo                 |maxlength|
      | externalApplicationId    |random|
      | externalCaseId           |random|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-10822 @CP-10822-02 @CP-13908 @CP-13908-05 @ui-wf-nj @nj-regression @vidya
  Scenario: Verify mandatory and optional filelds in Edit task page for Returned Mail task type
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Returned Mail" task page
    And I will provide following information before creating task
      | taskInfo                 |Test CP-10823|
      | assignee                 |Service AccountTwo|
      | externalCaseId           |CaseId123|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | OnHold |
      | assignee        |        |
      | taskInfo        | ABC    |
      | externalCaseId  |        |
      | reasonForOnHold |        |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE"
    And I verify task mandatory fields error message "REASON FOR HOLD"
    And I verify task mandatory fields error message "PRIORITY"
    And I verify minimum lenght error message "TASK INFORMATION"
    And I will update the following information in edit task page
      | status         |Complete|
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify "disposition" single select drop down value
      | Resolved   |
      | Unresolved |
    And I will update the following information in edit task page
      | status        | Complete             |
      | assignee      | Service AccountTwo   |
      | taskInfo      ||
      | disposition   | Resolved             |
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions