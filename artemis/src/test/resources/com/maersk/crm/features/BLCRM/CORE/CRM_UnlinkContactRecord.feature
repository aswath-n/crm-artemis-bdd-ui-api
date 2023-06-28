Feature: Unlink Contact Record - Active Contact

  @CRM-297 @CRM-297-01 @muhabbat @ui-core @crm-regression
  Scenario: Unlink Button on UI for Consumer
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by last name "Potter"
    When I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    Then I see unlink contact record option is displayed

  @CRM-297 @CRM-297-02 @muhabbat
  Scenario: Unlink Button on UI for Case
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by last name "Test Last"
    When I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    Then I see unlink contact record option is displayed

  @CRM-297 @CRM-297-03 @muhabbat @ui-core @crm-regression
  Scenario: Unlink - Initial Creation for Consumer
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by last name "Potter"
    When I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    And I see unlink contact record option is displayed
    And I click on Unlink Contact Button on Active Contact Page
    And I see no values are displayed on Header for Consumer name and ID
    Then I see Contact Record Search is displayed


  @CRM-297 @CRM-297-04 @muhabbat
  Scenario: Unlink - Initial Creation for Case
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by last name "Potter"
    When I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    And I see unlink contact record option is displayed
    And I click on Unlink Contact Button on Active Contact Page
    And I see no values are displayed on Header for Consumer name and ID
    Then I see Contact Record Search is displayed

  @CRM-297 @CRM-297-05 @ui-core @muhabbat @crm-regression
  Scenario: Unlink - Information Captured on Contact Record for Consumer
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact reason" field displayed
      | Update Information Request |
    And  I choose "Updated Eligibility Information" option for Contact Action field
    When I search for an existing contact by last name "Test Last"
    When I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    And I see unlink contact record option is displayed
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    When  I should see following dropdown options for "contact channel" field displayed
      | Web Chat |
    And I click on Unlink Contact Button on Active Contact Page
    And I see no values are displayed on Header for Consumer name and ID
    Then I see entered value for Contact Details and Reason not changed

  @CRM-297 @CRM-297-06 @muhabbat
  Scenario: Unlink - Information Captured on Contact Record for Case
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact reason" field displayed
      | Update Information Request |
    And  I choose "Updated Eligibility Information" option for Contact Action field
    When I search for an existing contact by last name "Test Last"
    When I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    And I see unlink contact record option is displayed
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    When  I should see following dropdown options for "contact channel" field displayed
      | Web Chat |
    And I click on Unlink Contact Button on Active Contact Page
    And I see no values are displayed on Header for Consumer name and ID
    Then I see entered value for Contact Details and Reason not changed

  @CRM-297 @CRM-297-07 @CRM-297-08 @muhabbat @ui-core @crm-regression
  Scenario: Unlink and link with information captured on Contact record- Standard User
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact reason" field displayed
      | Update Information Request |
    And  I choose "Updated Eligibility Information" option for Contact Action field
    When I search for an existing contact by last name "Test Last"
    When I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    And I see unlink contact record option is displayed
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    When  I should see following dropdown options for "contact channel" field displayed
      | Web Chat |
    And I click on Unlink Contact Button on Active Contact Page
    And I see no values are displayed on Header for Consumer name and ID
    And I see entered value for Contact Details and Reason not changed
    When I search for an existing contact by first name "Ethan"
    When I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    And I see unlink contact record option is displayed
    Then I expand and see entered value for Contact Details and Reason not changed

  @CRM-297 @CRM-297-08 @muhabbat @ui-core @crm-regression
  Scenario: Unlink - Initial Creation - Standard User
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I am navigated to active contact page
    Then I see unlink contact record option is displayed


  @CRM-706  @CRM-706-01 @muhabbat @ui-core @crm-regression
  Scenario Outline: Initial unlinking of contact record from one consumer to another
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | <contact type> |
    And I click on the Contact Channel Type "<ContactChannelType>"
    When I enter contact phone number "<Phone>"
    And I select contact program type as "<Program>"
    And I close the current Contact Record and re-initiate a new Contact Record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I expend the saved contact record and unlink it from current Consumer
    When I search for an existing consumer on contact History page and link it Contact Record
    Then I see new Consumer was successfully linked to the current Contact Record
    Examples:
      | Program   | ContactChannelType | contact type | Phone      |
      | Program A | Phone              | Outbound     | 4282929292 |

  @CRM-706 @CRM-706-02 @muhabbat @ui-core @crm-regression
  Scenario Outline: Verification Contact Record can be linked to a different Consumer
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | <contact type> |
    And I click on the Contact Channel Type "<ContactChannelType>"
    When I enter contact phone number "<Phone>"
    And I select contact program type as "<Program>"
    And I close the current Contact Record and re-initiate a new Contact Record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I expend the saved contact record and unlink it from current Consumer
    When I search for an existing consumer on contact History page and link it Contact Record
    And I see new Consumer was successfully linked to the current Contact Record
    When I close edited Contact Record
    And I navigate back to Contact History page
    Then I verify unlinked contact record is removed on Case and Contact details page
    Examples:
      | Program   | ContactChannelType | contact type | Phone      |
      | Program A | Phone              | Outbound     | 4282929292 |

  @CRM-706 @CRM-706-03 @muhabbat @ui-core @crm-regression
  Scenario Outline: Verification system does not save any changes after clicking on cancel button on Edit Contact Record page
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | <contact type> |
    And I click on the Contact Channel Type "<ContactChannelType>"
    When I enter contact phone number "<Phone>"
    And I select contact program type as "<Program>"
    And I close the current Contact Record and re-initiate a new Contact Record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I expend the saved contact record and unlink it from current Consumer
    When I search for an existing consumer on contact History page and link it Contact Record
    And I see new Consumer was successfully linked to the current Contact Record
    When I cancel editing current Contact Record
    And I navigate back to Contact History page
    Then I verify unlinked contact record does not change on Case and Contact details page
    Examples:
      | Program   | ContactChannelType | contact type | Phone      |
      | Program A | Phone              | Outbound     | 4282929292 |

  @CRM-706 @CRM-706-04 @muhabbat @ui-core @crm-regression
  Scenario Outline: Verification unlinking process can be resumed after cancellation on warning message
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | <contact type> |
    And I click on the Contact Channel Type "<ContactChannelType>"
    When I enter contact phone number "<Phone>"
    And I select contact program type as "<Program>"
    And I close the current Contact Record and re-initiate a new Contact Record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I expend the saved contact record and unlink it from current Consumer
    When I search for an existing consumer on contact History page and link it Contact Record
    Then I see new Consumer was successfully linked to the current Contact Record
    When I initiate cancellation of changes but click "cancel" on the warning message
    And I see system returns to the Edit Contact Record Page
    Then I verify unlinking process can be resumed
    Examples:
      | Program   | ContactChannelType | contact type | Phone      |
      | Program A | Phone              | Outbound     | 4282929292 |

  @CRM-706 @CRM-706-05 @muhabbat @ui-core @crm-regression
  Scenario: Verification all captured details before unlinking & linking remain unchanged
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I save the contact as "Outbound" and "Phone"
    And I navigate to contact history page after re-initiating a new contact with the same Consumer
    And I expend the saved contact record, capture all the details and unlink it from current Consumer
    When I search for an existing consumer on contact History page and link it Contact Record
    And I see new Consumer was successfully linked to the current Contact Record
    When I close edited Contact Record
    And I unlink the previous consumer from the active record on Active Contact Record Page
    And I search for second consumer on Contact Record UI Page, link and navigate to Contact Record Info Page
    When I see second consumer has expected contact record displayed
    Then I expend the record to verify all captured details before linking remain unchanged

  @CRM-706 @CRM-706-06 @muhabbat @ui-core @crm-regression
  Scenario: Verification Unlinked Contact Record is not displayed as part of Contact History
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I save the contact as "Outbound" and "Phone"
    And I navigate to contact history page after re-initiating a new contact with the same Consumer
    And I expend the saved contact record, capture all the details and unlink it from current Consumer
    When I search for an existing consumer on contact History page and link it Contact Record
    And I see new Consumer was successfully linked to the current Contact Record
    When I close edited Contact Record
    And I navigate back to Contact History page
    Then I verify unlinked contact record is removed on Case and Contact details page

  @CRM-706 @CRM-706-07 @muhabbat @ui-core @crm-regressi
  Scenario Outline: Validation error message is displayed and Contact record can not be saved without being linked to a Case or Consumer
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | <contact type> |
    And I click on the Contact Channel Type "<ContactChannelType>"
    When I enter contact phone number "<Phone>"
    And I select contact program type as "<Program>"
    And I close the current Contact Record and re-initiate a new Contact Record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I expend the saved contact record and unlink it from current Consumer
    When I click on save button on Edit Contact Record Page
    Then I see error message that Contact record can not be saved without being linked to a Case or Consumer
    Examples:
      | Program   | ContactChannelType | contact type | Phone      |
      | Program A | Phone              | Outbound     | 4282929292 |

  @CRM-706 @CRM-706-08 @muhabbat @ui-core @crm-regression # already covered
  Scenario: Verification System Captures when, by and reason for the record being edited
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I save the contact as "Outbound" and "Phone"
    And I navigate to contact history page after re-initiating a new contact with the same Consumer
    And I expend the saved contact record, capture all the details and unlink it from current Consumer
    When I search for an existing consumer on contact History page and link it Contact Record
    And I see new Consumer was successfully linked to the current Contact Record
    When I close edited Contact Record
    And I unlink the previous consumer from the active record on Active Contact Record Page
    And I search for second consumer on Contact Record UI Page, link and navigate to Contact Record Info Page
    When I see second consumer has expected contact record displayed
    Then I expend the record to verify system captured when, by and reason for the record being edited is displayed