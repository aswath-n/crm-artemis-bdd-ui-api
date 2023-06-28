Feature: Edit Third Party Contact Record Part 2

  @EB-344 @EB-344-01 @shruti @crm-regression @ui-core
  Scenario: Verify design updated to third party contact screen
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    Then I verify sections displayed in third party contact record

  @EB-344 @EB-344-02 @shruti @crm-regression @ui-core
  Scenario: Verify Wrap-up and close section is displayed after the contact details section
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    Then I verify wrap up and close section is displayed after contact details section


  @EB-344 @EB-344-03 @shruti @crm-regression @ui-core @sang
  Scenario: Verify Consumer in Contact section and Consumer contact about section headers are displayed
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    Then I verify sections displayed in third party contact record after link

  @CP-1155 @CP-1155-01 @paramita @crm-regression @ui-core @sang
  Scenario: Validate 3rd Party Contact Details Design on create
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
      | FIRST NAME         | John      |
      | LAST NAME          | Smith     |
      | ORGANIZATION NAME  | ABC Group |
      | CONSUMER TYPE      | Media     |
      | PREFERRED LANGUAGE | English   |
    And I select "Program A" "Web Chat" "Outbound" "abctest@xyz.com" for active contact selection and save contact record
    And I click on initiate a contact button
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I select the "previously" ui created contact record
    Then I verify 'Third Party Details' header relabel to 'Third Party-Consumer in Contact'
    And I verify available tabs name
    And I verify Standard Links Component
    And I verify Case ID and Consumer ID reference to the Standard Link Component in THIRD PARTY - CONSUMER CONTACTED ABOUT component
    And I verify Edit button displayed at page level
    And I verify wrap up and close component is displayed below contact details section

  @CP-1155 @CP-1155-02 @paramita @crm-regression @ui-core @sang
  Scenario: Validate 3rd Party Contact Details Design while EDIT
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
      | FIRST NAME         | John      |
      | LAST NAME          | Smith     |
      | ORGANIZATION NAME  | ABC Group |
      | CONSUMER TYPE      | Media     |
      | PREFERRED LANGUAGE | English   |
    And I select "Program A" "Web Chat" "Outbound" "abctest@xyz.com" for active contact selection and save contact record
    And I click on initiate a contact button
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I select the "previously" ui created contact record
    When I click on edit icon the Contact Details page
    And I enter Third Party Details Panel with the following field values
      | FIRST NAME | Paul |
    And I select the following Reasons for Edit values in the Wrap up and Close panel for Contact Record edit page
      | Correcting Third Party Information |
    And I click on save button in edit third party contact record
    Then I verify different sections of Third Party components in sequence order
