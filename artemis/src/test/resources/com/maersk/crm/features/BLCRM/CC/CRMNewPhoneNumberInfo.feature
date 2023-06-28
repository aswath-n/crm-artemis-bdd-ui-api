@fixed
Feature: New Phone Number Add/Edit feature

  @CRM-760 @CRM-760-01-01 @CRM-1344 @CRM-1344-01 @muhabbat @crm-regression @ui-cc
  Scenario: Verification of all fields being displayed on Add Phone Number Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I click on the Add Phone Number button on Contact Info Page
    Then I see all the fields are present on Add Phone Number Page

  @CRM-760 @CRM-760-01-02 @CRM-1344 @CRM-1344-02 @muhabbat @crm-regression @ui-cc
  Scenario: Validation of Type drop-down options on Add Phone Number Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I click on the Add Phone Number button on Contact Info Page
    Then I see drop-down option for Phone Number Type
      | Cell |
      | Home |
      | Work |

  @CRM-760 @CRM-760-02 @CRM-1047 @CRM-1047-01 @muhabbat @crm-regression @ui-cc @ui-cc-smoke
  Scenario: Verification new Phone number can be added
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I click on the Add Phone Number button on Contact Info Page
    When I provide valid data in all the fields and date as"current"
    And I click on Save button on add phone number page
    Then I am navigated back to Contact Info page

  @CRM-760 @CRM-760-03 @CRM-1344 @CRM-1344-03 @muhabbat @crm-regression @ui-cc
  Scenario: Verification of mandatory fields error messages on Add Phone Number Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I click on the Add Phone Number button on Contact Info Page
    Then I do not provide any data in all the fields
    And I click on Save button on add phone number page
    Then I see all Add New Phone page mandatory fields error messages displayed

  @CRM-760 @CRM-760-04 @muhabbat @crm-regression @ui-cc
  Scenario: Verification of error messages for invalid data provided to the fields on Add New Phone Number page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I click on the Add Phone Number button on Contact Info Page
    When I provide invalid data for Phone Number, Start Date and End Date
    Then I see invalid field error message displayed below Phone Number, Start Date and End Date fields

  @CRM-760 @CRM-760-05 @muhabbat @crm-regression @ui-cc
  Scenario Outline: Verification of Phone number statuses are according to the Start and End Dates
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I click on the Add Phone Number button on Contact Info Page
    When I provide valid data in all the fields on Add Phone Number page
    And I set start Date as "<startDate>" and end Date "<endDate>"
    Then I verify phone number status as "<status>"
    Examples:
      | startDate | endDate | status   |
      | past      | past    | INACTIVE |
#      | past      |[blank]| ACTIVE   |
      | past      | future  | ACTIVE   |
      | current   |[blank]| ACTIVE   |
      | current   | future  | ACTIVE   |
      | future    |[blank]| FUTURE   |
      | future    | future  | FUTURE   |


  @CRM-760 @CRM-760-06 @CRM-762 @CRM-762-09 @muhabbat @crm-regression @ui-cc
  Scenario: Validation of Inactivate Immediately functionality on Add/Edit new Phone Number Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I click on the Add Phone Number button on Contact Info Page
    When I provide valid data in all the fields and date as"current"
    And I click on Save button on add phone number page
    When I am navigated back to Contact Info page
    And I verify the current consumer has a phone number record displayed with status "ACTIVE"
    And I expend current phone number record to inactivate it immediately
    Then I verify phone number status as "INACTIVE"

  @CRM-762 @CRM-1047 @CRM-762-01 @muhabbat @crm-regression @ui-cc
  Scenario: Verification of all fields being displayed on Edit Phone Number Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I click on the Add Phone Number button on Contact Info Page
    When I provide valid data in all the fields and date as"current"
    And I click on Save button on add phone number page
    When I am navigated back to Contact Info page
    And I expend current phone number record and navigated to Edit Phone Number Page
    Then I see all the fields are present on Edit Phone Number Page

  @CRM-762 @CRM-762-020 @CRM-1047 @CRM-1047-02 @CRM-762-03 @CRM-762-06 @muhabbat @crm-regression @ui-cc
  Scenario Outline: Verification Additional Start and End dates editing phone number #1
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I click on the Add Phone Number button on Contact Info Page
    When I provide valid data in all the fields and date as"<date>"
    And I click on Save button on add phone number page
    When I am navigated back to Contact Info page
    And I expend current phone number record and navigated to Edit Phone Number Page
    When I provide valid data in all the fields on Add Phone Number page
    And I set start Date as "<startDate>" and end Date "<endDate>"
    Then I verify phone number status as "<status>"
    Examples:
      | date    | startDate | endDate | status   |
      | future  | past      | past    | INACTIVE |
      | current | future    |[blank]| FUTURE   |
      | current | future    | future  | FUTURE   |

  @CRM-762 @CRM-762-021 @CRM-1047 @CRM-1047-02 @CRM-762-03 @CRM-762-06 @muhabbat @crm-regression @ui-cc
  Scenario Outline: Verification Additional Start and End dates editing phone number #2
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I click on the Add Phone Number button on Contact Info Page
    When I provide valid data in all the fields and date as"<date>"
    And I click on Save button on add phone number page
    When I am navigated back to Contact Info page
    And I expend current phone number record and navigated to Edit Phone Number Page
    When I provide valid data in all the fields on Add Phone Number page
    And I set start Date as "<startDate>" and end Date "<endDate>"
    Then I verify edited phone number status as "<status>"
    Examples:
      | date   | startDate | endDate | status |
      | future | past      |[blank]| ACTIVE |
      | future | past      | future  | ACTIVE |
      | future | current   |[blank]| ACTIVE |
      | future | current   | future  | ACTIVE |


  @CRM-762 @CRM-762-04 @muhabbat @crm-regression @ui-cc
  Scenario: Verification of mandatory fields error messages on Edit Phone Number Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I click on the Add Phone Number button on Contact Info Page
    Then I do not provide any data in all the fields
    And I click on Save button on add phone number page
    Then I see all Add New Phone page mandatory fields error messages displayed

  @CRM-762 @CRM-762-05 @CRM-1047 @CRM-1047-04 @muhabbat @crm-regression @ui-cc
  Scenario: Verification of error messages for invalid data provided to the fields on Edit New Phone Number page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I click on the Add Phone Number button on Contact Info Page
    When I provide valid data in all the fields and date as"current"
    And I click on Save button on add phone number page
    When I am navigated back to Contact Info page
    And I expend current phone number record and navigated to Edit Phone Number Page
    When I provide invalid data for Phone Number, Start Date and End Date
    Then I see invalid field error message displayed below Phone Number, Start Date and End Date fields


  @CRM-762 @CRM-762-07 @muhabbat @crm-regression @ui-cc
  Scenario: Verification Additional Start and End dates editing phone number #3
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I click on the Add Phone Number button on Contact Info Page
    When I provide valid data in all the fields and date as"current"
    And I click on Save button on add phone number page
    When I am navigated back to Contact Info page
    And I expend current phone number record and navigated to Edit Phone Number Page
    When I provide valid data in all the fields on Add Phone Number page
    And I set start Date as "past" and end Date "future"
    Then I verify edited phone number information is displayed on Contact Info Page

  @CRM-762 @CRM-762-08 @muhabbat @crm-regression @ui-cc
  Scenario: Verification of Cancel Button functionality on edit Phone Number page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I click on the Add Phone Number button on Contact Info Page
    When I provide valid data in all the fields and date as"current"
    And I click on Save button on add phone number page
    When I am navigated back to Contact Info page
    And I expend current phone number record and navigated to Edit Phone Number Page
    When I provide valid data in all the fields on Add Phone Number page
    And I set Start and End Date and click on Cancel Button on Edit Phone Number Page
    And I verify warning is displayed with message "If you continue, all the captured information will be lost"
    And I click on continue button on warning message
    And I am navigated back to Contact Info page
    Then I see Phone number record on Contact Info Page has not been edited

