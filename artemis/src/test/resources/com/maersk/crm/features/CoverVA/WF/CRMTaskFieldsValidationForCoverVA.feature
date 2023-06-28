Feature: Task Fields Validation for CoverVA

  @CP-19349 @CP-19349-01 @CP-10073 @CP-10073-11 @CP-20719 @CP-20719-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Verification all fields and mandatory fields on create task page in coverVA project
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Translation Request" task page
    Then I verify create task fields displayed on screen
    Then I verify task type is selected as "Translation Request"
    And I will provide following information before creating task
      | taskType ||
      | priority ||
      | status   ||
      | assignee ||
      | taskInfo | ABC |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "TASK TYPE"
    And I verify task mandatory fields error message "PRIORITY"
    And I verify task mandatory fields error message "STATUS"
    And I verify minimum lenght error message "TASK INFORMATION"
    Then I verify "taskPriority" single select drop down value
      | 1 |
      | 2 |
      | 3 |
      | 4 |
      | 5 |
    Then I verify "taskStatus" single select drop down value
      | Complete  |
      | Created   |
      | Escalated |
    And I select "Translation Request" option in task type drop down
    And I verify note component is not present in create task page
    And Close the soft assertions

  @CP-19349 @CP-19349-02 @CP-18046 @CP-18046-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Verification all fields and mandatory fields on Translation Request create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Translation Request" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "INFORMATION TYPE "
    And I verify "actionTaken" multi select drop down value
      | Escalated to Translation Group |
      | Mailed Translated Docs         |
      | None                           |
    And I verify "disposition" single select drop down value
      | Resolved    |
      | Transferred |
      | Unresolved  |
    And I verify "informationType" single select drop down value
      | Extended Pend Letter          |
      | Notice of Action (NOA)        |
      | Other                         |
      | Renewal Packet                |
      | Verification Check List (VCL) |
    And I verify "preferredLanguage" single select drop down value
      | Amharic               |
      | Arabic                |
      | Bassa                 |
      | Bengali               |
      | Chinese (Traditional) |
      | English               |
      | Farsi                 |
      | French                |
      | German                |
      | Hindi                 |
      | Ibo                   |
      | Korean                |
      | Other                 |
      | Russian               |
      | Spanish               |
      | Tagalog               |
      | Urdu                  |
      | Vietnamese            |
      | Yoruba                |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID              |
      | MMIS Member ID             |
      | Date Translation Escalated |
      | Date Translation Received  |
      | Date Translation Mailed    |
    And Close the soft assertions

  @CP-19365 @CP-19365-01 @CP-18993 @CP-18993-01 @CP-22537 @CP-22537-01 @CP-34498 @CP-34498-17 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @ruslan
  Scenario: Verification all fields and mandatory fields on DMAS to Appeals Escalatio create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "DMAS to Appeals Escalation" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "DUE DATE "
    And I verify task mandatory fields error message "APPLICATION ID "
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    And I verify "actionTakenSingle" single select drop down value
      | Escalated to CoverVA (DMAS)          |
      | Request Complete (maersk)           |
      | Request in Progress (maersk)        |
      | Returning - Sent in Error (maersk)  |
      | Unable to Complete Request (maersk) |
    Then I verify "contactReason" single select drop down value
      | Late Summary        |
      | Late/Missed Hearing |
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
    And Close the soft assertions

  @CP-19433 @CP-19433-01 @CP-18025 @CP-18025-01 @CP-18024 @CP-18024-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Verification all fields and mandatory fields on HPE Final Decision create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "HPE Final Decision" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "OUTCOME "
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify "actionTaken" multi select drop down value
      | Corrected Exception |
      | Escalated to DMAS   |
      | Other               |
      | Sent NOA            |
      | Uploaded to DMIS    |
    And I verify "outcome" single select drop down value
      | Final Approval   |
      | Final Denial     |
      | Manual           |
      | Pending Research |
    And I verify "contactReason" single select drop down value
      | Eligibility Status |
      | Enrollment Status  |
      | Other              |
    Then I verify text box Date and Time field value and error message for following fields
      | MMIS Member ID |
    And I select "HPE Interim Decision" option in task type drop down
    And I click on save button on task edit page
    And I verify task mandatory fields error message "OUTCOME "
    And I verify "outcome" single select drop down value
      | Approved |
      | Denied   |
    And Close the soft assertions

  @CP-19363 @CP-19363-01 @CP-22537 @CP-39041-02 @CP-22537-01 @CP-21023 @CP-21023-01 @CP-21021 @CP-21021-02 @CP-42875 @CP-42875-01 @CP-47703 @CP-47703-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @priyal
  Scenario: Verification all fields and mandatory fields on Renewal SR create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Renewal SR" service request page
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "APPLICATION ID "
    Then I verify task mandatory fields error message "APPLICATION TYPE "
    Then I verify task mandatory fields error message "CHANNEL "
    And I scroll up the page
    And I will provide following information before creating task
      | applicationType     | Ex Parte Renewal - CVIU |
      | missingInfoRequired | Yes                     |
    And I click on save button on task edit page
    And I verify "MI RECEIVED DATE" field is optional
    Then I verify due date field in create task page for 30 "Calendar Day"
    Then I verify task mandatory fields error message "FACILITY TYPE "
    Then I verify task mandatory fields error message "FACILITY NAME "
    Then I verify task mandatory fields error message "APPLICATION RECEIVED DATE "
    Then I verify task mandatory fields error message "APPLICATION SIGNATURE DATE "
    Then I verify task mandatory fields error message "VCL SENT DATE "
    Then I verify task mandatory fields error message "VCL DUE DATE "
    And I will provide following information before creating task
      | applicationType     | MAGI - CPU |
    And I click on save button on task edit page
    Then I verify due date field in create task page for 30 "Calendar Day"
    Then I verify task mandatory fields error message "APPLICATION RECEIVED DATE "
    Then I verify task mandatory fields error message "APPLICATION SIGNATURE DATE "
    Then I verify task mandatory fields error message "VCL SENT DATE "
    Then I verify task mandatory fields error message "VCL DUE DATE "
    And I will provide following information before creating task
      | applicationType     | MAGI - PW |
    And I click on save button on task edit page
    Then I verify due date field in create task page for 30 "Calendar Day"
    Then I verify task mandatory fields error message "APPLICATION RECEIVED DATE "
    Then I verify task mandatory fields error message "APPLICATION SIGNATURE DATE "
    Then I verify task mandatory fields error message "VCL SENT DATE "
    Then I verify task mandatory fields error message "VCL DUE DATE "
    And I will provide following information before creating task
      | applicationType     | Renewal Application CVIU |
    And I click on save button on task edit page
    Then I verify due date field in create task page for 30 "Calendar Day"
    Then I verify task mandatory fields error message "FACILITY TYPE "
    Then I verify task mandatory fields error message "FACILITY NAME "
    Then I verify task mandatory fields error message "APPLICATION RECEIVED DATE "
    Then I verify task mandatory fields error message "APPLICATION SIGNATURE DATE "
    Then I verify task mandatory fields error message "VCL SENT DATE "
    Then I verify task mandatory fields error message "VCL DUE DATE "
    And I verify "channel" single select drop down value
      | CommonHelp |
      | FFM-D      |
      | FFM-R      |
      | FFM-R&D    |
      | LDSS       |
      | Paper      |
      | RDE        |
    And I verify "applicationType" single select drop down value
      | Ex Parte Renewal - CVIU  |
      | MAGI - CPU               |
      | MAGI - PW                |
      | Renewal Application CPU  |
      | Renewal Application CVIU |
    Then I verify text box Date and Time field value and error message for following fields
      | Application Id |
    Then I scroll up the page
    And Wait for 5 seconds
    And I select "Application SR" option in task type drop down
    And I click on save button on task edit page
    And I verify task mandatory fields error message "APPLICATION TYPE "
    And I verify "applicationType" single select drop down value
      | Emergency Medicaid Services Application - CVIU |
      | Expedited Application - CVIU                   |
      | MAGI - CPU                                     |
      | MAGI - PW                                      |
      | MAGI Standard Application - CVIU               |
      | Pre-Release Application - CVIU                 |
      | Re-Entry Application - CVIU                    |
    Then I verify text box Date and Time field value and error message for following fields
      | Application Id |
    And Close the soft assertions

  @CP-19367 @CP-19367-01 @CP-18028 @CP-18028-01 @CP-18029 @CP-18029-01 @CP-22537 @CP-22537-01 @CP-20049 @CP-20049-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Verification all fields and mandatory fields on Inbound Document and Verification Document create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Inbound Document" task page
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify task mandatory fields error message "DOCUMENT TYPE "
    And I verify task mandatory fields error message "PROGRAM "
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
    And I verify "disposition" single select drop down value
      | Resolved    |
      | Transferred |
      | Unresolved  |
    And I verify "inboundCorrespondenceType" multi select drop down value
      | 1095-B Inquiry                           |
      | Call Center - Medicaid/FAMIS Application |
      | Communication Form - Other               |
      | Complaint                                |
      | CPU Verification Document                |
      | CVIU - Standard Application              |
      | CVIU Communication Form                  |
      | CVIU Verification Document               |
      | DSS Application/Renewal                  |
      | DSS Document - Other                     |
      | DSS Verification Document                |
      | General Inquiry                          |
      | LDSS Communication Form                  |
      | Other                                    |
      | Paper Application                        |
      | Returned Mail                            |
    And I verify "program" multi select drop down value
      | ABD-LTC    |
      | EAP        |
      | FAMIS      |
      | FAMIS MOMS |
      | Medicaid   |
      | SNAP/TANF  |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID  |
      | MMIS Member ID |
      | Application Id |
    And I select "Verification Document" option in task type drop down
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "DOCUMENT TYPE "
    And I verify "actionTaken" multi select drop down value
      | Denied - Insufficient Verification Provided |
      | Escalated to Supervisor                     |
      | Obtained - Electronic Sources               |
      | Obtained - Inbound Verif Docs               |
      | Obtained - Outbound Call                    |
      | Outbound Call - Did Not Reach               |
      | Pending Additiontal MI                      |
      | Updated SR Details                          |
    And I verify "disposition" single select drop down value
      | Resolved    |
      | Transferred |
      | Unresolved  |
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
    And Close the soft assertions

  @CP-19358 @CP-19358-01 @CP-18030 @CP-18030-01 @CP-19566 @CP-19566-01 @CP-18879 @CP-18879-03 @CP-22537 @CP-22537-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @elvin @priyal
  Scenario: Verification all fields and mandatory fields on create task page LDSS Communication Form task type 2
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "LDSS Communication Form" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "LOCALITY "
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "REQUEST TYPE "
#    And I verify task mandatory fields error message "CASE WORKER FIRST NAME "
#    And I verify task mandatory fields error message "CASE WORKER LAST NAME "
    And I verify "actionTaken" multi select drop down value
      | Emailed Requested Docs   |
      | Enrollment Corrected     |
      | Escalated to Supervisor  |
      | Generated Manual OB Corr |
      | Mailed Requested Docs    |
      | None                     |
      | Submitted Change         |
      | Transferred to CPU       |
      | Updated Application      |
      | Updated Case             |
    And I verify "issueType" single select drop down value
      | App or Case assigned to LDSS while pending (CPU Error)    |
      | App Sent to Wrong LDSS (CPU Error)                        |
      | Application/Case Incomplete (CPU Error)                   |
      | Completed Case not Assigned to LDSS (CPU Error)           |
      | Determination Incorrect (CPU Error)                       |
      | Forms not uploaded to DMIS (CPU Error)                    |
      | LDSS Comm Form Error (CPU Error)                          |
      | MMIS Enrollment Incomplete (CPU Error)                    |
      | MMIS Enrollment Incorrect (CPU Error)                     |
      | Pending case/app not assigned to the locality (CPU Error) |
      | Review Date not updated in VaCMS (CPU Error)              |
      | VaCMS Error by CV (CPU Error)                             |
    And I verify "disposition" single select drop down value
      | Resolved    |
      | Transferred |
      | Unresolved  |
    And I verify "requestType" single select drop down value
      | Report case issues                                              |
      | Request to Assign Pending App or Case to LDSS                   |
      | Request to Assign Pending App or Case to LDSS due to a SNAP app |
      | Update Information on Case or Other info                        |
    And I verify "locality" single select drop down value
      | Accomack         |
      | Albemarle        |
      | Alexandria       |
      | Alleghany        |
      | Amelia           |
      | Amherst          |
      | Appomattox       |
      | Arlington        |
      | Augusta          |
      | Bath             |
      | Bedford County   |
      | Bland            |
      | Botetourt        |
      | Bristol          |
      | Brunswick        |
      | Buchanan         |
      | Buckingham       |
      | Buena Vista      |
      | Campbell         |
      | Caroline         |
      | Carroll          |
      | Charles City     |
      | Charlotte        |
      | Charlottesville  |
      | Chesapeake       |
      | Chesterfield     |
      | Clarke           |
      | Clifton Forge    |
      | Colonial Heights |
      | CoverVA          |
      | Covington        |
      | Craig            |
      | Culpeper         |
      | Cumberland       |
      | Danville         |
      | Dickenson        |
      | Dinwiddie        |
      | Emporia          |
      | Essex            |
      | Fairfax City     |
      | Fairfax County   |
      | Falls Church     |
      | Fauquier         |
      | Floyd            |
      | Fluvanna         |
      | Franklin City    |
      | Franklin County  |
      | Frederick        |
      | Fredericksburg   |
      | Galax            |
      | Giles            |
      | Gloucester       |
      | Goochland        |
      | Grayson          |
      | Greene           |
      | Greensville      |
      | Halifax          |
      | Hampton          |
      | Hanover          |
      | Harrisonburg     |
      | Henrico          |
      | Henry            |
      | Highland         |
      | Hopewell         |
      | Isle of Wright   |
      | James City Co.   |
      | King & Queen     |
      | King George      |
      | King William     |
      | Lancaster        |
      | Lee              |
      | Lexington        |
      | Loudoun          |
      | Louisa           |
      | Lunenburg        |
      | Lynchburg        |
      | Madison          |
      | Manassas City    |
      | Manassas Park    |
      | Martinsville     |
      | Mathews          |
      | Mecklenburg      |
      | Middlesex        |
      | Montgomery       |
      | Nelson           |
      | New Kent         |
      | Newport News     |
      | Norfolk          |
      | Northampton      |
      | Northumberland   |
      | Norton           |
      | Nottoway         |
      | Orange           |
      | Page             |
      | Patrick          |
      | Petersburg       |
      | Pittsylvania     |
      | Poquoson         |
      | Portsmouth       |
      | Powhatan         |
      | Prince Edward    |
      | Prince George    |
      | Prince William   |
      | Pulaski          |
      | Radford          |
      | Rappahannock     |
      | Richmond City    |
      | Richmond County  |
      | Roanoke          |
      | Roanoke County   |
      | Rockbridge       |
      | Rockingham       |
      | Russell          |
      | Salem            |
      | Scott            |
      | Shenandoah       |
      | Smyth            |
      | Southampton      |
      | Spotsylvania     |
      | Stafford         |
      | Staunton         |
      | Suffolk          |
      | Surry            |
      | Sussex           |
      | Tazewell         |
      | Virginia Beach   |
      | Warren           |
      | Washington       |
      | Waynesboro       |
      | Westmoreland     |
      | Williamsburg     |
      | Winchester       |
      | Wise             |
      | Wythe            |
      | York             |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID          |
      | MMIS Member ID         |
      | Application Id         |
      | Case Worker First Name |
      | Case Worker Last Name  |
    And Close the soft assertions

  #deleted since we dont manually create that task
  #@CP-19368 @CP-19368-01 @crm-regression @CoverVA-UI-Regression @elvin @ui-wf-coverVA
  Scenario: Verification all fields and mandatory fields on create task page Review Complaint task type
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Review Complaint" task page
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "COMPLAINT TYPE "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify "complaintType" single select drop down value
      | Access to Service/Providers      |
      | Application Determination        |
      | Application Processing Timeframe |
      | Billing/Claims (FFS)             |
      | Billing/Claims (MCO)             |
      | CCC Plus                         |
      | CPU Complaint                    |
      | CVCC Customer Service            |
      | LDSS                             |
      | Other                            |
      | Provider - Fraud                 |
      | TPL Removal                      |
      | Transportation (FFS)             |
      | Transportation (MCO)             |
    And I verify "disposition" single select drop down value
      | Escalated |
      | Referred  |
      | Resolved  |
      | Withdrawn |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID  |
      | MMIS Member ID |
    And Close the soft assertions

  @CP-19368 @CP-19368-01 @CP-19345 @CP-19345-01 @CP-18020 @CP-18020-01 @CP-19313 @CP-19313-01 @CP-22537 @CP-22537-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @elvin @vidya
  Scenario: Verification all fields and mandatory fields on Process Returned Mail create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Process Returned Mail" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    Then I verify task mandatory fields error message "APPLICATION ID "
    Then I verify task mandatory fields error message "DOCUMENT TYPE "
    Then I verify task mandatory fields error message "BUSINESS UNIT "
    Then I verify task mandatory fields error message "RETURNED MAIL REASON "
    And I verify task mandatory fields error message "DISPOSITION"
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
    And I verify "disposition" single select drop down value
      | Resolved    |
      | Transferred |
      | Unresolved  |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID  |
      | MMIS Member ID |
      | Application Id |
      | Received Date  |
      | Date Resent    |
    And Close the soft assertions

  @CP-19276 @CP-19276-01 @CP-19195 @CP-19195-01 @CP-22537 @CP-22537-01 @CP-38195 @CP-38195-03 @priyal @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Verification all fields and mandatory fields on Appeal Remand create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Appeal Remand" task page
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    And I verify "ASSIGNEE BUSINESS UNIT" filed is not mandatory for the task
    Then I verify task mandatory fields error message "APPLICATION ID "
    Then I verify task mandatory fields error message "RECEIVED DATE "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "REMAND REASON "
    And I verify task mandatory fields error message "REMAND DUE DATE "
    And I verify task mandatory fields error message "ELIGIBILITY DECISION "
    And I verify "remandReason" multi select drop down value
      | Case Correction - Incorrect Alien Status        |
      | Case Correction - Incorrect Document Processing |
      | Case Correction - Incorrect Income Evaluation   |
      | Case Correction - Missing VCL                   |
      | Other                                           |
      | Timeliness                                      |
    And I verify "eligibilityDecision" single select drop down value
      | Approved |
      | Denied   |
      | Pending  |
    And I verify "disposition" single select drop down value
      | Approved |
      | Denied   |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID          |
      | Application Id         |
      | Received Date          |
      | Remand Due Date        |
      | Remand Completion Date |
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And Get the task type information of "Appeal Remand" for project ""
    Given I initiated active business units and teams api
    When I provide taskTypeId for active business units and teams "singleVal"
    And I can run active bu and teams api
    Then I get business unit names from business units and teams response api
    Then I verify Assignee Business Unit dropdown displays only BU that are associated to the task type
    And Close the soft assertions

  @CP-19311 @CP-19311-01 @CP-19198 @CP-19198-01 @crm-regression @CoverVA-UI-Regression  @vidya #@ui-wf-coverVA deleted since we dont create manually this task
  Scenario: Verification all fields and mandatory fields on Fair Hearing create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Fair Hearing " task page
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "APPOINTMENT DATE"
    And I verify task mandatory fields error message "APPOINTMENT TIME"
    And I verify "disposition" single select drop down value
      | Abandoned         |
      | Admin Resolved    |
      | Forwarded to LDSS |
      | Invalidated       |
      | Pending           |
      | Remanded          |
      | Rescheduled       |
      | Upheld            |
      | Withdrawn         |
    And I verify "accessibilityNeeded" multi select drop down value
      | Hearing Impaired |
      | Interpreter      |
    Then I verify text box Date and Time field value and error message for following fields
      | Appointment Date |
      | Appointment Time |
    And Close the soft assertions

@CP-19311 @CP-19311-02 @CP-18044 @CP-18044-01 @CP-25708 @CP-25708-04 @CP-39671-04 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @ruslan
  Scenario: Verification all fields and mandatory fields on CVIU Provider Request create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "CVIU Provider Request" task page
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify "disposition" single select drop down value
      | Resolved    |
      | Transferred |
      | Unresolved  |
    And I verify "actionTaken" multi select drop down value
      | Confirm Coverage |
      | Other            |
    And I verify "contactReason" single select drop down value
      | Other                   |
      | Provide Medical Records |
    And I verify "facilityType" single select drop down value
      | Department of Corrections      |
      | Department of Juvenile Justice |
      | Local Jail                     |
      | Regional Jail                  |
    And I verify "facilityName" single select drop down value
      | Accomack County Jail                             |
      | Albemarle/Charlottesville Regional Jail          |
      | Albermarle County Jail                           |
      | Alexandria City Jail                             |
      | Alexandria Detention Center                      |
      | Alleghany County Jail                            |
      | Alleghany/Covington Regional Jail                |
      | Amelia County Jail                               |
      | Amherst County Jail                              |
      | Appalachian Detention Center                     |
      | Appomattox County Jail                           |
      | Arlington County Jail                            |
      | Augusta Correctional Center                      |
      | Augusta County Jail                              |
      | Baskerville Correctional Center                  |
      | Beaumont Correctional Center                     |
      | Bedford County Jail                              |
      | Bland Correctional Center                        |
      | Blue Ridge Regional Jail (BRRJ) - Amherst        |
      | Blue Ridge Regional Jail (BRRJ) - Bedford        |
      | Blue Ridge Regional Jail (BRRJ) - Campbell       |
      | Blue Ridge Regional Jail (BRRJ) - Halifax        |
      | Blue Ridge Regional Jail (BRRJ) - Lynchburg      |
      | Botetourt County Jail                            |
      | Bristol City Jail                                |
      | Brunswick County Jail                            |
      | Brunswick Work Center                            |
      | Buckingham Correctional Center                   |
      | Campbell County Detention Center                 |
      | Caroline Correctional Unit                       |
      | Caroline Correctional Unit #2                    |
      | Caroline Detention Facility                      |
      | Carroll County Jail                              |
      | Central VA Correctional Unit #13                 |
      | Central Virginia Regional Jail                   |
      | Charles City County Jail                         |
      | Charlotte County Jail                            |
      | Charlottesville City Jail                        |
      | Chesapeake City Jail                             |
      | Chesterfield County Jail                         |
      | Chesterfield Women’s Diversion Center            |
      | Clarke County Jail                               |
      | Clifton Forge Sheriff’s Office                   |
      | Coffeewood Correctional Center                   |
      | Cold Springs Correctional Unit 10                |
      | Cold Springs Detention Center                    |
      | Colonial Heights Sheriff’s Office                |
      | Covington City Sheriff’s Office                  |
      | Craig County Sheriff’s Office                    |
      | Culpeper County Jail                             |
      | Cumberland County Jail                           |
      | Danville City Jail                               |
      | Deep Meadow Correctional Center                  |
      | Deerfield Correctional Center                    |
      | Department of Corrections (DOC)                  |
      | Department of Juvenile Justice                   |
      | Dickinson County Jail                            |
      | Dillwyn Correctional Center                      |
      | Dinwiddie County Jail                            |
      | Eastern Shore Regional Jail (Northampton County) |
      | Emporia City Jail                                |
      | Essex County VA Jail                             |
      | Fairfax County Jail                              |
      | Falls Church City Jail                           |
      | Fauquier County Sheriff's Office                 |
      | Floyd County Jail                                |
      | Fluvanna Correctional Center for Women           |
      | Fluvanna County Jail                             |
      | Franklin City Police Station                     |
      | Franklin County Jail                             |
      | Frederick County Jail                            |
      | Fredericksburg City Jail                         |
      | Galax City Jail                                  |
      | Gloucester County Jail                           |
      | Goochland County Jail                            |
      | Grayson County Jail                              |
      | Green Rock Correctional Center                   |
      | Greene County Jail                               |
      | Greensville Correctional Center                  |
      | Greensville County Jail                          |
      | Halifax Correctional Unit                        |
      | Halifax County Jail                              |
      | Hampton City Jail                                |
      | Hampton Roads Regional Jail                      |
      | Hanover County Jail                              |
      | Harrisonburg Diversion Center                    |
      | Haynesville Correctional Center                  |
      | Haysi Regional Jail                              |
      | Henrico County Regional Jail (East)              |
      | Henrico County Regional Jail (West)              |
      | Henry County Jail                                |
      | Highland County Jail                             |
      | Hopewell City Jail                               |
      | Indian Creek Correctional Center                 |
      | James City County Jail                           |
      | James River Correctional Center                  |
      | Keen Mountain Correctional Center                |
      | King George County Sheriff's Office              |
      | Lancaster County Sheriff's Office                |
      | Lawrenceville Correctional Center                |
      | Lee County Jail                                  |
      | Loudoun County Jail                              |
      | Louisa County Jail                               |
      | Lunenburg Correctional Center                    |
      | Lynchburg City Jail                              |
      | Madison County Sheriff's Office                  |
      | Manassas Park City Police Station                |
      | Marion Correctional Center                       |
      | Marion Correctional Treatment Center             |
      | Martinsville City Sheriff's Office               |
      | Mathews County Jail                              |
      | Meherrin River Regional Jail (Alberta)           |
      | Meherrin River Regional Jail (Boydton)           |
      | Middle Peninsula Regional Jail                   |
      | Middle River Regional Jail                       |
      | Montgomery County Jail                           |
      | Nelson County Sheriff's Office                   |
      | New Kent County Jail                             |
      | New River Valley Regional Jail                   |
      | Newport News City Jail                           |
      | Norfolk City Jail                                |
      | Northampton County Jail                          |
      | Northern Neck Regional Jail                      |
      | Northumberland County Jail                       |
      | Northwestern Regional Adult Detention Center     |
      | Nottoway Correctional Center                     |
      | Nottoway Work Center                             |
      | Orange County Jail                               |
      | Page County Sheriff's Office                     |
      | Pamunkey Regional Jail                           |
      | Patrick County Sheriff's Office                  |
      | Patrick Henry Correctional Unit                  |
      | Peumansend Creek Regional Jail                   |
      | Piedmont Regional Jail                           |
      | Pittsylvania County Sheriff's Office             |
      | Pocahontas State Correctional Center             |
      | Poquoson City Sheriff's Office                   |
      | Portsmouth City Jail                             |
      | Powhatan Correctional Center                     |
      | Prince William/Manassas Regional ADC             |
      | Radford City Police Department                   |
      | Rappahannock - Shanandoah - Warrenton Regional Jail|
      | Rappahannock Regional Jail                       |
      | Red Onion State Prison                           |
      | Richmond City Jail                               |
      | River North Correctional Center                  |
      | Riverside Regional Jail                          |
      | Roanoke City Jail                                |
      | Roanoke County Jail                              |
      | Rockbridge Regional Jail                         |
      | Rockingham/Harrisonburg Regional Jail            |
      | Rustburg Correctional Unit                       |
      | Salem Sheriff’s Office                           |
      | Scott County Jail                                |
      | Shenandoah County Jail                           |
      | Southampton County Jail                          |
      | Southside Regional Jail                          |
      | Southwest Virginia Regional Jail - Abington      |
      | Southwest Virginia Regional Jail - Duffield      |
      | Southwest Virginia Regional Jail - Haysi         |
      | Southwest Virginia Regional Jail - Tazewell      |
      | St. Brides Correctional Center                   |
      | Stafford Diversion Center                        |
      | State Farm Correctional Center                   |
      | Staunton City Jail                               |
      | Surry County Jail                                |
      | Sussex County Jail                               |
      | Sussex I State Prison                            |
      | Sussex II State Prison                           |
      | Tazewell County Jail                             |
      | Virginia Beach Correctional Center               |
      | Virginia Correctional Center for Women           |
      | Virginia Peninsula Regional Jail                 |
      | Wallens Ridge State Prison                       |
      | Warren County Jail                               |
      | Warren County RSW Regional Jail                  |
      | Waynesboro City Jail                             |
      | Western Tidewater Regional Jail                  |
      | Western Virginia Regional Jail                  |
      | Westmoreland County Jail                         |
      | Williamsburg City Jail                           |
      | Wise Correctional Unit                           |
      | Wise County Jail                                 |
      | York-Poquoson Sheriff's Office                   |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID       |
      | MMIS Member ID      |
      | Provider First Name |
      | Provider Last Name  |
      | Provider Phone      |
      | Provider Email      |
    And Close the soft assertions

 @CP-19311 @CP-19311-03 @CP-18031 @CP-18031-01 @CP-22537 @CP-22537-01 @CP-25708 @CP-25708-02 @CP-43441-01 @CP-39671-05 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @ruslan
  Scenario: Verification all fields and mandatory fields on CVIU Communication Form create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "CVIU LDSS Communication Form" task page
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "LOCALITY "
    And I verify "disposition" single select drop down value
      | Resolved    |
      | Transferred |
      | Unresolved  |
    And I verify "actionTaken" multi select drop down value
      | Escalated to DMAS               |
      | Assigned App/Case to LDSS       |
      | Emailed Requested Docs          |
      | Escalated to Supervisor         |
      | Generated Manual OB Corr        |
      | Mailed Requested Docs           |
      | None                            |
      | Outbound Call - Did Not Reach   |
      | Outbound Call - Reached         |
      | Provided Clarification/Response |
      | Updated Application             |
      | Updated Case                    |
    And I verify "contactReason" single select drop down value
      | Re-entry                      |
      | Coverage Correction           |
      | Facility Change               |
      | Pre-Release                   |
      | Reporting Newborn             |
      | Reporting Pregnancy Requested |
      | Case Transfer                 |
      | Case Change                   |
      | Split Case                    |
    And I verify "facilityType" single select drop down value
      | Department of Corrections      |
      | Department of Juvenile Justice |
      | Local Jail                     |
      | Regional Jail                  |
    And I verify "facilityName" single select drop down value
      | Accomack County Jail                             |
      | Albemarle/Charlottesville Regional Jail          |
      | Albermarle County Jail                           |
      | Alexandria City Jail                             |
      | Alexandria Detention Center                      |
      | Alleghany County Jail                            |
      | Alleghany/Covington Regional Jail                |
      | Amelia County Jail                               |
      | Amherst County Jail                              |
      | Appalachian Detention Center                     |
      | Appomattox County Jail                           |
      | Arlington County Jail                            |
      | Augusta Correctional Center                      |
      | Augusta County Jail                              |
      | Baskerville Correctional Center                  |
      | Beaumont Correctional Center                     |
      | Bedford County Jail                              |
      | Bland Correctional Center                        |
      | Blue Ridge Regional Jail (BRRJ) - Amherst        |
      | Blue Ridge Regional Jail (BRRJ) - Bedford        |
      | Blue Ridge Regional Jail (BRRJ) - Campbell       |
      | Blue Ridge Regional Jail (BRRJ) - Halifax        |
      | Blue Ridge Regional Jail (BRRJ) - Lynchburg      |
      | Botetourt County Jail                            |
      | Bristol City Jail                                |
      | Brunswick County Jail                            |
      | Brunswick Work Center                            |
      | Buckingham Correctional Center                   |
      | Campbell County Detention Center                 |
      | Caroline Correctional Unit                       |
      | Caroline Correctional Unit #2                    |
      | Caroline Detention Facility                      |
      | Carroll County Jail                              |
      | Central VA Correctional Unit #13                 |
      | Central Virginia Regional Jail                   |
      | Charles City County Jail                         |
      | Charlotte County Jail                            |
      | Charlottesville City Jail                        |
      | Chesapeake City Jail                             |
      | Chesterfield County Jail                         |
      | Chesterfield Women’s Diversion Center            |
      | Clarke County Jail                               |
      | Clifton Forge Sheriff’s Office                   |
      | Coffeewood Correctional Center                   |
      | Cold Springs Correctional Unit 10                |
      | Cold Springs Detention Center                    |
      | Colonial Heights Sheriff’s Office                |
      | Covington City Sheriff’s Office                  |
      | Craig County Sheriff’s Office                    |
      | Culpeper County Jail                             |
      | Cumberland County Jail                           |
      | Danville City Jail                               |
      | Deep Meadow Correctional Center                  |
      | Deerfield Correctional Center                    |
      | Department of Corrections (DOC)                  |
      | Department of Juvenile Justice                   |
      | Dickinson County Jail                            |
      | Dillwyn Correctional Center                      |
      | Dinwiddie County Jail                            |
      | Eastern Shore Regional Jail (Northampton County) |
      | Emporia City Jail                                |
      | Essex County VA Jail                             |
      | Fairfax County Jail                              |
      | Falls Church City Jail                           |
      | Fauquier County Sheriff's Office                 |
      | Floyd County Jail                                |
      | Fluvanna Correctional Center for Women           |
      | Fluvanna County Jail                             |
      | Franklin City Police Station                     |
      | Franklin County Jail                             |
      | Frederick County Jail                            |
      | Fredericksburg City Jail                         |
      | Galax City Jail                                  |
      | Gloucester County Jail                           |
      | Goochland County Jail                            |
      | Grayson County Jail                              |
      | Green Rock Correctional Center                   |
      | Greene County Jail                               |
      | Greensville Correctional Center                  |
      | Greensville County Jail                          |
      | Halifax Correctional Unit                        |
      | Halifax County Jail                              |
      | Hampton City Jail                                |
      | Hampton Roads Regional Jail                      |
      | Hanover County Jail                              |
      | Harrisonburg Diversion Center                    |
      | Haynesville Correctional Center                  |
      | Haysi Regional Jail                              |
      | Henrico County Regional Jail (East)              |
      | Henrico County Regional Jail (West)              |
      | Henry County Jail                                |
      | Highland County Jail                             |
      | Hopewell City Jail                               |
      | Indian Creek Correctional Center                 |
      | James City County Jail                           |
      | James River Correctional Center                  |
      | Keen Mountain Correctional Center                |
      | King George County Sheriff's Office              |
      | Lancaster County Sheriff's Office                |
      | Lawrenceville Correctional Center                |
      | Lee County Jail                                  |
      | Loudoun County Jail                              |
      | Louisa County Jail                               |
      | Lunenburg Correctional Center                    |
      | Lynchburg City Jail                              |
      | Madison County Sheriff's Office                  |
      | Manassas Park City Police Station                |
      | Marion Correctional Center                       |
      | Marion Correctional Treatment Center             |
      | Martinsville City Sheriff's Office               |
      | Mathews County Jail                              |
      | Meherrin River Regional Jail (Alberta)           |
      | Meherrin River Regional Jail (Boydton)           |
      | Middle Peninsula Regional Jail                   |
      | Middle River Regional Jail                       |
      | Montgomery County Jail                           |
      | Nelson County Sheriff's Office                   |
      | New Kent County Jail                             |
      | New River Valley Regional Jail                   |
      | Newport News City Jail                           |
      | Norfolk City Jail                                |
      | Northampton County Jail                          |
      | Northern Neck Regional Jail                      |
      | Northumberland County Jail                       |
      | Northwestern Regional Adult Detention Center     |
      | Nottoway Correctional Center                     |
      | Nottoway Work Center                             |
      | Orange County Jail                               |
      | Page County Sheriff's Office                     |
      | Pamunkey Regional Jail                           |
      | Patrick County Sheriff's Office                  |
      | Patrick Henry Correctional Unit                  |
      | Peumansend Creek Regional Jail                   |
      | Piedmont Regional Jail                           |
      | Pittsylvania County Sheriff's Office             |
      | Pocahontas State Correctional Center             |
      | Poquoson City Sheriff's Office                   |
      | Portsmouth City Jail                             |
      | Powhatan Correctional Center                     |
      | Prince William/Manassas Regional ADC             |
      | Radford City Police Department                   |
      | Rappahannock - Shanandoah - Warrenton Regional Jail|
      | Rappahannock Regional Jail                       |
      | Red Onion State Prison                           |
      | Richmond City Jail                               |
      | River North Correctional Center                  |
      | Riverside Regional Jail                          |
      | Roanoke City Jail                                |
      | Roanoke County Jail                              |
      | Rockbridge Regional Jail                         |
      | Rockingham/Harrisonburg Regional Jail            |
      | Rustburg Correctional Unit                       |
      | Salem Sheriff’s Office                           |
      | Scott County Jail                                |
      | Shenandoah County Jail                           |
      | Southampton County Jail                          |
      | Southside Regional Jail                          |
      | Southwest Virginia Regional Jail - Abington      |
      | Southwest Virginia Regional Jail - Duffield      |
      | Southwest Virginia Regional Jail - Haysi         |
      | Southwest Virginia Regional Jail - Tazewell      |
      | St. Brides Correctional Center                   |
      | Stafford Diversion Center                        |
      | State Farm Correctional Center                   |
      | Staunton City Jail                               |
      | Surry County Jail                                |
      | Sussex County Jail                               |
      | Sussex I State Prison                            |
      | Sussex II State Prison                           |
      | Tazewell County Jail                             |
      | Virginia Beach Correctional Center               |
      | Virginia Correctional Center for Women           |
      | Virginia Peninsula Regional Jail                 |
      | Wallens Ridge State Prison                       |
      | Warren County Jail                               |
      | Warren County RSW Regional Jail                  |
      | Waynesboro City Jail                             |
      | Western Tidewater Regional Jail                  |
      | Western Virginia Regional Jail                   |
      | Westmoreland County Jail                         |
      | Williamsburg City Jail                           |
      | Wise Correctional Unit                           |
      | Wise County Jail                                 |
      | York-Poquoson Sheriff's Office                   |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID          |
      | MMIS Member ID         |
      | Application Id         |
      | Case Worker First Name |
      | Case Worker Last Name  |
    And Close the soft assertions

  @CP-18777 @CP-18777-01 @CP-18776 @CP-18776-01 @CP-22537 @CP-22537-01 @CP-34498 @CP-34498-07 @CP-38195 @CP-38195-01-05 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @ruslan
  Scenario: Verification all fields and mandatory fields on Appeals to DMAS Escalation create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Appeals to DMAS Escalation" task page
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    Then I verify task mandatory fields error message "APPLICATION ID "
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "DUE DATE "
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    And I verify "actionTakenSingle" single select drop down value
      | Escalated to DMAS (maersk)       |
      | Request Complete (DMAS)           |
      | Request in Progress (DMAS)        |
      | Returning - Sent in Error (DMAS)  |
      | Unable to Complete Request (DMAS) |
    Then I verify "contactReason" single select drop down value
      | Delayed Fair Hearing |
      | DMAS Appeal          |
      | Other                |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID        |
      | MMIS Member ID       |
      | Application Id       |
      | CP contact record Id |
      | CP SR Id             |
      | CP Task Id           |
      | Due Date             |
    And Close the soft assertions

    # refactoring on 19-08-2021 by priyal
  @CP-21003 @CP-21003-01 @CP-34498 @CP-34498-02 @CP-38195 @CP-38195-01-01 @CP-47535 @CP-47535-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal @ruslan
  Scenario: Verification all fields and mandatory fields on General Escalation create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "General Escalation" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    Then I verify "businessUnitAssignedTo" field does not contains value on "edit" page
      | Appeals     |
      | PHC Renewal |
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "DUE DATE "
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    And I verify "actionTakenSingle" single select drop down value
      | Escalated to CPU   |
      | Escalated to DMAS  |
      | None               |
      | Pending            |
      | Sent to LDSS       |
      | Sent to Supervisor |
    Then I verify "contactReason" single select drop down value
      | Address Change |
      | Emergency      |
      | Non-Emergency  |
      | Other          |
      | PW Emergency   |
    Then I verify "disposition" single select drop down value
      | Resolved    |
      | Transferred |
      | Unresolved  |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID        |
      | Application Id       |
      | CP contact record Id |
      | CP SR Id             |
      | CP Task Id           |
      | Due Date             |
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And Get the task type information of "General Escalation" for project ""
    Given I initiated active business units and teams api
    When I provide taskTypeId for active business units and teams "singleVal"
    And I can run active bu and teams api
    Then I get business unit names from business units and teams response api
    Then I verify Assignee Business Unit dropdown displays only BU that are associated to the task type
    And Close the soft assertions