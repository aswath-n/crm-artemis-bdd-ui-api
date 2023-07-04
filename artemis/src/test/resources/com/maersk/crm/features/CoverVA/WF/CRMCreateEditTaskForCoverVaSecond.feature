Feature: Create/Edit task from UI and check the update in DB Second

  @CP-18988 @CP-43438 @CP-18988-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Validate CPU Eligibility to DMAS Escalation task created/Edited/viewable and data is saved in DB
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated Business Unit By Project ID via API "8166"
    Then I can verify Business Unit get API response will be "success"
    Then I get the business unit data from TM
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "CPU Eligibility to DMAS Escalation" task page
    And I will provide following information before creating task
      | taskInfo               | Test CP-18988                     |
      | businessUnitAssigneeTo | CPU New Application               |
      | assignee               | Service TesterTwo                 |
      | externalCaseId         | 564763567                         |
      | externalConsumerID     | 1234567890                        |
      | externalApplicationId  | random                            |
      | actionTakenSingle      | Unable to Complete Request (DMAS) |
      | contactRecordSingle    | Medicare Review                   |
      | documentDueDate        | today                             |
      | cpCRID                 | 18988                             |
      | cpSRID                 | 18988                             |
      | cpTaskID               | 18988                             |
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
      | status                 | Cancelled           |
      | reasonForCancel        | Created Incorrectly |
      | taskInfo               |                     |
      | contactRecordSingle    | ECPR Review         |
      | externalCaseId         |                     |
      | externalConsumerID     |                     |
      | documentDueDate        | 05/19/2025          |
      | cpCRID                 |                     |
      | cpSRID                 |                     |
      | cpTaskID               |                     |
      | businessUnitAssigneeTo | CPU New Application |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-19203 @CP-19203-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Validate CVCC to DMAS Escalation task created/Edited/viewable and data is saved in DB
    When I will get the Authentication token for "CoverVA" in "Tenant Manager"
    And I initiated Business Unit By Project ID via API "8166"
    Then I can verify Business Unit get API response will be "success"
    Then I get the business unit data from TM
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "CVCC to DMAS Escalation" task page
    And I will provide following information before creating task
      | taskInfo               | Test CP-19203       |
      | externalConsumerID     | 1234567890          |
      | externalCaseId         | 123456789           |
      | externalApplicationId  | random              |
      | actionTakenSingle      | Transferred to DMAS |
      | contactRecordSingle    | Other               |
      | cpCRID                 | 12345               |
      | cpSRID                 | 12345               |
      | cpTaskID               | 12345               |
      | documentDueDate        | today               |
      | businessUnitAssigneeTo | DMAS                |
      | assignee               | Service TesterTwo   |
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
      | status                 | Cancelled           |
      | reasonForCancel        | Created Incorrectly |
      | taskInfo               |                     |
      | contactRecordSingle    | Media Inquiries     |
      | externalConsumerID     |                     |
      | externalApplicationId  | Tf456T546           |
      | externalCaseId         |                     |
      | cpCRID                 |                     |
      | cpSRID                 |                     |
      | cpTaskID               |                     |
      | documentDueDate        | 05/19/2022          |
      | businessUnitAssigneeTo | DMAS                |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
 #   Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-19194 @CP-19194-02 @CP-39600 @CP-39600-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Validate Appeal Withdrawal task created/Edited/viewable and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Appeal Withdrawal" task page
    And I will provide following information before creating task
      | taskInfo | maxlength         |
      | assignee | Service TesterTwo |
      | cpSRID   | 34567             |
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
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | assignee      |                      |
      | taskInfo      |                      |
      | cpSRID        |                      |
      | disposition   | Returned to DMAS     |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-21003 @CP-21003-02 @CP-19834 @CP-19834-01 @CP-30299 @CP-30299-02 @CP-38195 @CP-38195-04 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal @ruslan
  Scenario Outline: Validate General Escalation task created/Edited/viewable and data is saved in DB
    When I will get the Authentication token for "CoverVA" in "Tenant Manager"
    And I initiated Business Unit By Project ID via API "8166"
    Then I can verify Business Unit get API response will be "success"
    Then I get the business unit data from TM
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "General Escalation" task page
    And I will provide following information before creating task
      | taskInfo               | maxlength         |
      | externalCaseId         | 123654789         |
      | externalApplicationId  | random            |
      | actionTakenSingle      | Escalated to DMAS |
      | contactRecordSingle    | Emergency         |
      | documentDueDate        | today             |
      | cpCRID                 | 34567             |
      | cpSRID                 | 34567             |
      | cpTaskID               | 34567             |
      | businessUnitAssigneeTo | EscalationBU      |
      | assignee               | Service TesterTwo |
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
      | status                 | Cancelled           |
      | reasonForCancel        | Created Incorrectly |
      | taskInfo               |                     |
      | contactRecordSingle    | PW Emergency        |
      | externalCaseId         |                     |
      | documentDueDate        | 05/19/2021          |
      | cpCRID                 |                     |
      | cpSRID                 |                     |
      | cpTaskID               |                     |
      | businessUnitAssigneeTo | EscalationBU        |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions
    Examples:
      | taskId | taskType           | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn | contactReason |
      |        | General Escalation |        | Created |            |          |         |            |        |                |            | true          |            |            |        |          | Service TesterTwo | today     |               |


    # refactoring on 30-08-2021 by priyal
  @CP-19311 @CP-19311-06 @CP-18031 @CP-18031-02 @CP-19542 @CP-19542-03 @CP-19541 @CP-19541-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate CVIU Communication Form task created and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "CVIU LDSS Communication Form" task page
    And I will provide following information before creating task
      | taskInfo              | Test CP-19311               |
      | assignee              | Service TesterTwo           |
      | externalConsumerID    | 1234567890                  |
      | externalCaseId        | 123456789                   |
      | externalApplicationId | Test19365                   |
      | actionTaken           | Updated Application         |
      | contactRecordSingle   | Reporting Newborn           |
      | facilityType          | Department of Corrections   |
      | facilityName          | Augusta Correctional Center |
      | caseWorkerFirstName   | Eldora Dali                 |
      | caseWorkerLastName    | Mikelangello Do             |
      | locality              | Bristol                     |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    When I expend first Task from the list by clicking in Task ID
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
      | taskInfo              |                     |
      | assignee              |                     |
      | externalConsumerID    |                     |
      | externalCaseId        |                     |
      | externalApplicationId | 1234Test            |
      | contactRecordSingle   | Facility Change     |
      | facilityName          |                     |
      | facilityType          |                     |
      | caseWorkerFirstName   | Eldora Dali         |
      | caseWorkerLastName    | Mikelangello Do     |
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-23725 @CP-23725-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Validate Facility Type is equal to "Local Jail"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "CVIU LDSS Communication Form" task page
    And I will provide following information before creating task
      | facilityType | Regional Jail |
    And I will verify the following Facility Name and Facility Type set to "Local Jail"
      | Accomack County Jail                        |
      | Albemarle/Charlottesville Regional Jail     |
      | Alexandria City Jail                        |
      | Arlington County Jail                       |
      | Blue Ridge Regional Jail (BRRJ) - Amherst   |
      | Blue Ridge Regional Jail (BRRJ) - Bedford   |
      | Blue Ridge Regional Jail (BRRJ) - Campbell  |
      | Blue Ridge Regional Jail (BRRJ) - Halifax   |
      | Blue Ridge Regional Jail (BRRJ) - Lynchburg |
      | Botetourt County Jail                       |
      | Bristol City Jail                           |
      #| Central Virginia Regional Jail|
      | Charlotte County Jail                       |
      | Chesapeake City Jail                        |
      | Chesterfield County Jail                    |
      | Culpeper County Jail                        |
      | Danville City Jail                          |
      | Fairfax County Jail                         |
      | Franklin County Jail                        |
      | Gloucester County Jail                      |
      | Hampton City Jail                           |
      | Rockingham/Harrisonburg Regional Jail       |
      | Southampton County Jail                     |
      | Southwest Virginia Regional Jail - Abington |
      | Southwest Virginia Regional Jail - Duffield |
      | Southwest Virginia Regional Jail - Haysi    |
      | Southwest Virginia Regional Jail - Tazewell |
      | Sussex County Jail                          |
      #| Virginia Beach City Jail|
      | Virginia Peninsula Regional Jail            |
      | Western Tidewater Regional Jail             |
    And Close the soft assertions
