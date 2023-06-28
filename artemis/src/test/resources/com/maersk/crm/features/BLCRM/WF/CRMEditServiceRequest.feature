@EditServiceRequest
Feature: Edit Service Request Functionality

  @CP-2389 @CP-2389-03 @crm-regression @ui-wf @ui-sr @elvin
  Scenario: Validate Back Arrow functionality while editing SR
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Nancy" and Last Name as "FOREST"
    When I click the first consumer id from the search results
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I click on first SR ID
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
      | priority      | 5                    |
    And I click on the back arrow button
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | SR Details Edit |
    And I click on the back arrow button
    And I click on continue button on warning message
    Then I Verify user is navigate back to Task and service Request Page
    And Close the soft assertions

  @CP-2389 @CP-2389-04 @CP-21187 @CP-21187-01 @crm-regression @ui-wf @ui-sr @elvin @vidya
  Scenario: Validate Cancel functionality while editing SR
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Nancy" and Last Name as "FOREST"
    When I click the first consumer id from the search results
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I click on first SR ID
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
      | priority      | 5                    |
    And I click on cancel button on create task type screen
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | SR Details Edit |
    And I click on cancel button on create task type screen
    Then I verify warning popup is displayed with message
    And I click on continue button on warning message
    And I verify SR id and edit SR button are displayed
    And Close the soft assertions

  @CP-2389 @CP-2389-05 @crm-regression @ui-wf @ui-sr @elvin
  Scenario: Validate Navigate Away functionality while editing SR
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "Nancy" and Last Name as "FOREST"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I click on first SR ID
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
      | priority      | 5                    |
    When I click contact search tab for Contact Record
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | SR Details Edit |
    When I click contact search tab for Contact Record
    Then I verify warning popup is displayed with message
    And I click on continue button on warning message
    Then I navigate to manual contact record search page
    And Close the soft assertions

  @CP-22014 @CP-22014-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario Outline: Validate CaseId filed is present in for closed sr on edit page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I verify status is not editable for closed sr on edit page
    And I will update the following information in edit task page
      | priority                | 2 |
      | taskInfo                | Test 22014|
      | reasonForEdit           | Corrected Data Entry|
      | documentDueDate         | 07/22/2024|
      | disposition             | Resolved|
    And I click on save button on task edit page
#    Then I verify success message for update service request
    And I verify the updated information in view sr details page
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType    |status |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      |156305 ||Appeals SR|Closed ||        ||          ||              ||false        ||          ||        ||         ||
