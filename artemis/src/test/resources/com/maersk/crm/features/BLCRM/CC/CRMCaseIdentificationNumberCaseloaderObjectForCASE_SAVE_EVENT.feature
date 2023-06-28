Feature: Add caseIdentificationNumber object when case loader generates CASE_SAVE_EVENT

  @API-CP-11982 @API-CP-11982-01 @umid @events @events-cc
  Scenario: include caseIdentificationNumber object in the CASE_SAVE_EVENT when it is generated so that external case Ids can be sent to DPBI.
    Given I initiated Case Loader API for Create New Consumer for Digital Integration
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I set up test data for a new "" caseConsumer with "CM" created by caseloader with "", "", ""
    And I run the CaseLoader PUT API to Create New Consumer for Digital Integration
    When I get a case id and consumer id created from Consumer Search API
    When I get CaseIdentificationNumber load created from Consumer Search API
    When I search "case_save_event" endpoint for DBPI project "BLCRM"
    Then I verify case_save_event payload for the correct case identification object

  @CP-11982 @CP-11982-02 @umid @events @nj-regression @events-cc
  Scenario: include caseIdentificationNumber object in the CASE_SAVE_EVENT when it is generated so that external case Ids can be sent to DPBI.
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Johnny" and Last Name as "Cash"
    Then I link the contact to an existing Case
    When I get rawLogs for External Case ID with its case ID
    When I search "case_save_event" endpoint for DBPI project "NJ-SBE"
    Then I verify payload information
