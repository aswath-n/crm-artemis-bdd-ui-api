Feature: Validation of Bulk Update Page

  @CP-25394 @CP-25394-01 @CP-25092 @CP-25092-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario Outline: Verification CheckBox and bulk Update button is displaying and Main Checkbox is selected or not in Task Search Results in CoverVA Project
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And Verify Bulk Update button and CheckBox is displaying
    Then I verify Main Check box is selected
    And Click on Bulk Update button
    Then I verify All Selected Task Ids in Bulk Update Screen
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      ||          || Created ||          ||            ||                || true          ||            ||          || today     ||

  @CP-25394 @CP-25394-02 @CP-25092 @CP-25092-01 @priyal @crm-regression @ui-wf-ineb
  Scenario Outline:  verify CheckBox and bulk Update button is displaying and Main Checkbox is selected or not in IN-EB Project
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And Verify Bulk Update button and CheckBox is displaying
    Then I verify Main Check box is selected
    Then I verify Main Check box is Unselected
    And Click on Bulk Update button
    Then I verify Task Ids in Bulk Update Screen
    And Close the soft assertions
    Examples:
      | taskId | taskType      | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      || Disenrollment || Created ||          ||            ||                || true          ||            ||          || today     ||

  @CP-21344 @CP-21344-01 @CP-25394 @CP-25394-03 @CP-25092 @CP-25092-02 @priyal @ui-wf-nj @nj-regression
  Scenario Outline: verify CheckBox and bulk Update button is displaying and main checkbox is selected or not in NJ-SBE Project
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And Verify Bulk Update button and CheckBox is displaying
    Then I verify Main Check box is Unselected
    And Click on Bulk Update button
    Then I verify Task Ids in Bulk Update Screen
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      ||          | Appeal | Closed ||          ||            ||                || true          ||            ||          || today     ||

  @CP-25394 @CP-25394-04 @CP-25092 @CP-25092-04 @priyal @crm-regression @ui-wf
  Scenario Outline: verify CheckBox and bulk Update button is displaying and Main Checkbox is select or deselect in BLCRM Project
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    Then I verify save task search section is displayed
    And Verify Bulk Update button and CheckBox is displaying
    Then I verify Main Check box is Unselected
    And Click on Bulk Update button
    Then I verify Task Ids in Bulk Update Screen
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      || General  ||        | today      ||         ||        ||            | false         ||            ||          || today     ||

  @CP-2695 @CP-2695-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Verification Main Checkbox is selected or not in Task Search Results in CoverVA Project
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
#    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify bulk update button is "disable"
    And I select main check box
    Then I verify bulk update button is "enable"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      ||          || Created | today      ||         ||        ||            | true          ||            ||          || today     ||

  @CP-2695 @CP-2695-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Verification Main Checkbox is selected or not in Task Search Results in CoverVA Project
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I select main check box
    And Click on Bulk Update button
    And I click on the back arrow button
    Then I verify warning message is displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Bulk Update Task |
    And I click on the back arrow button
    And I click on continue button on warning message
    Then I will verify task search page retains search criteria "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      ||          || Created ||          ||            ||                || true          ||            ||          || today     ||

  @CP-2695 @CP-2695-03 @crm-regression @ui-wf @vidya
  Scenario Outline: Verification Main Checkbox is selected or not in Task Search Results in BLCRM Project
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I select main check box
    And Click on Bulk Update button
    When I click to navigate "General" task page
    Then I verify warning message is displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Bulk Update Task |
    When I click to navigate "General" task page
    And I click on continue button on warning message
    Then I should return back to Create Task screen
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      ||          || Created ||          ||            ||                || true          ||            ||          || today     ||

  @CP-2695 @CP-2695-04 @crm-regression @ui-wf-ineb @vidya
  Scenario Outline: Verification Main Checkbox is selected or not in Task Search Results in IN-EB Project
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I select main check box
    And Click on Bulk Update button
    And I click on Next button in bulk update screen
    And I update following information in bulk update screen
      | priority | 2 |
    And I click on the back arrow button
    Then I verify warning message is displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Bulk Update Screen |
    And I click on the back arrow button
    And I click on continue button on warning message
    Then I will verify task search page retains search criteria "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      ||          || Created ||          || State      || MEDICAID/RID   || true          ||            ||          || today     ||

  @CP-2695 @CP-2695-05 @crm-regression @ui-wf @vidya
  Scenario Outline: Verification Main Checkbox is selected or not in Task Search Results in BLCRM Project
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I select main check box
    And Click on Bulk Update button
    And I click on Next button in bulk update screen
    And I update following information in bulk update screen
      | note | Test 123 |
    When I click to navigate "General" task page
    Then I verify warning message is displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Bulk Update Screen |
    When I click to navigate "General" task page
    And I click on continue button on warning message
    Then I should return back to Create Task screen
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      ||          || Created ||          ||            ||                || true          ||            ||          || today     ||

  @CP-2695 @CP-2695-06 @crm-regression @ui-wf-nj @vidya
  Scenario Outline: Verification Main Checkbox is selected or not in Task Search Results in NJ-SBE Project
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I select main check box
    And Click on Bulk Update button
    And I click on Next button in bulk update screen
    And I update following information in bulk update screen
      | assignee | Anton Zanin |
    When I click on cancel button in bulk update task page
    Then I verify warning message is displayed
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Bulk Update Screen |
    When I click on cancel button in bulk update task page
    And I click on continue button on warning message
    Then I will verify task search page retains search criteria "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      ||          || Created ||          ||            ||                || true          ||            ||          || today     ||

  @CP-2695 @CP-2695-07 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Verification Main Checkbox is selected or not in Task Search Results in CoverVA Project
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I select 2 pages records check box
    And Click on Bulk Update button
    Then I verify all selected records in bulk update screen
    And Close the soft assertions
    Examples:
      | taskId | taskType            | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      || Process Application || Created | today      ||         ||        ||            | true          ||            ||          || today     ||

  @CP-2695 @CP-2695-08 @crm-regression @ui-wf-ineb @vidya
  Scenario Outline: Verification Main Checkbox is selected or not in Task Search Results in IN-EB Project
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I select 3 pages records check box
    And Click on Bulk Update button
    Then I verify all selected records in bulk update screen
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      ||          || Created ||          ||            ||                || true          ||            ||          || today     ||

  @CP-2695 @CP-2695-09 @crm-regression @ui-wf-nj @vidya
  Scenario Outline: Verification Main Checkbox is selected or not in Task Search Results in NJ-SBE Project
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I select 3 pages records check box
    And Click on Bulk Update button
    Then I verify all selected records in bulk update screen
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      ||          || Open   ||          ||            ||                || true          ||            ||          || today     ||

#  @CP-2695 @CP-2695-10 @crm-regression @ui-wf @vidya
  Scenario Outline: Verification Main Checkbox is selected or not in Task Search Results in BLCRM Project
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I select 4 pages records check box
    And Click on Bulk Update button
    Then I verify all selected records in bulk update screen
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                  | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      ||          | General Service Request ||            ||         ||        ||            | true          ||            ||          || today     ||

  @CP-2695 @CP-2695-11 @crm-regression @ui-wf @vidya
  Scenario: Verification Main Checkbox is selected or not in Task Search Results in BLCRM Project
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | assignmentStatus | Unassigned |
      | status           | Created    |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I select 1 pages records check box
    And Click on Bulk Update button
    And I click on Next button in bulk update screen
    And I update following information in bulk update screen
      | assignee | Daryl Sharp |
    And I click on confirm bulk update button
    And I click on back to task search button
    And I verify all task are updated with "Only Assignee"
    And Close the soft assertions

  @CP-2695 @CP-2695-12 @crm-regression @ui-wf-nj @vidya
  Scenario: Scenario: Verification of bulk update functionality in NJ-SBE
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | assignmentStatus | Assigned |
      | status           | Created  |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I select 1 pages records check box
    And Click on Bulk Update button
    And I click on Next button in bulk update screen
    And I update following information in bulk update screen
      | priority | 2 |
    And I click on confirm bulk update button
    And I click on back to task search button
    And I verify all task are updated with "Only Priority"
    And Close the soft assertions

  @CP-2695 @CP-2695-13 @crm-regression @ui-wf-coverVA @vidya
  Scenario: Verification Main Checkbox is selected or not in Task Search Results in CoverVA Project
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | assignmentStatus | Assigned |
      | status           | Created  |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I select 1 pages records check box
    And Click on Bulk Update button
    And I click on Next button in bulk update screen
    And I update following information in bulk update screen
      | note | String + Number |
    And I click on confirm bulk update button
#    Then I verify system displays Success Message
    And I click on back to task search button
    And I verify all task are updated with "Only Note"
    And Close the soft assertions

  @CP-2695 @CP-2695-14 @crm-regression @ui-wf-ineb @vidya
  Scenario: Verification Main Checkbox is selected or not in Task Search Results in IN-EB Project
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | assignmentStatus | Unassigned |
      | status           | Created    |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I select 1 pages records check box
    And Click on Bulk Update button
    And I click on Next button in bulk update screen
    And I update following information in bulk update screen
      | assignee | ECMS Service null |
      | priority | 2                 |
      | note     | String            |
    And I click on confirm bulk update button
    And I click on back to task search button
    And I verify all task are updated with "Assignee,Priority and Note"
    And Close the soft assertions

  @CP-25092 @CP-25092-05 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: VerificationCheckbox and Bulk Update button is not displaying in Task Search Results in CoverVA Project
    Given I logged into CRM with "Service Account 8" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | assignmentStatus | Assigned |
      | status           | Created  |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And Verify Bulk Update button and CheckBox is not displaying
    And Close the soft assertions

  @CP-25092 @CP-25092-06 @crm-regression @ui-wf @priyal
  Scenario: VerificationCheckbox and Bulk Update button is not displaying in Task Search Results in BLCRM Project
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | assignmentStatus | Assigned |
      | status           | Created  |
      | advanceSearch    | true     |
      | createdOn        | today    |
    And I click on search button on task search page
#    Then Wait for 10000 seconds
    And I verify save task search section is displayed
    And Verify Bulk Update button and CheckBox is not displaying
    And Close the soft assertions

  @CP-25092 @CP-25092-07 @crm-regression @ui-wf-ineb @priyal
  Scenario Outline: VerificationCheckbox and Bulk Update button is not displaying in Task Search Results in INEB Project
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And Verify Bulk Update button and CheckBox is not displaying
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType               | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      ||          | HCC Outbound Call SR ||            ||         ||        ||            | true          ||            ||          || today     ||

  @CP-25092 @CP-25092-08 @crm-regression @ui-wf-nj @priyal
  Scenario Outline: VerificationCheckbox and Bulk Update button is not displaying in Task Search Results in NJ-SBE Project
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And Verify Bulk Update button and CheckBox is not displaying
    And Close the soft assertions
    Examples:
      | taskId | taskType      | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      || Outbound Call | Appeal ||            ||         ||        ||            | true          ||            ||          || today     ||
