Feature: Conditionally Fields are displayed for App SR

  @CP-22803 @CP-22803-01 @CP-22801 @CP-22801-01 @CP-22802 @CP-22802-01 @CP-22800 @CP-22800-01 @CP-22799 @CP-22799-01 @CP-22784 @CP-22784-01 @CP-15323 @CP-15323-10 @CP-20049 @CP-20049-02 @CP-40291 @CP-40291-01 @CP-47703 @CP-47703-03 @vidya @ruslan @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate fields enable and disable functionality present in Application SR and sr info allow special character
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      | taskInfo              | Test-CP-22803 $ allow Special Character &% |
      | applicationType       | Expedited Application - CVIU               |
      | externalApplicationId | random                                     |
      | myWorkSpaceDate       | today                                      |
      | channel               | CommonHelp                                 |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | dateOfContact | today             |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will update the following information in edit task page
      | status              | Closed   |
      | missingInfoRequired | Yes      |
      | disposition         | Referred |
    And I click on save button on task edit page
    And I verify "MI RECEIVED DATE" field is optional
    Then I verify "VCL Due Date" field is enable and required
    Then I verify "VCL Sent Date" field is enable and required
    Then I verify "Document Type" field is enable and required
    Then I verify "Locality" field is enable and required
    Then I verify "Case Worker Name" field is enable and required
    Then I verify "Transfer Reason" field is enable and required
    And I click on cancel button on create task type screen
    Then I verify warning popup is displayed with message
    And I click on continue button on warning message
    And I click on edit service request button
    Then I verify "VCL Due Date" field is enabled
    Then I verify "VCL Sent Date" field is disable and cleared out
    Then I verify "Document Type" field is disable and cleared out
    Then I verify "Locality" field is disable and cleared out
    Then I verify "Case Worker Name" field is disable and cleared out
    Then I verify "Transfer Reason" field is disable and cleared out
    And I will update the following information in edit task page
      | status              | Closed        |
      | missingInfoRequired | Yes           |
      | disposition         | Referred      |
      | vclDueDate          | today         |
      | InbDocType          | Earned Income |
      | vclSentDate         | +1            |
      | caseWorkerFirstName | Vidya         |
      | caseWorkerLastName  | Mithun        |
      | transferReason      | Case Change   |
      | locality            | Albemarle     |
    And I click on cancel button on create task type screen
    Then I verify warning popup is displayed with message
    And I click on continue button on warning message
    And I click on edit service request button
    Then I verify "VCL Due Date" field is enabled
    Then I verify "VCL Sent Date" field is disable and cleared out
    Then I verify "Document Type" field is disable and cleared out
    Then I verify "Locality" field is disable and cleared out
    Then I verify "Case Worker Name" field is disable and cleared out
    Then I verify "Transfer Reason" field is disable and cleared out
    And I will update the following information in edit task page
      | status                    | Closed                                                       |
      | taskInfo                  | Test-CP-22802 $ allow Special Character &% in edit page %^$# |
      | reasonForEdit             | Corrected Data Entry                                         |
      | applicationReceivedDate   | today                                                        |
      | channel                   | RDE                                                          |
      | noOfApplicantsInHousehold | 1                                                            |
      | missingInfoRequired       | Yes                                                          |
      | InbDocType                | Earned Income                                                |
      | applicationStatus         | Data Collection                                              |
      | vclSentDate               | +1                                                           |
      | vclDueDate                | -1                                                           |
      | disposition               | Referred                                                     |
      | caseWorkerFirstName       | Vidya                                                        |
      | caseWorkerLastName        | Mithun                                                       |
      | transferReason            | Case Change                                                  |
      | locality                  | Albemarle                                                    |
    And I click on save button on task edit page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-22087 @CP-22087-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Validate Additional template fields for Missing info task in view and edit task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status       | Complete |
      | disposition  | Denied   |
      | denialReason ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DENIAL REASON "
    And I will update the following information in edit task page
      | disposition | Approved |
    And I click on save button on task edit page
    Then I verify "DENIAL REASON " filed is not mandatory for the task
    And I will update the following information in edit task page
      | disposition | Denied |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DENIAL REASON "
    And I will update the following information in edit task page
      | disposition | Referred |
    And I click on save button on task edit page
    Then I verify "DENIAL REASON " filed is not mandatory for the task
    And I will update the following information in edit task page
      | reasonForEdit   | Corrected Data Entry                                |
      | documentDueDate | today                                               |
      | receivedDate    | today                                               |
      | InbDocType      | Earned Income                                       |
      | actionTaken     | Escalated to Supervisor                             |
      | taskInfo        | Testing Complete functionality of Missinf info task |
      | disposition     | Denied                                              |
      | denialReason    | Does not meet a full benefit alien status           |
    And I click on save button on task edit page
    And I verify the updated information in view task details page
    And Close the soft assertions
    Examples:
      | taskId | taskType            | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || Missing Information || Created ||          ||            ||                || false         ||            ||          ||           ||

  @CP-22087 @CP-22087-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Validate Additional template fields for Missing info task in view and edit task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status       | Complete |
      | disposition  | Denied   |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DENIAL REASON "
    And I will update the following information in edit task page
      | disposition | Approved |
    And I click on save button on task edit page
    Then I verify "DENIAL REASON " filed is not mandatory for the task
    And I will update the following information in edit task page
      | disposition | Denied |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DENIAL REASON "
    And I will update the following information in edit task page
      | disposition | Referred |
    And I click on save button on task edit page
    Then I verify "DENIAL REASON " filed is not mandatory for the task
    And I will update the following information in edit task page
      | reasonForEdit             | Corrected Data Entry                      |
      | applicationType           | MAGI - PW                                 |
      | externalApplicationId     | Test1234                                  |
      | taskInfo                  | Test 123                                  |
      | myWorkSpaceDate           | -2                                        |
      | applicationReceivedDate   | today                                     |
      | applicationSignatureDate  | today                                     |
      | noOfApplicantsInHousehold | 1                                         |
      | channel                   | Paper                                     |
      | applicationStatus         | Data Collection                           |
      | actionTknSingle           | Sent NOA                                  |
      | denialReason              | Does not meet a full benefit alien status |
      | disposition               | Denied                                    |
      | noOfApprovedApplicants    | 1                                         |
      | missingInfoRequired       | No                                        |
      | decisionSource            | Manual                                    |
    And I click on save button on task edit page
    And I verify the updated information in view task details page
    And Close the soft assertions
    Examples:
      | taskId | taskType       | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || Process App V1 || Created | today      ||         ||        ||            | false         ||            ||          ||           ||

  @CP-22731 @CP-22731-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Validate DENIAL REASON fields enable and disable functionality for Application SR
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    #And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will update the following information in edit task page
      | status       | Closed |
      | disposition  | Denied |
      | denialReason ||
    And I click on save button on task edit page
    Then I verify "DENIAL REASON" field is enable and required
    And I will update the following information in edit task page
      | denialReason | Auto-Denied (Self-Direct) |
      | disposition  | Approved                  |
    Then I verify "DENIAL REASON" field is disable and cleared out
    And I will update the following information in edit task page
      | disposition | Auto-Denied |
    And I click on save button on task edit page
    Then I verify "DENIAL REASON" field is enable and required
    And I will update the following information in edit task page
      | denialReason | Auto-Denied (Self-Direct) |
      | disposition  | Auto-Approved             |
    Then I verify "DENIAL REASON" field is disable and cleared out
    And I will update the following information in edit task page
      | disposition | Denied |
    And I click on save button on task edit page
    Then I verify "DENIAL REASON" field is enable and required
    And I will update the following information in edit task page
      | denialReason | Auto-Denied (Self-Direct) |
      | disposition  | Referred                  |
    Then I verify "DENIAL REASON" field is disable and cleared out
    And I will update the following information in edit task page
      | disposition | Auto-Denied |
    And I click on save button on task edit page
    Then I verify "DENIAL REASON" field is enable and required
    And I will update the following information in edit task page
      | denialReason | Auto-Denied (Self-Direct) |
      | disposition  | Cancelled                 |
    Then I verify "DENIAL REASON" field is disable and cleared out
    And I will update the following information in edit task page
      | reasonForEdit             | Corrected Data Entry      |
      | missingInfoRequired       | No                        |
      | applicationType           | MAGI - PW                 |
      | externalApplicationId     | Test1234                  |
      | taskInfo                  | Test-#$123                |
      | externalCaseId            | 12345                     |
      | applicationReceivedDate   | today                     |
      | applicationSignatureDate  | today                     |
      | noOfApplicantsInHousehold | 1                         |
      | channel                   | Paper                     |
      | applicationStatus         | Data Collection           |
      | disposition               | Denied                    |
      | denialReason              | Auto-Denied (Self-Direct) |
      | decisionSource            | Manual                    |
    And I click on save button on task edit page
    Then I verify the updated information in view task details page
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType         | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy       | createdOn |contactReason|
      ||          | Application SR | Open   ||          ||            ||                || true          ||            ||          |Service TesterTwo|today|             |

  @CP-22731 @CP-22731-02 @CP-43239 @CP-43239-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Validate DENIAL REASON fields enable and disable functionality for Renewal SR
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will update the following information in edit task page
      | disposition  | Denied/Closed |
      | denialReason ||
    And I click on save button on task edit page
    Then I verify "DENIAL REASON" field is enable and required
    And I will update the following information in edit task page
      | denialReason | Auto-Denied (Self-Direct) |
      | disposition  | Approved                  |
    Then I verify "DENIAL REASON" field is disable and cleared out
    And I will update the following information in edit task page
      | disposition | Auto-Denied |
    And I click on save button on task edit page
    Then I verify "DENIAL REASON" field is enable and required
    And I will update the following information in edit task page
      | denialReason | Auto-Denied (Self-Direct) |
      | disposition  | Auto-Approved             |
    Then I verify "DENIAL REASON" field is disable and cleared out
    And I will update the following information in edit task page
      | disposition | Denied/Closed |
    And I click on save button on task edit page
    Then I verify "DENIAL REASON" field is enable and required
    And I will update the following information in edit task page
      | denialReason | Auto-Denied (Self-Direct) |
      | disposition  | Referred                  |
    Then I verify "DENIAL REASON" field is disable and cleared out
    And I will update the following information in edit task page
      | disposition | Auto-Denied |
    And I click on save button on task edit page
    Then I verify "DENIAL REASON" field is enable and required
    And I will update the following information in edit task page
      | denialReason | Auto-Denied (Self-Direct) |
      | disposition  | Cancelled                 |
    Then I verify "DENIAL REASON" field is disable and cleared out
    And I will update the following information in edit task page
      | reasonForEdit             | Corrected Data Entry      |
      | missingInfoRequired       | No                        |
      | applicationType           | MAGI - PW                 |
      | renewalDate               | today                     |
      | externalApplicationId     | Test1234                  |
      | taskInfo                  | Test-#$123                |
      | externalCaseId            | 12345                     |
      | myWorkSpaceDate           | -2                        |
      | applicationReceivedDate   | today                     |
      | applicationSignatureDate  | today                     |
      | noOfApplicantsInHousehold | 1                         |
      | channel                   | Paper                     |
      | applicationStatus         | Data Collection           |
      | disposition               | Denied/Closed             |
      | denialReason              | Auto-Denied (Self-Direct) |
      | decisionSource            | Manual                    |
    And I click on save button on task edit page
    Then I verify the updated information in view sr details page
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Renewal SR | Closed ||          ||            ||                || true          ||            ||          | Service TesterTwo ||             |

  @CP-26070 @CP-26070-02 @CP-13177 @CP-13177-04 @crm-regression @INEB-UI-Regression @ui-wf-ineb @vidya
  Scenario: Validate fields enable and disable functionality present in DCS Request on Create/ edit page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "DCS Request" task page
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | eligibilityDecision | Denied |
      | denialReasonSingle  | Other  |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | assignee    | Service TesterTwo |
      | other       | maxlength         |
      | actionTaken | Sent Denial Email |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | denialReasonSingle | Not Eligible |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | status          | Cancelled               |
      | reasonForCancel | Task No Longer Required |
      | reasonForEdit   | Corrected Data Entry    |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-23886 @CP-23886-02 @CP-34966 @CP-34966-02 @crm-regression @INEB-UI-Regression @ui-wf-ineb @vidya
  Scenario: Validate fields enable and disable functionality present in Hoosier Care Connect Do Not Call List Create/ edit page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HCC Do Not Call List" task page
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | reason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | assignee     | Service TesterTwo       |
      | contactPhone | 9876543210              |
      | other        | 123 $567 Tesr5565#$#535 |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | reason | No Longer Lives Here |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | status          | Cancelled               |
      | reasonForCancel | Task No Longer Required |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-24567 @CP-24567-02 @CP-34965 @CP-34965-01 @crm-regression @INEB-UI-Regression @ui-wf-ineb @priyal
  Scenario: Validate fields enable and disable functionality present in HCC Plan Change Form on Create/ edit page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HCC Plan Change Form" task page
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | assignee         | Service TesterTwo       |
      | currentPlan      | Anthem HCC              |
      | DesiredPlan      | Anthem HCC              |
      | planChangeReason | Other                   |
      | requestedDate    | today                   |
      | other            | 123 $567 Tesr5565#$#535 |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | planChangeReason | CP Error |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | status          | Cancelled               |
      | reasonForCancel | Task No Longer Required |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-25037 @CP-25037-02 @crm-regression @INEB-UI-Regression @ui-wf-ineb @priyal
  Scenario: Validate fields enable and disable functionality present in Disenrollment on Create/ edit page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Disenrollment" task page
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | source | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | assignee            | Service TesterTwo       |
      | currentPlan         | Anthem                  |
      | disenrollmentReason | PRTF                    |
      | other               | 123 $567 Tesr5565#$#535 |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | source | DCS |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | status          | Cancelled               |
      | reasonForCancel | Task No Longer Required |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-24568 @CP-24568-02 @crm-regression @INEB-UI-Regression @ui-wf-ineb @priyal
  Scenario: Validate fields enable and disable functionality present in HIP Plan Change Form on Create/ edit page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HIP Plan Change Form" task page
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | assignee      | Service TesterTwo       |
      | currentPlan   | Anthem HIP              |
      | DesiredPlan   | Anthem HIP              |
      | requestedDate | today                   |
      | other         | 123 $567 Tesr5565#$#535 |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | planChangeReason | Conditional |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | status          | Cancelled               |
      | reasonForCancel | Task No Longer Required |
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-24569 @CP-24569-02 @crm-regression @INEB-UI-Regression @ui-wf-ineb @priyal
  Scenario: Validate fields enable and disable functionality present in HHW Plan Change Form on Create/ edit page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
#    And I minimize Genesys popup if populates
    When I navigate to "HHW Plan Change Form" task page
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | assignee      | Service TesterTwo       |
      | currentPlan   | Anthem                  |
      | DesiredPlan   | Anthem                  |
      | requestedDate | today                   |
      | other         | 123 $567 Tesr5565#$#535 |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | planChangeReason | Annual Enrollment |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | status          | Cancelled      |
      | reasonForCancel | Duplicate Task |
    And I click on save button on task edit page
 #   Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-23438 @CP-23438-01 @CP-23437 @CP-23437-01 @crm-regression @INEB-UI-Regression @ui-wf-ineb @vidya
  Scenario: Validate fields enable and disable functionality present in Customer Service Complaint Create SR page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    Then I verify "Escalation Reason" field is disable and cleared out
    Then I verify "Other Entity" field is disable and cleared out
    And I will provide following information before creating task
      | escalated      | true  |
      | complaintAbout | Other |
    And I click on save button on task edit page
    Then I verify "Escalation Reason" field is enable and required
    Then I verify "Other Entity" field is enable and required
    And I will provide following information before creating task
      | escalationReason | Consumer Contacting the State |
      | organizationDD   | CareSource                    |
      | escalated        | false                         |
      | complaintAbout   | maersk                       |
    Then I verify "Escalation Reason" field is disable and cleared out
    Then I verify "Other Entity" field is disable and cleared out
    And I will provide following information before creating task
      | complaintAbout | Other |
    And I click on save button on task edit page
    Then I verify "Other Entity" field is enable and required
    And I will provide following information before creating task
      | organizationDD | GainWell           |
      | complaintAbout | Emergent Situation |
    Then I verify "Other Entity" field is disable and cleared out
    And Close the soft assertions

  @CP-23438 @CP-23438-02 @CP-23437 @CP-23437-02 @crm-regression @INEB-UI-Regression @ui-wf-ineb @vidya
  Scenario Outline: Validate fields enable and disable functionality present in Customer Service Complaint Edit SR page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will update the following information in edit task page
      | escalated      | false |
      | complaintAbout ||
    Then I verify "Escalation Reason" field is disable and cleared out
    Then I verify "Other Entity" field is disable and cleared out
    And I will update the following information in edit task page
      | escalated      | true  |
      | complaintAbout | Other |
    And I click on save button on task edit page
    Then I verify "Escalation Reason" field is enable and required
    Then I verify "Other Entity" field is enable and required
    And I will update the following information in edit task page
      | escalationReason | Consumer Contacting the State |
      | organizationDD   | DFR                           |
      | escalated        | false                         |
      | complaintAbout   | maersk                       |
    Then I verify "Escalation Reason" field is disable and cleared out
    Then I verify "Other Entity" field is disable and cleared out
    And I will update the following information in edit task page
      | complaintAbout | Other |
    And I click on save button on task edit page
    Then I verify "Other Entity" field is enable and required
    And I will update the following information in edit task page
      | organizationDD | MHS                |
      | complaintAbout | Emergent Situation |
    Then I verify "Other Entity" field is disable and cleared out
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | consumerName |contactReason|
      ||          | Customer Service Complaint ||            ||         ||        ||            ||            ||        ||           ||              ||

  @CP-43484 @CP-43484-01 @CP-44696 @CP-44696-01 @CP-45391 @CP-45391-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario Outline: Validate APPROVAL REASON fields enable and disable functionality based on disposition value for Renewal SR
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will update the following information in edit task page
      | status       | Closed |
      | disposition  | Approved |
    And I click on save button on task edit page
    Then I verify "APPROVAL REASON" field is enable and required
    And I verify "approvalReason" single select drop down value
      | Change In Coverage    |
      | No Change In Coverage |
    And I will update the following information in edit task page
      | disposition  | Auto-Denied |
    And I click on save button on task edit page
    Then I verify "Denial Reason" field is enable and required
    Then I verify "Approval Reason" field is disable and cleared out
    And I will update the following information in edit task page
      | disposition | Auto-Approved |
    And I click on save button on task edit page
    Then I verify "APPROVAL REASON" field is enable and required
    And I will update the following information in edit task page
      | disposition  | Cancelled |
    And I click on save button on task edit page
    Then I verify "Approval Reason" field is disable and cleared out
    And I will update the following information in edit task page
      | disposition | Reinstated and Approved |
    And I click on save button on task edit page
    Then I verify "APPROVAL REASON" field is enable and required
    And I will update the following information in edit task page
      | disposition  | Denied/Closed |
    And I click on save button on task edit page
    Then I verify "Denial Reason" field is enable and required
    Then I verify "Approval Reason" field is disable and cleared out
    And I will update the following information in edit task page
      | disposition | Referred |
    Then I verify "Approval Reason" field is disable and cleared out
    And I click on cancel button on create task type screen
    Then I click on continue button on warning message
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Renewal SR | Open   ||          ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

  @CP-43484 @CP-43484-02 @CP-45391 @CP-45391-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario Outline: Validate APPROVAL REASON isnt populated for Application SR
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will update the following information in edit task page
      | status       | Closed |
      | disposition  | Approved |
    Then I verify "Approval Reason" field is not displayed on Edit SR Page
    And I will update the following information in edit task page
      | disposition  | Auto-Denied |
    Then I verify "Approval Reason" field is not displayed on Edit SR Page
    And I will update the following information in edit task page
      | disposition | Auto-Approved |
    Then I verify "Approval Reason" field is not displayed on Edit SR Page
    And I will update the following information in edit task page
      | disposition  | Cancelled |
    Then I verify "Approval Reason" field is not displayed on Edit SR Page
    And I will update the following information in edit task page
      | disposition  | Denied |
    Then I verify "Approval Reason" field is not displayed on Edit SR Page
    Then I verify "disposition" field does not contains value on "edit" page
      | Denied/Closed |
    And I will update the following information in edit task page
      | disposition | Referred |
    Then I verify "Approval Reason" field is not displayed on Edit SR Page
    And I click on cancel button on create task type screen
    Then I click on continue button on warning message
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType         | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Application SR | Open   ||          ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

  @CP-44142 @CP-44142-01 @CP-44142-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Validate new Reinstated & Approved” Disposition to “Sent to NOA” Action Taken for Process Application Task and Final Application Review Task which is created for Renewal SR
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType          | MAGI - CPU |
      | externalApplicationId    | random     |
      | myWorkSpaceDate          | -31        |
      | renewalDate              | today      |
      | missingInfoRequired      | Yes        |
      | vclDueDate               | -2         |
      | channel                  | CommonHelp |
      | applicationSignatureDate | today      |
      | applicationReceivedDate  | today      |
      | miReceivedDate           | today      |
      | InbDocType               | Other      |
    And I click on save button in create service request page
    Then Wait for 4 seconds
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | dateOfContact | -31               |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click id of "Process Application" in Links section
    Then I click on edit button on view task page
    And I will update the following information in edit task page
      | status            | Complete |
      | actionTakenSingle | Sent VCL |
    And I verify "disposition" single select drop down value
      | Pending MI |
    And I will update the following information in edit task page
      | actionTakenSingle | Transferred to LDSS |
    And I verify "disposition" single select drop down value
      | Referred |
    And I will update the following information in edit task page
      | actionTakenSingle | Sent NOA |
    And I verify "disposition" single select drop down value
      | Approved                |
      | Auto-Approved           |
      | Auto-Denied             |
      | Denied/Closed           |
      | Reinstated and Approved |
    And I will update the following information in edit task page
      | actionTakenSingle | Sent VCL |
      | disposition       | Pending MI |
      | reasonForEdit     | Corrected Data Entry |
    Then I click on save button on task edit page
    And I click id of "Renewal SR" in Links section
    Then Wait for 3 seconds
    And I click id of "Final Application Review" in Links section
    Then I click on edit button on view task page
    And I will update the following information in edit task page
      | status  | Complete |
    And I verify "disposition" single select drop down value
      | Approved                |
      | Auto-Approved           |
      | Auto-Denied             |
      | Denied/Closed           |
      | Referred                |
      | Reinstated and Approved |
    And Close the soft assertions

  @CP-44142 @CP-44142-03 @CP-44142-04 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Validate new Reinstated & Approved” Disposition to “Sent to NOA” Action Taken NOT displayed for Process Application Task and Final Application Review Task which is created by Application SR
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      | applicationType       |MAGI Standard Application - CVIU |
      | externalApplicationId | random      |
      | myWorkSpaceDate       | -45         |
      | missingInfoRequired   | Yes         |
      | vclDueDate            | today       |
      | channel               | CommonHelp  |
      | vclSentDate           | today       |
      | InbDocType            | Other       |
    And I click on save button in create service request page
    Then Wait for 4 seconds
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | dateOfContact | -45               |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click id of "Process Application" in Links section
    Then I click on edit button on view task page
    And I will update the following information in edit task page
      | status            | Complete |
    And I verify "disposition" single select drop down value
      | Approved      |
      | Auto-Approved |
      | Auto-Denied   |
      | Denied        |
      | Pending MI    |
      | Referred      |
    And I will update the following information in edit task page
      | actionTakenSingle | Sent VCL |
    And I verify "disposition" single select drop down value
      | Pending MI |
    And I will update the following information in edit task page
      | actionTakenSingle | Transferred to LDSS |
    And I verify "disposition" single select drop down value
      | Referred |
    And I will update the following information in edit task page
      | actionTakenSingle | Sent NOA |
    And I verify "disposition" single select drop down value
      | Approved      |
      | Auto-Approved |
      | Auto-Denied   |
      | Denied        |
    And I will update the following information in edit task page
      | actionTakenSingle | Sent VCL |
      | disposition       | Pending MI |
      | reasonForEdit     | Corrected Data Entry |
    Then I click on save button on task edit page
    And I click id of "Application SR" in Links section
    Then Wait for 3 seconds
    And I click id of "Final Application Review" in Links section
    Then I click on edit button on view task page
    And I will update the following information in edit task page
      | status  | Complete |
    And I verify "disposition" single select drop down value
      | Approved      |
      | Auto-Approved |
      | Auto-Denied   |
      | Denied        |
      | Referred      |
    And Close the soft assertions

  @CP-47704 @CP-47703 @CP-47703-01 @CP-47704-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Validate new enabled fields based on Reinstated & Approved Disposition on Edit Renewal SR Page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType          | MAGI - CPU |
      | externalApplicationId    | random     |
      | myWorkSpaceDate          | -20        |
      | renewalDate              | today      |
      | missingInfoRequired      | Yes        |
      | vclDueDate               | -2         |
      | channel                  | CommonHelp |
      | applicationSignatureDate | today      |
      | applicationReceivedDate  | today      |
      | miReceivedDate           | today      |
      | InbDocType               | Other      |
    #missing info required date field is optional
    And I click on save button in create service request page
    Then Wait for 4 seconds
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | dateOfContact | -20               |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I click on edit service request button
    And I will update the following information in edit task page
      | status                    | Closed   |
      | disposition               | Reinstated and Approved |
      | noOfApplicantsInHousehold | 4          |
      | applicationStatus         | Inbound Call |
    Then I click on save button on task edit page
    Then I verify "Decision Source" field is enable and required
    Then I verify "Number Of Approved Applicants" field is enable and required
    Then I verify "VACMS Case ID" field is enable and required
    And I will update the following information in edit task page
      | disposition  | Approved |
    And I will update the following information in edit task page
      | disposition  | Denied/Closed |
    Then I click on save button on task edit page
    Then I verify "Decision Source" field is enable and required
    Then I verify "VACMS Case ID" field is enable and required
    And Close the soft assertions

  @CP-47984 @CP-47984-01 @CP-47984-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario Outline: Validate applicationId field format Application and Renewal SR in Create SR Page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "<srType>" service request page
    And I will provide following information before creating task
      | externalApplicationId | t81265904   |
    And I click on save button in create service request page
    Then I verify application id format warning message is displayed
    And I will provide following information before creating task
      | externalApplicationId | 1265904   |
    And I click on save button in create service request page
    Then I verify application id format warning message is displayed
    And I will provide following information before creating task
      | externalApplicationId    | 181265904   |
    And I click on save button in create service request page
    Then I verify application id format warning message is displayed
    Examples:
    | srType         |
    | Application SR |
    | Renewal SR     |

  @CP-47984 @CP-47984-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario Outline: Validate applicationId field format Application and Renewal SR on Edit SR Page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true       |
      | srTypes       | <srType>   |
      | createdOn     | today      |
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I click on edit service request button
    And I will update the following information in edit task page
      | externalApplicationId | t81265004   |
    Then I click on save button on task edit page
    Then I verify application id format warning message is displayed
    And I will update the following information in edit task page
      | externalApplicationId | 165904   |
    And I click on save button on task edit page
    Then I verify application id format warning message is displayed
    And I will update the following information in edit task page
      | externalApplicationId    | 181265904   |
    And I click on save button on task edit page
    Then I verify application id format warning message is displayed
    Examples:
      | srType         |
      | Application SR |
      | Renewal SR     |





