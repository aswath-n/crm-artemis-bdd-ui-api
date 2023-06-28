Feature: CoverVA Contact Record Configurations Part 6

  @CP-21528 @CP-21528-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @chopa
  Scenario Outline: CoverVA: Electronic Signature Displays Based on Contact Reason in General Contact Inbound Call
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I click on Call Managment window
    And I choose Contact Reason for Cover VA "<ContactReasons>" and associated Contact Action
    Then I verify Electronic Signature Captured checkbox is displayed as a multi-select drop with following options
      | N/A |
      | No  |
      | Yes |
    Examples:
      | ContactReasons                 |
      | Renewal - CVCC                 |
      | Renewal - CVIU                 |
      | Pre-Release Application - CVIU |
      | Re-Entry Application - CVIU    |
      | New Application - CVCC         |
      | New Application - CVIU         |
      | Appeal                         |
      | Expedited Applications - CVIU  |

  @CP-21528 @CP-21528-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @chopa
  Scenario Outline: CoverVA: Electronic Signature Displays Based on Contact Reason in General Contact Outbound Call
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I click on Call Managment window
    Then I click on "Outbound" Contact Type
    And I choose Contact Reason for Cover VA "<ContactReasons>" and associated Contact Action
    Then I verify Electronic Signature Captured checkbox is displayed as a multi-select drop with following options
      | N/A |
      | No  |
      | Yes |
    Examples:
      | ContactReasons                 |
      | Renewal - CVCC                 |
      | Renewal - CVIU                 |
      | Pre-Release Application - CVIU |
      | Re-Entry Application - CVIU    |
      | New Application - CVCC         |
      | New Application - CVIU         |
      | Appeal                         |
      | Expedited Applications - CVIU  |

  @CP-21528 @CP-21528-03 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @chopa
  Scenario Outline: CoverVA: Electronic Signature Displays Based on Contact Reason in Third Party Contact
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I click on Call Managment window
    Then I click on third party contact record type radio button
    And I choose Contact Reason for Cover VA "<ContactReasons>" and associated Contact Action
    Then I verify Electronic Signature Captured checkbox is displayed as a multi-select drop with following options
      | N/A |
      | No  |
      | Yes |
    Examples:
      | ContactReasons                 |
      | Renewal - CVCC                 |
      | Renewal - CVIU                 |
      | Pre-Release Application - CVIU |
      | Re-Entry Application - CVIU    |
      | New Application - CVCC         |
      | New Application - CVIU         |
      | Appeal                         |
      | Expedited Applications - CVIU  |

  @CP-21528 @CP-21528-04 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @chopa
  Scenario Outline: CoverVA: Electronic Signature Displays Based on Contact Reason in General Contact Outbound Call
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I click on Call Managment window
    Then I click on third party contact record type radio button
    Then I click on "Outbound" Contact Type
    And I choose Contact Reason for Cover VA "<ContactReasons>" and associated Contact Action
    Then I verify Electronic Signature Captured checkbox is displayed as a multi-select drop with following options
      | N/A |
      | No  |
      | Yes |
    Examples:
      | ContactReasons                 |
      | Renewal - CVCC                 |
      | Renewal - CVIU                 |
      | Pre-Release Application - CVIU |
      | Re-Entry Application - CVIU    |
      | New Application - CVCC         |
      | New Application - CVIU         |
      | Appeal                         |
      | Expedited Applications - CVIU  |

  @CP-21528 @CP-21528-05 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @chopa
  Scenario: CoverVA: Electronic Signature toggle Visibility Based on Contact Reason in General/Third Party Contact
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I click on Call Managment window
    And I choose Contact Reason for Cover VA "Appeal" and associated Contact Action
    Then I verify Electronic Signature Captured checkbox is displayed as a multi-select drop with following options
      | N/A |
      | No  |
      | Yes |
    And I click on expand reason button to edit previous contact reason
    And I choose Contact Reason for Cover VA "Change Request" and associated Contact Action
    Then I verify Electronic Signature Captured checkbox is hidden
    Then I click on third party contact record type radio button
    And I click on expand reason button to edit previous contact reason
    And I choose Contact Reason for Cover VA "Change Request" and associated Contact Action
    Then I verify Electronic Signature Captured checkbox is hidden

#Edit Contact functionality will be implemented in CP-24815
  @CP-21528 @CP-21528-06 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @chopa
  Scenario: CoverVA: Electronic Signature toggle Visibility Based on Contact Reason in Edit Contact
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I click on Call Managment button
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    When I save the contact and click "Yes" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    Then I verify Electronic Signature Captured Checkbox
    When I click on edit icon the Contact Details page
    And I choose Contact Reason for Cover VA "Complaint - CVIU" and associated Contact Action
    Then I verify Electronic Signature Captured checkbox is hidden

  @CP-21528 @CP-21528-07 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @chopa
  Scenario: CoverVA: Electronic Signature On-Field Validation Error Message in General Contact
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I click on Call Managment window
    And I choose Contact Reason for Cover VA "Appeal" and associated Contact Action
    When I save the contact without choose an option in Electronic Signature Dropdown
    Then I get an error "Electronic Signature is Required"

  @CP-21528 @CP-21528-08 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @chopa
  Scenario: CoverVA: Electronic Signature On-Field Validation Error Message in Third Party Contact
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I click on Call Managment window
    Then I click on third party contact record type radio button
    And I choose Contact Reason for Cover VA "Appeal" and associated Contact Action
    When I save the contact without choose an option in Electronic Signature Dropdown
    Then I get an error "Electronic Signature is Required"