Feature: Business Events: New Address & Phone, Address & Phone Change

  @CP-29183 @CP-29183-01 @muhabbat @events-cc
  Scenario: Contact Info Business Events New Address for consumer not on case
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I click on the Demographic Info Tab
    When I click on Add new address button on Contact Info Tab Page
    And I edit all required fields along with "past" as "future"
    And I click on Save button on Edit Address Page
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve the "NEW_ADDRESS" business event that is produced by trace id
    Then I should see the payload for the business "NEW_ADDRESS" is as expected


  @CP-29183 @CP-29183-02 @muhabbat @events-cc
  Scenario: Contact Info Business Events New Phone for consumer not on case
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I click on the Demographic Info Tab
    When I add new phone to the searched consumer for event check
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve the "NEW_PHONE" business event that is produced by trace id
    Then I should see the payload for the business "NEW_PHONE" is as expected


  @CP-29183 @CP-29183-03 @muhabbat @events-cc
  Scenario: Contact Info Business Events New Address for case consumer
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for communication PreferencesID check with "Primary Individual"
    When I click case consumer search tab
    And I search consumer with first name and last name for Communication preference from consumer profile for Id check for "Case Loader"
    And I navigate to Contact Info Tab
    When I click on Add new address button on Contact Info Tab Page
    And I edit all required fields along with "past" as "future"
    And I select a consumer from dropdown on edit contact info page
    And I click on Save button on Edit Address Page
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve the "NEW_ADDRESS" business event that is produced by trace id
    Then I should see the payload for the business "NEW_ADDRESS" is as expected


  @CP-29183 @CP-29183-04 @muhabbat @events-cc
  Scenario: Contact Info Business Events New Phone for caseconsumer
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for communication PreferencesID check with "Primary Individual"
    When I click case consumer search tab
    And I search consumer with first name and last name for Communication preference from consumer profile for Id check for "Case Loader"
    And I navigate to Contact Info Tab
    When I add new phone to the case consumer for event check
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve the "NEW_PHONE" business event that is produced by trace id
    Then I should see the payload for the business "NEW_PHONE" is as expected

  @CP-29182 @CP-29182-01 @muhabbat @events-cc
  Scenario: Contact Info Business Events Address Change for consumer not on case
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I click on the Demographic Info Tab
    When I click on newly created address to navigate edit address page
    And I edit all required fields along with "past" as ""
    And I click on Save button on Edit Address Page
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve the "ADDRESS_CHANGE" business event that is produced by trace id
    Then I should see the payload for the business "ADDRESS_CHANGE" is as expected


  @CP-29182 @CP-29182-02 @muhabbat @events-cc
  Scenario: Contact Info Business Events Phone Change for consumer not on case
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I click on the Demographic Info Tab
    And I expend current phone number record and navigated to Edit Phone Number Page
    And I edit expanded for Phone number and save changes
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve the "PHONE_CHANGE" business event that is produced by trace id
    Then I should see the payload for the business "PHONE_CHANGE" is as expected


  @CP-29182 @CP-29182-03 @muhabbat @events-cc
  Scenario: Contact Info Business Events Address Change for case consumer
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for communication PreferencesID check with "Primary Individual"
    When I click case consumer search tab
    And I search consumer with first name and last name for Communication preference from consumer profile for Id check for "Case Loader"
    And I navigate to Contact Info Tab
    When I click on newly created address to navigate edit address page
    And I edit all required fields along with "past" as ""
    And I select a consumer from dropdown on edit contact info page
    And I click on Save button on Edit Address Page
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve the "ADDRESS_CHANGE" business event that is produced by trace id
    Then I should see the payload for the business "ADDRESS_CHANGE" is as expected

  @CP-29182 @CP-29182-04 @muhabbat @events-cc
  Scenario: Contact Info Business Events Phone Change for caseconsumer
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for communication PreferencesID check with "Primary Individual"
    When I click case consumer search tab
    And I search consumer with first name and last name for Communication preference from consumer profile for Id check for "Case Loader"
    And I navigate to Contact Info Tab
    And I expend current phone number record and navigated to Edit Phone Number Page
    And I select a consumer from dropdown on edit contact info page
    And I edit expanded for Phone number and save changes
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve the "PHONE_CHANGE" business event that is produced by trace id
    Then I should see the payload for the business "PHONE_CHANGE" is as expected

