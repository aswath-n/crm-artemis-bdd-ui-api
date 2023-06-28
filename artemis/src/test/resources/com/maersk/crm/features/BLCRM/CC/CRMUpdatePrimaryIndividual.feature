Feature: Update Primary Individual

  @CRM-756 @CRM-756-01 @muhabbat
  Scenario:Verify Update Primary Individual Name Fields
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Primary Individual section displayed
    When I click on second existing Primary Individual
    When I update PI information "{random}", "{random}", "", "", "", "", "", ""
    Then I verify that Primary Individual name is updated successfully


  @CRM-756 @CRM-756-02 @muhabbat
  Scenario:Verify Update Primary Individual DOB
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Primary Individual section displayed
    When I click on second existing Primary Individual
    When I update PI information "", "", "{random}", "", "", "", "", ""
    Then I verify that Primary Individual date of birth is updated successfully


  @CRM-756 @CRM-756-03 @muhabbat
  Scenario Outline:Verify Update Primary Individual Gender
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Primary Individual section displayed
    When I click on second existing Primary Individual
#    And I should be navigated to Update Primary Individual page
    When I update PI information "<FirstName>", "<LastName>", "<DOB>", "<Gender>", "<StartDate>", "<EndDate>", "<Language>", "<SSN>"
    Then I verify that case Primary Individual is updated successfully
    Examples:
      | FirstName | LastName | DOB | Gender | StartDate | EndDate | Language | SSN |
      |[blank]|          |[blank]| Male   |[blank]|         |[blank]|     |


  @CRM-756 @CRM-756-04 @muhabbat
  Scenario Outline:Verify Inactive Primary Individual
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Primary Individual section displayed
    When I click on second existing Primary Individual
    When I update PI information "<FirstName>", "<LastName>", "<DOB>", "<Gender>", "<StartDate>", "<EndDate>", "<Language>", "<SSN>"
    When I click on second existing Primary Individual
    And I inactivate Primary Individual
    Then I verify the status of Primary Individual is inactive
    Examples:
      | FirstName | LastName | DOB | Gender | StartDate | EndDate | Language | SSN         |
      | {random}  | {random} |[blank]| Female | today     | future  | English  | 123-12-1234 |

  @CRM-756 @CRM-756-05 @muhabbat
  Scenario Outline: Verify Primary Individual status when Inactivate Immediately is checked the end date is current date
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify Primary Individual section displayed
    When I click on second existing Primary Individual
    When I update PI information "<FirstName>", "<LastName>", "<DOB>", "<Gender>", "<StartDate>", "<EndDate>", "<Language>", "<SSN>"
    And I inactivate Primary Individual
    And I verify the status of Primary Individual is inactive
    Then I hover over the status and verify end date is current date
    Examples:
      | FirstName | LastName | DOB | Gender | StartDate | EndDate | Language | SSN         |
      | {random}  | {random} |[blank]| Female | today     | future  | English  | 123-12-1234 |

  @CRM-756  @CRM-756-06 @muhabbat
  Scenario:Verify error displayed for mandatory fields for no data on Update Primary Individual page
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
   When I click on second existing Primary Individual
    When I clear mandatory Update Primary Individual fields and click on save
    Then I verify Update Primary Individual mandatory field error messages
    When I click cancel button on Update Primary Individual page
    When I click on second existing Primary Individual
    Then I verify previous values for the Primary Individual fields remain after cancel

  @CRM-756  @CRM-756-07 @muhabbat
  Scenario Outline:Verify Update Primary Individual start date and end date
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joan" and Last Name as "Riley"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    When I click on second existing Primary Individual
    When I update PI information "<FirstName>", "<LastName>", "<DOB>", "<Gender>", "<StartDate>", "<EndDate>", "<Language>", "<SSN>"
    Then I verify that Primary Individual start date and end date is updated successfully
    Examples:
      | FirstName | LastName | DOB | Gender | StartDate | EndDate | Language | SSN |
      |[blank]|          |[blank]|        | past      | future  |[blank]|     |