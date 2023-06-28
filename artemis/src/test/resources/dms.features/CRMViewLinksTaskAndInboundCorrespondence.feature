@CP-10004
Feature: View Links between Task and Inbound Correspondence

  @CP-10004 @CP-10004-01.0 @asad @ui-ecms1
  Scenario: Verify task linked to Inbound Correspondence viewable from Task Details
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    Given I create a Task of type Inbound Task that is linked to an Inbound Correspondence for "BLCRM"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for Inbound Correspondence with the one created
    And I click on the Inbound Correspondence Id result
    When I click on the Task Id to navigate to the Task details screen
    Then I should see a Link to the Inbound Correspondence in the Links Section for "BLCRM"
    And the Link to the Inbound Correspondence should display the following values
      | ID          | Inbound Correspondence ID            |
      | Name        | Inbound Correspondence               |
      | Type-BLCRM  | Inbound Correspondence Type          |
      | Status Date | Inbound Correspondence Status Set On |
      | Status      | Inbound Correspondence Status        |

  @CP-10004 @CP-10004-02.0 @asad @ui-ecms1
  Scenario: Verify Inbound Correspondence linked to task is viewable from Inbound Correspondence Details Page
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    Given I create a Task of type Inbound Task that is linked to an Inbound Correspondence for "BLCRM"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for Inbound Correspondence with the one created
    And I click on the Inbound Correspondence Id result
    Then I should see a Link to the Task in the Links Section
    And the Link to the Task should display the following values
      | ID          | Task ID                         |
      | Name        | Service Request                 |
      | Type        | Inbound Task                    |
      | Status Date | Date of Task most recent Change |
      | Status      | Status of Task                  |

  @CP-10004 @CP-10004-03.0 @asad @ui-ecms2
  Scenario: Verify task linked to Inbound Correspondence will take me to the Inbound Correspondence
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    Given I create a Task of type Inbound Task that is linked to an Inbound Correspondence for "BLCRM"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for Inbound Correspondence with the one created
    And I click on the Inbound Correspondence Id result
    And I click on the Task Id to navigate to the Task details screen
    And I should see a Link to the Inbound Correspondence in the Links Section for "BLCRM"
    When I click on the Link to the Inbound Correspondence
    Then I should see that I am navigated to the Inbound Correspondence Details Screen for "BLCRM"

