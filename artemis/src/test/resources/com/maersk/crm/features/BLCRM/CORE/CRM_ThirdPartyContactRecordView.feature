Feature: View Third Party Contact Record

  @CRM-1187 @CRM-1187-01 @shruti @crm-regression @ui-core @sang
  Scenario: Verify mandatory fields for the third party contact record
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    When I enter Consumer details "Ethan" on contact record UI
    Then I verify that third party details header is displayed
    Then I verify that third party first name is displayed
    Then I verify that third party last name is displayed
    And I verify that third party organization name is displayed
    And I verify that third party consumer drop down displayed with correct values
      | Agency   |
      | Provider |
      | Media    |
    And I verify that third party preferred language drop down displayed with correct values
      | English    |
      | Other      |
      | Russian    |
      | Spanish    |
      | Vietnamese |
    And I verify Third Party tab dropdown "CONTACT TYPE" displays the following values
      | Inbound  |
      | Outbound |
    And I verify Third Party tab dropdown "contact channel" displays the following values
      | Email            |
      | Phone            |
      | SMS Text         |
      | Web Chat         |
      | IVR Self-Service |
      | Mobile App       |
      | Web Portal       |

  @CRM-1187 @CRM-1187-02 @shruti @crm-regression @ui-core @sang
  Scenario: Verify Reset Button Functionality  the third party contact record
    Given I logged into CRM and click on initiate contact
    And I click on third party contact record type radio button
    When I populate Search criteria fields for a new consumer for third party contact
    And I click on reset button on Contact Record UI
    Then I see Search criteria fields have no value for third party contact


  @CRM-1187 @CRM-1187-03 @shruti @crm-regression @ui-core @sang
  Scenario Outline: Save Button Functionality
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    Given I logged into CRM and click on initiate contact
    And I click on third party contact record type radio button
    When  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    And I capture current contact phone number
    And I select contact program type as "<Program>"
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I end the current call
    And I close the current Active Contact
    Then I see initiate a contact button on CRM Dashboard
    Examples:
      | First Name | Last Name | Organization Name | Consumer Type | Preferred Language | Program   |
      | John       | Smith     | ABC Group         | Media         | English            | Program A |


  @CRM-1187 @CRM-1187-04 @shruti @crm-regression @ui-core @sang
  Scenario: View Consumer CASE ID and CONSUMER ID After Linking in Third party tab with First and Last name search
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    Then I verify the Case and Consumer Ids are displayed after the third party link to contact record

  @CRM-1187 @CRM-1187-05 @shruti @crm-regression @ui-core @sang
  Scenario: View Consumer CASE ID and CONSUMER ID After Linking in Third party tab with SSN search
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "SSN"
    And I link the "First" consumer searched from the third party tab to the active contact
    Then I verify the Case and Consumer Ids are displayed after the third party link to contact record

  @CRM-1187 @CRM-1187-06 @shruti @crm-regression @ui-core @sang
  Scenario:3rd party Contact Record - Cancel Button Functionality
    Given I logged into CRM and click on initiate contact
    And I click on third party contact record type radio button
    And I click on cancel button and see a message
    And I confirm cancellation on the message
    Then I should be navigated to dashboard

  @CRM-1187 @CRM-1187-07 @shruti @crm-regression @ui-core @sang #Functionality changed: Special chars allowed in the fields from comment in CP-15121
  Scenario Outline: Field Validations for Third party Details section
    Given I logged into CRM and click on initiate contact
    And I click on third party contact record type radio button
#    When I enter third party first name as "<SpecialCahracters>"
#    Then I verify that first name is not accepted any letters other than alphabets
    When I enter third party first name as "<MoreThan30Chars>"
    Then I verify that first name accepts maximum thirty characters
#    When I enter third party last name as "<SpecialCahracters>"
#    Then I verify that last name is not accepted any letters other than alphabets
    When I enter third party last name as "<MoreThan30Chars>"
    Then I verify that last name accepts maximum thirty characters
#    When I enter third party organization name as "<SpecialCahracters>"
#    Then I verify that organization name accepts only alphanumeric values
    When I enter third party organization name as "<MoreThan50Chars>"
    Then I verify that organization name accepts maximum fifty characters
    Examples:
      | SpecialCahracters                          | MoreThan30Chars                                       | MoreThan50Chars                                      |
      | 3!@#$%He%^&* ()_+-=e[{]}]\|;:'r<>?,./mione | EnterMoreThan ThirtyCharacters ForMaxLengthValidation | EnterMoreThan FiftyCharacters ForMaxLengthValidation |

  @CRM-1187 @CRM-1187-08 @shruti @crm-regression @ui-core #Claims is added as part of CP-16726
  Scenario: Verification of values in Inbound and Outbound queue
    Given I logged into CRM and click on initiate contact
    And I click on third party contact record type radio button
    And I click on the Contact Type "Inbound"
    Then I verify that following dropdown options for Inbount Queue Call field are displayed
      | Eligibility               |
      | Enrollment                |
      | General Program Questions |
      | Claims                    |
    And I click on the Contact Type "Outbound"
    Then I verify that following dropdown options for Call Campaign Reference field are displayed
      | Enrollment Reminder |
      | Payment Reminder    |
      | Program Information |
    Then I verify that following dropdown options for Outcome Of Contact field are displayed
      | Did Not Reach/Left Voicemail |
      | Did Not Reach/No Voicemail   |
      | Invalid Phone Number         |
      | Reached Successfully         |

  @CRM-1187 @CRM-1187-11 @shruti @crm-regression @ui-core @sang
  Scenario: Verify contact channel changed based on contact channel type selected
    Given I logged into CRM and click on initiate contact
    And I click on third party contact record type radio button
    When I select contact channel type as "Phone"
    Then I verify contact phone number filed is displayed
    And I verify contact email filed is not displayed
    When I select contact channel type as "Email"
    Then I verify contact email filed is displayed
    And I verify contact phone number filed is not displayed
    When I select contact channel type as "SMS Text"
    Then I verify contact phone number filed is displayed
    And I verify contact email filed is not displayed
    When I select contact channel type as "Web Chat"
    Then I verify contact email filed is displayed
    And I verify contact phone number filed is not displayed