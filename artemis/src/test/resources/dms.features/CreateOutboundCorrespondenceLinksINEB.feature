Feature: Creating Outbound Correspondence from CP Links section IN-EB

  @CP-28601 @CP-28601-09 @ui-ecms-ineb @RuslanL
  Scenario: Verify the Links section not appears on OB correspondence request page (Active contact - Consumer on case)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id for IN-EB
    And I create an Outbound Correspondence
    And I should NOT see the Links section under Save button
    And I have selected "HCC Welcome - IA" as a type
    Then I should NOT see the Links section under Save button

  @CP-28601 @CP-28601-10 @ui-ecms-ineb @RuslanL
  Scenario: Verify the Links section not appears on OB correspondence request page (Active contact - Consumer with no case)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer not on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id for IN-EB
    And I minimize Genesys popup if populates
    And I create an Outbound Correspondence
    And I should NOT see the Links section under Save button
    And I have selected "HCC Welcome - IA" as a type
    Then I should NOT see the Links section under Save button

  @CP-28601 @CP-28601-11 @ui-ecms-ineb @RuslanL
  Scenario: Verify the Links section not appears on OB correspondence request page (context of consumer on a case - searching for a case from case / consumer searching)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I should NOT see the Links section under Save button
    And I have selected "HCC Welcome - IA" as a type
    Then I should NOT see the Links section under Save button

  @CP-28601 @CP-28601-12 @ui-ecms-ineb @RuslanL
  Scenario: Verify the Links section not appears on OB correspondence request page (context of a consumer without a case - searching for a case from case / consumer searching)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer not on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I should NOT see the Links section under Save button
    And I have selected "HCC Welcome - IA" as a type
    Then I should NOT see the Links section under Save button

  @CP-28601 @CP-28601-13 @ui-ecms-ineb @RuslanL
  Scenario: Verify the Links section not appears on OB correspondence request page (from my Task for Active contact - Consumer on case)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then If any In Progress task present then update that to Cancelled
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 15350             |
    Then I send the request for the External task endpoint
    Then I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id for IN-EB
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And I click on initiate button in task search page
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I create an Outbound Correspondence
    And I should NOT see the Links section under Save button
    And I have selected "HCC Welcome - IA" as a type
    Then I should NOT see the Links section under Save button
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I select random value from Action Taken drop down from task details
    And I click on save in Task Slider

  @CP-28601 @CP-28601-14 @ui-ecms-ineb @RuslanL
  Scenario: Verify the Links section not appears on OB correspondence request page (from my Task for Active contact - Consumer with no case)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then If any In Progress task present then update that to Cancelled
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 15350             |
    Then I send the request for the External task endpoint
    And I have a consumer not on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Then I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id for IN-EB
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And I click on initiate button in task search page
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I create an Outbound Correspondence
    And I should NOT see the Links section under Save button
    And I have selected "HCC Welcome - IA" as a type
    Then I should NOT see the Links section under Save button
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I select random value from Action Taken drop down from task details
    And I click on save in Task Slider

  @CP-28601 @CP-28601-15 @ui-ecms-ineb @RuslanL
  Scenario: Verify the Links section not appears on OB correspondence request page (from my Task - context of consumer on a case)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then If any In Progress task present then update that to Cancelled
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 15350             |
    Then I send the request for the External task endpoint
    Then I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And I click on initiate button in task search page
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I should NOT see the Links section under Save button
    And I have selected "HCC Welcome - IA" as a type
    Then I should NOT see the Links section under Save button
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I select random value from Action Taken drop down from task details
    And I click on save in Task Slider

  @CP-28601 @CP-28601-16 @ui-ecms-ineb @RuslanL
  Scenario: Verify the Links section not appears on OB correspondence request page (from my Task - context of a consumer without a case)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then If any In Progress task present then update that to Cancelled
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 15350             |
    Then I send the request for the External task endpoint
    Then I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And I click on initiate button in task search page
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I have a consumer not on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I should NOT see the Links section under Save button
    And I have selected "HCC Welcome - IA" as a type
    Then I should NOT see the Links section under Save button
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I select random value from Action Taken drop down from task details
    And I click on save in Task Slider