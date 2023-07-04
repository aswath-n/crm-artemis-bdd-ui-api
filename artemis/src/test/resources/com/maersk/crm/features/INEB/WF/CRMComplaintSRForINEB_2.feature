Feature: Validate INEB Complaint SR_2

  @CP-23608 @CP-23608-01 @CP-24968 @CP-24968-01 @CP-25642 @CP-25642-03 @CP-23606 @CP-23606-01 @CP-17599 @CP-17599-04 @CP-17859 @CP-17859-03 @CP-31982 @CP-31982-02 @crm-regression @ui-wf-ineb @vidya @priyal
  Scenario Outline: Trigger workflow when Complaint SR is created through UI
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I send API CALL for Create CaseConsumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched case and consumer created by api
    And I link the contact to an existing Case or Consumer Profile
    And I store external consumer id from Demographic Info Page
    And I store external case id from Demographic Info Page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | memberName     | Vidya Mithun     |
      | preferredPhone | 1234567890       |
      | complaintAbout | maersk          |
      | complaintType  | Customer Service |
      | incidentDate   | today            |
      | priority       | 3                |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I navigated to the Edit History tab ON Edit Task
    And I navigated to the sr Details tab ON Edit Task
    And I will click on back arrow on view task page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    And I verify the SR details displayed on view SR page
    And I initiated bpm process get api by service request id for process name "INEB_Complaint"
    And I run bpm process get api by sr id
    Then I will check response of service request bpm process
    And I have a Inbound Document that with the Inbound Document Type of "INEB Unknown"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on Create Link button and choose task or sr link
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And I click on Link button in task search
    And I click on refresh button
    And I verify that Inbound Correspondence Page has all "Customer Service Complaint" linked objects
    And I click id of "Customer Service Complaint" in link section of inbound page
    And I navigated to the Edit History tab ON Edit Task
    And I navigated to the sr Details tab ON Edit Task
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task,Consumer,Case,Contact Record,Inbound Correspondence"
    When I click on Inbound Correspondence Link from View Task Page
    Then I should see I am navigated to the Inbound Correspondence Details Page for "INEB Complaint"
    And I click on refresh button
    And I verify that Inbound Correspondence Page has all "Customer Service Complaint" linked objects
    And I click on "Customer Service Complaint" sr id on Inbound Correspondence Page link section
    And I click on CaseId on Links section
    And I verify user is navigate to Demographic info tab after clicking on external consumer id
    And I navigate to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click on ConsumerId on Links section
    And I verify user is navigate to Demographic info tab after clicking on external consumer id
    And I navigate to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    Then I navigate to view SR details page by clicking on sr id
    And I click id of "QA Review Complaint" in Links section
    Then I verify "QA Review Complaint" task fields are displayed
    Then I verify "QA Review Complaint" link section has all the business object linked : "Consumer,Case,Service Request"
    And I navigated to the Edit History tab ON Edit Task
    And I navigated to the Task Details tab ON Edit Task
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify latest task displayed in "Work Queue or My Task" page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And In search result click on task id to navigate to view page
    And I navigated to the Edit History tab ON Edit Task
    And I navigated to the Task Details tab ON Edit Task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                                                                   |
      | reasonForEdit | Corrected Data Entry                                                       |
      | taskInfo      | Validate Complaint Sr closed when we complete the QA Review Complaint Task |
      | actionTaken   | Created Evaluation                                                         |
      | disposition   | Complaint Invalid                                                          |
    And I click on save button on task edit page
    And I click id of "Customer Service Complaint" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Complaint Invalid"
    And I initiated bpm process get api by service request id for process name "INEB_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Open   || 3        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

  @CP-24969 @CP-24969-01 @CP-23607 @CP-23607-01 @CP-23609 @CP-23609-01 @CP-16788 @CP-16788-02 @CP-23611 @CP-23611-01 @crm-regression @ui-wf-ineb @priyal
  Scenario: Validate Customer Service Complaint link objects for QA Review Complaint and Supervisor Review Complaint
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I send API CALL for Create CaseConsumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I store external case id from Demographic Info Page
    And I store external consumer id from Demographic Info Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout | maersk          |
      | memberName     | Priyal           |
      | preferredPhone | 1234567890       |
      | incidentDate   | today            |
      | complaintType  | Customer Service |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I verify the SR details displayed on view SR page
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task,Consumer,Case,Contact Record"
    And I Navigate to Contact Record details page
    Then I verify contact record page link section has "Customer Service Complaint" and other business object: "Consumer Profile,Case,Service Request"
    And I click on id of "Customer Service Complaint" in Links section of "Contact Record" page
    Then I verify should I navigate to service request details
    And I click on Active Contact widget
    And I verify active contact page link section has "Customer Service Complaint" and other business object: "Consumer Profile,Case,Service Request"
    And I click on id of "Customer Service Complaint" in Links section of "Active Contact Record" page
    Then I verify should I navigate to service request details
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
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task,Task,Consumer,Case,Contact Record"
    When I click id of "Supervisor Review Complaint" in Links section
    Then I verify "Supervisor Review Complaint" link section has all the business object linked : "Consumer,Case,Service Request"
    When I click id of "Customer Service Complaint" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit    | Corrected Data Entry          |
      | escalated        | true                          |
      | escalationReason | Consumer Contacting the State |
    And I click on save button on task edit page
    And I will click on back arrow on view task page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    When I click id of "State Escalated Complaint" in Links section
    Then I verify "State Escalated Complaint" link section has all the business object linked : "Consumer,Case,Service Request"
    And Close the soft assertions

  @CP-24969 @CP-24969-03 @CP-29557 @CP-29557-02 @CP-23611 @CP-23611-02 @crm-regression @ui-wf-ineb @priyal
  Scenario Outline: Validate Customer Service Complaint link objects and verify unlink button is not displaying for case/consumer
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout | maersk          |
      | incidentDate   | today            |
      | memberName     | Priyal           |
      | preferredPhone | 1234567890       |
      | complaintType  | Customer Service |
      | priority       | 5                |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","Customer Service Complaint","Open","<statusDate>","5","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","true","<consumerFN>","<consumerLN>","<source>","<assignee>","Service TesterTwo","today","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task"
    When I click id of "QA Review Complaint" in Links section
    Then I verify "QA Review Complaint" link section has all the business object linked : "Service Request"
    And I click on edit button on view task page
    And I click on contact link button under LINK section
    And I Verify Task service Request Button is not displaying in the create link section for SR Task Type
    And I will update the following information in edit task page
      | status        | Complete               |
      | reasonForEdit | Corrected Data Entry   |
      | actionTaken   | Created Summary Report |
      | disposition   | Complaint Reviewed     |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    When I click id of "Supervisor Review Complaint" in Links section
    Then I verify "Supervisor Review Complaint" link section has all the business object linked : "Service Request"
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
    And I click on edit service request button
    Then I verify unlink button is not displayed for "Case,Consumer" in link the section
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I click on cancel button on task search page
    And I click on save button on task edit page
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I populate required fields to do task search "1565","QA Review Complaint","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And I click on Link button in task search
    Then I will verify user gets error message pop up for linking task created by SR to another SR
    And I click on cancel button on task search page
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task,Task,Consumer,Case"
    When I click id of "QA Review Complaint" in Links section
    Then I verify "QA Review Complaint" link section has all the business object linked : "Consumer,Case,Service Request"
    When I click id of "Customer Service Complaint" in Links section
    When I click id of "Supervisor Review Complaint" in Links section
    Then I verify "Supervisor Review Complaint" link section has all the business object linked : "Consumer,Case,Service Request"
    When I click id of "Customer Service Complaint" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit    | Corrected Data Entry          |
      | escalated        | true                          |
      | escalationReason | Consumer Contacting the State |
    And I click on save button on task edit page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And In search result click on task id to navigate to view page
    When I click id of "Customer Service Complaint" in Links section
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task,Task,Task,Consumer,Case"
    When I click id of "State Escalated Complaint" in Links section
    Then I verify "State Escalated Complaint" task fields are displayed
    Then I verify "State Escalated Complaint" link section has all the business object linked : "Consumer,Case,Service Request"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||          ||        ||          ||            ||                ||               ||            ||          ||           ||

    #  refactoring by priyal
  @CP-23607 @CP-23607-03 @crm-regression @ui-wf-ineb @priyal
  Scenario: Validate Customer Service Complaint link Consumer or contact record only
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Tara" and Last Name as "Jain"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout | maersk          |
      | incidentDate   | today            |
      | memberName     | Priyal           |
      | preferredPhone | 1234567890       |
      | complaintType  | Customer Service |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Customer Service Complaint" link section has all the business object linked : "Task,Consumer,Contact Record"
    And Close the soft assertions

  @CP-24976 @CP-24976-01 @CP-24977 @CP-24977-01 @CP-25093 @CP-25093-01 @CP-24097 @CP-24097-01 @CP-24975 @CP-24975-01 @CP-24970 @CP-24970-01 @crm-regression @ui-wf-ineb @priyal #will fail due to CP-41963
  Scenario Outline: Validate 2nd and 3rd call attempt for Complaint SR and verify Link Component for Complaint Outbound Call and Sub-process is ended
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I send API CALL for Create CaseConsumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I store external case id from Demographic Info Page
    And I store external consumer id from Demographic Info Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout | maersk          |
      | incidentDate   | today            |
      | memberName     | Priyal           |
      | preferredPhone | 1234567890       |
      | complaintType  | Customer Service |
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
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
    Then I verify "Complaint Outbound Call" link section has all the business object linked : "Consumer,Case,Service Request"
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
    Then I verify "Complaint Outbound Call" task fields are displayed
    Then I verify "Complaint Outbound Call" link section has all the business object linked : "Consumer,Case,Service Request"
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
    Then I verify "Complaint Outbound Call" task fields are displayed
    Then I verify "Complaint Outbound Call" link section has all the business object linked : "Consumer,Case,Service Request"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                        |
      | reasonForEdit  | Corrected Data Entry                            |
      | disposition    | <disposition>                                   |
      | preferredPhone | 1234567890                                      |
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail |
      | contactName    | Alewa Papovich                                  |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    Then I verify link section contains 3 "Complaint Outbound Call"
    Then I verify the sr status is updated to close and disposition is set to "<dispositionValue>"
    And I initiated bpm process get api by service request id for process name "INEB_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | disposition                   | dispositionValue                          |
      | Did Not Reach Consumer        | Complaint Closed - Did Not Reach Consumer |
      | Successfully Reached Consumer | Complaint Resolved                        |

 # @CP-24976 @CP-24976-02 @CP-24977 @CP-24977-02 @CP-25093 @CP-25093-02 @CP-24097 @CP-24097-02 @CP-24975 @CP-24975-02 @CP-24970 @CP-24970-05 @crm-regression @ui-wf-ineb @priyal
  #based on CP-24970 AC 3.0 if complaint type is Misinformation, we expect to see Outbound Call Task
    #but this value is removed from dropdown by referring CP-35081
  Scenario Outline: Validate Save event for 2nd and 3rd call attempt for Complaint SR for Outbound CAll and Sub-process is ended
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I send API CALL for Create CaseConsumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I store external case id from Demographic Info Page
    And I store external consumer id from Demographic Info Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout | maersk        |
      | incidentDate   | today          |
      | memberName     | Priyal         |
      | preferredPhone | 1234567890     |
      | complaintType  | Misinformation |
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
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
    When I click id of "Outbound Call" in Links section
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
    And I click on id of "Customer Service Complaint" in Links section of "Task" page
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
    And I click on id of "Customer Service Complaint" in Links section of "Task" page
    And I click on task id with status created in the link section
    Then I verify "Outbound Call" task fields are displayed
    Then I verify "Outbound Call" link section has all the business object linked : "Consumer,Case,Service Request"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                        |
      | reasonForEdit  | Corrected Data Entry                            |
      | disposition    | <disposition>                                   |
      | preferredPhone | 1234567890                                      |
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail |
      | contactName    | Alewa Papovich                                  |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    Then I verify link section contains 3 "Outbound Call"
    Then I verify the sr status is updated to close and disposition is set to "<dispositionValue>"
    And I initiated bpm process get api by service request id for process name "INEB_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | disposition                   | dispositionValue                          |
      | Did Not Reach Consumer        | Complaint Closed - Did Not Reach Consumer |
      | Successfully Reached Consumer | Complaint Resolved                        |

  @CP-24977 @CP-24977-03 @CP-25093 @CP-25093-03 @CP-24097 @CP-24097-03 @crm-regression @ui-wf-ineb @priyal
  Scenario: Validate Sub-Process is ended for Complaint SR for Complaint Outbound CAll
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "FXItXNW" and Last Name as "pIfWdYx"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout | maersk          |
      | incidentDate   | today            |
      | memberName     | Priyal           |
      | preferredPhone | 1234567890       |
      | complaintType  | Customer Service |
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click id of "QA Review Complaint" in Links section
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
    Then I verify "Complaint Outbound Call" task fields are displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         | Complete                                        |
      | reasonForEdit  | Corrected Data Entry                            |
      | disposition    | Successfully Reached Consumer                   |
      | preferredPhone | 1234567890                                      |
      | actionTaken    | Contacted Consumer - Did Not Reach/No Voicemail |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    Then I verify link section contains 1 "Complaint Outbound Call"
    Then I verify the sr status is updated to close and disposition is set to "Complaint Resolved"
    And I initiated bpm process get api by service request id for process name "INEB_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-24967 @CP-24967-01 @CP-25094 @CP-25094-03 @crm-regression @ui-wf-ineb @priyal
  Scenario Outline: Validate Close the Complaint SR - emergency with Complint About = Emergent Situation and Verify Cancelled status for 2 Complaint Outbound Call Task
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
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
    And I store the task id for created status from the link section
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit  | Corrected Data Entry |
      | complaintAbout | Other                |
      | organizationDD | Anthem               |
    And I click on save button on task edit page
    Then I verify "Complaint Outbound Call" task status is updated to "Cancelled" in the link section for multiple tasks
    Then I verify the sr status is updated to close and disposition is set to "Complaint Resolved"
    And I initiated bpm process get api by service request id for process name "INEB_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Open   || 5        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

  @CP-24967 @CP-24967-02 @CP-25094 @CP-25094-04 @CP-28211 @CP-28211-02 @crm-regression @ui-wf-ineb @priyal @kamil #will fail due to CP-41963
  Scenario Outline: Validate Close the Complaint SR - emergency with Complint About = Other and Verify Cancelled status for 1 Complaint Outbound Call Task and Create status for State Escalated Complaint
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout | maersk          |
      | incidentDate   | today            |
      | memberName     | Priyal           |
      | preferredPhone | 1234567890       |
      | complaintType  | Customer Service |
      | priority       | 3                |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    When I click id of "QA Review Complaint" in Links section
    Then I verify "QA Review Complaint" task fields are displayed
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
      | reasonForEdit    | Corrected Data Entry          |
      | complaintAbout   | Other                         |
      | organizationDD   | Anthem                        |
      | escalated        | true                          |
      | escalationReason | Consumer Contacting the State |
    And I click on save button on task edit page
    When I click id of "Supervisor Review Complaint" in Links section
    When I click id of "Customer Service Complaint" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Complaint Resolved - Emergency"
    Then I verify "Complaint Outbound Call" task status is updated to "Cancelled" in the link section for multiple tasks
    And I verify "State Escalated Complaint" task status is updated to "Created" on the link section
    And I initiated bpm process get api by service request id for process name "INEB_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    When I click id of "State Escalated Complaint" in Links section
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Open   || 3        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

  @CP-24967 @CP-24967-03 @CP-25094 @CP-25094-05 @crm-regression @ui-wf-ineb @priyal
  Scenario Outline: Validate Close the Complaint SR - non emergency with Complint About = Emergent Situation and Verify Cancelled status for Supervisor Review Complaint Task
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout | maersk          |
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
    When I click id of "QA Review Complaint" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete               |
      | reasonForEdit | Corrected Data Entry   |
      | actionTaken   | Created Summary Report |
      | disposition   | Complaint Reviewed     |
    And I click on save button on task edit page
    When I click id of "Customer Service Complaint" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit  | Corrected Data Entry |
      | complaintAbout | Emergent Situation   |
    And I click on save button on task edit page
    Then I verify "Supervisor Review Complaint" task status is updated to "Cancelled" on the link section
    Then I verify the sr status is updated to close and disposition is set to "Complaint Resolved"
    And I initiated bpm process get api by service request id for process name "INEB_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Open   || 4        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||

  @CP-24967 @CP-24967-04 @CP-25094 @CP-25094-02 @crm-regression @ui-wf-ineb @priyal #will fail due to CP-41963
  Scenario Outline: Validate Close the Complaint SR - non emergency with Complint About = Other and Verify Cancelled status for 3 Complaint Outbound Call Task
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
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
      | reasonForEdit    | Corrected Data Entry          |
      | complaintAbout   | Emergent Situation            |
      | escalated        | true                          |
      | escalationReason | Consumer Contacting the State |
    And I click on save button on task edit page
    When I click id of "Supervisor Review Complaint" in Links section
    When I click id of "Customer Service Complaint" in Links section
    Then I verify "Complaint Outbound Call" task status is updated to "Cancelled" in the link section for multiple tasks
    And I verify "State Escalated Complaint" task status is updated to "Created" on the link section
    Then I verify the sr status is updated to close and disposition is set to "Complaint Resolved - Emergency"
    And I initiated bpm process get api by service request id for process name "INEB_Complaint"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    When I click id of "State Escalated Complaint" in Links section
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Open   || 2        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||