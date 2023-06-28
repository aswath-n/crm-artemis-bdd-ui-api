Feature: Complaint SR Events For IN-EB

  @CP-23608 @CP-23608-02 @CP-23606 @CP-23606-02 @moldir @priyal @events-wf
  Scenario Outline: Trigger workflow when Complaint SR is created through UI
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | memberName       |Vidya Mithun|
      | preferredPhone   |1234567890|
      | complaintAbout   |maersk|
      | complaintType    |Customer Service|
      | incidentDate     |today|
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for SR has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName        | correlationId |
      |TASK_SAVE_EVENT  | Open          |

  @CP-24968 @CP-24968-03 @moldir @events-wf
  Scenario Outline: Verify Task_save_event for Supervisor Review Complaint task created for complaint sr in in-eb
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I send API CALL for Create CaseConsumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched case and consumer created by api
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | memberName       |Vidya Mithun|
      | preferredPhone   |1234567890|
      | complaintAbout   |maersk|
      | incidentDate     |today|
      | complaintType    |Customer Service|
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I click id of "QA Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | taskInfo                |Validate Complaint Sr closed when we complete the QA Review Complaint Task|
      | actionTaken             |Created Evaluation|
      | disposition             |Complaint Reviewed|
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName        | correlationId |
      |TASK_SAVE_EVENT  | Task_update   |

#  refactor by priyal
  @CP-24969 @CP-24969-02 @CP-24975 @CP-24975-09 @priyal @moldir @events-wf
  Scenario Outline: IN-EB: validate Link Events generated for Supervisor Review Complaint or Complaint Outbound Call Tasks
    Given I will get the Authentication token for "IN-EB" in "CRM"
    When I created a case consumer to link contact record
    And I send API CALL for Create CaseConsumer
    And I initiated Link API
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout      | maersk|
      | incidentDate        | today|
      | memberName          | Priyal|
      | preferredPhone      | 1234567890|
      | complaintType       | Customer Service|
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    When I click id of "QA Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Created Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "TASK" from "Complaint Sr For INEB Side,taskToSR,srToTask,taskToConsumer,consumerToTask,taskToCase,caseToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    When I click id of "Supervisor Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Provide Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "TASK" from "Complaint Sr For INEB Side,taskToSR,srToTask,taskToConsumer,consumerToTask,taskToCase,caseToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    When I click id of "Complaint Outbound Call" in Links section
    Then I verify "Complaint Outbound Call" task fields are displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Did Not Reach Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "TASK" from "Complaint Sr For INEB Side,taskToSR,srToTask,taskToConsumer,consumerToTask,taskToCase,caseToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    And I click on task id with status created in the link section
    Then I verify "Complaint Outbound Call" task fields are displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Did Not Reach Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "TASK" from "Complaint Sr For INEB Side,taskToSR,srToTask,taskToConsumer,consumerToTask,taskToCase,caseToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    And I click on task id with status created in the link section
    Then I verify "Complaint Outbound Call" task fields are displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Did Not Reach Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    Examples:
      | eventName  | correlationId |
      | LINK_EVENT | Task_update   |

#   refactor by priyal
  @CP-23607 @CP-23607-02 @priyal @moldir @events-wf
  Scenario Outline: IN-EB: validate Link Events generated for Supervisor Review Complaint Task
    Given I will get the Authentication token for "IN-EB" in "CRM"
    When I created a case consumer to link contact record
    And I send API CALL for Create CaseConsumer
    And I initiated Link API
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout      | Emergent Situation|
      | incidentDate        | today|
      | memberName          | Priyal|
      | preferredPhone      | 1234567890|
      | complaintType       | Customer Service|
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to view SR details page by clicking on sr id
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "ECMS Side,srToCase,caseToSR,srToConsumer,consumerToSR,crToSR,srToCR"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName  | correlationId | projectName |
      | LINK_EVENT | Open          | IN-EB       |

  @CP-24976 @CP-24976-03 @CP-24097 @CP-24097-06 @CP-24970 @CP-24970-03 @priyal @moldir @events-wf
  Scenario Outline: Validate Save event for 2nd and 3rd call attempt for Complaint SR for Outbound Call
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout      | maersk|
      | incidentDate        | today|
      | memberName          | Priyal|
      | preferredPhone      | 1234567890|
      | complaintType       | Customer Service|
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "","QA Review Complaint","","Created","","1","","","","","","true","","","","","","today",""
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Created Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    When I click id of "Supervisor Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Provide Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    And I initiate Event get api for trace id "actionTakenProvideSummaryReport"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Did Not Reach Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Did Not Reach Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | <disposition>|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId | projectName | disposition                  |
      | TASK_UPDATE_EVENT | Task_update   | IN-EB       | Did Not Reach Consumer       |
      | TASK_UPDATE_EVENT | Task_update   | IN-EB       | Successfully Reached Consumer|

  @CP-24976 @CP-24976-04 @CP-24097 @CP-24097-05 @CP-24970 @CP-24970-04 @priyal @moldir @events-wf
  Scenario Outline: Validate Save event for 2nd and 3rd call attempt for Complaint SR for Complaint Outbound Call
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout      | maersk|
      | incidentDate        | today|
      | memberName          | Priyal|
      | preferredPhone      | 1234567890|
      | complaintType       | Customer Service|
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "","QA Review Complaint","","Created","","1","","","","","","true","","","","","","today",""
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Created Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    When I click id of "Supervisor Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Provide Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    And I initiate Event get api for trace id "actionTakenProvideSummaryReport"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Did Not Reach Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Did Not Reach Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | <disposition>|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId | projectName | disposition                  |
      | TASK_UPDATE_EVENT | Task_update   | IN-EB       | Did Not Reach Consumer       |
      | TASK_UPDATE_EVENT | Task_update   | IN-EB       | Successfully Reached Consumer|

  @CP-24097 @CP-24097-04 @priyal @moldir @events-wf
  Scenario Outline: Validate Task Update Event for Outbound Call in Complaint SR
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout      | maersk|
      | incidentDate        | today|
      | memberName          | Priyal|
      | preferredPhone      | 1234567890|
      | complaintType       | Customer Service|
    And I click on save button in create service request page
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    When I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Created Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    When I click id of "Supervisor Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Provide Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Did Not Reach Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Successfully Reached Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    Then I verify link section contains 2 "Complaint Outbound Call"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId    | projectName |
      | TASK_UPDATE_EVENT | disposition_IN-EB| IN-EB       |

  @CP-24097 @CP-24097-10 @priyal @moldir @events-wf
  Scenario Outline: Validate Task Update Event for Outbound Call in Complaint SR
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout      | maersk|
      | incidentDate        | today|
      | memberName          | Priyal|
      | preferredPhone      | 1234567890|
      | complaintType       | Customer Service|
    And I click on save button in create service request page
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    When I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Created Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    When I click id of "Supervisor Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Provide Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Successfully Reached Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    Then I verify link section contains 1 "Complaint Outbound Call"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId     | projectName |
      | TASK_UPDATE_EVENT | disposition_IN-EB | IN-EB       |

  @CP-24097 @CP-24097-11 @priyal @moldir @events-wf
  Scenario Outline: Validate Task Update Event for Outbound Call in Complaint SR
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout      | maersk|
      | incidentDate        | today|
      | memberName          | Priyal|
      | preferredPhone      | 1234567890|
      | complaintType       | Customer Service|
    And I click on save button in create service request page
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    When I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Created Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    When I click id of "Supervisor Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Provide Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Did Not Reach Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Successfully Reached Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    Then I verify link section contains 2 "Complaint Outbound Call"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId       | projectName |
      | TASK_UPDATE_EVENT | disposition_IN-EB   | IN-EB       |

  @CP-24097 @CP-24097-12 @priyal @moldir @events-wf
  Scenario Outline: Validate Task Update Event for Outbound Call in Complaint SR
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout      | maersk|
      | incidentDate        | today|
      | memberName          | Priyal|
      | preferredPhone      | 1234567890|
      | complaintType       | Customer Service|
    And I click on save button in create service request page
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    When I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Created Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    When I click id of "Supervisor Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Provide Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Successfully Reached Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    Then I verify link section contains 1 "Complaint Outbound Call"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId    | projectName |
      | TASK_UPDATE_EVENT | disposition_IN-EB| IN-EB       |

  @CP-23606 @CP-23606-03 @priyal @moldir @events-wf
  Scenario Outline: Trigger workflow when Complaint SR is created through UI
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | memberName              |Priyal Garg|
      | preferredPhone          |1234567890|
      | complaintAbout          |maersk|
      | complaintType           |Customer Service|
      | incidentDate            |today|
      | preferredCallBackDate   |today|
      | preferredCallBackTime   |04:30 PM|
      | contactName             |Test|
      | escalated               |true|
      | escalationReason        |Consumer Contacting the State|
      | csrName                 |Service TesterTwo|
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for SR has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    Examples:
      |eventName        | correlationId |
      |TASK_SAVE_EVENT  | Open          |

  @CP-23609 @CP-23609-03 @CP-23611 @CP-23611-03 @priyal @moldir @events-wf
  Scenario Outline: Verify Link_Events for QA Review Complaint task created for complaint sr in in-eb
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I send API CALL for Create CaseConsumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched case and consumer created by api
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | memberName       |Vidya Mithun|
      | preferredPhone   |1234567890|
      | complaintAbout   |maersk|
      | complaintType    |Customer Service|
      | incidentDate     |today|
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I will click on back arrow on view task page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I click id of "QA Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | taskInfo                |Validate Complaint Sr closed when we complete the QA Review Complaint Task|
      | actionTaken             |Created Evaluation|
      | disposition             |Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    #Then I verify Success message is displayed for task update
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "TASK" from "Complaint Sr For INEB Side,taskToSR,srToTask,taskToConsumer,consumerToTask,taskToCase,caseToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit    |Corrected Data Entry |
      | escalated        |true|
      | escalationReason |Consumer Contacting the State|
    And I click on save button on task edit page
    And I will click on back arrow on view task page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    #When I click id of "State Escalated Complaint" in Links section
    And I initiate Event get api for trace id "escalatedReason"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "TASK" from "Complaint Sr For INEB Side,taskToSR,srToTask,taskToConsumer,consumerToTask,taskToCase,caseToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName   | correlationId |
      |LINK_EVENT  | Task_update   |

  @CP-24975 @CP-24975-10 @priyal @moldir @events-wf
  Scenario Outline: Trigger workflow when Complaint SR is created through UI
    Given I will get the Authentication token for "IN-EB" in "CRM"
    When I created a case consumer to link contact record
    And I send API CALL for Create CaseConsumer
    And I initiated Link API
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout      | maersk|
      | incidentDate        | today|
      | memberName          | Priyal|
      | preferredPhone      | 1234567890|
      | complaintType       | Customer Service|
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    When I click id of "QA Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Created Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    When I click id of "Supervisor Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Provide Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "TASK" from "Complaint Sr For INEB Side,taskToSR,srToTask,taskToConsumer,consumerToTask,taskToCase,caseToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Did Not Reach Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "TASK" from "Complaint Sr For INEB Side,taskToSR,srToTask,taskToConsumer,consumerToTask,taskToCase,caseToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Did Not Reach Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "TASK" from "Complaint Sr For INEB Side,taskToSR,srToTask,taskToConsumer,consumerToTask,taskToCase,caseToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Did Not Reach Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    Examples:
      | eventName  | correlationId |
      | LINK_EVENT | Task_update   |

  @CP-24970 @CP-24970-06 @priyal @moldir @events-wf
  Scenario Outline: Validate TASK_SAVE_EVENT for Complaint SR for Outbound Call with CSR name field
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout      | maersk|
      | incidentDate        | today|
      | memberName          | Priyal|
      | preferredPhone      | 1234567890|
      | complaintType       | Customer Service|
      | csrName             | Service TesterTwo|
      | priority            |3|
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    When I click id of "QA Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Created Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    When I click id of "Supervisor Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Provide Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    And I initiate Event get api for trace id "actionTakenProvideSummaryReport"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Did Not Reach Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Did Not Reach Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Did Not Reach Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    Examples:
      |taskId |taskType|srType                    |status  |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy        |createdOn|correlationId | projectName |contactReason|
      ||        |Customer Service Complaint|Open    ||3       ||          ||              ||true         ||          ||        |Service TesterTwo|today    |Task_update   | IN-EB       ||

  @CP-22015 @CP-22015-05 @priyal @moldir @events-wf
  Scenario Outline: Validate task update event has for any task in IN-EB
    When I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                |Complete|
      | reasonForEdit         |Corrected Data Entry|
      | priority              |3|
      | assignee              |Service TesterTwo|
      | taskInfo              |CP-22015-05|
      | eligibilityDecision   |Denied|
      | denialReasonSingle    |Other|
      | other                 |Test CP-30397 Working Or Not|
      | actionTaken           |Sent Denial Email|
    And I click on save button on task edit page
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for Task has all the proper data
    And I will check "TASK_UPDATE_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "TASK_UPDATE_EVENT" publish to DPBI
    Examples:
      |taskId|taskType    |srType|status   |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|correlationId |contactReason|
      ||DCS Request ||Cancelled||        ||          ||              ||             ||          ||        ||         |Task_update   ||

  @CP-25094 @CP-25094-09 @priyal @moldir @events-wf
  Scenario Outline: Validate TASK_UPDATE_EVENT for Complaint SR and Cancelled Supervisor Review Complaint Task
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout      | maersk|
      | incidentDate        | today|
      | memberName          | Priyal|
      | preferredPhone      | 1234567890|
      | complaintType       | Customer Service|
      | priority            | 5|
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    When I click id of "QA Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
      | status        | Complete             |
      | actionTaken   | Created Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit  | Corrected Data Entry|
      | status         | Closed|
      | disposition    | Complaint Invalid|
    And I click on save button on task edit page
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_UPDATE_EVENT for Task has all the proper data
    Then I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I will check "TASK_UPDATE_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "TASK_UPDATE_EVENT" publish to DPBI
    Examples:
      |taskId |taskType|srType                    |status  |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy        |createdOn|correlationId |projectName |contactReason|
      ||        |Customer Service Complaint|Open    ||5       ||          ||              ||true         ||          ||        |Service TesterTwo|today    | Task_update  |IN-EB       ||

  @CP-25094 @CP-25094-10 @priyal @moldir @events-wf
  Scenario Outline: Validate TASK_UPDATE_EVENT for Complaint SR and Cancelled Outbound Call Task
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout      | maersk|
      | incidentDate        | today|
      | memberName          | Priyal|
      | preferredPhone      | 1234567890|
      | complaintType       | Customer Service|
      | priority            | 4|
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    When I click id of "QA Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
      | status        | Complete             |
      | actionTaken   | Created Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    When I click id of "Supervisor Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
      | status        | Complete             |
      | actionTaken   | Provide Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | reasonForEdit  | Corrected Data Entry|
      | status         | Complete|
      | disposition    | Did Not Reach Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | reasonForEdit  | Corrected Data Entry|
      | status         | Complete|
      | disposition    | Did Not Reach Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit  | Corrected Data Entry|
      | status         | Closed|
      | disposition    | Complaint Invalid|
    And I click on save button on task edit page
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_UPDATE_EVENT for Task has all the proper data
    Then I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I will check "TASK_UPDATE_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "TASK_UPDATE_EVENT" publish to DPBI
    Examples:
      |taskId |taskType |srType                    |status  |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy        |createdOn|correlationId |projectName |contactReason|
      ||         |Customer Service Complaint|Open    ||4       ||          ||              ||true         ||          ||        |Service TesterTwo|today    | Task_update  |IN-EB       ||

  @CP-25094 @CP-25094-11 @priyal @moldir @events-wf
  Scenario Outline: Validate TASK_UPDATE_EVENT for Complaint SR and Cancelled QA Review Complaint Task
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout      | maersk|
      | incidentDate        | today|
      | memberName          | Priyal|
      | preferredPhone      | 1234567890|
      | complaintType       | Customer Service|
      | priority            | 3|
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit  | Corrected Data Entry|
      | status         | Closed|
      | disposition    | Complaint Invalid|
    And I click on save button on task edit page
    #And I click id of "QA Review Complaint" in Links section
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_UPDATE_EVENT for Task has all the proper data
    Then I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I will check "TASK_UPDATE_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "TASK_UPDATE_EVENT" publish to DPBI
    Examples:
      |taskId |taskType|srType                    |status  |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy        |createdOn|correlationId    |projectName |contactReason|
      ||        |Customer Service Complaint|Open    ||3       ||          ||              ||true         ||          ||        |Service TesterTwo|today    |TASK_UPDATE_EVENT|IN-EB       ||

  @CP-25094 @CP-25094-12 @priyal @moldir @events-wf
  Scenario Outline: Validate TASK_UPDATE_EVENT for Complaint SR and Cancelled Complaint Outbound Call Task
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout      | maersk|
      | incidentDate        | today|
      | memberName          | Priyal|
      | preferredPhone      | 1234567890|
      | complaintType       | Customer Service|
      | priority            | 2|
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    When I click id of "QA Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Created Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    When I click id of "Supervisor Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Provide Summary Report|
      | disposition   | Complaint Reviewed|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Did Not Reach Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Did Not Reach Consumer|
      | preferredPhone | 1234567890|
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail|
      | contactName    | Alewa Papovich|
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | status         | Closed|
      | reasonForEdit  | Corrected Data Entry|
      | disposition    | Complaint Invalid|
    And I click on save button on task edit page
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_UPDATE_EVENT for Task has all the proper data
    Then I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I will check "TASK_UPDATE_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "TASK_UPDATE_EVENT" publish to DPBI
    Examples:
      |taskId |taskType|srType                    |status  |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy        |createdOn|correlationId |projectName |contactReason|
      ||        |Customer Service Complaint|Open    ||2       ||          ||              ||true         ||          ||        |Service TesterTwo|today    | Task_update  |IN-EB       ||
