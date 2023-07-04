Feature: IN-EB Configured Create Consumer Part 3

@CP-28045 @CP-28045-04 @CP-17043 @CP-17043-02 @Beka @crm-regression @ui-cc-in
Scenario: Verify external ID in consumer on a case not have a little trash bucket
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "Jolie" and Last Name as "Jones"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I click on first Primary Individual
Then I verify external ID on edit consumer page not have a little trash bucket

  #@CP-28045 @CP-28045-05 @Beka @crm-regression @ui-cc-in                 muted due to changes in CP-30604 and CP-30248
Scenario: Verify external ID in case member page is not editable
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "Jolie" and Last Name as "Jones"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
And click on existing case
Then I verify external ID on edit consumer page is NOT editable

@CP-28045 @CP-28045-06 @CP-17043 @CP-17043-03 @Beka @crm-regression @ui-cc-in
Scenario: Verify external ID in case member page not have a little trash bucket
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "Jolie" and Last Name as "Jones"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I click on first Primary Individual
Then I verify external ID on edit consumer page not have a little trash bucket

@CP-30249 @CP-30249-01 @crm-regression @ui-cc-in @Beka
Scenario: Suppress Edit of View Primary Individual
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "Brat" and Last Name as "Smith"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
When click on existing case
Then I will be UNABLE to edit any of the fields on "Primary Individual page"
And  Verify Save and Cancel buttons not visible

@CP-30249 @CP-30249-02 @CP-30248-01 @crm-regression @ui-cc-in @Beka
Scenario: Suppress Add Button on External ID - Primary Individual
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "Brat" and Last Name as "Smith"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
When click on existing case
Then I will not see the "Add" button for the External ID component

@CP-30249 @CP-30249-03 @crm-regression @ui-cc-in @Beka
Scenario: Suppress Edit of View Case Member
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "Brat" and Last Name as "Smith"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I click on any existing Case Member
Then I will be UNABLE to edit any of the fields on "Case Member page"
And  Verify Save and Cancel buttons not visible

@CP-30249 @CP-30249-04 @CP-30248-02 @crm-regression @ui-cc-in @Beka
Scenario: Suppress Add Button on External ID - Case Member
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "Brat" and Last Name as "Smith"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I click on any existing Case Member
Then I will not see the "Add" button for the External ID component

@CP-30604 @CP-30604-01 @crm-regression @ui-cc-in @chopa
Scenario: Suppress Add Button on External ID - Create Consumer Page in General Active Contact Search
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I enter Search criteria fields for a new consumer
And I click on Search Button on Search Consumer Page
And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
Then I will not see the "Add" button for the External ID component

@CP-30604 @CP-30604-02 @crm-regression @ui-cc-in @chopa
Scenario: Suppress Add Button on External ID - Create Consumer Page in Third Party Contact Search
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
And I clicked on Third Party Contact
When I enter Search criteria fields for a new consumer from Third Party
And I click on Search Button on Search Consumer Page
And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
Then I will not see the "Add" button for the External ID component

#@CP-30604 @CP-30604-03 @crm-regression @ui-cc-in @chopa                <<<<<<<<<<<<<<<<<<<<<<<<<<<---------------- Commented dou to functionality changed (unable edit PI)
#Scenario: Suppress Add Button on External ID - Edit Consumer Page in General Active Contact Search
 # Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
#When I click on initiate contact record
#When I searched customer have First Name as "Chess" and Last Name as ""
#And I link the contact to an existing Case or Consumer Profile
#When I click on "Demographic Info" Tab on Contact Dashboard Page
#And I click on first Primary Individual
#Then I will not see the "Add" button for the External ID component

@CP-30604 @CP-30604-04 @crm-regression @ui-cc-in @chopa
Scenario: Suppress Add Button on External ID - Create Consumer Page in Case Consumer search
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
Then I navigate to Case Consumer search
When I enter Search criteria fields for a new consumer
And I click on Search Button on Search Consumer Page
And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
Then I will not see the "Add" button for the External ID component


@CP-32605 @CP-32605-01 @Beka @crm-regression @ui-cc-in
Scenario: IN-EB: Verification County field to be optional Manual Consumer Create (not on a Case)
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I enter Search criteria fields for a new consumer
And I click on Search Button on Search Consumer Page
And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
When I populate Create Consumer fields with no county for a new consumer for IN-EB
When I click on Create Consumer Button
Then I see new consumer is created and has a unique Consumer ID