Feature: Create a Consumer Profile

  @CRM-300 @CRM-300-01 @muhabbat @CRM-905-01 @crm-regression @ui-cc
  Scenario: Verification Add Consumer button is present and functions
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI


  @CRM-300 @CRM-300-02 @muhabbat @crm-regression @ui-cc
  Scenario: Create consumer fields auto-filled with search values
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I see previously populated criteria fields are auto-filled


  @CRM-300 @CRM-300-03 @muhabbat @crm-regression @ui-cc
  Scenario: Verification of fields on Create Consumer UI
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I see all fields and buttons present


  @CRM-300 @CRM-300-04 @muhabbat @crm-regression @ui-cc
  Scenario: Verification of mandatory fields on Create Consumer UI
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify "following" fields being marked mandatory
      | Consumer First Name |
      | Consumer Last Name  |
      | Consumer DOB        |
      | Consumer Type       |
      | Written Language    |
      | Spoken Language     |
    #functionality is changed with CP-10961, added required fields
#      | Zip Code            | Feature has changed with CP-338 implementation
#      | Phone Number        |

  @CRM-300 @CRM-300-05 @muhabbat @crm-regression @ui-cc #need to ask warning message to cancel
  Scenario: Verification of Cancel Button functionality
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I am navigated back to Search Contact Record UI page

  @CRM-300 @CRM-300-06 @muhabbat @crm-regression @crm-smoke @ui-cc @ui-cc-smoke
  Scenario: Verification of Save Consumer Button functionality
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    Then I see new consumer is created and has a unique Consumer ID

  @CRM-300 @CRM-300-07 @muhabbat @crm-regression @ui-cc
  Scenario: Verification Address fields accept up to 50 characters
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I see "addressLine1" field accept 50 characters in total
    And I see "addressLine2" field accept 50 characters in total


  @CRM-300 @CRM-300-10 @muhabbat @crm-regression @ui-cc
  Scenario: Verification City field accept up to 30 alphabetic characters
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I see "city" field accept 30 characters in total


  @CRM-300 @CRM-300-11 @muhabbat @crm-regression @ui-cc
  Scenario: Verification all states in State Dropdown are available
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I should be able to see all states in State dropdown are available

    #refactoring 10/17/18
  @CRM-300 @CRM-300-12 @muhabbat @crm-regression @ui-cc
  Scenario: Verification Zip consist out of Numbers only
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I see "<Zip Code>" field accepts up to 9 digits


  @CRM-300 @CRM-300-13 @muhabbat #@crm-regression muted as this functionality has changes with CP-338 implementation
  Scenario: Verification Unique ID field accept up to 30 alphanumeric characters
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I see "uniqueID" field accept only 30 characters
    Then I see "uniqueID" field accept only alphanumeric characters


  @CRM-575 @CRM-300 @CRM-482 @CRM-300-08 @CRM-482-02 @CRM-575-06 @muhabbat @shruti @vinuta @crm-regression @ui-cc
  Scenario: Validating Error message is displayed for the mandatory fields
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I click on Create Consumer Button
    #And I select Continue Button on Opt-in warning message // Opt In warning not displayed, raise bug if not a CR
    Then I see Please fill in the required field error message displayed


  @CRM-300 @CRM-300-09 @muhabbat #duplicate of CRM-482-05 by Shruti # not relevant sceanario CP-1435
  Scenario: Validating Error message is displayed when consumer details match an existing consumer
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I am navigated to active contact page
    And I click on Unlink Contact Button on Active Contact Page
    When I enter random "xyz" Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    And I click on Create Consumer Button on Create Consumer Page
    Then I see Consumer already exists, please associate existing Consumer to Case message displayed

    #refactoring 10/17/18
  @CRM-300 @CRM-300-14 @muhabbat @crm-regression @ui-cc
  Scenario: Verification Zip field has 5 digits required and 9 digits optional format
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I see 5 digits required and 9 digits optional format

  @CRM-482-03 @shruti @crm-regression @ui-cc
  Scenario: Verification of Save Consumer Button functionality
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    Then I am navigated to active contact page

  @CRM-482-04 @shruti @crm-regression @ui-cc
  Scenario: Verification of unique Consumer ID is generated functionality
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    And I am navigated to General Consumer in Contact UI page
    Then I see a unique Consumer Profile ID generated

  @CRM-575 @CRM-482 @CRM-482-05 @CRM-575-12 @shruti @vinuta  #removing regression tag , functionality has changed CP-1435
  Scenario: Verification of Duplicate consumer functionality
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    Then I am navigated to active contact page
    And I click on Unlink Contact Button on Active Contact Page
    When I enter random "xyz" Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    Then I see Consumer already exists, please associate existing Consumer to Case message displayed

#By Vinuta, Check for field-level validations on mandatory fields
  @CRM-575-10 @CRM-575 @vinuta @crm-regression @ui-cc
  Scenario: Validating error message on mandatory fields when input is in incorrect format
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I populate invalid phone,zipcode
    And I click on Create Consumer Button
   # And I select Continue Button on Opt-in warning message
    Then I see mandatory fields highlighted with error messages

  #By Vinuta, Check for field-level validations on optional fields
  @CRM-575-07 @CRM-575 @vinuta @crm-regression @ui-cc
  Scenario: Validating error message on optional fields when input is in incorrect format
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I populate Create Consumer fields for a new consumer
    And I populate invalid date, ssn, email
    And I click on Create Consumer Button
    Then I see optional fields highlighted with error messages

  @CRM-905-01 @shruti @CRM-905 @crm-regression @ui-cc
  Scenario: Verification Add Consumer button is displayed when consumer is not found
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button on Consumer Search Results Page

  @CRM-905-02 @crm-regression @CRM-905 @shruti @crm-smoke @ui-cc
  Scenario: Verify Add Consumer Button Availability All the time (even when consumer is displayed in search result)
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    Then I am navigated to active contact page
    And I click on Unlink Contact Button on Active Contact Page
    When I enter Search criteria fields for the new consumer created
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button on Consumer Search Results Page

  @CRM-905-03 @shruti @CRM-905 @crm-regression @ui-cc
  Scenario: Verify Add Consumer button is not displayed when consumer search is not performed
    Given I logged into CRM and click on initiate contact
    Then  Add Consumer button is not displayed on Consumer Search Results Page

  @CRM-905-04 @shruti @CRM-905 @crm-regression @ui-cc
  Scenario: Verify Add Consumer button is not displayed when blank consumer search is performed
    Given I logged into CRM and click on initiate contact
    When I click on Search Button without entering search parameters
    Then  Add Consumer button is not displayed on Consumer Search Results Page

#new Added 11/12/18
  @CRM-864-01 @shruti @CRM-864 #removed cmr-regression tag due to changes EB-272 & EB-338 06/03/19
  Scenario: Verification Email Opt in Selected email ID becomes mandatory filed
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I select Email Opt in check box
    Then I see Email becomes mandatory

  @CRM-864-02 @shruti @CRM-864 #removed cmr-regression tag due to changes EB-272 & EB-338 06/03/19
  Scenario: Verification Error is displayed if Email address is not entered when Email Optin is selected
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I select Email Opt in check box and leave Email field blank and click on create
    Then I verify Error is displayed for Email address field

  @CRM-864-03 @shruti @CRM-864 #removed cmr-regression tag due to changes EB-272 & EB-338 06/03/19
  Scenario: Verification Error is not displayed if Email address is blank when Email Option is not selected
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I select Email Opt out check box and leave Email field blank and click on create
    Then I verify Error is not displayed for Email address field

  @CRM-1087-01 @CRM-1087 @muhabbat #removing regression tag new implementation CP-7451
  Scenario: Verification Consumer Role and Preferred Language fields displayed
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    And I am navigated to active contact page
    Then I see Consumer Role and Preferred Language fields displayed

  @CRM-1087-02 @CRM-1087 @muhabbat #removing regression tag new implementation CP-9456
  Scenario: Verification of default Values of Consumer Role Type and Add Preferred Language
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I am navigated to active contact page
    Then I see default value "" for Consumer Type and "English" for Preferred Language displayed

  @CRM-722 @CRM-722-03 @vinuta @crm-regression @ui-cc
  Scenario: Validate dob error on create consumer page
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I enter dob as "02322000" and click on Save
   # And I select Continue Button on Opt-in warning message
    Then I see the error that date does not exists
    And I enter dob as "02/02/2028" and click on Save
   # And I select Continue Button on Opt-in warning message
    Then I see the error that DOB is in future


  @CRM-1296-01 @CRM-1296 @muhabbat @crm-regression @ui-cc
  Scenario: Verify Error is displayed for Address Line 1 when only special characters are entered
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I enter only special character in Address Line one field
    Then I verify error is displayed for the Address Line one

  #@CRM-1296-02 @CRM-1296 @muhabbat @crm-regression @ui-cc validation is not reqired as this functionality has changed Re: CP-15961
  Scenario: Verify Error is displayed for Address Line 2 when only special characters are entered
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I enter only special character in Address Line two field
    Then I verify error is displayed for the Address Line two

  @EB-346 @EB-346-01 @muhabbat @crm-regression @ui-cc
  Scenario: Verification Create Consumer UI is not a pop-up
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    When I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify Create Consumer UI is inline with the application by checking on hidden element from Contact Record UI page
    And I verify Back Arrow button is displayed and has "CREATE CONSUMER" text

  @EB-346 @EB-346-02 @muhabbat @crm-regression @ui-cc
  Scenario: Verification Back Arrow button functionality on Create Consumer UI
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    When I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I click on Back Arrow to navigate away from Create Consumer Page
    Then I verify system navigates back to Contact Record UI

  @CP-274 @CP-274-01 @aikanysh @crm-regression @ui-cc
  Scenario: Correspondence preference dropdown is displayed and options are selectable
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    When I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And correspondence preference dropdown is displayed

  @CP-274 @CP-274-02 @aikanysh @crm-regression @ui-cc
  Scenario: Correspondence preference dropdown is displayed and options are selectable-2
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    When I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And correspondence preference dropdown is displayed and "Paperless" option is selectable

  @CP-274 @CP-274-03 @aikanysh @crm-regression @ui-cc
  Scenario:  When saving new consumer without selecting correspondence preference put under this tag CRM-300-06
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    When I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    Then I see new consumer is created and has a unique Consumer ID

  @CP-274 @CP-274-04 @CP-274_TC_04 @aikanysh @crm-regression @ui-cc
  Scenario:  When saving new consumer select multiple correspondence preferences
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    When I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer  and select single "Paperless" option for "Correspondence Preference"
    When I click on Create Consumer Button
    Then I see new consumer is created and has a unique Consumer ID