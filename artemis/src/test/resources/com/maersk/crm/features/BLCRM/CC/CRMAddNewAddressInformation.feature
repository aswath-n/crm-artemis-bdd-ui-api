Feature: Add New Address Information
#refactored by Muhabbat
  @CRM-757  @CRM-757-01 @shilpa #refactored by Muhabbat
  Scenario: Verification all fields on Add new address page are displayed
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    When I click on Add new address button on Contact Info Tab Page
    Then I see all add new address fields are displayed

  @CRM-757 @CRM-757-02 @shilpa @muhabbat #refactored by Muhabbat
  Scenario:Validation of all fields on Add new Address Page
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    When I click on Add new address button on Contact Info Tab Page
    And I see "addressLine1" field on edit Address page accept 50 characters in total
    And I see "addressLine2" field on edit Address page accept 50 characters in total
    And I see "city" field on edit Address page accept 30 characters in total
    And I should be able to see all states in State dropdown on edit Address page are available
    And I see "<Zip Code>" field on edit Address page accepts up to 9 digits
    And I should see following dropdown options for "addressType" field on edit Address page displayed
      | Physical |
      | Mailing  |
    And I see Address Start Date and End Date on edit Address page in MM/DD/YYYY format


  @CRM-757 @CRM-757-03 @CRM-757-04 @CRM-757-07 @shilpa @muhabbat @ui-cc #refactored by Muhabbat
  Scenario Outline: Validate added Address status
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    #And I navigate to Contact Info Page
    And I click on the Demographic Info Tab
    When I click on Add new address button on Contact Info Tab Page
    And I edit all required fields along with "<start>" as "<end>"
    And I click on Save button on Edit Address Page
    Then I see address "<status>" on view address page
    Examples:
      | start  | end     | status   |
      | future |[blank]| FUTURE   |
      | past   | present | ACTIVE   |
      | past   |[blank]| ACTIVE   |
      | past   | past2   | INACTIVE |

  @CRM-757 @CRM-757-03 @CRM-757-04 @CRM-757-07  @muhabbat  @ui-cc-smoke
  Scenario: Validate added Address status
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I click on the Demographic Info Tab
    When I click on Add new address button on Contact Info Tab Page
    And I edit all required fields along with "past" as "future"
    And I click on Save button on Edit Address Page
    Then I see address "ACTIVE" on view address page

  @CRM-757 @CRM-757-03 @CRM-757-04 @CRM-757-07 @shilpa @muhabbat @ui-cc #refactored by Muhabbat
  Scenario Outline: Validate added Address status
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    #And I navigate to Contact Info Page
    And I click on the Demographic Info Tab
    When I click on Add new address button on Contact Info Tab Page
    And I edit all required fields along with "<start>" as "<end>"
    And I click on Save button on Edit Address Page
    Then I see address "<status>" on view address page
    Examples:
      | start  | end     | status   |
      | future |[blank]| FUTURE   |
      | past   | present | ACTIVE   |
      | past   | future  | ACTIVE   |
      | past   |[blank]| ACTIVE   |
      | past   | past2   | INACTIVE |

  @CRM-757 @CRM-757-05 @shilpa @ui-cc #refactored by Muhabbat
  Scenario:Validate error messages are displayed when invalid format data used
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    When I click on Add new address button on Contact Info Tab Page
    When I enter invalid 4 digit value into Zip Code field
    And I enter invalid Date value into Start Date and End Date fields
    And I click on Save button on Edit Address Page
    Then I see error messages "ZIP CODE must be 5 or 9 characters" with "Invalid date format"

  @CRM-757 @CRM-757-06 #refactored by Muhabbat
  Scenario: Validate error messages are displayed when mandatory fields are not filled in
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    When I click on Add new address button on Contact Info Tab Page
    And I click on Save button on Edit Address Page
    Then I see "Please fill in the required fields." with all mandatory fields error messages displayed

  @CRM-757 @CRM-757-09 @muhabbat @crm-regression @crm-smoke @ui-cc
  Scenario: Capture Address Source
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    When I click on Add new address button on Contact Info Tab Page
    And I edit all mandatory fields with new data on Edit Address Page within a case
    And I click on Save button on Edit Address Page
    Then I verify on newly created Address Source is "Consumer Reported"

  @CRM-1296 @CRM-1296-03 @shruti
  Scenario:Validate Error is displayed if Address line one has only special characters on Demo-Contact Info Page
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    When I click on Add new address button on Contact Info Tab Page
    And I enter only special character in Address Line one field on the Demographic page
    Then I verify error is displayed for the Address Line one on the Demographic page

  @CRM-1296 @CRM-1296-04 @shruti #@crm
  Scenario:Validate Error is displayed if Address line two has only special characters on Demo-Contact Info Page
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    When I click on Add new address button on Contact Info Tab Page
    And I enter only special character in Address Line two field on the Demographic page
    Then I verify error is displayed for the Address Line two on the Demographic page

  @CP-33355 @CP-33355-01 @crm-regression @ui-cc @muhabbat
  Scenario: Verify County field is optional for Address on Create Consumer Screen
    Given I logged into CRM and click on initiate contact
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    And I ensure County filed is optional
    When I click on Create Consumer Button
    Then I see new consumer is created and has a unique Consumer ID


  @CP-33355 @CP-33355-02 @crm-regression @ui-cc @muhabbat
  Scenario: Verify County field is optional for Edit Consumer Address
    Given I logged into CRM and click on initiate contact
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    And I ensure County filed is optional
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on newly created address to navigate edit address page
    And I ensure County filed is optional
    And I see "county" field accept 30 characters in total
    And I ensure County filed is optional
    Then I verify Consumer updated with County field is optional

  @CP-33355 @CP-33355-03 @crm-regression @ui-cc @muhabbat
  Scenario: Verify County field is optional in Add Address Profile
    Given I logged into CRM and click on initiate contact
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on Add new address button on Contact Info Tab Page
    And I see "county" field accept 30 characters in total
    And I ensure County filed is optional
    And I verify new address created with County as optional