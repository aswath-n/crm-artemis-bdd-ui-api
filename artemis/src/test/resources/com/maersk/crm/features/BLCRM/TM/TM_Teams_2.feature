Feature: Tenant Manager - Teams Functionality

@CP-800 @CP-800-03 @vidya @tm-regression @ui-tm
Scenario Outline: Verification of description and end date fields are optional
Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
When I navigate to business unit list  page
And I check whether it has business unit, if not, I will create business unit "<buName>","<buDesc>","<buStartDate>","<buEndDate>"
When I navigate to team details page
When I populate data on team details page "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
And I click on Save button on team details page
And I navigate to newly created team
Then I verify Team List columns Values
Examples:
| buName   | buDesc | buStartDate | buEndDate | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
| {random} |[blank]| today       | +5        | {random} |[blank]| today         |[blank]|        |

@CP-800 @CP-800-04 @vidya @tm-regression @ui-tm
Scenario Outline: Verification of Business Unit Status value
Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
When I navigate to business unit list  page
And I check whether it has business unit, if not, I will create business unit "<buName>","<buDesc>","<buStartDate>","<buEndDate>"
When I navigate to team details page
When I populate data on team details page "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
And I click on Save button on team details page
And I navigate to newly created team
Then I verify team list status as "<status>"
Examples:
| buName   | buDesc | buStartDate | buEndDate | teamName | teamDesc | teamStartDate | teamEndDate | teamBU | status |
| {random} |[blank]| today       | +5        | {random} | {random} | today         |[blank]|        | Active |
| {random} |[blank]| today       | +5        | {random} | {random} | today         | today       |[blank]| Active |
| {random} |[blank]| today       | +5        | {random} | {random} | today         | +2          |[blank]| Active |
| {random} |[blank]| today       | +5        | {random} | {random} | +1            | +1          |[blank]| FUTURE |
| {random} |[blank]| today       | +5        | {random} | {random} | +2            |[blank]|        | FUTURE |
| {random} |[blank]| today       | +5        | {random} | {random} | +1            | +2          |[blank]| FUTURE |

@CP-800 @CP-800-05 @vidya @tm-regression @ui-tm
Scenario Outline: Validate Navigate away and Back Arrow without doing any action
Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
When I navigate to team list page
And I click on "<Button>" on team list page
Then I verify it is display "<Page>" page
Examples:
| Button     | Page                 |
| Back Arrow | Project Details Page |
| BU Icon    | BU List page         |

@CP-800 @CP-800-06 @vidya @tm-regression @ui-tm
Scenario: Verification of Team List sorting order
Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
When I navigate to team list page
And I check weather it as 5 team records if not I add 5 team records
Then I verify team records are in according to sorted order

# This feature file has test cases for Edit Team -Update Supervisor functionality
#Feature: Tenant Manager Edit Team -Update Supervisor

@CP-2408 @CP-2408-01 @shruti @tm-regression @ui-tm
Scenario Outline: Verify Edit icon is displayed on user contact card to check or uncheck supervisor
Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
When I click on Team icon from left menu bar
And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
And I verify Add User button on User List section is enabled
When I click on Add User button
And I select any dropdown value other remaining fields get autopopulated
When I click on Add User button second time
And I select any dropdown value other remaining fields get autopopulated
And I click on team user save button
    #Then I see multiple user is associated with a Team
When I click on Edit icon on a User contact card
Then I verify the supervisor check box is available to update
Examples:
| teamName | teamDesc | teamStartDate | teamEndDate | teamBU | status |
| {random} | {random} | today         | +6          |[blank]| ACTIVE |

@CP-2408 @CP-2408-02 @shruti @tm-regression @ui-tm
Scenario Outline: Verify Contact Card Update is saved sucessfully
Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
When I click on Team icon from left menu bar
And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
And I verify Add User button on User List section is enabled
When I click on Add User button
And I select any dropdown value other remaining fields get autopopulated
When I click on Add User button second time
And I select any dropdown value other remaining fields get autopopulated
And I click on team user save button
    #Then I see multiple user is associated with a Team
When I click on Edit icon on a User contact card
And  I update the user by checking or unchecking the checkbox
And I click on Save checkmark on the Contact Card for the User
Then I verify the update is reflected on the Contact card
Examples:
| teamName | teamDesc | teamStartDate | teamEndDate | teamBU | status |
| {random} | {random} | today         | +6          |[blank]| ACTIVE |

@CP-2408 @CP-2408-03 @shruti @tm-regression @ui-tm
Scenario Outline: Verify only 1 user is designated as Supervisor at a time
Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
When I click on Team icon from left menu bar
And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
And I verify Add User button on User List section is enabled
When I click on Add User button
And I select any dropdown value other remaining fields get autopopulated
When I click on Add User button second time
And I select any dropdown value other remaining fields get autopopulated
And I click on team user save button
    #Then I see multiple user is associated with a Team
Given Supervisor checkbox is selected for user "1"
When I check the supervisor check box for a non supervisor user
Then I verify non supervisor become supervisor and supervisor become non supervisor
Examples:
| teamName | teamDesc | teamStartDate | teamEndDate | teamBU | status |
| {random} | {random} | today         | +6          |[blank]| ACTIVE |

#Feature: Edit Team - Add or Remove Users in Team User List verify all UI functions

@CP-2407 @CP-2407-01 @Umid @ui-tm @tm-regression
Scenario: Add Team User List - Team Details by User Name
Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
And I navigate to the TEAM Section of the content
When I select the Edit button on the for a "ACTIVE" Team
And I add a user in Team Details by User Name as Team Supervisor "false"
And I verify User List was added on UI

@CP-2407 @CP-2407-02 @Umid @ui-tm @tm-regression
Scenario: Add Team User List - Team Details by Email
Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
And I navigate to the TEAM Section of the content
When I select the Edit button on the for a "FUTURE" Team
And I add a user in Team Details by Email as Team Supervisor "false"
And I verify User List was added on UI

@CP-2407 @CP-2407-03 @Umid @ui-tm @tm-regression
Scenario: Add Team User List - Team Details by maersk ID
Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
And I navigate to the TEAM Section of the content
When I select the Edit button on the for a "INACTIVE" Team
And I add a user in Team Details by maersk ID as Team Supervisor "true"
And I verify User List was added on UI

@CP-2407 @CP-2407-04 @Umid @ui-tm @tm-regression
Scenario: Update Team User List - Team Details check or uncheck supervisor
Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
And I navigate to the TEAM Section of the content
When I select the Edit button on the for a "ACTIVE" Team
And I update User List
And I click on save button

@CP-2407 @CP-2407-05 @Umid @ui-tm @tm-regression
Scenario: Update Team User List - Team Details delete Team User
Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
And I navigate to the TEAM Section of the content
When I select the Edit button on the for a "ACTIVE" Team
And I delete a Regular User
And I click on save button
And I verify user was deleted

@CP-2407 @CP-2407-06 @Umid @ui-tm @tm-regression
Scenario: Add more than 1 Team User List Verify User list View for BU supervisor is listed first, both Supervisor and Regular users colors & that Regular users are listed in Alphabetical order
Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
And I navigate to the TEAM Section of the content
When I select the Edit button on the for a "ACTIVE" Team
When I add 3 user by clicking on the add user button
And I add all the users to the same team amd make one of them as a Supervisor
And I verify User List was added on UI
And I verify Supervisor User is listed first
And I verify Supervisor and Regular users colors
And I verify users excluding Supervisor are listed in Alphabetical order

@CP-2407  @API-CP-2407-07 @events @umid
Scenario: Verify common ui functions(color, asc order, popUp messages) & TEAM_USER_SAVE_EVENT is published to DB4UI Payload Is captured
Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
And I navigate to the TEAM Section of the content
When I select the Edit button on the for a "ACTIVE" Team
And I add a user in Team Details by User Name as Team Supervisor "false"
And I verify User List was added on UI
And I search "TEAM_USER_SAVE_EVENT" in the Get Events endpoint for TM
Then I should see "TEAM_USER_SAVE_EVENT" Event was created

@CP-2407 @API-CP-2407-08 @events @umid
Scenario: Verify common ui functions(color, asc order, popUp messages) & TEAM_USER_UPDATE_EVENT is published to DB4UI Payload Is captured
Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
And I navigate to the TEAM Section of the content
When I select the Edit button on the for a "ACTIVE" Team
And I update User List
And I click on save button
And I click on save button
    # 2 saves bc first one falls on to team details save
And I search "TEAM_USER_UPDATE_EVENT" in the Get Events endpoint for TM
Then I should see "TEAM_USER_UPDATE_EVENT" Event was created