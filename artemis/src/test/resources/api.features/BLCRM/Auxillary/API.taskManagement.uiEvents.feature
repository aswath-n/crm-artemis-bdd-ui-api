Feature: UI-TaskManagement: Events

  @CP-4751 @CP-4751-1 @CP-12274 @CP-12274-1 @moldir @kamil @events-wf @events_smoke_level_two
  Scenario Outline: Validation of TASK_SAVE_EVENT
    Given I logged into CRM
    When I navigate to "General" task page
    And I select task type as "" priority as "" assignee as "Service AccountOne" and I enter task info as "Task Info"
    And I click on save button in create task page
    #Then I verify Success message is displayed for task
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task save event payloads has correct data
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName       | correlationId | projectName |
      | TASK_SAVE_EVENT | Created       |             |

  @CP-4751 @CP-4751-2 @CP-337 @CP-337-1 @Vidya @events-wf @events_smoke_level_two
  Scenario Outline: Validation of TASK_UPDATE_EVENT
    Given I logged into CRM
    When I navigate to "My Task" page
    And I will ensure assignee contains at least one "General" task if not I will create task
    And I will get the index of "General" task and click on initiate button for that
    And I update the task status in task slider as "<status>"
    And I click on save in Task Slider
    Then I verify navigate back to My Task or Work Queue page
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task update event payloads has correct data
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName         | correlationId | projectName | status    |
      | TASK_UPDATE_EVENT | Task_update   |             | Escalated |

  @CP-1230 @CP-1250 @CP-1230-1 @CP-1250-1 @vidya @events-wf
  Scenario Outline: Validation of TASK_UPDATE_EVENT
    Given I logged into CRM
    When I navigate to "My Task" page
    And I ensure My task page has at least one task with status other than "<status>" and I navigate to view task
    And I click on edit button on view task page
    And I changed status on edit page to "<status1>"
    And I select value in "<field>" drop down
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task update "<status>" event payloads has correct data
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | status    | status1   | field             | eventName         | correlationId | projectName |
      | Onhold    | OnHold    | REASON FOR HOLD   | TASK_UPDATE_EVENT | Task_update   |             |
      | Cancelled | Cancelled | REASON FOR CANCEL | TASK_UPDATE_EVENT | Task_update   |             |

  @CP-5774 @CP-5774-1 @kamil @events-wf
  Scenario Outline: Validation of TASK_SAVE_EVENT of All Fields task
    Given I logged into CRM
    When I navigate to "All Fields" task page
    And I will provide following information before creating task
      | actionTaken                       | No Action Taken              |
      | applicationId                     | random                       |
      | applicationSource                 | Phone                        |
      | appointmentTime                   | 03:28 PM                     |
      | appointmentDate                   | today                        |
      | caseWorkerFirstName               | TagTest                      |
      | caseWorkerLastName                | Demo                         |
      | channel                           | Phone                        |
      | dateOfBirth                       | today                        |
      | fromName                          | fromName abc                 |
      | fromPhone                         | 9654345676                   |
      | fromEmail                         | fromemail123@gmail.com       |
      | inboundCorrespondenceId           | 76545                        |
      | InbDocType                        |                              |
      | inboundCorrespondenceWorkableFlag | true                         |
      | informationType                   | Consumer Profile Information |
      | invalidAddressReason              | PO Box Closed                |
      | newAddressLine1                   | newAddLine1 123              |
      | newAddressCity                    | Austin                       |
      | newAddressLine2                   | newAddLine2 234              |
      | newAddressState                   | Montana                      |
      | newAddressZipCode                 | 1009                         |
      | notificationId                    | 234567                       |
      | oldAddressLine1                   | oldAddLine1 12               |
      | oldAddressCity                    | Denver                       |
      | oldAddressLine2                   | oldAddLine2 23               |
      | oldAddressState                   | Colorado                     |
      | oldAddressZipCode                 | 1010                         |
      | outBoundCorrespondenceId          | 656554                       |
      | outreachLocation                  | Location 2                   |
      | planChangeReason                  | Plan Change Reason 1         |
      | planStartDate                     | today                        |
      | planId                            | 87                           |
      | planName                          | Wellcare                     |
      | preferredCallBackDate             | today                        |
      | preferredCallBackTime             | 02:20 PM                     |
      | language                          |                              |
      | preferredPhone                    | 8765678765                   |
      | program                           | CHIP                         |
      | providerAddressCity               | Canaan                       |
      | providerCounty                    | Albany                       |
      | providerAddressLine1              | providerAddLine1 345         |
      | providerAddressLine2              | providerAddLine2 654         |
      | providerAddressState              | Colorado                     |
      | providerAddressZipCode            | 20171                        |
      | providerFirstName                 | proFN abc                    |
      | providerLastName                  | proLN abc                    |
      | providerNpi                       | 567895678                    |
      | providerPhone                     | 9678765456                   |
      | providerStateId                   | 1223                         |
      | contactReason                     | Materials Request            |
      | requestedNewProviderChkBx         | true                         |
      | urgentAccessToCareChkBx           | true                         |
      | returnedMailReason                | Mailbox Full                 |
      | requestedNewPlanChkBx             | true                         |
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify All Fields task event payloads has correct data
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName       | correlationId | projectName |
      | TASK_SAVE_EVENT | Created       |             |

  @CP-12694 @CP-12694-1 @CP-5190 @CP-5190-06 @kamil @events-wf
  Scenario Outline: Verify TASK_SAVE_EVENT with Escalated flag
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Emma" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I create "General" task with priority "" assignee as "" and task info as "Task Info" and status as "Escalated"
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task save event payloads has correct data for "Escalated" task
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | projectName | eventName       | correlationId | status    |
      |             | TASK_SAVE_EVENT | escalated     | escalated |

  @CP-137 @CP-137-5 @kamil @events-wf
  Scenario Outline: Verify TASK_UPDATE_EVENT are generated
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I navigate to "My Task" page
    When If any In Progress task present then update that to Cancelled
    And I will ensure assignee contains at least one "General" task if not I will create task
    And I will get the index of "General" task and click on initiate button for that
    When I enter text in task note field
    And I click on save in Task Slider
    Then I Verify user is navigate back to My task page
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task update event with status "<status>"
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName         | correlationId | projectName | status |
      | TASK_UPDATE_EVENT | Task_update   |             | Open   |

  @CP-10930 @CP-10930-03 @Vidya @events-wf
  Scenario Outline: Validate task update event has external application id object in task details array
    Given I logged into CRM with "Mailroom Specialist" and select a project "NJ-SBE"
    When I navigate to "My Task" page
    And I will ensure assignee contains at least one "Inbound Application Data Entry" task if not I will create task
    And I click task id to get the results in descending order
    And I will get the index of "Inbound Application Data Entry" task and click on initiate button for that
    Then Verify task slider is displayed
    And Verify External Application Id accept only 30 character
    And I click on save in Task Slider
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task update event has external application id
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName         | correlationId | projectName | status |
      | TASK_UPDATE_EVENT | Task_update   | NJ-SBE      | Open   |

  @CP-10196 @CP-10196-1 @kamil @events-wf @CP-10196-event
  Scenario Outline: Verify TASK_SAVE_EVENT for DPBI with Disposition
    Given I logged into CRM
    When I navigate to "General Two" task page
    And I will provide following information before creating task
      | Status      | Complete    |
      | disposition | User closed |
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task save event with status Complete and Disposition
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName       | correlationId | projectName |
      | TASK_SAVE_EVENT | Complete      |             |

  @CP-10196 @CP-10196-2 @CP-11947 @CP-11947-01 @kamil @events-wf @CP-10196-event
  Scenario Outline: Verify TASK_UPDATE_EVENT for DPBI
    Given I logged into CRM
    When I navigate to "My Task" page
    And I will ensure assignee contains at least one "General Two" task if not I will create task
    Then I will get the index of "General Two" task and click on initiate button for that
    And I update the task status in task slider as "<status>"
    Then I staying same UI and selecting value "<disposition>" from Disposition field and saving
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task update event with status "<status>" and Disposition is "<disposition>"
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName         | correlationId | projectName | status   | disposition |
      | TASK_UPDATE_EVENT | Task_update   |             | Complete | User closed |

  #refactorBy: priyal on 25-10-2021
  @CP-11619 @CP-11619-1 @ruslan @events-wf
  Scenario Outline: Validation of TASK_SAVE_EVENT of NJ task
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "NJ" task page
    And I will provide following information before creating task
      | complaintAbout        | Exchange         |
      | name                  | Mark taylor      |
      | externalApplicationId | ExtAPPID123      |
      | externalCaseId        | ExtCaseID123     |
      | reason                | Customer Service |
    And I click on save button in create task page
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify NJ task event payload has correct data
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName       | correlationId | projectName |
      | TASK_SAVE_EVENT | Created       |             |

    #refactorBy:Vidya Date:24-02-2020
  @CP-134 @CP-134-1 @vidya @events-wf
  Scenario Outline: Verification of Task update event for complete
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "My Task" page
    When I will get the Authentication token for "<projectName>" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I will ensure assignee contains at least one "General" task if not I will create task
    And I will get the index of "General" task and click on initiate button for that
    When I enter text in task note field
    And I will update the following information in task slider
      | status      | Complete    |
      | disposition | User closed |
    And I click on save in Task Slider
    Then I verify navigate back to My Task or Work Queue page
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task update event with status "<status>"
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName         | correlationId | projectName | status   |
      | TASK_UPDATE_EVENT | Task_update   |             | Complete |

  @CP-13599 @CP-13599-1 @Basha @events-wf
  Scenario Outline: Validation of TASK_SAVE_EVENT for Incomplete contact record task type
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "fnMGpjs" and Last Name as "lnhSLAW"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Incomplete Contact Record" task page
    Then I verify create task fields displayed on screen
    And I will provide following information before creating task
      | taskInfo | Test CP-13599      |
      | assignee | Service AccountOne |
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task save event payloads has correct data
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName       | correlationId | projectName |
      | TASK_SAVE_EVENT | Created       |             |

  @CP-13599 @CP-13599-2 @Basha @events-wf
  Scenario Outline: Verify LINK_EVENT when task is link to contact record, case and consumer for Incomplete contact record task type
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "fnMGpjs" and Last Name as "lnhSLAW"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Incomplete Contact Record" task page
    Then I verify create task fields displayed on screen
    And I will provide following information before creating task
      | taskInfo | Test CP-13599      |
      | assignee | Service AccountOne |
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId for LINK Event and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify link event from task to case
    And I will get "taskToConsumer" from traceId for LINK Event and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify link event from task to consumer
    And I will get "taskToContactRecord" from traceId for LINK Event and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify link event from task to contact record
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | projectName | eventName  | correlationId |
      |             | LINK_EVENT | taskToCase    |

  @CP-16063 @CP-16063-07 @vidya @events-wf
  Scenario Outline: Validate action taken field in create task and view task details page navigating from My task
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Review Appeal Form" task page
    And I will provide following information before creating task
      | taskInfo    | Test CP 16149                                                                                                                      |
      | Status      | Complete                                                                                                                           |
      | disposition | Resolved                                                                                                                           |
      | actionTaken | Acknowledgement Letter Generated,IDR Resolved Letter Generated,No Action Taken,Outbound Call Successful,Outbound Call Unsuccessful |
    And I click on save button in create task page
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task save event payloads has correct data
    And I will verify action taken values in event task details array
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName       | correlationId | projectName |
      | TASK_SAVE_EVENT | Complete      |             |

  @CP-145 @CP-145-04 @vidya @events-wf
  Scenario Outline: Verify TASK_UPDATE_EVENT when we cancel the task from task slider
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    And I will provide following information before creating task
      | assignee | Service AccountOne |
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    When I will get the Authentication token for "<projectName>" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I click on initiate randomly
    And I click on cancel button on task slider
    And I click on continue on task details warning window
    Then I verify navigate back to My Task or Work Queue page
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task update event with status "<status>"
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName         | correlationId | projectName | status |
      | TASK_UPDATE_EVENT | Task_update   |             | Open   |

  @CP-13089 @CP-13089-4 @elvin @moldir @events-wf
  Scenario Outline: Verify TASK_UPDATE_EVENT for Action Taken field update
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Review Appeal Form" task page
    And I will provide following information before creating task
      | assignee | Service AccountOne |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    When I will get the Authentication token for "<projectName>" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status      | Complete                                                                                                                           |
      | actionTaken | Acknowledgement Letter Generated,IDR Resolved Letter Generated,No Action Taken,Outbound Call Successful,Outbound Call Unsuccessful |
      | disposition | Resolved                                                                                                                           |
    And I click on save in Task Slider
    And I initiate Event get api for trace id "<correlationId>"
    And I run the Event GET API and get the payload
    And I will verify action taken values in event task details array
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for TASK has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId | projectName |
      | TASK_UPDATE_EVENT | Task_update   | BLCRM       |

  @CP-25116 @CP-25116-02 @crm-regression @events-wf @ozgen
  Scenario Outline: IN-EB: Verification of Task Save Event for HCC Outbound Call Service Request
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HCC Outbound Call SR" service request page
    And I will provide following information before creating task
      | taskInfo       | Test CP-25116 |
      | preferredPhone | 5468902314    |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for SR has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId | projectName |
      | TASK_SAVE_EVENT | Open          | IN-EB       |

  @CP-25119 @CP-25119-02 @crm-regression @events-wf @ozgen
  Scenario Outline: IN-EB: Verification of Task Save Event for HCC Outbound Call Task
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HCC Outbound Call SR" service request page
    And I will provide following information before creating task
      | taskInfo       | Test CP-25119 |
      | preferredPhone | 5468902314    |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId | projectName |
      | TASK_SAVE_EVENT | Open          | IN-EB       |

  @CP-25121 @CP-25121-03 @events-wf @ozgen
  Scenario Outline: IN-EB: Verification of Link Events for HCC Outbound Call Task SR when it is linked to consumer
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    Then I store external consumer id from Demographic Info Page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "HCC Outbound Call SR" service request page
    And I will provide following information before creating task
      | taskInfo       | Test CP-25121-01 |
      | preferredPhone | 8762349011       |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "Outside of Contact"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName  | correlationId |
      | LINK_EVENT | Open          |

  @CP-25121 @CP-25121-04 @events-wf @ozgen
  Scenario Outline: IN-EB: Verification of Link Events for HCC Outbound Call Task SR when it is linked to case and consumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Janer" and Last Name as "Loren"
    When I expand the record to navigate Case Consumer Record from manual view
    Then I store external consumer id from Demographic Info Page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "HCC Outbound Call SR" service request page
    And I will provide following information before creating task
      | taskInfo       | Test CP-25121-01 |
      | preferredPhone | 8762349011       |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "Outside of Contact"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName  | correlationId |
      | LINK_EVENT | Open          |

  @CP-25120 @CP-25120-01 @ozgen @events-wf
  Scenario Outline: IN-EB: Validate Link Events generated consumer/case/sr/task for HCC Outbound Call SR
    Given I will get the Authentication token for "IN-EB" in "CRM"
    When I created a case consumer to link contact record
    And I send API CALL for Create CaseConsumer
    And I initiated Link API
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    And I searched consumer created by api script
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "HCC Outbound Call SR" service request page
    And I will provide following information before creating task
      | taskInfo | Test CP-25120 Events |
    And I click on save button in create service request page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to view SR details page by clicking on sr id
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "Outside of Contact"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName  | correlationId | projectName |
      | LINK_EVENT | Open          | IN-EB       |

  @CP-25122 @CP-25122-01 @ozgen @events-wf
  Scenario Outline: IN-EB: Validate Task Save Event after disposition is selected Needs Plan Selection for HCC Outbound Call SR
    Given I will get the Authentication token for "IN-EB" in "CRM"
    When I created a case consumer to link contact record
    And I send API CALL for Create CaseConsumer
    And I initiated Link API
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    And I searched consumer created by api script
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "HCC Outbound Call SR" service request page
    And I will provide following information before creating task
      | taskInfo | Test CP-25120 Events |
    And I click on save button in create service request page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    When I click id of "HCC Outbound Call" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | No Action Taken      |
      | disposition   | Needs Plan Selection |
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

  @CP-26114 @CP-26114-01 @ozgen @events-wf
  Scenario Outline: IN-EB: Validate Task Update Event after HCC Outbound Call SR is updated as Closed
    Given I will get the Authentication token for "IN-EB" in "CRM"
    When I created a case consumer to link contact record
    And I send API CALL for Create CaseConsumer
    And I initiated Link API
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    And I searched consumer created by api script
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "HCC Outbound Call SR" service request page
    And I click on save button in create service request page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    When I click id of "HCC Outbound Call" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | No Action Taken      |
      | disposition   | Needs Plan Selection |
    And I click on save button on task edit page
    When I click id of "HCC Outbound Call SR" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | status        | Closed               |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Invalid              |
    And I click on save button on task edit page
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_UPDATE_EVENT for Task has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId | projectName |
      | TASK_UPDATE_EVENT | Task_update   | IN-EB       |

  @CP-25124 @CP-25124-03 @events @ozgen-wf
  Scenario Outline: Verification of Task Update Event for closing HCC Outbound Call SR After Task Completion
    Given I will get the Authentication token for "IN-EB" in "CRM"
    When I created a case consumer to link contact record
    And I send API CALL for Create CaseConsumer
    And I initiated Link API
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    And I searched consumer created by api script
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "HCC Outbound Call SR" service request page
    And I will provide following information before creating task
      | taskInfo | Test CP-25124 events |
    And I store external case id from Create Task Links Component
    And I store external consumer id from Create Task Links Component
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    When I click id of "HCC Outbound Call" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | No Action Taken      |
      | disposition   | Plan Selection Made  |
    And I click on save button on task edit page
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_UPDATE_EVENT for Task has all the proper data
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId | projectName |
      | TASK_UPDATE_EVENT | Task_update   | IN-EB       |

  @CP-22015 @CP-22015-04 @priyal @moldir @events-wf
  Scenario Outline: Validate task update event has for any task
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Cancelled            |
      | reasonForCancel       | Created Incorrectly  |
      | reasonForEdit         | Corrected Data Entry |
      | priority              | 2                    |
      | assignee              | Service AccountOne   |
      | taskInfo              | CP-22015-06          |
      | preferredCallBackDate | today                |
      | preferredCallBackTime | 03:28 PM             |
      | preferredPhone        | 1234567890           |
      | disposition           | Consumer not reached |
      | name                  | Alewa Papovich       |
      | contactReason         | Courtesy Call Back   |
    And I click on save button on task edit page
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for Task has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "TASK_UPDATE_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "TASK_UPDATE_EVENT" publish to DPBI
    Examples:
      | taskId | taskType      | srType | status   | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy          | createdOn | correlationId  | contactReason |
      |        | Outbound Call |        | Complete |            |          |         |            |        |                |            | true          |            |            |        |          | Service AccountOne |           | disposition_NJ |               |

  @CP-27696 @CP-27696-09 @CP-22677 @CP-22677-09 @ruslan @events-wf
  Scenario Outline: Validate LINK_EVENT & UNLINK_EVENT for Manually Link Service Request to Service Request for CoverVA
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to "Complaint SR" service request page
    And I will provide following information before creating task
      | taskInfo | Test CP-18749 |
      | priority | 5             |
    And I click on save button in create service request page
    When I navigate to "App SR V1 " service request page
    And I will provide following information before creating task
      | taskInfo              | Test fsafasfa                  |
      | applicationType       | Pre-Release Application - CVIU |
      | externalApplicationId | random                         |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","Complaint SR","Open","<statusDate>","5","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","true","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","today","<contactReason>"
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
    And I populate required fields to do task search "<taskId>","<taskType>","App SR V1 ","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And I click task id to get the results in descending order in Task Search
    And I scroll down the page
    And I click on Link button in task search
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    And I store sr id on edit page
    And I initiate Event get api for trace id "manually_link_sr_to_sr"
    And I will run the Event GET API and get the payload
    And I verify "LINK_EVENT" generated for "Customer Service Complaint" task : "SERVICE_REQUEST TO SERVICE_REQUEST,SERVICE_REQUEST TO SERVICE_REQUEST"
    And I will check "LINK_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "LINK_EVENT" publish to DPBI
    And I click on save button on task edit page
    And I click on edit service request button
    And I unlink "App SR V1 " from link section
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    And I initiate Event get api for trace id "manually_unlink_sr_from_sr"
    And I will run the Event GET API and get the payload
    And I verify "UNLINK_EVENT" generated for "Customer Service Complaint" task : "SERVICE_REQUEST TO SERVICE_REQUEST,SERVICE_REQUEST TO SERVICE_REQUEST"
    And I will check "UNLINK_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "UNLINK_EVENT" publish to DPBI
    Examples:
      | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      |        |          |        |        |            |          |         |            |        |                |            | true          |            |            |        |          |           | today     |               |

  @CP-27696 @CP-27696-10 @CP-22677 @CP-22677-10 @ruslan @events-wf
  Scenario Outline: Validate LINK_EVENT & UNLINK_EVENT for Manually Link Service Request to Service Request for BLCRM
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to "General Service Request" service request page
    And I will provide following information before creating task
      | taskInfo | First GSR |
      | priority | 3         |
    And I click on save button in create service request page
    When I navigate to "General Service Request" service request page
    And I will provide following information before creating task
      | taskInfo | Second GSR |
      | priority | 5          |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","General Service Request","Open","<statusDate>","3","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","true","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","today","<contactReason>"
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
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And I click task id to get the results in descending order in Task Search
    And I click on Link button in task search
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    And I store sr id on edit page
    And I initiate Event get api for trace id "manually_link_sr_to_sr"
    And I will run the Event GET API and get the payload
    And I verify "LINK_EVENT" generated for "General Service Request" task : "SERVICE_REQUEST TO SERVICE_REQUEST,SERVICE_REQUEST TO SERVICE_REQUEST"
    And I will check "LINK_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "LINK_EVENT" publish to DPBI
    And I click on edit service request button
    And I will update the following information in edit task page
      | taskInfo      | Un linking General Service Request from General Service Request SR |
      | reasonForEdit | Corrected Data Entry                                               |
    And I unlink "General Service Request" from link section
    And I click on save button on task edit page
    And I initiate Event get api for trace id "manually_unlink_sr_from_sr"
    And I will run the Event GET API and get the payload
    And I verify "UNLINK_EVENT" generated for "General Service Request" task : "SERVICE_REQUEST TO SERVICE_REQUEST,SERVICE_REQUEST TO SERVICE_REQUEST"
    And I will check "UNLINK_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "UNLINK_EVENT" publish to DPBI
    Examples:
      | taskId | taskType | srType                  | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      |        |          | General Service Request |        |            | 5        |         |            |        |                |            | true          |            |            |        |          |           | today     |               |

  @CP-27696 @CP-27696-11 @CP-22677 @CP-22677-11 @ruslan @events-wf
  Scenario Outline: Validate LINK_EVENT & UNLINK_EVENT for Manually Link Service Request to Service Request for NJ-SBE
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | Channel      | Mail                   |
      | documentType | NJ SBE Appeal Form     |
    When I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And In search result click on task id to navigate to view page
    And I click on id of "Appeal" in Links section of "TASK" page
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | Channel      | Mail                   |
      | documentType | NJ SBE Appeal Form     |
    When I send the request to create the Inbound Correspondence Save Event
    And I click on cancel button on task search page
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click taskId column in the task search result
    And I click on Link button in task search
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    And I store sr id on edit page
    And I initiate Event get api for trace id "manually_link_sr_to_sr"
    And I will run the Event GET API and get the payload
    And I verify "LINK_EVENT" generated for "Appeal" task : "SERVICE_REQUEST TO SERVICE_REQUEST,SERVICE_REQUEST TO SERVICE_REQUEST"
    And I will check "LINK_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "LINK_EVENT" publish to DPBI
    And I click on edit service request button
    And I will update the following information in edit task page
      | taskInfo      | Un linking Appeal SR from Appeal SR |
      | reasonForEdit | Corrected Data Entry                |
    And I unlink "Appeal" from link section
    And I click on save button on task edit page
    And I initiate Event get api for trace id "manually_unlink_sr_from_sr"
    And I will run the Event GET API and get the payload
    And I verify "UNLINK_EVENT" generated for "Appeal" task : "SERVICE_REQUEST TO SERVICE_REQUEST,SERVICE_REQUEST TO SERVICE_REQUEST"
    And I will check "UNLINK_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "UNLINK_EVENT" publish to DPBI
    Examples:
      | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      |        |          | Appeal | Open   |            |          |         |            |        |                |            | true          |            |            |        |          |           | today     |               |