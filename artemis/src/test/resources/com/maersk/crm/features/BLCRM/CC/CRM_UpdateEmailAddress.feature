@CP-1423
Feature: Update email validations

  @CP-1423 @CP-1423-1.0 @crm-regression @ui-cc @JP
  Scenario: Update email with field level validations
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I click on the Add button for Email Address
    Then I verify if email input field does not accept below invalid test data
      | InvalidEmail    |
      | test@test.123:  |
      | test@test.c-    |
      | test@test..com  |
      | test@test.#%{   |
      | test..@test.com |
    Then I verify email input field accepts below valid test data.
      | ValidEmail                                                                |
      | myemail@test.com                                                          |
      | test@test.gjghgghfhgfhjkjhkjhkjhjkhkjhghjhjghjgujhgjhgftjhghjfhhjgfhg1kju |
      | test@gjghgghfhgfhjkjhkjhkjhjkhkjhghjhjghjgujhgjhgftjhghjfhhjgfhg1kju.test |
      | test_com@tests.com                                       |
      | test_@test.comm                                                   |