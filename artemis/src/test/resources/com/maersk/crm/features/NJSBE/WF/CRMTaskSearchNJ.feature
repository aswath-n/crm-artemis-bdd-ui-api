Feature: Validation of Task search Page

  @CP-14273 @CP-14273-02 @vidya @ui-wf-nj @nj-regression
  Scenario Outline: Verify tasks with Complete and Cancelled status does not has initiate button for NJ
    Given I logged into CRM with "Service Account 6" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
 #   And I verify save task search section is displayed
    And I click task id to get the results in descending order in Task Search
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I click on initiate button in task search page
    Then Verify task slider is displayed
    And I verify Active contact screen displayed with initiate call
    Then I will grab task id from active contact page after initiating task
    And I click on cancel button on task slider
 #   Then I verify warning is displayed upon clicking Cancel button on Task slider
    And I click on continue on task details warning window
    Then I verify error message displayed and user remains on active contact screen and task slider closed
    When I navigate to "Task Search" page
    And I will search with taskId
    Then In result I verify task is saved with status "Open"
    And Close the soft assertions
    Examples:
      | taskId | taskType      | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee           | createdBy | createdOn |contactReason|
      || Outbound Call || Open   ||          ||            ||                || true          ||            || Service AccountSix ||           ||

  @CP-18447 @CP-18447-02 @CP-828 @CP-828-02 @vidya @ui-wf-nj @nj-regression
  Scenario Outline: Verify Task Search Criteria Stays Cleared After Selecting Cancel Button
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click on cancel button on task search page
    And I navigate to "Saved Task Search" page
    And I navigate to "Task Search" page
    Then I verify task search parameter are cleared
    And Close the soft assertions
    Examples:
      | taskId | taskType                     | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee           | createdBy          | createdOn |contactReason|
      | 4509   | Verification Document Upload || Open   | today      | 2        | +2      | GetInsured | SBE008 | GetInsured     | 11990      | true          | Juila      | Lee        | User   | Service AccountOne | Service AccountOne | today     ||

    #Functionality changed because of CP-23744
  @CP-3996 @CP-3996-03 @CP-23744 @ui-wf-nj @nj-regression @elvin
  Scenario Outline: Verify retaining search criteria and results on task search page in NJ-SBE s1
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And In search result click on task id to navigate to view page
    Then I navigate to "Work Queue" from task view page
    Then I navigate to "Task Search" from task view page
    Then I verify task search parameter are cleared
    And Close the soft assertions
    #Then I will verify task search page retains search criteria "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>"
    Examples:
      | taskId | taskType      | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || Returned Mail || Open   || 3        || GetInsured || GetInsured     || true          ||            | User   ||           ||             |

    #Functionality changed because of CP-23744
  @CP-3996 @CP-3996-04 @CP-23744 @CP-31978 @CP-31978-02 @priyal @ui-wf-nj @nj-regression @elvin
  Scenario Outline: Verify retaining search criteria and results on task search page and verify Task Management DD in left side menu in NJ-SBE s2
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
 #   Then I verify Task Management panel and it's DD value
 #     | MY TASK / WORK QUEUE|
 #     | TASK/SR SEARCH|
 #     | MANAGE QUEUE FILTER|
 #     | SAVED TASK SEARCH|
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    Then I navigate to "My Task" from task view page
    Then I navigate to "Task Search" from task view page
    Then I verify task search parameter are cleared
    And Close the soft assertions
    #Then I will verify task search page retains search criteria "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>"
    Examples:
      | taskId | taskType      | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || Returned Mail || Open   || 3        || GetInsured || GetInsured     || true          ||            | User   ||           ||             |

  @CP-22822 @CP-22822-01 @ui-wf-nj @nj-regression @ruslan
  Scenario Outline: Display Consumer Name in Task Search Results
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    Then I validate column "CONSUMER NAME" is displayed on the task search result
    And I validate consumer name "<consumerName>" on the search result
    And Close the soft assertions
    Examples:
      | taskId | taskType          | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | consumerName |contactReason|
      || Business Day Task || Open   || 3        || GetInsured || GetInsured     || true          | Ruby       | Lewis      | User   ||           || Ruby Lewis   ||
      || Business Day Task ||        || 5        ||            ||                ||               ||            ||          ||           | -- --        ||

  @CP-31980 @CP-31980-06 @priyal @ui-wf-nj @nj-regression
    #CP-21344 is applicable for BLCRM and CoverVA tenants, so it is invalid for NJ-SBE tenant
  Scenario Outline: Verify orange icon for SRs
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify "orange" icon displays on "SR"
#    And I verify due in days after close status for SR
#    And I click task id to get the results in descending order in Task Search
#    And In search result click on task id to navigate to view page
#    And I click on edit service request button
#    And I verify due in days after close status for SR
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||          | Appeal | Closed ||          ||            ||                ||               ||            | User   ||           ||             |

  @CP-14664 @CP-14664-01 @ui-wf-nj @nj-regression @ozgen
  Scenario: Display Get Insured Case and ConsumerId in Task Search Results from Active Contact
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I click on initiate contact record
    When I searched customer have First Name as "Blake" and Last Name as "Dugan"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    When I navigate to "Process Inbound Document" task page
    And I will provide following information before creating task
      | taskInfo | Task Info CP-14664-01 |
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    And I am able to navigate back to My Task or Work Queue page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I click on search button on task search page
    Then I validate column "GetInsured CASE ID" is displayed on the task search result
    Then I validate column "GetInsured CONSUMER ID" is displayed on the task search result
    And I verify GetInsured case and consumer ids in "Task Search"
    And Close the soft assertions

  @CP-14664 @CP-14664-01.2 @ui-wf-nj @nj-regression @ozgen
  Scenario: Display Get Insured Case and ConsumerId in Task Search Results from Create Task
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Process Inbound Document" task page
    And I will provide following information before creating task
      | taskInfo | Task Info CP-14664-01.2 |
    When I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "Blake" and Last Name as "Dugan"
    And I click on primary individual record in search result
    Then I click validate and link Consumer & Case
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    And I am able to navigate back to My Task or Work Queue page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I click on search button on task search page
    Then I validate column "GetInsured CASE ID" is displayed on the task search result
    Then I validate column "GetInsured CONSUMER ID" is displayed on the task search result
    And I verify GetInsured case and consumer ids in "Task Search"
    And Close the soft assertions

  @CP-23400 @CP-23400-01 @CP-31980 @CP-31980-02 @crm-regression @ui-wf-nj @nj-regression @ruslan
  Scenario Outline: Verify re-factor Due Date for Task Search in UI request
    Given I logged into CRM with "<userName>" and select a project "<projectName>"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    When I select "<sign>" sign for due date in task search
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I verify "blue" icon displays on "TASK"
    Then Validate due date column contains "<dueDate>" values "<sign>"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | sign | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | userName          | projectName |contactReason|
      ||          || Created ||          | >    | today   ||        ||            ||            ||        ||           || Service Account 1 | NJ-SBE      ||

  @CP-13652 @CP-13652-01 @CP-13652-02 @ui-wf-nj @nj-regression @kamil
  Scenario Outline: Verify NJ - Manual Task Search - Search by GI Case ID & Consumer ID
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    Then Verify search results displayed GetInsuredCASEID "<caseId>"
    And In search result click on task id to navigate to view page
    Then Verify tasks linked to Case ID "<caseId>"
    When Wait for 2 seconds
    When I navigate to "Task Search" page
    And I click on cancel button on task search page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    Then Verify search results displayed GetInsuredConsumerID "<consumerId>"
    And In search result click on task id to navigate to view page
    Then Verify tasks linked to Consumer ID "<consumerId>"
    When Wait for 2 seconds
    When I navigate to "Task Search" page
    And I click on cancel button on task search page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    Then Verify search results displayed GetInsuredConsumerID "<consumerId>"
    And In search result click on task id to navigate to view page
    Then Verify tasks linked to Consumer ID "<consumerId>"
    Then Verify tasks linked to Case ID "<caseId>"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId       | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||          ||        ||          ||            | SBE100002045 || 1000008352 ||            ||        ||           ||             |

  @CP-29290 @CP-29290-01 @CP-13265 @CP-13265-04 @crm-regression @ui-wf-nj @nj-regression @vidya
  Scenario: Verify CaseID and ConsumerID in task search result which we link with SR in NJ-SBE Tenant
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
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
    And Wait for 3 seconds
    And I click on contact link button under LINK section
    And I click on Case and Consumer button
    Then I see Case& Consumer Search section display
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I will expand the consumer record
    And I select Consumer radio button
    And I click on validate link button
    When I click on Link Record Consumer button
    And I verify case consumer link button is not present in create link list
    And I click on save button on task edit page
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    And I will click on back arrow on view task page
    And I verify save task search section is displayed
    Then I verify following information in task search result
      | taskType    | Appeal        |
      | caseID1     | NJ100002908   |
      | consumerID1 | 1000005508    |
    And Close the soft assertions

  @CP-29078 @CP-29078-02 @crm-regression @ui-wf-nj @nj-regression @vidya
  Scenario: Verify only task which are assignee is returned in task search result
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | assignmentStatus | Assigned |
    And I click on search button on task search page
    And I click on continue button on warning message
    And I verify save task search section is displayed
    Then I verify task search result not returned any SR and has task with assignee filed "Not -- --"
    And Close the soft assertions

  @CP-24051 @CP-24051-01 @vidya @crm-regression @ui-wf-nj @nj-regression
  Scenario Outline: Verify escalated check box checked in task search result
    Given I logged into CRM with "<userName>" and select a project "<projectName>"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    When I select escalate Flag checkbox
    And I click on search button on task search page
    And I click on continue button on warning message
    And I verify save task search section is displayed
    Then I verify task search result has "escalated" checkbox "checked"
    And Close the soft assertions
    Examples:
      | userName          | projectName |
      | Service Account 1 | NJ-SBE      |

  @CP-24051 @CP-24051-02 @CP-31979 @CP-31979-02 @crm-regression @ui-wf-nj @nj-regressionA @vidya @priyal
  Scenario Outline: Verify escalated check box unchecked in task search result and verify Task search page ui
    Given I logged into CRM with "<userName>" and select a project "<projectName>"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | taskTypes | Outbound Call,Returned Mail,Review Complaint |
      | srTypes   | Appeal                                       |
    And I click on search button on task search page
    And I click on continue button on warning message
    Then I Verify "Task Type" multi select DD value in search page "Outbound Call, Returned Mail, Review Complaint"
    And I Verify "SR Type" multi select DD value in search page "Appeal"
    And I click on cancel button on task search page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify task search result has "escalated" checkbox "unchecked"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee           | createdBy          | createdOn | userName          | projectName |contactReason|
      ||          || Created || 3        ||            ||                || true          ||            || Service AccountOne | Service AccountOne || Service Account 1 | NJ-SBE      ||

  @CP-25850 @CP-25850-01 @CP-27055 @CP-27055-01 @crm-regression @ui-wf-nj @nj-regressionA @ruslan @vidya
  Scenario Outline: verify warning message displayed for fetching 500 records
    Given I logged into CRM with "<userName>" and select a project "<projectName>"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click search button on task search page
    Then I validate the system displays an Warning Message: "Search results in excess. The first 500 are returned below. Enter additional search criteria to limit search results"
    Then I verify view pagination "<recordPerPage>" in "Task Search" page
    Then Verify "<numberOfPage>" pages listed in the task search result
    And I click on cancel button on task search page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","Created","today","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click search button on task search page
    Then I validate the system does not displays an Warning Message: "Search results in excess. The first 500 are returned below. Enter additional search criteria to limit search results"
    And Close the soft assertions
    Examples:
      | userName          | projectName | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | recordPerPage | numberOfPage |contactReason|
      | Service Account 1 | NJ-SBE      ||          ||        ||          ||            ||                || true          ||            | User   ||           || show 5        | 100          ||
