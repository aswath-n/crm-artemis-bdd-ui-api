Feature: CoverVA Create a Consumer Profile - Part Three

  @CP-19023 @CP-19023-2.0 @ui-cc-va @JP
  Scenario: Verify Program, Consumer ID of external IDs On COVER-VA Create On Consumer profile
    Given I logged into CRM and select a project "COVER-VA"
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with external id for cover-va
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    Then I verify program,consumer ID are visible On Consumer profile

  @CP-18547 @CP-18547-2.0 @crm-regression @ui-cc-va @JP
  Scenario: Verify Program dropdown value On COVER-VA Create On Consumer profile
    Given I logged into CRM and select a project "COVER-VA"
    When I click on initiate contact record
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify program dropdown value behaviour if External ID is missing in CoverVA
      | ExternalId_program  |
      | MMIS,Offender,VaCMS |

  @CP-18547 @CP-18547-2.1 @crm-regression @ui-cc-va @JP
  Scenario: Verify removing of External ID Type and  External ID On COVER-VA Create On Consumer profile
    Given I logged into CRM and select a project "COVER-VA"
    When I click on initiate contact record
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify removing of External ID Type, External ID fields

  @CP-17219 @CP-17219-1.0 @crm-regression @ui-cc-va @JP
  Scenario: Configure Consumer Search Fields - Initial
    Given I logged into CRM with "Service Tester 3" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to Case Consumer Advance Search
    And I select Contact Type as "Internal"
    Then I verify fields are present in case consumer search page
      | Consumer_Type                |
      | Internal,MMIS,Offender,VaCMS |
    And I see "firstName" field accept  15 characters
    And I see "lastName" field accept  15 characters
    And I see "middleName" field accept  1 characters
    And I see "ssn" field accept  9 characters
    And I see "consumerID" field accept  12 characters
    And I see "dob" field accept  8 characters

  @CP-17219 @CP-17219-1.1 @crm-regression @ui-cc-va @JP
  Scenario: Configure Consumer Search Fields - Advanced Search
    Given I logged into CRM with "Service Tester 3" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to Case Consumer Advance Search
    Then I verify fields are present in manual case consumer advance search page
    And I see "addressLine1" field accept  50 characters
    And I see "addressLine1" field accept  50 characters
    And I see "city" field accept  30 characters
    And I see "county" field accept  30 characters
    And I see "phone" field accept  10 characters
        #county missed and zip code element wrong
  #for all these fields we should check the number of characters as per requirement.

  @CP-17219 @CP-17219-1.2 @crm-regression @ui-cc-va @JP
  Scenario: Minimum Search Criteria Required - Match Current Baseline Product
    Given I logged into CRM with "Service Tester 3" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to Case Consumer Advance Search
    And I search for data without any data for single field
    Then I verify the search error message
    When I search for data with minimum criteria "Em"
    Then I verify the search results

  @CP-17219 @CP-17219-1.3 @crm-regression @ui-cc-va @JP
  Scenario: If First or Last Name, Require At Least 3 Characters - Match Current Baseline Product
    Given I logged into CRM with "Service Tester 3" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I click on advanced search
    And I search for data with first name, last name less than minimum length
    Then I verify the minimum chars error message
    When I search for data with first name "Em", last name "Jo" with minimum length
    Then I verify the search results

  @CP-21827 @CP-21827-01 @crm-regression @ui-cc-va @JP
  Scenario: Verify CreateConsumer County field accepting only 30 chars in CoverVA
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I see "county" field accept 30 characters in total


  @CP-21827 @CP-21827-02 @crm-regression @ui-cc-va @JP
  Scenario: Verify CreateConsumer County field is not mandatory in CoverVA
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I ensure County filed is optional


  @CP-21827 @CP-21827-03 @crm-regression @ui-cc-va @JP
  Scenario: Verify Consumer created with County as optional on CoverVA
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with out County for cover-va
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page


  @CP-20949 @CP-20949-1.0 @crm-regression @ui-cc-va @JP
  Scenario: Verify spoken languages configured for Create On Consumer profile for CoverVA
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify spoken languages configured
      | spoken_languages                                                                                                                       |
      | English,Spanish,Korean,Vietnamese,Chinese,Tagalog,Amharic,French,Russian,Arabic,Urdu,Hindi,Farsi,Bengali,German,Bassa,Ibo,Yoruba,Other |

  @CP-20949 @CP-20949-2.0 @crm-regression @ui-cc-va @JP
  Scenario: Verify written languages configured for Create On Consumer profile for CoverVA
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify written languages configured
      | written_languages      |
      | Spanish,English,Other  |



