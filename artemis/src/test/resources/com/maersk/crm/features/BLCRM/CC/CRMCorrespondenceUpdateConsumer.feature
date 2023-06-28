@CP-278
Feature: Capture Correspondence Preferences from Update Consumer Profile Info - Consumer Profile View

  @CP-278 @CP-278-01.0 @asad @crm-regression @ui-cc
  Scenario: View Correspondence Preference Field
    Given I logged into CRM
    When I create a consumer for Correspondence preference from consumer profile
    When I click case consumer search tab
    And I search consumer with first name and last name for Correspondence preference from consumer profile
    Then I verify that Consumers Correspondence Preference is "Paperless" in consumer profile

  @CP-278 @CP-278-01.1 @asad @crm-regression @ui-cc
  Scenario: Edit Correspondence Preference Field
    Given I logged into CRM
    When I create a consumer for Correspondence preference from consumer profile
    When I click case consumer search tab
    And I search consumer with first name and last name for Correspondence preference from consumer profile
    And I edit the correspondence preferences from consumer profile
    Then I verify that Consumers Correspondence Preference is "" in consumer profile

  @CP-278 @CP-278-01.2 @asad @crm-regression @ui-cc
  Scenario: Baseline Dropdown Option
    Given I logged into CRM
    When I create a consumer for Correspondence preference from consumer profile
    When I click case consumer search tab
    And I search consumer with first name and last name for Correspondence preference from consumer profile
    And I edit to view correspondence preferences from consumer profile
    Then I verify Consumers Correspondence Preference baseline select dropdown option for consumer profile

  @CP-278 @CP-278-02.0 @asad @crm-regression @ui-cc
  Scenario: Multi-Select
    Given I logged into CRM
    When I create a consumer for Correspondence preference from consumer profile
    When I click case consumer search tab
    And I search consumer with first name and last name for Correspondence preference from consumer profile
    Then I verify Consumers Correspondence Preference dropdown option is multi select from editing consumer profile

