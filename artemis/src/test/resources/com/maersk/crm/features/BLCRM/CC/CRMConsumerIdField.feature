Feature: Copy/Paste of Internal Consumer ID must manage extra spaces

#  @CP-34510 @CP-34510-01 @muhabbat @ui-cc @crm-regression   scenario is muted due to change of functionality under CP-36577
  Scenario:  Active Contact Consumer Search with extra space Consumer ID
    Given I logged into CRM and click on initiate contact
    And I searched consumer with "CHIP" Consumer Id "MK876876876 "
    Then I validate consumer search result according to "CHIP CONSUMER ID" with extra space Consumer Id "MK876876876"

#  @CP-34510 @CP-34510-02 @muhabbat @ui-cc @crm-regression    scenario is muted due to change of functionality under CP-36577
  Scenario:  Manual Consumer Search with extra space Consumer ID
    Given I logged into CRM
    When I navigate to Manual Consumer search page
    And I searched consumer with "CHIP" Consumer Id "MK876876876 "
    Then I validate consumer search result according to "CHIP CONSUMER ID" with extra space Consumer Id "MK876876876"

  @CP-34510 @CP-34510-03 @muhabbat @ui-cc @crm-regression
  Scenario: Manual Contact record search with extra space Consumer ID
    Given I logged into CRM
    And I navigate to Contact Record search
    And I searched with "CHIP" Consumer Id "MK876876876 "
    Then Verify "CHIP" and Consumer Id "MK876876876" is visible

  @CP-34510 @CP-34510-04 @muhabbat @ui-cc @crm-regression
  Scenario: Task record search with a with extra space Consumer ID
    Given I logged into CRM
    When I navigate to "Task Search" page
    And I searched task with "CHIP" Consumer Id "MK876876876 "
    Then I validate task search result displayed expected record

