Feature: IN-EB Case Member and Primary Individual Configurations

  @CP-33874 @CP-33874-01 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Display Medicaid/RID as link to View PI and View CM
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I searched customer have First Name as "Larouge" and Last Name as "Larouge"
    And I click on the case Id that I previously created
    And I select the Demographic Info tab on the Consumer profile
    Then I see "PI" external ID value "fghfzdx44767" is displayed
    And I click on first consumerID for Primary Individual
    And I click on Save button on add primary individual page

  @CP-33874 @CP-33874-02 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Display Medicaid/RID as link to View CM
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I searched customer have First Name as "BRITTANY" and Last Name as "ANHOJNAHTA"
    And I click on the case Id that I previously created
    And I select the Demographic Info tab on the Consumer profile
    Then I see "CM" external ID value "365557306144" is displayed
    When I click on any existing Case Member
    And I click on Save button on add primary individual page

