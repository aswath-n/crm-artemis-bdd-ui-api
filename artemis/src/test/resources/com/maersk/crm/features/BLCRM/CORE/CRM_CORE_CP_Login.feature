Feature: CP Login

  @CP-22158 @CP-22158-01 @CP-22158-02 @CP-22158-03 @aikanysh @ui-core @crm-regression
  Scenario: Add User Role Dropdown to CP Login Screen - 1 default role
    Given I started to login with "SVC_mars_tester_3" and choose "8166 CoverVA"
    Then I am able to see default role populated in user role dropdown

  @CP-22158 @CP-22158-2.2 @aikanysh @ui-core @crm-regression
  Scenario: Add User Role Dropdown to CP Login Screen - Multiple Roles
    Given I started to login with "SVC_mars_tester_5" and choose "6210 NJ-SBE"
    Then I am able to see multiple roles populated in user role dropdown

  @CRM-280 @CRM-280-01 @shilpa @crm-regression @ui-core
  Scenario: Validate the Create Task button
    Given I logged into the CRM Application
    When I click on Hamburger Menu I should see the create Task button and I should be able to click

  @CRM-280 @CRM-280-02 @shilpa @crm-regression @ui-core
  Scenario: Validate the options are all displayed in the Hamburger Menu
    Given I logged into the CRM Application
    When I click on the Hamburger Menu
    Then I should see all the options present

  @CRM-280 @CRM-280-03 @shilpa @crm-regression @ui-core
  Scenario:Validate the Username
    Given I logged into the CRM Application
    And I should see the Username should be present

  @CRM-280 @CRM-280-04 @shilpa @crm-regression @ui-core
  Scenario: Validate the Role
    Given I logged into the CRM Application
    And I should see the Role present in the header

  @CRM-280 @CRM-280-05 @shilpa
  Scenario: Validate the Current Date and Time
    Given I logged into the CRM Application
    And I should see the Current Date and Time Displayed

    # @CRM-280-06 functionality changed by @CP-37704
#   @CRM-280 @CRM-280-06 @shilpa @crm-regression @ui-core
#   Scenario: validate the office Address in the footer
#   Given I logged into the CRM Application
#   And I should see the office Address displayed in the bottom

  @login
  Scenario Outline: Execute login to CRM for an hour part 1-5
    Given I logged into the CRM Application "<attempt>"
    And I should see the Username should be present
    Examples:
      | attempt |
      | 1       |
      | 2       |
      | 3       |
      | 4       |
      | 5       |

  @login
  Scenario Outline: Execute login to CRM for an hour part 2-5
    Given I logged into the CRM Application "<attempt>"
    And I should see the Username should be present
    Examples:
      | attempt |
      | 1       |
      | 2       |
      | 3       |
      | 4       |
      | 5       |

  @login
  Scenario Outline: Execute login to CRM for an hour part 3-5
    Given I logged into the CRM Application "<attempt>"
    And I should see the Username should be present
    Examples:
      | attempt |
      | 1       |
      | 2       |
      | 3       |
      | 4       |
      | 5       |

  @login
  Scenario Outline: Execute login to CRM for an hour part 4-5
    Given I logged into the CRM Application "<attempt>"
    And I should see the Username should be present
    Examples:
      | attempt |
      | 1       |
      | 2       |
      | 3       |
      | 4       |
      | 5       |

  @login
  Scenario Outline: Execute login to CRM for an hour part 5-5
    Given I logged into the CRM Application "<attempt>"
    And I should see the Username should be present
    Examples:
      | attempt |
      | 1       |
      | 2       |
      | 3       |
      | 4       |
      | 5       |