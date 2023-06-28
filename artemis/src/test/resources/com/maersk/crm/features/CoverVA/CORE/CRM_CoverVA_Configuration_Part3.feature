Feature: CoverVA Contact Record Configurations Part 3

  @CP-19131 @CP-17374 @CP-17374-10 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: CoverVA: Configure Contact Record Drop-downs - Unidentified: PrefLanguage #@CP-19131 is the most recent
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I click on unidentified contact record type radio button
    And I verify Cover VA Unidentified Preferred Language drop down with options below:
      | Amharic               |
      | Arabic                |
      | Bassa                 |
      | Bengali               |
      | Chinese (Traditional) |
      | English               |
      | Farsi                 |
      | French                |
      | German                |
      | Hindi                 |
      | Ibo                   |
      | Korean                |
      | Russian               |
      | Spanish               |
      | Tagalog               |
      | Urdu                  |
      | Vietnamese            |
      | Yoruba                |
      | Other                 |

  @CP-19131 @CP-19131-06 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: CoverVA: Configure Contact Record Drop-downs - Unidentified: Caller Type
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I click on unidentified contact record type radio button
    And I verify Cover VA Unidentified Caller Type drop down with options below:
      | Anonymous   |
      | VA Citizen  |
      | Non-Citizen |

  @CP-29539 @CP-19131 @CP-17210 @CP-17210-02 @CP-21126 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: CoverVA: Configure Contact Record Drop-downs - General&Third party: Inbound Call Queue #CP-19131 is the most recent
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I verify "Inbound Call Queue" have following options
      | CoverVA_CVCC_AppealsEscalation_Eng01_Voice   |
      | CoverVA_CVCC_AppealsEscalation_Spa01_Voice   |
      | CoverVA_CVCC_AppealsVoicemail_Eng01_Voice    |
      | CoverVA_CVCC_AppealsVoicemail_Spa01_Voice    |
      | CoverVA_CVCC_Apply_Eng01_Voice               |
      | CoverVA_CVCC_Apply_Spa02_Voice               |
      | CoverVA_CVCC_ExistingCustomer_Eng01_Voice    |
      | CoverVA_CVCC_ExistingCustomer_Spa02_Voice    |
      | CoverVA_CVCC_MedicaidEstateLetter_Eng01_Voice |
      | CoverVA_CVCC_MedicaidEstateLetter_Spa02_Voice|
      | CoverVA_CVCC_Outbound_Eng01_Voice            |
      | CoverVA_CVCC_Outbound_Spa01_Voice            |
      | CoverVA_CVCC_StatusChange_Eng01_Voice        |
      | CoverVA_CVCC_StatusChange_Spa02_Voice        |
      | CoverVA_CVCC_SupervisorEscaltion_Eng01_Voice |
      | CoverVA_CVCC_SupervisorEscaltion_Spa02_Voice |
      | CoverVA_CVCC_Voicemail_Eng01_Voice           |
      | CoverVA_CVCC_Voicemail_Spa01_Voice           |
      | CoverVA_CVIU_Eng01_Voice                     |
      | CoverVA_CVIU_Outbound_Eng01_Voice            |
      | CoverVA_CVIU_Outbound_Spa01_Voice            |
      | CoverVA_CVIU_Spa02_Voice                     |
      | CoverVA_CVIU_SupervisorEscaltion_Eng01_Voice |
      | CoverVA_CVIU_SupervisorEscaltion_Spa02_Voice |
      | CoverVA_CVIU_Voicemail_Eng01_Voice           |
      | CoverVA_CVIU_Voicemail_Spa01_Voice           |

  @CP-19131 @CP-17210 @CP-17210-03 @CP-21134 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: CoverVA: Configure Contact Record Drop-downs - General&Third party: Call Campaign Field #CP-19131 is the most recent
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I click on "Outbound" type of call option in "Contact Type" dropdown
    Then Verify Cover VA General & Third Party Call Campaign has values
      | Outbound Calls - CVCC - English |
      | Outbound Calls - CVCC - Spanish |
      | Outbound Calls - CVIU - English |
      | Outbound Calls - CVIU - Spanish |

  @CP-17210 @CP-17210-06 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario Outline: CoverVA: Configure Contact Record Drop-downs - General&Third party: Program
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I will verify Cover VA General & Third Party PROGRAM options "<ProgramOptions>"
    Examples:
      | ProgramOptions            |
      | Deemed Newborn            |
      | FAMIS                     |
      | FAMIS MOMS                |
      | HPE                       |
      | Medicaid                  |
      | Medicaid - Pregnant Women |
      | Other                     |
      | N/A                       |

  @CP-17210 @CP-17210-07 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: CoverVA: Configure Contact Record Drop-downs - General&Third party: Disposition
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then Verify disposition field values are exist
      | Cancelled           |
      | Complete            |
      | Dropped             |
      | Escalate            |
      | Requested Call Back |
      | Transfer            |
    Then I click on "Outbound" Contact Type
    Then Verify disposition field values are exist
      | Cancelled           |
      | Complete            |
      | Dropped             |
      | Escalate            |
      | Outbound Incomplete |
      | Requested Call Back |
      | Transfer            |

  @CP-17210 @CP-17210-08 @CP-17210-09 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: CoverVA: Configure Contact Record Drop-downs - Third party: Consumer Type
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I navigate to Third Party page
    And Verify consumer Type field for Cover VA with the options
      | Agency                    |
      | Assister                  |
      | Authorized Representative |
      | DOC                       |
      | Hospital                  |
      | Juvenile Justice          |
      | LDSS                      |
      | Media                     |
      | Navigator                 |
      | Provider                  |
      | Regional/Local Jails      |

  @CP-19131 @CP-17210 @CP-17210-11 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: CoverVA: Configure Contact Record Drop-downs - Third party: Preferred Language #CP-19131 is the most recent
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I navigate to Third Party page
    And I verify Preferred Language drop down for Cover VA with options below:
      | English               |
      | Spanish               |
      | Korean                |
      | Vietnamese            |
      | Chinese (Traditional) |
      | Tagalog               |
      | Amharic               |
      | French                |
      | Russian               |
      | Arabic                |
      | Urdu                  |
      | Hindi                 |
      | Farsi                 |
      | Bengali               |
      | German                |
      | Bassa                 |
      | Ibo                   |
      | German                |
      | Other                 |

  @CP-16999 @CP-16999-1 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario: CoverVA: Add Electronic Signature Captured Checkbox - Create/Active Contact
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I click on Call Managment button
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    When I save the contact and click "Yes" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    Then I verify Electronic Signature Captured Checkbox

  @CP-16999 @CP-16999-2 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario: CoverVA: Add Electronic Signature Captured Checkbox - Edit
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I click on Call Managment button
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    When I save the contact and click "No" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    Then I verify edit Electronic Signature Captured Checkbox to Unchecked

  @CP-16999 @CP-16999-3 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario: CoverVA: Add Electronic Signature Captured Checkbox - Reason for Edit
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I click on Call Managment button
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    When I save the contact and click "Yes" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    And I edit Electronic Signature Checkbox in contact record with "Correcting Contact Details" Reason for Edit
    Then I verify Electronic Signature Captured Checkbox Unchecked