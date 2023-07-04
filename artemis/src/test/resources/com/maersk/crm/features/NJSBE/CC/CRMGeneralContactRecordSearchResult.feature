@CP-11341
Feature: NJ Process GetInsured case/consumer API response - General Contact Record - Display Search Results

  @CP-11341 @CP-11341-01.0 @asad @ui-cc-nj @crm-regression @NJ-UI-Regression
  Scenario: Exclude Brokers, Navigators, Assisters in General Search Results expanded view
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Jennifer" and Last Name as "Holden"
    And I expand the Case Consumer this contact relates to in search result
    Then I verify only primary individual and case member is displayed in the search result

  @CP-11341 @CP-11341-01.1 @asad @ui-cc-nj @crm-regression @NJ-UI-Regression
  Scenario: Exclude Brokers, Navigators, Assisters in General Search Results expanded view
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Jennifer" and Last Name as "Holden"
    And I expand the Case Consumer this contact relates to in search result
    Then I verify only primary individual and case member is displayed in the search result

  @CP-31437 @CP-31437-1 @crm-regression @ui-cc-nj @nj-regression @Beka
  Scenario: verify Case ID hyperlink is disabled and not clickable J-SBE
    Given I logged into CRM and select a project "NJ-SBE"
    And I navigate to Manual Consumer search page
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on the first case and consumer search result
    Then I will verify "Case ID" hyperlink is disabled and not clickable