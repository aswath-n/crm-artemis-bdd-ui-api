Feature: Validation of Appeals SR

  #@CP-18466 @CP-18466-01 @CP-22069 @CP-22069-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen @vidya
  Scenario: Initialize workflow when Appeals SR is created through CP UI
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      |  Test CP-18466               |
      | documentDueDate               |  today                       |
      | myWorkSpaceDate               |  today                       |
      | preferredPhone                |  5189083418                  |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Appeal"
    And I run bpm process get api by sr id
    And I will check response of service request bpm process

  #@CP-18466 @CP-18541 @CP-18466-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Create Appeals SR and link to consumer and Review Appeal Task manually (Outside of the Active Contact)
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      |  Test CP-18466               |
      | documentDueDate               |  today                       |
      | myWorkSpaceDate               |  today                       |
      | preferredPhone                |  5189083418                  |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Zarina" and Last Name as "Pottery"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    When I click on Link Record Consumer button
    Then I verify task is linked with Consumer ID
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When I click case consumer search tab
    When I searched customer have First Name as "Zarina" and Last Name as "Pottery"
    When I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Appeals SR" link section has all the business object linked : "Task,Consumer"

  #@CP-18466 @CP-18541 @CP-18466-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Create Appeals SR and link to consumer and Review Appeal Task systematically (from manual Case/Consumer Search)
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      |  Test CP-18466               |
      | documentDueDate               |  today                       |
      | myWorkSpaceDate               |  today                       |
      | preferredPhone                |  5189083418                  |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Appeals SR" link section has all the business object linked : "Task,Consumer"

  #@CP-17927 @CP-18541 @CP-18467 @CP-17927-01 @CP-18467-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Validate Appeals SR and Review Appeal Task is created from inbound document
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Appeal"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | VACV Appeal              |
    When I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then I will verify "Appeals SR" Details view SR details page
    Then I verify "Appeals SR" link section has all the business object linked : "Task,Inbound Correspondence"
    And I initiated bpm process get api by service request id for process name "CoverVA_Appeal"
    And I run bpm process get api by sr id
    And I will check response of service request bpm process

  #@CP-18467 @CP-18467-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Verification of Link Appeal Inbound Document from Links Component
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Appeal"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | VACV Appeal              |
    When I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then I will verify "Appeals SR" Details view SR details page
    Then I verify "Appeals SR" link section has all the business object linked : "Task,Inbound Correspondence"
    When I click on Inbound Correspondence Link from View Task Page
    Then I should see I am navigated to the Inbound Correspondence Details Page for "CoverVA Appeals"
    And I verify that Inbound Correspondence Page has all "Appeals SR" linked objects

  #@CP-18527 @CP-18527-01 @CP-18710 @CP-18710-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Systematically create Fair Hearing task when Review Appeal Task is updated as Completed, linked to consumer and SR
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      |  Test CP-18527               |
      | documentDueDate               |  today                       |
      | myWorkSpaceDate               |  today                       |
      | preferredPhone                |  5189083418                  |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Appeals SR" link section has all the business object linked : "Task,Consumer"
    And I click id of "Review Appeal" in Links section
    Then I verify "Review Appeal" link section has all the business object linked : "Service Request,Consumer"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Complete            |
      | disposition             | Fair Hearing        |
      | actionTaken             | Returned to DMAS    |
      | reasonForEdit           | Corrected Data Entry|
      | dmasReceivedDate        | today               |
      | coverVAReceivedDate     | today               |
      | externalApplicationId   | T5690125            |
      | caseStatus              | New Application     |
      | appealReason            | Other               |
      | businessUnit            | CPU                 |
      | appealCaseSummaryDueDate| today               |
      | appealCaseSummaryStatus | In-Progress         |
      | reviewOutcome           | Processing Delay    |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    And I click id of "Appeals SR" in Links section
    And I click id of "Fair Hearing" in Links section
    Then I verify "Fair Hearing" task fields are displayed
    Then I verify "Fair Hearing" link section has all the business object linked : "Service Request,Consumer"
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify latest task displayed in "Work Queue or My Task" page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page

  #@CP-18527 @CP-18527-02 @CP-18710 @CP-18710-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Systematically create Fair Hearing task when Review Appeal Task is updated as Completed from Inbound Correspondence, linked to inbound correspondence and SR
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Appeal"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | VACV Appeal              |
    When I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then I will verify "Appeals SR" Details view SR details page
    Then I verify "Appeals SR" link section has all the business object linked : "Task,Inbound Correspondence"
    And I click id of "Review Appeal" in Links section
    Then I verify "Review Appeal" link section has all the business object linked : "Service Request,Inbound Correspondence"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Complete            |
      | disposition             | Fair Hearing        |
      | actionTaken             | Returned to DMAS    |
      | reasonForEdit           | Corrected Data Entry|
      | dmasReceivedDate        | today               |
      | coverVAReceivedDate     | today               |
      | externalApplicationId   | T5690126            |
      | caseStatus              | New Application     |
      | appealReason            | Other               |
      | businessUnit            | CPU                 |
      | appealCaseSummaryDueDate| today               |
      | appealCaseSummaryStatus | In-Progress         |
      | reviewOutcome           | Processing Delay    |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    And I click id of "Appeals SR" in Links section
    And I click id of "Fair Hearing" in Links section
    Then I verify "Fair Hearing" task fields are displayed
    Then I verify "Fair Hearing" link section has all the business object linked : "Service Request,Inbound Correspondence"
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify latest task displayed in "Work Queue or My Task" page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page

  #@CP-18784 @CP-18784-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Systematically set Appeals SR status to Closed when Review Appeal Task is updated as Cancelled
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      |  Test CP-18784               |
      | documentDueDate               |  today                       |
      | myWorkSpaceDate               |  today                       |
      | preferredPhone                |  5189083424                  |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Appeals SR" link section has all the business object linked
    And I click id of "Review Appeal" in Links section
    Then I verify "Review Appeal" link section has all the business object linked
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Cancelled                                     |
      | reasonForEdit           | Corrected Data Entry                          |
      | dmasReceivedDate        | today                                         |
      | coverVAReceivedDate     | today                                         |
      | externalApplicationId   | T5690137                                      |
      | caseStatus              | Renewal Application                           |
      | taskInfo                | Cancelled functionality of Review Appeal Task |
      | businessUnit            | CPU                                           |
      | appealCaseSummaryDueDate| today                                         |
      | appealCaseSummaryStatus | Approved by DMAS                              |
      | reviewOutcome           | Processing Delay                              |
      | reasonForCancel         | Created Incorrectly                           |
      | appealReason            | Other                                         |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    And I click id of "Appeals SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Withdrawn"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Appeal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  #@CP-18784 @CP-18784-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Systematically set Appeals SR status to Closed when Review Appeal Task is updated as Cancelled from Task Slider
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I have collected Case Consumer Name and ID from header
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      |  Test CP-18784               |
      | documentDueDate               |  today                       |
      | myWorkSpaceDate               |  today                       |
      | preferredPhone                |  5189083424                  |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I Verify user is navigate back to Task and service Request Page
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify that first task is "Review Appeal" and linked to consumer
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status             | Cancel          |
      | reasonForCancel    | Duplicate Task  |
    And I click on save in Task Slider
    And I navigate to "Task Search" page
    When I will search with taskId
    And In search result click on task id to navigate to view page
    And I click id of "Appeals SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Withdrawn"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Appeal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  #@CP-18784 @CP-18784-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Systematically set Appeals SR status to Closed when Fair Hearing Task is updated as Cancelled
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      | Test CP-18527               |
      | documentDueDate               | today                       |
      | myWorkSpaceDate               | today                       |
      | preferredPhone                | 5189083418                  |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Appeals SR" link section has all the business object linked
    And I click id of "Review Appeal" in Links section
    Then I verify "Review Appeal" link section has all the business object linked
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Complete                                      |
      | disposition             | Fair Hearing                                  |
      | taskInfo                | Cancelled functionality of Fair Hearing Task  |
      | actionTaken             | Returned to DMAS                              |
      | reasonForEdit           | Corrected Data Entry                          |
      | dmasReceivedDate        | today                                         |
      | coverVAReceivedDate     | today                                         |
      | externalApplicationId   | T5690125                                      |
      | caseStatus              | New Application                               |
      | appealReason            | Other                                         |
      | businessUnit            | CPU                                           |
      | appealCaseSummaryDueDate| today                                         |
      | appealCaseSummaryStatus | In-Progress                                   |
      | reviewOutcome           | Processing Delay                              |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    And I click id of "Appeals SR" in Links section
    And I click id of "Fair Hearing" in Links section
    Then I verify "Fair Hearing" task fields are displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Cancelled                                    |
      | reasonForCancel         | Created Incorrectly                          |
      | taskInfo                | Cancelled functionality of Fair Hearing Task |
      | interimStatus           | Awaiting Decision                            |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    And I click id of "Appeals SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Withdrawn"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Appeal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  #@CP-18784 @CP-18784-04 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Systematically set Appeals SR status to Closed when Fair Hearing Task is updated as Cancelled from Task Slider
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I have collected Case Consumer Name and ID from header
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      |  Test CP-18784               |
      | documentDueDate               |  today                       |
      | myWorkSpaceDate               |  today                       |
      | preferredPhone                |  5189083424                  |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Appeals SR" link section has all the business object linked
    And I click id of "Review Appeal" in Links section
    Then I verify "Review Appeal" link section has all the business object linked
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Complete                                      |
      | disposition             | Fair Hearing                                  |
      | taskInfo                | Cancelled functionality of Fair Hearing Task  |
      | actionTaken             | Returned to DMAS                              |
      | reasonForEdit           | Corrected Data Entry                          |
      | dmasReceivedDate        | today                                         |
      | coverVAReceivedDate     | today                                         |
      | externalApplicationId   | T5690125                                      |
      | caseStatus              | New Application                               |
      | appealReason            | Other                                         |
      | businessUnit            | CPU                                           |
      | appealCaseSummaryDueDate| today                                         |
      | appealCaseSummaryStatus | In-Progress                                   |
      | reviewOutcome           | Processing Delay                              |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify that first task is "Fair Hearing" and linked to consumer
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status             | Cancel          |
      | reasonForCancel    | Duplicate Task  |
    And I click on save in Task Slider
    And I navigate to "Task Search" page
    When I will search with taskId
    And In search result click on task id to navigate to view page
    And I click id of "Appeals SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Withdrawn"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Appeal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  #@CP-18561 @CP-18561-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Systematically set Appeals SR status to Closed when Review Appeal Task is updated as Completed with Withdrawn
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I have collected Case Consumer Name and ID from header
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      |  Test CP-18561-01            |
      | documentDueDate               |  today                       |
      | myWorkSpaceDate               |  today                       |
      | preferredPhone                |  5189083424                  |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Appeals SR" link section has all the business object linked
    And I click id of "Review Appeal" in Links section
    Then I verify "Review Appeal" link section has all the business object linked
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Complete                     |
      | disposition             | Withdrawn                    |
      | actionTaken             | Admin Resolve                |
      | reasonForEdit           | Corrected Data Entry         |
      | taskInfo                | Completed Review Appeal Task |
      | dmasReceivedDate        | today                        |
      | coverVAReceivedDate     | today                        |
      | externalApplicationId   | T5690126                     |
      | caseStatus              | New Application              |
      | appealReason            | Other                        |
      | businessUnit            | CPU                          |
      | appealCaseSummaryDueDate| today                        |
      | appealCaseSummaryStatus | In-Progress                  |
      | reviewOutcome           | Processing Delay             |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    And I click id of "Appeals SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Withdrawn"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Appeal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  #@CP-18561 @CP-18561-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Systematically set Appeals SR status to Closed when Review Appeal Task is completed with Withdrawn from Task Slider
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I have collected Case Consumer Name and ID from header
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      |  Test CP-18561-02            |
      | documentDueDate               |  today                       |
      | myWorkSpaceDate               |  today                       |
      | preferredPhone                |  5189083424                  |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I Verify user is navigate back to Task and service Request Page
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify that first task is "Review Appeal" and linked to consumer
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status             | Complete        |
      | actionTaken        | Admin Resolve   |
      | disposition        | Withdrawn       |
    And I click on save in Task Slider
    And I navigate to "Task Search" page
    When I will search with taskId
    And In search result click on task id to navigate to view page
    And I click id of "Appeals SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Withdrawn"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Appeal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

 # @CP-18561 @CP-18561-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Systematically set Appeals SR status to Closed when Review Appeal Task is updated as Completed with Returned to DMAS
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I have collected Case Consumer Name and ID from header
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      |  Test CP-18561-03            |
      | documentDueDate               |  today                       |
      | myWorkSpaceDate               |  today                       |
      | preferredPhone                |  5189083424                  |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Appeals SR" link section has all the business object linked
    And I click id of "Review Appeal" in Links section
    Then I verify "Review Appeal" link section has all the business object linked
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Complete                     |
      | disposition             | Returned to DMAS             |
      | actionTaken             | Admin Resolve                |
      | reasonForEdit           | Corrected Data Entry         |
      | taskInfo                | Completed Review Appeal Task |
      | dmasReceivedDate        | today                        |
      | coverVAReceivedDate     | today                        |
      | externalApplicationId   | T5690126                     |
      | caseStatus              | New Application              |
      | appealReason            | Other                        |
      | businessUnit            | CPU                          |
      | appealCaseSummaryDueDate| today                        |
      | appealCaseSummaryStatus | In-Progress                  |
      | reviewOutcome           | Processing Delay             |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    And I click id of "Appeals SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Returned to DMAS"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Appeal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  #@CP-18561 @CP-18561-04 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Systematically set Appeals SR status to Closed when Review Appeal Task is completed with Returned to DMAS from Task Slider
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I have collected Case Consumer Name and ID from header
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      |  Test CP-18561-04            |
      | documentDueDate               |  today                       |
      | myWorkSpaceDate               |  today                       |
      | preferredPhone                |  5189083424                  |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I Verify user is navigate back to Task and service Request Page
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify that first task is "Review Appeal" and linked to consumer
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status             | Complete          |
      | actionTaken        | Admin Resolve     |
      | disposition        | Returned to DMAS  |
    And I click on save in Task Slider
    And I navigate to "Task Search" page
    When I will search with taskId
    And In search result click on task id to navigate to view page
    And I click id of "Appeals SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Returned to DMAS"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Appeal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  #@CP-18561 @CP-18561-05 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Systematically set Appeals SR status to Closed when Fair Hearing Task is updated as Completed with Abandoned disposition
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      | Test CP-18561-05 |
      | documentDueDate               | today            |
      | myWorkSpaceDate               | today            |
      | preferredPhone                | 5189083429       |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Appeals SR" link section has all the business object linked
    And I click id of "Review Appeal" in Links section
    Then I verify "Review Appeal" link section has all the business object linked
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Complete                     |
      | disposition             | Fair Hearing                 |
      | actionTaken             | Returned to DMAS             |
      | reasonForEdit           | Corrected Data Entry         |
      | taskInfo                | Completed Review Appeal Task |
      | dmasReceivedDate        | today                        |
      | coverVAReceivedDate     | today                        |
      | externalApplicationId   | T5690126                     |
      | caseStatus              | New Application              |
      | appealReason            | Other                        |
      | businessUnit            | CPU                          |
      | appealCaseSummaryDueDate| today                        |
      | appealCaseSummaryStatus | In-Progress                  |
      | reviewOutcome           | Processing Delay             |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    And I click id of "Appeals SR" in Links section
    And I click id of "Fair Hearing" in Links section
    Then I verify "Fair Hearing" task fields are displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Complete                                      |
      | reasonForEdit           | Corrected Data Entry                          |
      | taskInfo                | Completed functionality of Fair Hearing Task  |
      | disposition             | Abandoned                                     |
      | interimStatus           | Awaiting Decision                             |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    And I click id of "Appeals SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Abandoned"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Appeal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  #@CP-18561 @CP-18561-06 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Systematically set Appeals SR status to Closed when Fair Hearing Task is updated as Completed with Admin Resolved disposition
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      | Test CP-18561-06 |
      | documentDueDate               | today            |
      | myWorkSpaceDate               | today            |
      | preferredPhone                | 5189083429       |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Appeals SR" link section has all the business object linked
    And I click id of "Review Appeal" in Links section
    Then I verify "Review Appeal" link section has all the business object linked
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Complete                     |
      | disposition             | Fair Hearing                 |
      | actionTaken             | Returned to DMAS             |
      | reasonForEdit           | Corrected Data Entry         |
      | taskInfo                | Completed Review Appeal Task |
      | dmasReceivedDate        | today                        |
      | coverVAReceivedDate     | today                        |
      | externalApplicationId   | T5690126                     |
      | caseStatus              | New Application              |
      | appealReason            | Other                        |
      | businessUnit            | CPU                          |
      | appealCaseSummaryDueDate| today                        |
      | appealCaseSummaryStatus | In-Progress                  |
      | reviewOutcome           | Processing Delay             |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    And I click id of "Appeals SR" in Links section
    And I click id of "Fair Hearing" in Links section
    Then I verify "Fair Hearing" task fields are displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Complete                                      |
      | reasonForEdit           | Corrected Data Entry                          |
      | taskInfo                | Completed functionality of Fair Hearing Task  |
      | disposition             | Admin Resolved                                |
      | interimStatus           | Awaiting Decision                             |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    And I click id of "Appeals SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Admin Resolved"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Appeal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  # Forwarded to LDSS has issue due to CP-21915
  # Pending has issue due to CP-21915
  # Rescheduled has issue due to CP-21915

  #@CP-18561 @CP-18561-07 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Systematically set Appeals SR status to Closed when Fair Hearing Task is updated as Completed with Invalidated disposition
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      | Test CP-18561-07 |
      | documentDueDate               | today            |
      | myWorkSpaceDate               | today            |
      | preferredPhone                | 5189083429       |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Appeals SR" link section has all the business object linked
    And I click id of "Review Appeal" in Links section
    Then I verify "Review Appeal" link section has all the business object linked
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Complete                     |
      | disposition             | Fair Hearing                 |
      | actionTaken             | Returned to DMAS             |
      | reasonForEdit           | Corrected Data Entry         |
      | taskInfo                | Completed Review Appeal Task |
      | dmasReceivedDate        | today                        |
      | coverVAReceivedDate     | today                        |
      | externalApplicationId   | T5690126                     |
      | caseStatus              | New Application              |
      | appealReason            | Other                        |
      | businessUnit            | CPU                          |
      | appealCaseSummaryDueDate| today                        |
      | appealCaseSummaryStatus | In-Progress                  |
      | reviewOutcome           | Processing Delay             |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    And I click id of "Appeals SR" in Links section
    And I click id of "Fair Hearing" in Links section
    Then I verify "Fair Hearing" task fields are displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Complete                                      |
      | reasonForEdit           | Corrected Data Entry                          |
      | taskInfo                | Completed functionality of Fair Hearing Task  |
      | disposition             | Invalidated                                   |
      | interimStatus           | Awaiting Decision                             |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    And I click id of "Appeals SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Invalidated"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Appeal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  #@CP-18561 @CP-18561-08 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Systematically set Appeals SR status to Closed when Fair Hearing Task is updated as Completed with Remanded disposition from Task Slider
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I have collected Case Consumer Name and ID from header
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      | Test CP-18561-08 |
      | documentDueDate               | today            |
      | myWorkSpaceDate               | today            |
      | preferredPhone                | 5189083429       |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Appeals SR" link section has all the business object linked
    And I click id of "Review Appeal" in Links section
    Then I verify "Review Appeal" link section has all the business object linked
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Complete                     |
      | disposition             | Fair Hearing                 |
      | actionTaken             | Returned to DMAS             |
      | reasonForEdit           | Corrected Data Entry         |
      | taskInfo                | Completed Review Appeal Task |
      | dmasReceivedDate        | today                        |
      | coverVAReceivedDate     | today                        |
      | externalApplicationId   | T5690126                     |
      | caseStatus              | New Application              |
      | appealReason            | Other                        |
      | businessUnit            | CPU                          |
      | appealCaseSummaryDueDate| today                        |
      | appealCaseSummaryStatus | In-Progress                  |
      | reviewOutcome           | Processing Delay             |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify that first task is "Fair Hearing" and linked to consumer
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status             | Complete        |
      | disposition        | Remanded        |
    And I click on save in Task Slider
    And I navigate to "Task Search" page
    When I will search with taskId
    And In search result click on task id to navigate to view page
    And I click id of "Appeals SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Remanded"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Appeal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  #@CP-18561 @CP-18561-09 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Systematically set Appeals SR status to Closed when Fair Hearing Task is updated as Completed with Upheld disposition from Task Slider
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I have collected Case Consumer Name and ID from header
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      | Test CP-18561-09 |
      | documentDueDate               | today            |
      | myWorkSpaceDate               | today            |
      | preferredPhone                | 5189083429       |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Appeals SR" link section has all the business object linked
    And I click id of "Review Appeal" in Links section
    Then I verify "Review Appeal" link section has all the business object linked
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Complete                     |
      | disposition             | Fair Hearing                 |
      | actionTaken             | Returned to DMAS             |
      | reasonForEdit           | Corrected Data Entry         |
      | taskInfo                | Completed Review Appeal Task |
      | dmasReceivedDate        | today                        |
      | coverVAReceivedDate     | today                        |
      | externalApplicationId   | T5690126                     |
      | caseStatus              | New Application              |
      | appealReason            | Other                        |
      | businessUnit            | CPU                          |
      | appealCaseSummaryDueDate| today                        |
      | appealCaseSummaryStatus | In-Progress                  |
      | reviewOutcome           | Processing Delay             |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify that first task is "Fair Hearing" and linked to consumer
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status             | Complete        |
      | disposition        | Upheld          |
    And I click on save in Task Slider
    And I navigate to "Task Search" page
    When I will search with taskId
    And In search result click on task id to navigate to view page
    And I click id of "Appeals SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Upheld"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Appeal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process

  #@CP-18561 @CP-18561-10 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Systematically set Appeals SR status to Closed when Fair Hearing Task is updated as Completed with Withdrawn disposition from Task Slider
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I have collected Case Consumer Name and ID from header
    When I navigate to "Appeals SR" service request page
    And I will provide following information before creating task
      | taskInfo                      | Test CP-18561-10 |
      | documentDueDate               | today            |
      | myWorkSpaceDate               | today            |
      | preferredPhone                | 5189083429       |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Appeals SR" link section has all the business object linked
    And I click id of "Review Appeal" in Links section
    Then I verify "Review Appeal" link section has all the business object linked
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Complete                     |
      | disposition             | Fair Hearing                 |
      | actionTaken             | Returned to DMAS             |
      | reasonForEdit           | Corrected Data Entry         |
      | taskInfo                | Completed Review Appeal Task |
      | dmasReceivedDate        | today                        |
      | coverVAReceivedDate     | today                        |
      | externalApplicationId   | T5690126                     |
      | caseStatus              | New Application              |
      | appealReason            | Other                        |
      | businessUnit            | CPU                          |
      | appealCaseSummaryDueDate| today                        |
      | appealCaseSummaryStatus | In-Progress                  |
      | reviewOutcome           | Processing Delay             |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify that first task is "Fair Hearing" and linked to consumer
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status             | Complete        |
      | disposition        | Withdrawn       |
    And I click on save in Task Slider
    And I navigate to "Task Search" page
    When I will search with taskId
    And In search result click on task id to navigate to view page
    And I click id of "Appeals SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Withdrawn"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Appeal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process





