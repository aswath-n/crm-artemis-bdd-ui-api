Feature: Save/Submit/View for UI Created Applications

  @CP-15844 @CP-15844-01.1.0 @crm-regression @ui-ats @asad
  Scenario: Verify Interactive flag for UI created applications After Save
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I verify the interactive indicator from application details of the Created Application from "UI" for interactive type "UI"

  @CP-15844 @CP-15844-01.1.1 @crm-regression @ui-ats @asad
  Scenario: Verify Interactive flag for UI Created Applications After Save and Submit Application
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I choose "Pregnancy Assistance" as program type
    Then I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    Then I click on Submit button only in Create Application Page
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I verify the interactive indicator from application details of the Created Application from "UI" for interactive type "UI"

  @CP-15844 @CP-15844-01.1.2 @crm-regression @ui-ats @asad  #will be failed due to CP-23841
  Scenario: Verify Interactive flag for UI created applications After Save Members and Submit Application
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    When I add Application Member for the Created Application with mandatory fields
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I verify the interactive indicator from application details of the Created Application from "UI" for interactive type "UI"
    When I add Authorized Representative for the Created Application with mandatory fields
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I verify the interactive indicator from application details of the Created Application from "UI" for interactive type "UI"
    And I click on Submit button and continue button for application without Programs
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I verify the interactive indicator from application details of the Created Application from "UI" for interactive type "UI"

  @CP-16710 @CP-15428 @ozgen @ui-ats @crm-regression
  Scenario: Added applicationConsumerStatus field on View API for UI created applications
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I choose "Pregnancy Assistance" as program type
    Then I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    When I add Application Member for the Created Application with mandatory fields
    When I add Authorized Representative for the Created Application with mandatory fields
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I verify all consumers objects have applicationConsumerStatus field
    Then I click on Submit button only in Create Application Page
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I verify all consumers objects have applicationConsumerStatus field

  @CP-12357 @CP-12357-01 @crm-regression @ui-ats @munavvar
  Scenario: Submit for Clearance Medical Assistance negative
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I fill in the Primary Individual Contact Information with the following values
      | EMAIL | TEST@TEST.COM |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I click on Save button on Create Application Page
    Then I see error message as "FIRST NAME is required and cannot be left blank" in the required field "FIRST NAME" in the create application page
    And I see error message as "LAST NAME is required and cannot be left blank" in the required field "LAST NAME" in the create application page

  @CP-12357 @CP-12357-02 @crm-regression @ui-ats @munavvar
  Scenario: Submit for Clearance LONG TERM CARE negative
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    And I fill in the Primary Individual Contact Information with the following values
      | EMAIL | TEST@TEST.COM |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I click on Save button on Create Application Page
    Then I see error message as "FIRST NAME is required and cannot be left blank" in the required field "FIRST NAME" in the create application page
    And I see error message as "LAST NAME is required and cannot be left blank" in the required field "LAST NAME" in the create application page

  @CP-12357 @CP-12357-03 @crm-regression @ui-ats @munavvar
  Scenario: Submit for Clearance Medical Assistance
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I choose Communication Opt In Information by the following list
      | Email |
    And I fill in the Primary Individual Contact Information with the following values
      | EMAIL | TEST@TEST.COM |
    When I click on Save button on Create Application Page
    Then I see error message as "FIRST NAME is required and cannot be left blank" in the required field "FIRST NAME" in the create application page
    And I see error message as "LAST NAME is required and cannot be left blank" in the required field "LAST NAME" in the create application page
    And I see error message as "CHANNEL is required and cannot be left blank" in the required field "CHANNEL" in the create application page

  @CP-12357 @CP-12357-04 @crm-regression @ui-ats @munavvar
  Scenario: Submit for Clearance LONG TERM CARE
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    And I choose Communication Opt In Information by the following list
      | Email |
    And I fill in the Primary Individual Contact Information with the following values
      | EMAIL | TEST@TEST.COM |
    When I click on Save button on Create Application Page
    Then I see error message as "FIRST NAME is required and cannot be left blank" in the required field "FIRST NAME" in the create application page
    And I see error message as "LAST NAME is required and cannot be left blank" in the required field "LAST NAME" in the create application page
    And I see error message as "CHANNEL is required and cannot be left blank" in the required field "CHANNEL" in the create application page

  @CP-12357 @CP-15567 @CP-15567-01 @CP-12357-05 @CP-33955 @CP-33955-01 @CP-33955-02 @crm-regression @ui-ats @munavvar
    #Vinuta - Refactored as per CP-29191
  Scenario: Minimum Requirements to Submit the Application
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME     | Alphabetic 30    |
      | MIDDLE INITIAL | Alphabetic 1     |
      | LAST NAME      | Alphabetic 30    |
      | SUFFIX         | Alphabetic 3     |
      | DOB            | random past date |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    Then I click on Save button on Create Application Page
    And I verify the following saved Primary Individual application details
      | FULL NAME |
    And I see Application tab is selected
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | MI         | RANDOM 1  |
      | LAST NAME  | RANDOM 30 |
      | SUFFIX     | RANDOM 3  |
    And click on save button for add application member
    And I verify the save successfully updated message
    When I click Add Authorized Representative button
    And I enter information to add "FUTURE" Authorized Representative
    And I click save button to save Authorized Representative
    And I click on Submit button and continue button for application without Programs
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
   # And I see I navigated to Member Matching page

  @CP-12357 @CP-12357-06 @crm-regression @ui-ats @munavvar
  Scenario: No Application Member applying for Coverage
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I add Application Member for the Created Application with mandatory fields
    When I add Authorized Representative for the Created Application with mandatory fields
    Then I click on Submit button only in Create Application Page
    Then I verify "Must have at least one Application Member applying for coverage." warning message displayed in create application page

  @CP-12357 @CP-12357-07 @crm-regression @ui-ats @munavvar
  Scenario: Generate Application Status
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I choose "Medicaid" as program type
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME       | Alphabetic 30    |
      | LAST NAME        | Alphabetic 30    |
      | DOB              | random past date |
      | GENDER           | Female           |
      | ARE YOU PREGNANT | No               |
      | SSN              | Numeric 9        |
    And I choose Communication Opt In Information by the following list
      | Phone |
    And I select Primary Individual Application information with the following
      | CYCLE          | New    |
      | CHANNEL        | random |
      | SIGNATURE      | Yes    |
      | SIGNATURE DATE | random |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    When I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    And I click on Submit button only in Create Application Page
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
  #  And I see I navigated to Member Matching page
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created application Id in the search application page Search menu
    When I click on search button in search application page
    And I click on first APPLICATION ID from created application
    And I see Application Tracking is selected by default
    When I click application id under Application Tracking tab in the Application Information panel
    Then I see application Status as "DETERMINING" in the create application page

  @CP-12357 @CP-12357-08 @crm-regression @ui-ats @munavvar
  Scenario: Validating if Created On and Updated On are NOT the same
    Given I logged into CRM
    When I saved logged in user ID
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    When I add Authorized Representative for the Created Application with mandatory fields
    And I get application id from the Create Application Page
    Then I click on Submit button only in Create Application Page
    Then I verify "Must have at least one Application Member applying for coverage." warning message displayed in create application page
    Then I click Continue button inside the warning message
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
  #  Then I click on member matching page back arrow to navigate to create application page
    And I click on application tracking tab to navigate to Application Tracking page
    Then I see application Status as "DETERMINING" in the create application page
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I verify PI UPDATEDON is later than PI CREATEDON
    Then I Verify the following from the GET Application with ApplicationId
      | PI CREATEDBY | base                  |
      | PI CREATEDON | today's date and hour |
      | PI UPDATEDBY | base                  |
      | PI UPDATEDON | today's date and hour |

  @CP-12357  @CP-12357-09 @crm-regression @ui-ats @munavvar
  Scenario: Cancel button verification inside warning message while submitting
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME     | Alphabetic 30    |
      | MIDDLE INITIAL | Alphabetic 1     |
      | LAST NAME      | Alphabetic 30    |
      | SUFFIX         | Alphabetic 3     |
      | DOB            | random past date |
      | GENDER         | Female           |
    And I choose Communication Opt In Information by the following list
      | Phone |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    Then I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    Then I click on Submit button only in Create Application Page
    Then I verify "Must have at least one Application Member applying for coverage." warning message displayed in create application page
    Then I click Cancel Button to stay on the Create Application Page
    Then I Verify that user is back to Create Application page
    Then I see application Status as "ENTERING DATA" in the create application page

  @CP-12357  @CP-12357-10 @crm-regression @ui-ats @munavvar
  Scenario: Continue button verification inside warning message while submitting
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME     | Alphabetic 30    |
      | MIDDLE INITIAL | Alphabetic 1     |
      | LAST NAME      | Alphabetic 30    |
      | SUFFIX         | Alphabetic 3     |
      | DOB            | random past date |
      | GENDER         | Female           |
    And I choose Communication Opt In Information by the following list
      | Phone |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    Then I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    Then I click on Submit button only in Create Application Page
    Then I verify "Must have at least one Application Member applying for coverage." warning message displayed in create application page
    Then I click Continue button inside the warning message
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
   # And I see I navigated to Member Matching page
    #updated due to changed functionality
  #  And verify the "If you continue, all the captured information will be lost" warning message
 #   Then I close the Success Message displayed in Create Application Page by clicking Close button
  #  Then I click continue inside warning message to navigate to member matching page
  #  Then I click on member matching page back arrow to navigate to create application page
    Then I see application Status as "DETERMINING" in the create application page

  @CP-28809 @CP-28809-01 @crm-regression @ui-ats @burak
  Scenario: Display warning message while submitting when application is missing critical information and submit application with adding new application member
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Alphabetic 30 |
      | LAST NAME  | Alphabetic 30 |
    And I choose Communication Opt In Information by the following list
      | Phone |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    Then I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    Then I click on Submit button and continue button for application without Programs
    Then I verify "Application missing critical information, please update the required fields and submit again" warning message displayed in create application page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30        |
      | LAST NAME  | RANDOM 30        |
      | DOB        | random past date |
    And click on save button for add application member
    Then I click on Submit button and continue button for application without Programs
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
   # And I see I navigated to Member Matching page
   # Then I click on member matching page back arrow to navigate to create application page
    Then I see application Status as "DETERMINING" in the create application page

  @CP-28809 @CP-28809-02 @crm-regression @ui-ats @burak
  Scenario: Display warning message while submitting when application is missing critical information and submit application with editing existing member information
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Alphabetic 30 |
      | LAST NAME  | Alphabetic 30 |
    And I choose Communication Opt In Information by the following list
      | Phone |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    Then I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    Then I click on Submit button and continue button for application without Programs
    Then I verify "Application missing critical information, please update the required fields and submit again" warning message displayed in create application page
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    Then I fill in the following application member values
      | DOB | random past date |
    And click on save button for add application member
    Then I click on Submit button and continue button for application without Programs
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
  #  And I see I navigated to Member Matching page
  #  Then I click on member matching page back arrow to navigate to create application page
    Then I see application Status as "DETERMINING" in the create application page

  @CP-28809 @CP-28809-03 @crm-regression @ui-ats @burak
  Scenario: Display warning message while submitting when application is missing critical information and submit application with adding new application member(Non-Interactive)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd | applicationCycle |
      | Long Term Care  | CURRENT YYYYMMDD        | true         | New              |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I see application Status as "INSUFFICIENT" in the create application page
    Then I click on Submit button only in Create Application Page
    Then I verify "Application missing critical information, please update the required fields and submit again" warning message displayed in create application page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30        |
      | LAST NAME  | RANDOM 30        |
      | DOB        | random past date |
    And click on save button for add application member
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
  #  And I see I navigated to Member Matching page
  #  Then I click on member matching page back arrow to navigate to create application page
    Then I see application Status as "DETERMINING" in the create application page

  @CP-28809 @CP-28809-04 @crm-regression @ui-ats @burak
  Scenario: Display warning message while submitting when application is missing critical information and submit application with editing existing member information(Non-Interactive)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd | applicationCycle |
      | Long Term Care  | CURRENT YYYYMMDD        | true         | New              |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I see application Status as "INSUFFICIENT" in the create application page
    Then I click on Submit button and continue button for application without Programs
    Then I verify "Application missing critical information, please update the required fields and submit again" warning message displayed in create application page
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    Then I fill in the following application member values
      | DOB | random past date |
    And click on save button for add application member
    Then I click on Submit button and continue button for application without Programs
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
  #  And I see I navigated to Member Matching page
  #  Then I click on member matching page back arrow to navigate to create application page
    Then I see application Status as "DETERMINING" in the create application page

  @CP-29657 @CP-29657-01 @crm-regression @ui-ats @sang
  Scenario: Verify Application Consumers can no longer be removed when the status is set to Determining for Medical Assistance Applications
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I clear the program list for "PI" Application Members
    Then I choose "Medicaid" as program type
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Alphabetic 30    |
      | LAST NAME  | Alphabetic 30    |
      | DOB        | random past date |
      | GENDER     | Male             |
      | SSN        | Numeric 9        |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | HOME PHONE NUMBER | Random Numeric 10 |
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30        |
      | LAST NAME  | RANDOM 30        |
      | DOB        | random past date |
      | GENDER     | Male             |
      | SSN        | Numeric 9        |
    Then I clear the program list for "AM" Application Members
    And Select the "CHIP" Program(s) for application member
    And click on save button for add application member
    And I verify the save successfully updated message
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And I click save button to save Authorized Representative
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
#    And I see I navigated to Member Matching page
 #   When I click on the "NEW CASE" button on Member Matching
 #   And I click on the "SAVE_ACTION" button on Member Matching
    Then I verify I am on the Application Tracking Page
    And I see application Status as "DETERMINING" in the application information
    And I navigate to application tab page
    And I verify Add Application member button is displayed  and disabled in create application page in Application tab
    Then I verify set as Primary Individual button is displayed and disabled in application member panel create application page in Application tab
    Then I verify application Member Remove Primary Individual button is displayed and disabled in application member panel create application page in Application tab
    And I verify Add Authorized Representative button is displayed  and disabled in create application page in Application tab
    And I click on application member name hyperlink to go to Application details page
    And I verify Edit button and Remove button for Application Member page are displayed and disabled
    Then I click on the back arrow button on add application member page
    And I click on Added Authorized Representative
    And I click on Edit button for Authorized Representative
    And I verify Edit button for Authorized Representative page is displayed and disabled
    Then I click on the back arrow button on add application member page
    And I see Application tab is selected
    And I see Submit button is displayed and disabled in create application page in Application tab

  @CP-29657 @CP-29657-02 @crm-regression @ui-ats @sang
  Scenario: Verify Application Consumers can no longer be removed when the status is set to Determining for Long Term Care Applications
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    Then I clear the program list for "PI" Application Members
    Then I choose "HCBS" as program type
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Alphabetic 30    |
      | LAST NAME  | Alphabetic 30    |
      | DOB        | random past date |
      | GENDER     | Male             |
      | SSN        | Numeric 9        |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | HOME PHONE NUMBER | Random Numeric 10 |
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30        |
      | LAST NAME  | RANDOM 30        |
      | DOB        | random past date |
      | GENDER     | Male             |
      | SSN        | Numeric 9        |
    Then I clear the program list for "AM" Application Members
    And Select the "HCBS" Program(s) for application member
    And click on save button for add application member
    And I verify the save successfully updated message
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And I click save button to save Authorized Representative
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
  #  And I see I navigated to Member Matching page
  #  When I click on the "NEW CASE" button on Member Matching
  #  And I click on the "SAVE_ACTION" button on Member Matching
    Then I verify I am on the Application Tracking Page
    And I see application Status as "DETERMINING" in the application information
    And I navigate to application tab page
    And I verify Add Application member button is displayed  and disabled in create application page in Application tab
    Then I verify set as Primary Individual button is displayed and disabled in application member panel create application page in Application tab
    Then I verify application Member Remove Primary Individual button is displayed and disabled in application member panel create application page in Application tab
    And I verify Add Authorized Representative button is displayed  and disabled in create application page in Application tab
    And I click on application member name hyperlink to go to Application details page
    And I verify Edit button and Remove button for Application Member page are displayed and disabled
    Then I click on the back arrow button on add application member page
    And I click on Added Authorized Representative
    And I click on Edit button for Authorized Representative
    And I verify Edit button for Authorized Representative page is displayed and disabled
    Then I click on the back arrow button on add application member page
    And I see Application tab is selected
    And I see Submit button is displayed and disabled in create application page in Application tab

  @CP-36119 @CP-36119-01 @crm-regression @ui-ats @ozgen
  Scenario: Verification of default value of application signature ind when app signature date is provided from API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 1
      | consumerFirstName | consumerLastName | consumerMiddleName | correspondence      | authorizedRepAppStartDate | authorizedRepAppEndDate | consumerType | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate | accessType  |
      | RANDOM FIRST      | RANDOM LAST      | M                  | Receive in Place Of | PAST TIMESTAMP            | FUTURE TIMESTAMP        | Broker       | VA Foundation        | true                   | CURRENT TIMESTAMP          | Full Access |
    And I initiate save applications api consumer 1 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Mailing     | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I verify that application signature ind is selected "true"

  @CP-36119 @CP-36119-02 @crm-regression @ui-ats @ozgen
  Scenario: Verification of default value of application signature ind when app signature date is provided from UI
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME       | Alphabetic 30    |
      | LAST NAME        | Alphabetic 30    |
      | DOB              | random past date |
      | GENDER           | Male             |
      | SSN              | Numeric 9        |
    And I select Primary Individual Application information with the following
      | CYCLE          | New    |
      | CHANNEL        | random |
      | SIGNATURE DATE | random |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    When I click on Save button on Create Application Page
    Then I verify that application signature ind is selected "true"

  @CP-36119 @CP-36119-04 @crm-regression @ui-ats @ozgen
  Scenario: Verification of being required of application signature when app signature ind is provided as true from UI
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME       | Alphabetic 30    |
      | LAST NAME        | Alphabetic 30    |
      | DOB              | random past date |
      | GENDER           | Male             |
      | SSN              | Numeric 9        |
    And I select Primary Individual Application information with the following
      | CYCLE          | New    |
      | CHANNEL        | random |
      | SIGNATURE      | Yes    |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    When I click on Save button on Create Application Page
    Then I see error message as "SIGNATURE DATE is required and cannot be left blank" in the required field "SIGNATURE DATE" in the create application page
