Feature: Validate of JustCause SR

  @CP-23612 @CP-23612-01 @CP-23613 @CP-23613-01 @CP-23614 @CP-23614-01 @CP-23615 @CP-23615-01 @CP-24094 @CP-24094-01 @CP-24095 @CP-24095-01 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Validate Just Cause Request link objects
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "NANCY" and Last Name as "VENANCIO"
    And I link the contact to an existing Case or Consumer Profile
    And I store external case id from Demographic Info Page
    And I store external consumer id from Demographic Info Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
    And I can minimize Active Contact Widget
    And I will provide following information before creating task
      | rid                   | 521521521512                                 |
      | contactName           | Alewa                                        |
      | contactPhone          | 5215215215                                   |
      | currentPlan           | Anthem                                       |
      | desiredPlanJC         | Anthem                                       |
      | programRequired       | Healthy Indiana Plan                         |
      | missingInfoRequired   | Yes                                          |
      | reasonExplanation     | FSSA Took Corrective Action Against the Plan |
      | explanation           | Limited Access to a Primary Care Clinic      |
      | decision              | Disagree                                     |
      | dateFollowupEmailSent | today                                        |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Just Cause" link section has all the business object linked : "Task,Consumer,Contact Record,Case"
    When I click id of "Just Cause Request" in Links section
    Then I verify "Just Cause Request" link section has all the business object linked : "Service Request,Consumer,Case"
    And I click on id of "Just Cause" in Links section of "TASK" page
    And I store sr id on edit page
    And I Navigate to Contact Record details page
    Then I verify contact record page link section has "Just Cause" and other business object: "Consumer Profile,Case,Service Request"
    And I click on Active Contact widget
    And I verify active contact page link section has "Just Cause" and other business object: "Consumer Profile,Case,Service Request"
    And I click on id of "Just Cause" in Links section of "Active Contact Record" page
    And I click on id of "Just Cause Request" in Links section of "SR" page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | No Action Taken      |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "TASK" page
    Then I verify "Just Cause" link section has all the business object linked : "Consumer,Case,Contact Record,Task,Task"
    And I Navigate to Contact Record details page
    Then I verify contact record page link section has "Just Cause" and other business object: "Consumer Profile,Case,Service Request"
    And I click on Active Contact widget
    And I verify active contact page link section has "Just Cause" and other business object: "Consumer Profile,Case,Service Request"
    And Close the soft assertions

  @CP-23612 @CP-23612-02 @CP-30301 @CP-30301-11 @CP-25316 @CP-25316-03 @CP-33292 @CP-33292-03 @crm-regression @ui-wf-ineb @ruslan
  Scenario: IN-EB: Create Just Cause SR with all fields
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                     | 521521521512                                 |
      | grievance               | 521521521512                                 |
      | contactName             | Alewa                                        |
      | contactPhone            | 5215215215                                   |
      | currentPlan             | Anthem                                       |
      | desiredPlanJC           | Anthem                                       |
      | programRequired         | Healthy Indiana Plan                         |
      | reasonExplanation       | FSSA Took Corrective Action Against the Plan |
      | explanation             | Limited Access to a Primary Care Clinic      |
      | taskInfo                | Test CP-23613                                |
      | missingInfoRequired     | No                                           |
      | dateHealthPlanContacted | today                                        |
      | dateReceivedGrievance   | -2                                           |
      | invalid                 | true                                         |
      | dateFollowupEmailSent   | -2                                           |
      | decision                | Agree                                        |
      | dateDecisionLetterSent  | -2                                           |
      | dateSateNotified        | -2                                           |
      | finalDecision           | Approved                                     |
      | consumerSatisfied       | Yes                                          |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for task
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    When I expand the first row in task list
    And I verify Task Notes field is not displayed on Task List expanded view page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    Then I verify the field values on View Task details
    And I click on id of "Just Cause" in Links section of "TASK" page
    And I store sr id on edit page
    When I navigate to "Task Search" page
    And I will search with srId
    And I perform a Task Search by following fields
      | assignmentStatus | Assigned |
    And I click on search button on task search page
    Then I verify request payload "assignedFlag" contains assignedFlag "true" and serviceRequestInd "false"
    Then I verify No Records Available message is displayed
    And I perform a Task Search by following fields
      | assignmentStatus | Unassigned |
    And I click on search button on task search page
    Then I verify request payload "assignedFlag" contains assignedFlag "false" and serviceRequestInd "false"
    Then I verify No Records Available message is displayed
    And Close the soft assertions

  @CP-24096 @CP-24096-01 @CP-24961 @CP-24961-03 @CP-21344 @CP-21344-04 @crm-regression @ui-wf-ineb @ruslan @priyal
  Scenario Outline: End Just Cause SR after all outbound call attempts are done and SR is still Invalid or Missing Information
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
      | rid                   | 521521521512                                 |
      | contactName           | Alewa                                        |
      | contactPhone          | 5215215215                                   |
      | currentPlan           | Anthem                                       |
      | desiredPlanJC         | Anthem                                       |
      | programRequired       | Healthy Indiana Plan                         |
      | reasonExplanation     | FSSA Took Corrective Action Against the Plan |
      | explanation           | Limited Access to a Primary Care Clinic      |
      | decision              | Disagree                                     |
      | dateFollowupEmailSent | today                                        |
      | invalid               | <invalid>                                    |
      | missingInfoRequired   | <missingInfoRequired>                        |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click on id of "Just Cause Request" in Links section of "Task" page
    And I click on edit button on view task page
    And I scroll up the page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | No Action Taken      |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    And I click on id of "JC Outbound Call" in Links section of "Service Request" page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                          |
      | reasonForEdit  | Corrected Data Entry                              |
      | disposition    | Did Not Reach Consumer                            |
      | preferredPhone | 1234567890                                        |
      | actionTaken    | Contacted Consumer - Did Not Reach/Left Voicemail |
      | contactName    | Alewa                                             |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                          |
      | reasonForEdit  | Corrected Data Entry                              |
      | disposition    | Did Not Reach Consumer                            |
      | preferredPhone | 1234567890                                        |
      | actionTaken    | Contacted Consumer - Did Not Reach/Left Voicemail |
      | contactName    | Alewa                                             |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                          |
      | reasonForEdit  | Corrected Data Entry                              |
      | disposition    | Did Not Reach Consumer                            |
      | preferredPhone | 1234567890                                        |
      | actionTaken    | Contacted Consumer - Did Not Reach/Left Voicemail |
      | contactName    | Alewa                                             |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    And I store sr id on edit page
    Then I verify link section contains 3 "JC Outbound Call"
    Then I verify the sr status is updated to close and disposition is set to "<disposition>"
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I initiated bpm process get api by service request id for process name "CauseAppSRFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | invalid | missingInfoRequired | disposition         |
      | true    | No                  | Invalid             |
      | false   | Yes                 | Missing Information |

  @CP-26864 @CP-26864-02 @CP-28646 @CP-28646-01 @CP-24962 @CP-24962-03 @crm-regression @ui-wf-ineb @ruslan
  Scenario Outline: Create State Discussion Task based on MCE and disposition fields & End Just cause SR after Send Just Cause Decision Letter Task is complete
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "NANCY" and Last Name as "VENANCIO"
    And I link the contact to an existing Case or Consumer Profile
    And I store external case id from Demographic Info Page
    And I store external consumer id from Demographic Info Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause Resolution" task page
    And I can minimize Active Contact Widget
    And I will provide following information before creating task
      | taskInfo | Test CP-22676     |
      | priority | 3                 |
      | assignee | Service TesterTwo |
    And I click on save button in create task page
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                    | 521521521512         |
      | contactName            | Alewa                |
      | contactPhone           | 5215215215           |
      | currentPlan            | Anthem               |
      | desiredPlanJC          | Anthem               |
      | programRequired        | Healthy Indiana Plan |
      | missingInfoRequired    | No                   |
      | dateFollowupEmailSent  | today                |
      | dateSateNotified       | today                |
      | dateDecisionLetterSent | today                |
      | consumerSatisfied      | Yes                  |
      | decision               | <decision>           |
      | finalDecision          | <finalDecision>      |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click on id of "Just Cause Request" in Links section of "SR" page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | No Action Taken      |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "TASK" page
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click the task search button
    And I click taskId column in the task search result
    And I click on Link button in task search
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "Just Cause" contains : "Just Cause Request,Just Cause Resolution" on "SR" link section
    When I click id of "Just Cause Resolution" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Contacted Member     |
      | disposition   | <disposition>        |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    Then I verify "Just Cause" contains : "Just Cause Request,Just Cause Resolution,Just Cause State Discussion" on "SR" link section
    When I click id of "Just Cause State Discussion" in Links section
    Then I verify "Just Cause State Discussion" link section has all the business object linked : "Service Request,Consumer,Case"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Contacted Member     |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click on id of "Just Cause" in Links section of "Task" page
    Then I verify "Just Cause" contains : "Just Cause Request,Just Cause Resolution,Just Cause State Discussion,Just Cause Decision Letter" on "SR" link section
    When I click id of "Just Cause Decision Letter" in Links section
    Then I verify "Just Cause Decision Letter" link section has all the business object linked : "Service Request,Consumer,Case"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Sent Approval Letter |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click on id of "Just Cause" in Links section of "Task" page
    And I store sr id on edit page
    Then I verify the sr status is updated to close and disposition is set to "<dispositionAfterClosedSR>"
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I initiated bpm process get api by service request id for process name "CauseAppSRFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | taskId | taskType              | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee          | createdBy | createdOn | decision | disposition                           | finalDecision | dispositionAfterClosedSR |contactReason|
      || Just Cause Resolution || Created || 3        ||            ||                || true          ||            || Service TesterTwo || today     | Disagree | Contact Member - Successfully Reached | Approved      | Complete - Approved      ||
      || Just Cause Resolution || Created || 3        ||            ||                || true          ||            || Service TesterTwo || today     | Agree    | Contact Member - Successfully Reached | Denied        | Complete - Denied        ||




