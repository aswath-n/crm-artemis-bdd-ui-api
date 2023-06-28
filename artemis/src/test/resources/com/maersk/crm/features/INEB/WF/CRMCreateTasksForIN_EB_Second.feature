Feature: Task Fields Validation for IN-EB Second

@CP-30464 @CP-30464-01 @crm-regression @ui-wf-ineb @ruslan
Scenario: Verification all fields and mandatory fields on JC Outbound Call create task page
Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
And I minimize Genesys popup if populates
When I navigate to "JC Outbound Call" task page
And I will check action taken is not mandatory when task status is not complete
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "DISPOSITION"
And I verify "actionTaken" multi select drop down value
| Contacted Consumer - Did Not Reach/Left Voicemail |
| Contacted Consumer - Did Not Reach/No Voicemail   |
| Contacted Consumer - Invalid Phone Number         |
| Contacted Consumer - Reached                      |
Then I verify "disposition" single select drop down value
| Did Not Reach Consumer        |
| Successfully Reached Consumer |
Then I verify text box Date and Time field value and error message for following fields
| CONTACT NAME           |
| PREFERRED PHONE          |
| PREFERRED CALL BACK DATE |
| PREFERRED CALL BACK TIME |
And Close the soft assertions

@CP-30403 @CP-30403-01 @CP-34965 @CP-34965-02 @crm-regression @INEB-UI-Regression @ui-wf-ineb @chandrakumar
Scenario: Validate fields present in HCC Plan Change Form Task in create task page
Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
And I minimize Genesys popup if populates
When I navigate to "HCC Plan Change Form" task page
And I click on save button on task edit page
Then I verify task mandatory fields error message "CURRENT PLAN "
And I verify task mandatory fields error message "DESIRED PLAN "
And I verify task mandatory fields error message "REQUESTED DATE "
And I verify task mandatory fields error message "CHANGE REASON "
Then I verify "currentPlan" single select drop down value
| Anthem HCC                  |
| CareSource HCC              |
| Managed Health Services HCC |
| MDWise HCC                  |
| No Current Plan             |
| UHC                         |
Then I verify "desiredPlan" single select drop down value
| Anthem HCC                  |
|	CareSource HCC              |
|	Managed Health Services HCC |
| UHC                         |
Then I verify "planChangeReason" single select drop down value
| Annual Enrollment       |
| CP Error                |
| CP Shows Plan           |
| Disenroll               |
| Excluded                |
| Future Assignment       |
| Just Cause Family Change|
| New to the HCC Program  |
| Open Enrollment         |
| Other                   |
| Provider Change         |
Then I verify text box Date and Time field value and error message for following fields
|Requested Date|
And I will provide following information before creating task
|planChangeReason| Other  |
And I click on save button on task edit page
And I verify task mandatory fields error message "OTHER "
Then I verify text box Date and Time field value and error message for following fields
|Other|
And Close the soft assertions

@CP-30396 @CP-30396-01 @crm-regression @ui-wf-ineb @vidya
Scenario: Verification all fields and mandatory fields on After Hours Voicemail create task page
Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
And I minimize Genesys popup if populates
When I navigate to "After Hours Voicemail" task page
And I will check action taken is not mandatory when task status is not complete
And I will provide following information before creating task
| status |Complete|
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "CONTACT NAME "
And I verify task mandatory fields error message "CONTACT PHONE "
And I verify task mandatory fields error message "DATE OF VOICEMAIL "
And I verify task mandatory fields error message "TIME OF VOICEMAIL "
And I verify "actionTaken" multi select drop down value
|Contacted Consumer - Did Not Reach/Left Voicemail|
|Contacted Consumer - Did Not Reach/No Voicemail  |
|Contacted Consumer - Invalid Phone Number        |
|Contacted Consumer - Reached                     |
Then I verify text box Date and Time field value and error message for following fields
|CONTACT NAME|
|Contact Phone|
|Date Of VoiceMail|
|Time Of VoiceMail|
And Close the soft assertions

@CP-30406 @CP-30406-01 @crm-regression @ui-wf-ineb @vidya
Scenario: Verification all fields and mandatory fields on Inbound Document create task page
Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
And I minimize Genesys popup if populates
When I navigate to "Inbound Document" task page
And I will check action taken is not mandatory when task status is not complete
And I will provide following information before creating task
| status |Complete|
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify "actionTaken" multi select drop down value
|Other              |
|Responded via Email|
|Responded via Fax  |
|Responded via Phone|
Then I verify text box Date and Time field value and error message for following fields
|Medicaid ID/RID|
And Close the soft assertions

@CP-30405 @CP-30405-01 @crm-regression @INEB-UI-Regression @ui-wf-ineb @chandrakumar
Scenario: Validate fields present in HHW Plan Change Form Task in create task page
Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
And I minimize Genesys popup if populates
When I navigate to "HHW Plan Change Form" task page
And I click on save button on task edit page
Then I verify task mandatory fields error message "CURRENT PLAN "
And I verify task mandatory fields error message "DESIRED PLAN "
And I verify task mandatory fields error message "REQUESTED DATE "
And I verify task mandatory fields error message "CHANGE REASON "
Then I verify "currentPlan" single select drop down value
| Anthem                  |
| CareSource              |
| Managed Health Services |
| MDWise HH               |
| No Current Plan         |
Then I verify "desiredPlan" single select drop down value
| Anthem                  |
| CareSource              |
| Managed Health Services |
| Disenroll               |
| MDWise HH               |
Then I verify "planChangeReason" single select drop down value
| Annual Enrollment       |
| CP Error                |
| CP Shows Plan           |
| Excluded                |
| Future Assignment       |
| Just Cause Family Change|
| Open Enrollment         |
| Other                   |
| Provider Change         |
Then I verify text box Date and Time field value and error message for following fields
|Requested Date|
And I will provide following information before creating task
|planChangeReason| Other  |
And I click on save button on task edit page
And I verify task mandatory fields error message "OTHER "
Then I verify text box Date and Time field value and error message for following fields
|Other|
And Close the soft assertions

@CP-30461 @CP-30461-01 @crm-regression @ui-wf-ineb @ruslan
Scenario: Verification all fields and mandatory fields on Just Cause Resolution create task page
Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
And I minimize Genesys popup if populates
When I navigate to "Just Cause Resolution" task page
And I will check action taken is not mandatory when task status is not complete
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "DISPOSITION"
And I verify "actionTaken" multi select drop down value
| Contacted Member                    |
| Linked Inbound Correspondence To SR |
| Processed Update in CORE            |
Then I verify "disposition" single select drop down value
| Contact Member - Did Not Reach        |
| Contact Member - Successfully Reached |
| Did Not Contact Member                |
And Close the soft assertions

@CP-30463 @CP-30463-01 @crm-regression @ui-wf-ineb @ruslan
Scenario: Verification all fields and mandatory fields on Just Cause Resolution create task page
Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
And I minimize Genesys popup if populates
When I navigate to "Just Cause State Discussion" task page
And I will check action taken is not mandatory when task status is not complete
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify "actionTaken" multi select drop down value
| Contacted Member         |
| Discussed With State     |
| Processed Update in CORE |
And Close the soft assertions

@CP-30466 @CP-30466-01 @CP-30467 @CP-30467-01 @CP-30468 @CP-30468-01 @crm-regression @ui-wf-ineb @vidya
Scenario: Verification all fields and mandatory fields on Supervisor Review Complaint/QA Review Complaint/State Escalated Complaint create task page
Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
And I minimize Genesys popup if populates
When I navigate to "Supervisor Review Complaint" task page
And I will check action taken is not mandatory when task status is not complete
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "DISPOSITION"
And I verify "actionTaken" multi select drop down value
| Meet with CSR                |
| Provide Summary Report       |
| Review Complaint Information |
Then I verify "disposition" single select drop down value
| Complaint Reviewed |
And I select "QA Review Complaint" option in task type drop down
And I will check action taken is not mandatory when task status is not complete
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "DISPOSITION"
And I verify "actionTaken" multi select drop down value
| Created Evaluation            |
| Created Summary Report        |
| Updated Complaint Information |
Then I verify "disposition" single select drop down value
| Complaint Invalid  |
| Complaint Reviewed |
And I select "State Escalated Complaint" option in task type drop down
And I will check action taken is not mandatory when task status is not complete
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "DISPOSITION"
And I verify "actionTaken" multi select drop down value
| Notified State - Email |
| Notified State - Phone |
Then I verify "disposition" single select drop down value
| Escalation Complete |
And Close the soft assertions

@CP-30469 @CP-30469-01 @CP-30408 @CP-30408-01 @crm-regression @ui-wf-ineb @priyal
Scenario: Verification all fields and mandatory fields on Complaint Outbound Call/Outbound Call tasks on create task page
Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
And I minimize Genesys popup if populates
When I navigate to "Complaint Outbound Call" task page
And I will check action taken is not mandatory when task status is not complete
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "DISPOSITION"
And I verify "actionTaken" multi select drop down value
| Contacted Consumer - Did Not Reach/Left Voicemail|
| Contacted Consumer - Did Not Reach/No Voicemail|
| Contacted Consumer - Invalid Phone Number|
| Contacted Consumer - Reached|
Then I verify "disposition" single select drop down value
| Complaint Closed|
| Did Not Reach Consumer|
| Successfully Reached Consumer|
Then I verify text box Date and Time field value and error message for following fields
|PREFERRED CALL BACK DATE|
|PREFERRED CALL BACK TIME|
|PREFERRED PHONE|
|CONTACT NAME|
And I select "Outbound Call" option in task type drop down
And I will check action taken is not mandatory when task status is not complete
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
Then I verify task mandatory fields error message "ACTION TAKEN "
And I verify task mandatory fields error message "DISPOSITION"
And I verify "actionTaken" multi select drop down value
| Contacted Consumer - Did Not Reach/Left Voicemail|
| Contacted Consumer - Did Not Reach/No Voicemail|
| Contacted Consumer - Invalid Phone Number|
| Contacted Consumer - Reached|
Then I verify "disposition" single select drop down value
| Did Not Reach Consumer|
| Successfully Reached Consumer|
Then I verify text box Date and Time field value and error message for following fields
|PREFERRED CALL BACK DATE|
|PREFERRED CALL BACK TIME|
|PREFERRED PHONE|
|CONTACT NAME|
And Close the soft assertions

@CP-30458 @CP-30458-01 @CP-33292 @CP-33292-01 @crm-regression @ui-wf-ineb @ruslan
Scenario: Verification all fields and mandatory fields on Just Cause create sr page
Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
And I minimize Genesys popup if populates
When I navigate to "Just Cause" service request page
And I will provide following information before creating task
| dateReceivedGrievance  | +2 |
| dateFollowupEmailSent  | +2 |
| dateDecisionLetterSent | +2 |
| dateSateNotified       | +2 |
And I click on save button in create service request page
Then I verify error message displayed on the "DATE RECEIVED GRIEVANCE" if I select future day
Then I verify error message displayed on the "DATE FOLLOW-UP EMAIL SENT" if I select future day
Then I verify error message displayed on the "DATE STATE NOTIFIED" if I select future day
Then I verify error message displayed on the "DATE DECISION LETTER SENT" if I select future day
Then I verify task mandatory fields error message "RID # "
Then I verify task mandatory fields error message "CONTACT NAME "
Then I verify task mandatory fields error message "CONTACT PHONE "
Then I verify task mandatory fields error message "PROGRAM"
Then I verify task mandatory fields error message "CURRENT PLAN "
Then I verify task mandatory fields error message "DESIRED PLAN "
And I scroll up the page
Then I verify "currentPlan" single select drop down value
| Anthem                        |
| CareSource                    |
| Managed Health Services (MHS) |
| MDWise                        |
| United Healthcare             |
Then I verify "programRequired" single select drop down value
| Healthy Indiana Plan |
| Hoosier Care Connect |
| Hoosier Healthwise   |
Then I verify "desiredPlan" single select drop down value
| Anthem                        |
| CareSource                    |
| Managed Health Services (MHS) |
| MDWise                        |
| United Healthcare             |
Then I verify "reasonExplanationDropdown" single select drop down value
| Another MCE's Drug List Better Meets the Member's Health Care Needs                                                            |
| FSSA or Its Designees Found Poor Quality Healthcare Coverage                                                                   |
| FSSA Took Corrective Action Against the Plan                                                                                   |
| Lack of Access to Medically Necessary Services Covered Under the Plan's Contract with the State                                |
| Limited Access to a Primary Care Clinic or Other Health Services Close to a Member's Home                                      |
| Major Language or Cultural Barriers                                                                                            |
| The Member's Primary Medical Provider (PMP) Has Left the Member's MCE and is Available with Another MCE                        |
| Member Needs Related Services that Must Be Performed at the Same Time But Not All Services Are Available in the Plan's Network |
| Plan (Contractor) Did Not Provide Covered Services                                                                             |
| Plan Did Not Comply with Established Standards of Medical Care                                                                 |
| Plan Did Not Have Providers Experienced in Member's Healthcare Needs                                                           |
| Receiving Poor Quality of Care                                                                                                 |
| A Service is Not Covered by the Plan for Moral or Religious Objections                                                         |
Then I verify "missingInfoRequired" single select drop down value
| No  |
| Yes |
Then I verify "decision" single select drop down value
| Agree                |
| Disagree             |
| No Response Received |
Then I verify "consumerSatisfied" single select drop down value
| No            |
| Not Reachable |
| Yes           |
Then I verify "finalDecision" single select drop down value
| Approved  |
| Denied    |
| Withdrawn |
Then I verify text box Date and Time field value and error message for following fields
| Rid #                      |
| Grievance #                |
| CONTACT NAME               |
| Contact Phone              |
| Contact Email              |
| Explanation                |
| Date Health Plan Contacted |
| Date Received Grievance    |
| Invalid                    |
| Date Follow-up Email Sent  |
| Date State Notified        |
| New Plan Start Date        |
| Date Decision Letter Sent  |
And Close the soft assertions

@CP-30465 @CP-30465-01 @CP-34971 @CP-34971-01 @crm-regression @ui-wf-ineb @priyal
Scenario: Verification all fields and mandatory fields on Customer Service Complaint sr page
Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
And I minimize Genesys popup if populates
When I navigate to "Customer Service Complaint" service request page
And I click on save button in create service request page
Then I verify task mandatory fields error message "Complaint About"
Then I verify task mandatory fields error message "INCIDENT DATE "
Then I verify task mandatory fields error message "MEMBER NAME"
Then I verify task mandatory fields error message "COMPLAINT TYPE "
Then I verify "complaintAbout" single select drop down value
| Emergent Situation|
| maersk|
| Other|
Then I verify "complaintType" single select drop down value
| Customer Service|
| Emergent Situation|
| Other|
| State Escalation|
Then I verify text box Date and Time field value and error message for following fields
| Incident Date|
| Escalated|
| CONTACT NAME|
| PREFERRED PHONE|
| Member NAME|
| PREFERRED CALL BACK TIME|
| PREFERRED CALL BACK DATE|
And I will provide following information before creating task
| complaintAbout | Other|
And I click on save button in create service request page
Then I verify task mandatory fields error message "OTHER ENTITY "
Then I verify task mandatory fields error message "ESCALATION REASON "
Then I verify "organization" single select drop down value
| Anthem|
| CareSource|
| DFR|
| GainWell|
| MDWise|
| MHS|
| UHC|
Then I verify "escalationReason" single select drop down value
| Consumer Contacting the State|
| Dangerous Situation / Harm to Self|
| Threatening to go to the Media|
Then I verify CSR Name field is an autocomplete dropdown of all Active users
And Close the soft assertions

@CP-30409 @CP-30409-02 @CP-34969 @CP-34969-02 @crm-regression @ui-wf-ineb @kamil @ruslan
Scenario: Verification all fields and mandatory fields for Rejected Selection Task on Task create page
Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
And I minimize Genesys popup if populates
When I navigate to "Rejected Selection" task page
And I will provide following information before creating task
| status | Complete |
And I click on save button on task edit page
And I verify "applicationType" single select drop down value
| New Enrollment |
| Plan Change    |
And I verify "disposition" single select drop down value
| Contact Member (explain why selection didn't go through) |
| Contact State                                            |
| Processed in CORE                                        |
| Send back to CSR to correct mistake                      |
And I verify "program" single select drop down value
| HealthyIndianaPlan |
| HoosierCareConnect |
| HoosierHealthwise  |
Then I verify text box Date and Time field value and error message for following fields
| Medicaid ID/RID |
| Start Date      |
| END DATE        |
| Reason          |
And Close the soft assertions