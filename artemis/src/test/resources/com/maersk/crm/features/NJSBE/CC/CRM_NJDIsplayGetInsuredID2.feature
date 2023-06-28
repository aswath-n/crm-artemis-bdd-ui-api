Feature: NJ Search for case/Consumer Viewing GI Case/Consumer IDs - Part 2

@CP-11952 @CP-11952-05 @ui-cc-nj @nj-regression @muhabbat
Scenario: Viewing GI Case ID  - Edit Task Screen search result
Given I logged into CRM and select a project "NJ-SBE"
When I navigate to "Task Search" page
When I search Task by current status date for record
When I click on second Task ID on Task Search
When I click Edit task type button
When I click on Link Case or Consumer button under LINK section
When I searched customer have First Name as "Queen" and Last Name as "Faith"
Then I verify GI case id as "GetInsured CASE ID" in the search result

@CP-11952 @CP-11952-06 @ui-cc-nj @nj-regression @muhabbat
Scenario: Viewing GI Case ID - manual case/consumer search (from Manual Contact record Search screen)
Given I logged into CRM and select a project "NJ-SBE"
When I navigate to Contact Record Search Page
When I search Contact Record type "General" and case id "NJ100003702"
When I click on first Contact Record ID on Contact Record
When I click on edit button on contact details tab
And I click on Unlink Contact Button on Active Contact Page
When I searched customer have First Name as "Queen" and Last Name as "Faith"
Then I verify GI case id as "GetInsured CASE ID" in the search result

@CP-11952 @CP-11952-07 @ui-cc-nj @nj-regression @muhabbat
Scenario: GI Case ID - manual case/consumer search (from Active Contact record Search screen)
Given I logged into CRM and select a project "NJ-SBE"
When I click on initiate contact record
When I searched customer have First Name as "Jones" and Last Name as "Bryan"
And I click on first user radio button and "DOB" "SSN" check boxes
And I click validate&link and contact details tab
When I click on first Contact Record ID on Contact Record
When I click on edit button on contact details tab
When I click on Unlink Contact Button on Active Contact Page
When I searched customer have First Name as "Queen" and Last Name as "Faith"
Then I verify GI case id as "GetInsured CASE ID" in the search result

@CP-11952 @CP-11952-08 @ui-cc-nj @nj-regression @muhabbat
Scenario: GI Case ID on General Contact Record search result after linking
Given I logged into CRM and select a project "NJ-SBE"
When I click on initiate contact record
When I searched customer have First Name as "Queen" and Last Name as "Faith"
And I click on first user radio button and "DOB" "SSN" check boxes
Then I verify case id as "NJ100002908" after clicking validate&link button for "general"

@CP-11952 @CP-11952-09 @ui-cc-nj @nj-regression @muhabbat
Scenario: GI Case ID on 3rd Party Contact Record search result after linking
Given I logged into CRM and select a project "NJ-SBE"
When I click on initiate contact record
Then I click on third party contact record type radio button
When I searched customer have First Name as "Queen" and Last Name as "Faith" in third party
And I click on primary individual record in search result
Then I verify case id as "NJ100002908" after clicking validate&link button for "third"

@CP-11952 @CP-11952-10 @ui-cc-nj @nj-regression @muhabbat
Scenario: GI Case ID - Create Task UI after linking
Given I logged into CRM and select a project "NJ-SBE"
When I Navigate to create task link
And I select "Review Complaint" to create a Task
And I click on Link Case or Consumer button under LINK section
When I searched customer have First Name as "Queen" and Last Name as "Faith"
And I click on primary individual record in search result
Then I verify validate is displayed
Then I verify case id as "NJ100002908" after clicking validate&link button for "createTask"

@CP-11952 @CP-11952-11 @ui-cc-nj @nj-regression @muhabbat
Scenario: GI Case ID - Edit Task Screen after linking
Given I logged into CRM and select a project "NJ-SBE"
When I navigate to "Task Search" page
When I search Task by current status date for record
When I click on second Task ID on Task Search
When I click Edit task type button
When I click on Link Case or Consumer button under LINK section
When I searched customer have First Name as "Queen" and Last Name as "Faith"
And I click on primary individual record in search result
Then I verify validate is displayed
Then I verify case id as "NJ100002908" after clicking validate&link button for "taskSearch"

@CP-11952 @CP-11952-12 @ui-cc-nj @nj-regression @muhabbat
Scenario: GI Case ID - manual case/consumer search (from Manual Contact record Search screen) after linking
Given I logged into CRM and select a project "NJ-SBE"
When I navigate to Contact Record Search Page
When I can search Contact Record by status date and id "NJ100002862"
When I click on first Contact Record ID on Contact Record
When I click on edit button on contact details tab
And I click on Unlink Contact Button on Active Contact Page
When I searched customer have First Name as "Queen" and Last Name as "Faith"
And I click on primary individual record in search result
Then I verify case id as "NJ100002908" after clicking validate&link button for "search"

@CP-11952 @CP-11952-13 @ui-cc-nj @nj-regression @muhabbat
Scenario: GI Case ID - manual case/consumer search (from Active Contact record Search screen) after linking
Given I logged into CRM and select a project "NJ-SBE"
When I click on initiate contact record
When I searched customer have First Name as "Jones" and Last Name as "Bryan"
And I click on first user radio button and "DOB" "SSN" check boxes
And I click validate&link and contact details tab
When I click on first Contact Record ID on Contact Record
When I click on edit button on contact details tab
When I click on Unlink Contact Button on Active Contact Page
When I searched customer have First Name as "Queen" and Last Name as "Faith"
And I click on primary individual record in search result
Then I verify case id as "NJ100002908" after clicking validate&link button for "search"

@CP-13862 @CP-13862-1 @ui-cc-nj @nj-regression @kamil
Scenario: Verify during Active General Contact displayed GetInsured Case ID
Given I logged into CRM and select a project "NJ-SBE"
When I click on initiate a contact button
When I searched customer have First Name as "Queen" and Last Name as "Faith"
And I click on first user radio button and "DOB" "SSN" check boxes
And I click validate&link and contact details tab
When I click on "Case & Contact Details" Tab on Contact Dashboard Page
Then Navigate to first Link Contact History page
Then Verify GetInsured Case ID is "NJ100002908" and Primary Individual's name "Queen Faith" are displayed

# scenario retired
#@CP-13862 @CP-13862-2 @ui-cc-nj @nj-regression @kamil
#Scenario: Verify GetInsured Id from create task page within and active contact with linked case/consumer
#Given I logged into CRM and select a project "NJ-SBE"
#When I click on initiate a contact button
#When I searched customer have First Name as "Queen" and Last Name as "Faith"
#And I click on first user radio button and "DOB" "SSN" check boxes
#And I click validate&link and contact details tab
#When I Navigate to create task link
#And I select "Review Complaint" to create a Task
#Then Navigate to first Link in Create Task page
#Then Verify GetInsured Case ID is "NJ100002908" and Primary Individual's name "Queen Faith" are displayed

@CP-13862 @CP-13862-3 @ui-cc-nj @nj-regression @kamil
Scenario: Verify GetInsured Id from Manual search of contact record and expending by clicking on contact record ID
Given I logged into CRM and select a project "NJ-SBE"
When I click on initiate a contact button
When I click contact search tab for Contact Record
When I searched customer have First Name as "Han" and Last Name as "Solo"
When I click on first Contact Record ID on Contact Record
Then Verify GetInsured Case ID is "NJ100003702" and Primary Individual's name "Han Solo" are displayed

#Task
@CP-13862 @CP-13862-4 @ui-cc-nj @nj-regression @kamil
Scenario: Verify GetInsured Id from task page after clicking on CI case ID on link components
Given I logged into CRM and select a project "NJ-SBE"
When I click on initiate a contact button
When I navigate to "Task Search" page
When I search Task by current status date for record
When I click on second Task ID on Task Search
When I click Edit task type button
When I click on Link Case or Consumer button under LINK section
When I searched customer have First Name as "Queen" and Last Name as "Faith"
And I click on primary individual record in search result
Then Verify GetInsured Case ID is "NJ100002908"

@CP-13862 @CP-13862-5 @ui-cc-nj @nj-regression @kamil
Scenario: Verify GetInsured Id from Manual Case Consumer search result after validating and linking
Given I logged into CRM and select a project "NJ-SBE"
When I click on initiate a contact button
When I searched customer have First Name as "Queen" and Last Name as "Faith"
And I click on first user radio button and "DOB" "SSN" check boxes
And I click validate&link and contact details tab
When I click on "Case & Contact Details" Tab on Contact Dashboard Page
Then Navigate to first Link Contact History page
When I click on first Contact Record ID on Contact Record
Then Verify GetInsured Case ID is "NJ100002908" and Primary Individual's name "Queen Faith" are displayed

#Task
@CP-13862 @CP-13862-6 @ui-cc-nj @nj-regression @kamil
Scenario: Verify GetInsured Id View Task Screen After clicking on GI case ID
Given I logged into CRM and select a project "NJ-SBE"
When I click on initiate a contact button
When I searched customer have First Name as "Kylie" and Last Name as "Myers"
And I click on first user radio button and "DOB" "SSN" check boxes
And I click validate&link and contact details tab
When I click on "Task & Service Request" Tab on Contact Dashboard Page
Then Verify GetInsured Case ID is "SBE100000299" and Primary Individual's name "Kylie Myers" are displayed

@CP-15227 @CP-15227-1.0 @crm-regression @ui-cc-nj @nj-regression @JP
Scenario: Verify Primary individual details on Active Contact Widget
Given I logged into CRM and select a project "NJ-SBE"
When I click on initiate contact record
When I searched customer have First Name as "Queen" and Last Name as "Faith"
And I click on first user radio button and "DOB" "PHONE NUMBER" check boxes
And I click validate&link button for "general"
And I capture GetInsured Case ID in active contact tab
And I navigate to Case Consumer Advance Search
Then I verify Case ID, First Name as "Queen" and Last Name as "Faith" in Active Contact Widget

@CP-15227 @CP-15227-1.1 @crm-regression @ui-cc-nj @nj-regression @JP
Scenario: Verify Case member details on Active Contact Widget
Given I logged into CRM and select a project "NJ-SBE"
When I click on initiate contact record
When I searched customer have First Name as "Queen" and Last Name as "Faith"
And I click on first user second radio button and "DOB" "SSN" check boxes
And I click validate&link button for "{string}"
And I capture GetInsured Case ID in active contact tab
And I navigate to Case Consumer Advance Search
Then I verify Case ID, First Name as "Queen" and Last Name as "Faith" in Active Contact Widget

