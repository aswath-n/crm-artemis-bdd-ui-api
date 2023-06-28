Feature: Withdraw Application and related Permissions

  @CP-14492 @CP-14492-01 @crm-regression @ui-ats @munavvar
  Scenario: Create Application Record to withdraw
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I navigate to Application Tracking
    And I see application Status as "Entering Data" in the application information
    Then I verify I see Withdraw button displayed in Application Information panel
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    Then I  select "Already Receiving Services" from select reason dropdown
    When I click save button in the select reason
    Then I verify I see warning message "Are you sure you want to Withdraw this Application?"
    When I click Continue button inside the warning message
    And I see application Status as "WITHDRAWN" in the application information

  @CP-14492 @CP-14492-02 @crm-regression @ui-ats @munavvar
  Scenario:Select Reason for Withdraw
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I navigate to Application Tracking
    And I see application Status as "Entering Data" in the application information
    Then I verify I see Withdraw button displayed in Application Information panel
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    And I see these options as reasons for withdrawing:
    | Already Receiving Services | Not Interested in Services |

  @CP-14492 @CP-14492-03 @crm-regression @ui-ats @munavvar
  Scenario:  Save Withdraw Warning Message
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I navigate to Application Tracking
    And I see application Status as "Entering Data" in the application information
    Then I verify I see Withdraw button displayed in Application Information panel
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    And I  select "Already Receiving Services" from select reason dropdown
    When I click save button in the select reason
    Then I verify I see warning message "Are you sure you want to Withdraw this Application?"
    When I click Cancel button inside the warning message
    And I see application Status as "Entering Data" in the application information

  @CP-14492 @CP-14492-04 @crm-regression @ui-ats @munavvar
  Scenario: Cancel Withdraw
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I navigate to Application Tracking
    And I see application Status as "Entering Data" in the application information
    Then I verify I see Withdraw button displayed in Application Information panel
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    Then I  select "Already Receiving Services" from select reason dropdown
    When I click on the cancel button in the select reason
    Then the Application Withdraw Reason panel will be cleared and the Withdraw button will be Active

  @CP-14492 @CP-14492-05 @crm-regression @ui-ats @munavvar
  Scenario: Updated on an Updated By for Withdrawn Application
    Given I logged into CRM
    And I saved logged in user ID
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
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
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I Verify the following from the GET Application with ApplicationId
      | APP DATA UPDATEDBY | base                  |
      | APP DATA UPDATEDON | today's date and hour |


  @CP-14492 @CP-14492-06 @crm-regression @ui-ats @munavvar
  Scenario:  Navigate away from the screen
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I navigate to Application Tracking
    And I see application Status as "Entering Data" in the application information
    Then I verify I see Withdraw button displayed in Application Information panel
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    And I  select "Already Receiving Services" from select reason dropdown
    And I click on the back arrow in header row next to the icon Primary Individual name and application id
    And verify the "If you continue, all the captured information will be lost" warning message
    When I click Cancel button inside the warning message
    Then I see APPLICATION WITHDRAW REASON in Application Information panel

  @CP-14492 @CP-14492-07 @crm-regression @ui-ats @munavvar
  Scenario: Create Application Record & Submit to withdraw
    Given I logged into CRM
    And I saved logged in user ID
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    And I click on Submit button and continue button for application without Programs
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
  #  Then I navigate to Application Tracking
  #  Then I see I navigated to Member Matching page
 #   And I click on member matching page back arrow to navigate to create application page
    And I click on application tracking tab to navigate to Application Tracking page
    And I see application Status as "DETERMINING" in the application information
    Then I verify I see Withdraw button displayed in Application Information panel
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    Then I  select "Already Receiving Services" from select reason dropdown
    When I click save button in the select reason
    Then I verify I see warning message "Are you sure you want to Withdraw this Application?"
    When I click Continue button inside the warning message
    And I see application Status as "WITHDRAWN" in the application information
    And I get application id from the Create Application Page
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I Verify the following from the GET Application with ApplicationId
      | APP DATA UPDATEDBY | base                  |
      | APP DATA UPDATEDON | today's date and hour |

  @CP-16677  @CP-16677-02 @crm-regression @ui-ats @sang
    Scenario: Verify Withdraw status for Application and applicant consumers for Medical Assistance
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    Then I navigate to Application Tracking
  #  Then I click on continue button on warning message
    Then I verify I see Withdraw button displayed in Application Information panel
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    Then I  select "Already Receiving Services" from select reason dropdown
    When I click save button in the select reason
    Then I verify I see warning message "Are you sure you want to Withdraw this Application?"
    When I click Continue button inside the warning message
    And I see application Status as "WITHDRAWN" in the application information
    And I verify applicant consumers status is changed to "Withdrawn"

  @CP-16684 @CP-16684-01 @crm-regression @ui-ats @munavvar
  Scenario: Disabled Adding App Member/Auth Rep and Edit PI buttons for Withdrawn status from Entering Data
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I navigate to Application Tracking
    And I see application Status as "Entering Data" in the application information
    Then I verify I see Withdraw button displayed in Application Information panel
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    Then I  select "Already Receiving Services" from select reason dropdown
    When I click save button in the select reason
    Then I verify I see warning message "Are you sure you want to Withdraw this Application?"
    When I click Continue button inside the warning message
    And I see application Status as "WITHDRAWN" in the application information
    And I click application id under Application Tracking tab in the Application Information panel
    And I see Application tab is selected
    Then I verify Edit button is displayed and disabled in create application page in Application tab
    And I verify Add Application member button is displayed  and disabled in create application page in Application tab
    And I verify Add Authorized Representative button is displayed  and disabled in create application page in Application tab

  @CP-16684 @CP-16684-02 @crm-regression @ui-ats @munavvar
  Scenario: Withdraw button disabled for Withdrawn Applications
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I navigate to Application Tracking
    And I see application Status as "Entering Data" in the application information
    Then I verify I see Withdraw button displayed in Application Information panel
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    Then I  select "Already Receiving Services" from select reason dropdown
    When I click save button in the select reason
    Then I verify I see warning message "Are you sure you want to Withdraw this Application?"
    When I click Continue button inside the warning message
    And I see application Status as "WITHDRAWN" in the application information
    Then I verify I see Withdraw button displayed and disabled in Application Information panel

  @CP-16684 @CP-14932 @CP-14932-04 @CP-14236 @CP-14236-01 @CP-16684-03 @crm-regression @ui-ats @munavvar
  Scenario: Disabled Adding App Member/Auth Rep and Edit PI buttons for Withdrawn status from Determining, and not able to Withdraw Application one more time
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    And I click on Submit button and continue button for application without Programs
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
  #  Then I see I navigated to Member Matching page
  #  And I click on member matching page back arrow to navigate to create application page
  #  And I click on application tracking tab to navigate to Application Tracking page
  #  Then I navigate to Application Tracking
    And I see application Status as "DETERMINING" in the application information
    Then I verify I see Withdraw button displayed in Application Information panel
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    Then I  select "Already Receiving Services" from select reason dropdown
    When I click save button in the select reason
    Then I verify I see warning message "Are you sure you want to Withdraw this Application?"
    When I click Continue button inside the warning message
    And I see application Status as "WITHDRAWN" in the application information
    Then I verify I see Withdraw button displayed and disabled in Application Information panel
    And I click application id under Application Tracking tab in the Application Information panel
    And I see Application tab is selected
    Then I verify Edit button is displayed and disabled in create application page in Application tab
    And I verify Add Application member button is displayed  and disabled in create application page in Application tab
    And I verify Add Authorized Representative button is displayed  and disabled in create application page in Application tab

  @CP-16684 @CP-14236 @CP-14236-02 @CP-16684-04 @crm-regression @ui-ats @munavvar
  Scenario: Disabled Editing, Removing App Member/Auth Rep buttons for Withdrawn status from Entering Data
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    When I click Add Authorized Representative button
    And I enter information to add "ACTIVE" Authorized Representative
    And I click save button to save Authorized Representative
    And I verify the save successfully updated message
    Then I navigate to Application Tracking
   # And I click on continue button on warning message
    And I see application Status as "Entering Data" in the application information
    Then I verify I see Withdraw button displayed in Application Information panel
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    Then I  select "Already Receiving Services" from select reason dropdown
    When I click save button in the select reason
    Then I verify I see warning message "Are you sure you want to Withdraw this Application?"
    When I click Continue button inside the warning message
    And I see application Status as "WITHDRAWN" in the application information
    And I click application id under Application Tracking tab in the Application Information panel
    And I see Application tab is selected
    Then I verify Edit button is displayed and disabled in create application page in Application tab
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

  @CP-14893 @CP-14932 @CP-14932-01 @CP-14893-01 @crm-regression @ui-ats @munavvar
  Scenario Outline: Role based withdraw from Entering Data stage
    Given I logged into CRM with "<role>" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I navigate to Application Tracking
    And I see application Status as "Entering Data" in the application information
    Then I verify I see Withdraw button displayed in Application Information panel
    And with this role I was able to withdraw application "<isAbleToWithdraw>"
    Examples:
      | role              | isAbleToWithdraw |
      | Service Account 1 | true             |
      | Service Account 5 | true             |
      | Service Account 7 | true             |
      | Service Tester 1  | true             |
      | Service Account 2 | false            |

  @CP-14893 @CP-14932 @CP-14932-02 @CP-14893-02 @crm-regression @ui-ats @munavvar
  Scenario Outline: Role based withdraw from Target Unidentified stage
    Given I logged into CRM with "<role>" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | SSN              | 989898989        |
    Then I click on Save button on Create Application Page
    And I click on Submit button and continue button for application without Programs
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    And I click on member matching page back arrow to navigate to create application page
    Then I navigate to Application Tracking
    And I see application Status as "TARGETS UNIDENTIFIED" in the application information
    Then I verify I see Withdraw button displayed in Application Information panel
    And with this role I was able to withdraw application "<isAbleToWithdraw>"
    Examples:
      | role              | isAbleToWithdraw |
      | Service Account 1 | true             |
      | Service Account 5 | true             |
      | Service Account 7 | true             |
      | Service Tester 1  | true             |

  @CP-14893 @CP-14932 @CP-14932-03 @CP-14893-03 @crm-regression @ui-ats @munavvar
  Scenario Outline: NON CSR Role based withdraw from Entering Data stage
    Given I logged into CRM with "<role>" and select a project "BLCRM"
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I navigate to Application Tracking
    And I see application Status as "Entering Data" in the application information
    Then I verify I see Withdraw button displayed and disabled in Application Information panel
    Examples:
      | role              | isAbleToWithdraw |
      | Service Account 2 | false            |

