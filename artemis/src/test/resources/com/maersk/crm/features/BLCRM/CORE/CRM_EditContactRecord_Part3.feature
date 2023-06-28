Feature: Edit Contact Record History: Part 3

  @CP-19015 @CP-19015-01 @CP-19015-02 @ui-core @crm-regression @kamil
  Scenario Outline: Verify Phone Field Required/Cannot Be Blank in Edit Contact
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
    Then I verify when attend to Save Phone field with blank value error "PHONE is required and cannot be left blank"
    Then I verify when I saving Phone field less than 10 chars getting error "PHONE must be 10 characters"
    Examples:
      | contactType | language |
      | General     | Russian  |


  @CP-19015 @CP-19015-03 @CP-19015-04 @ui-core @crm-regression @kamil
  Scenario Outline: Verify Reason for Edit - Altered Existing Value
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Then I send API CALL for create CONTACT Record after creating new Consumer with data
      | consumerPhone             | <phone>       |
      | consumerEmail             | email::       |
      | contactType               | <contactType> |
      | consumerFirstName         | name::        |
      | consumerLastName          | name::        |
      | organizationName          | name::        |
      | consumerType              | Media         |
      | contactTranslationService | <language>    |
      | contactRecordStatusType   | <status>      |
    Given I logged into CRM
    Then I navigate to Contact Record Search Page
    Then Wait for 6 seconds
    Then I will verify search with Phone number
    Then I clicking on first Contact Record
    When I click on edit button on contact details tab
    Then I added valid phone number
    Then I choose Reason for Edit dropdown populated with value
      | Correcting Contact Details |
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab
    And Verify EDIT History with Reason for Edit "Correcting Contact Details"
    Examples:
      | contactType | language | phone   | status   |
      | General     | Russian  | phone:: | Complete |


  @CP-19015 @CP-19015-03.1 @ui-core @crm-regression @kamil
  Scenario Outline: Verify Reason for Edit - Added Value When Phone Field Was Null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Then I send API CALL for create CONTACT Record after creating new Consumer with data
      | consumerPhone             | <phone>       |
      | consumerEmail             | email::       |
      | contactType               | <contactType> |
      | consumerFirstName         | name::        |
      | consumerLastName          | name::        |
      | organizationName          | name::        |
      | consumerType              | Media         |
      | contactTranslationService | <language>    |
      | contactRecordStatusType   | <status>      |
    Given I logged into CRM
    Then I navigate to Contact Record Search Page
    Then Wait for 6 seconds
    Then I will verify search with Phone number
    Then I clicking on first Contact Record
    When I click on edit button on contact details tab
    Then I added valid phone number
    Then I choose Reason for Edit dropdown populated with value
      | Adding Contact Details |
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab
    And Verify EDIT History with Reason for Edit "Adding Contact Details"
    Examples:
      | contactType | language | phone  | status   |
      | General     | Russian  | null:: | Complete |

  @CP-19015 @CP-19015-03.2 @ui-core @crm-regression @kamil
  Scenario Outline: Verify Multiple Edits
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
    Then I choose Reason for Edit dropdown populated with value
      | Correcting Contact Details |
    And I have added a value when the Program field was null with Reason for Edit "Adding Contact Details"
    And Verify changes should be reflected in the Edit History for Multiple Edits
    Examples:
      | contactType | language |
      | General     | Russian  |


  @CP-19015 @CP-19015-03.3 @ui-core @crm-regression @kamil
  Scenario Outline: Verify Error for Incorrect Reason for Edit
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Then I send API CALL for create CONTACT Record after creating new Consumer with data
      | consumerPhone             | <phone>       |
      | consumerEmail             | email::       |
      | contactType               | <contactType> |
      | consumerFirstName         | name::        |
      | consumerLastName          | name::        |
      | organizationName          | name::        |
      | consumerType              | Media         |
      | contactTranslationService | <language>    |
      | contactRecordStatusType   | <status>      |
    Given I logged into CRM
    Then I navigate to Contact Record Search Page
    Then Wait for 6 seconds
    Then I will verify search with Phone number
    Then I clicking on first Contact Record
    When I click on edit button on contact details tab
    Then I added valid phone number
    Then I will see the error message for Phone Field "<ContactReasons>" with incorrect Reason for Edit
    Examples:
      | contactType | language | ContactReasons                     | contactType | language | phone   | status   |
      | General     | Russian  | Adding Additional Comment          | General     | Russian  | phone:: | Complete |
      | General     | Russian  | Adding Contact Details             | General     | Russian  | phone:: | Complete |
      | General     | Russian  | Adding Contact Reason/Action       | General     | Russian  | phone:: | Complete |
      | General     | Russian  | Correcting Additional Comment      | General     | Russian  | phone:: | Complete |
      | General     | Russian  | Correcting Case/Consumer Link      | General     | Russian  | phone:: | Complete |
      | General     | Russian  | Correcting Contact Reason/Action   | General     | Russian  | phone:: | Complete |
      | General     | Russian  | Correcting Disposition             | General     | Russian  | phone:: | Complete |
      | General     | Russian  | Correcting Third Party Information | General     | Russian  | phone:: | Complete |