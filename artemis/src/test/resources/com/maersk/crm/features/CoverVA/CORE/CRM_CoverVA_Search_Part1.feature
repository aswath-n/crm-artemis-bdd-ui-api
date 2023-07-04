Feature: CoverVA: Contact Record Search Part 1

  @CP-17990 @CP-17990-01 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario Outline: Configure Manual Contact Search Results
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I navigate to manual contact record search page
    Then I search with "<CreatedOn>" field with todays date
    And I verify appropriate CoverVA Contact Record Search Result field headers are displayed
    Examples:
      | CreatedOn |
      | =         |


  @CP-19522 @CP-19522-01 @CP-21126 @kamil @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: Configure Inbound Call Queue - Multi-Select
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I navigate to Contact Record search
    Then Clicking Advanced Search on Contact Record search in CoverVa
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


  @CP-19522 @CP-19522-02 @CP-21134 @kamil @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: Configure Call Campaign - Multi-Select
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I navigate to Contact Record search
    Then Clicking Advanced Search on Contact Record search in CoverVa
    And I verify "Call Campaign" have following options
      | Outbound Calls - CVCC - English |
      | Outbound Calls - CVCC - Spanish |
      | Outbound Calls - CVIU - English |
      | Outbound Calls - CVIU - Spanish |


  @CP-19522 @CP-19522-03 @kamil @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: Configure Outcome of Contact - Multi-Select
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I navigate to Contact Record search
    Then Clicking Advanced Search on Contact Record search in CoverVa
    And I verify "Outcome of Contact" have following options
      | Did Not Reach/Left Voicemail              |
      | Did Not Reach/No Voicemail                |
      | Invalid Phone Number                      |
      | Reached Successfully/did not speak to CAC |
      | Reached Successfully/spoke to CAC         |




  @CP-30031 @CP-17222 @CP-17222-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil @aikanysh
  Scenario: CoverVA: Configure Manual Contact Search Fields - Initial
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I navigate to manual contact record search page
    And I click on Call Managment window
    When I verify "ContactRecordId" field accept 10 characters in total
    When I verify "firstName" field accept 30 characters in total
    When I verify "lastName" field accept 30 characters in total
    When I verify "createdOn" field accept 8 characters in total
    When I verify "ConsumerId" field accept 12 characters in total
    Then Verify "ContactType" dropDown has a options
      | Inbound  |
      | Outbound |
      | Self Service |
    Then Verify "Channel" dropDown has a options
      | Phone    |
      | Web Chat |
      | IVR      |
    Then Verify "Disposition" dropDown has a options
      | Cancelled           |
      | Complete            |
      | Dropped             |
      | Escalate            |
      | Incomplete          |
      | Outbound Incomplete |
      | Requested Call Back |
      | Transfer            |
      | Pending IVR Self Service Data|
    Then Verify "Type" dropDown has a options
      | General              |
      | Third Party          |
      | Unidentified Contact |
    Then Verify "ConsumerIdType" dropDown has a options
      | Internal |
      | MMIS     |
      | VaCMS    |


  @CP-17222 @CP-17222-02 @CP-21134 @CP-21126 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario: Configure Manual Contact Search Fields - Advanced Search
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I navigate to Contact Record search
    Then Clicking Advanced Search on Contact Record search in CoverVa
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
    And I verify "Call Campaign" have following options
      | Outbound Calls - CVCC - English |
      | Outbound Calls - CVCC - Spanish |
      | Outbound Calls - CVIU - English |
      | Outbound Calls - CVIU - Spanish |
    And I verify "Outcome of Contact" have following options
      | Did Not Reach/Left Voicemail              |
      | Did Not Reach/No Voicemail                |
      | Invalid Phone Number                      |
      | Reached Successfully/did not speak to CAC |
      | Reached Successfully/spoke to CAC         |
    When I verify "Phone" field accept 10 characters in total
    When I verify "Email" field accept 30 characters in total
    When I verify "ThirdPartyFirstName" field accept 30 characters in total
    When I verify "ThirdPartyLasttName" field accept 30 characters in total
    When I verify "ThirdPartyOrganization" field accept 30 characters in total
    And I verify "CreatedBy" field has Auto-complete Dropdown when "Service" is opened
    And I verify "UserId" field has Auto-complete Dropdown when "24" is opened


  @CP-17222 @CP-17222-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario: CoverVA: Configure Manual Contact Search Fields - Negative Scenario
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I navigate to manual contact record search page
    Then Clicking Advanced Search on Contact Record search in CoverVa
    When I pass 20 characters in total I verify "ContactRecordId" field doesn't accept more 10 characters
    When I pass 50 characters in total I verify "firstName" field doesn't accept more 30 characters
    When I pass 50 characters in total I verify "lastName" field doesn't accept more 30 characters
    When I pass 24 characters in total I verify "ConsumerId" field doesn't accept more 12 characters
    When I pass 20 characters in total I verify "Phone" field doesn't accept more 10 characters
    When I pass 45 characters in total I verify "Email" field doesn't accept more 30 characters
    When I pass 50 characters in total I verify "ThirdPartyFirstName" field doesn't accept more 30 characters
    When I pass 50 characters in total I verify "ThirdPartyLasttName" field doesn't accept more 30 characters




