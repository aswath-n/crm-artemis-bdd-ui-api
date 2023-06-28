@applicationSREvents
Feature: Application SR Events For CoverVA

  @CP-19363 @CP-19363-03 @CP-18636 @CP-18636-01 @moldir @events-wf
  Scenario Outline: Validate TASK_SAVE_EVENT for Application SR created from ui
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      |taskInfo                 | Test CP-18013|
      |applicationType          | MAGI - CPU   |
      |externalApplicationId    | random       |
      |externalCaseId           | 111222333    |
      |channel                  | CommonHelp   |
      |missingInfoRequired      | No           |
      |caseWorkerFirstName      | James        |
      |caseWorkerLastName       | Bond         |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for SR has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId |
      | TASK_SAVE_EVENT | Open          |

  @CP-18636 @CP-18636-02 @kamil @events-wf
  Scenario Outline: Validate TASK_SAVE_EVENT for Application SR created from ECMS
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Application"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | VACV Application         |
    When I send the request to create the Inbound Correspondence Save Event
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for SR has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId |
      | TASK_SAVE_EVENT | traceId       |

  @CP-18677 @CP-18677-03 @vidya @events-wf
  Scenario Outline: Verify Link Events Created for an Process Application task created from Application SR (outside of Active Contact)
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
      | channel               | CommonHelp |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I navigate to newly created SR in Task & Service Request Tab
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "Outside of Contact"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName | correlationId |
      |LINK_EVENT| Open          |

  @CP-18013 @CP-18013-06 @kamil @events-wf
  Scenario Outline: Verify Link Events Created for an Application SR created from UI
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Joseph" and Last Name as "Archer"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      | taskInfo              |Test CP-18013-06|
      |applicationType        |Re-Entry Application - CVIU|
      |externalApplicationId  | random                     |
      |myWorkSpaceDate        | today                      |
      | channel               | CommonHelp |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "Active Contact"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName | correlationId |
      |LINK_EVENT| Open          |

  @CP-22735 @CP-22735-5 @CP-18554 @CP-18554-2 @kamil @events-wf
  Scenario Outline: Validate in TASK_UPDATE_EVENT priority set to 1 for Process Application task created when Application SR with Application type=e MAGI - PW
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      |applicationType          |MAGI - PW|
      |externalApplicationId    | random|
      |myWorkSpaceDate          | today|
      | channel               | CommonHelp |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    Then I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName        | correlationId |
      |TASK_UPDATE_EVENT| Open          |

   @CP-19449 @CP-19449-5 @moldir @events-wf
   Scenario Outline: Validate TASK_SAVE_EVENT for Missing Info task
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
       | taskInfo                |Test CP-18013  |
       |applicationType          |Pre-Release Application - CVIU|
       | externalApplicationId   |random         |
     And I click on save button in create service request page
     #Then I verify Success message is displayed for SR
     And I Verify user is navigate back to Task and service Request Page
     And I navigate to newly created SR in Task & Service Request Tab
     And I navigate to view SR details page by clicking on sr id
     And I will click on back arrow on view task page
     And I navigate to view SR details page by clicking on sr id
     And I click id of "Process App V1" in Links section
     And I click on edit button on view task page
     And I will update the following information in edit task page
       | status                  |Complete|
       | reasonForEdit           |Corrected Data Entry|
       | myWorkSpaceDate         | -2                 |
       | applicationReceivedDate | today              |
       | applicationSignatureDate| today              |
       |noOfApplicantsInHousehold| 1                  |
       | channel                 | Paper              |
       | applicationStatus       | Data Collection    |
       | actionTakenSingle       | Sent VCL           |
       | disposition             | Pending MI         |
       | noOfApprovedApplicants  | 1                  |
       | missingInfoRequired     | No                 |
       | decisionSource          | Manual             |
     And I click on save button on task edit page
     #Then I verify Success message is displayed for task update
     When I will get the Authentication token for "CoverVA" in "CRM"
     And I initiate Event get api for trace id "<correlationId>"
     And I will run the Event GET API and get the payload
     And I verify TASK_SAVE_EVENT for Task has all the proper data
     And I verify task details information in task save event for coverVA Task or SR
     And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
     Then I will verify "<eventName>" publish to DPBI
     Examples:
       | eventName       | correlationId   |
       | TASK_SAVE_EVENT | TASK_SAVE_EVENT |

  @CP-19571 @CP-19571-03 @vidya @events-wf
  Scenario Outline: Verify Task_update_event for Application SR
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      |applicationType          |Emergency Medicaid Services Application - CVIU|
      |externalApplicationId    | random   |
      |myWorkSpaceDate          | -2       |
      | missingInfoRequired     | Yes      |
      | vclDueDate              | -1       |
      | channel               | CommonHelp |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      |advanceSearch |true|
      |dateOfContact |-2  |
      |applicationId |getFromCreatePage|
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will update the following information in edit task page
      |reasonForEdit            |Entered Additional Info  |
      |status                   |Closed                   |
      |applicationType          |MAGI Standard Application - CVIU|
      |applicationReceivedDate  |today                    |
      |channel                  |CommonHelp               |
      |noOfApplicantsInHousehold|1                        |
      |missingInfoRequired      |No                       |
      |applicationStatus        |Medicare Review Needed   |
      |disposition              |Cancelled                |
      |facilityType             |Department of Juvenile Justice|
    And I click on save button on task edit page
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId |
      | TASK_SAVE_EVENT | Task_update   |

  @CP-20589 @CP-20589-01 @ruslan @events-wf
  Scenario Outline: VA: Generate TASK_UPDATE_EVENT when User Closes or Cancels Application SR
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      | applicationType           | Emergency Medicaid Services Application - CVIU |
      | externalApplicationId     | random                                         |
      | externalCaseId            | 123456789                                      |
      | myWorkSpaceDate           | -1                                             |
      | missingInfoRequired       | Yes                                            |
      | vclDueDate                | -1                                             |
      | decisionSource            ||
      | applicationReceivedDate   | today                                          |
      | noOfApprovedApplicants    | 1                                              |
      | noOfApplicantsInHousehold | 1                                              |
      | applicationStatus         | Data Collection                                |
      | InbDocType                | Drivers License                                |
      | vclSentDate               | today                                          |
      | channel                   | CommonHelp                                     |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | dateOfContact | -1                |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit             | Entered Additional Info          |
      | status                    | Closed                           |
      | applicationType           | MAGI Standard Application - CVIU |
      | applicationReceivedDate   | today                            |
      | channel                   | CommonHelp                       |
      | noOfApplicantsInHousehold | 1                                |
      | missingInfoRequired       | No                               |
      | applicationStatus         | Medicare Review Needed           |
      | disposition               | Cancelled                        |
      | facilityType              | Department of Juvenile Justice   |
    And I click on save button on task edit page
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for SR has all the proper data
    Then I verify TASK_UPDATE_EVENT for Task has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId |
      | TASK_UPDATE_EVENT | Task_update   |