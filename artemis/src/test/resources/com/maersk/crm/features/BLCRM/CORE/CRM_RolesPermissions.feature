Feature: Role Permissions

  @CP-148 @CP-148-01 @ui-core @crm-regression @aikanysh
Scenario: "Access denied. Please contact the system admin." Demographics/ContactInfoAddAddress("No Permission")
  Given I logged into CRM with "SVC_mars_user_4" and click on initiate contact
  When I searched customer have First Name as "Joan" and Last Name as "Riley"
  And I link the contact to an existing Case or Consumer Profile
  And I click on the Demographic Info Tab
  Then I click on the Contact Info Tab
  Then I click on the Add button
  And i verify that "SVC_mars_user_4" is not able to add address

@CP-148 @CP-148-02 @ui-core @crm-regression @aikanysh
Scenario: Read Only Access. Demographics/ContactInfoPhoneNumber("View")
  Given I logged into CRM with "SVC_mars_user_4" and click on initiate contact
  When I searched customer have First Name as "Joan" and Last Name as "Riley"
  And I link the contact to an existing Case or Consumer Profile
  And I click on the Demographic Info Tab
  Then I click on the Contact Info Tab
  And I verify that  "SVC_mars_user_4" is able to see phoneNumbers under Contact Info Tab
  
@CP-148 @CP-148-03 @ui-core @crm-regression @aikanysh
Scenario:  Edit At The Page Level (not allowed). case_consumer_search/editConsumer("View")
  Given I logged into CRM with "SVC_mars_user_4" and click on initiate contact
  When I enter Search criteria fields for a new consumer
  And I click on Search Button on Search Consumer Page
  And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
  When I populate Create Consumer fields for a new consumer
  When I click on Create Consumer Button on Create Consumer Page
  When I click case consumer search tab
  When I searched customer have First Name as created above and Last Name as created above
  When I click case consumer search tab
  When I searched customer have First Name as created above and Last Name as created above
  Then click on found case or consumer
  And I click on edit button on Profile Contact Page
  Then "SVC_mars_user_4"  should not be able to edit any of the Profile Contact fields
