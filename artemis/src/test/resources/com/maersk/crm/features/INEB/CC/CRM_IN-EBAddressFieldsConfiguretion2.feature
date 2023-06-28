Feature: IN-EB Configured Address fields Part 2

@CP-26939 @CP-26939-05 @muhabbat @crm-regression @ui-cc-in
Scenario: IN-EB: Verify Address source is displayed for Consumer on case Address as "Plan Reported"
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "wBvIU" and Last Name as "lnUcDnO"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I navigate to Contact Info Tab
Then I see address value Address Source on view address page
Then I verify Address Source is "Plan Reported"


@CP-26939 @CP-26939-06 @muhabbat @crm-regression @ui-cc-in
Scenario: IN-EB: Verify Address source is displayed for Consumer on case Address as "State Reported"
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "KYRA" and Last Name as "DEMOND"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I navigate to Contact Info Tab
Then I see address value Address Source on view address page
Then I verify on newly created Address Source is "State Reported"


@CP-26939 @CP-26939-07 @muhabbat @crm-regression @ui-cc-in
Scenario: IN-EB: Verify Address source is displayed for Consumer not on case Address as "Plan Reported"
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "Pavithra" and Last Name as "JayannaPlanConsumer"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
Then I see address value Address Source on view address page
Then I verify Address Source is "Plan Reported" for consumers not on a case


@CP-26939 @CP-26939-08 @muhabbat @crm-regression @ui-cc-in
Scenario: IN-EB: Verify Address source is displayed for Consumer not on case Address as "State Reported"
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "Sdfsdf" and Last Name as "Ssfdsf"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
Then I see address value Address Source on view address page
Then I verify Address Source is "State Reported" for consumers not on a case

@CP-26938 @CP-26938-01 @chopa @crm-regression @ui-cc-in
Scenario: IN-EB: Verify Address source is disabled for editing of State Reported Address for Consumer not on case
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "Sdfsdf" and Last Name as "Ssfdsf"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
Then I verify Address Source is "State Reported" for consumers not on a case
Then I verify the address is disabled to be edited

@CP-26938 @CP-26938-02 @chopa @crm-regression @ui-cc-in
Scenario: IN-EB: Verify Address source is disabled for editing of Plan Reported Address for Consumer not on case
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "Pavithra" and Last Name as "JayannaPlanConsumer"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
Then I verify Address Source is "Plan Reported" for consumers not on a case
Then I verify the address is disabled to be edited

@CP-26938 @CP-26938-03 @chopa @crm-regression @ui-cc-in
Scenario: IN-EB: Verify Address source is disabled for editing of Plan Reported Address for Consumer on case
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "wBvIU" and Last Name as "lnUcDnO"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I navigate to Contact Info Tab
Then I verify Address Source is "Plan Reported"
Then I verify the address is disabled to be edited

@CP-26938 @CP-26938-04 @chopa @crm-regression @ui-cc-in
Scenario: IN-EB: Verify Address source is disabled for editing of State Reported Address for Consumer on case
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "KYRA" and Last Name as "DEMOND"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I navigate to Contact Info Tab
Then I verify Address Source is "State Reported"
Then I verify the address is disabled to be edited

@CP-30157 @CP-30157-01 @Beka @crm-regression @ui-cc-in
Scenario:IN-EB: Verify notes components present on pages
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "JonatanRegula" and Last Name as "Consumer"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
Then I see notes component with dropdown relates to save and cancel button "ConsumerPorfile"
When I click on "Case & Contact Details" Tab on Contact Dashboard Page
Then I see notes component with dropdown relates to save and cancel button "ConsumerPorfile"
When I click on "Task & Service Request" Tab on Contact Dashboard Page
Then I see notes component with dropdown relates to save and cancel button "ConsumerPorfile"
When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
Then I see notes component with dropdown relates to save and cancel button "ConsumerPorfile"
When I click on "History" Tab on Contact Dashboard Page

@CP-30157 @CP-30157-02 @Beka @crm-regression @ui-cc-in @ui-cc
Scenario:  IN-EB: Verify notes components present on add address, phone, email pages
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "Dfghghfhfg3" and Last Name as "Consumer"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I click on the Contact Info Tab
Then I see notes component with dropdown relates to save and cancel button "Case"
When I click on Add new address button on Contact Info Tab Page
Then I see notes component with dropdown relates to save and cancel button "Case"
And I click on Cancel button
When I click on the Add Phone Number button on Contact Info Page
Then I see notes component with dropdown relates to save and cancel button "Case"

@CP-30157 @CP-30157-03 @Beka @crm-regression @ui-cc-in
Scenario: IN-EB: Verify notes components present on pages consumer no on a case
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "Asdasdas" and Last Name as "rgsdfsdf"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
Then I see notes component with dropdown relates to save and cancel button "ConsumerPorfile"
When I click on "Case & Contact Details" Tab on Contact Dashboard Page
Then I see notes component with dropdown relates to save and cancel button "ConsumerPorfile"
When I click on "Task & Service Request" Tab on Contact Dashboard Page
Then I see notes component with dropdown relates to save and cancel button "ConsumerPorfile"
When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
Then I see notes component with dropdown relates to save and cancel button "ConsumerPorfile"
When I click on "History" Tab on Contact Dashboard Page

@CP-30157 @CP-30157-04 @Beka @crm-regression @ui-cc-in
Scenario:IN-EB: Verify Navigates Away While Unsaved Note Present
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I searched customer have First Name as "Dfghghfhfg3" and Last Name as "Consumer"
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I type some note in to text field but not saved
When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
When I Navigates Away While Unsaved Note Present
Then I should see warning message: "If you continue, all the captured information will be lost"

@CP-30553 @CP-30553-01 @Beka @crm-regression @ui-cc-in
Scenario: IN-EB: Verify Address source got change to State Reported
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
And I will get the Authentication token for "IN-EB" in "CRM"
And I create a case in INEB with address source "Consumer Reported"
When I will update address source for this consumer to "State Reported"
And I will search case by state caseID
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I navigate to Contact Info Tab
Then I verify Address Source is "State Reported"
Then I verify the address is disabled to be edited

@CP-32605 @CP-32605-02 @Beka @crm-regression @ui-cc-in
Scenario: IN-EB: Verification Address record will save with or without the County field populated
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
When I enter Search criteria fields for a new consumer
And I click on Search Button on Search Consumer Page
And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
When I populate Create Consumer fields with no county for a new consumer for IN-EB
When I click on Create Consumer Button
And I click on the Demographic Info Tab
And I click on Add new address button on Contact Info Tab Page
And I edit all mandatory fields with new data on Edit Address Page within a case no county
And I click save button on add field page
Then I verify that I am in the Address Page

@CP-31313 @CP-31313-01 @chopa @crm-regression @ui-cc-in
Scenario: IN-EB: Verify phone source is updated from Consumer Reported Source to State Reported
Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
When I click on initiate contact record
And I will get the Authentication token for "IN-EB" in "CRM"
And I create a case in INEB with phone source "Consumer Reported"
When I will update phone source for this consumer to "State Reported"
And I will search case by state caseID
And I link the contact to an existing Case or Consumer Profile
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I navigate to Contact Info Tab
Then I verify Phone Source value is "State Reported"