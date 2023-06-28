Feature: CoverVA Contact Record Configurations Part 7

  @CP-21428 @CP-21428-01 @CP-21428-02 @CP-20997 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario Outline:Application ID Visible and Required in Active Contact when I have selected a Contact Reason that refers to an applicationId
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I navigate to "<contact>" Contact page
    Then I click on Call Managment button without getting consumerId
    And I verify Application ID field will be visible and Required with Contact Reasons
      | Appeal                            |
      | Application/Renewal Status - CVCC |
      | Change Request                    |
      | Renewal - CVCC                    |
      | New Application - CVCC            |
      | Application/Renewal Status - CVIU |
      | Change Request - CVIU             |
      | Expedited Applications - CVIU     |
      | New Application - CVIU            |
      | Pre-Release Application - CVIU    |
      | Re-Entry Application - CVIU       |
      | Renewal - CVIU                    |
    Examples:
      | contact    |
      | General    |
      | ThirdParty |


  @CP-21428 @CP-21428-03 @CP-21428-04 @CP-20997 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario Outline:Application ID Validation Error Message and Toggle Visibility Based on Contact Reason Selection
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I navigate to "<contact>" Contact page
    Then I click on Call Managment button without getting consumerId
    And  I verify error message "APPLICATION ID must be 9 characters" for ApplicationId
    Then I verify Application ID field becomes hidden when unselect all selected application-related Contact Reasons
    Examples:
      | contact    |
      | General    |
      | ThirdParty |

  @CP-20997 @CP-20997-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @aikanysh
  Scenario Outline:Application ID Not Visible for Unidentified
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I navigate to "<contact>" Contact page
    Then I click on Call Managment button without getting consumerId
    And I verify Application ID field will be NOT visible and Required with Contact Reasons
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
      | contact      |
      | Unidentified |


  @CP-24491 @CP-24491-01 @CP-24491-01.1 @CP-24491-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario:Verify Selected Business Unit Determines Contact Reason Select List Values
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I click on Call Managment button without getting consumerId
    Then Verify for Business Unit CVCC only have certain Contact Reason options available for selection
      | Appeal                                    |
      | Application/Renewal Status - CVCC         |
      | Callback (Outbound)                       |
      | Change Request                            |
      | Complaint                                 |
      | Correspondence                            |
      | Emergency Services - CVCC                 |
      | FAMIS Member Services                     |
      | General Inquiry                           |
      | HIPAA Violation - CVCC                    |
      | ID Card Request                           |
      | Medicaid Member Services                  |
      | Member Follow-Up (Outbound)               |
      | New Application - CVCC                    |
      | Newborn Notification                      |
      | Pre-Hearing Conference (Inbound/Outbound) |
      | Renewal - CVCC                            |
      | Reported Fraud                            |
      | Silent/No Consumer                        |
      | Verification Information                  |
    Then Verify for Business Unit CVIU only have certain Contact Reason options available for selection
      | Appeal                                    |
      | Application/Renewal Status - CVIU         |
      | Callback (Outbound) - CVIU                |
      | Change Request - CVIU                     |
      | Complaint - CVIU                          |
      | Correspondence - CVIU                     |
      | Emergency Services - CVIU                 |
      | Expedited Applications - CVIU             |
      | FAMIS Member Services                     |
      | General Inquiry - CVIU                    |
      | HIPAA Violation - CVIU                    |
      | ID Card Request - CVIU                    |
      | Medicaid Member Services - CVIU           |
      | Member Follow-Up (Outbound)               |
      | New Application - CVIU                    |
      | Newborn Notification - CVIU               |
      | Pre-Hearing Conference (Inbound/Outbound) |
      | Pre-Release Application - CVIU            |
      | Re-Entry Application - CVIU               |
      | Renewal - CVIU                            |
      | Reported Fraud                            |
      | Silent/No Consumer - CVIU                 |
      | Verification Information - CVIU           |

  @CP-24491 @CP-24491-01 @CP-24491-01.1 @CP-24491-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario:Verify Business Unit View Contact with Edit Contact screens and shall be Read Only
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    Then I click on Call Managment button
    And I save the contact with Business Unit "CVCC" and Contact Reason option "Appeal"
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    Then Verify Business Unit read only on View/Edit Contact
