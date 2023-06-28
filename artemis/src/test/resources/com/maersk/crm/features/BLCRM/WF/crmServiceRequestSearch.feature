Feature: Validation of SR/Task search Page

  @CP-5775 @CP-5775-03 @crm-regression @ui-wf @Basha
  Scenario Outline: verify task is NOT highlighted in red for Cancelled and Complete status in Task Search Results
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify task is not highlighted in red for task status "<status>" in Task Search screen
    And Close the soft assertions
    Examples:
      | taskId | taskType      | srType | status    | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      || Dropdowns One || Complete  ||          ||            ||                || true         ||            ||           | Service AccountOne||             |
      || Address       || Cancelled ||          ||            ||                || true         ||            ||           | Service AccountOne||             |

  @CP-18447 @CP-18447-01 @vidya @crm-regression @ui-wf
  Scenario Outline: Verify Cancel Button Clears Task Search Criteria
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click on cancel button on task search page
    Then I verify task search parameter are cleared
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee           | createdBy          | createdOn |contactReason|
      | 16501  | General  || Created | today      | 2        | +5      | Medicaid   | 8867   | CHIP           | 11990      | true          | Emma       | Test       | User   | Service AccountOne | Service AccountOne | today     ||

  @CP-3996 @CP-3996-01 @crm-regression @ui-wf @elvin
  Scenario Outline: Verify retaining search criteria and results on task search page in BLCRM s1
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I will click on back arrow on view task page
    Then I will verify task search page retains search criteria "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                  | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy          | createdOn |contactReason|
      |        |          | General Service Request | Open   |            | 3        |         | Medicaid   |        | Internal       |            | true          |            |            | User   |          | Service AccountOne |           |             |

  @CP-3996 @CP-3996-02 @crm-regression @ui-wf @elvin
  Scenario Outline: Verify retaining search criteria and results on task search page in BLCRM s2
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will click on back arrow on view task page
    And I click on continue button on warning message
    Then I will verify task search page retains search criteria "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                  | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||          | General Service Request | Open   || 3        || Medicaid   || Internal       || true          ||            | User   ||           ||             |

  @CP-21761 @CP-21761-01 @CP-21761-02 @crm-regression @ui-wf @kamil
  Scenario: Verify Date of Contact on Task Search tab
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true       |
      | dateOfContact | 08/29/2022 |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    Then Validate Date of Contact displayed on view task
      | 08/29/2022 |
    And Close the soft assertions

  @CP-28286 @CP-28286-02 @crm-regression @ui-wf @ozgen
  Scenario Outline: Verification of Specific Page of Task Search Results for Invalid Number in BLCRM Project
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify Go to page section is displayed
    And I provide number "3000" on the go to page section
    Then I verify that error message for invalid page number is getting displayed
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      |        | General  |        |        |            |          |         |            |        |                |            |  true         |            |            |        |          |           | today     |             |

  @CP-23339 @CP-23339-01 @vidya @crm-regression @ui-wf
  Scenario Outline: Verify Expedited check box checked in task search result
    Given I logged into CRM with "<userName>" and select a project "<projectName>"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true |
      | expedited     | true |
      | status        | Complete|
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify task search result has "expedited" checkbox "checked"
    And Close the soft assertions
    Examples:
      | userName          | projectName |
      | Service Tester 2  | CoverVA     |
      | Service Account 1 | BLCRM       |

  @CP-23339 @CP-23339-04 @crm-regression @ui-wf @vidya
  Scenario Outline: Verify expedited check box unchecked in task search result
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify task search result has "expedited" checkbox "unchecked"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee           | createdBy | createdOn |contactReason|
      || General  || Created ||          ||            ||                || true          ||            || Service AccountOne ||    today       ||

  @CP-29290 @CP-29290-03 @crm-regression @ui-wf @vidya
  Scenario: Verify CaseID and ConsumerID in task search result which we link with SR in BLCRM Tenant
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send API CALL for Create CaseConsumer
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General Service Request" service request page
    And I will provide following information before creating task
      | taskInfo | Test CP-14954-01 |
    And I click on contact link button under LINK section
    And I click on Case and Consumer button
    Then I see Case& Consumer Search section display
    And I searched consumer created by api script
    When I expand the first record of the search result
    And I select 'Link to Case Only' checkbox
    Then I see Link Record Case button get displayed
    When I click on Link Case button
    Then I verify task is linked with Case ID
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | caseId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify following information in task search result
      | taskType   | General Service Request |
      | caseID     | getFromCreatePage       |
      | consumerID | -- --                   |
    And I click on cancel button on task search page
    And I will search with srId
    Then I verify following information in task search result
      | taskType   | General Service Request |
      | caseID     | getFromCreatePage       |
      | consumerID | -- --                   |
    And Close the soft assertions

  #@CP-29078 @CP-29078-04 @crm-regression @ui-wf @vidya this scenario fails due to old data in db
  Scenario: Verify only general task with assignee is returned in task search result
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | assignmentStatus | Assigned                        |
      | taskTypes        | General |
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify task search result not returned any SR and has task with assignee filed "Not -- --"
    And Close the soft assertions

    #Verify warning is Display when User attempts to return to Active Contact and has unsaved data entry
  @CP-689 @CP-689-06 @vidya @crm-regression @ui-wf
  Scenario Outline: Verify warning is Display
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I enter value to search name field
    And I click on Active Contact widget
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Task Search |
    And I click on Active Contact widget
    And I click on continue button on warning message
    Then Verify should I return back to Active Contact screen
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId |contactReason|
      || General  ||        || 4        ||            ||                ||             |
