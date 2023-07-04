Feature: Validate task in view and edit page Second

  @CP-18879 @CP-18879-01 @CP-19551 @CP-19551-01 @CP-21398 @CP-21398-01 @CP-19552 @CP-19552-01 @CP-19343 @CP-19343-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @priyal
  Scenario Outline: Validate Additional template fields for process app v1 view task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I navigate to "Process App V1" task page
    And I will provide following information before creating task
      | assignee                  | Service TesterTwo            |
      | applicationType           | Expedited Application - CVIU |
      | externalApplicationId     | random                       |
      | channel                   | Paper                        |
      | myWorkSpaceDate           | today                        |
      | applicationReceivedDate   | +1                           |
      | applicationSignatureDate  | today                        |
      | noOfApplicantsInHousehold | 1                            |
      | applicationStatus         | Outbound Call                |
      | decisionSource            | VaCMS                        |
      | noOfApprovedApplicants    | 12                           |
      | missingInfoRequired       | No                           |
      | priority                  | 5                            |
      | hpe                       | true                         |
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    Then I verify task following fields are present on view page
      | APPLICATION ID               |
      | APPLICATION TYPE             |
      | MY WORKSPACE DATE            |
      | LDSS RECEIVED DATE           |
      | APPLICATION RECEIVED DATE    |
      | APPLICATION SIGNATURE DATE   |
      | APPLICATION UPDATE DATE      |
      | FACILITY NAME                |
      | FACILITY TYPE                |
      | CHANNEL                      |
      | DECISION SOURCE              |
      | # OF APPLICANTS IN HOUSEHOLD |
      | MISSING INFO REQUIRED?       |
      | APPLICATION SUB-STATUS       |
      | # OF APPROVED APPLICANTS     |
      | TRANSFER REASON              |
      | DENIAL REASON                |
      | ACTION TAKEN                 |
    Then I verify task following checkbox are present on view page
      | hpe |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | applicationReceivedDate   |       |
      | applicationSignatureDate  |       |
      | noOfApplicantsInHousehold |       |
      | applicationStatus         |       |
      | missingInfoRequired       |       |
      | decisionSource            |       |
      | noOfApprovedApplicants    |       |
      | hpe                       | false |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "# OF APPLICANTS IN HOUSEHOLD "
    Then I verify task mandatory fields error message "APPLICATION SUB-STATUS "
    Then I verify task mandatory fields error message "APPLICATION RECEIVED DATE "
    Then I verify task mandatory fields error message "APPLICATION SIGNATURE DATE "
    Then I verify task mandatory fields error message "# OF APPROVED APPLICANTS "
    Then I verify task mandatory fields error message "DECISION SOURCE "
    Then I verify task mandatory fields error message "MISSING INFO REQUIRED? "
    And I verify "reason" single select drop down value
      | Add a Person      |
      | Case Change       |
      | Non MAGI - ABD    |
      | Renewal           |
      | Requested by LDSS |
      | Re-registration   |
      | RMC Completed     |
      | SNAP/TANF         |
      | Spenddown         |
    And I verify "decisionSource" single select drop down value
      | Manual         |
      | VaCMS          |
      | VaCMS + Manual |
    And I verify "missingInfoRequired" single select drop down value
      | No  |
      | Yes |
    And I verify "applicationStatus" single select drop down value
      | Appeal                                       |
      | Approved                                     |
      | Assigned to ARC (Advanced Resolution Center) |
      | Auto-Approved (Self Direct)                  |
      | Auto-Denied                                  |
      | Coverage Correction Completed                |
      | Coverage Correction Needed                   |
      | Data Collection                              |
      | DC Ready                                     |
      | Denied                                       |
      | Documents Processed                          |
      | Documents Received                           |
      | ECPR Tool                                    |
      | Extend and Pend Letter                       |
      | File Clearance                               |
      | Inbound Call                                 |
      | Manual Enrollment Tool                       |
      | Medicare Review Completed                    |
      | Medicare Review Needed                       |
      | Outbound Call                                |
      | Pending Direction                            |
      | Pre-Hearing Conference                       |
      | Reassigned to CPU                            |
      | Research                                     |
      | Returned Mail                                |
      | Sent to LDSS in Error                        |
      | System Issue                                 |
      | Timestamp                                    |
      | VCL (Verification Checklist)                 |
    Then I verify text box Date and Time field value and error message for following fields
      | Hpe                               |
      | Application Received Date         |
      | Application Signature Date        |
      | Number Of Applicants In Household |
      | Number Of Approved Applicants     |
    And I will update the following information in edit task page
      | reasonForEdit             | Corrected Data Entry |
      | status                    | Complete             |
      | missingInfoRequired       | No                   |
      | applicationType           | MAGI - PW            |
      | externalApplicationId     | Test1234             |
      | myWorkSpaceDate           | today                |
      | applicationReceivedDate   | +1                   |
      | applicationSignatureDate  | today                |
      | noOfApplicantsInHousehold | 1                    |
      | channel                   | Paper                |
      | applicationStatus         | Data Collection      |
#| denialReason              | Auto-Denied (Self-Direct) |
      | decisionSource            | Manual               |
      | noOfApprovedApplicants    | 12                   |
      | hpe                       | false                |
      | actionTakenSingle         | Sent NOA             |
      | disposition               | Auto-Denied          |
    And I click on save button on task edit page
    Then I verify the updated information in view task details page
    And Close the soft assertions
    Examples:
      | taskId | taskType       | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee          | createdBy | createdOn | contactReason |
      |        | Process App V1 |        | Created |            | 5        |         |            |        |                |            | true          |            |            |        | Service TesterTwo |           | today     |               |

  @CP-18079 @CP-18079-01 @CP-21420 @CP-21420-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Validate Additional template fields for Missing info task in view and edit task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I verify task following fields are present on view page
      | ACTION TAKEN             |
      | DENIAL REASON            |
      | DUE DATE                 |
      | APPLICATION ID           |
      | DOCUMENT TYPE            |
      | # OF APPROVED APPLICANTS |
      | RECEIVED DATE            |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Complete |
      | receivedDate          |          |
      | deselectActionTaken   |          |
      | documentDueDate       |          |
      | externalApplicationId |          |
      | InbDocType            |          |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    Then I verify task mandatory fields error message "DISPOSITION"
    Then I verify task mandatory fields error message "DUE DATE "
    Then I verify task mandatory fields error message "APPLICATION ID "
    Then I verify task mandatory fields error message "RECEIVED DATE "
    Then I verify task mandatory fields error message "DOCUMENT TYPE "
    Then I verify "disposition" single select drop down value
      | Approved |
      | Denied   |
      | Referred |
    And I verify "actionTaken" multi select drop down value
      | Denied - Failure to Provide   |
      | Escalated to Supervisor       |
      | Obtained - Electronic Sources |
      | Obtained - Inbound Verif Docs |
      | Obtained - Outbound Call      |
      | Outbound Call - Did Not Reach |
      | Pending                       |
    And I verify "denialReason" multi select drop down value
      | Auto-Denied (Self-Direct)                                        |
      | Does not meet a full benefit alien status                        |
      | Does not meet a MAGI covered group/opted out of plan 1st         |
      | Duplicate Application                                            |
      | Existing Coverage                                                |
      | Failed to Provide Information necessary to determine eligibility |
      | Invalid Signature                                                |
      | Over Income                                                      |
      | Voluntarily Withdrew                                             |
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
      | Due Date                      |
      | Application Id                |
      | Number Of Approved Applicants |
      | Received Date                 |
    And Close the soft assertions
    Examples:
      | taskId | taskType            | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      |        | Missing Information |        | Created |            | 3        |         |            |        |                |            | false         |            |            |        |          |           |           |               |

  @CP-18654 @CP-18654-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Validate Additional template fields for Review Complaint task in view and edit task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I verify task following fields are present on view page
      | VACMS CASE ID  |
      | MMIS MEMBER ID |
      | COMPLAINT TYPE |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete |
      | complaintType |          |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    Then I verify task mandatory fields error message "COMPLAINT TYPE "
    And I scroll up the page
    Then I verify "disposition" single select drop down value
      | Escalated |
      | Referred  |
      | Resolved  |
      | Withdrawn |
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
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID  |
      | MMIS Member ID |
    And Close the soft assertions
    Examples:
      | taskId | taskType         | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      |        | Review Complaint |        | Created |            | 3        |         |            |        |                |            | false         |            |            |        |          |           |           |               |

  @CP-18657 @CP-18657-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Validate Additional template fields for Complaint Escalation task in view and edit task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I verify task following fields are present on view page
      | DISPOSITION |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status      | Complete |
      | disposition |          |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    Then I verify "disposition" single select drop down value
      | Referred  |
      | Resolved  |
      | Withdrawn |
    And Close the soft assertions
    Examples:
      | taskId | taskType             | srType | status   | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      |        | Complaint Escalation |        | Complete |            |          |         |            |        |                |            | false         |            |            |        |          |           |           |               |

  @CP-19197 @CP-19197-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Validate Additional template fields for Review Appeal task in view and edit task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I verify task following fields are present on view page
      | DMAS RECEIVED DATE             |
      | CoverVA RECEIVED DATE          |
      | APPLICATION ID                 |
      | VACMS CASE ID                  |
      | CASE STATUS                    |
      | APPEAL REASON                  |
      | BUSINESS UNIT                  |
      | APPEALS CASE SUMMARY DUE DATE  |
      | APPEALS CASE SUMMARY HYPERLINK |
      | APPEAL CASE SUMMARY STATUS     |
      | REVIEW OUTCOME                 |
      | ACTION TAKEN                   |
      | DISPOSITION                    |
    Then I verify task following checkbox are present on view page
      | expedited |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | disposition                 |  |
      | dmasReceivedDate            |  |
      | coverVAReceivedDate         |  |
      | externalApplicationId       |  |
      | externalCaseId              |  |
      | caseStatus                  |  |
      | businessUnit                |  |
      | appealCaseSummaryDueDate    |  |
      | appealsCaseSummaryHyperlink |  |
      | appealCaseSummaryStatus     |  |
      | reviewOutcome               |  |
      | deselectActionTaken         |  |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "DMAS RECEIVED DATE "
    And I verify task mandatory fields error message "CoverVA RECEIVED DATE "
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify task mandatory fields error message "CASE STATUS "
    And I verify "APPEAL REASON" field is required
    And I verify task mandatory fields error message "BUSINESS UNIT "
    And I verify task mandatory fields error message "APPEALS CASE SUMMARY DUE DATE "
    And I verify task mandatory fields error message "APPEAL CASE SUMMARY STATUS "
    And I verify task mandatory fields error message "REVIEW OUTCOME "
    Then I verify "disposition" single select drop down value
      | Fair Hearing         |
      | Resolved w/o Hearing |
      | Returned to DMAS     |
      | Withdrawn            |
    Then I verify "caseStatus" single select drop down value
      | Existing Coverage   |
      | New Application     |
      | Renewal Application |
    And I verify "businessUnit" single select drop down value
      | CPU  |
      | CVIU |
      | FFM  |
    And I verify "appealCaseSummaryStatus" single select drop down value
      | Approved by CoverVA            |
      | Approved by DMAS               |
      | Corrections In Progress        |
      | CoverVA Review in Progress     |
      | DMAS Review In Progress        |
      | In-Progress                    |
      | Requires Correction            |
      | Submitted for Approval         |
      | Submitted for CoverVA Approval |
    And I verify "reviewOutcome" multi select drop down value
      | Error(s) Found      |
      | No Error(s) Found   |
      | No Processing Delay |
      | Processing Delay    |
    And I verify "actionTaken" multi select drop down value
      | Admin Resolve                 |
      | Appeal Case Summary Sent      |
      | Processed Application/Renewal |
      | Redetermined Case             |
      | Returned to DMAS              |
    And I verify "appealReason" multi select drop down value
      | Coverage Period                                      |
      | Declared Not Disabled                                |
      | Denied - Does not meet MAGI                          |
      | Denied - Full Medicaid                               |
      | Denied - Immigration Status                          |
      | Denied - Incomplete                                  |
      | Denied - Institutional Status                        |
      | Denied - Other Insurance                             |
      | Denied - Over Resources                              |
      | Denied - Over Income                                 |
      | Denied - Renewal Incomplete                          |
      | Denied - Residency                                   |
      | Duplicate Coverage                                   |
      | Other                                                |
      | Refused to Accept Application                        |
      | Spenddown                                            |
      | Timely Processing                                    |
      | Took Other Action which Affected Receipt of Services |
    And I click on save button on task edit page
    And I will update the following information in edit task page
      | reasonForEdit               | Corrected Data Entry |
      | dmasReceivedDate            | today                |
      | coverVAReceivedDate         | -1                   |
      | externalApplicationId       | Test123              |
      | externalCaseId              | 12345                |
      | caseStatus                  | Existing Coverage    |
      | businessUnit                | CPU                  |
      | appealCaseSummaryDueDate    | +1                   |
      | appealsCaseSummaryHyperlink | Test123              |
      | appealCaseSummaryStatus     | Approved by CoverVA  |
      | reviewOutcome               | Error(s) Found       |
      | actionTaken                 | Admin Resolve        |
      | disposition                 | Resolved w/o Hearing |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions
    Examples:
      | taskId | taskType      | srType | status   | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      |        | Review Appeal |        | Complete |            | 1        |         |            |        |                |            | false         |            |            |        |          |           |           |               |


  @CP-15989 @CP-15989-1 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @kamil
  Scenario: Task Autocomplete Assignee Field does not Include System Users as Options
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I send API CALL to TM for get user list with accountType
      | user.accountType | System |
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Translation Request" task page
    When Verify the Assignee field and the autocomplete dropdown list displays
    Then Verify Assignee field not display System users
    And I will provide following information before creating task
      | taskInfo                 | Test CP 15989                  |
      | assignee                 | Service TesterTwo              |
      | externalConsumerID       | 1234567890                     |
      | externalCaseId           | 123456789                      |
      | actionTaken              | Escalated to Translation Group |
      | informationType          | Renewal Packet                 |
      | language                 | Hindi                          |
      | dateTranslationEscalated | +1                             |
      | dateTranslationReceived  | today                          |
      | dateTranslationMailed    | -1                             |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I navigate to View Task details
    And I click on edit button on view task page
    When Verify the Assignee field and the autocomplete dropdown list displays
    Then Verify Assignee field not display System users
    And I will update the following information in edit task page
      | taskInfo | Test CP 15989     |
      | assignee | Service TesterTwo |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And Close the soft assertions

  @CP-21849 @CP-21849-01 @CoverVA-UI-Regression @ui-wf-coverVA @kamil
  Scenario:Task that is Complete and then gets updated does not displaying more than once
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "AlexaZLvih" and Last Name as "WizaunRjW"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Appeal Withdrawal" task page
    And I will provide following information before creating task
      | assignee    |                 |
      | taskInfo    |                 |
      | cpSRID      |                 |
      | status      | Complete        |
      | disposition | Withdrew Appeal |
    And I click on save button in create task page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | disposition   | Other                |
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task
    Then I click on keyboard backspace button
    And I navigate to newly created task by clicking on TaskID column header
    And Verify TaskList not displaying duplicate TaskIds and task status is "Complete"
    And Close the soft assertions

  @CP-18777 @CP-18777-02 @CP-34498 @CP-34498-26 @CP-38195 @CP-38195-02-05 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan
  Scenario Outline: Verification all fields and mandatory fields on Appeals to DMAS Escalation edit task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in descending order in Task Search
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
      | priority               |          |
      | assignee               |          |
      | status                 | Complete |
      | businessUnitAssigneeTo |          |
      | taskInfo               | ABC      |
      | documentDueDate        |          |
      | contactRecordSingle    |          |
      | externalApplicationId  |          |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    And I verify task mandatory fields error message "PRIORITY"
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "DUE DATE "
    And I verify minimum lenght error message "TASK INFORMATION"
    And I verify task mandatory fields error message "APPLICATION ID "
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
    Examples:
      | taskId | taskType                   | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      |        | Appeals to DMAS Escalation |        |        |            | 5        |         |            |        |                |            | false         |            |            |        |          |           |           |               |
