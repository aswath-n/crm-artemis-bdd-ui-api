Feature: ATS Application Tracking Page 2

  @CP-16699 @CP-16699-01 @CP-22998-07 @crm-regression @ui-ats @sang
  Scenario: Verify AUTHORIZED REPRESENTATIVES panel labels in Application tracking Tab
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    Then I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify the following labels in AUTHORIZED REPRESENTATIVES Members Info Panel
      | FULL NAME | AUTH TYPE | ACCESS TYPE | AUTHORIZED | STATUS |

  @CP-16699 @CP-16699-02 @crm-regression @ui-ats @sang
  Scenario: Verify AUTHORIZED REPRESENTATIVES panel Full name contains sixtyone letters for First Mi and Last name values in Application tracking Tab
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | RANDOM 30 |
      | MI         | RANDOM 1  |
      | LAST NAME  | RANDOM 30 |
    Then I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify the Full Name for auth rep in AUTHORIZED REPRESENTATIVES panel contains sixtyone letters for First Mi and Last Name

  @CP-16699 @CP-16699-03 @crm-regression @ui-ats @sang
  Scenario: Verify all Auth Type is shown in AUTHORIZED REPRESENTATIVES panel for Application Tracking tab
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | AUTH TYPE  | Assister |
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    Then I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | AUTH TYPE  | Broker   |
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    Then I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | AUTH TYPE  | Nursing Facility Rep |
      | FIRST NAME | Random 5             |
      | LAST NAME  | Random 5             |
    Then I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | AUTH TYPE  | Other    |
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    Then I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | AUTH TYPE  | Power of Attorney |
      | FIRST NAME | Random 5          |
      | LAST NAME  | Random 5          |
    Then I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify Auth Type contains the following in AUTHORIZED REPRESENTATIVES panel for Application Tracking tab
      | Power of Attorney | Other | Nursing Facility Rep | Broker | Assister |

  @CP-16699 @CP-16699-04 @crm-regression @ui-ats @sang @CP-34278
  Scenario: Verify all Access Type is shown in AUTHORIZED REPRESENTATIVES panel for Application Tracking tab
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME  | Random 5       |
      | LAST NAME   | Random 5       |
      | ACCESS TYPE | Partial Access |
    Then I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME  | Random 5    |
      | LAST NAME   | Random 5    |
      | ACCESS TYPE | Full Access |
    Then I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify the the folowing Access typ is in AUTHORIZED REPRESENTATIVES panel for Application Tracking tab
      | Full Access | Partial Access |

  @CP-16699 @CP-16699-04 @crm-regression @ui-ats @sang
  Scenario: Verify Authorized is shown with green Yes and Unauthorized is shown with red No in AUTHORIZED REPRESENTATIVES panel
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME                   | Random 5      |
      | LAST NAME                    | Random 5      |
      | AUTHORIZATION SIGNATURE DATE | RANDOM PAST   |
      | END DATE                     | RANDOM FUTURE |
      | AUTHORIZED                   | Yes           |
    Then I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    Then I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify Authorized is shown with green Yes and Unauthorized is shown with red No in AUTHORIZED REPRESENTATIVES panel

  @CP-16699 @CP-16699-05 @crm-regression @ui-ats @sang
  Scenario: Verify Authorized Representatives will display based on Status of Active then Future
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    Then I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | Random 5      |
      | LAST NAME  | Random 5      |
      | START DATE | RANDOM FUTURE |
    Then I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    Then I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | Random 5      |
      | LAST NAME  | Random 5      |
      | START DATE | RANDOM FUTURE |
    Then I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify the created Auth Reps are in order of Active status first then Future status

  @CP-16699 @CP-16699-06 @crm-regression @ui-ats @sang
  Scenario: No Authorized Representative panel is shown when there is no Authorized Representative
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify that there is no Authorized Representative panel shown in the Members panel

  @CP-16699 @CP-16699-07 @crm-regression @ui-ats @sang
  Scenario: Authorized Rep panel in Application tracking tab will show double dashes for null value
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    Then I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    Then I should see double dash is represented for null value in Access Type in AUTHORIZED REPRESENTATIVE panel

  @CP-16913 @CP-16913-01 @crm-regression @ui-ats @burak
  Scenario Outline: Verify User can select the present or a future date for Application Deadline-Date within Application Tracking Page for Medical Assistance
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on application tracking tab to navigate to Application Tracking page
    And I verify Application Information panel has the  "DEADLINE DATE" label within Application Tracking Tab
    Then I verify Edit button is displayed and clickable in Application Information Panel
    And I click Edit Button for the Application Information Panel
    And I select "<Date>" for the "DEADLINE DATE" on Application Tracking Page
    When I click save button for Application Information Panel
    Then I verify "DEADLINE DATE" on Application Tracking Page
    Examples:
      | Date               |
      | PRESENT DATE       |
      | RANDOM FUTURE DATE |

  @CP-16913 @CP-16913-02 @crm-regression @ui-ats @burak
  Scenario Outline: Verify User can select the present or a future date for Application Deadline-Date within Application Tracking Page for Long Term Care
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    When I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on application tracking tab to navigate to Application Tracking page
    And I verify Application Information panel has the  "DEADLINE DATE" label within Application Tracking Tab
    Then I verify Edit button is displayed and clickable in Application Information Panel
    And I click Edit Button for the Application Information Panel
    And I select "<Date>" for the "DEADLINE DATE" on Application Tracking Page
    When I click save button for Application Information Panel
    Then I verify "DEADLINE DATE" on Application Tracking Page
    Examples:
      | Date               |
      | PRESENT DATE       |
      | RANDOM FUTURE DATE |

  @CP-16913 @CP-16913-03 @crm-regression @ui-ats @burak
  Scenario: Verify system will not allow me to select a past date for Application Deadline-Date within Application Tracking Page for Medical Assistance
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on application tracking tab to navigate to Application Tracking page
    And I click Edit Button for the Application Information Panel
    And I select "RANDOM PAST DATE" for the "DEADLINE DATE" on Application Tracking Page
    When I click save button for Application Information Panel
    Then I verify Warning message displayed for "DEADLINE DATE" on Application Tracking Page

  @CP-16913 @CP-16913-04 @crm-regression @ui-ats @burak
  Scenario: Verify system will not allow me to select a past date for Application Deadline-Date within Application Tracking Page for LONG TERM CARE
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    When I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on application tracking tab to navigate to Application Tracking page
    And I click Edit Button for the Application Information Panel
    And I select "RANDOM PAST DATE" for the "DEADLINE DATE" on Application Tracking Page
    When I click save button for Application Information Panel
    Then I verify Warning message displayed for "DEADLINE DATE" on Application Tracking Page

  @CP-16913 @CP-16913-05 @crm-regression @ui-ats @burak
  Scenario: Verify When I click continue button on Warning Alert window for Deadline Date , I navigated to selected screen and changes are not saved for MEDICAL ASSISTANCE
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on application tracking tab to navigate to Application Tracking page
    And I store the "DEADLINE DATE" on Application tracking Page
    And I click Edit Button for the Application Information Panel
    And I select "Present Date" for the "DEADLINE DATE" on Application Tracking Page
    And I click application id under Application Tracking tab in the Application Information panel
    Then I verify Warning message displayed for "If you continue, all the captured information will be lost" on Application Tracking Page
    Then I click Continue button inside the warning message
    Then I verify Application Member header on create Application Member Page
    Then I navigate to Application Tracking
    Then I verify "FIRST DEADLINE DATE" on Application Tracking Page

  @CP-16913 @CP-16913-06 @crm-regression @ui-ats @burak
  Scenario: Verify When I click cancel button on Warning Alert window for Deadline Date , I remain on the screen for MEDICAL ASSISTANCE
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on application tracking tab to navigate to Application Tracking page
    And I store the "DEADLINE DATE" on Application tracking Page
    And I click Edit Button for the Application Information Panel
    And I select "Random Future Date" for the "DEADLINE DATE" on Application Tracking Page
    And I click application id under Application Tracking tab in the Application Information panel
    Then I verify Warning message displayed for "If you continue, all the captured information will be lost" on Application Tracking Page
    And I click Cancel button inside the warning message
    Then I verify I am on the Application Tracking Page
    When I click save button for Application Information Panel
    Then I verify "DEADLINE DATE" on Application Tracking Page

  @CP-16913 @CP-16913-07 @crm-regression @ui-ats @burak
  Scenario: Verify When I click continue button on Warning Alert window for Deadline Date , I navigated to selected screen and changes are not saved for Long Term Care
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    When I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on application tracking tab to navigate to Application Tracking page
    And I store the "DEADLINE DATE" on Application tracking Page
    And I click Edit Button for the Application Information Panel
    And I select "Random Future Date" for the "DEADLINE DATE" on Application Tracking Page
    And I click application id under Application Tracking tab in the Application Information panel
    Then I verify Warning message displayed for "If you continue, all the captured information will be lost" on Application Tracking Page
    Then I click Continue button inside the warning message
    Then I verify Application Member header on create Application Member Page
    Then I navigate to Application Tracking
    Then I verify "FIRST DEADLINE DATE" on Application Tracking Page

  @CP-16913 @CP-16913-08 @crm-regression @ui-ats @burak
  Scenario: Verify When I click cancel button on Warning Alert window for Deadline Date , I remain on the screen for Long Term Care
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    When I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on application tracking tab to navigate to Application Tracking page
    And I store the "DEADLINE DATE" on Application tracking Page
    And I click Edit Button for the Application Information Panel
    And I select "Present Date" for the "DEADLINE DATE" on Application Tracking Page
    And I click application id under Application Tracking tab in the Application Information panel
    Then I verify Warning message displayed for "If you continue, all the captured information will be lost" on Application Tracking Page
    And I click Cancel button inside the warning message
    Then I verify I am on the Application Tracking Page
    When I click save button for Application Information Panel
    Then I verify "DEADLINE DATE" on Application Tracking Page

  @CP-16913 @CP-16913-09 @crm-regression @ui-ats @burak
  Scenario: Verify When When the application status is set to Withdrawn, User is not able to edit Deadline Date for MEDICAL ASSISTANCE
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify I see Withdraw button displayed in Application Information panel
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    Then I  select "Already Receiving Services" from select reason dropdown
    When I click save button in the select reason
    Then I verify I see warning message "Are you sure you want to Withdraw this Application?"
    When I click Continue button inside the warning message
    And I see application Status as "WITHDRAWN" in the application information
    And I verify Edit button is displayed and disabled in Application Tracking Page for  Application Information Tab

  @CP-16913 @CP-16913-10 @crm-regression @ui-ats @burak
  Scenario: Verify When When the application status is set to Withdrawn, User is not able to edit Deadline Date for Long Term Care
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    When I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify I see Withdraw button displayed in Application Information panel
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    Then I  select "Not Interested in Services" from select reason dropdown
    When I click save button in the select reason
    Then I verify I see warning message "Are you sure you want to Withdraw this Application?"
    When I click Continue button inside the warning message
    And I see application Status as "WITHDRAWN" in the application information
    And I verify Edit button is displayed and disabled in Application Tracking Page for  Application Information Tab