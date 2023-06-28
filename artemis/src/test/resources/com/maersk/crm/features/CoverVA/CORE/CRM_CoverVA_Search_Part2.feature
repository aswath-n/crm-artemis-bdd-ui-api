Feature: CoverVA: Contact Record Search Part 2

  @CP-18830 @CP-18830-01 @kamil @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario Outline: Searching with a Consumer ID as a Criterion
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I navigate to Contact Record search
    And I searched with "<SearchConsumer>" Consumer Id "<ConsumerId>"
    Then Verify "<SearchConsumer>" and Consumer Id "<ConsumerId>" is visible
    Examples:
      | SearchConsumer | ConsumerId |
      | Internal       | 121302     |
      | MMIS           | 358901     |
      | VaCMS          | 74546564   |



  @CP-16854 @CP-16854-01 @ui-cc-cover-va @crm-regression @chopa
  Scenario: CoverVA: Configure MMIS Consumer ID details
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I navigate to Case Consumer search
    And I click on Call Managment window
    When I pass 24 characters in total I verify MMIS field doesn't accept more 12 characters

  @CP-16854 @CP-16854-02 @ui-cc-cover-va @crm-regression @chopa      #failing due to defect CP-30102
  Scenario: CoverVA: Configure MMIS Consumer ID details in Contact Record Page
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I navigate to manual contact record search page
    And I click on Call Managment window
    When I pass 24 characters in total I verify MMIS field doesn't accept more 12 characters in Case and Consumer page

  @CP-16854 @CP-16854-03 @ui-cc-cover-va @crm-regression @chopa
  Scenario: CoverVA: Configure MMIS Consumer ID details in Active Contact Record Search
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I click on Call Managment window
    When I pass 24 characters in total I verify MMIS field doesn't accept more 12 characters

  @CP-18830 @CP-18830-02 @kamil @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: Searching without any Consumer ID as a Criterion
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I navigate to Contact Record search
    And I searched customer with Contact Record ID "402713"
    Then Verify Search Results will display the (Internal) Consumer ID column and Contact Record ID "402713"


  @CP-21191 @CP-21191-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @chopa
  Scenario: CoverVA: Configure Manual Contact Search Fields - Application ID field rules
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I navigate to manual contact record search page
    Then Clicking Advanced Search on Contact Record search in CoverVa
    Then I verify the presence of "Application ID" field
    Then I verify "Application ID" field accepts 9 alphanumeric characters in total
    Then I verify "Application ID" field doesn't accepts special characters


  @CP-21191 @CP-21191-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @chopa
  Scenario: CoverVA: Configure Manual Contact Search Fields - Advanced Search by Application ID
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I searched customer have First Name as "AikaIVR" and Last Name as "BegiIVR"
    And I link the contact to an existing Case or Consumer Profile
    Then I click on Call Managment button
    When I save the contact and click "Yes" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I searching for contact with Application ID
    Then I verify Application ID matches the contact


  @CP-21951 @CP-21951-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @chopa
  Scenario: CoverVA: Configure Manual Contact Search Fields - Electronic Signature field rules
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I navigate to manual contact record search page
    Then Clicking Advanced Search on Contact Record search in CoverVa
    Then I verify Electronic Signature Captured checkbox is displayed as a multi-select drop with following options
      | N/A |
      | No  |
      | Yes |

    #Failing due to CP-22643
  @CP-21951 @CP-21951-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @chopa
  Scenario Outline: CoverVA: Configure Manual Contact Search Fields - Expanded Search Result
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    Then I click on Call Managment button
    When I save the contact and click "<elecSignOption>" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I search for contact with Electronic Signature "<elecSignOption>"
    Then I verify Electronic Signature is captured in expanded contact record search results
    Then I verify Electronic Signature is captured in View page with options "<elecSignOption>"
    When I click on edit icon the Contact Details page
    Then I verify Electronic Signature is captured in Edit page with options "<elecSignOption>"
    Examples:
      |elecSignOption|
      | N/A          |
      | No           |
      | Yes          |