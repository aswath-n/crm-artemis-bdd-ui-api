@CP-3254
Feature: Store Outbound Correspondence Object into ECMS on request

  @CP-3254 @CP-3254-1.0 @asad @API-ECMS
  Scenario: API Store Outbound Correspondence Object
    Given I initiated outbound Correspondence save API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create outbound correspondence save request with "correct" request details and run it
    Then I validate response that outbound correspondence is successfully saved

  @CP-3254 @CP-3254-2.0 @asad @API-ECMS
  Scenario: Convert Request to OnBase API and Submit for Storage
    Given I initiated outbound Correspondence save API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create outbound correspondence save request with "correct" request details and run it
    Then I validate response that outbound correspondence is successfully saved
    Then I also validate the outbound correspondence created details successfully

  @CP-3254 @CP-3254-3.0 @asad @API-ECMS
  Scenario: Return Success Response
    Given I initiated outbound Correspondence save API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create outbound correspondence save request with "correct" request details and run it
    Then I validate response that outbound correspondence is successfully saved and documentId is returned

  @CP-3254 @CP-3254-4.0 @asad @API-ECMS
  Scenario: Return Error Response
    Given I initiated outbound Correspondence save API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create outbound correspondence save request with "incorrect" request details and run it
    Then I validate that 400 response is received for outbound correspondence