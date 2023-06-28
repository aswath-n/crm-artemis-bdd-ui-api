Feature: Viewing Consumer's SSN Information

  @CRM-281 @CRM-281-01 @muhabbat @crm-regression @ui-cc
  Scenario: Validating existing Consumer's SSN is masked by default
    Given I logged into CRM and click on initiate contact
    And I search for an existing contact by criteria
    Then I see all Search results have SSN field value masked in "***-**-****" format

  @CRM-281 @CRM-281-02 @muhabbat @crm-regression @ui-cc
  Scenario: Validating unmasking function
    Given I logged into CRM and click on initiate contact
    And I search for an existing contact by criteria
    Then I see unmasking button displayed

  @CRM-281 @CRM-281-03 @muhabbat @crm-regression @ui-cc
  Scenario: Validating unmasking function
    Given I logged into CRM and click on initiate contact
    And I search for an existing contact by criteria
    And I see unmasking button displayed
    When I and click on unmasking button
    Then I see SSN 9 digits value unmasked

  @CRM-281 @CRM-281-04 @ozgen @nj-regression @ui-cc-nj
  Scenario: Validating masking function for NJ on search parameters
    Given I logged into CRM with "Service AccountOne" and select a project "NJ-SBE"
    And I click on initiate a contact button
    And I and click on unmask button for "SSN" on Active Contact Record Page
    And I verify "SSN" field "***-**-" format

  @CRM-281 @CRM-281-05 @ozgen @nj-regression @ui-cc-nj
  Scenario: Validating unmasking function for NJ when SSN is provided as search parameter
    Given I logged into CRM with "Service AccountOne" and select a project "NJ-SBE"
    And I click on initiate a contact button
    And I search for an existing contact by SSN "1238"
    Then I and click on unmask button for "SSN" on Active Contact Record Page
    And I verify "SSN" field "***-**-****" format
    And I verify that "SSN" field is disabled

  @CRM-281 @CRM-281-06 @ozgen @nj-regression @ui-cc-nj @ui-cc-smoke
  Scenario: Validating consumer SSN is masked by default on Search Results
    Given I logged into CRM with "Service AccountOne" and select a project "NJ-SBE"
    And I click on initiate a contact button
    And I search for an existing contact by SSN "1238"
    And I click on Search Button on Search Consumer Page
    Then I see all Search results have SSN field value masked in "***-**-****" format

  @CRM-281 @CRM-281-07 @ozgen @nj-regression @ui-cc-nj
  Scenario: Validation of consumer SSN masked button
    Given I logged into CRM with "Service AccountOne" and select a project "NJ-SBE"
    And I click on initiate a contact button
    And I search for an existing contact by SSN "1111"
    And I click on Search Button on Search Consumer Page
    And I see unmasking button displayed

  @CRM-281 @CRM-281-08 @ozgen @nj-regression @ui-cc-nj
  Scenario: Validating consumer SSNs is unmasked on Search Results
    Given I logged into CRM with "Service AccountOne" and select a project "NJ-SBE"
    And I click on initiate a contact button
    And I search for an existing contact by SSN "1111"
    And I click on Search Button on Search Consumer Page
    When I and click on unmasking button
    Then I see SSN 4 digits value unmasked

  @CRM-281 @CRM-281-09 @ozgen @nj-regression @ui-cc-nj
  Scenario: Validating unmask button for SSN on Authentication Grid for NJ
    Given I logged into CRM with "Service AccountOne" and select a project "NJ-SBE"
    And I click on initiate a contact button
    And I search for an existing contact by SSN "2473"
    And I click on Search Button on Search Consumer Page
    And I expand the Case Consumer in search result
    And I verify that "SSN" unmask button on authentication grid

  @CRM-281 @CRM-281-10 @ozgen @nj-regression @ui-cc-nj
  Scenario: Validating format of SSN on Authentication Grid after unmasking for NJ
    Given I logged into CRM with "Service AccountOne" and select a project "NJ-SBE"
    And I click on initiate a contact button
    And I search for an existing contact by SSN "2473"
    And I click on Search Button on Search Consumer Page
    And I expand the Case Consumer in search result
    And I click on "SSN" unmask button authentication grid
    And I verify that SSN in asteriks format unmasked on Authentication Grid

  @CRM-281 @CRM-281-11 @ozgen @nj-regression @ui-cc-nj
  Scenario: Validating consumer SSN on General-Consumer in Contact
    Given I logged into CRM with "Service AccountOne" and select a project "NJ-SBE"
    And I click on initiate a contact button
    And I search for an existing contact by SSN "2473"
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    When I and click on unmasking button
    And I verify "SSN" format on General Consumer in Contact
