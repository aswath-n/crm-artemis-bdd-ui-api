Feature: IN-EB Configured Create Consumer Part 2

@CP-23374 @CP-23374-11 @muhabbat @crm-regression @ui-cc-in
Scenario: IN-EB: Validating Error message is displayed for the mandatory fields
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I populate Search criteria fields for a new consumer
And I click on Search Button on Search Consumer Page
And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
And I click on Create Consumer Button
Then I see Please fill in the required field error message displayed


@CP-23374 @CP-23374-12 @muhabbat @crm-regression @ui-cc-in @ui-cc-smoke
Scenario: Verification of Save Consumer Button functionality
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I enter Search criteria fields for a new consumer
And I click on Search Button on Search Consumer Page
And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
When I populate Create Consumer fields for a new consumer for IN-EB
When I click on Create Consumer Button
Then I am navigated to active contact page

@CP-26940 @CP-26940-1 @chopa @crm-regression @ui-cc-in
Scenario: Verify Email Component is not displayed on consumer profile
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I enter Search criteria fields for a new consumer
And I click on Search Button on Search Consumer Page
And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
When I populate Create Consumer fields for a new consumer for IN-EB
When I click on Create Consumer Button
When I click on "Demographic Info" Tab on Contact Dashboard Page
Then I verify email field is not displayed on profile details

@CP-26940 @CP-26940-2 @chopa @crm-regression @ui-cc-in
Scenario: Verify Email Component is not displayed on Edit consumer profile
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I enter Search criteria fields for a new consumer
And I click on Search Button on Search Consumer Page
And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
When I populate Create Consumer fields for a new consumer for IN-EB
When I click on Create Consumer Button
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I click on edit button
Then I verify email field is not displayed on profile details

@CP-26942 @CP-26942-1 @chopa @crm-regression @ui-cc-in
Scenario: Verify Email Component is not displayed on Case Summary Contact Info
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "Brat" and Last Name as "Smith"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I navigate to Contact Info Tab
Then I verify email field is not displayed on profile details

@CP-26943 @CP-26943-1 @chopa @crm-regression @ui-cc-in
Scenario: Verify Email Component is not displayed on Active General or Third Party Contact Search
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
And I click on advance search icon
Then I verify email field is not displayed on profile details
And I navigate to Third Party page
And I click on advance search icon
Then I verify email field is not displayed on profile details

@CP-26943 @CP-26943-2 @chopa @crm-regression @ui-cc-in
Scenario: Verify Email Component is not displayed on Manual Case/Consumer Search
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
And I navigate to Case Consumer search
And I click on advance search icon
Then I verify email field is not displayed on profile details

@CP-26943 @CP-26943-3 @chopa @crm-regression @ui-cc-in
Scenario: Verify Email Component is not displayed on Create or Edit Task screens
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I Navigate to create task link
And I select "HCC Do Not Call List" to create a Task
And I click on contact link button under LINK section
And I click on Case and Consumer button
And I click on advance search icon
Then I verify email field is not displayed on profile details

@CP-28291 @CP-28291-01 @Beka @crm-regression @ui-cc-in
Scenario: IN-EB: Hide Fields on Create Consumer Not on a Case
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I populate Search criteria fields for a new consumer
And I click on Search Button on Search Consumer Page
And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
Then I don't see unexpected "fields" on IN-EB Create Consumer present
| Ethnicity                                   |
| Race                                        |
| Citizenship                                 |
| American Indian or Alaska Native (checkbox) |

@CP-28291 @CP-28291-02 @Beka @crm-regression @ui-cc-in
Scenario: Verify Hide Opt In Info - View/Edit Consumer Not on a Case
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I enter Search criteria fields for a new consumer
And I click on Search Button on Search Consumer Page
And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
When I populate Create Consumer fields for a new consumer for IN-EB
When I click on Create Consumer Button
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I click on edit button
Then I don't see unexpected "fields" on IN-EB Create Consumer present
| optInOuts |

@CP-28291 @CP-28291-03 @Beka @crm-regression @ui-cc-in
Scenario: Verify Hide Fields - View/Edit Consumer Not on a Case
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I enter Search criteria fields for a new consumer
And I click on Search Button on Search Consumer Page
And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
When I populate Create Consumer fields for a new consumer for IN-EB
When I click on Create Consumer Button
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I click on edit button
Then I don't see unexpected "fields" on IN-EB Create Consumer present
| Correspondence preference |
| Written Language          |
| Spoken Language           |

  #@CP-28045 @CP-28045-01 @Beka @crm-regression @ui-cc-in             muted due to changes in CP-30604
Scenario: Verify external ID in consumer profile is not editable
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I enter Search criteria fields for a new consumer
And I click on Search Button on Search Consumer Page
And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
When I populate Create Consumer fields for a new consumer for IN-EB
When I click on Create Consumer Button
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I click on edit button
And I add "" external ID
And I click on edit button
Then I verify external ID on edit consumer page is NOT editable

  #@CP-28045 @CP-28045-02 @CP-17043 @CP-17043-01 @Beka @crm-regression @ui-cc-in   muted due to changes in CP-30604
Scenario: Verify external ID in consumer profile not have a little trash bucket
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I enter Search criteria fields for a new consumer
And I click on Search Button on Search Consumer Page
And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
When I populate Create Consumer fields for a new consumer for IN-EB
When I click on Create Consumer Button
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I click on edit button
And I add "" external ID
And I click on edit button
Then I verify external ID on edit consumer page not have a little trash bucket

  #@CP-28045 @CP-28045-03 @Beka @crm-regression @ui-cc-in    muted due to changes in CP-30604 and CP-30248
Scenario: Verify external ID in consumer on a case is not editable
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "Jolie" and Last Name as "Jones"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I click on first Primary Individual
Then I verify external ID on edit consumer page is NOT editable
