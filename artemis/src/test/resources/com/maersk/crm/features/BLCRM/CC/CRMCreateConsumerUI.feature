@CP-11148
Feature: Manual Case/Consumer Profile Search for Design Changes

  @CP-11148 @CP-11148-01 @asad @crm-regression @ui-cc
  Scenario: Design changes
    Given I logged into CRM and click on initiate contact
    When I search for a consumer and to click on Add Consumer button
    Then I see contact reasons and contact actions
    Then I see create consumer back arrow and active contact header