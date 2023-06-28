@CP-12186
Feature: Update HOLIDAY_DATE to date datatype in HOLIDAYS table

  @CP-12186 @CP-12186-02 @asad @API-TM
  Scenario Outline: Verify Holiday GET API
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I added the holiday for a project
    And I initiated holiday GET API for "<projectId>" and "<year>"
    When I run the holiday GET API for update Holiday page
    Then I verify date is yyyy-mm-dd format in the response
    Examples:
      | projectId | year|projectName|
      | BLCRM     |2020 |[blank]|
