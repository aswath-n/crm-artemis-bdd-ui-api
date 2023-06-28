Feature: ATS Missing Information Tab 2 Continued

  @CP-25663 @CP-25663-01 @crm-regression @ui-ats @vinuta
  Scenario: Verify Missing Information Indicator is not displayed when no MI records are present
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    And I add Application Member for the Created Application with mandatory fields
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify missing information flag is not displayed

  @CP-25663 @CP-25663-02 @crm-regression @ui-ats @vinuta
  Scenario: Display MI Icon on Application Tracking page & on MI tab for Application level information
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Residence County |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify the missing information icon displayed
      | APPLICATION |
    Then I verify Missing Information tab is highlighted with MI icon

  @CP-25663 @CP-25663-03 @crm-regression @ui-ats @vinuta
  Scenario:Display MI Icon on Application Tracking page for Applicant Consumer information - Primary Individual
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Citizenship Verification |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    Then I verify Missing Information tab is highlighted with MI icon
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify the missing information icon displayed
      | PRIMARY INDIVIDUAL |

  @CP-25663 @CP-25663-04 @crm-regression @ui-ats @vinuta
  Scenario:Display MI Icon on Application Tracking page for Applicant Consumer information - Application Member
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    And I add Application Member for the Created Application with mandatory fields
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Gender |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | APP MEM ONE |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    Then I verify Missing Information tab is highlighted with MI icon
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify the missing information icon displayed
      | APPLICATION MEMBER |

  @CP-25663 @CP-25663-05 @crm-regression @ui-ats @vinuta
  Scenario:Remove MI Icon on Application Tracking page for Applicant Consumer information - Primary Individual when all MI are Satisfied/Disregarded
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | SSN |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "SATISFY" from the User Icon menu for Missing Info Save
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | DOB |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "DISREGARD" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    Then I verify missing information flag is not displayed

  @CP-25663 @CP-25663-06 @crm-regression @ui-ats @vinuta
  Scenario:Remove MI Icon on Application Tracking page for Applicant Consumer information - Application Member when all MI are Satisfied/Disregarded
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    And I add Application Member for the Created Application with mandatory fields
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | First Name |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | APP MEM ONE |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "DISREGARD" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Last Name |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | APP MEM ONE |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "SATISFY" from the User Icon menu for Missing Info Save
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    Then I verify missing information flag is not displayed

  @CP-25663 @CP-25663-07 @crm-regression @ui-ats @vinuta
  Scenario: Remove MI Icon on Application Tracking page for Application level information when all MI are Satisfied/DISREGARDED
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Application Signed |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "SATISFY" from the User Icon menu for Missing Info Save
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Residence County |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "DISREGARD" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    Then I verify missing information flag is not displayed

  @CP-30892 @CP-30892-01 @crm-regression @ui-ats @sang
  Scenario: Display and not display Medical Assistance MI due date when Missing Info is in status of Pending and satisfied
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    Then I verify Missing Info due date is "NO DISPLAY"
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Application Signed |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    Then I verify Missing Info due date is "MEDICAL ASSISTANCE"
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "SATISFY" from the User Icon menu for Missing Info Save
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    Then I verify Missing Info due date is "NO DISPLAY"

  @CP-30892 @CP-30892-02 @crm-regression @ui-ats @sang
  Scenario: Display and not display Long Term MI due date when Missing Info is in status of Pending and Disregard
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    Then I verify Missing Info due date is "NO DISPLAY"
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Application Signed |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    Then I verify Missing Info due date is "LONG TERM CARE"
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "DISREGARD" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    Then I verify Missing Info due date is "NO DISPLAY"

  @CP-25057 @CP-25057-01 @crm-regression @ui-ats @sang
  Scenario: Validate Add Missing Info button disabled and no option to Satisfy and Disregard for Withdrawn Application
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |ssn|
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |RANDOM SSN|
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    Then I verify I see Withdraw button displayed in Application Information panel
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    Then I  select "Not Interested in Services" from select reason dropdown
    When I click save button in the select reason
    Then I verify I see warning message "Are you sure you want to Withdraw this Application?"
    When I click Continue button inside the warning message
    And I see application Status as "WITHDRAWN" in the application information
    When I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    Then I verify Add missing info item button is disabled on Missing info page
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I verify the following option are removed from the User Icon menu for Missing Info Save
      | SATISFY | DISREGARD |

  @CP-25057 @CP-25057-02 @crm-regression @ui-ats @sang
    #Refactored to avoid status from auto-clearing
  Scenario: Validate Add Missing Info button disabled and no option to Satisfy and Disregard for Expired Application
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | PRIOR 45                | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Mail |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Entering Data" on the response
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | applicationId      | applicationDeadlineDate |
      | Medical Assistance | PRIOR 45                | false         | created previously | PRIOR 1                 |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    When I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    Then I verify Add missing info item button is disabled on Missing info page
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I verify the following option are removed from the User Icon menu for Missing Info Save
      | SATISFY | DISREGARD |

  @CP-25057 @CP-25057-03 @crm-regression @ui-ats @sang
  Scenario: Validate Add Missing Info button disabled and no option to Satisfy and Disregard for Closed Application
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | PRIOR 45                | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Mail |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | applicationId      | applicationDeadlineDate |
      | Medical Assistance | PRIOR 45                | true         | created previously | PRIOR 11                |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    When I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    Then I verify Add missing info item button is disabled on Missing info page
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I verify the following option are removed from the User Icon menu for Missing Info Save
      | SATISFY | DISREGARD |

  @CP-25056 @CP-25056-01 @crm-regression @ui-ats @chandrakumar
  Scenario Outline: Verify Add Missing Information Button is displayed and satisfy and disregard options are displayed in the User Icon menu
    Given I logged into CRM with "<user>" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    Then I verify ADD MISSING INFO Button is displayed
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Application Signed |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    Then I verify the following values for "USER ICON" dropdown values for Missing Info add
      | EDIT COMMENT | SATISFY | DISREGARD |
    Examples:
      | user              |
      | Service Account 7 |
      | Service Tester 1  |
      | Service Tester 2  |

  @CP-25056 @CP-25056-02 @crm-regression @ui-ats @chandrakumar
  Scenario Outline: Verify Missing Information Button is displayed and satisfy and disregard options are displayed in the User Icon menu.
    Given I logged into CRM with "<user>" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    Then I verify ADD MISSING INFO Button is displayed
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Application Signed |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    Then I verify the following values for "USER ICON" dropdown values for Missing Info add
      | EDIT COMMENT | SATISFY | DISREGARD |
    Examples:
      | user              |
      | Service Account 7 |
      | Service Tester 1  |
      | Service Tester 2  |

  @CP-25056 @CP-25056-03 @crm-regression @ui-ats @chandrakumar
  Scenario: Verify Missing Information Button is not displayed and satisfy and disregard options are removed from the User Icon menu
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    When I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    Then I verify ADD MISSING INFO Button is not displayed
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I verify the following option are removed from the User Icon menu for Missing Info Save
      | SATISFY | DISREGARD |

  @CP-25056 @CP-25056-04 @crm-regression @ui-ats @chandrakumar
  Scenario: Verify Missing Information Indicator is not displayed and satisfy and disregard options are removed from the User Icon menu.
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd | interactiveInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    When I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    Then I verify ADD MISSING INFO Button is not displayed
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I verify the following option are removed from the User Icon menu for Missing Info Save
      | SATISFY | DISREGARD |