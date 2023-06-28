Feature: Task Update and View Validation for IN-EB_2

   # refactoring on 14-03-2022 by chandrakumar
  @CP-30402 @CP-30402-03 @CP-34964 @CP-34964-01 @CP-34964-02 @crm-regression @INEB-UI-Regression @ui-wf-ineb @chandrakumar
  Scenario: Validate HIP Plan Change Form task edit/view page
  Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
  And I minimize Genesys popup if populates
  When I navigate to "HIP Plan Change Form" task page
  And I click on save button on task edit page
  And I will provide following information before creating task
    | currentPlan      | Anthem HIP     |
    | DesiredPlan      | CareSource HIP |
    | requestedDate    | today          |
    | planChangeReason | Conditional    |
    | taskInfo         ||
    | assignee         ||
    | other            ||
  And I click on save button in create task page
  When I navigate to "Work Queue" page
  And I navigate to newly created task by clicking on TaskID column header
  And I will click on newly created task id
  Then I verify the below task details are displayed in my task
  And I click on edit button on view task page
  And I will update the following information in edit task page
    | currentPlan      ||
    | DesiredPlan      ||
    | requestedDate    ||
    | planChangeReason ||
  And I click on save button on task edit page
  Then I verify task mandatory fields error message "CURRENT PLAN "
  And I verify task mandatory fields error message "DESIRED PLAN "
  And I verify task mandatory fields error message "REQUESTED DATE "
  And I verify task mandatory fields error message "CHANGE REASON "
  Then I verify "currentPlan" single select drop down value
    | Anthem HIP                 |
    | CareSource HIP             |
    | Managed Health Services HIP|
    | MDWise                     |
    | No Current Plan            |
  Then I verify "desiredPlan" single select drop down value
    | Anthem HIP                 |
    | CareSource HIP             |
    | Managed Health Services HIP|
    | MDWise                     |
  Then I verify "planChangeReason" single select drop down value
    |Conditional	                    |
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
  And I will update the following information in edit task page
    |planChangeReason| Other  |
  And I click on save button on task edit page
  And I verify task mandatory fields error message "OTHER "
  Then I verify text box Date and Time field value and error message for following fields
      |Other|
  And I will update the following information in edit task page
    | status           | Complete                    |
    | reasonForEdit    | Corrected Data Entry        |
    | assignee         | Service TesterTwo           |
    | taskInfo         | Test CP-30402 Updated       |
    | currentPlan      | CareSource HIP              |
    | DesiredPlan      | Managed Health Services HIP |
    | requestedDate    | +5                          |
    | planChangeReason | Other                       |
    | other            | Information Added           |
    | priority         | 4                           |
  And I click on save button on task edit page
#  Then I verify Success message is displayed for task update
  Then I verify should I navigated to view task page
  And I verify the updated information in view task details page
  And Close the soft assertions

  @CP-30401 @CP-30401-03 @CP-34963 @CP-34963-03 @crm-regression @INEB-UI-Regression @ui-wf-ineb @chandrakumar @ruslan
  Scenario: Validate fields present in Healthy Indiana Tobacco Response Task created and data is saved
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HIP Tobacco Response" task page
    And I will provide following information before creating task
      | taskInfo                      ||
      | usedTobaccoInTheLastSixMonths | Yes |
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | usedTobaccoInTheLastSixMonths ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "USED TOBACCO IN THE LAST 6 MONTHS? "
    Then I verify "usedTobaccoInTheLast6Months" single select drop down value
      | No  |
      | Refused |
      | Yes |
    And I will update the following information in edit task page
      | status                        | Complete              |
      | reasonForEdit                 | Corrected Data Entry  |
      | assignee                      | Service TesterTwo     |
      | taskInfo                      | Test CP-30401 Updated |
      | usedTobaccoInTheLastSixMonths | No |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

    # refactoring on 14-03-2022 by chandrakumar
  @CP-30403 @CP-30403-03 @CP-34965 @CP-34965-04 @crm-regression @INEB-UI-Regression @ui-wf-ineb @chandrakumar
  Scenario: Validate HCC Plan Change Form task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HCC Plan Change Form" task page
    And I click on save button on task edit page
    And I will provide following information before creating task
      | currentPlan      | Anthem HCC        |
      | DesiredPlan      | CareSource HCC    |
      | requestedDate    | today             |
      | planChangeReason | Future Assignment |
      | taskInfo         ||
      | assignee         ||
      | other            ||
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | currentPlan      ||
      | DesiredPlan      ||
      | requestedDate    ||
      | planChangeReason ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "CURRENT PLAN "
    And I verify task mandatory fields error message "DESIRED PLAN "
    And I verify task mandatory fields error message "REQUESTED DATE "
    And I verify task mandatory fields error message "CHANGE REASON "
    Then I verify "currentPlan" single select drop down value
      | Anthem HCC                  |
      |	CareSource HCC              |
      |	Managed Health Services HCC |
      | MDWise HCC                  |
      | No Current Plan             |
      | UHC                         |
    Then I verify "desiredPlan" single select drop down value
      | Anthem HCC                  |
      |	CareSource HCC              |
      |	Managed Health Services HCC |
      | MDWise HCC                  |
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
    And I will update the following information in edit task page
      |planChangeReason| Other  |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "OTHER "
    Then I verify text box Date and Time field value and error message for following fields
      |Other|
    And I will update the following information in edit task page
      | status           | Complete               |
      | reasonForEdit    | Corrected Data Entry   |
      | assignee         | Service TesterTwo      |
      | taskInfo         | Test CP-30403 Updated  |
      | currentPlan      | MDWise HCC             |
      | DesiredPlan      | Anthem HCC             |
      | requestedDate    | +5                     |
      | planChangeReason | Other                  |
      | other            | Information Added      |
      | priority         | 4                      |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

    # refactoring on 14-03-2022 by chandrakumar
  @CP-30396 @CP-30396-03 @crm-regression @ui-wf-ineb @vidya
  Scenario: Verification all fields and mandatory fields on After Hours Voicemail create task page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "After Hours Voicemail" task page
    And I will provide following information before creating task
      | assignee             ||
      | taskInfo             ||
      | contactName          | Vidya Mithun|
      | contactPhone         | 1234467890  |
      | dateOfVoicemail      | +1          |
      | timeOfVoicemail      | 12:00 PM    |
      | actionTaken          ||
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status               |Complete|
      | contactName          ||
      | contactPhone         ||
      | dateOfVoicemail      ||
      | timeOfVoicemail      ||
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
    And I will update the following information in edit task page
      | status               | Complete           |
      | assignee             | Service TesterTwo  |
      | priority             | 4                  |
      | taskInfo             | CP-30396-03        |
      | reasonForEdit        |Corrected Data Entry|
      | contactName          | Surabhi Shankar    |
      | contactPhone         | 9876543210         |
      | dateOfVoicemail      | today              |
      | timeOfVoicemail      | 09:30 AM            |
      | actionTaken          |Contacted Consumer - Reached|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30406 @CP-30406-03 @crm-regression @ui-wf-ineb @vidya
  Scenario: Verification all fields and mandatory fields on Inbound Document create task page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Inbound Document" task page
    And I will provide following information before creating task
      | assignee    ||
      | taskInfo    ||
      | medicaidId  ||
      | actionTaken ||
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify "actionTaken" multi select drop down value
      | Other               |
      | Responded via Email |
      | Responded via Fax   |
      | Responded via Phone |
    Then I verify text box Date and Time field value and error message for following fields
      | Medicaid ID/RID |
    And I will update the following information in edit task page
      | status        | Complete             |
      | assignee      | Service TesterTwo    |
      | priority      | 4                    |
      | taskInfo      | CP-30406-03          |
      | reasonForEdit | Corrected Data Entry |
      | medicaidId    | 9876543210           |
      | actionTaken   | Responded via Phone  |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

    # refactoring on 14-03-2022 by chandrakumar
  @CP-30405 @CP-30405-03 @CP-34967 @CP-34967-01 @CP-34967-02 @CP-34967-03 @crm-regression @INEB-UI-Regression @ui-wf-ineb @chandrakumar
  Scenario: Validate HHW Plan Change Form task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HHW Plan Change Form" task page
    And I click on save button on task edit page
    And I will provide following information before creating task
      | currentPlan      | Anthem            |
      | DesiredPlan      | CareSource        |
      | requestedDate    | today             |
      | planChangeReason | Future Assignment |
      | taskInfo         ||
      | assignee         ||
      | other            ||
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | currentPlan      ||
      | DesiredPlan      ||
      | requestedDate    ||
      | planChangeReason ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "CURRENT PLAN "
    And I verify task mandatory fields error message "DESIRED PLAN "
    And I verify task mandatory fields error message "REQUESTED DATE "
    And I verify task mandatory fields error message "CHANGE REASON "
    Then I verify "currentPlan" single select drop down value
      | Anthem                  |
      |	CareSource              |
      |	Managed Health Services |
      | MDWise HH               |
      | No Current Plan         |
    Then I verify "desiredPlan" single select drop down value
      | Anthem                  |
      |	CareSource              |
      |	Managed Health Services |
      | MDWise HH               |
      | Disenroll               |
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
    And I will update the following information in edit task page
      |planChangeReason| Other  |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "OTHER "
    Then I verify text box Date and Time field value and error message for following fields
      |Other|
    And I will update the following information in edit task page
      | status           | Complete               |
      | reasonForEdit    | Corrected Data Entry   |
      | assignee         | Service TesterTwo      |
      | taskInfo         | Test CP-30405 Updated  |
      | currentPlan      | MDWise HH              |
      | DesiredPlan      | Anthem                 |
      | requestedDate    | +5                     |
      | planChangeReason | Other                  |
      | other            | Information Added      |
      | priority         | 4                      |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

    # refactoring on 14-03-2022 by chandrakumar
  @CP-30469 @CP-30469-03 @CP-30408 @CP-30408-03 @crm-regression @ui-wf-ineb @priyal
  Scenario: Verification all fields and mandatory fields on Outbound Call Task on edit page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Outbound Call" task page
    And I will provide following information before creating task
      | assignee                 ||
      | taskInfo                 ||
      | contactName              ||
      | preferredCallBackDate    ||
      | preferredCallBackTime    ||
      | preferredPhone           ||
      | actionTaken              ||
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status |Complete|
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
    And I will update the following information in edit task page
      | status                 | Complete|
      | assignee               | Service TesterTwo|
      | priority               | 4|
      | taskInfo               | CP-30469-03|
      | reasonForEdit          | Corrected Data Entry|
      | contactName            | Priyal|
      | preferredCallBackDate  | today|
      | preferredCallBackTime  | 03:28 PM|
      | preferredPhone         | 1234567890|
      | actionTaken            | Contacted Consumer - Reached|
      | disposition            | Did Not Reach Consumer|
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-34974 @CP-30469 @CP-30469-04 @CP-30408 @CP-30408-04 @crm-regression @ui-wf-ineb @priyal @moldir
  Scenario: Verification all fields and mandatory fields on Complaint Outbound Call Task on edit page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Complaint Outbound Call" task page
    And I will provide following information before creating task
      | assignee                 ||
      | taskInfo                 ||
      | contactName              ||
      | preferredCallBackDate    ||
      | preferredCallBackTime    ||
      | preferredPhone           ||
      | actionTaken              ||
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status |Complete|
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
      | Complaint Closed|
    Then I verify text box Date and Time field value and error message for following fields
      |PREFERRED CALL BACK DATE|
      |PREFERRED CALL BACK TIME|
      |PREFERRED PHONE|
      |CONTACT NAME|
    And I will update the following information in edit task page
      | status                 | Complete|
      | assignee               | Service TesterTwo|
      | priority               | 4|
      | taskInfo               | CP-30469-03|
      | reasonForEdit          | Corrected Data Entry|
      | contactName            | Priyal|
      | preferredCallBackDate  | today|
      | preferredCallBackTime  | 03:28 PM|
      | preferredPhone         | 1234567890|
      | actionTaken            | Contacted Consumer - Reached|
      | disposition            | Did Not Reach Consumer|
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-30465 @CP-30465-02 @CP-34971 @CP-34971-02 @crm-regression @ui-wf-ineb @priyal
  Scenario: Verification all fields and mandatory fields on Customer Service Complaint SR on create/edit/view page
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "CATHERINE" and Last Name as "MARCOS"
    When I click the first consumer id from the search results
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout        | maersk|
      | incidentDate          | today|
      | memberName            | Priyal|
      | complaintType         | Customer Service |
      | priority              | 4|
      | csrName               ||
      | preferredPhone        ||
      | preferredCallBackDate ||
      | preferredCallBackTime ||
      | contactName           ||
    And I click on save button in create service request page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I verify the SR details displayed on view SR page
    And I click on edit service request button
    And I will update the following information in edit task page
      | memberName            ||
      | complaintAbout        ||
      | complaintType         ||
      | incidentDate          ||
    And I click on save button on task edit page
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
    And I will update the following information in edit task page
      | complaintAbout | Other|
    And I click on save button on task edit page
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
    And I will update the following information in edit task page
      | status  | Closed|
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    Then I verify "disposition" single select drop down value
      | Complaint Closed - Did Not Reach Consumer|
      | Complaint Invalid|
      | Complaint Resolved|
      | Complaint Resolved - Emergency|
    And I will update the following information in edit task page
      | status                | Closed|
      | priority              | 5|
      | taskInfo              | CP-30465-02|
      | reasonForEdit         | Corrected Data Entry|
      | memberName            | Priyal Garg|
      | preferredPhone        | 1234567890|
      | complaintAbout        | Other|
      | organizationDD        | Anthem|
      | complaintType         | Customer Service|
      | incidentDate          | today|
      | preferredCallBackDate | today|
      | preferredCallBackTime | 04:30 PM|
      | contactName           | Test|
      | escalationReason      | Consumer Contacting the State |
      | csrName               | Service TesterTwo|
      | disposition           | Complaint Resolved - Emergency|
    And I click on save button on task edit page
    And I verify the updated information in view task details page
    And Close the soft assertions
