Feature: Adding / Editing Address Information from Manual View

  @CP-455 @CP-455-01 @muhabbat  @crm-regression @ui-cc
  Scenario: Verification of all fields on Edit new Address page from Manual View
    Given I logged into CRM
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields consumer First Name as "Ethan" and Last Name as "Hunt"
    And I navigate to Contact Info Page within a case
    And I expend the Address record to verify all fields are present
    Then I verify "following" fields being marked mandatory on edit Address page
      | AddressLineOne |
      | City           |
      | State          |
      | Zip            |
      | AddressType    |
      | StartDate      |

  @CP-455 @CP-455-02 @muhabbat @crm-regression  @ui-cc
  Scenario: Validation all the field on Edit Address Page from Manual View
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields for previously created consumer
    And I expend the Address Record on Contact Info Page
    And I see "addressLine1" field on edit Address page accept 50 characters in total
    And I see "addressLine2" field on edit Address page accept 50 characters in total
    And I see "city" field on edit Address page accept 30 characters in total
    And I should be able to see all states in State dropdown on edit Address page are available
    And I see "<Zip Code>" field on edit Address page accepts up to 9 digits
    And I should see following dropdown options for "addressType" field on edit Address page displayed
      | Physical |
      | Mailing  |
    And I see Address Start Date and End Date on edit Address page in MM/DD/YYYY format
    Then I see Address status on edit Address page is "ACTIVE" or "INACTIVE"


  @CP-455 @CP-455-03 @muhabbat @crm-regression @ui-cc
  Scenario: Validation of error messages on edit address page from Manual View when invalid format data entered
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields for previously created consumer
    And I expend the Address Record on Contact Info Page
    When I enter invalid 2 digit value into Zip Code field
    And I enter invalid Date value into Start Date and End Date fields
    And I click on Save button on Edit Address Page
    Then I see error messages "ZIP CODE must be 5 or 9 characters" with "Invalid date format"

  @CP-455 @CP-455-04 @muhabbat @crm-regression  @ui-cc
  Scenario: Validation of error messages on edit address page from Manual View when mandatory fields are not filled in
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    #And I navigate to Manual Consumer search page
    And I click on the Demographic Info Tab
    And I expend the Address Record on Contact Info Page
    And I clear all previously populated Edit Address Page mandatory fields
    And I click on Save button on Edit Address Page
    Then I see "Please fill in the required fields." with all mandatory fields error messages displayed

  @CP-455 @CP-455-05 @muhabbat @crm-regression  @ui-cc
  Scenario Outline: Edited Address Status Validation from Manual View
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields for previously created consumer
    And I expend the Address Record on Contact Info Page
    And I edit all required fields along with "<start>" as "<end>"
    And I click on Save button on Edit Address Page
    Then I see edited address "<status>" on view address page
    Examples:
      | start  | end | status |
      | future |[blank]| FUTURE |
      | past   |[blank]| ACTIVE |

  @CP-455 @CP-455-05-1 @muhabbat @crm-regression @ui-cc
  Scenario Outline: Edited Address Status Validation from Manual View
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields for previously created consumer
    And I expend the Address Record on Contact Info Page
    And I edit all required fields along with "<start>" as "<end>"
    And I click on Save button on Edit Address Page
    Then I see edited address "<status>" on view address page manual consumer search
    Examples:
      | start | end     | status   |
      | past  | present | ACTIVE   |
      | past  | future  | ACTIVE   |

  @CP-455 @CP-455-05-2 @muhabbat @crm-regression @ui-cc
  Scenario Outline: Edited Address Status Validation from Manual View
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields for previously created consumer
    And I expend the Address Record on Contact Info Page
    And I edit all required fields along with "<start>" as "<end>"
    When I choose inactivation reason from dropdown on Edit Address page
    And I click on Save button on Edit Address Page
    Then I see edited address "<status>" on view address page manual consumer search
    Examples:
      | start | end     | status   |
      | past  | past2   | INACTIVE |

  @CP-455 @CP-455-06 @muhabbat @crm-regression @ui-cc
  Scenario: Validation of Save button functionality on Edit Address Page from Manual View
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields for previously created consumer
    And I expend the Address Record on Contact Info Page
    And I edit all mandatory fields with new data on Edit Address Page
    And I click on Save button on Edit Address Page
    Then I am navigated to the read-only view of saved information on Address Information page

  @CP-455 @CP-455-07 @muhabbat @crm-regression @ui-cc
  Scenario: Validation of Cancel button functionality on Edit Address Page from Manual View
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields for previously created consumer
    And I expend the Address Record on Contact Info Page
    And I edit all mandatory fields with new data on Edit Address Page
    And I click on Cancel button on Edit Address Page
    And I am navigated to the read-only view of previously captured address on Address Information page

  @CP-455 @CP-455-08 @muhabbat @crm-regression @ui-cc
  Scenario: Validation of Inactivate Immediately functionality on Edit Address Page from Manual View
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields for previously created consumer
    And I verify current Address Record has "ACTIVE" status
    And I expend the Address Record on Contact Info Page
    And I edit all mandatory fields with new data on Edit Address Page
    When I click on inactivate immediately button on Edit Address page
    And I click on Save button on Edit Address Page
    And I am navigated to the read-only view of saved information on Address Information page
    Then I verify current Address Record has "INACTIVE" status


  @CP-455 @CP-455-10 @muhabbat @crm-regression @ui-cc
  Scenario: Validation of Address Source of Edited Address from Manual View
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields for previously created consumer
    And I expend the Address Record on Contact Info Page
    And I edit all mandatory fields with new data on Edit Address Page
    And I click on Save button on Edit Address Page
    When I am navigated to the read-only view of saved information on Address Information page
    Then I verify Address Source is "Consumer Reported"

  @CP-456 @CP-456-01 @muhabbat @crm-regression @ui-cc
  Scenario: Verification all fields on Add new address page are displayed
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields for previously created consumer
    When I click on Add new address button on Contact Info Tab Page
    Then I see all add new address fields are displayed

   @CP-456 @CP-456-02 @muhabbat @crm-regression @ui-cc
  Scenario:Validation of all fields on Add new Address Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields for previously created consumer
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


  @CP-456 @CP-456-03 @muhabbat @crm-regression @ui-cc
  Scenario Outline: Validate added Address status1
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields for previously created consumer
    When I click on Add new address button on Contact Info Tab Page
    And I edit all required fields along with "<start>" as "<end>"
    And I click on Save button on Edit Address Page
    Then I see address "<status>" on view address page
    Examples:
      | start  | end     | status   |
      | future |[blank]| FUTURE   |
#      | past   | present | ACTIVE   |
#      | past   | future  | ACTIVE   |
#      | past   |[blank]| ACTIVE   |
#      | past   | past2   | INACTIVE |


  @CP-456 @CP-456-04 @muhabbat @crm-regression @ui-cc
  Scenario:Validate error messages are displayed when invalid format data used
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields for previously created consumer
    When I click on Add new address button on Contact Info Tab Page
    When I enter invalid 4 digit value into Zip Code field
    And I enter invalid Date value into Start Date and End Date fields
    And I click on Save button on Edit Address Page
    Then I see error messages "ZIP CODE must be 5 or 9 characters" with "Invalid date format"

  @CP-456 @CP-456-05 @muhabbat @crm-regression @ui-cc
  Scenario: Validate error messages are displayed when mandatory fields are not filled in
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields for previously created consumer
    When I click on Add new address button on Contact Info Tab Page
    And I click on Save button on Edit Address Page
    Then I see "Please fill in the required fields." with all mandatory fields error messages displayed

#  @CP-456 @CP-456-06 @muhabbat @crm-regression @ui-cc This functionality is retired with "Last Updated by" implementation
  Scenario: Capture Address Source
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields for previously created consumer
    When I click on Add new address button on Contact Info Tab Page
    And I edit all mandatory fields with new data on Edit Address Page
    And I click on Save button on Edit Address Page
    When I am navigated to the read-only view of saved information on Address Information page
    Then I verify Address Source is "Manual User Captured"

  @CP-456 @CP-456-07 @ui-cc
  Scenario:Validate Error is displayed if Address line one has only special characters on Demo-Contact Info Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields for previously created consumer
    When I click on the Add button
    And I enter only special character in Address Line one field on the Demographic page
    Then I verify error is displayed for the Address Line one on the Demographic page

  @CP-456 @CP-456-08 @muhabbat @crm-regression @ui-cc
  Scenario:Validate Error is displayed if Address line one has only special characters on Demo-Contact Info Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Manual Consumer search page
    And I enter Manual Search criteria fields for previously created consumer
    When I click on the Add button
    And I enter only special character in Address Line two field on the Demographic page
    Then I verify error is displayed for the Address Line two on the Demographic page