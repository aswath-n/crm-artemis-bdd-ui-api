Feature: Task Create/View/Edit Validation for CoverVA

  @CP-18032 @CP-18032-03 @CP-19568 @CP-19568-01 @CP-19361 @CP-19361-02 @CP-19569 @CP-19569-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Validate Hospital Authorized Representative Request task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Hospital Authorized Representative Request" task page
    And I will provide following information before creating task
      | assignee                ||
      | taskInfo                ||
      | contactRecordSingle     | Application Status Inquiry|
      | externalCaseId          ||
      | externalConsumerID      ||
      | actionTaken             ||
      | ARFirstName             | TestDemo|
      | ARLastName              | Tag|
      | ARPhone                 | 1256754389|
      | AREmail                 ||
      | Organization            ||
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority                        ||
      | status                          |Complete|
      | taskInfo                        | asd    |
      | ARFirstName                     ||
      | contactRecordSingle             ||
      | ARLastName                      ||
      | ARPhone                         ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "AR FIRST NAME "
    And I verify task mandatory fields error message "AR LAST NAME "
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify minimum lenght error message "TASK INFORMATION"
    And I verify task mandatory fields error message "PRIORITY"
    And I verify "actionTaken" multi select drop down value
      | Emailed Requested Docs|
      | Escalated to Supervisor|
      | Mailed Requested Docs|
      | None|
      | Provided Case Status|
      | Transferred to LDSS|
      | Uploaded Provided Docs|
    Then I verify "disposition" single select drop down value
      | Resolved|
      | Transferred|
      | Unresolved|
    Then I verify "contactReason" single select drop down value
      | Appeal Status Inquiry|
      | Application Status Inquiry|
      | Document Request|
      | File Appeal|
      | Other|
      | Report Issue|
      | Request Copy of Notices|
      | Translation Services|
      | Unknown|
    Then I verify text box Date and Time field value and error message for following fields
      |VaCMS Case ID|
      |MMIS Member ID|
      |AR Email|
      |AR First Name|
      |AR Last Name|
      |AR Phone|
      |Organization|
    And I will update the following information in edit task page
      | status                  | Complete|
      | assignee                | Service TesterTwo|
      | priority                | 4 |
      | taskInfo                | CP-18032-03|
      | reasonForEdit           |Corrected Data Entry|
      | contactRecordSingle     | Report Issue|
      | externalCaseId          | 123456789|
      | externalConsumerID      | 1234567890|
      | actionTaken             | Emailed Requested Docs|
      | disposition             | Transferred|
      | ARFirstName             | TGHY|
      | ARLastName              | BHGT|
      | ARPhone                 | 1256754389|
      | AREmail                 | Tagdemo@gmail.com|
      | Organization            | TAG|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-19122 @CP-19122-03 @CP-34498 @CP-34498-11 @CP-38195 @CP-38195-02-08 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal @ruslan
  Scenario: Validate DMAS to CPU Escalation task edit/view page
    When I will get the Authentication token for "CoverVA" in "Tenant Manager"
    And I initiated Business Unit By Project ID via API "8166"
    Then I can verify Business Unit get API response will be "success"
    Then I get the business unit data from TM
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "DMAS to CPU Escalation" task page
    And I will provide following information before creating task
      | businessUnitAssigneeTo| CPU New Application|
      | assignee              | Service TesterTwo  |
      | taskInfo              ||
      | contactRecordSingle   | Consumer Complaint |
      | externalCaseId        ||
      | externalConsumerID    ||
      | externalApplicationId | random             |
      | actionTakenSingle     ||
      | documentDueDate       | today              |
      | cpCRID                ||
      | cpSRID                ||
      | cpTaskID              ||
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Complete |
      | businessUnitAssigneeTo||
      | assignee              ||
      | documentDueDate       ||
      | contactRecordSingle   ||
      | externalApplicationId ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "DUE DATE "
    And I verify task mandatory fields error message "APPLICATION ID "
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
    And I will update the following information in edit task page
      | status                | Complete                    |
      | businessUnitAssigneeTo| EscalationBU                |
      | assignee              | Service TesterTwo           |
      | priority              | 2                           |
      | taskInfo              | CP-19122-03                 |
      | reasonForEdit         | Corrected Data Entry        |
      | contactRecordSingle   | HIPPA                       |
      | externalCaseId        | 123456789                   |
      | externalConsumerID    | 1234467890                  |
      | externalApplicationId | 2uusr2345                   |
      | actionTakenSingle     | Escalated to CoverVA (DMAS) |
      | documentDueDate       | 07/22/2024                  |
      | cpCRID                | 123456                      |
      | cpSRID                | 123456                      |
      | cpTaskID              | 123456                      |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And Close the soft assertions

  @CP-19148 @CP-19148-03 @CP-34498 @CP-34498-09 @CP-38195 @CP-38195-02-07 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal @ruslan
  Scenario: Validate DMAS to CVCC Escalation task edit/view page
    When I will get the Authentication token for "CoverVA" in "Tenant Manager"
    And I initiated Business Unit By Project ID via API "8166"
    Then I can verify Business Unit get API response will be "success"
    Then I get the business unit data from TM
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "DMAS to CVCC Escalation" task page
    And I will provide following information before creating task
      | businessUnitAssigneeTo| EscalationBU           |
      | assignee              | Service TesterTwo      |
      | taskInfo              ||
      | contactRecordSingle   | Customer Service Issue |
      | externalCaseId        ||
      | externalConsumerID    ||
      | externalApplicationId | random                 |
      | actionTakenSingle     ||
      | documentDueDate       | today                  |
      | cpCRID                ||
      | cpSRID                ||
      | cpTaskID              ||
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Complete |
      | businessUnitAssigneeTo ||
      | assignee              ||
      | documentDueDate       ||
      | contactRecordSingle   ||
      | externalApplicationId ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "DUE DATE "
    And I verify task mandatory fields error message "APPLICATION ID "
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
    And I will update the following information in edit task page
      | status                | Complete                    |
      | businessUnitAssigneeTo| EscalationBU                |
      | assignee              | Service TesterTwo           |
      | priority              | 3                           |
      | taskInfo              | CP-19148-03                 |
      | reasonForEdit         | Corrected Data Entry        |
      | contactRecordSingle   | Other                       |
      | externalCaseId        | 123456789                   |
      | externalConsumerID    | 1234467890                  |
      | externalApplicationId | 2uusr2345                   |
      | actionTakenSingle     | Escalated to CoverVA (DMAS) |
      | documentDueDate       | 07/22/2024                  |
      | cpCRID                | 123456                      |
      | cpSRID                | 123456                      |
      | cpTaskID              | 123456                      |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And Close the soft assertions

  @CP-18030 @CP-18030-03 @CP-19567 @CP-19567-02 @CP-19566 @CP-19566-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Validate LDSS Communication Form task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "LDSS Communication Form" task page
    And I will provide following information before creating task
      | assignee                ||
      | taskInfo                ||
      | externalConsumerID      ||
      | externalCaseId          ||
      | externalApplicationId   | random|
      | actionTaken             ||
      | locality                | Accomack|
      | requestType             | Request to Assign Pending App or Case to LDSS|
      | issueType               ||
      | caseWorkerFirstName     | Test|
      | caseWorkerLastName      | Demo|
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | externalApplicationId   ||
      | locality                ||
      | requestType             ||
      | caseWorkerFirstName     ||
      | caseWorkerLastName      ||
    And I click on save button on task edit page
    And I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify task mandatory fields error message "LOCALITY "
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "REQUEST TYPE "
#    And I verify task mandatory fields error message "CASE WORKER FIRST NAME "
#    And I verify task mandatory fields error message "CASE WORKER LAST NAME "
    And I verify "actionTaken" multi select drop down value
      | Emailed Requested Docs|
      | Enrollment Corrected|
      | Escalated to Supervisor|
      | Generated Manual OB Corr|
      | Mailed Requested Docs|
      | None|
      | Submitted Change|
      | Transferred to CPU|
      | Updated Application|
      | Updated Case|
    And I verify "issueType" single select drop down value
      | App or Case assigned to LDSS while pending (CPU Error)|
      | App Sent to Wrong LDSS (CPU Error)|
      | Application/Case Incomplete (CPU Error)|
      | Completed Case not Assigned to LDSS (CPU Error)|
      | Determination Incorrect (CPU Error)|
      | Forms not uploaded to DMIS (CPU Error)|
      | LDSS Comm Form Error (CPU Error)|
      | MMIS Enrollment Incomplete (CPU Error)|
      | MMIS Enrollment Incorrect (CPU Error)|
      | Pending case/app not assigned to the locality (CPU Error)|
      | Review Date not updated in VaCMS (CPU Error)|
      | VaCMS Error by CV (CPU Error)|
    And I verify "disposition" single select drop down value
      | Resolved|
      | Transferred|
      | Unresolved|
    And I verify "requestType" single select drop down value
      | Report case issues|
      | Request to Assign Pending App or Case to LDSS|
      | Request to Assign Pending App or Case to LDSS due to a SNAP app|
      | Update Information on Case or Other info|
    And I verify "locality" single select drop down value
      | Accomack|
      | Albemarle|
      | Alexandria|
      | Alleghany|
      | Amelia|
      | Amherst|
      | Appomattox|
      | Arlington|
      | Augusta|
      | Bath|
      | Bedford County|
      | Bland|
      | Botetourt|
      | Bristol|
      | Brunswick|
      | Buchanan|
      | Buckingham|
      | Buena Vista|
      | Campbell|
      | Caroline|
      | Carroll|
      | Charles City|
      | Charlotte|
      | Charlottesville|
      | Chesapeake|
      | Chesterfield|
      | Clarke|
      | Clifton Forge|
      | Colonial Heights|
      | CoverVA|
      | Covington|
      | Craig|
      | Culpeper|
      | Cumberland|
      | Danville|
      | Dickenson|
      | Dinwiddie|
      | Emporia|
      | Essex|
      | Fairfax City|
      | Fairfax County|
      | Falls Church|
      | Fauquier|
      | Floyd|
      | Fluvanna|
      | Franklin City|
      | Franklin County|
      | Frederick|
      | Fredericksburg|
      | Galax|
      | Giles|
      | Gloucester|
      | Goochland|
      | Grayson|
      | Greene|
      | Greensville|
      | Halifax|
      | Hampton|
      | Hanover|
      | Harrisonburg|
      | Henrico|
      | Henry|
      | Highland|
      | Hopewell|
      | Isle of Wright|
      | James City Co.|
      | King & Queen|
      | King George|
      | King William|
      | Lancaster|
      | Lee|
      | Lexington|
      | Loudoun|
      | Louisa|
      | Lunenburg|
      | Lynchburg|
      | Madison|
      | Manassas City|
      | Manassas Park|
      | Martinsville|
      | Mathews|
      | Mecklenburg|
      | Middlesex|
      | Montgomery|
      | Nelson|
      | New Kent|
      | Newport News|
      | Norfolk|
      | Northampton|
      | Northumberland|
      | Norton|
      | Nottoway|
      | Orange|
      | Page|
      | Patrick|
      | Petersburg|
      | Pittsylvania|
      | Poquoson|
      | Portsmouth|
      | Powhatan|
      | Prince Edward|
      | Prince George|
      | Prince William|
      | Pulaski|
      | Radford|
      | Rappahannock|
      | Richmond City|
      | Richmond County|
      | Roanoke|
      | Roanoke County|
      | Rockbridge|
      | Rockingham|
      | Russell|
      | Salem|
      | Scott|
      | Shenandoah|
      | Smyth|
      | Southampton|
      | Spotsylvania|
      | Stafford |
      | Staunton|
      | Suffolk|
      | Surry|
      | Sussex|
      | Tazewell|
      | Virginia Beach|
      | Warren|
      | Washington|
      | Waynesboro|
      | Westmoreland|
      | Williamsburg|
      | Winchester|
      | Wise|
      | Wythe|
      | York|
    Then I verify text box Date and Time field value and error message for following fields
      |VaCMS Case ID|
      |MMIS Member ID|
      |Application Id|
      |Case Worker First Name|
      |Case Worker Last Name|
    And I will update the following information in edit task page
      | status                  | Complete|
      | assignee                | Service TesterTwo|
      | priority                | 2 |
      | taskInfo                | CP-18030-03|
      | reasonForEdit           |Corrected Data Entry|
      | externalConsumerID      | 1234567890|
      | externalCaseId          | 123456789|
      | externalApplicationId   | VGBHY6786|
      | actionTaken             | Mailed Requested Docs|
      | locality                | Westmoreland|
      | requestType             | Report case issues|
      | issueType               | Determination Incorrect (CPU Error)|
      | caseWorkerFirstName     | Test Dali|
      | caseWorkerLastName      | Demo|
      | disposition             | Resolved|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And Close the soft assertions

  @CP-19433 @CP-19433-03 @CP-18025 @CP-18025-03 @CP-19526 @CP-19526-02 @CP-19528 @CP-19528-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate HPE Final Decision task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "HPE Final Decision" task page
    And I will provide following information before creating task
      | assignee                ||
      | taskInfo                ||
      | externalConsumerID      ||
      | actionTaken             ||
      | outcome                 |Manual|
      | contactRecordSingle     |Eligibility Status|
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                     |Complete|
      | outcome                    ||
      | contactRecordSingle        ||
    And I click on save button on task edit page
    And I verify task mandatory fields error message "REASON FOR EDIT"
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
      | Final Approval  |
      | Final Denial    |
      | Manual          |
      | Pending Research|
    And I verify "contactReason" single select drop down value
      | Eligibility Status|
      | Enrollment Status|
      | Other|
    Then I verify text box Date and Time field value and error message for following fields
      |MMIS Member ID|
    And I will update the following information in edit task page
      | status                  | Complete            |
      | assignee                | Service TesterTwo   |
      | priority                | 2                   |
      | taskInfo                | CP-19526            |
      | reasonForEdit           |Corrected Data Entry |
      | externalConsumerID      |1234567890           |
      | actionTaken             |Corrected Exception  |
      | outcome                 |Pending Research     |
      | contactRecordSingle     |Eligibility Status   |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And Close the soft assertions

  @CP-19349 @CP-19349-05 @CP-18046 @CP-18046-03 @CP-19563 @CP-19563-02 @CP-19565 @CP-19565-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate Translation Request task edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Translation Request" task page
    And I will provide following information before creating task
      | assignee                 ||
      | taskInfo                 ||
      | externalConsumerID       ||
      | externalCaseId           ||
      | actionTaken              ||
      | informationType          |Renewal Packet|
      | language                 ||
      | dateTranslationEscalated ||
      | dateTranslationReceived  ||
      | dateTranslationMailed    ||
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                     |Complete|
      | informationType            ||
    And I click on save button on task edit page
    And I verify task mandatory fields error message "REASON FOR EDIT"
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
      | Extended Pend Letter         |
      | Notice of Action (NOA)       |
      | Other                        |
      | Renewal Packet               |
      | Verification Check List (VCL)|
    And I verify "preferredLanguage" single select drop down value
      | Amharic  |
      | Arabic |
      | Bassa  |
      | Bengali |
      | Chinese (Traditional)  |
      | English |
      | Farsi  |
      | French |
      | German  |
      | Hindi |
      | Ibo  |
      | Korean |
      | Other |
      | Russian |
      | Spanish |
      | Tagalog  |
      | Urdu |
      | Vietnamese |
      | Yoruba |
    Then I verify text box Date and Time field value and error message for following fields
      |VaCMS Case ID|
      |MMIS Member ID|
      |Date Translation Escalated|
      |Date Translation Received|
      |Date Translation Mailed|
    And I will update the following information in edit task page
      | status                   | Complete            |
      | assignee                 | Service TesterTwo   |
      | priority                 | 2                   |
      | taskInfo                 | CP-19565            |
      | reasonForEdit            |Corrected Data Entry |
      | externalConsumerID       |1234567890                     |
      | externalCaseId           |123456789                      |
      | actionTaken              |Escalated to Translation Group |
      | informationType          |Renewal Packet                 |
      | language                 |Hindi                          |
      | dateTranslationEscalated |+1                             |
      | dateTranslationReceived  |today                          |
      | dateTranslationMailed    |-1                             |
      | disposition              | Transferred                   |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And Close the soft assertions

  @CP-19276 @CP-19276-03 @CP-19195 @CP-19195-03 @CP-19531 @CP-19531-02 @CP-19533 @CP-19533-02 @CP-37763 @CP-37763-01 @CP-38195 @CP-38195-06 @priyal @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate Appeal Remand task edit/view page and Verify the details on UI and API
    When I will get the Authentication token for "CoverVA" in "Tenant Manager"
    And I initiated Business Unit By Project ID via API "8166"
    Then I can verify Business Unit get API response will be "success"
    Then I get the business unit data from TM
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Appeal Remand" task page
    And I will provide following information before creating task
      | taskInfo                ||
      | assignee                ||
      | externalCaseId          ||
      | externalApplicationId   |Test19365|
      | remandReason            |Other|
      | eligibilityDecision     |Approved |
      | remandCompletionDate    ||
      | remandDueDate           |today|
      | receivedDate            |-1|
      | businessUnitAssigneeTo  ||
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                     |Complete|
      | externalApplicationId      ||
      | remandReason               ||
      | eligibilityDecision        ||
      | remandCompletionDate       ||
      | remandDueDate              ||
      | receivedDate               ||
    And I click on save button on task edit page
    And I verify "ASSIGNEE BUSINESS UNIT" filed is not mandatory for the task
    And I verify task mandatory fields error message "REASON FOR EDIT"
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "RECEIVED DATE "
    And I verify task mandatory fields error message "REMAND REASON "
    And I verify task mandatory fields error message "REMAND DUE DATE "
    And I verify task mandatory fields error message "ELIGIBILITY DECISION "
    And I verify "remandReason" multi select drop down value
      | Case Correction - Incorrect Alien Status   |
      | Case Correction - Incorrect Document Processing |
      | Case Correction - Incorrect Income Evaluation |
      | Case Correction - Missing VCL|
      | Other|
      | Timeliness |
    And I verify "eligibilityDecision" single select drop down value
      | Approved|
      | Denied  |
      | Pending |
    And I verify "disposition" single select drop down value
      | Approved|
      | Denied  |
    Then I verify text box Date and Time field value and error message for following fields
      |VaCMS Case ID|
      |Application Id|
      |Received Date |
      |Remand Due Date|
      |Remand Completion Date |
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And Get the task type information of "Appeal Remand" for project "CoverVA"
    Given I initiated active business units and teams api
    When I provide taskTypeId for active business units and teams "singleVal"
    And I can run active bu and teams api
    Then I get business unit names from business units and teams response api
    Then I verify Assignee Business Unit dropdown displays only BU that are associated to the task type
    And I will update the following information in edit task page
      | status                   |Complete             |
      | assignee                 |Service TesterTwo    |
      | priority                 |2                    |
      | taskInfo                 |CP-19565             |
      | reasonForEdit            |Corrected Data Entry |
      | externalApplicationId    |123456789            |
      | externalCaseId           |123456789            |
      | remandReason             |Timeliness           |
      | eligibilityDecision      |Approved             |
      | remandCompletionDate     |+1                   |
      | remandDueDate            |today                |
      | receivedDate             |-1                   |
      | disposition              |Denied               |
      | businessUnitAssigneeTo   |CPU New Application  |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And Close the soft assertions

  @CP-19536 @CP-19536-01 @CP-19309 @CP-19309-03 @CP-19535 @CP-19535-03 @CP-18045 @CP-18045-01 @CP-39671-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @elvin @vidya
  Scenario: Verification Edit/View functionality for Authorized Representative Designation task and data saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Authorized Representative Designation" task page
    And I will provide following information before creating task
      | externalCaseId       ||
      | externalConsumerID   ||
      | actionTaken          ||
      | channel              | Phone             |
      | ARFirstName          | Alan              |
      | ARLastName           | Smith             |
      | ARPhone              | 7202022002        |
      | AREmail              ||
      | ARAddressLine1       | 11 Main St        |
      | ARAddressLine2       | Apt 1             |
      | ARCity               | Denver            |
      | ARState              | Colorado          |
      | ARZipCode            | 80001             |
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | channel        ||
      | ARFirstName    ||
      | ARLastName     ||
      | ARPhone        ||
      | ARAddressLine1 ||
      | ARCity         ||
      | ARState        ||
      | ARZipCode      ||
    And I click on save button on task edit page
    And I verify task mandatory fields error message "CHANNEL "
    And I verify task mandatory fields error message "AR FIRST NAME "
    And I verify task mandatory fields error message "AR LAST NAME "
    And I verify task mandatory fields error message "AR PHONE "
    And I verify task mandatory fields error message "AR ADDRESS LINE 1 "
    And I verify task mandatory fields error message "AR CITY "
    And I verify task mandatory fields error message "AR STATE "
    And I verify task mandatory fields error message "AR ZIP CODE"
    And I verify "channel" single select drop down value
      | Mail/Fax  |
      | Phone     |
      | VaCMS MWS |
    And I verify "actionTaken" multi select drop down value
      | Escalated to Supervisor |
      | None                    |
      | Submitted Change        |
    And I will update the following information in edit task page
      | status             | Complete                |
      | reasonForEdit      |Corrected Data Entry     |
      | priority           | 1                       |
      | taskInfo           | test                    |
      | externalCaseId     | 112233445               |
      | externalConsumerID | 123456789               |
      | actionTaken        | Escalated to Supervisor |
      | channel            | Mail/Fax                |
      | ARFirstName        | Joshua                  |
      | ARLastName         | Stallon                 |
      | ARPhone            | 7202020000              |
      | AREmail            | test123@mail.com        |
      | ARAddressLine1     | 324 Main St             |
      | ARAddressLine2     | Apt 134                 |
      | ARCity             | Littleton               |
      | ARState            | CO                      |
      | ARZipCode          | 80027                   |
      | disposition        | Resolved                |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    And I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And Close the soft assertions


  @CP-19311 @CP-19311-06 @CP-18044 @CP-18044-03 @CP-19542 @CP-19542-02 @CP-19541 @CP-19541-02 @CP-25708 @CP-25708-01 @CP-39671-06 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @ruslan
  Scenario: Validate CVIU Provider Request task created and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "CVIU Provider Request" task page
    And I will provide following information before creating task
      | taskInfo                ||
      | assignee                ||
      | externalConsumerID      ||
      | externalCaseId          ||
      | actionTaken             ||
      | contactRecordSingle     |Provide Medical Records|
      | facilityName            ||
      | facilityType            ||
      | providerFirstName       ||
      | providerLastName        ||
      | providerPhone           ||
      | providerEmail           ||
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      |status             |Complete|
      |contactRecordSingle||
    And I click on save button on task edit page
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify "disposition" single select drop down value
      | Resolved    |
      | Transferred |
      | Unresolved  |
    And I verify "actionTaken" multi select drop down value
      | Confirm Coverage  |
      | Other |
    And I verify "contactReason" single select drop down value
      | Other    |
      | Provide Medical Records |
    And I verify "facilityType" single select drop down value
      | Department of Corrections    |
      | Department of Juvenile Justice |
      | Local Jail  |
      | Regional Jail|
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
      |VaCMS Case ID         |
      |MMIS Member ID        |
      |Provider First Name   |
      |Provider Last Name    |
      |Provider Phone        |
      |Provider Email        |
    And I will update the following information in edit task page
      | reasonForEdit           |Corrected Data Entry|
      | taskInfo                | Test CP-19311    |
      | assignee                | Service TesterTwo|
      | externalConsumerID      | 1234567890       |
      | externalCaseId          | 123456789        |
      | actionTaken             | Confirm Coverage |
      | contactRecordSingle     |Provide Medical Records|
      | facilityType            |Department of Corrections|
      | facilityName            |Augusta Correctional Center|
      | providerFirstName       | Vidya            |
      | providerLastName        | Mithun           |
      | providerPhone           | 9876543210       |
      | providerEmail           | test@gmail.com   |
      | disposition             | Transferred|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions
    
