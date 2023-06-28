Feature: Spoken and Written Language Based on Channel

  @CP-11393 @CP-11393-01.1 @umid @crm-regression @ui-core
  Scenario: Active General Contact / Saved General Contact Record / Edit General Contact Record - Spoken Language
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "HarrisonoTCoL" and Last Name as "KuhicPZZLr"
    And I ensure Channel of the contact is "Phone"
    And I link the contact to an existing Case or Consumer Profile
    Then "Spoken Language" field and the value is Displayed
    And I verify the "Written Language" is not Displayed
    And I navigate to the case and contact details tab
    And I click on first Contact Record ID on Contact Record
    Then "Spoken Language" field and the value is Displayed
    And I verify the "Written Language" is not Displayed
    And I click on edit button on contact details tab
    Then "Spoken Language" field and the value is Displayed
    And I verify the "Written Language" is not Displayed

  @CP-11393 @CP-11393-01.2 @umid @crm-regression @ui-core
  Scenario: Active General Contact / Saved General Contact Record / Edit General Contact Record - Written Language
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "JulianneoWvvH" and Last Name as "HahnqzRpe"
    And I link the contact to an existing Case or Consumer Profile
    And I change the contact details channel to "SMS Text"
    Then "Written Language" field and the value is Displayed
    And I verify the "Spoken Language" is not Displayed
    And I choose a contact reason from reason dropdown list
    And I choose a contact action from action dropdown list
    And I click save button for reason and action
    And I enter "3214233322" for Contact Details Phone Field with "Program A"
    And I click on the contact dispotions "Complete"
    And I end the current call
    And I click on save button on task edit page
    And I navigate to Contact Record Search Page
    And I click advance search button on contact record search
    And I search by Phone and enter "3214233322"
    And I click on the first contact id
    Then "Written Language" field and the value is Displayed
    And I verify the "Spoken Language" is not Displayed
    And I click on edit button on contact details tab
    Then "Written Language" field and the value is Displayed
    And I verify the "Spoken Language" is not Displayed

  @CP-11393 @CP-11393-02.1 @umid @crm-regression @ui-core
  Scenario: Third Party Contact - Spoken Language
    Given I logged into CRM and click on initiate contact
    And I navigate to Third Party page
    When I searched customer have First Name as "HarrisonoTCoL" and Last Name as "KuhicPZZLr"
    And I ensure Channel of the contact is "Phone"
    And I click to expand on primary individual
    And I click link consumer on third party page
    Then "Spoken Language" field and the value is Displayed
    And I verify the "Written Language" is not Displayed
    And I navigate to the case and contact details tab
    And I click on first Contact Record ID on Contact Record
    Then "Spoken Language" field and the value is Displayed
    And I verify the "Written Language" is not Displayed
    And I click on edit button on contact details tab
    Then "Spoken Language" field and the value is Displayed
    And I verify the "Written Language" is not Displayed

  @CP-11393 @CP-11393-02.2 @umid @ui-core @crm-regression
  Scenario: Active Third Party Contact - Written Language
    Given I logged into CRM and click on initiate contact
    And I navigate to Third Party page
    When I searched customer have First Name as "JulianneoWvvH" and Last Name as "HahnqzRpe"
    And I click to expand on primary individual
    And I click link consumer on third party page
    And I change the contact details channel to "SMS Text"
    Then "Written Language" field and the value is Displayed
    And I verify the "Spoken Language" is not Displayed
    And I navigate to the case and contact details tab
    And I click on the SMS text contact id
    Then "Written Language" field and the value is Displayed
    And I verify the "Spoken Language" is not Displayed
    And I click on edit button on contact details tab
    Then "Written Language" field and the value is Displayed
    And I verify the "Spoken Language" is not Displayed
