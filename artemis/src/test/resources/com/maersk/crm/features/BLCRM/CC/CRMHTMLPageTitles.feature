Feature: HTML Page Titles

@CRM-710 @CRM-710-01 @shruti @crm-regression @ui-cc
Scenario: Verify Page title for Dashboard Screen
  Given I logged into CRM
  Then I verify page title is displayed as "Dashboard"
  And I click case consumer search tab
  Then I verify page title is displayed as "Case/Consumer Search"


@CRM-710 @CRM-710-02 @shruti @crm-regression @ui-cc
Scenario: Verify Page title for Active Contact - General, Third Party & Unidentified
  Given I logged into CRM and click on initiate contact
  Then I verify page title is displayed as "Active General Contact"
  When  I select "THIRD PARTY CONTACT" Contact Record Type
  Then I verify page title is displayed as "Active Third Party Contact"
  When  I select "UNIDENTIFIED CONTACT" Contact Record Type
  Then I verify page title is displayed as "Active Unidentified Contact"


  @CRM-710 @CRM-710-03 @shruti @crm-regression @ui-cc
  Scenario: Verify Page title for Linked Active Contact - General
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    And I link the contact to an existing Case or Consumer Profile
    Then I verify page title is displayed as "Active Linked General Contact"

  @CRM-710 @CRM-710-03-2 @shruti @crm-regression #fails due to CP-14543
  Scenario: Verify Page title for Linked Active Contact -  Third Party
    Given I logged into CRM and click on initiate contact
    When  I select "THIRD PARTY CONTACT" Contact Record Type
    And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    And I link the contact to an existing Case or Consumer Profile
    Then I verify page title is displayed as "Active Linked Third Party Contact"


  @CRM-710 @CRM-710-04 @shruti @crm-regression @ui-cc
  Scenario: Verify Page title for Demographic Info page- Members
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    And I link the contact to an existing Case or Consumer Profile
    Then I click on the Demographic Info Tab
    Then I verify page title is displayed as "Case View - Demographic Info - Members"

  @CRM-710 @CRM-710-05 @shruti @crm-regression @ui-cc
  Scenario: Verify Page title for Demographic Info page - Contact Info
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    And I link the contact to an existing Case or Consumer Profile
    And I navigate to Contact Info Page within a case
    Then I verify page title is displayed as "Case View - Demographic Info - Contact Info"


  @CRM-710 @CRM-710-06 @shruti @crm-regression @ui-cc
  Scenario: Verify Page title for Case & Contact Details Page
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    Then I verify page title is displayed as "Case View - Case & Contact Details"


  @CRM-710 @CRM-710-07 @shruti @crm-regression @ui-cc #not refactor due to defect
  Scenario: Verify Page title for View & Edit General Contact
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    When  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | Inbound |
    When  I should see following dropdown options for "contact channel" field displayed
      | Phone |
    And I capture current contact phone number
    And I select program type "Program A"
    And I close the current Contact Record and re-initiate a new Contact Record
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile "consumerId"
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I select the latest contact record with type "General"
    Then I verify page title is displayed as "View General Contact Details"
    And I click on edit icon the Contact Details page
    Then I verify page title is displayed as "Edit General Contact Details"

  @CRM-710 @CRM-710-08 @shruti @crm-regression @ui-cc #not refactored due third party record
  Scenario: Verify Page title for View & Edit Third Party Contact
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Emma" and Last Name as "Jones"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I select the latest contact id with type "Third Party"
    Then I verify page title is displayed as "View Third Party Contact Details"
    And I click on edit icon the Contact Details page
    Then I verify page title is displayed as "Edit Third Party Contact Details"

  @CRM-710 @CRM-710-09 @shruti @crm-regression @ui-cc
  Scenario: Verify Page title for Demographic Info page - Contact Info-Add Address
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    And I link the contact to an existing Case or Consumer Profile
    And I navigate to Contact Info Page within a case
    When I click on Add new address button on Contact Info Tab Page
    Then I verify page title is displayed as "Case View - Add/Edit Address Information"


  @CRM-710 @CRM-710-10 @shruti @crm-regression @ui-cc
  Scenario: Verify Page title for Demographic Info page - Contact Info-Add Email Address
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    And I link the contact to an existing Case or Consumer Profile
    And I navigate to Contact Info Page within a case
    When I click on the Add button for Email Address
    Then I verify page title is displayed as "Case View - Add/Edit Email Address Information"


  @CRM-710 @CRM-710-11 @shruti @crm-regression @ui-cc
  Scenario: Verify Page title for Demographic Info page - Members tab-Add PI
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Emma" and Last Name as "Jones"
    And I link the contact to an existing Case or Consumer Profile
    Then I click on the Demographic Info Tab
    And I click on the Add button for primary individual
    #And I verify primary individual section displayed
    Then I verify page title is displayed as "Add/Edit Primary Individual Information"

  @CRM-710 @CRM-710-12 @shruti @crm-regression @ui-cc
  Scenario: Verify Page title for Demographic Info page - Members tab-Add CM
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Emma" and Last Name as "Jones"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    When I click on Add Button for Case Member
    Then I verify page title is displayed as "Add/Edit Case Member Information"

  @CRM-710 @CRM-710-13 @shruti @crm-regression @ui-cc
  Scenario: Verify Page title for Demographic Info page - Members tab-Add AR
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    And I link the contact to an existing Case or Consumer Profile
    Then I click on the Demographic Info Tab
    And I click on the Add button for Authorized Representative
    Then I verify page title is displayed as "Add/Edit Authorized Representative Information"








