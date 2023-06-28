Feature: Validate Application Notes on Create/View/Edit

  @CP-36153 @CP-36383 @CP-36153-01 @CP-36153-03 @CP-36383-01 @ozgen @crm-regression @ui-ats
  Scenario Outline: Validation of Notes Component on Application and Application Tracking Tab while Viewing and Editing application
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I saved logged in user ID
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME           | Random 5        |
      | LAST NAME            | Random 5        |
      | DOB                  | random past date |
    And click on save button for add application member
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME        | Random 5  |
      | LAST NAME         | Random 5  |
      | ORGANIZATION NAME | RANDOM 5  |
    Then I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    Then I verify notes component for application appeared on the page
    And I choose relates to value as "<relatesToType>"
    Then I provide notes text with 50 characters
    Then I click on "Cancel" button for Application Notes
    Then I verify notes component for application appeared on the page
    And I choose relates to value as "<relatesToType>"
    Then I provide notes text with 1000 characters
    Then I click on "Save" button for Application Notes
    And I verify that Notes Success Message is displayed
    Then I verify that Note Record is created with expected details for "<consType>"
    Then I click on application tracking tab to navigate to Application Tracking page
    Then I verify notes component for application appeared on the page
    And I choose relates to value as "<relatesToType>"
    Then I provide notes text with 50 characters
    Then I click on "Save" button for Application Notes
    And I verify that Notes Success Message is displayed
    Then I verify that Note Record is created with expected details for "<consType>"
    Examples:
      | relatesToType             | consType |
      | Application               | App      |
      | Primary Individual        | PI       |
      | Application Member        | AM       |
      | Authorized Representative | AR       |

  @CP-36153 @CP-36383 @CP-36153-01 @CP-36153-05 @CP-36383-05 @ozgen @crm-regression @ui-ats
  Scenario: Validation of Notes Component while Editing application and can not edit existing note on Missing info tab, Application Tracking Tab
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | English         | Russian        |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Female     |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Pregnancy Assistance |
    And I POST ATS save application api
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated Save Notes API for ats
    And I run Save Notes API for "application" level
    Then I initiated POST Notes API to retrieve note details
    And I run POST Notes API for "application" level
    And Wait for 2 seconds
    Then I verify Save Notes response includes "application" level details
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I navigate to application tab page
    When I click on the Edit button for the Primary Individual Details
    Then I verify that Note Record is created with expected details for "App API"
    And I verify existing notes are not editable
    And I click on application member name hyperlink to go to Application details page
    Then I click on continue button on warning message
    And I verify existing notes are not editable
    Then I click back button
    When I click on missing information tab to navigate to Missing Information page
    And I verify existing notes are not editable
    And I click on application tracking tab to navigate to Application Tracking page
    And I verify existing notes are not editable

  @CP-36153 @CP-36383 @CP-36383-02 @CP-36153-02 @ozgen @crm-regression @ui-ats
  Scenario: Validate No Application Notes component available for SA2
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    Then I verify that no notes component is available
    When I click on missing information tab to navigate to Missing Information page
    Then I verify that no notes component is available
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify that no notes component is available

  @CP-36153 @CP-36383 @CP-36383-04 @CP-36153-04 @ozgen @crm-regression @ui-ats
  Scenario Outline: Validate No Application Notes component in Create Application Page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to Create "<applicationType>" application page
    Then I verify that no notes component is available
    Examples:
      | applicationType    |
      | LONG TERM CARE     |
      | MEDICAL ASSISTANCE |

  @CP-36153 @CP-36153-06 @ozgen @crm-regression @ui-ats
  Scenario: Verification of sort order where there is more than one application notes
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I saved logged in user ID
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME           | Random 5        |
      | LAST NAME            | Random 5        |
      | DOB                  | random past date |
    And click on save button for add application member
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME        | Random 5  |
      | LAST NAME         | Random 5  |
      | ORGANIZATION NAME | RANDOM 5  |
    Then I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    Then I verify notes component for application appeared on the page
    And I choose relates to value as "Application"
    Then I provide notes text with 50 characters
    Then I click on "Save" button for Application Notes
    And I verify that Notes Success Message is displayed
    And I choose relates to value as "Primary Individual"
    Then I provide notes text with 60 characters
    Then I click on "Save" button for Application Notes
    And I verify that Notes Success Message is displayed
    And I choose relates to value as "Application Member"
    Then I provide notes text with 80 characters
    Then I click on "Save" button for Application Notes
    And I verify that Notes Success Message is displayed
    And I choose relates to value as "Authorized Representative"
    Then I provide notes text with 100 characters
    Then I click on "Save" button for Application Notes
    And I verify that Notes Success Message is displayed
    Then I verify sorted order of application notes

  @CP-36383 @CP-36383-01.1 @CP-36383-03 @ozgen @crm-regression @ui-ats
  Scenario Outline: Validation of Notes Component on Missing Info and Application Tracking Tab while Viewing and Editing application
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I saved logged in user ID
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME           | Random 5        |
      | LAST NAME            | Random 5        |
      | DOB                  | random past date |
    And click on save button for add application member
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME        | Random 5  |
      | LAST NAME         | Random 5  |
      | ORGANIZATION NAME | RANDOM 5  |
    Then I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    When I click on missing information tab to navigate to Missing Information page
    Then I verify notes component for application appeared on the page
    And I choose relates to value as "<relatesToType>"
    Then I provide notes text with 50 characters
    Then I click on "Cancel" button for Application Notes
    Then I verify notes component for application appeared on the page
    And I choose relates to value as "<relatesToType>"
    Then I provide notes text with 1000 characters
    Then I click on "Save" button for Application Notes
    And I verify that Notes Success Message is displayed
    Then I verify that Note Record is created with expected details for "<consType>"
    When I click on application tracking tab to navigate to Application Tracking page
    And I choose relates to value as "<relatesToType>"
    Then I provide notes text with 40 characters
    Then I click on "Save" button for Application Notes
    And I verify that Notes Success Message is displayed
    Examples:
      | relatesToType             | consType |
      | Application               | App      |
      | Primary Individual        | PI       |
      | Application Member        | AM       |
      | Authorized Representative | AR       |

  @CP-36383 @CP-36383-01.2 @CP-36383-01.3 @CP-36383-03 @ozgen @crm-regression @ui-ats
  Scenario Outline: Validation of Notes Component on Add/Edit Application Member and Authorized Rep Page while Viewing and Editing application
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I saved logged in user ID
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I verify notes component for application appeared on the page
    Then I fill in the following application member values
      | FIRST NAME  | Random 5         |
      | LAST NAME   | Random 5         |
      | DOB         | random past date |
    And click on save button for add application member
    And I click Add Authorized Representative button
    And I verify notes component for application appeared on the page
    Then I fill in the following Authorized Representative values
      | FIRST NAME        | Random 5  |
      | LAST NAME         | Random 5  |
      | ORGANIZATION NAME | RANDOM 5  |
    Then I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    And I click on application member name hyperlink to go to Application details page
    And I choose relates to value as "<relatesToType>"
    Then I provide notes text with 100 characters
    Then I click on "Save" button for Application Notes
    And I verify that Notes Success Message is displayed
    Then I verify that Note Record is created with expected details for "<consType>"
    Then I click back button
    And I click on Added Authorized Representative
    And I choose relates to value as "<relatesToType>"
    Then I provide notes text with 100 characters
    Then I click on "Save" button for Application Notes
    And I verify that Notes Success Message is displayed
    Then I verify that Note Record is created with expected details for "<consType>"
    Examples:
      | relatesToType             | consType |
      | Application               | App      |
      | Primary Individual        | PI       |
      | Application Member        | AM       |
      | Authorized Representative | AR       |

  @CP-36383 @CP-36383-06 @ozgen @crm-regression @ui-ats
  Scenario: Drafting notes while navigating different Application Tabs
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I saved logged in user ID
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME           | Random 5        |
      | LAST NAME            | Random 5        |
      | DOB                  | random past date |
    And click on save button for add application member
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME        | Random 5  |
      | LAST NAME         | Random 5  |
      | ORGANIZATION NAME | RANDOM 5  |
    Then I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    Then I verify notes component for application appeared on the page
    And I choose relates to value as "Application"
    Then I provide notes text with 25 characters
    And I click on application member name hyperlink to go to Application details page
    And I verify previously provided notes text is drafted
    Then I click back button
    And I click on Added Authorized Representative
    And I verify previously provided notes text is drafted
    Then I click back button
    When I click on missing information tab to navigate to Missing Information page
    And I verify previously provided notes text is drafted
    When I click on application tracking tab to navigate to Application Tracking page
    And I verify previously provided notes text is drafted
    Then I click on "Save" button for Application Notes
    And I verify that Notes Success Message is displayed
    Then I verify that Note Record is created with expected details for "App"
