Feature: New Phone Number Add/Edit feature 2

  @CRM-1331 @CRM-1331-01 @aswath @crm-regression @ui-cc
  Scenario Outline: Verification of Phone number statuses are according to the Start and End Dates
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I click on the Add Phone Number button on Contact Info Page
    When I provide valid data in all the fields on Add Phone Number page
    And I set start Date as "<startDate>" and end Date "<endDate>"
    #Then I verify phone number status as "<status>" Dont remove, it old method
    Then verify status of phone number as "<status>"
    Examples:
      | startDate | endDate | status |
      | future    | future  | FUTURE |

#  @CRM-1331 @CRM-1331-07 @aswath @crm-regression
  Scenario Outline: Verification of Phone number statuses are according ascending and descending order
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I add new phone number with Active, Future and Inactive status
      | phoneType | startDate | endDate | comments                               |
      | Cell      | P2        |[blank]| Past date - Active State               |
      | Fax       | F2        | F14     | Future Start - End Date - Future State |
      | Work      | P4        | P2      | Past Start - End - Inactive State      |
      | Cell      | F2        | F4      | Future Start - End Date - Future State |
      | Home      | P10       | P4      | Past Start - End - Inactive State      |
    And I click on the Add Phone Number button on Contact Info Page
    When I provide valid data in all the fields on Add Phone Number page
    And I set start Date as "<startDate>" and end Date "<endDate>"
    Then Active Phone Number are sorted by start date in ascending order
    Then Future Phone Number are sorted by start date in ascending order
    And Inactive Phone Number are sorted by end date in descending order
    Examples:
      | startDate | endDate |
      | current   | future  |

  @CRM-1331 @CRM-1331-06 @aswath @crm-regression @ui-cc
  Scenario: Verification of Phone number statuses are according order Active, Future, Inactive
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I add new phone number with Active, Future and Inactive status
      | phoneType | startDate | endDate | comments                               |
#      | Cell      | P2        |[blank]| Past date - Active State               |
      | Fax       | F2        | F14     | Future Start - End Date - Future State |
      | Work      | P4        | P2      | Past Start - End - Inactive State      |
      | Cell      | F2        | F4      | Future Start - End Date - Future State |
      | Home      | P10       | P4      | Past Start - End - Inactive State      |
    And I click on the Add Phone Number button on Contact Info Page
    When I provide valid data in all the fields on Add Phone Number page
    And I set start Date as "<startDate>" and end Date "<endDate>"
    And Active Phone Number are displayed on top followed by future and inactive


  @CRM-946 @CRM-946-01 @crm-regression @shruti @ui-cc
  Scenario: Verification  Phone number of same type cannot be added- consumer
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I click on the Add Phone Number button on Contact Info Page
    When I provide phone type as "Cell" and start date as "current" to add phone number
    And I click on Save button on add phone number page
    Then I am navigated back to Contact Info page
    And I click on the Add Phone Number button on Contact Info Page
    When I provide phone type as "Cell" and start date as "current" to add phone number
    And I click on Save button on add phone number page
    Then I verify error message for phone number as "Phone type is already active for selected consumer"

  @CRM-946 @CRM-946-02 @crm-regression @shruti @ui-cc
  Scenario: Verification  Phone number of same type cannot be added-case member
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Emma" and Last Name as "Jones"
    And I link the contact to an existing Case or Consumer Profile
    And I navigate to Contact Info Page within a case
    And I check already active contact exists for phone type "Cell"
    And I provide phone type as "Cell" and start date as "current" to add phone number for case consumer and click save
    Then I verify error message for phone number as "Phone type is already active for selected consumer"

  @CRM-946 @CRM-946-03 @crm-regression @shruti @ui-cc
  Scenario: Verify when current phone number is inactivated , then same phone type can be added
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I click on the Add Phone Number button on Contact Info Page
    When I provide phone type as "Cell" and start date as "current" to add phone number
    And I click on Save button on add phone number page
    Then I am navigated back to Contact Info page
    And I click on the Add Phone Number button on Contact Info Page
    When I provide phone type as "Cell" and start date as "current" to add phone number
    And I click on Save button on add phone number page
    Then I verify error message for phone number as "Phone type is already active for selected consumer"
    When I click on cancel button on add phone number page
    And I inactivate an active phone number for phone type "Cell" and click on save
    And I click on the Add Phone Number button on Contact Info Page
    When I provide phone type as "Cell" and start date as "current" to add phone number
    And I click on Save button on add phone number page
    Then I verify error message for phone number is not displayed
    And I am navigated back to Contact Info page

  @CRM-946 @CRM-946-04 @crm-regression @shruti @ui-cc
  Scenario: Verify error is not displayed when different phone type are added
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    And I click on the Add Phone Number button on Contact Info Page
    When I provide phone type as "Cell" and start date as "current" to add phone number
    And I click on Save button on add phone number page
    Then I am navigated back to Contact Info page
    And I click on the Add Phone Number button on Contact Info Page
    When I provide phone type as "Work" and start date as "current" to add phone number
    And I click on Save button on add phone number page
    Then I verify error message for phone number is not displayed
    And I am navigated back to Contact Info page
    And I click on the Add Phone Number button on Contact Info Page
    Then I verify error message for phone number is not displayed

  @CRM-946 @CRM-946-05 @crm-regression @shruti @ui-cc
  Scenario: Verify same Phone type can be added to a another case consumer
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Emma" and Last Name as "Jones"
    And I link the contact to an existing Case or Consumer Profile
    And I navigate to Contact Info Page within a case
    And I check already active contact exists for phone type "Cell"
    And I provide phone type as "Cell" and start date as "current" to add phone number for case consumer and click save
    Then I verify error message for phone number as "Phone type is already active for selected consumer"
    When I select different consumer from consumer drop down to add phone number
    And I click on Save button on add phone number page
    Then I verify error message for phone number is not displayed
    And I am navigated back to Contact Info page

#  @CP-400 @CP-400-01 @crm-regression @ui-cc @mark this scenario has been change with implementation of last updated field
  Scenario: Verify Phone number information in Consumer Profile View
    Given I logged into CRM
    And I navigate to Case Consumer search
    When I search for record by value "Sdfsdf"
    And I click first CRM Case ID in search results on case and consumer search page
    And I navigate to Contact Info Tab
    Then I am able to view the following "details" for each Phone number listed
      | PHONE NUMBER | TYPE | COMMENTS | STATUS |

  @CP-400 @CP-400-01-01 @crm-regression @ui-cc @mark
  Scenario: Verify Phone number Start and End Date in Consumer Profile View
    Given I logged into CRM
    And I navigate to Case Consumer search
    When I search for record by value "Joan"
    And I click first CRM Case ID in search results on case and consumer search page
    And I navigate to Contact Info Tab
    And I select the first phone number listed
    Then I am able to view Phone Number Start and End Date

#  @CP-400 @CP-400-01-02 @crm-regression @ui-cc @mark this scenario has been change with implementation of last updated field
  Scenario: Verify Phone number comments in Consumer Profile View
    Given I logged into CRM
    And I navigate to Case Consumer search
    When I search for record by value "Sdfsdf"
    And I click first CRM Case ID in search results on case and consumer search page
    And I navigate to Contact Info Tab
    Then I see only 1 comment per Phone Number

  @CP-400 @CP-400-02 @crm-regression @ui-cc @mark
  Scenario: Verify number of visible Phone number records in Consumer Profile View
    Given I logged into CRM
    And I navigate to Case Consumer search
    When I search for record by value "Sdfsdf"
    And I click first CRM Case ID in search results on case and consumer search page
    And I navigate to Contact Info Tab
    Then I see 3 records visible per page of Phone Numbers


  @CP-7705 @CP-7705-01.0 @crm-regression @ui-cc @jp
  Scenario: Verify updated Consumer Demographic Info on Case Summary - View phone number
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I verify Consumer label in Phone Number details table
    And I verify Last Updated label in Phone Number details table

  @CP-7705 @CP-7705-01.1 @CP-7705-02.2 @CP-7705-02.3 @crm-regression @ui-cc @jp
  Scenario: Verify updated Consumer Demographic Info on Case Summary - View phone number and verify sort order
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I add new phone number with Active, Future and Inactive status
      | phoneType | startDate | endDate | comments                               |
      | Cell      | P2        |[blank]| Past date - Active State               |
      | Fax       | F2        | F14     | Future Start - End Date - Future State |
      | Work      | P4        | P2      | Past Start - End - Inactive State      |
      | Cell      | F2        | F4      | Future Start - End Date - Future State |
      | Home      | P10       | P4      | Past Start - End - Inactive State      |
    Then verify the different statuses of "Phone Number" are sorted
    And I verify Consumer label in Phone Number details table
    And I verify Last Updated label in Phone Number details table

  @CP-7705 @CP-7705-01.2 @crm-regression @ui-cc @jp
  Scenario: Verify updated Consumer Demographic Info on Case Summary - View phone number and verify alt text is Primary Case Phone Number
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I verify Alt text for Primary Phone Num Icon is "Primary Case Phone Number"

  @CP-7705 @CP-7705-02.0 @crm-regression @ui-cc @jp
  Scenario: Verify updated Consumer Demographic Info on Case Summary - View Primary Case Phone Number checkbox and consumer fields
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I click on the Add Phone Number button on Contact Info Page
    Then I verify Consumer field in Add Phone Number page
    Then I verify primary case phone check box is available and disabled

  @CP-7705 @CP-7705-02.1 @CP-7705-02.2 @crm-regression @ui-cc @jp
  Scenario: Verify updated Consumer Demographic Info on Case Summary - View Primary Case Phone Number checkbox is enabled/disabled
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I click on the Add Phone Number button on Contact Info Page
    Then I verify Consumer field in Add Phone Number page
    Then I verify primary case phone check box is available and disabled
    Then I verify primary case phone check box is enabled by entering test data
      | phoneType | startDate | endDate | comments                 |
      | Cell      | P2        |[blank]| Past date - Active State |
    And I click on the Add Phone Number button on Contact Info Page
    Then I verify Consumer field in Add Phone Number page
    Then I verify primary case phone check box is available and disabled
    Then I verify primary case phone check box is enabled by entering test data
      | phoneType | startDate | endDate | comments                 |
      | Home      | P2        |[blank]| Past date - Active State |

  @CP-7705 @CP-7705-02.3 @CP-7705-03.0 @CP-7705-04.0 @crm-regression @ui-cc @jp @events-cc
  Scenario Outline: Verify updated Consumer Demographic Info on Case Summary - View Primary Case Phone Number checkbox is enabled/disabled
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I click on the Add Phone Number button on Contact Info Page
    Then I verify Consumer field in Add Phone Number page
    Then I verify primary case phone check box is available and disabled
    Then I verify primary case phone check box is enabled by entering test data
      | phoneType | startDate | endDate | comments                 |
      | Cell      | P2        |[blank]| Past date - Active State |
    And I will capture required trace ID out of multiple matching Event Type "<eventType>" and Event Name "<eventName>"
    And I click on the Add Phone Number button on Contact Info Page
    Then I verify Consumer field in Add Phone Number page
    Then I verify primary case phone check box is available and disabled
    Then I verify primary case phone check box is enabled by entering test data
      | phoneType | startDate | endDate | comments                 |
      | Home      | P2        |[blank]| Past date - Active State |
    And I will capture required trace ID out of multiple matching Event Type "<eventType>" and Event Name "<eventName>"
    Then I will verify "phone" save and update events for the captured trace IDs
    Examples:
      | eventName        | module   | eventType | projectName | correlation |
      | PHONE_SAVE_EVENT | CONTACTS | contact   | BLCRM       |[blank]|

