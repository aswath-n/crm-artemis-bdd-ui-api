@CP-8104
Feature: Verify Warning Message when User Has Entered Data

  @CRM-8104 @CRM-8104-1.0 @jp @crm-regression @ui-cc
  Scenario: Verify Warning Message when User Click Cancel after Entering Data during Manually Creating Consumer
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    And I click on cancel button
    Then I verify the  warning message on dialog box
    And I verify users stays in same page when clicked on Cancel
    And I verify user is navigated back to previous screen when clicked on Continue

  @CRM-8104 @CRM-8104-1.1 @jp @crm-regression @ui-cc
  Scenario: Verify Warning Message when User Click back arrow after Entering Data during Manually Creating Consumer
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    And I click on Back Arrow to navigate away from Create Consumer Page
    Then I verify the  warning message on dialog box
    And I verify users stays in same page when clicked on Cancel
    And I verify user is navigated back to previous screen when clicked on Continue

  @CRM-8104 @CRM-8104-2.0 @jp @crm-regression @ui-cc
  Scenario: Verify Warning Message when User Entered No Data and navigates away during Manually Creating Consumer
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I click on cancel button
    Then I verify there is no warning message on dialog box