Feature: Task End To End Validation for CoverVA_2

  @CP-18026 @CP-18026-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Validate Outbound Call task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Outbound Call" task page
    And I will provide following information before creating task
      | assignee                  ||
      | taskInfo                  ||
      | contactRecordSingle       |Dropped Call|
      | externalCaseId            ||
      | externalConsumerID        ||
      | externalApplicationId     |random|
      | actionTaken               ||
      | name                      |Priyal|
      | preferredCallBackTime     ||
      | preferredCallBackDate     ||
      | preferredPhone            |1234567890|
      | language                  ||
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                          |Complete|
      | preferredPhone                  ||
      | contactRecordSingle             ||
      | externalApplicationId           ||
      | name                            ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "Name"
    And I verify task mandatory fields error message "PREFERRED PHONE"
    And I verify "actionTaken" multi select drop down value
      | Did Not Reach Consumer|
      | None|
      | Reached Consumer|
      | Referred to External Entity|
      | Transferred to LDSS|
    Then I verify "contactReason" single select drop down value
      | Courtesy Callback|
      | Dropped Call|
      | Follow-Up|
      | Missing Information|
      | Other|
    Then I verify "disposition" single select drop down value
      | Resolved|
      | Transferred|
      | Unresolved|
    And I verify "preferredLanguage" single select drop down value
      | Amharic|
      | Arabic|
      | Bassa|
      | Bengali|
      | Chinese (Traditional)|
      | English|
      | Farsi|
      | French|
      | German|
      | Hindi|
      | Ibo|
      | Korean|
      | Other|
      | Russian|
      | Spanish|
      | Tagalog|
      | Urdu|
      | Vietnamese|
      | Yoruba|
    Then I verify text box Date and Time field value and error message for following fields
      |VaCMS Case ID|
      |MMIS Member ID|
      |Application Id|
      |PREFERRED PHONE|
      |NAME|
      |PREFERRED CALL BACK TIME|
      |PREFERRED CALL BACK DATE|
    And I will update the following information in edit task page
      | status                   | Complete|
      | assignee                 | Service TesterTwo|
      | priority                 | 2 |
      | taskInfo                 | CP-18026-03|
      | reasonForEdit            | Corrected Data Entry|
      | externalCaseId           | 123456789|
      | externalConsumerID       | 1234467890|
      | externalApplicationId    | 3uusr2345|
      | name                     | TestTagDemo|
      | preferredCallBackDate    | today|
      | preferredCallBackTime    | 03:28 PM|
      | preferredPhone           | 1234567890|
      | language                 | Hindi|
      | contactRecordSingle      | Other|
      | actionTaken              | Reached Consumer|
      | disposition              | Unresolved|
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

    # refactoring on 30-08-2021 by priyal
  @CP-19376 @CP-19376-03 @CP-19545 @CP-19545-03 @CP-42774 @CP-42774-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal @vidya
  Scenario: Validate Pre-Hearing Conference task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Pre-Hearing Conference" task page
    And I verify "businessUnitAssignedTo" single select drop down value
      | Appeals      |
      | PHC Renewal  |
    And I will provide following information before creating task
      | externalApplicationId   |random|
      | preHearingReason        |Coverage Type|
      | caseStatus              |New Application|
      | preHearingOutcome       |No Processing Delay|
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                          |Complete|
      | preHearingReason                ||
      | caseStatus                      ||
      | preHearingOutcome               ||
      | externalApplicationId           ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify task mandatory fields error message "PRE-HEARING REASON "
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "CASE STATUS "
    And I verify "actionTaken" multi select drop down value
      | 2nd Outbound Call - Did Not Reach|
      | 2nd Outbound Call - Reached|
      | Admin Resolve|
      | Decision Upheld|
      | Outbound Call - Left Voicemail|
      | Outbound Call - Reached|
      | Outbound Call - Unable to Reach|
      | Processed Application/Renewal|
      | Redetermined Case|
    And I verify "pre-HearingOutcome" multi select drop down value
      | Error(s) Found|
      | No Error(s) Found|
      | No Processing Delay|
      | Processing Delay|
    And I verify "disposition" single select drop down value
      | Directed to DMAS|
      | Resolved|
      | Transferred to LDSS|
    And I verify "pre-HearingReason" multi select drop down value
      | Case Closure - Incomplete CPU Renewal|
      | Case Closure - Incomplete CVIU Renewal|
      | Case Closure - No Longer Eligible|
      | Coverage Period|
      | Coverage Type|
      | Denial - Does Not Meet MAGI Group|
      | Denial - Duplicate Application/Coverage|
      | Denial - Immigration Status|
      | Denial - Incomplete/Failure to Provide|
      | Denial - Over Income|
      | Untimely Processing|
    And I verify "caseStatus" single select drop down value
      | Existing Coverage|
      | New Application|
      | Renewal Application|
    Then I verify text box Date and Time field value and error message for following fields
      |VaCMS Case ID|
      |Application Id|
      |Appointment Time|
      |Appointment Date|
    And I will update the following information in edit task page
      | status                  |Complete|
      | assignee                |Service TesterTwo|
      | priority                |2 |
      | taskInfo                |CP-19376-02|
      | reasonForEdit           |Corrected Data Entry|
      | externalCaseId          |123456789|
      | externalApplicationId   |DRfgtyh76|
      | actionTaken             |Redetermined Case|
      | preHearingReason        |Coverage Period|
      | caseStatus              |Existing Coverage|
      | preHearingOutcome       |Error(s) Found|
      | appointmentTime         |03:28 PM|
      | appointmentDate         |today|
      | disposition             |Transferred to LDSS|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-26134 @CP-26134-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Verify Application id in task search parameter as well as search result
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Link Correspondence" task page
    And I will provide following information before creating task
      | externalApplicationId   |random|
    And I click on save button in create task page
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      |advanceSearch |true|
      |applicationId |getFromCreatePage|
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify task search result has application id which we passed in search parameter
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | externalApplicationId   |DRfgtyh76|
      | actionTakenSingle       |Linked Correspondence to SR|
      | disposition             |Link Complete|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-18021 @CP-18021-03 @CP-33898 @CP-33898-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Validate Report of Newborn task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Report of Newborn" task page
    And I will provide following information before creating task
      | taskInfo   | Test CP-18021-03  |
      | channel    | Web Chat          |
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    Then I select records count in pagination dropdown as "show 50" in "Work Queue" page
    Then I verify view pagination "show 50" in "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status    |Complete|
      | channel   ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "CHANNEL "
    And I verify task mandatory fields error message "REASON FOR EDIT"
    Then I verify "channel" single select drop down value
      | Fax        |
      | Phone      |
      | Web Chat   |
      | Web Portal |
    And I will update the following information in edit task page
      | status            | Complete|
      | assignee          | Service TesterTwo|
      | priority          | 4 |
      | taskInfo          | CP-18021-03 edit info |
      | reasonForEdit     | Corrected Data Entry|
      | channel           | Web Portal |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    #And Close the soft assertions

  @CP-19385 @CP-19385-04 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Validate Incomplete Contact Record task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Incomplete Contact Record" task page
    And I will provide following information before creating task
      | taskInfo           | Test CP-19385-03 Create |
      | externalCaseId     | 19385123                |
      | externalConsumerID | 78919385                |
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
    And I verify task mandatory fields error message "REASON FOR EDIT"
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
    And I will update the following information in edit task page
      | status             | Complete |
      | assignee           | Service TesterTwo|
      | priority           | 4 |
      | taskInfo           | CP-19385-03 edit info |
      | reasonForEdit      | Corrected Data Entry|
      | externalCaseId     | 23456 |
      | actionTaken        | Completed Contact Record |
      | externalConsumerID | 35565 |
      | disposition        | Resolved |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions