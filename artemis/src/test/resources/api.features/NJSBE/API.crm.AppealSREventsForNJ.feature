@nj-appeal-events
Feature: Appeal SR Events For NJ-SBE

  @CP-24422 @CP-24422-02 @CP-24424 @CP-24424-02 @CP-30299 @CP-30299-08 @ruslan @kamil @events-wf
  Scenario Outline: Validate TASK_SAVE_EVENT for Appeal SR, Review Appeals Form and GCNJ Appeals Acknowledgement Letter tasks
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | NJ SBE Appeal Form       |
    When I send the request to create the Inbound Correspondence Save Event
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for SR has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId       |
      | TASK_SAVE_EVENT | apiClassUtilTraceId |

  @CP-25110 @CP-25110-02 @CP-24423 @CP-24423-02 @kamil @events-wf
  Scenario Outline: Validate LINK_EVENTs for Appeal SR and Tasks created for it and linked inbound document 
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | NJ SBE Appeal Form       |
      |ConsumerId    |2160                      |
      |CaseId        |741                       |
    When I send the request to create the Inbound Correspondence Save Event
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "ECMS Side,taskToSR,taskToSR,srToTask,srToTask,inbToSR,srToInb,taskToInb,taskToInb,inbToTask,inbToTask,srToConsumer,consumerToSR,caseToSR,srToCase,taskToConsumer,taskToConsumer,consumerToTask,consumerToTask,taskToCase,taskToCase,caseToTask,caseToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName  | correlationId |
      |LINK_EVENT | traceId       |

  @CP-24429 @CP-24429-03 @kamil @events-wf
  Scenario Outline: Verify TASK_SAVE_EVENT and TASK_UPDATE_EVENT is generated for Follow up on appeal task generated for appeal sr
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | NJ SBE Appeal Form       |
      |ConsumerId    |2161                      |
      |CaseId        |742                       |
    When I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      |assignee                 |Service AccountOne|
      | reasonForEdit           |Corrected Data Entry|
      | taskInfo                |Validate Follow up on appeal task is created for appeal sr|
      | actionTaken             |No Action Taken|
      | disposition             |IDR Unsuccessful|
    And I click on save button on task edit page
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    Then I verify TASK_UPDATE_EVENT for Task has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName        | correlationId |
      |TASK_UPDATE_EVENT| Task_update   |

  @CP-28293 @CP-28293-03 @CP-24425 @CP-24425-03 @CP-25296 @CP-25296-06 @ruslan @kamil @events-wf
  Scenario Outline: Verify TASK_SAVE_EVENT is generated for GCNJ Resolve Appeal task generated for appeal sr and check TASK_UPDATE_EVENT is generated for appeal sr
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | NJ SBE Appeal Form       |
      |ConsumerId    |2165                      |
      |CaseId        |743                       |
    When I send the request to create the Inbound Correspondence Save Event
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | taskInfo                |Validate GCNJ Resolve Appeal task is created for appeal sr|
      | actionTaken             |Outbound Call Successful|
      | disposition             |IDR Successful|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName        | correlationId |
      |TASK_UPDATE_EVENT| taskId        |

  @CP-24430 @CP-24430-03 @kamil @events-wf
  Scenario Outline: Verify LINK_EVENTs are generated for Follow up on appeal task generated for appeal sr
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | NJ SBE Appeal Form       |
      |ConsumerId    |2169                      |
      |CaseId        |744                       |
    When I send the request to create the Inbound Correspondence Save Event
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will navigate to newly created task by clicking on task id
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      |assignee                 |Service AccountOne|
      | reasonForEdit           |Corrected Data Entry|
      | taskInfo                |Validate Follow up on appeal task is created for appeal sr|
      | actionTaken             |No Action Taken|
      | disposition             |IDR Unsuccessful|
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    And I click id of "Appeal" in Links section
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "ECMS Side,taskToSR,srToTask,taskToInb,inbToTask,taskToConsumer,consumerToTask,taskToCase,caseToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName  | correlationId |
      |LINK_EVENT | Task_update   |

  @CP-25286 @CP-25286-03 @CP-28293 @CP-28293-05 @CP-25296 @CP-25296-07 @CP-11947 @CP-11947-02 @ruslan @events-wf
  Scenario Outline: Verify Task_update_event for Appeal sr closed when Follow-Up on Appeal task is completed with disposition IDR Successful
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | NJ SBE Appeal Form       |
      |ConsumerId    |2173                      |
      |CaseId        |745                       |
    When I send the request to create the Inbound Correspondence Save Event
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will navigate to newly created task by clicking on task id
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | taskInfo                |Validate Follow up on appeal task is created for appeal sr|
      | actionTaken             |No Action Taken|
      | disposition             |IDR Unsuccessful|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click id of "Appeal" in Links section
    And I click id of "Follow-Up on Appeal" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | taskInfo                |Validate Appeal sr is closed when Follow-up on appeal task is completed|
      | actionTaken             |No Action Taken|
      | disposition             |IDR Successful|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId              |
      | TASK_UPDATE_EVENT | disposition_idr_successful |

  @CP-25286 @CP-25286-04 @CP-25672 @CP-25672-02 @ruslan @kamil @events-wf
  Scenario Outline: Verify Task save event for GCNJ Resolve Appeal task
    Given I logged into CRM with "Service Account 8" and select a project "NJ-SBE"
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | NJ SBE Appeal Form       |
      |ConsumerId    |2174                      |
      |CaseId        |746                       |
    When I send the request to create the Inbound Correspondence Save Event
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    When If any In Progress task present then update that to Cancelled
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on task id on the task slider
    And I click on the priority in dashboard
    And I will update the following information in task slider
      |status       |Complete|
      |actionTaken  |Outbound Call Unsuccessful|
      |disposition  |IDR Unsuccessful|
    And I click on save in Task Slider
#    Then I verify Success message is displayed for task update
    And In search result click on task id to navigate to view page
    And I click id of "Appeal" in Links section
    And I store sr id on edit page
    And I click id of "Follow-Up on Appeal" in Links section
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    When If any In Progress task present then update that to Cancelled
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on task id on the task slider
    And I click on the priority in dashboard
    And I will update the following information in task slider
      | status      | Complete                   |
      | actionTaken | Outbound Call Unsuccessful |
      | disposition | IDR Unsuccessful           |
    And I click on save in Task Slider
    And In search result click on task id to navigate to view page
    And I click id of "Appeal" in Links section
    When I click id of "GCNJ Resolve Appeal" in Links section
    And I initiate Event get api for trace id "<eventName>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       |
      | TASK_SAVE_EVENT |

  @CP-24428 @CP-24428-04 @kamil @events-wf
  Scenario Outline: Verify LINK_EVENTS created when GCNJ-IDR task is created
    Given I logged into CRM with "Service Account 8" and select a project "NJ-SBE"
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | NJ SBE Appeal Form       |
      |ConsumerId    |747                       |
      |CaseId        |2178                      |
    When I send the request to create the Inbound Correspondence Save Event
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    When If any In Progress task present then update that to Cancelled
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on task id on the task slider
    And I click on the priority in dashboard
    And I will update the following information in task slider
      |status       |Complete|
      |actionTaken  |Outbound Call Unsuccessful|
      |disposition  |IDR Unsuccessful|
    And I click on save in Task Slider
    Then I verify Success message is displayed for task update
    And In search result click on task id to navigate to view page
    And I click id of "Appeal" in Links section
    And I click id of "Follow-Up on Appeal" in Links section
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    When If any In Progress task present then update that to Cancelled
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on task id on the task slider
    And I click on the priority in dashboard
    And I will update the following information in task slider
      |status       |Complete|
      |actionTaken  |Outbound Call Unsuccessful|
      |disposition  |IDR Unsuccessful|
    And I click on save in Task Slider
    Then I verify Success message is displayed for task update
    And In search result click on task id to navigate to view page
    And I click id of "Appeal" in Links section
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "ECMS Side,taskToSR,srToTask,taskToInb,inbToTask,taskToConsumer,consumerToTask,taskToCase,caseToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName  | correlationId |
      |LINK_EVENT | Task_update   |

  @CP-28295 @CP-28295-05 @ruslan @events-wf
  Scenario Outline: Verify LINK_EVENTS created when Generate IDR Successful Letter task is created
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | NJ SBE Appeal Form       |
      |ConsumerId    |2182                      |
      |CaseId        |748                       |
    When I send the request to create the Inbound Correspondence Save Event
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | taskInfo                |Validate Generate IDR Successful Letter is created for appeal sr|
      | actionTaken             |Outbound Call Successful|
      | disposition             |IDR Successful|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click id of "Appeal" in Links section
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "ECMS Side,taskToSR,srToTask,taskToInb,inbToTask,taskToConsumer,consumerToTask,taskToCase,caseToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName  | correlationId |
      |LINK_EVENT | Task_update   |

  @CP-28293 @CP-28293-07 @CP-25296 @CP-25296-05 @CP-25892 @CP-25892-01 @kamil @events-wf
  Scenario Outline: Verify task save event for Generate IDR Successful Letter task and task update event for Appeal SR
    Given I logged into CRM with "Service Account 8" and select a project "NJ-SBE"
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | NJ SBE Appeal Form       |
      |ConsumerId    |2186                      |
      |CaseId        |749                       |
    When I send the request to create the Inbound Correspondence Save Event
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with taskId
    When If any In Progress task present then update that to Cancelled
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on task id on the task slider
    And I click on the priority in dashboard
    And I will update the following information in task slider
      |status       |Complete|
      |actionTaken  |Outbound Call Unsuccessful|
      |disposition  |IDR Unsuccessful|
    And I click on save in Task Slider
    #Then I verify Success message is displayed for task update
    And In search result click on task id to navigate to view page
    And I click id of "Appeal" in Links section
    And I click id of "Follow-Up on Appeal" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | taskInfo                |Validate Appeal sr is closed when Follow-up on appeal task is completed|
      | actionTaken             |Outbound Call Unsuccessful|
      | disposition             |IDR Unsuccessful|
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    And I click id of "Appeal" in Links section
    And I click id of "GCNJ Resolve Appeal" in Links section
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    When If any In Progress task present then update that to Cancelled
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on task id on the task slider
    And I click on the priority in dashboard
    And I will update the following information in task slider
      |status       |Complete|
      |disposition  |IDR Successful|
    And I click on save in Task Slider
    #Then I verify Success message is displayed for task update
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName        | correlationId |
      |TASK_UPDATE_EVENT| disposition   |

  @CP-28295 @CP-28295-06 @ruslan @events-wf
  Scenario Outline: Verify Link_Events Appeal SR to Generate IDR Successful Letter Task when GCNJ Resolve Appeal task completed from edit page
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | NJ SBE Appeal Form       |
      |ConsumerId    |2194                      |
      |CaseId        |751                       |
    When I send the request to create the Inbound Correspondence Save Event
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will navigate to newly created task by clicking on task id
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | taskInfo                |Validate Follow up on appeal task is created for appeal sr|
      | actionTaken             |No Action Taken|
      | disposition             |IDR Unsuccessful|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click id of "Appeal" in Links section
    And I click id of "Follow-Up on Appeal" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | taskInfo                |Validate Appeal sr is closed when Follow-up on appeal task is completed|
      | actionTaken             |No Action Taken|
      | disposition             |IDR Successful|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I click on id of "Appeal" in Links section of "TASK" page
    And I verify link events are generated for a "SR" from "ECMS Side,taskToSR,srToTask,taskToInb,inbToTask,taskToConsumer,consumerToTask,taskToCase,caseToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName  | correlationId |
      |LINK_EVENT | taskId        |

  @CP-28295 @CP-28295-07 @ruslan @events-wf
  Scenario Outline: Verify Link_Events Appeal SR to Generate IDR Successful Letter Task when GCNJ Resolve Appeal task completed from task slider
    Given I logged into CRM with "Service Account 8" and select a project "NJ-SBE"
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | NJ SBE Appeal Form       |
      |ConsumerId    |2195                      |
      |CaseId        |752                       |
    When I send the request to create the Inbound Correspondence Save Event
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with taskId
    When If any In Progress task present then update that to Cancelled
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on task id on the task slider
    And I click on the priority in dashboard
    And I will update the following information in task slider
      |status       |Complete|
      |actionTaken  |Outbound Call Unsuccessful|
      |disposition  |IDR Unsuccessful|
    And I click on save in Task Slider
    And In search result click on task id to navigate to view page
    And I click id of "Appeal" in Links section
    And I click id of "Follow-Up on Appeal" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | taskInfo                |Validate Appeal sr is closed when Follow-up on appeal task is completed|
      | actionTaken             |Outbound Call Unsuccessful|
      | disposition             |IDR Unsuccessful|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click id of "Appeal" in Links section
    And I click id of "GCNJ Resolve Appeal" in Links section
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on task id on the task slider
    And I click on the priority in dashboard
    And I will update the following information in task slider
      |status       |Complete|
      |disposition  |IDR Successful|
    And I click on save in Task Slider
    And In search result click on task id to navigate to view page
    And I click id of "Appeal" in Links section
    And I store sr id on edit page
    And I click on id of "Generate IDR Successful Letter" in Links section of "SR" page
    And I store task id on edit page
    And I initiate Event get api for trace id "<eventName>"
    And I will run the Event GET API and get the payload
    And I verify "<eventName>" generated for "Generate IDR Successful Letter" task : "TASK TO SERVICE_REQUEST,SERVICE_REQUEST TO TASK,TASK TO INBOUND_CORRESPONDENCE,INBOUND_CORRESPONDENCE TO TASK,TASK TO CONSUMER,CONSUMER TO TASK,TASK TO CASE,CASE TO TASK"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName  |
      | LINK_EVENT |

  @CP-28302 @CP-28302-02 @CP-25286 @CP-25286-02 @ruslan @kamil @events-wf
  Scenario Outline: Verify task save event for Generate IDR Unsuccessful Letter task and task update event for Appeal SR
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | NJ SBE Appeal Form       |
      |ConsumerId    |2203                      |
      |CaseId        |754                       |
    When I send the request to create the Inbound Correspondence Save Event
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with taskId
    When If any In Progress task present then update that to Cancelled
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on task id on the task slider
    And I click on the priority in dashboard
    And I will update the following information in task slider
      |status       |Complete|
      |actionTaken  |Outbound Call Unsuccessful|
      |disposition  |IDR Unsuccessful|
    And I click on save in Task Slider
    And In search result click on task id to navigate to view page
    And I click id of "Appeal" in Links section
    And I click id of "Follow-Up on Appeal" in Links section
    And I click on edit button on view task page
    And I scroll up the page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | taskInfo                |Validate Appeal sr is closed when Follow-up on appeal task is completed|
      | actionTaken             |Outbound Call Unsuccessful|
      | disposition             |IDR Unsuccessful|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click id of "Appeal" in Links section
    And I click id of "GCNJ Resolve Appeal" in Links section
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | disposition             |IDR Unsuccessful|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for Task has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I verify TASK_UPDATE_EVENT for SR has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName        | correlationId |
      |TASK_UPDATE_EVENT| taskId        |

  @CP-28303 @CP-28303-02 @kamil @events-wf
  Scenario Outline: Verify Link_Events Appeal SR to Generate IDR UnSuccessful Letter Task
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | NJ SBE Appeal Form       |
      |ConsumerId    |2207                      |
      |CaseId        |755                       |
    When I send the request to create the Inbound Correspondence Save Event
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with taskId
    When If any In Progress task present then update that to Cancelled
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on task id on the task slider
    And I click on the priority in dashboard
    And I will update the following information in task slider
      |status       |Complete|
      |actionTaken  |Outbound Call Unsuccessful|
      |disposition  |IDR Unsuccessful|
    And I click on save in Task Slider
    Then I verify Success message is displayed for task update
    And In search result click on task id to navigate to view page
    And I click id of "Appeal" in Links section
    And I click id of "Follow-Up on Appeal" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | taskInfo                |Validate Appeal sr is closed when Follow-up on appeal task is completed|
      | actionTaken             |Outbound Call Unsuccessful|
      | disposition             |IDR Unsuccessful|
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    And I click id of "Appeal" in Links section
    And I click id of "GCNJ Resolve Appeal" in Links section
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | disposition             |IDR Unsuccessful|
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "ECMS Side,taskToSR,srToTask,taskToInb,inbToTask,taskToConsumer,consumerToTask,taskToCase,caseToTask"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName  | correlationId |
      |LINK_EVENT | taskId        |