Feature: Contact Type & Channel Iconography

  #@CRM-1207 @CRM-1207-01a @muhabbat @crm-regression @crm-smoke @ui-core
  #Duplicate script
  Scenario Outline: View Contact Type & Channel Icon
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I save the contact as "<contactType>" and "<contactChannelType>"
    And I navigate to contact history page after re-initiating a new contact with the same Consumer
    Then I see expected "<contactType&ChanelIcon>" on Contact Record History Page
    Examples:
      | contactType | contactChannelType | contactType&ChanelIcon |
      | Outbound     | Web Chat           | webchat_out                |

  @CRM-1207 @CRM-1207-01 @muhabbat @crm-regression @ui-core
  Scenario Outline: View Contact Type & Channel Icon
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I save the contact as "<contactType>" and "<contactChannelType>"
    And I navigate to contact history page after re-initiating a new contact with the same Consumer
    Then I see expected "<contactType&ChanelIcon>" on Contact Record History Page
    Examples:
      | contactType | contactChannelType | contactType&ChanelIcon |
      | Inbound     | Phone              | phone_in               |
      | Inbound     | Email              | mail_in                |
      | Inbound     | SMS Text           | sms_in                 |
      | Inbound     | Web Chat           | webchat_in             |
      | Outbound    | Phone              | phone_out              |
      | Outbound    | Email              | mail_out               |
      | Outbound    | SMS Text           | sms_out                |
      | Outbound    | Web Chat           | webchat_out            |


  @CRM-1207 @CRM-1207-02a @muhabbat @crm-regression  @crm-smoke @ui-core
  #Duplicate script
  Scenario Outline: View Contact Type & Channel Icon
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I save the contact as "<contactType>" and "<contactChannelType>"
    And I navigate to contact history page after re-initiating a new contact with the same Consumer
    Then I see expected "<title>" when hover over "<contactType&ChanelIcon>" on Contact Record History Page
    Examples:
      | contactType | contactChannelType | contactType&ChanelIcon | title         |
      | Inbound     | Phone              | phone_in               | Inbound Phone     |

  @CRM-1207 @CRM-1207-02b @muhabbat @crm-regression @ui-core
  Scenario Outline: View Contact Type & Channel Icon
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I save the contact as "<contactType>" and "<contactChannelType>"
    And I navigate to contact history page after re-initiating a new contact with the same Consumer
    Then I see expected "<title>" when hover over "<contactType&ChanelIcon>" on Contact Record History Page
    Examples:
      | contactType | contactChannelType | contactType&ChanelIcon | title             |
      | Inbound     | Email              | mail_in                | Inbound Email     |
      | Inbound     | Phone              | phone_in               | Inbound Phone     |
      | Inbound     | SMS Text           | sms_in                 | Inbound SMS Text  |
      | Inbound     | Web Chat           | webchat_in             | Inbound Web Chat  |
      | Outbound    | Phone              | phone_out              | Outbound Phone    |
      | Outbound    | Email              | mail_out               | Outbound Email    |
      | Outbound    | SMS Text           | sms_out                | Outbound SMS Text |
      | Outbound    | Web Chat           | webchat_out            | Outbound Web Chat |
