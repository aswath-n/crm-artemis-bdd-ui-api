Feature: Search Feature - Do Not Cache Search Parameters

  @CP-949 @CP-949-01 @albert @crm-regression @ui-cc
  Scenario: Verification search parameters are not cached for any free text field on Case/Consumer Search Page in context of Active Contact
    Given I logged into CRM and click on initiate contact
    When I populate All Search criteria fields for an Active Contact
    And I click on Active Contact Search Button
    And I click on Active Contact Reset Button
    And I navigate away from Active Contact page to Third Party Contact page
    When I navigate from Third Party Contact page to Active Contact page
    And I enter the first three characters of the previously populated Active Contact parameters
    Then I do not see auto-populated values of Active Contact and no search parameters are cached

  @CP-949 @CP-949-02 @albert @crm-regression @ui-cc
  Scenario: Verification search parameters are not cached for any free text field on Create Consumer Profile Page
    Given I logged into CRM and click on initiate contact
    When I populate All Search criteria fields for an Active Contact
    And I click on Active Contact Search Button
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate the Create Consumer fields
    And I click on Create Consumer cancel button
    And I click on continue button on warning message
    And I click on Active Contact Reset Button
    And I navigate away from Active Contact page to Third Party Contact page
    When I navigate from Third Party Contact page to Active Contact page
    And I enter the first three characters of the previously populated Active Contact Name parameters
    And I click on Active Contact Search Button
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I populate the remaining Create Consumer fields with the first three characters of the previous parameters
    Then I do not see auto-populated values of Create Consumer fields and no search parameters are cached

  @CP-949 @CP-949-03 @albert @crm-regression @ui-cc
  Scenario: Verification search parameters are not cached for any free text field on Edit Contact Record page in context of Active Contact
    Given I logged into CRM and click on initiate contact
    When I populate All Search criteria fields for an Active Contact
    And I click on Active Contact Search Button
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate all the Create Consumer fields
    And I click on Create Consumer Button
    And I populate the Contact Details
    And  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    And I enter Contact Reason Comment
    And I enter Additional Comment
    And I close the current Contact Record and re-initiate a new Contact Record
    When I search for created consumer
    And I link the contact to an existing Case
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I click on first Contact Record ID on Contact Record
    And I click on edit icon the Contact Details page
    And I click on Unlink Contact Button on Active Contact Page
    And I populate the free text fields on the Edit Contact Record page
    And I navigate to the Edit History page
    And I navigate to Contact Details
    And I enter the first three characters of the previously populated Edit Contact Record parameters
    Then I do not see auto-populated values of Edit Contact Record fields and no search parameters are cached

  @CP-949 @CP-949-04 @CP-7827 @CP-7827-04 @albert @ui-cc
  Scenario: Verification search parameters are not cached for any free text field on Manual Case/Consumer Search page
    Given I logged into CRM
    And I click on Case Consumer Search page
    When I populate the free text parameters on Case Consumer Search page
    And I click the Case Consumer search button
    And I click on the Case Consumer cancel button
    And I click on the Contact Record Search Tab
    And I click on Case Consumer Search page
    And I enter the first three characters of the previously populated Case Consumer search parameters
    Then I do not see auto-populated values of Case Consumer fields and no search parameters are cached

  @CP-949 @CP-949-05 @CP-7827 @CP-7827-05 @albert @ui-cc
  Scenario: Verification search parameters are not cached for any free text field on Manual Contact Record Search page
    Given I logged into CRM
    And I navigate to Contact Record Search Page
    When I populate the free text parameteres on Contact Record page
    And I click search button on Contact Record Search page
    And I click cancel button on Contact Records Search page
    And I navigate to the Case Consumer Search page
    And I navigate to Contact Record Search Page
    And I enter the first three characters of the previously populated Contact Record search parameters
    Then I do not see auto-populated values of Contact Record fields and no search parameters are cached

  @CP-949 @CP-949-06 @CP-7827 @CP-7827-06 @albert @ui-cc
  Scenario: Verification search parameters are not cached for any free text field on Manual Task Search page
    Given I logged into CRM
    And I navigate to the task search page
    When I populate the free text parameters on Task Search page
    And I click the task search button
    And I populate the task search name
    And I click cancel on search name
    And I clear the task search Warning Message
    And I click the task cancel button
    Then the task search free text fields have been cleared
    When I navigate away from Task Search to Contact Record Search
    And I navigate to "Task Search" page
    And I enter the first three characters of the previously populated Task search parameters
    Then I do not see auto-populated values of Task search fields and no search parameters are cached








