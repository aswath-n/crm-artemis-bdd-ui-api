Feature: Viewing all DOB info field

  @CRM-925 @CRM-925-01 @muhabbat @crm-regression @ui-cc
  Scenario: Validating existing Consumer's DOB is masked by default on Contact Record UI Page
    Given I logged into CRM and click on initiate contact
    And I search for an existing contact by criteria
    Then I see all Search results have DOB field value masked in "**/**/****" format on Contact Record UI Page for "BLCRM"

  @CRM-925 @CRM-925-02 @muhabbat @crm-regression @ui-cc
  Scenario: Validating existing Consumer's DOB is masked by default on Active Consumer Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I am navigated to active contact page
    Then I see all Search results have DOB field value masked in "**/**/****" format on Active Consumer Page

  @CRM-925 @CRM-925-03 @muhabbat @crm-regression @ui-cc
  Scenario: Validating DOB unmasking function on Contact Record UI Page
    Given I logged into CRM and click on initiate contact
    And I search for an existing contact by criteria
    Then I see DOB unmasking button displayed on Contact Record UI Page
    When I and click on unmasking button on Contact Record UI Page
    Then I see DOB in MM/DD/YYYY format unmasked on Contact Record UI Page in "BLCRM"

  @CRM-925 @CRM-925-04 @muhabbat @crm-regression @ui-cc
  Scenario: Validating DOB unmasking function on Active Contact Record Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I am navigated to active contact page
    And  I see unmasking DOB button is displayed on Active Contact Record Page
    When I and click on unmasking button on Active Contact Record Page
    Then I see DOB in MM/DD/YYYY format unmasked on Active Contact Record Page

  @CRM-925 @CRM-925-05 @ozgen @nj-regression @ui-cc-nj
  Scenario: Validating Consumer's DOB is masked by default for NJ on Search parameters
    Given I logged into CRM with "Service AccountOne" and select a project "NJ-SBE"
    And I click on initiate a contact button
    Then I and click on unmask button for "DOB" on Active Contact Record Page
    And I verify "DOB" field "**/**/****" format
    And I verify that "DOB" field is disabled

  @CRM-925 @CRM-925-06 @ozgen @nj-regression @ui-cc-nj
  Scenario: Validating existing Consumer's DOB is masked by default for NJ on Search Parameters after providing DOB
    Given I logged into CRM with "Service AccountOne" and select a project "NJ-SBE"
    And I click on initiate a contact button
    And I search for an existing contact by DOB "07/07/2020"
    Then I and click on unmask button for "DOB" on Active Contact Record Page
    And I verify "DOB" field "**/**/****" format
    And I verify that "DOB" field is disabled

  @CRM-925 @CRM-925-07 @ozgen @nj-regression @ui-cc-nj
  Scenario: Validating DOB unmasking function on Contact Record UI Page for NJ
    Given I logged into CRM with "Service AccountOne" and select a project "NJ-SBE"
    And I click on initiate a contact button
    And I search for an existing contact by SSN "2473"
    And I click on Search Button on Search Consumer Page
    Then I see DOB unmasking button displayed on Contact Record UI Page

  @CRM-925 @CRM-925-08 @ozgen @nj-regression @ui-cc-nj
  Scenario: Validating DOB unmasking function on Search Result for NJ
    Given I logged into CRM with "Service AccountOne" and select a project "NJ-SBE"
    And I click on initiate a contact button
    And I search for an existing contact by SSN "2473"
    And I click on Search Button on Search Consumer Page
    When I and click on unmasking button on Contact Record UI Page
    And I see DOB in MM/DD/YYYY format unmasked on Contact Record UI Page in "NJ"

  @CRM-925 @CRM-925-09 @ozgen @nj-regression @ui-cc-nj
  Scenario: Validating consumer DOB on General-Consumer in Contact clicking unmask for NJ
    Given I logged into CRM with "Service AccountOne" and select a project "NJ-SBE"
    And I click on initiate a contact button
    And I search for an existing contact by SSN "2473"
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    And I and click on unmasking button on Contact Record UI Page
    And I verify "DOB" format on General Consumer in Contact

  @CRM-925 @CRM-925-10 @ozgen @nj-regression @ui-cc-nj
  Scenario: Validating Consumer's DOB is masked on General Consumer in Contact for NJ
    Given I logged into CRM with "Service AccountOne" and select a project "NJ-SBE"
    And I click on initiate a contact button
    And I search for an existing contact by SSN "2473"
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    Then I see all Search results have DOB field value masked in "**/**/****" format on Active Consumer Page

  @CRM-925 @CRM-925-11 @ozgen @nj-regression @ui-cc-nj
  Scenario: Validating unmask button for DOB on Authentication Grid for NJ
    Given I logged into CRM with "Service AccountOne" and select a project "NJ-SBE"
    And I click on initiate a contact button
    And I search for an existing contact by SSN "2473"
    And I click on Search Button on Search Consumer Page
    And I expand the Case Consumer in search result
    Then I verify that "DOB" unmask button on authentication grid

  @CRM-925 @CRM-925-12 @ozgen @nj-regression @ui-cc-nj
  Scenario: Validating Consumer's DOB is unmasked and format of it on Authentication Grid for NJ
    Given I logged into CRM with "Service AccountOne" and select a project "NJ-SBE"
    And I click on initiate a contact button
    And I search for an existing contact by SSN "2473"
    And I click on Search Button on Search Consumer Page
    And I expand the Case Consumer in search result
    And I click on "DOB" unmask button authentication grid
    And I verify that DOB in MM/DD/YYYY format on Authentication Grid







