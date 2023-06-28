@apiTaskMgmt
Feature: API: General Task Part2 Feature


  @API-CP-5774 @API-CP-5774-1 @API-CRM @Basha @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline: Create All Fields Task by getting task types for project
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated get task types API for project "<projectId>"
    When I run get task type API
    And I get task type id for task type name "All Fields"
    And I initiated create task API
    And I provide all fields task information to create task "withSingleVlu"
      | Faxed to Doctor              |
      | Phone                        |
      | 2020-06-11                   |
      | 03:28 PM                     |
      | CWFN Martin                  |
      | CWLN Jacob                   |
      | Phone                        |
      | Information Request          |
      | 2000-06-03                   |
      | External AppID 123           |
      | fromemail123@gmail.com       |
      | fromName abc                 |
      | 9654345676                   |
      | 76545                        |
      ||
      | true                         |
      | Consumer Profile Information |
      | PO Box Closed                |
      | Austin                       |
      | newAddLine1 123              |
      | newAddLine2 234              |
      | Montana                      |
      | 1009                         |
      | 234567                       |
      | Denver                       |
      | oldAddLine1 12               |
      | oldAddLine2 23               |
      | Colorado                     |
      | 1010                         |
      | 656554                       |
      | bTC5x7wrcV                   |
      | Location 2                   |
      | Plan Change Reason 1         |
      | 2021-06-17                   |
      | 876                          |
      | Wellcare                     |
      | 2020-06-17                   |
      | 2021-06-17                   |
      | 02:20 PM                     |
      | English                      |
      | 8765678765                   |
      | CHIP                         |
      | Canaan                       |
      | ALBANY                       |
      | providerAddLine1 345         |
      | providerAddLine2 654         |
      | Colorado                     |
      | 20171                        |
      | proFN abc                    |
      | proLN abc                    |
      | 567895678                    |
      | 9678765456                   |
      | Provider Record Issue 1      |
      | 1223                         |
      | true                         |
      | true                         |
      | Mailbox full                 |
      | true                         |
    And I run create task API
    Then I verify task created successfully
    And I initiated get task by task id ""
    And I run get task by task id API
    Then I verify task table record
    And I verify task details and task history details table records
    Examples:
      | projectName | projectId |
      ||           |

  @API-CP-5774 @API-CP-5774-2 @API-CRM @Basha @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline: Verify multi select value create separate task details and task details history objects
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated get task types API for project "<projectId>"
    When I run get task type API
    And I get task type id for task type name "All Fields"
    And I initiated create task API
    And I provide all fields task information to create task "withMultipleVlu"
      | Faxed to Doctor              |
      | Forwarded to State Worker    |
      | Phone                        |
      | 2020-06-11                   |
      | 03:28 PM                     |
      | CWFN Martin                  |
      | CWLN Jacob                   |
      | Phone                        |
      | Information Request          |
      | Update Information Request   |
      | Materials Request            |
      | Missing Information Request  |
      | Other                        |
      | 2000-06-03                   |
      | External AppID 123           |
      | fromemail123@gmail.com       |
      | fromName abc                 |
      | 9654345676                   |
      | 76545                        |
      ||
      | true                         |
      | Consumer Profile Information |
      | PO Box Closed                |
      | Austin                       |
      | newAddLine1 123              |
      | newAddLine2 234              |
      | Montana                      |
      | 1009                         |
      | 234567                       |
      | Denver                       |
      | oldAddLine1 12               |
      | oldAddLine2 23               |
      | Colorado                     |
      | 1010                         |
      | 656554                       |
      | bTC5x7wrcV                   |
      | Location 1                   |
      | Location 2                   |
      | Location 3                   |
      | Plan Change Reason 1         |
      | 2021-06-17                   |
      | 876                          |
      | Wellcare                     |
      | 2020-06-17                   |
      | 2021-06-17                   |
      | 02:20 PM                     |
      | English                      |
      | 8765678765                   |
      | CHIP                         |
      | Canaan                       |
      | ALBANY                       |
      | providerAddLine1 345         |
      | providerAddLine2 654         |
      | Colorado                     |
      | 20171                        |
      | proFN abc                    |
      | proLN abc                    |
      | 567895678                    |
      | 9678765456                   |
      | Provider Record Issue 1      |
      | 1223                         |
      | true                         |
      | true                         |
      | Mailbox full                 |
      | true                         |
    And I run create task API
    Then I verify task created successfully
    And I initiated get task by task id ""
    And I run get task by task id API
    Then I verify task table record
    And I verify task details and task history details table records
    Examples:
      | projectName | projectId |
      ||           |

  @API-CP-5774 @API-CP-5774-3 @API-CP-11619 @API-CP-11619-3 @API-CRM @Basha @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline: Verify API should require grouped fields to all be populated if one field in a group is populated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated get task types API for project "<projectId>"
    When I run get task type API
    And I get task type id for task type name "All Fields"
    And I initiated create task API
    And I provide all fields task information to create task "missingField"
      | Faxed to Doctor |
    And I run create task API for error file
    Then I verify error message is getting in response as "<errorCode>"
    Examples:
      | projectName | projectId | errorCode                                                        |
      ||           | Must pass all fields in PROVIDER Field Group to complete request |

  @API-CP-10679 @API-CP-10679-02 @API-CP-11959 @API-CP-11959-02 @API-CP-9269 @API-CP-9269-02 @API-CP-18206 @API-CP-18206-01 @API-CRM @Vidya @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline:Verify record is stored in task, task details, task details history and external link table
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "Correspondence" for project ""
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      | Notification ID            |
      | Test 1234                  |
      | Outbound Correspondence ID |
      | Test 123                   |
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    Then I initiated get task by "" for task history
    And I run get task by task id API
    And I verify Task history table information in response of external task
    Then I store task history response
    And I initiated get task by task id ""
    And I run get task by task id API
    Then I store task details response
    Then I verify Task table information in response of external task
    And I verify External Link table information in response has "<caseId>" "<consumerId>" "<inboundId>"
    And I verify Task Details and Task Details History table information in response
    Examples:
      | projectName | caseId | consumerId | inboundId | taskInfo | triggerDate |
      || 68     | 1446       | 12357     | CP-9269  | currentDate |

  @API-CP-11959 @API-CP-11959-03 @API-CRM @Basha @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline:Verify record is stored in task, task details, task details history and external link table when running without traceID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "inquiry/complaint" for project ""
    When I initiate create Inquiry task APi
    And I provide inquiry task details as "<caseId>" "<msgSubject>" "<msgBody>" "<phone>" "<email>" "<channel>"
    And I run the create Inquiry task API without traceId
    Then I initiated get task by "" for task history
    And I run get task by task id API
    Then I store task history response
    And I initiated get task by task id ""
    And I run get task by task id API
    Then I store task details response
    Then I verify Task table information in response
    And I verify External Link table information in response has "<caseId>"
    And I verify Task Details and Task Details History table information in response "<phone>" "<email>" "<channel>"
    Examples:
      | projectName | caseId | msgSubject | msgBody | phone      | email          | channel |
      || 20     | 10         | 30      | 9823674501 | test@gmail.com | Web     |

  @API-CP-964-01 @API-CP-118-01 @API-CP-118 @API-CRM @API-CRM-Regression @API-WF @task-manag-ms-WM @aswat @vidya
  Scenario Outline: Create task and verify that in task table and task history table
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated get task types API for project ""
    When I run get task type API
    And I get task type id for task type name "General"
    And I initiated create task API
    When I provide task information to create task with custom data "<Assignee>"
    And I run create task API
    Then I verify task created successfully
    And I initiated get task by task id ""
    And I run get task by task id API
    Then I verify task table record
    Then I initiated get task by "" for task history
    And I run get task by task id API
    And I verify Task history table information in response
    Examples:
      | projectName | Assignee         |
      || null             |
      || Service Account1 |

  @API-CP-13599 @API-CP-13599-01 @API-CP-16499 @API-CP-16499-01 @API-CRM @vidya @Basha @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario: validate all the fields of incomplete contact record task responce file
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate Incomplete task put APi
    And I will provide below information to create incomplete task using api
      | createdBy               | currentUser    |
      | externalRefIdCase       | 7899           |
      | externalRefTypeCase     | CASE           |
      | externalRefIdConsumer   | 18673          |
      | externalRefTypeConsumer | CONSUMER       |
      | externalRefIdCR         | 137374         |
      | externalRefTypeCR       | CONTACT_RECORD |
    Then I run the Incomplete task put APi and verify the response
    Then I initiated get task by "" for task history
    And I run get task by task id API
    And I verify Task history table information in response of incomplete contact record task
    Then I store task history response
    And I initiated get task by task id ""
    And I run get task by task id API
    Then I verify Task table information in response of incomplete contact record task
    And I verify External Link table information in response for incomplete CR Task
    Then I store task details response
    And I verify Task Details and Task Details History table information in incomplete response

  @API-CP-735 @API-CP-735-01 @API-WF @API-CRM @vidya @task-manag-ms-WM @API-CRM-Regression
  Scenario Outline: Work Queue Task List - POST /mars/taskmanagement/workqueue/tasks
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I will get the task type ids for "<role>" role permission
    Given I initiated get task list for work queue
    When I provide task type ids for "<role>" permission
    And I run work queue post api to get the list of task
    Then I verify all task records status in the response for "<role>"
    Examples:
      | projectName | role    |
      || Csr     |
      || Non Csr |

  @API-CP-18206 @API-CP-18206-02 @Vidya @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline:Verify external end point does not create task for Future or Inactive task types
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "<taskType>" for project ""
    When I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      ||
    And I initiated external create task api
    Then I verify task is not created for Future or Inactive task types
    Examples:
      | projectName | caseId | consumerId | inboundId | taskInfo | triggerDate | taskType             |
      || 68     | 1446       | 12357     | CP-18206 | currentDate | Future Task          |
      || 68     | 1446       | 12357     | CP-18206 | currentDate | Inactive Task Type   |
      || 68     | 1446       | 12357     | CP-18206 | currentDate | First Name Last Name |


  @API-CP-37691 @API-CP-37691-01 @API-CP-37905 @API-WF @API-CRM @kamil @task-manag-ms-WM @API-CRM-Regression
  Scenario: Work Queue Task Retrieval API Includes User's Business Unit
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiated Work Queue Task Retrieval API
    Then I run Work Queue Task Retrieval API call with data
      | businessUnits[0]        | 11    |
      | taskTypes[0].taskTypeId | 16351 |
    And Validate Work Queue API call logged with businessUnitId as part of the payload request
      | businessUnitAssignedTo | 11    |
      | taskTypeId             | 16351 |
      | taskId                 | 22362 |