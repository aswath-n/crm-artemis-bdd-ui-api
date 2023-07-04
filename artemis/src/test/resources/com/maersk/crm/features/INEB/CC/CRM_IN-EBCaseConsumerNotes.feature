Feature: IN-EB Notes Functionality

@CP-29785 @CP-29785-07 @Beka @crm-regression @ui-cc-in
Scenario: Verify Notes Component in Case Summary IN-EB
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click case consumer search tab
When I searched customer have First Name as "Emma" and Last Name as ""
When I expand the record to navigate Case Consumer Record from manual view
And I click on the Demographic Info Tab
Then I see notes component with dropdown relates to save and cancel button "Case"

@CP-29785 @CP-29785-08 @Beka @crm-regression @ui-cc-in
Scenario: Verify Notes Component Elements IN-EB
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click case consumer search tab
When I searched customer have First Name as "Emma" and Last Name as ""
When I expand the record to navigate Case Consumer Record from manual view
And I click on the Demographic Info Tab
Then I verify text field is can take 1000alphanumeric characters

@CP-29785 @CP-29785-09 @Beka @crm-regression @ui-cc-in
Scenario: Verify Then I will have a required single select list of options Case and all Consumers should be listed IN-EB
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click case consumer search tab
When I searched customer have First Name as "Emma" and Last Name as ""
When I expand the record to navigate Case Consumer Record from manual view
And I click on the Demographic Info Tab
Then Verify Then I will have a required single select list of options Case and all Consumers should be listed

@CP-29785 @CP-29785-10 @Beka @crm-regression @ui-cc-in
Scenario: Verify Notes get saved IN-EB
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click case consumer search tab
When I searched customer have First Name as "Emma" and Last Name as ""
When I expand the record to navigate Case Consumer Record from manual view
And I click on the Demographic Info Tab
Then I will verify new note get saved as 1st note in table

@CP-29785 @CP-29785-11 @Beka @crm-regression @ui-cc-in
Scenario: Verify error message when try to save notes without selecting relates to value IN-EB
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click case consumer search tab
When I searched customer have First Name as "Emma" and Last Name as ""
When I expand the record to navigate Case Consumer Record from manual view
And I click on the Demographic Info Tab
Then I Verify error message "Please indicate who this note relates to."

@CP-29785 @CP-29785-12 @Beka @crm-regression @ui-cc-in
Scenario: Verify error message when try to save notes with No text IN-EB
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click case consumer search tab
When I searched customer have First Name as "Emma" and Last Name as ""
When I expand the record to navigate Case Consumer Record from manual view
And I click on the Demographic Info Tab
Then I Verify error message "Please provide Note text."