Feature: NJ Store INCOMPLETE APP GetInsured Consumer Id for applicants returned without an applicant GUID

  @CP-14472 @CP-14472-01 @ui-cc-nj @nj-regression @umid
  Scenario: Store INCOMPLETE APP GetInsured Consumer Id- General Contact Record
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Dimelo" and Last Name as "Keloke"
    And GetInsured is Displayed as "Incomplete App"
    And I click on first user radio button and "DOB" "PHONE NUMBER" check boxes
    Then I verify validate&link and Consumer Authenticated is displayed
    And I click validate&link button for "{string}"
    And I verify Link component Consumer Profile Id is "Incomplete App" on active page
    And I navigate to the case and contact details
    And I click on the first contact id on contact details page
    And I verify Link component Consumer Profile Id is "Incomplete App"
    And I verify GetInsure Consumer Id in Consumer in Contact is "Incomplete App"

  @CP-14472 @CP-14472-02  @ui-cc-nj @nj-regression @umid
  Scenario: Store INCOMPLETE APP GetInsured Consumer Id - 3rd Party Contact
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then I click on third party contact record type radio button
    When I searched existing case where First Name as "Dimelo" and Last Name as "Keloke" on third party page
    And GetInsured is Displayed as "Incomplete App"
    And I click on primary individual record in search result
    Then I verify validate&link is displayed
    And I click validate&link button for "{string}"

  @CP-14472 @CP-14472-03 @ui-cc-nj @nj-regression @umid
  Scenario: Store INCOMPLETE APP GetInsured Consumer Id- Manual Case/Consumer Search
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on Case Consumer Search page
    When I searched customer have First Name as "Dimelo" and Last Name as "Keloke"
    And GetInsured is Displayed as "Incomplete App"
    And I click to expand on primary individual
    Then I verify validate&link is displayed
    And I click validate&link button for "{string}"

  @CP-14472 @CP-14472-04 @ui-cc-nj @nj-regression @umid
  Scenario: Store INCOMPLETE APP GetInsured Consumer Id- Edit Contact Record Details
    Given I logged into CRM and select a project "NJ-SBE"
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    And I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "Dimelo" and Last Name as "Keloke"
    And GetInsured is Displayed as "Incomplete App"
    And I click on primary individual record in search result

  @CP-14472 @CP-14472-05 @ui-cc-nj @nj-regression @umid
  Scenario: Store INCOMPLETE APP GetInsured Consumer Id- Edit Task Screen
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to "Task Search" page
    And I click on advanced search
    When I search Task by current status date for record
    When I click on second Task ID on Task Search
    When I click Edit task type button
    When I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "Dimelo" and Last Name as "Keloke"
    And GetInsured is Displayed as "Incomplete App"
    And I click on primary individual record in search result
    Then I verify validate is displayed

  @CP-14472 @CP-14472-06 @ui-cc-nj @nj-regression @umid @ui-cc-smoke
  Scenario: Store INCOMPLETE APP GetInsured Consumer Id - manual case/consumer search (from Manual Contact record Search screen)
    Given I logged into CRM and select a project "NJ-SBE"
    And I navigate to Contact Record search
    When I search for contact record by contact id "23816"
    When I click on first Contact Record ID on Contact Record
    And I verify search result component Consumer Profile Id is "Incomplete App"
    And I verify GetInsure Consumer Id in Consumer in Contact is "Incomplete App"

  @CP-14472 @CP-14472-07 @ui-cc-nj @nj-regression @umid
  Scenario:  Store INCOMPLETE APP GetInsured Consumer Id - manual case/consumer search (from Active Contact record Search screen)
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Dimelo" and Last Name as "Keloke"
    And GetInsured is Displayed as "Incomplete App"
    And I click on first user radio button and "DOB" "PHONE NUMBER" check boxes
    And I click validate&link button for "{string}"
    And I navigate to the case and contact details
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    When I click on Unlink Contact Button on Active Contact Page
    When I searched customer have First Name as "Fedrick" and Last Name as "Bobe"
    And GetInsured is Displayed as "Incomplete App"
    And I click on primary individual record in search result
    Then I verify validate&link is displayed
    And I click validate&link button for "{string}"
