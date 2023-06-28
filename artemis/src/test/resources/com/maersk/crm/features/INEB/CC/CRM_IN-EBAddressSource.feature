Feature: IN-EB Address Source Consumer reported and State reported

  @CP-26937 @CP-26937-01 @CP-24066 @CP-24066-01 @crm-regression @ui-cc-in
  Scenario: IN-EB: Verification of Address Source field has all expected options
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I see "Address Source" field on Create Consumer UI has all expected options on IN-EB


  @CP-26937 @CP-26937-02 @CP-24066 @CP-23375-01 @CP-24066-02 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Verification Address Source field has all expected options Add address within a case
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    And I searched customer have First Name as "Something" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab for contact and click on add for "address"
    And I see source field has all expected options on Add Edit address IN-EB

  @CP-26937 @CP-26937-03 @CP-24066 @CP-23375-02 @CP-24066-03 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Verification Address Source field has all expected options edit address within a case
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "FnsFOuX" and Last Name as "LnnIWam"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    And I expend the Address Record on Contact Info Page
    And I see source field has all expected options on Add Edit address IN-EB


  @CP-26937 @CP-26937-04 @CP-24066 @CP-23375-01 @CP-24066-04 @CP-28402 @CP-28402-01 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Verification Address Source field has all expected options edit Address for consumer
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    And I searched customer have First Name as "Harry" and Last Name as "potter"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I expend the Address Record on Contact Info Page
    And I see source field has all expected options on Add Edit address IN-EB

  @CP-30538 @CP-30538-01 @CP-28402 @CP-28402-02 @crm-regression @ui-cc-in @Beka
  Scenario: IN-EB: Display Report label for State Dropdown in create consumer page
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I able to see all state from state dropdown in format USPS two character abbreviation in the Report Label column

  @CP-30538 @CP-30538-02 @CP-28402 @CP-28402-03 @crm-regression @ui-cc-in @Beka
  Scenario: IN-EB: Display Report label for State Dropdown in case add address page
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    And I searched customer have First Name as "Something" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab for contact and click on add for "address"
    Then I able to see all state from state dropdown in format USPS two character abbreviation in the Report Label column

  @CP-30538 @CP-30538-03 @CP-28402 @CP-28402-04 @crm-regression @ui-cc-in @Beka
  Scenario: IN-EB: Display Report label for State Dropdown in consumer profile add address page
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    And I searched customer have First Name as "Something" and Last Name as "Fortest"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on Add new address button on Contact Info Tab Page
    Then I able to see all state from state dropdown in format USPS two character abbreviation in the Report Label column

  @CP-30250 @CP-30250-01 @crm-regression @ui-cc-in @chopa
  Scenario: IN-EB: Verify new Address for Same Type and Consumer as State Reported cannot be created
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    And I searched customer have First Name as "LnHEWEY" and Last Name as "XrRdpda"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I navigate to Contact Info Tab
    And I click on Add new address button on Contact Info Tab Page
    And I edit all mandatory fields with new data on Edit Address Page within a case for INEB
    And I click on Save button
    And I verify error message "Address type is already active for selected consumer"

