Feature: Editing Address Information on Demographics Page

  @CRM-759 @CRM-759-01 @muhabbat @crm-regression @ui-cc
  Scenario: Verification of all fields on Edit new Address page
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    And I expend the Address record to verify all fields are present
    Then I verify "following" fields being marked mandatory on edit Address page
      | AddressLineOne |
      | City           |
      | State          |
      | Zip            |
      | AddressType    |
      | StartDate      |

  @CRM-759 @CRM-759-02 @muhabbat @crm-regression @ui-cc
  Scenario: Validation all the field on Edit new Address Page
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
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

  @CRM-759 @CRM-759-03 @muhabbat @crm-regression @ui-cc
  Scenario: Validation of error messages on edit address page when invalid format data entered
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    And I expend the Address Record on Contact Info Page
    When I enter invalid 4 digit value into Zip Code field
    And I enter invalid Date value into Start Date and End Date fields
    And I click on Save button on Edit Address Page
    Then I see error messages "ZIP CODE must be 5 or 9 characters" with "Invalid date format"

  @CRM-759 @CRM-759-04 @muhabbat @crm-regression @ui-cc
  Scenario: Validation of error messages on edit address page when mandatory fields are not filled in
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    And I expend the Address Record on Contact Info Page
    And I clear all previously populated Edit Address Page mandatory fields
    And I click on Save button on Edit Address Page
    Then I see "Please fill in the required fields." with all mandatory fields error messages displayed

  @CRM-759 @CRM-759-05-01 @muhabbat @crm-regression @ui-cc
  Scenario Outline: Address Status Validation
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I expend the Address Record on Contact Info Page
    And I edit all required fields along with "<start>" as "<end>"
    And I click on Save button on Edit Address Page
    Then I see edited address "<status>" on view address page
    Examples:
      | start  | end | status |
      | future |[blank]| FUTURE |
      | past   |[blank]| ACTIVE |

  @CRM-759 @CRM-759-05 @muhabbat @crm-regression @ui-cc
  Scenario Outline: Address Status Validation
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I expend the Address Record on Contact Info Page
    And I edit all required fields along with "<start>" as "<end>"
    When I choose inactivation reason from dropdown on Edit Address page
    And I click on Save button on Edit Address Page
    Then I see edited address "<status>" on view address page
    Examples:
      | start | end     | status   |
      | past  | present | ACTIVE   |
      | past  | future  | ACTIVE   |
      | past  | past2   | INACTIVE |

  @CRM-759 @CRM-759-06 @muhabbat @crm-regression @ui-cc
  Scenario: Validation of Save button functionality on Edit Address Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I expend the Address Record on Contact Info Page
    And I edit all mandatory fields with new data on Edit Address Page
    And I click on Save button on Edit Address Page
    Then I am navigated to the read-only view of saved information on Address Information page

  @CRM-759 @CRM-759-07 @muhabbat @crm-regression @ui-cc
  Scenario: Validation of Cancel button functionality on Edit Address Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I expend the Address Record on Contact Info Page
    And I edit all mandatory fields with new data on Edit Address Page
    And I click on Cancel button on Edit Address Page
    And I am navigated to the read-only view of previously captured address on Address Information page

  @CRM-759 @CRM-759-08 @muhabbat @crm-regression @crm-smoke @ui-cc
  Scenario: Validation of Inactivate Immediately functionality on Edit Address Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I verify current Address Record has "ACTIVE" status
    When I click on newly created address to navigate edit address page
    And I edit all mandatory fields with new data on Edit Address Page
    When I click on inactivate immediately button on Edit Address page
    And I click on Save button on Edit Address Page
    And I am navigated to the read-only view Address Information page
    Then I verify current Address Record has "INACTIVE" status

  @CRM-759 @CRM-759-10 @muhabbat @crm-regression @ui-cc
  Scenario: Validation of Address Source of Edited Address
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I expend the Address Record on Contact Info Page
    And I edit all mandatory fields with new data on Edit Address Page
    And I click on Save button on Edit Address Page
    When I am navigated to the read-only view of saved information on Address Information page
    Then I verify Address Source is "Consumer Reported"

  @CRM-1049 @CRM-1049-01 @aswath  @crm-regression @ui-cc
  Scenario: Validation of 'Reason for Inactivation' field when Inactivate Immediately is selected
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I verify current Address Record has "ACTIVE" status
    When I click on newly created address to navigate edit address page
    And I edit all mandatory fields with new data on Edit Address Page
    When I click on inactivate immediately check box on Edit Address page
    When I verify values in "reason" for inactivation dropdown
      | Added New Address |
      | Invalid Address   |
      | Returned Mail     |
    And I click on Save button on Edit Address Page
    And I am navigated to the read-only view of saved information on Address Information page
    Then I verify current Address Record has "INACTIVE" status

  @CRM-1049 @CRM-1049-03 @aswath @crm-regression @ui-cc
  Scenario: Validation of address status, after updating the end date
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I verify current Address Record has "ACTIVE" status
    When I click on newly created address to navigate edit address page
    And I update address and start date in Edit Address Page
    And I enter end date on Edit Address page
    Then I verify values in "reason" for inactivation dropdown
      | Invalid Address   |
      | Added New Address |
      | Returned Mail     |
    And I click on Save button on Edit Address Page
    And I am navigated to the read-only view Address Information page
    Then I verify current Address Record has "INACTIVE" status

  @CRM-1049 @CRM-1049-02 @aswath @crm-regression @ui-cc
  Scenario: Validation of error message on 'Reason for Inactivation field' on Edit Address Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I verify current Address Record has "ACTIVE" status
    When I click on newly created address to navigate edit address page
    And I edit all mandatory fields with new data on Edit Address Page
    When I click on inactivate immediately check box on Edit Address page
    And I click on Save button on Edit Address Page
    Then I verify mandatory error is displayed under reason for inactivation dropdown

  @CP-10856 @CP-10856-03 @CP-9058 @CP-9058-1 @kamil @crm-regression @ui-cc
  Scenario:Update the UI for Manually Capturing Address - Add Address
    Given I logged into CRM and click on initiate contact
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer to check profile details
    When I search for consumer to check profile details
    And I select the Demographic Info tab on the Consumer profile
    Then I am navigated in Demographic Info tab for view Contact Info
    And I see all add new address fields are displayed
    And I adding new address to Contact Info


  @CP-9058 @CP-9058-2 @kamil @crm-regression @ui-cc
  Scenario:Verify Capturing Address -fields are marked as mandatory
    Given I logged into CRM and click on initiate contact
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer to check profile details
    When I search for consumer to check profile details
    And I select the Demographic Info tab on the Consumer profile
    Then I am navigated in Demographic Info tab for view Contact Info
    And Verify fields are marked as mandatory
      | ADDRESS LINE 1 |
      | CITY           |
      | STATE          |
      | ZIP CODE       |
      | TYPE           |
      | START DATE     |


  @CP-9058 @CP-9058-3 @kamil @crm-regression @ui-cc
  Scenario:Verify mandatory fields cannot be left blank
    Given I logged into CRM and click on initiate contact
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer to check profile details
    When I search for consumer to check profile details
    And I select the Demographic Info tab on the Consumer profile
    Then I am navigated in Demographic Info tab for view Contact Info
    And Verify mandatory fields cannot be left blank
      | ADDRESS LINE 1 |
      | CITY           |
      | STATE          |
      | ZIP CODE       |
      | TYPE           |
      | START DATE     |


  @CP-10856 @CP-10856-03 @CP-9058 @CP-9058-4 @kamil @crm-regression @events @events-cc
  Scenario Outline: verify ADDRESS_SAVE_EVENT When I Add a new Address and successfully Save
    Given I logged into CRM and click on initiate contact
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer to check profile details
    When I search for consumer to check profile details
    And I select the Demographic Info tab on the Consumer profile
    Then I am navigated in Demographic Info tab for view Contact Info
    And I see all add new address fields are displayed
    And I adding new address to Contact Info
    Then I will take trace id for "ADDRESS_SAVE_EVENT"
    When I will get the Authentication token for "<projectName>" in "CRM"
    And Verify an "ADDRESS_SAVE_EVENT" is created with fields
    Then I will verify subscriber received "ADDRESS_SAVE_EVENT" event for "DPBI" is created
    Examples:
      | projectName |
      |[blank]|

  @CP-10856 @CP-10856-01 @Beka @crm-regression @events-cc
  Scenario:  The system will publish an ADDRESS_UPDATE_EVENT for the prior end-dated address for reporting, reflecting the data changes to the address
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    When I click on Add new address button on Contact Info Tab Page
    And I edit all fields with past date on Edit Address Page within a case
    And I click on Save button on Edit Address Page
    Then I mouse over on the Status of the newly added Address


  @CP-10856 @CP-10856-02 @Beka @crm-regression @ui-cc
  Scenario: Verify error messages on case contact address add page
    Given I logged into CRM
    And I click on initiate a contact button
    And I searched customer have First Name as "Johnathan" and Last Name as "Harvey"
    And I link the contact to an existing Case or Consumer Profile
    And I select the Demographic Info tab on the Consumer profile
    Then I am navigated in Demographic Info tab for view Contact Info
    And I adding new "Mailing" address to Contact Info same address
    And I click on Save button on Edit Address Page
    Then I see error messages "Address type is already active for selected consumer" displayed

