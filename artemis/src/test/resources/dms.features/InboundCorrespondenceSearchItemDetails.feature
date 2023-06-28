Feature: View inbound correspondence item details from global search

  @CP-8955 @ui-ecms2 @Sang @CP-8955-01
  Scenario: Verify each CID element is a hyperlink
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CASEID | 4945 |
    Then all the CID elements are hyperlinks

  @CP-8955 @ui-ecms2 @Sang @CP-8955-02
  Scenario: Verify each CID element takes you to the Inbound Correspondence Details screen
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CASEID | 68 |
    When I select the Inbound Document CID link "firstAvailable" on CID list
    Then I should be navigated to the Inbound Correspondence Details page for that Document

  @CP-8955 @ui-ecms2 @Sang @CP-8955-03
  Scenario: Verify that after viewing Inbound Correspondence Details that I can return to the results where I left off
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CASEID | 60690 |
    And I get a list of the Inbound Correspondence Search CIDs
    When I select the Inbound Document CID link "firstAvailable" on CID list
    And I navigate back to the Inbound Correspondence Search results
    Then I should see that I am in the same place in the search results where I left off

  @CP-8955 @ui-ecms2 @Sang @CP-8955-04
  Scenario: Verify that after viewing Inbound Correspondence Details that I can return to the results where I left off after a second search
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CASEID | 60690 |
    And I get a list of the Inbound Correspondence Search CIDs
    And I select the Inbound Document CID link "firstAvailable" on CID list
    And I navigate back to the Inbound Correspondence Search results
    And I select the Inbound Document CID link "LastAvailable" on CID list
    And I navigate back to the Inbound Correspondence Search results
    Then I should see that I am in the same place in the search results where I left off