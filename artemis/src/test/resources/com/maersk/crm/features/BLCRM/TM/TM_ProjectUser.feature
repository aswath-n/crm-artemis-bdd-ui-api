Feature: Tenant Manager - Project User Functionality

#  @CRM-635 @tm-regression @ui-tm @vinuta             // Combined with script @CRM-751-03-a
  Scenario Outline: Check if the project has active role, if not, create a new role
    Given I check for a role with Active status,if not will create a Role on project role page "<roleName>r,"<roleDesc>","<startDate>","<endDate>"
    Examples:
      | roleName | roleDesc | startDate | endDate |
      | {random} | {random} | today     | +1      |

  @CRM-635 @CRM-635-01 @muhabbat @tm-regression @ui-tm
  Scenario:Validate fields, default values on User Details page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
    And I click on add new user button on add new user page
    When I should be navigated to User Details page
    And I should see all the elements displayed in the User Details Page
    Then I should see default values populated

  @CRM-635 @CRM-635-02 @muhabbat @tm-regression @ui-tm
  Scenario: Validating mandatory fields on User Details page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I check Account Manager and Approver are added
    And I click on User List Menu
    And I click on add new user button on add new user page
    When I should be navigated to User Details page
    And I click on save a user button
    Then I see mandatory fields have error messages
      | First Name                       |
      | Last Name                        |
      | Internal maersk Employee? - Y/N |
      | Email Address                    |
      | Start Date                       |
      | Account Type                     |

  @CRM-635 @CRM-635-03 @muhabbat @tm-regression @ui-tm
  Scenario: Validating auto-populated fields on User Details page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I check Account Manager and Approver are added
    And I click on User List Menu
    And I click on add new user button on add new user page
    When I should be navigated to User Details page
    And I set maersk employee to yes
    And I enter max id "200341"
    And I click on add max id button
    When I see fields auto-populated with the corresponding values from Active Directory
    Then I see Auto-populated fields are not editable

  #By Vinuta, If the Active Directory does not identify the MAXID
  @CRM-635 @vinuta @CRM-635-04 @tm-regression @ui-tm
  Scenario Outline: If the Active Directory does not identify the MAXID, display error
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
    And I click on add new user button on add new user page
    And I set maersk employee to yes
    And I enter max id "<MaxID>"
    And I click on add max id button
    Then I receive an error that max ID does not exist
    Examples:
      | MaxID |
      | 123   |

  @CRM-635 @vinuta @CRM-635-05 @tm-regression @ui-tm
  Scenario: Creating user when approvers are not added to the project
    Given I logged into Tenant Manager Project list page
    When I click on Create a New Project
    When I create,save and view new project details
    And I click on User List Menu
    And I click on add new user button on add new user page
    Then I should see a pop-up as an overlay on User Details page
    And I click on Continue button on User Details error pop-up
    Then I am navigated to Project Details page

  @CRM-635 @CRM-635-06 @muhabbat @tm-regression @ui-tm
  Scenario: Cancel button functionality on Add User page when some data is entered
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
    And I click on add new user button on add new user page
    And I should be navigated to User Details page
    And I populate some data in the fields
    And I click on Cancel button on Add User Page
    And I see "If you navigate away, your information will not be saved" alert displayed
    When I click on No and I am navigated back to Add User page and see all previously entered unsaved data
    And I click on Cancel button on Add User Page
    And I see "If you navigate away, your information will not be saved" alert displayed
    Then I click on Yes and I am navigated back to User List Page

  #Refactored 03/14 Vinuta - removed the step which verifies warning pop-up
  @CRM-635 @CRM-635-07 @muhabbat @tm-regression @ui-tm
  Scenario: Cancel button functionality on Add User page when no data is entered
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
    And I click on add new user button on add new user page
    And I should be navigated to User Details page
    And I click on Cancel button on Add User Page
    Then I am navigated back to User List Page

    #Refactored Vidya 02-03-2020
  @CRM-635-08 @CRM-635 @vinuta @tm-regression @ui-tm
  Scenario Outline: User is created successfully with Inactive status & verify we can create duplicate user
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
    And I will make sure that user by <MaxID> is not there for this project
    And I click on add new user button on add new user page
    And I create User with given data <isMaxEmp>,<MaxID>,<FN>,<MN>,<LN>,<Email>,<Start Date>,<End Date>,<Acct Type>,<Acct Auth>,<Auth Date>,<Override Auth>,<Override Auth Reason>,<PHI Access>,<PHI Reason>,<PII Access>,<PII Reason>,<Status>,<Role>
    And I click on save a user button
    And I click on yes button on warning message for selected role for user
    Then I should see a pop-up that user is created successfully
    When I enter search criteria for a user by "maxID" value <MaxID>
    And I enter search criteria for a user by "status" value <Status>
    And I click on Search Button on User List Page
    And I click on first user to open User Details
    Then I verify values in account inactivation "reason" dropdown
      | Change of Job Functions |
    And I set Inactive Immediately to Yes
    Then I should see pop-up with message "The user account will be deactivated when you click the Save button"
    And I click on ok button to inactivate user
    And I click on save a user button
    Then I should see message that user has been inactivated successfully
    And I click on add new user button on add new user page
    And I create User with given data <isMaxEmp>,<MaxID>,<FN>,<MN>,<LN>,<Email>,<Start Date>,<End Date>,<Acct Type>,<Acct Auth>,<Auth Date>,<Override Auth>,<Override Auth Reason>,<PHI Access>,<PHI Reason>,<PII Access>,<PII Reason>,<Status>,<Role>
    And I click on save a user button
    And I click on yes button on warning message for selected role for user
    Then I should see a pop-up that user is created successfully
    And I enter search criteria for a user by "maxID" value <MaxID>
    And I click on Search Button on User List Page
    And I should see all users with "status" value <Status>
    Examples:
      | isMaxEmp | MaxID    | FN | MN | LN | Email | Start Date | End Date | Acct Type | Acct Auth | Auth Date | Override Auth | Override Auth Reason | PHI Access | PHI Reason | PII Access | PII Reason | Status   | Role  |
      | 'Yes'    | '324099' | '' | '' | '' | ''    | ''         | ''       | ''        | ''        | ''        | ''            | ''                   | ''         | ''         | ''         | ''         | 'Active' | 'Csr' |


    #Refactored Vidya 02-03-2020
  @CRM-635-09 @CRM-635 @muhabbat @tm-regression @ui-tm
  Scenario Outline: User is created successfully with Active status & Verify duplicate active user cannot be created
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
   # And I will make sure that user by <MaxID> is not there for this project
   # And I click on add new user button on add new user page
   # And I create User with given data <isMaxEmp>,<MaxID>,<FN>,<MN>,<LN>,<Email>,<Start Date>,<End Date>,<Acct Type>,<Acct Auth>,<Auth Date>,<Override Auth>,<Override Auth Reason>,<PHI Access>,<PHI Reason>,<PII Access>,<PII Reason>,<Status>,<Role>
   # And I click on save a user button
   # And I click on yes button on warning message for selected role for user
   # Then I should see a pop-up that user is created successfully
    And I click on add new user button on add new user page
    And I create User with given data <isMaxEmp>,<MaxID>,<FN>,<MN>,<LN>,<Email>,<Start Date>,<End Date>,<Acct Type>,<Acct Auth>,<Auth Date>,<Override Auth>,<Override Auth Reason>,<PHI Access>,<PHI Reason>,<PII Access>,<PII Reason>,<Status>,<Role>
    And I click on save a user button
    And I click on yes button on warning message for selected role for user
    Then I should see error that user is already active
    And Wait for 5 seconds
    And I click on Cancel button on Add User Page
    And I see "All changes will be lost" alert displayed
    Then I click on Yes and I am navigated back to User List Page
    And I enter search criteria for a user by "maxID" value <MaxID>
    And I click on Search Button on User List Page
    And I should see all users with "status" value <Status>
    Examples:
      | isMaxEmp | MaxID    | FN | MN | LN | Email | Start Date | End Date | Acct Type | Acct Auth | Auth Date | Override Auth | Override Auth Reason | PHI Access | PHI Reason | PII Access | PII Reason | Status   | Role  |
      | 'Yes'    | '451221' | '' | '' | '' | ''    | ''         | ''       | ''        | ''        | ''        | ''            | ''                   | ''         | ''         | ''         | ''         | 'Active' | 'Csr' |

  #refactored 03/14 Vinuta - added examples section
  @CRM-635 @CRM-635-10 @muhabbat @tm-regression @ui-tm
  Scenario Outline: Entered Dates Validation
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
    And I click on add new user button on add new user page
    And I set maersk employee to yes
    And I enter max id "200341"
    And I click on add max id button
    And I enter the Start Date prior to the date of creation
    And I click on save a user button
    And I see "The Start date cannot be in the past" message under the "start" field
    When I enter the End Date "<end date>" to the Start Start
    And I click on save a user button
    And I see "<message>" message under the "<end date>" field

    Examples:
      | end date | message                                                      |
      | prior    | The End Date must be greater than or equal to the Start Date |
      | equal    | The End Date must be greater than or equal to the Start Date |


  #@CRM-1509 @CRM-1509-01 @shruti @tm-regression @ui-tm
  #Added to script @CP-28407-01
  Scenario: Verify user is not navigated to project list page after adding account manager & approver
    Given I logged into Tenant Manager Project list page
    When I expend a Project to view the details
    And I verify "Add Contact Details" button is displayed & click on it
    And I add Account Manager and Approver roles to new project
    Then I verify user is not navigated to Project List Page

  #@CRM-635-11 @CRM-635 @tm-regression @ui-tm @tm-smoke @Vidya
  #Covered in script @CRM-635-09
  Scenario Outline: User is created successfully with Active status
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
    And I will make sure that user by <MaxID> is not there for this project
    And I click on add new user button on add new user page
    And I create User with given data <isMaxEmp>,<MaxID>,<FN>,<MN>,<LN>,<Email>,<Start Date>,<End Date>,<Acct Type>,<Acct Auth>,<Auth Date>,<Override Auth>,<Override Auth Reason>,<PHI Access>,<PHI Reason>,<PII Access>,<PII Reason>,<Status>,<Role>
    And I click on save a user button
    And I click on No button on warning message for selected role for user
    Then I verify user is on same page and see all previously entered unsaved data
    And I click on save a user button
    And I click on yes button on warning message for selected role for user
    Then I should see a pop-up that user is created successfully
    Examples:
      | isMaxEmp | MaxID   | FN | MN | LN | Email | Start Date | End Date | Acct Type | Acct Auth | Auth Date | Override Auth | Override Auth Reason | PHI Access | PHI Reason | PII Access | PII Reason | Status   | Role  |
      | 'Yes'    | '86295' | '' | '' | '' | ''    | ''         | ''       | ''        | ''        | ''        | ''            | ''                   | ''         | ''         | ''         | ''         | 'Active' | 'Csr' |

  #@CRM-635-12 @CRM-635 @tm-regression @ui-tm @Vidya
    #Covered in script @CRM-635-09
  Scenario Outline: User is created successfully with Active status
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
    And I will make sure that user by <MaxID> is not there for this project
    And I click on add new user button on add new user page
    And I create User with given data <isMaxEmp>,<MaxID>,<FN>,<MN>,<LN>,<Email>,<Start Date>,<End Date>,<Acct Type>,<Acct Auth>,<Auth Date>,<Override Auth>,<Override Auth Reason>,<PHI Access>,<PHI Reason>,<PII Access>,<PII Reason>,<Status>,<Role>
    And I click on save a user button
    Then I should see a pop-up that user is created successfully
    Examples:
      | isMaxEmp | MaxID   | FN | MN | LN | Email | Start Date | End Date | Acct Type | Acct Auth | Auth Date | Override Auth | Override Auth Reason | PHI Access | PHI Reason | PII Access | PII Reason | Status   | Role |
      | 'Yes'    | '86295' | '' | '' | '' | ''    | ''         | ''       | ''        | ''        | ''        | ''            | ''                   | ''         | ''         | ''         | ''         | 'Active' | ''   |


  #@CP-10355-01 @CP-10355 @vidya @tm-regression @ui-tm @ui-tm
    #Covered in script @CRM-635-08
  Scenario Outline: User is created successfully with Inactive status & verify we can create duplicate user
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
    And I will make sure that user by <MaxID> is not there for this project
    And I click on add new user button on add new user page
    And I create User with given data <isMaxEmp>,<MaxID>,<FN>,<MN>,<LN>,<Email>,<Start Date>,<End Date>,<Acct Type>,<Acct Auth>,<Auth Date>,<Override Auth>,<Override Auth Reason>,<PHI Access>,<PHI Reason>,<PII Access>,<PII Reason>,<Status>,<Role>
    And I click on save a user button
    And I click on yes button on warning message for selected role for user
    Then I should see a pop-up that user is created successfully
    Examples:
      | isMaxEmp | MaxID   | FN | MN | LN | Email | Start Date | End Date | Acct Type | Acct Auth | Auth Date | Override Auth | Override Auth Reason | PHI Access | PHI Reason | PII Access | PII Reason | Status   | Role  |
      | 'Yes'    | '52987' | '' | '' | '' | ''    | ''         | ''       | ''        | ''        | ''        | ''            | ''                   | ''         | ''         | ''         | ''         | 'Active' | 'Csr' |


  @CP-10317 @CP-10317-1 @kamil @tm-regression
  Scenario:Verify when saving role First name & Start date are required all other fields are not editable
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
    And I click on add new user button on add new user page
    And I select Account Type as System
    Then Verify when Account Type is System, other fields are not editable
    Then I entering FirstName and StartDate
    And I click on save a user button

#Feature: Search User page in a project, including 'Add' User option, 'Remove' User option, ' Inactivate' User option

  #@CRM-637-11 @CRM-637 @CRM-628 @vinuta @tm-regression @ui-tm
  #Merged checking inactivation end date with @CRM-637-09 to avoid multiple data creation
  Scenario Outline: Validate End date is set to current date & is not editable when inactivate immediately is selected
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on User List Menu
    And I click on add new user button
    And I create User with given data <isMaxEmp>,<MaxID>,<FN>,<MN>,<LN>,<Email>,<Start Date>,<End Date>,<Acct Type>,<Acct Auth>,<Auth Date>,<Override Auth>,<Override Auth Reason>,<PHI Access>,<PHI Reason>,<PII Access>,<PII Reason>,<Status>,<Role>
    When I click on save a user button
    Then I should see a pop-up that user is created successfully
    And I enter search criteria for a user by "email" value <Email>
    And I enter search criteria for a user by "status" value <Status>
    And I click on Search Button on User List Page
    Then I should see all users with "status" value <Status>
    When I click on first user to open User Details
    And I set Inactive Immediately to Yes
    Then I should see pop-up with message "The user account will be deactivated when you click the Save button"
    When I click on ok button to inactivate user
    Then I verify end date is set to current date & not editable
    Examples:
      | isMaxEmp | MaxID    | FN | MN | LN | Email                                 | Start Date | End Date | Acct Type | Acct Auth | Auth Date | Override Auth | Override Auth Reason | PHI Access | PHI Reason | PII Access | PII Reason | Status   | Role  |
      | 'Yes'    | '276121' | '' | '' | '' | 'JosephAbner@maersk.com@maersk.com' | ''         | ''       | ''        | ''        | ''        | ''            | ''                   | ''         | ''         | ''         | ''         | 'Active' | 'Csr' |

  @CRM-637-09 @CRM-637 @CRM-628 @vinuta @tm-regression @ui-tm @tm-smoke
  Scenario Outline: Validate account inactivation reason dropdown values & inactivate user immediately, remove user
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on User List Menu
    And I click on add new user button
    And I create User with given data <isMaxEmp>,<MaxID>,<FN>,<MN>,<LN>,<Email>,<Start Date>,<End Date>,<Acct Type>,<Acct Auth>,<Auth Date>,<Override Auth>,<Override Auth Reason>,<PHI Access>,<PHI Reason>,<PII Access>,<PII Reason>,<Status>,<Role>
    When I click on save a user button
    And I click on yes button on warning message for selected role for user
    Then I should see a pop-up that user is created successfully
    Then I enter search criteria for a user by "first name" value <FN>
    Then I enter search criteria for a user by "last name" value <LN>
    And I enter search criteria for a user by "status" value <Status>
    And I click on Search Button on User List Page
    Then I should see all users with "status" value <Status>
    And I click on first user to open User Details
    Then I verify values in account inactivation "reason" dropdown
      | Change of Job Functions |
      | Leave (Other)           |
      | Parental Leave          |
      | Resigned                |
      | Terminated              |
    And I set Inactive Immediately to Yes
    Then I should see pop-up with message "The user account will be deactivated when you click the Save button"
    And I click on ok button to inactivate user
    Then I verify end date is set to current date & not editable
    And I click on save a user button
    Then I should see message that user has been inactivated successfully
    Then I enter search criteria for a user by "first name" value <FN>
    Then I enter search criteria for a user by "last name" value <LN>
    And I enter search criteria for a user by "status" value "Inactive"
    And I click on Search Button on User List Page
    Then I should see all users with "email" value <Email>
    And I should see all users with "status" value "Inactive"
    When I click on select all checkbox
    Then I can select only Inactive users but not active users
    And  I click on Remove User button
    Then I should not see user with "email" <Email> on User List page
    Examples:
      | isMaxEmp | MaxID    | FN       | MN | LN         | Email                        | Start Date | End Date | Acct Type | Acct Auth | Auth Date | Override Auth | Override Auth Reason | PHI Access | PHI Reason | PII Access | PII Reason | Status   | Role  |
      | 'Yes'    | '445200' | 'Elshan' | '' | 'Bairamov' | 'elshanbairamov@maersk.com' | ''         | ''       | ''        | ''        | ''        | ''            | ''                   | ''         | ''         | ''         | ''         | 'Active' | 'Csr' |

  #@CRM-628 @CRM-634 @CRM-634-01 @CRM-634-02 @CRM-634-03 @vinuta @tm-regression @ui-tm @tm-smoke
  #Merging with inactivation script above to avoid multiple data creation
  Scenario Outline: Remove users from project
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on User List Menu
    When I click on User List Menu
    And I click on add new user button
    And I create User with given data <isMaxEmp>,<MaxID>,<FN>,<MN>,<LN>,<Email>,<Start Date>,<End Date>,<Acct Type>,<Acct Auth>,<Auth Date>,<Override Auth>,<Override Auth Reason>,<PHI Access>,<PHI Reason>,<PII Access>,<PII Reason>,<Status>,<Role>
    When I click on save a user button
    Then I should see a pop-up that user is created successfully
    When I enter search criteria for a user by "email" value <Email>
    And I enter search criteria for a user by "status" value <Status>
    And I click on Search Button on User List Page
    Then I should see all users with "status" value <Status>
    And I click on first user to open User Details
    Then I verify values in account inactivation "reason" dropdown
      | Change of Job Functions |
      | Leave (Other)           |
      | Parental Leave          |
      | Resigned                |
      | Terminated              |
    And I set Inactive Immediately to Yes
    Then I should see pop-up with message "The user account will be deactivated when you click the Save button"
    And I click on ok button to inactivate user
    And I click on save a user button
    Then I should see message that user has been inactivated successfully
    Then I enter search criteria for a user by "email" value <Email>
    And I enter search criteria for a user by "status" value "Inactive"
    And I click on Search Button on User List Page
    Then I should see all users with "email" value <Email>
    And I should see all users with "status" value "Inactive"
    When I click on select all checkbox
    Then I can select only Inactive users but not active users
    And  I click on Remove User button
    Then I should not see user with "email" <Email> on User List page
    Examples:
      | isMaxEmp | MaxID             | FN | MN | LN | Email                         | Start Date | End Date | Acct Type | Acct Auth | Auth Date | Override Auth | Override Auth Reason | PHI Access | PHI Reason | PII Access | PII Reason | Status   | Role  |
      | 'Yes'    | 'SVC_mars_user_5' | '' | '' | '' | 'serviceaccount5@maersk.com' | ''         | ''       | ''        | ''        | ''        | ''            | ''                   | ''         | ''         | ''         | ''         | 'Active' | 'Csr' |

  @CRM-628 @CRM-638 @CRM-638-01 @CRM-638-04 @vinuta @tm-regression @ui-tm @tm-smoke
  Scenario Outline: Reactivate a user immediately
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on User List Menu
    And I click on add new user button
    And I create User with given data <isMaxEmp>,<MaxID>,<FN>,<MN>,<LN>,<Email>,<Start Date>,<End Date>,<Acct Type>,<Acct Auth>,<Auth Date>,<Override Auth>,<Override Auth Reason>,<PHI Access>,<PHI Reason>,<PII Access>,<PII Reason>,<Status>,<Role>
    And I click on save a user button
    And I click on yes button on warning message for selected role for user
    Then I should see a pop-up that user is created successfully
    And I enter search criteria for a user by "email" value <Email>
    And I enter search criteria for a user by "status" value <Status>
    And I click on Search Button on User List Page
    Then I should see all users with "status" value <Status>
    And I click on first user to open User Details
    And I set Inactive Immediately to Yes
    Then I should see pop-up with message "The user account will be deactivated when you click the Save button"
    And I click on ok button to inactivate user
    And I select value "Resigned" in account inactivation dropdown
    And I click on save a user button
    Then I should see message that user has been inactivated successfully
    And I enter search criteria for a user by "email" value <Email>
    And I click on Search Button on User List Page
    And I click on first user to open User Details
    Then I verify values in account reactivation "reason" dropdown
      | Change of Job Functions      |
      | Returned from Leave (Other)  |
      | Returned from Parental Leave |
      | Rehire                       |
    And I click on save a user button
    #Then I see the error that End date must be empty to reactivate a user ---- updated as per CP-34631
    #And I enter the End Date "equal" to the Start Start
    #And I nullify the end date
    #And I click on save a user button
    Then I should see message that user has been reactivated successfully
    And I enter search criteria for a user by "email" value <Email>
    And I enter search criteria for a user by "status" value <Status>
    And I click on Search Button on User List Page
    Then I should see all users with "status" value <Status>
    Examples:
      | isMaxEmp | MaxID    | FN       | MN | LN         | Email                        | Start Date | End Date | Acct Type | Acct Auth | Auth Date | Override Auth | Override Auth Reason | PHI Access | PHI Reason | PII Access | PII Reason | Status   | Role  |
      | 'Yes'    | '445200' | 'Elshan' | '' | 'Bairamov' | 'elshanbairamov@maersk.com' | ''         | ''       | ''        | ''        | ''        | ''            | ''                   | ''         | ''         | ''         | ''         | 'Active' | 'Csr' |

#Feature: User Role in Tenant Manager

  @CP-502 @CP-502-01 @paramita @tm @tm-regression @ui-tm
  Scenario: Verify only ACTIVE Role are displaying in User ROLE dropdown list
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role page
    When I click on User List Menu
    And I click on add new user button
    And I scroll down and click on add role button on Add User page
    Then I verify only active roles are displaying in User Role dropdown
    Then I verify only active roles are displaying sorted in ascending order in User Role dropdown


  #@CP-502 @CP-502-02 @paramita @tm @tm-regression @ui-tm
  #Covered in CP-502-01
  Scenario:Verify Active Role are displaying sorted in ascending order in User ROLE dropdown list
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role page
    When I click on User List Menu
    And I click on add new user button
    And I scroll down and click on User Role section
    Then I verify only active roles are displaying sorted in ascending order in User Role dropdown

  @CP-502 @CP-502-03 @paramita @tm @tm-regression @ui-tm
  Scenario:Verify Role description populates automatically on select Role from User ROLE dropdown list
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role page
    When I click on User List Menu
    And I click on add new user button
    And I scroll down and select User Role "Csr" from User ROLE dropdown list,then Role description populates automatically
    Then I verify Role description field is not editable

  @CP-502 @CP-502-04 @CP-25943-01 @paramita @tm @tm-regression @ui-tm
  Scenario Outline:Verify Multiple User Roles can be added at one time
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role page
    When I click on User List Menu
    And I click on add new user button
    And I create User with given data <isMaxEmp>,<MaxID>,<FN>,<MN>,<LN>,<Email>,<Start Date>,<End Date>,<Acct Type>,<Acct Auth>,<Auth Date>,<Override Auth>,<Override Auth Reason>,<PHI Access>,<PHI Reason>,<PII Access>,<PII Reason>,<Status>,<Role>
    And I scroll down and select "2" User Role from User ROLE dropdown list
    And I verify user has default user role assigned
    And I click on save a user button
    And I click on yes button on warning message for selected role for user
    Then I should see a pop-up that user is created successfully
    Then I verify Multiple User Roles can be added at one time
    Examples:
      | isMaxEmp | MaxID | FN | MN | LN | Email | Start Date | End Date | Acct Type | Acct Auth | Auth Date | Override Auth | Override Auth Reason | PHI Access | PHI Reason | PII Access | PII Reason | Status   | Role  | eventName       | correlationId             | projectName |
      | 'No'     | ''    | '' | '' | '' | ''    | ''         | ''       | ''        | ''        | ''        | ''            | ''                   | ''         | ''         | ''         | ''         | 'Active' | 'Csr' | USER_SAVE_EVENT | ismaerskInternalEmployee |[blank]|


  @CP-502 @CP-502-05 @paramita @tm @tm-regression @ui-tm
  Scenario:Verify Roles already assigned to the User will not display in User Role dropdown list
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role page
    When I click on User List Menu
    And I click on add new user button
    And I scroll down and click on add role button on Add User page
    And I scroll down and assigned a User with role "Csr" value
    Then I verify role "Csr" already assigned to the User will not display in User Role dropdown list

  @CP-502 @CP-502-06 @paramita @tm @tm-regression @ui-tm
  Scenario:Verify Unsaved assigned User Role can be deleted
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role page
    When I click on User List Menu
    And I click on add new user button
    And I scroll down and click on add role button on Add User page
    And I scroll down and assigned a User with role "Csr" value
    Then I can delete the same role that is not saved

  @CP-502 @CP-502-07 @paramita @tm @tm-regression @ui-tm
  Scenario:Verify Validation message when roles End Date is less than roles Start Date
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role page
    When I click on User List Menu
    And I click on add new user button
    And I scroll down and click on add role button on Add User page
    And I scroll down and assigned a User with role "Csr" value
    And I enter Role's End date less than Role's Start date
    Then I see "The End Date must be greater than or equal to the Start Date" message under the roles "EndDate" field

  @CP-502 @CP-502-08 @paramita @tm @tm-regression @ui-tm
  Scenario: Verify Warning message display when user try to navigate away with unsaved data in User role add page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role page
    When I click on User List Menu
    And I click on add new user button
    And I scroll down and click on add role button on Add User page
    And I scroll down and assigned a User with role "Csr" value
    When I click on Cancel button on Add User Page
    Then I verify warning message and warning text is displayed with Continue and Cancel button
    And I click on Cancel option on warning message
    And I should remain on the same page and information should not get saved
    When I click on the back arrow button
    Then I verify warning message and warning text is displayed with Continue and Cancel button
    And I click on Cancel option on warning message
    And I should remain on the same page and information should not get saved
    When I click on User List Menu
    Then I verify warning message and warning text is displayed with Continue and Cancel button
    And I click on Continue option on warning message
    Then I am navigated back to User List Page

  #@CP-502 @CP-502-09 @paramita @tm @tm-regression @ui-tm
  #Covered in above script
  Scenario Outline:Verify user remain on same page and information should not get saved on click Cancel button in Warning message
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role page
    When I click on User List Menu
    And I click on add new user button
    And I scroll down and click on add role button on Add User page
    And I scroll down and assigned a User with role "Csr" value
    When I click on available "<Button>"
    Then I verify warning message and warning text is displayed with Continue and Cancel button
    When I click on Cancel option on warning message
    Then I should remain on the same page and information should not get saved
    Examples:
      | roleName | roleDesc | startDate | endDate | Button     |
      | {random} | {random} | today     | +1      | Cancel     |
      |[blank]|          |[blank]|         | Back Arrow |
      |[blank]|          |[blank]|         | User Icon  |

  #@CP-502 @CP-502-10 @paramita @tm @tm-regression @ui-tm
  #Covered in above script
  Scenario Outline:Verify user redirect to UserList screen on click Continue button in Warning message
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role page
    When I click on User List Menu
    And I click on add new user button
    And I scroll down and assigned a User with role "Csr" value
    When I click on available "<Button>"
    Then I verify warning message and warning text is displayed with Continue and Cancel button
    When I click on Continue option on warning message
    Then I should redirect to User List Screen
    Examples:
      | roleName | roleDesc | startDate | endDate | Button     |
      | {random} | {random} | today     | +1      | Cancel     |
      |[blank]|          |[blank]|         | Back Arrow |
      |[blank]|          |[blank]|         | User Icon  |

  @CP-502 @CP-502-11 @paramita @tm @tm-regression @ui-tm
  Scenario:Verify mandatory field validation in User Role section
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role page
    When I click on User List Menu
    And I click on add new user button
    And I scroll down and click on add role button on Add User page
    And I click on save a user button
    Then I see "The Default RoleType type is required and cannot be left blank" message under the roles "Select Role" field
    And I see "The Start date is required and cannot be left blank." message under the roles "Start Date" field

  @CP-502 @CP-502-12 @paramita @tm @tm-regression @ui-tm
  Scenario:Verify User Role End Date and User Role Description fields are optional
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role page
    When I click on User List Menu
    And I click on add new user button
    And I scroll down and click on add role button on Add User page
    And I click on save a user button
    Then I verify user-role end date and user-role description are optional

  @CP-502 @CP-502-13 @paramita @tm @tm-regression @ui-tm
  Scenario Outline:Verify No delete/thrash icon is not available for Saved User Role when START DATE <= Current Date
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role page
    When I click on User List Menu
    And I select an existing user record with "<status>" and scroll to User Role section
    Then I verify no delete icon is showing for a saved user role when START DATE less than Current Date
    Examples:
      | roleName | roleDesc | startDate | endDate | status |
      | {random} | {random} | today     | +1      | ACTIVE |

  @CP-502 @CP-502-14 @CP-25943-02 @paramita @tm @tm-regression @ui-tm
  Scenario Outline:Verify User Role End Date is editable for Saved User Role when START DATE >= Current Date
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role page
    When I click on User List Menu
    And I select an existing user record with "<status>" and scroll to User Role section
    And I verify user has default user role assigned
    Then I verify Role End Date is updated for Saved User Role with update success message
    Examples:
      | roleName | roleDesc | startDate | endDate | status |
      | {random} | {random} | today     | +1      | ACTIVE |

  @CP-502 @CP-502-15 @paramita @tm @tm-regression @ui-tm
  Scenario Outline:Verify delete/thrash icon is available for Saved User Role when START DATE > Current Date
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role page
    When I click on User List Menu
    And I select an existing user record with "<status>" and scroll to User Role section
    And I check for existing Future role,if not add a new role with Start date greater than today's date and saved the role
    Then I verify delete icon is showing for a saved user role when START DATE greater than Today's Date
    Examples:
      | roleName | roleDesc | startDate | endDate | status |
      | {random} | {random} | today     | +1      | ACTIVE |

  @CP-502 @CP-502-16 @CP-25943-04 @paramita @tm @tm-regression @ui-tm
  Scenario Outline:Verify Future User Role is succesfully deleted and not displaying for an existing user
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role page
    When I click on User List Menu
    And I select an existing user record with "<status>" and scroll to User Role section
    And I verify user has default user role assigned
    And I check for existing Future role,if not add a new role with Start date greater than today's date and saved the role
    Then I verify delete icon is showing for a saved user role when START DATE greater than Today's Date
    When I delete the Future Role
    And I click on User Role Save button
    Then I see update success message "User Updated Successfully"
    And I should be navigate back to the User List screen
    When I select an existing user record with "<status>" and scroll to User Role section
    Then I verify selected Future role which is deleted is not displaying
    Examples:
      | roleName | roleDesc | startDate | endDate | status |
      | {random} | {random} | today     | +1      | ACTIVE |

  @CP-502 @CP-502-17 @CP-25943-02 @paramita @tm @tm-regression @ui-tm
  Scenario Outline:Verify User Role is updated sucessfully
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role page
    When I click on User List Menu
    And I select an existing user record with "<status>" and scroll to User Role section
    And I verify user has default user role assigned
    And I select existing role and update with another role value
    And I click on User Role Save button
    Then I see update success message "User Updated Successfully"
    And I should be navigate back to the User List screen
    Examples:
      | roleName | roleDesc | startDate | endDate | status |
      | {random} | {random} | today     | +1      | ACTIVE |

  @CP-9376 @CP-9376-01 @CP-25943-04 @vinuta @tm @tm-regression @ui-tm
  Scenario Outline: Verify User Role is required field when creating a new user
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on User List Menu
    And I click on add new user button
    And I create User with given data "<isMaxEmp>","<maxID>","<Start Date>","<End Date>"
    When I click on save a user button
    Then I verify user role mandatory error is displayed
    And I verify user is on same page and see all previously entered unsaved data
    Examples:
      | isMaxEmp | maxID  | Start Date | End Date |
      | Yes      | 324099 |[blank]|          |

  @CP-21406 @CP-21406-01 @mital @tm @tm-regression @ui-tm
  Scenario: Verify required fields for adding Non maersk Users
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on User List Menu
    And I click on add new user button
    And I select Account Type as Individual Non-maersk
    And I click on save a user button
    And I see mandatory fields have error messages
      | First Name    |
      | Last Name     |
      | Email Address |
      | Start Date    |


  @CP-21406 @CP-21406-02 @mital @tm @tm-regression @ui-tm
  Scenario Outline: Verify successful user creation of type Non maersk user
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on User List Menu
    And I click on add new user button
    And I select Account Type as Individual Non-maersk
    And I create User with given data <isMaxEmp>,<MaxID>,<FN>,<MN>,<LN>,<Email>,<Start Date>,<End Date>,<Acct Type>,<Acct Auth>,<Auth Date>,<Override Auth>,<Override Auth Reason>,<PHI Access>,<PHI Reason>,<PII Access>,<PII Reason>,<Status>,<Role>
    And I click on save a user button
    And I click on yes button on warning message for selected role for user
    Then I should see a pop-up that user is created successfully
    Examples:
      | isMaxEmp | MaxID | FN | MN | LN | Email | Start Date | End Date | Acct Type | Acct Auth | Auth Date | Override Auth | Override Auth Reason | PHI Access | PHI Reason | PII Access | PII Reason | Status   | Role  |
      | 'No'     | ''    | '' | '' | '' | ''    | ''         | ''       | ''        | ''        | ''        | ''            | ''                   | ''         | ''         | ''         | ''         | 'Active' | 'Csr' |

  @CP-21406 @CP-21406-03 @mital @tm @tm-regression @ui-tm
  Scenario Outline: Verify cannot create duplicate user of type Non maersk User
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on User List Menu
    And I click on add new user button
    And I select Account Type as Individual Non-maersk
    And I create User with given data <isMaxEmp>,<MaxID>,<FN>,<MN>,<LN>,<Email>,<Start Date>,<End Date>,<Acct Type>,<Acct Auth>,<Auth Date>,<Override Auth>,<Override Auth Reason>,<PHI Access>,<PHI Reason>,<PII Access>,<PII Reason>,<Status>,<Role>
    And I click on save a user button
    And I click on yes button on warning message for selected role for user
    Then I should see a pop-up that user is created successfully
    And I click on add new user button
    And I select Account Type as Individual Non-maersk
    And I create User with duplicate emailID <isMaxEmp>,<MaxID>,<FN>,<MN>,<LN>,<Email>,<Start Date>,<End Date>,<Acct Type>,<Acct Auth>,<Auth Date>,<Override Auth>,<Override Auth Reason>,<PHI Access>,<PHI Reason>,<PII Access>,<PII Reason>,<Status>,<Role>
    And I click on save a user button
    And I click on yes button on warning message for selected role for user
    Then I should see error that user is already active
    Examples:
      | isMaxEmp | MaxID | FN | MN | LN | Email | Start Date | End Date | Acct Type | Acct Auth | Auth Date | Override Auth | Override Auth Reason | PHI Access | PHI Reason | PII Access | PII Reason | Status   | Role  |
      | 'No'     | ''    | '' | '' | '' | ''    | ''         | ''       | ''        | ''        | ''        | ''            | ''                   | ''         | ''         | ''         | ''         | 'Active' | 'Csr' |

  @CP-25943 @CP-25943-04 @mital @tm @tm-regression @ui-tm
  Scenario:Verify Default indicator is visible when I view a User’s details
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role page
    When I click on User List Menu
    And I enter search criteria for a user by "status" value "Inactive"
    And I click on Search Button on User List Page
    Then I should see all users with "status" value "Inactive"
    And I click on first user to open User Details
    And I verify user has default user role assigned

  @CP-25943 @CP-25943-05 @mital @CP-25943-01 @paramita @tm @tm-regression @ui-tm
  Scenario Outline:Verify changing the default role assignment for user to a different actively associated role
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project role page
    When I click on User List Menu
    And I click on add new user button
    And I create User with given data <isMaxEmp>,<MaxID>,<FN>,<MN>,<LN>,<Email>,<Start Date>,<End Date>,<Acct Type>,<Acct Auth>,<Auth Date>,<Override Auth>,<Override Auth Reason>,<PHI Access>,<PHI Reason>,<PII Access>,<PII Reason>,<Status>,<Role>
    And I scroll down and select "2" User Role from User ROLE dropdown list
    And I verify user has default user role assigned
    And I verify that I can select another role as default
    And I click on save a user button
    And I click on yes button on warning message for selected role for user
    Then I should see a pop-up that user is created successfully
    Examples:
      | isMaxEmp | MaxID | FN | MN | LN | Email | Start Date | End Date | Acct Type | Acct Auth | Auth Date | Override Auth | Override Auth Reason | PHI Access | PHI Reason | PII Access | PII Reason | Status   | Role  | eventName       | correlationId             | projectName |
      | 'No'     | ''    | '' | '' | '' | ''    | ''         | ''       | ''        | ''        | ''        | ''            | ''                   | ''         | ''         | ''         | ''         | 'Active' | 'Csr' | USER_SAVE_EVENT | ismaerskInternalEmployee |[blank]|

