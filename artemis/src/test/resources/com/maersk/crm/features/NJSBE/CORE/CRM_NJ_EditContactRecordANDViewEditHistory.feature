Feature:NJ Edit Contact Record & View Edit History

  @CP-14278 @CP-14278-1.1 @ui-core-nj @nj-regression @kamil
  Scenario: Verify Reason for Edit dropdown for Unidentified Contact Record
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Contact Record search
    And I searched customer with Contact Record ID "8341"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I verify popup with Error message displayed for "Correcting Case/Consumer Link"

  @CP-14278 @CP-14278-1.2 @ui-core-nj @nj-regression @kamil
  Scenario: Verify Reason for Edit dropdown for General or Unidentified Contact Record
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Contact Record search
    And I searched customer with Contact Record ID "8341"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I verify popup with Error message displayed for "Correcting Third Party Information"

  @CP-14278 @CP-14278-2 @ui-core-nj @nj-regression @kamil
  Scenario: Verify Contact Record with a Reason for Edit of “Correcting Case/Consumer link“
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Contact Record search
    And I searched customer with Contact Record ID "11696"
    When I click on first Contact Record ID on Contact Record
    When I click on edit icon the Contact Details page
    Then I choose Reason for Edit dropdown populated with value
      | Correcting Case/Consumer Link |
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab
    And Verify EDIT History with Reason for Edit "Correcting Case/Consumer link"

  @CP-14278 @CP-14278-3 @ui-core-nj @nj-regression @kamil
  Scenario: Verify Contact Record with a Reason for Edit of “Correcting Contact Details“
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Contact Record search
    And I searched customer with Contact Record ID "24409"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I choose Reason for Edit dropdown populated with value
      | Correcting Contact Details |
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab
    And Verify EDIT History with Reason for Edit "Correcting Contact Details"

  @CP-14278 @CP-14278-4 @ui-core-nj @nj-regression @kamil
  Scenario: Verify Contact Record with a Reason for Edit of “Adding Contact Reason/Action“
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Contact Record search
    And I searched customer with Contact Record ID "24380"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I choose Reason for Edit dropdown populated with value
      | Adding Contact Reason/Action |
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab
    And Verify EDIT History with Reason for Edit "Adding Contact Reason/Action"

  @CP-14278 @CP-14278-5 @ui-core-nj @nj-regression @kamil
  Scenario: Verify Contact Record with a Reason for Edit of “Correcting Contact Reason/Action“
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Contact Record search
    And I searched customer with Contact Record ID "8327"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I choose Reason for Edit dropdown populated with value
      | Correcting Contact Reason/Action |
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab
    And Verify EDIT History with Reason for Edit "Correcting Contact Reason/Action"

  @CP-14278 @CP-14278-6 @ui-core-nj @nj-regression @kamil
  Scenario: Verify Contact Record with a Reason for Edit of “Adding Additional Comment“
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Contact Record search
    And I searched customer with Contact Record ID "24341"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I choose Reason for Edit dropdown populated with value
      | Adding Additional Comment |
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab
    And Verify EDIT History with Reason for Edit "Adding Additional Comment"

  @CP-14278 @CP-14278-7 @ui-core-nj @nj-regression @kamil
  Scenario: Verify Contact Record with a Reason for Edit of “Correcting Additional Comment“
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Contact Record search
    And I searched customer with Contact Record ID "24246"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I choose Reason for Edit dropdown populated with value
      | Correcting Additional Comment |
    And I edit the saved Additional comments
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab
    And Verify EDIT History with Reason for Edit "Correcting Additional Comment"

  @CP-14278 @CP-14278-8 @ui-core-nj @nj-regression @kamil
  Scenario: Verify Contact Record with a Reason for Edit of “Correcting Third Party Information“
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Contact Record search
    And I searched customer with Contact Record ID "24411"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I choose Reason for Edit dropdown populated with value
      | Correcting Third Party Information |
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab
    And Verify EDIT History with Reason for Edit "Correcting Third Party Information BLCRM"

  @CP-14278 @CP-14278-9 @ui-core-nj @nj-regression @kamil
  Scenario: Verify Edit History is descending order
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Contact Record search
    And I searched customer with Contact Record ID "24343"
    When I click on first Contact Record ID on Contact Record
    Then I navigate to the Edit History page
    Then Verify in Edit History page EditedOn is descending order

  @CP-18285 @CP-18285-11 @ui-core-nj @nj-regression @khazar
  Scenario: Verify Field Dependencies on channel field for inbound contact type
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Contact Record search
    And I click on the Contact Type "Inbound MultiSelect"
    When I searched customer have First Name as "Bruce" and Last Name as "Banner"
    When I click on first Contact Record ID on Contact Record
    When I click on edit icon the Contact Details page
    And I verify fields dependencies on channel field for inbound contact type
      | Phone |

  @CP-18285 @CP-18285-12 @ui-core-nj @nj-regression @khazar
  Scenario: Verify Field Dependencies on channel field for outbound contact type
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Contact Record search
    And I click on the Contact Type "Outbound MultiSelect"
    When I searched customer have First Name as "Bruce" and Last Name as "Banner"
    When I click on first Contact Record ID on Contact Record
    When I click on edit icon the Contact Details page
    And I verify fields dependencies on channel field for outbound contact type
      | Phone |

@CP-46054 @ui-core-nj @crm-regression @khazar
Scenario: Adding and Editing Additional Comments for NJ-SBE
Given I logged into CRM and select a project "NJ-SBE"
And I minimize Genesys popup if populates
When I click on initiate contact record
When I searched customer have First Name as "Bruce" and Last Name as "Banner"
And I click on Search Button on Search Consumer Page
And I click validate&link the contact to an existing Case or Consumer Profile for NJ-SBE
And I choose a contact reason and contact action from dropdown list for NJ-SBE
And I enter additional comments "First additional comment"
When I enter contact phone number "1327894561"
And I select contact program type as "Other"
Then I click on the contact dispotions "Complete"
When I click End Contact
And I click save button Active contact
When I navigate to manual contact record search page
And I search contact record with phone number and click on the first record
When I click on edit button on contact details tab
Then I verify that saved additional comments is displayed
Then I will edit the saved additional comments and the new value is saved and displayed