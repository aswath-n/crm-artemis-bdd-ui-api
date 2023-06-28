@CreateServiceRequest
Feature: Create Service Request Functionality Check

  @CP-1273 @CP-1273-01 @CP-4902-04 @crm-regression @ui-wf @ui-sr @vidya
  Scenario: Verification all fields and mandatory fields on create Service request page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General Service Request" service request page
    Then I verify create service request fields displayed
    Then I verify task type is selected as "General Service Request"
    And I will provide following information before creating task
      | taskType ||
      | priority ||
      | taskInfo |ABC|
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "TASK TYPE"
    And I verify task mandatory fields error message "PRIORITY"
    And I verify minimum lenght error message "TASK INFORMATION"
    Then I verify "taskPriority" single select drop down value
      | 1 |
      | 2 |
      | 3 |
      | 4 |
      | 5 |
    Then I verify Status drop down is not editable field
    And Close the soft assertions

  @CP-4901 @CP-4902 @CP-4901-02 @CP-20585 @CP-20585-02 @crm-regression @ui-wf @ui-sr @vidya
  Scenario: Verify only Active Service Request in create SR link and SR drop down and listed in alphabetical order
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I will get the all active service request which has "Csr" permission to create and edit for project ""
    When I logged into CRM with "Service Account 1" and select a project "BLCRM"
    Then I verify only active service request is displayed in create SR link
    And I verify only active service request is displayed in service request drop down in create SR page
    And Close the soft assertions

  @CP-4902 @CP-4902-05 @CP-30301 @CP-30301-10 @crm-regression @ui-wf @ui-sr @elvin @ruslan
  Scenario: Create Application SR and link to consumer and Task systematically
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Fernando" and Last Name as "Macejkovic"
    Then I expand the first record of the search result
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "General Service Request" service request page
    And I will provide following information before creating task
      | taskInfo                | Test CP-4902 |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I store sr id on edit page
    Then I verify "General Service Request" link section has all the business object linked : "Task,Consumer,Case"
    When I navigate to "Task Search" page
    And I will search with srId
    And I perform a Task Search by following fields
      | assignmentStatus | Assigned |
    And I click on search button on task search page
    Then I verify request payload "assignedFlag" contains assignedFlag "true" and serviceRequestInd "false"
    Then I verify No Records Available message is displayed
    And I perform a Task Search by following fields
      | assignmentStatus | Unassigned |
    And I click on search button on task search page
    Then I verify request payload "assignedFlag" contains assignedFlag "false" and serviceRequestInd "false"
    Then I verify No Records Available message is displayed
    And Close the soft assertions

  @CP-4902 @CP-4902-06 @crm-regression @ui-wf @ui-sr @elvin
  Scenario: Create Application SR and link to CR and Task
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I navigate to "General Service Request" service request page
    And I will provide following information before creating task
      | taskInfo                | Test CP-4902 |
    And I click on save button in create service request page
  #  Then I verify Success message is displayed for SR
    Then Wait for 3 seconds
    And I click on srId in active contact page and navigate to view sr details page
    Then I verify "General Service Request" link section has all the business object linked : "Task,Contact Record"
    And I Navigate to Contact Record details page
    Then Wait for 10 seconds
    Then I verify link section has "General Service Request" and other business object
    And I click on Active Contact widget
    Then Wait for 3 seconds
    Then I verify link section has "General Service Request" and other business object
    And Close the soft assertions

  @CP-4902 @CP-4902-07 @CP-30299 @CP-30299-01 @crm-regression @ui-wf @elvin @ruslan
  Scenario Outline: Validate Application SR created and data is saved in DB in BLCRM
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General Service Request" service request page
    Then I verify create service request fields displayed
    Then I verify task type is selected as "General Service Request"
    And I will provide following information before creating task
      | priority | 1 |
      | taskInfo | Test CP-4902 |
    And I click on save button in create service request page
 #   Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated get task by task id "getSRIDFromUi"
    And I run get task by task id API
    Then I verify task table record for the task
    And Close the soft assertions
    Examples:
      | taskId | taskType|srType                 | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||         |General Service Request| Open   |  today     |   1      ||            ||                ||               ||            ||          ||           ||

  @CP-14954 @CP-14954-01 @crm-regression @ui-wf @vidya
  Scenario: Initialize workflow when General Service Request is created through CP UI
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "General Service Request" service request page
    And I will provide following information before creating task
      | taskInfo                |Test CP-14954-01             |
    And I click on save button in create service request page
 #   Then I verify Success message is displayed for SR
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I initiated bpm process get api by service request id for process name "general-servicerequest"
    And I run bpm process get api by sr id
    And I will check response of service request bpm process
    And Close the soft assertions

  @CP-4902 @CP-4902-01 @CP-4902-02 @CP-4902-03 @crm-regression @ui-wf @elvin
  Scenario: Validate Create Service Request UI in CoverVA
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Appeals SR" service request page
    Then I verify CREATE SERVICE REQUEST header displayed in CSR page
    Then I will verify following fields exist
      | SR CATEGORY    |
      | SR TYPE        |
      | SR INFORMATION |
      | CONSUMER ID    |
      | DUE DATE       |
      | DUE IN         |
      | SOURCE         |
      | STATUS DATE    |
      | PRIORITY       |
      | STATUS         |
    Then I will verify following fields not exist
      | ASSIGNEE |
    And I will provide following information before creating task
      | taskType ||
      | priority ||
      | taskInfo | ABC |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "TASK TYPE"
    And I verify task mandatory fields error message "PRIORITY"
    And I verify minimum lenght error message "TASK INFORMATION"
    Then I verify "taskPriority" single select drop down value
      | 1 |
      | 2 |
      | 3 |
      | 4 |
      | 5 |
    Then I verify Status drop down is not editable field

  @CP-28060 @CP-28060-03 @crm-regression @ui-wf @ruslan
  Scenario Outline: BLCRM: Automatically cancel systematically linked tasks if SR is updated to closed status
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Member Matching" task page
    And I will provide following information before creating task
      | taskInfo | Test CP-dsds       |
      | assignee | Service AccountOne |
    And I click on save button in create task page
    When I navigate to "General Service Request" service request page
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    And I click on id of "General Service Request" in Links section of "TASK" page
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
   # And I verify task search results are displayed
   # And I click task id to get the results in descending order in Task Search
    And I click on Link button in task search
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    And I click on edit service request button
    And I will update the following information in edit task page
      | status        | Closed               |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | General SR Closed    |
    And I click on save button on task edit page
    Then I verify "General Three" task status is updated to "Cancelled" on the link section
    And I verify "Member Matching" task status is updated to "Created" on the link section
    Examples:
      | taskId | taskType         |srType| status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee           | createdBy | createdOn |contactReason|
      || Member Matching  || Created ||          ||            ||                || true          ||            || Service AccountOne || today     ||

  @CP-17859 @CP-17859-04 @crm-regression @ui-wf @priyal #will be failed due to CP-41341
  Scenario Outline: Verify link sections for General SR
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "General Service Request" service request page
    And I will provide following information before creating task
      | taskInfo | Test CP-17859-04 |
      | priority | 2                |
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    Then I verify "General Service Request" link section has all the business object linked : "Task,Consumer,Case"
    And I click id of "General Three" in Links section
    Then I verify "General Three" link section has all the business object linked : "Case,Service Request,Consumer"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Unknown"
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
    And I verify that Inbound Correspondence Page has all "General Service Request" linked objects
    And I click id of "General Service Request" in link section of inbound page
    Then I verify "General Service Request" link section has all the business object linked : "Task,Case,Consumer,Inbound Correspondence"
    And I click on ConsumerId on Links section
    And I verify user is navigate to Demographic info tab after clicking on external consumer id
    And I navigate to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    Then I navigate to view SR details page by clicking on sr id
    When I click on Inbound Correspondence Link from View Task Page
    Then I should see I am navigated to the Inbound Correspondence Details Page for "General SR"
    And I click on refresh button
    And I verify that Inbound Correspondence Page has all "General Service Request" linked objects
    And I click on "General Service Request" sr id on Inbound Correspondence Page link section
    And Close the soft assertions
    Examples:
      | taskId | taskType|srType                   | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      |        |         |General Service Request  | Open   |            | 2        |         |            |        |                |            | true          |            |            |        |          | Service AccountOne| today     |             |


  @CP-46978 @CP-47695 @CP-46978-01 @CP-48250 @CP-48250-01 @crm-regression @ui-wf @ozgen
  Scenario: Configure Tiered Support SR without case and field validations
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I navigate to "Tiered Support SR" service request page
    And I click on save button in create service request page
    Then I validate the system displays an Error Message: "A link to a Case is required before this SR can be created."
    Then I verify "Caller Name" field is enable and required
    Then I verify "Preferred Phone" field is enable and required
    Then I verify "Member Name" field is enable and required
    Then I verify "Originating Escalation Type" field is enable and required
    Then I verify "Escalation Reason" field is enable and required
    Then I verify "referredBy" single select drop down value
      | Agency   |
      | CNSI     |
      | Member   |
      | Other    |
      | Provider |
    #due to new user story CP-48366, escalation reason values will be dynamically changed based on originating escalation type values
  #  Then I verify "escalationReason" single select drop down value
  #    | Reason A |
  #    | Reason B |
  #    | Reason C |
    Then I verify "originatingEscalationType" single select drop down value
      | Level I    |
      | Level II   |
      | Level III  |
      | Supervisor |
    And Close the soft assertions

  @CP-46978 @CP-46978-02 @CP-48250 @CP-48250-02 @crm-regression @ui-wf @ozgen
  Scenario: Configure Tiered Support SR with case and field validations
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send API CALL for Create CaseConsumer
    And I initiated Link API
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Tiered Support SR" service request page
    And I will provide following information before creating task
      | taskInfo                  |Test CP-46978  |
      | callerName                | maxlength     |
      | preferredPhone            | 7892357810    |
      | referredBy                | Member        |
      | memberName                | consumerName  |
      | originatingEscalationType | Supervisor    |
      | escalationReason          | Reason A      |
    And I click on save button in create service request page
   And I click on "Task & Service Request" Tab on Contact Dashboard Page
   And I navigate to newly created SR in Task & Service Request Tab
   And I navigate to view SR details page by clicking on sr id
    Then I click on edit service request button
    And I verify "current escalation" task field is disabled
    And I verify "originating escalation type" task field is disabled
    And I will update the following information in edit task page
      | status | Closed |
    Then I verify "disposition" single select drop down value
      | Resolved   |
      | Unresolved |
    And Close the soft assertions