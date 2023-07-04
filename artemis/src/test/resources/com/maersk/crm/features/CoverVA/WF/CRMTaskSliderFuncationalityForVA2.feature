Feature: Task slider functionality for CoverVA SR

  @CP-22827 @CP-22827-01 @CP-31982 @CP-31982-11 @priyal @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Update Conditional Action Taken + VCL Due Date on Process App Task for Application SR
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 1" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      | applicationType           | Re-Entry Application - CVIU |
      | externalApplicationId     | random                      |
      | myWorkSpaceDate           | -1                          |
      | applicationReceivedDate   | today                       |
      | channel                   | CommonHelp                  |
      | noOfApplicantsInHousehold | 1                           |
      | missingInfoRequired       | Yes                         |
      | applicationStatus         | Data Collection             |
      | vclDueDate                | -1                          |
      | vclSentDate               | today                       |
      | documentType              | Drivers License             |
    And I click on save button in create service request page
    When If any In Progress task present then update that to Cancelled
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I click on initiate randomly
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status            | Complete   |
      | actionTakenSingle | Sent VCL   |
      | disposition       | Pending MI |
    And I click on save in Task Slider
    Then Verify I will see error message "Please correct the Action Taken or provide the VCL Due Date on the linked Application or Application Renewal Service Request"
    And I click on task id on the task slider
    And I navigated to the Edit History tab ON Edit Task
    And I navigated to the Task Details tab ON Edit Task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status            | Complete                |
      | reasonForEdit     | Entered Additional Info |
      | actionTakenSingle | Sent VCL                |
      | disposition       | Pending MI              |
    And I click on save button on task edit page
    Then Verify I will see error message "Please correct the Action Taken or provide the VCL Due Date on the linked Application or Application Renewal Service Request"
    And I click on cancel button on create task type screen
    And I click on continue button on warning message
    Then I verify should I navigated to view task page
    And I click id of "Application SR" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit             | Entered Additional Info |
      | applicationReceivedDate   | today                   |
      | channel                   | CommonHelp              |
      | noOfApplicantsInHousehold | 1                       |
      | missingInfoRequired       | Yes                     |
      | applicationStatus         | Medicare Review Needed  |
      | InbDocType                | Drivers License         |
      | vclDueDate                | -2                      |
      | vclSentDate               | today                   |
      | caseWorkerFirstName       | TagTest                 |
      | caseWorkerLastName        | Demo                    |
    And I click on save button on task edit page
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I will update the following information in task slider
      | status            | Complete   |
      | actionTakenSingle | Sent VCL   |
      | disposition       | Pending MI |
    And I click on save in Task Slider
    #Then I verify task save success message
    And Close the soft assertions

  @CP-22827 @CP-22827-02 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Update Conditional Action Taken + VCL Due Date on Process App Task for Renewal SR
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Account 1" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType           | Ex Parte Renewal - CVIU |
      | externalApplicationId     | random                  |
      | myWorkSpaceDate           | -1                      |
      | applicationSignatureDate  | today                   |
      | applicationReceivedDate   | today                   |
      | channel                   | Paper                   |
      | noOfApplicantsInHousehold | 1                       |
      | missingInfoRequired       | Yes                     |
      | miReceivedDate            | today                   |
      | applicationStatus         | Data Collection         |
      | facilityName              | Bristol City Jail       |
    And I click on save button in create service request page
    Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in descending order in Task Search
    When If any In Progress task present then update that to Cancelled
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status            | Complete   |
      | actionTakenSingle | Sent VCL   |
      | disposition       | Pending MI |
    And I click on save in Task Slider
    Then Verify I will see error message "Please correct the Action Taken or provide the VCL Due Date on the linked Application or Application Renewal Service Request"
    And I click on task id on the task slider
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status            | Complete                |
      | reasonForEdit     | Entered Additional Info |
      | actionTakenSingle | Sent VCL                |
      | disposition       | Pending MI              |
    And I click on save button on task edit page
    Then Verify I will see error message "Please correct the Action Taken or provide the VCL Due Date on the linked Application or Application Renewal Service Request"
    And I click on cancel button on create task type screen
    And I click on continue button on warning message
    Then I verify should I navigated to view task page
    And I click id of "Renewal SR" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit             | Entered Additional Info |
      | applicationReceivedDate   | today                   |
      | channel                   | CommonHelp              |
      | noOfApplicantsInHousehold | 1                       |
      | missingInfoRequired       | Yes                     |
      | miReceivedDate            | today                   |
      | applicationStatus         | Medicare Review Needed  |
      | InbDocType                | Drivers License         |
      | vclDueDate                | -2                      |
      | vclSentDate               | today                   |
    And I click on save button on task edit page
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I will update the following information in task slider
      | status            | Complete   |
      | actionTakenSingle | Sent VCL   |
      | disposition       | Pending MI |
    And I click on save in Task Slider
    And Close the soft assertions

  @CP-19031 @CP-19031-03 @CP-34852 @CP-34852-05 @CP-34852-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Verification Complaint SR is closed when we complete the Review Complaint task
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Complaint"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | Channel      | Mail                   |
      | documentType | VACV Complaint         |
    When I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM with "Service Account 1" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    And I click id of "Review Complaint" in Links section
    And I will click on back arrow on view task page
    And I click on cancel button on task search page
    And I will search with taskId
    When If any In Progress task present then update that to Cancelled
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status      | Complete         |
      | disposition | Withdrawn        |
      | noteValue   | Test SliderNotes |
    And I click on save in Task Slider
    Then I verify task save success message
    And In search result click on task id to navigate to view page
    Then I verify saved task note from slider
    And I click id of "Complaint SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Withdrawn"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions