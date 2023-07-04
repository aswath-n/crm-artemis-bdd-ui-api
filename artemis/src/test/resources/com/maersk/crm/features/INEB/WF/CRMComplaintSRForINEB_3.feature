Feature: Validate INEB Complaint SR_3

  Background: Common steps for login functionality
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page

  @CP-25094 @CP-25094-06 @crm-regression @ui-wf-ineb @priyal
  Scenario Outline: Validate Cancelled status for 2 Outbound Call Task after manually close the SR
    And I will provide following information before creating task
      | complaintAbout | maersk        |
      | incidentDate   | today          |
      | memberName     | Priyal         |
      | preferredPhone | 1234567890     |
      | complaintType  | Customer Service |
      | priority       | 2              |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    #And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    When I click id of "QA Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete               |
      | reasonForEdit | Corrected Data Entry   |
      | actionTaken   | Created Summary Report |
      | disposition   | Complaint Reviewed     |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    When I click id of "Supervisor Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete               |
      | reasonForEdit | Corrected Data Entry   |
      | actionTaken   | Provide Summary Report |
      | disposition   | Complaint Reviewed     |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                        |
      | reasonForEdit  | Corrected Data Entry                            |
      | disposition    | Did Not Reach Consumer                          |
      | preferredPhone | 1234567890                                      |
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail |
      | contactName    | Alewa Papovich                                  |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I store the task id for created status from the link section
    And I click on edit service request button
    And I will update the following information in edit task page
      | status        | Closed               |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Complaint Invalid    |
    And I click on save button on task edit page
    Then I verify "Outbound Call" task status is updated to "Cancelled" in the link section for multiple tasks
    And I initiated bpm process get api by service request id for process name "INEB_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Open   || 2        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

  @CP-25094 @CP-25094-07 @crm-regression @ui-wf-ineb @priyal
  Scenario Outline: Validate Cancelled status for 3 Outbound Call Task after manually close the SR
    And I will provide following information before creating task
      | complaintAbout | maersk        |
      | incidentDate   | today          |
      | memberName     | Priyal         |
      | preferredPhone | 1234567890     |
      | complaintType  | Customer Service |
      | priority       | 3              |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    When I click id of "QA Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete               |
      | reasonForEdit | Corrected Data Entry   |
      | actionTaken   | Created Summary Report |
      | disposition   | Complaint Reviewed     |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    When I click id of "Supervisor Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete               |
      | reasonForEdit | Corrected Data Entry   |
      | actionTaken   | Provide Summary Report |
      | disposition   | Complaint Reviewed     |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                        |
      | reasonForEdit  | Corrected Data Entry                            |
      | disposition    | Did Not Reach Consumer                          |
      | preferredPhone | 1234567890                                      |
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail |
      | contactName    | Alewa Papovich                                  |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                        |
      | reasonForEdit  | Corrected Data Entry                            |
      | disposition    | Did Not Reach Consumer                          |
      | preferredPhone | 1234567890                                      |
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail |
      | contactName    | Alewa Papovich                                  |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I store the task id for created status from the link section
    And I click on edit service request button
    And I will update the following information in edit task page
      | status        | Closed               |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Complaint Invalid    |
    And I click on save button on task edit page
    Then I verify "Outbound Call" task status is updated to "Cancelled" in the link section for multiple tasks
    And I initiated bpm process get api by service request id for process name "INEB_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Open   || 3        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

  @CP-25094 @CP-25094-08 @crm-regression @ui-wf-ineb @priyal
  Scenario Outline: Validate Cancelled status for 1 Outbound Call Task after manually close the SR
    And I will provide following information before creating task
      | complaintAbout | maersk        |
      | incidentDate   | today          |
      | memberName     | Priyal         |
      | preferredPhone | 1234567890     |
      | complaintType  | Customer Service |
      | priority       | 4              |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    When I click id of "QA Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete               |
      | reasonForEdit | Corrected Data Entry   |
      | actionTaken   | Created Summary Report |
      | disposition   | Complaint Reviewed     |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    When I click id of "Supervisor Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete               |
      | reasonForEdit | Corrected Data Entry   |
      | actionTaken   | Provide Summary Report |
      | disposition   | Complaint Reviewed     |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I store the task id for created status from the link section
    And I click on edit service request button
    And I will update the following information in edit task page
      | status        | Closed               |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Complaint Invalid    |
    And I click on save button on task edit page
    Then I verify "Outbound Call" task status is updated to "Cancelled" in the link section for multiple tasks
    And I initiated bpm process get api by service request id for process name "INEB_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Open   || 4        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||
