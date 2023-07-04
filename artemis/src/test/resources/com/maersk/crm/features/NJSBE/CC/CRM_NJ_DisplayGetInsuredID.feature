Feature: NJ Search for case/Consumer Viewing GI Case/Consumer IDs

  @CP-11951 @CP-11951-01 @ui-cc-nj @nj-regression @muhabbat
  Scenario: Viewing GI Consumer ID on General Contact Record search result
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    Then I verify GI consumer id as "GetInsured CONSUMER ID" in the search result


  @CP-11951 @CP-11951-02 @ui-cc-nj @nj-regression @muhabbat
  Scenario: Viewing GI Consumer ID on 3rd Party Contact Record search result
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then I click on third party contact record type radio button
    When I searched customer have First Name as "Queen" and Last Name as "Faith" in third party
    Then I verify GI consumer id as "GetInsured CONSUMER ID" in the search result

  @CP-11951 @CP-11951-03 @ui-cc-nj @nj-regression @muhabbat
  Scenario: Viewing GI Consumer ID  - Manual Case/Consumer Search
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on Case Consumer Search page
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    Then I verify GI consumer id as "GetInsured CONSUMER ID" in the search result

  @CP-11951 @CP-11951-04 @ui-cc-nj @nj-regression @muhabbat
  Scenario: Viewing GI Consumer ID  - Create Task UI search result
    Given I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    And I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    Then I verify GI consumer id as "GetInsured CONSUMER ID" in the search result

  @CP-11951 @CP-11951-05 @ui-cc-nj @nj-regression @muhabbat
  Scenario: Viewing GI Consumer ID  - Edit Task Screen search result
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to "Task Search" page
    When I search Task by current status date for record
    When I click on second Task ID on Task Search
    When I click Edit task type button
    When I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    Then I verify GI consumer id as "GetInsured CONSUMER ID" in the search result

  @CP-11951 @CP-11951-06 @ui-cc-nj @nj-regression @muhabbat
  Scenario: Viewing GI Consumer ID - manual case/consumer search (from Manual Contact record Search screen)
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Contact Record Search Page
    When I can search Contact Record by status date and id "NJ100002908"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    And I click on Unlink Contact Button on Active Contact Page
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    Then I verify GI consumer id as "GetInsured CONSUMER ID" in the search result

  @CP-11951 @CP-11951-07 @ui-cc-nj @nj-regression @muhabbat
  Scenario: GI Consumer ID - manual case/consumer search (from Active Contact record Search screen)
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Nova" and Last Name as "Pablo"
    And I click on first user radio button and "DOB" "SSN" check boxes
    And I click validate&link and contact details tab
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    When I click on Unlink Contact Button on Active Contact Page
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    Then I verify GI consumer id as "GetInsured CONSUMER ID" in the search result

  @CP-11951 @CP-11951-08 @ui-cc-nj @nj-regression @muhabbat
  Scenario: GI Consumer ID on General Contact Record search result after linking
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on first user radio button and "DOB" "SSN" check boxes
    Then I verify consumer id as "1000005508" after clicking validate&link button for "general"

  @CP-11951 @CP-11951-09 @ui-cc-nj @nj-regression @muhabbat
  Scenario: GI Consumer ID on 3rd Party Contact Record search result after linking
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then I click on third party contact record type radio button
    When I searched customer have First Name as "Queen" and Last Name as "Faith" in third party
    And I click on primary individual record in search result
    Then I verify consumer id as "1000005508" after clicking validate&link button for "third"

  @CP-11951 @CP-11951-10 @ui-cc-nj @nj-regression @muhabbat
  Scenario: GI Consumer ID - Create Task UI after linking
    Given I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    And I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on primary individual record in search result
    Then I verify validate is displayed
    Then I verify consumer id as "1000005508" after clicking validate&link button for "createTask"

  @CP-11951 @CP-11951-11 @ui-cc-nj @nj-regression @muhabbat
  Scenario: GI Consumer ID - Edit Task Screen after linking
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to "Task Search" page
    When I search Task by type for record
    When I click on second Task ID on Task Search
    When I click Edit task type button
    When I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on primary individual record in search result
    Then I verify validate is displayed
    Then I verify consumer id as "1000005508" after clicking validate&link button for "taskSearch"

  @CP-11951 @CP-11951-12 @ui-cc-nj @nj-regression @muhabbat
  Scenario: GI Consumer ID - manual case/consumer search (from Manual Contact record Search screen) after linking
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Contact Record Search Page
    When I searched customer with Contact Record ID "19841"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    And I click on Unlink Contact Button on Active Contact Page
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on primary individual record in search result
    Then I verify consumer id as "1000005508" after clicking validate&link button for "search"

  @CP-11951 @CP-11951-13 @ui-cc-nj @nj-regression @muhabbat
  Scenario: GI Consumer IDd - manual case/consumer search (from Active Contact record Search screen) after linking
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Nova" and Last Name as "Pablo"
    And I click on first user radio button and "DOB" "SSN" check boxes
    And I click validate&link and contact details tab
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    When I click on Unlink Contact Button on Active Contact Page
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on primary individual record in search result
    Then I verify consumer id as "1000005508" after clicking validate&link button for "search"


  @CP-11952 @CP-11952-01 @ui-cc-nj @nj-regression @muhabbat
  Scenario: Viewing GI Case ID on General Contact Record search result
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    Then I verify GI case id as "GetInsured CASE ID" in the search result


  @CP-11952 @CP-11952-02 @ui-cc-nj @nj-regression @muhabbat
  Scenario: Viewing GI Case ID on 3rd Party Contact Record search result
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then I click on third party contact record type radio button
    When I searched customer have First Name as "Queen" and Last Name as "Faith" in third party
    Then I verify GI case id as "GetInsured CASE ID" in the search result

  @CP-11952 @CP-11952-03 @ui-cc-nj @nj-regression @muhabbat
  Scenario: Viewing GI Case ID  - Manual Case/Consumer Search
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on Case Consumer Search page
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    Then I verify GI case id as "GetInsured CASE ID" in the search result

  @CP-11952 @CP-11952-04 @ui-cc-nj @nj-regression @muhabbat
  Scenario: Viewing GI Case ID  - Create Task UI search result
    Given I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    And I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    Then I verify GI case id as "GetInsured CASE ID" in the search result


  @CP-38661 @CP-38661-3 @ui-cc-nj @nj-regression @beka
  Scenario: Verify search circle is display when search result is loading manual search NJ
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on Case Consumer Search page
    When I search for an existing contact by SSN "7777"
    And I click on Search Button on Search Consumer Page
    Then I verify search circle is display

  @CP-38661 @CP-38661-4 @ui-cc-nj @nj-regression @beka
  Scenario: Verify search circle is display when search result is loading from active contact search page NJ
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I search for an existing contact by SSN "7777"
    And I click on Search Button on Search Consumer Page
    Then I verify search circle is display