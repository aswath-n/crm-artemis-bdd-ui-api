Feature: Validation of SR/Task search Page Second

@CP-9838 @CP-9838-04 @crm-regression @ui-wf @ui-sr @vidya
Scenario Outline: Verify Service request does not have initiate button in search result
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
When I navigate to "Task Search" page
And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
And I click on search button on task search page
And I verify save task search section is displayed
Then I verify tasks are does not have initiate button since searched parameter does not have permission to work
And Close the soft assertions
Examples:
| taskId | taskType | srType                  | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
|        |          | General Service Request |        |            |  1       |         |            |        |                |            | false         |            |            |        |          |           |           |             |

@CP-17798 @CP-17798-01 @CP-9839 @CP-9839-02 @CP-2389 @CP-2389-02 @crm-regression @ui-wf @ui-sr @vidya @elvin
Scenario Outline: Verify Service request does not have initiate button in search result
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
When I navigate to "Task Search" page
And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
And I click on search button on task search page
And I verify save task search section is displayed
And I click task id to get the results in descending order in Task Search
And In search result click on task id to navigate to view page
And I click on edit service request button
And I will update the following information in edit task page
| priority      |     |
| taskInfo      | abc |
| reasonForEdit |     |
And I click on save button on task edit page
And I verify task mandatory fields error message "PRIORITY"
And I verify minimum lenght error message "TASK INFORMATION"
And I verify task mandatory fields error message "REASON FOR EDIT"
Then I verify "task-priority" single select drop down value
| 1 |
| 2 |
| 3 |
| 4 |
| 5 |
And I verify "reasonForEdit" multi select drop down value
| Corrected Case/Consumer Link |
| Corrected Data Entry         |
| Corrected Error(s)           |
| Entered Additional Info      |
And I will update the following information in edit task page
| priority      | 2                       |
| taskInfo      | Test CP-17798 task info |
| reasonForEdit | Corrected Data Entry    |
And I click on save button on task edit page
    #Then I verify success message for update service request
And I will click on back arrow on view task page
Then I verify user is navigate back to task search page
Given I will get the Authentication token for "<projectName>" in "CRM"
And I initiated get task by task id "getSRIDFromUi"
And I run get task by task id API
And I verify edit reason information for SR in DB
And Close the soft assertions
Examples:
| taskId | taskType | srType                  | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
|        |          | General Service Request | Open   |            |    1     |         |            |        |                |            | false         |            |            |        |          |           |           |             |

@CP-670 @CP-670-04 @paramita @crm @crm-regression @ui-wf
Scenario Outline: Verify warning message is Display when User attempts to Create Task with unsaved data entry in Task Search screen
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
When I navigate to "Task Search" page
And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
And I click on search button on task search page
And I enter value to search name field
When I click to navigate "General" task page
Then I verify warning popup is displayed with message
And I click on cancel button on warning message
Then Verify should I remains on screen and the information add or update captured will not be cleared
| Task Search |
When I click to navigate "General" task page
And I click on continue button on warning message
Then I should return back to Create Task screen
And Close the soft assertions
Examples:
| taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
|        | General  |        |        |            | 4        |         |            |        |                |            | false         |            |            |        |          |           |           |             |

@CP-11150 @CP-11150-02 @vidya @crm-regression @ui-wf
Scenario Outline:Verify functionality of Continue button present on Warning message
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
When I navigate to "Task Search" page
And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
And I click on search button on task search page
And In search result click on task id to navigate to view page
And I will click on back arrow on view task page
Then I verify user is navigate back to task search page
And Close the soft assertions
Examples:
| taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
|        | General  |        | Created| today      |          |         |            |        |                |            | false         |            |            |        |          |           |           |             |

@CP-12694 @CP-12694-01 @Basha @crm-regression @ui-wf
Scenario Outline: Validate Escalated Search Parameters
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
And I click on initiate a contact button
When I searched customer have First Name as "ARTHUR" and Last Name as "KYSON"
And I link the contact to an existing Case or Consumer Profile
When I click on "Task & Service Request" Tab on Contact Dashboard Page
When I navigate to "General" task page
And I will provide following information before creating task
| taskInfo  | Test CP-12694-01 |
| status    | Escalated        |
And I click on save button in create task page
When I navigate to "Task Search" page
And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
When I select escalate Flag checkbox
And I click on search button on task search page
And I click task id to get the results in descending order in Task Search
Then I verify results "<status>" in the results table
And Close the soft assertions
Examples:
| taskId | taskType | srType | status    | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn | updateType   | updateStatus | updatePriority |contactReason|
|        | General  |        | Escalated |            |          |         |            |        |                |            | true          |            |            |        |          | Service AccountOne|           | Inbound Task |              |                |             |
