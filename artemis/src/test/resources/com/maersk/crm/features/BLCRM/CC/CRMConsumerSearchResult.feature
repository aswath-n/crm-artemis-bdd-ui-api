@CP-336
Feature: Consumer Search Result

  @CP-336 @CP-336-01.0 @asad @crm-regression @ui-cc
  Scenario Outline: Case Summary - Case ID & Consumer ID Links
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case using Case Loader API
    And I click case consumer search tab
    And I search for consumer associated to a Case
    Then I see Case ID link is enabled and the Consumer ID link is disabled
    Examples:
      |projectName|
      |[blank]|

  @CP-336 @CP-336-01.1 @asad @crm-regression @ui-cc
  Scenario Outline: Navigate to Case Summary
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case using Case Loader API
    And I click case consumer search tab
    And I search and select consumer associated to a Case
    Then I verify consumer and case data for Consumer search
    Examples:
      |projectName|
      |[blank]|

  @CP-336 @CP-336-01.2 @asad @crm-regression @ui-cc
  Scenario Outline: Navigate Back to Search Results for case loader
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case using Case Loader API
    And I click case consumer search tab
    And I search and select consumer associated to a Case
    Then I select the Back Arrow on the top left and navigated back to Consumer Search for "case loader"
    Examples:
      |projectName|
      |[blank]|

  @CP-336 @CP-336-02.0 @asad @crm-regression @ui-cc
  Scenario: Consumer Profile - Case ID & Consumer ID Links
    Given I logged into CRM
    When I create a consumer for case consumer link check
    And I click case consumer search tab
    And I search for consumer not associated to a Case
    Then I see the Consumer ID link is enabled and NO Case ID link

  @CP-336 @CP-336-02.1 @asad @crm-regression @ui-cc
  Scenario: Navigate to Consumer Profile
    Given I logged into CRM
    When I create a consumer for case consumer link check
    And I click case consumer search tab
    And I search and select consumer not associated to a Case
    Then I verify consumer for Consumer search

  @CP-336 @CP-336-02.2 @asad @crm-regression @ui-cc
  Scenario: Navigate Back to Search Results for create consumer
    Given I logged into CRM
    When I create a consumer for case consumer link check
    And I click case consumer search tab
    And I search and select consumer not associated to a Case
    Then I select the Back Arrow on the top left and navigated back to Consumer Search for "create consumer"

  @CP-25849 @CP-25849-1 @chopa @crm-regression @ui-cc
  Scenario: Verify warning message for results that exceeds 100 records
    Given I logged into CRM
    When I click on initiate contact record
    When I search consumers by DOB
    Then I will see warning message results exceed 100 records

  @CP-25849 @CP-25849-2 @chopa @crm-regression @ui-cc
  Scenario: Verify default number of search results
    Given I logged into CRM
    When I click on initiate contact record
    When I search consumers by DOB
    Then I verify 20 records listed in each page of results by default

  @CP-25849 @CP-25849-3 @chopa @crm-regression @ui-cc
  Scenario: Verify option to manually increase display of results
    Given I logged into CRM
    When I click on initiate contact record
    When I search consumers by DOB
    Then I have the option to manually increase my display of results per page to "50"
    Then I verify 50 records listed in each page of results by default