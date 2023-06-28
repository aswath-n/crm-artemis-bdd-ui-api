
Feature: Email and Phone on Create Consumer Profile as Primary

  @CP-1852  @CP-1852-01 @ozgen @events @events-cc
  Scenario Outline: Email as Primary to Create a Consumer Profile
    Given I logged into CRM and click on initiate contact
    And I add new consumer to the record by choosing email as primary
    And I will take trace Id for Events and "<eventType>"
    When I initiate Event POST API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I provide the "<eventName>" and "<module>" and "<correlation>" in the body
    Then I will run the Event POST API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I verify that payload contains primaryIndicator paramater as true
    Examples:
      |eventName              |module         |eventType   |projectName|
      |EMAIL_SAVE_EVENT       |CONTACTS       |consumer    |[blank]|


  @CP-1852  @CP-1852-02 @ozgen @events @events-cc
    Scenario Outline: Phone as Primary to Create a Consumer Profile
      Given I logged into CRM and click on initiate contact
      And I add new consumer to the record by choosing phone as primary
      And I will take trace Id for Events and "<eventType>"
      When I initiate Event POST API
     When I will get the Authentication token for "<projectName>" in "CRM"
      And I provide the "<eventName>" and "<module>" and "<correlation>" in the body
      Then I will run the Event POST API and get the payload with "<eventName>" and "<module>" and "<correlation>"
      Then I verify that payload contains primaryIndicator paramater as true
      Examples:
        |eventName              |module         |eventType  |projectName|
        |PHONE_SAVE_EVENT       |CONTACTS       |consumer   |[blank]|