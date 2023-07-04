Feature: CoverVA Contact Record Configurations Part 1

  @CP-17683 @CP-17683-01 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: CoverVA: Configure Translation Service Field
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I verify Translation Service dropdown with options below:
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
      | Other                 |
      | Russian               |
      | Tagalog               |
      | Urdu                  |
      | Vietnamese            |
      | Yoruba                |
    When I click on "Outbound" type of call option in "Contact Type" dropdown
    And I verify Translation Service dropdown with options below:
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
      | Other                 |
      | Russian               |
      | Tagalog               |
      | Urdu                  |
      | Vietnamese            |
      | Yoruba                |
    When I click on third party contact record type radio button
    And I verify Translation Service dropdown with options below:
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
      | Other                 |
      | Russian               |
      | Tagalog               |
      | Urdu                  |
      | Vietnamese            |
      | Yoruba                |
    When I click on unidentified contact record type radio button
    And I verify Translation Service dropdown with options below:
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
      | Other                 |
      | Russian               |
      | Tagalog               |
      | Urdu                  |
      | Vietnamese            |
      | Yoruba                |

  @CP-19131 @CP-17374 @CP-17374-02 @CP-21126 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: CoverVA: Configure Contact Record Drop-downs - Unidentified: Inbound Call Queue #@CP-19131 is the most recent
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I click on unidentified contact record type radio button
    And I verify "Inbound Call Queue" have following options
      | CoverVA_CVCC_AppealsEscalation_Eng01_Voice   |
      | CoverVA_CVCC_AppealsEscalation_Spa01_Voice   |
      | CoverVA_CVCC_AppealsVoicemail_Eng01_Voice    |
      | CoverVA_CVCC_AppealsVoicemail_Spa01_Voice    |
      | CoverVA_CVCC_Apply_Eng01_Voice               |
      | CoverVA_CVCC_Apply_Spa02_Voice               |
      | CoverVA_CVCC_ExistingCustomer_Eng01_Voice    |
      | CoverVA_CVCC_ExistingCustomer_Spa02_Voice    |
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

  @CP-19131 @CP-17374 @CP-17374-03 @CP-21134 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: CoverVA: Configure Contact Record Drop-downs - Unidentified: Call Campaign #@CP-19131 is the most recent
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I click on unidentified contact record type radio button
    When I click on "Outbound" type of call option in "Contact Type" dropdown
    Then Verify Cover VA Call Campaign has values
      | Outbound Calls - CVCC - English |
      | Outbound Calls - CVCC - Spanish |
      | Outbound Calls - CVIU - English |
      | Outbound Calls - CVIU - Spanish |

  @CP-17374 @CP-17374-04 @CP-17210 @CP-17210-04 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: CoverVA: Configure Contact Record Drop-downs - Unidentified: Channel
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I will see Cover VA Channel field with the options
      | Phone    |
      | Web Chat |
    Then I navigate to Third Party page
    Then I will see Cover VA Channel field with the options
      | Phone    |
      | Web Chat |
    When I click on unidentified contact record type radio button
    Then I will see Cover VA Channel field with the options
      | Phone    |
      | Web Chat |

  @CP-19131 @CP-17374 @CP-17374-05 @CP-17210 @CP-17210-05 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: CoverVA: Configure Contact Record Drop-downs - Gen, ThirdPart, Unident: Outcome Of the contact #@CP-19131 is the most recent
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I click on "Outbound" type of call option in "Contact Type" dropdown
    Then Verify Outcome of Contact field with the options
      | Did Not Reach/No Voicemail                |
      | Did Not Reach/Left Voicemail              |
      | Invalid Phone Number                      |
      | Reached Successfully/spoke to CAC         |
      | Reached Successfully/did not speak to CAC |
    Then I navigate to Third Party page
    When I click on "Outbound" type of call option in "Contact Type" dropdown
    Then Verify Outcome of Contact field with the options
      | Did Not Reach/No Voicemail                |
      | Did Not Reach/Left Voicemail              |
      | Invalid Phone Number                      |
      | Reached Successfully/spoke to CAC         |
      | Reached Successfully/did not speak to CAC |
    When I click on unidentified contact record type radio button
    When I click on "Outbound" type of call option in "Contact Type" dropdown
    Then Verify Outcome of Contact field with the options
      | Did Not Reach/No Voicemail                |
      | Did Not Reach/Left Voicemail              |
      | Invalid Phone Number                      |
      | Reached Successfully/spoke to CAC         |
      | Reached Successfully/did not speak to CAC |

  @CP-19131 @CP-17374 @CP-17374-06 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario Outline: CoverVA: Configure Contact Record Drop-downs - Unidentified: Program #@CP-19131 is the most recent
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I click on unidentified contact record type radio button
    And I will verify Cover VA PROGRAM options "<ProgramOptions>"
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

  @CP-19131 @CP-17374 @CP-17374-07 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: CoverVA: Configure Contact Record Drop-downs - Unidentified: Disposition #@CP-19131 is the most recent
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I click on unidentified contact record type radio button
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
      | Requested Call Back |
      | Transfer            |
      | Outbound Incomplete |


  @CP-19580 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @aikanysh
  Scenario: CoverVA: Hide Claim ID on Contact record on EDIT/VIEW screen
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I click on Call Managment button
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    When I save the contact and click "Yes" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    And I verify there is NO field named "Claim ID"

  @CP-22099-0 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @chopa
  Scenario: CoverVA: Save Contact Comments during Contact Record Creation
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I verify the error message for not providing comments in Contact Reasons
    And I verify comments field is successfully saved when providing at least 1 character

  @CP-22099-1 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @chopa
  Scenario: CoverVA: Edit Contact Comments saved during Contact Record Creation
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I verify the error message for not providing comments in Contact Reasons
    And I verify comments field is successfully saved when providing at least 1 character
    Then I click on expand reason button to edit previous saved comment and verify the error message

  @CP-19519 @CP-19519-01 @ui-cc-va @crm-regression @CoverVA-UI-Regression @chopa
  Scenario: CoverVA: Hide Case ID and Consumer Role for Linked Consumer on General contact and on EDIT/VIEW screen
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I click on Call Managment button
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    Then I verify Case ID and Consumer Role column headers are not displayed for consumer
    When I save the contact and click "Yes" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    Then I verify Case ID and Consumer Role column headers are not displayed for consumer
    When I click on edit icon the Contact Details page
    Then I verify Case ID and Consumer Role column headers are not displayed for consumer

  @CP-19519 @CP-19519-02 @ui-cc-va @crm-regression @CoverVA-UI-Regression @chopa
  Scenario Outline: CoverVA: Hide Case ID and Consumer Role for Linked Consumer on Third Party contact and on EDIT/VIEW screen
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    When I searched existing case where First Name as "Aika" and Last Name as "Begi" on third party page
    And I link the contact to an existing Case or Consumer Profile
    Then I verify Case ID and Consumer Role column headers are not displayed for consumer
    Then I click on Call Managment button
    When I save the contact and click "Yes" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    Then I verify Case ID and Consumer Role column headers are not displayed for consumer
    When I click on edit icon the Contact Details page
    Then I verify Case ID and Consumer Role column headers are not displayed for consumer
    Examples:
      | First Name | Last Name | Organization Name | Consumer Type | Preferred Language |
      | Ellie      | Smith     | ABC Group         | Media         | English            |


  @CP-31987 @CP-31987-02 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: VA| VA| Hide Web Chat in Business Unit dropdowns in Contact Record-Active Screen
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    And I select channel "Phone"
    And I verify there is NO "Web Chat" business unit

  @CP-31987 @CP-31987-03 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: VA| VA| Hide Web Chat in Business Unit dropdowns in Contact Record-Edit Screen
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    Then I navigate to Contact Record Search Page
    And I search for contact record by contact id "2670"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    And I verify there is NO "Web Chat" business unit

  @CP-29471 @CP-29471-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @khazar
  Scenario Outline: Verify Action dropdown values in Active Contact for General, ThirdParty and Unidentified contact
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I navigate to "<CR type>" contact record type
    And I verify business unit and associated contact reason
      | CVCC         | Silent/No Consumer        |
      | CVIU - DJJ   | Silent/No Consumer - CVIU |
      | CVIU - DOC   | Silent/No Consumer - CVIU |
      | CVIU - Jails | Silent/No Consumer - CVIU |
      | CVIU - Other | Silent/No Consumer - CVIU |
    Examples:
      | CR type      |
      | General      |
      | ThirdParty   |
      | Unidentified |

  @CP-29471 @CP-29471-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @khazar
  Scenario Outline: Verify Action dropdown values on Edit page for General, ThirdParty and Unidentified contact
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    Then I navigate to Contact Record Search Page
    And I search for contact record id by "<CR type>" task type
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I am able to delete above reason&action by clicking trash icon and clicking Continue
    And I verify business unit and associated contact reason
      | CVCC         | Silent/No Consumer        |
      | CVIU - DJJ   | Silent/No Consumer - CVIU |
      | CVIU - DOC   | Silent/No Consumer - CVIU |
      | CVIU - Jails | Silent/No Consumer - CVIU |
      | CVIU - Other | Silent/No Consumer - CVIU |
    Examples:
      | CR type      |
      | General      |
      | ThirdParty   |
      | Unidentified |





