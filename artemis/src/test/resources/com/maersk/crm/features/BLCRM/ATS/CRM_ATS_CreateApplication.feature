Feature: Create Application Navigate/View/Validation

  @CP-13766  @CP-13766-01 @CP-15790 @CP-15790-01 @crm-regression @ui-ats @ozgen
  Scenario: Navigation to Create Application Page
    Given I logged into CRM
    When I click on the Hamburger Menu
    Then I should see Create Application option

  @CP-13766  @CP-13766-02 @CP-15790 @CP-15790-02 @crm-regression @ui-ats @ozgen
  Scenario: Verification of two Application Pages
    Given I logged into CRM
    When I click on the Hamburger Menu
    Then I should see Create Application option
    And I verify that there are two application types

  @CP-13766  @CP-13766-03 @CP-14977 @CP-14977-26 @crm-regression @ui-ats @ozgen
  Scenario: Verification of Back Arrow Long Term Care Application Page for outside of contact
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    Then I verify "Long Term Care" Application Page header is displayed
    And I verify Back Arrow button is displayed on Create Application screen
    And I click on back arrow button on create application page
    Then I verify that user is navigated/stayed "Dashboard" page

  @CP-13766  @CP-13766-04 @CP-14977 @CP-14977-27 @crm-regression @ui-ats @ozgen
  Scenario: Verification of Back Arrow Medical Assistance Application for in active contact
    Given I logged into CRM and click on initiate contact
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I verify "Medical Assistance" Application Page header is displayed
    And I verify Back Arrow button is displayed on Create Application screen
    And I click on back arrow button on create application page
    Then I verify that user is navigated/stayed "Active Contact" page

  @CP-13766  @CP-13766-05 @CP-14977 @CP-14977-28 @crm-regression @ui-ats @ozgen
  Scenario: Verification of Back Arrow when we add some fields on Create Application Page
    Given I logged into CRM and click on initiate contact
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I verify "Medical Assistance" Application Page header is displayed
    And I verify Back Arrow button is displayed on Create Application screen
    And I provide mandatory primary individual details on Create Application Page
    And I click on back arrow button on create application page
    And I verify warning message and warning text is displayed with Continue and Cancel button
    Then I click on continue button on warning message
    Then I verify that user is navigated/stayed "Active Contact" page

  @CP-13766  @CP-13766-06 @CP-14977 @CP-14977-29 @crm-regression @ui-ats @ozgen
  Scenario: Verification of Cancel on Warning Message clicked on Back Arrow when we add some fields on Create Application Page
    Given I logged into CRM and click on initiate contact
    When I navigate to Create "LONG TERM CARE" application page
    Then I verify "Long Term Care" Application Page header is displayed
    And I verify Back Arrow button is displayed on Create Application screen
    And I provide mandatory primary individual details on Create Application Page
    And I click on back arrow button on create application page
    And I verify warning message and warning text is displayed with Continue and Cancel button
    Then I click on cancel button on warning message
    Then I verify that user is navigated/stayed "Create Application" page

  @CP-13083 @CP-13083-01 @crm-regression @ui-ats @ozgen
  Scenario: Mandatory fields for Medicaid as program type and not able to save application for primary individual
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I choose "Medicaid" as program type
    Then I clear the program list for "PI" Application Members
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I verify that following fields become mandatory for "Medicaid for PI"
    Then I click on Save button on Create Application Page
    And I verify mandatory fields error messages displayed for "Medicaid"

  @CP-13083 @CP-13083-02 @crm-regression @ui-ats @ozgen
  Scenario: Mandatory fields for Medicaid as program type and not able to save application for application member
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    Then I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And Select the "Medicaid" Program(s) for application member
    And I verify that following fields become mandatory for "Medicaid for AM"
    And click on save button for add application member
    And I verify mandatory fields error messages displayed for "Medicaid"
    Then I clear the program list for "PI" Application Members

  @CP-13083 @CP-13083-03 @crm-regression @ui-ats @ozgen
  Scenario: Mandatory fields for CHIP as program type for primary individual
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I choose "CHIP" as program type
    And I verify that following fields become mandatory for "CHIP for PI"
    Then I clear the program list for "PI" Application Members

  @CP-13083 @CP-13083-04 @crm-regression @ui-ats @ozgen
  Scenario: Mandatory fields error messages for CHIP as program type for primary individual
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I choose "CHIP" as program type
    Then I clear the program list for "PI" Application Members
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    Then I click on Save button on Create Application Page
    And I verify mandatory fields error messages displayed for "CHIP"

  @CP-13083 @CP-13083-05 @crm-regression @ui-ats @ozgen
  Scenario: Mandatory fields for CHIP as program type for application member
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    Then I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And Select the "CHIP" Program(s) for application member
    And I verify that following fields become mandatory for "CHIP for AM"
    Then I clear the program list for "AM" Application Members

  @CP-13083 @CP-13083-06 @crm-regression @ui-ats @ozgen
  Scenario: Mandatory fields error messages for CHIP as program type for application member
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    Then I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And Select the "CHIP" Program(s) for application member
    And click on save button for add application member
    And I verify mandatory fields error messages displayed for "CHIP"
    Then I clear the program list for "AM" Application Members

  @CP-13083 @CP-13083-07 @crm-regression @ui-ats @ozgen
  Scenario: Mandatory fields for HCBS as program type for primary individual
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    Then I choose "HCBS" as program type
    And I verify that following fields become mandatory for "HCBS for PI"
    Then I clear the program list for "PI" Application Members

  @CP-13083 @CP-13083-08 @crm-regression @ui-ats @ozgen
  Scenario: Mandatory field error messages for HCBS as program type for primary individual
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    Then I choose "HCBS" as program type
    Then I clear the program list for "PI" Application Members
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    Then I click on Save button on Create Application Page
    And I verify mandatory fields error messages displayed for "HCBS"

  @CP-13083 @CP-13083-09 @crm-regression @ui-ats @ozgen
  Scenario: Mandatory fields for HCBS as program type for application member
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    Then I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And Select the "HCBS" Program(s) for application member
    And I verify that following fields become mandatory for "HCBS for AM"
    Then I clear the program list for "AM" Application Members

  @CP-13083 @CP-13083-10 @crm-regression @ui-ats @ozgen
  Scenario: Mandatory field error messages for HCBS as program type for application member
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    Then I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And Select the "HCBS" Program(s) for application member
    And click on save button for add application member
    And I verify mandatory fields error messages displayed for "HCBS"
    Then I clear the program list for "AM" Application Members

  @CP-16388 @CP-16388-01 @CP-14977 @CP-14977-15 @crm-regression @ui-ats @ozgen
  Scenario: Programs and cycles applied for Medical Assistance
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I verify programs for "Medical Assistance"
    And I verify application cycles for "Medical Assistance"
      | New     |
      | Renewal |

  @CP-16388 @CP-16388-02 @CP-14977 @CP-14977-16 @crm-regression @ui-ats @ozgen
  Scenario: Programs and cycles applied for Long Term Care
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    And I verify programs for "Long Term Care"
    And I verify application cycles for "Long Term Care"
      | New |

  @CP-13355 @CP-13355-01 @crm-regression @ui-ats @sang
  Scenario: Navigate to Create Application screen and validate Long Term and Medical Assistance
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    Then I verify "Long Term Care" Application Page header is displayed
    And I click on the back arrow button
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I verify "Medical Assistance" Application Page header is displayed

  @CP-13355 @CP-13355-02 @crm-regression @ui-ats @sang
  Scenario: Verify the Primary Individual Details labels in Medical Assistance Create application
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I verify the Primary Individual have following labels
      | FIRST NAME | MI | LAST NAME | SUFFIX | DATE OF BIRTH | AGE | GENDER | SSN | SPOKEN LANGUAGE | WRITTEN LANGUAGE | EXTERNAL CONSUMER ID | EXTERNAL ID TYPE |
    And I verify Primary Individual Contact Information has the following labels
      | CELL PHONE NUMBER | HOME PHONE NUMBER | WORK PHONE NUMBER | FAX NUMBER | EMAIL |
    And I verify Contact Information Residence Address labels
      | ADDRESS LINE 1 | ADDRESS LINE 2 | CITY | STATE | ZIP CODE | COUNTY |
    And I verify Contact Information Mailing Address has the following labels
      | ADDRESS LINE 1 | ADDRESS LINE 2 | CITY | STATE | ZIP CODE |
    And I verify Programs(s) Applied for and Communication opt-in information has the following labels in "Medical Assistance"
      | MEDICAID | CHIP | PREGNANCY ASSISTANCE | EMAIL | FAX | MAIL | PHONE | TEXT |
    And I verify Application Info for Primary Individual has the following labels
      | APPLICATION CYCLE | RECEIVED DATE | CHANNEL | APPLICATION SIGNATURE | SIGNATURE DATE |
    And I verify the Contact Info Information Icon label with the hover over message "must have at least one of the following: phone/address/email"

  @CP-13355 @CP-13355-03 @crm-regression @ui-ats @sang
  Scenario: Verify the Primary Individual Details labels in Long Term Care Create application
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    Then I verify the Primary Individual have following labels
      | FIRST NAME | MI | LAST NAME | SUFFIX | DATE OF BIRTH | AGE | GENDER | SSN | SPOKEN LANGUAGE | WRITTEN LANGUAGE | EXTERNAL CONSUMER ID | EXTERNAL ID TYPE |
    And I verify Primary Individual Contact Information has the following labels
      | CELL PHONE NUMBER | HOME PHONE NUMBER | WORK PHONE NUMBER | FAX NUMBER | EMAIL |
    And I verify Contact Information Residence Address labels
      | ADDRESS LINE 1 | ADDRESS LINE 2 | CITY | STATE | ZIP CODE | COUNTY |
    And I verify Contact Information Mailing Address has the following labels
      | ADDRESS LINE 1 | ADDRESS LINE 2 | CITY | STATE | ZIP CODE |
    And I verify Programs(s) Applied for and Communication opt-in information has the following labels in "Long Term Care"
      | HCBS | EMAIL | FAX | MAIL | PHONE | TEXT |
    And I verify Application Info for Primary Individual has the following labels
      | APPLICATION CYCLE | RECEIVED DATE | CHANNEL | APPLICATION SIGNATURE | SIGNATURE DATE |
    And I verify the Contact Info Information Icon label with the hover over message "must have at least one of the following: phone/address/email"

  @CP-13355 @CP-13355-06 @crm-regression @ui-ats @sang
  Scenario: Verify the application cycles and program values in Medical Assistance
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I verify the following Application cycle values for "MEDICAL ASSISTANCE"
      |[blank]| New | Renewal |
    And I verify the defined programs for Primary Individual "MEDICAL ASSISTANCE"

  @CP-13355 @CP-13355-07 @crm-regression @ui-ats @sang
  Scenario: Verify the application cycles and program values in Long Term Care
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    Then I verify the following Application cycle values for "LONG TERM CARE"
      |[blank]| New |
    And I verify the defined programs for Primary Individual "LONG TERM CARE"

  @CP-13355 @CP-13355-10 @crm-regression @ui-ats @sang
  Scenario: Channel selected in Active contact has a counterpart in ATS MEDICAL ASSISTANCE
    Given I logged into CRM and click on initiate contact
    And I click on the Contact Channel Type "Phone"
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I verify PI Channel "Phone" dropdown value
    And I click on Active Contact widget
    And I click on the Contact Channel Type "Email"
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I verify PI Channel "Email" dropdown value
    And I click on Active Contact widget
    And I click on the Contact Channel Type "Web Chat"
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I verify PI Channel "Web Chat" dropdown value

  @CP-13355 @CP-13355-11 @crm-regression @ui-ats @sang
  Scenario: Channel selected in Active contact has a counterpart in ATS LONG TERM CARE
    Given I logged into CRM and click on initiate contact
    And I click on the Contact Channel Type "Phone"
    When I navigate to Create "LONG TERM CARE" application page
    And I verify PI Channel "Phone" dropdown value
    And I click on Active Contact widget
    And I click on the Contact Channel Type "Email"
    When I navigate to Create "LONG TERM CARE" application page
    And I verify PI Channel "Email" dropdown value
    And I click on Active Contact widget
    And I click on the Contact Channel Type "Web Chat"
    When I navigate to Create "LONG TERM CARE" application page
    And I verify PI Channel "Web Chat" dropdown value

  @CP-13355 @CP-13355-12 @crm-regression @ui-ats @sang
  Scenario: Channel selected in Active contact does not have a counterpart in ATS Medical Assistance
    Given I logged into CRM and click on initiate contact
    And I click on the Contact Channel Type "IVR Self-Service"
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I verify Primary Individual Channel dropdown value is empty
    And I click on Active Contact widget
    And I click on the Contact Channel Type "Mobile App"
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I verify Primary Individual Channel dropdown value is empty
    And I click on Active Contact widget
    And I click on the Contact Channel Type "SMS Text"
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I verify Primary Individual Channel dropdown value is empty
    And I click on Active Contact widget
    And I click on the Contact Channel Type "Web Portal"
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I verify Primary Individual Channel dropdown value is empty

  @CP-13355 @CP-13355-13 @crm-regression @ui-ats @sang
  Scenario: Channel selected in Active contact does not have a counterpart in ATS Long Term Care
    Given I logged into CRM and click on initiate contact
    And I click on the Contact Channel Type "IVR Self-Service"
    When I navigate to Create "LONG TERM CARE" application page
    And I verify Primary Individual Channel dropdown value is empty
    And I click on Active Contact widget
    And I click on the Contact Channel Type "Mobile App"
    When I navigate to Create "LONG TERM CARE" application page
    And I verify Primary Individual Channel dropdown value is empty
    And I click on Active Contact widget
    And I click on the Contact Channel Type "SMS Text"
    When I navigate to Create "LONG TERM CARE" application page
    And I verify Primary Individual Channel dropdown value is empty
    And I click on Active Contact widget
    And I click on the Contact Channel Type "Web Portal"
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I verify Primary Individual Channel dropdown value is empty

  @CP-15425 @CP-15425-01 @crm-regression @ui-ats @sang
  Scenario: Verify Medical Assistance  Priority Label and the default value with the values contained
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I Verify the Priority Label and the default values for the dropdown is set to "Normal"
    And I verify the Priorty dropdown contains the following values
      | Normal | High | Expedited |[blank]|

  @CP-15425 @CP-15425-02 @crm-regression @ui-ats @sang
  Scenario: Verify Long Term Care application Priority Label and the default value with the values contained
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    Then I Verify the Priority Label and the default values for the dropdown is set to "Normal"
    And I verify the Priorty dropdown contains the following values
      | Normal | High | Expedited |[blank]|

  @CP-15425 @CP-15425-03 @crm-regression @ui-ats @sang
  Scenario: Verify default Normal Priority Value is saved and shown in Application details and Application tracking Tab
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify the "Normal" Priority value on the Application details Page
    And I click on application tracking tab to navigate to Application Tracking page
    And I verify the "Normal" priority value on the Application Tracking page

  @CP-15425 @CP-15425-04 @crm-regression @ui-ats @sang
  Scenario: Verify High Priority Value is saved and shown in Application details and Application tracking Tab
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I select "High" value from the Application Priority dropdown
    Then I click on Save button on Create Application Page
    And I verify the "High" Priority value on the Application details Page
    And I click on application tracking tab to navigate to Application Tracking page
    And I verify the "High" priority value on the Application Tracking page

  @CP-15425 @CP-15425-05 @crm-regression @ui-ats @sang
  Scenario: Verify Expedited Priority Value is saved and shown in Application details and Application tracking Tab
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I select "Expedited" value from the Application Priority dropdown
    Then I click on Save button on Create Application Page
    And I verify the "Expedited" Priority value on the Application details Page
    And I click on application tracking tab to navigate to Application Tracking page
    And I verify the "Expedited" priority value on the Application Tracking page

  @CP-15425 @CP-15425-06 @crm-regression @ui-ats @sang
  Scenario: Verify Priority dropdown is enabled in Application Edit mode and verify the values contained
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    Then I click on the Edit button for the Primary Individual Details
    And I verify the Priority dropdown is enabled for application in edit mode
    And I verify the Priorty dropdown contains the following values
      | Normal | High | Expedited |[blank]|

  @CP-15425 @CP-15425-07 @crm-regression @ui-ats @sang
  Scenario: Editing Priority value without saving should not change saved Priority Save
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I select "High" value from the Application Priority dropdown
    Then I click on Save button on Create Application Page
    And I verify the "High" Priority value on the Application details Page
    Then I click on the Edit button for the Primary Individual Details
    And I select "Expedited" value from the Application Priority dropdown
    And I click on application tracking tab to navigate to Application Tracking page
    When I click on continue button on warning message
    And I verify the "High" priority value on the Application Tracking page
    And I click application id under Application Tracking tab in the Application Information panel
    And I verify I am on Primary Individual details page
    And I verify the "High" Priority value on the Application details Page

  @CP-15425 @CP-15425-08 @crm-regression @ui-ats @sang
  Scenario: Verify updatedBy and updated on value when Application Priorty value is edited
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I POST ATS save application api
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    And I saved logged in user ID
    Then I click on the Edit button for the Primary Individual Details
    And I select "Expedited" value from the Application Priority dropdown
    Then I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I Verify the following from the GET Application with ApplicationId
      | PI UPDATEDBY | base                  |
      | PI UPDATEDON | today's date and hour |

  @CP-23836 @CP-23836-01 @CP-23842 @CP-23842-01 @crm-regression @ui-ats @sang
  Scenario: Verify External Application ID save with 36 variable character and shown in Application Tracking tab
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I select Primary Individual Application information with the following
      | EXTERNAL APPLICATION ID | 36 VARIABLE CHARACTERS |
    When I click on Save button on Create Application Page
    And I verify the following saved Primary Individual Application Info
      | EXTERNAL APPLICATION ID |
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify "EXTERNAL APPLICATION ID" on Application Tracking Page

  @CP-23836 @CP-23836-02 @CP-23842 @CP-23842-02 @crm-regression @ui-ats @sang
  Scenario: Verify External Application ID save does not capture spaces in front back or in between value
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I select Primary Individual Application information with the following
      | EXTERNAL APPLICATION ID | ID ENTERED WITH SPACES |
    When I click on Save button on Create Application Page
    And I verify the following saved Primary Individual Application Info
      | EXT APP ID WITHOUT SPACES |
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify "EXT APP ID WITHOUT SPACES" on Application Tracking Page

  @CP-23836 @CP-23836-01 @CP-23842 @CP-23842-01 @crm-regression @ui-ats @sang
  Scenario: Verify External Application ID save captures no more then 36 Variable Characters
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I select Primary Individual Application information with the following
      | EXTERNAL APPLICATION ID | OVER MAX VARIABLE CHARACTERS |
    When I click on Save button on Create Application Page
    And I verify the following saved Primary Individual Application Info
      | OVER MAX VARIABLE CHARACTERS |
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify "OVER MAX VARIABLE CHARACTERS" on Application Tracking Page

  @CP-16673 @CP-16673-01 @crm-regression @ui-ats @sang
  Scenario: Verify Received language dropdown with default value and dropdown values in Medical Assistance and Long Term Care
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    Then I verify "Long Term Care" Application Page header is displayed
    And I verify the Received Language dropdown values with the following
      |[blank]| Braille English | Braille Spanish | English | Other | Russian | Spanish | Vietnamese |
    And I click on the back arrow button
    Then I click on continue button on warning message
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I verify "Medical Assistance" Application Page header is displayed
    And I verify the Received Language dropdown values with the following
      |[blank]| Braille English | Braille Spanish | English | Other | Russian | Spanish | Vietnamese |

  @CP-16673 @CP-16673-02 @crm-regression @ui-ats @sang
  Scenario: Verify Received language default value is saved and shown in  saved application tab and app tracking tab
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    Then I verify "Long Term Care" Application Page header is displayed
    And I provide mandatory primary individual details on Create Application Page
    And I select Primary Individual Application information with the following
      | RECEIVED LANGUAGE | Default |
    When I click on Save button on Create Application Page
    And I verify the following saved Primary Individual Application Info
      | RECEIVED LANGUAGE |
    When I click on application tracking tab to navigate to Application Tracking page
    And I verify "RECEIVED LANGUAGE" field is saved as "English" in the Application Tracking tab

  @CP-30365 @CP-30365-01 @crm-regression @ui-ats @chandrakumar
  Scenario Outline: Verify application GUID number field value in the response for an application with Entering Data status
    When I will get the Authentication token for "BLCRM" in "CRM"
    Given I logged into CRM
    When I navigate to Create "<applicationType>" application page
    Then I verify "<applicationType>" Application Page header is displayed
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    Then I get application id from the Create Application Page
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    Then I verify the application GUID from the response for "<applicationType>" application
    Examples:
      | applicationType    |
      | MEDICAL ASSISTANCE |

  @CP-30365 @CP-30365-02 @crm-regression @ui-ats @chandrakumar
  Scenario Outline: Verify application GUID number field value in the response for an application with Submitted status
    When I will get the Authentication token for "BLCRM" in "CRM"
    Given I logged into CRM
    When I navigate to Create "<applicationType>" application page
    Then I verify "<applicationType>" Application Page header is displayed
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    Then I get application id from the Create Application Page
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I get the application GUID from API response
    Then I click on Submit button only in Create Application Page
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I get the application GUID from API response after updating status
    Then I verify the application GUID from the response for "<applicationType>" application
    Examples:
      | applicationType    |
      | MEDICAL ASSISTANCE |

  @CP-30365 @CP-30365-03 @crm-regression @ui-ats @chandrakumar
  Scenario Outline: Verify application GUID number field value in the response for an application with Withdrawn status
    And I will get the Authentication token for "BLCRM" in "CRM"
    Given I logged into CRM
    And I saved logged in user ID
    When I navigate to Create "<applicationType>" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    Then I get application id from the Create Application Page
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I get the application GUID from API response
    Then I navigate to Application Tracking
    And I see application Status as "Entering Data" in the application information
    Then I verify I see Withdraw button displayed in Application Information panel
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    And I  select "Already Receiving Services" from select reason dropdown
    And I click save button in the select reason
    Then I verify I see warning message "Are you sure you want to Withdraw this Application?"
    When I click Continue button inside the warning message
    And I see application Status as "WITHDRAWN" in the application information
    And I get application id from the Create Application Page
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I get the application GUID from API response after updating status
    Then I verify the application GUID from the response for "<applicationType>" application
    Examples:
      | applicationType    |
      | MEDICAL ASSISTANCE |

  @CP-30365 @CP-30365-04 @crm-regression @ui-ats @chandrakumar
  Scenario Outline: Verify application GUID number field value in the response for an edited application
    When I will get the Authentication token for "BLCRM" in "CRM"
    Given I logged into CRM
    When I navigate to Create "<applicationType>" application page
    Then I verify "<applicationType>" Application Page header is displayed
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    Then I get application id from the Create Application Page
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I get the application GUID from API response
    And I click on the Edit button for the Primary Individual Details
    Then I click on Save button on Create Application Page
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I get the application GUID from API response after updating status
    Then I verify the application GUID from the response for "<applicationType>" application
    Examples:
      | applicationType    |
      | MEDICAL ASSISTANCE |

  @CP-34253 @CP-34253-01 @crm-regression @ui-ats @sang
  Scenario: View Application Code in application tab tracking tab and missing info tab and verify it is uneditable for UI created application
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    Then I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME | Random 5    |
      | LAST NAME  | Random 5    |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | HOME PHONE NUMBER | Random Numeric 10 |
    Then I click on Save button on Create Application Page
    And I save and store Application Code from application tab after UI application is created
    And I verify the following saved Primary Individual Application Info
      | APPLICATION CODE UI|
    Then I click on the Edit button for the Primary Individual Details
    And I verify the following saved Primary Individual Application Info
      | APPLICATION CODE UI|
    When I click on application tracking tab to navigate to Application Tracking page
    Then I click on continue button on warning message
    And I verify "APPLICATION CODE UI" field is saved as "CREATED" in the Application Tracking tab
    When I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    Then I verify "APPLICATION CODE UI" in the Application Information panel of the Missing Info tab

  @CP-34253 @CP-34253-02 @crm-regression @ui-ats @sang
  Scenario: View Application Code in application tab tracking tab and missing info tab and verify it is uneditable for API created application
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I reinitialize Application data for "ALL DUPLICATES DATA" for duplicates
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | false         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | RANDOM FIRST   | RANDOM LAST   | RANDOM DOB | RANDOM SSN |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I get the application GUID from API response
    Then I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I verify "APPLICATION CODE API" field is saved as "CREATED" in the Application Tracking tab
    And I navigate to application tab page
    And I verify the following saved Primary Individual Application Info
      | APPLICATION CODE API|
    Then I click on the Edit button for the Primary Individual Details
    And I verify the following saved Primary Individual Application Info
      | APPLICATION CODE API|
    When I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    Then I click on continue button on warning message
    Then I verify "APPLICATION CODE API" in the Application Information panel of the Missing Info tab