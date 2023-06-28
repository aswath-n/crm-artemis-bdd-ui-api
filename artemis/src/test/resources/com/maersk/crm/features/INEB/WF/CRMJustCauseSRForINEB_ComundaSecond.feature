Feature: Validate of JustCause SR Comunda Second

  @CP-24965 @CP-24965-01 @crm-regression @sr-camunda @ruslan
  Scenario: validate process moves off of the 30 day wait step path if user manually link JC Resolution task
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "NANCY" and Last Name as "JAKOBE"
    And I link the contact to an existing Case or Consumer Profile
    And I store external case id from Demographic Info Page
    And I store external consumer id from Demographic Info Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
    And I can minimize Active Contact Widget
    And I will provide following information before creating task
      | rid                    | 521521521512                                 |
      | contactName            | Alewa                                        |
      | contactPhone           | 5215215215                                   |
      | currentPlan            | Anthem                                       |
      | desiredPlanJC          | Anthem                                       |
      | programRequired        | Healthy Indiana Plan                         |
      | missingInfoRequired    | No                                           |
      | dateFollowupEmailSent  | today                                        |
      | dateSateNotified       | today                                        |
      | dateDecisionLetterSent | today                                        |
      | consumerSatisfied      | Yes                                          |
      | decision               | Disagree                                     |
      | finalDecision          | Approved                                     |
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
    And Wait for 250 seconds
    And I click on id of "Just Cause" in Links section of "TASK" page
    When I click id of "Just Cause Follow-up Email" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                    |
      | reasonForEdit | Corrected Data Entry        |
      | actionTaken   | Sent Follow-up Email to MCE |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "TASK" page
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click on Link button in task search
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "Just Cause" contains : "Just Cause Request,Just Cause Follow-up Email,Just Cause Resolution" on "SR" link section
    When I click id of "Just Cause Resolution" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                              |
      | reasonForEdit | Corrected Data Entry                  |
      | actionTaken   | Contacted Member                      |
      | disposition   | Contact Member - Successfully Reached |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    Then I verify "Just Cause" contains : "Just Cause Request,Just Cause Follow-up Email,Just Cause Resolution,Just Cause State Discussion" on "SR" link section
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
    Then I verify "Just Cause" contains : "Just Cause Request,Just Cause Follow-up Email,Just Cause Resolution,Just Cause State Discussion,Just Cause Decision Letter" on "SR" link section
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
    Then I verify the sr status is updated to close and disposition is set to "Complete - Approved"
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I initiated bpm process get api by service request id for process name "CauseAppSRFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | taskId | taskType              | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      |        | Just Cause Resolution |        | Created |            | 3        |         |            |        |                |            |               |            |            |        |          |           |           |               |

  @CP-25059 @CP-25059-02 @CP-25060 @CP-25060-02 @CP-25061 @CP-25061-02 @CP-25062 @CP-25062-02 @CP-31052 @CP-31052-01 @sr-camunda @ruslan
  Scenario: Conditionally Require Fields on Just Cause SR related Task on Task slider
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click case consumer search tab
    When I searched customer have First Name as "NANCY" and Last Name as "ABELARDO"
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                 | 521521521512                                 |
      | contactName         | Alewa                                        |
      | contactPhone        | 5215215215                                   |
      | currentPlan         | Anthem                                       |
      | desiredPlanJC       | Anthem                                       |
      | programRequired     | Healthy Indiana Plan                         |
      | missingInfoRequired | No                                           |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click on id of "Just Cause Request" in Links section of "SR" page
    And I click on edit button on view task page
    And I scroll up the page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | No Action Taken      |
    And I click on save button on task edit page
    And Wait for 250 seconds
    When If any In Progress task present then update that to Cancelled
    And I will click on back arrow on view task page
    And I Verify user is navigate back to Task and service Request Page
    Then I navigate to newly created task in Task & Service Request Tab
    And I scroll up the page
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status      | Complete                    |
      | actionTaken | Sent Follow-up Email to MCE |
    And I click on save in Task Slider
    Then Verify I will see error message "Please provide the Date Follow-up Email Sent on the Just Cause SR before completing this task."
    When I click on continue on task details warning window
    And Verify task slider is displayed
    And I click on the priority in dashboard
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit         | Corrected Data Entry |
      | dateFollowupEmailSent | today                |
    And I click on save button on task edit page
    And I click on the priority in dashboard
    And I click on save in Task Slider
#    Then I verify task save success message
    And Wait for 250 seconds
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I Verify user is navigate back to Task and service Request Page
    Then I navigate to newly created task in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status      | Complete                              |
      | actionTaken | Contacted Member                      |
      | disposition | Contact Member - Successfully Reached |
    And I click on save in Task Slider
    Then Verify I will see error message "Please provide the MCE Decision on the Just Cause SR before completing this task."
    When I click on continue on task details warning window
    And Verify task slider is displayed
    And I click on the priority in dashboard
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
      | decision      | Agree                |
    And I click on save button on task edit page
    And I click on the priority in dashboard
    And I click on save in Task Slider
#    Then I verify task save success message
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I Verify user is navigate back to Task and service Request Page
    Then I navigate to newly created task in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status      | Complete         |
      | actionTaken | Contacted Member |
    And I click on save in Task Slider
    Then Verify I will see error message "Please provide the Date State Notified, the Final Decision and if the consumer was satisfied on the Just Cause SR before completing this task."
    When I click on continue on task details warning window
    And Verify task slider is displayed
    And I click on the priority in dashboard
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit     | Corrected Data Entry |
      | finalDecision     | Approved             |
      | consumerSatisfied | Yes                  |
      | dateSateNotified  | today                |
    And I click on save button on task edit page
    And I click on the priority in dashboard
    And I click on save in Task Slider
#    Then I verify task save success message
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I Verify user is navigate back to Task and service Request Page
    Then I navigate to newly created task in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status      | Complete             |
      | actionTaken | Sent Approval Letter |
    And I click on save in Task Slider
    Then Verify I will see error message "Please provide the Date Decision Letter Sent on the Just Cause SR before completing this task."
    When I click on continue on task details warning window
    And Verify task slider is displayed
    And I click on the priority in dashboard
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit          | Corrected Data Entry |
      | dateDecisionLetterSent | today                |
    And I click on save button on task edit page
    And I click on the priority in dashboard
    And I click on save in Task Slider
#    Then I verify task save success message
    And Close the soft assertions

  @CP-24965 @CP-24965-03 @crm-regression @sr-camunda @ruslan
  Scenario: validate process moves off of the 30 day wait step path if user manually link JC SR to JC Resolution Task
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click case consumer search tab
    When I searched customer have First Name as "NANCY" and Last Name as "ELIAS"
    When I store external case id and consumer id
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
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
      | decision               | Disagree             |
      | finalDecision          | Approved             |
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
    And Wait for 250 seconds
    And I click on id of "Just Cause" in Links section of "TASK" page
    And I store sr id on edit page
    And I click on id of "Just Cause Follow-up Email" in Links section of "SR" page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                    |
      | reasonForEdit | Corrected Data Entry        |
      | actionTaken   | Sent Follow-up Email to MCE |
    And I click on save button on task edit page
    When I navigate to "Just Cause Resolution" task page
    And I will provide following information before creating task
      | assignee | Service TesterTwo |
      | priority | 2                 |
    And I click on save button on task edit page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on first task id in my task
    And I click on edit button on view task page
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I will search with srId
    And I click on Link button in task search
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "TASK" page
    Then I verify "Just Cause" contains : "Just Cause Request,Just Cause Follow-up Email,Just Cause Resolution" on "SR" link section
    When I click id of "Just Cause Resolution" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                              |
      | reasonForEdit | Corrected Data Entry                  |
      | actionTaken   | Contacted Member                      |
      | disposition   | Contact Member - Successfully Reached |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    Then I verify "Just Cause" contains : "Just Cause Request,Just Cause Follow-up Email,Just Cause Resolution,Just Cause State Discussion" on "SR" link section
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
    Then I verify "Just Cause" contains : "Just Cause Request,Just Cause Follow-up Email,Just Cause Resolution,Just Cause State Discussion,Just Cause Decision Letter" on "SR" link section
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
    Then I verify the sr status is updated to close and disposition is set to "Complete - Approved"
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I initiated bpm process get api by service request id for process name "CauseAppSRFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-36910 @CP-36910-01 @crm-regression @sr-camunda @ruslan
  Scenario: Suppress Just Cause Follow Up Task When Date Received Grievance is Added or Just Cause Resolution task is linked to the SR
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "NANCY" and Last Name as "ELIAS"
    When I click the first consumer id from the search results
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                    | 521521521512         |
      | grievance              | 3253252355           |
      | contactName            | Alewa                |
      | contactPhone           | 5215215215           |
      | currentPlan            | Anthem               |
      | desiredPlanJC          | Anthem               |
      | programRequired        | Healthy Indiana Plan |
      | missingInfoRequired    | No                   |
      | dateReceivedGrievance  | today                |
      | dateFollowupEmailSent  | today                |
      | dateSateNotified       | today                |
      | dateDecisionLetterSent | today                |
      | consumerSatisfied      | Yes                  |
      | decision               | Disagree             |
      | finalDecision          | Approved             |
    And I click on save button in create service request page
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
    And Wait for 250 seconds
    And I click on id of "Just Cause" in Links section of "TASK" page
    Then I verify "Just Cause" contains : "Just Cause Request,Just Cause Resolution" on "SR" link section
    When I click id of "Just Cause Resolution" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                       |
      | reasonForEdit | Corrected Data Entry           |
      | actionTaken   | Contacted Member               |
      | disposition   | Contact Member - Did Not Reach |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    And I click on id of "JC Outbound Call" in Links section of "Task" page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                                          |
      | reasonForEdit | Corrected Data Entry                              |
      | actionTaken   | Contacted Consumer - Did Not Reach/Left Voicemail |
      | contactName   | Alewa                                             |
      | disposition   | Did Not Reach Consumer                            |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                                          |
      | reasonForEdit | Corrected Data Entry                              |
      | actionTaken   | Contacted Consumer - Did Not Reach/Left Voicemail |
      | contactName   | Alewa                                             |
      | disposition   | Did Not Reach Consumer                            |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                                          |
      | reasonForEdit | Corrected Data Entry                              |
      | actionTaken   | Contacted Consumer - Did Not Reach/Left Voicemail |
      | contactName   | Alewa                                             |
      | disposition   | Did Not Reach Consumer                            |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    Then I verify link section contains 3 "JC Outbound Call"
    Then I verify "Just Cause" contains : "Just Cause Request,Just Cause Resolution,JC Outbound Call,JC Outbound Call,JC Outbound Call,Just Cause State Discussion" on "SR" link section
    When I click id of "Just Cause State Discussion" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Contacted Member     |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    Then I verify "Just Cause" contains : "Just Cause Request,Just Cause Resolution,JC Outbound Call,JC Outbound Call,JC Outbound Call,Just Cause State Discussion,Just Cause Decision Letter" on "SR" link section
    When I click id of "Just Cause Decision Letter" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Sent Approval Letter |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    And I store sr id on edit page
    Then I verify the sr status is updated to close and disposition is set to "Complete - Approved"
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I initiated bpm process get api by service request id for process name "CauseAppSRFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions