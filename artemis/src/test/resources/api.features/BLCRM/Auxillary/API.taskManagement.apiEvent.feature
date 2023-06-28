Feature: API-TaskManagement: Events

  @API-CP-9716 @API-CP-9716-03 @events @events_smoke_level_two @kamil @events-wf
  Scenario Outline:Verify 3k Char Value is stored in task save event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate create Inquiry task APi
    And I provide inquiry task details as "<caseId>" "<msgSubject>" "<msgBody>" "<phone>" "<email>" "<channel>"
    Then I run the create Inquiry task API
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task save event payloads has "<msgBody>" char data
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | projectName | caseId | msgSubject | msgBody | phone      | email          | channel | eventName       | correlationId |
      |[blank]| 20     | 10         | 3000    | 9823674501 | test@gmail.com | Web     | TASK_SAVE_EVENT | traceId       |

  @API-CP-9716 @API-CP-9716-04 @Vidya @events_smoke_level_two @kamil @events-wf
  Scenario Outline: Verify 4k Char Value is stored in Task Management Dbs
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated get task types API for project ""
    When I run get task type API
    And I get task type id for task type name "inquiry/complaint"
    And I initiated create task API
    When I provide task information and "<msgBody>" to create task
    And I run create task API
    Then I verify task created successfully
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task save event payloads has "<msgBody>" char data
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | projectName | msgBody | eventName       | correlationId |
      |[blank]| 4000    | TASK_SAVE_EVENT | traceId       |

  @API-CP-9076 @API-CP-9076-04 @API-CP-11959 @API-CP-11959-04 @moldir @kamil @events-wf @events_smoke_level_two
  Scenario Outline:Verify task save event when we create task from inquiry end point
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "inquiry/complaint" for project ""
    When I initiate create Inquiry task APi
    And I provide inquiry task details as "<caseId>" "<msgSubject>" "<msgBody>" "<phone>" "<email>" "<channel>"
    And I run the create Inquiry task API
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task save event payloads has "<phone>" "<email>" "<channel>" char data
    And I also Verify information in LINK_EVENT for "<caseId>"
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | projectName | caseId | msgSubject | msgBody | phone      | email          | channel | eventName       | correlationId |
      |[blank]| 20     | 10         | 30      | 9823674501 | test@gmail.com | Web     | TASK_SAVE_EVENT | traceId       |

  @API-CP-9269 @API-CP-9269-03 @events_smoke_level_two @kamil @events-wf
  Scenario Outline:Verify Saved Task Search Get API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "Correspondence" for project ""
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      | Notification ID            |
      | Test 1234                  |
      | Outbound Correspondence ID |
      | Test 123                   |
      | External Case ID           |
      | CaseId 123                 |
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload for task created by external api
    Then I verify link events are generated and payload has "<caseId>" "<consumerId>" "<inboundId>"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task save event payloads for task created from external api
      |Application Source|
      |From Email       |
      |From Name         |
      |From Phone        |
      |Inbound Correspondence ID|
      |Inbound Correspondence Type|
      |Invalid Address Reason     |
      |Notification ID            |
      |Outbound Correspondence ID |
      |Outbound Correspondence Type|
      |Returned Mail Reason        |
      |External Case ID            |
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | projectName | caseId | consumerId | inboundId | taskInfo | triggerDate | eventName       | correlationId |
      |[blank]| 68     | 1446       | 12357     | CP-9269  | currentDate | TASK_SAVE_EVENT | traceId1      |

  @API-CP-9269 @API-CP-9269-04 @API-CP-13133 @API-CP-13133-02 @moldir @kamil @events-wf
  Scenario Outline:Verify task save event has all dynamic fields in task details array even if we did not pass all in request boday for NJ-SBE Tenent
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "Inbound Application Data Entry" for project "NJ-SBE"
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      | Notification ID            |
      | Test 1234                  |
      | External Case ID           |
      | CaseId 123                 |
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload for task created by external api
    Then I verify link events are generated and payload has "<caseId>" "<consumerId>" "<inboundId>"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task save event payloads for task created from external api
      |Notification ID            |
      |External Case ID           |
      |External Application ID    |
      |Disposition                |
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | projectName | caseId | consumerId | inboundId | taskInfo | triggerDate | eventName       | correlationId |
      | NJ-SBE      | 68     | 1446       | 12357     | CP-9269  | currentDate | TASK_SAVE_EVENT | traceId1      |

  @API-CP-13599 @API-CP-13599-02 @Basha @events-wf
  Scenario Outline: validate both link and task save events of incomplete contact record task
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate Incomplete task put APi
    And I will provide below information to create incomplete task using api
      |createdBy              |currentUser|
      |externalRefIdCase      |7899|
      |externalRefTypeCase    |CASE|
      |externalRefIdConsumer  |18673|
      |externalRefTypeConsumer|CONSUMER|
      |externalRefIdCR        |137374|
      |externalRefTypeCR      |CONTACT_RECORD|
    Then I run the Incomplete task put APi and verify the response
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload for task created by external api
    And I verify link events are generated for incomplete contact record api
    Then I verify task save event payloads for task created from incomplete contact record api
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | projectName | eventName       | correlationId |
      | NJ-SBE      | TASK_SAVE_EVENT | traceId       |

