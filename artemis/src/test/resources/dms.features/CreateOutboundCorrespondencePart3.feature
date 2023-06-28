Feature: Creating Outbound Correspondence from CP part 3

  @CP-24718 @CP-24718-12  @ui-ecms1 @RuslanL
  Scenario: Verify Body Data section displays with the body data fields editable, when user selects a Type that is configured to have a Body Data Structure (context of consumer on a case - searching for a case from case / consumer searching)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    Then Body Data section displays with the body data fields editable

  @CP-24718 @CP-24718-13  @ui-ecms1 @RuslanL
  Scenario: Verify Body Data section displays with the body data fields editable, when user selects a Type that is configured to have a Body Data Structure (context of a consumer without a case - searching for a case from case / consumer searching)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I have selected "Consumer - CONONLY" as a type
    Then Body Data section displays with the body data fields editable

  @CP-24718 @CP-24718-14  @ui-ecms1 @RuslanL
  Scenario: Verify Body Data section displays with the body data fields editable, when user selects a Type that is configured to have a Body Data Structure (from my Task for Active contact - Consumer on case)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then If any In Progress task present then update that to Cancelled
    When I have a request to create External task with the following values
      | taskTypeId | 13241 |
    Then I send the request for the External task endpoint
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And I click on initiate button in task search page
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    Then Body Data section displays with the body data fields editable
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I click on save in Task Slider

  @CP-24718 @CP-24718-15  @ui-ecms1 @RuslanL
  Scenario: Verify Body Data section displays with the body data fields editable, when user selects a Type that is configured to have a Body Data Structure (from my Task for Active contact - Consumer with no case)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then If any In Progress task present then update that to Cancelled
    When I have a request to create External task with the following values
      | taskTypeId | 13241 |
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
    Then Body Data section displays with the body data fields editable
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I click on save in Task Slider


  @CP-24718 @CP-24718-16  @ui-ecms1 @RuslanL
  Scenario: Verify Body Data section displays with the body data fields editable, when user selects a Type that is configured to have a Body Data Structure (from my Task - context of consumer on a case)
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
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    Then Body Data section displays with the body data fields editable

     #as per CP-33215,its out of scope
  #@CP-24718 @CP-24718-17  @ui-ecms2 @RuslanL
  Scenario: Verify Body Data section displays with the body data fields editable, when user selects a Type that is configured to have a Body Data Structure (from my Task - context of a consumer without a case)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "My Task" page
    And I will search for the task with status "In-Progress"
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I choose correspondence type which was created
    Then Body Data section displays with the body data fields editable

  @CP-2960 @CP-2960-01 @ui-ecms2 @burak ## will fail until CP-2960 fully implemented
  Scenario Outline: Verify Default channels are selected after selecting default send to checkbox for consumers for one channel (Context of Case Profile)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole      | Primary Individual |
      | <destinationType> | <destination>      |
    And I initiate Case Correspondence API for created Case
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched case and consumer created by api
    And I click on the first case and consumer search result
    And I have selected an Outbound Correspondence through the UI "ALL CHANNELS - 000001" as type
    And I select default send to checkbox
    And I verify usable channels are selected for default consumers
    Examples:
      | destinationType  | destination                   |
      | mailDestination  | random active mailing address |
      | emailDestination | random active email address   |
      | faxDestination   | random active fax phone       |
      | textDestination  | random active text phone      |

  @CP-2960 @CP-2960-02 @ui-ecms2 @burak ## will fail until CP-2960 fully implemented
  Scenario: Verify Default channels are selected after selecting default send to checkbox for consumers for multiple channels (Context of Case Profile)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | mailDestination | random active mailing address |
    And I add another "Primary Individual" on the "previouslyCreated" case with a new "mailDestination" that will receive an Outbound Correspondence
    And I add another "Primary Individual" on the "previouslyCreated" case with a new "emailDestination" that will receive an Outbound Correspondence
    And I add another "Primary Individual" on the "previouslyCreated" case with a new "faxDestination" that will receive an Outbound Correspondence
    And I add another "Primary Individual" on the "previouslyCreated" case with a new "textDestination" that will receive an Outbound Correspondence
    And I initiate Case Correspondence API for created Case
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched case and consumer created by api
    And I click on the first case and consumer search result
    And I have selected an Outbound Correspondence through the UI "ALL CHANNELS - 000001" as type
    And I select default send to checkbox
    And I verify usable channels are selected for default consumers

  @CP-2960 @CP-2960-03 @ui-ecms2 @burak ## will fail until CP-2960 fully implemented
  Scenario Outline: Verify Default channels are selected after selecting default send to checkbox for consumers for one channel (Context of Active Contact)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole      | Primary Individual |
      | <destinationType> | <destination>      |
    And I initiate Case Correspondence API for created Case
    And I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I minimize Genesys popup if populates
    And I have selected an Outbound Correspondence through the UI "ALL CHANNELS - 000001" as type
    And I select default send to checkbox
    And I verify usable channels are selected for default consumers
    Examples:
      | destinationType  | destination                   |
      | mailDestination  | random active mailing address |
      | emailDestination | random active email address   |
      | faxDestination   | random active fax phone       |
      | textDestination  | random active text phone      |

  @CP-2960 @CP-2960-04 @ui-ecms2 @burak ## will fail until CP-2960 fully implemented
  Scenario: Verify Default channels are selected after selecting default send to checkbox for consumers for multiple channels (Context Active Contact)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | mailDestination | random active mailing address |
    And I add another "Primary Individual" on the "previouslyCreated" case with a new "mailDestination" that will receive an Outbound Correspondence
    And I have a consumer on a case that wants to send an Outbound Correspondence but has changed his "emailDestination" contact information
    And I have a consumer on a case that wants to send an Outbound Correspondence but has changed his "faxDestination" contact information
    And I have a consumer on a case that wants to send an Outbound Correspondence but has changed his "textDestination" contact information
    And I initiate Case Correspondence API for created Case
    And I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I minimize Genesys popup if populates
    And I have selected an Outbound Correspondence through the UI "ALL CHANNELS - 000001" as type
    And I select default send to checkbox
    And I verify usable channels are selected for default consumers