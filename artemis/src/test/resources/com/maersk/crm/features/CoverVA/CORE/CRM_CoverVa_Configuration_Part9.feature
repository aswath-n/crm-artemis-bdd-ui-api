Feature: CoverVA Contact Record Configurations Part 9

  @CP-19579 @CP-19579-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario Outline: Verify Electronic Signature removed from Unidentified Contact
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I click on Call Managment window
    When I click on unidentified contact record type radio button
    And I choose Contact Reason for Cover VA "<ContactReasons>" and associated Contact Action
    Then I verify Electronic Signature Captured checkbox not  displayed on Unidentified Contact
    Examples:
      | ContactReasons |
      | Appeal         |

  #CP-19679
  @CP-19579 @CP-19579-02 @CP-19579-3.0 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario: Verify Electronic Signature Captured in Edit History and Reason for Edit
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    Then I click on Call Managment button
    When I save the contact and click "Yes" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    And I edit Electronic Signature Checkbox in contact record with "Correcting Contact Details" Reason for Edit
    Then I verify Electronic Signature Captured Checkbox Unchecked
    And Verify Electronic Signature Captured in Edit History and Reason for Edit


  #CP-25854
  @CP-19579 @CP-19579-3.1 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario Outline: Verify I will see the error message that corresponds with my incorrect Reason for Edit
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    Then I click on Call Managment button
    When I click on initiate contact record
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    When I save the contact and click "Yes" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    Then Verify error message that "<ContactReasons>" with my incorrect Reason for Edit
    Examples:
      | ContactReasons                     |
      | Adding Additional Comment          |
      | Adding Contact Details             |
      | Adding Contact Reason/Action       |
      | Correcting Additional Comment      |
      | Correcting Case/Consumer Link      |
      | Correcting Contact Reason/Action   |
      | Correcting Disposition             |
      | Correcting Third Party Information |

  @CP-19579 @CP-19579-4.0 @CP-19579-4.1 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario: Verify Electronic Signature Captured Flag to Expanded Contact Search Result
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I click on Call Managment button
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    When I save the contact and click "Yes" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature
    Then I expand first contact record after contact record search
    And Verify I will see a read only version of the Electronic Signature Captured checkbox

  @CP-22331 @CP-22331-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @aikanysh
  Scenario: VA: Add Business Unit Field to Contact Search & Search Result
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I click on Call Managment button
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    When I save the contact and click "Yes" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature
    Then I expand first contact record after contact record search
    And Verify I will see a Business Unit as part of expanded search result


  @CP-27299 @CP-21429 @CP-21429-01 @CP-21429-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario Outline: Verify Application ID Visible and Required  - Edit Contact
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    Then I save the contact without Application ID with BusinessUnit Drop Down "<busninesdrpDwn>"
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    And I verify Application ID field will be visible with BusinessUnit Drop Down "<busninesdrpDwn>" and Contact Reasons on Edit page
      | Appeal                            |
      | Application/Renewal Status - CVCC |
      | Application/Renewal Status - CVIU |
      | Change Request                    |
      | Change Request - CVIU             |
      | Expedited Applications - CVIU     |
      | New Application - CVCC            |
      | New Application - CVIU            |
      | Pre-Release Application - CVIU    |
      | Re-Entry Application - CVIU       |
      | Renewal - CVCC                    |
      | Renewal - CVIU                    |
    Examples:
      | busninesdrpDwn |
      | CVCC           |
      | CVIU - DJJ     |
      | CVIU - DOC     |
      | CVIU - Jails   |
      | CVIU - Other   |
      | Web Chat       |
