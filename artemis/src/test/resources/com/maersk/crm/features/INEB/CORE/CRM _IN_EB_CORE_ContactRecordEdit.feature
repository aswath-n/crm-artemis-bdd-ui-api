Feature: IN-EB Contact Record Edit Feature

@CP-25408 @CP-25408-04 @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @aikanysh
Scenario: IN-EB: Contact Record Customizations - Call Summary - View/Edit Contact
Given I logged into CRM with Superuser account and select a project "IN-EB"
And I minimize Genesys popup if populates
When I click on initiate contact record
When I searched customer have First Name as "Aika" and Last Name as "Begi"
And I link the contact to an existing Case or Consumer Profile
When I save the contact record for IN_EB
When I navigate to manual contact record search page
And I searching for contact with phone number and consumer id
  And I searching for contact with electronic signature and choosing first record
And I will edit call summary field and verify edit is displayed on edit history screen

@CP-46054 @ui-core-in-eb @crm-regression @khazar
Scenario: Adding and Editing Additional Comments
Given I will get the Authentication token for "IN-EB" in "CRM"
And I send API CALL for Create CaseConsumer
Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
And I minimize Genesys popup if populates
When I click on initiate contact record
Then When I enter Search criteria fields for a newly created consumer
And I click on Search Button on Search Consumer Page
And I link the contact to an existing CaseConsumer in "IN-EB"
And Wait for 5 seconds
And I select "Inquiry Covered Benefits" from contact reason with "Referral - Health Plan" Contact Action for ineb and save
And I fill Call Summary field with "First call summary" and save
And I click on the Inbound Call queue "UAT_KIDS"
And I enter contact phone number "3332223311"
Then I select "HIP" as contact program type
Then I click on the contact dispotions "Complete"
When I click End Contact
And I click save button Active contact
When I navigate to manual contact record search page
And I search contact record with phone number and click on the first record
When I click on edit button on contact details tab
Then I verify that saved Call Summary is displayed
Then I will edit the saved call summary and the new value is saved and displayed
