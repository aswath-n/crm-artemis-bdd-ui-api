Feature: Consumer with DOD should not be Active

  @CP-24838 @CP-24838-01 @Beka @crm-regression @ui-cc
  Scenario: Verify Inactive Status for Primary Individual when DoD is populated
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on demographic tab and edit the "Primary Individual" member
    And I populate the Date of Death "curentDate" and EndDate ""
    Then Verify the consumer has been systematically inactivated "Primary Individual"

  @CP-24838 @CP-24838-02 @Beka @crm-regression @ui-cc
  Scenario: Verify the End Date is systematically set for Primary Individual when DoD is populated
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on demographic tab and edit the "Primary Individual" member
    And I populate the Date of Death "past1" and EndDate "future5"
    Then Verify the End Date is systematically set to match the DoD

  @CP-24838 @CP-24838-03 @Beka @crm-regression @ui-cc
  Scenario: Verify the End Date is systematically set as DoD date when End Date not pupulated
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on demographic tab and edit the "Primary Individual" member
    And I populate the Date of Death "past1" and EndDate ""
    Then Verify the End Date is systematically set to match the DoD

  @CP-24838 @CP-24838-04 @Beka @crm-regression @ui-cc
  Scenario: Verify the End Date is stay as it is when DoD greter then End Date
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on demographic tab and edit the "Primary Individual" member
    And I populate the Date of Death "past1" and EndDate "past2"
    Then Verify the End Date is stay same

  @CP-24838 @CP-24838-05 @Beka @crm-regression @ui-cc
  Scenario: Verify Inactive Status for Case member when DoD is populated
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on add button Case Member
    And I create case member with end date "" and DoD "past"
    Then Verify the consumer has been systematically inactivated "member"

  @CP-24838 @CP-24838-06 @Beka @crm-regression @ui-cc
  Scenario: Verify the End Date is systematically set for case member when End Date not pupulated
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on add button Case Member
    And I create case member with end date "" and DoD "past"
    Then Verify the End Date is systematically set to match the DoD for case member

  @CP-24838 @CP-24838-07 @Beka @crm-regression @ui-cc
  Scenario: Verify the End Date is systematically set for case member when End Date is populated
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on add button Case Member
    And I create case member with end date "present" and DoD "past"
    Then Verify the End Date is systematically set to match the DoD for case member