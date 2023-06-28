Feature: Prevent UnLink Contact

  @CP-1159 @CP-1159-01 @asad @crm-regression @ui-core
  Scenario: Button Unavailability in active contact record screen
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Harry" and Last Name as "Grant"
    And I link the contact to an existing Case or Consumer Profile
    And I create a general task for consumer
    Then I will no longer see the Unlink button available

  @CP-1159 @CP-1159-02 @asad @crm-regression @ui-core
  Scenario: Button Unavailability in Case & Contact Details tab of Active contact record screen
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Harry" and Last Name as "Grant"
    And I link the contact to an existing Case or Consumer Profile
    And I create a Inbound document for consumer
    Then I will no longer see the Unlink button available

  @CP-1159 @CP-1159-03 @asad @crm-regression @ui-core
  Scenario: Button Unavailability in contact record edit page
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Harry" and Last Name as "Grant"
    And I link the contact to an existing Case or Consumer Profile
    And I create a outbound correspondence for consumer
    Then I will no longer see the Unlink button available

  @CP-1159 @CP-1159-02 @asad @functionality-needed #Removed regression tag since functionality is not developed yet
  Scenario: Button Unavailability in Case & Contact Details tab of Active contact record screen
    Given I logged into CRM
    When I create a case for consumer using Case Loader API
    And I searched for consumer with first name and last name
    And I link the contact to an Consumer Profile
    And I create a service request for consumer
    Then I will no longer see the Unlink button available

  @CP-17078 @CP-17078-2 @crm-regression @ui-core @khazar
  Scenario: Verify unlink button is not displayed when editing contact record which is linked to outbound correspondence
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "IaRavDoxBc" and Last Name as "sDPUbUMzIX"
    And I link the contact to an existing Case or Consumer Profile
    And I have selected an Outbound Correspondence through the UI "testingfinal1 - testingfinal1" as type
    Then I fill mandatory fields and save correspondence
    When I navigate to active contact tab on dashboard
    And I click on the Inbound Call queue "Claims"
    And I enter contact phone number "3332223311"
    Then I select "Medicaid" as contact program type
    Then I click on the contact dispotions "Complete"
    When I click End Contact
    And I choose a contact reason from reason dropdown list as "Enrollment"
    And  I choose "Plan Change" option for Contact Action field
    And I click save button for reason and action
    And I enter Call Summary as "First call summary"
    Then I click on save Call Summary
    And I click save button Active contact
    Then I navigate to Contact Record Search Page
    And I search for contact record by contact firstName "IaRavDoxBc" and lastName "sDPUbUMzIX"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I wont see the Unlink button