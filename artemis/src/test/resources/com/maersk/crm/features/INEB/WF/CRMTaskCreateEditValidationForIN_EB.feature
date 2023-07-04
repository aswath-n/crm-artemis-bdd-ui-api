Feature: Task Update and View Validation for IN-EB

  @CP-30400 @CP-30400-03 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Validate Guardianship Form task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Guardianship Form" task page
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify "actionTaken" multi select drop down value
      | Added Authorized Representative to Case |
      | Outbound Call - Consumer Reached        |
      | Outbound Call - Did Not Reach           |
    Then I verify "disposition" single select drop down value
      | Invalid   |
      | Processed |
    And I will update the following information in edit task page
      | assignee      | Service TesterTwo                |
      | priority      | 2                                |
      | taskInfo      | updated task info                |
      | reasonForEdit | Corrected Data Entry             |
      | actionTaken   | Outbound Call - Consumer Reached |
      | disposition   | Invalid                          |
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And I navigated to the Edit History tab ON Edit Task
    And I navigated to the Task Details tab ON Edit Task
    And Close the soft assertions

  @CP-30404 @CP-30404-03 @CP-34966 @CP-34966-05 @crm-regression @INEB-UI-Regression @ui-wf-ineb @kamil
  Scenario: Validate Hoosier Care Connect Do Not Call List validation Edit/View page
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
      | contactPhone | 9876543210              |
      | other        | 123 $567 Tesr5565#$#535 |
    And I click on save button in create task page
    When I navigate to "Work Queue" page
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

  @CP-30459 @CP-30459-03 @CP-39600 @CP-39600-04 @crm-regression @ui-wf-ineb @ruslan
  Scenario Outline: Validate Just Cause Request task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Just Cause Request" task page
    And I will provide following information before creating task
      | priority | 2 |
      | taskInfo | maxlength |
    And I click on save button in create task page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify "actionTaken" multi select drop down value
      | No Action Taken                     |
      | Submitted Just Cause Request to MCE |
    And I will update the following information in edit task page
      | assignee      | Service TesterTwo    |
      | priority      | 2                    |
      | taskInfo      | updated task info    |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | No Action Taken      |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions
    Examples:
      | taskId | taskType           | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      || Just Cause Request || Created || 2        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||


  @CP-30460 @CP-30460-03 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Validate Just Cause Follow-up Email task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Just Cause Follow-up Email" task page
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify "actionTaken" multi select drop down value
      | Sent Follow-up Email to MCE |
    And I will update the following information in edit task page
      | assignee      | Service TesterTwo           |
      | priority      | 2                           |
      | taskInfo      | updated task info           |
      | reasonForEdit | Corrected Data Entry        |
      | actionTaken   | Sent Follow-up Email to MCE |
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30462 @CP-30462-03 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Validate Just Cause Decision Letter task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Just Cause Decision Letter" task page
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify "actionTaken" multi select drop down value
      | Sent Approval Letter |
      | Sent Denial Letter   |
    And I will update the following information in edit task page
      | assignee      | Service TesterTwo    |
      | priority      | 2                    |
      | taskInfo      | updated task info    |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Sent Approval Letter |
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30397 @CP-30397-02 @crm-regression @ui-wf-ineb @vidya
  Scenario: Validate DCS Request task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "DCS Request" task page
    And I will provide following information before creating task
      | assignee            ||
      | taskInfo            ||
      | eligibilityDecision ||
      | denialReasonSingle  ||
      | other               ||
      | actionTaken         ||
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status              | Complete |
      | eligibilityDecision | Denied   |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "REASON FOR EDIT"
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DENIAL REASON "
    And I will update the following information in edit task page
      | denialReasonSingle | Other |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "OTHER "
    And I verify "actionTaken" multi select drop down value
      | Sent Denial Email    |
      | Sent Processed Email |
    Then I verify "eligibilityDecision" single select drop down value
      | Approved |
      | Denied   |
    Then I verify "denialReason" single select drop down value
      | Eligibility Ending |
      | Not Eligible       |
      | Other              |
    Then I verify text box Date and Time field value and error message for following fields
      | Other |
    And I will update the following information in edit task page
      | status              | Complete                     |
      | assignee            | Service TesterTwo            |
      | priority            | 2                            |
      | taskInfo            | CP-30397                     |
      | reasonForEdit       | Corrected Data Entry         |
      | eligibilityDecision | Denied                       |
      | denialReasonSingle  | Other                        |
      | other               | Test CP-30397 Working Or Not |
      | actionTaken         | Sent Denial Email            |
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30398 @CP-30398-02 @CP-35380 @CP-35380-01 @CP-34961 @CP-34961-01 @crm-regression @ui-wf-ineb @vidya
  Scenario: Validate DCS Request task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Disenrollment" task page
    And I will provide following information before creating task
      | assignee            ||
      | taskInfo            ||
      | currentPlan         | CareSource |
      | disenrollmentReason | Death      |
      | source              | DCS        |
      | other               ||
      | disenrollmentDate   ||
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status              | Complete |
      | currentPlan         ||
      | disenrollmentReason ||
      | source              ||
    And I click on save button on task edit page
    And I verify task mandatory fields error message "REASON FOR EDIT"
    Then I verify task mandatory fields error message "CURRENT PLAN "
    And I verify task mandatory fields error message "DISENROLLMENT REASON "
    Then I verify task mandatory fields error message "SOURCE "
    And I verify task mandatory fields error message "DISPOSITION"
    And I will update the following information in edit task page
      | disposition | Approved |
      | source      | Other    |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "OTHER "
    And I verify task mandatory fields error message "DISENROLLMENT DATE "
    Then I verify "currentPlan" single select drop down value
      | Anthem                        |
      | CareSource                    |
      | Managed Health Services (MHS) |
      | MDWise                        |
      | No Plan Assigned              |
      | United Healthcare             |
    Then I verify "disenrollmentReason" single select drop down value
      | Death                       |
      | Disenroll from Managed Care |
      | Hospice                     |
      | PRTF                        |
      | Voluntary                   |
      | Waiver                      |
    Then I verify "applicationSource" single select drop down value
      | DCS                     |
      | FSSA                    |
      | Member                  |
      | Neurodiagnostic Center  |
      | Other                   |
      | Richmond State Hospital |
      | State                   |
    Then I verify "disposition" single select drop down value
      | Approved       |
      | Denied         |
    Then I verify text box Date and Time field value and error message for following fields
      | Other              |
      | Disenrollment Date |
    And I will update the following information in edit task page
      | status              | Complete                     |
      | assignee            | Service TesterTwo            |
      | priority            | 2                            |
      | taskInfo            | CP-30398                     |
      | reasonForEdit       | Corrected Data Entry         |
      | currentPlan         | MDWise                       |
      | disenrollmentReason | PRTF                         |
      | source              | Other                        |
      | disposition         | Approved                     |
      | disenrollmentDate   | today                        |
      | other               | Test CP-30398 Working Or Not |
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30399 @CP-30399-02 @CP-31982 @CP-31982-06 @crm-regression @ui-wf-ineb @vidya @priyal
  Scenario: Validate Error Correction task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Error Correction" task page
    And I will provide following information before creating task
      | assignee      ||
      | taskInfo      ||
      | planName      ||
      | planStartDate ||
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I navigated to the Edit History tab ON Edit Task
    And I navigated to the Task Details tab ON Edit Task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status | Complete |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "DISPOSITION"
    Then I verify "planName" single select drop down value
      | Anthem                        |
      | CareSource                    |
      | Managed Health Services (MHS) |
      | MDWise                        |
      | United Healthcare             |
    Then I verify "disposition" single select drop down value
      | Contact Member - Did Not Reach |
      | Contact Member - Reached       |
      | Error Corrected                |
    Then I verify text box Date and Time field value and error message for following fields
      | Plan Start Date |
    And I will update the following information in edit task page
      | status        | Complete             |
      | assignee      | Service TesterTwo    |
      | priority      | 2                    |
      | taskInfo      | CP-30399             |
      | reasonForEdit | Corrected Data Entry |
      | planName      | CareSource           |
      | planStartDate | today                |
      | disposition   | Error Corrected      |
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30410 @CP-30410-02 @crm-regression @ui-wf-ineb @vidya
  Scenario: Validate TBI Report task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "TBI Report" task page
    And I will provide following information before creating task
      | assignee    ||
      | taskInfo    ||
      | actionTaken ||
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify "actionTaken" multi select drop down value
      | Disenroll |
      | Re-enroll |
    And I will update the following information in edit task page
      | status        | Complete             |
      | assignee      | Service TesterTwo    |
      | priority      | 2                    |
      | taskInfo      | CP-30410             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Disenroll            |
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30464 @CP-30464-03 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Validate  JC Outbound Call task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "JC Outbound Call" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | assignee              ||
      | taskInfo              ||
      | contactName           | Vasiliy |
      | preferredCallBackDate ||
      | preferredCallBackTime ||
      | preferredPhone        ||
      | actionTaken           ||
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify "actionTaken" multi select drop down value
      | Contacted Consumer - Did Not Reach/Left Voicemail |
      | Contacted Consumer - Did Not Reach/No Voicemail   |
      | Contacted Consumer - Invalid Phone Number         |
      | Contacted Consumer - Reached                      |
    Then I verify "disposition" single select drop down value
      | Did Not Reach Consumer        |
      | Successfully Reached Consumer |
    Then I verify text box Date and Time field value and error message for following fields
      | CONTACT NAME             |
      | PREFERRED PHONE          |
      | PREFERRED CALL BACK DATE |
      | PREFERRED CALL BACK TIME |
    And I will update the following information in edit task page
      | status                | Complete                      |
      | reasonForEdit         | Corrected Data Entry          |
      | assignee              | Service TesterTwo             |
      | taskInfo              | Test CP-30464 2               |
      | contactName           | Vasya Petrov                  |
      | preferredCallBackDate | today                         |
      | preferredCallBackTime | 03:28 PM                      |
      | preferredPhone        | 1234567890                    |
      | actionTaken           | Contacted Consumer - Reached  |
      | disposition           | Successfully Reached Consumer |
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions


  @CP-30461 @CP-30461-03 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Validate Just Cause Resolution task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Just Cause Resolution" task page
    And I will provide following information before creating task
      | assignee    ||
      | taskInfo    ||
      | actionTaken ||
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify "actionTaken" multi select drop down value
      | Contacted Member                    |
      | Linked Inbound Correspondence To SR |
      | Processed Update in CORE            |
    Then I verify "disposition" single select drop down value
      | Contact Member - Did Not Reach        |
      | Contact Member - Successfully Reached |
      | Did Not Contact Member                |
    And I will update the following information in edit task page
      | assignee      | Service TesterTwo                     |
      | priority      | 2                                     |
      | taskInfo      | updated task info5353                 |
      | reasonForEdit | Corrected Data Entry                  |
      | actionTaken   | Contacted Member                      |
      | disposition   | Contact Member - Successfully Reached |
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30463 @CP-30463-03 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Validate Just Cause State Discussion task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Just Cause State Discussion" task page
    And I will provide following information before creating task
      | assignee    ||
      | taskInfo    ||
      | actionTaken ||
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify "actionTaken" multi select drop down value
      | Contacted Member         |
      | Discussed With State     |
      | Processed Update in CORE |
    And I will update the following information in edit task page
      | assignee      | Service TesterTwo     |
      | priority      | 2                     |
      | taskInfo      | updated task info5353 |
      | reasonForEdit | Corrected Data Entry  |
      | actionTaken   | Contacted Member      |
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30407 @CP-30407-02 @CP-30407-03 @CP-30301 @CP-30301-05 @CP-30081 @CP-30081-04 @crm-regression @ui-wf-ineb @kamil
  Scenario: Verification all fields match requirements on View/Edit
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Member Address Change Request" task page
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
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task
    When I will get the Authentication token for "IN-EB" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I will get "Created" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    When I navigate to "Task Search" page
    Then I will search for newly created task on Task Search
    And I perform a Task Search by following fields
      | assignmentStatus | Assigned |
    And I click on search button on task search page
    Then I verify request payload "assignedFlag" contains assignedFlag "true" and serviceRequestInd "false"
    Then I verify No Records Available message is displayed
    And I perform a Task Search by following fields
      | assignmentStatus | Unassigned |
    And I click on search button on task search page
    And I initiated search records API
    Then I verify staffAssignedTo field returns "null"
    Then I verify request payload "assignedFlag" contains assignedFlag "false" and serviceRequestInd "false"
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
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
    And I will update the following information in edit task page
      | addressType    | Physical             |
      | addressSource  | Consumer Reported    |
      | addressZipCode | 80145                |
      | status         | Complete             |
      | actionTaken    | Refer to FSSA        |
      | reasonForEdit  | Corrected Data Entry |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30457 @CP-30457-03 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Validate HCC Outbound Call task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HCC Outbound Call" task page
    And I will provide following information before creating task
      | taskInfo              ||
      | assignee              ||
      | invalidReason         ||
      | preferredCallBackDate ||
      | preferredCallBackTime ||
      | actionTaken           ||
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DISPOSITION"
    And I will update the following information in edit task page
      | disposition | Invalid |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "INVALID REASON "
    And I verify "invalidReason" single select drop down value
      | Not an HCC Member         |
      | Other                     |
      | Plan Selection Not Needed |
    And I verify "disposition" single select drop down value
      | Invalid              |
      | Needs Plan Selection |
      | Plan Selection Made  |
    And I will update the following information in edit task page
      | invalidReason | Other |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "OTHER "
    Then I verify text box Date and Time field value and error message for following fields
      | Other                    |
      | PREFERRED CALL BACK DATE |
      | PREFERRED CALL BACK TIME |
    And I verify "actionTaken" multi select drop down value
      | Contacted Consumer - Did Not Reach/Left Voicemail |
      | Contacted Consumer - Did Not Reach/No Voicemail   |
      | Contacted Consumer - Invalid Phone Number         |
      | Contacted Consumer - Reached                      |
      | No Action Taken                                   |
      | Verified - Needs Call                             |
    And I will update the following information in edit task page
      | reasonForEdit         | Corrected Data Entry         |
      | assignee              | Service TesterTwo            |
      | taskInfo              | updated task info5353        |
      | disposition           | Invalid                      |
      | invalidReason         | Other                        |
      | other                 | 123 $567 Tesr5565#$#535      |
      | preferredCallBackDate | today                        |
      | preferredCallBackTime | 03:28 PM                     |
      | actionTaken           | Contacted Consumer - Reached |
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

#    Fails due the 3275
  @CP-30409 @CP-30409-01 @CP-34969 @CP-34969-01 @crm-regression @ui-wf-ineb @kamil @ruslan
  Scenario: Verification all fields and mandatory fields for Rejected Selection Task on Task View/Edit
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | taskTypes | Rejected Selection |
      | status    | Complete           |
    And I click on search button on task search page
    And I verify task search results are displayed
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I verify "applicationType" single select drop down value
      | New Enrollment |
      | Plan Change    |
    And I verify "disposition" single select drop down value
      | Contact Member (explain why selection didn't go through) |
      | Contact State                                            |
      | Processed in CORE                                        |
      | Send back to CSR to correct mistake                      |
    And I verify "program" single select drop down value
      | HealthyIndianaPlan |
      | HoosierCareConnect |
      | HoosierHealthwise  |
    Then I verify text box Date and Time field value and error message for following fields
      | Medicaid ID/RID |
      | Start Date      |
      | END DATE        |
      | Reason          |
    And Close the soft assertions