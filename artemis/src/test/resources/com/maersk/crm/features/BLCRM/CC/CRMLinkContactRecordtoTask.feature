Feature: Link Contact Record to Task Worked During Active Contact


  @CP-691 @CP-691-01 @ui-core @crm-regression @kamil
  Scenario Outline:  Active Contact Screen and Verify Link Component for Task
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to "General" task page
    Then I click Initiate and save the changes
    And I am navigated to the Active Contact page and Verify Link Component for Task
    Examples:
      | contactType | contactChannelType |
      | Inbound     | Phone              |


  @CP-691 @CP-691-02 @ui-core @crm-regression @kamil
  Scenario Outline: Verifly Link Component for Contact Record
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to "General" task page
    Then I click Initiate and save the changes
    And I am navigated to the Active Contact page and Verify Link Component for Task
    When I navigate to "Task Search" page
    And I search Task by Task Id for record
    Then Verify Link Component for Contact Record
    Examples:
      | contactType | contactChannelType |
      | Inbound     | Phone              |


  @CP-691 @CP-691-03 @ui-core @crm-regression @kamil
  Scenario Outline: Third Party Active Contact Screen Verify Link Component for Task
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>" and capture organization name
    When I add New Consumer to the record for third party
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to "General" task page
    And I select task type as "" priority as "" assignee as "" and I enter task info as "Task info"
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    Then I click Initiate and save the changes
    And I am navigated to the Active Contact page and Verify Link Component for Task
    Examples:
      | contactType | contactChannelType | First Name  | Last Name | Organization Name | Consumer Type | Preferred Language |
      | Inbound     | Phone              |     Jack    |     Dawson|    OrgNameTest    | Media         |       English      |


  @CP-691 @CP-691-04 @ui-core @crm-regression @kamil
  Scenario Outline: Verify Link Contact Record to Task Worked During Active Contact(Third Party)
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>" and capture organization name
    When I add New Consumer to the record for third party
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to "General" task page
    Then I click Initiate and save the changes
    And I am navigated to the Active Contact page and Verify Link Component for Task
    When I navigate to "Task Search" page
    And I search Task by Task Id for record
    Then Verify Link Component for Contact Record
    Examples:
      | contactType | contactChannelType | First Name  | Last Name | Organization Name | Consumer Type | Preferred Language |
      | Inbound     | Phone              |     Jack    |     Dawson|    OrgNameTest    | Media         |       English      |


  @CP-12656 @CP-12656-01 @ui-core @crm-regression @aikanysh
  Scenario:  Link Unidentified Contact Record to Task(s) Created During Active Contact
    Given I logged into CRM and click on initiate contact
    And I start creating  Unidentified Contact Record
    When I navigate to "General" task page
    And I select task type as "" priority as "" assignee as "" and I enter task info as "Task info"
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    And I verify Task Link Component has unique TaskId, name = "Task", type = "General", status date is present, and status = "Created"


  @CP-9676 @CP-9676-01 @ui-core  @crm-regression @kamil
  Scenario: Populate Case Summary & Consumer Profile Tabs when navigating from Links Component on Contact Record: Demographic Page: Case Summary
    Given I logged into CRM
    And I navigate to manual contact record search page
    And I search for contact record by contact id "163703"
    When I click on first Contact Record ID on Contact Record
    Then I click linked First Record
    And Verify Demographic Info page for Case ID


  @CP-9676 @CP-9676-02 @ui-core  @crm-regression @kamil
  Scenario: Populate Case Summary & Consumer Profile Tabs when navigating from Links Component on Contact Record: Demographic Page: Consumer Profile
    Given I logged into CRM
    And I navigate to manual contact record search page
    And I search for contact record by contact id "163678"
    When I click on first Contact Record ID on Contact Record
    Then I click linked First Record
    And Verify Demographic Info page for Consumer ID

  @CP-13714 @CP-13714-01 @ui-core  @crm-regression @aikanysh
  Scenario Outline: Update Standardized Link Component to Capture Accurate Contact Record Type: General, ThirdParty, Unidentified
    Given I logged into CRM
    When I navigate to "Task Search" page
    And I search specific task by task id "<taskID>"
    Then I expand above task and very Contact Record Link type and verify the type "<contactRecordType>"
    Examples:
    |taskID |contactRecordType           |
    |22985  | General                    |
    |22986  | Third Party                |
    |22987  | Unidentified Contact       |

  @CP-9675 @CP-9675-01 @ui-core @crm-regression @aikanysh
  Scenario Outline: Populate Consumer Profile Tabs when navigating from Links Component on Task
    Given I logged into CRM
    When I navigate to "Task Search" page
    And I search specific task by task id "<taskID>"
    Then I expand above task and verify that when clicking on consumerID without CASE I am navigated to see "Demographic Info", "Case & Contact Details" and "Tasks & Service Requests" screen
    Examples:
      |taskID     |
      |22996      |