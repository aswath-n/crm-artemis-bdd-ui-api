Feature: Validation of Application SR

  @CP-18013 @CP-18013-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate Application SR link to CR, consumer and Task
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "ThirdConsumer" and Last Name as "DemoVa"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "App SR V1 " service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-18013                |
      | applicationType       | Expedited Application - CVIU |
      | externalApplicationId | random                       |
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "App SR V1" link section has all the business object linked : "Task,Consumer,Contact Record"
    And I Navigate to Contact Record details page
    Then I verify link section has "App SR V1" and other business object
    And I click on Active Contact widget
    Then I verify link section has "App SR V1" and other business object on Active Contact

  @CP-18013 @CP-18013-02 @CP-19449 @CP-19449-02 @CP-19476 @CP-19476-03 @CP-19451 @CP-19451-02 @CP-19716 @CP-19716-02 @CP-25316, @CP-25316-11 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Create App SR V1 and link to consumer and Task manually and missing info task is created and assign to current user and App SR V1 is end when missing info task is completed
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "App SR V1 " service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-18013                |
      | applicationType       | Expedited Application - CVIU |
      | externalApplicationId | random                       |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Ravi" and Last Name as "Abc"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    When I click on Link Record Consumer button
    Then I verify task is linked with Consumer ID
    And I click on save button in create service request page
    When I click case consumer search tab
    When I searched customer have First Name as "Ravi" and Last Name as "Abc"
    When I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "App SR V1" link section has all the business object linked : "Task,Consumer"
    And I click id of "Process App V1" in Links section
    And I click on edit button on view task page
    And I verify Task Notes field is not displayed on Edit SR page
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
      | actionTakenSingle         | Sent VCL             |
      | disposition               | Pending MI           |
      | decisionSource            | Manual               |
      | missingInfoRequired       | No                   |
      | noOfApprovedApplicants    | 12                   |
    And I click on save button on task edit page
    And I click id of "App SR V1" in Links section
    And I click id of "Missing Information" in Links section
    Then I verify "Missing Information" task fields are displayed
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I verify latest task displayed in "Work Queue or My Task" page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And In search result click on task id to navigate to view page
    Then I Verify Task Notes field is not displayed on View SR page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | Complete                                            |
      | reasonForEdit   | Corrected Data Entry                                |
      | documentDueDate | today                                               |
      | receivedDate    | today                                               |
      | InbDocType      | Earned Income                                       |
      | actionTaken     | Escalated to Supervisor                             |
      | taskInfo        | Testing Complete functionality of Missinf info task |
      | disposition     | Referred                                            |
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I click id of "App SR V1" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Referred"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVAInboundProcessFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  @CP-18013 @CP-18013-03 @CP-19449 @CP-19449-01 @CP-19451 @CP-19451-03 @CP-18677 @CP-18677-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Create Application SR and link to consumer and Task systematically
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "App SR V1 " service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-18013                  |
      | applicationType       | Pre-Release Application - CVIU |
      | externalApplicationId | random                         |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I navigate to view SR details page by clicking on sr id
    Then I verify "App SR V1" link section has all the business object linked : "Task,Consumer"
    And I click id of "Process App V1" in Links section
    Then I verify "Process App V1" link section has all the business object linked : "Service Request,Consumer"
    Then I verify process application task fields displayed
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
      | actionTakenSingle         | Sent VCL             |
      | disposition               | Pending MI           |
      | noOfApprovedApplicants    | 12                   |
      | decisionSource            | Manual               |
      | missingInfoRequired       | No                   |
    And I click on save button on task edit page
    And I click id of "App SR V1" in Links section
    And I click id of "Missing Information" in Links section
    Then I verify "Missing Information" task fields are displayed
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify latest task displayed in "Work Queue or My Task" page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page

  @CP-18013 @CP-18013-04 @CP-31982 @CP-31982-09 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @priyal
  Scenario: Create Application SR and link to CR and Task
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I navigate to "App SR V1 " service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-18013                |
      | applicationType       | Expedited Application - CVIU |
      | externalApplicationId | random                       |
    And I click on save button in create service request page
    And I click on srId in active contact page and navigate to view sr details page
    And I navigated to the Edit History tab ON Edit Task
    And I navigated to the sr Details tab ON Edit Task
    Then I verify "App SR V1" link section has all the business object linked : "Task,Contact Record"
    And I Navigate to Contact Record details page
    Then I verify link section has "Application SR" and other business object
    And I click on Active Contact widget
    Then I verify link section has "Application SR" and other business object on Active Contact

  @CP-19363 @CP-19363-02 @CP-19449 @CP-19449-06 @CP-19451 @CP-19451-04 @CP-19489 @CP-19489-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Verify App SR V1 camunda flow and when cancel the missing info task app sr v1 is closed and disposition set to cancelled
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "App SR V1 " service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-18013                                  |
      | applicationType       | Emergency Medicaid Services Application - CVIU |
      | externalApplicationId | random                                         |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I navigate to view SR details page by clicking on sr id
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
      | actionTakenSingle         | Sent VCL             |
      | disposition               | Pending MI           |
      | noOfApprovedApplicants    | 12                   |
      | decisionSource            | Manual               |
      | missingInfoRequired       | No                   |
    And I click on save button on task edit page
    And I click id of "App SR V1" in Links section
    And I click id of "Missing Information" in Links section
    Then I verify "Missing Information" task fields are displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | Cancelled                                         |
      | reasonForCancel | Created Incorrectly                               |
      | reasonForEdit   | Corrected Data Entry                              |
      | documentDueDate | today                                             |
      | receivedDate    | today                                             |
      | InbDocType      | Earned Income                                     |
      | actionTaken     | Escalated to Supervisor                           |
      | taskInfo        | Testing Cancel functionality of Missing Info Task |
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I click id of "App SR V1" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Cancelled"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVAInboundProcessFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  @CP-18013 @CP-18013-05 @CP-19449 @CP-19449-03 @CP-19451 @CP-19451-05 @CP-19716 @CP-19716-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Verify App SR V1 camunda flow and missing infor task is created for App sr V1 and End App sr V1 when Missing Information Task is Complete
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "App SR V1 " service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-18013-05 |
      | applicationType       | MAGI - PW        |
      | externalApplicationId | random           |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I will click on back arrow on view task page
    And In search result click on task id to navigate to view page
    And I click id of "Process App V1" in Links section
    Then I verify process application task fields displayed
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
      | actionTakenSingle         | Sent VCL             |
      | disposition               | Pending MI           |
      | noOfApprovedApplicants    | 12                   |
      | decisionSource            | Manual               |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click id of "App SR V1" in Links section
    And I click id of "Missing Information" in Links section
    Then I verify "Missing Information" task fields are displayed
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
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I click id of "App SR V1" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Approved"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVAInboundProcessFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  @CP-18012 @CP-18012-01 @CP-21397 @CP-21397-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate Application SR is created from inbound document and verfiy App SR V1 closed when we complete the Process App V1 with action Transferred to LDSS
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Application"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | Channel      | Mail                   |
      | documentType | VACV Application       |
    When I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    Then I will verify "App SR V1" Details view SR details page
    And I initiated bpm process get api by service request id for process name "CoverVAInboundProcessFlow"
    And I run bpm process get api by sr id
    And I will check response of service request bpm process
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
      | noOfApprovedApplicants    | 12                   |
      | decisionSource            | Manual               |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click id of "App SR V1" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Referred"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  @CP-22069 @CP-22069-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Initialize workflow when Application SR is created through CP UI
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-22069-01 |
      | applicationType       | MAGI - PW        |
      | externalApplicationId | TTT123410        |
      | myWorkSpaceDate       | today            |
      | channel               | CommonHelp       |
    And I click on save button in create service request page
    Then I verify system returns an error "Duplicate Application Id found. Please correct the Application Id"

  @CP-19477 @CP-19477-01 @CP-19477-02 @CP-33898 @CP-33898-04 @chandrakumar @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate of Linking Missing Information Task and consumer to App SR V1 Service Request
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "App SR V1 " service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-19477 |
      | applicationType       | MAGI - PW     |
      | externalApplicationId | random        |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
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
      | actionTakenSingle         | Sent VCL             |
      | disposition               | Pending MI           |
      | noOfApprovedApplicants    | 12                   |
      | decisionSource            | Manual               |
    And I click on save button on task edit page
    And I click id of "App SR V1" in Links section
    And I click id of "Missing Information" in Links section
    Then I verify "Missing Information" link section has all the business object linked : "Service Request"
    And I click id of "App SR V1" in Links section
    Then I verify "App SR V1" link section has all the business object linked : "Task,Task"
    And I click on edit service request button
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Vally" and Last Name as "AAAAA"
    And I click on Search option
    And I select records count in pagination dropdown as "show 50" in "Case Consumer Task Search" page
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    When I click on Link Record Consumer button
    Then I verify task is linked with Consumer ID
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    And I click id of "Missing Information" in Links section
    Then I verify "Missing Information" link section has all the business object linked : "Service Request,Consumer"
    And I click id of "App SR V1" in Links section
    Then I verify "App SR V1" link section has all the business object linked : "Task,Task,Consumer"
    And Close the soft assertions