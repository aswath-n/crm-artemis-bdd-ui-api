Feature: Case-Consumer Profile Search Results View

  @CP-348 @CP-348-01 @ozgen @crm-regression @ui-cc
  Scenario:Verify View Search Results-First Glance
    Given I logged into CRM
    When I click case consumer search tab
    When I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    Then I should view CRM case id, CRM consumer id, FirstName, Middle Name, LastName, DOB, SSN fields at search results

  @CP-348 @CP-348-02 @ozgen @crm-regression @ui-cc
  Scenario: Verify View Search Results-Expanded View
    Given I logged into CRM
    When I click case consumer search tab
    When I searched customer have First Name as "DemondIwoBE" and Last Name as "KeelingnRAmy"
    Then I expand the Case Consumer in search result
    Then I should see address information for each result

  @CP-348 @CP-348-03 @ozgen @crm-regression @ui-cc
  Scenario: Verify View Search Results-Expanded View-Case Search Results
    Given I logged into CRM
    When I click case consumer search tab
    When I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    Then I expand the Case Consumer in search result
    Then I should view all Consumer's associated to the Case & their specific Role on the Case

  @CP-348 @CP-348-04 @ozgen @crm-regression @ui-cc
  Scenario: Initial Search Result Sort Order by Case Id
    Given I logged into CRM
    When I click case consumer search tab
    When I search consumer has first name as "Ethan"
    Then I verify that all records sorted by Case Id in descending order


  @CP-348 @CP-348-05 @ozgen @crm-regression @ui-cc
  Scenario: Initial Search Result Sort Order by Consumer Id
    Given I logged into CRM
    When I click case consumer search tab
    When I search consumer has first name as "Ethan"
    Then I verify that all records which doesnt have case id sorted in descending order


 # @CP-348 @CP-348-06 @ozgen @crm-regression
    #It is not able to be tested because as Courtney's comment on user story this is not able to sort the search results by any other columns

  @CP-29785 @CP-29785-01 @Beka @crm-regression @ui-cc
  Scenario: Verify Notes Component in Case Summary
    Given I logged into CRM
    When I click case consumer search tab
    When I searched customer have First Name as "Jon" and Last Name as "Dow"
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on the Demographic Info Tab
    Then I see notes component with dropdown relates to save and cancel button "Case"

  @CP-29785 @CP-29785-02 @Beka @crm-regression @ui-cc
  Scenario: Verify Notes Component Elements
    Given I logged into CRM
    When I click case consumer search tab
    When I searched customer have First Name as "Jon" and Last Name as "Dow"
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on the Demographic Info Tab
    Then I verify text field is can take 1000alphanumeric characters

  @CP-29785 @CP-29785-03 @Beka @crm-regression @ui-cc
  Scenario: Verify Then I will have a required single select list of options Case and all Consumers should be listed
    Given I logged into CRM
    When I click case consumer search tab
    When I searched customer have First Name as "John" and Last Name as "Muller"
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on the Demographic Info Tab
    Then Verify Then I will have a required single select list of options Case and all Consumers should be listed

  @CP-29785 @CP-29785-04 @Beka @crm-regression @ui-cc
  Scenario: Verify Notes get saved
    Given I logged into CRM
    When I click case consumer search tab
    When I searched customer have First Name as "jon" and Last Name as "dow"
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on the Demographic Info Tab
    Then I will verify new note get saved as 1st note in table

  @CP-29785 @CP-29785-05 @Beka @crm-regression @ui-cc
  Scenario: Verify error message when try to save notes without selecting relates to value
    Given I logged into CRM
    When I click case consumer search tab
    When I searched customer have First Name as "Jon" and Last Name as "dow"
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on the Demographic Info Tab
    Then I Verify error message "Please indicate who this note relates to."

  @CP-29785 @CP-29785-06 @Beka @crm-regression @ui-cc
  Scenario: Verify error message when try to save notes with No text
    Given I logged into CRM
    When I click case consumer search tab
    When I searched customer have First Name as "Jon" and Last Name as "dow"
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on the Demographic Info Tab
    Then I Verify error message "Please provide Note text."

  @CP-29799 @CP-29799-01 @Beka @crm-regression @ui-cc
  Scenario: Verify Notes Component in consumer profile not on a case in BLCRM
    Given I logged into CRM
    When I click case consumer search tab
    When I searched customer have First Name as "Umma" and Last Name as "Turman"
    And I click on first consumerID of consumer profile on manual search result
    And I click on the Demographic Info Tab
    Then I see notes component with dropdown relates to save and cancel button "ConsumerProfile"

  @CP-29799 @CP-29799-02 @Beka @crm-regression @ui-cc
  Scenario: Verify Notes can be successfully save in BLCRM
    Given I logged into CRM
    When I click case consumer search tab
    When I searched customer have First Name as "Umma" and Last Name as "Turman"
    And I click on first consumerID of consumer profile on manual search result
    And I click on the Demographic Info Tab
    Then I will verify new note get saved as 1st note in table

  @CP-29799 @CP-29799-03 @Beka @crm-regression @ui-cc-in
  Scenario: Verify Notes Component in consumer profile not on a case in IN-EB
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click case consumer search tab
    When I searched customer have First Name as "Umma" and Last Name as "Turman"
    When I expand the record to navigate Consumer Record from manual view
    And I click on the Demographic Info Tab
    Then I see notes component with dropdown relates to save and cancel button "ConsumerProfile"

  @CP-29799 @CP-29799-04 @Beka @crm-regression @ui-cc-in
  Scenario: Verify Notes can be successfully save in IN-EB
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click case consumer search tab
    When I searched customer have First Name as "Umma" and Last Name as "Turman"
    When I expand the record to navigate Consumer Record from manual view
    And I click on the Demographic Info Tab
    Then I will verify new note get saved as 1st note in table

