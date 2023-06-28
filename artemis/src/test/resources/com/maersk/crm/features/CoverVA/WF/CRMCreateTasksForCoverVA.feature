Feature: Create/Edit task from UI and check the update in DB by using Get task api

  @CP-19349 @CP-19349-03 @CP-18046 @CP-18046-02 @CP-19563 @CP-19563-01 @CP-19565 @CP-19565-01 @CP-28816 @CP-28816-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate Translation Request task created and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I navigate to "Translation Request" task page
    And I will provide following information before creating task
      | taskInfo                 | Test CP 16149                 |
      | assignee                 | Service TesterTwo             |
      | externalConsumerID       |1234567890                     |
      | externalCaseId           |123456789                      |
      | informationType          |Renewal Packet                 |
      | actionTaken              |Escalated to Translation Group |
      | language                 |Hindi                          |
      | dateTranslationEscalated |+1                             |
      | dateTranslationMailed    |-1                             |
      | dateTranslationReceived  |today                          |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                   | Cancelled                      |
      | reasonForCancel          | Created Incorrectly            |
      | assignee                 ||
      | taskInfo                 ||
      | externalConsumerID       ||
      | externalCaseId           ||
      | informationType          | Renewal Packet                 |
      | dateTranslationEscalated ||
      | dateTranslationReceived  ||
      | dateTranslationMailed    ||
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete               |
      | reasonForEdit | Corrected Data Entry   |
      | disposition   | Unresolved             |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-19365 @CP-19365-02 @CP-18993 @CP-18993-02 @CP-19573 @CP-19573-02 @CP-19572 @CP-19572-01 @CP-38195 @CP-38195-01-12 @@crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate DMAS to Appeals Escalation task created/Edited/viewable and data is saved in DB
    When I will get the Authentication token for "CoverVA" in "Tenant Manager"
    And I initiated Business Unit By Project ID via API "8166"
    Then I can verify Business Unit get API response will be "success"
    Then I get the business unit data from TM
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "DMAS to Appeals Escalation" task page
    And I will provide following information before creating task
      | taskInfo               | Test CP-19365       |
      | businessUnitAssigneeTo | CPU New Application |
      | assignee               | Service TesterTwo   |
      | externalConsumerID     | 1234567890          |
      | externalCaseId         | 123456789           |
      | externalApplicationId  | Test19365           |
      | contactRecordSingle    | Summary Not Sent    |
      | documentDueDate        | today               |
      | cpCRID                 | 12345               |
      | cpSRID                 | 12345               |
      | cpTaskID               | 12345               |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Cancelled           |
      | reasonForCancel       | Created Incorrectly |
      | taskInfo              ||
      | contactRecordSingle   | Late Summary        |
      | externalConsumerID    ||
      | externalCaseId        ||
      | externalApplicationId | T56778              |
      | actionTknSingle       ||
      | documentDueDate       | 07/22/2021          |
      | cpCRID                ||
      | cpSRID                ||
      | cpTaskID              ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-19433 @CP-19433-02 @CP-18025 @CP-18025-02 @CP-19526 @CP-19526-01 @CP-19528 @CP-19528-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate HPE Final Decision task created and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "HPE Final Decision" task page
    And I will provide following information before creating task
      | taskInfo            | Test CP-19365       |
      | assignee            | Service TesterTwo   |
      | externalConsumerID  | 1234567890          |
      | externalCaseId      | 324543567           |
      | actionTaken         | Corrected Exception |
      | outcome             | Final Approval      |
      | contactRecordSingle | Eligibility Status  |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status              | Cancelled           |
      | reasonForCancel     | Created Incorrectly |
      | assignee            ||
      | taskInfo            ||
      | externalConsumerID  ||
      | deselectActionTaken | Corrected Exception |
      | outcome             | Final Denial        |
      | contactRecordSingle |Eligibility Status|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-19367 @CP-19367-02 @CP-18028 @CP-18028-02 @CP-19575 @CP-19575-02 @CP-19574 @CP-19574-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate Inbound Document task created and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Inbound Document" task page
    And I will provide following information before creating task
      | taskInfo                | Test CP-19367|
      | assignee                | Service TesterTwo|
      | externalConsumerID      |1234567890|
      | externalCaseId          |123456789|
      | externalApplicationId   |Test19365|
      | actionTaken             |Uploaded Doc to Case|
      | program                 |EAP|
      | InbDocType              |Paper Application |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Cancelled            |
      | reasonForCancel       | Created Incorrectly  |
      | assignee              ||
      | taskInfo              ||
      | externalConsumerID    ||
      | externalCaseId        ||
      | externalApplicationId | T19367               |
      | deselectActionTaken   | Uploaded Doc to Case |
      | InbDocType            | General Inquiry      |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-19367 @CP-19367-03 @CP-18029 @CP-18029-02 @CP-19575 @CP-19575-02 @CP-19574 @CP-19574-04 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate Verification Document task created and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I navigate to "Verification Document" task page
    And I will provide following information before creating task
      | taskInfo              | Test CP-19367         |
      | assignee              | Service TesterTwo     |
      | externalConsumerID    | 1234567890            |
      | externalCaseId        | 123456789             |
      | externalApplicationId | Test19365             |
      | InbDocType            | Citizenship Documents |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    Then I verify task table record for the task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Cancelled          |
      | reasonForCancel       | Duplicate Task     |
      | assignee              ||
      | taskInfo              ||
      | externalConsumerID    ||
      | externalCaseId        ||
      | externalApplicationId | T19367             |
      | deselectActionTaken   | Updated SR Details |
#      | InbDocType            | Earned Income      |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-19358 @CP-19358-02 @CP-18030 @CP-18030-02 @CP-19566 @CP-19566-02 @CP-19567 @CP-19567-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @elvin @priyal
  Scenario: Validate LDSS Communication Form task created and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "LDSS Communication Form" task page
    And I will provide following information before creating task
      | taskInfo                | Test CP-19358|
      | assignee                | Service TesterTwo|
      | externalConsumerID      | 1234567890|
      | externalCaseId          | 123456789|
      | externalApplicationId   | Test19358|
      | actionTaken             | Mailed Requested Docs|
      | locality                | Bath|
      | requestType             | Update Information on Case or Other info |
      | issueType               | Determination Incorrect (CPU Error)|
      | caseWorkerFirstName     | Eldora Dali|
      | caseWorkerLastName      | Mikelangello Do|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Cancelled                                     |
      | reasonForCancel       | Created Incorrectly                           |
      | assignee              ||
      | taskInfo              ||
      | externalConsumerID    ||
      | externalCaseId        ||
      | externalApplicationId | RFTR456y8                                     |
      | deselectActionTaken   | Mailed Requested Docs                         |
      | locality              | Accomack                                      |
      | requestType           | Request to Assign Pending App or Case to LDSS |
      | issueType             ||
      | caseWorkerFirstName   | Test Dali                                     |
      | caseWorkerLastName    | Demo                                          |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

    #deleted ui-wf-coverva tag since we dont manually create Review Complaint task
  #@CP-19368 @CP-19368-02 @crm-regression @CoverVA-UI-Regression @elvin @ui-wf-coverva
  Scenario: Validate Review Complaint task created and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Review Complaint" task page
    And I will provide following information before creating task
      | taskInfo             | Test CP-19368     |
      | assignee             | Service TesterTwo |
      | externalConsumerID   | 1234567890        |
      | externalCaseId       | 123456789         |
      | complaintType        | LDSS              |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    When I expend first Task from the list by clicking in Task ID
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    Then I verify task table record for the task
    And I verify task details information for task fields for coverVA

  @CP-19368 @CP-19368-02 @CP-19578 @CP-19578-02 @CP-19345 @CP-19345-02 @CP-19545 @CP-19545-01 @CP-18020 @CP-18020-02 @CP-19577 @CP-19577-01 @CP-19559 @CP-19559-01 @CP-19560 @CP-19560-01 @CP-19544 @CP-19544-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @elvin @vidya
  Scenario: Validate Process Returned Mail task created and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Process Returned Mail" task page
    And I will provide following information before creating task
      | taskInfo                 | Test CP-19368           |
      | assignee                 | Service TesterTwo       |
      | externalConsumerID       | 1234567890              |
      | externalCaseId           | 123456789               |
      | externalApplicationId    | Test19368               |
      | InbDocType               | COC                     |
      | returnedMailReason       | Unknown                 |
      | businessUnit             | CPU                     |
      | returnedMailReceivedDate | -1                      |
      | dateResent               | today                   |
      | actionTaken              | Escalated to Supervisor |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                   | Cancelled               |
      | reasonForCancel          | Duplicate Task          |
      | assignee                 ||
      | taskInfo                 ||
      | externalConsumerID       ||
      | externalCaseId           ||
      | externalApplicationId    | Test19578               |
      | deselectActionTaken      | Escalated to Supervisor |
      | InbDocType               | HIPAA                   |
      | returnedMailReason       | Invalid Address         |
      | businessUnit             | CVIU                    |
      | returnedMailReceivedDate | today                   |
      | dateResent               ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-19276 @CP-19276-02 @CP-19195 @CP-19195-02 @CP-19531 @CP-19531-01 @CP-19533 @CP-19533-01 @CP-38195 @CP-38195-03 @priyal @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Validate Appeal Remand task created and data is saved in DB
    When I will get the Authentication token for "CoverVA" in "Tenant Manager"
    And I initiated Business Unit By Project ID via API "8166"
    Then I can verify Business Unit get API response will be "success"
    Then I get the business unit data from TM
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Appeal Remand" task page
    And I will provide following information before creating task
      | taskInfo               | Test CP-19367                            |
      | businessUnitAssigneeTo | CPU New Application                      |
      | assignee               | Service TesterTwo                        |
      | externalCaseId         | 123456789                                |
      | externalApplicationId  | Test19365                                |
      | remandReason           | Case Correction - Incorrect Alien Status |
      | eligibilityDecision    | Approved                                 |
      | remandCompletionDate   | +1                                       |
      | remandDueDate          | today                                    |
      | receivedDate           | -1                                       |
    And I click on save button in create task page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                   |Cancelled        |
      | reasonForCancel          |Duplicate Task   |
      | assignee                 ||
      | taskInfo                 ||
      | externalCaseId           ||
      | externalApplicationId    |Test12345        |
      | remandReason             |Other            |
      | eligibilityDecision      |Denied           |
      | remandCompletionDate     ||
      | remandDueDate            |+1               |
      | receivedDate             |today            |
      | businessUnitAssigneeTo   ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions
    Examples:
      |taskId|taskType       |srType|status   |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy        |createdOn|contactReason|
      ||Appeal Remand  ||Created  ||        ||          ||              ||true         ||          ||        |Service TesterTwo | today  ||


    # comment this because of @CP-22201 
  #@CP-19311 @CP-19311-04 @CP-19198 @CP-19198-02 @crm-regression @CoverVA-UI-Regression  @vidya #deleted @ui-wf-coverVA tag since we dont create it manually
  Scenario: Validate Fair Hearing task created and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Fair Hearing " task page
    And I will provide following information before creating task
      | taskInfo                | Test CP-19311    |
      | assignee                | Service TesterTwo|
      | accessibilityNeeded     | Hearing Impaired |
      | appointmentDate         | today            |
      | appointmentTime         | 03:00 PM         |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    When I expend first Task from the list by clicking in Task ID
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    Then I verify task table record for the task
    And I verify task details information for task fields for coverVA

    # refactoring on 30-08-2021 by priyal
  @CP-19311 @CP-19311-05 @CP-18044 @CP-18044-02 @CP-19542 @CP-19542-01 @CP-19541 @CP-19541-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate CVIU Provider Request task created and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "CVIU Provider Request" task page
    And I will provide following information before creating task
      | taskInfo                | Test CP-19311    |
      | assignee                | Service TesterTwo|
      | externalConsumerID      | 1234567890       |
      | externalCaseId          | 123456789        |
      | actionTaken             | Confirm Coverage |
      | contactRecordSingle     | Provide Medical Records|
      | facilityType            | Department of Corrections|
      | facilityName            | Augusta Correctional Center|
      | providerFirstName       | Vidya            |
      | providerLastName        | Mithun           |
      | providerPhone           | 9876543210       |
      | providerEmail           | test@gmail.com   |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status              | Cancelled               |
      | reasonForCancel     | Duplicate Task          |
      | taskInfo            ||
      | assignee            ||
      | externalConsumerID  ||
      | externalCaseId      ||
      | deselectActionTaken | Confirm Coverage        |
      | contactRecordSingle | Provide Medical Records |
      | providerFirstName   ||
      | providerLastName    ||
      | providerPhone       ||
      | providerEmail       ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

    # refactoring on 19-08-2021 by priyal
  @CP-23725 @CP-23725-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Verify Facility Type is equal to "Department of Corrections"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "CVIU Provider Request" task page
    And I will provide following information before creating task
      | facilityType  | Department of Corrections|
    And I will verify the following Facility Name and Facility Type set to "Department of Corrections"
      | Augusta Correctional Center|
      | Baskerville Correctional Center|
      | Beaumont Correctional Center|
      | Bland Correctional Center|
      | Buckingham Correctional Center|
      | Caroline Correctional Unit|
      | Coffeewood Correctional Center|
      | Deerfield Correctional Center|
      | Dillwyn Correctional Center|
      #| Fluvanna Correctional Center for Women|
      | Greensville Correctional Center|
      | Green Rock Correctional Center|
      | Halifax Correctional Unit|
      | Haynesville Correctional Center|
      | Indian Creek Correctional Center|
      | Keen Mountain Correctional Center|
      | Lawrenceville Correctional Center|
      | Lunenburg Correctional Center|
      | Nottoway Correctional Center|
      | Patrick Henry Correctional Unit|
      | Pocahontas State Correctional Center|
      | Red Onion State Prison|
      | River North Correctional Center|
      | Rustburg Correctional Unit|
      | St. Brides Correctional Center|
      | State Farm Correctional Center|
      | Sussex I State Prison|
      #| Virginia Correctional Center for Women|
      | Wallens Ridge State Prison|
      | Wise Correctional Unit|
    And Close the soft assertions

  @CP-19291 @CP-19291-02 @CP-34498 @CP-34498-22 @CP-38195 @CP-38195-02-10 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @ruslan
  Scenario Outline: Validate DMAS to CVIU CC Escalation task in edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    Then I verify task following fields are present on view page
      | VACMS CASE ID                     |
      | MMIS MEMBER ID                    |
      | APPLICATION ID                    |
      | CONNECTIONPOINT CONTACT RECORD ID |
      | CONNECTIONPOINT SR ID             |
      | CONNECTIONPOINT TASK ID           |
      | CONTACT REASON                    |
      | DUE DATE                          |
      | ACTION TAKEN                      |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority              |          |
      | assignee              |          |
      | status                | Complete |
      | taskInfo              | ABC      |
      | documentDueDate       |          |
      | contactRecordSingle   |          |
      | externalApplicationId |          |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    And I verify task mandatory fields error message "PRIORITY"
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "DUE DATE "
    And I verify minimum lenght error message "TASK INFORMATION"
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify "actionTakenSingle" single select drop down value
      | Disputed RICKI (maersk)             |
      | Escalated to CoverVA (DMAS)          |
      | Request Complete (maersk)           |
      | Request in Progress (maersk)        |
      | Returning - Sent in Error (maersk)  |
      | Unable to Complete Request (maersk) |
    Then I verify "contactReason" single select drop down value
      | Consumer Complaint     |
      | Customer Service Issue |
      | HIPPA                  |
      | Other                  |
      | PINK                   |
      | SLA Not Met            |
      | Worker Error           |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID        |
      | MMIS Member ID       |
      | Application Id       |
      | CP contact record Id |
      | CP SR Id             |
      | CP Task Id           |
      | Due Date             |
    And Close the soft assertions
    Examples:
      | taskId | taskType                   | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      |        | DMAS to CVIU CC Escalation |        |        |            |          |         |            |        |                |            | false         |            |            |        |          |           |           |             |