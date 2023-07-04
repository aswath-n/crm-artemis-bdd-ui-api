Feature: CoverVA Validate all potential match functionality

@CP-30136 @CP-30136-07 @Beka @crm-regression @ui-cc-va
Scenario: Apply Potential Match to Third Party C&C Search - Create Consumer for COVER-VA
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I click on initiate contact record
And I click on third party contact record in active contact
When I searched existing from third party search case where First Name as "Jon" and Last Name as "Nicholas"
And I populate "12021923" and "997779900" for existing consumer
And I click on Add Consumer button
When I populate the rest fields and try to create consumer "COVER-VA" to trigger potential match functionality
Then  I will be presented with Consumer options that match the data I entered per

@CP-30136 @CP-30136-08 @Beka @crm-regression @ui-cc-va
Scenario: Permit Linking of Potential Match for  COVER-VA
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I click on initiate contact record
And I click on third party contact record in active contact
  When I searched existing from third party search case where First Name as "Jon" and Last Name as "Nicholas"
And I populate "12021923" and "997779900" for existing consumer
And I click on Add Consumer button
When I populate the rest fields and try to create consumer "COVER-VA" to trigger potential match functionality
Then I am able to link the selected matching Consumer to the Contact Record "COVER-VA"

@CP-30136 @CP-30136-09 @Beka @crm-regression @ui-cc-va
Scenario: Verify ability to create consumer if not exact match consumer exist - Create Consumer for  COVER-VA
Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
And I click on initiate contact record
When I searched existing case where First Name as "Jon" and Last Name as "Nicholas"
And I populate "12021923" and "" for existing consumer
And I click on Add Consumer button
When I populate the rest fields and try to create consumer "COVER-VA" to trigger potential match functionality
Then I verify "CONTINUE AND ADD NEW CONSUMER" is enabled in Create Consumer Page

  @CP-33556 @CP-33556-03 @Beka @ui-cc-va @crm-regression
  Scenario: Verify potential matches header from create consumer page CoverVA
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I click on Case Consumer Search page
    And I search for consumer have First Name as "Umesh" and Last Name as "Kumar"
    And I click on Search Button
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I populate email and phone number and click on create consumer button
    Then I see list of Potential Match consumers table with header
      | CONSUMER ID          |
      | EXTERNAL CONSUMER ID |
      | ROLE                 |
      | FULL NAME            |
      | GENDER               |
      | DATE OF BIRTH        |
      | SSN                  |
      | STATUS               |