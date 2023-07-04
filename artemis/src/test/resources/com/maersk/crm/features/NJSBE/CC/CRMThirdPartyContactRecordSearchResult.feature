@CP-11342
Feature: NJ Display 3rd Party Contact Record Results & case/consumer search results components on Details screens

  @CP-11342 @CP-11342-01.0 @asad @ui-cc-nj @crm-regression @nj-regression
  Scenario: Case/Consumer search result for NJ-SBE includes Broker & Assister roles in results
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I click on third party contact record in active contact
    When I searched customer have First Name as "Keshanna" and Last Name as "Jackson" in third party
    And I expand the Case Consumer this contact relates to in search result
    Then I verify radio buttons are displayed correctly for members with "atleast" broker and assister

  @CP-11342 @CP-11342-02 @asad @ui-cc-nj @crm-regression @nj-regression
  Scenario: Case/Consumer search result for NJ-SBE -NO  Broker, Assisters in result
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I click on third party contact record in active contact
    When I searched customer have First Name as "Keshanna" and Last Name as "Jackson" in third party
    And I expand the Case Consumer this contact relates to in search result
    Then I verify radio buttons are displayed correctly for members with "NO" broker and assister

  @CP-11342 @CP-11342-03.0.0 @asad @ui-cc-nj @crm-regression @nj-regression
  Scenario: Case/Consumer search result for NJ-SBE includes Broker & Assister roles in result - Create Task
    Given I logged into CRM and select a project "NJ-SBE"
    When I click to navigate "Outbound Call" task page
    When I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "Keshanna" and Last Name as "Jackson"
    And I expand the Case Consumer this contact relates to in search result
    Then I verify radio buttons are displayed correctly for members with "atleast" broker and assister

  @CP-11342 @CP-11342-03.1.0 @asad @ui-cc-nj @crm-regression @nj-regression
  Scenario: Case/Consumer search result for NJ-SBE includes Broker & Assister roles in result - Edit Task Details
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to "Task Search" page
    When I search Task by Task Id for contact record
    And I click on search button on task search page
    And I click on found task record for contact record and edit task
    When I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "Keshanna" and Last Name as "Jackson"
    And I expand the Case Consumer this contact relates to in search result
    Then I verify radio buttons are displayed correctly for members with "atleast" broker and assister

  @CP-11342 @CP-11342-03.2.0 @asad @ui-cc-nj @crm-regression @nj-regression
  Scenario: Case/Consumer search result for NJ-SBE includes Broker & Assister roles in result - Edit Contact Record Details
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to "Task Search" page
    When I navigate to manual contact record search page
    And I search for contact record by contact id "22825"
    And I click on searched record and edit contact
    When I searched customer have First Name as "Keshanna" and Last Name as "Jackson"
    And I expand the Case Consumer this contact relates to in search result
    Then I verify radio buttons are displayed correctly for members with "atleast" broker and assister
