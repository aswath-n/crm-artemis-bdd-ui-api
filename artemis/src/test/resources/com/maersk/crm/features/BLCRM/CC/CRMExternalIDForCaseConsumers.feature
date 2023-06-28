Feature: Add External ID Component to Consumer on a Case PI and CM

  @CP-25743 @CP-25743-01 @muhabbat @crm-regression @ui-cc
  Scenario: View External Id component from Add PI screen
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an Consumer Profile for Correspondence preference
    And I click on the Demographic Info Tab
    And I click on the Add button for primary individual
    Then I verify program dropdown value behaviour if External ID is missing
      | ExternalId_program |
      | CHIP,MEDICAID      |


  @CP-25743 @CP-25743-02 @muhabbat @crm-regression @ui-cc
  Scenario: Verify able to add greater than one External ID On Consumer profile Existing PI
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an Consumer Profile for Correspondence preference
    And I click on the Demographic Info Tab
    And I click on first Primary Individual
    Then I verify able to add more than on external ID
      | ExternalId_program |
      | CHIP,MEDICAID      |

  @CP-25743 @CP-25743-03 @muhabbat @crm-regression @ui-cc
  Scenario: View External Id component from Add CM screen
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an Consumer Profile for Correspondence preference
    And I click on the Demographic Info Tab
    And I click on add button Case Member
    Then I verify program dropdown value behaviour if External ID is missing
      | ExternalId_program |
      | CHIP,MEDICAID      |

  @CP-25743 @CP-25743-04 @muhabbat @crm-regression @ui-cc
  Scenario: Verify able to add greater than one External ID On Consumer profile Existing CM
    Given I logged into CRM
    And I click on initiate a contact button
    When I searched customer have First Name as "Sheldon" and Last Name as "Thomson"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on any existing Case Member
    Then I verify able to add more than on external ID
      | ExternalId_program |
      | CHIP,MEDICAID      |


  @CP-25743 @CP-25743-05 @muhabbat @crm-regression @ui-cc
  Scenario: Verify External ID Persists When Consumer Role Changes Existing PI
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "Primary Individual" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an Consumer Profile for Correspondence preference
    And I click on the Demographic Info Tab
    And I click on first Primary Individual
    When I edit Consumer Role from "Primary Individual" to "Member" and save
    Then I expand saved "Member" view screen
    And I verify External ID Persists after editing role


  @CP-25743 @CP-25743-06 @muhabbat @crm-regression @ui-cc
  Scenario: Verify External ID Persists When Consumer Role Changes Existing CM
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "Member" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an Consumer Profile for Correspondence preference
    And I click on the Demographic Info Tab
    And I click on first Primary Individual
    When I edit Consumer Role from "Member" to "Primary Individual" and save
    Then I expand saved "Primary Individual" view screen
    And I verify External ID Persists after editing role
