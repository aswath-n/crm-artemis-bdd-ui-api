Feature: Validation of Saved Task search Functionality

  @CP-830 @CP-830-01 @vidya @crm-regression @ui-wf @tasksearch
  Scenario Outline:Validate Save Search Parameters
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I create the save task search
    When I navigate to "Saved Task Search" page
  #  And I navigate to "Task/SR Management" and then to "Saved Task Search" in the left menu
    Then I verify newly created save task search is listed on the saved task search page
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType|status   |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      || General||         || 4      ||          ||              ||false        ||          ||        ||         ||

  @CP-830 @CP-830-03 @CP-5046 @CP-5046-02 @vidya @crm-regression @ui-wf @tasksearch
  Scenario Outline:Validate Save Search Parameters
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I create the save task search
  #  And I navigate to "Task/SR Management" and then to "Saved Task Search" in the left menu
    When I navigate to "Saved Task Search" page
    And I select to expand a Saved Task Search Record
    Then I will be able to view the Selected Parameters for the Saved Search "<taskId>","<taskType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>"
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType|status   |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee          |createdBy         |createdOn|contactReason|
      || General||         || 4      ||          ||              ||false        ||          ||                  ||         ||
      |12345  |General ||Created  |today     | 3      |today  ||8089  ||9125      |true         |Alli      |Pasha     |User  |Service AccountOne|Service AccountOne|today    ||

  @CP-830 @CP-830-04 @vidya @crm-regression @ui-wf @tasksearch
  Scenario:Validate Save Search Parameters for SA2
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to "Saved Task Search" page
  #  And I navigate to "Task/SR Management" and then to "Saved Task Search" in the left menu
    Then I verify No Records Available message is displayed
    And Close the soft assertions

    #Below scripts is for Delete a Saved Task Search verification

  @CP-832 @CP-832-01 @vidya @crm-regression @ui-wf @tasksearch
  Scenario Outline:Validate Delete Saved Search
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I create the save task search
  #  And I navigate to "Task/SR Management" and then to "Saved Task Search" in the left menu
    When I navigate to "Saved Task Search" page
    Then I verify will have the option to delete the Saved Task Search
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType|status   |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      || General||         || 4      ||          ||              ||false        ||          ||        ||         ||

  @CP-832 @CP-832-02 @vidya @crm-regression @ui-wf @tasksearch
  Scenario:Validate Warning Message
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
  #  And I navigate to "Task/SR Management" and then to "Saved Task Search" in the left menu
    When I navigate to "Saved Task Search" page
    And I click on delete saved Task search button
    Then I verify warning message is displayed in saved task search page
    And I click on cancel button on warning message
    Then I verify saved task search is remains on the screen
    And Close the soft assertions

  @CP-832 @CP-832-04 @CP-5046 @CP-5046-03 @vidya @crm-regression @crm-smoke @ui-wf @tasksearch
  Scenario Outline:Verify we are able to delete the saved task search and again we can able to save the task search with same name
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
  #  And I navigate to "Task/SR Management" and then to "Saved Task Search" in the left menu
    When I navigate to "Saved Task Search" page
    And I click on delete saved Task search button
    And I click on continue button on warning message
    Then I verify newly created save task search is deleted from saved task search page
 #   And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    #this step will save the task search with name we just deleted above
    And I enter already created saved task search name
    Then I verify success message and text are displayed task search page
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType|status   |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      || General||         || 4      ||          ||              ||false        ||          ||        ||         ||

  @CP-5046 @CP-5046-01 @vidya @crm-regression @crm-smoke @ui-wf @tasksearch
  Scenario Outline:verify user is not able to save the task search with already exit saved task search name
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
  #  And I navigate to "Task/SR Management" and then to "Saved Task Search" in the left menu
    When I navigate to "Saved Task Search" page
    And I get the already created saved task search name
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I enter already created saved task search name
    Then Verify the system displays the following "Error Message: A Saved Task Search already exists for this user"
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType|status   |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      || General||         || 4      ||          ||              ||false        ||          ||        ||         ||

  @CP-5768 @CP-5768-01 @crm-regression @ui-wf @vidya @tasksearch
  Scenario: Verification of Paginate Saved Task Search List
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
  #  And I navigate to "Task/SR Management" and then to "Saved Task Search" in the left menu
    When I navigate to "Saved Task Search" page
    Then I will verify Paginate for "show 5" records in "Save Task Search" Page
    And I will verify Paginate for "show 10" records in "Save Task Search" Page
    And I will verify Paginate for "show 20" records in "Save Task Search" Page
    And Close the soft assertions

  @CP-34837 @CP-34837-01 @ozgen @crm-regression @ui-wf @tasksearch
  Scenario Outline: Update Save Search Parameters by clicking Yes
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I create the save task search
    And I click on cancel button on task search page
    And I will select save task search value from Search by Saved Task Search drop down
    When I update the Search parameter "<updateType>","<updateStatus>","<updatePriority>"
    Then I verify I see warning message to update saved task search
    And I click on Continue option in warning message
    Then Verify the system displays the following "Saved Task Search successfully updated"
    Then Wait for 3 seconds
    Then I will verify newly created saved task search is updated with new search parameter "<taskId>","<taskType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<updateType>","<updateStatus>","<updatePriority>"
    And Close the soft assertions
    Examples:
      |taskId | taskType | srType       | status   | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee           | createdBy          | createdOn | updateType | updateStatus | updatePriority | contactReason |
      |123    | General  | General SR 2 | Created  | today      | 5        | today   | CHIP       | 8089   | CHIP           | 912556     | true          | Mike       | Leonard    | User   | Service AccountOne | Service AccountOne | today     | Address    | Complete     | 3              ||

  @CP-34837 @CP-34837-02 @ozgen @crm-regression @ui-wf @tasksearch
  Scenario Outline: Update Save Search Parameters by clicking No
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I create the save task search
    And I click on cancel button on task search page
    And I will select save task search value from Search by Saved Task Search drop down
    When I update the Search parameter "Address","Escalated","1"
    Then I verify I see warning message to update saved task search
    And I click on cancel button on warning message
    Then Wait for 3 seconds
    Then I will verify newly created saved task search is updated with new search parameter "<taskId>","<taskType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<updateType>","<updateStatus>","<updatePriority>"
    And Close the soft assertions
    Examples:
      |taskId | taskType | srType       | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee           | createdBy          | createdOn | updateType | updateStatus | updatePriority | contactReason |
      |345    | General  | General SR 2 | Open    | today      | 5        | today   | CHIP       | 8089   | CHIP           | 912556     | true          | Mike       | Leonard    | User   | Service AccountOne | Service AccountOne | today     ||              ||               |