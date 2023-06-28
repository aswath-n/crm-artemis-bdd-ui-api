Feature: Advance Search Feature - Contact Record

  @CRM-906  @CRM-906-01 @shruti @crm-regression @ui-cc
  Scenario: Verify dropdown values displayed for state , zip & city when county is entered
    Given I logged into CRM and click on initiate contact
    When I click on advance search icon
    And I enter county as "Fairfax" city as "" zip as "" and state as ""
    Then I verify the following options displayed for city field
      | Herndon |
      | Reston  |
    Then I verify the following options displayed for zip field
      | 20171 |
      | 20190 |
    Then I verify the following options displayed for state field
      | Virginia |


  @CRM-906  @CRM-906-02 @shruti @crm-regression @ui-cc
  Scenario: Verify dropdown values displayed for county , zip & city when state is entered
    Given I logged into CRM and click on initiate contact
    When I click on advance search icon
    And I enter county as "" city as "" zip as "" and state as "Georgia"
    Then I verify the following options displayed for county field
      | Fulton   |
      | Columbia |
    Then I verify the following options displayed for zip field
      | 30004 |
      | 30005 |
      | 30075 |
      | 39813 |
    Then I verify the following options displayed for city field
      | Alpharetta |
      | Roswell    |
      | Arlington  |


  @CRM-906  @CRM-906-03 @shruti @crm-regression @ui-cc
  Scenario: Verify dropdown values displayed for county , zip & city when state is entered
    Given I logged into CRM and click on initiate contact
    When I click on advance search icon
    And I enter county as "Columbia" city as "" zip as "" and state as "Georgia"
    Then I verify the following options displayed for zip field
      | 39813 |

  @CRM-906  @CRM-906-04 @shruti @crm-regression @ui-cc
  Scenario: Verify dropdown values displayed for county , zip & city when state is entered
    Given I logged into CRM and click on initiate contact
    When I click on advance search icon
    And I enter county as "Columbia" city as "" zip as "" and state as ""
    Then I verify the following options displayed for state field
      | New York   |
      | Georgia    |
      | Washington |


  @CRM-906  @CRM-906-05 @shruti @crm-regression @ui-cc
  Scenario: Verify dropdown values displayed for county , zip & city when state is entered
    Given I logged into CRM and click on initiate contact
    When I click on advance search icon
    And I enter county as "" city as "Denver" zip as "" and state as ""
    Then I verify the following options displayed for county field
      | Denver    |
      | Jefferson |


  @CRM-623-00 @CRM-623 @sujoy @crm-regression @ui-cc
  Scenario: Validate advance contact record search section for field-level validations
    Given I logged into CRM and click on initiate contact
    And I click on advanced search
    Then verify all advanced fields are displayed

  @CRM-623-01 @CRM-623 @sujoy @crm-regression @ui-cc
  Scenario Outline: Validate advance contact record search section provides the valid search result
    Given I logged into CRM and click on initiate contact
    When I enter Advance Search with <Address>,<City>,<State>,<County>,<ZipCode>,<PhoneNumber> and <Email> on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I verify search results according to the first and last name <FirstName> <LastName> on Contact Record
    Examples:
      | Address            | City         | State      | County     | ZipCode | PhoneNumber    | Email            | FirstName | LastName |
      | '7789 Test Avenue' | ''           | ''         | ''         | ''      | ''             | ''               | 'David'   | 'Miller' |
      | ''                 | ''           | ''         | ''         | ''      | '978-796-8796' | 'david@gmai.com' | 'David'   | 'Miller' |
      | ''                 | ''           | ''         | ''         | ''      | '978-796-8796' | ''               | 'David'   | 'Miller' |
      | ''                 | ''           | ''         | ''         | ''      | ''             | 'david@gmai.com' | 'David'   | 'Miller' |
      | ''                 | ''           | 'New York' | 'Columbia' | ''      | ''             | ''               | ''        | ''       |
      | ''                 | ''           | 'New York' | 'Columbia' | '12017' | ''             | ''               | ''        | ''       |
      | ''                 | ''           | 'New York' | 'Columbia' | ''      | '978-796-8796' | ''               | 'David'   | 'Miller' |
      | ''                 | ''           | 'New York' | 'Columbia' | '12017' | '978-796-8796' | ''               | 'David'   | 'Miller' |
      | ''                 | ''           | 'New York' | 'Columbia' | ''      | ''             | 'david@gmai.com' | 'David'   | 'Miller' |
      | ''                 | ''           | 'New York' | 'Columbia' | '12017' | ''             | 'david@gmai.com' | 'David'   | 'Miller' |
      | ''                 | ''           | 'New York' | 'Columbia' | ''      | '978-796-8796' | 'david@gmai.com' | 'David'   | 'Miller' |
      | ''                 | ''           | 'New York' | 'Columbia' | '12017' | '978-796-8796' | 'david@gmai.com' | 'David'   | 'Miller' |
      | ''                 | 'Austerlitz' | ''         | ''         | ''      | ''             | ''               | ''        | ''       |
      | ''                 | 'Austerlitz' | 'New York' | ''         | ''      | ''             | ''               | ''        | ''       |
      | ''                 | 'Austerlitz' | ''         | 'Columbia' | ''      | ''             | ''               | ''        | ''       |
      | ''                 | 'Austerlitz' | 'New York' | 'Columbia' | ''      | ''             | ''               | ''        | ''       |
      | ''                 | 'Austerlitz' | 'New York' | 'Columbia' | '12017' | '978-796-8796' | 'david@gmai.com' | 'David'   | 'Miller' |

  @CRM-623-03 @CRM-623 @Sujoy @crm-regression @ui-cc
  Scenario Outline: For exact match (case in-sensitive) section, validate advance contact record search
    Given I logged into CRM and click on initiate contact
    When I enter Advance Search with <Address>,<City>,<State>,<County>,<ZipCode>,<PhoneNumber> and <Email> on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I verify search is providing a valid search result is <ValidSearchResult> on Contact Record
    Examples:
      | Address | City | State      | County     | ZipCode | PhoneNumber    | Email             | ValidSearchResult |
      | ''      | ''   | 'New York' | 'Columbia' | '12017' | '978-796-8796' | 'david@gmai.com'  | 'true'            |
      | ''      | ''   | 'New York' | 'Columbia' | '12017' | '978-796-8796' | 'DAVID@gmai.com'  | 'true'            |
      | ''      | ''   | 'New York' | 'Columbia' | '12017' | '978-796-8799' | 'david@gmai.com'  | 'false'           |
      | ''      | ''   | 'New York' | 'Columbia' | '12017' | '978-796-8796' | 'david@gmail.com' | 'false'           |


  @CRM-623-04 @Sujoy @crm-regression @CRM-623 @ui-cc
  Scenario Outline: For Street Address match criteria, validate advance contact record search
    Given I logged into CRM and click on initiate contact
    When I enter Advance Search with <Address>,<City>,<State>,<County>,<ZipCode>,<PhoneNumber> and <Email> on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I verify search is providing a valid search result is <ValidSearchResult> on Contact Record
    Examples:
      | Address       | City | State | County | ZipCode | PhoneNumber | Email | ValidSearchResult |
      | 'Aven'        | ''   | ''    | ''     | ''      | ''          | ''    | 'true'            |
      | 'aVen'        | ''   | ''    | ''     | ''      | ''          | ''    | 'true'            |
      | 'Avenue'      | ''   | ''    | ''     | ''      | ''          | ''    | 'true'            |
      | 'avenuE'      | ''   | ''    | ''     | ''      | ''          | ''    | 'true'            |
      | 'AVENUE'      | ''   | ''    | ''     | ''      | ''          | ''    | 'true'            |
      | 'Test Aven'   | ''   | ''    | ''     | ''      | ''          | ''    | 'true'            |
      | 'test aven'   | ''   | ''    | ''     | ''      | ''          | ''    | 'true'            |
      | 'Test AVEN'   | ''   | ''    | ''     | ''      | ''          | ''    | 'true'            |
      | 'Avenue Test' | ''   | ''    | ''     | ''      | ''          | ''    | 'false'           |
      | 'Ave Test'    | ''   | ''    | ''     | ''      | ''          | ''    | 'false'           |


  @CP-334  @CP-334-01 @asad @crm-regression @ui-cc
  Scenario: Consumer Id when manually view Consumer Profile Information
    Given I logged into CRM
    And I search for consumer profile for Profile View
    Then I will see a visual indication in the header for Profile View

  @CP-334  @CP-334-01.1 @asad @crm-regression @ui-cc
  Scenario: Text Display when manually viewing Consumer Profile details
    Given I logged into CRM
    And I search for consumer profile for Profile View
    And I navigate to consumer profile for Profile View
    Then I view the Icon and I will see text display that indicates a user is viewing the 'Consumer Profile View'
