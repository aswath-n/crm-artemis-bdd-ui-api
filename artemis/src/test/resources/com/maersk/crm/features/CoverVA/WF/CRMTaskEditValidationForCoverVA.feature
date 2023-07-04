Feature: Task View/Edit Validation for CoverVA

  @CP-19573 @CP-19573-01 @CP-19572 @CP-19572-02 @CP-18993 @CP-18993-03 @CP-34498 @CP-34498-16 @CP-38195 @CP-38195-02-12 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @ruslan
  Scenario: Validate DMAS to Appeals Escalation task edit/view page
    When I will get the Authentication token for "CoverVA" in "Tenant Manager"
    And I initiated Business Unit By Project ID via API "8166"
    Then I can verify Business Unit get API response will be "success"
    Then I get the business unit data from TM
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "DMAS to Appeals Escalation" task page
    And I will provide following information before creating task
      | businessUnitAssigneeTo| EscalationBU      |
      | assignee              | Service TesterTwo |
      | taskInfo              ||
      | contactRecordSingle   | Late Summary      |
      | externalConsumerID    ||
      | externalCaseId        ||
      | externalApplicationId | T19573            |
      | actionTakenSingle     ||
      | documentDueDate       | today             |
      | cpCRID                ||
      | cpSRID                | 123456                      |
      | cpTaskID              ||
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | businessUnitAssigneeTo||
      | assignee            ||
      | status              | Complete |
      | documentDueDate     ||
      | contactRecordSingle ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "DUE DATE "
    And I verify "actionTakenSingle" single select drop down value
      | Escalated to CoverVA (DMAS)          |
      | Request Complete (maersk)           |
      | Request in Progress (maersk)        |
      | Returning - Sent in Error (maersk)  |
      | Unable to Complete Request (maersk) |
    And I verify "contactReason" single select drop down value
      | Late/Missed Hearing |
      | Late Summary        |
      | Other               |
      | Summary Not Sent    |
      | Untimely Remand     |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID        |
      | MMIS Member ID       |
      | Application Id       |
      | CP contact record Id |
      | CP SR Id             |
      | CP Task Id           |
      | Due Date             |
    And I will update the following information in edit task page
      | businessUnitAssigneeTo| EscalationBU      |
      | assignee              | Service TesterTwo           |
      | priority              | 2                           |
      | taskInfo              | CP-19573                    |
      | reasonForEdit         | Corrected Data Entry        |
      | contactRecordSingle   | Late Summary                |
      | externalConsumerID    | 1234567890                  |
      | externalCaseId        | 123456789                   |
      | externalApplicationId | Test19573                   |
      | actionTakenSingle     | Escalated to CoverVA (DMAS) |
      | documentDueDate       | 07/22/2021                  |
      | cpCRID                | 123456                      |
      | cpSRID                | 123456                      |
      | cpTaskID              | 123456                      |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And Close the soft assertions

  @CP-19575 @CP-19575-01 @CP-19574 @CP-19574-01 @CP-13177 @CP-13177-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @elvin
  Scenario: Validate Inbound Document task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Inbound Document" task page
    And I will provide following information before creating task
      | assignee                ||
      | taskInfo                ||
      | externalConsumerID      ||
      | externalCaseId          ||
      | externalApplicationId   | T19575    |
      | actionTaken             ||
      | InbDocType              | Complaint |
      | program                 | EAP       |
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | OnHold |
      | InbDocType              ||
      | program                 ||
      | externalApplicationId   ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE"
    And I verify task mandatory fields error message "REASON FOR HOLD"
    And I verify task mandatory fields error message "DOCUMENT TYPE "
    And I verify task mandatory fields error message "PROGRAM "
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify "actionTaken" multi select drop down value
      | Entered Application           |
      | Escalated to Supervisor       |
      | None                          |
      | Outbound Call - Did Not Reach |
      | Outbound Call - Reached       |
      | Reclassified Document Type    |
      | Sent to Appropriate Agency    |
      | Transferred to LDSS           |
      | Updated Case                  |
      | Uploaded Doc to Case          |
    And I verify "inboundCorrespondenceType" multi select drop down value
      | 1095-B Inquiry                           |
      | Call Center - Medicaid/FAMIS Application |
      | Communication Form - Other               |
      | Complaint                                |
      | CPU Verification Document                |
      | CVIU LDSS Communication Form             |
      | CVIU - Standard Application              |
      | CVIU Verification Document               |
      | DSS Application/Renewal                  |
      | DSS Document - Other                     |
      | DSS Verification Document                |
      | General Inquiry                          |
      | LDSS Communication Form                  |
      | Other                                    |
      | Paper Application                        |
      | Returned Mail                            |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID  |
      | MMIS Member ID |
      | Application Id |
    And I will update the following information in edit task page
      | status                  | Complete             |
      | assignee                | Service TesterTwo    |
      | priority                | 3                    |
      | taskInfo                | CP-19575             |
      | externalConsumerID      | 1234567890           |
      | externalCaseId          | 123456789            |
      | externalApplicationId   | Test19575            |
      | actionTaken             | Updated Case         |
      | InbDocType              | Returned Mail        |
      | program                 | FAMIS                |
      | reasonForEdit           | Corrected Case/Consumer Link,Corrected Data Entry|
      | disposition             | Unresolved           |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And Close the soft assertions

  @CP-19575 @CP-19575-04 @CP-19574 @CP-19574-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @elvin
  Scenario: Validate Verification Document task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Verification Document" task page
    And I will provide following information before creating task
      | assignee                ||
      | taskInfo                ||
      | externalConsumerID      ||
      | externalCaseId          ||
      | externalApplicationId   | T1957501               |
      | actionTaken             ||
      | InbDocType              | Self Employment Income |
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | OnHold |
      | InbDocType              ||
      | externalApplicationId   ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE"
    And I verify task mandatory fields error message "REASON FOR HOLD"
    And I verify task mandatory fields error message "DOCUMENT TYPE "
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify "actionTaken" multi select drop down value
      | Denied - Insufficient Verification Provided|
      | Escalated to Supervisor|
      | Obtained - Electronic Sources|
      | Obtained - Inbound Verif Docs|
      | Obtained - Outbound Call|
      | Outbound Call - Did Not Reach |
      | Pending Additiontal MI|
      | Updated SR Details|
    And I verify "inboundCorrespondenceType" multi select drop down value
      | Authorized Representative Verification |
      | Birth Verification                     |
      | Citizenship Documents                  |
      | Drivers License                        |
      | Earned Income                          |
      | Health Insurance Documents             |
      | Identity Verification                  |
      | Medical ID Card                        |
      | Non-Medical ID Card                    |
      | Other                                  |
      | Self Employment Income                 |
      | SSN Documents                          |
      | Unearned Income                        |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID  |
      | MMIS Member ID |
      | Application Id |
    And I will update the following information in edit task page
      | status                  | Complete             |
      | assignee                | Service TesterTwo    |
      | priority                | 3                    |
      | taskInfo                | CP-19575             |
      | externalConsumerID      | 1234567890           |
      | externalCaseId          | 123456789            |
      | externalApplicationId   | Test19575            |
      | actionTaken             | Updated SR Details   |
      | InbDocType              | SSN Documents        |
      | reasonForEdit           | Corrected Data Entry |
      | disposition             | Resolved             |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And Close the soft assertions

  @CP-19578 @CP-19578-01 @CP-19545 @CP-19545-04 @CP-18020 @CP-18020-03 @CP-19577 @CP-19577-02 @CP-19559 @CP-19559-02 @CP-19560 @CP-19560-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @elvin @vidya
  Scenario: Validate Process Returned Mail task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Process Returned Mail" task page
    And I will provide following information before creating task
      | assignee                ||
      | taskInfo                ||
      | externalConsumerID      ||
      | externalCaseId          ||
      | externalApplicationId   | T19578            |
      | actionTaken             ||
      | dateResent              ||
      | returnedMailReceivedDate||
      | InbDocType              | COC               |
      | returnedMailReason      | Consumer Deceased |
      | businessUnit            | CPU               |
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | OnHold |
      | InbDocType              ||
      | returnedMailReason      ||
      | businessUnit            ||
      | externalApplicationId   ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE"
    And I verify task mandatory fields error message "REASON FOR HOLD"
    And I verify task mandatory fields error message "DOCUMENT TYPE "
    Then I verify task mandatory fields error message "BUSINESS UNIT "
    Then I verify task mandatory fields error message "RETURNED MAIL REASON "
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify "actionTaken" multi select drop down value
      | Confirmed Existing Case Address |
      | Escalated to Supervisor         |
      | None                            |
      | Outbound Call - Did Not Reach   |
      | Outbound Call - Reached         |
      | Submitted Address Change        |
      | Transferred to LDSS             |
    And I verify "businessUnit" single select drop down value
      | CPU  |
      | CVIU |
    And I verify "returnedMailReason" single select drop down value
      | Address Data Entry Error |
      | Consumer Deceased        |
      | Consumer Moved           |
      | Expired Fwd Address      |
      | Invalid Address          |
      | Unknown                  |
    And I verify "inboundCorrespondenceType" multi select drop down value
      | CCC Plus                                      |
      | COC                                           |
      | CPU - 45 Day Pend                             |
      | CPU - 7 Day Pend                              |
      | CPU - AR                                      |
      | CPU/Other (Approvals and Denials)             |
      | CPU/VCL                                       |
      | CVIU - 45 Day Pend                            |
      | CVIU - 7 Day Pend                             |
      | CVIU - AR                                     |
      | CVIU/Other (Approvals, Denials, and Renewals) |
      | CVIU/VCL                                      |
      | E213 Denial                                   |
      | HIPAA                                         |
      | Newborn NOA                                   |
      | Other                                         |
      | PE Approvals                                  |
      | PMOC                                          |
      | Voter Registration Card                       |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID  |
      | MMIS Member ID |
      | Application Id |
      | Received Date  |
      | Date Resent    |
    And I will update the following information in edit task page
      | status                  | Complete             |
      | assignee                | Service TesterTwo    |
      | priority                | 3                    |
      | taskInfo                | CP-19578             |
      | externalConsumerID      | 1234567890           |
      | externalCaseId          | 123456789            |
      | externalApplicationId   | Test19578            |
      | actionTaken             | None                 |
      | reasonForEdit           | Corrected Data Entry |
      | dateResent              | today                |
      | returnedMailReceivedDate| today                |
      | InbDocType              | HIPAA                |
      | returnedMailReason      | Invalid Address      |
      | businessUnit            | CVIU                 |
      | disposition             | Resolved             |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And Close the soft assertions
