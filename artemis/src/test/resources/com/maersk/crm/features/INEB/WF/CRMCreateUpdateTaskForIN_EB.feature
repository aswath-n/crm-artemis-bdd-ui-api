Feature: Task End To End Validation for IN-EB

  @CP-30400 @CP-30400-02 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Validate Guardianship Form task created and data is saved
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Guardianship Form" task page
    And I will provide following information before creating task
      | taskInfo    | Test CP-18026-02&$%              |
      | assignee    | Service TesterTwo                |
      | actionTaken | Outbound Call - Consumer Reached |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                    |Cancelled|
      | reasonForCancel           |Created Incorrectly|
      | assignee                  ||
      | taskInfo                  |Test CP-18026-Updated|
      | actionTaken               ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30404 @CP-30404-02 @CP-34966 @CP-34966-04 @crm-regression @INEB-UI-Regression @ui-wf-ineb @kamil
  Scenario: Validate Hoosier Care Connect Do Not Call List Update Task
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HCC Do Not Call List" task page
    And I click on save button on task edit page
    And I verify task mandatory fields error message "PHONE "
    And I verify task mandatory fields error message "Reason"
    Then I verify "Other" field is disable and cleared out
    Then I verify when I saving Task with Phone field less than 10 chars getting error "PHONE must be 10 characters"
    And I will provide following information before creating task
      | reason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | assignee     | Service TesterTwo       |
      | contactPhone | 9876543210              |
      | other        | 123 $567 Tesr5565#$#535 |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | reason | Death |
    Then I verify when I saving Task with Phone field less than 10 chars getting error "PHONE must be 10 characters"
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | reason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | contactPhone | 9876543210              |
      | other        | 123 $567 Tesr5565#$#535 |
    And I will update the following information in edit task page
      | status          | Cancelled      |
      | reasonForCancel | Duplicate Task |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30459 @CP-30459-02 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Validate Just Cause Request task created and data is saved
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Just Cause Request" task page
    And I will provide following information before creating task
      | taskInfo    | Test CP-sasa-02&$% |
      | assignee    | Service TesterTwo   |
      | actionTaken | No Action Taken     |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | Cancelled             |
      | reasonForCancel | Created Incorrectly   |
      | assignee        ||
      | taskInfo        | Test CP-18026-Updated |
      | actionTaken     ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30460 @CP-30460-02 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Validate Just Cause Follow-up Email task created and data is saved
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Just Cause Follow-up Email" task page
    And I will provide following information before creating task
      | taskInfo    | Test CP-dsd-02&$%           |
      | assignee    | Service TesterTwo           |
      | actionTaken | Sent Follow-up Email to MCE |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | Cancelled             |
      | reasonForCancel | Created Incorrectly   |
      | assignee        ||
      | taskInfo        | Test CP-18026-Updated |
      | actionTaken     ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30462 @CP-30462-02 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Validate Just Cause Decision Letter task created and data is saved
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Just Cause Decision Letter" task page
    And I will provide following information before creating task
      | taskInfo    | Test CP-dsd-02&$%    |
      | assignee    | Service TesterTwo    |
      | actionTaken | Sent Approval Letter |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | Cancelled             |
      | reasonForCancel | Created Incorrectly   |
      | assignee        ||
      | taskInfo        | Test CP-18026-Updated |
      | actionTaken     ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30397 @CP-30397-03 @CP-37763 @CP-37763-02 @priyal @crm-regression @ui-wf-ineb @vidya
  Scenario Outline: Validate DCS Request task created and update and Verify the Details on UI and API
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I initiated Business Unit By Project ID via API "8861"
    Then I can verify Business Unit get API response will be "success"
    Then I get the business unit data from TM
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "DCS Request" task page
    And I will provide following information before creating task
      | assignee                | Service TesterTwo           |
      | taskInfo                | Test CP-30397               |
      | eligibilityDecision     | Denied                      |
      | denialReasonSingle      | Other                       |
      | other                   | Test CP-30397 Working Or Not|
      | actionTaken             | Sent Denial Email           |
      | businessUnitAssigneeTo  | Back Office |
    And I click on save button in create task page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    Then I verify task table record for the task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Cancelled          |
      | reasonForCancel         |Created Incorrectly|
      | assignee                ||
      | taskInfo                ||
      | other                   ||
      | denialReasonSingle      ||
      | eligibilityDecision     ||
      | actionTaken             ||
      | businessUnitAssigneeTo  ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    Then I verify task table record for the task
    And Close the soft assertions
    Examples:
      | taskId | taskType   | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      || DCS Request|| Created||          ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

  @CP-30398 @CP-30398-03 @crm-regression @ui-wf-ineb @vidya
  Scenario: Validate Disenrollment task created and update
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Disenrollment" task page
    And I will provide following information before creating task
      | assignee                |Service TesterTwo           |
      | taskInfo                |Test CP-30397               |
      | currentPlan             |MDWise                      |
      | disenrollmentReason     |PRTF                        |
      | source                  |Other                       |
      | disenrollmentDate       ||
      | other                   |Test CP-30398 Working Or Not|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status               |Cancelled          |
      | reasonForCancel      |Created Incorrectly|
      | assignee             ||
      | taskInfo             ||
      | currentPlan          |CareSource|
      | disenrollmentReason  |Death|
      | source               |DCS|
      | other                ||
      | disenrollmentDate    ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30399 @CP-30399-03 @crm-regression @ui-wf-ineb @vidya
  Scenario: Validate Error Correction task created and update
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Error Correction" task page
    And I will provide following information before creating task
      | assignee                |Service TesterTwo   |
      | taskInfo                |Test CP-30399       |
      | planName                |CareSource          |
      | planStartDate           |+1                  |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status               |Cancelled          |
      | reasonForCancel      |Created Incorrectly|
      | assignee             ||
      | taskInfo             ||
      | planName             ||
      | planStartDate        ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30410 @CP-30410-03 @crm-regression @ui-wf-ineb @vidya
  Scenario: Validate TBI Report task created and update
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "TBI Report" task page
    And I will provide following information before creating task
      | assignee                |Service TesterTwo   |
      | taskInfo                |Test CP-30410       |
      | actionTaken             |Re-enroll           |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status               |Cancelled          |
      | reasonForCancel      |Created Incorrectly|
      | assignee             ||
      | taskInfo             ||
      | actionTaken          ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30464 @CP-30464-02 @crm-regression @ui-wf-ineb @ruslan
  Scenario:Validate JC Outbound Call task created and update
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "JC Outbound Call" task page
    And I will provide following information before creating task
      | assignee              | Service TesterTwo            |
      | taskInfo              | Test CP-30464 2              |
      | contactName           | Vasya Petrov                 |
      | preferredCallBackDate | today                        |
      | preferredCallBackTime | 03:28 PM                     |
      | preferredPhone        | 1234567890                   |
      | actionTaken           | Contacted Consumer - Reached |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Cancelled           |
      | reasonForCancel       | Created Incorrectly |
      | assignee              ||
      | taskInfo              ||
      | contactName           ||
      | preferredCallBackDate ||
      | preferredCallBackTime ||
      | preferredPhone        ||
      | actionTaken           ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30461 @CP-30461-02 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Verification all fields and mandatory fields on Just Cause Resolution create task page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Just Cause Resolution" task page
    And I will provide following information before creating task
      | assignee    | Service TesterTwo        |
      | taskInfo    | test CP-30461-02         |
      | actionTaken | Processed Update in CORE |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status               |Cancelled          |
      | reasonForCancel      |Created Incorrectly|
      | assignee             ||
      | taskInfo             ||
      | actionTaken          ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page

  @CP-30463 @CP-30463-02 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Verification all fields and mandatory fields on Just Cause State Discussion create task page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Just Cause State Discussion" task page
    And I will provide following information before creating task
      | assignee    | Service TesterTwo        |
      | taskInfo    | test CP-30463-02         |
      | actionTaken | Processed Update in CORE |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status               |Cancelled          |
      | reasonForCancel      |Created Incorrectly|
      | assignee             ||
      | taskInfo             ||
      | actionTaken          ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page

  @CP-30458 @CP-30458-02 @CP-33292 @CP-33292-02 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Verification all fields and mandatory fields on Just Cause SR edit/view
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "NANCY" and Last Name as "KIYAN"
    When I expand the record to navigate Case Consumer Record from manual view
    Then I store external consumer id from Demographic Info Page
    And I store external consumer id from Demographic Info Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                 | 521521521512         |
      | contactName         | Alewa                |
      | contactPhone        | 5215215215           |
      | currentPlan         | Anthem               |
      | desiredPlanJC       | Anthem               |
      | programRequired     | Healthy Indiana Plan |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify the SR details displayed on view SR page
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit          | Corrected Data Entry |
      | status                 | Closed               |
      | rid                    |                      |
      | contactName            |                      |
      | currentPlan            |                      |
      | contactPhone           |                      |
      | DesiredPlan            |                      |
      | programRequired        |                      |
      | dateReceivedGrievance  | +2                   |
      | dateFollowupEmailSent  | +2                   |
      | dateDecisionLetterSent | +2                   |
      | dateSateNotified       | +2                   |
    And I click on save button on task edit page
    Then I verify error message displayed on the "DATE RECEIVED GRIEVANCE" if I select future day
    Then I verify error message displayed on the "DATE FOLLOW-UP EMAIL SENT" if I select future day
    Then I verify error message displayed on the "DATE STATE NOTIFIED" if I select future day
    Then I verify error message displayed on the "DATE DECISION LETTER SENT" if I select future day
    Then I verify task mandatory fields error message "RID # "
    Then I verify task mandatory fields error message "CONTACT NAME "
    Then I verify task mandatory fields error message "CONTACT PHONE "
    Then I verify task mandatory fields error message "PROGRAM"
    Then I verify task mandatory fields error message "CURRENT PLAN "
    Then I verify task mandatory fields error message "DESIRED PLAN "
    And I verify task mandatory fields error message "DISPOSITION"
    Then I verify "disposition" single select drop down value
      | Invalid             |
      | Complete - Approved |
      | Complete - Denied   |
      | Missing Information |
      | Withdrawn           |
    And I will update the following information in edit task page
      | rid                    | 521521521512         |
      | contactName            | Alewa                |
      | currentPlan            | Anthem               |
      | contactPhone           | 5215215215           |
      | desiredPlanJC          | Anthem               |
      | programRequired        | Healthy Indiana Plan |
      | disposition            | Invalid              |
      | dateReceivedGrievance  | -5                   |
      | dateFollowupEmailSent  | -5                   |
      | dateDecisionLetterSent | -5                   |
      | dateSateNotified       | -5                   |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for SR update
    Then I verify the SR details displayed on view SR page
    And Close the soft assertions

  @CP-30457 @CP-30457-02 @crm-regression @INEB-UI-Regression @ui-wf-ineb @ruslan
  Scenario: Verification all fields and mandatory fields on HCC Outbound Call edit task page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HCC Outbound Call" task page
    And I will provide following information before creating task
      | assignee    | Service TesterTwo        |
      | taskInfo    | test CP-30461-02         |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Cancelled           |
      | reasonForCancel       | Created Incorrectly |
      | assignee              ||
      | taskInfo              ||
      | preferredCallBackDate ||
      | preferredCallBackTime ||
      | actionTaken           ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions