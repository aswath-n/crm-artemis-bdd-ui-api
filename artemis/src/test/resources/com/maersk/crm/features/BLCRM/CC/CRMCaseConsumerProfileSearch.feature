Feature: Manual Case/Consumer Profile Search

  @CP-345 @CP-345-01 @asad @crm-regression @ui-cc
  Scenario: Search Criteria for case consumer profile search
    Given I logged into CRM
    When I click case consumer search tab
    Then I am able to manual Case Consumer Profile Search

  @CP-345 @CP-345-01.1 @asad @crm-regression @ui-cc
  Scenario: Field Level Validation for case consumer profile search
    Given I logged into CRM
    When I click case consumer search tab
    Then I entered a search parameter in the incorrect format and receive a error message for case consumer search

  @CP-345 @CP-345-02 @asad @crm-regression @ui-cc
  Scenario: Manual Search for Consumer and Case
    Given I logged into CRM
    When I click case consumer search tab
    Then I search and validate the result for case consumer search

  @CP-345 @CP-345-03 @asad @crm-regression @ui-cc
  Scenario: No Blank Searches for case consumer profile search
    Given I logged into CRM
    When I click case consumer search tab
    Then I search without any parameters and will be provided with error message "Please enter the search parameters" for case consumer search

  @CP-345 @CP-345-3.2 @asad @crm-regression @ui-cc
  Scenario: No Search Results for case consumer profile search
    Given I logged into CRM
    When I click case consumer search tab
    Then I search and validate the no result message "No Search Results Found" for case consumer search

  @CP-345 @CP-345-04 @asad @crm-regression @ui-cc
  Scenario: Exact Match Criteria for case consumer profile search
    Given I logged into CRM
    When I click case consumer search tab
    Then I enter the search data and validate the search result with the search data for case consumer search

  @CP-345 @CP-345-05 @asad @crm-regression @ui-cc
  Scenario: Contains Match Criteria for case consumer profile search
    Given I logged into CRM
    When I click case consumer search tab
    Then I enter the search data and validate whether the search result contains the search data for case consumer search

  @CP-3989 @CP-3989-01 @crm-regression @ui-cc @muhabbat
  Scenario Outline: Manual search parameters remain View Case/Consumer and navigate away with Back Arrow and Contact Icon
    Given I logged into CRM
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields consumer First Name as "Ethan" and Last Name as "Hunt"
    And I navigate to Contact Info Page within a case
    When I click on "<button>" manual case consumer info page
    And I navigate to Manual Consumer search page
    Then I see all search parameters remain per search by "<field1>" "<field2>"
    Examples:
      | button       | field1 | field2 |
      | Back Arrow   | Ethan  | Hunt   |
      | Contact Icon | Ethan  | Hunt   |

  @CP-3989 @CP-3989-02 @crm-regression @ui-cc @muhabbat
  Scenario: Cancel after Manual search View Case/Consumer and navigate away with Back Arrow and Contact Icon
    Given I logged into CRM
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields consumer First Name as "Ethan" and Last Name as "Hunt"
    And I navigate to Contact Info Page within a case
    When I click on "Back Arrow" manual case consumer info page
    And I navigate to Manual Consumer search page
    Then I see all search parameters remain per search by "Ethan" "Hunt"
    And I click on cancel button on manual case Consumer search UI
    When I click on "Contact Icon" manual case consumer info page
    And I navigate to Manual Consumer search page
    Then I verify search parameters on Manual Consumer search UI are cleared
