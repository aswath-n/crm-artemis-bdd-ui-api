@apiTaskMgmt
Feature: API: General Task Feature

  @API-CRM-964-02 @API-CRM @API-CRM-SmokeTest @API-CRM-Regression @API-WF @task-manag-ms-WM @shruti
  Scenario Outline: Create a Task by searching task type by name
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated get task type by name
    When I provide project id as "" and task type name as "General" to get task type
    And I run get task type by name API
    And I initiated create task API
    When I provide task information to create task
    And I run create task API
    Then I verify task created successfully
    Examples:
      | projectName |
      |             |

  @API-CRM-970-01 @API-CRM-968-01 @api-smoke-devops @API-WF @API-CRM @shruti @API-CRM-971-01  @task-manag-ms-WM @API-CRM-Regression
  Scenario Outline: Search for Tasks assigned to a user - POST /mars/taskmanagement/tasks
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 970 |
    When I initiated get task list by staff assigned to
    And I provide staff assigned to as "970.consumerId"
    And I run get task list by staff assigned to
    Then I verify task record details retrieved by get api
    Examples:
      | projectName |
      | BLCRM       |


  @API-CP-138-02 @test123 @API-CP-1230-01 @API-CP-1250-01 @API-WF @API-CRM @aswath @task-manag-ms-WM @API-CRM-SmokeTest @API-CRM-Regression
  Scenario Outline: Create a Task by getting task types for project
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated get task types API for project ""
    When I run get task type API
    And I get task type id for task type name "General"
    And I initiated create task API
    When I provide task information to create task
    And I run create task API
    Then I verify task created successfully
    And I initiate task
    Then I provide task information to initiate task "<taskStatus>"
    And I run initiate task API
    Then I verify task initaited successfully "<taskStatus>"
    Examples:
        |taskStatus|projectName|
        |Cancelled||
        |In-Progress||
        |Complete||
        |Escalated||
        |OnHold||

    #Save Task Search Post API
  @API-CP-831 @API-CP-831-1 @API-CRM @Vidya @API-WF @API-CRM @task-manag-ms-WM @API-CRM-SmokeTest @API-CRM-Regression
  Scenario Outline:Verify Save Task Search Post API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Save Task Search API
    When I can provide search criteria and task save name
    And I run the Save Task Search API
    Then I can get API response status as "success"
    Examples:
      | projectName |
      |             |

    #Saved Task Search GET API
  @API-CP-830 @API-CP-830-1 @API-CP-829 @API-CP-829-1 @API-CRM @Vidya @API-WF @task-manag-ms-WM @API-CRM-SmokeTest @API-CRM-Regression
  Scenario Outline:Verify Saved Task Search Get API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I create one save task search
    When I initiated Saved Task Search Get API
    And I run the Saved Task Search Get API
    Then I can get API response status as "success"
    And I verify that response has save task search which I newly created
    Examples:
      | projectName |
      | CoverVA     |

    #Saved Task Search Delete POST API
  @API-CP-832 @API-CP-832-1 @API-CRM @Vidya @API-WF @API-CRM @task-manag-ms-WM @API-CRM-SmokeTest @API-CRM-Regression
  Scenario Outline:Verify Saved Task Search Get API_1
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I create one save task search
    When I get the task search id from newly created record
    And I initiated Delete Saved Task Search API
    When I can provide Delete information
    And I run the Delete Saved Task Search API
    Then I can get API response status as "success"
    And I verify deleted task search id is not there in Saved Task Search GET API response
    Examples:
      | projectName |
      | CoverVA     |

    #Saved Task Search GET API
  @API-CP-9077 @API-CP-9077-01 @API-CRM @Vidya @API-CRM-SmokeTest @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline:Verify response of inquiry get api
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate create Inquiry task APi
    And I provide inquiry task details as "<caseId>" "<msgSubject>" "<msgBody>" "<phone>" "<email>" "<channel>"
    Then I run the create Inquiry task API
    When I initiated Inquiries Get API for "<caseId>" and "<channel>" from "" and size "100"
    And I run the Inquiries Get API
    Then I verify the response of inquiry task has "<caseId>" "<phone>" "<email>" and other details
    Examples:
      |projectName|caseId|msgSubject|msgBody|phone     |email         |channel|
      || 20   | 10       | 30    |9823674501|test@gmail.com|Web    |

  @API-CP-9077 @API-CP-9077-02 @API-CRM @Vidya @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline:Verify from and size fields of inquiry get api
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Inquiries Get API for "<caseId>" and "<channel>" from "" and size ""
    And I run the Inquiries Get API
    And Store record from 5 position to 15
    And I initiated Inquiries Get API end point as from "5" and size "10"
    And I run the Inquiries Get API
    Then Check we return record result from 5th to 15th position
    Examples:
      |projectName|caseId|channel|
      || 20   |Web    |

  @API-CP-9077 @API-CP-9077-03 @API-CRM @Vidya @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline:Verify error code and error message in response when wrong caseId is pass the url
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Inquiries Get API for "<caseId>" and "<channel>" from "" and size ""
    And I run the Inquiries Get API
    Then verify errorCode and errorMessage is return in response
    Examples:
      |projectName|caseId|channel|
      ||50000 |Web    |

  @API-CP-9077 @API-CP-9077-04 @API-CRM @Vidya @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline:Verify sorting order of inquiry get api
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Inquiries Get API for "<caseId>" and "<channel>" from "" and size ""
    And I run the Inquiries Get API
    Then verify sorting order of taskId descending
    Examples:
      |projectName|caseId|channel|
      ||20    |Web    |

  @API-CP-12203 @API-CP-12203-01 @API-CP-10679 @API-CP-10679-01 @API-CP-9269 @API-CP-9269-01 @API-CP-26388 @API-CP-26388-03 @API-CRM @Vidya @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline:Verify task information  of external api with only required fields
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "Correspondence" for project ""
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      |  |
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    And I initiated get task by task id ""
    And I run get task by task id API
    Then I verify Task table information in response of external task
    And I will verify task details and external link table data is empty
    Then I initiated get task by "" for task history
    And I run get task by task id API
    And I verify Task history table information in response of external task
    Examples:
      |projectName|caseId|consumerId|inboundId|taskInfo|triggerDate|
      ||      ||         ||           |

  @API-CP-12203 @API-CP-12203-02 @API-CRM @Vidya @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline:Verify external api gives 400 response when we pass default priority in request body
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I will provide required information and defaultPriority to create external task
    And I initiated external create task api
    Then I run the create external task API and check the status code is "400"
    Examples:
      |projectName|
      ||


  @API-CP-9716 @API-CP-9716-01 @API-CP-26388 @API-CP-26388-04 @API-CRM @Vidya @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline:Verify 3k Char Value is stored in Task Management Dbs
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate create Inquiry task APi
    And I provide inquiry task details as "<caseId>" "<msgSubject>" "<msgBody>" "<phone>" "<email>" "<channel>"
    Then I run the create Inquiry task API
    Then I initiated get task by "" for task history
    And I run get task by task id API
    Then I store task history response
    And I initiated get task by task id ""
    And I run get task by task id API
    Then I store task details response
    Then In response I will check the "<msgBody>" value in Task Details and Task Details History table
    Examples:
      |projectName|caseId|msgSubject|msgBody|phone     |email         |channel|
      || 20   | 10       | 3000  |9823674501|test@gmail.com|Web    |

  @API-CP-9716 @API-CP-9716-02 @API-CP-26388 @API-CP-26388-05 @API-CRM @Vidya @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline: Verify 4k Char Value is stored in Task Management Dbs
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "inquiry/complaint" for project ""
    And I initiated create task API
    When I provide task information and "<msgBody>" to create task
    And I run create task API
    Then I verify task created successfully
    Then I initiated get task by "" for task history
    And I run get task by task id API
    Then I store task history response
    And I initiated get task by task id ""
    And I run get task by task id API
    Then I store task details response
    Then In response I will check the "<msgBody>" value in Task Details and Task Details History table
    Examples:
      |projectName|msgBody|
      ||4000   |

  @API-CP-9076 @API-CP-9076-01 @API-CRM @Vidya @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline:Verify Missing required parameters Error Message
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate create Inquiry task APi
    And I provide inquiry task details as "<caseId>" "<msgSubject>" "<msgBody>" "<phone>" "<email>" "<channel>"
    And I run the create Inquiry task API
    Then I verify Missing required parameters Error Message in response
    Examples:
      |projectName|caseId|msgSubject|msgBody|phone        |email          |channel|
      || 20   | 10       | 30    |982367450    |test@gmail.com |Web    |
      || 20   | 141      | 30    |9823674501   |test@gmail.com |Web    |
      || 20   | 10       | 3001  |9823674501   |test@gmail.com |Web    |
      || 20   | 10       | 30    |9823674501   |testgmail.com  |Web    |
      || 20   | 10       | 30    |982-367-45012|test@gmail.com |Web    |
      || 20   | 10       | 30    |9823674501   |test@gmail.fwuf|Web    |
      ||      | 10       | 30    |9823674501   |test@gmail.com |Web    |
      || 20   | 10       | 30    |9823674501   |test@gmail.com ||

  @API-CP-9076 @API-CP-9076-02 @API-CP-11959 @API-CP-11959-01 @API-CP-26388 @API-CP-26388-06 @API-CRM @Vidya @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline:Verify record is stored in task, task details, task details history and external link table
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "inquiry/complaint" for project ""
    When I initiate create Inquiry task APi
    And I provide inquiry task details as "<caseId>" "<msgSubject>" "<msgBody>" "<phone>" "<email>" "<channel>"
    And I run the create Inquiry task API
    Then I initiated get task by "" for task history
    And I run get task by task id API
    And I verify Task history table information in response of external task
    Then I store task history response
    And I initiated get task by task id ""
    And I run get task by task id API
    Then I store task details response
    Then I verify task table record for the task
    And I verify External Link table information in response has "<caseId>"
    And I verify Task Details and Task Details History table information in response "<phone>" "<email>" "<channel>"
   Examples:
      |projectName|caseId|msgSubject|msgBody|phone     |email         |channel|
      || 20   | 10       | 30    |9823674501|test@gmail.com|Web    |

  @API-CP-9076 @API-CP-9076-03 @API-CRM @Vidya @API-CRM-SmokeTest @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline:Verify Response structure after saving the task
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate create Inquiry task APi
    And I provide inquiry task details as "<caseId>" "<msgSubject>" "<msgBody>" "<phone>" "<email>" "<channel>"
    And I run the create Inquiry task API
    Then I verify the response structure has "<caseId>"  "<phone>" "<email>"
    Examples:
      |projectName|caseId|msgSubject|msgBody|phone     |email         |channel|
      || 20   | 10       | 30    |9823674501|test@gmail.com|Web    |
      || 20   || 30    ||              |Web    |

  @API-CP-27055 @API-CP-27055-01 @API-CRM @vidya @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario: Verify warning message displayed as ture for fetching more then 500 records
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiate task search post APi
    And I will provide below information to search a task
      | source | User |
    And I run the task search post APi
    Then I verify following information in task search response
      | moreResults | true    |
      | count       | 500     |
      | status      | success |

  @API-CP-27055 @API-CP-27055-02 @API-CRM @vidya @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario: Verify warning message displayed as false for fetching less then 500 records
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiate task search post APi
    And I will provide below information to search a task
      | taskStatus      | Created |
      | staffAssignedTo | userID  |
    And I run the task search post APi
    Then I verify following information in task search response
      | moreResults | false         |
      | count       | less then 500 |
      | status      | success       |
