Feature: Initiate Functionality oF My Task

    #commented tc's due the permission
#  @pc-337 @CP-337-01 @CP-550 @CP-550-02 @CP-138 @CP-138-14 @CP-34852 @CP-34852-01 @CP-34852-02 @aswath @crm-regression @ui-wf
  Scenario: Verification of Escalate state
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I will ensure assignee contains at least one "General" task if not I will create task
    And I will get the index of "General" task and click on initiate button for that
    And I will update the following information in task slider
      | status            |Escalated|
      | noteValue         | Test SliderNotes   |
    And I click on save in Task Slider
    Then I verify navigate back to My Task or Work Queue page
    And I click on Work Queue tab
    And I navigate to newly created task by clicking on TaskID column header
    Then I will verify Escalated task is sits in work queue page
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then I verify saved task note from slider
    And Close the soft assertions

     #commented tc's due the permission
#  @CP-138 @CP-138-01 @CP-28998 @CP-28998-05 @aswath @crm-regression @ui-wf
  Scenario: Verify user cannot work on mutliple active task
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I will ensure assignee contains at least one "General" task if not I will create task
    And I will get the index of "General" task and click on initiate button for that
    And I Verify fields on the Task slider
    And I verify the field values on the Task slider
    And I verify Assignee is unchanged
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I verify the priority in dashboard
    When I navigate to "My Task" page
    And I click on initiate randomly
    And I verify the error message on working single active task
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I verify Task status drop down values in slider
      | In Progress |
      | Complete    |
      | Escalated   |
      | Cancel      |
    And verify task note field length in task slider
    And I will update the following information in task slider
      | status      | Complete    |
      | disposition | User closed |
    And I click on save in Task Slider
    Then I verify task save success message
    Then I verify navigate back to My Task or Work Queue page
    Then I verify Complete or Cancelled task is NOT in My Tasks queue
    And Close the soft assertions

  @CP-138 @CP-138-13 @CP-18090 @CP-18090-01 @CP-134 @CP-134-01 @CP-34852 @CP-34852-02 @kamil @vidya @aswath @crm-regression @ui-wf
  Scenario: Verify task slider unchanged functionality
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I will ensure assignee contains at least one "General" task if not I will create task
    And I will get the index of "General" task and click on initiate button for that
    And I will update the following information in task slider
      | status      | Complete    |
      | disposition | User closed |
    And I click on cancel button on task slider
    Then I verify warning is displayed upon clicking Cancel button on Task slider
    And I click on cancel button on warning message
    Then I verify the data in slider is unchanged
    And I will update the following information in task slider
      | status | Cancel |
    And I click on save in Task Slider
    Then I verify task mandatory fields error message "REASON FOR CANCEL"
    And I verify "reasonForCancel" single select drop down value in slider
      | Created Incorrectly     |
      | Duplicate Task          |
      | Task No Longer Required |
    And I select value in "REASON FOR CANCEL" drop down
    And I click on save in Task Slider
    Then I verify navigate back to My Task or Work Queue page
    Then I verify Complete or Cancelled task is NOT in My Tasks queue
    And Close the soft assertions

  @CP-138 @CP-138-15 @aswath @crm-regression @ui-wf @crm-regression
  Scenario: Verify Link Task To 'Consumer on Case' before Save
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When I navigate to "General" task page
    And I select task type as "General" priority as "" assignee as "Service AccountEight" and I enter task info as "Task Info"
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Selmer" and Last Name as "Lang"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    Then I see radio button is associated with a Case or Consumer
    When I select Consumer radio button
    Then I see Link Record Consumer button get displayed
    When I click on Link Record Consumer button
    Then I verify task is linked with ConsumerID and CaseID
    And I click on save button in create task page
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    And I verify the case ID and consumer ID in the task slider
    And I click on save in Task Slider
    And Close the soft assertions

  @CP-138 @CP-138-16 @aswath @crm-regression @ui-wf
  Scenario Outline: Verify task status in progress as when initated the task slider
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I ensure My task page has at least one task with status other than "<status>" and I initate task
    And I verify the task status in task slider is in progress
    And I will update the following information in task slider
      | status | Complete |
    And I click on save in Task Slider
    And Close the soft assertions
    Examples:
      | status    |
      | Open      |
      | Created   |
      | Escalated |
      | OnHold    |

    #below scripts are for CP-137
  #AC 1.0 and 1.1 and 4.0
  @CP-137 @CP-137-01 @CP-5139 @CP-5139-01 @CP-34852 @CP-34852-03 @CP-34852-02 @vidya @crm-regression @ui-wf
  Scenario: Verify Status is changed to open upon clicking Save for task
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I will ensure assignee contains at least one "General" task if not I will create task
    And I will get the index of "General" task and click on initiate button for that
    Then I verify the field values on View Task details
    And I verify Assignee is unchanged
    When I enter text in task note field
    And I click on save in Task Slider
    Then I verify Success message is displayed for task
    Then I Verify user is navigate back to My task page
    Then I check first record task status is "Open"
    And Close the soft assertions

  #AC 1.0
  @CP-145 @CP-145-03 @CP-138 @CP-138-02 @vidya @crm-regression @ui-wf
  Scenario: Verify Status is changed to open upon clicking cancel for task
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When I navigate to "General" task page
    And I select task type as "General" priority as "" assignee as "Service" and I enter task info as "Task Info"
    And I click on save button in create task page
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    When I enter task notes "test Notes" and select task status as "Complete"
    And I verify Assignee is unchanged
    And I click on cancel button on task slider
    Then I verify warning is displayed upon clicking Cancel button on Task slider
    And I click on cancel button on warning message
    Then I verify user remains on same page and the data captured will not be cleared
    When I click on cancel button on task slider
    And I click on continue on task details warning window
    And I click task id to get the results in descending order
    Then I verify user navigate back to previous UI and no updates will be saved
    Then I check first record task status is "Open"
    And Close the soft assertions

  @CP-550 @CP-550-01 @vidya @crm-regression @ui-wf
  Scenario: Verify Escalated task is presented in work queue for Service Account two user
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I will ensure assignee contains at least one "Inbound Task" task if not I will create task
    And I will get the index of "Inbound Task" task and click on initiate button for that
    And I will update the following information in task slider
      | status | Escalated |
    And I click on save in Task Slider
    And I click on Work Queue tab
    Then I will verify all record status are not "Escalated"
    And Close the soft assertions

  @CP-10852 @CP-10852-04 @CP-10196 @CP-10196-1 @CP-10196-2 @ui-wf @crm-regression @vidya
  Scenario: Validate Disposition field value when select from task slider for BaseLine project
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I will ensure assignee contains at least one "General Two" task if not I will create task
    And I click task id to get the results in descending order
    And I will get the index of "General Two" task and click on initiate button for that
    Then Verify task slider is displayed
    And I will update the following information in task slider
      | status | Complete |
    And I click on save in Task Slider
    And Verify the system displays an Error Message: "DISPOSITION is required and cannot be left blank"
    Then Verify the Disposition options are visible
      | User closed          |
      | Consumer reached     |
      | Consumer not reached |
    And I update the dispostion field in task slider as "Consumer reached"
    And I click on save in Task Slider
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then I verify view page has disposition value
    And Close the soft assertions

  @CP-10196 @CP-10196-3 @kamil @crm-regression @ui-wf
  Scenario: Verify Disposition Field NOT Visible
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I will ensure assignee contains at least one "General Two" task if not I will create task
    And I click task id to get the results in descending order
    And I will get the index of "General Two" task and click on initiate button for that
    And Update the task slider status and verify disposition
      | In Progress |
      | Escalated   |
      | Cancel      |
    And I click on save in Task Slider
    And Close the soft assertions

  @CP-11764 @CP-11764-02 @vidya @crm-regression @ui-wf
  Scenario: My Task, verification of task slider is open inside task active contact page
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When I navigate to "Calendar Day" task page
    And I will provide following information before creating task
      | taskInfo | Test CP-11764        |
      | assignee | Service AccountEight |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Luis" and Last Name as "Janey"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I select the record whose Case ID Blank
    When I click on Link Record Consumer button
    And I click on save button in create task page
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I will get the index of "Calendar day " task and click on initiate button for that
    Then Verify task slider is displayed
    And I verify Active contact screen displayed with initiate call
    And I verify internal "Consumer" id is search by default
    And I click on save in Task Slider
    Then I verify error message displayed and user remains on active contact screen and task slider closed
    And I link the contact record to an existing Case or Consumer Profile
    When  I should see following dropdown options for "contact reason" field displayed
      | Complaint - Customer Service |
    And  I choose "Escalated" option for Contact Action field
    And I enter additional comments "First saved reason and action"
    Then I click save button for reason and action
    And I click on the Contact Channel Type "Phone"
    And I enter contact phone number "3334445566"
    Then I select "Program A" as contact program type
    Then I click on the contact dispotions "Complete"
    When I click on the close button on the Header
    And I scroll the Page to the Bottom
    And I click on the Close button in the bottom
    Then I verify home dashboard page is displayed
    And Close the soft assertions

  @CP-19608 @CP-19608-01 @CP-20436 @CP-20436-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate CaseId and Application ID are not displayed in task slider when we initiate from My task/work queue
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to "Inbound Document" task page
    And I will provide following information before creating task
      | assignee              | Service TesterTwo |
      | externalApplicationId | random            |
      | InbDocType            | Complaint         |
      | program               | EAP               |
    And I click on save button in create task page
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    And Verify task slider is displayed
    Then I will verify Case Id is removed in task slider
    And I will verify External application id is removed in task slider
    And I click on save in Task Slider
    Then I verify navigate back to My Task or Work Queue page
    And Close the soft assertions

  @CP-19608 @CP-19608-02 @CP-20436 @CP-20436-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Validate CaseId and Application ID are not displayed in task slider when we initiate from Task search
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I minimize Genesys popup if populates
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click on initiate button in task search page
    And Verify task slider is displayed
    Then I will verify Case Id is removed in task slider
    And I will verify External application id is removed in task slider
    And I will update the following information in task slider
      | status          | Cancel         |
      | reasonForCancel | Duplicate Task |
    And I click on save in Task Slider
    Then I verify user is navigate back to task search page
    And Close the soft assertions
    Examples:
      | taskId | taskType         |srType| status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || Inbound Document || Open   ||          ||            ||                || false         ||            ||          ||           ||

  @CP-19608 @CP-19608-03 @CP-20436 @CP-20436-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate CaseId and Application ID are not displayed in task slider when we initiate from TSR tab
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with default state for cover-va
    When I click on Create Consumer Button
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Inbound Document" task page
    And I will provide following information before creating task
      | externalApplicationId | T19575    |
      | InbDocType            | Complaint |
      | program               | EAP       |
    And I click on save button in create task page
    When If any In Progress task present then update that to Cancelled
    And I navigate to newly created task in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    Then I will verify Case Id is removed in task slider
    And I will verify External application id is removed in task slider
    And I will update the following information in task slider
      | status          | Cancel         |
      | reasonForCancel | Duplicate Task |
    And I click on save in Task Slider
    And I verify Success message is displayed for task
    Then I Verify user is navigate back to Task and service Request Page
    And Close the soft assertions

  @CP-3996 @CP-3996-05 @CP-18090 @CP-18090-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @elvin
  Scenario Outline: Verify retaining search criteria and results on task search page in CoverVa
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    When If any In Progress task present then update that to Cancelled
    And I click on search button on task search page
    When I click on "StatusDate" column on My Tasks Page to set to descending
    And I click on initiate button in task search page
    And I will update the following information in task slider
      | status          | Cancel         |
      | reasonForCancel | Duplicate Task |
    And I click on save in Task Slider
    When I click on "StatusDate" column on My Tasks Page to set to descending
    Then I will verify task search page retains search criteria "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>"
    And Close the soft assertions
    Examples:
      | taskId | taskType |srType| status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee          | createdBy | createdOn |contactReason|
      ||          || Created || 4        ||            || MMIS           || true          ||            || Service TesterTwo ||           ||

  @CP-19834 @CP-19834-04 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Verification Task Note length on Task slider
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to "Inbound Document" task page
    And I will provide following information before creating task
      | assignee              | Service TesterTwo |
      | taskInfo              | testing CP-19834  |
      | externalApplicationId | random            |
      | InbDocType            | Complaint         |
      | program               | EAP               |
    And I click on save button in create task page
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    And Verify task slider is displayed
    And verify task note field length in task slider
    And I verify the below details are displayed in task slider
    And I will update the following information in task slider
      | status          | Cancel         |
      | reasonForCancel | Duplicate Task |
    And I click on save in Task Slider
    Then I verify user is redirected to work queue page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    Then I verify saved task note from slider
    And Close the soft assertions

 # @CP-21546 @CP-21546-06 @CP-18556 @CP-18556-04 @CP-23430 @CP-23430-04 @priyal @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate process application Application type matches value Pre-Release Application - CVIU
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with default state for cover-va
    When I click on Create Consumer Button
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      | applicationType       | Pre-Release Application - CVIU |
      | externalApplicationId | random                         |
      | myWorkSpaceDate       | -2                             |
      | channel               | CommonHelp |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status            | Complete |
      | actionTakenSingle | Sent NOA |
      | disposition       | Denied   |
    And I click on save in Task Slider
    Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    Then I verify the sr status is updated to close and disposition is set to "Denied"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal_V2"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

 # @CP-21546 @CP-21546-03 @CP-18647 @CP-18647-01 @CP-18649 @CP-18649-01 @CP-18556 @CP-18556-03 @CP-16059 @CP-16059-01 @CP-23430 @CP-23430-05 @ruslan @priyal @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate process application Application type matches value MAGI - CPU
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with default state for cover-va
    When I click on Create Consumer Button
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      | applicationType       | MAGI - CPU |
      | externalApplicationId | random     |
      | myWorkSpaceDate       | +1         |
      | channel               | CommonHelp |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I click on task id on the task slider
    Then I verify should I navigated to view task page
    And I Verify Task slider is collasped
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I will update the following information in task slider
      | status | Complete |
    And I verify "actionTakenSingle" single select drop down value in slider
      | Sent NOA            |
      | Sent VCL            |
      | Transferred to LDSS |
    And I click on save in Task Slider
    Then I verify task mandatory fields error message "ACTION TAKEN"
    And I verify task mandatory fields error message "DISPOSITION"
    And I will update the following information in task slider
      | status            | Complete    |
      | actionTakenSingle | Sent NOA    |
      | disposition       | Auto-Denied |
    And I click on save in Task Slider
    Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    Then I verify the sr status is updated to close and disposition is set to "Auto-Denied"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVAApplicationSRFlow"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions


  @CP-10145 @CP-10145-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan
  Scenario: Associated Screen - View Service Request Details for Cover-VA
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType       | MAGI - CPU |
      | applicationSignatureDate| today    |
      | applicationReceivedDate | today    |
      | renewalDate           | today      |
      | externalApplicationId | random     |
      | myWorkSpaceDate       | +1         |
      | channel               | CommonHelp |
    And I click on save button in create service request page
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    Then I verify should I navigate to service request details
    And I click on save in Task Slider
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify save task search section is displayed
    And I click on Initiate button
    Then I verify should I navigate to service request details
    And I will update the following information in task slider
      | status            | Complete    |
      | actionTakenSingle | Sent NOA    |
      | disposition       | Auto-Denied |
    And I click on save in Task Slider
  #  Then I verify Success message is displayed for task update
    And Close the soft assertions

  @CP-10145 @CP-10145-02 @CP-25501 @CP-25501-03 @crm-regression @ui-wf @ruslan
  Scenario: Associated Screen - View Service Request Details for BLCRM
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "General Service Request" service request page
    And I click on save button in create service request page
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    Then I verify "task-status" field does not contains value on "edit" page
      | Cancelled |
    And I will update the following information in edit task page
      | assignee | Service AccountOne |
    And I click on save button on task edit page
    When I will click on back arrow on view task page
    When I click on My task tab
    And I click task id to get the results in descending order
    And I click on initiate randomly
    And Verify task slider is displayed
    Then I verify should I navigate to service request details
    And I click on save in Task Slider
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify save task search section is displayed
    And I click on Initiate button
    Then I verify should I navigate to service request details
    And Verify task slider is displayed
    Then I verify "taskStatus" field does not contains value on "task slider" page
      | Cancel |
    And I will update the following information in task slider
      | status      | Complete          |
      | disposition | General SR Closed |
    And I click on save in Task Slider
    And Close the soft assertions

  @CP-10145 @CP-10145-03 @CP-22730 @CP-22730-06 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan @vidya
  Scenario: Associated Screen - View Service Request Details from TSR tab for Cover-VA
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType          | MAGI - CPU |
      | applicationSignatureDate | today   |
      | applicationReceivedDate  | today   |
      | externalApplicationId    | random     |
      | renewalDate              | today      |
      | myWorkSpaceDate          | +1         |
      | channel                  | CommonHelp |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Luisa" and Last Name as "Skilesg"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    When I click on Link Record Consumer button
    And I click on save button in create service request page
  #  Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    When I click case consumer search tab
    When I searched customer have First Name as "Luisa" and Last Name as "Skilesg"
  #  When I click first CRM Case ID in search results on case and consumer search page
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    Then I verify should I navigate to service request details
    And I will update the following information in task slider
      | status            | Complete    |
      | actionTakenSingle | Sent NOA    |
      | disposition       | Auto-Denied |
    And I click on save in Task Slider
  #  Then I verify Success message is displayed for task update
    And Close the soft assertions

  @CP-10145 @CP-10145-04 @crm-regression @ui-wf @ruslan
  Scenario: Associated Screen - View Service Request Details from SRT tab for BLCRM
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When I navigate to "General Service Request" service request page
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "FnEIXno" and Last Name as "LnLaKwh"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    When I select Consumer radio button
    When I click on Link Record Consumer button
    And I click on save button in create service request page
    When If any In Progress task present then update that to Cancelled
    When I click case consumer search tab
    When I searched customer have First Name as "FnEIXno" and Last Name as "LnLaKwh"
    When I click first CRM Case ID in search results on case and consumer search page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    Then I verify should I navigate to service request details
    And I will update the following information in task slider
      | status | Escalated |
    And I click on save in Task Slider
#    Then I verify Success message is displayed for task update
    And Close the soft assertions

  @CP-10145 @CP-10145-05 @CP-22730 @CP-22730-05 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @ruslan
  Scenario:  Associated Screen - View Service Request Details  - NO SR is linked for Cover-VA
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I minimize Genesys popup if populates
    When I navigate to "Process App V1" task page
    And I will provide following information before creating task
      | assignee                  | Service TesterTwo            |
      | applicationType           | Expedited Application - CVIU |
      | externalApplicationId     | random                       |
      | channel                   | Paper                        |
      | myWorkSpaceDate           | today                        |
      | applicationReceivedDate   | today                        |
      | applicationSignatureDate  | today                        |
      | noOfApplicantsInHousehold | 1                            |
      | applicationStatus         | Data Collection              |
      | decisionSource            | VaCMS                        |
      | noOfApprovedApplicants    | 2                            |
      | missingInfoRequired       | No                           |
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    Then I verify the field values on View Task details
    And I will update the following information in task slider
      | status            | Cancel         |
      | reasonForCancel   | Duplicate Task |
      | actionTakenSingle | Sent NOA       |
    And I click on save in Task Slider
#    Then I verify Success message is displayed for task update
    And Close the soft assertions

  @CP-10145 @CP-10145-06 @crm-regression @ui-wf @ruslan
  Scenario:  Associated Screen - View Service Request Details  - NO SR is linked for BLCRM
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When I navigate to "General Three" task page
    And I will provide following information before creating task
      | assignee | Service AccountOne |
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    Then I verify the field values on View Task details
    And I will update the following information in task slider
      | status          | Cancel         |
      | reasonForCancel | Duplicate Task |
    And I click on save in Task Slider
#    Then I verify Success message is displayed for task updated
    And Close the soft assertions

 # @CP-22730 @CP-22730-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Validate Conditional Action Taken + Disposition Combinations for Process Application Task
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I minimize Genesys popup if populates
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status            | Complete |
      | actionTakenSingle | Sent VCL |
    And I verify "disposition" single select drop down value in slider
      | Pending MI |
    And I will update the following information in task slider
      | actionTakenSingle | Transferred to LDSS |
    And I verify "disposition" single select drop down value in slider
      | Referred |
    And I will update the following information in task slider
      | actionTakenSingle | Sent NOA |
    And I verify "disposition" single select drop down value in slider
      | Approved      |
      | Auto-Approved |
      | Auto-Denied   |
      | Denied        |
    And I will update the following information in task slider
      | disposition | Auto-Denied |
    And I click on save in Task Slider
    Then I verify task save success message
    And Close the soft assertions
    Examples:
      | taskId | taskType            |srType| status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || Process Application || Created || 1        ||            ||                || false         ||            ||          ||           ||
   # @CP-22730 @CP-22730-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Validate Conditional Action Taken + Disposition Combinations for Process App V1 Task
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I minimize Genesys popup if populates
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status            | Complete |
      | actionTakenSingle | Sent VCL |
    And I verify "disposition" single select drop down value in slider
      | Pending MI |
    And I will update the following information in task slider
      | actionTakenSingle | Transferred to LDSS |
    And I verify "disposition" single select drop down value in slider
      | Referred |
    And I will update the following information in task slider
      | actionTakenSingle | Sent NOA |
    And I verify "disposition" single select drop down value in slider
      | Approved      |
      | Auto-Approved |
      | Auto-Denied   |
      | Denied        |
    And I will update the following information in task slider
      | disposition | Auto-Denied |
    And I click on save in Task Slider
    Then I verify task save success message
    And Close the soft assertions
    Examples:
      | taskId | taskType       |srType| status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || Process App V1 || Created || 3        ||            ||                || false         ||            ||          ||           ||

  # @CP-23433 @CP-23433-01 @CP-19443 @CP-19443-08 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate process application Application type matches value Pre-Release Application - CVIU
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with default state for cover-va
    When I click on Create Consumer Button
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType       | Renewal Application CVIU |
      | applicationSignatureDate | today                 |
      | applicationReceivedDate  | today                 |
      | externalApplicationId | random                   |
      | myWorkSpaceDate       | -2                       |
      | channel               | CommonHelp               |
      | facilityName          | Bristol City Jail        |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status            | Complete |
      | actionTakenSingle | Sent NOA |
      | disposition       | Denied   |
    And I click on save in Task Slider
    Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    Then I verify the sr status is updated to close and disposition is set to "Denied"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal_V2"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

 # @CP-23433 @CP-23433-02 @CP-19443 @CP-19443-07 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate process application Application type matches value MAGI - CPU
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with default state for cover-va
    When I click on Create Consumer Button
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType       | MAGI - PW  |
      | applicationSignatureDate | today   |
      | applicationReceivedDate  | today   |
      | externalApplicationId | random     |
      | myWorkSpaceDate       | +1         |
      | channel               | CommonHelp |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I click on task id on the task slider
    Then I verify should I navigated to view task page
    And I Verify Task slider is collasped
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I will update the following information in task slider
      | status            | Complete      |
      | actionTakenSingle | Sent NOA      |
      | disposition       | Auto-Approved |
    And I click on save in Task Slider
    Then I verify task save success message
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    Then I verify the sr status is updated to close and disposition is set to "Auto-Approved"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal_V2"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

