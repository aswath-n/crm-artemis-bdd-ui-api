Feature: Create Authorized Representative

  @CP-14979 @CP-14979-01 @crm-regression @ui-ats @asad
  Scenario: Navigate to Authorized Representative Panel
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    Then I check that Add Authorized Representative button is enabled

  @CP-14979 @CP-14979-02 @crm-regression @ui-ats @asad
  Scenario: Data enter Authorized Representative information
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    Then I verify the Authorized Representative have following labels
      | AUTH TYPE | FIRST NAME | MI | LAST NAME | ORGANIZATION NAME | ID NUMBER | START DATE | END DATE | ACCESS TYPE | CORRESPONDENCE | ADDRESS LINE 1 | ADDRESS LINE 2 | CITY | STATE | ZIP CODE | AUTHORIZED | AUTHORIZATION SIGNATURE DATE |
    Then I verify data can be entered into fields on Authorized Representative Page for ""

  @CP-14979 @CP-14979-03 @crm-regression @ui-ats @asad @sang
  Scenario: Verify successful Authorized Representative save with all valid field values
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | AUTH TYPE                    | RANDOM        |
      | FIRST NAME                   | RANDOM 30     |
      | MI                           | RANDOM 1      |
      | LAST NAME                    | RANDOM 30     |
      | ORGANIZATION NAME            | RANDOM 5      |
      | ID NUMBER                    | Numeric 5     |
      | AUTHORIZATION SIGNATURE DATE | RANDOM PAST   |
      | END DATE                     | RANDOM FUTURE |
      | ADDRESS LINE 1               | ALPHA NUMERIC |
      | ADDRESS LINE 2               | ALPHA NUMERIC |
      | CITY                         | RANDOM        |
      | STATE                        | RANDOM        |
      | ZIP CODE                     | RANDOM        |
      | ACCESS TYPE                  | RANDOM        |
      | CORRESPONDENCE               | RANDOM        |
      | AUTHORIZED                   | Yes           |
      | AUTH STATUS                  | SET BY DATES  |
    Then I click on Save button on Create Application Page
    Then I verify entered information from the "collapsed" view in Create Application Page
    Then I verify entered information from the "expanded" view in Create Application Page

  @CP-14979 @CP-14979-04 @crm-regression @ui-ats @asad @CP-34278
  Scenario: Verify Authorized Representative dropdown values -- dates verification
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    Then I verify dropdown values available for "Auth Type"
      | Assister             |
      | Broker               |
      | Nursing Facility Rep |
      | Other                |
      | Power of Attorney    |
      |[blank]|
    Then I verify dropdown values available for "Access Type"
      | Full Access    |
      | Partial Access |
      |[blank]|
    Then I verify dropdown values available for "Correspondence"
      | Do Not Receive         |
      | Receive in Addition To |
      | Receive in Place Of    |
      |[blank]|

  @CP-14979 @CP-14979-05 @crm-regression @ui-ats @asad @sang
  Scenario: Start date is populated by Authorized Signature Date -New
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME                   | RANDOM 30   |
      | LAST NAME                    | RANDOM 30   |
      | AUTHORIZATION SIGNATURE DATE | RANDOM PAST |
    Then I click on Save button on Create Application Page
    Then I verify Start date is same as Authorization Signature Date on Authorized Representative Page

  @CP-14979 @CP-14979-06 @crm-regression @ui-ats @asad @sang
  Scenario: End Date validation - New
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | RANDOM 30     |
      | LAST NAME  | RANDOM 30     |
      | START DATE | RANDOM FUTURE |
      | END DATE   | RANDOM PAST   |
    Then I click on Save button on Create Application Page
    Then I verify END DATE cannot be before START DATE error message is displayed and Auth rep is not saved

  @CP-14979 @CP-14979-07 @crm-regression @ui-ats @asad @sang
  Scenario: Display Authorized Representative(s) in the following order
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    And I enter information to add "ACTIVE" Authorized Representative
    And I click save button to save Authorized Representative
    When I click Add Authorized Representative button
    And I enter information to add "INACTIVE" Authorized Representative
    And I click save button to save Authorized Representative
    When I click Add Authorized Representative button
    And I enter information to add "FUTURE" Authorized Representative
    And I click save button to save Authorized Representative
    Then I verify Authorized Representatives are in order

  @CP-14979 @CP-14979-08 @crm-regression @ui-ats @asad @sang
  Scenario: View Authorized Representative information Expanded -- Status Hover dates verification
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | RANDOM 30     |
      | LAST NAME  | RANDOM 30     |
      | START DATE | RANDOM PAST   |
      | END DATE   | RANDOM FUTURE |
    And I click save button to save Authorized Representative
    Then I verify start and end date on Authorized Rep status date hover pop up

  @CP-14979 @CP-14979-09 @crm-regression @ui-ats @asad @sang
  Scenario: Determining Authorized Representative Status as Active
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    And I enter information to add "ACTIVE" Authorized Representative
    And I click save button to save Authorized Representative
    Then I verify status if Authorized Representative is "ACTIVE"

  @CP-14979 @CP-14979-10 @crm-regression @ui-ats @asad @sang
  Scenario: Determining Authorized Representative Status as Inactive
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    And I enter information to add "INACTIVE" Authorized Representative
    And I click save button to save Authorized Representative
    Then I verify status if Authorized Representative is "INACTIVE"

  @CP-14979 @CP-14979-11 @crm-regression @ui-ats @asad @sang
  Scenario: Determining Authorized Representative Status as Future
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    And I enter information to add "FUTURE" Authorized Representative
    And I click save button to save Authorized Representative
    Then I verify status if Authorized Representative is "FUTURE"

  @CP-14979 @CP-14979-12 @crm-regression @ui-ats @asad @sang
  Scenario: Edit Authorized Representative information
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And I click save button to save Authorized Representative
    And I click on Added Authorized Representative
    And I click on Edit button for Authorized Representative
    Then I verify data can be entered into fields on Authorized Representative Page for "Edit"

  @CP-14979 @CP-14979-13 @crm-regression @ui-ats @asad
  Scenario: Minimum Requirements to Save the Authorized representative
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    Then I verify minimum fields need to be entered in Authorized Representative Page

  @CP-14979 @CP-14979-14 @crm-regression @ui-ats @asad
  Scenario: Add Authorized Representative back arrow -warning
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    And I enter information to add "ACTIVE" Authorized Representative
    And I click back button
    Then I click on cancel button on warning message
    And I click back button
    And I click on continue button on warning message
    Then I verify that it is present on the create application page

  @CP-14979 @CP-14979-15 @crm-regression @ui-ats @asad
  Scenario: Add Authorized Representative back arrow
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    And I click back button
    Then I Verify that user is back to Create Application page

  @CP-14979 @CP-14979-16 @crm-regression @ui-ats @asad
  Scenario: Save Authorized Representative information- Success
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    And I enter information to add "ACTIVE" Authorized Representative
    And I click save button to save Authorized Representative
    Then I verify the save successfully updated message

  @CP-14979 @CP-14979-17 @crm-regression @ui-ats @asad
  Scenario: Save Authorized Representative information - Fail
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    Then I verify minimum fields need to be entered in Authorized Representative Page

  @CP-14979 @CP-14979-18 @crm-regression @ui-ats @asad @sang
  Scenario: Created On and Created By
    Given I logged into CRM
    And I saved logged in user ID
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    And I enter information to add "ACTIVE" Authorized Representative
    And I click save button to save Authorized Representative
    And I get application id from the Create Application Page
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I verify the following Authorized Representative values from the GET Application with ApplicationId
      | CREATED BY | base                  |
      | CREATED ON | today's date and hour |

  @CP-14979 @CP-14979-19 @crm-regression @ui-ats @asad @sang
  Scenario: Updated on an Updated By
    Given I logged into CRM
    And I saved logged in user ID
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    And I enter information to add "ACTIVE" Authorized Representative
    And I click save button to save Authorized Representative
    And I click on Added Authorized Representative
    And I click on Edit button for Authorized Representative
    And I edit Authorized Representative and save it
    And I get application id from the Create Application Page
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I verify the following Authorized Representative values from the GET Application with ApplicationId
      | UPDATED BY | base                  |
      | UPDATED ON | today's date and hour |

  @CP-14979 @CP-14979-20 @crm-regression @ui-ats @asad @sang
  Scenario: Navigate away from the screen
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | RANDOM 30 |
    When I navigate to Create "LONG TERM CARE" application page
    Then I click on cancel button on warning message
    And I verify I am on Authorized Rep Create page
    When I navigate to Create "LONG TERM CARE" application page
    And I click on continue button on warning message
    Then I verify that it is present on the create application page

  @CP-14979 @CP-14979-21 @crm-regression @ui-ats @sang
  Scenario: Verify null values for Authorized Representative in the Authorized Representative panel for Medical Assistance
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    Then I click on Save button on Create Application Page
    Then I verify double dash is represented for NUll values for Authorized Representative panel info

  @CP-14979 @CP-14979-22 @crm-regression @ui-ats @sang
  Scenario: Verify null values for Authorized Representative in the Authorized Representative panel for Long Term Care
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    Then I click on Save button on Create Application Page
    Then I verify double dash is represented for NUll values for Authorized Representative panel info

  @CP-14979 @CP-14979-23 @crm-regression @ui-ats @sang
  Scenario: View and verify Authorized representative details from application created by API in the application tab
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 1
      | consumerFirstName | consumerLastName | consumerMiddleName | accessType  | consumerType | correspondence | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate | authorizedRepExternalId | authorizedRepAppEndDate | authorizedRepAppStartDate |
      | Authfirstname     | Authlastname     | M                  | Full Access | Broker       | Receive in Addition To     | VA Foundation        | true                   | CURRENT TIMESTAMP          | 123auto                 | FUTURE TIMESTAMP        | PAST TIMESTAMP            |
    And I initiate save applications api consumer 1 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip |
      | Mailing     | 9 Metro Ave    | 2nd apt        | Reston      | Virginia     | 12311      |
    And I POST ATS save application api
    And I set Authorized representative Values from the sent application request payload to be checked
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I verify entered information from the "collapsed" view in Create Application Page
    Then I verify entered information from the "expanded" view in Create Application Page

  @CP-12703 @CP-12703-10 @crm-regression @ui-ats @sang
  Scenario: Verify Authorized Representative details Page has correct labels
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    Then I click on Save button on Create Application Page
    And I click on the created Auth Rep name hyperlink to go to Auth Rep Details page
    And I verify the Authorized Representative details page have following labels

  @CP-12703 @CP-12703-11 @crm-regression @ui-ats @sang @ats-smoke
  Scenario: Verify Authorized Representative information in Auth Rep Details page
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
   Then I fill in the following Authorized Representative values
      | AUTH TYPE                    | RANDOM        |
      | FIRST NAME                   | RANDOM 30     |
      | MI                           | RANDOM 1      |
      | LAST NAME                    | RANDOM 30     |
      | ORGANIZATION NAME            | RANDOM 5      |
      | ID NUMBER                    | Numeric 5     |
      | AUTHORIZATION SIGNATURE DATE | RANDOM PAST   |
      | END DATE                     | RANDOM FUTURE |
      | ADDRESS LINE 1               | ALPHA NUMERIC |
      | ADDRESS LINE 2               | ALPHA NUMERIC |
      | CITY                         | RANDOM        |
      | STATE                        | RANDOM        |
      | ZIP CODE                     | RANDOM        |
      | ACCESS TYPE                  | RANDOM        |
      | CORRESPONDENCE               | RANDOM        |
      | AUTHORIZED                   | Yes           |
      | AUTH STATUS                  | SET BY DATES  |
    Then I click on Save button on Create Application Page
    And I click on the created Auth Rep name hyperlink to go to Auth Rep Details page
    And I verify the Authorized Representative Values in the Auth Rep details page

  @CP-12703 @CP-12703-12 @crm-regression @ui-ats @sang #failes due CP-17650
  Scenario: Validate null values in Authorized Representative page for Long Term Care with no minimum required value entered
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    Then I click on Save button on Create Application Page
    And I click on the created Auth Rep name hyperlink to go to Auth Rep Details page
    Then I validate double dashes are used for null values in Authorized Representative detail page

  @CP-14979 @CP-14979-24 @crm-regression @ui-ats @burak
  Scenario: Verify User not able to save Auth Rep with Past End Date and  Without Start Date
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | RANDOM 30   |
      | LAST NAME  | RANDOM 30   |
      | END DATE   | RANDOM PAST |
    Then I click save button to save Authorized Representative (negative)
    Then I verify END DATE cannot be before START DATE error message is displayed and Auth rep is not saved

  @CP-32904 @CP-32904-01 @CP-32904-02 @crm-regression @ui-ats @chandrakumar
  Scenario Outline: Verify successfully Authorized Representative is linked to application and case
    Given I logged into CRM
    And I navigate to Create "<applicationType>" application page
    And I provide mandatory primary individual details on Create Application Page
    And Select the "<programTypeName>" Program(s) for application member
    Then I fill in the following "<applicationType>" Primary Individual with the following values
      | GENDER               | Female             |
      | ARE YOU PREGNANT     | Yes                |
      | NO. BABIES EXPECTED  | 8                  |
      | EXPECTED DUE DATE    | random future date |
      | SSN                  | Numeric 9          |
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | AUTH TYPE                    | Other         |
      | FIRST NAME                   | RANDOM 30     |
      | MI                           | RANDOM 1      |
      | LAST NAME                    | RANDOM 30     |
      | ORGANIZATION NAME            | RANDOM 5      |
      | ID NUMBER                    | Numeric 5     |
      | END DATE                     | RANDOM FUTURE |
      | ADDRESS LINE 1               | ALPHA NUMERIC |
      | ADDRESS LINE 2               | ALPHA NUMERIC |
      | CITY                         | RANDOM        |
      | STATE                        | RANDOM        |
      | ZIP CODE                     | RANDOM        |
      | ACCESS TYPE                  | RANDOM        |
      | CORRESPONDENCE               | RANDOM        |
      | AUTHORIZATION SIGNATURE DATE | CURRENT       |
      | START DATE                   | RANDOM PAST   |
      | AUTHORIZED                   | <authorized>  |
      | AUTH STATUS                  | SET BY DATES  |
    Then I click on Save button on Create Application Page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
    Then I verify entered Auth Rep information in view "Application Tracking" Page
    And I click on the first case ID under links section in application tracking page
    Then I verify entered Auth Rep information in view "Demographic" Page
    And Wait for 2 seconds
    Then I click on the Auth Rep consumer Id in demographic page
    Then I verify entered Auth Rep information in view "Authorized Representative" Page
    Examples:
      | applicationType    | authorized | programTypeName |
      | MEDICAL ASSISTANCE | Yes        | Medicaid        |
      | LONG TERM CARE     | Yes        | HCBS            |

  @CP-32904 @CP-32904-03 @CP-32904-04 @crm-regression @ui-ats @chandrakumar
  Scenario Outline: Verify successfully Authorized Representative is linked to application and not linked to case
    Given I logged into CRM
    And I navigate to Create "<applicationType>" application page
    And I provide mandatory primary individual details on Create Application Page
    And Select the "<programTypeName>" Program(s) for application member
    Then I fill in the following "<applicationType>" Primary Individual with the following values
      | GENDER               | Female             |
      | ARE YOU PREGNANT     | Yes                |
      | NO. BABIES EXPECTED  | 8                  |
      | EXPECTED DUE DATE    | random future date |
      | SSN                  | Numeric 9          |
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | AUTH TYPE                    | Other         |
      | FIRST NAME                   | RANDOM 30     |
      | MI                           | RANDOM 1      |
      | LAST NAME                    | RANDOM 30     |
      | ORGANIZATION NAME            | RANDOM 5      |
      | ID NUMBER                    | Numeric 5     |
      | START DATE                   | <startDate>   |
      | END DATE                     | <endDate>     |
      | ADDRESS LINE 1               | ALPHA NUMERIC |
      | ADDRESS LINE 2               | ALPHA NUMERIC |
      | CITY                         | RANDOM        |
      | STATE                        | RANDOM        |
      | ZIP CODE                     | RANDOM        |
      | ACCESS TYPE                  | RANDOM        |
      | CORRESPONDENCE               | RANDOM        |
      | AUTHORIZED                   | <authorized>  |
      | AUTH STATUS                  | SET BY DATES  |
    Then I click on Save button on Create Application Page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
    Then I verify entered Auth Rep information in view "Application Tracking" Page
    And I click on the first case ID under links section in application tracking page
    Then I verify entered Auth Rep information is not displayed in Demographic member view Page
    Examples:
      | applicationType    | authorized | programTypeName | startDate     | endDate       |
      | MEDICAL ASSISTANCE | Yes        | Medicaid        | RANDOM FUTURE | RANDOM FUTURE |
      | MEDICAL ASSISTANCE | No         | Medicaid        | RANDOM FUTURE | RANDOM FUTURE |

  @CP-32904 @CP-32904-05 @CP-32904-06 @crm-regression @ui-ats @chandrakumar
  Scenario Outline: Verify successfully Authorized Representative is not linked to application and case
    Given I logged into CRM
    And I navigate to Create "<applicationType>" application page
    And I provide mandatory primary individual details on Create Application Page
    And Select the "<programTypeName>" Program(s) for application member
    Then I fill in the following "<applicationType>" Primary Individual with the following values
      | GENDER               | Female             |
      | ARE YOU PREGNANT     | Yes                |
      | NO. BABIES EXPECTED  | 8                  |
      | EXPECTED DUE DATE    | random future date |
      | SSN                  | Numeric 9          |
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | AUTH TYPE                    | Other         |
      | FIRST NAME                   | RANDOM 30     |
      | MI                           | RANDOM 1      |
      | LAST NAME                    | RANDOM 30     |
      | ORGANIZATION NAME            | RANDOM 5      |
      | ID NUMBER                    | Numeric 5     |
      | START DATE                   | <startDate>   |
      | END DATE                     | <endDate>     |
      | ADDRESS LINE 1               | ALPHA NUMERIC |
      | ADDRESS LINE 2               | ALPHA NUMERIC |
      | CITY                         | RANDOM        |
      | STATE                        | RANDOM        |
      | ZIP CODE                     | RANDOM        |
      | ACCESS TYPE                  | RANDOM        |
      | CORRESPONDENCE               | RANDOM        |
      | AUTHORIZED                   | <authorized>  |
      | AUTH STATUS                  | SET BY DATES  |
    Then I click on Save button on Create Application Page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
    Then I verify entered Auth Rep information is not displayed in Application Tracking Page
    And I click on the first case ID under links section in application tracking page
    Then I verify entered Auth Rep information is not displayed in Demographic member view Page
    Examples:
      | applicationType    | authorized | programTypeName | startDate   | endDate     |
      | MEDICAL ASSISTANCE | Yes        | Medicaid        | RANDOM PAST | RANDOM PAST |
      | MEDICAL ASSISTANCE | No         | Medicaid        | RANDOM PAST | RANDOM PAST |

  @CP-17381 @CP-17381-01 @crm-regression @ui-ats @priyal
  Scenario: Verify error message is displaying for Address if we give correspondence as "Do Not Receive" on create Page and verify error message is displaying for Address if we changed correspondence as "Receive in Addition To" on edit page
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I click on Save button on Create Application Page
    Then I verify "ADDRESS LINE 1" filed is not mandatory for the task
    Then I fill in the following Authorized Representative values
      | AUTH TYPE                    | RANDOM        |
      | FIRST NAME                   | RANDOM 30     |
      | MI                           | RANDOM 1      |
      | LAST NAME                    | RANDOM 30     |
      | CORRESPONDENCE               | Do Not Receive|
    Then I verify field "CORRESPONDENCE" is set to value "Do Not Receive Of" on Auth Rep Page
    Then I click on Save button on Create Application Page
    And I click on Added Authorized Representative
    And I click on Edit button for Authorized Representative
    Then I fill in the following Authorized Representative values
      | CORRESPONDENCE               | Receive in Addition To|
    Then I verify field "CORRESPONDENCE" is set to value "Receive in Addition To" on Auth Rep Page
    Then I click on Save button on Create Application Page
    Then I see error message as "ADDRESS LINE 1 is required and cannot be left blank" in the required field "ADDRESS LINE 1" in the create application page
    Then I fill in the following Authorized Representative values
      | ADDRESS LINE 1               | ALPHA NUMERIC |
      | CITY                         | RANDOM        |
      | STATE                        | RANDOM        |
      | ZIP CODE                     | 24356         |
    Then I verify field "CORRESPONDENCE" is set to value "Receive in Addition To" on Auth Rep Page
    Then I verify field "Address Line 1" is set to value "ALPHA NUMERIC" on Auth Rep Page
    Then I click on Save button on Create Application Page

  @CP-17381 @CP-17381-02 @crm-regression @ui-ats @priyal
  Scenario: Verify error message is displaying for Address if we give correspondence as "Receive in Addition To" on create Page
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I click on Save button on Create Application Page
    Then I verify "ADDRESS LINE 1" filed is not mandatory for the task
    Then I fill in the following Authorized Representative values
      | AUTH TYPE                    | RANDOM        |
      | FIRST NAME                   | RANDOM 30     |
      | MI                           | RANDOM 1      |
      | LAST NAME                    | RANDOM 30     |
      | CORRESPONDENCE               | Receive in Addition To|
    Then I verify field "CORRESPONDENCE" is set to value "Receive in Addition To" on Auth Rep Page
    Then I click on Save button on Create Application Page
    Then I see error message as "ADDRESS LINE 1 is required and cannot be left blank" in the required field "ADDRESS LINE 1" in the create application page
    Then I fill in the following Authorized Representative values
      | ADDRESS LINE 1               | ALPHA NUMERIC |
      | CITY                         | RANDOM        |
      | STATE                        | RANDOM        |
      | ZIP CODE                     | 24356         |
    Then I verify field "CORRESPONDENCE" is set to value "Receive in Addition To" on Auth Rep Page
    Then I verify field "Address Line 1" is set to value "ALPHA NUMERIC" on Auth Rep Page
    Then I click on Save button on Create Application Page

  @CP-17381 @CP-17381-03 @crm-regression @ui-ats @priyal
  Scenario: Verify Authorized is Yes when we give Auth Signature Date on create page
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I click on Save button on Create Application Page
    Then I verify field "Authorized" is set to value "No" on Auth Rep Page
    Then I fill in the following Authorized Representative values
      | AUTHORIZATION SIGNATURE DATE | RANDOM PAST   |
      | AUTH TYPE                    | RANDOM        |
      | FIRST NAME                   | RANDOM 30     |
      | MI                           | RANDOM 1      |
      | LAST NAME                    | RANDOM 30     |
    Then I verify field "Authorized Signature Date" is set to value "RANDOM PAST" on Auth Rep Page
    Then I verify field "Authorized" is set to value "Yes" on Auth Rep Page
    Then I click on Save button on Create Application Page

  @CP-17381 @CP-17381-04 @crm-regression @ui-ats @priyal
  Scenario: Verify error message is displaying for Auth Signature Date if set authorized is Yes and not giving Auth Signature Date on create page and verify Auth Signature Date field is not required when we changed Authorized from yes to No on edit page
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I click on Save button on Create Application Page
    Then I fill in the following Authorized Representative values
      | AUTHORIZED                   | Yes           |
    Then I verify field "Authorized" is set to value "Yes" on Auth Rep Page
    Then I click on Save button on Create Application Page
    Then I see error message as "AUTHORIZATION SIGNATURE DATE is required and cannot be left blank" in the required field "AUTHORIZATION SIGNATURE DATE" in the create application page
    Then I fill in the following Authorized Representative values
      | AUTHORIZATION SIGNATURE DATE | RANDOM PAST|
      | AUTH TYPE                    | RANDOM|
      | FIRST NAME                   | RANDOM 30|
      | MI                           | RANDOM 1|
      | LAST NAME                    | RANDOM 30|
    Then I verify field "Authorized Signature Date" is set to value "RANDOM PAST" on Auth Rep Page
    Then I verify field "Authorized" is set to value "Yes" on Auth Rep Page
    Then I click on Save button on Create Application Page
    And I click on Added Authorized Representative
    And I click on Edit button for Authorized Representative
    Then I fill in the following Authorized Representative values
      | AUTHORIZATION SIGNATURE DATE ||
      | AUTHORIZED                   |No|
      | FIRST NAME                   ||
    Then I click on Save button on Create Application Page
    Then I verify "AUTHORIZATION SIGNATURE DATE " filed is not mandatory for the task
    Then I fill in the following Authorized Representative values
      | FIRST NAME  | RANDOM 30 |
    Then I verify field "Authorized Signature Date" is set to value "" on Auth Rep Page
    Then I verify field "Authorized" is set to value "No" on Auth Rep Page
    Then I click on Save button on Create Application Page

  @CP-17381 @CP-17381-05 @crm-regression @ui-ats @priyal
  Scenario: Verify authorized is No if we not give Auth Signature Date on create page and verify error message is displaying for Auth Signature Date if changed authorized to Yes and not giving Auth Signature Date on edit page
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I click on Save button on Create Application Page
    Then I verify "AUTHORIZATION SIGNATURE DATE " filed is not mandatory for the task
    Then I fill in the following Authorized Representative values
      | AUTH TYPE                    | RANDOM        |
      | FIRST NAME                   | RANDOM 30     |
      | MI                           | RANDOM 1      |
      | LAST NAME                    | RANDOM 30     |
    Then I verify field "Authorized Signature Date" is set to value "" on Auth Rep Page
    Then I verify field "Authorized" is set to value "No" on Auth Rep Page
    Then I click on Save button on Create Application Page
    And I click on Added Authorized Representative
    And I click on Edit button for Authorized Representative
    Then I fill in the following Authorized Representative values
      | AUTHORIZED  | Yes|
    Then I verify field "Authorized" is set to value "Yes" on Auth Rep Page
    Then I click on Save button on Create Application Page
    Then I see error message as "AUTHORIZATION SIGNATURE DATE is required and cannot be left blank" in the required field "AUTHORIZATION SIGNATURE DATE" in the create application page
    Then I fill in the following Authorized Representative values
      | AUTHORIZATION SIGNATURE DATE | RANDOM PAST|
    Then I verify field "Authorized Signature Date" is set to value "RANDOM PAST" on Auth Rep Page
    Then I verify field "Authorized" is set to value "Yes" on Auth Rep Page
    Then I click on Save button on Create Application Page

  @CP-17381 @CP-17381-06 @crm-regression @ui-ats @priyal
  Scenario: Verify error message is displaying for Address if we give correspondence as "Receive in Place Of" on create page and verify error message is not displaying for Address if we changed correspondence as "Do Not Receive" on edit page
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I click on Save button on Create Application Page
    Then I verify "ADDRESS LINE 1" filed is not mandatory for the task
    Then I fill in the following Authorized Representative values
      | AUTH TYPE                    | RANDOM        |
      | FIRST NAME                   | RANDOM 30     |
      | MI                           | RANDOM 1      |
      | LAST NAME                    | RANDOM 30     |
      | CORRESPONDENCE               | Receive in Place Of|
    Then I verify field "CORRESPONDENCE" is set to value "Receive in Place Of" on Auth Rep Page
    Then I click on Save button on Create Application Page
    Then I see error message as "ADDRESS LINE 1 is required and cannot be left blank" in the required field "ADDRESS LINE 1" in the create application page
    Then I fill in the following Authorized Representative values
      | ADDRESS LINE 1               | ALPHA NUMERIC |
      | CITY                         | RANDOM        |
      | STATE                        | RANDOM        |
      | ZIP CODE                     | 24356         |
    Then I verify field "CORRESPONDENCE" is set to value "Receive in Place Of" on Auth Rep Page
    Then I verify field "Address Line 1" is set to value "ALPHA NUMERIC" on Auth Rep Page
    Then I click on Save button on Create Application Page
    And I click on Added Authorized Representative
    And I click on Edit button for Authorized Representative
    Then I fill in the following Authorized Representative values
      | ADDRESS LINE 1               ||
      | CITY                         ||
      | STATE                        ||
      | ZIP CODE                     ||
      | CORRESPONDENCE               | Do Not Receive|
      | FIRST NAME                   ||
    Then I click on Save button on Create Application Page
    Then I verify "ADDRESS LINE 1" filed is not mandatory for the task
    Then I fill in the following Authorized Representative values
      | FIRST NAME  | RANDOM 30 |
    Then I verify field "CORRESPONDENCE" is set to value "Do Not Receive Of" on Auth Rep Page
    Then I verify field "Address Line 1" is set to value "" on Auth Rep Page
    Then I click on Save button on Create Application Page

