@CP-11154
Feature: Back Arrow on Add/Edit PI/AR/CM

  @CP-11154 @CP-11154-01 @asad @crm-regression @ui-cc
  Scenario: Back arrow  - Add PI, CM, AR
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer of type "Primary Individual" using Case Loader API for members
    And I search consumer with first name and last name for members
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "PI"
    Then I verify that I see back arrow beneath the consumer profile icon for "PI"
    And I click on demographic tab and click on add for "AR"
    Then I verify that I see back arrow beneath the consumer profile icon for "AR"
    And I click on demographic tab and click on add for "CM"
    Then I verify that I see back arrow beneath the consumer profile icon for "CM"


  @CP-11154 @CP-11154-02.1 @asad @crm-regression @ui-cc
  Scenario: Back arrow  - Edit PI
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer of type "Primary Individual" using Case Loader API for members
    And I search consumer with first name and last name for members
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and edit the "Primary Individual" member
    Then I verify that I see back arrow beneath the consumer profile icon for "PI"


  @CP-11154 @CP-11154-02.2 @asad @crm-regression @ui-cc
  Scenario: Back arrow  - Edit CM
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer of type "Member" using Case Loader API for members
    And I search consumer with first name and last name for members
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and edit the "Case Member" member
    Then I verify that I see back arrow beneath the consumer profile icon for "CM"


  @CP-11154 @CP-11154-02.3 @asad @crm-regression @ui-cc
  Scenario: Back arrow  - Edit AR
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer of type "Authorized Representative" using Case Loader API for members
    And I search consumer with first name and last name for members
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and edit the "Authorized Representative" member
    Then I verify that I see back arrow beneath the consumer profile icon for "AR"


  @CP-11154 @CP-11154-03 @asad @crm-regression @ui-cc
  Scenario: Back arrow  - Add PI, CM, AR outside active contact
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer of type "Primary Individual" using Case Loader API for members
    And I click case consumer search tab
    And I search consumer from consumer search with first name and last name for members
    And I click on demographic tab and click on add for "PI"
    Then I verify that I see back arrow beneath the consumer profile icon for "PI"
    And I click on demographic tab and click on add for "AR"
    Then I verify that I see back arrow beneath the consumer profile icon for "AR"
    And I click on demographic tab and click on add for "CM"
    Then I verify that I see back arrow beneath the consumer profile icon for "CM"


  @CP-11154 @CP-11154-04.1 @asad @crm-regression @ui-cc
  Scenario: Back arrow  - Edit PI outside active contact
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer of type "Primary Individual" using Case Loader API for members
    And I click case consumer search tab
    And I search consumer from consumer search with first name and last name for members
    And I click on demographic tab and edit the "Primary Individual" member
    Then I verify that I see back arrow beneath the consumer profile icon for "PI"


  @CP-11154 @CP-11154-04.2 @asad @crm-regression @ui-cc
  Scenario: Back arrow  - Edit CM outside active contact
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer of type "Member" using Case Loader API for members
    And I click case consumer search tab
    And I search consumer from consumer search with first name and last name for members
    And I click on demographic tab and edit the "Case Member" member
    Then I verify that I see back arrow beneath the consumer profile icon for "CM"


  @CP-11154 @CP-11154-04.3 @asad @crm-regression @ui-cc
  Scenario: Back arrow  - Edit AR outside active contact
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer of type "Authorized Representative" using Case Loader API for members
    And I click case consumer search tab
    And I search consumer from consumer search with first name and last name for members
    And I click on demographic tab and edit the "Authorized Representative" member
    Then I verify that I see back arrow beneath the consumer profile icon for "AR"