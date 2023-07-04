Feature: CoverVA: Configure Contact Record Authentication

  @CP-17211 @CP-17211-01 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @ui-core-smoke
  Scenario: Update Cover VA Authentication Checkboxes
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I searched customer have First Name as "Aika" and Last Name as "Begi"
    And I expand the Case Consumer this contact relates to in search result
    Then I verify system displays the Home Address, DOB and SSN with Authentication checkboxes

  @CP-17211 @CP-17211-02 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: Business Rules for Authentication Boxes
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I searched customer have First Name as "Aika" and Last Name as "Begi"
    And I expand the Case Consumer this contact relates to in search result
    Then I verify display of 'Consumer Authenticated' message by selecting two checkboxes for CoverVA

  @CP-9483 @CP-9483-02 @CP-6042 @CP-6042-02 @moldir @ui-core-cover-va @crm-regression #Fails due to CP-48272
  Scenario Outline: Validate CONSUMER_AUTHENTICATION_SAVE_EVENT is generated and sent to DPBI when Consumer is successfully authenticated in CoverVA
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing CaseConsumer in "<projectName>"
    And Wait for 5 seconds
    When I navigate to the case and contact details tab
    And I will save contact record id from CASE & CONTACT DETAILS tab Contact History
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I will verify CONSUMER_AUTHENTICATION_SAVE_EVENT response
    And Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      |eventName                          | module         | eventType      | projectName |
      |CONSUMER_AUTHENTICATION_SAVE_EVENT | CONTACT_RECORD | authentication | CoverVA     |
