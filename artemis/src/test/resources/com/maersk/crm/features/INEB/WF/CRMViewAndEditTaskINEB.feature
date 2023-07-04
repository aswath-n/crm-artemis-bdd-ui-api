Feature: Validate task in view and edit page

  @CP-30468 @CP-30468-02 @CP-25316 @CP-25316-15 @CP-25316-08 @crm-regression @ui-wf-ineb @vidya
  Scenario Outline: Validate Supervisor Review Complaint task in edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify Task Notes field is not displayed in Search Results page
    And In search result click on task id to navigate to view page
    Then I verify task following fields are present on view page
      | ACTION TAKEN |
    And I click on edit button on view task page
    And I verify Task Notes field is not displayed on Edit Task page
    And I will update the following information in edit task page
      | priority    ||
      | status      | Complete |
      | taskInfo    | ABC      |
      | actionTaken ||
      | disposition ||
    And I click on save button on task edit page
    And I verify task mandatory fields error message "PRIORITY"
    And I verify minimum lenght error message "TASK INFORMATION"
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify "actionTaken" multi select drop down value
      | Meet with CSR                |
      | Provide Summary Report       |
      | Review Complaint Information |
    Then I verify "disposition" single select drop down value
      | Complaint Reviewed |
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
      | priority      | 2                    |
      | taskInfo      | test @CP-30468-02    |
      | actionTaken   | Meet with CSR        |
      | disposition   | Complaint Reviewed   |
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    Then I Verify Task Notes field is not displayed on View Task page
    And I verify the updated information in view task details page
    And Close the soft assertions
    Examples:
      | taskId | taskType                    | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || Supervisor Review Complaint || Created ||          ||            ||                || false         ||            ||          ||           ||

  @CP-30466 @CP-30466-02 @CP-31982 @CP-31982-04 @crm-regression @ui-wf-ineb @vidya @priyal
  Scenario Outline: Validate QA Review Complaint task in edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I navigated to the Edit History tab ON Edit Task
    And I navigated to the Task Details tab ON Edit Task
    Then I verify task following fields are present on view page
      | ACTION TAKEN |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority    ||
      | status      | Complete |
      | taskInfo    | ABC      |
      | actionTaken ||
      | disposition ||
    And I click on save button on task edit page
    And I verify task mandatory fields error message "PRIORITY"
    And I verify minimum lenght error message "TASK INFORMATION"
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify "actionTaken" multi select drop down value
      | Created Evaluation            |
      | Created Summary Report        |
      | Updated Complaint Information |
    Then I verify "disposition" single select drop down value
      | Complaint Invalid  |
      | Complaint Reviewed |
    And I will update the following information in edit task page
      | priority      | 2                    |
      | reasonForEdit | Corrected Data Entry |
      | taskInfo      | Test @CP-30466-02    |
      | actionTaken   | Created Evaluation   |
      | disposition   | Complaint Invalid    |
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions
    Examples:
      | taskId | taskType            | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || QA Review Complaint || Created ||          ||            ||                || false         ||            ||          ||           ||

  @CP-30467 @CP-30467-02 @crm-regression @ui-wf-ineb @vidya
  Scenario Outline: Validate State Escalated Complaint task in edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    Then I verify task following fields are present on view page
      | ACTION TAKEN |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority    ||
      | status      | Complete |
      | taskInfo    | ABC      |
      | actionTaken ||
      | disposition ||
    And I click on save button on task edit page
    And I verify task mandatory fields error message "PRIORITY"
    And I verify minimum lenght error message "TASK INFORMATION"
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify "actionTaken" multi select drop down value
      | Notified State - Email |
      | Notified State - Phone |
    Then I verify "disposition" single select drop down value
      | Escalation Complete |
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry   |
      | priority      | 2                      |
      | taskInfo      | test @CP-30467-02      |
      | actionTaken   | Notified State - Phone |
      | disposition   | Escalation Complete    |
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And Close the soft assertions
    Examples:
      | taskId | taskType                  | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || State Escalated Complaint || Created ||          ||            ||                || false         ||            ||          ||           ||
