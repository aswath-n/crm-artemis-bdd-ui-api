@fix3
Feature: Primary Individual Information
  #Test data refactored by Vinuta
  @CRM-754 @CRM-754-01 @vinuta #@crm-regression
  Scenario Outline: Validate primary individual section in demographic details
    Given I logged into CRM and click on initiate contact
    When I enter First Name as <firstName>, Middle Initial as <middleName>, Last Name as <lastName>, SSN as <ssn>, Date Of Birth as <DOB>, Unique ID as <ID>  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    Examples:
      | firstName | middleName | lastName | ssn | DOB | ID |
      | 'Linda'       | ''         | ''       | ''  | ''  | '' |

  @CRM-754 @CRM-754-02 @vinuta #@crm-regression
  Scenario: Validate start date & end date are displayed on hover over PI row
    Given I logged into CRM and click on initiate contact
    When I enter First Name as "Linda", Middle Initial as "", Last Name as "", SSN as "", Date Of Birth as "", Unique ID as ""  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    And I hover over status of the first PI
    Then I verify that the start and end dates are displayed for PI

  @CRM-755 @CRM-755-01 @CRM-754 @CRM-754-05 @vinuta #@crm-regression
  Scenario: Verify Add New Primary Individual - Required Information Validation
    Given I logged into CRM and click on initiate contact
    When I enter First Name as "Linda", Middle Initial as "", Last Name as "", SSN as "", Date Of Birth as "", Unique ID as ""  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I should see the Add button displayed for primary individual
    When I click on the Add button for primary individual
    And I verify that I am in the Add Primary Individual Page
    Then I verify the fields displayed on Add Primary Individual Page

  @CRM-755 @CRM-755-02 @CRM-754 @vinuta #@crm-regression
  Scenario:Verify Error is displayed for Mandatory fields on Add Primary Individual Page
    Given I logged into CRM and click on initiate contact
    When I enter First Name as "Linda", Middle Initial as "", Last Name as "", SSN as "", Date Of Birth as "", Unique ID as ""  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I should see the Add button displayed for primary individual
    When I click on the Add button for primary individual
    And I verify that I am in the Add Primary Individual Page
    And I click on save without entering the mandatory fields
    Then I see error message populated below each field on Add Primary Individual Page

  @CRM-755 @CRM-755-01 @CRM-755-05 @CRM-754 @vinuta #@crm-regression
  Scenario Outline: :Verify Error is displayed for Invalid /Incorrect Format for the fields on Add Primary Individual Page
    Given I logged into CRM and click on initiate contact
    When I enter First Name as "Linda", Middle Initial as "", Last Name as "", SSN as "", Date Of Birth as "", Unique ID as ""  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I should see the Add button displayed for primary individual
    When I click on the Add button for primary individual
    And I verify that I am in the Add Primary Individual Page
    And I provide invalid data for <iteration>,<firstName>,<middleName>,<lastName>,<DOB>,<age>,<startDate>,<endDate>,<SSN>
    Then I see valid field error message populated below each field on Add Primary Individual Page
    Examples:
      | iteration | firstName            | middleName     | lastName             | DOB          | age   | startDate    | endDate       | SSN          |
      | 1         | '123:>%^&~'          | '345#$%~`"/>:' | '456%`~[]  \\*'      | 'gg\{;.'     | '123' | 'dx/}:./;'   | 'dfhg!~`\;\{' | 'ddsd!&*)>'  |
      | 2         | 'qaw bvckjdkjdvdcjs' | 'qa g'         | 'qaw bvckjdkjdvdcjs' | '12/12/2020' | '10'  | '12/12/2020' | '12/12/2010'  | '233'        |
      | 3         | 'First'              | 'M'            | 'Last'               | '13/13/2050' | ''    | '13/13/5050' | '45/55/1452'  | '45  51'     |
      | 4         | 'First'              | 'M'            | 'Last'               | '51'         | ''    | '96/2'       | '85/535'      | '1253456789' |
      | 5         | 'First'              | 'M'            | 'Last'               | '02/32/2010' | ''    | '11/32/2018' | '03/55/2017'  | '123456789'  |
      | 6         | 'First'              | 'M'            | 'Last'               | '02/20/2010' | ''    | '12/12/2018' | '12/12/2018'  | '123456789'  |


  @CRM-755 @CRM-755-03 @CRM-755-05 @CRM-754 @vinuta #@crm-regression
  Scenario: Verify new primary individual can be saved successfully
    Given I logged into CRM and click on initiate contact
    When I enter First Name as "Linda", Middle Initial as "", Last Name as "", SSN as "", Date Of Birth as "", Unique ID as ""  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I should see the Add button displayed for primary individual
    When I click on the Add button for primary individual
    And I verify that I am in the Add Primary Individual Page
    And I populate all mandatory details of primary individual
    And I click on Save button on add primary individual page
    Then I click on continue button on warning message
    Then I verify primary individual section displayed
    And New primary individual should be added to the PI list
    And I verify status of primary individual as "ACTIVE"

  @CRM-755 @CRM-755-04 @CRM-754 @vinuta #@crm-regression
  Scenario: Verify cancel button on add primary individual page
    Given I logged into CRM and click on initiate contact
    When I enter First Name as "Lisa", Middle Initial as "", Last Name as "", SSN as "", Date Of Birth as "", Unique ID as ""  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I should see the Add button displayed for primary individual
    When I click on the Add button for primary individual
    And I verify that I am in the Add Primary Individual Page
    And I populate all mandatory details of primary individual
    And I click on Cancel button on add primary individual page
    Then I verify primary individual section displayed

  @CRM-755 @CRM-755-05 @CRM-754 @vinuta #@crm-regression
  Scenario:Verify Status of the PI as Inactive when start date and end date are in the past
    Given I logged into CRM and click on initiate contact
    When I enter First Name as "Lisa", Middle Initial as "", Last Name as "", SSN as "", Date Of Birth as "", Unique ID as ""  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I should see the Add button displayed for primary individual
    When I click on the Add button for primary individual
    And I verify that I am in the Add Primary Individual Page
    And I populate all mandatory details of primary individual
    And I give start date and end date in the past and save
    Then I click on continue button on warning message
    Then New primary individual should be added to the PI list
    And I verify status of primary individual as "INACTIVE"

  @CRM-755 @CRM-755-05 @CRM-754 @vinuta #@crm-regression
  Scenario:Verify Status of the PI as Active when start date in the past and end date as current date
    Given I logged into CRM and click on initiate contact
    When I enter First Name as "Meredith", Middle Initial as "", Last Name as "", SSN as "", Date Of Birth as "", Unique ID as ""  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I should see the Add button displayed for primary individual
    When I click on the Add button for primary individual
    And I verify that I am in the Add Primary Individual Page
    And I populate all mandatory details of primary individual
    And I give start date in the past and end date as current date and save
    Then New primary individual should be added to the PI list
    And I verify status of primary individual as "ACTIVE"

#  @CRM-755 @CRM-755-05 @CRM-754 @vinuta this functionality has retired with CP-7451 implementation
  Scenario:Verify Status of the PI as Active when start date as current date and end date in future
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Ethan" and Last Name as "Hunt"
    And I link the contact to an existing Case or Consumer Profile with first name "Ethan" and last name "Hunt"
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I should see the Add button displayed for primary individual
    When I click on the Add button for primary individual
    And I verify that I am in the Add Primary Individual Page
    And I populate all mandatory details of primary individual
    And I give start date as current date and end date in future and save
    Then New primary individual should be added to the PI list
    And I verify status of primary individual as "ACTIVE"

  @CRM-755 @CRM-755-05 @CRM-754 @vinuta #@crm-regression
  Scenario:Verify Status of the PI as Inactive when start date and end date in future
    Given I logged into CRM and click on initiate contact
    When I enter First Name as "Diana", Middle Initial as "", Last Name as "Mildred", SSN as "", Date Of Birth as "", Unique ID as ""  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I should see the Add button displayed for primary individual
    When I click on the Add button for primary individual
    And I verify that I am in the Add Primary Individual Page
    And I populate all mandatory details of primary individual
    And I give start date and end date in future and save
    Then New primary individual should be added to the PI list
    And I verify status of primary individual as "INACTIVE"

  @CRM-755 @CRM-755-05 @CRM-754 @vinuta #@crm-regression
  Scenario:Verify Status of the PI as Active when start date < current date, end date blank
    Given I logged into CRM and click on initiate contact
    When I enter First Name as "Timothy", Middle Initial as "", Last Name as "", SSN as "", Date Of Birth as "", Unique ID as ""  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I should see the Add button displayed for primary individual
    When I click on the Add button for primary individual
    And I verify that I am in the Add Primary Individual Page
    And I populate all mandatory details of primary individual
    And I give start date in past and save
    Then New primary individual should be added to the PI list
    And I verify status of primary individual as "ACTIVE"

  @CRM-755 @CRM-755-05 @CRM-754 @vinuta #@crm-regression
  Scenario:Verify Status of the PI as Inactive when start date > current date, end date blank
    Given I logged into CRM and click on initiate contact
    When I enter First Name as "Timothy", Middle Initial as "", Last Name as "Nelson", SSN as "", Date Of Birth as "", Unique ID as ""  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I should see the Add button displayed for primary individual
    When I click on the Add button for primary individual
    And I verify that I am in the Add Primary Individual Page
    And I populate all mandatory details of primary individual
    And I give start date in future and save
    Then New primary individual should be added to the PI list
    And I verify status of primary individual as "INACTIVE"

  @CRM-755 @CRM-755-06 @CRM-754 @CRM-754-04 @CRM-754-05 @vinuta #@crm-regression
  Scenario:Verify age is calculated based on PI DOB
    Given I logged into CRM and click on initiate contact
    When I enter First Name as "Brittany", Middle Initial as "", Last Name as "Brandon", SSN as "", Date Of Birth as "", Unique ID as ""  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I should see the Add button displayed for primary individual
    When I click on the Add button for primary individual
    And I verify that I am in the Add Primary Individual Page
    And I populate all mandatory details of primary individual
    Then I verify PI age is calculated on DOB
    And I click on Save button on add primary individual page
    Then I verify primary individual section displayed
    And New primary individual should be added to the PI list
    Then Age is displayed by calculating based on DOB on PI List Page
    And Preferred language has value as "English"

  @CRM-754 @CRM-754-06 @vinuta #@crm-regression
  Scenario:Verify only 2 PI are displayed at first glance on Demographic Section
    Given I logged into CRM and click on initiate contact
    When I enter First Name as "Brittany", Middle Initial as "", Last Name as "Edward", SSN as "", Date Of Birth as "", Unique ID as ""  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    And I click on the Add button for primary individual
    Then I click on continue button on warning message
    And I add three pi with active and inactive statuses
    Then I verify that two pis are display at first glance

  @CRM-754 @CRM-754-07 @vinuta #@crm-regression
  Scenario: Verify active primary individuals are displayed on top, followed by inactive
    Given I logged into CRM and click on initiate contact
    When I enter First Name as "Mary", Middle Initial as "", Last Name as "", SSN as "", Date Of Birth as "", Unique ID as ""  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    And I click on the Add button for primary individual
    And I add three pi with active and inactive statuses
    Then Active primary individuals are displayed on top followed by inactive

  @CRM-754 @CRM-754-08 @vinuta #@crm-regression
  Scenario: Verify primary individuals are sorted by start date ascending for active PI, by end date descending for inactive PI
    Given I logged into CRM and click on initiate contact
    When I enter First Name as "Meredith", Middle Initial as "", Last Name as "", SSN as "", Date Of Birth as "", Unique ID as ""  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    And I click on the Add button for primary individual
    And I add three pi with active and inactive statuses
    Then Active primary individuals are sorted by start date in ascending order
    And Inactive primary individuals are sorted by end date in descending order

  @CP-7451 @CP-7451-01 @umid @ui-cc @crm-regression
  Scenario: :Verify Split AR Component into 2 sections, Mandatory fields, PREGNANCY DUE DATE error message
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I should see the Add button displayed for primary individual
    When I click on the Add button for primary individual
    Then I will see information split into 2 sections
    And I verify consumer role "Primary Individual"
    When I select Gender as "Female" to verify pregnancy checkbox is displayed
    And I click pregnancy checkbox
    Then I verify Mandatory fields
    |Start Date|
    |Receive Correspondence|
    |First Name|
    |Last Name|
    |DOB|
    |SSN|
    |Gender|
    |Spoken Language|
    |Written Language|
    |Pregnancy Due Date|
    And I click on Save button on add primary individual page
    Then I verify "PREGNANCY DUE DATE" is required and cannot be left blank

  @CP-7451 @CP-7451-02 @umid @ui-cc @crm-regression
  Scenario: Verify error messages for mandatory fields and DOD NOTIFIED DATE & SOURCE
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I should see the Add button displayed for primary individual
    When I click on the Add button for primary individual
    And I click on Save button on add primary individual page
    Then I see error message populated below each field on Add Primary Individual Page
    And I fill DOD with past date
    And I click on Save button on add primary individual page
    Then I verify "DOD NOTIFIED DATE" is required and cannot be left blank
    Then I verify "DOD NOTIFICATION SOURCE  " is required and cannot be left blank

  @CP-7451 @CP-7451-03 @umid @ui-cc @crm-regression
  Scenario: :Verify All of the dropdown's for Primary Individual Page
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I should see the Add button displayed for primary individual
    When I click on the Add button for primary individual
    And I verify consumer role "Primary Individual"
    Then I verify the dropdown values for "Gender"
      |Female|
      |Male|
      |Unknown|
      |Neutral|
    Then I verify the dropdown values for "Ethnicity"
      |Hispanic or Latino|
      |Not Hispanic or Latino|
      |Unknown|
    Then I verify the dropdown values for "Race"
      |American Indian or Alaska Native|
      |Asian|
      |Black or African American|
      |More than one race       |
      |Native Hawaiian or Other Pacific Islander|
      |Other Race|
      |Unknown|
      |White       |
    Then I verify the dropdown values for "Citizenship"
      |Citizen|
      |Non-Citizen|
    Then I verify the dropdown values for "Residency"
      |Non-Resident|
      |Resident|
    Then I verify the dropdown values for "receiveCorrespondence"
      |Yes|
      |No |
    Then I verify the dropdown values for "spokenLanguage"
      |English|
      |Vietnamese|
      |Other|
      |Russian|
      |Spanish|
    Then I verify the dropdown values for "writtenLanguage"
      |English|
      |Vietnamese|
      |Other|
      |Russian|
      |Spanish|
    Then I verify the dropdown values for "Correspondence"
      |Paperless|

  @CP-7451 @CP-7451-04 @umid @ui-cc @crm-regression @CP-5796 @CP-5796-06 @CP-5796-07 @CP-5796-08 @CP-1824 @CP-1824-02 @CP-1824-05
  Scenario: Cancel & Back Arrow & Navigate to Contact search without editing
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I should see the Add button displayed for primary individual
    When I click on the Add button for primary individual
    And I click on cancel button on create task type screen
    Then I verify primary individual section displayed
    When I click on the Add button for primary individual
    When I navigate away by clicking the back arrow without saving changes on this page
    Then I verify primary individual section displayed
    When I navigate to Contact Record Search Page
    When I am navigated back to Contact Record UI page

  @CP-7451 @CP-7451-05 @umid @ui-cc @crm-regression @CP-5796 @CP-5796-06 @CP-5796-07 @CP-5796-08
  Scenario: Cancel & Back Arrow & Navigate to Contact search with editing warning message
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I should see the Add button displayed for primary individual
    When I click on the Add button for primary individual
    And I fill DOD with past date
    And I click on cancel button on create task type screen
    Then I verify warning is displayed with message "If you continue, all the captured information will be lost"
    And I click on the Cancel Button
    Then I will see information split into 2 sections
    When I navigate away by clicking the back arrow without saving changes on this page
    Then I verify warning is displayed with message "If you continue, all the captured information will be lost"
    And I click on the Cancel Button
    Then I will see information split into 2 sections
    When I navigate to Contact Record Search Page
    Then I verify warning is displayed with message "If you continue, all the captured information will be lost"

  @CP-7451 @CP-7451-06 @umid @ui-cc @crm-regression
  Scenario Outline: Profile Information fields Save, check format
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I click on first consumerID for Primary Individual
    And I provide invalid data for <iteration>,<firstName>,<middleName>,<lastName>,<DOB>,<age>,<startDate>,<endDate>,<SSN>
    When I click "yes" on all changes will be lost warning message
    Then New primary individual should be added to the PI list
    Examples:
      | iteration | firstName            | middleName     | lastName             | DOB          | age   | startDate    | endDate       | SSN          |
      | 1         | 'ADASDIOAJDHSJDK'   | ''             | 'DASDIOAJDHISJDA'    | '01/01/1945' | '' | '10/19/2020'   | '' | '121212212'  |

  @CP-5796 @CP-5796-01 @umid @ui-cc @crm-regression
  Scenario: View checkboxes with the PI’s current opt-in/out selections
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I click on first consumerID for Primary Individual
    Then I see the following checkboxes with the PI current opt-in and out selections

  @CP-5796 @CP-5796-02 @umid @ui-cc @crm-regression
  Scenario: Update each checkbox with the PI’s current opt-in/out selections
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I click on first consumerID for Primary Individual
    Then I am able to update each checkbox

  @CP-5796 @CP-5796-03 @umid @ui-cc @crm-regression
  Scenario: Save update each checkbox with the PI’s current opt-in/out selections
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I click on first consumerID for Primary Individual
    Then I am able to update each checkbox
    And I click on Save button on add primary individual page
    And I click on the continue button on warning pop up
    Then I verify primary individual section displayed

  @CP-5796 @CP-5796-04 @umid @ui-cc @crm-regression
  Scenario: Record Updated Date and Updated By fields
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I click on first consumerID for Primary Individual
    Then I am able to update each checkbox
    And I click on Save button on add primary individual page
    And I click on the continue button on warning pop up
    Then I verify primary individual section displayed
    And I get rawLogs for "Primary Individual" verify updated on by

  @CP-24513 @CP-24513-01 @chopa @ui-cc-in @crm-regression
  Scenario: "IN-EB" Verify the delete icon next to the Medicaid/RID is not present PI
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Brat" and Last Name as "Smith"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    When I click on first Primary Individual
    And I verify delete icon next to the MedicaidRID is not present

  @CP-24513 @CP-24513-02 @chopa @ui-cc-in @crm-regression
  Scenario: "IN-EB" Verify the delete icon next to the Medicaid/RID is not present CM
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Brat" and Last Name as "Smith"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    When I click on any existing Case Member
    And I verify delete icon next to the MedicaidRID is not present

  @CP-24350 @CP-24350-1.0 @crm-regression @ui-cc-in @chopa
  Scenario: "IN-EB" View Do Not Call Field in Create Consumer
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I search for a consumer and to click on Add Consumer button
    And I verify Do Not Call field label with checkbox is displayed

  #@CP-24350 @CP-24350-2.0 @crm-regression @ui-cc-in @chopa  muted due to changes in CP-30604 and CP-30248
  Scenario: "IN-EB" View Do Not Call Field in PI page
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Brat" and Last Name as "Smith"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    When I click on first Primary Individual
    And I verify Do Not Call field label with checkbox is displayed

  #@CP-24350 @CP-24350-3.0 @crm-regression @ui-cc-in @chopa   muted due to changes in CP-30604 and CP-30248
  Scenario: "IN-EB" View Do Not Call Field in CM page
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Brat" and Last Name as "Smith"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    When I click on any existing Case Member
    And I verify Do Not Call field label with checkbox is displayed

  @CP-24350 @CP-24350-4.0 @crm-regression @ui-cc-in @chopa
  Scenario: "IN-EB" View Do Not Call Field in AR page
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Brat" and Last Name as "Smith"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    When I click on the Add button for Authorized Representative
    And I verify Do Not Call field label with checkbox is displayed

  @CP-7888 @CP-7888-1.0 @crm-regression @ui-cc-in @chopa
  Scenario: "IN-EB" Verify 'Inactivate Immediately' check box is not displayed for Inactive consumers in PI, CM, AR
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Brat" and Last Name as "Smith"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    When I click on first Primary Individual
    And I verify "Inactive Immidiatelly" checkbox is not displayed and the consumer is inactive
    And I click on keyboard backspace button
    When I click on any existing Case Member
    And I verify "Inactive Immidiatelly" checkbox is not displayed and the consumer is inactive
    And I click on keyboard backspace button
    When I click on existing Authorized Representative Record
    And I verify "Inactive Immidiatelly" checkbox is not displayed and the consumer is inactive

  @CP-7888 @CP-7888-2.0 @crm-regression @ui-cc @chopa
  Scenario: "BLCRM" Verify 'Inactivate Immediately' check box is not displayed for Inactive consumers in PI, CM, AR
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Brat" and Last Name as "Smith"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    When I click on first Primary Individual
    And I verify "Inactive Immidiatelly" checkbox is not displayed and the consumer is inactive
    And I click on keyboard backspace button
    When I click on any existing Case Member
    And I verify "Inactive Immidiatelly" checkbox is not displayed and the consumer is inactive
    And I click on keyboard backspace button
    When I click on existing Authorized Representative Record
    And I verify "Inactive Immidiatelly" checkbox is not displayed and the consumer is inactive

  @CP-15629 @CP-15629-1 @crm-regression @ui-cc-in @chopa
  Scenario: "IN-EB" Verify 'Inactivate Immediately' check box is not displayed for address, phone and email
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Sdfsdf" and Last Name as "Sdfsdf"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I click on the Contact Info Tab
    And I expend the Address record on INEB contact page
    And I verify "Inactive Immidiatelly" checkbox is not displayed and the status is inactive
    And I click on keyboard backspace button
    And I click on existing phone number
    And I verify "Inactive Immidiatelly" checkbox is not displayed and the status is inactive
   #removed 2 step due to configuration changed no more email table on contact info page INEB

  @CP-15629 @CP-15629-2.0 @crm-regression @ui-cc @chopa
  Scenario: "BLCRM" Verify 'Inactivate Immediately' check box is not displayed for address, phone and email
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Brat" and Last Name as "Smith"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I click on the Contact Info Tab
    And I expend the Address Record on Contact Info Page
    And I verify "Inactive Immidiatelly" checkbox is not displayed and the status is inactive
    And I click on keyboard backspace button
    And I click on existing phone number
    And I verify "Inactive Immidiatelly" checkbox is not displayed and the status is inactive
    And I click on keyboard backspace button
    And I edit existing email to check primary indicator flag
    And I verify "Inactive Immidiatelly" checkbox is not displayed and the status is inactive

  @CP-11586 @CP-11586-01 @chopa @ui-cc @crm-regression
  Scenario: Back Arrow & Warning Message -Cancel button Add PI
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    When I click on the Add button for primary individual
    And I populate all mandatory details of primary individual
    And I click on Cancel button on add primary individual page
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      |PRIMARY INDIVIDUAL|
    Then I click on the back arrow button
    Then I verify warning popup is displayed with message
    And I click on the continue button on warning pop up
    Then I verify primary individual section displayed

  @CP-11586 @CP-11586-02 @chopa @ui-cc @crm-regression
  Scenario: Back Arrow & Warning Message -Cancel button Edit PI
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    And I click on first consumerID for Primary Individual
    Then I am able to update each checkbox
    And I click on Cancel button on add primary individual page
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      |PRIMARY INDIVIDUAL|
    Then I click on the back arrow button
    Then I verify warning popup is displayed with message
    And I click on the continue button on warning pop up
    Then I verify primary individual section displayed

  @CP-11586 @CP-11586-03 @chopa @ui-cc @crm-regression
  Scenario: Back Arrow & Warning Message -Cancel button Add Case Member
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    And I should see the Add button displayed for primary individual
    When I click on Add Button for Case Member
    Then I am able to update each checkbox
    And I click on Cancel button on add primary individual page
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    Then I will see information split into 2 sections
    Then I click on the back arrow button
    Then I verify warning popup is displayed with message
    And I click on the continue button on warning pop up
    Then I verify Case Member section displayed

  @CP-11586 @CP-11586-04 @chopa @ui-cc @crm-regression
  Scenario: Back Arrow & Warning Message -Cancel button Edit Case Member
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    When I click on any existing Case Member
    Then I am able to update each checkbox
    And I click on cancel button on create task type screen
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    Then I will see information split into 2 sections
    Then I click on the back arrow button
    Then I verify warning popup is displayed with message
    And I click on the continue button on warning pop up
    Then I verify Case Member section displayed

  @CP-11586 @CP-11586-05 @chopa @ui-cc @crm-regression
  Scenario: Back Arrow & Warning Message -Cancel button Add Authorized Representative
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    When I click on the Add button for Authorized Representative
    Then I fill in the following Authorized Representative values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And I click on cancel button on create task type screen
    Then I verify warning message is displayed
    And I click on cancel button on warning message
    Then I will see information split into 2 sections
    Then I click on the back arrow button
    Then I verify warning message is displayed
    And I click on the continue button on warning pop up
    Then I verify Authorized Representative section displayed

  @CP-11586 @CP-11586-06 @chopa @ui-cc @crm-regression
  Scenario: Back Arrow & Warning Message -Cancel button Edit Authorized Representative
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    And I click on first consumerID for Authorized Representative
    Then I am able to update each checkbox
    And I click on Cancel button on add primary individual page
    Then I verify warning message is displayed
    And I click on cancel button on warning message
    Then I will see information split into 2 sections
    Then I click on the back arrow button
    Then I verify warning message is displayed
    And I click on the continue button on warning pop up
    Then I verify Authorized Representative section displayed

  @CP-11586 @CP-11586-07 @chopa @ui-cc @crm-regression
  Scenario: Back Arrow & Warning Message -Cancel button Add Email
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I click on the Add button for Email Address
    And I add new Email
    And I click on cancel button on create task type screen
    Then I verify warning popup is displayed with message
    Then I verify Email section fields displayed
    And I click on cancel button on warning message
    Then I click on the back arrow button
    Then I verify warning popup is displayed with message
    And I click on the continue button on warning pop up
    Then I should see the Add button displayed for Email address

  @CP-11586 @CP-11586-08 @chopa @ui-cc @crm-regression
  Scenario: Back Arrow & Warning Message -Cancel button Add Phone Number
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I add new Phone Number
    And I click on cancel button on create task type screen
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    Then I verify Phone Number section fields displayed
    Then I click on the back arrow button
    Then I verify warning popup is displayed with message
    And I click on the continue button on warning pop up
    Then I should see the Add button displayed for Email address

  @CP-11586 @CP-11586-09 @chopa @ui-cc @crm-regression
  Scenario: Back Arrow & Warning Message -Cancel button Add Address
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    When I click on Add new address button on Contact Info Tab Page
    And I edit all mandatory fields with new data on Edit Address Page within a case
    And I click on cancel button on create task type screen
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    Then I see all add new address fields are displayed
    Then I click on the back arrow button
    Then I verify warning popup is displayed with message
    And I click on the continue button on warning pop up
    Then I verify that I am in the Address Page