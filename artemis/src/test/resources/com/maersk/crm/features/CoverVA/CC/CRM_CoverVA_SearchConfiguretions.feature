Feature: CoverVA Search Configurations

@CP-19614 @CP-19614-0 @chopa @crm-regression @ui-cc-va
Scenario: Verify Search Case and Case ID fields are not present in Manual Case/Consumer Search Page
Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
When I navigate to case and consumer search page
And I verify Search Case field is not displayed
And I verify Case ID field is not displayed

@CP-19614 @CP-19614-01 @chopa @crm-regression @ui-cc-va
Scenario: Verify Case ID column is not displayed in Manual Case/Consumer Search Page Results
Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
When I navigate to case and consumer search page
And I search for record by value "Emma"
And I verify Case ID field is not displayed

@CP-19614 @CP-19614-02 @chopa @crm-regression @ui-cc-va
Scenario: Verify Case ID column is not displayed in Active Contact
Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
When I click on initiate contact record
When I searched customer have First Name as "Emma" and Last Name as "Testing"
And I verify Case ID field is not displayed

@CP-19614 @CP-19614-03 @chopa @crm-regression @ui-cc-va
Scenario: Verify Case ID column is not displayed creating or editing a task
Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
When I navigate to "Task Search" page
And I click on Call Managment window
And I click on advanced search
When I search Task by current status date for record
When I click on first Contact Record ID on Contact Record
And I verify Case ID field is not displayed