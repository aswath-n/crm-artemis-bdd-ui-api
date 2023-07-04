Feature: IN-EB Contact Record Configurations Part One

  @CP-23012 @CP-23012-01 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Configure Contact Record Drop-downs - Unidentified: Caller Type
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    When I click on unidentified contact record type radio button
    And I verify Cover VA Unidentified Caller Type drop down with options below:
      | Anonymous |

  @CP-23012 @CP-23012-02 @CP-25936 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Configure Contact Record Drop-downs - Consumer type
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    Then I navigate to Third Party page
    And Verify consumer Type field for IN_EB with the options
      | Plan                |
      | One-Time Authorized |
      | Representative      |
      | Media               |
      | Navigator           |
      | Provider            |
      | Other               |

  @CP-23012 @CP-23012-03 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Configure Contact Record Drop-downs - Pref Language
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    Then I navigate to Third Party page
    And I verify Preferred Language drop down for Cover VA with options below:
      | English |
      | Spanish |
      | Burmese |
      | Other   |

  @CP-23012 @CP-23012-04 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Configure Contact Record Drop-downs - Channel
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    Then I will see the Channel field with the options
      | Phone |

  @CP-23012 @CP-23012-05 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Configure Contact Record Drop-downs - Inbound call queue
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    And I verify "Inbound Call Queue" have following options
      | UAT_CARE_ENG_Q |
      | UAT_CARE_SPA_Q |
      | UAT_HIP_ENG_Q  |
      | UAT_HIP_SPA_Q  |
      | UAT_HOOS_ENG_Q |
      | UAT_HOOS_SPA_Q |
      | UAT_INS_ENG_Q  |
      | UAT_INS_SPA_Q  |
      | UAT_KIDS       |

  @CP-23012 @CP-23012-06 @CP-32742 @CP-33483 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Configure Contact Record Drop-downs - Call Campaign
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    When I click on "Outbound" type of call option in "Contact Type" dropdown
    And I verify "Call Campaign" have following options
      | INEB_CSR_DIALER          |
      | Outbound Calls - English |
      | Outbound Calls - Spanish |
      | Voicemail                |

  @CP-23012 @CP-23012-07 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Configure Contact Record Drop-downs - Program
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    And I select "All" program types

  @CP-23012 @CP-23012-08 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Configure Contact Record Drop-downs - Translation Service
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    And I verify Translation Service dropdown with options below:
      | English |
      | Spanish |
      | Burmese |
      | Other   |

  @CP-23012 @CP-23012-09 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Configure Contact Record Drop-downs - Outcome Of Contact
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    When I click on "Outbound" type of call option in "Contact Type" dropdown
    And I verify "Outcome of Contact" have following options
      | Did Not Reach/Left Voicemail |
      | Did Not Reach/No Voicemail   |
      | Invalid Phone Number         |
      | DNC List                     |
      | Reached Successfully         |

  @CP-23012 @CP-23012-10 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Configure Contact Record Drop-downs - Disposition
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    Then Verify disposition field values are exist
      | Complete            |
      | Dropped             |
      | Requested Call Back |
      | Transfer            |
      | Voicemail           |
    Then I click on "Outbound" Contact Type
    Then Verify disposition field values are exist
      | Complete            |
      | Dropped             |
      | Outbound Incomplete |
      | Requested Call Back |
      | Transfer            |
      | Voicemail           |

  @CP-23012 @CP-23012-11 @CP-28616 @CP-28616-01 @CP-25408 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Configure Contact Record Drop-downs - Reason for Edit
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to Contact Record search
    And I searched customer have First Name as "Core" and Last Name as "Addressmail"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I verify Reason for Edit dropdown populated with values
      | Adding Call Summary                |
      | Adding Contact Details             |
      | Adding Contact Reason/Action       |
      | Correcting Call Summary            |
      | Correcting Case/Consumer Link      |
      | Correcting Contact Reason/Action   |
      | Correcting Contact Details         |
      | Correcting Third Party Information |
      | Correcting Disposition             |

  @CP-23012 @CP-23012-12 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Configure Contact Record Drop-downs - Verify Required Fields
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    Then I verify required fields are CallerType, ConsumerType, PreferredLanguage, InboundCallQueue, Program, Disposition

  @CP-23012 @CP-23012-13 @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @kamil
  Scenario Outline: Configure Contact Record Dropdowns - Edit Contact
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    Then I navigate to Contact Record Search Page
    And I search for contact record by contact id "<conId>"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I verify for contact record for type "<type>" and ContactType "<conType>" with reasonForEdit
      | Adding Call Summary                |
      | Adding Contact Details             |
      | Adding Contact Reason/Action       |
      | Correcting Call Summary            |
      | Correcting Case/Consumer Link      |
      | Correcting Contact Reason/Action   |
      | Correcting Contact Details         |
      | Correcting Third Party Information |
      | Correcting Disposition             |
    Then I verify for contact record for type "<type>" and ContactType "<conType>" with InboundCallQueue
      | UAT_CARE_ENG_Q |
      | UAT_CARE_SPA_Q |
      | UAT_HIP_ENG_Q  |
      | UAT_HIP_SPA_Q  |
      | UAT_HOOS_ENG_Q |
      | UAT_HOOS_SPA_Q |
      | UAT_INS_ENG_Q  |
      | UAT_INS_SPA_Q  |
      | UAT_KIDS       |
    Then I verify for contact record for type "<type>" and ContactType "<conType>" with ConsumerType and PhoneNumber
      | Plan                               |
      | One-Time Authorized Representative |
      | Media                              |
      | Navigator                          |
      | Provider                           |
      | Other                              |
    Then I verify for contact record for type "<type>" and ContactType "<conType>" with Preferred Languages and Channel
      | English |
      | Spanish |
      | Burmese |
      | Other   |
    Then I verify for contact record for type "<type>" and ContactType "<conType>" with CallCampaign and OutcomeOfContact
      | Did Not Reach/Left Voicemail |
      | Did Not Reach/No Voicemail   |
      | Invalid Phone Number         |
      | DNC List                     |
      | Reached Successfully         |
      |[blank]|
    Then I verify for contact record has TranslationService on Edit page
      | English |
      | Spanish |
      | Burmese |
      | Other   |
      |[blank]|
    And I verify for contact record has Program on Edit page
      | HCC                  |
      | HHW                  |
      | HIP                  |
      | Traditional Medicaid |
    Then I verify the dropdown values for DISPOSITION Contact Details dropdown list
      | Complete            |
      | Dropped             |
      | Requested Call Back |
      | Transfer            |
      | Voicemail           |
      | Incomplete          |
    Examples:
      | type         | conType | conId |
      | General      | Inbound | 451   |
      | ThirdParty   | Inbound | 478   |
      | Unidentified | Inbound | 479   |
      | Unidentified | Inbound | 2588  |

  @CP-23012 @CP-23012-14 @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @kamil
  Scenario Outline: Configure Contact Record Dropdowns - Edit Contact
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    Then I navigate to Contact Record Search Page
    And I search for contact record by contact id "<conId>"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I verify for contact record for type "<type>" and ContactType "<conType>" with reasonForEdit
      | Adding Call Summary                |
      | Adding Contact Details             |
      | Adding Contact Reason/Action       |
      | Correcting Call Summary            |
      | Correcting Case/Consumer Link      |
      | Correcting Contact Reason/Action   |
      | Correcting Contact Details         |
      | Correcting Third Party Information |
      | Correcting Disposition             |
    Then I verify for contact record for type "<type>" and ContactType "<conType>" with InboundCallQueue
      | UAT_CARE_ENG_Q |
      | UAT_CARE_SPA_Q |
      | UAT_HIP_ENG_Q  |
      | UAT_HIP_SPA_Q  |
      | UAT_HOOS_ENG_Q |
      | UAT_HOOS_SPA_Q |
      | UAT_INS_ENG_Q  |
      | UAT_INS_SPA_Q  |
      | UAT_KIDS       |
    Then I verify for contact record for type "<type>" and ContactType "<conType>" with ConsumerType and PhoneNumber
      | Plan                               |
      | One-Time Authorized Representative |
      | Media                              |
      | Navigator                          |
      | Provider                           |
      | Other                              |
    Then I verify for contact record for type "<type>" and ContactType "<conType>" with Preferred Languages and Channel
      | English |
      | Spanish |
      | Burmese |
      | Other   |
    Then I verify for contact record for type "<type>" and ContactType "<conType>" with CallCampaign and OutcomeOfContact
      | Did Not Reach/Left Voicemail |
      | Did Not Reach/No Voicemail   |
      | Invalid Phone Number         |
      | DNC List                     |
      | Reached Successfully         |
      |                              |
    Then I verify for contact record has TranslationService on Edit page
      | English |
      | Spanish |
      | Burmese |
      | Other   |
      |         |
    And I verify for contact record has Program on Edit page
      | HCC                  |
      | HHW                  |
      | HIP                  |
      | Traditional Medicaid |
    Then I verify the dropdown values for DISPOSITION Contact Details dropdown list
      | Complete            |
      | Dropped             |
      | Requested Call Back |
      | Transfer            |
      | Voicemail           |
      | Outbound Incomplete |
    Examples:
      | type       | conType  | conId |
      | General    | Outbound | 550   |
      | ThirdParty | Outbound | 2590  |

  @CP-23012 @CP-23012-15 @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @aikanysh
  Scenario Outline: Configure Contact Record Dropdowns - Edit Contact-Outbound Disposition
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    Then I navigate to Contact Record Search Page
    And I search for contact record by contact id "<conId>"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I verify the dropdown values for "DISPOSITION"
      | Complete            |
      | Dropped             |
      | Outbound Incomplete |
      | Requested Call Back |
      | Transfer            |
      | Voicemail           |
    Examples:
      | conId |
      | 2590  |
      | 550   |
      | 2058  |


