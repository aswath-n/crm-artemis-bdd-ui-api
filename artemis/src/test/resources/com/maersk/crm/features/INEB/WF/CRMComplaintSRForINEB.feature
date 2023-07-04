Feature: Validation of Complaint SR

  Background: Common steps for login functionality
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page

 # @CP-24970 @CP-24977 @CP-24977-04 @CP-25093 @CP-25093-04 @CP-24097 @CP-24097-07 @CP-24975 @CP-24975-06 @CP-18423 @CP-18423-04 @crm-regression @ui-wf-ineb @priyal
    #based on CP-24970 AC 3.0 if complaint type is Misinformation, we expect to see Outbound Call Task
    #but this value is removed from dropdown by referring CP-35081
  Scenario Outline: Validate Save event for 2nd and 3rd call attempt for Complaint SR for Outbound CAll and Sub-process is ended
    And I will provide following information before creating task
      | complaintAbout | maersk        |
      | incidentDate   | today          |
      | memberName     | Priyal         |
      | preferredPhone | 1234567890     |
      | complaintType  | Misinformation |
      | priority       | 2              |
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
    Then I verify "Outbound Call" task fields are displayed
    Then I verify "Outbound Call" link section has all the business object linked : "Service Request"
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
    Then I verify "Outbound Call" link section has all the business object linked : "Service Request"
    When I click id of "Customer Service Complaint" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on contact link button under LINK section
    And I click on Case and Consumer button
    Then I see Case& Consumer Search section display
    When I searched customer have First Name as "Lorenzo" and Last Name as "Franecki"
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    And I select Consumer radio button
    When I click on Link Record Consumer button
    And I click on save button on task edit page
    And I click on task id with status created in the link section
    Then I verify "Outbound Call" task fields are displayed
    Then I verify "Outbound Call" link section has all the business object linked : "Consumer,Case,Service Request"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                        |
      | reasonForEdit  | Corrected Data Entry                            |
      | disposition    | Successfully Reached Consumer                   |
      | preferredPhone | 1234567890                                      |
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail |
      | contactName    | Alewa Papovich                                  |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    Then I verify link section contains 2 "Outbound Call"
    Then I verify the sr status is updated to close and disposition is set to "Complaint Resolved"
    And I initiated bpm process get api by service request id for process name "INEB_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Open   || 2        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

#  @CP-24977 @CP-24977-05 @CP-25093 @CP-25093-05 @CP-24097 @CP-24097-08 @crm-regression @ui-wf-ineb @priyal
    #based on CP-24970 AC 3.0 if complaint type is Misinformation, we expect to see Outbound Call Task
    #but this value is removed from dropdown by referring CP-35081
  Scenario Outline: Validate Save event for 2nd and 3rd call attempt for Complaint SR for Outbound CAll and Sub-process is ended
    And I will provide following information before creating task
      | complaintAbout | maersk        |
      | incidentDate   | today          |
      | memberName     | Priyal         |
      | preferredPhone | 1234567890     |
      | complaintType  | Misinformation |
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
    Then I verify "Outbound Call" task fields are displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                        |
      | reasonForEdit  | Corrected Data Entry                            |
      | disposition    | Successfully Reached Consumer                   |
      | preferredPhone | 1234567890                                      |
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail |
      | contactName    | Alewa Papovich                                  |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    Then I verify link section contains 1 "Outbound Call"
    Then I verify the sr status is updated to close and disposition is set to "Complaint Resolved"
    And I initiated bpm process get api by service request id for process name "INEB_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Open   || 3        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

  @CP-24977 @CP-24977-06 @CP-25093 @CP-25093-06 @CP-24097 @CP-24097-09 @CP-24975 @CP-24975-05 @crm-regression @ui-wf-ineb @priyal #will fail due to CP-38628
  Scenario Outline: Validate Sub-Process is ended for Complaint SR and Link Component for Complaint Outbound CAll
    And I will provide following information before creating task
      | complaintAbout | maersk          |
      | incidentDate   | today            |
      | memberName     | Priyal           |
      | preferredPhone | 1234567890       |
      | complaintType  | Customer Service |
      | priority       | 5                |
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
    Then I verify "Complaint Outbound Call" task fields are displayed
    Then I verify "Complaint Outbound Call" link section has all the business object linked : "Service Request"
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
    Then I verify "Complaint Outbound Call" link section has all the business object linked : "Service Request"
    When I click id of "Customer Service Complaint" in Links section
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I click on Case and Consumer button
    Then I see Case& Consumer Search section display
    When I searched customer have First Name as "Luis" and Last Name as "Von"
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    And I select Consumer radio button
    When I click on Link Record Consumer button
    And Wait for 3 seconds
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    And I click on task id with status created in the link section
    Then I verify "Complaint Outbound Call" task fields are displayed
    Then I verify "Complaint Outbound Call" link section has all the business object linked : "Consumer,Case,Service Request"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                        |
      | reasonForEdit  | Corrected Data Entry                            |
      | disposition    | Successfully Reached Consumer                   |
      | preferredPhone | 1234567890                                      |
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    Then I verify link section contains 2 "Complaint Outbound Call"
    Then I verify the sr status is updated to close and disposition is set to "Complaint Resolved"
    And I initiated bpm process get api by service request id for process name "INEB_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Open   || 5        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

  @CP-23606 @CP-23606-04 @CP-25094 @CP-25094-01 @crm-regression @ui-wf-ineb @priyal
  Scenario Outline: Trigger workflow when Complaint SR is created through UI and verify Cancelled status for QA Review Complaint task
    And I will provide following information before creating task
      | memberName            | Priyal Garg                   |
      | preferredPhone        | 1234567890                    |
      | complaintAbout        | maersk                       |
      | complaintType         | Customer Service              |
      | incidentDate          | today                         |
      | preferredCallBackDate | today                         |
      | preferredCallBackTime | 04:30 PM                      |
      | contactName           | Test                          |
      | escalated             | true                          |
      | escalationReason      | Consumer Contacting the State |
      | csrName               | Service TesterTwo             |
      | priority              | 4                             |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I verify the SR details displayed on view SR page
    And I initiated bpm process get api by service request id for process name "INEB_Complaint"
    And I run bpm process get api by sr id
    Then I will check response of service request bpm process
    And I click on edit service request button
    And I will update the following information in edit task page
      | status        | Closed               |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Complaint Invalid    |
    And I click on save button on task edit page
    Then I verify "QA Review Complaint" task status is updated to "Cancelled" on the link section
    And I verify "State Escalated Complaint" task status is updated to "Created" on the link section
    And I initiated bpm process get api by service request id for process name "INEB_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Open   || 4        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

 # @CP-23609 @CP-23609-02 @CP-24970 @CP-24970-02 @CP-31982 @CP-31982-08 @crm-regression @ui-wf-ineb @priyal
    #based on CP-24970 AC 3.0 if complaint type is Misinformation, we expect to see Outbound Call Task
    # but this value is removed from dropdown by referring CP-35081
  Scenario Outline: Validate Customer Service Complaint link objects for QA Review Complaint
    And I will provide following information before creating task
      | complaintAbout | maersk           |
      | incidentDate   | today             |
      | memberName     | Priyal            |
      | preferredPhone | 1234567890        |
      | complaintType  | Misinformation    |
      | csrName        | Service TesterTwo |
      | priority       | 2                 |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task"
    When I click id of "QA Review Complaint" in Links section
    Then I verify "QA Review Complaint" link section has all the business object linked : "Service Request"
    When I click id of "Customer Service Complaint" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on contact link button under LINK section
    And I click on Case and Consumer button
    Then I see Case& Consumer Search section display
    When I searched customer have First Name as "Lorenzo" and Last Name as "Franecki"
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    And I select Consumer radio button
    When I click on Link Record Consumer button
    And I click on save button on task edit page
    Then I verify the SR details displayed on view SR page
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task,Consumer,Case"
    When I click id of "QA Review Complaint" in Links section
    Then I verify "QA Review Complaint" link section has all the business object linked : "Consumer,Case,Service Request"
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
    When I click id of "Outbound Call" in Links section
    Then I verify "Outbound Call" task fields are displayed
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    When I expand the first row in search result list
    Then I verify the task details are displayed in search result when expanded
    And In search result click on task id to navigate to view page
    Then I verify "Outbound Call" task fields are displayed
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
    Then I verify "Outbound Call" task fields are displayed
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    When I expand the first row in search result list
    Then I verify the task details are displayed in search result when expanded
    And In search result click on task id to navigate to view page
    Then I verify "Outbound Call" task fields are displayed
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
    Then I verify "Outbound Call" task fields are displayed
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    When I expand the first row in search result list
    Then I verify the task details are displayed in search result when expanded
    And In search result click on task id to navigate to view page
    Then I verify "Outbound Call" task fields are displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                        |
      | reasonForEdit  | Corrected Data Entry                            |
      | disposition    | Did Not Reach Consumer                          |
      | preferredPhone | 1234567890                                      |
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail |
      | contactName    | Alewa Papovich                                  |
    And I click on save button on task edit page
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Open   || 2        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

 # @CP-24975 @CP-24975-04 @crm-regression @ui-wf-ineb @priyal
    #based on CP-24970 AC 3.0 if complaint type is Misinformation, we expect to see Outbound Call Task
    # but this value is removed from dropdown by referring CP-35081
  Scenario Outline: Validate Complaint Outbound Call link objects for Complaint SR
    And I will provide following information before creating task
      | complaintAbout | maersk        |
      | incidentDate   | today          |
      | memberName     | Priyal         |
      | preferredPhone | 1234567890     |
      | complaintType  | Misinformation |
      | priority       | 3              |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task"
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
    Then I verify "Outbound Call" link section has all the business object linked : "Service Request"
    When I click id of "Customer Service Complaint" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on contact link button under LINK section
    And I click on Case and Consumer button
    Then I see Case& Consumer Search section display
    When I searched customer have First Name as "aXsXz" and Last Name as "lnXFvQE"
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    And I select Consumer radio button
    When I click on Link Record Consumer button
    And I click on save button on task edit page
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task,Task,Task,Consumer,Case"
    And I click on task id with status created in the link section
    Then I verify "Outbound Call" link section has all the business object linked : "Consumer,Case,Service Request"
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
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task,Task,Task,Task,Consumer,Case"
    And I click on task id with status created in the link section
    Then I verify "Outbound Call" task fields are displayed
    Then I verify "Outbound Call" link section has all the business object linked : "Consumer,Case,Service Request"
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
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task,Task,Task,Task,Task,Consumer,Case"
    And I click on task id with status created in the link section
    Then I verify "Outbound Call" link section has all the business object linked : "Consumer,Case,Service Request"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                        |
      | reasonForEdit  | Corrected Data Entry                            |
      | disposition    | Did Not Reach Consumer                          |
      | preferredPhone | 1234567890                                      |
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail |
      | contactName    | Alewa Papovich                                  |
    And I click on save button on task edit page
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Open   || 3        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

  @CP-24975 @CP-24975-07 @CP-34974 @CP-34974-01 @crm-regression @ui-wf-ineb @priyal #fails due to CP-38628
  Scenario Outline: Verify Link Component for Complaint Outbound Call for Complaint SR
    And I will provide following information before creating task
      | complaintAbout | maersk          |
      | incidentDate   | today            |
      | memberName     | Priyal           |
      | preferredPhone | 1234567890       |
      | complaintType  | Customer Service |
      | priority       | 2                |
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
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task,Task,Task"
    When I click id of "Complaint Outbound Call" in Links section
    Then I verify "Complaint Outbound Call" link section has all the business object linked : "Service Request"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                        |
      | reasonForEdit  | Corrected Data Entry                            |
      | disposition    | Did Not Reach Consumer                          |
      | preferredPhone | 1234567890                                      |
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    Then I verify "Complaint Outbound Call" task fields are displayed
    Then I verify "Complaint Outbound Call" link section has all the business object linked : "Service Request"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                        |
      | reasonForEdit  | Corrected Data Entry                            |
      | disposition    | Did Not Reach Consumer                          |
      | preferredPhone | 1234567890                                      |
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I click on task id with status created in the link section
    Then I verify "Complaint Outbound Call" link section has all the business object linked : "Service Request"
    When I click id of "Customer Service Complaint" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on contact link button under LINK section
    And I click on Case and Consumer button
    Then I see Case& Consumer Search section display
    When I searched customer have First Name as "aXsXz" and Last Name as "lnXFvQE"
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    And I select Consumer radio button
    When I click on Link Record Consumer button
    And I click on save button on task edit page
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task,Task,Task,Task,Task,Consumer,Case"
    And I click on task id with status created in the link section
    Then I verify "Complaint Outbound Call" task fields are displayed
    Then I verify "Complaint Outbound Call" link section has all the business object linked : "Consumer,Case,Service Request"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                        |
      | reasonForEdit  | Corrected Data Entry                            |
      | disposition    | Complaint Closed                                |
      | preferredPhone | 1234567890                                      |
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail |
    And I click on save button on task edit page
    And I click id of "Customer Service Complaint" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Complaint Closed"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Open   || 2        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

#  @CP-24975 @CP-24975-08 @CP-18704 @CP-18704-04 @crm-regression @ui-wf-ineb @priyal
     #based on CP-24970 AC 3.0 if complaint type is Misinformation, we expect to see Outbound Call Task
    # but this value is removed from dropdown by referring CP-35081
  Scenario Outline: Validate Complaint Outbound Call link objects for Complaint SR
    And I will provide following information before creating task
      | complaintAbout | maersk        |
      | incidentDate   | today          |
      | memberName     | Priyal         |
      | preferredPhone | 1234567890     |
      | complaintType  | Misinformation |
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
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task,Task,Task"
    And I click on task id with status created in the link section
    Then I verify "Outbound Call" link section has all the business object linked : "Service Request"
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
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task,Task,Task,Task"
    And I click on task id with status created in the link section
    Then I verify "Outbound Call" task fields are displayed
    Then I verify "Outbound Call" link section has all the business object linked : "Service Request"
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
    Then I verify "Outbound Call" link section has all the business object linked : "Service Request"
    When I click id of "Customer Service Complaint" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on contact link button under LINK section
    And I click on Case and Consumer button
    Then I see Case& Consumer Search section display
    When I searched customer have First Name as "aXKIm" and Last Name as "lnSKkHD"
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    And I select Consumer radio button
    When I click on Link Record Consumer button
    And I click on save button on task edit page
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task,Task,Task,Task,Task,Consumer,Case"
    And I click on task id with status created in the link section
    Then I verify "Outbound Call" task fields are displayed
    Then I verify "Outbound Call" link section has all the business object linked : "Consumer,Case,Service Request"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                        |
      | reasonForEdit  | Corrected Data Entry                            |
      | disposition    | Did Not Reach Consumer                          |
      | preferredPhone | 1234567890                                      |
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail |
      | contactName    | Alewa Papovich                                  |
    And I click on save button on task edit page
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Open   || 3        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

  @CP-24975 @CP-24975-03 @crm-regression @ui-wf-ineb @priyal #will fail due to CP-38628
  Scenario Outline: Validate Complaint Outbound Call link objects
    And I will provide following information before creating task
      | complaintAbout | maersk          |
      | incidentDate   | today            |
      | memberName     | Priyal           |
      | preferredPhone | 1234567890       |
      | complaintType  | Customer Service |
      | priority       | 5                |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task"
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
    When I click id of "Complaint Outbound Call" in Links section
    Then I verify "Complaint Outbound Call" link section has all the business object linked : "Service Request"
    When I click id of "Customer Service Complaint" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on contact link button under LINK section
    And I click on Case and Consumer button
    Then I see Case& Consumer Search section display
    When I searched customer have First Name as "fXgPV" and Last Name as "lnNbgvI"
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    And I select Consumer radio button
    When I click on Link Record Consumer button
    And I click on save button on task edit page
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task,Task,Task,Consumer,Case"
    When I click id of "Complaint Outbound Call" in Links section
    Then I verify "Complaint Outbound Call" link section has all the business object linked : "Consumer,Case,Service Request"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                        |
      | reasonForEdit  | Corrected Data Entry                            |
      | disposition    | Did Not Reach Consumer                          |
      | preferredPhone | 1234567890                                      |
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task,Task,Task,Task,Consumer,Case"
    And I click on task id with status created in the link section
    Then I verify "Complaint Outbound Call" task fields are displayed
    Then I verify "Complaint Outbound Call" link section has all the business object linked : "Consumer,Case,Service Request"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                        |
      | reasonForEdit  | Corrected Data Entry                            |
      | disposition    | Did Not Reach Consumer                          |
      | preferredPhone | 1234567890                                      |
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task,Task,Task,Task,Task,Consumer,Case"
    And I click on task id with status created in the link section
    Then I verify "Complaint Outbound Call" task fields are displayed
    Then I verify "Complaint Outbound Call" link section has all the business object linked : "Consumer,Case,Service Request"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                        |
      | reasonForEdit  | Corrected Data Entry                            |
      | disposition    | Successfully Reached Consumer                   |
      | preferredPhone | 1234567890                                      |
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail |
    And I click on save button on task edit page
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Open   || 5        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

  @CP-24967 @CP-24967-05 @crm-regression @ui-wf-ineb @priyal
  Scenario Outline: Validate Close the Complaint SR - emergency with Complint About = Emergent Situation after create new sr
    And I will provide following information before creating task
      | incidentDate     | today                         |
      | memberName       | Priyal                        |
      | preferredPhone   | 1234567890                    |
      | complaintType    | Customer Service              |
      | priority         | 2                             |
      | complaintAbout   | Emergent Situation            |
      | escalated        | true                          |
      | escalationReason | Consumer Contacting the State |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I verify the sr status is updated to close and disposition is set to "Complaint Resolved - Emergency"
    And I initiated bpm process get api by service request id for process name "INEB_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    When I click id of "State Escalated Complaint" in Links section
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status   | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Closed   || 2        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

  @CP-24967 @CP-24967-06 @crm-regression @ui-wf-ineb @priyal
  Scenario Outline: Validate Close the Complaint SR - Non emergency with Complint About = Other after create new sr
    And I will provide following information before creating task
      | complaintAbout | Other            |
      | organizationDD | Anthem           |
      | incidentDate   | today            |
      | memberName     | Priyal           |
      | preferredPhone | 1234567890       |
      | complaintType  | Customer Service |
      | priority       | 4                |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I verify the sr status is updated to close and disposition is set to "Complaint Resolved"
    And I initiated bpm process get api by service request id for process name "INEB_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Closed || 4        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

