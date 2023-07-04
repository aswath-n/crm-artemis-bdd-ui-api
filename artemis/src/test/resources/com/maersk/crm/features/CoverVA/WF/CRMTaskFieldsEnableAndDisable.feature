Feature: Verify Task Fields enable and disable functionality

  @CP-22803 @CP-22803-02 @CP-22801 @CP-22801-02 @CP-22802 @CP-22802-02 @CP-22800 @CP-22800-02 @CP-22799 @CP-22799-02 @CP-22784 @CP-22784-02 @CP-19570 @CP-19570-03 @CP-43009-01 @CP-47703 @CP-47703-02 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate fields enable and disable functionality present in Renewal SR and Application type displayed on view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType       | Ex Parte Renewal - CVIU |
      | applicationSignatureDate | today                    |
      | applicationReceivedDate  | today                    |
      | facilityName             | Bristol City Jail        |
      | externalApplicationId | random                  |
      | myWorkSpaceDate       | today                   |
      | caseWorkerFirstName   | Vidya                   |
      | caseWorkerLastName    | Mithun                  |
      #| transferReason        | Case Change             |
      #| locality              | Albemarle               |
      | channel               | CommonHelp |
      | renewalDate              | today      |
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
    Then I verify "VCL Due Date" field is enabled
    Then I verify "VCL Sent Date" field is disable and cleared out
    Then I verify "Document Type" field is disable and cleared out
    Then I verify "Locality" field is disable and cleared out
    Then I verify "Case Worker Name" field is disable and cleared out
    Then I verify "Transfer Reason" field is disable and cleared out
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
    And I will update the following information in edit task page
      | InbDocType          | Earned Income |
      | caseWorkerFirstName | Vidya         |
      | caseWorkerLastName  | Mithun        |
      | transferReason      | Non MAGI - ABD|
      | locality            | Albemarle     |
      | missingInfoRequired | No            |
    And I click on save button on task edit page
    Then I verify "VCL Due Date" field is enabled
    Then I verify "VCL Sent Date" field is disable and cleared out
    Then I verify "Document Type" field is disable and cleared out
#    Then I verify "Locality" field is disable and cleared out
#    Then I verify "Case Worker Name" field is disable and cleared out
#    Then I verify "Transfer Reason" field is disable and cleared out
    And I will update the following information in edit task page
      | status              | Closed   |
      | missingInfoRequired | Yes      |
      | miReceivedDate      | today    |
      | disposition         | Referred |
      | caseWorkerFirstName ||
      | caseWorkerLastName  ||
      | transferReason      ||
      | locality            ||
    And I click on save button on task edit page
    Then I verify "VCL Due Date" field is enable and required
    Then I verify "VCL Sent Date" field is enable and required
    Then I verify "Document Type" field is enable and required
    Then I verify "Locality" field is enable and required
    Then I verify "Case Worker Name" field is enable and required
    Then I verify "Transfer Reason" field is enable and required
    And I will update the following information in edit task page
      | vclDueDate          | +1            |
      | InbDocType          | Earned Income |
      | vclSentDate         | today         |
      | caseWorkerFirstName | Vidya         |
      | caseWorkerLastName  | Mithun        |
      | transferReason      | Pending Gap Filling Evaluation|
      | locality            | Albemarle     |
      | missingInfoRequired ||
      | disposition         | Denied/Closed |
    And I click on save button on task edit page
    Then I verify "VCL Due Date" field is enabled
    Then I verify "VCL Sent Date" field is disable and not empty
    Then I verify "Document Type" field is disable and cleared out
    Then I verify "Locality" field is disable and cleared out
    Then I verify "Case Worker Name" field is disable and cleared out
    Then I verify "Transfer Reason" field is disable and cleared out
    And I will update the following information in edit task page
      | disposition | Referred |
    And I click on save button on task edit page
    Then I verify "Locality" field is enable and required
    Then I verify "Case Worker Name" field is enable and required
    Then I verify "Transfer Reason" field is enable and required
    And I will update the following information in edit task page
      | caseWorkerFirstName | Vidya       |
      | caseWorkerLastName  | Mithun      |
      | transferReason      | Requested by LDSS |
      | locality            | Albemarle   |
      | missingInfoRequired ||
      | disposition         | Auto-Denied |
    And I click on save button on task edit page
    Then I verify "Locality" field is disable and cleared out
    Then I verify "Case Worker Name" field is disable and cleared out
    Then I verify "Transfer Reason" field is disable and cleared out
    And I will update the following information in edit task page
      | disposition | Referred |
    And I click on save button on task edit page
    Then I verify "Locality" field is enable and required
    Then I verify "Case Worker Name" field is enable and required
    Then I verify "Transfer Reason" field is enable and required
    And I will update the following information in edit task page
      | caseWorkerFirstName | Vidya         |
      | caseWorkerLastName  | Mithun        |
      | transferReason      | Re-registration|
      | locality            | Albemarle     |
      | missingInfoRequired ||
      | disposition         | Auto-Approved |
    And I click on save button on task edit page
    Then I verify "Locality" field is disable and cleared out
    Then I verify "Case Worker Name" field is disable and cleared out
    Then I verify "Transfer Reason" field is disable and cleared out
    And I will update the following information in edit task page
      | disposition | Referred |
    And I click on save button on task edit page
    Then I verify "Locality" field is enable and required
    Then I verify "Case Worker Name" field is enable and required
    Then I verify "Transfer Reason" field is enable and required
    And I will update the following information in edit task page
      | caseWorkerFirstName | Vidya       |
      | caseWorkerLastName  | Mithun      |
      | transferReason      | RMC Completed |
      | locality            | Albemarle   |
      | missingInfoRequired ||
      | disposition         | Approved    |
    And I click on save button on task edit page
    Then I verify "Locality" field is disable and cleared out
    Then I verify "Case Worker Name" field is disable and cleared out
    Then I verify "Transfer Reason" field is disable and cleared out
    And I will update the following information in edit task page
      | status                    | Closed               |
      | reasonForEdit             | Corrected Data Entry |
      | missingInfoRequired       | Yes                  |
      | miReceivedDate            | today                |
      | vclDueDate                | today                |
      | disposition               | Cancelled            |
      | InbDocType                | Drivers License      |
      | channel                   | RDE                  |
      | noOfApplicantsInHousehold | 1                    |
      | applicationReceivedDate   | today                |
      | applicationStatus         | Data Collection      |
      | vclSentDate               | today                |
      | caseWorkerFirstName       ||
      | caseWorkerLastName        ||
      | transferReason            ||
      | locality                  ||
    And I click on save button on task edit page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-25771 @CP-25771-01 @CP-30299 @CP-30299-05 @crm-regression @INEB-UI-Regression @ui-wf-ineb @priyal @ruslan
  Scenario: Validate fields enable and disable functionality present in DCS Request on Create/ edit page based on Denial Reason
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "DCS Request" task page
    Then I verify "Denial Reason" field is disable and cleared out
    And I will provide following information before creating task
      | eligibilityDecision | Denied |
    And I click on save button in create task page
    Then I verify "Denial Reason" field is enable and required
    And I will provide following information before creating task
      | denialReasonSingle  | Other             |
      | assignee            | Service TesterTwo |
      | eligibilityDecision | Approved          |
    Then I verify "Denial Reason" field is disable and cleared out
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | eligibilityDecision ||
    Then I verify "Denial Reason" field is disable and cleared out
    And I will update the following information in edit task page
      | eligibilityDecision | Denied |
    And I click on save button on task edit page
    Then I verify "Denial Reason" field is enable and required
    And I will update the following information in edit task page
      | denialReasonSingle  | Not Eligible |
      | eligibilityDecision | Approved     |
    Then I verify "Denial Reason" field is disable and cleared out
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    Then I verify task table record for the task
    And Close the soft assertions

  @CP-26070 @CP-26070-01 @crm-regression @INEB-UI-Regression @ui-wf-ineb @vidya
  Scenario: Validate fields enable and disable functionality present in DCS Request on Create/ edit page based on Other field
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
      | other              | Test @ 1234  |
      | denialReasonSingle | Not Eligible |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | denialReasonSingle | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other              | 123 $567 Tesr5565#$#535 |
      | denialReasonSingle | Eligibility Ending      |
    Then I verify "Other" field is disable and cleared out
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | denialReasonSingle | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other              | Test @ 1234  |
      | denialReasonSingle | Not Eligible |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | denialReasonSingle | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other              | 123 $567 Tesr5565#$#535 |
      | denialReasonSingle | Eligibility Ending      |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | status             | Complete             |
      | reasonForEdit      | Corrected Data Entry |
      | denialReasonSingle | Other                |
      | other              | maxlength            |
      | actionTaken        | Sent Denial Email    |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-23886 @CP-23886-01 @CP-34966 @CP-34966-03 @crm-regression @INEB-UI-Regression @ui-wf-ineb @vidya
  Scenario: Validate fields enable and disable functionality present in DCS Request on Create/ edit page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HCC Do Not Call List" task page
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | reason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other  | maxlength |
      | reason | Death     |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | reason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other  | 123 $567 Tesr5565#$#535 |
      | reason | No Longer Lives Here    |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | reason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other  | Test 123@546464@$#@#@$#@ gfgfgfhfg |
      | reason | Not Managed Care                   |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | reason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other  | Test @123 Tesrt256253#$#$%#%$ |
      | reason | Selected a Plan               |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | reason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other        | maxlength    |
      | reason       | Wrong Number |
      | contactPhone | 9876543210   |
    Then I verify "Other" field is disable and cleared out
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | reason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other  | maxlength |
      | reason | Death     |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | reason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other  | 123 $567 Tesr5565#$#535 |
      | reason | No Longer Lives Here    |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | reason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other  | Test 123@546464@$#@#@$#@ gfgfgfhfg |
      | reason | Not Managed Care                   |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | reason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other  | Test @123 Tesrt256253#$#$%#%$ |
      | reason | Selected a Plan               |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | reason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other  | Test @123 Tesrt256253#$#$%#%$ |
      | reason | Wrong Number                  |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | status        | Complete                      |
      | reasonForEdit | Corrected Data Entry          |
      | reason        | Other                         |
      | other         | Test @123 Tesrt256253#$#$%#%$ |
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

    # refactoring by Priyal on 14-09-2021
  @CP-24567 @CP-24567-01 @CP-34965 @CP-34965-05 @crm-regression @INEB-UI-Regression @ui-wf-ineb @priyal
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
      | other            | maxlength |
      | planChangeReason | CP Error  |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other            | 123 $567 Tesr5565#$#535 |
      | planChangeReason | CP Shows Plan           |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other            | Test 123@546464@$#@#@$#@ gfgfgfhfg |
      | planChangeReason | Disenroll                          |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other            | Test @123 Tesrt256253#$#$%#%$ |
      | planChangeReason | Excluded                      |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other            | maxlength         |
      | planChangeReason | Future Assignment |
      | currentPlan      | Anthem HCC        |
      | DesiredPlan      | CareSource HCC    |
      | requestedDate    | today             |
    Then I verify "Other" field is disable and cleared out
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other            | maxlength                |
      | planChangeReason | Just Cause Family Change |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other            | 123 $567 Tesr5565#$#535 |
      | planChangeReason | New to the HCC Program  |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other            | Test 123@546464@$#@#@$#@ gfgfgfhfg |
      | planChangeReason | Open Enrollment                    |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other            | Test @123 Tesrt256253#$#$%#%$ |
      | planChangeReason | Provider Change               |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | status           | Complete                      |
      | reasonForEdit    | Corrected Data Entry          |
      | currentPlan      | Anthem HCC                    |
      | planChangeReason | Other                         |
      | other            | Test @123 Tesrt256253#$#$%#%$ |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

    # refactoring by Priyal on 14-09-2021
  @CP-25037 @CP-25037-01 @crm-regression @INEB-UI-Regression @ui-wf-ineb @priyal
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
      | other  | maxlength |
      | source | DCS       |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | source | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other  | 123 $567 Tesr5565#$#535 |
      | source | FSSA                    |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | source | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other  | Test @123 Tesrt256253#$#$%#%$ |
      | source | Member                        |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | source | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other               | maxlength              |
      | currentPlan         | Anthem                 |
      | disenrollmentReason | PRTF                   |
      | source              | Neurodiagnostic Center |
    Then I verify "Other" field is disable and cleared out
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | source | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other  | maxlength               |
      | source | Richmond State Hospital |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | source | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other  | Test @123 Tesrt256253#$#$%#%$ |
      | source | State                         |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | status              | Complete                      |
      | reasonForEdit       | Corrected Data Entry          |
      | currentPlan         | Anthem                        |
      | disenrollmentReason | PRTF                          |
      | source              | Other                         |
      | disposition         | Approved                      |
      | disenrollmentDate   | today                         |
      | other               | Test @123 Tesrt256253#$#$%#%$ |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-24568 @CP-24568-01 @crm-regression @INEB-UI-Regression @ui-wf-ineb @priyal
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
      | other            | maxlength   |
      | planChangeReason | Conditional |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other            | 123 $567 Tesr5565#$#535     |
      | planChangeReason | Conditional Open Enrollment |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other            | Test 123@546464@$#@#@$#@ gfgfgfhfg |
      | planChangeReason | CP Error                           |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other            | Test @123 Tesrt256253#$#$%#%$ |
      | planChangeReason | EB Error                      |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other            | maxlength                         |
      | planChangeReason | Eligibility Issue Open Enrollment |
      | currentPlan      | Anthem HIP                        |
      | DesiredPlan      | Anthem HIP                        |
      | requestedDate    | today                             |
    Then I verify "Other" field is disable and cleared out
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other            | maxlength        |
      | planChangeReason | No Plan Assigned |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other            | 123 $567 Tesr5565#$#535 |
      | planChangeReason | Program Change          |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other            | Test 123@546464@$#@#@$#@ gfgfgfhfg |
      | planChangeReason | Not Given Time to Select Plan      |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other            | Test @123 Tesrt256253#$#$%#%$ |
      | planChangeReason | Wrong MCE                     |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | status           | Complete                      |
      | reasonForEdit    | Corrected Data Entry          |
      | currentPlan      | Anthem HIP                    |
      | DesiredPlan      | CareSource HIP                |
      | planChangeReason | Other                         |
      | other            | Test @123 Tesrt256253#$#$%#%$ |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-24569 @CP-24569-01 @crm-regression @INEB-UI-Regression @ui-wf-ineb @priyal
  Scenario: Validate fields enable and disable functionality present in HHW Plan Change Form on Create/ edit page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HHW Plan Change Form" task page
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other            | maxlength         |
      | planChangeReason | Annual Enrollment |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other            | 123 $567 Tesr5565#$#535 |
      | planChangeReason | CP Error                |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other            | Test 123@546464@$#@#@$#@ gfgfgfhfg |
      | planChangeReason | CP Shows Plan                      |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other            | Test @123 Tesrt256253#$#$%#%$ |
      | planChangeReason | Excluded                      |
    Then I verify "Other" field is disable and cleared out
    And I will provide following information before creating task
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other            | maxlength         |
      | planChangeReason | Future Assignment |
      | currentPlan      | Anthem            |
      | DesiredPlan      | Anthem            |
      | requestedDate    | today             |
    Then I verify "Other" field is disable and cleared out
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other            | maxlength                |
      | planChangeReason | Just Cause Family Change |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other            | 123 $567 Tesr5565#$#535 |
      | planChangeReason | Open Enrollment         |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | planChangeReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other            | Test 123@546464@$#@#@$#@ gfgfgfhfg |
      | planChangeReason | Provider Change                    |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | status           | Complete                      |
      | reasonForEdit    | Corrected Data Entry          |
      | currentPlan      | Anthem                        |
      | DesiredPlan      | CareSource                    |
      | planChangeReason | Other                         |
      | other            | Test @123 Tesrt256253#$#$%#%$ |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-25066 @CP-25066-01 @CP-25065 @CP-25065-01 @crm-regression @INEB-UI-Regression @ui-wf-ineb @vidya
  Scenario: Validate fields enable and disable functionality present on HCC Outbound Call task Create/ edit page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HCC Outbound Call" task page
    Then I verify "Other" field is disable and cleared out
    Then I verify "Invalid Reason" field is disable and cleared out
    And I will provide following information before creating task
      | status      | Complete |
      | disposition | Invalid  |
    And I click on save button on task edit page
    Then I verify "Invalid Reason" field is enable and required
    And I will provide following information before creating task
      | invalidReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other         | 123 $567 Tesr5565#$#535   |
      | invalidReason | Plan Selection Not Needed |
      | disposition   | Plan Selection Made       |
    Then I verify "Other" field is disable and cleared out
    Then I verify "Invalid Reason" field is disable and cleared out
    And I will provide following information before creating task
      | disposition | Invalid |
    And I click on save button on task edit page
    Then I verify "Invalid Reason" field is enable and required
    And I will provide following information before creating task
      | invalidReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other         | 123 $567 Tesr5565#$#535 |
      | invalidReason | Not an HCC Member       |
      | disposition   | Needs Plan Selection    |
    Then I verify "Other" field is disable and cleared out
    Then I verify "Invalid Reason" field is disable and cleared out
    And I will provide following information before creating task
      | disposition   | Invalid |
      | invalidReason | Other   |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | other         | 123 $567 Tesr5565#$#535 |
      | invalidReason | Not an HCC Member       |
      | disposition   | Plan Selection Made     |
    Then I verify "Other" field is disable and cleared out
    Then I verify "Invalid Reason" field is disable and cleared out
    And I will provide following information before creating task
      | disposition | Invalid |
    And I click on save button on task edit page
    Then I verify "Invalid Reason" field is enable and required
    And I will provide following information before creating task
      | invalidReason | Other                   |
      | other         | 123 $567 Tesr5565#$#535 |
      | status        | Created                 |
    Then I verify "Other" field is disable and cleared out
    Then I verify "Invalid Reason" field is disable and cleared out
    And I click on save button on task edit page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    Then Wait for 5 seconds
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | status      | Complete |
      | disposition | Invalid  |
    And I click on save button on task edit page
    Then I verify "Invalid Reason" field is enable and required
    And I will update the following information in edit task page
      | invalidReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other         | Test @ 1234 |
      | invalidReason ||
      | disposition   ||
    Then I verify "Other" field is disable and cleared out
    Then I verify "Invalid Reason" field is disable and cleared out
    And I will update the following information in edit task page
      | disposition | Invalid |
    And I click on save button on task edit page
    Then I verify "Invalid Reason" field is enable and required
    And I will update the following information in edit task page
      | invalidReason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other         | 123 $567 Tesr5565#$#535 |
      | invalidReason | Not an HCC Member       |
      | disposition   | Plan Selection Made     |
    Then I verify "Other" field is disable and cleared out
    Then I verify "Invalid Reason" field is disable and cleared out
    And I will update the following information in edit task page
      | disposition   | Invalid |
      | invalidReason | Other   |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other         | 123 $567 Tesr5565#$#535   |
      | invalidReason | Plan Selection Not Needed |
      | disposition   | Needs Plan Selection      |
    Then I verify "Other" field is disable and cleared out
    Then I verify "Invalid Reason" field is disable and cleared out
    And I will update the following information in edit task page
      | disposition   | Invalid |
      | invalidReason | Other   |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other         | 123 $567 Tesr5565#$#535 |
      | invalidReason | Not an HCC Member       |
      | disposition   | Plan Selection Made     |
    Then I verify "Other" field is disable and cleared out
    Then I verify "Invalid Reason" field is disable and cleared out
    And I will update the following information in edit task page
      | disposition   | Invalid                 |
      | invalidReason | Other                   |
      | other         | 123 $567 Tesr5565#$#535 |
      | status        | Escalated               |
    Then I verify "Other" field is disable and cleared out
    Then I verify "Invalid Reason" field is disable and cleared out
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Invalid              |
      | invalidReason | Other                |
      | actionTaken   | No Action Taken      |
      | other         | maxlength            |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-27301 @CP-27301-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Validate fields enable and disable functionality present in HPE Final Decision on Create / Task Slider page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "HPE Final Decision" task page
    Then I verify "sentNOADate" field is disable and cleared out
    And I will provide following information before creating task
      | actionTaken | Other,Sent NOA |
    And I click on save button in create task page
    Then I verify "sentNOADate" field is enable and required
    And I will provide following information before creating task
      | sentNOADate | today               |
      | actionTaken | Corrected Exception |
    Then I verify "sentNOADate" field is disable and cleared out
    And I will provide following information before creating task
      | actionTaken | Corrected Exception,Other,Escalated to DMAS,Sent NOA |
    And I click on save button in create task page
    Then I verify "sentNOADate" field is enable and required
    And I will provide following information before creating task
      | sentNOADate | today                                       |
      | actionTaken | Corrected Exception,Other,Escalated to DMAS |
    Then I verify "sentNOADate" field is disable and cleared out
    And I will provide following information before creating task
      | actionTaken | Sent NOA |
    And I click on save button in create task page
    Then I verify "sentNOADate" field is enable and required
    And I will provide following information before creating task
      | sentNOADate | today             |
      | actionTaken | Escalated to DMAS |
    Then I verify "sentNOADate" field is disable and cleared out
    And I will provide following information before creating task
      | actionTaken | Sent NOA |
    And I click on save button in create task page
    Then I verify "sentNOADate" field is enable and required
    And I will provide following information before creating task
      | sentNOADate         | today                                |
      | actionTaken         | Corrected Exception,Uploaded to DMIS |
      | outcome             | Final Denial                         |
      | contactRecordSingle | Eligibility Status                   |
    Then I verify "sentNOADate" field is disable and cleared out
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    When I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    Then I verify "sentNOADate" field is disable and cleared out
    And I will update the following information in edit task page
      | actionTaken | Other,Sent NOA |
    And I click on save button on task edit page
    Then I verify "sentNOADate" field is enable and required
    And I will update the following information in edit task page
      | sentNOADate | today               |
      | actionTaken | Corrected Exception |
    Then I verify "sentNOADate" field is disable and cleared out
    And I will update the following information in edit task page
      | actionTaken | Corrected Exception,Escalated to DMAS,Sent NOA |
    And I click on save button on task edit page
    Then I verify "sentNOADate" field is enable and required
    And I will update the following information in edit task page
      | sentNOADate | today                   |
      | actionTaken | Other,Escalated to DMAS |
    Then I verify "sentNOADate" field is disable and cleared out
    And I will update the following information in edit task page
      | actionTaken | Sent NOA |
    And I click on save button on task edit page
    Then I verify "sentNOADate" field is enable and required
    And I will update the following information in edit task page
      | sentNOADate | today            |
      | actionTaken | Uploaded to DMIS |
    Then I verify "sentNOADate" field is disable and cleared out
    And I will update the following information in edit task page
      | actionTaken | Sent NOA |
    And I click on save button on task edit page
    Then I verify "sentNOADate" field is enable and required
    And I will update the following information in edit task page
      | sentNOADate         | today                                |
      | actionTaken         | Corrected Exception,Uploaded to DMIS |
      | outcome             | Final Denial                         |
      | contactRecordSingle | Eligibility Status                   |
    Then I verify "sentNOADate" field is disable and cleared out
    And I click on save button on task edit page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-28341 @CP-28341-01 @crm-regression @INEB-UI-Regression @ui-wf-ineb @priyal
  Scenario Outline: Validate fields enable and disable functionality present in Inbound Document on Create/ edit/page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | actionTaken ||
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | actionTaken | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other       | maxlength         |
      | actionTaken | Responded via Fax |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | actionTaken | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other       | 123 $567 Tesr5565#$#535 |
      | actionTaken | Responded via Email     |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | actionTaken | Other,Responded via Email |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will update the following information in edit task page
      | other       | Test 123@546464@$#@#@$#@ gfgfgfhfg      |
      | actionTaken | Responded via Phone,Responded via Email |
    Then I verify "Other" field is disable and cleared out
    And I will update the following information in edit task page
      | status        | Complete                      |
      | medicaidId    | 234567651                     |
      | reasonForEdit | Corrected Data Entry          |
      | actionTaken   | Other                         |
      | other         | Test @123 Tesrt256253#$#$%#%$ |
    And I click on save button on task edit page
#      Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions
    Examples:
      | taskId | taskType         | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || Inbound Document || Created ||          ||            ||                ||               ||            ||          ||           ||

  @CP-22783 @CP-22783-01 @CP-22782 @CP-22782-01 @CP-23708 @CP-23708-01 @CP-40291 @CP-40291-02 @CP-43014 @CP-43014-01 @CP-43007 @CP-43007-02 @CP-43442-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal @ruslan
  Scenario Outline: Validate fields enable and disable functionality present in  Renewal SR edit/page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    Then I verify "Number Of Approved Applicants" field is disable and cleared out
    Then I verify "Decision Source" field is disable and cleared out
    And I will update the following information in edit task page
      | actionTaken | Sent VCL |
    And I click on save button on task edit page
    Then I verify "Decision Source" field is enabled
    And I will update the following information in edit task page
      | deselectActionTaken | Sent VCL            |
      | actionTaken         | Transferred to LDSS |
    And I click on save button on task edit page
    Then I verify "Decision Source" field is enabled
    And I will update the following information in edit task page
      | status                    | Closed              |
      | noOfApplicantsInHousehold | 1                   |
      | channel                   | Paper               |
      | applicationStatus         | Data Collection     |
      | externalCaseId            ||
      | missingInfoRequired       | No                  |
      | applicationReceivedDate   | today               |
      | applicationSignatureDate  | today               |
      | deselectActionTaken       | Transferred to LDSS |
      | disposition               | Approved            |
    And I click on save button on task edit page
    Then I verify "Number Of Approved Applicants" field is enable and required
    Then I verify "Decision Source" field is enable and required
    Then I verify "VACMS Case ID" field is enable and required
    Then I verify "Approval Reason" field is enabled
    And I will update the following information in edit task page
      | decisionSource | VaCMS  |
      | externalCaseId | 123456 |
    Then I verify text box Date and Time field value and error message for following fields
      | Number Of Approved Applicants |
    And I will update the following information in edit task page
      | reasonForEdit             | Corrected Data Entry |
      | noOfApplicantsInHousehold ||
      | noOfApprovedApplicants    | 3                    |
      | disposition               | Referred             |
    Then I verify "Number Of Approved Applicants" field is enabled
    Then I verify "Decision Source" field is enabled
    Then I verify "Approval Reason" field is disable and cleared out
    And I click on save button on task edit page
    Then I verify "VACMS CASE ID " filed is not mandatory for the task
    And I will update the following information in edit task page
      | decisionSource         ||
      | noOfApprovedApplicants ||
      | externalCaseId         ||
      | disposition            | Auto-Approved |
    And I click on save button on task edit page
    Then I verify "Number Of Approved Applicants" field is enable and required
    Then I verify "Approval Reason" field is enabled
    And I verify "approvalReason" single select drop down value
      | Change In Coverage   |
      | No Change In Coverage|
    Then I verify "Decision Source" field is enable and required
    Then I verify "VACMS Case ID" field is enable and required
    And I will update the following information in edit task page
      | decisionSource         | Manual |
      | noOfApprovedApplicants | 3      |
      | externalCaseId         | 45535  |
      | disposition            ||
    Then I verify "Number Of Approved Applicants" field is disable and cleared out
    Then I verify "Decision Source" field is disable and cleared out
    And I click on save button on task edit page
    Then I verify "VACMS CASE ID " filed is not mandatory for the task
    And I will update the following information in edit task page
      | externalCaseId ||
      | disposition    | Auto-Denied |
    Then I verify "Number Of Approved Applicants" field is disable and cleared out
    Then I verify "Approval Reason" field is disable and cleared out
    And I click on save button on task edit page
    Then I verify "Decision Source" field is enable and required
    Then I verify "VACMS Case ID" field is enable and required
    And I will update the following information in edit task page
      | disposition | Approved |
    And I click on save button on task edit page
    Then I verify "Number Of Approved Applicants" field is enable and required
    Then I verify "Decision Source" field is enable and required
    Then I verify "VACMS Case ID" field is enable and required
    And I will update the following information in edit task page
      | noOfApprovedApplicants | 20     |
      | disposition            | Denied/Closed |
    Then I verify "Number Of Approved Applicants" field is disable and cleared out
    Then I verify "Approval Reason" field is disable and cleared out
    And I click on save button on task edit page
    Then I verify "Decision Source" field is enable and required
    Then I verify "VACMS Case ID" field is enable and required
    And I will update the following information in edit task page
      | decisionSource | VaCMS + Manual |
      | externalCaseId | 54673          |
      | disposition    | Cancelled      |
    Then I verify "Number Of Approved Applicants" field is disable and cleared out
    Then I verify "Approval Reason" field is disable and cleared out
    Then I verify "Decision Source" field is disable and cleared out
    And I click on save button on task edit page
    Then I verify "VACMS CASE ID " filed is not mandatory for the task
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Renewal SR | Open   ||      3    ||            ||                || true          ||            ||          | Service TesterTwo ||             |


  @CP-22783 @CP-22783-01-1 @CP-22782 @CP-22782-01-1 @CP-23708 @CP-23708-01-1 @CP-40291 @CP-40291-02-1 @CP-43014 @CP-43014-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal @ruslan
  Scenario Outline: Validate fields enable and disable functionality present in Application SR edit/page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    Then I verify "Number Of Approved Applicants" field is disable and cleared out
    Then I verify "Decision Source" field is disable and cleared out
    And I will update the following information in edit task page
      | actionTaken | Sent VCL |
    And I click on save button on task edit page
    Then I verify "Decision Source" field is enabled
    And I will update the following information in edit task page
      | deselectActionTaken | Sent VCL            |
      | actionTaken         | Transferred to LDSS |
    And I click on save button on task edit page
    Then I verify "Decision Source" field is enabled
    And I will update the following information in edit task page
      | status                    | Closed              |
      | noOfApplicantsInHousehold | 1                   |
      | channel                   | Paper               |
      | applicationStatus         | Data Collection     |
      | externalCaseId            ||
      | missingInfoRequired       | No                  |
      | applicationReceivedDate   | today               |
      | applicationSignatureDate  | today               |
      | deselectActionTaken       | Transferred to LDSS |
      | disposition               | Approved            |
    And I click on save button on task edit page
    Then I verify "Number Of Approved Applicants" field is enable and required
    Then I verify "Approval Reason" field is disable and cleared out
    Then I verify "Decision Source" field is enable and required
    Then I verify "VACMS Case ID" field is enable and required
    And I will update the following information in edit task page
      | decisionSource | VaCMS  |
      | externalCaseId | 123456 |
    Then I verify text box Date and Time field value and error message for following fields
      | Number Of Approved Applicants |
    And I will update the following information in edit task page
      | reasonForEdit             | Corrected Data Entry |
      | noOfApplicantsInHousehold ||
      | noOfApprovedApplicants    | 3                    |
      | disposition               | Referred             |
    Then I verify "Number Of Approved Applicants" field is enabled
    Then I verify "Approval Reason" field is enabled
    Then I verify "Decision Source" field is enabled
    And I click on save button on task edit page
    Then I verify "VACMS CASE ID " filed is not mandatory for the task
    And I will update the following information in edit task page
      | decisionSource         ||
      | noOfApprovedApplicants ||
      | externalCaseId         ||
      | disposition            | Auto-Approved |
    And I click on save button on task edit page
    Then I verify "Number Of Approved Applicants" field is enable and required
    Then I verify "Approval Reason" field is disable and cleared out
    Then I verify "Decision Source" field is enable and required
    Then I verify "VACMS Case ID" field is enable and required
    And I will update the following information in edit task page
      | decisionSource         | Manual |
      | noOfApprovedApplicants | 3      |
      | externalCaseId         | 45535  |
      | disposition            ||
    Then I verify "Number Of Approved Applicants" field is disable and cleared out
    Then I verify "Decision Source" field is disable and cleared out
    And I click on save button on task edit page
    Then I verify "VACMS CASE ID " filed is not mandatory for the task
    And I will update the following information in edit task page
      | externalCaseId ||
      | disposition    | Auto-Denied |
    Then I verify "Number Of Approved Applicants" field is disable and cleared out
    Then I verify "Approval Reason" field is enabled
    And I click on save button on task edit page
    Then I verify "Decision Source" field is enable and required
    Then I verify "VACMS Case ID" field is enable and required
    And I will update the following information in edit task page
      | disposition | Approved |
    And I click on save button on task edit page
    Then I verify "Number Of Approved Applicants" field is enable and required
    Then I verify "Approval Reason" field is enabled
    Then I verify "Decision Source" field is enable and required
    Then I verify "VACMS Case ID" field is enable and required
    And I will update the following information in edit task page
      | noOfApprovedApplicants | 20     |
      | disposition            | Denied |
    Then I verify "Number Of Approved Applicants" field is disable and cleared out
    Then I verify "Approval Reason" field is enabled
    And I click on save button on task edit page
    Then I verify "Decision Source" field is enable and required
    Then I verify "VACMS Case ID" field is enable and required
    And I will update the following information in edit task page
      | decisionSource | VaCMS + Manual |
      | externalCaseId | 54673          |
      | disposition    | Cancelled      |
    Then I verify "Number Of Approved Applicants" field is disable and cleared out
    Then I verify "Approval Reason" field is enabled
    Then I verify "Decision Source" field is disable and cleared out
    And I click on save button on task edit page
    Then I verify "VACMS CASE ID " filed is not mandatory for the task
    And I will update the following information in edit task page
      | taskInfo                  ||
      | noOfApplicantsInHousehold | 12    |
    And I click on save button on task edit page
    And I scroll up the page
    And I verify the updated information in view sr details page
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                 | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Application SR         | Open   ||          ||            ||                || true          ||            ||          | Service TesterTwo ||             |

  @CP-43239 @CP-43239-03 @CP-44306 @CP-44306-01 @CP-47438 @CP-47439 @CP-47439-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Validate renewal processing duedate, app signature date, app received date fields enable and required functionality present on Renewal SR Create page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to "Renewal SR" service request page
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "APPLICATION RECEIVED DATE "
    Then I verify task mandatory fields error message "APPLICATION SIGNATURE DATE "
    Then I verify task mandatory fields error message "RENEWAL PROCESSING DUE DATE "
    Then I verify task mandatory fields error message "RENEWAL DATE "
    And I verify "renewal processing due date" task field is disabled
    Then I verify "Renewal Date" field is enable and required
    And I will provide following information before creating task
      | applicationReceivedDate  | +5     |
      | externalApplicationId    | random |
      | channel                  | FFM-R  |
      | applicationSignatureDate | today  |
      | renewalDate              | today  |
    Then I verify renewal processing due date is populated after 34 days application received date
    Then I verify Renewal Cutoff Date is auto-populated based on the value entered in the Renewal Date
    And I will provide following information before creating task
      | applicationReceivedDate | empty |
    Then Wait for 3 seconds
    Then I verify renewal processing due date is populated after 0 days application received date
    And I will provide following information before creating task
      | applicationReceivedDate | today |
    Then Wait for 3 seconds
    Then I verify renewal processing due date is populated after 29 days application received date
    And I will provide following information before creating task
      | applicationType | MAGI - PW |
    And I click on save button on task edit page





