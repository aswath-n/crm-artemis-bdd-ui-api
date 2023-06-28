Feature: Task View/Edit Validation for CoverVA Second

@CP-21003 @CP-21003-03 @CP-34498 @CP-34498-01 @CP-38195 @CP-38195-02-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal @ruslan
Scenario Outline: Validate General Escalation task edit/view page
When I will get the Authentication token for "CoverVA" in "Tenant Manager"
And I initiated Business Unit By Project ID via API "8166"
Then I can verify Business Unit get API response will be "success"
Then I get the business unit data from TM
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
#    And I minimize Genesys popup if populates
When I navigate to "General Escalation" task page
And I will provide following information before creating task
| taskInfo              |                   |
| contactRecordSingle   | Emergency         |
| externalCaseId        |                   |
| externalApplicationId | random            |
| actionTakenSingle     |                   |
| documentDueDate       | today             |
| cpCRID                |                   |
| cpSRID                |                   |
| cpTaskID              |                   |
| businessUnitAssigneeTo| EscalationBU      |
| assignee              | Service TesterTwo |
And I click on save button in create task page
When I navigate to "Task Search" page
And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
And I click on search button on task search page
And I click task id to get the results in descending order in Task Search
And In search result click on task id to navigate to view page
Then I verify the below task details are displayed in my task
Given I will get the Authentication token for "CoverVA" in "CRM"
And I initiated get task by task id "getFromUi"
And I run get task by task id API
And I verify task details information for task fields for coverVA
Then I verify task table record for the task
Then I verify the below task details are displayed in my task
And I click on edit button on view task page
And I will update the following information in edit task page
| status                | Complete |
| assignee              |          |
| documentDueDate       |          |
| contactRecordSingle   |          |
| externalApplicationId |          |
| businessUnitAssigneeTo|          |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "REASON FOR EDIT"
And I verify task mandatory fields error message "CONTACT REASON"
And I verify task mandatory fields error message "DUE DATE "
And I verify task mandatory fields error message "APPLICATION ID "
And I verify task mandatory fields error message "DISPOSITION"
Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
And I verify "actionTakenSingle" single select drop down value
| Escalated to CPU   |
| Escalated to DMAS  |
| None               |
| Pending            |
| Sent to LDSS       |
| Sent to Supervisor |
And I verify "contactReason" single select drop down value
| Address Change |
| Emergency      |
| Non-Emergency  |
| Other          |
| PW Emergency   |
Then I verify "disposition" single select drop down value
| Resolved    |
| Transferred |
| Unresolved  |
Then I verify text box Date and Time field value and error message for following fields
| VaCMS Case ID        |
| Application Id       |
| CP contact record Id |
| CP SR Id             |
| CP Task Id           |
| Due Date             |
Given I will get the Authentication token for "CoverVA" in "CRM"
And Get the task type information of "General Escalation" for project ""
Given I initiated active business units and teams api
When I provide taskTypeId for active business units and teams "singleVal"
And I can run active bu and teams api
Then I get business unit names from business units and teams response api
Then I verify Assignee Business Unit dropdown displays only BU that are associated to the task type
And I will update the following information in edit task page
| status                | Complete             |
| priority              | 2                    |
| taskInfo              | CP-21003-02          |
| reasonForEdit         | Corrected Data Entry |
| contactRecordSingle   | PW Emergency         |
| externalCaseId        | 123456789            |
| externalApplicationId | 2uusr2345            |
| actionTakenSingle     | Pending              |
| documentDueDate       | 07/22/2024           |
| cpCRID                | 123456               |
| cpSRID                | 123456               |
| cpTaskID              | 123456               |
| disposition           | Resolved             |
| businessUnitAssigneeTo| EscalationBU         |
| assignee              | Service TesterTwo    |
And I click on save button on task edit page
Then I verify should I navigated to view task page
And I verify the updated information in view task details page
And I initiated get task by task id "getFromUi"
And I run get task by task id API
And I verify task details information for task fields for coverVA
Then I verify task table record for the task
And Close the soft assertions
Examples:
|taskId|taskType            |srType|status   |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy        |createdOn|contactReason|
|      |General Escalation  |      |Created  |          |        |       |          |      |              |          |true         |          |          |      |        |Service TesterTwo | today  |             |

@CP-19194 @CP-19194-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
Scenario: Validate Appeal Withdrawal task edit/view page
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I minimize Genesys popup if populates
When I navigate to "Appeal Withdrawal" task page
And I will provide following information before creating task
| assignee                ||
| taskInfo                ||
| cpSRID                  ||
And I click on save button in create task page
When I navigate to "Work Queue" page
And I navigate to newly created task by clicking on TaskID column header
And I will click on newly created task id
Then I verify the below task details are displayed in my task
And I click on edit button on view task page
And I will update the following information in edit task page
| status                          |Complete|
And I click on save button on task edit page
And I verify task mandatory fields error message "DISPOSITION"
Then I verify "disposition" single select drop down value
| Escalated to Supervisor|
| Other|
| Reclassified Document|
| Returned to DMAS|
| Withdrew Appeal|
Then I verify text box Date and Time field value and error message for following fields
|CP SR Id|
And I will update the following information in edit task page
| status                  | Complete|
| assignee                | Service TesterTwo|
| priority                | 2 |
| taskInfo                 | CP-19194-01|
| reasonForEdit           |Corrected Error(s)|
| cpSRID                  | 1234567|
| disposition             | Returned to DMAS|
And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
Then I verify should I navigated to view task page
And I verify the updated information in view task details page
Given I will get the Authentication token for "CoverVA" in "CRM"
And I initiated get task by task id "getFromUi"
And I run get task by task id API
And I verify task details information for task fields for coverVA
Then I verify task table record for the task
And Close the soft assertions

@CP-19203 @CP-19203-03 @CP-34498 @CP-34498-03 @CP-38195 @CP-38195-02-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal @ruslan
Scenario: Validate CVCC to DMAS Escalation task edit/view page
When I will get the Authentication token for "CoverVA" in "Tenant Manager"
And I initiated Business Unit By Project ID via API "8166"
Then I can verify Business Unit get API response will be "success"
Then I get the business unit data from TM
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I minimize Genesys popup if populates
When I navigate to "CVCC to DMAS Escalation" task page
And I will provide following information before creating task
| taskInfo              |                   |
| businessUnitAssigneeTo| EscalationBU      |
| assignee              | Service TesterTwo |
| externalConsumerID    |                   |
| externalCaseId        |                   |
| externalApplicationId | random            |
| actionTakenSingle     |                   |
| contactRecordSingle   | Other             |
| cpCRID                |                   |
| cpSRID                |                   |
| cpTaskID              |                   |
| documentDueDate       | today             |
And I click on save button in create task page
When I navigate to "My Task" page
And I navigate to newly created task by clicking on TaskID column header
And I will click on newly created task id
Then I verify the below task details are displayed in my task
And I click on edit button on view task page
And I will update the following information in edit task page
| assignee              |          |
| businessUnitAssigneeTo|          |
| status                | Complete |
| contactRecordSingle   |          |
| documentDueDate       |          |
| externalApplicationId |          |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "REASON FOR EDIT"
And I verify task mandatory fields error message "CONTACT REASON"
And I verify task mandatory fields error message "APPLICATION ID "
And I verify task mandatory fields error message "DUE DATE "
And I verify "actionTakenSingle" single select drop down value
| Returned - Sent in Error |
| Transferred to DMAS      |
And I verify "contactReason" single select drop down value
| FAMIS MCO Change - Error              |
| FAMIS MCO Change - Good Cause Request |
| Media Inquiries                       |
| Other                                 |
Then I verify text box Date and Time field value and error message for following fields
| VaCMS Case ID        |
| MMIS Member ID       |
| Application Id       |
| CP contact record Id |
| CP SR Id             |
| CP Task Id           |
| Due Date             |
And I will update the following information in edit task page
| status                | Complete             |
| businessUnitAssigneeTo| EscalationBU         |
| assignee              | Service TesterTwo    |
| priority              | 3                    |
| taskInfo              | CP-19203-03          |
| reasonForEdit         | Corrected Data Entry |
| contactRecordSingle   | Media Inquiries      |
| externalCaseId        | 123456789            |
| externalConsumerID    | 1234567890           |
| actionTakenSingle     | Transferred to DMAS  |
| cpCRID                | 123456               |
| cpSRID                | 123456               |
| documentDueDate       | 05/19/2022           |
| cpTaskID              | 123456               |
| externalApplicationId | TRF564r76            |
And I click on save button on task edit page
Then I verify should I navigated to view task page
And I verify the updated information in view task details page
Given I will get the Authentication token for "CoverVA" in "CRM"
And I initiated get task by task id "getFromUi"
And I run get task by task id API
And I verify task details information for task fields for coverVA
Then I verify task table record for the task
And Close the soft assertions

@CP-18988 @CP-18988-03 @CP-34498 @CP-34498-06 @CP-38195 @CP-38195-02-04 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal @ruslan
Scenario: Validate CPU Eligibility to DMAS Escalation task edit/view page
When I will get the Authentication token for "CoverVA" in "Tenant Manager"
And I initiated Business Unit By Project ID via API "8166"
Then I can verify Business Unit get API response will be "success"
Then I get the business unit data from TM
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I minimize Genesys popup if populates
When I navigate to "CPU Eligibility to DMAS Escalation" task page
And I will provide following information before creating task
| businessUnitAssigneeTo| CPU New Application|
| assignee              | Service TesterTwo |
| taskInfo              |                   |
| contactRecordSingle   | ECPR Review       |
| externalCaseId        |                   |
| externalConsumerID    |                   |
| externalApplicationId | random            |
| actionTakenSingle     |                   |
| documentDueDate       | today             |
| cpCRID                |                   |
| cpSRID                | 123456                      |
| cpTaskID              |                   |
And I click on save button in create task page
When I navigate to "My Task" page
And I navigate to newly created task by clicking on TaskID column header
And I will click on newly created task id
Then I verify the below task details are displayed in my task
And I click on edit button on view task page
And I will update the following information in edit task page
| status                | Complete |
| businessUnitAssigneeTo|          |
| assignee              |          |
| documentDueDate       |          |
| contactRecordSingle   |          |
| externalApplicationId |          |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
Then I verify task mandatory fields error message "ACTION TAKEN"
And I verify task mandatory fields error message "REASON FOR EDIT"
And I verify task mandatory fields error message "CONTACT REASON"
And I verify task mandatory fields error message "DUE DATE "
And I verify task mandatory fields error message "APPLICATION ID "
And I verify "actionTakenSingle" single select drop down value
| Escalated to DMAS (maersk)       |
| Request Complete (DMAS)           |
| Request in Progress (DMAS)        |
| Returning - Sent in Error (DMAS)  |
| Unable to Complete Request (DMAS) |
And I verify "contactReason" single select drop down value
| Coverage Correction (HPE) |
| ECPR Review               |
| Emergency Services        |
| Medicare Review           |
| Other                     |
| SAVE/SVES Request         |
Then I verify text box Date and Time field value and error message for following fields
| VaCMS Case ID        |
| Application Id       |
| CP contact record Id |
| CP SR Id             |
| CP Task Id           |
| Due Date             |
And I will update the following information in edit task page
| status                | Complete                   |
| priority              | 4                          |
| taskInfo              | CP-18988-03                |
| businessUnitAssigneeTo| CPU New Application        |
| assignee              | Service TesterTwo          |
| reasonForEdit         | Corrected Data Entry       |
| contactRecordSingle   | Other                      |
| externalCaseId        | 123456789                  |
| externalConsumerID    | 1234567890                 |
| externalApplicationId | E5r6y67u7                  |
| actionTakenSingle     | Request in Progress (DMAS) |
| documentDueDate       | 07/22/2024                 |
| cpCRID                | 123456                     |
| cpSRID                | 123456                     |
| cpTaskID              | 123456                     |
And I click on save button on task edit page
Then I verify should I navigated to view task page
And I verify the updated information in view task details page
Given I will get the Authentication token for "CoverVA" in "CRM"
And I initiated get task by task id "getFromUi"
And I run get task by task id API
And I verify task details information for task fields for coverVA
Then I verify task table record for the task
And Close the soft assertions

@CP-18781 @CP-18781-03 @CP-22077 @CP-22077-03 @CP-19834 @CP-19834-02 @CP-38195 @CP-38195-02-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
Scenario: Validate CVIU CC to DMAS Escalation task edit/view page
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I minimize Genesys popup if populates
When I navigate to "CVIU CC to DMAS Escalation" task page
And I will provide following information before creating task
| businessUnitAssigneeTo| EscalationBU      |
| assignee              | Service TesterTwo |
| taskInfo              |                   |
| contactRecordSingle   | Media Inquiries   |
| externalCaseId        |                   |
| externalConsumerID    |                   |
| externalApplicationId | random            |
| actionTakenSingle     |                   |
| documentDueDate       | today             |
| cpCRID                |                   |
| cpSRID                |                   |
| cpTaskID              |                   |
And I click on save button in create task page
When I navigate to "My Task" page
And I navigate to newly created task by clicking on TaskID column header
And I will click on newly created task id
Then I verify the below task details are displayed in my task
And I click on edit button on view task page
And I will update the following information in edit task page
| status                | Complete |
| businessUnitAssigneeTo|          |
| documentDueDate       |          |
| contactRecordSingle   |          |
| externalApplicationId |          |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "REASON FOR EDIT"
And I verify task mandatory fields error message "CONTACT REASON"
And I verify task mandatory fields error message "DUE DATE "
And I verify task mandatory fields error message "APPLICATION ID "
And I verify "actionTakenSingle" single select drop down value
| Returned - Sent in Error |
| Transferred to DMAS      |
And I verify "contactReason" single select drop down value
| Facility Complaint |
| Media Inquiries    |
| Other              |
And I verify "reasonForEdit" multi select drop down value
| Corrected Case/Consumer Link |
| Corrected Data Entry         |
| Corrected Error(s)           |
| Entered Additional Info      |
Then I verify text box Date and Time field value and error message for following fields
| VaCMS Case ID        |
| MMIS Member ID       |
| Application Id       |
| CP contact record Id |
| CP SR Id             |
| CP Task Id           |
| Due Date             |
And I will update the following information in edit task page
| status                | Complete             |
| businessUnitAssigneeTo| EscalationBU         |
| assignee              | Service TesterTwo    |
| priority              | 4                    |
| taskInfo              | maxlength            |
| reasonForEdit         | Corrected Data Entry |
| contactRecordSingle   | Other                |
| externalCaseId        | 123456789            |
| externalConsumerID    | 1234567890           |
| externalApplicationId | E5r6y67u7            |
| actionTakenSingle     | Transferred to DMAS  |
| documentDueDate       | 07/22/2024           |
| cpCRID                | 18781                |
| cpSRID                | 18781                |
| cpTaskID              | 18781                |
And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
Then I verify should I navigated to view task page
And I verify the updated information in view task details page
When I navigate to "Task Search" page
And I will search with taskId
And I verify save task search section is displayed
And I expand the first row in search result list
Then I verify the task details are displayed in search result when expanded
And Close the soft assertions