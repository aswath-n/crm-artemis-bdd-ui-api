@CP-11155
Feature: Back Arrow on Add/Edit Address, phone, email on case view

  @CP-11155 @CP-11155-01 @asad @crm-regression @ui-cc
  Scenario: Back arrow  - Add Address, phone, email
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab for contact and click on add for "address"
    Then I verify that I see back arrow beneath the consumer profile icon for contact
    And I click on demographic tab for contact and click on add for "phone"
    Then I verify that I see back arrow beneath the consumer profile icon for contact
    And I click on demographic tab for contact and click on add for "email"
    Then I verify that I see back arrow beneath the consumer profile icon for contact


  @CP-11155 @CP-11155-02.1 @asad @crm-regression @ui-cc
  Scenario: Back arrow  - Edit Address
    Given I logged into CRM
    When I will get the Authentication token for "" in "CRM"
    When I create a case for consumer of type "Primary Individual" using Case Loader API for contacts
    And I search consumer with first name and last name for contacts
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and add the "address" for the contacts from "initiate"
    And I click on newly created address to navigate edit address page
    Then I verify that I see back arrow beneath the consumer profile icon for contact


  @CP-11155 @CP-11155-02.2 @asad @crm-regression @ui-cc
  Scenario: Back arrow  - Edit Phone
    Given I logged into CRM
    When I will get the Authentication token for "" in "CRM"
    When I create a case for consumer of type "Primary Individual" using Case Loader API for contacts
    And I search consumer with first name and last name for contacts
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and add the "phone" for the contacts from "initiate"
    And I click on existing phone number
    Then I verify that I see back arrow beneath the consumer profile icon for contact

  @CP-11155 @CP-11155-02.3 @asad @crm-regression @ui-cc
  Scenario: Back arrow  - Edit Email
    Given I logged into CRM
    When I will get the Authentication token for "" in "CRM"
    When I create a case for consumer of type "Primary Individual" using Case Loader API for contacts
    And I search consumer with first name and last name for contacts
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and add the "email" for the contacts from "initiate"
    And I click on existing email
    Then I verify that I see back arrow beneath the consumer profile icon for contact


  @CP-11155 @CP-11155-03 @asad @crm-regression @ui-cc
  Scenario: Back arrow  - Add Address, phone, email outside active contact
    Given I logged into CRM
    When I will get the Authentication token for "" in "CRM"
    When I create a case for consumer of type "Primary Individual" using Case Loader API for contacts
    And I click case consumer search tab
    And I search consumer from consumer search with first name and last name for contacts
    And I click on demographic tab for contact and click on add for "address"
    Then I verify that I see back arrow beneath the consumer profile icon for contact
    And I click on demographic tab for contact and click on add for "phone"
    Then I verify that I see back arrow beneath the consumer profile icon for contact
    And I click on demographic tab for contact and click on add for "email"
    Then I verify that I see back arrow beneath the consumer profile icon for contact


  @CP-11155 @CP-11155-04.1 @asad @crm-regression @ui-cc #Fails due to CP-11797
  Scenario: Back arrow  - Edit Address outside active contact
    Given I logged into CRM
    When I will get the Authentication token for "" in "CRM"
    When I create a case for consumer of type "Primary Individual" using Case Loader API for contacts
    And I click case consumer search tab
    And I search consumer from consumer search with first name and last name for contacts
    And I click on demographic tab and add the "address" for the contacts from "search"
    And I click on newly created address to navigate edit address page
    Then I verify that I see back arrow beneath the consumer profile icon for contact


  @CP-11155 @CP-11155-04.2 @asad @crm-regression @ui-cc
  Scenario: Back arrow  - Edit Phone outside active contact
    Given I logged into CRM
    When I will get the Authentication token for "" in "CRM"
    When I create a case for consumer of type "Primary Individual" using Case Loader API for contacts
    And I click case consumer search tab
    And I search consumer from consumer search with first name and last name for contacts
    And I click on demographic tab and add the "phone" for the contacts from "search"
    And I click on existing phone number
    Then I verify that I see back arrow beneath the consumer profile icon for contact


  @CP-11155 @CP-11155-04.3 @asad @crm-regression @ui-cc
  Scenario: Back arrow  - Edit Email outside active contact
    Given I logged into CRM
    When I will get the Authentication token for "" in "CRM"
    When I create a case for consumer of type "Primary Individual" using Case Loader API for contacts
    And I click case consumer search tab
    And I search consumer from consumer search with first name and last name for contacts
    And I click on demographic tab and add the "email" for the contacts from "search"
    And I click on existing email
    Then I verify that I see back arrow beneath the consumer profile icon for contact
