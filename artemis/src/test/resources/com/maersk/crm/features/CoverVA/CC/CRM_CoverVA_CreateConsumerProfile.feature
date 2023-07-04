Feature: CoverVA Create a Consumer Profile

  @CP-16856 @CP-16856-1.1 @crm-regression @ui-cc-va @JP
  Scenario: Verify spoken languages configured for COVER-VA Create On Consumer profile
    Given I logged into CRM and select a project "COVER-VA"
    When I click on initiate contact record
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify spoken languages configured
      | spoken_languages                                                                                                                       |
      | English,Spanish,Korean,Vietnamese,Chinese,Tagalog,Amharic,French,Russian,Arabic,Urdu,Hindi,Farsi,Bengali,German,Bassa,Ibo,Yoruba,Other |

  @CP-16856 @CP-16856-1.2 @crm-regression @ui-cc-va @JP
  Scenario: Verify written languages configured for COVER-VA Create On Consumer profile
    Given I logged into CRM and select a project "COVER-VA"
    When I click on initiate contact record
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify written languages configured
      | written_languages     |
      | English,Spanish,Other |

  @CP-17212 @CP-17212-10 @crm-regression @ui-cc-va @JP
  Scenario: Verify consumer detail fields configured for COVER-VA Create On Consumer profile
    Given I logged into CRM and select a project "COVER-VA"
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I ensure customer detail fields are present for VA Create Customer profile
      | Gender                            | Consumer_Type | Corp_Pref | ExternalId_program  |
      | Male,Female,Neutral,Other,Unknown | Consumer      | Paperless | MMIS,Offender,VaCMS |

  @CP-17212 @CP-17212-2.0 @crm-regression @ui-cc-va @JP
  Scenario: Verify consumer communication fields configured for COVER-VA Create On Consumer profile
    Given I logged into CRM and select a project "COVER-VA"
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I ensure customer communication fields are present for VA Create Customer profile

  @CP-17212 @CP-17212-3.0 @crm-regression @ui-cc-va @JP
  Scenario: Verify consumer contact fields configured for COVER-VA Create On Consumer profile
    Given I logged into CRM and select a project "COVER-VA"
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I ensure customer contact fields are present for VA Create Customer profile
      | Phone_Type     | Addres_Type      |
      | Cell,Home,Work | Mailing,Physical |

  @CP-19815 @CP-19815-1.0 @crm-regression @ui-cc-va @JP
  Scenario: Verify Trim spaces off of Case/consumer search first name and Last Names
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    Then I am navigated to active contact page
    And I click on Unlink Contact Button on Active Contact Page
    And I enter first name begining with space for search consumer details
    Then I verify consumer details are returned in result
    When I enter first name ending with space for search consumer details
    Then I verify consumer details are returned in result
    When I enter last name begining with space for search consumer details
    Then I verify consumer details are returned in result
    When I enter last name ending with space for search consumer details
    Then I verify consumer details are returned in result

  @CP-19815 @CP-19815-1.1 @crm-regression @ui-cc-va @JP
  Scenario: Verify Trim spaces off of left nav bar Case/consumer search first name and Last Names
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    Then I am navigated to active contact page
    And I click on Unlink Contact Button on Active Contact Page
    When I navigate to left nav bar Case Consumer Search
    And I enter first name begining with space for search consumer details
    Then I verify consumer details are returned in result
    When I enter first name ending with space for search consumer details
    Then I verify consumer details are returned in result
    When I enter last name begining with space for search consumer details
    Then I verify consumer details are returned in result
    When I enter last name ending with space for search consumer details
    Then I verify consumer details are returned in result

  @CP-1438 @CP-1438-1.0 @crm-regression @ui-cc-va @JP
  Scenario: Verify consumer contact field configured for COVER-VA Create On Consumer profile
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I ensure "Fax" is available in Phone Number Type dropdown


  @CP-22546 @CP-22546-10 @crm-regression @ui-cc-va @chopa
  Scenario: Verify manual search with Offender ID as the Search Consumer value
    Given I logged into CRM and select a project "COVER-VA"
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with Offender external id for cover-va
    When I click on Create Consumer Button
    When I navigate to manual contact record search page
    And I click on Call Managment window
    And I searching for contact with Offender ID
    And I verify Offender ID column is displayed
    When I navigate to case and consumer search page
    When I searched customer by Offender ID in case and consumer page
    And I verify Offender ID column is displayed


  @CP-21418 @CP-21418-1.0 @crm-regression @ui-cc-va @JP
  Scenario: Create Consumer - Default State Dropdown field to 'VA'
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify default State dropdown to "Virginia"

  @CP-21418 @CP-21418-2.0 @crm-regression @ui-cc-va @JP
  Scenario: Editing Consumer Profile - Edit Address - Default State Dropdown field to 'VA'
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with default state for cover-va
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on newly created address to navigate edit address page
    Then I verify default State dropdown to "Virginia"

  @CP-21418 @CP-21418-3.0 @crm-regression @ui-cc-va @JP
  Scenario: Editing Consumer Profile - Add Address - Default State Dropdown field to 'VA'
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
    Then I verify default State dropdown to "Virginia"
