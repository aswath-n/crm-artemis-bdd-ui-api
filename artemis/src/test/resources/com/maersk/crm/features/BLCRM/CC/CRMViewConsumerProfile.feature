Feature: View Consumer Profile Feature

  @CP-343  @CP-343-01 @asad @crm-regression @ui-cc
  Scenario: View Consumer Profile Information
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I navigate to Case Consumer Search Page
    And I search for consumer profile summary information for viewing consumer profile
    Then I will be able to view the Consumer Information for the Consumer Profile I have selected for viewing consumer profile

  @CP-343 @CP-343-02 @CP-4747 @CP-23126-01 @asad @crm-regression @ui-cc
  Scenario: View Consumer Information
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer  and select single "Paperless" option for ""
    When I click on Create Consumer Button
    When I navigate to Case Consumer Search Page
    And I search for consumer profile summary information for viewing consumer profile
    And I navigate to Consumer profile summary information for viewing consumer profile
    Then I see "consumerID" field on consumer profile page has 30 characters in total for consumer profile
    Then I see "consumerFirstName" field on consumer profile page has 50 characters in total for consumer profile
    Then I see "consumerLastName" field on consumer profile page has 50 characters in total for consumer profile
    Then I see "consumerAge" field on consumer profile page has 2 characters in total for consumer profile
    Then I see gender for consumer profile
    Then I see Date of Birth in "**/**/****" format
    Then I see SSN 9 digits value for consumer profile
    Then I see preferred spoken and written languages for consumer profile
    Then I see Consumer Start Date and End Date format for consumer profile
    Then I see Correspondence Preferences for consumer profile
    Then I see profile status for consumer profile

  @CP-440 @CP-440-01 @muhabbat @crm-regression @crm-smoke @ui-cc
  Scenario: Case Profile Icon - Viewing Case Id next to Case Icon within an Active Contact
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Lola" and Last Name as "Zcxc"
    And I link the contact to an existing Case or Consumer Profile
    Then I verify Case Id next to Case Icon
    Then I see Consumer Name is next to Case Icon within an Active Contact

  @CP-440 @CP-440-02 @muhabbat @crm-regression @ui-cc
  Scenario: Case Profile Icon - Visual indication of viewing a Case Information within an Active Contact
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Lola" and Last Name as "Zcxc"
    And I link the contact to an existing Case or Consumer Profile
    Then I verify visual indication of viewing a Case Information

  @CP-440 @CP-440-03 @muhabbat @crm-regression @ui-cc
  Scenario: Case Profile Icon - Visual indication of viewing a hover over and Title property text for Case Information within an Active Contact
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Lola" and Last Name as "Zcxc"
    And I link the contact to an existing Case or Consumer Profile
    Then I hover over the Case Icon and see its text and Title property has "Case Profile View" value

  @CP-440 @CP-440-04 @muhabbat @crm-regression @ui-cc
  Scenario: Case Profile Icon - Visual indication of viewing a Case Information outside of an Active Contact
    Given I logged into CRM
    When I click case consumer search tab
    When I searched customer have First Name as "Lola" and Last Name as "Zcxc"
    And I expand the record to navigate Case Consumer Record from manual view
    Then I verify visual indication of viewing a Case Information

  @CP-440 @CP-440-05 @muhabbat @crm-regression @ui-cc
  Scenario: Case Profile Icon - Visual indication of viewing a hover over and Title property text for Case Information outside of an Active Contact
    Given I logged into CRM
    When I click case consumer search tab
    When I searched customer have First Name as "Lola" and Last Name as "Zcxc"
    And I expand the record to navigate Case Consumer Record from manual view
    Then I hover over the Case Icon and see its text and Title property has "Case Profile View" value

  @CP-440 @CP-440-06 @muhabbat @crm-regression @ui-cc
  Scenario: Case Profile Icon - Viewing Case Id next to Case Icon outside of an Active Contact
    Given I logged into CRM
    When I click case consumer search tab
    When I searched customer have First Name as "Lola" and Last Name as "Zcxc"
    And I expand the record to navigate Case Consumer Record from manual view
    Then I verify Case Id next to Case Icon