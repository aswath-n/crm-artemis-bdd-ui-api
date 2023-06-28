@eventTaskCoverVa
Feature: Task Events For CoverVA

  @CP-19349 @CP-19349-04 @CP-30299 @CP-30299-07 @events_smoke_level_two @ruslan @events-wf
  Scenario Outline: Validate TASK_SAVE_EVENT for Translation Request
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Translation Request" task page
    And I will provide following information before creating task
      | taskInfo                 | Test CP 16149                  |
      | assignee                 | Service TesterTwo              |
      | externalConsumerID       | 1234567890                     |
      | externalCaseId           | 123456789                      |
      | actionTaken              | Escalated to Translation Group |
      | informationType          | Renewal Packet                 |
      | language                 | Hindi                          |
      | dateTranslationEscalated | +1                             |
      | dateTranslationReceived  | today                          |
      | dateTranslationMailed    | -1                             |
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId |
      | TASK_SAVE_EVENT | Created       |

  @CP-19365 @CP-19365-03 @moldir @events-wf
  Scenario Outline: Validate TASK_SAVE_EVENT for DMAS to Appeals Escalation
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "DMAS to Appeals Escalation" task page
    And I will provide following information before creating task
      | taskInfo               | Test CP-19365               |
      | businessUnitAssigneeTo | EscalationBU                |
      | assignee               | Service TesterTwo           |
      | externalConsumerID     | 1234567890                  |
      | externalCaseId         | 123456789                   |
      | externalApplicationId  | Test19365                   |
      | actionTakenSingle      | Escalated to CoverVA (DMAS) |
      | contactRecordSingle    | Summary Not Sent            |
      | documentDueDate        | today                       |
      | cpCRID                 | 12345                       |
      | cpSRID                 | 12345                       |
      | cpTaskID               | 12345                       |
    And I click on save button in create task page
    #Then I verify Success message is displayed for task
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId |
      | TASK_SAVE_EVENT | Created       |

  @CP-19433 @CP-19433-03 @events-wf @vidya
  Scenario Outline: Validate TASK_SAVE_EVENT for HPE Final Decision
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "HPE Final Decision" task page
    And I will provide following information before creating task
      | taskInfo            | Test CP-19365       |
      | assignee            | Service TesterTwo   |
      | externalConsumerID  | 1234567890          |
      | actionTaken         | Corrected Exception |
      | outcome             | Final Approval      |
      | contactRecordSingle | Eligibility Status  |
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId |
      | TASK_SAVE_EVENT | Created       |

  @CP-19367 @CP-19367-04 @events-wf @vidya
  Scenario Outline: Validate TASK_SAVE_EVENT for Inbound Document
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Inbound Document" task page
    And I will provide following information before creating task
      | taskInfo              | Test CP-19367        |
      | assignee              | Service TesterTwo    |
      | externalConsumerID    | 1234567890           |
      | externalCaseId        | 123456789            |
      | externalApplicationId | Test19365            |
      | actionTaken           | Uploaded Doc to Case |
      | program               | EAP                  |
      | InbDocType            | Paper Application    |
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId |
      | TASK_SAVE_EVENT | Created       |

  @CP-19367 @CP-19367-05 @events-wf @vidya
  Scenario Outline: Validate TASK_SAVE_EVENT for Verification Document
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Verification Document" task page
    And I will provide following information before creating task
      | taskInfo              | Test CP-19367         |
      | assignee              | Service TesterTwo     |
      | externalConsumerID    | 1234567890            |
      | externalCaseId        | 123456789             |
      | externalApplicationId | Test19365             |
      | actionTaken           | Updated SR Details    |
      | InbDocType            | Citizenship Documents |
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId |
      | TASK_SAVE_EVENT | Created       |

  @CP-19358 @CP-19358-03 @ruslan @events-wf
  Scenario Outline: Validate TASK_SAVE_EVENT for LDSS Communication Form
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "LDSS Communication Form" task page
    And I will provide following information before creating task
      | taskInfo              | Test CP-19358                            |
      | assignee              | Service TesterTwo                        |
      | externalConsumerID    | 1234567890                               |
      | externalApplicationId | 123rg89j                                 |
      | externalCaseId        | 123456789                                |
      | actionTaken           | Mailed Requested Docs                    |
      | locality              | Bath                                     |
      | requestType           | Update Information on Case or Other info |
      | issueType             | Determination Incorrect (CPU Error)      |
      | caseWorkerFirstName   | Eldora Dali                              |
      | caseWorkerLastName    | Mikelangello Do                          |
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId |
      | TASK_SAVE_EVENT | Created       |

    #Not able to create Review complaint task from UI functionality removed
  #@CP-19368 @CP-19368-03 @events @elvin
  Scenario Outline: Validate TASK_SAVE_EVENT for Review Complaint
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Review Complaint" task page
    And I will provide following information before creating task
      | taskInfo           | Test CP-19368     |
      | assignee           | Service TesterTwo |
      | externalConsumerID | 1234567890        |
      | externalCaseId     | 123456789         |
      | complaintType      | LDSS              |
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId |
      | TASK_SAVE_EVENT | Created       |

  @CP-19368 @CP-19368-04 @CP-19345 @CP-19345-03 @elvin @moldir @events-wf
  Scenario Outline: Validate TASK_SAVE_EVENT for Process Returned Mail
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Process Returned Mail" task page
    And I will provide following information before creating task
      | taskInfo              | Test CP-19368           |
      | assignee              | Service TesterTwo       |
      | externalConsumerID    | 1234567890              |
      | externalCaseId        | 123456789               |
      | externalApplicationId | Test19358               |
      | InbDocType            | COC                     |
      | returnedMailReason    | Unknown                 |
      | businessUnit          | CPU                     |
      | receivedDate          | -1                      |
      | dateResent            | today                   |
      | actionTaken           | Escalated to Supervisor |
    And I click on save button in create task page
    #Then I verify Success message is displayed for task
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId |
      | TASK_SAVE_EVENT | Created       |

  @CP-19276 @CP-19276-04 @moldir @events-wf
  Scenario Outline: Validate TASK_SAVE_EVENT for Appeal Remand
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Appeal Remand" task page
    And I will provide following information before creating task
      | taskInfo              | Test CP-19367                            |
      | assignee              | Service TesterTwo                        |
      | externalCaseId        | 123456789                                |
      | externalApplicationId | Test19365                                |
      | remandReason          | Case Correction - Incorrect Alien Status |
      | eligibilityDecision   | Approved                                 |
      | remandCompletionDate  | +1                                       |
      | remandDueDate         | today                                    |
      | receivedDate          | -1                                       |
    And I click on save button in create task page
    #Then I verify Success message is displayed for task
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId |
      | TASK_SAVE_EVENT | Created       |

    #Not able to create Fair Hearing task from UI functionality removed
  #@CP-19311 @CP-19311-07 @events @vidya
  Scenario Outline: Validate TASK_SAVE_EVENT for Fair Hearing
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Fair Hearing " task page
    And I will provide following information before creating task
      | taskInfo            | Test CP-19311     |
      | assignee            | Service TesterTwo |
      | accessibilityNeeded | Hearing Impaired  |
      | appointmentDate     | today             |
      | appointmentTime     | 03:00 PM          |
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId |
      | TASK_SAVE_EVENT | Created       |

    # refactoring 19-08-2021 by priyal
  @CP-19311 @CP-19311-08 @events @vidya
  Scenario Outline: Validate TASK_SAVE_EVENT for CVIU Provider Request
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "CVIU Provider Request" task page
    And I will provide following information before creating task
      | taskInfo            | Test CP-19311           |
      | assignee            | Service TesterTwo       |
      | externalConsumerID  | 1234567890              |
      | externalCaseId      | 123456789               |
      | actionTaken         | Confirm Coverage        |
      | contactRecordSingle | Provide Medical Records |
      | facilityType        | Local Jail              |
      | facilityName        | Accomack County Jail    |
      | providerFirstName   | Vidya                   |
      | providerLastName    | Mithun                  |
      | providerPhone       | 9876543210              |
      | providerEmail       | test@gmail.com          |
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId |
      | TASK_SAVE_EVENT | Created       |

    # refactoring 19-08-2021 by priyal
  @CP-19311 @CP-19311-09 @events @vidya
  Scenario Outline: Validate TASK_SAVE_EVENT for CVIU Communication Formt
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "CVIU LDSS Communication Form" task page
    And I will provide following information before creating task
      | taskInfo              | Test CP-19311        |
      | assignee              | Service TesterTwo    |
      | externalConsumerID    | 1234567890           |
      | externalCaseId        | 123456789            |
      | externalApplicationId | Test19365            |
      | actionTaken           | Updated Application  |
      | contactRecordSingle   | Reporting Newborn    |
      | facilityType          | Local Jail           |
      | facilityName          | Accomack County Jail |
      | caseWorkerFirstName   | Eldora Dali          |
      | caseWorkerLastName    | Mikelangello Do      |
      | locality              | Bristol              |
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId |
      | TASK_SAVE_EVENT | Created       |


  @Ignored
  Scenario Outline: Test
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated get task types API for project "<projectId>"
    When I run get task type API
    And I get task type id for task type name "Appeals to DMAS Escalation"
    And I initiated create task API
    And I create request body with below field params and assign it to created user "true"
      | Task_Field_Names                  | Task_Field_Values     |
      | VACMS_CASE_ID                     | 71111                 |
      | MMIS_MEMBER_ID                    | 7222                  |
      | APPLICATION_ID                    | 73333                 |
      | CONNECTIONPOINT_CONTACT_RECORD_ID | 7444                  |
      | CONNECTIONPOINT_SR_ID             | 712343                |
      | CONNECTIONPOINT_TASK_ID           | 72343                 |
      | CONTACT_REASON                    | DMAS_APPEAL           |
      | DUE_DATE                          | 2021-07-30            |
      | ACTION_TAKEN_SINGLE               | REQUEST_COMPLETE_DMAS |
      | TASK_INFO                         | Automation Work Queue |
    And I run create task API
    Then I verify task created successfully
    Examples:
      | projectName | projectId |
      | CoverVA     |[blank]|


  @Ignored
  Scenario Outline: Test2
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated get task types API for project "<projectId>"
    When I run get task type API
    And I get task type id for task type name "APPEAL_REMAND"
    And I initiated create task API
    And I create request body with below field params and assign it to created user "true"
      | Task_Field_Names       | Task_Field_Values                           |
      | VACMS_CASE_ID          | 78687                                       |
      | APPLICATION_ID         | 8675878                                     |
      | RECEIVED_DATE          | 2021-07-27                                  |
      | REMAND_REASON          | CASE_CORRECTION_INCORRECT_INCOME_EVALUATION |
      | REMAND_DUE_DATE        | 2021-07-27                                  |
      | REMAND_COMPLETION_DATE | 2021-07-27                                  |
      | ELIGIBILITY_DECISION   | PENDING                                     |
      | TASK_INFO              | Automation Info                             |
      | ASSIGNEE               | Service TesterTwo                           |
    And I run create task API
    Then I verify task created successfully
    Examples:
      | projectName | projectId |
      | CoverVA     |[blank]|

  @Ignored
  Scenario Outline: Test3
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated get task types API for project "<projectId>"
    When I run get task type API
    And I get task type id for task type name "CVIU_LDSS_COMMUNICATION_FORM"
    And I initiated create task API
    And I create request body with below field params and assign it to created user "true"
      | Task_Field_Names       | Task_Field_Values                           |
      | VACMS_CASE_ID          | 7000                                        |
      | MMIS_MEMBER_ID         | 8000                                        |
      | APPLICATION_ID         | 9000                                        |
      | CONTACT_REASON         | CASE_CORRECTION_INCORRECT_INCOME_EVALUATION |
      | FACILITY_NAME          | INDIAN_CREEK_CORRECTIONAL_CENTER            |
      | FACILITY_TYPE          | DEPARTMENT_OF_JUVENILE_JUSTICE              |
      | CASE_WORKER_FIRST_NAME | Thomas                                      |
      | CASE_WORKER_LAST_NAME  | Green                                       |
      | ACTION_TAKEN_MULTI     | PROVIDED_CLARIFICATION_RESPONSE             |
    And I run create task API
    Then I verify task created successfully
    Examples:
      | projectName | projectId |
      | CoverVA     |[blank]|


  @Ignored
  Scenario Outline: Test4
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated get task types API for project "<projectId>"
    When I run get task type API
    And I get task type id for task type name "FOLLOW_UP_ON_APPEAL"
    And I initiated create task API
    And I create request body with below field params and assign it to created user "false"
      | Task_Field_Names   | Task_Field_Values             |
      | ACTION_TAKEN_MULTI | IDR_RESOLVED_LETTER_GENERATED |
      | TASK_INFO          | Dynamic API Testing           |
    And I run create task API
    Then I verify task created successfully
    Examples:
      | projectName | projectId |
      | NJ-SBE      |[blank]|


  @Ignored
  Scenario Outline: Test5
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated get task types API for project "<projectId>"
    When I run get task type API
    And I get task type id for task type name "INCOMPLETE_CONTACT_RECORD"
    And I initiated create task API
    And I create request body with below field params and assign it to created user "true"
      | Task_Field_Names | Task_Field_Values   |
      | TASK_INFO        | Dynamic API Testing |
    And I run create task API
    Then I verify task created successfully
    Examples:
      | projectName | projectId |
      | BLCRM       |[blank]|


  @Ignored
  Scenario Outline: Test6
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated get task types API for project "<projectId>"
    When I run get task type API
    And I get task type id for task type name "JUST_CAUSE_DECISION_LETTER"
    And I initiated create task API
    And I create request body with below field params and assign it to created user "false"
      | Task_Field_Names   | Task_Field_Values   |
      | TASK_INFO          | Dynamic API Testing |
      | ACTION_TAKEN_MULTI |[blank]|
    And I run create task API
    Then I verify task created successfully
    Examples:
      | projectName | projectId |
      | IN-EB       | IN-EB     |