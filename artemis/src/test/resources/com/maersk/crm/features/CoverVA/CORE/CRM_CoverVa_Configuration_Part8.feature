Feature: CoverVA Contact Record Configurations Part 8

  @CP-21429 @CP-21429-03 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario Outline: Verify Application ID saved minimum of 9 characters of text - Edit Contact
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    Then I click on Call Managment button
    Then I save the contact without Application ID with BusinessUnit Drop Down "<busninesdrpDwn>"
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    When I click on edit icon the Contact Details page
    When I attempt to save the Contact without providing a minimum of 9 characters of text in the Application ID field
    When Verify will see error message "APPLICATION ID must be 9 characters"
    Examples:
      | busninesdrpDwn |
      | CVCC           |


  @CP-21429 @CP-21429-03.1 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario: Verify On-Field Validation Error Message - Deleting Existing Application ID Values
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I click on Call Managment button
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    When I save the contact and click "Yes" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    When I click on edit icon the Contact Details page
    Then I delete the contents of the Application ID and attempt to Save without providing a minimum of 9 characters
    When Verify will see error message "APPLICATION ID must be 9 characters"


  @CP-21429 @CP-21429-04 @CP-21197-03 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario: Verify Toggle Visibility Based on Contact Reason Selection Adjustment
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I click on Call Managment button
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    When I save the contact and click "Yes" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    When I click on edit icon the Contact Details page
    Then Verify I unselect all selected application-related Contact Reasons Application ID field becomes hidden

  @CP-21197 @CP-21197-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @aikanysh
  Scenario: Verify Editing Application ID Edit is visible on Edit History
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I searched customer have First Name as "Aika" and Last Name as "Begi"
    And I link the contact to an existing Case or Consumer Profile
    Then I click on Call Managment button
    When I save the contact and click "Yes" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    When I click on edit icon the Contact Details page
    Then I edit the Application ID field and verify edit is visible on Edit History

  @CP-21197 @CP-21197-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @aikanysh #fails due to bug
  Scenario Outline: Verify Adding Application ID edit is visible on Edit History
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I searched customer have First Name as "Aika" and Last Name as "Begi"
    And I link the contact to an existing Case or Consumer Profile
    Then I click on Call Managment button
    Then I save the contact without Application ID with BusinessUnit Drop Down "<busninesdrpDwn>"
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    When I click on edit icon the Contact Details page
    Then I add the Application ID field and verify edit is visible on Edit History
    Examples:
      | busninesdrpDwn |
      | CVCC           |

  @CP-21197 @CP-21197-04 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @aikanysh
  Scenario Outline: Verify Editing Application ID edit is only possible with correct reason
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I searched customer have First Name as "Aika" and Last Name as "Begi"
    And I link the contact to an existing Case or Consumer Profile
    Then I click on Call Managment button
    When I save the contact and click "Yes" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    When I click on edit icon the Contact Details page
    Then I edit the Application ID field and verify Error Message when trying to save Edit with "<IncorrectReasons>"
    Examples:
      | busninesdrpDwn | IncorrectReasons                   |
      | CVCC           | Adding Additional Comment          |
      | CVCC           | Adding Contact Details             |
      | CVCC           | Adding Contact Reason/Action       |
      | CVCC           | Correcting Additional Comment      |
      | CVCC           | Correcting Case/Consumer Link      |
      | CVCC           | Correcting Contact Reason/Action   |
      | CVCC           | Correcting Disposition             |
      | CVCC           | Correcting Third Party Information |

  @CP-21197 @CP-21197-05 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @aikanysh #fails due to bug
  Scenario Outline: Verify Adding Application ID edit is only possible with correct reason
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I searched customer have First Name as "Aika" and Last Name as "Begi"
    And I link the contact to an existing Case or Consumer Profile
    Then I click on Call Managment button
    Then I save the contact without Application ID with BusinessUnit Drop Down "<busninesdrpDwn>"
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    When I click on edit icon the Contact Details page
    Then I add the Application ID field and verify Error Message when trying to save Edit with "<IncorrectReasons>"
    Examples:
      | busninesdrpDwn | IncorrectReasons                   |
      | CVCC           | Adding Additional Comment          |
      | CVCC           | Adding Contact Reason/Action       |
      | CVCC           | Correcting Additional Comment      |
      | CVCC           | Correcting Case/Consumer Link      |
      | CVCC           | Correcting Contact Reason/Action   |
      | CVCC           | Correcting Disposition             |
      | CVCC           | Correcting Third Party Information |
      | CVCC           | Correcting Contact Details         |


  @CP-32042 @CP-32042-01 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: VA| Add Web Chat to Business Unit, Reasons and Actions - Edit Contact reason
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    Then I navigate to Contact Record Search Page
    And I search for contact record by contact id "412405"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    And I verify Contact Reason for Cover VA are associated with Business Unit "Web Chat"
      | Appeals                   |
      | Eligibility               |
      | Medicaid                  |
      | Member Information        |
      | New/Existing Applications |

  @CP-32042 @CP-32042-02 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario Outline:VA| Add Web Chat to Business Unit, Reasons and Actions - Edit Contact: action
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    Then I navigate to Contact Record Search Page
    And I search for contact record by contact id "412405"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    And I verify Contact Reason for Cover VA "<ContactReasons>" and associated Contact Action for Business Unit "Web Chat"
    Examples:
      | ContactReasons            |
      | Appeals                   |
      | Eligibility               |
      | Medicaid                  |
      | Member Information        |
      | New/Existing Applications |