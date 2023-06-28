Feature: Add Program Contact Details for General and Third Party Contact

  @CP-1000 @CP-1000-01 @ozgen @crm-regression @ui-core
  Scenario Outline: Adding Program Contact Details for General
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I click on Unlink Contact Button on Active Contact Page
    And I search for a consumer that I just created for general contact
    And I link the contact to an existing Case or Consumer Profile
    When  I should see following dropdown options for "contact type" field displayed
      | Inbound |
    And I capture current contact phone number
    And I select contact program types as "<Program1>" and "<Program2>"
    Then I verify programs "<Program1>" and "<Program2>" fields are captured
    Examples:
    |Program1             |Program2    |
    |Program B            |Program C   |
    
  @CP-1000 @CP-1000-02 @ozgen @crm-regression @ui-core
    Scenario Outline: Adding Program Contact Details for Third Party
      Given I logged into CRM and click on initiate contact
      When I click on third party contact record type radio button
      And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
      When I add New Consumer to the record for third party
      When  I should see following dropdown options for "contact type" field displayed
        | Outbound |
      And I capture current contact phone number
      And I select contact program type as "<Program>"
      Then I verify the program "<Program>" field that captures during saving the contact record

      Examples:
        | First Name  | Last Name | Organization Name | Consumer Type | Preferred Language | Program  |
        | Ellie       | Smith     | ABC Group         | Media         | English            |Program C |

