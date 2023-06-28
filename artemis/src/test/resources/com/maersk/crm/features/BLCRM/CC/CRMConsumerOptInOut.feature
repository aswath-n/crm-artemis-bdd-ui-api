Feature: Capture Consumer Opt-In/Out

  @CP-268 @CP-268-01 @asad @crm-regression @ui-cc
  Scenario: Opt-in/out Check Boxes
    Given I logged into CRM and click on initiate contact
    When I search for a consumer and able to click on Add Consumer button
    When I verify that all the required checkboxes are present
    Then I will see check boxes for the consumer opt-in

  @CP-268 @CP-268-02 @asad @crm-regression @ui-cc
  Scenario: Default Selections
    Given I logged into CRM and click on initiate contact
    When I search for a consumer and able to click on Add Consumer button
    Then I will see the default configured channels of Opted-in as Checked

  @CP-268 @CP-268-03 @asad @crm-regression @ui-cc
  Scenario: Check or Uncheck
    Given I logged into CRM and click on initiate contact
    When I search for a consumer and able to click on Add Consumer button
    Then I will be able to check or uncheck any of the Opt-in check boxes

  @CP-273 @CP-273-01 @muhabbat @crm-regression @ui-cc
  Scenario: Opt-in/out Check Boxes on edit consumer profile page
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as "Potter"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on Edit button for Profile Details
    When I verify that all the required checkboxes are present
    Then I will see check boxes for the consumer opt-in

  @CP-273 @CP-273-02 @muhabbat @crm-regression @ui-cc
  Scenario: Default Selections on edit consumer profile page
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as "Potter"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on Edit button for Profile Details
    Then I will see the default configured channels of Opted-in as Checked

  @CP-273 @CP-273-03 @muhabbat @crm-regression @ui-cc
  Scenario: Check or Uncheck on edit consumer profile page
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Victor" and Last Name as "Gugo"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on Edit button for Profile Details
    Then I will be able to check or uncheck any of the Opt-in check boxes

  @CP-30519 @CP-30519-01 @Beka @crm-regression @ui-cc
  Scenario: Verify Opt-In Information component NOT Displayed on consumer profile page
    Given I logged into CRM with "Service Tester 2" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "Tes" and Last Name as "Sdfs"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I NOT see the Communication Opt-In Information component on "consumer profile page"

  @CP-30519 @CP-30519-02 @Beka @crm-regression @ui-cc
  Scenario: Verify Opt-In Information component NOT Displayed on add PI page
    Given I logged into CRM with "Service Tester 2" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "John" and Last Name as "Muller"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Add button for primary individual
    Then I NOT see the Communication Opt-In Information component on "add PI page"

  @CP-30519 @CP-30519-03 @Beka @crm-regression @ui-cc
  Scenario: Verify Opt-In Information component NOT Displayed on add CM page
    Given I logged into CRM with "Service Tester 2" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "John" and Last Name as "Muller"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on add button Case Member
    Then I NOT see the Communication Opt-In Information component on "add CM page"

  @CP-30519 @CP-30519-04 @Beka @crm-regression @ui-cc
  Scenario: Verify Opt-In Information component NOT Displayed on Create Consumer page
    Given I logged into CRM with "Service Tester 2" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "SomeRandomName" and Last Name as "SomeRandomLastname"
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I NOT see the Communication Opt-In Information component on "Create Consumer page"

  @CP-30519 @CP-30519-05 @Beka @crm-regression @ui-cc-va
  Scenario: Verify Opt-In Information component NOT Displayed on consumer profile page CoverVA
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I click on initiate a contact button
    When I searched customer have First Name as "Test" and Last Name as "Asdasdas"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I NOT see the Communication Opt-In Information component on "consumer profile page"

  @CP-30519 @CP-30519-06 @Beka @crm-regression @ui-cc-va
  Scenario: Verify Opt-In Information component NOT Displayed on Create Consumer page CoverVA
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I click on initiate a contact button
    When I searched customer have First Name as "SomeRandomName" and Last Name as "SomeRandomLastname"
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I NOT see the Communication Opt-In Information component on "Create Consumer page"
