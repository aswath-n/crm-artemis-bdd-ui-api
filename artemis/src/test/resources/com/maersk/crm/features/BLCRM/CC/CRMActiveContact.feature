Feature: Active Contact - Case/Consumer Search

  @CP-11278 @CP-11278-01 @asad @crm-regression @ui-cc
  Scenario: Link Active contact record to a Consumer profile only
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    Then I am navigated to the Active Contact screen which has null consumer role
    And I select the Demographic Info tab on the Consumer profile
    Then I am navigated to the Consumer Profile view of the Demographic Info tab

  @CP-11278 @CP-11278-02 @asad @crm-regression @ui-cc
  Scenario Outline: Unlink consumer profile and re-link to a Case
    Given I logged into CRM and click on initiate contact
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer to check profile details
    When I search for consumer to check profile details
    And I select the Demographic Info tab on the Consumer profile
    Then I am navigated to the Consumer Profile view with the list of case members
    Examples:
      |projectName|
      |[blank]|

  @umid @CP-15547 @CP-15547-04 @CP-15547-05 @ui-core @crm-regression
  Scenario: Invalid Omitted from the Disposition Dropdown on Active Contact/ Edit
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Lisa" and Last Name as "John"
    And I link the contact to an existing Case or Consumer Profile
    Then I verify the dropdown values for "DISPOSITION"
      |Cancelled|
      |Complete|
      |Dropped|
      |Escalate|
      |Requested Call Back |
      |Transfer |
    And I do NOT see "Invalid" as a dropdown option
    And I navigate to the case and contact details tab
    And I click on first Contact Record ID on Contact Record
    And I click on edit button on contact details tab
    Then I verify the dropdown values for "DISPOSITION"
      |Cancelled|
      |Complete|
      |Dropped|
      |Escalate|
      |Requested Call Back |
      |Transfer |
    And I do NOT see "Invalid" as a dropdown option


  @CP-15547 @CP-15547-07 @CP-15547-08 @ui-core @crm-regression
  Scenario: Contact History Does Not Show Invalid Contacts/Contact History Shows Cancelled Contacts
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Lisa" and Last Name as "John"
    And I link the contact to an existing Case or Consumer Profile
    When  I should see following dropdown options for "contact reason" field displayed
      | Enrollment |
    And  I choose "New Enrollment" option for Contact Action field
    And I click on the save button
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    When I enter contact phone number "1327894561"
    And I select contact program type as "Program A"
    Then Verify disposition field values are exist
      |Cancelled|
    When I click End Contact
    And I click save button Active contact
    And I click on Initiate button
    When I searched customer have First Name as "Lisa" and Last Name as "John"
    And I link the contact to an existing Case or Consumer Profile
    And I click cancel button and click continue on warning message
    And I click on Initiate button
    When I searched customer have First Name as "Lisa" and Last Name as "John"
    And I link the contact to an existing Case or Consumer Profile
    And I navigate to the case and contact details tab
    And I verify in Contact History a Disposition of cancelled is displayed and a Disposition of invalid is not displayed
