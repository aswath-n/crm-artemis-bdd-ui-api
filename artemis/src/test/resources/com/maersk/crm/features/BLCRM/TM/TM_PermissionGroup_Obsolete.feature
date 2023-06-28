#removed regression tag due to change in functionality CP-1496
Feature: Create and Edit Permission Group

  @CRM-902 @CRM-902-01 @muhabbat 
  Scenario: Add Permission Group fields verification
    Given I logged into Tenant Manager Project list page
    When I search for a project by "program" value "BLCRM"
    And I expand a random project to view the details
    And I navigate to Permission Group Page
    When I click on add new permission button
    Then I verify all fields are displayed on Permission Group Page

  @CRM-902 @CRM-902-02 @muhabbat 
  Scenario: Add Permission Group mandatory fields verification
    Given I logged into Tenant Manager Project list page
    When I search for a project by "program" value "BLCRM"
    And I expand a random project to view the details
    And I navigate to Permission Group Page
    When I click on add new permission button
    Then I verify all mandatory fields on Permission Group Page

  @CRM-902 @CRM-902-03 @muhabbat 
  Scenario: Cancel Button functionality with YES on Add Permission Group Page
    Given I logged into Tenant Manager Project list page
    When I search for a project by "program" value "BLCRM"
    And I expand a random project to view the details
    When I navigate to add a new role page
    When I populate data on project role page "{random}","Role for Perm Gr","today","+1"
    And I click on Save button on project role page
    And I navigate to Permission Group Page
    When I click on add new permission button
    And I populate all field with valid data on Add Permission Group Page
    When I click on Cancel button on Add Permission Group Page
    And I select "yes" on warning pop-up of Add Permission Group Page
    Then I see permission was not saved on Permission Group Page

  @CRM-902 @CRM-902-04 @muhabbat 
  Scenario: Cancel Button functionality with NO on Add Permission Group Page
    Given I logged into Tenant Manager Project list page
    When I search for a project by "program" value "BLCRM"
    And I expand a random project to view the details
    When I navigate to add a new role page
    When I populate data on project role page "{random}","Role for Perm Gr","today","+1"
    And I click on Save button on project role page
    And I navigate to Permission Group Page
    When I click on add new permission button
    And I populate all field with valid data on Add Permission Group Page
    When I click on Cancel button on Add Permission Group Page
    And I select "no" on warning pop-up of Add Permission Group Page
    Then I see previously populated data on fields it not changed on add Permission Group Page

  @CRM-902 @CRM-902-05 @muhabbat 
  Scenario: Save Button functionality on Add Permission Group Page
    Given I logged into Tenant Manager Project list page
    When I search for a project by "program" value "BLCRM"
    And I expand a random project to view the details
    And I navigate to Permission Group Page
    When I click on add new permission button
    And I populate all field with valid data on Add Permission Group Page
    When I click on Save button on Add Permission Group Page
    Then I see section for capturing the specific permission on UI components

  @CRM-902 @CRM-902-06 @muhabbat 
  Scenario: Permissions not selected by default on creating new permission
    Given I logged into Tenant Manager Project list page
    When I search for a project by "program" value "BLCRM"
    And I expand a random project to view the details
    When I navigate to add a new role page
    When I populate data on project role page "{random}","Role for Perm Gr","today","+5"
    And I click on Save button on project role page
    And I navigate to Permission Group Page
    When I click on add new permission button
    And I populate all field with valid data on Add Permission Group Page
    When I click on Save button on Add Permission Group Page
    Then I see permission options flags by default not selected

  @CRM-902 @CRM-902-07 @muhabbat 
  Scenario Outline: View/Edit/No permission flags on Add Permission Group Page
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "BLCRM"
    And I expand a random project to view the details
    And I navigate to Permission Group Page
    When I click on add new permission button
    And I populate all field with valid data on Add Permission Group Page
    When I click on Save button on Add Permission Group Page
    And I see section for capturing the specific permission on UI components
    When I see "<level>" Permission
    Then I see permission options flags by default not selected
    Examples:
      | level   |
      | page    |
      | section |
      | field   |

  @CRM-902 @CRM-902-08 @CRM-902-11 @muhabbat 
  Scenario Outline: Selecting and deselecting View/Edit/No permission flags on Add Permission Group Page
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "BLCRM"
    And I expand a random project to view the details
    And I navigate to Permission Group Page
    When I click on add new permission button
    And I populate all field with valid data on Add Permission Group Page
    When I click on Save button on Add Permission Group Page
    When I see "<level>" Permission
    Then I am able to select and deselect View/Edit/No and Apply For All permission flags on Add Permission Group Page
    Examples:
      | level   |
      | page    |
      | section |
      | field   |

  @CRM-902  @CRM-902-09 @muhabbat 
  Scenario Outline: Editing Permission Group
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "BLCRM"
    And I expand a random project to view the details
    And I navigate to Permission Group Page
#    When I click on add new permission button
#    And I populate all field with valid data on Add Permission Group Page
#    When I click on Save button on Add Permission Group Page
#    When I see "section" Permission
#    When I see select permission options flags "view" one of the pages and save it
    Then I expand previously created random Permission Group
    Then I can edit "<field>" on Edit Permission Group Page
    When I see "{random}" Permission
    When I save the Permission Group
    Then I can see new permission group was saved with expected value
    Examples:
      | field                 |
      | Group Permission Name |
      | Description           |
#      | User Role             |

  @CRM-902 @CRM-902-10 @muhabbat # todo add tm-regression tag after future start date status active Permission group bug is fixed 03/20/19
  Scenario: Validate only active roles are displayed in the role dropdown
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "BLCRM"
    And I expand a random project to view the details
    When I navigate to add a new role page
    When I populate data on project role page "{random}","Active Role for Perm Gr","today","+2"
    And I click on Save button on project role page
    When I see pop-up that role is created
    And I click on add role button on role list page
    When I populate data on project role page "{random}","Inactive Role for Perm Gr","+1",""
    And I click on Save button on project role page
    When I see pop-up that role is created
    And I navigate to Permission Group Page
    When I click on add new permission button
    Then I see Select Role drop-down has only Active role as an option


  @EB-224 @EB-224-01 @shruti 
  Scenario: Selecting  View permission for all the grids in config json
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "AUT UI PRJ"
    And I expand a random project to view the details
    And I navigate to Permission Group Page
    When I click on group permission to edit for role "Csr"
    And I add "View" permission for all grids

  @EB-224 @EB-224-02 @shruti 
  Scenario: Selecting No permission  for grids
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "AUT UI PRJ"
    And I expand a random project to view the details
    And I navigate to Permission Group Page
    When I click on group permission to edit for role "Csr"
    And I add "No Permission" permission for page "demographic" section "demographicPrimaryIndividual" and grid "demographicPrimaryIndividualGrid"
    #When I click on group permission to edit for role "Csr"
    Then I verify "No Permission" permission is updated for page "demographic" section "demographicPrimaryIndividual" and grid "demographicPrimaryIndividualGrid"


  @EB-224 @EB-224-03 @shruti 
  Scenario: Selecting and deselecting View/Edit/No permission flags on Add Permission Group Page at GRID Level
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "AUT UI PRJ"
    And I expand a random project to view the details
    And I navigate to Permission Group Page
    When I click on group permission to edit for role "Csr"
    And I add "View" permission for page "demographic" section "demographicPrimaryIndividual" and grid "demographicPrimaryIndividualGrid"
    Then I verify "View" permission is updated for page "demographic" section "demographicPrimaryIndividual" and grid "demographicPrimaryIndividualGrid"
    When I click on group permission to edit for role "Csr"
    And I add "Edit" permission for page "demographic" section "demographicPrimaryIndividual" and grid "demographicPrimaryIndividualGrid"
    Then I verify "Edit" permission is updated for page "demographic" section "demographicPrimaryIndividual" and grid "demographicPrimaryIndividualGrid"

  @EB-224 @EB-224-04 @shruti 
  Scenario: Verify Applying permission at section level -Permission applied to GRID
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "AUT UI PRJ"
    And I expand a random project to view the details
    Then I navigate to Permission Group Page
    When I click on group permission to edit for role "Csr"
    And I add "View" permission for page "demographic" section "demographicCaseMember" with appy for all
   # And I click on group permission to edit for role "Csr"
    Then I verify "View" permission for page "demographic" section "demographicCaseMember" for all grids

#removed regression tag due to change in functionality CP-1496
#Feature: View Permission in the in Tenant Manager

  @CRM-903 @CRM-903-01 @aswath  #Added line 6 to eliminate driver not being able to expend a rundom projext if list of the projects is too big
  Scenario: Verify the Permission Group page is displayed
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "BLCRM"
    And I expand a random project to view the details
    And I navigate to Permission Group Page
    Then I verify the Permission table

  @CRM-903 @CRM-903-02 @aswath   # Message was changed to "No Records Available "
  Scenario: Verify the No Permission Group message displayed on new project
    Given I logged into Tenant Manager Project list page
    And I create project and navigate to the project
    And I expand a random project to view the details
    And I navigate to Permission Group Page
    Then I verify No Permission message in permission list page

  #@CRM-903 @CRM-903-03 @aswath   Can be deleted this scenario, added in the CRM 902
  Scenario: Validate fields in permission page
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "BLCRM"
    And I expand a random project to view the details
    And I navigate to Permission Group Page
    When I click on add new permission button
    And I populate all field with valid data on Add Permission Group Page
    When I click on Save button on Add Permission Group Page
    And I see section for capturing the specific permission on UI components

  @CRM-903 @CRM-903-04 @aswath
  Scenario: Validate permissions records associated with project
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "BLCRM"
    And I expand a random project to view the details
    And I navigate to Permission Group Page
    And I verify the records in a permission table

  @CRM-903 @CRM-903-05 @aswath  # for line 47 please consider adding condition of records<=Pagesize, cose if the project is having less records than Pagesize test will fail
  Scenario Outline: Validate Pagination records on the view permission page
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "TXCRM"
    And I expand a random project to view the details
    And I navigate to Permission Group Page
    Then I verify default pagination in permission page
    And I add new permission records
    And I select the pagination option has <Pagesize> records view option
    Then I verify the <Pagesize> records are displayed
    Examples:
      |Pagesize|
      |5|

  @CRM-903 @CRM-903-06 @aswath
  Scenario: Verify the edit permission and validate the permission
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "TXCRM"
    And I expand a random project to view the details
    And I navigate to Permission Group Page
    And I verify the records in a permission table
    When I click on add new permission button
    And I populate all field with valid data on Add Permission Group Page
    When I click on Save button on Add Permission Group Page
    Then I edit the Permission on the permission group page
    Then I verify the edit permission page

  @CRM-903 @CRM-903-07 @aswath
  Scenario: Validate default sort in Group Permission name column
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "BLCRM"
    And I expand a random project to view the details
    And I navigate to Permission Group Page
    Then I verify the default sort for the permission column

    #This feature will be modified to removing the permission group section from Create Role page.
#Create Permission will be valid after you create a role CRM-902 "comment added by Prem at CRM-2076"
#Feature: Associating Permission to Role

  @CRM-904 @CRM-904-01 @muhabbat
  Scenario: Validate permission section fields on Permission Group section are displayed
    Given I navigate to project role details page of first project
    Then I see all fields on Select Permission Group section displayed

  @CRM-904 @CRM-904-02 @muhabbat
  Scenario: Validate permission name is auto-completed with available permission names
    Given I navigate to project role details page of first project
    When I enter a permission name "a" on select permission group section
    Then I see group permission options available for this name "a" displayed

  @CRM-904 @CRM-904-03 @muhabbat
  Scenario: Validate permission description on Permission Group section is not editable
    Given I navigate to project role details page of first project
    Then I see permission description field on Permission Group section is not editable

  @CRM-904 @CRM-904-04 @muhabbat
  Scenario Outline: Validate permission start and end dates with past present and future dates
    Given I navigate to project role details page of first project
    When I populate data on project role page "<roleName>","<roleDesc>","<startDate>","<endDate>"
    And I populate data on select permission group section for "admin" group name and "<permissionStartDate>", "<permissionEndDate>"
    And I click on Save button on project role page
    Then I see incorrect permission group dates error messages "<message>"
    Examples:
      | roleName | roleDesc | startDate | endDate | permissionStartDate | permissionEndDate | message       |
      | {random} | {random} | today     | +2      | future              | past              | {lessOrEqual} |
      | {random} | {random} | today     | +2      | future              | sameFuture        | {lessOrEqual} |
      | {random} | {random} | today     | +2      | past                |[blank]| {past}        |
      | {random} | {random} | today     | +2      | current             | current           | {lessOrEqual} |


  @CRM-904 @CRM-904-05 @CRM-904-06 @muhabbat
  Scenario Outline: Validate permission start and end dates with past present and future dates
    Given I navigate to project role details page of first project
    When I populate data on project role page "<roleName>","<roleDesc>","<startDate>","<endDate>"
    And I populate data on select permission group section for "admin" group name and "<permissionStartDate>", "<permissionEndDate>"
    And I click on Save button on project role page
    Then I see pop-up that role with selected permission group is created
    Examples:
      | roleName | roleDesc | startDate | endDate | permissionStartDate | permissionEndDate |
      | {random} | {random} | today     | +1      | future              | future            |
      | {random} | {random} | today     | +1      | current             | future            |
      | {random} | {random} | today     | +1      | current             |[blank]|

  @CRM-904 @CRM-904-07 @muhabbat
  Scenario:Verify warning message is displayed if attempt to save a role with permission that does not exist
    Given I navigate to project role details page of first project
    When I populate data on project role page "random","random","today","+1"
    And I populate data on select permission group section for "random" group name and "current", "future"
    When I click on Save button on project role page
    Then I see warning message to save a role without permission group selected


