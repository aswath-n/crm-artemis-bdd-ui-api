Feature: CoverVA Contact Record Configurations Part 10

  @CP-25840 @CP-25840-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @aikanysh
  Scenario: Verify Adding Application ID edit is only possible with correct reason
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I verify there is NO cancel button

  @CP-25840 @CP-25840-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @aikanysh
  Scenario: Verify Adding Application ID edit is only possible with correct reason
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I searched customer have First Name as "Aika" and Last Name as "Begi"
    And I link the contact to an existing Case or Consumer Profile
    Then I click on Call Managment button
    Then I save the contact without Application ID with BusinessUnit Drop Down "<busninesdrpDwn>"
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    When I click on edit icon the Contact Details page
    Then I verify cancel button is displayed

  @CP-29492 @CP-29492-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @khazar
  Scenario Outline: Verify Action dropdown values for Newborn Notification for general contact
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I select business unit "<business unit>" and contact reason "<contact reason>"
    And I verify contact action multi select drop down values
      | Escalated to Supervisor |
      | Submitted Deeming Form  |
    Examples:
      | business unit | contact reason              |
      | CVCC          | Newborn Notification        |
      | CVIU - DJJ    | Newborn Notification - CVIU |
      | CVIU - DOC    | Newborn Notification - CVIU |
      | CVIU - Jails  | Newborn Notification - CVIU |
      | CVIU - Other  | Newborn Notification - CVIU |

  @CP-29492 @CP-29492-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @khazar
  Scenario Outline: Verify Action dropdown values for Newborn Notification for third party contact
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I click on third party contact record type radio button
    When I select business unit "<business unit>" and contact reason "<contact reason>"
    And I verify contact action multi select drop down values
      | Escalated to Supervisor |
      | Submitted Deeming Form  |
    Examples:
      | business unit | contact reason              |
      | CVCC          | Newborn Notification        |
      | CVIU - DJJ    | Newborn Notification - CVIU |
      | CVIU - DOC    | Newborn Notification - CVIU |
      | CVIU - Jails  | Newborn Notification - CVIU |
      | CVIU - Other  | Newborn Notification - CVIU |

  @CP-29492 @CP-29492-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @khazar
  Scenario Outline: Verify Action dropdown values for Newborn Notification for unidentified contact
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I click on unidentified contact record type radio button
    When I select business unit "<business unit>" and contact reason "<contact reason>"
    And I verify contact action multi select drop down values
      | Escalated to Supervisor |
      | Submitted Deeming Form  |
    Examples:
      | business unit | contact reason              |
      | CVCC          | Newborn Notification        |
      | CVIU - DJJ    | Newborn Notification - CVIU |
      | CVIU - DOC    | Newborn Notification - CVIU |
      | CVIU - Jails  | Newborn Notification - CVIU |
      | CVIU - Other  | Newborn Notification - CVIU |

  @CP-31958 @CP-31958-01 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: VA| Add Web Chat Business Unit, Reasons and Actions -Active Contact: reason
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I select channel type as web chat
    And I verify Contact Reason for Cover VA are associated with Business Unit "Web Chat"
      | Appeals                   |
      | Eligibility               |
      | Medicaid                  |
      | Member Information        |
      | New/Existing Applications |

  @CP-31958 @CP-31958-02 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario Outline: VA| Add Web Chat Business Unit, Reasons and Actions -Active Contact: action
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I select channel type as web chat
    And I verify Contact Reason for Cover VA "<ContactReasons>" and associated Contact Action for Business Unit "Web Chat"
    Examples:
      | ContactReasons            |
      | Appeals                   |
      | Eligibility               |
      | Medicaid                  |
      | Member Information        |
      | New/Existing Applications |
