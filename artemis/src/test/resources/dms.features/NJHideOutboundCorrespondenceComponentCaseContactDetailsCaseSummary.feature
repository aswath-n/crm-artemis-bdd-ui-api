Feature: NJ Hide Outbound Correspondence Component in Case & Contact Details tab of Case Summary

@CP-11584 @CP-11584-01 @ui-cc-nj @ui-ecms-nj @umid
Scenario: Hide Outbound Correspondence Component in Case & Contact Details tab
  Given I logged into CRM and select a project "NJ-SBE"
  When I click on initiate contact record
  When I searched customer have First Name as "Queen" and Last Name as "Faith"
  And I click on first user radio button and "DOB" "SSN" check boxes
  And I click validate and link button
  And I navigate to the case and contact details
  Then I verify Outbound Correspondence Component is hidden

@CP-11584 @CP-11584-02 @ui-cc-nj @ui-ecms-nj @umid
Scenario: Hide Outbound Correspondence Component in Manual Case/Consumer Search
  Given I logged into CRM and select a project "NJ-SBE"
  When I click on Case Consumer Search page
  When I searched customer have First Name as "Queen" and Last Name as "Faith"
  And I click to expand on primary individual
  And I click validate and link button
  Then I verify Outbound Correspondence Component is hidden

@CP-11584 @CP-11584-03 @ui-cc-nj @ui-ecms-nj @umid
Scenario: Hide Outbound Correspondence Component in Contact Record Search
  Given I logged into CRM and select a project "NJ-SBE"
  And I navigate to Contact Record Search Page
  When I searched customer have First Name as "Queen" and Last Name as "Faith"
  And I click on first Contact Record ID on Contact Record
  And I click on CaseId on Links section
  Then I verify Outbound Correspondence Component is hidden

@CP-11584 @CP-11584-04 @ui-cc-nj @ui-ecms-nj @umid
Scenario: Hide Outbound Correspondence Component in Task Search
  Given I logged into CRM and select a project "NJ-SBE"
  When I navigate to "Task Search" page
  And I click on advanced search
  When I searched customer have First Name as "Queen" and Last Name as "Faith"
  When I click on second Task ID on Task Search
  And I click on CaseId on Links section
  Then I verify Outbound Correspondence Component is hidden