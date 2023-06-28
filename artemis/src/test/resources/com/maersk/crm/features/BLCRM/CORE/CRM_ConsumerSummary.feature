Feature: Consumer Summary

  @CP-769 @CP-769-01 @asad @crm-regression @ui-core
  Scenario: View User Name
    Given I logged into CRM
    When I create consumer for Contact Record Consumer View
    When I click contact search tab for Contact Record Consumer View
    And I search and view results for Contact Record Consumer View
    Then I am able to select either the Case ID or Consumer ID hyperlink to view the Summary Tab information for the associated Case or Consumer Profile

  @CP-769 @CP-769-02 @asad @crm-regression @ui-core
  Scenario: View User Name
    Given I logged into CRM
    When I create consumer for Contact Record Consumer View
    When I click contact search tab for Contact Record Consumer View
    And I search and view results for Contact Record Consumer View
    Then I will be able to navigate back to the Contact Record Details from consumer profile viewing