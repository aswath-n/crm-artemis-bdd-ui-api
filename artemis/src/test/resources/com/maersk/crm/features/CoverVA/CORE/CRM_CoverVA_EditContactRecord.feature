Feature: CoverVA: Edit Rules for Business Unit Field

  @CP-24815 @CP-24815-1.0 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @Araz
  Scenario: VA: Attempting to Choose Contact Reason Before Business Unit Selection
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I verify Contact Reason for CoverVA has no selection options
    Then I see Business Unit must be selected before Contact Reason message displayed


  @CP-24815 @CP-24815-1.1 @CP-24815-2.0 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @Araz
  Scenario: VA: Changing Business Unit Selection After Reason Selection
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I searched customer have First Name as "Araz" and Last Name as "Ismayilov"
    And I link the contact to an existing Case or Consumer Profile
    Then I save the contact without Application ID with BusinessUnit Drop Down "CVCC"
    And I navigate to manual contact record search page
    Then I searching for contact with electronic signature and choosing first record
    And I click on edit icon the Contact Details page
    Then I edit Reason Action with "ID Card Request" reason and "Referred to MCO" action values
    And I saved Reason For Edit dropdown with "Correcting Contact Reason/Action" value
    And I click on edit icon the Contact Details page
    Then I click on already selected Business Unit dropdown in order to verify warning message in edit page


  @CP-24815 @CP-24815-1.2 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @Araz
  Scenario: VA: Changing Business Unit Selection After Reason Selection Deleted
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I searched customer have First Name as "Araz" and Last Name as "Ismayilov"
    And I link the contact to an existing Case or Consumer Profile
    Then I save the contact without Application ID with BusinessUnit Drop Down "CVCC"
    And I navigate to manual contact record search page
    Then I searching for contact with electronic signature and choosing first record
    And I click on edit icon the Contact Details page new
    Then I am able to delete above reason&action by clicking trash icon and clicking Continue
    Then Verify for Business Unit "CVCC" only have certain Contact Reason options available for selection on edit page
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
    Then Verify for Business Unit "CVIU - DJJ" only have certain Contact Reason options available for selection
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

  @CP-24815 @CP-24815-2.1 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @Araz
  Scenario Outline: VA: Edit "Business Unit" Field - Incorrect Reason for Edit
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I searched customer have First Name as "Araz" and Last Name as "Ismayilov"
    And I link the contact to an existing Case or Consumer Profile
    Then I save the contact without Application ID with BusinessUnit Drop Down "CVCC"
    And I navigate to manual contact record search page
    Then I searching for contact with electronic signature and choosing first record
    And I click on edit icon the Contact Details page
    Then I will see the error message for editing and saving Reason&Action with "<IncorrectReasons>" incorrect Reason for Edit
    Examples:
      | IncorrectReasons                   |
      | Adding Additional Comment          |
      | Adding Contact Details             |
      | Adding Contact Reason/Action       |
      | Correcting Additional Comment      |
      | Correcting Case/Consumer Link      |
      | Correcting Contact Details         |
      | Correcting Disposition             |
      | Correcting Third Party Information |


  @CP-24815 @CP-24815-2.2 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @Araz
  Scenario: VA: Edit "Business Unit" Field - Edit History
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I searched customer have First Name as "Araz" and Last Name as "Ismayilov"
    And I link the contact to an existing Case or Consumer Profile
    Then I save the contact without Application ID with BusinessUnit Drop Down "CVCC"
    And I navigate to manual contact record search page
    Then I searching for contact with electronic signature and choosing first record
    And I click on edit icon the Contact Details page
    Then I add new Reason Action with "Emergency Services - CVCC" reason and "Complete Telephonic Application" action values
    Then I select multiple values from Reason For Edit field on the Contact Details page
    |Adding Contact Reason/Action|
    Then I verify previous and updated value on Edit History page

  @CP-18285 @CP-18285-09 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @khazar
  Scenario: Verify Field Dependencies on channel field for inbound contact type
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    Then I save "Inbound" contact record with mandatory fields
    And I navigate to manual contact record search page
    Then I searching for contact with electronic signature and choosing first record
    When I click on edit icon the Contact Details page
    And I verify fields dependencies on channel field for cover va inbound contact type
      | Phone            |
      | Web Chat         |
      | IVR Self-Service |

  @CP-18285 @CP-18285-10 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @khazar
  Scenario: Verify Field Dependencies on channel field for outbound contact type
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    Then I save "Outbound" contact record with mandatory fields
    And I navigate to manual contact record search page
    Then I searching for contact with electronic signature and choosing first record
    When I click on edit icon the Contact Details page
    And I verify fields dependencies on channel field for cover va outbound contact type
      | Phone            |
      | Web Chat         |

@CP-46054 @ui-core-cover-va @crm-regression @khazar
Scenario: Adding and Editing Additional Comments for CoverVA
Given I will get the Authentication token for "CoverVA" in "CRM"
Given  I created a consumer with population type "MEDICAID-GENERAL" and following
| consumerRole     | Primary Individual |
| saveConsumerInfo | QW1                |
Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
And I minimize Genesys popup if populates
When I click on initiate contact record
When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
And I link the contact to an existing Case or Consumer Profile
And I enter additional comments "First additional comment"
Then I save the contact without Application ID with BusinessUnit Drop Down "CVCC"
And I navigate to manual contact record search page
Then I searching for contact with electronic signature and choosing first record
And I click on edit icon the Contact Details page
Then I verify that saved additional comments is displayed
Then I will edit the saved additional comments and the new value is saved and displayed