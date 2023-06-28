Feature: Validate CTI Call Functionality

  @CP-10775 @CP-10775-01 @aikanysh  #need to run as standalone, don't add to regression
  Scenario Outline: Validate Contact Record Fields are populated with information sent with CTI call | Inbound
    Given I logged into the CRM Application
    When I initiated CTI API Call
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I populate CTI Call Information with "<contactType>", "<inboundCallQueue>", "<contactChannelType>","<ani>"
    And I verify sent above "<contactType>", "<inboundCallQueue>","<contactChannelType>" and "<ani>" are displayed on UI

    Examples:
      | contactType | inboundCallQueue | contactChannelType | ani |
      | Inbound     | Eligibility       | Phone              | random      |

  @CP-10775 @CP-10775-02 @aikanysh  #need to run as standalone, don't add to regression
  Scenario Outline: Validate Contact Record Fields are populated with information sent with CTI call | Outbound
    Given I logged into the CRM Application
    When I initiated CTI API Call
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I populate CTI Call Information with "<contactType>", "<inboundCallQueue>", "<contactChannelType>","<ani>"
    And I verify sent above "<contactType>", "<inboundCallQueue>","<contactChannelType>" and "<ani>" are displayed on UI

    Examples:
      | contactType  |inboundCallQueue|   contactChannelType    | ani        |
      | Outbound     |[blank]|    Phone              | random      |

  @CP-10775 @CP-10775-03 @aikanysh #need to run as standalone, don't add to regression
  Scenario Outline: Validate  Case/Consumer Search is NOT automatically performed as part of the Active Contact after CTI call
    Given I logged into the CRM Application
    When I initiated CTI API Call
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I populate CTI Call Information with "<contactType>", "<inboundCallQueue>", "<contactChannelType>","<ani>"
    And I verify sent above "<contactType>", "<inboundCallQueue>","<contactChannelType>" and "<ani>" are displayed on UI
    And I verify that Case/Consumer search is not performed

    Examples:
      | contactType | inboundCallQueue | contactChannelType | ani |
      | Inbound     | Eligibility       | Phone              | random      |

  @CP-10775 @CP-10775-04 @aikanysh #need to run as standalone, don't add to regression
  Scenario Outline: Validate Contact Record Fields are not changing when changing between: General, Third Party, and Unidentified
    Given I logged into the CRM Application
    When I initiated CTI API Call
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I populate CTI Call Information with "<contactType>", "<inboundCallQueue>", "<contactChannelType>","<ani>"
    And I verify sent above "<contactType>", "<inboundCallQueue>","<contactChannelType>" and "<ani>" are displayed on UI
    When I click on third party contact record type radio button
    And I verify sent above "<contactType>", "<inboundCallQueue>","<contactChannelType>" and "<ani>" are displayed on UI
    When I select "UNIDENTIFIED CONTACT" Contact Record Type
    And I verify sent above "<contactType>", "<inboundCallQueue>","<contactChannelType>" and "<ani>" are displayed on UI

    Examples:
      | contactType | inboundCallQueue | contactChannelType | ani |
      | Inbound     | Eligibility       | Phone              | random      |

  @CP-9469 @CP-9469-01 @vinuta @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: Display Telephony Widget for CSR Role
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    Then I verify that telephony widget is displayed

  @CP-9469 @CP-9469-02 @vinuta @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: Do not display Telephony Widget for Non-CSR Role
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    Then I verify that telephony widget is not displayed

  @CP-9469 @CP-9469-03 @CP-9469-04 @vinuta @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: Collapse & Expand Telephony Widget
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    Then I verify telephony widget is "expanded"
    When I click on collapse on telephony widget
    Then I verify telephony widget is "collapsed"
