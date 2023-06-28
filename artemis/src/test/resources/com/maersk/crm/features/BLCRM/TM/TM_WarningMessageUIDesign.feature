Feature: Tenant Manager - Viewing Warning Messages as separate messages

  @CRM-1499 @CRM-1499-01 @muhabbat @CP-471 @CP-471-01 @tm-regression @ui-tm
  Scenario Outline: Clicking the Back Arrow button without saving changes on any given Tenant Manager page
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    When I navigate to Create "<page>" of TM project
    And I populate some fields with data on "<page>" of the project
    And I navigate away by clicking the back arrow without saving changes on this page
    And I see Warning pop-up message displayed
    Examples:
      | page               |
      | EditProjectDetails |
      | AddUser            |
      | AddRole            |
      #| AddGroupPerm       |
      | AddHolidays        |
      | AddTaskTemplate    |
      #| UploadProject      |

# @CRM-1499 this scenario outline is not in AC, but is covered on add/edit pages of CRM
#  Scenario Outline: Clicking Cancel button without saving changes on any given CRM page
#    Given I logged into Tenant Manager Project list page
#    When I search for a project by "project" value "BLCRM"
#    And I expand a random project to view the details
#    When I navigate to Create "<page>" of CRM project
#    And I populate some fields with data on CRM "<page>"
#    And I click on Cancel Button on this page
#    And I see Warning pop-up message displayed
#    Then I am able to to call Cancel action on Warning pop-up message on CRM "<page>" component
#    And I system did not navigate away from the CRM "<page>"
#    Then I verify previously populated on CRM "<page>" fields have expected data
#    Examples:
#      | page |


  @CRM-1499 @CRM-1499-03 @muhabbat @tm-regression @ui-tm
  Scenario: Static Warning Message verification
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    When I navigate to Create "AddHolidays" of TM project
    Then I see static warning message on this page

  @CRM-233 @CRM-233-01 @Sujoy
  Scenario: Static Warning Message verification for Holidays while pressing Cancel button
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Create "AddHolidays" of TM project
    And I input Holiday Name "Company Holiday" for a holiday of TM project
    And I press "Cancel" button on Holidays of TM project
    Then I see Warning pop-up message displayed
    And I click cancel on warning popup message for a holiday of TM project
    Then Verify user remain on the Holiday screen

  @CRM-233 @CRM-233-02 @Sujoy @tm-regression @ui-tm
  Scenario: Static Warning Message verification for Holidays while pressing Continue button
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Create "AddHolidays" of TM project
    And I input Holiday Name "Company Holiday" for a holiday of TM project
    And I press "Cancel" button on Holidays of TM project
    Then I see Warning pop-up message displayed
    And I click continue on warning popup message for a holiday of TM project
    Then Verify user remain on the Holiday screen

  @CRM-233 @CRM-233-03 @Sujoy @tm-regression @ui-tm
  Scenario: Static Warning Message verification for Holidays While Navigate away
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Create "AddHolidays" of TM project
    And I input Holiday Name "Company Holiday" for a holiday of TM project
    And I navigate away by clicking the back arrow without saving changes on this page
    Then I see Warning pop-up message displayed
    And I click cancel on warning popup message for a holiday of TM project
    Then Verify user remain on the Holiday screen

  @CP-241 @CP-241-01 @muhabbat @tm-regression @ui-tm
  Scenario: Inline warning beneath the field with the date on weekend
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Create "AddHolidays" of TM project
    And I input Holiday Date "09/24/2023" for a holiday of TM project
    And I input Holiday Name "Company Holiday" for a holiday of TM project
    And I will click on add icon on Holidays Page
    And I press "Save" button on Holidays of TM project
    When I see Warning pop-up message displayed
    ##inline is no longer displayed, we see pop-up that holiday is on weekend
    #Then I see inline warning beneath the field and the field itself is highlighted in orange
    Then I see Warning pop-up message displayed that holiday is on weekend