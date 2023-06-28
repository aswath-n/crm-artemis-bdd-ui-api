@CreateGeneraltask
Feature: Create General Task Second

#Refactor By:Vidya
@CP-118  @CP-118-03 @CP-25316 @CP-25316-04 @crm-regression @crm-smoke @ui-wf @shruti
Scenario: Save Button functionality
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
When I navigate to "General" task page
And I click on save button in create task page
    #Then I verify Success message is displayed for task
When I navigate to "Work Queue" page
And I navigate to newly created task by clicking on TaskID column header
When I expand the first row in task list
And I verify Task Notes field is not displayed on Task List expanded view page
And Close the soft assertions

@CP-118  @CP-118-04 @CP-25316 @CP-25316-16 @crm-regression @ui-wf @vidya
Scenario: Verification all fields and mandatory fields on create task page
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
When I navigate to "General" task page
And I will provide following information before creating task
| taskType                 |Inbound Task|
| priority                 |4|
| status                   |Escalated|
| assignee                 |Service AccountTwo|
| taskInfo                 |random|
And I click on save button in create task page
    #Then I verify Success message is displayed for task
When I navigate to "My Task" page
And I navigate to newly created task by clicking on TaskID column header
When I expand the first row in task list
And I verify Task Notes field is not displayed on Task List expanded view page
And Close the soft assertions

    #Refactor By:Vidya
@CP-118  @CP-118-05 @crm-regression @ui-wf @shruti
Scenario: Cancel button functionality
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
And I click on initiate a contact button
And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
Then I link the contact to an existing Case or Consumer Profile
When I navigate to "General" task page
And I will provide following information before creating task
| priority                 |4|
| status                   |Escalated|
| assignee                 |Service AccountTwo|
| taskInfo                 |random|
And I click on cancel button on create task type screen
Then I verify warning message is displayed for task
And I click on cancel button on create task warning message
Then I verify user remains on same create task page
And I click on cancel button on create task type screen
Then I verify warning message is displayed for task
And I click on continue button on create task warning message
Then I will be take to the same screen where I initiated create task
And Close the soft assertions

@CP-118  @CP-118-06 @crm-regression @ui-wf @shruti
Scenario: contact-link info displayed
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
And I click on initiate a contact button
And I searched customer have First Name as "Ethan" and Last Name as "Hunt"
Then I link the contact to an existing Case or Consumer Profile
And I navigate to "General" task page
Then I verify Contact information is populated on the screen
And Close the soft assertions

    #Below steps are for story CP-2098
@CP-2098 @CP-2098-01 @CP-25982 @CP-25982-01 @ruslan @vidya @crm-regression @ui-wf
Scenario: Verify if User role has permission to create task in Tenant manager then in CP user is able create task
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
When I navigate to "General" task page
Then I verify create task fields displayed
And I will provide following information before creating task
| assignee                 |Service AccountTwo|
| taskInfo                 |Task Info|
And I click on save button in create task page
    #Then I verify Success message is displayed for task
And Close the soft assertions

@CP-2098 @CP-2098-03 @CP-25982 @CP-25982-03 @ruslan @vidya @crm-regression @crm-smoke @ui-wf
Scenario: Verify if User role does not has permission to create task in Tenant manager then in CP user is not able to create task
Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
When I Navigate to create task link
Then I should not able to created "General" task
And Close the soft assertions

   #AC-1.0
@CP-146 @CP-146-01 @paramita @ui-wf @crm-regression
Scenario: Verify manually link the Task to a Case or Consumer by search operation
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
When I navigate to "General" task page
And I click on Link Case or Consumer button under LINK section
Then I see Case& Consumer Search section display
When I search for consumer have First Name as "fnaDgkL" and Last Name as "lnqOguN"
And I click on Search option
Then I should able to Manually Link the Task to a Case or Consumer by search operation
And Close the soft assertions

  #AC-2.0
@CP-146 @CP-146-02 @paramita @ui-wf @crm-regression
Scenario: Verify display of radio button associated with a Case or Consumer in search result
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
When I navigate to "Address" task page
And I click on Link Case or Consumer button under LINK section
Then I see Case& Consumer Search section display
When I search for consumer have First Name as "fnaDgkL" and Last Name as "lnqOguN"
And I click on Search option
Then I should able to Manually Link the Task to a Case or Consumer by search operation
When I expand the first record of the search result
Then I see radio button is associated with a Case or Consumer
And Close the soft assertions

  #AC-2.1
@CP-146 @CP-146-03 @paramita @ui-wf @crm-regression
Scenario: Verify display of 'Link to Case Only' checkbox in search result
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
When I navigate to "Address" task page
And I click on Link Case or Consumer button under LINK section
Then I see Case& Consumer Search section display
When I search for consumer have First Name as "fnaDgkL" and Last Name as "lnqOguN"
And I click on Search option
Then I should able to Manually Link the Task to a Case or Consumer by search operation
When I expand the first record of the search result
Then I see radio button is associated with a Case or Consumer
And I see 'Link to Case Only' checkbox and not selected
And Close the soft assertions

  #AC-3.0
@CP-146 @CP-146-04 @paramita @ui-wf @crm-regression
Scenario: Verify Link Consumer Record button display on selecting Consumer radio button
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
When I navigate to "Address" task page
And I click on Link Case or Consumer button under LINK section
Then I see Case& Consumer Search section display
When I search for consumer have First Name as "fnaDgkL" and Last Name as "lnqOguN"
And I click on Search option
Then I should able to Manually Link the Task to a Case or Consumer by search operation
When I expand the first record of the search result
Then I see radio button is associated with a Case or Consumer
When I select Consumer radio button
Then I see Link Record Consumer button get displayed
And Close the soft assertions

  #AC-3.1
@CP-146 @CP-146-05 @paramita @ui-wf @crm-regression
Scenario: Verify Link Record Case button display on selecting 'Link to Case Only' checkbox
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
When I navigate to "Address" task page
And I click on Link Case or Consumer button under LINK section
Then I see Case& Consumer Search section display
When I search for consumer have First Name as "fnaDgkL" and Last Name as "lnqOguN"
And I click on Search option
Then I should able to Manually Link the Task to a Case or Consumer by search operation
When I expand the first record of the search result
Then I see radio button is associated with a Case or Consumer
When I select 'Link to Case Only' checkbox
Then I see Link Record Case button get displayed
And Close the soft assertions

  #AC-4.0
@CP-146 @CP-146-06 @paramita @ui-wf @crm-regression
Scenario: Verify  Consumer Radio Buttons become disabled when user select 'Link to Case Only' checkbox
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
When I navigate to "Address" task page
And I click on Link Case or Consumer button under LINK section
Then I see Case& Consumer Search section display
When I search for consumer have First Name as "fnaDgkL" and Last Name as "lnqOguN"
And I click on Search option
Then I should able to Manually Link the Task to a Case or Consumer by search operation
When I expand the first record of the search result
Then I see radio button is associated with a Case or Consumer
When I select 'Link to Case Only' checkbox
Then I see all Consumer Radio Buttons become disabled
And Close the soft assertions

  #AC-6.0
@CP-146 @CP-146-07 @paramita @ui-wf @crm-regression
Scenario: Verify  Consumer Radio Buttons become enabled when user select and de-select 'Link to Case Only' checkbox
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
When I navigate to "Address" task page
And I click on Link Case or Consumer button under LINK section
Then I see Case& Consumer Search section display
When I search for consumer have First Name as "fnaDgkL" and Last Name as "lnqOguN"
And I click on Search option
Then I should able to Manually Link the Task to a Case or Consumer by search operation
When I expand the first record of the search result
Then I see radio button is associated with a Case or Consumer
When I select 'Link to Case Only' checkbox
Then I see all Consumer Radio Buttons become disabled
When I select 'Link to Case Only' checkbox once again
Then I see all Consumer Radio Buttons become enabled
And I see Link Record button will not get display
And Close the soft assertions

  #AC- 5.0
@CP-146 @CP-146-08 @paramita @ui-wf @crm-regression
Scenario: Verify Consumer Radio Buttons become disabled when user select radio button and  select 'Link to Case Only' checkbox
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
When I navigate to "Address" task page
And I click on Link Case or Consumer button under LINK section
Then I see Case& Consumer Search section display
When I search for consumer have First Name as "fnaDgkL" and Last Name as "lnqOguN"
And I click on Search option
Then I should able to Manually Link the Task to a Case or Consumer by search operation
When I expand the first record of the search result
Then I see radio button is associated with a Case or Consumer
When I select Consumer radio button
Then I see Link Record Consumer button get displayed
When I select 'Link to Case Only' checkbox
Then I see all Consumer Radio Buttons become disabled
And I see Link Record Case button get displayed
And Close the soft assertions

  #AC-7.0 Defect
@CP-146 @CP-146-09 @paramita @ui-wf @crm-regression
Scenario: Verify 'Link to Case Only' checkbox disabled when case/consumer search result is not associated with a Case
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
When I navigate to "Address" task page
And I click on Link Case or Consumer button under LINK section
Then I see Case& Consumer Search section display
When I search for consumer have First Name as "Robertlrhik" and Last Name as ""
And I click on Search option
Then I should able to Manually Link the Task to a Case or Consumer by search operation
And I select the record whose Case ID Blank
Then I verify 'Link to Case Only' checkbox disabled for the record whose CASE ID is blank
And Close the soft assertions