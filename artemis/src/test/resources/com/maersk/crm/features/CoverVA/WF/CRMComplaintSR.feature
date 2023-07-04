Feature: Validation of Complaint SR

  @CP-18734 @CP-18734-01 @CP-18749 @CP-18749-01 @CP-18832 @CP-18832-02 @CP-19032 @CP-19032-01 @CP-19038 @CP-19038-01 @CP-17859 @CP-17859-02 @CP-19034 @CP-19034-01 @priyal @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @mark
  #needs to be updated because we dont see cancel section anymore on systematically created tasks
  Scenario: Validate Complaint SR is created from inbound document and Create Review Complain & Complaint Escalation Task for Complaint SR and Close Complaint SR when Complaint Escalation is Cancelled
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I have a Inbound Document that with the Inbound Document Type of "VACV Complaint"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | Channel      | Mail                   |
      | documentType | VACV Complaint         |
    When I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    Then I will verify "Complaint SR" Details view SR details page
    Then I verify "Complaint SR" link section has all the business object linked : "Task,Inbound Correspondence"
    And I initiated bpm process get api by service request id for process name "CoverVA_Complaint"
    And I run bpm process get api by sr id
    And I will check response of service request bpm process
    And I click id of "Review Complaint" in Links section
    Then I verify "Review Complaint" task fields are displayed
    Then I verify "Review Complaint" link section has all the business object linked : "Service Request,Inbound Correspondence"
    And I click id of "Complaint SR" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on contact link button under LINK section
    And I click on Case and Consumer button
    And I searched consumer created by api script
    And I click on Search option
    And I will expand the consumer record
    When I click on Link Record Consumer button
    And I click on save button on task edit page
    Then I verify "Complaint SR" link section has all the business object linked : "Task,Consumer,Inbound Correspondence"
    When I click on Inbound Correspondence Link from View Task Page
    Then I should see I am navigated to the Inbound Correspondence Details Page for "CoverVA Complaint"
    And I click on refresh button
    And I verify that Inbound Correspondence Page has all "Complaint SR" linked objects
    And I click on "Complaint SR" sr id on Inbound Correspondence Page link section
    And I click on ConsumerId on Links section
    And I verify user is navigate to Demographic info tab after clicking on external consumer id
    And I navigate to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    Then I navigate to view SR details page by clicking on sr id
    And I click id of "Review Complaint" in Links section
    Then I verify "Review Complaint" link section has all the business object linked : "Service Request,Inbound Correspondence,Consumer"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | externalConsumerID      |567890765          |
      | externalCaseId          |567897654321       |
      | complaintType           |CPU Complaint      |
      | disposition             |Escalated|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click id of "Complaint SR" in Links section
    And I click id of "Complaint Escalation" in Links section
    Then I verify "Complaint Escalation" task fields are displayed
    Then I verify "Complaint Escalation" link section has all the business object linked : "Service Request,Inbound Correspondence,Consumer"
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify latest task displayed in "Work Queue or My Task" page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Cancelled|
      | reasonForCancel         | Created Incorrectly|
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    And I click id of "Complaint SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Cancelled"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-18739 @CP-18739-01 @CP-19031 @CP-19031-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @mark @vidya
  Scenario: Trigger workflow when Complaint SR is created through UI
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Complaint SR" service request page
    And I will provide following information before creating task
      | taskInfo | Test CP-18737 |
    And I click on save button in create service request page
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Complaint"
    And I run bpm process get api by sr id
    And I will check response of service request bpm process
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete            |
      | reasonForEdit           |Corrected Data Entry|
      | externalConsumerID      |567890765           |
      | externalCaseId          |567897654321        |
      | complaintType           |CPU Complaint       |
      | disposition             |Referred            |
    And I click on save button on task edit page
    And I click id of "Complaint SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Referred"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-18739 @CP-18739-02 @CP-19038 @CP-19038-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @mark
  Scenario: Link Complaint SR to CR, Consumer and Task and Close Complaint SR when Review Complaint task is Cancelled
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Markell" and Last Name as "Declan"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Complaint SR" service request page
    And I will provide following information before creating task
      | taskInfo | Test CP-18739 |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Complaint SR" link section has all the business object linked : "Task,Consumer,Contact Record"
    And I Navigate to Contact Record details page
    Then I verify link section has "Complaint SR" and other business object
    And I click on Active Contact widget
    Then I verify link section has "Complaint SR" and other business object
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And In search result click on task id to navigate to view page
    And I click id of "Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  | Cancelled|
      | reasonForCancel         | Created Incorrectly|
      | externalConsumerID      | 567890765          |
      | externalCaseId          | 567897654321       |
      | complaintType           | CPU Complaint      |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    And I click id of "Complaint SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Cancelled"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-18739 @CP-18739-04 @CP-19457 @CP-19457-01 @CP-19031 @CP-19031-04 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @mark @vidya
  Scenario: Create Complaint SR and Link to Consumer Manually
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Complaint SR" service request page
    And I will provide following information before creating task
      | taskInfo | Test CP-18739 |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Vidya" and Last Name as "Mithun"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    When I click on Link Record Consumer button
    Then I verify task is linked with Consumer ID
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    When I click case consumer search tab
    When I searched customer have First Name as "Vidya" and Last Name as "Mithun"
    When I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Complaint SR" link section has all the business object linked : "Task,Consumer"
    And I click id of "Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | externalConsumerID      | 567890765          |
      | externalCaseId          | 567897654321       |
      | complaintType           | CPU Complaint      |
      | disposition             |Escalated|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click id of "Complaint SR" in Links section
    And I click id of "Complaint Escalation" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | disposition             |Referred|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click id of "Complaint SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Referred"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-18739 @CP-18739-05 @CP-19031 @CP-19031-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @mark @vidya
  Scenario: Create Complaint SR and Link to CR (no Consumer link) and verify Complaint SR is closed when we complete the Review Complaint task
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I navigate to "Complaint SR" service request page
    And I will provide following information before creating task
      | taskInfo | Test CP-18739 |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I verify Task Link Component has unique TaskId, name = "Service Request", type = "Complaint SR", status date is present, and status = "Open"
    When I navigate to "Task Search" page
    And I search Task by Task Id
    And I click on search button on task search page
    And In search result click on task id to navigate to view page
    Then I verify task is linked with Contact ID
    And I click id of "Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete            |
      | reasonForEdit           |Corrected Data Entry|
      | externalConsumerID      |567890765           |
      | externalCaseId          |567897654321        |
      | complaintType           |CPU Complaint       |
      | disposition             |Resolved            |
    And I click on save button on task edit page
    And I click id of "Complaint SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Resolved"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-18413 @CP-18413-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Validate that there are no optional fields available on the Create SR UI with open status
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with default state for cover-va
    When I click on Create Consumer Button
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Complaint SR" service request page
    And I will provide following information before creating task
      | taskInfo | Test CP-18413-01 |
    And I click on save button in create service request page
 #   Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify the SR details displayed on view SR page
    And I click on edit service request button
    Then I verify Disposition field is display on screen
    And I will update the following information in edit task page
      | status | Closed |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "DISPOSITION"
    And I verify task mandatory fields error message "REASON FOR EDIT"
    Then I verify "disposition" single select drop down value
      | Referred  |
      | Resolved  |
      | Withdrawn |
    And I will update the following information in edit task page
      | status        | Closed               |
      | priority      | 2                    |
      | taskInfo      | TestDemo             |
      | disposition   | Resolved             |
      | reasonForEdit | Corrected Data Entry |
    Then I verify Disposition field is display on screen
    And I click on save button on task edit page
    Then I verify the SR details displayed on view SR page
    And Close the soft assertions

  @CP-18749 @CP-18749-02 @CP-19457 @CP-19457-02 @CP-19031 @CP-19031-05 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate Review Complaint task is create for complaint SR
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched case and consumer created by api
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Complaint SR" service request page
    And I will provide following information before creating task
      | taskInfo | Test CP-18739 |
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click id of "Review Complaint" in Links section
    Then I verify "Review Complaint" task fields are displayed
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify latest task displayed in "Work Queue or My Task" page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status             | Complete             |
      | reasonForEdit      | Corrected Data Entry |
      | externalConsumerID | 567890765            |
      | externalCaseId     | 567897654321         |
      | complaintType      | CPU Complaint        |
      | disposition        | Escalated            |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click id of "Complaint SR" in Links section
    And I click id of "Complaint Escalation" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                  |Complete|
      | reasonForEdit           |Corrected Data Entry|
      | disposition             |Resolved|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click id of "Complaint SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Resolved"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

