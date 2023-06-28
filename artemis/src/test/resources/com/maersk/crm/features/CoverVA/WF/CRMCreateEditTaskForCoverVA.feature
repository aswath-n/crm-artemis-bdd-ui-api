Feature: Create/Edit task from UI and check the update in DB

  @CP-19309 @CP-19309-04 @CP-19535 @CP-19535-01 @CP-18045 @CP-18045-02 @CP-25642 @CP-25642-04 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @elvin @vidya
  Scenario: Validate Authorized Representative Designation task created and saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Authorized Representative Designation" task page
    And I will provide following information before creating task
      | assignee             | Service TesterTwo |
      | taskInfo             | test              |
      | externalCaseId       | 324543567         |
      | externalConsumerID   | 1234467890        |
      | actionTaken          | Submitted Change  |
      | channel              | Phone             |
      | ARFirstName          | Alan              |
      | ARLastName           | Smith             |
      | ARPhone              | 7202022002        |
      | AREmail              | test@mail.com     |
      | ARAddressLine1       | 11 Main St        |
      | ARAddressLine2       | Apt 1             |
      | ARCity               | Denver            |
      | ARState              | Colorado          |
      | ARZipCode            | 80001             |
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
      | status               |Cancelled          |
      | reasonForCancel      |Created Incorrectly|
      | assignee             ||
      | taskInfo             ||
      | externalCaseId       ||
      | externalConsumerID   ||
      | channel              | VaCMS MWS         |
      | ARFirstName          | Punith            |
      | ARLastName           | Rajkumar          |
      | ARPhone              | 1234567890        |
      | AREmail              ||
      | ARAddressLine1       | 14 Main Road      |
      | ARAddressLine2       ||
      | ARCity               | Bangalore         |
      | ARState              | Karnataka         |
      | ARZipCode            | 56002             |
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-19148 @CP-19148-02 @CP-20719 @CP-20719-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @priyal
  Scenario: Validate DMAS to CVCC Escalation task created/Edited/viewable and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "DMAS to CVCC Escalation" task page
    And I will provide following information before creating task
      | taskInfo              | Test CP-19148              |
      | externalCaseId        | 324543567                  |
      | externalConsumerID    | 1234467890                 |
      | externalApplicationId | random                     |
      | actionTakenSingle     | Request Complete (maersk) |
      | contactRecordSingle   | Worker Error               |
      | documentDueDate       | today                      |
      | cpCRID                | 34567                      |
      | cpSRID                | 34567                      |
      | cpTaskID              | 34567                      |
      | businessUnitAssigneeTo| EscalationBU               |
      | assignee              | Service TesterTwo          |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Cancelled           |
      | reasonForCancel       | Created Incorrectly |
      | taskInfo              ||
      | contactRecordSingle   | HIPPA               |
      | externalCaseId        ||
      | externalConsumerID    ||
      | externalApplicationId | Sorg564rf           |
      | documentDueDate       | 05/19/2023          |
      | cpCRID                ||
      | cpSRID                ||
      | cpTaskID              ||
      | businessUnitAssigneeTo| EscalationBU        |
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

#   refactoring by priyal
  @CP-19122 @CP-43438 @CP-19122-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Validate DMAS to CPU Escalation task created/Edited/viewable and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "DMAS to CPU Escalation" task page
    And I will provide following information before creating task
      | taskInfo              | Test CP-19122            |
      | externalCaseId        | 324543567                |
      | externalConsumerID    | 1234467890               |
      | externalApplicationId | random                   |
      | actionTakenSingle     | Disputed RICKI (maersk) |
      | contactRecordSingle   | PINK                     |
      | documentDueDate       | today                    |
      | cpCRID                | 34567                    |
      | cpSRID                | 34567                    |
      | cpTaskID              | 34567                    |
      | businessUnitAssigneeTo| CPU New Application      |
      | assignee              | Service TesterTwo        |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    When I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Cancelled           |
      | reasonForCancel       | Created Incorrectly |
      | taskInfo              ||
      | contactRecordSingle   | HIPPA               |
      | externalCaseId        ||
      | externalConsumerID    ||
      | externalApplicationId | SErg564rf           |
      | documentDueDate       | 05/19/2023          |
      | cpCRID                ||
      | cpSRID                ||
      | cpTaskID              ||
      | businessUnitAssigneeTo| CPU New Application |
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-18032 @CP-18032-02 @CP-19568 @CP-19568-02 @CP-19361 @CP-19361-03 @CP-19569 @CP-19569-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Validate Hospital Authorized Representative Request task created/Edited/viewable and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Hospital Authorized Representative Request" task page
    And I will provide following information before creating task
      | taskInfo                | Test CP-18032|
      | assignee                | Service TesterTwo|
      | externalCaseId          | 564563567|
      | externalConsumerID      | 1234467890|
      | actionTaken             | Provided Case Status|
      | contactRecordSingle     | Translation Services|
      | ARFirstName             | ARft|
      | ARLastName              | Bhgt|
      | ARPhone                 | 1345768781|
      | AREmail                 | Tag@gmail.com|
      |Organization             | ABCetryew|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Cancelled          |
      | reasonForCancel         | Created Incorrectly|
      | assignee                ||
      | taskInfo                ||
      | contactRecordSingle     | Unknown|
      | externalCaseId          ||
      | externalConsumerID      ||
      | ARFirstName             | FGTYr|
      | ARLastName              | KJTGJ|
      | ARPhone                 | 2345678954|
      | AREmail                 ||
      | Organization            ||
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-18781 @CP-18781-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Validate CVIU CC to DMAS Escalation task created/Edited/viewable and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "CVIU CC to DMAS Escalation" task page
    And I will provide following information before creating task
      | taskInfo              | Test CP-18781       |
      | externalCaseId        | 564563567           |
      | externalConsumerID    | 1234467890          |
      | externalApplicationId | random              |
      | actionTakenSingle     | Transferred to DMAS |
      | contactRecordSingle   | Facility Complaint  |
      | documentDueDate       | today               |
      | cpCRID                | 18781               |
      | cpSRID                | 18781               |
      | cpTaskID              | 18781               |
      | businessUnitAssigneeTo| CVIU                |
      | assignee              | Service TesterTwo   |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Cancelled           |
      | reasonForCancel       | Created Incorrectly |
      | taskInfo              ||
      | contactRecordSingle   | Other               |
      | externalCaseId        ||
      | externalConsumerID    ||
      | documentDueDate       | 05/19/2025          |
      | cpCRID                ||
      | cpSRID                ||
      | cpTaskID              ||
      | businessUnitAssigneeTo| CVIU                |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

