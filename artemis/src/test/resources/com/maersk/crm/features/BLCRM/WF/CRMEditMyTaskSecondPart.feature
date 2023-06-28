@editMyTask
Feature: Edit My Task Second


  @CP-10822 @CP-10822-03 @ui-wf-nj @nj-regression @vidya
  Scenario: Verify field length in Edit task page for Returned Mail task type
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "My Task" page
    And I make sure assignee contains at least one "Returned Mail" task if not I will create task
    And I click task id to get the results in descending order
    And I ensure My task page has at least one task with type "Returned Mail" and I navigate to view task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority        | 1                   |
      | status          | OnHold              |
      | reasonForOnHold | Missing Information |
      | assignee        | Service AccountTwo  |
      | taskInfo        | maxlength           |
      | externalCaseId  | random              |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-11414 @CP-11414-02 @CP-13908 @CP-13908-06 @ui-wf-nj @nj-regression @vidya
  Scenario Outline: Verify mandatory and optional filelds in Edit task page for Verification Document Upload task type
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority        |        |
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
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify "disposition" single select drop down value
      | Uploaded         |
      | Unable to Upload |
    And I will update the following information in edit task page
      | status        | Complete             |
      | assignee      | Service AccountTwo   |
      | priority      | 2                    |
      | taskInfo      |                      |
      | disposition   | Unable to Upload     |
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions
    Examples:
      | taskId | taskType                     | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      |        | Verification Document Upload |        | Created |            |          |         |            |        |                |            |               |            |            |        |          |           |           |               |

  @CP-11414 @CP-11414-03 @ui-wf-nj @nj-regression @vidya
  Scenario Outline: Verify field length in Edit task page for Verification Document Upload task type
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority        | 1                   |
      | status          | OnHold              |
      | reasonForOnHold | Missing Information |
      | assignee        | Service AccountTwo  |
      | externalCaseId  | random              |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions
    Examples:
      | taskId | taskType                     | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      |        | Verification Document Upload |        | Created |            |          |         |            |        |                |            |               |            |            |        |          |           |           |               |

  @CP-10689 @CP-110689-02 @CP-13908 @CP-13908-07 @ui-wf-nj @nj-regression @vidya
  Scenario Outline: Verify mandatory and optional filelds in Edit task page for Process Inbound Document task type
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority        |        |
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
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify "disposition" single select drop down value
      | Resolved   |
      | Unresolved |
    And I will update the following information in edit task page
      | priority      | 2                    |
      | status        | Complete             |
      | assignee      | Service AccountTwo   |
      | taskInfo      |                      |
      | disposition   | Resolved             |
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions
    Examples:
      | taskId | taskType                 | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      |        | Process Inbound Document |        | Created |            |          |         |            |        |                |            | false         |            |            |        |          |           |           |               |

  @CP-10689 @CP-10689-03 @ui-wf-nj @nj-regression @vidya
  Scenario Outline: Verify field length in Edit task page for Process Inbound Document task type
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority        | 1                   |
      | status          | OnHold              |
      | reasonForOnHold | Missing Information |
      | assignee        | Service AccountTwo  |
      | taskInfo        | maxlength           |
      | externalCaseId  | random              |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions
    Examples:
      | taskId | taskType                 | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      |        | Process Inbound Document |        | Created |            |          |         |            |        |                |            | false         |            |            |        |          |           |           |               |

  @CP-11837 @CP-11837-02 @vidya @crm-regression @ui-wf
  Scenario Outline: Verify Task Routed to Work - Escalated Permissions when Escalated
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I will ensure assignee contains at least one "General" task if not I will create task
    And I ensure My task page has at least one task with type "General" and I navigate to view task
    And I click on edit button on view task page
    And I changed status on edit page to "<status>"
    And I select the assignee as " " in my task edit page
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I am able to navigate back to My Task or Work Queue page
    And I click on Work Queue tab
    And I navigate to newly created task by clicking on TaskID column header
    Then I will verify Escalated task is sits in work queue page
    And Close the soft assertions
    Examples:
      | status    |
      | Escalated |

  @CP-11837 @CP-11837-03 @CP-37692 @CP-37692-02 @CP-968 @CP-968-05 @kamil @vidya @crm-regression @ui-wf
  Scenario Outline: Verify Task Routed to Assignee “Work - Escalated” Permissions when Escalated
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    And I will provide following information before creating task
      | assigneeBusinessUnit | Csr team |
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    When I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    Then I verify "task-priority" single select drop down value
      | 1 |
      | 2 |
      | 3 |
      | 4 |
      | 5 |
    And I will update the following information in edit task page
      | priority        | 1                      |
      | status          | OnHold                 |
      | reasonForOnHold | Missing Information    |
      | assignee        | Service AccountTwo     |
      | taskInfo        | CP 968 Cancel function |
    And I click on cancel button on create task type screen
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    And I changed status on edit page to "<status>"
    And I select the assignee as "Service AccountOne" in my task edit page
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I am able to navigate back to My Task or Work Queue page
    And I click on My task tab
    And I navigate to newly created task by clicking on TaskID column header
    Then I will verify Escalated task is sits in work queue page
    And Close the soft assertions
    Examples:
      | status    |
      | Escalated |

#  @CP-11837 @CP-11837-04 @vidya @crm-regression @ui-wf
  Scenario Outline: Verify Task Routed to Work - Escalated Permissions when Escalated
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "My Task" page
    And I will check whether it contains at least one "Inbound Task" task if not I will create task
    And I ensure My task page has at least one task with type "Inbound Task" and I navigate to view task
    And I click on edit button on view task page
    And I changed status on edit page to "<status>"
    And I select the assignee as " " in my task edit page
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I am able to navigate back to My Task or Work Queue page
    And I click on Work Queue tab
    Then I will verify all record status are not "Escalated"
    And Close the soft assertions
    Examples:
      | status    |
      | Escalated |

  @CP-10931 @CP-10931-04 @CP-13907 @CP-13907-02 @CP-968 @CP-968-09 @CP-28816 @CP-28816-02 @vidya @crm-regression @ui-wf
  Scenario: Verify mandatory and optional filelds in Edit task page for Incomplete Contact Record task type in BLCRM
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "Incomplete Contact Record" task page
    And I will provide following information before creating task
      | assignee                 |Service AccountTwo|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority        |        |
      | status          | OnHold |
      | assignee        |        |
      | taskInfo        | ABC    |
      | reasonForOnHold |        |
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
      | Contact Record Completed            |
      | Contact Record Updated - Incomplete |
      | Unable to Update Contact Record     |
    And I will update the following information in edit task page
      | priority        | 2                    |
      | status          | Cancelled            |
      | assignee        | Service AccountTwo   |
      | reasonForCancel | Created Incorrectly  |
      | taskInfo        |                      |
      | reasonForEdit   | Corrected Data Entry |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                 |
      | disposition   | Contact Record Completed |
      | reasonForEdit   | Corrected Data Entry |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-2098 @CP-2098-02 @CP-25982 @CP-25982-02 @ruslan @vidya @crm-regression @crm-smoke @ui-wf
  Scenario: Verify if User role has permission to edit task in Tenant manager then in CP user is able edit the task
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "My Task" page
    And I ensure My task page has at least one task with type "General" and I navigate to view task
    And I click on edit button on view task page
    And I select the assignee as "Service AccountTwo" in my task edit page
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And Close the soft assertions

  @CP-2098 @CP-2098-04 @CP-25982 @CP-25982-04 @ruslan @vidya @crm-regression @ui-wf
  Scenario: Verify if User role does not has permission to edit task in Tenant manager then in CP user is not able to edit the task
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "My Task" page
    And I ensure My task page has at least one task with type "General" and I navigate to view task
    Then I should not able see edit option to edit the "task"
    And Close the soft assertions

  @CP-13907 @CP-13907-03 @CP-15323 @CP-15323-08 @CP-13177 @CP-13177-02 @vidya @crm-regression @ui-wf
  Scenario: Verify Disposition fields in Edit task page for General Two task type
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General Two" task page
    And I will provide following information before creating task
      | taskInfo | Test CP-13907      |
      | assignee | Service AccountOne |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    Then verify disposition field is not displayed
    And I will update the following information in edit task page
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify "disposition" single select drop down value
      | User closed          |
      | Consumer reached     |
      | Consumer not reached |
    And I will update the following information in edit task page
      | taskInfo      | Test-CP-13907 $ allow Special Character &%        |
      | disposition   | Consumer not reached                              |
      | reasonForEdit | Corrected Case/Consumer Link,Corrected Data Entry |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    Then I verify view page has disposition value
    And Close the soft assertions

  @CP-24419 @CP-24419-04 @crm-regression @ui-wf @ruslan
  Scenario Outline: Validate no error message is displayed edit page correct link combination
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I navigate to "<taskType>" task page
    And I will provide following information before creating task
      | assignee | Service AccountEight |
    Then I link to "<typeLink>" using consumer as "<firstName>" and Last Name as "<lastName>"
    Then I click on save button in create task page
#    Then I verify Success message is displayed for task
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | User closed          |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task
    And Close the soft assertions
    Examples:
      | taskType                             | typeLink      | firstName      | lastName    |
      | Case Required Link Task              | Case Only     | Alexandrine    | Halvorson   |
      | Consumer Required Link Task          | Consumer Only | AlexandroQexke | HellerNiYAx |
      | Case And Consumer Required Link Task | Case Consumer | Alexandrine    | Halvorson   |

  @CP-28998 @CP-28998-04 @priyal @crm-regression @ui-wf
  Scenario Outline: Verify Systematically Closed Indicator on Task Type Configuration on Edit page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And Wait for 5 seconds
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    Then I verify "task-status" single select drop down value
      | Cancelled |
      | Created   |
      | Escalated |
      | OnHold    |
    And I click on save button on task edit page
    And Close the soft assertions
    Examples:
      | taskId | taskType        | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      |        | Member Matching |        | Created |            |          |         |            |        |                |            | true          |            |            |        |          |           | today     |               |


  @CP-2978 @CP-2978-07 @CP-3313 @CP-3313-07 @CP-22015 @CP-22015-06 @priyal @vidya @crm-regression @ui-wf
  Scenario Outline: Verification of status drop down value when we are editing the task with Complete and Cancelled status
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And Wait for 5 seconds
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    Then I verify the status drop down value of task with "<status>" status
      | Cancelled |
      | Complete  |
    And I changed status on edit page to "<statusUpdate>"
    And I click on save button on task edit page
    Then I verify "<field>" drop down is displayed as mandatory field
    And I click on save button on task edit page
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status    | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason | field             | statusUpdate |
      |        | General  |        | Complete  |            | 1        |         |            |        |                |            |               |            |            |        |          |           |           |               | REASON FOR CANCEL | Cancelled    |
      |        | General  |        | Cancelled |            | 5        |         |            |        |                |            |               |            |            |        |          |           |           |               | REASON FOR EDIT   | Complete     |