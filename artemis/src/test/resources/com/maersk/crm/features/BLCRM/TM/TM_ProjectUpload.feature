Feature: Tenant Manager - Project Upload


@CP-25637 @mital @tm-regression @ui-tm
Scenario Outline:  Display error to user if duplicates exist in project config
Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
And I click on the configuration icon for the project
And I click on the Upload Project tab
Then I verify Upload project page is displayed
When I choose "<FileType>" to upload project
And I attach file to choose file tab
Then I click on Upload button on Upload Project config
Then I verify the file uplaod failure "<MessageType>" message
Examples:
| FileType        | MessageType    |
| File with Duplicate field key | Duplicate field failure message |
| File with Duplicate grid key | Duplicate grid failure message |
| File with Duplicate grid column key | Duplicate grid column failure message |
| File with Duplicate page key | Duplicate page failure message |
| File with Duplicate section key | Duplicate section failure message |
| File with Duplicate label key | Duplicate label failure message |