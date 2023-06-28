Feature: CRM-Case Member Correspondence Preference Events

  @CP-275 @CP-275-4.0 @ozgen @events @CP-11786 @CP-11786-02 @events-cc
  Scenario Outline:Verification of Consumer's Correspondence Preference with Case Member Update Event
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Michaella" and Last Name as "Pindiv"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    When I click on existing Authorized Representative Record
    And I populate consumer's correspondence preference as "Paperless" from multi select
    Then I verify consumer's updated correspondence preference
    And I click on Save button for Authorized Representative
    And I click on continue button on warning message
    And I will take trace id for case consumer events "<eventType>"
    When I initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I will verify that payload contains event details
    And I will verify that payload has correspondence preference details based on "update"
    And I will initiate subscribers POST API
    Then I will provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I check the response has event Subscriber Mapping ID for "<eventName>"
    And I will initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName             | module   | eventType  | projectName |
      | CONSUMER_UPDATE_EVENT | CONSUMER | casemember |[blank]|


  @CP-275 @CP-275-5.0 @ozgen @events @CP-11786 @CP-11786-03 @events-cc
  Scenario Outline:Verification of Consumer's Correspondence Preference with Case Member Save Event
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Michaella" and Last Name as "Bennet"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I click on the Add button for Authorized Representative
    And I populate Authorized Representative field for new record
    And I populate consumer's correspondence preference as "Paperless" from multi select
    Then I verify consumer's updated correspondence preference
    And I click on Save button for Authorized Representative
    And I click on continue button on warning message
    And I will take trace id for case consumer events "<eventType>"
    When I initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I will verify that payload contains event details
    And I will verify that payload has correspondence preference details based on "save"
    And I will initiate subscribers POST API
    Then I will provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I check the response has event Subscriber Mapping ID for "<eventName>"
    And I will initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName           | module   | eventType  | projectName |
      | CONSUMER_SAVE_EVENT | CONSUMER | casemember |[blank]|

  @CP-3336 @CP-3336-01 @ozgen @events @events-smoke @events-cc
  Scenario Outline:Verification of NOT have contact objects in Consumer Save Event
    Given I logged into CRM and click on initiate contact
    Then I add New Consumer to the record
    And I will take trace id for case consumer events "<eventType>"
    When I initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>" for Consumer Save Event
    Then I will verify that payload contains event details
    And I will verify that payload doesnt have contact objects
    And I will initiate subscribers POST API
    Then I will provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I check the response has event Subscriber Mapping ID for "<eventName>"
    And I will initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName           | module   | eventType | projectName |
      | CONSUMER_SAVE_EVENT | CONSUMER | consumer  |[blank]|

  @CP-3336 @CP-3336-02 @ozgen @events @events-cc
  Scenario Outline:Verification of NOT have contact objects in Consumer Update Event
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields for previously created consumer
    Then I click on edit button on Profile Contact Page
    And I provide ssn number and DOB to edit consumer profile
    Then I click on save button on Edit Consumer Profile page
    And I will take trace id for consumer update events "<eventType>"
    When I initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I will verify that payload contains event details
    And I will verify that payload doesnt have contact objects
    And I will initiate subscribers POST API
    Then I will provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I check the response has event Subscriber Mapping ID for "<eventName>"
    And I will initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName             | module   | eventType | projectName |
      | CONSUMER_UPDATE_EVENT | CONSUMER | consumer  |[blank]|


  @CP-3336 @CP-3336-03 @ozgen @events @CP-11786 @CP-11786-04 @events-cc
  Scenario Outline:Verification of NOT have contact objects in Case Member Save Event for AR
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Lisa" and Last Name as "Watson"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I click on the Add button for Authorized Representative
    And I populate Authorized Representative field for new record
    And I click on Save button for Authorized Representative
    And I click on continue button on warning message
    And I will take trace id for case consumer events "<eventType>"
    When I initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I will verify that payload contains event details
    And I will verify that payload contains consumer role details based on "Authorized Representative"
    And I will verify that payload doesnt have contact objects
    And I will initiate subscribers POST API
    Then I will provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I check the response has event Subscriber Mapping ID for "<eventName>"
    And I will initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName           | module   | eventType  | projectName |
      | CONSUMER_SAVE_EVENT | CONSUMER | casemember |[blank]|

  @CP-3336 @CP-3336-04 @ozgen @events @CP-11786 @CP-11786-05 @events-cc
  Scenario Outline:Verification of NOT have contact objects in Case Member Update Event for AR
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Linda" and Last Name as "Watson"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    When I click on existing Authorized Representative Record
    And I update authorized representative's middle name
    And I click on Save button for Authorized Representative
    And I click on continue button on warning message
    And I will take trace id for case consumer events "<eventType>"
    When I initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I will verify that payload contains event details
    And I will verify that payload contains consumer role details based on "Authorized Representative"
    And I will verify that payload doesnt have contact objects
    And I will initiate subscribers POST API
    Then I will provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I check the response has event Subscriber Mapping ID for "<eventName>"
    And I will initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName             | module   | eventType  | projectName |
      | CONSUMER_UPDATE_EVENT | CONSUMER | casemember |[blank]|

  @CP-3336 @CP-3336-05 @ozgen @events @CP-11786 @CP-11786-06 @events-cc
  Scenario Outline:Verification of NOT have contact objects in Case Member Save Event for PI
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Lisa" and Last Name as "Watson"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    When I click on the Add button for primary individual
    And I populate all mandatory details of primary individual
    And I click on Save button on add primary individual page
    And I click on continue button on warning message
    And I will take trace id for case consumer events "<eventType>"
    When I initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I will verify that payload contains event details
    And I will verify that payload contains consumer role details based on "Primary Individual"
    And I will verify that payload doesnt have contact objects
    And I will initiate subscribers POST API
    Then I will provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I check the response has event Subscriber Mapping ID for "<eventName>"
    And I will initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName           | module   | eventType  | projectName |
      | CONSUMER_SAVE_EVENT | CONSUMER | casemember |[blank]|

  @CP-3336 @CP-3336-06 @ozgen @events @CP-11786 @CP-11786-07 @events-cc
  Scenario Outline:Verification of NOT have contact objects in Case Member Update Event for PI
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Linda" and Last Name as "Watson"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on existing Primary Individual Record
    And I update primary individual's middle name
    Then I click on Save button on add primary individual page
    And I click on continue button on warning message
    And I will take trace id for case consumer events "<eventType>"
    When I initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I will verify that payload contains event details
    And I will verify that payload contains consumer role details based on "Primary Individual"
    And I will verify that payload doesnt have contact objects
    And I will initiate subscribers POST API
    Then I will provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I check the response has event Subscriber Mapping ID for "<eventName>"
    And I will initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName             | module   | eventType  | projectName |
      | CONSUMER_UPDATE_EVENT | CONSUMER | casemember |[blank]|

  @CP-3336 @CP-3336-07 @ozgen @events @CP-11786 @CP-11786-08 @events-cc
  Scenario Outline:Verification of NOT have contact objects in Case Member Save Event for CM
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Lisa" and Last Name as "Watson"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on Add Button for Case Member
    When I enter the mandatory fields details "<FirstName>", "<LastName>", "<DOB>", "<Gender>", "<StartDate>","<EndDate>" "<Language>","<Relation>","<SSN>"
    And I click on continue button on warning message
    And I will take trace id for case consumer events "<eventType>"
    When I initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I will verify that payload contains event details
    And I will verify that payload contains consumer role details based on "Member"
    And I will verify that payload doesnt have contact objects
    And I will initiate subscribers POST API
    Then I will provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I check the response has event Subscriber Mapping ID for "<eventName>"
    And I will initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName           | module   | eventType  | projectName | FirstName | LastName | DOB        | Gender | StartDate | EndDate | Language | Relation | SSN         | status |
      | CONSUMER_SAVE_EVENT | CONSUMER | casemember |[blank]| {random}  | {random} | 01/01/2012 | Male   | past      | past    | Spanish  | Child    | 987-65-4321 | Active |


  @CP-3336 @CP-3336-08 @ozgen @events @CP-11786 @CP-11786-09 @events-cc
  Scenario Outline:Verification of NOT have contact objects in Case Member Update Event for CM
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Lisa" and Last Name as "Watson"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on any existing Case Member
    And I update case member's middle name
    And I click on Save button for Case Member
    And I click on continue button on warning message
    And I will take trace id for case consumer events "<eventType>"
    When I initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I will verify that payload contains event details
    And I will verify that payload contains consumer role details based on "Member"
    And I will verify that payload doesnt have contact objects
    And I will initiate subscribers POST API
    Then I will provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I check the response has event Subscriber Mapping ID for "<eventName>"
    And I will initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName             | module   | eventType  | projectName |
      | CONSUMER_UPDATE_EVENT | CONSUMER | casemember |[blank]|

