@eventSRCoverVa
Feature: SR Events For CoverVA

  @CP-18639 @CP-18639-4 @basha @events-wf
  Scenario Outline: Verify Link Events Created for an Renewal SR
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "John" and Last Name as "Mathew"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-18639          |
      | applicationType       | Ex Parte Renewal - CVIU|
      | applicationSignatureDate | today              |
      | applicationReceivedDate  | today              |
      | externalApplicationId | random                 |
      | myWorkSpaceDate       | today                  |
      | channel               | CommonHelp             |
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

  @CP-18642 @CP-18642-1 @kamil @events-wf @events
  Scenario Outline: Create Process Application Task for Renewal SR
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | taskInfo              | Test CP-18639         |
      | applicationType       |Ex Parte Renewal - CVIU|
      | applicationSignatureDate | today              |
      | applicationReceivedDate  | today              |
      | externalApplicationId | random                |
      | myWorkSpaceDate       | today                 |
      | channel               | CommonHelp            |
      | facilityName          | Bristol City Jail     |
    And I click on save button in create service request page
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    And I verify task details information for a Task which is created when SR got created
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal_V2"
    And I run bpm process get api by sr id
    And I will check response of service request bpm process
    Examples:
      | eventName       | correlationId |
      | TASK_SAVE_EVENT | Open          |

  @CP-22014 @CP-22014-03 @events-wf @priyal
  Scenario Outline: Validate CaseId filed is not present in task list on TSR Tab
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I verify status is not editable for closed sr on edit page
    And I will update the following information in edit task page
      | priority                | 2 |
      | taskInfo                | Test 22014|
      | reasonForEdit           | Corrected Data Entry|
      | externalApplicationId | OEDRFT567|
      | documentDueDate         | 07/22/2024|
      | disposition             | Resolved|
    And I click on save button on task edit page
    Then I verify success message for update service request
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |taskId |taskType|srType    |status |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|eventName          | correlationId |contactReason|
      |[blank]|        |Appeals SR|Closed |[blank]|        |[blank]|          |[blank]|              |[blank]|false        |[blank]|          |[blank]|        |[blank]|         | TASK_UPDATE_EVENT | Task_update   |[blank]|

#   refactor by priyal
  @CP-22014 @CP-22014-04 @priyal @events-wf
  Scenario Outline: verify SR is linked to consumer and contact record does not have unlink button in edit task page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And If any case consumer already linked and unlink that
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "FnCCFZu" and Last Name as "Lnhjhcu"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    Then I see radio button is associated with a Case or Consumer
    When I select Consumer radio button
    Then I see Link Record Consumer button get displayed
    When I click on Link Record Consumer button
    Then I verify task is linked with ConsumerID and CaseID
    And I will update the following information in edit task page
      | reasonForEdit |Corrected Data Entry|
      | disposition   |User closed|
    And I click on save button on task edit page
    And I verify should I navigated to view SR page
    Then I verify task is linked with ConsumerID and CaseID
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I will get "<correlationId>" from traceId for LINK Event and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify link event from task to case
    And I will get "taskToConsumer" from traceId for LINK Event and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify link event from task to consumer
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      |taskId|taskType|srType        |status |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn| eventName  | correlationId |contactReason|
      |20863 |[blank]|General Two SR|Closed |[blank]|        |[blank]|          |[blank]|              |[blank]|false        |[blank]|          |[blank]|        |[blank]|         | LINK_EVENT | taskToCase    |[blank]|

  @CP-18747 @CP-18747-02 @CP-18832 @CP-18832-04 @CP-19034 @CP-19034-03 @kamil @events-wf @moldir
  Scenario Outline: Validate LINK_EVENTs for Complaint SR or Review Complaint Task or Complaint Escalated Task
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Complaint"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | Channel      | Mail                   |
      | documentType | VACV Complaint         |
      | ConsumerId   | 121840                 |
    When I send the request to create the Inbound Correspondence Save Event
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "ECMS Side,inbToSR,srToInb,taskToSR,srToTask,taskToInb,inbToTask,taskToConsumer,srToConsumer,consumerToSR,consumerToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    And I click id of "Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | externalConsumerID      |567890765|
      | externalCaseId          |567897654321|
      | complaintType           |CPU Complaint|
      | disposition             |Escalated|
    And I click on save button on task edit page
    And I click id of "Complaint SR" in Links section
    #And I click id of "Complaint Escalation" in Links section
    And I initiate Event get api for trace id "Task_update"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "TASK" from "Complaint Sr For INEB Side,taskToSR,srToTask,taskToConsumer,consumerToTask,taskToInb,inbToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName  | correlationId      |
      |LINK_EVENT | apiClassUtilTraceId|

  @CP-19478 @CP-19478-04 @chandrakumar @events-wf
  Scenario Outline: Verify Link Events Created for an Application Renewal SR to task
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "App Renewal SR V1" service request page
    And I will provide following information before creating task
      |taskInfo              |Test CP-19478|
      |applicationType       |Renewal Application CVIU|
      |externalApplicationId |random     |
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
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      |advanceSearch |true|
      |applicationId |getFromCreatePage|
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      |status                   |Complete|
      |reasonForEdit            |Corrected Data Entry|
      |assignee                 |Service TesterTwo   |
      |taskInfo                 |Test 123            |
      |myWorkSpaceDate          |-2                 |
      |applicationReceivedDate  |today              |
      |applicationSignatureDate |today              |
      |noOfApplicantsInHousehold|1                  |
      |channel                  |CommonHelp         |
      |applicationStatus        |Data Collection    |
      |actionTakenSingle        |Sent VCL|
      |disposition              |Pending MI|
    And I click on save button on task edit page
    And I click id of "App Renewal SR V1" in Links section
    And I click id of "Missing Information" in Links section
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "Missing Information,taskToSR,srToTask,taskToConsumer,consumerToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName  | correlationId |
      |LINK_EVENT | Task_update   |

#  @CP-19477 @CP-19477-04 @ruslan @events-wf not valid CP-43127
  Scenario Outline: Verify Link Events Created for an Application SR to task
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "App SR V1 " service request page
    And I will provide following information before creating task
      | taskInfo               | Test CP-19478 |
      | applicationType        | MAGI - PW     |
      | externalApplicationId  | random        |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Jenny" and Last Name as "John"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    When I click on Link Record Consumer button
    Then I verify task is linked with Consumer ID
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | applicationId | getFromCreatePage |
      | source        | System            |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
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
      | actionTknSingle           | Sent VCL             |
      | decisionSource            | VaCMS                |
      | noOfApprovedApplicants    | 1                    |
      | missingInfoRequired       | No                   |
      | disposition               | Pending MI           |
    And I click on save button on task edit page
    And I click id of "App SR V1" in Links section
    And I store sr id on edit page
    And I click on id of "Missing Information" in Links section of "SR" page
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I click on id of "App SR V1 " in Links section of "TASK" page
    And I verify link events are generated for a "SR" from "Missing Information,taskToSR,srToTask,taskToConsumer,consumerToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName  | correlationId |
      |LINK_EVENT | Task_update   |

  @CP-19034 @CP-19034-02 @events-wf @priyal
  Scenario Outline: Validate LINK_EVENTs for Complaint Escalated and Review Complaint Task
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I have a Inbound Document that with the Inbound Document Type of "VACV Complaint"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | Channel      | Mail                   |
      | documentType | VACV Complaint         |
    When I send the request to create the Inbound Correspondence Save Event
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on contact link button under LINK section
    And I click on Case and Consumer button
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Kevin" and Last Name as "Coello"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    When I click on Link Record Consumer button
    Then I verify task is linked with Consumer ID
    And I click on save button on task edit page
    And I click id of "Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | externalConsumerID      |567890765|
      | externalCaseId          |567897654321|
      | complaintType           |CPU Complaint|
      | disposition             |Escalated|
    And I click on save button on task edit page
    And I click id of "Complaint SR" in Links section
    And I click id of "Complaint Escalation" in Links section
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "TASK" from "Complaint Sr For INEB Side,taskToSR,srToTask,taskToConsumer,consumerToTask,taskToInb,inbToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName  | correlationId |
      | LINK_EVENT | Task_update   |


  @CP-18704-events @CP-18704-01 @events-wf @kamil
  Scenario Outline: Verify Link Events Created for Task to consumer when Application SR linked to the consumer
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      | applicationType           | Re-Entry Application - CVIU |
      | externalApplicationId     | random                      |
      | myWorkSpaceDate           |  -2                         |
      | vclDueDate                |  -2                         |
      | externalCaseId            | 123654789                   |
      | channel                   | CommonHelp                  |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    And I click on id of "Application SR" in Links section of "TASK" page
    And I click on edit service request button
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Kamil" and Last Name as "shikh"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    When I click on Link Record Consumer button
    And I will update the following information in edit task page
      | reasonForEdit             | Corrected Data Entry |
      | applicationReceivedDate   | today                |
      | noOfApplicantsInHousehold | 1                    |
      | channel                   | CommonHelp           |
      | applicationStatus         | Data Collection      |
      | missingInfoRequired       | No                   |
    And I click on save button on task edit page
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify "LINK_EVENT" generated for "Process Application" task : "TASK TO CONSUMER,CONSUMER TO TASK,SERVICE_REQUEST TO CONSUMER,CONSUMER TO SERVICE_REQUEST"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName  | correlationId                      |
      | LINK_EVENT | manually_link_case_to_task_with_SR |

  @CP-18704-events @CP-18704-02 @events-wf @kamil
  Scenario Outline: Verify Link Events Created for Task to consumer when Complaint SR linked to the consumer
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Complaint SR" service request page
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    And I click on id of "Complaint SR" in Links section of "TASK" page
    And I click on edit service request button
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Kamil" and Last Name as "shikh"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    When I click on Link Record Consumer button
    And I will update the following information in edit task page
      | reasonForEdit             | Corrected Data Entry |
    And I click on save button on task edit page
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify "LINK_EVENT" generated for "Review Complaint" task : "TASK TO CONSUMER,CONSUMER TO TASK,SERVICE_REQUEST TO CONSUMER,CONSUMER TO SERVICE_REQUEST"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName  | correlationId                      |
      | LINK_EVENT | manually_link_case_to_task_with_SR |