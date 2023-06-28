Feature: Add, view and Update Case Member

   # These tests are dependant on creating case data and will not be included in regression until we figure out test data dependencies in the framework
 # Few of the test are failing intermittently due to Bug # 1512


  @CRM-778 @CRM-778-01 @Shruti  @failed
  Scenario:Validate Add Button is displayed for Case Member
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    When I click on Add Button for Case Member
    Then I should be navigated to Add Case Members page

  @CRM-778 @CRM-778-02 @Shruti @failed
  Scenario:Validate Add Case Member- Field Validations
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    When I click on Add Button for Case Member
    And I should be navigated to Add Case Members page
    Then I verify all the fields displayed on the Add Case Member page

  @CRM-778 @CRM-778-03 @Shruti @CP-23126
  Scenario:Add Case Member- First Name & Last Name Field -Field Type Validation and Character limit restrictions
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    When I click on Add Button for Case Member
    And I should be navigated to Add Case Members page
    Then I verify first name and last name fields doesn't accept more than fifty characters

  @CRM-778 @CRM-778-04 @Shruti @failed
  Scenario Outline:Add Case Member- Age is calculated based on DOB entered
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    When I click on Add Button for Case Member
    And I should be navigated to Add Case Members page
    Then I verify calculated Age as per "<DOB>"
    Examples:
      | DOB        |
      | 03/16/1992 |

  @CRM-778 @CRM-778-05 @Shruti @failed
  Scenario Outline: Add Case Member - Relationship to PI-Field Type Validation
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    When I click on Add Button for Case Member
    And I should be navigated to Add Case Members page
    Then I verify "<Relationship_to_PI>" dropdown had values
    Examples:
      | Relationship_to_PI |
      | Child              |
      | Spouse             |
      | Guardian           |

  @CRM-778 @CRM-778-06 @Shruti @failed
  Scenario Outline:Add Case Member-Save Button
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Emma" and Last Name as "Jones"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    When I click on Add Button for Case Member
    And I should be navigated to Add Case Members page
    When I enter the mandatory fields details "<FirstName>", "<LastName>", "<DOB>", "<Gender>", "<StartDate>","<EndDate>" "<Language>","<Relation>","<SSN>"
    Then I verify that case member is added successfully
    Examples:
      | FirstName | LastName | DOB        | Gender | StartDate | EndDate | Language | Relation | SSN         |
      | {random}  | {random} | 01/31/2014 | Female | today     | future  | English  | Child    | 123-12-1234 |
     # | {random}  | {random} | 01/01/1982 | Male   | past      | future  | Spanish  | Spouse   | 123-52-1234 |


  @CRM-778 @CRM-778-07 @Shruti @failed
  Scenario Outline:Verify Case member status is active if start is  less than or equal to current date and end date is future
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    When I click on Add Button for Case Member
    And I should be navigated to Add Case Members page
    When I enter the mandatory fields details "<FirstName>", "<LastName>", "<DOB>", "<Gender>", "<StartDate>","<EndDate>" "<Language>","<Relation>","<SSN>"
    Then I verify the case member "<status>"
    Examples:
      | FirstName | LastName | DOB        | Gender | StartDate | EndDate | Language | Relation | SSN         | status |
      | {random}  | {random} | 01/01/2015 | Female | past      | future  | English  | Child    | 987-23-1234 | Active |


  @CRM-778 @CRM-778-08 @Shruti @failed
  Scenario Outline:Verify Case member status is inactive if start is less than current date and end date is in past
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    When I click on Add Button for Case Member
    And I should be navigated to Add Case Members page
    When I enter the mandatory fields details "<FirstName>", "<LastName>", "<DOB>", "<Gender>", "<StartDate>","<EndDate>" "<Language>","<Relation>","<SSN>"
    Then I verify the case member "<status>"
    Examples:
      | FirstName | LastName | DOB        | Gender | StartDate | EndDate | Language | Relation | SSN         | status   |
      | {random}  | {random} | 08/01/1983 | Male   | past      | past    | Spanish  | Guardian | 987-23-1234 | Inactive |

  @CRM-778 @CRM-778-09 @Shruti @failed
  Scenario: Add Case Member -Verify Field Labels are mixed case
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    When I click on Add Button for Case Member
    And I should be navigated to Add Case Members page
    Then I verify all the Field Labels are Mixed Case
    # 11-21 New Functionality , trying to update an exisiting case member

  @CRM-779 @CRM-779-01 @Shruti  @failed
  Scenario Outline:Verify Update Case Member Name Fields
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    When I click on any existing Case Member
    And I should be navigated to Add Case Members page
    When I update one or more of the following fields "<FirstName>", "<LastName>", "<DOB>", "<Gender>", "<StartDate>","<EndDate>" "<Language>","<Relation>","<SSN>"
    Then I verify that case member name is updated successfully
    Examples:
      | FirstName | LastName | DOB | Gender | StartDate | EndDate | Language | Relation | SSN |
      | {random}  | {random} |[blank]|        |[blank]|         |[blank]|          |[blank]|

  @CRM-779 @CRM-779-02 @Shruti @failed
  Scenario Outline:Verify Update Case Member DOB
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    When I click on any existing Case Member
    And I should be navigated to Add Case Members page
    When I update one or more of the following fields "<FirstName>", "<LastName>", "<DOB>", "<Gender>", "<StartDate>","<EndDate>" "<Language>","<Relation>","<SSN>"
    Then I verify that case member date of birth is updated successfully
    Examples:
      | FirstName | LastName | DOB      | Gender | StartDate | EndDate | Language | Relation | SSN |
      |[blank]|          | {random} |[blank]|           |[blank]|          |[blank]|     |

  @CRM-779 @CRM-779-03 @Shruti @failed
  Scenario Outline:Verify Update Case Member Gender
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    When I click on any existing Case Member
    And I should be navigated to Add Case Members page
    When I update one or more of the following fields "<FirstName>", "<LastName>", "<DOB>", "<Gender>", "<StartDate>","<EndDate>" "<Language>","<Relation>","<SSN>"
    Then I verify that case member gender is updated successfully
    Examples:
      | FirstName | LastName | DOB | Gender | StartDate | EndDate | Language | Relation | SSN |
      |[blank]|          |[blank]| Male   |[blank]|         |[blank]|          |[blank]|


  @CRM-779 @CRM-779-04 @Shruti @failed
  Scenario Outline:Verify Inactive case member
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    When I click on Add Button for Case Member
    And I should be navigated to Add Case Members page
    When I enter the mandatory fields details "<FirstName>", "<LastName>", "<DOB>", "<Gender>", "<StartDate>","<EndDate>" "<Language>","<Relation>","<SSN>"
    When I click on added Case Member
    And I inactivate Case Member
    Then I verify the status as inactive
    Examples:
      | FirstName | LastName | DOB        | Gender | StartDate | EndDate | Language | Relation | SSN         |
      | {random}  | {random} | 01/31/2014 | Female | today     | future  | English  | Child    | 123-12-1234 |

  @CRM-779 @CRM-779-05 @Shruti @failed
  Scenario Outline: :Verify Case member when Inactivate Immediately is checked the end date is current date
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    When I click on Add Button for Case Member
    And I should be navigated to Add Case Members page
    When I enter the mandatory fields details "<FirstName>", "<LastName>", "<DOB>", "<Gender>", "<StartDate>","<EndDate>" "<Language>","<Relation>","<SSN>"
    When I click on added Case Member
    And I inactivate Case Member
    And I verify the status as inactive
    Then I hover over the status and verify end date is current date
    Examples:
      | FirstName | LastName | DOB        | Gender | StartDate | EndDate | Language | Relation | SSN         |
      | {random}  | {random} | 01/31/2014 | Female | today     | future  | English  | Child    | 123-12-1234 |

  @CRM-779 @CRM-779-06 @Shruti @failed
  Scenario:Verify error displayed for mandatory fields for no data
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    When I click on any existing Case Member
    And I should be navigated to Add Case Members page
    When I clear mandatory case member fields and click on save
    Then I verify that case member mandatory field error messages
    When I click cancel button on update case member page
    When I click on any existing Case Member
    And I should be navigated to Add Case Members page
    Then I verify previous values for the case member fileds remains after cancel

  @CRM-779 @CRM-779-07 @Shruti @failed
  Scenario Outline:Verify Update Case Member start date and end date
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    When I click on any existing Case Member
    And I should be navigated to Add Case Members page
    When I update one or more of the following fields "<FirstName>", "<LastName>", "<DOB>", "<Gender>", "<StartDate>","<EndDate>" "<Language>","<Relation>","<SSN>"
    Then I verify that case member start date and end date is updated successfully
    Examples:
      | FirstName | LastName | DOB | Gender | StartDate | EndDate | Language | Relation | SSN |
      |[blank]|          |[blank]|        | past      | future  |[blank]|          |[blank]|

  @CP-7452 @CP-7452-01 @umid @ui-cc @crm-regression
  Scenario: :Verify Split AR Component into 2 sections, Mandatory fields, PREGNANCY DUE DATE error message Case Member
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    And I should see the Add button displayed for primary individual
    When I click on Add Button for Case Member
    Then I will see information split into 2 sections
    And I verify consumer role "Member"
    When I select Gender as "Female" to verify pregnancy checkbox is displayed
    And I click pregnancy checkbox
    Then I verify Mandatory fields
      |Start Date|
      |relationShip|
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

  @CP-7452 @CP-7452-02 @umid @ui-cc @crm-regression
  Scenario: Verify error messages for mandatory fields and DOD NOTIFIED DATE & SOURCE Case Member
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    And I should see the Add button displayed for primary individual
    When I click on Add Button for Case Member
    And I click on Save button on add primary individual page
    Then I see error message populated below each field on Add Primary Individual Page
    And I fill DOD with past date
    And I click on Save button on add primary individual page
    Then I verify "DOD NOTIFIED DATE" is required and cannot be left blank
    Then I verify "DOD NOTIFICATION SOURCE  " is required and cannot be left blank

  @CP-7452 @CP-7452-03 @umid @ui-cc @crm-regression
  Scenario: :Verify All of the dropdown's for Primary Individual Page Case Member
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    And I should see the Add button displayed for primary individual
    When I click on Add Button for Case Member
    And I verify consumer role "Member"
    Then I verify the dropdown values for "relationShip"
      |Child|
      |Guardian|
      |Spouse|
      |Unknown|
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

  @CP-7452 @CP-7452-04 @umid @ui-cc @crm-regression
  Scenario: Cancel & Back Arrow & Navigate to Contact search without editing Case Member
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    And I should see the Add button displayed for primary individual
    When I click on Add Button for Case Member
    And I click on cancel button on create task type screen
    Then I verify Case Member section displayed
    When I click on Add Button for Case Member
    When I navigate away by clicking the back arrow without saving changes on this page
    Then I verify Case Member section displayed
    When I navigate to Contact Record Search Page
    When I am navigated back to Contact Record UI page

  @CP-7452 @CP-7452-05 @umid @ui-cc @crm-regression
  Scenario: Cancel & Back Arrow & Navigate to Contact search with editing warning message Case Member
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    And I should see the Add button displayed for primary individual
    When I click on Add Button for Case Member
    And I fill DOD with past date
    And I click on cancel button on create task type screen
    Then I should see a warning message when navigating away "If you continue, all the captured information will be lost"
    And I click on cancel button on warning message
    Then I will see information split into 2 sections
    When I navigate away by clicking the back arrow without saving changes on this page
    Then I should see a warning message when navigating away "If you continue, all the captured information will be lost"
    And I click on cancel button on warning message
    Then I will see information split into 2 sections
    When I navigate to Contact Record Search Page
    Then I should see a warning message when navigating away "If you continue, all the captured information will be lost"

  @CP-7452 @CP-7452-05 @umid @ui-cc @crm-regression @CP-1824 @CP-1824-01 @CP-1824-03
  Scenario Outline: Profile Information fields Save, check format Case Member
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Garold" and Last Name as "Kumar"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify Case Member section displayed
    When I click on any existing Case Member
    When I update one or more of the following fields "<FirstName>", "<LastName>", "<DOB>", "<Gender>", "<StartDate>","<EndDate>" "<Language>","<Relation>","<SSN>"
    When I click "yes" on all changes will be lost warning message
    Examples:
      | FirstName        | LastName         | DOB | Gender | StartDate | EndDate | Language | Relation | SSN |
      | 'ADASDIOAJDHSJDK'| 'DASDIOAJDHISJDA'|[blank]|  Male  | past      | future  |[blank]|  Child   |[blank]|
