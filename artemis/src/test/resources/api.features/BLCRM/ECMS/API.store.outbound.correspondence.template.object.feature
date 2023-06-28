@CP-3255
Feature: Store Outbound Correspondence Template Object into ECMS on request

  @CP-3255 @CP-3255-1.0 @asad @API-ECMS
  Scenario: API Store Outbound Correspondence Template Object
    Given I initiated Outbound Correspondence Template save API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create Outbound Correspondence Template save request with "correct" request details and run it
    Then I validate response that Outbound Correspondence Template is successfully saved

  @CP-3255 @CP-3255-2.0 @asad @API-ECMS
  Scenario: Convert Request to OnBase API and Submit for Storage
    Given I initiated Outbound Correspondence Template save API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create Outbound Correspondence Template save request with "correct" request details and run it
    Then I validate response that Outbound Correspondence Template is successfully saved
    Then I also validate the Outbound Correspondence Template created details successfully

  @CP-3255 @CP-3255-3.0 @asad @API-ECMS
  Scenario: Return Success Response
    Given I initiated Outbound Correspondence Template save API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create Outbound Correspondence Template save request with "correct" request details and run it
    Then I validate response that Outbound Correspondence Template is successfully saved and documentId is returned

  @CP-3255 @CP-3255-4.0 @asad @API-ECMS
  Scenario: Return Error Response
    Given I initiated Outbound Correspondence Template save API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create Outbound Correspondence Template save request with "incorrect" request details and run it
    Then I validate that 400 response is received for outbound correspondence template