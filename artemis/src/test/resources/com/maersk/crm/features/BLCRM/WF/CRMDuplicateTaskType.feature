Feature: Validation of duplicate task or sr

  @CP-38149 @CP-38149-01 @crm-regression @ui-wf @ruslan
  Scenario: Verify duplicate error message for consumer linked to sr
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I navigate to case and consumer search page
    When I searched customer have First Name as "Alexys" and Last Name as "Bernhard"
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "General/CRM/EB" task page
    And I will provide following information before creating task
      | contactReason  | Information Request |
      | preferredPhone | 1234567890          |
    And I click on save button in create task page
    Then I verify error message contains "A task of the same type with ID 21991 already exists, linked to CONSUMER 8185, you may not create a duplicate."

  @CP-38149 @CP-38149-02 @crm-regression @ui-wf @ruslan
  Scenario: Verify duplicate error message for case linked to sr
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I navigate to case and consumer search page
    When I searched customer have First Name as "Nancy" and Last Name as "Barrow"
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "General Three" task page
    And I will provide following information before creating task
      | priority | 1 |
    And I click on save button in create task page
    Then I verify error message contains "A task of the same type with ID 9026 already exists, linked to CASE 395, you may not create a duplicate."

  @CP-38149 @CP-38149-03 @crm-regression @ui-wf @ruslan
  Scenario: Verify duplicate error message for case or consumer linked to sr
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I navigate to case and consumer search page
    When I searched customer have First Name as "AlexanderWbBEq" and Last Name as "CormierDbTkJ"
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Inquiry/complaint" task page
    And I will provide following information before creating task
      | priority            | 1             |
    And I click on save button in create task page
    Then I verify error message contains "A task of the same type with ID 22222 already exists, linked to CONSUMER 8185, CASE 395, you may not create a duplicate."

  @CP-38149 @CP-38149-04 @crm-regression @ui-wf @ruslan
  Scenario: Verify duplicate error message  displayed if user linking case & consumer on create page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General/CRM/EB" task page
    And I will provide following information before creating task
      | contactReason  | Information Request |
      | preferredPhone | 1234567890          |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Alexander" and Last Name as "Carroll"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    When I select Consumer radio button
    When I click on Link Record Consumer button
    And I click on save button in create task page
    Then I verify error message contains "A task of the same type with ID 21991 already exists, linked to CONSUMER 19775, you may not create a duplicate."

  @CP-38149 @CP-38149-05 @crm-regression @ui-wf @ruslan
  Scenario: Verify duplicate error message  displayed if user linking case on create page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General Three" task page
    And I will provide following information before creating task
      | priority | 1 |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Nancy" and Last Name as "Barrow"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    When I select 'Link to Case Only' checkbox
    When I click the Link Case button
    And I click on save button in create task page
    Then I verify error message contains "A task of the same type with ID 9026 already exists, linked to CASE 395, you may not create a duplicate."
    And Close the soft assertions

  @CP-38149 @CP-38149-06 @crm-regression @ui-wf @ruslan
  Scenario: Verify duplicate error message displayed if user linking case & consumer on create page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Inquiry/complaint" task page
    And I will provide following information before creating task
      | priority            | 1             |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I scroll down the page
    When I searched customer have First Name as "AlexanderWbBEq" and Last Name as "CormierDbTkJ"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    When I select Consumer radio button
    When I click on Link Record Consumer button
    And I click on save button in create task page
    Then I verify error message contains "A task of the same type with ID 22785 already exists, linked to CONSUMER 19775, CASE 12189, you may not create a duplicate."
    And Close the soft assertions

  @CP-38149 @CP-38149-07 @crm-regression @ui-wf @ruslan
  Scenario Outline: Verify duplicate error message  displayed if user linking consumer on edit page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    Then I select "Unlink Case/Consumer" in the create link section
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "AlexandriaypYtO" and Last Name as "FarrellTBOBW"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I select the record whose Case ID Blank
    And I scroll down the page
    And I will expand the consumer record
    When I click on Link Record Consumer button
    And I scroll up the page
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify error message contains "A task of the same type with ID 21991 already exists, linked to CONSUMER 19775, you may not create a duplicate."
    And Close the soft assertions
    Examples:
      | taskId | taskType       | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      |        | General/CRM/EB |        | Created |            |          |         |            |        |                | 175069     |               |            |            |        |          |           |           |             |

  @CP-38149 @CP-38149-08 @crm-regression @ui-wf @ruslan
  Scenario Outline: Verify duplicate error message  displayed if user linking consumer on edit page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General Three" task page
    And I will provide following information before creating task
      | priority | 4 |
    And I click on save button in create task page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Nancy" and Last Name as "Barrow"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    When I select 'Link to Case Only' checkbox
    When I click the Link Case button
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify error message contains "A task of the same type with ID 9026 already exists, linked to CASE 395, you may not create a duplicate."
    And Close the soft assertions
    Examples:
      | taskId | taskType      | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      || General Three || Created || 4        ||            ||                ||               ||            ||          || today     ||