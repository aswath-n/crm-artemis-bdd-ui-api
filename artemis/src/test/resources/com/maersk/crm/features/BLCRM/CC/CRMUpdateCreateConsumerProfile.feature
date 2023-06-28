Feature: Updated Create Consumer Profile

  @CP-338 @CP-338-01 @crm-regression @muhabbat @ui-cc
  Scenario Outline: Verification of new drop-down fields on Updated Create Consumer Profile Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I see new "<fieldName>" field on Updated Create Consumer Profile Page
    Examples:
      | fieldName         |
#      | PreferredLanguage | functionality chage with written ans spoken language fields added
#      | ConsumerIdType    |
      | ConsumerType      |
      | PhoneNumberType   |
      | ExternalId        |

  @CP-338 @CP-338-02 @crm-regression @muhabbat   @ui-cc #failed due to CP-11351
  Scenario Outline: Creating a Consumer Profile with one piece of Demographic Information on Updated Create Consumer Profile Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I add mandatory DOB field value on Create consumer profile UI Page
    When I complete Consumer profile and save with "<fieldName>"
    And I select Continue Button on Opt-in warning message
    Then I see new consumer is created and has a unique Consumer ID
    Examples:
      | fieldName |
      | Address   |
      | Phone     |
      | Email     |


  @CP-338 @CP-338-03 @crm-regression @muhabbat  @ui-cc #failed due to CP-11351
  Scenario Outline: Capturing Default Consumer Information on Updated Create Consumer Profile Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I add mandatory DOB field value on Create consumer profile UI Page
    When I complete Consumer profile and save with "<fieldName>"
    And I select Continue Button on Opt-in warning message
    And I navigate to Contact Info Page
    Then I see "<demographic>" Start Date will be defaulted to the Current System Date
    Examples:
      | demographic | fieldName |
      | Address     | Address   |
      | Phone       | Phone     |
      | Email       | Email     |


  @CP-338 @CP-338-04 @crm-regression @muhabbat @ui-cc
  Scenario Outline: Validation of default field values on Updated Create Consumer Profile Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I see "<fieldName>" field has default "<value>" value capture on Updated Create Consumer Profile Page
    Examples:
      | fieldName         | value    |
#      | PreferredLanguage | English  | functionality chage with written ans spoken language fields added
      | ConsumerType      | Consumer |


  @CP-338 @CP-338-06 @CP-338-05 @crm-regression @muhabbat @ui-cc
  Scenario Outline: Required Related Fields on Updated Create Consumer Profile Page and error messages
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I add mandatory DOB field value on Create consumer profile UI Page
    When I populate one of the related fields "<relatedField>" on Create Consumer Profile page
    And I click on Create Consumer Button
    Then I see error message if required field "<fieldName>" is not captured
    Examples:
      | relatedField    | fieldName       |
      | phoneNumber     | phoneNumberType |
      | phoneNumberType | phoneNumber     |
      | externalIdType  | externalId      |
      | externalId      | externalIdType  |
      | addressType     | addressLine1    |
      | addressLine1    | addressType     |


  @CP-338 @CP-338-06 @crm-regression @muhabbat   @ui-cc #failed due to CP-11351
  Scenario Outline: Required Fields on Updated Create Consumer Profile Page and First & Last name error messages
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I clear all fields values
#    And I clear Consumer Type option on Updated Create Consumer Profile Page
    And I click on Create Consumer Button
    Then I see error message if required field "<fieldName>" is not captured
    Examples:
      | fieldName |
      | firstName |
      | lastName  |


  @CP-338 @CP-338-06 @crm-regression @muhabbat @ui-cc
  Scenario Outline: Required Fields on Updated Create Consumer Profile Page and Phone/Address/Email error messages
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I add mandatory DOB field value on Create consumer profile UI Page
    And I click on Create Consumer Button
    Then I see error message if required field "<fieldName>" is not captured
    Examples:
      | fieldName    |
      | phoneNumber  |
      | addressLine1 |
      | email        |


  @CP-338 @CP-338-07 @crm-regression @muhabbat @ui-cc
  Scenario: Navigate Back Functionality of Updated Create Consumer Profile Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I add mandatory DOB field value on Create consumer profile UI Page
    When I click on navigate back arrow on updated Create consumer profile page
    And I click on continue button on warning message
    Then I verify system navigates back to Contact Record UI
