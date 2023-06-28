Feature: Add Pregnancy Indicator and Pregnancy Due Date to consumer profile

  @CP-26393 @CP-26393-01 @crm-regression @ui-cc @chopa
Scenario: Display Pregnant Checkbox when Gender = Female BLCRM
Given I logged into CRM
When I click on initiate contact record
And I search for a consumer and to click on Add Consumer button
When I select Gender as "Female" to verify pregnancy checkbox is displayed on create consumer page

@CP-26393 @CP-26393-02 @crm-regression @ui-cc @chopa
Scenario:  Pregnancy Due Date Field Display BLCRM
Given I logged into CRM
When I click on initiate contact record
And I search for a consumer and to click on Add Consumer button
When I select Gender as "Female"
And I click pregnancy checkbox
Then I should be able to see PREGNANCY DUE DATE Field Display

@CP-26393 @CP-26393-03 @crm-regression @ui-cc @chopa
Scenario Outline: NOT Display Pregnant Checkbox when Gender = all the rest BLCRM
Given I logged into CRM
When I click on initiate contact record
And I search for a consumer and to click on Add Consumer button
When I select all the rest "<Gender>" except Female
Then I should NOT see PREGNANT checkbox
Examples:
|  Gender   |
|  Male    |
|  Neutral |
|  Other   |
|  Unknown |

@CP-26393 @CP-26393-04 @crm-regression @ui-cc @chopa
Scenario: I verify PREGNANCY DUE DATE is required BLCRM
Given I logged into CRM
When I click on initiate contact record
And I search for a consumer and to click on Add Consumer button
When I select Gender as "Female"
And I click pregnancy checkbox
When I click on Create Consumer Button
Then I verify "PREGNANCY DUE DATE" is required and cannot be left blank

@CP-26393 @CP-26393-05 @crm-regression @ui-cc @chopa
Scenario:  Pregnant Checkbox and PREGNANCY DUE DATE NOT display when other than Female reselected BLCRM
Given I logged into CRM
When I click on initiate contact record
And I search for a consumer and to click on Add Consumer button
When I populate Create Consumer fields for a new  pregnant Female consumer
When I select Gender as "Male"
Then I should NOT see PREGNANT checkbox