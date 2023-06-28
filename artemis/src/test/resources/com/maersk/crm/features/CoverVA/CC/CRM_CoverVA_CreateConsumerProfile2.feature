Feature: CoverVA Create a Consumer Profile - Part Two


  @CP-17955 @CP-17955-20 @crm-regression @ui-cc-va @JP
  Scenario: Verify able to add greater than one External ID On COVER-VA Create On Consumer profile
    Given I logged into CRM and select a project "COVER-VA"
    When I click on initiate contact record
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify able to add more than on external ID
      | ExternalId_program  |
      | MMIS,Offender,VaCMS |

  @CP-17955 @CP-17955-21 @crm-regression @ui-cc-va @JP
  Scenario: Verify removal of external IDs On COVER-VA Create On Consumer profile
    Given I logged into CRM and select a project "COVER-VA"
    When I click on initiate contact record
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify removal of every external ID added
      | ExternalId_program  |
      | MMIS,Offender,VaCMS |


  @CP-21540 @CP-21540-1.0 @crm-regression @ui-cc-va @JP
  Scenario: Verify consumer detail fields configured for COVER-VA Create On Consumer profile
    Given I logged into CRM and select a project "COVER-VA"
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I ensure below external ids lds are present for VA Create Customer profile
      | ExternalId_program  |
      | MMIS,Offender,VaCMS |

  @CP-21540 @CP-21540-1.1 @crm-regression @ui-cc-va @JP
  Scenario: Verify options of external IDs On Edit COVER-VA Create On Consumer profile
    Given I logged into CRM and select a project "COVER-VA"
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with external id for cover-va
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    Then I ensure below external ids lds are present for VA Edit Customer profile
      | ExternalId_program  |
      | MMIS,Offender,VaCMS |


  @CP-17517 @CP-17517-1.1 @crm-regression @ui-cc-va @JP
  Scenario: Verify address fields are free input active contact search screen
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I click on advanced search
    Then I verify Country,City,Zip code fields are input in "Active Contact" page

  @CP-17517 @CP-17517-1.2 @crm-regression @ui-cc-va @JP
  Scenario: Verify address fields are free input On Create Consumer profile screen
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify Country,City,Zip code fields are input in "Create Consumer" page

  @CP-17517 @CP-17517-1.3 @crm-regression @ui-cc-va @JP
  Scenario: Verify address fields are free input On Edit Consumer profile screen
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with external id for cover-va
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on newly created address to navigate edit address page
    Then I verify Country,City,Zip code fields are input in "Edit Address" page

  @CP-17517 @CP-17517-1.4 @crm-regression @ui-cc-va @JP @ui-cc-smoke
  Scenario: Verify address fields are free input On Manual Consumer profile Advanced search screen
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with external id for cover-va
    When I click on Create Consumer Button
    When I navigate to Case Consumer Advance Search
    Then I verify Country,City,Zip code fields are input in "Case Consumer" page

  @CP-17517 @CP-17517-1.5 @crm-regression @ui-cc-va @JP
  Scenario: Verify address fields are free input On on saved Contact record Edit - Advanced search
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with external id for cover-va
    When I click on Create Consumer Button
    When I navigate to Case Consumer Advance Search in Saved Contact record
    Then I verify Country,City,Zip code fields are input in "Case Consumer Save Record Search" page

  @CP-17517 @CP-17517-1.6 @crm-regression @ui-cc-va @JP
  Scenario: Verify address fields are free input On on Create Task - All fields
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "All Fields 2" task page
    Then I verify Country,City,Zip code fields are input in "Create Task" page


  @CP-21250 @CP-21250-2.0 @crm-regression @ui-cc-va @JP
  Scenario: Verify County field is optional for Edit Consumer Address on CoverVA
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with external id for cover-va
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on newly created address to navigate edit address page
    And I ensure County filed is optional
    And I see "county" field accept 30 characters in total
    And I ensure County filed is optional
    Then I verify Consumer updated with County field is optional

  @CP-21250 @CP-21250-3.0 @crm-regression @ui-cc-va @JP
  Scenario: Verify County field is optional in Add Address Profile on CoverVA
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with external id for cover-va
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on Add new address button on Contact Info Tab Page
    And I see "county" field accept 30 characters in total
    And I ensure County filed is optional
    And I verify new address created with County as optional