Feature:Verify by Linking a Consumer

  @CRM-232 @CRM-232-01 @sujoy @crm-regression @ui-cc
  Scenario: Validate by Linking the Consumer
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by first name "Test First"
    When I search for an existing contact by last name "Test Last"
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    Then I should see Linked Contact in the Header

  @CRM-232 @CRM-232-02 @sujoy @crm-regression @ui-cc
  Scenario: Validate by Linking the Case
    Given I logged into CRM and click on initiate contact
    And I search for an existing contact by "FirstName" and value "Test First"
    And I search for an existing contact by "LastName" and value "Test Last"
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    Then I should see Linked Contact in the Header
    And I click on the Case Contact Details Tab
    Then I should see Contact Id Present

  @CRM-232 @CRM-232-03  @sujoy @crm-regression @ui-cc
  Scenario: Validate Contact Record by Linking the Consumer
    Given I logged into CRM and click on initiate contact
    And I search for an existing contact by "FirstName" and value "Test First"
    And I search for an existing contact by "LastName" and value "Test Last"
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    Then I should see Linked Contact in the Header
    And I should see the first name and Last name "Test First T Test Last"

