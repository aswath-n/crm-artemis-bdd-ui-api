@eventTaskUpdateCoverVa
Feature: Task Update Events For CoverVA

  @CP-19573 @CP-19573-03 @events-wf @vidya
  Scenario Outline: Validate TASK_UPDATE_EVENT for DMAS to Appeals Escalation
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "DMAS to Appeals Escalation" task page
    And I will provide following information before creating task
      | businessUnitAssigneeTo| EscalationBU      |
      | assignee              | Service TesterTwo |
      | contactRecordSingle   | Late Summary      |
      | documentDueDate       | today             |
      | externalApplicationId | Test19573         |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Cancelled                   |
      | reasonForCancel       | Created Incorrectly         |
      | priority              | 2                           |
      | taskInfo              | CP-19573                    |
      | contactRecordSingle   | Summary Not Sent            |
      | externalConsumerID    | 1234567890                  |
      | externalCaseId        | 123456789                   |
      | externalApplicationId | Test19573                   |
      | actionTakenSingle     | Escalated to CoverVA (DMAS) |
      | documentDueDate       | 07/22/2021                  |
      | cpCRID                | 123456                      |
      | cpSRID                | 123456                      |
      | cpTaskID              | 123456                      |
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId |
      | TASK_UPDATE_EVENT | Task_update   |

  @CP-19575 @CP-19575-03 @events-wf @elvin
  Scenario Outline: Validate TASK_UPDATE_EVENT for Inbound Document
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Inbound Document" task page
    And I will provide following information before creating task
      | InbDocType              | Complaint |
      | program                 | EAP       |
      | externalApplicationId | Test19575 |
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Cancelled           |
      | assignee              | Service TesterTwo   |
      | priority              | 2                   |
      | taskInfo              | CP-19575            |
      | externalConsumerID    | 1234567890          |
      | externalCaseId        | 123456789           |
      | externalApplicationId | UPT19575            |
      | actionTaken           | Updated Case        |
      | InbDocType            | General Inquiry     |
      | program               | Medicaid            |
      | reasonForCancel       | Created Incorrectly |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId |
      | TASK_UPDATE_EVENT | Task_update   |

  @CP-19575 @CP-19575-03 @events-wf @elvin
  Scenario Outline: Validate TASK_UPDATE_EVENT for Verification Document
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Verification Document" task page
    And I will provide following information before creating task
      | InbDocType              | Medical ID Card |
      | externalApplicationId | Test19575       |
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Cancelled             |
      | assignee              | Service TesterTwo     |
      | priority              | 2                     |
      | taskInfo              | CP-19575              |
      | externalConsumerID    | 1234567890            |
      | externalCaseId        | 123456789             |
      | externalApplicationId | UPT19575              |
      | actionTaken           | Updated SR Details    |
      | InbDocType            | Identity Verification |
      | reasonForCancel       | Duplicate Task        |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId |
      | TASK_UPDATE_EVENT | Task_update   |

  @CP-19578 @CP-19578-03 @CP-19545 @CP-19545-05 @CP-19560 @CP-19560-03 @elvin @priyal @moldir @events-wf
  Scenario Outline: Validate TASK_UPDATE_EVENT for Process Returned Mail
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Process Returned Mail" task page
    And I will provide following information before creating task
      | InbDocType              | HIPAA           |
      | returnedMailReason      | Invalid Address |
      | businessUnit            | CVIU            |
      | externalApplicationId   | Test19578       |
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Cancelled               |
      | assignee                | Service TesterTwo       |
      | priority                | 2                       |
      | taskInfo                | CP-19578                |
      | externalConsumerID      | 1234567890              |
      | externalCaseId          | 123456789               |
      | externalApplicationId   | UPT19578                |
      | actionTaken             | Escalated to Supervisor |
      | reasonForCancel         | Duplicate Task          |
      | InbDocType              | COC                     |
      | returnedMailReason      | Consumer Deceased       |
      | businessUnit            | CPU                     |
      | dateResent              | today                   |
      | receivedDate            | today                   |
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName       | correlationId |
      | TASK_UPDATE_EVENT | Task_update   |

  @CP-18030 @CP-18030-04 @CP-19567 @CP-19567-03 @priyal @moldir @events-wf
  Scenario Outline: Validate TASK_UPDATE_EVENT for LDSS Communication Form
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "LDSS Communication Form" task page
    And I will provide following information before creating task
      | externalApplicationId   | random|
      | locality                | Accomack|
      | requestType             | Request to Assign Pending App or Case to LDSS|
      | caseWorkerFirstName     | TagTest|
      | caseWorkerLastName      | Demo|
    And I click on save button in create task page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "","LDSS Communication Form","","Created","","","","","","","","true","","","","","Service TesterTwo","today",""
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Cancelled|
      | reasonForCancel         | Created Incorrectly|
      | assignee                | Service TesterTwo|
      | priority                | 2|
      | taskInfo                | CP-18030|
      | externalConsumerID      | 1234567890|
      | externalCaseId          | 123456789|
      | externalApplicationId   | GGBHY6786|
      | actionTaken             | Mailed Requested Docs|
      | locality                | Westmoreland|
      | requestType             | Report case issues|
      | issueType               | Determination Incorrect (CPU Error)|
      | caseWorkerFirstName     | Testtag|
      | caseWorkerLastName      | Demo|
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId |
      | TASK_UPDATE_EVENT | Task_update   |

  @CP-19528 @CP-19528-03 @events-wf @vidya
  Scenario Outline: Validate TASK_UPDATE_EVENT for HPE Final Decision task
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "HPE Final Decision" task page
    And I will provide following information before creating task
      | assignee                ||
      | taskInfo                ||
      | externalConsumerID      ||
      | actionTaken             ||
      | outcome                 |Manual|
      | contactRecordSingle     |Eligibility Status|
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status              | Cancelled|
      | reasonForCancel     | Created Incorrectly|
      | assignee            | Service TesterTwo|
      | priority            | 2|
      | taskInfo            | CP-19528|
      | externalConsumerID  | 1234567890|
      | actionTaken         |Corrected Exception  |
      | outcome             |Pending Research     |
      | contactRecordSingle |Other|
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId |
      | TASK_UPDATE_EVENT | Task_update   |

  @CP-19565 @CP-19565-03 @moldir @events-wf
  Scenario Outline: Validate TASK_UPDATE_EVENT for Translation Request task
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Translation Request" task page
    And I will provide following information before creating task
      | informationType          |Renewal Packet|
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                   | Cancelled|
      | reasonForCancel          | Created Incorrectly|
      | assignee                 | Service TesterTwo|
      | priority                 | 2|
      | taskInfo                 | CP-19565|
      | externalConsumerID       |1234567890                     |
      | externalCaseId           |123456789                      |
      | actionTaken              |Escalated to Translation Group |
      | informationType          |Renewal Packet                 |
      | language                 |Hindi                          |
      | dateTranslationEscalated |+1                             |
      | dateTranslationReceived  |today                          |
      | dateTranslationMailed    |-1                             |
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId |
      | TASK_UPDATE_EVENT | Task_update   |

  @CP-19365 @CP-19365-04 @elvin @moldir @events-wf
  Scenario Outline: Validate TASK_UPDATE_EVENT for DMAS to Appeals Escalation
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "My Task" page
    And I make sure assignee contains at least one "DMAS to Appeals Escalation" task if not I will create task
    And I click task id to get the results in descending order
    And I ensure My task page has at least one task with type "DMAS to Appeals Escalation" and I navigate to view task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | businessUnitAssigneeTo | EscalationBU      |
      | assignee               | Service TesterTwo |
      | dueDate                | 07/22/2021        |
      | cpCRID                 | 123456            |
      | cpSRID                 | 123456            |
      | cpTaskID               | 123456            |
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId |
      | TASK_UPDATE_EVENT | Task_update   |

  @CP-19533 @CP-19533-03 @events-wf @vidya
  Scenario Outline: Validate TASK_UPDATE_EVENT for Appeal Remand task
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Appeal Remand" task page
    And I will provide following information before creating task
      | externalApplicationId   |Test19365|
      | remandReason            |Other|
      | eligibilityDecision     |Approved |
      | remandDueDate           |today|
      | receivedDate            |-1|
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                   | Complete            |
      | assignee                 | Service TesterTwo   |
      | priority                 | 2                   |
      | taskInfo                 | CP-19565            |
      | reasonForEdit            |Corrected Data Entry |
      | externalApplicationId    |123456789            |
      | externalCaseId           |123456789            |
      | remandReason             |Timeliness           |
      | eligibilityDecision      |Approved             |
      | remandCompletionDate     |+1                   |
      | remandDueDate            |today                |
      | receivedDate             |-1                   |
      | disposition              | Denied              |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName         | correlationId |
      | TASK_UPDATE_EVENT | Task_update   |

  @CP-19552 @CP-19552-02 @CP-19343 @CP-19343-02 @events-wf @priyal
  Scenario Outline: Validate TASK_SAVE_EVENT and Task_Update_Event for Process App V1 Task
    When I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Process App V1" task page
    And I will provide following information before creating task
      | assignee                  | Service TesterTwo|
      | applicationType           | Expedited Application - CVIU |
      | externalApplicationId     | random|
      | channel                   | Paper|
      | myWorkSpaceDate           | today|
      | applicationReceivedDate   | today|
      | applicationSignatureDate  | today|
      | noOfApplicantsInHousehold | 1|
      | applicationStatus         | Outbound Call|
      | decisionSource            | VaCMS|
      | noOfApprovedApplicants    | 12|
      | missingInfoRequired       | No|
      | priority                  | 4|
      | hpe                       | false|
      | denialReason              | Over Income|
    And I click on save button in create task page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And In search result click on task id to navigate to view page
    And I initiate Event get api for trace id "Created"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "TASK_SAVE_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "TASK_SAVE_EVENT" publish to DPBI
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                    | Complete|
      | reasonForEdit             | Corrected Data Entry |
      | missingInfoRequired       | No|
      | applicationType           | MAGI - PW|
      | externalApplicationId     | Test1234|
      | myWorkSpaceDate           | today|
      | applicationReceivedDate   | today|
      | applicationSignatureDate  | today|
      | noOfApplicantsInHousehold | 10|
      | channel                   | Paper|
      | applicationStatus         | Data Collection|
      | denialReason              | Auto-Denied (Self-Direct)|
      | decisionSource            | Manual|
      | noOfApprovedApplicants    | 16|
      | hpe                       | true|
      | actionTakenSingle         | Transferred to LDSS|
      | disposition               | Referred|
    And I click on save button on task edit page
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for TASK has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |taskId |taskType      |srType|status  |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee         |createdBy   |createdOn| eventName         | correlationId |contactReason|
      |[blank]|Process App V1|[blank]|Created |[blank]|4       |[blank]|          |[blank]|              |[blank]| true        |[blank]|          |[blank]|Service TesterTwo|[blank]|today    | TASK_UPDATE_EVENT | Task_update   |[blank]|


