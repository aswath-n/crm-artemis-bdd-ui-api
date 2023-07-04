Feature: Validation of Application Renewal SR

  @CP-18639 @CP-18639-1 @CP-25316 @CP-25316-10 @basha @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Initialize workflow when Application Renewal SR is created through CP UI
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "App Renewal SR V1" service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-18639           |
      | applicationType       | Ex Parte Renewal - CVIU |
      | externalApplicationId | random                  |
    And I click on save button in create service request page
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    When I expand the first row in task list
    And I verify Task Notes field is not displayed on Task List expanded view page
    And I expend first Task from the list by clicking in Task ID
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal"
    And I run bpm process get api by sr id
    And I will check response of service request bpm process

  @CP-18639 @CP-18639-03 @CP-19448 @CP-19448-03 @CP-19452 @CP-19452-02 @CP-19480 @CP-19480-01 @CP-25316 @CP-25316-02 @vidya @basha @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Verify App Renewal SR V1 camunda flow and when cancel the missing info task app Renewal sr v1 is closed and disposition set to cancelled
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "App Renewal SR V1" service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-18639           |
      | applicationType       | Ex Parte Renewal - CVIU |
      | externalApplicationId | random                  |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Jenny" and Last Name as "John"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    When I click on Link Record Consumer button
    Then I verify task is linked with Consumer ID
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When I click case consumer search tab
    When I searched customer have First Name as "Jenny" and Last Name as "John"
    When I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Application Renewal SR" link section has all the business object linked : "Task,Consumer"
    And I click id of "Process App V1" in Links section
    And I click on edit button on view task page
    And I verify Task Notes field is not displayed on Edit SR page
    And I will update the following information in edit task page
      | status                    | Complete             |
      | reasonForEdit             | Corrected Data Entry |
      | myWorkSpaceDate           | -2                   |
      | applicationReceivedDate   | today                |
      | applicationSignatureDate  | today                |
      | noOfApplicantsInHousehold | 1                    |
      | channel                   | Paper                |
      | applicationStatus         | Data Collection      |
      | actionTaken               | Sent VCL             |
      | disposition               | Pending MI           |
      | decisionSource            | Manual               |
      | noOfApprovedApplicants    | 1                    |
      | missingInfoRequired       | Yes                  |
    And I click on save button on task edit page
    And I click id of "App Renewal SR V1" in Links section
    And I click id of "Missing Information" in Links section
    Then I verify "Missing Information" task fields are displayed
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify latest task displayed in "Work Queue or My Task" page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And In search result click on task id to navigate to view page
    Then I Verify Task Notes field is not displayed on View SR page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | Cancelled                                                |
      | reasonForCancel | Created Incorrectly                                      |
      | reasonForEdit   | Corrected Data Entry                                     |
      | documentDueDate | today                                                    |
      | receivedDate    | today                                                    |
      | InbDocType      | Earned Income                                            |
      | actionTaken     | Escalated to Supervisor                                  |
      | taskInfo        | Testing Cancel functionality of Final Application Review |
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I click id of "App Renewal SR V1" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Cancelled"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  @CP-18639 @CP-18639-04 @CP-19448 @CP-19448-04 @CP-19456 @CP-19456-01 @CP-19452 @CP-19452-03 @CP-19482 @CP-19482-01 @vidya @basha @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Verify Missing information task is created with due date 8 calendar days and assign to current your and  End App Renewal SR V1 when Missing Information Task is Complete
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "App Renewal SR V1" service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-18639            |
      | applicationType       | Renewal Application CVIU |
      | externalApplicationId | random                   |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Application Renewal SR" link section has all the business object linked : "Task,Consumer"
    And I click id of "Process App V1" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                    | Complete             |
      | reasonForEdit             | Corrected Data Entry |
      | assignee                  | Service TesterTwo    |
      | taskInfo                  | Test 123             |
      | myWorkSpaceDate           | -2                   |
      | applicationReceivedDate   | today                |
      | applicationSignatureDate  | today                |
      | noOfApplicantsInHousehold | 1                    |
      | channel                   | Paper                |
      | applicationStatus         | Data Collection      |
      | actionTaken               | Sent VCL             |
      | disposition               | Pending MI           |
      | decisionSource            | Manual               |
      | noOfApprovedApplicants    | 1                    |
      | missingInfoRequired       | Yes                  |
    And I click on save button on task edit page
    And I click id of "App Renewal SR V1" in Links section
    And I click id of "Missing Information" in Links section
    Then I verify "Missing Information" task fields are displayed
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I verify latest task displayed in "Work Queue or My Task" page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | Complete                                            |
      | reasonForEdit   | Corrected Data Entry                                |
      | documentDueDate | today                                               |
      | receivedDate    | today                                               |
      | InbDocType      | Earned Income                                       |
      | actionTaken     | Escalated to Supervisor                             |
      | taskInfo        | Testing Complete functionality of Missinf info task |
      | denialReason    | Existing Coverage                                   |
      | disposition     | Denied                                              |
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I click id of "App Renewal SR V1" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Denied"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  @CP-18639 @CP-18639-05 @CP-19443 @CP-19443-01 @vidya @basha @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Create Application Renewal SR and link to CR and Task and Match App Renewal SR V1 disposition to Process App V1 Task disposition when Process App task has 'Sent NOA' Action
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I navigate to "App Renewal SR V1" service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-18639            |
      | applicationType       | Renewal Application CVIU |
      | externalApplicationId | random                   |
    And I click on save button in create service request page
    And I click on srId in active contact page and navigate to view sr details page
    Then I verify "Application Renewal SR" link section has all the business object linked : "Task,Contact Record"
    And I Navigate to Contact Record details page
    Then I verify link section has "Application Renewal SR" and other business object
    And I click on Active Contact widget
    Then I verify link section has "Application Renewal SR" and other business object
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And In search result click on task id to navigate to view page
    And I click id of "Process App V1" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                    | Complete             |
      | reasonForEdit             | Corrected Data Entry |
      | taskInfo                  | Test 123             |
      | myWorkSpaceDate           | -2                   |
      | applicationReceivedDate   | today                |
      | applicationSignatureDate  | today                |
      | noOfApplicantsInHousehold | 1                    |
      | channel                   | Paper                |
      | applicationStatus         | Data Collection      |
      | actionTakenSingle         | Sent NOA             |
      | disposition               | Approved             |
      | decisionSource            | Manual               |
      | noOfApprovedApplicants    | 1                    |
      | missingInfoRequired       | Yes                  |
    And I click on save button on task edit page
    And I click id of "App Renewal SR V1" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Approved"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  @CP-18642 @CP-18642-03 @CP-19482 @CP-19482-02 @vidya @basha @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Verify Process Application task when CR and Consumer is linked and End App Renewal SR V1 when Missing Information Task is Complete
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "John" and Last Name as "Mathew"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "App Renewal SR V1" service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-18642 |
      | applicationType       | MAGI - CPU    |
      | externalApplicationId | random        |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Application Renewal SR" link section has all the business object linked : "Task,Contact Record,Consumer"
    And I click id of "Process App V1" in Links section
    And I verify process application task fields displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                    | Complete             |
      | reasonForEdit             | Corrected Data Entry |
      | taskInfo                  | Test 123             |
      | myWorkSpaceDate           | -2                   |
      | applicationReceivedDate   | today                |
      | applicationSignatureDate  | today                |
      | noOfApplicantsInHousehold | 1                    |
      | channel                   | Paper                |
      | applicationStatus         | Data Collection      |
      | actionTaken               | Sent VCL             |
      | disposition               | Pending MI           |
      | decisionSource            | Manual               |
      | noOfApprovedApplicants    | 1                    |
      | missingInfoRequired       | Yes                  |
    And I click on save button on task edit page
    And I click id of "App Renewal SR V1" in Links section
    And I click id of "Missing Information" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | Complete                                            |
      | reasonForEdit   | Corrected Data Entry                                |
      | documentDueDate | today                                               |
      | receivedDate    | today                                               |
      | InbDocType      | Earned Income                                       |
      | actionTaken     | Escalated to Supervisor                             |
      | taskInfo        | Testing Complete functionality of Missinf info task |
      | disposition     | Approved                                            |
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I click id of "App Renewal SR V1" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Approved"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

#  @CP-18642 @CP-18642-06 @CP-19479 @CP-19479-04 @vidya @basha @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
#  Process Application Task do not have Cancelled status since it's created systematically
  Scenario: Verify Process Application task in Work Queue/Task Search/TSR tabs and End Application Renewal SR when Process Application Task is cancelled
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "App Renewal SR V1" service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-18639 |
      | applicationType       | MAGI - CPU    |
      | externalApplicationId | random        |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Jenny" and Last Name as "John"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    When I click on Link Record Consumer button
    Then I verify task is linked with Consumer ID
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When I click case consumer search tab
    When I searched customer have First Name as "Jenny" and Last Name as "John"
    When I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to newly created task by clicking on TaskID column header
    And I verify latest task displayed in "Task & Service Request" page
    And I navigate to view SR details page by clicking on sr id
    And I click id of "Process App V1" in Links section
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify latest task displayed in "Work Queue or My Task" page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                    | Cancelled            |
      | reasonForCancel           | Created Incorrectly  |
      | reasonForEdit             | Corrected Data Entry |
      | taskInfo                  | Test 123             |
      | myWorkSpaceDate           | -2                   |
      | applicationReceivedDate   | today                |
      | applicationSignatureDate  | today                |
      | noOfApplicantsInHousehold | 1                    |
      | channel                   | Paper                |
      | applicationStatus         | Data Collection      |
      | actionTaken               | Sent VCL             |
      | decisionSource            | Manual               |
      | noOfApprovedApplicants    | 1                    |
      | missingInfoRequired       | Yes                  |
    And I click on save button on task edit page
 #   Then I verify Success message is displayed for task update
    And I click id of "App Renewal SR V1" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Cancelled"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  @CP-18642 @CP-18642-04 @CP-19443 @CP-19443-02 @vidya @basha @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Application Renewal Service Request is created from ECMS end point and Match App Renewal SR V1 disposition to Process App V1 Task disposition when Process App task has 'Sent NOA' Action
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Application Renewal"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | VACV Application Renewal |
    When I send the request to create the Inbound Correspondence Save Event related to SR
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    And I click id of "Process App V1" in Links section
    And I verify process application task fields displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                    | Complete             |
      | reasonForEdit             | Corrected Data Entry |
      | applicationType           | MAGI - PW            |
      | externalApplicationId     | random               |
      | taskInfo                  | Test 123             |
      | myWorkSpaceDate           | -2                   |
      | applicationReceivedDate   | today                |
      | applicationSignatureDate  | today                |
      | noOfApplicantsInHousehold | 1                    |
      | channel                   | Paper                |
      | applicationStatus         | Data Collection      |
      | actionTakenSingle         | Sent NOA             |
      | disposition               | Auto-Denied          |
      | decisionSource            | Manual               |
      | noOfApprovedApplicants    | 1                    |
      | missingInfoRequired       | Yes                  |
    And I click on save button on task edit page
    And I click id of "App Renewal SR V1" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Auto-Denied"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  @CP-18642 @CP-18642-05 @CP-19443 @CP-19443-03 @CP-21397 @CP-21397-02 @vidya @basha @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario Outline:Verify Process Application task created from external end point with Inbound doc and Consumer and Match App Renewal SR V1 disposition to Process App Task disposition when Process App task has 'Transfer to LDSS' Action
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I will get the Authentication token for "CoverVA" in "CRM"
    And Get the task type information of "App Renewal SR V1" for project ""
    And I will provide required information to create external sr with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      | Disposition |
    When I initiated external create sr api
    And I run the create external sr API and check the status code is "200"
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal"
    And I run bpm process get api by sr id
    Then I will check response of service request bpm process
    And I click id of "Process App V1" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                    | Complete             |
      | reasonForEdit             | Corrected Data Entry |
      | applicationType           | MAGI - PW            |
      | externalApplicationId     | Tgh6789              |
      | taskInfo                  | Test 123             |
      | myWorkSpaceDate           | -2                   |
      | applicationReceivedDate   | today                |
      | applicationSignatureDate  | today                |
      | noOfApplicantsInHousehold | 1                    |
      | channel                   | Paper                |
      | applicationStatus         | Data Collection      |
      | actionTakenSingle         | Transferred to LDSS  |
      | disposition               | Referred             |
      | decisionSource            | Manual               |
      | noOfApprovedApplicants    | 1                    |
      | missingInfoRequired       | Yes                  |
    And I click on save button on task edit page
    And I click id of "App Renewal SR V1" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Referred"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    Examples:
      | caseId | consumerId | inboundId | taskInfo |
      || 8          | 53091     ||

  @CP-22069 @CP-22069-02 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Renewal SR link to CR, consumer and Task
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-22069-02 |
      | applicationType       | MAGI - PW        |
      | applicationSignatureDate | today         |
      | applicationReceivedDate  | today         |
      | externalApplicationId | TTT123410        |
      | myWorkSpaceDate       | today            |
      | channel               | CommonHelp       |
    And I click on save button in create service request page
    Then I verify system returns an error "Duplicate Application Id found. Please correct the Application Id"

  @CP-19478 @CP-19478-01 @CP-19478-02 @CP-19484 @CP-19484-01 @priyal @chandrakumar @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario Outline: Validate of Linking Missing Information Task and consumer to App Renewal SR V1 Service Request
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "App Renewal SR V1" service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-19478            |
      | applicationType       | Renewal Application CVIU |
      | externalApplicationId | random                   |
      | priority              | 5                        |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click id of "Process App V1" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                    | Complete             |
      | reasonForEdit             | Corrected Data Entry |
      | assignee                  | Service TesterTwo    |
      | taskInfo                  | Test 123             |
      | myWorkSpaceDate           | -2                   |
      | applicationReceivedDate   | today                |
      | applicationSignatureDate  | today                |
      | noOfApplicantsInHousehold | 1                    |
      | channel                   | CommonHelp           |
      | applicationStatus         | Data Collection      |
      | actionTaken               | Sent VCL             |
      | disposition               | Pending MI           |
      | decisionSource            | Manual               |
      | missingInfoRequired       | Yes                  |
      | noOfApprovedApplicants    | 1                    |
    And I click on save button on task edit page
    And I click id of "App Renewal SR V1" in Links section
    And I click id of "Missing Information" in Links section
    Then I verify "Missing Information" link section has all the business object linked : "Service Request"
    And I click id of "App Renewal SR V1" in Links section
    Then I verify "App Renewal SR V1" link section has all the business object linked : "Task,Task"
    And I click on edit service request button
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Jenny" and Last Name as "John"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    When I click on Link Record Consumer button
    Then I verify task is linked with Consumer ID
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    And I click id of "Missing Information" in Links section
    Then I verify "Missing Information" link section has all the business object linked : "Service Request,Consumer"
    And I click id of "App Renewal SR V1" in Links section
    Then I verify "App Renewal SR V1" link section has all the business object linked : "Task,Task,Consumer"
    And I click on edit service request button
    And I will update the following information in edit task page
      | status        | Closed               |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Cancelled            |
    And I click on save button on task edit page
    Then I verify "Missing Information" task status is updated to "Cancelled" on the link section
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType            | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | App Renewal SR V1 | Open   || 5        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

  @CP-19484 @CP-19484-03 @priyal @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario Outline: Verify Missing Information Task moved to Cancelled statu when we manually close the SR
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "App Renewal SR V1" service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-19478            |
      | applicationType       | Renewal Application CVIU |
      | externalApplicationId | random                   |
      | priority              | 4                        |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click id of "Process App V1" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                    | Complete             |
      | reasonForEdit             | Corrected Data Entry |
      | myWorkSpaceDate           | -2                   |
      | applicationReceivedDate   | today                |
      | applicationSignatureDate  | today                |
      | noOfApplicantsInHousehold | 1                    |
      | channel                   | Paper                |
      | applicationStatus         | Data Collection      |
      | actionTaken               | Sent VCL             |
      | disposition               | Pending MI           |
      | noOfApprovedApplicants    | 23|
      | decisionSource            | Manual|
      | missingInfoRequired       | No|
    And I click on save button on task edit page
    And I click id of "App Renewal SR V1" in Links section
    And I click id of "Missing Information" in Links section
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | Escalated                                         |
      | reasonForEdit   | Corrected Data Entry                              |
      | documentDueDate | today                                             |
      | receivedDate    | today                                             |
      | InbDocType      | Earned Income                                     |
      | actionTaken     | Escalated to Supervisor                           |
      | taskInfo        | Testing Cancel functionality of Missing Info Task |
    And I click on save button on task edit page
    And I click id of "App Renewal SR V1" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | status        | Closed               |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Cancelled            |
    And I click on save button on task edit page
    Then I verify "Missing Information" task status is updated to "Cancelled" on the link section
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType            | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | App Renewal SR V1 | Open   || 4        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

  @CP-19484 @CP-19484-02 @priyal @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario Outline: Validate of Linking Missing Information Task to App SR V1 Service Request
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "App Renewal SR V1" service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-19477 |
      | applicationType       | MAGI - PW     |
      | externalApplicationId | random        |
      | priority              | 3             |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click id of "Process App V1" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                    | Complete             |
      | reasonForEdit             | Corrected Data Entry |
      | assignee                  | Service TesterTwo    |
      | taskInfo                  | Test 123             |
      | myWorkSpaceDate           | -2                   |
      | applicationReceivedDate   | today                |
      | applicationSignatureDate  | today                |
      | noOfApplicantsInHousehold | 1                    |
      | channel                   | CommonHelp           |
      | applicationStatus         | Data Collection      |
      | actionTaken               | Sent VCL             |
      | disposition               | Pending MI           |
      | decisionSource            | Manual               |
      | noOfApprovedApplicants    | 1                    |
      | missingInfoRequired       | Yes                  |
    And I click on save button on task edit page
    And I click id of "App Renewal SR V1" in Links section
    And I click id of "Missing Information" in Links section
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on cancel button on task slider
    When I click Continue button inside the warning message
    And In search result click on task id to navigate to view page
    And I click id of "App Renewal SR V1" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | status        | Closed               |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Cancelled            |
    And I click on save button on task edit page
    Then I verify "Missing Information" task status is updated to "Cancelled" on the link section
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType            | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReasom|
      ||          | App Renewal SR V1 | Open   || 3        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||
