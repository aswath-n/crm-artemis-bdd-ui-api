Feature: Tenant Manager -- Verify Login

  @CRM-395 @CRM-395-01 @shilpa
  Scenario:Validate the Login fields
    Given I  navigate to the Tenant Manager Url
    When I  navigate it should redirect to the login Page
    Then I should see the login fields present in the login Page

  @CRM-395 @CRM-395-02 @shilpa @tm-regression @tm-smoke
  Scenario:Validate the Login Page by providing valid data
    Given I  navigate to the Tenant Manager Url
    When I navigate it should redirect to the login Page and provide valid login credentials
    Then the system should allow me to see the Home page

  #Invalid login is not developed as a user story as of Jan-04-2019
  @CRM-395 @CRM-395-03 @shilpa
  Scenario:Validate the Login Page by providing invalid data
    Given I  navigate to the Tenant Manager Url
    When I  navigate it should redirect to the login Page  and I provide invalid data in the login page
    Then  the system should throw an error message of invalid username and invalid password

  #Repeated test case of CRM-395-02
  @CRM-395 @CRM-395-04 @shilpa
  Scenario: Validate the Tenant Manger Home Page after logging
    Given I  navigate to the Tenant Manager Url
    When I navigate it should redirect to the login Page and provide valid login credentials
    Then the system should allow me to see the Home page and search for the project

  @CRM-2265 @CRM-2265-01 @aswath @crm-regression
  Scenario:Validate the logout fields
    Given I logged into the CRM Application
    Then I should be navigated to initial landing page
    Then I verify the user icon and logout option

  @CRM-2265 @CRM-2265-02 @aswath @crm-regression
  Scenario:Validate the logout functionality
    Given I logged into the CRM Application
    Then I should be navigated to initial landing page
    Then I verify logout functionality

  @EB-210 @EB-210-01 @crm-regression @muhabbat
  Scenario Outline:Select then Cancel Log out from any page other than Landing page
    Given I logged into the CRM Application
    When I navigate to "<page>" UI
    When I initiate logout button
    And I see Warning Message pop-up
    When I click on cancel button on warning message
    Then I verify system did not navigate away from current "<page>" UI
    Examples:
      | page               |
      | activeContact    |
      | myTask             |
#      | caseConsumerSearch | page is not implemented
      | contactRecord      |


  @EB-210 @EB-210-02 @crm-regression @muhabbat
  Scenario Outline:Select then Continue Log out from any page other than Landing page
    Given I logged into the CRM Application
    When I navigate to "<page>" UI
    When I initiate logout button
    And I see Warning Message pop-up
    When I click on continue button on warning message
    Then I verify system navigated to login page
    Examples:
      | page               |
      | activeContact    |
      | myTask             |
#      | caseConsumerSearch | page is not implemented
      | contactRecord      |








