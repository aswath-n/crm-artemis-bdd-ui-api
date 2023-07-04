Feature: CoverVA Add Pregnancy Indicator and Pregnancy Due Date to consumer profile

  @CP-24844 @CP-24844-01 @crm-regression @ui-cc-va @Beka
  Scenario: Display Pregnant Checkbox when Gender = Female
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I click on initiate contact record
    And I search for a consumer and to click on Add Consumer button
    When I select Gender as "Female" to verify pregnancy checkbox is displayed on create consumer page


  @CP-24844 @CP-24844-02 @crm-regression @ui-cc-va @Beka
  Scenario:  Pregnancy Due Date Field Display
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I search for a consumer and to click on Add Consumer button
    When I select Gender as "Female"
    And I click pregnancy checkbox
    Then I should be able to see PREGNANCY DUE DATE Field Display


  @CP-24844 @CP-24844-03 @crm-regression @ui-cc-va @Beka
  Scenario Outline: NOT Display Pregnant Checkbox when Gender = all the rest
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I click on initiate contact record
    And I search for a consumer and to click on Add Consumer button
    When I select all the rest "<Gender>" except Female
    Then I should NOT see PREGNANT checkbox
    Examples:
      |  Gender   |
      |  Male    |
      |  Neutral |
      |  Other   |
      |  Unknown |

  @CP-24844 @CP-24844-04 @crm-regression @ui-cc-va @Beka
  Scenario: I verify PREGNANCY DUE DATE is required
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I search for a consumer and to click on Add Consumer button
    When I select Gender as "Female"
    And I click pregnancy checkbox
    When I click on Create Consumer Button
    Then I verify "PREGNANCY DUE DATE" is required and cannot be left blank

  @CP-24844 @CP-24844-05 @crm-regression @ui-cc-va @Beka
  Scenario: Verification of Save Consumer with new Pregnancy functionality
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I search for a consumer and to click on Add Consumer button
    When I populate Create Consumer fields for a new  pregnant Female consumer
    When I click on Create Consumer Button
    Then I see new consumer is created and has a unique Consumer ID

  @CP-24844 @CP-24844-06 @crm-regression @ui-cc-va @Beka
  Scenario:  Pregnant Checkbox and PREGNANCY DUE DATE NOT display when other than Female reselected
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I search for a consumer and to click on Add Consumer button
    When I populate Create Consumer fields for a new  pregnant Female consumer
    When I select Gender as "Male"
    Then I should NOT see PREGNANT checkbox

