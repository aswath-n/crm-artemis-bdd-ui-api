Feature: Task Fields Validation for IN-EB

  @CP-30400 @CP-30400-01 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Verification all fields and mandatory fields on Guardianship Form create task page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Guardianship Form" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify "actionTaken" multi select drop down value
      | Added Authorized Representative to Case |
      | Outbound Call - Consumer Reached        |
      | Outbound Call - Did Not Reach           |
    Then I verify "disposition" single select drop down value
      | Invalid   |
      | Processed |
    And Close the soft assertions

  @CP-30404 @CP-30404-01 @CP-25316 @CP-25316-13 @CP-34966 @CP-34966-01 @crm-regression @INEB-UI-Regression @ui-wf-ineb @kamil
  Scenario: Validate Hoosier Care Connect Do Not Call List Task CREATE Task
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HCC Do Not Call List" task page
    And I click on save button on task edit page
    And I verify task mandatory fields error message "PHONE "
    And I verify task mandatory fields error message "Reason"
    Then I verify "Other" field is disable and cleared out
    Then I verify when I saving Task with Phone field less than 10 chars getting error "PHONE must be 10 characters"
    And I will provide following information before creating task
      | reason | Other |
    And I click on save button on task edit page
    Then I verify "Other" field is enable and required
    And I will provide following information before creating task
      | assignee     | Service TesterTwo       |
      | contactPhone | 9876543210              |
      | other        | 123 $567 Tesr5565#$#535 |
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    When I expand the first row in task list
    And I verify Task Notes field is not displayed on Task List expanded view page
    And Close the soft assertions

  @CP-30459 @CP-30459-01 @CP-37688 @CP-37688-03 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Verification all fields and mandatory fields on Just Cause Request create task page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Just Cause Request" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    And I verify "ASSIGNEE BUSINESS UNIT" filed is not mandatory for the task
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify "actionTaken" multi select drop down value
      | No Action Taken                     |
      | Submitted Just Cause Request to MCE |
    And I select "Just Cause Request" option in task type drop down
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And Get the task type information of "Just Cause Request" for project "IN-EB"
    Given I initiated active business units and teams api
    When I provide taskTypeId for active business units and teams "singleVal"
    And I can run active bu and teams api
    Then I get business unit names from business units and teams response api
    Then I verify Assignee Business Unit dropdown displays only BU that are associated to the task type
    And Close the soft assertions

  @CP-30460 @CP-30460-01 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Verification all fields and mandatory fields on Just Cause Follow-up Email create task page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Just Cause Follow-up Email" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify "actionTaken" multi select drop down value
      | Sent Follow-up Email to MCE |
    And Close the soft assertions

  @CP-30462 @CP-30462-01 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Verification all fields and mandatory fields on Just Cause Decision Letter create task page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Just Cause Decision Letter" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify "actionTaken" multi select drop down value
      | Sent Approval Letter |
      | Sent Denial Letter   |
    And Close the soft assertions

  @CP-30397 @CP-30397-01 @crm-regression @INEB-UI-Regression @ui-wf-ineb @vidya
  Scenario: Validate fields present in DCS Request on Create task page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "DCS Request" task page
    And I will provide following information before creating task
      | status              | Complete |
      | eligibilityDecision | Denied   |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DENIAL REASON "
    And I will provide following information before creating task
      | denialReasonSingle  | Other  |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "OTHER "
    And I verify "actionTaken" multi select drop down value
      |Sent Denial Email|
      |Sent Processed Email|
    Then I verify "eligibilityDecision" single select drop down value
      | Approved |
      | Denied   |
    Then I verify "denialReason" single select drop down value
      |Eligibility Ending|
      |Not Eligible|
      |Other|
    Then I verify text box Date and Time field value and error message for following fields
      |Other|
    And Close the soft assertions

  @CP-30398 @CP-30398-01 @crm-regression @INEB-UI-Regression @ui-wf-ineb @vidya
  Scenario: Validate fields present in Disenrollment Create task page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Disenrollment" task page
    And I will provide following information before creating task
      | status              | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "CURRENT PLAN "
    And I verify task mandatory fields error message "DISENROLLMENT REASON "
    Then I verify task mandatory fields error message "SOURCE "
    And I verify task mandatory fields error message "DISPOSITION"
    And I will provide following information before creating task
      | disposition | Approved  |
      | source      | Other     |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "OTHER "
    And I verify task mandatory fields error message "DISENROLLMENT DATE "
    Then I verify "currentPlan" single select drop down value
      | Anthem                        |
      | CareSource                    |
      | Managed Health Services (MHS) |
      | MDWise                        |
      | No Plan Assigned              |
      | United Healthcare             |
    Then I verify "disenrollmentReason" single select drop down value
      |Death                      |
      |Disenroll from Managed Care|
      |Hospice                    |
      |PRTF                       |
      |Voluntary                  |
      |Waiver                     |
    Then I verify "applicationSource" single select drop down value
      |DCS                    |
      |FSSA                   |
      |Member                 |
      |Neurodiagnostic Center |
      |Other                  |
      |Richmond State Hospital|
      |State                  |
    Then I verify "disposition" single select drop down value
      |Approved      |
      |Denied        |
    Then I verify text box Date and Time field value and error message for following fields
      |Other|
      |Disenrollment Date|
    And Close the soft assertions

  @CP-30402 @CP-30402-01 @crm-regression @INEB-UI-Regression @ui-wf-ineb @chandrakumar
  Scenario: Validate fields present in HIP Plan Change Form create task page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HIP Plan Change Form" task page
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "CURRENT PLAN "
    And I verify task mandatory fields error message "DESIRED PLAN "
    And I verify task mandatory fields error message "REQUESTED DATE "
    And I verify task mandatory fields error message "CHANGE REASON "
    Then I verify "currentPlan" single select drop down value
      | Anthem HIP                  |
      | CareSource HIP              |
      | Managed Health Services HIP |
      | MDWise                      |
      | No Current Plan             |
    Then I verify "desiredPlan" single select drop down value
      | Anthem HIP                 |
      |	CareSource HIP             |
      |	Managed Health Services HIP|
      |	MDWise                     |
    Then I verify "planChangeReason" single select drop down value
      |Conditional	                      |
      |Conditional Open Enrollment        |
      |CP Error                           |
      |EB Error                           |
      |Eligibility Issue Open Enrollment  |
      |No Plan Assigned                   |
      |Not Given Time to Select Plan      |
      |Program Change                     |
      |Wrong MCE                          |
      |Other                              |
    Then I verify text box Date and Time field value and error message for following fields
      |Requested Date|
    And I will provide following information before creating task
      |planChangeReason| Other  |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "OTHER "
    Then I verify text box Date and Time field value and error message for following fields
      |Other|
    And Close the soft assertions

  @CP-30399 @CP-30399-01 @crm-regression @INEB-UI-Regression @ui-wf-ineb @vidya
  Scenario: Validate fields present in Error Correction Create task page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Error Correction" task page
    And I will provide following information before creating task
      | status              | Complete |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "DISPOSITION"
    Then I verify "planName" single select drop down value
      | Anthem                        |
      | CareSource                    |
      | Managed Health Services (MHS) |
      | MDWise                        |
      | United Healthcare             |
    Then I verify "disposition" single select drop down value
      |Contact Member - Did Not Reach|
      |Contact Member - Reached|
      |Error Corrected|
    Then I verify text box Date and Time field value and error message for following fields
      |Plan Start Date|
    And Close the soft assertions

  @CP-30410 @CP-30410-01 @crm-regression @INEB-UI-Regression @ui-wf-ineb @vidya
  Scenario: Validate fields present in TBI Report Create task page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "TBI Report" task page
    And I will provide following information before creating task
      | status              | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify "actionTaken" multi select drop down value
      | Disenroll |
      | Re-enroll |
    And Close the soft assertions

  @CP-30419 @CP-30419-01 @CP-30457 @CP-30457-01 @CP-32925 @CP-32925-04 @crm-regression @INEB-UI-Regression @ui-wf-ineb @vidya @ruslan
  Scenario: Validate fields present in HCC Outbound Call Create task page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HCC Outbound Call" task page
    And I will provide following information before creating task
      | status              | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DISPOSITION"
    And I will provide following information before creating task
      | disposition | Invalid |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "INVALID REASON "
    And I will provide following information before creating task
      | invalidReason |Other   |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "OTHER "
    And I verify "invalidReason" single select drop down value
      | Not an HCC Member         |
      | Other                     |
      | Plan Selection Not Needed |
    And I verify "disposition" single select drop down value
      | Invalid|
      | Needs Plan Selection|
      | Plan Selection Made|
    And I verify "actionTaken" multi select drop down value
      | Contacted Consumer - Did Not Reach/Left Voicemail |
      | Contacted Consumer - Did Not Reach/No Voicemail   |
      | Contacted Consumer - Invalid Phone Number         |
      | Contacted Consumer - Reached                      |
      | No Action Taken                                   |
      | Verified - Needs Call                             |
    Then I verify text box Date and Time field value and error message for following fields
      |Other|
      |PREFERRED CALL BACK DATE|
      |PREFERRED CALL BACK TIME|
    And Close the soft assertions

  @CP-30401 @CP-30401-01 @CP-34963 @CP-34963-01 @crm-regression @INEB-UI-Regression @ui-wf-ineb @chandrakumar @ruslan
  Scenario: Validate fields present in Healthy Indiana Tobacco Response Task in Create task page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HIP Tobacco Response" task page
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "USED TOBACCO IN THE LAST 6 MONTHS? "
    Then I verify "usedTobaccoInTheLast6Months" single select drop down value
      | No      |
      | Refused |
      | Yes     |
    And Close the soft assertions