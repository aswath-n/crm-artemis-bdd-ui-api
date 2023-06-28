@CP-13222
Feature: NJ Caseloader endpoint should return the existing case Id for a Get Insured case Id that was previously saved
# to-do this functionality changed with CP-11951 and 11952 and automation has to be changes to check response returning corresponding case/consumer IDS
  @CP-13222 @CP-13222-01 #@ui-cc-nj @nj-regression @asad
  Scenario: Hit Validate & Link for a case that was previously saved - General Contact Record
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Harry" and Last Name as "Potter"
    And I click on first user radio button and "DOB" "SSN" check boxes
    Then I verify case id as "614" and consumer id as "1646" after clicking validate&link button for "general"

  @CP-13222 @CP-13222-02 #@ui-cc-nj @nj-regression @asad
  Scenario: Hit Validate & Link for a case that was previously saved - 3rd Party Contact Record
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then I click on third party contact record type radio button
    When I searched customer have First Name as "Harry" and Last Name as "Potter"
    And I click on primary individual record in search result
    Then I verify case id as "614" and consumer id as "1646" after clicking validate&link button for "third"

  @CP-13222 @CP-13222-03 @ui-cc-nj @nj-regression @asad
  Scenario: Hit Validate & Link for a case that was previously saved - Manual Case/Consumer Search
    Given I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I click on Case Consumer Search page
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click to expand on primary individual
    And I click validate&link and contact details tab
    When I click on first Contact Record ID on Contact Record
    Then I verify case id as "NJ100002908" and consumer id as "1000005508" after clicking validate&link button for "search"

  @CP-13222 @CP-13222-04 #@ui-cc-nj @nj-regression @asad
  Scenario: Hit Validate & Link for a case that was previously saved - Edit Contact Record Details
    Given I logged into CRM and select a project "NJ-SBE"
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    And I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "Harry" and Last Name as "Potter"
    And I click on primary individual record in search result
    Then I verify validate is displayed
    Then I verify case id as "614" and consumer id as "1646" after clicking validate&link button for "createTask"

  @CP-13222 @CP-13222-05 #@ui-cc-nj @nj-regression @asad
  Scenario: Hit Validate & Link for a case that was previously saved - Edit Task Screen
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to "Task Search" page
    When I search Task by current status date for record
    When I click on second ID on Task Search
    When I click Edit task type button
    When I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "Harry" and Last Name as "Potter"
    And I click on primary individual record in search result
    Then I verify validate is displayed
    Then I verify case id as "614" and consumer id as "1646" after clicking validate&link button for "taskSearch"

  @CP-13222 @CP-13222-06 #@ui-cc-nj @nj-regression @asad #Fails due to defect CP-14062
  Scenario: Hit Validate & Link for a case that was previously saved - manual case/consumer search (from Manual Contact record Search screen)
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Contact Record Search Page
    When I can search Contact Record by status date and id "SBE100000591"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on primary individual record in search result
    Then I verify case id as "614" and consumer id as "1646" after clicking validate&link button for "search"

  @CP-13222 @CP-13222-07 #@ui-cc-nj @nj-regression @asad #Fails due to defect CP-14062
  Scenario: Hit Validate & Link for a case that was previously saved - manual case/consumer search (from Active Contact record Search screen)
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
    Then I verify case id as "NJ100002908" and consumer id as "1000005508" after clicking validate&link button for "search"