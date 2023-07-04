Feature: ATS Second Tenant BLATS2 Member Matching Page: New and Duplicate buttons Functionality

  @CP-14369 @CP-14369-01 @CP-20725 @CP-20725-01 @crm-regression @ui-ats-blats2 @sang
  Scenario: Verify NEW button is enabled for a Medical Assistance inbound application in Member Matching page
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "Pregnancy Assistance" as program type
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I verify the New button is enabled for inbound application in Member Matching page
    And I verify Duplicate button is disabled for inbound application in Member Matching page

  @CP-14369 @CP-14369-02 @CP-20725 @CP-20725-02 @crm-regression @ui-ats-blats2 @sang
  Scenario: Verify NEW button is enabled for a Long Term Care inbound application in Member Matching page
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | DOB    | random past date |
      | GENDER | Female           |
      | SSN    | Numeric 9        |
    Then I choose "HCBS" as program type
    Then  I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I verify the New button is enabled for inbound application in Member Matching page
    And I verify Duplicate button is disabled for inbound application in Member Matching page

  @CP-14369 @CP-14369-03 @crm-regression @ui-ats-blats2 @sang
  Scenario: Navigated to Application Tracking tab upon clicking on the New button for Long Term Care application
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | DOB    | random past date |
      | GENDER | Female           |
      | SSN    | Numeric 9        |
    And I choose "HCBS" as program type
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    And I see columns displayed in this order in the Application Information panel
      | APPLICATION ID | EXTERNAL APP ID | PRIORITY | DEADLINE DATE | SIGNATURE DATE | APPLICATION TYPE | CYCLE | RECEIVED DATE | CHANNEL | CREATE DATE | LAST UPDATED DATE |

  @CP-14369 @CP-14369-04 @crm-regression @ui-ats-blats2 @sang
  Scenario: Navigated to Application Tracking tab upon clicking on the New button for Medical Assistance application
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "Pregnancy Assistance" as program type
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    And I see columns displayed in this order in the Application Information panel
      | APPLICATION ID | EXTERNAL APP ID | PRIORITY | DEADLINE DATE | SIGNATURE DATE | APPLICATION TYPE | CYCLE | RECEIVED DATE | CHANNEL | CREATE DATE | LAST UPDATED DATE |

  @CP-14369 @CP-14369-05 @crm-regression @ui-ats-blats2 @sang
  Scenario: Verify Updated On and Updated By values when Member matching page NEW button is clicked on for Medical Assistance
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    And I get application id from the Create Application Page
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I Verify the following from the GET Application with ApplicationId
      | PI UPDATEDBY | base                  |
      | PI UPDATEDON | today's date and hour |

  @CP-14369 @CP-14369-06 @crm-regression @ui-ats-blats2 @sang
  Scenario: Verify Updated On and Updated By values when Member matching page NEW button is clicked on for Long Term Care
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate |
      | Long Term Care  | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I POST ATS save application api
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    And I get application id from the Create Application Page
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I Verify the following from the GET Application with ApplicationId
      | PI UPDATEDBY | base                  |
      | PI UPDATEDON | today's date and hour |

  @CP-18263 @CP-18263-01 @crm-regression @ui-ats-blats2 @sang
  Scenario: Set Application status to DETERMINING in Application tab and Tracking tab for Long Term Care
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | DOB    | random past date |
      | GENDER | Female           |
      | SSN    | Numeric 9        |
    And I choose "HCBS" as program type
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    And I see application Status as "DETERMINING" in the application information
    And I navigate to application tab page
    Then I verify "DETERMINING" application status in the top right corner in the Application details tab

  @CP-18263 @CP-18263-02 @crm-regression @ui-ats-blats2 @sang
  Scenario: Set Application status to DETERMINING in Application tab and Tracking tab for MEDICAL ASSISTANCE
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "Pregnancy Assistance" as program type
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    And I see application Status as "DETERMINING" in the application information
    And I navigate to application tab page
    Then I verify "DETERMINING" application status in the top right corner in the Application details tab

  @CP-18263 @CP-18263-03 @crm-regression @ui-ats-blats2 @sang
  Scenario: Submit button is disabled for Long Term Care when clicking on NEW button in member matching page to set application to DETERMINING
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | DOB    | random past date |
      | GENDER | Female           |
      | SSN    | Numeric 9        |
    And I choose "HCBS" as program type
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    And I navigate to application tab page
    And I see Submit button is displayed and disabled in create application page in Application tab

  @CP-18263 @CP-18263-04 @crm-regression @ui-ats-blats2 @sang
  Scenario: Submit button is disabled for MEDICAL ASSISTANCE when clicking on NEW button in member matching page to set application to DETERMINING
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "Pregnancy Assistance" as program type
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    And I navigate to application tab page
    And I see Submit button is displayed and disabled in create application page in Application tab

  @CP-18263 @CP-18263-05 @crm-regression @ui-ats-blats2 @sang
  Scenario: Set MEDICAL ASSISTANCE Application Consumers with programs to determining Applicant Status
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "Medicaid" as program type
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | DOB    | random past date |
      | GENDER | Male             |
      | SSN    | Numeric 9        |
    Then I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30        |
      | LAST NAME  | RANDOM 30        |
      | DOB        | random past date |
      | GENDER     | Male             |
      | SSN        | Numeric 9        |
    And Select the "CHIP" Program(s) for application member
    And click on save button for add application member
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    Then I verify Applicant status "Determining" for PI and AM on application tracking page

  @CP-18263 @CP-18263-06 @crm-regression @ui-ats-blats2 @sang
  Scenario: Set LONG TERM CARE Application Consumers with programs to determining Applicant Status
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "HCBS" as program type
    Then I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | DOB    | random past date |
      | GENDER | Male             |
      | SSN    | Numeric 9        |
    Then I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30        |
      | LAST NAME  | RANDOM 30        |
      | DOB        | random past date |
      | GENDER     | Male             |
      | SSN        | Numeric 9        |
    And Select the "HCBS" Program(s) for application member
    And click on save button for add application member
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    Then I verify Applicant status "Determining" for PI and AM on application tracking page

  @CP-18263 @CP-18263-07 @crm-regression @ui-ats-blats2 @sang
  Scenario: MEDICAL ASSISTANCE Application Consumers without programs remain in Received Applicant Status
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    And I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    Then I verify Applicant status "Received" for PI and AM on application tracking page

  @CP-18263 @CP-18263-08 @crm-regression @ui-ats-blats2 @sang
  Scenario: LONG TERM CARE Application Consumers without programs remain in Received Applicant Status
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    And I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    Then I verify Applicant status "Received" for PI and AM on application tracking page

  @CP-20725 @CP-20725-03 @CP-20725-04 @crm-regression @ui-ats-blats2 @vinuta
  Scenario: Activate DUPLICATE Action button & Restrict Selection to One Potential Match Application
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    Then I verify I am on the Application Tracking Page
    And I navigate to Create "LONG TERM CARE" application page
    And I create duplicate application to land on member matching page
    And I click on Save button on Create Application Page
    Then I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    Then I verify I am on the Application Tracking Page
    And I navigate to Create "LONG TERM CARE" application page
    And I create duplicate application to land on member matching page
    And I click on Save button on Create Application Page
    Then  I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    And I click on the "0" select box for matching application in Member Matching page
    Then I verify Duplicate button is enabled for inbound application in Member Matching page
    When I click on the "1" select box for matching application in Member Matching page
    Then I verify number of Potential Application selection is "1"

  @CP-20725 @CP-20725-05 @CP-20725-06 @crm-regression @ui-ats-blats2 @vinuta
  Scenario: Select DUPLICATE Application, verify warning & verify clicking on Cancel button
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    Then  I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    Then I verify I am on the Application Tracking Page
    And I navigate to Create "LONG TERM CARE" application page
    And I create duplicate application to land on member matching page
    And I click on Save button on Create Application Page
    Then I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    And I click on the "0" select box for matching application in Member Matching page
    When I click on the Duplicate button on Member Matching page
    Then I verify warning message displayed in Member Matching Page when application is marked Duplicate
    When I click on Cancel button on Duplicate Application Warning Message
    Then I see I navigated to Member Matching page
    And I verify number of Potential Application selection is "0"
    And I verify Duplicate button is disabled for inbound application in Member Matching page

  @CP-20725 @CP-20725-07 @CP-20725-08 @crm-regression @ui-ats-blats2 @vinuta
  Scenario: Create DUPLICATE Application, verify application, applicant status & cannot Submit application
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "Pregnancy Assistance" as program type
    Then I click on Save button on Create Application Page
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    Then I verify I am on the Application Tracking Page
    And I navigate to Create "LONG TERM CARE" application page
    And I create duplicate application to land on member matching page
    And I click on Save button on Create Application Page
    Then I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    And I click on the "0" select box for matching application in Member Matching page
    When I click on the Duplicate button on Member Matching page & Continue
    And I navigate to application tab page
    Then I see Submit button is displayed and disabled in create application page in Application tab
    And I verify application is in read-only mode
    And I verify "Edit Primary Individual" button is disabled and not clickable
    And I verify "Add Application Member" button is disabled and not clickable
    And I verify "Add Authorized Representative" button is disabled and not clickable

  @CP-20725 @CP-20725-09 @CP-20736 @CP-20736-01 @crm-regression @ui-ats-blats2 @vinuta
  Scenario: Verify Updated On and Updated by, when DUPLICATE Application is created
    Given I logged into CRM
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "Pregnancy Assistance" as program type
    Then I click on Save button on Create Application Page
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    Then I verify I am on the Application Tracking Page
    And I navigate to Create "LONG TERM CARE" application page
    And I create duplicate application to land on member matching page
    And I click on Save button on Create Application Page
    Then I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    And I click on the "0" select box for matching application in Member Matching page
    When I click on the Duplicate button on Member Matching page & Continue
    Then I see application Status as "DUPLICATE" in the create application page
    And I see applicant status for all applicants as "Duplicate" in the create application page
    When I navigate to application tab page
    Then I verify "DUPLICATE" application status in the top right corner in the Application details tab
    And I get application id from the Create Application Page
    And I saved logged in user ID
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I Verify the following from the GET Application with ApplicationId
      | PI UPDATEDBY | base                  |
      | PI UPDATEDON | today's date and hour |

  @CP-20737 @CP-20737-01 @crm-regression @ui-ats-blats2 @vinuta
  Scenario: Verify applicant status is set to Duplicate for Medical Assistance application
    Given I logged into CRM
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "Pregnancy Assistance" as program type
    Then I click on Save button on Create Application Page
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    Then I verify I am on the Application Tracking Page
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I create duplicate application to land on member matching page
    And I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30        |
      | MI         | RANDOM 1         |
      | LAST NAME  | RANDOM 30        |
      | SUFFIX     | RANDOM 3         |
      | DOB        | random past date |
      | SSN        | Numeric 9        |
      | GENDER     | Male             |
    And Select the "Medicaid" Program(s) for application member
    And click on save button for add application member
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    And I click on the "0" select box for matching application in Member Matching page
    When I click on the Duplicate button on Member Matching page & Continue
    Then I see application Status as "DUPLICATE" in the create application page
    Then I verify Applicant status "Duplicate" for PI and AM on application tracking page

  @CP-20737 @CP-20737-02 @crm-regression @ui-ats-blats2 @vinuta
  Scenario: Verify applicant status is set to Duplicate for Long Term Care application
    Given I logged into CRM
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "HCBS" as program type
    Then I click on Save button on Create Application Page
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    Then I verify I am on the Application Tracking Page
    And I navigate to Create "LONG TERM CARE" application page
    And I create duplicate application to land on member matching page
    And I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30        |
      | MI         | RANDOM 1         |
      | LAST NAME  | RANDOM 30        |
      | SUFFIX     | RANDOM 3         |
      | DOB        | random past date |
      | SSN        | Numeric 9        |
      | GENDER     | Male             |
    And Select the "HCBS" Program(s) for application member
    And click on save button for add application member
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    And I click on the "0" select box for matching application in Member Matching page
    When I click on the Duplicate button on Member Matching page & Continue
    Then I see application Status as "DUPLICATE" in the create application page
    Then I verify Applicant status "Duplicate" for PI and AM on application tracking page

  @CP-22277 @CP-22277-01 @crm-regression @ui-ats-blats2 @vinuta
  Scenario: Verify Member Matching Back Arrow for Medical Assistance, New Application
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "Pregnancy Assistance" as program type
    And I fill in the Primary Individual Contact Information with the following values
      | RESIDENCE ADDRESS LINE 1 | random 41  |
      | RESIDENCE ADDRESS LINE 2 | random 41  |
      | RESIDENCE CITY           | Metropolis |
      | RESIDENCE STATE          | Illinois   |
      | RESIDENCE ZIP CODE       | 62960      |
      | RESIDENCE COUNTY         | Massac     |
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    And I click on member matching page back arrow to navigate to create application page
    Then I verify "MEDICAL ASSISTANCE" Application Page header is displayed
    Then I click on Submit button only in Create Application Page
    When I click on the New button in the Application Member Matching Page
    Then I click on the back arrow in header row next to the icon Primary Individual name and application id
    And I should return to dashboard page

  @CP-22277 @CP-22277-02 @crm-regression @ui-ats-blats2 @vinuta
    #Failing due to single tenant configured for "matchingType": "caseConsumers"
  Scenario: Verify Member Matching Back Arrow for Long Term Care, Duplicate Application
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    Then I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    Then I verify I am on the Application Tracking Page
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I create duplicate application to land on member matching page
    Then I click on Save button on Create Application Page
    Then I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    And I click on the "0" select box for matching application in Member Matching page
    And I click on member matching page back arrow to navigate to create application page
    And I verify Back Arrow warning warning message displayed on Member Matching Page
    And I click "CANCEL" button inside the warning message on Member Matching Page
    And I see I navigated to Member Matching page
    When I click on the Duplicate button on Member Matching page & Continue
    And I get application id from the Create Application Page
    Then I see application Status as "DUPLICATE" in the create application page
    When I click on the back arrow in header row next to the icon Primary Individual name and application id
    Then I should return to dashboard page

  @CP-22277 @CP-22277-04 @crm-regression @ui-ats-blats2 @vinuta
  Scenario: Verify Member Matching Back Arrow when application retrieved from search page
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
    When I click on the back arrow in header row next to the icon Primary Individual name and application id
    Then the UI will display the SEARCH APPLICATION screen.