Feature: Create a Consumer Profile - Part Two

  @CP-18547 @CP-18547-1 @crm-regression @ui-cc @JP
  Scenario: Verify Program dropdown value On Consumer profile
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify program dropdown value behaviour if External ID is missing
      | ExternalId_program | CHIP,MEDICAID |

  @CP-18547 @CP-18547-1.1 @crm-regression @ui-cc @JP
  Scenario: Verify removing of External ID Type and  External ID On Consumer profile
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify removing of External ID Type, External ID fields

  @CP-17955 @CP-17955-1.0 @crm-regression @ui-cc @JP
  Scenario: Verify able to add greater than one External ID On Consumer profile
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify able to add more than on external ID
      | ExternalId_program | CHIP,MEDICAID |

  @CP-17955 @CP-17955-1.1 @crm-regression @ui-cc @JP
  Scenario: Verify removal of external IDs On Consumer profile
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify removal of every external ID added
      | ExternalId_program | CHIP,MEDICAID |

  @CP-19023 @CP-19023-10 @crm-regression @ui-cc @JP
  Scenario: Verify Program, Consumer ID of external IDs On Consumer profile
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with external id
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    Then I verify program,consumer ID are visible On Consumer profile

  @CP-10961 @CP-10961-1-1 @crm-regression @ui-cc @JP
  Scenario: Verify fields on Edit Consumer profile page
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with external id
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    Then I ensure below fields are present in edit customer detail page
      | Gender             | Female,Male,Neutral,Other,Unknown        |
      | Corp_Pref          | Paperless                                |
      | written_languages  | English,Spanish,Russian,Vietnamese,Other |
      | spoken_languages   | English,Spanish,Russian,Vietnamese,Other |
      | ExternalId_program | CHIP,MEDICAID                            |

  @CP-10961 @CP-10961-2.0 @crm-regression @ui-cc @JP
  Scenario: Verify error messages for mandatory fields on Edit Consumer profile page
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with external id
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    Then I verify error messages for mandatory fields in edit consumer profile page
      | Field_Name |
      | FIRST NAME |
      | LAST NAME  |
      | DOB        |
      | START DATE |

  @CP-10961 @CP-10961-30 @crm-regression @ui-cc @JP
  Scenario: Verify successful messages during consumer update on Edit Consumer profile page
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with external id
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    Then I verify update consumer successful message
    When I click on "Active Contact" Tab on Contact Dashboard Page
    Then I verify created consumer linked to Active Contact

  @CP-10961 @CP-10961-4.0 @crm-regression @ui-cc @JP @events-cc
  Scenario: Verify publish CONSUMER_UPDATE_EVENT during consumer update on Edit Consumer profile page
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with external id
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    Then I verify update consumer successful message
    And I initiate the Events APi
    Then User send Api call to get "CONSUMER_UPDATE_EVENT"
    Then I verify updated consumer details in event list of response string

  @CP-10961 @CP-10961-5-0 @crm-regression @ui-cc @JP @events-cc
  Scenario: Verify communication preferences details after spoken language update on Edit Consumer profile page
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with external id
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I initiate the Events APi
    Then User send Api call to get "CONSUMER_SAVE_EVENT"
    And I capture communication preferences details before update
    Then I update spoken language as "Spanish" on Edit Consumer profile page
    And I initiate the Events APi
    Then User send Api call to get "CONSUMER_UPDATE_EVENT"
    Then I verify capture communication preferences details after spoken language update

  @CP-10961 @CP-10961-6-0 @crm-regression @ui-cc @JP @events-cc
  Scenario: Verify communication preferences details when language not updated on Edit Consumer profile page
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with external id
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I initiate the Events APi
    Then User send Api call to get "CONSUMER_SAVE_EVENT"
    And I capture communication preferences details before update
    Then I update Phone Type as "Work" on Edit Consumer profile page
    And I initiate the Events APi
    Then User send Api call to get "CONSUMER_UPDATE_EVENT"
    Then I verify capture communication preferences details when language not updated

  @CP-10961 @CP-10961-7.0 @crm-regression @ui-cc @JP @events-cc
  Scenario: Verify communication preferences details after Correspondence Preference updated to null on Edit Consumer profile page
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with external id
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I initiate the Events APi
    Then User send Api call to get "CONSUMER_SAVE_EVENT"
    And I capture communication preferences details before update
    Then I update Correspondence Preference as "Paperless" on Edit Consumer profile page
    And I initiate the Events APi
    Then User send Api call to get "CONSUMER_UPDATE_EVENT"
    Then I verify capture communication preferences details after Correspondence Preference updated

  @CP-1435 @CP-1435-1.1  @crm-regression @ui-cc @JP
  Scenario Outline: Verify consumer creation of Consumer profile with external id changes
    Given I logged into CRM and click on initiate contact
    Given I initiated Create Consumer API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I can provide consumer information with "<consumerFirstName>" "<consumerLastName>" "<phoneNumber>" and "<addressZip>"
    And I can run create consumer API
    Then I try create new consumer with already existing consumer data except SSN, firstName, lastName, DOB, EXTN ID with MEDICAID
    Then I will verify an newly created consumer first,last name by searching on UI
    Examples:
      | consumerFirstName | consumerLastName | phoneNumber | addressZip | projectName |
      | {random}          | {random}         | {random}    | {random}   |[blank]|

  @CP-17312 @CP-17312-1.0 @crm-regression @ui-cc @JP
  Scenario: Verify Add Primary Consumer Phone Flag - Add New Phone
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    When I add new phone number to check Primary Consumer Phone Flag
    Then I verify Primary Consumer Phone Flag is enabled

  @CP-17312 @CP-17312-1.2 @crm-regression @ui-cc @JP
  Scenario: Verify warning when Primary Indicator is already populated for this Consumer for Phone
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    When I add new phone number to check default Primary Consumer Phone Flag
    Then I verify warning message when Primary Phone is available

  @CP-17312 @CP-17312-1.3 @crm-regression @ui-cc @JP
  Scenario: Verify Add Primary Consumer Phone Flag UI - Default Primary Phone
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    When I add new phone number to check default Primary Consumer Phone Flag
    When I will get the Authentication token for "BLCRM" in "CRM"
    Then I verify consumer phone number primary indicator added on UI

  @CP-17312 @CP-17312-1.4 @crm-regression @ui-cc @JP
  Scenario: Verify Add Primary Consumer Phone Flag using API- Save in Backend
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    When I add new phone number to check default Primary Consumer Phone Flag
    When I will get the Authentication token for "BLCRM" in "CRM"
    Then I verify consumer phone number primary indicator added using API

  @CP-17312 @CP-17312-2.0 @crm-regression @ui-cc @JP
  Scenario: Verify Primary Consumer Phone Flag - Edit Phone
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I edit existing phone number to check primary indicator flag
    Then I verify Primary Consumer Phone Flag is enabled

  @CP-17312 @CP-17312-2.1 @crm-regression @ui-cc @JP
  Scenario: Verify warning when Primary Indicator is already populated - Edit existing Phone
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    When I add new phone number to check default Primary Consumer Phone Flag
    Then I verify warning message when editing already available phone number

  @CP-17312 @CP-17312-2.2 @crm-regression @ui-cc @JP
  Scenario: Verify Primary Consumer Phone Flag Default in UI - Edit Phone
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I update existing phone number to set primary indicator flag
    When I will get the Authentication token for "BLCRM" in "CRM"
    Then I verify consumer phone number primary indicator added on UI

  @CP-17312 @CP-17312-2.3 @crm-regression @ui-cc @JP
  Scenario: Verify Primary Consumer Phone Flag Default using API - Edit Phone
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I update existing phone number to set primary indicator flag
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I verify consumer phone number primary indicator added using API