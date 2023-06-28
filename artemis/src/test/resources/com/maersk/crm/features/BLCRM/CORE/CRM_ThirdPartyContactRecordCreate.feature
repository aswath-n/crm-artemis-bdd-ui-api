Feature: Create Third Party Contact Record


  @CRM-1188 @CRM-1188-01 @shruti @crm-regression @ui-core @sang
  Scenario Outline: Verify 3rd party contact record is created successfully and viewed on the Contact History Page
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I store "CONTACT ID AND NAME OF CONTACT" for use in verification
    When I click on "Active Contact" Tab on Contact Dashboard Page
    And I enter Third Party Details Panel with the following field values
      | FIRST NAME         | <First Name>         |
      | LAST NAME          | <Last Name>          |
      | ORGANIZATION NAME  | <Organization Name>  |
      | CONSUMER TYPE      | <Consumer Type>      |
      | PREFERRED LANGUAGE | <Preferred Language> |
    And I complete active contact by filling out all minimal required fields
    And I click on initiate a contact button
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    Then I verify newly added contact record with third party details displayed in contact history grid
    Examples:
      | First Name | Last Name | Organization Name | Consumer Type | Preferred Language |
      | John       | Smith     | ABC Group         | Media         | English            |

  @CRM-1188 @CRM-1188-02 @shruti @crm-regression @ui-core @sang
  Scenario Outline: Verify Third party & Contact details match for Inbound Contact Type- Phone & SMS text channel type
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I store "CONTACT ID AND NAME OF CONTACT" for use in verification
    When I click on "Active Contact" Tab on Contact Dashboard Page
    And I enter Third Party Details Panel with the following field values
      | FIRST NAME         | <First Name>         |
      | LAST NAME          | <Last Name>          |
      | ORGANIZATION NAME  | <Organization Name>  |
      | CONSUMER TYPE      | <Consumer Type>      |
      | PREFERRED LANGUAGE | <Preferred Language> |
    And I select "<Program>" "<ContactChannelType>" "<ContactType>" "<Phone Number>" for active contact selection and save contact record
    And I click on initiate a contact button
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I select the "previously" ui created contact record
    Then I verify third party details are displayed as read only fields
    And I verify that the displayed details match with the details entered while creating contact "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    And I verify that contact channel type and contact channel details are matching "<ContactChannelType>" and "<Phone Number>"
    Examples:
      | First Name | Last Name | Organization Name | Consumer Type | Preferred Language | Program   | ContactChannelType | ContactType | Phone Number |
      | John       | Smith     | ABC Group         | Media         | English            | Program A | Phone              | Inbound     | 9012345678   |
      | John       | Smith     | ABC Group         | Media         | English            | Program A | SMS Text           | Inbound     | 9012345678   |
      | John       | Smith     | ABC Group         | Media         | English            | Program A | Phone              | Outbound    | 9012345678   |
      | John       | Smith     | ABC Group         | Media         | English            | Program A | SMS Text           | Outbound    | 9012345678   |

  @CRM-1188 @CRM-1188-03 @shruti @crm-regression @ui-core @sang
  Scenario Outline: Verify Third party & Contact details match for-Inbound Contact Type- Email & Web Chat Channel Type
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I store "CONTACT ID AND NAME OF CONTACT" for use in verification
    When I click on "Active Contact" Tab on Contact Dashboard Page
    And I enter Third Party Details Panel with the following field values
      | FIRST NAME         | <First Name>         |
      | LAST NAME          | <Last Name>          |
      | ORGANIZATION NAME  | <Organization Name>  |
      | CONSUMER TYPE      | <Consumer Type>      |
      | PREFERRED LANGUAGE | <Preferred Language> |
    And I select "<Program>" "<ContactChannelType>" "<ContactType>" "<Email>" for active contact selection and save contact record
    And I click on initiate a contact button
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I select the "previously" ui created contact record
    Then I verify third party details are displayed as read only fields
    And I verify that the displayed details match with the details entered while creating contact "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    And I verify that contact channel type and contact channel details are matching "<ContactChannelType>" and "<Email>"
    Then I verify contact record details in contact history is displayed as "<User Name>"
    Examples:
      | First Name | Last Name | Organization Name | Consumer Type | Preferred Language | Program   | ContactChannelType | ContactType | Email           | User Name          |
      | John       | Smith     | ABC Group         | Media         | English            | Program A | Web Chat           | Inbound     | abctest@xyz.com | Service AccountOne |
      | John       | Smith     | ABC Group         | Media         | English            | Program A | Email              | Inbound     | abctest@xyz.com | Service AccountOne |
      | John       | Smith     | ABC Group         | Media         | English            | Program A | Web Chat           | Outbound    | abctest@xyz.com | Service AccountOne |
      | John       | Smith     | ABC Group         | Media         | English            | Program A | Email              | Outbound    | abctest@xyz.com | Service AccountOne |