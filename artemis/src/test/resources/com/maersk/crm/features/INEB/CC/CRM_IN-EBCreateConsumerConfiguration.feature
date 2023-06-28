Feature: IN-EB Configured Create Consumer

  @CP-23374 @CP-23374-01 @CP-25310 @CP-25310-01 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Configure Create Consumer expected fields
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I see all fields on IN-EB Create Consumer present

  @CP-23374 @CP-23374-02 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Configure Consumer validate fields are not displayed
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I don't see unexpected "fields" on IN-EB Create Consumer present
      | Email                     |
      | MI                        |
      | Correspondence preference |
      | Written Language          |
      | Spoken Language           |
      | optInOuts                 |

  @CP-23374 @CP-23374-03 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Configure Consumer fax option should not be a Phone Type
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I don't see unexpected "fax" option should not be a Phone Type on IN-EB Create Consumer present


  @CP-23374 @CP-23374-04 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Verification of mandatory fields on Create Consumer UI
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify "following" fields being marked mandatory
      | Consumer First Name |
      | Consumer Last Name  |
      | Consumer DOB        |
      | Gender              |
      | SSN                 |

  @CP-23374 @CP-23374-05 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Verification of Cancel Button functionality on Create Consumer screen
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I am navigated back to Search Contact Record UI page

  @CP-23374 @CP-23374-06 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Verification of Save Consumer Button functionality on Create Consumer Screen
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer for IN-EB
    When I click on Create Consumer Button
    Then I see new consumer is created and has a unique Consumer ID

  @CP-23374 @CP-23374-07 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Verification Address fields accept up to 50 characters on Create Consumer screen
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I see "addressLine1" field accept 50 characters in total
    And I see "addressLine2" field accept 50 characters in total


  @CP-23374 @CP-23374-08 @muhabbat @crm-regression @ui-cc-in
  Scenario: Verification all states in State Dropdown are available
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I should be able to see all states in State dropdown are available

  @CP-23374 @CP-23374-09 @CP-25310-02 @muhabbat @crm-regression @ui-cc-in
  Scenario: Verification Zip consist out of Numbers only
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I see "<Zip Code>" field accepts up to 9 digits

  @CP-23374 @CP-23374-10 @CP-25310-03 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Verification Zip field has 5 digits required only
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I see 5 digits required and 9 digits optional format


