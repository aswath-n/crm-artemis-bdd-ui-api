Feature: SR Fields Validation for IN-EB

  @CP-30456 @CP-30456-01 @crm-regression @ui-wf-ineb @vidya
  Scenario: IN-EB: Create HCC Outbound Call Service Request
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "HCC Outbound Call SR" service request page
    Then I verify text box Date and Time field value and error message for following fields
      | CONTACT NAME    |
      | PREFERRED PHONE |
    And Close the soft assertions

  @CP-32921 @CP-32921-01 @crm-regression @INEB-UI-Regression @ui-wf-ineb @priyal
  Scenario Outline: Validate CSR Name field value on create/edit page
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    Then Verify "Service" auto suggestion value is displaying for csr name
      | Service AccountOne  |
      | Service TesterFive  |
      | Service TesterSeven |
      | Service TesterThree |
      | Service TesterTwo   |
    Then Verify "Aaron" auto suggestion value is displaying for csr name
      | Aaron Solenberg |
      | Aaron Thomas    |
    And I click on cancel button on create task type screen
    And I click on continue button on warning message
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    Then Verify "Service" auto suggestion value is displaying for csr name
      | Service TesterThree |
      | Service TesterFive  |
      | Service TesterTwo   |
      | Service AccountOne  |
      | Service TesterSeven |
    Then Verify "Aaron" auto suggestion value is displaying for csr name
      | Aaron Solenberg |
      | Aaron Thomas    |
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Customer Service Complaint | Open   ||          ||            ||                || true          ||            ||          | Service TesterTwo ||             |
