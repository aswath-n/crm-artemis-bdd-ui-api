Feature: Validation SR Link and UnLink functionality

  @CP-27696 @CP-27696-01 @CP-22677 @CP-22677-01 @CP-30300 @CP-30300-01 @crm-regression @ui-wf-ineb @ruslan
  Scenario Outline: Validate Manually Link & Unlink a Service Request to Service Request on edit page for IN-EB
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout | maersk          |
      | incidentDate   | today            |
      | memberName     | Ruslan           |
      | preferredPhone | 1234567890       |
      | complaintType  | Customer Service |
      | priority       | 5                |
    And I click on save button in create service request page
    When I navigate to case and consumer search page
    When I searched customer have First Name as "NANCY" and Last Name as "KIYAN"
    When I expand the record to navigate Case Consumer Record from manual view
    Then I store external consumer id from Demographic Info Page
    And I store external consumer id from Demographic Info Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                 | 521521521512         |
      | contactName         | Alewa                |
      | contactPhone        | 5215215215           |
      | currentPlan         | Anthem               |
      | desiredPlanJC       | Anthem               |
      | programRequired     | Healthy Indiana Plan |
      | missingInfoRequired | No                   |
    And I click on save button in create service request page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I initiated search records API
    And I validate serviceRequestInd value for "SR"
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And I click on Link button in task search
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "Just Cause" contains : "Just Cause Request" on "SR" link section
    Then I verify "Just Cause" contains : "Customer Service Complaint" on "TASK" link section
    When I click id of "Just Cause Request" in Links section
    And I initiated search records API
    And I validate serviceRequestInd value for "TASK"
    Then I verify "Just Cause Request" contains : "Just Cause" on "TASK" link section
    And I click on id of "Just Cause" in Links section of "SR" page
    And I click on id of "Customer Service Complaint" in Links section of "SR" page
    Then I verify "Customer Service Complaint" contains : "QA Review Complaint" on "SR" link section
    Then I verify "Customer Service Complaint" contains : "Just Cause" on "TASK" link section
    When I click id of "QA Review Complaint" in Links section
    Then I verify "QA Review Complaint" contains : "Customer Service Complaint" on "TASK" link section
    And I click on id of "Customer Service Complaint" in Links section of "TASK" page
    And I click on edit service request button
    And I unlink "Just Cause" from link section
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "Customer Service Complaint" does not contains : "Just Cause" on link section
    And I click on id of "QA Review Complaint" in Links section of "SR" page
    Then I verify "QA Review Complaint" contains : "Customer Service Complaint" on "TASK" link section
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      |        |          | Customer Service Complaint |        |            | 5        |         |            |        |                |            | true          |            |            |        |          |           | today     |               |

  @CP-27696 @CP-27696-02 @CP-22677 @CP-22677-02 @CP-30300 @CP-30300-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan
  Scenario Outline: Validate Manually Link & Unlink a Service Request to Service Request on edit page for CoverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Complaint SR" service request page
    And I will provide following information before creating task
      | taskInfo | Test CP-18749 |
      | priority | 5             |
    And I click on save button in create service request page
    When I navigate to case and consumer search page
    When I searched customer have First Name as "AlexandreSQPLf" and Last Name as "RennerKYOJN"
    When I click the first consumer id from the search results
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "App SR V1 " service request page
    And I will provide following information before creating task
      | taskInfo              | Test fsafasfa                  |
      | applicationType       | Pre-Release Application - CVIU |
      | externalApplicationId | random                         |
    And I click on save button in create service request page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I initiated search records API
    And I validate serviceRequestInd value for "SR"
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And I click on Link button in task search
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "App SR V1" contains : "Process App V1" on "SR" link section
    Then I verify "App SR V1" contains : "Complaint SR" on "TASK" link section
    When I click id of "Process App V1" in Links section
    And I initiated search records API
    And I validate serviceRequestInd value for "TASK"
    Then I verify "Process App V1" contains : "App SR V1" on "TASK" link section
    When I click id of "App SR V1" in Links section
    When I click id of "Complaint SR" in Links section
    Then I verify "Complaint SR" contains : "Review Complaint" on "SR" link section
    Then I verify "Complaint SR" contains : "App SR V1" on "TASK" link section
    When I click id of "Review Complaint" in Links section
    Then I verify "Review Complaint" contains : "Complaint SR" on "TASK" link section
    When I click id of "Complaint SR" in Links section
    And I click on edit service request button
    And I unlink "App SR V1 " from link section
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "Complaint SR" does not contains : "App SR V1" on link section
    And I click on id of "Review Complaint" in Links section of "SR" page
    Then I verify "Review Complaint" contains : "Complaint SR" on "TASK" link section
    And Close the soft assertions
    Examples:
      | taskId | taskType|srType       | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||         |Complaint SR ||            | 5        ||            ||                || true          ||            ||          || today     ||

  @CP-27696 @CP-27696-03 @CP-22677 @CP-22677-03 @CP-30300 @CP-30300-03 @crm-regression @ui-wf @ruslan
  Scenario Outline: Validate Manually Link & Unlink a Service Request to Service Request on edit page for BLCRM
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "General Service Request" service request page
    And I will provide following information before creating task
      | taskInfo | First GSR |
      | priority | 5         |
    And I click on save button in create service request page
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Tomon" and Last Name as "Kha"
    When I click the first consumer id from the search results
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "General Service Request" service request page
    And I will provide following information before creating task
      | taskInfo | Second GSR |
    And I click on save button in create service request page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to view SR details page by clicking on sr id
    And I initiated search records API
    And I validate serviceRequestInd value for "SR"
    And I click on edit service request button
    And I scroll up the page
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And I click on Link button in task search
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "General Service Request" contains : "General Three" on "SR" link section
    Then I verify "General Service Request" contains : "General Service Request" on "TASK" link section
    When I click id of "General Three" in Links section
    And I initiated search records API
    And I validate serviceRequestInd value for "TASK"
    Then I verify "General Three" contains : "General Service Request" on "TASK" link section
    And I click on id of "General Service Request" in Links section of "TASK" page
    And I click on id of "General Service Request" in Links section of "SR" page
    Then I verify "General Service Request" contains : "General Three" on "SR" link section
    Then I verify "General Service Request" contains : "General Service Request" on "TASK" link section
    When I click id of "General Three" in Links section
    Then I verify "General Three" contains : "General Service Request" on "TASK" link section
    And I click on id of "General Service Request" in Links section of "SR" page
    And I click on edit service request button
    And I unlink "General Service Request" from link section
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "General Service Request" does not contains : "General Service Request" on link section
    And I click on id of "General Three" in Links section of "SR" page
    Then I verify "General Three" contains : "General Service Request" on "TASK" link section
    And Close the soft assertions
    Examples:
      | taskId | taskType |srType                   | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||          | General Service Request ||            | 5        ||            ||                ||               | true       ||        ||           | today     ||

  @CP-27696 @CP-27696-04 @CP-22677 @CP-22677-04 @CP-30300 @CP-30300-04 @ui-wf-nj @nj-regression @ruslan # Not completed
  Scenario Outline: Validate Manually Link & Unlink a Service Request to Service Request on edit page for NJ-SBE
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | Channel      | Mail                   |
      | documentType | NJ SBE Appeal Form     |
    When I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And In search result click on task id to navigate to view page
    And I click on id of "Appeal" in Links section of "TASK" page
    And I initiated search records API
    And I validate serviceRequestInd value for "TASK"
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | Channel      | Mail                   |
      | documentType | NJ SBE Appeal Form     |
    When I send the request to create the Inbound Correspondence Save Event
    And I click on cancel button on task search page
    And I click on edit service request button
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And I click on Link button in task search
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "Appeal" contains : "Appeal" on "TASK" link section
    And I click on id of "Appeal" in Links section of "SR" page
    Then I verify "Appeal" contains : "Appeal" on "TASK" link section
    And I click on id of "Appeal" in Links section of "SR" page
    And I store sr id on edit page
    And I click on edit service request button
    And I initiated search records API
    And I validate serviceRequestInd value for "SR"
    And I unlink "Appeal" from link section
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "Appeal" does not contains : "Appeal" on link section
    And Close the soft assertions
    Examples:
      | taskId | taskType|srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||         |Appeal | Open   ||          ||            ||                || true          ||            ||          || today     ||

  @CP-27696 @CP-27696-05 @CP-22677 @CP-22677-05 @crm-regression @ui-wf-ineb @ruslan
  Scenario Outline: Validate Manually Link & Unlink a Service Request to Service Request on create page for IN-EB
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | complaintAbout | maersk        |
      | incidentDate   | today          |
      | memberName     | Ruslan         |
      | preferredPhone | 1234567890     |
      | complaintType  | Customer Service |
      | priority       | 5              |
    And I click on save button in create service request page
    When I navigate to "Just Cause" service request page
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And I click task id to get the results in descending order in Task Search
    And I click on Link button in task search
    And I will provide following information before creating task
      | rid                   | 521521521512                                 |
      | contactName           | Alewa                                        |
      | contactPhone          | 5215215215                                   |
      | currentPlan           | Anthem                                       |
      | desiredPlanJC         | Anthem                                       |
      | programRequired       | Healthy Indiana Plan                         |
      | missingInfoRequired   | No                                           |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for task
    And I populate required fields to do task search "getFromUI","","","<status>","<statusDate>","","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","","<contactReason>"
    And I click on search button on task search page
    And In search result click on task id to navigate to view page
    And I click on id of "Just Cause" in Links section of "SR" page
    Then I verify "Just Cause" contains : "Just Cause Request" on "SR" link section
    Then I verify "Just Cause" contains : "Customer Service Complaint" on "TASK" link section
    When I click id of "Just Cause Request" in Links section
    Then I verify "Just Cause Request" contains : "Just Cause" on "TASK" link section
    When I click id of "Just Cause" in Links section
    When I click id of "Customer Service Complaint" in Links section
    Then I verify "Customer Service Complaint" contains : "QA Review Complaint" on "SR" link section
    Then I verify "Customer Service Complaint" contains : "Just Cause" on "TASK" link section
    When I click id of "QA Review Complaint" in Links section
    Then I verify "QA Review Complaint" contains : "Customer Service Complaint" on "TASK" link section
    When I click id of "Customer Service Complaint" in Links section
    And I click on edit service request button
    And I unlink "Just Cause" from link section
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "Customer Service Complaint" does not contains : "Just Cause" on link section
    And I click on id of "QA Review Complaint" in Links section of "SR" page
    Then I verify "QA Review Complaint" contains : "Customer Service Complaint" on "TASK" link section
    And Close the soft assertions
    Examples:
      | taskId | taskType|srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||         |Customer Service Complaint ||            | 5        ||            ||                || true          ||            ||          || today     ||

  @CP-27696 @CP-27696-06 @CP-22677 @CP-22677-06 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan
  Scenario Outline: Validate Manually Link & Unlink a Service Request to Service Request on create page for CoverVA
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Complaint SR" service request page
    And I will provide following information before creating task
      | taskInfo | Test CP-18749 |
      | priority | 5             |
    And I click on save button in create service request page
    When I navigate to "App SR V1 " service request page
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And I click on Link button in task search
    And I will update the following information in edit task page
      | taskInfo              | Test fsafasfa                  |
      | applicationType       | Pre-Release Application - CVIU |
      | externalApplicationId | random                         |
    And I click on save button on task edit page
    And I populate required fields to do task search "getFromUI","<taskType>","<srType>","<status>","<statusDate>","","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","","<contactReason>"
    And I click on search button on task search page
    And In search result click on task id to navigate to view page
    And I click on id of "App SR V1 " in Links section of "SR" page
    Then I verify "App SR V1" contains : "Process App V1" on "SR" link section
    Then I verify "App SR V1" contains : "Complaint SR" on "TASK" link section
    When I click id of "Process App V1" in Links section
    Then I verify "Process App V1" contains : "App SR V1" on "TASK" link section
    When I click id of "App SR V1" in Links section
    When I click id of "Complaint SR" in Links section
    Then I verify "Complaint SR" contains : "Review Complaint" on "SR" link section
    Then I verify "Complaint SR" contains : "App SR V1" on "TASK" link section
    When I click id of "Review Complaint" in Links section
    Then I verify "Review Complaint" contains : "Complaint SR" on "TASK" link section
    When I click id of "Complaint SR" in Links section
    And I click on edit service request button
    And I unlink "App SR V1 " from link section
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "Complaint SR" does not contains : "App SR V1" on link section
    And I click on id of "Review Complaint" in Links section of "SR" page
    Then I verify "Review Complaint" contains : "Complaint SR" on "TASK" link section
    And Close the soft assertions
    Examples:
      | taskId | taskType|srType        | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||         | Complaint SR ||            | 5        ||            ||                || true          ||            ||          || today     ||

  @CP-27696 @CP-27696-07 @CP-22677 @CP-22677-07 @crm-regression @ui-wf @ruslan
  Scenario Outline: Validate Manually Link & Unlink a Service Request to Service Request on create page for BLCRM
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "General Service Request" service request page
    And I will provide following information before creating task
      | taskInfo | First GSR |
      | priority | 5         |
    And I click on save button in create service request page
    When I navigate to "General Service Request" service request page
    And I click on contact link button under LINK section
    And I select "TASK/SERVICE REQUEST" in the create link section
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And I click on Link button in task search
    And I click on save button on task edit page
    And I populate required fields to do task search "getFromUI","<taskType>","<srType>","<status>","<statusDate>","","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","","<contactReason>"
    And I click on search button on task search page
    And In search result click on task id to navigate to view page
    And I click on id of "General Service Request" in Links section of "SR" page
    Then I verify "General Service Request" contains : "General Three" on "SR" link section
    Then I verify "General Service Request" contains : "General Service Request" on "TASK" link section
    When I click id of "General Three" in Links section
    Then I verify "General Three" contains : "General Service Request" on "TASK" link section
    And I click on id of "General Service Request" in Links section of "TASK" page
    And I click on id of "General Service Request" in Links section of "SR" page
    Then I verify "General Service Request" contains : "General Three" on "SR" link section
    Then I verify "General Service Request" contains : "General Service Request" on "TASK" link section
    When I click id of "General Three" in Links section
    Then I verify "General Three" contains : "General Service Request" on "TASK" link section
    And I click on id of "General Service Request" in Links section of "SR" page
    And I click on edit service request button
    And I unlink "General Service Request" from link section
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "General Service Request" does not contains : "General Service Request" on link section
    And I click on id of "General Three" in Links section of "SR" page
    Then I verify "General Three" contains : "General Service Request" on "TASK" link section
    And Close the soft assertions
    Examples:
      | taskId | taskType|srType                  | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||         |General Service Request ||            | 5        ||            ||                || true          ||            ||          || today     ||
