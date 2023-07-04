Feature: Task End To End Validation for IN-EB_2

  @CP-30402 @CP-30402-02 @crm-regression @INEB-UI-Regression @ui-wf-ineb @chandrakumar
  Scenario: Validate Healthy Indiana Plan, Plan Change Form Task created and data is saved
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HIP Plan Change Form" task page
    And I will provide following information before creating task
      | taskInfo         | Test CP-30402     |
      | assignee         | Service TesterTwo |
      | currentPlan      | Anthem HIP        |
      | DesiredPlan      | CareSource HIP    |
      | requestedDate    | today             |
      | planChangeReason | Other             |
      | other            | Other Information Added|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status           | Cancelled                   |
      | reasonForCancel  | Created Incorrectly         |
      | assignee         ||
      | currentPlan      | CareSource HIP              |
      | DesiredPlan      | Managed Health Services HIP |
      | requestedDate    | +5                          |
      | planChangeReason | No Plan Assigned            |
      | other            ||
      | taskInfo         ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30401 @CP-30401-02 @CP-34963 @CP-34963-02 @crm-regression @INEB-UI-Regression @ui-wf-ineb @chandrakumar @ruslan
  Scenario: Validate fields present in Healthy Indiana Tobacco Response Task created and data is saved
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HIP Tobacco Response" task page
    And I will provide following information before creating task
      | taskInfo                      | Test CP-30401     |
      | assignee                      | Service TesterTwo |
      | usedTobaccoInTheLastSixMonths | Yes               |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                        | Cancelled           |
      | reasonForCancel               | Created Incorrectly |
      | usedTobaccoInTheLastSixMonths | No |
      | assignee                      ||
      | taskInfo                      ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30403 @CP-30403-02 @CP-34965 @CP-34965-03 @crm-regression @INEB-UI-Regression @ui-wf-ineb @chandrakumar
  Scenario: Validate HCC Plan Change Form Task created and data is saved
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HCC Plan Change Form" task page
    And I will provide following information before creating task
      | taskInfo         | Test CP-30403               |
      | assignee         | Service TesterTwo           |
      | currentPlan      | Managed Health Services HCC |
      | DesiredPlan      | UHC                         |
      | requestedDate    | today                       |
      | planChangeReason | Other                       |
      | other            | Other Information Added     |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status           | Cancelled                   |
      | reasonForCancel  | Created Incorrectly         |
      | assignee         ||
      | currentPlan      | MDWise HCC                  |
      | DesiredPlan      | CareSource HCC              |
      | requestedDate    | +5                          |
      | planChangeReason | Disenroll                   |
      | other            ||
      | taskInfo         ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30396 @CP-30396-02 @crm-regression @ui-wf-ineb @vidya
  Scenario: Verification all fields and mandatory fields on After Hours Voicemail create task page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "After Hours Voicemail" task page
    And I will provide following information before creating task
      | assignee             | Service TesterTwo |
      | status               | Created           |
      | taskInfo             | test              |
      | contactName          | Vidya Mithun      |
      | contactPhone         | 1234467890        |
      | dateOfVoicemail      | today             |
      | timeOfVoicemail      | 12:00 PM          |
      | actionTaken          |Contacted Consumer - Reached|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status               |Cancelled          |
      | reasonForCancel      |Created Incorrectly|
      | assignee             ||
      | taskInfo             ||
      | contactName          | Priyal Gurg       |
      | contactPhone         | 0123456789        |
      | dateOfVoicemail      | +1                |
      | timeOfVoicemail      | 09:30 AM          |
      | actionTaken          ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30406 @CP-30406-02 @crm-regression @ui-wf-ineb @vidya
  Scenario: Verification all fields and mandatory fields on Inbound Document create task page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Inbound Document" task page
    And I will provide following information before creating task
      | assignee             |Service TesterTwo  |
      | taskInfo             |test CP-30406-01   |
      |externalConsumerID    | 123456789         |
      | actionTaken          |Responded via Email|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status               |Cancelled          |
      | reasonForCancel      |Created Incorrectly|
      | assignee             ||
      | taskInfo             ||
      |externalConsumerID    ||
      | actionTaken          ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30405 @CP-30405-02 @crm-regression @INEB-UI-Regression @ui-wf-ineb @chandrakumar
  Scenario: Validate HHW Plan Change Form Task created and data is saved
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HHW Plan Change Form" task page
    And I will provide following information before creating task
      | taskInfo         | Test CP-30405           |
      | assignee         | Service TesterTwo       |
      | currentPlan      | Managed Health Services |
      | DesiredPlan      | Anthem                  |
      | requestedDate    | today                   |
      | planChangeReason | Other                   |
      | other            | Other Info Added*&^$%#@ |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status           | Cancelled           |
      | reasonForCancel  | Created Incorrectly |
      | assignee         ||
      | currentPlan      | MDWise HH           |
      | DesiredPlan      | CareSource          |
      | requestedDate    | +5                  |
      | planChangeReason | Excluded            |
      | other            ||
      | taskInfo         ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30469 @CP-30469-02 @CP-30408 @CP-30408-02 @crm-regression @ui-wf-ineb @priyal
  Scenario Outline: Verification all fields and mandatory fields on Complaint Outbound Call/Outbound Call tasks on create/edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "<taskTypeName>" task page
    And I will provide following information before creating task
      | assignee                 | Service TesterTwo |
      | status                   | Created|
      | taskInfo                 | test|
      | contactName              | Vidya Mithun|
      | preferredCallBackDate    | today|
      | preferredCallBackTime    | 03:28 PM|
      | preferredPhone           | 1234567890|
      | actionTaken              | Contacted Consumer - Reached|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                   |Cancelled|
      | reasonForCancel          |Created Incorrectly|
      | assignee                 ||
      | taskInfo                 ||
      | contactName              ||
      | preferredCallBackDate    ||
      | preferredCallBackTime    ||
      | preferredPhone           ||
      | actionTaken              ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions
    Examples:
      |taskTypeName|
      |Complaint Outbound Call|
      |Outbound Call|

  @CP-30465 @CP-30465-03 @crm-regression @ui-wf-ineb @priyal
  Scenario: Verification all fields and mandatory fields on Customer Service Complaint SR on create/edit/view page
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I send API CALL for Create CaseConsumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I store external case id from Demographic Info Page
    And I store external consumer id from Demographic Info Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | taskInfo              |test->30465|
      | memberName            | Priyal Garg|
      | preferredPhone        | 1234567890|
      | complaintAbout        | maersk|
      | complaintType         | Customer Service|
      | incidentDate          | today|
      | preferredCallBackDate | today|
      | preferredCallBackTime | 04:30 PM|
      | contactName           | Test|
      | escalated             | true|
      | escalationReason      | Consumer Contacting the State |
      | csrName               | Service TesterTwo|
      | priority              | 3|
    And I click on save button in create service request page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I verify the SR details displayed on view SR page
    And I click on edit service request button
    And I will update the following information in edit task page
      | status                | Closed               |
      | reasonForEdit         | Corrected Data Entry |
      | disposition           | Complaint Invalid    |
      | preferredPhone        ||
      | complaintAbout        | maersk|
      | csrName               ||
      | preferredCallBackDate ||
      | preferredCallBackTime ||
      | contactName           ||
      | escalated             | false|
      | escalationReason      ||
    And I click on save button on task edit page
    And I verify the updated information in view task details page
    And Close the soft assertions
