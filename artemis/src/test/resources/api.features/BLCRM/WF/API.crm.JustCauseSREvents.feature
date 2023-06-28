Feature: Just Cause SR Flow Events for IN-EB

  @CP-23612 @CP-23612-03 @ruslan @events-wf
  Scenario Outline: IN-EB: Trigger Workflow for Just Cause SR when initiated via UI
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click case consumer search tab
    When I searched customer have First Name as "CARINA" and Last Name as "ELI"
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                     | 521521521512                            |
      | grievance               | 521521521512                            |
      | contactName             | Alewa                                   |
      | desiredPlanJC           | Anthem                                  |
      | reasonExplanation       | Receiving Poor Quality of Care          |
      | explanation             | Limited Access to a Primary Care Clinic |
      | programRequired         | Healthy Indiana Plan                    |
      | contactPhone            | 521-521-5215                            |
      | AREmail                 | VASILIY@GMAIL.COM                       |
      | currentPlan             | Anthem                                  |
      | taskInfo                | Test CP-23613                           |
      | missingInfoRequired     | No                                      |
      | dateHealthPlanContacted | today                                   |
      | dateReceivedGrievance   | today                                   |
      | invalid                 | true                                    |
      | dateFollowupEmailSent   | today                                   |
      | decision                | Agree                                   |
      | dateDecisionLetterSent  | today                                   |
      | dateSateNotified        | today                                   |
      | newPlanStartDate        | today                                   |
      | finalDecision           | Approved                                |
      | consumerSatisfied       | Yes                                     |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated bpm process get api by service request id for process name "CauseAppSRFlow"
    And I run bpm process get api by sr id
    And I will check response of service request bpm process
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for SR has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId | projectName |
      | TASK_SAVE_EVENT | Open          | IN-EB       |

  @CP-23613 @CP-23613-02 @ruslan @events-wf
  Scenario Outline: IN-EB: Create Just Cause Request task
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                     | 521521521512                            |
      | grievance               | 521521521512                            |
      | contactName             | Alewa                                   |
      | desiredPlanJC           | Anthem                                  |
      | reasonExplanation       | Receiving Poor Quality of Care          |
      | explanation             | Limited Access to a Primary Care Clinic |
      | programRequired         | Healthy Indiana Plan                    |
      | contactPhone            | 521-521-5215                            |
      | AREmail                 | VASILIY@GMAIL.COM                       |
      | currentPlan             | Anthem                                  |
      | taskInfo                | Test CP-23613                           |
      | missingInfoRequired     | No                                      |
      | dateHealthPlanContacted | today                                   |
      | dateReceivedGrievance   | today                                   |
      | invalid                 | true                                    |
      | dateFollowupEmailSent   | today                                   |
      | decision                | Agree                                   |
      | dateDecisionLetterSent  | today                                   |
      | dateSateNotified        | today                                   |
      | newPlanStartDate        | today                                   |
      | finalDecision           | Approved                                |
      | consumerSatisfied       | Yes                                     |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated search records API
    Then I validate serviceRequestInd value for "TASK"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for Task has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId | projectName |
      | TASK_SAVE_EVENT | Open          | IN-EB       |

  @CP-23614 @CP-23614-02 @ruslan @events-wf
  Scenario Outline: IN-EB: validate LINK_EVENTS generated contact/consumer/case/task for Just Cause
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "CARINA" and Last Name as "TREVEON"
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
    And I click on save button in create service request page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "Active contact with case/consumer/task"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName  | correlationId | projectName |
      | LINK_EVENT | Open          | IN-EB       |

  @CP-23615 @CP-23615-02 @ruslan @events-wf
  Scenario Outline: IN-EB: validate Link Events generated consumer/case/sr/task for Just Cause Request
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                   | 521521521512                                 |
      | contactName           | Alewa                                        |
      | contactPhone          | 5215215215                                   |
      | currentPlan           | Anthem                                       |
      | desiredPlanJC         | Anthem                                       |
      | programRequired       | Healthy Indiana Plan                         |
      | missingInfoRequired   | No                                           |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I searched customer have First Name as "NANCY" and Last Name as "VENANCIO"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    When I select Consumer radio button
    When I click on Link Record Consumer button
    And I click on save button in create service request page
    When I click case consumer search tab
    When I searched customer have First Name as "NANCY" and Last Name as "VENANCIO"
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "TASK" from "Outside of contact with case/consumer/sr"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName  | correlationId | projectName |
      | LINK_EVENT | Open          | IN-EB       |

  @CP-24094 @CP-24094-02 @ruslan @events-wf
  Scenario Outline: IN-EB: validate TASK SAVE EVENT generated for JC Outbound
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click case consumer search tab
    When I searched customer have First Name as "CARINA" and Last Name as "JIMMY"
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                 | 521521521512                            |
      | contactName         | Alewa                                   |
      | contactPhone        | 521-521-5215                            |
      | currentPlan         | Anthem                                  |
      | desiredPlanJC       | Anthem                                  |
      | programRequired     | Healthy Indiana Plan                    |
      | reasonExplanation   | Receiving Poor Quality of Care          |
      | explanation         | Limited Access to a Primary Care Clinic |
      | invalid             | false                                   |
      | missingInfoRequired | Yes                                     |
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
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId | projectName |
      | TASK_SAVE_EVENT | Task_update   | IN-EB       |

  @CP-24095 @CP-24095-02 @CP-28211 @CP-28211-03 @ruslan @kamil @events-wf
  Scenario Outline: IN-EB: validate Link Events generated for JC Outbound
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click case consumer search tab
    When I searched customer have First Name as "CARINA" and Last Name as "JIMMY"
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid               | 521521521512                                 |
      | contactName       | Alewa                                        |
      | contactPhone      | 521-521-5215                                 |
      | currentPlan       | Anthem                                       |
      | desiredPlanJC     | Anthem                                       |
      | reasonExplanation | FSSA Took Corrective Action Against the Plan |
      | explanation       | Limited Access to a Primary Care Clinic      |
      | programRequired   | Healthy Indiana Plan                         |
      | invalid           | true                                         |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I scroll up the page
    When I click id of "Just Cause Request" in Links section
    Then I verify "Just Cause Request" task fields are displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | No Action Taken      |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    And I store sr id on edit page
    And I click on id of "JC Outbound Call" in Links section of "Service Request" page
    And I store task id on edit page
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify "LINK_EVENT" generated for "JC Outbound Call" task : "TASK TO SERVICE_REQUEST,SERVICE_REQUEST TO TASK,TASK TO CONSUMER,CONSUMER TO TASK,CASE TO TASK,TASK TO CASE"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName  | correlationId | projectName |
      | LINK_EVENT | Task_update   | IN-EB       |

  @CP-24096 @CP-24096-02 @CP-24961 @CP-24961-02 @ruslan @events-wf
  Scenario Outline: IN-EB: Validate Save event for 2nd and 3rd call attempt for Just Cause SR
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click case consumer search tab
    When I searched customer have First Name as "CARINA" and Last Name as "AXELAREDNA"
    When I expand the record to navigate Case Consumer Record from manual view
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
      | invalid               | true                                         |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    When I click id of "Just Cause Request" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | No Action Taken      |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    When I click id of "Just Cause" in Links section
    When I click id of "JC Outbound Call" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                          |
      | reasonForEdit  | Corrected Data Entry                              |
      | disposition    | Did Not Reach Consumer                            |
      | preferredPhone | 1234567890                                        |
      | actionTaken    | Contacted Consumer - Did Not Reach/Left Voicemail |
      | contactName    | Alewa                                             |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    When I click id of "Just Cause" in Links section
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
#    Then I verify Success message is displayed for task update
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    When I click id of "Just Cause" in Links section
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
#    Then I verify Success message is displayed for task update
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId | projectName |
      | TASK_UPDATE_EVENT | Task_update   | IN-EB       |

  @CP-22676 @CP-22676-04 @CP-29557 @CP-29557-04 @priyal @ruslan @events-wf
  Scenario Outline: Validate LINK_EVENT & UNLINK_EVENT for Manually Link a Task to a Service Request
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout | maersk          |
      | incidentDate   | today            |
      | memberName     | Priyal           |
      | preferredPhone | 1234567890       |
      | complaintType  | Customer Service |
      | priority       | 3                |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    When I navigate to "DCS Request" task page
    And I will provide following information before creating task
      | taskInfo | Test CP-22676     |
      | assignee | Service TesterTwo |
      | priority | 3                 |
    And I click on save button in create task page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","Customer Service Complaint","Open","<statusDate>","3","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","true","<consumerFN>","<consumerLN>","<source>","<assignee>","Service TesterTwo","today","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I click on cancel button on task search page
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I populate required fields to do task search "<taskId>","DCS Request","<srType>","Created","<statusDate>","3","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","true","<consumerFN>","<consumerLN>","<source>","Service TesterTwo","<createdBy>","today","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And I click task id to get the results in descending order in Task Search
    And I scroll down the page
    And I click on Link button in task search
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    And I store sr id on edit page
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "manually_link_sr_to_task"
    And I will run the Event GET API and get the payload
    And I verify "LINK_EVENT" generated for "Customer Service Complaint" task : "TASK TO SERVICE_REQUEST,SERVICE_REQUEST TO TASK"
    And I will check "LINK_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "LINK_EVENT" publish to DPBI
    And I click on edit service request button
    And I will update the following information in edit task page
      | taskInfo      | Un linking DCS Request from Customer Service Complaint SR |
      | reasonForEdit | Corrected Data Entry                                      |
    And I unlink "DCS Request" from link section
    And I click on save button on task edit page
    And I initiate Event get api for trace id "manually_unlink_sr_from_task"
    And I will run the Event GET API and get the payload
    And I verify "UNLINK_EVENT" generated for "Customer Service Complaint" task : "TASK TO SERVICE_REQUEST,SERVICE_REQUEST TO TASK"
    And I will check "UNLINK_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "UNLINK_EVENT" publish to DPBI
    Examples:
      | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | projectName |contactReason|
      ||          ||        ||          ||            ||                || true          ||            ||          || today     | IN-EB       ||

  @CP-24964 @CP-24964-02 @CP-26865 @CP-26865-02 @CP-24966 @CP-24966-02 @CP-26867 @CP-26867-02 @CP-26866 @CP-26866-04 @sr-camunda-events @ruslan
  Scenario Outline: Validate LINK_EVENT for Follow-Up Email Task | Just Cause Resolution Task | Just Cause State Discussion | Just Cause Decision Letter
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click case consumer search tab
    When I searched customer have First Name as "NANCY" and Last Name as "ABELARDO"
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                    | 521521521512                                 |
      | contactName            | Alewa                                        |
      | contactPhone           | 5215215215                                   |
      | currentPlan            | Anthem                                       |
      | desiredPlanJC          | Anthem                                       |
      | programRequired        | Healthy Indiana Plan                         |
      | missingInfoRequired    | No                                           |
      | reasonExplanation      | FSSA Took Corrective Action Against the Plan |
      | explanation            | Limited Access to a Primary Care Clinic      |
      | decision               | Agree                                        |
      | consumerSatisfied      | Yes                                          |
      | dateFollowupEmailSent  | today                                        |
      | dateDecisionLetterSent | today                                        |
      | dateSateNotified       | today                                        |
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
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "Task_update"
    And I will run the Event GET API and get the payload
    And I verify "<eventName>" generated for "Just Cause Follow-up Email" task : "TASK TO SERVICE_REQUEST,SERVICE_REQUEST TO TASK,TASK TO CONSUMER,CONSUMER TO TASK,CASE TO TASK,TASK TO CASE"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    And I click on id of "Just Cause" in Links section of "Task" page
    When I click id of "Just Cause Follow-up Email" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                    |
      | reasonForEdit | Corrected Data Entry        |
      | actionTaken   | Sent Follow-up Email to MCE |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "Just Cause" in Links section of "Task" page
    Then I verify "Just Cause" contains : "Just Cause Follow-up Email,Just Cause Request,Just Cause Resolution" on "SR" link section
    And I store sr id on edit page
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "actionTaken"
    And I will run the Event GET API and get the payload
    And I verify "<eventName>" generated for "Just Cause Resolution" task : "TASK TO SERVICE_REQUEST,SERVICE_REQUEST TO TASK,TASK TO CONSUMER,CONSUMER TO TASK,CASE TO TASK,TASK TO CASE"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    When I click id of "Just Cause Resolution" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                              |
      | reasonForEdit | Corrected Data Entry                  |
      | actionTaken   | Contacted Member                      |
      | disposition   | Contact Member - Successfully Reached |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    Then I verify "Just Cause" contains : "Just Cause Follow-up Email,Just Cause Request,Just Cause Resolution,Just Cause State Discussion" on "SR" link section
    And I store sr id on edit page
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "actionTakenContactMember"
    And I will run the Event GET API and get the payload
    And I verify "<eventName>" generated for "Just Cause State Discussion" task : "TASK TO SERVICE_REQUEST,SERVICE_REQUEST TO TASK,TASK TO CONSUMER,CONSUMER TO TASK,CASE TO TASK,TASK TO CASE"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    When I click id of "Just Cause State Discussion" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Discussed With State |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    Then I verify "Just Cause" contains : "Just Cause Follow-up Email,Just Cause Request,Just Cause Resolution,Just Cause State Discussion,Just Cause Decision Letter" on "SR" link section
    And I store sr id on edit page
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "actionTakenDiscussedWithState"
    And I will run the Event GET API and get the payload
    And I verify "<eventName>" generated for "Just Cause State Discussion" task : "TASK TO SERVICE_REQUEST,SERVICE_REQUEST TO TASK,TASK TO CONSUMER,CONSUMER TO TASK,CASE TO TASK,TASK TO CASE"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName  | projectName |
      | LINK_EVENT | IN-EB       |

  @CP-28460 @CP-28460-02 @sr-camunda-events @ruslan
  Scenario Outline: Validate TASK_SAVE_EVENT is created for this JC Outbound Call Task
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click case consumer search tab
    When I searched customer have First Name as "NANCY" and Last Name as "ABELARDO"
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                   | 521521521512                                 |
      | contactName           | Alewa                                        |
      | contactPhone          | 5215215215                                   |
      | currentPlan           | Anthem                                       |
      | dateFollowupEmailSent | today                                        |
      | desiredPlanJC         | Anthem                                       |
      | programRequired       | Healthy Indiana Plan                         |
      | missingInfoRequired   | No                                           |
      | reasonExplanation     | FSSA Took Corrective Action Against the Plan |
      | explanation           | Limited Access to a Primary Care Clinic      |
      | decision              | Disagree                                     |
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
    And I click on id of "Just Cause" in Links section of "Task" page
    When I click id of "Just Cause Follow-up Email" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                    |
      | reasonForEdit | Corrected Data Entry        |
      | actionTaken   | Sent Follow-up Email to MCE |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "Just Cause" in Links section of "Task" page
    When I click id of "Just Cause Resolution" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                       |
      | reasonForEdit | Corrected Data Entry           |
      | actionTaken   | Contacted Member               |
      | disposition   | Contact Member - Did Not Reach |
    And I click on save button on task edit page
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
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
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
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
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
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
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_UPDATE_EVENT for Task has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId | projectName |
      | TASK_UPDATE_EVENT | Task_update   | IN-EB       |

  @CP-26864 @CP-26864-03 @CP-28646 @CP-28646-02 @sr-camunda-events @ruslan
  Scenario Outline: Validate TASK_UPDATE_EVENT is generated end Just cause SR after Send Just Cause Decision Letter Task is complete
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click case consumer search tab
    When I searched customer have First Name as "NANCY" and Last Name as "ABELARDO"
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
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
      | reasonExplanation      | FSSA Took Corrective Action Against the Plan |
      | explanation            | Limited Access to a Primary Care Clinic      |
      | decision               | <decision>                                   |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I initiate Event get api for trace id "Open"
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
    And I click on id of "Just Cause" in Links section of "Task" page
    When I click id of "Just Cause Follow-up Email" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                    |
      | reasonForEdit | Corrected Data Entry        |
      | actionTaken   | Sent Follow-up Email to MCE |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "Just Cause" in Links section of "Task" page
    When I click id of "Just Cause Resolution" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Contacted Member     |
      | disposition   | <disposition>        |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
      | finalDecision | <finalDecision>      |
    And I click on save button on task edit page
    When I click id of "Just Cause State Discussion" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Contacted Member     |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click on id of "Just Cause" in Links section of "Task" page
    When I click id of "Just Cause Decision Letter" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Sent Approval Letter |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I initiate Event get api for trace id "actionTakenSentApprovalLetter"
    And I will run the Event GET API and get the payload
    And I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I will check "TASK_UPDATE_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "TASK_UPDATE_EVENT" publish to DPBI
    Examples:
      | decision             | disposition                           | finalDecision |
      | Disagree             | Contact Member - Successfully Reached | Approved      |
      | Agree                | Contact Member - Successfully Reached | Denied        |
      | No Response Received | Did Not Contact Member                | Withdrawn     |

  @CP-24965 @CP-24965-02 @CP-24962 @CP-24962-02 @sr-camunda-events @ruslan
  Scenario: Validate SAVE_EVENT for Just Cause Follow-up Email | Just Cause Resolution Task
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click case consumer search tab
    When I searched customer have First Name as "NANCY" and Last Name as "ABELARDO"
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                   | 521521521512                                 |
      | contactName           | Alewa                                        |
      | contactPhone          | 5215215215                                   |
      | currentPlan           | Anthem                                       |
      | desiredPlanJC         | Anthem                                       |
      | programRequired       | Healthy Indiana Plan                         |
      | missingInfoRequired   | No                                           |
      | reasonExplanation     | FSSA Took Corrective Action Against the Plan |
      | explanation           | Limited Access to a Primary Care Clinic      |
      | decision              | Disagree                                     |
      | dateFollowupEmailSent | today                                        |
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
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I initiate Event get api for trace id "Task_update"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    And I will check "TASK_SAVE_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "TASK_SAVE_EVENT" publish to DPBI
    And I click on id of "Just Cause" in Links section of "Task" page
    When I click id of "Just Cause Follow-up Email" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                    |
      | reasonForEdit | Corrected Data Entry        |
      | actionTaken   | Sent Follow-up Email to MCE |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I initiate Event get api for trace id "actionTaken"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    And I will check "TASK_SAVE_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "TASK_SAVE_EVENT" publish to DPBI

  @CP-26866 @CP-26866-02 @ruslan @events-wf
  Scenario Outline: IN-EB: Verify TASK_SAVE_EVENT for Send Just Cause Decision Letter Task if user marks SR Withdrawn and the task was not created yet
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "<projectName>"
    And I minimize Genesys popup if populates
    When I click case consumer search tab
    When I searched customer have First Name as "CARINA" and Last Name as "URIAH"
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                 | 521521521512         |
      | contactName         | Alewa                |
      | contactPhone        | 5215215215           |
      | currentPlan         | Anthem               |
      | desiredPlanJC       | Anthem               |
      | programRequired     | Healthy Indiana Plan |
      | missingInfoRequired | Yes                  |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
      | finalDecision | Withdrawn            |
    And I click on save button on task edit page
    Then I verify "Just Cause" contains : "Just Cause Request,Just Cause Decision Letter" on "SR" link section
    When I click id of "Just Cause Decision Letter" in Links section
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "<eventName>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | projectName |
      | TASK_SAVE_EVENT | IN-EB       |