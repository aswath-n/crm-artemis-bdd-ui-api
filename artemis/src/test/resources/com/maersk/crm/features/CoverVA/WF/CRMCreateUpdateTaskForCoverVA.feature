Feature: Task End To End Validation for CoverVA

  @CP-18026 @CP-18026-02 @CP-15323 @CP-15323-09 @CP-25316 @CP-25316-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Validate Outbound Call task created and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Outbound Call" task page
    And I will provide following information before creating task
      | taskInfo                  | Test CP-18026-02&$%|
      | assignee                  | Service TesterTwo|
      | externalConsumerID        | 1234567890|
      | externalCaseId            | 123456789|
      | externalApplicationId     | random|
      | name                      | Priyal|
      | preferredCallBackDate     | today|
      | preferredCallBackTime     | 03:28 PM|
      | preferredPhone            | 1234567890|
      | language                  | Hindi|
      | contactRecordSingle       | Other|
      | actionTaken               | Reached Consumer|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I verify Task Notes field is not displayed on Edit Task page
    And I will update the following information in edit task page
      | status                    |Cancelled|
      | reasonForCancel           |Created Incorrectly|
      | assignee                  ||
      | taskInfo                  |Test CP-18026-02&$%|
      | contactRecordSingle       |Other|
      | externalCaseId            ||
      | externalConsumerID        ||
      | externalApplicationId     |Forg564rf|
      | actionTaken               ||
      | preferredCallBackDate     ||
      | preferredCallBackTime     ||
      | preferredPhone            |3456787657|
      | language                  ||
      | name                      |Garg|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    Then I Verify Task Notes field is not displayed on View Task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-19376 @CP-19376-02 @CP-19545 @CP-19545-02 @CP-19544 @CP-19544-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal @vidya
  Scenario: Validate Pre-Hearing Conference task created/Edited/viewable and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Pre-Hearing Conference" task page
    And I will provide following information before creating task
      | taskInfo                |Test-19376|
      | assignee                |Service TesterTwo|
      | externalCaseId          |123456789|
      | externalApplicationId   |random|
      | actionTaken             |Redetermined Case|
      | preHearingOutcome       |No Processing Delay|
      | preHearingReason        |Coverage Type,Coverage Period|
      | caseStatus              |New Application|
      | appointmentTime         |03:28 PM|
      | appointmentDate         |today|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Cancelled|
      | reasonForCancel         |Created Incorrectly|
      | taskInfo                ||
      | assignee                ||
      | externalCaseId          ||
      | externalApplicationId   |ADCShiTR5|
      | actionTaken             ||
      | preHearingReason        |Untimely Processing|
      | caseStatus              |New Application|
      | preHearingOutcome       |No Processing Delay,Processing Delay|
      | appointmentTime         ||
      | appointmentDate         ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-18021 @CP-18021-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Validate Report of Newborn task created and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Report of Newborn" task page
    And I will provide following information before creating task
      | taskInfo   | Test CP-18021-02  |
      | assignee   | Service TesterTwo |
      | channel    | Fax               |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status           | Cancelled           |
      | reasonForCancel  | Created Incorrectly |
      | assignee         ||
      | taskInfo         |Test CP-18021-02 edit page|
      | channel          |Phone|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-19385 @CP-19385-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Systematically creation of Incomplete Contact Record Task when user log out
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then I searched existing case where First Name as "Jane" and Last Name as "Potter"
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    And I click on the Contact Type "Inbound"
    And I click on the Inbound Call queue "CoverVA_CVCC_AppealsVoicemail_Eng01_Voice"
    When I enter contact phone number "9632154874"
    And I select program type "HPE"
    And I select Business Unit "CVCC" and contact reasons "Correspondence"
    And  I choose "Extended Pend Letter" option for Contact Action field
    Then I enter additional comments "Complaint issue is resolved"
    Then I click save button for reason and action
    Then I logOut while Working a Task
    Then I logged into CRM after logged out with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    Then I verify that Incomplete Contact Record Task is systematically created and has expected details
    And Close the soft assertions

  @CP-19385 @CP-19385-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Validate Report of Incomplete Contact Record Task created and data is saved in DB
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Incomplete Contact Record" task page
    And I will provide following information before creating task
      | taskInfo           | Test CP-19385-02 Create |
      | assignee           | Service TesterTwo       |
      | externalCaseId     | 19385123                |
      | actionTaken        | Outbound Call - Reached |
      | externalConsumerID | 45619385                |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will click on newly created task id
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status             | Cancelled           |
      | reasonForCancel    | Created Incorrectly |
      | assignee           ||
      | taskInfo           |Test CP-19385-02 edit page|
      | externalCaseId     ||
      | actionTaken        ||
      | externalConsumerID ||
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions

  @CP-22738 @CP-22738-01  @CP-22738-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @kamil
  Scenario: Validate Final Application Review task is created and Link SR then Link Final Application Review Task  to Consumers associated to Application SR
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I navigate to "Application SR" service request page
    And I will provide following information before creating task
      | applicationType       |MAGI Standard Application - CVIU |
      | externalApplicationId | random   |
      | myWorkSpaceDate       | -45      |
      | missingInfoRequired   | Yes      |
      | vclDueDate            | today       |
      | channel               | CommonHelp  |
      | vclSentDate           | today       |
      | InbDocType            | Other       |
    When I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | actionTakenSingle       |Sent VCL|
      | disposition             |Pending MI|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click id of "Application SR" in Links section
    And I click id of "Final Application Review" in Links section
    Then I verify "Final Application Review" task fields are displayed
    Then I verify Task or SR linked to
    |Application SR|
    |Consumer|
    And Close the soft assertions

  @CP-22738 @CP-22738-03 @kamil @ruslan @events-wf
  Scenario Outline: Validate Final Application Review task Link Task to any Documents associated to the Application SR
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I navigate to "Application SR" service request page
    And I will provide following information before creating task
      | applicationType           | MAGI - PW       |
      | externalApplicationId     | random          |
      | myWorkSpaceDate           | -2              |
      | missingInfoRequired       | Yes             |
      | vclDueDate                | +4              |
      | decisionSource            ||
      | externalCaseId            | 123654789       |
      | applicationReceivedDate   | today           |
      | noOfApprovedApplicants    | 1               |
      | noOfApplicantsInHousehold | 1               |
      | applicationStatus         | Data Collection |
      | InbDocType                | Drivers License |
      | vclSentDate               | today           |
      |channel                    | CommonHelp      |
    When I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | Complete             |
      | reasonForEdit   | Corrected Data Entry |
      | actionTknSingle | Sent VCL             |
      | disposition     | Pending MI           |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    When I will get the Authentication token for "CoverVA" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I will get "Complete" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    And I have a Inbound Document that with the Inbound Document Type of "VACV Verification Document"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on Create Link button and choose task or sr link
    Then I will search for newly created task on Task Search
    And I click on search button on task search page
    And I verify task search results are displayed
    And I click on Link button in task search
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify "LINK_EVENT" generated for "Final Application Review" task : "TASK TO INBOUND_CORRESPONDENCE,INBOUND_CORRESPONDENCE TO TASK"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    And Close the soft assertions
    Examples:
      |eventName  | correlationId |
      |LINK_EVENT | manually_link_case_to_task   |


  @CP-22906 @CP-22906-01 @CP-22906-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @kamil
  Scenario: Validate Final Application Review task is created and Link SR then Link Final Application Review Task  to Consumers associated to Renewal SR
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType          | MAGI - PW  |
      | externalApplicationId    | random     |
      | myWorkSpaceDate          | -45        |
      | missingInfoRequired      | Yes        |
      | miReceivedDate           | today      |
      | vclDueDate               | today      |
      | renewalDate              | today      |
      | channel                  | CommonHelp |
      | applicationReceivedDate  | today      |
      | applicationSignatureDate | today      |
      | InbDocType               | Other      |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | actionTakenSingle       |Sent VCL|
      | disposition             |Pending MI|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click id of "Renewal SR" in Links section
    And I click id of "Final Application Review" in Links section
    Then I verify "Final Application Review" task fields are displayed
    Then I verify Task or SR linked to
      |Renewal SR|
      |Consumer|
    And Close the soft assertions


  @CP-22906 @CP-22906-03 @CP-22906-04 @events-wf @kamil
  Scenario Outline: Validate Final Application Review task Link Task to any Documents associated to the Renewal SR
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType       | MAGI - PW  |
      | applicationSignatureDate | today   |
      | applicationReceivedDate  | today   |
      | externalApplicationId | random     |
      | myWorkSpaceDate       | -2         |
      | missingInfoRequired   | Yes        |
      | miReceivedDate        | today      |
      | vclDueDate            | -2         |
      | channel               | CommonHelp |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | actionTakenSingle       |Sent VCL|
      | disposition             |Pending MI|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    When I will get the Authentication token for "CoverVA" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I will get "Complete" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on Create Link button and choose task or sr link
    Then I will search for newly created task on Task Search
    And I click on search button on task search page
    And I verify task search results are displayed
    And I click on Link button in task search
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify "LINK_EVENT" generated for "Final Application Review" task : "SERVICE_REQUEST TO INBOUND_CORRESPONDENCE,INBOUND_CORRESPONDENCE TO SERVICE_REQUEST"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    And Close the soft assertions
    Examples:
      |eventName  | correlationId |
      |LINK_EVENT | manually_link_case_to_task   |