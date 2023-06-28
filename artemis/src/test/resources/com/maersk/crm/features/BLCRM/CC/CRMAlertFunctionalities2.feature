Feature: Alert Validations Part Two
@CP-31567 @CP-31567-03 @chopa @ui-cc @crm-regression
Scenario: Verify Alert Text, Start Date and Type are required to create an alert
Given I logged into CRM and click on initiate contact
When I searched customer have First Name as "John" and Last Name as "Thomos"
And I link the contact to an existing Case or Consumer Profile
And I click on Alert on Hamburder button
When I click on Save button
Then I verify error message for fields to be required

@CP-31567 @CP-31567-04 @chopa @ui-cc @crm-regression
Scenario: Verify Links Component is systematically populated with the linked Case or Consumer
Given I logged into CRM and click on initiate contact
When I searched customer have First Name as "John" and Last Name as "Thomos"
And I link the contact to an existing Case or Consumer Profile
And I click on Alert on Hamburder button
Then I verify medicaid case id is systematically populated with the linked consumer

@CP-31567 @CP-31567-05 @chopa @ui-cc @crm-regression
Scenario: Verify I am navigated back to Active Contact Search page after successful alert creation
Given I logged into CRM and click on initiate contact
When I searched customer have First Name as "John" and Last Name as "Thomos"
And I link the contact to an existing Case or Consumer Profile
And I click on Alert on Hamburder button
And I successfully create an "Active" Alert for consumer
When I click on Save button
Then I am navigated back to Active Contact Search Page


@CP-25965 @CP-25965-01 @ui-cc @Beka
Scenario: Validate Create Alert link Button
Given I logged into CRM
And I click on Alert on Hamburder button
And I successfully create an "Active" Alert for consumer
Then I will see create links button

@CP-25965 @CP-25965-02 @ui-cc @Beka
Scenario: Validate Case/Consumer Search Results in the component
Given I logged into CRM
And I click on Alert on Hamburder button
And I successfully create an "Active" Alert for consumer
And I will click on create link button on manual create alert page
When I search consumer has first name as "RenNrhACSg"
And I click on search button on manual create alert page
Then I will see the Case Consumer Search Results in the component
| CRM CASE ID     |
| CRM CONSUMER ID |
| FIRST NAME      |
| LAST NAME       |
| DATE OF BIRTH   |
| SSN             |
| PHONE NUMBER    |

@CP-25965 @CP-25965-03 @ui-cc @Beka
Scenario: Validate Alert Linking Consumer on a Case
Given I logged into CRM
And I click on Alert on Hamburder button
And I successfully create an "Active" Alert for consumer
And I will click on create link button on manual create alert page
When I search consumer has first name as "RenNrhACSg"
And I click on search button on manual create alert page
And I expend the Case Consumer this contact relates to in search result
Then I click on each radio box in front of all consumer one by one and verify linkCaseConsumer button is displayed

@CP-25965 @CP-25965-04 @ui-cc @Beka
Scenario: Validate Alert Linking Consumer not on a Case
Given I logged into CRM
And I click on Alert on Hamburder button
And I successfully create an "Active" Alert for consumer
And I will click on create link button on manual create alert page
When I search consumer has first name as "DeborahuyWoM"
And I click on search button on manual create alert page
And I expend the Case Consumer this contact relates to in search result
Then I will see the Link Consumer button

@CP-25965 @CP-25965-05 @ui-cc @Beka
Scenario: Verify Alert Link to Case Only
Given I logged into CRM
And I click on Alert on Hamburder button
And I successfully create an "Active" Alert for consumer
And I will click on create link button on manual create alert page
When I search consumer has first name as "RenNrhACSg"
And I click on search button on manual create alert page
And I expend the Case Consumer this contact relates to in search result
When I select 'Link to Case Only' checkbox
Then I see LINK CASE button is displayed on the page
When I click the Link Case button
Then Alert is linked to the Case record only and not a specific Consumer on that Case

@CP-33881 @CP-33881-01 @ui-cc @chopa
Scenario: Verify Alert is displayed on View or Edit Task or Service Request - Case Alerts
Given I logged into CRM
Then I navigate to "Task Search" from task view page
Then I search by Case ID "73476"
And In search result click on task id to navigate to view page
Then I verify "View All Alerts" button is displayed on case summary page
Then I verify Alert Banner is displayed with text "Sample Alert for linked consumer" on View Task Page

@CP-33881 @CP-33881-02 @ui-cc @chopa
Scenario: Verify Alert is displayed on View or Edit Task or Service Request - Consumer Alerts
Given I logged into CRM
Then I navigate to "Task Search" from task view page
Then I search by Consumer ID "195641"
And In search result click on task id to navigate to view page
Then I verify "View All Alerts" button is displayed on case summary page
Then I verify Alert Banner is displayed with text "Sample Alert for linked consumer" on View Task Page

  @CP-33282 @CP-33282-01 @ui-cc @chopa
  Scenario: Verify Unlinking a Case/Consumer from Existing Alert on Edit Alert Screen
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Oghtyijf" and Last Name as "Gwsgfsg"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    When I click on View All Alerts button
    And I click on Hamburger menu
    Then I click on "Edit" option for alert
    Then I verify I am on Alert Details screen
    Then I verify hidden Unlink Case and Consumer button is visible
    And I can minimize Active Contact Widget
    When I click hidden Unlink Case and Consumer button
    Then I verify Unlink Case and Consumer button is not visible

  @CP-33282 @CP-33282-02 @ui-cc @chopa                                    
  Scenario: Verify Unlinking and Then Clicking Cancel on Edit Alert Screen
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Oghtyijf" and Last Name as "Gwsgfsg"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    When I click on View All Alerts button
    And I click on Hamburger menu
    Then I click on "Edit" option for alert
    And I can minimize Active Contact Widget
    When I click hidden Unlink Case and Consumer button
    And I click on cancel option on warning message
    Then I verify Alert Banner is displayed with text "Alert for CP-33282" on Case Summary Page

#  @CP-33282 @CP-33282-03 @ui-cc @chopa                                          #muted, reason: alert can be stored at the CASE level but only be queried at the COSUMER level
#  Scenario: Verify Replacing the linked Case/Consumer on Edit Alert Screen
#    Given I logged into CRM and click on initiate contact
#    When I searched customer have First Name as "Oghtyijf" and Last Name as "Gwsgfsg"
#    And I link the contact to an existing Case or Consumer Profile
#    And I click on the Demographic Info Tab
#    When I click on View All Alerts button
#    And I click on Hamburger menu
#    Then I click on "Edit" option for alert
#    And I can minimize Active Contact Widget
#    When I click hidden Unlink Case and Consumer button
#    And I will click on create link button on manual create alert page
#    When I search consumer has first name as "Oghtyijf"
#    And I click on search button on manual create alert page
#    And I expend the Case Consumer this contact relates to in search result
#    When I select 'Link to Case Only' checkbox
#    Then I see LINK CASE button is displayed on the page
#    When I click the Link Case button
#    Then Alert is linked to the Case record only and not a specific Consumer on that Case

  @CP-33282 @CP-33282-04 @ui-cc @chopa
  Scenario: Verify Validation That Linked Case/Consumer is Required on Edit Alert Screen
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Oghtyijf" and Last Name as "Gwsgfsg"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    When I click on View All Alerts button
    And I click on Hamburger menu
    Then I click on "Edit" option for alert
    And I can minimize Active Contact Widget
    When I click hidden Unlink Case and Consumer button
    When I click on Save button
    Then I verify validation error "You must link a Case or Consumer to save your Alert"
