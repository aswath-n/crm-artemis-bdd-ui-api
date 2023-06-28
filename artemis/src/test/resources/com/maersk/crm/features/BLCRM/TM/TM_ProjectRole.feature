Feature: Tenant Manager - Project Role Functionality

  @CRM-751 @CRM-751-01 @vinuta @tm-regression @ui-tm
  Scenario: Validate project role fields are displayed as expected
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    Then I see project name, project ID, role name, role description, start date, end date fields displayed

  @CRM-751 @CRM-751-02 @vinuta @tm-regression @ui-tm
  Scenario:Verify error displayed when saved without entering any data
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I click on Save button on project role page
    Then I see mandatory fields have error messages
      | Role Name       |
      | Role Start Date |

  @CRM-751 @CRM-751-03-a @vinuta @tm-regression @ui-tm @CRM-635
  Scenario Outline: Verify role is created when start date = end date
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    Given I check for a role with Active status,if not will create a Role on project role page "<roleName>","<roleDesc>","<startDate>","<endDate>"
#    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate>","<endDate>"
    And I click on Save button on project role page
    Then I see pop-up that role is created
    Examples:
      | roleName | roleDesc | startDate | endDate |
      | {random} | {random} | today     | today   |

  @CRM-751 @CRM-751-03-b @vinuta @tm-regression @ui-tm
  Scenario Outline: Verify error displayed when start date > end date
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate>","<endDate>"
    And I click on Save button on project role page
    Then I see "End date should not be less or equal than start date" message under the "roleEndDate" field
    Examples:
      | roleName | roleDesc | startDate | endDate |
      | {random} | {random} | today     | -1      |

  @CRM-751 @CRM-751-03-c @vinuta @tm-regression @ui-tm
  Scenario Outline: Verify error displayed when start date < current date
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate>","<endDate>"
    And I click on Save button on project role page
    Then I see "The Start Date cannot be in the past" message under the "roleStartDate" field
    Examples:
      | roleName | roleDesc | startDate | endDate |
      | {random} | {random} | -1        |[blank]|

  @CRM-751 @CRM-751-03-e @vinuta @tm-regression @ui-tm
  Scenario Outline: Verify error displayed when start date,end date are invalid
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate>","<endDate>"
    And I click on Save button on project role page
    Then I see "Invalid date format" message under the "roleStartDate" field
    And I see "Invalid date format" message under the "roleEndDate" field
    Examples:
      | roleName | roleDesc | startDate | endDate |
      | {random} | {random} | 09/02     | 09/02   |

  @CRM-751 @CRM-751-03-f @vinuta @tm-regression @ui-tm
  Scenario Outline: Verify error displayed when start date,end date are non-existing dates
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate>","<endDate>"
    And I click on Save button on project role page
    Then I see "The date entered does not exist. Please enter a valid date." message under the "roleStartDate" field
    And I see "The date entered does not exist. Please enter a valid date." message under the "roleEndDate" field
    Examples:
      | roleName | roleDesc | startDate  | endDate    |
      | {random} | {random} | 02/32/2019 | 13/13/3000 |

  @CRM-751 @CRM-751-04-a @vinuta @tm-regression @ui-tm @tm-smoke
  Scenario Outline: Verify error is displayed when duplicate role is created with same dates
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate>","<endDate>"
    And I click on Save button on project role page
    Then I see pop-up that role is created
    And I click on add role button on role list page
    When I populate data on project role page "<roleName>","<roleDesc>","<startDate>","<endDate>"
    And I click on Save button on project role page
    Then I see error that role already exists
    Examples:
      | roleName | roleDesc                       | startDate | endDate |
      | {random} | This is to test duplicate role | +1        |[blank]|

#  @CRM-751 @CRM-751-04-b @vinuta @tm-regression @ui-tm  //Functionality Changes
  Scenario Outline: Verify error is displayed when duplicate role is created with new start date between the time segment
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate1>","<endDate1>"
    And I click on Save button on project role page
    Then I see pop-up that role is created
    And I click on add role button on role list page
    When I populate data on project role page "<duplicateRoleName>","<roleDesc>","<startDate2>","<endDate2>"
    And I click on Save button on project role page
    Then I see error that role already exists
    Examples:
      | roleName         |duplicateRoleName| roleDesc                       | startDate1 | endDate1 | startDate2 | endDate2 |
      | {random}         | NewDuplicateRole|This is to test duplicate role | today      | +2       | +1         |[blank]|

#  @CRM-751 @CRM-751-04-c @vinuta @tm-regression @ui-tm       //Functionality Change
  Scenario Outline: Verify role is created when new role is created with new start date after first role end date
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate1>","<endDate1>"
    And I click on Save button on project role page
    Then I see pop-up that role is created
    And I click on add role button on role list page
    When I populate data on project role page "<roleName>","<roleDesc>","<startDate2>","<endDate2>"
    And I click on Save button on project role page
    Then I see pop-up that role is created
    Examples:
      | roleName | roleDesc                       | startDate1 | endDate1 | startDate2 | endDate2 |
      | {random} | This is to test duplicate role | today      | +1       | +2         |[blank]|

  @CRM-751 @CRM-751-05 @vinuta @tm-regression @ui-tm
  Scenario: Cancel button functionality on Add Role page when some data is entered
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "Test","Testing cancel button","today","today"
    And I click on Cancel button on Add Role Page
    Then I see "If you navigate away, your information will not be saved" alert displayed
    And I click on No and I am navigated back to Add Role page and see all previously entered unsaved data
    When I click on Cancel button on Add Role Page
    Then I see "If you navigate away, your information will not be saved" alert displayed
    And I click on Yes and I am navigated back to Role List Page

  @CRM-751 @CRM-751-06 @vinuta @tm-regression @ui-tm
  Scenario: Cancel button functionality on Add Role page when some data is entered
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I click on Cancel button on Add Role Page
    Then I navigate to project role list page

  @CP-1496 @CP-1496-1 @kamil @tm-regression
  Scenario Outline:Verify pages and sections and Permission Details Grid - display by default
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate1>","<endDate1>"
    And I click on Save button on project role page
    When I selected newly created role
    And Verify Permission Details grid
    And Verify each page has a caret
    And No Permission is selected by default to all pages
    Then Verify checked Apply to All checkbox displayed for the selected permissions
    Then Verify Filter By Page dropdown
    Then Verify selected the caret, then the fields and grids are displayed
    Then Verify Clear filter by page button
    Examples:
      | roleName | roleDesc         | startDate1 | endDate1 |
      | {random} | New role created | today      | +1       |

  @CP-1496 @CP-1496-2 @kamil @tm-regression
  Scenario Outline:Verify after expand the sections Apply to All checkbox displayed for the selected permissions
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate1>","<endDate1>"
    And I click on Save button on project role page
    When I selected newly created role
    And I select page "common" from Filter by Page dropdown
    And I checked "<permission>" Permission and Apply to All
    And Click on Save button and saving Permission
    And I select page "common" from Filter by Page dropdown
    Then I verify "<permission>" permission is applied to all sections after expand
    Examples:
      | roleName | roleDesc         | startDate1 | endDate1 | permission |
      | {random} | New role created | today      | +1       | Edit       |
      | {random} | New role created | today      | +1       | No         |
      | {random} | New role created | today      | +1       | View       |

  @CP-1496 @CP-1496-3 @kamil @tm-regression
  Scenario Outline:Verify selected a Permission for a field beneath that section
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate1>","<endDate1>"
    And I click on Save button on project role page
    When I selected newly created role
    Then Verify select a Permission for a field beneath that section that field display the same permission selected
    Examples:
      | roleName | roleDesc         | startDate1 | endDate1 |
      | {random} | New role created | today      | +1       |

  @CP-1496 @CP-1496-4 @kamil @tm-regression
  Scenario Outline:Verify checked View Permission and Apply to All for expanded caret same permission selected
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate1>","<endDate1>"
    And I click on Save button on project role page
    When I selected newly created role
    Then I checked View Permission and Apply to All
    Then Click on Save button and saving Permission
    And I select page "common" from Filter by Page dropdown
    And Verify Clear filter by page button
    Then Verify for expanded caret same permission selected
    Examples:
      | roleName | roleDesc         | startDate1 | endDate1 |
      | {random} | New role created | today      | +1       |

  @CP-1496 @CP-1496-5 @kamil @tm-regression
  Scenario Outline:Verify CANNOT de-select (nullify) any permissions and Apply for All
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate1>","<endDate1>"
    And I click on Save button on project role page
    And I selected newly created role
    And I select page "common" from Filter by Page dropdown
    And I checked "<permission>" Permission and Apply to All
    And Click on Save button and saving Permission
    And Verify success message is displayed & user remains on the same screen
    And Verify cannot de-select a "<permission>" Permission
    Examples:
      | roleName | roleDesc         | startDate1 | endDate1 | permission |
      | {random} | New role created | today      | +1       | Edit       |
      | {random} | New role created | today      | +1       | No         |
      | {random} | New role created | today      | +1       | View       |

  @CP-1496 @CP-1496-6 @kamil @tm-regression
  Scenario:Verify Permission Group icon is no longer displayed on the left navigation
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And Verify Permission Group icon is not displayed

#Feature: Validate the List of all Project Roles

  #RefactorBy: Vidya Date: 02-04-2020
  @CRM-752 @CRM-752-01 @vinuta @tm-regression @ui-tm
  Scenario: Verify by clicking on Project Roles
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role search page
    Then I should see all the fields in the Role Page

    #RefactorBy: Vidya Date: 02-04-2020
  @CRM-752 @CRM-752-02 @vinuta @tm-regression @ui-tm
  Scenario: Verify search results have the specified fields displayed
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role search page
    When I enter search criteria for a role by "Creation Date" value "Last month"
    And I click on search button on role search page
    Then Specified fields should be displayed in the results table

    #RefactorBy: Vidya Date: 02-04-2020
  @CRM-752 @CRM-752-03 @vinuta @tm-regression @ui-tm @tm-smoke
  Scenario: Verify error displayed when no results are returned for role search
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role search page
    When I enter search criteria for a role by "Role Name" value "fvgbf"
    And I click on search button on role search page
    Then Error displayed that no role exists

    #RefactorBy: Vidya Date: 02-04-2020
  @CRM-752 @CRM-752-04 @vinuta @tm-regression @ui-tm
  Scenario: Verify search criteria are cleared on clicking clear button
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role search page
    When I enter search criteria for a role by "Role Name" value "fvgbf"
    And I enter search criteria for a role by "Role Description" value "rtyuik"
    And I click on clear button on role search page
    Then All search criteria are reset to blank on role search page

    #RefactorBy: Vidya Date: 02-04-2020
  @CRM-752 @CRM-752-05 @vinuta @tm-regression @ui-tm
  Scenario: Verify dropdown values in search role page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role search page
    Then I see following dropdown options for "Creation Date" field displayed
      | Last 12 months |
      | Last 2 months  |
      | Last 3 months  |
      | Last 6 months  |
      | Last month     |
      | This month     |
    Then I see following dropdown options for "Modified Date" field displayed
      | Last 12 months |
      | Last 2 months  |
      | Last 3 months  |
      | Last 6 months  |
      | Last month     |
      | This month     |
    Then I see following dropdown options for "Role Staetus" field displayed
      | Active   |
      | All      |
      | Inactive |

  #refactored 03/14 Vinuta
  #RefactorBy: Vidya Date: 02-04-2020
  @CRM-752 @CRM-752-06 @vinuta @tm-regression @ui-tm @tm-smoke
  Scenario: Verify exact role names are displayed when searched by role name
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role search page
    When I enter search criteria for a role by "Role Name" value "Csr"
    And I click on search button on role search page
    Then Exact match role names are displayed in search results for role name "Csr"
    When I click on clear button on role search page
    When I enter search criteria for a role by "Role Description" value "Csr"
    And I click on search button on role search page
    And Wild card match for role description are displayed in search results

    #RefactorBy: Vidya Date: 02-04-2020
  @CRM-752 @CRM-752-07 @vinuta @tm-regression @ui-tm
  Scenario: Search by Active Status returns only active roles
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role search page
    And I click on clear button on role search page
    When I enter search criteria for a role by "Role Status" value "Active"
    And I click on search button on role search page
    Then Only active roles are displayed and not inactive roles
    And I click on clear button on role search page
    When I enter search criteria for a role by "Role Status" value "Inactive"
    And I click on search button on role search page
    Then Only inactive roles are displayed and not active roles

    #RefactorBy: Vidya Date: 02-04-2020
  @CRM-752 @CRM-752-08 @vinuta @tm-regression @ui-tm
  Scenario:  Search by Creation Date as This month & verify results
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role search page
    When I enter search criteria for a role by "Creation Date" value "This month"
    And I click on search button on role search page
    Then Only roles created this month are displayed

    #RefactorBy: Vidya Date: 02-04-2020
  @CRM-752 @CRM-752-09 @vinuta @tm-regression @ui-tm
  Scenario:  Search by Modified Date as This month & verify results
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role search page
    When I enter search criteria for a role by "Modified Date" value "This month"
    And I click on search button on role search page
    Then Only roles modified this month are displayed

    #RefactorBy: Vidya Date: 02-04-2020
  @CRM-752 @CRM-752-10 @vinuta @tm-regression @ui-tm
  Scenario: Role details page displayed on clicking a role from role list
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role search page
    When I enter search criteria for a role by "Role Name" value "Csr"
    And I click on search button on role search page
    Then I click on first role to open role details page

    #RefactorBy: Vidya Date: 02-04-2020
  @CRM-752 @CRM-752-11 @vinuta @tm-regression @ui-tm
  Scenario: Default sorting order on role list page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role search page
    Then Roles are sorted by start date descending

  @CP-13407 @CP-13407-01 @mital @tm-regression @ui-tm
  Scenario: Add Refresh Button to Role List in Tenant Manager
    Given I logged into Tenant Manager and set the project context "project" value "SelectBLCRMConfig"
    And I navigate to project role search page
    Then I verify Refresh Button is displayed and working on Role List Page

  @CP-34496 @CP-34496-01 @mital @tm-regression @ui-tm
  Scenario Outline: Verify permission cloning for User Role in Tenant Manager
    Given I logged into Tenant Manager and set the project context "project" value "SelectRegressionBaseline"
    And I navigate to project role search page
    When I enter search criteria for a role by "Role Name" value "Supervisor"
    And I click on search button on role search page
    Then I click on first role to open role details page
    Then I save permissions assigned in a list to compare with target role
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate1>","<endDate1>"
    And I click on Save button on project role page
    When I selected newly created role
    And I click on button Copy Permission
    Then I select a source role "Supervisor" from dropdown to be cloned and save it
    And I verify the success message is displayed for cloned permissions
    And I verify the permissions are cloned from role "Supervisor"
    Examples:
      | roleName | roleDesc         | startDate1 | endDate1 |
      | {random} | New role created | today      | +1       |

  @CP-25170 @CP-25170-01 @mital @tm-regression @ui-tm
  Scenario: Verify allowing TM user to apply a new field's permissions to all user roles on a project
    Given I logged into Tenant Manager and set the project context "project" value "SelectTESTREGRESSION2"
    And I navigate to project role search page
    When I enter search criteria for a role by "Role Name" value "Role1"
    And I click on search button on role search page
    Then I click on first role to open role details page
    And I select page "common" from Filter by Page dropdown
    And I checked "Edit" Permission and Apply to All Roles
    And Click on Save button and saving Permission
    And I navigate to project role search page
    When I enter search criteria for a role by "Role Name" value "Role2"
    And I click on search button on role search page
    Then I click on first role to open role details page
    And I select page "common" from Filter by Page dropdown
    And I verify other role has also got saved with same "Edit" permissions

  @CP-38253 @CP-38253-01 @mital @tm-regression @ui-tm
  Scenario Outline: Verify permission cloning for User Role for new permissions settings in Tenant Manager
    Given I logged into Tenant Manager and set the project context "project" value "SelectRegressionBaseline"
    And I navigate to project role search page
    When I enter search criteria for a role by "Role Name" value "Supervisor"
    And I click on search button on role search page
    Then I click on first role to open role details page
    When I navigate to new permission settings
    Then I save permissions assigned in a list to compare with target role
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate1>","<endDate1>"
    And I click on Save button on project role page
    When I selected newly created role
    When I navigate to new permission settings
    And I click on button Copy Permission
    Then I select a source role "Supervisor" from dropdown to be cloned and save it
    And I verify the success message is displayed for cloned permissions
    And I verify the permissions are cloned from role "Supervisor"
    Examples:
      | roleName | roleDesc         | startDate1 | endDate1 |
      | {random} | New role created | today      | +1       |

  @CP-38253 @CP-38253-02 @mital @tm-regression @ui-tm
  Scenario: Verify allowing user to apply a field's permissions to all user roles for new permissions settings
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role search page
    When I enter search criteria for a role by "Role Name" value "Role1"
    And I click on search button on role search page
    Then I click on first role to open role details page
    When I navigate to new permission settings
    And I select page "activeContact" from Filter by Page dropdown
    And I checked "Edit" Permission and Apply to All Roles
    And Click on Save button and saving Permission
    And I navigate to project role search page
    When I enter search criteria for a role by "Role Name" value "Role2"
    And I click on search button on role search page
    Then I click on first role to open role details page
    When I navigate to new permission settings
    And I select page "activeContact" from Filter by Page dropdown
    And I verify other role has also got saved with same "Edit" permissions





