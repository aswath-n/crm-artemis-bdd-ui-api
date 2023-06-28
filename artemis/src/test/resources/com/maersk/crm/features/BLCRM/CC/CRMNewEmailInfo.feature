Feature:Demographic Page-Contact Info- Add,View &Update New Email Address Information

  @CRM-763  @CRM-763-01 @shruti @crm-regression @ui-cc
  Scenario: Verify Add Email Address- Required Information Validation
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    Then I navigate to Contact Info Page
    And I should see the EmailAddress label displayed
    And I should see the Add button displayed for Email address
    When I click on the Add button for Email Address
    And I verify that I am in the Add Email Address Page
    Then I verify the fields displayed on the Add Email Address Page


  @CRM-763 @CRM-763-02 @shruti @crm-regression @ui-cc
  Scenario:Verify new email can be added to case member
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    Then I navigate to Contact Info Page
    And I should see the EmailAddress label displayed
    And I should see the Add button displayed for Email address
    When I click on the Add button for Email Address
    And I verify that I am in the Add Email Address Page
    And I enter the mandatory fields on the add email page and click on save
    Then I am navigated back to Demographic page

  @CRM-763 @CRM-763-03 @shruti  @crm-regression @ui-cc
  Scenario:Verify the new email address added
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    Then I navigate to Contact Info Page
    And I should see the Add button displayed for Email address
    When I click on the Add button for Email Address
    And I verify that I am in the Add Email Address Page
    And I enter the mandatory fields on the add email page and click on save
    Then I am navigated back to Demographic page

  @CRM-763 @CRM-763-04 @shruti @crm-regression @crm-smoke @ui-cc
  Scenario:Verify Status of the Email with Current Start date and future end date
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    Then I navigate to Contact Info Page
    When I click on the Add button for Email Address
    And I save the fields and give start date as current and end date in future
    Then I verify status of email as "ACTIVE"


  @CRM-763 @CRM-763-05 @shruti @crm-regression @ui-cc
  Scenario:Verify Status of the Email with Start date in past and future end date
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    Then I navigate to Contact Info Page
    When I click on the Add button for Email Address
    And I save the fields and give start date in past and end date in future
    Then I verify status of email as "ACTIVE"

  @CRM-763 @CRM-763-06 @shruti @crm-regression @ui-cc
  Scenario:Verify Status of the Email with Start date and end date in future
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    Then I navigate to Contact Info Page
    When I click on the Add button for Email Address
    And I save the fields and give start date in the future and end date in future
    Then I verify status of email as "FUTURE"


  @CRM-763 @CRM-763-07 @shruti  @crm-regression @ui-cc
  Scenario:Verify Status of the Email start date in the past and end date as current date
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    Then I navigate to Contact Info Page
    When I click on the Add button for Email Address
    And I save the fields and give start date in the past and end date as current date
    Then I verify status of email as "ACTIVE"

  @CRM-763 @CRM-763-08 @shruti @crm-regression @crm-smoke @ui-cc
  Scenario:Verify Status of the Email start date in the past and end date in past
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    Then I navigate to Contact Info Page
    When I click on the Add button for Email Address
    And I save the fields and give start date in the past and end date as past
    Then I verify status of email as "INACTIVE"

  #@CRM-763 @CRM-763-09 @shruti @crm-regression @ui-cc Muted due to change of functionality CP-15743
  Scenario:Verify Email Address doesn't allow more than 30 characters
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    Then I navigate to Contact Info Page
    When I click on the Add button for Email Address
    And I provide more than thirty characters in email address field "testlongemail!#$%^*/@sample.com"
    Then I verify email address more than 30 characters is not allowed

  @CRM-763 @CRM-763-10 @shruti @crm-regression @ui-cc
  Scenario:Verify Error is displayed for Mandatory fields on Add Email Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    Then I navigate to Contact Info Page
    When I click on the Add button for Email Address
    And I click on save without entering the mandatory fields
    Then I see error message populated below each field

  @CRM-763 @CRM-763-11 @shruti @crm-regression @ui-cc
  Scenario:Verify Error is displayed for Invalid /Incorrect Format for the fields on Add Email Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    Then I navigate to Contact Info Page
    When I click on the Add button for Email Address
    And I provide invalid data for emailAddress,StartDate and EndDate
    Then I see valid field error message populated below each field


  @CRM-764 @CRM-764-01 @shruti @crm-regression @ui-cc
  Scenario:Verify that Active Emails are displayed in descending order
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    Then I navigate to Contact Info Page
    When I click on the Add button for Email Address
    And I add three emailId's with start date as current and end date in future
    Then I verify the order of emailID's and the respective status
 #failing due to bug execute once Bug 1185 is fixed
  @CRM-764 @CRM-764-02 @shruti
  Scenario:Verify that InActive Emails are displayed in descending order
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    Then I navigate to Contact Info Page
    When I click on the Add button for Email Address
    And I add three emailId's with start date and end date in past
    Then I verify the order of emailID's and the respective status


  @CRM-764 @CRM-764-03 @shruti
  Scenario:Verify that Active Emails are displayed on the top followed by inactive
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    Then I navigate to Contact Info Page
    And I click on the Add button for Email Address
    And I add three emailId's with active and inactive status
    Then I verify that the active emails are displayed on the top


  @CRM-764 @CRM-764-04 @shruti
  Scenario:Verify Pagination for email address - 5 emails displayed at first glance
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    Then I navigate to Contact Info Page
    When I add 6 random emailId's with random status
    Then I verify that 5 emails are display at first glance


  @CRM-764 @CRM-764-05 @shruti
  Scenario:Verify that Hower over status start and end dates are displayed
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    Then I navigate to Contact Info Page
    When I click on the Add button for Email Address
    And I save the fields and give start date in the past and end date as current date
    And I verify status of email as "ACTIVE"
    And I hower over status of the newly email added
    Then I verify that the start and end dates are displayed for email address

  @CRM-1330 @CRM-1330-06 @aswath
  Scenario:Verify the order of display of email - ascending and desecnding
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to Contact Info Page
    When I click on the Add button for Email Address
    And I add emailId's with active and inactive status
    Then Active Email are sorted by start date in ascending order
    Then Future Email are sorted by start date in ascending order
    And Inactive Email are sorted by end date in descending order

  @CRM-1330 @CRM-1330-07 @aswath
  Scenario:Verify the order of display of email - Active, Future, Inactive
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    Then I navigate to Contact Info Page
    When I click on the Add button for Email Address
    And I add emailId's with active and inactive status
    Then Active Emails are displayed on top followed by future and inactive

  @CRM-1330 @CRM-1330-01 @aswath @crm-regression
  Scenario: Verify status of address is Future, if start date>current date
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
   # And I select Continue Button on Opt-in warning message
    And  I navigate to Contact Info Page
    When I click on the Add button for Email Address
    And I save the fields and give start date as future
    #Then New Email address should be added to the Email list
    And I verify status of email as "FUTURE"


  @CP-758 @CP-758-01 @crm-regression @ui-cc @mark
  Scenario: Verify Email information in Demographic Contact Info Page
    Given I logged into CRM
    And I navigate to Case Consumer search
    When I search for record by value "DemoPI"
    And I click first CRM Case ID in search results on case and consumer search page
    And I navigate to Contact Info Tab
    Then I am able to view the following "details" for each Email listed
      | EMAIL ADDRESS | STATUS |

  @CP-758 @CP-758-01-01 @crm-regression @ui-cc @mark
  Scenario: Verify Email Start and End Date in Demographic Contact Info Page
    Given I logged into CRM
    And I navigate to Case Consumer search
    When I search for record by value "DemoPI"
    And I click first CRM Case ID in search results on case and consumer search page
    And I navigate to Contact Info Tab
    And I select the first Email listed
    Then I am able to view Email Start and End Date

  @CP-758 @CP-758-02 @crm-regression @ui-cc @mark
  Scenario: Verify number of visible Email records in Demographic Contact Info Page
    Given I logged into CRM
    And I navigate to Case Consumer search
    When I search for record by value "DemoPI"
    And I click first CRM Case ID in search results on case and consumer search page
    And I navigate to Contact Info Tab
    Then I see 3 records visible per page of Emails

  @CP-758 @CP-758-02-01 @crm-regression @ui-cc @mark
  Scenario: Verify email display order based on their status
    Given I logged into CRM
    And I navigate to Case Consumer search
    When I search for record by value "DemoPI"
    And I click first CRM Case ID in search results on case and consumer search page
    And I navigate to Contact Info Tab
    Then I see Email records in the following order by "status"
      | ACTIVE | FUTURE | INACTIVE |



