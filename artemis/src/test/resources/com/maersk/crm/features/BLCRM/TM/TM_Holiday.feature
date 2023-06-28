Feature: Tenant Manager - Holidays Functionality

  @CP-12186 @CP-12186-01 @asad @tm-smoke @ui-tm
  Scenario Outline: Verify Holiday is stored in yyyy-mm-dd format in db
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Holiday Details page
    And I enter the holiday details and click on save
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I initiated holiday GET API for "<projectId>" and "<year>"
    When I run the holiday GET API for update Holiday page
    Then I verify date is yyyy-mm-dd format in the response
    Then I remove the added holiday
    Examples:
      | projectId | year|projectName|
      | BLCRM     |2023 |[blank]|

#Feature: View Holiday Details

  @GA-CP-8364 @GA-CP-8364-01 @ga-tm-regression @vidya
  Scenario: Verify Holiday Details for GA project
    Given I logged into Tenant Manager and set the project context "project" value "SelectGAProject"
    When I navigate to Holiday Details page
    Then I verify select date are correctly configure for GA project
      |04/10/2020|
      |05/25/2020|
      |07/03/2020|
      |09/07/2020|
      |10/12/2020|
      |11/11/2020|
      |11/26/2020|
      |11/27/2020|
      |12/24/2020|
    And I verify holiday name are correctly configure for GA project
      |State Holiday|
      |Memorial Day |
      |4th of July  |
      |Labor Day    |
      |Columbus Day |
      |Veterans Day |
      |Thanksgiving Day|
      |State Holiday   |
      |Washington’s Birthday|
    And I will verify all task and service request check boxes are unchecked