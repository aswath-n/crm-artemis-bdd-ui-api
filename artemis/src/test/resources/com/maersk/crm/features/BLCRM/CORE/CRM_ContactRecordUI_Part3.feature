Feature: Contact Record UI Part 3


  @CRM-1548 @CRM-1548-01 @muhabbat @ui-core @crm-regression
  Scenario Outline:Inbound Call Queue field is captured only as an Inbound Phone Contact
    Given I logged into CRM and click on initiate contact
    And I select a "<contactRecordType>" as a contact Record Type
    When I select a "<contactType>" as Contact Type dropdown
    And I select "<contactChannelType>" as a contact Channel Type
    Then I am able to verify if Inbound Call Queue is possible to capture "<applicable>"
    Examples:
      | contactRecordType    | contactType | contactChannelType | applicable |
      | GENERAL CONTACT      | Inbound     | Phone              | true       |
      | GENERAL CONTACT      | Inbound     | SMS Text           | false      |
      | GENERAL CONTACT      | Inbound     | Email              | false      |
      | GENERAL CONTACT      | Inbound     | Web Chat           | false      |
      | GENERAL CONTACT      | Outbound    | Phone              | false      |
      | GENERAL CONTACT      | Outbound    | SMS Text           | false      |
      | GENERAL CONTACT      | Outbound    | Email              | false      |
      | GENERAL CONTACT      | Outbound    | Web Chat           | false      |
      | UNIDENTIFIED CONTACT | Inbound     | Phone              | true       |
      | UNIDENTIFIED CONTACT | Inbound     | SMS Text           | false      |
      | UNIDENTIFIED CONTACT | Inbound     | Email              | false      |
      | UNIDENTIFIED CONTACT | Inbound     | Web Chat           | false      |
      | THIRD PARTY CONTACT  | Inbound     | Phone              | true       |
      | THIRD PARTY CONTACT  | Inbound     | SMS Text           | false      |
      | THIRD PARTY CONTACT  | Inbound     | Email              | false      |
      | THIRD PARTY CONTACT  | Inbound     | Web Chat           | false      |
      | THIRD PARTY CONTACT  | Outbound    | Phone              | false      |
      | THIRD PARTY CONTACT  | Outbound    | SMS Text           | false      |
      | THIRD PARTY CONTACT  | Outbound    | Email              | false      |
      | THIRD PARTY CONTACT  | Outbound    | Web Chat           | false      |