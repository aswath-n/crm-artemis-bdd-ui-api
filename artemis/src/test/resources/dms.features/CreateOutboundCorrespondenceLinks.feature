Feature: Creating Outbound Correspondence from CP Links section BLCRM

  @CP-28601 @CP-28601-01  @ui-ecms1 @RuslanL
  Scenario: Verify the Links section not appears on OB correspondence request page (Active contact - Consumer on case)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I minimize Genesys popup if populates
    And I create an Outbound Correspondence
    And I should NOT see the Links section under Save button
    When I select an Outbound Correspondence Type
    Then I should NOT see the Links section under Save button

  @CP-28601 @CP-28601-02  @ui-ecms1 @RuslanL
  Scenario: Verify the Links section not appears on OB correspondence request page (Active contact - Consumer with no case)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I minimize Genesys popup if populates
    And I create an Outbound Correspondence
    And I should NOT see the Links section under Save button
    When I select an Outbound Correspondence Type
    Then I should NOT see the Links section under Save button

  @CP-28601 @CP-28601-03  @ui-ecms1 @RuslanL
  Scenario: Verify the Links section not appears on OB correspondence request page (context of consumer on a case - searching for a case from case / consumer searching)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I should NOT see the Links section under Save button
    When I select an Outbound Correspondence Type
    Then I should NOT see the Links section under Save button

  @CP-28601 @CP-28601-04  @ui-ecms2 @RuslanL
  Scenario: Verify the Links section not appears on OB correspondence request page (context of a consumer without a case - searching for a case from case / consumer searching)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I should NOT see the Links section under Save button
    When I select an Outbound Correspondence Type
    Then I should NOT see the Links section under Save button

  @CP-28601 @CP-28601-05  @ui-ecms2 @RuslanL
  Scenario: Verify the Links section not appears on OB correspondence request page (from my Task for Active contact - Consumer on case)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then If any In Progress task present then update that to Cancelled
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 13241             |
    Then I send the request for the External task endpoint
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And I click on initiate button in task search page
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I create an Outbound Correspondence
    And I should NOT see the Links section under Save button
    When I select an Outbound Correspondence Type
    Then I should NOT see the Links section under Save button
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I click on save in Task Slider

  @CP-28601 @CP-28601-06  @ui-ecms2 @RuslanL
  Scenario: Verify the Links section not appears on OB correspondence request page (from my Task for Active contact - Consumer with no case)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then If any In Progress task present then update that to Cancelled
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | taskTypeId | 13241             |
    Then I send the request for the External task endpoint
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And I click on initiate button in task search page
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I create an Outbound Correspondence
    And I should NOT see the Links section under Save button
    When I select an Outbound Correspondence Type
    Then I should NOT see the Links section under Save button
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I click on save in Task Slider
