Feature: View/Update Consumer Role - Primary Individual Information

  @CP-401 @CP-401-01 @ui-cc @muhabbat
  Scenario:View Primary Individual Information
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Tom" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    When I click on existing Primary Individual Record
    And I validate relationship field "not displayed"
    Then I am able to update the Consumer Role "Member"
    And I validate relationship field "required"


  @CP-401 @CP-401-02 @ui-cc @muhabbat
  Scenario: Capture Relationship to PI
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Tom" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    When I click on existing Primary Individual Record
    Then I am able to update the Consumer Role "Member"
    And I validate relationship field "required"
    Then I see options for relationship field will display and is required
      | Child    |
      | Guardian |
      | Spouse   |
      | Unknown  |

  @CP-401 @CP-401-03 @ui-cc @muhabbat
  Scenario:Default and not editable Role on Add New Primary Individual
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Tom" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    When I click on the Add button for primary individual
    Then I verify consumer role is "Primary Individual" and is disabled

  @CP-401-04 @crm-regression @events-cc @muhabbat @events-cc
  Scenario: CONSUMER_UPDATE_EVENT check for CM End-date and PI start-end
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for case consumer date field check for "Primary Individual"
    When I click case consumer search tab
    And I search consumer with first name and last name for case consumer date field check
    When I click on existing Primary Individual Record
    When I change consumer role to "Member"
    Then I see options for relationship field will display and is required
    |Spouse |
    And I click on Save button on add primary individual page
    And I click on continue button on warning message
    And I click on demographic tab and click on existing "Member" for Correspondence preference
    Then I verify consumer to have start date as current date
    And I will take correlation Id for "CONSUMER_UPDATE_EVENT" for "consumerId" for case consumer date field check
    Then I will verify Case Member end dated
