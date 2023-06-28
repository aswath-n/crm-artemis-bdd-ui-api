@CP-4639
@umid

Feature: include inbound document type value for each document or section of document sent for document assembly

  @API-ECMS @CP-4639 @CP-4639-01
  Scenario: Verify Inbound Type defined in the Outbound type is included in data object
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random   |
      | type   | formTest |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the Inbound Type defined in the Outbound type is included in data object

