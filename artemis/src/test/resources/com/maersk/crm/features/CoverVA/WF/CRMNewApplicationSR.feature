Feature: Validation of New Application SR

  @CP-21546 @CP-21546-01 @CP-21829 @CP-21829-02 @CP-21597 @CP-21597-02 @CP-21019 @CP-21019-02 @CP-22664 @CP-21597-03 @CP-22730 @CP-22730-07 @CP-22740 @CP-22740-01 @vidya @chandrakumar @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate process application Application type matches value Expedited Application - CVIU
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      |applicationType          |Expedited Application - CVIU|
      |externalApplicationId    | random                     |
      |myWorkSpaceDate          | today                      |
      |channel                  | CommonHelp                 |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      |advanceSearch |true|
      |dateOfContact |today|
      |applicationId |getFromCreatePage|
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify task search result has my work space date which we provide the search parameter
    And In search result click on task id to navigate to view page
    And I click id of "Process Application" in Links section
    Then I verify "Process Application" task fields are displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | Complete             |
      | reasonForEdit   | Corrected Data Entry |
      | actionTknSingle | Transferred to LDSS  |
      | disposition     | Referred             |
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    And Close the soft assertions

  @CP-21546 @CP-21546-02 @CP-18554 @CP-18554-01 @CP-22735 @CP-22735-01 @CP-19487 @CP-19487-01 @CP-23725 @CP-23725-02 @priyal @vidya @elvin @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate process application Application type matches value Emergency Medicaid Services Application - CVIU and priority set to 2
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with default state for cover-va
    When I click on Create Consumer Button
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      |applicationType          |Emergency Medicaid Services Application - CVIU|
      |externalApplicationId    | random                     |
      |myWorkSpaceDate          | -1                         |
      |channel                  | CommonHelp                 |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click id of "Process Application" in Links section
    Then I verify "Process Application" task fields are displayed
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify latest task displayed in "Work Queue or My Task" page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And In search result click on task id to navigate to view page
    And I click id of "Application SR" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      |reasonForEdit            |Entered Additional Info  |
      |status                   |Closed                   |
      |applicationReceivedDate  |today                    |
      |channel                  |CommonHelp               |
      |noOfApplicantsInHousehold|1                        |
      |missingInfoRequired      |No                       |
      |applicationStatus        |Medicare Review Needed   |
      |disposition              |Cancelled                |
      #|facilityType             |Department of Juvenile Justice|
    And I will verify the following Facility Name and Facility Type set to "Regional Jail"
      |Hampton Roads Regional Jail           |
      |Henrico County Regional Jail (East)   |
      |Henrico County Regional Jail (West)   |
      |Henry County Jail                     |
      #|Lancaster County Sheriff’s Office    |
      |Loudoun County Jail                   |
      #|Martinsville City Sheriff’s Office    |
      |Meherrin River Regional Jail (Alberta)|
      |Meherrin River Regional Jail (Boydton)|
      |Middle Peninsula Regional Jail        |
      |Middle River Regional Jail            |
      |Montgomery County Jail                |
      |New River Valley Regional Jail        |
      |Newport News City Jail                |
      |Norfolk City Jail                     |
      |Northern Neck Regional Jail           |
      #|Northwestern Regional Adult Detention Center|
      #|Page County Sheriff’s Office          |
      |Pamunkey Regional Jail                |
      |Piedmont Regional Jail                |
      #|Pittsylvania County Sheriff’s Office |
      |Prince William/Manassas Regional ADC  |
      |Rappahannock Regional Jail            |
      |Richmond City Jail                    |
      |Riverside Regional Jail               |
      |Roanoke City Jail                     |
      |Roanoke County Jail                   |
      |Rockbridge Regional Jail              |
      |Southside Regional Jail               |
    And I click on save button on task edit page
    And I click id of "Process Application" in Links section
    Then I verify task's status is Cancelled
    And I click id of "Application SR" in Links section
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVAApplicationSRFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-21546 @CP-21546-04 @CP-18647 @CP-18647-02 @CP-18649 @CP-18649-02 @CP-18556 @CP-18556-02 @CP-23430 @CP-23430-03 @CP-22735 @CP-22735-02 @priyal @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate process application Application type matches value MAGI - PW and priority set to 1
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      |applicationType          |MAGI - PW|
      |externalApplicationId    | random|
      |myWorkSpaceDate          | -2|
      |channel                | CommonHelp     |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click id of "Process Application" in Links section
    Then I verify "Process Application" task fields are displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status |Complete|
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify "actionTakenSingle" single select drop down value
      | Sent NOA|
      | Sent VCL|
      | Transferred to LDSS|
    And I will update the following information in edit task page
      | status                  | Complete|
      | reasonForEdit           | Corrected Data Entry|
      | actionTknSingle         | Sent NOA|
      | disposition             | Auto-Approved|
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I click id of "Application SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Auto-Approved"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVAApplicationSRFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-21546 @CP-21546-05 @CP-23430 @CP-23430-01 @CP-22735 @CP-22735-03 @CP-18677 @CP-18677-02 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate process application Application type matches value MAGI Standard Application - CVIU and validate application sr disposition value and priority set to 2
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      |applicationType          |MAGI Standard Application - CVIU |
      |externalApplicationId    | random   |
      |myWorkSpaceDate          | -2       |
      |channel                  | CommonHelp     |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Application SR" link section has all the business object linked : "Task,Consumer"
    And I click id of "Process Application" in Links section
    Then I verify "Process Application" task fields are displayed
    Then I verify "Process Application" link section has all the business object linked : "Service Request,Consumer"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete            |
      | reasonForEdit           |Corrected Data Entry|
      | actionTknSingle         |Transferred to LDSS |
      | disposition             |Referred            |
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I click id of "Application SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Referred"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVAApplicationSRFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-21546 @CP-21546-07 @CP-18556 @CP-18556-01 @CP-23430 @CP-23430-02  @CP-22735 @CP-22735-04 @priyal @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate process application Application type matches value Re-Entry Application - CVIU and priority set to 2
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      |applicationType          |Re-Entry Application - CVIU|
      |externalApplicationId    | random   |
      |myWorkSpaceDate          | -1       |
      |channel                | CommonHelp     |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I navigate to view SR details page by clicking on sr id
    And I click id of "Process Application" in Links section
    Then I verify "Process Application" task fields are displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Complete|
      | reasonForEdit           | Corrected Data Entry|
      | actionTknSingle         | Sent NOA|
      | disposition             | Approved|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I click id of "Application SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Approved"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVAApplicationSRFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

    # refactoring on 30-08-2021 by Priyal
  @CP-22664 @CP-22664-02 @CP-19570 @CP-19570-02 @CP-19487 @CP-19487-02 @CP-22877 @CP-22877-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan @vidya @elvin
  Scenario: Create Application SR with required/optional fields for Application SR in Cover-VA
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Application SR" service request page
    And I click on save button on task edit page
    And I will provide following information before creating task
      | applicationType           | Re-Entry Application - CVIU |
      | externalApplicationId     | random                      |
      | myWorkSpaceDate           |  -45                        |
      | ldssReceivedDate          | today                       |
      | miReceivedDate            | today                       |
      | applicationSignatureDate  | today                       |
      | applicationReceivedDate   | today                       |
      | applicationUpdateDate     | today                       |
      | noOfApplicantsInHousehold | 1                           |
      #| noOfApprovedApplicants    | 1                           |
      | channel                    | CommonHelp                  |
      | hpe                       | true                        |
      | expedited                 | true                        |
      | missingInfoRequired       | Yes                         |
      | applicationStatus         | Data Collection             |
      | externalCaseId            | 123654789                   |
      | caseWorkerFirstName       | Ruslan                      |
      | caseWorkerLastName        | Agaev                       |
      #| locality                  | Westmoreland                |
      #| reason                    | Case Change                 |
      | facilityName              | Roanoke City Jail           |
      | facilityType              | Regional Jail               |
      | vclDueDate                | -1       |
      | vclSentDate               | today    |
      | InbDocType                |Authorized Representative Verification|
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status            | Complete                |
      | reasonForEdit     | Entered Additional Info |
      | actionTknSingle   | Sent VCL                |
      | disposition       | Pending MI              |
    And I click on save button on task edit page
    And I click id of "Application SR" in Links section
    And I click id of "Final Application Review" in Links section
    And I click id of "Application SR" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit        | Entered Additional Info |
      | status               | Closed|
      | disposition          | Cancelled|
      | missingInfoRequired  | No|
    And I click on save button on task edit page
    And I click id of "Final Application Review" in Links section
    Then I verify task's status is Cancelled
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVAApplicationSRFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

#  @CP-22737 @CP-22737-01 @CP-22749 @CP-22749-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
#  Final Application Review task do not have Cancelled status since it's created systematically
  Scenario: Validate Final Application Review task is created when Execute Wait Step is over and also verify Application SR ends when Final Application Task is cancelled
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I navigate to "Application SR" service request page
    And I will provide following information before creating task
      |applicationType          |MAGI Standard Application - CVIU |
      |externalApplicationId    | random   |
      |myWorkSpaceDate          | -2       |
      | missingInfoRequired     | Yes      |
      | vclDueDate              | -1       |
      | channel                 | CommonHelp     |
    When I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | actionTakenSingle       |Sent VCL|
      | disposition             |Pending MI|
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    And I click id of "Application SR" in Links section
    And I click id of "Final Application Review" in Links section
    Then I verify "Final Application Review" task fields are displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Cancelled|
      | reasonForCancel         |Created Incorrectly|
      | reasonForEdit           |Corrected Data Entry|
      | actionTaken             |Obtained - Inbound Verif Docs|
      | taskInfo                |Testing Cancel functionality of Final Application Review|
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I click id of "Application SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Cancelled"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVAApplicationSRFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-22737 @CP-22737-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate Final Application Review task is not created when Execute Wait Step is not over
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to "Application SR" service request page
    And I will provide following information before creating task
      |applicationType          |MAGI Standard Application - CVIU |
      |externalApplicationId    | random   |
      |myWorkSpaceDate          | +4       |
      | missingInfoRequired     | Yes      |
      | InbDocType              |Birth Verification|
      | vclDueDate              | -1       |
      | vclSentDate             | -1       |
      | channel                 | CommonHelp|
    When I click on save button in create service request page
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      |advanceSearch |true|
      |dateOfContact |+4  |
      |applicationId |getFromCreatePage|
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click id of "Process Application" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete            |
      | reasonForEdit           |Corrected Data Entry|
      | actionTknSingle         |Sent VCL            |
      | disposition             |Pending MI          |
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    And I click id of "Application SR" in Links section
    Then I verify "Final Application Review" task is not created
    And Close the soft assertions

  @CP-22748 @CP-22748-01 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Application SR Ends and disposition = Approved when Final Application Review Task is complete
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      |applicationType          | MAGI - PW|
      |externalApplicationId    | random   |
      |myWorkSpaceDate          | -45      |
      |missingInfoRequired      | Yes      |
      |vclSentDate              | -1       |
      |vclDueDate               | -2       |
      |channel                  | CommonHelp |
      |InbDocType               |Citizenship Documents|
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      |advanceSearch |true|
      |dateOfContact |-45  |
      |applicationId |getFromCreatePage|
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click id of "Process Application" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | actionTknSingle         |Sent VCL|
      | disposition             |Pending MI|
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    And I click id of "Application SR" in Links section
    And I click id of "Final Application Review" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | actionTaken             |Escalated to Supervisor|
      | disposition             |Approved|
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I click id of "Application SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Approved"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVAApplicationSRFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-22748 @CP-22748-02 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Application SR Ends and disposition = Auto-Approved when Final Application Review Task is complete
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      |applicationType          |Expedited Application - CVIU|
      |externalApplicationId    | random   |
      |myWorkSpaceDate          | -45      |
      | missingInfoRequired     | Yes      |
      | vclSentDate             | -2       |
      | vclDueDate              | -2       |
      |channel                  | CommonHelp |
      |InbDocType               |Drivers License|
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      |advanceSearch |true|
      |dateOfContact |-45 |
      |applicationId |getFromCreatePage|
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click id of "Process Application" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | actionTknSingle         |Sent VCL|
      | disposition             |Pending MI|
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    And I click id of "Application SR" in Links section
    And I click id of "Final Application Review" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | taskInfo                |Validate Application SR ends when Final Application Review Task is complete|
      | actionTaken             |Obtained - Electronic Sources|
      | disposition             |Auto-Approved|
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I click id of "Application SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Auto-Approved"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVAApplicationSRFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-22748 @CP-22748-03 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Application SR Ends and disposition = Referred when Final Application Review Task is complete
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      |applicationType          |Emergency Medicaid Services Application - CVIU|
      |externalApplicationId    | random   |
      |myWorkSpaceDate          | -45      |
      | missingInfoRequired     | Yes      |
      | vclDueDate              | -1       |
      | vclSentDate             | -2       |
      |channel                  | CommonHelp   |
      |InbDocType               |Earned Income |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      |advanceSearch |true|
      |dateOfContact |-45 |
      |applicationId |getFromCreatePage|
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click id of "Process Application" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | taskInfo                |Validate Application SR ends when Final Application Review Task is complete|
      | actionTknSingle         |Sent VCL|
      | disposition             |Pending MI|
    And I click on save button on task edit page
    And I click id of "Application SR" in Links section
    And I click id of "Final Application Review" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | taskInfo                |Validate Application SR ends when Final Application Review Task is complete|
      | actionTaken             |Obtained - Outbound Call|
      | disposition             |Referred|
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I click id of "Application SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Referred"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVAApplicationSRFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-22751 @CP-22751-01 @CP-22752 @CP-22752-01 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario Outline: Systamatically created Verification Document for Application SR and link to business object consumer
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Verification Document"
    When I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on Create Link button and choose task or sr link
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And I click task id to get the results in descending order in Task Search
    And I click on Link button in task search
    And I click on refresh button
    And I verify that Inbound Correspondence Page has all "Application SR" linked objects
    And I click id of "Verification Document" in link section of inbound page
    Then I verify "Verification Document" task fields are displayed
    Then I verify "Verification Document" link section has all the business object linked
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType         | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||          | Application SR | Closed | today      ||         ||        ||            | false         ||            ||          ||           ||

  @CP-22751 @CP-22751-02 @CP-22752 @CP-22752-02 @CP-19571 @CP-19571-01 @CP-18083 @CP-18083-02 @CP-21021 @CP-21021-03 @priyal @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Systamatically created Verification Document for Application SR and link to business object consumer and Inbound document
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      |applicationType          |Emergency Medicaid Services Application - CVIU|
      |externalApplicationId    | random   |
      |myWorkSpaceDate          | -45      |
      |missingInfoRequired      | Yes      |
      |vclSentDate              | -1       |
      |vclDueDate               | -1       |
      |channel                  | CommonHelp |
      |InbDocType               |Health Insurance Documents|
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
      |advanceSearch |true|
      |dateOfContact |-45 |
      |applicationId |getFromCreatePage|
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click on Link button in task search
    And I click on refresh button
    And I verify that Inbound Correspondence Page has all "Application SR" linked objects
    And I click id of "Verification Document" in link section of inbound page
    Then I verify "Verification Document" task fields are displayed
    Then I verify "Verification Document" link section has all the business object linked : "Service Request,Consumer,Inbound Correspondence"
    And I click id of "Application SR" in Links section
    Then I will verify "Application SR" Details view SR details page
    And I click on edit service request button
    And I verify "applicationType" single select drop down value
      | Emergency Medicaid Services Application - CVIU |
      |Expedited Application - CVIU                    |
      | MAGI - CPU  |
      | MAGI - PW               |
      | MAGI Standard Application - CVIU    |
      | Pre-Release Application - CVIU      |
      | Re-Entry Application - CVIU |
    And I will update the following information in edit task page
      |reasonForEdit            |Entered Additional Info  |
      |status                   |Closed                   |
      |applicationReceivedDate  |today                    |
      |channel                  |CommonHelp               |
      |noOfApplicantsInHousehold|1                        |
      |missingInfoRequired      |No                       |
      |applicationStatus        |Medicare Review Needed   |
      |disposition              |Cancelled                |
      |facilityType             |Department of Juvenile Justice|
    And I click on save button on task edit page
    Then I verify the updated information in view sr details page
    And I click id of "Verification Document" in Links section
    Then I verify task's status is Cancelled
    And I click id of "Application SR" in Links section
    And I click on edit service request button
    Then I verify "disposition" single select drop down value
      | Approved      |
      | Auto-Approved |
      | Auto-Denied   |
      | Cancelled     |
      | Denied        |
      | Referred      |
    Then I verify text box Date and Time field value and error message for following fields
      |Application Id|
    And Close the soft assertions

  @CP-22740 @CP-22740-02 @CP-22739 @CP-22739-01 @CP-22739-02 @CP-21021 @CP-21021-01 @CP-31982 @CP-31982-01 @priyal @chandrakumar @kamil @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate process application Application type matches value Expedited Application - CVIU
    When I will get the Authentication token for "<projectName>" in "CoverVA"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      |applicationType          |Expedited Application - CVIU|
      |externalApplicationId    | random                     |
      |myWorkSpaceDate          | today                      |
      |channel                  | CommonHelp                 |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      |advanceSearch |true|
      |dateOfContact |today|
      |applicationId |getFromCreatePage|
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify task search result has my work space date which we provide the search parameter
    And In search result click on task id to navigate to view page
    Then I will verify "Application SR" Details view SR details page
    And I navigated to the Edit History tab ON Edit Task
    And I navigated to the sr Details tab ON Edit Task
    And I click on edit service request button
    And I will update the following information in edit task page
      |reasonForEdit            |Entered Additional Info  |
      |applicationReceivedDate  |today                    |
      |channel                  |CommonHelp               |
      |noOfApplicantsInHousehold|1                        |
      |missingInfoRequired      |No                       |
      |applicationStatus        |Medicare Review Needed   |
      |facilityType             |Department of Juvenile Justice|
      |myWorkSpaceDate          |-1                      |
      | caseWorkerFirstName     | TagTest                |
      | caseWorkerLastName      | Demo                   |
    And I click on save button on task edit page
    Then I will verify "Application SR" Details view SR details page
    And I click id of "Process Application" in Links section
    Then I verify "Process Application" task fields are displayed
    And I navigated to the Edit History tab ON Edit Task
    And I navigated to the Task Details tab ON Edit Task
    And Close the soft assertions