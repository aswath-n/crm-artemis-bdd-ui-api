Feature: Validate Task Notes on View/Edit

  @CP-10073 @CP-10073-01 @CP-19835 @CP-19835-01 @vidya @priyal @crm-regression @ui-wf
  Scenario: Validate Notes component in view task deatils page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "","General","","","","","","","","","","true","","","","","Service AccountOne","today",""
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I verify notes component present in view or edit task details page
    And I verify Notes component field length
    And Close the soft assertions

  @CP-10073 @CP-10073-02 @CP-18461 @CP-18461-01 @priyal @nj-regression @ui-wf-nj
  Scenario: Add Notes Component in view task deatils page
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "Work Queue" page
    And I expend first Task from the list by clicking in Task ID
    And I will provide information to task or SR note component
      | noteValue | String|
    And I click on cancel button on Note Component
    And I see "If you select Continue, your note will not be saved" alert message displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | view Task SR page |
    Then I Verify Note Component is not empty after click on Cancel button on Warning Popup
    And I click on cancel button on Note Component
    And I see "If you select Continue, your note will not be saved" alert message displayed
    And I click on continue button on warning message
    Then I Verify Note Component empty after click on Continue button on Warning Popup
    And I will provide information to task or SR note component
      | noteValue | String|
    And click on save button present in task or SR note component
    Then I verify Success message is displayed on note component
    And I verify saved task note
    And I click on edit button on view task page
    And I will provide information to task or SR note component
      | noteValue | Test3434|
    And I click on cancel button on Note Component
    And I see "If you select Continue, your note will not be saved" alert message displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Edit Task SR page |
    Then I Verify Note Component is not empty after click on Cancel button on Warning Popup
    And I click on cancel button on Note Component
    And I see "If you select Continue, your note will not be saved" alert message displayed
    And I click on continue button on warning message
    Then I Verify Note Component empty after click on Continue button on Warning Popup
    And Close the soft assertions

  @CP-10073 @CP-10073-03 @CP-19835 @CP-19835-02 @CP-33898 @CP-33898-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @priyal
  Scenario: Validate Notes component in edit task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "My Task" page
    Then I select records count in pagination dropdown as "show 50" in "My Task" page
    Then I verify view pagination "show 50" in "My Task" page
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    Then I verify notes component present in view or edit task details page
    And I verify Notes component field length
    And Close the soft assertions

  @CP-10073 @CP-10073-04 @CP-18461 @CP-18461-02 @priyal @crm-regression @ui-wf
  Scenario: Add Notes Component in edit task deatils page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will provide information to task or SR note component
      | noteValue | Number|
    And I will click on back arrow on view task page
    And I see "If you navigate away, your information will not be saved" alert message displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Edit Task SR page |
    Then I Verify Note Component is not empty after click on Cancel button on Warning Popup
    And I click on cancel button on Note Component
    And I see "If you select Continue, your note will not be saved" alert message displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Edit Task SR page |
    Then I Verify Note Component is not empty after click on Cancel button on Warning Popup
    And I click on cancel button on Note Component
    And I see "If you select Continue, your note will not be saved" alert message displayed
    And I click on continue button on warning message
    Then I Verify Note Component empty after click on Continue button on Warning Popup
    And I will provide information to task or SR note component
      | noteValue | Number|
    And click on save button present in task or SR note component
    Then I verify Success message is displayed on note component
    And I verify saved task note
    And Close the soft assertions

  @CP-10073 @CP-10073-05 @CP-19835 @CP-19835-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @priyal
  Scenario: Validate Notes component in view SR deatils page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Vally" and Last Name as "AAAAA"
    When I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I click on first SR ID
    Then I verify notes component present in view or edit task details page
    And I verify Notes component field length
    And Close the soft assertions

  @CP-10073 @CP-10073-06 @CP-19835 @CP-19835-04 @CP-18461 @CP-18461-03 @vidya @priyal @crm-regression @ui-wf
  Scenario: Add Notes Component in view SR deatils page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to case and consumer search page
    When I searched customer have First Name as "AlexandroJaQkL" and Last Name as "LuettgenIeaNu"
    When I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to view SR details page by clicking on existing sr id
    And I will provide information to task or SR note component
      | noteValue | String + Number|
    And I click on cancel button on Note Component
    And I see "If you select Continue, your note will not be saved" alert message displayed
    And I click on continue button on warning message
    Then I Verify Note Component empty after click on Continue button on Warning Popup
    And I will click on back arrow on view task page
    And Wait for 2 seconds
    And I Verify user is navigate back to Task and service Request Page
    And I click on first SR ID
    And I will provide information to task or SR note component
      | noteValue | Number|
    And I will click on back arrow on view task page
    And I see "If you navigate away, your information will not be saved" alert message displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | view Task SR page |
    Then I Verify Note Component is not empty after click on Cancel button on Warning Popup
    And I will click on back arrow on view task page
    And I see "If you navigate away, your information will not be saved" alert message displayed
    And I click on continue button on warning message
    And I Verify user is navigate back to Task and service Request Page
    And I click on first SR ID
    And I will provide information to task or SR note component
      | noteValue | String + Number|
    And click on save button present in task or SR note component
    #Then I verify Success message is displayed on note component
    And I verify saved task note
    And Close the soft assertions

  @CP-10073 @CP-10073-07 @crm-regression @ui-wf @priyal
  Scenario: Validate Notes component in edit SR page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Josh" and Last Name as "Maine"
    When I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to view SR details page by clicking on existing sr id
    And I click on edit service request button
    Then I verify notes component present in view or edit task details page
    And I verify Notes component field length
    And Close the soft assertions

  @CP-10073 @CP-10073-08 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Add Notes component in edit SR deatils page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Vally" and Last Name as "AAAAA"
    When I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I click on first SR ID
    And I click on edit service request button
    And I will provide information to task or SR note component
      | noteValue | Test@123|
    And click on save button present in task or SR note component
    Then I verify Success message is displayed on note component
    And I verify saved task note
    And Close the soft assertions

  @CP-10073 @CP-10073-09 @CP-18461 @CP-18461-10 @priyal @crm-regression @ui-wf
  Scenario Outline: Notes Components are not present in view page
    Given I logged into CRM with "Service Tester 2" and select a project "BLCRM"
    #When I navigate to "Task Search" page
    And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And In search result click on task id to navigate to view page
    Then I verify notes components and edit button not present in view page
    Then I verify List of existing Notes including all fields and ordered by date time descending
    Then I verify Note Component has five records with pagination
    #And I click on edit button on view task page
    #Then I verify Note Component has five records with pagination
    And Close the soft assertions
    Examples:
      |taskId |taskType |srType|status |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      |75624  |General  ||       ||        ||          ||              ||false        ||          ||        ||         ||

  @CP-10073 @CP-10073-10 @CP-18461 @CP-18461-09 @nj-regression @ui-wf-njA @priyal
  Scenario Outline: Validate Notes component in descending order
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And In search result click on task id to navigate to view page
    Then I verify List of existing Notes including all fields and ordered by date time descending
    Then I verify Note Component has five records with pagination
    And I click on edit button on view task page
    Then I verify Note Component has five records with pagination
    And Close the soft assertions
    Examples:
      |taskId |taskType |srType|status |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      |2674   ||      ||          ||       ||      ||          |false        ||          ||        ||         ||

  @18226 @CP-18226-01 @CP-19835 @CP-19835-05 @CP-18461 @CP-18461-08 @priyal @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario Outline: Validate we can add not to closed Application SR
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And In search result click on task id to navigate to view page
    And I will provide information to task or SR note component
      | noteValue | String + Number|
    And I click on cancel button on Note Component
    And I see "If you select Continue, your note will not be saved" alert message displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | view Task SR page |
    Then I Verify Note Component is not empty after click on Cancel button on Warning Popup
    And click on save button present in task or SR note component
    Then I verify Success message is displayed on note component
    And I verify saved task note
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType        |status |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      ||        |Application SR|Closed ||        ||          ||              ||false        ||          ||        ||         ||

  @18226 @CP-18226-02 @CP-19835 @CP-19835-06 @CP-18461 @CP-18461-05 @priyal @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario Outline: Validate we can add not to closed Renewal SR
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will provide information to task or SR note component
      | noteValue | String|
    And I will click on back arrow on view task page
    And I see "If you navigate away, your information will not be saved" alert message displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Edit Task SR page |
    Then I Verify Note Component is not empty after click on Cancel button on Warning Popup
    And I will click on back arrow on view task page
    And I see "If you navigate away, your information will not be saved" alert message displayed
    And I click on continue button on warning message
    Then I verify user is navigate back to task search page
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will provide information to task or SR note component
      | noteValue | Number|
    And click on save button present in task or SR note component
    Then I verify Success message is displayed on note component
    And I verify saved task note
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType    |status |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      ||        |Renewal SR|Closed | today    ||       ||      ||          |false        ||          ||        ||         ||

  @18226 @CP-18226-03 @CP-18461 @CP-18461-06 @priyal @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario Outline: Validate we can add not to closed Complaint SR
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And In search result click on task id to navigate to view page
    And I will provide information to task or SR note component
      | noteValue | Line Break|
    And I will click on back arrow on view task page
    And I see "If you navigate away, your information will not be saved" alert message displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | view Task SR page |
    Then I Verify Note Component is not empty after click on Cancel button on Warning Popup
    And I will click on back arrow on view task page
    And I see "If you navigate away, your information will not be saved" alert message displayed
    And I click on continue button on warning message
    Then I verify user is navigate back to task search page
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will provide information to task or SR note component
      | noteValue | Line Break|
    And click on save button present in task or SR note component
    Then I verify Success message is displayed on note component
    And I verify saved task note
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType      |status |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      ||        |Complaint SR|Closed | today    ||       ||      ||          |false        ||          ||        ||         ||

  @18226 @CP-18226-04 @CP-21882 @CP-21882-03 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario Outline: Validate we can add not to closed Appeal SR
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And In search result click on task id to navigate to view page
    And I will provide information to task or SR note component
      | noteValue | Line Break |
    And click on save button present in task or SR note component
    Then I verify Success message is displayed on note component
    And I verify saved task note
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType    |status |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      ||        |Appeals SR|Closed ||        ||          ||              ||false        ||          ||        ||         ||

  @18226 @CP-18226-05 @CP-18461 @CP-18461-12 @priyal @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario Outline: Validate we can add not to closed App SR V1
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","App SR V1 ","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And In search result click on task id to navigate to view page
    And I will provide information to task or SR note component
      | noteValue | Number |
    And click on save button present in task or SR note component
    Then I verify Success message is displayed on note component
    And I verify saved task note
    Then I verify Note Component has five records with pagination
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType  |status |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      ||        ||Closed ||        ||          ||              ||false        ||          ||        ||         ||

  @18226 @CP-18226-06 @CP-18461 @CP-18461-04 @priyal @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario Outline: Validate we can add not to closed App Renewal SR V1
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will provide information to task or SR note component
      | noteValue | Test@123|
    And I click on cancel button on Note Component
    And I see "If you select Continue, your note will not be saved" alert message displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Edit Task SR page |
    Then I Verify Note Component is not empty after click on Cancel button on Warning Popup
    And I click on cancel button on Note Component
    And I see "If you select Continue, your note will not be saved" alert message displayed
    And I click on continue button on warning message
    Then I Verify Note Component empty after click on Continue button on Warning Popup
    And I will provide information to task or SR note component
      | noteValue |String|
    And click on save button present in task or SR note component
    Then I verify Success message is displayed on note component
    And I verify saved task note
    And Close the soft assertions
    Examples:
      |taskId |taskType |srType           |status |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      ||         |App Renewal SR V1|Closed ||        ||          ||              ||false        ||          ||        ||         ||

  @18226 @CP-18226-07 @CP-18461 @CP-18461-11 @priyal @vidya @crm-regression @ui-wf
  Scenario Outline: Validate we can add not to closed Genearl SR
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    #When I navigate to "Task Search" page
    And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I will provide information to task or SR note component
      | noteValue |String|
    And click on save button present in task or SR note component
    Then I verify Success message is displayed on note component
    And I verify saved task note
    Then I verify Note Component has five records with pagination
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType                 |status |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy         |createdOn|contactReason|
      ||        |General Service Request|Closed ||        ||          ||              || true        ||          ||        |Service AccountOne||             |

  @18226 @CP-18226-06 @CP-21882 @CP-21882-02 @vidya @crm-regression @ui-wf
  Scenario Outline: Validate we can add not to closed General SR in edit page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will provide information to task or SR note component
      | noteValue | Line Break|
    And click on save button present in task or SR note component
    Then I verify Success message is displayed on note component
    And I verify saved task note
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType                 |status |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy         |createdOn|contactReason|
      ||        |General Service Request|Closed ||        ||          ||              || true        ||          ||        |Service AccountOne||             |

  @CP-21882 @CP-21882-01 @vidya @nj-regression @ui-wf-nj
  Scenario: Verify Task Notes Component accept line breaks and user able to copy paste the text
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "Work Queue" page
    And I expend first Task from the list by clicking in Task ID
    And I will provide information to task or SR note component
      | noteValue | Line Break|
    And click on save button present in task or SR note component
    Then I verify Success message is displayed on note component
    And I verify saved task note
    And I click on edit button on view task page
    And I will copy and paste the previous note text
    And click on save button present in task or SR note component
    Then I verify Success message is displayed on note component
    And I verify saved task note
    And Close the soft assertions

  @18461 @CP-18461-13 @priyal @crm-regression @ui-wf-ineb
  Scenario Outline: Validate the Pagination for note component and verify warning popup
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And In search result click on task id to navigate to view page
    And I will provide information to task or SR note component
      | noteValue | Test 34|
    And I will click on back arrow on view task page
    And I see "If you navigate away, your information will not be saved" alert message displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | view Task SR page |
    Then I Verify Note Component is not empty after click on Cancel button on Warning Popup
    And I will click on back arrow on view task page
    And I see "If you navigate away, your information will not be saved" alert message displayed
    And I click on continue button on warning message
    Then I verify user is navigate back to task search page
    And In search result click on task id to navigate to view page
    Then I verify Note Component has five records with pagination
    And I click on edit button on view task page
    Then I verify Note Component has five records with pagination
    And I will provide information to task or SR note component
      | noteValue | Line Break|
    And I will click on back arrow on view task page
    And I see "If you navigate away, your information will not be saved" alert message displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Edit Task SR page |
    Then I Verify Note Component is not empty after click on Cancel button on Warning Popup
    And I will click on back arrow on view task page
    And I see "If you navigate away, your information will not be saved" alert message displayed
    And I click on continue button on warning message
    Then I verify user is navigate back to task search page
    And Close the soft assertions
    Examples:
      |taskId |taskType   |srType|status |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      |4079   |DCS Request||       ||        ||          ||              ||false        ||          ||        ||         ||

  @18461 @CP-18461-14 @priyal @crm-regression @ui-wf-ineb
  Scenario: Validate the Pagination for note component and verify warning popup after click on cancel button
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "fXgPV" and Last Name as "lnNbgvI"
    When I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I click on first SR ID
    And I will provide information to task or SR note component
      | noteValue | Test %$#%|
    And I click on cancel button on Note Component
    And I see "If you select Continue, your note will not be saved" alert message displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | view Task SR page |
    Then I Verify Note Component is not empty after click on Cancel button on Warning Popup
    And I click on cancel button on Note Component
    And I see "If you select Continue, your note will not be saved" alert message displayed
    And I click on continue button on warning message
    Then I Verify Note Component empty after click on Continue button on Warning Popup
    And I will click on back arrow on view task page
    And I Verify user is navigate back to Task and service Request Page
    And I click on first SR ID
    And I click on edit service request button
    And I will provide information to task or SR note component
      | noteValue | Line726347 |
    And I click on cancel button on Note Component
    And I see "If you select Continue, your note will not be saved" alert message displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Edit Task SR page |
    Then I Verify Note Component is not empty after click on Cancel button on Warning Popup
    And I click on cancel button on Note Component
    And I see "If you select Continue, your note will not be saved" alert message displayed
    And I click on continue button on warning message
    Then I Verify Note Component empty after click on Continue button on Warning Popup
    And Close the soft assertions

  @18461 @CP-18461-07 @priyal @nj-regression @ui-wf-nj
  Scenario Outline: Validate the Pagination for note component and verify warning popup
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And In search result click on task id to navigate to view page
    And I will provide information to task or SR note component
      | noteValue | Line Break|
    And I will click on back arrow on view task page
    And I see "If you navigate away, your information will not be saved" alert message displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | view Task SR page |
    Then I Verify Note Component is not empty after click on Cancel button on Warning Popup
    And I will click on back arrow on view task page
    And I see "If you navigate away, your information will not be saved" alert message displayed
    And I click on continue button on warning message
    Then I verify user is navigate back to task search page
    And In search result click on task id to navigate to view page
    Then I verify Note Component has five records with pagination
    And I click on edit service request button
    And I will provide information to task or SR note component
      | noteValue | Line54754^#%#$|
    And I will click on back arrow on view task page
    And I see "If you navigate away, your information will not be saved" alert message displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Edit Task SR page |
    Then I Verify Note Component is not empty after click on Cancel button on Warning Popup
    And I will click on back arrow on view task page
    And I see "If you navigate away, your information will not be saved" alert message displayed
    And I click on continue button on warning message
    Then I verify user is navigate back to task search page
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    Then I verify Note Component has five records with pagination
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType  |status |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      |14174  ||Appeal  ||          ||       ||      ||          |false        ||          ||        ||         ||
