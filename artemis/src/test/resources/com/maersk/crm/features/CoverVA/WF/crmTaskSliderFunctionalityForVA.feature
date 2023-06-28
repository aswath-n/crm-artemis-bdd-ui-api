@taskSliderForCoverVA
Feature: Task slider functionality for CoverVA

  #@CP-22749 @CP-22749-02 is obselete due to CP-25501
  @CP-22737 @CP-22737-02 @CP-22749 @CP-22749-02 @CP-25501 @CP-25501-04 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Final Application Review task is created when Execute Wait Step is over and also verify Final Application Task can not be cancelled
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      |applicationType          | MAGI - PW|
      |externalApplicationId    | random   |
      |myWorkSpaceDate          | -45      |
      |missingInfoRequired      | Yes      |
      |vclDueDate               | today    |
      |vclSentDate              | today    |
      |channel                  | CommonHelp |
      |InbDocType               |Identity Verification|
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  | Complete|
      | actionTakenSingle       | Sent VCL|
      | disposition             | Pending MI|
    And I click on save in Task Slider
    #Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    Then I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I click id of "Final Application Review" in Links section
    Then I verify "Final Application Review" task fields are displayed
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify latest task displayed in "Work Queue or My Task" page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And I click on initiate button in task search page
    And Verify task slider is displayed
    Then I verify "taskStatus" field does not contains value on "task slider" page
      | Cancel |
    And I will update the following information in task slider
      | status      | Complete                |
      | actionTaken | Escalated to Supervisor |
      | disposition | Approved                |
    And I click on save in Task Slider
    #Then I verify Success message is displayed for task update
    And In search result click on task id to navigate to view page
    And I click id of "Application SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Approved"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVAApplicationSRFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-22737 @CP-22737-04 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Final Application Review task is not created when Execute Wait Step is not over
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      |applicationType          | MAGI - PW|
      |externalApplicationId    | random   |
      |myWorkSpaceDate          | -2       |
      | missingInfoRequired     | Yes      |
      | vclDueDate              | +4       |
      | vclSentDate             | +4       |
      | channel                 | CommonHelp |
      |InbDocType               |Medical ID Card|
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  | Complete|
      | actionTakenSingle       | Sent VCL|
      | disposition             | Pending MI|
    And I click on save in Task Slider
    Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Final Application Review" task is not created
    And Close the soft assertions

  @CP-22905 @CP-22905-02 @CP-22908 @CP-22908-02 @CP-25501 @CP-25501-05 @CP-42875 @CP-42875-01 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Final Application Review task is created when Execute Wait Step is over
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType          | Renewal Application CVIU |
      | applicationSignatureDate | today                    |
      | applicationReceivedDate  | today                    |
      | facilityName             | Bristol City Jail        |
      | externalApplicationId    | random                   |
      | myWorkSpaceDate          | -30                       |
      | missingInfoRequired      | Yes                      |
      | miReceivedDate           | today                    |
      | vclDueDate               | today                    |
      | InbDocType               | Earned Income            |
      | channel                  | CommonHelp               |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  | Complete|
      | actionTakenSingle       | Sent VCL|
      | vclSentDate             | today   |
      | disposition             | Pending MI|
    And I click on save in Task Slider
    #Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    Then I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I click id of "Final Application Review" in Links section
    Then I verify "Final Application Review" task fields are displayed
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify latest task displayed in "Work Queue or My Task" page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And I click on initiate button in task search page
    And Verify task slider is displayed
    Then I verify "taskStatus" field does not contains value on "task slider" page
      | Cancel |
    And I will update the following information in task slider
      | status      | Complete                    |
      | actionTaken | Denied - Failure to Provide |
      | disposition | Denied/Closed               |
    And I click on save in Task Slider
    #Then I verify Success message is displayed for task update
    And In search result click on task id to navigate to view page
    And I click id of "Renewal SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Denied/Closed"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal_V2"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-22905 @CP-22905-04 @CP-42875 @CP-42875-02 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Final Application Review task is not created when Execute Wait Step is not over
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType          | Ex Parte Renewal - CVIU |
      | applicationSignatureDate | today                   |
      | applicationReceivedDate  | today                   |
      | facilityName             | Roanoke City Jail       |
      | externalApplicationId    | random                  |
      | myWorkSpaceDate          | -2                      |
      | missingInfoRequired      | Yes                     |
      | miReceivedDate           | today                   |
      | vclDueDate               | +4                      |
      | InbDocType               | Earned Income           |
      | channel                  | CommonHelp              |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  | Complete|
      | actionTakenSingle       | Sent VCL|
      | vclSentDate             | today   |
      | disposition             | Pending MI|
    And I click on save in Task Slider
    #Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Final Application Review" task is not created
    And Close the soft assertions

  @CP-22748 @CP-22748-04 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Application SR Ends and disposition = Denied when Final Application Review Task is complete
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      |applicationType          |Re-Entry Application - CVIU|
      |externalApplicationId    |random   |
      |myWorkSpaceDate          |-1       |
      | missingInfoRequired     |Yes      |
      | vclDueDate              |today    |
      | vclSentDate             |today    |
      |channel                  | CommonHelp |
      |InbDocType               |Non-Medical ID Card|
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  | Complete|
      | actionTakenSingle       | Sent VCL|
      | disposition             | Pending MI|
    And I click on save in Task Slider
    #Then I verify task save success message
    When If any In Progress task present then update that to Cancelled
    And I Verify user is navigate back to Task and service Request Page
    And I expend first Task from the list by clicking in Task ID
    And I will click on back arrow on view task page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created task in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  |Complete|
      | actionTaken             |Obtained - Electronic Sources|
      | disposition             |Denied|
    And I click on save in Task Slider
    #Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    Then I verify the sr status is updated to close and disposition is set to "Denied"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVAApplicationSRFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-22748 @CP-22748-05 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Application SR Ends and disposition = Denied when Final Application Review Task is complete
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      |applicationType          |Re-Entry Application - CVIU|
      |externalApplicationId    |random   |
      |myWorkSpaceDate          |-1       |
      | missingInfoRequired     |Yes      |
      | vclDueDate              |today    |
      | vclSentDate             |today    |
      |channel                  | CommonHelp  |
      |InbDocType               |SSN Documents|
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  |Complete|
      | actionTakenSingle       |Sent VCL|
      | disposition             |Pending MI|
    And I click on save in Task Slider
    Then I verify task save success message
    When If any In Progress task present then update that to Cancelled
    And I Verify user is navigate back to Task and service Request Page
    And I expend first Task from the list by clicking in Task ID
    And I will click on back arrow on view task page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created task in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  |Complete|
      | actionTaken             |Obtained - Electronic Sources|
      | disposition             |Auto-Denied|
    And I click on save in Task Slider
    Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    Then I verify the sr status is updated to close and disposition is set to "Auto-Denied"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVAApplicationSRFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-22907 @CP-22907-04 @CP-43442-03 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Renewal SR Ends and disposition = Denied when Final Application Review Task is complete
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType          | Renewal Application CVIU |
      | applicationSignatureDate | today                    |
      | applicationReceivedDate  | today                    |
      | facilityName             | Bristol City Jail        |
      | externalApplicationId    | random                   |
      | myWorkSpaceDate          | -30                      |
      | missingInfoRequired      | Yes                      |
      | miReceivedDate           | today                    |
      | vclDueDate               | today                    |
      | InbDocType               | Earned Income            |
      | channel                  | CommonHelp               |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  | Complete|
      | actionTakenSingle       | Sent VCL|
      | vclSentDate             | today     |
      | disposition             | Pending MI|
    And I click on save in Task Slider
    Then I verify task save success message
    When If any In Progress task present then update that to Cancelled
    And I Verify user is navigate back to Task and service Request Page
    And I expend first Task from the list by clicking in Task ID
    And I will click on back arrow on view task page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created task in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  |Complete|
      | actionTaken             |Obtained - Electronic Sources|
      | disposition             |Denied/Closed|
    And I click on save in Task Slider
    Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    Then I verify the sr status is updated to close and disposition is set to "Denied/Closed"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal_V2"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-22907 @CP-22907-05 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Renewal SR Ends and disposition = Denied when Final Application Review Task is complete
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType          | MAGI - PW  |
      | applicationSignatureDate | today      |
      | applicationReceivedDate  | today      |
      | externalApplicationId    | random     |
      | myWorkSpaceDate          | -30        |
      | missingInfoRequired      | Yes        |
      | miReceivedDate           | today      |
      | vclDueDate               | today      |
      | InbDocType               | Earned Income|
      | channel                  | CommonHelp |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  |Complete|
      | actionTakenSingle       |Sent VCL|
      | vclDueDate              | today  |
      | disposition             |Pending MI|
    And I click on save in Task Slider
    Then I verify task save success message
    When If any In Progress task present then update that to Cancelled
    And I Verify user is navigate back to Task and service Request Page
    And I expend first Task from the list by clicking in Task ID
    And I will click on back arrow on view task page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created task in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  |Complete|
      | actionTaken             |Obtained - Electronic Sources|
      | disposition             |Auto-Denied|
    And I click on save in Task Slider
    Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    Then I verify the sr status is updated to close and disposition is set to "Auto-Denied"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal_V2"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

    # will be retired soon CP-43127
  #@CP-19449 @CP-19449-04 @CP-19454 @CP-19454-01 @CP-19476 @CP-19476-01 @CP-19489 @CP-19489-02 @CP-25501 @CP-25501-06 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: verify missing info task is cretaed and due date update to 5 business days and Assignee of the task is updated as current user
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "App SR V1 " service request page
    And I will provide following information before creating task
      | taskInfo                |Test CP-18013  |
      |applicationType          |MAGI - PW|
      | externalApplicationId   |random         |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  | Complete|
      | actionTakenSingle       | Sent VCL|
      | disposition             | Pending MI|
    And I click on save in Task Slider
    Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    Then I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I click id of "Missing Information" in Links section
    Then I verify "Missing Information" task fields are displayed
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I verify latest task displayed in "Work Queue or My Task" page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And I click on initiate button in task search page
    And Verify task slider is displayed
    Then I verify "taskStatus" field does not contains value on "task slider" page
      | Cancel |
    And I will update the following information in task slider
      | status      | Complete |
      | actionTaken | Pending  |
      | disposition | Referred |
    And I click on save in Task Slider
    #Then I verify Success message is displayed for task update
    And In search result click on task id to navigate to view page
    And I click id of "App SR V1" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Referred"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVAInboundProcessFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

   # will be retired soon CP-43127
  #@CP-19449 @CP-19449-05 @CP-19476 @CP-19476-02 @CP-19451 @CP-19451-01 @CP-19716 @CP-19716-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: verify missing info task is cretaed and due date update to 8 calendar days and Assignee of the task is updated as current user and App SR V1 ends when missing info task is completed
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "App SR V1 " service request page
    And I will provide following information before creating task
      | taskInfo                |Test CP-18013  |
      |applicationType          |MAGI Standard Application - CVIU|
      | externalApplicationId   |random         |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  | Complete|
      | actionTakenSingle       | Sent VCL|
      | disposition             | Pending MI|
    And I click on save in Task Slider
    Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    Then I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I click id of "Missing Information" in Links section
    Then I verify "Missing Information" task fields are displayed
    And I will click on back arrow on view task page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  | Complete|
      | actionTaken             | Pending|
      | disposition             | Denied|
    And I click on save in Task Slider
    Then I verify task save success message
    Then I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    Then I verify the sr status is updated to close and disposition is set to "Denied"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVAInboundProcessFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
#will fail due to CP-43127
  #@CP-18642 @CP-18642-02 @CP-19448 @CP-19448-01 @CP-19456 @CP-19456-02 @CP-19453 @CP-19453-01 @CP-19480 @CP-19480-02 @CP-25501 @CP-25501-07 @vidya @basha @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Verify Missing information task is created with due date 5 business days and assign to current your and when cancel the missing info task app Renewal sr v1 is closed and disposition set to cancelled
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "App Renewal SR V1" service request page
    And I will provide following information before creating task
      | taskInfo                |Test CP-18642|
      |applicationType          |MAGI - PW    |
      | externalApplicationId   |random     |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  | Complete|
      | actionTakenSingle       | Sent VCL|
      | disposition             | Pending MI|
    And I click on save in Task Slider
    Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    Then I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I click id of "Missing Information" in Links section
    Then I verify "Missing Information" task fields are displayed
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I verify latest task displayed in "Work Queue or My Task" page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And I click on initiate button in task search page
    And Verify task slider is displayed
    Then I verify "taskStatus" field does not contains value on "task slider" page
      | Cancel |
    And I will update the following information in task slider
      |status       |Complete                |
      | actionTaken |Obtained - Outbound Call|
      | disposition |Denied                  |
    And I click on save in Task Slider
    #Then I verify Success message is displayed for task update
    And In search result click on task id to navigate to view page
    And I click id of "App Renewal SR V1" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Denied"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
#will fail due to CP-43127
  #@CP-19482 @CP-19482-03 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Verify App Renewal SR V1 Ends when Missing Information Task is Complete
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "App Renewal SR V1" service request page
    And I will provide following information before creating task
      | taskInfo                |Test CP-18642|
      |applicationType          |MAGI - CPU   |
      | externalApplicationId   |random     |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  | Complete|
      | actionTakenSingle       | Sent VCL|
      | disposition             | Pending MI|
    And I click on save in Task Slider
    Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    Then I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I click id of "Missing Information" in Links section
    And I will click on back arrow on view task page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  | Complete|
      | actionTaken             | Pending|
      | disposition             | Referred|
    And I click on save in Task Slider
    Then I verify task save success message
    Then I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    Then I verify the sr status is updated to close and disposition is set to "Referred"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-18739 @CP-18739-03 @CP-18832 @CP-18832-03 @CP-19032 @CP-19032-01 @CP-19038 @CP-19038-03 @CP-25501 @CP-25501-08 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @mark @vidya
  Scenario: Link Complaint SR to Consumer outside of Active Contact (Case/Consumer Search) and Create Review Complain/Complaint Escalation Task for Complaint SR
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "Complaint SR" service request page
    And I will provide following information before creating task
      | taskInfo | Test CP-18739 |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Complaint SR" link section has all the business object linked : "Task,Consumer"
    And I click id of "Review Complaint" in Links section
    Then I verify "Review Complaint" task fields are displayed
    Then I verify "Review Complaint" link section has all the business object linked : "Service Request,Consumer"
    And I will click on back arrow on view task page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  | Complete|
      | disposition             |Escalated|
    And I click on save in Task Slider
    #Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I click id of "Complaint Escalation" in Links section
    Then I verify "Complaint Escalation" task fields are displayed
    And I will click on back arrow on view task page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    Then I verify "taskStatus" field does not contains value on "task slider" page
      | Cancel |
    And I will update the following information in task slider
      |status      |Complete|
      |disposition |Referred|
    And I click on save in Task Slider
    #Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    Then I verify the sr status is updated to close and disposition is set to "Referred"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  #@CP-19479 @CP-19479-02 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA  obsolete scenario due to CP-25501
  Scenario: Validate Renewal SR Ends when Process Application Task is cancelled
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType       | Renewal Application CVIU |
      | applicationSignatureDate | today                 |
      | applicationReceivedDate  | today                 |
      | externalApplicationId | random                   |
      | myWorkSpaceDate       | -1                       |
      | missingInfoRequired   | Yes                      |
      | miReceivedDate        | today                    |
      | vclDueDate            | today                    |
      | channel               | CommonHelp               |
      | facilityName          | Bristol City Jail        |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      |status           |Cancel|
      |reasonForCancel  |Duplicate Task|
    And I click on save in Task Slider
    Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    Then I verify the sr status is updated to close and disposition is set to "Cancelled"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal_V2"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-18639 @CP-18639-02 @CP-19479 @CP-19479-03 @CP-25501 @CP-25501-09 @basha @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Application Renewal SR link to CR, consumer and Task
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Adam" and Last Name as "Smith"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "App Renewal SR V1" service request page
    And I will provide following information before creating task
      | taskInfo                |Test CP-18639          |
      |applicationType          |Ex Parte Renewal - CVIU|
      | externalApplicationId   | random              |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "App Renewal SR V1" link section has all the business object linked : "Task,Consumer,Contact Record"
   And I Navigate to Contact Record details page
    Then I verify contact record page link section has "App Renewal SR V1" and other business object: "Consumer Profile,Service Request"
    And I click on Active Contact widget
    And I verify active contact page link section has "App Renewal SR V1" and other business object: "Consumer Profile,Service Request"
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And In search result click on task id to navigate to view page
    And I click id of "Process App V1" in Links section
    And I will click on back arrow on view task page
    And I click on cancel button on task search page
    And I will search with taskId
    And I click on initiate button in task search page
    And Verify task slider is displayed
    Then I verify "taskStatus" field does not contains value on "task slider" page
      | Cancel |
    And I will update the following information in task slider
      | status      | Complete  |
      | actionTaken | Sent NOA  |
      | disposition | Approved  |
    And I click on save in Task Slider
    #Then I verify task save success message
    And In search result click on task id to navigate to view page
    And I click id of "App Renewal SR V1" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Approved"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-18747 @CP-18747-01 @CP-18832 @CP-18832-01 @CP-19038 @CP-19038-04 @CP-25501 @CP-25501-10 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Verification of Link Complaint Inbound Document from Links Component and Close Complaint SR when Review Complaint task is Cancelled
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Complaint"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | VACV Complaint           |
    When I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    And I will click on back arrow on view task page
    And In search result click on task id to navigate to view page
    Then I will verify "Complaint SR" Details view SR details page
    Then I verify "Complaint SR" link section has all the business object linked : "Task,Inbound Correspondence"
    When I click on Inbound Correspondence Link from View Task Page
    Then I should see I am navigated to the Inbound Correspondence Details Page for "CoverVA Complaint"
    And I verify that Inbound Correspondence Page has all "Complaint SR" linked objects
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    And I click id of "Review Complaint" in Links section
    And I will click on back arrow on view task page
    And I click on cancel button on task search page
    And I will search with taskId
    And I click on initiate button in task search page
    And Verify task slider is displayed
    Then I verify "taskStatus" field does not contains value on "task slider" page
      | Cancel |
    And I will update the following information in task slider
      | status      | Complete |
      | disposition | Resolved |
    And I click on save in Task Slider
    Then I verify task save success message
    And In search result click on task id to navigate to view page
    And I click id of "Complaint SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Resolved"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-20263 @CP-20263-01 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Initiate work on a Task and launch the PDF Viewer for linked Inbound Correspondence
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
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
    And I click on initiate button in task search page
    And Verify task slider is displayed
    Then Verify PDF viewer is initiated for linked Correspondence ID
    And I will update the following information in task slider
      |status           |Cancel|
      |reasonForCancel  |Duplicate Task|
    And I click on save in Task Slider
    And Wait for 2 seconds
    #Then I verify task save success message
    Then I verify task slider is closed
    Then I verify user is navigate back to task search page
    And Close the soft assertions

  @CP-19457 @CP-19457-03 @CP-19031 @CP-19031-06 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Verification Complaint SR is closed when we complete the Complaint Escalation task
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Complaint"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | VACV Complaint           |
    When I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    And I click id of "Review Complaint" in Links section
    And I will click on back arrow on view task page
    And I click on cancel button on task search page
    And I will search with taskId
    When If any In Progress task present then update that to Cancelled
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  | Complete|
      | disposition             |Escalated|
    And I click on save in Task Slider
    #Then I verify task save success message
    And In search result click on task id to navigate to view page
    And I click id of "Complaint SR" in Links section
    And I click id of "Complaint Escalation" in Links section
    And I will click on back arrow on view task page
    And I click on cancel button on task search page
    And I will search with taskId
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  | Complete|
      | disposition             |Withdrawn|
    And I click on save in Task Slider
    Then I verify task save success message
    And In search result click on task id to navigate to view page
    And I click id of "Complaint SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Withdrawn"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-23032 @CP-23032-01 @CP-47703 @CP-47703-05 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan
  Scenario: Validate task slider remains open for Process Application task when user close Application SR
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    When If any In Progress task present then update that to Cancelled
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      | applicationType           | MAGI - PW       |
      | externalApplicationId     | random          |
      | myWorkSpaceDate           | -2              |
      | missingInfoRequired       | Yes             |
      | vclDueDate                | +4              |
      | decisionSource            ||
      | externalCaseId            | 123654789       |
      | applicationReceivedDate   | today           |
      | noOfApprovedApplicants    ||
      | noOfApplicantsInHousehold | 1               |
      | applicationStatus         | Data Collection |
      | InbDocType                | Drivers License |
      | vclSentDate               | today           |
      | channel                   | CommonHelp      |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I click on the priority in dashboard
    And I click on edit service request button
    And I verify "MI RECEIVED DATE" field is optional
    And I will update the following information in edit task page
      | reasonForEdit          | Corrected Data Entry |
      | status                 | Closed               |
      | disposition            | Approved             |
      | decisionSource         | Manual               |
      | noOfApprovedApplicants | 1                    |
    And I click on save button on task edit page
    And I click on the priority in dashboard
    And I will update the following information in task slider
      | status            | Complete   |
      | actionTakenSingle | Sent VCL   |
      | disposition       | Pending MI |
    And I click on save in Task Slider
    #Then I verify task save success message
    And Close the soft assertions

  @CP-23032 @CP-23032-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan
  Scenario: Validate task slider remains open for Final Application Review task when user close Application SR
    Given I will get the Authentication token for "CoverVA" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType           | Renewal Application CVIU |
      | applicationSignatureDate  | today                    |
      | applicationReceivedDate   | today                    |
      | externalApplicationId     | random                   |
      | myWorkSpaceDate           | -30                       |
      | missingInfoRequired       | Yes                      |
      | miReceivedDate            | today                    |
      | vclDueDate                | today                    |
     # | decisionSource            ||
      | externalCaseId            | 123654789                |
      #| noOfApprovedApplicants    ||
      | noOfApplicantsInHousehold | 1                        |
      | applicationStatus         | Data Collection          |
      | InbDocType                | Drivers License          |
      | facilityName              | Bristol City Jail        |
      | channel                   | CommonHelp                |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status                  | Complete  |
      | actionTakenSingle       | Sent VCL  |
      | vclSentDate             | today     |
      | disposition             | Pending MI|
      | decisionSource          | Manual    |
      | noOfApprovedApplicants  |   1       |
    And I click on save in Task Slider
    Then I verify task save success message
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created task in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I click on the priority in dashboard
    And I click on edit service request button
    And I verify "MI RECEIVED DATE" field is optional
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
      | status        | Closed               |
      | disposition   | Approved             |
    And I click on save button on task edit page
    And I click on the priority in dashboard
    And I will update the following information in task slider
      | status      | Complete                      |
      | actionTaken | Obtained - Electronic Sources |
      | disposition | Approved                      |
    And I click on save in Task Slider
    #Then I verify task save success message
    And Close the soft assertions

  @CP-23032 @CP-23032-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan
  Scenario: Validate task slider remains open for Verification Document task when user close Application SR
    Given I will get the Authentication token for "CoverVA" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType           | Renewal Application CVIU |
      | externalApplicationId     | random                   |
      | myWorkSpaceDate           | -2                       |
      | missingInfoRequired       | Yes                      |
      | miReceivedDate            | today                    |
      | vclDueDate                | -1                       |
      | decisionSource            ||
      | externalCaseId            | 123654789                |
      | applicationSignatureDate  | today                    |
      | applicationReceivedDate   | today                    |
      | noOfApprovedApplicants    ||
      | noOfApplicantsInHousehold | 1                        |
      | applicationStatus         | Data Collection          |
      | InbDocType                | Drivers License          |
      | vclSentDate               | today                    |
      | facilityName              | Bristol City Jail        |
      | channel                   | Paper                    |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    And I have a Inbound Document that with the Inbound Document Type of "VACV Verification Document"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on Create Link button and choose task or sr link
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | dateOfContact | -2                |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click on Link button in task search
    And I click on refresh button
    And I click on ConsumerId on Links section
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created task in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I click on the priority in dashboard
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit          | Corrected Data Entry |
      | status                 | Closed               |
      | disposition            | Approved             |
      | decisionSource         | Manual               |
      | noOfApprovedApplicants | 1                    |
    And I click on save button on task edit page
    And I click on the priority in dashboard
    And I will update the following information in task slider
      | status      | Complete |
      | disposition | Resolved |
      | actionTaken | Obtained - Outbound Call |
    And I click on save in Task Slider
    #Then I verify task save success message
    And Close the soft assertions
