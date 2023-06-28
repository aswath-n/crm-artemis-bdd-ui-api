Feature: Non-Editable External IDs Enhancements

  @CP-28045 @CP-28045-01 @Beka @crm-regression @ui-cc
  Scenario: Verify external ID in consumer profile is not editable
    Given I logged into CRM
    And I click on initiate a contact button
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on edit button
    And I add "CHIP and MADICAID" external ID
    And I click on edit button
    Then I verify external ID on edit consumer page is NOT editable

  @CP-28045 @CP-28045-02 @CP-17043 @CP-17043-01 @Beka @crm-regression @ui-cc
  Scenario: Verify external ID in consumer profile not have a little trash bucket
    Given I logged into CRM
    And I click on initiate a contact button
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on edit button
    And I add "CHIP and MADICAID" external ID
    And I click on edit button
    Then I verify external ID on edit consumer page not have a little trash bucket

  @CP-28045 @CP-28045-03 @Beka @crm-regression @ui-cc
  Scenario: Verify external ID in consumer on a case is not editable
    Given I logged into CRM
    And I click on initiate a contact button
    When I searched customer have First Name as "Moonica" and Last Name as "Bolo"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on first Primary Individual
    Then I verify external ID on edit consumer page is NOT editable

  @CP-28045 @CP-28045-04 @CP-17043 @CP-17043-02 @Beka @crm-regression @ui-cc
  Scenario: Verify external ID in consumer on a case not have a little trash bucket
    Given I logged into CRM
    And I click on initiate a contact button
    When I searched customer have First Name as "Moonica" and Last Name as "Bolo"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on first Primary Individual
    Then I verify external ID on edit consumer page not have a little trash bucket

  @CP-28045 @CP-28045-05 @Beka @crm-regression @ui-cc
  Scenario: Verify external ID in case member page is not editable
    Given I logged into CRM
    And I click on initiate a contact button
    When I searched customer have First Name as "Moonica" and Last Name as "Bolo"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And click on existing case
    Then I verify external ID on edit consumer page is NOT editable

  @CP-28045 @CP-28045-06 @CP-17043 @CP-17043-03 @Beka @crm-regression @ui-cc
  Scenario: Verify external ID in case member page not have a little trash bucket
    Given I logged into CRM
    And I click on initiate a contact button
    When I searched customer have First Name as "Moonica" and Last Name as "Bolo"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on first Primary Individual
    Then I verify external ID on edit consumer page not have a little trash bucket