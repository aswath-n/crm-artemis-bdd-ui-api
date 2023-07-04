Feature: Validation of HCC Outbound Call Service Request

  @CP-25116 @CP-25116-01 @CP-17599 @CP-17599-05 @crm-regression @ui-wf-ineb @ozgen @vidya
  Scenario: IN-EB: Create HCC Outbound Call Service Request
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HCC Outbound Call SR" service request page
    And I will provide following information before creating task
      | taskInfo       | Test CP-25116 |
      | preferredPhone | 5468902314    |
    And I click on save button in create service request page
  #  Then I verify Success message is displayed for SR
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I initiated bpm process get api by service request id for process name "INEB_HCC_Outbound_Call"
    And I run bpm process get api by sr id
    And I will check response of service request bpm process
    And Close the soft assertions

  @CP-25119 @CP-25119-01 @CP-25642 @CP-25642-01 @CP-18423 @CP-18423-05 @crm-regression @ui-wf-ineb @ozgen @vidya
  Scenario: IN-EB: Create HCC Outbound Call Task after Service Request Creation
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HCC Outbound Call SR" service request page
    And I will provide following information before creating task
      | taskInfo       | Test CP-25119 |
      | preferredPhone | 5468902314    |
      | priority       | 5             |
    And I click on save button in create service request page
  #  Then I verify Success message is displayed for SR
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    Then I verify "HCC Outbound Call" task fields are displayed
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And Close the soft assertions

  @CP-25121 @CP-25121-01 @crm-regression @ui-wf-ineb @ozgen
  Scenario: IN-EB: Linking to only consumer to SR (from manual Case/Consumer Search)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    When I created a case consumer to link contact record
    And I send API CALL for Create CaseConsumer
    And I initiated Link API
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    And I searched consumer created by api script
    Then I expand the first record of the search result
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "HCC Outbound Call SR" service request page
    And I will provide following information before creating task
      | taskInfo       | Test CP-25121-01 |
      | preferredPhone | 8762349011       |
    And I click on save button in create service request page
  #  Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "HCC Outbound Call SR" link section has all the business object linked : "Consumer,Case,Task"
    And Close the soft assertions

  @CP-25121 @CP-25121-02 @crm-regression @ui-wf-ineb @ozgen
  Scenario: IN-EB: Linking case/consumer to SR (from manual Case/Consumer Search)
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Mike" and Last Name as "Dibbert"
    Then I expand the first record of the search result
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "HCC Outbound Call SR" service request page
    And I will provide following information before creating task
      | taskInfo       | Test CP-25121-02 |
      | preferredPhone | 5468902314       |
    And I click on save button in create service request page
 #   Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "HCC Outbound Call SR" link section has all the business object linked : "Consumer,Case,Task"
    And Close the soft assertions

  @CP-25121 @CP-25121-03 @crm-regression @ui-wf-ineb @ozgen
  Scenario: IN-EB: Linking to only consumer to SR (from Create Task Page)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    When I created a case consumer to link contact record
    And I send API CALL for Create CaseConsumer
    And I initiated Link API
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HCC Outbound Call SR" service request page
    And I will provide following information before creating task
      | taskInfo       | Test CP-25121-03 |
      | preferredPhone | 8762349011       |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    And I searched consumer created by api script
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    When I select Consumer radio button
    When I click on Link Record Consumer button
    And I click on save button in create service request page
 #   Then I verify Success message is displayed for SR
    When I click case consumer search tab
    And I searched consumer created by api script
    Then I expand the first record of the search result
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to view SR details page by clicking on sr id
    When I click id of "HCC Outbound Call" in Links section
    Then I verify "HCC Outbound Call" link section has all the business object linked : "Consumer,Case,Service Request"
    And Close the soft assertions

  @CP-25121 @CP-25121-04 @crm-regression @ui-wf-ineb @ozgen
  Scenario: IN-EB: Linking to only case and consumer to SR (from Create Task Page)
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HCC Outbound Call SR" service request page
    And I will provide following information before creating task
      | taskInfo       | Test CP-25121-04 |
      | preferredPhone | 8762349011       |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I searched customer have First Name as "Jason" and Last Name as "Koch"
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    And I select Consumer radio button
    When I click on Link Record Consumer button
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    When I click case consumer search tab
    When I searched customer have First Name as "Jason" and Last Name as "Koch"
    Then I expand the first record of the search result
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    When I click id of "HCC Outbound Call" in Links section
    Then I verify "HCC Outbound Call" link section has all the business object linked : "Consumer,Case,Service Request"
    And Close the soft assertions

  @CP-25120 @CP-25122 @CP-26114 @CP-25120-01 @CP-25122-01 @CP-26114-01 @CP-30456 @CP-30456-03 @crm-regression @ui-wf-ineb @ozgen @vidya
  Scenario: Validate HCC Outbound Call SR link objects, Creation of Following HCC Outbound Call Task for Needs Plan Selection disposition, Updating SR as Closed
    Given I will get the Authentication token for "IN-EB" in "CRM"
    When I created a case consumer to link contact record
    And I send API CALL for Create CaseConsumer
    And I initiated Link API
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    And I searched consumer created by api script
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "HCC Outbound Call SR" service request page
    And I will provide following information before creating task
      | taskInfo       | Test CP-25120-01 |
      | preferredPhone | 8762349011       |
      | contactName    | Salvador         |
    And I store external case id from Create Task Links Component
    And I store external consumer id from Create Task Links Component
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "HCC Outbound Call SR" link section has all the business object linked : "Task,Consumer,Case"
    When I click id of "HCC Outbound Call" in Links section
    Then I verify "HCC Outbound Call" link section has all the business object linked : "Consumer,Case,Service Request"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | No Action Taken      |
      | disposition   | Needs Plan Selection |
    And I click on save button on task edit page
    When I click id of "HCC Outbound Call SR" in Links section
    Then I verify "HCC Outbound Call SR" link section has all the business object linked : "Consumer,Case,Task,Task"
    And I click on edit service request button
    And I will update the following information in edit task page
      | status        | Closed               |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Invalid              |
    And I click on save button on task edit page
    And Wait for 5 seconds
    And I verify all linked tasks are updated to Cancelled
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I initiated bpm process get api by service request id for process name "INEB_HCC_Outbound_Call"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-25124 @CP-25124-01 @crm-regression @ui-wf-ineb @ozgen
  Scenario Outline: Close HCC Outbound Call SR After Task Completion for Plan Selection Made disposition
    Given I will get the Authentication token for "IN-EB" in "CRM"
    When I created a case consumer to link contact record
    And I send API CALL for Create CaseConsumer
    And I initiated Link API
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    And I searched consumer created by api script
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "HCC Outbound Call SR" service request page
    And I will provide following information before creating task
      | taskInfo | Test CP-25124-01 |
    And I store external case id from Create Task Links Component
    And I store external consumer id from Create Task Links Component
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    When I click id of "HCC Outbound Call" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | No Action Taken      |
      | disposition   | <disposition>        |
    And I click on save button on task edit page
    When I click id of "HCC Outbound Call SR" in Links section
    And I verify the sr status is updated to close and disposition is set to "<disposition>"
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I initiated bpm process get api by service request id for process name "INEB_HCC_Outbound_Call"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | disposition         |
      | Plan Selection Made |

  @CP-25124 @CP-25124-02 @crm-regression @ui-wf-ineb @ozgen
  Scenario Outline: Close HCC Outbound Call SR After Task Completion for Invalid diposition
    Given I will get the Authentication token for "IN-EB" in "CRM"
    When I created a case consumer to link contact record
    And I send API CALL for Create CaseConsumer
    And I initiated Link API
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    And I searched consumer created by api script
    When I expand the record to navigate Case Consumer Record from manual view
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "HCC Outbound Call SR" service request page
    And I will provide following information before creating task
      | taskInfo | Test CP-25124-02 |
    And I store external case id from Create Task Links Component
    And I store external consumer id from Create Task Links Component
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    When I click id of "HCC Outbound Call" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | No Action Taken      |
      | disposition   | <disposition>        |
      | invalidReason | Not an HCC Member    |
    And I click on save button on task edit page
    When I click id of "HCC Outbound Call SR" in Links section
    And I verify the sr status is updated to close and disposition is set to "<disposition>"
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I initiated bpm process get api by service request id for process name "INEB_HCC_Outbound_Call"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | disposition |
      | Invalid     |

  @CP-30416 @CP-30416-01 @CP-30456 @CP-30456-02 @crm-regression @INEB-UI-Regression @ui-wf-ineb @vidya
  Scenario Outline: Validate disposition value for HCC Outbound Call SR
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in ascending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will update the following information in edit task page
      | status | <status1> |
    And I verify "disposition" single select drop down value
      | Invalid              |
      | Needs Plan Selection |
      | Plan Selection Made  |
    Then I verify text box Date and Time field value and error message for following fields
      | CONTACT NAME    |
      | PREFERRED PHONE |
    And Close the soft assertions
    Examples:
      | status1 | taskId | taskType | srType               | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      | Closed  ||          | HCC Outbound Call SR | Open   ||          ||            ||                ||               ||            ||          ||           ||
      ||        || HCC Outbound Call SR | Closed ||          ||            ||                ||               ||            ||          ||           ||
      | Closed  ||          | HCC Outbound Call SR | Open   | today      ||         ||        ||            ||            ||        ||           ||             |

  @CP-30419 @CP-30419-02 @CP-33069 @CP-33069-01 @crm-regression @INEB-UI-Regression @ui-wf-ineb @vidya @ruslan
  Scenario Outline: Validate disposition value for HCC Outbound Call task
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I verify application id in task search section is not displayed
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I verify "disposition" single select drop down value
      | Invalid              |
      | Needs Plan Selection |
      | Plan Selection Made  |
    And Close the soft assertions
    Examples:
      | status1 | taskId | taskType          | srType | status   | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||        | HCC Outbound Call || Complete ||          ||            ||                || true          ||            ||          ||           ||