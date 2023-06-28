Feature: Edit Contact Record History: Part 2

  @CP-14476 @CP-14476-01 @ui-core @crm-regression @aikanysh #failing due to CP-36638
  Scenario: Edit Contact Record: Correcting Action/Reason| Same Contact Reason as before & Add 1 Action
    Given I logged into CRM
    When I navigate to Contact Record search
    And I searched customer have First Name as "Dont" and Last Name as "Edit"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I edit above CR by keeping same Contact Reason as before & Adding Action
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab
    And Verify on EDIT History page that Reason and Action is concatenated and Reason for Edit is "Correcting Contact Reason/Action"
    And I navigate back to contact details page
    And I clean up after editing the CR by returning previous reason and action value

  @CP-14476 @CP-14476-02 @ui-core @crm-regression @aikanysh   #failing due to CP-36638
  Scenario: Edit Contact Record: Correcting Action/Reason|  Same Contact Reason as before & Change Action
    Given I logged into CRM
    When I navigate to Contact Record search
    And I searched customer have First Name as "Dont" and Last Name as "Edit"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I edit above CR by keeping same Contact Reason as before & Changing existing Action
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab
    And Verify on EDIT History page that Action is changed and Reason for Edit is "Correcting Contact Reason/Action"
    And I navigate back to contact details page
    And I clean up after editing the CR by returning previous reason and action value

  @CP-14476 @CP-14476-03 @ui-core @crm-regression @aikanysh   #failing due to CP-36638
  Scenario: Edit Contact Record: Correcting Action/Reason|  Change Contact Reason
    Given I logged into CRM
    When I navigate to Contact Record search
    And I searched customer have First Name as "Dont" and Last Name as "Edit"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I edit above CR by changing Contact Reason and choosing new appropriate action
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab
    And Verify on EDIT History page that Reason is changed and Reason for Edit is "Correcting Contact Reason/Action"
    And I navigate back to contact details page
    And I clean up after editing the CR by returning previous reason and action value


  @CP-4896 @CP-4896-01 @ui-core @crm-regression @kamil
  Scenario: Translation/language line service was used during the Contact
    Given I logged into CRM and click on initiate contact
    And I see a Translation Service on field displayed as described below
      | English    |
      | Spanish    |
      | Russian    |
      | Vietnamese |
      | Other      |
    Then I navigate to "THIRD PARTY CONTACT" tab
    And I see a Translation Service on field displayed as described below
      | English    |
      | Spanish    |
      | Russian    |
      | Vietnamese |
      | Other      |
    Then I navigate to "UNIDENTIFIED CONTACT" tab
    And I see a Translation Service on field displayed as described below
      | English    |
      | Spanish    |
      | Russian    |
      | Vietnamese |
      | Other      |


  @CP-4896 @CP-4896-02 @CP-4896-03 @ui-core @crm-regression @kamil
  Scenario Outline: I am able to view/Edit Translation/language information captured in Contact Record
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Then I send API CALL for create CONTACT Record after creating new Consumer with data
      | consumerPhone             | phone::       |
      | consumerEmail             | email::       |
      | contactType               | <contactType> |
      | consumerFirstName         | name::        |
      | consumerLastName          | name::        |
      | organizationName          | name::        |
      | consumerType              | Media         |
      | contactTranslationService | <language>    |
    Given I logged into CRM
    Then I navigate to Contact Record Search Page
    Then Wait for 6 seconds
    Then I will verify search with Phone number
    Then I clicking on first Contact Record
    And Verify language "<language>"  captured in the Translation Service
    Then Verify I am able to edit information captured in the Translation Service
    Examples:
      | contactType          | language   |
      | General              | Russian    |
      | Third Party          | Spanish    |
      | Unidentified Contact | Vietnamese |


  @CP-18402 @CP-18402-01 @CP-18402-02 @ui-core @crm-regression @kamil
  Scenario Outline: Verify Program Field Requires a Value in Edit Contact
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Then I send API CALL for create CONTACT Record after creating new Consumer with data
      | consumerPhone             | phone::       |
      | consumerEmail             | email::       |
      | contactType               | <contactType> |
      | consumerFirstName         | name::        |
      | consumerLastName          | name::        |
      | organizationName          | name::        |
      | consumerType              | Media         |
      | contactTranslationService | <language>    |
      | programTypes              | null::        |
    Given I logged into CRM
    Then I navigate to Contact Record Search Page
    Then Wait for 6 seconds
    Then I will verify search with Phone number
    Then I clicking on first Contact Record
    When I click on edit button on contact details tab
    And I click on Save button in Edit screen
    Then I will see the on-field error "PROGRAM is required and cannot be left blank"
    Then Verify Program Dropdown Does Not Have a Null or Blank Option
    Examples:
      | contactType | language |
      | General     | Russian  |


  @CP-18402 @CP-18402-03 @CP-18402-04 @ui-core @crm-regression @kamil
  Scenario Outline: Verify Reason For Edit - Altered Existing Value
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Then I send API CALL for create CONTACT Record after creating new Consumer with data
      | consumerPhone             | phone::       |
      | consumerEmail             | email::       |
      | contactType               | <contactType> |
      | consumerFirstName         | name::        |
      | consumerLastName          | name::        |
      | organizationName          | name::        |
      | consumerType              | Media         |
      | contactTranslationService | <language>    |
    Given I logged into CRM
    Then I navigate to Contact Record Search Page
    Then Wait for 6 seconds
    Then I will verify search with Phone number
    Then I clicking on first Contact Record
    When I click on edit button on contact details tab
    And I have altered the existing value in the Program field with Reason for Edit "Correcting Contact Details"
    Examples:
      | contactType | language |
      | General     | Russian  |


  @CP-18402 @CP-18402-04.1 @CP-18402-04.2 @CP-18402-05 @ui-core @crm-regression @kamil
  Scenario Outline: Verify Reason for Edit - Added Value When Field Was Null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Then I send API CALL for create CONTACT Record after creating new Consumer with data
      | consumerPhone             | phone::       |
      | consumerEmail             | email::       |
      | contactType               | <contactType> |
      | consumerFirstName         | name::        |
      | consumerLastName          | name::        |
      | organizationName          | name::        |
      | consumerType              | Media         |
      | contactTranslationService | <language>    |
      | programTypes              | null::        |
    Given I logged into CRM
    Then I navigate to Contact Record Search Page
    Then Wait for 6 seconds
    Then I will verify search with Phone number
    Then I clicking on first Contact Record
    When I click on edit button on contact details tab
    And I have added a value when the Program field was null with Reason for Edit "Adding Contact Details"
    Then Verify changes should be reflected in the Edit History, displaying the previous value and the new value
    Examples:
      | contactType | language |
      | General     | Russian  |


  @CP-18402 @CP-18402-04.3 @ui-core @crm-regression @kamil
  Scenario Outline: Verify Program Field Error for Incorrect Reason for Edit
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Then I send API CALL for create CONTACT Record after creating new Consumer with data
      | consumerPhone             | phone::       |
      | consumerEmail             | email::       |
      | contactType               | <contactType> |
      | consumerFirstName         | name::        |
      | consumerLastName          | name::        |
      | organizationName          | name::        |
      | consumerType              | Media         |
      | contactTranslationService | <language>    |
    Given I logged into CRM
    Then I navigate to Contact Record Search Page
    Then Wait for 6 seconds
    Then I will verify search with Phone number
    Then I clicking on first Contact Record
    When I click on edit button on contact details tab
    Then I will see the error message for Program Field "<ContactReasons>" with incorrect Reason for Edit
    Examples:
      | contactType | language | ContactReasons                     |
      | General     | Russian  | Adding Additional Comment          |
      | General     | Russian  | Adding Contact Details             |
      | General     | Russian  | Adding Contact Reason/Action       |
      | General     | Russian  | Correcting Additional Comment      |
      | General     | Russian  | Correcting Case/Consumer Link      |
      | General     | Russian  | Correcting Contact Reason/Action   |
      | General     | Russian  | Correcting Disposition             |
      | General     | Russian  | Correcting Third Party Information |



