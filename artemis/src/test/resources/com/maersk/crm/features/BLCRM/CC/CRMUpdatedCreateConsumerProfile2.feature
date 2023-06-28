Feature: Updated Create Consumer Profile Part Two

@CP-9456 @CP-9456-01 @mark @ui-cc @crm-regression @ui-cc
Scenario Outline: Validating Consumer Detail fields of Create Consumer Profile Page
Given I logged into CRM and click on initiate contact
When I enter Search criteria fields for a new consumer
And I click on Search Button on Search Consumer Page
And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
Then I see new "<fieldName>" field on Updated Create Consumer Profile Page
Examples:
| fieldName                   |
| FirstName                   |
| MiddleInitial               |
| LastName                    |
| ConsumerType                |
| SpokenLanguage              |
| WrittenLanguage             |
| CorrespondencePreference(s) |


@CP-9456 @CP-9456-02 @mark @ui-cc @crm-regression @ui-cc
Scenario Outline: Required Fields on Updated Create Consumer Profile Page and error messages
Given I logged into CRM and click on initiate contact
When I enter Search criteria fields for a new consumer
And I click on Search Button on Search Consumer Page
And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
When I clear all fields values
And I click on Create Consumer Button
Then I see error message if required field "<fieldName>" is not captured
Examples:
| fieldName |
| firstName |
| lastName  |
| DOB       |
      # Failing from CP-13476
      # Unable to automate for Consumer Type/Spoken Language/Written Language due to drop downs having default values


@CP-9456 @CP-9456-03 @mark @ui-cc @crm-regression @ui-cc
Scenario: Consumer Created Successfully success message
Given I logged into CRM and click on initiate contact
When I enter Search criteria fields for a new consumer
And I click on Search Button on Search Consumer Page
And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
And I populate Create Consumer fields for a new consumer
And I click on Create Consumer Button
And I navigate to active contact tab
Then The consumer I created should be linked to Active Contact


@CP-9456 @CP-9456-06 @mark @ui-cc @crm-regression @ui-cc
Scenario: Validating Profile Detail fields on Demographic Info tab
Given I logged into CRM
When I click case consumer search tab
And I navigate to case and consumer search page
And I search for record by value "Chopa"
And I click first consumer ID in search results on case and consumer search page
And I click on the Demographic Info Tab
Then I should see following fields in the Profile Details