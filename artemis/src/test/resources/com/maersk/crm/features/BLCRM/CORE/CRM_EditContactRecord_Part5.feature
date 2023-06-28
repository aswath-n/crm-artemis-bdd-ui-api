Feature: Edit Contact Record History: Part 5

  @CP-704 @CP-704-01.1 @CP-457-07 @asad @ui-core @crm-regression
  Scenario: View Edited By user name
    Given I logged into CRM and click on initiate contact
    When I create new unidentified contact record for Contact Record Edit
    When I click contact search tab for Contact Record
    And I search and view results for Contact Record Edit
    Then I navigate to edit history and I will view the Edited By field

  @CP-704 @CP-704-01.2 @CP-457-08 @asad @ui-core @crm-regression #Failing due to CP-41198
  Scenario: View most recent Edited By user name
    Given I logged into CRM and click on initiate contact
    When I create new unidentified contact record for Contact Record Edit
    When I click contact search tab for Contact Record
    And I search and view results for Contact Record Edit
    Then I navigate to edit history and I will see the first and last name of the User who made the most recent Edit

  @CP-704 @CP-704-01.3 @CP-457-09 @asad @ui-core @crm-regression #Failing due to CP-41198
  Scenario: Verify field name length for edited by user name
    Given I logged into CRM and click on initiate contact
    When I create new unidentified contact record for Contact Record Edit
    When I click contact search tab for Contact Record
    And I search and view results for Contact Record Edit
    Then I navigate to edit history and I will validate the character length of first and last name of the User who made the most recent Edit

  @reason/action/edit1 @aikanysh @crm-regression @ui-core
  Scenario Outline: All Tenants : Editing Reason/Action on Active Screen: Adding 1 more action to saved Reason/Action
    Given I logged into CRM and click on initiate contact
    And I should see all the attributes present in the Reasons Section
    When I create new reason "Information Request" with actions
      | Provided Appeal Information |
    And I Enter valid data and click on the save button
    And I am able to edit saved reason action by selecting one more "Provided Case Status/Information" action
    Then I verify below actions are saved
      | Provided Appeal Information      |
      | Provided Case Status/Information |
    When I searched customer have First Name as "Aika" and Last Name as "Begi"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Contact Type "<ContactType>"
    And I click on the Contact Channel Type "<ContactChannelType>"
    When I enter contact email as "<Email>"
    And I select contact program type as "<Program>"
    Then I should see following dropdown options for "contact disposition" field displayed
      | Complete |
    And I end the current call
    And I choose contact disposition and click on save button to save contact record
    Examples:
      | Program   | ContactChannelType | ContactType | Email           |
      | Program A | Web Chat           | Inbound     | abctest@xyz.com |

  @reason/action/edit2 @aikanysh @crm-regression @ui-core
  Scenario Outline: All Tenants : Editing Reason/Action on Active Screen: Removing 1 action from saved Reason/Action
    Given I logged into CRM and click on initiate contact
    And I should see all the attributes present in the Reasons Section
    When I create new reason "Information Request" with actions
      | Provided Appeal Information      |
      | Provided Case Status/Information |
    And I Enter valid data and click on the save button
    And I am able to edit saved reason action by removing "Provided Case Status/Information" action
    Then I verify below actions are saved
      | Provided Appeal Information |
    When I searched customer have First Name as "Aika" and Last Name as "Begi"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Contact Type "<ContactType>"
    And I click on the Contact Channel Type "<ContactChannelType>"
    When I enter contact email as "<Email>"
    And I select contact program type as "<Program>"
    Then I should see following dropdown options for "contact disposition" field displayed
      | Complete |
    And I end the current call
    And I choose contact disposition and click on save button to save contact record
    Examples:
      | Program   | ContactChannelType | ContactType | Email           |
      | Program A | Web Chat           | Inbound     | abctest@xyz.com |

  @reason/action/edit3 @aikanysh @crm-regression @ui-core
  Scenario Outline: All Tenants : Editing Additional Comments on Active Screen: Removing characters from saved Additional Comments
    Given I logged into CRM and click on initiate contact
    When I create new reason "Information Request" with actions
      | Provided Appeal Information             |
    And I Enter valid data and click on the save button
    And I Enter Valid  additional Comments
    Then I should be able to save the additional comments
    And I edit the saved Additional comments after saving reason action
    Then I verify edited Additional comments are displayed
    When I searched customer have First Name as "Aika" and Last Name as "Begi"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Contact Type "<ContactType>"
    And I click on the Contact Channel Type "<ContactChannelType>"
    When I enter contact email as "<Email>"
    And I select contact program type as "<Program>"
    Then I should see following dropdown options for "contact disposition" field displayed
      | Complete |
    And I end the current call
    And I choose contact disposition and click on save button to save contact record
    Examples:
      | Program   | ContactChannelType | ContactType | Email           |
      | Program A | Web Chat           | Inbound     | abctest@xyz.com |