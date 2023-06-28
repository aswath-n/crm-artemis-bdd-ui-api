Feature: Create Application Member

  @CP-14978 @CP-14978-01 @crm-regression @ui-ats @sang
  Scenario: Navigate to the create Application Member screen Medical Assistance
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    Then I click on + Add Application Member button
    And I verify Application Member header on create Application Member Page

  @CP-14978 @CP-14978-02 @crm-regression @ui-ats @sang
  Scenario: Navigate to the create Application Member screen Long Term Care
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    Then I click on + Add Application Member button
    And I verify Application Member header on create Application Member Page

  @CP-14978 @CP-14978-03 @crm-regression @ui-ats @sang
  Scenario: Verify the labels in MEDICAL ASSISTANCE Add Application screen
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I verify the labels for add application "MEDICAL ASSISTANCE"

  @CP-14978 @CP-14978-04 @crm-regression @ui-ats @sang
  Scenario: Verify the labels in LONG TERM CARE Add Application screen
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I verify the labels for add application "LONG TERM CARE"

  @CP-14978 @CP-14978-05 @crm-regression @ui-ats @sang @ats-smoke
  Scenario: Verify successful Medical Assistance save with all valid field values
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME           | RANDOM 30        |
      | MI                   | RANDOM 1         |
      | LAST NAME            | RANDOM 30        |
      | SUFFIX               | RANDOM 3         |
      | DOB                  | random past date |
      | GENDER               | Female           |
      | ARE YOU PREGNANT     | YES              |
      | NO. OF BABIES        | 2                |
      | EXPECTED DUE DATE    | RANDOM           |
      | SSN                  | Numeric 9        |
      | EXTERNAL CONSUMER ID | RANDOM           |
      | EXTERNAL ID TYPE     | random dropdown  |
    Then I clear the program list for "AM" Application Members
    And Select the "CHIP" Program(s) for application member
    And Select the "Medicaid" Program(s) for application member
    And Select the "Pregnancy Assistance" Program(s) for application member
    And click on save button for add application member
    And I verify the save successfully updated message

  @CP-14978 @CP-14978-06 @crm-regression @ui-ats @sang
  Scenario: Verify successful Long Term Care save with all valid field values
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME           | RANDOM 30        |
      | MI                   | RANDOM 1         |
      | LAST NAME            | RANDOM 30        |
      | SUFFIX               | RANDOM 3         |
      | DOB                  | random past date |
      | GENDER               | Female           |
      | ARE YOU PREGNANT     | YES              |
      | NO. OF BABIES        | 2                |
      | EXPECTED DUE DATE    | RANDOM           |
      | SSN                  | Numeric 9        |
      | EXTERNAL CONSUMER ID | RANDOM           |
      | EXTERNAL ID TYPE     | random dropdown  |
    Then I clear the program list for "AM" Application Members
    And Select the "HCBS" Program(s) for application member
    And click on save button for add application member
    And I verify the save successfully updated message

  @CP-14978 @CP-14978-07 @crm-regression @ui-ats @sang
  Scenario: Verify minimum field required message for Medical Assistance Add Application Member
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    And click on save button for add application member
    Then I verify the First and last name required warning message in Add application

  @CP-14978 @CP-14978-08 @crm-regression @ui-ats @sang
  Scenario: Verify minimum field required message for Long Term Care Add Application Member
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    And click on save button for add application member
    Then I verify the First and last name required warning message in Add application

  @CP-14978 @CP-14978-09 @crm-regression @ui-ats @sang
  Scenario: Verify Application Members labels in Application Member(s) panel for Medical Assistance
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    Then I verify the labels in application members panel collapsed View
    And I verify the labels in application members panel expanded view

  @CP-14978 @CP-14978-10 @crm-regression @ui-ats @sang
  Scenario: Verify Application Members labels in Application Member(s) panel for Long Term Care
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    Then I verify the labels in application members panel collapsed View
    And I verify the labels in application members panel expanded view

  @CP-14978 @CP-14978-11 @crm-regression @ui-ats @sang @CP-12703 @CP-12703-13  #will fail due to CP-36572
  Scenario: Verify Application Members values are populated in Application Member(s) panel for Medical Assistance
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME           | RANDOM 30        |
      | MI                   | RANDOM 1         |
      | LAST NAME            | RANDOM 30        |
      | SUFFIX               | RANDOM 3         |
      | DOB                  | random past date |
      | GENDER               | Female           |
      | ARE YOU PREGNANT     | YES              |
      | NO. OF BABIES        | 2                |
      | EXPECTED DUE DATE    | RANDOM           |
      | SSN                  | Numeric 9        |
      | EXTERNAL CONSUMER ID | RANDOM           |
      | EXTERNAL ID TYPE     | random dropdown  |
    And Select the "CHIP" Program(s) for application member
    And Select the "Medicaid" Program(s) for application member
    And Select the "Pregnancy Assistance" Program(s) for application member
    And click on save button for add application member
    Then I verify the saved application values in Application Member(s) panel collapsed view
    And I verify the saved application values in Application Member(s) panel expanded view
    Then I clear the program list for "AM" Application Members

  @CP-14978 @CP-14978-12 @crm-regression @ui-ats @sang @CP-12703 @CP-12703-14 #will fail due to CP-36572
  Scenario: Verify Application Members values are populated in Application Member(s) panel for Long Term Care
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME           | RANDOM 30        |
      | MI                   | RANDOM 1         |
      | LAST NAME            | RANDOM 30        |
      | SUFFIX               | RANDOM 3         |
      | DOB                  | random past date |
      | GENDER               | Female           |
      | ARE YOU PREGNANT     | YES              |
      | NO. OF BABIES        | 2                |
      | EXPECTED DUE DATE    | RANDOM           |
      | SSN                  | Numeric 9        |
      | EXTERNAL CONSUMER ID | RANDOM           |
      | EXTERNAL ID TYPE     | random dropdown  |
    And Select the "HCBS" Program(s) for application member
    And click on save button for add application member
    Then I verify the saved application values in Application Member(s) panel collapsed view
    And I verify the saved application values in Application Member(s) panel expanded view
    Then I clear the program list for "AM" Application Members

  @CP-14978 @CP-14978-13 @crm-regression @ui-ats @sang
  Scenario: Application Member Change to Primary Individual using the set PI indicator for Medical assistance
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | MI         | RANDOM 1  |
      | LAST NAME  | RANDOM 30 |
      | SUFFIX     | RANDOM 3  |
    And I click on Set PI checkbox in add application member page
    And click on save button for add application member
    Then I verify the "Are you sure you want to change the Primary Individual?" message is displayed
    And I select continue to change application member to Primary Individual
    And I verify the the application member is primary member and the previous PI is an application member

  @CP-14978 @CP-14978-14 @crm-regression @ui-ats @sang
  Scenario: Application Member Change to Primary Individual using the set PI indicator for Long Term Care
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | MI         | RANDOM 1  |
      | LAST NAME  | RANDOM 30 |
      | SUFFIX     | RANDOM 3  |
    And I click on Set PI checkbox in add application member page
    And click on save button for add application member
    Then I verify the "Are you sure you want to change the Primary Individual?" message is displayed
    And I select continue to change application member to Primary Individual
    And I verify the the application member is primary member and the previous PI is an application member

  @CP-14978 @CP-14978-15 @crm-regression @ui-ats @sang
  Scenario: Edit Application member Medical Assistance
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    And I click on application member name hyperlink to go to Application details page
    And I verify edit button is displayed and clickable in Add Application member

  @CP-14978 @CP-14978-16 @crm-regression @ui-ats @sang
  Scenario: Edit Application member Long Term Care
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    And I click on application member name hyperlink to go to Application details page
    And I verify edit button is displayed and clickable in Add Application member

  @CP-14978 @CP-14978-17 @crm-regression @ui-ats @sang
  Scenario: Verify back arrow warning message in Medical assistance add application
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
    Then I click on the back arrow button on add application member page
    And verify the "If you continue, all the captured information will be lost" warning message
    And I verify I am on application details tab by clicking on continue

  @CP-14978 @CP-14978-18 @crm-regression @ui-ats @sang
  Scenario: Verify back arrow warning message in Long Term Care add application
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
    Then I click on the back arrow button on add application member page
    And verify the "If you continue, all the captured information will be lost" warning message
    And I verify I am on application details tab by clicking on continue

  @CP-14978 @CP-14978-19 @crm-regression @ui-ats @sang
  Scenario: Verify no back arrow warning message is displayed in Medical assistance add application
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I click on the back arrow button on add application member page
    And I verify no "If you continue, all the captured information will be lost" warning message has appeared

  @CP-14978 @CP-14978-20 @crm-regression @ui-ats @sang
  Scenario: Verify no back arrow warning message is displayed in Long Term Care add application
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I click on the back arrow button on add application member page
    And I verify no "If you continue, all the captured information will be lost" warning message has appeared

  @CP-14978 @CP-14978-21 @crm-regression @ui-ats @sang
  Scenario: Verify Add Application Member fields are cleared and returned to default upon clicking cancel button for Medical Assistance
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME           | RANDOM 30        |
      | MI                   | RANDOM 1         |
      | LAST NAME            | RANDOM 30        |
      | SUFFIX               | RANDOM 3         |
      | DOB                  | random past date |
      | GENDER               | Female           |
      | ARE YOU PREGNANT     | YES              |
      | NO. OF BABIES        | 2                |
      | EXPECTED DUE DATE    | RANDOM           |
      | SSN                  | Numeric 9        |
      | EXTERNAL CONSUMER ID | RANDOM           |
      | EXTERNAL ID TYPE     | random dropdown  |
    And I click on Set PI checkbox in add application member page
    And Select the "CHIP" Program(s) for application member
    And Select the "Medicaid" Program(s) for application member
    And Select the "Pregnancy Assistance" Program(s) for application member
    Then I click on the Cancel Button in add application member page
    And I verify all fields in add application member page has cleared for "MEDICAL ASSISTANCE"
    Then I clear the program list for "AM" Application Members

  @CP-14978 @CP-14978-22 @crm-regression @ui-ats @sang
  Scenario: Verify Add Application Member fields are cleared and returned to default upon clicking cancel button for Long Term Care
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME           | RANDOM 30        |
      | MI                   | RANDOM 1         |
      | LAST NAME            | RANDOM 30        |
      | SUFFIX               | RANDOM 3         |
      | DOB                  | random past date |
      | GENDER               | Female           |
      | ARE YOU PREGNANT     | YES              |
      | NO. OF BABIES        | 2                |
      | EXPECTED DUE DATE    | RANDOM           |
      | SSN                  | Numeric 9        |
      | EXTERNAL CONSUMER ID | RANDOM           |
      | EXTERNAL ID TYPE     | random dropdown  |
    And I click on Set PI checkbox in add application member page
    And Select the "HCBS" Program(s) for application member
    Then I click on the Cancel Button in add application member page
    And I verify all fields in add application member page has cleared for "LONG TERM CARE"
    Then I clear the program list for "AM" Application Members

  @CP-14978 @CP-14978-23 @crm-regression @ui-ats @sang
  Scenario: Verify Medical Assistance Single Program selected is shown in application member panel
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30        |
      | LAST NAME  | RANDOM 30        |
      | DOB        | random past date |
      | GENDER     | Male             |
    And Select the "CHIP" Program(s) for application member
    And click on save button for add application member
    Then I verify selected application member program is shown in the application member panel
    Then I clear the program list for "AM" Application Members

  @CP-14978 @CP-14978-24 @crm-regression @ui-ats @sang
  Scenario: Verify Medical Assistance double Program selected is shown in application member panel
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30        |
      | LAST NAME  | RANDOM 30        |
      | DOB        | random past date |
      | GENDER     | Male             |
      | SSN        | Numeric 9        |
    And Select the "CHIP" Program(s) for application member
    And Select the "Medicaid" Program(s) for application member
    And click on save button for add application member
    Then I verify selected application member program is shown in the application member panel
    Then I clear the program list for "AM" Application Members

  @CP-14978 @CP-14978-25 @crm-regression @ui-ats @sang
  Scenario: Verify Medical Assistance Application member removed after selecting delete button from application member grid panel
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    And I click on the delete application member button in the application member button panel
    Then I verify the application member is deleted from the application member(s) panel

  @CP-14978 @CP-14978-26 @crm-regression @ui-ats @sang
  Scenario: Verify Long Term Care Application member removed after selecting delete button from application member grid panel
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    And I click on the delete application member button in the application member button panel
    Then I verify the application member is deleted from the application member(s) panel

  @CP-14978 @CP-14978-27 @crm-regression @ui-ats @sang
  Scenario: Verify Medical Assistance Application member removed after selecting delete button from add application member screen
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    And I click on application member name hyperlink to go to Application details page
    And I click on the remove application member from add application member screen
    Then I verify the application member is deleted from the application member(s) panel

  @CP-14978 @CP-14978-28 @crm-regression @ui-ats @sang
  Scenario: Verify Long Term Care Application member removed after selecting delete button from application member screen
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    And I click on application member name hyperlink to go to Application details page
    And I click on the remove application member from add application member screen
    Then I verify the application member is deleted from the application member(s) panel

  @CP-14978 @CP-14978-29 @crm-regression @ui-ats @sang
  Scenario: Verify CreatedBy and CreatedOn for Application Member UI
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    And I get application id from the Create Application Page
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I Verify the following from the GET Application with ApplicationId
      | APP MEM CREATEDBY | base                  |
      | APP MEM CREATEDON | today's date and hour |

  @CP-14978 @CP-14978-30 @crm-regression @ui-ats @sang
  Scenario: Verify UpdatedBy and UdatedOn for Application Member UI
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
    And click on save button for add application member
    And I get application id from the Create Application Page
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I Verify the following from the GET Application with ApplicationId
      | APP MEM UPDATEDBY | base                  |
      | APP MEM UPDATEDON | today's date and hour |

  @CP-14978 @CP-14978-31 @crm-regression @ui-ats @sang @AM-REFACTORED
  Scenario: Verify null values for Application Member in the Application Member panel for Medical Assistance
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    Then I verify double dash is represented for NULL values for Application Member in detail panel information

  @CP-14978 @CP-14978-32 @crm-regression @ui-ats @sang @AM-REFACTORED
  Scenario: Verify null values for Application Member in the Application Member panel for Long Term Care
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    Then I verify double dash is represented for NULL values for Application Member in detail panel information

  @CP-14978 @CP-14978-33 @crm-regression @ui-ats @sang @AM-REFACTORED #will fail due to CP-36572
  Scenario: View and verify Application Member details from application created by API in the application tab
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | externalConsumerId | externalConsumerIdType | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      | RANDOM LAST      | Dr             | A                  | 1998-04-03  | RANDOM SSN | Female     | 23456              | Internal               | true         | 2              | FUTURE DATE     |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Pregnancy Assistance |
    And I POST ATS save application api
    And I set Application Member Values from the sent application request payload to be checked
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I verify the saved application values in Application Member(s) panel collapsed view
    And I verify the saved application values in Application Member(s) panel expanded view

  @CP-12703 @CP-12703-04 @crm-regression @ui-ats @sang
  Scenario: Verify Application Members details Page has correct labels for LONG TERM CARE
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME        | RANDOM 30 |
      | LAST NAME         | RANDOM 30 |
      | GENDER            | Female    |
      | ARE YOU PREGNANT  | YES       |
      | NO. OF BABIES     | 2         |
      | EXPECTED DUE DATE | RANDOM    |
    And click on save button for add application member
    And I click on application member name hyperlink to go to Application details page
    Then I verify Application Member detail page has correct labels for "LONG TERM CARE"

  @CP-12703 @CP-12703-05 @crm-regression @ui-ats @sang
  Scenario: Verify Application Members details Page has correct labels for MEDICAL ASSISTANCE
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME        | RANDOM 30 |
      | LAST NAME         | RANDOM 30 |
      | GENDER            | Female    |
      | ARE YOU PREGNANT  | YES       |
      | NO. OF BABIES     | 2         |
      | EXPECTED DUE DATE | RANDOM    |
    And click on save button for add application member
    And I click on application member name hyperlink to go to Application details page
    Then I verify Application Member detail page has correct labels for "MEDICAL ASSISTANCE"

  @CP-12703 @CP-12703-06 @crm-regression @ui-ats @sang
  Scenario: Verify entered Application Members value in Application Members details Page for Long Term Care
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME           | RANDOM 30        |
      | MI                   | RANDOM 1         |
      | LAST NAME            | RANDOM 30        |
      | SUFFIX               | RANDOM 3         |
      | DOB                  | random past date |
      | GENDER               | Female           |
      | ARE YOU PREGNANT     | YES              |
      | NO. OF BABIES        | 2                |
      | EXPECTED DUE DATE    | RANDOM           |
      | SSN                  | Numeric 9        |
      | EXTERNAL CONSUMER ID | RANDOM           |
      | EXTERNAL ID TYPE     | random dropdown  |
    Then I clear the program list for "AM" Application Members
    And Select the "HCBS" Program(s) for application member
    And click on save button for add application member
    And I click on application member name hyperlink to go to Application details page
    Then I verify the Application member Values in the application member details page
      | FIRST | MI | LAST | SUFFIX | DOB | AGE | GENDER | ARE YOU PREGNANT | NO OF BABIES | DUE DATE | SSN | EXID | EXTYPE | PROGRAMS |

  @CP-12703 @CP-12703-07 @crm-regression @ui-ats @sang
  Scenario: Verify entered Application Members value in Application Members details Page for Medical Assistance
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME           | RANDOM 30        |
      | MI                   | RANDOM 1         |
      | LAST NAME            | RANDOM 30        |
      | SUFFIX               | RANDOM 3         |
      | DOB                  | random past date |
      | GENDER               | Female           |
      | ARE YOU PREGNANT     | YES              |
      | NO. OF BABIES        | 2                |
      | EXPECTED DUE DATE    | RANDOM           |
      | SSN                  | Numeric 9        |
      | EXTERNAL CONSUMER ID | RANDOM           |
      | EXTERNAL ID TYPE     | random dropdown  |
    Then I clear the program list for "AM" Application Members
    And Select the "CHIP" Program(s) for application member
    And Select the "Medicaid" Program(s) for application member
    And Select the "Pregnancy Assistance" Program(s) for application member
    And click on save button for add application member
    And I click on application member name hyperlink to go to Application details page
    Then I verify the Application member Values in the application member details page
      | FIRST | MI | LAST | SUFFIX | DOB | AGE | GENDER | ARE YOU PREGNANT | NO OF BABIES | DUE DATE | SSN | EXID | EXTYPE | PROGRAMS |


  @CP-12703 @CP-12703-08 @crm-regression @ui-ats @sang
  Scenario: Validate null values in Application Member details page for Medical Assistance with Female Gender
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME       | RANDOM 30 |
      | LAST NAME        | RANDOM 30 |
      | GENDER           | Female    |
      | ARE YOU PREGNANT | YES       |
    And click on save button for add application member
    And I click on application member name hyperlink to go to Application details page
    Then I validate double dashes are used for null values in Application members detail page
      | MI | SUFFIX | DOB | AGE | NO OF BABIES | DUE DATE | SSN | EXID | EXTYPE |

  @CP-12703 @CP-12703-09 @crm-regression @ui-ats @sang
  Scenario: Validate null values in Application Member details page for Long Term Care with no minimum required value entered
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    And I click on application member name hyperlink to go to Application details page
    Then I validate double dashes are used for null values in Application members detail page
      | MI | SUFFIX | DOB | AGE | GENDER | SSN | EXID | EXTYPE |