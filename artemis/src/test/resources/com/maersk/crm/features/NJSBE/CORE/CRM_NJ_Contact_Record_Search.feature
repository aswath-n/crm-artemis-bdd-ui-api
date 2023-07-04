Feature: CRM_NJ: Contact Record Search

  @CP-14366 @CP-14366-01 @ui-core-nj @nj-regression @crm-regression @aikanysh
  Scenario Outline: NJ: Display GetInsured Case & Consumer IDs in Contact Record Search Results
    Given I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched customer have First Name as "Jess" and Last Name as "Rosado"
    And I link the contact to an Consumer Profile with CSR Role
    And I save GetInsured Case Id and GetInsured Consumer Id
    And I save the contact as "<contactType>" and "<contactChannelType>" for NJ-SBE project
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by PhoneNumber under Advanced Search
    And I expand the found contact record and verify GetInsured Case Id and GetInsured Consumer Id are displayed
    Examples:
      | contactType | contactChannelType |
      | Inbound     | Phone              |

  @CP-13771 @CP-13771-01 @ui-core-nj @nj-regression @crm-regression @aikanysh
  Scenario Outline: NJ: Hide Email field in Contact Record Search & Expanded Search Results
    Given I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched customer have First Name as "Jess" and Last Name as "Rosado"
    And I link the contact to an Consumer Profile with CSR Role
    And I save the contact as "<contactType>" and "<contactChannelType>" for NJ-SBE project
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I verify Email Search Field is NOT displayed in Manual Contact Record Search
    And I search for an existing contact record by PhoneNumber under Advanced Search
    And I expand the found contact record and verify Email Field is NOT displayed in Manual Contact Record Search
    Examples:
      | contactType | contactChannelType |
      | Inbound     | Phone              |