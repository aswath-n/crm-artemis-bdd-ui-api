Feature: View Outbound Correspondence Summary from Case/Consumer Profile


  @CP-9272 @CP-9272-1  @ui-ecms1 @kamil
  Scenario: Outbound Correspondence panel for Case
    Given I logged into CRM
    And I click case consumer search tab
    And I search for the "CaseId" with value "7347"
    And I search for the Outbound Correspondence "7347"
    When I navigate to the case and contact details tab
    And I select Outbound Correspondence by "Any"
    Then Verify Case Id "7347" for Regarding Case Id in Outbound Correspondence Details Page


  @CP-9272 @CP-9272-2  @ui-ecms1 @kamil
  Scenario:Outbound Correspondence panel for Consumer
    Given I logged into CRM
    And I click case consumer search tab
    And I search for the "ConsumerId" with value "17517"
    And I click the first consumer id from the search results
    When I navigate to the case and contact details tab
    And I select Outbound Correspondence by "Any"
    Then Verify Case Id "17517" for Regarding Consumer Id in Outbound Correspondence Details Page

