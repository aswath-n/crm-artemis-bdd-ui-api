Feature: Tenant Manager - Project Functionality

#Feature: Verify Tenant Manager Dashboard

  @CRM-396 @CRM-289 @CRM-289-02 @CRM-290 @CRM-290-01 @CP-1722 @CP-1722-01 @CP-1722-05 @shilpa @tm-regression @ui-tm @tm-smoke
  Scenario: Verify fields & values displayed as expected in TM Dashboard
    Given I logged into Tenant Manager Project list page
    Then I can see all Project records with all data elements displayed
    Then The System should display the Current Date in the Header
    Then The System should display the Role
    Then The System should display the UserName
    And I scroll to the bottom of the page
    Then The System should display the Office Address
    When I click on Create a New Project
    Then I should be navigated to the Add Project Page
    Then I should see all the elements displayed in the Project Contact Page
    Then I validate Time zone drop down values

  #Refactored 12/19 Vinuta
  # Refactored 1-31-2020 Vidya As per strory CP-1722
  @CRM-289 @CRM-289-03 @CRM-291 @CRM-291-02 @CRM-290 @CRM-290-02 @CP-1722 @CP-1722-02 @shilpa @tm-regression @ui-tm @tm-smoke
  Scenario Outline: Create a new Project and verify error message for duplicate project
    Given I logged into Tenant Manager Project list page
    When I click on Create a New Project
    And I will enter "<prjName>","<prjState>","<prgName>","<contractId>","<stateAgencyName>","<startDate>","<endDate>","<goLiveDate>","<provStatus>","<timeZone>" fields values
    And I click on save button on project details page
    Then I verify success message is displayed for project creation and is on same page
    And I navigate back to Project List page
    When I click on Create a New Project
    And I will enter "<duplicateprjName>","<prjState>","<prgName>","","<stateAgencyName>","<startDate>","<endDate>","<goLiveDate>","<provStatus>","<timeZone>" fields values
    And I click on save button on project details page
    Then I should get a Error Message for duplicate project
    Examples:
      | prjName | duplicateprjName | prjState | prgName     | contractId | stateAgencyName | startDate | endDate | goLiveDate | provStatus | timeZone |
      | {random}   | duplicate        | FL       | TestProject | {random}   | Client2         |[blank]|         |[blank]| Active     | Eastern  |

  #Refactored 12/19 Vinuta
  #Refactored 03/14 Vinuta - as per bug 1244, error message consistency will be a future story
  @CRM-290 @CRM-290-04 @CP-1722 @CP-1722-03 @shilpa @tm-regression @ui-tm
  Scenario Outline:Validate the Contract Start Date and End Date
    Given I logged into Tenant Manager Project list page
    When I click on Create a New Project
    And I will enter "<prjName>","<prjState>","<prgName>","<contractId>","<stateAgencyName>","<startDate>","<endDate>","<goLiveDate>","<provStatus>","<timeZone>" fields values
    And I click on save button on project details page
    Then I verify filed level error in End date and Time Zone fields
    Examples:
      | prjName  | prjState | prgName  | contractId | stateAgencyName | startDate | endDate | goLiveDate | provStatus | timeZone |
      | {random} | CT       | {random} | {random}   | {random}        | today     | today   |[blank]| Active     |[blank]|

  @CP-3463 @CP-3463-01 @paramita @tm-regression @ui-tm
  Scenario:Validate error message when Go-Live Date is less than Contract Start date
    Given I logged into Tenant Manager Project list page
    When I click on Create a New Project
    And  I enter all the Details and enter Go-Live Date less than Contract start date
    Then I should see system throw an Error message

  @CP-3463 @CP-3463-02 @paramita @tm-regression @ui-tm
  Scenario:Validate error message when Go-Live Date is entered without Contract Start date
    Given I logged into Tenant Manager Project list page
    When I click on Create a New Project
    And  I enter all the Details and enter Go-Live Date without contract start date
    Then I should see an Error message

  @CP-3463 @CP-3463-03 @paramita @tm-regression @ui-tm
  Scenario:Validate error message when Go-Live Date is less than Contract End date
    Given I logged into Tenant Manager Project list page
    When I click on Create a New Project
    And  I enter all the Details and enter Go-Live Date less than Contract end date
    Then I should see system throw an Error message when Go-Live Date less than Contract end date

  @CP-3463 @CP-3463-04 @paramita @tm-regression @ui-tm
  Scenario:Validate user able to create the project when Go-Live Date is equal to Contract Start date
    Given I logged into Tenant Manager Project list page
    When I click on Create a New Project
    Then I enter Go-Live Date equal to Contract start date project created sucessfully

  #@CP-1722 @CP-1722-04 @vidya @tm-regression @ui-tm
  #Covered in CP-1722-07
  Scenario Outline: Verify warning message is displayed when project save is cancelled
    Given I logged into Tenant Manager Project list page
    When I click on Create a New Project
    And I will enter "<prjName>","<prjState>","<prgName>","<contractId>","<stateAgencyName>","<startDate>","<endDate>","<goLiveDate>","<provStatus>","<timeZone>" fields values
    And I click on "<Button>"
    Then I verify Warning message and Warning text is displayed with Continue and Cancel button
    And I click on cancel button on warning message in TM App
    Then I verify it should remain on the same page and information should not save in project details page
    And I click on "<Button>"
    And I click on continue button on warning message in TM APP
    Then I verify it should display "<Page>"
    Examples:
      | prjName  | prjState | prgName  | contractId | stateAgencyName | startDate | endDate | goLiveDate | provStatus | timeZone | Button     | Page              |
      | {random} | CT       | {random} | {random}   | {random}        |[blank]|         |[blank]| Active     | Eastern  | Back Arrow | Project List page |

  #@CP-1722 @CP-1722-05 @vidya @tm-regression @ui-tm
  #Covered in CRM-396
  Scenario: Verify timezone dropdown values
    Given I logged into Tenant Manager Project list page
    When I click on Create a New Project
    Then I validate Time zone drop down values

#Feature: View Project Details

  @GA-CP-7497 @GA-CP-7497-01 @ga-tm-regression @vidya
  Scenario Outline: Verify Project Details
    Given I logged into Tenant Manager and set the project context "project" value "SelectGAProject"
    Then I will verify "<prjName>","<prjState>","<prgName>","<contractId>","<stateAgencyName>","<startDate>","<endDate>","<goLiveDate>","<provStatus>","<timeZone>" fields values in project details page
    Examples:
      | prjName          | prjState | prgName             | contractId | stateAgencyName                              | startDate  | endDate    | goLiveDate | provStatus | timeZone |
      | Georgia-Families | GA       | Medicaid/CHIP/Other | 07XX       | Georgia Department of Community Health (DCH) | 07/01/2015 | 04/02/2026 | 04/02/2024 | Active     | Eastern  |

  @GA-CP-7497 @GA-CP-7497-02 @ga-tm-Regression @vidya
  Scenario Outline: Verify Project Details
    Given I logged into Tenant Manager and set the project context "project" value "SelectGAProject"
    Then I will verify the Contact Details for "<Role>","<fName>","<mName>","<lName>","<phone>","<email>" in project details page
    Examples:
      | Role             | fName    | mName | lName    | phone      | email                        |
      | Account Manager  | Adeline  |[blank]| Pierre   | 6789926241 | AdelineBPierre@maersk.com   |
      | Contact2         | Jeffrey  |[blank]| Hines    | 4045758008 | JefferyHines@maersk.com     |
      | Contact1         | Octavius |[blank]| Robinson | 4045758074 | OctaviusRobinson@maersk.com |
      | Account Approver | Donna    |[blank]| Herren   | 7032518502 | DonnaLHerren@maersk.com     |

#Feature: Tenant Manager Search a Project

  @CRM-294 @CRM-294-01 @muhabbat @tm-regression @ui-tm
  Scenario: Invalid State name search
    Given I logged into Tenant Manager Project list page
    When I search for a project by "state" value "QB"
    Then I should see "No records found" message

  @CRM-294 @CRM-294-02 @muhabbat @tm-regression @ui-tm
  Scenario: Invalid Project name search
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "Invalid"
    Then I should see "No records found" message

  @CRM-294  @CRM-294-03 @muhabbat @tm-regression @ui-tm
  Scenario: Invalid Program name search
    Given I logged into Tenant Manager Project list page
    When I search for a project by "program" value "NotExisting"
    Then I should see "No records found" message

  @CRM-294 @CRM-294-04 @muhabbat @tm-regression @ui-tm
  Scenario: Invalid value ClientAgency search
    Given I logged into Tenant Manager Project list page
    When I search for a project by "client" value "NoAgensy"
    Then I should see "No records found" message

  @CRM-294 @CRM-294-05 @muhabbat @tm-regression @ui-tm @tm-smoke
  Scenario: Valid State name search
    Given I logged into Tenant Manager Project list page
    When I search for a project by "state" value "TX"
    Then I should see all projects with "state" value "TX"

  @CRM-294 @CRM-294-06 @muhabbat  @tm-regression @ui-tm @tm-smoke
  Scenario: Valid Project name search
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "BLCRM"
    Then I should see all projects with "project" value "BLCRM"

  @CRM-294 @CRM-294-07 @muhabbat @tm-regression @ui-tm @tm-smoke
  Scenario: Valid Program name search
    Given I logged into Tenant Manager Project list page
    When I search for a project by "program" value "Baseline"
    Then I should see all projects with "program" value "Baseline"

  @CRM-294 @CRM-294-08 @muhabbat @tm-regression @ui-tm @tm-smoke
  Scenario: Valid Client/Agency name search
    Given I logged into Tenant Manager Project list page
    When I search for a project by "client" value "Louisiana ChildSupport"
    Then I should see all projects with "Client-Agency" value "Louisiana ChildSupport"

  @CRM-294 @CRM-294-09 @muhabbat @tm-regression @ui-tm
  Scenario: Project name Wild card search
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "S"
    Then I should see the projects according to "project" "S" wild card

  @CRM-294 @CRM-294-10 @muhabbat @tm-regression @ui-tm
  Scenario: Wild card search
    Given I logged into Tenant Manager Project list page
    When I search for a project by "state" value "V"
    Then I should see the projects according to "state" "V" wild card

  @CRM-294 @CRM-294-11 @muhabbat @tm-regression @ui-tm
  Scenario: Program name Wild card search
    Given I logged into Tenant Manager Project list page
    When I search for a project by "program" value "BL"
    Then I should see the projects according to "program" "BL" wild card

  @CRM-294 @CRM-294-12 @muhabbat @tm-regression @ui-tm
  Scenario: ClientAgency Wild card search
    Given I logged into Tenant Manager Project list page
    When I search for a project by "client" value "m"
    Then I should see the projects according to "client" "m" wild card

  @CRM-294 @CRM-294-13 @muhabbat @tm-regression @ui-tm @tm-smoke
  Scenario: No value provided search
    Given I logged into Tenant Manager Project list page
    When I search for a project with empty search fields
    Then I should see "Please provide search criteria" message

  @CRM-294 @CRM-294-14 @muhabbat @tm-regression @ui-tm
  Scenario: Autocomplete Project name search
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "S"
    Then I should see potential "project" with "S" autocomplete
    And I should see all projects with "project" value "S"

  @CRM-294 @CRM-294-15 @muhabbat @tm-regression @ui-tm
  Scenario: Autocomplete Program name search
    Given I logged into Tenant Manager Project list page
    When I search for a project by "program" value "PriorityHelp"
    Then I should see potential "program" with "PriorityHelp" autocomplete
    And I should see all projects with "program" value "PriorityHelp"

  @CRM-294 @CRM-294-16 @muhabbat @tm-regression @ui-tm
  Scenario: Autocomplete State search
    Given I logged into Tenant Manager Project list page
    When I search for a project by "state" value "v"
    Then I should see potential "state" with "v" autocomplete
    And I should see all projects with "state" value "v"

  @CRM-294 @CRM-294-16 @muhabbat @tm-regression @ui-tm
  Scenario: Autocomplete Client/State Agency search
    Given I logged into Tenant Manager Project list page
    When I search for a project by "client" value "l"
    Then I should see potential "client" with "l" autocomplete
    And I should see all projects with "client" value "l"

#Feature: View Project Search Result

  #@CRM-295 @CRM-295-03 @muhabbat @tm-regression @tm-smoke
  #Covered in CRM-396
  Scenario: View  All Projects on Project List Page
    When I logged into Tenant Manager Project list page
    Then I can see all Project records with all data elements displayed

  @CRM-295 @CRM-295-01 @muhabbat  @tm-regression @ui-tm
  Scenario Outline: View all mandatory Project elements
    Given I logged into Tenant Manager Project list page
    When I search for a project by "<field>" value "<value>"
    And I should see all projects with "<field>" value "<value>"
    Then I can see all Project records with all data elements displayed
    Examples:
      | field   | value                       |
      | project | BLCRM                       |
      | state   | VA                          |
      | program | Baseline                    |
      | client  | Mars Baseline Configuration |

  @CRM-295 @CRM-295-02 @muhabbat @tm-regression @ui-tm @tm-smoke
  Scenario Outline: Navigating through all project if more than on one page
    Given I logged into Tenant Manager Project list page
    When I search for a project by "<field>" value "<value>"
    And I should see all projects with "<field>" value "<value>"
    Then I can navigate to see more Projects than shown on current page
    Examples:
      | field   | value |
      | project | BLCRM |

  @CRM-295 @CRM-295-02 @muhabbat @tm-regression @ui-tm
  Scenario Outline: Navigating through all project if more than on one page
    Given I logged into Tenant Manager Project list page
    When I search for a project by "<field>" value "<value>"
    And I should see all projects with "<field>" value "<value>"
    Then I can navigate to see more Projects than shown on current page
    Examples:
      | field   | value                       |
#      | project | BLCRM                       |
      | state   | VA                          |
      | program | Baseline                    |
      | client  | Mars Baseline Configuration |

#Feature: Tenant Manager Editing a Project Details

  @CRM-293 @CRM-293-01 @muhabbat @tm-regression @ui-tm @tm-smoke
  Scenario Outline: Edit project details single field
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "ChildSupport1"
    When I expand the project "ChildSupport1" to view the details
    When I edit and save the project "<detail>" with "<value>" one at the time
    Then I verify success message is displayed for project creation and is on same page
    Then I navigate back to Project List page
    When I search for a project by "project" value "ChildSupport1"
    When I expand the project "ChildSupport1" to view the details
    Then I confirm the project naming "ChildSupport1" got updated with "<detail>" "<value>"
    Examples:
      | detail        | value               |
      | state         | random              |
      | program_name  | PriorityHelp        |
      | contract_id   | WS34                |
      | client_agency | PriorityPartners    |
      | start_date    | past                |
      | end_date      | future              |
      | pro_status    | random              |
      | project_Name  | ChildSupport1Update |
#
#  @CRM-293 @CRM-293-02 @muhabbat @tm-regression @ui-tm
#  Scenario Outline: Edit project details single field
#    Given I logged into Tenant Manager Project list page
#    When I search for a project by "project" value "ChildSupport1"
#    And I expand a random project to view the details
#    And I edit and save the project "<detail>" with "<value>" one at the time
#    Then I verify success message is displayed for project creation and is on same page
#    Then I navigate back to Project List page
#    Then I confirm the project "<detail>" "<value>"is updated
#    Examples:
#      | detail        | value            |
#      | program_name  | PriorityHelp     |
#      | contract_id   | WS34             |
#      | client_agency | PriorityPartners |
#      | start_date    | past             |
#      | end_date      | future           |
#      | pro_status    | Active           |

  @CRM-293 @CRM-293-02 @muhabbat @tm-regression @ui-tm
  Scenario Outline: Not saving edited project details
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "ChildSupport1"
    And I expand a random project to view the details
    And I edit but don't save the project "<detail>" with "<value>" one at the time
    Then I confirm the project "<detail>" "<value>" is not updated
    Examples:
      | detail        | value      |
      | project_name  | HomeCare   |
      | state         | MI         |
      | program_name  | MCHP       |
      | contract_id   | AAAA       |
      | client_agency | BBC        |
      | start_date    | 05/06/2018 |
      | end_date      | 05/09/2020 |
      | pro_status    | Inactive   |

  #@CRM-293 @CRM-293-09 @muhabbat
  Scenario: Edit project contact details
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I edit each role with new project contact details and see updates
    Then I change each role to previous value

  #@Test0710
  Scenario: Edit project contact details
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "BLCRM0703"
    #And I expand a random project to view the details
    #Then I add role to project
    #Then I add the permission to project
    And I navigate to Permission Group Page
    When I click on add new permission button
    And I populate all field with valid data on Add Permission Group Page
      | CSRQtpo |
    And I navigate to Task Template Page
    And I click on add new task template button
    When I populate template name as "Generalqtt" template description as "RANDOM"
    And I click on save task template button
    Then Success message is displayed on task template list page
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task
    And I enter the Associate Screen Name as "con"
    Then I click on save button on create task type screen
    And I verify newly created task type record

  @CP-3463 @CP-3463-05 @paramita @tm-regression @ui-tm
  Scenario:Validate error message when Go-Live Date is less than Contract Start date in edit project page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When  I enter Go-Live Date less than Contract start date
    Then I should see system throw an Error message when less than Contract Start Date

  @CP-3463 @CP-3463-06 @paramita @tm-regression @ui-tm
  Scenario:Validate user able to update the project details when Go-Live Date is equal to Contract Start date in edit project page
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "ChildSupport1"
    When I expand the project "ChildSupport1" to view the details
    When I enter Go-Live Date equal to Contract start date project updated sucessfully
    And I update Go-Live Date to next year

  @CP-3463 @CP-3463-07 @paramita @tm-regression @ui-tm
  Scenario:Validate error message when Go-Live Date is less than Contract End date in edit project page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When  I enter Go-Live Date less than Contract end date
    Then I should see system throw an Error message when less than Contract End Date

  @CP-1722 @CP-1722-06 @vidya @CP-1071 @vinuta @tm-regression @ui-tm
  Scenario Outline: Create a new Project and save the Project
    Given I logged into Tenant Manager Project list page
    When I click on Create a New Project
    When I will enter "<prjName>","<prjState>","<prgName>","<contractId>","<stateAgencyName>","<startDate>","<endDate>","<goLiveDate>","<provStatus>","<timeZone>" fields values
    And I click on save button on project details page
    Then I verify success message is displayed for project creation and is on same page
    And I verify "Add Contact Details" button is displayed & click on it
    Then I verify project contact details fields are displayed
    Then I verify I can delete the project contact details row before save
    When I verify "Add Contact Details" button is displayed & click on it
    Then I can enter project contact details
      | ROLE         | Account Approver      |
      | FIRST NAME   | RANDOM 10             |
      | LAST NAME    | RANDOM 10             |
      | MIDDLE NAME  | RANDOM 1              |
      | PHONE NUMBER | RANDOM 10             |
      | EMAIL        | testProject@email.com |
    And I click on save button on project details page
    Then I verify the project contact detail values are saved
    And I edit each role with new project contact details and see updates
    Examples:
      | prjName  | prjState | prgName  | contractId | stateAgencyName | startDate | endDate | goLiveDate | provStatus | timeZone |
      | {random} | UT       | {random} | {random}   | {random}        |[blank]|         |[blank]| Active     | Chamorro |

  @CP-1722 @CP-1722-07 @vidya @tm-regression @ui-tm
  Scenario Outline: Verify warning message is displayed when project save is cancelled
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I will enter "<prjName>","<prjState>","<prgName>","<contractId>","<stateAgencyName>","<startDate>","<endDate>","<goLiveDate>","<provStatus>","<timeZone>" fields values
    And I click on "<Button>"
    Then I verify Warning message and Warning text is displayed with Continue and Cancel button
    And I click on cancel button on warning message in TM App
    Then I verify it should remain on the same page and information should not save in project details page
    And I click on "<Button>"
    And I click on continue button on warning message in TM APP
    Then I verify it should display "<Page>"
    Examples:
      | prjName  | prjState | prgName  | contractId | stateAgencyName | startDate | endDate | goLiveDate | provStatus | timeZone | Button     | Page              |
      | {random} | CT       | {random} | {random}   | {random}        |[blank]|         |[blank]| Active     | Eastern  | Team Icon  | Team List page    |
      | {random} | CT       | {random} | {random}   | {random}        |[blank]|         |[blank]| Active     | Eastern  | Back Arrow | Project List page |

#Feature: Tenant Manager Project config

  #Refactor date:-23/10/2019 By:-Vidya
  @CRM-1411 @CRM-1411-01 @tm-regression @ui-tm @shruti
  Scenario: Verify configuration options  displayed on the left nav menu
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on the config icon for the project
    Then I verify configuration options displayed on the left nav menu

 #Refactor date:-23/10/2019 By:-Vidya
  @CRM-1411 @CRM-1411-02 @tm-regression @ui-tm @shruti
  Scenario: Verify when user clicks on configurations tab , Holiday configuration is defaulted
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on the config icon for the project
    Then I verify user lands on Holiday screen by default

  @CP-28407 @CP-28407-01 @chandrakumar @tm-regression @ui-tm
  Scenario: Validate appropriate error message when we try to create contact details with existing mail id
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I verify "Add Contact Details" button is displayed & click on it
    Then I verify project contact details fields are displayed
    Then I can enter project contact details
      | ROLE         | Account Approver |
      | FIRST NAME   | RANDOM 10        |
      | LAST NAME    | RANDOM 10        |
      | MIDDLE NAME  | RANDOM 1         |
      | PHONE NUMBER | RANDOM 10        |
      | EMAIL        | RANDOM EMAIL     |
    And I click on save button on project details page
    And I verify user is not navigated to Project List Page
    And I verify "Add Contact Details" button is displayed & click on it
    Then I can enter project contact details
      | ROLE         | Account Approver   |
      | FIRST NAME   | RANDOM 10          |
      | LAST NAME    | RANDOM 10          |
      | MIDDLE NAME  | RANDOM 1           |
      | PHONE NUMBER | RANDOM 10          |
      | EMAIL        | DUPLICATE EMAIL    |
    And I click on save button on project details page
    And I validate error message for duplicate emailid in add contact page

  @CP-28407 @CP-33104 @mital @tm-regression @ui-tm
  Scenario: Validate appropriate error message when we try edit contact details with existing mail id
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I verify the "Add Contact Details" button is displayed
    Then I verify if email ID can be duplicated or not
  Then I verify error message displayed with trace id

  @CP-35974 @CP-35974-01 @mital @tm-regression @ui-tm
  Scenario Outline: Restrict space and special characters in Project Name in create Project functionality
    Given I logged into Tenant Manager Project list page
    When I click on Create a New Project
    When I will enter "<prjName>","<prjState>","<prgName>","<contractId>","<stateAgencyName>","<startDate>","<endDate>","<goLiveDate>","<provStatus>","<timeZone>" fields values
    And I click on save button on project details page
    Then I verify the error message is displayed for Invalid project name
    Examples:
      | prjName         | prjState | prgName  | contractId | stateAgencyName | startDate | endDate | goLiveDate | provStatus | timeZone |
      | Test Project    | UT       | {random} | {random}   | {random}        |[blank]|         |[blank]| Active     | Chamorro |
      | TestProject#$@* | UT       | {random} | {random}   | {random}        |[blank]|         |[blank]| Active     | Chamorro |

  @CP-35974 @CP-35974-02 @mital @tm-regression @ui-tm
  Scenario Outline: Restrict space and special characters in Project Name in update Project functionality
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "ChildSupport1Update2"
    When I expend a Project to view the details
    And I edit and save the project "<detail>" with "<value>" one at the time
    Then I verify the error message is displayed for Invalid project name

    Examples:
      | detail  | value          |
      | project_Name | Test Project   |
      | project_Name | TestProject@#% |