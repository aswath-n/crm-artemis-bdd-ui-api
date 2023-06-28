Feature: NJ Display validate button across case/consumer search component UIs

  @CP-11405 @CP-11405-01 @ui-cc-nj @nj-regression @umid
  Scenario: Display Validate & Link button - General Contact Record
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on first user radio button and "DOB" "SSN" check boxes
    Then I verify validate&link and Consumer Authenticated is displayed

  @CP-11405 @CP-11405-02  @ui-cc-nj @nj-regression @umid
  Scenario: Display Validate & Link button - 3rd Party Contact
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then I click on third party contact record type radio button
    When I searched customer have First Name as "Queen" and Last Name as "Faith" in third party
    And I click on primary individual record in search result
    Then I verify validate&link is displayed

  @CP-11405 @CP-11405-03 @ui-cc-nj @nj-regression @umid
  Scenario: Display Validate & Link button - Manual Case/Consumer Search
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on Case Consumer Search page
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click to expand on primary individual
    Then I verify validate&link is displayed

  @CP-11405 @CP-11405-04 @ui-cc-nj @nj-regression @umid
  Scenario: Display Validate & Link button - Edit Contact Record Details
    Given I logged into CRM and select a project "NJ-SBE"
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    And I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on primary individual record in search result
    Then I verify validate is displayed
    Then I click validate and I verify after Action

  @CP-11405 @CP-11405-05 @ui-cc-nj @nj-regression @umid
  Scenario: Display Validate button AND separate Link button - Edit Task Screen
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to "Task Search" page
    When I search Task by current status date for record
    When I click on second Task ID on Task Search
    When I click Edit task type button
    When I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on primary individual record in search result
    Then I verify validate is displayed
    Then I click validate and I verify after Action


  @CP-11405 @CP-11405-06 @ui-cc-nj @nj-regression @umid
  Scenario: Display Validate & Link button - Edit Contact Record Details - manual case/consumer search (from Manual Contact record Search screen)
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Contact Record Search Page
    When I can search Contact Record by status date and id "NJ100003702"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    And I click on Unlink Contact Button on Active Contact Page
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on primary individual record in search result
    Then I verify validate&link is displayed

  @CP-11405 @CP-11405-07 @ui-cc-nj @nj-regression @umid
  Scenario:  Display Validate & Link button - Edit Contact Record Details - manual case/consumer search (from Active Contact record Search screen)
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    When I click on Unlink Contact Button on Active Contact Page
    When I searched customer have First Name as "Nova" and Last Name as "Pablo"
    And I click on primary individual record in search result
    Then I verify validate&link is displayed

  @CP-12965 @CP-12965-01 @ui-cc-nj @nj-regression @Beka
  Scenario: Select Validate & Link button - General Contact Record
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on first user radio button and "DOB" "SSN" check boxes
    Then I verify validate&link and Consumer Authenticated is displayed
    Then I click validate and I verify GetInsured CASE ID is display