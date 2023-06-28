Feature: ATS Application Tracking Page

  @CP-14853 @CP-14853-01 @CP-16116 @CP-16116-01 @crm-regression @ui-ats @sang
  Scenario: Verify Application Header information is displayed in Application Tracking Tab for Medical Assistance
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify application icon with Primary Individual name and App Id is displayed in the Header

  @CP-14853 @CP-14853-02 @CP-16116 @CP-16116-02 @crm-regression @ui-ats @sang
  Scenario: Verify Application Header information is displayed in Application Tracking Tab for Long Term Care
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify application icon with Primary Individual name and App Id is displayed in the Header

  @CP-14853 @CP-14853-03 @crm-regression @ui-ats @sang
  Scenario: Verify Application Tracking tab Application Information Panel Labels for Medical Assistance
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    When I click on application tracking tab to navigate to Application Tracking page
    And I see columns displayed in this order in the Application Information panel
      | APPLICATION ID | APPLICATION CODE | EXTERNAL APP ID | PRIORITY | DEADLINE DATE | SIGNATURE DATE | APPLICATION TYPE | CYCLE | RECEIVED DATE | RECEIVED LANGUAGE | CHANNEL | CREATE DATE | LAST UPDATED DATE |

  @CP-14853 @CP-14853-03.1 @CP-16673 @CP-16673-04 @crm-regression @ui-ats @sang @ats-smoke
  Scenario: Verify Application Tracking tab Application Information Panel Labels for Long Term Care
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    When I click on application tracking tab to navigate to Application Tracking page
    And I see columns displayed in this order in the Application Information panel
      | APPLICATION ID | APPLICATION CODE | EXTERNAL APP ID | PRIORITY | DEADLINE DATE | SIGNATURE DATE | APPLICATION TYPE | CYCLE | RECEIVED DATE | RECEIVED LANGUAGE | CHANNEL | CREATE DATE | LAST UPDATED DATE |

  @CP-14853 @CP-14853-04 @CP-16673 @CP-16673-05 @crm-regression @ui-ats @sang
  Scenario: Verify I can access Application page by clicking on the application Id hyperlink in Application tracking tab
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    And I click application id under Application Tracking tab in the Application Information panel
    And I verify I am on Primary Individual details page
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    And I click application id under Application Tracking tab in the Application Information panel
    And I verify I am on Primary Individual details page

  @CP-14853 @CP-14853-05 @crm-regression @ui-ats @sang
  Scenario: Verify correct Application values inside Application Info panel in Application Tracking tab for Medical Assistance
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CYCLE          | New     |
      | RECEIVED DATE  | current |
      | CHANNEL        | random  |
      | SIGNATURE      | Yes     |
      | SIGNATURE DATE | random  |
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify the Application panel values for "MEDICAL ASSISTANCE" in the Application tracking tab

  @CP-14853 @CP-14853-06 @crm-regression @ui-ats @sang
  Scenario: Verify correct Application values inside Application Info panel in Application Tracking tab for Long Term Care
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CYCLE          | New     |
      | RECEIVED DATE  | current |
      | CHANNEL        | random  |
      | SIGNATURE      | Yes     |
      | SIGNATURE DATE | random  |
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify the Application panel values for "LONG TERM CARE" in the Application tracking tab

  @CP-14853 @CP-14853-07 @crm-regression @ui-ats @sang
  Scenario: Display Null Values as double dashes in the Application Info Panel on MEDICAL ASSISTANCE Application Tracking tab
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify double dash is present in signature date value on Application info on Application Tracking tab

  @CP-14853 @CP-14853-07.1 @crm-regression @ui-ats @sang
  Scenario: Display Null Values as double dashes in the Application Info Panel on LONG TERM CARE Application Tracking tab
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify double dash is present in signature date value on Application info on Application Tracking tab

  @CP-14853 @CP-14853-08 @crm-regression @ui-ats @sang
  Scenario:Application Tracking Tab Back Arrow From application creation clears Application
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    When I click on application tracking tab to navigate to Application Tracking page
    And I click on the back arrow in header row next to the icon Primary Individual name and application id
    Then I verify application is cleared from view

  @CP-14853 @CP-14853-09 @CP-16116 @CP-16116-08 @crm-regression @ui-ats @sang #Fails due to CP-34128
  Scenario:Back Arrow to Search Results
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | APPLICATION ID |
      | 2              |
    When I click on search button in search application page
    When I click on first APPLICATION ID "2"
    And I click on the back arrow button on add application member page
    Then I verify I am on Search Application page

  @CP-14853 @CP-14853-10 @crm-regression @ui-ats @sang
  Scenario: Navigate to New create application page from Tracking Tab
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And  I verify Application created Success Message and Store Application ID value created
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify application icon with Primary Individual name and App Id is displayed in the Header
    And I navigate to Create "LONG TERM CARE" application page
    Then I verify "LONG TERM CARE" Application Page header is displayed

  @CP-15565 @CP-15565-01 @CP-22998-01 @crm-regression @ui-ats @sang @burak
  Scenario: Verify Application Tracking tab View Members Info panel Labels
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    And I verify the save successfully updated message
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify the labels in Application Tracking tab Members Info panel

  @CP-15565 @CP-15565-02 @crm-regression @ui-ats @sang
  Scenario: Verify Primary Individual is First in the list in Members Info Panel with alpha 61 letters for LONG TERM CARE
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME     | Alphabetic 30 |
      | MIDDLE INITIAL | Alphabetic 1  |
      | LAST NAME      | Alphabetic 30 |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And click on save button for add application member
    And I verify the save successfully updated message
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify Primary Individual name created with sixtyone letters is first on list in Members Info panel

  @CP-15565 @CP-15565-03 @crm-regression @ui-ats @sang
  Scenario: Verify Primary Individual is First in the list in Members Info Panel with alpha 61 letters for MEDICAL ASSISTANCE
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME     | Alphabetic 30 |
      | MIDDLE INITIAL | Alphabetic 1  |
      | LAST NAME      | Alphabetic 30 |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And click on save button for add application member
    And I verify the save successfully updated message
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify Primary Individual name created with sixtyone letters is first on list in Members Info panel

  @CP-15565 @CP-15565-04 @crm-regression @ui-ats @sang
  Scenario: Verify all Gender abbreviation and correct ages are listed in the Members Info Panel
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5   |
      | LAST NAME  | Random 5   |
      | GENDER     | Female     |
      | DOB        | 01/02/1920 |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5   |
      | LAST NAME  | Random 5   |
      | GENDER     | Male       |
      | DOB        | 12/12/1950 |
    And click on save button for add application member
    And I verify the save successfully updated message
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5   |
      | LAST NAME  | Random 5   |
      | GENDER     | Neutral    |
      | DOB        | 11/11/2000 |
    And click on save button for add application member
    And I verify the save successfully updated message
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5   |
      | LAST NAME  | Random 5   |
      | GENDER     | Other      |
      | DOB        | 10/10/2020 |
    And click on save button for add application member
    And I verify the save successfully updated message
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5   |
      | LAST NAME  | Random 5   |
      | GENDER     | Unknown    |
      | DOB        | 09/09/1990 |
    And click on save button for add application member
    And I verify the save successfully updated message
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify created Members following gender abbreviation are listed in Members Info Panel
      | F | M | N | O | U |
    And I verify correct ages are listed in members Info panel from created Applicant members
      | 09/09/1990 | 10/10/2020 | 11/11/2000 | 12/12/1950 | 01/02/1920 |

  @CP-15565 @CP-15565-05 @crm-regression @ui-ats @sang
  Scenario: Verify Medical assistance Members Info panel lists programs in alphabetical order
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I choose "Medicaid" as program type
    Then I choose "CHIP" as program type
    Then I choose "Pregnancy Assistance" as program type
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Alphabetic 30    |
      | LAST NAME  | Alphabetic 30    |
      | DOB        | random past date |
      | GENDER     | Male             |
      | SSN        | Numeric 9        |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    When I click on Save button on Create Application Page
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify the programs are listed and displayed stacked in Alphabetical order

  @CP-15565 @CP-15565-06 @crm-regression @ui-ats @sang
  Scenario: Verify Long Term Care program is listed in Application Tracking tab Members Info panel
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    Then I choose "HCBS" as program type
    Then I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME | Alphabetic 30    |
      | LAST NAME  | Alphabetic 30    |
      | DOB        | random past date |
      | GENDER     | Male             |
      | SSN        | Numeric 9        |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    When I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify Long Term Care "HCBS" is listed in Application Tracking page Members Info panel

  @CP-15565 @CP-15565-06 @CP-22998-02 @crm-regression @ui-ats @sang @burak
  Scenario: Verify doubles dashes are in place for null values in Application tracking tab Members Info panel
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I cleared DOB from primary individual on create application page
    When I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify double dash is used for null values in Application tracking tab Members Info panel

  @CP-16116 @CP-16116-03 @CP-22998-03 @crm-regression @ui-ats @sang @burak
  Scenario: Verify the labels in Contact Info panel in MEDICAL ASSISTANCE Application tracking Tab
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify the following labels in Contact Info panel in Application tracking Tab
      | PRIMARY PHONE NUMBER | SPOKEN LANGUAGE | WRITTEN LANGUAGE | RESIDENCE ADDRESS |

  @CP-16116 @CP-16116-03.1 @CP-22998-04 @crm-regression @ui-ats @sang @burak
  Scenario: Verify the labels in Contact Info panel in LONG TERM CARE Application tracking Tab
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify the following labels in Contact Info panel in Application tracking Tab
      | PRIMARY PHONE NUMBER | SPOKEN LANGUAGE | WRITTEN LANGUAGE | RESIDENCE ADDRESS |

  @CP-16116 @CP-16116-04 @crm-regression @ui-ats @sang
  Scenario: Verify Primary Phone Number Spoken Written Language and Residence Address values are shown in Contact Info Panel for Medical Assistance
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME       | Alphabetic 30   |
      | LAST NAME        | Alphabetic 30   |
      | SPOKEN LANGUAGE  | random dropdown |
      | WRITTEN LANGUAGE | random dropdown |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER        | Random Numeric 10 |
      | RESIDENCE ADDRESS LINE 1 | random            |
      | RESIDENCE ADDRESS LINE 2 | random            |
      | RESIDENCE CITY           | Metropolis        |
      | RESIDENCE STATE          | Illinois          |
      | RESIDENCE ZIP CODE       | 62960             |
      | RESIDENCE COUNTY         | Massac            |
    Then I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify Primary Phone Number Spoken Written Language and Residence Address values are shown in Contact Info Panel

  @CP-16116 @CP-16116-05 @crm-regression @ui-ats @sang
  Scenario: Verify Primary Phone Number Spoken Written Language and Residence Address values are shown in Contact Info Panel for Long Term Care
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    Then I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME       | Alphabetic 30   |
      | LAST NAME        | Alphabetic 30   |
      | SPOKEN LANGUAGE  | random dropdown |
      | WRITTEN LANGUAGE | random dropdown |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER        | Random Numeric 10 |
      | RESIDENCE ADDRESS LINE 1 | random            |
      | RESIDENCE ADDRESS LINE 2 | random            |
      | RESIDENCE CITY           | Metropolis        |
      | RESIDENCE STATE          | Illinois          |
      | RESIDENCE ZIP CODE       | 62960             |
      | RESIDENCE COUNTY         | Massac            |
    Then I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify Primary Phone Number Spoken Written Language and Residence Address values are shown in Contact Info Panel

  @CP-16116 @CP-16116-06 @CP-22998-05 @crm-regression @ui-ats @sang
  Scenario: Verify double dash are shown in Contact Info Panel for Medical Assistance
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    Then I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify double dash is used for null values in the Contact Info panel for following
      | RESIDENT ADDRESS | SPOKEN LANGUAGE | WRITTEN LANGUAGE |

  @CP-16116 @CP-16116-07 @CP-22998-06 @crm-regression @ui-ats @sang
  Scenario: Verify double dash are shown in Contact Info Panel for Long Term Care
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    Then I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | RESIDENCE ADDRESS LINE 1 | random     |
      | RESIDENCE ADDRESS LINE 2 | random     |
      | RESIDENCE CITY           | Metropolis |
      | RESIDENCE STATE          | Illinois   |
      | RESIDENCE ZIP CODE       | 62960      |
      | RESIDENCE COUNTY         | Massac     |
    Then I click on Save button on Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify double dash is used for null values in the Contact Info panel for following
      | PRIMARY PHONE | SPOKEN LANGUAGE | WRITTEN LANGUAGE |


