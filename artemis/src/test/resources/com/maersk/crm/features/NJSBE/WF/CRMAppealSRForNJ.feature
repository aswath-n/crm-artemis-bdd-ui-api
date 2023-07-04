Feature: Validation of NJ-SBE Appeal SR

  @CP-24422 @CP-24422-01 @CP-24424 @CP-24424-01 @CP-25642 @CP-25642-02 @CP-18423 @CP-18423-01 @CP-17599 @CP-17599-03 @CP-31982 @CP-31982-05 @crm-regression @ui-wf-nj @nj-regression @ozgen @vidya @priyal
  Scenario: Create Appeal SR from inbound correspondence document and validate GCNJ Appeals Acknowledgement Letter and Review Appeals Form task are created
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | Channel      | Mail                   |
      | documentType | NJ SBE Appeal Form     |
    When I send the request to create the Inbound Correspondence Save Event
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    Then I will verify "Appeal" Details view SR details page
    And I initiated bpm process get api by service request id for process name "NJSBE_Appeal"
    And I run bpm process get api by sr id
    And I will check response of service request bpm process
    And I navigated to the Edit History tab ON Edit Task
    And I navigated to the sr Details tab ON Edit Task
    And I click id of "GCNJ Appeals Acknowledgement Letter" in Links section
    Then I verify "GCNJ Appeals Acknowledgement Letter" task fields are displayed
    And I click id of "Appeal" in Links section
    And I click id of "Review Appeals Form" in Links section
    Then I verify "Review Appeals Form" task fields are displayed
    And I navigated to the Edit History tab ON Edit Task
    And I navigated to the Task Details tab ON Edit Task
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify latest task displayed in "Work Queue or My Task" page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And Close the soft assertions

  @CP-25110 @CP-25110-01 @CP-24423 @CP-24423-01 @CP-16788 @CP-16788-01 @crm-regression @ui-wf-nj @nj-regression @vidya
  Scenario: Verify Link section of Appeal SR and linked tasks and inbound document
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | Channel      | Mail                   |
      | documentType | NJ SBE Appeal Form     |
      | ConsumerId   | 2137                   |
      | CaseId       | 734                    |
    When I send the request to create the Inbound Correspondence Save Event
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    Then I verify "Appeal" link section has all the business object linked : "Task,Task,Inbound Correspondence,Case,Consumer"
    And I click id of "GCNJ Appeals Acknowledgement Letter" in Links section
    Then I verify "GCNJ Appeals Acknowledgement Letter" link section has all the business object linked : "Service Request,Inbound Correspondence,Case,Consumer"
    And I click id of "Appeal" in Links section
    And I click id of "Review Appeals Form" in Links section
    Then I verify "Review Appeals Form" link section has all the business object linked : "Service Request,Inbound Correspondence,Case,Consumer"
    When I click on Inbound Correspondence Link from View Task Page
    Then I should see I am navigated to the Inbound Correspondence Details Page for "NJ Appeal"
    And I verify that Inbound Correspondence Page has all "Appeal" linked objects
    And I click on "Appeal" sr id on Inbound Correspondence Page link section
    Then I will verify "Appeal" Details view SR details page
    And Close the soft assertions

  @CP-24429 @CP-24429-01 @CP-24430 @CP-24430-01 @CP-25286 @CP-25286-01 @CP-28293 @CP-28293-04 @CP-25296 @CP-25296-03 @CP-28295 @CP-28295-01 @CP-28060 @CP-28060-04 @CP-18423 @CP-18423-02 @crm-regression @ui-wf-nj @nj-regression @vidya @ruslan
  Scenario: Verify Follow-Up on Appeal task is created for Appeal sr when complete the Review Appeals Form task from edit page and validate appeal sr closed when follow-up on appeal task completed
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | Channel      | Mail                   |
      | documentType | NJ SBE Appeal Form     |
      | ConsumerId   | 2138                   |
      | CaseId       | 735                    |
    When I send the request to create the Inbound Correspondence Save Event
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I will navigate to newly created task by clicking on task id
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                                                   |
      | reasonForEdit | Corrected Data Entry                                       |
      | taskInfo      | Validate Follow up on appeal task is created for appeal sr |
      | actionTaken   | No Action Taken                                            |
      | disposition   | IDR Unsuccessful                                           |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click id of "Appeal" in Links section
    Then I verify "Appeal" link section has all the business object linked : "Task,Task,Task,Inbound Correspondence,Case,Consumer"
    And I click id of "Follow-Up on Appeal" in Links section
    Then I verify "Follow-Up on Appeal" task fields are displayed
    Then I verify "Follow-Up on Appeal" link section has all the business object linked : "Service Request,Consumer,Inbound Correspondence,Case"
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I verify latest task displayed in "Work Queue or My Task" page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                                                                |
      | reasonForEdit | Corrected Data Entry                                                    |
      | taskInfo      | Validate Appeal sr is closed when Follow-up on appeal task is completed |
      | actionTaken   | No Action Taken                                                         |
      | disposition   | IDR Successful                                                          |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click id of "Appeal" in Links section
    Then I verify the sr status is updated to close and disposition is set to "IDR Successful"
    Then I verify "Appeal" link section has all the business object linked : "Task,Task,Task,Task,Inbound Correspondence,Case,Consumer"
    And I initiated bpm process get api by service request id for process name "NJSBE_Appeal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And I click id of "Generate IDR Successful Letter" in Links section
    Then I verify "Generate IDR Successful Letter" task fields are displayed
    Then I verify "Generate IDR Successful Letter" link section has all the business object linked : "Service Request,Consumer,Inbound Correspondence,Case"
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And Close the soft assertions

  @CP-28293 @CP-28293-01 @CP-24425 @CP-24425-01 @CP-21344 @CP-21344-05 @CP-25296 @CP-25296-04 @CP-28295 @CP-28295-02 @CP-18423 @CP-18423-03 @crm-regression @ui-wf-nj @nj-regression @vidya @priyal
  Scenario: Verify Generate IDR Successful Letter task is created for Appeal sr when complete the Review Appeals Form task from edit page and check appeal sr is closed
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | Channel      | Mail                   |
      | documentType | NJ SBE Appeal Form     |
      | ConsumerId   | 2142                   |
      | CaseId       | 736                    |
    When I send the request to create the Inbound Correspondence Save Event
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                                                   |
      | reasonForEdit | Corrected Data Entry                                       |
      | taskInfo      | Validate GCNJ Resolve Appeal task is created for appeal sr |
      | actionTaken   | Outbound Call Successful                                   |
      | disposition   | IDR Successful                                             |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click id of "Appeal" in Links section
    Then I verify the sr status is updated to close and disposition is set to "IDR Successful"
    Then I verify "Appeal" link section has all the business object linked : "Task,Task,Task,Inbound Correspondence,Case,Consumer"
    And I initiated bpm process get api by service request id for process name "NJSBE_Appeal"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And I click id of "Generate IDR Successful Letter" in Links section
    Then I verify "Generate IDR Successful Letter" task fields are displayed
    Then I verify "Generate IDR Successful Letter" link section has all the business object linked : "Service Request,Consumer,Inbound Correspondence,Case"
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And Close the soft assertions

  @CP-29557 @CP-29557-05 @crm-regression @ui-wf-nj @nj-regression @priyal
  Scenario Outline: Verify link and Unlink functionality for NJ-SBE project
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | Channel      | Mail                   |
      | documentType | NJ SBE Appeal Form     |
    When I send the request to create the Inbound Correspondence Save Event
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    Then I verify unlink button is not displayed for "Review Appeals Form,GCNJ Appeals Acknowledgement Letter,NJ SBE Appeal Form" in link the section
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I click on cancel button on task search page
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And I click on Link button in task search
    Then I will verify user gets error message pop up for linking task created by SR to another SR
    And I click on cancel button on task search page
    And I click id of "Review Appeals Form" in Links section
    And I click on edit button on view task page
    Then I verify unlink button is not displayed for "Appeal,NJ SBE Appeal Form" in link the section
    And I click on contact link button under LINK section
    And I Verify Task service Request Button is not displaying in the create link section for SR Task Type
    And Close the soft assertions
    Examples:
      | taskId | taskType                       | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      | 14059  | Generate IDR Successful Letter ||        ||          ||            ||                ||               ||            ||          ||           ||

  @CP-29557 @CP-29557-06 @CP-17859 @CP-17859-01 @crm-regression @ui-wf-nj @nj-regression @priyal
  Scenario: Verify Unlink button is not displaying for Other business objects on NJ-SBE project
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | Channel      | Mail                   |
      | documentType | NJ SBE Appeal Form     |
    When I send the request to create the Inbound Correspondence Save Event
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on contact link button under LINK section
    And I click on Case and Consumer button
    Then I see Case& Consumer Search section display
    When I searched customer have First Name as "Jessica" and Last Name as "Alba"
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    And I select Consumer radio button
    And I click on validate link button
    When I click on Link Record Consumer button
    And I click on save button on task edit page
    Then I verify "Appeal" link section has all the business object linked : "Task,Task,Consumer,Case,Inbound Correspondence"
    And I click id of "Review Appeals Form" in Links section
    Then I verify "Review Appeals Form" link section has all the business object linked : "Consumer,Case,Inbound Correspondence,Service Request"
    When I click id of "Appeal" in Links section
    When I click id of "GCNJ Appeals Acknowledgement Letter" in Links section
    Then I verify "GCNJ Appeals Acknowledgement Letter" link section has all the business object linked : "Consumer,Case,Inbound Correspondence,Service Request"
    When I click id of "Appeal" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    Then I verify unlink button is not displayed for "Case,Consumer" in link the section
    And I click on save button on task edit page
    When I click on Inbound Correspondence Link from View Task Page
    Then I should see I am navigated to the Inbound Correspondence Details Page for "NJ Appeal"
    And I click on refresh button
    And I verify that Inbound Correspondence Page has all "Appeal" linked objects
    And I click on "Appeal" sr id on Inbound Correspondence Page link section
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
    And Close the soft assertions

  @CP-18704 @CP-18704-03 @crm-regression @ui-wf-nj @nj-regression @kamil
  Scenario: Verify Edit SR to add Consumer Link and verify Task is also linked with No IB Correspondence Link
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | Channel      | Mail                   |
      | documentType | NJ SBE Appeal Form     |
    When I send the request to create the Inbound Correspondence Save Event
    When I navigate to "Task Search" page
    And I will search with srId
    Then I verify following information in task search result
      | taskType    | Appeal |
      | caseID1     | -- --  |
      | consumerID1 | -- --  |
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I click on Case and Consumer button
    Then I see Case& Consumer Search section display
    When I searched customer have First Name as "Penny" and Last Name as "Lane"
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    And I select Consumer radio button
    And I click on validate link button
    When I click on Link Record Consumer button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "Appeal" link section has all the business object linked : "Task,Task,Consumer,Inbound Correspondence,Case"
    And I click on task id with status created in the link section
    Then I verify "Review Appeals Form" link section has all the business object linked : "Consumer,Inbound Correspondence,Service Request,Case"
    And I click id of "Appeal" in Links section
    And I click id of "GCNJ Appeals Acknowledgement Letter" in Links section
    Then I verify "GCNJ Appeals Acknowledgement Letter" link section has all the business object linked : "Service Request,Inbound Correspondence,Case,Consumer"
    And Close the soft assertions

  @CP-28060 @CP-28060-02 @crm-regression @ui-wf-nj @nj-regression @ruslan
  Scenario Outline: NJ-SBE: Automatically cancel systematically linked tasks if SR is updated to closed status
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    When I navigate to "Review Complaint" task page
    And I will provide following information before creating task
      | taskInfo       | Test CP-10685      |
      | assignee       | Service AccountOne |
      | complaintAbout | CAC (maersk)      |
      | name           | ISHAK ISHAKOV      |
      | reason         | Customer Service   |
    And I click on save button in create task page
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | Channel      | Mail                   |
      | documentType | NJ SBE Appeal Form     |
      | ConsumerId   | 2137                   |
      | CaseId       | 734                    |
    When I send the request to create the Inbound Correspondence Save Event
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I click on cancel button on task search page
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And I click task id to get the results in descending order in Task Search
    And I click on Link button in task search
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    And I click on edit service request button
    And I will update the following information in edit task page
      | status        | Closed               |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | IDR Successful       |
    And I click on save button on task edit page
    Then I verify "Review Appeals Form" task status is updated to "Cancelled" on the link section
    Then I verify "GCNJ Appeals Acknowledgement Letter" task status is updated to "Created" on the link section
    Then I verify "Review Complaint" task status is updated to "Created" on the link section
    Examples:
      | taskId | taskType         | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee           | createdBy | createdOn |contactReason|
      || Review Complaint ||        ||          ||            ||                || true          ||            || Service AccountOne || today     ||

  @CP-29557 @CP-29557-07 @crm-regression @ui-wf-nj @nj-regression @priyal
  Scenario Outline: Verify link and Unlink functionality for NJ-SBE project
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | Channel      | Mail                   |
      | documentType | NJ SBE Appeal Form     |
    When I send the request to create the Inbound Correspondence Save Event
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I click on cancel button on task search page
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And I click on Link button in task search
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "Appeal" contains : "Review Appeals Form,GCNJ Appeals Acknowledgement Letter,Outbound Call" on "SR" link section
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I unlink "Outbound Call" from link section
    And I click on save button on task edit page
    Then I verify "Appeal" contains : "GCNJ Appeals Acknowledgement Letter,Review Appeals Form" on "SR" link section
    And Close the soft assertions
    Examples:
      | taskId | taskType      | srType | status   | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      || Outbound Call || Complete ||   3      ||            ||                ||   True        ||            ||          | Service AccountOne||             |