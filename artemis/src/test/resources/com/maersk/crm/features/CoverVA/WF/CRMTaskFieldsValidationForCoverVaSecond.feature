Feature: Task Fields Validation for CoverVA Second

@CP-19194 @CP-19194-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
Scenario: Verification all fields and mandatory fields on Appeal Withdrawal create task page
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I minimize Genesys popup if populates
When I navigate to "Appeal Withdrawal" task page
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
Then I verify task mandatory fields error message "DISPOSITION"
Then I verify "disposition" single select drop down value
| Escalated to Supervisor |
| Other                   |
| Reclassified Document   |
| Returned to DMAS        |
| Withdrew Appeal         |
Then I verify text box Date and Time field value and error message for following fields
| CP SR Id |
And Close the soft assertions

@CP-19203 @CP-19203-01 @CP-19837 @CP-19837-01 @CP-22537 @CP-22537-01 @CP-34498 @CP-34498-04 @CP-38195 @CP-38195-01-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal @ruslan
Scenario: Verification all fields and mandatory fields on CVCC to DMAS Escalation create task page
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I minimize Genesys popup if populates
When I navigate to "CVCC to DMAS Escalation" task page
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
| Returned - Sent in Error |
| Transferred to DMAS      |
Then I verify "contactReason" single select drop down value
| FAMIS MCO Change - Error              |
| FAMIS MCO Change - Good Cause Request |
| Media Inquiries                       |
| Other                                 |
Then I verify text box Date and Time field value and error message for following fields
| VaCMS Case ID        |
| MMIS Member ID       |
| Application Id       |
| CP contact record Id |
| CP SR Id             |
| CP Task Id           |
| Due Date             |
And Close the soft assertions

@CP-18988 @CP-18988-01 @CP-22537 @CP-22537-01 @CP-34498 @CP-34498-05 @CP-38195 @CP-38195-01-04  @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal @ruslan
Scenario: Verification all fields and mandatory fields on CPU Eligibility to DMAS Escalation create task page
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I minimize Genesys popup if populates
When I navigate to "CPU Eligibility to DMAS Escalation" task page
And I will check action taken is not mandatory when task status is not complete
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "CONTACT REASON"
And I verify task mandatory fields error message "APPLICATION ID "
And I verify task mandatory fields error message "DUE DATE "
Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
And I verify "actionTakenSingle" single select drop down value
| Escalated to DMAS (maersk)       |
| Request Complete (DMAS)           |
| Request in Progress (DMAS)        |
| Returning - Sent in Error (DMAS)  |
| Unable to Complete Request (DMAS) |
Then I verify "contactReason" single select drop down value
| Coverage Correction (HPE) |
| ECPR Review               |
| Emergency Services        |
| Medicare Review           |
| Other                     |
| SAVE/SVES Request         |
Then I verify text box Date and Time field value and error message for following fields
| VaCMS Case ID        |
| Application Id       |
| CP contact record Id |
| CP SR Id             |
| CP Task Id           |
| Due Date             |
And Close the soft assertions

@CP-18781 @CP-18781-01 @CP-22537 @CP-22537-01 @CP-38195 @CP-38195-01-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
Scenario: Verification all fields and mandatory fields on CVIU CC to DMAS Escalation create task page
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I minimize Genesys popup if populates
When I navigate to "CVIU CC to DMAS Escalation" task page
And I will check action taken is not mandatory when task status is not complete
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "CONTACT REASON"
And I verify task mandatory fields error message "APPLICATION ID "
And I verify task mandatory fields error message "DUE DATE "
Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
And I verify "actionTakenSingle" single select drop down value
| Returned - Sent in Error |
| Transferred to DMAS      |
Then I verify "contactReason" single select drop down value
| Facility Complaint |
| Media Inquiries    |
| Other              |
Then I verify text box Date and Time field value and error message for following fields
| VaCMS Case ID        |
| MMIS Member ID       |
| Application Id       |
| CP contact record Id |
| CP SR Id             |
| CP Task Id           |
| Due Date             |
And Close the soft assertions

@CP-18032 @CP-18032-01 @CP-19361 @CP-19361-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
Scenario: Verification all fields and mandatory fields on Hospital Authorized Representative Request create task page
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I minimize Genesys popup if populates
When I navigate to "Hospital Authorized Representative Request" task page
And I will check action taken is not mandatory when task status is not complete
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "DISPOSITION"
And I verify task mandatory fields error message "CONTACT REASON"
And I verify task mandatory fields error message "AR FIRST NAME "
And I verify task mandatory fields error message "AR LAST NAME "
And I verify "actionTaken" multi select drop down value
| Emailed Requested Docs  |
| Escalated to Supervisor |
| Mailed Requested Docs   |
| None                    |
| Provided Case Status    |
| Transferred to LDSS     |
| Uploaded Provided Docs  |
Then I verify "disposition" single select drop down value
| Resolved    |
| Transferred |
| Unresolved  |
Then I verify "contactReason" single select drop down value
| Appeal Status Inquiry      |
| Application Status Inquiry |
| Document Request           |
| File Appeal                |
| Other                      |
| Report Issue               |
| Request Copy of Notices    |
| Translation Services       |
| Unknown                    |
Then I verify text box Date and Time field value and error message for following fields
| VaCMS Case ID  |
| MMIS Member ID |
| AR Email       |
| AR First Name  |
| AR Last Name   |
| AR Phone       |
| Organization   |
And Close the soft assertions

@CP-19122 @CP-19122-01 @CP-22537 @CP-22537-01 @CP-34498 @CP-34498-12 @CP-38195 @CP-38195-01-08 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal @ruslan
Scenario: Verification all fields and mandatory fields on  DMAS to CPU Escalation create task page
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I minimize Genesys popup if populates
When I navigate to "DMAS to CPU Escalation" task page
And I will check action taken is not mandatory when task status is not complete
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "CONTACT REASON"
And I verify task mandatory fields error message "APPLICATION ID "
And I verify task mandatory fields error message "DUE DATE "
Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
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

@CP-19148 @CP-19148-01 @CP-22537 @CP-22537-01 @CP-34498 @CP-34498-10 @CP-38195 @CP-38195-01-07 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal @ruslan
Scenario: Verification all fields and mandatory fields on  DMAS to CVCC Escalation create task page
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I minimize Genesys popup if populates
When I navigate to "DMAS to CVCC Escalation" task page
And I will check action taken is not mandatory when task status is not complete
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "CONTACT REASON"
And I verify task mandatory fields error message "APPLICATION ID "
And I verify task mandatory fields error message "DUE DATE "
Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
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

@CP-19309 @CP-19309-01 @CP-19309-02 @CP-19535 @CP-19535-02 @CP-18045 @CP-18045-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @elvin @vidya
Scenario: Verification all fields and mandatory fields on Authorized Representative Designation create task page
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I minimize Genesys popup if populates
When I navigate to "Authorized Representative Designation" task page
And I will provide following information before creating task
| priority |          |
| status   | Complete |
| taskInfo | ABC      |
And I click on save button in create task page
And I verify task mandatory fields error message "PRIORITY"
And I verify task mandatory fields error message "AR FIRST NAME "
And I verify task mandatory fields error message "AR LAST NAME "
And I verify task mandatory fields error message "AR PHONE "
And I verify task mandatory fields error message "AR ADDRESS LINE 1 "
And I verify task mandatory fields error message "AR CITY "
And I verify task mandatory fields error message "AR STATE "
And I verify task mandatory fields error message "AR ZIP CODE"
And I verify minimum lenght error message "TASK INFORMATION"
And I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "DISPOSITION"
And I verify task mandatory fields error message "CHANNEL "
And I verify "channel" single select drop down value
| Mail/Fax  |
| Phone     |
| VaCMS MWS |
And I verify "actionTaken" multi select drop down value
| Escalated to Supervisor |
| None                    |
| Submitted Change        |
And I verify "disposition" single select drop down value
| Resolved   |
| Unresolved |
Then I verify text box Date and Time field value and error message for following fields
| VaCMS Case ID     |
| MMIS Member ID    |
| AR First Name     |
| AR Last Name      |
| AR Email          |
| AR Phone          |
| AR Address Line 1 |
| AR Address Line 2 |
| AR City           |
| AR State          |
| AR Zip Code       |
And Close the soft assertions

@CP-18026 @CP-18026-01 @CP-22537 @CP-22537-01 @CP-32925 @CP-32925-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
Scenario: Verification all fields and mandatory fields on Outbound Call create task page
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I minimize Genesys popup if populates
When I navigate to "Outbound Call" task page
And I will check action taken is not mandatory when task status is not complete
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "CONTACT REASON"
And I verify task mandatory fields error message "APPLICATION ID "
And I verify task mandatory fields error message "DISPOSITION"
And I verify task mandatory fields error message "Name"
And I verify task mandatory fields error message "PREFERRED PHONE "
And I verify "actionTaken" multi select drop down value
| Did Not Reach Consumer      |
| None                        |
| Reached Consumer            |
| Referred to External Entity |
| Transferred to LDSS         |
Then I verify "contactReason" single select drop down value
| Courtesy Callback   |
| Dropped Call        |
| Follow-Up           |
| Missing Information |
| Other               |
Then I verify "disposition" single select drop down value
| Resolved    |
| Transferred |
| Unresolved  |
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
| VaCMS Case ID            |
| MMIS Member ID           |
| Application Id           |
| PREFERRED PHONE          |
| NAME                     |
| PREFERRED CALL BACK TIME |
| PREFERRED CALL BACK DATE |
And Close the soft assertions

@CP-18647 @CP-18647-03 @CP-18649 @CP-18649-03 @CP-19343 @CP-19343-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
Scenario: Verification all fields and mandatory fields on Process App V1 create task page
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I minimize Genesys popup if populates
When I navigate to "Process App V1" task page
And I will check action taken is not mandatory when task status is not complete
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
Then I verify task mandatory fields error message "# OF APPLICANTS IN HOUSEHOLD "
Then I verify task mandatory fields error message "APPLICATION SUB-STATUS "
Then I verify task mandatory fields error message "APPLICATION RECEIVED DATE "
Then I verify task mandatory fields error message "APPLICATION SIGNATURE DATE "
Then I verify task mandatory fields error message "# OF APPROVED APPLICANTS "
Then I verify task mandatory fields error message "DECISION SOURCE "
Then I verify task mandatory fields error message "MISSING INFO REQUIRED? "
And I verify "actionTakenSingle" single select drop down value
| Sent NOA            |
| Sent VCL            |
| Transferred to LDSS |
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
And Close the soft assertions

@CP-19376 @CP-19376-01 @CP-19313 @CP-19313-01 @CP-22537 @CP-22537-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @elvin @priyal
Scenario: Verification all fields and mandatory fields on create task page Pre-Hearing Conference task type
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I minimize Genesys popup if populates
When I navigate to "Pre-Hearing Conference" task page
And I will check action taken is not mandatory when task status is not complete
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "PRE-HEARING REASON "
And I verify task mandatory fields error message "APPLICATION ID "
And I verify task mandatory fields error message "DISPOSITION"
And I verify task mandatory fields error message "CASE STATUS "
And I verify "actionTaken" multi select drop down value
| 2nd Outbound Call - Did Not Reach |
| 2nd Outbound Call - Reached       |
| Admin Resolve                     |
| Decision Upheld                   |
| Outbound Call - Left Voicemail    |
| Outbound Call - Reached           |
| Outbound Call - Unable to Reach   |
| Processed Application/Renewal     |
| Redetermined Case                 |
And I verify "pre-HearingOutcome" multi select drop down value
| Error(s) Found      |
| No Error(s) Found   |
| No Processing Delay |
| Processing Delay    |
And I verify "disposition" single select drop down value
| Directed to DMAS    |
| Resolved            |
| Transferred to LDSS |
And I verify "pre-HearingReason" multi select drop down value
| Case Closure - Incomplete CPU Renewal   |
| Case Closure - Incomplete CVIU Renewal  |
| Case Closure - No Longer Eligible       |
| Coverage Period                         |
| Coverage Type                           |
| Denial - Does Not Meet MAGI Group       |
| Denial - Duplicate Application/Coverage |
| Denial - Immigration Status             |
| Denial - Incomplete/Failure to Provide  |
| Denial - Over Income                    |
| Untimely Processing                     |
And I verify "caseStatus" single select drop down value
| Existing Coverage   |
| New Application     |
| Renewal Application |
Then I verify text box Date and Time field value and error message for following fields
| VaCMS Case ID    |
| Application Id   |
| Appointment Time |
| Appointment Date |
And Close the soft assertions

@CP-21548 @CP-21548-01 @CP-21548-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @kamil
Scenario: Verification Facility Type and Facility Name Conditionally Display on Process Application Task
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I minimize Genesys popup if populates
When I navigate to "Process App V1" task page
And I will provide following information before creating task
  | status                    | Complete                     |
  | applicationType           | Expedited Application - CVIU |
  | externalApplicationId     | random                       |
  | channel                   | Paper                        |
  | myWorkSpaceDate           | today                        |
  | applicationReceivedDate   | -1                           |
  | applicationSignatureDate  | today                        |
  | noOfApplicantsInHousehold | 1                            |
  | applicationStatus         | Data Collection              |
  | decisionSource            | VaCMS                        |
  | noOfApprovedApplicants    | 12                           |
  | missingInfoRequired       | No                           |
  | disposition               | Approved                     |
  | actionTakenSingle         | Sent VCL                     |
And I click on save button in create task page
#    Then I verify Success message is displayed for task
When I will get the Authentication token for "CoverVA" in "CRM"
And I will get "Complete" from traceId and pass it as end point
And I initiated Event GET API
And I run the Event GET API and get the payload
When I navigate to "Task Search" page
Then I will search for newly created task on Task Search
And I click on search button on task search page
And In search result click on task id to navigate to view page
And I click on edit button on view task page
Then Verify Facility Type and Facility Name fields are enabled with Application Type
| Emergency Medicaid Services Application - CVIU |
| Expedited Application - CVIU                   |
| Ex Parte Renewal - CVIU                        |
| MAGI Standard Application - CVIU               |
| Pre-Release Application - CVIU                 |
| Re-Entry Application - CVIU                    |
| Renewal Application CVIU                       |
And I will update the following information in edit task page
| facilityType    | Department of Corrections    |
| facilityName    | Beaumont Correctional Center |
| applicationType | MAGI - CPU                   |
Then I verify "Facility Type" field is disable and cleared out
Then I verify "Facility Name" field is disable and cleared out
And I will update the following information in edit task page
| applicationType | MAGI - PW |
Then I verify "Facility Type" field is disable and cleared out
Then I verify "Facility Name" field is disable and cleared out
And Close the soft assertions

#  @CP-18021 @CP-18021-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
#  according to the story channel field is should be optional
Scenario: Verification all fields and mandatory fields on Report of Newborn create task page
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I minimize Genesys popup if populates
When I navigate to "Report of Newborn" task page
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
Then I verify task mandatory fields error message "CHANNEL "
Then I verify "channel" single select drop down value
| Fax        |
| Phone      |
| Web Chat   |
| Web Portal |
And Close the soft assertions

@CP-19385 @CP-19385-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
Scenario: Verification all fields and mandatory fields on Incomplete Contact Record Create Task Page
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I minimize Genesys popup if populates
When I navigate to "Incomplete Contact Record" task page
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "DISPOSITION"
And I verify "actionTaken" multi select drop down value
| Completed Contact Record          |
| Outbound Call - Did Not Reach     |
| Outbound Call - Reached           |
| Unable to Complete Contact Record |
And I verify "disposition" single select drop down value
| Resolved    |
| Transferred |
| Unresolved  |
Then I verify text box Date and Time field value and error message for following fields
| VaCMS Case ID  |
| MMIS Member ID |
And Close the soft assertions


@CP-32385 @CP-32385-01 @CP-30301 @CP-30301-03 @CP-30081 @CP-30081-07 @CoverVA-UI-Regression @ui-wf-coverVA @API-WF @kamil @ruslan
Scenario: Validate Re-calculate Due Date for a given Task
When I will get the Authentication token for "CoverVA" in "CRM"
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I minimize Genesys popup if populates
When I navigate to "Translation Request" task page
And I will provide following information before creating task
| taskInfo                 | Test CP 16149                  |
| externalConsumerID       | 1234567890                     |
| externalCaseId           | 123456789                      |
| actionTaken              | Escalated to Translation Group |
| informationType          | Renewal Packet                 |
| language                 | Hindi                          |
| dateTranslationEscalated | +1                             |
| dateTranslationReceived  | today                          |
| dateTranslationMailed    | -1                             |
And I click on save button on task edit page
When I will get the Authentication token for "CoverVA" in "CRM"
And I will get "Created" from traceId and pass it as end point
And I initiated Event GET API
And I run the Event GET API and get the payload
When I navigate to "Task Search" page
Then I will search for newly created task on Task Search
And I perform a Task Search by following fields
| assignmentStatus | Assigned |
And I click on search button on task search page
Then I verify request payload "assignedFlag" contains assignedFlag "true" and serviceRequestInd "false"
Then I verify No Records Available message is displayed
And I perform a Task Search by following fields
| assignmentStatus | Unassigned |
And I click on search button on task search page
Then I verify request payload "assignedFlag" contains assignedFlag "false" and serviceRequestInd "false"
And In search result click on task id to navigate to view page
Then I initiated Task Update DueDate API
Then I run API call for Update DueDate with data
| tasks[0].taskTriggerDate | date:: |
Then I verify DueDate updated on Task
And Close the soft assertions