@fix4
Feature: Viewing Address Information on Demographics Page

  @CRM-758 @CRM-758-01 @muhabbat @ui-cc
  Scenario: Verify Address Information on Contact Info Page
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    Then I navigate to Contact Info Page
    And I click on Add new address button on Contact Info Tab Page
    When I add a new "active" "Physical" address to a consumer profile
    Then I can view full address column has Address Line one, Address Line two, State, Zip in one line displayed
    And I see "County" column has expected value displayed
    And I see "Type" column has expected value displayed
    And I see "Source" column has expected value displayed
    Then I see "Status" column has expected value displayed

  @CRM-758 @CRM-758-02 @muhabbat
  Scenario: Validate Address Start Date and Address End Date format
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    Then I navigate to Contact Info Page
    And I click on Add new address button on Contact Info Tab Page
    When I add a new "active" "Physical" address to a consumer profile
    And I hover over the status of the address
    Then I see Address Start Date and End Date in MM/DD/YYYY format

  @CRM-758 @CRM-758-03 @muhabbat @crm-regression @ui-cc
  Scenario: Validate the records displayed on Address Information section
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    Then I navigate to Contact Info Page
    And I add new addresses with all required fields and different status
    And I see no more 2 Address records displayed on the page
    And I navigate thought additional Address records to see no more 2 Address records displayed on each page

  @CRM-758 @CRM-758-04 @muhabbat @crm-regression @ui-cc
  Scenario: Sorting of multiple records with Active and Inactive status
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    Then I navigate to Contact Info Page
    And I click on Add new address button on Contact Info Tab Page
    When I add a new "inactive" "Physical" address to a consumer profile
    And I change the status of top Address record to Inactive
    Then I see all Active addresses are displayed on the top and followed by Inactive

  @CRM-758 @CRM-758-05 @muhabbat @crm-regression @ui-cc
  Scenario: Sorting of multiple records with Active statuses
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    Then I navigate to Contact Info Page
    And I click on Add new address button on Contact Info Tab Page
    When I add a new "active" "Physical" address to a consumer profile
    Then I see Active record with most in the past Start Date displayed on the top

  @CRM-758 @CRM-758-06 @muhabbat @crm-regression @ui-cc
  Scenario: Sorting of multiple records with Active and Inactive status two
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    Then I navigate to Contact Info Page
    And I click on Add new address button on Contact Info Tab Page
    When I add a new "inactive" "Physical" address to a consumer profile
    And I change the status of second Address record to Inactive
    And I change the status of top Address record to Inactive
    Then I see Inactive record with most recent End Date displayed on the top

  @CRM-1328 @CRM-1328-01 @aswath @crm-regression @ui-cc
  Scenario Outline: Validate the Newly add Address and respective status
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I navigate to Contact Info Page
    When I click on Add new address button on Contact Info Tab Page
    And I edit all required fields along with "<start>" as "<end>"
    And I click on Save button on Edit Address Page
    Then I verify Newly added full address should be in the address list
    Then I view the address "<status>" on address page
    Examples:
      | start  | end | status |
      | future |[blank]| FUTURE |

#  @CRM-1328 @CRM-1328-06 @aswath @crm-regression @ui-cc muted due to functianality change
#  Scenario: Validate the sorting order of status by start and end dates
#    Given I logged into CRM and click on initiate contact
#    When I add New Consumer to the record
#    And I navigate to Contact Info Page
#    And I add new addresses with all required fields and different status
#    Then Active address are sorted by start date in ascending order
#    Then Future address are sorted by start date in ascending order
#    And Inactive address are sorted by end date in descending order

  @CRM-1328 @CRM-1328-07 @aswath @crm-regression @ui-cc
  Scenario: Validate the sorting order of status by active, future and inactive
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I navigate to Contact Info Page
    And I add new addresses with all required fields and different status
    Then Active address are displayed on top followed by future and inactive

  @CP-10960 @CP-10960-01 @crm-regression @ui-cc @jp
  Scenario: Verify updated Consumer Demographic Info on Case Summary - View Address List, Last updated and Status
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    When I click on Add new address button on Contact Info Tab Page
    And I edit all mandatory fields with new data on Edit Address Page within a case
    And I click on Save button on Edit Address Page
    Then I verify the address status as "ACTIVE" on address page
    Then I view the Last Updated on address section
    Then I view the consumer on address section

  @CP-10960 @CP-10960-02 @crm-regression @ui-cc @jp
  Scenario: Verify updated Consumer Demographic Info on Case Summary - View Address List with Status sort order
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    When I click on Add new address button on Contact Info Tab Page
    And I edit all mandatory fields with new data on Edit Address Page within a case
    And I click on Save button on Edit Address Page
    When I click on Add new address button on Contact Info Tab Page
    And I edit all mandatory fields with past date on Edit Address Page within a case
    And I click on Save button on Edit Address Page
    When I click on Add new address button on Contact Info Tab Page
    And I edit all mandatory fields with future date on Edit Address Page within a case
    And I click on Save button on Edit Address Page
    Then I verify the address status as on address page

  @CP-10960 @CP-10960-03 @crm-regression @ui-cc @jp
  Scenario: Verify updated Consumer Demographic Info on Case Summary - Verify Old Addresses
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    When I click on Add new address button on Contact Info Tab Page
    And I edit all mandatory fields with new data on Edit Address Page within a case
    And I click on Save button on Edit Address Page
    When I click on Add new address button on Contact Info Tab Page
    And I edit all mandatory fields with past date on Edit Address Page within a case
    And I click on Save button on Edit Address Page
    When I click on Add new address button on Contact Info Tab Page
    And I edit all mandatory fields with future date on Edit Address Page within a case
    And I click on Save button on Edit Address Page
    Then I verify old addresses not exist in address table

  @CP-454 @CP-454-1.0 @crm-regression @ui-cc @jp
  Scenario: Verify View Consumer Profile Address Information - Address Information
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    When I click on Add new address button on Contact Info Tab Page
    And I edit all fields with past date on Edit Address Page within a case
    And I click on Save button on Edit Address Page
    And I navigated to newly created address edit page
    Then I verify fields displayed under Address edit page

  @CP-454 @CP-454-1-1 @crm-regression @ui-cc @jp
  Scenario: Verify View Consumer Profile Address Information - StartDate and EndDate Tooltip
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    When I click on Add new address button on Contact Info Tab Page
    And I edit all fields with past date on Edit Address Page within a case
    And I click on Save button on Edit Address Page
    Then I mouse over on the Status of the newly added Address
    And I verify data displayed on tooltip

  @CP-454 @CP-454-2 @crm-regression @ui-cc @jp
  Scenario: Verify View Consumer Profile Address Information - Number of records displayed and Pagination
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    When I click on Add new address button on Contact Info Tab Page
    And I edit all mandatory fields with new data on Edit Address Page within a case
    And I click on Save button on Edit Address Page
    When I click on Add new address button on Contact Info Tab Page
    And I edit all mandatory fields with past date on Edit Address Page within a case
    And I click on Save button on Edit Address Page
    When I click on Add new address button on Contact Info Tab Page
    And I edit all mandatory fields with future date on Edit Address Page within a case
    And I click on Save button on Edit Address Page
    Then I verify pagination and number of address displayed

  @CP-26939 @CP-26939-01 @crm-regression @ui-cc @muhabbat
  Scenario: Verify Address source is displayed for Consumer not on case Address
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    Then I see address "Source" on view address page

  @CP-26939 @CP-26939-02 @crm-regression @ui-cc @muhabbat
  Scenario: Verify Address source is displayed as 'Consumer Provided' for Consumer not on case Address
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    When I see address "Source" on view address page
    Then I verify Address Source is "Consumer Reported"

  @CP-26939  @CP-26939-03 @crm-regression @ui-cc @muhabbat
  Scenario: Verify Address source is displayed for Consumer on case Address
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    Then I see address "Source" on view address page


  @CP-26939  @CP-26939-04 @crm-regression @ui-cc @muhabbat
  Scenario: Verify Address source is displayed for Consumer not on case Address
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    Then I see address "Source" on view address page


