@BusinessDays
Feature: Tenant Manager - Business Days Functionality

  @CP-13824 @CP-13824-01 @CP-13824-02 @CP-13824-02_01 @basha @tm-regression @ui-tm
  Scenario Outline: Add Time Frame in Business Days page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Business Days Page
    And I see a Warning Message permanently displayed at the bottom
    And I click Add Business Days
    When I populate data on business days details page "<businessDayName>","<startDate>","<endDate>","<excludeForTask>","<excludeForSR>"
    And I select business days "<sun>","<mon>","<tue>","<wed>","<thu>","<fri>","<sat>"
    And I click on Save button on business days details page
    Then I verify time frame is added on the screen
    Examples:
      | businessDayName | startDate | endDate | excludeForTask | excludeForSR | sun     | mon  | tue     | wed  | thu     | fri  | sat     |
      | {random}        | today     | +13     | true           | [blank]      |         | true | true    | true | [blank] |      | [blank] |
      | {random}        | today     | +366    | [blank]        | true         | [blank] |      | [blank] |      | true    | true | [blank] |

  @CP-13824 @CP-13824-03 @basha @tm-regression @ui-tm
  Scenario Outline: Edit Future Time Frames
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Business Days Page
    And I click Add Business Days
    When I populate data on business days details page "<businessDayName>","<startDate>","<endDate>","<excludeForTask>","<excludeForSR>"
    And I select business days "<sun>","<mon>","<tue>","<wed>","<thu>","<fri>","<sat>"
    And I click on Save button on business days details page
    Then I verify time frame is in the future
    And I edit the fields in a future time frame "<businessDayName>","<startDate>","+114","<excludeForTask>","","<sun>","<mon>","<tue>","","<thu>","<fri>","<sat>"
    And I click on Save button on business days details page
    Then I verify time frame is in the future
    Examples:
      | businessDayName | startDate | endDate | excludeForTask | excludeForSR | sun     | mon  | tue  | wed  | thu  | fri  | sat     |
      | {random}        | +90       | +111    | true           | true         | [blank] | true | true | true | true | true | [blank] |

  @CP-13824 @CP-13824-03_01 @basha @tm-regression @ui-tm
  Scenario Outline: Edit End Date on Current Time Frames
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Business Days Page
    And I click Add Business Days
    When I populate data on business days details page "<businessDayName>","<startDate>","<endDate>","<excludeForTask>","<excludeForSR>"
    And I select business days "<sun>","<mon>","<tue>","<wed>","<thu>","<fri>","<sat>"
    And I click on Save button on business days details page
    Then I verify time frame is added on the screen
    Then I can edit the End Date "+9" field in current time frame
    Examples:
      | businessDayName | startDate | endDate | excludeForTask | excludeForSR | sun | mon  | tue     | wed  | thu     | fri  | sat     |
      | {random}        | today     | +8      | true           | [blank]      |     | true | [blank] | true | [blank] | true | [blank] |

  @CP-13824 @CP-13824-03_02 @basha @tm-regression @ui-tm
  Scenario Outline: Cannot Edit Past Time Frames
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Business Days Page
    And I click Add Business Days
    When I populate data on business days details page "<businessDayName>","<startDate>","<endDate>","<excludeForTask>","<excludeForSR>"
    And I select business days "<sun>","<mon>","<tue>","<wed>","<thu>","<fri>","<sat>"
    And I click on Save button on business days details page
    Then I verify time frame is added on the screen
    Then I verify i cannot edit the fields in time frame
    Examples:
      | businessDayName | startDate | endDate | excludeForTask | excludeForSR | sun | mon     | tue | wed  | thu     | fri  | sat     |
      | {random}        | today     | +15     | true           | [blank]      |     | [blank] |     | true | [blank] | true | [blank] |

  #Defect CP-16381 covered
  @CP-13824 @CP-13824-04 @basha @tm-regression @ui-tm
  Scenario Outline: No Overlapping Time Frames
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Business Days Page
    And I click Add Business Days
    When I populate data on business days details page "<businessDayName>","<startDate>","<endDate>","<excludeForTask>","<excludeForSR>"
    And I select business days "<sun>","<mon>","<tue>","<wed>","<thu>","<fri>","<sat>"
    And I click on Save button on business days details page
    Then I verify time frame is added on the screen
    And I click Add Business Days
    When I populate data on business days details page "<businessDayName>","<startDate>","+10","<excludeForTask>","<excludeForSR>"
    And I select business days "<sun>","<mon>","<tue>","<wed>","<thu>","<fri>","<sat>"
    And I click on Save button on business days details page
    Then I verify system displays conflict error message on the screen
    Examples:
      | businessDayName | startDate | endDate | excludeForTask | excludeForSR | sun | mon     | tue | wed  | thu     | fri  | sat     |
      | {random}        | today     | +15     | true           | [blank]      |     | [blank] |     | true | [blank] | true | [blank] |

   #Defect CP-16222 covered
  @CP-13824 @CP-13824-05 @basha @tm-regression @ui-tm
  Scenario: Copy Time Frames from Previous Year
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Business Days Page
    And I am able to copy the Time Frames from the previous year
    And I click on Save button on business days details page

   #Defect CP-15686 covered
  @CP-13824 @CP-13824-06 @basha @tm-regression @ui-tm
  Scenario Outline: Cancel Changes in Business Days page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Business Days Page
    And I click Add Business Days
    When I populate data on business days details page "<businessDayName>","<startDate>","<endDate>","<excludeForTask>","<excludeForSR>"
    Then I verify changes on the screen after clicking cancel and continue buttons
    Examples:
      | businessDayName | startDate | endDate | excludeForTask | excludeForSR |
      | {random}        | +1        | +8      | true           | [blank]      |

   #additional scenarios Defect-16157
  @CP-13824 @CP-13824-07 @basha @tm-regression @ui-tm
  Scenario Outline: Verify warning message when navigating to another tab and validate name field
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Business Days Page
    And I click Add Business Days
    When I populate data on business days details page "<businessDayName>","<startDate>","<endDate>","<excludeForTask>","<excludeForSR>"
    Then I verify start date can not be past error message displayed
    And I select Plans Management
    Then I verify navigation warning message displayed
    And I click on continue button on warning message in TM APP
    When I navigate to Business Days Page
    And I click Add Business Days
    When I populate data on business days details page "<businessDayName>","+1","<endDate>","<excludeForTask>","<excludeForSR>"
    And I verify name field
    And I click on Save button on business days details page
    Examples:
      | businessDayName | startDate | endDate | excludeForTask | excludeForSR |
      | {random}        | -2        | +8      | true           | [blank]      |

