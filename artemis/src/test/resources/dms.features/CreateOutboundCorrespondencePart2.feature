Feature: Creating Outbound Correspondence from CP part 2

  @CP-24718 @CP-24718-01  @ui-ecms1 @RuslanL
  Scenario: Verify Case ID will display and be un-editable, when user selects a Type that is configured to Require a Case ID (Context of Case)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I have selected "Case - CAONLY" as a type
    Then "Case ID" will display and be un-editable

  @CP-24718 @CP-24718-02  @ui-ecms1 @RuslanL
  Scenario: Verify Case ID will display and be un-editable, when user selects a Type that is configured to Require a Case ID (Context of Case-Related Contact)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I minimize Genesys popup if populates
    And I create an Outbound Correspondence
    And I have selected "Case - CAONLY" as a type
    Then "Case ID" will display and be un-editable


  @CP-24718 @CP-24718-04  @ui-ecms1 @RuslanL
  Scenario: Verify Consumer ID will display and be un-editable, when user selects a Type that is configured to require a Consumer ID (Context of Consumer Profile not on a Case)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I have selected "Consumer - CONONLY" as a type
    Then "Consumer ID" will display and be un-editable

  @CP-24718 @CP-24718-05  @ui-ecms1 @RuslanL
  Scenario: Verify Consumer ID will display and be un-editable, when user selects a Type that is configured to Require a Consumer ID (Context of Consumer-Related Contact)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I minimize Genesys popup if populates
    And I create an Outbound Correspondence
    And I have selected "Consumer - CONONLY" as a type
    Then "Consumer ID" will display and be un-editable

  @CP-24718 @CP-24718-06  @ui-ecms2 @RuslanL
  Scenario: Verify Consumer ID will display and be un-editable, when user selects a Type that is configured to Require a Consumer ID (Context of Consumer-Related task)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then If any In Progress task present then update that to Cancelled
    When I have a request to create External task with the following values
      | taskTypeId | 12544 |
    Then I send the request for the External task endpoint
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And I click on initiate button in task search page
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I have selected "Consumer - CONONLY" as a type
    Then "Consumer ID" will display and be un-editable
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I click on save in Task Slider


  @CP-24718 @CP-24718-07  @ui-ecms2 @RuslanL
  Scenario: Verify Consumers dropdown will include the active Consumers associated with the Case, when the Type is configured to require a Consumer ID or Consumer IDs (Context of Case)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I have selected "Case Consumers - CCSONLY" as a type
    Then Consumer ID's dropdown will include the active Consumers associated with the Case

  @CP-24718 @CP-24718-08  @ui-ecms2 @RuslanL
  Scenario: Verify Consumers dropdown will include the active Consumers associated with the Case, when the Type is configured to require a Consumer ID or Consumer IDs (Context of Case-Related Contact)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I minimize Genesys popup if populates
    And I create an Outbound Correspondence
    And I have selected "Case Consumers - CCSONLY" as a type
    Then Consumer ID's dropdown will include the active Consumers associated with the Case

  @CP-24718 @CP-24718-10  @ui-ecms2 @RuslanL
  Scenario: Verify Body Data section displays with the body data fields editable, when user selects a Type that is configured to have a Body Data Structure(Active contact - Consumer on case)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I minimize Genesys popup if populates
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    Then Body Data section displays with the body data fields editable

  @CP-24718 @CP-24718-11  @ui-ecms2 @RuslanL
  Scenario: Verify Body Data section displays with the body data fields editable, when user selects a Type that is configured to have a Body Data Structure(Active contact - Consumer with no case)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I minimize Genesys popup if populates
    And I create an Outbound Correspondence
    And I have selected "Consumer - CONONLY" as a type
    Then Body Data section displays with the body data fields editable
