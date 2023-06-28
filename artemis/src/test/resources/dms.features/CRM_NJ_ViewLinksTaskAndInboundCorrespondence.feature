@CP-NJ10004
Feature: View Links between Task and Inbound Correspondence

  @CP-NJ10004 @CP-NJ10004-01.1 @asad @ui-ecms-nj
  Scenario: Verify task linked to Inbound Correspondence viewable from Task Details
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    Given I create a Task of type Inbound Task that is linked to an Inbound Correspondence for "NJ"
    And I logged into CRM and select a project "NJ-SBE"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for Inbound Correspondence with the one created
    And I click on the Inbound Correspondence Id result
    When I click on the Task Id to navigate to the Task details screen
    Then I should see a Link to the Inbound Correspondence in the Links Section for "NJ"
    And the Link to the Inbound Correspondence should display the following values
      |ID|Inbound Correspondence ID|
      |Name| Inbound Correspondence|
      |Type-NJ| Inbound Correspondence Type|
      |Status Date| Inbound Correspondence Status Set On|
      |Status| Inbound Correspondence Status|

  @CP-NJ10004 @CP-NJ10004-02.1 @asad @ui-ecms-nj
  Scenario: Verify Inbound Correspondence linked to task is viewable from Inbound Correspondence Details Page
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    Given I create a Task of type Inbound Task that is linked to an Inbound Correspondence for "NJ"
    And I logged into CRM and select a project "NJ-SBE"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for Inbound Correspondence with the one created
    And I click on the Inbound Correspondence Id result
    Then I should see a Link to the Task in the Links Section
    And the Link to the Task should display the following values
      | ID          | Task ID                         |
      | Name        | Task                            |
      | Type-NJ     | Type-NJ                         |
      | Status Date | Date of Task most recent Change |
      | Status      | Status of Task                  |

  @CP-NJ10004 @CP-NJ10004-03.1 @asad @ui-ecms-nj
  Scenario: Verify task linked to Inbound Correspondence will take me to the Inbound Correspondence
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    Given I create a Task of type Inbound Task that is linked to an Inbound Correspondence for "NJ"
    And I logged into CRM and select a project "NJ-SBE"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for Inbound Correspondence with the one created
    And I click on the Inbound Correspondence Id result
    And I click on the Task Id to navigate to the Task details screen
    And I should see a Link to the Inbound Correspondence in the Links Section for "NJ"
    When I click on the Link to the Inbound Correspondence
    Then I should see that I am navigated to the Inbound Correspondence Details Screen for "NJ"

