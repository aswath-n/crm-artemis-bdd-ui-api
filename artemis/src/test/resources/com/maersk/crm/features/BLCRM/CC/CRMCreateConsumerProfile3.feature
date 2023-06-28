@mk
Feature: Additional validations on Add/Create a Consumer Profile

  @CP-20788 @CP-20788-01 @crm-regression @ui-cc @chopa
  Scenario: Verify Add Consumer button is present from Inbound Search
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I select "Case/Consumer" option from Create Links(s) dropdown
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI

  @CP-20788 @CP-20788-02 @crm-regression @ui-cc @chopa
  Scenario: Verify External IDs are present in Add Consumer Page from Inbound Search
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I select "Case/Consumer" option from Create Links(s) dropdown
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I see all fields and buttons present
    Then I verify able to add more than on external ID
      | ExternalId_program |
      | CHIP,MEDICAID      |

  @CP-20788 @CP-20788-03 @crm-regression @ui-cc @chopa
  Scenario: Verify Cancel button from Add Consumer Page takes back to Inbound Search Page
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I select "Case/Consumer" option from Create Links(s) dropdown
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I select the back arrow navigated back to the search results screen
    Then I am navigated back to the Inbound Correspondence Details screen

  @CP-1247 @CP-1247-1.0 @crm-regression @ui-cc @JP
  Scenario: Display Created On Consumer profile
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    And I navigate to Contact Info Page
    Then I verify if 'Created On' value is displayed.

  @CP-9546 @CP-9546-1.1 @crm-regression @ui-cc @JP
  Scenario: Verify spoken languages configured for Create On Consumer profile
    Given I logged into CRM
    When I click on initiate contact record
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify spoken languages configured
      | spoken_languages                               |
    #  | American Sign Language,English,Spanish,Unknown |  # changed due to CP-10961
      | English,Other,Russian,Spanish,Vietnamese|

  @CP-9546 @CP-9546-1.2 @crm-regression @ui-cc @JP
  Scenario: Verify written languages configured for Create On Consumer profile
    Given I logged into CRM
    When I click on initiate contact record
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify written languages configured
      | written_languages                               |
    #  | Braille Spanish,Spanish,Braille English,English |   # changed due to CP-1096
     | Braille English,Braille Spanish,English,Other,Russian,Spanish,Vietnamese |


  @CP-1423 @CP-1423-1 @crm-regression @ui-cc @JP
  Scenario: Verify email with field level validations for Create On Consumer profile
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify if email input field does not accept below invalid test data in create customer page
      | InvalidEmail    |
      | test@test.123:  |
      | test@test.c-    |
      | test@test..com  |
      | test@test.#%{   |
      | test..@test.com |
    Then I verify email input field accepts below valid test data in create customer page
      | ValidEmail                                                                |
      | myemail@test.com                                                          |
      | test@test.gjghgghfhgfhjkjhkjhkjhjkhkjhghjhjghjgujhgjhgftjhghjfhhjgfhg1kju |
      | test@gjghgghfhgfhjkjhkjhkjhjkhkjhghjhjghjgujhgjhgftjhghjfhhjgfhg1kju.test |
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    Then I see new consumer is created and has a unique Consumer ID

 # @CP-18547 @CP-18547-1.0 @crm-regression @ui-cc @JP --------------------->>>>>>>>>>>>>   muted due to duplacate scenario
  #Scenario: Verify Program dropdown value On Consumer profile
  #  Given I logged into CRM and click on initiate contact
   # When I enter Search criteria fields for a new consumer
  #  And I click on Search Button on Search Consumer Page
   # And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
  #  Then I verify program dropdown value behaviour if External ID is missing
   #   | ExternalId_program |
   #   | CHIP,MEDICAID      |

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
      | ExternalId_program |
      | CHIP,MEDICAID      |


  @CP-17955 @CP-17955-1.1 @crm-regression @ui-cc @JP
  Scenario: Verify removal of external IDs On Consumer profile
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify removal of every external ID added
      | ExternalId_program |
      | CHIP,MEDICAID      |

  @CP-19023 @CP-19023-1.0 @crm-regression @ui-cc @JP
  Scenario: Verify Program, Consumer ID of external IDs On Consumer profile
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with external id
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    Then I verify program,consumer ID are visible On Consumer profile

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

  @CP-1158 @CP-1158-1.0 @crm-regression @ui-cc @JP
  Scenario: Manually Create Consumer
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    When I navigate to Case Consumer Advance Search
    And I search for consumer details in case consumer search
    Then I verify Add Consumer button

  @CP-1158 @CP-1158-2 @crm-regression @ui-cc @JP
  Scenario: Navigate to Create Consumer UI
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    When I navigate to Case Consumer Advance Search
    And I search for consumer details in case consumer search
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify search cretria fields are populated in Create Consumer UI with the values i have entered

  @CP-1158 @CP-1158-3.0 @crm-regression @ui-cc @JP
  Scenario: Navigate to Consumer Profile
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    When I navigate to Case Consumer Advance Search
    And I search for consumer details in case consumer search
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I minimize active contact gadget popup if populates
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    Then I verify navigated to the Consumer Profile of the consumer created

  @CP-1158 @CP-1158-4.0 @crm-regression @ui-cc @JP
  Scenario: Navigate to Consumer Profile
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    When I navigate to Case Consumer Advance Search
    And I search for consumer details in case consumer search
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I minimize active contact gadget popup if populates
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    Then I verify navigated to the Consumer Profile of the consumer created
    And I click on the back arrow button
    Then I see Search Results are details of consumer created

  @CP-17313 @CP-17313-1.0 @crm-regression @ui-cc @JP
  Scenario: Verify Add Primary Consumer Email Flag - Add New Email
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    When I add new email to check Primary Consumer Email Flag
    Then I verify Primary Consumer Email Flag is enabled

  @CP-17313 @CP-17313-1.2 @crm-regression @ui-cc @JP
  Scenario: Verify warning when Primary Indicator is already populated for this Consumer for Email
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    When I add new email to check default Primary Consumer Email Flag
    Then I verify warning message when Primary Email is available

  @CP-17313 @CP-17313-1.3 @crm-regression @ui-cc @JP
  Scenario: Verify Add Primary Consumer Email Flag UI - Default Primary Email
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    When I add new email to check default Primary Consumer Email Flag
    Then I verify consumer email number primary indicator added on UI

  @CP-17313 @CP-17313-1.4 @crm-regression @ui-cc @JP
  Scenario: Verify Add Primary Consumer Email Flag using API- Save in Backend
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    When I add new email to check default Primary Consumer Email Flag
    When I will get the Authentication token for "BLCRM" in "CRM"
    Then I verify consumer email primary indicator added using API

  @CP-17313 @CP-17313-2.0 @crm-regression @ui-cc @JP
  Scenario: Verify Primary Consumer Email Flag - Edit Email
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    When I add new email to check default Primary Consumer Email Flag
    And I edit existing email to check primary indicator flag
    Then I verify Primary Consumer Email Flag is enabled

  @CP-17313 @CP-17313-2.1 @crm-regression @ui-cc @JP
  Scenario: Verify warning when Primary Indicator is already populated - Edit existing Email
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    When I add new email to check default Primary Consumer Email Flag
    Then I verify warning message when editing already available email

  @CP-17313 @CP-17313-2.2 @crm-regression @ui-cc @JP
  Scenario: Verify Primary Consumer Email Flag Default in UI - Edit Email
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I add new email to check default Primary Consumer Email Flag
    And I update existing email to set primary indicator flag
    Then I verify consumer email number primary indicator added on UI

  @CP-17313 @CP-17313-2.3 @crm-regression @ui-cc @JP
  Scenario: Verify Primary Consumer Email Flag Default using API - Edit Email
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I add new email to check default Primary Consumer Email Flag
    And I update existing email to set primary indicator flag
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I verify consumer email primary indicator added using API

  @CP-9524 @CP-9524-01 @chopa @crm-regression @ui-cc
  Scenario: Verify Race options in Create Consumer UI page for BLCRM
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify race configured
      | race                                                                                                                                                   |
      | American Indian or Alaska Native,Asian,Black or African American,More than one race,Native Hawaiian or Other Pacific Islander,Other Race,Unknown,White |

  @CP-9524 @CP-9524-02 @chopa @crm-regression @ui-cc
  Scenario: Verify Ethnicity options in Create Consumer UI page for BLCRM
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify ethnicity configured
      | ethnicity                                                   |
      | Hispanic or Latino,Not Hispanic or Latino,Unknown |

  @CP-9524 @CP-9524-03 @chopa @crm-regression @ui-cc
  Scenario: Verify Citizenship options in Create Consumer UI page for BLCRM
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify citizenship configured
      | citizenship                 |
      | Citizen,Non-Citizen,Unknown |

  @CP-9524 @CP-9524-04 @chopa @crm-regression @ui-cc
  Scenario: Verify Residency options in Create Consumer UI page for BLCRM
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify residency configured
      | residency                     |
      | Resident,Non-Resident,Unknown |


